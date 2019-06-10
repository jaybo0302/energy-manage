<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="Bookmark" href="/favicon.ico">
<link rel="Shortcut Icon" href="/favicon.ico" />
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/echarts/3.4.0/echarts.common.min.js"></script>
<script src="${pageContext.request.contextPath}/lib/layui/layui.js" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/lib/bootstrap/js/bootstrap.min.js" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/lib/bootstrap/js/bootstrap-datetimepicker.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/layer/2.4/layer.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/lib/layui/css/layui.css"  media="all">
<link rel="stylesheet" href="${pageContext.request.contextPath}/lib/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/lib/bootstrap/css/bootstrap-datetimepicker.min.css">
<title>电表数据</title>
<%
	String contextPath = request.getContextPath();
%>
</head>

<body> 
  <div>
  	<br/>
  	<div class="row">
  		<div class="col-lg-1">
  			<button id="daySelect" class="btn btn-primary radius" style="margin-left:40px">天视图</button>
  		</div>
  		<div class="col-lg-1">
  			<button id="monthSelect" class="btn btn-default radius">月视图</button>
  		</div>
  		<div class="col-lg-2">
	  		<div class="form-group">
		        <div class="input-group date form_datetime col-md-2">
		            <input id="dateStr" class="form-control date-picker" size="16" type="text" readonly style="width:100px">
		        </div>
		    </div>
		 </div>
		 <div class="col-lg-2">
		 	<select class="form-control" id="devices">
		      <option value='0'>全部设备</option>
		    </select>
		 </div>
    </div>
  </div>
  <div class="layui-fluid" style="width:95%;height:600px">
	<div class="layui-row" style="margin-top: 10px;width:100%;height:90%" >
		<div class="layui-col-md3" style="width:33%;height:40%">
			<div id="container" style="width:100%;height:100%"></div>
		</div>
		<div class="layui-col-md3" style="width:33%;height:40%">
			<div id="container1" style="width:100%;height:100%"></div>
		</div>
		<div class="layui-col-md3" style="width:33%;height:40%">
			<div id="container2" style="width:100%;height:100%"></div>
		</div>
		<div class="layui-col-md3" style="width:33%;height:40%">
			<div id="container3" style="width:100%;height:100%"></div>
		</div>
		<div class="layui-col-md3" style="width:33%;height:40%">
			<div id="container4" style="width:100%;height:100%"></div>
		</div>
		<div class="layui-col-md3" style="width:33%;height:40%">
			<div id="container5" style="width:100%;height:100%"></div>
		</div>
		<div class="layui-col-md3" style="width:33%;height:40%">
			<div id="container6" style="width:100%;height:100%"></div>
		</div>
		<div class="layui-col-md3" style="width:33%;height:40%">
			<div id="container7" style="width:100%;height:100%"></div>
		</div>
		<div class="layui-col-md3" style="width:33%;height:40%">
			<div id="container8" style="width:100%;height:100%"></div>
		</div>
	</div>
  </div>
</body>
<script type="text/javascript">
	$.ajax({
		url:"${pageContext.request.contextPath}/electricmeter/getDevicesByCompany.do",
		async:false,
		method:"GET",
		cache:false,
		success:function(result) {
			var data = result.data;
			$("#devices").empty();
			var str ="";
			for (var i = 0;i<data.length;i++) {
				str+=("<option value='"+data[i].deviceNo+"'>"+data[i].deviceName+"</option>");
			}
			$("#devices").append(str);
		},
	});
	$("#devices").change(function() {
		getData();
	});
	var dateType = 0;
	$("#daySelect").click(function(){
		$("#daySelect").attr("class", "btn btn-primary radius");
		$("#monthSelect").attr("class", "btn btn-default radius");
		$(".date-picker").remove();
        $(".input-group-addon").remove();
        $(".datetimepicker").remove();
		$('.form_datetime').append("<input id='dateStr' class='form-control date-picker' size='16' type='text' readonly style='width:100px'>");
		$('.date-picker').datetimepicker({
		    weekStart: 1,
			autoclose: 1,
			startView: "month",
			forceParse: 0,
		    minView: "month",
		    format: "yyyy-mm-dd",
		}).on('changeDate', function(){
			getData();
		});
		$('.date-picker').val(formatDateTime(new Date()).substr(0, 10));
		dateType = 0;
		getData();
	});
	
	$("#monthSelect").click(function(){
		$("#monthSelect").attr("class", "btn btn-primary radius");
		$("#daySelect").attr("class", "btn btn-default radius");
		$(".date-picker").remove();
        $(".input-group-addon").remove();
        $(".datetimepicker").remove();
		$('.form_datetime').append("<input id='dateStr' class='form-control date-picker' size='16' type='text' readonly style='width:100px'>");
		$('.date-picker').datetimepicker({
		    weekStart: 1,
			autoclose: 1,
			startView: "year",
			forceParse: 0,
		    minView: "year",
		    format: "yyyy-mm",
		}).on('changeDate', function(){
			getData();
		});
		$('.date-picker').val(formatDateTime(new Date()).substr(0, 7));
		dateType = 1;
		getData();
	});

	function formatDateTime(date) {
	    var y = date.getFullYear();    
	    var m = date.getMonth() + 1;    
	    m = m < 10 ? ('0' + m) : m;    
	    var d = date.getDate();    
	    d = d < 10 ? ('0' + d) : d;    
	    var h = date.getHours();  
	    h = h < 10 ? ('0' + h) : h;  
	    var minute = date.getMinutes();
	    var second = date.getSeconds();  
	    minute = minute < 10 ? ('0' + minute) : minute;    
	    second = second < 10 ? ('0' + second) : second;   
	    return y + '-' + m + '-' + d+' '+h+':'+minute+':'+second;    
	}; 
	$('#daySelect').trigger("click");
	var h = document.documentElement.scrollHeight || document.body.scrollHeight;
	$(".layui-fluid").height(h*95/100);
	var dom = document.getElementById("container");
	var myChart = echarts.init(dom);
	var dom1 = document.getElementById("container1");
	var myChart1 = echarts.init(dom1);
	var dom2 = document.getElementById("container2");
	var myChart2 = echarts.init(dom2);
	var dom3 = document.getElementById("container3");
	var myChart3 = echarts.init(dom3);
	var dom4 = document.getElementById("container4");
	var myChart4 = echarts.init(dom4);
	var dom5 = document.getElementById("container5");
	var myChart5 = echarts.init(dom5);
	var dom6 = document.getElementById("container6");
	var myChart6 = echarts.init(dom6);
	var dom7 = document.getElementById("container7");
	var myChart7 = echarts.init(dom7);
	var dom8 = document.getElementById("container8");
	var myChart8 = echarts.init(dom8);
	option = {
		title : {
			text : '有功功率'
		},
		tooltip : {
			trigger : 'axis'
		},
		grid : {
			left : '3%',
			right : '4%',
			bottom : '3%',
			containLabel : true
		},
		toolbox : {
			feature : {
				saveAsImage : {}
			}
		},
		xAxis : {
			type : 'category',
			boundaryGap : false,
			data : [ '周一', '周二', '周三', '周四', '周五', '周六', '周日' ]
		},
		yAxis : {
			type : 'value'
		},
		series : {
			name : '有功功率',
			type : 'line',
			stack : '总量',
			data : [ 120, 132, 101, 134, 90, 230, 210 ]
		}
	};
	option1 = {
			title : {
				text : '无功功率'
			},
			tooltip : {
				trigger : 'axis'
			},
			grid : {
				left : '3%',
				right : '4%',
				bottom : '3%',
				containLabel : true
			},
			toolbox : {
				feature : {
					saveAsImage : {}
				}
			},
			xAxis : {
				type : 'category',
				boundaryGap : false,
				data : [ '周一', '周二', '周三', '周四', '周五', '周六', '周日' ]
			},
			yAxis : {
				type : 'value'
			},
			series : {
				name : '无功功率',
				type : 'line',
				stack : '总量',
				data : [ 120, 132, 101, 134, 90, 230, 210 ]
			}
	};
	option2 = {
			title : {
				text : 'A相电压'
			},
			tooltip : {
				trigger : 'axis'
			},
			grid : {
				left : '3%',
				right : '4%',
				bottom : '3%',
				containLabel : true
			},
			toolbox : {
				feature : {
					saveAsImage : {}
				}
			},
			xAxis : {
				type : 'category',
				boundaryGap : false,
				data : [ '周一', '周二', '周三', '周四', '周五', '周六', '周日' ]
			},
			yAxis : {
				type : 'value'
			},
			series : {
				name : 'A相电压',
				type : 'line',
				stack : '总量',
				data : [ 120, 132, 101, 134, 90, 230, 210 ]
			}
	};
	option3 = {
			title : {
				text : 'B相电压'
			},
			tooltip : {
				trigger : 'axis'
			},
			grid : {
				left : '3%',
				right : '4%',
				bottom : '3%',
				containLabel : true
			},
			toolbox : {
				feature : {
					saveAsImage : {}
				}
			},
			xAxis : {
				type : 'category',
				boundaryGap : false,
				data : [ '周一', '周二', '周三', '周四', '周五', '周六', '周日' ]
			},
			yAxis : {
				type : 'value'
			},
			series : {
				name : 'B相电压',
				type : 'line',
				stack : '总量',
				data : [ 120, 132, 101, 134, 90, 230, 210 ]
			}
	};
	option4 = {
			title : {
				text : 'C相电压'
			},
			tooltip : {
				trigger : 'axis'
			},
			grid : {
				left : '3%',
				right : '4%',
				bottom : '3%',
				containLabel : true
			},
			toolbox : {
				feature : {
					saveAsImage : {}
				}
			},
			xAxis : {
				type : 'category',
				boundaryGap : false,
				data : [ '周一', '周二', '周三', '周四', '周五', '周六', '周日' ]
			},
			yAxis : {
				type : 'value'
			},
			series : {
				name : 'C相电压',
				type : 'line',
				stack : '总量',
				data : [ 120, 132, 101, 134, 90, 230, 210 ]
			}
	};
	option5 = {
			title : {
				text : 'A相电流'
			},
			tooltip : {
				trigger : 'axis'
			},
			grid : {
				left : '3%',
				right : '4%',
				bottom : '3%',
				containLabel : true
			},
			toolbox : {
				feature : {
					saveAsImage : {}
				}
			},
			xAxis : {
				type : 'category',
				boundaryGap : false,
				data : [ '周一', '周二', '周三', '周四', '周五', '周六', '周日' ]
			},
			yAxis : {
				type : 'value'
			},
			series : {
				name : 'A相电流',
				type : 'line',
				stack : '总量',
				data : [ 120, 132, 101, 134, 90, 230, 210 ]
			}
	};
	option6 = {
			title : {
				text : 'B相电流'
			},
			tooltip : {
				trigger : 'axis'
			},
			grid : {
				left : '3%',
				right : '4%',
				bottom : '3%',
				containLabel : true
			},
			toolbox : {
				feature : {
					saveAsImage : {}
				}
			},
			xAxis : {
				type : 'category',
				boundaryGap : false,
				data : [ '周一', '周二', '周三', '周四', '周五', '周六', '周日' ]
			},
			yAxis : {
				type : 'value'
			},
			series : {
				name : 'B相电流',
				type : 'line',
				stack : '总量',
				data : [ 120, 132, 101, 134, 90, 230, 210 ]
			}
	};
	option7 = {
			title : {
				text : 'C相电流'
			},
			tooltip : {
				trigger : 'axis'
			},
			grid : {
				left : '3%',
				right : '4%',
				bottom : '3%',
				containLabel : true
			},
			toolbox : {
				feature : {
					saveAsImage : {}
				}
			},
			xAxis : {
				type : 'category',
				boundaryGap : false,
				data : [ '周一', '周二', '周三', '周四', '周五', '周六', '周日' ]
			},
			yAxis : {
				type : 'value'
			},
			series : {
				name : 'C相电流',
				type : 'line',
				stack : '总量',
				data : [ 120, 132, 101, 134, 90, 230, 210 ]
			}
	};
	function getData(){
		$.ajax({
			url:"${pageContext.request.contextPath}/meterinfo/queryStatisticMeterInfo.do?dateType="+dateType+"&date="+$('.date-picker').val()+"&deviceNo=" + $("#devices").val(),
			method:"GET",
			success:function(result){
				if (result.success) {
					var data = result.data;
					option.xAxis.data = [];
					option.series.data = [];
					option1.xAxis.data = [];
					option1.series.data = [];
					option2.xAxis.data = [];
					option2.series.data = [];
					option3.xAxis.data = [];
					option3.series.data = [];
					option4.xAxis.data = [];
					option4.series.data = [];
					option5.xAxis.data = [];
					option5.series.data = [];
					option6.xAxis.data = [];
					option6.series.data = [];
					option7.xAxis.data = [];
					option7.series.data = [];
					
					for (var i=0;i<data.length;i++) {
						option.xAxis.data[i]= data[i]["t"];
						option.series.data[i]= data[i]["activePowerAve"];
						option1.xAxis.data[i]= data[i]["t"];
						option1.series.data[i]= data[i]["reactivePowerAve"];
						option2.xAxis.data[i]= data[i]["t"];
						option2.series.data[i]= data[i]["aVAve"];
						option3.xAxis.data[i]= data[i]["t"];
						option3.series.data[i]= data[i]["bVAve"];
						option4.xAxis.data[i]= data[i]["t"];
						option4.series.data[i]= data[i]["cVAve"];
						option5.xAxis.data[i]= data[i]["t"];
						option5.series.data[i]= data[i]["aAAve"];
						option6.xAxis.data[i]= data[i]["t"];
						option6.series.data[i]= data[i]["bAAve"];
						option7.xAxis.data[i]= data[i]["t"];
						option7.series.data[i]= data[i]["cAAve"];
					}
					if (option && typeof option === "object") {
						myChart.setOption(option, true);
						myChart1.setOption(option1, true);
						myChart2.setOption(option2, true);
						myChart3.setOption(option3, true);
						myChart4.setOption(option4, true);
						myChart5.setOption(option5, true);
						myChart6.setOption(option6, true);
						myChart7.setOption(option7, true);
					}
				} else {
					layer.msg(result.message,{icon:5,time:1000});
				}
			}
		});	
	}
</script>
</html>