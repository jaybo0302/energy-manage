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
<%@include file="static.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/echarts/3.4.0/echarts.common.min.js"></script>
<script src="${pageContext.request.contextPath}/lib/layui/layui.js" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/lib/bootstrap/js/bootstrap.min.js" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/lib/bootstrap/js/bootstrap-datetimepicker.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/bootstrap/js/bootstrap-datetimepicker.fr.js" charset="UTF-8"></script>
<link href="${pageContext.request.contextPath}/css/pagination.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/highlight.min.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/common.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/reset.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/lib/layui/css/layui.css"  media="all">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/en.css"  media="all">
<link rel="stylesheet" href="${pageContext.request.contextPath}/lib/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/lib/bootstrap/css/bootstrap-datetimepicker.min.css">
<script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
<script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>


<title>电表数据</title>
<%
	String contextPath = request.getContextPath();
%>
</head>

<body> 
  <div>
  	<br/>
  	<div class="row">
<!--   		<div class="col-lg-1"> -->
<!--   			<button id="daySelect" class="btn btn-success" style="margin-left:40px">天视图</button> -->
<!--   		</div> -->
<!--   		<div class="col-lg-1"> -->
<!--   			<button id="monthSelect" class="btn btn-default">月视图</button> -->
<!--   		</div> -->
  		<div class="col-lg-2">
	  		<div class="form-group">
		        <div class="input-group date form_datetime col-md-2">
		            <input class="form-control date-picker" size="16" type="text" value="" readonly style="width:100px">
		            <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
		        </div>
		    </div>
		 </div>
<!-- 		 <div class="col-lg-2"> -->
<!-- 		 	<select class="form-control"> -->
<!-- 		      <option>全部设备</option> -->
<!-- 		    </select> -->
<!-- 		 </div> -->
    </div>
  </div>
  
</body>
<script type="text/javascript">
	$('.date-picker').datetimepicker({
	    weekStart: 1,
		autoclose: 1,
		startView: "month",
		forceParse: 0,
	    showMeridian: true,
	    minView: "month",
	    format: "yyyy-mm-dd",
	});
	
</script>
</html>