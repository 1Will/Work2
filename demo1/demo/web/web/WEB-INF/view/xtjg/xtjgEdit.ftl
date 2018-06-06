<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=8"/>
    <title>系统机构维护</title>
<#include "../header.ftl">
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
            <a href="javascript:void(0)" class="tb" onclick="backToList()"><i class="iconfont">&#xe642;</i>返回</a>
        </li>
        <li>
            <a onclick="xtjgSave()"><i class="iconfont">&#xe634;</i>保存</a>
        </li>
    </ul>
</div>
<div class="form_box">
    <form id="saveForm" method="post" action="${base}/xtjg/saveXtjg">
        <input type="hidden" id="id" name="id" value="${(xtjg.id)!}"/>
        <table class="form" border="0" cellspacing="0" cellpadding="0">
            <col width="100px"/><col/>
            <tr>
                <th><b>*</b>机构名称</th>
                <td id="jgmc_input">
                    <div class="element_box">
                        <input class="i_text NotEmpty" type="text" name="jgmc" title="机构名称" id ="jgmc"
                               value="${(xtjg.jgmc ?html)!}" maxlength="25"/>
                    </div>
                </td>
            </tr>
            <tr>
                <th><b>*</b>机构代码</th>
                <td id="jgdm_input">
                    <div class="element_box">
                        <input class="i_text NotEmpty" type="text" name="jgdm" title="机构代码" id="jgdm"
                               value="${(xtjg.jgdm ?html)!}" maxlength="25"/>
                    </div>
                </td>
            </tr>
            <tr>
                <th>上级机构</th>
                <td id="sjjg_mc_input">
                    <div class="element_box">
                        <input type="text" class="i_text" id="sjjgmc"
                               name="sjjgmc" title="上级机构" value="${(sjjg.jgmc)!(xtjg.sjjgmc)!}" onclick="getXtjgTree()" readonly/>
                        <input type="hidden" id="sjjg_id" name="sjjg_id" value="${(sjjg.id)!(xtjg.sjjg_id)!}"/>
                    </div>
                </td>
            </tr>
            <tr>
                <th>电话号码</th>
                <td id="dhhm_input">
                    <div class="element_box">
                        <input class="i_text Phone_Number" type="text" id="dhhm" name="dhhm" title="电话号码"
                               value="${(xtjg.dhhm ?html)!}"/>
                    </div>
                </td>
            </tr>
        </table>
    </form>
</div>
<script type="text/javascript" language="javascript">
    //返回列表页面
    function backToList() {
        window.location.href = "${base}/xtjg/xtjgRight";
    }
    //机构保存
    function xtjgSave() {
        var response;
        //验证非空
        $(".NotEmpty").each(function (i, e) {
            response = $.tooltips.isNotNull(e).response;
            if (!response) {
                hideAllTooltips();
                showTooltips(e.name + "_input", $(e).attr("title") + "不能为空!");
                $(e).focus();
                return false;
            }
        });
        if(response){
            //验证办公电话
            response = $.tooltips.isBgdh($('#dhhm')).response || $('#dhhm').val() == "";
            if(!response) {
                hideAllTooltips();
                showTooltips("dhhm_input","请输入正确的办公电话");
                return;
            }
        }
        if(response){
            var id = $("#id").val();
            var jgmc = $("#jgmc").val().trim();
            var jgdm = $("#jgdm").val().trim();
            if(isRepeat(id,jgmc,"jgmc")){
                top.layer.alert("机构名称重复，请重新输入", {icon: 2});
                return false;
            }
            if(isRepeat(id,jgdm,"jgdm")){
                top.layer.alert("机构代码重复，请重新输入", {icon: 2});
                return false;
            }
        }
        if(response) {
            MaskLayer("数据保存中，请稍候...");
            $("#saveForm").ajaxSubmit(function (data) {
                closeBg();
                if (data == 1) {
                    top.layer.alert("保存成功",{icon: 1});
                    window.location.href = "${base}/xtjg/xtjgRight";
                } else {
                    top.layer.alert("保存失败",{icon: 2});
                }
            });
        }
    }
    //验证机构名称或代码是否重复
    function isRepeat(id,mc,type) {
        var flag = false;
        $.ajax({
            url: "${base}/xtjg/isRepeat",
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
    //编辑页面打开机构树
    function getXtjgTree() {
        var url = "${base}/tree/xtjgTree?id=${(xtjg.id)!}&type=xtjg";
        openPage({
            area: ['300px', '600px'],
            type: 2,
            title: "上级机构",
            shade: 0.5,
            content: url
        });
    }
</script>
</body>
</html>