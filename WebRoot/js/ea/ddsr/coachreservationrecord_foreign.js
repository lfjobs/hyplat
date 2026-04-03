$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	$('.JQueryflexme').flexigrid({
				height : 400,
				width : 'auto',
				minwidth : 30,
				title : (subjType==10?"科一":subjType==20?"科二":subjType==30?"科三":"科四")+"预约管理-预约学员-"+studName,
				minheight : 80,
				buttons : [{
					name : '返回',
					bclass : 'restore',
					onpress : action
						// 当点击调用方法
					},{
					separator : true
				},{
					name : '查询',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					},{
					separator : true
				},{
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
			case '返回':
				document.location.href = basePath+ "ea/coachreservationrecordcontent/refprimary_doDdsrStudentManagerAction.jspa?search=search&innerAction=showDdsrStudentList&searchZhuangtai=10&searchDangQian="+subjType;
				break;	
			case '查询':
			$("#jqModelSearch").jqmShow();
				break;	
			case '设置每页显示条数' :
				var url = basePath + "ea/coachreservationrecord/foreign_ea_getListCoachReservationRecord.jspa?search="+search+"&dssrsubject.subjType="+subjType+"&dssrstudent.studKey="+stud_key+"&ddsrcoach.coacStatus="+coacStatus+"&ddsrcoach.coacTeachtype="+coacTeachtype+"&ddsrcoach.coacStar="+coacStar+"&studKey="+studKey+"&studName="+studName;
				numback(url);
				break;
		}
	}
	$(".JQueryflexme tr[id]").dblclick(function() {
				action('修改');
	});
	$(".JQueryflexme tr[id]").click(function() {
				coacKey = this.id;
				$("input.JQuerypersonvalue", $(this))
						.attr("checked", "checked");
			});
	/**
	 * 查询列表
	 */
	$("#tosearch").click(function(){
		$("#SearchForm").attr("action",basePath+ "ea/coachreservationrecord/foreign_ea_toSearch.jspa?pageNumber="+pNumber);
		document.getElementById("SearchForm").submit.click();
	});
	/**
	 * 弹出 某个教练 某天 所有时间段详细信息
	 */
	$(".JQueryflexme td[id]").click(function(){
		coacKey=$(this).attr("id").split("@")[0];
		rereAppdate=$(this).attr("id").split("@")[1];
		$("#ifr2").attr("src",basePath+"ea/coachreservationrecordcontent/ea_getListCoachReservationRecordContentPersonal.jspa?ddsrcoach.coacKey="+coacKey+"&ddsrreservationrecord.rereAppdate="+rereAppdate+"&dssrsubject.subjType="+subjType+"&dssrstudent.studKey="+stud_key+"&studKey="+studKey+"&studName="+studName);
 	  	$("#jqmWindow2").jqmShow();
	});
	$("#jqmWindow2 #isBack2").click(function() {
		$("#jqmWindow2").jqmHide();
		token=1;
		re_load();
	});
	/**
	 * 提交预约信息
	 */
	$("#jqmWindow2 #isSubmit2").click(function(){
		var rereKeyArray=window.frames["ifr2"].rereKeyArray;
		if(!rereKeyArray.length){
			alert("请选择预约时间段！！");
			return;
		}
		if(notoken){
			return;
		}
		notoken=1;
		$("#ReservationRecoardForm #rereKeyString").val(rereKeyArray.toString());
		$("#ReservationRecoardForm #studKey").val(studKey);
		var url = basePath+ "ea/coachreservationrecordcontent/sajax_ea_addReservationRecordContentPersonal.jspa?";
		$.ajax({
				url : encodeURI(url+"date01="+ new Date()),
				type : "post",
				async : false,
				dataType : "json",
				data:$('#ReservationRecoardForm').serializeArray(),
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var status=member.status;
					var message=member.message;
					if(status){
						alert(message);
						notoken=0;
					}else{
						alert(message);
						notoken=0;
					}
					$("#ifr2").attr("src",basePath+"ea/coachreservationrecordcontent/ea_getListCoachReservationRecordContentPersonal.jspa?ddsrcoach.coacKey="+coacKey+"&ddsrreservationrecord.rereAppdate="+rereAppdate+"&dssrsubject.subjType="+subjType+"&dssrstudent.studKey="+stud_key+"&studKey="+studKey+"&studName="+studName);
				},
				error : function cbf(data) {
					alert("数据获取失败！");
					notoken=0;
				}
			});
	});
});
function re_load() {
	if (token) {
		document.location.href = basePath
				+ "ea/coachreservationrecord/foreign_ea_getListCoachReservationRecord.jspa?pageNumber=" + pNumber+"&search="+search+"&dssrsubject.subjType="+subjType+"&dssrstudent.studKey="+stud_key+"&ddsrcoach.coacStatus="+coacStatus+"&ddsrcoach.coacTeachtype="+coacTeachtype+"&ddsrcoach.coacStar="+coacStar+"&studKey="+studKey+"&studName="+studName;
	}
}