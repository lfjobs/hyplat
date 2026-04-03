<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ page import="hy.ea.bo.Company"%>
<%@ page import="hy.ea.bo.CAccount"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    Company c = (Company)session.getAttribute("currentcompany");
    CAccount ca = (CAccount)session.getAttribute("account");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>消费补助设置</title>
    <script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
    <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
    <script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
    <link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
    <script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
    <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/uploadify/uploadify.css" />
    <script type="text/javascript" src="<%=basePath%>js/uploadify/jquery.uploadify.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/ea/finance/subsidize/subsidize.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/popLayer/css/popstyle.css" />
    <script src="<%=basePath%>js/popLayer/js/popLayer.js" type="text/javascript"></script>

    <script  src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js"></script>
    <script  src="<%=basePath%>js/tree/codebase/dhtmlxtree.js"></script>
    <link rel="STYLESHEET" type="text/css" href="<%=basePath%>js/tree/codebase/dhtmlxtree.css"/>

    <script>
        var companyID = '<%=c.getCompanyID()%>';
        var aemail = '<%=ca.getAccountEmail()%>';
        var basePath = "<%=basePath%>";
        var ppageNumber = '${pageNumber}';
        var pageSize = '${pageSize}';
        var token=0;
        var ssid="";
    </script>
    <style >
    </style>
</head>
<body >
<div class="main_main">
    <form name="onkeyfhForm" id="onkeyfhForm" method="post">
        <input type="submit" name="submit" id="submit" style="display: none;"/>
    </form>
    <table class="address">
        <thead>
        <tr class="tablewith">
            <th width="40" align="center">请选择</th>
            <th width="50" align="center">序号</th>
            <th width="50" align="center">编号</th>
            <th width="180" align="center">行业类别</th>
            <th width="100" align="center">添加时间</th>
            <th width="100" align="center">订单负责人</th>
            <th width="100" align="center">消费补助类型</th>
            <th width="100" align="center">零售价赠送比例</th>
            <th width="100" align="center">消费红包比例</th>
            <th width="100" align="center">第一次赠送比例</th>
            <th width="100" align="center">第二次赠送比例</th>
            <th width="100" align="center">消费补充红包比例</th>
            <th width="100" align="center">粉丝红包比例</th>
        </thead>
        <%int number = 1;%>
        <s:iterator value="pageForm.list">
        <%--<c:forEach items="${pageForm.list}">--%>
            <tr id="${ssid}">
                <td><input type="radio" name="a" class="JQuerypersonvalue"
                           value="${ssid}" /><input type="hidden" class="sskey"
                                                    value="${sskey}" /></td>
                <td><span><%=number%></span></td>
                <td><span class="">${codeNum}</span></td>
                <td><span class="gtid">${gtid}</span></td>
                <td><span class="adddate">${adddate}</span></td>
                <td><span class="staffName">${staffName}</span></td>
                <td><span class="stName">${stName}</span></td>
                <td><span class="totalPct">${totalPct}</span>%</td>
                <td><span class="xfPct">${xfPct}</span>%</td>
                <td><span class="flPct">${flPct}</span>%</td>
                <td><span class="slPct">${slPct}</span>%</td>
                <td><span class="xbPct">${xbPct}</span>%</td>
                <td><span class="fsPct">${fsPct}</span>%</td>
                <input type="hidden" value="${a[4]}" id="ddstatus"/>
            </tr>
            <%  number++; %>
        <%--</c:forEach>--%>
        </s:iterator>

    </table>
    <c:import url="../../../../../ea/page_navigator.jsp">
        <c:param name="actionPath"
                 value="/ea/setsubsidize/ea_listSub.jspa?pageForm.pageNumber=${pageForm.pageNumber}&pageNumber=${pageNumber}">
        </c:param>
    </c:import>
    <s:token></s:token>
</div>

<form name="saveForm" id="saveForm" method="post">
    <s:token></s:token>
    <div class="jqmWindow" style="width: 400px; right: 30%; top: 10%"
         id="jqModeladd">
        <input type="submit" name="submit"  style="display: none" />
        <div class="drag">
            添加
            <div class="close">
            </div>
        </div>
        <table width="396" id="SearchTable">
            <tr>
                <td align="right">
                    行业类型：
                </td>
                <td>
                    <input id="sskey" type="hidden" name="setSubsidize.sskey" />
                    <input id="ssid" type="hidden" name="setSubsidize.ssid" />
                    <input id="gtid" style="width: 190px" readonly
                           name="setSubsidize.gtid"  class="notNull"/>
                    &nbsp;<input type="button" onclick="xz('industry')" value="选择" class="btn01"/>
                </td>
            </tr>
            <tr>
                <td width="123" align="right">
                    零售价金额赠送比例：
                </td>
                <td>
                    <input id="totalPct" style="width: 100px" class="bl notNull"
                           name="setSubsidize.totalPct" />%
                </td>
            </tr>
            <tr>
                <td align="right">
                    消费补助类型：
                </td>
                <td id="stype" >
                </td>
            </tr>
            <tr>
                <td align="right">
                    消费红包比例：
                </td>
                <td >
                    <input id="xfPct" style="width: 100px" class="bl notNull" name="setSubsidize.xfPct" />%
                </td>
            </tr>
            <tr>
                <td align="right">
                    消费补充红包比例：
                </td>
                <td >
                    <input id="xbPct" style="width: 100px" class="bl notNull" name="setSubsidize.xbPct" />%
                </td>
            </tr>
            <tr>
                <td align="right">
                    粉丝红包比例：
                </td>
                <td >
                    <input id="fsPct" style="width: 100px" class="bl notNull" name="setSubsidize.fsPct" />%
                </td>
            </tr>
            <%--<tr>
                <td align="right">
                    消费补充红包比例：
                </td>
                <td >
                    <input id="fbPct" style="width: 100px" class="bl notNull" name="setSubsidize.fbPct" />%
                </td>
            </tr>--%>
            <tr>
                <td align="right">
                    第一次赠送比例：
                </td>
                <td>
                    <input id="flPct" style="width: 100px" class="bl notNull zs"
                           name="setSubsidize.flPct" />%
                </td>
            </tr>
            <tr>
                <td align="right">
                    第二次赠送比例：
                </td>
                <td>
                    <input id="slPct" style="width: 100px" class="bl notNull zs"
                           name="setSubsidize.slPct" />%
                </td>
            </tr>
        </table>
        <div align="center">
            <input type="button" class="input-button" id="tosave"
                   value=" 保存 "/>
        </div>
    </div>
</form>

<form name="fhbForm" id="fhbForm" method="post">
    <s:token></s:token>
    <div class="jqmWindow" style="width: 400px; right: 30%; top: 10%"
         id="jqModelfhb">
        <input type="submit" name="submit"  style="display: none" />
        <div class="drag">
            发红包
            <div class="close">
            </div>
        </div>
        <table width="396" id="SearchTable2">
            <tr>
                <td align="right">
                    行业：
                </td>
                <td>
                    <input id="ssid2"  type="hidden" name="ssid" />
                    <input id="sccid"  type="hidden" name="sccid" />
                    <input id="gtid2" style="width: 190px" readonly name="gtid"/>
                </td>
            </tr>
            <tr>
                <td width="123" align="right">
                    发红包帐号：
                </td>
                <td>
                    <input id="zh" style="width: 100px" class="zh"
                           name="zh" />
                </td>
            </tr>
            <tr>
                <td align="right">
                    支付密码：
                </td>
                <td>
                    <input id="mm" style="width: 100px" class="mm"
                           name="mm" />
                </td>
            </tr>
            <tr>
                <td align="right">
                    起始编号：
                </td>
                <td>
                    <input id="strnum" type="text" name="strnum"/>
                </td>
            </tr>
            <tr>
                <td align="right">
                    截至编号：
                </td>
                <td>
                    <input id="endnum" type="text" name="endnum"/>
                </td>
            </tr>
            <tr>
                <td align="right">
                    发红包金币数：
                </td>
                <td>
                    <input id="jb" style="width: 100px" class="jb"
                           name="jinbi" />
                </td>
            </tr>
            </tr>
        </table>
        <div align="center">
            <input type="button" class="input-button" id="tosave2"
                   value=" 保存 "/>
        </div>
    </div>
</form>

<!-- 行业 -->

<div id="industry" class="popMain jqmWindow" style="width: 500px;">
    <div class="choose-box" style="width: 500px;">
        <div class="choosetitle">
            <span>选择行业</span>
        </div>
        <div class="chooseborder" style="width: 490px;">
            <div id="industryTree" style=" border: 0px solid #000000;"></div>


        </div>
        <div class="choose-box-bottom">
            <input type="botton" onclick="hide('industry')" value="关闭" />
        </div>
    </div>
</div>

<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
</body>
</html>