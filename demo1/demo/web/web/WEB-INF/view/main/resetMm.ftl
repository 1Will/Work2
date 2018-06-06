<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="X-UA-COMPATIBLE" content="IE=EmulateIE7"/>
    <meta charset="UTF-8">
    <title>修改用户密码</title>
<#assign base=request.contextPath/>
<#include "../header.ftl">
    <style type="text/css">
        html,
        body {
            height: 100%;
            background: none;
            overflow: hidden;
        }
    </style>
</head>
<body>
<div>
    <div  class="form_box">
        <form action="" id="saveForm">
            <table  class="form" border="0" cellspacing="0" cellpadding="0">
                <colgroup>
                    <col width="20%"/>
                    <col />
                </colgroup>
                <tr>
                    <th><b>*</b>原密码</th>
                    <td id="mm_input">
                        <div class="element_box">
                            <input class="i_text NotEmpty" type="password" id="mm" title="初始密码" name="mm" value="" maxlength="10"/>
                        </div>
                    </td>
                </tr>
                <tr>
                    <th><b>*</b>新密码</th>
                    <td id="newPass1_input">
                        <div class="element_box">
                            <input class="i_text NotEmpty" type="password" id="newPass1" title="新密码" name="newMm" value="" maxlength="10"/>
                        </div>
                    </td>
                </tr>
                <tr>
                    <th><b>*</b>确认密码</th>
                    <td id="newPass2_input">
                        <div class="element_box">
                            <input class="i_text NotEmpty" type="password" id="newPass2"  title="再次输入" value="" maxlength="10"/>
                        </div>
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <!-- 下面按钮 -->
    <div class="w_button_box">
        <input type="button" id="save" value="保存" class="c_button" onclick="confirm()"/>
    </div>
</div>
</body>
<script type="text/javascript">
    function confirm() {
        var response1=true,response2=true;
        $(".NotEmpty").each(function (i, e) {
            response1 = $.tooltips.isNotNull(e).response;
            if (!response1) {
                hideAllTooltips();
                showTooltips(e.name + "_input", $(e).attr("title") + "不能为空!");
                $(e).focus();
                return false;
            }
        });
        if(response1) {
            var newPass1 = $("#newPass1").val();
            var newPass2 = $("#newPass2").val();
            if(newPass1!=newPass2) {
                response2 = false;
                hideAllTooltips();
                showTooltips("newPass2_input","两次输入不一致");
                return false;
            }
        }
       if(response1&&response2) {
           $("#saveForm").ajaxSubmit({
               url: "${base}/main/changePwd",
               type: "post",
               success: function (data) {
                   if (data === "1") {
                       top.layer.alert("保存成功", {icon: 1});
                       closePage();
                   } else if(data=="2"){
                       top.layer.alert("初始密码输入错误", {icon: 2});
                   } else {
                       top.layer.alert("保存失败", {icon: 2});
                   }
               }
           });
       }

    }
</script>
</html>