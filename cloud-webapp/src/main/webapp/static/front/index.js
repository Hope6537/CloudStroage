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

    /**
     * 当前被选中的hander的id
     */
    var selection;

    var fileCount;

    var allSelected;

    function getTypeIcon(type) {
        if (type == globalConstant.FILE) {
            return '<i class = "fa fa-file"></i>';
        } else if (type == globalConstant.FOLDER) {
            return '<i class = "fa fa-folder"></i>';
        }
    }

    var service = {

        getMember: function () {
            var $member = $('#member');
            member = JSON.parse($member.text());
            $member.text("");
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
                    if (globalFunction.returnResult(data)) {
                        var list = data.returnData.list;
                        fileCount = list.length;
                        var html;
                        for (var i = 0; i < list.length; i++) {
                            var hander = list[i];
                            var type = hander.folder;
                            html += '<tr class="odd gradeX"> ' +
                            '<td> <div class="checker"><span class=""><input type="checkbox" class="checkboxes" value="' + hander.handerId + '"></span></div> </td>' +
                            '<td> <a href="' + hander.handerId + '"> ' + getTypeIcon(type) + ' ' + hander.fileName + ' </a></td> ' +
                            '<td class="center"> ' + hander.fullPath + ' </td>' +
                            '<td class="center"> ' + hander.status + ' </td>' +
                            '</tr>';
                        }
                        if (html == undefined) {
                            toast.info("空文件夹");
                            html = '<tr class="odd gradeX"><td colspan="4" class="center">这里空空如也，什么都没有</td></tr>'
                        }
                        $("#tableContext").append(html);
                        service.initTable();
                    }
                }
            })
        },
        initRightClick: function () {
            $('#dataTable').find('tbody').contextmenu({
                target: '#context-menu',
                onItem: function (context, e) {
                    alert($(e.target).text());
                }
            });
        },
        getSelection: function () {
            var $selection = $("input[type='checkbox'].checkboxes");
            selection = new Array($selection.length);
            $selection.each(function (i) {
                if ($(this).parent().hasClass("checked")) {
                    selection[i] = $(this).val()
                }
            });
            for (var i = 0; i < selection.length; i++) {
                console.log(selection[i])
            }
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

        }
        ,
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
                "columns": [
                    {
                        "orderable": false
                    },
                    {
                        "orderable": true
                    },
                    {
                        "orderable": true
                    },
                    {
                        "orderable": true
                    },
                ],
                "pagingType": "bootstrap_full_number",
                "order": [
                    [1, "asc"]
                ]
            });
            table.find('.group-checkable').change(function () {
                var set = $(this).attr("data-set");
                var checked = $(this).is(":checked");
                var status = $(this).data("isc");
                $(set).each(function () {
                    if (status == 0) {
                        $(this).attr("checked", true);
                        $(this).parents('tr').toggleClass("active");
                        $(this).parents('tr').children(0).toggleClass("focus");
                        $(this).parents('tr').find("span").toggleClass("checked");
                    } else {
                        $(this).attr("checked", false);
                        $(this).parents('tr').removeClass("active");
                        $(this).parents('tr').children(0).removeClass("focus");
                        $(this).parents('tr').find("span").removeClass("checked");
                    }
                });
                if (status == 0) {
                    $(this).data("isc", 1);
                    allSelected = true;
                } else {
                    $(this).data("isc", 0);
                }
            });

            table.on('mousedown', 'tbody tr', function () {
                if (allSelected) {
                    allSelected = false;
                    table.find('.group-checkable').parents().removeClass("checked");
                }
                $(this).toggleClass("active");
                $(this).children(0).toggleClass("focus");
                $(this).find("span").toggleClass("checked");
            });
        }
        ,
        showUpload: function () {
            $("#full-width").modal();
        },
        initDropZone: function () {
            Dropzone.options.uploadzone = {
                init: function () {
                    this.on("addedfile", function (file) {
                        // Create the remove button
                        var removeButton = Dropzone.createElement("<button class='btn btn-sm btn-block'>Remove file</button>");

                        // Capture the Dropzone instance as closure.
                        var _this = this;

                        // Listen to the click event
                        removeButton.addEventListener("click", function (e) {
                            // Make sure the button click doesn't submit the form:
                            e.preventDefault();
                            e.stopPropagation();

                            // Remove the file preview.
                            _this.removeFile(file);
                            // If you want to the delete the file on the server as well,
                            // you can do the AJAX request here.
                        });

                        // Add the button to the file preview element.
                        file.previewElement.appendChild(removeButton);
                    });
                }
            }
        }
    };

    var handleEvent = function () {
        $(document).on("ready", service.initDropZone);
        $(document).on("ready", service.getMember);
        $(document).on("ready", service.getHander);
        $(document).on("ready", service.initRightClick);
        $("#toUpload").on("click", service.showUpload);
        $("#testButton").on("click", service.getSelection)
    };

    return {
        init: function () {
            handleEvent();
        }
    }

}();