package com.oriental.manage.core.schedule;

import com.oriental.manage.core.enums.UserStatus;
import com.oriental.manage.pojo.base.UserInfo;
import com.oriental.manage.service.base.IUserInfoService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Created by lupf on 2016/8/23.
 */
@Slf4j
@Component
public class PasswordExpiredSchedule {

    @Autowired
    private IUserInfoService userInfoService;
    @Value("#{cfgProperties['schedule_pwdExpired']}")
    @Setter
    private boolean pwdExpiredFlag;

    /**
     * 定时任务，每天凌晨1点启动
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void run() {
        //启用标记
        if (!pwdExpiredFlag){
            return;
        }

        log.info("定时任务-用户密码过期，启动");
        List<UserInfo> list = userInfoService.queryPwdExpired();

        if (list != null && list.size() > 0) {
            for (UserInfo userInfo : list) {
                log.info("用户：{}密码过期", userInfo.getUserName());

                UserInfo updateModel = new UserInfo();
                updateModel.setUserId(userInfo.getUserId());
                updateModel.setUserStatus(UserStatus.STOPPED.getCode());
                updateModel.setLastUpdTime(new Date());
                updateModel.setModifier("sys");
                try {
                    userInfoService.updateUserInfo(updateModel);
                } catch (Exception e) {
                    log.error("更新用户状态失败",e);
                    log.error("用户:{}密码过期设置,失败",userInfo.getUserName());
                }
            }
        }
    }
}
