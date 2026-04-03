<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page import="hy.ea.bo.CAccount" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    CAccount c = (CAccount) session.getAttribute("account");
    String companyID = c.getCompanyID();
%>
<html>
<head>
    <base href="<%=basePath%>">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <title>验货管理</title>
    <link rel="stylesheet" type="text/css" href="js/jqModal/css/flexigrid_blue.css"/>
    <link rel="stylesheet" type="text/css" href="js/jqModal/css/jqModal_blue.css"/>
    <link rel="stylesheet" href="js/jqueryplus/treeview/jquery.treeview.css"/>
    <link rel="stylesheet" type="text/css" href="page/ueditor/third-party/webuploader/webuploader.css">
    <%--<link rel="stylesheet" type="text/css" href="<%=basePath%>js/tree/codebase/dhtmlxtree.css"/>--%>
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/common/common.js"></script>
    <script type="text/javascript" src="js/flexigrid.js"></script>
    <script type="text/javascript" src="js/jqModal/jqDnR.js"></script>
    <script type="text/javascript" src="js/jqModal/jqModal.js"></script>
    <script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="js/ea/finance/invoicing/invAdjustment.js"></script>
    <%--<script type="text/javascript" src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/tree/codebase/dhtmlxtree.js"></script>--%>
    <script type="text/javascript" src="js/jqueryplus/treeview/jquery.treeview.js"></script>
    <script type="text/javascript" src="js/ea/finance/invoicing/xmTree.js"></script>
    <script type="text/javascript" src="js/xlsx.core.min.js"></script>
    <script type="text/javascript" src="page/ueditor/third-party/webuploader/webuploader.js"></script>
    <script type="text/javascript">
        var search = "${search}";
        var basePath = "<%=basePath%>";
        var pNumber =${pageNumber};
        var token = 0;
        var pageCount = '${pageForm.pageCount}';
        var notoken = 0;
        var companyID = "<%=companyID%>";
    </script>
</head>
<body>
<table class="flexme11" id="tt">
    <thead>
    <tr>
        <%--<th width="30" align="center">
            选择
        </th>--%>
        <th align="center" width="100">
            条码号
        </th>
        <th align="center" width="90">
            产品名称
        </th>
        <th align="center" width="50">
            库房
        </th>
        <th align="center" width="130">
            调整时间
        </th>
        <th align="center" width="90">
            原库存
        </th>
        <th align="center" width="50">
            调整库存
        </th>
        <th align="center" width="50">
            责任人
        </th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var='arr' items="${pageForm.list}">
        <tr id="${arr[0] }" class="xggoods">
                <%--<td>
                    <input type="checkbox" name="financialgoodID" class="JQuerypersonvalue"
                           value="${arr[0]}"/>
                </td>--%>
            <td>
                <span class="barcode">${arr[1]}</span>
            </td>
            <td>
                <span class="goodsName">${arr[2]}</span>
            </td>
            <td>
                <span class="kf">销售库</span>
            </td>
            <td>
                <span class="adddate"><fmt:formatDate value="${arr[4]}" pattern="yyyy-MM-dd HH:mm"/></span>
            </td>
            <td>
                <span class="sortCode">${arr[5]}</span>
            </td>
            <td>
                <span class="quantity">${arr[6]}</span>
            </td>
            <td>
                <span class="unitPrice">${arr[7]}</span>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<c:import url="/page/ea/page_navigator.jsp">
    <c:param name="actionPath"
             value="/ea/purchase/ea_getinspectList.jspa?search=${search}&pageNumber=${pageNumber}">
    </c:param>
</c:import>
<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
<!----------------------------------------添加---------------------------------------- -->

<div class="jqmWindow " style="width: 400px; right: 45%; top: 20%" id="jqModels">
    <form name="addForms" id="addForms" method="post" enctype="multipart/form-data">
        <%--<input type="submit" name="submit" style="display:none"/>--%>
        <div class="drag">
            添加
        </div>
        <table width="99%" id="table4">
            <tr>
                <td>
                    <table border="0" id="stafftables" align="center" cellpadding="0" cellspacing="0"
                           style="margin-top: 5px; margin-bottom: 5px;">
                        <tr>
                            <td height="40" align="right">条码号：</td>
                            <td>
                                <input type="hidden" id="invid" name="invAdjustment.invid" readonly="readonly"/>
                                <input type="hidden" id="ppid" name="invAdjustment.ppid" readonly="readonly"/>
                                <input class="notnull" type="text" id="barcode" name="invAdjustment.barcode" size="15"/>
                                <a id="cz">称重产品</a>
                            </td>
                        </tr>
                        <tr>
                            <td height="40" align="right">产品名称：</td>
                            <td>
                                <input class="notnull" type="text" id="goodsname" name="invAdjustment.goodsname"
                                       size="15" readonly="readonly"/>
                            </td>
                        </tr>
                        <tr>
                            <td height="40" align="right">单位：</td>
                            <td>
                                <input class="notnull" type="text" id="variableid" name="invAdjustment.variableid"
                                       size="15" readonly="readonly"/>
                            </td>
                        </tr>
                        <tr>
                            <td height="40" align="right">库房：</td>
                            <td>
                                <input class="notnull" type="text" id="kf" name="invAdjustment.kf" value="销售库" size="15"
                                       readonly="readonly"/>
                            </td>
                        </tr>
                        <tr>
                            <td height="40" align="right">原库存：</td>
                            <td>
                                <input class="notnull" type="text" id="invnum" name="invAdjustment.invnum" size="15"
                                       readonly="readonly"/>
                            </td>
                        </tr>
                        <tr>
                            <td height="40" align="right">调整库存：</td>
                            <td>
                                <input class="notnull" value="0" type="text" id="adjnum" name="invAdjustment.adjnum"
                                       size="15" oninput="if(value<0)value=1"/>
                            </td>
                        </tr>
                        <tr>
                            <td height="30" colspan="5" align="center">
                                <input type="button" class="input-button JQuerySubmits"
                                       style="cursor:pointer;width:80px;" value="提交"/>
                                <input name="sub" value="${session_value}" type="hidden"/><!-- 代替token-->
                                <input type="button" class="input-button JQueryreturns"
                                       style="cursor:pointer;width:80px;" value="取消"/>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
        <s:token></s:token>
    </form>
</div>

<%--******************************************物品选择****************************************--%>
<form name="goodsForm" id="goodsForm" method="post" enctype="multipart/form-data">
    <input type="submit" name="submit" style="display: none"/>
    <div class="jqmWindow jqmWindowcss1" style="top: 10%; left: 53%;" id="goodsjqModel">
        <div class="content1" style="width: 100%; height: 400px;">
            <div class="contentbannb">
                <div class="drag">
                    选择物品
                </div>
            </div>
            <table width="99%" height="33" id="goodtable" border="0"
                   align="center" cellpadding="0" cellspacing="0"
                   style="margin-top: 5px; background: #FFFFFF;">
                <tr>
                    <td width="120" align="right">
                        名称：
                    </td>
                    <td width="142">
                        <input name="typeID" class="input" id="typeID" size="10" style="margin-left: 2px;"/>
                    </td>
                    <td height="33" width="400">
                        <input type="button" class="btn02" ID="searchGood" name="button7" value="查询"/>
                        <input type="button" class="btn02" id="selectGood" name="button5" value="确定"/>
                        <input type="button" class="btn02" id="closes" name="button4" value="关闭"/>
                        <input type="hidden" name="parms" id="parms"/>
                        <input type="hidden" id="clicktr"/>
                    </td>
                    <td width="80">
                        <a id="wpsy" title="0">上一页</a>
                    </td>
                    <td width="80">
                        <a id="wpxy" title="0">下一页</a>
                    </td>
                    <td width="100">
                        <a id="wpzy">
                            共&nbsp;&nbsp;
                            <span style="color: red" id="wpzycount"></span>
                            &nbsp;&nbsp;页
                        </a>
                    </td>
                </tr>
            </table>
            <div id="body_02" style="margin-top: 2px; display: none; height: 310px; width: 100%; overflow: auto;">
            </div>
            <%--<table width="99%" border="0" align="center" cellpadding="0"
                   cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
                <tr>
                    <td width="16%">
                        <table width="100%" cellpadding="0" cellspacing="0">
                            <tr id="menuTreeTrid-1" sizcache="1" sizset="0">
                                <td>
                                    <div id="aadTree" class="text_tree"
                                         style="overflow: scroll; z-index: 99; height: 320px;">
                                        <ul id="xmul" class="filetree"></ul>
                                    </div>

                                </td>
                            </tr>
                        </table>
                    </td>
                    <td width="83%" valign="top" align="left">
                        <div id="body_02"
                             style="margin-top: 2px; display: none; height: 310px; width: 100%; overflow: auto;">
                        </div>
                    </td>
                </tr>
            </table>--%>
        </div>
    </div>
    <s:token></s:token>
</form>

<div class="jqmWindow " style="width: 400px; right: 45%; top: 20%" id="importModels">
    <form name="importForms" id="importForms" method="post" enctype="multipart/form-data">
        <%--<input type="submit" name="submit" style="display:none"/>--%>
        <div class="drag">
            导入
        </div>
        <table width="99%" id="table4">
            <tr>
                <td>
                    <table border="0" id="stafftables" align="center" cellpadding="0" cellspacing="0"
                           style="margin-top: 5px; margin-bottom: 5px;">
                        <tr>
                            <td height="40" align="right">下载模版</td>
                        </tr>
                        <tr>
                            <%--<td height="40" align="right">上传excel文件：</td>--%>
                            <td>
                                <div id="uploader" class="wu-example">
                                    <div class="btns">
                                        <div id="picker" >上传</div>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td height="30" colspan="5" align="center">
                                <input type="button" class="input-button JQuerySubmits"
                                       style="cursor:pointer;width:80px;" value="提交"/>
                                <input name="sub" value="${session_value}" type="hidden"/><!-- 代替token-->
                                <input type="button" class="input-button JQueryreturns"
                                       style="cursor:pointer;width:80px;" value="取消"/>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
        <s:token></s:token>
    </form>
</div>
</body>

</html>
