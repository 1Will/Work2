<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=8"/>
    <title>字典管理首页</title>
<#include "../header.ftl">
    <link rel="stylesheet" type="text/css" href="${base}/resources/css/zTreeStyle/zTreeStyle.css"/>
    <script type="text/javascript" src="${base}/resources/js/jquery.ztree.core-3.4.js"></script>
    <script type="text/javascript" src="${base}/resources/js/jquery.ztree.excheck-3.4.js"></script>
    <style type="text/css">
        html, body {
            height: 100%;
            overflow: hidden;
        }
    </style>
</head>

<body>
<div class="left_side" id="leftContent">
    <div class="tree_box">
        <div>
            <ul id="xtzdTree" class="ztree"></ul>
        </div>
    </div>
</div>
<div class="right_content" id="rightContent">
    <iframe name="xtzdRight" id="xtzdRight" frameborder="0" width="100%" height="100%" scrolling="auto" src="${base}/xtzd/xtzdRight"></iframe>
</div>
<script type="text/javascript" language="javascript">
    //记录树节点点击后的id值
    var trees;
    //记录状态位：是否进行树节点点击操作
    var isTree = 0;

    $(function() {
        //初始化内容区域高度
        $("#rightContent").width($(window).width() - $(".left_side").outerWidth());
        $(".tree_box").height($(window).height());
        loadZdTree();
    });

    //页面大小改变时，触发jquery的resize方法，自适应拖拽
    $(window).resize(function() {
        $("#rightContent").width($(window).width() - $(".left_side").outerWidth());
        $(".tree_box").height($(window).height());
    });

    //加载字典树
    function loadZdTree() {
        $.post("${base}/xtzd/getXtzdTree", null, function (data) {
            $.fn.zTree.init($("#xtzdTree"), setting, eval(data));
        });
    }
    //树配置
    var setting = {
        view: {
            showIcon: showIconForTree
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
            onClick: ztreeOnclick
        }
    };

    function showIconForTree(treeId, treeNode) {
        return !treeNode.isParent;
    }

    //树节点点击事件
    function ztreeOnclick(event, treeId, treeNode) {
        trees = treeNode.id;
        window.frames['xtzdRight'].rightSjzd(trees);
        isTree = 1;
    }

    //刷新字典树
    function refreshTree() {
        //加载字典树
        $.post("${base}/xtzd/getXtzdTree", null, function (data) {
            $.fn.zTree.init($("#xtzdTree"), setting, eval(data));
        });
    }

    //字典项新增
    function xtzdAddSrc() {
        //如果点击了左边的树，则新增时选中改默认的上级部门
        if (isTree === 1) {
            document.getElementById("xtzdRight").src = "${base}/xtzd/xtzdEdit?sjzd="+trees;
        } else {
            document.getElementById("xtzdRight").src = "${base}/xtzd/xtzdEdit";
        }
    }
</script>
</body>
</html>