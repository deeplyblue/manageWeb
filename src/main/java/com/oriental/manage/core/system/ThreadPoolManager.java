package com.oriental.manage.core.system;

import com.oriental.manage.core.utils.SessionUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by lupengfei on 2015/12/4.
 */
@Slf4j
public class ThreadPoolManager {

    private ThreadPoolExecutor pool;
    private final Logger LOG = LoggerFactory.getLogger(ThreadPoolManager.class);

    public ThreadPoolManager(ExecutorService pool){
        this.pool = (ThreadPoolExecutor)pool;
    }

    public void execute(Runnable runnable){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];

        LOG.debug("------线程池调用前-------");
        String userName;

        //防止系统内部任务导致的session空指针异常
        try {
            userName = SessionUtils.getUserName();
        }catch (NullPointerException e){
            userName = "系统内部调用";
            log.debug(userName);
            log.error("系统异常",e);
        }catch (Exception e){
            userName = "未获取到当前用户名";
            log.debug(userName);
            log.error("系统异常",e);
        }

        LOG.debug("当前操作人---{}",userName);
        LOG.debug("调用线程池的方法---{}",ste.getClassName() + "." + ste.getMethodName() + "[lineNumber:" +
                ste.getLineNumber() + "]");
        LOG.debug("当前线程池状态---最大线程数:{},当前线程数:{},活跃线程数:{},任务:{}",
                pool.getCorePoolSize(),pool.getPoolSize(),pool.getActiveCount(),
                pool.getCompletedTaskCount() + "/" + pool.getTaskCount());
        this.pool.execute(runnable);
    }

    public void clearAllTask(){
        pool.shutdownNow();
    }
}
