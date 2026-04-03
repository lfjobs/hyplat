$(document).ready(function() {
	var len = $("#tbwid").find(".trclass").length;
	select+=len;
	parent.document.getElementById("mainframe53").style.height = 180 + len * 27 + "px";
	
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20// 遮罩程度%
	}).jqmAddClose('.close');// 添加触发关闭的selector
	$('.contact').flexigrid({
				height : 'auto',
				width : 'auto',
				minwidth : 30,
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
					name : '全部保存',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}]
			});
	function action(com, grid) {
		switch (com) {
			case '添加' :
				if(select>1){
					alert("不可添加多个报名信息!!");
					return;
				}
				$("#sa").after($("#sa").clone(true).attr("id", "sa" + select)
						.addClass("check"));
				$("#sa" + select).find(':input:gt(0)').each(function() {
					$(this).attr("name",
							"studentRegInformap[" + select + "]." + this.name);
				});
				$("#sa" + select).show();
				var heis = parent.document.getElementById("mainframe53").offsetHeight + 27 + "px";
				parent.document.getElementById("mainframe53").style.height = heis;
				select++;
				bmDept("");
				break;

			case '修改' :
				if (drivingAllInformationID == '') {
					alert("请选择！");
					return;
				}
				$p = $("tr#" + drivingAllInformationID);
				if ($p.hasClass("check")) {
					return;
				}

				$p.addClass("check");
				$p.find(':input:gt(0)').each(function() {
					$(this).attr("name",
							"studentRegInformap[" + select + "]." + this.name);
				});
				select++;
				bmDept(drivingAllInformationID);
				$p.find("span")
						.addClass("model1");
				$p.find("input").removeClass("model1");
				$p.find("select").attr("disabled", false);
				$p.find("select").show();
				$p.find("a").show();
				$(this).parent().children("span").show();
				break;
			case '全部保存' :
				if (notoken) {
					return;
				}
				if (select == 1) {
					return;
				}
				notoken = 1;
				var re = 0;
				$("#organizationID", $(".check")).each(function(i, tmp) {
					if (this.value.substring(0,7) == "company") {
						alert("请选择公司下分校/报名点！ ");
						re = 1;
					}
				});
				if (re) {
					notoken = 0;
					return;
				}
				$('#contactForm')
						.attr("target", "hidden")
						.attr(
								"action",
								basePath
										+ "ea/enroll/ea_saveStudentRegInfors.jspa?");
				document.contactForm.submit.click();
				token = 2;
				break;

			case '删除' :
				if (drivingAllInformationID == '') {
					alert("请选择！");
					return;
				}
				if (drivingAllInformationID.substring(0, 2) == "sa") {
					$("#" + drivingAllInformationID).remove();
					drivingAllInformationID = '';
					var heis = parent.document.getElementById("mainframe53").offsetHeight - 27 + "px";
					parent.document.getElementById("mainframe53").style.height = heis;
					return;
				}
				$f = $('#contactForm');
				$f
						.attr("target", "hidden")
						.attr(
								"action",
								basePath
										+ "ea/enroll/ea_deleteStudentRegInfors.jspa?dtDrivingAllInformation.drivingAllInformationID="
										+ drivingAllInformationID);
				document.contactForm.submit.click();
				$("tr#" + drivingAllInformationID).remove();
				drivingAllInformationID = "";
				token = 11;
				break;
		}
	}

	$(".contact tr[id]").click(function() {
		$("input.JQuerypersonvalue", $(this))
				.attr("checked", "checked");
		drivingAllInformationID = this.id;
	});
	
	$(".contact tr[id]").dblclick(function() {
		$("input.JQuerypersonvalue", $(this))
				.attr("checked", "checked");
		drivingAllInformationID = this.id;
		action("修改");
	});
	
	//----------------选择在职人员操作------------//
	$(".xzry").live("click",function(){
		parent.document.getElementById("mainframe53").style.height = 320 + "px";
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
		$t=$("input.JQuerypersonvalue:checked").parents("tr");
		$t.find("input#referrer").attr("value",staffName);
		$t.find("input#referrerID").attr("value",childopertionID);
		$t.find("input#referrerPhone").attr("value",reference);
		$t.find("input#referrerIdentityCard").attr("value",staffIdentityCard);
		 $("#daoRu").attr("src","");
         $("#bankJqm").hide();
         parent.document.getElementById("mainframe53").style.height = 80 + select * 27 + "px";
   });
	$(".contact").find("select[id!=xxx]").each(function() {
		$s = $(this).hide();
		$o = $("<span/>").text($s.find("option:selected").text());
		$o.insertAfter($s);
	});
	$("select#organizationID").change(function(){
		$(this).parents("tr").find("input#organizationName").attr("value",$.trim($(this).children("option:selected").text().replace("├","")));
	})
});

function bmDept(id) {
	var url = basePath
			+ "ea/responsibilities/sajax_n_ea_getoList.jspa?companyID="+ companyID + "&date=" + new Date().toLocaleString()//+"&series=one"+"&level=organization";
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
					if(id!=""){
						ts = new TreeSelector($("tr#"+id).find("select#organizationID")[0], data2, '-1');
						ts.createTree();
						$("tr#"+id).find("select#organizationID").show();
						$("tr#"+id).find("option[value='"+$("tr#"+id).find("input#organizationID").val()+"']").attr("selected","selected");
						$("tr#"+id).find("select#organizationID").trigger("change");
					}else{
						ts = new TreeSelector($("tr#sa"+(select-1)).find("select#organizationID")[0], data2, '-1');
						ts.createTree();
						$("tr#sa"+(select-1)).find("select#organizationID").show();
						$("tr#sa"+(select-1)).find("select#organizationID").trigger("change");
					}
				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}
			});
}
function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/enroll/ea_getStudentCompanyList.jspa?dtDrivingAllInformation.dataTitle=04&dtDrivingAllInformation.staffID="+ staffID+"&dtDrivingAllInformation.relationID="+relationID;
}