$(function() {
	
	var title = "<form id='dcForm' method='post'>"+(status=="00"?"考核检验":(status=="01"?"考核检验合格":"考核检验不合格"))+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;产品编号："
			+ "<input  style='width:80px;' name='dcheck.itemNumber' id='itemNumber' style='width:150px;'/>"
			+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;产品名称："
			+ "<input name='dcheck.goodName'style='width:80px;'/>" + "&nbsp;&nbsp;&nbsp;"
			+ "<input type='submit' id='dc' class='input-button' style='margin:0px;margin-left:5px;' value='查询' /></form>";
	var pass = "";
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话

	// 遮罩程度%
	}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	$('.flexigrid').flexigrid({
		height : 355,
		width : 'auto',
		minwidth : 30,
		title : title,
		minheight : 80,
		buttons : Type=="00" ? ([ {
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
			}
			pass = "合格";
			var key = $("input[name='a']:checked").parent().parent().parent().attr("id");
			
			
			url =  basePath + "ea/dcheck/ea_agreed.jspa?dcheckkey="+key+"&pass="+pass+"&dcheckTime="+dcheckTime+"&status=00";
			open(url);
			
		
			break;
		case '不合格':
			if (id == "") {
				alert('请选择!');
				return;
			}pass = "不合格";
			var bid = $(".JQueryflexme").find("#number").val();
			var key = $("input[name='a']:checked").parent().parent().parent().attr("id");
			
			url =  basePath + "ea/dcheck/ea_agreed.jspa?dcheckkey="+key+"&pass="+pass+"&dcheckTime="+dcheckTime+"&status=00";
			open(url);
			

			break;

		case '导出':
			url = basePath + "ea/dcheck/ea_exportByDCheck.jspa?type="+Type+"&status="+status+"&show="+show;
			open(url);
			break;
		case '打印':
			
			url = basePath
					+ "ea/dcheck/ea_toPrintPreviewByDCheck.jspa?type="+Type+"&status="+status+"&show="+show;
			open(url);
			break;
		case '设置每页显示条数':
			var url = basePath + "ea/dcheck/ea_getDCheckList.jspa?type="+Type+"&status="+status+"&show="+show;
					
			numback(url);
			break;

		}
	}
    
	$(".number").each(function(){
		var num=parseFloat($(this).text());
		var str=parseInt(num.toFixed(2)*100) + "%";
		$(this).text(str);
		
	});
	// 新加内容结束
	$(".JQueryflexme tr[id]").dblclick(function() { // 双击查看
		action('不合格');// 当双击时出发 action方法.等价于先选中再点击修改按钮
	});
	//获取
	$(".JQueryflexme tr[id]").click(function() {
		id = this.id;
		$("input.JQuerypersonvalue", $(this)).attr("checked", "checked");
	});
	
	/**
	 * 
	 * 返回方法
	 */

	$("input#submitColsed").click(function() {
		$("#jqModelSend").jqmHide();
		document.location.href=basePath + "ea/dcheck/ea_getDCheckList.jspa?status=00&type=00";
		
	});
	/**
	 * 查询
	 */
	$("#dc").click(
			function() {
				var url=basePath + "ea/dcheck/ea_toSearchByCheck.jspa?show=01&type="+Type+"&status="+status;
				$("form#dcForm").attr("action",
						url);
			});
	$("#yield").blur(function(){
		$input = $(this);
		$parent = $input.parent();
		var inputValue = $input.attr("value");
		if ($input.is(".puttt")) {
			if (inputValue == "") {
				$parent
						.append("<span class=\"error\"><a class=\"tex\">\u4e0d\u80fd\u4e3a\u7a7a</a></span>");
				return;
			}
			if ($.trim(inputValue) == "") {
				$parent
						.append("<span class=\"error\"><a class=\"tex\">不能为空格</a></span>");
				return;
			}
			var value=$("#yield").val();
			var btnumber=$("tr#"+id).find("#btnumber").text();
			if(eval(value)>eval(btnumber)){
				$parent
				.append("<span class=\"error\"><a class=\"tex\">合格数不能大于考核数</a></span>");
				return;
			}
			$parent
					.append("<span class=\"corect\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>");
		}
	});
	
	/**
	 * 提交审核
	 * 
	 * @returns
	 */
	$(".submitResult")
			.click(
					function() {
						
						if (pass == "合格") {
							$("form :input:.put3").trigger("blur");
							$("form :input:.positiveNum").trigger("blur");
							if($("form .error").length)
							{ 
								return;
							} 
						if (confirm("确认要"+pass+"吗？")) {
                             
							var url = basePath
									+ "ea/dcheck/ea_getDCheckListByStatus.jspa?dcheckkey="
									+ id;
								$("form#dcheckForm").attr("target", "hidden")
										.attr("action", url + "&type=01");
								} 
						}else {
								$("form#dcheckForm").attr("target", "hidden")
										.attr("action", url + "&type=02");
							}
							document.dcheckForm.submit.click();
							document.dcheckForm.reset();
							token = 2;
						
					});

});

function re_load() {
	if (pageNumber == 1) {
		pageNumber = 0; // 找不到问题 暂时之使用这个方式 原因 不做任何操作 pageNumber 自动 变成 1
	}
	if (token)
		document.location.href = basePath + "ea/dcheck/ea_getDCheckList.jspa?type="+Type
				+"&status="+status+"&pageForm.pageNumber="
				+ $("#pageNumber").attr("value");
}

