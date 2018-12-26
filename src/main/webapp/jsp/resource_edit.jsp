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
<title>编辑资源</title>
</head>
<body>
<div class="pd-20">
  <div class="Huiform">
    <form action="${pageContext.request.contextPath}/resource/editResource.do" method="post" class="form form-horizontal" id="form-resource-edit">
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>标志：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${r.resource}" id="resource" name="resource">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>名称：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text" value="${r.name}"  id="name" name="name">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>描述：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text"  name="description" id="description" value="${r.description}">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>地址：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text"  name="url" id="url" value="${r.url}">
			</div>
		</div>
		<input type="hidden" value = "${r.id}" name = "id"/>
		<input type="hidden" value = "${r.pId}" name = "pId"/>
		<div class="row cl">
			<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
				<input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;修改&nbsp;&nbsp;">
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
			success:function(data){
				if (data.success){
					alert("修改成功");
					var index = parent.layer.getFrameIndex(window.name);
					parent.location.reload();
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