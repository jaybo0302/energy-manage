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
<title>用户管理</title>
<%
	String contextPath = request.getContextPath();
%>
</head>

<body>
<div class="page-container">
	<div class="cl pd-5 bg-1 bk-gray mt-20"> <span class="l"><a href="javascript:;" onclick="addUser()" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i> 添加用户</a></span></div>
	<br/>
	<table class="table table-border table-bordered table-hover table-bg table-sort" id ="userTable">
	</table>
	<br/>
	
	<div class="m-style M-box1" style="float:right;"></div>
	<input type="hidden" id = "pageNo"/> 
</div>
</div>
</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/cd-table.js"></script>
<script type="text/javascript">
	var pageSize = 10;
	var contextPath = "<%=contextPath%>";
    var tableParam = [{"field":"厂家名称",
		   		       "name":"companyName"}
    				 ,{"field":"用户名",
    				   "name":"userName"}
    				 ,{"field":"角色",
    				   "name":"roleName"}
    				 ,{"field":"名称",
    				   "name":"realName"}
    				 ,{"field":"创建时间",
    				   "name":"createTimeStr"}
    				 ,{"field":"操作",
    				   "name":"operate",
    				   "operates":[
    					   {"title":"删除",
    						"icon":"&#xe6e2;",
    						"function":"deleteUser"},
    					   {"title":"编辑",
    						"icon":"&#xe6df;",
    						"function":"editUser"}
    				   ]}];
	var moduleName = "user";
	
	function deleteUser(id) {
		layer.confirm('确认要删除吗？',function(index){
			$.ajax({
				type: 'POST',
				url: '${pageContext.request.contextPath}/user/deleteUser.do',
				data:{"id":id},
				dataType: 'json',
				success: function(data){
					if(data.success) {
						refresh(currentNo);
						layer.msg('已删除!',{icon:1,time:1000});
					} else {
						layer.msg(data.message,{icon:1,time:1000});
					}
				},
				error:function(data) {
					layer.msg(data,{icon:1,time:1000});
				},
			});
		});
	}
	
	function editUser(id) {
		layer_show("编辑用户","${pageContext.request.contextPath}/user/getEditPage.do?id="+id,800,500);
	}
	
	function addUser() {
		layer_show("添加用户","${pageContext.request.contextPath}/jsp/user_add.jsp",800,500);
	}
</script>
</html>