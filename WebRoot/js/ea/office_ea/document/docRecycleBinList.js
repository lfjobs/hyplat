$(document).ready(function() {

	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	$('.wspdoc').flexigrid({
				height : 330,
				width : 'auto',
				minwidth : 30,
				title : '回收站',
				minheight : 349,
				buttons : [{
					name : '查看',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},{
					name : '查看附件',
					bclass : 'attach',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},  {
					name : '还原',
					bclass : 'restore',
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
				var delId = $("input[name=radioGroup]:radio:checked").val();
				var docId = $("#tablerecyle tr#" + delId).find("input#docId")
						.val();

				if (docId == undefined) {
					alert('请选择!')
					return;
				}
				window.location.href = basePath
						+ "ea/documentinfo/ea_getViewDocument.jspa?docId="
						+ docId + "&type=toFinishedView";
				break;
			break;
			
		case '查看附件' :
					var delId = $("input[name=radioGroup]:radio:checked").val();
				var docId = $("#tablerecyle tr#" + delId).find("input#docId")
						.val();

				if (docId == undefined) {
					alert('请选择!')
					return;
				}
				OpenOffice(docId,"1","已放入回收站");
				break;
		case '还原' :
			var delId = $("input[name=radioGroup]:radio:checked").val();
			var delkey = $("#tablerecyle tr#" + delId).find("input#delkey")
					.val();
			var docId = $("#tablerecyle tr#" + delId).find("input#docId")
						.val();
			if (delkey == undefined) {
				alert('请选择!')
				return;
			}
			if (confirm("确定还原？")) {
				var url = basePath
						+ "ea/documentcommon/sajax_ea_restoreDoc.jspa?date="
						+ new Date();

				$.ajax({
							url : url,
							type : "get",
							async : false,
							dateType : "json",
							data : {
								delkey : delkey,
								docId:docId
								
							},
							success : function(data) {
								alert("操作成功");
								window.location.reload();
							},
							error : function(data) {
								alert("获取数据失败");
							}

						});
			}

			break;
		case '查询' :
			showQueryContent("recycle", "recycle");
			break;

		case '设置每页显示条数' :
			var url = basePath
					+ "ea/documentcommon/ea_getRecycelBinList.jspa?search="
					+ search + "&date=" + new Date();
			numback(url);
			break;

	}
}

	$("#tosearch").click(function() {
		$("#searchForm").attr(
				"action",
				basePath + "ea/documentcommon/ea_toSearch.jspa?pageNumber="
						+ pNumber + "&date=" + new Date());
		document.searchForm.submit.click();
		token = 2;
	});
	$(".wspdoc tr").click(function() {
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	$(".wspdoc tr").dblclick(function() {
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				action("查看");
			});

});
