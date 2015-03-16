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
    <link href="<c:url value="/static/front/css/index.css"/>" rel="stylesheet" type="text/css"/>
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
                                <a href="javascript:;" id="toUpload" class="btn btn-circle red-sunglo btn-sm">
                                    <i class="fa fa-upload"></i> 上传文件 </a>
                                <a href="#" class="btn btn-circle red-sunglo btn-sm" style="background-color:#6A99E2">
                                    <i class="fa fa-folder"></i> 新建文件夹 </a>
                                <a class="btn btn-circle btn-icon-only btn-default fullscreen" href="#"
                                   data-original-title="" title="全屏">
                                </a>
                            </div>
                        </div>
                        <div class="portlet-body" id="context">
                            <%--<table class="table table-hover table-light gtreetable" id="fileTable">
                            </table>--%>
                            <table class="table table-striped table-bordered table-hover" id="dataTable">
                                <thead>
                                <tr>
                                    <th class="table-checkbox">
                                        <input type="checkbox" class="group-checkable"
                                               data-set="#dataTable .checkboxes"/>
                                    </th>
                                    <th>
                                        文件名
                                    </th>
                                    <th>
                                        Email
                                    </th>
                                    <th>
                                        Points
                                    </th>
                                    <th>
                                        文件大小
                                    </th>
                                    <th>
                                        更新日期
                                    </th>
                                </tr>
                                </thead>
                                <tbody >
                                <tr class="odd gradeX">
                                    <td>
                                        <input type="checkbox" class="checkboxes" value="1"/>
                                    </td>
                                    <td>
                                        shuxer
                                    </td>
                                    <td>
                                        <a href="mailto:shuxer@gmail.com">
                                            shuxer@gmail.com </a>
                                    </td>
                                    <td>
                                        120
                                    </td>
                                    <td class="center">
                                        12 Jan 2012
                                    </td>
                                    <td>
									<span class="label label-sm label-success">
									Approved </span>
                                    </td>
                                </tr>
                                <tr class="odd gradeX">
                                    <td>
                                        <input type="checkbox" class="checkboxes" value="1"/>
                                    </td>
                                    <td>
                                        looper
                                    </td>
                                    <td>
                                        <a href="mailto:looper90@gmail.com">
                                            looper90@gmail.com </a>
                                    </td>
                                    <td>
                                        120
                                    </td>
                                    <td class="center">
                                        12.12.2011
                                    </td>
                                    <td>
									<span class="label label-sm label-warning">
									Suspended </span>
                                    </td>
                                </tr>
                                <tr class="odd gradeX">
                                    <td>
                                        <input type="checkbox" class="checkboxes" value="1"/>
                                    </td>
                                    <td>
                                        userwow
                                    </td>
                                    <td>
                                        <a href="mailto:userwow@yahoo.com">
                                            userwow@yahoo.com </a>
                                    </td>
                                    <td>
                                        20
                                    </td>
                                    <td class="center">
                                        12.12.2012
                                    </td>
                                    <td>
									<span class="label label-sm label-success">
									Approved </span>
                                    </td>
                                </tr>
                                <tr class="odd gradeX">
                                    <td>
                                        <input type="checkbox" class="checkboxes" value="1"/>
                                    </td>
                                    <td>
                                        user1wow
                                    </td>
                                    <td>
                                        <a href="mailto:userwow@gmail.com">
                                            userwow@gmail.com </a>
                                    </td>
                                    <td>
                                        20
                                    </td>
                                    <td class="center">
                                        12.12.2012
                                    </td>
                                    <td>
									<span class="label label-sm label-default">
									Blocked </span>
                                    </td>
                                </tr>
                                <tr class="odd gradeX">
                                    <td>
                                        <input type="checkbox" class="checkboxes" value="1"/>
                                    </td>
                                    <td>
                                        restest
                                    </td>
                                    <td>
                                        <a href="mailto:userwow@gmail.com">
                                            test@gmail.com </a>
                                    </td>
                                    <td>
                                        20
                                    </td>
                                    <td class="center">
                                        12.12.2012
                                    </td>
                                    <td>
									<span class="label label-sm label-success">
									Approved </span>
                                    </td>
                                </tr>
                                <tr class="odd gradeX">
                                    <td>
                                        <input type="checkbox" class="checkboxes" value="1"/>
                                    </td>
                                    <td>
                                        foopl
                                    </td>
                                    <td>
                                        <a href="mailto:userwow@gmail.com">
                                            good@gmail.com </a>
                                    </td>
                                    <td>
                                        20
                                    </td>
                                    <td class="center">
                                        19.11.2010
                                    </td>
                                    <td>
									<span class="label label-sm label-success">
									Approved </span>
                                    </td>
                                </tr>
                                <tr class="odd gradeX">
                                    <td>
                                        <input type="checkbox" class="checkboxes" value="1"/>
                                    </td>
                                    <td>
                                        weep
                                    </td>
                                    <td>
                                        <a href="mailto:userwow@gmail.com">
                                            good@gmail.com </a>
                                    </td>
                                    <td>
                                        20
                                    </td>
                                    <td class="center">
                                        19.11.2010
                                    </td>
                                    <td>
									<span class="label label-sm label-success">
									Approved </span>
                                    </td>
                                </tr>
                                <tr class="odd gradeX">
                                    <td>
                                        <input type="checkbox" class="checkboxes" value="1"/>
                                    </td>
                                    <td>
                                        coop
                                    </td>
                                    <td>
                                        <a href="mailto:userwow@gmail.com">
                                            good@gmail.com </a>
                                    </td>
                                    <td>
                                        20
                                    </td>
                                    <td class="center">
                                        19.11.2010
                                    </td>
                                    <td>
									<span class="label label-sm label-success">
									Approved </span>
                                    </td>
                                </tr>
                                <tr class="odd gradeX">
                                    <td>
                                        <input type="checkbox" class="checkboxes" value="1"/>
                                    </td>
                                    <td>
                                        pppol
                                    </td>
                                    <td>
                                        <a href="mailto:userwow@gmail.com">
                                            good@gmail.com </a>
                                    </td>
                                    <td>
                                        20
                                    </td>
                                    <td class="center">
                                        19.11.2010
                                    </td>
                                    <td>
									<span class="label label-sm label-success">
									Approved </span>
                                    </td>
                                </tr>
                                <tr class="odd gradeX">
                                    <td>
                                        <input type="checkbox" class="checkboxes" value="1"/>
                                    </td>
                                    <td>
                                        test
                                    </td>
                                    <td>
                                        <a href="mailto:userwow@gmail.com">
                                            good@gmail.com </a>
                                    </td>
                                    <td>
                                        20
                                    </td>
                                    <td class="center">
                                        19.11.2010
                                    </td>
                                    <td>
									<span class="label label-sm label-success">
									Approved </span>
                                    </td>
                                </tr>
                                <tr class="odd gradeX">
                                    <td>
                                        <input type="checkbox" class="checkboxes" value="1"/>
                                    </td>
                                    <td>
                                        userwow
                                    </td>
                                    <td>
                                        <a href="mailto:userwow@gmail.com">
                                            userwow@gmail.com </a>
                                    </td>
                                    <td>
                                        20
                                    </td>
                                    <td class="center">
                                        12.12.2012
                                    </td>
                                    <td>
									<span class="label label-sm label-default">
									Blocked </span>
                                    </td>
                                </tr>
                                <tr class="odd gradeX">
                                    <td>
                                        <input type="checkbox" class="checkboxes" value="1"/>
                                    </td>
                                    <td>
                                        test
                                    </td>
                                    <td>
                                        <a href="mailto:userwow@gmail.com">
                                            test@gmail.com </a>
                                    </td>
                                    <td>
                                        20
                                    </td>
                                    <td class="center">
                                        12.12.2012
                                    </td>
                                    <td>
									<span class="label label-sm label-success">
									Approved </span>
                                    </td>
                                </tr>
                                <tr class="odd gradeX">
                                    <td>
                                        <input type="checkbox" class="checkboxes" value="1"/>
                                    </td>
                                    <td>
                                        goop
                                    </td>
                                    <td>
                                        <a href="mailto:userwow@gmail.com">
                                            good@gmail.com </a>
                                    </td>
                                    <td>
                                        20
                                    </td>
                                    <td class="center">
                                        12.11.2010
                                    </td>
                                    <td>
									<span class="label label-sm label-success">
									Approved </span>
                                    </td>
                                </tr>
                                <tr class="odd gradeX">
                                    <td>
                                        <input type="checkbox" class="checkboxes" value="1"/>
                                    </td>
                                    <td>
                                        weep
                                    </td>
                                    <td>
                                        <a href="mailto:userwow@gmail.com">
                                            good@gmail.com </a>
                                    </td>
                                    <td>
                                        20
                                    </td>
                                    <td class="center">
                                        15.11.2011
                                    </td>
                                    <td>
									<span class="label label-sm label-default">
									Blocked </span>
                                    </td>
                                </tr>
                                <tr class="odd gradeX">
                                    <td>
                                        <input type="checkbox" class="checkboxes" value="1"/>
                                    </td>
                                    <td>
                                        toopl
                                    </td>
                                    <td>
                                        <a href="mailto:userwow@gmail.com">
                                            good@gmail.com </a>
                                    </td>
                                    <td>
                                        20
                                    </td>
                                    <td class="center">
                                        16.11.2010
                                    </td>
                                    <td>
									<span class="label label-sm label-success">
									Approved </span>
                                    </td>
                                </tr>
                                <tr class="odd gradeX">
                                    <td>
                                        <input type="checkbox" class="checkboxes" value="1"/>
                                    </td>
                                    <td>
                                        userwow
                                    </td>
                                    <td>
                                        <a href="mailto:userwow@gmail.com">
                                            userwow@gmail.com </a>
                                    </td>
                                    <td>
                                        20
                                    </td>
                                    <td class="center">
                                        9.12.2012
                                    </td>
                                    <td>
									<span class="label label-sm label-default">
									Blocked </span>
                                    </td>
                                </tr>
                                <tr class="odd gradeX">
                                    <td>
                                        <input type="checkbox" class="checkboxes" value="1"/>
                                    </td>
                                    <td>
                                        tes21t
                                    </td>
                                    <td>
                                        <a href="mailto:userwow@gmail.com">
                                            test@gmail.com </a>
                                    </td>
                                    <td>
                                        20
                                    </td>
                                    <td class="center">
                                        14.12.2012
                                    </td>
                                    <td>
									<span class="label label-sm label-success">
									Approved </span>
                                    </td>
                                </tr>
                                <tr class="odd gradeX">
                                    <td>
                                        <input type="checkbox" class="checkboxes" value="1"/>
                                    </td>
                                    <td>
                                        fop
                                    </td>
                                    <td>
                                        <a href="mailto:userwow@gmail.com">
                                            good@gmail.com </a>
                                    </td>
                                    <td>
                                        20
                                    </td>
                                    <td class="center">
                                        13.11.2010
                                    </td>
                                    <td>
									<span class="label label-sm label-warning">
									Suspended </span>
                                    </td>
                                </tr>
                                <tr class="odd gradeX">
                                    <td>
                                        <input type="checkbox" class="checkboxes" value="1"/>
                                    </td>
                                    <td>
                                        kop
                                    </td>
                                    <td>
                                        <a href="mailto:userwow@gmail.com">
                                            good@gmail.com </a>
                                    </td>
                                    <td>
                                        20
                                    </td>
                                    <td class="center">
                                        17.11.2010
                                    </td>
                                    <td>
									<span class="label label-sm label-success">
									Approved </span>
                                    </td>
                                </tr>
                                <tr class="odd gradeX">
                                    <td>
                                        <input type="checkbox" class="checkboxes" value="1"/>
                                    </td>
                                    <td>
                                        vopl
                                    </td>
                                    <td>
                                        <a href="mailto:userwow@gmail.com">
                                            good@gmail.com </a>
                                    </td>
                                    <td>
                                        20
                                    </td>
                                    <td class="center">
                                        19.11.2010
                                    </td>
                                    <td>
									<span class="label label-sm label-success">
									Approved </span>
                                    </td>
                                </tr>
                                <tr class="odd gradeX">
                                    <td>
                                        <input type="checkbox" class="checkboxes" value="1"/>
                                    </td>
                                    <td>
                                        userwow
                                    </td>
                                    <td>
                                        <a href="mailto:userwow@gmail.com">
                                            userwow@gmail.com </a>
                                    </td>
                                    <td>
                                        20
                                    </td>
                                    <td class="center">
                                        12.12.2012
                                    </td>
                                    <td>
									<span class="label label-sm label-default">
									Blocked </span>
                                    </td>
                                </tr>
                                <tr class="odd gradeX">
                                    <td>
                                        <input type="checkbox" class="checkboxes" value="1"/>
                                    </td>
                                    <td>
                                        wap
                                    </td>
                                    <td>
                                        <a href="mailto:userwow@gmail.com">
                                            test@gmail.com </a>
                                    </td>
                                    <td>
                                        20
                                    </td>
                                    <td class="center">
                                        12.12.2012
                                    </td>
                                    <td>
									<span class="label label-sm label-success">
									Approved </span>
                                    </td>
                                </tr>
                                <tr class="odd gradeX">
                                    <td>
                                        <input type="checkbox" class="checkboxes" value="1"/>
                                    </td>
                                    <td>
                                        test
                                    </td>
                                    <td>
                                        <a href="mailto:userwow@gmail.com">
                                            good@gmail.com </a>
                                    </td>
                                    <td>
                                        20
                                    </td>
                                    <td class="center">
                                        19.12.2010
                                    </td>
                                    <td>
									<span class="label label-sm label-success">
									Approved </span>
                                    </td>
                                </tr>
                                <tr class="odd gradeX">
                                    <td>
                                        <input type="checkbox" class="checkboxes" value="1"/>
                                    </td>
                                    <td>
                                        toop
                                    </td>
                                    <td>
                                        <a href="mailto:userwow@gmail.com">
                                            good@gmail.com </a>
                                    </td>
                                    <td>
                                        20
                                    </td>
                                    <td class="center">
                                        17.12.2010
                                    </td>
                                    <td>
									<span class="label label-sm label-success">
									Approved </span>
                                    </td>
                                </tr>
                                <tr class="odd gradeX">
                                    <td>
                                        <input type="checkbox" class="checkboxes" value="1"/>
                                    </td>
                                    <td>
                                        weep
                                    </td>
                                    <td>
                                        <a href="mailto:userwow@gmail.com">
                                            good@gmail.com </a>
                                    </td>
                                    <td>
                                        20
                                    </td>
                                    <td class="center">
                                        15.11.2011
                                    </td>
                                    <td>
									<span class="label label-sm label-success">
									Approved </span>
                                    </td>
                                </tr>
                                </tbody>
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
                            <div id="full-width" class="modal container fade" tabindex="-1">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal"
                                            aria-hidden="true"></button>
                                    <h4 class="modal-title">Full Width</h4>
                                </div>
                                <div class="modal-body">
                                    <p>
                                        This modal will resize itself to the same dimensions as the container class.
                                    </p>

                                    <p>
                                        Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis sollicitudin ipsum
                                        ac ante fermentum suscipit. In ac augue non purus accumsan lobortis id sed nibh.
                                        Nunc egestas hendrerit ipsum, et porttitor augue volutpat non. Aliquam erat
                                        volutpat. Vestibulum scelerisque lobortis pulvinar. Aenean hendrerit risus
                                        neque, eget tincidunt leo. Vestibulum est tortor, commodo nec cursus nec,
                                        vestibulum vel nibh. Morbi elit magna, ornare placerat euismod semper, dignissim
                                        vel odio. Phasellus elementum quam eu ipsum euismod pretium.
                                    </p>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" data-dismiss="modal" class="btn btn-default">Close</button>
                                    <button type="button" class="btn blue">Save changes</button>
                                </div>
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
