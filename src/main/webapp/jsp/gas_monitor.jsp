<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <title>气体检测系统</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/lib/layui/css/layui.css"  media="all">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/en.css"  media="all">
    <link href="${pageContext.request.contextPath}/css/pagination.css" rel="stylesheet" type="text/css" />
    <!-- 注意：如果你直接复制所有代码到本地，上述css路径需要改成你本地的 -->
    <script src="${pageContext.request.contextPath}/lib/layui/layui.js" charset="utf-8"></script>
	<script src="${pageContext.request.contextPath}/lib/jquery/1.9.1/jquery.min.js" charset="utf-8"></script>
	<script src="${pageContext.request.contextPath}/js/jquery.pagination.js" charset="utf-8"></script>
</head>
<body>
<!-- 让IE8/9支持媒体查询，从而兼容栅格 -->
<!-- [if lt IE 9]>[endif]-->
<script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
<script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>

<div class="layui-fluid">
	</br>
	<div class="layui-form" style="width:100%">
		<div class="layui-form-item">
		  <div class="layui-inline">
			  <button id="stopUserPosition" class="layui-btn layui-btn-normal layui-btn-radius">屏蔽人员定位</button>
		  </div>
		  <div class="layui-inline">
		    <label class="layui-form-label">轮询时间：</label>
		    <div class="layui-input-block">
		      <input type="radio" name="r" value="10" title="10s" lay-filter="re" checked="" />
		      <input type="radio" name="r" value="20" title="20s" lay-filter="re"/>
		      <input type="radio" name="r" value="30" title="30s" lay-filter="re"/>
<!-- 		      <input type="radio" name="r" value="40" title="40s" lay-filter="re"/> -->
<!-- 		      <input type="radio" name="r" value="50" title="50s" lay-filter="re"/> -->
<!-- 		      <input type="radio" name="r" value="60" title="60s" lay-filter="re"/> -->
		    </div>
		  </div>
		  <div class="layui-inline">
			  <button id="startAutoSearch" class="layui-btn layui-btn-disabled layui-btn-radius">开始轮询</button>
			  <button id="stopAutoSearch" class="layui-btn layui-btn-normal layui-btn-radius">停止轮询</button>
		  </div>
		  <div class="layui-inline">
			  <label style="color:#ff0000;">报警设备：</label>
			  <label id="warnIds" style="color:#ff0000;">无</label>
		  </div>
		</div>
    </div>
    <div class="layui-row" style="margin-top: 10px">
        <div class="layui-col-md2">
			<span style="font-size:20px;font-weight:bold">—设备1—</span></br></br>
			<span style="font-size:20px;font-weight:bold">氧气浓度</span></br>
			<div style="height:40px;width:200px;position:relative">
				<div style="position:absolute;top:1px;left:1px;background-color:#ff0000;height:38px;width:150px"></div>
				<img src="${pageContext.request.contextPath}/images/process.png" style="width:200px;height:40px;position:absolute;"/>
				<span style="position:absolute;left:60px;top:10px;font-size:18px;font-weight:1">75%</span>
			</div>
			<span style="font-size:20px;font-weight:bold">可燃气浓度</span></br>
			<div style="height:40px;width:200px;position:relative">
				<div style="position:absolute;top:1px;left:1px;background-color:#ff0000;height:38px;width:150px"></div>
				<img src="${pageContext.request.contextPath}/images/process.png" style="width:200px;height:40px;position:absolute;"/>
				<span style="position:absolute;left:60px;top:10px;font-size:18px;font-weight:1">75%</span>
			</div>
			<span style="font-size:20px;font-weight:bold">电池电量&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;人员定位</span></br>
			<div style="height:150px;width:200px;position:relative">
				<div style="background-image:url('${pageContext.request.contextPath}/images/battery.png');width:100px;height:50px;background-size: cover; -moz-background-size: cover; position:absolute;z-index:1"></div>
				<div style="background-color:#ff0000;width:50px;height:45px;position:absolute;left:4px;top:2px"> </div>
				<span style="position:absolute;left:20px;top:18px">3456mAh</span>
				<img src="${pageContext.request.contextPath}/images/people.png" style="position:absolute;left:120px;height:50px;"/>
				<span style="position:absolute;left:170px;top:18px">12</span>
				<span style="position:absolute;left:60px;top:69px">设备离线</span>
				<img src="${pageContext.request.contextPath}/images/offline.png" style="position:absolute;left:120px;height:20px;top:68px;"/>
			</div>
        </div>
     </div>
     <div class="m-style M-box1" style="float:right;"></div>
     <div id="audioDiv">
     	<audio id="alarmAudio" loop src="${pageContext.request.contextPath}/audio/warn.mp3">你的浏览器不支持audio标签</audio>
     </div>
</div>
<script type="text/javascript">
	var deviceList;
	var currentPage=1;
	var totalCount=0;
	var refresh=true;
	var stopPosition="";
	var currentSetting="";
	layui.use('form', function(){
	  var form = layui.form;
	  form.render(); //更新全部
	  form.on('radio(re)', function(data){
		 window.clearInterval(a);
		 a = self.setInterval("setData()", $("input[name='r']:checked").val() * 1000);
	  });
	});
	var a = self.setInterval("setData()", 10 * 1000);
	var b = self.setInterval("getData()", 3 * 1000);
	function setData() {
		if (totalCount == 0) {
			$(".layui-row").html("");
			return;
		}
		$(".layui-row").html("");
		var html = "<div class='layui-row' style='margin-top: 10px'>";
		var end;
		if (currentPage*12>totalCount) {
			end = totalCount;
		} else {
			end = currentPage*12;
		}
		for (var i=(currentPage-1)*12;i<end;i++) {
			var deviceStatus = "设备离线";
			var statusIcon = "offline";
			var peopleIcon = "people";
			var battery = deviceList[i].dePowerVoltage;
			var gasLevel = deviceList[i].gasLevel;
			var gasLevel1 = deviceList[i].gasLevel1;
			var batteryColor = "#0088ff";
			var o2Color = "#00CD00";
			var ch4Color = "#00CD00";
			var position = "";
			var title = "";
			if (stopPosition.indexOf("#"+deviceList[i].deviceNo+"$") == -1) {
				position = deviceList[i].deTemp;
			} else {
				peopleIcon = "nopeople";
			}
			if (Date.parse(new Date()) - deviceList[i].createTime < 3*60*1000) {
				deviceStatus = "设备在线"
				statusIcon = "online";
				if (deviceList[i].warningState == 1 || deviceList[i].O2Warning==1 || deviceList[i].CH4Warning==1) {
					deviceStatus = "设备报警";
					statusIcon = "warn";
					if (deviceList[i].O2Warning==1) {
						o2Color = "#ff0000";
					}
					if (deviceList[i].CH4Warning==1) {
					    ch4Color = "#ff0000";
					}
				}
			}
			if (deviceList[i].dePowerVoltage > 4120) {
				battery = 4120;
			}
			if (deviceList[i].dePowerVoltage < 0) {
				battery = 0;
			}
			if (battery >3500 && battery < 3600) {
				batteryColor = "#FFD700"
			}
			if (battery <= 3500) {
				batteryColor = "#ff0000"
			}
			if (deviceList[i].gasLevel > 100) {
				gasLevel = 100;
			}
			if (deviceList[i].gasLevel < 0) {
				gasLevel = 0;
			}
			if (deviceList[i].gasLevel1 > 100) {
				gasLevel1 = 100;
			}
			if (deviceList[i].gasLevel1 < 0) {
				gasLevel1 = 0;
			}
			if (deviceList[i].position==null||deviceList[i].position == 'null' || deviceList[i].position=='') {
				title="设备"+deviceList[i].deviceNo;
			} else {
				title=deviceList[i].position;
			}
			html+=("<div class='layui-col-md2'>"+
					"<span style='font-size:20px;font-weight:bold'>—"+title+"—</span></br></br>"+
					"<span style='font-size:20px;font-weight:bold'>氧气浓度</span></br>"+
					"<div style='height:40px;width:200px;position:relative'>"+
					"<div style='position:absolute;background-color:"+o2Color+";height:38px;width:"+gasLevel*2+"px'></div>"+
					"<img src='${pageContext.request.contextPath}/images/process.png' style='width:200px;height:40px;position:absolute;'/>"+
					"<span style='position:absolute;left:60px;top:10px;font-size:18px;font-weight:1'>"+gasLevel+"%</span>"+
					"</div>"+
					"<span style='font-size:20px;font-weight:bold'>可燃气体浓度</span></br>"+
					"<div style='height:40px;width:200px;position:relative'>"+
					"<div style='position:absolute;top:1px;left:1px;background-color:"+ch4Color+";height:38px;width:"+gasLevel1*1.98+"px'></div>"+
					"<img src='${pageContext.request.contextPath}/images/process.png' style='width:200px;height:40px;position:absolute;'/>"+
					"<span style='position:absolute;left:60px;top:10px;font-size:18px;font-weight:1'>"+gasLevel1+"%</span>"+
					"</div>"+
					"<span style='font-size:20px;font-weight:bold'>电池&nbsp;&nbsp;&nbsp;&nbsp;温度&nbsp;&nbsp;&nbsp;人员定位</span></br>"+
					"<div style='height:150px;width:200px;position:relative'>"+
					"<div style='background-image:url(\"${pageContext.request.contextPath}/images/battery.png\");width:50px;height:25px;background-size: cover; -moz-background-size: cover; position:absolute;z-index:1'></div>"+
					"<div style='background-color:"+batteryColor+";width:"+battery/4120*44+"px;height:23px;position:absolute;left:2px;top:1px'> </div>"+
					"<span style='position:absolute;left:1px;top:30px'>"+battery+"mV</span>"+
					"<img src='${pageContext.request.contextPath}/images/temperature.png' style='width:25px;height:25px;position:absolute;left:70px;top:1px'/>"+
					"<span style='position:absolute;left:70px;top:30px'>"+deviceList[i].temperature+"°C</span>"+
					"<img src='${pageContext.request.contextPath}/images/"+peopleIcon+".png' style='position:absolute;left:125px;height:50px;'/>"+
					"<span style='position:absolute;left:175px;top:18px'>"+position+"</span>"+
					"<span style='position:absolute;left:60px;top:69px'>"+deviceStatus+"</span>"+
					"<img src='${pageContext.request.contextPath}/images/"+statusIcon+".png' style='position:absolute;left:120px;height:20px;top:68px;'/>");
			if (${USER_CONTEXT.roleId} == 5) {
				html+="<img src='${pageContext.request.contextPath}/images/setting.png' style='position:absolute;left:1px;height:30px;top:60px;' onclick='setName("+deviceList[i].deviceNo+")'/>";
			}
			html+="</div></div>";
		}
		html+="</div>";
		$(".layui-row").append(html);
		
		
		//判断最后一页回到第一页
		var total;
		if (totalCount % 12 == 0) {
			total = totalCount/12;
		} else {
			total =  parseInt(totalCount/12) + 1;
		}
		$('.M-box1').pagination({
			pageCount:total,
		    totalData:totalCount,
		    showData:12,
		    coping:true,
		    isHide:true,
		    current:currentPage,
		    callback: function(api) {
		    	if (refresh) {
		    		window.clearInterval(a);
		    	}
		    	currentPage = api.getCurrent();
		    	setData(api.getCurrent());
		    	if (refresh) {
			    	a = self.setInterval("setData()", $("input[name='r']:checked").val() * 1000);
		    	}
			},
		});
		currentPage++;
		if (currentPage > total) {
			currentPage = 1;
		}
	}
	function getData() {
		$.ajax({
            type : "get",
            url : "${pageContext.request.contextPath}/gas_monitor/index.do",
            async : false,
            cache:false,
            success:function(data){
                if (data.success) {
            		deviceList = data.data.list;
	                totalCount = data.data.totalCount;
                } else {
                	parent.window.location.href = "login.jsp";
                }
            }
        });
		$("#alarmAudio")[0].pause();
		var warnIds = "";
		//遍历是否有报警设备
		for (var i=0;i<totalCount;i++) {
			if (deviceList[i].warningState == 1 || deviceList[i].O2Warning==1 || deviceList[i].CH4Warning==1) {
				if (Date.parse(new Date()) - deviceList[i].createTime < 3*60*1000) {
					warnIds += (deviceList[i].deviceNo + " ");
				}
			}
		}
		if (warnIds == "") {
			$("#warnIds").html("无");
			return true;
		} else {
			$("#warnIds").html(warnIds);
			for (var i=0;i<totalCount;i++) {
				if (deviceList[i].warningState == 1 || deviceList[i].O2Warning==1 || deviceList[i].CH4Warning==1) {
					if (Date.parse(new Date()) - deviceList[i].createTime < 3*60*1000) {
						//开始报警，并定位到报警设备页面
						currentPage = parseInt((i/12)) + 1;
						if (refresh) {
							window.clearInterval(a);
						}
						setData();
						if (refresh) {
							a = self.setInterval("setData()", $("input[name='r']:checked").val() * 1000);
						}
						$("#alarmAudio")[0].play();
						return false;
					}
				}
			}
		}
		
	}
	$(document).ready(function(){
	    //进入页面，去拿数据，然后赋值；
	    if (getData()) {
	    	setData();	
	    }
	});
	
	$("#stopAutoSearch").click(function() {
		 window.clearInterval(a);
		 refresh = false;
		 $("#startAutoSearch").attr("class","layui-btn layui-btn-normal layui-btn-radius");
		 $("#stopAutoSearch").attr("class","layui-btn layui-btn-disabled layui-btn-radius");
		 $("input[name='r']").attr("disabled","");
		 
	});
	$("#startAutoSearch").click(function() {
		$("input[name='r']").attr("disabled",false);
		refresh = true;
		a = self.setInterval("setData()", $("input[name='r']:checked").val() * 1000);
		$("#stopAutoSearch").attr("class","layui-btn layui-btn-normal layui-btn-radius");
		$("#startAutoSearch").attr("class","layui-btn layui-btn-disabled layui-btn-radius");
	});
	
	//屏蔽设备人员定位功能
	$("#stopUserPosition").click(function() {
		layer.open({
		  type: 2,
		  area: ['500px', '500px'],
		  title: "屏蔽人员定位",
		  fixed: false, //不固定
		  maxmin: true,
		  content: '${pageContext.request.contextPath}/jsp/stopPosition.jsp'
		});
	});
	
	//设置设备名称
	function setName(deviceNo) {
		currentSetting=deviceNo;
		layer.open({
		  type:2,
		  title:"设备" + deviceNo,
		  fixed:false,
		  maxmin:false,
		  content:'${pageContext.request.contextPath}/jsp/setting.jsp'
		});
	}
	
</script>
</body>
</html>