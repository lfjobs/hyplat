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
				title : "出纳记账单列表",
				minheight : 80,
				buttons : [ {
					name : '查看',
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
			case '查看' :
				if (cashierBillsID == "") {
					alert("请选择！");
					return;
				}
				document.location.href = basePath
						+ "/ea/rejectbills/ea_toSaveCashierBills.jspa?pageNumber="
						+ pNumber + "&cashierBills.cashierBillsID="
						+ cashierBillsID + "&search=" + search
						+ "&pagepageNumber=" + $("#pageNumber").attr("value")
						+ "&differenceStyle=" + differenceStyle+"&level="+level;
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				$("form#SearchForm").find("input#journalNum").focus();
				break;
			case '导出' :
				url = basePath + "/ea/rejectbills/ea_showExcel.jspa?search="
						+ search + "&sdate=" + sdate + "&edate=" + edate+"&level="+level;
				open(url);
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "/ea/rejectbills/ea_getCashierBillsList.jspa?search="
						+ search + "&sdate=" + sdate + "&edate=" + edate+"&level="+level ;
				numback(url);
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
	var opt=$("#btype").find("option:selected").text();
		if(opt=="请选择"){
			$("#btype").find("option:selected").attr("value","");
		}else{
			var leng=opt.length;
			var num=$("#btype").find("option:selected").text().indexOf("├");
			$("#btype").find("option:selected").attr("value",$("#btype").find("option:selected").text().substr(num+1,leng));
		}
		if(level=='company'){
			if($("#orgID").val()==companyid){
				$("#orgID").attr("value","");
				}
		};
		$("#SearchForm").attr(
				"action",
				basePath + "/ea/rejectbills/ea_toSearch.jspa?pageNumber="
						+ pNumber+"&level="+level);
		document.SearchForm.submit.click();
	});
	$(".flexme11 tr[id]").dblclick(function() {
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				cashierBillsID = this.id;
				action("查看");
			});
	
			
	if(level=="organization"){
	var treeName =treeNames;
	var treePID = treeID;
	var treePName = treeNames;

	var url = basePath + "ea/responsibilities/sajax_n_ea_getoList.jspa?date="
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
					var oList = member.organizationlist;
					var data = new Array();
					data[0] = {
						id : treeID,
						pid : '-1',
						text : treeName
					};
					for (var i = 0; i < oList.length; i++) {
						data[i + 1] = {
							id : oList[i].organizationID,
							pid : oList[i].organizationPID,
							text : oList[i].organizationName
						};
					}
					$t = $("table#SearchTable");
					var ts = new TreeSelector(
							$t.find("select#orgID")[0], data, -1);
					ts.createTree();
				},
				error : function cbf(data) {
					// alert("数据获取失败！")
				}
			});
	}else if(level=="company"){
		bmDept(companyid);
	}
	
	// 查询总公司下的所有子公司
	$(function() {
		var pid=companyid;
		var pname=companyname;
		
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
								var ts3 = new TreeSelector($("#deptID")[0],
										data1, -1);
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
								$("option", $("#orgID")).remove();
								$("#orgID")
										.html("<option value=''>请选择公司</option>");
							}
						});
				// 部门名称change事件
				$("#orgID").change(function() {
							var temp = $("#orgID").val();
							if (temp.substring(0, 7) != 'company') {
								if(level=="allCompany"){
									
									getPerson($("#deptID").val(), this.value);
								}else{
									getPerson(pid, this.value);
								}
							} else {
								$("option", $("select#person")).remove();
								$("#person")
										.html("<option value=''>请选择部门</option>");
							}
						});

			});
			
			
	var treeid = 'scode20101101dfs3uhdprp0000000029'; //单据类别
	var url = basePath + "ea/ccode/sajax_ea_getAllListCCodeByPID.jspa?codeID="+treeid+"&date="+new Date().toLocaleString(); 
	$.ajax({
		    url:encodeURI(url),
		    type: "get",
			async: true,
			dataType: "json",
			success: function cbf(data){
				var member = eval("(" + data + ")");
                var oList = member.codeList;
                var data2 = new Array();
		        data2[0] = {
	                id: treeid,
	                pid: '-1',
	                text: '请选择'
	            };
                for (var i = 0; i < oList.length; i++) {
                    data2[i + 1] = {
                        id: oList[i].codeID,
                        pid: oList[i].codePID,
                        text: oList[i].codeValue
                    };
                }
               var ts = new TreeSelector($("#btype")[0], data2, -1);
        		ts.createTree();
			},
			error: function cbf(data){
				alert("数据获取失败！");
			}
	});

});
// 根据公司ID和部门ID查询责任人
function getPerson(company, org) {
	$("option", $("select#person")).remove();
	var url = basePath
			+ "ea/cashiersummary/sajax_ea_getStaffList.jspa?currentCompanyID="
			+ company + "&currentOrgnizationID=" + org + "&date111="
			+ new Date();
	$.ajax({
		url : encodeURI(url),
		type : "get",
		async : true,
		dataType : "json",
		success : function cbf(data) {
			var member = eval("(" + data + ")");
			var persons = member.stafflist;
			var str = "<option value=''>全部</option>";
			for (var i = 0; i < persons.length; i++) {
				var obj = persons[i];
				str += "<option value='" + obj[0] + "'>" + obj[1] + "</option>";
			}
			$("#person").html(str);
		}
	});
}

// 根据公司ID查询对应的部门列表
function bmDept(val) {
	$("option", $("#orgID")).remove();
	var url = basePath
			+ "ea/responsibilitiessummary/sajax_n_ea_getoList.jspa?companyID="
			+ val + "&date=" + new Date().toLocaleString();
	$.ajax({
				url : encodeURI(url),
				type : "get",
				async : true,
				dataType : "json",
				success : function cbf(data) {
					/** **添加部门列表** */
					var member = eval("(" + data + ")");
					var oList = member.organizationlist;
					var data2 = new Array();
					data2[0] = {
						id : val,
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
					
					ts = new TreeSelector($("#orgID")[0], data2, -1);
					ts.createTree();
				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}
			});

	$("option[value=" + this.value + "]", $("#orgID")).val("");
}
function re_load() {
	var url = basePath
			+ "/ea/rejectbills/ea_getCashierBillsList.jspa?pageNumber="
			+ pNumber + "&pageForm.pageNumber="
			+ $("#pageNumber").attr("value") + "&sdate=" + sdate + "&edate="
			+ edate+"&level="+level;
	document.location.href = encodeURI(url);
}
