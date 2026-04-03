$(function() {
	$("#jqModelDimission").jqm({
		modal : true,// 离职员工
		overlay : 20
			//  
		}).jqmAddClose('.close');// 添加触发关闭的selector
	$("#jqModelWork").jqm({
		modal : true,// 工作变更
		overlay : 20
			//  
		}).jqmAddClose('.close');// 添加触发关闭的selector
	$("#jqModelSearch").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	$("#jqModel").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	$("#newdistrict").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	$("#jqModelAppraisal").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	$("#newstaffType").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	/*$('.JQueryflexmepost').flexigrid({
				height : 180,
				width : 'auto',
				minwidth : 30,
				title : '历史岗位查询----当前人员 ' + staffName,
				minheight : 80,
	});*/
	if(aa=="aa"){
	$('.JQueryflexme').flexigrid({
		        allDouble:true,
				height : 180,
				width : 'auto',
				minwidth : 30,
				title : '正式员工信息汇总',
				minheight : 80,
				buttons : [{
					name : '离职',
					bclass : 'delete',
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
					name :'查看',
					bclass :'see',
					onpress :action
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
					name : '工种分配',
					bclass : 'excel',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},{
					name : '工作日志',
					bclass : 'excel',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}
//				,{
//					name : '工作计划',
//					bclass : 'excel',
//					onpress : action
//						// 当点击调用方法
//					}, {
//					separator : true
//				}
				, {
					name : '月综合考评',
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
				}
//					, {
//					name : '变更',
//					bclass : 'edit',
//					onpress : action
//						// 当点击调用方法
//					}, {
//					separator : true
//				}
					]
			});
			}else if(aa=="bb"){
			$('.JQueryflexme').flexigrid({
				allDouble:true,
				height : 180,
				width : 'auto',
				minwidth : 30,
				title : '正式员工信息汇总',
				minheight : 80,
				buttons : [{
					name : '工作日志',
					bclass : 'excel',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '查看',
					bclass : 'mysearch',
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
			
			}else if(aa=="cc"){
				$('.JQueryflexme').flexigrid({
				height : 180,
				width : 'auto',
				minwidth : 30,
				title : '正式员工信息汇总',
				minheight : 80,
				buttons : [ {
					name : '工种分配',
					bclass : 'excel',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '查看',
					bclass : 'mysearch',
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
			}else if(aa=="dd"){
				$('.JQueryflexme').flexigrid({
				height : 180,
				width : 'auto',
				minwidth : 30,
				title : '正式员工信息汇总',
				minheight : 80,
				buttons : [{
					name : '月综合考评',
					bclass : 'excel',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},  {
					name : '查看',
					bclass : 'mysearch',
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
			}else if(aa=="ee"){
				$('.JQueryflexme').flexigrid({
				height : 180,
				width : 'auto',
				minwidth : 30,
				title : '正式员工信息汇总',
				minheight : 80,
				buttons : [ {
					name : '修改',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},{
					name : '查看',
					bclass : 'mysearch',
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
			}else{
			$('.JQueryflexme').flexigrid({
				height : 180,
				width : 'auto',
				minwidth : 30,
				title : '正式员工信息汇总',
				minheight : 80,
				buttons : [ {
					name : '离职',
					bclass : 'delete',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},{
					name : '查看',
					bclass : 'mysearch',
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
			}
	function action(com, grid) {
		switch (com) {
			case '工作日志' :
				// 工作日志列表
				if (personvalue == "") {
					alert("请选择具体人员！");
					return;
				}
				personurl = basePath
						+ "ea/logbook/ea_getListLogBook.jspa?pageNumber="
						+ ppageNumber + "&logbook.staffID=";
				$("#mainframe").css("height","auto")
						.attr(
								"src",
								basePath
										+ "ea/logbook/ea_getListLogBook.jspa?pageNumber="
										+ ppageNumber + "&logbook.staffID="
										+ personvalue);
				$(window).resize();
				break;
			case '修改' :
				if (personvalue == "") {
					alert('请选择人员');
					return
				}
				var url = basePath
						+ "ea/humanResource/ea_getHumanResource.jspa?showType=edit&cstaff.staffID="
						+ personvalue;
				window
						.open(url, '',
								'scrollbars=yes,resizable=yes,channelmode');
				break;
			case '离职' :
				if (personvalue == "") {
					alert('请选择人员');
					return
				}
				$("#jqModelDimission").jqmShow();
				break;
			case '变更' :
				if (personvalue == "") {
					alert('请选择人员');
					return
				}
				
				var url = basePath
				+ "ea/cstaff/sajax_n_ea_setOrgByStaff.jspa?districtPID=0"
				+ "&date1=" + new Date();
				$.ajax({
					url : url,
					type : "get",
					async : true,
					dataType : "json",
					success : function cbf(data) {
						var member = eval("(" + data + ")");
						var distinctlist = member.distinctlist;
						for (var i = 0; i < distinctlist.length; i++) {
							$op = $("<option />");
							$op.attr("value", distinctlist[i].districtID)
									.attr("id", distinctlist[i].districtID)
									.text(distinctlist[i].districtName);
							$("#province", $td).append($op);
						}
						retoken = 0;
					},
					error : function cbf(data) {
						retoken = 0;
						alert("数据获取失败！");
					}
				});
				
				var n = $(".JQueryflexme").find("tr#"+personvalue).find("span#staffName").text();
				var t = $(".JQueryflexme").find("tr#"+personvalue).find("span#categoryname").text();
				
				$("#cataffWorkTable").find("span#staffName").text(n);
				$("#cataffWorkTable").find("span#oldcategoryname").text(t);
				$("#cataffWorkTable").find("span#staffID").text(personvalue);
				$("#cataffWorkTable").find("input[type=radio]").each(function(){
					if($(this).val()==t){
						$(this).attr("checked","checked");
					}
				});

				$("#jqModelWork").jqmShow();
				break;
			case '工种分配' :
				if (personvalue == "") {
					alert('请选择人员');
					return
				}
				var url = basePath
						+ "page/ea/main/human/office/production/staff_post.jsp?pageNumber="
						+ ppageNumber + "&staffName=" + staffName + "&staffID="
						+ personvalue + "&pagetype=01 &pnum=" + pnum
						+ "&companyName=" + companyName+"&aa="+aa;
				url = encodeURI(url);
				document.location.href = url;
				break;
		/*	case '历史岗位查询' :
				if (personvalue == "") {
					alert('请选择人员')
					return
				}
				personurl = encodeURI(basePath
						+ "ea/soincumbent/ea_historyOfPost.jspa?pageNumber="
						+ ppageNumber + "&searchCStaff.staffName=" + staffName + "&staffID=");
				$("#mainframe")
						.attr(
								"src",
								encodeURI(basePath
										+ "ea/soincumbent/ea_historyOfPost.jspa?pageNumber="
										+ ppageNumber + "&searchCStaff.staffName=" + staffName + "&staffID="
										+ personvalue));
				break;*/
			case '月综合考评' :
				// 工作日志列表
				if (personvalue == "") {
					alert("请选择具体人员！");
					return;
				}
				personurl = basePath
						+ "ea/staffappraisal/ea_getListStaffAppraisal.jspa?pageNumber="
						+ ppageNumber + "&staffappraisal.staffID=";
				$("#mainframe").css("height","auto")
						.attr(
								"src",
								basePath
										+ "ea/staffappraisal/ea_getListStaffAppraisal.jspa?pageNumber="
										+ ppageNumber
										+ "&staffappraisal.staffID="
										+ personvalue);
				$(window).resize();
				break;
				
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
			case '导出' :
				url = basePath
						+ "ea/soincumbent/ea_showStaffExcelForIncumbent.jspa?search="
						+ psearch +"&searchValue=searchValue";
				open(url);
				break;
			case '查看' :
				if (personvalue == "") {
					alert("请选择具体人员！");
					return;
				}
				var url = basePath
						+ "ea/soincumbent/ea_getBasicInformation.jspa?staffID="
						+ personvalue;
				window.open(url);
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/soincumbent/ea_getStaffListForIncumbent.jspa?search="
						+ psearch + "&searchValue=searchValue&aa="+aa;
				numback(url);
				break;	
		}
	}
	$("#singleShuterphoto").click(function() {
		$("table#stafftable").find('img#photo').hide();
		$("table#stafftable").find('#singleShuter').show();
	});
	
	$(".JQueryflexme tr[id]").dblclick(function() {
		action('查看');// 当双击时出发 action方法.等价于先选中再点击修改按钮
	});

	$("input.JQueryreturn1").click(function() {// 城市添加取消
		retoken = 0;
		$("#newdistrict").jqmHide();
		$("#jqModel").jqmShow();
	});
	
	$(".JQueryflexme tr[id]").click(function() {
		personvalue = this.id;
		if (personurl) {
			$("#mainframe").attr("src", personurl + personvalue);
		}
		staffName = $(this).find("span#staffName").text();
		$("input.JQuerypersonvalue", $(this))
				.attr("checked", "checked");
	});
	
	$("input.JQ").click(function() {// 保存
		$(".put3").trigger("blur");
		if ($("form .error").length) {
			return;
		}
		$("#cstaffDForm")
			.attr(
					"action",
					basePath
							+ "ea/soincumbent/ea_delStaffListForIncumbent.jspa?pageNumber="
							+ ppageNumber + "&sub=" + session_val
							+ "&staffID=" + personvalue
							+ "&dimission=" + codimission);	
			
		document.cstaffDForm.submit.click();
	});

	$("input.JW").click(function() {// 保存
		$("#cstaffWForm")
			.attr(
					"action",
					basePath
							+ "ea/soincumbent/ea_saveWorkIncumbent.jspa?pageNumber="
							+ ppageNumber );	
			
		document.cstaffWForm.submit.click();
	});
/*	$("input.JQuerySubmit").click(function() {// 保存
				if (notoken)
					return;
				notoken = 1;
				$(".IdentityCard").trigger("blur");
				if ($("form .error").length) {
					return;
				}
				$t = $("table#stafftable");
				var addr = "";
				$(".JQueryaddress").find("select")
						.find("option[value]:selected").each(function() {
							if ($(this).text() != '--新增--'
									&& $(this).text() != '--请选择--')
								addr = addr + $(this).text();
						})
				$("#cstaffForm").find("input#staffAddress").val(addr);
				if ($("table#stafftable").find('#singleShuter').is(":visible")) {
					var f = null;
					if (document.singleShuter)
						f = document.singleShuter;
					else
						f = document.getElementById('singleShuter');
					f.SavePhoto(pbasePath + "js/photo/save2.jsp");
					token = 2;
				} else {
					$("#cstaffForm")
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/cstaff/t_ea_saveCStaff.jspa?pageNumber="
											+ ppageNumber);
					document.cstaffForm.submit.click();
					document.cstaffForm.reset();
					token = 2;
				}

			});*/
	$("input.JQueryreturn").click(function() {// 取消
					document.location.href = basePath
							+ "ea/soincumbent/ea_getStaffListForIncumbent.jspa?search="
							+ psearch + "&pageNumber=" + ppageNumber+"&aa="+aa;
				//$("#jqModel").jqmHide();
				//$("#jqModelSearch").jqmHide();
				//$("#jqMode2").jqmHide();
				
				document.cstaffDForm.reset();
				$("#jqModel").jqmHide();

			}); 
	// 查询相关操作
	$("#searchStaff").click(function() {
		$("#cstaffSearchForm").attr(
				"action",
				basePath + "ea/soincumbent/ea_toSearchCStaff.jspa?pageNumber="
						+ ppageNumber + "&searchValue=searchValue&aa="+aa);
		document.cstaffSearchForm.submit.click();
	});
	// 查询相关操作END

	// //////////////////////////////地址!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!==================================================BEGIN!
	var PID='';// 当点新曾时,上一级被选中项的id
	var rovince='';// 被改变的那个的id
	var districtPID='';
	function LiuZhongYaoDeShaGuaDiZhi(address) {
		// 非空验证还原
		if (retoken)
			return;
		retoken = 1;
		$(".notnull").addClass("model3");
		$(".notnull").css("background-color", "#ffffff");
		$(".IdentityCard").css("background-color", "#ffffff");

		// 非空验证End
		$td = $("td.JQueryaddress");
		$td.children('select').empty();
		$select = "<option selected='selected'>--请选择--</option>";
		$("#province", $td).append($select);
		$td = $("td.JQueryaddress");
		var DistrictID = address;
		if (DistrictID == "") {
			var url = basePath
					+ "ea/cstaff/sajax_n_ea_getCDistricts.jspa?districtPID=0"
					+ "&date1=" + new Date();
			$.ajax({
						url : url,
						type : "get",
						async : true,
						dataType : "json",
						success : function cbf(data) {
							var member = eval("(" + data + ")");
							var distinctlist = member.distinctlist;
							for (var i = 0; i < distinctlist.length; i++) {
								$op = $("<option />");
								$op.attr("value", distinctlist[i].districtID)
										.attr("id", distinctlist[i].districtID)
										.text(distinctlist[i].districtName);
								$("#province", $td).append($op);
							}
							retoken = 0;
						},
						error : function cbf(data) {
							retoken = 0;
							alert("数据获取失败！");
						}
					});
			return;
		}
		var urldistrict = basePath
				+ "ea/cstaff/sajax_n_ea_getPDetailsDistricts.jspa?districtPID="
				+ DistrictID + "&date2=" + new Date();
		$.ajax({
			url : urldistrict,
			type : "get",
			async : true,
			dataType : "json",
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				var distinctlistSaved = member.distinctlistSaved;
				var list = member.list;
				$select = "<option selected='selected'>--请选择--</option>";
				retoken = 0;
				for (var i = 0; i < distinctlistSaved.length; i++) {
					if (i == 9) {
						return;
					}
					$td.children('select:eq(' + i + ')').empty();
					$td.children('select:eq(' + i + ')').append($select);
					for (var j = 0; j < list[i].length; j++) {
						$op = $("<option />");
						$op.attr("value", list[i][j].districtID).attr("id",
								list[i][j].districtID)
								.text(list[i][j].districtName);
						$td.children('select:eq(' + i + ')').append($op);
					}
					$opp = $("<option  selected='selected'/>");
					$opp.attr("value", distinctlistSaved[i].districtID).attr(
							"id", distinctlistSaved[i].districtID)
							.text(distinctlistSaved[i].districtName);
					$td.children('select:eq(' + i + ')').append($opp);
					$add = "<option class='add'  value = '"
							+ distinctlistSaved[i].districtPID
							+ "' >--新增--</option>";
					$td.children('select:eq(' + i + ')').append($add);
				}
				$td.children('select:eq(' + distinctlistSaved.length + ')')
						.append($select);
				for (var z = 0; z < list[distinctlistSaved.length].length; z++) {
					$op = $("<option />");
					$op
							.attr(
									"value",
									list[distinctlistSaved.length][z].districtID)
							.attr(
									"id",
									list[distinctlistSaved.length][z].districtID)
							.text(list[distinctlistSaved.length][z].districtName);
					$td.children('select:eq(' + distinctlistSaved.length + ')')
							.append($op);
				}
				$addd = "<option class='add'  value = '"
						+ distinctlistSaved[distinctlistSaved.length - 1].districtID
						+ "' >--新增--</option>";
				$td.children('select:eq(' + distinctlistSaved.length + ')')
						.append($addd);
			},
			error : function cbf(data) {
				retoken = 0;
				alert("数据获取失败！");
			}
		});
	}

	$('td.JQueryaddress select[number]').change(function() {
		if (retoken)
			return;
		retoken = 1;

		var province = this.id;
		var number = $(this).attr("number");
		$td = $("td.JQueryaddress");
		rovince = "#" + province;
		$('#newdistrict', $td).hide();
		$td.children('select:gt(' + number + ')').empty();
		$td.children('select:gt(' + number + ')').show();
		var D = $(rovince, $td).children("option:selected").attr("class");
		if (D == 'add') {
			PID = $(rovince, $td).children("option:selected").val();
			$('#districtNames').attr("title", number).attr("value", "");
			$("#jqModel").jqmHide();
			$("#newdistrict").jqmShow();
			retoken = 0;
			return;
		}
		$($td).children('select:gt(' + number + ')').attr("disabled", false);
		var districtPID = $(rovince, $td).children("option:selected").val();
		if (districtPID == '--请选择--') {
			retoken = 0;
			return;
		}
		var url = basePath
				+ "ea/cstaff/sajax_n_ea_getCDistricts.jspa?districtPID="
				+ districtPID + "&date3=" + new Date();
		$.ajax({
					url : encodeURI(url),
					type : "get",
					async : true,
					dataType : "json",
					success : function cbf(data) {
						var member = eval("(" + data + ")");
						var distinctlist = member.distinctlist;
						$select = "<option selected='selected'>--请选择--</option>";
						$(rovince, $td).next().append($select);
						if (distinctlist.length) {
							for (var i = 0; i < distinctlist.length; i++) {
								$op = $("<option/>");
								$op.attr("value", distinctlist[i].districtID)
										.attr("id", distinctlist[i].districtID)
										.text(distinctlist[i].districtName);
								$(rovince, $td).next().append($op);
							}
						}
						$add = "<option class='add'  value = '" + districtPID
								+ "' >--新增--</option>";
						$(rovince, $td).next().append($add);
						$td.find('input#address').val(districtPID);
						retoken = 0;
					},
					error : function cbf(data) {
						retoken = 0;
						alert("数据获取失败！");
					}
				});

	});

	$('input#savedistrict').click(function() {
		if (retoken)
			return;
		retoken = 1;
		$td = $("td.JQueryaddress");
		number = $('input#districtNames').attr('title');
		districtName = $('input#districtNames').val();
		$td.children('select:gt(' + number + ')').empty();
		if ('' == districtName) {
			alert("请填写城市名称");
			retoken = 0;
			return;
		}
		$("#newdistrict").jqmHide();
		$("#jqModel").jqmShow();
		var urldistrict = basePath
				+ "ea/cstaff/sajax_n_ea_saveDistrict.jspa?district.districtPID="
				+ PID + "&district.districtName=" + districtName + "&date4="
				+ new Date();
		 url = basePath + "ea/cstaff/sajax_n_ea_getCDistricts.jspa";
		$.ajax({
					url : encodeURI(urldistrict),
					type : "get",
					async : true,
					dataType : "json",
					success : function cbf(data) {
						var member = eval("(" + data + ")");
						var sdistrict = member.sdistrict;
						$op1 = $("<option selected='selected'/>").attr("value",
								sdistrict.districtID).attr("id",
								sdistrict.districtID)
								.text(sdistrict.districtName);
						$("#" + sdistrict.districtID, $td).remove();
						$(rovince, $td).append($op1);
						districtPID = sdistrict.districtID;
						//var params = {
						//	"districtPID" : districtPID
						//};

						$select = "<option selected='selected'>--请选择--</option>";
						$(rovince, $td).next().append($select);
						$add = "<option class='add'  value = '" + districtPID
								+ "' >--新增--</option>";
						$(rovince, $td).next().append($add);
						$td.find('input#address').val(districtPID);
						retoken = 0;
					},
					error : function cbf(data) {
						retoken = 0;
						alert("数据获取失败！");
					}
				});

	});
		// 保存新地址...............
		// //////////////////////////////地址!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!==================================================END!
});

$(function() {
			// 为弹出框准备下拉表内容
			var url = basePath + "ea/cstaff/sajax_n_ea_getSelectLists.jspa";
			var parames = null;
			$.post(url, parames, function(data) {
						var member = eval("(" + data + ")");
						var codeSexList = member.codeSexList;
						var codeNationalityList = member.codeNationalityList;
						var codeNationList = member.codeNationList;
						var codeNativePlaceList = member.codeNativePlaceList;

						$t = $("table#stafftable");

						$sex = $t.find("select#sex");// 性别拉框
						for (var i = 0; i < codeSexList.length; i++) {
							$op = $("<option/>");
							$op.val(codeSexList[i].codeValue)
									.text(codeSexList[i].codeValue);
							$sex.append($op);
						}

						$nationality = $t.find("select#nationality");// 国籍拉框
						for (var i = 0; i < codeNationalityList.length; i++) {
							$op = $("<option/>");
							$op.val(codeNationalityList[i].codeValue)
									.text(codeNationalityList[i].codeValue);
							$nationality.append($op);
						}

						$nation = $t.find("select#nation");// 民族拉框
						for (var i = 0; i < codeNationList.length; i++) {
							$op = $("<option/>");
							$op.val(codeNationList[i].codeValue)
									.text(codeNationList[i].codeValue);
							$nation.append($op);
						}

						$nativePlace = $t.find("select#nativePlace");// 籍贯拉框
						for (var i = 0; i < codeNativePlaceList.length; i++) {
							$op = $("<option/>");
							$op.val(codeNativePlaceList[i].codeValue)
									.text(codeNativePlaceList[i].codeValue);
							$nativePlace.append($op);
						}
					}, 'json');

		});
function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/soincumbent/ea_getStaffListForIncumbent.jspa?search="
				+ psearch + "&pageNumber=" + ppageNumber
				+ "&pageForm.pageNumber=" + $("#pageNumber").attr("value");
}
$(function() {
	$(".positionPay").blur(function() {
		if (isNaN($(this).attr("value"))) {
			alert("必须为数字！");
			$(this).attr("value", 0);
			return;
		}else{
			if($(this).val() <0 || $(this).val() >5){
				alert("得分不能小于0或大于5！");
				$(this).attr("value", 0);
				return;
			}
		}
		positionPaysum = 0;
		$(".positionPay").each(function() {
					positionPaysum = positionPaysum + $(this).attr("value") * 1;
				});
		addpositionPay(positionPaysum);
	});
	$(".achievement").blur(function() {
		if (isNaN($(this).attr("value"))) {
			alert("必须为数字！");
			$(this).attr("value", 0);
			return;
		}else{
			if($(this).val() <0 || $(this).val() >5){
				alert("得分不能小于0或大于5！");
				$(this).attr("value", 0);
				return;
			}
		}
		achievementsum = 0;
		$(".achievement").each(function() {
					achievementsum = achievementsum + $(this).attr("value") * 1;
				});
		addachievementsum(achievementsum);
	});
	$(":input[class != 'nocheck']", "#staffappr").blur(function() {
				if (isNaN($(this).attr("value"))) {
					alert("必须为数字！");
					$(this).attr("value", 0);
					return;
				}else{
					if($(this).val() <0 || $(this).val() >5){
						alert("得分不能小于0或大于5！");
						$(this).attr("value", 0);
						return;
					}
				}
				Allsum = 0;
				$(":input[class != 'nocheck']", "#staffappr").each(function() {
							Allsum = Allsum + $(this).attr("value") * 1;
						});
				addAllsum(Allsum);
			});
	$("#workDateSaturation").blur(function() {
				if (isNaN($(this).attr("value"))) {
					alert("必须为数字！");
					$(this).attr("value", 0);
					return;
				}else{
					if($(this).val() <0 || $(this).val() >1){
						alert("饱和度不能小于0或大于1！");
						$(this).attr("value", 1);
						return;
					}
				}
				workday = $("#workDateSaturation").attr("value");
				addpositionPay(positionPaysum);
				addAllsum(Allsum);
				addachievementsum(achievementsum);
				addworkday(workday);
			});

	//月考评提交
	$(".JQuerySubmitAppraisal").click(function() {
		var todate = $("input#appraisalDate").val();
		if(todate == ''){
			$("input#appraisalDate").focus();
			alert("考评时间不能为空！");
		}else{
			var tomanths = todate.substring(0,todate.lastIndexOf("-"));
			var url = basePath + "ea/logbook/sajax_n_ea_isLocked.jspa?logbook.staffID="+personvalue+"&tomanths="+tomanths;
			$.ajax({ //判断月考评是否被加锁
					url : encodeURI(url),
					type: "get",
					async: true,
					dataType: "json",
					success: function cbf(data){
						var member = eval("(" + data + ")");
						var islock = member.islock;
						if(islock != ''){
						 	alert("此人员"+islock+"的考评已被加锁,不可再添加！");
						 	return;
						}
						$(":input[class != 'nocheck']", "#staffappr").trigger("blur");
						if ($("form .error").length) {
							return;
						}
						var re = 0;
						var iskong=$("#checkPerson").val();
						if(iskong == ""){
							alert("参会考评人不能为空");
							$("#checkPerson").css("background-color", "red");
							re = 1;
						}
						if (re) {
							return;
						}
						// workday 工作日饱和度
						var stPay = Math.round($("#appraisalForm #stPay").text() * 100
								* workday / 20)
								/ 100;// 特殊人才得分
						var secrecyPay = Math.round($("#appraisalForm #secrecyPay").text()
								* 100 * workday / 20)
								/ 100;// 保密金额得分
						var safetyAward = Math.round($("#appraisalForm #safetyAward").text()
								* 100 * workday / 20)
								/ 100;// 安全金额得分
						var pushScore = $("#pushScore").text();// 得分：考评金额，所得金额后面的得分
						var timingMoneyScore = $("#timingMoneyScore").text();// 任务得分
						var positionPayScore = $("#positionPayScore").text();// 职责得分
						var awardPayScore = Math.round($("#appraisalForm #awardPay").text()
								* 100 * workday / 20)
								/ 100;// 奖励工资得分
						var  pietypayScore =  Math.round($("#appraisalForm #pietypay").text() * 100 * workday / 20) / 100;   //pietypay;	//孝道金
						var  campaignpayScore =  Math.round($("#appraisalForm #campaignpay").text() * 100 * workday / 20) / 100;	 //campaignpay;	//竞职金
						var  telecompayScore =  Math.round($("#appraisalForm #telecompay").text() * 100 * workday / 20) / 100;	 //telecompay ;	//通讯补助	
//						var  pkpayScore =  Math.round($("#appraisalForm #pkpay").text() * 100 * workday / 20) / 100;	 //pkpay;	//pk 金
						var  livingScore =  Math.round($("#appraisalForm #living").text() * 100 * workday / 20) / 100;	 //living; //生活补助
						
						var parmeter = awardPayScore + "-" + safetyAward + "-" + pushScore
								+ "-" + timingMoneyScore + "-" + positionPayScore + "-" + stPay
								+ "-" + secrecyPay+"-"+pietypayScore+"-"+campaignpayScore
								+"-"+telecompayScore
//								+"-"+pkpayScore
								+"-"+livingScore;

						$("#result").attr("value", parmeter);
						$("#appraisalForm").attr("target", "main");
						$("#appraisalForm")
								.attr(
										"action",
										basePath
												+ "ea/staffappraisal/ea_saveStaffAppraisal.jspa?pageNumber="
												+ ppageNumber + "&staffappraisal.staffID="
												+ personvalue);
						document.appraisalForm.submit.click();
						alert("提交成功！");
						$("#jqModelAppraisal").jqmHide();
						
					}
			});
		}
	});
});

function onUploadSuccess(data) {
	if (data == "error") {
		alert("上传失败！");
		return;
	}
	if (data != "nophoto") {
		$t = $("table#stafftable");
		$("table#stafftable").find('input#photo').attr("value", data);
	}
	$("#cstaffForm").attr("target", "hidden").attr(
			"action",
			basePath + "ea/cstaff/t_ea_saveCStaff.jspa?pageNumber="
					+ ppageNumber);
	document.cstaffForm.submit.click();
	document.cstaffForm.reset();
}
function thisMovie(movieName) {
	var app = navigator.appName;
	//var verStr = navigator.appVersion;
	if (app.indexOf('Netscape') != -1) {
		return window[movieName];
	} else if (app.indexOf('Microsoft') != -1) {
		return document[movieName];
	}
}
function sunstaff(logLocklist) {
	loglist = logLocklist;
	appDate = $("input#appraisalDate", $("#appraisalForm")).val();
	workday = $("#workDateSaturation").attr("value");
	Allsum = 0;
	positionPaysum = 0;
	achievementsum = 0;
	$(".positionPay").each(function() {
				positionPaysum = positionPaysum + $(this).attr("value") * 1;
				addpositionPay(positionPaysum);
			});
	$(".achievement").each(function() {
				achievementsum = achievementsum + $(this).attr("value") * 1;
				addachievementsum(achievementsum);
			});

	$(":input[class != 'nocheck']", "#staffappr").each(function() {
				Allsum = Allsum + $(this).attr("value") * 1;
				addAllsum(Allsum);
			});
	addworkday(workday);
}

function addpositionPay(positionPaysum) {
	var pc1 = Math.round(positionPaysum * 10000 / 15) / 100;
	var positionPayMoney = Math.round(positionPaysum
			* $("#appraisalForm #positionPay").text() * 100 / 15)
			/ 100;
	var positionPayScore = Math.round(positionPayMoney * 100 * workday / 20)
			/ 100;
	$("#sumScole1").html(Math.round(positionPaysum*100)/100);
	$("#pc1").html(pc1 + "%");
	$("#positionPayMoney").html(positionPayMoney);
	$("#positionPayScore").html(positionPayScore);
}
function addachievementsum(achievementsum) {
	var pc1 = Math.round(achievementsum * 10000 / 15) / 100;
	var positionPayMoney = Math.round(achievementsum
			* $("#appraisalForm #timingMoney").text() * 100 / 15)
			/ 100;
	var positionPayScore = Math.round(positionPayMoney * 100 * workday / 20)
			/ 100;
	$("#sumScole3").html(Math.round(achievementsum*100)/100);
	$("#pc3").html(pc1 + "%");
	$("#timingMoneyMoney").html(positionPayMoney);
	$("#timingMoneyScore").html(positionPayScore);
}
function addAllsum(Allsum) {
	var pc1 = Math.round(Allsum * 10000 / 75) / 100;
	var positionPayMoney = Math.round(Allsum
			* $("#appraisalForm #pushMoney").text() * 100 / 75)
			/ 100;
	var positionPayScore = Math.round(positionPayMoney * 100 * workday / 20)
			/ 100;
	$("#allMoney").html(Math.round(Allsum*100)/100);
	$("#allpc").html(pc1 + "%");
	$("#pushMoneyMoney").html(positionPayMoney);
	$("#pushScore").html(positionPayScore);
}
function addworkday(workday) {
	var stPay = Math.round($("#appraisalForm #stPay").text() * 100 * workday
			/ 20)
			/ 100;
	var secrecyPay = Math.round($("#appraisalForm #secrecyPay").text() * 100
			* workday / 20)
			/ 100;
	var safetyAward = Math.round($("#appraisalForm #safetyAward").text() * 100
			* workday / 20)
			/ 100;
	var awardPayScore = Math.round($("#appraisalForm #awardPay").text() * 100
			* workday / 20)
			/ 100;
	$("#stPayscore").html(stPay);
	$("#secrecyPayscore").html(secrecyPay);
	$("#safetyAwardscore").html(safetyAward);
	$("#awardPayScore").html(awardPayScore);
}

$(function(){
	//单位下所有的部门列表
   	var url = basePath +"ea/organization/sajax_ea_getOrganizationList.jspa?organizationID=0"+"&date=<%=new Date()%>";
    $.ajax({
			url : encodeURI(url),
			type : "get",
			async : true,
			dataType : "json",
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				var nologin = member.nologin;
				if(nologin){
					document.location.href ="<%=basePath%>page/ea/not_login.jsp";
				}
				var persons = member.organizationList;
				var str = "<option value=''>请选择部门</option>";
				for(var i=0;i<persons.length;i++){ 
					var obj = persons[i].organizationName;
					var objID = persons[i].organizationID;
					str += "<option value='"+objID+"'>"+obj+"</option>";
				}
				$("#organizationID").html(str);
			}
	});
	
	//根据部门获取岗位
	$("#organizationID").change(function() { // 获取选中部门
		var orgID = $(this).val();
		var url = basePath +"ea/departmentpost/sajax_ea_getOrganizationPost.jspa?departmentPost.organizationID="+orgID;
		$.ajax({
				url : encodeURI(url),
				type : "get",
				async : true,
				dataType : "json",
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var persons = member.departmentPostlist;
					var str = "";
					if(persons.length == 0){
						str = "<option value=''>此部门无岗位</option>";
					}else{
						str = "<option value=''>请选择岗位</option>";
						for(var i=0;i<persons.length;i++){
							var obj = persons[i].postName;
							var objID = persons[i].depPostID;
							str += "<option value='"+objID+"'>"+obj+"</option>";
						}
					}
					$("#organizationPost").html(str);
				}
		});
	});
});