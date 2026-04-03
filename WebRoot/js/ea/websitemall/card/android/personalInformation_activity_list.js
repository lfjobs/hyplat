$(function(){
	$(".wfj01_001_bottom_width").click(function(){
		window.location.href=basePath+"ea/perinfor/ea_getPersonalActivityData.jspa?staffId="+$("#staffId").val()+"&status=01&type="+type+"&editType="+editType+"&backurl="+backurl; 
	});
	$(".wfj01_003_delete").click(function(){
		if($("#deleteActivities").val()=="")
			$("#deleteActivities").val($(this).parents("tr").find(".activitiesID").val());
		else
			$("#deleteActivities").val($("#deleteActivities").val()+" - "+$(this).parents("tr").find(".activitiesID").val());
		$(this).parents("table").remove();
	});
	$(".wfj01_003_list").scroll(function(){
		if(($(this).get(0).scrollHeight-window.innerHeight*0.84)*0.5<$(this).get(0).scrollTop&&pageNumber<pageCount){
			pageNumber++;
			$.ajax({
				url:basePath+"ea/perinfor/sajax_ea_getPersonalActivityList.jspa",
				data:{"staffId":$("#staffId").val(),
							"type":type,
							"ssj":"ajax",
							"pageForm.pageNumber":pageNumber},
				type:"post",
				success:function(data){
					var member = eval("(" + data + ")");
					var list=member.pageForm.list;
					for(var i=0;i<list.length;i++){
						var table="<table class='wfj01_003_click01'>";
						table+="<tr><td rowspan='2' class='wfj01_003_uploadimg'><img src='"+(basePath+list[i].picture)+"'  class='path'/></td>";
						table+="<td><span>"+list[i].title+"</span><input type='hidden' class='activitiesID' value="+list[i].activitiesID+"></td>";
						table+="<td rowspan='2' class='wfj01_003_delete'><img src='"+basePath+"images/ea/websitemall/card/wfj_delete_04.png' /></td>";
						table+="</tr><tr><td>作者："+list[i].author+"</td></tr></table>";
						$(".wfj01_003_list").append(table);
					}
					$(".wfj01_003_list").find("table").attr("style"," padding:"+$(window).height()*0.015+"px 0; border-bottom:"+$(window).height()*0.003+"px solid #F0F0F0;");
					$(".wfj01_003_list").find("td").attr("style","font-size:"+$(window).height()*0.02+"px;");
					$(".wfj01_003_list").find("span").attr("style","font-size:"+$(window).height()*0.025+"px;");
				},error:function(data){
					alert("数据获取失败");
				}
			});
		}
		
		
		
	});
});
$(document).ready(function(e) {
         	$("body").css("height",$(window).height()) ;
			//修改字体大小
			$(".wfj01_003_top").attr("style","height:"+$(window).height()*0.058+"px;line-height:"+$(window).height()*0.058+"px;");
		 
			$("#tops").find("li").attr("style","float:left;");
			$("#tops").find("li").eq(0).attr("style","width:15%; text-align:center;");
			$("#tops").find("li").eq(0).find("img").attr("style","height:"+$(window).height()*0.03+"px;padding-left:"+$(window).height()*0.02+"px; vertical-align:middle;");
			$("#tops").find("li").eq(1).attr("style","width:70%; text-align:center; font-size:"+$(window).height()*0.025+"px;");
			$("#tops").find("li").eq(2).attr("style","width:15%; text-align:center;font-size:"+$(window).height()*0.02+"px;");
			
			$(".wfj01_003_con").attr("style","height:"+(window.innerHeight*0.94)+"px;overflow:hidden;");
			$(".wfj01_003_list").attr("style","height:"+(window.innerHeight*0.84)+"px;overflow-y:scroll;");
			$(".wfj01_003_list").find("table").attr("style"," padding:"+$(window).height()*0.015+"px 0; border-bottom:"+$(window).height()*0.003+"px solid #F0F0F0;");
			$(".wfj01_003_list").find("td").attr("style","font-size:"+$(window).height()*0.02+"px;");
			$(".wfj01_003_list").find("span").attr("style","font-size:"+$(window).height()*0.025+"px;");
			
			$(".path").attr("style","height:"+$(window).height()*0.08125+"px");
			$(".wfj01_001_bottom").attr("style"," margin-top:"+$(window).height()*0.03+"px;");
			$(".wfj01_001_bottom_width").attr("style","height:"+$(window).height()*0.05+"px; line-height:"+$(window).height()*0.05+"px; font-size:"+$(window).height()*0.025+"px; border-radius:"+$(window).height()*0.01+"px;");
			
			$(".wfj01_003_edit").click(function(){
				if($(this).find("a").text()=="编辑"){
					$(".wfj01_003_delete").fadeIn(1000);
					$(this).find("a").text("完成");
				}else{
					if($("#deleteActivities").val()!=""){
						$("#form").attr("target", "hidden").attr("action",basePath+"ea/perinfor/ea_deletePersonalActivityList.jspa?del="+$("#deleteActivities").val()+"&editType="+editType);
						document.form.submit.click();
						token = 2;
					}
					$(".wfj01_003_delete").fadeOut(500);
					$(this).find("a").text("编辑");
				}
			});
			
			if($(window).width()>$(window).height()){
				$(".wfj01_003").attr("style","width:70%;height:"+$(window).height()+"px;");
				$(".wfj01_003_content").attr("style","width:"+$(".wfj01_003").width()+"px;height:"+parseInt($(window).height()-$(".wfj01_003_top").height())+"px;overflow:hidden;");
				if($(".wfj01_003_list").height()>$(".wfj01_003_content").height()){
					$(".wfj01_003_hidden").attr("style","width:"+parseInt($(".wfj01_003_content").width()+17)+"px;height:"+$(".wfj01_003_content").height()+"px;overflow:auto;");
				}else{
					$(".wfj01_003_hidden").attr("style","width:"+parseInt($(".wfj01_003_content").width())+"px;height:"+$(".wfj01_003_content").height()+"px;overflow:auto;");
				}
			}else{
				$(".wfj01_003").attr("style","width:100%;height:"+$(window).height()+"px;");
				$(".wfj01_003_content").attr("style","width:"+$(".wfj01_003").width()+"px;height:"+parseInt($(window).height()-$(".wfj01_003_top").height())+"px;overflow:hidden;");
				if($(".wfj01_003_list").height()>$(".wfj01_003_content").height()){
					$(".wfj01_003_hidden").attr("style","width:"+parseInt($(".wfj01_003_content").width()+17)+"px;height:"+$(".wfj01_003_content").height()+"px;overflow:auto;");
				}else{
					$(".wfj01_003_hidden").attr("style","width:"+parseInt($(".wfj01_003_content").width())+"px;height:"+$(".wfj01_003_content").height()+"px;overflow:auto;");
				}
				
			}
			
			$(".wfj_return").click(function(){
				window.location.href=basePath+"ea/perinfor/ea_getPageHomeData.jspa?staffId="+$("#staffId").val()+"&editType="+editType+"&backurl="+backurl;
			});
			
			$(".wfj01_003_click01").click(function(){
				if($(this).find(".wfj01_003_delete").css("display")=="none")
					window.location.href=basePath+"ea/perinfor/ea_getPersonalActivityData.jspa?staffId="+$("#staffId").val()+"&status=00&activitiesID="+$(this).find(".activitiesID").val()+"&type="+type+"&editType="+editType+"&backurl="+backurl; 
			});
			if(editType!="00")
				$(".display").css("display","none");
		});

function re_load(){
	window.location.href=basePath+"ea/perinfor/ea_getPersonalActivityList.jspa?staffId="+$("#staffId").val()+"&status=01&type="+type+"&editType="+editType+"&backurl="+backurl;
}