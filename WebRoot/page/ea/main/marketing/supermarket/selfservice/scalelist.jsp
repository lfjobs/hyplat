<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
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
        <title>电子称管理</title>

        <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
        <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
        <script src="<%=basePath%>/js/ea/validate.js" type="text/javascript"></script>
        <link href="<%=basePath%>/css/ea/validate.css" rel="stylesheet"type="text/css" />
        <script language="javascript" type="text/javascript" src="<%=basePath%>js/ea/marketing/supermarket/scalelist.js"></script>
        <script language="javascript" type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
        <script type="text/javascript">
            var scId = "";
        	var search="${search}";
        	var basePath="<%=basePath%>";
        	var pNumber=${pageNumber};
        	var token=0;

        </script>
        <style type="text/css">
            input.input-button {
                margin: 4px;
            }

        </style>
    </head>
    <body>
        <form  name="searchForm" id="searchForm">
            <input type="submit" name="submit" style="display:none"/>
			<input  name="scale.scaleNo" id="caleNo" style="display:none"/>
			<input  name="scale.address" id="address" style="display:none"/>
            <input type="hidden" name="search" value="search" />

			<s:token/>
        </form>
        <table class="flexme11">
            <thead>
                <tr>
                    <th width="30" align="center">
                        选择
                    </th>
                      <th width="80" align="center">
                        设备号
                    </th>
                   <th width="80" align="center">
                       称号
                    </th>
                    <th width="100" align="center">
                       称型号
                    </th>
                    <th width="100" align="center">
                        通讯类型
                    </th>
                    <th width="150" align="center">
                        IP地址
                    </th>
                    <th width="80" align="center">
                        端口号
                    </th>
                    <th width="80" align="center">
                       金额位数
                    </th>
                     <th width="90" align="center">
                       责任人
                    </th>
                    <th width="140" align="center">
                        日期
                    </th>

                </tr>
            </thead>
            <tbody>
             <s:iterator value="pageForm.list">
					<tr id="${scId}">
						<td>
							<input type="radio" name="a" class="JQuerypersonvalue"
								value="${scId}" />
						</td>
						<td>
                            <span id="scKey" style="display:none;">${scKey}</span>
                            <span id="scId" style="display:none;">${scId}</span>
							<span id="deviceID">${deviceID}</span>
						</td>
						<td>
							<span id="scaleNo">${scaleNo}</span>
						</td>
						<td>
							<span id="scaleType">${scaleType}</span>
						</td>
						<td>
							<span id="connectType">${connectType}</span>
						</td>
						<td>
							<span id="address">${address}</span>
						</td>
						<td>
							<span id="port">${port}</span>
						</td>
                        <td>
                            <span id="decimalDigits">${decimalDigits}</span>
                        </td>
                        <td>
                            <span id="staffname">${staffname}</span>
                        </td>
                        <td>
                            <span id="cdate">${fn:substring(cdate,0,19)}</span>
                        </td>

					</tr>
				</s:iterator>
            </tbody>
        </table>
        <c:import url="../../../../page_navigator.jsp">
            <c:param name="actionPath" value="ea/scale/ea_getScaleList.jspa?search=${search}&pageNumber=${pageNumber}">
            </c:param>
        </c:import>
        <!--添加窗口 -->
        <form name="addForm" id="addForm" method="post">
            <div class="jqmWindow" style="width:550px;right: 30%;top:10%" id="jqModeladd">
                <input type="submit" name="submit" style="display:none"/>
                <div class="drag">
                    电子秤信息
                    <div class="close">
                    </div>
                </div>

                <table width="100%" id="addTable">
                    <tr>
                        <td align="right">设备号：</td>
                        <td><input id="deviceID"  style="width:195px"  name="scale.deviceID" class="put3"  maxlength="50"/>
                            <input id="scKey"  type="hidden"  name="scale.scKey" />
                            <input id="scId"  type="hidden"  name="scale.scId" />
                        </td>

                    </tr>
                    <tr>
                        <td align="right">秤号：</td>
                        <td><input id="scaleNo"  style="width:195px"  name="scale.scaleNo"  class="put3"/></td>
                    </tr>
                    <tr><td align="right">通讯类型：</td>
                        <td>
                            <select id="connectType"  name="scale.connectType"><option value="Network">Network</option><option value="Internet">Internet</option></select>
                        </td>

                    </tr>
                    <tr>
                        <td align="right">秤类型：</td>
                        <td>
                            <select id="scaleType"  name="scale.scaleType"><option value="RL00">RL00</option><option value="bCom">bCom</option><option value="bPlus">bPlus</option></select>
                        </td>
                    </tr>
                    <tr><td align="right">秤IP：</td>
                        <td>
                            <input id="address"  style="width:195px"  name="scale.address"  class="put3" maxlength="20"/>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">端口号：</td>
                        <td>
                            <input id="port"  style="width:195px"  name="scale.port"  value="3001"  class="put3" maxlength="4"/>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">小数点位数：</td>
                        <td>
                            <input id="decimalDigits"  style="width:195px"  name="scale.decimalDigits" value="2"   class="put3 posnum" maxlength="1"/>
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
