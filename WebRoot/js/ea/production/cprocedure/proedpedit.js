$(document)
		.ready(
				function() {
					$("#selectme").click(function() {

						cxwlgr("contactUser.staffName=&contactUser.relation=");

					});
					
					// 提交保存
					$(".save").click(function() {
										
						$("form :input:.ckTextLength").trigger("blur");
						$("form :input:.input3").trigger("blur");
						$("form :input:.posnum").trigger("blur");
						  $("form :input:.put3").trigger("blur");
				          if($("form .error").length)
				          { 
				            return;
				          } 
						
						
										$("#mainForm")
												.attr("target", "hidden")
												.attr(
														"action",
														basePath
															+ "ea/proedpdist/ea_saveProEdp.jspa?fiveClear="+fiveClear);
										
										document.mainForm.submit.click();
										token=2;
										
					});
									
									
					$("#selgood").click(function() {

						cx("");

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
					$("#wpsyp").click(
							function() {
								var sy = $("#wpsyp").attr("title");
								if (sy != 0) {
									var typeName = $(":input#parameter").val();
									var typeCN = typeName
											+ "&pageForm.pageNumber=" + sy;
									pcx(typeCN);
								} else {
									alert("已是首页！");
								}
							});

					// 下一页
					$("#wpxyp").click(
							function() {
								var xy = $("#wpxyp").attr("title");
								if (xy != 0) {
									var typeName = $(":input#parameter").val();
									var typeCN = typeName
											+ "&pageForm.pageNumber=" + xy;
									pcx(typeCN);
								} else {
									alert("已是尾页！");
								}
							});

					// 根据输入的产品编号或产品名称查询
					$("input#searchProduct").click(
							function() {
								var typeName = $("#parameter",
										$("table#searchpro")).val();
								pcx("parameter=" + typeName);
							});

					// 选择产品确定
					$("#selectProduct").click(
							function() {

								if ($("[name='check']").is(':checked')) {
									var ppID = $("input[name='check']:checked")
											.val();
									$("#protable tr#" + ppID).find("td").each(
											function() {

												$("table#productbl").find(
														"input."
																+ $(this).attr(
																		"id"))
														.val($(this).text());

											});

									$("#products").hide();

								} else {
									alert("请选择项目产品");
								}
							});

					// 调用人员

					$(function() {
						treegr = new dhtmlXTreeObject("grTree", "100%", "100%",
								0);
						treegr.enableDragAndDrop(false);
						treegr.enableHighlighting(1);
						treegr.enableCheckBoxes(0);
						treegr.enableThreeStateCheckboxes(false);
						treegr.setSkin(basePath + 'js/tree/dhx_skyblue');
						treegr
								.setImagePath(basePath
										+ "js/tree/codebase/imgs/");
						treegr.loadXML(basePath + "js/tree/common/tree_b.xml");
						var getcodeurl = basePath
								+ "ea/ccode/sajax_ea_getListCCodeByPID.jspa?codeID=scode20110106hfjes5ucxp0000000017&date="
								+ new Date().toLocaleString();
						treegr.insertNewChild(0,
								"scode20110106hfjes5ucxp0000000017", "往来个人类别",
								0, 0, 0, 0);
						$
								.ajax({
									url : encodeURI(getcodeurl),
									type : "get",
									async : true,
									dataType : "json",
									success : function cbf(data) {
										var member = eval("(" + data + ")");
										var nologin = member.nologin;
										if (nologin) {
											document.location.href = basePath
													+ "page/ea/not_login.jsp";
										}
										var codeList = member.codeList;
										if (null == codeList) {
											return;
										}
										for (var i = 0; i < codeList.length; i++) {

											treegr
													.insertNewChild(
															"scode20110106hfjes5ucxp0000000017",
															codeList[i].codeID,
															codeList[i].codeValue,
															0, 0, 0, 0);
											treegr.setUserData(
													codeList[i].codeID,
													"codeNumber",
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
						$("table#gouserstable tr[id]").live(
								"click",
								function(event) {
									cuID = this.id;
									userstaffID = this.title;
									$("input.rauser", $(this)).attr("checked",
											"checked");
								});

						// 选择往来个人
						$(".wlgr")
								.live(
										"click",
										function() {

											$(".jqmWindow",
													$("#selectuserForm"))
													.jqmShow();
											cxwlgr("contactUser.staffName=&contactUser.relation=");
										});

						// 新增往来个人
						$(".xzgr")
								.click(
										function() {
											window
													.open(basePath
															+ "ea/cstaff/ea_getListCStaffByCompanyID.jspa?");
										});

						// 添加所选中的往来个人
						$("#qduser").click(
								function() {
									if (cuID != "") {

										var name = $("tr#" + cuID).find(
												"#contactUserName").text();
										var contactUserID = $("tr#" + cuID)
												.find("#contactUserID").text();

										$("#dutorID").val(contactUserID);
										$("#dutorName").val(name);
										$("#members").hide();

										return;
									} else {
										alert("请选择往来个人！");
									}
								});

						// 根据输入的往来个人名称查询
						$("input#searchuu").click(
								function() {
									cuID = "";
									userstaffID = "";
									var typeName = $("input#contactUserID",
											$("table#searchuser")).val();
									var relation = $(":input#grparms").val();
									cxwlgr("contactUser.staffName=" + typeName
											+ "&contactUser.relation="
											+ relation);
								});

						// 上一页
						$("#grsy").click(
								function() {
									var sy = $("#grsy").attr("title");
									if (sy != 0) {
										cuID = "";
										userstaffID = "";
										var typeName = $("input#contactUserID",
												$("table#searchuser")).val();
										var relation = $(":input#grparms")
												.val();
										var typeCN = "contactUser.staffName="
												+ typeName
												+ "&contactUser.relation="
												+ relation
												+ "&pageForm.pageNumber=" + sy;
										cxwlgr(typeCN);
									} else {
										alert("已是首页！");
									}
								});

						// 下一页
						$("#grxy").click(
								function() {
									var xy = $("#grxy").attr("title");
									if (xy != 0) {
										cuID = "";
										userstaffID = "";
										var typeName = $("input#contactUserID",
												$("table#searchuser")).val();
										var relation = $(":input#grparms")
												.val();
										var typeCN = "contactUser.staffName="
												+ typeName
												+ "&contactUser.relation="
												+ relation
												+ "&pageForm.pageNumber=" + xy;
										cxwlgr(typeCN);
									} else {
										alert("已是尾页！");
									}
								});

					});
					
					
					
					
						// 物品
	$(document).ready(function() {
		
		
		tree3 = new dhtmlXTreeObject("gtree", "100%", "100%", 0);
		tree3.enableDragAndDrop(false);
		tree3.enableHighlighting(1);
		tree3.enableCheckBoxes(0);
		tree3.enableThreeStateCheckboxes(false);
		tree3.setSkin(basePath + 'js/tree/dhx_skyblue');
		tree3.setImagePath(basePath + "js/tree/codebase/imgs/");
		tree3.loadXML(basePath + "js/tree/common/tree_b.xml");
		var getcodeurl = basePath
				+ "ea/ccode/sajax_ea_getListCCodeByPID.jspa?codeID=scode20140919hqjwqgg8fg0000000009&date="
				+ new Date().toLocaleString();
		tree3.insertNewChild(0, "scode20140919hqjwqgg8fg0000000009", "固定资产", 0,
				0, 0, 0);
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

					tree3.insertNewChild("scode20140919hqjwqgg8fg0000000009",
							codeList[i].codeID, codeList[i].codeValue, 0, 0, 0,
							0);
					tree3.setUserData(codeList[i].codeID, "codeNumber",
							codeList[i].codeNumber);

				}
			},
			error : function cbf(data) {
				alert("数据获取失败！");
			}
		});
		tree3.setOnClickHandler(function() {

					treeid = tree3.getSelectedItemId();
					treename = tree3.getItemText(treeid);

					if (treeid != "scode20140919hqjwqgg8fg0000000009") {
						$(":input#parms").val("typeID=" + treename);
						cx("typeID=" + treename);
						return;
					}

				});

		// 双击选中物品
		$("table#gotable tr[id]").live("click", function(event) {
					var b = $("input.ragood", $(this)).attr("checked");
					$("input.ragood", $(this)).attr("checked", !b);
				});

		// 复选框选中物品
		$(".ragood").live("click", function(event) {
					var b = $(this).attr("checked");
					$(this).attr("checked", !b);
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

		// 根据输入的物品编号或物品名称查询
		$("input#searchGood").click(function() {
					var typeName = $("#typeID", $("table#searchgood")).val();
					$(":input#parms").val("parameter=" + typeName);

					cx("parameter=" + typeName);
				});

		// 选择物品后确定
		$("#selectGood").click(function() {
				var str="";
			if($("input[name='checks']:checked").size()>0){
			
				$("input[name='checks']:checked").each(function(){
				
					var goodsID = $(this).val();
				  if($("#devices").val().indexOf(goodsID)==-1){
					   var goodsName = $("tr#"+goodsID).find("#goodsName").text();
					    str+=goodsID+"-"+goodsName+",";
					    var option = "<option title = '双击移除' value='"+goodsID+"'>"+goodsName+"</option>"
					    $("#sb").append(option);
						
					
					}
					
				});
				
			  $("#devices").val($("#devices").val()+str);
				
				
				$("#goods").hide();
			} else {
				alert("请选择物品");
			}
		});
		
		//移除
		$("#sb").dblclick(function(){
		
		 
		  var goodsID = this.value;
		  var goodsName =  $(this).find("option[value="+goodsID+"]").val();
		  
		  var str = goodsID+"-"+goodsName+","
		  $("#devices").val($("#devices").val().replace(str,""));
		  
		  $(this).find("option[value="+goodsID+"]").remove();

		});

		// 新增物品
		$(".xzwp").click(function() {
			window
					.open(basePath
							+ "ea/gooddesign/ea_getGoodDesignList.jspa?");
		});

	});
		$(".fieTr").live("click",function(){
			$(this).find("input").attr("checked","checked");
			fieId=$(this).find("input").val();
		});
		$("#fieldPut").click(function(){
			var pprID=$(".ppID").val();
			if(pprID==""){
				alert("请选择产品！");
				return;
			}
			$.ajax({
				url:basePath+"ea/proedpdist/sajax_ea_ajaxAcquisitionSite.jspa?ppID="+pprID+"&fiveClear="+fiveClear,
				type:"post",
				async : false,
				success:function(data){
					var member = eval("(" + data + ")");
					var pageForm=member.pageForm;
					var con=member.con;
					var trs="<tr class='fieTtr'><th>选择</th><th>开始时间</th><th>结束时间</th><th>职责</th>"+
									"<th>场地地址</th><th>责任人</th><th>分配时间</th><th>备注</th></tr>";
					if(pageForm!=null){
						var list=pageForm.list;
						for(var i=0;i<list.length;i++){
							trs+="<tr class='fieTr fieTtr' id="+list[i].fieldDistributionId+"><td><input type='radio' name='fieRadio' value='"+list[i].fieldDistributionId+"'></td>" +
									   "<td>"+list[i].startTime+"</td><td>"+list[i].endTime+"</td><td>"+list[i].duty+"</td>" +
									   	"<td>"+list[i].siteAddress+"</td><td>"+list[i].staffName+"</td><td>"+list[i].distributionTime+"</td>" +
									   	"<td>"+list[i].remarks+"</td></tr>";
						}
					}
					$("#fieTable").append(trs);
					if(con%10!=0)
						con=parseInt(con/10)+1;
					else
						con=parseInt(con/10);	
					$("#fieWpzycount").text(con);
				},
				error:function(data){
					alert("数据获取失败");
				}
			});
			$("#fie").attr("style","display: block; position: absolute; top: 50px; left: 18%;").show();
		});
		$("#fieClose").click(function(){
			$(".fieTtr").remove();
			$("#fie").hide();
		});
		$(".fieOperation").click(function(){
		if ($("[name='fieRadio']").is(':checked')) {
				var ppID = $("input[name='fieRadio']:checked")
						.val();
				$("#siteAddress").val($("#"+ppID).find("td").eq(4).text());
				$("#field").val(ppID);
				$(".fieTtr").remove();
				$("#fie").hide();
			} else {
				alert("请选择");
			}
		
		});
				});

function re_load() {
	window.opener.location.href = window.opener.location.href;
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
			+ "ea/prodesign/sajax_ea_getProductDesignList.jspa?fiveClear="+fiveClear;
	$
			.ajax({
				url : encodeURI(searchurl + typeCN + "&date="
						+ new Date().toLocaleString()+"&type=01"+"&fiveClear="+fiveClear),
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
						tabletr += "<td height='27' id='check' align='center'>"
								+ "<input type ='radio' class='raporducts' value="
								+ pageForm.list[i].ppID
								+ " name='check'/></td>";
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

	$
			.ajax({
				url : encodeURI(searchurl + typeCN + "&date="
						+ new Date().toLocaleString()),
				type : "get",
				async : true,
				dataType : "json",
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var nologin = member.nologin;
					if (nologin) {
						document.location.href = basePath
								+ "page/ea/not_login.jsp";
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


// ajax查询物品列表
function cx(typeCN) {
	if (notoken) {
		alert("正在获取数据！请稍等");
		return;
	}
	notoken = 1;
	$("#wpsy").attr("title", 0);
	$("#wpxy").attr("title", 0);
	$("#wpzy").attr("title", 0);
	var searchurl = basePath
			+ "ea/cashierbills/sajax_ea_getGoodsManageByTypeID.jspa?";
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
			//
			$("span#wpzycount").text(zys);
			var tabletr = "<table width='100%' height='26' align='center' cellspacing='0' cellpadding='1' style='font-size:12px;' class='bannb_01'><tr><td height='24' align='left' valign='top' class='txt01' >&nbsp;点击选择物品</td></tr></table>";
			tabletr += "<table width='100%' align='center' id='gotable' cellpadding='0' cellspacing='0' class='table'>";
			tabletr += " <tr><th height='21' align='center' bgcolor='#E4F1FA'>选择</th>";
			tabletr += "<th align='center' bgcolor='#E4F1FA'>行业</th>";
			tabletr += "<th align='center'  bgcolor='#E4F1FA'>物品条码</th>";
			tabletr += "<th align='center' bgcolor='#E4F1FA'>物品编码</th>";
			tabletr += "<th align='center' bgcolor='#E4F1FA'>物品名称</th>"
			tabletr += "<th align='center' bgcolor='#E4F1FA'>品牌</th>"
					+ "<th align='center' bgcolor='#E4F1FA'>型号</th>";
			+"<th align='center' bgcolor='#E4F1FA'>规格</th>";
			tabletr += "<th align='center' bgcolor='#E4F1FA'>规格</th>"
					+ "<th align='center' bgcolor='#E4F1FA'>单位</th></tr>";
			for (var i = 0; i < pageForm.list.length; i++) {
				tabletr += "<tr style='cursor: hand;' id = "
						+ pageForm.list[i].goodsID + ">";
				tabletr += "<td id='checks' align='center'>"
						+ "<input type ='checkbox' class='ragood' value="
						+ pageForm.list[i].goodsID + " name='checks'/></td>";
				tabletr += "<td id='tradeCode' align='center'>"
						+ pageForm.list[i].tradeCode + "</td>";
				tabletr += "<td id='barCode' align='center'>"
						+ pageForm.list[i].barCode + "</td>";
				tabletr += "<td id='goodsCoding' align='center'>"
						+ pageForm.list[i].goodsCoding + "</td>";
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
				tabletr += "<td id='photoPath' align='center' style='display:none;'>"
						+ pageForm.list[i].photoPath + "</td>";
				tabletr += "<td id='goodsID' align='center' style='display:none;'>"
						+ pageForm.list[i].goodsID + "</td>";
				tabletr += "<td id='tradeName' align='center' style='display:none;'>"
						+ pageForm.list[i].tradeName + "</td>";
				tabletr += "<td id='tradeID' align='center' style='display:none;'>"
						+ pageForm.list[i].tradeID + "</td>";
				tabletr += " </tr>";
			}
			tabletr += " </table>";
			$("#body_02").html(tabletr);
			$("#body_02").show();
			// alert("数据加载成功");
			notoken = 0;
			window.status = "数据加载成功";
		},
		error : function cbf(data) {
			notoken = 0;
			alert("数据获取失败！");
		}
	});
}