<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=8"/>
    <title>附件首页</title>
<#include "../header.ftl">
    <!-- jquery.FileUpload -->
    <script src="${base}/resources/js/jquery.FileUpload/js/vendor/jquery.ui.widget.js"></script>
    <script src="${base}/resources/js/jquery.FileUpload/js/jquery.iframe-transport.js"></script>
    <script src="${base}/resources/js/jquery.FileUpload/js/jquery.fileupload.js"></script>
    <script src="${base}/resources/js/jquery.FileUpload/js/cors/jquery.xdr-transport.js"></script>
    <style type="text/css">
        html,
        body {
            height: 100%;
            overflow: hidden;
            background: none;
        }

        .bar {
            height: 18px;
            background: green;
        }

        .a-upload {
            padding: 4px 10px;
            width: 100%;
            height: 100%;
            box-sizing: border-box;
            line-height: 30px;
            position: relative;
            cursor: pointer;
            color: #888;
            background: #fafafa;
            border: 1px solid #ddd;
            border-radius: 4px;
            overflow: hidden;
            display: inline-block;
            *display: inline;
            *zoom: 1;
            top: 2px;
        }

        .a-upload input {
            position: absolute;
            font-size: 100px;
            opacity: 0;
            filter: alpha(opacity=0);
            cursor: pointer;
            width: 100%;
            height: 100%;
            left: 0;
            top: 0;
        }

        .a-upload:hover {
            color: #444;
            background: #eee;
            border-color: #ccc;
            text-decoration: none
        }

    </style>
</head>
<body>
<!-- 附件列表 -->
<form id="xtfjForm" method="post">
    <input type="hidden" id="pageNo" name="pageNo" value=""/>
    <input type="hidden" id="pageSize" name="pageSize" value="3"/>
</form>
<div id="xtfjDiv" class="list_box" style="height: 210px"></div>

<!-- 附件上传部分 -->
<div class="fj">
    <div style="float:left;width: 25%;">
        <div  style="height: 30px;">
            <a class="a-upload" id="afile" style="width: 90%;height: 100%;">
                <input type="file" id="fileupload" name="file" multiple>点击选择文件
            </a>
        </div>
        <!-- 显示上传进度 -->
        <div id="progress" style="height: 20px;width: 90%;">
            <div class="bar" style="width: 0%;"></div>
        </div>
        <button class="query_button"  id="startBtn" >上传</button>
    </div>
    <div style="height: 60px;width: 30%;float:left;">
        <!-- 提示栏 -->
        <textarea id="checked" readonly="readonly"
                  style="border-style: solid; border-color: #FFFFFF;height: 100%;width: 100%;resize: none;"
        ></textarea>
    </div>
</div>
<input type="hidden" id="xxfbId" value="">
<script type="text/javascript">
    //初始化
    $(function () {
        var url = location.search.split("=");
        if (url.length > 1) {
            $("#xxfbId").val(url[url.length - 1]);
        }
        query(1);
    });

    $(function () {
        $('#fileupload').fileupload({
            singleFileUploads: false,
            dataType: 'json',
            url: '/xtfj/addXtfj?xxid=' + $('#xxfbId').val(),
            progressall: function (e, data) {
                var progress = parseInt(data.loaded / data.total * 100, 10);
                $('#progress .bar').css('width', progress + '%');
            },
            add: function (e, data) {
                data.context = $('#startBtn').click(function () {
                    data.submit();
                });
            },
            done: function (e, data) {
                $.each(data.result.files, function (index, file) {
                });
                location.reload();
            },
            change: function (e, data) {
                $.each(data.files, function (index, file) {
                    var checkedValue = ("" == $('#checked').val()) ? "" : ($('#checked').val() + "\n");
                    $('#checked').val(checkedValue + file.name + " 已选择");
                });
            }
        });
    });

    function delFj(fjId) {
        //删除
        var confirm = function () {
            MaskLayer("数据删除中,请稍后...");
            $.ajax({
                url: '${base}/xtfj/deleteXtfj',
                type: 'post',
                async: false,
                data: {id: fjId},
                success: function (data) {
                    closeBg();
                    if (data === '1') {
                        top.layer.alert("删除成功", {icon: 1});
                        query(1);
                    } else {
                        top.layer.alert("删除失败", {icon: 2});
                    }
                }
            });
        };
        top.layer.confirm("确认要删除吗", {icon: 3}, confirm)
    }


    //分页
    function query(pageNo) {
        var url = "${base}/xtfj/xtfjList?xxId=" + $("#xxfbId").val();
        MaskLayer("查询数据中，请稍后...");
        $("#pageNo").val(pageNo);
        $("#xtfjForm").ajaxSubmit({
            url: url,
            target: '#xtfjDiv',
            type: 'post',
            success: function () {
                closeBg();
            },
            error: function (XmlHttpRequest, textStatus) {
                closeBg();
                top.layer.alert("查询失败！错误类型:" + textStatus, {icon: 2});
            }
        })
    }

</script>
</body>
</html>