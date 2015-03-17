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
<link href="<c:url value="/static/common/bower_plugins/bootstrap/dist/css/bootstrap.css"/>" rel="stylesheet" type="text/css"/>
<link href="<c:url value="/static/common/bower_plugins/hope6537-plugin/uniform/css/uniform.default.css"/>" rel="stylesheet" type="text/css"/>
<link href="<c:url value="/static/common/bower_plugins/bootstrap-modal/css/bootstrap-modal-bs3patch.css"/>" rel="stylesheet" type="text/css"/>
<link href="<c:url value="/static/common/bower_plugins/bootstrap-modal/css/bootstrap-modal.css"/>" rel="stylesheet" type="text/css"/>
<link href="<c:url value="/static/common/bower_plugins/datatable/plugins/bootstrap/dataTables.bootstrap.css"/>" rel="stylesheet" type="text/css"/>
<link href="<c:url value="/static/common/bower_plugins/fontawesome/css/font-awesome.min.css"/>" rel="stylesheet" type="text/css"/>
<link href="<c:url value="/static/common/bower_plugins/bootstrap-gtreetable/dist/bootstrap-gtreetable.min.css"/>" rel="stylesheet" type="text/css"/>
<link href="<c:url value="/static/common/bower_plugins/simple-line-icons/css/simple-line-icons.css"/>" rel="stylesheet" type="text/css"/>
<link href="<c:url value="/static/common/bower_plugins/hope6537-plugin/global/plugins/uniform/css/uniform.default.min.css"/>" rel="stylesheet" type="text/css"/>
<link href="<c:url value="/static/common/bower_plugins/bootstrap-switch/dist/css/bootstrap3/bootstrap-switch.min.css"/>" rel="stylesheet" type="text/css"/>
<link href="<c:url value="/static/common/bower_plugins/select2/select2.css"/>" rel="stylesheet" type="text/css"/>
<link href="<c:url value="/static/common/bower_plugins/select2/select2-bootstrap.css"/>" rel="stylesheet" type="text/css"/>
<link href="<c:url value="/static/common/bower_plugins/toastr/toastr.min.css"/>" rel="stylesheet" type="text/css"/>
<link href="<c:url value="/static/common/bower_plugins/pace/themes/blue/pace-theme-barber-shop.css"/>" rel="stylesheet" type="text/css"/>
<link href="<c:url value="/static/common/bower_plugins/dropzone/css/basic.css"/>" rel="stylesheet" type="text/css"/>
<link href="<c:url value="/static/common/bower_plugins/dropzone/css/dropzone.css"/>" rel="stylesheet" type="text/css"/>
<!-- END GLOBAL MANDATORY STYLES -->
<!-- BEGIN THEME STYLES -->
<link href="<c:url value="/static/common/bower_plugins/hope6537-plugin/global/css/components.css"/>" id="style_components" rel="stylesheet" type="text/css"/>
<link href="<c:url value="/static/common/bower_plugins/hope6537-plugin/global/css/plugins.css"/>" rel="stylesheet" type="text/css"/>
<link href="<c:url value="/static/common/bower_plugins/hope6537-plugin/layout/css/layout.css"/>" rel="stylesheet" type="text/css"/>
<link href="<c:url value="/static/common/bower_plugins/hope6537-plugin/layout/css/themes/darkblue.css"/>" id="style_color" rel="stylesheet" type="text/css"/>
<link href="<c:url value="/static/common/bower_plugins/hope6537-plugin/global/css/global.css"/>" rel="stylesheet" type="text/css"/>
<!-- END THEME STYLES -->
<%--<link rel="shortcut icon" href="favicon.ico"/>--%>
<base href="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()%><c:url value="/"/>"/>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="menu" value="${applicationScope.menuMap[param.menuId]}"/>