<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=8"/>
    <title>机构管理</title>
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
            <a href="javascript:void(0);" onclick="xtjgEdit('add','')">
                <i class="iconfont">&#xe641;</i>新增</a>
        </li>
    </ul>
</div>
<div class="query_box">
    <form id="searchForm" method="post">
        <input type="hidden" id="pageNo" name="pageNo" value=""/>
        <input type="hidden" name="pageSize" value="10"/>
        <input type="hidden" id="sjjg_id" name="sjjg_id" value="${sjjg_id!}"/>
        <input type="hidden" id="jglx" name="jglx" value="${jglx!}"/>
        <table cellpadding="0" cellspacing="0" border="0" class="query_form">
            <colgroup>
                <col width="55px" />
                <col width="220px" />
                <col width="70px" />
                <col width="220px" />
                <col width="70px" />
            </colgroup>
            <tr>
                <th>机构名称</th>
                <td>
                    <div class="query_element_box">
                        <input type="text" class="i_query_text" id="jgmc" name="jgmc" maxlength="25"/>
                    </div>
                </td>
                <th>机构代码</th>
                <td>
                    <div class="query_element_box">
                        <input type="text" class="i_query_text" id="jgdm" name="jgdm" maxlength="50"/>
                    </div>
                </td>
                <th><input class="query_button" type="button" value="查 询" onclick="query(1)"/></th>
            </tr>
        </table>
    </form>
</div>
<div id="xtjgList" class="list_box">
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
        window.parent.getTree();
        query(1);
    });

    //查询列表
    function query(pageNo) {
        MaskLayer("查询数据中,请稍候...");
        $("#pageNo").val(pageNo);
        $("#searchForm").ajaxSubmit({
            url: "${base}/xtjg/xtjgList",
            target: '#xtjgList',
            type: "post",
            success: function () {
                closeBg();
            },
            error: function (XmlHttpRequest, textStatus) {
                top.layer.alert("查询失败！错误类型：" + textStatus);
                closeBg();
            }
        });
    }

    //维护
    function xtjgEdit(type,id) {
        if(type == "add") {
            window.location.href = "${base}/xtjg/xtjgEdit?sjjg_id="+$("#sjjg_id").val();
        } else {
            window.location.href = "${base}/xtjg/xtjgEdit?id="+id;
        }
    }

    //系统机构删除
    function deleteXtjg(id) {
        if(hasXjjg(id)) {
            top.layer.alert("有下级机构，不可删除",{icon: 2});
            return false;
        }
        if(hasJgYh(id)) {
            top.layer.alert("机构下有员工，不可删除",{icon: 2});
            return false;
        }
        var confirm = function () {
            MaskLayer("数据删除中，请稍候...");
            $.post("${base}/xtjg/deleteXtjg", {id: id}, function (data) {
                closeBg();
                if (data == "1") {
                    top.layer.alert("删除成功",{icon: 1});
                    location.reload();
                } else {
                    top.layer.alert("删除失败",{icon: 2});
                }
            });
        };
        top.layer.confirm("确认要删除吗？",{icon: 3},confirm);
    }

    function changeJgid(jgid) {
        $("#sjjg_id").val(jgid);
        query(1);
    }

    //删除时检查有无下级机构
    function hasXjjg(id) {
        var flag=true;
        $.ajax({
            url: "${base}/xtjg/hasXjjg",
            data: {id: id},
            async: false,
            type: "post",
            success: function (data) {
                flag = data=="1";
            }
        });
        return flag;
    }

    //删除时检查机构下有无人员
    function hasJgYh(id) {
        var flag=true;
        $.ajax({
            url: "${base}/xtjg/hasJgYh",
            data: {id: id},
            async: false,
            type: "post",
            success: function (data) {
                return data=="1";
            }
        });
        return flag;
    }
</script>
</body>
</html>