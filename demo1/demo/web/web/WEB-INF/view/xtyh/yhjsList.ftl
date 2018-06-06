<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="X-UA-COMPATIBLE" content="IE=EmulateIE7"/>
    <meta charset="UTF-8">
    <title>角色列表</title>
<#include "../header.ftl">
    <style type="text/css">
        html,
        body {
            height: auto;
            overflow: auto;
            background: none;
        }
    </style>
</head>
<body>
<input type="hidden" id="jsIds" value="${(jsIds)!''}"/>
<div class="tree_box" style="overflow: auto;">
    <div class="zTreeDemoBackground left">
        <ul id="yhjsTree" class="ztree" style="overflow-y:scroll;height:395px"></ul>
    </div>
</div>
<div class="w_button_box">
    <input type="button" value="确定" class="c_button" onclick="confirmCallBack()"/>
</div>
</body>
<script type="text/javascript">
    //角色菜单树配置
    var setting = {
        check: {
            enable: true,
            chkStyle: "checkbox",
            chkboxType: {"Y": "ps", "N": "ps"},
            autoCheckTrigger: true
        },
        view:{
            showLine:false,
            selectedMulti:false
        },
        data:{
            simpleData:{
                enable:true
            }
        },
        callback:{
            onCheck: ztreeOncheck
        }
    } ;
    $(function () {
        var jsIds = $("#jsIds").val();
        //角色菜单树加载
        $.post("${base}/tree/getYhjsTree?jsIds="+jsIds, null, function (data) {
            $.fn.zTree.init($("#yhjsTree"), setting, eval(data));
        });
    });
    var cdTreeId;
    //点击事件
    function ztreeOncheck(event,treeId,treeNode) {
        cdTreeId = treeId;
    }
    function confirmCallBack() {
        if (cdTreeId) {
            var frameLayer;
            if (!!window.ActiveXObject || "ActiveXObject" in window) {
                frameLayer =  window.top.frames[0].frames[0].frames[1];
            } else {
                frameLayer = window.top.frames[0].frames[0].frames[0];
            }
            var treeObj = $.fn.zTree.getZTreeObj(cdTreeId);
            var nodes = treeObj.getCheckedNodes(true);
            var jsIds = "";
            var jsMc = "";
            for (var i = 0; i < nodes.length; i++) {
                jsIds += nodes[i].id + ",";
                jsMc += nodes[i].name + ",";
            }
            jsIds = jsIds.substring(0, jsIds.length - 1);
            jsMc = jsMc.substring(0, jsMc.length - 1);
            frameLayer.$("#jsMc").val(jsMc);
            frameLayer.$("#jsIds").val(jsIds);
        }
        closePage();
    }
</script>
</html>