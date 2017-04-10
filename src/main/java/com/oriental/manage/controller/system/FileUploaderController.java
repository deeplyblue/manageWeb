package com.oriental.manage.controller.system;

import com.oriental.manage.core.dfsUtils.FastDFSPoolUtil;
import com.oriental.manage.core.exception.BusiException;
import com.oriental.manage.core.response.ResponseDTO;
import com.oriental.manage.core.utils.SessionUtils;
import com.oriental.manage.pojo.base.DfsFileInfo;
import com.oriental.manage.pojo.base.UserInfo;
import com.oriental.manage.service.base.IDfsFileInfoService;
import com.oriental.manage.service.base.IUserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * Created by lupf on 2016/5/31.
 */
@Slf4j
@Controller
@RequestMapping("system/fileUploader")
public class FileUploaderController {

    @Autowired
    private FastDFSPoolUtil fastDFSPoolUtil;
    @Autowired
    private IDfsFileInfoService dfsFileInfoService;
    @Autowired
    private IUserInfoService userInfoService;
    private final String[] allowFileTypes = new String[]{"pdf", "jpg", "jpeg"};
    /**
     * 禁止的文件类型
     */
    private final String[] limitFileTypes = new String[]{"exe", "sh"};
    /**
     * 限制的文件大小
     */
    private final long limitSize = 1024 * 1024 * 10;

    @RequestMapping("upload")
    @ResponseBody
    public ResponseDTO upload(MultipartFile file, String busiType, String remark) {
        String fileName = file.getOriginalFilename();
        log.info("access file,name:{}", fileName);

        String fileType = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        log.info("access file,type:{}", fileType);

        long fileSize = file.getSize();
        log.info("access file,size:{}", fileSize);

        ResponseDTO responseDTO = new ResponseDTO();
        DfsFileInfo dfsFileInfo = new DfsFileInfo();
        try {

            UserInfo userInfo = userInfoService.getUserByName(SessionUtils.getUserName());
            dfsFileInfo.setId(UUID.randomUUID().toString());
            dfsFileInfo.setCompanyType(userInfo.getUserType());
            dfsFileInfo.setCompanyCode(userInfo.getCompanyCode());
            dfsFileInfo.setBusiType(busiType);
            dfsFileInfo.setLocalFilename(file.getOriginalFilename());
            dfsFileInfo.setRemark(remark);
            Date now = new Date();
            dfsFileInfo.setCreateTime(now);
            dfsFileInfo.setLastUpdTime(now);
            dfsFileInfo.setOperator(SessionUtils.getUserName());

            if (StringUtils.isBlank(fileType) || Arrays.toString(limitFileTypes).indexOf(fileType) != -1) {
                throw new BusiException("不支持的文件类型！");
            }
            if (StringUtils.isBlank(String.valueOf(fileSize)) || fileSize > limitSize) {
                throw new BusiException("文件大小超限！");
            }

            Map<String, String> uploadResult = fastDFSPoolUtil.uploadByStream(file.getBytes());


            if (uploadResult.size() > 0) {
                dfsFileInfo.setDfsFullFilename(uploadResult.get("REMOTE_FILE_NAME"));
                dfsFileInfo.setDfsGroupname(uploadResult.get("GROUP_NAME"));
                dfsFileInfo.setStatus("0");
            } else {
                dfsFileInfo.setDfsFullFilename("don't know");
                dfsFileInfo.setDfsGroupname("error");
                dfsFileInfo.setStatus("1");
                //直接抛出异常，前端认为文件上传失败 @TODO 考虑优化
                throw new BusiException("DFS文件上传失败");
            }

            responseDTO.setSuccess(true);
            responseDTO.setObject(dfsFileInfo);
        } catch (BusiException e) {
            log.error("上传文件受限", e);

            dfsFileInfo.setDfsFullFilename("don't know");
            dfsFileInfo.setDfsGroupname("error");
            dfsFileInfo.setStatus("1");

            responseDTO.setSuccess(false);
            responseDTO.setMsg(e.getDesc());
        } catch (Exception e) {
            log.error("操作失败", e);
            responseDTO.setSuccess(false);
            responseDTO.setMsg("文件上传异常");
        } finally {
            dfsFileInfoService.insert(dfsFileInfo);
        }

        return responseDTO;
    }
}
