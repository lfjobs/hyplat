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
        <script  src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js"></script>
        <script  src="<%=basePath%>js/tree/codebase/dhtmlxtree.js"></script>
        <script src="<%=basePath%>/js/ea/validate.js" type="text/javascript"></script>
        <link href="<%=basePath%>/css/ea/validate.css" rel="stylesheet"type="text/css" />
        <link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"
              type="text/css" />
        <link rel="stylesheet" type="text/css"
              href="<%=basePath%>css/admin_main111.css" />
        <script language="javascript" type="text/javascript" src="<%=basePath%>js/ea/marketing/supermarket/device/storeBindlist.js"></script>
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
			<input  name="bindDevice.storeName" id="storeName" style="display:none"/>
            <input  name="bindDevice.subAppID" id="subAppID" style="display:none"/>
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
                       商户名称
                    </th>
                      <th width="200" align="center">
                       微信商户APPID
                    </th>
                    <th width="100" align="center">
                        微信商户号
                    </th>
                    <th width="180" align="center">
                       费率
                    </th>
                    <th width="180" align="center">
                        负责人
                    </th>

                    <th width="180" align="center">
                        录入时间
                    </th>

                </tr>

            </thead>
            <tbody>
             <s:iterator value="pageForm.list">
					<tr id="${sbdID}">
						<td>
							<input type="radio" name="a" class="JQuerypersonvalue"
								value="${sbdID}" />
						</td>
						<td>
                            <span id="sbdKey" style="display:none;">${sbdKey}</span>
                            <span id="sbdID" style="display:none;">${sbdID}</span>

							<span id="storeName">${storeName}</span>
                            <span id="subCompanyID" style="display:none;">${subCompanyID}</span>
                            <span id="storeID" style="display:none;">${subCompanyID}</span>



                        </td>
                        <td>

                        <span id="subAppID">${subAppID}</span>
                    </td>

                        <td>

                            <span id="subMchid">${subMchid}</span>
                        </td>
                        <td>

                            <span id="storeRate">${storeRate}</span>
                        </td>
                        <td>

                            <span id="staffID" style="display: none;">${staffID}</span>
                            <span id="staffName">${staffName}</span>
                            <span id="stname" style="display: none;">${staffName}</span>
                        </td>
                        <td>
                            <span id="createDate">${fn:substring(createDate,0,19)}</span>
                        </td>

					</tr>
				</s:iterator>
            </tbody>
        </table>
        <c:import url="../../../../page_navigator.jsp">
            <c:param name="actionPath" value="ea/face/ea_getStoreBindList.jspa?search=${search}&pageNumber=${pageNumber}&deviceType=${param.deviceType}">
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
                         <td align="right">商户名称：</td>
                         <td><input id="storeName"  type="text"  name="bindDevice.storeName"  class="wanladw comNum"/>
                             <input id="subCompanyID"  name="bindDevice.subCompanyID" type="text" style="display:none;"/>
                             <input id="storeID"  name="bindDevice.storeID" type="text" style="display:none;"/>
                             <input id="sbdID"  name="bindDevice.sbdID" type="text" style="display:none;"/>
                             <input id="sbdKey"  name="bindDevice.sbdKey" type="text" style="display:none;"/>


                         </td>
                    </tr>
                    <tr><td align="right">微信公众号APPID：</td>
                        <td>
                            <input id="subAppID"  type="text"  name="bindDevice.subAppID" maxlength="50" />
                        </td>

                    </tr>
                    <tr><td align="right">商户号mchid：</td>
                        <td>
                            <input id="subMchid"  type="text"  name="bindDevice.subMchid" maxlength="50" />
                        </td>

                    </tr>
                    <tr><td align="right">负责人：</td>
                        <td>
                            <input  id="stname" type="text" class="put3 wlgr" readonly/>
                            <input id="staffID"  type="text" name="bindDevice.staffID" style="display:none;"/>
                            <input id="staffName"  type="text" name="bindDevice.staffName" style="display:none;"/>
                        </td>
                    </tr>

                </table>
                <div align="center">
                    <input type="button" class="input-button" id="save" value=" 保存 "  style="margin: 10px;" />
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

        <%------------------------------------选择商户------------------------------------%>
        <form name="selectcompanyForm" id="selectcompanyForm" method="post"
              enctype="multipart/form-data">
            <input type="submit" name="submit" style="display: none" />
            <input type="submit" name="submit" style="display: none" />
            <div class="jqmWindow jqmWindowcss1" style="top: 5%; left: 53%;"
                 id="companyjqModel">
                <div class="content1" style="width: 100%; height: 400px;">
                    <div class="contentbannb">
                        <div class="drag">
                            商户
                        </div>
                    </div>
                    <table width="99%" height="33" id="searchcompany" border="0"
                           align="center" cellpadding="0" cellspacing="0"
                           style="margin-top: 5px; background: #FFFFFF;">
                        <tr>
                            <td width="100" align="right">
                                商户名称：
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

        <!--添加窗口 -->
        <form name="rateForm" id="rateForm" method="post">
            <div class="jqmWindow" style="width:450px;right: 30%;top:10%" id="jqModelrate">
                <input type="submit" name="submit" style="display:none"/>
                <div class="drag">
                    调整费率
                    <div class="close">
                    </div>
                </div>

                <table width="100%" id="rateTable">
                    <tr>
                        <td align="right">商户名称：</td>
                        <td><input id="storeName"  type="text" readonly />
                            <input id="subCompanyID"  name="bindDevice.subCompanyID" type="hidden" />


                        </td>
                    </tr>

                    <tr><td align="right">费率：</td>
                        <td>
                            <input id="storeRate"  type="text"  name="bindDevice.storeRate" maxlength="50"  class="put3" />
                        </td>

                    </tr>

                </table>
                <div align="center">
                    <input type="button" class="input-button" id="saveRate" value=" 保存 "  style="margin: 10px;" />
                </div>
            </div>
        </form>

        <iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
    </body>
</html>
