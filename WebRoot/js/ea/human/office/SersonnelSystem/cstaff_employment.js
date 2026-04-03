$(function() {
	$("#jqModelSearch").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close').jqDrag('.drag');// 添加拖拽的selector
	$("#jqModel").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	$("#jqMode2").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	$('.JQueryflexme').flexigrid({
				height : 300,
				width : 'auto',
				minwidth : 30,
				title : '人员面试管理',
				minheight : 80,
				buttons : [{
					name : '查看',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '面试',
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
					name : '打印登记表',
					bclass : 'excel',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '打印登记数据',
					bclass : 'excel',
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
				},  {
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
				if (auditionID == "") {
					alert('请选择人员');
					return
				}
				document.cstaffForm.reset();
				$t = $("table#stafftable");
				$p = $("tr#" + auditionID);

				$p.find("span[id]").each(function() {
							$t.find(":input#" + this.id).val($(this).text());
						});
				$("#jqModel").jqmShow();
				break;
			case '面试':
				if (auditionID == "") {
					alert('请选择人员');
					return
				}
				document.employmentForm.reset();
				var tax = $("tr#"+auditionID).find("span#status").text();
				if(tax == "10"){
					$("input#audBX").css("display","none");
					$("input#audBXW").css("display","none");
				}else if(tax == "11"){
					var str = "<img src='" + basePath
							+ "images/ea/human/mx_01.png'/>";
					$("td#imgshow").html(str);
					$("input#audKX").css("display","none");
					$("input#audKXW").css("display","none");
				}
				
				$t = $("table#stafftable");
				$p = $("tr#" + auditionID);

				$p.find("span[id]").each(function() {
							$t.find("span#" + this.id).text($(this).text());
						});
				$("#jqMode2").jqmShow();
				
				
				
//				if (auditionID == "") {
//					alert('请选择人员');
//					return;
//				}
//				var url = basePath
//						+ "ea/saudition/ea_editaudition.jspa?staffID="
//						+ auditionID;
//				window
//						.open(url, '',
//								'scrollbars=yes,resizable=yes,channelmode');
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
			 case '打印登记表':
				var url = basePath
						+ "/page/ea/main/human/office/production/printBasicInformation.jsp?star=面试登记表";
				window.open(encodeURI(url));
				break;
			 case '打印登记数据' :
					if (auditionID == "") {
						alert("请选择人员！");
						return;
					}
					var staffid = $("tr#"+auditionID).find("span#staffID").text();
					var url = basePath
							+ "ea/saudition/ea_printBIAud.jspa?star=面试登记表&staffID="
							+ staffid;
					window.open(encodeURI(url));
					break;
			case '删除' :
				if (auditionID == "") {
					alert('请选择人员');
					return;
				}
				$("#cstaffForm")
						.attr(
								"action",
								basePath
										+ "ea/saudition/t_ea_delAudition.jspa?pageNumber="
										+ ppageNumber
										+ "&status=1&audition.auditionID="
										+ auditionID);
				document.cstaffForm.submit();
				break;
			case '导出' :
				url = basePath + "ea/saudition/ea_showExcel.jspa?status=1";
				open(url);
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/saudition/ea_getauditionList.jspa?status=1";
				numback(url);
				break;
		}
	}
	$("input.JQueryreturn").click(function() {// 取消
		$("#jqMode2").jqmHide();
		 document.location.href = basePath
			+ "ea/saudition/ea_getauditionList.jspa?pageNumber="+pageNumber+"&status=1";
			});
	$(".menu00").click(function() {
				$(this).hide();
			});
	$(".JQueryflexme tr[id]").dblclick(function() {
				action('查看');// 当双击时出发 action方法.等价于先选中再点击修改按钮
			});
	$(".JQueryflexme tr[id]").click(function() {
				auditionID = this.id;
				$("input.JQueryauditionID", $(this)).attr("checked", "checked");
			});

	$(".btn").click(function() {
		var url = "";
		if($(this).attr("id") == "audKX"){
			url += basePath+"ea/saudition/sajax_ea_deitAuditionky.jspa?start=1&audition.auditionID="+auditionID+"&d="+Math.ceil(Math.random()*1000);
		}else if($(this).attr("id") == "audKXW"){
			url += basePath+"ea/saudition/sajax_ea_deitAuditionkn.jspa?start=1&audition.auditionID="+auditionID+"&d="+Math.ceil(Math.random()*1000);
		}else if($(this).attr("id") == "audBX"){
			url += basePath+"ea/saudition/sajax_ea_deitAuditionky.jspa?start=2&audition.auditionID="+auditionID+"&d="+Math.ceil(Math.random()*1000);
		}else if($(this).attr("id") == "audBXW"){
			url += basePath+"ea/saudition/sajax_ea_deitAuditionkn.jspa?start=2&audition.auditionID="+auditionID+"&d="+Math.ceil(Math.random()*1000);
		}
		$.ajax({
            url : encodeURI(url),
            type : "get",
            async : false,
            dataType : "json",
            success : function cbf(data) { 
                var member = eval("(" + data + ")");
                var res = member.result;
                if(res == "11"){
					var str = "<img src='" + basePath
							+ "images/ea/human/mx_01.png'/>";
					$("td#imgshow").html(str);
					$("input#audKX").css("display","none");
					$("input#audKXW").css("display","none");
					$("input#audBX").css("display","");
					$("input#audBXW").css("display","");
                }else{
                	document.location.href = basePath
         				+ "ea/saudition/ea_getAuditionkb.jspa?start=3";
                }
               },
            error : function cbf(data) {
                alert("获取数据失败!");
            }
        });
	});
	// 查询相关操作
	$("#searchStaff").click(function() {
		$("#cstaffSearchForm").attr(
				"action",
				basePath + "ea/saudition/ea_toSearchCStaff.jspa?pageNumber="
						+ ppageNumber + "&status=1");
		document.getElementById("cstaffSearchForm").submit();
		$("#cataffSearchTable").find(":input[name]").val("");
	});

});
