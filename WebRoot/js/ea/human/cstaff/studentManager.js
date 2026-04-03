$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	$('.JQueryflexme').flexigrid({
				height : 180,
				width : 'auto',
				minwidth : 30,
				title : '学员管理',
				minheight : 80,
				buttons : [{
					name : '添加学员报名',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
					}, {
					// 设置分割线
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
					name : '设置每页显示条数',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '吊牌打印',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}]
			});
	function action(com, grid) {
		switch (com) {
			case '添加学员报名' :
				var url = basePath
						+ "ea/studentManager/ea_getStudentManagerList.jspa?showType=add";
				window
						.open(url, '',
								'scrollbars=yes,resizable=yes,channelmode');
				break;

			case '修改' :
				var str = 0;
				$("[name='chbox']").each(function() {
							if ($(this).is(':checked')) {
								staffID = $(this).val();
								str++;
							}
						});

				if (str != 1) {
					alert('请选择具体一个往来个人!');
					return;
				}
				var url = basePath
						+ "ea/studentManager/ea_getStudentManagerList.jspa?showType=edit&staffID="
						+ staffID;
				window
						.open(url, '',
								'scrollbars=yes,resizable=yes,channelmode');
				break;
			case '吊牌打印' :
				var str = 0;
				$("[name='chbox']").each(function() {
							if ($(this).is(':checked')) {
								staffID = $(this).val();
								str++;
							}
						});

				if (str < 1) {
					alert('请至少选择一个学员!');
					return;
				}
				$("#jqModelprintIn").jqmShow();
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
			case '设置每页显示条数' :
				var url = pbasePath
						+ "ea/studentManager/ea_getStudentListCStaffByCompanyID.jspa?search="
						+ psearch;
				numback(url);
				break;
		}
	}
	// 条件查询
	$("#searchStaff").click(function() {
		$("#cstaffSearchForm").attr(
				"action",
				basePath + "ea/studentManager/ea_toSearch.jspa?pageNumber="
						+ pageNumber);
		document.getElementById("cstaffSearchForm").submit();

	});
	// 照片预览
	$("#singleShuterphoto").click(function() {
				$("table#stafftable").find('img#photo').hide();
				$("table#stafftable").find('#singleShuter').show();
			});

	// 吊牌打印
	$("#queding").click(function() {
		//alert($("input[name='IDSize']:checked").val())
		var str = "";
		if ($("form .error").length) {
			return;
		}if($('input:radio[name="IDSize"]:checked').val()== "身份证大小"){
		$("[name='chbox']").each(function() {
					if ($(this).is(':checked')) {
						str += $(this).attr("title") + ",";
					}
				});
		newwin = window
				.open(
						basePath
								+ "ea/studentManager/ea_SaveprintInformation.jspa?staffIDS="
								+ str + "&" + $("#cstaffPrintForm").serialize(),
						'',
						'menuBar=yes,scrollBars=yes,overflow:auto,width=400,height=600,left=0,top=0');
		newwin.resizeTo(screen.availWidth, screen.availHeight);
		document.cstaffPrintForm.reset();
		$("#jqModelprintIn").jqmHide();
		}else{
		$("[name='chbox']").each(function() {
					if ($(this).is(':checked')) {
						str += $(this).attr("title") + ",";
					}
				});
		newwin = window
				.open(
						basePath
								+ "ea/studentManager/ea_SaveprintInformation.jspa?staffIDS="
								+ str + "&chicun=1" + "&" + $("#cstaffPrintForm").serialize(),
						'',
						'menuBar=yes,scrollBars=yes,overflow:auto,width=400,height=600,left=0,top=0');
		newwin.resizeTo(screen.availWidth, screen.availHeight);
		document.cstaffPrintForm.reset();
		$("#jqModelprintIn").jqmHide();
		}
		
	});
	$(".menu00").click(function() {
				$(this).hide();
			});
	$("input.JQuerySubmit").click(function() {// 保存提交
				if (notoken)
					return;
				notoken = 1;
				if (photosizes) {
					alert("上传图片规格必须为102X126！ 大小必须小于1M");
					notoken = 0;
					return;
				}
				var tm = $(".tm").val();
				if (tm == "00") {
					$(".card").addClass("put3");
				} else if (tm == "01") {
					$(".card").removeClass("put3");
				}
				$("#cstaffForm .put3").trigger("blur");
				$(".IdentityCard").trigger("blur");
				if ($("form#cstaffForm .error").length) {
					notoken = 0;
					return;
				}
				$t = $("table#stafftable");
				var addr = "";
				$(".JQueryaddress").find("select")
						.find("option[value]:selected").each(function() {
							if ($(this).text() != '--新增--'
									&& $(this).text() != '--请选择--')
								addr = addr + $(this).text();
						});
				$("#cstaffForm").find("input#staffAddress").val(addr);
				if ($("table#stafftable").find('#singleShuter').is(":visible")) {
					var f = null;
					if (document.singleShuter)
						f = document.singleShuter;
					else
						f = document.getElementById('singleShuter');
					f.SavePhoto(pbasePath + "js/photo/save2.jsp");
					if (personvalue == "") {
						token = 1;
					} else {
						token = 2;
					}
				} else {
					if (personvalue == "") {
						$("#cstaffForm")
								.attr("target", "hidden")
								.attr(
										"action",
										pbasePath
												+ "ea/cstaff/t_ea_saveCStaff.jspa?pageNumber="
												+ ppageNumber + "&aa=" + aa);
						document.cstaffForm.submit.click();
						document.cstaffForm.reset();
						$t = $("table#stafftable");
						$t.find('img#photo').attr("src", "xxx");
						token = 1;
						return;
					}
					$("#cstaffForm")
							.attr("target", "hidden")
							.attr(
									"action",
									pbasePath
											+ "ea/cstaff/t_ea_saveCStaff.jspa?pageNumber="
											+ ppageNumber + "&aa=" + aa);
					document.cstaffForm.submit.click();
					document.cstaffForm.reset();
					$t = $("table#stafftable");
					$t.find('img#photo').attr("src", "xxx");
					token = 2;
				}
			});

	$("input.JQueryreturn").click(function() {// 取消

				$("#jqModelSearch").jqmHide();
			});
	$(".JQueryflexme tr[id]").click(function() {
				var d = $("input.chx", $(this)).attr("checked");
				$("input.chx", $(this)).attr("checked", !d);
			});
	$(".JQueryflexme tr[id]").dblclick(function() {
				action('修改');// 当双击时出发 action方法.等价于先选中再点击修改按钮
			});

});

$(document).ready(function() {
	// 序号自动生成
	var numurl = basePath + "ea/ccode/sajax_ea_getCodeNum.jspa";
	$.ajax({
				url : encodeURI(numurl),
				type : "get",
				async : true,
				dataType : "json",
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var nologin = member.nologin;
					if (nologin) {
						document.location.href = "<%=basePath%>page/ea/not_login.jsp";
					}
					maxNum = member.maxNum;
				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}
			});
});

function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/studentManager/ea_getStudentManagerList.jspa?search="
				+ psearch + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value");
}