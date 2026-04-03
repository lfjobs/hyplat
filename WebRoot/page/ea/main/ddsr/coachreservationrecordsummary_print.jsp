<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8" %>
        <%@ taglib uri="/struts-tags" prefix="s" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
       <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
        <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
        + request.getServerName() + ":" + request.getServerPort()
        + path + "/"; %>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="cache-control" content="no-cache"/>
        <title>预约记录汇总打印</title>
<style type="text/css">
.table {
	border-collapse: collapse;
	border: 1px solid #000000;
	font-size: 16px;
	padding-top:2px;
	padding-bottom:2px;
	padding-left:3px;
	padding-right:3px;
}
.table th {
	border: 1px solid #000000;
	color: #000000;
	background: #E4F1FA;
	white-space: nowrap;
	align: center;
	padding-top:2px;
	padding-bottom:2px;
	padding-left:3px;
	padding-right:3px;
}

.table td {
	border: 1px solid #000000;
	color: #000000;
	padding-top:2px;
	padding-bottom:2px;
	padding-left:3px;
	padding-right:3px;
}

body,td,th {
	font-size: 16px;
}

body {
	TEXT-ALIGN: center;
}
</style>    
</head>
	<body>
        <div style="margin-left: auto;margin-right: auto;width: 1000px;" >
        	<table width="1000" border="0" cellpadding="0" cellspacing="0"
					style="background: #FFFFFF;text-align: center;">
					<tr>
						<td height="25" align="center" style="font-weight: bold">
							<c:choose>
										<c:when test="${dssrsubject.subjType==10}">科一预约记录报表</c:when>
										<c:when test="${dssrsubject.subjType==20}">科二预约记录报表</c:when>
										<c:when test="${dssrsubject.subjType==30}">科三预约记录报表</c:when>
										<c:when test="${dssrsubject.subjType==40}">科四预约记录报表</c:when>
										<c:otherwise></c:otherwise>
							</c:choose>
						</td>
					</tr>
			</table>
            <table style="widht:630px;" cellpadding="0" cellspacing="0" class="table">
                <thead>
                    <tr>
                        <th width="40" align="center">
                     	   序号
                        </th>
                        <th width="80" align="center">
                     	   预约时间
                        </th>
                        <th width="80" align="center">
                     	   预约班次
                        </th>
                        <th width="80" align="center">
                     	   预约起时间
                        </th>
                        <th width="80" align="center">
                     	   预约止时间
                        </th>
                        <th width="80" align="center">
                     	   预约人数
                        </th>
                        <th width="80" align="center">
                     	   预约科目
                        </th>
                        <th width="80" align="center">
                     	   预约教练
                        </th>
                        <th width="110" align="center">
                     	   教练电话
                        </th>
                        <th width="80" align="center">
                     	   预约学员
                        </th>
                        <th width="110" align="center">
                     	   学员电话
                        </th>
                    </tr>
                </thead>
                <tbody> 
                    <s:iterator value="ddsrreservationrecordsummaryList" var="list" status="number">
                    	<tr  id="<s:property value="#list[0]"/>">
	                    	 <td  align="center">
	                    		 <span><s:property value="#number.index+1"/></span>
	                         </td>
	                         <td  align="center">
	                    		 <span><s:property value="#list[2]"/></span>
	                         </td>
	                         <td  align="center">
	                    		 <span>
	                    		 	<c:choose>
										<c:when test="${list[3]==10}">早间</c:when>
										<c:when test="${list[3]==20}">上午</c:when>
										<c:when test="${list[3]==30}">下午</c:when>
										<c:when test="${list[3]==40}">晚间</c:when>
									</c:choose>
								</span>
	                         </td>
	                         <td  align="center">
	                    		 <span><s:property value="#list[4]"/></span>
	                         </td>
	                         <td  align="center">
	                    		 <span><s:property value="#list[5]"/></span>
	                         </td>
	                         <td  align="center">
	                    		 <span><s:property value="#list[6]"/></span>
	                         </td>
	                         <td  align="center">
	                    		 <span>
	                    		 	<c:choose>
										<c:when test="${list[7]==10}">科一</c:when>
										<c:when test="${list[7]==20}">科二</c:when>
										<c:when test="${list[7]==30}">科三</c:when>
										<c:when test="${list[7]==40}">科四</c:when>
										<c:when test="${list[7]==90}">暂无</c:when>
									</c:choose>
	                    		 </span>
	                         </td>
	                         <td  align="center">
	                    		 <span><s:property value="#list[8]"/></span>
	                         </td>
	                         <td  align="center">
	                    		 <span><s:property value="#list[9]"/></span>
	                         </td>
	                         <td  align="center">
	                    		 <span><s:property value="#list[10]"/></span>
	                         </td>
	                         <td  align="center">
	                    		 <span><s:property value="#list[11]"/></span>
	                         </td>
                        </tr>
                    </s:iterator>
                </tbody>
            </table>
        </div> 
    </body>
</html>