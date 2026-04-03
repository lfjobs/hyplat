<%@ page language="java"  pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>模板套红</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel='stylesheet' type='text/css' href='dialog.css'>
		<script language=javascript>
function CheckValue(theForm)
{
  var mObject = window.dialogArguments;
  mObject.SelectValue=theForm.WordList.value;
  window.close();
  return;
}
</SCRIPT>
	</head>
	<body bgcolor="menu">
		<form name="FormSelect">
			<table border=0 cellspacing='0' cellpadding='0' width=100%
				height=100% align=center>
				<tr>
					<td align=center>
						<table border=0 cellspacing='0' cellpadding='0'>
							<tr>
								<td align=left nowrap></td>
								<td align=left nowrap class="TDStyle">
									请选择您要使用的word模板
								</td>
							</tr>
							<tr>
								<td align=right nowrap class="TDStyle"></td>
								<td>
									<select name=WordList style='width: 240 px;'>
										<option value="redhead01.doc">
											北京天太世统科技有限公司
										</option>
										<option value='redhead02.doc'>
											北京天太胜威科技有限公司
										</option>
									</select>
								</td>
							</tr>
							<tr>
								<td align=left nowrap></td>
								<td class="TDStyle">
									&nbsp;
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td align=center>
						<input class=button type=button value="  确定  "
							onclick="CheckValue(FormSelect);">
						<input class=button type=button value="  取消  "
							onclick="window.close();">
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
