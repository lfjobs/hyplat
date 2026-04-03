$(function() {
	$(".jqmWindow").jqm({
		modal : true, // 限制输入（鼠标点击，按键）的对话
		overlay : 20  // 遮罩程度%
	}).jqmAddClose('.close');// 添加触发关闭的selector
	$('.JQueryflexme').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : '招聘渠道管理',
				minheight : 80,
				buttons : [{
					name : '添加',
					bclass : 'add',
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
					name : '修改',
					bclass : 'edit',
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
					name : '导出',
					bclass : 'excel',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '打印预览',
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
				document.cstaffForm.reset();
				fullAddress("");  //详细地址
				$("#jqModel").jqmShow();
				break;
			case '删除' :
				if(recruitChannelID == ''){
					alert('请选择！');
					return;
				}
				if (confirm("确定删除？")) {
					var url = basePath + "ea/recruitchannel/ea_deleteRecruitChannel.jspa?pageNumber="
									+ ppageNumber +"&recruitchannel.recruitchannelid=" 
									+ recruitChannelID + "&recruitchannel.channelname=" 
									+ chname;
					document.location.href = encodeURI(url);
				}
				break;
			case '修改' :
				if(recruitChannelID == ''){
					alert('请选择！');
					return;
				}
				$t = $("div#jqModel");
				$p = $("tr#" + recruitChannelID);
				$p.find("span[id]").each(function() {
					$t.find("#" + this.id).val($(this).text());
				});
				$("#jqModel").jqmShow();
				fullAddress($p.find("span#address").text());
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
			case '导出' :
				url = basePath + "ea/recruitchannel/ea_showExcel.jspa?search="
						+ search;
				open(url);
				break;
			case '打印预览' :
				window.open(basePath
						+ "ea/recruitchannel/ea_toPrint.jspa?search="
						+ search);
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/recruitchannel/ea_getRecruitChannelList.jspa?search="
						+ search;
				numback(url);
				break;
		}
	}
	
	$("#searchChannel").click(function() { //查询
		$("#recruitChannelForm").attr(
				"action",
				basePath + "ea/recruitchannel/ea_toSearch.jspa?pageNumber="
						+ ppageNumber);
		document.recruitChannelForm.submit.click();
	});
	
	$("#save").click(function(){ //保存
		$(".put3").trigger("blur");
		if ($("form .error").length) {
			return;
		}
		
		var addr = "";
		$(".JQueryaddress").find("select")
				.find("option[value]:selected").each(function() {
			if ($(this).text() != '--新增--'
					&& $(this).text() != '--请选择--')
				addr = addr + $(this).text();
		});
		$("#cstaffForm").find("input#fullAddress").val(addr); //保存详细地址名称
		
		$("#cstaffForm")
				.attr("target", "hidden")
				.attr(
						"action",
						basePath
								+ "ea/recruitchannel/ea_saveRecruitChannel.jspa?pageNumber="
								+ ppageNumber);
		document.cstaffForm.submit.click();
		document.cstaffForm.reset();
		$("#cstaffForm").find(".corect").remove();
		if(recruitChannelID == ''){
			token = 1;
		}else{
			token = 2;
		}
	});
	
	$("input.JQueryreturn").click(function() {// 取消
		$("#jqModel").jqmHide();
		$("#jqModelSearch").jqmHide();
		
	});
	
	$("input.JQueryreturn1").click(function() {// 新增代码取消
		var formID = $($("#formID").attr("value"));
		$("#newccode").jqmHide();
		$(".jqmWindow", formID).jqmShow();
	});
	
	$("input.JQueryreturn2").click(function() {// 城市添加取消
		retoken = 0;
		$("#newdistrict").jqmHide();
		$("#jqModel").jqmShow();
	});
	
	$(".JQueryflexme tr[id]").click(function() { //单击事件
		recruitChannelID = this.id;
		chname = $(this).attr("title");  //删除时传参
		$("input.JQuerypersonvalue", $(this))
				.attr("checked", "checked");
	});
	
	$(".JQueryflexme tr[id]").dblclick(function() {
		action('修改');// 当双击时出发 action方法.等价于先选中再点击修改按钮
	});
	
	$("#tel",$("#cstaffForm")).blur(function(){
		if($(this).val() == ''){
			$("#err").find(".error").remove();
		}
	});
	
	$("#contactway",$("#cstaffForm")).focus(function(){
		$(this).after("<span class=\"blu\" style=\"color:blue;\">电话/QQ/MSN/邮箱</span>");
	}).blur(function(){
		$(".blu").remove();
	});
	
	// //////////////////////////////地址!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!==================================================BEGIN!
	var PID='';// 当点新曾时,上一级被选中项的id
	var rovince='';// 被改变的那个的id
	var districtPID='';
	function fullAddress(address) {
		if (retoken)
			return;
		retoken = 1;

		$td = $("td.JQueryaddress");
		$td.children('select').empty();
		$select = "<option selected='selected'>--请选择--</option>";
		$("#province", $td).append($select);
		$td = $("td.JQueryaddress");
		var DistrictID = address;
		if (DistrictID == "") {
			var url = basePath
					+ "ea/cstaff/sajax_n_ea_getCDistricts.jspa?districtPID=0"
					+ "&date1=" + new Date();
			$.ajax({
						url : url,
						type : "get",
						async : true,
						dataType : "json",
						success : function cbf(data) {
							var member = eval("(" + data + ")");
							var distinctlist = member.distinctlist;
							for (var i = 0; i < distinctlist.length; i++) {
								$op = $("<option />");
								$op.attr("value", distinctlist[i].districtID)
										.attr("id", distinctlist[i].districtID)
										.text(distinctlist[i].districtName);
								$("#province", $td).append($op);
							}
							retoken = 0;
						},
						error : function cbf(data) {
							retoken = 0;
							alert("数据获取失败！");
						}
					});
			return;
		}
		var urldistrict = basePath
				+ "ea/cstaff/sajax_n_ea_getPDetailsDistricts.jspa?districtPID="
				+ DistrictID + "&date01=" + new Date();
		$.ajax({
			url : urldistrict,
			type : "get",
			async : true,
			dataType : "json",
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				var distinctlistSaved = member.distinctlistSaved;
				var list = member.list;
				$select = "<option selected='selected'>--请选择--</option>";
				retoken = 0;
				for (var i = 0; i < distinctlistSaved.length; i++) {
					if (i == 9) {
						return;
					}
					$td.children('select:eq(' + i + ')').empty();
					$td.children('select:eq(' + i + ')').append($select);
					for (var j = 0; j < list[i].length; j++) {
						$op = $("<option />");
						$op.attr("value", list[i][j].districtID).attr("id",
								list[i][j].districtID)
								.text(list[i][j].districtName);
						$td.children('select:eq(' + i + ')').append($op);
					}
					$opp = $("<option  selected='selected'/>");
					$opp.attr("value", distinctlistSaved[i].districtID).attr(
							"id", distinctlistSaved[i].districtID)
							.text(distinctlistSaved[i].districtName);
					$td.children('select:eq(' + i + ')').append($opp);
					$add = "<option class='add'  value = '"
							+ distinctlistSaved[i].districtPID
							+ "' >--新增--</option>";
					$td.children('select:eq(' + i + ')').append($add);
				}
				$td.children('select:eq(' + distinctlistSaved.length + ')')
						.append($select);
				for (var z = 0; z < list[distinctlistSaved.length].length; z++) {
					$op = $("<option />");
					$op
							.attr(
									"value",
									list[distinctlistSaved.length][z].districtID)
							.attr(
									"id",
									list[distinctlistSaved.length][z].districtID)
							.text(list[distinctlistSaved.length][z].districtName);
					$td.children('select:eq(' + distinctlistSaved.length + ')')
							.append($op);
				}
				$addd = "<option class='add'  value = '"
						+ distinctlistSaved[distinctlistSaved.length - 1].districtID
						+ "' >--新增--</option>";
				$td.children('select:eq(' + distinctlistSaved.length + ')')
						.append($addd);
			},
			error : function cbf(data) {
				retoken = 0;
				alert("数据获取失败！");
			}
		});
	}

	$('td.JQueryaddress select[number]').change(function() {
		if (retoken)
			return;
		retoken = 1;

		var province = this.id;
		var number = $(this).attr("number");
		$td = $("td.JQueryaddress");
		rovince = "#" + province;
		$('#newdistrict', $td).hide();
		$td.children('select:gt(' + number + ')').empty();
		$td.children('select:gt(' + number + ')').show();
		var D = $(rovince, $td).children("option:selected").attr("class");
		if (D == 'add') {
			PID = $(rovince, $td).children("option:selected").val();
			$('#districtNames').attr("title", number).attr("value", "");
			$("#jqModel").jqmHide();
			$("#newdistrict").jqmShow();
			retoken = 0;
			return;
		}
		$($td).children('select:gt(' + number + ')').attr("disabled", false);
		var districtPID = $(rovince, $td).children("option:selected").val();
		if (districtPID == '--请选择--') {
			retoken = 0;
			return;
		}
		var url = basePath
				+ "ea/cstaff/sajax_n_ea_getCDistricts.jspa?districtPID="
				+ districtPID + "&date2=" + new Date();
		$.ajax({
					url : encodeURI(url),
					type : "get",
					async : true,
					dataType : "json",
					success : function cbf(data) {
						var member = eval("(" + data + ")");
						var distinctlist = member.distinctlist;
						$select = "<option selected='selected'>--请选择--</option>";
						var idval = $(rovince, $td).next().attr('id');
						if(idval == 'brid'){
							$(rovince, $td).next().next().append($select);
						}else{
							$(rovince, $td).next().append($select);
						}
						if (distinctlist.length) {
							for (var i = 0; i < distinctlist.length; i++) {
								$op = $("<option/>");
								$op.attr("value", distinctlist[i].districtID)
										.attr("id", distinctlist[i].districtID)
										.text(distinctlist[i].districtName);
								if(idval == 'brid'){
									$(rovince, $td).next().next().append($op);
								}else{
									$(rovince, $td).next().append($op);
								}
							}
						}
						$add = "<option class='add'  value = '" + districtPID
								+ "' >--新增--</option>";
						if(idval == 'brid'){
							$(rovince, $td).next().next().append($add);
						}else{
							$(rovince, $td).next().append($add);
						}
						$td.find('input#address').val(districtPID);
						retoken = 0;
					},
					error : function cbf(data) {
						retoken = 0;
						alert("数据获取失败！");
					}
				});

	});

	$('input#savedistrict').click(function() {
		if (retoken)
			return;
		retoken = 1;
		$td = $("td.JQueryaddress");
		number = $('input#districtNames').attr('title');
		districtName = $('input#districtNames').val();
		$td.children('select:gt(' + number + ')').empty();
		if ('' == districtName) {
			alert("请填写城市名称");
			retoken = 0;
			return;
		}
		$("#newdistrict").jqmHide();
		$("#jqModel").jqmShow();
		var urldistrict = basePath
				+ "ea/cstaff/sajax_n_ea_saveDistrict.jspa?district.districtPID="
				+ PID + "&district.districtName=" + districtName + "&date3="
				+ new Date();
		//var url = basePath + "ea/cstaff/sajax_n_ea_getCDistricts.jspa";
		$.ajax({
					url : encodeURI(urldistrict),
					type : "get",
					async : true,
					dataType : "json",
					success : function cbf(data) {
						var member = eval("(" + data + ")");
						var sdistrict = member.sdistrict;
						$op1 = $("<option selected='selected'/>").attr("value",
								sdistrict.districtID).attr("id",
								sdistrict.districtID)
								.text(sdistrict.districtName);
						$("#" + sdistrict.districtID, $td).remove();
						$(rovince, $td).append($op1);
						districtPID = sdistrict.districtID;
						//var params = {
						//	"districtPID" : districtPID
						//};

						$select = "<option selected='selected'>--请选择--</option>";
						var idval = $(rovince, $td).next().attr('id');
						if(idval == 'brid'){
							$(rovince, $td).next().next().append($select);
						}else{
							$(rovince, $td).next().append($select);
						}
						$add = "<option class='add'  value = '" + districtPID
								+ "' >--新增--</option>";
						if(idval == 'brid'){
							$(rovince, $td).next().next().append($add);
						}else{
							$(rovince, $td).next().append($add);
						}
						$td.find('input#address').val(districtPID);
						retoken = 0;
					},
					error : function cbf(data) {
						retoken = 0;
						alert("数据获取失败！");
					}
				});

	});
});

$(function(){
	var url = basePath + "ea/recruitchannel/sajax_ea_getSortsList.jspa";
	$.ajax({
			url : encodeURI(url),
			type : "get",
			async : true,
			dataType : "json",
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				var codeSortsList = member.codeSortsList;
				for (var i = 0; i < codeSortsList.length; i++) {
					$op = $("<option/>");
					$op.val(codeSortsList[i].codeValue)
							.text(codeSortsList[i].codeValue);
					$("select#sorts").append($op);
				}
			},
			error : function cbf(data) {
				alert("数据获取失败！");
			}
	});
});

function toCCode(codePID, selectID, formID) {
	$(".jqmWindow").jqmHide();
	$("#codePID").attr("value", codePID);
	$("#selectID").attr("value", selectID);
	$("#formID").attr("value", formID);
	$("#ccodevalue").attr("value", "");
	$("#newccode").jqmShow();
}
function saveCCode() {
	var codePID = $("#codePID").attr("value");
	var codeValue = $("#ccodevalue").attr("value");
	var selectID = $("#selectID").attr("value");
	var formID = $($("#formID").attr("value"));
	var url = basePath + "ea/ccode/sajax_ea_saveCCodeByAjax.jspa?code.codePID="
			+ codePID + "&code.codeValue=" + codeValue + "&date="
			+ new Date().toLocaleString();
	$.ajax({
			url : encodeURI(url),
			type : "get",
			async : true,
			dataType : "json",
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				var nologin = member.nologin;
				if (nologin) {
					document.location.href = basePath
							+ "page/ea/not_login.jsp";
				}
				var code = member.code;
				$("#newccode").jqmHide();
				$op = $("<option/>");
				$op.attr("value", code.codeValue).text(code.codeValue);
				$("select" + selectID).append($op);
				alert("操作成功！");
				$(".jqmWindow", formID).jqmShow();
			},
			error : function cbf(data) {
				alert("数据获取失败！");
			}
	});
}

function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/recruitchannel/ea_getRecruitChannelList.jspa?search="
				+ search + "&pageNumber=" + ppageNumber
				+ "&pageForm.pageNumber=" + $("#pageNumber").attr("value");
}