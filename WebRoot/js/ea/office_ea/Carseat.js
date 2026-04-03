$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	var title="";
	if(type=="c"){
		title="车辆准载座位管理公司汇总"
	}else if(type=="g"){
		title="车辆准载座位管理集团汇总"
	}else{
		title="车辆准载座位管理";
	}	
if(carID!=undefined&&carID!=""){
	$('.JQueryflexme').flexigrid({
				height : 165,
				width : 'auto',
				minwidth : 30,
				title : '车辆准载座位管理',
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
				},  {
					name : '查看',
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
}else{
		$('.JQueryflexme').flexigrid({
				height : 165,
				width : 'auto',
				minwidth : 30,
				title : title,
				minheight : 80,
				buttons : [{
					name : '查询',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '查看',
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
}
	function action(com, grid) {
		switch (com) {
			case '添加' :
				quasiID = "";
				$("input.JQuerypersonvalue").attr("checked", false);
				document.addseatForm.reset();
				$("#numCarID").val(carID);
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
				$p.find("span[id]").each(function() {
							$t.find(":input[name]#" + this.id).val($(this)
									.text());
						});
				$("#jqModelup").jqmShow();
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
							basePath + "ea/carquasi/ea_deleteCarquasis.jspa?pageNumber="+ pNumber  + "&carquasi.quasiID="+ quasiID);
					document.carquasiForm.submit.click();
					token = 2;
					$("tr#" + quasiID).remove();
					quasiID = "";
					
				}
				break;
			case '查询':
				$("#jqModelcarSearch").jqmShow();
				break;
			case '查看':
				if (quasiID == "") {
					alert('请选择!');
					return;
				}
				document.seeseatForm.reset();
				$t = $("table#stafftablesee");
				$p = $("tr#" + quasiID);
				$p.find("span[id]").each(function() {
							$t.find(":input[name]#" + this.id).val($(this)
									.text());
						});
				var paihao=($("#numeral",$("#seeseatForm")).val()).substring(0,($("#numeral",$("#seeseatForm")).val()).lastIndexOf("排"));
				var liehao=($("#seatNum",$("#seeseatForm")).val()).substring(0,($("#seatNum",$("#seeseatForm")).val()).lastIndexOf("号"));
				var tabletr = "<table width='98%' align='center' cellspacing='0' cellpadding='1' ><tr><td></tr></table>";
				for (var k = 1; k <=paihao ; k++) {
				for (var j = 0; j < liehao ; j++) {
				tabletr += "<tr ><td  align='center'>"
							+"  <input type='text'  size='10' style='height:20px;' name='carzuo.seatNum' >"+ "</td>";
					tabletr += " </tr>";
				}
				tabletr += " <br/>";
				}
				tabletr += " </table>";
				$("#body_05").html(tabletr);
				$("#body_05").show();
				$("#jqModelsee").jqmShow();
				break;
			case '设置每页显示条数' :
				var url = basePath+ "ea/carquasi/ea_getCarseatList.jspa?search="+ search+"&carInformation.carID="
						+ carID+"&type="+type;
				numback(url);
				break;
			case '导出' :
				var url = basePath+ "ea/carquasi/ea_showCarseatExcels.jspa?";
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
	
	// 添加查看
	$("#addseatForm").find("#seatNums").change(function() {
			if(this.value != "" ){
				var paihao=($("#numerals").val()).substring(0,($("#numerals").val()).lastIndexOf("排"));
				var liehao=($("#seatNums").val()).substring(0,($("#seatNums").val()).lastIndexOf("号"));
				var tabletr = "<table width='98%' align='center' cellspacing='0' cellpadding='1' ><tr><td align='left'>可填座位号:</tr></table>";
				for (var k = 1; k <=paihao ; k++) {
				for (var j = 0; j < liehao ; j++) {
				tabletr += "<tr ><td  align='center'>"
							+"  <input type='text'  size='10' style='height:20px;' name='carzuo.seatNum' >"+ "</td>";
					tabletr += " </tr>";
				}
				tabletr += " <br/>";
				}
				tabletr += " </table>";
				$("#body_03").html(tabletr);
				$("#body_03").show();
		$("#isShow").show();
	}
	});
	// 修改查看
	$("#updateseatForm").find("#seatNum2").change(function() {
			if(this.value != "" ){
				var paihao=($("#numeral2").val()).substring(0,($("#numeral2").val()).lastIndexOf("排"));
				var liehao=($("#seatNum2").val()).substring(0,($("#seatNum2").val()).lastIndexOf("号"));
				var tabletr = "<table width='98%' align='center' cellspacing='0' cellpadding='1' ><tr><td></tr></table>";
				for (var k = 1; k <=paihao ; k++) {
				for (var j = 0; j < liehao ; j++) {
				tabletr += "<tr ><td  align='center'>"
							+"  <input type='text'  size='10' style='height:20px;' name='carzuo.seatNum' >"+ "</td>";
					tabletr += " </tr>";
				}
				tabletr += " <br/>";
				}
				tabletr += " </table>";
				$("#body_04").html(tabletr);
				$("#body_04").show();
		$("#isShows").show();
		}
	});
	//车辆添加保存
	$("input.JQuerySubmit").click(function() {
				$(".put3").trigger("blur");
				if ($("form .error").length) {
					return;
				}
					$("#addseatForm")
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/carquasi/ea_saveCarseat.jspa?pageNumber="
											+ pNumber + "&search=" + search);
					document.addseatForm.submit.click();
					token = 2;
					document.addseatForm.reset();
					
					return;
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
								+ "ea/carquasi/ea_updateCarseat.jspa?pageNumber="
								+ pNumber + "&search=" + search+ "&carquasi.quasiID="+ quasiID);
		document.updateseatForm.submit.click();
		token = 2;
	});	
	//车辆查询
	$("#searchCar").click(function() {
		
		if(carID!=undefined&&carID!=""){
			$("#carIDs").val(carID);
		}
		$("#carSearchForm").attr(
				"action",
				basePath + "/ea/carquasi/ea_toSearch.jspa?pageNumber="
						+ pNumber);
		document.getElementById("carSearchForm").submit();
		$("#carSearchTable").find(":input[name]").val("");
	})	;
});
function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/carquasi/ea_getCarseatList.jspa?pageNumber="
				+ pNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value")+"&carInformation.carID="
						+ carID+"&type="+type;
}



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
	// 上一页
	var contactConnections = "";
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
				var tabletr = "<table width='100%' height='20' align='center' cellspacing='0' cellpadding='1' style='font-size:12px;' class='bannb_01'><tr><td height='24' align='left' valign='top' class='txt01' >&nbsp;点击选择往来单位</td></tr></table>";
				tabletr += "<table width='99%' align='center' id='gostable' cellpadding='0'  cellspacing='0' class='table'>";
				tabletr += " <tr><th height='21' align='center' bgcolor='#E4F1FA'>选择</th><th  align='center' bgcolor='#E4F1FA'>往来单位名称</th>";
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
