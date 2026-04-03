$(document).ready(function() {
    
	
	// 提交保存
	$(".save").click(function() {
		
		$("form :input:.ckTextLength").trigger("blur");
		$("form :input:.input3").trigger("blur");
		$("form :input:.posnum").trigger("blur");
		$("form :input:.positiveNumZ").trigger("blur");
		  
          if($("form .error").length)
          { 
            return;
          } 
		$("#mainForm").attr("target", "hidden").attr("action",
				basePath + "ea/budgetplan/ea_saveBudgetPlan.jspa?category="+category);

		document.mainForm.submit.click();
		token = 2;
		 

	});
	$("#selectpr").click(function(){
	  pcx("");	
	
	});
	
	     
       
	
	
	//最大量计算
	$(".jisuan").bind('input propertychange', function() {
				var value = $(this).val();
				if (isNaN(value)) {
					// 非数字
					return;
				}
				var id = $(this).attr("id");
				var maxDay;
				var maxWeek;
				var maxMonth;
				var maxSeason;
				var maxYear;

				if (id == "maxDay") {
					maxDay = value;
					maxWeek = value * 7;
					maxMonth = value * 30;
					maxSeason = maxMonth * 3;
					maxYear = maxMonth * 12;

				}
				if (id == "maxWeek") {
					maxDay = value / 7;
					maxWeek = value;
					maxMonth = maxDay * 30;
					maxSeason = maxMonth * 3;
					maxYear = maxMonth * 12;
				}
				if (id == "maxMonth") {
					maxDay = value / 30;
					maxWeek = maxDay * 7;
					maxMonth = value;
					maxSeason = maxMonth * 3;
					maxYear = value * 12;
				}
				
				if (id == "maxSeason") {
					maxMonth = value / 3;
					maxDay = maxMonth / 30;
					maxWeek = maxDay * 7;
					maxSeason = value;
					maxYear = maxMonth*12;
				}
				
				
				if (id == "maxYear") {
					maxMonth = value / 12;
					maxDay = maxMonth / 30;
					maxWeek = maxDay * 7;
					maxSeason = maxMonth * 3;
					maxYear = value;
				}
                //向上取整
				$("#maxDay").val(Math.ceil(maxDay));
				$("#maxWeek").val(Math.ceil(maxWeek));
				$("#maxMonth").val(Math.ceil(maxMonth));
				$("#maxSeason").val(Math.ceil(maxSeason));
				$("#maxYear").val(Math.ceil(maxYear));

			});

	
	
		// 项目产品分类
	$(document).ready(function() {
		tree2 = new dhtmlXTreeObject("projectTree", "100%", "100%", 0);
		tree2.enableDragAndDrop(false);
		tree2.enableHighlighting(1);
		tree2.enableCheckBoxes(0);
		tree2.enableThreeStateCheckboxes(false);
		tree2.setSkin(basePath + 'js/tree/dhx_skyblue');
		tree2.setImagePath(basePath + "js/tree/codebase/imgs/");
		tree2.loadXML(basePath + "js/tree/common/tree_b.xml");
		tree2.insertNewChild("0", "scode201410284shpd9x4fa0000000005", "项目产品分类",
				0, 0, 0, 0);
		getProjectItem(3)
		tree2.setOnClickHandler(function() {

					getProjectItem(1);

				});

		tree2.setOnDblClickHandler(function() {
					getProjectItem(2);

				});

	});
	
	
	
	
		//调用产品 
	$(function(){
	
	
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
						var typeCN = "&parameter="+typeName + "&pageForm.pageNumber=" + sy;
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
						var typeCN ="&parameter="+ typeName + "&pageForm.pageNumber=" + xy;
						pcx(typeCN);
					} else {
						alert("已是尾页！");
					}
				});

		// 根据输入的产品编号或产品名称查询
		$("input#searchProduct").click(function() {
					var typeName = $("#parameter", $("table#searchpro")).val();
					
					pcx("&parameter=" + typeName);
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
	
	
		
	});


});
function value(){
	var value=$(".inputtext").val();
	if(value==""){
		alert("==========");
	}
}

function re_load() {
	window.opener.location.href=window.opener.location.href
	window.close();

}


// 项目产品分类
function getProjectItem(type) {
  if(type==3){
   	treeid2 ="scode201410284shpd9x4fa0000000005";
   }else{
	treeid2 = tree2.getSelectedItemId();
   }
   treename2 = tree2.getItemText(treeid2);
	tree2.deleteChildItems(treeid2);
	var getListCCodeurl = basePath
			+ "ea/ccode/sajax_ea_getListCCodeByPID.jspa?codeID=" + treeid2
			+ "&date=" + new Date().toLocaleString()+"&category="+category;
	$.ajax({
		url : encodeURI(getListCCodeurl),
		type : "get",
		async : true,
		dataType : "json",
		success : function cbf(data) {
			var member = eval("(" + data + ")");
			var codeList = member.codeList;

			if (codeList.length == 0) {
				if (type == 2) {
					$("#pro").val(treename2);
					$("#project").hide();

					var b = new Array();
					b.unshift(">");
					b.unshift(treename2);
					var parentid = treeid2;
					var parentname = "";

					while (true) {

						parentid = tree2.getParentId(parentid);

						if (parentid != "scode201410284shpd9x4fa0000000005") {

							parentname = tree2.getItemText(parentid);
							b.unshift(">");
							b.unshift(parentname);
						} else {
							var str = b.join("").substring(0,
									b.join("").length - 1);
						   
							$("#mulpro").val(str);
							break;
						}

					}
				}
				return;
			}
			for (var i = 0; i < codeList.length; i++) {

				tree2.insertNewChild(treeid2, codeList[i].codeID,
						codeList[i].codeSn + codeList[i].codeValue, 0, 0, 0, 0);

			}
		},
		error : function cbf(data) {
			alert("数据获取失败！");
		}
	});

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
			+ "ea/prodesign/sajax_ea_getProductDesignList.jspa?category="+category;
	$.ajax({
		url : encodeURI(searchurl + typeCN + "&date="
				+ new Date().toLocaleString()+"&fiveClear="+fiveClear+"&type=01"),
		type : "get",
		async : true,
		dataType : "json",
		data:{
			iscall:"call",
			search:"search"
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
			var tabletr ="";
			tabletr += "<table width='100%' align='center' id='protable' cellpadding='0' cellspacing='0' class='table'>";
			tabletr += " <tr><th height='27' align='center' bgcolor='#E4F1FA'>选择</th>";
			tabletr += "<th align='center' bgcolor='#E4F1FA'>行业</th>";
			tabletr += "<th align='center'  bgcolor='#E4F1FA'>产品条码</th>";
			tabletr += "<th align='center' bgcolor='#E4F1FA'>产品编码</th>";
			tabletr += "<th align='center' bgcolor='#E4F1FA'>产品名称</th>";
			tabletr += "<th align='center' bgcolor='#E4F1FA'>品牌</th>";
			tabletr	+= "<th align='center' bgcolor='#E4F1FA'>型号</th>";
			tabletr += "<th align='center' bgcolor='#E4F1FA'>规格</th>";
			tabletr	+= "<th align='center' bgcolor='#E4F1FA'>单位</th>";
			tabletr	+= "<th align='center' bgcolor='#E4F1FA'>数量</th>";
			tabletr	+= "<th align='center' bgcolor='#E4F1FA'>单价</th>";
			tabletr	+= "<th align='center' bgcolor='#E4F1FA'>金额</th></tr>";
			
			for (var i = 0; i < pageForm.list.length; i++) {
				tabletr += "<tr  style='cursor: hand;' id = "
						+ pageForm.list[i].ppID + ">";
				tabletr += "<td height='27' id='check' align='center'>"
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

