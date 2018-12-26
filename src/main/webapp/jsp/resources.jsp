<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/jquery/1.9.1/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/lib/zTree/v3/js/jquery.ztree.core-3.5.js"></script>  
<script src="${pageContext.request.contextPath}/lib/zTree/v3/js/jquery.ztree.excheck-3.5.js"></script>  
<script src="${pageContext.request.contextPath}/lib/zTree/v3/js/jquery.ztree.exedit-3.5.js"></script> 
<link href="${pageContext.request.contextPath}/lib/zTree/v3/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" />
<script type="text/javascript" src="${pageContext.request.contextPath}/static/h-ui/js/H-ui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/h-ui.admin/js/H-ui.admin.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/lib/layer/2.4/layer.js"></script>
<title>资源管理</title>
<script type="text/javascript">
   var currentId;
   var setting = {  
	   async: {  
		   enable: true,  
           url: "../resource/getResources.do",  
           dataFilter: filter,  
           contentType: "application/json",  
           type:"get" 
	   },  
	   view: {  
	        expandSpeed: "",  
	        addHoverDom: addHoverDom,  
	        removeHoverDom: removeHoverDom,  
	        selectedMulti: false  
	   },  
	   check: {  
	        enable: false  
	   },  
	   edit: {  
	        enable: true  
	   },  
	   data: {  
	        simpleData: {  
	            enable: true  
	        }  
	   },  
	   callback: {  
	        beforeRemove: beforeRemove,  
	        beforeRename: beforeRename,  
	        beforeEditName:beforeEditName,
	   }  
	};  
	function filter(treeId, parentNode, childNodes) {  
	    if (!childNodes) return null;  
	    for (var i = 0, l = childNodes.length; i < l; i++) {  
	        childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');  
	    }  
	    return childNodes;  
	}  
	function beforeEditName(treeId, treeNode) {
		layer_show("编辑资源","${pageContext.request.contextPath}/resource/getEditPage.do?id="+treeNode.id,500,500);
		return false;
	}
	function beforeRemove(treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");  
    	zTree.selectNode(treeNode);  
    	if (confirm("确认删除 节点 -- " + treeNode.name + " 吗？")) {
    		$.ajax({
    			url:"${pageContext.request.contextPath}/resource/deleteResource.do?id="+treeNode.id,
    			method:"GET",
    			success:function(data){
    				if (data.success) {
    					alert("删除成功");
    					location.reload();
    				} else {
    					layer.msg(data.message,{icon:5,time:1000});
    				}
    			}
    		});
    		return false;
    	}  
	}  
	function beforeRename(treeId, treeNode, newName) {  
	    if (newName.length == 0) {  
	        alert("节点名称不能为空.");  
	        return false;  
	    }  
	    return true;  
	}  
	
	var newCount = 1;  
	function addHoverDom(treeId, treeNode) {  
	    var sObj = $("#" + treeNode.tId + "_span");  
	    if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0) return;  
	    var addStr = "<span class='button add' id='addBtn_" + treeNode.tId  
	        + "' title='add node' onfocus='this.blur();'></span>";  
	    sObj.after(addStr);  
	    console.log("add   " + "#addBtn_" + treeNode.id);  
	    var btn = $("#addBtn_" + treeNode.tId);  
	    if (btn) btn.bind("click", function () {  
// 	        var zTree = $.fn.zTree.getZTreeObj("treeDemo");  
// 	        zTree.addNodes(treeNode, { id: (100 + newCount), pId: treeNode.id, name: "new node" + (newCount++) });  
	        currentId = treeNode.id;
	        layer_show("添加资源","${pageContext.request.contextPath}/jsp/resource_add.jsp",500,500);
	        return false;  
	    });  
	};  
	function removeHoverDom(treeId, treeNode) {
		    console.log("remove   " + "#addBtn_" + treeNode.id);  
		    $("#addBtn_" + treeNode.tId).unbind().remove();  
	};  
	$(function () {  
	    $.fn.zTree.init($("#treeDemo"), setting);
	});
</script>
</head>
<body>
	<ul id="treeDemo" class="ztree" style="width: 560px; overflow: auto;"></ul>
</body>
</html>