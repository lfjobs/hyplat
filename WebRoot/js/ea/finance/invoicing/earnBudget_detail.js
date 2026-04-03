$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	//收入预算	
var title1 = "预算明细管理";
var title = "收入";
if(sztype=="z"){
  title = "支出";
}else if(sztype==""){
	title = "收支";
}

if(type=="01"){
	title1 = "预算调整明细管理";
}

	$('.flexme11').flexigrid({ 
				height : 330,
				width : 'auto',
				minwidth : 30,
				title : title+title1,
				minheight : 80,
				buttons : [{
					name : '查询',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '导出',
					bclass : 'excel',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},{
					name : '设置每页显示条数',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}]
			});



	function action(com, grid) {
		switch (com) {
	
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
			
			case '导出' :
				var url = basePath+ "ea/earnbudget/ea_showExcel.jspa?type="+type+"&search="+search+"&sztype="+sztype+"&excel=detail";
			    open(url);
				break;
		
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/earnbudget/ea_getEarnBudgetDetails.jspa?search="
						+ search+"&type="+type+"&sztype="+sztype;
				numback(url);
				break;
	
			
		}
	}
	$(".flexme11 tr[id]").click(function() {
				ebbID = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	
			
	$("#tosearch").click(function() {
		
	
		$("#SearchForm").attr(
				"action",
				basePath + "ea/earnbudget/ea_toSearchByDetail.jspa?pageNumber="
						+ pNumber);
		document.SearchForm.submit.click();
	});



});
function re_load() {
	var url = basePath
			+ "ea/earnbudget/ea_getEarnBudgetDetails.jspa?pageNumber="
			+ pNumber + "&pageForm.pageNumber="
			+ $("#pageNumber").attr("value")+"&type="+type+"&sztype="+sztype;
	document.location.href = encodeURI(url);
}

