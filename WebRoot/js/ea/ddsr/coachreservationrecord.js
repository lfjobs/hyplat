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
				title : (subjType==10?"科一":subjType==20?"科二":subjType==30?"科三":"科四")+"预约管理",
				minheight : 80,
				buttons : [{
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
			case '查询':
			$("#jqModelSearch").jqmShow();
			break;	
			case '设置每页显示条数' :
				var url = basePath + "ea/coachreservationrecord/ea_getListCoachReservationRecord.jspa?search="+search+"&dssrsubject.subjType="+subjType;
				numback(url);
				break;
		}
	}
	/**
	 * 查询列表
	 */
	$("#tosearch").click(function(){
		$("#SearchForm").attr("action",basePath+ "ea/coachreservationrecord/ea_toSearch.jspa?pageNumber="+pNumber);
		document.getElementById("SearchForm").submit.click();
	});
	/**
	 * 弹出 某个教练 某天 所有时间段详细信息
	 */
	$(".JQueryflexme td[id]").click(function() {
		coacKey=$(this).attr("id").split("@")[0];
		rereAppdate=$(this).attr("id").split("@")[1];
		coac_status=$.trim($(this).parent("tr").find("span#coac_status").text());
		coac_level=$.trim($(this).parent("tr").find("span#coac_level").text());
		coac_teachtype=$.trim($(this).parent("tr").find("span#coac_teachtype").text());
		coac_star=$.trim($(this).parent("tr").find("span#coac_star").text());
		$("#ifr2").attr("src",basePath+"ea/coachreservationrecordcontent/ea_getListCoachReservationRecordContentPersonal.jspa?ddsrcoach.coacKey="+coacKey+"&ddsrreservationrecord.rereAppdate="+rereAppdate+"&dssrsubject.subjType="+subjType);
 	  	$("#jqmWindow2").jqmShow();
	});
	$("#jqmWindow2 #isBack2").click(function() {
		$("#jqmWindow2").jqmHide();
		token=1;
		re_load();
	});
	/**
	 * 弹出学员列表信息 选择 预约学员
	 */
	$("#jqmWindow2 #isSubmit2").click(function(){
		var rereKeyArray=window.frames["ifr2"].rereKeyArray;
		if(!rereKeyArray.length){
			alert("请选择预约时间段！！");
			return;
		}
		$("#ifr3").attr("src",basePath+"ea/coachreservationrecordcontent/ref_doDdsrStudentManagerAction.jspa?search=search&innerAction=showDdsrStudentList&searchZhuangtai=10&searchDangQian="+subjType+"&searchStar="+coac_star+"&searchZhengtype="+coac_teachtype);
 	  	$("#jqmWindow3").jqmShow();
	});
	$("#jqmWindow3 #isBack3").click(function() {
		$("#jqmWindow3").jqmHide();
	});
	/**
	 * 提交预约信息
	 */
	$("#jqmWindow3 #isSubmit3").click(function(){
		var rereKeyArray=window.frames["ifr2"].rereKeyArray;
		var stud_key=window.frames["ifr3"].stud_key;
		if(stud_key==""){
			alert("请选择学员!!");
			return;
		}
		if(notoken){
			return;
		}
		notoken=1;
		$("#ReservationRecoardForm #rereKeyString").val(rereKeyArray.toString());
		$("#ReservationRecoardForm #studKey").val(stud_key);
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
					$("#jqmWindow3").jqmHide();
					$("#ifr2").attr("src",basePath+"ea/coachreservationrecordcontent/ea_getListCoachReservationRecordContentPersonal.jspa?ddsrcoach.coacKey="+coacKey+"&ddsrreservationrecord.rereAppdate="+rereAppdate+"&dssrsubject.subjType="+subjType);
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
				+ "ea/coachreservationrecord/ea_getListCoachReservationRecord.jspa?pageNumber=" + pNumber+"&search="+search+"&dssrsubject.subjType="+subjType;
	}
}