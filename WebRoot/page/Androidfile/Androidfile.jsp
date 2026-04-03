<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
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

<title>My JSP 'Androidfile.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<script src="<%=basePath%>js/jquery.js" type="text/javascript">
	
</script>
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript">
	function ti() {
		alert($("#fiule").val());
	}
	function mark(obj){
			var str="<input></input>"
		if(obj.options[obj.selectedIndex].value=='1'){
			$("#inner").css("display","block")
		} else {
			$("#inner").css("display","none")
		} 
	}

	
</script>

</head>
<body>
</body>

<body>
	<form id="form1" enctype="multipart/form-data"
		action="<%=basePath%>ea/buyproducts/ea_addIOSandroid.jspa"
		method="post">
		<div style="display: none;">
			<input type="text" name="filepahe" />
		</div>
		<div>
			<table>
				<tr>
					<td>添加上传文件</td>
				</tr>
				<tr>
					<td><span>上传人名字</span><input type="text" name="userfile" id="name" />
					</td>
				</tr>

				<tr>
					<td><span>APP版本号</span><input type="text" name="banbenhao" id="banben" />
					</td>
				</tr>
				<tr>
					<td><span>APP名字</span><input type="text" name="company" id="company" />
					</td>
				</tr>
				<tr>
					<td><span>APP标识</span><input type="text" name="applogo" id="applogo" />
					</td>
				</tr>
				<tr>
					<td><input type="file" name="file" id="fiule" /></td>
				</tr>
				<Tr>
					<td><select type="select" name="andrORios" id="type" onchange="mark(this)">
							<option value="0">安卓</option>
							<option value="1" id="show" >IOS</option>
					</select>
					</td>
				</Tr>
				
				
				<tr ID="inner" style="display:none;">
					<td>
						 <textarea style="resize:none;" id="iosFilename" name="iosFilename"></textarea> -->
						
					</td>
				</tr>
				
				<tr>
					<td><input type="submit" value="上传" onclick="ti()" />
					</td>
				</tr>

			</table>

		</div>
	</form>
</body>
</html>
