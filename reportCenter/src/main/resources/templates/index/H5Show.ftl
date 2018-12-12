<!DOCTYPE html>
<html>
<head>
    <title>H5演示</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <#include "../common.ftl">
    <script language="javascript" type="text/javascript">
        function window_onload() {
            //创建报表显示器，参数指定其在网页中的占位标签的ID，关联的报表模板URL，关联的报表数据URL
            var reportViewer = window.rubylong.grhtml5.insertReportViewer("report_holder", "/downloadReport?report=${(report)!}", "/data?name=${(report)!}");
            //启动报表运行，生成报表
            reportViewer.start();
        }
    </script>
</head>
<body onload="window_onload()">
<div id="report_holder"></div>
</body>
</html>
