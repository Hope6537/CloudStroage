/*
 * Copyright (c) 2015. Hope6537 The Founder of Lab.JiChuang ,ChangChun University,
 * JiLin Province,China
 * JiChuang CloudStroage is a maven webapp using Hadoop Distributed File System for storage ' s Cloud Stroage System
 */

/**
 * Created by Hope6537 on 2015/3/14.
 */
var Member = function () {

    var $table = $("#memberTable")
    var memberList;

    var service = {
        refreshTable: function () {
            $table.empty();
            $.ajax({
                url: basePath + "/member",
                type: "GET",
                contentType: "application/json",
                success: function (data) {
                    if (globalFunction.returnResult(data, undefined, false)) {
                        memberList = data.returnData.list;
                        var tableContent = "<tr> <th>用户账号</th> <th>用户姓名</th> <th>用户状态</th> <th>用户操作</th> </tr>";
                        for (var i = 0; i < memberList.length; i++) {
                            var td1 = '<td>' + memberList[i].username + '</td>';
                            var td2 = '<td>' + memberList[i].name + '</td>';
                            var td3 = '<td>' + memberList[i].status + '</td>';
                            var td4 = '<td><a class="btn edit editId' + memberList[i].memberId + '">编辑操作</a></td>';
                            tableContent += "<tr>" + td1 + td2 + td3 + td4 + "</tr>";
                        }
                        $table.append(tableContent);
                    }
                }
            })
        }
    }

    var handleEvent = function () {
        $("#refresh").on("click", service.refreshTable)
        /* $("#memberTable").on("click", "a.edit", function () {
         $("#memberModal").modal();
         })*/
    }

    return {
        init: function () {
            handleEvent();
            service.refreshTable();
        }
    }

}();