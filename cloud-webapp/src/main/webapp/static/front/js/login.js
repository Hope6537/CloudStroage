var Login = function () {
    var a = {
        login: function () {
            Pace.track(function () {
                $("#loginButton").hide();
                var c = {username: $("#username").val(), password: $("#password").val()};
                if (c.username != "" && c.password != "") {
                    c.password = globalFunction.Encrypt(c.password);
                    $.ajax({
                        url: basePath + "/login",
                        type: "POST",
                        contentType: "application/json",
                        data: JSON.stringify(c),
                        success: function (d) {
                            if (globalFunction.returnResult(d, "登录成功,正在跳转中")) {
                                setTimeout(function () {
                                    window.location.href = basePath + "index"
                                }, 2000)
                            } else {
                                toast.error("错误的用户名或密码");
                                $("#loginButton").show()
                            }
                        }
                    })
                } else {
                    toast.error("请输入用户名和密码");
                    $("#loginButton").show()
                }
            })
        }, register: function () {
            var c = {
                name: $("#register_name").val().trim(),
                username: $("#register_username").val().trim(),
                password: $("#register_password").val().trim()
            };
            if (c.name == "" || c.password == "" || c.username == "") {
                toast.error("请填写相关信息");
                return
            }
            if (c.password != $("#register_confrim").val()) {
                toast.error("两次密码输入的不一致");
                return
            }
            c.password = globalFunction.Encrypt(c.password);
            $.ajax({
                url: basePath + "/register",
                type: "POST",
                contentType: "application/json",
                data: JSON.stringify(c),
                success: function (d) {
                    if (globalFunction.returnResult(d, "注册成功,请查看邮箱邮件")) {
                        $(".login-form").show();
                        $(".register-form").hide()
                    } else {
                        toast.error("发生错误")
                    }
                }
            })
        }, forgetPassword: function () {
        }
    };
    var b = function () {
        $("#forget-password").on("click", function () {
            $(".login-form").hide();
            $(".forget-form").show()
        });
        $("#back-btn").on("click", function () {
            $(".login-form").show();
            $(".forget-form").hide()
        });
        $("#register-btn").on("click", function () {
            $(".login-form").hide();
            $(".register-form").show()
        });
        $("#register-back-btn").on("click", function () {
            $(".login-form").show();
            $(".register-form").hide()
        });
        $("#loginButton").on("click", a.login);
        $("#register-submit-btn").on("click", a.register)
    };
    return {
        init: function () {
            b()
        }
    }
}();