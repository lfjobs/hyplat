<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmx" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>营销产品发布>>产品发布</title>
    <script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

    <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
    <%--<link href="<%=basePath%>/WebRoot/css/ea/staff.css" rel="stylesheet"
          type="text/css"/>--%>
    <link rel="stylesheet" type="text/css"
          href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
    <link rel="stylesheet" type="text/css"
          href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
    <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
    <script type="text/javascript"
            src="<%=basePath%>js/jqModal/jqModal.js"></script>
    <script type="text/javascript">
        var basePath = '<%=basePath%>';
        var pNumber = "${pageNumber}";
        var search = '${search}';
        var activityType = '${activityType}';
        var activity = activityType == '00' ? "普通活动" : "特价活动";
        /*[00:普通活动，01:特价活动]*/
    </script>
</head>
<body>

<form style="display: none;" name="addressForm" id="addressForm"
      method="post">
    <s:token></s:token>
    <input type="submit" name="submit" style="display: none"/>
</form>

<div id="main_main" class="main_main">
    <table class="address">
        <thead>
        <tr>
            <th width="30" align="center">
                选择
            </th>
            <th width="30" align="center">
                序号
            </th>

            <th width="110" align="center">
                活动名称
            </th>
            <th width="100" align="center">
                活动描述
            </th>
            <th width="150" align="center">
                活动起始时间
            </th>
            <th width="150" align="center">
                活动结束时间
            </th>

            <th width="75" align="center">
                设置活动责任人
            </th>
            <th width="150" align="center">
                活动设置时间
            </th>
            <th width="150" align="center">
                活动修改时间
            </th>
            <th width="50" align="center">
                活动状态
            </th>
            <th width="50" align="center">
                活动类型
            </th>
            <th width="100" align="center">
                编辑活动相关产品
            </th>


        </tr>
        </thead>
        <tbody id="productList">
        <% int number = 1; %>
        <c:forEach var='arr' items="${pageForm.list}">
            <tr id="${arr[0]}">
                <td>
                    <input type="radio" name="checkedId" class="JQuerypersonvalue"
                           value="${arr[0]}"/>
                    <input type="hidden" name="activityId" id="activityId"
                           value="${arr[0]}"/>

                </td>
                <td>
                    <span id="number"><%=number%></span>
                </td>
                <td>
                    <span id="activityName">${arr[1]}</span>
                </td>
                <td>
                    <span id="activityDescribe">${arr[2]}</span>
                </td>
                <td>
                    <span class="startTime">${arr[3]}</span>
                </td>
                <td>
                    <span class="endTime">${arr[4]}</span>
                </td>
                <td>
                    <span id="principal">${arr[5]}</span>
                </td>
                <td>
                    <span id="addTimes">${arr[6]}</span>
                </td>
                <td>
                    <span id="updateTimes">${arr[9]}</span>
                </td>
                <td>
                    <c:if test="${arr[7]=='00'}">
                        <p id="state">初始</p>
                    </c:if>
                    <c:if test="${arr[7]=='01'}">
                        <p id="state">已开启</p>
                    </c:if>
                    <c:if test="${arr[7]=='02'}">
                        <p id="state">暂停</p>
                    </c:if>
                    <c:if test="${arr[7]=='03'}">
                        <p id="state">结束</p>
                    </c:if>
                    <c:if test="${arr[7]=='04'}">
                        <p id="state">已删除</p>
                    </c:if>
                </td>
                <td>
                    <c:if test="${arr[8]=='00'}">
                        <p class="type">普通活动</p>
                    </c:if>
                    <c:if test="${arr[8]=='01'}">
                        <p class="type">特价活动</p>
                    </c:if>
                </td>
                <td>
                    <a href="#" onclick="toadd('${arr[0]}')">添加</a>
                    <a href="#" onclick="toedit('${arr[0]}')">修改</a>
                </td>
            </tr>
            <% number++; %>
        </c:forEach>
        </tbody>
    </table>
    <c:import url="../../../../page_navigator.jsp">
        <c:param name="actionPath"
                 value="ea/wholesale/ea_selectActivityList.jspa?pageNumber=${pageNumber}&search=${search}"></c:param>
    </c:import>
</div>

<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>

<script type="text/javascript" src="<%=basePath%>js/ea/finance/brokerage/activity/activity.js"></script>
<script type="application/javascript">
    //去活动产品添加页面
    function toadd(activityId) {
        if (typeof(activityId) == "undefined" || activityId == "") {
            alert('活动id为空！');
            return
        } else {
            productList(activityId);
        }
    }
    //去活动产品修改页面
    function toedit(activityId) {
        if (typeof(activityId) == "undefined" || activityId == "") {
            alert('活动id为空！');
            return
        } else {
            //获取当前活动所有产品的活动价及相关佣金并回显
            $.ajax({
                type: "POST",
                url: basePath + "ea/activityPrice/sajax_ea_getActivityPrice.jspa",
                data: {
                    "actPrice.activityId": activityId,
                    "productPackaging.ppID": "00"//默认给00防止为空异常
                },
                success: function (data) {
                    var member = eval("(" + data + ")");
                    var actPriceList = member.actPriceList;
                    if (actPriceList != null && actPriceList.length > 0) {
                        url = basePath + "ea/activityPrice/ea_toActivityBrokerageUpdate.jspa?actPrice.activityId=" + activityId+ "&activityType=" + activityType + ""
                        open(url);
                    } else {
                        alert("您还没有添加当前活动所有产品的活动价及相关佣金！")
                    }
                },
                error: function (data) {
                    alert("数据获取异常！");
                },
                dateType: "json"
            });

        }
    }

    //获取没有设置活动价佣金并且已设置零售价佣金的产品
    function productList(activityId) {
        $.ajax({
            type: "POST",
            url: basePath + "ea/activityPrice/sajax_ea_getProductByStatus.jspa",
            data: null,
            success: function (data) {
                var member = eval("(" + data + ")");
                if (member.code == "200") {
                    var pageForm = member.pageForm;
                    if (pageForm != null && pageForm.list != null) {
                        url = basePath + "ea/activityPrice/ea_toActivityBrokerageAdd.jspa?actPrice.activityId=" + activityId + "&activityType=" + activityType + ""
                        open(url);
                    } else {
                        alert("请先设置零售价佣金！")
                    }
                } else {
                    //登录异常，回登录页面重新登录
                    location.href = basePath + "page/ea/not_login.jsp";
                }

            },
            error: function (data) {
                alert("获取数据失败！");
            },
            dateType: "json"
        });
    }
</script>
</body>
</html>
