<%@ page import="com.cdwoo.entity.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%
    String contextPath = request.getContextPath();
    request.setAttribute("context",contextPath);
%>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <link href='https://fonts.googleapis.com/css?family=Open+Sans:400,700' rel='stylesheet' type='text/css'>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/normalize.min.css">
	<link href='${pageContext.request.contextPath}/css/style.css' rel='stylesheet' type='text/css'>
    <!--[if IE 6]>
    <script type="text/javascript" src="/lib/DD_belatedPNG_0.0.8a-min.js" ></script>
    <script>DD_belatedPNG.fix('*');</script>
    <![endif]-->
    <title>无组织一体化后台系统 - 登录</title>
</head>
<body>
<div class="wrapper">
    <form id="loginForm" class="login" action="${pageContext.request.contextPath}/common/login.do" method="post">
	    <p class="title">登录</p>
	    <input type="text" id="userName" name="userName" placeholder="Username" autofocus/>
	    <i class="fa fa-user"></i>
	    <input type="password" id="password" name="password" placeholder="Password" />
	    <i class="fa fa-key"></i>
	    <c:if test="${!empty errorMsg}">
            <span style="color:red; text-align: center">${errorMsg}</span>
        </c:if>
	    <button id="loginButton" type="submit">登录</button>
    </form>
	<div style="text-align:center;clear:both;margin-top:80px"></div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript">
    var user_id = ${USER_CONTEXT.id};
    if(user_id != null){
        window.location.href = "${pageContext.request.contextPath}/common/index.do";
    }
</script>
</body>
</html>