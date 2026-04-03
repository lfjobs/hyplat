<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page language="java" pageEncoding="UTF-8" %>
<%@page import="hy.ea.bo.Company"%>
<%@page import="hy.ea.bo.CAccount"%>
        <%@ taglib uri="/struts-tags" prefix="s" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
        <%
	        String path = request.getContextPath();
	        String basePath = request.getScheme() + "://"
	        + request.getServerName() + ":" + request.getServerPort()
	        + path + "/"; 
	        CAccount account=(CAccount)session.getAttribute("account");
	        Company c = (Company)session.getAttribute("currentcompany");%>
        <html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="cache-control" content="no-cache"/>
        <title>月结</title>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
        <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
        <script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
        <script language="javascript" type="text/javascript" src="<%=basePath%>/js/ea/finance/invoicing/voucher/voucher_jie.js"></script>
        <script type="text/javascript">
        	var treeID ="<%=c.getCompanyID()%>";
    		var treeNames="<%=c.getCompanyName()%>";
    		var staffid="<%=account.getStaffID()%>";
    		var staffname="<%=account.getStaffName()%>";
        	var basePath="<%=basePath%>";
         	var notoken = 0;
			var select=0;
			var token=0;
			var mco_id="";
			var pNumber=${pageNumber};
        </script>
        <style type="text/css">
        .td_bg01 span{
		text-align:left;display:block;}
		</style>
    </head>
    <body>
    <form name="mcoform" id="mcoform" method="post">
	    <input type="submit" name="submit" id="submit" style="display: none;"/>
	         <table class="flexme11">
	            <thead>
	                <tr>
	                    <th width="30" align="center">
	                        选择
	                    </th>
	                    <th width="200" align="center">
	                        公司
	                    </th>
	                    <th width="70" align="center">
	                       年月
	                    </th>
	                    <th width="100" align="center">
	                       月结人员
	                    </th>
	                    <th width="150" align="center">
	                       月结时间
	                    </th>
	                </tr>
	            </thead>
	            <tbody  id="tbwid">
	            <c:forEach var='arr' items="${pageForm.list}">
	            	<tr id="${arr[0]}" align="center" class="mcotr">
							<td class="td_bg01">
								<input type="radio" name="a" class="JQuerypersonvalue"
									value="${arr[0]}" />
							</td>
							<td class="td_bg01">
								<span id="companyname">${arr[3]}</span>
							</td>
							<td class="td_bg01">
								<span id="ny_time">${arr[2]}</span>
							</td>
							<td class="td_bg01">
								<span id="staffName">(${arr[5]})${arr[4]}</span>
							</td>
							<td class="td_bg01">
								<span id="day_date">${arr[1]}</span>
							</td>
						</tr>
	            </c:forEach>
	              <tr id="kelong" style="display: none;" align="center"  class="mcotr">
							<td>
								<input type="radio" name="a" class="JQuerypersonvalue" value="" id="rad"/>
							</td>
							<td>
								<input  type="text" id="com_name"  style="display:inline; border:0; width: 98%;" value="<%=c.getCompanyName()%>" readonly="readonly"/>
								<input type="hidden" id="com_id" value="<%=c.getCompanyID()%>"/>							
							</td>
							<td>
								<input id="y_m" onfocus="cher_dm(this);" style="display:inline; border:0; width: 100%;"/>
							</td>
							<td>
								<input type="text" id="mcos_name"  style="border:0;width: 98%;" value="<%=account.getStaffName()%>" readonly="readonly"/>
								<input type="hidden" id="mcos_id" value="<%=account.getStaffID()%>"/>
							</td>
							<td>
								<input type="text" id="mco_date" style="display:inline;border:0;width: 98%;" onfocus="cher_time(this);"/>
							</td>
						</tr>
	            </tbody>
	        </table>
        </form>
        <c:import url="../../../../page_navigator.jsp">
            <c:param name="actionPath" value="/ea/mco/ea_getSerch.jspa?search=${search}&pageNumber=${pageNumber}&sdate=${sdate}&edate=${edate}&period=${period}">
            </c:param>
        </c:import>
        <%------------------------------------用于表单提交-----------------------------------%>
		<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
			framespacing="0" height="0"></iframe>
    </body>
</html>
