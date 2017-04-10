<%--
  Created by IntelliJ IDEA.
  User: lupf
  Date: 2016/7/27
  Time: 14:48

  @TODO 留待替换菜单编辑，采用ztree，目标支持菜单拖动排序、右侧悬浮框提示菜单增、删、改
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<core:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="${ctx}/css/tree/zTreeStyle.css" type="text/css">
    <script type="text/javascript" src="${ctx}/js/jquery/jquery-1.12.3.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/views/common/nav-jquery.js"></script>
    <script type="text/javascript" src="${ctx}/js/jquery/browser.js"></script>
    <script type="text/javascript" src="${ctx}/js/tree/jquery.ztree.core.js"></script>
    <script type="text/javascript" src="${ctx}/js/tree/jquery.ztree.excheck.js"></script>
    <script type="text/javascript" src="${ctx}/js/tree/jquery.ztree.exedit.js"></script>

    <script>
        var setting = {
            edit: {
                drag: {
                    autoExpandTrigger: true,
                    prev: dropPrev,
                    inner: dropInner,
                    next: dropNext
                },
                enable: true,
                showRemoveBtn: false,
                showRenameBtn: false
            },
            data: {
                simpleData: {
                    enable: true
                }
            },
            callback: {
                beforeDrag: beforeDrag,
                beforeDrop: beforeDrop,
                beforeDragOpen: beforeDragOpen,
                onDrag: onDrag,
                onDrop: onDrop,
                onExpand: onExpand
            }
        };

        var zNodes = [
            {id: 1, pId: 0, name: "随意拖拽 1", open: true},
            {id: 11, pId: 1, name: "随意拖拽 1-1"},
            {id: 12, pId: 1, name: "随意拖拽 1-2"},
            {id: 121, pId: 12, name: "随意拖拽 1-2-1"},
            {id: 122, pId: 12, name: "随意拖拽 1-2-2"},
            {id: 123, pId: 12, name: "随意拖拽 1-2-3"},
            {id: 13, pId: 1, name: "禁止拖拽 1-3", open: true, drag: false},
            {id: 131, pId: 13, name: "禁止拖拽 1-3-1", drag: false},
            {id: 132, pId: 13, name: "禁止拖拽 1-3-2", drag: false},
            {id: 132, pId: 13, name: "禁止拖拽 1-3-3", drag: false},
            {id: 2, pId: 0, name: "禁止子节点移走 2", open: true, childOuter: false},
            {id: 21, pId: 2, name: "我不想成为父节点 2-1", dropInner: false},
            {id: 22, pId: 2, name: "我不要成为根节点 2-2", dropRoot: false},
            {id: 23, pId: 2, name: "拖拽试试看 2-3"},
            {id: 3, pId: 0, name: "禁止子节点排序/增加 3", open: true, childOrder: false, dropInner: false},
            {id: 31, pId: 3, name: "随意拖拽 3-1"},
            {id: 32, pId: 3, name: "随意拖拽 3-2"},
            {id: 33, pId: 3, name: "随意拖拽 3-3"}
        ];

        function getTree() {
            var options = {
                url: "${ctx}/system/menu/searchDetail",
                onSuccessFunction: function (data) {
                    data.forEach(function (it) {
                        initNodes(it,zNodes);
                    })
                }
            };
            myAjaxRequest(options);
        }

        function initNodes(data, nodes) {
            if (data.nodes && data.nodes.length > 0) {
                data.nodes.forEach(function (item) {
                    initNodes(item, nodes);
                });
            }
            nodes.push({
                id: data.rsrcCode,
                pId: data.parentRsrcCode,
                name: data.text
            });
        }

        function dropPrev(treeId, nodes, targetNode) {
            var pNode = targetNode.getParentNode();
            if (pNode && pNode.dropInner === false) {
                return false;
            } else {
                for (var i = 0, l = curDragNodes.length; i < l; i++) {
                    var curPNode = curDragNodes[i].getParentNode();
                    if (curPNode && curPNode !== targetNode.getParentNode() && curPNode.childOuter === false) {
                        return false;
                    }
                }
            }
            return true;
        }
        function dropInner(treeId, nodes, targetNode) {
            if (targetNode && targetNode.dropInner === false) {
                return false;
            } else {
                for (var i = 0, l = curDragNodes.length; i < l; i++) {
                    if (!targetNode && curDragNodes[i].dropRoot === false) {
                        return false;
                    } else if (curDragNodes[i].parentTId && curDragNodes[i].getParentNode() !== targetNode && curDragNodes[i].getParentNode().childOuter === false) {
                        return false;
                    }
                }
            }
            return true;
        }
        function dropNext(treeId, nodes, targetNode) {
            var pNode = targetNode.getParentNode();
            if (pNode && pNode.dropInner === false) {
                return false;
            } else {
                for (var i = 0, l = curDragNodes.length; i < l; i++) {
                    var curPNode = curDragNodes[i].getParentNode();
                    if (curPNode && curPNode !== targetNode.getParentNode() && curPNode.childOuter === false) {
                        return false;
                    }
                }
            }
            return true;
        }

        var log, className = "dark", curDragNodes, autoExpandNode;
        function beforeDrag(treeId, treeNodes) {
            className = (className === "dark" ? "" : "dark");
            showLog("[ " + getTime() + " beforeDrag ]&nbsp;&nbsp;&nbsp;&nbsp; drag: " + treeNodes.length + " nodes.");
            for (var i = 0, l = treeNodes.length; i < l; i++) {
                if (treeNodes[i].drag === false) {
                    curDragNodes = null;
                    return false;
                } else if (treeNodes[i].parentTId && treeNodes[i].getParentNode().childDrag === false) {
                    curDragNodes = null;
                    return false;
                }
            }
            curDragNodes = treeNodes;
            return true;
        }
        function beforeDragOpen(treeId, treeNode) {
            autoExpandNode = treeNode;
            return true;
        }
        function beforeDrop(treeId, treeNodes, targetNode, moveType, isCopy) {
            className = (className === "dark" ? "" : "dark");
            showLog("[ " + getTime() + " beforeDrop ]&nbsp;&nbsp;&nbsp;&nbsp; moveType:" + moveType);
            showLog("target: " + (targetNode ? targetNode.name : "root") + "  -- is " + (isCopy == null ? "cancel" : isCopy ? "copy" : "move"));
            return true;
        }
        function onDrag(event, treeId, treeNodes) {
            className = (className === "dark" ? "" : "dark");
            showLog("[ " + getTime() + " onDrag ]&nbsp;&nbsp;&nbsp;&nbsp; drag: " + treeNodes.length + " nodes.");
        }
        function onDrop(event, treeId, treeNodes, targetNode, moveType, isCopy) {
            className = (className === "dark" ? "" : "dark");
            showLog("[ " + getTime() + " onDrop ]&nbsp;&nbsp;&nbsp;&nbsp; moveType:" + moveType);
            showLog("target: " + (targetNode ? targetNode.name : "root") + "  -- is " + (isCopy == null ? "cancel" : isCopy ? "copy" : "move"))
        }
        function onExpand(event, treeId, treeNode) {
            if (treeNode === autoExpandNode) {
                className = (className === "dark" ? "" : "dark");
                showLog("[ " + getTime() + " onExpand ]&nbsp;&nbsp;&nbsp;&nbsp;" + treeNode.name);
            }
        }

        function showLog(str) {
            if (!log) log = $("#log");
            log.append("<li class='" + className + "'>" + str + "</li>");
            if (log.children("li").length > 8) {
                log.get(0).removeChild(log.children("li")[0]);
            }
        }
        function getTime() {
            var now = new Date(),
                    h = now.getHours(),
                    m = now.getMinutes(),
                    s = now.getSeconds(),
                    ms = now.getMilliseconds();
            return (h + ":" + m + ":" + s + " " + ms);
        }

        function setTrigger() {
            var zTree = $.fn.zTree.getZTreeObj("treeDemo");
            zTree.setting.edit.drag.autoExpandTrigger = $("#callbackTrigger").attr("checked");
        }

        $(document).ready(function () {
            getTree();
            $.fn.zTree.init($("#treeDemo"), setting, zNodes);
            $("#callbackTrigger").bind("change", {}, setTrigger);
        });
        //-->
    </SCRIPT>
</head>
<body>
<p>1111</p>
<ul id="treeDemo" class="ztree"></ul>
</body>
</html>
