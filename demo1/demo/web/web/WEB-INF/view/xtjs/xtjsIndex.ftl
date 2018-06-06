<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=8"/>
    <title>角色管理首页</title>
<#include "../header.ftl">
    <style type="text/css">
        html, body {
            height: 100%;
            overflow: hidden;
        }
    </style>
</head>

<body>
<div class="base_box">

    <div class="operation_box">
        <ul class="action_button">
            <li>
                <a href="#none" onclick="editXtjs()"><i class="iconfont">&#xe641;</i>新增</a>
            </li>
        </ul>
    </div>
    <div class="query_box">
        <form id="searchForm" name="searchForm" method="post">
            <input type="hidden" id="pageNo" name="pageNo" value="1"/>
            <input type="hidden" name="pageSize" value="10"/>
            <table cellpadding="0" cellspacing="0" border="0" class="query_form">
                <col width="60px"/>
                <col width="180px"/>
                <col width="70px"/>
                <tr>
                    <th><label for="jsmc">角色名称</label></th>
                    <td>
                        <div class="query_element_box">
                            <input id="jsmc" class="i_query_text" name="jsmc" maxlength="10"/>
                        </div>
                    </td>
                    <th>
                        <input class="query_button" onclick="query(1)" type="button" value="查 询"/>
                    </th>
                </tr>
            </table>
        </form>
    </div>
    <div id="xtjsList" class="list_box"></div>
</div>
</body>
<script type="text/javascript" language="javascript">
    $(function () {
        //初始化内容区域高度
        $(".list_box").height($(window).height() - $(".operation_box").outerHeight() - $(".query_box").outerHeight() - 20);
        //页面大小改变时，触发jquery的resize方法，自适应拖拽
        $(window).resize(function () {
            $(".list_box").height($(window).height() - $(".operation_box").outerHeight() - $(".query_box").outerHeight() - 20);
        });
        query(1);
    });

    //角色管理列表
    function query(pageNo) {
        MaskLayer("查询数据中,请稍候...");
        $("#pageNo").val(pageNo);
        $("#searchForm").ajaxSubmit({
            url: "${base}/xtjs/xtjsList",
            target: '#xtjsList',
            type: "post",
            success: function (data) {
                closeBg();
            }
        });
    }

    //角色管理编辑
    function editXtjs(type, id) {
        var url = "${base}/xtjs/xtjsEdit";
        if (type === "edit") {
            url += "?id=" + id;
        }
        window.location.href = url;
    }

    //角色管理删除
    function delXtjs(id) {
        var confirm = function () {
            $.post("${base}/xtjs/deleteXtjs", {id: id}, function (data) {
                if (data == '1') {
                    top.layer.alert("删除成功", {icon: 1});
                    window.location.reload();
                } else {
                    top.layer.alert("删除失败", {icon: 2});
                }
            }, "json");
        };
        top.layer.confirm("确认要删除吗？", confirm);
    }
</script>
</html>