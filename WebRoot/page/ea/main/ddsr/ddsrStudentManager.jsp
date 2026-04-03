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
        <script src="<%=basePath%>js/ea/ddsr/ddsrStudentManager.js"></script>
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
                                <input type="checkbox" name="chbox" class="JQuerypersonvalue" value="${arr[0]}"/>	
								<span id="studKey" style="display:none">${arr[0]}</span>
								<span id="staffKey" style="display:none">${arr[0]}</span>
								<span id="photo" style="display:none">${arr[12]}</span>
								<span id="staffID" style="display:none">${arr[21]}</span>
								<span id="subjKey" style="display:none">${arr[22]}</span>
								<span id="studCompanyid" style="display:none">${arr[23]}</span>																
								<span id="usedNmae" style="display:none">${arr[2]}</span>	
								<span id="staffAddress" style="display:none">${arr[8]}</span>
								<span id="staffDesc" style="display:none">${arr[20]}</span>
								<span id="stud_key2" style="display:none">${arr[24]}</span>	
								<span id="status" style="display:none">${arr[25]}</span>	
																					
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
                <c:param name="actionPath" value="ea/ddsrstudentManager/ea_doDdsrStudentManagerAction.jspa?innerAction=showDdsrStudentList&pageNumber=${pageNumber}&search=turnpage">
                </c:param>
            </c:import>            
        </div> 
 	 	<div class="contentbannb jqmWindow " style="top: 10%;width: 720px;right: 30%" id="jqModel">
            <form method="post" enctype="multipart/form-data" name="cstaffForm" id="cstaffForm">
                <input type="submit" name="submit" style="display:none"/>
                <div class="drag">编辑
                  <div class="close">
                  </div>
                </div>  				
  				<table cellpadding="0px" cellspacing="0px" name="stafftable" id="stafftable" width="710" style="margin-left: 5px;" height="103"> 
				    <tr>
				    	<td height="25" align="right" width="100">姓名：</td>
				    	<td width="200"><input name="staff.staffName" id="staffName" class="put3"/></td>
						<td width="100" rowspan="6" align="right">图片：</td>
				    	<td width="200" rowspan="6"><img name="photos" id="photo" src="" width="102" height="126" alt="照片" onload="setImg(this,102,126)"/>&nbsp;
				    	  <label></label></td>	
				    </tr>
				     <tr>
				    	<td height="25" align="right" width="100">曾用名：</td>
				    	<td width="200"><input name="staff.usedNmae" id="usedNmae" class="put3"/>&nbsp;</td>
					</tr>
					 <tr>
				    	<td height="25" align="right" width="100">性别：</td>
				    	<td width="200"><select id="sexSelect" name="staff.sex">
								<option value="男">男</option>
								<option value="女">女</option>
							</select></td>
					</tr>
					 <tr>
				    	<td height="25" align="right" width="100">IC卡编号：</td>
				    	<td width="200"><input name="student.studIcnumber" id="studIcnumber" class="put3 positiveNum ckTextLength" maxlength="20"/></td>
					</tr>
					 <tr>
				    	<td height="25" align="right" width="100">身份证号：</td>
				    	<td width="200"><input name="staff.staffIdentityCard" id="staffIdentityCard" class="put3"/></td>
					</tr>
					 <tr>
				    	<td height="25" align="right" width="100">出生日期：</td>
				    	<td width="200"><input type="text" name="staff.birthday" id="birthday" 
								onfocus="WdatePicker({lang:'zh-cn', dateFmt:'yyyy-MM-dd', onpicked:function(){;}})"
								readonly size="24" /></td>
					</tr>
					 <tr>
				    	<td height="25" align="right" width="100">当前科目：</td>
				    	<td width="200">
						<s:select list="%{#request.subjectList}" id="subjKeySelect" name="subject.subjKey" listKey="subjKey" listValue="subjContent"  theme="simple"></s:select>
						</td>
						<td height="25" align="right" width="100">联系电话：</td>
						<td width="200"><input name="staff.reference" id="reference" class="put3 positiveNum"/></td>
				    </tr>
					<tr>
				    	<td height="25" align="right" width="100">地址：</td>
				    	<td width="200"><input name="staff.staffAddress" id="staffAddress" class="put3"/></td>
						<td height="25" align="right" width="100">籍贯：</td>
						<td width="200"><input name="staff.nativePlace" id="nativePlace" class="put3"/></td>
					</tr>
					<tr>
				    	<td height="25" align="right" width="100">学员状态：</td>
				    	<td width="200"><select name="student.studStatus" id="studStatusSelect">
							<option value="10">正常</option>
							<option value="20">退学</option>
							<option value="30">结业</option>
							<option value="40">停用</option>
						</select></td>
						<td height="25" align="right" width="100">信息类别：</td>
						<td width="200"><select name="student.studInforclass" id="studInforclassSelect">
							<option value="10">确定人员信息</option>
							<option value="20">不确定人员信息</option>
						</select></td>
					</tr>
					<tr>
				    	<td height="25" align="right" width="100">学时总数 ：</td>
				    	<td width="200"><input name="student.studSumhour" id="studSumhour" class="put3 positiveNum ckTextLength" maxlength="6" /></td>
						<td height="25" align="right" width="100">星级学员：</td>
						<td width="200"><select name="student.studStar" id="studStarSelect">
							<option value="10">是</option>
							<option value="20">否</option>
						</select></td>
					</tr>
					<tr>
				    	<td height="25" align="right" width="100">证照类型：</td>
				    	<td width="200"><select name="student.studCredentials" id="studCredentialsSelect">
							<option value="C1">C1</option>
							<option value="C2">C2</option>
							<option value="C3">C3</option>
							<option value="C4">C4</option>
							<option value="C5">C5</option>
							<option value="B1">B1</option>
							<option value="B2">B2</option>
							<option value="A1">A1</option>
							<option value="A2">A2</option>
							<option value="A3">A3</option>
							<option value="D">D</option>
							<option value="E">E</option>
							<option value="F">F</option>
						</select></td>
						<td height="25" align="right" width="100">开课期数：</td>
						<td width="200"><input name="student.studNper" id="studNper" class="put3"/></td>
					</tr>
					<tr>
				    	<td height="25" align="right" width="100">剩余学时：</td>
				    	<td width="200"><input name="student.studRemhour" id="studRemhour" class="put3 positiveNum  ckTextLength" maxlength="6" /></td>
						<td height="25" align="right" width="100">补时次数：</td>
						<td width="200"><input name="student.studStonumber" id="studStonumber" class="put3 positiveNum  ckTextLength" maxlength="6" /></td>
					</tr>
					<tr>
						<td height="25" align="right">备注：</td>
				    	<td colspan="3"><label>
				    	  <textarea name="staff.staffDesc" id="staffDesc" rows="5" cols="65"></textarea>
				    	</label></td>
					</tr>
				    <tr>
				    	<td colspan="4" align="center" >				            							
						    <input type="button"  class="input-button JQuerySubmit" style="cursor:pointer;width:80px;" value="提交" />
						    <input type="button"  class="input-button JQueryreturn" style="cursor:pointer;width:80px;" value="取消" />													
							
							<input type="hidden" name="stud_key2" id="stud_key2" />
							<input type="hidden" name="student.studKey" id="studKey" />
							<input type="hidden" name="student.studCompanyid" id="studCompanyid" />							
							<input type="hidden" name="staff.staffKey" id="staffKey" />
							<input type="hidden" name="staff.staffID" id="staffID" />
						</td>
				    </tr>
			  </table>
				   <s:token></s:token> 
		  </form>
	</div>
	<div class="contentbannb jqmWindow " style="top: 10%;width: 720px;right: 30%" id="jqModelShow">
          <div class="drag">查看
            <div class="close">
            </div>
          </div>  				
  			<table cellpadding="0px" cellspacing="0px" name="showstafftable" id="showstafftable" width="710" style="margin-left: 5px;" height="103"> 
			<tr>
				    	<td height="25" align="right" width="100">姓名：</td>
				    	<td width="200"><input name="show_staff.staffName" id="show_staffName" class="put3" disabled="disabled"/></td>
						<td width="100" rowspan="6" align="right">图片：</td>
				    	<td width="200" rowspan="6"><img name="photos" id="photo" src="" width="102" height="126" alt="照片" onload="setImg(this,102,126)"/>&nbsp;</td>	
				    </tr>
				     <tr>
				    	<td height="25" align="right" width="100">曾用名：</td>
				    	<td width="200"><input name="show_staff.usedNmae" id="show_usedNmae" class="put3" disabled="disabled"/>&nbsp;</td>
					</tr>
					 <tr>
				    	<td height="25" align="right" width="100">性别：</td>
				    	<td width="200"><select id="show_sexSelect" name="show_staff.sex" disabled="disabled">
								<option value="男">男</option>
								<option value="女">女</option>
							</select></td>
					</tr>
					 <tr>
				    	<td height="25" align="right" width="100">IC卡编号：</td>
				    	<td width="200"><input name="show_student.studIcnumber" id="show_studIcnumber" class="put3" disabled="disabled"/></td>
					</tr>
					 <tr>
				    	<td height="25" align="right" width="100">身份证号：</td>
				    	<td width="200"><input name="show_staff.staffIdentityCard" id="show_staffIdentityCard" class="put3" disabled="disabled"/></td>
					</tr>
					 <tr>
				    	<td height="25" align="right" width="100">出生日期：</td>
				    	<td width="200"><input type="text" name="show_staff.birthday" id="show_birthday" 
								onfocus="WdatePicker({lang:'zh-cn', dateFmt:'yyyy-MM-dd', onpicked:function(){;}})"
								readonly size="24"  disabled="disabled"/></td>
					</tr>
					 <tr>
				    	<td height="25" align="right" width="100">当前科目：</td>
				    	<td width="200">
						<s:select list="%{#request.subjectList}" id="show_subjKeySelect" name="show_subject.subjKey" listKey="subjKey" listValue="subjContent"  theme="simple" disabled="disabled"></s:select>
						</td>
						<td height="25" align="right" width="100">联系电话：</td>
						<td width="200"><input name="show_staff.reference" id="show_reference" class="put3" disabled="disabled"/></td>
				    </tr>
					<tr>
				    	<td height="25" align="right" width="100">地址：</td>
				    	<td width="200"><input name="show_staff.staffAddress" id="show_staffAddress" class="put3" disabled="disabled"/></td>
						<td height="25" align="right" width="100">籍贯：</td>
						<td width="200"><input name="show_staff.nativePlace" id="show_nativePlace" class="put3" disabled="disabled"/></td>
					</tr>
					<tr>
				    	<td height="25" align="right" width="100">学员状态：</td>
				    	<td width="200"><select name="show_student.studStatus" id="show_studStatusSelect" disabled="disabled">
							<option value="10">正常</option>
							<option value="20">退学</option>
							<option value="30">结业</option>
							<option value="40">停用</option>
						</select></td>
						<td height="25" align="right" width="100">信息类别：</td>
						<td width="200"><select name="show_student.studInforclass" id="show_studInforclassSelect" disabled="disabled">
							<option value="10">确定人员信息</option>
							<option value="20">不确定人员信息</option>
						</select></td>
					</tr>
					<tr>
				    	<td height="25" align="right" width="100">学时总数 ：</td>
				    	<td width="200"><input name="show_student.studSumhour" id="show_studSumhour" class="put3" disabled="disabled"/></td>
						<td height="25" align="right" width="100">星级学员：</td>
						<td width="200"><select name="show_student.studStar" id="show_studStarSelect" disabled="disabled">
							<option value="10">是</option>
							<option value="20">否</option>
						</select></td>
					</tr>
					<tr>
				    	<td height="25" align="right" width="100">证照类型：</td>
				    	<td width="200"><select name="show_student.studCredentials" id="show_studCredentialsSelect" disabled="disabled">
							<option value="C1">C1</option>
							<option value="C2">C2</option>
							<option value="C3">C3</option>
							<option value="C4">C4</option>
							<option value="C5">C5</option>
							<option value="B1">B1</option>
							<option value="B2">B2</option>
							<option value="A1">A1</option>
							<option value="A2">A2</option>
							<option value="A3">A3</option>
							<option value="D">D</option>
							<option value="E">E</option>
							<option value="F">F</option>
						</select></td>
						<td height="25" align="right" width="100">开课期数：</td>
						<td width="200"><input name="show_student.studNper" id="show_studNper" class="put3" disabled="disabled"/></td>
					</tr>
					<tr>
				    	<td height="25" align="right" width="100">剩余学时：</td>
				    	<td width="200"><input name="show_student.studRemhour" id="show_studRemhour" class="put3" disabled="disabled"/></td>
						<td height="25" align="right" width="100">补时次数：</td>
						<td width="200"><input name="show_student.studStonumber" id="show_studStonumber" class="put3" disabled="disabled"/></td>
					</tr>
					<tr>
						<td height="25" align="right">备注：</td>
				    	<td colspan="3"><label>
				    	  <textarea name="show_staff.staffDesc" id="show_staffDesc" rows="5" cols="65" disabled="disabled"></textarea>
				    	</label></td>
					</tr>				    	    				   
			</table>	  
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
				    	<td height="25" align="right" width="100">学员状态：</td>
				    	<td width="200"><select name="searchZhuangtai" id="searchZhuangtai">
						  <option value="0">请选择</option>
                          <option value="10">正常</option>
                          <option value="20">退学</option>
                          <option value="30">结业</option>
                          <option value="40">停用</option>
                        </select></td>
					 </tr>
					 <tr>
				    	<td height="25" align="right" width="100">当前科目：</td>
				    	<td width="200"><s:select list="%{#request.subjectList}" id="searchDangQian" name="searchDangQian" listKey="subjKey" listValue="subjContent" headerKey="0" headerValue="请选择" theme="simple"></s:select></td>
					 </tr>
					 <tr>
				    	<td height="25" align="right" width="100">证照类型：</td>
				    	<td width="200"><select name="searchZhengtype" id="searchZhengtype">
						  <option value="">请选择</option>
                          <option value="C1">C1</option>
                          <option value="C2">C2</option>
                          <option value="C3">C3</option>
                          <option value="C4">C4</option>
                          <option value="C5">C5</option>
                          <option value="B1">B1</option>
                          <option value="B2">B2</option>
                          <option value="A1">A1</option>
                          <option value="A2">A2</option>
                          <option value="A3">A3</option>
                          <option value="D">D</option>
                          <option value="E">E</option>
                          <option value="F">F</option>
                        </select>						</td>
						
				    </tr>
					<tr>
				    	<td height="25" align="right" width="100">星级学员：</td>
				    	<td width="200"><select name="searchStar" id="searchStar">
						  <option value="0">请选择</option>
                          <option value="10">是</option>
                          <option value="20">否</option>
                        </select></td>				    	
					</tr>
				    <tr>
				    	<td colspan="2" align="center" >				            							
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
<script type="text/javascript">
	function   setImg(img,   width,   height){ 
		var   s1   =   width/height; 
		var   s2   =   img.offsetWidth/img.offsetHeight; 
		if(s1> s2)   img.height   =   img.offsetHeight> height   ?   height   :   img.offsetHeight; 
		else     img.width   =   img.offsetWidth> width   ?   width   :   img.offsetWidth; 
	}
</script>
</html>