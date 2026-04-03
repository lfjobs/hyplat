<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>公司平台商城会员(购买)</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>

<link rel="stylesheet" href="<%=basePath%>/css/WFJClient/goodgm.css"
	type="text/css"></link>
</head>
<style type="text/css">
.top {
	width: 100%;
	height: 60px;
	background-color: buttonface;
	padding-top: 20px;
}

div {
	margin-top: 5px;
}
</style>
<body>

	<form method="post" id="formsutm" name="formsutm">
	<input type="submit" id="submit" name="submit" style="display: none;"> 
	<input type="text"  name="goodname" style="display: none;" value="${goodname }"> 
	<input type="text"  name="morre" style="display: none;" value="${morre }"> 
	<input type="text"  name="lei" style="display: none;"   value="${ lei}"> 
	<input type="text"  name="fenlei" style="display: none;" value="${ fenlei}"> 
	
		<div class="wfj10_008">
			<div class="wfj10_008_top">
				<ul>
					<li style="width:15%;"><a href="javascript:;"><img
							src="../Images/wfj_return.png" /> </a></li>
					<li style="width:70%;">公司平台商城会员（购买）</li>
				</ul>
			</div>
			<div class="wfj10_008_width">
				<div class="wfj10_008_info">
					<table border="0" width="100%">
						<tr>
							<td width="25%" height="30" style="color:#000; font-size:14px;">订单信息</td>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td width="25%;" rowspan="2"><img src="${pahe}" /></td>
							<td><a href="javascript:;">${goodname}</a></td>
							<td></td>
						</tr>
						<tr>
							<td><a href="javascript:;">￥${morre}</a></td>
							<td align="right">促销产品</td>
						</tr>
					</table>
				</div>
			</div>
			<div class="wfj10_008_content">
				<div class="wfj10_008_con_width">
					<table>
						<tr>
							<td align="left">单价：</td>
							<td><input type="text" value="${morre}" id="morre"
								readonly="readonly" /></td>
						</tr>
						<tr>
							<td align="left">输入数量：</td>
							<td><input type="text" id="shuliang" value="1"
								readonly="readonly" /></td>
						</tr>


						<tr>


							<td align="left">行业类别：</td>
							<td><select id="fenlei" name="company.industryType">
									<option value="0">====请选择====</option>
									<c:forEach items="${beans}" var="aa">
										<option value="${aa.codeValue}">${aa.codeValue}</option>
									</c:forEach>
							</select>
							</td>
						</tr>
						<tr>
							<td align="left">组织机构名：</td>
							<td><input type="text" name="company.companyIdentifier"
								id="compname1" /></td>
						</tr>
						<tr>
							<td align="left">公司名称：</td>
							<td><input type="text" name="company.companyName"
								id="compname2" /></td>
						</tr>

						<tr>
							<td align="left">法人代理：</td>
							<td><input type="text" name="cdl.companyManager"
								id="compstaff" /></td>
						</tr>
						<tr>
							<td align="left">注册号：</td>
							<td><input type="text" name="cdl.registrationNumber"
								id="compstaff1" /></td>
						</tr>
						<tr>
							<td align="left">联系方式：</td>
							<td><input type="text" name="cdl.companyPhone" id="comppahe" />
							</td>
						</tr>
						<tr>
							<td align="left">公司邮箱：</td>
							<td><input type="text" name="cdl.companyEmail" id="comppahe1" />
							</td>
						</tr>
						<tr>
							<td align="left">开户行名称：</td>
							<td><input type="text" name="cdl.bankNumber" id="comppahe2" />
							</td>
						</tr>
						<tr>
							<td align="left">开户行账号：</td>
							<td><input type="text" name="cdl.bankAccount" id="comppahe3" />
							</td>
						</tr>

						<tr>
							<td align="left">详细地址：</td>
							<td><input type="text" name="cdl.companyAddress"
								id="compadrrxx4" /></td>
						</tr>
						<tr>
							<td style=" padding-top:10px;">应付金额：</td>
							<td align="right"><div>
									<span id="qian"
										style="color: #F00;  font-size:24px; font-weight: bold; height:30px; line-height:30px;padding-right:10px;">${morre}</span>元
								</div></td>
						</tr>

						<tr>
							<td colspan="2"><div class="wfj10_008_commit" id="sumtin" >提交订单</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</form>
<script type="text/javascript">
 var basePath="<%=basePath%>";
 
 $(function()
 {
    var qian='${morre}';
		$("#shuliang").blur(function()
      {
 		     $("#qian").val(qian*$("#shuliang").val());
 		  
      });
      $("#compname1").blur(function()
      
      {
    
    			  var ulp=basePath + "/ea/wfjshop/sajax_ea_getcompispmun.jspa?goodsid="+$("#compname1").val();
           	   $.ajax({
         	    type: "GET",
          	   url: ulp ,
         	     dataType: "json",
          	   success: function(data){
                     var json = eval('(' + data + ')');
                    	if(json=='1')
                    	
                    	{
                    	alert("组织机构名重复请从填写");
                    	
                    	}
                      }
         });
      
      })
      $("#sumtin").click(function(){
         if($("#fenlei").val()==0)
         {
        	 alert("请选择公司行业");
           return;
         }
     
     	
          if(!CheckMail($("#comppahe1").val()))
         {
         	alert("您的电子邮件格式不正确");
          return;
         } 
         if(!isnull())
         {
         
            alert("信息不完整");
              return;
         }
          
           		$("#formsutm").attr("action",basePath + "/ea/wfjshop/ea_sevescomp.jspa ");
		document.formsutm.submit.click();
      })
 })
  function  isnull(){
    
     var  s=true;
      var allInputs = document.getElementsByTagName('input');
      for (var i = 0; i < allInputs.length; i++) {
		if (allInputs[i].type === 'text') {
			 if(allInputs[i].value=='')
			 {
    		 		s=false;
					break
		 }		    
		}
	}
	  return s;
  }
  //电子邮箱验证
  function CheckMail(mail) {
  
		 var filter  = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
		 if (filter.test(mail)) 
		 {		
			 return true;
		 }
		 else {
		 
		     return false;
				}
}
 


 

    
</script>
</body>



</html>
