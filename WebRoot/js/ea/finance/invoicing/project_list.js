$(function() {

	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
	// 遮罩程度%
	}).jqmAddClose('.close');// 添加触发关闭的selector


   
	var query = "<form method='post' name='searchf' id='searchf'>" +
			"<input type='submit' style='display:none;' name='submit' />" +
			"<input type='hidden' name='search' value='search'/>" +
			"<table border='0'>" +
			"<tr>" +
			"<td><strong>"+(type=="xm"?'项目管理':(type=="wxm"?'未审核项目':'已审核项目'))+"</strong>&nbsp;&nbsp;&nbsp;&nbsp;项目名称</td><td><input type='text' name='projectManage.projectName' size='13'/></td>" +
			"<td>主项目</td><td><input type='text' class='xmtype' name='projectManage.xmtype' size='13'/>" +
			"</td><td>负责人</td><td><input type='text' name='projectManage.staffName' size='10'/></td>" +
			"<td>创建人</td><td><input type='text' name='projectManage.createName' size='10'size='10'/></td>" +
			"<td>更新时间</td><td><input type='text' onfocus='date(this);' size='13' name='projectManage.startDate'/>至<input type='text' onfocus='date(this);' size='13' name='projectManage.endDate'/></td>" +
			"<td><input type='button' value=' 查询 ' class='input-button' style='margin:2px;'id='tosearch'/></td>" +
			"</tr></table></form>";

	$('.flexme11').flexigrid({
		allDouble : false,
		height : '280',
		width : 'auto',
		minwidth : 30,
		title : query,
		minheight : 80,
		buttons : type=="xm"?([ {
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
		},{
			name : '查看',
			bclass : 'see',
			onpress : action
		// 当点击调用方法
		},  {
			separator : true
		}, {
			name : '删除',
			bclass : 'delete',
			onpress : action
		// 当点击调用方法
		}, {
			name : '提交审核',
			bclass : 'examine',
			onpress : action
		// 当点击调用方法
		},{
			separator : true
		}, {
			name : '导出',
			bclass : 'excel',
			onpress : action
		// 当点击调用方法
		}, {
			separator : true
		}, {
			name : '招标打印',
			bclass : 'mysearch',
			onpress : action
		// 当点击调用方法
		}, {
			separator : true
		},{
			name : '打印',
			bclass : 'printer',
			onpress : action
		// 当点击调用方法
		},  {
			separator : true
		}, {
			name : '项目合同',
			bclass : 'excel',
			onpress : action
		// 当点击调用方法
		}, {
			separator : true
		}, {
			name : '项目规划',
			bclass : 'excel',
			onpress : action
		// 当点击调用方法
		}, {
			separator : true
		}, {
			name : '项目任务下发',
			bclass : 'excel',
			onpress : action
		// 当点击调用方法
		}, {
			separator : true
		},  {
			name : '设置每页显示条数',
			bclass : 'mysearch',
			onpress : action
		// 当点击调用方法
		}, {
			separator : true
		} ]):type=="wxm"?([ {
			name : '查看',
			bclass : 'see',
			onpress : action
		// 当点击调用方法
		}, {
			separator : true
		}, {
			name : '通过',
			bclass : 'examine',
			onpress : action
		// 当点击调用方法
		},{
			separator : true
		}, {
			name : '驳回',
			bclass : 'reject',
			onpress : action
		// 当点击调用方法
		},{
			separator : true
		}, {
			name : '导出',
			bclass : 'excel',
			onpress : action
		// 当点击调用方法
		}, {
			separator : true
		},{
			name : '打印',
			bclass : 'printer',
			onpress : action
		// 当点击调用方法
		},  {
			separator : true
		},  {
			name : '设置每页显示条数',
			bclass : 'mysearch',
			onpress : action
		// 当点击调用方法
		}, {
			separator : true
		} ]):([ {
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
		},{
			name : '打印',
			bclass : 'printer',
			onpress : action
		// 当点击调用方法
		},  {
			separator : true
		},  {
			name : '设置每页显示条数',
			bclass : 'mysearch',
			onpress : action
		// 当点击调用方法
		}, {
			separator : true
		} ])
	});

	function action(com, grid) {
		switch (com) {
		case '添加':

			window.open(basePath
					+ "/ea/promanage/ea_getAddPage.jspa?view=add");	
			break;
		
		case '修改':
			
			if (proID == "") {
				alert("请选择！");
				return;
			}

			window.open(basePath
			+ "/ea/promanage/ea_getAddPage.jspa?projectManage.proID="+proID+"&view=update");	
        break;
      case '查看':
			
			if (proID == "") {
				alert("请选择！");
				return;
			}

			window.open(basePath
			+ "/ea/promanage/ea_getAddPage.jspa?projectManage.proID="+proID+"&type="+type+"&view=view");	
			
			
			break;
		case '删除':
			if(proID==""){
				alert("请选择");
				return;
			}
			if (confirm("确定删除？")) {
				var url = basePath
						+ "ea/promanage/sajax_ea_deleteProject.jspa?date=" + new Date().toLocaleString();
				$.ajax({
					url : encodeURI(url),
					type : "get",
					async : true,
					dataType : "json",
					data:{
						"projectManage.proID":proID
						
					},
					success : function cbf(data) {
						var member = eval("(" + data + ")");
						var result = member.result;
						if(result=="success"){
							alert("操作成功！");
							$("tr#" + proID).remove();
						}else{
							alert("无权限删除");
						}
						
						
						
					},
					error : function cbf(data) {
						alert("数据获取失败3！");
					}
				});
			}
			break;
			
           case '提交审核':
			
			if (proID == "") {
				alert("请选择！");
				return;
			}
			
			
			if(confirm("确定提交审核?")){
				$("#formproid").val(proID);
				$("#postform").attr("target","hidden").attr("action",basePath+"/ea/promanage/ea_submitAudit.jspa");
			    document.postform.submit.click();
				token = 2;
			}
           break;
           
           case '通过':
   			
   			if (proID == "") {
   				alert("请选择！");
   				return;
   			}
   			if(confirm("确定通过审核?")){
   				$("#formproid").val(proID);
   				$("#audittype").val("02");
   				$("#postform").attr("target","hidden").attr("action",basePath+"/ea/promanage/ea_auditProject.jspa");
   			    document.postform.submit.click();
   				token = 2;
   			}
              break;
           case '驳回':
   			
   			if (proID == "") {
   				alert("请选择！");
   				return;
   			}
   			if(confirm("确定驳回审核?")){
   				$("#formproid").val(proID);
   				$("#audittype").val("03");
   				$("#postform").attr("target","hidden").attr("action",basePath+"/ea/promanage/ea_auditProject.jspa");
   			    document.postform.submit.click();
   				token = 2;
   			}
              break;

		case '打印':
			window.open(basePath
					+ "/ea/promanage/ea_printList.jspa");
			break;
		
		case '招标打印':
			if (proID== "") {
				alert("请选择！");
				return;
			}
			
			open(basePath
					+ "/ea/costsheet/ea_projectPrint.jspa?projectManage.proID="
					+ proID);

			break;
		case '导出':
			var url = basePath + "ea/promanage/ea_exportExcel.jspa?search=" + search+"&type="+type;
			open(url);
			break;
			
		case '设置每页显示条数':
			var url = basePath
					+ "/ea/promanage/ea_getProjectList.jspa?search=" + search+"&type="+type;
			numback(url);
			break;
		

		case '项目合同':
			if (proID == "") {
				alert("请选择！");
				return;
			}
			var projectCode = $("span#projectCode", $("tr#" + proID)).text();
			var projectName = $("span#projectName", $("tr#" + proID)).text();
			document.location.href = basePath
					+ "/ea/promanage/ea_getContractByProject.jspa?projectManage.projectCode="
					+ projectCode + "&type=contract&projectName=" + projectName;
			break;

		case '项目规划':
			if (proID == "") {
				alert("请选择！");
				return;
			}
			var projectCode = $("span#projectCode", $("tr#" + proID)).text();
			document.location.href = basePath
					+ "/ea/promanage/ea_getContractByProject.jspa?projectManage.projectCode="
					+ projectCode + "&type=plan";
			break;
		case '项目任务下发':
			if (proID == "") {
			alert("请选择！");
			return;
		}
		$("#csbID").val(proID);
		$("#projectnames").val(
				$("tr#" + proID).find("span#projectName").text());
		$("#rightfields").empty();
		$("#leftfields").empty();
		$("#planjqModelsend").jqmShow();
			break;
	}
	}

	// 这一行的单击事件
	$(".flexme11 tr[id]").click(function() {

		opertionID = this.id;
		proID = this.id;
		$("input.JQuerypersonvalue", $(this)).attr("checked", "checked");

	

	});
	// 这一行的单击事件
	$(".flexme11 tr[id]").dblclick(function() {
			action("修改");

	

	});
	// 单击事件
	$("tr[id].uremove").live("click", function(event) {
		proID = this.id;
		$("input.subproject", $(this)).attr("checked", "checked");

	

	});
	
	
	/**
	 * 
	 * 键入时查询项目分类
	 */
	$(".xmtype").focus(function(){
		getxmtype($(this));
	}).keyup(function(){
	
		getxmtype($(this));

		
	});
	
	
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

	$("#refress").click(function() {
		refress();

	});
	$("#query_add").click(function() {
		$("#leftfields").dblclick();
	});

	$("#query_delete").click(function() {
		$("#rightfields").dblclick();
	});
	$("#leftfields")
	.dblclick(
			function() {
				var left_vo, right_vo, vos, i;
				vos = document
						.getElementsByName('leftfields');

				if (vos == null)
					return false;
				left_vo = vos[0];
				vos = document
						.getElementsByName('rightfields');
				if (vos == null)
					return false;
				right_vo = vos[0];
				for (i = 0; i < left_vo.options.length; i++) {
					if (left_vo.options[i].selected) {
						var no = new Option();
						no.value = left_vo.options[i].value;
						no.text = left_vo.options[i].text;
						//$("#siaffID").val(no.value);
						
						
						right_vo.options[right_vo.options.length] = no;
					}
				}

				// 设为要可选状态

				for (i = 0; i < right_vo.options.length; i++) {
					right_vo.options[i].selected = true;
				}

				return true;
			});
	
	   //查询按钮单击事件
    $("#tosearch").click(function(){
       $("#searchf").attr("action", basePath+"/ea/promanage/ea_toSearch.jspa");
       document.searchf.submit.click();
   });
	



});



function selectz(obj){
	$("#jqModel").hide();
	var codeValues=$(obj).text();
	$(".xmtype").val(codeValues.substring(codeValues.indexOf(")")+1));
	
}

//键入时查询项目分类

function getxmtype(obj){
	
	var left = $(obj).position().left;
	var top = $(obj).position().top;
		var url = basePath
				+ "ea/promanage/sajax_ea_getXmtypeByCode.jspa?date="
				+ new Date().toLocaleString();
		$.ajax({
			url : encodeURI(url),
			type : "post",
			async : false,
			dataType : "json",
			data : {
				parameter : $.trim($(obj).val())
			},
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				var xmList = member.xmlist;
				
				var str="";

				for (var i = 0; i < xmList.length; i++) {
					if(xmList[i].isLeaf =="00"){
						str+="&nbsp;&nbsp;<span><a href='#' onclick='selectz(this);'>("+xmList[i].codeSn+")"+xmList[i].codeValue+"</a></span><br/>";
					}else{
					str+="&nbsp;&nbsp;<span>("+xmList[i].codeSn+")"+xmList[i].codeValue+"</span><br/>";
					}
				}
				if(str==""){
					str="&nbsp;无搜索结果";
				}
				
				$("#treeBoxs").html(str);

					
				    $("#jqModel").css({
						position : "absolute",
						left : left,
						top : top+23
					}).show();
				    

			},
			error : function cbf(data) {
				alert("数据获取失败4！");
			}
		});

}


$(document).ready(
		function() {

			// 加载当前人员所在的集团下所有公司
			var url = basePath
					+ "ea/costsheet/sajax_ea_seachgroupsync.jspa?";
			$
					.ajax({
						url : encodeURI(url),
						type : "get",
						async : false,
						dataType : "json",
						success : function cbf(data) {
							var member = eval("(" + data + ")");
							var oList = member.company;
							;
							var data = new Array();
							var result = "<ul id='browser' class='filetree'>";
							for ( var i = 0; i < oList.length; i++) {
								data[i] = {
									id : oList[i].companyID,
									text : oList[i].companyName
								};
								result += "<ul><li onclick='javascript:test1(\""
										+ data[i].id
										+ "\")' title='"
										+ data[i].text
										+ "' class='curor expandable closed'><span class='folder'>"
										+ data[i].text
										+ "</span><ul id='"
										+ data[i].id + "'>";
								result += "</ul></li></ul>";

							}
							result += "</ul>";
							$(result).appendTo("#grouptree");
							$("#browser").treeview();
						},
						error : function cbf(data) {
							alert("数据获取失败2！");
						}

					});


		$("#tosave")
					.click(
							function() {
								var coun = $("#rightfields option")
										.val();
								if (coun == null) {
									alert("下发人员未选择！请重试！");
									return;
								} else {
									var lengths = document.getElementById("rightfields").options.length;// 下拉项的长度
									var strid = "";
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
											

										}
									}
									$("#siaffID").val(strid);
									
									$("#planjqModelsend").jqmHide();
									
									
									$("#projectplanbudgetForm").attr("target","hidden")
											.attr(
													"action",
													basePath
															+ "ea/budget/ea_saveprojectPlanBudget.jspa?");
									document.projectplanbudgetForm.submit
											.click();
									token = 2;
									alert("操作成功");
							
								}
							});
				
			

		});

function test1(val) {
	// $("#companyID").val(val);
	$("#" + val).empty();
	var url = basePath + "ea/costsheet/sajax_ea_seachgroupsync.jspa?date="
			+ new Date().toLocaleString() + "&companyID=" + val;
	$.ajax({
		url : encodeURI(url),
		type : "post",
		async : false,
		dataType : "json",
		success : function cbf(data) {
			var member = eval("(" + data + ")");
			var departmentList = member.departmentList;

			var str1 = "";
			for ( var i = 0; i < departmentList.length; i++) {
				var obj = departmentList[i];
				var orgid = obj.organizationID;
				str1 += "<li><span class='file' onclick='searchRenyuan(\""
						+ val + "\",\"" + orgid + "\");'>"
						+ obj.organizationName + "</span></li>";
			}
			/* $("#SendForm #receiverDeptID").html(str); */
			$("#" + val).append(str1);

		},
		error : function cbf(data) {
			alert("数据获取失败1！");
		}
	});
}
//根据公司部门查询当前所选部门的员工

function searchRenyuan(companyid, org) {
	$("#leftfields").empty();
	var url = basePath
			+ "ea/cashiersummary/sajax_ea_getStaffListfordep.jspa?date111="
			+ new Date();
	$.ajax({
		url : encodeURI(url),
		type : "get",
		async : true,
		dataType : "json",
		data:{
			currentCompanyID:companyid,
			currentOrgnizationID:org
		},
		success : function cbf(data) {
			var member = eval("(" + data + ")");
			var depstaff = member.list;
			var str = "";
			for ( var i = 0; i < depstaff.length; i++) {
				var obj2 = depstaff[i];
				str += "<option id= '" + obj2.staffID 
						+ "'  value='"+companyid+"-"+org+"-"+obj2.staffID+"'>" + obj2.staffName
						+ obj2.staffCode + "</option>";
			}
			
			$("#leftfields").html(str);

		}
	});
}


function re_load() {
	if (token) {
		document.location.href = basePath
				+ "/ea/promanage/ea_getProjectList.jspa?type="+type;
	}
}