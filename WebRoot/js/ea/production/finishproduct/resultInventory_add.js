$(function(){
	$(".JQueryreturn").click(function(){
		window.close();
	});
	$(".radioButton").each(function(){
		$this=$(this);
		$.ajax({
			url:basePath+"ea/resultInv/sajax_ea_getLowerField.jspa?ppID="+$(this).find(".ppID").val(),
			type:"post",
			success:function(data){
				var member = eval("(" + data + ")");
				var list=member.list;
				for (var i=0;i<list.length;i++){
					var tt="<input radio='fieldConStors[6].content' type='radio'  name='fieldConStors[6].content'   class='radioClassContent' value='"+list[i].goodsName+"' />";
					tt+="<input radio='fieldConStors[6].textID' type='hidden'  class='radioClass' value='"+list[i].ppID+"' />";
					tt+="<font class='sort yincang'>&nbsp;&nbsp;"+list[i].goodsName+"&nbsp;&nbsp;</font>";
					$this.find(".radioButtonTw").append(tt);
				}
			},error:function(data){
				alert("获取数据失败");
			}
		});
	});
	
	$(".radioAdmin").find(".radioClassContent").live("click",function(){
		//$(this).parent().find(".radioClassContent").attr("name","");
		$(this).parent().find(".radioClass").attr("name","");
		//$(this).attr("name",$(this).attr("radio"));
		$(this).next().attr("name",$(this).next().attr("radio"));
	});
$(".overTimeStartDate").blur(function() { // 起时间失去焦点事件
		
		$("td.errortime").find(".corect").remove();

		var start = $(".overTimeStartDate").val().replace(/-/g, '');
		var end = $(".overTimeEndDate").val().replace(/-/g, '');
		if (end != '' && start > end) {
			alert('起时间必须小于止时间！');
			$(".errortime").find(".input").val("");
			$(".overTimeStartDate").focus();
		}
	});

	$(".overTimeEndDate")
			.focus(function() { // 止时间获取焦点事件
				if ($(".overTimeStartDate").val() == '') {
					alert("请先填写起时间！");
					$(".overTimeStartDate").focus();
				}
			})
			.blur(
					function() { // 止时间失去焦点事件
						
						$("td.errortime").find(".corect").remove();
						var start = $(".overTimeStartDate").val();
						var end = $(".overTimeEndDate").val();
						if (end != '') {
							if (start.replace(/-/g, '') > end.replace(/-/g, '')) {
								if (times == '0') {
									alert('起时间必须小于止时间！');
									$(".errortime").find(".input").val("");
									$(".overTimeStartDate").focus();
									times++;
								}
								if ($(".overTimeStartDate").focus()) {
									times = '0';
								}
							} else {
								$(".errortime")
										.append(
												"<span class=\"corect\"><a class=\"tex\">&nbsp;&nbsp;&nbsp;&nbsp;</a></span>");
								$(".overTimeDays").val("");
								$(".overTimeHour").val("");
								var date1 = new Date(start.replace(/-/g, "/"));
								var date2 = new Date(end.replace(/-/g, "/"));
								var date3 = parseInt((date2.getTime() - date1
										.getTime()));
								var daysNum = Math.floor(date3
										/ (1000 * 3600 * 24)); // 相差天数
								if (daysNum != '0') {
									$(".overTimeDays").val(daysNum).attr(
											"disabled", false).attr("readonly",
											"readonly");
								}

								var hours = date3 % (24 * 3600 * 1000); // 计算天数后剩余的毫秒数
								var timeNum = (hours / (3600 * 1000))
										.toFixed(2); // 相差小时数
								if (timeNum != '0.00') {
									$(".overTimeHour").val(timeNum).attr(
											"disabled", false).attr("readonly",
											"readonly");
								}
								
								$(".transmission").val(start+" - "+end);
							}
						}
					});

	
	$(".JQuerySave").click(function(){
		$("#form").attr("target", "hidden").attr("action",basePath+"ea/resultInv/ea_addResultsData.jspa?fiveClear="+fiveClear+"&category="+category+"&fiveClearName="+fiveClearName);
		document.form.submit.click();
		token = 2;
	});
});
function re_load() {
	window.opener.location.reload();
	window.close();
}
