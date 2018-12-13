<!DOCTYPE html>
<html>
<head>
    <title>Web报表演示</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <#include "../common.ftl">
    <style type="text/css">
        html,body {
            margin:0;
            height:100%;
        }
    </style>
    <script language="javascript" type="text/javascript">
        function setReport() {
            var Report = "/downloadReport?report=${(report)!}";
            var Data = "/data?name=${(report)!}";
            CreatePrintViewerEx("100%", "100%", Report, Data, true, "");
        }
        setReport();
    </script>
</head>
<body>
</body>
</html>
