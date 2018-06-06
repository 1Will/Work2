<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=8"/>
    <title>系统机构首页</title>
<#include "../header.ftl">
    <style type="text/css">
        html, body {
            height: 100%;
            overflow: hidden;
        }
    </style>
</head>
<body>
<div class="left_side" id="leftContent" >
    <div class="tree_box">
        <div>
            <ul id="xtjgTree" class="ztree"></ul>
        </div>
    </div>
</div>
<div class="right_content" id="rightContent">
    <iframe name="xtjgRight" id="xtjgRight" frameborder="0" width="100%" height="100%" scrolling="auto"
            src="${base}/xtjg/xtjgRight"></iframe>
</div>
<script type="text/javascript" language="javascript">
    //记录树节点点击后的机构id值
    var jgid;
    $(function() {
        //初始化内容区域高度
        $("#rightContent").width($(window).width() - $(".left_side").outerWidth() - 1);
        $(".tree_box").height($(window).height());
        getTree();
    });
    //页面大小改变时，触发jquery的resize方法，自适应拖拽
    $(window).resize(function() {
        $("#rightContent").width($(window).width() - $(".left_side").outerWidth());
        $(".tree_box").height($(window).height());
    });

    //树配置
    var nodes;
    var setting = {
        view: {
            showLine: false
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
            onClick: ztreeOnclick
        }
    };

    //树节点点击事件
    function ztreeOnclick(event, treeId, treeNode) {
        jgid = treeNode.id;
        window.frames['xtjgRight'].changeJgid(jgid);
    }

    function getTree() {
        $.post("${base}/tree/getXtjgTree", null, function (data) {
            $.fn.zTree.init($("#xtjgTree"), setting, eval(data));
        });
    }
</script>
</body>
</html>