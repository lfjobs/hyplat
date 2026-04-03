$(document).ready(function() {
	var len = $("#tbwid").find(".trclass").length;
	parent.document.getElementById("mainframe16").style.height = 180 + len * 27 + "px";
	
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	$(".JQueryaddress").jqm({
		modal : true,// 
		overlay : 20
			// 
		}).jqmAddClose('.close');//
	$('.contact').flexigrid({
				height : 'auto',
				allDouble : true,
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
				$("#sa").after($("#sa").clone(true).attr("id", "sa" + select)
						.addClass("check"));
				$("#sa" + select).find(':input:gt(0)').each(function() {
					$(this).attr("name",
							"bankAccountmap[" + select + "]." + this.name);
				});
				$("#sa" + select).show();
				var heis = parent.document.getElementById("mainframe16").offsetHeight + 27 + "px";
				parent.document.getElementById("mainframe16").style.height = heis;
				select++;
				break;

			case '修改' :
				if (bankAccountID == '') {
					alert("请选择！");
					return;
				}
				$p = $("tr#" + bankAccountID);
				if ($p.hasClass("check")) {
					return;
				}

				$p.addClass("check");
				$p.find(':input:gt(0)').each(function() {
					$(this).attr("name",
							"bankAccountmap[" + select + "]." + this.name);
				});
				select++;
				$p.find("span")
						.addClass("model1");
				$p.find("input").removeClass("model1");
				$p.find("s:select").attr("disabled", false);
				$p.find("select").show();
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
				$("input[name$='bankAccount']", $(".check")).each(
						function(i, tmp) {
							if (this.value == "") {
								alert("开户账号不能为空");
								$(this).css("background-color", "red");
								re = 1;
							}
						});
				$("input[name$='bankName']", $(".check")).each(
						function(i, tmp) {
							if (this.value == "") {
								alert("银行名称不能为空");
								$(this).css("background-color", "red");
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
										+ "ea/bankaccount/ea_saveBankAccount.jspa?bankAccount.staffID="
										+ staffID);
				document.contactForm.submit.click();
				token = 2;
				break;

			case '删除' :
				if (bankAccountID == '') {
					alert("请选择！");
					return;
				}
				if (bankAccountID.substring(0, 2) == "sa") {
					$("#" + bankAccountID).remove();
					bankAccountID = '';
					var heis = parent.document.getElementById("mainframe16").offsetHeight - 27 + "px";
					parent.document.getElementById("mainframe16").style.height = heis;
					return;
				}
				$f = $('#contactForm');
				$f
						.attr("target", "hidden")
						.attr(
								"action",
								basePath
										+ "ea/bankaccount/ea_delBankAccount.jspa?bankAccount.staffID="
										+ staffID
										+ "&bankAccount.bankAccountID="
										+ bankAccountID);
				document.contactForm.submit.click();
				$("tr#" + bankAccountID).remove();
				bankAccountID = "";
				token = 11;
				break;
		}
	}

	$(".contact tr[id]").click(function() {
		$("input.JQuerypersonvalue", $(this))
				.attr("checked", "checked");
		bankAccountID = this.id;
	});
	
	$(".contact tr[id]").dblclick(function() {
		$("input.JQuerypersonvalue", $(this))
				.attr("checked", "checked");
		bankAccountID = this.id;

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
				+ "ea/bankaccount/ea_getListBankAccount.jspa?bankAccount.staffID="
				+ staffID;
}