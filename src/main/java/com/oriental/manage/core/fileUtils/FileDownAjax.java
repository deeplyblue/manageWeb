package com.oriental.manage.core.fileUtils;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by lupf on 2016/6/15.
 */
@Slf4j
@Repository("FileDownAjax")
@Scope
public class FileDownAjax {

    @Value("#{cfgProperties['downloadTempDir']}")
    @Setter
    private String downloadTempDir;

    public File touch(String fileName) throws IOException {
        File tempFile = new File(downloadTempDir + fileName);
        FileUtils.touch(tempFile);

        return tempFile;
    }

    public HttpHeaders getHttpHeaders(File file) throws UnsupportedEncodingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", file.getName());
        headers.set("x-filename", URLEncoder.encode(file.getName(), "UTF-8"));

        return headers;
    }

    public ResponseEntity<byte[]> getResponseEntity(File file) throws IOException {

        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), getHttpHeaders(file), HttpStatus.OK);
    }

    public ResponseEntity<byte[]> getResponseEntityFail() {

        return new ResponseEntity<byte[]>(HttpStatus.EXPECTATION_FAILED);
    }

    public void forceDelete(File file) {
        try {
            FileUtils.forceDelete(file);
        } catch (IOException e) {
            log.error("删除文件失败", e);
        }
    }
}