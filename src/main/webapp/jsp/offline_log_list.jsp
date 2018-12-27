<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="Bookmark" href="/favicon.ico" >
<link rel="Shortcut Icon" href="/favicon.ico" />
<%@include file="static.jsp" %>
<script src="${pageContext.request.contextPath}/lib/layui/laydate.js" charset="utf-8"></script>
<title>电表掉线数据</title>
<%
	String contextPath = request.getContextPath();
%>
</head>

<body>
<div class="page-container">
	<form id="searchForm">
			<div class="col-xs-5">
		        <input class="input-text" id="startDate" name="startDate" style="width:100px" />
		        <label class="form-label col-xs-1">-</label>
		        <input class="input-text" id="endDate" name="endDate" style="width:100px"/>
	    	</div>
	    	&nbsp;
			<button id="searchButton" class="btn btn-primary radius" type="button"><i class="Hui-iconfont">&#xe665;</i> 搜索</button>
			&nbsp;
			<button id="resetButton" class="btn btn-primary radius" type="button"><i class="Hui-iconfont">&#xe6e2;</i> 清空</button>
	</form>
	<br/>
	<table class="table table-border table-bordered table-hover table-bg table-sort" id ="offlineTable"></table>
	<div class="m-style M-box1" style="float:right;"></div>
	<input type="hidden" id = "pageNo"/>
</div>
</div>
</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/cd-table.js"></script>
<script type="text/javascript">
	laydate.render({
	    elem: '#endDate'
	  });
	laydate.render({
	    elem: '#startDate'
	  });
	var pageSize = 10;
	var contextPath = "<%=contextPath%>";
    var tableParam = [{"field":"电表名称",
    				   "name":"deviceName"}
    				 ,{"field":"位置",
       				   "name":"position"}
    				 ,{"field":"离线时间",
    				   "name":"createTimeStr"}
    				 ,{"field":"离线时长",
      				   "name":"offlineLong",
	      			   formatter:function (offlineLong) {
	      				    var secondTime = parseInt(offlineLong);// 秒
	      				    var minuteTime = 0;// 分
	      				    var hourTime = 0;// 小时
	      				    if(secondTime > 60) {//如果秒数大于60，将秒数转换成整数
	      				        //获取分钟，除以60取整数，得到整数分钟
	      				        minuteTime = parseInt(secondTime / 60);
	      				        //获取秒数，秒数取余，得到整数秒数
	      				        secondTime = parseInt(secondTime % 60);
	      				        //如果分钟大于60，将分钟转换成小时
	      				        if(minuteTime > 60) {
	      				            //获取小时，获取分钟除以60，得到整数小时
	      				            hourTime = parseInt(minuteTime / 60);
	      				            //获取小时后取佘的分，获取分钟除以60取佘的分
	      				            minuteTime = parseInt(minuteTime % 60);
	      				        }
	      				    }
	      				    var result = "" + parseInt(secondTime) + "秒";
	      				
	      				    if(minuteTime > 0) {
	      				    	result = "" + parseInt(minuteTime) + "分" + result;
	      				    }
	      				    if(hourTime > 0) {
	      				    	result = "" + parseInt(hourTime) + "小时" + result;
	      				    }
	      				    return result;
	      				}}
    				 ];
	var moduleName = "offline";
</script>
</html>