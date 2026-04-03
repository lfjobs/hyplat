//导入数据（选择物品）
$(function() {
	  $("#browser").treeview();  
	$(".jqmWindow").jqm({
		modal : false,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
	// 遮罩程度%
	}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	
	$("#chaxun").click(function() {
		var carNum = $("#carNum", $("table#searchgood")).val();
		var deviceStatu=$("#deviceStatu", $("table#searchgood")).val();
		$(":input#parms").val("carNum=" + carNum+"&deviceStatu="+deviceStatu);
		cx("carNum=" + carNum+"&deviceStatu="+deviceStatu);
	});
	
	$("#newG").click(function() {
		cx("");
	});
	// 上一页(车辆)
	$("#wpsy").click(function() {
		var sy = $("#wpsy").attr("title");
		if (sy != 0) {
			var typeName = $(":input#parms").val();
			var typeCN = typeName + "&pageNumber=" + sy;
			cx(typeCN);
		} else {
			alert("已是首页！");
		}
	});
	// 下一页(车辆)
	$("#wpxy").click(function() {
		var xy = $("#wpxy").attr("title");
		if (xy != 0) {
			var typeName = $(":input#parms").val();
			var typeCN = typeName + "&pageNumber=" + xy;
			cx(typeCN);
		} else {
			alert("已是尾页！");
		}
	});
	// 关闭(车辆)
	$("#qdclose").click(function() {
		$("#body_02").jqmHide();

	});

	var cuID = "";
	var carnum = "";
	$("table#gotable tr[id]").live("click", function(event) {
		cuID = this.id;
		$("input.rauser", $(this)).attr("checked", "checked");
		$("tr#" + cuID).children("td").each(function() {
			if (this.id == "carNum") {
				carnum = $(this).text();
			}
		});
	});
	// 确定(车辆)
	$("#qdcar").click(function() {
		if (cuID != "") {
			var flag = true;
			var searchurl = basePath + "/ea/devicebind/sajax_isCarInDevice.jspa?";
			$.ajax({
				url : searchurl,
				type : "post",
				async : false,
				dataType : "json",
				data : {
					"carId":cuID,
					"carnum":carnum
				},
				success : function(data) {
					var count = eval("(" + data + ")");
					var num = parseInt(count);
					if(num > 0){
						alert("该车辆已被绑定，请先去生产");
						flag = false;
						return;
					}
				},
				error : function(data) {
					notoken = 0;
					alert("数据获取失败！");
				}
			});
			if(flag){
				$("tr#" + cuID).children("td").each(function() {
					if (this.id == "twoCode") {
						if ($(this).text() == "null") {
							$("input.twoCode").val("");
						} else {
							$("input.twoCode").val($(this).text());
						}
					} else if (this.id == "oneCode") {
						if ($(this).text() == "null") {
							$("input.oneCode").val("");
						} else {
							$("input.oneCode").val($(this).text());
						}
					} else if (this.id == "deviceCode") {
						$("input.deviceCode").val($(this).text());
					} else if (this.id == "deviceName") {
						$("input.deviceName").val($(this).text());
					} else if (this.id == "companyName") {
						$("input.companyName").val($(this).text());
					} else if (this.id == "department") {
						$("input.department").val($(this).text());
					} else if (this.id == "manageResponse") {
						$("input.manageResponse").val($(this).text());
					} else if (this.id == "staffid") {
						staffid = $(this).text();
					} else if (this.id == "state1") {
						investType = $(this).text();
						if (investType == "01") {
							$("input.investType").val("公司投资设备");
						} else if (investType == "00") {
							$("input.investType").val("挂靠设备");
						} else {
							$("input.investType").val("");
						}
					}else if (this.id == "carid") {
						$("input.carid").val($(this).text());
					}else if (this.id == "goodsid") {
						$("input.goodsid").val($(this).text());
					}else if (this.id == "carNum") {
						$("input.carNum").val($(this).text());
					}
				});
				cuID = "";
				$("#body_02").jqmHide();
				if(investType == "01"){
					gstz();
				}else{
					$("#tzzr").css("display","block");
					$("input.investname").val("");
					$("input.investaccount").val("");
				}
			}
		} else {
			alert("请选择车辆！");
		}
	});
})

// 选择车辆分页函数
function cx(typeCN) {
	if (notoken) {
		alert("正在获取数据！请稍等");
		return;
	}
	notoken = 1;
	$("#wpsy").attr("title", 0);
	$("#wpxy").attr("title", 0);
	$("#wpzy").attr("title", 0);
	var searchurl = basePath + "/ea/devicebind/sajax_carInforList.jspa?";
		$.ajax({
			url : encodeURI(searchurl+ typeCN),
			type : "post",
			async : false,
			dataType : "json",
			success : function(data) {
				var member = eval("(" + data + ")");
				var nologin = member.nologin;
				if (nologin) {
					document.location.href = basePath
							+ "page/ea/not_login.jsp";
				}
				var pageForm =member.pageForm;
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
				$("span#wpdqcount").text(dqy);
				var tabletr = "";
				var lists = pageForm.list;
				for (var i = 0; i < lists.length; i++) {
					var list = lists[i];
					tabletr += "<tr style='cursor: hand;' id = " + list[7]
							+ ">";
					tabletr += "<td id='check' align='center'><input type ='radio' class='rauser' value="
							+ list[7] + " name='check'/></td>";
					tabletr += "<td id='twoCode' align='center'>"
							+ ((list[0] == "null" || list[0] == null) ? ""
									: list[0]) + "</td>";
					tabletr += "<td id='oneCode'  align='center'>"
							+ ((list[1] == "null" || list[1] == null)?"":list[1]) + "</td>";
					tabletr += "<td id='deviceCode'  align='center'>"
							+ list[2] + "</td>";
					tabletr += "<td id='deviceName' align='center'>"
							+ list[3] + "</td>";
					tabletr += "<td id='carNum'  align='center'>"
						+ list[10] + "</td>";
					tabletr += "<td id='companyName'  align='center'>"
							+ list[4] + "</td>";
					tabletr += "<td id='department'  align='center'>"
							+ list[5] + "</td>";
					tabletr += "<td id='manageResponse'  align='center'>"
							+ list[6] + "</td>";
					tabletr += "<td id='carid'  style='display:none' align='center'>"
							+ list[7] + "</td>";
					tabletr += "<td id='goodsid'  style='display:none' align='center'>"
							+ list[11] + "</td>";
					tabletr += "<td id='state1'  style='display:none' align='center'>"
						+ list[9] + "</td>";
					tabletr += "<td id='investType'  align='center'>"+((list[9]=="01")?"公司投资设备":"挂靠设备")
							+ "</td>";
					tabletr += " </tr>";
				}
				$("#tbody2").html(tabletr);
				$("#body_02").jqmShow();

				notoken = 0;
				window.status = "数据加载成功";
			},
			error : function(data) {
				notoken = 0;
				alert("数据获取失败！");
			}
		});
}

// 导入投资责任人
$(function() {
	$("#chaxuntz").click(function() {
		var tzName = $("#tzName", $("table#searchgood")).val();
		var tzAccount=$("#tzAccount", $("table#searchgood")).val();
		var tzCustype = $("#tzCustype", $("table#searchgood")).val();
		$(":input#parms").val("tzName="+tzName+"&tzAccount="+tzAccount+"&tzCustype="+tzCustype);
		tz("tzName="+tzName+"&tzAccount="+tzAccount+"&tzCustype="+tzCustype);
	});
	
	$("#tzzr").click(function() {
		tz("");
	});
	// 上一页(投资人)
	$("#tzsy").click(function() {
		var sy = $("#tzsy").attr("title");
		if (sy != 0) {
			var typeName = $(":input#parms").val();
			var typeCN = typeName + "&pageNumber=" + sy;
			tz(typeCN);
		} else {
			alert("已是首页！");
		}
	});
	// 下一页(投资人)
	$("#tzxy").click(function() {
		var xy = $("#tzxy").attr("title");
		if (xy != 0) {
			var typeName = $(":input#parms").val();
			var typeCN = typeName + "&pageNumber=" + xy;
			tz(typeCN);
		} else {
			alert("已是尾页！");
		}
	});
	// 关闭(投资人)
	$("#tzclose").click(function() {
		$("#body_03").jqmHide();
	});

	var staffid = "";
	$("table#gotabletz tr[id]").live("click", function(event) {
		staffid = this.id;
		$("input.rauser", $(this)).attr("checked", "checked");
	});
	// 确定(投资人)
	$("#tzResponse").click(function() {
		if (staffid != "") {
			$("tr#" + staffid).children("td").each(function() {
				if (this.id == "investname") {
					$("input.investname").val($(this).text());
				} else if (this.id == "investaccount") {
					$("input.investaccount").val($(this).text());
				} else if (this.id == "investstaffid") {
					$("input.investstaffid").val($(this).text());
				}else if (this.id == "investsccid") {
					$("input.investsccid").val($(this).text());
				}
			});
			staffid = "";
			$("#body_03").jqmHide();
		} else {
			alert("请选择投资责任人！");
		}
	});
})
function tz(typeCN) {
	if (notoken) {
		alert("正在获取数据！请稍等");
		return;
	}
	notoken = 1;
	$("#tzsy").attr("title", 0);
	$("#tzxy").attr("title", 0);
	$("#tzzy").attr("title", 0);
	var searchurl = basePath + "/ea/devicebind/sajax_selInvestRespose.jspa?";
	$
			.ajax({
				url : (searchurl+ typeCN),
				type : "post",
				async : false,
				dataType : "json",
				success : function(data) {
					var member = eval("(" + data + ")");
					var nologin = member.nologin;
					if (nologin) {
						document.location.href = basePath
								+ "page/ea/not_login.jsp";
					}
					var pageForm =member.pageForm;
					if (pageForm == null) {
						alert("没有数据");
						notoken = 0;
						return;
					}
					var tzdqy = pageForm.pageNumber;// 当前页
					var tzzys = pageForm.pageCount;// 总页数
					if (tzdqy > 1) {
						$("#tzsy").attr("title", tzdqy - 1);
					}
					if (tzdqy < tzzys) {
						$("#tzxy").attr("title", tzdqy + 1);
					}
					$("span#tzzycount").text(tzzys);
					$("span#tzdqcount").text(tzdqy);
					$("#tbody3").html("");
					var tabletr = "";
					var lists = pageForm.list;
					console.log($("#body_03").html());
					for (var i = 0; i < lists.length; i++) {
						var list = lists[i];
						tabletr += "<tr style='cursor: hand;' id = " + list[4]
								+ ">";
						tabletr += "<td id='check' align='center'><input type ='radio' class='rauser' value="
								+ list[4] + " name='check'/></td>";
						tabletr += "<td id='investaccount' align='center'>"
								+ list[0] + "</td>";
						tabletr += "<td id='investcustype'  align='center'>"
								+ list[1] + "</td>";
						tabletr += "<td id='staffCode'  align='center'>"
								+ ((list[2] == "null" || list[2] == null) ? ""
									: list[2]) + "</td>";
						tabletr += "<td id='investname' align='center'>"
								+ list[3] + "</td>";
						tabletr += "<td id='investsccid' align='center' style='display:none'>"
								+ list[5] + "</td>";
						tabletr += "<td id='investstaffid' align='center' style='display:none'>"
							+ list[4] + "</td>";
						tabletr += " </tr>";
					}
					$("#tbody3").html(tabletr);
					$("#body_03").jqmShow();
					
					notoken = 0;
					window.status = "数据加载成功";
				},
				error : function(data) {
					notoken = 0;
					alert("数据获取失败！");
				}
			});
}

function gstz(){
	var searchurl = basePath + "/ea/devicebind/sajax_selComInvestment.jspa?";
	$.ajax({
		url : searchurl,
		type : "post",
		async : false,
		dataType : "json",
		success : function(data) {
			var member = eval("(" + data + ")");
			var list = member.invest;
			var nologin = member.nologin;
			if (nologin) {
				document.location.href = basePath
						+ "page/ea/not_login.jsp";
			}
			if (list == null) {
				alert("没有数据");
				notoken = 0;
				return;
			}
			for (var i = 0; i < list.length; i++) {
				var x = list[i];
				$("input.investname").val(x[3]);
				$("input.investaccount").val(x[0]);
				$("input.investstaffid").val(x[4]);
				$("input.investsccid").val(x[5]);
				break;
			}
			$("#tzzr").css("display","none");
		},
		error : function(data) {
			notoken = 0;
			alert("数据获取失败！");
		}
	});
}

//保存时触发进行非空验证
$(function(){
	$("#bc").click(function(){
		var flag = true;
		var carId = $("input.carid").val();
		var goodsid = $("input.goodsid").val();
		var investsccid = $("input.investsccid").val();
		var investstaffid = $("input.investstaffid").val();
		if(carId == "" || goodsid == "" || investsccid == "" || investstaffid == ""){
			alert("有数据不全，请重新获取数据")
			flag = false;
			return;
		}
		if(flag){
			$(".alert_2").show();
			token = 2;
			$('#baocun').attr("target","hidden")
	        .attr(
	            "action",
	            basePath
	            + "/ea/devicebind/ea_addDeviceBind.jspa?");
	        document.baocun.submit.click();
		}
		
	});
})



function re_load() {
	window.opener.location.href=window.opener.location.href
	window.close();
}

