<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=8"/>
    <title>字典管理右侧</title>
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
            <a href="javascript:void(0);" onclick="xtzdAdd()">
                <i class="iconfont">&#xe641;</i>新增
            </a>
        </li>
    </ul>
</div>
<div class="query_box">
    <form id="searchForm" method="post">
        <input type="hidden" id="pageNo" name="pageNo" value="1"/>
        <input type="hidden" name="pageSize" value="${pageSize}"/>
        <input type="hidden" id="sjzd" name="sjzd" value="${sjzd!}"/>
        <table cellpadding="0" cellspacing="0" border="0" class="query_form">
            <col width="55px" />
            <col width="220px" />
            <col width="70px" />
            <col width="220px" />
            <col width="70px" />
            <tr>
                <th>字典名称</th>
                <td>
                    <div class="query_element_box">
                        <input type="text" class="i_query_text" name="zdmc" />
                    </div>
                </td>
                <th>字典代码</th>
                <td>
                    <div class="query_element_box">
                        <input type="text" class="i_query_text" name="zddm" />
                    </div>
                </td>
                <th><input class="query_button" type="button" value="查 询" onclick="query(1)"/></th>
            </tr>
        </table>
    </form>
</div>
<div id="xtzdList" class="list_box">
</div>
<script type="text/javascript" language="javascript">
    //页面初始化
    $(function () {
        //初始化内容区域高度
        $(".list_box").height($(window).height() - $(".operation_box").outerHeight() - $(".query_box").outerHeight() - 20);
        //页面大小改变时，触发jquery的resize方法，自适应拖拽
        $(window).resize(function() {
            $(".list_box").height($(window).height() - $(".operation_box").outerHeight() - $(".query_box").outerHeight() - 20);
        });
        window.parent.loadZdTree();
        query(1);
    });
    function rightSjzd(id) {
        $("#sjzd").val(id);
        query(1);
    }
    //查询列表
    function query(pageNo) {
        MaskLayer("查询数据中,请稍候...");
        $("#pageNo").val(pageNo);
        $("#searchForm").ajaxSubmit({
            url: "${base}/xtzd/xtzdList",
            target: '#xtzdList',
            type: "post",
            success: function () {
                closeBg();
            },
            error: function (XmlHttpRequest, textStatus) {
                top.layer.alert("查询失败,错误类型：" + textStatus,{icon:2});
                closeBg();
            }
        });
    }
    //字典项新增
    function xtzdAdd() {
        window.parent.xtzdAddSrc($("#sjzd").val());
    }
    //字典项修改
    function xtzdEdit(id) {
        window.location.href = "${base}/xtzd/xtzdEdit?id="+id;
    }

    //字典项删除
    function deleteXtzd(id) {
        if(checkXtzd(id)) {
            top.layer.alert("有下级字典，不可删除",{icon:2});
            return false;
        }
        var confirm = function () {
            MaskLayer("数据删除中，请稍候...");
            $.post("${base}/xtzd/deleteXtzd", {id: id}, function (data) {
                closeBg();
                if (data === "1") {
                    top.layer.alert("删除成功",{icon:1});
                    window.location.href = "${base}/xtzd/xtzdRight";
                    window.parent.loadZdTree();
                } else {
                    top.layer.alert("删除失败",{icon:2});
                }
            });
        };
        openConfirm("确认要删除吗", confirm);
    }

    //删除时检查有无下级字典
    function checkXtzd(id) {
        var flag = false;
        $.ajax({
            url: "${base}/xtzd/checkXtzd",
            type: "post",
            data: {id: id},
            async: false,
            success: function (data) {
                if (data === "1") {
                    //代表该字典项存在下级
                    flag = true;
                }
            }
        });
        return flag;
    }
</script>
</body>
</html>