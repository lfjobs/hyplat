$(document).ready(function() {
	$("input#journalNumAjax").focus();
	$(".JQueryClose").click(function() {
				notoken = 0;
				re_load();
			});
	
	
	// 删除
	$("#delButton").click(function() {
				if (cashierBillsID == "") {
					alert("请选择！");
					return;
				}
				$("tr#"+cashierBillsID).remove();
				docNull-=1;
	});
	
	// 这一行的单击事件
	$("table#gostable tr[id]").live("click", function(event) {
				cashierBillsID = this.id;
				$("input.ra", $(this)).attr("checked", "checked");
			});
	//保存
	$("input#save").click(function() {
		if (notoken) {
			alert("正在提交数据！请稍等");
			return;
		}
		if(docNull==0){
			alert("必须添加预算项目");
			notoken = 0;
			return;
		}
		notoken = 1;
		$("#myform").attr("target", "hidden").attr(
				"action",
				basePath + "/ea/archivest/ea_toSave.jspa?");
		document.myform.submit.click();
		document.myform.reset();
		$("#journalNumAjax").text("");
		$("tr.kelong").remove();
		token = 1;
		return;
	});
			
		
});
$(document).ready(function() {

document.onkeydown = function(evt) {// 捕捉回车 根据激光扫描枪查询单据
		evt = (evt) ? evt : ((window.event) ? window.event : ""); // 兼容IE和Firefox获得keyBoardEvent对象
		var key = evt.keyCode ? evt.keyCode : evt.which; // 兼容IE和Firefox获得keyBoardEvent对象的键值
		if (key == 13 && showDocument == true) { // 判断是否是回车事件与是否打开扫描窗口。
			$("input#add").trigger("click");
		}
	};
	
	// 根据输入的单据编号查询
	$("input#add").click(function() {
				var yy = true;
				var typeName = "";
				if ($.trim($("#journalNumAjax").attr("value")) == "") {
					yy = false;
				} else {
					typeName = $.trim($("#journalNumAjax").attr("value"));
					$("table#gostable").find("td#journalNum").each(function() {
								if ($.trim($(this).text()) == typeName) {
									alert("该编号单据已加载到列表中！");
									yy = false;
								}
							});
				}
				if (yy) {
					cxwldw("cashierBillsVO.journalNum=" + typeName);
				}
			});

		// ajax查询往来单位列表
	function cxwldw(typeCN) {
		if (notoken) {
			alert("正在获取数据！请稍等");
			return;
		}
		notoken = 1;
		var searchurl = basePath
				+ "/ea/archivest/sajax_ea_getAjaxCashierList.jspa?";
		$.ajax({
			url : encodeURI(searchurl + typeCN + "&date="
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
				var nn=parseInt(member.num);
				if(nn==0){
					uu=number+1;
				}else if(nn>0){
					uu=nn+1+number;
				}
				var cashierBillsVO = member.cashierBillsVO;
				var tabletr = "<tr style='cursor: hand;'  id = "
						+ cashierBillsVO.cashierBillsID + " class='kelong'>";
				tabletr += "<td id='checkcc' align='center'><input type ='radio'  class='ra' value="
						+ cashierBillsVO.cashierBillsID
						+ " name='checkradio'/><input name='cashierBillsmap["+number+"].cashierBillsID' value="+cashierBillsVO.cashierBillsID+" style='display:none'/></td>";
				tabletr += "<td id='companyname' align='center'><input id='num' style='border: 0' size='1' value="
						+ uu + " name='cashierBillsmap["+number+"].snumber'/></td>";
				tabletr += "<td id='companyname' align='center'>"
						+ cashierBillsVO.companyname + "</td>";
				tabletr += "<td id='journalNum' align='center'>"
						+ cashierBillsVO.journalNum + "</td>";
				tabletr += "<td id='billsType' align='center'>"
						+ cashierBillsVO.billsType + "</td>";
				tabletr += "<td id='departmentname'  align='center'>"
						+ cashierBillsVO.departmentname + "</td>";
				tabletr += "<td id='staffname' align='center'>"
						+ cashierBillsVO.staffname + "</td>";
				tabletr += "<td id='cashierDate' align='center'>"
						+ cashierBillsVO.cashierDate + "</td>";
				tabletr += "<td id='ccompanyname'  align='center'>"
						+ cashierBillsVO.ccompanyname + "</td>";
				tabletr += " </tr>";
				$("#body_08cu table#gostable").append(tabletr);
				notoken = 0;
				window.status = "数据加载成功";
			},
			error : function cbf(data) {
				notoken = 0;
				alert("数据获取失败！");
			}
		});
	}
});
function re_load() {
	document.location.href = basePath
			+ "/ea/archivest/ea_getArchivesList.jspa?pageNumber="
			+ pNumber + "&pageForm.pageNumber="	+ $("#pageNumber").attr("value")
			+ "&sdate=" + sdate + "&edate="+ edate+"&search="+ search;
}