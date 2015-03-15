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
                <div class="page-toolbar">
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
                </div>
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
                                <div class="btn-group">
                                    <a class="btn btn-circle btn-default btn-sm" href="#" data-toggle="dropdown">
                                        <i class="fa fa-user"></i> User <i class="fa fa-angle-down"></i>
                                    </a>
                                    <ul class="dropdown-menu" role="menu">
                                        <li>
                                            <a href="#">
                                                <i class="icon-user"></i> New User </a>
                                        </li>
                                        <li>
                                            <a href="#">
                                                <i class="icon-present"></i> New Event <span
                                                    class="badge badge-success">4</span>
                                            </a>
                                        </li>
                                        <li>
                                            <a href="#">
                                                <i class="icon-basket"></i> New order </a>
                                        </li>
                                        <li class="divider">
                                        </li>
                                        <li>
                                            <a href="#">
                                                <i class="icon-flag"></i> Pending Orders <span
                                                    class="badge badge-danger">4</span>
                                            </a>
                                        </li>
                                        <li>
                                            <a href="#">
                                                <i class="icon-users"></i> Pending Users <span
                                                    class="badge badge-warning">12</span>
                                            </a>
                                        </li>
                                    </ul>
                                </div>
                                <a href="#" class="btn btn-circle red-sunglo btn-sm">
                                    <i class="fa fa-plus"></i> Add </a>
                                <a class="btn btn-circle btn-icon-only btn-default fullscreen" href="#"
                                   data-original-title="" title="">
                                </a>
                            </div>
                        </div>
                        <div class="portlet-body">
                            <table class="table table-hover table-light gtreetable" id="gtreetable">
                            </table>
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
<script>
    /*jQuery('#gtreetable').gtreetable({
     'source': function (id) {
     var data = {
     memberId: 1
     }
     return {
     type: 'GET',
     url: basePath + '/hander/model',
     data: JSON.stringify(data),
     contentType: 'application/json'
     }
     }
     });*/
</script>
</html>
