$(document).ready(function() {

	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close')// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	if (type == "group") {
		$("tr.company").show();
	} else {

		$("tr.company").hide();
	}
	var typetitle = "";
	var frametitle = "";
	if (type == "group") {
		typetitle = "集团——"
	} else {
		typetitle = "公司——"
	}

	var arraymodule = new Array("doc", "contract", "cg", "dg", "pg", "jg",
			"finace", "complaint", "InduReg", "regime", "AnnNoti", "news",
			"InterDis", "ExterDis", "bulletin", "CountReg", "person","MeetRecord");
	var arraytitle = new Array("公文流转单汇总", "企业合同汇总", "公司规划汇总", "部门规划汇总",
			"个人规划汇总", "职业规划汇总", "财务报表汇总", "投诉意见汇总", "行业法规汇总", "制度管理汇总",
			"公告通知汇总", "新闻汇总", "内部纠纷汇总", "外部纠纷汇总", "简报汇总", "国家法规汇总", "人事报表汇总","会议记录汇总");

	for (var i = 0; i < arraymodule.length; i++) {
		if (arraymodule[i] == module) {
			frametitle = arraytitle[i];
			break;
		}
	}
	if(frametitle=="企业合同汇总"){
		$(".trtip1").show();
	}else{
		$(".trtip1").hide();
	}

	$('.wspdoc').flexigrid({
				height : 349,
				width : 'auto',
				minwidth : 30,
				title : typetitle + frametitle,
				minheight : 349,
				buttons : [{
					name : '查看',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '打印',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '查询',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, module=="contract"?({
					separator : true
				}, {
					name : '已过期合同',
					bclass : 'excel',
					onpress : action
						// 当点击调用方法
					}):({
					separator : true
				}),{
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

				if (docId == "") {
					alert('请选择!');
					return;
				}
				OpenOffice(docId,1,"查看");

				break;
			case '打印' :
				document.searchForms.reset();
				getCurrentAndSubCompany();
				bmdepts();
				$(".search").hide();
				$(".preview").show();
				
				
				$("#searchForms #partyAstaff0").val("");
				$("#searchForms #partyAstaffnames0").val("");

				$("#searchForms #partyAName0").val("");
				$("#searchForms #partyA0").val("");
				$("#searchForms #partyAstaffname0").val("");
				
				
				$("#searchForms #partyBstaff0").val("");
				$("#searchForms #partyBstaffnames0").val("");

				$("#searchForms #partyBName0").val("");
				$("#searchForms #partyB0").val("");
				$("#searchForms #partyBstaffname0").val("");
				$("#jqModelSearch1").jqmShow();
				break;
			case '查询' :
				document.searchForms.reset();
				if (type == "group") {
					getCurrentAndSubCompany();
				} else {
					bmdepts("");
				}
				
				$("#searchForms #partyAstaff0").val("");
				$("#searchForms #partyAstaffnames0").val("");

				$("#searchForms #partyAName0").val("");
				$("#searchForms #partyA0").val("");
				$("#searchForms #partyAstaffname0").val("");
				
				
				$("#searchForms #partyBstaff0").val("");
				$("#searchForms #partyBstaffnames0").val("");

				$("#searchForms #partyBName0").val("");
				$("#searchForms #partyB0").val("");
				$("#searchForms #partyBstaffname0").val("");
				
				$(".search").show();
				$(".preview").hide();
				$("#jqModelSearch1").jqmShow();
				break;
            case '已过期合同' :

				window.location.href = basePath
						+ "ea/documentsummary/ea_getSummaryDocList.jspa?type="+type+"&overdue=over&date=" + new Date();

				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/documentsummary/ea_getSummaryDocList.jspa?search="
						+ search + "&type=" + type + "&date=" + new Date();
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
	$(".wspdoc tr").click(function() {
				docId = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
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
