package com.oriental.manage.controller.reserve;

import com.oriental.manage.core.paging.Pagination;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.utils.BigDecimalUtils;
import com.oriental.reserve.api.card.PrepaidCardInterface;
import com.oriental.reserve.model.ResponseModel;
import com.oriental.reserve.model.card.PrepaidCardDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 预付卡管理
 *
 * @author wuxg
 * @version 1.0.0 @createTime: 2016/6/14 16:33
 * @since 1.0
 */
@Controller
@Slf4j
@RequestMapping("/reserve/prepaidCard")
public class PrepaidCardManageController {

    @Autowired
    private PrepaidCardInterface prepaidCardInterface;

    @RequestMapping("/init")
    public String init() {
        return "reserve/prepaidCard/searchPrepaidCard";
    }

    @RequestMapping("/toAdd")
    public String toAdd() {
        return "reserve/prepaidCard/addPrepaidCard";
    }

    @RequestMapping("/toUpdate")
    @RequiresPermissions("prepaidCard_update")
    public String toUpdate() {
        return "reserve/prepaidCard/updatePrepaidCard";
    }

    @ResponseBody
    @RequestMapping("/search")
    @RequiresPermissions("prepaidCard_search")
    public ResponseDTO<Pagination<PrepaidCardDto>> queryPage(@RequestBody Pagination<PrepaidCardDto> pagination) {
        ResponseDTO<Pagination<PrepaidCardDto>> responseDTO = new ResponseDTO<>();
        try {
            log.info("查询预付卡信息:{}", pagination);
            ResponseModel<List<PrepaidCardDto>> responseModel = prepaidCardInterface.queryPrepaidCard(pagination.getQueryBean());

            log.info("查询预付卡结果:{}", responseModel);
            if (responseModel.isSuccess()) {
                for (int i = 0; i < responseModel.getResult().size(); i++) {
                    responseModel.getResult().get(i).setPrepaidCardAmt(BigDecimalUtils.changeF2Y(responseModel.getResult().get(i).getPrepaidCardAmt()));
                }
                pagination.setRowCount(0);
                if (responseModel.getResult().size()>0){
                    pagination.setRowCount(responseModel.getResult().get(0).getRowCount());
                    pagination.setPageNum(pagination.getQueryBean().getPageNum());
                    pagination.setPageSize(pagination.getQueryBean().getPageSize());
                    responseDTO.setSumObject(responseModel.getResult().get(0));
                }
                pagination.setList(responseModel.getResult());
                responseDTO.setSuccess(true);
                responseDTO.setObject(pagination);
            } else {
                responseDTO.setSuccess(false);
                responseDTO.setMsg(responseModel.getErrorMsg());
            }
        } catch (Exception e) {
            log.error("查询失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("系统繁忙");
        }
        return responseDTO;
    }


    @ResponseBody
    @RequiresPermissions("prepaidCard_update")
    @RequestMapping("/update")
    public ResponseDTO<String> updatePrepaidCard(@RequestBody PrepaidCardDto prepaidCard) {
        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
        try {
            log.info("修改预付卡信息:{}", prepaidCard);
            prepaidCard.setPrepaidCardAmt(BigDecimalUtils.changeY2F(prepaidCard.getPrepaidCardAmt()));
            ResponseModel<String> responseModel = prepaidCardInterface.updatePrepaidCard(prepaidCard);
            log.info("修改预付卡结果:{}", responseModel);
            if (responseModel.isSuccess()) {
                responseDTO.setSuccess(true);
                responseDTO.setObject("修改成功");
            } else {
                responseDTO.setSuccess(false);
                responseDTO.setMsg(responseModel.getErrorMsg());
            }
        } catch (Exception e) {
            log.error("修改失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("系统繁忙");
        }
        return responseDTO;
    }
}
