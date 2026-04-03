$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	$('.JQueryflexme').flexigrid({
				height : 165,
				width : 'auto',
				minwidth : 30,
				title : '车辆信息管理',
				minheight : 80,
				buttons : [{
					name : '添加',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '查看',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},{
					name : '查询',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '车辆状态',
					bclass : 'delete',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '调离',
					bclass : 'delete',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},{
					name : '修改',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '导出',
					bclass : 'excel',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},{
					name : '设置每页显示条数',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}]
			});
			$('.carList').flexigrid({
				height : 165,
				width : 'auto',
				minwidth : 30,
				title : '车辆信息管理',
				minheight : 80
				
			});
			
	function action(com, grid) {
		switch (com) {
			case '添加' :
				carID = "";
				$("input.JQuerypersonvalue").attr("checked", false);
				document.cstaffForm.reset();
				$t = $("table#stafftable");
				$t.find("#pic").attr("src", "");
					/*var treeName ;
					var treePID ;
					var treePName;
				if(parent.parent.frames["leftFrame"] == undefined){
					 treeName = parent.frames["leftFrame"].tree.getItemText(treeID);
					 treePID = parent.frames["leftFrame"].tree.getParentId(treeID);
					 treePName = parent.frames["leftFrame"].tree.getItemText(treePID);
				}else{
					 treeName = parent.parent.frames["leftFrame"].tree.getItemText(treeID);
					 treePID = parent.parent.frames["leftFrame"].tree.getParentId(treeID);
					 treePName = parent.parent.frames["leftFrame"].tree.getItemText(treePID);
				}*/
				$("span#companyNames").text(treeNames);
				$("span#dept").text(treePName);
				$("#jqModel").jqmShow();
				break;
			case '修改' :
				if (carID == "") {
					alert('请选择!');
					return;
				}
				document.cstaffForms.reset();
				$t = $("table#stafftables");
				$p = $("tr#" + carID);
				$p.find("span[id]").each(function() {
							$t.find(":input[name]#" + this.id).val($(this)
									.text());
						});
				$("#jqModels").jqmShow();
				break;
			case '车辆状态' :
				if (carID == "") {
					alert('请选择！');
					return
				}
				document.cstaffFormes.reset();
				$t = $("table#stafftablees");
				$p = $("tr#" + carID);
				$p.find("span[id]").each(function() {
							$t.find(":input[name]#" + this.id).val($(this)
									.text());
						});
				$("#jqModeles").jqmShow();
				break;
			case '调离':
				if(carID==""){
					alert('请选择');
					return 
				}
				
				document.cstaffFormdept.reset();
				$t = $("table#stafftabledept");
				$p = $("tr#" + carID);
				$p.find("span[id]").each(function() {
							$t.find(":input[name]#" + this.id).val($(this)
									.text());
						});
				$("#jqModeldept").jqmShow();
				break;
			case '查看':
				if(carID==""){
					alert('请选择');
					return 
				}
				document.location.href=basePath
				+ "ea/car/ea_getCarByID.jspa?carInformation.carID="+carID;
				break;
			case '查询':
				$("#jqModelcarSearch").jqmShow();
				break;
			case '导出' :
				url = basePath + "ea/car/ea_showExcel.jspa?search=" + search;
				open(url);
				break;
			//case '车辆准载座位' :
				// 综合考评
				//if (carID == "") {
					///alert("请选择具体车辆！");
					//return;
				//}
				//personurl = basePath
						//+ "ea/carmaintain/ea_getListCarMaintain.jspa?pageNumber="
						//+ pNumber + "&carMaintain.carID=";
				//$("#mainframe")
						//.attr(
							//	"src",
								//basePath
									//	+ "ea/carmaintain/ea_getListCarMaintain.jspa?pageNumber="
									//	+ pNumber + "&carMaintain.carID="
										//+ carID);
				//break;
			//case '车辆菜单' :
				///$(".menu01").show();
				//break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/car/ea_getCarInformationList.jspa?search="
						+ search;
				numback(url);
				break;
		}
	}	
	
	//双击事件
	$(".JQueryflexme tr[id]").dblclick(function() {
			document.location.href=basePath
				+ "ea/car/ea_getCarByID.jspa?carInformation.carID="+carID;
			});
			
	//单击事件
	$(".JQueryflexme tr[id]").click(function() {
				carID = this.id;
				if (personurl) {
					$("#mainframe").attr("src", personurl + carID);
				}
				carNums = $(this).find("span#carNum").text();
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
			
	//添加保存
	$("input.JQuerySubmit").click(function() {
				$(".put3").trigger("blur");
				if ($("form .error").length) {
					return;
				}
				if($("#engineNum",$("#cstaffForm")).text() == ''){
					alert("请选择车辆！");
					return;
				}
				if (carID == "") {
					$("#cstaffForm")
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/car/ea_saveCarInformation.jspa?pageNumber="
											+ pNumber + "&search=" + search);
					document.cstaffForm.submit.click();
					document.cstaffForm.reset();
					token = 2;
					return;
				}
				$("#cstaffForm")
						.attr("target", "hidden")
						.attr(
								"action",
								basePath
										+ "ea/car/ea_saveCarInformation.jspa?pageNumber="
										+ pNumber + "&search=" + search);
				document.cstaffForm.submit.click();
				token = 2;

			});
			
			// 修改保存
		$("input.JQuerySubmits").click(function() {
		if ($("form .error").length) {
			return;
		}
		$("#cstaffForms")
				.attr("target", "hidden")
				.attr(
						"action",
						basePath
								+ "ea/car/ea_updateCarInformation.jspa?pageNumber="
								+ pNumber + "&search=" + search);
		document.cstaffForms.submit.click();
		token = 2;

	});
	
	// 下线保存
			$("input.JQuerySubmites").click(function() {
			if ($("form .error").length) {
				return;
			}
			$("#cstaffFormes")
					.attr("target", "hidden")
					.attr(
							"action",
							basePath
									+ "ea/car/ea_delCarInformation.jspa?pageNumber="
									+ pNumber + "&search=" + search);
			document.cstaffFormes.submit.click();
			token = 2;

		});
		
	// 调离保存
		$("input.diaoli").click(function() {
		var organizationIDs=$("select#organizationID  option:selected").text();
		if ($("form .error").length) {
			return;
		}
		if(organizationIDs =='' || organizationIDs == '请选择'){
			alert("请选择部门！！！");
			return;
		}else{
			var pername=$("select#person option:selected").text();
			$("#cstaffFormdept")
					.attr("target", "hidden")
					.attr(
							"action",
							encodeURI(basePath
									+ "ea/car/ea_Depttransfer.jspa?pageNumber="
									+ pNumber+ "&carInformation.staffName="+pername + "&search=" + search ));
			document.cstaffFormdept.submit.click();
		token = 2;
		}
		
		
	});
	
	//物品返回
	$(".JQueryreturngoods").click(function() {
				notoken = 0;
				var numes="";
				$("#carNum", $("table#searchgood")).attr("value",numes);
				$("#carFrameNum", $("table#searchgood")).attr("value",numes);
				$("#carType", $("table#searchgood")).attr("value",numes);
				$(":input#parms").attr("value",numes);
				$(".jqmWindow", $("#goodsForm")).jqmHide();
			})	;
	// 添加取消	
	$("input.JQueryreturn").click(function() {
				$("#jqModel").jqmHide();
				re_load();
			});
	// 修改取消
	$("input.JQueryreturns").click(function() {
				$("#jqModels").jqmHide();
				re_load();
			});
	// 调离取消
	$("input.JQueryreturndept").click(function() {
				$("#jqModeldept").jqmHide();
				re_load();
			});
	// 下线取消
	$("input.JQueryreturnes").click(function() {
				$("#jqModeles").jqmHide();
				re_load();
			});
	//车辆菜单事件	
	$(".menu00").click(function() {
				$(this).hide();
			});
	//搜索窗口
	$(".close").click(function() {// 取消
				$("#jqModel").jqmHide();
				re_load();
			});
	
	//车辆搜索
	$("#searchCar").click(function() {
		$("#carSearchForm").attr(
				"action",
				basePath + "/ea/car/ea_toSearchCar.jspa?pageNumber="
						+ pNumber);
		document.getElementById("carSearchForm").submit();
		$("#carSearchTable").find(":input[name]").val("");
	});	
	//查询事件
	$("#tosearch").click(function() {	
		$("#postSearchForm").attr("action",
				basePath + "ea/car/ea_toSearch.jspa?pageNumber=" + pNumber);
		document.postSearchForm.submit.click();
	});
	$(".JQueryflexme").find("select").each(function() {
				$s = $(this).hide();
				$o = $("<span/>").text($s.find("option:selected").text());
				$o.insertAfter($s);
			});
});

//查询部门事件
$(function(){
	var url = basePath
			+ "ea/corganization/sajax_ea_getOrganizationLists.jspa?date="
			+ new Date().toLocaleString();
	$.ajax({
				url : encodeURI(url),
				type : "post",
				async : true,
				dataType : "json",
				data : {
					"organizationID" : companyID
				},
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var organizationList = member.organizationList;
					var str = "<option value=''>请选择</option>";
					for (var i = 0; i < organizationList.length; i++) {
						var obj = organizationList[i];
						str += "<option value='" + obj.organizationID + "'>"
								+ obj.organizationName + "</option>";
					}
					$("#organizationID").html(str);

				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}		
	});	
	// 根据公司ID和部门ID查询责任人
	$("#organizationID").change(function(){
		$("option", $("select#person")).remove();
		var organizationIDs=$("select#organizationID  option:selected").val();
		var url = basePath
				+ "ea/car/sajax_ea_getStaffList.jspa?carInformation.organizationID=" + organizationIDs ;
		$.ajax({
			url : encodeURI(url),
			type : "get",
			async : true,
			dataType : "json",
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				var persons = member.stafflist;
				var str = "<option value=''>全部</option>";
				for (var i = 0; i < persons.length; i++) {
					var obj = persons[i];
					str += "<option value='" + obj[0] + "'>" + obj[1] +"</option>";
				}
				$("#person").html(str);
			}
		});
	});
});
//刷新事件
function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/car/ea_getCarInformationList.jspa?pageNumber=" + pNumber
				+ "&pageForm.pageNumber=" + $("#pageNumber").attr("value");
}
/** **********************************选择物品**************************************** */
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
				var typeJia=$("#carFrameNum", $("table#searchgood")).val();
				var typeType=$("#carType", $("table#searchgood")).val();
				$(":input#parms").val("parameter=" + typeName+"&typeJia="+typeJia+"&typeType="+typeType);
				cx("parameter=" + typeName+"&typeJia="+typeJia+"&typeType="+typeType);
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
				+ "ea/car/sajax_ea_getcarnull.jspa?";
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
				tabletr += "<th align='center' bgcolor='#E4F1FA'>发动号</th><th align='center' bgcolor='#E4F1FA'>车辆类型</th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'>车架号</th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'>注册日期</th></tr>";
				for (var i = 0; i < pageForm.list.length; i++) {
					tabletr += "<tr style='cursor: hand;' id = "
							+ pageForm.list[i].carID + ">";
					tabletr += "<td id='check' align='center'><input type ='radio' class='rauser' value="
							+ pageForm.list[i].carID + " name='check'/></td>";
					tabletr += "<td id='carNum' align='center'>"
							+ pageForm.list[i].carNum + "</td>";
					tabletr += "<td id='engineNum'  align='center'>"
							+ pageForm.list[i].engineNum + "</td>";
					tabletr += "<td id='carType'  align='center'>"
							+ pageForm.list[i].carType + "</td>";
					tabletr += "<td id='carFrameNum' align='center'>"
							+ pageForm.list[i].carFrameNum + "</td>"	;
					tabletr += "<td id='registrationDate'  align='center'>"
							+ pageForm.list[i].registrationDate + "</td>";
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
