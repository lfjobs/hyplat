$(document).ready(function() {

	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	$('.draft0').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : '收件箱',
				minheight : 350,
				buttons : [{
					name : '修改',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},
				// 	{
				// 	name : '转至个人草稿箱',
				// 	bclass : 'edit',
				// 	onpress : action
				// 		// 当点击调用方法
				// 	}, {
				// 	separator : true
				// },
				//
					{
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
					name : '转移位置',
					bclass : 'transfer',
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
					name : '打包下载',
					bclass : 'load',
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

	function action(com, grid) {
		switch (com) {
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
						+ docId + "&type=receiver&date=" + new Date();

				break;
			case '查询' :
				showQueryContent("receiver");
				break;

			case '转至个人草稿箱' :
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

			//	if (confirm("确定转至个人草稿箱？")) {
					$("#CommonForm").find("input#docId").val(docId);
					$("#CommonForm")
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/documentinfo/ea_moveOthersToMine.jspa?date="
											+ new Date());
					document.CommonForm.submit.click();

					token = 2;
			//	}

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
			case '转移位置' :
				var checkinput = document.getElementsByName("checkinput");
				var length = 0;
				var docIds = "";
				for (var i = 0; i < checkinput.length; i++) {
					if (checkinput[i].checked) {
						length += 1;
						docIds+= checkinput[i].value+",";
					}
				}
				if (length == 0) {
					alert("请选择");
					return;
				}
				$("#positionForm #changeid").val(docIds);
				$("#positionForm #type1").val("receiver");
				$("#jqModelPosition").jqmShow();

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
			//	if (confirm("确定放入回收站？")) {
					putRecycleBin(docIds, "cg");
			//	}

				break;
			case '打包下载' :
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
				if (length > 5) {
					alert("最多5条");
					return;
				}
			//	if (confirm("确定下载？")) {
					var url = basePath
							+ "ea/documentcommon/sajax_ea_LoadOfZip.jspa?data="
							+ Math.random();
					$.ajax({
								url : url,
								type : "get",
								dataType : "json",
								async : false,
								data : {
									"document.docId" : docIds
								},
								success : function(data) {
									var member = eval("("+data+")");
									var zippath = member.result;
									window.location.href = basePath + "/servlets/render?filename=" + zippath+"&type=zip";

								},
								error : function(data) {
									alert("打包成Zip失败");
								}

							});
				//}

				break;
			case '审批跟踪登记表':
				document.location.href=basePath+"ea/documentinfo/ea_getReceiveBoxList.jspa?track=00&searchType=receiver";
				break;
		 case '导出' :
				url = basePath + "/ea/documentinfo/ea_showExcel.jspa?search="
						+ search+"&searchType=receiver&date="
						+ new Date();;
				open(url);
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/documentinfo/ea_getReceiveBoxList.jspa?search="
						+ search + "&searchType=" + searchType + "&date="
						+ new Date();
				numback(url);
				break;
		}
	}

	$("#tosearch").click(function() {
		$("#searchForm #searchType").val("receiver");
		$("#searchForm").attr(
				"action",
				basePath + "ea/documentinfo/ea_toSearch.jspa?pageNumber="
						+ pNumber + "&date=" + new Date());
		document.searchForm.submit.click();
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
		//	if (confirm("确定传阅草稿?")) {
				$("#SendForm")
						.attr("target", "hidden")
						.attr(
								"action",
								basePath
										+ "ea/documentinfo/ea_passDraftDocuments.jspa?date="
										+ new Date());
				document.SendForm.submit.click();

				token = 2;
		//	}
		} else {
			//if (confirm("确认要发送至领导审批？")) {
				$("#SendForm")
						.attr("target", "hidden")
						.attr(
								"action",
								basePath
										+ "ea/documentflow/ea_createDocument.jspa?date="
										+ new Date());
				document.SendForm.submit.click();
				token = 2;
			}
	//	}

	});

});

// 收件箱列表
function re_load() {

	if (token) {

		document.location.href = basePath
				+ "ea/documentinfo/ea_getReceiveBoxList.jspa";

	}
}

