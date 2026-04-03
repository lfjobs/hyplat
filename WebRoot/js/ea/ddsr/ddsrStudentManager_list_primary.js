$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	$('.JQueryflexme').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : '学员信息管理',
				minheight : 80,
				buttons : [ {
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
				}, {
					name : '个人预约记录',
					bclass : 'examine',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '选择教练预约',
					bclass : 'see',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}]
			});
	function action(com, grid) {
		switch (com) {	
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
			case '设置每页显示条数':
				var url = basePath + "ea/coachreservationrecordcontent/refprimary_doDdsrStudentManagerAction.jspa?innerAction=showDdsrStudentList&search=turnpage";
				numback(url);
				break;
			case '个人预约记录' :
				if(stud_key==""){
					alert("请选择");
					return ;
				}
				document.location.href = basePath+ "ea/coachreservationrecord/foreign_ea_toSearch.jspa?search=search&dssrsubject.subjType="+searchDangQian
				+"&dssrstudent.studKey="+stud_key+"&studKey="+studKey+"&studName="+studName;
				break;
			case '选择教练预约' :
				if(stud_key==""){
					alert("请选择");
					return ;
				}
				document.location.href = basePath+ "ea/coachreservationrecord/foreign_ea_toSearch.jspa?search=search&dssrsubject.subjType="+searchDangQian+
				"&ddsrcoach.coacStatus="+studStatus+"&ddsrcoach.coacTeachtype="+studCredentials+"&ddsrcoach.coacStar="+studStar+"&studKey="+studKey+"&studName="+studName;	
				break;	
		}
	}
	$(".JQueryflexme tr[id]").click(function() {
				stud_key = this.id;
				studKey=this.id;
				
				studName=$(this).find("span#staffName").text();
				
				studStatus=$.trim($(this).find("span#studStatus").text());
				studCredentials=$.trim($(this).find("span#studCredentials").text());
				studStar=$.trim($(this).find("span#studStar").text());
				
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	$(".JQuerySearchSubmit").click(function(){
		$("#jqModelSearch").jqmHide();
		$("#searchForm").attr("action",
				basePath + "ea/coachreservationrecordcontent/refprimary_doDdsrStudentManagerAction.jspa?innerAction=showDdsrStudentList");
		document.searchForm.submit.click();
	});
	$(".JQuerySearchReturn").click(function(){
		$("#jqModelSearch").jqmHide();
	});
});