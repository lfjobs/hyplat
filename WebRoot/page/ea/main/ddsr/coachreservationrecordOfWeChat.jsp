<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib  prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"  %>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://"
+ request.getServerName() + ":" + request.getServerPort()
+ path + "/";


%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="cache-control" content="no-cache"/>
<title>预约管理（微信）</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

<script src="<%=basePath%>js/jquery.js" type="text/javascript">
</script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/admin_main.css"/>
<script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
</script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script language="javascript" type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
</script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js">
</script>
<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />

<link rel="stylesheet" type="text/css" href="<%=basePath%>css/overlayer.css"/>
<script src="<%=basePath%>js/ea/ddsr/coachreservationrecordOfWeChat.js"></script>
<style type="text/css">
.JQueryflexme .record{
	cursor: pointer;
}
.JQueryflexme .is1{
	color: red;
}
.JQueryflexme .is0{
	color: blue;
}
</style>
<style type="text/css">
table.JQueryflexme {
	font-family: verdana,arial,sans-serif;
	font-size:11px;
	color:#333333;
	border-width: 1px;
	border-color: #999999;
	border-collapse: collapse;
}
table.JQueryflexme th {
	background:#b5cfd2 url('<%=basePath%>/images/ea/ddsr/cell-blue.jpg');
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #999999;
}
table.JQueryflexme td {
	background:#dcddc0 url('<%=basePath%>/images/ea/ddsr/cell-grey.jpg');
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #999999;
}
span,a{ font-size:25px;}
</style>
<script type="text/javascript">
var  basePath='<%=basePath%>';           
var  pNumber =${pageNumber};  
var  search='${search}';
var  coacKey = '';
var  token=0;
var  notoken=0;

var  subjType='${dssrsubject.subjType}';//科目导航页面传值 区分科目类别
var  stud_key='${dssrstudent.studKey}';//区别是 个人记录 或 选择教练

var  coacStatus='${ddsrcoach.coacStatus}';
var  coacTeachtype='${ddsrcoach.coacTeachtype}';
var  coacStar='${ddsrcoach.coacStar}';
        
var   studKey='${dssrstudent.studKey}';
var  studName='${dssrstudent.dtHrStaff.staffName}';

var coacKey="";//选中教练ID
var rereAppdate="";//选中预约日期
</script>    
</head>
	<body>
		<c:import url="../../page_navigatorForWeChat.jsp">
                <c:param name="actionPath" value="/ea/appointmentbymicroletter/ea_getListCoachReservationRecord.jspa?pageNumber=${pageNumber}&search=${search}&dssrsubject.subjType=${dssrsubject.subjType}">
                </c:param>
        </c:import>
        <div class="table-container-outer">
        	<div class="table-container-fade"></div>
        	<div class="table-container">
	  			<table class="JQueryflexme"  width="100%">
                <thead>
                	<tr>
                        <td  align="center" >
                     	   <div><a href="<%=basePath%>/ea/appointmentbymicroletter/ea_toSearchPage.jspa?">查询</a></div>
                        </td>
                    </tr>
                    <tr>
                        <th  align="center" style="padding-top:10px;padding-bottom:10px;">
                     	   <div><span>教练名称/车牌号</span></div>
                        </th>
                    </tr>
                </thead>
                <tbody> 
                    <s:iterator value="pageForm.list" var="list" status="number">
                    	<tr  id="<s:property value="#list[2]"/>">
	                    	 <td  align="center" style="padding-top:10px;padding-bottom:10px;">
	                    		 
	                    		 <span ><s:property value="#list[3]"/><br/><s:property value="#list[4]"/></span>
	                    		 	<%-- <span id="coac_carNumber" ><s:property value="#list[3]"/><br /> <s:property value="#list[4]"/></span>
	                    		 	<span id="coac_carNumber" ><img src="<%=basePath%>/<s:property value="#list[17]"/>" alt="" width="96" height="96"/></span> --%>
	                    		 <%-- <div><span id="staffname"><s:property value="#list[3]"/></span></div>
	                    		 <div style="padding-top:10px;"><span id="coac_carNumber" ><s:property value="#list[4]"/></span></div>
	                    		 <div><span id="staffname"> <img src="<%=basePath%>/<s:property value="#list[17]"/>" alt="" width="100" height="100"/></span></div> --%>
	                    		 <div style="display: none;">
	                    		 <span id="coac_status"><s:property value="#list[5]"/></span>
	                    		 <span id="coac_level"><s:property value="#list[6]"/></span>
	                    		 <span id="coac_teachtype"><s:property value="#list[7]"/></span>
	                    		 <span id="coac_star"><s:property value="#list[8]"/></span>
	                    		 </div>
	                         </td>
	                     </tr> 
	                     <tr>
	                     	 <td align="center">
	                         	<span ><img src="<%=basePath%>/<s:property value="#list[17]"/>" alt="" width="122" height="78" onerror="this.src='<%=basePath%>/images/ea/ddsr/moren.jpg'"/></span>
	                         </td>
	                     </tr>   
	                         <s:iterator value="arrayDateList" var="dt" status="status">
	                        	<tr>
	                        		<th align="center" style="padding-top:10px;padding-bottom:10px;">
	                        			<div><span><s:property value="#dt"/></span></div>
	                        		</th>
	                        	</tr>
	                        	<tr>
	                        	<td style="padding-top:20px;padding-bottom:20px;" align="center" id="<s:property value="#list[2]"/>@<s:property value="#dt"/>" class="record is${fn:trim(fn:split(list[status.index+9],'@')[1])}" >
		                        		<div><span>早间 |上午|下午|夜间 </span></div>
		                        		<div style="padding-top:10px;"><span>${fn:split(list[status.index+9],'@')[0]}</span></div>
		                      		</td>
	                      		</tr>
	                       	 </s:iterator> 
                        
                    </s:iterator>
                </tbody>
            </table>
			</div> 
        </div>
        <c:import url="../../page_navigatorForWeChat.jsp">
                <c:param name="actionPath" value="/ea/appointmentbymicroletter/ea_getListCoachReservationRecord.jspa?pageNumber=${pageNumber}&search=${search}&dssrsubject.subjType=${dssrsubject.subjType}">
                </c:param>
        </c:import>
         <iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
   
   <!-- 根据教练，日期生成 预约详细记录 列表 -->
<div id="jqmWindow2" class="jqmWindow"  align="center"
	style="width: 1000px; height: 450px; absolute; display: none; left: 10%; top: 10%; background: #eff">
		<iframe name="ifr2" id="ifr2" width="1000px" height="400px"
		frameborder="0"></iframe>
		<input type="button" class="input-button" id="isSubmit2" value=" 确定预约 "
			style="cursor: hand" />
		<input type="button" class="input-button" id="isBack2" value=" 关闭 "
			style="cursor: hand" />
 </div>
 
    <!-- 选择学员 -->
<div id="jqmWindow3" class="jqmWindow"  align="center"
	style="width: 1000px; height: 450px; absolute; display: none; left: 10%; top: 10%; background: #eff">
		<iframe name="ifr3" id="ifr3" width="1000px" height="400px"
		frameborder="0"></iframe>
		<input type="button" class="input-button" id="isSubmit3" value=" 确定 "
			style="cursor: hand" />
		<input type="button" class="input-button" id="isBack3" value=" 关闭 "
			style="cursor: hand" />
 </div>
 
 <!-- 查询功能 -->
 <div class="jqmWindow" style="width: 420px; right: 40%;; top: 15%" 
			id="jqModelSearch">
			<form name="SearchForm" id="SearchForm" method="post" action="">
				<div class="drag">
					预约查询
					<div class="close">
					</div>
				</div>
				<table width="420" id="cataffSearchTable">
					<%-- <tr>
						<td width="123" align="right">
							公司名称：
						</td>
						<td width="261">
							<select id="companyID" name="dtDrivingPrincipal.companyid">
							</select>
						</td>
					</tr> --%>
					<tr>
						<td width="123" align="right">
							预约时间：
						</td>
						<td width="261">
							<input type="text" style="width: 80px" name="ddsrreservationrecord.searchStaDate" onfocus="WdatePicker({skin:'whyGreen',dateFmt: 'yyyy-MM-dd',maxDate:'%y-%M-{%d+7}'})" value="<fmt:formatDate value="${ddsrreservationrecord.searchStaDate}" pattern="yyyy-MM-dd" />"/> <span>&nbsp;
							至&nbsp;</span><input type="text" style="width: 80px" name="ddsrreservationrecord.searchEndDate"  onfocus="WdatePicker({skin:'whyGreen',dateFmt: 'yyyy-MM-dd',maxDate:'%y-%M-{%d+7}'})" value="<fmt:formatDate value="${ddsrreservationrecord.searchEndDate}" pattern="yyyy-MM-dd" />"/>
						</td>
					</tr>
					<tr>
				    	<td  align="right" width="100">是否已满：</td>
				    	<td width="200"><select name="ddsrreservationrecord.rerePeoplesum" id="rerePeoplesum">
						  <option value="">请选择</option>
                          <option value="0">未满</option>
                          <option value="1">已满</option>
                        </select></td>				    	
					</tr>
					<tr>
						<td width="123" align="right">
							预约教练：
						</td>
						<td width="261">
							<input name="ddsrcoach.dtHrStaff.staffName" />
						</td>
					</tr>
					<tr>
				    	<td  align="right" width="100">教练等级：</td>
				    	<td width="200"><select name="ddsrcoach.coacLevel" id="coacLevel">
						  <option value="">请选择</option>
                          <option value="10">普通</option>
                          <option value="20">高级</option>
                        </select></td>				    	
					</tr>
                </table>
            <div align="center">
            <input type="submit" name="submit" style="display: none" />
              <input type="button" class="input-button" id="tosearch" value=" 查询 " />
              <input name="search" type="hidden" value="search" />
              <!-- 个人预约记录 查询 学员ENTITY-->
              <input name="dssrstudent.studKey" type="hidden" value="${dssrstudent.studKey}"/>
              <!-- 选择教练预约 查询 教练ENTITY以及科目ENTITY-->
              <input name="dssrsubject.subjType" type="hidden" value="${dssrsubject.subjType}"/>
              <input name="ddsrcoach.coacStatus" type="hidden" value="${ddsrcoach.coacStatus}"/>
              <input name="ddsrcoach.coacTeachtype" type="hidden" value="${ddsrcoach.coacTeachtype}"/>
              <input name="ddsrcoach.coacStar" type="hidden" value="${ddsrcoach.coacStar}"/>
              <!--  转发传递个人ID以及NAME-->
              <input name="studKey" type="hidden" value="${param.studKey}" />
              <input name="studName" type="hidden" value="${param.studName}" />
            </div>
            </form>
        </div>
 
<!-- 隐藏from  学员选择多个预约时间段 -->   
<form name="ReservationRecoardForm" id="ReservationRecoardForm" method="post" action="">
   			<input type="submit" name="submit" style="display: none" />
   			<input name="rereKeyString" type="hidden" id="rereKeyString"/>
   			<input name="dssrstudent.studKey" type="hidden" id="studKey"/>
   			<input name="dssrsubject.subjType" type="hidden" value="${dssrsubject.subjType}"/>
</form>
    </body>
</html>