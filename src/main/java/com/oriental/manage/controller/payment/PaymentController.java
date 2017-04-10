package com.oriental.manage.controller.payment;

import com.oriental.manage.core.enums.RedisKey;
import com.oriental.manage.core.exception.BusiException;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.smsUtils.SmsUtils;
import com.oriental.manage.core.system.Constants;
import com.oriental.manage.core.system.log.OperateLogger;
import com.oriental.manage.core.utils.DateUtils;
import com.oriental.manage.core.utils.RandomMath;
import com.oriental.manage.core.utils.SessionUtils;
import com.oriental.manage.pojo.base.UserInfo;
import com.oriental.manage.pojo.payment.PaymentInfo;
import com.oriental.manage.pojo.redis.Response;
import com.oriental.manage.service.base.IUserInfoService;
import com.oriental.manage.service.base.IUserLoginService;
import com.oriental.manage.service.payment.IPaymentService;
import com.oriental.manage.service.redis.RedisService;
import com.oriental.manage.service.redis.impl.RedisServiceImpl;
import com.oriental.paycenter.commons.exception.BizException;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.webbuilder.utils.file.Resources;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * User: hj
 * Date: 2016/6/13 20:15
 * Desc:
 */
@Slf4j
@Controller
@RequestMapping("payment")
public class PaymentController {

    @Autowired
    private IPaymentService paymentService;

    @Autowired
    private IUserLoginService userLoginService;

    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private SmsUtils smsUtils;
    @Autowired
    private Constants constants;


    @Autowired
    private RedisServiceImpl redisService;

    @RequestMapping("init")
    public String init() {
        return "payment/searchPaymentBatchList";
    }

    @OperateLogger(content = "代付信息查询", operationType = OperateLogger.OperationType.R)
    @RequestMapping("searchPaymentBatchList")
    @RequiresPermissions("payment_search")
    @ResponseBody
    public ResponseDTO<Pagination<PaymentInfo>> queryPage(Pagination<PaymentInfo> pagination, PaymentInfo paymentInfo) {
        ResponseDTO<Pagination<PaymentInfo>> responseDTO = new ResponseDTO<Pagination<PaymentInfo>>();
        try {
            paymentService.queryPage(pagination, paymentInfo);
            responseDTO.setSuccess(true);
            responseDTO.setObject(pagination);
        } catch (Exception e) {
            log.error("查询代付失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("查询异常，请稍后再尝试");
        }
        return responseDTO;
    }

    @RequestMapping("toQueryDetail")
    public String toQueryDetail() {

        return "payment/searchPaymentDetailList";
    }

    @OperateLogger(content = "代付信息明细查询", operationType = OperateLogger.OperationType.R)
    @RequestMapping("queryPaymentDetail")
    @ResponseBody
    public ResponseDTO<Pagination<PaymentInfo>> queryPaymentDetail(Pagination<PaymentInfo> pagination, PaymentInfo paymentInfo) {
        ResponseDTO<Pagination<PaymentInfo>> responseDTO = new ResponseDTO<Pagination<PaymentInfo>>();
        try {
            String batch = paymentInfo.getBatchNo();
            if (StringUtils.isBlank(batch)) {
                throw new BizException("批次号为空");
            }
            Date time = DateUtils.parse(paymentInfo.getBatchNo().substring(0, 8));
            paymentInfo.setBeginDate(time);
            paymentService.queryPaymentDetail(pagination, paymentInfo);
            responseDTO.setSuccess(true);
            responseDTO.setObject(pagination);
        } catch (Exception e) {
            log.error("查询代付明细失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("查询异常，请稍后再尝试");
        }
        return responseDTO;
    }

    @RequestMapping("toUpload")
    @RequiresPermissions("payment_upload")
    public String toUpload() {
        return "payment/upload";
    }

    @OperateLogger(content = "下载模板", operationType = OperateLogger.OperationType.R)
    @RequestMapping("downloadTemplate")
    @ResponseBody
    public ResponseDTO<String> downloadTemplate(HttpServletRequest request, HttpServletResponse response) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            String path = constants.getExcelPath() + "batch_recharge_template.xls";
            log.info("代付模板文件路径为 : {};", path);
            File file = new File(path);
            if (!file.exists()) {
                log.error("代付模板文件不存在");
                responseDTO.setSuccess(false);
                responseDTO.setMsg("download error");
                return responseDTO;
            }
            String fileName = "文件名.xls";
            downloadFileToWeb(request, response, file, fileName, false);
        } catch (Exception e) {
            log.error("下载模本文件有异常", e);
        }
        return null;
    }

    public boolean downloadFileToWeb(HttpServletRequest request, HttpServletResponse response,
                                     File file, String fileName, boolean deleteFlag) {
        boolean result = false;
        if (null == file) {
            return false;
        } else if (!file.exists() && !file.isDirectory()) {
            return false;
        }
        OutputStream out = null;
        BufferedInputStream bin = null;
        FileInputStream fis = null;
        try {
            response.setCharacterEncoding("utf-8");
            request.setCharacterEncoding("utf-8");
            response.addHeader("Content-Disposition", "attachment; filename="
                    + encodeChineseDownloadFileName(request, fileName == null ? file.getName() : fileName));
            response.setContentType("application/octet-stream");
            out = response.getOutputStream();
            fis = new FileInputStream(file.getPath());
            bin = new BufferedInputStream(fis);
            byte[] buf = new byte[1024];
            int len;
            while ((len = bin.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            result = true;
        } catch (Exception e) {
            log.error("下载模板文件异常：", e);
        } finally {
            IOUtils.closeQuietly(bin);
            IOUtils.closeQuietly(out);
            IOUtils.closeQuietly(fis);
            if (file.exists() && deleteFlag) {
                file.delete();
            }
        }
        return result;
    }

    /**
     * 对文件流输出下载的中文文件名进行编码 屏蔽各种浏览器版本的差异性
     *
     * @throws UnsupportedEncodingException
     */
    public static String encodeChineseDownloadFileName(HttpServletRequest request, String pFileName) throws UnsupportedEncodingException {
        String filename;
        String agent = request.getHeader("USER-AGENT");
        if (null != agent) {
            if (agent.contains("Firefox")) {
                //Firefox
                filename = "=?UTF-8?B?" + (new String(Base64.encodeBase64(pFileName.getBytes("UTF-8")))) + "?=";
            } else if (agent.contains("Chrome")) {
                //Chrome
                filename = new String(pFileName.getBytes(), "ISO-8859-1");
            } else {
                //IE7+
                filename = java.net.URLEncoder.encode(pFileName, "UTF-8");
                //替换空格
                filename = StringUtils.replace(filename, "+", "%20");
            }
        } else {
            filename = pFileName;
        }
        return filename;
    }

    @RequestMapping("check")
    @ResponseBody
    public ResponseDTO<String> check(String auditState, String batchNo, String verifyCode, String smsVerifyCode) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        log.info("call check method auditState:{}, batchNo:{}", auditState, batchNo);
        try {
            if (StringUtils.isBlank(auditState) || StringUtils.isBlank(batchNo)) {
                throw new BusiException("请求参数错误！");
            }
            if (!"04".equals(auditState) && !"03".equals(auditState)) {
                throw new BusiException("处理类型错误！");
            }
            //防并发处理
            if (checkRepeat(batchNo)) {
                throw new BusiException("操作频繁，请稍后再尝试！");
            }
            String status = paymentService.checkAuditState(batchNo);
            if ("03".equals(auditState) && "04".equals(status)) {
                throw new BusiException("此批次订单已被处理！");
            }
            if ("04".equals(auditState) && !"02".equals(status)) {
                throw new BusiException("此批次订单已处理或未被确认！");
            }
            if ("04".equals(auditState)) {
                if (StringUtils.isBlank(verifyCode)) {
                    throw new BusiException("登入密码为空！");
                }
                UserInfo userInfo = new UserInfo();
                userInfo.setUserName(SessionUtils.getUserName());
                userInfo.setUserPwd(userInfoService.enctryptPwd(verifyCode));
                if (!userLoginService.validateUserLogin(userInfo)) {
                    throw new BusiException("登入密码错误！");
                }
                //验证短信验证码
                if (StringUtils.isBlank(smsVerifyCode) || !smsVerifyCode.equals(SessionUtils.getSmsVerifyCode())) {
                    throw new BusiException("短信验证码有误！");
                }
            }
            PaymentInfo paymentInfo = new PaymentInfo();
            paymentInfo.setOperator(SessionUtils.getUserName());
            paymentInfo.setBatchNo(batchNo);
            paymentInfo.setStatus(auditState);
            if (!paymentService.updateAuditStateByBatchNo(paymentInfo)) {
                throw new BusiException("处理操作错误！");
            }
            //发起代付
            if ("04".equals(auditState)) {
                paymentService.startBatchPaymentThread(paymentInfo);
            }
            responseDTO.setSuccess(true);
        } catch (Exception e) {
            log.error("查询代付明细失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }

    @RequestMapping("uploadFile")
    @RequiresPermissions("payment_upload")
    @ResponseBody
    public ResponseDTO<String> uploadFile(String originFileName, String dfsFullFilename, String dfsGroupName) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            //防并发处理
            if (checkRepeat(originFileName)) {
                throw new BusiException("操作频繁，请稍后再尝试！");
            }
            paymentService.uploadFile(originFileName, dfsFullFilename, dfsGroupName);
            responseDTO.setSuccess(true);
        } catch (BizException be) {
            log.error("上传解析文件失败", be);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(be.getCode());
        } catch (Exception e) {
            log.error("上传解析文件失败异常", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }


    @RequestMapping("checkDownloadResultFile")
    @ResponseBody
    public ResponseDTO<String> checkDownloadResultFile(String batchNo) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            if (StringUtils.isBlank(batchNo)) {
                throw new BizException("批次号为空！");
            }
            Date time = DateUtils.parse(batchNo.substring(0, 8));
            PaymentInfo paymentInfo = paymentService.queryCheckDate(batchNo);
            if (null != paymentInfo && null != paymentInfo.getOperateTime()) {
                time = paymentInfo.getOperateTime();
            }
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(time);
            calendar.add(Calendar.DATE, 1); //把日期往后增加一天
            String ss = DateUtils.format(calendar.getTime(), DateUtils.DATESHORTFORMAT) + "090000";
            Date timeLimit = DateUtils.parse(ss, DateUtils.DATETIMESFORMAT);
            Date now = new Date();
            if (timeLimit.after(now)) {
                log.info("批次号:{},timeLimit:{}，currentDate:{},暂时不能下载结果文件", batchNo, timeLimit, now);
                throw new BizException("暂时不能下载结果文件！ 请于付款后T+1日9时后再尝试！");
            }
            responseDTO.setSuccess(true);
        } catch (BizException be) {
            log.error("校验是否可以下载结果文件失败", be);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(be.getCode());
        } catch (Exception e) {
            log.error("校验是否可以下载结果文件异常", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }

    /**
     * 下载结果文件
     *
     * @param batchNo  批次号
     * @param request  请求对象
     * @param response 响应对象
     * @return 结果文件
     */
    @RequestMapping("downloadResultFile")
    @RequiresPermissions("payment_downloadResultFile")
    public ResponseEntity<byte[]> downloadResultFile(String batchNo, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (StringUtils.isBlank(batchNo)) {
                throw new BizException("批次号为空！");
            }
            Date time = DateUtils.parse(batchNo.substring(0, 8));
            PaymentInfo queryPaymentInfo = paymentService.queryCheckDate(batchNo);
            if (null != queryPaymentInfo && null != queryPaymentInfo.getOperateTime()) {
                time = queryPaymentInfo.getOperateTime();
            }
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(time);
            calendar.add(Calendar.DATE, 1); //把日期往后增加一天
            String ss = DateUtils.format(calendar.getTime(), DateUtils.DATESHORTFORMAT) + "090000";
            Date timeLimit = DateUtils.parse(ss, DateUtils.DATETIMESFORMAT);
            if (timeLimit.after(new Date())) {
                throw new BizException("暂时不能下载结果文件！");
            }
            PaymentInfo paymentInfo = new PaymentInfo();
            paymentInfo.setBatchNo(batchNo);
            // 由于查询关联T_ORDER_MAIN表需要带时间条件
            paymentInfo.setBeginDate(DateUtils.parse(batchNo.substring(0, 8)));
            List<PaymentInfo> list = paymentService.queryListForDownLoad(paymentInfo);
            if (null == list) {
                throw new BizException("未查到数据！");
            }
            File file = this.getResultFile(list);
            //下载
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", file.getName());
            return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
        } catch (BizException be) {
            log.error("下载文件失败", be);
            return null;
        } catch (Exception e) {
            log.error("下载文件异常", e);
            return null;
        }
    }

    /**
     * 生成结果Excel
     *
     * @param list 数据集
     * @return 结果文件对象
     * @throws WriteException
     * @throws IOException
     */
    public File getResultFile(List<PaymentInfo> list) throws WriteException, IOException {
        PaymentInfo paymentInfo = list.get(0);
        String queryOrderNo = paymentInfo.getQueryOrderNo();
        String mchntCode = paymentInfo.getMchntCode();
        File file = new File(queryOrderNo + "_result.xls");
        //创建工作薄
        WritableWorkbook workbook = Workbook.createWorkbook(file);
        //创建新的一页
        WritableSheet sheet = workbook.createSheet("sheet", 0);
        //添加标题栏
        sheet.addCell(new Label(0, 0, "客户ID"));
        sheet.addCell(new Label(1, 0, "姓名"));
        sheet.addCell(new Label(2, 0, "联系电话"));
        sheet.addCell(new Label(3, 0, "中奖等级"));
        sheet.addCell(new Label(4, 0, "中奖金额"));
        sheet.addCell(new Label(5, 0, "银行卡号"));
        sheet.addCell(new Label(6, 0, "交易状态"));
        sheet.addCell(new Label(7, 0, "是否退款"));
        for (int i = 0; i < list.size(); i++) {
            sheet.addCell(new Label(0, i + 1, list.get(i).getUserId()));
            sheet.addCell(new Label(1, i + 1, list.get(i).getUserName()));
            sheet.addCell(new Label(2, i + 1, list.get(i).getPhoneNo()));
            sheet.addCell(new Label(3, i + 1, list.get(i).getBonusLevel()));
            sheet.addCell(new Label(4, i + 1, list.get(i).getAmount()));
            sheet.addCell(new Label(5, i + 1, list.get(i).getIdentityCardNo()));
            sheet.addCell(new Label(6, i + 1, list.get(i).getTransStatus()));
            if ("0".equals(list.get(i).getRefundFlag())) {
                sheet.addCell(new Label(7, i + 1, "未退款"));
            } else if ("1".equals(list.get(i).getRefundFlag())) {
                sheet.addCell(new Label(7, i + 1, "已退款"));
            } else {
                sheet.addCell(new Label(7, i + 1, ""));
            }
        }
        //把创建的内容写入到输出流中，并关闭输出流
        workbook.write();
        workbook.close();
        return file;
    }

    /**
     * 中断补代付
     *
     * @param batchNo 批次号
     * @return 操作结果
     */
    @RequestMapping("paymentAgain")
    @ResponseBody
    @RequiresPermissions("payment_paymentAgain")
    public ResponseDTO<String> paymentAgain(String batchNo) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            if (StringUtils.isBlank(batchNo)) {
                throw new BizException("批次号不能为空");
            }
            //防并发处理
            if (checkRepeat(batchNo)) {
                throw new BusiException("操作频繁，请稍后再尝试！");
            }
            paymentService.paymentAgain(batchNo);
            responseDTO.setSuccess(true);
        } catch (BizException be) {
            log.error("中断补代付失败", be);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(be.getCode());
        } catch (Exception e) {
            log.error("中断补代付异常", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }

    @RequestMapping("smsVerifyCode")
    @ResponseBody
    public ResponseDTO sendSmsVerifyCode() {
        ResponseDTO responseDTO = new ResponseDTO();
        String template = "您的手机验证码为:{smsVerifyCode}，有效期：5分钟【亿付数字】";
        try {
            UserInfo userInfo = userInfoService.getUserByName(SessionUtils.getUserName());
            if (userInfo == null || StringUtils.isBlank(userInfo.getUserMobile())) {
                throw new BusiException("用户不存在，或者手机号未配置");
            }
            //防并发处理
            if (checkRepeat(userInfo.getUserMobile())) {
                throw new BusiException("操作频繁，请稍后再尝试！");
            }
            SessionUtils.setSmsVerifyCode(RandomMath.getNum(6));
            String temp = template.replace("{smsVerifyCode}", SessionUtils.getSmsVerifyCode());
            smsUtils.send(userInfo.getUserMobile(), temp);
            responseDTO.setSuccess(true);
        } catch (Exception e) {
            log.error("短信发送失败", e);
            responseDTO.setSuccess(false);
        }
        return responseDTO;
    }

    @RequestMapping("toConfirmUpload")
    @RequiresPermissions("payment_confirmUpload")
    public String toConfirmUpload() {
        return "payment/confirmUpload";
    }

    @RequestMapping("confirmUploadFile")
    @RequiresPermissions("payment_confirmUpload")
    @ResponseBody
    public ResponseDTO<String> confirmUploadFile(String originFileName, String dfsFullFilename, String dfsGroupName) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            //防并发处理
            if (checkRepeat(originFileName)) {
                throw new BusiException("操作频繁，请稍后再尝试！");
            }
            paymentService.confirmUploadFile(originFileName, dfsFullFilename, dfsGroupName);
            responseDTO.setSuccess(true);
        } catch (BizException be) {
            log.error("上传解析确认文件失败", be);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(be.getCode());
        } catch (Exception e) {
            log.error("上传解析确认文件失败异常", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getMessage());
        }
        return responseDTO;
    }

    /**
     * 防止并发处理
     *
     * @param batch 批次号
     * @return true:重复操作  false:非重复操作
     */
    public boolean checkRepeat(String batch) {
        StringBuilder key = new StringBuilder()
                .append("PAYMENT.CHECK")
                .append(".").append(batch);
        Response<Boolean> response = redisService.setDataLock(key.toString(), key.toString(), 5);
        if (response != null && (!response.isSuccess() || !response.getResult())) {
            log.error("Redis key:{},正在处理中", key.toString());
            return true;
        }
        return false;
    }
}