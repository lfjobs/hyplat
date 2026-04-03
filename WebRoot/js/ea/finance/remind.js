$(document).ready(function() {
	$("#DaoRuFan").click(function(){// 关闭
       $("#bankJqm").jqmHide();
	}); 
	$("#DaoRuFanqd").click(function(){// 选择确定
		var checkopertionID =$("#checkopertionID",$("#bankJqm")).attr("value");//partnerID
		var checkform =$("#checkform",$("#bankJqm")).attr("value");//stafftable
		var checkopertionName = $("#checkopertionName",$("#bankJqm")).attr("value");//责任人名字partnerName
		var childopertionName = $("#childopertionName",$("#bankJqm")).attr("value");//人员编号childPartnerName
		var childopertionID = window.frames["daoRu"].opertionID;//单选选中人员id
		if(childopertionID == ""){
			alert("请选择")
			return;
		}
		var ids="";
		var names="";
		window.frames["daoRu"].$("tr").find("input:checkbox[name='a']:checked").each(function() {
			var id=$(this).val();
			var name=window.frames["daoRu"].$("tr#"+id).find("span#"+checkopertionName).text();
			ids+=id+",";
			names+=name+";";
		});
		//var no = window.frames["daoRu"].$('tr#'+childopertionID).find("span#"+checkopertionName).text();//单选选中人员名字
		//var childopertionName =window.frames["daoRu"].$('tr#'+childopertionID).find("span#"+childopertionName).text();//单选选中人员编号
		if(checkopertionID != "")
			$("#"+checkopertionID,$("#"+checkform)).attr("value",ids).trigger("blur");
		if(checkopertionName != ""){
			$("#"+checkopertionName,$("#"+checkform)).attr("value",names).trigger("blur");
		}
		if(checkopertionName =="partnerName"){
			var final = names;
			$("#"+checkopertionName,$("#"+checkform)).attr("value",final).trigger("blur");
		}
		 $("#daoRu").attr("src","");
         $("#bankJqm").jqmHide();
   });

	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	$('.address').flexigrid({
				height : 345,
				width : 'auto',
				minwidth : 30,
				title : '消息提醒',
				minheight : 80,
				buttons : [{
					name : '添加',
					bclass : 'add',
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
				}/*, {
					name : '修改',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}*/, {
					name : '查询',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}/*, {
					name : '导出',
					bclass : 'excel',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}*/, {
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
			case '添加' :
				remindID = "";
				$("input.JQuerypersonvalue").attr("checked", false);
				$("#jqModel").jqmShow();
				document.cstaffForm.reset();
				break;
			case '修改' :
				if (remindID == "") {
					alert('请选择!');
					return;
				}
				document.cstaffForm.reset();
				$t = $("table#stafftable");
				$p = $("tr#" + remindID);
				var state=$p.find("span#remindStatus").text();
				if (state != "未发送") {
					alert('只能修改未发送的信息!');
					return;
				}
				$p.find("span[id]").each(function() {
							$t.find(":input[name]#" + this.id).val($(this).text());
						});
				$("#jqModel").jqmShow();
				var circularType=$("input#circularType").val();
				if(circularType=="待阅")
				{$("select#circularType").get(0).selectedIndex = 0;}
				else if(circularType=="待办")
				{$("select#circularType").get(0).selectedIndex = 1;}
				
				var remindType=$("input#remindType").val();
				if(remindType=="页面弹框")
				{$("select#remindType").get(0).selectedIndex = 0;}
				else if(remindType=="电脑客户端")
				{$("select#remindType").get(0).selectedIndex = 1;}
				else if(remindType=="手机客户端提醒")
				{$("select#remindType").get(0).selectedIndex = 2;}
				
				break;
			case '删除' :
				if (remindID == "") {
					alert('请选择！');
					return;
				}
				$f = $('#cstaffForm');
				$f.find(':input#remindID').val(remindID);
				if (confirm("确定继续？")) {
					$f
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "/ea/remind/ea_delremind.jspa?pageNumber="
											+ pNumber + "&search=" + search);
					document.cstaffForm.submit.click();
                    token = 2;
					//$("tr#" + remindID).remove();
					remindID = "";
				}
				break;

			/*case '导出' :
				var url = basePath
						+ "ea/marketsurvey/ea_showExcel.jspa?pageNumber="
						+ pNumber + "&search=" + search;
				open(url);
				break;*/
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
			case '查看' :
				document.cstaffForm.reset();
				$t = $("table#stafftable");
				$p = $("tr#" + remindID);
				$p.find("span[id]").each(function() {
							$t.find(":input#" + this.id).val($(this).text());// .attr("readonly","readonly")
						});
				$("#jqModel").jqmShow();
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "/ea/remind/ea_getListRemind.jspa?search="
						+ search;
				numback(url);
				break;
		}
	}
	$("table tr[id]").click(function() {
		        remindID = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	//条件查询消息
	$("#tosearch").click(function() {
		$("#postSearchForm").attr(
				"action",
				basePath + "/ea/remind/ea_toSearch.jspa?pageNumber="
						+ pNumber);
		document.postSearchForm.submit.click();
	});
	
	$(".address tr[id]").dblclick(function() {
				//action("修改");
			});
	// 取消
	$(".close").click(function() {
				$("#jqModel").jqmHide();
				re_load();
			});
    // 取消
	$(".bankJqmclose").click(function() {
				$("#bankJqm").jqmHide();
			});
	$(".JQueryreturn").click(function() {
				$("#jqModel").jqmHide();
				re_load();
			});
	
	//添加、修改消息
	$("input#tosave").click(function() {
		var receiveDate = $("input#receiveDate").val();
		$(".put3").trigger("blur");
		if ($("form .error").length) {
			return;
		}
		//判断添加消息
		if (remindID == "") {
			$("#cstaffForm")
					.attr("target", "hidden")
					.attr(
							"action",
							basePath
									+ "/ea/remind/ea_addremind.jspa?receiveDate="+receiveDate+"&pageNumber="
									+ pNumber);
			document.cstaffForm.submit.click();
			document.cstaffForm.reset();
			token = 2;
			return;
		}
		//修改消息
		$("#cstaffForm")
				.attr("target", "hidden")
				.attr(
						"action",
						basePath
								+ "/ea/remind/ea_addremind.jspa?pageNumber="
								+ pNumber);
		document.cstaffForm.submit.click();
		token = 2;
	});
});
function re_load() {
	if (token)
		document.location.href = basePath
				+ "/ea/remind/ea_getListRemind.jspa?pageNumber="
				+ pNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value");
}
//选择责任人
	function importGY(attachSearchTable,checkopertionID,checkopertionName,childopertionName,url){ //打开页面
		 
		 if(checkopertionName=="bankNum"){
		 	var departmentID =  $("#departmentID").attr("value");
		 	url = url + "?departmentID="+departmentID;
		 }
		 if(checkopertionName=="partnerName"){
		 	url = url + "?title1=title1";
		 }
		 $("#checkopertionID",$("#bankJqm")).attr("value",checkopertionID);//责任人id
		 $("#checkform",$("#bankJqm")).attr("value",attachSearchTable);//对应表单
		 $("#checkopertionName",$("#bankJqm")).attr("value",checkopertionName);//判断
		 $("#childopertionName",$("#bankJqm")).attr("value",childopertionName);
	   $("#bankJqm #daoRu").attr("src",basePath+url);
	   $("#bankJqm").jqmShow();
     }
