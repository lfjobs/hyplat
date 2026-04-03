$(function() {
	$("#jqModel").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	$('.JQueryflexme').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : '工资级别',
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
			case '添加' :
				payScaleID = "";
				$("input.JQuerypersonvalue").attr("checked", false);
				document.cstaffForm.reset();
				$("#jqModel").jqmShow();
				scaleValue = '';
				break;
			case '修改' :
				if (payScaleID == "") {
					alert('请选择!');
					return;
				}
				document.cstaffForm.reset();
				$t = $("table#stafftable");
				$p = $("tr#" + payScaleID);
				$p.find("span[id]").each(function() {
							$t.find(":input#" + this.id).val($(this).text());
						});

				$("#jqModel").jqmShow();
				scaleValue = $("span#scale", $p).text();
				break;
			case '删除' :
				if (payScaleID == "") {
					alert('请选择！');
					return
				}
				$f = $('#cstaffForm');
				$f.find(':input#payScaleID').val(payScaleID);
				if (confirm("是否删除?")) {
					$("#cstaffForm")
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/payscale/ea_delPayScale.jspa?pageNumber="
											+ ppageNumber);
					document.cstaffForm.submit.click();
					$("tr#" + payScaleID).remove();
					payScaleID = "";
					token = 11;
				}
				break;
			case '导出' :
				url = basePath + "ea/payscale/ea_showExcel.jspa?1=1";
				open(url);
				break;
			case '设置每页显示条数' :
				var url = basePath + "ea/payscale/ea_getListPayScale.jspa?1=1";
				numback(url);
				break;
		}
	}
	$(".menu00").click(function() {
				$(this).hide();
			});
	$(".JQueryflexme tr[id]").dblclick(function() {
				action('修改');// 当双击时出发 action方法.等价于先选中再点击修改按钮
			});
	$(".JQueryflexme tr[id]").click(function() {
				payScaleID = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	
	var scaleValue = '';
	$("input.JQuerySubmit").click(function() {// 保存
				$(".put3").trigger("blur");
				if ($("form .error").length) {
					return;
				}
				s = $("input.isUniquenessScale").val();
				var b = isUniquenessScale(s);
				if (b) {
					if (payScaleID == "") {
						$("#cstaffForm")
								.attr("target", "hidden")
								.attr(
										"action",
										basePath
												+ "ea/payscale/ea_savePayScale.jspa?pageNumber="
												+ ppageNumber);
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
											+ "ea/payscale/ea_savePayScale.jspa?pageNumber="
											+ ppageNumber);
					document.cstaffForm.submit.click();
					token = 2;
				} else {
					alert("请填入正确信息！");
				}

			});
	function isUniquenessScale(v) {
		if (scaleValue == v && scaleValue != '') {
			return true;
		}
		if (v == "") {
			alert("岗位级差必填");
			return false;
		}
		bfalse = false;
		var url = basePath
				+ "ea/payscale/sajax_n_ea_isUniquenessScale.jspa?result=" + v
				+ "&date01=" + new Date();
		$.ajax({
			url : encodeURI(url),
			type : "get",
			async : false,
			dataType : "json",
			success : function cbf(cc) {
				var member = eval("(" + cc + ")");
				var nologin = member.nologin;
				if (nologin) {
					document.location.href = basePath + "page/ea/not_login.jsp";
				}
				var rowcount = member.rowcount;
				if (rowcount) {
					alert("岗位级差不能重复");
					return;
				}
				bfalse = true;
			},
			error : function cbf(data) {
				alert("数据获取失败！");
			}
		});
		return bfalse;
	}		
	$("input.JQueryreturn").click(function() {// 取消
				$("#jqModel").jqmHide();
				re_load();
			});
	$(".close").click(function() {// 取消
				$("#jqModel").jqmHide();
				re_load();
			});
});
function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/payscale/ea_getListPayScale.jspa?pageNumber="
				+ ppageNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value");
}