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
        <title>微信海报设置</title>

        <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
        <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>

        <script src="<%=basePath%>/js/ea/validate.js" type="text/javascript"></script>
        <link href="<%=basePath%>/css/ea/validate.css" rel="stylesheet"type="text/css" />

        <script language="javascript" type="text/javascript" src="<%=basePath%>js/ea/marketing/supermarket/device/posterlist.js"></script>
        <script language="javascript" type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
        <script type="text/javascript">
            var spID = "";
        	var search="${search}";
        	var basePath="<%=basePath%>";
        	var pNumber=${pageNumber};
            var  token = 0;
            var deviceType = "${param.deviceType}";
        </script>
    </head>
    <body>
        <form  name="searchForm" id="searchForm">
            <input type="submit" name="submit" style="display:none"/>
            <input  name="setPoster.posterName" id="posterName" style="display:none"/>
            <input  name="deviceType" id="deviceType" value="${param.deviceType}" style="display:none"/>

            <input type="hidden" name="search" value="search" />
            <input id="spKey"  type="text"  name="setPoster.spKey"  style="display: none;"/>
            <input id="spID"  type="text"  name="setPoster.spID"  style="display: none;"/>
            <input id="isPublish"  type="text"  name="setPoster.isPublish"  style="display: none;"/>

			<s:token/>
        </form>
        <table class="flexme11">
            <thead>
                <tr>
                    <th width="30" align="center">
                        选择
                    </th>
                    <th width="200" align="center">
                       海报名称
                    </th>
                      <th width="200" align="center">
                        海报查看
                    </th>
                    <th width="100" align="center">
                        排序
                    </th>
                    <th width="100" align="center">
                       是否上线
                    </th>
                    <th width="180" align="center">
                       创建人
                    </th>
                    <th width="180" align="center">
                        创建时间
                    </th>
                </tr>

            </thead>
            <tbody>
             <s:iterator value="pageForm.list">
					<tr id="${spID}">
						<td>
							<input type="radio" name="a" class="JQuerypersonvalue"
								value="${spID}" />
                            <span id="spKey" style="display:none;">${spKey}</span>
                            <span id="spID" style="display:none;">${spID}</span>
						</td>
                        <td>

                        <span id="posterName">${posterName}</span>
                       </td>

                        <td>
                            <s:if test="posterPath==null||posterPath==''">无</s:if>
                            <s:else>
                                <span id="posterPath"  onclick="lookImage('${posterPath}');"><a href="#">查看</a></span>
                            </s:else>
                        </td>
                        <td title="双击编辑序号" >

                            <span  id="sorts">${sorts}</span>
                            <input type="text" value="${sorts}" style="width:20px;display: none;" id="inputsort" class="inputsort"/>
                        </td>
                        <td>

                            <span id="isPublish"  style="display: none;">${isPublish}</span>
                            <c:choose>
                                <c:when test="${isPublish=='00'}">未上线</c:when>
                                <c:when test="${isPublish=='01'}">已上线</c:when>

                            </c:choose>
                        </td>
                        <td>

                            <span id="inputName">${inputName}</span>

                        </td>
                        <td>
                            <span id="createDate">${fn:substring(createDate,0,19)}</span>
                        </td>

					</tr>
				</s:iterator>
            </tbody>
        </table>
        <c:import url="../../../../page_navigator.jsp">
            <c:param name="actionPath" value="ea/face/ea_getPosterList.jspa?search=${search}&pageNumber=${pageNumber}&deviceType=${param.deviceType}">
            </c:param>
        </c:import>
        <!--添加窗口 -->
        <form name="addForm" id="addForm" method="post" enctype="multipart/form-data" >
            <div class="jqmWindow" style="width:450px;right: 30%;top:10%" id="jqModeladd">
                <input type="submit" name="submit" style="display:none"/>
                <div class="drag">
                    添加海报(800*1280px)
                    <div class="close">
                    </div>
                </div>

                <table width="100%" id="addTable">
                     <tr>
                         <td align="right">海报名称：</td>
                         <td><input  type="text"  id="posterName" name="setPoster.posterName" class="put3" maxlength="50"/>
                             <input  type="text"  name="setPoster.deviceType"  value="${param.deviceType}" style="display: none;"/>
                             <input  type="text"  name="setPoster.spID"  id="spID" style="display: none;"/>



                         </td>
                    </tr>
                    <tr><td align="right">海报图片：</td>
                        <td>
                            <input  type="file"  name="file" style="width:150px;"/>
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
