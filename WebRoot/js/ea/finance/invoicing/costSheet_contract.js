$(document).ready(function() {

	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	
		
	if(type=="contract"){
		$(".trtip1").show();
		
	}else{
		
		$(".trtip1").hide();
	}

	$('.wspdoc').flexigrid({
				height : 349,
				width : 'auto',
				minwidth : 30,
				title : "项目合同管理",
				minheight : 349,
				buttons : [{
					name : '返回',
					bclass : 'restore',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},{
					name : '添加',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},{
					name : '查看',
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
		   case '返回' :
				document.location.href=basePath+"ea/productdesign/ea_getListProductdesign.jspa?ghua="+ghua;

				break;
		    case '添加' :

			document.location.href=basePath+"ea/documentcommon/ea_showDocumentModule.jspa?module=contract&journalNum="+projectCode+"&projectName="+projectName;

			break;
			case '查看' :

				if(docId==""){
					alert("请选择");
					return;
				}
				
				window.location.href = basePath
						+ "ea/documentinfo/ea_getViewDocument.jspa?docId="
						+ docId + "&type=toFinishedView&date=" + new Date();

				break;
			
			case '查询' :
				document.searchForms.reset();
	
				
				$("#jqModelSearch1").jqmShow();
				break;
           

				

				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/promanage/ea_getContractByProject.jspa?search="
						+ search + "&productPack.ppID="+projectCode+"&type="+type+"&date=" + new Date();
				numback(url);
				break;

		}
	}

	$(".search").click(function() {
		$("#searchForms").attr("target","_self").attr(
				"action",
				basePath + "ea/promanage/ea_toSearchForContract.jspa?pageNumber="
						+ pNumber+"&date=" + new Date());
		document.searchForms.submit.click();
	});


	
	

   //用于阻止复选框的冒泡行为；
	$("input.JQuerypersonvalue").click(function(event) {
				event.stopPropagation();

			});
	
	

	$(".wspdoc tr").dblclick(function() {
				docId = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				action("查看");
			});
	$(".wspdoc tr").click(function() {
				docId = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
		
			});	
			


});






