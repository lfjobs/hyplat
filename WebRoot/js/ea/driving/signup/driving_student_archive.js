// ajax查询物品通过芯片
var chipids = new Array();
var i = 0;
$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 
		overlay : 20
			// 
		}).jqmAddClose('.close');//
	// .jqDrag('.drag');//
	/** **ajax查询所有部门列表** */
	var url = basePath
			+ "ea/responsibilities/sajax_n_ea_getoList.jspa?companyID="+ companyID + "&date=" + new Date().toLocaleString()//+"&series=one"+"&level=organization";
	$.ajax({
				url : encodeURI(url),
				type : "get",
				async : false,
				dataType : "json",
				success : function cbf(data) {
					/** **添加部门列表** */
					var member = eval("(" + data + ")");
					var oList = member.organizationlist;
					var data2 = new Array();
					data2[0] = {
						id : companyID,
						pid : '-1',
						text : companyName
					};
					for (var i = 0; i < oList.length; i++) {
						data2[i + 1] = {
							id : oList[i].organizationID,
							pid : oList[i].organizationPID,
							text : oList[i].organizationName
						};
					}
					ts = new TreeSelector($("select#organizationID")[0], data2, '-1');
					ts.createTree();
					$("select#organizationID").show();
					
				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}
							
});	
	
	if(dangan=="dangan"){
		var searchFormHtml=$("div#jqModelSearch").html();	
		$("div#jqModelSearch").remove();
		$('.JQueryflexme').flexigrid({
				allDouble : true,
				height : 345,
				width : 'auto',
				minwidth : 30,
				title : '教务生产跟踪列表'+searchFormHtml,
				minheight : 80,
				buttons : [{
					name : '跟踪详情',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '查询',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '设置每页显示条数',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}]
			});
	}else{
		var searchFormHtml=$("div#jqModelSearch").html();	
		$("div#jqModelSearch").remove();
		$('.JQueryflexme').flexigrid({
			allDouble : true,
			height : 345,
			width : 'auto',
			minwidth : 30,
			title : searchFormHtml,
			minheight : 80,
			buttons : [{
				name : '填写档案出库单',
				bclass : 'add',
				onpress : action
					// 当点击调用方法
				}, {
				separator : true
			}, {
				name : '查看',
				bclass : 'add',
				onpress : action
					// 当点击调用方法
				}, {
				separator : true
			}/*, {
				name : '查询',
				bclass : 'mysearch',
				onpress : action
					// 当点击调用方法
				}, {
				separator : true
			}*/, {
				name : '条码查询',
				bclass : 'mysearch',
				onpress : action
					// 当点击调用方法
				}, {
				separator : true
			}, {
				name : '打印条码',
				bclass : 'checkout',
				onpress : action
			},  {
				name : '打印学员证',
				bclass : 'checkout',
				onpress : action
			},{
				separator : true
			},  {
				name : '编辑学员信息',
				bclass : 'edit',
				onpress : action
			},{
				separator : true
			},  {
				name : '招生报表',
				bclass : 'excel',
				onpress : action
			},{
				separator : true
			}, {
				name : '设置每页显示条数',
				bclass : 'mysearch',
				onpress : action
					// 当点击调用方法
				}, {
				separator : true
			}]
		});
	}
	function action(com, grid) {
		switch (com) {
			case '填写档案出库单' :
				document.location.href = basePath
						+ "ea/clinch/ea_toSaveStudentArchive.jspa?pageNumber="
						+ pNumber;
				break;
			case '查看' :
				if (staffID == "") {
					alert("请选择");
					return;
				}
				var url = basePath
						+ "ea/humanResource/ea_getHumanResource.jspa?showType=edit&cstaff.staffID="
						+ staffID;
				window
						.open(url, '',
								'scrollbars=yes,resizable=yes,channelmode');

				break;
			case '条码查询' :
				$(".jqmWindow", $("#goodsForm")).jqmShow();
				$("#goodsForm #loadcabs")
						.attr(
								"src",
								basePath
										+ "page/ea/main/human/cstaff/loadActiveX.html?code="
										+ Math.random());
				$("#tbody").html("");
				$(".scan").hide();
				$(".manual").show();
				$("#searchGood").hide();
				break;
			/*case '查询' :
				bmDept();
				$("div#searchDivByAdmissions").jqmShow();
				break;	*/
			case '打印条码' :
				if (staffID == "") {
					alert("请选择");
					return;
				}
				var code = $("#" + relationID).find("#recordCode").text();
				var staffId = $("#" + relationID).attr("data-staffID");
				window.open(basePath
						+ "ea/studentManager/barcode.jspa?staffID="
						+ encodeURI(staffId)+"&recordCode="+encodeURI(code));
				break;
			case '打印学员证' :
				var $relationID = $("#" + relationID);
				var staffId = $relationID.attr("data-staffID");
				var companyId = $relationID.attr("data-companyId");
				window.open(basePath
						+ "ea/studentManager/card.jspa?staffID="
						+ encodeURI(staffId)+"&parameter="+encodeURI(companyId));
				break;
			case '编辑学员信息' :
				if (staffID == "") {
					alert("请选择");
					return;
				}
				var url =basePath+ "ea/enroll/ea_getHumanResource.jspa?showType=edit&marketingArchives=marketingArchives"+"&cstaff.staffID="+staffID;
				window.open(url, '','scrollbars=yes,resizable=yes,channelmode');
				break;	
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/clinch/ea_getListStudentArchive.jspa?search="
						+ search + "&module_Identifier=customer_Deal&view_Identifier=educational_train&module_title="+module_title+"&companyGroupLogo="+companyGroupLogo+"&dangan="+dangan;
				numback(url);
				break;
			case '招生报表' :
				var url= basePath
				+ "ea/clinch/ea_toExportByAdmissions.jspa?search="
				+ search+ "&module_Identifier=customer_Deal&view_Identifier=educational_train&module_title="+module_title+"&companyGroupLogo="+companyGroupLogo;
		    	open(url);
				break;	
			case '跟踪详情' :
				if (staffID == "") {
					alert("请选择");
					return;
				}
				var url =basePath+ "ea/enroll/ea_studentInfortrack.jspa?showType=edit"+"&cstaff.staffID="+staffID;
				
				window.open(url, '','scrollbars=yes,resizable=yes,channelmode');

				break;
		}
	}

	$(".JQueryreturns").click(function() {
				notoken = 0;
				$(".jqmWindow").jqmHide();

				loadcabs.window.closePort();// 关闭读数据端口
				// ajax查询物品通过芯片
				chipids = new Array();
				i = 0;

			});

	// 根据输入的条码查询
	$("input#searchGood").click(function() {
				var recordCode = $("#recordCode", $("table#searchgood")).val();
				if ($("#tbody").html().indexOf($.trim(recordCode)) > 0) {
					return;
				}
				$(":input#parms").val("parameter=" + recordCode);

				cx("parameter=" + $.trim(recordCode));
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

	$("tr[id]").click(function() {
				relationID = $(this).attr("id");
				staffID = $.trim($(this).find("span#staffID").text());
				$(this).find("input#relationID").attr("checked", "checked");
			});
	if(dangan=="dangan"){
		$("tr[id]").dblclick(function() {
			$(this).trigger("click");
			action("跟踪详情");
		});
	}else{
		$("tr[id]").dblclick(function() {
			$(this).trigger("click");
			action("查看");
		});
	}
	
	$("#tosearch").click(function() {
		$("#SearchForm").attr("action",
				basePath + "ea/clinch/ea_toSearch.jspa?pageNumber=" + pNumber);
		document.getElementById("SearchForm").submit.click();
	});
	
	$("#toSearchByAdmissions").click(function() {
		$("#searchFormByAdmissions").attr("action",
				basePath + "ea/clinch/ea_toSearchByAdmissions.jspa?pageNumber=" + pNumber +"&dangan="+dangan);
		document.getElementById("searchFormByAdmissions").submit.click();
	});

	// 手动输入点击
	$(".manual").click(function() {
		$(this).hide();
		$(".scan").show();
		$("#searchGood").show();
			// $("#recordCode", $("table#searchgood")).removeAttr("readonly");

		});

	// 扫描输入点击
	$(".scan").click(function() {
		$(this).hide();
		$(".manual").show();
		$("#searchGood").hide();
			// $("#recordCode", $("table#searchgood")).attr({
			// readonly : 'true'
			// });
		});
	
	//----------------选择在职人员操作------------//
	$(".xzry").live("click",function(){
		$("#daoRu").attr("src",basePath+'/ea/academicadmin/ea_getCompanyListEmployeeReferral.jspa');
		$("div#bankJqm").show();
	})
	$("#DaoRuFan").click(function(){// 返回
       $("#bankJqm").hide();
       parent.document.getElementById("mainframe53").style.height = 80 + select * 27 + "px";
	}); 
	$("#DaoRuFanqd").click(function(){// 选择确定
		var childopertionID = window.frames["daoRu"].opertionID;
		if(childopertionID == ""){
			alert("请选择");
			return;
		}
		var staffName = $(window.frames["daoRu"].$('tr#'+childopertionID)[0]).find("span#staffName").text();
		var staffIdentityCard =$(window.frames["daoRu"].$('tr#'+childopertionID)[0]).find("span#staffIdentityCard").text();
		var reference =$(window.frames["daoRu"].$('tr#'+childopertionID)[0]).find("span#reference").text();//mainframe4
		$t=$("form#searchFormByAdmissions");
		$t.find("input#referrer").attr("value",staffName);
		$t.find("input#referrerID").attr("value",childopertionID);
		 $("#daoRu").attr("src","");
         $("#bankJqm").hide();
    });
	//----------------选择在职人员操作------------//

	setInterval(function() {

				var recordCode = $("#recordCode", $("table#searchgood")).val();
				if ($("#goodsForm .scan").is(":hidden")) {
					if (recordCode != "") {
						if ($("#tbody").html().indexOf($.trim(recordCode)) > 0) {
							$("#recordCode", $("table#searchgood")).val("");
							return;
						}
						$("input#parms").val("parameter=" + recordCode);
						cx("parameter=" + $.trim(recordCode));
						$("#recordCode", $("table#searchgood")).val("");

					}

				}
			}, 1000);
});

// ajax查询物品列表
function cx(typeCN) {
	// if (notoken) {
	// alert("正在获取数据！请稍等");
	// return;
	// }
	notoken = 1;
	var searchurl = basePath
			+ "ea/clinch/sajax_ea_getAjaxListStudentArchive.jspa?type=archive&";
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
			var student = member.student;
			if (student == null) {
				notoken = 0;
				return;
			}
			var tabletr = "";
			var staffIDs = '"' + student.staffID + '"';
			tabletr += "<tr style='cursor: hand;' id = " + student.staffID
					+ ">";
			tabletr += "<td id='check' align='center'><input type ='checkbox' class='ragood' value="
					+ student.staffID + " name='check'/></td>";
			tabletr += "<td id='staffCode' align='center'>" + student.staffCode
					+ "</td>";
			tabletr += "<td id='recordCode'  align='center'>"
					+ student.recordCode + "</td>";
			tabletr += "<td id='staffName'  align='center'>"
					+ student.staffName + "</td>";
			tabletr += "<td id='usedNmae' align='center'>" + student.usedNmae
					+ "</td>";
			tabletr += "<td id='sex' align='center'>" + student.sex + "</td>";
			tabletr += "<td id='staffID' style='display:none' align='center'>"
					+ student.staffID + "</td>";
			tabletr += "<td id='birthday'  align='center'>" + student.birthday
					+ "</td>";
			tabletr += "<td id='nationality'  align='center'>"
					+ student.nationality + "</td>";
			tabletr += "<td id='nativePlace'  align='center'>"
					+ student.nativePlace + "</td>";
			tabletr += "<td id='nation'  align='center'>" + student.nation
					+ "</td>";
			tabletr += "<td id='staffIdentityCard'  align='center'>"
					+ student.staffIdentityCard + "</td>";
			tabletr += "<td id='price'  align='center'><a  onclick='viewDetail("
					+ staffIDs + ")'>查看</a></td>";
			tabletr += " </tr>";
			$("#tbody").append(tabletr);
			$("#body_02").show();
			notoken = 0;
			window.status = "数据加载成功";
		},
		error : function cbf(data) {
			notoken = 0;
			alert("获取物品出错！");
		}
	});
}

// 查看详情
function viewDetail(staffID) {
	// alert(staffID);
	// var url = basePath +
	// "ea/soincumbent/ea_getBasicInformation.jspa?staffID="
	// + staffID;
	// window.open(url);
	// var url = basePath
	// +
	// "ea/humanResource/ea_getHumanResource.jspa?showType=edit&cstaff.staffID="
	// + staffID;
	// window.open(url, '', 'scrollbars=yes,resizable=yes,channelmode');
	var url = basePath
			+ "ea/humanResource/ea_getHumanResource.jspa?showType=edit&cstaff.staffID="
			+ staffID;
	window.open(url, '', 'scrollbars=yes,resizable=yes,channelmode');

}

function QueryArchiveInfo(typeCN) {
	typeCN = '"' + typeCN + '"';
	setTimeout("getArchive(" + typeCN + ")", 100);
}

function getArchive(typeCN) {
	for (var i = 0; i < chipids.length; i++) {
		if (chipids[i].indexOf(typeCN) > -1) {
			return false;
		}
	}
	chipids[i] = typeCN;
	typeCN = "parameter=" + typeCN;
	notoken = 1;
	var searchurl = basePath
			+ "ea/clinch/sajax_ea_getStudentArchive.jspa?type=out" + "&";
	$.ajax({
		url : encodeURI(searchurl + typeCN + "&date="
				+ new Date().toLocaleString()),
		type : "get",
		async : true,
		dataType : "json",
		success : function cbf(data) {
			var member = eval("(" + data + ")");
			var staffinfo = member.staffinfo;
			if (staffinfo == null) {
				return;
			}
			var tabletr = "";
			var staffIDs = '"' + staffinfo.staffID + '"';
			tabletr += "<tr style='cursor: hand;' id = " + staffinfo.staffID
					+ ">";
			tabletr += "<td id='check' align='center'><input type ='checkbox' class='ragood' value="
					+ staffinfo.staffID + " name='check'/></td>";
			tabletr += "<td id='staffCode' align='center'>"
					+ staffinfo.staffCode + "</td>";
			tabletr += "<td id='recordCode'  align='center'>"
					+ staffinfo.recordCode + "</td>";
			tabletr += "<td id='staffName'  align='center'>"
					+ staffinfo.staffName + "</td>";
			tabletr += "<td id='usedNmae' align='center'>" + staffinfo.usedNmae
					+ "</td>";
			tabletr += "<td id='sex' align='center'>" + staffinfo.sex + "</td>";
			tabletr += "<td id='staffID' style='display:none' align='center'>"
					+ staffinfo.staffID + "</td>";
			tabletr += "<td id='birthday'  align='center'>"
					+ staffinfo.birthday + "</td>";
			tabletr += "<td id='nationality'  align='center'>"
					+ staffinfo.nationality + "</td>";
			tabletr += "<td id='nativePlace'  align='center'>"
					+ staffinfo.nativePlace + "</td>";
			tabletr += "<td id='nation'  align='center'>" + staffinfo.nation
					+ "</td>";
			tabletr += "<td id='staffIdentityCard'  align='center'>"
					+ staffinfo.staffIdentityCard + "</td>";
			tabletr += "<td id='price'  align='center'><a  onclick='viewDetail("
					+ staffIDs + ")'>查看</a></td>";
			tabletr += " </tr>";
			$("#tbody").append(tabletr);
			$("#body_02").show();
			notoken = 0;
		},
		error : function cbf(data) {
			notoken = 0;
			alert("获取物品出错！");
		}

	});
	i++;
}

function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/clinch/sajax_ea_getListStudentArchive.jspa?pageNumber="
				+ pNumber + "&search=" + search + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value");
}
