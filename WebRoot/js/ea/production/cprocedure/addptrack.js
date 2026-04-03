$(function() {

	if(ptrackeId==""){
	    getLotNumber();
	}
	// 获取id
	$(".JQueryflexme tr[id]").click(function() {
		id = this.id;
		$("input.JQuerypersonvalue", $(this)).attr("checked", "checked");
	});
	
	$("input.inputtext").blur(function() {
		var option=$(this).val();
		if(option==""){
			var str=$(this).parents("tr").find("td").eq(0).text();
			str=str.substr(0,str.length-1);
			alert(str+"不能为空");
			
		}
	});
	
    //保存
	$(".submitResult").click(function() {
		$("input.inputtext").each(function(){
			var option=$(this).val();
		     if(option==""){
		    	 var str=$(this).parents("tr").find("td").eq(0).text();
				 alert(str.substr(0,str.length-1)+"不能为空");
				 return false;
		 }else{
		$("form#ptrackForm").attr("target", "hidden").attr("action",
				basePath + "ea/ptrack/ea_saveOrEditByPtrack.jspa?status=00");
		document.ptrackForm.submit.click();
		document.ptrackForm.reset();
		token = 2;
		 }
		});
		});
			
			
	// 计算合格率
			
	$(".jisuan").bind('input propertychange', function() {
				var value = $(this).val();
				if (isNaN(value)) {
					// 非数字
					return;
				}
				
				yields();
				
				
	});
			
			
	// ////////////////////////////调用产品

	$("#selectpr").click(function() {

				pcx("");

			});

	// 双击选中产品
	$("table#protable tr[id]").live("click", function(event) {
				var b = $("input.raporducts", $(this)).attr("checked");
				$("input.raporducts", $(this)).attr("checked", !b);
			});

	// 单选框选中物品
	$(".raporducts").live("click", function(event) {
				var b = $(this).attr("checked");
				$(this).attr("checked", !b);
			});

	// 上一页
	$("#wpsyp").click(function() {
				var sy = $("#wpsyp").attr("title");
				if (sy != 0) {
					var typeName = $(":input#parameter").val();
					var typeCN = typeName + "&pageForm.pageNumber=" + sy;
					pcx(typeCN);
				} else {
					alert("已是首页！");
				}
			});

	// 下一页
	$("#wpxyp").click(function() {
				var xy = $("#wpxyp").attr("title");
				if (xy != 0) {
					var typeName = $(":input#parameter").val();
					var typeCN = typeName + "&pageForm.pageNumber=" + xy;
					pcx(typeCN);
				} else {
					alert("已是尾页！");
				}
			});

	// 根据输入的产品编号或产品名称查询
	$("input#searchProduct").click(function() {
				var typeName = $("#parameter", $("table#searchpro")).val();
				pcx("parameter=" + typeName);
			});

	// 选择产品确定
	$("#selectProduct").click(function() {

		if ($("[name='check']").is(':checked')) {
			var ppID = $("input[name='check']:checked").val();

			$("#protable tr#" + ppID).find("td").each(function() {

				$("table#productbl").find("input." + $(this).attr("id"))
						.val($(this).text());

			});

			$("#products").hide();

		} else {
			alert("请选择项目产品");
		}
	});

	// 调用人员

	$(function() {
		treegr = new dhtmlXTreeObject("grTree", "100%", "100%", 0);
		treegr.enableDragAndDrop(false);
		treegr.enableHighlighting(1);
		treegr.enableCheckBoxes(0);
		treegr.enableThreeStateCheckboxes(false);
		treegr.setSkin(basePath + 'js/tree/dhx_skyblue');
		treegr.setImagePath(basePath + "js/tree/codebase/imgs/");
		treegr.loadXML(basePath + "js/tree/common/tree_b.xml");
		var getcodeurl = basePath
				+ "ea/ccode/sajax_ea_getListCCodeByPID.jspa?codeID=scode20110106hfjes5ucxp0000000017&date="
				+ new Date().toLocaleString();
		treegr.insertNewChild(0, "scode20110106hfjes5ucxp0000000017", "往来个人类别",
				0, 0, 0, 0);
		$.ajax({
			url : encodeURI(getcodeurl),
			type : "get",
			async : true,
			dataType : "json",
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				var nologin = member.nologin;
				if (nologin) {
					document.location.href = basePath + "page/ea/not_login.jsp";
				}
				var codeList = member.codeList;
				if (null == codeList) {
					return;
				}
				for (var i = 0; i < codeList.length; i++) {

					treegr.insertNewChild("scode20110106hfjes5ucxp0000000017",
							codeList[i].codeID, codeList[i].codeValue, 0, 0, 0,
							0);
					treegr.setUserData(codeList[i].codeID, "codeNumber",
							codeList[i].codeNumber);

				}
			},
			error : function cbf(data) {
				alert("数据获取失败！");
			}
		});
		treegr.setOnClickHandler(function() {

					treeid = treegr.getSelectedItemId();
					treename = treegr.getItemText(treeid);

					var typeName = $("input#contactUserID",
							$("table#searchuser")).val();

					if (treeid == "scode20110106hfjes5ucxp0000000017") {
						treename = "";
					}
					$(":input#grparms").val(treename);

					var typeCN = "contactUser.staffName=" + typeName
							+ "&contactUser.relation=" + treename;
					cxwlgr(typeCN);
					return;

				});

		var cuID = "";
		var userstaffID = "";
		$("table#gouserstable tr[id]").live("click", function(event) {
					cuID = this.id;
					userstaffID = this.title;
					$("input.rauser", $(this)).attr("checked", "checked");
				});

		// 选择往来个人
		$(".wlgr").live("click", function() {

					$(".jqmWindow", $("#selectuserForm")).jqmShow();
					cxwlgr("contactUser.staffName=&contactUser.relation=");
				});

		// 新增往来个人
		$(".xzgr").click(function() {
			window.open(basePath
					+ "ea/cstaff/ea_getListCStaffByCompanyID.jspa?");
		});

		// 添加所选中的往来个人
		$("#qduser").click(function() {
			if (cuID != "") {

				var name = $("tr#"+ cuID).find("#contactUserName").text();
				var contactUserID = $("tr#"+ cuID).find("#contactUserID").text();
				
			     $("#dutorID").val(contactUserID);
			     $("#dutorName").val(name);
			     $("#members").hide();
				
				return;
			} else {
				alert("请选择往来个人！");
			}
		});

		// 根据输入的往来个人名称查询
		$("input#searchuu").click(function() {
			cuID = "";
			userstaffID = "";
			var typeName = $("input#contactUserID", $("table#searchuser"))
					.val();
			var relation = $(":input#grparms").val();
			cxwlgr("contactUser.staffName=" + typeName
					+ "&contactUser.relation=" + relation);
		});

		// 上一页
		$("#grsy").click(function() {
			var sy = $("#grsy").attr("title");
			if (sy != 0) {
				cuID = "";
				userstaffID = "";
				var typeName = $("input#contactUserID", $("table#searchuser"))
						.val();
				var relation = $(":input#grparms").val();
				var typeCN = "contactUser.staffName=" + typeName
						+ "&contactUser.relation=" + relation
						+ "&pageForm.pageNumber=" + sy;
				cxwlgr(typeCN);
			} else {
				alert("已是首页！");
			}
		});

		// 下一页
		$("#grxy").click(function() {
			var xy = $("#grxy").attr("title");
			if (xy != 0) {
				cuID = "";
				userstaffID = "";
				var typeName = $("input#contactUserID", $("table#searchuser"))
						.val();
				var relation = $(":input#grparms").val();
				var typeCN = "contactUser.staffName=" + typeName
						+ "&contactUser.relation=" + relation
						+ "&pageForm.pageNumber=" + xy;
				cxwlgr(typeCN);
			} else {
				alert("已是尾页！");
			}
		});

	});
	});

/**
 * 获得生产批次号
 */
function getLotNumber() {
	var url = basePath + "ea/ptrack/sajax_ea_getLotNumber.jspa?date="
			+ new Date().toLocaleString();
	$.ajax({
		url : url,
		type : "get",
		async : true,
		dataType : "json",
		data : {
			"lots" : "014"
		},
		success : function cbf(data) {
			var member = eval("(" + data + ")");
			var nologin = member.nologin;
			if (nologin) {
				document.location.href = basePath + "page/ea/not_login.jsp";
			}
			var vouch = member.BillID;
			$("input#lot", $("form#ptrackForm")).val(vouch);
		},
		error : function cbf(data) {
			alert("数据获取失败!");
		}
	});
}
function yields(){
	var throughput=$("input#throughput").val();
	var percent=$("input#percent").val();
	if(throughput!=""&&percent!=""){   
		throughput = parseFloat(throughput);   
		percent = parseFloat(percent);
		}
	var number=percent/throughput;
	var str=number.toFixed(2)*100 + "%"; 
	$("input#yield").val(str);
}



function re_load() {
	window.opener.location.href = window.opener.location.href
	window.close();

}

// 查询项目产品
function pcx(typeCN) {
	if (notoken) {
		alert("正在获取数据！请稍等");
		return;
	}
	notoken = 1;
	$("#wpsyp").attr("title", 0);
	$("#wpxyp").attr("title", 0);
	$("#wpzyp").attr("title", 0);
	var searchurl = basePath
			+ "ea/prodesign/sajax_ea_getProductDesignList.jspa?&type=01";
	$.ajax({
		url : encodeURI(searchurl + typeCN + "&date="
				+ new Date().toLocaleString()),
		type : "get",
		async : true,
		dataType : "json",
		data : {
			iscall : "call",
			search : "search"
		},
		success : function cbf(data) {
			var member = eval("(" + data + ")");
			var pageForm = member.pageForm;
			if (pageForm == null) {
				alert("没有数据");
				notoken = 0;
				return;
			}
			var dqy = pageForm.pageNumber;// 当前页
			var zys = pageForm.pageCount;// 总页数
			if (dqy > 1) {
				$("#wpsyp").attr("title", dqy - 1);
			}
			if (dqy < zys) {
				$("#wpxyp").attr("title", dqy + 1);
			}
			//
			$("span#wpzycountp").text(zys);
			var tabletr = "";
			tabletr += "<table width='100%' align='center' id='protable' cellpadding='0' cellspacing='0' class='table'>";
			tabletr += " <tr><th height='21' align='center' bgcolor='#E4F1FA'>选择</th>";
			tabletr += "<th align='center' bgcolor='#E4F1FA'>行业</th>";
			tabletr += "<th align='center'  bgcolor='#E4F1FA'>产品条码</th>";
			tabletr += "<th align='center' bgcolor='#E4F1FA'>产品编码</th>";
			tabletr += "<th align='center' bgcolor='#E4F1FA'>产品名称</th>";
			tabletr += "<th align='center' bgcolor='#E4F1FA'>品牌</th>";
			tabletr += "<th align='center' bgcolor='#E4F1FA'>型号</th>";
			tabletr += "<th align='center' bgcolor='#E4F1FA'>规格</th>";
			tabletr += "<th align='center' bgcolor='#E4F1FA'>单位</th>";
			tabletr += "<th align='center' bgcolor='#E4F1FA'>数量</th>";
			tabletr += "<th align='center' bgcolor='#E4F1FA'>单价</th>";
			tabletr += "<th align='center' bgcolor='#E4F1FA'>金额</th></tr>";

			for (var i = 0; i < pageForm.list.length; i++) {
				tabletr += "<tr style='cursor: hand;' id = "
						+ pageForm.list[i].ppID + ">";
				tabletr += "<td id='check' align='center'>"
						+ "<input type ='radio' class='raporducts' value="
						+ pageForm.list[i].ppID + " name='check'/></td>";
				tabletr += "<td id='tradeCode' align='center'>"
						+ pageForm.list[i].tradeCode + "</td>";
				tabletr += "<td id='barCode' align='center'>"
						+ pageForm.list[i].barCode + "</td>";
				tabletr += "<td id='productCode' align='left'>"
						+ pageForm.list[i].productCode + "</td>";
				tabletr += "<td id='goodsName'  align='center'>"
						+ pageForm.list[i].goodsName + "</td>";
				tabletr += "<td id='brand' align='center'>"
						+ pageForm.list[i].brand + "</td>";
				tabletr += "<td id='model' align='center'>"
						+ pageForm.list[i].model + "</td>";
				tabletr += "<td id='standard' align='center'>"
						+ pageForm.list[i].standard + "</td>";
				tabletr += "<td id='variableID' align='center'>"
						+ pageForm.list[i].variableID + "</td>";
				tabletr += "<td id='quantity' align='center'>"
						+ pageForm.list[i].quantity + "</td>";
				tabletr += "<td id='price' align='center'>"
						+ pageForm.list[i].price + "</td>";
				tabletr += "<td id='money' align='center'>"
						+ pageForm.list[i].money + "</td>";
				tabletr += "<td id='ppID' align='center' style='display:none;'>"
						+ pageForm.list[i].ppID + "</td>";
				tabletr += " </tr>";
			}
			tabletr += " </table>";
			$("#body_03").html(tabletr);
			$("#body_03").show();
			notoken = 0;
			window.status = "数据加载成功";
		},
		error : function cbf(data) {
			notoken = 0;
			alert("数据获取失败！");
		}
	});
}

// ajax查询往来个人列表
function cxwlgr(typeCN) {
	if (notoken) {
		alert("正在获取数据！请稍等");
		return;
	}
	notoken = 1;
	$("#grsy").attr("title", 0);
	$("#grxy").attr("title", 0);
	$("#grzy").attr("title", 0);
	var searchurl = basePath
			+ "ea/contactuser/sajax_ea_getListContactUserBycontactUserName.jspa?";

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
			var codeRelationList = member.codeRelationList;
			if (pageForm == null) {
				alert("没有数据");
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
			var tabletr = "<table width='100%' height='26' align='center' cellspacing='0' cellpadding='1' style='font-size:12px;' class='bannb_01'><tr><td height='24' align='left' valign='top' class='txt01' >&nbsp;点击选择往来个人</td></tr></table>";
			tabletr += "<table width='100%' align='center' id='gouserstable' cellpadding='0'  cellspacing='0' class='table'>";
			tabletr += " <tr><th height='21' align='center' bgcolor='#E4F1FA'>选择</th><th align='center' bgcolor='#E4F1FA'>个人名称</th>"
					+ "<th align='center' bgcolor='#E4F1FA'>往来关系</th><th align='center' bgcolor='#E4F1FA'>电话</th><th align='center' bgcolor='#E4F1FA'>身份证</th></tr>";
			for (var i = 0; i < pageForm.list.length; i++) {
				tabletr += "<tr style='cursor: hand;' id = "
						+ pageForm.list[i].relationID + " title= "
						+ pageForm.list[i].staffID + ">";
				tabletr += "<td id='checkcc' align='center'><input type ='radio'  class='rauser' value="
						+ pageForm.list[i].relationID
						+ " name='checkradio'/></td>";
				tabletr += "<td id='contactUserName' align='center'>"
						+ pageForm.list[i].staffName + "</td>";
				tabletr += "<td id='phone' align='center'>"
						+ pageForm.list[i].relation + "</td>";
				tabletr += "<td id='tel' align='center'>"
						+ pageForm.list[i].reference + "</td>";
				tabletr += "<td id='staffIdentityCard' align='center'>"
						+ pageForm.list[i].staffIdentityCard + "</td>";
				tabletr += "<td id='contactUserID'  style='display:none' align='center'>"
						+ pageForm.list[i].staffID + "</td>";
				tabletr += " </tr>";
			}
			tabletr += " </table>";
			$("#body_02cu").html(tabletr);

			// alert("数据加载成功")
			notoken = 0;
			window.status = "数据加载成功";
		},
		error : function cbf(data) {
			notoken = 0;
			alert("数据获取失败！");
		}
	});
}