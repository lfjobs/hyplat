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
				title : '员工工资列表     <<工资时间段：' + sdate + "-------------" + edate
						+ ">>",
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
				}, { 
					name: '导出', 
					bclass: 'excel', 
					onpress: action
						//当点击调用方法 
				}, { 
					separator: true
				}, {
						name : '汇总打印',
						bclass : 'mysearch',
						onpress : action
						// 当点击调用方法
				}, {
							separator : true
						}/*, {
							name : '工资表打印',
							bclass : 'mysearch',
							onpress : action
							// 当点击调用方法
					}	, {
							separator : true
						}, {
							name : '培训补助费打印',
							bclass : 'mysearch',
							onpress : action
							// 当点击调用方法
					}	, {
							separator : true
						}*/, {
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
			
			case '导出': 
				url =
				pbasePath+"ea/logbooksummary/ea_showIntegralExcel.jspa?staffName="+pstaffName+"&sdate="+sdate+"&edate="+edate+"&search="+search+"&all=all";
				open(url); 
				break;
			 
			case '汇总打印' :
			  /*
 url = pbasePath
						+ "page/ea/main/human/office/production/printWagesFrame.jsp?staffName="
						+ pstaffName + "&sdate=" + sdate + "&edate=" + edate
						+ "&search=" + search+"&staffcategoryid="+staffcategoryid+"&arg="+arg+"&pageNumber="+ppageNumber+"&pageCount="+pageCount+"&one=one0";
				open(url);	
*/		
			 url=pbasePath+ "ea/logbooksummary/ea_printWages.jspa?staffName="
							  + pstaffName + "&sdate=" + sdate + "&edate=" + edate
							  + "&search=" + search+"&staffcategoryid="+staffcategoryid+"&arg="+arg+"&all=all";
			  open(url);		
				break;
		/*	case '工资表打印' :
				url = pbasePath
						+ "ea/logbooksummary/ea_printWages.jspa?staffName="
						+ pstaffName + "&sdate=" + sdate + "&edate=" + edate
						+ "&search=" + search + "&logoStatusVar=01";
				open(url);
				break;
			case '培训补助费打印' :
				url = pbasePath
						+ "ea/logbooksummary/ea_printWages.jspa?staffName="
						+ pstaffName + "&sdate=" + sdate + "&edate=" + edate
						+ "&search=" + search + "&logoStatusVar=00";
				open(url);
				break;*/
			case '单条打印' :
				url = pbasePath
						+ "page/ea/main/human/office/production/printWagesFrame.jsp?staffName="
						+ pstaffName + "&sdate=" + sdate + "&edate=" + edate
						+ "&search=" + search+"&staffcategoryid="+staffcategoryid+"&arg="+arg+"&pageNumber="+ppageNumber+"&pageCount="+pageCount+"&one=one";
				open(url);	
				break;
			case '设置每页显示条数' :
				var url = pbasePath
						+ "ea/logbooksummary/ea_getListLogBookIntegral.jspa?staffName="
						+ pstaffName + "&sdate=" + sdate + "&edate=" + edate
						+ "&search=" + search+"&staffcategoryid="+staffcategoryid+"&arg="+arg;
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
		$(".put3").trigger("blur");
		if ($("#postSearchForm .error").length) {
			alert("请填完所有必填项");
			return;
		}
		$("#postSearchForm")
				.attr(
						"action",
						pbasePath
								+ "ea/logbooksummary/ea_getListLogBookIntegral.jspa?pageNumber="
								+ ppageNumber);
		document.postSearchForm.submit.click();
	});
});