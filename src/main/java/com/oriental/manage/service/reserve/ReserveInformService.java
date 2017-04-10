package com.oriental.manage.service.reserve;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.reserve.api.inform.ReserveInformInterface;
import com.oriental.reserve.model.ResponseModel;
import com.oriental.reserve.model.inform.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author: dongyang
 * Date: 2017/1/4
 * Time: 10:31
 * Desc：系统通知及预警
 */
@Service
@Slf4j
public class ReserveInformService {

    @Autowired
    private ReserveInformInterface reserveInformInterface;

    /**
     * 数据差异
     * @param pagination
     * @param responseDTO
     */
    public void differSearch(Pagination<DifferSearchRespDto> pagination, DifferSearchReqDto differSearchReqDto, ResponseDTO<Pagination<DifferSearchRespDto>> responseDTO){
        log.info("请求查询参数DifferSearchReqDto：{}",differSearchReqDto);
        ResponseModel<List<DifferSearchRespDto>> responseModel = reserveInformInterface.differSearch(differSearchReqDto);
        log.info("查询结果responseModel：{}",responseModel);

        DifferSearchRespDto mode = new DifferSearchRespDto();
        mode.setPageNum(differSearchReqDto.getPageNum());
        mode.setPageSize(differSearchReqDto.getPageSize());
        ReserveUtil.responseQueryHandle(responseModel,pagination,responseDTO,mode,"查询失败");
    }

    /**
     * 银行账户信息差异查询
     * @param pagination
     * @param responseDTO
     */
    public void bankDifferSearch(Pagination<BankDifferSearchRespDto> pagination, BankDifferSearchReqDto bankDifferSearchReqDto, ResponseDTO<Pagination<BankDifferSearchRespDto>> responseDTO){
        log.info("请求查询参数BankDifferSearchReqDto：{}",bankDifferSearchReqDto);
        ResponseModel<List<BankDifferSearchRespDto>> responseModel = reserveInformInterface.bankDifferSearch(bankDifferSearchReqDto);
        log.info("查询结果responseModel：{}",responseModel);

        BankDifferSearchRespDto mode = new BankDifferSearchRespDto();
        mode.setPageNum(bankDifferSearchReqDto.getPageNum());
        mode.setPageSize(bankDifferSearchReqDto.getPageSize());
        ReserveUtil.responseQueryHandle(responseModel,pagination,responseDTO,mode,"查询失败");
    }

    /**
     * 逾期未核对数据信息查询
     * @param pagination
     * @param responseDTO
     */
    public void overdueSearch(Pagination<OverdueSearchRespDto> pagination, OverdueSearchReqDto overdueSearchReqDto, ResponseDTO<Pagination<OverdueSearchRespDto>> responseDTO){
        log.info("请求查询参数OverdueSearchReqDto：{}",overdueSearchReqDto);
        ResponseModel<List<OverdueSearchRespDto>> responseModel = reserveInformInterface.overdueSearch(overdueSearchReqDto);
        log.info("查询结果responseModel：{}",responseModel);

        OverdueSearchRespDto mode = new OverdueSearchRespDto();
        mode.setPageNum(overdueSearchReqDto.getPageNum());
        mode.setPageSize(overdueSearchReqDto.getPageSize());
        ReserveUtil.responseQueryHandle(responseModel,pagination,responseDTO,mode,"查询失败");
    }

    /**
     * 查询备付金系统通知及数据催报信息
     * @param pagination
     * @param responseDTO
     */
    public void informSearch(Pagination<InformSearchRespDto> pagination, InformSearchReqDto informSearchReqDto, ResponseDTO<Pagination<InformSearchRespDto>> responseDTO){
        log.info("请求查询参数InformSearchReqDto：{}",informSearchReqDto);
        ResponseModel<List<InformSearchRespDto>> responseModel = reserveInformInterface.informSearch(informSearchReqDto);
        log.info("参数pagination：{}",pagination);
        log.info("查询结果responseModel：{}",responseModel);

        InformSearchRespDto mode = new InformSearchRespDto();
        mode.setPageNum(informSearchReqDto.getPageNum());
        mode.setPageSize(informSearchReqDto.getPageSize());
        ReserveUtil.responseQueryHandle(responseModel,pagination,responseDTO,mode,"查询失败");
    }

    /**
     * 结算流水分包缺失信息查询
     * @param pagination
     * @param responseDTO
     */
    public void lackPkgSearch(Pagination<LackPkgSearchRespDto> pagination, LackPkgSearchReqDto lackPkgSearchReqDto, ResponseDTO<Pagination<LackPkgSearchRespDto>> responseDTO){
        log.info("请求查询参数LackPkgSearchReqDto：{}",lackPkgSearchReqDto);
        ResponseModel<List<LackPkgSearchRespDto>> responseModel = reserveInformInterface.lackPkgSearch(lackPkgSearchReqDto);
        log.info("查询结果responseModel：{}",responseModel);

        LackPkgSearchRespDto mode = new LackPkgSearchRespDto();
        mode.setPageNum(lackPkgSearchReqDto.getPageNum());
        mode.setPageSize(lackPkgSearchReqDto.getPageSize());
        ReserveUtil.responseQueryHandle(responseModel,pagination,responseDTO,mode,"查询失败");
    }


    public ResponseModel<String> submitCheckDesc(CheckDiffDescDto checkDiffDescDto){

        ResponseModel<String> response = reserveInformInterface.submitCheckDesc(checkDiffDescDto);
        return response;
    }

}
