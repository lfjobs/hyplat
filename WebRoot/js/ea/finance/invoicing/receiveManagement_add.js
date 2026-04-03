// ajax查询物品通过芯片
var chipids = new Array();
var i = 0;

$(function() { 
	$(".telephone").keydown(function(e){
		if(e.keyCode==8){
			return;
		}
		var tele=$(this).val();
		if(tele.length==15){
			return false;
		}
		})
	//-----------------单据审核---------------------
	$(".btncon").click(function(){
		var cashierBillsID=$("#cashierBillsID").val();
		var staffname=$("#staffauditname").val();
		var staffid=$("#staffauditid").val();
		var remark=$("#remark").val();
		var id=$(this).attr("id");
		var subRuleurl = basePath
				+ "ea/allocation/sajax_ea_saveBillCheck.jspa?" 
				+"cashierBills.cashierBillsID="+cashierBillsID
				+"&deptpost="+id+"&remarks="+remark;
				+"&date="+ new Date().toLocaleString();
		 if(confirm("确定审核？")){
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
				loginstaffcheck = member.loginstaff;
			},
			error : function cbf(data) {
				alert("数据获取失败！");
			}
		   });
		    $("input."+id).val(staffname)
		    $("input#"+id).css("display","none")
		 }
		
	});
	//初始化加载总价
	    var heji=0;
		$("input#money", $("#goodtable")).each(function(){
			var s=$(this).val();
			var x=parseFloat(s);
			if(!isNaN(x)){
			heji=heji+x
			}
		});
		DX(heji);
		$("span#countmoney").text(heji);
		$("input#countmoney").val(heji);
	//物品选择关闭
	$(".jqmWindow").jqm({
		modal : true,// 
		overlay : 20
			// 
		}).jqmAddClose('.close');
	//选择仓库关闭
	$(".JQueryreturns").click(function() {
				notoken = 0;
				$(".jqmWindow").jqmHide();
				//loadcab.window.closePort();// 关闭读数据端口
				chipids = new Array();
                i = 0;
			});
   
// --------------------------------计算金额合计-------------------------
	$(".jisuan").live("keyup", function(event) {
		if (this.value != "") {
			if (isNaN(this.value)) {
				return;
			} else {
				var dj = $(this).parent().parent().find(":input#quantity")
						.val();
				var price = $(this).parent().parent().find(":input#price").val();
				if (!isNaN(dj) && !isNaN(price)) {
					var jine = dj * price;
					jine = Math.round(jine * 100) / 100;
					$(this).parent().parent().find(":input#money").attr(
							"value", jine);
				}
			}
		}
		var heji=0;
		$("input#money", $("#goodtable")).each(function(){
			var s=$(this).val();
			var x=parseFloat(s);
			if(!isNaN(x)){
			heji=heji+x
			}
		});
		DX(heji);
		$("span#countmoney").text(heji);
		$("input#countmoney").val(heji);
	});
	$(".jisuan").live("keydown", function(event) {
		if (this.value != "") {
			if (isNaN(this.value)) {
				return;
			} else {
				var dj = $(this).parent().parent().find(":input#quantity")
						.val();
				var price = $(this).parent().parent().find(":input#price")
						.val();
				if (!isNaN(dj) && !isNaN(price)) {
					var jine = dj * price;
					jine = Math.round(jine * 100) / 100;
					$(this).parent().parent().find(":input#money").attr(
							"value", jine);
				}
			}
		}
		var heji=0;
		$("input#money", $("#goodtable")).each(function(){
			var s=$(this).val();
			var x=parseFloat(s);
			if(!isNaN(x)){
			heji=heji+x
			}
		});
		DX(heji);
		$("span#countmoney").text(heji);
		$("input#countmoney").val(heji);
		
	});
	// 克隆的行删除
	$(".klsc").click(function() {
				$(this).parent().parent().remove();
				docNull -= 1;
				var heji=0;
				$("input#money", $("#goodtable")).each(function(){
					var s=$(this).val();
					var x=parseFloat(s);
					if(!isNaN(x)){
					heji=heji+x
					}
				});
				DX(heji);
				$("span#countmoney").text(heji);
				$("input#countmoney").val(heji);
			});
	// 归档
	$("input.JQuerySubmitgd").click(function() {
		var choosedepotID=$("input#choosedepotID").val();//领用仓库
		var drchoosedepotID=$("input#drchoosedepotID").val();//入库位置
		var buttonname=$(this).val();
	    var subtype="";
		if(buttonname=="保存"){
		  subtype="save";
		}else if(buttonname=="提交审核"){
		  subtype="savecheck";
		}
		
		$(".notnull").trigger("blur");
		if ($("form .error").length) {
			return;
		}
		if (notoken) {
			alert("正在提交数据！请稍等");
			return;
		}
		
		if(choosedepotID==drchoosedepotID){
			alert("调出调入仓库相同不能领用出库！");
			return;
		}
        //如果没有选择物品将提示请选择物品
		//.checkgoods
		var trlength=$("#goodtable").find("tr").length;
		if (trlength == 2) {
			alert("请添加库房出库物品");
			notoken = 0;
			return;
		}
		var position=$("#drchoosedepotID").val();
		var gm=$("#goodsName").val();
		var gm2=$("#gm2").text();
		if(position==""||(gm==""&&gm2=="")){
			alert("物品信息带*号的为必填项");
			return;
		}

		var re=0;
		$(".panduan",$(".checkgoods")).each(function(){
			if (this.value==""){
				$(this).css("background-color","red");
				$(this).attr("class","notnull");
				re=1;
			}
		});
		
		/*if(loginstaffcheck==0&&pagetype=="edit"){
    	    alert("登录人员审核后才能继续操作！");
        	return;
    	}*/
		if (financialbillID == "") {
			successtype="add";
			var goodsNum = [];
			$("#goodtable").find("input#goodsNum").each(function (){				
				goodsNum.push($(this).val());				
			});
			$("#InventoryForm")
			.attr("target", "hidden")
			.attr("action",	basePath
							+ "/ea/receive/ea_saveWareManagement.jspa?pageNumber="
							+ pNumber+"&arrgoodsNum="+goodsNum+"&subtype="+subtype);
			
			document.InventoryForm.submit.click();	
			/*if(subtype=="savecheck"){
				alert("成功");
				window.opener.location.reload(); 
				window.close();
				return;
			}
			if(confirm(buttonname+"成功，是否要继续添加")){
				window.location.reload ();
				return;
			}else{
				window.opener.location.reload(); 
				window.close();
				return;
			}*/
			token = 1;
			return;
		}
		
		successtype="edit";
		$("#InventoryForm").attr("target", "hidden").attr(
				"action",
				basePath + "/ea/receive/ea_editWareManagement.jspa?pageNumber="
									+ pNumber+"&subtype="+subtype);
		document.InventoryForm.submit.click();
		token = 2;
	});
	//添加出库单取消按钮操作
	$(".JQueryClose").click(function() {
		if(confirm("确定要关闭？")){
        window.close();
		}
	});
	/** **************************************科目管理开始******************************************* */
	var subjectsNumber = "";
	var subjectsName = "";
	$(".tosubjects").click(function() {
		$(this).parent().parent().find("td").addClass("receivesubjects");
		$td = $("td.subjects");
		$td.children('select').empty();
		var subRuleurl = basePath
				+ "/ea/csubjectsrule/sajax_n_ea_getCSubjectsRule.jspa?date="
				+ new Date().toLocaleString();
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
		var subjecturl = basePath
				+ "ea/csbjects/sajax_ea_getListCsubejstsByPID.jspa?subjectsID=";
		subjectsNumber = "";
		subjectsName = "";
		$.ajax({
			url : encodeURI(subjecturl + "002&date="
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
				var subjectsList = member.subjectsList;
				$td = $("td.subjects");
				$select = "<option selected='selected' value = ''>--请选择--</option>";
				$td.children('select:eq(0)').append($select);
				for (var i = 0; i < subjectsList.length; i++) {
					$op = $("<option />");
					$op.attr("value", subjectsList[i].subjectsNumbers).attr(
							"id", subjectsList[i].subjectsID)
							.text(subjectsList[i].subjectsName);
					$td.children('select:eq(0)').append($op);
				}
				$td.children('select:eq(0)').show();
				endnumber += parseInt(subRule[0]);
				if (subjectsNumber.substring(0, subRule[0]) != "") {
					$td.children('select:eq(0)').attr("value",
							subjectsNumber.substring(0, subRule[0]));
					$td.children('select:eq(0)').trigger("change");
				}
				$("#selectsubjects").jqmShow();
			},
			error : function cbf(data) {
				alert("数据获取失败！");
			}
		});

	});
	$("#savesubjects").click(function() {
		if (subjectsName != "--请选择--") {
			$("#subjectsID", $(".receivesubjects")).attr("value",
					subjectsnumber);
			$("#subjectsName", $(".receivesubjects")).attr("value",
					subjectsName);
		} else {
			$("#subjectsID", $(".receivesubjects")).attr("value", "");
			$("#subjectsName", $(".receivesubjects")).attr("value", "");
		}
		$("#InventoryForm").find("td.receivesubjects")
				.removeClass("receivesubjects");
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
		var subjectsPID = $td.children('select:eq(' + num + ')')
				.children("option:selected").attr("id");
		subjectsnumber = $td.children('select:eq(' + num + ')')
				.children("option:selected").val();
		subjectsName = $td.children('select:eq(' + num + ')')
				.children("option:selected").text();
		if (subjectsnumber == "") {
			var numbers = parseInt(num) - 1;
			subjectsnumber = $td.children('select:eq(' + numbers + ')')
					.children("option:selected").val();
			subjectsName = $td.children('select:eq(' + numbers + ')')
					.children("option:selected").text();
			notoken = 0;
			return;
		}
		var subjecturl = basePath
				+ "ea/csbjects/sajax_ea_getListCsubejstsByPID.jspa?subjectsID=";
		var subjectsNumber = "";
		$.ajax({
			url : encodeURI(subjecturl + subjectsPID + "&date="
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
				var subjectsList = member.subjectsList;
				notoken = 0;
				$td = $("td.subjects");
				$select = "<option selected='selected' value = ''>--请选择--</option>";
				$td.children('select:eq(' + number + ')').append($select);
				for (var i = 0; i < subjectsList.length; i++) {
					$op = $("<option />");
					$op.attr("value", subjectsList[i].subjectsNumbers).attr(
							"id", subjectsList[i].subjectsID)
							.text(subjectsList[i].subjectsName);
					$td.children('select:eq(' + number + ')').append($op);
				}
				$td.children('select:eq(' + number + ')').show();
				if (subjectsNumber.length == endnumber
						|| subjectsNumber.length == 0) {
					return;
				}
				endnumber += parseInt(subRule[number]);
				var startnumber = subRule[number - 1];
				if (subjectsNumber.substring(startnumber, endnumber) != "") {
					$td.children('select:eq(' + number + ')').attr("value",
							subjectsNumber.substring(0, endnumber));
					$td.children('select:eq(' + number + ')').trigger("change");
				}

			},
			error : function cbf(data) {
				notoken = 0;
				alert("数据获取失败！");
			}
		});
	});
	var subRuleurl = basePath
			+ "ea/csubjectsrule/sajax_n_ea_getCSubjectsRule.jspa?date="
			+ new Date().toLocaleString();
	$.ajax({
				url : encodeURI(subRuleurl),
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
					subRule = (member.subRule.rules).split(",");
				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}
			});
	//改变总金额
	

	/** *******************************科目管理结束******************************* */
});
function re_load() {
	if(successtype=="edit"){
	var journalNum=$("input.inputbottom").val();
	var url= basePath + "ea/allocation/ea_toWareManagementCashierBills.jspa?pageNumber="+ pNumber 
						+ "&cashierBills.journalNum="+journalNum + "&sdate="+ sdate 
						+ "&edate=" + edate + "&search="+ search+"&billStatus=07";
	document.location.href = url;
	}
	if(successtype=="add"){
	 window.close();
	}
    document.InventoryForm.reset();
}

function toCCode(codePID, selectID, formID) {
	$(".jqmWindow").jqmHide();
	$("#codePID").attr("value", codePID);
	$("#selectID").attr("value", selectID);
	$("#formID").attr("value", formID);
	$("#ccodevalue").attr("value", "");
	$("#newccode").jqmShow();
}
function saveCCode() {
	var codePID = $("#codePID").attr("value");
	var codeValue = $("#ccodevalue").attr("value");
	var selectID = $("#selectID").attr("value");
	var formID = $($("#formID").attr("value"));
	var url = basePath + "ea/ccode/sajax_ea_saveCCodeByAjax.jspa?code.codePID="
			+ codePID + "&code.codeValue=" + codeValue + "&date="
			+ new Date().toLocaleString();
	$.ajax({
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
					var code = member.code;
					$("#newccode").jqmHide();
					$op = $("<option/>");
					$op.attr("value", code.codeValue).text(code.codeValue);
					$(selectID, formID).append($op);
					alert("操作成功！");
					$(".jqmWindow", formID).jqmShow();
				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}
			});
}
/**********************************************************************************************/
$(document).ready(function() {
			$("input.JQueryreturn1").click(function() {// 取消
						var formID = $($("#formID").attr("value"));
						$("#newccode").jqmHide();
						$(".jqmWindow", formID).jqmShow();
					});
		});
/** **************************************仓库管理开始******************************************* */
$(document).ready(function() {
	// 新增
	$(".xzck").click(function() {
		window.open(basePath
				+ "/ea/depotmanage/ea_getListDepotManage.jspa");
	});
	//选择仓库
	$("input#choosedepotName").click(function() {
		$("#ckbody").html("");
		$("input#parmss").val("dc");
		$(".jqmWindow", $("#ckForms")).jqmShow();
	});
	$("input#position").click(function() {
		$("#ckbody").html("");
		$("input#parmss").val("dp");
		$(".jqmWindow", $("#ckForms")).jqmShow();
	});
	$("input#drchoosedepotName").click(function() {
		$("#ckbody").html("");
		$("input#parmss").val("dr");
		$(".jqmWindow", $("#ckForms")).jqmShow();
	});
	$("#drName2").click(function(){
		$("#ckbody").html("");
		$("input#parmss").val("dr");
		$(".jqmWindow", $("#ckForms")).jqmShow();
	})
	// 添加选中仓库到
	$("#ckok").click(function() {
		//判断选择物品
		$i=$("#mainframe");
	    if ($i.contents().find("[name='a']").is(':checked')) {
    	var id=$i.contents().find("input[type='radio']:checked").val();//库房id
    	$r=$i.contents().find("#" +id);
    	var depotName=$r.find("#depotName").text();//库房name
    	var useState=$r.find("#useState").text();//启用/未启用
    	if(useState=="未启用"){
		     alert("未启用，请选择已启用仓库!");
		     return;
		}
		//赋值
		var parmss=$("input#parmss").val();
		if(parmss=="dc"){
		$("input#choosedepotID").val(id);	
		$("input#choosedepotName").val(depotName);
		$("input#choosedepotName").blur();
		}
		if(parmss=="dr"){
		$("input#drchoosedepotID").val(id);	
		$("input#drchoosedepotName").val(depotName);
		$("input#drchoosedepotName").blur();
		$("#drName2").text(depotName);
		}
		if(parmss=="dp"){
			$("input#position").val(id);	
			$("input#position").val(depotName);
			$("input#position").blur();
			}
		$i.contents().find("input[type='radio']:checked").attr("checked", false);
		$(".jqmWindow", $("#ckForms")).jqmHide();
	    }else {
			alert("请选择仓库！");
		}
	});
});	
/** **********************************仓库管理结束**************************** */
/************************************选择物品开始*****************************************/
$(document).ready(function() {
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
	
	// 选择物品
	var kelongid="";
	var goodsNum="";
	$("#goodsName").click(function() {
		kelongid=$(this).parent().parent().attr("id");
		//获取已经选过的物品存货代码
		$("input#goodsNum", $("#goodtable")).each(function(){
			var s=$(this).val();
			if(s!=""){
			  goodsNum=goodsNum+s+",";
			}
		});
		$(".jqmWindow", $("#goodsForm")).jqmShow();
		$("#tbodya").html("");
		$("#loadcab").attr(
				"src",
				basePath + "page/ea/main/human/cstaff/loadActiveX.html?code="
						+ Math.random());
						
		$(".scan").hide();
		$(".manual").show();
		$("#searchGood").hide();
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
	// 新增物品
	$(".xzwp").click(function() {
		window.open(basePath
				+ "ea/goodsmanage/ea_getListGoodsManage.jspa?");
	});
	// 增加物品行
	var kelongtr="";
	$("#addgoodtr").click(function() {
		            var depotName=$("input#choosedepotName").val();
		            if(depotName==""){
		               alert("请先添加领用仓库！");
		               return;
		            }
		            //
		            $("input#warehouse").val(depotName);
					// 选中一行克隆一行
					select++;
					docNull += 1;//克隆行序号
					// 克隆一行并修改文本框的name
					$("#kelong").before(
							$("#kelong").clone(true).attr("id",
									"kelong" + select).addClass("checkgoods"));
					kelongtr="kelong" + select;
					// 修改input标签Id为goodsNomber的值（序号列）
					$("input#goodsNomber", $("#kelong" + select)).attr("value",
							select);
					// 修改当前行的所有input的name
					$("#kelong" + select).find(':input').each(function() {
						$(this).attr("name",
								"goodsmap[" + select + "]." + this.name);
					});
					
					// 遍历Id为$(this).val()的tr里的td
					$("tr#kelong" + select).show();
					$(this).attr("checked", false);
	});
	//删除克隆的行
	$("#delgoodtr").click(function() {
		var trhtml=$("tr#" + kelongtr).html();
		//判断增加行的数据值有几个
		var a=0;
		$("tr#" + kelongtr).find("input[type='text']").each(function(){
      		var value=$(this).val();
      		if(value!=""){
      		  a++;
      		}
        });
		if(trhtml!=null){
			if(a>1){
			   if(window.confirm('删除行存在数据，删除后数据不可恢复，是否进行删除操作？')){
                 //alert("确定");
		     	 $("tr#" + kelongtr).remove();
                 return true;
              }else{
                 //alert("取消");
                 return false;
              }
			}
			else{
			   $("tr#" + kelongtr).remove();
			    var count=0;
				$("input#money", $("#goodtable")).each(function(){
					var s=$(this).val();
					var x=parseFloat(s);
					if(!isNaN(x)){
					count=count+x
					}
				});
				DX(count);
				$("span#countmoney").text(count);
				$("input#countmoney").val(count);
			}
		     
		}else{
		  alert("新增行已经删除！");
		}
	});

	// 添加所选中的物品到物品单
	$("#selectGood").click(function() {
		if ($("[name='check']").is(':checked')) {
			$("input[name='check']").each(function() {
				if ($(this).is(':checked')) {
					var goodsnum=$("tr #" + $(this).val()).find("#goodsNum").text()
					if(goodsnum!=""){
					    var result=goodsNum.split(",");
						for(var i=0;i<result.length;i++){
							 var a=result[i];
							 if(goodsnum==a){
						       goodsnum="1";
							 }
						}
					}
					if(goodsnum=="1"){
					  alert("选中物品中有重复数据！");
					  return;
					}
					//-------------------------
					$("tr #" + $(this).val()).children("td").each(function() {
				        if (this.id == "goodsID") {
							$("input#goodsID", $("#" + kelongid)).attr(
									"value", $(this).text());
						}
						else if (this.id == "goodsName") {
							$("input#goodsName", $("#" + kelongid))
									.attr("value", $(this).text());
						}
						//存货代码
						else if (this.id == "goodsNum") {
							$("input#goodsNum", $("#" + kelongid))
									.attr("value", $(this).text());
						}
						//库房
						else if (this.id == "warehouse") {
							$("input#depotID", $("#" + kelongid))
									.attr("value", $(this).text());
						}
		                else if (this.id == "warehouseName") {
							$("input#depotName", $("#" + kelongid))
									.attr("value", $(this).text());
						}
						//库存外键
						else if (this.id == "inventoryID") {
							$("input#inventoryID", $("#" + kelongid))
									.attr("value", $(this).text());
						}
						//科目
						else if (this.id == "subjectsName") {
							if($(this).text()=="null"){
							$("input#subjectsName", $("#" + kelongid))
									.attr("value", "");
							}else{
							$("input#subjectsName", $("#" + kelongid))
									.attr("value", $(this).text());
							}
							
						} else if (this.id == "subjectsID") {
							if($(this).text()=="null"){
							$("input#subjectsID", $("#" + kelongid)).attr(
									"value", "");
							}else{
							$("input#subjectsID", $("#" + kelongid)).attr(
									"value", $(this).text());
							}
						} 
						//品名编号
						else if (this.id == "goodsCoding") {
							$("input#goodsCoding", $("#" + kelongid)).attr(
									"value", $(this).text());
						}
						//物品类别
						else if (this.id == "type") {
							$("input#typeID", $("#" + kelongid)).attr(
									"value", $(this).text());
						}
						//条码
						else if (this.id == "sortCode") {
							$("input#sortCode", $("#" + kelongid)).attr(
									"value", $(this).text());
						}
						//品牌规格
						else if (this.id == "standard") {
							$("input#standard", $("#" + kelongid)).attr(
									"value", $(this).text());
						}
						//数量
						else if (this.id == "invenQuantity") {
							$("input#quantity", $("#" + kelongid)).attr(
									"value", $(this).text());
						}
						//单价
						else if (this.id == "unitPrice") {
							$("input#price", $("#" + kelongid)).attr(
									"value", $(this).text());
						}
						//单位
						else if (this.id == "unit") {
							$("input#goodsVariableID", $("#" + kelongid)).attr(
									"value", $(this).text());
						}else{
							$("span#" + this.id, $("#" + kelongid))
									.text($(this).text());
						}

					});
					$("tr#kelong" + select).show();
					$(this).attr("checked", false);
				}
			});
			$(".jqmWindow", $("#goodsForm")).jqmHide();
		} else {
			alert("请选择物品！");
		}
		var quantity=$("input#quantity", $("#" + kelongid)).val();//当前要赋值的行物品的单价
		if (quantity != "") {
			if (isNaN(quantity)) {
				return;
			} else {
				var dj = quantity;
				var price =$("input#price", $("#" + kelongid)).val();
				if (!isNaN(dj) && !isNaN(price)) {
					var jine = dj * price;
					jine = Math.round(jine * 100) / 100;
					$("input#money", $("#" + kelongid)).val(jine);
				}
			}
		 var count=0;
		$("input#money", $("#goodtable")).each(function(){
			var s=$(this).val();
			var x=parseFloat(s);
			if(!isNaN(x)){
			count=count+x
			}
		});
		DX(count);
		$("span#countmoney").text(count);
		$("input#countmoney").val(count);
			
		}
		loadcab.window.closePort();// 关闭读数据端口
		chipids = new Array();
        i = 0;
	});
	// 根据输入的物品编号或物品名称查询
	$("input#searchGood").click(function() {
		var typeName = $("#typeID", $("table#searchgood")).val();
		var depotID=$("input#choosedepotID").val();//出货仓库id
		$(":input#parms").val("parameter=" + typeName +"&depotid="+depotID);
		$("#tbodya").children().remove();
		cx("parameter=" + typeName +"&depotid="+depotID);
	});
	$(".manual").click(function(){
	 $(this).hide();
	 $(".scan").show();
	 $("#searchGood").show();
	});
	// 扫描输入点击
	$(".scan").click(function() {
				$(this).hide();
				$(".manual").show();
				$("#searchGood").show();
			});

	 setInterval(function() {
	 	var typeName = $("#typeID", $("table#searchgood")).val();
	 	var depotID=$("input#choosedepotID").val();//出货仓库id
	    if ($("#goodsForm .scan").is(":hidden")) {
			if (typeName != "") {
				$("input#parms").val("parameter=" + typeName +"&depotid="+depotID);
				$("#tbodya").children().remove();
				cx("parameter=" + typeName +"&depotid="+depotID);
				$("#typeID", $("table#searchgood")).val("");
			}

	   }
	 },1000);
// ajax查询物品列表
	function cx(typeCN) {
		var searchurl = basePath
		+ "/ea/allocation/sajax_ea_getInventoryWithGoodsNum.jspa?";
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
				var goodlist = member.goodlist;
				if (goodlist.length==0) {
					$("#body_02").show();
					return;
				}
				var tabletr = "";
				var j=1;
				for (var i = 0; i < goodlist.length; i++) {
					tabletr += "<tr style='cursor: hand;' id = "
							+ "tr"+j + ">";
					tabletr += "<td id='check' align='center'><input type ='radio' class='ragood' value="
							+ "tr"+j + " name='check'/></td>";
					//物品名称
					tabletr += "<td id='goodsName'  align='center'>"
							+ goodlist[i][2] + "</td>";
					//库房
					tabletr += "<td id='warehouseName'  align='center'>"
							+ goodlist[i][1] + "</td>";
					//条码
					tabletr += "<td id='sortCode'  align='center'>"
							+ goodlist[i][3] + "</td>";
					//品名编号
					tabletr += "<td id='goodsCoding'  align='center'>"
						+ goodlist[i][4] + "</td>";
					//序列号
					tabletr += "<td id='goodsNum'  align='center'>"
						+ goodlist[i][10] + "</td>";
					//物品类别
					tabletr += "<td id='type' align='center'>"
							+ goodlist[i][5] + "</td>";
					//库存
					tabletr += "<td id='invenQuantity' align='center'>"
							+ goodlist[i][6] + "</td>";
					//单位
					tabletr += "<td id='unit' title='ava' align='center'>"
							+ goodlist[i][7] + "</td>";	
							
					//科目
					tabletr += "<td id='subjectsName' style='display:none' align='center'>"
							+ goodlist[i][11] + "</td>";	
					tabletr += "<td id='subjectsID' style='display:none' align='center'>"
							+ goodlist[i][12] + "</td>";	
					//库房
					tabletr += "<td id='warehouse' style='display:none' align='center'>"
							+ goodlist[i][13] + "</td>";
					//品牌规格
					tabletr += "<td id='standard' style='display:none' align='center'>"
							+ goodlist[i][14] + "</td>";
					//品牌规格
					tabletr += "<td id='unitPrice' style='display:none' align='center'>"
							+ goodlist[i][15] + "</td>";		
					//库存外键
					tabletr += "<td id='inventoryID' style='display:none' align='center'>"
								+ goodlist[i][0] + "</td>";
					//物品id
					tabletr += "<td id='goodsID' style='display:none' align='center'>"
								+ goodlist[i][8] + "</td>";
					tabletr += " </tr>";
					j++;
				}
				$("#tbodya").append(tabletr);
				$("#body_02").show();
				//alert("数据加载成功")
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
/************************************选择物品结束*****************************************/
//物品复选框全选
$("input#checkAll").live("click",function(){ //全选
    if($(this).attr("checked")){
    	$("#goodsForms").find("input[type='checkbox']").each(function(){
      		$(this).attr("checked",true);
        });
    }else{
    	$("#goodsForms").find("input[type='checkbox']").each(function(){
        	$(this).attr("checked",false);
        });
    }
});
//金额大写
function DX(n) {
       if (!/^(0|[1-9]\d*)(\.\d+)?$/.test(n))
           return "数据非法";
           var unit = "千百拾亿千百拾万千百拾元角分", str = "";
           n += "00";
       var p = n.indexOf('.');
       if (p >= 0)
           n = n.substring(0, p) + n.substr(p+1, 2);
           unit = unit.substr(unit.length - n.length);
           var j=n.length-1;
       for (var i=0; i < n.length; i++)
       {
             if(j>=6)
             {
               str += '零壹贰叁肆伍陆柒捌玖'.charAt(n.charAt(i)) + unit.charAt(i);
               var mm=  str.replace(/零(千|百|拾|角)/g, "零").replace(/(零)+/g, "零").replace(/零(万|亿|元)/g, "$1").
                replace(/(亿)万|壹(拾)/g, "$1$2");
                j--;
               if(j==5)
               {
                 mm=mm.replace("万",""); 
                 $("span#6").text(mm);
               }
                
             }else
             {  
                $("span#"+(n.length)).text('￥');
                $("span#"+j).text('零壹贰叁肆伍陆柒捌玖'.charAt(n.charAt(i)));
                j=j-1;
             }
                
       }
       }