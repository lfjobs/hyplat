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
        <title>POS收银设备管理</title>

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
        <script language="javascript" type="text/javascript" src="<%=basePath%>js/ea/marketing/supermarket/posDevicelist.js"></script>
        <script language="javascript" type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
        <script type="text/javascript">
            var posID = "";
        	var search="${search}";
        	var basePath="<%=basePath%>";
        	var pNumber=${pageNumber};
        	var token=0;
            var notoken = 0;
            var companyID='<%=c.getCompanyID()%>';
            var companyName='<%=c.getCompanyName()%>';
       
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
			<input  name="posDevice.posNum" id="posNum" style="display:none"/>
			<input  name="posDevice.organizationName" id="organizationName" style="display:none"/>
            <input  name="posDevice.state" id="state" style="display:none"/>

            <input type="hidden" name="search" value="search" />

			<s:token/>
        </form>

        <table class="flexme11">
            <thead>
                <tr>
                    <th width="30" align="center">
                        选择
                    </th>
                      <th width="100" align="center">
                        设备号
                    </th>
                    <th width="90" align="center">
                        设备名称
                    </th>
                    <th width="100" align="center">
                      投放区域
                    </th>
                    <th width="100" align="center">
                        负责人
                    </th>
                    <th width="200" align="center">
                       服务公司
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
                    <th width="180" align="center">
                        发货形式
                    </th>
                    <th width="180" align="center">
                       默认公司网站
                    </th>

                </tr>
            </thead>
            <tbody>
             <s:iterator value="pageForm.list">
					<tr id="${posID}">
						<td>
							<input type="radio" name="a" class="JQuerypersonvalue"
								value="${posID}" />
						</td>
						<td>
                            <span id="posKey" style="display:none;">${posKey}</span>
                            <span id="posID" style="display:none;">${posID}</span>
							<span id="posNum">${posNum}</span>
						</td>
                        <td>
                            <span id="posName" style="display: none;">${posName}</span>
                            <c:choose>
                                <c:when test="${posName=='01'}">互视达销售终端机</c:when>
                                <c:when test="${posName=='02'}">红外线手持机</c:when>
                                <c:when test="${posName=='03'}">非红外线手持机</c:when>
                                <c:when test="${posName=='04'}">平板电脑</c:when>
                                <c:otherwise>无</c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <span id="orgName">${organizationName}</span>
                            <span id="organizationID" style="display:none;" >${organizationID}</span>
							<span id="organizationName"  style="display:none;">${organizationName}</span>
						</td>
						<td>
                            <span id="stname">${staffName}</span>
                            <span id="staffID"style="display:none;">${staffID}</span>
							<span id="staffName" style="display:none;">${staffName}</span>
						</td>
						<td>
							<span id="comName">${comName}</span>
						</td>
						<td>
							<span id="inputName">${inputName}</span>
						</td>
                        <td>
                            <span id="createDate">${fn:substring(createDate,0,19)}</span>
                        </td>

                        <td>
							<span id="state" style="display: none;">${state}</span>
                            <c:choose>
                                <c:when test="${state=='0'}">使用中</c:when>
                                <c:when test="${state=='1'}">已停用</c:when>
                                <c:otherwise>已回收</c:otherwise>
                            </c:choose>
						</td>
                        <td>
                            <span id="fhform" style="display: none;">${fhform}</span>
                            <c:choose>
                                <c:when test="${fhform=='0'}">全部商家送货(或虚拟产品)</c:when>
                                <c:when test="${fhform=='1'}">商家送货+现场拿货</c:when>
                                <c:otherwise>仅现场拿货</c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <span id="accessCcomName">${accessCcomName}</span>
                            <span id="accessCcomID"style="display:none;">${accessCcomID}</span>
                        </td>
                        <td>
                            <span id="app" style="display: none;">${app}</span>
                            <c:choose>
                                <c:when test="${app==''}">不指定</c:when>
                                <c:when test="${app=='01'}">考场练车</c:when>
                            </c:choose>
                        </td>
					</tr>
				</s:iterator>
            </tbody>
        </table>
        <c:import url="../../../../page_navigator.jsp">
            <c:param name="actionPath" value="ea/pos/ea_getPosList.jspa?search=${search}&pageNumber=${pageNumber}">
            </c:param>
        </c:import>
        <!--添加窗口 -->
        <form name="addForm" id="addForm" method="post">
            <div class="jqmWindow" style="width:550px;right: 30%;top:10%" id="jqModeladd">
                <input type="submit" name="submit" style="display:none"/>
                <div class="drag">
                    POS销售终端
                    <div class="close">
                    </div>
                </div>

                <table width="100%" id="addTable">
                    <tr>
                        <td align="right">设备号：</td>
                        <td><input id="posNum"  style="width:195px"  name="posDevice.posNum" class="posNum"  maxlength="50"/>
                            <input id="posKey"  type="hidden"  name="posDevice.posKey" />
                            <input id="posID"  type="hidden"  name="posDevice.posID" />
                        </td>

                    </tr>

                    <tr><td align="right">设备名称：</td>
                        <td>
                            <select  id = "posName" name="posDevice.posName"><option value="01">互视达销售终端机</option>
                                <option value="02">红外线手持机</option>
                                <option value="03">非红外线手持机</option>
                                <option value="04">平板电脑</option>
                            </select>
                        </td>

                    </tr>
                    <tr>
                        <td align="right">投放区域：</td>
                        <td><input id="orgName"  type="text" style="width:195px"  class="put3" readonly/>
                            <input id="organizationID"  type="hidden" name="posDevice.organizationID"  />
                            <input id="organizationName"  type="hidden"  name="posDevice.organizationName"  />

                        </td>
                    </tr>
                    <tr><td align="right">负责人：</td>

                        <td>
                            <input  id="stname" style="width:195px"   type="text" class="put3 wlgr" readonly/>
                            <input id="staffID"  type="hidden" name="posDevice.staffID"/>
                            <input id="staffName"  type="hidden" name="posDevice.staffName"/>

                        </td>


                    </tr>
                    <tr><td align="right">使用状态：</td>
                        <td>
                            <select  id = "state" name="posDevice.state"><option value="0">使用中</option><option value="1">已停用</option><option value="2">已回收</option></select>
                        </td>

                    </tr>
                    <tr><td align="right">发货形式：</td>
                        <td>
                            <select  id = "fhform"  name="posDevice.fhform"><option value="0">全部商家送货(或虚拟产品)</option><option value="1">商家送货+现场拿货</option><option value="2">仅现场拿货</option></select>
                        </td>

                    </tr>
                    <tr><td align="right">默认公司网站：</td>
                        <td>
                            <input id="accessCcomName"  type="text" name="posDevice.accessCcomName"  readonly class="wanladw"/>
                            <input id="accessCcomID"  type="text" name="posDevice.accessCcomID" style="display: none;"/><input type="button" style="width:50px;"value="清空" id="clearID"/>
                        </td>

                    </tr>
                    <tr><td align="right">应用：</td>
                        <td>
                            <select  id = "app" name="posDevice.app"><option value="">不指定</option><option value="01">考场练车</option></select>
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
        <iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
    </body>
</html>
