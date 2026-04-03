$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	var searchFormHtml=$("div#jqModelSearch").html();	
		$('.address').flexigrid({
			height : 120,
			width : 'auto',
			minwidth : 30,
			title : '个人注册管理'+searchFormHtml,
			minheight : 80,
			buttons :[{
				name : '禁用',
				bclass : 'edit',
				onpress : action
					// 当点击调用方法
				}, {
				separator : true
			},  {
				name : '设置每页显示条数',
				bclass : 'mysearch',
				onpress : action
					// 当点击调用方法
				}, {
				separator : true
			}]
		});  
		
	
	$("div.bDiv",$("div.flexigrid")).css("height","410px");	
	function action(com, grid) {
		switch (com) {	
			case '禁用' :
				if (staffID == "") {
					alert('请选择!');
					return;
				}
				window.open(basePath
						+ "/ea/wfjcustomer/ea_getwfjEcusInfo.jspa?");
				break;	
			
			case '设置每页显示条数' :
				var url = basePath
						+ "/ea/wfjcustomer/ea_getwfjEcusInfo.jspa?search="+search;
				numback(url);
				break;
			
		}      
		
	}
	$("table tr[id]").click(function() {
		        opertionID = this.id;
				productdesignID = this.id ;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	$("#tosearch").click(function() {
		$("form :input").trigger("blur");
		if ($("form .error").length) {
			return false;
		}                                
		$("#postSearchForm").attr(
				"action",
				basePath + "ea/wfjcustomer/ea_toSearch.jspa?identifier="+identifier+"&ghua="+ghua);
		
		$("#submit").click();
		
	});
	
	$(".address tr[id]").dblclick(function() {
		if(flexbutton == 'flexbutton'){
			parent.document.getElementById("isSubmit").click();
		}else{
			action('禁用');
		}
	});
	
	$(".close").click(function() {
				$("#jqModel").jqmHide();
				re_load();
			});
	$(".JQueryreturn").click(function() {
				$("#jqModel").jqmHide();
				re_load();
			});
	$("input#tosave").click(function() {
		var hidIdList=$("#hidIdList").val();
		$t = $("table#stafftable");
		if ($("input#goodsID", $t).attr("value") == "") {
			alert("请选择物品！");
			return;
		}
		$(".put3", $("#jqModel")).trigger("blur");
		if ($("#cstaffForm .error").length) {
			alert("请正确操作");
			notoken = 0;
			return;
		}
	});

	//删除
	
	$(".removeline").click(function(){
		$(this).parent().parent().remove();	
	});
	
});

function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/marketsurvey/ea_getListMarketSurvey.jspa?pageNumber="
				+ pNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value")+"&search="+search+"&sdate="+sdate+"&edate="+edate+"&identifier="+identifier;


}