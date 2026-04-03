$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	$('.JQueryflexme').flexigrid({
		height : 350,
		width : 'auto',
		minwidth : 30,
		title : '个人客户调查管理',
		minheight : 80,
		buttons : [{
			name : '新增',
			bclass : 'add',
			onpress : action				
			}, {
			separator : true
		}, {
			name : '来自社会人力',
			bclass : 'add',
			onpress : action				
			}, {
			separator : true
		},{
			name : '删除',
			bclass : 'delete',
			onpress : action				
			}, {
			separator : true
		}, {
			name : '修改',
			bclass : 'edit',
			onpress : action				
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
			name : '设置每页显示条数',
			bclass : 'mysearch',
			onpress : action
				// 当点击调用方法
			}, {
			separator : true
		}]
	});
	
	//单击个人客户列表，选择一个客户
	$(".JQueryflexme tr[id]").click(function() {
		personvalue = this.id;
		$("input.JQuerypersonvalue", $(this)).attr("checked", "checked");
	});
	
	function action(com, grid) {
		switch(com){
		case '新增':	
			var url = basePath + "ea/marketingCrmCustomer/ea_addCustomer.jspa";
			window.open(url, '','scrollbars=yes,resizable=yes,channelmode');
			break;
		case '来自社会人力':
			accift('02');
			break;
		case '删除':
			if (personvalue == "") {
				alert('请选择人员');
				return;
			}
			if (confirm("是否删除？")){
				var url = basePath + "ea/marketingCrmCustomer/ea_delCustomer.jspa?customerid=" + personvalue;
				$f = $('#personForm');
				$f.attr("target", "hidden").attr("action", url);				
				document.personForm.submit();	
				$("tr#" + personvalue).remove();
			}
			break;
		case '修改':
			if (personvalue == "") {
				alert('请选择人员');
				return;
			}
			var url = basePath + "ea/marketingCrmCustomer/ea_getCustomer.jspa?customerid=" + personvalue;
			window.open(url, '','scrollbars=yes,resizable=yes,channelmode');
			break;
		case '查看':
			if (personvalue == "") {
				alert('请选择人员');
				return;
			}
			var url = basePath + "ea/marketingCrmCustomer/ea_viewCustomer.jspa?customerid=" + personvalue;
			window.open(url, '','scrollbars=yes,resizable=yes,channelmode');
			break;	
		};		
	};			
	
});