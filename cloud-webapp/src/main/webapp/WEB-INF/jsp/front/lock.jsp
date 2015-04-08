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
    <title>CloudStroage | 集创云存储</title>
    <jsp:include page="../common/template/template_head.jsp"/>
    <link href="<c:url value="/static/front/css/lock.css"/>" rel="stylesheet" type="text/css"/>
</head>
<body>
<div class="page-lock">
    <div class="page-logo">
        <a class="brand" href="<c:url value='//index'/>">
            <img src="../../assets/admin/layout/img/logo-big.png" alt="logo"/>
        </a>
    </div>
    <div class="page-body">
        <div class="lock-head">
            Locked
        </div>
        <div class="lock-body">
            <div class="pull-left lock-avatar-block">
                <img src="../../assets/admin/pages/media/profile/photo3.jpg" class="lock-avatar">
            </div>
            <form class="lock-form pull-left" action="index.html" method="post">
                <h4>${loginMember.name}</h4>

                <div class="form-group">
                    <input class="form-control placeholder-no-fix" type="password" autocomplete="off"
                           placeholder="Password" name="password"/>
                </div>
                <div class="form-actions">
                    <button type="submit" class="btn btn-success uppercase">Login</button>
                </div>
            </form>
        </div>
        <div class="lock-bottom">
            <a href="">您不是${loginMember.name}?</a>
        </div>
    </div>
    <div class="page-footer-custom">
        2014 &copy; Metronic. Admin Dashboard Template.
    </div>
</div>
</body>
<jsp:include page="../common/template/template_script.jsp"/>
<script src='<c:url value="/static/front/js/index.js"/>' type="text/javascript"></script>
<script>
    $(document).ready(function () {
        Index.init();
    });
</script>
</html>
