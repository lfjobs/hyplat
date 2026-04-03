$(function() {
	var title = "<form id='btForm' method='post' style='height:20px;'>"+(status==00?"模拟测试管理":(status==02?"模拟测试合格":"模拟测试不合格"))+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;产品编号："
			+ "<input id='itemNumber'  name='bsimtest.itemNumber' style='width:80px;'/>"
			+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;产品名称："
			+ "<input name='bsimtest.goodName' style='width:80px;'/>"
			+ "&nbsp;&nbsp;&nbsp;"
			+ "<input type='submit' id='bt' value='查询'class='input-button' style='margin:0px;margin-left:5px;' /></form>";
     var pass="";
	$(".jqmWindow").jqm({
		modal : true// 限制输入（鼠标点击，按键）的对话

	// 遮罩程度%
	}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	$('.flexigrid').flexigrid({
		height : 355,
		width : 'auto',
		minwidth : 30,
		title : title,
		minheight : 80,
		buttons :  type == "00"? ( [ {
			name : '合格',
			bclass : 'mark',
			onpress : action
		// 当点击调用方法
		}, {
			separator : true
		}, {
			name : '不合格',
			bclass : 'empty',
			onpress : action
		// 当点击调用方法
		}, {
			separator : true
		}, {
			name : '导出',
			bclass : 'excel',
			onpress : action
		// 当点击调用方法
		}, {
			separator : true
		}, {
			name : '打印',
			bclass : 'printer',
			onpress : action
		// 当点击调用方法
		}, {
			separator : true
		} , {
			name : '设置每页显示条数',
			bclass : 'mysearch',
			onpress : action
		// 当点击调用方法
		}, {
			separator : true
		}]):([{
			name : '导出',
			bclass : 'excel',
			onpress : action
		// 当点击调用方法
		}, {
			separator : true
		}, {
			name : '打印',
			bclass : 'printer',
			onpress : action
		// 当点击调用方法
		}, {
			separator : true
		} , {
			name : '设置每页显示条数',
			bclass : 'mysearch',
			onpress : action
		// 当点击调用方法
		}, {
			separator : true
		} ])
	});
	function action(com, grid) {
		switch (com) {
		case '合格':

			if (id == "") {
				alert('请选择!');
				return;
			}pass = "合格";
			var key = $("input[name='a']:checked").parent().parent().parent().attr("id");
			
			url =  basePath + "ea/bsimtest/ea_agreed.jspa?bsimtest.bsimTestkey="+key+"&pass="+pass+"&auditTime="+auditTime;
			open(url);
			
			
			break;
		case '不合格':
			if (id == "") {
				alert('请选择!');
				return;
			}pass = "不合格";
            var key = $("input[name='a']:checked").parent().parent().parent().attr("id");
			
			url =  basePath + "ea/bsimtest/ea_agreed.jspa?bsimtest.bsimTestkey="+key+"&pass="+pass+"&auditTime="+auditTime;
			open(url);
			

			break;

		case '导出':
			url = basePath + "ea/bsimtest/ea_exportByBsimtest.jspa?type="+type+"&status="+status+"&search="+search;
			open(url);
			break;
		case '打印':
			url = basePath
					+ "ea/bsimtest/ea_toPrintPreviewByBsimtest.jspa?type="+type+"&status="+status+"&search="+search;
			open(url);
			break;
		case '设置每页显示条数':
			var url = basePath + "ea/bsimtest/ea_getBsimtestList.jspa?type="+type+"&status="+status+"&search="+search;
					
			numback(url);
			break;

		}
	}
	
	// 获取
	$(".JQueryflexme tr[id]").click(function() {
		id = this.id;
		$("input.JQuerypersonvalue", $(this)).attr("checked", "checked");
	});

	$(".JQueryreturn").click(function() {// 返回
		$("#jqModel").jqmHide();
	});

	/**
	 * 
	 * 返回方法
	 */

	$("input#submitColsed").click(function() {
		$("#jqModelSend").jqmHide();

	});
	/**
	 * 查询
	 */
	$("#bt")
			.click(
					function() {
						var url = basePath
								+ "ea/bsimtest/ea_toSearchByBsimtest.jspa?search=search&type="+type+"&status="+status;  //通过search区分查询和非查询

						$("form#btForm").attr("action", url);

					});
	/**
	 * 合格与不合格
	 * 
	 * @returns
	 */
	$(".submitResult")
			.click(
					function() {
						alert("进来啦 ");
						$("form :input:.put3").trigger("blur");
						if($("form .error").length)
						{ 
							return;
						} 
						if (confirm("确认要"+pass+"？")) {

							var url = basePath
									+ "ea/bsimtest/ea_getBsimtestListByStatus.jspa?bsimTestkey="
									+ id;
							//判断pass取值
							if (pass == "合格") {
								$("form#bsimtestForm").attr("target", "hidden")
										.attr("action", url + "&type=02");
							} else {
								$("form#bsimtestForm").attr("target", "hidden")
										.attr("action", url + "&type=03");
							}
							document.bsimtestForm.submit.click();
							document.bsimtestForm.reset();
							token = 2;
						}
					});

});

function re_load() {
	if (pageNumber == 1) {
		pageNumber = 0; // 找不到问题 暂时之使用这个方式 原因 不做任何操作 pageNumber 自动 变成 1
	}
	if (token)
		document.location.href = basePath
				+ "ea/bsimtest/ea_getBsimtestList.jspa?type="+type +"&status="+status
				+ "&pageForm.pageNumber=" + $("#pageNumber").attr("value");
}

/**
 * 
 * 获得产品行业
 */
function getAllItemNumber() {
	var url = basePath
			+ "ea/bsimtest/sajax_ea_getAllItemNumber.jspa?date="
			+ new Date().toLocaleString();
	$.ajax({
		url : encodeURI(url),
		type : "get",
		async : true,
		dataType : "json",
		success : function cbf(data) {
			var member = eval("(" + data + ")");
			var itemNumberlist = member.itemNumberlist;

			var str = "<option value=''>请选择行业</option>";
			for ( var i = 0; i < itemNumberlist.length; i++) {
				var obj = itemNumberlist[i];
				str += "<option value='" + obj.itemNumber + "'>"
						+ obj.itemNumber + "</option>";
			}
			$("#btForm select#itemNumber").html(str);

		},
		error : function cbf(data) {
			alert("数据获取失败！");
		}
	});
}
