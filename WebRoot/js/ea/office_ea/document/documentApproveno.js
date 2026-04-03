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
				title : '未审批',
				minheight : 349,
				buttons : [{
							name : '查看详情',
							bclass : 'edit',
							onpress : action
						},{
							separator : true
						},{
							name : '查看附件',
							bclass : 'attach',
							onpress : action
						},{
							separator : true
						},{
							name : '不予通过',
							bclass : 'noapprove',
							onpress : action
						},{
							separator : true
						},{
							name : '返回修改',
							bclass : 'reject',
							onpress : action
						},{
							separator : true
						},{
							name : '转自己盖章签字',
							bclass : 'seal',
							onpress : action
						},{
							name : '转他人盖章签字',
							bclass : 'seal',
							onpress : action
						},{
							separator : true
						}, {
							name : '转交审批',
							bclass : 'examine',
							onpress : action
						},{
							separator : true
						},{
							name : '转移位置',
							bclass : 'transfer',
							onpress : action
						}, {
							separator : true
						}, {
							name : '审批跟踪登记表',
							bclass : 'excel',
							onpress : action
								// 当点击调用方法
							}, {
							separator : true
						},{
							name : '查询',
							bclass : 'mysearch',
							onpress : action
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
						+ docId + "&type=toApprovenoView";
				break;
			case '查看附件' :
				if (docId == "") {
					alert('请选择!');
					return;
				}
				OpenOffice(docId,"2","未审批");
				break;
		    case '不予通过' :
				if (docId == "") {
					alert('请选择!');
					return;
				}
			//	if(confirm("确定不予通过？")){
					 examine(docId,"noapprove");
			//	}
               
				
				break;
				
			case '返回修改' :
				if (docId == "") {
					alert('请选择!');
					return;
				}
				//if(confirm("确定返回修改？")){
				examine(docId,"reject");
				//}
				break;
			case '转自己盖章签字' :
				if (docId == "") {
					alert('请选择!');
					return;
				}
				$("#jqModelseal").jqmShow();


				break;	
			case '转他人盖章签字' :
				if (docId == "") {
					alert('请选择!');
					return;
				}
				Approve("toSeal");
				break;
			case '转交审批' :
				if (docId == "") {
					alert('请选择!');
					return;
				}
				Approve("toNext");
				break;
			case '转移位置' :
				if (docId == "") {
					alert("请选择");
					return;
				}
				$("#positionForm #changeid").val(docId+",");
				$("#positionForm #type1").val("approveno");
				$("#jqModelPosition").jqmShow();

				break;
			case '查询' :

				showQueryContent("examine");

				break;
			 case '审批跟踪登记表':
					document.location.href=basePath+"/ea/documentflow/ea_getExamineDocList.jspa?finishType=examine&track=00";
					break;
			case '导出' :
				url = basePath + "/ea/documentflow/ea_showExcel.jspa?search="
						+ search+"&finishType=examine&date="
						+ new Date();;
				open(url);
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/documentflow/ea_getExamineDocList.jspa?finishType=examine&search="+search+"&date="
						+ new Date();
				numback(url);
				break;

		}
	}
	
	
	$(".confirm").click(function(){
		examine(docId,"selftoSeal");
		$("#jqModelseal").jqmHide();
		
	});
	
    $(".cancel").click(function(){
    	
    	$("#jqModelseal").jqmHide();
		
	});

	$("#tosearch").click(function() {
		$("#searchForm #finishType").val("examine");

		$("#searchForm").attr(
				"action",
				basePath + "ea/documentflow/ea_toSearchexamine.jspa?pageNumber="
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
		var submitType = $("#jqModelSend #submitType").val();
	//	if (confirm("确定提交？")) {

			$("#SendForm #docId").val(docId);
			if (submitType == "toSeal") {
				$("#jump").val("approve");
			} else {
				$("#jump").val("transfer");
			}
			$("#SendForm").attr("target", "hidden").attr(
					"action",
					basePath + "ea/documentflow/ea_examineDocument.jspa?date="
							+ new Date());
			document.SendForm.submit.click();
			token = 2;
	//	}

	});

});

function re_load() {
	if (token) {
		document.location.href = basePath
				+ "ea/documentflow/ea_getExamineDocList.jspa?finishType=examine";

	}
}

// 点击查看office
function ViewOffice(docPath, fileType, fileShowNameField,fileId) {
	var subscriberName = $("#subscriberName").text();
	window.open(basePath + "page/ea/main/office_ea/document/word2.jsp?docPath="
			+ docPath + "&fileType=" + fileType + "&fileShowNameField="
			+ fileShowNameField + "&subscriberName=" + subscriberName+"&fileId="+fileId);

}

function examine(docId, type) {
	$("#examineForm #docID").val(docId);
	$("#examineForm #jump2").val(type);
	$("#examineForm").attr("target", "hidden").attr(
			"action",
			basePath + "ea/documentflow/ea_examineDocument.jspa?date="
					+ new Date());
	document.examineForm.submit.click();
	token = 2;

}

// 点击转去盖章/转交下个人审批 toSeal/toNext
function Approve(type) {
	$("#jqModelSend").jqmShow();
	document.SendForm.reset();
	getAllCompanyOfGroup();
	$("#jqModelSend #submitType").val(type);
	if (type == "toSeal") {
		$("#SendForm #titlem").text("选择人员——转去盖章");
	} else {
		$("#SendForm #titlem").text("选择人员——转交审批");
	}

}
