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
    <!-- 注意：如果你直接复制所有代码到本地，上述css路径需要改成你本地的 -->
    <script src="${pageContext.request.contextPath}/lib/layui/layui.js" charset="utf-8"></script>
	<script src="${pageContext.request.contextPath}/lib/jquery/1.9.1/jquery.min.js" charset="utf-8"></script>
</head>
<body>
	<br/>
	<br/>
	<span style="font-weight:bold;font-size:20px">&nbsp;&nbsp;设置位置：</span>
	<input type="text" id="position" placeholder="请输入位置" style="height:30px">
	<button id="confirm" class="layui-btn layui-btn-normal layui-btn-radius" style="float:right;margin-top:8px;margin-right:10px">确定</button>
</body>
<script type="text/javascript">
	$("#confirm").click(function() {
		if ($("#position").val().length > 8) {
			alert("最大输入8个字符");
			return;
		}
		$.ajax({
			type:"POST",
			dataType:"json",
			data:{"deviceNo":parent.currentSetting,"position":$("#position").val()},
			url:"${pageContext.request.contextPath}/gas_monitor/setPosition.do",
			success:function(data){
				var index = parent.layer.getFrameIndex(window.name);
        		parent.layer.close(index);
            }
		});
	});
</script>
</html>