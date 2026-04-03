$(document).ready(function() {

	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	$('.cabinet0').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : '个人文件柜',
				minheight : 350,
				buttons : [{
					name : '新建文件柜',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '进入文件柜',
					bclass : 'edit',
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
				},{
					name : '查询文件柜',
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
				},{
					name : '站内搜索',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}]
			});

	function action(com, grid) {
		switch (com) {
			case '新建文件柜' :
				document.newCabinet.reset();
				$("#tabname").text("新建文件柜");
				$("#jqModelCabinet").jqmShow();
				break;
			case '进入文件柜' :
				var fileCabinetId = $("input[name=radioGroup]:radio:checked")
						.val();
				if (fileCabinetId == undefined) {
					alert('请选择!');
					return;
				}

				window.location.href = basePath
						+ "ea/filecabinet/ea_getFileOfFolderList.jspa?fileCabinetId="
						+ fileCabinetId;

				break;
			case '修改' :
				var fileCabinetId = $("input[name=radioGroup]:radio:checked")
						.val();
				if (fileCabinetId == undefined) {
					alert('请选择!');
					return;
				}

				document.newCabinetU.reset();
				var fileCabinetName = $("span#fileCabinetName",
						"tr#" + fileCabinetId).text();
				var descriptor = $("span#descriptor", "tr#" + fileCabinetId)
						.text();
				$("#fileCabinetName3").val(fileCabinetName);
				$("#descriptor3").val(descriptor);
				$("#hidcabinetid").val(fileCabinetId);
				$("#jqModelCabinetU").jqmShow();
				break;
			case '删除' :
				var Id = $("input[name=radioGroup]:radio:checked").val();
				if (Id == undefined) {
					alert('请选择!');
					return;
				}
				if (confirm("确定要全部删除？")) {
					 $f = $('#newCabinetU');
		             $f.find('input#hidcabinetid').val(Id);
					 var url= basePath
										+ "ea/filecabinet/ea_delFileCabinet.jspa";
					$f.attr("action",url);
					document.newCabinetU.submit.click();
				}
				break;
			case '查询文件柜' :
				document.searchCabinet.reset();
				$("#jqModelCabinetS").jqmShow();
				break;

			case '设置每页显示条数' :
				var url = basePath
						+ "ea/filecabinet/ea_getListForFileCabinet.jspa?1=1";
				numback(url);
				break;
			case '站内搜索' :
				document.searchGlobal.reset();
				$("#jqModelGlobal").jqmShow();
				break;


		}
	}

	$("#sumbitCabinet").click(function() {
		var cabinetname = $.trim($("#newCabinet #fileCabinetName2").val());
		if(cabinetname==null||cabinetname==""){
			alert("请输入文件柜名称");
			return;
		}
		$("#newCabinet").attr("action",
				basePath + "ea/filecabinet/ea_newFileCabinet.jspa");
		document.newCabinet.submit.click();

	});

	$("#cancelCabinet").click(function() {
				$("#jqModelCabinet").jqmHide();
			});

	$("#sumbitCabinetU").click(function() {
		var cabinetname = $.trim($("#newCabinetU #fileCabinetName3").val());
		if(cabinetname==null||cabinetname==""){
			alert("请输入文件柜名称");
			return;
		}
		$("#newCabinetU").attr("action",
				basePath + "ea/filecabinet/ea_newFileCabinet.jspa");
		document.newCabinetU.submit.click();

	});

	$("#cancelCabinetU").click(function() {
				$("#jqModelCabinetU").jqmHide();
			});
	$("#cancelCabinetS").click(function() {
				$("#jqModelCabinetS").jqmHide();
			});	
	$("#cancelGlobalS").click(function() {
				$("#jqModelGlobal").jqmHide();
			});	
				
	 $("#tosearch").click(function(){
          $("#searchCabinet").attr("action", basePath+"ea/filecabinet/ea_toSearch.jspa?pageNumber="+pNumber);
          document.searchCabinet.submit.click();
      });
      
     $("#tosearchGlobal").click(function(){
          $("#searchGlobal").attr("action", basePath+"ea/filecabinet/ea_toSearchGlobal.jspa?pageNumber="+pNumber);
          document.searchGlobal.submit.click();
      });
});

