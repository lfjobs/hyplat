$(document).ready(function() {

	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	$('.draft0').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : '已发送',
				minheight : 350,
				buttons : [{
					name : '查看',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '查看附件',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '转发',
					bclass : 'transmit',
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
					name : '放入回收站',
					bclass : 'litter',
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
			case '查看' :
				if (docId == "") {
					alert('请选择!');
					return;
				}
				window.location.href = basePath
						+ "ea/documentinfo/ea_getViewDocument.jspa?docId="
						+ docId + "&type=toFinishedView&date=" + new Date();

				break;
				
			case '查看附件' :
				
				if (docId == "") {
					alert('请选择!');
					return;
				}
				
				OpenOffice(docId,"1","已发送");
				break;
			case '转发' :
				if (docId == "") {
					alert('请选择!');
					return;
				}

				window.location.href = basePath
						+ "ea/documentinfo/ea_transmitSendDoc.jspa?docId="
						+ docId + "&date=" + new Date();

				break;
			case '放入回收站' :
				if (docId == "") {
					alert("请选择");
					return;
				}
				//if (confirm("确定放入回收站？")) {
					putRecycleBin(docId, "reject");
			//	}
				break;
			case '查询' :
				showQueryContent("yessend");
				break;

			case '查看评论' :
				if (docId == "") {
					alert('请选择！');
					return
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
								str += "<tr><td align=\"left\"><a href='#' style='text-decoration:none;'>"
										+ obj.senderName
										+ ":</a>&nbsp;&nbsp;&nbsp;"
										+ obj.suggestion
										+ "</td></tr><tr><td align = \"left\">"
										+ obj.sugTimestr
										+ "</td></tr><tr rowspan='2'><td>&nbsp;&nbsp;</td></tr>";
							}
							$("#tablesug").html(str);
						} else {
							$("#tablesug").html("<tr><td>暂无评论</td></tr>");
						}

					},
					error : function(data) {
						alert("数据获取失败！");
					}
				});
				$("#jqModelSugg").jqmShow();
				$("#htt").hide();

				break;
			case '审批跟踪登记表':
				document.location.href=basePath+"ea/documentinfo/ea_getSendedDocList.jspa?track=00&searchType=yessend";
				break;
			 case '导出' :
				url = basePath + "/ea/documentinfo/ea_showExcel.jspa?search="
						+ search+"&searchType=yessend&date="
						+ new Date();;
				open(url);
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/documentinfo/ea_getSendedDocList.jspa?search="+search+"&searchType="+searchType+"&date="
						+ new Date();
				numback(url);
				break;

		}
	}
	$(".close").click(function() {
				$("#htt").show();
			});

	$("#tosearch").click(function() {
		$("#searchForm #searchType").val("yessend");
		$("#searchForm").attr(
				"action",
				basePath + "ea/documentinfo/ea_toSearch.jspa?pageNumber="
						+ pNumber + "&date=" + new Date());
		document.searchForm.submit.click();
	});
	$(".draft0 tr").click(function() {
				docId = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	$(".draft0 tr").dblclick(function() {
				docId = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				action("查看");
			});

});
function re_load() {
	if (token) {
		document.location.href = basePath
				+ "ea/documentinfo/ea_getSendedDocList.jspa";

	}
}
