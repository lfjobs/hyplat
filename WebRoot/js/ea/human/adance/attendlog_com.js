$(document).ready(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	$('.attendlog_com').flexigrid({
				height : 'auto',
				width : 'auto',
				title : '考勤记录汇总' ,
				minwidth : 30,
				minheight : 80,
				buttons : [{
					name : '考勤',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				},  {
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
				}]
			});
	function action(com, grid) {
		switch (com) {
			case '考勤' :
				if (logid == "") {
					alert("请选择！");
					return;
				}
				var url = basePath +"ea/attendlog/sajax_ea_getLogsCom.jspa?staffId="+logid+"&date=<%=new Date()%>";
				getlogscom(url);
				$("#jqModellog").jqmShow();
				break;
			case '查询' :
				$("#jqModelSerch").jqmShow();
				break;
			case '设置每页显示条数' :
				var url = basePath
						+ "ea/attendlog/ea_getLogCom.jspa?1=1";
				numback(url);
				break;
		}
	}

	$(".attendlog_com tr[id]").click(function() {
		$("input.JQuerypersonvalue", $(this))
				.attr("checked", "checked");
		logid = this.id;
	});
	$(".attendlog_com tr[id]").dblclick(
			function() {
				$("input.JQuerypersonvalue", $(this)).attr(
						"checked", "checked");
				logid = this.id;
				action("考勤");
			});
	// 查询相关操作
	$("#searchlogcom").click(function() {
		$("#jqModelSerchForm").attr(
				"action",
				basePath + "ea/attendlog/ea_getLogCom.jspa?pageNumber="
						+ ppageNumber);
		document.jqModelSerchForm.submit.click();
	});
	// 个人查询相关操作
	$("#searchlogscom").click(function() {
		var url = basePath +"ea/attendlog/sajax_ea_getLogsCom.jspa?staffId="+logid+"&seaDate="+$("#seaDate").val()+"&date=<%=new Date()%>";
		getlogscom(url);
	});
	$("input.JQueryreturn").click(function() {
				$("#jqModelAdd").jqmHide();
				re_load();
			});
});
function re_load() {
	if (token){
		document.location.href = basePath
				+ "ea/attendlog/ea_getLogCom.jspa?pageNumber="
				+ ppageNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value");
	}
}//单位下所有的部门列表
function getlogscom(url){
    $.ajax({
			url : encodeURI(url),
			type : "get",
			async : true,
			dataType : "json",
			success : function cbf(data) {
				var member = eval("(" + data + ")");
				var mapwc = member.mapwc;
				var thtml = "<tr style='height:30px;'><th>星期一</th><th>星期二</th><th>星期三</th><th>星期四</th><th>星期五</th><th>星期六</th><th>星期日</th></tr>";
				
				for(var i = 0 ; i < mapwc.length; i++){
					thtml += "<tr style='height:100px;background-color:#87aee5;'>";
					for(var j = 0 ; j < mapwc[i].length; j++){
						if(mapwc[i][j] == null){
							thtml += "<td style='background-color:#fafafa;margin:0px;'>&nbsp;</td>";
						}else{
							thtml += "<td style='background-color:#fafafa;margin:0px;' align='center' valign='top'>";
							thtml += mapwc[i][j].days+"<p>";
							
							if(mapwc[i][j].listLog != null){
								if(mapwc[i][j].listLog.signcome != null){
									thtml += "签到：" + mapwc[i][j].listLog.signcome+"<p>";
								}else{
									thtml += "签到：无<p>";
								}
								if(mapwc[i][j].listLog.signgo != null){
									thtml += "签退：" + mapwc[i][j].listLog.signgo+"<p>";
								}else{
									thtml += "签退：无<p>";
								}
							}else{
								thtml += "签到：无<p>";
								thtml += "签退：无<p>";
							}
							if(mapwc[i][j].listStus != null){
								var stus = mapwc[i][j].listStus;
								for(var m = 0 ; m < stus.length ; m++){
									thtml += stus[m].alStatus + "&nbsp;&nbsp;" + stus[m].altime + "<p>";
								}
							}
							thtml += "</td>";
						}
					}
					thtml += "</tr>";
				}
				$("table#logTable").text("");
				$("table#logTable").append(thtml);
			}
	});
	
}