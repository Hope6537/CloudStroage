var Index = function () {

    var currentMember = {
        memberId: "",
        email: "",
        username: "",
        status: ""
    };


    function validateMember(type) {

        if (type != "add" && currentMember.memberId == "") {
            toast.error("数据错误，请刷新后重试");
            return false;
        }
        if (currentMember.email == "" || currentMember.username == "" || currentMember.status == "") {
            toast.error("请补全信息");
            return false;
        }

        if (currentMember.memberId == undefined || currentMember.email == undefined || currentMember.username == undefined || currentMember.status == undefined) {
            toast.error("数据出现错误");
            return false;
        }
        return true;

    }

    /**
     * 将数据重新储存
     * @param oTable
     * @param nRow
     */
    function restoreRow(oTable, nRow) {
        var aData = oTable.fnGetData(nRow);
        var jqTds = $('>td', nRow);
        for (var i = 0, iLen = jqTds.length; i < iLen; i++) {
            oTable.fnUpdate(aData[i], nRow, i, false);
        }
        oTable.fnDraw();
    }

    /**
     * 将数据打在页面上
     * @param oTable
     * @param nRow
     */
    function editRow(oTable, nRow) {
        var aData = oTable.fnGetData(nRow);
        var jqTds = $('>td', nRow);
        jqTds[0].innerHTML = '<input type="text" class="form-control input-small" value="' + aData[0] + '">';
        jqTds[1].innerHTML = '<input type="text" class="form-control input-small" value="' + aData[1] + '">';
        jqTds[2].innerHTML = '<input type="text" class="form-control input-small" value="' + aData[2] + '">';
        currentMember.email = aData[0];
        currentMember.username = aData[1];
        currentMember.status = aData[2];
        jqTds[3].innerHTML = '<a href="javascript:;" class="edit btn btn-circle red-sunglo btn-sm"  data-id="' + currentMember.memberId + '"  data-type="save" style="background-color:#5EC23C"> <i class="fa fa-save"></i> 保存信息</a>';
        jqTds[4].innerHTML = '<a href="javascript:;" class="cancel btn btn-circle red-sunglo btn-sm"> <i class="fa fa-backward"></i> 取消操作</a>';
    }

    /**
     * 将数据保存
     * @param oTable
     * @param nRow
     */
    function saveRow(oTable, nRow) {
        var jqInputs = $('input', nRow);
        currentMember.email = jqInputs[0].value;
        currentMember.username = jqInputs[1].value;
        currentMember.status = jqInputs[2].value;
        if (currentMember.memberId == "") {
            if (!validateMember("add")) {
                return false;
            }
            $.ajax({
                url: "memberAction.do",
                type: "POST",
                data: {
                    action: "memberAction",
                    event_submit_do_member: "anyThing",
                    _input_charset: "utf8",
                    member: JSON.stringify(currentMember)
                },
                contentType: "application/x-www-form-urlencoded",
                success: function (data) {
                    if (globalFunction.returnResult(data)) {
                        oTable.fnUpdate(jqInputs[0].value, nRow, 0, false);
                        oTable.fnUpdate(jqInputs[1].value, nRow, 1, false);
                        oTable.fnUpdate(jqInputs[2].value, nRow, 2, false);
                        oTable.fnUpdate('<a href="javascript:;" style="background-color:#66CCFF"  data-id="' + data.returnData.id + '" class="edit btn btn-circle red-sunglo btn-sm"> <i class="fa fa-edit"></i> 修改信息</a>', nRow, 3, false);
                        oTable.fnUpdate('<a href="javascript:;" data-id="' + currentMember.memberId + '" class="delete btn btn-circle red-sunglo btn-sm"> <i class="fa fa-trash-o"></i> 删除信息</a>', nRow, 4, false);
                        oTable.fnDraw();
                        return true;
                    }
                }
            });

        }
        else if (validateMember()) {
            $.ajax({
                url: "memberAction.do",
                type: "PUT",
                data: {
                    action: "memberAction",
                    event_submit_do_member: "anyThing",
                    _input_charset: "utf8",
                    member: JSON.stringify(currentMember)
                },
                contentType: "application/x-www-form-urlencoded",
                success: function (data) {
                    if (globalFunction.returnResult(data)) {
                        console.log(data);
                        oTable.fnUpdate(jqInputs[0].value, nRow, 0, false);
                        oTable.fnUpdate(jqInputs[1].value, nRow, 1, false);
                        oTable.fnUpdate(jqInputs[2].value, nRow, 2, false);
                        oTable.fnUpdate('<a href="javascript:;" style="background-color:#66CCFF"  data-id="' + currentMember.memberId + '" class="edit btn btn-circle red-sunglo btn-sm"> <i class="fa fa-edit"></i> 修改信息</a>', nRow, 3, false);
                        oTable.fnUpdate('<a href="javascript:;" data-id="' + currentMember.memberId + '" class="delete btn btn-circle red-sunglo btn-sm"> <i class="fa fa-trash-o"></i> 删除信息</a>', nRow, 4, false);
                        oTable.fnDraw();
                        return true;
                    }
                }
            });
        }
        else {
            return false;
        }
    }

    var service = {
        initActions: function () {
            var actions = '<a href="javascript:;" id="dataTable_add_new" class="btn btn-circle red-sunglo btn-sm"> <i class="fa fa-upload"></i> 添加新纪录 </a> ' +
                '<a href="index" id="toBack" class="btn btn-circle red-sunglo btn-sm" style="background-color:#66CCFF"> <i class="fa fa-level-up"></i> 返回上一级 </a> ' +
                '<a href="javascript:;" id="toUpload" class="btn btn-circle red-sunglo btn-sm"> <i class="fa fa-upload"></i> 上传文件 </a> ' +
                '<a href="javascript:;" id="toNewFolder" class="btn btn-circle red-sunglo btn-sm" style="background-color:#6A99E2"> <i class="fa fa-folder"></i> 新建文件夹 </a> ' +
                '<a href="javascript:;" id="toRefresh" class="btn btn-circle red-sunglo btn-sm" style="background-color: #5EC23C"> <i class="fa fa-refresh"></i> 刷新 </a> ' +
                '<a class="btn btn-circle btn-icon-only btn-default fullscreen" href="#" data-original-title="" title="全屏"><i class="fa fa-fullscreen"></i> </a>';
            $(".actions").append(actions);
        },
        /**
         * 生成目录
         */
        initTable: function () {
            var table = $('#dataTable');
            var oTable = table.dataTable({
                "lengthMenu": [
                    [15, 20, -1],
                    [15, 20, "All"]
                ],
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
                "pageLength": 10,
                "paging": true,
                "searching": false,
                "columnDefs": [{
                    'orderable': true,
                    'targets': [0]
                }, {
                    "searchable": true,
                    "targets": [0]
                }],
                "order": [
                    [0, "asc"]
                ]
            });

            var tableWrapper = $("#dataTable_1_wrapper");

            tableWrapper.find(".dataTables_length select").select2({
                showSearchInput: false //hide search box with special css class
            }); // initialize select2 dropdown

            var nEditing = null;
            var nNew = false;

            $('#dataTable_add_new').live("click", function (e) {
                e.preventDefault();
                if (nNew && nEditing) {
                    if (confirm("您想保存正在修改的数据么?")) {
                        if (saveRow(oTable, nEditing)) {
                            $(nEditing).find("td:first").html("Untitled");
                            nEditing = null;
                            nNew = false;
                        } else {
                            //TODO:
                            console.log("something else")
                        }
                    } else {
                        oTable.fnDeleteRow(nEditing);
                        nEditing = null;
                        nNew = false;
                        return;
                    }
                }
                var aiNew = oTable.fnAddData(['', '', '', '', '', '']);
                var nRow = oTable.fnGetNodes(aiNew[0]);
                editRow(oTable, nRow);
                nEditing = nRow;
                nNew = true;
            });

            table.on('click', '.delete', function (e) {
                e.preventDefault();
                if (confirm("确认删除这条数据？") == false) {
                    return;
                }
                var nRow = $(this).parents('tr')[0];
                $.ajax({
                    url: "memberAction.do",
                    type: "PUT",
                    data: {
                        action: "memberAction",
                        event_submit_do_member: "anyThing",
                        _input_charset: "utf8",
                        memberId: $(this).data("id")
                    },
                    contentType: "application/x-www-form-urlencoded",
                    success: function (data) {
                        if (globalFunction.returnResult(data)) {
                            oTable.fnDeleteRow(nRow);
                            return true;
                        }
                    }
                });

            });

            table.on('click', '.cancel', function (e) {
                e.preventDefault();
                if (nNew) {
                    oTable.fnDeleteRow(nEditing);
                    nEditing = null;
                    nNew = false;
                } else {
                    restoreRow(oTable, nEditing);
                    nEditing = null;
                }
            });

            table.on('click', '.edit', function (e) {
                e.preventDefault();
                var nRow = $(this).parents('tr')[0];
                if (nEditing !== null && nEditing != nRow) {
                    restoreRow(oTable, nEditing);
                    editRow(oTable, nRow);
                    nEditing = nRow;
                } else if (nEditing == nRow && $(this).data('type') == "save") {
                    if (saveRow(oTable, nEditing)) {
                        nEditing = null;
                        toast.success("更新数据成功");
                    } else {
                        //TODO:
                        console.log("something else")
                    }
                } else {
                    currentMember.memberId = $(this).data("id");
                    editRow(oTable, nRow);
                    nEditing = nRow;
                }
            });
        },
        /**
         * 生成右键菜单
         */
        initRightClick: function () {
            $('#dataTable').find('tbody').contextmenu({
                target: '#context-menu'
            });
        },
        /**
         * 弹出创建文件夹模态窗口
         */
        showNewFolder: function () {
            $("#newFolderModal").modal();
            $("#folderName").val('');
            $("#buttonRenameModal").hide();
            $("#buttonAddFolder").show();
        },
        /**
         * 确认创建文件夹
         */
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

    var handleEvent = function () {

        $(document).on("ready", service.initActions);
        $(document).on("ready", service.initTable);
    };

    return {
        init: function () {
            handleEvent();
        }
    }
}();