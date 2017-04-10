package com.oriental.manage.core.dfsUtils;

import com.oriental.manage.core.utils.SessionUtils;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Repository("FastDFSPoolUtil")
@Scope
public class FastDFSPoolUtil {

    /**
     * fastdfs 配置文件路径
     */
    @Value("#{cfgProperties['FASTDFS_CONFI']}")
    @Setter
    private String FASTDFS_CONFI;

    private ObjectPool<TrackerServer> pool;

    /**
     * 初始化DFS配置
     */
    @PostConstruct
    public void init() throws IOException {
        log.info("dfs初始化开始");
        String fastdfsConfigFilePath =
                ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + FASTDFS_CONFI).getAbsolutePath();
        try {
            ClientGlobal.init(fastdfsConfigFilePath);
            initPool();
            log.info("DFS init finished success");
        } catch (Exception e) {
            log.error("dfs初始化异常", e);
        }
    }

    /**
     * 初始化连接池
     */
    public void initPool() {
        PooledObjectFactory<TrackerServer> factory = new FastDFSFactory();
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxIdle(15);
        config.setMaxTotal(20);
        config.setMinIdle(6);
        pool = new GenericObjectPool<TrackerServer>(factory, config);
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
            trackerServer = pool.borrowObject();
            StorageClient storageClient = new StorageClient(trackerServer, null);
            // 下载文件
            groupName = StringUtils.isBlank(groupName) ? "group1" : groupName;
            result = storageClient.download_file(groupName, remoteFilename, localFileName);
            log.info("download result: group_name:" + groupName + " remoteFilename:" + remoteFilename + " localFileName:" + localFileName);
        } catch (Exception e) {
            log.info("下载文件失败， remoteFilename:" + remoteFilename + " localFileName:" + localFileName);
            log.error("dfs文件下载", e);
        } finally {
            closeTrackerServer(trackerServer);
        }
        log.info("dfs-result：{},## 0 means success ##", String.valueOf(result));
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
            trackerServer = pool.borrowObject();
            StorageClient storageClient = new StorageClient(trackerServer, null);
            // 删除文件
            groupName = StringUtils.isBlank(groupName) ? "group1" : groupName;
            result = storageClient.delete_file(groupName, remoteFilename);
        } catch (Exception e) {
            log.info("删除文件失败， remoteFilename:" + remoteFilename);
            log.error("dfs文件删除失败", e);
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
            trackerServer = pool.borrowObject();
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
            log.error("dfs文件上传失败", e);
        } finally {
            closeTrackerServer(trackerServer);
        }
        return dfsMap;
    }

    public Map<String, String> uploadByStream(byte[] localFile) {
        Map<String, String> dfsMap = new HashMap<String, String>();
        TrackerServer trackerServer = null;
        try {
            trackerServer = pool.borrowObject();
            StorageClient storageClient = new StorageClient(trackerServer, null);

            String[] results = storageClient.upload_file(localFile, null, null);
            if (results == null) {
                log.info("FastDFS文件上传失败,Error Code:" + storageClient.getErrorCode());
            } else {
                log.info("文件上传成功;GROUP_NAME:" + results[0] + " REMOTE_FILE_NAME:" + results[1]);
                // 远程返回的文件名称
                dfsMap.put("GROUP_NAME", StringUtils.isBlank(results[0]) ? "group1" : results[0]);
                // 文件的groupId
                dfsMap.put("REMOTE_FILE_NAME", results[1]);
            }
        } catch (Exception e) {
            log.error("dfs文件上传失败", e);
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
        try {
            trackerServer = pool.borrowObject();
            StorageClient storageClient = new StorageClient(trackerServer, null);

            NameValuePair[] meta_list = new NameValuePair[1];
            meta_list[0] = new NameValuePair("author", SessionUtils.getUserName());

            String[] results = storageClient.upload_file(localFilePath, null, meta_list);
            if (results == null) {
                log.info("FastDFS文件上传失败,Error Code:" + storageClient.getErrorCode());
            } else {
                log.info("SUCCESS T0 UPLOAD, localFilePath:" + localFilePath + " GROUP_NAME:" + results[0] + " REMOTE_FILE_NAME:" + results[1]);
                // 远程返回的文件名称
                dfsMap.put("GROUP_NAME", results[0]);
                // 文件的groupId
                dfsMap.put("REMOTE_FILE_NAME", results[1]);
            }
        } catch (Exception e) {
            log.error("dfs文件上传失败", e);
        } finally {
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
            if (trackerServer != null) {
                pool.returnObject(trackerServer);
            }
        } catch (Exception e) {
            log.error("dfs放回连接池失败", e);
        }
    }

}
