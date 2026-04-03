$(document).ready(function() {

	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector

	if (module == "contract") {
		flexigrid2();
		$(".trtip").show();

	} else {
		flexigrid1();
		$(".trtip").hide();
	}

	function flexigrid1() {
		$('.draft0').flexigrid({
			  allDouble:true,
					height : 350,
					width : 'auto',
					minwidth : 30,
					title : '拟稿',
					minheight : 350,
					buttons : [{
						name : '新建',
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
						name : '传阅',
						bclass : 'send',
						onpress : action
							// 当点击调用方法
						}, {
						separator : true
					}, {
						name : '至领导审批',
						bclass : 'examine',
						onpress : action
							// 当点击调用方法
						}, {
						separator : true
					}, {
						name : '放入回收站',
						bclass : 'litter',
						onpress : action
							// 当点击调用方法
						}, {
						separator : true
					},{
						name : '转移位置',
						bclass : 'transfer',
						onpress : action
							// 当点击调用方法
						}, {
						separator : true
					},{
						name : '审批跟踪登记表',
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
					},{
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

	}

	function flexigrid2() {
		$('.draft0').flexigrid({
			  allDouble:true,
					height : 350,
					width : 'auto',
					minwidth : 30,
					title : '拟稿',
					minheight : 350,
					buttons : [{
						name : '新建',
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
						name : '传阅',
						bclass : 'send',
						onpress : action
							// 当点击调用方法
						}, {
						separator : true
					}, {
						name : '至信息平台',
						bclass : 'sendplat',
						onpress : action
							// 当点击调用方法
						}, {
						separator : true
					}, {
						name : '至领导审批',
						bclass : 'examine',
						onpress : action
							// 当点击调用方法
						}, {
						separator : true
					}, {
						name : '放入回收站',
						bclass : 'litter',
						onpress : action
							// 当点击调用方法
						}, {
						separator : true
					}, {
						name : '转移位置',
						bclass : 'transfer',
						onpress : action
							// 当点击调用方法
						}, {
						separator : true
					},{
						name : '查看评论',
						bclass : 'comment',
						onpress : action
							// 当点击调用方法
						}, {
						separator : true
					},{
						name : '审批跟踪登记表',
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
					},{
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

	}
	function action(com, grid) {
		switch (com) {
			case '新建' :
				window.location.href = basePath
						+ "ea/documentinfo/ea_newDocument.jspa?type=draft&date="
						+ new Date()+"&journalNum="+journalNum+"&projectName="+encodeURI(projectName);
				break;
			case '修改' :
				var checkinput = document.getElementsByName("checkinput");
				var length = 0;
				var docId = "";
				for (var i = 0; i < checkinput.length; i++) {
					if (checkinput[i].checked) {
						length += 1;
						docId = checkinput[i].value;
					}
				}
				if (length == 0) {
					alert("请选择");
					return;
				}
				if (length > 1) {
					alert("只能修改一个");
					return;
				}
				window.location.href = basePath
						+ "ea/documentinfo/ea_getUpdateDocument.jspa?docId="
						+ docId + "&type=draftupdate&date=" + new Date();

				break;
			
			case '传阅' :
			var checkinput = document.getElementsByName("checkinput");
				var length = 0;
				var docId = "";
				for (var i = 0; i < checkinput.length; i++) {
					if (checkinput[i].checked) {
						length += 1;
						docId += checkinput[i].value + ",";
					}
				}
				if (length == 0) {
					alert("请选择");
					return;
				}
				$("#jqModelSend").jqmShow();
				document.SendForm.reset();
				getAllCompanyOfGroup();
				$("#jqModelSend #submitType").val("pass");
				$("#SendForm #titlem").text("选择人员——传阅");
				$("#SendForm").find("input#docId").val(docId);


				break;
			case '至信息平台' :

				if (docId == "") {
					alert('请选择!');
					return;
				}
				$("#jqModelInfo").jqmShow();

				break;
			case '至领导审批' :
			
			var checkinput = document.getElementsByName("checkinput");
				var length = 0;
				var docId = "";
				for (var i = 0; i < checkinput.length; i++) {
					if (checkinput[i].checked) {
						length += 1;
						docId += checkinput[i].value + ",";
					}
				}
				if (length == 0) {
					alert("请选择");
					return;
				}
				$("#jqModelSend").jqmShow();
				document.SendForm.reset();
				getAllCompanyOfGroup();
				$("#jqModelSend #submitType").val("examine");
				$("#SendForm #titlem").text("选择人员——审批");
				$("#SendForm").find("input#docId").val(docId);
				break;
			case '放入回收站' :
			var checkinput = document.getElementsByName("checkinput");
				var length = 0;
				var docIds = "";
				for (var i = 0; i < checkinput.length; i++) {
					if (checkinput[i].checked) {
						length += 1;
						docIds += checkinput[i].value + ",";
					}
				}
				if (length == 0) {
					alert("请选择");
					return;
				}
//				if (confirm("确定放入回收站？")) {
					putRecycleBin(docIds, "draft");
//				}

				break;
			case '查询' :
				showQueryContent("draft");
				break;
           case '导出' :
				url = basePath + "/ea/documentinfo/ea_showExcel.jspa?search="
						+ search+"&searchType=draft&date="
						+ new Date();;
				open(url);
				break;
			case '查看评论' :
				var checkinput = document.getElementsByName("checkinput");
				var length = 0;
				var docId = "";
				for (var i = 0; i < checkinput.length; i++) {
					if (checkinput[i].checked) {
						length += 1;
						docId = checkinput[i].value;
					}
				}
				if (length == 0) {
					alert("请选择");
					return;
				}
				if (length > 1) {
					alert("只能查看一个");
					return;
				}
				var url = basePath
						+ "ea/documentinfo/sajax_ea_getSuggestions.jspa?date="
						+ new Date().toLocaleString();
				$.ajax({
					url : encodeURI(url),
					type : "get",
					async : false,
					dataType : "json",
					data : {
						docId : docId
					},
					success : function(data) {
						var member = eval("(" + data + ")");
						var suglist = member.result;
						if (suglist.length != 0) {
							var str = "";
							for (var i = 0; i < suglist.length; i++) {
								var obj = suglist[i];
								str += "<tr><td align=\"left\"><a href='' style='text-decoration:none;'>"
										+ obj.senderName
										+ ":</a>&nbsp;&nbsp;&nbsp;"
										+ obj.suggestion
										+ "</td></tr><tr><td align = \"left\">"
										+ obj.sugTimestr
										+ "</td></tr><tr rowspan='2'><td>&nbsp;&nbsp;</td></tr>"
							}
							$("#tablesug").html(str);
						} else {
							$("#tablesug")
									.html("<tr><td>暂无评论</span></td></tr>")
						}
						$("#jqModelSend23").jqmShow();
						$("#htt").hide();

					},
					error : function(data) {
						alert("数据获取失败！");
					}
				});

				break;
			case '转移位置' :
			    var checkinput = document.getElementsByName("checkinput");
				var length = 0;
				var docIds = "";
				for (var i = 0; i < checkinput.length; i++) {
					if (checkinput[i].checked) {
						length += 1;
						docIds+=checkinput[i].value+",";
					}
				}
				if (length == 0) {
					alert("请选择");
					return;
				}
				$("#positionForm #changeid").val(docIds);
				$("#positionForm #type1").val("draft");
				$("#jqModelPosition").jqmShow();

				break;
			case '审批跟踪登记表':
				document.location.href=basePath+"ea/documentinfo/ea_getDocDraftList.jspa?track=00&searchType=draft";
				break;

			case '设置每页显示条数' :
				var url = basePath
						+ "ea/documentinfo/ea_getDocDraftList.jspa?search="
						+ search +"&searchType="+searchType+"&date=" + new Date();
				numback(url);
				break;
		}
	}


	$("#tosearch").click(function() {
		$("#searchForm #searchType").val("draft");
		$("#searchForm").attr(
				"action",
				basePath + "ea/documentinfo/ea_toSearch.jspa?pageNumber="
						+ pNumber + "&date=" + new Date());
		document.searchForm.submit.click();
		token = 2;
	});


	$(".draft0 tr").toggle(function() {
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");

			}, function() {
				$("input.JQuerypersonvalue", $(this)).attr("checked", false);
			});
   //用于阻止复选框的冒泡行为；
	$("input.JQuerypersonvalue").click(function(event) {
				event.stopPropagation();

			});
			
		// 提交去审批传阅
	$("#submitResult").click(function() {
        var radio = $(".source:checked").val();

         if(radio=="in") {
             if ($("#SendForm #receiverID").val() == "") {
                 alert("请选择收件人");
                 return;
             }else{

             	$("#SearchTable").remove();
			 }
         }else{
             if($("#receiverid").val()==""){
                 alert("请填写收件人");
                 return;
			 }else{
                 $("#SearchTable2").remove();
			 }

		 }

		if ($("#jqModelSend #submitType").val() == "pass") {
			//if (confirm("确定传阅草稿?")) {
				$("#SendForm")
						.attr("target", "hidden")
						.attr(
								"action",
								basePath
										+ "ea/documentinfo/ea_passDraftDocuments.jspa?date="
										+ new Date());
				document.SendForm.submit.click();

				token = 2;
			//}
		} else {
		//	if (confirm("确认要发送至领导审批？")) {
				$("#SendForm")
						.attr("target", "hidden")
						.attr(
								"action",
								basePath
										+ "ea/documentflow/ea_createDocument.jspa?date="
										+ new Date());
				document.SendForm.submit.click();
				token = 2;
		//	}
		}

	});
	
	
	
			// 点击马上添加显示添加表单
	$("#rightadd").click(function() {
				if ($(this).text() == "取消添加") {
					$(this).text("马上添加");
					$("#addform").hide();
					$("#addsubmit").show();
				} else {
					$(this).text("取消添加");
					$("#addform").show();
					$("#addsubmit").hide();
				}

			});	

	// 选择方法一的提交
	$("#addInfoForm #addsubmit").click(function() {
		var stelphone = $("#addInfoForm #historyinfo").find("option:selected")
				.val();
		var smname = $("#addInfoForm #historyinfo").find("option:selected")
				.text();
		if(stelphone==""){
			alert("请选择社会人员");
			return;
		}
		$("#InfoForm #socialName").val(smname);
		$("#InfoForm #socials").val(stelphone);
		$("#jqModeladdInfo").jqmHide();
		$("#jqModelInfo").jqmShow();

	});
	
	
		// 临时添加表单验证
	$("#addInfoForm :input").bind("blur", function() {
		$input = $(this);
		$parent = $input.parent();
		var inputValue = $input.attr("value");
		$parent.find(".error").remove();
		$parent.find(".corect").remove();
		$parent.find(".tooltip").remove();
		if ($input.is(".put3")) {
			if ($.trim(inputValue) == "") {
				$parent
						.append("<span class=\"error\"><a  class=\"tex\">不能为空</a></span>");
				return;
			} else {

				$parent
						.append("<span class=\"corect\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>");

			}
		}
		if ($input.is(".tel")) {
			if ($.trim(inputValue) == "") {
				$parent
						.append("<span class=\"error\"><a  class=\"tex\">不能为空</a></span>");
				return;
			} else {
				reg = /^(13[0-9]|15[0|2|3|6|7|8|9]|18[7|8|9])\d{8}/;
				if (!reg.test(inputValue)) {
					$parent
							.append("<span class=\"error\"><a  class=\"tex\">号码不合法</a></span>");
					return;
				} else if (checkTel($.trim(inputValue)) == 1) {
					$parent
							.append("<span class=\"error\"><a  class=\"tex\">号码已存在</a></span>");
				} else {
					$parent
							.append("<span class=\"corect\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>");
				}
			}
		}

	});

});


// 传送草稿到公共信息平台
function passDraftToComPlat() {
	var docId = $("input[name=radioGroup]:radio:checked").val();
	var social = $("#InfoForm #socials").val();
	if (social == "") {
		alert("请选择人员");

		return;
	}
	var checkinput = document.getElementsByName("checkinput");
	var length = 0;
	var values = "";
	for (var i = 0; i < checkinput.length; i++) {
		if (checkinput[i].checked) {
			length += 1;
			values += checkinput[i].value + ",";
		}
	}
	if (length == 0) {
		alert("请选择交互平台");
		return;
	}

	var visitType = $("input[name=inputRadio]:radio:checked").val();

	//if (confirm("确定传至信息平台？")) {

		$("#docIds").val(docId);
		$("#webs").val(values);
		$("#visitTypes").val(visitType);
		$("#social2").val(social);
		$("#InfoForm").attr("target", "hidden").attr(
				"action",
				basePath + "ea/documentinfo/ea_passDraftToComPlat.jspa?date="
						+ new Date());
		document.InfoForm.submit.click();
		token = 2;
	//}

}

// 点击临时添加
function addSociety() {
	$("#jqModelInfo").jqmHide();
	$("#jqModeladdInfo").jqmShow();
	$("#addform").hide();
	$(".hr2").hide();
	$("#rightadd").text("马上添加");
	getSocialMember();
	$("#addsubmit").show();
}

// 添加社会人员信息
function addInfo() {
	if ($("#addInfoForm .error").length) {
		return;
	}
	var smName = $.trim($("#addform #smName").val());
	var scompany = $.trim($("#addform #scompany").val());
	var stelphone = $.trim($("#addform #stelphone").val());
	var url = basePath + "ea/documentinfo/sajax_ea_addSocialMember.jspa?date="
			+ new Date();
	$.ajax({
				url : url,
				type : "get",
				async : false,
				dataType : "json",
				data : {
					"socialMemberInfo.smName" : smName,
					"socialMemberInfo.companyName" : scompany,
					"socialMemberInfo.telphone" : stelphone
				},
				success : function(data) {
					$("#InfoForm #socialName").val(smName + "(" + scompany + ")");
					$("#InfoForm #socials").val(stelphone);
					$("#jqModeladdInfo").jqmHide();
					$("#jqModelInfo").jqmShow();
				},
				error : function(data) {
					alert("添加社会人员失败");
				}

			});

}
// 获得所有社会人员信息
function getSocialMember() {
	var url = basePath + "ea/documentinfo/sajax_ea_getSocialMember.jspa?date="
			+ new Date();
	$.ajax({
				url : url,
				type : "get",
				async : false,
				dataType : "json",
				success : function(data) {
					var member = eval("(" + data + ")");
					var smlist = member.result;
					var str = "<option value=''>请选择社会人员</option>"
					for (var i = 0; i < smlist.length; i++) {
						var obj = smlist[i];
						str += "<option title=" + obj.smName + "("
								+ obj.companyName + ") value = " + obj.telphone
								+ ">" + obj.smName + "(" + obj.companyName
								+ ")</option>"
					}
					$("#addInfoForm #historyinfo").html(str);
				},
				error : function(data) {
					alert("读取数据失败");
				}
			});
}

// 验证电话号码不可重复
function checkTel(telphone) {
	var num = 0;
	var url = basePath + "ea/documentinfo/sajax_ea_checktel.jspa?date="
			+ new Date();
	$.ajax({
				url : url,
				type : "get",
				async : false,
				datatype : "json",
				data : {
					"socialMemberInfo.telphone" : telphone
				},
				success : function(data) {
					var memeber = eval("(" + data + ")");
					var result = memeber.result;
					if (result == "fail") {
						num = 1;
					}
				},
				error : function(data) {
					alert("验证电话失败");
				}

			});

	return num;
}

// 获取草稿
function re_load() {
	if (token) {
		document.location.href = basePath
				+ "ea/documentinfo/ea_getDocDraftList.jspa";

	}
}

