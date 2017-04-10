package com.oriental.manage.dao.base;

import java.util.List;
import java.util.Map;

/**
 * Author: Yang xp
 * Date: 2016/5/20
 * Time: 10:59
 * Desc：省市表 没有国家只有中国
 */
public interface GovAreaMapper {

    List<Map<String,String>> selectAllArea();
}
