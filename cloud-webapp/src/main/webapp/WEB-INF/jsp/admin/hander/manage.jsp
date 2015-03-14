<%--
  ~ Copyright (c) 2015. Hope6537 The Founder of Lab.JiChuang ,ChangChun University,
  ~ JiLin Province,China
  ~ JiChuang CloudStroage is a maven webapp using Hadoop Distributed File System for storage ' s Cloud Stroage System
  --%>

<%--
  Created by IntelliJ IDEA.
  User: Hope6537
  Date: 2015/3/14
  Time: 13:21
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>关系管理</title>
    <jsp:include page="../template/template_head.jsp"/>
</head>
<body>

</body>
<jsp:include page="../template/template_script.jsp"/>
<script src="<c:url value="/static/admin/js/hander/hander.js"/>"></script>
<script>
    $(document).ready(function () {
        Hander.init();
    });
</script>
</html>
