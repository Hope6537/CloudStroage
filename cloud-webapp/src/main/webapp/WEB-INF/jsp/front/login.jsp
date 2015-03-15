<%--
  ~ Copyright (c) 2015. Hope6537 The Founder of Lab.JiChuang ,ChangChun University,
  ~ JiLin Province,China
  ~ JiChuang CloudStroage is a maven webapp using Hadoop Distributed File System for storage ' s Cloud Stroage System
  --%>

<%--
  Created by IntelliJ IDEA.
  User: Hope6537
  Date: 2015/3/14
  Time: 13:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title></title>
    <jsp:include page="../common/template/template_head.jsp"/>
    <link href='<c:url value="/static/front/css/login2.css"/>' rel="stylesheet" type="text/css"/>
</head>
<body class="login">
<div class="menu-toggler sidebar-toggler">
</div>
<div class="logo">
</div>
<div class="content">
    <form class="login-form" id="loginForm">
        <div class="form-title">
            <span class="form-subtitle" style=" padding-left: 0px; ">请先登录</span>
        </div>
        <div class="alert alert-danger display-hide">
            <button class="close" data-close="alert"></button>
			<span>
			请输入用户名和密码 </span>
        </div>
        <div class="form-group">
            <label class="control-label visible-ie8 visible-ie9">账号/邮箱</label>
            <input class="form-control form-control-solid placeholder-no-fix" type="text" autocomplete="off"
                   placeholder="账号/邮箱" id="username" value="caodan@jichuang.com"/>
        </div>
        <div class="form-group">
            <label class="control-label visible-ie8 visible-ie9">密码</label>
            <input class="form-control form-control-solid placeholder-no-fix" type="password" autocomplete="off"
                   placeholder="密码" id="password" value="1234567"/>
        </div>
        <div class="form-actions">
            <button type="button" id="loginButton" class="btn btn-primary btn-block uppercase">登录</button>
        </div>
        <div class="form-actions">
            <div class="pull-left">
                <label class="rememberme check">
                    <input type="checkbox" name="remember" value="1"/>记住我(请勿在公共电脑勾选)</label>
            </div>
            <div class="pull-right forget-password-block">
                <a href="javascript:;" id="forget-password" class="forget-password">忘记密码？</a>
            </div>
        </div>
        <div class="create-account">
            <p>
                <a href="javascript:;" id="register-btn">创建新账号</a>
            </p>
        </div>
    </form>
    <form class="forget-form">
        <div class="form-title">
            <span class="form-title">Forget Password ?</span>
            <span class="form-subtitle">Enter your e-mail to reset it.</span>
        </div>
        <div class="form-group">
            <input class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="Email"
                   name="email"/>
        </div>
        <div class="form-actions">
            <button type="button" id="back-btn" class="btn btn-default">Back</button>
            <button type="button" id="forgetPasswordButton" class="btn btn-primary uppercase pull-right">Submit</button>
        </div>
    </form>
    <form class="register-form">
        <div class="form-title">
            <span class="form-title">注册</span>
        </div>
        <p class="hint">
            请输入您的账号信息
        </p>

        <div class="form-group">
            <label class="control-label visible-ie8 visible-ie9">您的姓名</label>
            <input class="form-control placeholder-no-fix" type="text" placeholder="您的姓名" id="register_name"/>
        </div>
        <div class="form-group">
            <label class="control-label visible-ie8 visible-ie9">注册邮箱</label>
            <input class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="注册邮箱(将会作为账号使用)"
                   id="register_username" name="username"/>
        </div>
        <div class="form-group">
            <label class="control-label visible-ie8 visible-ie9">账号密码</label>
            <input class="form-control placeholder-no-fix" type="password" autocomplete="off" id="register_password"
                   placeholder="账号密码" name="password"/>
        </div>
        <div class="form-group">
            <label class="control-label visible-ie8 visible-ie9">请重复输入密码</label>
            <input class="form-control placeholder-no-fix" type="password" autocomplete="off"
                   placeholder="请重复输入密码" id="register_confrim" name="rpassword"/>
        </div>
        <div class="form-group margin-top-20 margin-bottom-20">
            <label class="check">
                <input type="checkbox" name="tnc" id="checkPrivacy"/>
                <span class="loginblue-font">I agree to the </span>
                <a href="#" class="loginblue-link">Terms of Service</a>
                <span class="loginblue-font">and</span>
                <a href="#" class="loginblue-link">Privacy Policy </a>
            </label>

            <div id="register_tnc_error">
            </div>
        </div>
        <div class="form-actions">
            <button type="button" id="register-back-btn" class="btn btn-default">后退</button>
            <button type="button" id="register-submit-btn" class="btn btn-primary uppercase pull-right">确认</button>
        </div>
    </form>
</div>
<div class="copyright hide">
    2015 &copy; Lab.JiChuang by Hope6537.
</div>
<jsp:include page="../common/template/template_script.jsp"/>
<script src='<c:url value="/static/front/js/login.js"/>' type="text/javascript"></script>
<script>
    jQuery(document).ready(function () {
        Login.init();
    });
</script>
</body>
</html>
