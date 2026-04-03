$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	$('.address').flexigrid({
				height : 70,
				width : 'auto',
				minwidth : 30,
				title : '考试信息--当前学员：' + parent.studentName,
				minheight : 50,
				buttons : [ {
					name : '设置考试结果',
					bclass : 'edit',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '设置每页显示条数',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}]
			});
	function action(com, grid) {
		switch (com) {
			case '设置考试结果' :
				if (drivingtestid == '') {
					alert("请选择！");
					return;
				}
				var isTrue=parent.$("tr#"+drivingprincipalid).find("span#studentstatus").text();
				if(isTrue=='已合格'){
					alert("该学员考试已合格!并提交至下一科目！不能修改考试信息 ");
					return;
				}
				$("#drivingtestid",$("#kaishiForm")).attr("value",drivingtestid);
				$("#jqModelSearchss").jqmShow();
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/driving/ea_getDtDrivingTestList.jspa?dtDrivingPrincipal.drivingprincipalid="+drivingprincipalid+"&docstatus="+docstatus;
				numback(url);
				break;

		}
	}

	$(".address tr[id]").click(function() {
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				drivingtestid = this.id;
			});
	$(".address tr[id]").dblclick(function() {

				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
				drivingtestid = this.id;
				action("修改");
			});
	$("input.JQuerySubmit").click(function() {// 保存
					var ksjg=$("input.ksjg:checked",$("#kaishiForm"));
					if(!ksjg.length){
						alert("请选择");
						return	
					}
					var  tatus=$.trim(ksjg.attr("value"));
					var studentstatus="";
					var testresult="";
					if("00"==tatus){
						studentstatus="已合格";
						testresult="合格";
					}else if("01"==tatus){
						studentstatus="已约考";
						testresult="不合格";
					}else{
						studentstatus="已约考";
						testresult="未考";
					}
					parent.$("tr#"+drivingprincipalid).find("span#studentstatus").text(studentstatus);
					parent.$("tr#"+drivingprincipalid).find("span#testresult").text(testresult);
					$("#kaishiForm")
								.attr("target", "hidden")
								.attr(
										"action",
										basePath
												+ "ea/driving/ea_updateDrivingTest.jspa?pageNumber="
												+ pNumber );
					document.kaishiForm.submit.click();
					token = 2;
		
	});						
});
function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/driving/ea_getDtDrivingTestList.jspa?pageNumber=" + pNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value")+"&dtDrivingPrincipal.drivingprincipalid="+drivingprincipalid+"&docstatus="+docstatus;
}