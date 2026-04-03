$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 
		overlay : 20
			// 
		}).jqmAddClose('.close');//
	$('.JQueryflexme').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : '收件箱',
				minheight : 80,
				buttons : [{
					name : '回复',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '转发',
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
					name : '彻底删除',
					bclass : 'edit',
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
			case '回复' :
			
				document.location.href = basePath
						+ "ea/email/ea_getEmaiInfo.jspa?emailID=" + emailID
						+ "&zhType=h";
				break;
			case '转发' :
	
				document.location.href = basePath
						+ "ea/email/ea_getEmaiInfo.jspa?emailID=" + emailID
						+ "&zhType=z";
				break;
			case '彻底删除' :
				var reciveID = "";
				var checkbs = document.getElementsByName("chss");
				for (var i = 0; i < checkbs.length; i++) {
					if (checkbs[i].checked) {
						reciveID += checkbs[i].value + ";";

					}

				}
				if (reciveID == "") {
					alert("请选择");
					return;
				}
				if (confirm("是否确定彻底删除？")) {
					if (reciveID != "") {
						$("#reciveID").attr("value", reciveID);
						$("#emailForm")
								.attr(
										"action",
										basePath
												+ "ea/email/ea_delEmail.jspa?status=04");
						document.emailForm.submit.click();
					}
				}

				break;
			case '删除' :
				var reciveID = "";
				var checkbs = document.getElementsByName("chss");
				for (var i = 0; i < checkbs.length; i++) {
					if (checkbs[i].checked) {
						reciveID += checkbs[i].value + ";";

					}

				}
				if (reciveID == "") {
					alert("请选择");
					return;
				}
				if (confirm("是否确定删除？")) {
					if (reciveID != "") {
						$("#reciveID").attr("value", reciveID);
						$("#emailForm")
								.attr(
										"action",
										basePath
												+ "ea/email/ea_delEmail.jspa?status=03");
						document.emailForm.submit.click();
					}
				}

				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/manage/ea_getEnterpriseManageList.jspa?1=1";
				numback(url);
				break;
		}
	}
	$(".JQueryflexme tr[id]").click(function() {
				$("input[name=chs]", $(this)).attr("checked", "checked");
				emailID = this.id;
			});
	$("#xz").live("click", function(event) {
				if ($(this).attr("checked")) {
					$("input[type='checkbox']").each(function() {
								$(this).attr("checked", true);
							});
				} else {
					$("input[type='checkbox']").each(function() {
								$(this).attr("checked", false);
							});
				}
			});
	$(".JQueryflexme tr[id]").dblclick(function() {
		if (emailID == "") {
			alert('请选择!');
			return;
		}
		var status = $(this).find("span#addresseeStatus").text();
		var $this = $(this);
		if (status == "未读") {
			var url = basePath + "ea/email/sajax_n_ea_ChangeStatus.jspa?";
			var params = {
				'email.emailID' : emailID
			};
			$.post(url, params, function(data) {
						/*var member = eval("(" + data + ")");*/
						//var st = member.st;
						$this.find("span#addresseeStatus").text("已读");
					});
		}
		document.cstaffForm.reset();
		$t = $("div#jqModel");
		$p = $("tr#" + emailID);
		$p.find("span[id]").each(function() {
					$t.find(":input[name]#" + this.id).val($(this).text());
				});

		var urlattach = basePath
				+ "ea/email/sajax_ea_getAttachOfEmail.jspa?datesete="
				+ new Date();
		$.ajax({
					url : urlattach,
					type : "post",
					async : true,
					dataType : "json",
					data : {
						emailID : emailID
					},
					success : function cbf(data) {
						var member = eval("(" + data + ")");
						var attachlist = member.attachlist;
						var str = "";
						for (var i = 0; i < attachlist.length; i++) {
							var filepath = '"' + attachlist[i].filepath + '"';
							str += "<a style='text-decoration:none;'href='javascript:downLoadAttach("
									+ filepath + ");'>"
									+ attachlist[i].filename + "</a><br/>";

						}

						$("#attach").html(str);

					},
					error : function cbf(data) {
						alert("数据获取失败！");
					}
				});
		$("#jqModel").jqmShow();
	});
});

// 下载附件
function downLoadAttach(filePath) {
	window.open(basePath + "/servlets/render?filename=" + filePath);
}