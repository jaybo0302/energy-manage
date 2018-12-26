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
<!--[if lt IE 9]><![endif]-->
<%@include file="static.jsp" %>
<title>添加资源</title>
</head>
<body>
<div class="pd-20">
  <div class="Huiform">
    <form action="${pageContext.request.contextPath}/resource/addResource.do" method="post" class="form form-horizontal" id="form-resource-edit">
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>标志：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text"  id="resource" name="resource">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>名称：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text"   id="name" name="name">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>描述：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text"  name="description" id="description" >
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>地址：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text"  name="url" id="url">
			</div>
		</div>
		<div class="row cl">
			<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
				<input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;添加&nbsp;&nbsp;">
			</div>
		</div>
	</form>
  </div>
</div>
<script type="text/javascript">
$("#form-resource-edit").validate({
	rules:{
		name:{
			required:true,
			minlength:2,
			maxlength:16
		},
		resource:{
			required:true,
		},
		url:{
			required:true,
		},
	},
	onkeyup:false,
	focusCleanup:false,
	success:"valid",
	submitHandler:function(form){
		$(form).ajaxSubmit({
			data:{pId:parent.currentId},
			success:function(data){
				if (data.success){
					alert("添加成功");
					parent.location.reload();
					var index = parent.layer.getFrameIndex(window.name);
	        		parent.layer.close(index);
				} else {
					layer.msg(data.message,{icon:5,time:2000});
				}
			}
		});
	}
});
</script>
</body>
</html>