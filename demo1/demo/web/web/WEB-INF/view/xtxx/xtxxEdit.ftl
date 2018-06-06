<html>
<head>
    <meta http-equiv="X-UA-COMPATIBLE" content="IE=EmulateIE7"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta charset="UTF-8">
    <title>信息发布</title>
<#include "../header.ftl">
    <link rel="stylesheet" type="text/css" href="${base}/resources/css/zTreeStyle/zTreeStyle.css"/>
    <script type="text/javascript" src="${base}/resources/js/jquery.ztree.core-3.4.js"></script>
    <script type="text/javascript" src="${base}/resources/js/jquery.ztree.excheck-3.4.js"></script>

    <script type="text/javascript" src="${base}/resources/js/xheditor/xheditor-1.1.14-zh-cn.min.js"></script>
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
        <li><a href="javascript:void(0)" onclick="save(0)"><i class="iconfont">&#xe634;</i>保存</a></li>
        <#if !(xtxx.fbzt)?? || xtxx.fbzt == '0'><!-- 发布状态不存在，或者存在为0都显示发布按钮 -->
        <li><a href="javascript:void(0)" onclick="save(1)"><i class="iconfont">&#xe634;</i>发布</a></li>
    </#if>

    </ul>
</div>
<!-- 信息编辑 -->
<div class="form_box">
    <form method="post" action="" id="saveForm" style="margin-top: 18px" enctype="multipart/form-data">
        <input type="hidden" name="fbIP" id="fbIP" value="${(xtxx.fbIP)!}"/>
        <input type="hidden" name="czsj" id="czsj" value="${(xtxx.czsj)!}"/>
        <input type="hidden" name="fbrId" id="fbrId" value="${(xtxx.fbrId)!}"/>
        <input type="hidden" name="fbdwId" id="fbdwId" value="${(xtxx.fbdwId)!}"/>
        <input type="hidden" name="fbsj" id="fbsj" value="${(xtxx.fbsj)!}"/>
        <input type="hidden" name="fbzt" id="fbzt" value="${(xtxx.fbzt)!}"/>
        <#if (xtxx.top)??>
            <input type="hidden" name="top" id="top" value="${xtxx.top?string("1","0")}"/>
        <#else>
            <input type="hidden" name="top" id="top" value="0"/>
        </#if>

        <table class="form" border="0" cellspacing="0" cellpadding="0">
            <colgroup>
                <col width="10%"/>
                <col/>
            </colgroup>
            <tr>
                <th><b>*</b><label for="xxbt">信息标题</label></th>
                <td id="xxbt_input">
                    <div class="element_box">
                        <input class="i_text NotEmpty" type="text" id="xxbt" name="xxbt" displayName="信息标题"
                               value="${(xtxx.xxbt)!}" maxlength="50"/>
                        <input type="hidden" name="id" id="id" value="${(xtxx.id)!}"/>
                    </div>
                </td>
            </tr>
            <tr>
                <th><b>*</b><label for="gjz">关键字</label></th>
                <td id="gjz_input">
                    <div class="element_box">
                        <input class="i_text NotEmpty" id="gjz" type="text" name="gjz" displayName="关键字"
                               value="${(xtxx.gjz)!}" maxlength="50"/>
                    </div>
                </td>

            </tr>
            <tr>
                <th>内容信息</th>
                <td>
                    <div class="element_box">
                        <textarea class="editor" runat="server" maxlength="500" id="nr" name="nr" cols="50" rows="20"
                                  style="width:80% ;height: 150px">${(xtxx.nr)!}</textarea>
                    </div>
                </td>

            </tr>
        </table>
    </form>
</div>
<div id="xtfj">
    <iframe name="fjIndex" id="fjIndex" frameborder="0" width="100%" height="100%" scrolling="auto"
            src="${base}/xtfj/xtfjIndex?xxId=${(xtxx.id)!}"></iframe>
</div>
<#--script区域-->
<script type="text/javascript" src="http://pv.sohu.com/cityjson?ie=utf-8"></script>
<script type="text/javascript">
    //初始化编辑器
    $(function () {
        $('#nr').xheditor({
            tools: 'full', width: '100%', height: '180px', forcePtag: false,
            upBtnText: '上传', upMultiple: 1, upLinkUrl: '${base}/xtxx/uploadFile',
            upImgUrl: '${base}/xtxx/uploadFile', upImgExt: 'jpg,jpeg,gif,png',
            upFlashUrl: '${base}/xtxx/uploadFile', upFlashExt: 'swf',
            upMediaUrl: '${base}/xtxx/uploadFile', upMediaExt: 'wmv,avi,wma,mp3,mid'
        });
    });

    //系统用户保存
    function save(fbzt) {
        var response;
        //非空项验证
        $(".NotEmpty").each(function (i, e) {
            response = isNotEmpty(e);
            if (!response) {
                return false;
            }
        });

        var nrLength = $('#nr').val().length;
        if (nrLength > 1000) {
            top.layer.alert("内容过长！", {icon: 2});
            return false;
        }

        if($("#fbIP").val()=='' && fbzt=='1'){
            //补填IP
            $("#fbIP").val(returnCitySN.cip);
        }
        //进行保存
        if (response) {
            $("#saveForm").ajaxSubmit({
                url: "${base}/xtxx/saveXtxx?fbzt=" + fbzt,
                type: "post",
                success: function (data) {
                    if (data === "1") {
                        if (fbzt == 1) {
                            top.layer.alert("发布成功", {icon: 1});
                        } else {
                            top.layer.alert("保存成功", {icon: 1});
                        }
                        window.location.href = "${base}/xtxx/xtxxIndex";
                    } else {
                        if (fbzt == 1) {
                            top.layer.alert("发布失败", {icon: 2});
                        } else {
                            top.layer.alert("保存失败", {icon: 2});
                        }
                    }
                }
            });
        }
    }

    //返回
    function closePage() {
        window.location.href = "${base}/xtxx/xtxxIndex";
    }


</script>
</body>
</html>