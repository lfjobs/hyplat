$(document).ready(function() {
	var nowtime=new Date().getTime();//当前时间
	var nowdate=toda;
	var tomrowday=dateOperator(nowdate,1,"+");
	var startdate=nowdate+" 17:30:00";
	var enddate=tomrowday+" 08:30:00";
	var oldtime=GetTimeByTimeStr(nowdate+" 08:30:00").getTime();//今天早上8:30的时间点
	var starttime=GetTimeByTimeStr(startdate).getTime();//应该写日志的开始时间,当天17:30
	var endtime=GetTimeByTimeStr(enddate).getTime();//应该写日志的结束时间,次日8:30
	if(nowtime>oldtime&&nowtime<starttime){
		$("input#todaydate", $("#logbookaddForm")).hide();
		$("input#todaydate", $("#logbookaddForm")).after("<font color='red'>不是写日志的时间段</font>");
	}else{
		$("input#todaydate", $("#logbookaddForm")).show();
	}
	
	
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
	// .jqDrag('.drag');// 添加拖拽的selector
	$('.logbook').flexigrid({
				height : 395,
				width : 'auto',
				minwidth : 30,
				title : '工作日志----当前人员' + staffName,
				minheight : 80,
				buttons : [{
					name : '添加',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, {
					name : '修改',
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
				}, /*{
					name : '全部保存',
					bclass : 'add',
					onpress : action
						// 当点击调用方法
					}, {
					separator : true
				}, */{
					name : '设置每页显示条数',
					bclass : 'mysearch',
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
				}, {
					name : '查询',
					bclass : 'mysearch',
					onpress : action
						// 当点击调用方法
					}]
			});
	function action(com, grid) {
		switch (com) {
			case '添加' :
				logBookID="";
			/*	$("#sa").after($("#sa").clone(true).attr("id", "sa" + select)
						.addClass("check"));
				$("#sa" + select).find(':input:gt(0)').each(function() {
					$(this).attr("name",
							"logbookmap[" + select + "]." + this.name);
				})
				$("#sa" + select).show();
				select++;*/
				
				$("#logbookaddForm").find(".error").remove();
				$("#logbookaddForm").find(".corect").remove();
				mydate();
//				getconten2();
				//getjobplan();
				//add();
				//$("#sa1" + (select-1)).find("a").css("display","none");
				$("#jqModel").jqmShow();
				break;
			case '修改' :
				if (logBookID == '') {
					alert("请选择！");
					return;
				}
				if ($("#" + logBookID + " #status").val() == '01') {
					alert("已加锁不可修改");
					return;
				}
				if ($("tr#" + logBookID).find("input#scoreSorts").val() != 'scode201007306kdf8m76me0000000002') {
					alert("不可修改");
					return;
				}
				var todate = $("span#todaydate", $("tr#" + logBookID)).text();
				var tomanths = todate.substring(0,todate.lastIndexOf("-"));
				var url = basePath + "ea/logbook/sajax_n_ea_isLocked.jspa?logbook.staffID="+logbookstaffID+"&tomanths="+tomanths;
				$.ajax({ //判断个人日志是否被加锁
						url : encodeURI(url),
						type: "get",
						async: true,
						dataType: "json",
						success: function cbf(data){
							var member = eval("(" + data + ")");
							var islock = member.islock;
							if(islock != ''){
							 	alert("此人员"+islock+"的日志已被加锁,不可修改！");
							 	return;
							}
							$t = $("div#jqModel");
							$p = $("tr#" + logBookID);
							$p.find("span[id]").each(function() {
								$t.find("#" + this.id).val($(this).text());
							});
							$p.find("input[id]").each(function() {
								$t.find("#" + this.id).val($(this).val());
							});
							
							$("#logbookaddForm").find(".error").remove();
							$("#logbookaddForm").find(".corect").remove();
							getconten2();
							$("#jqModel").jqmShow();
						}
				});
				break;
			case '删除' :
				if (logBookID == '') {
					alert("请选择！");
					return;
				}
				if ($("#" + logBookID + " #status").attr("value") == '01') {
					alert("已加锁不可删除");
					return;
				}
				if ($("tr#" + logBookID).find("input#scoreSorts").val() != 'scode201007306kdf8m76me0000000002') {
					alert("不可删除");
					return;
				}
				var todate = $("span#todaydate", $("tr#" + logBookID)).text();
				var tomanths = todate.substring(0,todate.lastIndexOf("-"));
				var url = basePath + "ea/logbook/sajax_n_ea_isLocked.jspa?logbook.staffID="+logbookstaffID+"&tomanths="+tomanths+"&date="+new Date();
				$.ajax({ //判断个人日志是否被加锁
						url : encodeURI(url),
						type: "get",
						async: true,
						dataType: "json",
						success: function cbf(data){
							var member = eval("(" + data + ")");
							var islock = member.islock;
							if(islock != ''){
							 	alert("此人员"+islock+"的日志已被加锁,不可删除！");
							 	return;
							}
							$f = $('#logFrom');
							if (confirm("是否删除？")) {
								if (notoken)
									return;
								notoken = 1;
								$f.attr("target", "hidden")
									.attr("action",basePath
										+ "ea/logbook/ea_delLogBook.jspa?pageNumber="
										+ ppageNumber + "&logbook.staffID="
										+ logbookstaffID + "&sdate="
										+ psdate + "&edate=" + pedate
										+ "&logbook.logBookID=" + logBookID);
								document.logFrom.submit.click();
							//	$("tr#" + logBookID,"#tab0").remove();
								logBookID = '';
								token = 11;
							}
						}
				});
				break;
			case '设置每页显示条数' :
				var url = basePath + "ea/logbook/ea_getListLogBook.jspa?sdate="
						+ psdate + "&edate=" + pedate + "&scoreSort="
						+ scoreSort + "&status=" + status;
				numback(url);
				break;
			case '查询' :
				$("#jqModelSearch").jqmShow();
				break;
			case '导出' :
				var url = basePath
						+ "ea/logbook/ea_showExcel.jspa?logbook.staffID="
						+ logbookstaffID + "&sdate=" + psdate + "&edate="
						+ pedate + "&scoreSort=" + scoreSort + "&status="
						+ status;
				open(url);
				break;
		}
	}
	
	$(".logbook tr[id]").click(function() {
		$("input.JQuerypersonvalue", $(this))
				.attr("checked", "checked");
		logBookID = this.id;
	});
	$(".logbook tr[id]").dblclick(function() {
		$("input.JQuerypersonvalue", $(this))
				.attr("checked", "checked");
		logBookID = this.id;
		action("修改");
	});
	$("#tosearch").click(function() {
		$f = $('#postSearchForm');
		$f.attr("action", basePath
						+ "ea/logbook/ea_getListLogBook.jspa?pageNumber="
						+ ppageNumber);
		document.postSearchForm.submit.click();
	});
	
	

	//新增一行
	$("#newline").click(function(){
		selects++;
		// 克隆一行并修改文本框的name
		$("#kelong").before(
		
		$("#kelong").clone(true).show().attr("class","checkgoods")
		.attr("id",
				"kelong" + selects)
		);
		$("#kelong" + selects).find(':input').each(function() {
			$(this).attr("name","logcontenmap[" + selects + "]." + this.name);
			
			
			
		});
		
		$("#kelong" + selects).find("div#slider").attr("id","slider"+selects);
		
		$("#kelong" + selects).find("input#amount").attr("id","amount"+selects);
	
		
       var scirpt = "$('#slider"+selects+"').slider({range: 'min',value: 0,min: 0,max: 100,slide: function( event, ui ) {$('#amount"+selects+"').val(ui.value+'%');}});" +
        		"$('#amount"+selects+"').val($('#slider"+selects+"').slider( 'value' )+'%');";

		  $("body").append("<div id='div"+selects+"'><script type='text/javascript'>$(function(){"+scirpt+"});</script></div>");



	});
	
	//删除
	
	$(".removeline").click(function(){
		
		$(this).parent().parent().remove();
//		var slide = $(this).parent().parent().find("div.sld").attr("id");
//		
//		if(slide!="slider1"){
//		alert(slide.substring(6))
//			alert($("#div"+slide.substring(6)).text());
//		}
		

		
	});
	

	
	
	// 保存
	$("input.JQuerySubmitgd").click(function() {
		if($("input#todaydate:hidden", $("#logbookaddForm")).length>0){
			return;
		}
		if (notoken) {
			alert("正在提交数据！请稍等");
			return;
		}
		var todate = $("input#todaydate", $("#logbookaddForm")).val();
		var tomanths = todate.substring(0,todate.lastIndexOf("-"));
		var url = basePath + "ea/logbook/sajax_n_ea_isLocked.jspa?logbook.staffID="+logbookstaffID+"&tomanths="+tomanths;
		$.ajax({ //判断个人日志是否被加锁
				url : encodeURI(url),
				type: "get",
				async: true,
				dataType: "json",
				success: function cbf(data){
					var member = eval("(" + data + ")");
					var islock = member.islock;
					if(islock != ''){
					 	alert("此人员"+islock+"的日志已被加锁,不可再添加！");
					 	return;
					}
					$(".put3").trigger("blur");
					$(".timeformat").trigger("blur");
					$(".posnum").trigger("blur");
					if ($("form .error").length) {
						return;
					}
					var starttime = $("#startdate",$("#logbookaddForm")).val();
					var endtime = $("#enddate",$("#logbookaddForm")).val();
					var start1;
					var start2;
					var end1;
					var end2;
					if(starttime.substring(0,1) == '0'){
						 start1 = parseInt(starttime.substring(1,starttime.indexOf(":")));
					}else{
						 start1 = parseInt(starttime.substring(0,starttime.indexOf(":")));
					}
					
					if(endtime.substring(0,1) == '0'){
						 end1 = parseInt(endtime.substring(1,endtime.indexOf(":")));
					}else{
						 end1 = parseInt(endtime.substring(0,endtime.indexOf(":")));
					}
					
					if(starttime.substring(starttime.indexOf(":")+1,starttime.indexOf(":")+2) == '0'){
						 start2 = parseInt(starttime.substring(starttime.indexOf(":")+2));
					}else{
						 start2 = parseInt(starttime.substring(starttime.indexOf(":")+1));
					}
					
					if(endtime.substring(endtime.indexOf(":")+1,endtime.indexOf(":")+2) == '0'){
						  end2 = parseInt(endtime.substring(endtime.indexOf(":")+2));
					}else{
						  end2 = parseInt(endtime.substring(endtime.indexOf(":")+1));
					}
					
					if(start1 > end1){
						alert("起时间必须小于止时间！");
						return;
					}else if(start1 == end1){
						if(start2 >= end2){
							alert("起时间必须小于止时间！");
							return;
						}
					}
					
					
					var con = "";
					$("#logbookaddForm").find('.rem').each(function (){
						con+= " "+$(this).val();
						
					});
				//	alert(con);
					$("#jobContent",$("#logbookaddForm")).attr("value",con);
					if($("#jobContent",$("#logbookaddForm")).val() == ''){
						alert("工作内容描述不能为空！");
						return;
					}
					if(len($("#jobContent",$("#logbookaddForm")).val()) >= 256){
						alert("工作内容描述总共不能超过256个字符！");
						return;
					}
					
					notoken = 1;
					$("#logbookaddForm")
							.attr("target", "hidden")
							.attr(
									"action",
									basePath
											+ "ea/logbook/ea_savelog.jspa?pageNumber="
											+ ppageNumber);
					document.logbookaddForm.submit.click();
					if(logBookID == ''){
						document.logbookaddForm.reset();
						$("tr.checkgoods").remove();
						$("#logbookaddForm").find(".corect").remove();
						$("#kelong").before(
								
								$("#kelong").clone(true).show().attr("class","checkgoods")
								.attr("id",
										"kelong1")
								);
						
						$("#kelong1").find(':input').each(function() {
							$(this).attr("name","logcontenmap[1]." + this.name);
						});
						
			
						$("#kelong1").find("div#slider").attr("id","slider1");
						
						$("#kelong1").find("input#amount").attr("id","amount1");
					
						
				       var scirpt = "$('#slider1').slider({range: 'min',value: 0,min: 0,max: 100,slide: function( event, ui ) {$('#amount1').val(ui.value+'%');}});" +
				        		"$('#amount1').val($('#slider1').slider( 'value' )+'%');";

						  $("body").append("<div id='div1'><script type='text/javascript'>$(function(){"+scirpt+"});</script></div>");
					
						$("#todaydate",$("#logbookaddForm")).click();
						mydate();
						token = 1;
					}else{
						token = 2;
					}
				}
		});
	});
	
	//返回
	$(".JQueryClose").click(function() {
		token = 13;
		re_load();
	});
	//
	$("#tab1 a").click(function(){
		$(this).parent().parent().remove();
	});
});
//处理数据
function len(s) { var l = 0; var a = s.split(""); for (var i=0;i<a.length;i++) { if (a[i].charCodeAt(0)<299) { l++; } else { l+=2; } } return l; }
function add(){
	$("#sa1").after($("#sa1").clone(true).attr("id", "sa1" + select)
			.addClass("check"));
	$("#sa1" + select).find('input').each(function() {
		$(this).attr("name",
				"logcontenmap[" + select + "]." + this.name);
	});
	$("#sa1" + select).find('select').each(function() {
		$(this).attr("name",
				"logcontenmap[" + select + "]." + this.name);
	});
	$("#sa1" + select).find("select").show();
	$("#sa1" + select).show();
	
	select++;
}
function del(t){
	$(t).parent().parent().remove();
}
function mydate(){
	var myDate = new Date();
    var month = (myDate.getMonth()+1) < 10 ? ("0" + (myDate.getMonth()+1)) : (myDate.getMonth()+1);
    var day = myDate.getDate() < 10 ? ("0" + myDate.getDate()) : myDate.getDate();
    $("#todaydate",$("#logbookaddForm")).val(myDate.getFullYear()+"-"+month+"-"+day);
}
 // 获取COSJobPlan工作计划集合
function getjobplan(){

	var todate = $("input#todaydate", $("#logbookaddForm")).val();
	var tomanths = todate.substring(0,todate.lastIndexOf("-"));
	var url = basePath + "ea/logbook/sajax_n_ea_getJob.jspa?logbook.logBookID="+logBookID+"&tomanths="+tomanths+"&date="+new Date();
	$.ajax({ 
			url : encodeURI(url),
			type: "get",
			async: false,
			dataType: "json",
			success: function cbf(data){
				var member = eval("(" + data + ")");
				var jobList = member.jobList;
				$("#logbookaddForm").find('.sel').each(function (){
					this.length = 1; 
				});
				var htmlStr = "";
				for(var i=0;i<jobList.length;i++) {
					htmlStr += "<option value='"+jobList[i]+"'>"+jobList[i]+"</option>";
				}
				$(".sel").append(htmlStr);
			}	
	});
}

function getconten(){
	var todate = $("input#todaydate", $("#logbookaddForm")).val();
	var tomanths = todate.substring(0,todate.lastIndexOf("-"));
	var url = basePath + "ea/logbook/sajax_n_ea_getConten.jspa?logbook.logBookID="+logBookID+"&tomanths="+tomanths+"&date="+new Date();
	$.ajax({ 
			url : encodeURI(url),
			type: "get",
			async: true,
			dataType: "json",
			success: function cbf(data){
				var member = eval("(" + data + ")");
				var logList = member.logList;
				var htmlStr = "";
				for(var i=0;i<logList.length;i++) {
					htmlStr += "<tr id='"+logList[i].logcontenid+"' class='trl''>";	
					htmlStr += "<td>&nbsp;<a href='#' id='' onclick='del(this)' ><img src='"+basePath+"images/u16.png'  style='margin-top: 5px'  width='16' height='16'   border='0'/></a>" +
							"项目名称:<select class='sel' name='logcontenmap[" + select + "].jpbname' style='width:130px'><option value='其他'>其他</option></select>";
					htmlStr += " 计划类别:<select name='logcontenmap[" + select + "].jobstatus'>";
					if(logList[i].jobstatus == "基本任务"){
						htmlStr += "<option value='基本任务' selected>基本任务</option><option value='额外任务'>额外任务</option>";
					}else{
						htmlStr += "<option value='基本任务' >基本任务</option><option value='额外任务' selected>额外任务</option>";
					}
					htmlStr += "</select>";
					htmlStr += " 完成度:<input type='text' name='logcontenmap[" + select + "].contactcom'  size='5' value='"+logList[i].contactcom+"'/>";
					htmlStr += " 描述:<input type='text' name='logcontenmap[" + select + "].remark' size='45' class='rem' value='"+logList[i].remark+"'/></td></tr>";
					select++;
				}
				$("#tab1").append(htmlStr);
				getjobplan();
				for(var i=0;i<logList.length;i++) {
					$(".trl").each(function(){
						if(logList[i].logcontenid == this.id){
							$("tr#"+this.id).find("select.sel").find("option[value='"+logList[i].jpbname+"']").attr("selected","selected");
							
						}
					});	
				}
			}	
	});
}
//用来替换getconten方法
function getconten2(){
	var todate = $("input#todaydate", $("#logbookaddForm")).val();
	var tomanths = todate.substring(0,todate.lastIndexOf("-"));
	var url = basePath + "ea/logbook/sajax_n_ea_getConten.jspa?logbook.logBookID="+logBookID+"&tomanths="+tomanths+"&date="+new Date();
	$.ajax({ 
			url : encodeURI(url),
			type: "get",
			async: true,
			dataType: "json",
			success: function cbf(data){
				var member = eval("(" + data + ")");
				var logList = member.logList;
				var projectlist = member.projectlist;
				var taskmap = member.taskmap;
				var htmlStr = "";
				var optionstr ="";
			
				for ( var x = 0; x < projectlist.length; x++) {
					
					optionstr+="<option value='"+projectlist[x].csbid+"'>"+projectlist[x].projectname+"</option>";
				}
				optionstr+="<option value='其他'>其他</option>";
				
				
				for(var i=0;i<logList.length;i++) {
					htmlStr+="<tr class='checkgoods "+logList[i].logcontenid+"' id='kelong"+(i+2)+"'>";
					   
					htmlStr+="<td align='center'>" +
							"<select class='sel' onchange='getTask(this)' name='logcontenmap["+(i+2)+"].projectID'>"+optionstr+"</select>" +
							
							"</td>";
					htmlStr+="<td align='center'>" +
							"<select class='tasksel' style=\"width:130px;\"  onchange='getBaifen(this)' name='logcontenmap["+(i+2)+"].jobTaskID'></select>" +
							"</td>";
			
					htmlStr+="<td align='center'><input type='text' name='logcontenmap["+(i+2)+"].remark' size='55' value='"+logList[i].remark+"' class='rem' /></td>";
					htmlStr+="<td align='center'><input type='text' id='amount"+(i+2)+"' name='logcontenmap["+(i+2)+"].contactcom' size='3' readonly value='"+logList[i].contactcom+"'/></td>";
					htmlStr+="<td align='center'><div id='slider"+(i+2)+"' class='sld' style='width:150px;'></div></td>";
					htmlStr+="<td align='center'><img src='"+basePath+"images/gtk-del.png'  class=\"removeline\" onclick='removetr(this);' style='cursor:pointer;'/></td></tr>";
					
					
				
				}
				
			
				$("#kelong1").remove();
				$("#kelong").before(htmlStr);

				for(var i=0;i<logList.length;i++) {
				var contactcom = logList[i].contactcom;
				if(contactcom==""){
					contactcom="1%";
				}
				contactcom = contactcom.substring(0,contactcom.indexOf("%"));
				
				
				var scirpt = "$('#slider"+(i+2)+"').slider({range: 'min',value:"+contactcom+",min: 0,max: 100,slide: function( event, ui ) {$('#amount"+(i+2)+"').val(ui.value+'%');}});" +
	        	"$('#amount"+(i+2)+"').val($('#slider"+(i+2)+"').slider( 'value' )+'%');";
			     $("body").append("<div id='div"+(i+2)+"'><script type='text/javascript'>$(function(){"+scirpt+"});</script></div>");
				
				
				}
				selects+=logList.length;
				
				//任务
				
				$.each(taskmap,function(key,list){ 
					var taskoption = "";
					for ( var j = 0; j < list.length; j++) {
						taskoption+="<option value='"+list[j].jobTaskID+"'>"+list[j].taskName+"</option>";
					}
					taskoption+="<option value='其他'>其他</option>";
				    $("tr."+key).find(".tasksel").html(taskoption);
				});
				
				
				//选中
				for(var i=0;i<logList.length;i++) {
					if(logList[i].projectID==""||logList[i].projectID=="其他"){
						logList[i].projectID="其他";
					}
					
					if(logList[i].jobTaskID==""||logList[i].jobTaskID=="其他"){
						logList[i].jobTaskID="其他";
					}
						
			     $("tr."+logList[i].logcontenid).find("select.sel").find("option[value='"+logList[i].projectID+"']").attr("selected","selected");
			     $("tr."+logList[i].logcontenid).find("select.tasksel").find("option[value='"+logList[i].jobTaskID+"']").attr("selected","selected");
							
						
				}
				
				
				
//				getjobplan();
	
			}	
	});
}
function re_load() {
	if (token)
		document.location.href = basePath
				+ "/ea/logbook/ea_getListLogBook.jspa?pageNumber="
				+ ppageNumber + "&pageForm.pageNumber="
				+ $("#pageNumber").attr("value");
}

function getTask(obj){
 var url = basePath+"/ea/logbook/sajax_n_ea_getTaskByproject.jspa";
 $.ajax({
	 url:url,
	 type:"get",
	 async:false,
	 dataType:"json",
	 data:{
		 "projectplanbudget.csbid":$(obj).val()
	 },
	 success:function(data){
		var member = eval("("+data+")");
		var tasklist  = member.tasklist;
		var optionstr="";
	
		for ( var j = 0; j < tasklist.length; j++) {
			optionstr+="<option value='"+tasklist[j].jobTaskID+"'>"+tasklist[j].taskName+"</option>";
		}
		
	    optionstr+="<option value='其他'>其他</option>";
		
		$(obj).parent().next("td").find("select").html(optionstr);
		
	
		var num = $(obj).parent().parent().attr("id").substring(6);
		if($(obj).parent().next("td").find("select").val()!="其他"){
			if(tasklist[0].finishrate==""){
				tasklist[0].finishrate="0%";
			}
		var contactcom = tasklist[0].finishrate.substring(0,tasklist[0].finishrate.indexOf("%"));
		$("#slider"+num).slider("value",contactcom);
		$("#amount"+num).val(tasklist[0].finishrate);
		}else{
			$("#slider"+num).slider("value",0);
			$("#amount"+num).val("0%");
		}
	 },
	 error:function(data){
		 
	 }
	 
 });
}


function getBaifen(obj){
	 var url = basePath+"/ea/logbook/sajax_n_ea_getTaskByID.jspa";
	 $.ajax({
		 url:url,
		 type:"get",
		 async:false,
		 dataType:"json",
		 data:{
			 "jobTask.jobTaskID":$(obj).val()
		 },
		 success:function(data){
			var member = eval("("+data+")");
			var task  = member.task;
			var num = $(obj).parent().parent().attr("id").substring(6);
			if(task==null){
				$("#slider"+num).slider("value",0);
				$("#amount"+num).val("0%");
			}else{
				if(task.finishrate==""){
					task.finishrate="0%";
				}
			var contactcom = task.finishrate.substring(0,task.finishrate.indexOf("%"));
			$("#slider"+num).slider("value",contactcom);
			$("#amount"+num).val(task.finishrate);
			}
			
		 },
		 error:function(data){
			 
		 }
		 
	 });
	
	
}

function removetr(obj){
	$(obj).parent().parent().remove();
}
function getTime(day){       
    re = /(\d{4})(?:-(\d{1,2})(?:-(\d{1,2}))?)?(?:\s+(\d{1,2}):(\d{1,2}):(\d{1,2}))?/.exec(day); 
    return new Date(re[1],(re[2]||1)-1,re[3]||1,re[4]||0,re[5]||0,re[6]||0).getTime();
}
function dateOperator(date, days, operator)

{

	date = date.replace(/-/g, "/"); //更改日期格式  
	var nd = new Date(date);
	nd = nd.valueOf();
	if (operator == "+") {
		nd = nd + days * 24 * 60 * 60 * 1000;
	} else if (operator == "-") {
		nd = nd - days * 24 * 60 * 60 * 1000;
	} else {
		return false;
	}
	nd = new Date(nd);

	var y = nd.getFullYear();
	var m = nd.getMonth() + 1;
	var d = nd.getDate();
	if (m <= 9)
		m = "0" + m;
	if (d <= 9)
		d = "0" + d;
	var cdate = y + "-" + m + "-" + d;
	return cdate;
}
//常规方法不兼容new Date("2016-01-01")这种格式.下面方法兼容ff,chrome
function GetTimeByTimeStr(dateStr) {
	var timeArr = dateStr.split(" ");
	var d = timeArr[0].split("-");
	var t = timeArr[1].split(":");
	return new Date(d[0], (d[1] - 1), d[2], t[0], t[1], t[2]);
}