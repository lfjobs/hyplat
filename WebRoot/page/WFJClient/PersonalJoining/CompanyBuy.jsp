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
<title>公司平台管理系统-公司购买</title>

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
<script type="text/javascript" src="<%=basePath %>js/jqModal/jqModal.js"></script>
<script type="text/javascript" src="<%=basePath %>js/ea/marketing/wfjeshop/companyBuy.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath %>js/jqModal/css/jqModal_blue.css"/>

</head>
<body>
	<div class="wfj11_008">
    	<div class="wfj11_008_top">
        	<ul id="ul">
            	<li><a href="javascript:history.go(-1);" target="_self"><</a></li>
            	<li>购买订单</li>
            	<li><a href="javascript:;"><img src="<%=basePath %>images/WFJClient/PersonalJoining/top_more_02.png" /></a></li>
            </ul>
        </div>
        <div class="wfj11_008_name">
            <table width="100%">
                <tr>
                    <td width="30%" align="center" rowspan="2"><img src="<%=basePath %>images/WFJClient/PersonalJoining/com_business_01.png" /></td>
                    <td width="70%">${goodname }</td>
                </tr>
                <tr>
                    <td><span>￥${morre}</span></td>
                </tr>
            </table>
        </div>
        <form method="post" id="formsutm" name="formsutm">
        
    		<input type="submit" id="submit" name="submit" style="display: none;"> 
			<input type="text"  name="goodname" style="display: none;" value="${goodname }"> 
			<input type="text"  name="morre" style="display: none;" value="${morre }"> 
       		<input type="text"  name="ppid" style="display: none;" value="${ppid}"> 
        
        <div class="wfj11_008_content">
        	<div class="wfj11_008_width">
                <table width="100%" border="1" style="margin-top:10px; margin-bottom:10px;">
                    <tr>
                        <td width="25%" align="center">行业类别</td>
                        <td width="75%">
                        	<input  id="fenlei2" type="hidden" value="" name="company.industryType"/>
                        	<select id="fenlei"  style="width:100%;">
                           		<option style="width:75%; overflow:hidden;" value="0">====请选择====</option>
                           	<c:forEach items="${beans}" var="aa"> 
										<option style="width:75%; overflow:hidden;" value="${aa.codeID}">${aa.codeValue}</option>
							</c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                    	<td width="25%" align="center">二级行业</td>
                    	<td width="75%">
                    		<select id="fenlei1" name="" style="width:100%;">
                    			<option style="width:75%; overflow:hidden;" value="0">====请选择====</option>
                    		</select>
                    	</td>
                    </tr>
                </table>
                <table width="100%" border="1" style="margin-bottom:10px;">
                    <tr>
                    	<td width="25%" align="center">公司名称</td>
                    	<td width="75%"><input type="text" value="" name="company.companyName" /></td>
                    </tr>
                    <tr>
                    	<td width="25%" align="center">机构名称</td>
                    	<td width="75%"><input type="text" id="compname1" value="" name="company.companyIdentifier" maxlength="20"/></td>
                    </tr>
                    <tr>
                    	<td width="25%" align="center">详细地址</td>
                    	<td width="75%"><input type="text" value="" name="cdl.companyAddress"/></td>
                    </tr>
                    <tr>
                    	<td width="25%" align="center">注册号</td>
                    	<td width="75%"><input type="text" value="" name="cdl.registrationNumber"/></td>
                    </tr>
                    <tr>
                    	<td width="25%" align="center">公司邮箱</td>
                    	<td width="75%"><input type="text" id="comppahe1" value="" name="cdl.companyEmail"/></td>
                    </tr>
                </table>
                <table width="100%" border="1" style="margin-bottom:10px;">
                    <tr>
                    	<td width="25%" align="center">法人代表</td>
                    	<td width="75%"><input type="text" value="" name="cdl.companyManager" maxlength="10"/></td>
                    </tr>
                    <tr>
                    	<td width="25%" align="center">联系电话</td>
                    	<td width="75%"><input type="text" value="" name="cdl.companyPhone" maxlength="11"/></td>
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
                    	</select>
                    	</td>
                    </tr>
                    <tr>
                    	<td width="25%" align="center">开户账号</td>
                    	<td width="75%"><input type="text" id="txt" name="cdl.bankAccount" onkeyup="this.value=this.value.replace(/(\d{4})(?=\d)/g,'$1 ')" maxlength="23"  value=""/></td>
                        <div id="show" style="display:none"><!--银行账户取值--></div>
                    </tr>
                </table>
            </div>
            <div class="wfj11_008_buy">
            	<div>立即购买</div>
            </div>
        </div></form>
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
                    <td align="right" style="padding-right:4%;" width="50%"><span id="money" style="color:#F74C31;">￥${morre}</span></td>
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
                    	<input type="hidden" name="code" value="1"/>
                    	</td>
                    </tr>
                 </table>
            </div>
            <div class="wfj11_008_bottom_commit">
            	<div onclick="zfb()">确认支付</div>
            </div>
        </div>
    </div>
    <script type="text/javascript">
    	var basePath='<%=basePath%>';
        var mort="${morre}";
        var orname="${goodname}";
      //验证组织结构名
        $("#compname1").blur(function(){
            var ulp=basePath + "/ea/wfjshop/sajax_ea_getcompispmun.jspa?goodsid="+$("#compname1").val();
            $.ajax({
                type: "GET",
                url: ulp ,
                dataType: "json",
                success: function(data){
                var json = eval('(' + data + ')');
                if(json=='1'){
                	alert("组织机构名重复请从填写");
                	$("#compname1").focus();
                	$("#compname1").val("");
                	}
                }
              });
        });
      //选择支付方式
     	function chance(code){
     		$("#code").val(code);
     	}
    </script>
   <div id="occlusion2" class="jqmWindow jqmWindowcss1"></div>
</body>
</html>