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
    <span hidden="hidden" id="backId">${backId}</span>
    <span hidden="hidden" id="member">${member}</span>
    <span hidden="hidden" id="parentId">${parentId}</span>
    <jsp:include page="../common/template/template_sidebar.jsp"/>
    <div class="page-content-wrapper">
        <div class="page-content">
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
            <div class="row">
                <div class="col-md-12">
                    <div class="portlet light bordered">
                        <div class="portlet-title">
                            <div class="caption font-purple-plum">
                                <i class="icon-file font-purple-plum"></i>
                                <span class="caption-subject bold uppercase">我的文件</span>
                            </div>
                            <div class="actions">
                            </div>
                        </div>
                        <div class="portlet-body" id="context">
                            <table class="table table-striped table-bordered table-hover" id="dataTable">
                                <thead>
                                <tr>
                                    <th class="table-checkbox">
                                        <input type="checkbox" class="group-checkable"
                                               data-set="#dataTable .checkboxes" data-isc="0"/>
                                    </th>
                                    <th width="70%">
                                        文件名
                                    </th>
                                    <th>
                                        文件大小
                                    </th>
                                    <th>
                                        更新日期
                                    </th>
                                </tr>
                                </thead>
                                <tbody id="tableContext">
                            </table>
                            <div id="context-menu">
                                <ul class="dropdown-menu" role="menu">
                                    <li>
                                        <a href="javascript:;" id="buttonDownload">
                                            <i class="fa fa-download"></i> 下载文件
                                        </a>
                                    </li>
                                    <li class="divider">
                                    <li>
                                        <a href="javascript:;" id="buttonShare">
                                            <i class="fa fa-share"></i> 分享文件
                                        </a>
                                    </li>
                                    <li class="divider">
                                    <li id="open">
                                        <a href="javascript:;" id="buttonOpen">
                                            <i class="fa fa-folder-open"></i> 打开文件</a>
                                    </li>
                                    <li>
                                        <a href="javascript:;" id="buttonDelete">
                                            <i class="fa fa-trash-o"></i> 删除文件 </a>
                                    </li>
                                    <li class="divider">
                                    </li>
                                    <li id="rename">
                                        <a href="javascript:;" id="buttonRename">
                                            <i class="fa fa-cut"></i> 重命名
                                        </a>
                                    </li>
                                    <li>
                                        <a href="javascript:;" id="buttonMove">
                                            <i class="fa fa-cut"></i> 移动到
                                        </a>
                                    </li>
                                    <li>
                                        <a href="javascript:;" id="buttonCopy">
                                            <i class="fa fa-copy"></i> 复制到
                                        </a>
                                    </li>
                                </ul>
                            </div>
                            <div id="uploadModal" class="modal container fade" tabindex="-1" data-backdrop="static"
                                 data-keyboard="false">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal"
                                            aria-hidden="true"></button>
                                    <h4 class="modal-title">上传文件</h4>
                                </div>
                                <div class="modal-body">
                                    <p>
                                        请拖拽文件或点击下面的选择框选择文件
                                    </p>

                                    <p>

                                    <form action='upload' class="dropzone" id="uploadzone">
                                    </form>
                                    </p>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" data-dismiss="modal" class="btn btn-default">取消上传</button>
                                    <button type="button" id="buttonUpload" class="btn blue">确认上传</button>
                                </div>
                            </div>
                            <div id="newFolderModal" class="modal fade" tabindex="-1">
                                <input type="hidden" id="renameHanderId">

                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal"
                                            aria-hidden="true"></button>
                                    <h4 class="modal-title" id="modalTitle">新建文件夹</h4>
                                </div>
                                <div class="modal-body">
                                    <p>
                                        <input class="form-control form-control-solid placeholder-no-fix" type="text"
                                               autocomplete="off"
                                               placeholder="请输入名称" id="folderName"/>
                                    </p>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" data-dismiss="modal" class="btn btn-default">取消</button>
                                    <button type="button" id="buttonAddFolder" data-dismiss="modal" class="btn blue">
                                        确认
                                    </button>
                                    <button type="button" id="buttonRenameModal" data-dismiss="modal" class="btn green">
                                        确认
                                    </button>
                                </div>
                            </div>

                            <div id="preViewModal" class="modal fade" tabindex="-1">
                                <input type="hidden" id="preViewHanderId">

                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal"
                                            aria-hidden="true"></button>
                                    <h4 class="modal-title">预览文件</h4>
                                </div>
                                <div class="modal-body">
                                    <p>
                                        文件正文
                                    </p>

                                </div>
                                <div class="modal-footer">
                                    <button type="button" data-dismiss="modal" class="btn btn-default">取消</button>
                                </div>
                            </div>

                            <div id="downloadModal" class="modal container fade" tabindex="-1">
                                <input type="hidden" id="downloadHanderId">

                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal"
                                            aria-hidden="true"></button>
                                    <h4 class="modal-title">下载文件</h4>
                                </div>
                                <div class="modal-body">
                                    <div class="note note-success">
                                        <h4 class="block">正在为您生成下载链接</h4>

                                        <p>

                                        <div id="bar" class="progress progress-striped active" role="progressbar">
                                            <div style="width: 50%;" class="progress-bar progress-bar-success">
                                            </div>
                                        </div>
                                        </p>
                                    </div>
                                    <div id="downloadButtonZone" class="row">

                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" data-dismiss="modal" class="btn btn-default">取消</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <jsp:include page="../common/template/template_quick_sidebar.jsp"/>
</div>
<jsp:include page="../common/template/template_footer.jsp"/>
</body>
<jsp:include page="../common/template/template_script.jsp"/>
<script src='<c:url value="/static/front/js/index.js"/>' type="text/javascript"></script>
<script>
    $(document).ready(function () {
        Index.init();
    });
</script>
</html>
