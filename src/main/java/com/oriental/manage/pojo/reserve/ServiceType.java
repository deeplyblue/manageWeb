package com.oriental.manage.pojo.reserve;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Created by jinxin on 2016/12/27.
 * 业务种类
 */
@Setter
@Getter
@ToString
public class ServiceType {

    private String serviceType;

    private List<ServiceRange> serviceRange;

}
