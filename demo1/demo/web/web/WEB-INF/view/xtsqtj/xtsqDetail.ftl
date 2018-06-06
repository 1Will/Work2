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
<div class="form_box">
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
                            <input type="hidden" name="file" id="file">
                        <#if xtsq?exists && xtsq.zpmc?exists>
                            <label id="text">${xtsq.zpmc}</label>
                        <#else>
                            <label id="text">无图片</label>
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
</div>
</body>
</html>


