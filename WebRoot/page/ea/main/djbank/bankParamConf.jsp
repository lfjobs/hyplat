<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.tiantai.nwa.tbank.bo.BankSX" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
List listBank  = (List)request.getAttribute("banklist");
Iterator iter = listBank.iterator();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">    
    <title>网银适配器设置参数</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">

	<link href="<%=basePath%>/css/ea/organization.css" rel="stylesheet"type="text/css"/>
	<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
	<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
	<script src="<%=basePath%>js/nwa/bankParamConf.js" type="text/javascript"></script>
	<script type="text/javascript">
		var basePath = "<%=basePath%>";
	</script>
	<style type="text/css">
	input
	{
		width:200px;
	}
	
	</style>
  </head>

<body style="background-image:url(<%=basePath%>/images/bg01.jpg)">
<table width="100%" height="68" border="0" align="center" cellpadding="0" cellspacing="2">
  <tr>
    <td align="left" valign="top">
      <div class="right">
    	<div class="qh_gg_nav">&nbsp;对接银行参数设置</div> 
          <table width="100%" 　border="0" align="center" cellpadding="0" cellspacing="0">
			<tr>
			  <td width="3%" rowspan="11" align="center">
	<form name="paramForm" id="paramForm" method="post" target="_top">
		<input type="submit" name="submit" style="display:none;"/>
			
			<div style="">
				<table id="paramtbl">
					<tr>
					  <td align="right">选择银行：</td>
					  <td><select id="bank">
							<option value="" selected>请选择</option>
							<% while (iter.hasNext())
								{
									BankSX bsx = (BankSX)iter.next();
							%>
								<option value="<%=bsx.getEsx()%>"><%=bsx.getName()%></option>
							<%							
								}
							 %>
	 						</select>
						</td>
				    </tr>
					<tr>
						<td align="right">
							银行业务系统：						</td>
						<td>
							<input type="text" name="bankLinkParam.bankBusiname" id="bankBusiname"/>
							<input type="hidden" id="key" name="bankLinkParam.key" value="${bankLinkParam.key}"/>
							<input type="hidden" id="bpID" name="bankLinkParam.bpID" value="${bankLinkParam.bpID}"/>
						    <input type="hidden" id="enterpriseID" name="bankLinkParam.enterpriseID" value="${bankLinkParam.enterpriseID}"/>						</td>
					</tr>
					<tr>
						<td align="right">
							银行名称：						</td>
						<td>
							<input type="text" name="bankLinkParam.bankName" id="bankName"  value=""/>						</td>
					</tr>
					<tr>
						<td align="right">
							银行英文缩写：						</td>
						<td>
							<input type="text"  name="bankLinkParam.enameSx" id="enameSx" value="" readonly/>						</td>
					</tr>
					<tr>
						<td align="right">
							是否有效：						</td>
						<td>
							<select name="bankLinkParam.isEnabled" id="isEnabled">
								<option value="0" />
									未对接
								<option value="1" />
									已对接
							</select>						</td>
					</tr>
					<tr>
						<td align="right">
							银行客服号/企业号：						</td>
						<td>
							<input type="text" id="customerCode" name="bankLinkParam.customerCode" value=""/>						</td>
					</tr>
					<tr>
						<td align="right">
							联行行号：						</td>
						<td>
							<input type="text" id="bankID" name="bankLinkParam.bankID" value=""/>						</td>
					</tr>
					<tr>
						<td align="right">
							用户代码：						</td>
						<td>
							<input type="text" id="useCode" name="bankLinkParam.useCode" value=""/>						</td>
					</tr>
					<tr>
						<td align="right">
							登录密码：						</td>
						<td>
							<input type="text" id="loginPassword" name="bankLinkParam.loginPassword" value=""/>						</td>
					</tr>
					<tr>
						<td align="right">
							连接测试账号：						</td>
						<td>
							<input type="text" id="testAccount" name="bankLinkParam.testAccount" value=""/>						</td>
					</tr>
					<tr>
						<td align="right">
							业务接口Ip/域名：						</td>
						<td>
							<input type="text" id="interfaceIp" name="bankLinkParam.interfaceIp" value=""/>						</td>
					</tr>
					<tr>
						<td align="right">
							业务接口端口号：						</td>
						<td>
							<input type="text" id="port" name="bankLinkParam.port" value=""/>						</td>
					</tr>
					<tr>
						<td align="right">
							通讯协议：						</td>
						<td>
							<input type="text" id="protocol" name="bankLinkParam.protocol" value=""/>						</td>
					</tr>
					<tr>
						<td align="right">
							业务接口路径：						</td>
						<td>
							<input type="text" id="path" name="bankLinkParam.path" value=""/>						</td>
					</tr>

                     <tr>
						<td align="center" colspan="2">
							<input type="button" value="保存" id="save" style="width:auto;"/>						</td>
					</tr>
				</table>
			</div>
		</form>
			  </td>                      
			</tr>                    
		  </table>
		  <s:token></s:token>
				 
        </div>
      </td>
  </tr>
</table>	 
</body>
</html>
