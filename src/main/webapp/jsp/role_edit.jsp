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
<script src="${pageContext.request.contextPath}/lib/zTree/v3/js/jquery.ztree.core-3.5.js"></script>  
<script src="${pageContext.request.contextPath}/lib/zTree/v3/js/jquery.ztree.excheck-3.5.js"></script>  
<script src="${pageContext.request.contextPath}/lib/zTree/v3/js/jquery.ztree.exedit-3.5.js"></script> 
<link href="${pageContext.request.contextPath}/lib/zTree/v3/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" />
<title>编辑角色</title>
</head>
<body>
<div class="pd-20">
  <div class="Huiform">
    <form action="${pageContext.request.contextPath}/role/editRole.do" method="post" class="form form-horizontal" id="form-role-edit">
		<input type="hidden" name="id" value="${r.id}" />
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>角色标志：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text"  id="role" name="role" value="${r.role}">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>角色名称：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text"   id="roleName" name="roleName" value = "${r.roleName}">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>角色描述：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<input type="text" class="input-text"  name="description" id="description" value="${r.description}">
			</div>
		</div>
		<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>资源配置：</label>
			<div class="formControls col-xs-8 col-sm-9">
				<ul id="tree" class="ztree" style="width: 560px; overflow: auto;"></ul>
			</div>
		</div>
		<div class="row cl">
			<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
				<input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;修改&nbsp;&nbsp;">
			</div>
		</div>
	</form>
  </div>
</div>
</body>
<script type="text/javascript">
var setting = {  
   	async: {  
	   	  enable: true,  
          url: "../resource/getResources.do",  
          dataFilter: filter,  
          contentType: "application/json",  
          type:"get" 
   },
   callback: {  
       onAsyncSuccess: onAsyncSuccess  
   },
   check: {  
        enable: true  
   },  
   edit: {  
        enable: false  
   },  
   data: {  
        simpleData: {  
            enable: true  
        }  
   },  
};  
function filter(treeId, parentNode, childNodes) {  
    if (!childNodes) return null;  
    for (var i = 0, l = childNodes.length; i < l; i++) {  
        childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');  
    }  
    return childNodes;  
}  

function onAsyncSuccess() {
	var treeObj = $.fn.zTree.getZTreeObj("tree");
	//给tree赋值，勾选
    var rIds = "${r.rIds}";
    var ids = rIds.split(",");
    for (var i=0;i<ids.length;i++) {
    	if(ids[i] != "") {
	    	treeObj.checkNode(treeObj.getNodeByParam("id",ids[i]),true);
    	}
    }
}
$(function () {  
	$.fn.zTree.init($("#tree"), setting);
});

$("#form-role-edit").validate({
	rules:{
		roleName:{
			required:true,
			minlength:2,
			maxlength:16
		},
		role:{
			required:true,
		},
	},
	onkeyup:false,
	focusCleanup:false,
	success:"valid",
	submitHandler:function(form){
		var rIds = "";
		var treeObj = $.fn.zTree.getZTreeObj("tree");
		var nodes = treeObj.getCheckedNodes(true);
		for (var i=0;i<nodes.length;i++) {
			rIds+=nodes[i].id + ",";
		}
		$(form).ajaxSubmit({
			data:{rIds:rIds},
			success:function(data){
				if (data.success){
					alert("修改成功");
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
</html>