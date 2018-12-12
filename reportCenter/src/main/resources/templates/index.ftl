<!DOCTYPE html>
<html>
<head>
    <title>报表中心</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <#include "common.ftl">
    <script type="application/javascript" >
        Install_InsertReport();
        var Installed = Install_Detect();
    </script>
</head>
<body>

<ul class="layui-nav" lay-filter="">
    <li class="layui-nav-item  layui-this"><a href="/reportList" target="page_body">报表列表</a></li>
    <li class="layui-nav-item"><a href="/hello" target="page_body">测试</a></li>
    <span class="layui-nav-bar" style="left: 20px; top: 55px; width: 0px; opacity: 0;"></span>
</ul>
<iframe name="page_body" frameborder="0" width="100%" height="800px"  scrolling="auto" src="/reportList">
</iframe>
</body>
<script type="application/javascript">
//注意：导航 依赖 element 模块，否则无法进行功能性操作
    layui.use('element', function(){
        var element = layui.element;
        element.on('nav(demo)', function(elem){
            //console.log(elem)
            layer.msg(elem.text());
        });
    });
</script>
</html>
