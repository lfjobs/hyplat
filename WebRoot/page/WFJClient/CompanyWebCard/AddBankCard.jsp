<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>



<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>公司银行卡添加</title>
<meta http-equiv="Access-Control-Allow-Origin" content="*">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no" />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<head>
<link rel="stylesheet" type="text/css" href="<%=basePath %>css/WFJClient/companycard.css"/>
<script type="text/javascript" src="<%=basePath %>js/jquery-1.6.1.min.js"></script>
<script type="text/javascript" src="<%=basePath %>js/jqModal/jqModal.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath %>js/jqModal/css/jqModal_blue.css"/>
<style type="text/css">
#prompt div{
				width: 70%;
				background: rgba(0,0,0, 0.5);
			}
.select-style{
  position:absolute;
  overflow:hidden;
  
}
.select-style select{
 margin:-2px;
 background-color:#F2F2F2;
 width:87%;
}
</style>
</head>
<body class="bgcolorFFF" style="overflow: auto;">
	<div class="wfj01_008">
		<div class="wfj01_008_top">
        	<ul id="tops">
            	<li class="wfj_return"><a href="<%=basePath %>ea/industry/ea_getBankCardsList.jspa?sccId=${sccId}" target="_self"><img src="<%=basePath%>images/ea/websitemall/card/wfj_return_02.png" /></a></li>
            	<li>添加银行卡</li>
            	<li><a href="javascript:;" style="display: none;"><img src="<%=basePath%>images/ea/websitemall/card/dot.png" /></a></li>
            </ul>
        </div>
	
        <div class="wfj01_008_title">
        	<div>请绑定持卡人本人的银行卡</div>       	
        </div>
		
        <form id="bankcardForm" method="post">
        <input id="ccompanyId" type="hidden" value="${ccompanyId }" />
        <input type="submit" style="display:none;" id="submit"/>
        <input type="hidden" value="${sccId }" id="sccId" />
        <div class="wfj01_008_content">
            <table>
            	<tr>
                	<td width="15%" align="center"><img src="<%=basePath%>/images/WFJClient/PersonalJoining/bank_card_01.png" /></td>
                	<td width="85%"><input class="verification" type="text" placeholder="请输入姓名"  name="staffbank.cardholder" onkeyup="this.value=this.value.replace(/^ +| +$/g,'')"/></td>
                </tr>
                <tr>
                	<td width="15%" align="center"><img src="<%=basePath%>/images/WFJClient/PersonalJoining/bank_card_03.png" /></td>
                	<td width="85%"><input class="verification" type="text" placeholder="请输入所属省份"  name="staffbank.province" onkeyup="this.value=this.value.replace(/^ +| +$/g,'')"/></td>
                </tr>
                <tr>
                	<td width="15%" align="center"><img src="<%=basePath%>/images/WFJClient/PersonalJoining/wfj_all_Profile_04.png" /></td>
                	<td width="85%"><input class="verification" type="text" placeholder="请输入所属城市"  name="staffbank.bankAddress" onkeyup="this.value=this.value.replace(/^ +| +$/g,'')"/></td>
                </tr>                
                <tr>
                	<td align="center"><img src="<%=basePath%>/images/WFJClient/PersonalJoining/bank_card_02.png" /></td>
                	<td>
                		<span class="select-style">
                		<select name="staffbank.bankName"  id="bkName" style="width:100%;border:none;">
							<option value=''>请选择银行</option> 
							<option value='中国工商银行'>中国工商银行</option>
							<option value='中国建设银行'>中国建设银行</option> 
							<option value='中国银行'>中国银行</option>
							<option value='中国农业银行'>中国农业银行</option>
							<option value='中国交通银行'>中国交通银行</option> 
							<option value='招商银行'>招商银行</option>
							<option value='浦发银行'>浦发银行</option> 
							<option value='中信银行'>中信银行</option>
							<option value='中国光大银行'>中国光大银行</option>
							<option value='华夏银行'>华夏银行</option> 
							<option value='中国民生银行'>中国民生银行 </option>
							<option value='兴业银行'>兴业银行</option> 
							<option value='平安银行'>平安银行</option>						
						</select>	
						</span>
                	</td>              
                </tr>    
                
                
                
                 <tr>
                	<td align="center"><img src="<%=basePath%>/images/WFJClient/PersonalJoining/bottom_03_03.png" /></td>
                	<td><input class="verification" type="text" placeholder="请输入支银行名称(选填)" name="staffbank.branchName" onkeyup="this.value=this.value.replace(/^ +| +$/g,'')"/></td>
                </tr> 
             
            	<tr>
                	<td align="center"><img src="<%=basePath%>/images/WFJClient/PersonalJoining/bank_card_04.png" /></td>
                	<td><input class="verification" type="text" placeholder="请输入卡号"  name="staffbank.bankAccount" maxlength="19" onkeyup="this.value=this.value.replace(/^ +| +$/g,'')"/></td>
                </tr>
                <tr>
                	<td align="center"><img src="<%=basePath%>images/ea/websitemall/card/wfj09_25_03.png" /></td>
                	<td>               		
                		<span class="select-style">
                		<select name="staffbank.accountNature" id="accountNature"  style="width: 100%;border: none;">
                		<option value="">请选择银行卡类型</option>
                		<option value="借记卡">借记卡</option>
                		<option value="储蓄卡">储蓄卡</option>
                		<option value="信用卡">信用卡</option>
                		</select></span>
                	</td>
                </tr>
                <tr>
                	<td align="center"><img src="<%=basePath%>/images/WFJClient/PersonalJoining/wfj_all_Profile_09.png"/></td>
                	<td>
                	<span class="select-style" style="width: 85%;">
                	<select name="staffbank.cardType" id="cardStatus"  style="width: 100%;border: none;">
                	<option value="">请选择银行卡状态</option>
                	<option value="01">企业账户</option>
                	<option value="00">个人账户</option></select></span></td>
                </tr>
            </table></form>
        </div>
	<div id="prompt" style="width: 100%; display: none;">
		<center>
			<div>
				<span style="position: relative; top: 19.8%;"></span>
			</div>
		</center>
	</div>
	<div class="wfj01_008_bottom">
        	<div class="wfj01_008_save">保存信息</div>
    </div>
</div>
    <script type="text/javascript">
    var basePath='<%=basePath%>'; 
    var sccId='${sccId}';
    var ccompanyId='${ccompanyId}';
    	$(document).ready(function(e) {
    		
/*    		$("#cardStatus").blur(function(){
			 	var userName=$(".verification").eq(0).val()//获得用户名字
			 	var cardStatus=$("#cardStatus").val()//对公对私		 	
			 	if(cardStatus!="" && userName!=1){		 		
			 		if(userName.length>1){
			 			if(cardStatus!="01"){		 				
			 					if(confirm("你确定是个人账户吗？")){					
									$(".wfj01_008_save").click();
								}else{
									$("#cardStatus").val("");
									return
								}		 						 				
			 			}else{
			 				if(confirm("你确定是企业账户吗？")){					
									$(".wfj01_008_save").click();
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
    		
    		
    		
    	
         	$("body").css("height",$(window).height()) ;
			//修改字体大小
			$("#tops").find("li").attr("style","float:left;");
			$("#tops").find("li").eq(0).attr("style","width:15%;");
			$("#tops").find("li").eq(0).find("img").attr("style","height:"+$(window).height()*0.03+"px;padding-left:"+$(window).height()*0.02+"px; vertical-align:middle;");
			$("#tops").find("li").eq(1).attr("style","width:70%; text-align:center; font-size:"+$(window).height()*0.025+"px;");
			$("#tops").find("li").eq(2).attr("style","width:15%; text-align:center;");
			$("#tops").find("li").eq(2).find("img").attr("style","height:"+$(window).height()*0.03+"px; width:auto; vertical-align:middle;");
				
			$(".wfj01_008_top").css("height",$(window).height()*0.08+"px");
			$(".wfj01_008_top").css("lineHeight",$(window).height()*0.08+"px");
			
			$("#prompt").css("position","absolute").css("top",$(window).height()*0.09+"px");
			$("#prompt").find("div").css("height",$(window).height()*0.06+"px").css("font-size",$(window).height()*0.0285+"px").css("color","#FFFFFF");
			$("#prompt").find("div").css("-moz-border-radius",$(window).height()*0.015+"px").css("-webkit-border-radius",$(window).height()*0.015+"px");
			
			$(".select-style").css({"width":"85%","margin-top":-$(".select-style").height()*0.5+"px"})
			$(".select-style").css({"font-size":$(window).width()*0.05+"px"})
			$(".select-style select").css({"font-size":$(window).width()*0.04+"px","color":"#888"})
			
			
			$(".wfj01_008_title").attr("style"," height:"+$(window).height()*0.08+"px; line-height:"+$(window).height()*0.08+"px;");
			$(".wfj01_008_title").find("div").attr("style"," font-size:"+$(window).height()*0.02+"px;");
			$(".wfj01_008_content").find("tr").attr("style"," height:"+$(window).height()*0.08+"px; line-height:"+$(window).height()*0.08+"px; border-bottom:"+$(window).height()*0.003+"px solid #FFF;");
			$(".wfj01_008_content").find("img").attr("style"," height:"+$(window).height()*0.04+"px; width:auto;");
			$(".wfj01_008_content").find("input").attr("style"," height:"+$(window).height()*0.06+"px; font-size:"+$(window).height()*0.02+"px;");
			$(".wfj01_008_bottom").attr("style","height:"+$(window).height()*0.06+"px; line-height:"+$(window).height()*0.06+"px; margin-top:"+$(window).height()*0.03+"px;");
			$(".wfj01_008_save").attr("style","font-size:"+$(window).height()*0.025+"px;");
			
			$(".verification").eq(6).attr("style","width:40%");
			$(".verification").eq(7).attr("style","width:40%");
		
			 $(".more").find("li").attr("style"," height:"+$(window).height()*0.05+"px; line-height:"+$(window).height()*0.05+"px; border-bottom: 1px solid #F1F1F1;");
				$(".more").find("li").find("a").attr("style"," font-size:"+$(window).height()*0.02+"px;");

				
				/* 2017.2.7 移动端输入法遮盖输入框 */
				$(".wfj01_008 .wfj01_008_content table tr td input").focus(function(){
					$(".bgcolorFFF").css("height",$(window).height()*1.3+"px");
				})
				$(".wfj01_008 .wfj01_008_content table tr td input").blur(function(){
					$(".bgcolorFFF").css("height",$(window).height()*1+"px");
				})
				
				
			$(".wfj01_008_save").click(function(){
				var z1=/^(\d{16}|\d{19})$/;		//判断银行卡
				var z2="([\u4e00-\u9fa5])";   //判断中文
			 	if($(".verification").eq(0).val()==""){
					prompt("姓名不可为空");
				}else if(!new RegExp(z2).test($(".verification").eq(0).val())){
					prompt("姓名必须为中文");
				}else if($(".verification").eq(1).val()==""){
					prompt("省份不可为空");
    			}else if(!new RegExp(z2).test($(".verification").eq(1).val())){
					prompt("所属省份格式错误");
    			}else if($(".verification").eq(2).val()==""){
					prompt("所属城市不可为空");
    			}else if(!new RegExp(z2).test($(".verification").eq(2).val())){
					prompt("所属城市格式错误");					
    			}else if($("#bkName option:selected").val()==""){
    				prompt("请选择银行名字");	 					
    			}else if($(".verification").eq(4).val()==""){
					prompt("银行卡号不可为空");
    			}else if(!z1.test($(".verification").eq(4).val())){
					prompt("银行卡号格式错误"); 
    			}else if($("#accountNature").find("select").eq(0).val()==""){
					prompt("请选择银行卡类型");
    			}else if($("#cardStatus").find("select").eq(1).val()==""){
					prompt("请选择银行卡状态");
    			}else{ 
    				
    				$("#bankcardForm").attr("action",basePath+"ea/industry/ea_addBankCardInformation.jspa?ccompanyId="+ccompanyId+"&sccId="+sccId);
					$("#submit").click();
    				
					/* var ccompanyId=$("#ccompanyId").val();
					var sccId=$("#sccId").val();
					
					//银行卡卡号和对应的银行匹配
					var httpArg =$(".verification").eq(4).val();
    				var bkName=$("#bkName").val();
    				var cardType=$("#accountNature").val();
    				var uut = basePath + "/ea/industry/sajax_ea_checkBankNum.jspa";
    				
    				if(httpArg!=null&&httpArg!=''){
    					$.ajax({
    						url : encodeURI(uut),
    						type : "get",
    						async : false,
    						dataType : "json",
    						data:{"httpArg":httpArg},
    						 success : function(data) {
    							var member = eval("(" + data + ")");
    							var fault = member.data.mess;
    							var ctype=member.data.cardtype;
    							var bname=member.data.bankname;
    							 if(fault!=null){	
    								prompt(fault);
    								$("#cardStatus").val("");
    								$(".verification").eq(4).val("");
    							}else if(bname.indexOf("中国")==-1 && bname!=bkName){
    								
    								var bname="中国"+member.data.bankname;
    								if(bname==bkName){
    									if(ctype=="贷记卡"){
    										var ctype="信用卡";
    										}
    									if(ctype!=cardType){
    										//prompt("银行卡类型错误！");
    										$("#cardStatus").val("");
    										$(".verification").eq(4).val("");
    									
    									}else{  								
    									$("#bankcardForm").attr("action",basePath+"ea/industry/ea_addBankCardInformation.jspa?ccompanyId="+ccompanyId+"&sccId="+sccId);
    									$("#submit").click();
    							} 
    								}else{ 									
    								prompt("银行卡与所属银行不相符！");
    								$("#cardStatus").val("");
    								$(".verification").eq(4).val("");
    								}
    							} 
    							 else if(bname==bkName){
    								if(ctype=="贷记卡"){
    								    var ctype="信用卡";
    									}
    								if(ctype!=cardType){
    									prompt("银行卡类型错误！");
    									$("#cardStatus").val("");
    									$(".verification").eq(4).val("");
    								}else{
    									$("#bankcardForm").attr("action",basePath+"ea/industry/ea_addBankCardInformation.jspa?ccompanyId="+ccompanyId+"&user="+user);
        								$("#submit").click();
    									
    								}
    								
    							}    							 					
    						}, 					
    					})					
    				} */   			
				}
			
			});
	
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

    </script>
</body>
</html>