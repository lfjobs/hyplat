<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>打印单据</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
		%>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/ea/finance/invoicing/archive_print.js"></script>
		<style type="text/css">
.table {
	border-collapse: collapse;
	border: 1px solid #000000;
	font-size: 9px;
}

.table th {
	border: 1px solid #000000;
	color: #000000;
}

.table td {
	border: 1px solid #000000;
	color: #000000;
}

body,td,th {
	font-size: 9px;
}

body {
	margin-left: 15px;
}
</style>
		<script type="text/javascript">
		    var basePath = "<%=basePath%>";
		    $(document).ready(function(){
		
		    var printtype = '${printtype}';
		    if(printtype=="出库单"){
		       $(".out").hide();
		      }else{
		       $(".out").show();
		      }
		      })
		</script>
	</head>
	<body>
		<div id="apDiv1"></div>
		<div id="tableprint" align="center">
			<form id="printform">
				<table width="620" border="0" cellpadding="0" cellspacing="0"
					style="background: #FFFFFF;">
					<tr>
						<td height="25" align="center" style="font-weight: bold">
							&nbsp;${printtype}
						</td>
					</tr>
				</table>
				<table width="620" border="0" cellpadding="0" cellspacing="0"
					style="background: #FFFFFF;">
					<tr>
					</tr>
					<tr>
						<td width="10%" height="25" align="left">
							公司：
						</td>
						<td width="30%" align="left">
							${archiveTemp2.companyName}
						</td>
						<td width="6%" align="left">
							部门：
						</td>
						<td width="13%" align="left">
							${archiveTemp2.organization}
						</td>
						<td width="7%" align="left">
							责任人：
						</td>
						<td width="10%" align="left">
							${archiveTemp2.inuser}
						</td>
						<td width="32%" colspan="3" align="left">
							制单日期：${fn:substring(archiveTemp2.outtimestr, 0, 10)}
						</td>
					</tr>
						<tr>
						<td width="6%" height="25" align="left">
							出库接收人：
						</td>
						<td width="30%" align="left">
							<s:if test="archiveTemp2.outusername==null||archiveTemp2.outusername==''">
							       无指定
							</s:if><s:else>
							 ${archiveTemp2.outusername}
							</s:else>
						</td>
						
					</tr>
				</table>
				<table width="620" cellpadding="0" cellspacing="0" class="table">
					<tr>
						<th height="24" align="center" bgcolor="#E4F1FA">
							档案编号
						</th>
						<th align="center" bgcolor="#E4F1FA">
							档案名称
						</th>
						<th align="center">
							档案类别
						</th>
						<th align="center">
							条码
						</th>
						<th align="center">
							芯片号
						</th>
						<th align="center">
							保密等级
						</th>

						<th align="center">
							入库人
						</th>
						<th align="center">
							入库时间
						</th>
						<th align="center">
							存放位置
						</th>
					</tr>
					<%
						int number = 1;
					%>
					<s:iterator value="archivelist">
						<tr>
							<td>
								<span id="archiveCode">${archiveCode}</span>
							</td>
							<td>
								<span id="name">${name}</span>
							</td>
							<td>
								<span id="catalogue">${catalogue}</span>
							</td>
							<td>
								<span id="barcode">${barcode}</span>
							</td>
							<td>
								<span id="chipid">${chipid}</span>
							</td>
							<td>

								<span id="securitylevel">${securitylevel}</span>

							</td>
							<td>
								<span id="inuser">${inuser}</span>
							</td>
							<td>
								<span id="intime">${fn:substring(intime,0,19)}</span>
							</td>
							<td>

								<span id="location">${location}</span>
							</td>

						</tr>
						<%
							number++;
						%>
					</s:iterator>
					<%
						if(number<5){
					%>
					<tr class="blank">
						<td align="center"></td>
						<td align="center"></td>
						<td align="center">
							&nbsp;
						</td>
						<td align="center">
							&nbsp;
						</td>
						<td align="center">
							&nbsp;
						</td>
						<td align="center">
							&nbsp;
						</td>
						<td align="center"></td>
						<td align="center">
							&nbsp;
						</td>
						<td align="center"></td>
					</tr>
					<tr  class="blank">
						<td align="center"></td>
						<td align="center"></td>
						<td align="center">
							&nbsp;
						</td>
						<td align="center">
							&nbsp;
						</td>
						<td align="center">
							&nbsp;
						</td>
						<td align="center">
							&nbsp;
						</td>
						<td align="center"></td>
						<td align="center">
							&nbsp;
						</td>
						<td align="center"></td>
					</tr>
					<tr  class="blank"> 
						<td align="center"></td>
						<td align="center"></td>
						<td align="center">
							&nbsp;
						</td>
						<td align="center">
							&nbsp;
						</td>
						<td align="center">
							&nbsp;
						</td>
						<td align="center">
							&nbsp;
						</td>
						<td align="center"></td>
						<td align="center">
							&nbsp;
						</td>
						<td align="center"></td>
					</tr>
					<tr  class="blank">
						<td  align="center">
							&nbsp;
						</td>
						<td align="center">
							&nbsp;
						</td>
						<td align="center">
							&nbsp;
						</td>
						<td align="center">
							&nbsp;
						</td>
						<td align="center">
							&nbsp;
						</td>
						<td align="center">
							&nbsp;
						</td>
						<td align="center">
							&nbsp;
						</td>
						<td align="center">
							&nbsp;
						</td>
						<td align="center">
							&nbsp;
						</td>
					</tr>
					<%
						}
					%>
				</table>
			</form>
		</div>
		<br />
		<br />
		<br />
		<br />
	</body>
</html>