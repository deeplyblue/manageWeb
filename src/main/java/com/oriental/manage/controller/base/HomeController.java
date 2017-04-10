package com.oriental.manage.controller.base;

import com.oriental.manage.core.authorize.UserRealm;
import com.oriental.manage.core.enums.SessionKey;
import com.oriental.manage.core.utils.BigDecimalUtils;
import com.oriental.manage.pojo.base.ResourceInfo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by lupf on 2016/4/18.
 */
@Slf4j
@Controller
@RequestMapping("home")
public class HomeController {

    @Autowired
    private UserRealm userRealm;

    @RequestMapping("/menuTree")
    public String toMenuTree(Model mvcModel) {
        System.out.println("-----menuTree-------");

        return "home/menuTree";
    }

    @RequestMapping("/welcome")
    public String toWelcome(Model mvcModel) {
        System.out.println("--------welcome-------");

        return "home/welcome";
    }

    @RequestMapping("userLoadInit")
    public
    @ResponseBody
    List<Tree> userLoadInit(Model mvcModel) {
        log.info("----------菜单加载---------");
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        HashMap<String, String> map1 = new HashMap<String, String>();
        map1.put("text", "parent 1");
        HashMap<String, String> map2 = new HashMap<String, String>();
        map2.put("text", "parent 2");
        list.add(map1);
        list.add(map2);

        Subject subject = SecurityUtils.getSubject();
        List<Tree> treeList = null;
        try {
            /*啥都没干，我就想缓存下菜单*/
            subject.hasRole("超级管理员");

            Session session = subject.getSession();
            Set<ResourceInfo> resourceInfoSet = (Set<ResourceInfo>) session.getAttribute(SessionKey.MENU.getCode());
            //防止被踢出用户授权信息缓存未清理完成，导致使踢出用户授权信息未加载
            if (resourceInfoSet == null){
                userRealm.doGetAuthorizationInfo(subject.getPrincipals());
                resourceInfoSet = (Set<ResourceInfo>) session.getAttribute(SessionKey.MENU.getCode());
            }
            List<ResourceInfo> resourceInfoList = new ArrayList<ResourceInfo>(resourceInfoSet);
            Collections.sort(resourceInfoList, new Comparator<ResourceInfo>() {
                @Override
                public int compare(ResourceInfo o1, ResourceInfo o2) {
                    int sort1 = BigDecimalUtils.isCompareTo(o1.getParentRsrcCode(), o2.getParentRsrcCode());
                    return sort1 != 0 ? sort1 : BigDecimalUtils.isCompareTo(o1.getRsrcDspOrder(), o2.getRsrcDspOrder());
                }
            });

            treeList = new ArrayList<Tree>();
            HashMap<String, Tree> treeHashMap = new HashMap<String, Tree>();

            for (ResourceInfo info : resourceInfoList) {
                Tree tree = new Tree();
                tree.setText(info.getRsrcName());
                if (StringUtils.equals("资源编辑",info.getRsrcName())){
                    System.out.println(info);
                }
                if (!StringUtils.equals(info.getRsrcUrl(), "common.jsp")) {
                    //菜单
                    tree.setHref(info.getRsrcUrl());
                } else if (StringUtils.equals(info.getParentRsrcCode(), "0")) {
                    //一级目录
                    tree.setNodes(new ArrayList<Tree>());
                } else {
                    //次级目录
                    tree.setNodes(new ArrayList<Tree>());
                }
                treeHashMap.put(info.getRsrcCode(), tree);
            }

            for (ResourceInfo info : resourceInfoList){
                if (StringUtils.equals(info.getParentRsrcCode(), "0")){
                    treeList.add(treeHashMap.get(info.getRsrcCode()));
                }else {
                    if (treeHashMap.get(info.getParentRsrcCode()) == null){
                        log.error("菜单树拼装异常,子:{},父:{}", info.getRsrcCode() + info.getRsrcName(), info.getParentRsrcCode());
                    }else {
                        treeHashMap.get(info.getParentRsrcCode()).getNodes().add(treeHashMap.get(info.getRsrcCode()));
                    }
                }
            }

        } catch (Exception e) {
            log.debug("验证权限失败", e);
        }

        return treeList;
    }


    @Data
    private class Tree {

        private String text;
        private String href;
        private List<Tree> nodes;

    }
}
