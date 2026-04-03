$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector

	$(".jqmreturn").click(function() {
		notoken = 0;
		$("#documentsjqModel").jqmHide();
		$("#previewjqModel").jqmHide();
		$("#journalNumAjax").attr("value", "");
		$("#taxDateAjax").attr("value", "");
		showDocument = false;
	});

	$('.flexme11').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : "验货管理",
				minheight : 80,
				buttons : [{
					name : '选择接收人',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},{
					name : '验货确定',
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
					name : '设置每页显示条数',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},{
					name : '打印预览',
					bclass : 'printer',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}]
			});

	function action(com, grid) {
		switch (com) {
			case '选择接收人':
				var xuanze = $("#Inspectform").find("input.JQuerypersonvalue[checked='true']");
				if (xuanze.length == 0) {
					alert("请选择！");
					return;
				}
				var strs = "";
				$("[name='financialgoodID']").each(function() {
					if ($(this).is(':checked')) {
						strs += $(this).val() + ",";
					}
				});
				str=strs;
				document.cstaffForms.reset();
				$("#jqModels").jqmShow();
				break;
			case '验货确定' :
				var xuanze = $("#Inspectform")
						.find("input.JQuerypersonvalue[checked='true']");
				if (xuanze.length == 0) {
					alert("请选择！");
					return;
				}
				
				if($("input#startu").val()==02){
					alert("历史数据不可操作！");
					return;
				}
				
				inspectmap = "";
				var tt = "";
				var zens="";
				$("table#tt").find("tr.xggoods").each(function() {
					if ($("input.JQuerypersonvalue", $(this)).attr("checked")) {
						var iq = $("input#isQualify", "tr#" + this.id).val();
						var qt = $("span#requantity", "tr#" + this.id).text();
						var zen=$("span#staffsName", "tr#" + this.id).text();
						if(zen==""){
							zens="kong";
						}else{
							if (iq != "" && !isNaN(iq) ) {
								if (parseInt(iq) > parseInt(qt)) {
									tt = "tt";
									return;
								}
								$("tr#" + this.id).find(':input').each(function() {
									$(this)
											.attr(
													"name",
													"goodsmap[" + select + "]."
															+ this.name);
								});
								select++;
							}else {
								tt = "ss";
							}
						}
					}
				});
				if(zens=="kong"){
					alert("请选择接收人");
					return;
				}
				if (tt == "ss") {
					alert("合格数量不能为空并且必须是数字");
					return;
				}
				if (tt == "tt") {
					alert("合格数量必须小于收货数量");
					return;
				}
				if (confirm("确定提交？")) {
				var uri = basePath + "/ea/purchase/ea_toUpdateIsQualify.jspa?";
				$("form#Inspectform").attr("target", "hidden").attr("action",
						uri);
				document.Inspectform.submit.click();
				token = 2;
				}
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "/ea/purchase/ea_getinspectList.jspa?search="
						+ search;
				numback(url);
				break;
			case '查询' :
				$("#jqModelSearch2").jqmShow();
				break;
			case '打印预览':
				var url =basePath
				+ "page/ea/main/finance/invoicing/inspect_frame.jsp?search="+search
				+ "&pageNumber="+pNumber+"&pageCount="+pageCount+"&one=one";
		         open(url);
				break;	
		}
	}
	
	// 这一行的单击事件
	$(".flexme11 tr[id]").click(function() {
				if (event.srcElement.value == undefined) {
					if ($("input.JQuerypersonvalue", $(this)).attr("checked")) {
						$("input.JQuerypersonvalue", $(this)).attr("checked",
								false);
					} else {
						financialbillID = this.id;
						$("input.JQuerypersonvalue", $(this)).attr("checked",
								true);
					}
				}
			});
	//选择接收人
	$("input.JQuerySubmits").click(function() {
		if ($("form .error").length) {
			return;
		}
		$("#cstaffForms")
				.attr("target", "hidden")
				.attr(
						"action",
						basePath
								+ "ea/purchase/ea_updatelist.jspa?pageNumber="
								+ pNumber + "&search=" + search+"&str="+str );
		document.cstaffForms.submit.click();
		token = 2;

	});
	// 接收人取消
	$("input.JQueryreturns").click(function() {
				$("#jqModels").jqmHide();
				re_load();
			});
	$("#tosearch").click(function() {// 查询
				$("#postSearchForm2")
						.attr(
								"action",
								basePath
										+ "ea/purchase/ea_toinspectSearch.jspa?pageNumber="
										+ pNumber);
				document.postSearchForm2.submit.click();
			});
});

function re_load() {
	document.location.href = basePath
			+ "/ea/purchase/ea_getinspectList.jspa?pageNumber=" + pNumber
			+ "&pageForm.pageNumber=" + $("#pageNumber").attr("value")
			+ "&search=" + search;
}

function fj(cID) {
	var statusfj = $("tr#" + cID).find("span#status").text();
	if (statusfj != '01') {
		alert("已经归档不能修改附件");
		return;
	}
	window.open(basePath
			+ "ea/uploadfile/ea_getListImage.jspa?loadFile.parmeterID=" + cID);
}
