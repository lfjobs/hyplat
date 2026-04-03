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
         <meta http-equiv="pragma" content="no-cache">
 		<meta http-equiv="expires" content="0">  
        <title>科目信息管理</title>
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
        <script src="<%=basePath%>js/ea/ddsr/subjectManager.js"></script>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>/js/jquery-easyui-1.4/themes/default/easyui.css">
	    <link rel="stylesheet" type="text/css" href="<%=basePath%>/js/jquery-easyui-1.4/themes/icon.css">
	    <script type="text/javascript" src="<%=basePath%>/js/jquery-easyui-1.4/jquery.easyui.min.js"></script>
	    <style type="text/css">
			table#tableOfContent td{
				text-align: center;padding: 0px
			}
			table#tableOfContent tr#data input[id],table#tableOfContent tr#data select{
				display: none;
			}
			table#tableOfContent input,select{
				border-left:0px;border-top:0px;border-right:0px;border-bottom:0px; border-bottom-color:Black;width:100%;
			}
		</style>
        <script type="text/javascript">
		 var  basePath='<%=basePath%>'; 
		 var  pNumber =${pageNumber};  
         var  search='${search}';                  
         var  subjKey = '';
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
                        <th width="100" align="center">科目名称</th>
                         <th width="100" align="center">科目内容</th>
                         <th width="280" align="center">备注</th>                         
                    </tr>
                </thead>
                <tbody> 
                    <s:iterator value="pageForm.list" status="number">
                        <tr id="${subjKey}"  >
                            <td>
                                <input type="radio" name="a"  class="JQuerypersonvalue" value="${subjKey}"/>
								<span id="subjKey" style="display:none">${subjKey}</span>
                                <span id="subjCompanyid" style="display:none">${subjCompanyid}</span>
								<span id="subjKeyVal" style="display:none">${subjType}</span>
                            </td>                           
                            <td>
                                <span id="subjType">
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
                            <td>
                                <span id="subjNote">${subjNote}</span>
                            </td>
                        </tr> 
                    </s:iterator>
                </tbody>
            </table>
			<c:import url="../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/subjectManager/ea_doSubjectManagerAction.jspa?innerAction=showSubjectList&pageNumber=${pageNumber}&search=${search}">
                </c:param>
            </c:import>            
        </div> 
 	 	<div class="contentbannb jqmWindow " style="top: 10%;width: 500px;right: 30%" id="jqModel">
            <form name="cstaffForm" id="cstaffForm" method="post">
                <input type="submit" name="submit" style="display:none"/>
                <div class="drag">
					编辑
                  <div class="close">
                    </div>
                </div>
  				<table cellpadding="0px" cellspacing="0px" name="stafftable" id="stafftable" width="418" style="margin-left: 70px;" height="103"> 
				    <tr>
				    	<td width="100" height="37" align="right">科目名称：</td>
				    	<td><select name="dssrSubject.subjType" id="subjTypeSelect">
								<option value="10">科目一</option>
								<option value="20">科目二</option>
								<option value="30">科目三</option>
								<option value="40">科目四</option>
						</select></td>
				    </tr>
				    <tr>
				    	<td width="100" height="37" align="right">科目内容：</td>
				    	<td><input name="dssrSubject.subjContent" id="subjContent" class="put3"/></td>				    	
				    </tr>
					<tr>
						<td width="100" height="37" align="right">备注：</td>
				    	<td>
				    	  <textarea name="dssrSubject.subjNote" id="subjNote" rows="8" cols="25"></textarea>
				    	</td>
					</tr>
				    <tr>
				    	<td colspan="2" align="center" >
				    		<input type="hidden" name="dssrSubject.subjKey" id="subjKey" />
				            <input type="hidden" name="dssrSubject.subjCompanyid" id="subjCompanyid" />							
						    <input type="button"  class="input-button JQuerySubmit" style="cursor:pointer;width:80px;" value="提交" />
						    <input type="button"  class="input-button JQueryreturn" style="cursor:pointer;width:80px;" value="取消" /></td>
				    </tr>
				  </table>
				   <s:token></s:token> 
			</form>
	</div>
	
	<div class="jqmWindow " style="top: 10%;width: 700px;right: 20%;height: 440px;" id="jqModelOfSyllabus">
            <form name="syllabusForm" id="syllabusForm" method="post">
                <input type="submit" name="submit" style="display:none"/>
                <div class="drag">
					设置教学项目学时
                  <div class="close">
                    </div>
                </div>
                <div style="width: 700px;height: 410px;overflow: scroll;">
		 				<table cellpadding="0px" border="1" bordercolor="white"   cellspacing="0px" name="stafftable" id="tableOfSyllabus" width="660" style="margin-left: 10px"> 
					    <tr>
					    	<td colspan="3" align="center">
					    	 <input type="button"  class="input-button JQuerySubmitOfSyllabus" style="cursor:pointer;width:80px;" value="保存" />
					    	</td>
					    </tr>
					    <tr>
					    	<td width="100" align="right"> 选择驾照类型：</td>
					    	<td colspan="2"> <select id="type" name="ddsrsyllabus.type">
					    		<option value="C1">C1</option>
					    		<option value="C2">C2</option>
					    		<option value="C3">C3</option>
					    		<option value="C4">C4</option>
					    		<option value="C5">C5</option>
					    		<option value="D">D</option>
					    		<option value="E">E</option>
					    		<option value="F">F</option>
					    		<option value="B1">B1</option>
					    		<option value="B2">B2</option>
					    		<option value="A1">A1</option>
					    		<option value="A2">A2</option>
					    		<option value="A3">A3</option>
					    	</select> 
					    	</td>
					    </tr>
					    <tr>
					    	<td align="right">科一:</td><td>教学项目</td><td>学时</td>
					    </tr>
				    	<tr>
				    	<td align="right"><input type="checkbox" /><input type="hidden" name="ddsrsyllabusMap[0].id" id="id"/><input type="hidden" id="key" name="ddsrsyllabusMap[0].key"/></td><td width="200">1,机械常识<input type="hidden" name="ddsrsyllabusMap[0].program" id="program" value="机械常识"/><input type="hidden" name="ddsrsyllabusMap[0].subject" id="subject" value="科目一"/><input type="hidden" name="ddsrsyllabusMap[0].programType" id="programType" value="理论知识"/>  </td><td><input size="3" name="ddsrsyllabusMap[0].time" id="time"   /> <input type="hidden" name="ddsrsyllabusMap[0].serial" id="serial" value="101"/></td>
				    	</tr>
				    	<tr >
				    	<td align="right"><input type="checkbox"/><input type="hidden" name="ddsrsyllabusMap[1].id" id="id"/><input type="hidden" id="key" name="ddsrsyllabusMap[1].key"/></td><td>2,法律法规<input type="hidden" name="ddsrsyllabusMap[1].program" id="program" value="法律法规"/><input type="hidden" name="ddsrsyllabusMap[1].subject" id="subject" value="科目一"/><input type="hidden" name="ddsrsyllabusMap[1].programType" id="programType" value="理论知识"/> </td><td><input size="3" name="ddsrsyllabusMap[1].time" id="time"   /> <input type="hidden" name="ddsrsyllabusMap[1].serial" id="serial" value="102"/></td>
				    	</tr>
					    <tr >
					    <td align="right"><input type="checkbox"/><input type="hidden" name="ddsrsyllabusMap[2].id" id="id"/><input type="hidden" id="key" name="ddsrsyllabusMap[2].key"/></td><td>3,安全文明<input type="hidden" name="ddsrsyllabusMap[2].program" id="program" value="安全文明"/><input type="hidden" name="ddsrsyllabusMap[2].subject" id="subject" value="科目一"/><input type="hidden" name="ddsrsyllabusMap[2].programType" id="programType" value="理论知识"/> </td><td><input size="3" name="ddsrsyllabusMap[2].time" id="time"   /><input type="hidden" name="ddsrsyllabusMap[2].serial" id="serial" value="103"/></td>
					    </tr>
					    <tr>
					    	<td align="right">科二: </td><td>教学项目</td><td>学时</td>
					    </tr>
				    	<tr >
				    	<td align="right"><input type="checkbox"/><input type="hidden" name="ddsrsyllabusMap[3].id" id="id"/><input type="hidden" id="key" name="ddsrsyllabusMap[3].key"/></td><td>1,倒车入库<input type="hidden" name="ddsrsyllabusMap[3].program" id="program" value="倒车入库"/><input type="hidden" name="ddsrsyllabusMap[3].subject" id="subject" value="科目二"/><input type="hidden" name="ddsrsyllabusMap[3].programType" id="programType" value="实际操作"/>  </td><td><input size="3" name="ddsrsyllabusMap[3].time" id="time"   /> <input type="hidden" name="ddsrsyllabusMap[3].serial" id="serial" value="201"/></td>
				    	</tr>
				    	<tr >
				    	<td align="right"><input type="checkbox"/><input type="hidden" name="ddsrsyllabusMap[4].id" id="id"/><input type="hidden" id="key" name="ddsrsyllabusMap[4].key"/></td><td>2,侧方移位<input type="hidden" name="ddsrsyllabusMap[4].program" id="program" value="侧方移位"/><input type="hidden" name="ddsrsyllabusMap[4].subject" id="subject" value="科目二"/><input type="hidden" name="ddsrsyllabusMap[4].programType" id="programType" value="实际操作"/></td><td><input size="3" name="ddsrsyllabusMap[4].time" id="time"   /> <input type="hidden" name="ddsrsyllabusMap[4].serial" id="serial" value="202"/></td>
				    	</tr>
					    <tr >
					    <td align="right"><input type="checkbox"/><input type="hidden" name="ddsrsyllabusMap[5].id" id="id"/><input type="hidden" id="key" name="ddsrsyllabusMap[5].key"/></td><td>3,单边桥<input type="hidden" name="ddsrsyllabusMap[5].program" id="program" value="单边桥"/><input type="hidden" name="ddsrsyllabusMap[5].subject" id="subject" value="科目二"/><input type="hidden" name="ddsrsyllabusMap[5].programType" id="programType" value="实际操作"/></td><td><input size="3" name="ddsrsyllabusMap[5].time" id="time"   /><input type="hidden" name="ddsrsyllabusMap[5].serial" id="serial" value="203"/></td>
					    </tr>
					    <tr >
					    <td align="right"><input type="checkbox"/><input type="hidden" name="ddsrsyllabusMap[6].id" id="id"/><input type="hidden" id="key" name="ddsrsyllabusMap[6].key"/></td><td>4,直角转弯<input type="hidden" name="ddsrsyllabusMap[6].program" id="program" value="直角转弯"/><input type="hidden" name="ddsrsyllabusMap[6].subject" id="subject" value="科目二"/><input type="hidden" name="ddsrsyllabusMap[6].programType" id="programType" value="实际操作"/></td><td><input size="3" name="ddsrsyllabusMap[6].time" id="time"   /><input type="hidden" name="ddsrsyllabusMap[6].serial" id="serial" value="204"/></td>
					    </tr>
					    <tr >
					    <td align="right"><input type="checkbox"/><input type="hidden" name="ddsrsyllabusMap[7].id" id="id"/><input type="hidden" id="key" name="ddsrsyllabusMap[7].key"/></td><td>5,S路<input type="hidden" name="ddsrsyllabusMap[7].program" id="program" value="S路"/><input type="hidden" name="ddsrsyllabusMap[7].subject" id="subject" value="科目二"/><input type="hidden" name="ddsrsyllabusMap[7].programType" id="programType" value="实际操作"/></td><td><input size="3" name="ddsrsyllabusMap[7].time" id="time"   /><input type="hidden" name="ddsrsyllabusMap[7].serial" id="serial" value="205"/></td>
					    </tr>
					    <tr >
					    <td align="right"><input type="checkbox"/><input type="hidden" name="ddsrsyllabusMap[8].id" id="id"/><input type="hidden" id="key" name="ddsrsyllabusMap[8].key"/></td><td>6,坡道起步<input type="hidden" name="ddsrsyllabusMap[8].program" id="program" value="坡道起步"/><input type="hidden" name="ddsrsyllabusMap[8].subject" id="subject" value="科目二"/><input type="hidden" name="ddsrsyllabusMap[8].programType" id="programType" value="实际操作"/></td><td><input size="3" name="ddsrsyllabusMap[8].time" id="time"    /><input type="hidden" name="ddsrsyllabusMap[8].serial" id="serial" value="206"/></td>
					    </tr>
					    <tr>
					    	<td align="right">科三:</td><td>教学项目</td><td>学时</td>
					    </tr>
				    	<tr >
				    	<td align="right"><input type="checkbox"/><input type="hidden" name="ddsrsyllabusMap[9].id" id="id"/><input type="hidden" id="key" name="ddsrsyllabusMap[9].key"/></td><td>1,加减挡<input type="hidden" name="ddsrsyllabusMap[9].program" id="program" value="加减挡"/><input type="hidden" name="ddsrsyllabusMap[9].subject" id="subject" value="科目三"/> <input type="hidden" name="ddsrsyllabusMap[9].programType" id="programType" value="实际操作"/></td><td><input size="3" name="ddsrsyllabusMap[9].time" id="time"   /> <input type="hidden" name="ddsrsyllabusMap[9].serial" id="serial" value="301"/></td>
				    	</tr>
				    	<tr >
				    	<td align="right"><input type="checkbox"/><input type="hidden" name="ddsrsyllabusMap[10].id" id="id"/><input type="hidden" id="key" name="ddsrsyllabusMap[10].key"/></td><td>2,靠边停车<input type="hidden" name="ddsrsyllabusMap[10].program" id="program" value="靠边停车"/><input type="hidden" name="ddsrsyllabusMap[10].subject" id="subject" value="科目三"/><input type="hidden" name="ddsrsyllabusMap[10].programType" id="programType" value="实际操作"/></td><td><input size="3" name="ddsrsyllabusMap[10].time" id="time"   /> <input type="hidden" name="ddsrsyllabusMap[10].serial" id="serial" value="302"/></td>
				    	</tr>
					    <tr >
					    <td align="right"><input type="checkbox"/><input type="hidden" name="ddsrsyllabusMap[11].id" id="id"/><input type="hidden" id="key" name="ddsrsyllabusMap[11].key"/></td><td>3,过红绿灯<input type="hidden" name="ddsrsyllabusMap[11].program" id="program" value="过红绿灯"/><input type="hidden" name="ddsrsyllabusMap[11].subject" id="subject" value="科目三"/><input type="hidden" name="ddsrsyllabusMap[11].programType" id="programType" value="实际操作"/></td><td><input size="3" name="ddsrsyllabusMap[11].time" id="time"   /><input type="hidden" name="ddsrsyllabusMap[11].serial" id="serial" value="303"/></td>
					    </tr>
					    <tr >
					    <td align="right"><input type="checkbox"/><input type="hidden" name="ddsrsyllabusMap[12].id" id="id"/><input type="hidden" id="key" name="ddsrsyllabusMap[12].key"/></td><td>4,超车<input type="hidden" name="ddsrsyllabusMap[12].program" id="program" value="超车"/><input type="hidden" name="ddsrsyllabusMap[12].subject" id="subject" value="科目三"/><input type="hidden" name="ddsrsyllabusMap[12].programType" id="programType" value="实际操作"/></td><td><input size="3" name="ddsrsyllabusMap[12].time" id="time"   /><input type="hidden" name="ddsrsyllabusMap[12].serial" id="serial" value="304"/></td>
					    </tr>
					    <tr >
					    <td align="right"><input type="checkbox"/><input type="hidden" name="ddsrsyllabusMap[13].id" id="id"/><input type="hidden" id="key" name="ddsrsyllabusMap[13].key"/></td><td>5,灯光使用<input type="hidden" name="ddsrsyllabusMap[13].program" id="program" value="灯光使用"/><input type="hidden" name="ddsrsyllabusMap[13].subject" id="subject" value="科目三"/><input type="hidden" name="ddsrsyllabusMap[13].programType" id="programType" value="实际操作"/></td><td><input size="3" name="ddsrsyllabusMap[13].time" id="time"   /><input type="hidden" name="ddsrsyllabusMap[13].serial" id="serial" value="305"/></td>
					    </tr>
					    <tr >
					    <td align="right"><input type="checkbox"/><input type="hidden" name="ddsrsyllabusMap[14].id" id="id"/><input type="hidden" id="key" name="ddsrsyllabusMap[14].key"/></td><td>6,窄路掉头<input type="hidden" name="ddsrsyllabusMap[14].program" id="program" value="窄路掉头"/><input type="hidden" name="ddsrsyllabusMap[14].subject" id="subject" value="科目三"/><input type="hidden" name="ddsrsyllabusMap[14].programType" id="programType" value="实际操作"/></td><td><input size="3" name="ddsrsyllabusMap[14].time" id="time"   /><input type="hidden" name="ddsrsyllabusMap[14].serial" id="serial" value="306"/></td>
					    </tr>
					    <tr >
					    <td align="right"><input type="checkbox"/><input type="hidden" name="ddsrsyllabusMap[15].id" id="id"/><input type="hidden" id="key" name="ddsrsyllabusMap[15].key"/></td><td>7,超实线<input type="hidden" name="ddsrsyllabusMap[15].program" id="program" value="超实线"/><input type="hidden" name="ddsrsyllabusMap[15].subject" id="subject" value="科目三"/><input type="hidden" name="ddsrsyllabusMap[15].programType" id="programType" value="实际操作"/></td><td><input size="3" name="ddsrsyllabusMap[15].time" id="time"   /><input type="hidden" name="ddsrsyllabusMap[15].serial" id="serial" value="307"/></td>
					    </tr>
					    <tr>
					    	<td align="right">科四:</td><td>教学项目</td><td>学时</td>
					    </tr>
				    	<tr >
				    	<td align="right"><input type="checkbox"/><input type="hidden" name="ddsrsyllabusMap[16].id" id="id"/><input type="hidden" id="key" name="ddsrsyllabusMap[16].key"/></td><td>1,安全驾驶<input type="hidden" name="ddsrsyllabusMap[16].program" id="program" value="安全驾驶"/><input type="hidden" name="ddsrsyllabusMap[16].subject" id="subject" value="科目四"/><input type="hidden" name="ddsrsyllabusMap[16].programType" id="programType" value="理论知识"/>  </td><td><input size="3" name="ddsrsyllabusMap[16].time" id="time"   /> <input type="hidden" name="ddsrsyllabusMap[16].serial" id="serial" value="401"/></td>
				    	</tr>
					</table>
				</div>
				<s:token></s:token> 
			</form>
	</div>
	
	<div class="jqmWindow " style="top: 10%;width: 700px;right: 20%;height: 440px;" id="jqModelOfContent">
            <form name="contentForm" id="contentForm" method="post">
                <input type="submit" name="submit" style="display:none"/>
                <div class="drag">
					设置教学项目内容
                  <div class="close">
                    </div>
                </div>
                <div style="width: 700px;height: 410px;overflow: scroll;">
		 			<table cellpadding="0px" border="1" bordercolor="white"   cellspacing="0px" name="tableOfContent" id="tableOfContent" width="100%" style=""> 
					    <thead>
					    	<tr id="search">
					    		<td style="width: 60px;border: 0px;">
					    			查询：
					    		</td>
					    		<td style="width: 60px;border: 0px;"> 
							 		<select id="type" name="type" >
							 			<option value="">请选择</option>
							    		<option value="C1">C1</option>
							    		<option value="C2">C2</option>
							    		<option value="C3">C3</option>
							    		<option value="C4">C4</option>
							    		<option value="C5">C5</option>
							    		<option value="D">D</option>
							    		<option value="E">E</option>
							    		<option value="F">F</option>
							    		<option value="B1">B1</option>
							    		<option value="B2">B2</option>
							    		<option value="A1">A1</option>
							    		<option value="A2">A2</option>
							    		<option value="A3">A3</option>
						    		</select> 
					    		</td>
					    		<td style="width: 60px;border: 0px;"> 
						 			<select id="subject" name="subject"   >
						 				<option value="">请选择</option>
						 				<option value="科目一">科目一</option>
						 				<option value="科目二">科目二</option>
						 				<option value="科目三">科目三</option>
						 				<option value="科目四">科目四</option>
						 			</select> 
						 		</td>
						 		<td style="width: 60px;border: 0px;">
						 			<select id="program" name="program" >
						 				<option value="">请选择</option>
						 				<optgroup label="科目一">
						 					<option value="机械常识">机械常识</option>
						 					<option value="法律法规">法律法规</option>
						 					<option value="安全文明">安全文明</option>
						 				</optgroup>
						 				<optgroup label="科目二">
						 					<option value="倒车入库">倒车入库</option>
						 					<option value="侧方移位">侧方移位</option>
						 					<option value="单边桥">单边桥</option>
						 					<option value="直角转弯">直角转弯</option>
						 					<option value="S路">S路</option>
						 					<option value="坡道起步">坡道起步</option>
						 				</optgroup>
						 				<optgroup label="科目三">
						 					<option value="加减挡">加减挡</option>
						 					<option value="靠边停车">靠边停车</option>
						 					<option value="过红绿灯">过红绿灯</option>
						 					<option value="超车">超车</option>
						 					<option value="灯光使用">灯光使用</option>
						 					<option value="窄路掉头">窄路掉头</option>
						 					<option value="超实线">超实线</option>
						 				</optgroup>
						 				<optgroup label="科目四">
						 					<option value="安全驾驶">安全驾驶</option>
						 				</optgroup>
						 			</select> 
						 		</td>
						 		<td style="width: 220px;border: 0px;"><input type="text" id="content" name="content" />
						 		</td>
					    		<td style="border: 0px;">
					    		<img src="<%=basePath%>/images/r_7_09.gif"  class="search" style="cursor: pointer;" width="16" height="18"/></td>
					    	</tr>
					   		<tr>
					    		<th colspan="6"><img src="<%=basePath%>/images/r_8_06.gif"  class="add" style="cursor: pointer;float: left;"/>
					    		
					    		<img src="<%=basePath%>/images/admin_images/bc.png"  class="save" style="cursor: pointer;float: right;"/></th>
					    	</tr>
					    	<tr>
					    		<th style="width: 30px;">序号</th>
					    		<th style="width: 60px;">驾照类型</th>
					    		<th style="width: 60px;">科目</th>
					    		<th style="width: 60px;">教学项目</th>
					    		<th style="width: auto;">教学内容</th>
					    		<th style="width: 60px;">操作</th>
					    	</tr>
					    </thead>
					    <tbody>
					    		<tr id="clone" class="clone" style="display: none;">
						 		<td>&nbsp; </td>
						 		<td> 
							 		<select id="type" name="type">
							    		<option value="C1">C1</option>
							    		<option value="C2">C2</option>
							    		<option value="C3">C3</option>
							    		<option value="C4">C4</option>
							    		<option value="C5">C5</option>
							    		<option value="D">D</option>
							    		<option value="E">E</option>
							    		<option value="F">F</option>
							    		<option value="B1">B1</option>
							    		<option value="B2">B2</option>
							    		<option value="A1">A1</option>
							    		<option value="A2">A2</option>
							    		<option value="A3">A3</option>
						    		</select> 
					    		</td>
						 		<td> 
						 			<select id="subject" name="subject"   >
						 				<option value="">请选择</option>
						 				<option value="科目一">科目一</option>
						 				<option value="科目二">科目二</option>
						 				<option value="科目三">科目三</option>
						 				<option value="科目四">科目四</option>
						 			</select> 
						 		</td>
						 		<td>
						 			<select id="program" name="program" >
						 				<option value="">请选择</option>
						 				<optgroup label="科目一">
						 					<option value="机械常识">机械常识</option>
						 					<option value="法律法规">法律法规</option>
						 					<option value="安全文明">安全文明</option>
						 				</optgroup>
						 				<optgroup label="科目二">
						 					<option value="倒车入库">倒车入库</option>
						 					<option value="侧方移位">侧方移位</option>
						 					<option value="单边桥">单边桥</option>
						 					<option value="直角转弯">直角转弯</option>
						 					<option value="S路">S路</option>
						 					<option value="坡道起步">坡道起步</option>
						 				</optgroup>
						 				<optgroup label="科目三">
						 					<option value="加减挡">加减挡</option>
						 					<option value="靠边停车">靠边停车</option>
						 					<option value="过红绿灯">过红绿灯</option>
						 					<option value="超车">超车</option>
						 					<option value="灯光使用">灯光使用</option>
						 					<option value="窄路掉头">窄路掉头</option>
						 					<option value="超实线">超实线</option>
						 				</optgroup>
						 				<optgroup label="科目四">
						 					<option value="安全驾驶">安全驾驶</option>
						 				</optgroup>
						 			</select> 
						 		</td>
						 		<td><input type="text" id="content" name="content" />
						 		<input type="hidden" id="key" name="key" />
						 		<input type="hidden" id="id"  name="id" />
						 		</td>
						 		<td>
							 		 <img src="<%=basePath%>/images/r_8_10.gif"  style="cursor: pointer;" class="edit"/>&nbsp;&nbsp;<img src="<%=basePath%>/images/r_8_08.gif"  style="cursor: pointer;" class="del"/> 
						 		</td>
						 	</tr>
					    </tbody>
					    <tfoot>
						    <tr id="data" class="data" style="display: none;">
							 		<td>  <span id="number"></span> </td>
							 		<td> 
								 		<select id="type" name="type">
								    		<option value="C1">C1</option>
								    		<option value="C2">C2</option>
								    		<option value="C3">C3</option>
								    		<option value="C4">C4</option>
								    		<option value="C5">C5</option>
								    		<option value="D">D</option>
								    		<option value="E">E</option>
								    		<option value="F">F</option>
								    		<option value="B1">B1</option>
								    		<option value="B2">B2</option>
								    		<option value="A1">A1</option>
								    		<option value="A2">A2</option>
								    		<option value="A3">A3</option>
							    		</select> 
							    		<span id="type"> </span> 
						    		</td>
							 		<td> 
							 			<select id="subject" name="subject" >
							 				<option value="">请选择</option>
							 				<option value="科目一">科目一</option>
							 				<option value="科目二">科目二</option>
							 				<option value="科目三">科目三</option>
							 				<option value="科目四">科目四</option>
							 			</select>
							 			<span id="subject"> </span> 
							 		</td>
							 		<td>
							 			<select id="program"  name="program">
							 				<option value="">请选择</option>
							 				<optgroup label="科目一">
							 					<option value="机械常识">机械常识</option>
							 					<option value="法律法规">法律法规</option>
							 					<option value="安全文明">安全文明</option>
							 				</optgroup>
							 				<optgroup label="科目二">
							 					<option value="倒车入库">倒车入库</option>
							 					<option value="侧方移位">侧方移位</option>
							 					<option value="单边桥">单边桥</option>
							 					<option value="直角转弯">直角转弯</option>
							 					<option value="S路">S路</option>
							 					<option value="坡道起步">坡道起步</option>
							 				</optgroup>
							 				<optgroup label="科目三">
							 					<option value="加减挡">加减挡</option>
							 					<option value="靠边停车">靠边停车</option>
							 					<option value="过红绿灯">过红绿灯</option>
							 					<option value="超车">超车</option>
							 					<option value="灯光使用">灯光使用</option>
							 					<option value="窄路掉头">窄路掉头</option>
							 					<option value="超实线">超实线</option>
							 				</optgroup>
							 				<optgroup label="科目四">
							 					<option value="安全驾驶">安全驾驶</option>
							 				</optgroup>
							 			</select> 
							 			<span id="program"> </span> 
							 		</td>
							 		<td>
							 		<input type="text"  id="content"  name="content"/>
							 		<span id="content"> </span>
							 		<input type="hidden" id="key"  name="key"/>
							 		<input type="hidden" id="id"   name="id"/>
							 		</td>
							 		<td>
								 		<img src="<%=basePath%>/images/r_8_10.gif"  style="cursor: pointer;" class="edit"/>&nbsp;&nbsp;<img src="<%=basePath%>/images/r_8_08.gif"  style="cursor: pointer;" class="del"/> 
							 		</td>
							 	</tr>
					    </tfoot>
					</table>
				</div>
				<s:token></s:token> 
			</form>
	</div>
		     
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe> 
    </body>
</html>