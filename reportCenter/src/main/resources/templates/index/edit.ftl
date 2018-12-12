<!DOCTYPE html>
<html>
<head>
    <title>报表中心</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <#include "../common.ftl">
</head>
<body>
</body>
<script type="application/javascript">
    window.onload = function DailyCharge() {
        var Report = "/downloadReport?report=${(report)!}";
        CreateDesignerEx("100%", "100%", Report, "saveReport?report=${(report)!}", "/data?name=${(report)!}",
                "<param name=OnSaveReport value=OnSaveReport>");
    }
</script>
</html>
