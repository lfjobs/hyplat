$(function(){
	if(backID!=null&&backID!=""){
		$(".verification").attr("readonly","readonly");
		$("select").attr("disabled","disabled");
		$(".wfj01_008_save").text("操    作");
		$("#preservation").attr("id","operation");
	}
	
/*	//验证对公对私
	 $("#cardStatus").blur(function(){
		 	var userName=$(".verification").eq(0).val();//获得用户名字
		 	var cardStatus=$("#cardStatus").val();//对公对私		 	
		 	if(cardStatus!="" && userName!=1){		 		
		 		if(userName.length>1){
		 			if(cardStatus!="公开"){		 				
		 					if(confirm("你确定是个人账户吗？")){					
								$("#preservation").click();
							}else{
								$("#cardStatus").val("");
								return
							}		 						 				
		 			}else{
		 				if(confirm("你确定是企业账户吗？")){					
								$("#preservation").click();
							}else{
								$("#cardStatus").val("");
								return
							}	 	 				
		 			}	 			
		 		}else{
		 			prompt("名字输入错误！");
		 			$(".verification").eq(0).val("");
		 		}	
		 	}		 			 	
	 });*/
	 	
	$("#preservation").click(function(){
		var z1=/^(\d{16}|\d{19})$/;		//判断银行卡
		var z2="([\u4e00-\u9fa5])";   //判断中文
		if($(".verification").eq(0).val()==""){
			prompt("姓名不可为空");
		}else if(!new RegExp(z2).test($(".verification").eq(0).val())||/\s+/.test($(".verification").eq(0).val())){
			prompt("姓名格式错误");
		}else if($(".verification").eq(1).val()==""){
			prompt("省份不可为空");
		}else if(!new RegExp(z2).test($(".verification").eq(1).val())||/\s+/.test($(".verification").eq(1).val())){
			prompt("所属省份格式错误");
		}else if($(".verification").eq(2).val()==""){
			prompt("所属城市不可为空");
		}else if(!new RegExp(z2).test($(".verification").eq(2).val())||/\s+/.test($(".verification").eq(2).val())){
			prompt("所属城市格式错误");
		}else if($("#bankName option:selected").val()==""){
			prompt("请选择银行名字");
		}else if($(".verification").eq(4).val()==""){
			prompt("银行卡号不可为空");
		}else if(!z1.test($(".verification").eq(4).val())||/\s+/.test($(".verification").eq(4).val())){
			prompt("银行卡号格式错误");
		}else if($("#accountNature option:selected").val()==""){
			prompt("请选择银行卡类型");
		}else if($("#cardStatus option:selected").val()==""){
			prompt("请选择银行卡用途");
		}else {	
			
		
									
	/*		登录银行和卡号相对应的判断		
			var httpArg =$(".verification").eq(4).val();//获取输入的卡号
			var bkName=$("#bankName").val();//获取银行名字
			var cardType=$("#accountNature").val();//获得银行卡类型
			var cardStatus=$("#cardStatus").val();//对公对私
			var uut = basePath + "/ea/perinfor/sajax_ea_checkBankNum.jspa";
				
			
			if(httpArg!=null&&httpArg!=''){
				$.ajax({
					url : encodeURI(uut),
					type : "get",
					async : false,
					dataType : "json",
					data:{"httpArg":httpArg},
					success : function(data) {
						var member = eval("(" + data + ")");//获得接口提供的信息
						var code = member.code;//0表示查询成功，其他表示查询失败
						var bname=member.data.bankname;//获得银行接口提供的银行名字		
						var ctype = member.data.cardtype;//获得银行接口提供的银行卡类型					
						 if(code!='0'){	//判断卡号是否正确
							 prompt("添加失败！");
							$("#cardStatus").val("");
							$(".verification").eq(4).val("");							
						}else if(bname.indexOf("中国")==-1 && bname!=bkName){
							
							var bname="中国"+member.data.bankname;
							if(bname==bkName){
								if(ctype=="贷记卡"){
									var ctype="信用卡";
									}
								if(ctype!=cardType){
									prompt("银行卡类型错误！");
									$("#cardStatus").val("");
									$(".verification").eq(4).val("");
								
								}else{
								$("#form").attr("target", "hidden").attr("action",basePath+"ea/perinfor/ea_addBankCardInformation.jspa?editType="+editType);
								document.form.submit.click();
								token = 2;	
								}
							}else{
							prompt("银行卡与所属银行不相符！");
							$("#cardStatus").val("");
							$(".verification").eq(4).val("");
							}
						}else if(bname==bkName){
							if(ctype=="贷记卡"){
							    var ctype="信用卡";
								}
							if(ctype!=cardType){
								prompt("银行卡类型错误！");
								$("#cardStatus").val("");
								$(".verification").eq(4).val("");
							}else{*/
								$("#form").attr("target", "hidden").attr("action",basePath+"ea/perinfor/ea_addBankCardInformation.jspa?editType="+editType+"&flag="+flag);
								document.form.submit.click();
								token = 2;	
							/*	}							
						}  					 					
					}, 					
				});			
			}*/
		}
	
	});
	
	$(".wfj_return").find("a").click(function(){
		window.location.href = basePath+ "ea/perinfor/ea_getBankCardInformation.jspa?staffId="+ $("#staffId").val()+"&editType="+editType+"&backurl="+backurl;
	});
	$("#operation").live("click",function(){
		$(".wfj01_001_popup").css("display","block");
	});
	$(".wfj01_001_popup_body").click(function(){
		if(confirm("是否设置该银行卡为默认银行卡")){
			$("#form").attr("target", "hidden").attr("action",basePath+"ea/perinfor/ea_setTheDefaultBankCard.jspa?staffId="+$("#staffId").val()+"&bankAccountID="+$("#bankId").val()+"&editType="+editType);
			document.form.submit.click();
			token = 2;
		}
	});
	$(".wfj01_001_delete_body").click(function(){
		if(confirm("是否删除该银行卡")){
			$("#form").attr("target", "hidden").attr("action",basePath+"ea/perinfor/ea_deleteBankCard.jspa?staffId="+$("#staffId").val()+"&bankAccountID="+$("#bankId").val()+"&editType="+editType);
			document.form.submit.click();
			token = 2;
		}
	});
	$(".wfj01_001_popup").click(function(){
		$(".wfj01_001_popup").hide();
	});
	
	$("#cardStatus").blur();
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
$(document).ready(function(e) {
         	$("body").css("height",$(window).height()) ;
			//修改字体大小
			$("#tops").find("li").attr("style","float:left;");
			$("#tops").find("li").eq(0).attr("style","width:15%;");
			$("#tops").find("li").eq(0).find("img").attr("style","height:"+$(window).height()*0.03+"px;padding-left:"+$(window).height()*0.02+"px; vertical-align:middle;");
			$("#tops").find("li").eq(1).attr("style","width:70%; text-align:center; font-size:"+$(window).height()*0.025+"px;");
			$("#tops").find("li").eq(2).attr("style","width:15%; text-align:center;");
			$("#tops").find("li").eq(2).find("img").attr("style","height:"+$(window).height()*0.03+"px; width:auto; vertical-align:middle;");
				
			$(".wfj01_008_top").css("height",$(window).height()*0.058+"px");
			$(".wfj01_008_top").css("lineHeight",$(window).height()*0.058+"px");
			
			$(".wfj01_008_title").attr("style"," height:"+$(window).height()*0.08+"px; line-height:"+$(window).height()*0.08+"px;");
			$(".wfj01_008_title").find("div").attr("style"," font-size:"+$(window).height()*0.02+"px;");
			$(".wfj01_008_content").find("tr").attr("style"," height:"+$(window).height()*0.08+"px; line-height:"+$(window).height()*0.08+"px; border-bottom:"+$(window).height()*0.003+"px solid #FFF;");
			$(".wfj01_008_content").find("img").attr("style"," height:"+$(window).height()*0.04+"px; width:auto;");
			$(".wfj01_008_content").find("input").attr("style"," height:"+$(window).height()*0.06+"px; font-size:"+$(window).height()*0.025+"px;");
			$(".wfj01_008_content").find("select").attr("style","width:100%;height:"+$(window).height()*0.06+"px; font-size:"+$(window).height()*0.025+"px;border:0px;background-color:#F2F2F2;");
			$(".wfj01_008_bottom").attr("style","height:"+$(window).height()*0.06+"px; line-height:"+$(window).height()*0.06+"px; margin-top:"+$(window).height()*0.03+"px;");
			$(".wfj01_008_save").attr("style","font-size:"+$(window).height()*0.025+"px;");
			
			$("#prompt").css("position","absolute").css("top",$(window).height()*0.10+"px");
			$("#prompt").find("div").css("height",$(window).height()*0.06+"px").css("font-size",$(window).height()*0.0285+"px").css("color","#FFFFFF");
			$("#prompt").find("div").css("-moz-border-radius",$(window).height()*0.015+"px").css("-webkit-border-radius",$(window).height()*0.015+"px");
			$(".typeface").find("div").css("fontSize",window.innerHeight*0.0265+"px");
        });

function re_load(){
	window.location.href = basePath+ "ea/perinfor/ea_getBankCardInformation.jspa?staffId="+ $("#staffId").val()+"&editType="+editType+"&backurl="+backurl+"&flag="+flag;
}










