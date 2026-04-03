$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	$(".jqmreturn").click(function() {
				notoken = 0;
				$("#jqmAdd").jqmHide();
				showDocument = false;
			});
	$('.flexme11').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : "集团单据归档",
				minheight : 80,
				buttons : [{
					name : '查看',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
				}, {
					separator : true
				},{
					name : '查询',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
				}, {
					separator : true
				}, {
					name : '生成报表',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
				}, {
					separator : true
				},{
					name : '打印预览',
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
			
			case '查看':
				if (cashierBillsID == "") {
					alert("请选择！");
					return;
				}
				document.location.href = basePath
						+ "/ea/archivessummary/ea_toCashier.jspa?pageNumber="
						+ pNumber + "&tt=x&historyCashierBills.cashierBillsID="
						+ cashierBillsID + "&search=" + search
						+ "&pagepageNumber=" + $("#pageNumber").attr("value");
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
			case '生成报表' :
				$("#FormsSearch").jqmShow();
				break;
			case '打印预览' :
			if (cashierBillsID == "") {
					alert("请选择！");
					return;
				}
				window
						.open(basePath
								+ "/ea/printcashierbills/ea_toprintArchive.jspa?cashierBillsVO.cashierBillsID="
								+ cashierBillsID);
			break;
			case '设置每页显示条数' :
				var url = basePath
						+ "/ea/archivessummary/ea_getArchivesList.jspa?tt=x&search="
						+ search + "&sdate=" + sdate + "&edate=" + edate;
				numback(url);
				break;
		}
	}
	$(".flexme11 tr[id]").click(function() {
				cashierBillsID = this.id;
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
		$("#SearchForm")
				.attr(
						"action",
						basePath
								+ "/ea/archivessummary/ea_toSearch.jspa?tt=x&pageNumber="
								+ pNumber);
		document.SearchForm.submit.click();
	});
	$("#toFormsSearch").click(function() {
		var opt=$("#btypes").find("option:selected").text();
		if(opt=="请选择"){
			var ss="";
		}else{
			var leng=opt.length;
			var num=$("#btypes").find("option:selected").text().indexOf("├");
			var ss=$("#btypes").find("option:selected").text().substr(num+1,leng);
		}
		chuanzhi="&historyVO.billsType="+ss+"&historyVO.departmentID="+$("#orgID2").val()+"&historyVO.companyID="+$("#deptID2").val()+
		"&historyVO.ccompanyname="+$("#danwei").val()+"&historyVO.contactUserName="+$("#peo").val()+
/*		"&historyVO.staffID="+$("#zeren").find("option:selected").val()+*/
		"&historyVO.contactConnections="+$("#danid").find("option:selected").val()+
		"&historyVO.phone="+$("#pepid").find("option:selected").val();
		window.open(basePath + "/ea/archivessummary/ea_toStatements.jspa?tt=g&sfdate=" + $("#sfdate").val() + "&efdate=" + $("#efdate").val()+"&chuanzhi="+chuanzhi);
		$("#FormsSearch").jqmHide();
	});
	$(".flexme11 tr[id]").dblclick(function() {
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				cashierBillsID = this.id;
				action("查看");
			});
		});
		

$(document).ready(function() {
	// 删除
	$("#delButton").click(function() {
				if (cashierBillsID == "") {
					alert("请选择！");
					return;
				}
				$("tr#"+cashierBillsID).remove();
				docNull-=1;
	});
	
	// 这一行的单击事件
	$("table#gostable tr[id]").live("click", function(event) {
				cashierBillsID = this.id;
				$("input.ra", $(this)).attr("checked", "checked");
			});
			
	
	
	$(function() {
	var pid="";
		var pname="";
			pid=ids;
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
							var ts3 = new TreeSelector($("#deptID")[0], data1,
									-1);
							ts3.createTree();
							var ts4 = new TreeSelector($("#deptID2")[0], data1,
									-1);
							ts4.createTree();
						},
						error : function cbf(data) {
							alert("数据获取失败！");
						}
					});

			$("#deptID").change(function() {
						if ($(this).val() != '') {
							bmdept(this.value);
						} else {
							$("option", $("#orgID")).remove();
							$("#orgID").html("<option value=''>请选择公司</option>");
						}
					});
			$("#deptID2").change(function() {
						if ($(this).val() != '') {
							bmdept2(this.value);
						} else {
							$("option", $("#orgID2")).remove();
							$("#orgID2").html("<option value=''>请选择公司</option>");
						}
					});
			$("#orgID").change(function() {
						var temp = $("#orgID").val();
						if (temp.substring(0, 7) != 'company') {
							getPerson($("#deptID").val(), this.value);
						} else {
							$("option", $("select#person")).remove();
							$("#person")
									.html("<option value=''>请选择部门</option>");
						}
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
        	 var ts1 = new TreeSelector($("#btypes")[0], data2, -1);
        		ts1.createTree();
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

function bmdept(val) {
	$("option", $("#orgID")).remove();
	var url = basePath
			+ "ea/responsibilitiessummary/sajax_n_ea_getoList.jspa?companyID="
			+ val + "&pid=" + val + "&date=" + new Date().toLocaleString();
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
					ts = new TreeSelector($("#orgID")[0], data2, -1);
					ts.createTree();
				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}
			});

	$("option[value=" + this.value + "]", $("#orgID")).val("");
}

function bmdept2(val) {
	$("option", $("#orgID2")).remove();
	var url = basePath
			+ "ea/responsibilitiessummary/sajax_n_ea_getoList.jspa?companyID="
			+ val + "&pid=" + val + "&date=" + new Date().toLocaleString();
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
						id : $("#deptID2").attr("value"),
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
					ts = new TreeSelector($("#orgID2")[0], data2, -1);
					ts.createTree();
				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}
			});

	$("option[value=" + this.value + "]", $("#orgID2")).val("");
}
});
function re_load() {
	
	document.location.href = basePath
			+ "/ea/archivessummary/ea_getArchivesList.jspa?pageNumber="
			+ pNumber + "&pageForm.pageNumber="	+ $("#pageNumber").attr("value")
			+ "&sdate=" + sdate + "&edate="+ edate+"&search="+ search;
}

