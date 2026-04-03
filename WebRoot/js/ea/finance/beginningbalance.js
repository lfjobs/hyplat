$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	$('.flexme11').flexigrid({
				height : 330,
				width : 'auto',
				minwidth : 30,
				title : "期初余额列表",
				minheight : 80,
				buttons : [{
					name : '添加',
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
					// 设置分割线
					separator : true
				}, {
					name : '删除',
					bclass : 'delete',
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
				}]
			});

	function action(com, grid) {
		switch (com) {
			case '添加' :
				$("#jqModeladd").jqmShow();
				$("form#addForm").find("input#journalNum").focus();
				break;
			case '修改' :
				if (cashierBillsID == "") {
					alert("请选择！");
					return;
				}
				$("tr#" + cashierBillsID).find("span[id]").each(function() {
					$("#addForm").find(":input#BeginningBalanceID").val(cashierBillsID);
							$("#addForm").find(":input#" + this.id).val($(this).text());//.attr("readonly","readonly")
							
						});
				$("#jqModeladd").jqmShow();
				$("form#addForm").find("input#journalNum").focus();
				break;
			case '删除' :
				$form = $("#CashierBillsform");
				if (cashierBillsID == "") {
					alert("请选择！");
					return;
				}
				if (confirm("确定删除？")){
					$form
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "/ea/beginningbalance/ea_delIPaddress.jspa?search="
											+ search + "&pageNumber=" + pNumber);
					$form.find("input#BeginningBalanceID").val(cashierBillsID);
					document.CashierBillsform.submit.click();
					$("tr#" + cashierBillsID).remove();
					cashierBillsID = "";
					token = 11;
				}
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "/ea/beginningbalance/ea_getListIPaddress.jspa?search="
						+ search + "&sdate=" + sdate +"&level="+level;
				numback(url);
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				$("form#SearchForm").find("input#journalNum").focus();
				break;
			
		}
	}
	$(".flexme11 tr[id]").click(function() {
				cashierBillsID = this.id;
				status = $("tr#" + cashierBillsID).find("span#status").text();
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	$("#tosearch").click(function() {
		$("#SearchForm").attr(
				"action",
				basePath + "/ea/beginningbalance/ea_toSearch.jspa?pageNumber="
						+ pNumber+"&level="+level);
		document.SearchForm.submit.click();
	});
			
	$("#toadd").click(function() {
		$(".isNaN").trigger("blur");
		$(".put3").trigger("blur");
		if ($("#addForm .error").length) {
			alert("请填写必填项");
			notoken = 0;
			return;
		}
		$("#addForm").attr("target", "hidden").attr("action",
						basePath + "/ea/beginningbalance/ea_addIPaddress.jspa?pageNumber="
								+ pNumber+ "&zz=" + zz+"&level="+level);
		document.addForm.submit.click();
		token = 12;
	});
	$("input#Customizedate1").blur(function(){
		if($(this).val()!=""){
			var url = basePath + "/ea/beginningbalance/sajax_ea_ajaxSearch.jspa?sdate="+$(this).val()+"&date="
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
						var tt=member.tt;
						if(tt=="01"){
							alert("该月份期初余额已存在,不可重复添加！");
							return;
						}
					}
				});
		}
		
	});
});



function re_load() {
	var url = basePath
			+ "/ea/beginningbalance/ea_getListIPaddress.jspa?sdate="+sdate;
	document.location.href = encodeURI(url);
}
