$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close')// 添加触发关闭的selector
	$(".jqmreturn").click(function() {
				notoken = 0;
				$("#jqmAdd").jqmHide();
				showDocument = false;
			})
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
				}, {
					name : '修改',
					bclass : 'edit',
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
					name : '生成报表',
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
				$("#jqmAdd").jqmShow();
				$("input#journalNumAjax").focus();
				showDocument = true;
				break;
			case '修改':
				if (cashierBillsID == "") {
					alert("请选择！");
					return;
				}
				document.location.href = basePath
						+ "/ea/archivest/ea_toCashier.jspa?tt=z&pageNumber="
						+ pNumber + "&historyCashierBills.cashierBillsID="
						+ cashierBillsID + "&search=" + search
						+ "&pagepageNumber=" + $("#pageNumber").attr("value");
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
			case '生成报表' :
				if(sdate==''||edate==''){
					alert("请先查询在生成报表！");
					return;
				}
				window.open(basePath + "/ea/archivest/ea_toStatements.jspa?tt=z&search="
						+ search + "&sdate=" + sdate + "&edate=" + edate);
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "/ea/archivest/ea_getArchivesList.jspa?tt=z&search="
						+ search + "&sdate=" + sdate + "&edate=" + edate;
				numback(url);
				break;
		}
	}
	$(".flexme11 tr[id]").click(function() {
				cashierBillsID = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			})
	$("#tosearch").click(function() {
		$("#SearchForm")
				.attr(
						"action",
						basePath
								+ "/ea/archivest/ea_toSearch.jspa?tt=z&pageNumber="
								+ pNumber);
		document.SearchForm.submit.click();
	});
	$(".flexme11 tr[id]").dblclick(function() {
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				cashierBillsID = this.id;
				action("查看");
			})
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
	})
	
	// 这一行的单击事件
	$("table#gostable tr[id]").live("click", function(event) {
				cashierBillsID = this.id;
				$("input.ra", $(this)).attr("checked", "checked");
			})
	//保存
	$("input#save").click(function() {
		if (notoken) {
			alert("正在提交数据！请稍等")
			return;
		}
		if(docNull==0){
			alert("必须添加归档数据")
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
	})
	document.onkeydown = function(evt) {// 捕捉回车 根据激光扫描枪查询单据
		evt = (evt) ? evt : ((window.event) ? window.event : "") // 兼容IE和Firefox获得keyBoardEvent对象
		var key = evt.keyCode ? evt.keyCode : evt.which; // 兼容IE和Firefox获得keyBoardEvent对象的键值
		if (key == 13 && showDocument == true) { // 判断是否是回车事件与是否打开扫描窗口。
			$("input#add").trigger("click");
		}
	}
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
							})
				}
				if (yy) {
					cxwldw("cashierBillsVO.journalNum=" + typeName);
				}
			})
	// ajax查询往来单位列表
	function cxwldw(typeCN) {
		if (notoken) {
			alert("正在获取数据！请稍等")
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
				alert("数据获取失败！")
				$("#journalNumAjax").attr("value","");
			}
		});
	}
	$(function() {
		var treeName = parent.frames["left_tree"].tree.getItemText(treeID);
		var treePID = parent.frames["left_tree"].tree.getParentId(treeID);
		var treePName = parent.frames["left_tree"].tree.getItemText(treePID);
		var url = basePath
				+ "ea/responsibilities/sajax_n_ea_getoList.jspa?date="
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
					document.location.href = basePath + "page/ea/not_login.jsp";
				}
				var oList = member.organizationlist;

				var data1 = new Array();
				data1[0] = {
					id : treePID,
					pid : '-1',
					text : treePName
				};
				for (var i = 0; i < oList.length; i++) {
					data1[i + 1] = {
						id : oList[i].organizationID,
						pid : oList[i].organizationPID,
						text : oList[i].organizationName
					};
				}
				$t = $("table#SearchTable");

				var ts3 = new TreeSelector($("#departmentID")[0], data1, -1);
				ts3.createTree();
			},
			error : function cbf(data) {
				alert("数据获取失败！")
			}
		});

	})
})
function re_load() {
	document.location.href = basePath
			+ "/ea/archivest/ea_getArchivesList.jspa?pageNumber="
			+ pNumber + "&pageForm.pageNumber="	+ $("#pageNumber").attr("value")
			+ "&sdate=" + sdate + "&edate="+ edate+"&search="+ search+"&tt=z";
}

