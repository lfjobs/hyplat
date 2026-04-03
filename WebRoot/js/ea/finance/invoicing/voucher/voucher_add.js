$(function() {
	// 为弹出框准备下拉表内容
	$(".jqmWindow").jqm({
		modal : true,// 
		overlay : 20
		}).jqmAddClose('.close');
		$("span#gongsi").text(companynames);
		$("span#bumen").text(deptNames);
			$("#xitong").text(myDate.toString());
		//添加页面返回
	$(".JQueryClose").click(function() {
		notoken = 0;
		re_load();
	});
		//科目取消
	$(".JQueryreturns").click(function() { 
		notoken = 0;
 		$(".jqmWindow").jqmHide();
	 });
		//添加保存
	$("input.JQuerySubmitgd").click(function(){
		var ss=0;
		$(".panduan", $("tr.checkgoods")).each(function(){
			if($(this).val() ==""){
				ss+=1;
			}
		});
		if(ss>0){
			alert("请填完所有必填项！");
			return; 
		}
		if($("#makeDate").val()==""){
			alert("请填写制单日期！");
			return; 
		}
		$("#voucheraddForm").attr("target", "hidden").attr(
				"action",basePath + "/ea/voucher/ea_save.jspa?aa="+aa+"&voucher.makeDate="+$("input#makeDate").val());
		document.voucheraddForm.submit.click();
		token = 2;
	});
	
/** **************************************科目管理******************************************* */
	var subjectsNumber = "";
	var subjectsName = "";
	var endnumber = 0;
	showDiv();
	$("#savesubjects").click(function() {
		if (subjectsName != "--请选择--") {
			$("#subjectsID", $(".receivesubjects")).attr("value",subjectsnumber);
			$("#subjectsName", $(".receivesubjects")).attr("value",subjectsName);
		} else {
			$("#subjectsID", $(".receivesubjects")).attr("value", "");
			$("#subjectsName", $(".receivesubjects")).attr("value", "");
		}
		$("#voucheraddForm").find("td.receivesubjects").removeClass("receivesubjects");
		notoken = 0;
		$("#selectsubjects").jqmHide();
	});

	// 科目管理
	$('td.subjects select[number]').change(function() {
		if (notoken) {
			alert("正在获取数据！请稍等");
			return;
		}
		notoken = 1;
		var num = $(this).attr("number");
		var number = parseInt(num) + 1;
		$td = $("td.subjects");
		$td.children('select:gt(' + num + ')').empty();
		var subjectsPID = $td.children('select:eq(' + num + ')').children("option:selected").attr("id");
		subjectsnumber = $td.children('select:eq(' + num + ')').children("option:selected").val();
		subjectsName = $td.children('select:eq(' + num + ')').children("option:selected").text();
		if (subjectsnumber == "") {
			var numbers = parseInt(num) - 1;
			subjectsnumber = $td.children('select:eq(' + numbers + ')').children("option:selected").val();
			subjectsName = $td.children('select:eq(' + numbers + ')').children("option:selected").text();
			notoken = 0;
			return;
		}
		
	});
});

function showDiv(){
	$(".tosubjects").click(function() {
		$(this).parent().parent().find("td").addClass("receivesubjects");
		$td = $("td.subjects");
		$td.children('select').empty();
		var subRuleurl = basePath+ "/ea/csubjectsrule/sajax_n_ea_getCSubjectsRule.jspa?date="+ new Date().toLocaleString();
		var subRule = new Array();
		var endnumber = 0;
		$.ajax({
			url : encodeURI(subRuleurl),
			type : "get",
			async : true,
			dataType : "json",
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				var nologin = member.nologin;
				if (nologin) {
					document.location.href = basePath + "page/ea/not_login.jsp";
				}
				subRule = (member.subRule.rules).split(",");
			},
			error : function cbf(data) {
				alert("数据获取失败！");
			}
		});
		var subjecturl = basePath+ "ea/voucher/sajax_ea_getListCsubejstsByPID.jspa?";
		subjectsNumber = "";
		subjectsName = "";
		$.ajax({
			url : encodeURI(subjecturl + "date="+ new Date().toLocaleString()),
			type : "get",
			async : true,
			dataType : "json",
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				var nologin = member.nologin;
				if (nologin) {
					document.location.href = basePath + "page/ea/not_login.jsp";
				}
				var subjectsList = member.subjectsList;
				$td = $("td.subjects");
				$select = "<option selected='selected' value = ''>--请选择--</option>";
				$td.children('select:eq(0)').append($select);
				for (var i = 0; i < subjectsList.length; i++) {
					$op = $("<option />");
					$op.attr("value", subjectsList[i].subjectsNumbers).attr("id", subjectsList[i].subjectsID).text(subjectsList[i].subjectsName);
					$td.children('select:eq(0)').append($op);
				}
				$td.children('select:eq(0)').show();
				endnumber += parseInt(subRule[0]);
				if (subjectsNumber.substring(0, subRule[0]) != "") {
					$td.children('select:eq(0)').attr("value",subjectsNumber.substring(0, subRule[0]));
					$td.children('select:eq(0)').trigger("change");
				}
				$("#selectsubjects").jqmShow();
			},
			error : function cbf(data) {
				alert("数据获取失败！");
			}
		});

	});
	}
function re_load() {
	document.location.href = basePath+ "ea/voucher/ea_getVoucherList.jspa?pageNumber="+ pNumber + "&pageForm.pageNumber="+ $("#pageNumber").attr("value")+"&aa="+aa;
}

/** **********************************选择票据**************************************** */
$(document).ready(function() {
	$("table#gotable tr[id]").live("click", function(event) {
		var b = $("input.rauser", $(this)).attr("checked");
		$("input.rauser", $(this)).attr("checked", !b);
	});
	// 复选框选中物品
	$(".rauser").live("click", function(event) {
		var b = $(this).attr("checked");
		$(this).attr("checked", !b);
	});
	// 导入数据（选择物品）
	$("#chouse").click(function() {
		$(".jqmWindowcss1").jqmShow();
	});
	//关闭
	$(".JQueryreturnbill").click(function() {
				notoken = 0;
				var numes="";
				$("#journalNum", $("table#searchgood")).attr("value",numes);
				$(":input#parms").attr("value",numes);
				$(".jqmWindowcss1").jqmHide();
			});
	// 根据编号查询
	$("input#chaxun").click(function() {
				var typeType=$("#journalNum", $("table#searchgood")).val();
				if(typeType==""){
					alert("黏贴单编号不能为空");
					return;
				}
				jnumber=typeType;
				$(":input#parms").val("&typeType="+typeType);
				cx("typeType="+typeType);
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
			})	;
	//确定复制
	$("#qdbill").click(function() {
		$("input[name='check']").attr("checked",true);
		if ($("[name='check']").is(':checked')) {
			$("input[name='check']").each(function() {
				if ($(this).is(':checked')) {
					select++;
					selects+=2;
					docNull+=1;
					$("#kelong").before(
					($("#kelong").clone(true).attr("id","kelong" + select).addClass("checkgoods")).clone()
					);
					$("#kelong"+ select).after(
					($("#kelong"+ select).clone(true).attr("id","kelong1" + select).addClass("checkgoods")).clone()
					);
					$("span#numbers",$("#kelong" + select)).text(selects-1);
					$("#kelong" + select).find(':input').each(function() {$(this).attr("name","logbookmap[" + (selects-1) + "]." + this.name);
					});
					$("span#numbers",$("#kelong1" + select)).text(selects);
					$("#kelong1" + select).find(':input').each(function() {$(this).attr("name","logbookmap[" + selects + "]." + this.name);
					});
					$op = $("<option value='' selected>请选择</option>");
					$("tr #" + $(this).val()).children("td").each(function() {
						if (this.id == "cashierBillsID") {
							$("input#cashierBillsID", $("#kelong" + select)).attr("value", $(this).text()).removeClass();
								$("input#cashierBillsID", $("#kelong1" + select)).attr("value", $(this).text()).removeClass();
						}else if (this.id == "subjectsName") {
							$("input#subjectsName", $("#kelong1" + select)).attr("value", $(this).text());
						} else if (this.id == "subjectsID") {
							$("input#subjectsID", $("#kelong1" + select)).attr("value", $(this).text());
						}else if(this.id == "loan"){
							$("input#loan", $("#kelong" + select)).attr("value", $(this).text()).removeClass();
							$("input#forLoan", $("#kelong1" + select)).attr("value", $(this).text()).removeClass();
						}else if(this.id == "forLoan"){
							$("input#forLoan", $("#kelong" + select)).attr("value", $(this).text()).removeClass();
							$("input#loan", $("#kelong1" + select)).attr("value", $(this).text()).removeClass();
						}else if(this.id == "reasonThing"){
							$("input#reasonThing", $("#kelong" + select)).attr("value", $(this).text()).removeClass();
						}else if(this.id == "subID"){
							$("input#subjectsID", $("#kelong" + select)).attr("value", $(this).text()).removeClass();
						}else if(this.id == "subName"){
							$("input#subjectsName", $("#kelong" + select)).attr("value", $(this).text()).removeClass();
							$(".tosubjects", $("#kelong" + select)).hide();
						}else if(this.id == "goodsID"){
							$("input#goodsID", $("#kelong" + select)).attr("value", $(this).text()).removeClass();
							$("input#goodsID", $("#kelong1" + select)).attr("value", $(this).text()).removeClass();
						}else if(this.id == "goodsBillsID"){
							$("input#goodsBillsID", $("#kelong" + select)).attr("value", $(this).text()).removeClass();
							$("input#goodsBillsID", $("#kelong1" + select)).attr("value", $(this).text()).removeClass();
						}else {
							$("span#" + this.id, $("#kelong" + select)).text($(this).text());							
						}
					});
					$("tr[id!='kelong']").show();
					$(this).attr("checked", false);
				}	
			});
			showDiv();
			$(".jqmWindowcss1").jqmHide();
			//用于判断科目是否为末级
			var subRuleurl = basePath+ "ea/voucher/sajax_ea_getsubm.jspa?date="+ new Date().toLocaleString()+"&typeType="+jnumber;
			$.ajax({
					url : encodeURI(subRuleurl),
					type : "get",
					async : true,
					dataType : "json",
					success : function cbf(data) {
						var member = eval("(" + data + ")");
						var nologin = member.nologin;
						if (nologin) {
							document.location.href = basePath+ "page/ea/not_login.jsp";
						}
						var counts = member.counts;
						if(parseInt(counts)>0){
							alert("请选择末级科目并设置初期余额！");
							notoken = 0;
							re_load();
						}
					},
					error : function cbf(data) {
						alert("数据获取失败！");
					}
					});
		} 
		else {
			alert("请选择票据！");
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
		var searchurl = basePath+ "ea/voucher/sajax_ea_getbill.jspa?";
		$.ajax({
			url : encodeURI(searchurl+ typeCN+ "&date="+ new Date().toLocaleString()),
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
				var counts=member.counts;
				if(counts=="01"){
					alert("该票据已添加！");
					notoken = 0;
					return;
				}else{
					if (pageForm == null) {
					alert("没有数据");
					notoken = 0;
					return;
					}
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
				var tabletr = "<table width='98%' height='26' align='center' cellspacing='0' cellpadding='1' style='font-size:12px;' class='bannb_01'><tr><td height='24' align='left' valign='top' class='txt01' >&nbsp;点击选择票据</td></tr></table>";
				tabletr += "<table width='99%' align='center' id='gotable' cellpadding='0' cellspacing='0' class='table'>";
				tabletr += " <tr ><th align='center' bgcolor='#E4F1FA'>单据类别</th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'>黏贴单编号</th><th align='center' bgcolor='#E4F1FA'>物品类型</th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'>物品编号</th><th align='center' bgcolor='#E4F1FA'>科目</th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'>借方金额</th><th align='center' bgcolor='#E4F1FA'>贷方金额</th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'>摘要</th><th align='center' bgcolor='#E4F1FA'>方向</th></tr>";
				for (var i = 0; i < pageForm.list.length; i++) {
					tabletr += "<tr style='cursor: hand;' id = "
							+ pageForm.list[i].goodsBillsID + ">";
					tabletr += "<td id='check' align='center' style='display:none'><input type ='checkbox' class='rauser' value="
							+ pageForm.list[i].goodsBillsID + " name='check'/></td>";
					tabletr += "<td id='billsType' align='center'>"
							+ pageForm.list[i].billsType + "</td>";
					tabletr += "<td id='journalNum'  align='center'>"
							+ pageForm.list[i].journalNum + "</td>";
					tabletr += "<td id='typeID'  align='center'>"
							+ pageForm.list[i].typeID + "</td>";
					tabletr += "<td id='goodsCoding' align='center'>"
							+ pageForm.list[i].goodsCoding + "</td>";	
					tabletr += "<td id='subName' align='center'>"
							+ pageForm.list[i].subjectsName + "</td>";
					tabletr += "<td id='subID' style='display:none' align='center'>"
							+ pageForm.list[i].subjectsID + "</td>";	
					tabletr += "<td id='goodsID'style='display:none' align='center'>"
							+ pageForm.list[i].goodsID + "</td>";	
					tabletr += "<td id='goodsBillsID' style='display:none' align='center'>"
							+ pageForm.list[i].goodsBillsID + "</td>";	
					tabletr += "<td id='loan' align='center'>"
							+ pageForm.list[i].loan + "</td>";	
					tabletr += "<td id='forLoan' align='center'>"
							+ pageForm.list[i].forLoan + "</td>";	
					tabletr += "<td id='reasonThing' align='center'>"
							+ pageForm.list[i].reasonThing + "</td>";	
					tabletr += "<td id='direction' align='center'>"
							+ pageForm.list[i].direction + "</td>";	
					tabletr += "<td id='cashierBillsID'  style='display:none' align='center'>"
							+ pageForm.list[i].cashierBillsID + "</td>";
					tabletr += " </tr>";
				}
				tabletr += "</table>";
				$("#body_02").html(tabletr);
				$("#body_02").show();
				notoken = 0;
				$("input#journalNum").val("");
				window.status = "数据加载成功";
			},
			error : function cbf(data) {
				notoken = 0;
				alert("数据获取失败！");
			}
		});
	}

});