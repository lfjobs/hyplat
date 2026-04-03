<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort() + path + "/"; 
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="cache-control" content="no-cache"/>
<title>学员信息管理</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/admin_main.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/overlayer.css"/>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>        
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>        
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>        
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script src="<%=basePath%>js/ea/ddsr/ddsrStudentManager_list.js"></script>
<script type="text/javascript">
var  basePath='<%=basePath%>'; 
var  pNumber =${pageNumber};  
var  search='${search}';                  
var  stud_key = '';
var  token=0;
</script>     
</head>
<body >
        <div class="main_main">
            <table class="JQueryflexme">
                <thead>
                    <tr class="tablewith">
                        <th width="40" align="center">
                            请选择
                        </th>                       
                        <th width="80" align="center">姓名</th>
						<th width="40" align="center">性别</th>
						<th width="80" align="center">IC卡编号</th>
						<th width="120" align="center">身份证号</th>
						<th width="80" align="center">出生日期</th>
						<th width="80" align="center">当前科目</th>
						<th width="80" align="center">学员状态</th>
						<th width="80" align="center">联系电话</th>
						<th width="80" align="center">籍贯</th>
						<th width="80" align="center">信息类别</th>
						<th width="80" align="center">星级</th>
						<th width="80" align="center">学时总数</th>
						<th width="80" align="center">开课期数</th>
						<th width="80" align="center">证照类型</th>
						<th width="80" align="center">剩余学时</th>
						<th width="80" align="center">补时次数</th>                         
                    </tr>
                </thead>
                <tbody> 
                    <c:forEach var="arr" items="${pageForm.list}">
                        <tr id="${arr[0]}">
                            <td>
                                <input type="radio" name="chek" class="JQuerypersonvalue" value="${arr[0]}"/>	
								<span id="studKey" style="display:none">${arr[0]}</span>
								<span id="staffKey" style="display:none">${arr[0]}</span>
								<span id="staffID" style="display:none">${arr[21]}</span>
								<span id="subjKey" style="display:none">${arr[22]}</span>
								<span id="studCompanyid" style="display:none">${arr[23]}</span>																
								<span id="usedNmae" style="display:none">${arr[2]}</span>	
								<span id="staffAddress" style="display:none">${arr[8]}</span>
								<span id="staffDesc" style="display:none">${arr[20]}</span>
								<span id="stud_key2" style="display:none">${arr[24]}</span>		
																					
                            </td>                           
                            <td>
                                <span id="staffName">${arr[1]}</span>
                            </td>
                             <td>
                                <span id="sex">${arr[3]}</span>
                            </td>
							<td>
                                <span id="studIcnumber">${arr[4]}</span>
                            </td>
                             <td>
                                <span id="staffIdentityCard">${arr[5]}</span>
                            </td>
							<td>
                                <span id="birthday">${arr[6]}</span>
                            </td>
                             <td>
                               <span id="subjType" style="display:none">${arr[7]}</span>
								<span>
									<c:choose>
										<c:when test="${arr[7]=='10'}">科目一</c:when>
										<c:when test="${arr[7]=='20'}">科目二</c:when>
										<c:when test="${arr[7]=='30'}">科目三</c:when>
										<c:when test="${arr[7]=='40'}">科目四</c:when>
										<c:otherwise>&nbsp;</c:otherwise>
									</c:choose>
								</span>
                            </td>
							<td>
                                <span id="studStatus" style="display:none">${arr[9]}</span>
								<span>
									<c:choose>
										<c:when test="${arr[9]=='10'}">正常</c:when>
										<c:when test="${arr[9]=='20'}">退学</c:when>
										<c:when test="${arr[9]=='30'}">结业</c:when>
										<c:when test="${arr[9]=='40'}">停用</c:when>
										<c:otherwise>&nbsp;</c:otherwise>
									</c:choose>
								</span>
                            </td>
                             <td>
                                <span id="reference">${arr[13]}</span>
                            </td>
							<td>
                                <span id="nativePlace">${arr[14]}</span>
                            </td>
                             <td>
                                <span id="studInforclass" style="display:none">${arr[15]}</span>
								<span>
									<c:choose>
										<c:when test="${arr[15]=='10'}">确定人员信息</c:when>
										<c:when test="${arr[15]=='20'}">不确定人员信息</c:when>										
										<c:otherwise>&nbsp;</c:otherwise>
									</c:choose>
								</span>
                            </td>
							<td>
                                 <span id="studStar" style="display:none">${arr[16]}</span>
								<span>
									<c:choose>
										<c:when test="${arr[16]=='10'}">是</c:when>
										<c:when test="${arr[16]=='20'}">否</c:when>										
										<c:otherwise>&nbsp;</c:otherwise>
									</c:choose>
								</span>
                            </td>
                             <td>
                                <span id="studSumhour">${arr[18]}</span>
                            </td>
							<td>
                                <span id="studNper">${arr[17]}</span>
                            </td>
                             <td>
                                <span id="studCredentials">${arr[10]}</span>
                            </td>
							<td>
                                <span id="studRemhour">${arr[11]}</span>
                            </td>
                             <td>
                                <span id="studStonumber">${arr[19]}</span>
                            </td>
                        </tr> 
                    </c:forEach>
                </tbody>
            </table>
			<c:import url="../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/coachreservationrecordcontent/ref_doDdsrStudentManagerAction.jspa?innerAction=showDdsrStudentList&pageNumber=${pageNumber}&search=turnpage">
                </c:param>
            </c:import>            
        </div> 
	<div class="contentbannb jqmWindow " style="top: 10%;width: 360px;right: 30%" id="jqModelSearch">
            <form method="post" name="searchForm" id="searchForm">
                <input type="submit" name="submit" style="display:none"/>
                <div class="drag">查询
                  <div class="close">
                  </div>
                </div>  				
  				<table cellpadding="0px" cellspacing="0px" name="searchstafftable" id="searchstafftable" width="350" style="margin-left: 5px;" height="103"> 
				    <tr>
				    	<td height="25" align="right" width="100">姓名：</td>
				    	<td width="200"><input name="searchName" id="searchName" class="put3"/></td>
						
				    </tr>
				     <tr>
				    	<td height="25" align="right" width="100">IC卡编号：</td>
			    	   <td width="200"><input name="searchIcNumber" id="searchIcNumber" class="put3"/></td>
					</tr>
					 <tr>
				    	<td height="25" align="right" width="100">籍贯：</td>
				    	<td width="200"><input name="searchJiguan" id="searchJiguan" class="put3"/></td>
					 </tr>
					 <tr>
				    	<td height="25" align="right" width="100">开课期数：</td>
				    	<td width="200"><input name="searchQishu" id="searchQishu" class="put3"/></td>
					 </tr>
				    <tr>
				    	<td colspan="2" align="center" >
				    		<input type="hidden" name="searchZhuangtai" id="searchZhuangtai" value="${searchZhuangtai}" />
				    		<input type="hidden" name="searchDangQian" id="searchDangQian" value="${searchDangQian}" />
				    		<input type="hidden" name="searchStar" id="searchStar" value="${searchStar}" />
				    		<input type="hidden" name="searchZhengtype" id="searchZhengtype" value="${searchZhengtype}" />				            							
						    <input type="button"  class="input-button JQuerySearchSubmit" style="cursor:pointer;width:80px;" value="查询" />
						    <input type="button"  class="input-button JQuerySearchReturn" style="cursor:pointer;width:80px;" value="取消" />	
							<input type="hidden" name="search" id="search" value="search" />																			
						</td>
				    </tr>
			  </table>				   
		  </form>
	</div> 
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe> 
    </body>
</html>