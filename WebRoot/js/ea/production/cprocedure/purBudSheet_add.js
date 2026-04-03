$(document).ready(function() {
	
	$("#selectme").click(function(){
	
		cxwlgr("contactUser.staffName=&contactUser.relation=");
		
	});
	
	// 计算金额
	$(".jisuan").live(
			"keyup",
			function(event) {
				var $tr =  $(this).parents("tr");
				if (this.value != "") {
					if (isNaN(this.value)) {
						$tr.find(".money").val("");
						return;
					} else {
						
						var price = $tr.find(".price").val();
						var quantity = $tr.find(".quantity").val();
						if (!isNaN(quantity) && !isNaN(price)) {
							var jine = quantity * price;
							jine = Math.round(jine * 100) / 100;
							$tr.find(".money").val(jine);
						}
					}

				}
			});

	// 提交保存
	$(".save").click(function() {
		
		


		$("#mainForm").attr("target", "hidden").attr("action",
				basePath + "ea/purchasebids/ea_savePurchaseSheet.jspa?type="+type);

		document.mainForm.submit.click();
		token = 2;
		    
	});

	// ////////////////////////////调用产品

	$(".selectpr").click(function() {
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
					var typeCN = "parameter=" + typeName+ "&pageForm.pageNumber=" + sy;
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
					var typeCN = "parameter=" + typeName + "&pageForm.pageNumber=" + xy;
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
			var quantity=parseInt($("#protable tr#"+ppID).find("#quantity").text());
			$("#subTable").find("tbody").find("tr").remove();
			
			 $("#protable tr#" + ppID).find("td").each(function() {
					$("table#productbl").find("input." + $(this).attr("id"))
							.val($(this).text());
			$("table#productbl").find("input.ppID").val($("#protable tr#" + ppID).find(".ppID").val());
			
			 });
			$.ajax({
				url : basePath+"ea/assembly/sajax_ea_getTheLowest.jspa?ppID="+ppID,
				type : "post",
				async : false,
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var list=member.list;
					if(list.length!=0){
						for(var i=0;i<list.length;i++){
							
							var tr="<tr id="+list[i][0]+">" +
									"<td align='center'>"+(i+1)+"</td>" +
									"<td align='center'>"+(list[i][1]==null?"":list[i][1])+"</td>" +
									"<td align='center'>"+list[i][2]+"</td>" +
									"<td align='center'>"+list[i][3]+"</td>" +
									"<td align='center'>"+list[i][4]+"</td>" +
									"<td align='center'><input type='text' class='jisuan price' name='logbookmap["+(i+1)+
									"].price' style='border:none;width:99%;' value='"+list[i][5]+"'></td>" +
									"<td align='center'><input type='text' class='jisuan quantity'  name='logbookmap["+(i+1)+
									"].quantity' style='border:none;width:99%;' value="+(parseInt(list[i][6])*quantity)+" ></td>" +
									"<td align='center'><input type='text' class='money'  name='logbookmap["+(i+1)+
									"].money' style='border:none;width:99%;'readonly value="+(parseFloat(list[i][5])*(parseInt(list[i][6])*quantity))+"  ></td>" +
									"<td align='center'><input type='text' name='logbookmap["+(i+1)+"].remark' style='border:none;width:99%;' value="+list[i][7]+" ></td>" +						
									"<input type='hidden' name='logbookmap["+(i+1)+"].goodsID'  value='"+list[i][0]+"'/>"	+
									"</tr>";
							$("#sublist").append(tr);
						}
					}else{
						var pp=member.pp;
						var tr="<tr id="+pp.ppID+">" +
						"<td align='center'>1</td>" +
						"<td align='center'>"+(pp.goodsNum==null?"":pp.goodsNum)+"</td>" +
						"<td align='center'>"+pp.goodsName+"</td>" +
						"<td align='center'>"+pp.model+"</td>" +
						"<td align='center'>"+pp.variableID+"</td>" +
						"<td align='center'><input type='text' class='jisuan price' name='logbookmap[1"+
						"].price' style='border:none;width:99%;' value='"+pp.price+"'></td>" +
						"<td align='center'><input type='text' class='jisuan quantity'  name='logbookmap[1"+
						"].quantity' style='border:none;width:99%;' value="+(parseInt(pp.quantity)*quantity)+" ></td>" +
						"<td align='center'><input type='text' class='money'  name='logbookmap[1"+
						"].money' style='border:none;width:99%;'readonly value="+(parseFloat(pp.price)*(parseInt(pp.quantity)*quantity))+"  ></td>" +
						"<td align='center'><input type='text' name='logbookmap[1].remark' style='border:none;width:99%;' value="+pp.remark+" ></td>" +						
						"<input type='hidden' name='logbookmap[1].goodsID'  value='"+pp.ppID+"'/>"	+
						"</tr>";
						$("#sublist").append(tr);
					}
				},error:function(data){
					alert("获取数据失败");
				}
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
				var staffCode=$("tr#"+ cuID).find("#contactUserCode").val();
			     $("#dutorID").val(contactUserID);
			     $("#dutorName").val(name+"("+staffCode+")");
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
			+ "ea/assembly/sajax_ea_getProductAssemblyList.jspa?";
	$.ajax({
		url : encodeURI(searchurl + typeCN + "&date="+ new Date().toLocaleString()+
					"&type="+type+"&fiveClear="+fiveClear+"&ajax=ajax&status=00"),
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
			tabletr += " <tr><th height='27' align='center' bgcolor='#E4F1FA'>选择</th>";
			tabletr += "<th align='center' bgcolor='#E4F1FA'>生产批次号</th>";
			tabletr += "<th align='center'  bgcolor='#E4F1FA'>产品名称</th>";
			tabletr += "<th align='center' bgcolor='#E4F1FA'>产品编号</th>";
			tabletr += "<th align='center' bgcolor='#E4F1FA'>产品品牌</th>";
			tabletr += "<th align='center' bgcolor='#E4F1FA'>往来个人</th>";
			tabletr += "<th align='center' bgcolor='#E4F1FA'>单价</th>";
			tabletr += "<th align='center' bgcolor='#E4F1FA'>数量</th>";
			tabletr += "<th align='center' bgcolor='#E4F1FA'>金额</th></tr>";

			for (var i = 0; i < pageForm.list.length; i++) {
				tabletr += "<tr style='cursor: hand;' id = "
						+ (pageForm.list[i][10]==null?"":pageForm.list[i][10]) + " name="+pageForm.list[i][0]+">";
				tabletr += "<td  height='27' id='check' align='center'>"
						+ "<input type ='radio' class='ppID raporducts' value="
						+( pageForm.list[i][10] ==null?"":pageForm.list[i][10])+ " name='check'/></td>";
				tabletr += "<td id='batchNumber' align='center'>"
						+ (pageForm.list[i][2] ==null?"":pageForm.list[i][2])+ "</td>";
				tabletr += "<td id='goodsName' align='center'>"
						+ (pageForm.list[i][3]==null?"":pageForm.list[i][3]) + "</td>";
				tabletr += "<td id='productCode' align='left'>"
						+ (pageForm.list[i][4] ==null?"":pageForm.list[i][4])+ "</td>";
				tabletr += "<td id='standard'  align='center'>"
						+( pageForm.list[i][5]==null?"":pageForm.list[i][5]) + "</td>";
				tabletr += "<td id='contactUserName' align='center'>"
						+ (pageForm.list[i][6]==null?"":pageForm.list[i][6]) + "</td>";
				tabletr += "<td id='price' align='center'>"
						+ (pageForm.list[i][7]==null?"":pageForm.list[i][7]) + "</td>";
				tabletr += "<td id='quantity' align='center'>"
						+ (pageForm.list[i][8] ==null?"":pageForm.list[i][8])+ "</td>";
				tabletr += "<td id='money' align='center'>"
						+ (pageForm.list[i][9] ==null?"":pageForm.list[i][9])+ "</td>";
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
				tabletr+="<input type='hidden' id='contactUserCode' value="+pageForm.list[i].staffCode+">";
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