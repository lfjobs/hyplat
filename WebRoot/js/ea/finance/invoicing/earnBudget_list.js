$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	//收入预算	
var title = "收入";
if(sztype=="z"){
  title = "支出";
}else if(sztype==""){
	title = "收支";
}
 if(type=="00"){
	$('.flexme11').flexigrid({ 
				height : 330,
				width : 'auto',
				minwidth : 30,
				title : title+"预算单管理",
				minheight : 80,
				buttons : [{
					name : '添加',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
					}, {
					// 设置分割线
					separator : true
				},{
					name : '修改',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
					}, {
					// 设置分割线
					separator : true
				},{
					name : '查看',
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
					name : '确认预算',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},{
					name : '查询',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '打印预览',
					bclass : 'printer',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},  {
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
 }
 //预算调整
 if(type=="01"){
	$('.flexme11').flexigrid({ 
				height : 330,
				width : 'auto',
				minwidth : 30,
				title : title+"预算调整管理",
				minheight : 80,
				buttons : [{
					name : '预算调整',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
					}, {
					// 设置分割线
					separator : true
				},{
					name : '查看',
					bclass : 'see',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},{
					name : '查询',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '打印预览',
					bclass : 'printer',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},  {
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
 }
 

	function action(com, grid) {
		switch (com) {
			case '添加' :
				var url = basePath
						+ "ea/earnbudget/ea_toGetBudgetBills.jspa?pageNumber="
						+ pNumber+"&type="+type+"&addType=add&sztype="+sztype;
				document.location.href = encodeURI(url);
				break;
			case '预算调整' :
				if (ebbID == "") {
					alert("请选择！");
					return;
				}
				document.location.href = basePath
						+ "/ea/earnbudget/ea_toEditEarnBudget.jspa?pageNumber="
						+ pNumber + "&earnBudgetBill.ebbID=" + ebbID+"&type="+type+"&sztype="+sztype;
				break;
			 case '修改' :
				if (ebbID == "") {
					alert("请选择！");
					return;
				}
				var billStatus = $("tr#"+ebbID).find("span#billStatus").text();
				/*var sname = $("tr#"+ebbID).find("span#staffName").text();
				if(sname!=accname){
					alert("责任人不是绑定人员,不能修改");
					return;
				}*/
				if(billStatus=="01"){
					alert("已确认预算不能修改");
					return;
				}
				document.location.href = basePath
						+ "/ea/earnbudget/ea_toEditEarnBudget.jspa?pageNumber="
						+ pNumber + "&earnBudgetBill.ebbID=" + ebbID+"&type="+type+"&sztype="+sztype+"&addType=edit";;
				break;
			case '删除' :
				if (ebbID == "") {
					alert("请选择！");
					return;
				}
				var billStatus = $("tr#"+ebbID).find("span#billStatus").text();
				/*var sname = $("tr#"+ebbID).find("span#staffName").text();
				if(sname!=accname){
					alert("责任人不是绑定人员,不能删除");
					return;
				}*/
				if(billStatus=="01"){
					alert("已确认预算不能删除");
					return;
				}
				$form = $("#EarnBudgetBillform");
				if (confirm("确定删除？")) {
					$form
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/earnbudget/ea_deteleEarnBudgetBill.jspa");
					$form.find("input#ebbIDs").val(ebbID);
					document.EarnBudgetBillform.submit.click();
					$("tr#" + ebbID).remove();
					ebbID = "";
					token = 2;
				}
				break;
				
			case '确认预算' :
			
				if (ebbID == "") {
					alert("请选择！");
					return;
				}
				
				var billStatus = $("tr#"+ebbID).find("span#billStatus").text();
				if(billStatus=="01"){
					alert("已提交确认");
					return;
				}
				$form = $("#EarnBudgetBillform");
				if (confirm("确定提交预算？")) {
					$form
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/earnbudget/ea_confirmBudget.jspa");
					$form.find("input#ebbIDs").val(ebbID);
					document.EarnBudgetBillform.submit.click();
					token = 2;
				}
				
				break;
			case '查看' :
				if (ebbID == "") {
					alert("请选择！");
					return;
				}
				document.location.href = basePath
						+ "/ea/earnbudget/ea_toEditEarnBudget.jspa?pageNumber="
						+ pNumber + "&earnBudgetBill.ebbID=" + ebbID+"&type="+type+"&sztype="+sztype+"&toSee=see";
				break;
			case '打印预览' :
				if (ebbID == "") {
					alert("请选择！");
					return;
				}
				window.open(basePath + "/ea/earnbudget/ea_toprintcsb.jspa?earnBudgetBill.ebbID="+ebbID+"&type="+type+"&sztype="+sztype);
				break;
			case '导出' :
				var url = basePath+ "ea/earnbudget/ea_showExcel.jspa?type="+type+"&search="+search+"&sztype="+sztype;
			    open(url);
				break;
		
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/earnbudget/ea_earnBudgetList.jspa?search="
						+ search+"&type="+type+"&sztype="+sztype;
				numback(url);
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
			
		}
	}
	$(".flexme11 tr[id]").click(function() {
				ebbID = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	
	$(".flexme11 tr[id]").dblclick(function() {
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				ebbID = this.id;
				action("查看");
			});
			
	$("#tosearch").click(function() {
		
	
		$("#SearchForm").attr(
				"action",
				basePath + "ea/earnbudget/ea_toSearch.jspa?pageNumber="
						+ pNumber);
		document.SearchForm.submit.click();
	});



});
function re_load() {
	var url = basePath
			+ "ea/earnbudget/ea_earnBudgetList.jspa?pageNumber="
			+ pNumber + "&pageForm.pageNumber="
			+ $("#pageNumber").attr("value")+"&type="+type+"&sztype="+sztype;
	document.location.href = encodeURI(url);
}

