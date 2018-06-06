<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=8"/>
<#assign base=request.contextPath/>
    <title></title>
    <link rel="stylesheet" type="text/css" href="${base}/resources/css/login.css" media="all">
    <script type="text/javascript" src="${base}/resources/js/jquery-1.8.3.js"></script>
    <script type="text/javascript" src="${base}/resources/js/common.js"></script>
    <script type="text/javascript" src="${base}/resources/js/masklayer.js"></script>
    <style type="text/css">
        html,
        body {
            height: 100%;
            overflow: hidden
        }
    </style>
    <script type="text/javascript">
        //页面初始化
        $(function () {
            //初始化选中用户名输入框
            $("#itemBox").find("input[name='yhzh']").focus();
            /* 登录表单获取焦点变色 */
            $(".login-form").on("focus", "input", function () {
                $(this).closest('.item').addClass('focus');
            }).on("blur", "input", function () {
                $(this).closest('.item').removeClass('focus');
            });

            //回车登录
            document.onkeydown = function (evt) {
                var evts = window.event ? window.event : evt;
                if (evts.keyCode == 13) {
                    loginIn();
                }
            };
        });
        //用户登录
        function loginIn() {
            //移除错误信息
            $("#error").html("");
            //登录
            var jh = $("#loginName").val();
            if ($.trim(jh) == "") {
                $("#error").html("用户名不能为空");
                $("#jh").focus();
                return;
            }
            var mm = $("#loginPwd").val();
            if ($.trim(mm) == "") {
                $("#error").html("密码不能为空");
                $("#mm").focus();
                return;
            }
            $.post("${base}/main/loginIn", $("form").serialize(), function (data) {
                if (data == ZXJG.ERROR) {
                    $("#error").html("登录失败");
                } else if (data == ZXJG.REJECTED) {
                    $("#error").html("用户名密码错误");
                } else if (data == ZXJG.DISABLE) {
                    $("#error").html("该用户已被停用");
                } else {
                    window.location.href = "${base}/main/index";
                }
            });
        }

        //重置
        function loginReset() {
            $("#loginName").val("");
            $("#loginPwd").val("");
            $("#error").html("");
            $("#loginName").focus();
        }
    </script>
</head>

<body>
<div id="login-page">
    <div class="logo"></div>
    <div id="main-content">
        <!-- 主体 -->
        <div class="login-body">
            <div class="login-main pr">
                <div class="leftBox"></div>
                <form action="login" method="post" class="login-form">
                    <h3 class="welcome">用户登录</h3>
                    <div style="color:red;" id="error"></div>
                    <div id="itemBox" class="item-box">
                        <div class="item"><i class="icon-login-user"></i>
                            <input type="text" name="loginName" id="loginName" placeholder="请填写用户名"
                                   autocomplete="off"/>
                        </div>
                        <div class="item b0"><i class="icon-login-pwd"></i>
                            <input type="password" name="loginPwd" id="loginPwd" placeholder="请填写密码"
                                   autocomplete="off"/>
                        </div>
                    </div>
                    <div class="login_btn_panel">
                        <button class="login-btn" type="button" onclick="loginIn()"><span
                                class="on">登 录</span></button>
                        <button class="login-pkibtn" type="button" onclick="loginReset()"><span
                                class="on">重 置</span></button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>

</html>