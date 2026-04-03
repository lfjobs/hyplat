<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>公司资金收入支出管理报表</title>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <%@page import="hy.ea.bo.Company"%>
        <%@ page language="java" pageEncoding="UTF-8"%>
        <%@ taglib uri="/struts-tags" prefix="s"%>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
        <%
            String path = request.getContextPath();
            String basePath = request.getScheme() + "://"
                    + request.getServerName() + ":" + request.getServerPort()
                    + path + "/";
             Company c = (Company)session.getAttribute("currentcompany");
        %>
        <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
        </script>
        <script type="text/javascript"
            src="<%=basePath%>js/jqModal/jqModal.js">
        </script>
        <script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
        <link rel="stylesheet" type="text/css"
            href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
        <link rel="stylesheet" type="text/css"
            href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
         <script type="text/javascript"
            src="<%=basePath%>js/ea/finance/CashdayStatements2c_sprin.js">
        </script>
        <script type="text/javascript"
            src="<%=basePath%>js/common/organizationTree.js"></script>
        <style type="text/css">
.table {
    border-collapse: collapse;
    border: 1px solid #000000;
    font-size: 9px;
}

.table th {
    border: 1px solid #000000;
    color: #000000;
}

.table td {
    border: 1px solid #000000;
    color: #000000;
}

body,td,th {
    font-size: 9px;
}

body {
    margin-left: 15px;
}

         th, TH {
                font-size: 12px;
                border-color: #000000;
                height:18;
}
    </style>
<script type="text/javascript">
    var basePath = "<%=basePath%>";
    var sdate = "${sdate}";
    var level="${level}";
    var companyid="";
    var companyname="";
    var treeID ="";
    var treeNames="";
    var notoken = 0;
    if(level!="staff"){
             companyid="<%=c.getCompanyID()%>";
            companyname="<%=c.getCompanyName()%>";
            treeID = '<%=session.getAttribute("organizationID")%>';
            treeNames="<%=session.getAttribute("organizationName")%>";
    }
    var tt = "${tt}";
    var balance2=0;
    var qcye=parseFloat("${qcye}");
    var num=0;
    var type="";
    var zz="${zz}"
</script>
</head>
<body>
    <table width="100%" border="1" cellspacing="0" cellpadding="0">
          <tr>
            <th colspan="43" align="center" style="font-size: 20px"><input type="button" value="查询" id="searchbutton"/></th>
        </tr>
        <tr>
            <th colspan="43" align="center" style="font-size: 20px">资金收入支出报表</th>
        </tr>
        <tr>
            <th width="4%" rowspan="2" align="center">公司</th>
            <th width="4%" rowspan="2" align="center">部门</th>
            <th width="4%" rowspan="2" align="center">责任人</th>
            <th width="3%" rowspan="2" align="center">单据类型</th>
            <th width="4%" rowspan="2" align="center">银行账号</th>
            <th width="3%" rowspan="2" align="center">款源日期</th>
            <th width="3%" rowspan="2" align="center">入账日期</th>
            <th width="10%" rowspan="2" align="center">品名名称</th>
            <th width="4%" rowspan="2" align="center">事由</th>
            <th width="4%" rowspan="2" align="center">科目管理</th>
            <th width="3%" rowspan="2" align="center">费用类别</th>
            <th width="1%" rowspan="2">&nbsp;</th>
            <th colspan="9" align="center">借方金额</th>
            <th width="1%" rowspan="2" align="center">方向</th>
            <th colspan="9" align="center">贷方金额</th>
            <th width="1%" rowspan="2" align="center">&nbsp;</th>
            <th colspan="11" align="center">余额</th>
        </tr>
        <tr>
            <th width="2%">百</th>
            <th width="2%">十</th>
            <th width="2%">万</th>
            <th width="2%">千</th>
            <th width="2%">百</th>
            <th width="2%">十</th>
            <th width="2%">元</th>
            <th width="2%">角</th>
            <th width="2%">分</th>
            <th width="2%">百</th>
            <th width="2%">十</th>
            <th width="2%">万</th>
            <th width="2%">千</th>
            <th width="2%">百</th>
            <th width="2%">十</th>
            <th width="2%">元</th>
            <th width="2%">角</th>
            <th width="2%">分</th>
            <th width="2%">亿</th>
            <th width="2%">千</th>
            <th width="2%">百</th>
            <th width="2%">十</th>
            <th width="2%">万</th>
            <th width="2%">千</th>
            <th width="2%">百</th>
            <th width="2%">十</th>
            <th width="2%">元</th>
            <th width="2%">角</th>
            <th width="2%">分</th>

        </tr>
       <c:forEach var='arr' items="${blist}" varStatus="list">
            <tr id="${arr[0]}">
                <td>${arr[1]}</td>
                <td>${arr[2]}</td>
                <td>${arr[3]}</td>
                <td>${arr[4]}</td>
                <td>${arr[5]}</td>
                <td>${arr[6]}</td>
                <td>${arr[7]}</td>
                <td>${arr[8]}</td>
                <td>${arr[9]}</td>
                <td>${arr[10]}</td>
                <td>${arr[11]}</td>
                <td>&nbsp;<span style="display: none;" class="money1">${arr[12]}</span>
                </td>
                <td><span id='1'></span>
                </td>
                <td><span id='2'></span>
                </td>
                <td><span id='3'></span>
                </td>
                <td><span id='4'></span>
                </td>
                <td><span id='5'></span>
                </td>
                <td><span id='6'></span>
                </td>
                <td><span id='7'></span>
                </td>
                <td><span id='8'></span>
                </td>
                <td><span id='9'></span>
                </td>
                <td>${arr[13]}<span style="display: none;" class="money2">${arr[14]}</span>
                </td>
                <td><span id='11'></span>
                </td>
                <td><span id='12'></span>
                </td>
                <td><span id='13'></span>
                </td>
                <td><span id='14'></span>
                </td>
                <td><span id='15'></span>
                </td>
                <td><span id='16'></span>
                </td>
                <td><span id='17'></span>
                </td>
                <td><span id='18'></span>
                </td>
                <td><span id='19'></span>
                </td>
                <td><span style="display: none;" class="yue">${arr[15]}</span>
                </td>
                <td><span id='21'></span>
                </td>
                <td><span id='22'></span>
                </td>
                <td><span id='23'></span>
                </td>
                <td><span id='24'></span>
                </td>
                <td><span id='25'></span>
                </td>
                <td><span id='26'></span>
                </td>
                <td><span id='27'></span>
                </td>
                <td><span id='28'></span>
                </td>
                <td><span id='29'></span>
                </td>
                <td><span id='210'></span>
                </td>
                <td><span id='211'></span>
                </td>
            </tr></c:forEach>
    </table>
    <!--搜索窗口 -->
        <form name="SearchForm" id="SearchForm" method="post">
            <div class="jqmWindow" style="width: 400px; right: 30%; top: 10%"
                id="jqModelSearch">
                <input type="submit" name="submit" style="display: none" />
                <div class="drag">
                    查询信息
                    <div class="close">
                    </div>
                </div>
                <table width="396" id="SearchTable">
                <s:if test="zz=='07'||zz=='08'">
                <tr>
                    <td align="right" width="100px">
                            单据类别：
                        </td>
                        <td style="width: 200px">
                            <s:select class="dis" list="%{#request.billsType}"
                            headerValue="请选择" listKey="codeValue" listValue="codeValue" headerKey="0"  id="billsTypes"
                                                    name="billsTypes" theme="simple"></s:select>
                        </td>
                    </tr>
                </s:if>
                    <tr>
                        <td align="right">
                            部门名称：
                        </td>
                        <td width="314">
                            <select id="orgID" name="balanceChange.departmentID">
                            <option value="">
                                请选择
                            </option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td width="200" align="right">
                            责任人：
                        </td>
                        <td>
                            <select name="balanceChange.staffID" id='person'>
                                <option value="">
                                    请选择部门
                                </option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                    <td align="right">
                            款源日期：
                        </td>
                        <td style="width: 200px">
                            <input id="sdate" name="sdate" onfocus="date(this);" class="put3"
                                style="width: 85px" />至<input id="edate" name="edate" onfocus="date(this);" class="put3"
                                style="width: 85px" />
                        </td>
                    </tr>
                     <tr>
                    <td align="right">
                            科目管理：
                        </td>
                        <td style="width: 200px">
                            <input type="hidden" value="${subjectsID}" id="subjectsID"
                                                    name="balanceChange.subjectsID" />
                                                <input type="text" readonly="readonly"
                                                    value="${subjectsName}"  size="10"
                                                    id="subjectsName" name="balanceChange.subjectsName" /><a href="#" class="tosubjects ">选择科目</a>
                        </td>
                    </tr>
                     <tr>
                    <td align="right">
                            费用类别：
                        </td>
                        <td style="width: 200px">
                            <s:select class="dis" list="%{#request.costTypeList}" headerKey="0" headerValue="请选择"
                                                    listKey="codeValue" listValue="codeValue" id="costType"
                                                    name="balanceChange.costType" theme="simple"></s:select>
                        </td>
                    </tr>
                </table>
                <div align="center">
                    <input type="button" class="input-button" id="tosearch"
                        value=" 查询 "/>
                    <input name="search" type="hidden" value="search" />
                    <input name="zz" type="hidden" value="${zz}" />
                </div>
            </div>
        </form>

        <div class="jqmWindow jqmWindowcss2" style="width: 600px; top: 10%;"
            id="selectsubjects">
            <div class="drag">
                选择
            </div>
            <table>
                <tr>
                    <td>
                        科目名字：
                    </td>
                    <td align="left" class="subjects">
                        <select id="province" number='0' style="width: 110px;"></select>
                        <select id="city" number='1' style="width: 110px;"></select>
                        <select id="county" number='2'
                            style="width: 110px; display: none;"></select>
                        <select id="addressTown" number='3'
                            style="width: 110px; display: none;"></select>
                        <select id="addressVillage" number='4'
                            style="width: 110px; display: none;"></select>
                        <select id="addressCommunity" number='5'
                            style="width: 110px; display: none;"></select>
                        <select id="addressFloor" number='6'
                            style="width: 110px; display: none;"></select>
                        <select id="addressLayer" number='7'
                            style="width: 110px; display: none;"></select>
                        <select id="addressSize" number='8'
                            style="width: 110px; display: none;"></select>
                    </td>
                    <td>
                        <a href="#"
                            onclick="window.open('<%=basePath%>/page/ea/main/finance/production/csubejsts/csubejst_manger.jsp')">新增</a>
                    </td>
                </tr>
            </table>
            <div align="center">
                <input type="button" class="input-button" id="savesubjects"
                    value="确定" />
                <input type="button" class="input-button JQueryreturns" value="取消" />
            </div>
        </div>

</body>
</html>
