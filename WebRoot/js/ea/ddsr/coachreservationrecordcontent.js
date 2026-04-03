$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector

	$('.address').flexigrid({
				height : 300,
				width : 'auto',
				minwidth : 30,
				title : '预约记录管理',
				minheight : 80
			});
	/**
	 * 选择 某个教练 某天 多个时间段详细信息
	 */
	$("input:checkbox").click(function(){
		rereKeyArray=new Array();
		$("table.address").find("input:checkbox[name=check]:checked").each(function(){
			rereKeyArray.push($(this).val());
		});
	});
	/*$("tr[id]",$("table.address")).click(function(){
		$(this).find("input:checkbox[name=check]").attr("checked",!$(this).find("input:checkbox[name=check]").is(":checked"));
		rereKeyArray=new Array();
		$("table.address").find("input:checkbox[name=check]:checked").each(function(){
			rereKeyArray.push($(this).val());
		});
	});*/
	/**
	 * 
	 * 查看详情
	 */
	$("table.address span.detailsOfstaffID").click(function(){
		var staffID=$.trim($(this).attr("id"));
		var url =basePath+ "ea/enroll/ea_getHumanResource.jspa?showType=edit"+"&cstaff.staffID="+staffID;
		window.open(url, '','scrollbars=yes,resizable=yes,channelmode');
	});
	/**
	 * 选择取消预约
	 */
	$("table.address span.rereStudent").click(function(){
		rereKey=$(this).parents("tr[id]").attr("id");
		stureKey=$(this).attr("id");
		if(studKey!=""){
			var rereStudentStudKey=$.trim($(this).next("#rereStudentStudKey").attr("value"));
			 if(rereStudentStudKey!=studKey){
				 alert("不可操作他人预约数据!!");
				 return;
			 }
		}
		if(notoken){
			return;
		}
		notoken=1;
		if(confirm("确定取消预约?")){
			$("#ReservationRecoardForm #rereKey").val(rereKey);
			$("#ReservationRecoardForm #stureKey").val(stureKey);
			var url = basePath+ "ea/coachreservationrecordcontent/sajax_ea_deleteReservationRecordContentPersonal.jspa?";
			$.ajax({
					url : encodeURI(url+"date01="+ new Date()),
					type : "get",
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
						window.location.reload();
					},
					error : function cbf(data) {
						alert("数据获取失败！");
						notoken=0;
					}
				});
		}else{
			notoken=0;
		}
	});
});
function re_load() {
	if (token)
		document.location.href = basePath
				+ "ea/coachreservationrecordcontent/ea_getListCoachReservationRecordContentPersonal.jspa?ddsrcoach.coacKey="+coacKey+"&studKey="+studKey+"&studName="+studName;
}
