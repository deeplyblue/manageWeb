package com.oriental.manage.service.base;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Author: Yang xp
 * Date: 2016/5/20
 * Time: 11:12
 * Desc：省市表
 */
public interface IGovAreaService {

    Set<Map<String,Object>> selectAllArea();

    List<Map<String,String>> selectArea();
}
