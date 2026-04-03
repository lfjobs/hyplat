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
        <title>现场会议管理</title>
        <script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
        <style type="text/css"> 
        .wenben{
        	border-color: #FFFFFF;
        	border-width: 0px;
        	background: url('../../../images/ea/office/brue.jpg')
        }
        </style>
        <script src="<%=basePath%>js/jquery.js" type="text/javascript">
        </script>
        <script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
        </script>
        <link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
			type="text/css" />
        <link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"
			type="text/css" />
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
        <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/common/organizationTree.js">
        </script>
        <script src="<%=basePath%>js/ea/office_ea/dtconference_list.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/common/common_word.js"></script>
        <script type="text/javascript">
         var treeID ="<%=session.getAttribute("organizationID")%>";
         var treeNames="<%=session.getAttribute("organizationName")%>";
	     var prID = "";
		 var  basePath='<%=basePath%>';           
         var pNumber ='${pageNumber}';  
         var search='${search}';  
         var personurl = "";
         var token=0;
         var dtconferenceID = "";
		 var companyID='${account.companyID}';
		 var newState = "${newState}";
		 var parm = '';
   		</script>  
   		
	</head>
	<body >
		<form name="mainForms" id="mainForms" method="post">
			
            <table class="JQueryflexme">
                <thead>
                    <tr class="tablewith">
                        <th width="30" align="center">
                            	请选择
                        </th>
                         <th width="30" align="center">
                            	序号
                        </th>
                        <th width="70" align="center">
                            	日期
                        </th>
                        <th width="50" align="center">
                            	起止时间
                        </th>
                        <th width="50" align="center">
                            	终止时间
                        </th>
                         <th width="60" align="center">
                            	部门
                        </th>
                       
                        <th width="50" align="center">
                            	责任人
                        </th>
                        <th width="80" align="center">
                            	会议流程
                        </th>
                        <th width="170" align="center">
                            	会议内容
                        </th>
                       
                        
                    </tr>
                    </thead>
                   
                </table>
            
             
        </div>
       </form>
      
      
		 
       
    </body>
  
</html>