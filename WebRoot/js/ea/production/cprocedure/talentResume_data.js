$(function(){
	$(".outer_title").attr("style","width:"+(window.innerWidth*0.6)+"px;position:relative;left:"+(window.innerWidth*0.2)+"px;overflow:hidden;");
	$(".outer_body").attr("style","width:"+(window.innerWidth*0.6+17)+"px;left:"+(window.innerWidth*0.2)+"px;overflow-y:scroll;");
	$(".interview_body").attr("style","width:"+(window.innerWidth*0.35)+"px;height:"+(window.innerHeight*0.75)+
			"px;position:fixed;top:"+(window.innerHeight*0.1)+"px;left:"+(window.innerWidth*0.32)+
			"px;border:1px solid;background-color:#fff;z-index:3001;");
	$(".interview_title").attr("style","width: "+$(window).width()+"px;height: "+$(".outer_title").height()+"px;position: absolute;top:0px;left:0px;background-color: rgba(0,0,0,0.5);z-index:3000;display:none;");
	
	$(".jianli_footer").attr("style","position:fixed;top:"+(window.innerHeight*0.935)+"px;left:"+
			(window.innerWidth*0.2)+"px;width:"+(window.innerWidth*0.6)+"px;border-top:0px;");
	$(document).on("blur",".ws",function(){
		if($(".ws option:selected").text()=="上午"){
			var str="<option value='8:00'>8:00</option>" +
					"<option value='8:30'>8:30</option>" +
					"<option value='9:00'>9:00</option>" +
					"<option value='9:30'>9:30</option>" +
					"<option value='10:00'>10:00</option>"+
					"<option value='10:30'>10:30</option>"+
					"<option value='11:00'>11:00</option>"+
					"<option value='11:30'>11:30</option>"+
					"<option value='12:00'>12:00</option>"+
					"<option value='12:30'>12:30</option>";
			$(".sj").html(str);
		}else{
			var str="<option value='1:00'>1:00</option>" +
					"<option value='1:30'>1:30</option>" +
					"<option value='2:00'>2:00</option>" +
					"<option value='2:30'>2:30</option>" +
					"<option value='3:00'>3:00</option>" +
					"<option value='3:30'>3:30</option>" +
					"<option value='4:00'>4:00</option>" +
					"<option value='4:30'>4:30</option>" +
					"<option value='5:00'>5:00</option>" +			
					"<option value='5:30'>5:30</option>";
			$(".sj").html(str);
		}
	});
	
	$(".buttom").click(function(){
		if($(this).text()=="发送"){
			if($(".interviewTime").val()==""){
				alert("面试时间不可为空！");return;
			}
			if($(".interviewPlace").val()==""){
				alert("面试地点不可为空！");return;
			}
			if($(".contactor").val()==""){
				alert("请选择联系人！");return;
			}
			if($(".contactTel").val()==""){
				alert("联系人电话不可为空！");return;
			}


			var str="ws="+$(".ws option:selected").text()+"&sj="+$(".sj option:selected").text()+"&tpID="+tpID+
					"&rq="+$(".interviewTime").val()+"&isSMS="+($(".ccbox").attr("checked")?"00":"01");
			alert(str);
			return;
			$("#form").attr("target", "hidden").attr("action",basePath+"ea/tresume/ea_sendMessages.jspa?"+str);
			document.form.submit.click();
			token = 2;
		}else{
			$(".inputr").val("");$(".chbox").attr("checked",false);
			$(".interview_title").hide();
		};
	});
	$(".tou").click(function(){
		$(".interview_title").show();
	});
});

function re_load(){
	window.parent.location.reload();
	window.close();
}