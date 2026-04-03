<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
        <%@ page language="java" pageEncoding="UTF-8" %>
        <%@ taglib uri="/struts-tags" prefix="s" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
         <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
        <%@ page import="hy.ea.bo.Company"%>

        <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
        + request.getServerName() + ":" + request.getServerPort()
        + path + "/";
            Company c = (Company)session.getAttribute("currentcompany");
        %>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="cache-control" content="no-cache"/>
        <title>人脸闸机设备管理</title>

        <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
        <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>

        <script src="<%=basePath%>/js/ea/validate.js" type="text/javascript"></script>
        <link href="<%=basePath%>/css/ea/validate.css" rel="stylesheet"type="text/css" />
        <script language="javascript" type="text/javascript" src="<%=basePath%>js/ea/office_ea/carmanage/faceRecEquiplist.js"></script>
        <script language="javascript" type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
        <script type="text/javascript">
            var frId = "";
        	var search="${search}";
        	var basePath="<%=basePath%>";
        	var pNumber = ${pageNumber};
        	var token = 0;

        </script>

    </head>
    <body>
        <form  name="searchForm" id="searchForm">
            <input type="submit" name="submit" style="display:none"/>
			<input  name="faceRec.sn" id="posNum" style="display:none"/>

            <input type="hidden" name="search" value="search" />

			<s:token/>
        </form>

        <table class="flexme11">
            <thead>
                <tr>
                    <th width="30" align="center">
                        选择
                    </th>
                      <th width="200" align="center">
                        设备号
                    </th>
                    <th width="100" align="center">
                        设备名称
                    </th>
                    <th width="80" align="center">
                        录入人员
                    </th>
                    <th width="140" align="center">
                       录入时间
                    </th>
                     <th width="90" align="center">
                      使用状态
                    </th>
                    <th width="90" align="center">
                       在线状态
                    </th>
                </tr>
            </thead>
            <tbody>
             <s:iterator value="pageForm.list">
					<tr id="${frId}">
						<td>
							<input type="radio" name="a" class="JQuerypersonvalue"
								value="${frId}" />
						</td>
						<td>
                            <span id="frkey" style="display:none;">${frkey}</span>
                            <span id="frId" style="display:none;">${frId}</span>
                            <span id="sn">${sn}</span>

                        </td>
                        <td>
                            <span id="name">${name}</span>
                        </td>


						<td>
							<span id="staffName">${staffName}</span>
						</td>
                        <td>
                            <span id="createDate">${fn:substring(createDate,0,19)}</span>
                        </td>

                        <td>
							<span id="state" style="display: none;">${state}</span>
                            <c:choose>
                                <c:when test="${state=='0'}">使用中</c:when>
                                <c:when test="${state=='1'}">已停用</c:when>

                            </c:choose>
						</td>
                        <td>
                            <span id="onlineState">${onlineState}</span>
                        </td>
					</tr>
				</s:iterator>
            </tbody>
        </table>
        <c:import url="../../../page_navigator.jsp">
            <c:param name="actionPath" value="ea/facerec/ea_getListFaceRec.jspa?search=${search}&pageNumber=${pageNumber}">
            </c:param>
        </c:import>
        <!--添加窗口 -->
        <form name="addForm" id="addForm" method="post">
            <div class="jqmWindow" style="width:550px;right: 30%;top:10%" id="jqModeladd">
                <input type="submit" name="submit" style="display:none"/>
                <div class="drag">
                    人脸闸机
                    <div class="close">
                    </div>
                </div>

                <table width="100%" id="addTable">
                    <tr>
                        <td align="right">设备号：</td>
                        <td><input id="sn"  style="width:195px"  name="faceRec.sn" class="posNum"  maxlength="50"/>
                            <input id="frkey"  type="text"  name="faceRec.frkey"  style="display:none;"/>
                            <input id="frId"  type="text"  name="faceRec.frId" style="display:none;"/>
                        </td>

                    </tr>

                    <tr><td align="right">设备名称：</td>
                        <td>
                            <input id="name"  type="text"  name="faceRec.name" />
                        </td>

                    </tr>

                    <tr><td align="right">使用状态：</td>
                        <td>
                            <select  id = "state" name="faceRec.state"><option value="0">使用中</option><option value="1">已停用</option></select>
                        </td>

                    </tr>

                </table>
                <div align="center">
                    <input type="button" class="input-button" id="save" value=" 保存 "  style="margin: 10px;" />
                </div>
            </div>
        </form>



        <iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
    </body>
</html>
