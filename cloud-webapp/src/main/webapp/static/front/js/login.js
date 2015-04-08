/*
 * Copyright (c) 2015. Hope6537 The Founder of Lab.JiChuang ,ChangChun University,
 * JiLin Province,China
 * JiChuang CloudStroage is a maven webapp using Hadoop Distributed File System for storage ' s Cloud Stroage System
 */

var Login = function () {

    var service = {

        login: function () {
            Pace.track(function () {
                $("#loginButton").hide();
                var data = {
                    username: $("#username").val(),
                    password: $("#password").val(),
                    remember: $("#remember").attr("selected") == "selected"
                }
                if (data.username != "" && data.password != "") {
                    data.password = globalFunction.Encrypt(data.password);
                    $.ajax({
                        url: basePath + "/login",
                        type: "POST",
                        contentType: "application/json",
                        data: JSON.stringify(data),
                        success: function (data) {
                            if (globalFunction.returnResult(data, "登录成功,正在跳转中")) {
                                setTimeout(function () {
                                    window.location.href = basePath + "index";
                                }, 2000)
                            }
                            else {
                                toast.error("错误的用户名或密码")
                                $("#loginButton").show();
                            }
                        }
                    })
                }
                else {
                    toast.error("请输入用户名和密码");
                    $("#loginButton").show();
                }
            });
        },
        register: function () {
            var data = {
                name: $("#register_name").val().trim(),
                username: $("#register_username").val().trim(),
                password: $("#register_password").val().trim()
            }

            if (data.name == "" || data.password == "" || data.username == "") {
                toast.error("请填写相关信息")
                return;
            }
            if (data.password != $("#register_confrim").val()) {
                toast.error("两次密码输入的不一致")
                return;
            }

            data.password = globalFunction.Encrypt(data.password);

            $.ajax({
                url: basePath + "/register",
                type: "POST",
                contentType: "application/json",
                data: JSON.stringify(data),
                success: function (data) {
                    if (globalFunction.returnResult(data, "注册成功,请查看邮箱邮件")) {
                        $('.login-form').show();
                        $('.register-form').hide();
                    } else {
                        toast.error("发生错误")
                    }
                }
            })
        },
        forgetPassword: function () {

        }


    }

    var handleEvent = function () {
        $('#forget-password').on("click", function () {
            $('.login-form').hide();
            $('.forget-form').show();
        });
        $('#back-btn').on("click", function () {
            $('.login-form').show();
            $('.forget-form').hide();
        });
        $('#register-btn').on("click", function () {
            $('.login-form').hide();
            $('.register-form').show();
        });
        $('#register-back-btn').on("click", function () {
            $('.login-form').show();
            $('.register-form').hide();
        });
        $("#loginButton").on("click", service.login);
        $("#register-submit-btn").on("click", service.register);
    };

    return {
        //main function to initiate the module
        init: function () {
            handleEvent()
        }

    };

}();