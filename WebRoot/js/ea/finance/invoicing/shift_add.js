$(function() {
	// 为弹出框准备下拉表内容
	$(".jqmWindow").jqm({
		modal : true,// 
		overlay : 20
			// 
		}).jqmAddClose('.close');//
	$(".JQueryreturns").click(function() {
		notoken = 0;
		$(".jqmWindow").jqmHide();
	});
	$(".JQueryClose").click(function() {
				notoken = 0;
				re_load();
	});
	
	//change事件加载下一级内容
      $("select.warehouse").change(function(){
           if(notoken){
				alert("正在加载数据");
				return;
			}
			notoken=1;
            if($.trim($(this).attr("title"))=='4'){
                notoken=0;
                return;
            }
            aryParam=$.trim($(this).attr("title"));
            $parent=$(this).parent().parent();
            var wareID=$.trim($(this).children("option:selected").val());
            var url =  basePath+"/ea/warehouse/sajax_ea_getListWareHouseZiAjax.jspa?warehouse.wareID="+wareID+"&warehouse.wareType="+aryParam+"&date="+new Date();
       		$.ajax({
					url: encodeURI(url),
					type: "get",
					async: false,
					dataType: "json",
					success: function cbf(data){
					var member = eval("(" + data + ")");
					var nologin = member.nologin;
					if(nologin){
						document.location.href =basePath+"page/ea/not_login.jsp";
					}
					var wareHouseList = member.wareHouseList;
					var se="";
					if(wareHouseList != null&&wareHouseList.length>0){
						for(var i=0;i<wareHouseList.length;i++){
							var op="<option value='"+wareHouseList[i].wareID+"'>"+wareHouseList[i].wareName+"</option>";
							se+=op;
						}
						var xe=parseInt(aryParam)+1;
						$parent.find("select[title='"+ xe +"'] option").remove();
						$parent.find("select[title='"+ xe +"']").append("<option value=''>无</option>");
						$parent.find("select[title='"+ xe +"']").append(se);
					}else{
						var xe=parseInt(aryParam)+1;
						for(var i=xe;i<=4;i++){
							$parent.find("select[title='"+ i +"'] option").remove();
							$parent.find("select[title='"+ i +"']").append("<option value='' selected='selected'>无</option>");
						}
					}
					notoken=0;
					},
					error: function cbf(data){
						   notoken=0;
						   alert("数据获取失败！");
					}
			});
        });

	//部门选择单击事件
	$(".update").click(function() {
		var selectID = $(this).attr("title");
		var departmentIDselect = $(this).next("select").attr("name","cashierBills." + selectID);
		$(this).parent().html(departmentIDselect);
		$("#" + selectID).show();
	});
	
	// 更改部门事件 清空银行帐号
	$("select#departmentID", "table#table3").change(function() {
				$("input#bankNum", "table#table3").attr("value", "");
			});

	// 双҉击҉事҉件҉修҉改
	$("tr.xggoods").dblclick(function() {
		trID=$(this).attr("id");
		$(".jqmWindow", $("#goodsForm")).jqmShow();
	});
	
	// 克隆的商品删除
	/**$("a.klsc").click(function(){
		alert($(this).parent().parent().parent().find(":span").html());
		$(this).parent().parent().parent().find("span[name!='number']").text("");
		$(this).parent().parent().parent().find(":input").attr("value","").hide();
		$(this).parent().parent().parent().find(":input").removeClass();
		$(this).parent().parent().parent().find("div#a").hide();
	})**/
	
	$("input#shiftQuantity").change(function(){
			if ($(this).val()!= "") {
				//移库数量
				var sq=$(this).val();
				//库存数量
				var iq=$(this).parent().parent().find("span#invenQuantity").text();
				if(parseFloat(sq)<0||parseFloat(sq)==0){
					alert("移库数量不能为负数或零");
					$(this).attr("value","");
					$(this).parent().parent().find(":input#shiftQuantity").attr("value","");
					$(this).focus();
					return;
				}
				if (!isNaN(sq) && !isNaN(iq)) {
					if(parseFloat(sq)>parseFloat(iq)){
						alert("移库数量不能大于库存数量");
						$(this).attr("value","");
						$(this).parent().parent().find(":input#shiftQuantity").attr("value","");
						$(this).focus();
						return;
					}
				}
			}
	});

	// 归档
	$("input.JQuerySubmitgd").click(function() {
		if (notoken) {
			alert("正在提交数据！请稍等");
			return;
		}
		notoken = 1;		
		var re=0;
		$(".put3",$(".checkgoods")).each(function(){
			if (this.value==""){
				re=1;
			}
		});
		$(".put3",$("#PurchaseForm")).trigger("blur");
        if ($("form .error").length) {
         	re=1;
        }
        if(re){
			alert("请填完所有必填项");
			notoken = 0;
			return;
		}
		if (financialbillID == "") {
			$("#PurchaseForm")
					.attr("target", "hidden")
					.attr(
							"action",
							basePath
									+ "/ea/stock/ea_saveShift.jspa?pageNumber="
									+ pNumber);
			document.PurchaseForm.submit.click();
			document.PurchaseForm.reset();
			$(".qk").text("");
			$("select#contactConnections", $("table#table4")).hide();
			$("select#aNum", $("table#table4")).hide();
			$("select#phone", $("table#table5")).hide();
			$("select#userNum", $("table#table5")).hide();
			$("#cashierstatus").attr("value", "00");
			$("tr.checkgoods").remove();
			token = 1;
			return;
		}
	});
	/** *******************************取得部门下拉************************************ */
	$(function(){
        var treeName = parent.frames["leftFrame"].tree.getItemText(treeID);
        var treePID = parent.frames["leftFrame"].tree.getParentId(treeID);
        var treePName =parent.frames["leftFrame"].tree.getItemText(treePID);        
        var url = basePath+"ea/responsibilities/sajax_n_ea_getoList.jspa?date="+new Date();
        $.ajax({
                    url: encodeURI(url),
                    type: "get",
                    async: true,
                    dataType: "json",
                    success: function cbf(cc){
                        var member = eval("(" + cc + ")");
                        var nologin = member.nologin;
		                if(nologin){
		                	document.location.href =basePath+"page/ea/not_login.jsp";
		                }
			            var oList = member.organizationlist;
			            var data = new Array();
				        data[0] = {
				            id: treeID,
				            pid: '-1',
				            text: treeName
				        }; 
				        var data1 = new Array();
				        data1[0] = {
				            id: treePID,
				            pid: '-1',
				            text: treePName
				        }; 
			            for (var i = 0; i < oList.length; i++) {
			               	data[i + 1] = {
		                    	id: oList[i].organizationID,
		                    	pid: oList[i].organizationPID,
		                    	text: oList[i].organizationName
		                	};
			                data1[i + 1] = {
			                    id: oList[i].organizationID,
			                    pid: oList[i].organizationPID,
			                    text: oList[i].organizationName
			               };
			            }
				        $t = $("div.content1");
				        $("#companyNames").text(treePName);
				        var ts = new TreeSelector($t.find("select#departmentID")[0], data, -1);
				        ts.createTree();
					},
                    error: function cbf(data){
                        alert("数据获取失败！");
                    }
         });
});
	
	
});
function re_load() {
var url = basePath+"/ea/stock/ea_getShiftList.jspa?pageNumber="+pNumber+"&pageForm.pageNumber="+$("#pageNumber").attr("value")+"&type=06&search="+search+"&sdate="+sdate+"&edate="+edate;
document.location.href =url;
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
$(document).ready(function() {
			$("input.JQueryreturn1").click(function() {// 取消
						var formID = $($("#formID").attr("value"));
						$("#newccode").jqmHide();
						$(".jqmWindow", formID).jqmShow();
					});

		});
/** **********************************选择物品**************************************** */
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
				$(":input#parms").val("typeID=" + treename);
				cx("typeID=" + treename);
				return;
			}
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
	
	// 选择物品
	$("#shuju").click(function() {
		var bt = $("#billsType").text();
		if(bt=="入库单"){
			$("#goodtitle").text("物品编码或名称：");
			
		}else  if(bt=="出库单"){
			$("#goodtitle").text("条码或名称：");
		}
		$(".jqmWindow", $("#goodsForm")).jqmShow();
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
	
	// 添加所选中的物品到物品单
	$("#selectGood").click(function() {
		if ($("[name='check']").is(':checked')) {
			$(":radio[name='check']").each(function() {
				if ($(":radio[name='check']").is(':checked')) {
					$("#"+trID).addClass("checkgoods");
					select++;
					$("span#remindContent", $("#"+trID)).replaceWith('<input id="remindContent" name="remindContent"/>');
					$("input#shiftQuantity",$("#"+trID)).addClass("isNaN put3").show();
					$(":input#ku",$("#"+trID)).addClass("put3").show();
					$(":input#qu",$("#"+trID)).addClass("put3").show();
					$(":input#jia",$("#"+trID)).addClass("put3").show();
					$(":input#wei",$("#"+trID)).addClass("put3").show();
					$("span.xx",$("#"+trID)).show();
					//修改当前行的所有input的name
					$("#"+trID).find(':input').each(function() {
						$(this).attr("name","goodsmap[" + select + "]." + this.name);
					});
					$("tr #" + $(this).val()).children("td").each(function() {
						if (this.id == "pware") {
							$("input#pware", $("#"+trID)).attr(
										"value", $(this).text());
						}
						if (this.id == "parea") {
							$("input#parea", $("#"+trID)).attr(
										"value", $(this).text());
						}
						if (this.id == "pframe") {
							$("input#pframe", $("#"+trID)).attr(
										"value", $(this).text());
						}
						if (this.id == "place") {
							$("input#place", $("#"+trID)).attr(
										"value", $(this).text());
						}
						if (this.id == "goodsID") {
							$("input#goodsID", $("#"+trID)).attr(
										"value", $(this).text());
						} else {
							$("span#" + this.id, $("#"+trID))
										.text($(this).text());							
						}
					});
					$(this).attr("checked", false);
				}
			});
			$(".jqmWindow", $("#goodsForm")).jqmHide();
		} else {
			alert("请选择物品！");
		}
	});
	
	// 根据输入的物品编号或物品名称查询
	$("input#searchGood").click(function() {
		var typeName = $("#typeID", $("table#searchgood")).val();
		if($.trim(typeName)==""){alert("查询条件为空"); return;}
		$(":input#parms").val("parameter=" + typeName);
		cx("parameter=" + typeName);
	});
	
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
		//var bt = $("#billsType").text();
		var searchurl = basePath
				+ "/ea/warehousing/sajax_ea_getInventory.jspa?";
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
				$("span#wpzycount").text(zys);
				var tabletr = "<table width='98%' height='26' align='center' cellspacing='0' cellpadding='1' style='font-size:12px;' class='bannb_01' ><tr><td height='24' align='left' valign='top' class='txt01' >&nbsp;点击选择物品</td></tr></table>";
				tabletr += "<table width='99%' align='center' id='gotable' cellpadding='0' cellspacing='0' class='table'>";
				tabletr += " <tr><th height='21' align='center' bgcolor='#E4F1FA'>选择</th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'>库</th><th align='center' bgcolor='#E4F1FA'>区</th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'>架</th><th align='center' bgcolor='#E4F1FA'>位</th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'>物品名称</th><th align='center' bgcolor='#E4F1FA'>条码</th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'>类型</th><th align='center' bgcolor='#E4F1FA'>库存量</th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'>单位</th><th align='center' bgcolor='#E4F1FA'>规格</th>";
				tabletr += "</tr>";
				for (var i = 0; i < pageForm.list.length; i++) {
					tabletr += "<tr style='cursor: hand;'id = "
							+ pageForm.list[i].inventoryID + ">"; 
					tabletr += "<td id='check' align='center'><input type ='radio' class='ragood' value="
							+ pageForm.list[i].inventoryID + " name='check'/></td>";
					tabletr += "<td id='warehouseName'  align='center'>"
							+ pageForm.list[i].warehouseName + "</td>";
					tabletr += "<td id='areaName'  align='center'>"
							+ pageForm.list[i].areaName + "</td>";
					tabletr += "<td id='frameName'  align='center'>"
							+ pageForm.list[i].frameName + "</td>";
					tabletr += "<td id='positionName'  align='center'>"
							+ pageForm.list[i].positionName + "</td>";
					tabletr += "<td id='goodsName'  align='center'>"
							+ pageForm.list[i].goodsName + "</td>";
					tabletr += "<td id='sortCode'  align='center'>"
							+ pageForm.list[i].barcode + "</td>";					
					tabletr += "<td id='type' align='center'>"
							+ pageForm.list[i].goodsType + "</td>";
					tabletr += "<td id='invenQuantity' align='center'>"
							+ pageForm.list[i].invenQuantity + "</td>";					
					tabletr += "<td id='unit' title='ava' align='center'>"
							+ pageForm.list[i].unit + "</td>";	
					tabletr += "<td id='goodsID' style='display:none' align='center'>"
								+ pageForm.list[i].goodsID + "</td>";
					tabletr += "<td id='acquiesceStandard' align='center'>"
							+ pageForm.list[i].standard + "</td>";
					tabletr += "<td id='pware'  style='display:none' align='center'>"
							+ pageForm.list[i].warehouse + "</td>";
					tabletr += "<td id='parea'  style='display:none' align='center'>"
							+ pageForm.list[i].area + "</td>";
					tabletr += "<td id='pframe'  style='display:none' align='center'>"
							+ pageForm.list[i].frame + "</td>";
					tabletr += "<td id='place' style='display:none' align='center'>"
							+ pageForm.list[i].position + "</td>";
					tabletr += " </tr>";
				}
				tabletr += " </table>";
				$("#body_02").html(tabletr);
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