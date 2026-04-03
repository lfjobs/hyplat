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
		
 if(module_title=="科一基本信息录入"){
	$('.JQueryflexme').flexigrid({
		        allDouble:true,
				height : 345,
				width : 'auto',
				minwidth : 30,
				title : module_title,
				minheight : 80,
				buttons : [{
					name : '查询',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					},{
					separator : true
				},{
					name : '设置每页显示条数',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					},{
					separator : true
				},{
					name : '窗口扫描',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					},{
					separator : true
				}, {
				name : '条码芯片：<input size ="15" type="text" id="bc"  onblur="queryByBarCode();"/>',
				bclass : 'mysearch',
				onpress : action
					// 当点击调用方法
				}, {
					separator : true
				}, {
					name : '打印条码',
					bclass : 'checkout',
					onpress : action
				} ,{
					separator : true
				},  {
					name : '打印学员证背面',
					bclass : 'checkout',
					onpress : action
				},{
					separator : true
				},{
					name : '历史打印',
					bclass : 'checkout',
					onpress : action
				},{
					separator : true
				}, {
					name : '浏览学员预约信息',
					bclass : 'mysearch',
					onpress : action
				} ,{
					separator : true
				}, {
					name : '导出',
					bclass : 'excel',
					onpress : action
				} ,{
					separator : true
				}]
			});
				$(".keyi").show();
 }else{
 		$('.JQueryflexme').flexigrid({
		        allDouble:true,
				height : 345,
				width : 'auto',
				minwidth : 30,
				title : module_title,
				minheight : 80,
				buttons : [{
					name : '查询',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					},{
					separator : true
				},{
					name : '设置每页显示条数',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					},{
					separator : true
				},{
					name : '窗口扫描',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					},{
					separator : true
				}, {
				name : '条码芯片：<input size ="15" type="text" id="bc"  onblur="queryByBarCode();"/>',
				bclass : 'mysearch',
				onpress : action
					// 当点击调用方法
				}, {
					separator : true
				}, {
					name : '打印条码',
					bclass : 'checkout',
					onpress : action
				} ,{
					separator : true
				}, {
					name : '浏览学员预约信息',
					bclass : 'mysearch',
					onpress : action
				} ,{
					separator : true
				}, {
					name : '导出',
					bclass : 'excel',
					onpress : action
				} ,{
					separator : true
				}]
			});
			
	$(".keyi").hide();
 }
	function action(com, grid) {
		switch (com) {
			case '查询':
				$("#jqModelSearch").jqmShow();
				break;	
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/clinch/ea_getListContactUser.jspa?search="
						+ search+"&module_Identifier="+module_Identifier+"&view_Identifier="+view_Identifier+"&module_title="+module_title+"&educationalCategories="+educationalCategories+"&theModule="+theModule+"&companyGroupLogo="+companyGroupLogo;
				numback(url);
				break;
			case '窗口扫描' :
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
			case '打印条码' :
				if (staffID == ""||relationID=="") {
					alert("请选择");
					return;
				}
				var code = $("tr#" + relationID).find("span#recordCode").text();
				window.open(basePath
						+ "ea/studentManager/barcode.jspa?staffID="
						+ encodeURI(staffID)+"&recordCode="+encodeURI(code));
				break;
			case '打印学员证背面' :
				window.open(basePath
						+ "ea/studentManager/cardback.jspa");
				break;	
		    case '历史打印' :
		      document.location.href=basePath+"/ea/clinch/ea_getListContactUser.jspa?module_Identifier=customer_Deal&educationalCategories=01&view_Identifier=educational_train&module_title=科一基本信息录入&typeprint=history"+"&companyGroupLogo="+companyGroupLogo;
		      break;
		    case '浏览学员预约信息' :
				window.open(basePath
						+ "ea/appointment/ea_getListDtDrivingAppointmentRecordCompany.jspa?date="+new Date(),'','fullscreen=1,toolbar=0,directories=0,status=0,menubar=0,scrollbars=0,resizable=0');
				break;  
		    case '导出' :
		    	var url= basePath
				+ "ea/clinch/ea_exportExcelDrivingStudentView.jspa?search="
				+ search+"&module_Identifier="+module_Identifier+"&view_Identifier="+view_Identifier+"&module_title="+module_title+"&educationalCategories="+educationalCategories+"&theModule="+theModule+"&companyGroupLogo="+companyGroupLogo;
		    	open(url);
		    	break;
		}
	}
	$("tr[id]").click(function(){
		relationID = $(this).attr("id");
		staffID=$.trim($(this).find("span#staffID").text());
		$(this).find("input#relationID").attr("checked","checked");
	})
	$(".JQueryflexme tr[id]").dblclick(function(){
		if(companyIDLogin!=$.trim($(this).find("span#companyID").text())){
			alert("请登录 “"+$(this).find("span#companyName").text()+"” 操作");
			return;
		}
		relationID = $(this).attr("id");
		var staffID=$.trim($(this).find("span#staffID").text());
		var url =basePath+ "ea/enroll/ea_getHumanResource.jspa?showType=edit"+"&cstaff.staffID="+staffID+"&module_title="+module_title+"&educationalCategories="+educationalCategories+"&theModule="+theModule;
		window.open(url, '','scrollbars=yes,resizable=yes,channelmode');
	})
	$("#tosearch").click(function(){
		$(".ckTextLength", $("table#cataffSearchTable")).trigger("blur");
		if ($("#cataffSearchTable .error").length) {
			alert("请按提示填写查询条件");
			return;
		}
		$("#SearchForm").attr("action",basePath+ "ea/clinch/ea_toSearch.jspa?pageNumber="+pNumber+"&companyGroupLogo="+companyGroupLogo);
		document.getElementById("SearchForm").submit.click();
	})
	
	
	
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
	$("tr[id]").dblclick(function() {
				$(this).trigger("click");
				action("查看");
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
			
			
			
			
			
		//打印预览
	$(".print").click(function(){
	    var arr=new Array(); 
		$("#body_02").find("input:checkbox[name=check]:checked").each(function(){
			arr.push($(this).val());
		});
		if(!arr.length){
			alert("请选择!");
			return;
		}
		$("#SearchForm #staffIdArr").val(arr.toString());
		$("#SearchForm").attr("target","_blank").attr("action",basePath+ "ea/clinch/ea_toPrintPre.jspa?");
		document.SearchForm.submit.click();
		$("#SearchForm").attr("target","")
	   });
	   
	   
	 //全选
	   
	  $("#fullcheck").click(function(){
	  	if($(this).attr("checked")==true){
          $("#body_02").find("input:checkbox[name=check]").attr("checked",true);
	  	}else{
	  	  $("#body_02").find("input:checkbox[name=check]").attr("checked",false);
	  	}
	  	
	  });
	
});
document.onkeydown = function(evt) {// 捕捉回车
		evt = (evt) ? evt : ((window.event) ? window.event : ""); // 兼容IE和Firefox获得keyBoardEvent对象
		var key = evt.keyCode ? evt.keyCode : evt.which; // 兼容IE和Firefox获得keyBoardEvent对象的键值
		var activeElementId = document.activeElement.id;// 当前处于焦点的元素的id
		if (key == 13 && activeElementId == "bc") {
			queryByBarCode();// 要触发的方法
		}
};

	// 快速条码查询
function queryByBarCode() {
		$("#jqModelSearch #staffCode").val($("#bc").val());
		$("#SearchForm").attr("action",basePath+ "ea/clinch/ea_toSearch.jspa?pageNumber="+pNumber);
		document.getElementById("SearchForm").submit.click();
}


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
		url : encodeURI(searchurl + typeCN +"&view_Identifier="+view_Identifier+"&module_title="+module_title+"&companyGroupLogo="+companyGroupLogo+"&date="
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
	var url =basePath+ "ea/enroll/ea_getHumanResource.jspa?showType=edit"+"&cstaff.staffID="+staffID+"&module_title="+module_title+"&educationalCategories="+educationalCategories+"&theModule="+theModule;
		window.open(url, '','scrollbars=yes,resizable=yes,channelmode');

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






/*
    
function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/contactuser/ea_getListContactUser.jspa?pageNumber="
				+ pNumber + "&search=" + search + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value");
}*/


//打印学员证正面
function printCard(staffID,companyID){
	
	window.open(basePath
						+ "ea/studentManager/card.jspa?staffID="
						+ encodeURI(staffID)+"&parameter="+encodeURI(companyID));
	
	
}
$(function() {
	var url = basePath
			+ "ea/company/sajax_n_ea_getCompanyList.jspa?date01="
			+ new Date();
	$.ajax({
				url : encodeURI(url),
				type : "get",
				async : false,
				dataType : "json",
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var companylist = member.companylist;
					var data1 = new Array();
					data1[0] = {
							id : companyIDLogin,
							pid : '-1',
							text : companyNameLogin
						};
					for (var i = 0; i < companylist.length; i++) {
						data1[i + 1] = {
							id : companylist[i].companyID,
							pid : companylist[i].companyPID,
							text : companylist[i].companyName
						};
					}
					var ts3 = new TreeSelector($("select#companyID")[0],
							data1, -1);
					ts3.createTree();
				},
				error : function cbf(data) {
					alert("机构数据获取失败！");
				}
			});
});	

