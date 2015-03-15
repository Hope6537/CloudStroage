<%--
  ~ Copyright (c) 2015. Hope6537 The Founder of Lab.JiChuang ,ChangChun University,
  ~ JiLin Province,China
  ~ JiChuang CloudStroage is a maven webapp using Hadoop Distributed File System for storage ' s Cloud Stroage System
  --%>

<%--
  Created by IntelliJ IDEA.
  User: Hope6537
  Date: 2015/3/14
  Time: 14:43
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!-- BEGIN SIDEBAR -->
<div class="page-sidebar-wrapper">
    <div class="page-sidebar navbar-collapse collapse">
        <!-- BEGIN SIDEBAR MENU -->
        <!-- DOC: Apply "page-sidebar-menu-light" class right after "page-sidebar-menu" to enable light sidebar menu style(without borders) -->
        <!-- DOC: Apply "page-sidebar-menu-hover-submenu" class right after "page-sidebar-menu" to enable hoverable(hover vs accordion) sub menu mode -->
        <!-- DOC: Apply "page-sidebar-menu-closed" class right after "page-sidebar-menu" to collapse("page-sidebar-closed" class must be applied to the body element) the sidebar sub menu mode -->
        <!-- DOC: Set data-auto-scroll="false" to disable the sidebar from auto scrolling/focusing -->
        <!-- DOC: Set data-keep-expand="true" to keep the submenues expanded -->
        <!-- DOC: Set data-auto-speed="200" to adjust the sub menu slide up/down speed -->
        <ul class="page-sidebar-menu" data-keep-expanded="false" data-auto-scroll="true" data-slide-speed="200">
            <!-- DOC: To remove the sidebar toggler from the sidebar you just need to completely remove the below "sidebar-toggler-wrapper" LI element -->
            <li class="sidebar-toggler-wrapper">
                <!-- BEGIN SIDEBAR TOGGLER BUTTON -->
                <div class="sidebar-toggler">
                </div>
                <!-- END SIDEBAR TOGGLER BUTTON -->
            </li>
            <!-- DOC: To remove the search box from the sidebar you just need to completely remove the below "sidebar-search-wrapper" LI element -->
            <li class="sidebar-search-wrapper">
                <!-- BEGIN RESPONSIVE QUICK SEARCH FORM -->
                <!-- DOC: Apply "sidebar-search-bordered" class the below search form to have bordered search box -->
                <!-- DOC: Apply "sidebar-search-bordered sidebar-search-solid" class the below search form to have bordered & solid search box -->
                <form class="sidebar-search " action="extra_search.html" method="POST">
                    <a href="javascript:;" class="remove">
                        <i class="icon-close"></i>
                    </a>

                    <div class="input-group">
                        <input type="text" class="form-control" placeholder="在我的文件内搜索">
							<span class="input-group-btn">
							<a href="javascript:;" class="btn submit"><i class="icon-magnifier"></i></a>
							</span>
                    </div>
                </form>
                <!-- END RESPONSIVE QUICK SEARCH FORM -->
            </li>
            <li class="start active open">
                <a href="javascript:;">
                    <i class="icon-home"></i>
                    <span class="title">主页</span>
                    <span class="selected"></span>
                    <span class="arrow open"></span>
                </a>
            </li>
            <li>
                <a href="javascript:;">
                    <i class="icon-picture"></i>
                    <span class="title">图片</span>
                    <span class="arrow "></span>
                </a>
            </li>
            <li>
                <a href="javascript:;">
                    <i class="icon-docs"></i>
                    <span class="title">文档</span>
                    <span class="arrow "></span>
                </a>
            </li>
            <li>
                <a href="javascript:;">
                    <i class="icon-film"></i>
                    <span class="title">视频</span>
                    <span class="arrow "></span>
                </a>
            </li>
            <li>
                <a href="javascript:;">
                    <i class="icon-music-tone"></i>
                    <span class="title">音乐</span>
                    <span class="arrow "></span>
                </a>
            </li>
            <li>
                <a href="javascript:;">
                    <i class="icon-compass"></i>
                    <span class="title">压缩包</span>
                    <span class="arrow "></span>
                </a>
            </li>
            <li class="heading">
                <h3 class="uppercase"></h3>
            </li>
            <li>
                <a href="javascript:;">
                    <i class="icon-share"></i>
                    <span class="title">我的分享</span>
                    <span class="arrow "></span>
                </a>
            </li>
            <li class="heading">
                <h3 class="uppercase"></h3>
            </li>
            <li>
                <a href="javascript:;">
                    <i class="icon-trash"></i>
                    <span class="title">回收站</span>
                    <span class="arrow "></span>
                </a>
            </li>
        </ul>
        <!-- END SIDEBAR MENU -->
    </div>
</div>
<!-- END SIDEBAR -->