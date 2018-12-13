<!DOCTYPE html>
<html>
<head>
    <title>报表中心</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <#include "../common.ftl">
</head>
<body>
</body>
<script language="javascript" type="text/javascript">
    window.onload = function report() {
        Report.LoadFromURL('/downloadReport?report=${(report.grf)!}');
        Report.LoadDataFromXML(JSON.stringify(${(report.json)!}));
        Report.PrintPreview(true);
    }
</script>
</html>
