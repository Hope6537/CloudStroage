/*
 * Copyright (c) 2015. Hope6537 The Founder of Lab.JiChuang ,ChangChun University,
 * JiLin Province,China
 * JiChuang CloudStroage is a maven webapp using Hadoop Distributed File System for storage ' s Cloud Stroage System
 */

/**
 * Created by Hope6537 on 2015/3/15.
 */
var Index = function () {

    /**
     * 当前操作用户对象
     */
    var member;

    /**
     * 当前所指向路径
     */
    var path;

    /**
     * 当前路径下父节点
     */
    var parentId;

    var service = {

        getMember: function () {
            member = JSON.parse($('#member').text());
            $("#member").text("");
            parentId = (window.location + "").split("parentId=")[1];
            if (parentId == undefined) {
                parentId = "-1";
            }
        },
        getHander: function () {
            $.ajax({
                url: basePath + "hander/" + parentId + "/son",
                contentType: "application/json",
                type: "GET",
                success: function (data) {
                    console.log(data)
                }
            })

        },
        initRightClick: function () {
            $('#dataTable').contextmenu({
                target: '#context-menu',
                onItem: function (context, e) {
                    alert($(e.target).text());
                }
            });
        },
        getTree: function () {
            $('#fileTable').gtreetable({
                    'source': function () {
                        return {
                            type: 'GET',
                            url: basePath + "hander/" + parentId + "/son/wrapper",
                            contentType: "application/json",
                            error: function (data) {
                                alert(data.status + ': ' + data.responseText);
                            }
                        }
                    },
                    'onSave': function (oNode) {
                        return {
                            type: 'POST',
                            url: !oNode.isSaved() ? 'nodeCreate' : 'nodeUpdate?id=' + oNode.getId(),
                            data: {
                                parent: oNode.getParent(),
                                name: oNode.getName(),
                                position: oNode.getInsertPosition(),
                                related: oNode.getRelatedNodeId()
                            },
                            dataType: 'json',
                            error: function (XMLHttpRequest) {
                                alert(XMLHttpRequest.status + ': ' + XMLHttpRequest.responseText);
                            }
                        };
                    }

                    ,
                    'onDelete': function (oNode) {
                        return {
                            type: 'POST',
                            url: 'nodeDelete?id=' + oNode.getId(),
                            dataType: 'json',
                            error: function (XMLHttpRequest) {
                                alert(XMLHttpRequest.status + ': ' + XMLHttpRequest.responseText);
                            }
                        };
                    }
                    ,
                    'language': 'zh-CN',
                    'types': {
                        default: 'glyphicon glyphicon-folder-open',
                        file: 'glyphicon glyphicon-file',
                        folder: 'glyphicon glyphicon-folder-open'
                    }
                }
            )
            ;

        },
        initTable: function () {

            var table = $('#dataTable');

            // begin first table
            table.dataTable({

                "paging": false,

                "searching": false,

                "language": {
                    "sProcessing": "处理中...",
                    "sLengthMenu": "显示 _MENU_ 项结果",
                    "sZeroRecords": "没有匹配结果",
                    "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
                    "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
                    "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
                    "sInfoPostFix": "",
                    "sSearch": "搜索:",
                    "sUrl": "",
                    "sEmptyTable": "表中数据为空",
                    "sLoadingRecords": "载入中...",
                    "sInfoThousands": ",",
                    "oPaginate": {
                        "sFirst": "首页",
                        "sPrevious": "上页",
                        "sNext": "下页",
                        "sLast": "末页"
                    },
                    "oAria": {
                        "sSortAscending": ": 以升序排列此列",
                        "sSortDescending": ": 以降序排列此列"
                    }
                },


                "bStateSave": true, // save datatable state(pagination, sort, etc) in cookie.

                "columns": [{
                    "orderable": false
                }, {
                    "orderable": true
                }, {
                    "orderable": false
                }, {
                    "orderable": false
                }, {
                    "orderable": true
                }, {
                    "orderable": false
                }],
                "pagingType": "bootstrap_full_number",
                "columnDefs": [{  // set default column settings
                    'orderable': false,
                    'targets': [0]
                }, {
                    "searchable": false,
                    "targets": [0]
                }],
                "order": [
                    [1, "asc"]
                ]
            });

            var tableWrapper = jQuery('#sample_1_wrapper');

            table.find('.group-checkable').change(function () {
                var set = jQuery(this).attr("data-set");
                var checked = jQuery(this).is(":checked");
                jQuery(set).each(function () {
                    if (checked) {
                        $(this).attr("checked", true);
                        $(this).parents('tr').addClass("active");
                    } else {
                        $(this).attr("checked", false);
                        $(this).parents('tr').removeClass("active");
                    }
                });
                jQuery.uniform.update(set);
            });

            table.on('change', 'tbody tr .checkboxes', function () {
                $(this).parents('tr').toggleClass("active");
            });

            tableWrapper.find('.dataTables_length select').
                addClass("form-control input-xsmall input-inline"); // modify table per page dropdown
        },
        showUpload: function () {
            $("#full-width").modal();
        }
    };

    var handleEvent = function () {
        $(document).on("ready", service.getMember);
        $(document).on("ready", service.getHander);
        $(document).on("ready", service.initTable);
        $(document).on("ready", service.initRightClick);
        $("#toUpload").on("click", service.showUpload);
    };

    return {
        init: function () {
            handleEvent();

        }
    }

}
();