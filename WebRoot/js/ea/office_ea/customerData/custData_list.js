$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	$('.flexme11').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : "客户资料列表",
				minheight : 80,
				buttons : [{
					name : '客户注册',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '删除客户',
					bclass : 'delete',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '查询客户',
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
			case '客户注册' :
				$("#jqModelSearch").jqmShow();
				break;
			case '删除客户' :
				if (companyKey == '') {
					alert("请选择！");
					return;
				}
				$f = $('#Companyform');
				if (notoken)
					return;
				notoken = 1;
				$f
						.attr("target", "hidden")
						.attr(
								"action",
								basePath
										+ "ea/custdata/ea_updateCompanyStatus.jspa?");
				
				$("input#companyKey",$f).attr("value",companyKey);
				document.Companyform.submit.click();
				$("tr#" + companyKey).remove();
				companyKey = "";
				token = 11;
				break;
			case '查询客户' :
				
				$("#jqModelSearch1").jqmShow();
				break;
			case '导出' :
				var url = basePath
						+ "ea/custdata/ea_showExcel.jspa?pageNumber="
						+ pNumber + "&search=" + search+"&comType="+comType;
				open(url);
				break;
			case '设置每页显示条数' :
				var url = basePath + "ea/custdata/ea_getCustomerDataList.jspa?1=1&comType="+comType;
				numback(url);
				break;
		}
	}
	$("#saves").click(function() {
		pass = '1';
		$("form :input").trigger("blur");
		$(".validate").trigger("blur");
		if ($("form .error").length) {
			return;
		}
		var ccID = $("#cID").val();
		$('#Loginform').attr("target", "hidden").attr(
				'action',
				basePath + "custregister.jspa?pageNumber=" + pNumber
						+ "&contactCompany.ccompanyID=" + ccID); // 注册
		document.Loginform.submit.click();
		$("#jqModelSearch").jqmHide();
		token = 2;
	});

	$("#tosearch").click(function() { // 查询
				$("#SearchForm").attr(
						"action",
						basePath + "ea/custdata/ea_toSearch.jspa?pageNumber="
								+ pNumber);
				document.SearchForm.submit.click();
				$("#jqModelSearch1").jqmHide();
			});
	

	$("#closes").click(function() {
				window.location.reload();
				$("#jqModelSearch").jqmHide();
			});
	$("tr[id]").click(function(){
		$("input.JQuerypersonvalue", $(this))
		.attr("checked", "checked");
		companyKey=$(this).attr("id");
	})
});
function re_load() {
	window.location.reload();
}
function blue(){
	$("#JQueryaddress").jqmShow();
}

//地域调查 
$(document).ready(function() {
	var PID="";// 当点新曾时,上一级被选中项的id
	var rovince="";// 被改变的那个的id
	var districtPID;
	function LiuZhongYaoDeShaGuaDiZhi() {
		// 非空验证还原
		if (retoken)
			return;
		retoken = 1;
		$td = $("td.JQueryaddress");
		$td.children('select').empty();
		$select = "<option selected='selected'>--请选择--</option>";
		$("#province", $td).append($select);
		$td = $("td.JQueryaddress");
		$('td.JQueryaddress input[name=changes]').show();
		var url = basePath
				+ "/ea/contactcompany/sajax_ea_getCDistricts.jspa?districtPID=scode20110106hfjes5ucxp0000000003"
				+ "&date1=" + new Date();

		$.ajax({
					url : url,
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
						var distinctlist = member.distinctlist;
						for (var i = 0; i < distinctlist.length; i++) {
							$op = $("<option />");
							$op.attr("value", distinctlist[i].codeID)
									.attr("id", distinctlist[i].codeID)
									.text(distinctlist[i].codeDesc+""+distinctlist[i].codeValue);
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
		districtPID = $(rovince, $td).children("option:selected").val();
		if (districtPID == '--请选择--') {
			if (number != "0") {
				var nu = parseInt(number) - 1;
				districtPID = $("select[number=" + nu + "]", $td).val();
			} else {
				districtPID = "";
			}
			$td.find('input#address').val(districtPID);
			retoken = 0;
			return;
		}
		var url = basePath
				+ "ea/contactcompany/sajax_ea_getCDistricts.jspa?districtPID="
				+ encodeURI(districtPID) + "&date3=" + new Date();
		$.ajax({
			url : url,
			type : "get",
			async : true,
			dataType : "json",
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				var nologin = member.nologin;
				if (nologin) {
					document.location.href = basePath + "page/ea/not_login.jsp";
				}
				var distinctlist = member.distinctlist;
				$select = "<option selected='selected'>--请选择--</option>";
				$(rovince, $td).next().append($select);
				if (distinctlist.length) {
					for (var i = 0; i < distinctlist.length; i++) {
						$op = $("<option/>");
						$op.attr("value", distinctlist[i].codeID).attr(
								"id", distinctlist[i].codeID)
								.text(distinctlist[i].codeDesc+""+distinctlist[i].codeValue);
						$(rovince, $td).next().append($op);
					}
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
	LiuZhongYaoDeShaGuaDiZhi();
});

