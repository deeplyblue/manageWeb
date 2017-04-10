package com.oriental.manage.service.reserve;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.reserve.api.config.BankAccountDetailInterface;
import com.oriental.reserve.model.ResponseModel;
import com.oriental.reserve.model.config.BankAccountDetailDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tanglu on 2016/12/30 0030.
 * 银行信息录入
 */
@Slf4j
@Service
public class BankInformationService {

    @Autowired
    private BankAccountDetailInterface bankAccountDetailInterface;

    /**
     * 查询银行信息录入
     *
     */

    public void query(Pagination<BankAccountDetailDto> pagination, BankAccountDetailDto bankAccountDetailDto, ResponseDTO<Pagination<BankAccountDetailDto>> responseDTO) {
        ResponseModel<List<BankAccountDetailDto>> responseModel = bankAccountDetailInterface.queryBankAccountDetail(bankAccountDetailDto);
        ReserveUtil.responseQueryHandle(responseModel, pagination, responseDTO, bankAccountDetailDto, "查询失败");
    }

    public void add(BankAccountDetailDto bankAccountDetailDto, ResponseDTO<String> responseDTO) {
        ResponseModel<BankAccountDetailDto> responseModel = bankAccountDetailInterface.createBankAccountDetail(bankAccountDetailDto);
        ReserveUtil.responseHandle(responseModel, responseDTO, "新增成功");
    }

    public void update(BankAccountDetailDto bankAccountDetailDto, ResponseDTO<String> responseDTO) {
        ResponseModel<BankAccountDetailDto> responseModel = bankAccountDetailInterface.updateBankAccountDetail(bankAccountDetailDto);
        ReserveUtil.responseHandle(responseModel, responseDTO, "修改成功");
    }

    public void delete(BankAccountDetailDto bankAccountDetailDto, ResponseDTO<String> responseDTO) {
        ResponseModel<BankAccountDetailDto> responseModel = bankAccountDetailInterface.deleteBankAccountDetail(bankAccountDetailDto);
        ReserveUtil.responseHandle(responseModel, responseDTO, "删除成功");
    }

    public void selectAll(ResponseDTO<Map<String, BankAccountDetailDto>> responseDTO) {
        BankAccountDetailDto bankAccountDetailDto = new BankAccountDetailDto();
        ResponseModel<List<BankAccountDetailDto>> responseModel= bankAccountDetailInterface.initBankAccountDetail(bankAccountDetailDto);
        if (responseModel.isSuccess()){
            Map<String,BankAccountDetailDto> item = new HashMap<>();
            for (BankAccountDetailDto bank : responseModel.getResult()){
                item.put(bank.getAccountNo(),bank);
            }
            responseDTO.setObject(item);
            responseDTO.setSuccess(true);
        }else {
            responseDTO.setSuccess(false);
            responseDTO.setMsg(responseModel.getErrorMsg());
        }
    }

}
