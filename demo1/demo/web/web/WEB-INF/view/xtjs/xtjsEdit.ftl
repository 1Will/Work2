<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=8"/>
    <title>角色管理编辑</title>
<#include "../header.ftl">
    <style type="text/css">
        html, body {
            width: 100%;
            height: 100%;
            overflow: hidden;
        }
    </style>
</head>

<body>
<div class="tcbox">
    <div class="operation_box">
        <ul class="action_button">
            <li><a href="javascript:void(0)" class="tb" onclick="back()"><i class="iconfont">&#xe642;</i>返回</a></li>
            <li><a href="javascript:void(0)" onclick="saveXtjs()"><i class="iconfont">&#xe634;</i>保存</a></li>
        </ul>
    </div>
    <form id="xtjsForm">
        <div class="form_box">
            <input type="hidden" id="jsId" name="id" value="${(xtjs.id)!''}"/>
            <table class="form" border="0" cellspacing="0" cellpadding="0">
                <colgroup>
                    <col width="150px">
                    <col/>
                </colgroup>
                <tr>
                    <th><b>*</b><label for="jsmc">角色名称</label></th>
                    <td id="jsmc_input">
                        <div class="element_box"><input id="jsmc" title="角色名称" class="i_text NotEmpty" name="jsmc"
                                                        value="${(xtjs.jsmc)!''}" maxlength="10"/></div>
                    </td>
                </tr>
                <tr>
                    <th><b>*</b><label for="jsdm">角色代码</label></th>
                    <td id="jsdm_input">
                        <div class="element_box"><input id="jsdm" title="角色代码" class="i_text NotEmpty" name="jsdm"
                                                        value="${(xtjs.jsdm)!''}" maxlength="5"
                                                        onkeyup="value=value.replace(/([\u4e00-\u9fa5]|[\（\）\《\》\——\；\，\。\“\”\<\>\！])+/g,'')"/>
                        </div>
                    </td>
                </tr>
                <tr>
                    <th>菜单</th>
                    <td>
                        <input id="cdName" class="i_text" name="cdName" readonly onclick="getCdTree(${(data.cdIds)!''})"
                               value="${(data.cdmcs)!''}"/>
                        <input type="hidden" id="cdId" name="jscdIds" value="${(data.cdIds)!''}"/>
                    </td>
                </tr>
            </table>
        </div>
    </form>
</div>
<script type="text/javascript" language="javascript">

    //初始化尺寸
    $(function () {
        $(".tcbox").height($(window).height() - $(".WindowMenu").outerHeight() - 15);
        $(window).resize(function () {
            $(".tcbox").height($(window).height() - $(".WindowMenu").outerHeight() - 15);
        });
    });

    function getCdTree(cdIds) {
        var url = "${base}/tree/cdTree?cdIds=" + $("#cdId").val() + "&type=jscd&ssxt=0";
        openPage({
            area: ['300px', '500px'],
            type: 2,
            title: "请选择菜单",
            shade: 0.5,
            content: url,
            success: function (data) {
            }
        });
    }

    //返回列表
    function back() {
        window.location.href = "${base}/xtjs/xtjsIndex";
    }

    //非空校验
    function validateNotEmpty() {
        var response = true;
        //验证非空表单项
        $(".NotEmpty").each(function (i, e) {
            response = $.tooltips.isNotNull(e).response;
            if (!response) {
                hideAllTooltips();
//                showTooltips(e.name + "_input", $(e).attr("displayName") + "不完整");
                showTooltips(e.name + "_input", $(e).attr("title") + "不可为空");
                $(e).focus();
                return false;
            }
        });
        return response;
    }

    function saveXtjs() {
        var jsmc = $("#jsmc").val($("#jsmc").val().trim()).val();
        var jsdm = $("#jsdm").val($("#jsdm").val().trim()).val();
        var id = $("input[name='id']").val();
        if (validateNotEmpty()) {
            if ($("#jsdm").val().trim().match(/^[A-Za-z0-9_]+$/) == null) {
                top.layer.alert("角色代码错误，请输入英文字母数字", {icon: 2});
                return false;
            }
            if(isRepeat(id,jsmc,"jsmc")){
                top.layer.alert("角色名称重复，请重新输入", {icon: 2});
                return false;
            }
            if(isRepeat(id,jsdm,"jsdm")){
                top.layer.alert("角色代码重复，请重新输入", {icon: 2});
                return false;
            }
            //数据保存
            $("#xtjsForm").ajaxSubmit({
                url: "${base}/xtjs/saveXtjs",
                type: "post",
                dataType: "json",
                success: function (ar) {
                    if (ar == '1') {
                        top.layer.alert("保存成功", {icon: 1});
                        window.location.href = "${base}/xtjs/xtjsIndex";
                    } else if (ar == "JSDMCF") {
                        top.layer.alert("角色代码重复，请重新输入", {icon: 2});
                    } else if (ar == "JSMCCF") {
                        top.layer.alert("角色代码重复，请重新输入", {icon: 2});
                    }else {
                        top.layer.alert("保存失败", {icon: 2});
                    }
                }
            });
        }
    }
    function isRepeat(id,mc,type) {
        var flag = false;
        $.ajax({
            url: "${base}/xtjs/isRepeat",
            data: {id: id, mc: mc, type: type},
            async: false,
            type: 'post',
            success: function (ar) {
                if (ar == "1") {
                    flag = true;
                }
            }
        });
        return flag;
    }
</script>
</body>
</html>