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
				title : "预约管理",
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
				},{
					name : '打印预览',
					bclass : 'printer',
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
				var url = basePath + "ea/coachreservationrecordcontent/ea_getListReservationRecordSummary.jspa?search="+search+"&dssrsubject.subjType="+subjType;
				numback(url);
			break;
			case '打印预览':
			if(confirm("确定打印吗？分批次打印效果最好")){
				window.open(basePath + "ea/coachreservationrecordcontent/ea_getListReservationRecordSummaryPrint.jspa?search="+search+"&dssrsubject.subjType="+subjType);
			}
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
		$("#SearchForm").attr("action",basePath+ "ea/coachreservationrecordcontent/ea_toSearchByReservationRecordSummary.jspa?pageNumber="+pNumber);
		document.getElementById("SearchForm").submit.click();
	})
	/**
	 * 弹出 某个教练 某天 所有时间段详细信息
	 */
	$(".JQueryflexme td[id]").click(function() {
		coacKey=$(this).attr("id").split("@")[0];
		rereAppdate=$(this).attr("id").split("@")[1];
		$("#ifr2").attr("src",basePath+"ea/coachreservationrecordcontent/ea_getListCoachReservationRecordContentPersonal.jspa?ddsrcoach.coacKey="+coacKey+"&ddsrreservationrecord.rereAppdate="+rereAppdate);
 	  	$("#jqmWindow2").jqmShow();
	});
	$("#jqmWindow2 #isBack2").click(function() {
		$("#jqmWindow2").jqmHide();
		window.location.reload();
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
		$("#ifr3").attr("src",basePath+"ea/coachreservationrecordcontent/ref_doDdsrStudentManagerAction.jspa?innerAction=showDdsrStudentList&searchZhuangtai=10");
 	  	$("#jqmWindow3").jqmShow();
	});
	$("#jqmWindow3 #isBack3").click(function() {
		$("#jqmWindow3").jqmHide();
		re_load();
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
					if(status){
						alert("预约成功!!");
						notoken=0;
					}else{
						alert("预约失败!!请稍后再试!!");
						notoken=0;
					}
					$("#jqmWindow3").jqmHide();
					$("#ifr2").attr("src",basePath+"ea/coachreservationrecordcontent/ea_getListCoachReservationRecordContentPersonal.jspa?ddsrcoach.coacKey="+coacKey+"&ddsrreservationrecord.rereAppdate="+rereAppdate);
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
				+ "ea/coachreservationrecord/ea_getListCoachReservationRecord.jspa?pageNumber=" + pNumber;
	}
}