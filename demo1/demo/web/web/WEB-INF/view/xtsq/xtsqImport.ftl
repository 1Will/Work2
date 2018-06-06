<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="X-UA-COMPATIBLE" content="IE=EmulateIE7"/>
    <meta charset="UTF-8">
    <title>导入页面</title>
<#include "../header.ftl">
    <style type="text/css">
        html,
        body {
            height: 100%;
            overflow: hidden;
            background: none;
        }
    </style>
</head>
<body>
<div class="query_box">
    <form id="sqImportForm" method="post" enctype="multipart/form-data">
        <table cellpadding="0" cellspacing="0" border="0" class="query_form">
            <colgroup>
                <col width="53%"/>
                <col width="25%"/>
                <col/>
            </colgroup>
            <tr>
                <td><input type="file" name="file" id="excelFile"></td>
                <td><input class="query_button" type="button" value="导入" onclick="importExcel()"/></td>
                <td><a href="javascript:void(0)" onclick="dowmloadMb() ">模板下载</a></td>
            </tr>
            <tr>
                <td><p id="cwxx" style="width: 100%;height: 24px;line-height: 24px;color: #666;"></p></td>
                <td colspan="2">
                    <p id="ckjg" style="display: none">
                        <a href="javascript:void(0);" onclick="downloadResult()">查看结果</a>
                    </p>
                </td>
            </tr>
        </table>
    </form>
</div>

<script type="text/javascript" language="javascript">

    //下载模板
    function dowmloadMb() {
        window.location.href =  '${base}/xtsq/downloadSqExcel?file=/upload/' + encodeURI(encodeURI('社区模板.xls'));
    }
    //下载结果
    function downloadResult() {
        window.location.href =  '${base}/xtsq/downloadSqExcel?file=/upload/ImportSqResult.xls';
    }

    //导入模板
    function importExcel() {
        $('#sqImportForm').ajaxSubmit({
            beforeSubmit: checkData,
            url: "${base}/xtsq/importSqExcel",
            success: function (msg) {
                if (msg) {
                    if (msg == "导入成功") {
                        top.layer.alert(msg, {icon: 1});
                        //window.top.frames[0].frames[0].location.reload();
                        window.top.frames[0].frames[0].frames[0].query(1);
                        parent.layer.close(parent.layer.getFrameIndex(window.name));
                    } else if (msg == "cw") {
                        $('#cwxx').hide();
                        $('#ckjg').hide();
                        clearFile();
                        top.layer.alert("请导入正确模板信息", {icon: 2});
                    } else {
                        clearFile();
                        $('#cwxx').show();
                        $('#cwxx').text(msg);
                        $('#ckjg').show();
                        window.top.frames[0].frames[0].frames[0].query(1);
                    }
                }
            },
            error: function(){
                top.layer.alert("导入失败", {icon: 2});
            }
        })
    }

    //验证格式
    function checkData() {
        var fileDir = $("#excelFile").val();
        var suffix = fileDir.substr(fileDir.lastIndexOf("."));
        if (fileDir == "") {
            top.layer.alert("选择需要导入的Excel文件！", {icon: 2});
            return false;
        }
        if (suffix != ".xls") {
            top.layer.alert("选择xls格式的文件导入！", {icon: 2});
            return false;
        }
        return true;
    }

    //清空file
    function clearFile() {
        var file = $("#excelFile");
        file.after(file.clone().val(""));
        file.remove();
    }
</script>
</body>
</html>