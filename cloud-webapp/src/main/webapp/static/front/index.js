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

    /**
     * 当前被选中的数量
     */
    var selectionCount;

    /**
     * 当前文件的数量
     */
    var fileCount;

    /**
     * 是否全部选中
     */
    var allSelected;

    /**
     * 表格对象
     */
    var $dataTable;

    /**
     * 根据类型获取图标
     */
    function getTypeIcon(type) {
        if (type == globalConstant.FILE) {
            return '<i class = "fa fa-file"></i>';
        } else if (type == globalConstant.FOLDER) {
            return '<i class = "fa fa-folder"></i>';
        }
    }

    var service = {
        /**
         * 获得member的json对象
         */
        getMember: function () {
            var $member = $('#member');
            member = JSON.parse($member.text());
            $member.text("");
            parentId = $("#parentId").text();
            if (parentId == undefined) {
                parentId = "-1";
            }
        },
        /**
         * 载入hander对象，并调用表格初始化方法
         */
        getHander: function () {
            $("#tableContext").empty();
            $.ajax({
                url: basePath + "hander/" + parentId + "/son/wrapper",
                contentType: "application/json",
                type: "GET",
                success: function (data) {
                    if (globalFunction.returnResult(data, undefined, false)) {
                        var list = data.returnData.list;
                        fileCount = list.length;
                        var html;
                        for (var i = 0; i < list.length; i++) {
                            var hander = list[i];
                            var type = hander.folder;
                            html += '<tr class="odd gradeX"> ' +
                            '<td> <div class="checker"><span class=""><input type="checkbox" class="checkboxes" value="' + hander.handerId + '"></span></div> </td>' +
                            '<td> <a class ="toSon" href="javascript:;" data-href="' + (basePath + 'index?p=' + hander.handerId) + '" data-type = "' + type + '"> ' + getTypeIcon(type) + ' ' + hander.fileName + ' </a></td> ' +
                            '<td class="center"> ' + (hander.itemInfo.size == "0B" ? "" : hander.itemInfo.size) + ' </td>' +
                            '<td class="center"> ' + hander.updateDate + ' </td>' +
                            '</tr>';
                        }
                        if (html == undefined) {
                            toast.info("空文件夹");
                            html = '<tr class="odd gradeX"><td colspan="4" class="center">这里空空如也，什么都没有</td></tr>'
                        }
                        $("#tableContext").append(html);
                        service.initTable();
                    } else {
                        var html = '<tr class="odd gradeX"><td></td>' +
                            '<td colspan="4" class="center"> 空文件夹 </td>' +
                            '</tr>';
                        $("#tableContext").append(html);
                    }
                }
            })
        },
        /**
         * 跳转到子节点页面
         */
        toSonHander: function (a) {
            var type = a.data("type");
            console.log(a[0]);
            if (type == globalConstant.FOLDER) {
                window.location.href = a.data("href");
            } else if (type == globalConstant.FILE) {
                console.log("file")
            } else {
                console.log("no type");
            }

        },
        /**
         * 生成右键菜单
         */
        initRightClick: function () {
            service.getSelection();
            console.log(selectionCount)
            if (selectionCount > 1) {
                $("#rename").hide();
            } else {
                $("#rename").show();
            }
            $('#dataTable').find('tbody').contextmenu({
                target: '#context-menu',
                onItem: function (context, e) {
                    alert($(e.target).text());
                }
            });
        },
        /**
         * 生成被选中的handerId数组，同时给选中数量赋值
         */
        getSelection: function () {
            selectionCount = 0;
            var $selection = $("input[type='checkbox'].checkboxes");
            selection = new Array($selection.length);
            $selection.each(function (i) {
                if ($(this).parent().hasClass("checked")) {
                    selection[i] = $(this).val()
                }
            });
            for (var i = 0; i < selection.length; i++) {
                //console.log(selection[i])
                if (selection[i] != undefined) {
                    selectionCount++;
                }
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
            if ($dataTable != undefined) {
                $dataTable.destroy();
            }
            $dataTable = table.dataTable({
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
                    [2, "asc"]
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
            table.on('mousedown', 'tbody tr', function (e) {
                if (allSelected) {
                    allSelected = false;
                    table.find('.group-checkable').parents().removeClass("checked");
                }
                $(this).toggleClass("active");
                $(this).children(0).toggleClass("focus");
                $(this).find("span").toggleClass("checked");
                if (e.which == 3) {
                    service.initRightClick();
                }
            });
        }
        ,
        showUpload: function () {
            $("#uploadModal").modal();
        },
        initDropZone: function () {
            Dropzone.options.uploadzone = {
                init: function () {
                    this.on("addedfile", function (file) {
                        // Create the remove button
                        var removeButton = Dropzone.createElement("<button class='btn btn-sm btn-block'>移除上传队列</button>");
                        var _this = this;
                        removeButton.addEventListener("click", function (e) {
                            e.preventDefault();
                            e.stopPropagation();
                            _this.removeFile(file);
                        });
                        file.previewElement.appendChild(removeButton);
                    });
                }
            }
        },
        showNewFolder: function () {
            $("#newFolderModal").modal();
            $("#folderName").val(' ')
        },
        addNewFolder: function () {
            Pace.track(function () {
                var data = {
                    memberId: member.memberId,
                    fileName: $("#folderName").val().trim(),
                    itemId: 1,
                    folder: globalConstant.FOLDER,
                    status: globalConstant.STATUS_NORMAL,
                    parentId: parentId
                }
                $.ajax({
                    url: basePath + "hander",
                    type: "POST",
                    contentType: "application/json",
                    data: JSON.stringify(data),
                    success: function (data) {
                        if (globalFunction.returnResult(data, "成功创建文件夹")) {
                            $("#newFolderModal").modal('hide');
                            service.getHander();
                        } else {
                            toast.error("操作失败，请重试")
                        }
                    }
                })
            });
        }

    };

    var handleEvent = function () {
        $(document).on("ready", service.initDropZone);
        $(document).on("ready", service.getMember);
        $(document).on("ready", service.getHander);
        /*$(document).on("ready", service.initRightClick);*/
        $("#toUpload").on("click", service.showUpload);
        $("#testButton").on("click", service.getSelection);
        $("#toNewFolder").on("click", service.showNewFolder);
        $("#toRefresh").on("click", service.getHander);
        $("#buttonAddFolder").on("click", service.addNewFolder);
        $(".back").live("click", function () {
            window.location.href = $(this).find("a").attr("href");
        });
        $(".toSon").live("click", function () {
            service.toSonHander($(this))
        })
    };

    return {
        init: function () {
            handleEvent();
        }
    }

}();