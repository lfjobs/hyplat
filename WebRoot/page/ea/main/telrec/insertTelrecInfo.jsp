<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">  
<html xmlns="http://www.w3.org/1999/xhtml"> 
	<head>
	    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css" />
		<!-- <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" /> -->
		<!-- <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css" /> -->
		
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<script type="text/javascript" src="<%=basePath%>page/ea/main/telrec/js/valifunc.js"></script>
		<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<title>电话记录录入</title> 
	</head>

	<body bgcolor="#d2e3ec">
		<h3>
			电话记录录入
		</h3>
		<hr/>
		<div align="center">
			<form action="clientInsertTelRec.do">
				<table>
					<tr>
						<td>
							电话号码：
						</td>
						<td>
							<input id="telno" name="telno"/>
						</td>
					</tr>
					<tr>
						<td>
							呼入呼出：
						</td>
						<td>
							<select name="in_or_out" id="in_or_out"> 
								<option value="呼入">
									呼入
								</option>
								<option value="呼出">
									呼出
								</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>
							客户姓名：
						</td>
						<td>
							<input id="customer_span" name="customer_name"/>
						</td>
					</tr>
					<tr>
						<td>
							来电时间：
						</td>
						<td>
							<input name="start_time" id="start_time" onfocus="daytime(this)"/>
						</td>
					</tr>
					<tr>
						<td>
							结束时间：
						</td>
						<td>
							<input name="end_time" id="end_time" onfocus="daytime(this)"/>
						</td>
					</tr>
					<tr>
						<td>
							通话内容：
						</td>
						<td>
							<textarea cols="50" rows="3" id="content" name="content" onkeyup="imposeMaxLength(this)" ></textarea>
						</td>
					</tr>
					<tr>
						<td></td>
						<td colspan="2">
							<input type="button" id="save" value="提交"/>
							<input type="hidden" name="user_id" id="user_id" value="${account.accountID}"/>
							<input type="hidden" name="user_name" id="user_name" value="${account.accountName}"/>
							<input type="hidden" name="customer_id" id="customer_id" value=""/>
							<input type="hidden" name="isweb" value="1"/>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<script type="text/javascript">
			var basePath="<%=basePath%>";
			function imposeMaxLength(obj){ 			
 				if (obj.value.length>200)
 			 	obj.value=obj.value.substring(0,200)
			}
			
		</script> 
		
		<script src="<%=basePath%>page/ea/main/telrec/js/insertTelrecInfo.js" type="text/javascript"></script>
	</body>
</html>
