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
        <title>平台积分详细管理</title>
        <script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
        <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
        <script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
     
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
        <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
     <script src="<%=basePath%>js/ea/human/office/SersonnelSystem/jifendetail.js"></script>
        <script>
             var basePath = "<%=basePath%>";
             var ppageNumber = '${pageNumber}';
             var pageSize = '${pageForm.pageNumber}';
             var token=0;
        </script>
	</head>
	<body >
        <div class="main_main">
            <table class="address">
                <thead>
                    <tr class="tablewith">
                    			
                    			<th width="100" align="center">选择</th>
                    			<th width="100" align="center">序号</th>
                    			<th width="100" align="center">姓名</th>   		
                    			<th width="100" align="center">编号</th>
                    			<th width="100" align="center">电话</th>
                    		    <th width="100" align="center">积分</th>	
                  	</tr>     
                </thead>
                
                    <%    int number = 1; %>
                      <c:forEach items="${pageForm.list}" var="a">
                        <tr id="${a[6]}"  >
                         	   <td>  
                                <input type="radio" name="a"  class="JQuerypersonvalue" value="${a[1]}"/>
                                </td>
                                <td>
                                <span ><%=number%></span>
                               </td>            
                              <td>  <span >${a[2]}</span>	
                              </td>
                      	      <td>  <span >${a[3]}</span></td>  
                      	         <td>  <span >${a[4]}</span></td>  
                      	            <td>  <span >${a[5]}</span></td>  
                        </tr>
                        
                           <%  number++; %>
                       </c:forEach>
               
            </table> 
        <form style="display: none;" name="addressForm" id="addressForm" method="post" action="<%=basePath%>ea/activity/ea_getjifenlist.jspa">
		<s:token></s:token>
		<input id="formtest" type="test" name="inforType" style="display:none"/>
		<input type="submit" name="submit" style="display:none"/>
	</form>
           <c:import url="../../ea/page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/activity/ea_getjifenlist.jspa?pageForm.pageNumber=${pageForm.pageNumber}&pageNumber=${pageNumber}">
				</c:param>
			</c:import>
			<s:token></s:token>
        </div>
        
         
            <iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>         
    </body>
</html>