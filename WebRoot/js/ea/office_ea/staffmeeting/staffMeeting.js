$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	$('.JQueryflexme').flexigrid({
				height : 360,
				width : 'auto',
				minwidth : 30,
				title : '员工会议管理',
				minheight : 80,
				buttons : [{
					name : '添加',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, 
					{
					name : '查看',
					bclass : 'see',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}
				, {
					name : '修改',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},  {
					name : '查询',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '通知',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, 
					{
					name : '取消会议',
					bclass : 'delete',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '会议记录录入',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '上传会议录音',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '参会人员',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '简报传阅',
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
	function action(com, grid) {
		switch (com) {
			case '添加' :
				$("input.JQuerypersonvalue").attr("checked", false);
				document.cstaffForm.reset();
				$("#jqModel").jqmShow();
				break;
			case '查看' :
				if (meetingID == "") {
					alert('请选择!');
					return;
				}
				document.cstaffForm.reset();
				$t = $("div#jqModel");
				$p = $("tr#" + meetingID);
				$p.find("span[id]").each(function() {
							$t.find(":input[name]#" + this.id).val($(this)
									.text());
						});
			    getPartiMember(meetingID);
				$(".JQuerySubmit").hide();
				$("#jqModel").jqmShow();
				break;
			case '修改' :
				if (meetingID == "") {
					alert('请选择!');
					return;
				}
				document.cstaffForm.reset();
				$t = $("div#jqModel");
				$p = $("tr#" + meetingID);
				$p.find("span[id]").each(function() {
							$t.find(":input[name]#" + this.id).val($(this)
									.text());
						});
				getPartiMember(meetingID);
				$(".JQuerySubmit").show();
				$("#jqModel").jqmShow();
				break;
			case '简报传阅' :
		     alert("后期开发");
				break;

			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;

			case '通知' :
				if (meetingID == "") {
					alert('请选择!');
					return;
				}

				$("#jqModelNotice").jqmShow();
				break;

			case '会议时间调整' :
				if (meetingID == "") {
					alert('请选择!');
					return;
				}
				var url = basePath
						+ "ea/smeeting/sajax_ea_getStaffMeetingInfo.jspa";
				$.ajax({
							url : url,
							type : "get",
							dateType : "json",
							data : {

								meetingID : meetingID

							},
							success : function(data) {
								var member = eval("(" + data + ")");
								var startDate = member.startDate;
								var endDate = member.endDate;
								$("#meetingtime").text(startDate + "~"
										+ endDate);
								$("#tmeetingid").val(meetingID);
								$("#jqModelTime").jqmShow();

							},
							error : function(data) {
								alert("哎");
							}

						});

				break;

			case '会议地点调整' :
			   if (meetingID == "") {
					alert('请选择!');
					return;
				}
				var meetingPlace= $("tr#"+meetingID).find("#meetingPlace").text();
				$("#jqModelPlace #meetingPlaces").text(meetingPlace);
				$("#jqModelPlace").jqmShow();
				break;
			case '取消会议' :
			   if (meetingID == "") {
					alert('请选择!');
					return;
				}
				var meetingName = $("tr#"+meetingID).find("#meetingName").text();
				var noticeType = $("tr#"+meetingID).find("#noticeType").text();
				if(noticeType=="00"){
					$(".notice").hide();
				}else{
					$(".notice").show();
				}
				$("#jqModelCancel #mn").text(meetingName);
				$("#jqModelCancel #meetingIDca").val(meetingID);
				$("#jqModelCancel").jqmShow();
				break;
				
			case '会议记录录入' :
			   if (meetingID == "") {
					alert('请选择!');
					return;
				}
		       document.location.href=basePath+"ea/smeeting/ea_getRecordPage.jspa?staffMeeting.meetingID="+meetingID;
				break;
				
		     case '上传会议录音' :
			   if (meetingID == "") {
					alert('请选择!');
					return;
				}
		     	var meetingName = $("tr#"+meetingID).find("#meetingName").text();
		     	
				$("#jqModelVideo #mnr").text(meetingName);
				$("#jqModelVideo #meetingIDv").val(meetingID);
				$("#jqModelVideo").jqmShow();
				break;
			case '参会人员' :
				if (meetingID == "") {
					alert('请选择!');
					return;
				}

				document.location.href = basePath
						+ "ea/smeeting/ea_getPartiStaffList.jspa?meetingID="
						+ meetingID;
				break;

			case '设置每页显示条数' :
				var url = basePath
						+ "ea/smeeting/ea_getStaffMeeingList.jspa?search="
						+ psearch;
				numback(url);
				break;
		}
	}
	$(".JQueryflexme tr[id]").dblclick(function() {
				meetingID = this.id;
				action('修改');// 当双击时出发 action方法.等价于先选中再点击修改按钮
			});
	$(".JQueryflexme tr[id]").click(function() {
				meetingID = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	$("input.JQuerySubmit").click(function() {// 保存
				$(".put3").trigger("blur");
				if ($("#jqModel .error").length){
					return;
				}
				if (meetingID == "") {
					$("#cstaffForm")
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/smeeting/ea_saveSmeeting.jspa?pageNumber="
											+ ppageNumber + "&search="
											+ psearch);
					document.cstaffForm.submit.click();
					document.cstaffForm.reset();
					token = 1;
					return;
				}
				$("#cstaffForm")
						.attr("target", "hidden")
						.attr(
								"action",
								basePath
										+ "ea/smeeting/ea_saveSmeeting.jspa?pageNumber="
										+ ppageNumber + "&search=" + psearch);
				document.cstaffForm.submit.click();
				token = 2;
			});
			
	$("#saverecord").click(function() {// 保存会议记录

					$("#postRecordForm")
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/smeeting/ea_saveMeetingRecord.jspa?pageNumber="
											+ ppageNumber + "&search="
											+ psearch);
					document.postRecordForm.submit.click();
					document.postRecordForm.reset();
					token = 2;
					return;
	
			});
	
	$("#uploadVideo").click(function() {// 保存会议记录

					$("#postVideoForm")
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/smeeting/ea_uploadMeetingVideo.jspa?pageNumber="
											+ ppageNumber + "&search="
											+ psearch);
					document.postVideoForm.submit.click();
					token = 2;
					return;
	
			});
			
	$("input.JQueryreturn").click(function() {// 取消
				$("#jqModel").jqmHide();
				re_load();
			});
			
				
	$("input.back").click(function() {// 取消
				token = 1;
				re_load();
			});
	$(".close").click(function() {// 取消
				$("#jqModel").jqmHide();
				re_load();
			});
	$("#tosearch").click(function() {
		$("#postSearchForm").attr(
				"action",
				basePath + "ea/smeeting/ea_toSearch.jspa?pageNumber="
						+ ppageNumber);
		document.postSearchForm.submit.click();
	});

	// 通知
	$("#tonotice").click(function() {

		if ($("tr#" + meetingID).find("#noticeType").text() == "01") {
			if (confirm("您已经通知过了,确定再次通知？")) {

			} else {
				$("#jqModelNotice").jqmHide();
				return;
			}
		}

		var url = basePath + "ea/smeeting/sajax_ea_toNotice.jspa?dae="
				+ new Date();
		var sms = "";
		var email = "";
		var qq = "";
		if ($("#postNoticeForm #sms").attr("checked")) {
			sms = $("#postNoticeForm #sms").val();
		}
		if ($("#postNoticeForm #email").attr("checked")) {
			email = $("#postNoticeForm #email").val();
		}
	

		$.ajax({
					url : url,
					type : "post",
					async : true,
					dataType : "json",
					data : {
						"staffMeeting.smsNotice" : sms,
						"staffMeeting.emailNotice" : email,
						"staffMeeting.meetingID" : meetingID

					},
					success : function(data) {
						alert("操作成功");
						document.location.reload();
					},
					error : function(data) {
						alert("通知失败");
					}

				});

			
	});

	$(".JQueryflexme").find("select").each(function() {
				$s = $(this).hide();
				$o = $("<span/>").text($s.find("option:selected").text());
				$o.insertAfter($s);
			});

	var url1 = basePath
			+ "/ea/office/sajax_ea_getOrganizationList.jspa?organizationID="
			+ encodeURI(treeid) + "&datesete=" + new Date();
	$.ajax({
				url : url1,
				type : "get",
				dataType : "json",
				success : function cbf(data) {

					var member = eval("(" + data + ")");
					var nologin = member.nologin;
					if (nologin) {
						document.location.href = basePath
								+ "page/ea/not_login.jsp";
					}
					var oList = member.organizationList;
					var data = new Array();
					data[0] = {
						id : treeid,
						pid : '-1',
						durl : 0,
						text : companyName,
						str : '00'
					};
					for (var i = 0; i < oList.length; i++) {
						data[i + 1] = {
							id : oList[i].organizationID,
							pid : oList[i].organizationPID,
							text : oList[i].organizationName,
							str : oList[i].status

						};
					}
					parentMenu(treeid, data);
				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}
			});

	$("#leftfields").dblclick(function() {
				var left_vo, right_vo, vos, i;
				vos = document.getElementsByName('leftfields');

				if (vos == null)
					return false;
				left_vo = vos[0];
				vos = document.getElementsByName('rightfields');
				if (vos == null)
					return false;
				right_vo = vos[0];
				for (i = 0; i < left_vo.options.length; i++) {
					if (left_vo.options[i].selected) {
						var no = new Option();
						no.value = left_vo.options[i].value;
						no.text = left_vo.options[i].text;
						right_vo.options[right_vo.options.length] = no;
					}
				}

				// 设为要可选状态

				for (i = 0; i < right_vo.options.length; i++) {
					right_vo.options[i].selected = true;
				}

				return true;
			});

	$("#rightfields").dblclick(function() {
				var vos, right_vo, i;
				vos = document.getElementsByName('rightfields');
				if (vos == null)
					return false;
				right_vo = vos[0];
				for (i = right_vo.options.length - 1; i >= 0; i--) {
					if (right_vo.options[i].selected) {
						// alert(i);
						right_vo.options.remove(i);
					}
				}
				// 设为要可选状态

				for (i = 0; i < right_vo.options.length; i++) {
					right_vo.options[i].selected = true;
				}

				return true;
			});

	/**
	 * 
	 * 提交调整时间
	 */
	$("#toTiaoTime").click(function() {
		$("#postTimeForm").attr("target", "hidden").attr(
				"action",
				basePath + "ea/smeeting/ea_toSaveTiao.jspa?pageNumber="
						+ ppageNumber);
		document.postTimeForm.submit.click();
		token = 2;

        toTiaoSubmit("Time");

	});
	
		/**
	 * 
	 * 提交调整地点
	 */
	$("#toTiaoPlace").click(function() {
		$("#postPlaceForm").attr("target", "hidden").attr(
				"action",
				basePath + "ea/smeeting/ea_toSaveTiao.jspa?pageNumber="
						+ ppageNumber);
		document.postPlaceForm.submit.click();
		token = 2;
      toTiaoSubmit("Place");
         

	});

	
	
		/**
	 * 
	 * 提交取消会议
	 */
	$("#toCancel").click(function() {
		$("#postCancelForm").attr("target", "hidden").attr(
				"action",
				basePath + "ea/smeeting/ea_toSaveTiao.jspa?pageNumber="
						+ ppageNumber);
		document.postCancelForm.submit.click();
		token = 2;

         toTiaoSubmit("Cancel");

	});


	$("#query_add").click(function() {
				$("#leftfields").dblclick();
			});

	$("#query_delete").click(function() {
				$("#rightfields").dblclick();
			});

		// ///////////////////////////////////////////////以上为固定功能//////////////////////////////////////////////////////////

});

function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/smeeting/ea_getStaffMeeingList.jspa?search=" + psearch
				+ "&pageNumber=" + ppageNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value");
}

function selectPeople() {
	if($("#smpid").val()!=""){
		getPartiMember(meetingID);
	}
    
	$("#zj").jqmShow();

}
function clearPeople() {
//	if($("tr#"+meetingID).find("#noticeType").text()=="01"){
//		alert("已通知不能全部清空");
//		return;
//	}
	$("#leftfields").html("");
	$("#rightfields").html("");
	$("#smpid").val("");
	$("#smp").val("");

}

function closed() {
	$("#leftfields").html("");
	$("#rightfields").html("");
	$("#zj").jqmHide();

}

function parentMenu(companyID, vals) {// 1级

	result += "<ul id='browser' class='filetree'><li title='"
			+ vals[0].text
			+ "' id='"
			+ vals[0].id
			+ "' class='curor'><a><span class='folder' onclick='javascript:getPerson(\""
			+ companyID + "\",\"1\",\"company\")'>" + vals[0].text
			+ "</span></a><ul id='child'>";
	childMenu(companyID, vals);
	result += "</ul></li></ul>";
	$(result).appendTo("#tree1");
	$("#browser").treeview();
	result = "";
}

function childMenu(companyID, vals) {// 2级
	for (var j = 0; j < vals.length; j++) {
		if (vals[j].pid == companyID && vals[j].str == "00") {
			result += "<li title='" + vals[j].text + "'><a><span id='"
					+ vals[j].id
					+ "' class='folder curor' onclick='javascript:getPerson(\""
					+ companyID + "\",\"" + vals[j].id + "\",\"org\")'>"
					+ vals[j].text + "</span></a></li>";
		}
	}
}

function getPerson(company, org, searchType) {
	var url = basePath + "ea/smeeting/sajax_ea_getAllStaff.jspa?date="
			+ new Date();
	$.ajax({
				url : encodeURI(url),
				type : "post",
				async : true,
				dataType : "json",
				data : {
					"companyID" : company,
					"orgID" : org,
					"searchType" : searchType
				},
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var persons = member.stafflist;
					var str = "";
					for (var i = 0; i < persons.length; i++) {
						var obj = persons[i];

						str += "<option value='" + obj.staffID + "-" + org
								+ "-" + "'>" + obj.staffName + "("
								+ obj.staffCode + ")</option>";
					}
					$("#leftfields").html(str);
				}
			});
}

// 点击确定按钮
function submit() {
	var lengths = document.getElementById("rightfields").options.length;// 下拉项的长度
	var strid = "";
	var strname = "";
	var flag = true;
	for (var i = 0; i < lengths; i++) {
		flag = true;
		for (var j = i + 1; j < lengths; j++) {
			var stri = document.getElementById("rightfields").options[i].value;
			var strj = document.getElementById("rightfields").options[j].value;
			if (stri == strj) {
				flag = false;
				break;
			}
		}
		if (flag == true) {
			strid += document.getElementById("rightfields").options[i].value;
			strid += ",";
			strname += document.getElementById("rightfields").options[i].text;
			strname += "|";

		}
	}
	$("#smpid").val(strid);
	$("#smp").val(strname);
	$("#zj").jqmHide();
}


function toTiaoSubmit(type){
	

	
		var sms = "";
		var email = "";
		var qq = "";
		if ($("#sms"+type).attr("checked")) {
			sms = $("#sms"+type).val();
		}
		if ($("#email"+type).attr("checked")) {
			email = $("email"+type).val();
		}
		if ($("#qq"+type).attr("checked")) {
			qq = $("#qq"+type).val();
		}

	return;
	var url = basePath+"ea/smeeting/sajax_ea_reparedNotice.jspa";
	$.ajax({
	url:url,
	type:"post",
	async:true,
	dataType:"json",
	data:{
		"staffMeeting.meetingID":meetingID,
		type:type,
		"staffMeeting.smsNotice":sms,
		"staffMeeting.emailNotice":email,
		"staffMeeting.qqNotice":qq
		
		
	},
	success:function(data){
		
	},
	error:function(data){
		
	}
	
	});
	
	
}




function importGY(url) { // 打开页面
	$("#daoRu").attr("src", basePath + url + "?date=" + new Date()+"&type=inner");
	$("#socialJqm").jqmShow();
}

function DaoruConfirm() {// 选择确定
	var childopertionID = window.frames["daoRu"].mroomoID;
	if (childopertionID == "") {
		alert("请选择")
		return;
	}
	
	$t = $("div#jqModel");
	$t.find("input[id]").each(function() {
	
		$t.find(":input[name]." + this.id).val(window.frames["daoRu"].$('tr#' + childopertionID)
			.find("span#"+this.id).text());
	
	
						});
	
  
     $t.find("input[name].meetingPlace").val(window.frames["daoRu"].$('tr#' + childopertionID)
			.find("span#zone").text());
	 $t.find(".meetingTheme").val(window.frames["daoRu"].$('tr#' + childopertionID)
			.find("span#meetingTheme").text());
	 $t.find("input.startDate").val(window.frames["daoRu"].$('tr#' + childopertionID)
			.find("span#startD").text()+":00");
	 
	 $t.find("input.endDate").val(window.frames["daoRu"].$('tr#' + childopertionID)
			.find("span#endD").text()+":00");
	$("#daoRu").attr("src", "");
	$("#socialJqm").jqmHide();
}
function cancelJqm() {
	$("#socialJqm").jqmHide();
}

function playVideo(filepath){
				var url = basePath + "page/ea/main/telrec/playwav.jsp?wavpath="
						+ encodeURI(filepath);
				window.showModalDialog(url,
						"dialogWidth=500px;dialogHeight=500px");
	
}

/**
 * 
 * 获取参加人员信息
 */
function getPartiMember(meetingID){
	
	 var url = basePath+"ea/smeeting/sajax_ea_ajaxGetPartiMember.jspa";
	 $.ajax({
	 url:url,
	 type:"get",
	 async:false,
	 dataType:"json",
	 data:{
	 	"staffMeeting.meetingID":meetingID
	 	
	 },
	 success:function(data){
	 	var mem = eval("("+data+")");
	 	var memlist = mem.result;
	 	var obj;
	 	var staffIDs="";
	 	var staffnames="";
	 	var strs = "";
	 	if(memlist.length!=0){
	 		for (var i = 0; i < memlist.length; i++) {
	 			obj = memlist[i];
	 			staffIDs+=obj.staffID+"-"+obj.organizationID+",";
	 			staffnames+= obj.staffName+"|";
	 			
				strs += "<option value='" + obj.staffID + "-" + obj.organizationID
								+ "-" + "'>" + obj.staffName + "</option>";
	 		}
	 		
	 		
	    $("#smpid").val(staffIDs);
	    $("#smp").val(staffnames);
	    $("#rightfields").html(strs);
	 		
	 	}
	 	
     
	 },
	 error:function(data){
	   alert("获取参会人员失败");	
	 }
	 
	 });
}