<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="hy.ea.bo.Company"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	Company c = (Company)session.getAttribute("currentcompany");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>团队管理</title>
		<script src="<%=basePath%>js/jquery-1.3.1.js" type="text/javascript"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/csc2.css" />
		<script type="text/JavaScript">
		var basePath = '<%=basePath%>';
/** ********************************团队管理*********************************** */
	var comy = "<%=request.getParameter("comy")%>";
	
	var treeID = '<%=session.getAttribute("organizationID")%>';
	var treeName = '<%=session.getAttribute("organizationName")%>';
	if(comy == "comy"){
		treeID = '<%=c.getCompanyID()%>';
		treeName = '<%=c.getCompanyName()%>';
	}
	
	var treePID = '<%=c.getCompanyID()%>';
	var treePName = '<%=c.getCompanyName()%>';
	var url = basePath + "ea/responsibilities/sajax_n_ea_getoList.jspa?date="
			+ new Date().toLocaleString();
	$.ajax({
				url : encodeURI(url),
				type : "get",
				async : true,
				dataType : "json",
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var nologin = member.nologin;
					if (nologin) {
						document.location.href = basePath
								+ "page/ea/not_login.jsp";
					}
					var oList = member.organizationlist;
					var data = new Array();
					data[0] = {
						id : treeID,
						pid : '-1',
						text : treeName,
						str : '00'
					};
					for (var i = 0; i < oList.length; i++) {
						data[i + 1] = {
							id : oList[i].organizationID,
							pid : oList[i].organizationPID,
							text : oList[i].organizationName,
							str : oList[i].status
							
						};
					}
					var result="";
					var onlys = new Array();//子菜单只有一个子菜单
					var onlyi = 0;
					var lasts = new Array();
					var lastsi = 0;
					result = "<li id='"+treeID+"'><div class='root section '><a id='ai'>"+treeName+"</a></div><div class='va'></div>";
					
					function parentMenu(compareID,vals) {//1级
						var temp = 0 ;
						var u ="";
						
						for (var i = 0; i < vals.length; i++) {
							if( vals[i].pid == compareID && vals[i].str == "00" ){
								if(temp==0){
									result+="<ul><li id='"+vals[i].id+"' ><div class='first section'><a>"+vals[i].text+"</a></div>";
									temp++;
								}else{
									result+="</li><li id='"+vals[i].id+"' ><div id='d"+vals[i].id+"'class='section'><a>"+vals[i].text+"</a></div>";
									u = vals[i].id;
								}
								childMenu(vals[i].id,vals);
								
							}
							if(i==vals.length-1){
								result+="</li></ul></li>";	
								lasts[lastsi] = u;
								lastsi++;			
							}
						}
							
						$("#map").append(result);
						
						
						//子菜单下单独子菜单配置
						for(var i = 0 ;i <onlys.length;i++){
							$("#ul"+onlys[i]).addClass(" sav ");
						}
						//子级最后一个加添加class
						for(var i = 0 ;i <lasts.length;i++){
							$("#"+lasts[i]).find("#d"+lasts[i]).addClass(" last");
						}
						
					}
					function childMenu(compareID,vals){//2级
					  	var temp = 0 ;
					  	var u ="" ; //最后一个子菜单的中间量
					  	var n = 0;
						for (var j = 0; j < vals.length; j++) {
							if( vals[j].pid == compareID && vals[j].str == "00"){
								if(temp==0){
								    result+="<div class='va'id='div"+vals[j].id+"'></div>";
									result+="<ul id = 'ul"+vals[j].id+"'>";
									result+="<li id='"+vals[j].id+"'><div class='first section'><a>"+vals[j].text+"</a></div>";
									temp++;
									u = vals[j].id;
								}else{
									result+="</li><li id='"+vals[j].id+"'><div id='d"+vals[j].id+"' class='section'><a>"+vals[j].text+"</a></div>";
									u = vals[j].id;
									
								}
								
								n++;
								childMenu(vals[j].id,vals);
								
							}
							if(j==(vals.length-1) && temp != 0){
								result+="</li></ul>";
								if(n>1){
									lasts[lastsi] = u;
									lastsi++;
								}
								if(n==1){
								
								}
							}
						}
						
						if(n == 1){
							onlys[onlyi] = u;
							onlyi++;
						}
						
						
					}
					parentMenu(treeID,data);
				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}
			});

</script>
	</head>
	<body>
		<%--制作下拉框  style="overflow: scroll;height:580px;"--%>
		<div id="contain">
			<ul id='map' style='width:5000px' class='solo'>
			</ul>
		</div>

	</body>
</html>