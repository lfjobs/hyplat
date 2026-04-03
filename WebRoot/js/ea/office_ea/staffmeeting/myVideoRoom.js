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
		title : '我创建的会议',
		minheight : 80,
		buttons : [ {
			name : '立即加入',
			bclass : 'edit',
			onpress : action
		// 当点击调用方法
		}, {
			separator : true
		}, {
			name : '创建会议',
			bclass : 'add',
			onpress : action
		// 当点击调用方法
		}, {
			separator : true
		}, {
			name : '删除会议',
			bclass : 'delete',
			onpress : action
		// 当点击调用方法
		}, {
			separator : true
		} ]
	});
	function action(com, grid) {
		switch (com) {
		case '立即加入':
			if (roomid == "") {
				alert('请选择!');
				return;
			}
			var  meetingpsw = $("tr#"+roomid).find("#meetingpsw").text();
			window
			.open("https://47.94.154.17:8443/launch/toEnterMeeting.do?roomID="
					+ roomid
					+ "&userName="
					+ user
					+ "&userPwd=123456wfj&roomPwd="
					+ meetingpsw);
			break;
		case '创建会议':
			$("input.JQuerypersonvalue").attr("checked", false);
			document.cstaffForm.reset();
			$("#jqModel").jqmShow();
			break;

		case '删除会议':
			if (roomid == "") {
				alert('请选择!');
				return;
			}
			if(confirm("确定删除会议？")){
			var meetingID = $("tr#"+roomid).find("#meetingID").text();
			$("#cstaffForm #meetingID").val(meetingID);
			$("#cstaffForm")
			.attr("target", "hidden")
			.attr(
					"action",
					basePath+"ea/videoroom/ea_deleteRoom.jspa");
			document.cstaffForm.submit.click();
			token = 2;
			break;
			}

		}
	}
	$(".JQueryflexme tr[id]").dblclick(function() {
		roomid = this.id;
		action('立即加入');// 当双击时出发 action方法.等价于先选中再点击修改按钮
	});
	$(".JQueryflexme tr[id]").click(function() {
		roomid = this.id;
		$("input.JQuerypersonvalue", $(this)).attr("checked", "checked");
	});

	// 提交
	$("input.JQuerySubmit").click(function() {// 保存
		$(".put3").trigger("blur");
		if ($("#jqModel .error").length){
			return;
		}

			$("#cstaffForm")
					.attr("target", "hidden")
					.attr(
							"action",
							basePath
									+ "ea/videoroom/ea_addRoominfo.jspa");
			document.cstaffForm.submit.click();
			document.cstaffForm.reset();
			token = 2;
			return;

	});
	
	$("input.JQueryreturn").click(function() {// 取消
		$("#jqModel").jqmHide();
	});

	$("#query_add").click(function() {
		$("#leftfields").dblclick();
	});

	$("#query_delete").click(function() {
		$("#rightfields").dblclick();
	});

	$(".JQueryflexme").find("select").each(function() {
		$s = $(this).hide();
		$o = $("<span/>").text($s.find("option:selected").text());
		$o.insertAfter($s);
	});

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
			var oList = member.companylist;
			;
			var data = new Array();
			var result = "<ul id='browser' class='filetree'>";
			for ( var i = 0; i < oList.length; i++) {
				data[i] = {
					id : oList[i].companyID,
					text : oList[i].companyName
				};
				result += "<ul><li onclick='javascript:childMenu(\""
						+ data[i].id + "\")' title='" + data[i].text
						+ "' class='curor closed'><span class='folder'>"
						+ data[i].text + "</span><ul id='" + data[i].id + "'>";
				result += "</ul></ul>";

			}
			result += "</li></ul>";
			$(result).appendTo("#tree1");
			$("#browser").treeview();

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

});
function selectPeople() {

	$("#zj").jqmShow();

}
function clearPeople() {
	// if($("tr#"+meetingID).find("#noticeType").text()=="01"){
	// alert("已通知不能全部清空");
	// return;
	// }
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

function childMenu(companyID) {// 2级
	if ($("ul#" + companyID + ">li").length > 0) {
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
			var result = "";
			for ( var i = 0; i < orglist.length; i++) {
				data[i] = {
					id : orglist[i].organizationID,
					text : orglist[i].organizationName
				};
				result += "<li onclick='javascript:getPerson(\"" + companyID
						+ "\",\"" + data[i].id + "\")' title='" + data[i].text
						+ "'><a href='#'><span id='" + data[i].id
						+ "' class='folder curor'>" + data[i].text
						+ "</span></a></li>";
			}

			$(result).appendTo("#" + companyID);

		},
		error : function cbf(data) {
			alert("数据获取失败！");
		}
	});

}

function getPerson(company, org) {
	var url = basePath
			+ "ea/videoroom/sajax_ea_getPersonList.jspa?date="
			+ new Date();
	$.ajax({
		url : encodeURI(url),
		type : "post",
		async : true,
		dataType : "json",
		data : {
			"companyID" : company,
			"orgID" : org,
			sccid:sccid
		},
		success : function cbf(data) {
			var member = eval("(" + data + ")");
			var persons = member.stafflist;
			var str = "";
			for ( var i = 0; i < persons.length; i++) {
				var obj = persons[i];

				str += "<option value='" + obj[0] + "," + obj[1] + ","
						+ obj[2] + "'>" + obj[1] + "(" + obj[2]
						+ ")</option>";
			}
			$("#leftfields").html(str);
		}
	});
}

function re_load() {

	if (token)
		document.location.href = basePath
				+ "ea/videoroom/ea_myRoomMeeting.jspa?&user="+user+"&sccid="+sccid
				+ "&pageNumber=" + ppageNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value");
}

//点击确定按钮
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
			strid += "-";
			strname += document.getElementById("rightfields").options[i].text;
			strname += "|";

		}
	}
	$("#smpid").val(strid);
	$("#smp").val(strname);
	$("#zj").jqmHide();
}

