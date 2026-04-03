<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
        <%@ page language="java" pageEncoding="UTF-8" %>
        <%@ taglib uri="/struts-tags" prefix="s" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%
        	String path = request.getContextPath();
        	String basePath = request.getScheme() + "://"
        			+ request.getServerName() + ":" + request.getServerPort()
        			+ path + "/";
        %>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>个人仓库--个人基本信息</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
        <script src="<%=basePath%>js/jquery.js" type="text/javascript">
        </script>
        <script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
        </script>
        <link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
        <script type="text/javascript" src="<%=basePath%>js/flexigrid.js">
        </script>
            <script src="<%=basePath%>js/ea/human/staff_info.js">
        </script>
        <script src="<%=basePath%>js/ea/human/cstaff.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js">
        </script>
        <script src="<%=basePath%>js/ea/human/office/production/BPInformation_list.js"></script>
        
        <script  src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js"></script>
		<script  src="<%=basePath%>js/tree/codebase/dhtmlxtree.js"></script>
		<link rel="STYLESHEET" type="text/css" href="<%=basePath%>js/tree/codebase/dhtmlxtree.css"/>
		<script type="text/javascript" src="<%=basePath%>js/common/organizationTree.js"></script>
        
    </head>
    <body >
        <div class="main_main">
            <table class="JQueryflexme">
                <thead>
					<tr class="tablewith">
						<th width="100" align="center">
							上月工资
						</th>
						<th width="100" align="center">
							专岗
						</th>
						<c:forEach items="${jg}">
						<th width="100" align="center">
							兼岗
						</th>
						</c:forEach>
					</tr>
				</thead>
                <tbody>
						<tr >
							<td>
								<span id="gz">${gz}</span>
							</td>
							<td>
								<span id="zg">${zg}</span>
							</td>
							<s:iterator id="data1" value="jg" status="d1">
							<td>
								<span id="jg">${data1}</span>
							</td>
							</s:iterator>
                    </tr>
                </tbody>
            </table>
    </body>
    
    <script type="text/javascript">
        
        var personurl = "";
        var staffName="";
       
        var retoken = 0;
        var token = 0;
        var notoken = 0;
        
        var comID = '${account.companyID}';
		var companyName='<%=request.getAttribute("companyName")%>';
		var parentID;
		var treeid = null;
		var treename;
		var parentid;
		var parentname;
		var organizationid;
		var tree;
		
    </script>
</html>