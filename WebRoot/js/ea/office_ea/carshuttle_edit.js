$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	$('.JQueryflexme').flexigrid({
				height : 165,
				width : 'auto',
				minwidth : 30,
				title : '车辆接送信息管理',
				minheight : 80,
				buttons : [{
					name : '添加',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '修改',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '删除',
					bclass : 'delete',
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
				},  {
					name : '导出',
					bclass : 'excel',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '设置每页显示条数',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}]
			});
	function action(com, grid) {
		switch (com) {
			case '添加' :
				quasiID = "";
				$("input.JQuerypersonvalue").attr("checked", false);
				document.addseatForm.reset();
				$t = $("table#stafftable");
				$("#jqModel").jqmShow();
				break;
			case '修改' :
				if (quasiID == "") {
					alert('请选择!');
					return;
				}
				document.updateseatForm.reset();
				$t = $("table#stafftables");
				$p = $("tr#" + quasiID);
				$("#jqModelup").jqmShow();
				$p.find("span[id]").each(function() {
							$t.find("input[name]#" + this.id).val($(this)
									.text());
							var dd=$(this).attr("id");
							if(dd=="byWay"){
							$("#ss2").attr("value",$(this).text());
							}
							if(dd=="beganPlace"){
							$("#ss1").attr("value",$(this).text());	
							}
							$t.find("select[name]#" + this.id).val($(this)
									.text()).attr("selected","selected");
						});
				
				
				break;
			case '删除' :
				if (quasiID == '') {
					alert("请选择！");
					return;
				}
				$f = $('#carquasiForm');
				$f.find(':input#quasiID').val(quasiID);
				if (confirm("是否删除？")) {
					$("#carquasiForm").attr("target", "hidden").attr(
							"action",
							basePath + "ea/shuttle/ea_deleteCarquasis.jspa?pageNumber="+ pNumber  + "&carquasi.quasiID="+ quasiID);
					document.carquasiForm.submit.click();
					$("tr#" + quasiID).remove();
					quasiID = "";
					token = 11;
				}
				break;
			case '查询':
				$("#jqModelcarSearch").jqmShow();
				break;
			case '设置每页显示条数' :
				var url = basePath+ "ea/shuttle/ea_getCarQuasiList.jspa?search="+ search;
				numback(url);
				break;
			case '导出' :
				var url = basePath+ "ea/shuttle/ea_showCarseatExcels.jspa?";
				open(url);
				break;
		}
	}
	//单击事件
	$(".JQueryflexme tr[id]").click(function() {
		quasiID = this.id;
		if (personurl) {
			$("#mainframe").attr("src", personurl + quasiID);
		}
		$("input.JQuerypersonvalue", $(this)).attr("checked", "checked");
	});
	// 车辆添加取消	
	$("input.JQueryreturn").click(function() {
				$("#jqModel").jqmHide();
				re_load();
			});
	//车辆添加保存
	$("input.JQuerySubmit").click(function() {
				$(".notnull").trigger("blur");
				if ($("form .error").length) {
					return;
				}
				if($("#carNum",$("#addseatForm")).text() == ''){
					alert("请选择车辆！");
					return;
				}
				if (carID == "") {
					$("#addseatForm")
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/shuttle/ea_saveCarseat.jspa?pageNumber="
											+ pNumber + "&search=" + search);
					document.addseatForm.submit.click();
					document.addseatForm.reset();
					token = 2;
					return;
				}
			});
		// 车辆修改取消	
	$("input.JQueryreturns").click(function() {
				$("#jqModelup").jqmHide();
				re_load();
			});
	//选择车辆返回
	$(".JQueryreturngoods").click(function() {
				notoken = 0;
				var numes="";
				$("#carNum", $("table#searchgood")).attr("value",numes);
				$("#carFrameNum", $("table#searchgood")).attr("value",numes);
				$("#carType", $("table#searchgood")).attr("value",numes);
				$(":input#parms").attr("value",numes);
				$(".jqmWindow", $("#goodsForm")).jqmHide();
			});
	// 新增物品
	$(".xzwp").click(function() {
				window.open(basePath
						+ "ea/goodsmanage/ea_getListGoodsManage.jspa?");
			});
	//选择往来单位返回
	$(".JQueryreturnutil").click(function() {
				notoken = 0;
				$(".jqmWindow", $("#selectcompanyForm")).jqmHide();
			});
	// 修改保存
	$("input.JQuerySubmits").click(function() {
		if ($("form .error").length) {
			return;
		}
		$("#updateseatForm")
				.attr("target", "hidden")
				.attr(
						"action",
						basePath
								+ "ea/shuttle/ea_updateCarseat.jspa?pageNumber="
								+ pNumber + "&search=" + search+ "&carquasi.quasiID="+ quasiID);
		document.updateseatForm.submit.click();
		token = 2;
	});	
	//车辆查询
	$("#searchCar").click(function() {
		$("#carSearchForm").attr(
				"action",
				basePath + "/ea/shuttle/ea_toSearchCar.jspa?pageNumber="
						+ pNumber);
		document.getElementById("carSearchForm").submit();
		$("#carSearchTable").find(":input[name]").val("");
	});
	$(".JQueryflexme").find("select[id!=xxx]").each(function() {
				$s = $(this).hide();
				$o = $("<span/>").text($s.find("option:selected").text());
				$o.insertAfter($s);
			});	
});
function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/shuttle/ea_getCarQuasiList.jspa?pageNumber="
				+ pNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value");
}

/** **********************************选择车辆**************************************** */
$(document).ready(function() {
	var cuID = "";
	$("table#gotable tr[id]").live("click", function(event) {
				cuID = this.id;
				$("input.rauser", $(this)).attr("checked", "checked");
			});
	// 导入数据（选择物品）
		$("#newG").click(function() {
				cx("");
				$(".jqmWindow", $("#goodsForm")).jqmShow();
			});
	// 根据车牌号查询
	$("#chaxun").click(function() {
				var typeName = $("#carNum", $("table#searchgood")).val();
				var typeJia=$("#carFrameNum", $("table#searchgood")).val();
				var typeType=$("#carType", $("table#searchgood")).val();
				$(":input#parms").val("parameter=" + typeName+"&typeJia="+typeJia+"&typeType="+typeType);
				cx("parameter=" + typeName+"&typeJia="+typeJia+"&typeType="+typeType);
			});
	// 上一页
	$("#wpsy").click(function() {
				var sy = $("#wpsy").attr("title");
				if (sy != 0) {
					var typeName = $(":input#parms").val();
					var typeCN = typeName + "&pageForm.pageNumber=" + sy;
					cx(typeCN);
				} else {
					alert("已是首页！");
				}
			});
	// 下一页
	$("#wpxy").click(function() {
				var xy = $("#wpxy").attr("title");
				if (xy != 0) {
					var typeName = $(":input#parms").val();
					var typeCN = typeName + "&pageForm.pageNumber=" + xy;
					cx(typeCN);
				} else {
					alert("已是尾页！");
				}
			});	
		$("#qdcar").click(function() {
		if (cuID != "") {
			$("tr #" + cuID).children("td").each(function() {
				if (this.id == "contactUserID") {
					$("input#contactUserID", $("table#stafftable")).val($(this)
							.text());
				} else {
					$("span#" + this.id, $("table#stafftable"))
							.text($(this).text());
				}
			});
			cuID="";
			$(".jqmWindow", $("#goodsForm")).jqmHide();
		} else {
			alert("请选择车辆！");
		}
	});
	
	function cx(typeCN){
		if (notoken) {
			alert("正在获取数据！请稍等");
			return;
		}
		notoken = 1;
		$("#wpsy").attr("title", 0);
		$("#wpxy").attr("title", 0);
		$("#wpzy").attr("title", 0);
		var searchurl = basePath
				+ "ea/shuttle/sajax_ea_getcseatnull.jspa?";
		$.ajax({
			url : encodeURI(searchurl+ typeCN+ "&date="
					+ new Date().toLocaleString()),
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
					alert("没有数据");
					notoken = 0;
					return;
				}
				
				var dqy = pageForm.pageNumber;// 当前页
				var zys = pageForm.pageCount;// 总页数
				if (dqy > 1) {
					$("#wpsy").attr("title", dqy - 1);
				}
				if (dqy < zys) {
					$("#wpxy").attr("title", dqy + 1);
				}
				$("span#wpzycount").text(zys);
				var tabletr = "<table width='98%' height='26' align='center' cellspacing='0' cellpadding='1' style='font-size:12px;' class='bannb_01'><tr><td height='24' align='left' valign='top' class='txt01' >&nbsp;点击选择物品</td></tr></table>";
				tabletr += "<table width='99%' align='center' id='gotable' cellpadding='0' cellspacing='0' class='table'>";
				tabletr += " <tr><th height='21' align='center' bgcolor='#E4F1FA'>选择</th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'>车牌号</th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'>车型</th><th align='center' bgcolor='#E4F1FA'>车架号</th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'>准载人数</th></tr>";
				for (var i = 0; i < pageForm.list.length; i++) {
					tabletr += "<tr style='cursor: hand;' id = "
							+ pageForm.list[i].carID + ">";
					tabletr += "<td id='check' align='center'><input type ='radio' class='rauser' value="
							+ pageForm.list[i].carID + " name='check'/></td>";
					tabletr += "<td id='carNum' align='center'>"
							+ pageForm.list[i].carNum + "</td>";
					tabletr += "<td id='carType'  align='center'>"
							+ pageForm.list[i].carType + "</td>";
					tabletr += "<td id='carFrameNum'  align='center'>"
							+ pageForm.list[i].carFrameNum + "</td>";
					tabletr += "<td id='ratifyPeople' align='center'>"
							+ pageForm.list[i].ratifyPeople + "</td>";	
					tabletr += "<td id='contactUserID'  style='display:none' align='center'>"
							+ pageForm.list[i].carID + "</td>";
					tabletr += " </tr>";
				}
				tabletr += " </table>";
				$("#body_02").html(tabletr);
				$("#body_02").show();
				
				notoken = 0;
				window.status = "数据加载成功";
			},
			error : function cbf(data) {
				notoken = 0;
				alert("数据获取失败！");
			}
		});
	}

});

/** **********************************往来单位**************************************** */
$(document).ready(function() {
	var contactcID = "";// 已经添加到往来单位的ID
	$("table#gostable tr[id]").live("click", function(event) {
				contactcID = this.id;
				$("input.ra", $(this)).attr("checked", "checked");
			});
			
	// 选择往来单位
	$("#xzwlaw").click(function() {
				$("input#ccompanyID", $("table#searchcompany")).val("");
				$("select#contactConnections", $("table#searchcompany"))
						.val("全部");
				$(".jqmWindow", $("#selectcompanyForm")).jqmShow();
				cxwldw("contactCompany.companyName=&cconnection.contactConnections=");
			});
	// 新增往来单位
	$(".xzdw").click(function() {
		window.open(basePath
				+ "ea/contactcompany/ea_getListContactCompany.jspa?");
	});
	// 添加所选中的往来单位
	$("#qdcompany").click(function() {
		if (contactcID != "") {
			$("tr #" + contactcID).children("td").each(function() {
				if (this.id == "ccompanyID") {
					$("input#ccompanyID", $("table#stafftable"))
							.val($(this).text());

				} else {
					$("span#" + this.id, $("table#stafftable"))
							.text($(this).text());
				}
			});
			$(".jqmWindow", $("#selectcompanyForm")).jqmHide();
		} else {
			alert("请选择往来单位！");
		}
	});
	// 根据输入的往来单位名称查询
	$("input#searchcc").click(function() {
		contactcID = "";
		var typeName = $("input#ccompanyID", $("table#searchcompany")).val();
		var contactConnections = $("select#contactConnections",
				$("table#searchcompany")).val();
		quzhi=contactConnections;
		cxwldw("contactCompany.companyName=" + typeName
				+ "&cconnection.contactConnections=" + contactConnections);
	});
	var contactConnections = "";
	// 上一页
	$("#dwsy").click(function() {
		var sy = $("#dwsy").attr("title");
		
		if (sy != 0) {
			contactcID = "";
			var typeName = $("input#ccompanyID", $("table#searchcompany"))
					.val();
			if(quzhi != ""){
				contactConnections=quzhi;
			}else{
			   contactConnections = $("select#contactConnections",
					$("table#searchcompany")).val();
				}
			var typeCN = "contactCompany.companyName=" + typeName
					+ "&cconnection.contactConnections=" + contactConnections
					+ "&pageForm.pageNumber=" + sy;
			cxwldw(typeCN);
		} else {
			alert("已是首页！");
		}
	});
	// 下一页
	$("#dwxy").click(function() {
		var xy = $("#dwxy").attr("title");
		if (xy != 0) {
			contactcID = "";
			var typeName = $("input#ccompanyID", $("table#searchcompany"))
					.val();
			if(quzhi != ""){
				contactConnections=quzhi;
			}else{
			    contactConnections = $("select#contactConnections",
					$("table#searchcompany")).val();
				}
			var typeCN = "contactCompany.companyName=" + typeName
					+ "&cconnection.contactConnections=" + contactConnections
					+ "&pageForm.pageNumber=" + xy;
			cxwldw(typeCN);
		} else {
			alert("已是尾页！");
		}
	});
	// ajax查询往来单位列表
	function cxwldw(typeCN) {
		if (notoken) {
			alert("正在获取数据！请稍等");
			return;
		}
		notoken = 1;
		$("#dwsy").attr("title", 0);
		$("#dwxy").attr("title", 0);
		$("#dwzy").attr("title", 0);
		var searchurl = basePath
				+ "ea/contactcompany/sajax_ea_getListContactCompanyByCompanyName.jspa?";
		$.ajax({
			url : encodeURI(searchurl + typeCN + "&date="
					+ new Date().toLocaleString()),
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
				var connectionlist = member.connectionlist;
				if (pageForm == null) {
					alert("没有数据");
					notoken = 0;
					return;
				}
				var $se = $("select#contactConnections",
						$("table#searchcompany"));
				$se.empty();
				$select = "<option selected='selected' value = ''>--全部--</option>";
				$se.append($select);
				for (var i = 0; i < connectionlist.length; i++) {
					$op = $("<option />");
					$op.attr("value", connectionlist[i].codeValue)
							.text(connectionlist[i].codeValue);
					$se.append($op);
				}
				var dqy = pageForm.pageNumber;// 当前页
				var zys = pageForm.pageCount;// 总页数
				if (dqy > 1) {
					$("#dwsy").attr("title", dqy - 1);
				}
				if (dqy < zys) {
					$("#dwxy").attr("title", dqy + 1);
				}
				$("span#zycount").text(zys);
				var tabletr = "<table width='98%' height='26' align='center' cellspacing='0' cellpadding='1' style='font-size:12px;' class='bannb_01'><tr><td height='24' align='left' valign='top' class='txt01' >&nbsp;点击选择往来单位</td></tr></table>";
				tabletr += "<table width='99%' align='center' id='gostable' cellpadding='0'  cellspacing='0' class='table'>";
				tabletr += " <tr><th height='21' align='center' bgcolor='#E4F1FA'>选择</th><th align='center' bgcolor='#E4F1FA'>往来单位名称</th>";
				tabletr += " <th align='center' bgcolor='#E4F1FA'>往来关系</th>";
				tabletr += " <th align='center' bgcolor='#E4F1FA'>行业类别</th><th align='center' bgcolor='#E4F1FA'>单位电话</th>";
				tabletr += " <th align='center' bgcolor='#E4F1FA'>单位负责人</th><th align='center' bgcolor='#E4F1FA'>负责人电话</th>";
				tabletr += " <th align='center' bgcolor='#E4F1FA'>公司地址</th></tr>";
				for (var i = 0; i < pageForm.list.length; i++) {
					tabletr += "<tr style='cursor: hand;' title="
							+ pageForm.list[i].ccompanyID + " id = "
							+ pageForm.list[i].contactConnectionID + ">";
					tabletr += "<td id='checkcc' align='center'><input type ='radio'  class='ra' value="
							+ pageForm.list[i].contactConnectionID
							+ " name='checkradio'/></td>";
					tabletr += "<td id='ccompanyname' align='center'>"
							+ pageForm.list[i].companyName + "</td>";
					tabletr += "<td id='contactConnections' align='center'>"
							+ pageForm.list[i].contactConnections + "</td>";
					tabletr += "<td id='industryType' align='center'>"
							+ pageForm.list[i].industryType + "</td>";
					tabletr += "<td id='companyTel'  align='center'>"
							+ pageForm.list[i].companyTel + "</td>";
					tabletr += "<td id='cresponsible' align='center'>"
							+ pageForm.list[i].cresponsible + "</td>";
					tabletr += "<td id='responsibleTel' align='center'>"
							+ pageForm.list[i].responsibleTel + "</td>";
					tabletr += "<td id='companyAddr'  align='center'>"
							+ pageForm.list[i].companyAddr + "</td>";
					tabletr += "<td id='ccompanyID' style='display:none' align='center'>"
							+ pageForm.list[i].ccompanyID + "</td>";
					tabletr += " </tr>";
				}
				tabletr += " </table>";
				$("#body_02cc").html(tabletr);
				$("#body_02cc").show();
				alert("数据加载成功");
				notoken = 0;
				window.status = "数据加载成功";
			},
			error : function cbf(data) {
				notoken = 0;
				alert("数据获取失败！");
			}
		});
	}
});
