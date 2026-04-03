$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true, 
		overlay : 20 
		}).jqmAddClose('.close');  
	$('.JQueryflexme').flexigrid({
				height : 445,
				width : 'auto',
				minwidth : 30,
				title : '物品管理',
				minheight : 80,
				buttons : [{
					name : '添加',
					bclass : 'add',
					onpress : action
						//
					}, {
					separator : true
				}, {
					name : '删除',
					bclass : 'delete',
					onpress : action
					}, {
					separator : true
				}, {
					name : '修改',
					bclass : 'edit',
					onpress : action
					}, {
					separator : true
				}, {
					name : '查询',
					bclass : 'mysearch',
					onpress : action
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
				goodsID = "";
				$("td.variables", $("#cstaffForm")).children("span:gt(0)")
						.hide();
				$("input.JQuerypersonvalue").attr("checked", false);
				$t = $("div#jqModel");
				$t.find("img#showphoto").attr("src", "");
				document.cstaffForm.reset();
				$("#jqModel").jqmShow();
				break;
			case '修改' :
				if (goodsID == "") {
					alert('请选择!');
					return;
				}
				$("td.variables", $("#cstaffForm")).children("span:gt(0)")
						.hide();
				document.cstaffForm.reset();
				$t = $("div#jqModel");
				$t.find("img#showphoto").attr("src", "");
				$p = $("tr#" + goodsID);
				$p.find("span[id]").each(function() {
					var spid = this.id;
					if (spid.indexOf("num") >= 0 && $(this).text() != "") {
						$("td.variables", $("#cstaffForm"))
								.find("span#" + spid).show();
					}
					$t.find(":input[name]#" + this.id).val($(this).text());
					if (this.id == "standard") {
						$("select#standards").attr("value", $(this).text());
					}
				});
				var photo = $p.find("span#photoPath").text();
				if (photo.length != 0) {
					$t.find("img#showphoto").attr("src", basePath + photo);
				}

				$("#jqModel").jqmShow();
				break;
			case '删除' :
				if (goodsID == "") {
					alert('请选择');
					return
				}
				$f = $('#cstaffForm');
				$f.find(':input#goodsID').val(goodsID);
				if (confirm("确定删除？")) {
					$("#cstaffForm")
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/cgoodsmanage/ea_delGoodsManage.jspa?pageNumber="
											+ ppageNumber);
					document.cstaffForm.submit.click();
					$("tr#" + goodsID).remove();
					goodsID = "";
					token = 11;
				}
				break;
			case '查询' :
				$("#jqModelSearch2").jqmShow();
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/cgoodsmanage/ea_getListGoodsManage.jspa?1=1";
				numback(url);
				break;
		}
	}
	$('td.variables select[number]').change(function() {
	    var number = $(this).attr("number");
		var num = parseInt(number) + 1;
		$("td.variables").find('select:gt(' + number + ')').attr("value", "");
		$("td.variables").find('input:gt(' + number + ')').val("");
		$("td.variables").children('span:gt(' + num + ')').hide();
		$("td.variables").children('span:eq(' + num + ')').show();
	});
	
	$(".JQueryflexme tr[id]").click(function() {
		$("input.JQuerypersonvalue", $(this)).attr("checked", "checked");
		goodsID = this.id;
	});
	
	$(".JQueryflexme tr[id]").dblclick(function() {
		action('修改');
	});
	
	$("input.JQuerySubmit").click(function() {
		if ($("form .error").length) {
			return;
		}
		
		if ($("input.goodsCoding").val() == '') {
			alert('编号不能为空');
			return;
		}
		
		if ($("input.goodsName").val() == '') {
			alert('名称不能为空');
			return;
		}
		
		var b = true;
		var str = "";
		$("td.variables").find('select').each(function() {
			if ($(this).attr("value") != "") {
				var seinput = $(this).parent().find("input").val();
				if (jQuery.trim(seinput) == "" || isNaN(seinput)) {
					b = false;
					return;
				} else {
					if (this.id != "variableID") {
						str += "=" + seinput + $(this).attr("value");
					} else {
						str += seinput + $(this).attr("value");
					}
				}
			} else {
				$(this).parent().find("input").val("");
			}
		});
		
		if (b == false) {
			alert("请填入正确的换算单位值！");
			return;
		}
		$("input#goodsvariable", $("#cstaffForm")).val(str);
		$("td.variables", $("#cstaffForm")).children("span:gt(0)").hide();
	    if (goodsID == "") {
			$("#cstaffForm")
					.attr("target", "hidden")
					.attr(
							"action",
							basePath
									+ "ea/cgoodsmanage/ea_saveGoodsManage.jspa?pageNumber="
									+ ppageNumber);
			document.cstaffForm.submit.click();
			document.cstaffForm.reset();
			token = 1;
			return;
	    }
		$("#cstaffForm").attr("target", "hidden").attr(
					"action",
					basePath
							+ "ea/cgoodsmanage/ea_saveGoodsManage.jspa?pageNumber="
							+ ppageNumber);
			document.cstaffForm.submit.click();
			token = 2;
		});
		
		$('#staffphoto').change(function() {
			$t = $("table#stafftable");
			$t.find('img#showphoto').attr("src", this.value).show();
		});
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
	if (token){
		document.location.href = basePath
				+ "ea/cgoodsmanage/ea_getListGoodsManage.jspa?pageNumber="
				+ ppageNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value");
	}
}
$(document).ready(function() {
	// 图片预览
	$('#filePhoto').change(function() {
		$t = $("table#stafftable");
		$t.find('img#showphoto').attr("src", this.value).show();
	});	// 图片预览END
});
