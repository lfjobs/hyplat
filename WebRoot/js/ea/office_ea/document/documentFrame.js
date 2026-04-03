$(document).ready(function() {  

	$("#navigation").treeview();   
	var frametitle = "";
	var arraymodule = new Array("doc", "contract", "cg", "dg", "pg", "jg",
			"finace", "complaint", "InduReg", "regime", "AnnNoti", "news",
			"InterDis", "ExterDis", "bulletin", "CountReg", "person","MeetRecord","renshi","bangongshi","finance","shengchan","yingxiao");
	var arraytitle = new Array("公文流转单流转", "企业合同流转", "公司规划流转", "部门规划流转",
			"个人规划流转", "职业规划流转", "财务报表流转", "投诉意见流转", "行业法规流转", "制度管理流转",
			"公告通知流转", "新闻流转", "内部纠纷流转", "外部纠纷流转", "简报流转", "国家法规流转", "人事报表流转","会议记录管理",
			"人事合同流转", "办公室合同流转", "财务合同流转", "生产合同流转", "营销合同流转");

	for (var i = 0; i < arraymodule.length; i++) {
		if (arraymodule[i] == module) {
			frametitle = arraytitle[i];
			break;
		}
	}
	$("#frametitle").text(frametitle);
	if(frametitle=="企业合同流转"){
    $("#tit").text("合同电子化传输");
	$("#unex").text("未审批合同");
	$("#ex").text("已审批合同");
	$("#unseal").text("未盖章合同");
	$("#seal").text("已盖章合同");
	$("#gl").text("合同管理");
	$("#unpub").text("未分发合同");
	$("#pub").text("已分发合同");
	$("#unread").text("未阅读合同");
	$("#readed").text("已阅读合同");
	$("#gd").text("合同归档");
	}
	var result1 = 0, result2 = 0, result3 = 0, result4 = 0;
	var result5 = 0, result6 = 0;

	// 查询草稿箱个数，收件箱个数(包括驳回和传阅)
	urlPath = basePath
			+ "ea/documentinfo/sajax_ea_getCountsByDraftList.jspa?data="
			+ Math.random();
	$.ajax({
				url : urlPath,
				type : "get",
				dataType : 'json',
				async : true,
				success : function scanback(data) {
					var member = eval("(" + data + ")");
					var counts = member.result;
					result1 = counts[0];
					result2 = counts[1];
					$("#navigation #draft").text("("+result1+")");
					$("#navigation #receivebox").text("("+result2+")");
				},
				error : function scanback(data) {
					alert("检查拟稿个数失败！");
				}
			});
	// 查询待审批，待盖章，待分发，待阅读个数
	var urlPath = basePath
			+ "ea/documentflow/sajax_n_ea_checkUnFinishedDocument.jspa?data="
			+ Math.random();
	$.ajax({
				url : urlPath,
				type : "get",
				dataType : 'json',
				async : true,
				data : {
					type : "examine"
				},
				success : function scanback(data) {
					var member = eval("(" + data + ")");
					result3 = member.result;
					$("#navigation #approvalno").text("("+result3+")");

				},
				error : function scanback(data) {
					alert("检查未审查公文失败！");
				}
			});

	urlPath = basePath
			+ "ea/documentflow/sajax_n_ea_checkUnFinishedDocument.jspa?data="
			+ Math.random();
	$.ajax({
				url : urlPath,
				type : "get",
				async : true,
				dataType : 'json',
				data : {
					type : "seal"
				},
				success : function scanback(data) {
					var member = eval("(" + data + ")");
					result4 = member.result;
					$("#navigation #stampno").text("("+result4+")");
				},
				error : function scanback(data) {
					alert("检查未盖章公文失败！");
				}
			});

	urlPath = basePath
			+ "ea/documentflow/sajax_n_ea_checkUnFinishedDocument.jspa?data="
			+ Math.random();
	$.ajax({
				url : urlPath,
				type : "get",
				dataType : 'json',
				async : true,
				data : {
					type : "publish"
				},
				success : function scanback(data) {
					var member = eval("(" + data + ")");
					result5 = member.result;
					$("#navigation #publish").text("("+result5+")");
				},
				error : function scanback(data) {
					alert("检查未发行公文失败！");
				}
			});
	urlPath = basePath
			+ "ea/documentflow/sajax_n_ea_checkUnFinishedDocument.jspa?data="
			+ Math.random();
	$.ajax({
				url : urlPath,
				type : "get",
				dataType : 'json',
				async : true,
				data : {
					type : "read"
				},
				success : function scanback(data) {
					var member = eval("(" + data + ")");
					result6 = member.result;
					    $("#navigation #read").text("("+result6+")");
				},
				error : function scanback(data) {
					alert("检查未阅读公文失败！");
				}
			});

	setInterval(function() {
				var FormObj = document.getElementById("mainframe").contentWindow;
				var total = FormObj.document.getElementById("totals");
				if (total != null) {
					total.innerText = Number(result1) + Number(result2)
							+ Number(result3) + Number(result4)
							+ Number(result5) + Number(result6);

				}

			}, 100);
   

});

function tonclick(id) {
	if (id == "draft") {
		$("#mainframe").attr(
				"src",
				basePath + "ea/documentinfo/ea_getDocDraftList.jspa?data="
						+ Math.random()+"&journalNum="+journalNum+"&projectName="+encodeURI(projectName));
	}
	if (id == "receivebox") {
		$("#mainframe").attr(
				"src",
				basePath + "ea/documentinfo/ea_getReceiveBoxList.jspa?searchType=pc&data="
						+ Math.random());
	}

	if (id == "yessenddraft") {
		$("#mainframe")
				.attr(
						"src",
						basePath
								+ "ea/documentinfo/ea_getSendedDocList.jspa?data="
								+ Math.random());
	}
	if (id == "approvalno") {
		$("#mainframe")
				.attr(
						"src",
						basePath
								+ "ea/documentflow/ea_getExamineDocList.jspa?finishType=examine&data="
								+ Math.random());

	}
	if (id == "aprrovalyes") {
		$("#mainframe")
				.attr(
						"src",
						basePath
								+ "ea/documentflow/ea_getExamineDocList.jspa?finishType=examineyes&data="
								+ Math.random());
	}
	if (id == "stampno") {
		$("#mainframe")
				.attr(
						"src",
						basePath
								+ "ea/documentflow/ea_getSealDocList.jspa?finishType=seal&data="
								+ Math.random());
	}
	if (id == "stampyes") {
		$("#mainframe")
				.attr(
						"src",
						basePath
								+ "ea/documentflow/ea_getSealDocList.jspa?finishType=sealyes&data="
								+ Math.random());
	}
	if (id == "nosenddoc") {
		$("#mainframe")
				.attr(
						"src",
						basePath
								+ "ea/documentflow/ea_getPublishDocList.jspa?finishType=publish&data="
								+ Math.random());
	}
	if (id == "yessenddoc") {
		$("#mainframe")
				.attr(
						"src",
						basePath
								+ "ea/documentflow/ea_getPublishDocList.jspa?finishType=publishyes&data="
								+ Math.random());
	}
	if (id == "readno") {
		$("#mainframe")
				.attr(
						"src",
						basePath
								+ "ea/documentflow/ea_getReadDocList.jspa?finishType=read&data="
								+ Math.random());
	}
	if (id== "readyes") {
		$("#mainframe")
				.attr(
						"src",
						basePath
								+ "ea/documentflow/ea_getReadDocList.jspa?finishType=readyes&data="
								+ Math.random());
	}
	if (id == "archive") {
		$("#mainframe")
				.attr(
						"src",
						basePath
								+ "ea/documentinfo/ea_getArchivedList.jspa?data="
								+ Math.random());
	}
	if (id == "share") {
		$("#mainframe").attr(
				"src",
				basePath + "ea/documentinfo/ea_getShareDocuments.jspa?data="
						+ Math.random());
	}
	if (id == "litter") {
		$("#mainframe").attr(
				"src",
				basePath + "ea/documentcommon/ea_getRecycelBinList.jspa?data="
						+ Math.random());
	}
	if (id == "template") {
		$("#mainframe")
				.attr(
						"src",
						basePath
								+ "ea/documenttemplate/ea_getDocTemplateList.jspa?data="
								+ Math.random());
	}

}
// 首页快速新建公文
function newBuildDoc() {
	$("#mainframe").attr(
			"src",
			basePath + "ea/documentinfo/ea_newDocument.jspa?type=draft&data="
					+ Math.random()+"&journalNum="+journalNum+"&projectName="+projectName);

}
