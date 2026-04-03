$(function() {
	$("#jqModelSearch").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector

	$("#jqModelDimission").jqm({
		modal : true,// 离职员工
		overlay : 20
			//  
		});

	$("#jqModel").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector

	$("#jqMode2").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector

	$("#jqModelUpdate").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector

	$('.JQueryflexme').flexigrid({
				height : 300,
				width : 'auto',
				minwidth : 30,
				title : '入职管理',
				minheight : 80,
				buttons : [{
					name : '查看',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, /*
					 * { name: '删除', bclass: 'delete', onpress: action//当点击调用方法 }, {
					 * separator: true },
					 */{
					name : '导出',
					bclass : 'excel',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '人员分配入职',
					bclass : 'excel',
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
					name : '离职',
					bclass : 'delete',
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
					name : '修改入职和转正时间',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},{
					name : '入职登记表',
					bclass : 'excel',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}]
			});
	function action(com, grid) {
		switch (com) {
			case '查看' :
				if (auditionID == "") {
					alert('请选择人员');
					return
				}
				document.cstaffForm.reset();
				$t = $("table#stafftable");
				$p = $("tr#" + auditionID);
				$t.find('img#photo').attr("src",
						basePath + $p.find("span#photo").text());
				$p.find("span[id]").each(function() {
							$t.find(":input#" + this.id).val($(this).text());
						});
				$("#jqModel").jqmShow();
				break;
			case '人员分配入职' :
				document.location.href=basePath
					+ "/ea/corganization/ea_getCompanyMessage.jspa?corString=68";
				break;

			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
			case '离职' :
				if (auditionID == "") {
					alert('请选择人员');
					return
				}
				$("#jqModelDimission").jqmShow();
				break;
			/*
			 * case '删除': if (auditionID == "") { alert('请选择人员') return; } $p =
			 * $("tr#" + auditionID); if($p.find("span#status").text()== "已入职"){
			 * alert('入职人员不能删除') return } if (confirm("确定继续？")) {
			 * $("#cstaffForm").attr("action",basePath+"ea/saudition/t_ea_delAudition.jspa?pageNumber="+ppageNumber+"&status=2&audition.auditionID="+auditionID);
			 * document.all.cstaffForm.submit(); $("tr#"+auditionID).remove();
			 * auditionID==''; token = 11 ; } break;
			 */
			case '修改入职和转正时间' :
				if (auditionID == "") {
					alert('请选择人员');
					return;
				}
				$t = $("div#jqModelUpdate");
				$p = $("tr#" + auditionID);
				$t.find("input#staffName").attr("value",
						$p.find("span#staffName").text());
				$t.find("input#registerDate").attr("value",
						$p.find("span#registerDate").text());
				$t.find("input#becomesDate").attr("value",
						$p.find("span#becomesDate").text());
				$("#jqModelUpdate").jqmShow();
				break;
			case '入职登记表' :
				if(auditionID ==""){
					alert("请选择人员");
					return;
				}
				var staffID = $("tr#" + auditionID).find("span#staffID").text();
				var url = basePath
				+ "ea/soincumbent/ea_getBasicInformation.jspa?staffID="
				+ staffID;
				window.open(url);
				break;
			case '导出' :
				url = basePath
						+ "ea/saudition/ea_showExcel.jspa?status=2&audition.staffIdentityCard="
						+ staffIdentityCard + "&audition.staffName=" + sName
						+ "&search=" + search;
				open(url);
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/saudition/ea_getauditionList.jspa?status=2&audition.staffIdentityCard="
						+ staffIdentityCard + "&audition.staffName=" + sName
						+ "&search" + search;
				numback(url);
				break;
		}
	}
	$(".JQueryflexme tr[id]").dblclick(function() {
				action('查看');// 当双击时出发 action方法.等价于先选中再点击修改按钮
			});
	$(".JQueryflexme tr[id]").click(function() {
				auditionID = this.id;
				$("input.JQueryauditionID", $(this)).attr("checked", "checked");
			});
	$("#toUpdate").click(function() {
		$(".put3","form#UpdateForm").trigger("blur");
		if ($("form .error").length) {
			alert(1);
			return false;
		}
		var url = basePath
				+ "ea/saudition/sajax_n_ea_upDateStuffDate.jspa?pageNumber="
				+ ppageNumber + "&audition.auditionID=" + auditionID
				+ "&status=2&" + $("#UpdateForm").serialize();
		$.ajax({
			type : "POST",
			url : encodeURI(url),
			dataType : "json",
			success : function () {
				re_load();
			}
		});

	});
	$(".JQueryreturn").click(function() {
				$("#jqModel").jqmHide();
				$("#jqModelDimission").jqmHide();
			});
	$("#saveCos").click(function() {
				action('入职');
			});
	// 查询相关操作
	$("#tosearch").click(function() {
		$("#SearchForm").attr(
				"action",
				basePath + "ea/saudition/ea_getauditionList.jspa?pageNumber="
						+ ppageNumber + "&status=2").serialize();
			document.SearchForm.submit.click();
	});
	$("input.JQ").click(function() {// 保存
		$(".put3").trigger("blur");
		if ($("#cstaffDForm").find(".error").length) {
			return;
		}
		
		$p = $("tr#" + auditionID);
		var personvalue = $p.find("span#staffID").text();
		$("#cstaffDForm")
				.attr(
						"action",
						basePath
								+ "ea/soincumbent/ea_delStaffListForIncumbent.jspa?pageNumber="
								+ ppageNumber + "&sub=1&staffID="
								+ personvalue);
		document.cstaffDForm.submit.click();
	});
			
			$("input.JQueryreturn").click(function() {// 取消
				document.cstaffDForm.reset();
				$("#jqModelDimission").jqmHide();
				
			}); 
	function re_load() {
		var url = basePath
				+ "ea/saudition/ea_getauditionList.jspa?status=2&pageNumber="
				+ ppageNumber + "&audition.staffIdentityCard="
				+ staffIdentityCard + "&audition.staffName=" + sName
				+ "&search" + search;
		document.location.href = encodeURI(url);
	}
});
