$(document).ready(function(e) {
			//修改字体大小
			$("#tops").find("li").attr("style","float:left;");
			$("#tops").find("li").eq(0).attr("style","width:15%;");
			$("#tops").find("li").eq(0).find("img").attr("style","height:"+$(window).height()*0.03+"px;padding-left:"+$(window).height()*0.02+"px; vertical-align:middle;");
			$("#tops").find("li").eq(1).attr("style","width:70%; text-align:center; font-size:"+$(window).height()*0.025+"px;color:#FFF;");
			$("#tops").find("li").eq(2).attr("style","width:15%; text-align:center;");
			$("#tops").find("li").eq(2).find("img").attr("style","height:"+$(window).height()*0.03+"px; width:auto; vertical-align:middle;");
			$(".wfj12_008_top").css("height",$(window).height()*0.08+"px");
			$(".wfj12_008_top").css("lineHeight",$(window).height()*0.08+"px");
			
			$(".wfj12_008_filecard").attr("style","height:"+$(window).height()*0.05+"px;line-height:"+$(window).height()*0.05+"px;font-size:"+$(window).height()*0.02+"px;padding-left:"+$(window).height()*0.01+"px;");
			$(".wfj12_008_filecard").find("li").attr("style","font-size:"+$(window).height()*0.02+"px;");
			$(".wfj12_008_filecard").find("video").attr("style","font-size:"+$(window).height()*0.02+"px;");
			
			
			$(".wfj12_008_filecard_sd").attr("style","height:"+$(window).height()*0.05+"px;line-height:"+$(window).height()*0.05+"px;font-size:"+$(window).height()*0.02+"px;padding-left:"+$(window).height()*0.01+"px;");
			$(".wfj12_008_filecard_sd").find("li").attr("style","font-size:"+$(window).height()*0.02+"px;");
			$(".wfj12_008_filecard_sd").find("img").attr("style","float:right;height:"+$(window).height()*0.03+"px;width:auto;padding-right:"+$(window).height()*0.01+"px;margin:"+$(window).height()*0.01+"px auto;");
			
			$(".designat").css("width",$(window).width()*0.38+"px");
			
			$(".wfj12_008_music").attr("style","height:"+$(window).height()*0.05+"px;line-height:"+$(window).height()*0.05+"px;font-size:"+$(window).height()*0.02+"px;padding-left:"+$(window).height()*0.01+"px;");
			$(".wfj12_008_music").find("img").attr("style","float:right;height:"+$(window).height()*0.03+"px;width:auto;padding-right:"+$(window).height()*0.01+"px;margin:"+$(window).height()*0.01+"px auto;");
			
			$(".wfj12_008_mp").find("li").eq(0).attr("style","float:left;width:60%;overflow:hidden;font-size:"+$(window).height()*0.02+"px;height:"+$(window).height()*0.05+"px;line-height:"+$(window).height()*0.05+"px;padding-left:"+$(window).height()*0.01+"px;");
			$(".wfj12_008_mp").find("li").eq(1).attr("style","float:left;height:"+$(window).height()*0.05+"px;line-height:"+$(window).height()*0.05+"px;");
			$(".wfj12_008_mp").find("li").eq(1).find("img").attr("style","float:left;height:"+$(window).height()*0.03+"px;width:auto;margin:"+$(window).height()*0.01+"px auto;");
			

			$(".wfj12_008_mp").find("img").click(function(){
				if($(this).attr("src")==(basePath+"page/WFJClient/PersonalJoining/mp3zanting.png")){
					$(this).attr("src",basePath+"page/WFJClient/PersonalJoining/mp3bofang.png");
				}else{
					$(this).attr("src",basePath+"page/WFJClient/PersonalJoining/mp3zanting.png");
				}
			});
			$(".filecard").find("video").attr("style","width:100%;height:auto;");
			
			//基本信息
			
			$("#ch_sex").find("div").attr("style","height:"+$(window).height()*0.05+"px;line-height:"+$(window).height()*0.05+"px; border-radius:"+$(window).height()*0.05+"px;");
			//$("#ch_sex").find("div").find("li").eq(0).attr("style","background-color:#1F7EC8;color:#FFF;border-radius:"+$(window).height()*0.05+"px;font-size:"+$(window).height()*0.02+"px;");
			$("#ch_sex").find("div").find("li").eq(1).attr("style","font-size:"+$(window).height()*0.02+"px;");
			
			$("#ch_sex").find("div").find("li").click(function(){
				if($(this).text()=="男"){
					$("#ch_sex").find("div").find("li").attr("style","background-color:#F2F2F2;color:#666;border-radius:"+$(window).height()*0.05+"px;font-size:"+$(window).height()*0.02+"px;");
					$(this).attr("style","background-color:#1F7EC8;color:#FFF;border-radius:"+$(window).height()*0.05+"px;font-size:"+$(window).height()*0.02+"px;");
					sex=$(this).text();
				}else{
					$("#ch_sex").find("div").find("li").attr("style","background-color:#F2F2F2;color:#666;border-radius:"+$(window).height()*0.05+"px;font-size:"+$(window).height()*0.02+"px;");
					$(this).attr("style","background-color:#F74C31;color:#FFF;border-radius:"+$(window).height()*0.05+"px;font-size:"+$(window).height()*0.02+"px;");
					sex=$(this).text();
				}
			});

			//显示性别
			//变量未定义
			/*if(sex1=='男'){
				$("#ch_sex").find("div").find("li.left").attr("style","background-color:#1F7EC8;color:#FFF;border-radius:"+$(window).height()*0.05+"px;font-size:"+$(window).height()*0.02+"px;");
				sex='男';
			}else{
				$("#ch_sex").find("div").find("li.right").find(".right").attr("style","background-color:#F74C31;color:#FFF;border-radius:"+$(window).height()*0.05+"px;font-size:"+$(window).height()*0.02+"px;");
				sex='女';
			}*/

			//二维码
			$("#QRCodeDiv").css("width",window.innerWidth*0.6+"px");
			$("#QRCodeDiv").css("height",window.innerWidth*0.6+"px");
			

			
			$(".wfj12_008_bottom").attr("style","height:"+$(window).height()*0.05+"px;line-height:"+$(window).height()*0.05+"px;");
			$(".wfj12_008_bottom").find("div").attr("style","font-size:"+$(window).height()*0.03+"px; border-radius:"+$(window).height()*0.01+"px;height:"+$(window).height()*0.05+"px;line-height:"+$(window).height()*0.05+"px;");

			//商城信息
			$(".wfj12_008_shop").find("a").attr("style","font-size:"+$(window).height()*0.02+"px;");
			$(".wfj12_008_info").find("td").attr("style","font-size:"+$(window).height()*0.02+"px;height:"+$(window).height()*0.08+"px;line-height:"+$(window).height()*0.08+"px;");
			$(".wfj12_008_info").find("td").find("input").attr("style","font-size:"+$(window).height()*0.02+"px;height:"+$(window).height()*0.05+"px;padding-left:"+$(window).height()*0.01+"px;");
			$(".wfj12_008_info").find("td").find("textarea").attr("style","font-size:"+$(window).height()*0.02+"px;height:"+$(window).height()*0.1+"px;line-height:"+$(window).height()*0.03333+"px;padding-left:"+$(window).height()*0.01+"px;color:#000; border:none; width:94%; background-color:#F2F2F2;resize: none;");
			//$(".wfj12_008_info").find("td").find("img").attr("style","height:"+$(window).height()*0.05+"px;width:auto;");
			$(".wfj12_008_info").find("#addimg").find("img").attr("style","width:90%;");
			$(".wfj12_008_info").find("#addimg").find("li").attr("style","height:"+$(window).height()*0.08+"px;padding-bottom:"+$(window).height()*0.01+"px;");
			$("#mores_info").find("div").attr("style","margin:"+$(window).height()*0.05+"px auto;height:"+$(window).height()*0.05+"px;line-height:"+$(window).height()*0.05+"px;font-size:"+$(window).height()*0.025+"px;");
			
			
			$(".filecard_sd").find(".mainimg").attr("style","width:80%;");
			$(".filecard_sd").find(".mainimg").find("img").attr("style","width:100%;margin-bottom:"+$(window).height()*0.01+"px;");
			$(".filecard_sd").find(".right_top_img").attr("style","width:20%;margin-bottom:"+$(window).height()*0.01+"px;");
			$(".filecard_sd").find(".right_top_img").find("img").attr("style","width:100%;display:none;top:-"+$(window).height()*0.005+"px; right:"+$(window).height()*0.01+"px;");
			
			$(".filecard_sd").find(".mainimg").click(function(){
				$(".filecard_sd").find(".right_top_img").find("img").attr("style","width:100%;display:none;");
				$(this).parent().find(".right_top_img").find("img").attr("style","width:100%;display:block;top:-"+$(window).height()*0.005+"px; right:"+$(window).height()*0.015+"px;");
			});
			
			
			
			
			
			$(".filecard").find("td").attr("style","height:0px;");
			$(".wfj12_008_filecard").click(function(){
				if($(".filecard").is(":hidden")){
					$(".filecard").slideDown(1000);
					$(this).find("img").attr("src","page/WFJClient/PersonalJoining/wfj11_014_nav_04.png");
					$(".filecard2").slideDown(1000);
				}else{
					$(".filecard").slideUp(1000);
					$(this).find("img").attr("src","page/WFJClient/PersonalJoining/wfj11_014_nav_03.png");
					$(".filecard2").slideUp(1000);
				}
			});
			$(".filecard_sd").find("td").attr("style","height:0px;");
			$(".wfj12_008_filecard_sd").click(function(){
				if($(".filecard_sd").is(":hidden")){
					$(".filecard_sd").slideDown(1000);
					$(this).find("img").attr("src","page/WFJClient/PersonalJoining/wfj11_014_nav_04.png");
					$(".filecard_sd2").slideDown(1000);
				}else{
					$(".filecard_sd").slideUp(1000);
					$(this).find("img").attr("src","page/WFJClient/PersonalJoining/wfj11_014_nav_03.png");
					$(".filecard_sd2").slideUp(1000);
				}
			});
			//商城信息--隐藏滚动条
			$(".wfj12_008_shop_content").attr("style","width:"+$(window).width()+"px;height:"+parseInt($(window).height()-$(".wfj12_008_top").height()-$(".wfj12_008_title").height()-$(".wfj12_008_bottom").height())+"px; overflow:hidden;");
			$(".wfj12_008_shop_hidden").attr("style","height:"+$(".wfj12_008_shop_content").height()+"px;width:"+parseInt($(".wfj12_008_shop_content").width()+17)+"px;overflow:auto;");
			//保存
			$(".wfj12_008_bottom").find("#save").click(function(){
				 		//验证信息
					 	var z1=/^[0][0-9]{2,3}-[0-9]{5,8}$/;		//判断公司座机电话
						var z2=/^1\d{10}$/;   //判断手机
						var z3="[a-zA-z]+://[^\s]*";
						var logo=$(".verification").eq(0).find("input").val();//公司logo
						var src=$("#logo").attr("src"); 
						var onlyName=$.trim($(".verification").eq(1).val());
						if(logo==null&&src.indexOf(".")==-1){
							prompt("公司logo不可为空");
							$(".verification").eq(0).focus();}
						else if($(".verification").eq(1).val()==""){
							prompt("公司名称不可为空");
							$(".verification").eq(1).focus();}
						else if($(".verification").eq(1).val()=="请输入公司名称")
							prompt("请输入公司名称");
						else if($(".verification").eq(3).val()==""){
							prompt("公司地址不可为空");
							$(".verification").eq(3).focus();}
						else if($(".verification").eq(3).val()=="请输入公司地址")
							prompt("请输入公司地址");
						else if($(".verification").eq(4).val()==""){
							prompt("公司负责人姓名不可为空");
							$(".verification").eq(4).focus();}
						else if($(".verification").eq(4).val()=="请输入公司负责人")
							prompt("请输入公司负责人");
						else if($(".verification").eq(5).val()==""){
							prompt("公司负责人电话不可为空");
							$(".verification").eq(5).focus();}
						else if(!new RegExp(z2).test($(".verification").eq(5).val())){
							prompt("电话格式不对");
							$(".verification").eq(5).focus();}
						else if($(".verification").eq(6).val()==""){
							prompt("公司网址不可为空");
							$(".verification").eq(6).focus();}
						else if(!new RegExp(z3).test($(".verification").eq(6).val())){
							prompt("公司网址格式不对");
							$(".verification").eq(6).focus();}
						else if($(".verification").eq(7).val()==""){
							prompt("公司电话不可为空");
							$(".verification").eq(7).focus();}
						else if(!new RegExp(z1).test($(".verification").eq(7).val())){
							prompt("公司电话格式不对");
							$(".verification").eq(7).focus();}
						else if($(".verification").eq(8).val()==""){
							prompt("公司简介不可为空");
							$(".verification").eq(8).focus();}
						else if($(".verification").eq(8).val()=="请输入公司简介")
							prompt("请输入公司简介");
						else if($(".verification").eq(1).val()!=""&&$(".verification").eq(1).val()!=$.trim(companyName)){
								if(prompt1(onlyName)!='0'){
									prompt("公司名称已存在！");
									return;
								}
						}						
						else{
							$("#companyInfo").attr("action",basePath+"ea/industry/ea_saveCompanyCard.jspa?contactCompany.sex="+sex+"&user="+user);
							$("#submit").click();
						}
			});
			//行业分类
			var url=basePath+"/ea/industry/sajax_ea_getIndustry.jspa";
			$.ajax({
				url: encodeURI(url),
				type: "get",
				async: true,
				dataType: "json",
				success:function cbf(data){
					var member=eval("("+data+")");
					var list=member.industryList;
					if(null==list){
						return ;
					}else{
					var htl=new Array();
					for(var i=0;i<list.length;i++){
					htl.push("<li class='"+list[i].codeID+"'><span>"+list[i].codeValue+"</span></li>");
						}
						$("#industry1").html(htl.join(""));
					}
					$(".tanchu").find("div").find("ul").css("height",parseInt($(".tanchu").height()-20)+"px")
				},
				error:function cbf(data){
					alert("数据加载失败！");
				}
			});
	$("#industry1").find("li").live("click",function(){
		$(".industry2").attr("style","display:block;");
		$(".industry1").attr("style","display:none;");
		$("#industry2").empty();
		var change= $(this).attr("class");
		$("#indus").val($("#indus").val()+$(this).find("span").html()+"/");
		var url=basePath+"/ea/industry/sajax_ea_getIndustry.jspa?codePID="+change;
		$.ajax({
			url: encodeURI(url),
			type: "get",
			async: true,
			dataType: "json",
			success:function cbf(data){
				var member=eval("("+data+")");
				var list=member.industryList;
				if(null==list){
					return ;
				}else{
				var htl=new Array();
				for(var i=0;i<list.length;i++){
				htl.push("<li class='"+list[i].codeID+"'><span>"+list[i].codeValue+"</span></li>");
					}
					$("#industry2").html(htl.join(""));
				}
				
			},
			error:function cbf(data){
				alert("数据加载失败！");
			}
		});
	});
		//点击触发弹出行业分类层
	 $("#indus").focus(function(){ 
		 	$(this).val("");
			$("#occlusion2").css("z-index",$(".wfj12_008").css("z-index")+1);
			$("#occlusion2").jqmShow();
			$(".tanchu").css("z-index",$("#occlusion2").css("z-index")+1);		
			$(".tanchu").fadeIn(1000);
			$(".industry2").attr("style","display:none;");
			$("#industry2").empty();
			$(".industry1").attr("style","display:block;");
	 });
	 //选择完毕退出弹出层
	 $("#industry2").find("li").live("click",function(){
		 $("#indus").val($("#indus").val()+$(this).find("span").html());
		  $(".tanchu").fadeOut();
		  $("#occlusion2").jqmHide();
		 });  
});//加载完毕

$("#erweima").live("click",function(){
	$("#QRCode").css("display","block");
});
$("#QRCode").live("click",function(){
	$("#QRCode").css("display","none");
});
function prompt(obj){
	if($("#prompt").css("display")!="none")
		return;
	$("#prompt").find("span").text(obj);
	$("#prompt").fadeIn(500);
	setTimeout(function(){
		$("#prompt").fadeOut(500);
		$("#prompt").find("span").text("");
	}, 2000);
}
//添加或修改公司名称判断
function prompt1(onlyCompany){
	var result;
	var url = basePath
			+ "ea/industry/sajax_ea_isCompanyName.jspa?onlyCompany="
			+ onlyCompany;
	$.ajax({
				url : encodeURI(url),
				type : "get",
				async : false,
				dataType : "json",
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					result = member.c;
				},error:function(data){
					alert("读取失败");					
				}
	});
	return result;
}
$(function(){
    $(".addmore").click(function(){
        $(".add_1").css("display","block")
		$("body,.main").css("overflow","hidden")       
    })
    $(".add_1 .mu").click(function(){
        $(".add_1").css("display","none")
		$("body,.main").css("overflow","auto")
    })		
	$(".add_1").find("li").click(function(){
		$(".add_1").css("display","none")
		$("body,.main").css("overflow","auto")
		$(".wfj01_006_con_list ul:last-child").append($(this))
		})
		
	
})		 
	


