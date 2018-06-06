<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="X-UA-COMPATIBLE" content="IE=EmulateIE7"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta charset="UTF-8">
    <title>社区管理</title>
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

        .pic {
            width: 300px;
            height: 200px;
            padding-top: 5px;
            padding-left: 5px;
        }

        .pic img {
            width: 100%;
            height: 100%;
        }

        .a-upload {
            padding: 4px 10px;
            width: 100%;
            height: 100%;
            box-sizing: border-box;
            line-height: 20px;
            position: relative;
            cursor: pointer;
            color: #888;
            background: #fafafa;
            border: 1px solid #ddd;
            border-radius: 4px;
            overflow: hidden;
            display: inline-block;
            *display: inline;
            *zoom: 1;
            top: 2px;
        }

        .a-upload input {
            position: absolute;
            font-size: 100px;
            opacity: 0;
            filter: alpha(opacity=0);
            cursor: pointer;
            width: 100%;
            height: 100%;
            left: 0;
            top: 0;
        }

        .a-upload:hover {
            color: #444;
            background: #eee;
            border-color: #ccc;
            text-decoration: none
        }

    </style>
</head>
<body>
<div class="operation_box">
    <ul class="action_button">
        <li><a href="javascript:void(0)" onclick="closePage()" class="tb"><i class="iconfont">&#xe642;</i>返回</a></li>
        <li><a href="javascript:void(0)" onclick="xtsqSave()"><i class="iconfont">&#xe634;</i>保存</a></li>
    </ul>
</div>
<div class="form_box">
    <form method="post" action="" id="saveForm" style="margin-top: 18px" enctype="multipart/form-data">
        <table class="form" border="0" cellspacing="0" cellpadding="0">
            <colgroup>
                <col width="12%"/>
                <col/>
                <col width="12%"/>
                <col/>
            </colgroup>
            <tr>
                <th><b>*</b><label for="sqmc">社区名称</label></th>
                <td id="sqmc_input">
                    <div class="element_box">
                        <input class="i_text NotEmpty" type="text" id="sqmc" name="sqmc" displayName="社区名称"
                               value="${(xtsq.sqmc)!}" maxlength="20"/>
                        <input class="i_text" type="hidden" name="id" value="${(xtsq.id)!}"/>
                    </div>
                </td>

                <th>社区机构</th>
                <td id="jgmc_input">
                    <div class="element_box">
                        <input class="i_text" id="jgmc" type="text" name="jgmc" value="${(xtsq.jgmc)!}"
                               onclick="getXtjgTree()" readonly/>
                        <input class="i_text" id="jg_id" type="hidden" name="jgid" value="${(xtsq.jgId)!}"/>
                    </div>
                </td>

            </tr>
            <tr>
                <th>社区照片</th>
                <td>
                    <div class="element_box">
                        <a class="a-upload">
                            <input type="file" name="file" id="file">
                        <#if xtsq?exists && xtsq.zpmc?exists>
                            <label id="text">${xtsq.zpmc}</label>
                        <#else>
                            <label id="text">点击上传图片</label>
                        </#if>
                        </a>
                    </div>
                    <div id="pic" class="pic">
                    <#if xtsq?exists && xtsq.cclj?exists>
                        <img src="${base}${xtsq.cclj}" width="" height="" title="社区照片">
                    </#if>
                    </div>

                </td>

            </tr>

        </table>
    </form>
</div>


<#--script区域-->
<script>

    //系统用户保存
    function xtsqSave() {
        var response;
        //非空项验证
        $(".NotEmpty").each(function (i, e) {
            response = isNotEmpty(e);
            if (!response) {
                return false;
            }
        });
        //若选择上传文件，将进行文件类型判断
        if($("#file")[0].files[0] != null){
            response = fileType();
        }

        //进行保存
        if (response) {
            $("#saveForm").ajaxSubmit({
                url: "${base}/xtsq/xtsqSave",
                type: "post",
                success: function (data) {
                    if (data === "1") {
                        top.layer.alert("保存成功", {icon: 1});
                        window.location.href = "${base}/xtsq/xtsqRight";
                    } else {
                        top.layer.alert("保存失败", {icon: 2});
                    }
                }
            });
        }
    }

    function fileType() {
        if ("" != $("#file").val()) {
            var name = $("#file").val();
            if (!/\.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/.test(name)) {
                top.layer.alert("图片类型必须是.gif,jpeg,jpg,png中的一种");
                return false;
            }else {
                return true;
            }
        }
    }
    function closePage() {
        window.location.href = "${base}/xtsq/xtsqRight";
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

    $(function () {
        // 当 id 为 file 的对象发生变化时
        $("#file").change(function () {
            if (!fileType()) {
                return;
            }
            imgPreview();
            var name = $("#file").val().split("\\");
            $("#text").html(name[name.length - 1]);  //将 #file 的值赋给 #a
        })
    })


    //实现图片实时预览的函数
    function imgPreview(){
        //判断是否支持FileReader
        if (window.FileReader) {
            var reader = new FileReader();
        } else {
            alert("您的设备不支持图片预览功能，如需该功能请升级您的设备！");
        }
        //读取完成
        reader.onload = function(e) {
            $("#pic").html("<img src='"+e.target.result+"' />")
        };
        reader.readAsDataURL($("#file")[0].files[0]);
    }

    function changeJgid(jgid) {
        window.location.href = "${base}/xtsq/xtsqRight?jgid="+jgid;
    }
</script>
</body>
</html>


