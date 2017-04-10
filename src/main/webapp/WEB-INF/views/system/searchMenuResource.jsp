<%--
  Created by IntelliJ IDEA.
  User: lupf
  Date: 2016/4/26
  Time: 13:03
--%>
<%--
@TODO
2、修改菜单后，暂时未做页面展示修改
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<core:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="${ctx}/css/common.css">
    <link rel="stylesheet" href="${ctx}/css/bootstrap/bootstrap.min.css">
    <script src="${ctx}/js/jquery/jquery-1.12.3.min.js"></script>
    <script src="${ctx}/js/tree/bootstrap-treeview.js"></script>
    <script src="${ctx}/js/views/common/nav-jquery.js"></script>
    <script src="${ctx}/js/jquery/jquery.form.js"></script>
    <script type="application/javascript">
        function doUpdate() {
            /*$('#myForm').ajaxSubmit({
             success: function (data) {
             alert(data.msg);
             }
             })*/

            var options = {
                url: $('#myForm').attr('action'),
                postData: $('#myForm').serializeArray(),
                onSuccessFunction: function (data) {
                    alert(data.msg);
                }
            }
            myAjaxRequest(options);
        }

        //在后面插入一个同级节点
        function addNode() {
            if (checkSelected()) {
                var selectedNode = $('#tree').treeview('getSelected')[0];
                clearFormAll();
                document.myForm.action = '${ctx}/system/menu/add';
                <%--$('#myform').attr('action','${ctx}/sytem/menu/add');--%>
                $('#parentRsrcCode').val(selectedNode.parentRsrcCode);
//              排序字段处理
//                alert(selectedNode.rsrcDspOrder);
                $('#rsrcDspOrder').val(parseInt(selectedNode.rsrcDspOrder) + 1);
            }
        }
        //在里面插入一个子节点
        function addSubNode() {
            if (checkSelected()) {
                var selectedNode = $('#tree').treeview('getSelected')[0];
                /*var lastSubNode = $('#tree').treeview('getSiblings', selectedNode).pop();*/
                clearFormAll();
                document.myForm.action = '${ctx}/system/menu/add';
                <%--$('#myform').attr('action','${ctx}/sytem/menu/add');--%>
                $('#parentRsrcCode').val(selectedNode.rsrcCode);
//              排序字段处理
                var childNodes = selectedNode.nodes;
                var rsrcDspOrder;
                if (childNodes && childNodes.length > 0) {
                    rsrcDspOrder = childNodes.reduce(function (prev, cur, index, array) {
                        return parseInt(prev.rsrcDspOrder) > parseInt(cur.rsrcDspOrder) ? prev.rsrcDspOrder : cur.rsrcDspOrder;
                    });
                }
//                alert(rsrcDspOrder);
                if (isNaN(rsrcDspOrder)) {
                    rsrcDspOrder = 0;
                }
                $('#rsrcDspOrder').val(parseInt(rsrcDspOrder) + 1);
            }
        }
        //删除节点
        function deleteNode() {
            if (checkSelected()) {
                var selectedNode = $('#tree').treeview('getSelected')[0];
                var options = {
                    url: "${ctx}/system/menu/delete",
                    postData: {
                        rsrcCode: selectedNode.rsrcCode
                    },
                    onSuccessFunction: function (data) {
                        alert(data.msg);
                    }
                }
                myAjaxRequest(options);
            }
        }

        function checkSelected() {
            var selectedNodes = $('#tree').treeview('getSelected');
            if (selectedNodes instanceof Array && selectedNodes.length == 1) {
                return true;
            } else {
                alert("请先选择一个节点");
                return false;
            }
        }

        function clearFormAll() {
            $('#myForm').clearForm();
            $('#parentRsrcCode').val("");
            $('#rsrcCode').val("");
            $('#rsrcDspOrder').val("");
        }
    </script>
</head>
<body>
<core:import url="../common/pageHead.jsp">
    <core:param name="title" value="菜单管理"/>
</core:import>
<div>
    <button type="button" class="btn btn-success btn-sm" onclick="addNode()">新增</button>
    <button type="button" class="btn btn-success btn-sm" onclick="addSubNode()">新增子节点</button>
    <button type="button" class="btn btn-warning btn-sm" onclick="deleteNode()">删除节点</button>

    <div class="clearfix"></div>
    <div class="row">
        <div id="tree" class="col-md-4"></div>
        <div class="col-md-2"></div>
        <div id="model" class="col-md-5" style="display: none">
            <form id="myForm" name="myForm" class="form-horizontal" role="form" action="${ctx}/system/menu/update">
                <div class="form-group">
                    <label for="rsrcName" class="col-sm-2 control-label">名称</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control input-sm form-control-inline" id="rsrcName" name="text">
                    </div>
                </div>
                <div class="form-group">
                    <label for="rsrcUrl" class="col-sm-2 control-label">路径</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control input-sm form-control-inline" id="rsrcUrl" name="href">
                    </div>
                </div>
                <div class="form-group">
                    <label for="rsrcDesc" class="col-sm-2 control-label">描述</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control input-sm form-control-inline" id="rsrcDesc" name="desc">
                    </div>
                </div>
                <div class="form-group">
                    <label for="rsrcType" class="col-sm-2 control-label">类型</label>
                    <div class="col-sm-10">
                        <select class="form-control input-sm form-control-inline" id="rsrcType" name="rsrcType">
                            <option value="01">菜单</option>
                            <option value="02">动作</option>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label for="rsrcOperation" class="col-sm-2 control-label">操作</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control input-sm form-control-inline" id="rsrcOperation" name="operation">
                    </div>
                </div>
                <div class="form-group">
                    <label for="resourceType" class="col-sm-2 control-label">模块</label>
                    <div class="col-sm-10">
                        <select class="form-control input-sm form-control-inline" id="resourceType" name="resourceType">
                            <option value="01">公司</option>
                            <option value="02">商户</option>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label for="rsrcDspOrder" class="col-sm-2 control-label">排序</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control input-sm form-control-inline" id="rsrcDspOrder" name="rsrcDspOrder">
                    </div>
                </div>

                <%--两个隐藏属性--%>
                <input type="hidden" id="parentRsrcCode" name="parentRsrcCode">
                <input type="hidden" id="rsrcCode" name="rsrcCode">
                <%--<input type="text" id="rsrcDspOrder" name="rsrcDspOrder">--%>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="button" class="btn btn-default" onclick="doUpdate()">保存</button>
                    </div>
                </div>
            </form>
        </div>
        <div class="col-md-1"></div>
    </div>
</div>

<script type="application/javascript">
    function getTree() {
        var options = {
            url: "${ctx}/system/menu/searchDetail",
            onSuccessFunction: function (data) {
                $("#tree").treeview(
                        {
                            data: data,
                            levels: 1
                        });
                $("#tree").on('nodeSelected', function (event, data) {
                    clearFormAll();
                    $('#model').show();

                    $('#myform').attr('action', '${ctx}/system/menu/update');
                    $('#rsrcName').val(data.text);
                    $('#rsrcUrl').val(data.href);
                    $('#rsrcDesc').val(data.desc);
                    $('#rsrcOperation').val(data.operation);
                    $('#rsrcType').val(data.rsrcType);
                    $('#resourceType').val(data.resourceType);
                    $('#parentRsrcCode').val($('#tree').treeview('getParent', data).rsrcCode);
                    $('#rsrcCode').val(data.rsrcCode);
                    $('#rsrcDspOrder').val(data.rsrcDspOrder);
                });
            }
        };
        myAjaxRequest(options);
    }

    getTree();
</script>
</body>
</html>
