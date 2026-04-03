<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        
        <meta http-equiv="cache-control" content="no-cache"/>
        <title>岗位职责打印预览</title>
        <%@ page language="java" pageEncoding="UTF-8" %>
        <%@ taglib uri="/struts-tags" prefix="s" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
        <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
        + request.getServerName() + ":" + request.getServerPort()
        + path + "/"; %>
        <script src="<%=basePath%>js/jquery.js" type="text/javascript">
        </script>
        <script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
        </script>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
        
        <script type="text/javascript">
        $(function(){
               // 取消按钮
               $("input.JQueryreturn").click(function(){
                   window.close(); 
                });
                // 打印预览
                $("input.JQueryPrint").click(function(){
                   $("#JQueryPrint").html(null);
                   window.print();
                   window.close(); 
                });
                //处理下拉框
                 $(".JQueryflexme").find("select").each(function(){
                    $s = $(this).hide()
                    $o = $("<span/>").text($s.find("option:selected").text())
                    $o.insertAfter($s)
                });
        })
       
        </script>
	</head>
	<body style="font-size:12px;">
<div class="contentbannb " style="width: 643px;top:10%" id="jqModel">
  <div class="content">
  <div class="contentbannb">
  	<div class="drag">岗位职责管理
    <div class="close"></div>
    </div>
  </div>
   <table width="643" height="826" border="1" align="center" cellpadding="0" cellspacing="0" class="JQueryflexme" id="stafftable" 
   style="margin-top:5px;margin-bottom:5px; table-layout:fixed;border-color: black;">
  <tr>
    <td width="90" height="31" align="right" style="width:90px;">员工编号：</td>
    <td width="140" style="overflow:hidden; text-overflow:ellipsis; width:140px;">${staffResponsibilities.staffCode}</td>
    <td width="110"align="right" style="width:110px;">员工姓名：</td>
    <td width="140" style="overflow:hidden; text-overflow:ellipsis;">${staffResponsibilities.staffName}</td>
    <td width="70"  rowspan="6" align="center"><img src="<%=basePath%>${result}" id="pic" width="70" height="110" /></td>
  </tr>
  <tr>
    <td height="36"  align="right">岗位职责编号：</td>
    <td style="overflow:hidden; text-overflow:ellipsis;">${staffResponsibilities.responsibilitiesNum}</td>
    <td  align="right">岗位职责档案编号：</td>
    <td style="overflow:hidden; text-overflow:ellipsis;">${staffResponsibilities.recordNum}</td>
    </tr>
  <tr>
    <td height="44" align="right">岗位起始时间：</td>
    <td style="overflow:hidden; text-overflow:ellipsis;width:140px;">${fn:substring(staffResponsibilities.startDate, 0, 10)}</td>
    <td align="right">岗位截止时间：</td>
    <td style="overflow:hidden; text-overflow:ellipsis;width:140px;">${fn:substring(staffResponsibilities.endDate, 0, 10)}</td>
    </tr>
  <tr>
    <td height="34" align="right">岗位名称：</td>
    <td style="overflow:hidden; text-overflow:ellipsis;">${staffResponsibilities.postName}</td>
    <td align="right">职务名称：</td>
    <td style="overflow:hidden; text-overflow:ellipsis;">${staffResponsibilities.dutyName}</td>
    </tr>
  <tr>
    <td height="30" align="right">岗位情况管理：</td>
    <td style="overflow:hidden; text-overflow:ellipsis;">${staffResponsibilities.postmanage}</td>
    <td align="right">文件号：</td>
    <td></td>
    </tr>
  <tr>
    <td height="37" align="right">工作单位名称：</td>
    <td style="overflow:hidden; text-overflow:ellipsis;">${staffResponsibilities.companyName}</td>
    <td align="right">部门名称：</td>
    <td><span>${staffResponsibilities.departmentIDName}</span></td>
    </tr>
  <tr>
    <td height="38" align="right">直接行政上级：</td>
    <td><span>${staffResponsibilities.organizationPIDName}</span></td>
    <td align="right">直接行政下级：</td>
      <td><span>${staffResponsibilities.organizationCIDName}</span></td>
    <td align="center">员工头像</td>
  </tr>
  <tr>
    <td height="103" align="right">管理范围：</td>
    <td colspan="4" style="overflow:hidden; text-overflow:ellipsis;height:50px;">${staffResponsibilities.managesCope}</td>
  </tr>
  <tr >
    <td height="109" align="right" >工作内容1：</td>
    <td colspan="4"   style="overflow:hidden; text-overflow:ellipsis;height:50px; ">${staffResponsibilities.jobContent1}</td>
    
  </tr>
  <tr>
    <td height="78" align="right">工作内容2：</td>
    <td colspan="4"  style="overflow:hidden; text-overflow:ellipsis;height:50px;">${staffResponsibilities.jobContent2}</td>
  </tr>
  <tr>
    <td height="94" align="right">工作内容3：</td>
    <td colspan="4"  style="overflow:hidden; text-overflow:ellipsis;height:50px;">${staffResponsibilities.jobContent3}</td>
  </tr>
  <tr>
    <td height="83" align="right">工作内容4：</td>
    <td colspan="4"   style="overflow:hidden; text-overflow:ellipsis;height:50px;">${staffResponsibilities.jobContent4}</td>
  </tr>
  <tr>
    <td height="84" align="right">工作内容5：</td>
    <td colspan="4"  style="overflow:hidden; text-overflow:ellipsis;height:50px;">${staffResponsibilities.jobContent5}</td>
  </tr>
  <tr>
    <td colspan="5" align="center" id="JQueryPrint">
      <input type="button"   class="input-button JQueryPrint" style="cursor:pointer;width:80px;" value="打印" />
      <input type="button"  class="input-button JQueryreturn" style="cursor:pointer;width:80px;"  value="取消" />
    </td>
  </tr>
</table>
</div>
</div>
</body>
</html>