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

    $.extend({
        StandardPost: function (url, args) {
            var form = $("<form method='post'></form>"),
                input;
            form.attr({"action": url});
            $.each(args, function (key, value) {
                input = $("<input type='hidden'>");
                input.attr({"name": key});
                input.val(value);
                form.append(input);
            });
            $(document.body).append(form);
            form.submit();
        }
    });

    window.onload = function () {
        var json ="{\"Master\":[{\"HOS_NAME\":\"湖南省华容县人民医院\",\"EMP_NAME\":\"儿童\",\"EMP_CODE\":\"02079449\",\"START_TIME_STR\":\"2016-07-12 08:00:00\",\"END_TIME_STR\":\"2016-07-12 18:00:00\",\"START_NO\":\"10020030010\",\"END_NO\":\"10020030020\",\"FORM_NUM\":\"30\",\"BLANK_FP\":\"1\",\"SSWYCE\":\"0\"}],\"Pro\":[{\"TYPE_NAME\":\"西药费\",\"ZJE\":\"100\"},{\"TYPE_NAME\":\"中药费\",\"ZJE\":\"200\"},{\"TYPE_NAME\":\"检查费\",\"ZJE\":\"30\"},{\"TYPE_NAME\":\"手术费\",\"ZJE\":\"3000\"},{\"TYPE_NAME\":\"卫生材料费\",\"ZJE\":\"0\"}],\"Pro2\":[{\"TYPE_NAME\":\"现金支付\",\"ZJE\":\"200\"},{\"TYPE_NAME\":\"医保支付\",\"ZJE\":\"10000\"}]}";
        $.StandardPost('/report', {grf: 'DailyCharge', json: json});
    }
</script>
</html>
