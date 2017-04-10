package com.oriental.manage.controller.reserve;

import com.oriental.manage.core.exception.BusiException;
import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.service.reserve.BankInformationService;
import com.oriental.reserve.api.sys.SystParticipantsMsgInterface;
import com.oriental.reserve.model.ResponseModel;
import com.oriental.reserve.model.config.BankAccountDetailDto;
import com.oriental.reserve.model.sys.SystParticipantsMsgDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by tanglu on 2016/12/20 0020.
 * Desc：银行信息录入
 */

@Slf4j
@Controller
@RequestMapping("reserve/bank/info")
public class BankInformationController {

    @Autowired
    private BankInformationService bankInformationService;

    @Autowired
    private SystParticipantsMsgInterface systParticipantsMsgInterface;

    @RequestMapping("/init")
    public String init(){
        return "reserve/searchBankInformation";
    }


    @RequestMapping("/toAdd")
    public String toAdd(){
        return "reserve/addBankInformation";
    }


    @RequestMapping("/toUpdate")
    public String toUpdate(){
        return "reserve/updateBankInformation";
    }

    @RequestMapping("/add")
    @ResponseBody
    public ResponseDTO<String> add( BankAccountDetailDto bankAccountDetailDto) throws Exception {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            log.info("新增银行信息:{}",bankAccountDetailDto);
            bankAccountDetailDto.setOrgNo("Z12931000018");
            bankInformationService.add(bankAccountDetailDto,responseDTO);
        } catch (Exception e) {
            log.error("新增银行信息失败", e);
        }
        return responseDTO;
    }

    @RequestMapping("/update")
    @ResponseBody
    public ResponseDTO<String>  update(BankAccountDetailDto baseModel) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        log.info("修改银行信息:{}",baseModel);
        try {
            bankInformationService.update(baseModel,responseDTO);
        } catch (Exception e) {
            log.error("修改银行信息失败", e.getMessage());
        }
        return responseDTO;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public ResponseDTO<String> delete(BankAccountDetailDto baseModel){
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            bankInformationService.delete(baseModel,responseDTO);
        } catch (Exception e) {
            log.error("删除银行信息失败", e);
        }
        return responseDTO;
    }

    @RequestMapping("/search")
    @ResponseBody
    public ResponseDTO<Pagination<BankAccountDetailDto>> search(Pagination<BankAccountDetailDto> pagination, BankAccountDetailDto bankAccountDetailDto){
        ResponseDTO<Pagination<BankAccountDetailDto>> responseDTO = new ResponseDTO<Pagination<BankAccountDetailDto>>();
        try{
            bankInformationService.query(pagination,bankAccountDetailDto,responseDTO);
            log.info("查询银行信息录入:{},{}", bankAccountDetailDto, pagination);
            responseDTO.setSuccess(true);
            responseDTO.setObject(pagination);
        }catch (Exception e){
            log.error("查询银行信息录入失败", e);
        }
        return responseDTO;
    }

    @RequestMapping("/getPartner")
    @ResponseBody
    public ResponseDTO<Map<String,SystParticipantsMsgDto>> initPartnerInfo(){
        ResponseDTO<Map<String,SystParticipantsMsgDto>> responseDTO = new ResponseDTO<Map<String, SystParticipantsMsgDto>>();
        try {
            SystParticipantsMsgDto systParticipantsMsgDto = new SystParticipantsMsgDto();
            systParticipantsMsgDto.setDeleteFlag("0");
            systParticipantsMsgDto.setType("02");
            ResponseModel<List<SystParticipantsMsgDto>> responseModel = systParticipantsMsgInterface.query(systParticipantsMsgDto);
            log.info("查询数据:{}",responseModel.getResult());

            Map<String,SystParticipantsMsgDto> reserveInfoMap = new HashMap<String, SystParticipantsMsgDto>();
            for(SystParticipantsMsgDto participantsMsgDto : responseModel.getResult()){
                reserveInfoMap.put(participantsMsgDto.getCode(),participantsMsgDto);
            }

      /*    Map<String,SystParticipantsMsgDto> reserveInfoMap = new HashMap<String, SystParticipantsMsgDto>();
            for (SystParticipantsMsgDto participantsMsgDto : responseModel.getResult()){
                if(participantsMsgDto.getType().equals("02")){
                    reserveInfoMap.put(participantsMsgDto.getCode(),participantsMsgDto);
                }
            }*/
            responseDTO.setSuccess(true);
            responseDTO.setObject(reserveInfoMap);
            log.info("备付金响应信息:{}",responseDTO);
        } catch (BusiException e){
            log.error("系统异常",e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getDesc());
        } catch (Exception e){
            log.error("初始化失败",e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("初始化失败");
        }
        return responseDTO;
    }

    @ResponseBody
    @RequestMapping("/init/bank")
    public ResponseDTO<Map<String,BankAccountDetailDto>> initBankDetail(){
        ResponseDTO<Map<String,BankAccountDetailDto>> responseDTO = new ResponseDTO<>();
        try {
            bankInformationService.selectAll(responseDTO);
        }catch (Exception e){
            log.error("获取账户信息错误:",e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("获取失败");
        }
        return responseDTO;
    }

}
