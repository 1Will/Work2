<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="X-UA-COMPATIBLE" content="IE=EmulateIE7"/>
    <meta charset="UTF-8">
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
<#--功能区-->
<div class="operation_box">
    <ul class="action_button">
        <li><a href="javascript:void(0)" class="tb" onclick="back()"><i class="iconfont">&#xe642;</i>返回</a></li>
        <li><a href="javascript:void(0)" onclick="save()"><i class="iconfont">&#xe634;</i>保存</a></li>
    </ul>
</div>
<div class="form_box">
    <form action="${base}/xtcd/saveXtcd" id="saveForm" method="post" style="margin-top:18px">
        <input type="hidden" id="id" name="id" value="${(cd.id?c)!}">
        <input type="hidden" id="xstp" name="xstp" value="${(cd.xstp)!}">

        <table class="form" border="0" cellspacing="0" cellpadding="0">
            <colgroup>
                <col width="10%"/>
                <col/>
            </colgroup>
            <tr>
                <th><b>*</b><label for="cdmc">菜单名称</label></th>
                <td id="cdmc_input">
                    <div class="element_box">
                        <input class="i_text NotEmpty" displayName="菜单名称" name="cdmc" id="cdmc" value="${(cd.cdmc)!''}"
                               maxlength="50"/>
                    </div>
                </td>
            </tr>
            <tr>
                <th><label for="url">URL</label></th>
                <td id="url_input">
                    <div class="element_box">
                        <input class="i_text" name="url" id="url" value="${(cd.url)!''}"
                               maxlength="50"/>
                    </div>
                </td>
            </tr>
            <tr>
                <th><b>*</b><label for="url">所属系统</label></th>
                <td id="ssxt_input">
                    <div class="element_box">
                        <label><input type="radio" class="i_radio" name="ssxt" value="0"
                                      <#if ((cd.ssxt)!'0') == '0'>checked</#if>/> 外网</label>
                        <label><input type="radio" class="i_radio" name="ssxt" value="1"
                                      <#if (cd.ssxt)! == '1'>checked</#if>/> 内网</label>
                    </div>
                </td>
            </tr>
            <tr>
                <th><label for="sjcdmc">上级菜单</label></th>
                <td>
                    <div class="element_box">
                        <input type="hidden" id="sjcdId" value="${(cd.sjcdId?c)!}" name="sjcdId">
                        <input id="sjcdmc" class="i_text" name="sjcdmc"
                               value="${(cd.sjcdmc)!''}" title="请选择上级菜单" readonly
                               onclick="getCdTree('${(cd.id?c)!}','${(cd.ssxt)!'0'}')">
                    </div>
                </td>
            </tr>
            <tr>
                <th><b>*</b><label for="pxh">排序号</label></th>
                <td id="pxh_input">
                    <div class="element_box">
                        <input class="i_text NotEmpty" displayName="排序号" name="pxh" id="pxh" maxlength="10"
                               onkeyup="this.value=this.value.replace(/\D/g,'')"
                               value="<#if cd??&&cd.id??>${(cd.pxh?c)!''}<#else >${(initXh?c)!''}</#if>"/>
                    </div>
                </td>
            </tr>
        </table>
    </form>
</div>

<#--script区域-->
<script>

    //监听radio框的变化(这儿用attr方法在ie下不行,原因不明，改用click)
    $(function () {
        //获取id
        var id = $('#id').val();
        $('input:radio[name="ssxt"]').change(function () {
            //清空上级菜单,并绑定新的事件
            $('#sjcdId').val("");
            $('#sjcdmc').val("");
            if (this.value === '0') {
                $("#sjcdmc").removeAttr('onclick').click(function () {
                    getCdTree(id, '0');
                });
            } else {
                $('#sjcdmc').removeAttr('onclick').click(function () {
                    getCdTree(id, '1');
                });
            }
        })
    });

    //获取菜单树 type:0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
    function getCdTree(id, ssxt) {
        var url = "${base}/tree/cdTree?id=" + id + "&ssxt=" + ssxt;
        var title;
        if (ssxt === '0') {
            title = '(外网)';
        } else {
            title = '(内网)';
        }
        openPage({
            area: ['300px', '500px'],
            type: 2,
            title: "上级菜单" + title,
            shade: 0.5,
            content: url
        });
    }


    //返回
    function back() {
        window.location.href = "${base}/xtcd/xtcdIndex";
    }

    //保存
    function save() {
        var response = true;
        //验证非空表单项
        $(".NotEmpty").each(function (i, e) {
            response = isNotEmpty(e);
            if (!response) {
                return false;
            }
        });
        if (response) {
            var id = $("input[name='id']").val();
            var cdmc = $("input[name='cdmc']").val();
            var ssxt = $("input:radio[name='ssxt']:checked").val();
            if (!checkCdmc(id, cdmc, ssxt)) {
                MaskLayer("数据保存中，请稍后");
                $('#saveForm').ajaxSubmit(function (ar) {
                    closeBg();
                    if (ar === '1') {
                        top.layer.alert("保存成功", {icon: 1});
                        window.location.href = "${base}/xtcd/xtcdIndex";
                    } else {
                        top.layer.alert("保存失败", {icon: 2});
                    }
                })
            } else {
                top.layer.alert("该名称已存在,请重新填写", {icon: 2});
            }
        }

    }

    //判断名称是否存在
    function checkCdmc(id, cdmc, ssxt) {
        var flag = false;
        $.ajax({
            url: "${base}/xtcd/checkCdmc",
            data: {id: id, cdmc: cdmc, ssxt: ssxt},
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
