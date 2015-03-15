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
</head>
<body class="page-header-fixed page-quick-sidebar-over-content page-style-square page-sidebar-closed">
<jsp:include page="../common/template/template_header.jsp"/>
<div class="page-container">
    <span hidden="hidden" id="member">${member}</span>
    <jsp:include page="../common/template/template_sidebar.jsp"/>
    <div class="page-content-wrapper">
        <div class="page-content">
            <!-- BEGIN PAGE HEADER-->
            <h3 class="page-title">
                CloudStroage
                <small>集创云存储</small>
            </h3>
            <div class="page-bar">
                <ul class="page-breadcrumb">
                    <li>
                        <i class="fa fa-home"></i>
                        <a href="index.html">全部文件</a>
                        <i class="fa fa-angle-right"></i>
                    </li>
                </ul>
                <%--<div class="page-toolbar">
                    <div class="btn-group pull-right">
                        <button type="button" class="btn btn-fit-height grey-salt dropdown-toggle"
                                data-toggle="dropdown" data-hover="dropdown" data-delay="1000" data-close-others="true">
                            Actions <i class="fa fa-angle-down"></i>
                        </button>
                        <ul class="dropdown-menu pull-right" role="menu">
                            <li>
                                <a href="#">Action</a>
                            </li>
                            <li>
                                <a href="#">Another action</a>
                            </li>
                            <li>
                                <a href="#">Something else here</a>
                            </li>
                            <li class="divider">
                            </li>
                            <li>
                                <a href="#">Separated link</a>
                            </li>
                        </ul>
                    </div>
                </div>--%>
            </div>
            <!-- END PAGE HEADER-->
            <!-- BEGIN PAGE CONTENT-->
            <div class="row">
                <div class="col-md-12">
                    <!-- BEGIN PORTLET-->
                    <div class="portlet light bordered">
                        <div class="portlet-title">
                            <div class="caption font-purple-plum">
                                <i class="icon-file font-purple-plum"></i>
                                <span class="caption-subject bold uppercase">我的文件</span>
                                <span class="caption-helper" id=""></span>
                            </div>
                            <div class="actions">
                                <%--<div class="btn-group">
                                    <a class="btn btn-circle btn-default btn-sm" href="#" data-toggle="dropdown"
                                       style="margin-right: 50px;">
                                        <i class="icon-file"></i> 文件操作 <i class="fa fa-angle-down"></i>
                                    </a>
                                    <ul class="dropdown-menu" role="menu">
                                        <li>
                                            <a href="#">
                                                <i class="icon-user"></i> 上传文件 </a>
                                        </li>
                                        <li>
                                            <a href="#">
                                                <i class="icon-present"></i> 新建文件夹
                                            </a>
                                        </li>
                                    </ul>
                                </div>--%>
                                <a href="#" class="btn btn-circle red-sunglo btn-sm">
                                    <i class="fa fa-upload"></i> 上传文件 </a>
                                <a href="#" class="btn btn-circle red-sunglo btn-sm" style="background-color:#6A99E2">
                                    <i class="fa fa-folder"></i> 新建文件夹 </a>
                                <a class="btn btn-circle btn-icon-only btn-default fullscreen" href="#"
                                   data-original-title="" title="全屏">
                                </a>
                            </div>
                        </div>
                        <div class="portlet-body" id="context">
                            <table class="table table-hover table-light gtreetable" id="fileTable">
                            </table>
                            <div id="context-menu">
                                <ul class="dropdown-menu" role="menu">

                                    <li>
                                        <a href="#">
                                            <i class="fa fa-download"></i> 下载文件
                                        </a>
                                    </li>
                                    <li class="divider">
                                    <li>
                                        <a href="#">
                                            <i class="fa fa-share"></i> 分享文件
                                        </a>
                                    </li>
                                    <li class="divider">
                                    <li>
                                        <a href="#">
                                            <i class="fa fa-folder-open"></i> 打开文件</a>
                                    </li>
                                    <li>
                                        <a href="#">
                                            <i class="fa fa-trash-o"></i> 删除文件 </a>
                                    </li>
                                    <li class="divider">
                                    </li>
                                    <li>
                                        <a href="#">
                                            <i class="fa fa-cut"></i> 移动到
                                        </a>
                                    </li>
                                    <li>
                                        <a href="#">
                                            <i class="fa fa-copy"></i> 复制到 <span
                                                class="badge badge-warning">12</span>
                                        </a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <!-- END PORTLET-->
                </div>
            </div>
            <!-- END PAGE CONTENT-->
        </div>
    </div>
    <jsp:include page="../common/template/template_quick_sidebar.jsp"/>
</div>
<jsp:include page="../common/template/template_footer.jsp"/>
</body>
<jsp:include page="../common/template/template_script.jsp"/>
<script src='<c:url value="/static/front/index.js"/>' type="text/javascript"></script>
<script>
    $(document).ready(function () {

        Index.init();
    });
</script>
</html>
