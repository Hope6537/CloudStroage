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

    var uploadInfoMap;

    var uploadzone;

    var fileCount;

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
            if (parentId == "-1") {
                $("#toBack").hide();
            }

        },
        /**
         * 载入hander对象，并调用表格初始化方法
         */
        getHander: function () {
            $("#tableContext").empty();
            if ($dataTable == undefined) {
                service.initTable();
            } else {
                $dataTable.fnClearTable();
            }
            $.ajax({
                url: basePath + "hander/" + parentId + "/son/wrapper",
                contentType: "application/json",
                type: "GET",
                success: function (data) {
                    if (!globalFunction.returnResult(data, undefined, false)) {
                        html = '<tr class="odd gradeX"><td colspan="4" class="center">这里空空如也，什么都没有</td></tr>';
                        //$("#tableContext").append(html);
                    } else {
                        /** @namespace data.returnData */
                        var list = data.returnData.list;
                        fileCount = list.length;
                        var html = '';
                        for (var i = 0; i < list.length; i++) {
                            var hander = list[i];
                            var type = hander.folder;
                            var value = JSON.stringify({
                                handerId: hander.handerId,
                                memberId: hander.memberId,
                                folder: type,
                                parentId: hander.parentId
                            });
                            var line1 = '<div class="checker"><span class=""><input type="checkbox" class="checkboxes" data-json=\'' + value + '\'></span></div>';
                            var line2 = '<a class ="toSon" href="javascript:;" data-href="' + (basePath + 'index?p=' + hander.handerId) + '" data-type = "' + type + '"> ' + getTypeIcon(type) + ' ' + hander.fileName + ' </a>';
                            var line3 = (hander.itemInfo.size == "0B" ? "" : hander.itemInfo.size);
                            var line4 = hander.updateDate;
                            $dataTable.fnAddData([line1, line2, line3, line4]);
                        }
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
            if (type == globalConstant.FOLDER) {
                window.location.href = a.data("href");
            } else if (type == globalConstant.FILE) {
            } else {
            }

        },
        /**
         * 生成右键菜单
         */
        initRightClick: function () {
            service.getSelection();
            if (selectionCount > 1) {
                $("#rename").hide();
                $("#open").hide();
                $()
            } else {
                $("#rename").show();
                $("#open").show();
            }
            $('#dataTable').find('tbody').contextmenu({
                target: '#context-menu'
            });
        },
        /**
         * 生成被选中的handerId数组，同时给选中数量赋值
         */
        getSelection: function () {
            selectionCount = 0;
            var $selection = $("input[type='checkbox'].checkboxes");
            var tmp = new Array($selection.length);
            $selection.each(function (i) {
                if ($(this).parent().hasClass("checked")) {
                    tmp[i] = $(this).data("json");
                    if (tmp[i] != undefined) {
                        selectionCount++;
                    }
                }
            });
            selection = new Array(selectionCount);
            var index = 0;
            for (var i = 0; i < tmp.length; i++) {
                if (tmp[i] != undefined) {
                    selection[index++] = (tmp[i]);
                }
            }
        },
        /**
         *
         */
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
                },
                'language': 'zh-CN',
                'types': {
                    default: 'glyphicon glyphicon-folder-open', file: 'glyphicon glyphicon-file',
                    folder: 'glyphicon glyphicon-folder-open'
                }
            });

        },
        initTable: function () {
            $dataTable = $('#dataTable').dataTable({
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
                    "sEmptyTable": "这里空空如也，什么都没有",
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
                'bStateSave': true,
                "columns": [{
                    "orderable": false
                }, {
                    "orderable": true
                }, {
                    "orderable": true
                }, {
                    "orderable": true
                }],
                "order": [
                    [2, "asc"]
                ]
            });
        },
        showUpload: function () {
            $("#uploadModal").modal();
            if (uploadInfoMap.count() == 0) {
                fileCount = 0;
                uploadzone.removeAllFiles();
            }
        },
        initDropZone: function () {
            if (uploadInfoMap == undefined) {
                uploadInfoMap = new HashMap();
            }
            if (uploadzone == undefined) {
                uploadzone = new Dropzone("form#uploadzone");
                uploadzone.on("addedfile", function (file) {
                    fileCount++;
                    if (fileCount > 14) {
                        toast.error("超过最大同时上传文件数，请上传完当前文件");
                        this.removeFile(file);
                        return;
                    }
                    $("#buttonUpload").hide();
                    // 增加删除按钮
                    var removeButton = Dropzone.createElement("<button class='btn btn-sm btn-block'>移除上传队列</button>");
                    var _this = this;
                    removeButton.addEventListener("click", function (e) {
                        e.preventDefault();
                        e.stopPropagation();
                        _this.removeFile(file);
                        fileCount--;
                        var response = file.xhr.response;
                        if (response != undefined) {
                            var data = JSON.parse(response);
                            uploadInfoMap.remove(data.returnData.itemId);
                        }
                    });
                    file.previewElement.appendChild(removeButton);
                });
                uploadzone.on("error", function (file) {
                    $("#buttonUpload").show();
                    toast.error("上传失败")
                });
                uploadzone.on("success", function (file, data) {
                    $("#buttonUpload").show();
                    if (globalFunction.returnResult(data, undefined, false)) {
                        var itemId = data.returnData.itemId;
                        uploadInfoMap.set(itemId, file.name);
                    }
                });
            }
        },
        confirmUpload: function () {
            var json = '';
            uploadInfoMap.forEach(
                function (value, key) {
                    json += '"' + key + '":"' + value + '",';
                }
            );
            json = '{' + json.substring(0, json.length - 1) + '}';
            if (json != '') {
                var data = {
                    itemIdAndName: JSON.parse(json),
                    parentId: parentId,
                    memberId: member.memberId
                };
                $.ajax({
                    url: basePath + "hander/addMulti",
                    type: "POST",
                    data: JSON.stringify(data),
                    contentType: "application/json",
                    success: function (data) {
                        if (globalFunction.returnResult(data, "上传成功")) {
                            uploadInfoMap.clear();
                            $("#uploadModal").modal('hide');
                            service.getHander();
                        }
                    }
                })
            } else {
                toast.info("您未上传任何文件")
            }

        },
        showNewFolder: function () {
            $("#newFolderModal").modal();
            $("#folderName").val('');
            $("#buttonRenameModal").hide();
            $("#buttonAddFolder").show();
        },
        addNewFolder: function () {
            $("#modalTitle").text("新建文件夹");
            $("#buttonAddFolder").show();
            $("#buttonRenameModal").hide();
            Pace.track(function () {
                var data = {
                    memberId: member.memberId,
                    fileName: $("#folderName").val().trim(),
                    itemId: 1,
                    folder: globalConstant.FOLDER,
                    status: globalConstant.STATUS_NORMAL,
                    parentId: parentId
                };
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

    var rightClickService = {

        deleteHander: function () {
            $.ajax({
                url: basePath + "/hander/deleteMulti",
                type: "DELETE",
                dataType: "json",
                contentType: "application/json",
                data: JSON.stringify(selection),
                success: function (data) {
                    if (globalFunction.returnResult(data, "成功删除文件")) {
                        service.getHander();
                    }
                }
            })
        },
        openHander: function () {
            var parentId = selection[0].handerId;
            window.location.href = basePath + "index?p=" + parentId;
        },
        showRenameFolder: function () {
            $("#modalTitle").text("文件重命名");
            $("#newFolderModal").modal();
            $("#folderName").val('');
            $("#renameHanderId").val(JSON.stringify(selection[0]));
            $("#buttonAddFolder").hide();
            $("#buttonRenameModal").show();
        },
        renameFolder: function () {
            Pace.track(function () {
                var hander = JSON.parse($("#renameHanderId").val());
                hander.fileName = $("#folderName").val().trim();
                $.ajax({
                    url: basePath + "hander/",
                    type: "PUT",
                    contentType: "application/json",
                    data: JSON.stringify(hander),
                    success: function (data) {
                        $("#newFolderModal").modal('hide');
                        if (globalFunction.returnResult(data, "重命名成功")) {
                            service.getHander();
                        }
                    }
                });
            });
        }
    };


    var handleEvent = function () {
        var table = $('#dataTable');
        Dropzone.autoDiscover = false;
        $(document).on("ready", service.initDropZone);
        $(document).on("ready", service.getMember);
        $(document).on("ready", service.getHander);
        /*$(document).on("ready", service.initRightClick);*/
        $("#toUpload").on("click", service.showUpload);
        $("#testButton").on("click", service.getSelection);
        $("#toNewFolder").on("click", service.showNewFolder);
        $("#toRefresh").on("click", service.getHander);
        $("#buttonAddFolder").on("click", service.addNewFolder);
        $("#buttonUpload").on("click", service.confirmUpload);
        $(".back").live("click", function () {
            window.location.href = $(this).find("a").attr("href");
        });
        $(".toSon").live("click", function () {
            service.toSonHander($(this))
        });

        $("#buttonRename").on("click", rightClickService.showRenameFolder);
        $("#buttonDelete").on("click", rightClickService.deleteHander);
        $("#buttonOpen").on("click", rightClickService.openHander);
        $("#buttonRenameModal").on("click", rightClickService.renameFolder);

        table.find('.group-checkable').on("change", function () {
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
    };

    return {
        init: function () {
            handleEvent();
        }
    }

}();