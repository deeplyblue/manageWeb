package com.oriental.manage.core.exception;

import com.oriental.manage.core.enums.ErrorCodeManage;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by lupf on 2016/4/13.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BusiException extends RuntimeException {

    private String code;
    private String desc;
    private ErrorCodeManage errorCodeManage;

    public BusiException(ErrorCodeManage error) {
        super(error.getCode());
        this.code = error.getCode();
        this.desc = error.getDesc();
        this.errorCodeManage = error;
    }

    public BusiException(String msg) {
        super(msg);
        this.desc = msg;
    }

    public BusiException(String code, String msg) {
        super(msg);
        this.code = code;
        this.desc = msg;
    }
}
