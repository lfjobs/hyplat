$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	
	var query = "<form name='cstaffSearchForm' id='cstaffSearchForm' method='post'>" +
			"<span style=\"margin-left:20px;\">人员姓名：</span>" +
			"<input type='text' style=\"width: 90px\" id='staffName' name='searchCStaff.staffName'/>" +
			"<span style=\"margin-left:10px;\">身份证：</span>" +
			"<input type='text' style=\"width: 130px\" id='staffIdentityCard' name='searchCStaff.staffIdentityCard'/>" +
			"<span style=\"margin-left:10px;\">地址：</span>" +
			"<input type='text' style=\"width: 90px\" id='staffAddress' name='searchCStaff.staffAddress' onfocus='blue()'/>" +
			"<span style=\"margin-left:10px;\">录入人员：</span>" +
			"<input type='text' style=\"width: 90px\" id='entryPersonnel' name='searchCStaff.entryPersonnel'/>" +
			"<span style=\"margin-left:10px;\">邀请人账号：</span>" +
			"<input type='text' style=\"width: 90px\" id='account' name='searchCStaff.account'/>" +
			"<input class=\"input-button\"type='button' style=\"margin:0px;margin-left:15px;margin-top:11px;\" value='查询' id='searchStaff'/>" +
			"<input name='search' type='hidden' value='search' />" +
			"<input name='showType' type='hidden' value='"+showType+"' />" +
			"<input name='parameter' type='hidden' value='"+parameter+"' />" +
			"</form>";
	
	if (aa == "aa") {
		$('.JQueryflexme').flexigrid({
					height : 180,
					width : 'auto',
					minwidth : 30,
					title : titlename+query,
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
						separator : true
					}, {
						name : '招聘',
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
						name : '添加到往来个人',
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
	} else if (aa == "bb") {
	$('.JQueryflexme').flexigrid({
					height : 180,
					width : 'auto',
					minwidth : 30,
					title : '登记汇总'+query,
					minheight : 80,
					buttons : [ {
						name : '修改',
						bclass : 'edit',
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
						name : '招聘',
						bclass : 'excel',
						onpress : action
							// 当点击调用方法
						}, {
						separator : true
					}, {
						name : '打印登记表',
						bclass : 'excel',
						onpress : action
							// 当点击调用方法
						}, {
						separator : true
					}, {
						name : '添加到往来个人',
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
	} else {
		if(flexbutton == 'flexbutton'){
			$('.JQueryflexme').flexigrid({
					height : 180,
					width : 'auto',
					minwidth : 30,
					title : titlename+query,
					minheight : 80,
					buttons : [{
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
					allDouble : true,
					width : 'auto',
					minwidth : 30,
					title : titlename+query,
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
						separator : true
					}, {
						name : '查看',
						bclass : 'mysearch',
						onpress : action
							// 当点击调用方法
						}, {
						separator : true
					}, /*{
						name : '查询',
						bclass : 'mysearch',
						onpress : action
							// 当点击调用方法
						}, {
						separator : true
					},*/ {
						name : '个人需求客户登记表',
						bclass : 'excel',
						onpress : action
							// 当点击调用方法
						},{
						separator : true
					},{
						name : '导出',
						bclass : 'excel',
						onpress : action
							// 当点击调用方法
					}, {
						separator : true
					}, {
						name : '导入',
						bclass : 'excel',
						onpress : action
							// 当点击调用方法
					}, {
						separator : true
					}, {
						name : '添加到往来个人',
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
	}
	function action(com, grid) {
		switch (com) {
			case '招聘' :
				staffsize = 1;
				if (personvalue == "") {
					alert('请选择人员');
					return
				}
				$p = $("tr#" + personvalue);
				var data = $p.find("span#staffIdentityCard").text();
				if (data == "") {
					alert('该人员没有身份证！不能办理入职手续');
					return;
				}
				if (notoken)
					return;
				notoken = 1;

				var url = pbasePath
						+ "ea/cstaff/sajax_ea_saveCOS.jspa?cos.staffID="
						+ personvalue + "&date=" + new Date().toLocaleString();
				$.ajax({
							url : encodeURI(url),
							type : "get",
							async : true,
							dataType : "json",
							success : function cbf(data) {
								var member = eval("(" + data + ")");
								var result = member.result;
								if (result) {
									alert('该人员已在本公司');
									notoken = 0;
									return;
								}

								$t = $("#AuditionForm");
								$t.find('img#photo').attr("src", "xxx");
								document.AuditionForm.reset();
								$p = $("tr#" + personvalue);
								if ($p.find("span#photo").text() != "")
									$t.find('img#photo').attr(
											"src",
											pbasePath
													+ $p.find("span#photo")
															.text());
								$t.find('#staffName').attr("value",
										$p.find("span#staffName").text());
								$t.find('#staffID').attr("value",
										$p.find("span#staffID").text());
								$t.find('#staffIdentityCard').attr(
										"value",
										$p.find("span#staffIdentityCard")
												.text());
								$("#jqModel").jqmHide();
								$("#jqMode2").jqmShow();
								notoken = 0;
							},
							error : function cbf(data) {
								notoken = 0;
								alert("数据获取失败！");
							}
						});
				break;
			case '个人需求客户登记表' :
				if (personvalue == "") {
					alert('请选择人员');
					return
				}
				var staffID = $("tr#" + personvalue).find("span#staffID")
						.text();
				var url = basePath
						+ "ea/cstaff/ea_getBasicInformation.jspa?staffID="
						+ staffID;
				window.open(url);
				break;
			case '添加' :
				var url = basePath+ "ea/humanResource/ea_getHumanResource.jspa?showType=add&cstaff.status="+status;
				window.open(url, '','scrollbars=yes,resizable=yes,channelmode');
				break;
			 case '打印登记表':
			 	if(aa == "bb"){
					var url = basePath
						+ "/page/ea/main/human/office/production/printBasicInformation.jsp?star=招聘登记表";
			 	}
				window.open(encodeURI(url));
				break;
			case '修改' :
				$("#PhotoName").html("");
				if (personvalue == "") {
					alert('请选择人员');
					return;
				}
				var url = basePath
						+ "ea/humanResource/ea_getHumanResource.jspa?showType=edit&cstaff.staffID="
						+ personvalue+"&cstaff.status="+status;

                var findurl = basePath + "/st/enroll/sajax_ea_findRelation.jspa?staffId="+personvalue;
                $.ajax({
                    url : encodeURI(findurl),
                    type : "get",
                    async : false,
                    dataType : "json",
                    success : function (data) {
                        var member = eval("(" + data + ")");
                        var contactRelationList = member.contactRelationList;
                        for (var i = 0;i<contactRelationList.length;i++){
                            var relation = contactRelationList[i];
                            if (relation == "学员"){
								 url += "&relation=学员";
								break;
                            }else if(relation =="教练"){
                                url += "&relation=教练";
                                break;
							}
                        }
                    }
                });
				window
						.open(url, '',
								'scrollbars=yes,resizable=yes,channelmode');
				break;
			case '查看' :
				$("#PhotoName").html("");
				if (personvalue == "") {
					alert('请选择人员');
					return;
				}
				var url = basePath
						+ "ea/soincumbent/ea_getBasicInformation.jspa?staffID="
						+ personvalue;
				window.open(url);
				break;
			case '添加到往来个人' :
				staffsize = 1;
				if (personvalue == "") {
					alert('请选择人员');
					return
				}
				$td = $("td.code");
				$td.children('select').empty();
				$td.children('select:gt(0)').hide();

				var url = pbasePath
						+ "ea/cstaff/sajax_n_ea_getSelectLists.jspa?date="
						+ new Date().toLocaleString();
				$.ajax({
					url : url,
					type : "get",
					async : true,
					dataType : "json",
					success : function cbf(data) {
						var member = eval("(" + data + ")");
						var codeRelationList = member.codeRelationList;
						$select = "<option selected='selected' value = ''>--请选择--</option>";
						$td.children('select:eq(0)').append($select);
						for (var i = 0; i < codeRelationList.length; i++) {
							$op = $("<option/>");
							$op.val(codeRelationList[i].codeID)
									.text(codeRelationList[i].codeValue).attr(
											"id", codeRelationList[i].codeID);
							$td.children('select:eq(0)').append($op);
						}
						notoken = 0;
						$add = "<option class='add'  value ="+codeRelationList[0].codePID+" >--新增--</option>";
						$td.children('select:eq(0)').append($add);
						$td.children('select:eq(0)').show();
						$("div#selectcode").jqmShow();
					},
					error : function cbf(data) {
						alert("数据获取失败！");
					}
				});
				break;
			case '导出' :
				url = pbasePath + "ea/cstaff/ea_showStaffExcel.jspa?search="+psearch+"&showType="+showType+"&parameter="+parameter;
				open(url);
				break;
			case '导入' :
				url = basePath + "ea/importdata/ea_showImportDataPage.jspa?fileType=SHRLGL(YX)";				
				window.open(url,"importDataPage","height=400,width=400,top=0,left=0,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no");
				break;
			case '设置每页显示条数' :
				var url = pbasePath
						+ "ea/cstaff/ea_getListCStaffByCompanyID.jspa?search="
						+ psearch+"&aa="+aa+"&flexbutton="+flexbutton+"&cstaff.status="+status+"&showType="+showType+"&parameter="+parameter;
				numback(url);
				break;
		}
	}
	$("#singleShuterphoto").click(function() {
				$("table#stafftable").find('img#photo').hide();
				$("table#stafftable").find('#singleShuter').show();
			});

	$(".menu00").click(function() {
				$(this).hide();
			});

	// /////////////////////////////添加到往来个人
	$("input#savecode").click(function() {// 保存
				if (retoken)
					return;
				retoken = 1;
				var relats = '';
				$("td.code").children('select:lt(' + opaNum + ')').each(
						function() {
							var relat = $(this).children("option:selected")
									.text();
							relats = relats + relat + "-";
						});
				relatAll = relats.substring(0, relats.lastIndexOf("-"));
				var wlgrurl = pbasePath
						+ "ea/cstaff/sajax_n_ea_isContactUser.jspa?conRelation.staffID="
						+ personvalue + "&aa=" + aa + "&conRelation.relation="
						+ relatAll + "&date=" + new Date().toLocaleString();
				$.ajax({
							url : encodeURI(wlgrurl),
							type : "get",
							async : true,
							dataType : "json",
							success : function cbf(data) {
								var member = eval("(" + data + ")");
								var c = member.c;
								retoken = 0;
								if (c) {
									alert("此人员已经添加到往来个人！");
									$(".jqmWindow").jqmHide();
								} else {
									alert("添加往来个人成功！");
									$(".jqmWindow").jqmHide();
								}
							},
							error : function cbf(data) {
								retoken = 0;
								alert("数据获取失败！");
							}
						});
			});

	$(".JQueryreturns").click(function() { // 取消
				retoken = 0;
				$(".jqmWindow").jqmHide();
			});

	/** ************************************往来关系********************************** */
	$('td.code select[number]').change(function() {
		if (notoken) {
			alert("正在获取数据！请稍等");
			return;
		}
		notoken = 1;
		var num = $(this).attr("number");
		var number = parseInt(num) + 1;
		$td = $("td.code");
		$td.children('select:gt(' + num + ')').empty();
		codePID = $td.children('select:eq(' + num + ')')
				.children("option:selected").attr("id");
		codeID = $td.children('select:eq(' + num + ')')
				.children("option:selected").val();
		codeValue = $td.children('select:eq(' + num + ')')
				.children("option:selected").text();
		var D = $td.children('select:eq(' + num + ')')
				.children("option:selected").attr("class");
		if (D == 'add') {
			notoken = 0;
			codePID = $td.children('select:eq(' + num + ')')
					.children("option:selected").val();
			var numbers = parseInt(num) - 1;
			codeID = $td.children('select:eq(' + numbers + ')')
					.children("option:selected").val();
			codeValue = $td.children('select:eq(' + numbers + ')')
					.children("option:selected").text();
			$("#selectcode").jqmHide();
			$("input#codePID", $("#cstaffCodeForm")).val(codePID);
			$("input#treenum", $("#cstaffCodeForm")).val(num);
			$("input#codePID", $("#cstaffCodeForm"))
					.attr("title", "selectcode");
			$("input#codeNumber", $("#cstaffCodeForm")).val(maxNum);
			$("#jqModelkf").jqmShow();
			$("input#codeValue", $("#cstaffCodeForm")).focus();
			return;
		}
		if (codeID == "") {
			var numbers = parseInt(num) - 1;
			codeID = $td.children('select:eq(' + numbers + ')')
					.children("option:selected").val();
			codeValue = $td.children('select:eq(' + numbers + ')')
					.children("option:selected").text();
			$td.children('select:gt(' + num + ')').hide();
			notoken = 0;
			return;
		}
		var codeurl = basePath
				+ "ea/ccode/sajax_ea_getListCCodeByPID.jspa?codeID=";
		$.ajax({
			url : encodeURI(codeurl + codeID + "&date="
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
				var codeList = member.codeList;
				$td = $("td.code");
				$select = "<option selected='selected' value = ''>--请选择--</option>";
				$td.children('select:eq(' + number + ')').append($select);
				for (var i = 0; i < codeList.length; i++) {
					$op = $("<option />");
					$op.attr("value", codeList[i].codeID).attr("id",
							codeList[i].codeID).text(codeList[i].codeValue);
					$td.children('select:eq(' + number + ')').append($op);
				}
				$add = "<option class='add'  value =" + codeID
						+ " >--新增--</option>";
				$td.children('select:eq(' + number + ')').append($add);
				$td.children('select:eq(' + number + ')').show();
				$td.children('select:gt(' + number + ')').hide();
				opaNum = number;
				notoken = 0;
			},
			error : function cbf(data) {
				notoken = 0;
				alert("数据获取失败！");
			}
		});
	});

	$(".JQuerySubmitkf").click(function() { // 保存往来关系
				$(".put3").trigger("blur");
				if ($(".error", $("#cstaffCodeForm")).length) {
					return;
				}
				parmiter = $("#parmiter").val();
				var url = basePath
						+ "ea/ccode/sajax_ea_ajaxSavaCCode.jspa?pageNumber=${pageNumber}&parmiter="
						+ parmiter + "&code.codeNumber="
						+ $("#codeNumber").val() + "&code.codeValue="
						+ $("#codeValue").val() + "&code.codeDesc="
						+ $("#desc").val() + "&code.codePID="
						+ $("#codePID").val();
				$.ajax({
					url : encodeURI(url),
					type : "get",
					async : true,
					dataType : "json",
					success : function cbf(data) {
						var member = eval("(" + data + ")");
						var code = member.code;
						var divid = $("input#codePID", $("#cstaffCodeForm"))
								.attr("title");
						$op1 = $("<option selected='selected'/>").attr("value",
								code.codeID).attr("id", code.codeID)
								.text(code.codeValue);
						var treenum = $("input#treenum", $("#cstaffCodeForm"))
								.val();
						var num = parseInt(treenum);
						$("select:eq(" + num + ")", $("#" + divid))
								.append($op1);
						$select = "<option selected='selected'>--请选择--</option>";
						var number = num + 1;
						$("select:eq(" + number + ")", $("#" + divid))
								.append($select);
						$add = "<option class='add'  value = '" + code.codeID
								+ "' >--新增--</option>";
						$("select:eq(" + number + ")", $("#" + divid))
								.append($add);
						$("select:eq(" + number + ")", $("#" + divid)).show();
						codeID = code.codeID;
						codeValue = code.codeValue;
						notoken = 0;
						alert("添加成功！");
						$(".error", $("#cstaffCodeForm")).remove();
						$("#jqModelkf").jqmHide();
						$("#" + divid).jqmShow();
					},
					error : function cbf(data) {
						notoken = 0;
						alert("数据获取失败！");
					}
				});
			});

	$(".JQueryreturnkf").click(function() { // 取消添加往来关系
				notoken = 0;
				document.cstaffCodeForm.reset();
				$(".error", $("#cstaffCodeForm")).remove();
				var divid = $("input#codePID", $("#cstaffCodeForm"))
						.attr("title");
				$("#jqModelkf").jqmHide();
				$("#" + divid).jqmShow();
			});
	/** ************************************************************** */

	$("input.JQuerySubmit").click(function() {// 保存
				if (notoken)
					return;
				notoken = 1;
				if (photosizes) {
					alert("上传图片规格必须为102X126！ 大小必须小于1M");
					notoken = 0;
					return;
				}
				var tm = $(".tm").val();
				if (tm == "00") {
					$(".card").addClass("put3");
				} else if (tm == "01") {
					$(".card").removeClass("put3");
				}
				$("#cstaffForm .put3").trigger("blur");
				$(".IdentityCard").trigger("blur");
				if ($("form#cstaffForm .error").length) {
					notoken = 0;
					return;
				}
				$t = $("table#stafftable");
				var addr = "";
				$(".JQueryaddress").find("select")
						.find("option[value]:selected").each(function() {
							if ($(this).text() != '--新增--'
									&& $(this).text() != '--请选择--')
								addr = addr + $(this).text();
						});
				$("#cstaffForm").find("input#staffAddress").val(addr);
				if ($("table#stafftable").find('#singleShuter').is(":visible")) {
					var f = null;
					if (document.singleShuter)
						f = document.singleShuter;
					else
						f = document.getElementById('singleShuter');
					f.SavePhoto(pbasePath + "js/photo/save2.jsp");
					if (personvalue == "") {
						token = 1;
					} else {
						token = 2;
					}
				} else {
					if (personvalue == "") {
						$("#cstaffForm")
								.attr("target", "hidden")
								.attr(
										"action",
										pbasePath
												+ "ea/cstaff/t_ea_saveCStaff.jspa?pageNumber="
												+ ppageNumber + "&aa=" + aa+"&cstaff.status="+status);
						document.cstaffForm.submit.click();
						document.cstaffForm.reset();
						$t = $("table#stafftable");
						$t.find('img#photo').attr("src", "xxx");
						token = 1;
						return;
					}
					$("#cstaffForm")
							.attr("target", "hidden")
							.attr(
									"action",
									pbasePath
											+ "ea/cstaff/t_ea_saveCStaff.jspa?pageNumber="
											+ ppageNumber + "&aa=" + aa+"&cstaff.status="+status);
					document.cstaffForm.submit.click();
					document.cstaffForm.reset();
					$t = $("table#stafftable");
					$t.find('img#photo').attr("src", "xxx");
					token = 2;
				}

			});

	$("input.JQueryreturn").click(function() {// 取消
				if (token)
					document.location.href = basePath
							+ "ea/cstaff/ea_getListCStaffByCompanyID.jspa?search="
							+ psearch + "&pageNumber=" + ppageNumber + "&aa="
							+ aa+"&cstaff.status="+status;
				$("#jqModel").jqmHide();
				$("#jqMode2").jqmHide();
				$("#contactuser").jqmHide();
			});

	$("input.JQueryreturn2").click(function() {// 城市添加取消
				retoken = 0;
				$("#newdistrict").jqmHide();
				$("#jqModel").jqmShow();
			});
	$("input.JQueryreturn4").click(function() {// 取消
				window.close();

			});
	$("input.JQueryreturn5").click(function() {// 打印预览
				var techang = $("input#techang").val();
				var xuqiu = $("input#xuqiu").val();
				var fazhan = $("input#fazhan").val();
				var zhengjian = $("input#zhengjian").val();
				var bianhao = $("input#bianhao").val();
				var idea = $("input#idea").val();
				var str = "";
				$("[name='cbox']").each(function() {
							if ($(this).is(':checked')) {
								str += $(this).val() + "，";
							}
						});
				str = str.substring(0, str.length - 1);
				window.open(encodeURI(basePath
						+ "/ea/cstaff/ea_toprintpeople.jspa?staffID=" + staffID
						+ "&techang=" + techang + "&xuqiu=" + xuqiu
						+ "&fazhan=" + fazhan + "&zhengjian=" + zhengjian
						+ "&bianhao=" + bianhao + "&idea=" + idea + "&str="
						+ str));
			});
	$(".close").click(function() {// 取消
				if (token)
					document.location.href = basePath
							+ "ea/cstaff/ea_getListCStaffByCompanyID.jspa?search="
							+ psearch + "&pageNumber=" + ppageNumber + "&aa="
							+ aa+"&cstaff.status="+status;
				$("#jqModel").jqmHide();
				$("#jqModelSearch").jqmHide();
				$("#jqMode2").jqmHide();
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
	$(".JQueryflexme tr[id]").dblclick(function() {
		if(flexbutton == 'flexbutton'){
			parent.document.getElementById("isSubmit").click();
		}else{
			action('修改');// 当双击时出发 action方法.等价于先选中再点击修改按钮
		}
	});
	$("#invite").click(function() {
				action('招聘');
			});
	// $("#printsingleShuterphoto").click(function(){
	// action('打印');
	// })
	// 查询相关操作
	$("#searchStaff").click(function() {
		$("#cstaffSearchForm").attr(
				"action",
				pbasePath + "ea/cstaff/ea_toSearchCStaff.jspa?pageNumber="
						+ ppageNumber + "&aa=" + aa +"&flexbutton="+flexbutton+"&cstaff.status="+status);
		document.getElementById("cstaffSearchForm").submit();
		$("#cataffSearchTable").find("input[name]").val("");
	});
	
	$("#Auditiontj").click(function() {
		 if (retoken)
		 return;
		 retoken = 1;
		 if ($("form#AuditionForm .error").length) {
			 retoken = 0;
			 return;
		 }
		 var url = pbasePath + "ea/saudition/sajax_n_ea_saveAudition.jspa?"
		 + $("#AuditionForm").serialize() + "&date="
		 + new Date().toLocaleString()+"&aa="+aa;
		 $.ajax({
				 url : url,
				 type : "get",
				 async : true,
				 dataType : "json",
				 success : function cbf(data) {
					 var member = eval("(" + data + ")");
					 var result = member.result;
					 retoken = 0;
					 if (result) {
						 alert('招聘成功');
						 $("#jqMode2").jqmHide();
						 document.location.href = basePath
							+ "ea/saudition/ea_getauditionList.jspa?status=0";
					 }
					 $("#jqMode2").jqmHide();
				 },
				 error : function cbf(data) {
					 retoken = 0;
					 alert("数据获取失败！");
				 }
		});
	});

});
// 查询相关操作END

$(document).ready(function() {
	// 序号自动生成
	var numurl = basePath + "ea/ccode/sajax_ea_getCodeNum.jspa";
	$.ajax({
				url : encodeURI(numurl),
				type : "get",
				async : true,
				dataType : "json",
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var nologin = member.nologin;
					if (nologin) {
						document.location.href = "<%=basePath%>page/ea/not_login.jsp";
					}
					maxNum = member.maxNum;
				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}
			});
});

function blue(){
	$("#JQueryaddress").jqmShow();
}

function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/cstaff/ea_getListCStaffByCompanyID.jspa?search="
				+ psearch + "&pageNumber=" + ppageNumber + "&aa=" + aa
				+ "&pageForm.pageNumber=" + $("#pageNumber").attr("value")+"&showType="+showType+"&parameter="+parameter;
}

function onUploadSuccess(data) {
	if (data == "error") {
		alert("上传失败！");
		return;
	}
	if (data != "nophoto") {
		$t = $("table#stafftable");
		$("table#stafftable").find('input#photo').attr("value", data);
	}
	if (personvalue == "") {
		$("#cstaffForm").attr("target", "hidden").attr(
				"action",
				pbasePath + "ea/cstaff/t_ea_saveCStaff.jspa?pageNumber="
						+ ppageNumber);
		document.cstaffForm.submit.click();
		document.cstaffForm.reset();
		$t = $("table#stafftable");
		$t.find('img#photo').attr("src", "xxx");
		token = 1;
		return;
	}
	$("#cstaffForm").attr("target", "hidden").attr(
			"action",
			pbasePath + "ea/cstaff/t_ea_saveCStaff.jspa?pageNumber="
					+ ppageNumber);
	document.cstaffForm.submit.click();
	document.cstaffForm.reset();
	$t = $("table#stafftable");
	$t.find('img#photo').attr("src", "xxx");
	token = 2;
};


//地域调查 
$(document).ready(function() {
	var PID="";// 当点新曾时,上一级被选中项的id
	var rovince="";// 被改变的那个的id
	var districtPID;
	function LiuZhongYaoDeShaGuaDiZhi() {
		// 非空验证还原
		if (retoken)
			return;
		retoken = 1;
		$td = $("td.JQueryaddress");
		$td.children('select').empty();
		$select = "<option selected='selected'>--请选择--</option>";
		$("#province", $td).append($select);
		$td = $("td.JQueryaddress");
		$('td.JQueryaddress input[name=changes]').show();
		var url = basePath
				+ "/ea/cstaff/sajax_n_ea_getCDistricts.jspa?districtPID=0"
				+ "&date1=" + new Date();
		$.ajax({
					url : url,
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
		districtPID = $(rovince, $td).children("option:selected").val();
		if (districtPID == '--请选择--') {
			if (number != "0") {
				var nu = parseInt(number) - 1;
				districtPID = $("select[number=" + nu + "]", $td).val();
			} else {
				districtPID = "";
			}
			$td.find('input#address').val(districtPID);
			retoken = 0;
			return;
		}
		var url = basePath
				+ "ea/cstaff/sajax_n_ea_getCDistricts.jspa?districtPID="
				+ encodeURI(districtPID) + "&date3=" + new Date();
		$.ajax({
			url : url,
			type : "get",
			async : true,
			dataType : "json",
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				var nologin = member.nologin;
				if (nologin) {
					document.location.href = basePath + "page/ea/not_login.jsp";
				}
				var distinctlist = member.distinctlist;
				$select = "<option selected='selected'>--请选择--</option>";
				$(rovince, $td).next().append($select);
				if (distinctlist.length) {
					for (var i = 0; i < distinctlist.length; i++) {
						$op = $("<option/>");
						$op.attr("value", distinctlist[i].districtID).attr(
								"id", distinctlist[i].districtID)
								.text(distinctlist[i].districtName);
						$(rovince, $td).next().append($op);
					}
				}
				
				$td.find('input#address').val(districtPID);
				retoken = 0;
			},
			error : function cbf(data) {
				retoken = 0;
				alert("数据获取失败！");

			}
		});
	});	
	LiuZhongYaoDeShaGuaDiZhi();
});