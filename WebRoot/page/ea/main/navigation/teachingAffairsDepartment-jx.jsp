<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>教务(生产)一项目管理</title>
    <link href="<%=basePath %>css/navigation_a.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="<%=basePath %>/js/jquery.js"></script>
		<script type="text/javascript" src="<%=basePath %>/js/dropdown/extendPageMenu.js"></script>	
		<script>
function clickAction(action,parater){
if(parater == '1'){
var treeID = '<%=session.getAttribute("organizationID")%>';
 window.location.href= action+treeID;
 return;
 }
 window.location.href= action;
}
</script>
	</head>

	<body>
	<table>

      <tr>
        <td  align="center">
          <div class="na_back_img" onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/productionManagement.jsp?companyGroupLogo=${param.companyGroupLogo}'"></div>
          <div class="center_a">驾校行业生产系统</div>
        </td>
        <td><div class="na_back_img_jt_hx"></div></td>
        <td><table border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td>
                <div class="na_back_img" onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/researchManagement_order.jsp?companyGroupLogo=${param.companyGroupLogo}'"></div>
                <div class="center_a">预约配置管理</div>
                </td>
              <td>
              <%--<div class="na_back_img"  onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/simulationManagement.jsp?companyGroupLogo=${param.companyGroupLogo}'"></div>--%>
                  <div class="na_back_img"  onclick="document.location.href='<%=basePath%>/ea/coachreserv/ea_pcStudentManager.jspa'"></div>
                <div class="center_a">约车管理</div>
               </td>
                <td>
                <div class="na_back_img" onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/trainingManagement.jsp?companyGroupLogo=${param.companyGroupLogo}'"></div>
                <div class="center_a">培训管理</div>
                </td>

            </tr>

        </table></td>
      </tr>

    </table>
</body>
</html>
