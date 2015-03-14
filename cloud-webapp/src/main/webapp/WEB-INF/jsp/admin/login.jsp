<%--
  ~ Copyright (c) 2015. Hope6537 The Founder of Lab.JiChuang ,ChangChun University,
  ~ JiLin Province,China
  ~ JiChuang CloudStroage is a maven webapp using Hadoop Distributed File System for storage ' s Cloud Stroage System
  --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>登录</title>
    <jsp:include page="template/template_head.jsp"/>
</head>
<body>
CloudStroage 云盘 Version 0.2-SNAPSHOT<br/>

<div id="login">
    用户名:<input type="text" id="username"/><br/>
    密码:<input type="password" id="password"/><br/>
    <button type="button" id="loginButton">登录</button>
</div>
<jsp:include page="template/template_script.jsp"/>
<script src="<c:url value="/static/admin/js/login.js"/>"></script>
<script>
    $(document).ready(function () {
        Login.init();
        console.log("init")
    });
</script>
</body>
</html>
