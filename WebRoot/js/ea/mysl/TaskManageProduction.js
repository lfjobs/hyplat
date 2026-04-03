$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	$('.JQueryflexme').flexigrid({
				height : 300,
				width : 'auto',
				minwidth : 30,
				title : '生产设计阶段',
				minheight : 80,
				buttons : [{
					name : '提交任务成果',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '查看',
					bclass : 'see',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '传阅任务',
					bclass : 'send',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},{
					name : '报送审批',
					bclass : 'examine',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '转入完成阶段',
					bclass : 'transfer',
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
				},{
					name : '导出',
					bclass : 'excel',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},{
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
			case '提交任务成果' :
				var str = "";
				$("[name='chbox']").each(function() {
					if ($(this).is(':checked')) {
						str += $(this).val() + ",";
						taskid = $(this).val();
					}
				});
				if (str == "" || str.length == 0||str.split(",").length>2) {
					alert('请选择单个任务');
					return
				}
				document.location.href = basePath+ "ea/taskmanage/ea_seeTaskPage.jspa?mytask.taskid="+taskid+"&editOrAdd=editOrAdd";
				break;
			case '查看' :
				var str = "";
				$("[name='chbox']").each(function() {
					if ($(this).is(':checked')) {
						str += $(this).val() + ",";
						taskid = $(this).val();
					}
				});
				if (str == "" || str.length == 0||str.split(",").length>2) {
					alert('请选择单个任务');
					return
				}
				document.location.href = basePath
							+ "ea/taskmanage/ea_seeTaskPage.jspa?mytask.taskid="+taskid;
				break;
			case '传阅任务' :
				var str = "";
				$("[name='chbox']").each(function() {
					if ($(this).is(':checked')) {
						str += $(this).val() + ",";
						taskid = $(this).val();
					}
				});
				if (str == "" || str.length == 0) {
					alert('请选择');
					return
				}
				selectPeople();
				break;
			case '报送审批' :
				var str = "";
				$("[name='chbox']").each(function() {
					if ($(this).is(':checked')) {
						str += $(this).val() + ",";
						taskid = $(this).val();
					}
				});
				if (str == "" || str.length == 0) {
					alert('请选择');
					return
				}
				$("#jqModelSend").jqmShow();
				document.SendForm.reset();
				getAllCompanyOfGroup();
				break;
			case '转入完成阶段' :
				var str = "";
				var b = false;
				$("[name='chbox']").each(function() {
					if ($(this).is(':checked')) {
						str += $(this).val() + ",";
						taskid = $(this).val();
						if($("span#auditstatus",$("tr#"+taskid)).text()!="02"){
							b = true;
						}
					}
				});
				if (str == "" || str.length == 0) {
					alert('请选择');
					return;
				}
				if(b){
					alert("选中任务包含未通过审核!!");
					return;
				}
				if (confirm("确认要转入完成阶段？")) {
					
					$("[name='chbox']").each(function(index) {
						if ($(this).is(':checked')) {
							var value=$(this).val();
							$("<input>",{type:"hidden",value:value,name:"dtMytaskMap[" + index + "].taskid"}).appendTo($("form#searchForm"));
						}
					});
					$("#phasestatus",$("#searchForm")).val("02");
					$("#searchForm")
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/taskmanage/ea_toChangePhaseByTask.jspa");
					document.searchForm.submit.click();
					token = 2;
				}	
				break;
			case '查询' :
				$("#jqModel").jqmShow();
				break;
			case '导出' :
				var url = basePath + "ea/taskmanage/ea_toExportExcelByTask.jspa?search=" + search+"&myproject.proid="+proid+"&production=production&mytask.phasestatus=01";
				open(url);
				break;	
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/taskmanage/ea_getListByTaskManageProduction.jspa?search=" + search+"&myproject.proid="+proid;
				numback(url);
				break;
		}
	}
	
	$("input.JQueryreturn").click(function() {// 取消
				$("#jqModel").jqmHide();
				location.reload(); 
			});
	$(".close").click(function() {// 取消
				$("#jqModel").jqmHide();
				location.reload(); 

	});

	$(".chx").click(function() {
		var b = $(this).attr("checked");
		$(this).attr("checked", !b);
	});
	
	$(".JQueryflexme tr[id]").click(function() {
		var d = $("input.chx", $(this)).attr("checked");
		$("input.chx", $(this)).attr("checked", !d);
	});
	
	// 查询
	$("#search").click(function() {
		$("#searchForm").attr("action",
				basePath + "ea/taskmanage/ea_toSearchByTaskManageProduction.jspa");
		document.searchForm.submit.click();

	});
	
	$("#submitResult").click(function() {
		if ($("#SendForm #receiverID").val() == "") {
			alert("请选择收件人");
			return;
		}

		if (confirm("确认要发送至领导审批？")) {
			
			$("[name='chbox']").each(function(index) {
				if ($(this).is(':checked')) {
					var value=$(this).val();
					$("<input>",{type:"hidden",value:value,name:"dtMytaskMap[" + index + "].taskid"}).appendTo($("form#SendForm"));
				}
			});
			var index=0;
			var auditorcompanyid=$("select#receiverCompanyID").find("option:selected").val();
			var auditorcompanyname=$("select#receiverCompanyID").find("option:selected").text();
			var auditororgID=$("select#receiverDeptID").find("option:selected").val();
			var auditororgName=$("select#receiverDeptID").find("option:selected").text();
			var auditorid=$("select#receiverID").find("option:selected").val();
			var auditorname=$("select#receiverID").find("option:selected").text();
			$("<input>",{type:"hidden",value:auditorcompanyid,name:"dtMyauditMap[" + index + "].auditorcompanyid"}).appendTo($("form#SendForm"));
			$("<input>",{type:"hidden",value:auditorcompanyname,name:"dtMyauditMap[" + index + "].auditorcompanyname"}).appendTo($("form#SendForm"));	
			$("<input>",{type:"hidden",value:auditororgID,name:"dtMyauditMap[" + index + "].auditororgID"}).appendTo($("form#SendForm"));
			$("<input>",{type:"hidden",value:auditororgName,name:"dtMyauditMap[" + index + "].auditororgName"}).appendTo($("form#SendForm"));
			$("<input>",{type:"hidden",value:auditorid,name:"dtMyauditMap[" + index + "].auditorid"}).appendTo($("form#SendForm"));
			$("<input>",{type:"hidden",value:auditorname,name:"dtMyauditMap[" + index + "].auditorname"}).appendTo($("form#SendForm"));
			
			$("<input>",{type:"hidden",value:"00",name:"dtMyauditMap[" + index + "].taskstatus"}).appendTo($("form#SendForm"));
			$("#SendForm")
						.attr("target", "hidden")
						.attr(
								"action",
								basePath
										+ "ea/taskmanage/ea_toExamineByTask.jspa?date="
										+ new Date());
			document.SendForm.submit.click();
			token = 2;
		}

	});
	
	
	//组织机构数加载数据--------------------------------开始----------------------------------------------------
	var url1 = basePath
			+ "ea/office/sajax_ea_getOrganizationList.jspa?organizationID="
			+ encodeURI(treeid) + "&datesete=" + new Date();
	$.ajax({
				url : url1,
				type : "get",
				dataType : "json",
				success : function cbf(data) {

					var member = eval("(" + data + ")");
					var nologin = member.nologin;
					if (nologin) {
						document.location.href = basePath
								+ "page/ea/not_login.jsp";
					}
					var oList = member.organizationList;
					var data = new Array();
					data[0] = {
						id : treeid,
						pid : '-1',
						durl : 0,
						text : companyName,
						str : '00'
					};
					for (var i = 0; i < oList.length; i++) {
						data[i + 1] = {
							id : oList[i].organizationID,
							pid : oList[i].organizationPID,
							text : oList[i].organizationName,
							str : oList[i].status

						};
					}
					parentMenu(treeid, data);
				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}
			});
			//双击备选人员
	$("#leftfields").dblclick(function() {
				var left_vo, right_vo, vos, i,j;
				vos = document.getElementsByName('leftfields');
				if (vos == null)
					return false;
				left_vo = vos[0];
				vos = document.getElementsByName('rightfields');
				if (vos == null)
					return false;
				right_vo = vos[0];
				for (i = 0; i < left_vo.options.length; i++) {
				    if (left_vo.options[i].selected) {
				    	   var left_value=left_vo.options[i].value.split("-")[0];
				    	   for (j = 0; j < right_vo.options.length; j++) {
							   var right_value=right_vo.options[j].value.split("-")[0];
							   if(left_value==right_value)
							   {
								   	 alert("人员已经选择，不能重复！");
								   	 return false;
						       }
						    }
							var no = new Option();
							no.value = left_vo.options[i].value;
							no.text = left_vo.options[i].text;
							right_vo.options[right_vo.options.length] = no;
						}
				}
				
				
				// 设为要可选状态
				for (i = 0; i < right_vo.options.length; i++) {
					right_vo.options[i].selected = true;
				}

				return true;
			});
    //双击已选人员
	$("#rightfields").dblclick(function() {
				var vos, right_vo, i;
				vos = document.getElementsByName('rightfields');
				if (vos == null)
					return false;
				right_vo = vos[0];
				for (i = right_vo.options.length - 1; i >= 0; i--) {
					if (right_vo.options[i].selected) {
						// alert(i);
						right_vo.options.remove(i);
					}
				}
				// 设为要可选状态

				for (i = 0; i < right_vo.options.length; i++) {
					right_vo.options[i].selected = true;
				}

				return true;
			});
	//确定选择人员
	$("#query_add").click(function() {
				$("#leftfields").dblclick();
			});
   //删除已经选的人员
	$("#query_delete").click(function() {
				$("#rightfields").dblclick();
			});
//组织机构数加载数据-----------------------结束------------------------------
	
});

/**---------------------------------------------------------选择多个人员---------------------------------------------**/

function selectPeople() {
    $("#zj").jqmShow();
}

//选择人员确定
function submit() {
	var lengths = document.getElementById("rightfields").options.length;// 下拉项的长度
	if (lengths==0) {
		alert("请选择收件人");
		return;
	}
	if (confirm("确认要传阅？")) {
		
		$("[name='chbox']").each(function(index) {
			if ($(this).is(':checked')) {
				var value=$(this).val();
				$("<input>",{type:"hidden",value:value,name:"dtMytaskMap[" + index + "].taskid"}).appendTo($("form#SendForm"));
			}
		});
		$("select#rightfields").find("option").each(function(index){
			var  receiverid=$.trim($(this).val().split("-")[0]);
			var  receiverName=$.trim($(this).text());
			$("<input>",{type:"hidden",value:receiverid,name:"dtMypassrecordMap[" + index + "].receiverid"}).appendTo($("form#SendForm"));
			$("<input>",{type:"hidden",value:receiverName,name:"dtMypassrecordMap[" + index + "].receiverName"}).appendTo($("form#SendForm"));
			
		});
		$("#SendForm")
					.attr("target", "hidden")
					.attr(
							"action",
							basePath
									+ "ea/taskmanage/ea_toCirculatedByTask.jspa?date="
									+ new Date());
		document.SendForm.submit.click();
		token = 2;
	}
}
//树形机构菜单
function parentMenu(companyID, vals) {// 1级
	result += "<ul id='browser' class='filetree'><li title='"
			+ vals[0].text
			+ "' id='"
			+ vals[0].id
			+ "' class='curor'><a><span class='folder' onclick='javascript:getPerson(\""
			+ companyID + "\",\"1\",\"company\")'>" + vals[0].text
			+ "</span></a><ul id='child'>";
	childMenu(companyID, vals);
	result += "</ul></li></ul>";
	$(result).appendTo("#tree1");
	$("#browser").treeview();
	result = "";
}

function childMenu(companyID, vals) {// 2级
	for (var j = 0; j < vals.length; j++) {
		if (vals[j].pid == companyID && vals[j].str == "00") {
			result += "<li title='" + vals[j].text + "'><a><span id='"
					+ vals[j].id
					+ "' class='folder curor' onclick='javascript:getPersonMany(\""
					+ companyID + "\",\"" + vals[j].id + "\",\"org\")'>"
					+ vals[j].text + "</span></a></li>";
		}
	}
}

function getPersonMany(company, org, searchType) {
	var url = basePath + "ea/smeeting/sajax_ea_getAllStaff.jspa?date="
			+ new Date();
	$.ajax({
				url : encodeURI(url),
				type : "post",
				async : true,
				dataType : "json",
				data : {
					"companyID" : company,
					"orgID" : org,
					"searchType" : searchType
				},
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var persons = member.stafflist;
					var str = "";
					for (var i = 0; i < persons.length; i++) {
						var obj = persons[i];
						str += "<option value='" + obj.staffID + "-" + org
								+ "-" + "'>" + obj.staffName + "("
								+ obj.staffCode + ")</option>";
					}
					$("#leftfields").html(str);
				}
			});
}
//关闭选择人员
function closed() {
	$("#leftfields").html("");
	$("#rightfields").html("");
	$("#zj").jqmHide();
	location.reload(); 
}
/**---------------------------------------------------------选择多个人员---------------------------------------------**/
/**
 * 
 * 获得当前公司集团的所有公司
 */
function getAllCompanyOfGroup() {
	var url = basePath
			+ "ea/documentcommon/sajax_ea_getAllCompanyByCurrent.jspa?date="
			+ new Date().toLocaleString();
	$.ajax({
				url : encodeURI(url),
				type : "get",
				async : true,
				dataType : "json",
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var companylist = member.companylist;

					var str = "<option value=''>请选择公司</option>";
					for (var i = 0; i < companylist.length; i++) {
						var obj = companylist[i];
						str += "<option title='" + obj.companyName + "'value='"
								+ obj.companyID + "'>" + obj.companyName
								+ "</option>";
					}
					$("#SendForm select#receiverCompanyID").html(str);
				},
				error : function cbf(data) {
					alert("数据获取失败！")
				}
			});
}

/**
 * 
 * 根据公司获得部门
 * 
 * @param {}
 *            val
 */
function bmdept(val) {
	
	$("option", $("#receiverDeptID")).remove();

	var url = basePath
			+ "ea/documentcommon/sajax_ea_getAllOrganizations.jspa?date="
			+ new Date().toLocaleString();
	$.ajax({
				url : encodeURI(url),
				type : "post",
				async : true,
				dataType : "json",
				data : {
					"companyID" : val
				},
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var orgaizationlist = member.orgaizationlist;
					var str = "<option value=''>请选择部门</option>";
					for (var i = 0; i < orgaizationlist.length; i++) {
						var obj = orgaizationlist[i];
						str += "<option value='" + obj.organizationID + "'>"
								+ obj.organizationName + "</option>";
					}
					$("#SendForm #receiverDeptID").html(str);

				},
				error : function cbf(data) {
					alert("数据获取失败！")
				}
			});
}

/**
 * 
 * 根据部门获得人员
 * 
 * @param {}
 *            company
 * @param {}
 *            org
 */
function getPerson(company, org) {

	$("option", $("select#receiverID")).remove();

	var url = basePath
			+ "ea/documentcommon/sajax_ea_getPersonByDept.jspa?date="
			+ new Date();
	$.ajax({
				url : encodeURI(url),
				type : "post",
				async : false,
				dataType : "json",
				data : {
					"currentCompanyID" : company,
					"checkOrgID" : org
				},
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var persons = member.stafflist;
					var str = "<option value=''>请选择收件人</option>";
					for (var i = 0; i < persons.length; i++) {
						var obj = persons[i];
						str += "<option value='" + obj.staffID + "'>"
								+ obj.staffName + "(" + obj.staffCode
								+ ")</option>";
					}
					$("#SendForm #receiverID").html(str);
				}
			});
}
/**
 * 
 * 当公司改变时，获取部门
 * 
 * @param {}
 *            obj
 */
function changeCompany(obj) {
	if ($(obj).val() != '') {
		bmdept($(obj).val());
	} else {
		$("#receiverDeptID").html("<option value=''>请选择部门</option>");
	}

}

/**
 * 
 * 当部门改变时，获取员工
 * 
 * @param {}
 *            obj
 */
function changeDept(obj) {
	var dept = $("#SendForm #receiverDeptID").val();
	if (dept != "") {
		getPerson($("#SendForm #receiverCompanyID").val(), dept);
	} else {
		$("#SendForm #receiverID").html("<option value=''>请选择人员</option>");
	}
}
function re_load() {
	if(pageNumber==1){
		pageNumber=0;   //找不到问题 暂时之使用这个方式  原因 不做任何操作 pageNumber 自动 变成  1
	}
	if (token)
		document.location.href = basePath
				+ "ea/taskmanage/ea_getListByTaskManageProduction.jspa?pageNumber="
				+ pageNumber +"&myproject.proid="+proid;
}