<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=8"/>
    <title>社区统计首页</title>
<#include "../header.ftl">
    <script type="text/javascript" src="${base}/resources/js/tableExport.min.js"></script>
    <script src="http://cdn.hcharts.cn/highcharts/highcharts.js"></script>
    <style type="text/css">
        html, body {
            height: 100%;
            overflow: hidden;
        }
    </style>
</head>
<body>
<div class="operation_box">
    <ul class="action_button">
        <li>
            <a href="javascript:void(0)" onclick="back()" class="tb"><i class="iconfont">&#xe642;</i>返回上级</a>
        </li>
        <li>
            <a href="javascript:void(0)" onclick="changeDisplay()"><i class="iconfont">&#xe641;</i>切换显示</a>
        </li>
        <li>
            <a href="javascript:void(0)" onclick="exportExcel()"><i class="iconfont">&#xe641;</i>导出所有</a>
        </li>
        <li>
            <a href="javascript:void(0)" onclick="exportExcelByJs()"><i class="iconfont">&#xe641;</i>导出当前</a>
        </li>
    </ul>
</div>

<div class="query_box">
    <form id="searchForm" method="post">
        <input type="hidden" id="pageNo" name="pageNo" value=""/>
        <input type="hidden" id="pageSize" name="pageSize" value="10"/>
        <input type="hidden" id="jgId" name="jgId" value=""/>
    </form>
</div>

<div>
    <div id="xtsqtjList" class="list_box" style="display:block">
    </div>
    <div id="container" style="min-width:100%;height:100%;display: none"></div>
</div>


<script type="text/javascript" language="javascript">
    //页面初始化
    $(function () {
        //初始化内容区域高度
        $(".list_box").height($(window).height() - $(".operation_box").outerHeight() - $(".query_box").outerHeight() - 20);
        //页面大小改变时，触发jquery的resize方法，自适应拖拽
        $(window).resize(function () {
            $(".list_box").height($(window).height() - $(".operation_box").outerHeight() - $(".query_box").outerHeight() - 20);
        });
        var url = location.search.split("=");
        if (url.length > 1) {
            $("#jgId").val(url[url.length - 1]);
        }
        query(1);
    });

    //查询列表
    function query(pageNo, id) {
        MaskLayer("查询数据中,请稍候...");
        $("#pageNo").val(pageNo);
        if (typeof(id) !== "undefined") {
            $("#jgId").val(id);
        }
        if ($("#jgId").val() === '') {
            $("#jgId").val(0);
        }

        $("#searchForm").ajaxSubmit({
            url: "${base}/xtsqtj/xtsqtjList",
            target: '#xtsqtjList',
            type: "post",
            success: function (data) {
                closeBg();
                initCharts();//刷新完成后生成柱状图
            },
            error: function (XmlHttpRequest, textStatus) {
                top.layer.alert("查询失败！错误类型" + textStatus);
                closeBg();
            }
        });
    }

    function changeDisplay() {
        $("#xtsqtjList").toggle("fast");
        $("#container").toggle("fast");
    }
    //返回
    function back() {
        var jgId = $("#jgId").val();
        var id = parseInt(jgId / 100);
        query(1, id);
    }

    //导出excel
    function exportExcel() {
        window.location.href = "${base}/xtsqtj/exportExcel?jgId=" + $("#jgId").val();
    }


    function exportExcelByJs() {
        if ($("#xtsqtjList").is(":hidden")) {
            top.layer.alert("页面没有表格！");
        } else {
            $('#tbColor').tableExport({
                type: 'excel',
                fileName: '社区统计表',//文件名
                ignoreColumn: [3]//忽略的列，从0开始算
            });
        }
    }

    //Highcharts图
    var oChart = null;
    function initCharts() {
        oChart = new Highcharts.Chart({
            credits: {
                enabled: false  //去掉版权信息，就是右下角显示的highchars的网站链接
            },
            chart: {
                type: 'column',
                renderTo: 'container'
            },
            title: {
                text: null
            },
            xAxis: {
                type: 'category',
                labels: {
                    rotation: -30  // 设置轴标签旋转角度
                }
            },
            yAxis: {
                min: 0,
                minRange: 1,
                title: {
                    text: '数量 (个)'
                }
            },

            plotOptions: {
                series: {
                    cursor: 'pointer',
                    point: {
                        events: {
                            click: function () {
                                if(typeof(this.options.key) !== "undefined"){
                                    query(1,this.options.key);
                                }
                            }
                        }
                    }
                }
            },

            legend: {
                enabled: false
            },
            tooltip: {
                pointFormat: '社区数量: <b>{point.y:.0f} 个</b>'
            },
            series: create()
        });
    }
    function create() {
        var series = {};
        series.name = '数量';
        var str = $("#data").val();
        if (typeof(str) === "undefined") {
            series.data = [];
        } else {
            str = str.replace(/'/g, "\"");
            var array = JSON.parse(str);
            series.data = array;
        }
        series.dataLabels = {
            enabled: true,
            rotation: -90,
            color: '#FFFFFF',
            align: 'right',
            format: '{point.y:.0f}',
            y: 10
        };
        var data = [];
        data[0] = series;
        return data;
    }
</script>
</body>
</html>