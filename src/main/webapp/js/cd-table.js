//当前页面标志，方便刷新
var currentNo;
/**
 * 表格刷新方法
 * @param pageNo 页码
 * @returns
 */
function refresh(pageNo){
	//moduleName 模块名称，需要声明
	$("#"+moduleName+"Table").empty();
	var theadStr = "<thead><tr>";
	var tbodyStr = "<tbody>";
	/*tableParam 表格参数，需要声明 格式如下
				var tableParam = [{"field":"名称",
					   "name":"name"}
					 ,{"field":"部门",
					   "name":"department"}
					 ,{"field":"类型",
					   "name":"type"}
					 ,{"field":"地址",
					   "name":"address"}
					 ,{"field":"操作",
					   "name":"operate",
					   "operates":[
						   {"title":"删除",
							"icon":"&#xe6e2;",
							"function":"deleteTransformer"},
						   {"title":"编辑",
							"icon":"&#xe6df;",
							"function":"editTransformer"}
					   ]}];*/
	for(var i=0;i<tableParam.length;i++){
		theadStr+=("<th>"+tableParam[i]["field"]+"</th>");
	}
	theadStr+=("</tr></thead>");
	$("#"+moduleName+"Table").append(theadStr);
	//服务端controller方法名称规则，queryModulenameByPage.do
	$.ajax({"url":"../"+moduleName+"/query"+first2Up(moduleName)+"ByPage.do?pageSize="+pageSize+"&pageNo="+pageNo,
			"type":"POST",
			"dataType" : "json",
			async:false,
			data: $('#searchForm').serialize(),
			contentType:"application/x-www-form-urlencoded;charset=UTF-8",
			"success":function(result){
				if (result.success) {
					var data = result.data.data;
					for(var t=0;t<data.length;t++){
						tbodyStr+="<tr class='text-c'>";
						for(var i=0;i<tableParam.length;i++){
							var tdStr = "<td  class='text-c'";
							if (tableParam[i]["width"] != null) {
								tdStr+=" style='width:"+ tableParam[i]["width"]+"'";
							}
							tdStr+=">";
							if (tableParam[i]["name"] == "operate") {
								var str = "";
								for (var j=0;j<tableParam[i]["operates"].length;j++){
									var paramStr = "";
									if (tableParam[i]["operates"][j]["param"] != null) {
										var params = tableParam[i]["operates"][j]["param"].split(",");
										for (var x=0;x<params.length;x++) {
											paramStr+=("," + data[t][params[x]]); 
										}
									}
									str+=("<a style='text-decoration:none' onClick='"+tableParam[i]["operates"][j]["function"]+"("+data[t]["id"] + paramStr + ")' href='javascript:;' title='"+tableParam[i]["operates"][j]["title"]+"'><i class='Hui-iconfont'>"+tableParam[i]["operates"][j]["icon"]+"</i></a>&nbsp;");
								}
								tbodyStr+=(tdStr+str+"</td>");
							} else {
								if (tableParam[i].formatter != null) {
									tbodyStr+=(tdStr + tableParam[i].formatter(data[t][tableParam[i]["name"]])+"</td>");
								} else {
									tbodyStr+=(tdStr+data[t][tableParam[i]["name"]]+"</td>");
								}
							}
						}
						tbodyStr+=("</tr>");
					}
					tbodyStr+="</tbody>";
					$("#"+moduleName+"Table").append(tbodyStr);
					//分页插件配置。
					currentNo =pageNo;
					$('.M-box1').pagination({
						pageCount:result.data.totalCount/pageSize+1,
						totalData:result.data.totalCount,
						showData:pageSize,
						coping:true,
						jump:true,
						homePage:'首页',
						endPage:'尾页',
						isHide:true,
						current:result.data.currentPage,
						callback: function(api) {
							refresh(api.getCurrent());
						},
					});
				} else {
					parent.window.location.href = "login.jsp";
				}
			}
	});
}
//进入页面，首先刷新表格
$(function() {
	refresh(1);
});
//首字母大写方法。
function first2Up(str){ // 正则法
  str = str.toLowerCase();
  var reg = /\b(\w)|\s(\w)/g; //  \b判断边界\s判断空格
  return str.replace(reg,function(m){
    return m.toUpperCase();
  });
}
//搜索按钮
$("#searchButton").click(function() {
	refresh(1);
});
//搜索重置按钮
$("#resetButton").click(function() {
	$("#searchForm")[0].reset();
	refresh(1);
});

$("#export").click(function(){
	var title="";
	var names="";
	for(var i=0;i<tableParam.length;i++){
		if (tableParam[i]["name"] != "operate") {
			title += (tableParam[i]["field"] + ",");
			names += (tableParam[i]["name"] + ",");
		}
	}
	title = title.substring(0, title.length-1);
	names = names.substring(0, names.length-1);
	var url = "../"+moduleName+"/export.do?names=" + names + "&title=" +  encodeURI(encodeURI(title, "utf-8")) + "&" + $('#searchForm').serialize();
	parent.window.location.href= url;
});