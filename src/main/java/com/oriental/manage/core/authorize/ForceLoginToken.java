package com.oriental.manage.core.authorize;

import lombok.Getter;
import lombok.Setter;
import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * Created by lupf on 2016/7/15.
 */
public class ForceLoginToken extends UsernamePasswordToken {

    @Getter
    @Setter
    private boolean forceFlag = false;

    public ForceLoginToken(String userName, String password, boolean forceFlag) {
        super(userName, password);
        this.forceFlag = forceFlag;
    }
}
