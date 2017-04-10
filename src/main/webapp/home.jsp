<%@ page import="com.oriental.manage.core.utils.SessionUtils" %><%--
  Created by IntelliJ IDEA.
  User: lupf
  Date: 2016/4/15
  Time: 14:01
--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<core:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>亿付数字 - 后台管理系统</title>
    <meta http-equiv="Cache-Control" content="max-age=3600"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <link rel="icon" href="${ctx}/img/login/favicon_yf.png" type="image/x-icon"/>

    <link rel="stylesheet" href="${ctx}/css/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" href="${ctx}/css/index.css">
    <link rel="stylesheet" href="${ctx}/css/bootstrap-addtabs.css">
    <script type="text/javascript" src="${ctx}/js/jquery/jquery-1.12.3.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/bootstrap/bootstrap.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/angular/angular.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/angular/angular-animate.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/angular/angular-touch.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/angular/ui-bootstrap-1.3.2.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/views/common/bootstrap-addtabs.js"></script>
</head>
<body scroll="no" style="overflow: hidden" class="container-fluid" id="outContainer">
<%--<div class="clearfix"></div>--%>
<%--<table class="container-fluid" style="padding:0 0 0 0;height: 100%;">--%>
<%--<tr class="page-top" role="navigation">
    <td class="navbar-left">
        <h2>&nbsp;&nbsp;&nbsp;统一运营</h2>
    </td>
    <td></td>
</tr>--%>
<div class="row">
    <div class="logo_box">
        <div class="logo">
            <div class="bg_logo"></div>
            <div class="logo_text">
                <h2>管理中心</h2>
                <p>manage center</p>
            </div>
        </div>
        <%--<div style="width: 50%">--%>
        <%--<a style="color:white;text-decoration: none ;background-color:#0a97ca; border:#087ea9 1px solid;padding: 4px 4px 4px 4px"
           href="${ctx}/login/logout">
            安全退出
        </a>--%>
        <%--</div>--%>

        <%--<span style="font-size: x-large;margin-left: 70%" class="dropdown">
                <button id="dropdownLable" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true"
                   aria-expanded="false">
                    <i class="glyphicon glyphicon-user "></i>
                </button>
                <ul class="dropdown-menu" aria-labelledby="dropdownLable">
                    <li><a href="${ctx}/login/logout">退出</a></li>
                </ul>
            </span>

        <a style="font-size: x-large;margin-left: 70%" href="${ctx}/login/logout">
            <i class="glyphicon glyphicon-log-out" style="color: #d58512"></i>
        </a>--%>

        <span style="display:block;text-align:right;font-size:12px;background-color: #1F81D2;padding-right: 290px;padding-top: 10px">上次登录时间：<%= SessionUtils.getLastLoginTime()%>  上次登录IP：<%= SessionUtils.getLastLoginIpAddress()%>  </span>
        <div>
        <a id="dLabel" class="dropdown-toggle user_header_outer" data-hover="dropdown" data-target="#" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
            <div class="user_header"></div>
            <div class="user_body"></div>
        </a>
        <ul class="dropdown-menu show_message" role="menu" aria-labelledby="dropdownMenu1">
            <li>
                <a href="#">
                    欢迎登陆。
                </a>
            </li>
            <li class="divider"></li>
            <li>
                <a href="${ctx}/password_find.jsp">
                    <strong> 重置密码</strong>
                </a>
            </li>
        </ul>
        </div>

        <span class="user_center">个人中心</span>
        <a href="${ctx}/login/logout" class="user_quit"  style="" role="button">安全退出</a>
    </div>
</div>


<%--<div class="row">
    <span style="display:block;text-align:right;background-color: #1F81D2;padding-right: 30px">当前登录用户【<%= userName%>】  最后登录时间：<%= lastLoginTime%>  最后登录IP：<%= lastLoginIpAddress%></span>
</div>--%>

<div class="row" id="mainContainer">
    <div class="col-md-2" style="padding:0 0 0 1px;height: 100%">
        <iframe name="menuFrame" width="100%" height="100%" style="border: none"
                src="${ctx}/home/menuTree"></iframe>
    </div>
    <div id="tabs" class="col-md-10" style="padding:0 0 0 1px;height: 100%;">
        <ul class="nav nav-tabs" role="tablist">

            <li role="presentation"  class="active">
                <a href="#home" aria-controls="home" role="tab" data-toggle="tab">首页</a></li>

        </ul>
        <div class="tab-content">
            <div class="tab-pane active" id="home" role="tabpanel">
                <iframe name="mainFrame" id="mainFrame" class="iframeClass" frameborder="no" border="0" src="${ctx}/home/welcome"></iframe>
            </div>
        </div>
    </div>
</div>

<%--</table>--%>
<script type="text/javascript">
    var totalHeight = document.documentElement.clientHeight;
    console.log(document.getElementById('mainContainer'));
    var mainHeight = totalHeight - document.getElementById('mainContainer').offsetTop;
    document.getElementById('mainContainer').style.height = mainHeight + 'px';
    console.log(totalHeight);
    console.log(mainHeight);


    (function($, window) {
        // outside the scope of the jQuery plugin to
        // keep track of all dropdowns
        var $allDropdowns = $();
        // if instantlyCloseOthers is true, then it will instantly
        // shut other nav items when a new one is hovered over
        $.fn.dropdownHover = function(options) {

            // the element we really care about
            // is the dropdown-toggle's parent
            $allDropdowns = $allDropdowns.add(this.parent());

            return this.each(function() {
                var $this = $(this).parent(),
                        defaults = {
                            delay: 300,
                            instantlyCloseOthers: true
                        },
                        data = {
                            delay: $(this).data('delay'),
                            instantlyCloseOthers: $(this).data('close-others')
                        },
                        options = $.extend(true, {}, defaults, options, data),
                        timeout;

                $this.hover(function() {
                    if(options.instantlyCloseOthers === true)
                        $allDropdowns.removeClass('open');

                    window.clearTimeout(timeout);
                    $(this).addClass('open');
                }, function() {
                    timeout = window.setTimeout(function() {
                        $this.removeClass('open');
                    }, options.delay);
                });
            });
        };

        $('[data-hover="dropdown"]').dropdownHover();
    })(jQuery, this);

    /*$("#dLabel").on("mouseover", function(e) {
        if ($(this).parent().is(".open")) {
            return
        }

        $(this).dropdown("toggle")
    })

    $("#dLabel,.show_message,.user_body").on("click", function(e) {
         if ($(this).parent().is(".close")) {
         return
         }

         $(this).dropdown("toggle")
     })*/

    $(document).ready(function(){
        $('.dropdown-toggle').dropdown();
    });
</script>
</body>
</html>
