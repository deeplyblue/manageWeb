package com.oriental.manage.service.payment.impl;

import com.oriental.manage.core.desUtils.CryptTool;
import com.oriental.manage.core.dfsUtils.FastDFSPoolUtil;
import com.oriental.manage.core.excelUtils.ExcelUtils;
import com.oriental.manage.core.exception.BusiException;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.system.Constants;
import com.oriental.manage.core.utils.BigDecimalUtils;
import com.oriental.manage.core.utils.DateUtils;
import com.oriental.manage.core.utils.FieldUtils;
import com.oriental.manage.core.utils.SessionUtils;
import com.oriental.manage.dao.base.CommonMapper;
import com.oriental.manage.dao.business.MchntInfoMapper;
import com.oriental.manage.dao.payment.PaymentInfoMapper;
import com.oriental.manage.pojo.base.DataDict;
import com.oriental.manage.pojo.payment.PaymentInfo;
import com.oriental.manage.service.base.IDataDictService;
import com.oriental.manage.service.payment.IPaymentService;
import com.oriental.opcif.common.model.Response;
import com.oriental.opcif.product.bizModel.request.customerAgreement.FindAgreementReqDto;
import com.oriental.opcif.product.bizModel.response.customerAgreement.FindAgreementResDto;
import com.oriental.opcif.product.facade.CustomerAgreementFacade;
import com.oriental.paycenter.commons.exception.BizException;
import com.oriental.paycenter.external.api.operations.TransferFacade;
import com.oriental.paycenter.external.model.transfer.TransferDTO;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class PaymentServiceImpl implements IPaymentService {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private PaymentInfoMapper paymentInfoMapper;
    @Autowired
    private CommonMapper commonMapper;
    @Autowired
    private MchntInfoMapper mchntInfoMapper;
    @Autowired(required = false)
    private TransferFacade transferFacade;
    @Autowired
    private CustomerAgreementFacade customerAgreementFacade;
    @Autowired
    private FastDFSPoolUtil fastDFSPoolUtil;
    @Value("#{cfgProperties['downloadTempDir']}")
    @Setter
    private String downloadTempDir;
    @Autowired
    private IDataDictService dataDictService;

    /**
     * 主页面查询
     *
     * @param pagination  页面对象
     * @param paymentInfo 请求对象
     */
    @Override
    public void queryPage(Pagination<PaymentInfo> pagination, PaymentInfo paymentInfo) {
        List<PaymentInfo> list = paymentInfoMapper.selectBatchList(paymentInfo);
        if (null != list && list.size() > 0) {
            for (PaymentInfo paymentInfo1 : list) {
                paymentInfo1.setSumAmt(BigDecimalUtils.changeF2Y(paymentInfo1.getSumAmt()));
            }
            pagination.setList(list);
            pagination.setRowCount(list.get(0).getRowCount());
        }
    }

    /**
     * 详细页面查询
     *
     * @param pagination  页面对象
     * @param paymentInfo 请求对象
     */
    @Override
    public void queryPaymentDetail(Pagination<PaymentInfo> pagination, PaymentInfo paymentInfo) {
        List<PaymentInfo> list = paymentInfoMapper.queryPaymentDetail(paymentInfo);
        if (null != list && list.size() > 0) {
            doWithList(list);
            pagination.setList(list);
            pagination.setRowCount(list.get(0).getRowCount());
        }
    }

    /**
     * 解密字段
     *
     * @param str 密文
     * @return 明文
     */
    private String decrypt(String str) throws Exception {
        return CryptTool.des3Base64Dec(CryptTool.genDESKey(), str);
    }

    /**
     * 校验当前批次是否被审核
     *
     * @param batchNo 批次号
     * @return 是否被处理
     */
    @Override
    public String checkAuditState(String batchNo) {
        return paymentInfoMapper.checkAuditState(batchNo);
    }

    /**
     * 更新审核状态
     *
     * @param paymentInfo 请求对象
     * @return 更新结果
     */
    @Override
    public boolean updateAuditStateByBatchNo(PaymentInfo paymentInfo) {
        int i = paymentInfoMapper.updateAuditStateByBatchNo(paymentInfo);
        return i > 0;
    }

    /**
     * 启动线程，发起代付
     *
     * @param paymentInfo 请求对象
     */
    @Override
    public void startBatchPaymentThread(final PaymentInfo paymentInfo) {
        Runnable batchRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0, len = paymentInfoMapper.getCountByBatchNo(paymentInfo); i < len; i += 100) {
                        PaymentInfo batchPaymentInfo = new PaymentInfo();
                        batchPaymentInfo.setBatchNo(paymentInfo.getBatchNo());
                        batchPaymentInfo.setPageSize(100);
                        batchPaymentInfo.setPageNum(i / 100 + 1);
                        List<PaymentInfo> paymentList = paymentInfoMapper.queryPaymentDetail(batchPaymentInfo);
                        //密文字段解密处理
                        for (PaymentInfo item : paymentList) {
                            item.setUserName(CryptTool.des3Base64Dec(CryptTool.genDESKey(), item.getUserName()));
                            item.setPhoneNo(CryptTool.des3Base64Dec(CryptTool.genDESKey(), item.getPhoneNo()));
                            item.setIdentityCardNo(CryptTool.des3Base64Dec(CryptTool.genDESKey(), item.getIdentityCardNo()));
                            item.setBankCardNo(CryptTool.des3Base64Dec(CryptTool.genDESKey(), item.getBankCardNo()));
                        }
                        batchPayment(paymentList);
                    }
                } catch (Exception e) {
                    log.error("批量代付异常", e);
                }
            }
        };
        Constants.THREAD_POOL.execute(batchRunnable);
    }

    private void batchPayment(List<PaymentInfo> paymentList) {
        for (PaymentInfo paymentInfo : paymentList) {
            try {
                TransferDTO transferDTO = new TransferDTO();
                transferDTO.setMerchantCode(paymentInfo.getMchntCode());
                transferDTO.setOrderNo(paymentInfo.getOrderNo());
                transferDTO.setChannel("07");//渠道：运营系统
                transferDTO.setOrderDate(DateUtils.getToDay(DateUtils.DATETIMESFORMAT));
                transferDTO.setOrderAmt(paymentInfo.getAmount());
                transferDTO.setTransferType("01");//转账类型：委托代发
                transferDTO.setPayeeNo(paymentInfo.getBankCardNo());
                transferDTO.setPayeeName(paymentInfo.getUserName());
                transferDTO.setPayeePhoneNo(paymentInfo.getPhoneNo());
                log.info("批量代付,订单号:{},调用代付接口transferDTO:{}", paymentInfo.getOrderNo(), transferDTO);
                com.oriental.paycenter.external.model.ResponseDTO<String> response = transferFacade.transfer(transferDTO);
                log.info("批量代付,订单号:{},调用代付接口response:{}", paymentInfo.getOrderNo(), response);
                if (!response.isSuccess()) {
                    log.error("批量代付,订单号:{},错误码:{},错误信息:{}", paymentInfo.getOrderNo(), response.getErrorCode(), response.getErrorMsg());
                }
                paymentInfoMapper.updateBatchPaymentDetail(paymentInfo);
            } catch (Exception e) {
                log.error("批量代付,订单号:{},异常", paymentInfo.getOrderNo(), e);
            }
        }
    }

    /**
     * 文件上传解析
     *
     * @param originFileName  文件名称
     * @param dfsFullFilename dfs全路径
     * @param dfsGroupName    dfs分组
     */
    @Override
    public void uploadFile(String originFileName, String dfsFullFilename, String dfsGroupName) {
        File file = null;
        int i = fastDFSPoolUtil.download(dfsGroupName, dfsFullFilename, downloadTempDir + originFileName);
        if (i == -1) {
            log.error("dfsFullFilename：{}，下载文件异常！", dfsFullFilename);
            throw new BizException("下载文件异常");
        }
        try {
            file = new File(downloadTempDir + originFileName);
            if (StringUtils.isBlank(originFileName)) {
                throw new BizException("文件名为空！");
            }
            if (originFileName.contains("文件名")) {
                throw new BizException("文件名有误！");
            }
            /**原设计通过文件名取得商户号，现直接取数据字典配置，以后若有需求再改造**/
//            String[] aFileName = originFileName.split("_");
//            if (aFileName.length != 2 || StringUtils.isBlank(aFileName[0])) {
//                throw new BizException("文件名有误！");
//            }
//            String queryOrderNo = aFileName[0].trim();
//            String[] aFileName2 = aFileName[1].split("\\.");
//            if (aFileName2.length != 2 || StringUtils.isBlank(aFileName2[0])) {
//                throw new BizException("文件名有误！");
//            }
//            String mchntCode = aFileName2[0].trim();
//            MchntInfo mchntInfo = new MchntInfo();
//            mchntInfo.setMchntCode(mchntCode);
//            List<MchntInfo> list = mchntInfoMapper.getAll(mchntInfo);
//            if (null == list || list.size() == 0) {
//                throw new BizException("商户号不存在！");
//            }
            /**原设计通过文件名取得商户号，现直接取数据字典配置，以后若有需求再改造**/
            String[] aFileName2 = originFileName.split("\\.");
            if (aFileName2.length != 2 || StringUtils.isBlank(aFileName2[0])) {
                throw new BizException("文件名有误！");
            }
            String queryOrderNo = aFileName2[0];
            String mchntCode = getPaymentMchntCode();
            if (!"xls".equals(aFileName2[1])) {
                throw new BizException("文件后缀名必须为xls！");
            }
            // 校验查款录入订单号（文件名）
            if (paymentInfoMapper.isQueryOrderNoExist(queryOrderNo) > 0) {
                throw new BizException("文件名已存在！");
            }
            // 从文件获取信息
            List<PaymentInfo> paymentInfoList = getFileData(file);
            log.info("文件名:{},读取文件内容结束--结束--", queryOrderNo);
            // 数据处理 调用cif查询用户绑卡信息
            paymentInfoList = queryUserInfo(paymentInfoList);
            log.info("文件名:{},查询用户绑卡信息--结束--", queryOrderNo);
            int totalSize = paymentInfoList.size();
            if (totalSize > 0) {
                // 开线程进行批量插入
                this.startBatchInsertThread(mchntCode, queryOrderNo, paymentInfoList);
            } else {
                throw new BizException("文件中没有数据！");
            }
        } catch (BizException be) {
            log.error("上传解析文件失败", be);
            throw new BizException(be.getCode());
        } catch (Exception e) {
            log.error("上传解析文件失败异常", e);
            throw new BizException("上传解析文件失败异常");
        } finally {
            try {
                if (null != file) {
                    FileUtils.forceDelete(file);
                }
            } catch (Exception e) {
                log.error("文件删除异常", e);
            }
        }
    }

    /**
     * 获取数据字典中的代付商户号
     *
     * @return 代付商户号
     */
    private String getPaymentMchntCode() {
        DataDict dataDict = new DataDict();
        dataDict.setItemName("MCHNT_CODE");
        dataDict.setColName("PAYMENT_MCHNT");
        List<DataDict> list = dataDictService.queryAll(dataDict);
        if (null == list || list.size() == 0 || StringUtils.isBlank(list.get(0).getItemDesc())) {
            throw new BizException("未找到付款商户号！");
        }
        return list.get(0).getItemVal();
    }

    /**
     * 上传信息处理
     *
     * @param paymentInfoList 文件信息
     * @return 处理后的文件信息
     */
    private List<PaymentInfo> queryUserInfo(List<PaymentInfo> paymentInfoList) {
        try {
            for (PaymentInfo paymentInfo : paymentInfoList) {
                //根据用户ID 查询用户绑卡信息
                FindAgreementReqDto findAgreementReqDto = new FindAgreementReqDto();
                findAgreementReqDto.setEntityNo(paymentInfo.getUserId());
                findAgreementReqDto.setEntityType("OPERATOR");
                findAgreementReqDto.setAgreementType("QUICK");
                String logId = paymentInfo.getUserId() + DateUtils.getToDay(DateUtils.DATETIMESFORMAT);
                log.info("用户ID:{},调用cif查询用户绑卡信息findAgreementReqDto:{}", paymentInfo.getUserId(), findAgreementReqDto);
                Response<List<FindAgreementResDto>> response;
                response = customerAgreementFacade.queryBindCardList(findAgreementReqDto, logId);
                log.info("用户ID:{},调用cif查询用户绑卡信息response is null:{}", paymentInfo.getUserId(), null == response);
                if (null == response || null == response.getResult() || response.getResult().size() == 0) {
                    log.error("用户ID:{},调用cif查询接口未返回用户绑卡信息response:{}", paymentInfo.getUserId(), response);
                    throw new BizException("用户ID:" + paymentInfo.getUserId() + "调用cif查询接口未返回用户绑卡信息");
                }
                FindAgreementResDto findAgreementResDto = response.getResult().get(0);
                if (StringUtils.isBlank(findAgreementResDto.getAgreerelEntityNo())
                        || StringUtils.isBlank(findAgreementResDto.getAccountName())
                        || StringUtils.isBlank(findAgreementResDto.getPhone())) {
                    log.error("用户ID:{},调用cif查询绑卡接口返回信息字段为空", paymentInfo.getUserId());
                    throw new BizException("用户ID:" + paymentInfo.getUserId() + "调用cif查询绑卡接口返回信息字段为空");
                }
                //根据ID 查询用户信息
           /*  log.info("用户ID:{},调用cif查询用户信息userId:{}", paymentInfo.getUserId());
                Response<CustomerBaseInfoResDto> resDtoResponse = customerInfoFacade.queryCustomerBaseInfo("OPERATOR", paymentInfo.getUserId(), logId);
                log.info("用户ID:{},调用cif查询用户信息resDtoResponse is null:{}", paymentInfo.getUserId(), null == resDtoResponse);
                if (null == resDtoResponse || null == resDtoResponse.getResult()) {
                    log.error("用户ID:{},调用cif查询接口未返回用户绑卡信息response:{}", paymentInfo.getUserId(), resDtoResponse);
                    throw new BizException("用户ID:" + paymentInfo.getUserId() + "调用cif查询接口未返回用户信息");
                }
                CustomerBaseInfoResDto customerBaseInfoResDto = resDtoResponse.getResult();
                if (StringUtils.isBlank(customerBaseInfoResDto.getCertificateNo())) {
                    log.error("用户ID:{},调用cif查询接口用户信息返回信息字段为空", paymentInfo.getUserId());
                    throw new BizException("用户ID:" + paymentInfo.getUserId() + "调用cif查询接口用户信息返回信息字段为空");
                }*/
                paymentInfo.setUserName(CryptTool.des3Base64Enc(CryptTool.genDESKey(), findAgreementResDto.getAccountName()));
                paymentInfo.setBankCardNo(CryptTool.des3Base64Enc(CryptTool.genDESKey(), findAgreementResDto.getAgreerelEntityNo()));
                paymentInfo.setPhoneNo(CryptTool.des3Base64Enc(CryptTool.genDESKey(), findAgreementResDto.getPhone()));
                paymentInfo.setIdentityCardNo(CryptTool.des3Base64Enc(CryptTool.genDESKey(), findAgreementResDto.getCertNo()));
            }
            return paymentInfoList;
        } catch (BizException be) {
            log.error("查询cif用户异常：", be);
            throw new BizException(be.getCode());
        } catch (Exception e) {
            log.error("查询cif用户异常：", e);
            throw new BizException("调用客户系统查询用户信息异常");
        }
    }

    /**
     * 从文件中解析数据
     *
     * @return 数据(手机 与 金额)
     * @throws Exception
     */
    private List<PaymentInfo> getFileData(File file) throws Exception {
        List<PaymentInfo> paymentInfoList = new ArrayList<PaymentInfo>();
        List<Object[]> dataList = ExcelUtils.getDataFromExcel(file, 1, 0);
        if (dataList.size() == 0) {
            throw new BizException("文件中没有数据，不需要处理！");
        } else {
            for (Object[] data : dataList) {
                PaymentInfo paymentInfo = new PaymentInfo();
                String data0 = data[0].toString();
                String data1 = data[1].toString();
                String data2 = data[2].toString();
                String data3 = data[3].toString();
                String data4 = data[4].toString();
                if (StringUtils.isBlank(data0) || StringUtils.isBlank(data1) || StringUtils.isBlank(data2)
                        || StringUtils.isBlank(data3) || StringUtils.isBlank(data4)) {
                    throw new BizException("存在空数据！");
                }
                if (data0.indexOf(".") > -1 || data1.indexOf(".") > -1 || data2.indexOf(".") > -1 || data3.indexOf(".") > -1) {
                    throw new BizException("excel内容必须为文本格式！");
                }
                int index = data4.indexOf(".");
                if (index == -1) {
                    throw new BizException("金额必须为数字格式！");
                }
                paymentInfo.setUserId(data0);
                paymentInfo.setUserName(CryptTool.des3Base64Enc(CryptTool.genDESKey(), data1));
                paymentInfo.setPhoneNo(CryptTool.des3Base64Enc(CryptTool.genDESKey(), data2));
                paymentInfo.setBonusLevel(data3);
                paymentInfo.setAmount(BigDecimalUtils.changeY2F(data4));
                paymentInfoList.add(paymentInfo);
            }
        }
        return paymentInfoList;
    }

    private void startBatchInsertThread(final String mchntCode, final String queryOrderNo, final List<PaymentInfo> paymentInfoList) {
        final String userName = SessionUtils.getUserName();
        Runnable biRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    String batchNo = createBatchNo();
                    log.info("代付批量插入开始, 当前批次号为:{};上传人为:{}", batchNo, userName);
                    PaymentInfo brd = new PaymentInfo();
                    brd.setBatchNo(batchNo);
                    brd.setMchntCode(mchntCode);
                    brd.setCreator(userName);
                    brd.setQueryOrderNo(queryOrderNo);
                    batchInsert(paymentInfoList, brd);
                } catch (Exception e) {
                    log.error("代付批量插入异常", e);
                }
            }
        };
        Constants.THREAD_POOL.execute(biRunnable);
    }

    public String createBatchNo() throws Exception {
        String today = DateUtils.getToDay(DateUtils.DATESHORTFORMAT);
        log.info("获取批次号开始, 当天日期: {}; 当前时间: {}", today, DateUtils.getToDay(DateUtils.DATETIMEFORMAT));
        String batchNo = paymentInfoMapper.queryMaxBatchNo(today + "%");
        log.info("获取批次号结束, 查询出的批次号: {}; 当前时间: {}", batchNo, DateUtils.getToDay(DateUtils.DATETIMEFORMAT));
        if (batchNo == null) {
            batchNo = today + "0001";
        } else {
            batchNo = BigDecimalUtils.add(batchNo, "1");
        }
        return batchNo;
    }

    public void batchInsert(List<PaymentInfo> paymentInfoList, PaymentInfo paymentInfo) throws Exception {
        String batchNo = paymentInfo.getBatchNo();
        String mchntCode = paymentInfo.getMchntCode();
        String creator = paymentInfo.getCreator();
        String queryOrderNo = paymentInfo.getQueryOrderNo();
        int totalSize = paymentInfoList.size();
        String reqTime = DateUtils.getToDay(DateUtils.DATETIMESFORMAT);
        // 入库操作 每100条插入一次
        int perCount = 100;
        List<List<PaymentInfo>> loopList = this.getLoopList(paymentInfoList, perCount);
        int loopCount = loopList.size();
        for (int i = 0; i < loopCount; i++) {
            List<PaymentInfo> subList = loopList.get(i);
            if (subList.size() > 0) {
                for (PaymentInfo brd : subList) {
                    String orderNo = DateUtils.getToDay(DateUtils.DATETIMESFORMAT) + commonMapper.getSeq("SEQ_ORDER_NO_8");
                    brd.setOrderNo(orderNo);
                    brd.setReqTime(new Date());
                    brd.setBatchNo(batchNo);
                    brd.setMchntCode(mchntCode);
                    brd.setCreator(creator);
                    brd.setQueryOrderNo(queryOrderNo);
                }
                paymentInfoMapper.batchInsert(subList);
                int haveInserted = (i == loopCount - 1 ? totalSize : perCount * (i + 1));
                log.info("代付批量插入, 当前第{}次, 共{}次, 已插入{}条, 共{}条", (i + 1), loopCount, haveInserted, totalSize);
            }
        }
    }

    /**
     * 将list切分成多个子集合
     *
     * @param batchRechargeDetails 需要切分的集合
     * @param perCount             每个集合大小
     * @return 切分完的集合
     */
    private List<List<PaymentInfo>> getLoopList(List<PaymentInfo> batchRechargeDetails, int perCount) {
        List<List<PaymentInfo>> list = new ArrayList<List<PaymentInfo>>();
        int loopCount = batchRechargeDetails.size() / perCount + 1;
        for (int i = 0; i < loopCount; i++) {
            int fromIndex = i * perCount;
            int toIndex;
            if (i == loopCount - 1) {
                toIndex = batchRechargeDetails.size();
            } else {
                toIndex = fromIndex + perCount;
            }
            List<PaymentInfo> subBatchRechargeDetails = batchRechargeDetails.subList(fromIndex, toIndex);
            list.add(subBatchRechargeDetails);
        }
        return list;
    }

    /**
     * 根据批次号查询所有数据
     *
     * @param paymentInfo 批次号
     * @return 结果集
     */
    @Override
    public List<PaymentInfo> queryListForDownLoad(PaymentInfo paymentInfo) {
        List<PaymentInfo> list = paymentInfoMapper.queryListForDownLoad(paymentInfo);
        doWithList(list);
        return list;
    }

    /**
     * 字段解密、脱敏，金额转换处理
     *
     * @param list 结果集
     */
    private void doWithList(List<PaymentInfo> list) {
        if (null != list && list.size() > 0) {
            try {
                for (PaymentInfo paymentInfo : list) {
                    paymentInfo.setUserName(decrypt(paymentInfo.getUserName()));
                    paymentInfo.setPhoneNo(decrypt(paymentInfo.getPhoneNo()));
                    paymentInfo.setBankCardNo(FieldUtils.hideBankCardNo(decrypt(paymentInfo.getBankCardNo())));
                    paymentInfo.setIdentityCardNo(FieldUtils.hideCertNo(decrypt(paymentInfo.getIdentityCardNo())));
                    paymentInfo.setAmount(BigDecimalUtils.changeF2Y(paymentInfo.getAmount()));
                }
            } catch (Exception e) {
                log.error("解密信息异常:", e);
            }
        }
    }

    /**
     * 中断后补代付
     *
     * @param batchNo 批次号
     */
    @Override
    public void paymentAgain(String batchNo) throws Exception {
        String status = paymentInfoMapper.checkAuditState(batchNo);
        if (!"04".equals(status)) {
            log.error("批次号:{}，未执行过的订单不能补发", batchNo);
            throw new BizException("未执行过的订单不能补发");
        }
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setBatchNo(batchNo);
        Date time = DateUtils.parse(batchNo.substring(0, 8));
        paymentInfo.setBeginDate(time);
        List<PaymentInfo> list = paymentInfoMapper.paymentAgain(paymentInfo);
        if (null == list || list.size() == 0) {
            log.error("批次号:{}，未查询到需要补发的记录", batchNo);
            throw new BizException("未查询到需要补发的记录");
        }
        for (PaymentInfo item : list) {
            item.setIdentityCardNo(CryptTool.des3Base64Dec(CryptTool.genDESKey(), item.getIdentityCardNo()));
            item.setUserName(CryptTool.des3Base64Dec(CryptTool.genDESKey(), item.getUserName()));
            item.setPhoneNo(CryptTool.des3Base64Dec(CryptTool.genDESKey(), item.getPhoneNo()));
            item.setBankCardNo(CryptTool.des3Base64Dec(CryptTool.genDESKey(), item.getBankCardNo()));
        }
        batchPayment(list);
    }

    /**
     * 查询审核时间
     *
     * @param batchNo 批次号
     */
    public PaymentInfo queryCheckDate(String batchNo) {
        return paymentInfoMapper.queryCheckDate(batchNo);
    }

    /**
     * 确认文件上传解析
     *
     * @param originFileName  文件名称
     * @param dfsFullFilename dfs全路径
     * @param dfsGroupName    dfs分组
     */
    @Override
    public void confirmUploadFile(String originFileName, String dfsFullFilename, String dfsGroupName) {
        File file = null;
        int i = fastDFSPoolUtil.download(dfsGroupName, dfsFullFilename, downloadTempDir + originFileName);
        if (i == -1) {
            log.error("dfsFullFilename：{}，下载文件异常！", dfsFullFilename);
            throw new BizException("下载文件异常");
        }
        try {
            file = new File(downloadTempDir + originFileName);
            if (StringUtils.isBlank(originFileName)) {
                throw new BizException("文件名为空！");
            }
            String[] aFileName2 = originFileName.split("\\.");
            if (aFileName2.length != 2 || StringUtils.isBlank(aFileName2[0])) {
                throw new BizException("文件名有误！");
            }
            String queryOrderNo = aFileName2[0];
            String mchntCode = getPaymentMchntCode();
            if (!"xls".equals(aFileName2[1])) {
                throw new BizException("文件后缀名必须为xls！");
            }
            //根绝查询编号查询原数据
            PaymentInfo paymentInfo = new PaymentInfo();
            paymentInfo.setQueryOrderNo(queryOrderNo);
            List<PaymentInfo> oldList = paymentInfoMapper.queryPaymentDetail(paymentInfo);
            if (null == oldList || oldList.size() == 0) {
                throw new BizException("未找到原始数据,请核对文件名！");
            }
            List<PaymentInfo> paymentInfoList = getConfirmFileData(file);
            //数据比对
            if (oldList.size() != paymentInfoList.size()) {
                throw new BizException("数据条数不匹配");
            }
            boolean flag;
            for (PaymentInfo payment : paymentInfoList) {
                flag = false;
                for (PaymentInfo oldPayment : oldList) {
                    if (oldPayment.getUserId().equals(payment.getUserId())
                            && oldPayment.getAmount().equals(payment.getAmount())) {
                        flag = true;
                        oldPayment.setUserId("");//清空ID  标示此记录已被匹配过
                        break;
                    }
                }
                if (!flag) {
                    throw new BizException("用户ID:" + payment.getUserId() + "用户信息或金额不匹配");
                }
            }
            PaymentInfo updatePayment = new PaymentInfo();
            updatePayment.setBatchNo(oldList.get(0).getBatchNo());
            updatePayment.setAuditor(SessionUtils.getUserName());
            updatePayment.setStatus("02");//02 已审核
            if (paymentInfoMapper.updateAuditStateByBatchNo(updatePayment) <= 0) {
                throw new BusiException("处理操作错误！");
            }
        } catch (BizException be) {
            log.error("上传解析确认文件失败", be);
            throw new BizException(be.getCode());
        } catch (Exception e) {
            log.error("上传解析确认文件失败异常", e);
            throw new BizException("上传解析确认文件失败异常");
        } finally {
            try {
                if (null != file) {
                    FileUtils.forceDelete(file);
                }
            } catch (Exception e) {
                log.error("确认文件删除异常", e);
            }
        }
    }

    /**
     * 从文件中解析数据
     *
     * @return 数据(手机 与 金额)
     * @throws Exception
     */
    private List<PaymentInfo> getConfirmFileData(File file) throws Exception {
        List<PaymentInfo> paymentInfoList = new ArrayList<PaymentInfo>();
        List<Object[]> dataList = ExcelUtils.getDataFromExcel(file, 1, 0);
        if (dataList.size() == 0) {
            throw new BizException("文件中没有数据，不需要处理！");
        } else {
            for (Object[] data : dataList) {
                PaymentInfo paymentInfo = new PaymentInfo();
                String data0 = data[0].toString();
                String data1 = data[1].toString();
                String data2 = data[2].toString();
                String data3 = data[3].toString();
                String data4 = data[4].toString();
                if (StringUtils.isBlank(data0) || StringUtils.isBlank(data1) || StringUtils.isBlank(data2)
                        || StringUtils.isBlank(data3) || StringUtils.isBlank(data4)) {
                    throw new BizException("存在空数据！");
                }
                if (data0.indexOf(".") > -1 || data1.indexOf(".") > -1 || data2.indexOf(".") > -1 || data3.indexOf(".") > -1) {
                    throw new BizException("excel内容必须为文本格式！");
                }
                int index = data4.indexOf(".");
                if (index == -1) {
                    throw new BizException("金额必须为数字格式！");
                }
                paymentInfo.setAmount(BigDecimalUtils.changeY2F(data4));
                paymentInfo.setUserId(data0);
                paymentInfoList.add(paymentInfo);
            }
        }
        return paymentInfoList;
    }
}
