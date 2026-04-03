<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="hy.ea.bo.Company"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	Company c = (Company) session.getAttribute("currentcompany");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>机构图word管理</title>
<script src="<%=basePath%>js/jquery-1.3.1.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath%>js/common/common_word.js"></script>
<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
<script type="text/JavaScript">
	var basePath = '<%=basePath%>';
	var comy = "<%=request.getParameter("comy")%>";
	
	var treeID = '<%=session.getAttribute("organizationID")%>';
	var treeName = '<%=session.getAttribute("organizationName")%>';
	if(comy == "comy"){
		treeID = '<%=c.getCompanyID()%>';
		treeName = '<%=c.getCompanyName()%>';
	}
	
	var treePID = '<%=c.getCompanyID()%>';
	var treePName = '<%=c.getCompanyName()%>';
</script>
</head>
<body>
	<div style='width:500 ;'>
		<input type="button" class=" input-button add" id="add" style="cursor: pointer; width: 80px; display: none;" value="添加"></input> 
		<input type="button" class=" input-button edit" id="edit" style="cursor: pointer; width: 80px; display: none;" value="修改"></input> 
		<input type="button" class=" input-button del" id="del" style="cursor: pointer; width: 80px; display: none;" value="删除"></input> 
		<input type="text" style="display: none;" id="orgcharUrl" />
		<input type="text" style="display: none;"id="orgcharallid" />
	</div>
	<p></p>
	<%--制作下拉框  style="overflow: scroll;height:580px;"--%>
<iframe src="" name="main" scrolling="no" style="width:100%;height:600px;"
					marginwidth="0" marginheight="0" frameborder="0"
					id="mainframe" border="0" framespacing="0" noresize="noResize"
					vspale="0"> </iframe>
<script type="text/javascript">
	$(document).ready(function() {
		getOrgcharAll();
	});
	$(".add").click(function() {
		var urlReturn = OpenWord("", 2);
		var url =basePath+"ea/orgcharall/sajax_ea_save.jspa?orgcharall.orgcharUrl=" 
		 			+ urlReturn+"&orgcharall.organizationID="+treeID+"&data="+new Date();
		 $.ajax({
				url: encodeURI(url),
				type: "get",
				async: true,
				dataType: "json",
				success: function cbf(data){
						var member = eval("(" + data + ")");
						var savee = member.savee;
	                  	getOrgcharAll();
				},
				error: function cbf(data){
				alert("数据获取失败！");
				}
		});
	});
	$(".del").click(function() {
		var urlR = $.trim($("#orgcharUrl").attr("value"));
		var urlID = $.trim($("#orgcharallid").attr("value"));
		var url = basePath + "ea/orgcharall/sajax_ea_deletee.jspa?orgcharall.orgcharallid=" 
					+ urlID +"&data="+new Date();
		 $.ajax({
				url: encodeURI(url),
				type: "get",
				async: true,
				dataType: "json",
				success: function cbf(data){
						var member = eval("(" + data + ")");
						var deletee = member.deletee;
						
						$("input#orgcharUrl").attr("value","");
						$("input#orgcharallid").attr("value","");
	                  	getOrgcharAll();
				},
				error: function cbf(data){
				alert("数据获取失败！");
				}
		});
	});
	$(".edit").click(function() {
		var urlR = $.trim($("#orgcharUrl").attr("value"));
		var urlReturn = OpenWord(urlR, 2);
		getOrgcharAll();
	});
function getOrgcharAll(){
	var url = basePath + "ea/orgcharall/sajax_ea_getOrgcharAll.jspa?orgcharall.companyID=" 
		 			+ treePID+"&orgcharall.organizationID="+treeID+"&data="+new Date();
	$.ajax({
			url : encodeURI(url),
			type: "get",
			async: true,
			dataType: "json",
			success: function cbf(data){
				var member = eval("(" + data + ")");
				var orgcharall = member.orgcharall;
				if(orgcharall == "isnull"){
					$("#add").css("display","");
					$("#edit").css("display","none");
					$("#del").css("display","none");
					$("#mainframe").attr("src","");
				}else{
				
					$("#add").css("display","none");
					$("#edit").css("display","");
					$("#del").css("display","");
					$("input#orgcharUrl").attr("value",orgcharall.orgcharUrl);
					$("input#orgcharallid").attr("value",orgcharall.orgcharallid);
					
					OpenWord(orgcharall.orgcharUrl,"1");			
				}

			},
			error: function cbf(data){
				alert("数据保存失败！");
			}
	});
}
function ViewOffice(docPath, fileType) {
	$("#mainframe").attr("src",basePath
		+ "page/ea/common/weonlyreadprint.jsp?docPath="
		+ docPath + "&fileType=" + fileType
		+ "&WorkMode=2");
}

</script>

</body>
</html>