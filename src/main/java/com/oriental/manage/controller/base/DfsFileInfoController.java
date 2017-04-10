package com.oriental.manage.controller.base;

import com.oriental.manage.core.dfsUtils.FastDFSPoolUtil;
import com.oriental.manage.core.exception.BusiException;
import com.oriental.manage.pojo.base.DfsFileInfo;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;

/**
 * Created by lupf on 2016/6/17.
 */
@Slf4j
@Controller
@RequestMapping("base/dfsFileInfo")
public class DfsFileInfoController {

    @Autowired
    private FastDFSPoolUtil fastDFSPoolUtil;

    @Value("#{cfgProperties['downloadTempDir']}")
    @Setter
    private String downloadTempDir;

    @RequestMapping("download")
    public ResponseEntity<byte[]> download(DfsFileInfo dfsFileInfo) {
        ResponseEntity<byte[]> responseEntity;
        File file = new File(downloadTempDir + dfsFileInfo.getLocalFilename());
        try {
            int i = fastDFSPoolUtil.download(dfsFileInfo.getDfsGroupname(), dfsFileInfo.getDfsFullFilename(),
                    downloadTempDir + dfsFileInfo.getLocalFilename());
            if (i == -1) {
                throw new BusiException("file not found!");
            }

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", file.getName());

            responseEntity = new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.OK);
        } catch (BusiException e) {
            responseEntity = new ResponseEntity<byte[]>(HttpStatus.NOT_FOUND);
            log.error("dfs文件未找到", e);
        } catch (Exception e) {
            responseEntity = new ResponseEntity<byte[]>(HttpStatus.EXPECTATION_FAILED);
            log.error("dfs文件下载失败", e);
        } finally {
            try {
                FileUtils.forceDelete(file);
            } catch (Exception e) {
                log.error("文件删除失败", e);
            }
        }
        return responseEntity;
    }
}
