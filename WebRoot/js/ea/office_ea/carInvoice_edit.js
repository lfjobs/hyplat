$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	if (type == "c") {
		title = "购车发票管理公司汇总"
	} else if (type == "g") {
		title = "购车发票管理集团汇总"
	} else {
		title = "购车发票管理";
	}
   if(carID!=undefined&&carID!=""){
	$('.JQueryflexme').flexigrid({
				height : 165,
				width : 'auto',
				minwidth : 30,
				title : '购车发票管理',
				minheight : 80,
				buttons : [{
					name : '添加',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '修改',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '删除',
					bclass : 'delete',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},  {
					name : '导出',
					bclass : 'excel',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '设置每页显示条数',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}]
			});
			
   }else{
   		$('.JQueryflexme').flexigrid({
				height : 165,
				width : 'auto',
				minwidth : 30,
				title : title,
				minheight : 80,
				buttons : [ {
					name : '查询',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},  {
					name : '导出',
					bclass : 'excel',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '设置每页显示条数',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}]
			});
   	
   }
	function action(com, grid) {
		switch (com) {
			case '添加' :
			    $("#addForm .error").remove();
			    $("#addForm .correct").remove();
			    $("#updateForm .error").remove();
			    $("#updateForm .correct").remove();
			    
				invoiceID = "";
				$("input.JQuerypersonvalue").attr("checked", false);
				document.addForm.reset();
				$("#numCarID").val(carID);
				$("#jqModel").jqmShow();
				break;
			case '修改' :
				if (invoiceID == "") {
					alert('请选择!');
					return;
				}
				document.updateForm.reset();
				$t = $("table#stafftableupdate");
				$p = $("tr#" + invoiceID);
				$p.find("span[id]").each(function() {
							$t.find(":input[name]#" + this.id).val($(this)
									.text());
						});
				$("#jqModelup").jqmShow();
				break;
			case '删除' :
				if (invoiceID == '') {
					alert("请选择！");
					return;
				}
				$f = $('#carInvoiceForm');
				$f.find(':input#invoiceID').val(invoiceID);
				if (confirm("是否删除？")) {
					$("#carInvoiceForm").attr("target", "hidden").attr(
							"action",
							basePath + "ea/carinvoice/ea_deleteCarinvoice.jspa?pageNumber="+ pNumber  + "&carinvoice.invoiceID="+ invoiceID);
					document.carInvoiceForm.submit.click();
					token = 2;
//					$("tr#" + invoiceID).remove();
//					invoiceID = "";
					
				}
				break;
			case '查询':
				$("#jqModelcarSearch").jqmShow();
				break;
			case '设置每页显示条数' :
				var url = basePath+ "ea/carinvoice/ea_getCarInvoiceList.jspa?search="+ search+"&carInformation.carID="
						+ carID+"&type="+type;
				numback(url);
				break;
			case '导出' :
				var url = basePath+ "ea/carinvoice/ea_showCarinvoiceExcels.jspa?";
				open(url);
				break;
		}
	}
	//单击事件
	$(".JQueryflexme tr[id]").click(function() {
				invoiceID = this.id;
				if (personurl) {
					$("#mainframe").attr("src", personurl + invoiceID);
				}
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	$(".JQueryflexme tr[id]").dblclick(function() {
				action('修改');// 当双击时出发 action方法.等价于先选中再点击修改按钮
			});
	// 车辆添加取消	
	$("input.JQueryreturn").click(function() {
				$("#jqModel").jqmHide();
				re_load();
			});
	//车辆添加保存
	$("input.JQuerySubmit").click(function() {
				$(".put3").trigger("blur");
				if ($("#addForm .error").length) {
					alert($("form .error").length)
					
					return;
				}

					$("#addForm")
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/carinvoice/ea_saveCarinvoice.jspa?pageNumber="
											+ pNumber );
					document.addForm.submit.click();
					token = 2;
					document.addForm.reset();
					
					return;
			});
		// 车辆修改取消	
	$("input.JQueryreturns").click(function() {
				$("#jqModelup").jqmHide();
				re_load();
			});
	// 新增物品
	$(".xzwp").click(function() {
				window.open(basePath
						+ "ea/goodsmanage/ea_getListGoodsManage.jspa?");
			});
	//选择车辆返回
	$(".JQueryreturngoods").click(function() {
				notoken = 0;
				var numes="";
				$("#carNum", $("table#searchgood")).attr("value",numes);
				$("#carFrameNum", $("table#searchgood")).attr("value",numes);
				$("#carType", $("table#searchgood")).attr("value",numes);
				$(":input#parms").attr("value",numes);
				$(".jqmWindow", $("#goodsForm")).jqmHide();
			});
	// 修改保存
	$("input.JQuerySubmits").click(function() {
		if ($("form .error").length) {
			return;
		}
		$("#updateForm")
				.attr("target", "hidden")
				.attr(
						"action",
						basePath
								+ "ea/carinvoice/ea_updateCarinvoice.jspa?pageNumber="
								+ pNumber + "&search=" + search+ "&carinvoice.invoiceID="+ invoiceID);
		document.updateForm.submit.click();
		token = 2;
	});
	//车辆查询
	$("#searchCar").click(function() {
		if(carID!=undefined&&carID!=""){
			$("#carIDs").val(carID);
		}
		$("#carSearchForm").attr(
				"action",
				basePath + "/ea/carinvoice/ea_toSearch.jspa?pageNumber="
						+ pNumber);
		document.getElementById("carSearchForm").submit();
		$("#carSearchTable").find(":input[name]").val("");
	})	;
			
});
function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/carinvoice/ea_getCarInvoiceList.jspa?pageNumber="
				+ pNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value")+"&carInformation.carID="
						+ carID+"&type="+type;
}

/** **********************************选择车辆**************************************** */
$(document).ready(function() {
	var cuID = "";
	$("table#gotable tr[id]").live("click", function(event) {
				cuID = this.id;
				$("input.rauser", $(this)).attr("checked", "checked");
			});
	// 导入数据（选择物品）
		$("#newG").click(function() {
				cx("");
				$(".jqmWindow", $("#goodsForm")).jqmShow();
			});
	// 根据车牌号查询
	$("#chaxun").click(function() {
				var typeName = $("#carNum", $("table#searchgood")).val();
				var typeType=$("#carType", $("table#searchgood")).val();
				$(":input#parms").val("parameter=" + typeName+"&typeType="+typeType);
				cx("parameter=" + typeName+"&typeType="+typeType);
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
		$("#qdcar").click(function() {
		if (cuID != "") {
			$("tr #" + cuID).children("td").each(function() {
				if (this.id == "contactUserID") {
					$("input#contactUserID", $("table#stafftable")).val($(this)
							.text());
				} else {
					$("span#" + this.id, $("table#stafftable"))
							.text($(this).text());
				}
			});
			cuID="";
			$(".jqmWindow", $("#goodsForm")).jqmHide();
		} else {
			alert("请选择车辆！");
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
		var searchurl = basePath
				+ "ea/carinvoice/sajax_n_ea_getcmotornull.jspa?";
		$.ajax({
			url : encodeURI(searchurl+ typeCN+ "&date="
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
				var tabletr = "<table width='98%' height='26' align='center' cellspacing='0' cellpadding='1' style='font-size:12px;' class='bannb_01'><tr><td height='24' align='left' valign='top' class='txt01' >&nbsp;点击选择物品</td></tr></table>";
				tabletr += "<table width='99%' align='center' id='gotable' cellpadding='0' cellspacing='0' class='table'>";
				tabletr += " <tr><th height='21' align='center' bgcolor='#E4F1FA'>选择</th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'>车牌号</th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'>车型</th><th align='center' bgcolor='#E4F1FA'>发动机号</th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'>负责人</th></tr>";
				for (var i = 0; i < pageForm.list.length; i++) {
					tabletr += "<tr style='cursor: hand;' id = "
							+ pageForm.list[i].carID + ">";
					tabletr += "<td id='check' align='center'><input type ='radio' class='rauser' value="
							+ pageForm.list[i].carID + " name='check'/></td>";
					tabletr += "<td id='carNum' align='center'>"
							+ pageForm.list[i].carNum + "</td>";
					tabletr += "<td id='carType'  align='center'>"
							+ pageForm.list[i].carType + "</td>";
					tabletr += "<td id='engineNum'  align='center'>"
							+ pageForm.list[i].engineNum + "</td>";
					tabletr += "<td id='staffName' align='center'>"
							+ pageForm.list[i].staffName + "</td>"	;
					tabletr += "<td id='contactUserID'  style='display:none' align='center'>"
							+ pageForm.list[i].carID + "</td>";
					tabletr += " </tr>";
				}
				tabletr += " </table>";
				$("#body_02").html(tabletr);
				$("#body_02").show();
				
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