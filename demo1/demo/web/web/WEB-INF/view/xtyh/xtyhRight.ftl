<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=8"/>
    <title>用户管理</title>
<#include "../header.ftl">
    <link rel="stylesheet" type="text/css" href="${base}/resources/css/zTreeStyle/zTreeStyle.css"/>
    <script type="text/javascript" src="${base}/resources/js/jquery.ztree.core-3.4.js"></script>
    <script type="text/javascript" src="${base}/resources/js/jquery.ztree.excheck-3.4.js"></script>
    <style type="text/css">
        html, body {
            height: 100%;
            overflow: hidden;
        }
    </style>
</head>

<body>
<div class="operation_box">
    <ul class="action_button">
        <li>
            <a href="javascript:void(0)" onclick="xtyhEdit('add',null)"><i class="iconfont">&#xe641;</i>新增</a>
        </li>
    </ul>
</div>
<div class="query_box">
    <form id="searchForm" method="post">
        <input type="hidden" id="pageNo" name="pageNo" value=""/>
        <input type="hidden" id="pageSize" name="pageSize" value="10"/>
        <input type="hidden" id="jgid" name="jgid" value=""/>
        <table cellpadding="0" cellspacing="0" border="0" class="query_form">
            <colgroup>
                <col width="55px"/>
                <col width="200px"/>
                <col width="100px"/>
            </colgroup>
            <tr>
                <th>用户名称</th>
                <td>
                    <div class="query_element_box">
                        <input id="yhmc" name="yhmc" class="i_query_text" maxlength="10" style="text-align: left"/>
                    </div>
                </td>
                <th><input class="query_button" type="button" value="查 询" onclick="query(1)"/></th>
            </tr>
        </table>
    </form>
</div>
<div id="xtyhList" class="list_box">
</div>
<script type="text/javascript" language="javascript">
    //页面初始化
    $(function () {
        //初始化内容区域高度
        $(".list_box").height($(window).height() - $(".operation_box").outerHeight() - $(".query_box").outerHeight() - 20);
        //页面大小改变时，触发jquery的resize方法，自适应拖拽
        $(window).resize(function () {
            $(".list_box").height($(window).height() - $(".operation_box").outerHeight() - $(".query_box").outerHeight() - 20);
        });
        query(1);
    });

    function query(pageNo) {
        MaskLayer("查询数据中,请稍候...");
        $("#pageNo").val(pageNo);
        $("#searchForm").ajaxSubmit({
            url: "${base}/xtyh/xtyhList",
            target: '#xtyhList',
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


    function xtyhEdit(type, id) {
        var jgid = $("#jgid").val();
        var url = "${base}/xtyh/xtyhEdit";
        if (type == "add") {
            url += "?id&jgid=" + jgid;
        } else {
            url += "?id=" + id + "&jgid=" + jgid;
        }
        window.location.href = url;
    }


    function deleteXtyh(id) {
        var confirm = function () {
            MaskLayer("数据删除中，请稍候...");
            //后台请求
            $.post("${base}/xtyh/deleteXtyh", {id: id}, function (data) {
                closeBg();
                if (data == "1") {
                    top.layer.alert("删除成功", {icon: 1});
                    location.reload();
                } else {
                    top.layer.alert("删除失败", {icon: 2});
                }
            });
        };
        top.layer.confirm("确认要删除吗？", {icon: 3}, confirm);
    }

    function changeJgid(jgid) {
        $("#jgid").val(jgid);
        query(1);
    }
</script>
</body>
</html>