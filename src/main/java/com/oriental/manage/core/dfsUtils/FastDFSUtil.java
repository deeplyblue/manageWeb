package com.oriental.manage.core.dfsUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.csource.fastdfs.*;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
@Slf4j
public class FastDFSUtil {
    /**
     * fastdfs 配置文件路径
     */
    private String fastdfsConfigFilePath = "fastdfs/client.conf";

    /**
     * 初始化DFS配置
     */
    public void init() throws IOException {
        fastdfsConfigFilePath =
                ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + fastdfsConfigFilePath).getAbsolutePath();
        try {
            ClientGlobal.init(fastdfsConfigFilePath);
            System.out.println("DFS init finished success");
        } catch (Exception e) {
            log.error("系统异常", e);

        }
    }

    /**
     * 下载文件
     *
     * @param groupName      下载组，默认为Group1
     * @param remoteFilename DFS上面的文件名
     * @param localFileName  下载到本地的文件名
     * @return int           文件下载结果
     */
    public int download(String groupName, String remoteFilename, String localFileName) {
        int result = -1;
        TrackerServer trackerServer = null;
        try {
            TrackerClient trackerClient = new TrackerClient();
            trackerServer = trackerClient.getConnection();
            StorageClient storageClient = new StorageClient(trackerServer, null);
            // 下载文件
            groupName = StringUtils.isEmpty(groupName) ? "group1" : groupName;
            result = storageClient.download_file(groupName, remoteFilename, localFileName);
            System.out.println("download result: group_name:" + groupName);
            System.out.println("remoteFilename:" + remoteFilename);
            System.out.println("localFileName:" + localFileName);
            System.out.println("download success");
        } catch (Exception e) {
            log.error("系统异常", e);
            log.info("下载文件失败， remoteFilename:" + remoteFilename);
            log.info("localFileName:" + localFileName);
        } finally {
            closeTrackerServer(trackerServer);
        }
        return result;
    }

    /**
     * 删除文件
     *
     * @param groupName      组名
     * @param remoteFilename 文件名
     * @return 执行结果
     */
    public int delete(String groupName, String remoteFilename) {
        int result = -1;
        TrackerServer trackerServer = null;
        try {
            TrackerClient trackerClient = new TrackerClient();
            trackerServer = trackerClient.getConnection();
            StorageClient storageClient = new StorageClient(trackerServer, null);
            // 删除文件
            groupName = StringUtils.isEmpty(groupName) ? "group1" : groupName;
            result = storageClient.delete_file(groupName, remoteFilename);
        } catch (Exception e) {
            log.error("系统异常", e);
            log.info("删除文件失败， remoteFilename:" + remoteFilename);
        } finally {
            closeTrackerServer(trackerServer);
        }
        return result;
    }

    /**
     * 上传文件到FastDFS服务器
     *
     * @param localFileStream 文件流
     * @return 远程返回的文件名称和group名称
     */
    public Map<String, String> uploadByStream(String localFileStream) {
        Map<String, String> dfsMap = new HashMap<String, String>();
        TrackerServer trackerServer = null;
        try {
            // 初始化连接
            TrackerClient trackerClient = new TrackerClient();
            trackerServer = trackerClient.getConnection();
            StorageClient storageClient = new StorageClient(trackerServer, null);

            byte[] fileBytes = Base64.decodeBase64(localFileStream);
            String[] results = storageClient.upload_file(fileBytes, null, null);
            if (results == null) {
                System.out.println("FastDFS文件上传失败,Error Code:" + storageClient.getErrorCode());
            } else {
                System.out.println("文件上传成功;GROUP_NAME:" + results[0] + " REMOTE_FILE_NAME:" + results[1]);
                // 远程返回的文件名称
                dfsMap.put("GROUP_NAME", results[0]);
                // 文件的groupId
                dfsMap.put("REMOTE_FILE_NAME", results[1]);
            }
        } catch (Exception e) {
            log.info("上传文件到FastDFS服务器异常");
            log.error("系统异常", e);
        } finally {
            closeTrackerServer(trackerServer);
        }
        return dfsMap;
    }

    /**
     * 上传文件到FastDFS服务器
     *
     * @param localFilePath 文件流
     * @return 远程返回的文件名称和group名称
     */
    public Map<String, String> uploadByPath(String localFilePath) {
        Map<String, String> dfsMap = new HashMap<String, String>();
        TrackerServer trackerServer = null;
        System.out.println("000000");
        try {
            // 初始化连接
            TrackerClient trackerClient = new TrackerClient();
            System.out.println("tracker client is OK");
            trackerServer = trackerClient.getConnection();
            System.out.println("tracker server is OK");
            StorageClient storageClient = new StorageClient(trackerServer, null);
            System.out.println("storage client is OK");
            String[] results = storageClient.upload_file(localFilePath, null, null);
            if (results == null) {
                System.out.println("FastDFS文件上传失败,Error Code:" + storageClient.getErrorCode());
            } else {
                System.out.println("文件上传成功;GROUP_NAME:" + results[0] + " REMOTE_FILE_NAME:" + results[1]);
                // 远程返回的文件名称
                dfsMap.put("GROUP_NAME", results[0]);
                // 文件的groupId
                dfsMap.put("REMOTE_FILE_NAME", results[1]);
            }
        } catch (Exception e) {
            log.info("22222");
            log.error("系统异常",e);
        } finally {
            log.info("11111");
            closeTrackerServer(trackerServer);
        }
        return dfsMap;
    }

    /**
     * 关闭队列服务
     *
     * @param trackerServer trackerServer队列服务
     */
    public void closeTrackerServer(TrackerServer trackerServer) {
        // 退出前,一定要将队列服务关闭
        try {
            if (trackerServer != null)
                trackerServer.close();
        } catch (Exception e) {
           log.info("关闭队列服务异常");
            log.error("系统异常", e);
        }
    }

    /**
     * 关闭数据服务
     *
     * @param storageServer storageServer数据服务
     */
    public void closeStorageServer(StorageServer storageServer) {
        // 退出前,一定要将数据服务关闭
        try {
            if (storageServer != null)
                storageServer.close();
        } catch (Exception e) {
            log.info("关闭数据服务异常");
            log.error("系统异常", e);
        }
    }

    /**
     * 关闭DFS服务
     *
     * @param trackerServer 队列服务
     * @param storageServer 数据服务
     */
    public void closeDFSServer(TrackerServer trackerServer, StorageServer storageServer) {
        try {
            closeTrackerServer(trackerServer);
            closeStorageServer(storageServer);
        } catch (Exception ioe) {
            log.info("DFS关闭连接异常:" + ioe.getStackTrace());
        }
    }

}
