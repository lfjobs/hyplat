$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
	// 遮罩程度%
	}).jqmAddClose('.close');// 添加触发关闭的selector
	$('.JQueryflexme').flexigrid({
		height : 300,
		width : 'auto',
		minwidth : 30,
		title : '收支费用管理',
		minheight : 80,
		buttons : [ {
			name : '添加应收款',
			bclass : 'add',
			onpress : action
		// 当点击调用方法
		}, {
			name : cost1+"元",
			bclass : '',
			onpress : action
		// 当点击调用方法
		}, {
			separator : true
		}, {
			name : '添加已收款',
			bclass : 'add',
			onpress : action
		// 当点击调用方法
		}, {
			name : cost2+"元",
			bclass : '',
			onpress : action
		// 当点击调用方法
		}, {
			separator : true
		}, {
			name : '未收款',
			bclass : '',
			onpress : action
		// 当点击调用方法
		}, {
			name : cost3+"元",
			bclass : '',
			onpress : action
		// 当点击调用方法
		} ]
	});
	function action(com, grid) {
		switch (com) {
		case '添加应收款':
			document.payForm.reset();
			$("#jqModelpay").jqmShow();
			$("#titles").text("添加应收款");
			$("#payForm #costType1").val("00");
			

			break;
		case '添加已收款':
			var num = 0;
			$(".JQueryflexme").find("span#costType").each(function(){
				
			    if($(this).text()=="00"){
			    	num++;
			    }
			});
			if(num==0){
			   alert("请先添加应收款");
	    	   return;
			}
			document.payForm.reset();
			$("#jqModelpay").jqmShow();
			$("#titles").text("添加已收款");
			$("#payForm #costType1").val("01");
			break;

		case '修改':
			if (szid == "") {

				alert("请选择");
			}
			document.payForm.reset();
			$t = $("table#paytbl");
			$p = $("tr#" + szid);
			$p.find("span[id]").each(function() {
				$t.find(":input[name]#" + this.id).val($(this).text());
				
				
			});
			var costType = $p.find("#costType").text();
			$("#payForm #costType1").val(costType);
			if (costType== "00") {
				$("#titles").text("修改应收款");
			} else {
				$("#titles").text("修改已收款");
			}

			$("#jqModelpay").jqmShow();

			break;

		case '查询':
			$("#jqModel").jqmShow();

			break;
		case '设置每页显示条数':
			var url = basePath + "ea/taskmanage/ea_getSzcostList.jspa?myproject.proid="+proid;
			numback(url);
			break;
		}
	}

	// 新加内容结束

	$(".JQueryflexme tr[id]").dblclick(function() {
		action('修改');// 当双击时出发 action方法.等价于先选中再点击修改按钮
	});
	$(".JQueryflexme tr[id]").click(function() {
		szid = this.id;
		$("input.JQuerypersonvalue", $(this)).attr("checked", "checked");
	});

	// 查询
	$("#search").click(
			function() {
				$("#searchForm").attr("action",
						basePath + "ea/taskmanage/ea_toSearchBySzcost.jspa");
				document.searchForm.submit.click();

			});

	// 保存更新

	$("#submitpay").click(

			function() {
				$(".put3").trigger("blur");
				
				if ($("#payForm .error").length) {
					return;

				}

				$("#payForm #proid1").val(proid);
				
				
				
				$("#payForm").attr("target", "hidden").attr("action",
						basePath + "ea/taskmanage/ea_saveSzCost.jspa");
				document.payForm.submit.click();
				token = 2;

			});

});

function re_load() {
	if (token) {

		document.location.href = basePath
				+ "ea/taskmanage/ea_getSzcostList.jspa?myproject.proid="
				+ proid;

	}
}