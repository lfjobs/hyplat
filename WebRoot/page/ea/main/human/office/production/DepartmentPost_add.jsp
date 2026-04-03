<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
   <html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="cache-control" content="no-cache"/>
        <title>岗位职责汇总列表</title>  
        
        <%@ page language="java" pageEncoding="UTF-8" %>
        <%@ taglib uri="/struts-tags" prefix="s" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
         <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
        <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
        + request.getServerName() + ":" + request.getServerPort()
        + path + "/"; %>
        
        <style type="text/css"> 
		.table {
			border-collapse:collapse;
			border:1px solid #a8c7ce;
			font-size:12px;
			margin:5px auto;
		}
		.table td {
			border:1px solid #a8c7ce;
			color:#333;
			padding-left:5px;
		}
		.content1{ 
			display:block; 
			margin:0 auto;
			border:#99bbe8 1px solid;
			background:#dae7f6;
		}
        </style>
        <script src="<%=basePath%>js/jquery.js" type="text/javascript">
        </script>
        <script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
        </script>
        <link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
        <script language="javascript" type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js">
        </script>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>css/overlayer.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>css/admin_main2.css"/>
        <script type="text/javascript" src="<%=basePath%>js/ea/human/office/production/DepartmentPost_add.js">
        </script>
        <script language="javascript" type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/common/organizationTree.js"></script>
        <link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
        <script src="<%=basePath%>js/ea/validate.js"  type="text/javascript"></script>
        
        <script type="text/javascript">
            var pbasePath = "<%=basePath%>";
            var ppageNumber = ${pageNumber};
            var psearch = '${search}';
            var depPostID = '${departmentPost.depPostID}';
            var maxNum = '${maxNum}';
            var postNum = '${departmentPost.postNum}';
            var organizationName = '${departmentPost.organizationID}';
            var token = 0;
            var comID = '${account.companyID}';
            
            var reValue = '<%=request.getParameter("reValue")%>'; //返回页面参数
            var stID = '<%=request.getParameter("staffID")%>'; //人员ID传值
            var stName = '<%=request.getParameter("staffName")%>'; //人员name传值
            var auID = '<%=request.getParameter("auditionID")%>'; //人员职责ID传值
            var pnum = '<%=request.getParameter("pnum")%>'; //页数传值
            var companyName = '<%=request.getParameter("companyName")%>'; //公司名称传值
		</script>
		
	</head>
	
	<body>
	<div class="jqmWindow" style="top:1%;" id="jqModel">
	<form name="cstaffForm" id="cstaffForm" method="post" enctype="multipart/form-data">
	<input type="submit" name="submit" style="display:none"/>
	<input name="departmentPost.depPostKey" value="${departmentPost.depPostKey }" style="display:none"/>
	<input name="departmentPost.depPostID" value="${departmentPost.depPostID }" style="display:none"/>
	<input name="departmentPost.companyID" value="${departmentPost.companyID }" style="display:none"/>

	<div class="content1" style="width:100%; background:#FFFFFF;">
	  <div class="contentbannb">
	  	<div class="divtx">职位说明书</div>
	    <div class="close"></div>
	  </div>
	     <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" class="table">
	<tr>
	      <td width="100" height="35" align="right">职位名称：</td>
	      <td><input name="departmentPost.postName" value="${departmentPost.postName}" class="put3"/></td>
	      <td width="100" align="right">职位编号：</td>
	      <td><input name="departmentPost.postNum" value="${departmentPost.postNum}" id="postNum" readonly="readonly"/></td>
	      <td width="100" align="right">所属部门：</td>
	      <td><select name="departmentPost.organizationID" id="organizationID">${departmentPost.organizationID}</select>
	     	  <input type="hidden" name="departmentPost.leveloneOrgID" value="${departmentPost.leveloneOrgID}" id="leveloneOrgID"/>
	      </td>
	</tr>
	<tr>
	       <td height="35" align="right">辖员人数：</td>
	      <td><input name="departmentPost.adminNum" id="adminNum" class="positiveNum" value="${departmentPost.adminNum}"/></td>
	      <td align="right">职位定员：</td>
	      <td colspan="3"><input name="departmentPost.postSureNum" id="postSureNum" class="positiveNum" value="${departmentPost.postSureNum}"/></td>
	      </tr>
	  </table>
	  <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" class="table">
	       <tr>
	         <td width="100" rowspan="2" align="right">职责概述：</td>
	         <td width="100" height="35" align="right">岗位职责：</td>
	         <td style="padding:5px">
	           <textarea name="departmentPost.postResponsibility" id="postResponsibility" class="ckTextLength" maxlength="2000" cols="98" rows="5" style="margin-left:2px;">${departmentPost.postResponsibility}</textarea>
	         </td>
	       </tr>
	       <tr>
	         <td height="35" align="right">任职要求：</td>
	         <td style="padding:5px">
	           <textarea name="departmentPost.responsibilityRequire" id="responsibilityRequire" class="ckTextLength" maxlength="2000" cols="98" rows="5" style="margin-left:2px;">${departmentPost.responsibilityRequire}</textarea>
	         </td>
	       </tr>
	  </table>
	  <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" class="table">
	       <tr>
	         <td width="100" align="right">备注：</td>
	         <td style="padding:5px">
	           <textarea name="departmentPost.remark" id="remark" class="ckTextLength" maxlength="2000" cols="113" rows="5" style="margin-left:2px;">${departmentPost.remark}</textarea>
	         </td>
	       </tr>
	     </table>
	  <table width="98%" height="35" border="0" align="center" cellpadding="0" cellspacing="0" style="background-color:#FFFFFF; margin-top:5px" class="table">
	       <tr>
	         <td align="center"><input type="button" class="btn02 JQuerySubmit" name="button" value="提交"/>  &nbsp;&nbsp;         
	            <input type="button" class="btn02 JQueryNoSubmit" name="button5" value="取消" /></td>
	       </tr>
	     </table>
	</div>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
    <s:token></s:token>
	</form>
	</div>
	</body>
</html>