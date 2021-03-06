<%--
  ~ Copyright (c) 2015. Hope6537 The Founder of Lab.JiChuang ,ChangChun University,
  ~ JiLin Province,China
  ~ JiChuang CloudStroage is a maven webapp using Hadoop Distributed File System for storage ' s Cloud Stroage System
  --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<script src='<c:url value="/static/common/bower_plugins/respond/respond.min.js"/>'></script>
<script src='<c:url value="/static/common/bower_plugins/moment/min/moment.min.js"/>'></script>
<script src="<c:url value="/static/common/bower_plugins/jquery/dist/jquery.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/static/common/bower_plugins/jquery-migrate/jquery-migrate.min.js"/>"
        type="text/javascript"></script>
<script src="<c:url value="/static/common/bower_plugins/jquery-ui/ui/minified/jquery-ui.min.js"/>"
        type="text/javascript"></script>
<script src="<c:url value="/static/common/bower_plugins/bootstrap/dist/js/bootstrap.min.js"/>"
        type="text/javascript"></script>
<script src="<c:url value="/static/common/bower_plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js"/>"
        type="text/javascript"></script>
<script src="<c:url value="/static/common/bower_plugins/jquery-slimscroll/jquery.slimscroll.min.js"/>"
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
<script src="<c:url value="/static/common/bower_plugins/ztree_v3/js/jquery.ztree.core-3.5.min.js"/>"
        type="text/javascript"></script>
<script src="<c:url value="/static/common/bower_plugins/bootstrap-gtreetable/dist/bootstrap-gtreetable.min.js"/>"
        type="text/javascript"></script>
<script src="<c:url value="/static/common/bower_plugins/bootstrap-gtreetable/dist/languages/bootstrap-gtreetable.zh-CN.js"/>"
        type="text/javascript"></script>
<script src="<c:url value="/static/common/bower_plugins/bootstrap-contextmenu/bootstrap-contextmenu.js"/>"
        type="text/javascript"></script>
<script src="<c:url value="/static/common/bower_plugins/cryptojslib/components/core-min.js"/>"
        type="text/javascript"></script>
<script src="<c:url value="/static/common/bower_plugins/cryptojslib/rollups/aes.js"/>" type="text/javascript"></script>
<script src="<c:url value="/static/common/bower_plugins/bootstrap-modal/js/bootstrap-modal.js"/>"
        type="text/javascript"></script>
<script src="<c:url value="/static/common/bower_plugins/bootstrap-modal/js/bootstrap-modalmanager.js"/>"
        type="text/javascript"></script>
<script src="<c:url value="/static/common/bower_plugins/zeroclipboard/dist/ZeroClipboard.min.js"/>"
        type="text/javascript"></script>
<script src='<c:url value="/static/common/bower_plugins/hope6537-plugin/global/plugins/excanvas.min.js"/>'></script>
<script src="<c:url value="/static/common/bower_plugins/hope6537-plugin/global/plugins/jquery.blockui.min.js"/>"
        type="text/javascript"></script>
<script src="<c:url value="/static/common/bower_plugins/hope6537-plugin/global/plugins/jquery.cokie.min.js"/>"
        type="text/javascript"></script>
<script src="<c:url value="/static/common/bower_plugins/hope6537-plugin/global/plugins/uniform/jquery.uniform.min.js"/>"
        type="text/javascript"></script>
<script src="<c:url value="/static/common/bower_plugins/hope6537-plugin/plugins/datatable/media/js/jquery.dataTables.min.js"/>"
        type="text/javascript"></script>
<script src="<c:url value="/static/common/bower_plugins/hope6537-plugin/plugins/datatable/plugins/bootstrap/dataTables.bootstrap.js"/>"
        type="text/javascript"></script>
<script src="<c:url value="/static/common/bower_plugins/hope6537-plugin/plugins/pace/pace.js"/>"
        data-pace-options='{ "ajax": true }' type="text/javascript"></script>
<script src="<c:url value="/static/common/bower_plugins/hope6537-plugin/plugins/dropzone/dropzone.js"/>"
        type="text/javascript"></script>
<script src="<c:url value="/static/common/bower_plugins/hope6537-plugin/global/scripts/metronic.js"/>"
        type="text/javascript"></script>
<script src="<c:url value="/static/common/bower_plugins/hope6537-plugin/layout/scripts/layout.js"/>"
        type="text/javascript"></script>
<script src="<c:url value="/static/common/bower_plugins/hope6537-plugin/layout/scripts/quick-sidebar.js"/>"
        type="text/javascript"></script>
<script src="<c:url value="/static/common/bower_plugins/hope6537-plugin/layout/scripts/demo.js"/>"
        type="text/javascript"></script>
<script src="<c:url value="/static/common/bower_plugins/hope6537-plugin/global/scripts/util.js"/>"
        type="text/javascript"></script>
<script src="<c:url value="/static/common/bower_plugins/hope6537-plugin/global/scripts/toast.js"/>"></script>
<script src="<c:url value="/static/common/bower_plugins/hope6537-plugin/global/scripts/jquery.copy.js"/>"></script>
<script src="<c:url value="/static/common/bower_plugins/hope6537-plugin/global/plugins/autoComplete/autoComplete.js"/>"></script>
<script src="<c:url value="/static/common/bower_plugins/hope6537-plugin/global/scripts/global.js"/>"></script>
<script src="<c:url value="/static/common/bower_plugins/hope6537-plugin/global/scripts/hashmap.js"/>"></script>
<script>
    $(document).ready(function () {
        Metronic.init();
        Layout.init();
        QuickSidebar.init();
        Demo.init();
        $(".sidebar-toggler").trigger("click");
    });
</script>

