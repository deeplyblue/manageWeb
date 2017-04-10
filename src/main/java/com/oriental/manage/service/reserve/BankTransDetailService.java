package com.oriental.manage.service.reserve;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.utils.SessionUtils;
import com.oriental.reserve.api.trans.BankTransDetailInterface;
import com.oriental.reserve.api.trans.PeriodCheckDetailInterface;
import com.oriental.reserve.model.ResponseModel;
import com.oriental.reserve.model.business.PeriodCheckDto;
import com.oriental.reserve.model.trans.BankTransDetailDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author: Yang xp
 * Date: 2016/6/29
 * Time: 18:09
 * Desc：银行交易
 */
@Slf4j
@Service
public class BankTransDetailService {

    @Autowired
    private BankTransDetailInterface bankTransDetailInterface;

    @Autowired
    private PeriodCheckDetailInterface periodCheckDetailInterface;


    /**
     * 查询银行信息
     *
     * @param pagination  查询信息
     * @param responseDTO 返回信息
     */
    public void queryBankTransDetail(Pagination<BankTransDetailDto> pagination, ResponseDTO<Pagination<BankTransDetailDto>> responseDTO) {
        ResponseModel<List<BankTransDetailDto>> responseModel = bankTransDetailInterface.queryBankTransDetail(pagination.getQueryBean());
        ReserveUtil.responseQueryHandle(responseModel,pagination,responseDTO,pagination.getQueryBean(),"查询银行交易明细失败");
    }

    /**
     * 修改备付金交易信息
     * @param bankTransDetail 形象信息
     * @param responseDTO 是否成功
     */
    public void updateBankTransDetail(BankTransDetailDto bankTransDetail,ResponseDTO<String> responseDTO){
        ResponseModel<String> responseModel = bankTransDetailInterface.updateBankTransDetail(bankTransDetail);
        ReserveUtil.responseHandle(responseModel,responseDTO,"操作成功");
    }


    /**
     * 查询出业务数据流水
     * @param bankTransDetail 银行交易
     * @param responseDTO 返回信息
     */
    public void queryPeriodCheck(BankTransDetailDto bankTransDetail, ResponseDTO<Pagination<PeriodCheckDto>> responseDTO){
        Pagination<PeriodCheckDto> pagination = new Pagination<>();
        PeriodCheckDto periodCheck = new PeriodCheckDto();
        periodCheck.setBankTransNo(bankTransDetail.getId());
        periodCheck.setClrAccount(bankTransDetail.getAccountNo());
        periodCheck.setQueryStartDate(bankTransDetail.getQueryStartDate());
        periodCheck.setQueryEndDate(bankTransDetail.getQueryEndDate());
        periodCheck.setCdFlag(bankTransDetail.getCdFlag());
        ResponseModel<List<PeriodCheckDto>> responseModel = periodCheckDetailInterface.queryPeriodCheck(periodCheck);
        ReserveUtil.responseQueryHandle(responseModel,pagination,responseDTO,periodCheck,"查询成功");
    }

    /**
     * 修改核对信息
     * @param periodCheckList 和当前的银行流水核对上的业务数据
     * @param responseDTO 返回信息
     */
    public void updatePeriodCheck(List<PeriodCheckDto> periodCheckList,ResponseDTO<String> responseDTO){
        ResponseModel<String> responseModel = periodCheckDetailInterface.updatePeriodCheck(periodCheckList);
        ReserveUtil.responseHandle(responseModel,responseDTO,"操作成功");
    }

    /**
     * 清除系统自动核对数据
     * @param periodCheck 需要清除
     * @param responseDTO 返回信息
     */
    public void clearPeriodCheck(PeriodCheckDto periodCheck,ResponseDTO<String> responseDTO){
        ResponseModel<String> responseModel = periodCheckDetailInterface.clearPeriodCheck(periodCheck);
        ReserveUtil.responseHandle(responseModel,responseDTO,"操作成功");
    }

    public void queryPeriodCheck(PeriodCheckDto periodCheckDto,ResponseDTO<Pagination<PeriodCheckDto>> responseDTO,Pagination<PeriodCheckDto> pagination){
        ResponseModel<List<PeriodCheckDto>> responseModel = periodCheckDetailInterface.queryPeriodCheckNoBank(periodCheckDto);
        ReserveUtil.responseQueryHandle(responseModel,pagination,responseDTO,pagination.getQueryBean(),"查询成功");
    }

    public void queryBankTrans(PeriodCheckDto periodCheckDto,ResponseDTO<Pagination<BankTransDetailDto>> responseDTO){
        BankTransDetailDto bankTransDetailDto = new BankTransDetailDto();
        bankTransDetailDto.setCheckId(periodCheckDto.getId());
        bankTransDetailDto.setQueryStartDate(periodCheckDto.getQueryStartDate());
        bankTransDetailDto.setQueryEndDate(periodCheckDto.getQueryEndDate());
        bankTransDetailDto.setAccountNo(periodCheckDto.getOtherSideAccountNo());
        ResponseModel<List<BankTransDetailDto>> responseModel = bankTransDetailInterface.queryBankTransDetail(bankTransDetailDto);
        Pagination<BankTransDetailDto> pagination = new Pagination<>();
        ReserveUtil.responseQueryHandle(responseModel,pagination,responseDTO,bankTransDetailDto,"查询成功");
    }

    public void updateBankTransDetail(List<BankTransDetailDto> bankTransDetailDtoList,ResponseDTO<String> responseDTO){
        for (BankTransDetailDto bank : bankTransDetailDtoList){
            bank.setUpdateBy(SessionUtils.getUserName());
        }
        ResponseModel<String> responseModel = bankTransDetailInterface.updateCheckBankTrans(bankTransDetailDtoList);
        ReserveUtil.responseHandle(responseModel,responseDTO,"操作成功");
    }

}
