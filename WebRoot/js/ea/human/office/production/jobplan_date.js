$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	if(ent == '待办事项'){
		$('.jobPlan').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : "工作计划",
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
				}, {
					name : '增加责任人',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '工作计划进度',
					bclass : 'mysearch',
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
	}else if(ent == '已办事项'){
		$('.jobPlan').flexigrid({
			height : 350,
			width : 'auto',
			minwidth : 30,
			title : "工作计划",
			minheight : 80,
			buttons : [{
				name : '工作计划进度',
				bclass : 'mysearch',
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
	}else if(ent=='完成事项'){
		$('.jobPlan').flexigrid({
			height : 350,
			width : 'auto',
			minwidth : 30,
			title : "工作计划",
			minheight : 80,
			buttons : [{
				name : '工作计划进度',
				bclass : 'mysearch',
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
	}
	function action(com, grid) {
		switch (com) {
			case '添加' :
				document.addplanForm.reset();
				$("#jqModelplan").jqmShow();
				break;
			case '修改' :
				if (jobPlanID == '') {
					alert("请选择！");
					return;
				}
				document.addplanForm.reset();
				var url = basePath + "ea/jobplan/sajax_ea_tojobEdit.jspa?jobPlan.jobPlanID="+jobPlanID+"&date="+ new Date();
				$.ajax({
					url : encodeURI(url),
					type : "get",
					async : true,
					dataType : "json",
					success : function cbf(data) {
						var member = eval("(" + data + ")");
						var list =member.list;
						$t = $("#addplanForm");
						$p = $("tr#" + jobPlanID);
						$p.find("span[id]").each(function() {
							$t.find("#" + this.id).val($(this).text());
						});
						$p.find("input[id]").each(function() {
							$t.find("#" + this.id).val($(this).val());
							if(this.id == "jobstatus"){
								$t.find("select#jobstatus");
								$t.find("option[value='"+$(this).text()+"']").attr("selected","selected");
							}
						});
						$("#jqModelplan").jqmShow();
						for(var i=0;i<list.length;i++){
							if(i == 0){
								$t.find("td#atr").find("input#staffName").attr("value",list[i][0]);
								$t.find("td#atr").find("input#staffID").attr("value",list[i][1]);
								$t.find("td#atr").find("input#companyID").attr("value",list[i][2]);
								$t.find("td#atr").find("input#orgID").attr("value",list[i][3]);
							}else{
								$("tr#addtr").after($("#addtr").clone(true)
										.attr("id", "addtr" + select)
										.addClass("check"));
								$("#addtr" + select).find('input').each(function() {
									$(this).attr("name",
											"jobstamap[" + select + "]." + this.name);
								});
								$("tr#addtr" + select).find("input,a").removeClass("model1");
								$("tr#addtr" + select).find("a").attr("id","staffID"+select);
								$t.find("tr#addtr" + select).find("input#staffName").attr("value",list[i][0]);
								$t.find("tr#addtr" + select).find("input#staffID").attr("value",list[i][1]);
								$t.find("tr#addtr" + select).find("input#companyID").attr("value",list[i][2]);
								$t.find("tr#addtr" + select).find("input#orgID").attr("value",list[i][3]);
								select++;
							}
						}
						
					
					},
					error : function cbf(data) {
						alert("数据获取失败！")
					}
				});
				
				break;
			case '删除' :
				if (jobPlanID == '') {
					alert("请选择！");
					return;
				}
				$f = $('#jobPlanForm');
				if (confirm("是否删除？")) {
					if (notoken)
						return;
					notoken = 1;
					$f.attr("target", "hidden").attr(
							"action",
							basePath + "ea/jobplan/ea_delJobPlanDate.jspa?pageNumber="
									+ ppageNumber +  "&jobPlan.jobPlanID="
									+ jobPlanID);
					document.jobPlanForm.submit.click();
					$("tr#" + jobPlanID).remove();
					jobPlanID = '';
					token = 11;
				}
				break;
			case '导出' :
				var url = pbasePath
						+ "ea/jobplan/ea_showExcelSummary.jspa?search="
						+ psearch;
				open(url);
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
			case '增加责任人' :
				if (jobPlanID == "") {
					alert('请选择!');
					return;
				}
					if (confirm("是否增加责任人？")) {
						$("#body_02").html("");
				        $(".jqmWindow", $("#goodsForm")).jqmShow();
				        $("#selectGood1").show();
				        $("#selectGood").hide();
					}
				break;
			case '打印' :
				$("#jqModelSearch1").jqmShow();
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "/ea/jobplan/ea_getjobPlanListdate.jspa?search="
						+ psearch+"&jobPlan.jobstatus="+jobstatus+"&jobPlan.entry="+ent;
				numback(url);
				break;
			case '工作计划进度' :
				if (jobPlanID == '') {
					alert("请选择！");
					return;
				}
				$("table#strhtml").empty();  
				document.jobplanrecordForm.reset();
				var url = basePath
				+ "ea/jobplan/sajax_ea_getJobplanRecord.jspa?jobPlan.jobPlanID="+jobPlanID+"&date="+ new Date().toLocaleString();
				$.ajax({
					url : encodeURI(url),
					type : "get",
					async : true,
					dataType : "json",
					success : function cbf(data) {
						var member = eval("(" + data + ")");
						var listjpr =member.html;
						$("table#strhtml").append(listjpr);
					},
					error : function cbf(data) {
						alert("数据获取失败！")
					}
				});
				
				$("#jqModeljobplan").jqmShow();
				break;	
		}
	}
	//加载
	$("select#jobstatus option[value='"+jobstatus+"']").attr("selected",true) 
	//a标签click事件
	$("a.staffID").click(function(){
		aid = $(this).attr("id");
		 $("#body_02").html("");
         $(".jqmWindow", $("#goodsForm")).jqmShow();
		 $("#goodsForm #companyIDs,#deptIDs,#staffIDs,#staffNames").attr("value","");	
         $("#selectGood1").hide();
         $("#selectGood").show();
         
	});
	//添加新的工作计划
	$("input.JQuerySubmitgd").click(function(){
		var starttime = $("table#cataffaddplanTable #strStartDate").val();
		var stoptime =$("table#cataffaddplanTable #strEndDate").val();
				if (notoken)
				return;
				var re = 0;
				$("input.aaaa").each(function() {
							var date = this.value;
							var $s = $(this);
							if (date == "") {
								$s.css("background-color", "red");
								re = 1;
							}
						});
				if (re) {
					alert("项目名称和起始日期与结束日期都不能为空!");
					return;
				}
				if(starttime > stoptime){
					alert("起始日期不能大于结束日期！");
					return;
				}
				if($("td#atr").find("input#staffName").val() ==""){
					alert("请选择责任人！");
					return 
				}
				notoken = 1;
				$f = $('#addplanForm');
				$f.attr("target","hidden").attr(
						"action",
						basePath
								+ "ea/jobplan/ea_saveJObPlan.jspa?");
				document.addplanForm.submit.click();
				if(jobPlanID == ''){
						document.addplanForm.reset();
						token = 1;
				}else{
					token = 2;
				}
		});
	$(".widthpx").blur(function(){
		if(!isNaN($(".widthpx").val()|| $(".widthpx").val()== '')){
				$(".widthpx").attr("value",$(".widthpx").val()+"%");
				$(".widthpx").css("background-color","red");
				}else{
				notoken = 0;
				return ;
				}
	});
	//取消添加
	$("input.JQueryClose").click(function() {
				token = 13;
				re_load();
			});
	$(".jobPlan tr[id]").click(function() {
		jobPlanID = this.id;
		$("input.JQuerypersonvalue", $(this))
				.attr("checked", "checked");
	});
	//添加多个责任人
	$("a#addstaffID").click(function(){
		$("tr#addtr").after($("#addtr").clone(true)
				.attr("id", "addtr" + select)
				.addClass("check"));
		$("#addtr" + select).find('input').each(function() {
			$(this).attr("name",
					"jobstamap[" + select + "]." + this.name);
		});
		$("#addtr" + select).find("input,a").removeClass("model1");
		$("#addtr" + select).find("a").attr("id","staffID"+select);
		select++;
	});
	
	//删除责任人
	$("a#delstaffID").click(function(){
		 $(this).parent().parent().remove(); 
	});
	// 条件查询
	$("#tosearch").click(function() {
		$("form :input").trigger("blur");
		if ($("form .error").length) {
			return false;
		}
		$("#postSearchForm").attr(
				"action",
				basePath + "ea/jobplan/ea_toSearchdate.jspa?pageNumber="
						+ ppageNumber+"&jobPlan.entry="+ent);
		document.postSearchForm.submit.click();
	});
	// 条件打印
	$("#toprint").click(function() {
		//var staffName = $("#staffName1", $("#cataffPrintTable")).val();
		var startDate = $("#startdate", $("#cataffPrintTable")).val();
		var endDate = $("#enddate", $("#cataffPrintTable")).val();

		if (startDate == "") {
			alert("日期不能为空");
			$(".startdate").css("background-color", "red");
			return;
		}
		if (endDate == "") {
			alert("日期不能为空");
			$(".enddate").css("background-color", "red");
			return;
		}
		$("#postSearchForm1").attr("action",
				pbasePath + "ea/jobplan/ea_toPrint.jspa?");
		document.postSearchForm1.submit.click();
	});
	$(".jobPlan tr[id]").dblclick(function() {
		jobPlanID = this.id;
		if($(this).attr("id").indexOf("sa") < 0 ){
			action('修改');// 当双击时出发 action方法.等价于先选中再点击修改按钮
		}
	});

	//加载集团机构树
		
	var url1 = basePath
		+ "ea/documentcommon/sajax_ea_getAllCompanyByCurrent.jspa?date="
		+ new Date().toLocaleString();
	$.ajax({
		url : url1,
		type : "get",
		dataType : "json",

		success : function cbf(data) {
			var member = eval("(" + data + ")");
			var nologin = member.nologin;
			if (nologin) {
				document.location.href = basePath + "page/ea/not_login.jsp";
			}
			var oList = member.companylist;;
			var data = new Array();
			var result = "<ul id='browser' class='filetree'>" +
					        "<li class='closed'><span class='folder'>集团机构树</span>" +
					           "<ul>";
			for (var i = 0; i < oList.length; i++) {
				data[i] = {
					id : oList[i].companyID,
					text : oList[i].companyName
				};
				result += "<li onclick='javascript:childMenu(\""
						+ data[i].id + "\")' title='"
						+ data[i].text 
						+ "' class='curor closed'><span class='folder'>" + data[i].text
						+ "</span>";
				result += "<ul id='"+data[i].id+"'></ul></li>";
			}
			      result+="</ul>" +
					         "</li>" +
				             	"</ul>";
			$(result).appendTo("#addTreess");
		    $("#browser").treeview();

		    
		},
		error : function cbf(data) {
			alert("数据获取失败！");
		}
	});
	
	// 上一页
$("#wpsy1").click(function() {
	var sy = $("#wpsy1").attr("title");
	if (sy != 0) {
		var companyID = $("#companyIDs").val();
        var org = $("#deptIDs").val();
        var querytxt = $.trim($("#querytxt").val());
        if(querytxt!= "undefined"){
        	getPersonsTerm(companyID,org,"&pageForm.pageNumber=" + sy,null,querytxt);
        }else{
		   getPersonsTerm(companyID,org,"&pageForm.pageNumber=" + sy);
        }
	} else {
		alert("已是首页！");
	}
});

// 下一页
$("#wpxy1").click(function() {
	var xy = $("#wpxy1").attr("title");
	if (xy != 0) {
		var companyID = $("#companyIDs").val();
        var org = $("#deptIDs").val();
        var querytxt = $.trim($("#querytxt").val());
		if(querytxt!= "undefined"){
        	getPersonsTerm(companyID,org,"&pageForm.pageNumber=" + xy,null,querytxt);
        }else{
		   getPersonsTerm(companyID,org,"&pageForm.pageNumber=" + xy);
        }
	} else {
		alert("已是尾页！");
	}
});

// 双击选中物品
$("table#gotable tr[id]").live("click", function(event) {
	var b = $("input.ragood", $(this)).attr("checked");
	$("input.ragood", $(this)).attr("checked", !b);
	var companyid = $(this).find("input#companyIDss").val();
	var deptid = $(this).find("input#deptIDss").val();
	var staffName = $(this).find("input#staffNamess").val();
	
	$("#staffIDs").val(this.id);
	if(companyid!="undefined"){
		$("#companyIDs").val(companyid);	
	}
	if(deptid!="undefined"){
	    $("#deptIDs").val(deptid);	
	}
	if(staffName!="undefined"){
	    $("#staffNames").val(staffName);	
	}

});

	// 单选框选中物品
$(".ragood").live("click", function(event) {
	var b = $(this).attr("checked");
	$(this).attr("checked", !b);
});


$(".JQueryreturns").click(function() { 
		notoken = 0;
 		$("#goodsjqModel").jqmHide();
 });
 
 //c查询物品
 
 $("#searchMember").click(function data(){
     var querytxt = $.trim($("#querytxt").val());
 	getPersonsTerm(null,null,null,"search",querytxt);
 	
 });
 // 传阅提交
 
	$(".submitResult").click(function() {
		var receiverCompanyID = $("#goodsForm #companyIDs").val();
		var receiverDeptID =$("#goodsForm #deptIDs").val();
		var receiverID = $("#goodsForm #staffIDs").val();
		var receiverName = $("#goodsForm #staffNames").val();
		if (receiverID == "") {
			alert("请选择接收人");
			return;
		}
		$("a#"+aid).parent().find("input#staffName").attr("value",receiverName);
		$("a#"+aid).parent().find("input#staffID").attr("value",receiverID);
		$("a#"+aid).parent().find("input#companyID").attr("value",receiverCompanyID);
		$("a#"+aid).parent().find("input#orgID").attr("value",receiverDeptID);
		
		$(".jqmWindow", $("#goodsForm")).jqmHide();
	});
	
	$(".submitResult1").click(function() {

		var receiverCompanyID = $("#goodsForm #companyIDs").val();
		var receiverDeptID =$("#goodsForm #deptIDs").val();
		var receiverID = $("#goodsForm #staffIDs").val();
		var receiverName = $("#goodsForm #staffNames").val();
		if (receiverID == "") {
			alert("请选择接收人");
			return;
		}
		var url = basePath
			+ "ea/jobplan/sajax_ea_toJobSta.jspa?jobplansta.jobPlanID="+jobPlanID+"&jobplansta.companyID="+receiverCompanyID
			+"&jobplansta.orgID="+receiverDeptID+"&jobplansta.staffID="+receiverID+"&jobplansta.staffName="
			+receiverName+"&date="+ new Date().toLocaleString();
		$.ajax({
			url : encodeURI(url),
			type : "get",
			async : true,
			dataType : "json",
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				var jobplansta =member.jobplansta;
				
				alert(jobplansta );
				
				re_load();	
			},
			error : function cbf(data) {
				alert("数据获取失败！")
			}
		});
		$(".jqmWindow", $("#goodsForm")).jqmHide();
	});

});

function getAllCompanyOfGroups() {
	var url = basePath
			+ "ea/documentcommon/sajax_ea_getAllCompanyByCurrent.jspa?date="
			+ new Date().toLocaleString();
	$.ajax({
		url : encodeURI(url),
		type : "get",
		async : true,
		dataType : "json",
		success : function cbf(data) {
			var member = eval("(" + data + ")");
			var companylist = member.companylist;

			var str = "<option value=''>请选择公司</option>";
			for (var i = 0; i < companylist.length; i++) {
				var obj = companylist[i];
				str += "<option title='" + obj.companyName + "'value='"
						+ obj.companyID + "'>" + obj.companyName + "</option>";
			}
			$("#SendForm select#receiverCompanyID").html(str);
			if (receiverCompanyID != "") {
				$("#SendForm select#receiverCompanyID option[value="
						+ receiverCompanyID + "]").attr("selected", "selected");

				bmdepts(receiverCompanyID);
			}

		},
		error : function cbf(data) {
			alert("数据获取失败！")
		}
	});
}

function fj(cID){
	var statusfj= $("tr#"+cID).find("span#status").text();
	if(statusfj!='01'){
		alert("已经归档不能修改附件");
		return;
	}
    window.open(basePath+"ea/uploadfile/ea_getListImage.jspa?loadFile.parmeterID="+cID);
}


function getAllCompanyOfGroups() {
	var url = basePath
			+ "ea/documentcommon/sajax_ea_getAllCompanyByCurrent.jspa?date="
			+ new Date().toLocaleString();
	$.ajax({
		url : encodeURI(url),
		type : "get",
		async : true,
		dataType : "json",
		success : function cbf(data) {
			var member = eval("(" + data + ")");
			var companylist = member.companylist;

			var str = "<option value=''>请选择公司</option>";
			for (var i = 0; i < companylist.length; i++) {
				var obj = companylist[i];
				str += "<option title='" + obj.companyName + "'value='"
						+ obj.companyID + "'>" + obj.companyName + "</option>";
			}
			$("#SendForm select#receiverCompanyID").html(str);
			if (receiverCompanyID != "") {
				$("#SendForm select#receiverCompanyID option[value="
						+ receiverCompanyID + "]").attr("selected", "selected");

				bmdepts(receiverCompanyID);
			}

		},
		error : function cbf(data) {
			alert("数据获取失败！")
		}
	});
}

/**
 * 
 * 根据公司获得部门
 * 
 * @param {}
 *            val
 */
function bmdepts(val) {

	$("option", $("#receiverDeptID")).remove();

	var url = basePath
			+ "ea/documentcommon/sajax_ea_getAllOrganizations.jspa?date="
			+ new Date().toLocaleString();
	$.ajax({
				url : encodeURI(url),
				type : "post",
				async : false,
				dataType : "json",
				data : {
					"companyID" : val
				},
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var orgaizationlist = member.orgaizationlist;
					var str = "<option value=''>请选择部门</option>";
					for (var i = 0; i < orgaizationlist.length; i++) {
						var obj = orgaizationlist[i];
						str += "<option value='" + obj.organizationID + "'>"
								+ obj.organizationName + "</option>";
					}
					$("#SendForm #receiverDeptID").html(str);

				},
				error : function cbf(data) {
					alert("数据获取失败！")
				}
			});
	if (receiverDeptID != "") {
		$("#SendForm select#receiverDeptID option[value=" + receiverDeptID
				+ "]").attr("selected", "selected");
		getPersons(receiverCompanyID, receiverDeptID);
	}

}

// 根据公司ID和部门ID查询收件人
function getPerson(company, org) {
	$("option", $("select#subscriberID")).remove();
	var url = basePath
			+ "ea/cashiersummary/sajax_ea_getStaffListforsub.jspa?currentCompanyID="
			+ company + "&currentOrgnizationID=" + org + "&date111="
			+ new Date();
	$.ajax({
		url : encodeURI(url),
		type : "get",
		async : true,
		dataType : "json",
		success : function cbf(data) {
			var member = eval("(" + data + ")");
			var persons = member.stafflist;
			var str = "<option value=''>请选择收件人</option>";
			for (var i = 0; i < persons.length; i++) {
				var obj = persons[i];
				str += "<option value='" + obj[0] + "'>" + obj[1] + "</option>";
			}
			$("#subscriberID").html(str);
			if (subscriberIDs != "")
				$("#newForm select#subscriberID option[value=" + subscriberIDs
						+ "]").attr("selected", "selected");
		}
	});
}

function bmdept(val) {
	$("option", $("#receiverDeptID")).remove();
	var url = basePath
			+ "ea/responsibilitiessummary/sajax_n_ea_getoList.jspa?companyID="
			+ val + "&date=" + new Date().toLocaleString();
	$.ajax({
				url : encodeURI(url),
				type : "get",
				async : true,
				dataType : "json",
				success : function cbf(data) {
					/** **添加部门列表** */
					var member = eval("(" + data + ")");
					var oList = member.organizationlist;
					var data2 = new Array();
					data2[0] = {
						id : val,
						pid : '-1',
						text : '请选择部门'
					};
					for (var i = 0; i < oList.length; i++) {
						data2[i + 1] = {
							id : oList[i].organizationID,
							pid : oList[i].organizationPID,
							text : oList[i].organizationName
						};
					}

					ts = new TreeSelector($("#receiverDeptID")[0], data2,
							-1);
					ts.createTree();
				},
				error : function cbf(data) {
					alert("数据获取失败！")
				}
			});

	$("option[value=" + this.value + "]", $("#receiverDeptID")).val("");
	
 
}

/**
 * 
 * 根据部门获得人员
 * 
 * @param {}
 *            company
 * @param {}
 *            org
 */
function getPersons(company, org) {

	$("option", $("select#receiverID")).remove();

	var url = basePath
			+ "ea/documentcommon/sajax_ea_getPersonByDept.jspa?date="
			+ new Date();
	$.ajax({
				url : encodeURI(url),
				type : "post",
				async : false,
				dataType : "json",
				data : {
					"currentCompanyID" : company,
					"checkOrgID" : org
				},
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var persons = member.stafflist;
					var str = "<option value=''>请选择收件人</option>";
					for (var i = 0; i < persons.length; i++) {
						var obj = persons[i];
						str += "<option value='" + obj.staffID + "'>"
								+ obj.staffName + "(" + obj.staffCode
								+ ")</option>";
					}
					$("#SendForm #receiverID").html(str);
				}
			});
	if (receiverID != "") {
		$("#SendForm select#receiverID option[value=" + receiverID + "]").attr(
				"selected", "selected");
	}
}

function changeCompany(obj) {
	if ($(obj).val() != '') {
		bmdept($(obj).val());
	} else {
		$("#SendForm #receiverDeptID").html("<option value=''>请选择部门</option>");
	}

}

/**
 * 
 * 当部门改变时，获取员工
 * 
 * @param {}
 *            obj
 */
function changeDept(obj) {
	var dept = $(obj).val();
	if (dept != "") {
		getPersons($("#SendForm #receiverCompanyID").val(), dept);
	} else {
		$("#SendForm #receiverID").html("<option value=''>请选择人员</option>");
	}
}

function childMenu(companyID) {// 2级
		if($("ul#"+companyID+">li").length>0){
			
			
			return;
		}
		var url2 = basePath
				+ "ea/documentcommon/sajax_ea_getAllOrganizations.jspa?date="
				+ new Date().toLocaleString();
		$.ajax({
			url : encodeURI(url2),
			type : "post",
			async : false,
			dataType : "json",
			data : {
				companyID : companyID
			},
			success : function cbf(data) {

				/** **添加部门列表** */

				var member = eval("(" + data + ")");
				var orglist = member.orgaizationlist;
				var data = new Array();
				var result="";
				for (var i = 0; i < orglist.length; i++) {
					data[i] = {
					id : orglist[i].organizationID,
					text : orglist[i].organizationName
				    };
					result += "<li class='curor closed'  title='"
								+ data[i].text
								+ "'><span onclick='javascript:getSubDept(\""
								+ companyID + "\",\"" + data[i].id + "\")' class='folder'>"
								+ data[i].text + "</span>";
				   result += "<ul id='"+data[i].id+"'></ul></li>";
				}
				
				$(result).appendTo("#"+companyID);

			},
			error : function cbf(data) {
				alert("数据获取失败！");
			}
		});


	}

//根据父部门查询子部门，循环查询
function  getSubDept(companyID,deptID){
$("#goodsForm #companyIDs").val(companyID);
$("#goodsForm #deptIDs").val(deptID);

	
	if($("ul#"+deptID+">li").length>0){
		getPersonsTerm(companyID,deptID);
			return;
		}
	
		var url2 = basePath
				+ "ea/responsibilitiessummary/sajax_n_ea_getSubDeptList.jspa?companyID="
			+ companyID + "&date=" + new Date().toLocaleString();
		$.ajax({
			url : encodeURI(url2),
			type : "post",
			async : false,
			dataType : "json",
			data : {
				companyID : companyID,
				checkDeptID:deptID
			},
			success : function cbf(data) {

				/** **添加部门列表** */

				var member = eval("(" + data + ")");
				var orglist = member.organizationlist;

				var data = new Array();
				var result="";
				for (var i = 0; i < orglist.length; i++) {
					data[i] = {
					id : orglist[i].organizationID,
					text : orglist[i].organizationName
				    };
					result += "<li  class='closed'><span onclick='javascript:getSubDept(\""
								+ companyID + "\",\"" + data[i].id + "\")' title='"
								+ data[i].text
								+ "' class='folder'>"
								+ data[i].text + "</span>";
					result += "<ul id='"+data[i].id+"'></ul></li>";
				}
				$(result).appendTo("#"+deptID);
				getPersonsTerm(companyID,deptID);

			},
			error : function cbf(data) {
				alert("数据获取失败！");
			}
		});
		
	
	          
}

	// 查询人使用中；
	function getPersonsTerm(companyID,org,pagenumber,search,querytxt) {
//		if (notoken) {
//			alert("正在获取数据！请稍等");
//			return;
//		}
		notoken = 1;
		$("#wpsy1").attr("title", 0);
		$("#wpxy1").attr("title", 0);
		$("#wpzy1").attr("title", 0);
	    var url = basePath
			+ "ea/documentcommon/sajax_ea_getPersonByDept2.jspa?date="
			+ Math.random()+pagenumber;
		$.ajax({
			url : encodeURI(url),
			type : "get",
			async : true,
			dataType : "json",
			data:{
			        "currentCompanyID" : companyID,
					"checkOrgID" : org,
					"search":search,
					"querytxt":querytxt
			},
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				var nologin = member.nologin;
				if (nologin) {
					document.location.href = basePath + "page/ea/not_login.jsp";
				}
				var pageForm = member.pageForm;
				var companyName = member.companyName;
			    var orgName = member.orgName;
				if (pageForm == null) {
					alert("没有数据");
					notoken = 0;
					return;
				}
				var dqy = pageForm.pageNumber;// 当前页
				var zys = pageForm.pageCount;// 总页数
				if (dqy > 1) {
					$("#wpsy1").attr("title", dqy - 1);
				}
				if (dqy < zys) {
					$("#wpxy1").attr("title", dqy + 1);
				}
				$("span#wpzycount1").text(zys);
				var tabletr = "<table width='98%' height='26' align='center' cellspacing='0' cellpadding='1' style='font-size:12px;' class='bannb_01'><tr><td height='24' align='left' valign='top' class='txt01' >&nbsp;点击选择人员</td></tr></table>";
				tabletr += "<table width='99%' align='center' id='gotable' cellpadding='0' cellspacing='0' class='table'>";
				tabletr += " <tr><th height='21' align='center' bgcolor='#E4F1FA'>选择</th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'>人员编号</th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'> 姓名 </th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'> 性别 </th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'> 身份证 </th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'> 电话 </th>";
				tabletr += "<th align='center' bgcolor='#E4F1FA'> 部门 </th>" +
						"<th align='center' bgcolor='#E4F1FA'> 所在公司</th>";
				for (var i = 0; i < pageForm.list.length; i++) {
					tabletr += "<tr style='cursor: hand;' id = "
							+ pageForm.list[i][0] + ">";
					tabletr += "<td id='check' align='center'><input type ='radio' class='ragood' value="
							+ pageForm.list[i][0]+ " name='check'/><input type='hidden' id='deptIDss' value='"+pageForm.list[i][8]+"'/>" +
									"<input type='hidden' id='companyIDss' value='"+pageForm.list[i][9]+"'/>" +
									"<input type='hidden' id='staffNamess' value='"+pageForm.list[i][2]+"'/></td>";
					tabletr += "<td id='staffCode' align='center'>"
							+ pageForm.list[i][1] + "</td>";
					tabletr += "<td id='staffName'  align='center'>"
							+ pageForm.list[i][2] + "</td>";
					tabletr += "<td id='sex'  align='center'>"
							+ pageForm.list[i][3] + "</td>";					
					tabletr += "<td id='staffIdentityCard' align='center'>"
							+ pageForm.list[i][4]+ "</td>";
					tabletr += "<td id='reference' align='center'>"
							+ pageForm.list[i][5] + "</td>";		
				    if(orgName == undefined){
				    	tabletr += "<td id='orgName'  align='center'>"
							+pageForm.list[i][6]+"</td>";
				    }else{
				    	tabletr += "<td id='orgName'  align='center'>"
							+orgName+"</td>";
				    }
				    if(companyName == undefined){
				    	tabletr += "<td id='companyName' align='center'>"
							+ pageForm.list[i][7]+"</td>";
				    }else{
				    	tabletr += "<td id='companyName' align='center'>"
							+ companyName+"</td>";
				    }
					
					
					tabletr += " </tr>";
				}
				tabletr += " </table>";
				$("#body_02").html(tabletr);
				$("#body_02").show();
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
	
	function re_load(){
		document.location.href=basePath+"/ea/jobplan/ea_getjobPlanListdate.jspa?jobPlan.jobstatus="+jobstatus+"&jobPlan.entry="+ent+"&pageNumber="+ppageNumber;
	}

	  