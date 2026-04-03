$(function() {
	
	var title ="<form id='ptForm' name='ptForm' method='post' style='height:25px;' >日生产跟踪管理&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;产品编号："
			+ "<input name='ptrack.productNumber' style='width:80px;'/>"
			+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;产品名称："
			+ "<input name='ptrack.productName' style='width:80px;'/>" +
					"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;跟踪员："
			+ "<input name='ptrack.trackman' style='width:80px;'/>" +
			"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;状态："
			+ "<select  id='ptstatus' style='width:80px;'/>" +
					"<option value=''>请选择</option>" +
					"<option value='00'>未提交审核</option>" +
					"<option value='01'>已提交审核</option></select>" +
					"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;时间："
			+ "<input name='ptrack.trackTime' onfocus='date()' style='width:80px;'/>"
			+ "&nbsp;&nbsp;&nbsp;"
			+ "<input type='submit' id='pt' class='input-button' style='margin:0px;margin-left:5px;' value='查询' /></form>";
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
	// 遮罩程度%
	}).jqmAddClose('.close');// 添加触发关闭的selector
	var html =  $(".query").html();
	$(".query").remove();
	// .jqDrag('.drag');// 添加拖拽的selector
	$('.flexigrid').flexigrid({
		height : 355,
		width : 'auto',
		minwidth : 30,
		title :title,
		minheight : 80,
		buttons : [{
			name : '添加',
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
		}, {
			name : '删除',
			bclass : 'delete',
			onpress : action
		// 当点击调用方法
		}, {
			separator : true
		}, {
			name : '提交审核',
			bclass : 'checkout',
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
		}, {
			name : '设置每页显示条数',
			bclass : 'mysearch',
			onpress : action
		// 当点击调用方法
		}, {
			separator : true
		} ]
	});
	function action(com, grid) {
		switch (com) {
		case '添加':
			id = '';
			
			window.open(basePath+"ea/ptrack/ea_addByPTrack.jspa?fiveClear="+fiveClear);
			getLotNumber();
			break;
		case '修改':
			if (id == "") {
				alert('请选择!');
				return;
			}
			$p = $("tr#" + id);
			var status = $.trim($p.find("span#status").text());
			if (status != "00") {
				alert("只能修改未审核状态!");
				return;
			}
			
			window.open(basePath+"ea/ptrack/ea_addByPTrack.jspa?ptrack.ptrackekey="+id+"&fiveClear="+fiveClear);
			getLotNumber();

			break;

	   case '删除':

				if (id == "") {
					alert('请选择!');
					return;
				}
				$p = $("tr#" + id);
				var status = $.trim($p.find("span#status").text());
				if (status != "00") {
					alert("只能删除草稿状态!");
					return;
				}
				
				if (confirm("确认要删除吗")) {
					
					$("#forms").attr("target", "hidden").attr("action",
							basePath + "ea/ptrack/ea_deleteByPTrack.jspa?ptrackekey="+id);
				   document.forms.submits.click();
					token = 2;
				}
				
				break;
	  case '提交审核':
				if (id == "") {
					alert('请选择!');
					return;
				}
				$p = $("tr#" + id);
				var status = $.trim($p.find("span#status").text());
				if (status != "00") {
					alert("只能提交未审核状态!");
					return;
				}
				if (confirm("确认要提交审核吗")) {
					$("#forms").attr("target", "hidden").attr("action",
							basePath + "ea/ptrack/ea_saveByPtrack.jspa?status=01&ptrackekey="+id);
				   document.forms.submits.click();
					token = 2;
				}
				break;
				
		case '导出':
			url = basePath + "ea/ptrack/ea_exportByPTrack.jspa?type="+type+"&ptrack.status="+$("select#ptstatus").find("option:selected").val();
			open(url);
			break;
		case '打印':
			url = basePath
					+ "ea/ptrack/ea_toPrintPreviewByPTrack.jspa?type="+type+"&ptrack.status="+$("select#ptstatus").find("option:selected").val();
			open(url);
			break;
		case '设置每页显示条数':
			var url = basePath + "ea/ptrack/ea_getPtrackList.jspa?type="+type+"&search=search&ptrack.status="+$("select#ptstatus").find("option:selected").val()+"&fiveClear="+fiveClear;
					
			numback(url);
			break;

		}

		}
    
	
	// 新加内容结束
	$(".JQueryflexme tr[id]").dblclick(function() { // 双击查看
		action('修改');// 当双击时出发 action方法.等价于先选中再点击修改按钮
	});
	// 获取id
	$(".JQueryflexme tr[id]").click(function() {
		id = this.id;
		$("input.JQuerypersonvalue", $(this)).attr("checked", "checked");
	});

	$(".JQueryreturn").click(function() {// 返回
		$("#jqModel").jqmHide();
	});
	
	/**
	 * 查询
	 */
	$("#pt")
			.click(
					function() {
						var url = basePath
								+ "ea/ptrack/ea_toSearchByPTrack.jspa?search=search&type=01&ptrack.status="+$("select#ptstatus").find("option:selected").val()+"&fiveClear="+fiveClear;

						$("form#ptForm").attr("action", url);

					});
	

});

function re_load() {
	if (pageNumber == 1) {
		pageNumber = 0; // 找不到问题 暂时之使用这个方式 原因 不做任何操作 pageNumber 自动 变成 1
	}
	if (token)
		document.location.href = basePath
				+ "ea/ptrack/ea_getPtrackList.jspa?pageForm.pageNumber=" + $("#pageNumber").attr("value");
}
/**
 * 获得生产批次号
 */
function getLotNumber() {
	var url = basePath + "ea/ptrack/sajax_ea_getLotNumber.jspa?date="
			+ new Date().toLocaleString();
	$.ajax({
		url : url,
		type : "get",
		async : true,
		dataType : "json",
		data : {
			"lots" : "014"
		},
		success : function cbf(data) {
			var member = eval("(" + data + ")");
			var nologin = member.nologin;
			if (nologin) {
				document.location.href = basePath + "page/ea/not_login.jsp";
			}
			vouch = member.BillID;
			$("input#lot", $("form#ptrackForm")).val(vouch);
		},
		error : function cbf(data) {
			alert("数据获取失败!");
		}
	});
}

