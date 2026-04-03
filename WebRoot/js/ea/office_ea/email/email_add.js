$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		});
	tree = new dhtmlXTreeObject("staffTree", "100%", "100%", 0);
	tree.enableDragAndDrop(false);
	tree.enableHighlighting(1);
	tree.enableCheckBoxes(0);
	tree.enableThreeStateCheckboxes(false);
//	tree.setSkin(basePath + "js/tree/dhx_skyblue");
	tree.setImagePath(basePath + "js/tree/codebase/imgs/");
	tree.loadXML(basePath + "js/tree/common/tree_b.xml");
//	tree.setXMLAutoLoading();
	// tree.insertNewChild("0", treePID, treePName, 0, "dept.gif", "dept.gif",
	// "dept.gif");
	var url1 = basePath
			+ "ea/email/sajax_ea_getAllCompanyByCurrent.jspa?datesete="
			+ new Date();
	$.ajax({
				url : url1,
				type : "post",
				async : true,
				dataType : "json",
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var companylist = member.companylist;
					for (var i = 0; i < companylist.length; i++) {
//						function f(companylist,i) {
						tree.insertNewChild(0, companylist[i].companyID,
								companylist[i].companyName, 0, "dept.gif",
								"dept.gif", "dept.gif");
//						}
//						  Concurrent.Thread.create(f, companylist,i);
					}

				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}
			});

	tree.setOnDblClickHandler(function() {

		var selectedid = tree.getSelectedItemId();// 点击公司获得公司ID
		var level = tree.getLevel(selectedid);
		if (level == 1) {
			if (tree.hasChildren(selectedid) != 0) {
				return;
			}
			var url2 = basePath
					+ "ea/email/sajax_n_ea_getAccountList.jspa?companyID="
					+ selectedid + "&date=" + new Date().toLocaleString();
			$.ajax({
				url : encodeURI(url2),
				type : "get",
				async : false,
				dataType : "json",
				success : function cbf(data) {

					/** **添加账号** */

					var member = eval("(" + data + ")");
					
						var accountList = member.accountList;
						if (null != accountList) {
							if (!tree.hasChildren(selectedid)) {
								for (var i = 0; i < accountList.length; i++) {
//                                  function f(accountList, selectedid,i) {
									tree
											.insertNewChild(
													selectedid,
													accountList[i].accountID,
													accountList[i].accountName
															+ "("
															+ accountList[i].accountEmail
															+ ")", 0,
													"dept_node.gif",
													"dept_node.gif",
													"dept_node.gif");
//									}
//					           Concurrent.Thread.create(f, accountList, selectedid,i);
								}
							}
						}
					
				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}
			});
		}
		if (level == 2) {
			var treename = tree.getItemText(selectedid);
			var use = tree.getUserData(selectedid, "status");
			var reciveID = $("#reciveID").val();
			var reciveName = $("#reciveName").val();
			if (use != "used") {
				tree.setUserData(selectedid, "status", "used");
				$("#reciveID").attr("value", reciveID + selectedid + ";");
				$("#reciveName").attr("value", reciveName + treename + ";");
			} else {
				tree.setUserData(selectedid, "status", "");
				reciveID = reciveID.replace(selectedid + ";", "");
				reciveName = reciveName.replace(treename + ";", "");
				$("#reciveID").attr("value", reciveID);
				$("#reciveName").attr("value", reciveName);
			}
		}

	}

	);

	displayContent();

});
// 获得回复或者转发时的值
// zhtype：z,h；代码转发还是回复；
function getEmailInfo(zhtype) {
	var url2 = basePath + "ea/email/sajax_n_ea_getEmaiInfo.jspa?date="
			+ new Date().toLocaleString();
	$.ajax({
				url : encodeURI(url2),
				type : "post",
				async : false,
				dataType : "json",
				data : {
					emailID : emailID
				},
				success : function cbf(data) {

					/** **获取title content recive attach** */

					//var member = eval("(" + data + ")");

					//var emailInfo = member.emailInfo;
					//var attachList = member.attachList;

				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}
			});
}

// 下载附件
function downLoadAttach(filePath) {
	window.open(basePath + "/servlets/render?filename=" + filePath);
}
// 根据回复，转发，写信 显示不同的东西；
function displayContent() {
	// var title = document.getElementById("title").value;
	if (zhType == "h") {

		$("input#title").val("回复：" + title);
	} else if (zhType == "z") {
		$("#reciveName").val("");
		$("#reciveID").val("");
		$("input#title").val("转发：" + title);
	} else {
		$("#reciveName").val("");
		$("#reciveID").val("");
		$("input#title").val("");
		$("#content").val("");
	}

}

function cancelButton(){
	$("#cstaffForm #reciveName").val("");
    $("#title").val("");
    $("#content").val("");
}
