$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	$('.JQueryflexme').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : '银行账户管理',
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
				accid = '';
				$("input.JQuerypersonvalue").attr("checked", false);
				document.cstaffForm.reset();
				$("#jqModel").jqmShow();
				$("option", $("#oorgID")).remove();
				$("#oorgID").html("<option value=''>请选择部门</option>");
				break;
			case '删除' :
				if (accid == "") {
					alert('请选择账号');
					return
				}
				if (confirm("是否删除？")) {
					$f = $('#cstaffForm');
					$f.find('#accid').val(accid);
					var url = basePath + "ea/bankaccountManager/ea_delBankAccount.jspa?pageNumber="
							+ pNumber;
					$f.attr("target", "hidden").attr("action", url);
					document.cstaffForm.submit.click();
					$("tr#" + accid).remove();
					accid = "";
					token = 11;
				}
				break;
			case '修改' :
				if (accid == "") {
					alert('请选择账号');
					return
				}
				document.cstaffForm.reset();
				$t = $("table#stafftable");
				$p = $("tr#" + accid);
				$t.find('img#photo').attr("src",
						basePath + $p.find("span#photo").text());
				$p.find("span[id]").each(function() {
							$t.find(":input[name]#" + this.id).val($(this).text());
						});
				$("select[id=deptID]").val($("span[id=companyid]").html());				
				bmDept($("span[id=companyid]").html());				  
				$("#jqModel").jqmShow();				
				$("select[id=oorgID]").val($("span[id=orgid]").html());				
				break;			
			case '设置每页显示条数' :
				var url = basePath + "ea/bankaccountManager/ea_getBankAccountList.jspa?search=" + search;
				numback(url);
				break;
		}
	}
	$(".JQueryflexme tr[id]").dblclick(function() {
				action('修改');// 当双击时出发 action方法.等价于先选中再点击修改按钮
			});
	$(".JQueryflexme tr[id]").click(function() {
				accid = this.id;				
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	$(".JQuerySubmit").click(function() {	
		$("input[id=companyname]").val($("#deptID option:selected").text());
		$("input[id=orgname]").val($("#oorgID option:selected").text().replace("├","").replace(" ",""));
		$(".put3", $("table#stafftable")).trigger("blur");
		if ($("#cstaffForm .error").length) {
			alert("请填完所有必填项");
			notoken = 0;
			return;
		}
		$("#cstaffForm").attr("target", "hidden").attr("action",
				basePath + "ea/bankaccountManager/ea_saveBankAccount.jspa?pageNumber=" + pNumber);		
		if (accid == "") {			
			document.cstaffForm.submit.click();
			$("#cstaffForm").find(":input[name]").val("");
			token = 1;
			return;
		}		
		document.cstaffForm.submit.click();
		token = 2;
	});
	$(".JQueryreturn").click(function() {
				$("#jqModel").jqmHide();
				re_load();
			});	
});

//查询总公司下的所有子公司
$(function() {	
	var pid="";
	var pname="";
	pid=treeids;
	pname=treename;
	
	var url = basePath
			+ "ea/company/sajax_n_ea_getCompanyList.jspa?date="
			+ new Date().toLocaleString();
	$.ajax({
				url : encodeURI(url),
				type : "get",
				async : true,
				dataType : "json",
				success : function cbf(data) {					
					var member = eval("(" + data + ")");
					var companylist = member.companylist;
					var data1 = new Array();
					data1[0] = {
						id : pid,
						pid : '-1',
						text :pname
					};
					for (var i = 0; i < companylist.length; i++) {
						data1[i + 1] = {
							id : companylist[i].companyID,
							pid : companylist[i].companyPID,
							text : companylist[i].companyName
						};
					}
					var ts3 = new TreeSelector($("#deptID")[0],data1, -1);
					ts3.createTree();
				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}
			});
	// 公司名称change事件
	$("#deptID").change(function() {				
				if ($(this).val() != '') {					
					bmDept(this.value);
				} else {					
					$("option", $("#oorgID")).remove();
					$("#oorgID")
							.html("<option value=''>请选择部门</option>");
				}
			});
});

//根据公司ID查询对应的部门列表
function bmDept(val) {	
	$("option", $("#oorgID")).remove();
	var url = basePath
			+ "ea/responsibilitiessummary/sajax_n_ea_getoList.jspa?companyID="
			+ val + "&date=" + new Date().toLocaleString();
	$.ajax({
			url : encodeURI(url),
			type : "get",
			async : false,
			dataType : "json",
			success : function cbf(data) {
				/** **添加部门列表** */
				var member = eval("(" + data + ")");
				var oList = member.organizationlist;
				var data2 = new Array();
				data2[0] = {
					id : $("#deptID").attr("value"),
					pid : '-1',
					text : '全部'
				};
				for (var i = 0; i < oList.length; i++) {
					data2[i + 1] = {
						id : oList[i].organizationID,
						pid : oList[i].organizationPID,
						text : oList[i].organizationName
					};
				}
				ts = new TreeSelector($("#oorgID")[0], data2, -1);
				ts.createTree();
			},
			error : function cbf(data) {
				alert("数据获取失败！");
			}
			});
	$("option[value=" + this.value + "]", $("#oorgID")).val("");	
}

function re_load() {
	if (token) {
		document.location.href = basePath
				+ "ea/bankaccountManager/ea_getBankAccountList.jspa?pageNumber=" + pNumber
				+ "&pageForm.pageNumber=" + $("#pageNumber").attr("value");
	}
}