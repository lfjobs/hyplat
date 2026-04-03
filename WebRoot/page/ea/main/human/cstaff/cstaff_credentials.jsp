<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>证件列表</title>
    <!-- 此页面在2014-12-18被humanResource.jsp替代。三个月后没有用到可以删除 -->
    <script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
    <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
    <link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
    <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
    <script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
    <script src="<%=basePath%>js/ea/human/cstaff/cstaff_credentials.js"></script>

    <script type="text/javascript" src="<%=basePath%>js/ea/human/cstaff.js"></script>
    <script type="text/javascript">
        var select = 1;
        var credentialsID = '';
        var basePath = '<%=basePath%>';
        var staffID = '${credentials.staffID}';
        var notoken = 0;
        var mainheught = 0; //框架高度
        var ids = ''; //存放行ID
        var isvals = 0; //赋值判断
        var customer = '<%=request.getParameter("customer")%>';

        function getValueForParm(id, url, isval) { //打开页面
            ids = id;
            isvals = isval;
            $("#ifr").attr("src", basePath + url);
            mainheught = parent.document.getElementById("mainframe13").offsetHeight;
            parent.document.getElementById("mainframe13").style.height = 330 + 'px';
            $("#jqmWindow2").jqmShow();
        }

        $(document).ready(function () {
            $("#isBack").click(function () {// 返回
                $("#ifr").attr("src", "");
                $("#jqmWindow2").jqmHide();
                parent.document.getElementById("mainframe13").style.height = mainheught + 'px';
            });

            $("#isSubmit").click(function () {// 选择确定
                var value1 = "";
                if (isvals == 1) {
                    value1 = window.frames["ifr"].personvalue;
                } else if (isvals == 2) {
                    value1 = window.frames["ifr"].productdesignID;
                }
                if (value1 == "") {
                    alert("请选择")
                    return;
                }
                if (isvals == 1) {
                    var value2 = window.frames["ifr"].$('tr#' + value1).find("span#staffName").text();//弹出框的页面存在于span中才取得到
                    var value3 = window.frames["ifr"].$('tr#' + value1).find("span#staffCode").text();//弹出框的页面存在于span中才取得到
                    $("#" + ids).find("#auditor").val(value2);
                    $("#" + ids).find("#auditorNumber").val(value3);
                } else if (isvals == 2) {
                    var values = window.frames["ifr"].$('tr#' + value1).find("span#goodsName").text();
                    $("#" + ids).find("#credentialsName").val(values);
                }
                $("#ifr").attr("src", "");
                parent.document.getElementById("mainframe13").style.height = mainheught + 'px';
                $("#jqmWindow2").jqmHide();
            });
        });
    </script>
</head>
<body>
<form enctype="multipart/form-data" name="credentialsForm" id="credentialsForm" method="post">
    <s:token></s:token><input type="submit" name="submit" style="display:none"/>
    <div id="main_main" class="main_main">
        <table class="credentials">
            <thead>
            <tr>
                <th width="30" align="center">选择</th>
                <th width="70" align="center">
                    有效起时间
                </th>
                <th width="70" align="center">
                    有效止时间
                </th>
                <th width="85" align="center">
                    证件名称
                </th>
                <th width="85" align="center">
                    证件类型
                </th>
                <th width="60" align="center">
                    证件编号
                </th>
                <th width="60" align="center">
                    档案编号
                </th>
                <th width="200" align="center">
                    住址
                </th>
                <th width="200" align="center">
                    发证机关（单位）
                </th>
                <th width="80" align="center">
                    证件资料文号
                </th>
                <th width="60" align="center">
                    附件编号
                </th>
                <th width="80" align="center">
                    审核人
                </th>
                <th width="85" align="center">
                    审核人人员编号
                </th>
                <th width="70" align="center">
                    审核时间
                </th>
                <th width="200" align="center">
                    备注
                </th>
                <th width="150" align="center">
                    附件
                </th>
            </tr>
            </thead>
            <tbody id="tbwid">
            <input type="hidden" id="start"/>
            <input type="hidden" id="end"/>
            <tr id="sa" style="display: none" class="td_bg01 saveAjax model2">
                <td><input type="radio" name="a" class="credentialsID"></td>
                <td class="td_bg01">
                    <input name="invalidateStart" id="invalidateStart"
                           onfocus="WdatePicker({dateFmt:'yyyy-MM-dd', maxDate:'#F{$dp.$D(\'end\')}'})" size="10"/>
                </td>
                <td class="td_bg01">
                    <input name="invalidateEnd" id="invalidateEnd"
                           onfocus="WdatePicker({dateFmt:'yyyy-MM-dd', minDate:'#F{$dp.$D(\'start\')}'})" size="10"/>
                </td>
                <td class="td_bg01">
                    <input name="credentialsName" class="err" id="credentialsName" size="18"/>
                    <c:if test="${param.customer == 'customer'}"><a href="#"
                                                                    onclick="getValueForParm($(this).parent().parent().parent().attr('id'),'ea/productdesign/ea_getListProductdesign.jspa?flexbutton=flexbutton','2')">选择</a></c:if>

                </td>
                <td class="td_bg01">
                    <s:select list="credentialsTypelist" listKey="codeValue" listValue="codeValue"
                              name="credentialsType" id="xxx" theme="simple"></s:select>
                </td>
                <td class="td_bg01">
                    <input name="credentialsNo" id="credentialsNo" size="10"/>
                </td>
                <td class="td_bg01">
                    <input name="recordsNumber" id="recordsNumber" size="10"/>
                </td>
                <td class="td_bg01">
                    <input name="address" id="address" size="25"/>
                </td>
                <td class="td_bg01">
                    <input name="organ" id="organ" size="25"/>
                </td>
                <td class="td_bg01">
                    <input name="credentialsrecordNo" id="credentialsrecordNo" size="10"/>
                </td>
                <td class="td_bg01">
                    <input name="appendixNumber" id="appendixNumber" size="10"/>
                </td>
                <td class="td_bg01">
                    <input name="auditor" id="auditor" size="10" readonly="readonly"/>
                    <a href="#"
                       onclick="getValueForParm($(this).parent().parent().parent().attr('id'),'ea/cstaff/ea_getListCStaffByCompanyID.jspa?flexbutton=flexbutton','1')">选择</a>
                </td>
                <td class="td_bg01">
                    <input name="auditorNumber" id="auditorNumber" size="10" readonly="readonly"/>
                </td>
                <td class="td_bg01">
                    <input name="invalidate" id="invalidate" onfocus="date(this);" size="10"/>
                </td>
                <td class="td_bg01">
                    <input name="credentialsDesc" id="credentialsDesc" size="30"/>
                    <input type="hidden" name="credentialskey" id="credentialskey"/>
                    <input type="hidden" name="staffID" value="${credentials.staffID}" id="staffID"/>
                    <input type="hidden" name="credentialsID" id="credentialsID"/>
                </td>
                <td class="td_bg01">
                    <input name="photos" id="photos" type="file" contentEditable="false" size="10"/>
                </td>
            </tr>
            <s:iterator value="credentialsList">
                <tr class="td_bg01 saveAjax trclass" id="${credentialsID}">
                    <td>
                        <input type="radio" name="a" class="JQuerypersonvalue" value="${credentialsID}"/>
                    </td>
                    <td class="td_bg01">
                        <SPAN id="invalidateStart" class="datas">${invalidateStart}</SPAN>
                        <input class="model1" name="invalidateStart" id="invalidateStart" value="${invalidateStart}"
                               onfocus="WdatePicker({dateFmt:'yyyy-MM-dd', maxDate:'#F{$dp.$D(\'end\')}'})" size="10"/>
                    </td>
                    <td class="td_bg01">
                        <SPAN id="invalidateEnd" class="datas">${invalidateEnd}</SPAN>
                        <input class="model1" name="invalidateEnd" id="invalidateEnd" value="${invalidateEnd}"
                               onfocus="WdatePicker({dateFmt:'yyyy-MM-dd', minDate:'#F{$dp.$D(\'start\')}'})"
                               size="10"/>
                    </td>

                    <td class="td_bg01">
                        <SPAN id="credentialsName">${credentialsName}</SPAN>
                        <input class="model1 err" name="credentialsName" value="${credentialsName}" size="18"/>
                        <c:if test="${param.customer == 'customer'}">
                            <a href="#" class="model1"
                               onclick="getValueForParm($(this).parent().parent().parent().attr('id'),'ea/productdesign/ea_getListProductdesign.jspa?flexbutton=flexbutton','2')">选择</a>
                        </c:if>
                    </td>
                    <td class="td_bg01">
                        <s:select list="credentialsTypelist" listKey="codeValue" listValue="codeValue" disabled="true"
                                  name="credentialsType" id="credentialsType" theme="simple"></s:select>
                    </td>

                    <td class="td_bg01">
                        <SPAN id="credentialsNo">${credentialsNo}</SPAN>
                        <input class="model1" name="credentialsNo" id="credentialsNo" value="${credentialsNo}"
                               size="10"/>
                    </td>
                    <td class="td_bg01">
                        <SPAN id="recordsNumber">${recordsNumber}</SPAN>
                        <input class="model1" name="recordsNumber" id="recordsNumber" value="${recordsNumber}"
                               size="10"/>
                    </td>
                    <td class="td_bg01">
                        <SPAN id="address">${address}</SPAN>
                        <input class="model1" name="address" id="address" value="${address}" size="25"/>
                    </td>
                    <td class="td_bg01">
                        <SPAN id="organ">${organ}</SPAN>
                        <input class="model1" name="organ" id="organ" value="${organ}" size="25"/>
                    </td>
                    <td class="td_bg01">
                        <SPAN id="credentialsrecordNo">${credentialsrecordNo}</SPAN>
                        <input class="model1" name="credentialsrecordNo" id="credentialsrecordNo"
                               value="${credentialsrecordNo}" size="10"/>
                    </td>
                    <td class="td_bg01">
                        <SPAN id="appendixNumber">${appendixNumber}</SPAN>
                        <input class="model1" name="appendixNumber" id="appendixNumber" value="${appendixNumber}"
                               size="10"/>
                    </td>
                    <td class="td_bg01">
                        <SPAN id="auditor">${auditor}</SPAN>
                        <input class="model1" id="auditor" name="auditor" value="${auditor}" size="10"
                               readonly="readonly"/>
                        <a href="#" class="model1"
                           onclick="getValueForParm($(this).parent().parent().parent().attr('id'),'ea/cstaff/ea_getListCStaffByCompanyID.jspa?flexbutton=flexbutton','1')">选择</a>
                    </td>
                    <td class="td_bg01">
                        <SPAN id="auditorNumber">${auditorNumber}</SPAN>
                        <input class="model1" id="auditorNumber" name="auditorNumber" value="${auditorNumber}" size="10"
                               readonly="readonly"/>
                    </td>
                    <td class="td_bg01">
                        <SPAN id="invalidate" class="datas">${invalidate}</SPAN>
                        <input class="model1" name="invalidate" id="invalidate" value="${invalidate}"
                               onfocus="date(this);" size="10"/>
                    </td>
                    <td class="td_bg01">
                        <SPAN id="credentialsDesc">${credentialsDesc}</SPAN>
                        <input class="model1" name="credentialsDesc" value="${credentialsDesc}" id="credentialsDesc"
                               size="30"/>
                        <input type="hidden" name="credentialskey" value="${credentialskey}"/>
                        <input type="hidden" name="credentialsID" value="${credentialsID}"/>
                        <input type="hidden" name="staffID" value="${staffID}"/>
                    </td>
                    <td class="td_bg01">
                        <span><s:if test="photo==null||photo==''">无</s:if></span>
                        <s:else>
                            <span id="photos" onclick="lookImage('${photo}');"><a href="#">查看</a></span>
                        </s:else>
                        <input name="photos" type="file" class="model1" size="10" contentEditable="false"/>
                        <input name="photo" type="hidden" value="${photo}" class="model1"/>
                    </td>
                </tr>
            </s:iterator>
            </tbody>
        </table>
        <c:import url="../../../page_navigator.jsp">
            <c:param name="actionPath"
                     value="ea/credentials/ea_getListCredentials.jspa?credentials.staffID=${credentials.staffID}">
            </c:param>
        </c:import>
    </div>
</form>
<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>

<!-- 从当前部门的员工中选择责任人 -->
<div id="jqmWindow2" class="jqmWindow"
     style="width: 98%; height: 320px; absolute; display: none; left: 1%; top: 1%; background: #eff">
    <div align="center">
        <iframe name="ifr" id="ifr" width="100%" height="280px"
                frameborder="0"></iframe>
        <input type="button" class="input-button" id="isSubmit" value=" 确定 "
               style="cursor: hand"/>
        <input type="button" class="input-button" id="isBack" value=" 关闭 "
               style="cursor: hand"/>
    </div>
</div>
<%--
<script type="text/javascript">
    $(function(){
        setTimeout(function(){
            $("div.bDiv").css({"height":parent.document.getElementById("mainframe13").offsetHeight-57+"px"});
    },100);
         $(window).resize(function(){
            setTimeout(function(){
                    $("div.bDiv").css({"height":parent.document.getElementById("mainframe13").offsetHeight-57+"px"});
            },100);
         });
    });
</script> --%>
</body>
</html>