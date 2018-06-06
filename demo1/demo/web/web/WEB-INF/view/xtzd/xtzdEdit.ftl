<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=8"/>
    <title>字典项维护</title>
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
            <a class="tb" onclick="backToList()"><i class="iconfont">&#xe642;</i>返回</a>
        </li>
        <li>
            <a onclick="saveXtzd()"><i class="iconfont">&#xe634;</i>保存</a>
        </li>
    </ul>
</div>
<div class="form_box">
   <form id="saveForm" method="post" action="${base}/xtzd/saveXtzd">
        <input type="hidden" id="id" name="id" value="${(xtzd.id)!}"/>
        <table class="form" border="0" cellspacing="0" cellpadding="0">
            <col width="100px"/><col/>
            <tr>
                <th><b>*</b>字典名称</th>
                <td id="zdmc_input">
                    <div class="element_box">
                        <input class="i_text NotEmpty" title="字典名称" maxlength="15"
                               type="text" name="zdmc" value="${(xtzd.zdmc ?html)!}"/>
                    </div>
                </td>
            </tr>
            <tr>
                <th><b>*</b>字典代码</th>
                <td id="zddm_input">
                    <div class="element_box">
                        <input class="i_text NotEmpty" title="字典代码" maxlength="10"
                               type="text" name="zddm" value="${(xtzd.zddm ?html)!}"/>
                    </div>
                </td>
            </tr>
            <tr>
                <th>上级字典</th>
                <td>
                    <div class="element_box">
                        <select name="sjzd_id" class="i_select">
                            <option selected value="">请选择</option>
                        <#list sjzdList as o>
                            <option value="${(o.id)!}" <#if ((o.id)!0)==((xtzd.sjzd_id)!0)>selected="selected"
                                    <#elseif ((o.id)!0)==((sjzd_id)!0)>selected="selected"</#if>>
                            ${(o.zdmc)!}
                            </option>
                        </#list>
                        </select>
                    </div>
                </td>
            </tr>
            <tr>
                <th>序号</th>
                <td>
                    <div class="element_box">
                        <input class="i_text" type="text"
                               onkeyup="this.value=this.value.replace(/\D/g,'')"
                               name="xh" value="${(xtzd.xh ?html)!}"/>
                    </div>
                </td>
            </tr>
        </table>
    </form>
</div>
<script type="text/javascript" language="javascript">
    //返回列表页面
    function backToList() {
        window.history.back();
    }
    //字典项保存
    function saveXtzd(){
        var response = true;
        //验证非空表单项
        $(".NotEmpty").each(function (i, e) {
            response = $.tooltips.isNotNull(e).response;
            if (!response) {
                hideAllTooltips();
                showTooltips(e.name + "_input", $(e).attr("title") + "不能为空!");
                $(e).focus();
                return false;
            }
        });
        if (response) {
            if (validateForm()) {
                //数据保存
                MaskLayer("数据保存中，请稍候...");
                $("#saveForm").ajaxSubmit(function (data) {
                    closeBg();
                    if (data === "1") {
                        top.layer.alert("保存成功",{icon:1});
                        window.location.href = "${base}/xtzd/xtzdRight";
                    } else if (data === "2"){
                        top.layer.alert("字典代码重复，请重新输入",{icon:2});
                    }else{
                        top.layer.alert("保存失败",{icon:2});
                    }
                });
            }
        }
    }
</script>
</body>
</html>