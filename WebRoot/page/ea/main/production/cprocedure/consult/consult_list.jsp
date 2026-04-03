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
        <title>项目咨询</title>

        <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
        <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
        <script src="<%=basePath%>/js/ea/validate.js" type="text/javascript"></script>
        <link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
        <script language="javascript" type="text/javascript" src="<%=basePath%>js/ea/production/cprocedure/consult/consult_list.js"></script>
        <script language="javascript" type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
        <script type="text/javascript">
            var crId = "";
        	var search="${search}";
        	var basePath="<%=basePath%>";
        	var pNumber=${pageNumber};
        	var token=0;
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
			<input  name="consult.consultantName" id="consultantName" style="display:none"/>
			<input  name="consult.consultantPhone" id="consultantPhone" style="display:none"/>
            <input  name="consult.returnVisit" id="returnVisit" style="display:none"/>

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
                        客户姓名
                    </th>
                    <th width="90" align="center">
                        客户电话
                    </th>
                    <th width="150" align="center">
                        咨询时间
                    </th>
                    <th width="90" align="center">
                        客户类型
                    </th>
                    <th width="200" align="center">
                        咨询项目
                    </th>

                    <th width="180" align="center">
                        被咨询商家
                    </th>
                    <th width="200" align="center">
                       是否回访
                    </th>
                    <th width="140" align="center">
                       回访客服
                    </th>
                    <th width="150" align="center">
                        回访时间
                    </th>
                     <th width="200" align="center">
                       电话记录
                    </th>
                    <th width="180" align="center">
                       是否有意向
                    </th>
                    <th width="180" align="center">
                       是否接听
                    </th>

                </tr>
            </thead>
            <tbody>
             <s:iterator value="pageForm.list">
					<tr id="${crId}">
						<td>
							<input type="radio" name="a" class="JQuerypersonvalue"
								value="${crId}" />
						</td>
						<td>
                            <span id="crId" style="display:none;">${crId}</span>
							<span id="consultantName">${consultantName}</span>
						</td>
                        <td>
                            <span id="consultantPhone">${consultantPhone}</span>
                        </td>
                        <td>
                            <span id="consultingDate">${fn:substring(consultingDate,0,19)}</span>
                        </td>
                        <td>
                            <span id="clientType"  style="display: none;">${clientType}</span>
                            <c:choose>
                                <c:when test="${clientType=='01'}">公司</c:when>
                                <c:when test="${clientType=='02'}">个人</c:when>

                            </c:choose>
                        </td>
                        <td>
                            <c:if test="${ppid ne null&&ppid ne ''}">
                                <a target = "blank" href = "<%=basePath%>ea/industry/ea_informationDetails.jspa?ppId=${ppid}&ccompanyId=${companyId}&type=time&miniSystemJudge=03"><span id="goodsname">${goodsname}</span></a>
                            </c:if>
                            <c:if test="${ppid eq null||ppid eq ''}">
                                <a target = "blank" href = "<%=basePath%>ea/industry/ea_getCompanyHome.jspa?ccompanyId=${ccompanyId}&industryType=&etype=">公司网站</a>
                            </c:if>


                        </td>
                        <td>
                            <span id="companyName">${companyName}</span>
                        </td>
                        <td>
                            <span id="returnVisit" style="display: none;">${returnVisit}</span>
                            <c:choose>
                                <c:when test="${returnVisit=='00'}">未回访</c:when>
                                <c:when test="${returnVisit=='01'}">已回访</c:when>

                            </c:choose>
                        </td>
                        <td>
                            <span id="visitStaffName">${visitStaffName}</span>
                        </td>

                        <td>
                            <span id="visitDate">${fn:substring(visitDate,0,19)}</span>
                        </td>
                        <td>
                            <span id="visitContent">${visitContent}</span>
                        </td>
                        <td>
							<span id="isIntentCustomer" style="display: none;">${isIntentCustomer}</span>
                            <c:choose>
                                <c:when test="${isIntentCustomer=='00'}">不感兴趣</c:when>
                                <c:when test="${isIntentCustomer=='01'}">意向客户</c:when>
                                <c:when test="${isIntentCustomer=='02'}">成交客户</c:when>
                            </c:choose>
						</td>
                        <td>
                            <span id="islisten" style="display: none;">${islisten}</span>
                            <c:choose>
                                <c:when test="${islisten=='00'}">未接听</c:when>
                                <c:when test="${islisten=='01'}">已接听</c:when>
                            </c:choose>
                        </td>

					</tr>
				</s:iterator>
            </tbody>
        </table>
        <c:import url="../../../../page_navigator.jsp">
            <c:param name="actionPath" value="/ea/consult/ea_getConsultList.jspa?search=${search}&pageNumber=${pageNumber}">
            </c:param>
        </c:import>
        <!--添加窗口 -->
        <form name="addForm" id="addForm" method="post">
            <div class="jqmWindow" style="width:550px;right: 30%;top:10%" id="jqModeladd">
                <input type="submit" name="submit" style="display:none"/>
                <div class="drag">
                    电话记录(<span class="conname"></span>)
                    <div class="close">
                    </div>
                </div>

                <table width="100%" id="addTable">
                    <tr><td align="right">是否接听：</td>
                        <td>
                            <select  id = "islisten" name="consult.islisten">
                                <option value="01">已接听</option>
                                <option value="00">未接听</option>

                            </select>
                            <input type="text" style="display: none;" value="" id="crId" name="consult.crId" />
                        </td>

                    </tr>
                    <tr class="jt"><td align="right">客户类型：</td>
                        <td>
                            <select  id = "clientType" name="consult.clientType">
                                <option value="01">公司</option>
                                <option value="02">个人</option>
                            </select>
                        </td>

                    </tr>
                    <tr class="jt"><td align="right">合作意向：</td>
                        <td>
                            <select  id = "isIntentCustomer" name="consult.isIntentCustomer">
                                <option value="00">不感兴趣</option>
                                <option value="01">意向客户</option>
                                <option value="02">成交客户</option>
                            </select>
                        </td>

                    </tr>
                    <tr class="jt">
                        <td align="right">通话重点内容：</td>
                        <td>
                            <textarea rows="5" cols="30" name="consult.visitContent" id="visitContent" class="put3" maxlength="500"></textarea>

                        </td>

                    </tr>


                </table>
                <div align="center">
                    <input type="button" class="input-button" id="save" value=" 保存 "  style="margin: 10px;" />
                </div>
            </div>
        </form>


        <!--查看窗口 -->
        <form name="viewForm" id="viewForm" method="post">
            <div class="jqmWindow" style="width:550px;right: 30%;top:10%" id="jqModelview">
                <input type="submit" name="submit" style="display:none"/>
                <div class="drag">
                    电话记录(<span class="conname"></span>)
                    <div class="close">
                    </div>
                </div>

                <table width="100%" id="viewTable">
                    <tr><td align="right">客户姓名：</td>
                        <td>
                            <input type="text"  readonly id="consultantName"/>
                        </td>
                    </tr>
                    <tr><td align="right">客户电话：</td>
                        <td>
                            <input type="text"  readonly id="consultantPhone"/>
                        </td>
                    </tr>
                    <tr><td align="right">咨询日期：</td>
                        <td>
                            <input type="text"  readonly id="consultingDate"/>
                        </td>
                    </tr>
                    <tr><td align="right">是否回访：</td>
                        <td>
                            <span id="returnVisit"></span>
                        </td>
                    </tr>
                    <tr><td align="right">回访日期：</td>
                        <td>
                            <input type="text"  readonly id="visitDate"/>
                        </td>
                    </tr>
                    <tr><td align="right">回访客服：</td>
                        <td>
                            <input type="text"  readonly id="visitStaffName"/>
                        </td>
                    </tr>
                    <tr><td align="right">是否接听：</td>
                        <td>
                            <span id="islisten"></span>
                        </td>
                    </tr>
                    <tr class="jt"><td align="right">客户类型：</td>
                        <td>
                            <span id="clientType"></span>
                        </td>

                    </tr>
                    <tr class="jt"><td align="right">合作意向：</td>
                        <td>
                            <span id="isIntentCustomer"></span>
                        </td>

                    </tr>
                    <tr class="jt">
                        <td align="right">通话重点内容：</td>
                        <td>
                            <textarea rows="5" cols="30"  id="visitContent" readonly></textarea>

                        </td>

                    </tr>

                </table>
            </div>
        </form>
        <iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
    </body>
</html>
