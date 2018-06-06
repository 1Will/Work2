<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="X-UA-COMPATIBLE" content="IE=EmulateIE7"/>
    <meta charset="UTF-8">
    <title>机构树弹窗</title>
<#assign base=request.contextPath/>
    <link rel="stylesheet" type="text/css" href="${base}/resources/css/Main.css"/>
    <link rel="stylesheet" type="text/css" href="${base}/resources/css/zTreeStyle/zTreeStyle.css"/>
    <script type="text/javascript" src="${base}/resources/js/jquery-1.8.3.js"></script>
    <script type="text/javascript" src="${base}/resources/js/jquery.ztree.core-3.4.js"></script>
    <script type="text/javascript" src="${base}/resources/layer/layer.js"></script>
    <script type="text/javascript" src="${base}/resources/js/Validform.js"></script>
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
            <ul id="treeDemo" class="ztree" style="overflow-y:scroll;height:495px"></ul>
        </div>
    </div>
    <!-- 下面按钮 -->
    <div class="w_button_box">
        <input type="button" id="save" value="确认" class="c_button" onclick="confirm()"/>
    </div>
</div>
</body>
<script type="text/javascript">
    var jgid, jgmc;
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
        async:{
            enable:true,
            type:"post",
            url:"${base}/tree/getXtjgTree",
            autoParam:["id"]
        },
        callback: {
            onClick: ztreeOnclick,
            onDblClick: ztreeOnDblclick
        }
    };
    //树配置
    $(function () {
        $.post("${base}/tree/getXtjgTree", null, function (data) {
            $.fn.zTree.init($("#treeDemo"), setting, data);
        })
    });

    //双击事件
    function ztreeOnDblclick(event, treeId, treeNode) {
        ztreeOnclick(event, treeId, treeNode);
        confirm();
    }

    //点击事件
    function ztreeOnclick(event, treeId, treeNode) {
        jgid = treeNode.id;
        jgmc = treeNode.name;
    }

    //确认
    function confirm() {
        var frameLayer;
        var frames = window.top.frames[0].frames[0];
        if (!!window.ActiveXObject || "ActiveXObject" in window) { //ie
            frameLayer =  frames.frames[1];
        } else {
            frameLayer = frames.frames[0];
        }
        if(getQueryString("type")){ //系统机构模块树
            frameLayer.$("#sjjgmc").val(jgmc);
            frameLayer.$("#sjjg_id").val(jgid);
        }else { //系统用户模块选择机构树
            frameLayer.$("#jg_id").val(jgid);
            frameLayer.$("#jgmc").val(jgmc);
        }

        parent.layer.close(parent.layer.getFrameIndex(window.name));
    }
</script>
</html>