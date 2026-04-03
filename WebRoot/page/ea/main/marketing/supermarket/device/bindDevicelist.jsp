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
        <title>终端分配</title>

        <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
        <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>

        <script src="<%=basePath%>/js/ea/validate.js" type="text/javascript"></script>
        <link href="<%=basePath%>/css/ea/validate.css" rel="stylesheet"type="text/css" />

        <script language="javascript" type="text/javascript" src="<%=basePath%>js/ea/marketing/supermarket/device/bindDevicelist.js"></script>
        <script language="javascript" type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
        <script type="text/javascript">
            var pfdID = "";
        	var search="${search}";
        	var basePath="<%=basePath%>";
        	var pNumber=${pageNumber};
        	var token=0;
            var storeName = "${bindDevice.storeName}";
            var subCompanyID = "${bindDevice.subCompanyID}";
        </script>
    </head>
    <body>
        <form  name="searchForm" id="searchForm">
            <input type="submit" name="submit" style="display:none"/>
			<input  name="bdevice.sn" id="posNum" style="display:none"/>
            <input  name="bindDevice.subCompanyID" id="subCompanyID" value="${bindDevice.subCompanyID}" type="hidden" />
            <input   name="bindDevice.storeName" id="storeName" type="hidden"  value="${bindDevice.storeName}"/>

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
                       设备序列号SN
                    </th>
                    <th width="180" align="center">
                        业务员
                    </th>
                    <th width="180" align="center">
                        绑定人姓名
                    </th>
                    <th width="180" align="center">
                        绑定时间
                    </th>

                </tr>

            </thead>
            <tbody>
             <c:forEach items="${pageForm.list}" var="item">
					<tr id="${item[0]}">
						<td>
							<input type="radio" name="a" class="JQuerypersonvalue"
								value="${item[0]}" />
						</td>

                        <td>
                            <span id="sn">${item[1]}</span>
                        </td>

                        <td>

                            <span id="clerkName">${item[4]}</span>
                        </td>
                        <td>

                            <span id="bindName">${item[2]}</span>
                        </td>

                        <td>
                            <span id="bindDate">${fn:substring(item[3],0,19)}</span>
                        </td>

					</tr>
				</c:forEach>
            </tbody>
        </table>
        <c:import url="../../../../page_navigator.jsp">
            <c:param name="actionPath" value="ea/face/ea_getStoreAllDevice.jspa?search=${search}&pageNumber=${pageNumber}&bindDevice.storeName=${bindDevice.storeName}&bindDevice.subCompanyID=${bindDevice.subCompanyID}">
            </c:param>
        </c:import>
        <!--添加窗口 -->
        <form name="addForm" id="addForm" method="post">
            <div class="jqmWindow" style="width:450px;right: 30%;top:10%" id="jqModeladd">
                <input type="submit" name="submit" style="display:none"/>
                <div class="drag">
                    添加设备
                    <div class="close">
                    </div>
                </div>

                <table width="100%" id="addTable">
                     <tr>
                         <td align="right">商户名称：</td>
                         <td><input id="storeName"  type="text" readonly  value="${bindDevice.storeName}"/>
                             <input id="subCompanyID"  name="bdevice.subCompanyID" type="text" value="${bindDevice.subCompanyID}" style="display: none;"/>


                         </td>
                    </tr>

                    <tr><td align="right">设备序列号：</td>
                        <td>
                            <input id="sn"  type="text"  name="bdevice.sn" maxlength="50" class="posNum" />
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
