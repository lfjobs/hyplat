<%@ page contentType="text/html;charset=UTF-8" import="java.util.*"%>
<%@ page import="hy.ea.bo.company.*"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	Float size = Float.valueOf((String) request.getAttribute("size"));
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>条码形成</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<style type="text/css">
<!--
body,td,th {
	font-size: <%=(int) (12 * size)%>             px;
}

body {
	background-color: #999999; 
}
-->
</style>
		<script language="javascript"> 

</script>

	</head>
	<body topmargin="0px" leftmargin="0px" rightmargin="0px"
		bottommargin="0px">

		<OBJECT id="wb" height="0" width="0"
			classid="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2" name="wb"></OBJECT>
		<%
			List list = (List) request.getAttribute("baseBeanList");
		%>
		<div
			style="overflow: no; height: <%=(int) (list.size() * 340 * size)%>px;">
			<%
				int left = 0;
				for (int i = 0; i < list.size(); i++) {
					PrintInformation bean = (PrintInformation) list.get(i);
			%>
			<div width="<%=(int) (283 * size)%>" height="<%=(int) (340 * size)%>"
				style="position: absolute; z-index: -1; padding-top: 0px; top: <%=i * (int) (340 * size)%>; px; left: 0px">
				<image src="<%=basePath%>js/photo/diaopai.jpg"
					width="<%=(int) (283 * size)%>" height="<%=(int) (340 * size)%>">
			</div>
			<table width="<%=(int) (283 * size)%>" border="0" cellspacing="0"
				cellpadding="0" height="<%=(int) (340 * size)%>">
				<tr>
					<td valign="top">
						<table width="<%=(int) (250 * size)%>" border="0" align="center"
							cellpadding="0" cellspacing="0">
							<tr>
								<td height="<%=(int) (85 * size)%>" align="right">
									<span
										style="width: <%=(int) (200 * size)%>px; height: <%=(int) (15 * size)%>px; 
											background: none; border: 0px; text-align: center"><b><%=bean.getCredentialsTitle()%></b>
									</span>
								</td>
							</tr>
						</table>

						<table width="<%=(int) (250 * size)%>" border="0" align="center"
							cellpadding="0" cellspacing="0">
							<tr>
								<td valign="bottom">
									<img src="<%=basePath + bean.getPhoto()%>"
										width="<%=(int) (102 * size)%>"
										height="<%=(int) (126 * size)%>" />
								</td>
								<td valign="top">
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td height="<%=(int) (25 * size)%>" align="right"
												valign="bottom" style="line-height: 15px">
												证件名称:
											</td>
											<td height="<%=(int) (25 * size)%>" align="center"
												valign="bottom" style="border-bottom: 1px solid #231815">
												<%=bean.getCredentialsName()%>
											</td>
										</tr>
										<tr>
											<td height="<%=(int) (25 * size)%>" align="right"
												valign="bottom" style="line-height: 15px">
												姓&nbsp;&nbsp;&nbsp;&nbsp;名:

											</td>
											<td height="<%=(int) (25 * size)%>" align="center"
												valign="bottom" style="border-bottom: 1px solid #231815">

												<%=bean.getStaffName()%>

											</td>
										</tr>
										<tr>
											<td height="<%=(int) (25 * size)%>" align="right"
												valign="bottom" style="line-height: 15px">
												职&nbsp;&nbsp;&nbsp;&nbsp;务:

											</td>
											<td height="<%=(int) (25 * size)%>" align="center"
												valign="bottom" style="border-bottom: 1px solid #231815">

												<%=bean.getCredentialsCode()%>

											</td>
										</tr>
										<tr>
											<td height="<%=(int) (25 * size)%>" align="right"
												valign="bottom" style="line-height: 15px">
												组&nbsp;&nbsp;&nbsp;&nbsp;别:

											</td>
											<td height="<%=(int) (25 * size)%>" align="center"
												valign="bottom" style="border-bottom: 1px solid #231815">
												<%=bean.getCredentialsType()%>
											</td>
										</tr>
										<tr></tr>
									</table>
								</td>
							</tr>
						</table>
						<p></p>
						<table width="<%=(int) (150 * size)%>" border="0" align="center"
							cellpadding="0" cellspacing="0"
							style="margin-top: <%=(int) (15 * size)%>px">
							<tr>
								<td>
									<%
										String data = (String) bean.getStaffIdentityCard();
											if (data != null) {
												StringBuffer barCode = new StringBuffer();
												barCode.append("<img src='");
												barCode.append(request.getContextPath());
												barCode.append("/CreateBarCode?data=");
												barCode.append(data);
												int width = (int) (200 * size);
												barCode
														.append("&barType=TF25&height=20&headless=true&drawText=true&width=1' width='"
																+ width + "'>");
												out.println(barCode.toString());
											} else {
												out.println("no data");
											}
									%>
								</td>
							</tr>
						</table>

						<table width="<%=(int) (250 * size)%>" border="0" align="center"
							cellpadding="0" cellspacing="0">
							<tr>
								<td width="<%=(int) (50 * size)%>"
									height="<%=(int) (30 * size)%>" align="right" valign="bottom">
									<span style="line-height: 15px">&nbsp;&nbsp;</span>时&nbsp;&nbsp;间：

								</td>
								<td width="<%=(int) (200 * size)%>"
									height="<%=(int) (30 * size)%>" align="left" valign="bottom"
									style="border-bottom: 1px solid #231815">
									<%=bean.getCredentialsDate()%>&nbsp;至&nbsp;<%=bean.getCredentialsDate2()%>
								</td>
							</tr>
							<tr>
								<td height="<%=(int) (30 * size)%>" align="right"
									valign="bottom" style="line-height: 15px">
									地&nbsp;&nbsp;点：

								</td>
								<td height="<%=(int) (30 * size)%>" align="left" valign="bottom"
									style="border-bottom: 1px solid #231815">
									<%=bean.getAddress()%>
								</td>
							</tr>
						</table>


					</td>
				</tr>
			</table>
			<%
				}
			%>

		</div>
	</body>
</html>

