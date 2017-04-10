package com.oriental.manage.pojo.reserve;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by jinxin on 2016/12/26.
 * 支付机构基本信息（出资人及持股比例）
 */
@Setter
@Getter
@ToString
public class SponsorPojo {

    private String shareholderName;

    private String shareholding;

    private String idNo;

}
