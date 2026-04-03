$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	$('.agreement').flexigrid({
				height : 'auto',
				width : 'auto',
				title : '合同报表' ,
				minwidth : 30,
				minheight : 80,
				buttons : [ {
					name : '查询',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '打印',
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
			case '查询' :
				$("#jqModelSerch").jqmShow();
				break;
			case '打印' :
				var url = basePath
						+ "ea/agreement/ea_printExcel.jspa?search=search";
				open(url);
				break;	
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/agreement/ea_getListEXC.jspa?search=search&staff="+staff;
				numback(url);
				break;	
		}
	}
	// 查询相关操作
	$("#searchAda").click(function() {
		$("#jqModelSerchForm").attr(
				"action",
				basePath + "ea/agreement/ea_toSearch.jspa?pageNumber="
						+ ppageNumber);
		document.jqModelSerchForm.submit.click();
		$("#jqModelSerchForm").find("input[name]").val("");
	});
	
	$(".agreement tr[id]").click(function() {
		$("input.JQuerypersonvalue", $(this))
				.attr("checked", "checked");
		agreementID = this.id;
		
		
		
	});
	
	$(".agreement tr[id]").dblclick(function() {
		$("input.JQuerypersonvalue", $(this))
				.attr("checked", "checked");
		agreementID = this.id;

		action("修改");

	});
	
	$(".bankAddr").focus(function() {
		$("tr").find("input[title=1]").attr("title", 0);
		$(this).attr("title", 1);
		LiuZhongYaoDeShaGuaDiZhisearch("");

		$("#jqModelSearch").jqmShow();
	});
	
	$("#addrOK").click(function() {
		var addr = "";
		$(".JQueryaddresssearch").find("select").find("option[value]:selected")
				.each(function() {
					if ($(this).text() != '--新增--'
							&& $(this).text() != '--请选择--')
						addr = addr + $(this).text();
				});
		$("tr").find("input[title=1]").val(addr);
		$("tr").find("input[title=1]").attr("title", 0);
		$("#jqModelSearch").jqmHide();
	});
	
	$("#addrREST").click(function() {
		$("#jqModelSearch").jqmHide();
	});

	$(".contact").find("select[id!=xxx]").each(function() {
		$s = $(this).hide();
		$o = $("<span/>").text($s.find("option:selected").text());
		$o.insertAfter($s);
	});

	var retoken = 0;

	// //////////////////////////////地址查询!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!==================================================BEGIN!
	//var searchPID;// 当点新曾时,上一级被选中项的id
	var searchrovince;// 被改变的那个的id
	//var searchdistrictPID;
	function LiuZhongYaoDeShaGuaDiZhisearch(address) {
		// 非空验证还原
		if (retoken)
			return;
		retoken = 1;
		$(".notnull").addClass("model3");
		$(".notnull").css("background-color", "#ffffff");
		$(".IdentityCard").css("background-color", "#ffffff");

		// 非空验证End
		$td = $("td.JQueryaddresssearch");
		$td.children('select').empty();
		$select = "<option selected='selected'>--请选择--</option>";
		$("#province", $td).append($select);
		$td = $("td.JQueryaddresssearch");
		var DistrictID = address;
		if (DistrictID == "") {
			var url = basePath
					+ "ea/cstaff/sajax_n_ea_getCDistricts.jspa?districtPID=0"
					+ "&date=" + new Date().toLocaleString();
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
				+ DistrictID + "&date=" + new Date().toLocaleString();
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
			},
			error : function cbf(data) {
				retoken = 0;
				alert("数据获取失败！");
			}
		});
	}

	$('td.JQueryaddresssearch select[number]').change(function() {
		if (retoken)
			return;
		retoken = 1;

		var province = this.id;
		var number = $(this).attr("number");
		$td = $("td.JQueryaddresssearch");
		searchrovince = "#" + province;
		$('#newdistrict', $td).hide();
		$td.children('select:gt(' + number + ')').empty();
		$td.children('select:gt(' + number + ')').show();
		//var D = $(searchrovince, $td).children("option:selected").attr("class");
		$($td).children('select:gt(' + number + ')').attr("disabled", false);
		var searchdistrictPID = $(searchrovince, $td)
				.children("option:selected").val();
		if (searchdistrictPID == '--请选择--') {
			retoken = 0;
			return;
		}
		var url = basePath
				+ "ea/cstaff/sajax_n_ea_getCDistricts.jspa?districtPID="
				+ searchdistrictPID + "&date=" + new Date().toLocaleString();
		$.ajax({
					url : encodeURI(url),
					type : "get",
					async : true,
					dataType : "json",
					success : function cbf(data) {
						var member = eval("(" + data + ")");
						var distinctlist = member.distinctlist;
						$select = "<option selected='selected'>--请选择--</option>";
						$(searchrovince, $td).next().append($select);
						if (distinctlist.length) {
							for (var i = 0; i < distinctlist.length; i++) {
								$op = $("<option/>");
								$op.attr("value", distinctlist[i].districtID)
										.attr("id", distinctlist[i].districtID)
										.text(distinctlist[i].districtName);
								$(searchrovince, $td).next().append($op);
							}
						}
						$td.find('input#address').val(searchdistrictPID);
						retoken = 0;
					},
					error : function cbf(data) {
						retoken = 0;
						alert("数据获取失败！");

					}
				});

	});

});

function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/agreement/ea_getListAgreement.jspa?agreement.staffID="
				+ staffID;
}