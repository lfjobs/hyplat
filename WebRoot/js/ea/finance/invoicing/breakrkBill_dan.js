// ajax查询物品通过芯片
var chipids = new Array();
var i = 0;

$(function() {
	 //制单时间
	var d = new Date(); 
	var year=d.getFullYear(); 
	var month = d.getMonth()+1; 
	var date = d.getDate(); 
	var day = d.getDay(); 
	var hours = d.getHours(); 
	var minutes = d.getMinutes(); 
	var seconds = d.getSeconds(); 
	var ms = d.getMilliseconds();   
	var curDateTime= year+"-";
	if(month>9)
	 curDateTime = curDateTime +month+"-";
	else
	 curDateTime = curDateTime +"0"+month+"-";
	if(date>9)
	 curDateTime = curDateTime +date;
	else
	 curDateTime = curDateTime +"0"+date;
	$("span#zddate").text(curDateTime);
	//关闭
	$(".jqmWindow").jqm({
		modal : true,// 
		overlay : 20
			// 
		}).jqmAddClose('.close');
	//关闭
	$(".JQueryreturns").click(function() {
				notoken = 0;
				$(".jqmWindow").jqmHide();
				//loadcab.window.closePort();// 关闭读数据端口
				chipids = new Array();
                i = 0;
			});
    //取消按钮操作
	$(".JQueryClose").click(function() {
        //re_load();
	});
	
   function re_load() {
	var url = basePath + "#";
	document.location.href = url;
   }
   
// --------------------------------计算金额合计-------------------------
	$(".jisuan").live("keyup", function(event) {
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
		$("table .checkgoods").each(function(){
			var s=$(this).find("input#money").val();
			var x=parseFloat(s);
			if(!isNaN(x)){
			heji=heji+x
			}
		});
		DX(heji);
		$("span#countmoney").text(heji);
		$("input#goodsmoney").val(heji);
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
		$("table .checkgoods").each(function(){
			var s=$(this).find("input#money").val();
			var x=parseFloat(s);
			if(!isNaN(x)){
			heji=heji+x
			}
		});
		DX(heji);
		$("span#countmoney").text(heji);
		$("input#goodsmoney").val(heji);
		
	});
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
	// 克隆的行删除
	$(".klsc").click(function() {
				$(this).parent().parent().remove();
				docNull -= 1;
			});
	// 归档
	$("input.JQuerySubmitgd").click(function() {
		$(".notnull").trigger("blur");
		if ($("form .error").length) {
			return;
		}
		if (notoken) {
			alert("正在提交数据！请稍等");
			return;
		}
		if (docNull == 0) {
			alert("必须添加预算项目");
			notoken = 0;
			return;
		}
        //提交审核如果没有选择物品将提示请选择物品
		notoken = 1;
		var trlength=$("#goodtable").find("tr").length;
		if (trlength == 3) {
			alert("请添加入库物品");
			notoken = 0;
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
		$(".put3", $("tr.checkgoods")).trigger("blur");
		if ($("form .error").length){
         	re=1;
        }
        if(re){
			alert("请填完所有必填项");
			return;
		}
		if ($("form#InventoryForm .error").length) {
			alert("请填完所有必填项");
			notoken = 0;
			return;
		}
		/*$("#InventoryForm").attr("target", "hidden").attr(
				"action",
				basePath + "/ea/purchase/ea_savePurchase.jspa?pageNumber="
						+ pNumber);
		document.InventoryForm.submit.click();
		document.InventoryForm.reset();
		token = 2;*/
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

	/** *******************************科目管理结束******************************* */
});

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
	$("#depotName").click(function() {
		$("#ckbody").html("");
		$(".jqmWindow", $("#ckForms")).jqmShow();
	});
	
	// 添加选中仓库到
	$("#ckok").click(function() {
		//判断选择物品
		$i=$("#mainframe");
	    if ($i.contents().find("[name='a']").is(':checked')) {
    	var id=$i.contents().find("input[type='radio']:checked").val();//库房id
    	$r=$i.contents().find("#" +id);
    	var depotName=$r.find("#depotName").text();//库房name
    	var useState=$r.find("#useState").text();//启用/未启用
    	if(useState=="未启用00"){
		     alert("未启用，请选择已启用仓库!");
		     return;
		}
		//赋值
		$("input#depotID").val(id);	
		$("input#depotName").val(depotName);	
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
	tree = new dhtmlXTreeObject("aadTree", "100%", "100%", 0);
	tree.enableDragAndDrop(false);
	tree.enableHighlighting(1);
	tree.enableCheckBoxes(0);
	tree.enableThreeStateCheckboxes(false);
	tree.setSkin(basePath + 'js/tree/dhx_skyblue');
	tree.setImagePath(basePath + "js/tree/codebase/imgs/");
	tree.loadXML(basePath + "js/tree/common/tree_b.xml");
	var getcodeurl = basePath
			+ "ea/ccode/sajax_ea_getListCCodeByPID.jspa?codeID=scode20101014v5zed7cukk0000000002&date="
			+ new Date().toLocaleString();
	tree.insertNewChild(0, "scode20101014v5zed7cukk0000000002", "物品树", 0, 0, 0,
			0);
	$.ajax({
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

						tree.insertNewChild(
								"scode20101014v5zed7cukk0000000002",
								codeList[i].codeID, codeList[i].codeValue, 0,
								0, 0, 0);
						tree.setUserData(codeList[i].codeID, "codeNumber",
								codeList[i].codeNumber);

					}
				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}
			});
	tree.setOnClickHandler(function() {
		var oldtreeid = treeid;
		treeid = tree.getSelectedItemId();
		treename = tree.getItemText(treeid);
		if (oldtreeid != treeid) {
			if (treeid != "scode20101014v5zed7cukk0000000002") {
				$(":input#parmss").val("typeID=" + treename);
				cxs("typeID=" + treename);
				return;
			}
		}
	});
	
	
	
	
	// 双击选中物品
	$("table#gotables tr[id]").live("click", function(event) {
		var b = $("input.ragoods", $(this)).attr("checked");
		$("input.ragoods", $(this)).attr("checked", !b);
	});
	
	// 复选框选中物品
	$(".ragoods").live("click", function(event) {
		var b = $(this).attr("checked");
		$(this).attr("checked", !b);
	});
	
	// 导入数据（选择物品）
	var kelongid="";
	$("#shujus").click(function() {
		kelongid=$(this).parent().parent().attr("id");
		$("#body_03").html("");
		$("#selectType").val("goods");
		$(".jqmWindow", $("#goodsForms")).jqmShow();
	});
	
	// 上一页
	$("#wpsy_1").click(function() {
		var sy = $("#wpsy_1").attr("title");
		if (sy != 0) {
			var typeName = $(":input#parmss").val();
			var typeCN = typeName + "&pageForm.pageNumber=" + sy;
			cxs(typeCN);
		} else {
			alert("已是首页！");
		}
	});
	
	// 下一页
	$("#wpxy_1").click(function() {
		var xy = $("#wpxy_1").attr("title");
		if (xy != 0) {
			var typeName = $(":input#parmss").val();
			var typeCN = typeName + "&pageForm.pageNumber=" + xy;
			cxs(typeCN);
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
					// 选中一行克隆一行
					select++;
					docNull += 1;//克隆行序号
					// 克隆一行并修改文本框的name
					$("#kelong").before(
							$("#kelong").clone(true).attr("id",
									"kelong" + select).addClass("checkgoods"));
					kelongtr="kelong" + select;
					// 修改input标签Id为goodsNomber的值（序号列）
					$("input#numbers", $("#kelong" + select)).attr("value",
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
			}
		     
		}else{
		   alert("本行已经删除！");
		}
		
	});
	// 添加所选中的物品到物品单
	$("#selectGoods").click(function() {
		//判断选择物品
		if ($("[name='check']").is(':checked')) {
			$("input[name='check']").each(function() {
				if ($(this).is(':checked')) {
					$("tr #" + $(this).val()).children("td").each(function() {
						if (this.id == "goodsID") {
							$("input#goodsID", $("#" + kelongid)).attr(
									"value", $(this).text());
						}
						else if (this.id == "goodsName") {
							$("input#shujus", $("#" + kelongid))
									.attr("value", $(this).text());
						}
						//规格
						else if (this.id == "standard") {
							$("input#standard", $("#" + kelongid))
									.attr("value", $(this).text());
						}
						//型号
						else if (this.id == "modelNumber") {
							$("input#modelNumber", $("#" + kelongid))
									.attr("value", $(this).text());
						}
						//科目
						else if (this.id == "subjectsName") {
							$("input#subjectsName", $("#" + kelongid))
									.attr("value", $(this).text());
						} else if (this.id == "subjectsID") {
							$("input#subjectsID", $("#" + kelongid)).attr(
									"value", $(this).text());
						} 
						//物品编号（批次）
						else if (this.id == "goodsNum") {
							$("input#goodsNum", $("#" + kelongid)).attr(
									"value", $(this).text());
						}
						//类型
						else if (this.id == "type") {
							$("input#goodsType", $("#" + kelongid)).attr(
									"value", $(this).text());
						}
						//单位
						else if (this.id == "unit") {
							$("input#unit", $("#" + kelongid)).attr(
									"value", $(this).text());
						}else {
							$("span#" + this.id, $("#" + kelongid))
									.text($(this).text());
						}
						/*if (this.title == "ava" && $(this).text().length != 0) {
							$op = $("<option />");
							$op.attr("value", $(this).text()).text($(this)
									.text());
							$("select#unit", $("#" + kelongid)).append($op);
						}*/
					});
					$("tr#" + kelongid).show();
					$(this).attr("checked", false);
				}
			});
			$(".jqmWindow", $("#goodsForms")).jqmHide();
		} else {
			alert("请选择物品！");
		}
		
		loadcab.window.closePort();// 关闭读数据端口
		chipids = new Array();
        i = 0;
	});
	// 根据输入的物品编号或物品名称查询
	$("input#searchGoods").click(function() {
		var typeName = $("#typeID", $("table#searchgoods")).val();
		$(":input#parmss").val("parameter=" + typeName);
		cxs("parameter=" + typeName);
	});
	
	// ajax查询物品列表
	function cxs(typeCN) {
		if (notoken) {
			alert("正在获取数据！请稍等");
			return;
		}
		notoken = 1;
		$("#wpsy_1").attr("title", 0);
		$("#wpxy_1").attr("title", 0);
		$("#wpzy_1").attr("title", 0);
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
					$("#wpsy_1").attr("title", dqy - 1);
				}
				if (dqy < zys) {
					$("#wpxy_1").attr("title", dqy + 1);
				}
				//‘
				$("span#wpzycount").text(zys);
				var tabletr = "<table width='98%' height='26' align='center' cellspacing='0' cellpadding='1' style='font-size:12px;' class='bannb_01'><tr><td height='24' align='left' valign='top' class='txt01' >&nbsp;点击选择物品</td></tr></table>";
				tabletr += "<table width='99%' align='center' id='gotables' cellpadding='0' cellspacing='0' class='table'>";
				tabletr += " <tr><th height='21' align='center' bgcolor='#E4F1FA'><input type ='checkbox' id='checkAll'/>全选</th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'>物品编码</th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'>物品名称</th>" +
						"<th align='center' bgcolor='#E4F1FA'>统一分类条码</th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'>类型</th>" +
						"<th align='center' bgcolor='#E4F1FA'>品牌</th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'>库存量</th>" +
						"<th align='center' bgcolor='#E4F1FA'>单位</th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'>默认规格</th>" +
						"<th align='center' bgcolor='#E4F1FA'>型号</th>" +
						"<th align='center' bgcolor='#E4F1FA'>品牌规格</th></tr>";
			    var types="checkbox";
				if($("#selectType").val()=="projects"){
					
					types = "radio";
				}
				for (var i = 0; i < pageForm.list.length; i++) {
					tabletr += "<tr style='cursor: hand;' id = "
							+ pageForm.list[i].goodsID + ">";
					tabletr += "<td id='check' align='center'><input type ='"+types+"' class='ragoods' value="
							+ pageForm.list[i].goodsID + " name='check'/></td>";
					tabletr += "<td id='goodsNum' align='center'>"
							+ pageForm.list[i].goodsCoding + "</td>";
					tabletr += "<td id='goodsName'  align='center'>"
							+ pageForm.list[i].goodsName + "</td>";
					tabletr += "<td id='sortCode'  align='center'>"
							+ pageForm.list[i].defaultStorage + "</td>";					
					tabletr += "<td id='type' align='center'>"
							+ pageForm.list[i].typeID + "</td>";
					tabletr += "<td id='brand' align='center'>"
							+ pageForm.list[i].mnemonicCode + "</td>";					
					tabletr += "<td id='variableID'  align='center'>"
							+ "</td>";
					tabletr += "<td id='goodsID' style='display:none' align='center'>"
							+ pageForm.list[i].goodsID + "</td>";
					tabletr += "<td id='unit' title='ava' align='center'>"
							+ pageForm.list[i].variableID + "</td>";
					tabletr += "<td id='variable1ID' title='ava' style='display:none' align='center'>"
							+ pageForm.list[i].variable1ID + "</td>";
					tabletr += "<td id='variable2ID' title='ava' style='display:none' align='center'>"
							+ pageForm.list[i].variable2ID + "</td>";
					tabletr += "<td id='variable3ID' title='ava' style='display:none' align='center'>"
							+ pageForm.list[i].variable3ID + "</td>";
					tabletr += "<td id='variable4ID' title='ava' style='display:none' align='center'>"
							+ pageForm.list[i].variable4ID + "</td>";
					tabletr += "<td id='acquiesceStandard' align='center'>"
							+ pageForm.list[i].acquiesceStandard + "</td>";
					tabletr += "<td id='modelNumber' align='center'>"
							+ pageForm.list[i].model + "</td>";
					tabletr += "<td id='goodsID' style='display:none' align='center'>"
						+ pageForm.list[i].goodsID + "</td>";
					tabletr += "<td id='standard' align='center'>"
						+ pageForm.list[i].standard + "</td>";
					tabletr += "<td id='subjectsName' title='ava' style='display:none' align='center'>"
						+ pageForm.list[i].subjectsName + "</td>";
					tabletr += "<td id='subjectsID' title='ava' style='display:none' align='center'>"
						+ pageForm.list[i].subjectsID + "</td>";
					tabletr += " </tr>";
				}
				tabletr += " </table>";
				$("#body_03").html(tabletr);
				$("#body_03").show();
				//alert("数据加载成功");
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