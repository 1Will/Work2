<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=8"/>
    <title>系统用户首页</title>
<#include "../header.ftl">
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
            <ul id="xtjgTree" class="ztree"></ul>
        </div>
    </div>
</div>
<div class="right_content" id="rightContent">
    <iframe name="xtyhRight" id="xtyhRight" frameborder="0" width="100%" height="100%" scrolling="auto" src="${base}/xtyh/xtyhRight"></iframe>
</div>
<script type="text/javascript" language="javascript">
    //记录树节点点击后的机构id值
    var jgid;
    //初始化页面
    $(function () {
        //初始化尺寸
        $("#rightContent").width($(window).width() - $("#leftContent").outerWidth() - 1);
        $(".tree_box").height($(window).height());
        //加载机构树
        $.post("${base}/tree/getXtjgTree", null, function (data) {
            $.fn.zTree.init($("#xtjgTree"), setting, eval(data));
        });
    });

    //树配置
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
        window.frames['xtyhRight'].changeJgid(jgid);
    }

</script>
</body>
</html>