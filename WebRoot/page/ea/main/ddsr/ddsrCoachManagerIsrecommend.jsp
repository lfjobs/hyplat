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
        <title>教练信息管理</title>
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
        <script src="<%=basePath%>js/ea/ddsr/ddsrCoachManagerIsrecommend.js"></script>
        <script type="text/javascript">
		 var  basePath='<%=basePath%>'; 
		 var  pNumber =${pageNumber};  
         var  search='${search}';                  
         var  coacKey = '';
		 var  subjKey = '';
         var  token=0;
         var  companyGroupLogo='${companyGroupLogo}';
		</script>     
		
	</head>
	<body >
        <div class="main_main">
            <table class="JQueryflexme">
                <thead>
                    <tr class="tablewith">
                        <th width="40" align="center">请选择</th>                       
                        <th width="80" align="center">姓名</th>						
						<th width="120" align="center">教练员证号</th>
						<th width="80" align="center">车辆牌照</th>
						<th width="80" align="center">等级</th>
						<th width="80" align="center">是否星级</th>
						<th width="80" align="center">准教类型</th>
						<th width="80" align="center">教授科目</th>
						<th width="80" align="center">驾龄</th>
						<th width="80" align="center">教龄</th>
						<th width="80" align="center">IC卡编号</th>
						<th width="80" align="center">联系电话</th>
						<th width="80" align="center">满意度</th> 
						<th width="80" align="center">公司推荐</th>
						<th width="80" align="center">集团推荐</th>                        
                    </tr>
                </thead>
                <tbody> 
                    <c:forEach var="arr" items="${pageForm.list}">
                        <tr id="${arr[0]}">
                            <td>
                                <input type="checkbox" name="chbox" class="JQuerypersonvalue" value="${arr[0]}"/>								
								<span id="staffKey" style="display:none">${arr[0]}</span>
								<span id="staffAddress" style="display:none">${arr[6]}</span>
								<span id="photo" style="display:none">${arr[7]}</span>
								<span id="staffDesc" style="display:none">${arr[10]}</span>
								<span id="coacReleasedate" style="display:none">${arr[12]}</span>
								<span id="coacCertificateDate" style="display:none">${arr[28]}</span>
								<span id="coacStatus" style="display:none">${arr[16]}</span>
								<span id="staffIdentityCard" style="display:none">${arr[19]}</span>																				
								<span id="coacKey2" style="display:none">${arr[20]}</span>
								<span id="coacCompanyid" style="display:none">${arr[21]}</span>	
								<span id="subjKey" style="display:none">${arr[22]}</span>		
								<span id="status" style="display:none">${arr[24]}</span>
								<span id="staffID" style="display:none">${arr[25]}</span>																			
                            </td>                           
                            <td><span id="staffName">${arr[1]}</span></td>
                            <td><span id="coacPapersCode">${arr[11]}</span></td>
							<td><span id="coacCarNumber">${arr[18]}</span></td>
							<td><span id="coacLevel">${arr[14]}</span></td>
							<td><span id="coacStar" style="display:none">${arr[17]}</span>
								<span>
									<c:choose>
										<c:when test="${arr[17]=='10'}">是</c:when>
										<c:when test="${arr[17]=='20'}">否</c:when>										
										<c:otherwise>&nbsp;</c:otherwise>
									</c:choose>
								</span>
							</td>
							<td><span id="coacTeachtype">${arr[15]}</span></td>
							<td><span id="subjType">${arr[5]}</span></td>
							<td><span id="jiaLing">${arr[23]}年</span></td>
							<td><span id="jiaoLing">${arr[27]}年</span></td>
							<td><span id="coacIcnumber">${arr[13]}</span></td>
							<td><span id="reference">${arr[8]}</span></td>
							<td><span id="coacSatisfaction"></span></td> 
							<td><span id="coacIsrecommend">${arr[26]=='1'?"是":"否"}</span></td>
							<td><span id="coacIsrecommendS">${arr[29]=='1'?"是":"否"}</span></td>  
                        </tr> 
                    </c:forEach>
                </tbody>
            </table>
			<c:import url="../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/ddsrcoachManager/isrecommend_ea_doDdsrCoachManagerAction.jspa?innerAction=showDdsrCoachList&pageNumber=${pageNumber}&search=turnpage">
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
						<td width="100" rowspan="5" align="right">图片：</td>
				    	<td width="200" rowspan="5"><img name="photos" id="photo" src="" width="102" height="126" alt="照片" onload="setImg(this,102,126)"/>&nbsp;						
				    	  <label>				    	  
			    	    </label></td>	
				    </tr>
				     <tr>
				    	<td height="25" align="right" width="100">教练证号：</td>
				    	<td width="200"><input name="coach.coacPapersCode" id="coacPapersCode" class="put3"/>&nbsp;</td>
					</tr>
					 <tr>
				    	<td height="25" align="right" width="100">发证日期：</td>
				    	<td width="200"><input type="text" name="coach.coacReleasedate" id="coacReleasedate" 
								onfocus="WdatePicker({lang:'zh-cn', dateFmt:'yyyy-MM-dd', onpicked:function(){;}})"
								readonly="readonly" class="put3" /></td>
					 </tr>					 
					 <tr>
					   <td height="25" align="right" width="100">IC卡编号：</td>
					   <td width="200"><input name="coach.coacIcnumber" id="coacIcnumber" class="put3"/></td>
			    	</tr>
					 <tr>
					   <td height="25" align="right" width="100">身份证号：</td>
					   <td width="200"><input name="staff.staffIdentityCard" id="staffIdentityCard" class="put3"/>&nbsp;</td>
			    	</tr>
					 <tr>
					   <td height="25" align="right" width="100">联系电话：</td>
					   <td width="200"><input type="text" name="staff.reference" id="reference" class="put3" /></td>
					   <td height="25" align="right" width="100">等级：</td>
						<td width="200"><input name="coach.coacLevel" id="coacLevel" class="put3"/></td>
				    </tr>
					<tr>
					  <td height="25" align="right" width="100">教授科目：</td>
					  <td width="200">
                        <input name="subject.subjType" id="subjType" class="put3" readonly=""/><input type="hidden" name="subject.subjKey" id="subjKey" class="put3"/>
					    <a href="#" id="hrefSelect">选择</a> </td>
				    	<td height="25" align="right" width="100">准教类型：</td>
						<td width="200"><select name="coach.coacTeachtype" id="coacTeachtypeSelect">
                          <option value="0">请选择</option>
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
					</tr>
					<tr>
				    	<td height="25" align="right" width="100">教练状态：</td>
				    	<td width="200"><select name="coach.coacStatus" id="coacStatusSelect">
                          <option value="0">请选择</option>
                          <option value="10">正常</option>
                          <option value="20">开会</option>
                          <option value="30">请假</option>
                          <option value="40">修车</option>
                          <option value="50">账号停用</option>
                        </select></td>
						<td height="25" align="right" width="100">地址：</td>
						<td width="200"><input name="staff.staffAddress" id="staffAddress" class="put3"/></td>
					</tr>
					<tr>
				    	<td height="25" align="right" width="100">星级教练 ：</td>
				    	<td width="200"><select name="coach.coacStar" id="coacStarSelect">
                          <option value="10">是</option>
                          <option value="20">否</option>
                        </select></td>
				    	<td height="25" align="right" width="100">车辆牌照：</td>
						<td width="200"><input name="coach.coacCarNumber" id="coacCarNumber" class="put3"/>&nbsp;</td>
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
							<input type="hidden" name="coacKey2" id="coacKey2" />
							<input type="hidden" name="staff.staffKey" id="staffKey" />
							<input type="hidden" name="coach.coacCompanyid" id="coacCompanyid" />						</td>
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
  				<table cellpadding="0px" cellspacing="0px" name="showstafftable" id="showstafftable" width="710" style="margin-left: 5px;" height="285"> 
				    <tr>
				      <td height="25" align="right" width="100">姓名：</td>
				      <td width="200"><input name="show_staff.staffName" id="show_staffName" class="put3" disabled="disabled"/></td>
				      <td width="100" rowspan="5" align="right">图片：</td>
				    	<td width="200" rowspan="5"><img name="photos" id="photo" src="" width="102" height="126" alt="照片" onload="setImg(this,102,126)"/>&nbsp;
				    	  <label></label></td>	
				    </tr>
				     <tr>
				       <td height="25" align="right" width="100">教练证号：</td>
				       <td width="200"><input name="show_coach.coacPapersCode" id="show_coacPapersCode" class="put3" disabled="disabled"/>
  &nbsp;</td>
			        </tr>
					 <tr>
					   <td height="25" align="right" width="100">发证日期：</td>
					   <td width="200"><input type="text" name="show_coach.coacReleasedate" id="show_coacReleasedate" 
								onfocus="WdatePicker({lang:'zh-cn', dateFmt:'yyyy-MM-dd', onpicked:function(){;}})"
								readonly="readonly" class="put3"  disabled="disabled"/></td>
				     </tr>
					 <tr>
					   <td height="25" align="right" width="100">IC卡编号：</td>
					   <td width="200"><input name="show_coach.coacIcnumber" id="show_coacIcnumber" class="put3"  disabled="disabled"/></td>
				    </tr>
					 <tr>
					   <td height="25" align="right" width="100">身份证号：</td>
					   <td width="200"><input name="show_staff.staffIdentityCard" id="show_staffIdentityCard" class="put3" disabled="disabled"/></td>
				    </tr>					 
					 <tr>
					   <td height="25" align="right" width="100">联系电话：</td>
					   <td width="200"><input type="text" name="show_staff.reference" id="show_reference" class="put3"  disabled="disabled"/></td>
				    	<td height="25" align="right" width="100">等级：</td>
						<td width="200"><input name="show_coach.coacLevel" id="show_coacLevel" class="put3" disabled="disabled"/></td>
				    </tr>
					<tr>
					  <td height="25" align="right" width="100">教授科目：</td>
					  <td width="200"><input name="show_subject.subjType" id="show_subjType" class="put3" readonly="" disabled="disabled"/></td>
				    	<td height="25" align="right" width="100">准教类型：</td>
						<td width="200"><select name="show_coach.coacTeachtype" id="show_coacTeachtypeSelect" disabled="disabled">
                          <option value="0">请选择</option>
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
					</tr>
					<tr>
				    	<td height="25" align="right" width="100">教练状态：</td>
				    	<td width="200"><select name="show_coach.coacStatus" id="show_coacStatusSelect" disabled="disabled">
                          <option value="0">请选择</option>
                          <option value="10">正常</option>
                          <option value="20">开会</option>
                          <option value="30">请假</option>
                          <option value="40">修车</option>
                          <option value="50">账号停用</option>
                        </select></td>
						<td height="25" align="right">地址：</td>
						<td><input name="show_staff.staffAddress" id="show_staffAddress" class="put3" disabled="disabled"/></td>
					</tr>
					<tr>
				    	<td height="25" align="right" width="100">星级教练 ：</td>
				    	<td width="200"><select name="show_coach.coacStar" id="show_coacStarSelect" disabled="disabled">
                          <option value="10">是</option>
                          <option value="20">否</option>
                        </select></td>
				    	<td height="25" align="right">车辆牌照：</td>
				    	<td><input name="show_coach.coacCarNumber" id="show_coacCarNumber" class="put3" disabled="disabled"/>
				    	  &nbsp;</td>
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
				    	<td height="25" align="right" width="100">车辆牌照：</td>
				    	<td width="200"><input name="searchCarNumber" id="searchCarNumber" class="put3"/></td>
					 </tr>
					 <tr>
				    	<td height="25" align="right" width="100">驾龄：</td>
				    	<td width="200"><select name="searchJialing" id="searchJialing">
                          <option value="0">请选择</option>
                          <option value="10">5年以下</option>
                          <option value="20">5-10年</option>
                          <option value="30">10-20年</option>
                          <option value="40">20年以上</option>
                        </select></td>
					 </tr>
					 <tr>
				    	<td height="25" align="right" width="100">状态：</td>
				    	<td width="200"><select name="searchZhuangtai" id="searchZhuangtai">
                          <option value="0">请选择</option>
                          <option value="10">正常</option>
                          <option value="20">开会</option>
                          <option value="30">请假</option>
                          <option value="40">修车</option>
                          <option value="50">账号停用</option>
                        </select></td>
					 </tr>
					 <tr>
				    	<td height="25" align="right" width="100">教授科目：</td>
				    	<td width="200"><s:select list="%{#request.subjectList}" id="searchTeachSubj" name="searchTeachSubj" listKey="subjKey" listValue="subjContent" headerKey="0" headerValue="请选择" theme="simple"></s:select></td>
					 </tr>
					 <tr>
				    	<td height="25" align="right" width="100">准教类型：</td>
				    	<td width="200"><select name="searchTeachType" id="searchTeachType">
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
				    	<td height="25" align="right" width="100">是否星级：</td>
				    	<td width="200"><select name="searchStar" id="searchStar">
						  <option value="0">请选择</option>
                          <option value="10">是</option>
                          <option value="20">否</option>
                        </select></td>				    	
					</tr>
				    <tr>
				    	<td colspan="2" align="center" >
				    		<input type="hidden" name="companyGroupLogo" id="companyGroupLogo" value="${companyGroupLogo}" />
				    		<input type="hidden" name="searchIsrecommend" id="searchIsrecommend" value="${searchIsrecommend}" />	
						    <input type="hidden" name="searchIsrecommendS" id="searchIsrecommendS" value="${searchIsrecommendS}" />
						    <input type="button"  class="input-button JQuerySearchSubmit" style="cursor:pointer;width:80px;" value="查询" />
						    <input type="button"  class="input-button JQuerySearchReturn" style="cursor:pointer;width:80px;" value="取消" />	
							<input type="hidden" name="search" id="search" value="search" />						</td>
				    </tr>
			  </table>				   
		  </form>
	</div>
	<div class="contentbannb jqmWindow " style="top: 10%;width: 300px;right: 30%" id="jqModelSelect">
		<table class="no_JQueryflexme" name="no_JQueryflexme">
                <thead>
                    <tr class="tablewith">
                        <th width="40" align="center">
                            请选择
                        </th>                       
                        <th width="120" align="center">科目名称</th>
                        <th width="120" align="center">科目内容</th>                                              
                    </tr>
                </thead>
                <tbody> 
                    <s:iterator value="subjectList" >
                        <tr id="${subjKey}">
                            <td>
                                <input type="checkbox" name="chboxselect"  class="JQuerypersonvalue2" value="${subjKey}"/>
								<span id="subjKey" style="display:none">${subjKey}</span>
                                <span id="subjCompanyid" style="display:none">${subjCompanyid}</span>
								<span id="subjKeyVal" style="display:none">${subjType}</span>
                            </td>                           
                            <td>
                                <span id="subjType2">
									<c:choose>
										<c:when test="${subjType=='10'}">科目一</c:when>
										<c:when test="${subjType=='20'}">科目二</c:when>
										<c:when test="${subjType=='30'}">科目三</c:when>
										<c:otherwise>科目四</c:otherwise>
									</c:choose>
								</span>
                            </td>
                            <td>
                               <span id="subjContent">${subjContent}</span>
                            </td>                           
                        </tr> 
                    </s:iterator>
					<tr>
				    	<td colspan="3" align="center" >				            							
						    <input type="button"  class="input-button JQuerySelectSubmit" style="cursor:pointer;width:50px;" value="确定" />
						    <input type="button"  class="input-button JQuerySelectReturn" style="cursor:pointer;width:50px;" value="取消" />	
				    </tr>
                </tbody>
      </table>          
	</div>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe> 
	<select name="select2">
    </select>
	<div class="contentbannb jqmWindow " style="top: 10%;width: 460px;right: 30%" id="jqModelSetWorkTime">
            <form method="post" name="workTimeForm" id="workTimeForm">
                <input type="submit" name="submit" style="display:none"/>
                <div class="drag">设置工作时段
                  <div class="close">
                  </div>
                </div>  				
  				<table cellpadding="0px" cellspacing="0px" name="setWorkTimeTable" id="setWorkTimeTable" width="450" style="margin-left: 5px;" height="100%"> 
				    <tr>
				    	<td height="25" colspan="2" align="center"><label>
				    	  <input type="radio" name="ddsrWorkTime.wotiType" id="wotiType" value="20" checked="checked"/>
				    	  教练自定义上班时间
				    	</label>
				    	  <label>
			    	    <input type="radio" name="ddsrWorkTime.wotiType" id="wotiType" value="10" />
			    	    本校常规上班时间				    	  </label></td>
			    	</tr>
				     <tr>
				    	<td height="25" colspan="2" align="center">起日期：
				    	  <input type="text" name="ddsrWorkTime.wotiStrdaydate" id="wotiStrdaydate" 
								onfocus="WdatePicker({lang:'zh-cn', dateFmt:'yyyy-MM-dd', maxDate:'%y-%M-%d', onpicked:function(){wotiEnddaydate.focus();}})"
								readonly="readonly" style="width:80px" class="put3"/>
				    	  止日期：
			    	   <input type="text" name="ddsrWorkTime.wotiEnddaydate" id="wotiEnddaydate" 
								onfocus="WdatePicker({lang:'zh-cn', dateFmt:'yyyy-MM-dd', minDate:'%y-%M-%d', onpicked:function(){;}})"
								readonly="readonly" style="width:80px" class="put3"/></td>
		    	     </tr>
					 <tr>
				    	<td height="25" align="right" width="140">早间：</td>
				    	<td width="310">
				    	  <select name="zjWotiStrdateHour" id="zjWotiStrdateHour">
						    <option value="06">06</option>
						    <option value="07">07</option>						  
			    	      </select>
				    	  ：
				    	  <select name="zjWotiStrdateTime" id="zjWotiStrdateTime">
						    <option value="00">00</option>
						    <option value="30">30</option>
			    	      </select>
				    	
				    	~
				    	<select name="zjWotiEnddateHour" id="zjWotiEnddateHour">
						   <option value="07">07</option>
						   <option value="08">08</option>
			    	    </select>
				    	  ：
				    	 <select name="zjWotiEnddateTime" id="zjWotiEnddateTime">
						  <option value="00">00</option>
						  <option value="30">30</option>
			    	    </select>				    	</td>
					 </tr>
					 <tr>
                       <td height="25" align="right">上午：</td>
					   <td><select name="swWotiStrdateHour" id="swWotiStrdateHour">
    					      <option value="08">08</option>
	    					  <option value="09">09</option>
							  <option value="10">10</option>
							  <option value="11">11</option>
                         </select>
					     ：
					     <select name="swWotiStrdateTime" id="swWotiStrdateTime">
						  <option value="00">00</option>
						  <option value="30">30</option>
				         </select>
					     ~
					     <select name="swWotiEnddateHour" id="swWotiEnddateHour">
    						  <option value="09">09</option>
							  <option value="10">10</option>
							  <option value="11">11</option>
							  <option value="12">12</option>
				         </select>
					     ：
					     <select name="swWotiEnddateTime" id="swWotiEnddateTime">
						  <option value="00">00</option>
						  <option value="30">30</option>
				         </select>                       </td>
			      </tr>
					 <tr>
                       <td height="25" align="right">下午：</td>
					   <td><select name="xwWotiStrdateHour" id="xwWotiStrdateHour">
  					        <option value="13">13</option>
							<option value="14">14</option>
							<option value="15">15</option>
							<option value="16">16</option>
                         </select>
					     ：
					     <select name="xwWotiStrdateTime" id="xwWotiStrdateTime">
						  <option value="00">00</option>
						  <option value="30">30</option>
				         </select>
					     ~
					     <select name="xwWotiEnddateHour" id="xwWotiEnddateHour">
						    <option value="14">14</option>
							<option value="15">15</option>
							<option value="16">16</option>
							<option value="17">17</option>
				         </select>
					     ：
					     <select name="xwWotiEnddateTime" id="xwWotiEnddateTime">
						  <option value="00">00</option>
						  <option value="30">30</option>
				         </select>                       </td>
			      </tr>
					 <tr>
                       <td height="25" align="right">晚间：</td>
					   <td><select name="wjWotiStrdateHour" id="wjWotiStrdateHour">
					          <option value="17">17</option>
							  <option value="18">18</option>
							  <option value="19">19</option>
							  <option value="20">20</option>
                         </select>
					     ：
					     <select name="wjWotiStrdateTime" id="wjWotiStrdateTime">
						  <option value="00">00</option>
						  <option value="30">30</option>
				         </select>
					     ~
					     <select name="wjWotiEnddateHour" id="wjWotiEnddateHour">
						      <option value="18">18</option>
							  <option value="19">19</option>
							  <option value="20">20</option>
							  <option value="21">21</option>
				         </select>
					     ：
					     <select name="wjWotiEnddateTime" id="wjWotiEnddateTime">
						  <option value="00">00</option>
						  <option value="30">30</option>
				         </select>                       </td>
			      </tr>
				    <tr>
				    	<td colspan="2" align="center" >				            							
						    <input type="button"  class="input-button JQuerySetWorkTimeSubmit" style="cursor:pointer;width:80px;" value="确认" />
						    <input type="button"  class="input-button JQuerySetWorkTimeSubmit2" style="cursor:pointer;width:180px;" value="设为本校常规上班时间" />														
						</td>
				    </tr>
			  </table>				   
		  </form>
	</div>
	<form name="Staffform" id="Staffform" method="post">
		<div id="bankJqm" class="jqmWindow"
			style="width: 95%; height: 400px; absolute; display: none; left: 1%; top: 1%; background: #eff; overflow-x: hidden; overflow-y: auto;">
			
			<div style="background: #efg; margin-right: 500px;">
				<input style="display: none;" id="checkopertionID" />
				<input style="display: none;" id="checkopertionName" />
				<input style="display: none;" id="childopertionName" />
				<input style="display: none;" id="checkform" />
			</div>
			<iframe name="daoRu" id="daoRu" width="100%" height="360px"
				frameborder="0" style="overflow-x: hidden; overflow-y: auto;"></iframe>
			<div align="center">
				<input type="button" class="input-button" id="DaoRuFanqd"
					value=" 确定 " style="cursor: hand; border: 0;" />
				<input type="button" class="input-button" id="DaoRuFan" value=" 关闭 "
					style="cursor: hand; border: 0;" />
			</div>
			
		</div>
	</form>
<script type="text/javascript">
function   setImg(img,   width,   height){ 
    var   s1   =   width/height; 
    var   s2   =   img.offsetWidth/img.offsetHeight; 
    if(s1> s2)   img.height   =   img.offsetHeight> height   ?   height   :   img.offsetHeight; 
    else     img.width   =   img.offsetWidth> width   ?   width   :   img.offsetWidth; 
}
</script>
    </body>
</html>