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
<title>公司平台管理系统-个人购买</title>

<meta http-equiv="Access-Control-Allow-Origin" content="*">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" type="text/css" href="<%=basePath %>css/WFJClient/wfjapp.css"/>
<script type="text/javascript" src="<%=basePath %>js/jquery-1.6.1.min.js"></script>
<script type="text/javascript" src="<%=basePath %>js/ea/marketing/wfjeshop/personalBuy.js"></script>
<script type="text/javascript" src="<%=basePath %>js/jqModal/jqModal.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath %>js/jqModal/css/jqModal_blue.css"/>
<script type="text/javascript">

</script>
</head>

<body>
	<div class="wfj11_008">
    	<div class="wfj11_008_top">
        	<ul id="ul">
            	<li><a href="javascript:history.go(-1)" target="_self"><</a></li>
            	<li>购买订单</li>
            	<li><a href="javascript:;"><img src="<%=basePath %>images/WFJClient/PersonalJoining/top_more_02.png" /></a></li>
            </ul>
        </div>
        <div class="wfj11_008_name">
            <table width="100%">
                <tr>
                    <td width="30%" align="center" rowspan="2"><img src="<%=basePath %>images/WFJClient/PersonalJoining/me_business_01.png" /></td>
                    <td width="70%">${goodname }</td>
                </tr>
                <tr>
                    <td><span>￥${morre}</span></td>
                </tr>
            </table>
        </div>
        <div class="wfj11_008_content">
        <form method="post" id="formsutm" name="formsutm">
        	<div class="wfj11_008_width">
        		
        		
        	<input type="submit" style="display: none;"id="submit"> 
		 	<input  name="goodname" value="${goodname} " style="display: none;"/>
		    <input name="morre" value="${morre}" style="display: none;"/> 
		    <input name="fenlei" value="${fenlei}"style="display: none;"/>
        	 <input name="ppid" value="${ppid}"style="display: none;"/>
        		
                <table width="100%" border="1" style="margin-top:10px; margin-bottom:10px;">
                    <tr>
                        <td width="25%" align="center">选择上级</td>
                        <td width="75%">
                        	<select id="dl" name="activity" style="width:100%;">
                        		<option style="width:75%; overflow:hidden;"value="0">选择上级代理</option>
                   
                            </select>
                        </td>
                    </tr>
                </table>
                <table width="100%" border="1" style="margin-bottom:10px;">
                    <tr>
                    	<td width="25%" align="center">法人代表</td>
                    	<td width="75%"><input type="text"  value="例如:张三" name=""/></td>
                    </tr>
                    <tr>
                    	<td width="25%" align="center">联系电话</td>
                    	<td width="75%"><input type="text" value="例如:13122226656" name="staff.reference" maxlength="11"/></td>
                    </tr>
                    <tr>
                    	<td width="25%" align="center">个人邮箱</td>
                    	<td width="75%"><input type="text" id="comppahe1" value="例如:123456@qq.com" name="staff.referenceOrganization"/></td>
                    </tr>
                    <tr>
                    	<td width="25%" align="center">详细地址</td>
                    	<td width="75%"><input type="text" value="例如:东直门外大街" name="staff.staffAddress"/></td>
                    </tr>
                </table>
                <table width="100%" border="1">
                    <tr>
                    	<td width="25%" align="center">开户银行</td>
                    	<td width="75%"><select name="cdl.bankNumber">
                    			<option value="中国建设银行">中国建设银行</option>
                    			<option value="中国工商银行">中国工商银行</option>
                    			<option value="中国交通银行">中国交通银行</option>
                    			<option value="中国农业银行">中国农业银行</option>
                    	</select></td>
                    </tr>
                    <tr>
                    	<td width="25%" align="center">开户账号</td>
                    	<td width="75%"><input type="text" value="" id="txt" name="staff.bankNum" onkeyup="this.value=this.value.replace(/(\d{4})(?=\d)/g,'$1 ')" maxlength="23" /></td>
                        <div id="show" style="display:none"><!--银行账户取值--></div>
                        
                    </tr>
                </table>
                </form>
            </div>
            <div class="wfj11_008_buy">
            	<div>立即购买</div>
            </div>
        </div>
        <script type="text/javascript">
			var pa = /^([0-9])+$/;
			var pas = /^([0-9]){19}$/;
			
			$(document).ready(function(e) {
				var txt = document.getElementById("txt").value;
				var a = document.getElementById("show").text;
				while(txt.indexOf(" ")!=-1){
					a=txt.replace(" ","");
				}
			});
		</script>
        <div class="wfj11_008_hidden_buy" style="display:none;">
            <table id="pays" width="100%">
                <tr>
                    <td width="50%" style="padding-left:4%;"><span>需支付：</span></td>
                    <td align="right" style="padding-right:4%;" width="50%"><span id="money" style="color:#F74C31;">￥${morre }</span></td>
                </tr>
            </table>
        	<div class="wfj11_008_buy_width">
                <table>
                	<tr>
                    	<td colspan="2">选择支付方式</td>
                    </tr>
                	<tr>
                    	<td class="wfj11_008_pay"><img src="<%=basePath %>images/WFJClient/PersonalJoining/all_pay_01.png" /></td>
                    	<td align="right" class="wfj11_008_choice"><img onclick="chance(1)" width="24" height="24" src="<%=basePath %>images/WFJClient/PersonalJoining/choice_01.png" />
                    	</td>
                    </tr>
                    <input type="hidden" name="code" id="code" value=""/>
                    <input type="hidden" id="ddid" value=""/>
                 </table>
            </div>
            <div class="wfj11_008_bottom_commit">
            	<div onclick="zfb()">确认支付</div>
            </div>
        </div>
    </div>
    <script type="text/javascript">
    	
    </script>
   <div id="occlusion2" class="jqmWindow jqmWindowcss1"></div>
</body>
<script type="text/javascript">
	var  basePath='<%=basePath%>';
	var  where='${fenlei}';
    var mort="${morre}";
    var orname="${goodname}";
 	function chance(code){
 		$("#code").val(code);
 	}
</script>
</html>