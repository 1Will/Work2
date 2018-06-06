<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=8"/>
    <title>菜单管理</title>
<#include "../header.ftl">
    <style type="text/css">
        html,
        body {
            height: 100%;
            overflow: hidden;
            background: none;
        }
    </style>
</head>
<body>
<div class="base_box">
<#--功能区-->
    <div class="operation_box">
        <ul class="action_button">
            <li>
                <a href="javascript:void(0)" onclick="edit('add','')"><i class="iconfont">&#xe641;</i>新增</a>
            </li>
        </ul>
    </div>
<#--搜索区-->
    <div class="query_box">
        <form id="cdglForm" method="post">
            <input type="hidden" id="pageNo" name="pageNo" value=""/>
            <input type="hidden" id="pageSize" name="pageSize" value="10"/>
            <table cellpadding="0" cellspacing="0" border="0" class="query_form">
                <colgroup>
                    <col width="55px"/>
                    <col width="200px"/>
                    <col width="70px"/>
                    <col width="150px"/>
                    <col width="70px"/>
                </colgroup>
                <tr>
                    <th><label for="cdmc">菜单名称</label></th>
                    <td>
                        <div class="query_element_box">
                            <input id="cdmc" name="cdmc" class="i_query_text" maxlength="50"/>
                        </div>
                    </td>
                    <th><label for="ssxt">所属系统</label></th>
                    <td>
                        <div class="element_box">
                            <select name="ssxt" class="i_select" id="ssxt">
                                <option value="">请选择</option>
                                <option value="0">外网</option>
                                <option value="1">内网</option>
                            </select>
                        </div>
                    </td>
                    <th><input class="query_button" type="button" value="查 询" onclick="query(1)"/></th>
                </tr>
            </table>
        </form>
    </div>
<#--列表区-->
    <div id="cdglDiv" class="list_box"></div>
</div>
<#--script区-->
<script type="text/javascript">
    //初始化
    $(function () {
        //初始化内容区域高度
        $(".list_box").height($(window).height() - $(".operation_box").outerHeight() - $(".query_box").outerHeight() - 20);
        //页面大小改变时，触发jquery的resize方法，自适应拖拽
        $(window).resize(function () {
            $(".list_box").height($(window).height() - $(".operation_box").outerHeight() - $(".query_box").outerHeight() - 20);
        });
        query(1);
    });

    //查询
    function query(pageNo) {
        var url = "${base}/xtcd/xtcdList";
        MaskLayer("查询数据中，请稍后...");
        $("#pageNo").val(pageNo);
        $("#cdglForm").ajaxSubmit({
            url: url,
            target: '#cdglDiv',
            type: 'post',
            success: function () {
                closeBg();
            },
            error: function (XmlHttpRequest, textStatus) {
                closeBg();
                top.layer.alert("查询失败！错误类型:" + textStatus, {icon: 2});
            }
        })
    }

    //删除
    function del(cdId, ssxt) {
        if (!hasChildMenu(cdId, ssxt)) {
            var confirm = function () {
                MaskLayer("数据删除中,请稍后...");
                $.ajax({
                    url: '${base}/xtcd/deleteCd',
                    type: 'post',
                    async: false,
                    data: {cdId: cdId, ssxt: ssxt},
                    success: function (data) {
                        closeBg();
                        if (data === '1') {
                            top.layer.alert("删除成功", {icon: 1});
                            window.location.reload();
                        } else {
                            top.layer.alert("删除失败", {icon: 2});
                        }
                    }
                });
            };
            top.layer.confirm("确认要删除吗", {icon: 3}, confirm)
        } else {
            top.layer.alert("该菜单有下级菜单,不可删除", {icon: 2});
        }
    }

    //是否有子菜单
    function hasChildMenu(cdId, ssxt) {
        var flag = false;
        $.ajax({
            url: "${base}/xtcd/hasChildMenu",
            data: {cdId: cdId, ssxt: ssxt},
            type: 'post',
            async: false,
            success: function (ar) {
                if (ar == "1") {
                    flag = true;
                }
            }
        });
        return flag;
    }

    //编辑
    function edit(type, id) {
        var url = "${base}/xtcd/xtcdEdit";
        if (type === "xg") {
            url += "?id=" + id;
        }
        window.location.href = url;
    }
</script>
</body>
</html>