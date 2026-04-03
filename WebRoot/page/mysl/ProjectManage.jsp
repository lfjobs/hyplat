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
        <title>项目管理</title>
        <script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
        <script src="<%=basePath%>js/jquery.js" type="text/javascript">
        </script>
        <script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
        </script>
        <script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
        <link rel="stylesheet" type="text/css" href="<%=basePath %>css/ea/validate.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
        <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js">
        </script>
         <script type="text/javascript" src="<%=basePath %>js/ea/mysl/ProjectManage.js"></script>
        <script>
            var basePath = "<%=basePath%>";
            var proid = "";
            var search="${search}";
            var ppageNumber = ${pageNumber};
            var token=0;
</script>
	</head>
	<body >
	<form name="projectForm" id="projectForm" method="post"
			enctype="multipart/form-data">
	<input type="submit" name="submit" style="display: none" />
	</form>
        <div class="main_main">
            <table class="JQueryflexme">
                <thead>
                    <tr class="tablewith">
                        <th width="40" align="center">
                            请选择
                        </th>
                         <th width="50" align="center">
                            序号
                        </th>
                        <th width="200" align="center">
                          项目编号
                        </th>
                        <th width="120" align="center">
                           项目名称
                        </th>
                        <th width="120" align="center">
                            最后操作人
                        </th>
                        <th width="120" align="center">
                            往来单位
                        </th>
                        <th width="120" align="center">
                            启动时间
                        </th>
                        <th width="120" align="center">
                            计划完成时间
                        </th>
                        <th width="120" align="center">
                            实际完成时间
                        </th>
                        <th width="120" align="center">
                            项目简介
                        </th>
                </thead>
                <tbody>
                    <%
                    int number = 1; %>
                    <s:iterator value="pageForm.list">
                        <tr id="${proid}"  >
                            <td>
                                <input type="radio" name="a"  class="JQuerypersonvalue" value="${proid}"/>
                            </td>
                            <td>
                                <span ><%=number%></span>
                            </td>
                            <td>
                                <span id="procode">${procode}</span>
                            </td>
                            <td>
                                <span id="proname">${proname}</span>
                            </td>
                            <td>
                                <span id="straffName">${straffName}</span>
                            </td>
                            <td>
                                <span id="contactcompany">${contactcompany}</span>
                            </td>
                            <td>
                                <span id="startdate">${fn:substring(startdate,0,10)}</span>
                            </td>
                            <td>
                                <span id="planfinishdate">${fn:substring(planfinishdate,0,10)}</span>
                            </td>
                            <td>
                                <span id="factfinishdate">${fn:substring(factfinishdate,0,10)}</span>
                            </td>
                            <td>
                                <span id="probrief">${probrief}</span>
                                <span id="proid" style="display:none">${proid}</span>
                                <span id="prokey"  style="display:none">${prokey}</span>
                            </td>
                        </tr>
                        <% 
                        number++; %>
                    </s:iterator>
                </tbody>
            </table>
            <c:import url="../ea/page_navigator.jsp">
                <c:param name="actionPath" value="ea/projectmanager/ea_getDtMyprojectList.jspa?pageNumber=${pageNumber}">
                </c:param>
            </c:import>
        </div>
         <!--搜索窗口 -->
		<div class="jqmWindow" style="width: 400px; right: 40%;; top: 10%"
			id="jqModelSearch">
			<form name="postSearchForm" id="postSearchForm" method="post">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table width="396" id="cataffSearchTable">
					<tr>
						<td width="123" align="right">
							项目编号：
						</td>
						<td width="261">
							<input name="project.procode" class="ckTextLength" maxlength="14" id="procode"/>
						</td>
					</tr>
					<tr>
						<td align="right">
							项目名称：
						</td>
						<td>
						    <input name="project.proname" class="ckTextLength" maxlength="40" id="proname"/>
						</td>
					</tr>
					<tr>
						<td align="right">
							往来单位：
						</td>
						<td>
						    <input name="project.contactcompany" class="ckTextLength" maxlength="40" id="contactcompany"/>
						</td>
					</tr>
					<tr>
						<td align="right">
							启动时间：
						</td>
						<td>
						    <input name="project.startdate" id="startdate" style="width:200px;height:100%;" onfocus="date(this)"/>
						</td>
					</tr>
                    <tr>
						<td align="right">
							计划完成时间：
						</td>
						<td>
						    <input name="project.planfinishdate" id="planfinishdate" style="width:200px;height:100%;" onfocus="date(this)"/>
						</td>
					</tr>
					<tr>
						<td align="right">
							最后操作人：
						</td>
						<td>
						    <input name="project.straffName" class="ckTextLength" maxlength="20" id="straffName"/>
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="tosearch"
						value=" 查询 " />
					<input name="search" type="hidden" value="search" />
				</div>
			</form>
		</div>
        <iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
    </body>
</html>