package com.oriental.manage.controller.reserve;

import com.oriental.check.commons.util.BeanMapperUtil;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.pojo.inform.*;
import com.oriental.manage.service.reserve.ReserveInformService;
import com.oriental.reserve.model.ReserveBalance;
import com.oriental.reserve.model.ReserveInfo;
import com.oriental.reserve.model.ResponseModel;
import com.oriental.reserve.model.config.AccountOrgCodeDto;
import com.oriental.reserve.model.inform.*;
import com.oriental.reserve.model.message.DepositDetailDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jiaof on 2016/12/21.
 */
@Slf4j
@Controller
@RequestMapping("/reserve/data")
public class ReserveInformController {

    @Autowired
    private ReserveInformService reserveInformService;
    /**
     * 系统通知及数据催报
     * @return
     */
    @RequestMapping("/inform/init")
    public String informInit(){
        return "reserve/searchReserveInform";
    }

    /**
     * 系统通知及数据催报
     * @return
     */
    @RequestMapping("/inform/search")
    @ResponseBody
    public ResponseDTO<Pagination<InformSearchRespDto>> informSearch(Pagination<InformSearchRespDto> pagination, InformSearchReqPojo  baseModel){
        ResponseDTO<Pagination<InformSearchRespDto>> responseDTO = new ResponseDTO<Pagination<InformSearchRespDto>>();
        try{
            InformSearchReqDto dto =BeanMapperUtil.objConvert(baseModel,InformSearchReqDto.class);
            reserveInformService.informSearch(pagination,dto,responseDTO);
            Pagination<InformSearchRespDto> lists = responseDTO.getObject();
            pagination.setList(lists.getList());
            responseDTO.setSuccess(true);
            responseDTO.setObject(pagination);
        }catch(Exception e){
            log.error("查询失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("查询失败");
        }
        return responseDTO;

    }

    /**
     * 银行账户信息差异查询初始化
     * @return
     */
    @RequestMapping("/bankDiffer/init")
    public String bankDifferInit(){
        return "reserve/searchReserveBankDiffer";
    }

    /**
     * 银行账户信息差异查询
     * @return
     */
    @RequestMapping("/bankDiffer/search")
    @ResponseBody
    public ResponseDTO<Pagination<BankDifferSearchRespDto>> bankSearch(Pagination<BankDifferSearchRespDto> pagination,  BankDifferSearchRespPojo baseModel){
        ResponseDTO<Pagination<BankDifferSearchRespDto>> responseDTO=new ResponseDTO<Pagination<BankDifferSearchRespDto>>();
        try{
            BankDifferSearchReqDto dto = BeanMapperUtil.objConvert(baseModel,BankDifferSearchReqDto.class);
            reserveInformService.bankDifferSearch(pagination,dto,responseDTO);
            List<BankDifferSearchRespDto> bankDifferSearchRespDtoList = responseDTO.getObject().getList();
            pagination.setList(bankDifferSearchRespDtoList);
            responseDTO.setSuccess(true);
            responseDTO.setObject(pagination);
        }catch(Exception e){
            log.error("查询失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("查询失败");
        }
        return responseDTO;

    }


    /**
     * 逾期未核对数据页面初始化
     * @return
     */
    @RequestMapping("/overdue/init")
    public String overdueInit(){
        log.info("*******searchReserveOverdueController****");
        return "reserve/searchReserveOverdue";
    }

    /**
     * 逾期未核对数据详情页面
     * @return
     */
    @RequestMapping("/overdue/toOverdueDetail")
    public String toOverdueDetail(){
        log.info("*******overdueDetail****");
        return "reserve/searchReserveOverdueDetail";
    }

    /**
     * 逾期未核对数据查询
     * @return
     */
    @RequestMapping("/overdue/overdueSearch")
    @ResponseBody
    public ResponseDTO<Pagination<OverdueSearchRespDto>> overdueSearch(Pagination<OverdueSearchRespDto> pagination, OverdueSearchRespPojo baseModel){
        ResponseDTO<Pagination<OverdueSearchRespDto>> responseDTO = new ResponseDTO<>();
        try{
            OverdueSearchReqDto dto =BeanMapperUtil.objConvert(baseModel,OverdueSearchReqDto.class);
            reserveInformService.overdueSearch(pagination,dto,responseDTO);
            List<OverdueSearchRespDto> overdueSearchRespDtoList = responseDTO.getObject().getList();

            pagination.setList(overdueSearchRespDtoList);
            responseDTO.setSuccess(true);
            responseDTO.setObject(pagination);
        }catch(Exception e){
            log.error("查询失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("查询失败");
        }
        return responseDTO;

    }

    /**
     * 逾期未核对数据说明提交
     */
    @RequestMapping("/overdue/overdueSubmit")
    @ResponseBody
    public ResponseModel<String> overdueSubmit(@RequestBody CheckDiffDescDto checkDiffDescDto){
        log.info("*******overdueSubmit****，说明提交，{}",checkDiffDescDto);
        ResponseModel<String> response = reserveInformService.submitCheckDesc(checkDiffDescDto);
        return response;
    }

    /**
     * 差异数据页面初始化
     * @return
     */
    @RequestMapping("/differ/init")
    public String differInit(){
        log.info("*******controller****");
        return "reserve/searchReserveDiffer";
    }

    /**
     * 差异数据详情页面
     * @return
     */
    @RequestMapping("/differ/toDifferDetail")
    public String toDifferDetail(){
        log.info("*******differDetail****");
        return "reserve/searchReserveDifferDetail";
    }

    /**
     * 差异数据查询
     * @return
     */
    @RequestMapping("/differ/differSearch")
    @ResponseBody
    public ResponseDTO<Pagination<DifferSearchRespDto>> differSearch(Pagination<DifferSearchRespDto> pagination,DefferSearchReqPojo baseModel){
        log.info("查询参数：pagination:{},baseModel:{}", pagination, baseModel);
        ResponseDTO<Pagination<DifferSearchRespDto>> responseDTO=new ResponseDTO<>();
        try{
            DifferSearchReqDto dto = BeanMapperUtil.objConvert(baseModel,DifferSearchReqDto.class);
            reserveInformService.differSearch(pagination,dto,responseDTO);
            List<DifferSearchRespDto> differSearchRespDtoList = responseDTO.getObject().getList();

            pagination.setList(differSearchRespDtoList);
            responseDTO.setSuccess(true);
            responseDTO.setObject(pagination);
        }catch(Exception e){
            log.error("查询失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("查询失败");
        }
        return responseDTO;

    }

    /**
     * 差异数据说明提交
     */
    @RequestMapping("/differ/differSubmit")
    @ResponseBody
    public ResponseModel<String> differSubmit(@RequestBody CheckDiffDescDto checkDiffDescDto){
        log.info("*******differSubmit****，说明提交，{}",checkDiffDescDto);
        ResponseModel<String> response = reserveInformService.submitCheckDesc(checkDiffDescDto);
        return response;

    }

    /**
     * 结算流水分包缺失信息初始化页面
     * @return
     */
    @RequestMapping("/settlementMissing/init")
    public String settlementMissingInit(){
        log.info("*******settlementMissingInit****");
        return "reserve/searchSettlementMissing";
    }

    /**
     * 结算流水分包缺失信息查询页面
     * @return
     */
    @RequestMapping("/settlementMissing/search")
    @ResponseBody
    public ResponseDTO<Pagination<LackPkgSearchRespDto>> settlementMissingSearch(Pagination<LackPkgSearchRespDto> pagination,  LackPkgSearchReqPojo baseModel){
        ResponseDTO<Pagination<LackPkgSearchRespDto>> responseDTO = new ResponseDTO<>();
        try{
            LackPkgSearchReqDto dto =BeanMapperUtil.objConvert(baseModel,LackPkgSearchReqDto.class);
            reserveInformService.lackPkgSearch(pagination,dto,responseDTO);
            List<LackPkgSearchRespDto> lackPkgSearchRespDtoList = responseDTO.getObject().getList();

            pagination.setList(lackPkgSearchRespDtoList);
            responseDTO.setSuccess(true);
            responseDTO.setObject(pagination);
        }catch(Exception e){
            log.error("查询失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("查询失败");
        }
        return responseDTO;

    }

    /**
     * @return
     */
    @RequestMapping("/settlementMissing/toMissingDetail")
    public String toMissingDetail(){
        log.info("*******toMissingDetail****");
        return "reserve/searchMissingDetail";
    }

    /**
     *
     */
    @RequestMapping("/settlementMissing/missingSubmit")
    @ResponseBody
    public void missingSubmit(){
        log.info("*******missingSubmit****");

    }

}
