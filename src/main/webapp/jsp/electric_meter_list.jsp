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

<link href="${pageContext.request.contextPath}/css/pagination.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/highlight.min.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/common.css" rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/css/reset.css" rel="stylesheet" type="text/css" />
<title>电表管理</title>
<%
	String contextPath = request.getContextPath();
%>
</head>

<body>
<div class="page-container">
	<div class="cl pd-5 bg-1 bk-gray mt-20"> <span class="l"><a href="javascript:;" onclick="addDevice()" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i> 添加电表</a></span></div>
	<br/>
	<table class="table table-border table-bordered table-hover table-bg table-sort" id ="electricmeterTable">
	</table>
	<br/>
	
	<div class="m-style M-box1" style="float:right;"></div>
	<input type="hidden" id = "pageNo"/> 
</div>
</div>
</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/cd-table.js?v=2"></script>
<script type="text/javascript">
	var pageSize = 10;
	var contextPath = "<%=contextPath%>";
    var tableParam = [{"field":"电表名称",
		   			   "name":"deviceName"}
				 	 ,{"field":"电表号",
    				   "name":"deviceNo"}
    				 ,{"field":"位置",
      				   "name":"position"}
    				 ,{"field":"串口号",
        			   "name":"port"}
    				 ,{"field":"状态",
    				   "name":"status",
    				   formatter:function(status){
    					   if (status ==0 ) {
    						   return "<font color='red'>启用</font>";
    					   } else if (status ==1) {
    						   return "停用";
    					   }
    				   }}
    				 ,{"field":"操作",
    				   "name":"operate",
    				   "operates":[
    					   {"title":"修改状态",
    						"icon":"&#xe63c;",
    						"function":"updateDevice",
    						"param":"deviceNo,status"},
    					   {"title":"编辑",
    						"icon":"&#xe6df;",
    						"function":"editDevice",
    						"param":"deviceNo"},
    				   ]}];
	var moduleName = "electricmeter";
	function updateDevice(id,deviceNo, status) {
		if (status == 1) {
			layer.confirm('确认要启用该电表吗？',function(index){
				$.ajax({
					type: 'POST',
					url: '${pageContext.request.contextPath}/electricmeter/updateElectricmeterStatus.do',
					data:{"deviceNo":deviceNo,"status":0},
					dataType: 'json',
					success: function(data){
						if(data.success) {
							refresh(currentNo);
							layer.msg('已启用!',{icon:1,time:1000});
						} else {
							layer.msg(data.message,{icon:1,time:1000});
						}
					},
					error:function(data) {
						layer.msg(data,{icon:1,time:1000});
					},
				});
			});
		} else if (status == 0) {
			layer.confirm('确认要停用该电表吗？',function(index){
				$.ajax({
					type: 'POST',
					url: '${pageContext.request.contextPath}/electricmeter/updateElectricmeterStatus.do',
					data:{"deviceNo":deviceNo,"status":1},
					dataType: 'json',
					success: function(data){
						if(data.success) {
							refresh(currentNo);
							layer.msg('已停用!',{icon:1,time:1000});
						} else {
							layer.msg(data.message,{icon:1,time:1000});
						}
					},
					error:function(data) {
						layer.msg(data,{icon:1,time:1000});
					},
				});
			});
		} else {
			alert("错误的状态码");
		}
	}
	
	function editDevice(id,deviceNo) {
		layer_show("编辑电表","${pageContext.request.contextPath}/electricmeter/getEditPage.do?deviceNo="+deviceNo,800,500);
	}
	
	function addDevice() {
		layer_show("添加电表 ","${pageContext.request.contextPath}/jsp/electric_meter_add.jsp",800,500);
	}
</script>
</html>