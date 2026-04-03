<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="cache-control" content="no-cache"/>
        <title>学员培训协议汇总</title>

        <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
        <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
        <script src="<%=basePath%>/js/ea/validate.js" type="text/javascript"></script>
        <link href="<%=basePath%>/css/ea/validate.css" rel="stylesheet"type="text/css" />
        <link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"
              type="text/css" />
        <link rel="stylesheet" type="text/css"
              href="<%=basePath%>css/admin_main111.css" />
        <script src="<%=basePath%>js/ea/office_ea/contract/allfiles.js" type="text/javascript" charset="utf-8"></script>
        <script language="javascript" type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
        <script type="text/javascript">
            var docId = "";
        	var search="${search}";
        	var basePath="<%=basePath%>";
        	var pNumber=${pageNumber};
        	var token=0;
            var notoken = 0;

        </script>

    </head>
    <body>
        <form  name="searchForm" id="searchForm">
            <input type="submit" name="submit" style="display:none"/>
			<input  name="parameter" id="parameter" style="display:none"/>

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
                        协议名称
                    </th>
                    <th width="90" align="center">
                        学员姓名
                    </th>
                    <th width="180" align="center">
                       身份证号
                    </th>
                    <th width="180" align="center">
                        手机号
                    </th>
                    <th width="100" align="center">
                        制定日期
                    </th>
                    <th width="200" align="center">
                        状态
                    </th>

                </tr>
            </thead>
            <tbody>
             <s:iterator value="pageForm.list" var="item">
					<tr id="${item[0]}">
                        <td>
							<input type="radio" name="a" class="JQuerypersonvalue"
								value="${item[0]}" />
						</td>
						<td>
							<span id="title">${item[1]}</span>
						</td>

                        <td>
                            <span id="staffname">${item[3]}</span>

						</td>
						<td>
                            <span id="staffidentitycard">${item[4]}</span>

						</td>

                        <td>
                            <span id="tel">${item[6]}</span>

                        </td>

                        <td>
                            <span id="createDate">${fn:substring(item[2],0,19)}</span>
                        </td>



                        <td>

                             <span id="status" style="display: none;">${item[5]}</span>
                            <c:choose>
                                <c:when test="${item[5]=='A'}">
                                    待签约
                                </c:when>
                                <c:when test="${item[5]=='R'}">
                                    已驳回
                                </c:when>
                                <c:when test="${item[5]=='U'}">
                                    不批准
                                </c:when>
                                <c:when test="${item[5]=='F'}">
                                    已签约
                                </c:when>
                                <c:when test="${item[5]=='P'}">
                                    待分发
                                </c:when>
                                <c:when test="${item[5]=='S'}">
                                    审核中
                                </c:when>
                                <c:when test="${item[5]=='I'}">
                                    草稿
                                </c:when>
                                <c:when test="${item[5]=='O'}">
                                    待阅读
                                </c:when>
                                <c:when test="${item[5]=='K'}">
                                    待支付
                                </c:when>
                                <c:otherwise>
                                    已阅读
                                </c:otherwise>
                            </c:choose>
                        </td>
					</tr>
				</s:iterator>
            </tbody>
        </table>
        <c:import url="../../../page_navigator.jsp">
            <c:param name="actionPath" value="ea/contract/ea_getAllFileList.jspa?search=${search}&pageNumber=${pageNumber}">
            </c:param>
        </c:import>
        <!--添加窗口 -->

        <form name="addForm" id="addForm" method="post"  enctype="multipart/form-data">
            <div class="jqmWindow" style="width:550px;right: 30%;top:10%" id="jqModeladd">
                <input type="submit" name="submit" style="display:none"/>
                <div class="drag">
                    上传本地公章
                    <div class="close">
                    </div>
                </div>

                <table width="100%" id="addTable">
                    <tr>
                        <td align="right">公章名称：</td>
                        <td><input  type="text" style="width:195px"  name="stampname"/>

                        </td>

                    </tr>
                    <tr>
                        <td align="right">公章图片：</td>
                        <td><input type="file" style="width:195px"  name="photo"/>

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
