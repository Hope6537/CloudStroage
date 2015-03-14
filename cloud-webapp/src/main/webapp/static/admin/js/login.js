/*
 * Copyright (c) 2015. Hope6537 The Founder of Lab.JiChuang ,ChangChun University,
 * JiLin Province,China
 * JiChuang CloudStroage is a maven webapp using Hadoop Distributed File System for storage ' s Cloud Stroage System
 */

/**
 * Created by Hope6537 on 2015/3/12.
 */
var Login = function () {

    var loginService = {
        login: function () {
            var data = {
                username: $("#username").val(),
                password: $("#password").val()
            }
            $.ajax({
                url: basePath + "/page/login",
                contentType: "application/json",
                type: "POST",
                data: JSON.stringify(data),
                success: function (data) {
                    if (globalFunction.returnResult(data)) {
                        setTimeout(function () {
                            window.location.href = basePath + "/page/toIndex";
                        }, 2000);
                    }
                }

            })
        },
        register: function () {
            var data = {
                name: $("#_name").val(),
                username: $("#_username").val(),
                password: $("#_password").val(),
                confrim: $("#_confirm").val()
            }
            var validate = $("#_validate").val();
            $.ajax({
                url: basePath + "/page/register",
                contentType: "application/json",
                type: "POST",
                data: JSON.stringify(data),
                success: function (data) {
                    if (globalFunction.returnResult(data)) {
                        setTimeout(function () {
                            window.location.href = basePath + "/page/toIndex";
                        }, 2000);
                    }
                }

            })
        }
    }

    var handleEvent = function () {
        $("#loginButton").on("click", loginService.login);
    }

    return {
        init: function () {
            handleEvent();
        }
    }

}();