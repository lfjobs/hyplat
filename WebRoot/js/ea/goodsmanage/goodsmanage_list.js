$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,
		overlay : 20
			// 
		}).jqmAddClose('.close');//
	// .jqDrag('.drag');//
	$('.JQueryflexme').flexigrid({
				height : 360,
				width : 'auto',
				minwidth : 30,
				title : '物品管理',
				minheight : 80,
				buttons : [{
					name : '添加',
					bclass : 'add',
					onpress : action
						//
					}, {
					separator : true
				}, {
					name : '发布',
					bclass : 'add',
					onpress : action
						//
					}, {
					separator : true
				}, {
					name : '删除',
					bclass : 'delete',
					onpress : action
						//
					}, {
					separator : true
				}, {
					name : '修改',
					bclass : 'edit',
					onpress : action
						//
					}, {
					separator : true
				}, {
					name : '导出',
					bclass : 'excel',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '查询',
					bclass : 'mysearch',
					onpress : action
						
				},
					{
				separator : true
			}
				,{  
					name:'查看',
					bclass: 'mysearch',
					onpress: action
					
				},{
					separator:true
				},
				{
					name : '打印条码',
					bclass : 'checkout',
					onpress : action
						//
					}, {
					separator : true
				}, {
					name : '设置每页显示条数',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '设置上级',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}]
			});
	function action(com, grid) {
		switch (com) {
			case '添加' :
			 	goodsID = "";
				$("td.variables", $("#cstaffForm")).children("span:gt(0)")
						.hide();
				$("input.JQuerypersonvalue").attr("checked", false);
				$t = $("div#jqModel");
				$t.find("img#showphoto").attr("src", "");
				$t.find("img#showlogo").attr("src", "");
				$(".isremove").val("");
				$("select#variableID").val("请选择");
				var cpr = $("#inputTypeID").val().indexOf("车");
				if(cpr != -1){
					carInformation();
				}
		    // $("#jqModel").jqmShow();
				var carurl = basePath+"ea/goodsmanage/ea_addGoodsManagejsp.jspa?iid="+iid;		
				window.open(carurl);
			break;		
			case '发布' :
				var carurl = basePath+"ea/babymanage/ea_getBabyPublishPage.jspa";				
				window.open(carurl);
			break;				
			case '修改' :				
				if (goodsID == "") {
					alert('请选择!');
					return;
				}
				var cN = $("tr#" + goodsID).find("span#companyName").text();
				if (cName != cN) {			
					alert("请登录‘" + cN + "’进行操作！");
					return;
				}		
			var carurl1 = basePath+"ea/goodsmanage/ea_addGoodsManagejsp.jspa?goodsID="+goodsID;
			window.open(carurl1);
// $("td.variables", $("#cstaffForm")).children("span:gt(0)")
// .hide();
// document.cstaffForm.reset();
// $t = $("div#jqModel");
// $p = $("tr#" + goodsID);
// $p.find("span[id]").each(function() {
// var spid = this.id;
// var b1 = spid.indexOf("num");
// var b2 = $(this).text();
// if ( b1 >= 0 && b2 != "") {
// $("td.variables", $("#cstaffForm"))
// .find("span#" + spid).show();
// }
// $t.find(":input[name]#" + this.id).val($(this).text());
// if (this.id == "standard") {
// $("select#standards").attr("value", $(this).text());
// }
// });
// $t.find("input#oldchipids").val($t.find("input#defaultStorage").val());
// var logo = $p.find("span#logoPath").text();
// if (logo.length != 0) {
// $t.find("img#showlogo").attr("src", basePath + logo);
// }
// var photo = $p.find("span#photoPath").text();
// if (photo.length != 0) {
// $t.find("img#showphoto").attr("src", basePath + photo);
// }
//				
// chejiahao = $("#" + goodsID).find("#manufacturers").text();
// zhubanhao = $("#" + goodsID).find("#mnemonicCode").text();
// //
// var carurl = basePath
// + "ea/goodsmanage/sajax_ea_getToSubject.jspa?goodsManage.goodsID=" + goodsID;
// $.ajax({
// url : encodeURI(carurl),
// type : "get",
// async : true,
// dataType : "json",
// success : function cbf(data) {
// var member = eval("(" + data + ")");
// // var carmation = member.carmation;
// $("#subjectsName").val(member.subjectsName);
// $("#subjectsID").val(member.subjectsID);
// },
// error : function cbf(data) {
// alert("数据获取失败！");
// }
// });
// if($("#inputTypeID").val().indexOf("车")!= -1){
// carInformation();
// }
// $("#jqModel").jqmShow();
			
				break;
			case '删除' :
				if (goodsID == "") {
					alert('请选择');
					return
				}
				var cN = $("tr#" + goodsID).find("span#companyName").text();
				if (cName != cN) {
					alert("不能删除不属于本登陆单位的物品！");
					return;
				}
				$f = $('#cstaffForm');
				$f.find(':input#goodsID').val(goodsID);
				if (confirm("确定删除？")) {
					$("#cstaffForm")
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/goodsmanage/ea_delGoodsManage.jspa?pageNumber="
											+ ppageNumber);
					document.cstaffForm.submit.click();
					$("tr#" + goodsID).remove();
					goodsID = "";
					token = 11;
				}
				break;
			case '查询' :
				$("#jqModelSearch2").jqmShow();
					break;	
			case '查看' :
			if(goodsID=="")
				{
				  alert('亲请选择一行');
				}else{
						var carurl = basePath+"ea/goodsmanage/ea_chaGoodsMangejsp.jspa?goodsID="+goodsID;			
						window.open(carurl);
				}
					break;		
			case '导出' :
				var url = basePath + "ea/goodsmanage/ea_showExcel.jspa?search="
						+ search;
				open(url);
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/goodsmanage/ea_getListGoodsManage.jspa?search="
						+ search;
				numback(url);
				break;
			case '打印条码': 
				if(goodsID==""){
                 	alert("请选择！");
                 	return;
                }
				var code = $("#" + goodsID).find("#goodsCoding").text(); 
				window.open(basePath + "ea/goodsmanage/ea_barcode.jspa?parameter=" + encodeURI(code));
			break;
			case '设置上级': 
				if(goodsID==""){
                 	alert("请选择！");
                 	return;
                }
				basic="004";
				$("#jqModelSearch3").jqmShow();
				break;
		}
	}

	$('td.variables select[number]').change(function() {
				var number = $(this).attr("number");
				var num = parseInt(number) + 1;
				$("td.variables").find('select:gt(' + number + ')').attr(
						"value", "");
				$("td.variables").find('input:gt(' + number + ')').val("");
				$("td.variables").children('span:gt(' + num + ')').hide();
				$("td.variables").children('span:eq(' + num + ')').show();
			});
	$(".JQueryflexme tr[id]").click(function() {
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				goodsID = this.id;
			});
	$(".JQueryflexme tr[id]").dblclick(function() {
				action('修改');
			});
	$("input.JQuerySubmit").click(function() {
		if($("#inputTypeID").val().indexOf("车")!= -1){
			$("input.mnemonicCode").addClass("put3");
			$("input.carType").addClass("put3");
			$("input.vehicleBrand").addClass("put3");
		}
		$(".put3").trigger("blur");
		$("#cstaffForm .chips").trigger("blur");
		if ($("form .error").length) {
			return;
		}
		var b = true;
		var str = "";
		$("td.variables").find('select').each(function() {
					if ($(this).attr("value") != "") {
						var seinput = $(this).parent().find("input").val();
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
		if (b == false) {
			alert("请填入正确的换算单位值！");
			return;
		}
		$("input#goodsvariable", $("#cstaffForm")).val(str);
		$("td.variables", $("#cstaffForm")).children("span:gt(0)").hide();
		if (goodsID == "") {
			$("#cstaffForm")
					.attr("target", "hidden")
					.attr(
							"action",
							basePath
									+ "ea/goodsmanage/ea_saveGoodsManage.jspa?pageNumber="
									+ ppageNumber);
			document.cstaffForm.submit.click();
			document.cstaffForm.reset();
			// journalNumAjax();
			token = 2;
			return;
		}
		$("#cstaffForm").attr("target", "hidden").attr(
				"action",
				basePath + "ea/goodsmanage/ea_saveGoodsManage.jspa?pageNumber="
						+ ppageNumber);
		document.cstaffForm.submit.click();
		
		token = 2;
		
	});
	$("#tosearch").click(function() {
		$("#SearchForm").attr(
				"action",basePath + "ea/goodsmanage/ea_toSearch.jspa?iid="+iid+"&pageNumber="+ ppageNumber + "&pageForm.pageNumber="
						+ $("#pageNumber").attr("value"));
		document.SearchForm.submit.click();
		$("#tosearch").attr("disabled", "disabled");
	});
	$("#toupdate").click(function() {
		var ids=tree1.getSelectedItemId();
		if(ids.length!=99){
			alert("请选择物品分类");
			return;
		}else{
			document.location.href = basePath + "/ea/goodsmanage/ea_toupdate.jspa?ids="+ids+"&goodsID="+goodsID+"&iid="+iid;
		}
	});
	$('#staffphoto').change(function() {
				$t = $("table#stafftable");
				$t.find('img#showphoto').attr("src", this.value).show();
			});
	$("input.JQueryreturn").click(function() {// 取消
		$("#isShow").hide();
		$("#jqModel").jqmHide();
		$("#jqModel2").css("top","34%").css("left","14%");
		re_load();
	});
	$("input.JQueryreturn2").click(function() {// 取消
		$("#JQueryaddress").jqmHide();
	});
	$(".close").click(function() {// 取消
		$("#isShow").hide();
		$("#jqModel").jqmHide();
		$("#jqModel2").css("top","34%").css("left","14%");
		re_load();
	});
	
	$("#cstaffForm").find("#inputTypeID").click(function() {
		$("#browser").remove();
      // 账号所属的部门列表
        var searchurl=  basePath +"ea/goodsmanage/sajax_ea_AjaxgoodType.jspa?date="+new Date().toLocaleString();
         $.ajax({
                 url: searchurl,
                 type: "get",
                 async: true,
                 dataType: "json",
                 success: function cbf(data){
			             var member = eval("(" + data + ")");
			              var nologin = member.nologin;
			              if(nologin){
			              document.location.href ="<%=basePath%>page/ea/not_login.jsp";
			              }
			             var gotypelist = member.gotypelist;
			             if (null != gotypelist) {
			            	   var result="<ul id='browser' class='filetree'>";
			                   for (var i = 0; i < gotypelist.length; i++) {
			                	   if(gotypelist[i].codeID=="scode20140919hqjwqgg8fg0000000038"){
			                		   result+="<li id='"+gotypelist[i].codeID+"'><a href='#'><span class='folder' onclick='javascript:changeCoding(\""+gotypelist[i].codeValue+"\")'>("+gotypelist[i].codeDesc+")"+gotypelist[i].codeValue+"</span></a></li>"; 
			                	   }else{
			                		   result+="<li id='"+gotypelist[i].codeID+"'><a href='#'><span class='folder' onclick='javascript:changemenu(\""+gotypelist[i].codeID+"\")'>("+gotypelist[i].codeDesc+")"+gotypelist[i].codeValue+"</span></a></li>";
			                	   }
			                	   
			                   }
			                   result+="</ul>";
			                   $(result).appendTo("#aadTree");
			                   $("#browser").treeview();
			                   browser="";
			             }
			        },
			     error: function cbf(data){
                        alert("数据获取失败！");
                     }
                 });
		$("#jqModelType").jqmShow();
	});
	
	$("#closes").click(function(){
		$("#jqModelType").jqmHide();
	});
	
	
	// 点击读取 芯片号
	$(".readchipid").click(function() {

		$("#cstaffForm #loadcab").attr(
				"src",
				basePath + "page/ea/common/loadActiveX.html?code="
						+ Math.random());

	});
	
	// 判断芯片重复用的事件
	$("form .chips").bind("blur", function() {
		$input = $(this);
		$parent = $input.parent();
		var inputValue = $input.attr("value");
		$parent.find(".error").remove();
		$parent.find(".corect").remove();
		$parent.find(".tooltip").remove();
		if ($input.is(".chips")) {
			if (inputValue != "") {
				if ($("#cstaffForm oldchipids").val() != inputValue) {
					if (!checkChip(inputValue)) {
						$parent
								.append("<span class=\"error\"><a class=\"tex\">芯片号已使用</a></span>");
						return;
					} else {
						$parent
								.append("<span class=\"corect\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>");
						return;
					}
				}
			}
	// 暂时注释掉，因为总部没芯片，暂时可以不用填写；以后为必填选项
// else {
// $parent
// .append("<span class=\"error\"><a
// class=\"tex\">\u4e0d\u80fd\u4e3a\u7a7a</a></span>");
// }
		}
	});
	
	
	
	/** ******行业类别******* */
	var PID="";// 当点新曾时,上一级被选中项的id
	var rovince="";// 被改变的那个的id
	var districtPID;
	$('td.JQueryaddress select[number]').change(function() {

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
		var D = $(rovince, $td).children("option:selected").attr("class");
		if (D == 'add') {
			PID = $(rovince, $td).children("option:selected").val();
			$('#districtNames').attr("title", number).attr("value", "");
			$("#jqModel").jqmHide();
			$("#newdistrict").jqmShow();
			retoken = 0;
			return;
		}
		$($td).children('select:gt(' + number + ')').attr("disabled", false);
		districtPID = $(rovince, $td).children("option:selected").val();	
		if (districtPID == '--请选择--') {
			if (number != "0") {
				var nu = parseInt(number) - 1;
				districtPID = $("select[number=" + nu + "]", $td).val();
			} else {
				districtPID = "";
			}
			$td.find('input#address').val(districtPID);
			retoken = 0;
			return;
		}
		var url = basePath
				+ "ea/contactcompany/sajax_ea_getCDistricts.jspa?districtPID="
				+ encodeURI(districtPID) + "&date3=" + new Date();
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
				$select = "<option selected='selected'>--请选择--</option>";
				$(rovince, $td).next().append($select);
				if (distinctlist.length) {
					for (var i = 0; i < distinctlist.length; i++) {
						$op = $("<option/>");
						$op.attr("value", distinctlist[i].codeID).attr(
								"id", distinctlist[i].codeID)
								.text(distinctlist[i].codeValue);
						$(rovince, $td).next().append($op);
					}
				}
				
				$td.find('input#address').val(districtPID);
				retoken = 0;
			},
			error : function cbf(data) {
				retoken = 0;
				alert("数据获取失败！");

			}
		});
	});	
	LiuZhongYaoDeShaGuaDiZhi();
});
function getAddress(){
	if($("#province ").find("option:selected").text()=="--请选择--"){
		$("#province ").text("");
		}
	 var province=$("#province ").find("option:selected").text();
	 if($("#city ").find("option:selected").text()=="--请选择--"){
		 $("#city ").text("");
		 }
	 var city=$("#city ").find("option:selected").text();
	 if($("#county ").find("option:selected").text()=="--请选择--"){
		 $("#county ").text("");
		 }

	 if(city == ""){
		 alert("请选择2级菜单！");
		return;
	 }
	$("#JQueryaddress").jqmHide();
	$("#cstaffForm").find("input#tradeCode").val(city);
}
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
						document.location.href = basePath
								+ "page/ea/not_login.jsp";
					}
					var distinctlist = member.distinctlist;
					for (var i = 0; i < distinctlist.length; i++) {
						$op = $("<option />");
						$op.attr("value", distinctlist[i].codeID)
								.attr("id", distinctlist[i].codeID)
								.text(distinctlist[i].codeDesc+""+distinctlist[i].codeValue);
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

// 开始读芯片号
function readChiping(values) {
	$("#cstaffForm #defaultStorage").val(values);
}

// 判断芯片号是否重复
function checkChip(chipid) {
	if ($("#cstaffForm #oldchipids").val() == chipid) {
		return true;
	}
	var bool = null;
	var url = basePath + "ea/goodsmanage/sajax_ea_IsChipRepeat.jspa?date="
			+ new Date();
	$.ajax({
				url : url,
				type : "get",
				async : false,
				dataType : "json",
				data : {
					chipid : chipid
				},
				success : function(data) {
					var member = eval("(" + data + ")");
					var result = member.result;
					if (result == "fail") {// 重复
						bool = false;
					} else {
						bool = true;// 不重复
					}

				},
				error : function(data) {
					alert("读取数据失败");
				}

			});

	return bool;
}
function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/goodsmanage/ea_getListGoodsManage.jspa?pageNumber="
				+ ppageNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value") + "&search=" + search;
}
$(document).ready(function() {
	// 图片预览
	$('#filePhoto').change(function() {
				$t = $("table#stafftable");
				$t.find('img#showphoto').attr("src", this.value).show();
			});
		// 图片预览END
	$('#fileLogo').change(function() {
		$t = $("table#stafftable");
		$t.find('img#showlogo').attr("src", this.value).show();
	});
// 图片预览END
	});
	
$(function() {
 
			var url = basePath
					+ "ea/company/sajax_n_ea_getCompanyList.jspa?companyIdentifier="
					+ cID + "&date=" + new Date().toLocaleString();
			$.ajax({
						url : encodeURI(url),
						type : "get",
						async : true,
						dataType : "json",
						success : function cbf(data) {
							var member = eval("(" + data + ")");
							var companylist = member.companylist;
							var data1 = new Array();
							data1[0] = {
								id : cID,
								pid : '-1',
								text : pName
							};
							for (var i = 0; i < companylist.length; i++) {
								data1[i + 1] = {
									id : companylist[i].companyID,
									pid : companylist[i].companyPID,
									text : companylist[i].companyName
								};
							}

							var ts3 = new TreeSelector(
									$("select#companyID")[0], data1, -1);
							ts3.createTree();
						},

						error : function cbf(data) {
							alert("数据获取失败！");
						}
					});

		});

function changemenu(codeID){
	$("#browser2").remove();
	 var searchurl=  basePath +"ea/goodsmanage/sajax_ea_AjaxgoodType.jspa?codepid="+codeID+"&date="+new Date().toLocaleString();
   $.ajax({
           url: searchurl,
           type: "get",
           async: true,
           dataType: "json",
           success: function cbf(data){
		             var member = eval("(" + data + ")");
		              var nologin = member.nologin;
		              if(nologin){
		              document.location.href ="<%=basePath%>page/ea/not_login.jsp";
		              }
		             var gotypelist = member.gotypelist;
		             if (null != gotypelist) {
		            	   result="<ul id='browser2' class='filetree'>";
		                   for (var i = 0; i < gotypelist.length; i++) {
		                	   result+="<li id='"+gotypelist[i].codeID+"'><a href='#'><span class='file' onclick='javascript:changeCoding(\""+gotypelist[i].codeValue+"\")'>("+gotypelist[i].codeDesc+")"+gotypelist[i].codeValue+"</span></a></li>";
		                   }
		                   result+="</ul>";
		                   $(result).appendTo("#text_tree");
		                   $("#browser2").treeview();
		                   browser2="";
		             };
		        },
		     error: function cbf(data){
                  alert("数据获取失败！");
            }
           });
}

function changeCoding(codeValue){
	// 如果选择的类别不是车辆则没有车架号/主机号的非空限制
	$("#isShow").hide();
	$("#isShows").hide();
	$("form#cstaffForm").find("span.error").remove();
	$("form#cstaffForm").find("input#mnemonicCode").attr("class","mnemonicCode ckTextLength isremove");
	$("form#cstaffForm").find("input#vehicleBrand").attr("class","vehicleBrand isremove");
	$("#isShowes").hide();
	$("#jqModel2").css("top","34%").css("left","14%");
	
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
	if(cpr != -1){
		carInformation();
	}
	$("#jqModelType").jqmHide();
}

// 获取车辆信息
function carInformation(){
	$("#jqModel2").css("top","19%").css("left","9%");
	$("#isShow").show();
	$("#isShows").show();
	$("#isShowes").show();
	if(goodsID != ''){
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

/** 对类型进行添加保存 */	
 function saveCCodes(){
  var codePID =  $("#codePID").attr("value");
  var codeValue = $("#ccodevalue").attr("value");
   var selectID = $("#selectID").attr("value");
   var formID = $($("#formID").attr("value"));
  if((codeValue=="")||(codeValue==null)){
  	alert("代码名称不能为空");
  }else{
  var url =  basePath+"ea/ccode/sajax_ea_saveCCodeByAjax.jspa?code.codePID="+codePID+"&code.codeValue="+codeValue+"&date="+new Date();
        $.ajax({
						                        url: encodeURI(url),
						                        type: "get",
						                        async: true,
						                        dataType: "json",
						                        success: function cbf(data){
						                         var member = eval("(" + data + ")");
						                         var nologin = member.nologin;
								                  if(nologin){
								                  document.location.href =basePath+"page/ea/not_login.jsp";
								                  }
								                 var alerts=member.alert;
						                         if(alerts != ""){
						                         	// $("#wareName",$("#newccode")).alert("value","");
						                         	notoken=0;
						                         	return;
						                         }
											      var code = member.code;
											      $("#newccode").jqmHide();
											      $op = $("<option selected='selected'/>");
											      $op.attr("value",code.codeValue).text(code.codeValue);
											      if(selectID == "#variableID"){
											        $(selectID,formID).parent().parent().find("select").append($op);
											      }else{
											        $(selectID,formID).append($op);
											      }
											      // document.cstaffForm.reset();
											      var delurl = basePath
						+ "ea/goodsmanage/sajax_ea_pinyinchang.jspa?goodsManage.typeID="+codeValue+ "&date=" + new Date().toLocaleString();
					$.ajax({
						url : encodeURI(delurl),
						type : "get",
						async : false,
						dataType : "json",
						success : function cbf(data) {
							var member = eval("(" + data + ")");
							var nologin = member.nologin;
							if (nologin) {
								document.location.href = basePath + "page/ea/not_login.jsp";
							}
						var goodsManage=member.goodsManage;
						$("#cstaffForm").find("input#goodsCoding").attr("value", goodsManage+ "-"+"1");
						},
						error : function cbf(data) {
				notoken = 0;
				alert("数据获取失败！");
			}
					});
											      $(".jqmWindow",formID).jqmShow();
						                        },
						                        error: function cbf(data){
						                           alert("数据获取失败！");
						                        }
						                    });
}
}
/*$(function() {
	tree=new dhtmlXTreeObject("jqModel2","100%","100%",0);
    tree.enableDragAndDrop(false);
    tree.enableHighlighting(1);
    tree.enableCheckBoxes(0);
	tree.enableThreeStateCheckboxes(false);
	tree.setSkin(basePath+'js/tree/dhx_skyblue');
	tree.setImagePath(basePath+"js/tree/codebase/imgs/");
	tree.loadXML(basePath+"js/tree/common/tree_b.xml");
	tree.insertNewChild("0","002","科目树",0,0,0,0);
	tree.setOnClickHandler(function(){
        $(".input01").attr("value","");
        $("#desc").attr("html","");
        treeid= tree.getSelectedItemId();
        treename = tree.getItemText(treeid);
        subjectsNumbers=tree.getUserData(treeid,"subjectsNumbers");
        $("#codeName").attr("value",treename);
        $("input#subjectsName").attr("value",treename);
        tree.deleteChildItems(treeid);
        var getListCSbjectsurl=basePath+"ea/csbjects/sajax_ea_getListCsubejstsByPID.jspa?subjectsID="+treeid+"&date="+new Date().toLocaleString();
    	$.ajax({
                    url: encodeURI(getListCSbjectsurl),
                    type: "get",
                    async: true,
                    dataType: "json",
                    success: function cbf(data){
		            	var member = eval("("+data+")");
		            	var nologin = member.nologin;
                		if(nologin){
                		document.location.href =basePath+"page/ea/not_login.jsp";
                    	}
			            var subjectsList = member.subjectsList;
			            if(null == subjectsList){
			                return;
			            }    
			            for(var i=0;i<subjectsList.length;i++){
			                tree.insertNewChild(treeid,subjectsList[i].subjectsID,subjectsList[i].subjectsName,0,0,0,0);
				            tree.setUserData(subjectsList[i].subjectsID,"subjectsStatus",subjectsList[i].subjectsStatus);
				            tree.setUserData(subjectsList[i].subjectsID,"subjectsNumbers",subjectsList[i].subjectsNumbers);
			            }
             		},
                    error: function cbf(data){
                        alert("数据获取失败！");
                    }
        });
	});
	tree.setOnDblClickHandler(function(){
		$("#jqModel2").hide();
	});
	tree.setOnDblClickHandler(function(){
					$("#subjectsID").attr("value",subjectsNumbers);
        			$("#subjectsName").attr("value",treename);
					$("#jqMode2").hide();
	
	});
});*/
function blue(){
	$("#JQueryaddress").jqmShow();
}
// 地域调查
$(document).ready(function() {
	
	
		
	
});

$(document).ready(function(){   
    tree1=new dhtmlXTreeObject("aadTree1","100%","100%",0);
		    tree1.enableDragAndDrop(false);
		    tree1.enableHighlighting(1);
	        tree1.enableCheckBoxes(0);
			tree1.enableThreeStateCheckboxes(false);
			tree1.setSkin(basePath+'js/tree/dhx_skyblue');
			tree1.setImagePath(basePath+"js/tree/codebase/imgs/");
			tree1.loadXML(basePath+"js/tree/common/tree_b.xml");
			if(basic=='scode20150501kze3xkwxgv0000000006'){
				tree1.insertNewChild("0","scode20150501kze3xkwxgv0000000006","代码元素管理",0,0,0,0);
				tree1.insertNewChild("scode20150501kze3xkwxgv0000000006","scode20141029rnzhjs7ap60000000005","人事代码元素",0,0,0,0);
				tree1.insertNewChild("scode20150501kze3xkwxgv0000000006","scode20141029rnzhjs7ap60000000006","办公室代码元素",0,0,0,0);
				tree1.insertNewChild("scode20150501kze3xkwxgv0000000006","scode20141029rnzhjs7ap60000000026","财务代码元素",0,0,0,0);
				tree1.insertNewChild("scode20150501kze3xkwxgv0000000006","scode20141029rnzhjs7ap60000000027","生产代码元素",0,0,0,0);
				tree1.insertNewChild("scode20150501kze3xkwxgv0000000006","scode20141029rnzhjs7ap60000000028","营销代码元素",0,0,0,0);
			}else if(basic=='004'){
				/*tree.insertNewChild("0",basic,"行业类别",0,0,0,0);*/
				tree1.insertNewChild("0",basic,"物品元素管理",0,0,0,0);
			}else{
				tree1.insertNewChild("0",basic,basicMap[basic],0,0,0,0);
			}
			tree1.setOnClickHandler(function(){
				$("#mainframe").contents().empty();
				treeid= tree1.getSelectedItemId();
				 if(treeid.length>33){
		        	  iids=treeid.substring("33",treeid.length);
		        	  treeid=treeid.substring("0","33");
		          }else if(treeid.length==33){
		        	  iids="";
		          }
				treename = tree1.getItemText(treeid+iids);
               $("#codeName").attr("value",treename);
               var li = tree1.getAllSubItems(tree1.getParentId(treeid+iids));
	              var ll=li.split(",");
               for(var z=0;z<ll.length;z++){
              	 tree1.deleteChildItems(ll[z]);
               }
			         
		  	     var getListCCodeurl=basePath+"ea/ccode/sajax_ea_getListCCodeByPID.jspa?codeID="+treeid+"&date="+new Date().toLocaleString();
		         $.ajax({
		                        url: encodeURI(getListCCodeurl),
		                        type: "get",
		                        async: true,
		                        dataType: "json",
		                        success: function cbf(data){
		                           var member = eval("("+data+")");
		                           var nologin = member.nologin;
				                  if(nologin){
				                  document.location.href =basePath+"page/ea/not_login.jsp";
				                  }
						           var codeList = member.codeList;
						           if(null == codeList){
						              return;
						           }    
						            for(var i=0;i<codeList.length;i++){
						             tree1.insertNewChild(treeid+iids,codeList[i].codeID+iids, codeList[i].codeSn+codeList[i].codeValue,0,0,0,0);
						             tree1.setUserData(codeList[i].codeID+iids,"groupSn",codeList[i].groupSn);
						           }
						            if(treeid=="scode20141029rnzhjs7ap60000000006"){//判断办公室基础数据时
										tree1.insertNewChild("scode20141029rnzhjs7ap60000000006","002","库存编码树",0,0,0,0);
									}
						            if(treeid=='scode20150817mqy3awyt3t0000000013' || treeid=='scode20150817mqy3awyt3t0000000012' || treeid=='scode20150817mqy3awyt3t0000000011' || 
						               treeid=='scode20150817mqy3awyt3t0000000010' || treeid=='scode20150817mqy3awyt3t0000000009'){
						            	tree1.deleteChildItems("scode20150817mqy3awyt3t0000000012");
						            	tree1.deleteChildItems("scode20150817mqy3awyt3t0000000013");
						            	tree1.deleteChildItems("scode20150817mqy3awyt3t0000000011");
						            	tree1.deleteChildItems("scode20150817mqy3awyt3t0000000010");
						            	tree1.deleteChildItems("scode20150817mqy3awyt3t0000000009");
						            	tree1.insertNewChild(treeid,"scode20150815wygb79q82p0000000005"+treeid,"行业类别",0,0,0,0);
						            }
						            if(tree1.getUserData(treeid+iids, "groupSn")==codeHylb && codeList.length==0){
						            	tree1.insertNewChild(treeid+iids,"scode20101014v5zed7cukk0000000002"+treeid+iids,"物品类别",0,0,0,0);
						            }
		                        },
		                        error: function cbf(data){
		                           alert("数据获取失败！");
		                        }
		                    });
			});		 	
});
 	 
