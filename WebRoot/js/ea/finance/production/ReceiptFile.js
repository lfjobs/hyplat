$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	$(".jqmreturn").click(function() {
				notoken = 0;
				$("#jqmAdd").jqmHide();
				showDocument = false;
			});
	$('.flexme11').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : "部门单据归档",
				minheight : 80,
				buttons : [{
					name : '数据归档',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
				}, {
					separator : true
				},{
					name : '查询',
					bclass : 'mysearch',
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
					name : '驳回',
					bclass : 'delete',
					onpress : action
						// 当点击调用方法
				}, {
					separator : true
				}, {
					name : '审核记录',
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

	function action(com, grid) {
		switch (com) {
			case '数据归档':
				cashierBillsID="";
				$("[name='chkMsgId23']").each(function() {
					if ($(this).is(':checked')) {
						cashierBillsID += $(this).val() + ",";
					}
				});
				if (cashierBillsID == "" || cashierBillsID.length == 0) {
					alert('请选择');
					return
				}
				$("#billid").val(cashierBillsID);
				$("#guidangForm").attr("target", "hidden")
				.attr(
						"action",
						basePath
								+ "/ea/archivest/ea_toSave1.jspa");
				token = 12;
				document.guidangForm.submit.click();
				break;
			case '驳回':
				var cid="";
				var str="";
				cashierBillsID="";
				$("[name='chkMsgId23']").each(function() {
					if ($(this).is(':checked')) {
						cashierBillsID += $(this).val() + ",";
						cid=$(this).val();
						if($("tr#"+cid).find("#statusbill").attr("value")!=null&&$("tr#"+cid).find("#statusbill").attr("value")=="00"){
							str="00";
						}
					}
				});
				if (cashierBillsID == "" || cashierBillsID.length == 0) {
					alert('请选择');
					return;
				}
				if(str=="00"){
					alert("拨款自动生成单据不能驳回！");
					return;
				}
				$("#CashierBillsform").find("input#status").val("10");
				$("#jqModelSend").jqmShow();
				document.SendForm.reset();
				break;
			case '审核记录' :
				cashierBillsID="";
				$("[name='chkMsgId23']").each(function() {
					if ($(this).is(':checked')) {
						cashierBillsID += $(this).val() + ",";
					}
				});
				if (cashierBillsID == "" || cashierBillsID.length == 0||cashierBillsID.split(",").length>2) {
					alert('请选择单个任务');
					return;
				}
				var searchurl = basePath
				+ "/ea/archivest/sajax_ea_AjxaGetCheckbill.jspa?str="+cashierBillsID+"&date="
							+ new Date().toLocaleString();
				$.ajax({
					url : encodeURI(searchurl),
					type : "get",
					async : true,
					dataType : "json",
					success : function cbf(data) {
						var member = eval("(" + data + ")");
						var nologin = member.nologin;
						if (nologin) {
							document.location.href = basePath + "page/ea/not_login.jsp";
						}
			
						//var pageForm = member.pageForm;
						var checkList = member.checkList;
						/*if (pageForm == null) {
							alert("没有数据");
							notoken = 0;
							return;
						}
						var dqy = pageForm.pageNumber;// 当前页
						var zys = pageForm.pageCount;// 总页数
						if (dqy > 1) {
							$("#grsy").attr("title", dqy - 1);
						}
						if (dqy < zys) {
							$("#grxy").attr("title", dqy + 1);
						}
						$("span#grzycount").text(zys);*/
						if(checkList!=null){
							$("#CheckTable").empty();
							var tabletr="<tr id='checkTitle'>" +
									"<th width='200' align='center'>审核阶段</th>" +
									"<th width='200' align='center'>审核时间</th>" +
									"<th width='70' align='center'>审核人</th>" +
									"<th width='123' align='center'>审核状态</th>" +
									"<th width='123' align='center'>审核意见</th>" +
									"</tr><tr class='shxx'>";
							for (var i = 0; i < checkList.length; i++) {
								var status=checkList[i].auditorstatus=='01'?'未审核':checkList[i].auditorstatus=='02'?'审核通过':'驳回';
								tabletr += "<td id='billtatus' align='center'>"
									+ checkList[i].billtatus + "</td>";
								tabletr += "<td id='audittime' align='center'>"
										+ checkList[i].audittime+"</td>";
								tabletr += "<td id='auditorname' align='center'>"
										+ checkList[i].auditorname + "</td>";
								tabletr += "<td id='auditorstatus' align='center'>"
										+ status + "</td>";
								tabletr += "<td id='comments' align='center'>"
										+ checkList[i].comments + "</td>";
								tabletr += " </tr>";
							}
							$("#CheckTable").append(tabletr);
							//alert("数据加载成功");
							notoken = 0;
							window.status = "数据加载成功";
							$("#jqModelCheck").jqmShow();
						}else{
							alert("该单据没有审核记录！");
						}					
					},
					error : function cbf(data) {
						notoken = 0;
						alert("数据获取失败！");
					}
				});
				break;
			case '查看' :
				cashierBillsID="";
				$("[name='chkMsgId23']").each(function() {
					if ($(this).is(':checked')) {
						cashierBillsID += $(this).val() + ",";
					}
				});
				if (cashierBillsID == "" || cashierBillsID.length == 0||cashierBillsID.split(",").length>2) {
					alert('请选择单个任务');
					return;
				}
				alert(cashierBillsID);
				window.open(basePath
						+ "ea/responsible/ea_toSaveResponsible.jspa?cashierBills.cashierBillsID="
						+ cashierBillsID);
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "/ea/archivest/ea_getArchivesList.jspa?tt=z&search="
						+ search + "&sdate=" + sdate + "&edate=" + edate;
				numback(url);
				break;
		}
	}
	$(".chx").click(function() {
		var b = $(this).attr("checked");
		$(this).attr("checked", !b);
	});

	$(".flexme11 tr[id]").click(function() {
		var d = $("input.chx", $(this)).attr("checked");
		$("input.chx", $(this)).attr("checked", !d);
	});
	
	$("#tosearch").click(function() {
		var opt=$("#btype").find("option:selected").text();
		if(opt=="请选择"){
			$("#btype").find("option:selected").attr("value","");
		}else{
			var leng=opt.length;
			var num=$("#btype").find("option:selected").text().indexOf("├");
			$("#btype").find("option:selected").attr("value",$("#btype").find("option:selected").text().substr(num+1,leng));
		}
		$("#SearchForm")
				.attr(
						"action",
						basePath
								+ "/ea/archivest/ea_getCashierBillsList.jspa?tt=z&pageNumber="
								+ pNumber);
		document.SearchForm.submit.click();
	});
	$(".flexme11 tr[id]").dblclick(function() {
				$("input.chx", $(this))
						.attr("checked", "checked");
				cashierBillsID = this.id;
				action("查看");
	});
	$("#submitResult").click(function(){
		if ($.trim($("#SendForm #comments").val()) == "") {
			alert("请填写审核意见");
			return;
		}
		$(".ckTextLength").trigger("blur");
		if ($("#SendForm .error").length) {
			notoken = 0;
			return;
		 }
		$form = $("#SendForm");
		if (confirm("确定执行该操作？")) {
			$form
					.attr("target", "hidden")
					.attr(
							"action",
							basePath
									+ "/ea/archivest/ea_SaveOrUpdateCheckbill.jspa?");
			$form.find("input#str").val(cashierBillsID);
			var str=cashierBillsID.split(',');
			for(var s=0;s<str.length;s++){
				if(str[s]!=null&&str[s]!=""){
					$("tr#" + str[s]).remove();
				}
			}
			cashierBillsID = "";
			token = 12;
			document.SendForm.submit.click();
		}
	});
	
	var treeid = 'scode20101101dfs3uhdprp0000000029'; //单据类别
	var url = basePath + "ea/ccode/sajax_ea_getAllListCCodeByPID.jspa?codeID="+treeid+"&date="+new Date().toLocaleString(); 
	$.ajax({
		    url:encodeURI(url),
		    type: "get",
			async: true,
			dataType: "json",
			success: function cbf(data){
				var member = eval("(" + data + ")");
                var oList = member.codeList;
                var data2 = new Array();
		        data2[0] = {
	                id: treeid,
	                pid: '-1',
	                text: '请选择'
	            };
                for (var i = 0; i < oList.length; i++) {
                    data2[i + 1] = {
                        id: oList[i].codeID,
                        pid: oList[i].codePID,
                        text: oList[i].codeValue
                    };
                }
               var ts = new TreeSelector($("#btype")[0], data2, -1);
        		ts.createTree();
			},
			error: function cbf(data){
				alert("数据获取失败！");
			}
	});
	
	var treeName =treeNames;

	var url = basePath + "ea/responsibilities/sajax_n_ea_getoList.jspa?date="
			+ new Date().toLocaleString();
	$.ajax({
				url : encodeURI(url),
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
					var oList = member.organizationlist;
					var data = new Array();
					data[0] = {
						id : treeID,
						pid : '-1',
						text : treeName
					};
					for (var i = 0; i < oList.length; i++) {
						data[i + 1] = {
							id : oList[i].organizationID,
							pid : oList[i].organizationPID,
							text : oList[i].organizationName
						};
					}
					$t = $("table#SearchTable");
					var ts = new TreeSelector(
							$t.find("select#departmentID")[0], data, -1);
					ts.createTree();
				},
				error : function cbf(data) {
					// alert("数据获取失败！")
				}
			});
});
		

$(document).ready(function() {
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
			alert("必须添加归档数据");
			notoken = 0;
			return;
		}
		notoken = 1;
		$("#myform").attr("target", "hidden").attr(
				"action",
				basePath + "/ea/archivest/ea_toSave1.jspa?");
		document.myform.submit.click();
		document.myform.reset();
		$("tr.kelong").remove();
		token = 1;
		return;
	});
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
					alert("单据编号为空！");
					yy = false;
				} else {
					typeName = $.trim($("#journalNumAjax").attr("value"));
					$("table#gostable").find("td#journalNum").each(function() {
								if ($.trim($(this).text()) == typeName) {
									alert("该编号单据已加载到列表中！");
									$("#journalNumAjax").attr("value","");
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
				var cashierBillsVO = member.cashierBillsVO;
				if (cashierBillsVO == null) {
					alert("没有数据！");
					$("#journalNumAjax").attr("value","");
					notoken = 0;
					return;
				}
				if(treeID != cashierBillsVO.organizationID){
					alert("单据不是本部门！");
					$("#journalNumAjax").attr("value","");
					notoken = 0;
					return;
				}
				if ( cashierBillsVO.status == '04'
						|| cashierBillsVO.status == '05'
						|| cashierBillsVO.status == '06'
						|| cashierBillsVO.status == '10') {
					alert("未审核通过，请添加其他单据！");
					$("#journalNumAjax").attr("value","");
					notoken = 0;
					return;
				}
				if(cashierBillsVO.status=='20'){
					if(cashierBillsVO.taxstatus=='00'||cashierBillsVO.taxstatus=='01'||cashierBillsVO.taxstatus=='02'||cashierBillsVO.taxstatus=='03'||cashierBillsVO.taxstatus=='10'){
						alert("未审核通过，请添加其他单据！");
						$("#journalNumAjax").attr("value","");
						notoken = 0;
						return;
					}
				}
				if(number>=25){
					alert("一次性最多只能保存25条数据，请添加其他单据！");
					$("#journalNumAjax").attr("value","");
					notoken = 0;
					return;
				}
				var nn=parseInt(member.num);
				if(nn==0){
					uu=number+1;
				}else if(nn>0){
					uu=nn+1+number;
				}
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
				number++;
				docNull+=1;
				alert("数据加载成功！");
				$("#journalNumAjax").attr("value","");
				notoken = 0;
				window.status = "数据加载成功";
			},
			error : function cbf(data) {
				notoken = 0;
				alert("数据获取失败！");
				$("#journalNumAjax").attr("value","");
			}
		});
	}
});
function re_load() {
	document.location.href = basePath
			+ "/ea/archivest/ea_getCashierBillsList.jspa?pageNumber="
			+ pNumber + "&pageForm.pageNumber="	+ $("#pageNumber").attr("value")
			+ "&sdate=" + sdate + "&edate="+ edate+"&search="+ search+"&tt=z";
}

