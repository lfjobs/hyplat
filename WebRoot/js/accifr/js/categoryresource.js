$(document).ready(function() {
	document.getElementById("aadTree").style.height = $("#tdheight").height() +"px";
	tree = new dhtmlXTreeObject("aadTree", "100%", "100%", 0);
	tree.enableDragAndDrop(false);
	tree.enableHighlighting(1);
	tree.enableCheckBoxes(0);
	tree.enableThreeStateCheckboxes(false);
	tree.setSkin(basePath + 'js/tree/dhx_skyblue');
	tree.setImagePath(basePath + "js/tree/codebase/imgs/");
	tree.loadXML(basePath + "js/tree/common/tree_b.xml");
	tree.insertNewChild("0", pID, pName, 0, 0, 0, 0);
	$.ajaxSetup({async:false});
	treeload();
	tree.setOnClickHandler(function() {
				treeid = tree.getSelectedItemId();				
				if(treeid.substring(0,4) == "00no" || treeid.substring(1,6) == "scode"){
					tree.deleteChildItems(treeid);
					var url = basePath + "ea/accessresource/sajax_ea_getTreebody.jspa?stuts=" + stuts + "&selectedID=" + treeid +
					"&date="+new Date().toLocaleString();
					$.ajax({
						 url: encodeURI(url),
							type: "get",
							async: true,
							dataType: "json",
							success: function cbf(data){
									var member = eval("(" + data + ")");
									var ccodeList = member.ccodeList;
									if (null == ccodeList) {
										return;
									}
									for (var i = 0; i < ccodeList.length; i++) {
										tree.insertNewChild(treeid,ccodeList[i].codeSn +ccodeList[i].codeID,"("+
												 ccodeList[i].codeSn +")"+ccodeList[i].codeValue,0, 0, 0, 0);
									}
							},
						error: function cbf(data){
							alert("错误");
						}
					});
				}
				getAccess("");
				
	});
	// 关闭
	$("#close",window.parent.document).click(function() {
		$("#accift",window.parent.document).hide();
	});
	
	//行选中事件
	$("table#gouserstable tr[id]").live("click", function(event) {
		if(stuts == "04"){
			var b = $("input.rauser", $(this)).attr("checked");
			$("input.rauser", $(this)).attr("checked", !b);
			pareID = "checkbox";
			$("input#pareID").attr("value",pareID);
		}else{
			$("input.rauser", $(this)).attr("checked", "checked");
			pareID = this.id;
			$("input#pareID").attr("value",pareID);
		}
	});
	
	// >>按钮事件
	$("#addItem",window.parent.document).click(function(){
		treeid = tree.getSelectedItemId();
		treetext = tree.getSelectedItemText();		
		if (!isExist(treeid) && treeid!="" && treetext!="行业分类"){
			$("#selRight",window.parent.document).append("<option value='"+treeid.substring(treeid.indexOf("scode"))+"'>"+treetext+"</option>");
		}
	});
	// <<按钮事件
	$("#delItem",window.parent.document).click(function(){
		$("#selRight option:selected",window.parent.document).remove();
	});
	
	$("#confirmBtn",window.parent.document).click(function(){
		if($("#selRight option",window.parent.document).size()>0){
			$("#prdCategory",window.parent.document).find("option").remove();
		}
		var res = "";		
		$("#selRight option",window.parent.document).each(function(){			
			res += $(this).val() + "," + $(this).text() +";";
			$("#prdCategory",window.parent.document).append("<option value=\"" + $(this).val() + "\">" + $(this).text() + "</option>");
		});		
		$("#prdCategory",window.parent.document).find("option").attr("selected",true);
	  	//隐藏弹出层
	  	$("#category",window.parent.document).hide();
		
	});
	
});

//判断是否存在了key=treeid的option
function isExist(treeid){	
	return $("#selRight option[value='"+treeid+"']",window.parent.document).length > 0;		
}
//加载分类树
function treeload(){
	treename = tree.getItemText(treeid);
	parentid = tree.getParentId(treeid);
	parentname = tree.getItemText(parentid);
	tree.deleteChildItems(treeid);
	var url = basePath + "ea/accessresource/sajax_ea_getTreebody.jspa?stuts=" + stuts +
			"&date="+new Date().toLocaleString();
		$.ajax({
			 url: encodeURI(url),
				type: "get",
				async: true,
				dataType: "json",
				success: function cbf(data){
						var member = eval("(" + data + ")");
						var ccodeList = member.ccodeList;
						if (null == ccodeList) {
							return;
						}
						for (var i = 0; i < ccodeList.length; i++) {
							if(stuts == "04" ){
								if(ccodeList[i].codeStatus == "00"){
									tree.insertNewChild(treeid,"00no"+ccodeList[i].codeID,"("+
										 ccodeList[i].codeSn +")"+ccodeList[i].codeValue,0, 0, 0, 0);
								}else{
									tree.insertNewChild(treeid,ccodeList[i].codeValue,"("+
											 ccodeList[i].codeSn +")"+ccodeList[i].codeValue,0, 0, 0, 0);
								}
							}else if(stuts == "01"){
								if(ccodeList[i].codeStatus == "00"){
									tree.insertNewChild(treeid,ccodeList[i].codeSn + ccodeList[i].codeID,"("+
										 ccodeList[i].codeSn +")"+ccodeList[i].codeValue,0, 0, 0, 0);
									
								}else{
									tree.insertNewChild(treeid,ccodeList[i].codeValue,"("+
											 ccodeList[i].codeSn +")"+ccodeList[i].codeValue,0, 0, 0, 0);
								}
							}else{
								tree.insertNewChild(treeid,ccodeList[i].codeID,ccodeList[i].codeValue,0, 0, 0, 0);
							}
						}
						getAccess("");
				},
			error: function cbf(data){
				alert("错误");
			}
		});
}
//查询方法
function getAccess(e){
	$("#grsy",window.parent.document).attr("title", 0);
	$("#grxy",window.parent.document).attr("title", 0);
	$("#grzy",window.parent.document).attr("title", 0);
	var url = basePath + stutsurl + treeid+e;
	if(stuts == "01"){
		$.ajax({
			url : encodeURI(url + "&date=" + new Date().toLocaleString()),
			type : "get",
			async : true,
			dataType : "json",
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				var nologin = member.nologin;
				if (nologin) {
					document.location.href = basePath + "page/ea/not_login.jsp";
				}

				var pageForm = member.pageForm;
				if (pageForm == null) {
					var tabletr = "没有查询到数据！";
					$("#body_02cu").html(tabletr);
					notoken = 0;
					return;
				}
				var dqy = pageForm.pageNumber;// 当前页
				var zys = pageForm.pageCount;// 总页数
				if (dqy > 1) {
					$("#grsy").attr("title", dqy - 1);
				}
				if (dqy < zys) {
					$("#grxy").attr("title", dqy + 1);
				}
				$("span#grzycount").text(zys);
				$("span#grzydqy").text(dqy);
				var tabletr = "<table width='100%' align='center' id='gouserstable' cellpadding='0'  cellspacing='0' class='table'>";
				tabletr += " <tr><th height='30'width='30px' align='center' bgcolor='#E4F1FA'>选择</th>" +
						"<th align='center' bgcolor='#E4F1FA'>往来单位名称</th>" +
						"<th align='center' bgcolor='#E4F1FA'>往来关系</th>" +
						"<th align='center' bgcolor='#E4F1FA'>行业类别</th>" +
						"<th align='center' bgcolor='#E4F1FA'>单位电话</th>" +
						"</tr>";
				for (var i = 0; i < pageForm.list.length; i++) {
					tabletr += "<tr style='cursor: hand;' id = "
							+ pageForm.list[i][0] + " title= "
							+ pageForm.list[i][0] + " class='trclick'>";
					tabletr += "<td id='contactConnectionID' height='28' align='center'><input type ='radio'  class='rauser' value="
							+ pageForm.list[i][0]
							+ " name='checkradio'/></td>";
					tabletr += "<td id='companyName' align='center'>"
							+ pageForm.list[i][1] + "</td>";
					tabletr += "<td id='contactConnections' align='center'>"
							+ pageForm.list[i][2] + "</td>";
					tabletr += "<td id='industryType' align='center'>"
							+ pageForm.list[i][3] + "</td>";
					tabletr += "<td id='companyTel' align='center'>"
							+ pageForm.list[i][4] + "</td>";
					
					tabletr += "<td id='all' style='display:none' align='center'>"
						+ pageForm.list[i] + "</td>";
					tabletr += " </tr>";
				}
				tabletr += " </table>";
				$("#body_02cu").html(tabletr);
//				$("#body_02cu").show();
			},
			error : function cbf(data) {
				notoken = 0;
				alert("数据获取失败！");
			}
		});
	}else if(stuts == "02"){
		$.ajax({
			url : encodeURI(url + "&date=" + new Date()),
			type : "get",
			async : true,
			dataType : "json",
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				var nologin = member.nologin;
				if (nologin) {
					document.location.href = basePath + "page/ea/not_login.jsp";
				}

				var pageForm = member.pageForm;
				if (pageForm == null) {
					var tabletr = "没有查询到数据！";
					$("#body_02cu").html(tabletr);
					notoken = 0;
					return;
				}
				var dqy = pageForm.pageNumber;// 当前页
				var zys = pageForm.pageCount;// 总页数
				if (dqy > 1) {
					$("#grsy",window.parent.document).attr("title", dqy - 1);
				}
				if (dqy < zys) {
					$("#grxy",window.parent.document).attr("title", dqy + 1);
				}
				$("span#grzycount",window.parent.document).text(zys);
				$("span#grzydqy",window.parent.document).text(dqy);
				var tabletr = "<table width='100%' height=\"100%\" align='center' id='gouserstable' cellpadding='0'  cellspacing='0' class='table'>";
				tabletr += " <tr><th height='30' height=\"9%\" align='center' bgcolor='#E4F1FA'>选择</th><th align='center' bgcolor='#E4F1FA'>人员编号</th><th align='center' bgcolor='#E4F1FA'>姓名</th>"
						+ "<th align='center' bgcolor='#E4F1FA'>电话</th><th align='center' bgcolor='#E4F1FA'>身份证</th></tr>";
				for (var i = 0; i < pageForm.list.length; i++) {
					tabletr += "<tr style='cursor: hand;' height=\"9%\" id = "
							+ pageForm.list[i][0] + " title= "
							+ pageForm.list[i][2] + " class='trclick'>";
					tabletr += "<td id='staffid' height='28' align='center'><input type ='radio'  class='rauser' value="
							+ pageForm.list[i][0]
							+ " name='checkradio'/></td>";
					tabletr += "<td id='staffCode' align='center'>"
							+ pageForm.list[i][1] + "</td>";
					tabletr += "<td id='staffname' align='center'>"
							+ pageForm.list[i][2] + "</td>";
					tabletr += "<td id='reference' align='center'>"
							+ pageForm.list[i][4] + "</td>";
					tabletr += "<td id='staffidentitycard' align='center'>"
							+ pageForm.list[i][3] + "</td>";
					tabletr += "<td id='all' style='display:none' align='center'>"
						+ pageForm.list[i] + "</td>";
					tabletr += " </tr>";
				}
				for(var j = 0 ;j < 10 - pageForm.list.length ; j++){
					tabletr += " <tr><td height=\"9%\">&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>";
				}
				tabletr += " </table>";
				$("#body_02cu").html(tabletr);
			},
			error : function cbf(data) {
				notoken = 0;
				alert("数据获取失败！");
			}
		});
	}else if(stuts == "03"){ 
		$.ajax({
			url : encodeURI(url + "&date=" + new Date().toLocaleString()),
			type : "get",
			async : true,
			dataType : "json",
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				var nologin = member.nologin;
				if (nologin) {
					document.location.href = basePath + "page/ea/not_login.jsp";
				}

				var pageForm = member.pageForm;
				if (pageForm == null) {
					var tabletr = "没有查询到数据！";
					$("#body_02cu").html(tabletr);
					notoken = 0;
					return;
				}
				var dqy = pageForm.pageNumber;// 当前页
				var zys = pageForm.pageCount;// 总页数
				if (dqy > 1) {
					$("#grsy").attr("title", dqy - 1);
				}
				if (dqy < zys) {
					$("#grxy").attr("title", dqy + 1);
				}
				$("span#grzycount").text(zys);
				$("span#grzydqy").text(dqy);
//				var tabletr = "<div class='darg'>&nbsp;点击选择往来个人</div>";
				var tabletr = "<table width='100%' align='center' id='gouserstable' cellpadding='0'  cellspacing='0' class='table'>";
				tabletr += " <tr><th height='30' align='center' bgcolor='#E4F1FA'>选择</th>" +
						"<th align='center' bgcolor='#E4F1FA'>编号</th>"
						+ "<th align='center' bgcolor='#E4F1FA'>姓名</th>" +
								"<th align='center' bgcolor='#E4F1FA'>部门</th>" +
								"<th align='center' bgcolor='#E4F1FA'>职务</th></tr>";
				for (var i = 0; i < pageForm.list.length; i++) {
					tabletr += "<tr style='cursor: hand;' id = "
							+ pageForm.list[i][0] + " title= "
							+ pageForm.list[i][0] + " class='trclick'>";
					tabletr += "<td id='staffid' height='28' align='center'><input type ='radio'  class='rauser' value="
							+ pageForm.list[i][0]
							+ " name='checkradio'/></td>";
					tabletr += "<td id='staffname' align='center'>"
							+ pageForm.list[i][1] + "</td>";
					tabletr += "<td id='organizationname' align='center'>"
							+ pageForm.list[i][2] + "</td>";
					tabletr += "<td id='postname' align='center'>"
							+ pageForm.list[i][5] + "</td>";
					tabletr += "<td id='staffidentitycard' align='center'>"
							+ pageForm.list[i][6] + "</td>";
					tabletr += "<td id='all' style='display:none' align='center'>"
						+ pageForm.list[i] + "</td>";
					tabletr += " </tr>";
				}
				tabletr += " </table>";
				$("#body_02cu").html(tabletr);
			},
			error : function cbf(data) {
				notoken = 0;
				alert("数据获取失败！");
			}
		});
	}else if(stuts == "04"){
		//物品
		$.ajax({
			url : encodeURI(url + "&date=" + new Date().toLocaleString()),
			type : "get",
			async : true,
			dataType : "json",
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				var nologin = member.nologin;
				if (nologin) {
					document.location.href = basePath + "page/ea/not_login.jsp";
				}
				var pageForm = member.pageForm;
				if (pageForm == null) {
					var tabletr = "没有查询到数据！";
					$("#body_02cu").html(tabletr);
					notoken = 0;
					return;
				}
				var dqy = pageForm.pageNumber;// 当前页
				var zys = pageForm.pageCount;// 总页数
				if (dqy > 1) {
					$("#grsy").attr("title", dqy - 1);
				}
				if (dqy < zys) {
					$("#grxy").attr("title", dqy + 1);
				}
				$("span#grzycount").text(zys);
				$("span#grzydqy").text(dqy);
//				var tabletr = "<div class='darg'>&nbsp;点击选择往来个人</div>";
				var tabletr = "<table width='100%' align='center' id='gouserstable' cellpadding='0'  cellspacing='0' class='table'>";
				tabletr += " <tr><th height='30' align='center' bgcolor='#E4F1FA'>选择</th>" +
						"<th align='center' bgcolor='#E4F1FA'>编号</th>"
						+ "<th align='center' bgcolor='#E4F1FA'>姓名</th>" +
								"<th align='center' bgcolor='#E4F1FA'>数量</th>" +
								"<th align='center' bgcolor='#E4F1FA'>单位</th></tr>";
				for (var i = 0; i < pageForm.list.length; i++) {
					tabletr += "<tr style='cursor: hand;' id = "
							+ pageForm.list[i][0] + " title= "
							+ pageForm.list[i][0] + " class='trclick'>";
					tabletr += "<td id='goodsID' height='28' align='center'><input type ='checkbox' class='rauser' value="
							+ pageForm.list[i][0]
							+ "  name='check'/></td>";
					tabletr += "<td id='goodsCoding' align='center'>"
							+ pageForm.list[i][1] + "</td>";
					tabletr += "<td id='goodsName' align='center'>"
							+ pageForm.list[i][2] + "</td>";
					tabletr += "<td id='num' align='center'>"
							+ pageForm.list[i][4] + "</td>";
					tabletr += "<td id='variableID' align='center'>"
							+ pageForm.list[i][5] + "</td>";
					tabletr += "<td id='all' style='display:none' align='center'>"
						+ pageForm.list[i] + "</td>";
					tabletr += " </tr>";
				}
				tabletr += " </table>";
				$("#body_02cu").html(tabletr);
			},
			error : function cbf(data) {
				notoken = 0;
				alert("数据获取失败！");
			}
		});
	}	
}