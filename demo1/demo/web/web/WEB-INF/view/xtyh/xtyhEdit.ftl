<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="X-UA-COMPATIBLE" content="IE=EmulateIE7"/>
    <meta charset="UTF-8">
    <title>用户管理</title>
<#include "../header.ftl">
    <link rel="stylesheet" type="text/css" href="${base}/resources/css/zTreeStyle/zTreeStyle.css"/>
    <script type="text/javascript" src="${base}/resources/js/jquery.ztree.core-3.4.js"></script>
    <script type="text/javascript" src="${base}/resources/js/jquery.ztree.excheck-3.4.js"></script>
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
<div class="operation_box">
    <ul class="action_button">
        <li><a href="javascript:void(0)" onclick="closePage()" class="tb"><i class="iconfont">&#xe642;</i>返回</a></li>
        <li><a href="javascript:void(0)" onclick="xtyhSave()"><i class="iconfont">&#xe634;</i>保存</a></li>
    </ul>
</div>
<div  class="form_box">
    <form action="" id="saveForm" style="margin-top: 18px">
        <table  class="form" border="0" cellspacing="0" cellpadding="0">
            <colgroup>
                <col width="12%"/>
                <col />
                <col width="12%"/>
                <col />
            </colgroup>
            <tr>
                <th><b>*</b>用户名</th>
                <td id="xm_input">
                    <div class="element_box">
                        <input class="i_text NotEmpty" id="xm" type="text" title="姓名" name="xm" value="${(xtyh.xm)!}" maxlength="20"/>
                        <input class="i_text" type="hidden" name="id"  value="${(xtyh.id)!}"/>
                    </div>
                </td>
                <th>性别</th>
                <td>
                    <div class="element_box">
                        <#if xbList??>
                            <#list xbList as o>
                            ${(o.ZDMC)!}<input type="radio" class="i_radio" name="xb_no" title="性别"
                                               value="${(o.ID)!}" <#if (o.ID)??&&(xtyh.xb_no)??&&(o.ID)?string ==(xtyh.xb_no)>checked</#if>>
                            </#list>
                        </#if>
                    </div>
                </td>
            </tr>
            <tr>
                <th><b>*</b>登录名</th>
                <td id="dlm_input">
                    <div class="element_box">
                        <input class="i_text NotEmpty" title="登录名" type="text" name="dlm" value="${(xtyh.dlm)!}" maxlength="20"/>
                    </div>
                </td>
                <th><b>*</b>密码</th>
                <td id="mm_input">
                    <div class="element_box">
                        <input class="i_text NotEmpty" type="password" title="密码" name="mm" value="${(xtyh.mm)!}" maxlength="10"/>
                    </div>
                </td>
            </tr>
            <tr>
                <th>所属机构</th>
                <td>
                    <div class="element_box">
                        <input class="i_text" id="jgmc" type="text" name="jgmc" value="${(xtyh.jgmc)!}" onclick="getXtjgTree()" readonly/>
                        <input class="i_text" id="jg_id" type="hidden" name="jg_id" value="${(xtyh.jg_id)!}"/>
                    </div>
                </td>
                <th><b>*</b>身份证号码</th>
                <td id="sfzhm_input">
                    <div class="element_box">
                        <input class="i_text NotEmpty ID_Number" type="text" id="sfzhm" title="身份证号" name="sfzhm" value="${(xtyh.sfzhm)!}" maxlength="18"/>
                    </div>
                </td>
            </tr>
            <tr>
                <th>联系电话</th>
                <td id="lxdh_input">
                    <div class="element_box">
                        <input class="i_text Phone_Number" type="text" id="lxdh" title="联系电话" name="lxdh" value="${(xtyh.lxdh)!}"/>
                    </div>
                </td>
                <th>用户角色</th>
                <td>
                    <input id="jsMc" class="i_text" onclick="getXtjsList()" value="${(data.jsmcs)!""}" readonly/>
                    <input type="hidden" id="jsIds" name="jsIds" value="${(data.ids)!""}"/>
                </td>
            </tr>
        </table>
    </form>
</div>

<#--script区域-->
<script>

    $(function () {
        //身份证号添加失焦事件验证正确性
       $("#sfzhm").blur(function () {
           isIdCardNo($(this).val(),$(this).attr('id'));
       });
        //联系电话添加失焦事件验证正确性
       $("#lxdh").blur(function () {
           isTelphone(this);
       });
       //性别选择框若无值则默认选中男
       if($('input[type="radio"]:checked').length==0){
           $('input[type="radio"]:eq(0)').attr("checked","checked");
       }
    });

    //系统用户保存
    function xtyhSave() {
        var response;
        //非空项验证
        $(".NotEmpty").each(function (i, e) {
            response = $.tooltips.isNotNull(e).response;
            if (!response) {
                hideAllTooltips();
                showTooltips(e.name + "_input", $(e).attr("title") + "不能为空!");
                $(e).focus();
                return false;
            }
        });
        //身份证号验证是否正确
        if(response) {
          response = isIdCardNo( $("#sfzhm").val(),'sfzhm');
        }
        //验证身份证号是否重复
        if(response) {
            response = isIdNumberRepeated();
        }
        //联系电话验证
        if(response) {
            response = isTelphone( $("#lxdh"));
        }
        //验证登陆名是否重复
        if(response) {
            response = isDlmRepeated();
        }
        //进行保存
        if (response) {
            $("#saveForm").ajaxSubmit({
                url: "${base}/xtyh/xtyhSave",
                type: "post",
                success: function (data) {
                    if (data === "1") {
                        top.layer.alert("保存成功",  {icon: 1});
                        window.location.href = "${base}/xtyh/xtyhRight";
                    } else {
                        top.layer.alert("保存失败", {icon: 2});
                    }
                }
            });
        }
    }

    function closePage() {
        window.location.href = "${base}/xtyh/xtyhRight";
    }
    function getXtjgTree() {
        var url = "${base}/tree/xtjgTree";
        openPage({
            area: ['320px', '600px'],
            type: 2,
            title: "所属机构",
            shade: 0.5,
            content: url
        });
    }
    //验证登陆名是否存在重复,不等于1即为不重复
    function isDlmRepeated() {
        var result;
        $.ajax({
            url:"${base}/xtyh/isDlmRepeated",
            type:'post',
            async:false,
            data:{id:$("input[name='id']").val(),dlm:$("input[name='dlm']").val()},
            success:function (data) {
                result =  data;
                if(data==='1') {
                    top.layer.alert("该登录名已存在");
                }
            }
        });
        return result != '1';
    }

    //验证身份证号是否存在重复,不等于1即为不重复
    function isIdNumberRepeated() {
        var result;
        $.ajax({
            url:"${base}/xtyh/isIdNumberRepeated",
            type:'post',
            async:false,
            data:{id:$("input[name='id']").val(),idCard:$("#sfzhm").val()},
            success:function (data) {
                result =  data;
                if(data==='1') {
                    top.layer.alert("身份证号码重复，请重新输入");
                }
            }
        });
        return result != '1';
    }

    function getXtjsList() {
        var url ="${base}/xtyh/yhjsList?jsIds="+$("#jsIds").val();
        openPage({
            area:['300px', '500px'],
            type:2,
            title:"请选择用户角色",
            shade: 0.5,
            content:url,
            success: function (data) {
            }
        });
    }
</script>
</body>
</html>


