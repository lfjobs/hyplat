$(function(){
	var title=" <form name='likeForm' id='likeForm'>场地分配 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 项目编号：" +
			"<input type='text' id='likeProductCode' style='width:100px;'>&nbsp;&nbsp;&nbsp;产品名称：" +
			"<input type='text' id='likePpName' style='width:100px;'>&nbsp;&nbsp;&nbsp;" +
			"<input type='button' id='like' class='input-button' value='查询' style=\"margin:0px;margin-left:5px;\"></form>";
	$('.fexlist').flexigrid({
		height : 145,
		width : 'auto',
		minwidth : 30,
		title : title,
		minheight : 80,
		buttons : [{
			name : '添加',
			bclass : 'add',
			onpress : action
				// 当点击调用方法
			}, {
				separator : true
			},{
			name : '修改',
			bclass : 'edit',
			onpress : action
				// 当点击调用方法
			}, {
			separator : true
			},{
			name : '删除',
			bclass : 'delete',
			onpress : action
				// 当点击调用方法
			}, {
			separator : true
			},{
				name : '导出',
				bclass : 'excel',
				onpress : action
					// 当点击调用方法
			}, {
			separator : true
			},{
				name : '打印',
				bclass : 'printer',
				onpress : action
					// 当点击调用方法
			}, {
			separator : true
			},{
			name : '设置每页显示条数',
			bclass : 'mysearch',
			onpress : action
				// 当点击调用方法
			}]
	});
	function action(com, grid) {
		switch (com) {

			case '添加' :
				open(basePath+"ea/fielddistr/ea_getAddPage.jspa?type=add"+"&category="+category+"&grType="+grType+"&fiveClear="+fiveClear);
				break;
			case '修改' :
				if(id==""){
					alert("请选择！");
					break;
				}
				open(basePath+"ea/fielddistr/ea_getAddPage.jspa?type=modify&fieldDistribution.fieldDistributionId="+id+"&category="+category+"&grType="+grType);
				break;
			case '删除' :
				if(id==""){
					alert("请选择！");
					break;
				}
				if(!confirm("确定删除"))
					break;
				$.ajax({
					url:basePath+"ea/fielddistr/sajax_ea_deleteFieldDistribution.jspa?fieldDistribution.fieldDistributionId="+id+"&category="+category+"&grType="+grType,
					type:"post",
					async : false,
					success:function(data){
						var member = eval("(" + data + ")");
						location.reload();
					},
					error:function(data){
						
					}
				});
				break;
			case '导出' :
				
				var obj=new Array();
				$(".dps").each(function(i,element){				
					obj[i]=new Array();
					for(var r=0;r<11;r++){
						obj[i][r]=$(this).find("span").eq(r).text();
					};
				}); 
				var json=arrayToJson(obj);
				window.location.href=basePath+"ea/fielddistr/ea_exportExcelTable.jspa?json="+json+"&category="+category+"&grType="+grType+"&fiveClear="+fiveClear;			
				break;
			case '打印' :
				open(basePath+"ea/fielddistr/ea_toPrint.jspa"+"?category="+category+"&grType="+grType);
				break;
			case '设置每页显示条数' :
				var pr=prompt("请输入0~20的正整数");
				if(pr>0&&pr<=20){
					window.location.href=basePath+"ea/fielddistr/ea_getHomePage.jspa?pageNumber="+pr+"&category="+category+"&grType="+grType+"&fiveClear="+fiveClear;
				}else{
					alert("您输入的有误，请重新输入");
				}
				break;
			
		};
	};
	$("#like").live("click",function(){
		window.location.href=basePath+"ea/fielddistr/ea_getHomePage.jspa?fieldDistribution.productCode="+$("#likeProductCode").val()+"&fieldDistribution.ppName="+$("#likePpName").val()+"&category="+category+"&grType="+grType+"&fiveClear="+fiveClear;
	});
	
	
	$("tr[id]").click(function(){
		$(this).find(".radio").attr("checked","checked");
		id=this.id;
	});
});
//将多维数组转换成JSon
function arrayToJson(o) {     
    var r = [];     
    if (typeof o == "string") return "\"" + o.replace(/([\'\"\\])/g, "\\$1").replace(/(\n)/g, "\\n").replace(/(\r)/g, "\\r").replace(/(\t)/g, "\\t") + "\"";     
    if (typeof o == "object") {     
    if (!o.sort) {     
    for (var i in o)     
    r.push(i + ":" + arrayToJson(o[i]));     
    if (!!document.all && !/^\n?function\s*toString\s*\{\n?\s*\[native code\]\n?\s*\}\n?\s*$/.test(o.toString)) {     
    r.push("toString:" + o.toString.toString());     
    }     
    r = "{" + r.join() + "}";     
    } else {     
    for (var i = 0; i < o.length; i++) {     
    r.push(arrayToJson(o[i]));     
    }     
    r = "[" + r.join() + "]";     
    }     
    return r;     
    }     
    return o.toString();     
}   