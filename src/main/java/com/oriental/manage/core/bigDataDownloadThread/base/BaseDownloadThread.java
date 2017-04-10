package com.oriental.manage.core.bigDataDownloadThread.base;

import com.oriental.manage.core.dfsUtils.FastDFSPoolUtil;
import com.oriental.manage.core.fileUtils.FileDownAjax;
import com.oriental.manage.core.fileUtils.FileUtilsExt;
import com.oriental.manage.core.utils.ApplicationContextUtil;
import com.oriental.manage.core.utils.SessionUtils;
import com.oriental.manage.dao.base.CommonMapper;
import com.oriental.manage.pojo.base.BaseModel;
import com.oriental.manage.pojo.download.UserDownload;
import com.oriental.manage.service.download.IUserDownloadService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.webbuilder.office.excel.config.Header;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by lupf on 2016/8/10.
 */
@Slf4j
public abstract class BaseDownloadThread<T> implements Runnable {

    @Setter
    protected T queryModel;
    @Setter
    protected String fileNameOut;
    protected FileDownAjax fileDownAjax = ApplicationContextUtil.getBean(FileDownAjax.class);
    private FastDFSPoolUtil fastDFSPoolUtil = ApplicationContextUtil.getBean(FastDFSPoolUtil.class);
    private IUserDownloadService userDownloadService = ApplicationContextUtil.getBean(IUserDownloadService.class);
    private CommonMapper commonMapper = ApplicationContextUtil.getBean(CommonMapper.class);

    protected int pageSize = 10000;
    protected int rowCount = 0;
    private int pageCount;
    private File out;
    private List<File> fileList = new ArrayList();
    private UserDownload userDownload = new UserDownload();
    protected LinkedList<Header> tHeaders = new LinkedList<>();

    @Override
    public void run() {
        try {
            log.info("下载线程启动-config");
            config(tHeaders);

            log.info("下载前,插入请求记录");
            userDownload.setId(commonMapper.getCommonId("seq_common_8"));
            userDownload.setFileName(fileNameOut);
            userDownload.setUserName(SessionUtils.getUserName());
            userDownload.setStatus("A");
            userDownloadService.add(userDownload);

            log.info("构建下载文件开始");
            buildFile();

            log.info("上传文件到dfs");
            uploadToDfs();
        } catch (Exception e) {
            log.error("下载文件失败:{}", e);
            userDownload.setStatus("C");
        } finally {
            userDownloadService.updateById(userDownload);
            if (fileList.size() > 0) {
                for (File file : fileList) {
                    fileDownAjax.forceDelete(file);
                }
            }
            fileDownAjax.forceDelete(out);
        }
    }

    protected abstract void config(LinkedList<Header> tHeaders);

    private void buildFile() throws Exception {

        queryRowCount();
        calculatePageCount();

        if (queryModel instanceof BaseModel) {
            ((BaseModel) queryModel).setPageNum(1);
            ((BaseModel) queryModel).setPageSize(pageSize);
        }

        for (int i = 1; i <= pageCount; i++) {
            File temp = buildTempFile(i);
            fileList.add(temp);
        }
    }

    protected abstract File buildTempFile(int pageNum) throws Exception;

    private void calculatePageCount() {
        if (rowCount % pageSize != 0) {
            pageCount = 1 + (rowCount / pageSize);
        } else {
            pageCount = rowCount / pageSize;
        }
    }

    protected abstract void queryRowCount();

    private void uploadToDfs() throws IOException {

        out = fileDownAjax.touch(fileNameOut);
        FileUtilsExt.zipFile(fileList, out.getAbsolutePath());
        Map<String, String> res = fastDFSPoolUtil.uploadByPath(out.getAbsolutePath());

        if (res.containsKey("REMOTE_FILE_NAME")) {
            userDownload.setStatus("B");
            userDownload.setRemoteFileName(res.get("REMOTE_FILE_NAME"));
        } else {
            userDownload.setStatus("C");
        }

    }
}
