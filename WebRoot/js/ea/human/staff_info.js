$(document).ready(function() {
	// $("#pdetails").click(function() {
	// //人员基本信息
	// if (personvalue == "") {
	// alert("请选择具体人员！");
	// return;
	// }
	// document.location.href = basePath +
	// "ea/pdetails/ea_toGetPDetails.jspa?person.staffID="+ personvalue;
	// });

	$("#Address").click(function() {
		// 地址管理
		if (personvalue == "") {
			alert("请选择具体人员！");
			return;
		}
		personurl = basePath
				+ "ea/csaddress/ea_getListAddress.jspa?pageNumber=4&address.staffID=";
		$("#mainframe").css({width:"100%",height:"auto"}).show().attr("src", personurl + personvalue);
		$(window).resize();
	});
	
	$("#jobplan").click(function() {
		if (personvalue == "") {
			alert("请选择具体人员！");
			return;
		}
		personurl = basePath
				+ "ea/jobplan/ea_getJobPlanList.jspa?pageNumber=4&staffID=";
		$("#mainframe").css({width:"100%",height:"auto"}).show().attr("src", personurl + personvalue);
		$(window).resize();
	});
	
	$("#contact").click(function() {
		// 联系人管理
		if (personvalue == "") {
			alert("请选择具体人员！");
			return;
		}
		personurl = basePath
				+ "ea/contact/ea_getListContact.jspa?pageNumber=4&contact.staffID=";
		$("#mainframe").css({width:"100%",height:"auto"}).show().attr("src", personurl + personvalue);
		$(window).resize();
	});
	
	$("#bankAccount").click(function() {
		// 银行帐号
		if (personvalue == "") {
			alert("请选择具体人员！");
			return;
		}
		personurl = basePath
				+ "ea/bankaccount/ea_getListBankAccount.jspa?pageNumber=4&bankAccount.staffID=";
		$("#mainframe").css({width:"100%",height:"auto"}).show().attr("src", personurl + personvalue);
		$(window).resize();
	});
	
	$("#education").click(function() {
		// 学历学位
		if (personvalue == "") {
			alert("请选择具体人员！");
			return;
		}
		personurl = basePath
				+ "ea/education/ea_getListEducation.jspa?pageNumber=4&education.staffID=";
		$("#mainframe").css({width:"100%",height:"auto"}).show().attr("src", personurl + personvalue);
		$(window).resize();
	});

	$("#precord").click(function() { // 个人覆历
		if (personvalue == "") {
			alert("请选择具体人员！");
			return;
		}
		personurl = basePath
				+ "ea/precord/ea_getListPRecord.jspa?pageNumber=4&record.staffID=";
		$("#mainframe").css({width:"100%",height:"auto"}).show().attr("src", personurl + personvalue);
		$(window).resize();
	});
	
	// 工作目标任务
	$("#jobtask").click(function() {

		if (personvalue == "") {
			alert("请选择具体人员！");
			return;
		}
		personurl = basePath
				+ "ea/jobtask/ea_getJobTaskList.jspa?pageNumber=4&staffID=";
		$("#mainframe").css({width:"100%",height:"auto"}).show().attr("src", personurl + personvalue);
		$(window).resize();
	});
	
	// 家人员
	$("#showfamily").click(function() {
		if (personvalue == "") {
			alert("请选择具体人员！");
			return;
		}
		personurl = basePath
				+ "ea/fmember/ea_getListFMember.jspa?pageNumber=4&member.staffID=";
		$("#mainframe").css({width:"100%",height:"auto"}).show().attr("src", personurl + personvalue);
		$(window).resize();
	});
	
	$("#personalRecord").click(function() {
		// 人事档案
		if (personvalue == "") {
			alert("请选择具体人员！");
			return;
		}
		personurl = basePath
				+ "ea/personalrecord/ea_getListPersonalRecord.jspa?pageNumber=4&personalRecord.staffID=";
		$("#mainframe").css({width:"100%",height:"auto"}).show().attr("src", personurl + personvalue);
		$(window).resize();
	});
	
	// 身体状况
	$("#showhealth").click(function() {
		if (personvalue == "") {
			alert("请选择具体人员！");
			return;
		}
		personurl = basePath
				+ "ea/pcondition/ea_getListPCondition.jspa?pageNumber=4&condition.staffID=";
		$("#mainframe").css({width:"100%",height:"auto"}).show().attr("src", personurl + personvalue);
		$(window).resize();
	});

	$("#political").click(function() {
		if (personvalue == "") {
			alert("请选择具体人员！");
			return;
		}
		personurl = basePath
				+ "ea/political/ea_getListPolitical.jspa?pageNumber=4&political.staffID=";
		$("#mainframe").css({width:"100%",height:"auto"}).show().attr("src", personurl + personvalue);
		$(window).resize();
	});
	
	$("#award").click(function() {
		if (personvalue == "") {
			alert("请选择具体人员！");
			return;
		}
		personurl = basePath
				+ "ea/encourage/ea_getListEncourage.jspa?pageNumber=4&encourage.staffID=";
		$("#mainframe").css({width:"100%",height:"auto"}).show().attr("src", personurl + personvalue);
		$(window).resize();
	});
	
	// 处分情况
	$("#punishment").click(function() {
		if (personvalue == "") {
			alert("请选择具体人员！");
			return;
		}
		personurl = basePath
				+ "ea/punishment/ea_getListPunishment.jspa?pageNumber=4&punishment.staffID=";
		$("#mainframe").css({width:"100%",height:"auto"}).show().attr("src", personurl + personvalue);
		$(window).resize();
	});
	
	// 保险
	$("#showassurance").click(function() {
		if (personvalue == "") {
			alert("请选择具体人员！");
			return;
		}
		personurl = basePath
				+ "ea/insurance/ea_getListInsurance.jspa?pageNumber=4&insurance.staffID=";
		$("#mainframe").css({width:"100%",height:"auto"}).show().attr("src", personurl + personvalue);
		$(window).resize();
	});
	
	// 调查情况
	$("#survey").click(function() {
		if (personvalue == "") {
			alert("请选择具体人员！");
			return;
		}
		personurl = basePath
				+ "ea/investigation/ea_getListInvestigation.jspa?pageNumber=4&investigation.staffID=";
		$("#mainframe").css({width:"100%",height:"auto"}).show().attr("src", personurl + personvalue);
		$(window).resize();
	});
	
	// 证件列表
	$("#credentials").click(function() {
		if (personvalue == "") {
			alert("请选择具体人员！");
			return;
		}
		personurl = basePath
				+ "ea/credentials/ea_getListCredentials.jspa?pageNumber=4&credentials.staffID=";
		$("#mainframe").css({width:"100%",height:"auto"}).show().attr("src", personurl + personvalue);
		$(window).resize();
	});
	
	// 资料列表
	$("#documentation").click(function() {
		if (personvalue == "") {
			alert("请选择具体人员！");
			return;
		}
		personurl = basePath
				+ "ea/documentation/ea_getListDocumentation.jspa?pageNumber=4&documentation.staffID=";
		$("#mainframe").css({width:"100%",height:"auto"}).show().attr("src", personurl + personvalue);
		$(window).resize();
	});
	
	$(".link02").click(function() {
		$(this).next("ul").slideToggle();
	});
});
