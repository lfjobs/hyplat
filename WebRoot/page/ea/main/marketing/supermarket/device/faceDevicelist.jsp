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
        <title>刷脸设备管理</title>

        <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
        <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
        <script  src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js"></script>
        <script  src="<%=basePath%>js/tree/codebase/dhtmlxtree.js"></script>
        <script src="<%=basePath%>/js/ea/validate.js" type="text/javascript"></script>
        <link href="<%=basePath%>/css/ea/validate.css" rel="stylesheet"type="text/css" />
        <link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"
              type="text/css" />
        <link rel="stylesheet" type="text/css"
              href="<%=basePath%>css/admin_main111.css" />
        <script language="javascript" type="text/javascript" src="<%=basePath%>js/ea/marketing/supermarket/device/faceDevicelist.js"></script>
        <script language="javascript" type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
        <script type="text/javascript">
            var pfdID = "";
        	var search="${search}";
        	var basePath="<%=basePath%>";
        	var pNumber=${pageNumber};
        	var token=0;
            var notoken = 0;
            var companyID='<%=c.getCompanyID()%>';
            var companyName='<%=c.getCompanyName()%>';
            var deviceType = "${param.deviceType}";
        </script>
        <style type="text/css">
            input.input-button {
                margin: 4px;
            }
            .containerTableStyle {
                position: static;
                overflow: auto;
            }
            .text_tree{
                width:220px;
                background:#ffffff;
                overflow:auto;
            }
        </style>
    </head>
    <body>
        <form  name="searchForm" id="searchForm">
            <input type="submit" name="submit" style="display:none"/>
			<input  name="payDevice.sn" id="posNum" style="display:none"/>
            <input  name="payDevice.bindState" id="state" style="display:none"/>
            <input  name="payDevice.activeState" id="activeState" style="display:none"/>
            <input  name="deviceType" id="deviceType" value="${param.deviceType}" style="display:none"/>

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
                    <th width="90" align="center">
                        设备型号
                    </th>
                    <th width="200" align="center">
                      设备厂家
                    </th>
                    <th width="100" align="center">
                        设备来源
                    </th>
                    <th width="200" align="center">
                       设备类型
                    </th>
                    <th width="80" align="center">
                        商户绑定状态
                    </th>
                    <th width="80" align="center">
                        激活状态
                    </th>
                    <th width="140" align="center">
                        累计交易额
                    </th>
                     <th width="90" align="center">
                         累计返佣
                    </th>
                    <th width="180" align="center">
                        当前铺设业务员
                    </th>
                    <th width="180" align="center">
                        维护负责人
                    </th>
                    <th width="180" align="center">
                        录入时间
                    </th>

                </tr>
            </thead>
            <tbody>
             <s:iterator value="pageForm.list">
					<tr id="${pfdID}">
						<td>
							<input type="radio" name="a" class="JQuerypersonvalue"
								value="${pfdID}" />
						</td>
						<td>
                            <span id="pfdkey" style="display:none;">${pfdkey}</span>
                            <span id="pfdID" style="display:none;">${pfdID}</span>
							<span id="sn">${sn}</span>
						</td>
                        <td>

                            <span id="model">${model}</span>
                        </td>
                        <td>
                            <span id="facName">${factoryName}</span>
                            <span id="factoryID" style="display:none;" >${factoryID}</span>
                            <span id="factoryName"  style="display:none;">${factoryName}</span>
                        </td>
                        <td>
                            <span id="source" style="display: none;">${source}</span>
                            <c:choose>
                                <c:when test="${source=='00'}">采购</c:when>
                                <c:when test="${source=='01'}">自研</c:when>

                            </c:choose>
                        </td>
                        <td>
                            <span id="deviceType" style="display: none;">${deviceType}</span>
                            <c:choose>
                                <c:when test="${deviceType=='00'}">微信设备</c:when>
                                <c:when test="${deviceType=='01'}">支付宝设备</c:when>

                            </c:choose>
                        </td>
                        <td>
                            <span id="bindState" style="display: none;">${bindState}</span>
                            <c:choose>
                                <c:when test="${bindState=='00'}">未绑定</c:when>
                                <c:when test="${bindState=='01'}">已绑定</c:when>

                            </c:choose>
                        </td>
                        <td>
                            <span id="activeState" style="display: none;">${activeState}</span>
                            <c:choose>
                                <c:when test="${activeState=='00'}">未激活</c:when>
                                <c:when test="${activeState=='01'}">已激活</c:when>

                            </c:choose>
                        </td>
						<td>
							<span id="tradeMoney">${tradeMoney}</span>
						</td>
						<td>
							<span id="rakeMoney">${rakeMoney}</span>
						</td>
                        <td>
                            <span id="clName">${clerkName}<c:if test="${clearkAccount ne null&&clearkAccount ne ''}">(${clearkAccount})</c:if></span>
                            <span id="clerkID"style="display:none;">${clerkID}</span>
                            <span id="clerkName" style="display:none;">${clerkName}</span>
                            <span id="clearkAccount" style="display:none;">${clearkAccount}</span>
                        </td>

                        <td>
                            <span id="stname">${staffName}</span>
                            <span id="staffID"style="display:none;">${staffID}</span>
                            <span id="staffName" style="display:none;">${staffName}</span>
                        </td>

                        <td>
                            <span id="createDate">${fn:substring(createDate,0,19)}</span>
                        </td>
					</tr>
				</s:iterator>
            </tbody>
        </table>
        <c:import url="../../../../page_navigator.jsp">
            <c:param name="actionPath" value="ea/face/ea_getFaceDeviceList.jspa?search=${search}&pageNumber=${pageNumber}&deviceType=${param.deviceType}">
            </c:param>
        </c:import>
        <!--添加窗口 -->
        <form name="addForm" id="addForm" method="post">
            <div class="jqmWindow" style="width:450px;right: 30%;top:10%" id="jqModeladd">
                <input type="submit" name="submit" style="display:none"/>
                <div class="drag">
                    终端信息
                    <div class="close">
                    </div>
                </div>

                <table width="100%" id="addTable">
                    <tr>
                        <td align="right">设备序列号SN：</td>
                        <td>


                            <input id="sn"   name="payDevice.sn" class="posNum"  maxlength="50"/>
                            <input id="pfdkey"  type="text"  name="payDevice.pfdkey"  style="display: none;"/>
                            <input id="pfdID"  type="text"  name="payDevice.pfdID"   style="display: none;"/>
                            <input id="deviceType"  type="hidden"  name="payDevice.deviceType"  value="${param.deviceType}"/>

                        </td>

                    </tr>

                    <tr><td align="right">设备型号：</td>
                        <td>
                            <input id="model"  type="text"  name="payDevice.model" maxlength="50" />
                        </td>

                    </tr>
                    <tr>
                        <td align="right">设备厂家：</td>
                        <td><input id="facName"  type="text" class="put3 wanladw" readonly/>
                            <input id="factoryID"  type="text" name="payDevice.factoryID"   style="display: none;" />
                            <input id="factoryName"  type="text"  name="payDevice.factoryName"  style="display: none;" />

                        </td>
                    </tr>

                    <tr><td align="right">设备来源：</td>
                        <td>
                            <select  id = "source" name="payDevice.source"><option value="00">采购</option><option value="01">自研</option></select>
                        </td>
                    </tr>
                    <tr><td align="right">负责人：</td>

                        <td>
                            <input  id="stname" type="text" class="put3 wlgr" readonly/>
                            <input id="staffID"  type="hidden" name="payDevice.staffID"/>
                            <input id="staffName"  type="hidden" name="payDevice.staffName"/>

                        </td>
                    </tr>

                </table>
                <div align="center">
                    <input type="button" class="input-button" id="save" value=" 保存 "  style="margin: 10px;" />
                </div>
            </div>
        </form>
        <div style="display:none;width: 280px; padding:10px;height: 500px;z-index:3001 ; background-color:#e1ecfc; filter : Alpha(opacity=100);"
             id="serdevice">
            <div id="addtree" style="overflow:auto;height: 480px;background:#ffffff;width:100%;"></div>


        </div>
        <!--绑定业务员 -->
        <form name="bindForm" id="bindForm" method="post">
            <div class="jqmWindow" style="width:450px;right: 30%;top:10%" id="jqModelbind">
                <input type="submit" name="submit" style="display:none"/>
                <div class="drag">
                    绑定业务员
                    <div class="close">
                    </div>
                </div>

                <table width="100%" id="bindTable">
                    <tr>
                        <td align="right">设备序列号：</td>
                        <td>
                            <input id="sn"  type="text"  name="payDevice.sn"   readonly/>

                        </td>
                    </tr>
                    <tr>

                        <td align="right">微分金账号：</td>
                        <td>
                            <input   type="text"  name="payDevice.clearkAccount" maxlength="20" class="wfjac" />
                        </td>

                    </tr>


                </table>
                <div align="center">
                    <input type="button" class="input-button" id="saveBind" value=" 保存 "  style="margin: 10px;" />
                </div>
            </div>
        </form>
        <%------------------------------------选择往来个人------------------------------------%>

        <form name="selectuserForm" id="selectuserForm" method="post"
              enctype="multipart/form-data">
            <input type="submit" name="submit" style="display: none" />
            <input type="submit" name="submit" style="display: none" />
            <div class="jqmWindow jqmWindowcss1" style="top: 5%; left: 53%;"
                 id="grjqModel">
                <div class="content1" style="width: 100%; height: 400px;">
                    <div class="contentbannb">
                        <div class="drag">
                            往来个人
                        </div>
                    </div>
                    <table width="99%" height="33" id="searchuser"  border="0"
                           align="center" cellpadding="0" cellspacing="0"
                           style="margin-top: 5px; background: #FFFFFF;">
                        <tr>
                            <td width="100" align="right">
                                姓名：
                            </td>
                            <td width="142">
                                <input name="contactUserID" class="input" id="contactUserID"
                                       size="10" style="margin-left: 2px;" />
                            </td>
                            <td height="33">
                                <input type="button" class="btn02" id="searchuu" name="button7"
                                       value="查询" />
                                <input type="button" class="btn02" id="qduser" name="button5"
                                       value="确定" />
                                <input type="button" class="btn02 xzgr" name="button5"
                                       value="新增" />
                                <input type="button" class="btn02 closewr" name="button4"
                                       value="关闭" />
                                <input type="hidden" name="parms" id="grparms" />

                            </td>
                            <td width="80">
                                <a id="grsy" title="0">上一页</a>
                            </td>
                            <td width="80">
                                <a id="grxy" title="0">下一页</a>
                            </td>
                            <td width="100">
                                <a id="grzy">共&nbsp;&nbsp; <span style="color: red"
                                                                 id="grzycount"></span>&nbsp;&nbsp;页 </a>
                            </td>
                        </tr>
                    </table>
                    <table width="99%" border="0" align="center" cellpadding="0"
                           cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
                        <tr>
                            <td width="16%">
                                <table width="100%" cellpadding="0" cellspacing="0">
                                    <tr id="menuTreeTrid-1" sizcache="1" sizset="0">
                                        <td>
                                            <div id="grTree" class="text_tree"
                                                 style="overflow: scroll; z-index: 99; height: 320px;"></div>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                            <td width="83%" valign="top" align="left">
                                <div id="body_02cu"
                                     style="margin-top: 2px; display: none; height: 310px; width: 100%; overflow: auto;">
                                </div>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
            <s:token></s:token>
        </form>

        <%------------------------------------选择往来单位------------------------------------%>
        <form name="selectcompanyForm" id="selectcompanyForm" method="post"
              enctype="multipart/form-data">
            <input type="submit" name="submit" style="display: none" />
            <input type="submit" name="submit" style="display: none" />
            <div class="jqmWindow jqmWindowcss1" style="top: 5%; left: 53%;"
                 id="companyjqModel">
                <div class="content1" style="width: 100%; height: 400px;">
                    <div class="contentbannb">
                        <div class="drag">
                            往来单位
                        </div>
                    </div>
                    <table width="99%" height="33" id="searchcompany" border="0"
                           align="center" cellpadding="0" cellspacing="0"
                           style="margin-top: 5px; background: #FFFFFF;">
                        <tr>
                            <td width="100" align="right">
                                单位名称：
                            </td>
                            <td width="142">
                                <input name="ccompanyID" class="input" id="ccompanyIDs" size="10"
                                       style="margin-left: 2px;" />
                            </td>
                            <td height="33">
                                <input type="button" class="btn02" id="searchcc" name="button7"
                                       value="查询" />
                                <input type="button" class="btn02" id="qdcompany" name="button5"
                                       value="确定" />
                                <input type="button" class="btn02 xzdw" name="button" value="新增" />
                                <input type="button" class="btn02 JQueryreturns" name="button4"
                                       value="关闭" />
                                <input type="hidden" name="parms" id="dwparms" />

                            </td>
                            <td width="80">
                                <a id="dwsy" title="0">上一页</a>
                            </td>
                            <td width="80">
                                <a id="dwxy" title="0">下一页</a>
                            </td>
                            <td width="100">
                                <a id="dwzy">共&nbsp;&nbsp; <span style="color: red"
                                                                 id="dwzycount"></span>&nbsp;&nbsp;页 </a>
                            </td>
                        </tr>
                    </table>
                    <table width="99%" border="0" align="center" cellpadding="0"
                           cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
                        <tr>
                            <td width="16%">
                                <table width="100%" cellpadding="0" cellspacing="0">
                                    <tr id="menuTreeTrid-1" sizcache="1" sizset="0">
                                        <td>
                                            <div id="dwTree" class="text_tree"
                                                 style="overflow: scroll; z-index: 99; height: 320px;"></div>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                            <td width="83%" valign="top" align="left">
                                <div id="body_02cc"
                                     style="margin-top: 2px; display: none; height: 310px; width: 100%; overflow: auto;">
                                </div>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
            <s:token></s:token>
        </form>



        <iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
    </body>
</html>
