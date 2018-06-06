<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=8"/>
<#assign base=request.contextPath/>
    <title>技防一体化系统</title>
    <link rel="stylesheet" type="text/css" href="${base}/resources/css/Global.css"/>
    <link rel="stylesheet" type="text/css" href="${base}/resources/css/Main.css"/>
    <link rel="stylesheet" type="text/css" href="${base}/resources/layer/skin/layer.css/"/>
    <link rel="stylesheet" type="text/css" href="${base}/resources/css/iconfont.css"/>
    <script type="text/javascript" src="${base}/resources/js/jquery-1.8.3.js" ></script>
    <script type="text/javascript" src="${base}/resources/layer/layer.js"></script>
    <script type="text/javascript" src="${base}/resources/js/common.js"></script>
    <style type="text/css">
        html,
        body {
            height: 100%;
            overflow: hidden;
        }
    </style>
</head>

<body>
<div class="top">
    <div class="logo">
        <img src="${base}/resources/image/logo.png" title="demo项目"/></div>
    <div class="user">
        <ul>
            <li><a href="remind.ftl;" target="MainIframeR"><i class="iconfont">&#xe627;</i>首页</a></li>
            <li><a href="javascript:void(0)" onclick="resetMm()"><i class="iconfont">&#xe660;</i>修改密码</a></li>
            <li><a href="javascript:void(0)" onclick="logout()"><i class="iconfont">&#xe611;</i>退出</a></li>
        </ul>
        <p><i class="iconfont yonghu">&#58893;</i>欢迎您：${(userName)!}</p>
    </div>
</div>

<div class="box">
    <iframe name="MainIframe" id="test" frameborder="0" width="100%" height="100%" scrolling="auto"
            src="${base}/main/left"></iframe>
</div>

<!--个人信息下拉-->
<div class="Navigation_simple">
    <div class="Navigation_simple_top">
        <img src="${base}/resources/image/tail_shadow2x.png"/>
    </div>
    <div class="Navigation_simple_center">
        <div class="Navigation_simple_box">
            <a onclick="logout()" class="btn_exit"><i class="iconfont">&#xe60a;</i>退出</a>
            <div class="touxxx">
                <img src="${base}/resources/image/135.jpg" class="toux"/>
                xxx派出所民警
            </div>
            <ul class="tasks">
                <li>单位单位单位单位单位单位</li>
                <li>地址地址地址地址地址地址地址地址地址地址地址地址地址地址地址地址地址地址地址地址地址地址</li>
            </ul>
            <ul class="tbtx">
                <li><a href="#none"><i class="iconfont">&#xe60f;</i>个人中心</a></li>
                <li><a href="#none"><i class="iconfont">&#xe607;</i>设置</a></li>
                <li><a href="#none"><i class="iconfont">&#xe605;</i>修改密码</a></li>
            </ul>
        </div>
    </div>
</div>

<script type="text/javascript" language="javascript">
    //页面初始化，页面全部dom节点载入后执行的方法
    $(function () {
        //初始化内容区域高
        $("#test").height($(window).height() - 65);
    });
    //页面大小改变时，触发jquery的resize方法，自适应拖拽
    $(window).resize(function () {
        $("#test").height($(window).height() - 65);
    });
    function logout() {
        top.layer.confirm("确认要退出",function () {
            $.post("${base}/main/loginOut", {}, function () {
                window.location.href = "${base}/main/login";
            });
        });
    }

    function resetMm() {
        var url ="${base}/main/resetMm";
        openPage({
            area:['500px', '300px'],
            type:2,
            title:"修改密码",
            shade: 0.5,
            content:url,
            success: function (data) {
            }
        });
    }
</script>
</body>
</html>