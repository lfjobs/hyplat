 $(document).ready(function() {
	if(type == 'customer'){
		var len = $(".trclass").length;
		if(len == 0){
			window.parent.document.getElementById("mainframe14").style.height = 80 + "px";
		}else{
			window.parent.document.getElementById("mainframe14").style.height = 102 + len * 27 + "px";
		}
	}
	
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	var title = "";
	if(type != "customer"){
		if (type == "group") {
			title = "接待个人记录集团汇总";
		} else {
			if(telcodetype=='0'){
			   title = "座机信息处理中心";
			}else{
			   title = "手机信息处理中心";
			}
		}
	}
	
	if(type == 'customer'){
		$('.JQueryflexme').flexigrid({
			height : 240,
			allDouble : true,
			width : 'auto',
			striped:false,
			minwidth : 30,
			title : title,
			minheight : 80,
			buttons : [{
				name : '设置每页显示条数',
				bclass : 'mysearch',
				onpress : action // 当点击调用方法
				}, {
				separator : true
			}]
		});
	}else{
		$('.JQueryflexme').flexigrid({
			height : 240,
			width : 'auto',
			striped:false,
			minwidth : 30,
			title : title,
			minheight : 80,
			buttons : [{
				name : '查询',
				bclass : 'mysearch',
				onpress : action
					// 当点击调用方法
				}, {
				separator : true
			}, {
				name : '打印',
				bclass : 'mysearch',
				onpress : action
					// 当点击调用方法
				}, {
				separator : true
			}, {
				name : '问题处理',
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
			case '删除' :
				if (telMessageID == "") {
					alert('请选择接待个人记录');
					return;
				}
				if (confirm("是否删除？")) {
					$f = $('#cstaffForm');
					$f.find('#id').val(telMessageID);
					var url = basePath
							+ "ea/tel/tel_delVisit.jspa?visitRecord.id="
							+ telMessageID + "&pageNumber=" + pNumber;
					$f.attr("target", "hidden").attr("action", url);
					telMessageID = "";
					document.cstaffForm.submit.click();
					token = 2;
				}
				break;
			/*case '查看' :
				if (telMessageID == "") {
					alert('请选择接待个人记录');
					return
				}
				document.cstaffForm.reset();
				lookMSG(telMessageID);
				break;*/
			case '查询' :
			case '打印':
				$("#jqModelSearch").jqmShow();
				break;
			case '问题处理' :
				if (telMessageID == "") {
					alert('请选择记录');
					return
				}
				
				if(telMessageID.indexOf("telOut") > -1 ){
					alert("打出电话信息暂时不支持问题处理。");
					return false;
				}

				var deal = $.trim($("#" + telMessageID).children(".isDeal")
						.text());
				if (deal == "是") {
					$("#btnDeal").hide();
					$("#dealComment").show();
				} else {
					$("#dealComment").show();
					$("#btnDeal").show();
				}
				lookMSG(telMessageID);
				var dealContents = $("#" + telMessageID).children(".dealContent").text();
				$("#dealContent").text($.trim(dealContents));
				$("#dealId").val($("#" + telMessageID).attr("data-dealid"));
				break;
			/*
			 * case '导出': url =
			 * basePath+"ea/telmessage/ea_showTelMessageExcel.jspa?search="+search;
			 * open(url); break;
			 */
			case '设置每页显示条数' :
				var pageSize = prompt("输入显示条数", "请输入小于50正整数");
				if (pageSize < 0 || pageSize != parseInt(pageSize)
						|| pageSize > 50) {
					alert("请输入小于50的正整数");
					return;
				}
				var url = basePath + "ea/tel/tel_infoDealCenter.jspa?search="
						+ search + "&type=" + type + "&pageSize="
						+ pageSize + "&staffID=" + staffID+"&telcodetype="+telcodetype;
				window.location.href = url;
				break;
		}
	}

	$("#btnDeal").click(function() { 
		var dealContent = $("#dealContent").val();
		var dealId = $("dealId").val() | "";
		var dealuser = $("#receiverID option:selected").text();
		var companyName = $("#receiverCompanyID option:selected").text();
		if(dealuser.indexOf("请选择") < 0){
		    $("#dealuser").val(dealuser);
		    $("#companyName").val(companyName);
		}
		if(dealContent==""){
			alert("请填写处理内容");
			return;
		}

		if (dealContent) {
			
			$f = $('#cstaffForm');
			var url = basePath + "ea/tel/tel_dealTel.jspa?pageNumber=" + pNumber;

			$f.attr("target", "hidden").attr("action", url);
			telMessageID = "";
			document.cstaffForm.submit.click();
			token = 2;
		}
	});
	
	$(".JQueryflexme tr[id]").click(function() {
				telMessageID = this.id; 
				$("#telInRecordId").val(telMessageID); 
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	$(".JQueryreturn").click(function() {
				$("#jqModel").jqmHide();
			});

	// 查询相关操作
	$("#searchStaff").click(function() {
		var begin = $("#beginDate").val();
		var to = $("#endDate").val();
		var DateFrom = "";
		var DataTo = "";
        $("#staffKey").val($("#receiverID1 option:selected").attr("rvalue")); 
		// 日期校验
		if (begin) {
			var t = begin.split("-");
			DateFrom = new Date(t[0], t[1], t[2]);
		}

		if (to) {
			var t = to.split("-");
			DataTo = new Date(t[0], t[1], t[2]);
		}

		if (begin && to && (DateFrom >= DataTo)) {
			alert("请选择正确的时间段。");
			return;
		}

		$("#postSearchForm")
				.attr(
						"action",
						basePath + "ea/tel/tel_searchInfoDeal.jspa?pageNumber="
								+ pNumber).attr("target","_self");
		$("#search").val("search");
		//$("#telcodetype").val(getcookie("telcodetype"));
		document.postSearchForm.submit.click();
		$("#postSearchForm").find(":input[name]").val("");
	});
	//打印报表
	$("#toPrint").click(function() {
		var begin = $("#beginDate").val();
		var to = $("#endDate").val();
		var DateFrom = "";
		var DataTo = "";
		$("#staffKey").val($("#receiverID1 option:selected").attr("rvalue"));
		// 日期校验
		if (begin) {
			var t = begin.split("-");
			DateFrom = new Date(t[0], t[1], t[2]);
		}

		if (to) {
			var t = to.split("-");
			DataTo = new Date(t[0], t[1], t[2]);
		}

		if (begin && to && (DateFrom >= DataTo)) {
			alert("请选择正确的时间段。");
			return;
		}
		$("#search").val("search");
		//$("#telcodetype").val(getcookie("telcodetype"));
		
		$("#postSearchForm").attr("action",basePath + "ea/tel/tel_toPrint.jspa").attr("target","_blank");
		document.postSearchForm.submit.click();
		$("#postSearchForm").find(":input[name]").val("");
	});
});
function re_load() { 
	window.location.reload(); 
}

function lookMSG(msgID) {
	$temp = $("tr#" + msgID);
	$("#telInRecordDeal").val(msgID);
	$("#recordType").val($.trim($temp.find(".recodeType > div").text()));
	$("#customName").val($.trim($temp.find(".customName > div").text()));
	$("#customTel").val($.trim($temp.find(".customTel > div").text()));
	$("#dealDate").val($.trim($temp.find(".dealDate > div").text()));
	$("#recordContent").val($.trim($temp.find(".recordContent > div").text()));
	if($.trim($temp.find(".isDeal > div").text()) == "是"){
		$("#btnDeal").hide();	
	}else{
		$("#btnDeal").show();
	}
	$("#jqModel").jqmShow();
}