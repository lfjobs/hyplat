$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector

	$("#save").click(function(){
		$(".put3", $("#jqModelExtForm")).trigger("blur");
		if ($("#jqModelExtForm .error").length) {
			alert("请按标准填写！");
			return;
		}
		if($("select#leaveWork", $("#jqModelExtForm")).val() == ""){
			alert("请选择项目类别！");
			return;
		}
		var formd = $("#jqModelExtForm").serialize();
		var url = basePath+"ea/attendextleave/sajax_ea_ExtLeave.jspa?d="+Math.ceil(Math.random()*1000);
	    $.ajax({
	        url : encodeURI(url),
	        type : "get",
	        async : false,
	        dataType : "application/json",
	        data:formd,
	        success : function cbf(data) { 
	            var member = eval("(" + data + ")");
	            	alert("操作成功!");
	        		$("#jqModelExt1").jqmHide();
	           },
	        error : function cbf(data) {
	            alert("获取数据失败!");
	        }
	    });
	});
	
	$("#savetrim").click(function(){
		if ($("#jqModelTrimForm .error").length) {
			alert("请按标准填写！");
			return;
		}
		var formd = $("#jqModelTrimForm").serialize();
		var url = basePath+"ea/attendtrim/sajax_ea_appTrim.jspa?d="+Math.ceil(Math.random()*1000);
	    $.ajax({
	        url : encodeURI(url),
	        type : "get",
	        async : false,
	        dataType : "application/json",
	        data:formd,
	        success : function cbf(data) {
	            alert(eval("(" + data + ")"));
	        	$("#jqModelTrim").jqmHide();
	        	$(".jqmWindow").jqmHide();
	           },
	        error : function cbf(data) {
	            alert("获取数据失败!");
	        }
	    });
	});
	
	
	$("div.dbcil").click(function(){
		var id = $(this).attr("id");
		$("div#"+id).find(".jqmWindow").jqmShow();
	});
	$("#beginTime").blur(function() { // 起时间失去焦点事件

		var start = $("#beginTime").val().replace(/-/g, '');
		var end = $("#endTime").val().replace(/-/g, '');
		if (end != '' && start > end) {
			$("span#beginTime").text("起时间必须小于止时间");
			$("#beginTime").focus();
		}else{
			$("span#beginTime").text("");
		}
	});

	$("#endTime").focus(function() { // 止时间获取焦点事件
		if ($("#beginTime").val() == '') {
			alert("请先填写起时间!");
			$("#beginTime").attr("value","");
			$("#endTime").attr("value","");
			$("#beginTime").focus();
		}else{
			$("span#endTime").text("");
		}
	}).blur(function() { // 止时间失去焦点事件
		var start = $("#beginTime").val();
		var end = $("#endTime").val();
		if (end != '') {
		if (start.replace(/-/g, '') > end.replace(/-/g, '')) {
				$("#endTime").attr("value","");
				$("span#endTime").text("起时间必须小于止时间");
		} else {
			var date1 = new Date(start.replace(/-/g, "/"));
			var date2 = new Date(end.replace(/-/g, "/"));
			var date3 = date2.getTime() - date1.getTime();
			var days = Math.floor(date3 / (1000 * 3600 * 24)); // 相差天数
			
			var leave1=date3%(24*3600*1000);
			var hours=Math.floor(leave1/(3600*1000))
			
			var leave2=leave1%(3600*1000)        //计算小时数后剩余的毫秒数
			var minutes=Math.floor(leave2/(60*1000))
			
			var str = "";
			if(days != '0'){
				hours = days * 24 +hours;
			}
			if(hours != '0'){
				str += hours;
			}
//			if(minutes != '0'){
//				str += minutes + "分钟";
//			}
			
			$("#sumTime").attr("value",str);
		}
	}
	});
});
function foc(e){
	$(".button").attr('disabled',"true");
	document.location.href = basePath
		+ "/ea/attendlog/ea_getAttendLog.jspa?seaDate="+$(e).val();
}
function mouo(e){
	$(e).css("border-color","#eead00");
}
function moum(e){
	$(e).css("border-color","#e6e7e8");
}
function re_load() {
	if (token)
		document.location.href = basePath
				+ "/ea/attendlog/ea_getAttendLog.jspa?seaDate="+seaDate;
}
function extleave(e){
	document.jqModelExtForm.reset();
	$("#jqModelExt1").jqmShow();
	$("input#state").attr("value",e);
}
function signa(e){
	document.jqModelTrimForm.reset();
	$("#TrimTable").find("#days").attr("value",e);
	$("#jqModelTrim").jqmShow();
}
//签到签退
function sign(e){
	var url = basePath+"ea/attendlog/sajax_ea_sign.jspa?sign="+e+"&d="+Math.ceil(Math.random()*1000);
    $.ajax({
        url : encodeURI(url),
        type : "get",
        async : false,
        dataType : "json",
        success : function cbf(data) { 
            var member = eval("(" + data + ")");
            	alert(member.suc );
            	document.location.href = basePath
				+ "/ea/attendlog/ea_getAttendLog.jspa?seaDate=";
           },
        error : function cbf(data) {
            alert("获取数据失败!");
        }
    });
}
//选择责任人
function paret(e){
		var arr = e.split(",");
		$("#approveName").attr("value",arr[2]);
		$("#approveId").attr("value",arr[0]);
	  	//隐藏弹出层
	  	$("#accift").jqmHide();
	}		