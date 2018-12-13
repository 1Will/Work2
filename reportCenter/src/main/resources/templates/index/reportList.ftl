<!DOCTYPE html>
<html>
<head>
    <title>模板列表</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <#include "../common.ftl">
</head>
<body>
<div style="margin-left: 10px;margin-top: 10px">
<button class="layui-btn upload" lay-data="{url: '/uploadReport', accept: 'file',exts:'grf'}">上传模板</button>
</div>

<table class="layui-table" lay-even lay-skin="nob">
    <colgroup>
        <col width="5%"/>
        <col width="15%"/>
        <col width="15%"/>
        <col width="15%"/>
        <col width="10%"/>
        <col/>
    </colgroup>
    <thead>
    <tr>
        <th>序号</th>
        <th>名称</th>
        <th>演示</th>
        <th>插件演示</th>
        <th>H5演示</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <#if names??&&names?size gt 0>
        <#assign num=1/>
        <#list names as o>
            <tr>
                <td>${num}</td><#assign num++/>
                <td><a href="/downloadReport?report=${(o)!}"><font style='color:dodgerblue'>${(o)!}</font></a></td>
                <td><a class="layui-btn layui-btn-sm layui-btn-radius layui-btn-primary" href="javascript:void(0)" onclick="webReport('${(o)!}')">show</a></td>
                <td><a class="layui-btn layui-btn-sm layui-btn-radius layui-btn-primary" href="javascript:void(0)" onclick="showReport('${(o)!}')">show</a></td>
                <td><a class="layui-btn layui-btn-sm layui-btn-radius layui-btn-primary" href="javascript:void(0)" onclick="showH5Report('${(o)!}')">show</a></td>
                <td>
                    <div class="layui-btn-group">
                        <a class="layui-btn layui-btn-sm" href="javascript:void(0)" onclick="edit('${(o)!}')">编辑</a>
                        <a class="layui-btn layui-btn-sm" href="javascript:void(0)" onclick="del('${(o)!}')">删除</a>
                    </div>
                </td>
            </tr>
        </#list>
    <#else>
        <tr class="trhover">
            <td class="none" colspan="6">无记录</td>
        </tr>
    </#if>
    </tbody>
</table>

</body>
<script type="application/javascript">

    function showReport(name){
        Report.LoadFromURL('/downloadReport?report='+name);
        Report.LoadDataFromURL('/data?name='+name);
        //Report.LoadDataFromXML(JSON.stringify(all_json_p));
        Report.PrintPreview(true);
    }
    function showH5Report(name){
        window.location.href="/H5Show?report="+name;
    }

    function webReport(name){
        window.location.href="/webReport?report="+name;
    }

    function edit(name) {
        var url = '/editReport?report=' + name;
        window.open(url);
    }
    function del(name) {
        var url = '/deleteReport?report=' + name;
        $.ajax({ url: url, async:false, success: function(){
                window.location.reload();
            }});
    }

    layui.use('upload', function(){
        var upload = layui.upload;
        //执行实例
        var uploadInst = upload.render({
            elem: '.upload' //绑定元素
            ,done: function(res){
                if(res.code>0){
                    layer.msg('上传成功！', {
                        icon: 1,
                        time: 1500 //2秒关闭（如果不配置，默认是3秒）
                    }, function(){
                        location.reload();
                    });
                }
                //上传完毕回调
            },error: function(){
                //请求异常回调
                layer.msg('上传错误！', {
                    icon: 2,
                    time: 1500 //2秒关闭（如果不配置，默认是3秒）
                });
            }
        });
    });
</script>

</html>
