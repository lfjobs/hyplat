$(document).ready(function() {

	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close')// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
		var title="";
	if (type == "group") {
		$("tr.company").show();
		title="集团过期合同";
	} else {

		$("tr.company").hide();
		title="公司过期合同";
	}
	

	$('.wspdoc').flexigrid({
				height : 349,
				width : 'auto',
				minwidth : 30,
				title : title,
				minheight : 349,
				buttons : [{
					name : '查看',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '设置/取消提醒',
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
			case '查看' :

				var docId = "";
				var length = 0;
				$("input:checked[name=checkGroup]").each(function() {
							docId = $(this).val();
							length++;
						});
						
				if(length==0){
					alert("请选择");
					return;
				}
				if(length>1){
					alert("只能查看一个");
					return;
				}
				
				window.location.href = basePath
						+ "ea/documentinfo/ea_getViewDocument.jspa?docId="
						+ docId + "&type=toFinishedView&date=" + new Date();

				break;
			
			case '查询' :
				document.searchForms.reset();
				if (type == "group") {
					getCurrentAndSubCompany();
				} else {
					bmdepts("");
				}
				
				$("#jqModelSearch1").jqmShow();
				break;
            case '设置/取消提醒' :
				var docIds = "";
				var length = 0;
				$("input:checked[name=checkGroup]").each(function() {
							docIds+= $(this).val() + ",";
							length++;
						});

				if (length == 0) {
					alert("请选择");
					return;
				}
				if (confirm("确定继续？")) {
					var url = basePath
							+ "ea/documentsummary/sajax_ea_setTipOperate.jspa?date="
							+ new Date();
					$.ajax({
								url : url,
								type : "get",
								async : false,
								dataType : "json",
								data : {
									"document.docId" : docIds
 
								},
								success : function(data) {
									alert("操作成功!");
									document.location.reload();
								},
								error : function(data) {
									alert("设置/取消提醒失败");
								}

							});

				}

				

				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/documentsummary/ea_getSummaryDocList.jspa?search="
						+ search + "&type=" + type + "&overdue=over&date=" + new Date();
				numback(url);
				break;

		}
	}

	$(".search").click(function() {
		$("#searchForms").attr("target","_self").attr(
				"action",
				basePath + "ea/documentsummary/ea_toSearch.jspa?pageNumber="
						+ pNumber + "&date=" + new Date());
		document.searchForms.submit.click();
	});

	// 点击打印预览
	$("#topreview").click(function() {
		$("#searchForms").attr("target","_blank").attr(
				"action",
				basePath + "ea/documentsummary/ea_toPreview.jspa?pageNumber="
						+ pNumber + "&date=" + new Date());
		document.searchForms.submit.click();
		
	});
	
	

   //用于阻止复选框的冒泡行为；
	$("input.JQuerypersonvalue").click(function(event) {
				event.stopPropagation();

			});
	
	
	$(".wspdoc tr").toggle(function() {
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");

			}, function() {
				$("input.JQuerypersonvalue", $(this)).attr("checked", false);
			});
	$(".wspdoc tr").dblclick(function() {
				docId = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				action("查看");
			});
			
			


});
// 获取当前公司及其子公司
function getCurrentAndSubCompany() {
	var url = basePath
			+ "ea/documentsummary/sajax_ea_getCurrentAndSubCompany.jspa?date="
			+ new Date().toLocaleString();
	$.ajax({
				url : encodeURI(url),
				type : "post",
				async : true,
				dataType : "json",
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var companylist = member.companylist;
					var str = "<option value=''>请选择公司</option>";
					for (var i = 0; i < companylist.length; i++) {
						var obj = companylist[i];
						str += "<option title='" + obj.companyName
								+ "' value='" + obj.companyID + "'>"
								+ obj.companyName + "</option>";
					}
					$("#searchForms #companyID").html(str);

				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}
			});
}

function bmdepts(val) {
	$("#searchForms #organizationID").html("");
	var url = basePath
			+ "ea/documentcommon/sajax_ea_getAllOrganizations.jspa?date="
			+ new Date().toLocaleString();
	$.ajax({
				url : encodeURI(url),
				type : "post",
				async : true,
				dataType : "json",
				data:{
					companyID:val
				},
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var orgaizationlist = member.orgaizationlist;
					var str = "<option value=''>请选择部门</option>";
					for (var i = 0; i < orgaizationlist.length; i++) {
						var obj = orgaizationlist[i];
						str += "<option value='" + obj.organizationID + "'>"
								+ obj.organizationName + "</option>";
					}
					$("#searchForms #organizationID").html(str);

				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}
			});
}

// 获得人力资源或者在职员工等的弹出框的操作
function importGY2(url,type) { // 打开页面
	$("#daoRu").attr("src", basePath + url + "?date=" + new Date());
	$("#socialJqm").jqmShow();
	$("#socialJqm #type").val(type);
}

function DaoruConfirm() {// 选择确定
	var childopertionID = window.frames["daoRu"].opertionID;
	if (childopertionID == "") {
		alert("请选择")
		return;
	}
	var staffName = window.frames["daoRu"].$('tr#' + childopertionID)
			.find("span#staffName").text();

	var type = $("#socialJqm #type").val();
	var companyName = "";
	if (type == "partyAD" || type == "partyBD") {
		companyName = window.frames["daoRu"].$('tr#' + childopertionID)
				.find("span#companyName").text();
	}

	if (type == "partyA") {
		$("#searchForms #partyAstaffnames0").val(staffName);
		$("#searchForms #partyAstaff0").val(childopertionID);
	} else if (type == "partyB") {
		$("#searchForms #partyBstaffnames0").val(staffName);
		$("#searchForms #partyBstaff0").val(childopertionID);
	} else if (type == "partyAD") {
		$("#searchForms #partyAName0").val(companyName);
		$("#searchForms #partyA0").val(childopertionID);
	} else if (type == "partyBD") {
		$("#searchForms #partyBName0").val(companyName);
		$("#searchForms #partyB0").val(childopertionID);
	} else {
		$("#searchForms #socialNames").val(staffName);
		$("#searchForms #socialss").val(childopertionID);
	}
	
	$("#daoRu").attr("src", "");
	$("#socialJqm").jqmHide();
}
function cancelJqm() {
	$("#socialJqm").jqmHide();
}

function changeCompany(obj) {
	if ($(obj).val() != '') {
		bmdepts($(obj).val());
	} else {
		$("#searchForms #organizationID").html("<option value=''>请先选择公司</option>");
	}
}
