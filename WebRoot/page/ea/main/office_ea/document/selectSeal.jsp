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

		<title>选择印章</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<script language=javascript>
		var basePath='<%=basePath%>';
  function CheckValue(theForm)
{
  var mObject = window.dialogArguments;
  mObject.SelectValue=theForm.WordList.value;
  window.close();
  return;
}
</script>
		<script type="text/javascript">
    $(document).ready(function() {
	var url = basePath+"ea/enterprisestamp/sajax_n_ea_queryStampFileInfo.jspa?type=stamp";
		$.ajax({
			url : encodeURI(url),
			type : "get",
			async : true,
			dataType : "json",
			success : function cbf(data) {
				var jsonresult = eval("(" + data + ")");
				var list = jsonresult.result;
				if(list.length!=0){
				var str = "";
				 $(list).each(function(i, obj) {
			    
				str += "<option value='"+obj.enterpriseStampID + obj.scanningAccessories + "'>" + obj.stampName + "</option>";
			
			    $("#selectbox").html(str);
			   })
				
			  }
			},
			error : function cbf(data) {
				alert("数据获取失败！")
			}
			});


})

 

</script>
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
									请选择您要加盖的电子印章
								</td>
							</tr>
							<tr>
								<td align=right nowrap class="TDStyle"></td>
								<td>
									<select name=WordList id="selectbox" style='width: 240 px;'>
										

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

