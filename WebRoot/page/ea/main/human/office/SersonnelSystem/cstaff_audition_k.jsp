<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>口试管理</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script type="text/javascript" >
   var token = 0;
   var auditionID = '';
   var basePath = '<%=basePath%>';
   var notoken = 0;
   var start='${start}';
   var staffID = '';
</script>
<script src="<%=basePath%>js/ea/human/office/SersonnelSystem/cstaff_audition_all.js"></script>
</head>
<body>
<form  name="addressForm" id="addressForm" method="post"><s:token></s:token><input type="submit" name="submit" style="display:none"/>

  <div class="main_main">
            <table class="JQueryflexme">
                <thead>
                    <tr class="tablewith">
                        <th width="40" align="center">
                            选择
                        </th>
                         <th width="80" align="center">
                            状态
                        </th>
                        <th width="80" align="center">
                            人员姓名
                        </th>
                        <th width="150" align="center">
                            身份证
                        </th>
                        <th width="100" align="center">
                            应聘方向
                        </th>
                        <th width="100" align="center">
                           应聘岗位
                        </th>
                        <th width="150" align="center">
                            工作经验
                        </th>
                         <th width="100" align="center">
                            面试地点
                        </th>
                        <th width="100" align="center">
                            面试考官
                        </th>
                        <th width="80" align="center">
                           面试时间
                        </th>
                        <th width="100" align="center">
                          面试部门
                        </th>
                      
                    </tr>
                </thead>
                <tbody>
                	<s:if test="audition != null">
                        <tr id="${audition.auditionID}">
                            <td>
                                <input type="radio" name="a" class="JQueryaudition" value="${audition.auditionID}"/>
                            </td>
                              <td>
                                 <span id="status">
                                 <s:if test="audition.status == 10">
                               		  待口试
                                 </s:if>	
                                  <s:if test="audition.status == 11">
                               		  待笔试
                                 </s:if>
                                 </span>
                                <span style="display:none" id="auditionID">${audition.auditionID}</span>
                                <span style="display:none" id="auditionKey">${audition.auditionKey}</span>
                                <input style="display:none" id="staffID" value="${audition.staffID}"></input>
                                 
                            </td>
                            <td>
                                <span id="staffName">${audition.staffName}</span>
                            </td>
                            <td>
                                <span id="staffIdentityCard">${audition.staffIdentityCard}</span>
                            </td>
                            <td>
                                <span id="auditionDirection">${audition.auditionDirection}</span>
                            </td>
                            <td>
                                <span id="auditionPost" >${audition.auditionPost}</span>
                            </td>
                            <td>
                                <span id="experience">${audition.experience}</span>
                            </td>
                            
                            <td>
                                <span id="place">${audition.place}</span>
                            </td>
                            <td>
                                <span id="examiner">${audition.examiner}</span>
                            </td>
                            <td>
                                <span id="auditionDate">${fn:substring(audition.auditionDate, 0, 10)}</span>
                            </td>
                            <td>
                                <span id="auditionDept">${audition.auditionDept}</span>
                            </td>
                          
                        </tr>
                        </s:if>
                </tbody>
            </table>
</div>
</form>
<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
</body>
</html>
