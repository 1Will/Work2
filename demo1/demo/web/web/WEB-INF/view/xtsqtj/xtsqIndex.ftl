<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=8"/>
    <title>社区管理</title>
<#include "../header.ftl">
    <style type="text/css">
        html, body {
            height: 100%;
            overflow: hidden;
        }
    </style>
</head>
<body>

<div class="query_box">
    <form id="searchForm" method="post">
        <input type="hidden" id="pageNo" name="pageNo" value=""/>
        <input type="hidden" id="pageSize" name="pageSize" value="10"/>
        <input type="hidden" id="jgId" name="jgId" value=""/>
    </form>
</div>
<div id="xtsqList" class="list_box"></div>
<script language="JavaScript">
    //页面初始化
    $(function () {
        //初始化内容区域高度
        $(".list_box").height($(window).height() - $(".operation_box").outerHeight() - $(".query_box").outerHeight() - 20);
        //页面大小改变时，触发jquery的resize方法，自适应拖拽
        $(window).resize(function () {
            $(".list_box").height($(window).height() - $(".operation_box").outerHeight() - $(".query_box").outerHeight() - 20);
        });
        var url = location.search.split("=");
        if (url.length>1){
            $("#jgId").val(url[url.length-1]);
        }
        query(1);
    });

    //查询列表
    function query(pageNo) {
        MaskLayer("查询数据中,请稍候...");
        $("#pageNo").val(pageNo);
        $("#searchForm").ajaxSubmit({
            url: "${base}/xtsqtj/xtsqList",
            target: '#xtsqList',
            type: "post",
            success: function (data) {
                closeBg();
            },
            error: function (XmlHttpRequest, textStatus) {
                top.layer.alert("查询失败！错误类型" + textStatus);
                closeBg();
            }
        });
    }

    //返回
    function back() {
        var sjjg = $('#sjjg').val();
        window.location.href = "${base}/xtsqtj/xtsqtjIndex?jgId="+sjjg;
    }
</script>
</body>