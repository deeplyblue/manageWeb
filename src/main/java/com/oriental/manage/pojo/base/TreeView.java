package com.oriental.manage.pojo.base;

import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lupf on 2016/4/26.
 */
@Data
public class TreeView {

    private String text;
    private String href;
    private String desc;
    private String operation;
    /*以下部分与resourceInfo相同*/
    private String rsrcType;
    private String rsrcCode;
    private String resourceType;
    private String parentRsrcCode;
    private String rsrcDspOrder;
    private List<TreeView> nodes;
    //是否拥有改权限
    private Map<String, Boolean> state = new HashMap<String, Boolean>();

    public TreeView() {
        state.put("checked", false);
        state.put("disabled", false);
        state.put("expanded", false);
        state.put("selected", false);
    }
}
