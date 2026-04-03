<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="cache-control" content="no-cache" />
		<title>导入界面列表</title>
		<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
		%>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript">
		</script>
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js">
		</script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
		</script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js">
		</script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript"
			src="<%=basePath%>js/ea/human/office/SersonnelSystem/payresearch_list.js"></script>
		<script type="text/javascript">
		alert("为防止导入失败！ 一次导入最多1000条!如果数据多请分几个Excle完成导入")
		var token=0;
				function aaa(){
					var count = document.getElementById("count").value;
					var obj=document.getElementById("a");
					for (i = 0; i < count; i++){                        
						var val=obj.options[i].value; 
						var j=0;
						$("table").find("input:gt(0)."+(i+1)).each(function (){
							$(this).attr("name","baseMap["+(j+1)+"]."+val);
							j++;
						});
					}
					$("#myform").attr("target","hidden").attr("action","<%=basePath%>${excelImport.importPath}")
					document.forms[0].submit();
					token=3;
				}
			</script>
	</head>
	<body style="background: #eff;">
		<form action="" method="post" name="myform" id="myform">
		<input type="button" onclick="javascript:aaa();" value="提交"></input>
			<input type="hidden" name="count" id="count" value="${excelImport.dataCount }" />
			<table border="1" cellpadding="0" cellspacing="0"
				style="border-color: black">
				<select name="a" id="a" style="display: none">
					<s:iterator value="excelImport.propers" id="str">
						<s:iterator value="str" id="s">
							<option value="<s:property  value="s"/>">
								<s:property value="s" />
							</option>
						</s:iterator>
					</s:iterator>
				</select>

				<c:forEach var="str" items="${excelImport.lists}">
					<tr>
						<c:forEach var="entity" items="${str}" varStatus="i" begin="1" end="${excelImport.dataCount}">
							<td>
								<input class="${i.index }" name="${i.index }" value="${entity }" />
							</td>
						</c:forEach>
					</tr>
				</c:forEach>
			</table>
		</form>
		<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
	</body>
</html>
