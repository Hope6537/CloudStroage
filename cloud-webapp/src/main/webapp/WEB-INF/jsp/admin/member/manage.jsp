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
    <title></title>
    <jsp:include page="../common/template/template_head.jsp"/>
</head>
<body>

<h1>用户管理</h1>
<button id="addNewMember">
    添加新用户
</button>
<button id="refresh">
    刷新
</button>
<table id="memberTable">

</table>

<div id="memberModal" class="modal container fade in" tabindex="-1" style="height: auto;">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
        <h4 class="modal-title"><i class="icon-pencil"></i> 维护用户信息窗口</h4>
    </div>
    <span hidden="hidden" id="updateMemberId"></span>

    <div class="modal-body form" style="height: auto;">
        <form id="dialogForm" class="form-horizontal">
            <div class="form-body">
                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group">
                            <label class="col-md-4 control-label">登录名<span class="required"> * </span></label>

                            <div class="col-md-8">
                                <input type="hidden" id="userId">
                                <input type="text" class="form-control input-medium" placeholder="登录名"
                                       id="memberUsername">
                                <span class="help-block">此名称用于登录</span>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group last">
                            <label class="col-md-4 control-label">用户姓名<span class="required"> * </span></label>

                            <div class="col-md-8">
                                <input type="text" class="form-control input-medium" placeholder="用户姓名"
                                       id="memberName">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group">
                            <label class="col-md-4 control-label">登录密码<span class="required"> * </span></label>

                            <div class="col-md-8">
                                <input type="password" class="form-control input-medium" placeholder="登录密码"
                                       id="memberPassword">
                                <span class="help-block" id="passwordHelpBlock"></span>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group last">
                            <label class="col-md-4 control-label">确认密码<span class="required"> * </span></label>

                            <div class="col-md-8">
                                <input type="password" class="form-control input-medium" placeholder="确认密码"
                                       id="confirmPassword">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row" id="postField">
                    <div class="col-md-6">
                        <div class="form-group last">
                            <label class="col-md-4 control-label">用户其他信息<span class="required"> * </span></label>

                            <div class="col-md-8">
                                <label class="control-label">更改用户的状态</label>
                                <select id="updateMemberStatus" class="form-control input-medium">
                                    <option value="正常">正常</option>
                                    <option value="待审核">待审核</option>
                                    <option value="不可用">不可用</option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
    <div class="modal-footer">
        <button type="button" data-dismiss="modal" id="cancel" class="btn btn-default">取消</button>
        <button type="button" id="addMemberButton" class="btn blue">添加成员信息</button>
        <button type="button" id="updateMemberButton" class="btn blue">更新成员信息</button>
    </div>
</div>

</body>
<jsp:include page="../../common/template/template_script.jsp"/>
<script src="<c:url value="/static/admin/js/member/member.js"/>"></script>
<script>
    $(document).ready(function () {
        Member.init();
    });
</script>
</html>
