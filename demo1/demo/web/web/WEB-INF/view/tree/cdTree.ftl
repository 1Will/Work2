<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="X-UA-COMPATIBLE" content="IE=EmulateIE7"/>
    <meta charset="UTF-8">
    <title>菜单树</title>
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
    <div class="tree_box">
        <div class="zTreeDemoBackground">
            <ul id="treeDemo" class="ztree" style="overflow-y:scroll;height:393px"></ul>
        </div>
    </div>
    <!-- 下面按钮 -->
    <div class="w_button_box">
        <input type="button" id="save" value="确认" class="c_button" onclick="confirm()"/>
    </div>
</div>
</body>
<script type="text/javascript">
    var cdId, cdmc;
    var id = getQueryString('id');
    var ssxt = getQueryString('ssxt');
    var type = getQueryString("type");
    var cdIds = getQueryString("cdIds");
    var setting = {
        view: {
            showLine: false,
            selectedMulti: false
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
            onClick: ztreeOnclick,
            onDblClick: ztreeOnDblclick,
            onCheck: ztreeOncheck
        }
    };
    if (type) {
        setting.check = {
            enable: true,
            chkStyle: "checkbox",
            chkboxType: {"Y": "ps", "N": "ps"},
            autoCheckTrigger: true
        };
        setting.view.notShowClass = true;
    }
    //树配置
    $(function () {
        var url = "${base}/tree/getCdTree?ssxt=" + ssxt + "&type=" + type + "&cdIds=" + cdIds;
        if (id != 'undefined' && id) {
            url += "&id=" + id;
        }
        $.post(url, null, function (data) {
            $.fn.zTree.init($("#treeDemo"), setting, eval(data));
        })
    });

    //双击事件
    function ztreeOnDblclick(event, treeId, treeNode) {
        ztreeOnclick(event, treeId, treeNode);
        confirm();
    }

    //点击事件
    function ztreeOnclick(event, treeId, treeNode) {
        if (treeNode.id == id) {
            top.layer.alert("不可选择菜单本身作为它的上级菜单!", {icon: 2});
        } else {
            cdId = treeNode.id;
            cdmc = treeNode.name;
        }
    }

    function ztreeOncheck(event, treeId, treeNode) {
        var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
        var pNode = treeObj.getNodeByParam("id", treeNode.pId, null);
        if (!treeNode.checked) {
            pNode.checked = false;
        }
    }

    //确认
    function confirm() {
        var frameLayer = window.top.frames[0].frames[0];
        if (type) {
            var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
            var nodes = treeObj.getCheckedNodes(true);
            var cdIds = "";
            var cdNames = "";
            for (var i = 0; i < nodes.length; i++) {
                cdIds += nodes[i].id + ",";
                cdNames += nodes[i].name + ",";
            }
            cdIds = cdIds.substring(0, cdIds.length - 1);
            cdNames = cdNames.substring(0, cdNames.length - 1);
            frameLayer.$("#cdId").val(cdIds);
            frameLayer.$("#cdName").val(cdNames);
        } else {
            frameLayer.$("#sjcdId").val(cdId);
            frameLayer.$("#sjcdmc").val(cdmc);
        }
        closePage();
    }
</script>
</html>