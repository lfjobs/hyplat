<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String backurl=request.getParameter("backurl");
%>
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/websitemall/card/style01.css"/>
<script src="<%=basePath%>/js/jquery.js" type="text/javascript"></script>
<script src="<%=basePath%>js/ea/websitemall/card/android/personalInformation_bank_add.js" type="text/javascript"></script>
<title>添加银行卡</title>
<style type="text/css">
	#prompt div{
		width: 80%;
		background: rgba(0,0,0, 0.5);
	}
	.message input{
	border: 0px;
	width:90%;
	height:100%;
	background-color: #F2F2F2;
	position: relative;
	left: 10%;
	}
</style>
<script type="text/javascript">
	var basePath="<%=basePath%>";
	var editType="${editType}";
	var backurl="<%=backurl%>";
	var backID="${staffVo.bankSn['staffBank'].bankAccountID}";
	var flag="${flag}";
</script>
</head>

<body class="bgcolorFFF">
	<div class="wfj01_008">
    	<div class="wfj01_008_top">
        	<ul id="tops">
            	<li class="wfj_return"><a href="javascript:;" target="_self"><img src="<%=basePath%>images/ea/websitemall/card/wfj_return_02.png" /></a></li>
            	<li>添加银行卡</li>
            	<li><a href="javascript:;" style="display: none;"><img src="<%=basePath%>images/ea/websitemall/card/dot.png" /></a></li>
            </ul>
        </div>
        <div class="wfj01_008_title">
        	<div>请绑定持卡人本人的银行卡</div>
        </div>
        <div id="prompt" style="width: 100%;display: none;">
        <center>
        	<div>
        		<span style="position: relative;top: 19.8%;"></span>
        	</div>
        	</center>
        </div>
        <form name="form" id="form" method="post">
        <div class="wfj01_008_content">
            <table>
            	<tr>
                	<td width="15%" align="center"><img src="<%=basePath%>images/ea/websitemall/card/bank_card_01.png" /></td>
                	<td width="85%"><input type="text" name="staffVo.cstaff.staffName" placeholder="请输入姓名" class="verification" 
                	  value="${staffVo.bankSn['staffBank'].cardholder}"/>
                	  <input type="hidden" name="staffVo.cstaff.staffID" value="${staffId}" id="staffId">
                	<input type="hidden" name="staffVo.bankSn['0'].bankAccountID"   value="${staffVo.bankSn['staffBank'].bankAccountID}" id="bankId"/></td>
                </tr>
            	<tr>
                	<td align="center"><img src="<%=basePath%>images/ea/websitemall/card/bank_card_03.png" /></td>
                	<td><input type="text" name="province" placeholder="请输入所属省份"  class="verification" value="${staffVo.bankSn['staffBank'].province}"/></td>
                </tr><!-- province -->
                <tr>
                	<td align="center"><img src="<%=basePath%>images/ea/websitemall/card/wfj_all_Profile_04.png" /></td>
                	<td><input type="text" name="city" placeholder="请输入所属城市"  class="verification" value="${staffVo.bankSn['staffBank'].bankAddress}"/></td>
                </tr>                       
                <tr>
                	<td align="center"><img src="<%=basePath%>images/ea/websitemall/card/bank_card_02.png" /></td>
                	<td>
                		<select name="staffVo.bankSn['0'].bankName"  id="bankName">
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
                	</td>            
                </tr>               
                
                <tr>
                	<td align="center"><img src="<%=basePath%>images/ea/websitemall/card/bank_card_05.png" /></td>
                	<td><input type="text" name="staffVo.bankSn['0'].branchName" placeholder="请输入支行名称       可不填"  class="verification" value="${staffVo.bankSn['staffBank'].branchName}"/></td>
                </tr>
            	<tr>
                	<td align="center"><img src="<%=basePath%>images/ea/websitemall/card/bank_card_04.png" /></td>
                	<td><input type="text" name="staffVo.bankSn['0'].bankAccount" placeholder="请输入卡号"  class="verification" value="${staffVo.bankSn['staffBank'].bankAccount}"/></td>
                </tr>
                <tr>
                	<td align="center"><img src="<%=basePath%>images/ea/websitemall/card/wfj09_25_03.png" /></td>
                	<td><!--信用卡=贷记卡  -->
                		<select name="staffVo.bankSn['0'].accountNature"  id="accountNature">
                			<c:if test="${staffVo.bankSn['staffBank'].accountNature==null}">
                				<option value="">请选择银行卡类型</option>
	                			<option value="借记卡">借记卡</option>
	                			<option value="储蓄卡">储蓄卡</option>
	                			<option value="信用卡">信用卡</option>
                			</c:if>
                			<c:if test="${staffVo.bankSn['staffBank'].accountNature=='借记卡'}">
                				<option value="借记卡">借记卡</option>
	                			<option value="储蓄卡">储蓄卡</option>
	                			<option value="信用卡">信用卡</option>
                			</c:if>
                			<c:if test="${staffVo.bankSn['staffBank'].accountNature=='储蓄卡'}">
	                			<option value="储蓄卡">储蓄卡</option>
	                			<option value="借记卡">借记卡</option>
	                			<option value="信用卡">信用卡</option>
                			</c:if>
                			<c:if test="${staffVo.bankSn['staffBank'].accountNature=='信用卡'}">
                				<option value="信用卡">信用卡</option>
                				<option value="借记卡">借记卡</option>
	                			<option value="储蓄卡">信用卡</option>
                			</c:if>
                		</select>
                	</td>
                </tr>
                <tr>
                	<td align="center"><img src="<%=basePath%>images/ea/websitemall/card/wfj09_25_03.png" /></td>
                	<td>
                		<select name="staffVo.bankSn['0'].cardType" id="cardStatus">
                			<c:if test="${staffVo.bankSn['staffBank'].cardType==null}">
                				<option value="">请选择银行卡状态</option>
	                			<option value="公开">企业账户</option>
	                			<option value="对私">个人账户</option>
                			</c:if>
                			<c:if test="${staffVo.bankSn['staffBank'].cardType=='00'}">
                				<option value="对私">个人账户</option>
	                			<option value="公开">企业账户</option>
                			</c:if>
                			<c:if test="${staffVo.bankSn['staffBank'].cardType=='01'}">
                				<option value="公开">企业账户</option>
                				<option value="对私">个人账户</option>
                			</c:if>
                		</select>
                </tr>
            </table>
            
        </div>
        <input type="submit" name="submit" id="submit" style="display: none;">
        <iframe name="hidden"  style="display: none;"></iframe>
        </form>
       <!--  <div style="background-color:#F2F2F2;width: 100%;height: 40px;" class="message">
            	<table style="height: 100%;width: 100%;">
            		<tr>
            			<td width="50%"><input type="text" placeholder="请输入验证码"></td>
            			<td align="center" style="background-color: #FF8247;" class="me">
            				<font color="#FFFAFA">点击获取验证码</font>
            			</td>
            		</tr>
            	</table>
            </div> -->
        <div class="wfj01_008_bottom" id="preservation">
        	<div class="wfj01_008_save">保存信息</div>
        	
        	
        	        	
        	
        </div>
	</div>
	
	<div style="position: absolute;top:0%;width: 100%;height:100%;background: rgba(0,0,0, 0.5);display: none;" class="wfj01_001_popup">
     	<div style="background-color: #FFFFFF;width: 100%;height:8%;position: absolute;top: 84%;text-align: center;" class="wfj01_001_delete_body typeface">
    		<div style="padding-top: 4%;cursor:pointer;">删除该银行卡</div>
    	</div> 
    	<hr  style="position: absolute;top: 92%;color:#000000;width: 100%;">
    	<div style="background-color: #FFFFFF;width: 100%;height:8%;position: absolute;top: 92%;text-align: center;" class="wfj01_001_popup_body typeface">
    		<div style="padding-top: 4%;cursor:pointer;" class="positionSelection" name="job">设置为默认银行卡</div>
    	</div>
    </div>

     <script type="text/javascript">
     $(document).ready(function(){
    	 var bankname="${staffVo.bankSn['staffBank'].bankName}";
      	$("#bankName option").each(function(){
      		if(bankname==$(this).text()){
      			$(this).attr("selected",true);
      		}
      	});
     });
     </script>
            
            
</body>
</html>