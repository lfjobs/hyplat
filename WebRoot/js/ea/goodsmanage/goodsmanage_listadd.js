$(document).ready(function() {

	$(".jqmWindow").jqm({
		modal : true,// 
		overlay : 20
	// 
	}).jqmAddClose('.close');//

	LiuZhongYaoDeShaGuaDiZhi();
	qingchu();

});

$(function() {	
	$('td.variables select[number]').change(function() {
		var number = $(this).attr("number");
		var num = parseInt(number) + 1;
		$("td.variables").find('select:gt(' + number + ')').attr("value", "");
		$("td.variables").find('input:gt(' + number + ')').val("");
		$("td.variables").children('span:gt(' + num + ')').hide();
		$("td.variables").children('span:eq(' + num + ')').show();
	});
	$("#shujufromtijiao").click(function() {
	});
	$("input.JQueryreturn2").click(function() {// 取消
		$("#JQueryaddress").jqmHide();
	});
	$("#inputTypeID")
			.click(
					function() {
						$("#browser").remove();
						// 账号所属的部门列表
						var searchurl = basePath
								+ "ea/goodsmanage/sajax_ea_AjaxgoodType.jspa?date="
								+ new Date().toLocaleString();
						$
								.ajax({
									url : searchurl,
									type : "get",
									async : true,
									dataType : "json",
									success : function cbf(data) {
										var member = eval("(" + data + ")");
										var nologin = member.nologin;
										if (nologin) {
											document.location.href = "<%=basePath%>page/ea/not_login.jsp";
										}
										var gotypelist = member.gotypelist;
										if (null != gotypelist) {
											var result = "<ul id='browser' class='filetree'>";
											for ( var i = 0; i < gotypelist.length; i++) {
												if (gotypelist[i].codeID == "scode20140919hqjwqgg8fg0000000038") {
													result += "<li id='"
															+ gotypelist[i].codeID
															+ "'><a><span class='folder' onclick='javascript:changeCoding(\""
															+ gotypelist[i].codeValue
															+ "\")'>("
															+ gotypelist[i].codeDesc
															+ ")"
															+ gotypelist[i].codeValue
															+ "</span></a></li>";
												} else {
													result += "<li id='"
															+ gotypelist[i].codeID
															+ "'><a ><span class='folder' onclick='javascript:changemenu(\""
															+ gotypelist[i].codeID
															+ "\")'>("
															+ gotypelist[i].codeDesc
															+ ")"
															+ gotypelist[i].codeValue
															+ "</span></a></li>";
												}

											}
											result += "</ul>";
											$(result).appendTo("#aadTree");
											$("#browser").treeview();
											browser = "";
										}
									},
									error : function cbf(data) {
										alert("数据获取失败！");
									}
								});
						$("#jqModelType").jqmShow();
					});

	// // 图片预览主题

//	$('#tupphoto')
//			.change(
//				function() {
//						$t = $("table#stafftable2");
//						alert("phot");
//					var url = basePath
//							+ "ea/company/ea_ajaxshangchuan.jspa?goodsManage.getFilePhoto="
//								+ this.value;
//						// AJAX 选择图片 上传给后台然后 显示在页面上
//						$.ajax({
//					url : encodeURI(url),
//							type : "get",
//							async : true,
//						dataType : "json",
//							success : function cbf(data) {
//								$("#dazhuti").attr("src",data).show();
//								$t.find('#zhuti').attr("src",data)
//										.show();
//									},
//
//							error : function cbf(data) {
//								alert("数据获取失败！");
//								}
//						}
//			);
//			});
	// 图片预览LOGO
// //$('#tuplogo')
		// .change(
		// function() {
			// $t = $("table#stafftable");
				// alert("logo");
			// // /// var url = basePath
			// // + "ea/company/ea_ajaxshangchuan.jspa?goodsManage.getFilelogo="
			// /// + this.value;
				// // AJAX 选择图片 上传给后台然后 显示在页面上
			// alert(this.value);
				// $
				// .ajax({
					// url : encodeURI(url),
				// /// type : "get",
				// async : true,
					// dataType : "json",
					// success : function cbf(data) {
					// $t.find('#logo')
						// .attr("src", this.value).show();
						// },

						// error : function cbf(data) {
						// alert("数据获取失败！");
						// / }
						// });

				// });
	// 图片浏览视频
// $('#tuppship').change(function() {
// $t = $("table#stafftable");
// $t.find('#ship').attr("src", this.value).show();
// });
});
// 选择 图片 上传图片 然后 显示选择路径
function chuan(name) {

	if (name == 'logo') {
		var b1 = $("#tuplogo");
		b1.click();
	} else if (name == 'zhuti') {
		var b1 = $("#tupphoto");
		b1.click();
	} else if (name == 'ship') {// 选择视频的时候
		var b1 = $("#tuppship");
		b1.click();
	} else if (name == 'tijiao') {

		var b1 = $("#qijiao");
		b1.click();
	} else if (name == 'qingchu') {
		var b1 = $("#qingchu");
		b1.click();
	}
}

// 行业分类窗口确定时 判断三个
function getAddress() {
	if ($("#province ").find("option:selected").text() == "--请选择--") {
		$("#province ").text("");
	}
	var province = $("#province ").find("option:selected").text();
	if ($("#city ").find("option:selected").text() == "--请选择--") {
		$("#city ").text("");
	}
	var city = $("#city ").find("option:selected").text();
	if ($("#county ").find("option:selected").text() == "--请选择--") {
		$("#county ").text("");
	}

	if (city == "") {
		alert("请选择2级菜单！");
		return;
	}
	$("#JQueryaddress").jqmHide();
	$("input#tradeCode").val(city);
};
// 选择行业ADB----的时候根绝获取的 名字查询这个下面的东西
function changemenu(codeID) {
	$("#browser2").remove();
	var searchurl = basePath
			+ "ea/goodsmanage/sajax_ea_AjaxgoodType.jspa?codepid=" + codeID
			+ "&date=" + new Date().toLocaleString();
	$
			.ajax({
				url : searchurl,
				type : "get",
				async : true,
				dataType : "json",
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var nologin = member.nologin;
					if (nologin) {
						document.location.href = "<%=basePath%>page/ea/not_login.jsp";
					}
					var gotypelist = member.gotypelist;
					if (null != gotypelist) {
						result = "<ul id='browser2' class='filetree'>";
						for ( var i = 0; i < gotypelist.length; i++) {
							result += "<li id='"
									+ gotypelist[i].codeID
									+ "'><a ><span class='file' onclick='javascript:changeCoding(\""
									+ gotypelist[i].codeValue + "\")'>("
									+ gotypelist[i].codeDesc + ")"
									+ gotypelist[i].codeValue
									+ "</span></a></li>";
						}
						result += "</ul>";
						$(result).appendTo("#text_tree");
						$("#browser2").treeview();
						browser2 = "";
					}
					;
				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}
			});
};
// 行业分类管理选择车辆时然后取消对车架号 的飞空验证
function changeCoding(codeValue) {
	// 如果选择的类别不是车辆则没有车架号/主机号的非空限制
	$("#isShow").hide();
	$("#isShows").hide();
	$("form#cstaffForm").find("span.error").remove();
	$("form#cstaffForm").find("input#mnemonicCode").attr("class",
			"mnemonicCode ckTextLength isremove");
	$("form#cstaffForm").find("input#vehicleBrand").attr("class",
			"vehicleBrand isremove");
	$("#isShowes").hide();
	$("#jqModel2").css("top", "34%").css("left", "14%");

	/*
	 * var delurl = basePath +
	 * "ea/goodsmanage/sajax_ea_SavdFileAjax.jspa?goodsManage.typeID="+codeValue+
	 * "&date=" + new Date().toLocaleString(); $.ajax({ url : encodeURI(delurl),
	 * type : "post", async : false, dataType : "json", success : function
	 * cbf(data) { var member = eval("(" + data + ")"); var nologin =
	 * member.nologin; if (nologin) { document.location.href = basePath +
	 * "page/ea/not_login.jsp"; } var goodsManage=member.goodsManage;
	 * $("input#goodsCoding","form#cstaffForm").attr("value",goodsManage); },
	 * error : function cbf(data) { notoken = 0; alert("数据获取失败！"); }
	 * 
	 * });
	 */
	$("#inputTypeID").val(codeValue);
	var cpr = codeValue.indexOf("车");
	if (cpr != -1) {
		carInformation();

	}
	$("#jqModelType").jqmHide();
}
// 添加小东西是弹出的窗口
function blue() {
	$("#JQueryaddress").jqmShow();
}

/** ******行业类别******* */
var PID = "";// 当点新曾时,上一级被选中项的id
var rovince = "";// 被改变的那个的id
var districtPID;
$(function() {
	$('td.JQueryaddress select[number]')
			.change(
					function() {

						if (retoken)
							return;
						retoken = 1;
						var province = this.id;
						var number = $(this).attr("number");
						$td = $("td.JQueryaddress");
						rovince = "#" + province;
						$('#newdistrict', $td).hide();
						$td.children('select:gt(' + number + ')').empty();
						$td.children('select:gt(' + number + ')').show();
						var D = $(rovince, $td).children("option:selected")
								.attr("class");
						if (D == 'add') {
							PID = $(rovince, $td).children("option:selected")
									.val();
							$('#districtNames').attr("title", number).attr(
									"value", "");
							$("#jqModel").jqmHide();
							$("#newdistrict").jqmShow();
							retoken = 0;
							return;
						}
						$($td).children('select:gt(' + number + ')').attr(
								"disabled", false);
						districtPID = $(rovince, $td).children(
								"option:selected").val();

						if (districtPID == '--请选择--') {
							if (number != "0") {
								var nu = parseInt(number) - 1;
								districtPID = $("select[number=" + nu + "]",
										$td).val();
							} else {
								districtPID = "";
							}
							$td.find('input#address').val(districtPID);
							retoken = 0;
							return;
						}
						var url = basePath
								+ "ea/contactcompany/sajax_ea_getCDistricts.jspa?districtPID="
								+ encodeURI(districtPID) + "&date3="
								+ new Date();
						$
								.ajax({
									url : url,
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
										var distinctlist = member.distinctlist;
										$select = "<option selected='selected'>--请选择--</option>";
										$(rovince, $td).next().append($select);
										if (distinctlist.length) {
											for ( var i = 0; i < distinctlist.length; i++) {
												$op = $("<option/>");
												$op
														.attr(
																"value",
																distinctlist[i].codeID)
														.attr(
																"id",
																distinctlist[i].codeID)
														.text(
																distinctlist[i].codeValue);
												$(rovince, $td).next().append(
														$op);
											}
										}

										$td.find('input#address').val(
												districtPID);
										retoken = 0;
									},
									error : function cbf(data) {
										retoken = 0;
										alert("数据获取失败！");

									}
								});
					});

});

function shujufromtijiao() {
   /*if($("#tradeCode").val()==null||$("#tradeCode").val()=="")
	{
	     alert("行业分类不能为空");
	     
	     return ;
	}*/
  /* else if($("#inputTypeID").val()==null||$("#inputTypeID").val()=="")
	 {
	   alert("物品类别管理不能为空");
	   return false;
	 }*/
    if($("#wupname").val()==null||$("#wupname").val()=="")
	 {
	   
	   alert("物品名称不能为空");
	   return false;
	 }
   else{	   
	   var b = true;
		var str = "";
		$("#goodsvariable").val("");
		$("td.variables").find('select').each(function() {
					if ($(this).attr("value") != "") {
						var seinput = $(this).parent().find("input").val();
						seinput.replace(/^(\s|\xA0)+|(\s|\xA0)+$/g, '');
						if (jQuery.trim(seinput) == "" || isNaN(seinput)) {
							b = false;
							return;
						} else {	
							if (this.id != "variableID") {
								str += "=" + seinput + $(this).attr("value");
							} else {
								str += seinput + $(this).attr("value");
							}
						}
					} else {
						$(this).parent().find("input").val("");
					}
				});	
		$(".put3").trigger("blur");
		$("#goodsvariable").val(str);
		$(".chips").trigger("blur");
		$("#shujufrom").attr("target", "hidden").attr("action",
				basePath + "ea/goodsmanage/ea_saveGoodsManage.jspa?");
	document.shujufrom.submit();	
	kong();	

	

   }

}
function kong()
{
//	CKEDITOR.instances.content.setData(' ');
//	CKEDITOR.instances.content1.setData(' ');
//	CKEDITOR.instances.content2.setData(' ');

	var myobject = $("textarea").each(function(sn,item){
		var id = item.id;
		eval("CKEDITOR.instances."+id+".setData(' ')");
	});
	
	var myobject = $("input");
	var mynum = myobject.length;
	for (i=0; i<mynum; i++){
	if(myobject[i].type == "text" || myobject[i].type == "file" || myobject[i].type == "hidden" &&myobject[i].id!=id&&myobject[i].id!=key&&myobject[i].id!=carKey&&myobject[i].id!="bigClass"&&myobject[i].id!="typeID"&&myobject[i].id!="tradeCode"){
			myobject[i].value="";
		}
	}
	
	var myobject = $("image");
	var mynum = myobject.length;
	for (i=0; i<mynum; i++){
	if(myobject[i].src != basePath+"/images/xiaozanwuxianshi.png"){
		myobject[i].src = basePath+"/images/xiaozanwuxianshi.png";	
		}
	} 
	
	var myobject = $("img");
	var mynum = myobject.length;
	for (i=0; i<mynum; i++){
	if(myobject[i].src != basePath+"/images/xiaozanwuxianshi.png"){
		myobject[i].src = basePath+"/images/xiaozanwuxianshi.png";	
		}
	}
	
}
// 添加小东西 单位 规格
function saveCCodes() {
	var codePID = $("#codePID").attr("value");
	var codeValue = $("#ccodevalue").attr("value");
	var selectID = $("#selectID").attr("value");
	var formID = $($("#formID").attr("value"));
	if ((codeValue == "") || (codeValue == null)) {
		alert("代码名称不能为空");
	} else {
		var url = basePath
				+ "ea/ccode/sajax_ea_saveCCodeByAjax.jspa?code.codePID="
				+ codePID + "&code.codeValue=" + codeValue + "&date="
				+ new Date();
		$
				.ajax({
					url : encodeURI(url),
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
						var alerts = member.alert;
						if (alerts != "") {
							// $("#wareName",$("#newccode")).alert("value","");
							notoken = 0;
							return;
						}
						var code = member.code;
						$("#newccode").jqmHide();
						$op = $("<option selected='selected'/>");
						$op.attr("value", code.codeValue).text(code.codeValue);
						if (selectID == "#variableID") {
							$(selectID, formID).parent().parent()
									.find("select").append($op);
						} else {
							$(selectID, formID).append($op);
						}
						// document.cstaffForm.reset();
						var delurl = basePath
								+ "ea/goodsmanage/sajax_ea_pinyinchang.jspa?goodsManage.typeID="
								+ codeValue + "&date="
								+ new Date().toLocaleString();
						$
								.ajax({
									url : encodeURI(delurl),
									type : "get",
									async : false,
									dataType : "json",
									success : function cbf(data) {
										var member = eval("(" + data + ")");
										var nologin = member.nologin;
										if (nologin) {
											document.location.href = basePath
													+ "page/ea/not_login.jsp";
										}
										var goodsManage = member.goodsManage;
										$("#cstaffForm").find(
												"input#goodsCoding").attr(
												"value",
												goodsManage + "-" + "1");
									},
									error : function cbf(data) {
										notoken = 0;
										alert("数据获取失败！");
									}
								});
						$(".jqmWindow", formID).jqmShow();
					},
					error : function cbf(data) {
						alert("数据获取失败！");
					}
				});
	}
}
// 清楚页面INPUT 所有东西
function qingchu() {

	$("#qingchu").click();

};
// 关闭页面
function closejsp() {
	$("#closejsp").click();
}
// 行业分类二级
function LiuZhongYaoDeShaGuaDiZhi() {
	// 非空验证还原
	$td = $("td.JQueryaddress");
	$td.children('select').empty();
	$select = "<option selected='selected'>--请选择--</option>";
	$("#province", $td).append($select);
	$td = $("td.JQueryaddress");
	$('td.JQueryaddress input[name=changes]').show();
	var url = basePath
			+ "/ea/contactcompany/sajax_ea_getCDistricts.jspa?districtPID=scode20110106hfjes5ucxp0000000003"
			+ "&date1=" + new Date();

	$.ajax({
		url : url,
		type : "get",
		async : true,
		dataType : "json",
		success : function cbf(data) {
			var member = eval("(" + data + ")");
			var nologin = member.nologin;
			if (nologin) {
				document.location.href = basePath + "page/ea/not_login.jsp";
			}
			var distinctlist = member.distinctlist;
			for ( var i = 0; i < distinctlist.length; i++) {
				$op = $("<option />");
				$op.attr("value", distinctlist[i].codeID).attr("id",
						distinctlist[i].codeID).text(
						distinctlist[i].codeDesc + ""
								+ distinctlist[i].codeValue);
				$("#province", $td).append($op);
			}
			retoken = 0;
		},
		error : function cbf(data) {
			retoken = 0;
			alert("数据获取失败！");
		}
	});
	return;
}
function carInformation() {
	$("#jqModel2").css("top", "19%").css("left", "9%");
	$("#isShow").show();
	$("#jqModelType").jqmHide();
	$("#isShows").show();
	$("#isShowes").show();
	if (goodsID != '') {
		var carurl = basePath
				+ "ea/goodsmanage/sajax_ea_getToCarinformation.jspa?carInformation.carFrameNum="
				+ chejiahao + "&carInformation.engineNum=" + zhubanhao;
		$.ajax({
			url : encodeURI(carurl),
			type : "get",
			async : true,
			dataType : "json",
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				var carmation = member.carmation;
				$("#carNum").val(carmation.carNum);
				$("#carType").val(carmation.carType);
				$("engineNum").val(carmation.engineNum);
				$("#buyDate").val(carmation.buyDate);
				$("conditions").val(carmation.conditions);
				$("companyName").val(carmation.companyName);
				$("driver").val(carmation.driver);
				$("#carPlace").val(carmation.carPlace);
				$("#brandModel").val(carmation.brandModel);
				$("#engineType").val(carmation.engineType);
				$("#containerInSize").val(carmation.containerInSize);
				$("#outerSize").val(carmation.outerSize);
				$("#driveType").val(carmation.driveType);
				$("#power").val(carmation.power);
				$("#fuelType").val(carmation.fuelType);
				$("#colorPaintNum").val(carmation.colorPaintNum);
				$("#vehicleBrand").val(carmation.vehicleBrand);
				$("#factoryName").val(carmation.factoryName);
				$("#tractionTotal").val(carmation.tractionTotal);
				$("#wheelTead").val(carmation.wheelTead);
				$("#ratifyPeople").val(carmation.ratifyPeople);
				$("#ratifyQuality").val(carmation.ratifyQuality);
				$("#domestic").val(carmation.domestic);
				$("#bridgePeople").val(carmation.bridgePeople);
				$("#springNum").val(carmation.springNum);
				$("#vehicleGet").val(carmation.vehicleGet);
				$("#useProp").val(carmation.useProp);
				$("#releaseDate").val(member.releaseDate);
				$("#operationDate").val(member.operationDate);
				$("#wheelbase").val(carmation.wheelbase);
				$("#kmFuel").val(carmation.kmFuel);
				$("#shaftNum").val(carmation.shaftNum);
				$("#tireNum").val(carmation.tireNum);
				$("#tireSpecifications").val(carmation.tireSpecifications);
				$("#serviceQuality").val(carmation.serviceQuality);
				$("#steeringType").val(carmation.steeringType);
				$("carPrice").val(carmation.carPrice);
				$("#registrationDate").val(carmation.registrationDate);
				$("#carKey").val(carmation.carKey);
				$("#carID").val(carmation.carID);
			},
			error : function cbf(data) {
				alert("数据获取失败！");
			}
		});
	}
}