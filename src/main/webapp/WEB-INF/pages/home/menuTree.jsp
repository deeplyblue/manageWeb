<%--
  Created by IntelliJ IDEA.
  User: lupf
  Date: 2016/4/15
  Time: 14:01
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<core:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>侧边栏</title>
    <link rel="stylesheet" href="${ctx}/css/bootstrap/bootstrap.min.css">
    <script src="${ctx}/js/jquery/jquery-1.12.3.min.js"></script>
    <script src="${ctx}/js/tree/bootstrap-treeview.js"></script>
    <script src="${ctx}/build/js/views/common/nav-jquery-d762925ca6.js"></script>
    <script type="text/javascript" src="${ctx}/build/js/views/common/bootstrap-addtabs-690da11d14.js"></script>
</head>
<body>
<nav class="navbar navbar-default" role="navigation">
    <div class="navbar-header">
        <a class="navbar-brand" href="#">菜单列表</a>
    </div>
    <div id="tree" style="font-size: 12px"></div>
</nav>

<script type="application/javascript">
    $(document).ready(function(){
        $(window.parent.document).find("#tabs").addtabs({close: true,monitor: '#tree',iframeUse: true});
    });
    function getTree() {
        /*data oriental*/
        /*var tree = [
         {
         text: "Parent 1",
         nodes: [
         {
         text: "Child 1",
         nodes: [
         {
         text: "Grandchild 1"
         },
         {
         text: "Grandchild 2"
         }
         ]
         },
         {
         text: "Child 2"
         }
         ]
         },
         {
         text: "Parent 2"
         },
         {
         text: "Parent 3"
         },
         {
         text: "Parent 4"
         },
         {
         text: "Parent 5"
         }
         ];*/

        var options = {
            url: "${ctx}/home/userLoadInit",
            onSuccessFunction: function (data) {
                $("#tree").treeview(
                        {
                            data: data,
                            levels: 1,
//                            color: 'rgb(0, 189, 123)',
                            expandIcon: 'glyphicon glyphicon-chevron-right customIcon',
                            collapseIcon: 'glyphicon glyphicon-chevron-down customIcon',
                            emptyIcon: 'glyphicon glyphicon-bookmark customIcon',
                            openOnClick: true
                        });
//                $('.customIcon').css('color', 'rgb(0, 189, 123)');
                $("#tree").on('nodeSelected', function (event, data) {
                    if (data.href) {
                        Addtabs.add({id: data.nodeId,title:data.text,url:data.href});
                        //$('iframe[name=mainFrame]', window.parent.document).attr('src', '${ctx}/' + data.href);
                    }
                });
                $("#tree").on('nodeUnselected', function (event, data) {
                    if (data.href) {
                        //$('iframe[name=mainFrame]', window.parent.document).attr('src', '${ctx}/' + data.href);
                    }
                })
            }
        };

        myAjaxRequest(options);
    }

    getTree();
</script>
</body>
</html>