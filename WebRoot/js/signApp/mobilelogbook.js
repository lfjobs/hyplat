$(document).ready(function(){
	if(logbookstaffID=="")
	{
		//document.location.href=pbasePath+"ea/mobilelogbook/ea_getListLogBook.jspa?pageNumber="+ppageNumber+"&pageForm.pageNumber="+$("#pageNumber").attr("value");
		document.location.href=pbasePath+"ea/mobilelogbook/ea_getListLogBook.jspa";
	}
	$("#append").click(function(){
		$("#sa").after($("#sa").clone(true).attr("id","sa"+select).addClass("check"));
		$("#sa"+select).find(':input:gt(0)').each(function(){
			$(this).attr("name","logbookmap["+select+"]."+this.name);
		})
		$("#sa"+select).show(); 
		select++;
	});
	$("#edit").click(function(){
		if(logBookID=='')
		{
		   alert("请选择！")
		   return;
		}
		var re = 0;
		var todate=$("span#todaydate",$("tr#"+logBookID)).text();
		$("#loglock option",$("#postSearchForm")).each(function(j,tmps){
			var startDate= this.value;
			var endDate =this.text;
			if(Date.parse(todate.replace(/-/g, "/")) >= Date.parse(startDate.replace(/-/g, "/"))  && Date.parse(todate.replace(/-/g, "/")) <= Date.parse(endDate.replace(/-/g, "/")))
			{
				re = 1;
			}
		})
		if(re){
			alert("此时间段的日志已加锁,不能修改！");
			return;
		}
		if($("#"+logBookID+" #status").attr("value") == '01'){
			alert("已加锁不可修改")
			return;
		}
		if($("#"+logBookID+" input[name=scoreSort]").attr("value") != 'scode201007306kdf8m76me0000000002'){
			alert("不可修改")
			return;
		}
		$p = $("tr#"+logBookID);
		if($p.hasClass("check")){
			return;
		}
		$p.addClass("check");
		$p.find(':input:gt(0)').each(function(){
			$(this).attr("name","logbookmap["+select+"]."+this.name);
		})
		select++;
		$("#:"+logBookID).children("."+logBookID).children("span").addClass("model");
		$("#:"+logBookID).children("."+logBookID).children(".todaydate").css({'display':'none'});
		$("#:"+logBookID).children("."+logBookID).children("input").removeClass("model");
	});
	$("#save").click(function(){
		if(notoken)return;
		if(select==1)
		{
			return;
		}
		notoken = 1;
		$f = $('#logbookForm');
		var re = 0;
		$("input#todaydate",$(".check")).each(function(i,tmp){
			var todate=this.value;
			var $s=$(this);
			if(todate == ""  ){
				$s.css("background-color","red");
				re = 1;
			}
			$("#loglock option",$("#postSearchForm")).each(function(j,tmps){
				var startDate= this.value;
				var endDate =this.text;
				if(Date.parse(todate.replace(/-/g, "/")) >= Date.parse(startDate.replace(/-/g, "/"))  && Date.parse(todate.replace(/-/g, "/")) <= Date.parse(endDate.replace(/-/g, "/")))
				{
					$s.css("background-color","red");
					re = 2;
				}
			})
		})
		if(re){
			if(re==1)
			{
				alert("日期不能为空！");
			}else
			{
				alert("该时间已加锁，请重新输入日期！");
			}
			notoken = 0;
			return;
		}
		//$('#logbookForm').attr("target","hidden").attr("action",pbasePath+"ea/phonelogbook/t_ea_saveLogBook.jspa?pageNumber="+ppageNumber);
		$('#logbookForm').attr("target","hidden").attr("action",pbasePath+"ea/phonelogbook/t_ea_saveLogBook.jspa");
		document.logbookForm.submit.click();
		token = 2;
		return;
	});
	$("#delete").click(function(){
		if(logBookID=='')
		{
			alert("请选择！");
			return;
		}
		var re = 0;
		var todate=$("span#todaydate",$("tr#"+logBookID)).text();
		$("#loglock option",$("#postSearchForm")).each(function(j,tmps){
			var startDate= this.value;
			var endDate =this.text;
			if(Date.parse(todate.replace(/-/g, "/")) >= Date.parse(startDate.replace(/-/g, "/"))  && Date.parse(todate.replace(/-/g, "/")) <= Date.parse(endDate.replace(/-/g, "/")))
			{
				re = 1;
			}
		})
		if(re){
			alert("此时间段的日志已加锁,不能删除！");
			return;
		}
		if(logBookID.substring(0,2) == 'sa')
		{
			if (confirm("是否删除？")) {
				$("#"+logBookID).remove();
				logBookID =''
			}
			return;
		}
		if($("#"+logBookID+" #status").attr("value") == '01'){
			alert("已加锁不可删除")
			return;
		}
		if($("#"+logBookID+"  input[name=scoreSort]").attr("value") != 'scode201007306kdf8m76me0000000002'){
			alert("不可删除")
			return;
		}
		$f = $('#logbookForm');
		if (confirm("是否删除？")) {
			if(notoken)return;
			notoken = 1;
			$f.attr("target","hidden").attr("action",pbasePath+"ea/phonelogbook/ea_delLogBook.jspa?pageNumber="+ppageNumber+"&logbook.staffID="+logbookstaffID+"&sdate="+psdate+"&edate="+pedate+"&logbook.logBookID="+logBookID);
			//$f.attr("target","hidden").attr("action",pbasePath+"ea/logbook/ea_delLogBook.jspa?pageNumber="+ppageNumber+"&logbook.staffID="+logbookstaffID+"&sdate="+psdate+"&edate="+pedate+"&logbook.logBookID="+logBookID);
			document.logbookForm.submit.click();
			$("tr#"+logBookID).remove();
			logBookID ='';
			token = 11 ;
			return;
		}
	});
	$(".address tr[id]").click(function(){
		$("input.JQuerypersonvalue", $(this)).attr("checked", "checked");
		logBookID =this.id;
	})
});
	function re_load(){
		if(token)
			document.location.href=pbasePath+"/ea/mobilelogbook/ea_getListLogBook.jspa?pageNumber="+ppageNumber+"&pageForm.pageNumber="+$("#pageNumber").attr("value");
	}
	function lookfileNum(s){
		if(!s)
		{
			alert("不存在文件号！");
			return;
		}
		var imgurl =pbasePath+s;
		newwin=window.open('about:blank','','width=800,height=600,left=0,top=0'); 
		newwin.document.write('<body  style="margin: 0px"><div align="center"><img id="img1" src="'+imgurl+'"></div>');newwin.resizeTo(screen.availWidth,screen.availHeight);
	}