<!DOCTYPE HTML>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ page import="hy.ea.bo.Company"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	Company c = (Company)session.getAttribute("currentcompany");
%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    
    <title>申请退货</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<link rel="stylesheet" type="text/css"  href="<%=basePath%>css/admin_main111.css" />
<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />

<script src="<%=basePath%>js/ea/finance/BenDis/refund.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/uploadify/uploadify.css" />
       <script type="text/javascript" src="<%=basePath%>js/uploadify/jquery.uploadify.min.js"></script>
<script type="text/javascript">
  var basePath="<%=basePath%>";
  var companyID = '<%=c.getCompanyID()%>';
  var id="${id}";
  var ppid="${ppid}";
  var token=0;
</script>

  </head>
  
  <body>
   <div  class="jqmWindow "
		style="background-color:white; width: 600px;height: 500px; margin: 0 auto; border: 1px solid gray;" id="refund">
            <div  style="height: 50px;background-color:#00CCFF;text-align: center;font-size: 25px;line-height: 50px;">
            退 货 申 请 单
            </div>
            <div style="overflow: auto;height: 350px;font-size: 15px">
		<form name="refundForm" id="refundForm" method="post"
			enctype="multipart/form-data" >
			<input type="submit" name="submit" style="display: none" />
			
			<center>
			<table width="100%" style="height: 1000px;" id="SearchTable2" >

				<tr>

					<td align="right" style="width:150px;">退款类型：</td>
					<td>
					
					<select style="width:150px;height: 20px;"
							name="refundSheet.refundType" id="refundType" >
							<option value="00" >仅退款</option>
							
							<c:if test="${fxStatus=='已出库正在物流'}">
							<option value="01" id="money">
							退货退款</option>
							</c:if>
							
							</select>
							</td>
				</tr>
				<tr >
					<td align="right" style="width:150px;">退款原因：</td>
					<td>
					
					<select style="width:150px;height: 20px;"
							name="refundSheet.refundCause" id="refundCause" >
							<option value="00">七天无理由退换货</option>
							<option value="01">收到商品破损/漏发</option>
							<option value="02">商品与描述不符合</option>
							<option value="03">商品质量问题</option>
							<option value="04">未按时间发货</option>
							</select>
							</td>
				</tr>
				<tr >
					<td align="right" style="width:150px;">退款金额：</td>
					<td align="left"><input name="refundSheet.refundMoney" style="width:50px;height: 20px;"
						value="" class="put3"/>元</td>
				</tr>
				<tr >
					<td align="right" style="width:150px;">退款说明：</td>
					<td align="left">
					<textarea  name="refundSheet.refundRemark" class="ckTextLength put3"
						value=""  style="width:250px;height: 50px;" maxLength="50"></textarea>
						</td>
				</tr>
			
				<tr>
				<td align="right" style="width:150px;height: 250px;">上传凭证1:</td>
				             <td>                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            

							
						 <div class="uploadpic" id="file" style="width:200px;height: 200px;border:solid 1px gray;"></div>
							<input type="hidden"  name="refundSheet.voucherfile1" id="voucherfile1" class="voucherfile"/>
							<input type="file"
								name="upload1" id="upload" class="upload"  multiple="true">
								<p>尺寸：建议300px*300px</p>
								<p>大小：小于3M</p>
						</td>
				</tr>
				<tr>
				<td align="right" style="width:150px;height: 250px;">上传凭证2:</td>
				             <td>                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            

							<div class="uploadpic" id="file1" style="width:200px;height: 200px;border:solid 1px gray;"></div>
							<input type="hidden"  name="refundSheet.voucherfile2" id="voucherfile2" class="voucherfile"/>
							
							<input type="file"
								name="upload2" id="upload1" class="upload" multiple="true">
								<p>尺寸：建议300px*300px</p>
								<p>大小：小于3M</p>
						</td>
				</tr>
				<tr>
				<td align="right" style="width:150px;height: 250px;">上传凭证3:</td>
				             <td>                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            

							<div class="uploadpic" id="file2" style="width:200px;height: 200px;border:solid 1px gray;"></div>
							<input type="hidden"  name="refundSheet.voucherfile3" id="voucherfile3" class="voucherfile"/>
							
							<input type="file"
								name="upload2" id="upload2" class="upload" multiple="true"/>
								<p>尺寸：建议300px*300px</p>
								<p>大小：小于3M</p>
						</td>
				</tr>

				</table>
				</center>
				</form>	
				</div>
                 <div style="text-align: center;height: 100px;line-height: 100px;">
                 <input type="button" class="submitResult"  value="提交申请" style="background-color: #00CCFF; "/>
                 &nbsp;&nbsp;&nbsp;
                  <input type="button" class="submitClose" id="pass" value="退      出" style="background-color: #00CCFF; "/>
                 </div>
				
			</div>
			<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
  </body>
 
</html>
