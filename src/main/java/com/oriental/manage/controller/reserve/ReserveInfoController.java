package com.oriental.manage.controller.reserve;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Author: Yang xp
 * Date: 2016/6/3
 * Time: 17:30
 * Desc：备付金基本维护信息
 */
@Slf4j
@Controller
@RequestMapping("/reserve/info")
public class ReserveInfoController  {

//    @Autowired
//    private ReserveInfoService reserveInfoService;
//
//    @RequestMapping("/init")
//    public String init(){
//        return "reserve/searchReserveInfo";
//    }
//
//    @RequiresPermissions("reserveInfo_add")
//    @RequestMapping("/toAdd")
//    public String toAdd(){
//        return "reserve/addReserveInfo";
//    }
//
//    @RequiresPermissions("reserveInfo_update")
//    @RequestMapping("/toUpdate")
//    public String toUpdate(){
//        return "reserve/updateReserveInfo";
//    }
//
//
//    @RequestMapping("/add")
//    @ResponseBody
//    public ResponseDTO<String> add( ReserveInfo baseModel) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidKeySpecException {
//        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
//        try {
//            log.info("新增信息:{}",baseModel);
//            reserveInfoService.createReserve(baseModel,responseDTO);
//        } catch (Exception e) {
//            log.error("新增失败", e);
//            responseDTO.setSuccess(false);
//            responseDTO.setMsg("新增失败");
//        }
//        return responseDTO;
//    }
//
//    @RequestMapping("/update")
//    @ResponseBody
//    public ResponseDTO<String>  update( ReserveInfo baseModel) {
//        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
//        log.info("修改信息:{}",baseModel);
//        try {
//            reserveInfoService.updateReserve(baseModel,responseDTO);
//        } catch (Exception e) {
//            log.error("更新失败", e);
//            responseDTO.setSuccess(false);
//            responseDTO.setMsg("修改失败");
//        }
//        return responseDTO;
//    }
//
//    @RequestMapping("/search")
//    @ResponseBody
//    @RequiresPermissions("reserveInfo_search")
//    public ResponseDTO<Pagination<ReserveInfo>> search(Pagination<ReserveInfo> pagination, ReserveInfo baseModel) {
//        ResponseDTO<Pagination<ReserveInfo>> responseDTO=new ResponseDTO<Pagination<ReserveInfo>>();
//        try{
//            reserveInfoService.searchReserve(pagination,baseModel);
//            log.info("查询信息:{},{}",baseModel,pagination);
//            responseDTO.setSuccess(true);
//            responseDTO.setObject(pagination);
//        }catch(Exception e){
//            log.error("查询失败", e);
//            responseDTO.setSuccess(false);
//            responseDTO.setMsg("查询失败");
//        }
//        return responseDTO;
//
//    }
//    @RequiresPermissions("reserveInfo_delete")
//    @RequestMapping("/delete")
//    @ResponseBody
//    public ResponseDTO<String> delete(ReserveInfo baseModel){
//        ResponseDTO<String> responseDTO = new ResponseDTO<String>();
//        try {
//            reserveInfoService.deleteReserve(baseModel,responseDTO);
//        } catch (Exception e) {
//            log.error("删除失败", e);
//            responseDTO.setSuccess(false);
//            responseDTO.setMsg(e.getMessage());
//        }
//        return responseDTO;
//    }
//
//    @RequestMapping("/init/reserve")
//    @ResponseBody
//    public ResponseDTO<Map<String,ReserveInfo>> initReserveInfo(){
//        ResponseDTO<Map<String,ReserveInfo>> responseDTO = new ResponseDTO<Map<String, ReserveInfo>>();
//        try {
//            List<ReserveInfo> reserveInfoList = reserveInfoService.searchReserve();
//            Map<String,ReserveInfo> reserveInfoMap = new HashMap<String, ReserveInfo>();
//            for (ReserveInfo reserveInfo : reserveInfoList){
//                reserveInfoMap.put(reserveInfo.getAccountNo(),reserveInfo);
//            }
//            responseDTO.setSuccess(true);
//            responseDTO.setObject(reserveInfoMap);
//            log.info("备付金响应信息:{}",responseDTO);
//        } catch (BusiException e){
//            log.error("系统异常",e);
//            responseDTO.setSuccess(false);
//            responseDTO.setMsg(e.getDesc());
//        } catch (Exception e){
//            log.error("初始化失败",e);
//            responseDTO.setSuccess(false);
//            responseDTO.setMsg("初始化失败");
//        }
//        return responseDTO;
//    }
}
