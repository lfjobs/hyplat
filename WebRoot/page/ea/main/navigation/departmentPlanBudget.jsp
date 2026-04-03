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
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>部门项目计划预算</title>
	<link href="<%=basePath %>css/navigation_a.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="<%=basePath %>/js/jquery.js"></script>
	<script type="text/javascript" src="<%=basePath %>/js/dropdown/extendPageMenu.js"></script>
	</head>
	<body>
	<br/>
	<table width="100%" >
      <tr><td><table border="0"  width="100%" cellspacing="2" cellpadding="2">
            <tr>
               <td>
              <div class="na_back_img"  onclick="document.location.href='<%=basePath%>ea/promanage/ea_getProjectList.jspa'">
              <img src="<%=basePath%>images/sytemicon/r11_c3.gif"></img>
              </div>
			  <div class="center_a">项目管理</div>	
              </td>
              <td><div class="na_back_img_jt_hx"></div></td>
              <td>
              <div class="na_back_img"  onclick="document.location.href='<%=basePath%>ea/costsheet/ea_getCostSheetList.jspa?jumptype=fxlb&summary=bm'">
              <img src="<%=basePath%>images/sytemicon/r11_c3.gif"></img>
              </div>
			  <div class="center_a">项目计划预算</div>	
              </td>
              <td><div class="na_back_img_jt_hx"></div></td>
              <td>
              	<div class="na_back_img" onclick="document.location.href='<%=basePath%>ea/costsheet/ea_getCostSheetList.jspa?jumptype=zbqsh'" >
              	<img src="<%=basePath%>images/sytemicon/r11_c9.gif"></img>
              	</div>
				<div class="center_a">未审核项目预算</div>
              </td>
              <td><div class="na_back_img_jt_hx"></div></td>
               <td>
              	<div class="na_back_img" onclick="document.location.href='<%=basePath%>ea/costsheet/ea_getCostSheetList.jspa?jumptype=zbqysh'" >
              	<img src="<%=basePath%>images/sytemicon/r11_c9.gif"></img>
              	</div>
				<div class="center_a">已审核项目预算</div>
              </td>
              <td><div class="na_back_img_jt_hx"></div></td>
              <td>
              	<div class="na_back_img" onclick="document.location.href='<%=basePath%>ea/costsheet/ea_getCostSheetList.jspa?jumptype=bjlb'">
              	<img src="<%=basePath%>images/sytemicon/r11_c7.gif"></img>
              	</div>
				<div class="center_a">未招标比价项目管理</div>
              </td> 
              <td><div class="na_back_img_jt_hx"></div></td>
               <td>
              	<div class="na_back_img" onclick="document.location.href='<%=basePath%>ea/product/ea_getZbPriceedList.jspa'">
              	<img src="<%=basePath%>images/sytemicon/r11_c7.gif"></img>
              	</div>
				<div class="center_a">已比价物品管理</div>
              </td> 
             
            </tr>
            <tr><td>&nbsp;</td></tr>
            <tr>
              
              <td>
              	<div class="na_back_img" onclick="document.location.href='<%=basePath%>ea/costsheet/ea_getProjectDetailList.jspa?type=xmmx'">
              	<img src="<%=basePath%>images/sytemicon/r11_c5.gif"></img>
              	</div>
				<div class="center_a">项目明细列表</div>
              </td>
              <td>&nbsp;</td>
               <td>
              	<div class="na_back_img" onclick="document.location.href='<%=basePath%>ea/costsheet/ea_getProjectDetailList.jspa?type=srmx'">
              	<img src="<%=basePath%>images/sytemicon/r11_c5.gif"></img>
              	</div>
				<div class="center_a">项目收入明细列表</div>
              </td>
              <td>&nbsp;</td>
               <td>
              	<div class="na_back_img" onclick="document.location.href='<%=basePath%>ea/costsheet/ea_getProjectDetailList.jspa?type=zcmx'">
              	<img src="<%=basePath%>images/sytemicon/r11_c5.gif"></img>
              	</div>
				<div class="center_a">项目支出明细列表</div>
              </td>
              <td>&nbsp;</td>
              
              <td>
              	&nbsp;
              </td>
              
             
            </tr>
        </table>
        </td>
      </tr>
    </table>
	</body>
</html>
