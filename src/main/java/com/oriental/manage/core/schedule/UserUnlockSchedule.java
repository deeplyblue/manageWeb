package com.oriental.manage.core.schedule;

import com.oriental.manage.core.enums.UserStatus;
import com.oriental.manage.pojo.base.UserInfo;
import com.oriental.manage.service.base.IUserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Created by yutao on 2016/10/24.
 */
@Slf4j
@Component
public class UserUnlockSchedule {
    @Autowired
    private IUserInfoService userInfoService;

    /**
     * 定时任务每隔1小时启动
     */

    @Scheduled(cron = "0 0 0/1 * * ?")
    public void run(){
        log.info("定时任务-用户解除锁定启动");
        List<UserInfo> list = userInfoService.queryUserLock();
        if(list!=null && list.size()>0){
            for(UserInfo info:list){
                log.info("用户：{}被锁定",info.getUserName());

                UserInfo updateBean = new UserInfo();
                updateBean.setUserId(info.getUserId());
                updateBean.setStatusLastUpd(new Date());
                updateBean.setUserStatus(UserStatus.NORMAL.getCode());
                updateBean.setModifier("sys");
                try {
                    //解除账号锁定，修改状态正常
                    userInfoService.updateUserInfo(updateBean);
                } catch (Exception e) {
                   log.info("修改用户状态失败",e);
                   log.info("用户：{}，修改状态失败",info.getUserName());
                }

            }

        }



    }


}
