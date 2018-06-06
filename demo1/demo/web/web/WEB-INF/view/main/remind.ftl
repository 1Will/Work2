<!DOCTYPE html>
<html>

	<head>
		<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<title></title>
	<#assign base=request.contextPath/>
        <link rel="stylesheet" type="text/css" href="${base}/resources/css/Global.css" />
		<link rel="stylesheet" type="text/css" href="${base}/resources/css/Main.css" />
		<link rel="stylesheet" type="text/css" href="${base}/resources/css/iconfont.css" />
		<script src="${base}/resources/js/jquery-1.8.3.js" type="text/javascript"></script>
		<script src="${base}/resources/js/echarts.min.js" type="text/javascript"></script>

	</head>

	<body>
		<div class="homepage">

		</div>

		<script type="text/javascript" language="javascript">
			$(function() {
				//初始化内容区域高度
				//									$(".homepage").height($(window).height() - 40);
			})
			//页面大小改变时，触发jquery的resize方法，自适应拖拽
			$(window).resize(function() {
				//								$(".homepage").height($(window).height() - 40);
			})
		</script>
		<script type="text/javascript">
			// 基于准备好的dom，初始化echarts实例
			var myChart = echarts.init(document.getElementById('main'));

			// 指定图表的配置项和数据
			//app.title = '折柱混合';

			option = {

				title: {
					text: '十大高耗水行业用水量八减两增 ', //标题  
					//  backgroundColor: '#ff0000',            //背景  
					subtext: '同比百分比(%)', //子标题  

					textStyle: {
						fontWeight: 'normal', //标题颜色  
						color: '#ffffff'
					},

					x: "center"
				},
				tooltip: {
					trigger: 'axis',
					axisPointer: { // 坐标轴指示器，坐标轴触发有效
						type: 'shadow' // 默认为直线，可选为：'line' | 'shadow'
					},
					formatter: function(params) {
						var tar = params[1];
						return tar.name + '<br/>' + tar.seriesName + ' : ' + tar.value;
					}
				},
				grid: {
					left: '3%',
					right: '4%',
					bottom: '3%',
					containLabel: true
				},
				xAxis: {
					type: 'category',
					splitLine: {
						show: false
					},
					axisLine: {
						lineStyle: {
							color: '#25c36c',
							width: 2
						}
					},
					data: ['总费用', '房租', '水电费', '交通费', '伙食费', '日用品数']
				},
				yAxis: {
					type: 'value',
					axisLine: {
						lineStyle: {
							color: '#25c36c',
							width: 2
						}
					}
				},
				series: [{
						name: '辅助',
						type: 'bar',
						stack: '总量',
						itemStyle: {
							normal: {
								barBorderColor: 'rgba(0,0,0,0)',
								color: 'rgba(0,0,0,0)'
							},
							emphasis: {
								barBorderColor: 'rgba(0,0,0,0)',
								color: 'rgba(0,0,0,0)'
							}
						},
						data: [0, 1700, 1400, 1200, 300, 0]
					},
					{
						name: '生活费',
						type: 'bar',
						stack: '总量',
						label: {
							normal: {
								show: true,
								position: 'inside'
							}
						},
						data: [2900, 1200, 300, 200, 900, 300]
					}
				]
			};

			// 使用刚指定的配置项和数据显示图表。
			myChart.setOption(option);
		</script>
		<script type="text/javascript">
			// 基于准备好的dom，初始化echarts实例
			var myChart = echarts.init(document.getElementById('map'));

			// 指定图表的配置项和数据
			//app.title = '折柱混合';

			option = {
				tooltip: {
					trigger: 'item',
					formatter: "{a} <br/>{b}: {c} ({d}%)"
				},
				legend: {
					orient: 'vertical',
					x: 'left',
					data: ['直接访问', '邮件营销', '联盟广告', '视频广告', '搜索引擎'],
					data: [{
							name: '直接访问',
							textStyle: {
								color: "#25c36c"
							}
						},
						{
							name: '邮件营销',
							textStyle: {
								color: "#25c36c"
							}
						},
						{
							name: '联盟广告',
							textStyle: {
								color: "#25c36c"
							}
						},
						{
							name: '视频广告',
							textStyle: {
								color: "#25c36c"
							}
						},
						{
							name: '搜索引擎',
							textStyle: {
								color: "#25c36c"
							}
						}
					],
				},
				series: [{
					name: '访问来源',
					type: 'pie',
					radius: ['60%', '90%'],
					avoidLabelOverlap: false,
					label: {
						normal: {
							show: false,
							position: 'center'
						},
						emphasis: {
							show: true,
							textStyle: {
								fontSize: '30',
								fontWeight: 'bold'
							}
						}
					},
					labelLine: {
						normal: {
							show: false
						}
					},
					data: [{
							value: 335,
							name: '直接访问'
						},
						{
							value: 310,
							name: '邮件营销'
						},
						{
							value: 234,
							name: '联盟广告'
						},
						{
							value: 135,
							name: '视频广告'
						},
						{
							value: 1548,
							name: '搜索引擎'
						}
					]
				}]
			};
			// 使用刚指定的配置项和数据显示图表。
			myChart.setOption(option);
		</script>
	</body>

</html>