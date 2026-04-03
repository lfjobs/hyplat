$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	
	var query ="<form method='post' name='SearchForm' id='SearchForm'><input type='submit' style='display:none;' name='submit' /><input type='hidden' name='search' value='search'/>待出纳审核列表"	
		+ "<table id='SearchTable'><tr>"
		+ "<td align='right'>&nbsp;&nbsp;项目名称：<input type='text' style=\"width: 100px\" id='xmname' name='cashierBillsVO.projectName'/>" 
		+ "&nbsp;&nbsp;黏贴单编号：<input id='journalNum' style=\"width: 100px\" name='cashierBillsVO.journalNum' />" 
		/*
		 * + "&nbsp;&nbsp;部门：<select name='cashierBillsVO.departmentID'
		 * style=\"width: 110px\" id='departmentID'><option value=''>请选择</option></select>"
		 */
		+ "&nbsp;&nbsp;责任人：<input id='staffname' style=\"width: 50px\" name='cashierBillsVO.staffname' />" +
		"&nbsp;&nbsp;单据类别：<select name='cashierBillsVO.billsType' style=\"width: 85px;hight: 50px\">" +
				"<option value='' selected='selected'>全部</option>" 
	    + "<option value='收款单'>收款单</option><option value='支款单'>支款单</option><option value='欠款单'>欠款单</option>" +
	    	"<option value='销售明细单'>销售明细单</option><option value='项目收入预算单'>项目收入预算单</option>" +
	    	"<option value='项目支出预算单'>项目支出预算单</option><option value='采购单'>采购单</option>" +
	    	"<option value='验货单'>验货单</option><option value='采购入库单'>采购入库单</option><option value='销售出库单'>销售出库单</option></select>"+
		/*"&nbsp;&nbsp;制单人：<input type='text' style=\"width: 50px\" id='inputs' name='cashierBillsVO.input'/>" +*/
		"&nbsp;&nbsp;制单日期：<input id='sdate' name='sdate' onfocus='\date(this);\' style='width: 85px' />至<input id='edate' name='edate' onfocus='\date(this);\' style='width: 85px' />" 
		
		/*
		 * + "&nbsp;&nbsp;单据类别:<select name='cashierBillsVO.billsType'
		 * id='btype'></select>"
		 */
	    /*+ "<option value='04'>待主管审核</option><option value='05'>待会计审核</option><option value='06'>待出纳审核</option>" 
		+ "<option value='07'>已审核</option><option value='11'>驳回待修改</option><option value='09'>未确认收款</option>" 
		+ "<option value='30'>未审核作废</option></select>"*/ +
				"&nbsp;&nbsp;<input class=\"input-button\"type='button' style=\"margin:0px;margin-left:5px;\" value=' 查询 ' id='tosearch'/></td>"
		+ "<tr><td></table>"
		/*
		 * + "&nbsp;<input class=\"input-button\"type='button'
		 * style=\"margin:0px;margin-left:5px;margin-top:11px;\" id='refress'
		 * value=' 刷新 '/></td></tr>";
		 */
		+ "</form>";
	
	
	$('.flexme11').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : query,
				minheight : 80,
				buttons : [{
					name : '驳回',
					bclass : 'delete',
					onpress : action
						// 当点击调用方法
				},{
					separator : true
				}, {
					name : '查看',
					bclass : 'edit',
					onpress : action // 当点击调用方法
				},{
					separator : true
				}, {
					name : '审核通过',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
				},{
					separator : true
				},{
					name : '审核记录',
					bclass : 'mysearch',
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
				},{
					name : '设置每页显示条数',
					bclass : 'mysearch',
					onpress : action
					// 当点击调用方法
				}, {
					separator : true
				},{
					name : '查看历史数据',
					bclass : 'mysearch',
					onpress : action
				// 当点击调用方法
				}, {
					separator : true
				}]
			});

	function action(com, grid) {
		switch (com) {
			case '驳回' :
				if (history == "history") {
					alert("历史数据不可更改");
					return;
				}
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
					return
				}
				if(str=="00"){
					alert("拨款自动生成单据不能驳回！");
					return;
				}
				status="10";
				$("#jqModelSend2").jqmShow();
				document.SendForm2.reset();
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
				/*
				 * document.location.href = basePath +
				 * "ea/cashier/ea_toCashier.jspa?pageNumber=" + pNumber +
				 * "&cashierBills.cashierBillsID=" + cashierBillsID +
				 * "&history=" + history + "&sdate=" + sdate + "&edate=" + edate +
				 * "&search=" + search+
				 * "&checkOutUrl=%2Fea%2Fcashier%2Fea_getCashierList.jspa%3FpageNumber%3D" +
				 * pNumber + "%26pageForm.pageNumber%3D" +
				 * $("#pageNumber").attr("value") + "%26history%3D" + history +
				 * "%26sdate%3D" + sdate + "%26edate%3D" + edate;
				 */
				window.open(basePath
						+ "ea/responsible/ea_toSaveResponsible.jspa?pageNumber="
						+ pNumber + "&cashierBills.cashierBillsID="
						+ cashierBillsID + "&sdate=" + sdate + "&edate="
						+ edate+"&checkOutUrl=%2Fea%2Fresponsible%2Fea_getResponsibleList.jspa%3FpageNumber%3D" + pNumber
						+ "%26pageForm.pageNumber%3D" + $("#pageNumber").attr("value")
						+ "%26sdate%3D" + sdate + "%26edate%3D" + edate);
				break;
			case '审核通过' :
				$("#shyj").css("height","220px");
				$("#fl").hide();
				$("#sq").hide();
				$("#xs").hide();
				if (history == "history") {
					alert("历史数据不可更改");
					return;
				} 
				cashierBillsID="";
				var num=0;
				$("[name='chkMsgId23']").each(function() {
					if ($(this).is(':checked')) {
						cashierBillsID += $(this).val() + ",";
						if(($("#"+$(this).val()).find("#bType").val()=="欠款单"
							||$("#"+$(this).val()).find("#bType").val()=="收款单")
							&&$("#"+$(this).val()).find("#statusbill").val()=="02"){
							$("#sq").show();
							num+=1;
						}
						if($("#"+cashierBillsID).find("#bType").val()=="销售明细单"
							&&$("#"+cashierBillsID).find("#statusbill").val()=="01"){
							$("#xs").show();
							num+=1;
						}
					}
				});
				if (cashierBillsID == "" || cashierBillsID.length == 0) {
					alert('请选择');
					return;
				}
				
				if(num>0){
					if(num=1){
						$("#shyj").css("height","290px");
					}else{
						$("#shyj").css("height","320px");
					}
					$("#fl").show();
				}
				
				status="07";
				$("#shyj").jqmShow();
				document.SendForm2.reset();
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
				+ "ea/cashier/sajax_ea_AjxaGetCheckbill.jspa?cashierBillsID="+cashierBillsID+"&date="
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
			
						// var pageForm = member.pageForm;
						var checkList = member.checkList;
						/*
						 * if (pageForm == null) { alert("没有数据"); notoken = 0;
						 * return; } var dqy = pageForm.pageNumber;// 当前页 var
						 * zys = pageForm.pageCount;// 总页数 if (dqy > 1) {
						 * $("#grsy").attr("title", dqy - 1); } if (dqy < zys) {
						 * $("#grxy").attr("title", dqy + 1); }
						 * $("span#grzycount").text(zys);
						 */
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
							// alert("数据加载成功");
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
			case '导出' :
				url = basePath + "ea/cashier/ea_showExcel.jspa?search="
						+ search + "&history=" + history + "&sdate=" + sdate
						+ "&edate=" + edate;
				open(url);
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/cashier/ea_getCashierList.jspa?search=" + search
						+ "&history=" + history + "&sdate=" + sdate 
						+ "&edate=" + edate;
				numback(url);
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				$("input#journalNum").focus();
				break;
			case '查看历史数据' :
				document.location.href = basePath
						+ "ea/cashier/ea_getCashierList.jspa?pageNumber="
						+ pNumber + "&history=history";
				break;
		}
	}
	
	$("#tosearch").click(function() {
	var opt=$("#btype").find("option:selected").text();
		if(opt=="请选择"){
			$("#btype").find("option:selected").attr("value","");
		}else{
			var leng=opt.length;
			var num=$("#btype").find("option:selected").text().indexOf("├");
			$("#btype").find("option:selected").attr("value",$("#btype").find("option:selected").text().substr(num+1,leng));
		}
		$("#SearchForm").attr("action",
				basePath + "ea/cashier/ea_toSearch.jspa?pageNumber=" + pNumber);
		document.SearchForm.submit.click();
	});
	
	$(".flexme11 tr[id]").dblclick(function() {
		$("input.JQuerypersonvalue", $(this))
				.attr("checked", "checked");
		cashierBillsID = this.id;
		action("查看");
	});


	$(".chx").click(function() {
		var b = $(this).attr("checked");
		$(this).attr("checked", !b);
	});

	$(".flexme11 tr[id]").click(function() {
		var d = $("input.chx", $(this)).attr("checked");
		$("input.chx", $(this)).attr("checked", !d);
	});
	
	$("#submitResult2").click(function(){
		if ($.trim($("#SendForm2 #comments").val()) == "") {
			alert("请填写审核意见");
			return;
		}
		$(".ckTextLength").trigger("blur");
		if ($("#SendForm .error").length) {
			notoken = 0;
			return;
		}
		
		$form = $("#SendForm2");
		if (confirm("确定执行该操作？")) {
			$form
					.attr("target", "hidden")
					.attr(
							"action",
							basePath
									+ "ea/cashier/ea_updateCashier.jspa?search="
									+ search + "&pageNumber=" + pNumber);
			$("input#status1").val(status);
			$form.find("input#cashierBillsID").val(cashierBillsID);
			var str=cashierBillsID.split(',');
			for(var s=0;s<str.length;s++){
				if(str[s]!=null&&str[s]!=""){
					$("tr#" + str[s]).remove();
				}
			}
			cashierBillsID = "";
			token = 12;
			document.SendForm2.submit.click();
			
		}
	});

	// /////////////////////////////////////
	var treeName = treenames;
	// var treePID = parent.frames["leftFrame"].tree.getParentId(treeID);
	// var treePName = parent.frames["leftFrame"].tree.getItemText(treePID);
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

					var data1 = new Array();
					data1[0] = {
						id : treeID,
						pid : '-1',
						text : treeName
					};
					for (var i = 0; i < oList.length; i++) {
						data1[i + 1] = {
							id : oList[i].organizationID,
							pid : oList[i].organizationPID,
							text : oList[i].organizationName
						};
					}
/**
 * function TreeSelector(item, data, rootId){ this._data = data; this._item =
 * item; this._rootId = rootId; }
 * 
 * TreeSelector.prototype.createTree = function(){ var len = this._data.length;
 * for (var i = 0; i < len; i++) { if (this._data[i].pid == this._rootId) {
 * this._item.options.add(new Option("" + this._data[i].text,
 * this._data[i].id)); for (var j = 0; j < len; j++) { this.createSubOption(len,
 * this._data[i], this._data[j]); } } } }
 * 
 * TreeSelector.prototype.createSubOption = function(len, current, next){ var
 * blank = ' '; if (next.pid == current.id) { intLevel = 0; var intlvl =
 * this.getLevel(this._data, this._rootId, current); for(var a = 0; a < intlvl;
 * a++ ) blank += " "; blank += "├"; this._item.options.add(new Option(blank +
 * next.text, next.id)); for (var j = 0; j < len; j++) {
 * this.createSubOption(len, next, this._data[j]); } } }
 * TreeSelector.prototype.getLevel = function(datasources, topId, currentitem){
 * var pid = currentitem.pid; if (pid != topId) { for (var i = 0; i <
 * datasources.length; i++) { if (datasources[i].id == pid) { intLevel++;
 * this.getLevel(datasources, topId, datasources[i]); } } } return intLevel; }
 */
					$t = $("table#SearchTable");

					var ts3 = new TreeSelector(
							$t.find("select#departmentID")[0], data1, -1);
					ts3.createTree();

				// var s = "2010-9-9";
					// var d = new Date(Date.parse(s.replace(/-/g, "/")));
				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}
			});
			
			
			var treeid = 'scode20101101dfs3uhdprp0000000029'; // 单据类别
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

		// 为弹出框准备下拉表内容
		/*
		 * var url =
		 * basePath+"ea/ccode/sajax_ea_getListCCodeByPID.jspa?codeID=scode20101101dfs3uhdprp0000000029&date="+new
		 * Date().toLocaleString(); $.ajax({ url: encodeURI(url), type: "get",
		 * async: true, dataType: "json", success: function cbf(data){ var
		 * member = eval("(" + data + ")"); var nologin = member.nologin;
		 * if(nologin){ document.location.href
		 * =basePath+"page/ea/not_login.jsp"; } var codeList = member.codeList;
		 * for(var i = 0;i<codeList.length;i++){ $op = $("<option/>");
		 * $op.val(codeList[i].codeValue); $op.text(codeList[i].codeValue);
		 * $("#bills[name]").append($op); } }, error: function cbf(data){
		 * alert("数据获取失败！") } });
		 */
});
function re_load() {
	document.location.href = basePath
			+ "ea/cashier/ea_getCashierList.jspa?pageNumber=" + pNumber
			+ "&pageForm.pageNumber=" + $("#pageNumber").attr("value")
			+ "&history=" + history + "&sdate=" + sdate + "&edate=" + edate;
}
function fj(cID) {
	var statusfj = $("tr#" + cID).find("span#status").text();
	if (statusfj != '04') {
		alert("已经归档不能修改附件");
		return;
	}
	window.open(basePath
			+ "ea/uploadfile/ea_getListImage.jspa?loadFile.parmeterID=" + cID);
}
