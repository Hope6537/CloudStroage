<%--
  ~ Copyright (c) 2015. Hope6537 The Founder of Lab.JiChuang ,ChangChun University,
  ~ JiLin Province,China
  ~ JiChuang CloudStroage is a maven webapp using Hadoop Distributed File System for storage ' s Cloud Stroage System
  --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!-- BEGIN CORE PLUGINS -->
<!--[if lt IE 9]>
<script src='<c:url value="/static/common/bower_plugins/respond/respond.min.js"/>'></script>
<script src='<c:url value="/static/common/bower_plugins/moment/min/moment.min.js"/>'></script>
<![endif]-->
<script src="<c:url value="/static/common/bower_plugins/jquery/dist/jquery.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/static/common/bower_plugins/jquery-migrate/jquery-migrate.min.js"/>"
        type="text/javascript"></script>
<!-- IMPORTANT! Load jquery-ui-1.10.3.custom.min.js before bootstrap.min.js to fix bootstrap tooltip conflict with jquery ui tooltip -->
<script src="<c:url value="/static/common/bower_plugins/jquery-ui/ui/minified/jquery-ui.min.js"/>"
        type="text/javascript"></script>
<script src="<c:url value="/static/common/bower_plugins/bootstrap/dist/js/bootstrap.min.js"/>"
        type="text/javascript"></script>
<script src="<c:url value="/static/common/bower_plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js"/>"
        type="text/javascript"></script>
<script src="<c:url value="/static/common/bower_plugins/jquery-slimscroll/jquery.slimscroll.min.js"/>"
        type="text/javascript"></script>
<script src='<c:url value="/static/common/global/plugins/excanvas.min.js"/>'></script>
<script src="<c:url value="/static/common/global/plugins/jquery.blockui.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/static/common/global/plugins/jquery.cokie.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/static/common/global/plugins/uniform/jquery.uniform.min.js"/>"
        type="text/javascript"></script>
<script src="<c:url value="/static/common/bower_plugins/bootstrap-switch/dist/js/bootstrap-switch.min.js"/>"
        type="text/javascript"></script>
<script src="<c:url value="/static/common/bower_plugins/toastr/toastr.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/static/common/bower_plugins/jquery-validation/dist/jquery.validate.min.js"/>"
        type="text/javascript"></script>
<script src="<c:url value="/static/common/bower_plugins/jquery-validation/src/localization/messages_zh.js"/>"
        type="text/javascript"></script>
<script src="<c:url value="/static/common/bower_plugins/select2/select2.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/static/common/bower_plugins/select2/select2_locale_zh-CN.js"/>"
        type="text/javascript"></script>

<script src="<c:url value="/static/common/bower_plugins/flot/jquery.flot.js"/>" type="text/javascript"></script>
<script src="<c:url value="/static/common/bower_plugins/flot/jquery.flot.resize.js"/>" type="text/javascript"></script>
<script src="<c:url value="/static/common/bower_plugins/flot/jquery.flot.categories.js"/>"
        type="text/javascript"></script>
<script src="<c:url value="/static/common/bower_plugins/bootstrap-gtreetable/dist/bootstrap-gtreetable.min.js"/>"
        type="text/javascript"></script>
<!-- END CORE PLUGINS -->
<!-- BEGIN PAGE LEVEL SCRIPTS -->
<script src="<c:url value="/static/common/global/scripts/metronic.js"/>" type="text/javascript"></script>
<!-- END PAGE LEVEL SCRIPTS -->
<script src="<c:url value="/static/common/global/scripts/global.js"/>" type="text/javascript"></script>
<script src="<c:url value="/static/admin/js/global.js"/>" type="text/javascript"></script>
<script src="<c:url value="/static/common/layout/scripts/layout.js"/>" type="text/javascript"></script>
<script src="<c:url value="/static/common/layout/scripts/quick-sidebar.js"/>" type="text/javascript"></script>
<script src="<c:url value="/static/common/layout/scripts/demo.js"/>" type="text/javascript"></script>
<script src="<c:url value="/static/common/global/scripts/util.js"/>" type="text/javascript"></script>
<script src="<c:url value="/static/common/global/scripts/toast.js"/>"></script>
<script src="<c:url value="/static/common/global/plugins/autoComplete/autoComplete.js"/>"></script>

<script>
    $(document).ready(function () {
        Metronic.init();
        Layout.init();
        QuickSidebar.init();
        $(".sidebar-toggler").trigger("click");
    });
</script>
<!-- END JAVASCRIPTS -->

