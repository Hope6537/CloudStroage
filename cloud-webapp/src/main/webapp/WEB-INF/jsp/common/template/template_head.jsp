<%--
  ~ Copyright (c) 2015. Hope6537 The Founder of Lab.JiChuang ,ChangChun University,
  ~ JiLin Province,China
  ~ JiChuang CloudStroage is a maven webapp using Hadoop Distributed File System for storage ' s Cloud Stroage System
  --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<meta charset="utf-8"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1.0" name="viewport"/>
<meta http-equiv="Content-type" content="text/html; charset=utf-8">
<meta content="" name="description"/>
<meta content="" name="author"/>
<!-- BEGIN GLOBAL MANDATORY STYLES -->
<link href="<c:url value="/static/common/bower_plugins/fontawesome/css/font-awesome.min.css"/>" rel="stylesheet" type="text/css"/>
<link href="<c:url value="/static/common/bower_plugins/bootstrap-gtreetable/dist/bootstrap-gtreetable.min.css"/>" rel="stylesheet" type="text/css"/>
<link href="<c:url value="/static/common/bower_plugins/simple-line-icons/css/simple-line-icons.css"/>" rel="stylesheet" type="text/css"/>
<link href="<c:url value="/static/common/bower_plugins/bootstrap/dist/css/bootstrap.min.css"/>" rel="stylesheet" type="text/css"/>
<link href="<c:url value="/static/common/global/plugins/uniform/css/uniform.default.min.css"/>" rel="stylesheet" type="text/css"/>
<link href="<c:url value="/static/common/bower_plugins/bootstrap-switch/dist/css/bootstrap3/bootstrap-switch.min.css"/>" rel="stylesheet" type="text/css"/>
<link href="<c:url value="/static/common/bower_plugins/select2/select2.css"/>" rel="stylesheet" type="text/css"/>
<link href="<c:url value="/static/common/bower_plugins/toastr/toastr.min.css"/>" rel="stylesheet" type="text/css"/>
<!-- END GLOBAL MANDATORY STYLES -->
<!-- BEGIN THEME STYLES -->
<link href="<c:url value="/static/common/global/css/components.css"/>" rel="stylesheet" type="text/css"/>
<link href="<c:url value="/static/common/global/css/plugins.css"/>" rel="stylesheet" type="text/css"/>
<link href="<c:url value="/static/common/global/css/global.css"/>" rel="stylesheet" type="text/css"/>
<link href="<c:url value="/static/common/layout/css/layout.css"/>" rel="stylesheet" type="text/css"/>
<link href="<c:url value="/static/common/layout/css/custom.css"/>" rel="stylesheet" type="text/css"/>
<link href="<c:url value="/static/common/layout/css/themes/darkblue.css"/>" rel="stylesheet" type="text/css"/>
<!-- END THEME STYLES -->
<link href="<c:url value="/static/common/global/plugins/autoComplete/autoComplete.css"/>" rel="stylesheet" type="text/css"/>
<%--<link rel="shortcut icon" href="favicon.ico"/>--%>
<base href="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()%><c:url value="/"/>"/>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="menu" value="${applicationScope.menuMap[param.menuId]}"/>