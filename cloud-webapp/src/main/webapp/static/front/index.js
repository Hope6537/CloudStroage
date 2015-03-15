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
            $('#context').contextmenu({
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

        }

    }

    var handleEvent = function () {
        $(document).on("ready", service.initRightClick);
        $(document).on("ready", service.getMember);
        $(document).on("ready", service.getHander);
        $(document).on("ready", service.getTree);
    }

    return {
        init: function () {
            handleEvent();

        }
    }

}
();