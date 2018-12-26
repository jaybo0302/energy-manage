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
	<form class="layui-form" action="">
		<div class="layui-form-item">
		    <div class="layui-input-block">
		      <input type="checkbox" name="stop" value="1" title="设备1&nbsp;&nbsp;" lay-filter="position">
		      <input type="checkbox" name="stop" value="2" title="设备2&nbsp;&nbsp;" lay-filter="position">
		      <input type="checkbox" name="stop" value="3" title="设备3&nbsp;&nbsp;" lay-filter="position">
		      <input type="checkbox" name="stop" value="4" title="设备4&nbsp;&nbsp;" lay-filter="position">
		      <input type="checkbox" name="stop" value="5" title="设备5&nbsp;&nbsp;" lay-filter="position">
		      <input type="checkbox" name="stop" value="6" title="设备6&nbsp;&nbsp;" lay-filter="position">
		      <input type="checkbox" name="stop" value="7" title="设备7&nbsp;&nbsp;" lay-filter="position">
		      <input type="checkbox" name="stop" value="8" title="设备8&nbsp;&nbsp;" lay-filter="position">
		      <input type="checkbox" name="stop" value="9" title="设备9&nbsp;&nbsp;" lay-filter="position">
		      <input type="checkbox" name="stop" value="10" title="设备10" lay-filter="position">
		      <input type="checkbox" name="stop" value="11" title="设备11" lay-filter="position">
		      <input type="checkbox" name="stop" value="12" title="设备12" lay-filter="position">
		      <input type="checkbox" name="stop" value="13" title="设备13" lay-filter="position">
		      <input type="checkbox" name="stop" value="14" title="设备14" lay-filter="position">
		      <input type="checkbox" name="stop" value="15" title="设备15" lay-filter="position">
		      <input type="checkbox" name="stop" value="16" title="设备16" lay-filter="position">
		      <input type="checkbox" name="stop" value="17" title="设备17" lay-filter="position">
		      <input type="checkbox" name="stop" value="18" title="设备18" lay-filter="position">
		      <input type="checkbox" name="stop" value="19" title="设备19" lay-filter="position">
		      <input type="checkbox" name="stop" value="20" title="设备20" lay-filter="position">
		      <input type="checkbox" name="stop" value="21" title="设备21" lay-filter="position">
		      <input type="checkbox" name="stop" value="22" title="设备22" lay-filter="position">
		      <input type="checkbox" name="stop" value="23" title="设备23" lay-filter="position">
		      <input type="checkbox" name="stop" value="24" title="设备24" lay-filter="position">
		      <input type="checkbox" name="stop" value="25" title="设备25" lay-filter="position">
		      <input type="checkbox" name="stop" value="26" title="设备26" lay-filter="position">
		      <input type="checkbox" name="stop" value="27" title="设备27" lay-filter="position">
		      <input type="checkbox" name="stop" value="28" title="设备28" lay-filter="position">
		      <input type="checkbox" name="stop" value="29" title="设备29" lay-filter="position">
		      <input type="checkbox" name="stop" value="30" title="设备30" lay-filter="position">
		    </div>
		</div>
	</form>
<script type="text/javascript">
	layui.use('form', function(){
	  var form = layui.form;
	  form.render(); //更新全部
	  form.on('checkbox(position)', function(data){
		 //修改父窗口position的值，添加上选中的设备编号
		 if (data.elem.checked) {
			 //添加编号
			 parent.stopPosition += ("#"+data.value+"$");
		 } else {
			 //去掉编号
			 parent.stopPosition = parent.stopPosition.replace("#"+data.value+"$", "");
		 }
	  });
	});
	
	$(document).ready(function(){
	    var stops = parent.stopPosition;
	    //遍历checkbox，判断是否需要选中
	    $('input[type="checkbox"][name="stop"]').each(
                function() {
                      if (stops.indexOf("#" + $(this).val() + "$") != -1) {
                    	  $(this).attr("checked", true);
                      }
                }
            );
	});
</script>
</body>
</html>