$(function() {
	var staffID = "";

	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	$('.JQueryflexme').flexigrid({
				height : 340,
				width : 'auto',
				minwidth : 30,
				title : '月工资汇总列表 ',
				minheight : 80,
				buttons : [{
					name : '查看',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '查询',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},
				{
					name : '汇总打印',
					bclass : 'mysearch',
					onpress : action
					// 当点击调用方法
				}	, {
						separator : true
				}, {
						name : '单条打印',
						bclass : 'mysearch',
						onpress : action
						// 当点击调用方法
				}	, {
						separator : true
					}, {
						name : '设置每页显示条数',
						bclass : 'mysearch',
						onpress : action
						// 当点击调用方法
				}	, {
						separator : true
					}]
			});
	function action(com, grid) {
		switch (com) {
			case '查看' :
				if (staffID == "") {
					alert('请选择!');
					return;
				}
				document.cstaffForm.reset();
				$t = $("table#stafftable");
				$p = $("tr#" + staffID);
				$p.find("span[id]").each(function() {
							$t.find(":input[name]#" + this.id).val($(this)
									.text());
						});
				$("#jqModel").jqmShow();
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
			case '汇总打印' :
			 	url= pbasePath+ "ea/monthSalary/ea_printManthSalary.jspa?search=" 
			 		+ search + "&all=all";
			  	open(url);		
				break;
			case '单条打印' :
				url = pbasePath
						+ "page/ea/main/human/office/production/printWagesFrame.jsp?search=" 
				+ search + "&pageNumber="+ppageNumber+"&pageCount="+pageCount+"&one=one4";
				open(url);	
				break;
			case '设置每页显示条数' :
				var url = pbasePath
						+ "ea/monthSalary/ea_getMonthSalaryList.jspa?&search=" + search;
				numback(url);
				break;
		}
	}
	$(".menu00").click(function() {
				$(this).hide();
			});
	$(".JQueryflexme tr[id]").dblclick(function() {
				action('查看');// 当双击时出发 action方法.等价于先选中再点击修改按钮
			});
	$(".JQueryflexme tr[id]").click(function() {
				staffID = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	$("input.JQueryreturn").click(function() {// 取消
				$("#jqModel").jqmHide();

			});
	$("#tosearch").click(function() {
		$("#postSearchForm")
				.attr(
						"action",
						pbasePath
								+ "ea/monthSalary/ea_toSearch.jspa?pageNumber="
								+ ppageNumber);
		document.postSearchForm.submit.click();
	});
});