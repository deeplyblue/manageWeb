<%--
  Created by IntelliJ IDEA.
  User: lupf
  Date: 2016/5/17
  Time: 16:15
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<core:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Title</title>
    <%--<script src="${ctx}/js/tree/bootstrap-treeview.js"></script>--%>
    <link href="${ctx}/css/tree/zTreeStyle.css" rel="stylesheet">
    <script src="${ctx}/js/tree/jquery.ztree.core-3.5.js"></script>
    <script src="${ctx}/js/tree/jquery.ztree.excheck-3.5.js"></script>
</head>
<body>
<%--<div ivh-treeview="vm.item.treeData"
     ivh-treeview-default-selected-state="false"
     &lt;%&ndash;ivh-treeview-expand-to-depth="2"&ndash;%&gt;
     ivh-treeview-on-toggle="vm.setLastToggle(ivhNode, ivhTree)"
     ivh-treeview-on-cb-change="vm.lastSelect = ivhNode.label"
     ivh-treeview-id-attribute="'rsrcCode'"
     ivh-treeview-label-attribute="'text'"
     ivh-treeview-children-attribute="'nodes'"
     ivh-treeview-selected-attribute="'selected'">
</div>--%>
<div class="row">
    <div class="col-md-offset-1 btn-group">
        <button id="expandAllBtn" class="btn btn-primary">全部展开</button>
        <button id="collapseAllBtn" class="btn btn-primary">全部收起</button>
        <%--<button class="btn btn-primary" ng-click="vm.checkAll()">全选</button>
        <button class="btn btn-primary" ng-click="vm.uncheckAll()">全不选</button>--%>
    </div>
</div>
<div class="row">
    <%--<div id="tree" class="col-md-6">

    </div>
    <div class="col-md-6">

    </div>--%>
    <div class="zTreeDemoBackground col-md-offset-1">
        <ul id="treeDemo" class="ztree"></ul>
    </div>
    <!-- 		被勾选时： -->
    <!-- 		<span>关联父</span> -->
    <input style="display: none;" type="checkbox" id="py" name="checkEn" class="checkbox first" checked/>
    <input style="display: none;" type="checkbox" id="sy" name="checkEnOff" class="checkbox first" checked/>
    <!-- 		<span>关联子</span><br/> -->
    <!-- 		取消勾选时： -->
    <input style="display: none;" type="checkbox" id="pn" name="checkOfEn" class="checkbox first" checked/>
    <!-- 		<span>关联父</span> -->
    <input style="display: none;" type="checkbox" id="sn" name="checkOf" class="checkbox first" checked/>
    <!-- 		<span>关联子</span><br/> -->
</div>

</body>
</html>
