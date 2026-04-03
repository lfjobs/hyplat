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
		title : '个人待办',
		minheight : 80,
		buttons : [ {
			name : '指派',
			bclass : 'edit',
			onpress : action
		// 当点击调用方法
		}, {
			separator : true
		}, {
			name : '提交完成事项',
			bclass : 'edit',
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
			name : '打印',
			bclass : 'printer',
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
			name : '查看历史单据',
			bclass : 'edit',
			onpress : action
		// 当点击调用方法
		}, {
			separator : true
		} ]
	});
	function action(com, grid) {
		switch (com) {
			case '指派':
				str = "";
				var stra="";
				$("[name='chbox']").each(function() {
					if ($(this).is(':checked')) {
						str += $(this).val() + ",";
						doid = $(this).val();
						if($("#complystatuss",$("tr#"+doid)).val()=="01"){
						}else{
							stra=$("#complystatuss",$("tr#"+doid)).val();
						}
					}
				});
				if (str == "" || str.length == 0) {
					alert('请选择');
					return
				}
				if(stra!=""){
					alert('选中的待办事项已完成或已指派！');
					return;
				}
				$("#zj").jqmShow();
				break;
			case '提交完成事项':
				str = "";
				var stra="";
				$("[name='chbox']").each(function() {
					if ($(this).is(':checked')) {
						str += $(this).val() + ",";
						doid = $(this).val();
						if($("#complystatuss",$("tr#"+doid)).val()=="01"){
						}else{
							stra=$("#complystatuss",$("tr#"+doid)).val();
						}
					}
				});
				if (str == "" || str.length == 0) {
					alert('请选择');
					return
				}
				if(stra!=""){
					alert('选中的待办事项已完成或已指派！');
					return;
				}
				$("#ToDoForm").attr("target", "hidden")
			    .attr("action",
						basePath+"/ea/personaltodo/ea_accomplish.jspa?doid="+str);
				document.ToDoForm.submit.click();
				token = 2;
				break;
			case '查看':
				str = "";
				$("[name='chbox']").each(function() {
					if ($(this).is(':checked')) {
						str += $(this).val() + ",";
						doid = $(this).val();
					}
				});
				if (str == "" || str.length == 0||str.split(",").length>2) {
					alert('请选择单个任务');
					return
				}
				var url=$("#"+str).find("#lookOverurl").val();
				document.location.href = basePath+url;
				break;
			case '查询':
				$("#jqModelsubmit").jqmShow();
				break;
			case '导出':
				var url = basePath + "/ea/personaltodo/ea_toExportExcelByDo.jspa?search=" + search
							+ "&sdate=" + sdate + "&edate=" + edate;
				open(url);
				break;
			case '打印':
				str = "";
				$("[name='chbox']").each(function() {
					if ($(this).is(':checked')) {
						str += $(this).val() + ",";
						doid = $(this).val();
					}
				});
				if (str == "" || str.length == 0||str.split(",").length>2) {
					alert('请选择单个任务');
					return;
				}
				var url=$("#"+str).find("#printurl").val();
				document.location.href = basePath+url;
				break;
			case '设置每页显示条数':
				var url = basePath
						+ "/ea/personaltodo/ea_getDtMydoList.jspa?search=" + search
							+ "&sdate=" + sdate + "&edate=" + edate;
				numback(url);
				break;
			case '查看历史单据':
				document.location.href = basePath
					+ "/ea/personaltodo/ea_getDtMydoList.jspa?history=history";
				break;
		}
	}
	
	$("input.JQueryreturn").click(function() {// 取消
		$("#jqModel").jqmHide();
		re_load();
	});
	$(".close").click(function() {// 取消
		$("#jqModel").jqmHide();
		re_load();

	});

	$(".chx").click(function() {
		var b = $(this).attr("checked");
		$(this).attr("checked", !b);
	});

	$(".JQueryflexme tr[id]").click(function() {
		var d = $("input.chx", $(this)).attr("checked");
		$("input.chx", $(this)).attr("checked", !d);
	});
	
	
	$("#JQuerySubmit").click(function() {
		$("#cstaffForm").attr(
				"action",
				basePath+"/ea/personaltodo/ea_toSearchByDtMydo.jspa?pageNumber="
						+ ppageNumber);
		document.cstaffForm.submit.click();
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
				for (i = right_vo.options.length - 1; i >= 0; i--){
					if (right_vo.options[i].selected) {
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


// 选择人员确定
function submit() {
	var lengths = document.getElementById("rightfields").options.length;// 下拉项的长度
	var strid = "";
	var strname = "";
	var flag = true;
	for (var i = 0; i < lengths; i++) {
		flag = true;
		for (var j = i + 1; j < lengths; j++) {
			var stri = document.getElementById("rightfields").options[i].value;
			var strj = document.getElementById("rightfields").options[j].value;
			if (stri == strj) {
				flag = false;
				break;
			}
		}
		if (flag == true) {
			strid += document.getElementById("rightfields").options[i].value;
			strid += ",";
			strname += document.getElementById("rightfields").options[i].text;
			strname += ";";

		}
	}
    // 判断是否选责
    if(strid==""){
    	alert("负责人必选!");
    	return;
	}
    $("#ToDoForm").attr("target", "hidden")
    .attr("action",
			basePath+"/ea/personaltodo/ea_appoint.jspa?doid="+str+"&staffid="+strid+"&staffname="+strname);
	document.ToDoForm.submit.click();
	token = 2;
    $("#zj").jqmHide();
}
// 树形机构菜单
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
					+ "' class='folder curor' onclick='javascript:getPerson(\""
					+ companyID + "\",\"" + vals[j].id + "\",\"org\")'>"
					+ vals[j].text + "</span></a></li>";
		}
	}
}

function getPerson(company, org, searchType) {
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
				// obj.staffCode人员编号
				for (var i = 0; i < persons.length; i++) {
					var obj = persons[i];
					str += "<option value='" + obj.staffID + "-" + org
							+ "-" + "'>" + obj.staffName + "</option>";
				}
				$("#leftfields").html(str);
			}
	});
}
// 关闭选择人员
function closed() {
	$("#leftfields").html("");
	$("#rightfields").html("");
	$("#zj").jqmHide();

}


function re_load() {
	if (token){
		document.location.href = basePath
				+ "/ea/personaltodo/ea_getDtMydoList.jspa?pageNumber="
				+ ppageNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value")+"&search="+search+"&sdate="+sdate+"&edate="+edate;
	};
};