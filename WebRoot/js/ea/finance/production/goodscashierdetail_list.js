$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	$(".JQueryreturnsCcompany").click(function() {
				notoken = 0;
				$(".jqmWindow", $("#selectcompanyForm")).jqmHide();
			});
	$(".JQueryreturnsUser").click(function() {
				notoken = 0;
				$(".jqmWindow", $("#selectuserForm")).jqmHide();
			});
	$('.flexme11').flexigrid({
				height : 340,
				width : 'auto',
				minwidth : 30,
				title : "部门物品流水汇总",
				minheight : 80,
				buttons : [{
					name : '查看',
					bclass : 'see',
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
					name : '查询',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '打印条码',
					bclass : 'checkout',
					onpress : action
						//
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
				if (goodsBillsID == "") {
					alert('请选择!');
					return;
				}
				document.cstaffForm.reset();
				$t = $("#cstaffForm");
				$p = $("tr#" + goodsBillsID);
				$p.find("span[id]").each(function() {
							$t.find("span#" + this.id).text($(this).text());
						});
				$("#jqModels").jqmShow();
				break;
			case '导出' :
				url = basePath + "/ea/splitbill/ea_showDetailExcel.jspa?search="
						+ search +"&dtype="+dtype+"&level="+level;
				open(url);
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "/ea/splitbill/ea_getBillsDetailList.jspa?dtype="+dtype+"&level="+level+"&search="
						+ search;
				numback(url);
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				$("input#journalNum").focus();
				break;
			case '打印条码': 
				if(goodsBillsID==""){
                 	alert("请选择！");
                 	return;
                }
				var code = $("tr#" + goodsBillsID).find("#goodsCoding").text(); 
				var bumen = $("tr#" + goodsBillsID).find("#departmentname").text(); 
				var goodsName = $("tr#" + goodsBillsID).find("#goodsName").text(); 			
				var staffName = $("tr#" + goodsBillsID).find("#staffname").text();
				window.open(basePath + "ea/goodsmanage/ea_barcode.jspa?parameter=" + encodeURI(code) + "&departmentName=" 
						+ encodeURI(bumen) + "&goodsName=" + encodeURI(goodsName) + "&staffName=" + encodeURI(staffName));
				break;
		}
	}
	$(".flexme11 tr[id]").dblclick(function() {
				action('查看');// 当双击时出发 action方法.等价于先选中再点击修改按钮
			});
	$(".flexme11 tr[id]").click(function() {
				goodsBillsID = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	$("#tosearch").click(function() {
		var opt=$("#btype").find("option:selected").text();
		$("#SearchForm").attr(
				"action",
				basePath + "/ea/splitbill/ea_toDetailSearch.jspa?pageNumber="
						+ pNumber); 
		document.SearchForm.submit.click();
	});
	$(".JQueryClose").click(function() {
				$("#jqModels").jqmHide();
	});
	
	
	if(level=="organization"){
		var treeName = treeNames;
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
				
		if(dtype=="asset"){
			$("#btype").html("<option value=''>请选择</option><option value='固定资产单'>固定资产单</option><option value='资产增加单'>资产增加单</option><option value='资产减少单'>资产减少单</option>");
			$(".btypetr").show();
		}else if(dtype=="salary"){
			$("#btype").html("<option value=''>请选择</option><option value='应付工资单'>应付工资单</option><option value='已付工资单'>已付工资单</option>");
			$(".btypetr").show();
		}else{
			$(".btypetr").hide();
		}
		
		var pid=companyid;
        var pname=companyname;
        if(level=="allCompany"){
                pid=companyid;
                pname=companyname;
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
                        $("#orgID").html("<option value=''>请选择公司</option>");
                    }
                });
        }
		
        if(level=="allCompany"||level=="company"){
	        // 部门名称change事件
	        $("#orgID").change(function() {
	            var temp = $("#orgID").val();
	            if (temp.substring(0, 7) != 'company') {
	                    getPerson($("#deptID").val()==undefined?companyid:$("#deptID").val(), this.value);
	            } else {
	                $("option", $("select#person")).remove();
	                $("#person").html("<option value=''>请选择部门</option>");
	            }
	        });
        }
			
			
});
function fj(cID) {
	var statusfj = $("tr#" + cID).find("span#status").text();
	if (statusfj != '04') {
		alert("已经归档不能修改附件");
		return;
	}
	window.open(basePath
			+ "ea/uploadfile/ea_getListImage.jspa?loadFile.parmeterID=" + cID);
}

//根据公司ID和部门ID查询责任人
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

	

/** **********************************往来单位**************************************** */
$(document).ready(function() {
	var contactcID = "";// 已经添加到往来单位的ID
	//var ccID = "";// ccompanyID
	$("table#gostable tr[id]").live("click", function(event) {
				contactcID = this.id;
				ccID = this.title;
				$("input.ra", $(this)).attr("checked", "checked");
			});
	// 选择往来单位
	$("#wldw").click(function() {
				$("input#ccompanyID", $("table#searchcompany")).val("");
				$("select#contactConnections", $("table#searchcompany"))
						.val("全部");
				$(".jqmWindow", $("#selectcompanyForm")).jqmShow();
				cxwldw("contactCompany.companyName=&cconnection.contactConnections=");
			});
	// 新增往来单位
	$(".xzdw").click(function() {
		window.open(basePath
				+ "ea/contactcompany/ea_getListContactCompany.jspa?");
	});
	// 添加所选中的往来单位
	$("#qdcompany").click(function() {

		if (contactcID != "") {
			$s = $("#SearchForm");
			var wldwName = $("tr#" + contactcID, $("#selectcompanyForm"))
					.find("td#ccompanyname").html();
			$s.find("input#ccID").attr("value", wldwName);
			$(".jqmWindow", $("#selectcompanyForm")).jqmHide();
		} else {
			alert("请选择往来单位！");
		}
	});
	// 根据输入的往来单位名称查询
	$("input#searchcc").click(function() {
		contactcID = "";
		var typeName = $("input#ccompanyID", $("table#searchcompany")).val();
		var contactConnections = $("select#contactConnections",
				$("table#searchcompany")).val();
		cxwldw("contactCompany.companyName=" + typeName
				+ "&cconnection.contactConnections=" + contactConnections);
	});
	// 上一页
	$("#dwsy").click(function() {
		var sy = $("#dwsy").attr("title");
		if (sy != 0) {
			contactcID = "";
			var typeName = $("input#ccompanyID", $("table#searchcompany"))
					.val();
			var contactConnections = $("select#contactConnections",
					$("table#searchcompany")).val();
			var typeCN = "contactCompany.companyName=" + typeName
					+ "&cconnection.contactConnections=" + contactConnections
					+ "&pageForm.pageNumber=" + sy;
			cxwldw(typeCN);
		} else {
			alert("已是首页！");
		}
	});
	// 下一页
	$("#dwxy").click(function() {
		var xy = $("#dwxy").attr("title");
		if (xy != 0) {
			contactcID = "";
			var typeName = $("input#ccompanyID", $("table#searchcompany"))
					.val();
			var contactConnections = $("select#contactConnections",
					$("table#searchcompany")).val();
			var typeCN = "contactCompany.companyName=" + typeName
					+ "&cconnection.contactConnections=" + contactConnections
					+ "&pageForm.pageNumber=" + xy;
			cxwldw(typeCN);
		} else {
			alert("已是尾页！");
		}
	});
	// ajax查询往来单位列表
	function cxwldw(typeCN) {
		if (notoken) {
			alert("正在获取数据！请稍等");
			return;
		}
		notoken = 1;
		$("#dwsy").attr("title", 0);
		$("#dwxy").attr("title", 0);
		$("#dwzy").attr("title", 0);
		var searchurl = basePath
				+ "ea/contactcompany/sajax_ea_getListContactCompanyByCompanyName.jspa?";
		$.ajax({
			url : encodeURI(searchurl + typeCN + "&date="
					+ new Date().toLocaleString()),
			type : "get",
			async : true,
			dataType : "json",
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				var nologin = member.nologin;
				if (nologin) {
					document.location.href = basePath + "page/ea/not_login.jsp";
				}
				var pageForm = member.pageForm;
				var connectionlist = member.connectionlist;
				if (pageForm == null) {
					alert("没有数据");
					notoken = 0;
					return;
				}
				var $se = $("select#contactConnections",
						$("table#searchcompany"));
				$se.empty();
				$select = "<option selected='selected' value = ''>--全部--</option>";
				$se.append($select);
				for (var i = 0; i < connectionlist.length; i++) {
					$op = $("<option />");
					$op.attr("value", connectionlist[i].codeValue)
							.text(connectionlist[i].codeValue);
					$se.append($op);
				}
				var dqy = pageForm.pageNumber;// 当前页
				var zys = pageForm.pageCount;// 总页数
				if (dqy > 1) {
					$("#dwsy").attr("title", dqy - 1);
				}
				if (dqy < zys) {
					$("#dwxy").attr("title", dqy + 1);
				}
				$("span#zycount").text(zys);
				var tabletr = "<table width='98%' height='26' align='center' cellspacing='0' cellpadding='1' style='font-size:12px;' class='bannb_01'><tr><td height='24' align='left' valign='top' class='txt01' >&nbsp;点击选择往来单位</td></tr></table>";
				tabletr += "<table width='99%' align='center' id='gostable' cellpadding='0'  cellspacing='0' class='table'>";
				tabletr += " <tr><th height='21' align='center' bgcolor='#E4F1FA'>选择</th><th align='center' bgcolor='#E4F1FA'>往来单位名称</th>";
				tabletr += " <th align='center' bgcolor='#E4F1FA'>往来关系</th>";
				tabletr += " <th align='center' bgcolor='#E4F1FA'>行业类别</th><th align='center' bgcolor='#E4F1FA'>单位电话</th>";
				tabletr += " <th align='center' bgcolor='#E4F1FA'>单位负责人</th><th align='center' bgcolor='#E4F1FA'>负责人电话</th>";
				tabletr += " <th align='center' bgcolor='#E4F1FA'>公司地址</th></tr>";
				for (var i = 0; i < pageForm.list.length; i++) {
					tabletr += "<tr style='cursor: hand;' title="
							+ pageForm.list[i].ccompanyID + " id = "
							+ pageForm.list[i].contactConnectionID + ">";
					tabletr += "<td id='checkcc' align='center'><input type ='radio'  class='ra' value="
							+ pageForm.list[i].contactConnectionID
							+ " name='checkradio'/></td>";
					tabletr += "<td id='ccompanyname' align='center'>"
							+ pageForm.list[i].companyName + "</td>";
					tabletr += "<td id='contactConnections' align='center'>"
							+ pageForm.list[i].contactConnections + "</td>";
					tabletr += "<td id='industryType' align='center'>"
							+ pageForm.list[i].industryType + "</td>";
					tabletr += "<td id='companyTel'  align='center'>"
							+ pageForm.list[i].companyTel + "</td>";
					tabletr += "<td id='cresponsible' align='center'>"
							+ pageForm.list[i].cresponsible + "</td>";
					tabletr += "<td id='responsibleTel' align='center'>"
							+ pageForm.list[i].responsibleTel + "</td>";
					tabletr += "<td id='companyAddr'  align='center'>"
							+ pageForm.list[i].companyAddr + "</td>";
					tabletr += "<td id='ccompanyID' style='display:none' align='center'>"
							+ pageForm.list[i].ccompanyID + "</td>";
					tabletr += " </tr>";
				}
				tabletr += " </table>";
				$("#body_02cc").html(tabletr);
				$("#body_02cc").show();
				//alert("数据加载成功");
				notoken = 0;
				window.status = "数据加载成功";
			},
			error : function cbf(data) {
				notoken = 0;
				alert("数据获取失败！");
			}
		});
	}
});

// 选择往来个人
$(document).ready(function() {
	var cuID = "";
	//var userstaffID = "";
	$("table#gouserstable tr[id]").live("click", function(event) {
				cuID = this.id;
				userstaffID = this.title;
				$("input.rauser", $(this)).attr("checked", "checked");
			});
	// 选择往来个人
	$("#wlgr").click(function() {
				$("input#contactUserID", $("table#searchuser")).val("");
				$("select#relation", $("table#searchuser")).val("全部");
				$(".jqmWindow", $("#selectuserForm")).jqmShow();
				cxwlgr("contactUser.staffName=&contactUser.relation=");
			});
	// 新增往来个人
	$(".xzgr").click(function() {
				window.open(basePath
						+ "ea/cstaff/ea_getListCStaffByCompanyID.jspa?");
			});

	// 添加所选中的往来个人
	$("#qduser").click(function() {
		if (cuID != "") {
			$s = $("#SearchForm");
			var wlgrName = $("tr#" + cuID, $("#selectuserForm"))
					.find("td#contactUserName").html();
			$s.find("input#cuID").attr("value", wlgrName);
			$(".jqmWindow", $("#selectuserForm")).jqmHide();
		} else {
			alert("请选择往来个人！");
		}
	});
	// 根据输入的往来个人名称查询
	$("input#searchuu").click(function() {
		cuID = "";
		userstaffID = "";
		var typeName = $("input#contactUserID", $("table#searchuser")).val();
		var relation = $("select#relation", $("table#searchuser")).val();
		cxwlgr("contactUser.staffName=" + typeName + "&contactUser.relation="
				+ relation);
	});
	// 上一页
	$("#grsy").click(function() {
		var sy = $("#grsy").attr("title");
		if (sy != 0) {
			cuID = "";
			userstaffID = "";
			var typeName = $("input#contactUserID", $("table#searchuser"))
					.val();
			var relation = $("select#relation", $("table#searchuser")).val();
			var typeCN = "contactUser.staffName=" + typeName
					+ "&contactUser.relation=" + relation
					+ "&pageForm.pageNumber=" + sy;
			cxwlgr(typeCN);
		} else {
			alert("已是首页！");
		}
	});
	// 下一页
	$("#grxy").click(function() {
		var xy = $("#grxy").attr("title");
		if (xy != 0) {
			cuID = "";
			userstaffID = "";
			var typeName = $("input#contactUserID", $("table#searchuser"))
					.val();
			var relation = $("select#relation", $("table#searchuser")).val();
			var typeCN = "contactUser.staffName=" + typeName
					+ "&contactUser.relation=" + relation
					+ "&pageForm.pageNumber=" + xy;
			cxwlgr(typeCN);
		} else {
			alert("已是尾页！");
		}
	});
	// ajax查询往来个人列表
	function cxwlgr(typeCN) {
		if (notoken) {
			alert("正在获取数据！请稍等");
			return;
		}
		notoken = 1;
		$("#grsy").attr("title", 0);
		$("#grxy").attr("title", 0);
		$("#grzy").attr("title", 0);
		var searchurl = basePath
				+ "ea/contactuser/sajax_ea_getListContactUserBycontactUserName.jspa?";
		$.ajax({
			url : encodeURI(searchurl + typeCN + "&date="
					+ new Date().toLocaleString()),
			type : "get",
			async : true,
			dataType : "json",
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				var nologin = member.nologin;
				if (nologin) {
					document.location.href = basePath + "page/ea/not_login.jsp";
				}

				var pageForm = member.pageForm;
				var codeRelationList = member.codeRelationList;
				if (pageForm == null) {
					alert("没有数据");
					notoken = 0;
					return;
				}
				var $se = $("select#relation", $("table#searchuser"));
				$se.empty();
				$select = "<option selected='selected' value = ''>--全部--</option>";
				$se.append($select);
				for (var i = 0; i < codeRelationList.length; i++) {
					$op = $("<option />");
					$op.attr("value", codeRelationList[i].codeValue)
							.text(codeRelationList[i].codeValue);
					$se.append($op);
				}
				var dqy = pageForm.pageNumber;// 当前页
				var zys = pageForm.pageCount;// 总页数
				if (dqy > 1) {
					$("#grsy").attr("title", dqy - 1);
				}
				if (dqy < zys) {
					$("#grxy").attr("title", dqy + 1);
				}
				$("span#grzycount").text(zys);
				var tabletr = "<table width='98%' height='26' align='center' cellspacing='0' cellpadding='1' style='font-size:12px;' class='bannb_01'><tr><td height='24' align='left' valign='top' class='txt01' >&nbsp;点击选择往来个人</td></tr></table>";
				tabletr += "<table width='99%' align='center' id='gouserstable' cellpadding='0'  cellspacing='0' class='table'>";
				tabletr += " <tr><th height='21' align='center' bgcolor='#E4F1FA'>选择</th><th align='center' bgcolor='#E4F1FA'>个人名称</th>"
						+ "<th align='center' bgcolor='#E4F1FA'>往来关系</th><th align='center' bgcolor='#E4F1FA'>电话</th><th align='center' bgcolor='#E4F1FA'>身份证</th><th align='center' bgcolor='#E4F1FA'>qq</th><th align='center' bgcolor='#E4F1FA'>邮箱 </th><th align='center' bgcolor='#E4F1FA'>家庭地址</th></tr>";
				for (var i = 0; i < pageForm.list.length; i++) {
					tabletr += "<tr style='cursor: hand;' id = "
							+ pageForm.list[i].relationID + " title= "
							+ pageForm.list[i].staffID + ">";
					tabletr += "<td id='checkcc' align='center'><input type ='radio'  class='rauser' value="
							+ pageForm.list[i].relationID
							+ " name='checkradio'/></td>";
					tabletr += "<td id='contactUserName' align='center'>"
							+ pageForm.list[i].staffName + "</td>";
					tabletr += "<td id='phone' align='center'>"
							+ pageForm.list[i].relation + "</td>";
					tabletr += "<td id='tel' align='center'>"
							+ pageForm.list[i].reference + "</td>";
					tabletr += "<td id='staffIdentityCard' align='center'>"
							+ pageForm.list[i].staffIdentityCard + "</td>";
					tabletr += "<td id='userQq'  align='center'>"
							+ pageForm.list[i].referenceCode + "</td>";
					tabletr += "<td id='email'  align='center'>"
							+ pageForm.list[i].referenceOrganization + "</td>";
					tabletr += "<td id='userAddr'  align='center'>"
							+ pageForm.list[i].staffAddress + "</td>";
					tabletr += "<td id='contactUserID'  style='display:none' align='center'>"
							+ pageForm.list[i].staffID + "</td>";
					tabletr += " </tr>";
				}
				tabletr += " </table>";
				$("#body_02cu").html(tabletr);
				$("#body_02cu").show();
				//alert("数据加载成功");
				notoken = 0;
				window.status = "数据加载成功";
			},
			error : function cbf(data) {
				notoken = 0;
				alert("数据获取失败！");
			}
		});
	}
});