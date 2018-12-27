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
<script src="${pageContext.request.contextPath}/lib/bootstrap/js/bootstrap.min.js" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/lib/bootstrap/js/bootstrap-datetimepicker.min.js" charset="utf-8"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/lib/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/lib/bootstrap/css/bootstrap-datetimepicker.min.css">
<script src="${pageContext.request.contextPath}/lib/layui/laydate.js" charset="utf-8"></script>

<title>电表数据</title>
<%
	String contextPath = request.getContextPath();
%>
</head>

<body>
<div class="page-container">
  	<div>
	  	<br/>
		<form id="searchForm">
			<div class="col-lg-2">
		  		<div class="input-group date">
		  			<span class="input-group-addon">时间</span>
			        <input id="startDate" name="startDate" class="form-control form-control-left" style="width:100px">
			        <span class="input-group-addon">-</span>
			        <input id="endDate" name="endDate" class="form-control form-control-right" style="width:100px">
			    </div>
			 </div>
			 <div class="col-lg-1">
			 </div>
			 <div class="col-lg-2">
			 	<select class="form-control" name="devices" id="devices">
			      <option name="0">全部设备</option>
			    </select>
			 </div>
			&nbsp;
			<button name="" id="searchButton" class="btn btn-primary radius" type="button"><i class="Hui-iconfont">&#xe665;</i> 搜索</button>
			&nbsp;
			<button name="" id="resetButton" class="btn btn-primary radius" type="button"><i class="Hui-iconfont">&#xe6e2;</i> 清空</button>
			&nbsp;
			<button name="" id="export" class="btn btn-primary radius" type="button" style="algin-right:true"><i class="Hui-iconfont">&#xe644;</i> 导出</button>
		</form>
  	</div>
	<br/>
	<div>
		<span>&nbsp;共&nbsp;</span><i id="totalCount">0</i><span>&nbsp;条数据</span>
	</div>
	<table class="table table-border table-bordered" id ="meterinfoTable">
	</table>
	<br/>
	
	<div class="m-style M-box1" style="float:right;"></div>
	<input type="hidden" id = "pageNo"/> 
</div>
</div>
</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/cd-table.js?v=2"></script>
<script type="text/javascript">
	laydate.render({
	    elem: '#endDate'
	  });
	laydate.render({
	    elem: '#startDate'
	  });
	$.ajax({
		url:"${pageContext.request.contextPath}/electricmeter/getDevicesByCompany.do",
		async:false,
		method:"GET",
		cache:false,
		success:function(result) {
			var data = result.data;
			$("#devices").empty();
			var str ="<option value='0'>全部设备</option>";
			for (var i = 0;i<data.length;i++) {
				str+=("<option value='"+data[i].deviceNo+"'>"+data[i].deviceName+"</option>");
			}
			$("#devices").append(str);
		},
	});
	var pageSize = 10;
	var contextPath = "<%=contextPath%>";
    var tableParam = [{"field":"电表名称",
    				   "name":"deviceName"}
    				 ,{"field":"位置",
       				   "name":"position",
       				   "width":"200px"}
    				 ,{"field":"时间",
       				   "name":"dateTimeStr",
       				   "width":"200px"}
    				 ,{"field":"总有功功率",
    				   "name":"activePower"}
    				 ,{"field":"总无功功率",
      				   "name":"reactivePower"}
    				 ,{"field":"A相电压",
      				   "name":"aV"}
    				 ,{"field":"B相电压",
      				   "name":"bV"}
    				 ,{"field":"C相电压",
      				   "name":"cV"}
    				 ,{"field":"A相电流",
      				   "name":"aA"}
    				 ,{"field":"B相电流",
      				   "name":"bA"}
    				 ,{"field":"C相电流",
      				   "name":"cA"}
    				 ];
	var moduleName = "meterinfo";
</script>
</html>