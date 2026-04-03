$(document).ready(function() {

	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector

	$('.wspdoc').flexigrid({
				height : 349,
				width : 'auto',
				minwidth : 30,
				title : '未盖章',
				minheight : 349,
				buttons : [{
					name : '查看详情',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '查看盖章',
					bclass : 'attach',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},{
					name : '至分发人',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},{
					name : '驳回',
					bclass : 'reject',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},{
					name : '转交他人盖章/签字',
					bclass : 'seal',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '审批跟踪登记表',
					bclass : 'excel',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},
//					{
//					name : '存档',
//					bclass : 'store',
//					onpress : action
//						// 当点击调用方法
//					}, {
//					separator : true
//				},
					{
					name : '转移位置',
					bclass : 'transfer',
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
				}, {
					name : '申请印章',
					bclass : 'load',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}]
			});

	function action(com, grid) {
		switch (com) {
			case '查看详情' :
				if (docId == "") {
					alert('请选择!');
					return;
				}
				window.location.href = basePath
						+ "ea/documentinfo/ea_getViewDocument.jspa?docId="
						+ docId + "&type=toStampnoView&date=" + new Date();

				$("#newForm input[id=title]").attr("disabled", "disabled");
				break;
			case '查看盖章' :
				if (docId == "") {
					alert('请选择!');
					return;
				}
				OpenOffice(docId,"2","未盖章");
				break;
			case '驳回' :
				if (docId == "") {
					alert("请选择");
					return;
				}
				//if (confirm("确定驳回？")) {
					$("#sealForm #docID").val(docId);
	                $("#sealForm #jump2").val("noSeal");
					$("#sealForm")
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/documentflow/ea_sealDocument.jspa?date="
											+ new Date());
					document.sealForm.submit.click();
					token = 2;
			//	}
				

				break;
			case '转交他人盖章/签字' :
				if (docId == "") {
					alert("请选择");
					return;
				}
				$("#jqModelSend").jqmShow();
				document.SendForm.reset();
				getAllCompanyOfGroup();
				$("#jump").val("seal");
				$("#SendForm #titlem").text("选择人员——转至盖章/签字");

				break;
			case '至分发人' :
				if (docId == "") {
					alert("请选择");
					return;
				}
				$("#jqModelSend").jqmShow();
				document.SendForm.reset();
				getAllCompanyOfGroup();
				$("#jump").val("publish");
				$("#SendForm #titlem").text("选择人员——转至分发人");

				break;
			case '存档' :
				if (docId == "") {
					alert("请选择");
					return;
				}
			//	if (confirm("确定存档？")) {
					$("#sealForm #docID").val(docId);
	                $("#sealForm #jump2").val("finish");
					$("#sealForm")
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/documentflow/ea_sealDocument.jspa?date="
											+ new Date());
					document.sealForm.submit.click();
					token = 2;
			//	}

				break;
			case '转移位置' :
				if (docId == "") {
					alert("请选择");
					return;
				}
				$("#positionForm #changeid").val(docId+",");
				$("#positionForm #type1").val("seal");
				$("#jqModelPosition").jqmShow();

				break;
			case '审批跟踪登记表':
				document.location.href=basePath+"/ea/documentflow/ea_getSealDocList.jspa?finishType=seal&track=00";
				break;
			case '查询' :
				showQueryContent("seal");
				break;
			case '导出' :
				url = basePath + "/ea/documentflow/ea_showExcel.jspa?search="
						+ search+"&finishType=seal&date="
						+ new Date();;
				open(url);
				break;

			case '设置每页显示条数' :
				var url = basePath
						+ "ea/documentflow/ea_getSealDocList.jspa?finishType=seal&search="+search+"&date="
						+ new Date();
				numback(url);
				break;
			case '申请印章' :
				open("http://seal.impf2010.com:8816/ZoomSeal");
				break;
			case '下载签章工具' :
				open(basePath + "page/ea/main/office_ea/document/loadTool.jsp");
				break;

		}
	}
	$("#tosearch").click(function() {
		$("#searchForm #finishType").val("seal");
		$("#searchForm").attr(
				"action",
				basePath + "ea/documentflow/ea_toSearchseal.jspa?pageNumber="
						+ pNumber + "&date=" + new Date());
		document.searchForm.submit.click();
		token = 2;
	});
	$(".wspdoc tr").click(function() {
				docId = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	$(".wspdoc tr").dblclick(function() {
				docId = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				action("查看详情");
			});
			
		// 提交审批通过转去盖章
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
	//	if (confirm("确定提交？")) {
			$("#SendForm #docId").val(docId);
           
			$("#SendForm").attr("target", "hidden").attr(
					"action",
					basePath + "ea/documentflow/ea_sealDocument.jspa?date="
							+ new Date());
			document.SendForm.submit.click();
			token = 2;
		//}

	});

});

function re_load() {
	if (token) {
		document.location.href = basePath
				+ "ea/documentflow/ea_getSealDocList.jspa?finishType=seal";

	}
}

// 点击查看office
function ViewOffice(docPath, fileType, fileShowNameField, fileId) {
	var sealName = $("#sealerName").val();
	var formalNum = $("#formalNum").text();
	window.open(basePath + "page/ea/main/office_ea/document/word4.jsp?docPath="
			+ docPath + "&fileType=" + fileType + "&fileShowNameField="
			+ fileShowNameField + "&userName=" + sealName+"&fileId="+fileId
			+"&formalNum="+encodeURI(formalNum));
}
