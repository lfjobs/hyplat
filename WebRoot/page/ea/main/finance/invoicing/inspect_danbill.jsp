<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="cache-control" content="no-cache"/>
    <title>验货单显示数据</title>
    <%@ page language="java" pageEncoding="UTF-8" %>
    <%@ taglib uri="/struts-tags" prefix="s" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
                + request.getServerName() + ":" + request.getServerPort()
                + path + "/";
    %>
    <link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"
          type="text/css"/>
    <link rel="stylesheet" type="text/css"
          href="<%=basePath%>css/admin_main111.css"/>
    <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
    <script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
    <link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
          type="text/css"/>
    <script language="javascript" type="text/javascript"
            src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
    <script language="javascript" type="text/javascript"
            src="<%=basePath%>js/ea/finance/invoicing/inspect_dan.js"></script>
    <link rel="stylesheet" type="text/css"
          href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
    <script type="text/javascript"
            src="<%=basePath%>js/jqModal/jqModal.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/overlayer.js"></script>
    <link rel="stylesheet" type="text/css"
          href="<%=basePath%>css/overlayer.css"/>
    <link rel="stylesheet"
          href="<%=basePath%>js/tree/common/css/style.css" type="text/css"
          media="screen"/>
    <script src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js"></script>
    <script src="<%=basePath%>js/tree/codebase/dhtmlxtree.js"></script>
    <link rel="STYLESHEET" type="text/css"
          href="<%=basePath%>js/tree/codebase/dhtmlxtree.css"/>
    <script src="<%=basePath%>js/ea/human/cstaff.js"></script>
    <script type="text/javascript"
            src="<%=basePath%>js/common/organizationTree.js"></script>

    <style type="text/css">
        body {
            margin-left: 0px;
            margin-top: 0px;
            margin-right: 0px;
            margin-bottom: 0px;
        }

        .STYLE1 {
            font-size: 8px;
            font-weight: bold;
        }
    </style>
    <script type="text/javascript">
        var basePath = "<%=basePath%>";
        var pNumber = '${param.pageNum}';
        var pageCount = '${param.pageCount}';
        var token = 0;
        var loginstaffcheck =${loginstaffcheck};
        //------------------------------选择接收人
        $(document).ready(function () {
            //取消
            $("#DaoRuFan").click(function () {
                $("#bankJqm").jqmHide();
            });
            $("#DaoRuFanqd").click(function () {// 选择确定
                var checkopertionID = $("#checkopertionID", $("#bankJqm")).attr("value");
                var checkform = $("#checkform", $("#bankJqm")).attr("value");
                var checkopertionName = $("#checkopertionName", $("#bankJqm")).attr("value");
                var childopertionName = $("#childopertionName", $("#bankJqm")).attr("value");
                var childopertionID = window.frames["daoRu"].opertionID;
                if (childopertionID == "") {
                    alert("请选择人员！")
                    return;
                }
                var no = window.frames["daoRu"].$('tr#' + childopertionID).find("span#" + checkopertionName).text();
                var childopertionName = window.frames["daoRu"].$('tr#' + childopertionID).find("span#" + childopertionName).text();
                if (checkopertionID != "")
                    $("#" + checkopertionID, $("#" + checkform)).attr("value", childopertionID).trigger("blur");
                if (checkopertionName != "") {
                    $("#" + checkopertionName, $("#" + checkform)).attr("value", childopertionName).trigger("blur");
                }
                if (checkopertionName == "partnerName") {
                    var final = no + "---" + childopertionName;
                    $("#" + checkopertionName, $("#" + checkform)).attr("value", final).trigger("blur");
                }
                $("#daoRu").attr("src", "");
                $("#bankJqm").jqmHide();
            });
        });
        function importGY(attachSearchTable, checkopertionID, checkopertionName, childopertionName, url) { //打开页面
            if (checkopertionName == "bankNum") {
                var departmentID = $("#departmentID").attr("value");
                url = url + "?departmentID=" + departmentID;
            }
            $("#checkopertionID", $("#bankJqm")).attr("value", checkopertionID);
            $("#checkform", $("#bankJqm")).attr("value", attachSearchTable);
            $("#checkopertionName", $("#bankJqm")).attr("value", checkopertionName);
            $("#childopertionName", $("#bankJqm")).attr("value", childopertionName);
            $("#daoRu").attr("src", basePath + url);
            $("#bankJqm").jqmShow();
        }
        //提交form表单
        function child() {
            $("#billGoodsForm").attr("target", "_parent").attr(
                "action",
                basePath + "/ea/purchase1/ea_toUpdateIsQualify.jspa?type=hgrk");
            document.billGoodsForm.submit.click();
            document.billGoodsForm.reset();
            alert("操作成功！");
        }

    </script>
    <script type="text/javascript">
        //选择库房
        var basePath = '<%=basePath%>';
        var pNumber = '${pageNumber}';
        var treeid;
        var treename;
        var date;
        var token = 0;
        var tree1;
        $(document).ready(function () {
            tree1 = new dhtmlXTreeObject("ckaadTree", "100%", "100%", 0);
            tree1.enableDragAndDrop(false);
            tree1.enableHighlighting(1);
            tree1.enableCheckBoxes(0);
            tree1.enableThreeStateCheckboxes(false);
            tree1.setSkin(basePath + 'js/tree/dhx_skyblue');
            tree1.setImagePath(basePath + "js/tree/codebase/imgs/");
            tree1.loadXML(basePath + "js/tree/common/tree_b.xml");
            tree1.insertNewChild("0", "001", "实物仓库", 0, 0, 0, 0);
            //tree1.insertNewChild("0","002","资料仓库",0,0,0,0);
            //tree1.insertNewChild("0","003","财务仓库",0,0,0,0);
            tree1.setOnClickHandler(function () {
                if (token)return;
                token = 1;
                $(".input01").attr("value", "");
                $("#desc").attr("html", "");
                treeid = tree1.getSelectedItemId();
                treename = tree1.getItemText(treeid);
                $("#codeName").attr("value", treename);
                tree1.deleteChildItems(treeid);
                var getListCCodeurl = basePath + "ea/cdepotmanage/sajax_ea_getListDepotmanageByPID.jspa?depotID=" + treeid + "&date=" + new Date().toLocaleString();
                $.ajax({
                    url: encodeURI(getListCCodeurl),
                    type: "get",
                    async: true,
                    dataType: "json",
                    success: function cbf(data) {
                        var member = eval("(" + data + ")");
                        var nologin = member.nologin;
                        if (nologin) {
                            document.location.href = basePath + "page/ea/not_login.jsp";
                        }
                        var depotManagelist = member.depotManagelist;

                        if (null == depotManagelist) {
                            return;
                        }
                        for (var i = 0; i < depotManagelist.length; i++) {
                            tree1.insertNewChild(treeid, depotManagelist[i].depotID, depotManagelist[i].depotName, 0, 0, 0, 0);
                            tree1.setUserData(depotManagelist[i].depotID, "depotState", depotManagelist[i].depotState);
                        }
                        token = 0;

                    },
                    error: function cbf(data) {
                        alert("数据获取失败！");
                    }
                });

                $("#mainframe").attr(
                    "src", basePath + "ea/cdepotmanage1/ea_getListDepotManageTreenew.jspa?pageNumber=0&depotID=" + treeid + "&treename=" + treename + "&usetype=ck");
                $(window).resize();

            });

            var url = basePath + "ea/cashierbills/sajax_ea_getBillID.jspa?date=" + new Date().toLocaleString();
            var str = "";
            $.ajax({
                url: url,
                type: "get",
                async: false,
                dataType: "json",
                success: function cbf(data) {
                    var member = eval("(" + data + ")");
                    $("#journalNum").val(member.BillID);
                    var nologin = member.nologin;
                    if (nologin) {
                        document.location.href = basePath + "page/ea/not_login.jsp";
                    }
                },
                error: function cbf(data) {
                    alert("数据获取失败！")
                }
            });
        });
    </script>
</head>
<body>
<div style="width: 900px;text-align:center;">
    <%--<c:forEach var="arr" items="${pageForm.list}" varStatus="step">--%>
        <form name="billGoodsForm" id="billGoodsForm" method="post"
              enctype="multipart/form-data">
            <input type="submit" name="submit" style="display: none"/>
            <input type="hidden" name="financialbillID" value="${financialBill.financialbillID}"/>
            <input type="hidden" id="journalNumcg" value="${financialBill.journalNum}"/>
            <input type="hidden" name="financialBill.groupCompanySn"
                   id="groupCompanySn" value="${financialBill.groupCompanySn}"/>
            <input type="hidden" name="financialBill.companyID"
                   id="companyID" value="${financialBill.companyID}"/>
            <input type="hidden" name="financialBill.organizationID"
                   id="organizationID" value="${financialBill.organizationID}"/>
            <input type="hidden" name="financialBill.departmentID"
                   id="departmentID" value="${financialBill.departmentID}"/>
            <input type="hidden" name="financialBill.goodsmoney"
                   id="goodsmoney" value="${financialBill.goodsmoney}"/>
            <input type="hidden" name="financialBill.purchaseDate"
                   id="purchaseDate" value="${financialBill.purchaseDate}"/>
            <div style="width:100%;text-align:center;">
                <table width="800" border="0" id="table1" align="center"
                       cellpadding="0" cellspacing="0" style="margin-left:10px;background: #FFFFFF;">
                    <tr>
                        <td colspan="6" align="center" id="titlestyle">
                            <span>验货单</span><span style="display:none;">（<%--${pageForm.pageNumber}--%>）</span>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" colspan="5" height="25">单据编号：</td>
                        <td align="left">
                            <input type="text" style="width: 160px;" class="inputbottom"
                                   id="journalNum" name="financialBill.journalNum"
                                   readonly="readonly" value="${param.jnum}"/></td>
                    </tr>
                    <tr>
                        <td height="25" align="right">
                            采购单位：
                        </td>
                        <td align="left">
                            <input type="hidden" id="ccompanyID"
                                   name="financialBill.ccompanyID"
                                   value="${financialBill.ccompanyID}"/>
                            <input type="text" id="ccompanyName" style="width: 160px;"
                                   class="inputbottom" readonly="readonly" name="financialBill.ccompanyName"
                                   value="${financialBill.ccompanyName}"/>
                        </td>
                        <td align="right">
                            联系电话：
                        </td>
                        <td align="left">
                            <input type="text" id="companyTel" style="width: 160px;"
                                   class="inputbottom" readonly="readonly"
                                   name="financialBill.ccompanyTel" value="${financialBill.ccompanyTel}"/>
                        </td>
                        <td align="right">
                            预付定金：
                        </td>
                        <td align="left">
                            <input type="text" style="width: 160px;"
                                   class="inputbottom" readonly="readonly"
                                   value="${financialBill.frontMoney==null?0:financialBill.frontMoney}&nbsp;${financialBill.moneyType}"/>
                            <input type="hidden" name="financialBill.frontMoney" value="${financialBill.frontMoney}"
                                   id="frontMoney"/>
                            <input type="hidden" name="financialBill.moneyType" value="${financialBill.moneyType}"
                                   id="moneyType"/>
                        </td>
                    </tr>
                    <tr>
                        <td height="25" align="right">
                            采购员：
                        </td>
                        <td align="left">
                            <input type="hidden" id="staffID" name="financialBill.staffID" value="${financialBill.staffID}"/>
                            <input type="text" style="width: 160px;"
                                   class="inputbottom" readonly="readonly" id="staffName"
                                   name="financialBill.staffName" value="${financialBill.staffName}"/>

                        </td>
                        <td align="right">
                            联系电话：
                        </td>
                        <td align="left">
                            <input type="text" id="staffPhone" style="width: 160px;"
                                   class="inputbottom" readonly="readonly"
                                   name="financialBill.staffPhone" value="${financialBill.staffPhone}"/>
                        </td>
                        <td align="right">
                            <div id="u1170_rtf">
                                付款方式：
                            </div>
                        </td>
                        <td align="left" colspan=2>
                            <input type="text" id="staffPhone" style="width: 160px;"
                                   class="inputbottom" readonly="readonly"
                                   name="financialBill.paymentType" value="${financialBill.paymentType==00?'请选择':financialBill.paymentType==01?'银行转帐':financialBill.paymentType==02?'银行支票转账'
                                  :financialBill.paymentType==03?'支付宝转帐':financialBill.paymentType==04?'App转账':financialBill.paymentType==05?'POS机转账'
                                  :financialBill.paymentType==06?'现金转账':''}"/>
                        </td>
                    </tr>
                    <tr>
                        <td height="25" align="right">
                            <span style="color:red;">*</span>验货日期：
                        </td>
                        <td align="left">
                            <input type="text" class="notnull inputbottom" style="width: 160px;"
                                   onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="examinegoodsDate"
                                   name="financialBill.examinegoodsDate"/>
                        </td>
                        <td align="right">
                            对方科目：
                        </td>
                        <td align="left">
                            <input type="hidden" value="${financialBill.subjectID}" id="subjectID"
                                   name="financialBill.subjectID"/>
                            <input type="text" value="${financialBill.subjectName}" style="width: 160px;"
                                   class="inputbottom" readonly="readonly"
                                   id="subjectName" name="financialBill.subjectName"/>
                        </td>
                        <td align="right">
                            <div id="u1170_rtf">
                                物流方式：
                            </div>
                        </td>
                        <td align="left" colspan=2>
                            <input type="text" id="staffPhone" style="width: 160px;"
                                   class="inputbottom" readonly="readonly"
                                   name="financialBill.logisticsType" value="${financialBill.logisticsType}"/>
                        </td>
                    </tr>
                    <tr>
                        <td height="25" align="right">
                            <span style="color:red;">*</span>收货仓库：
                        </td>
                        <td align="left">
                            <input name="financialBill.depotName" id="depotName" style="width: 160px;"
                                   placeholder="单击选择仓库" class="notnull inputbottom" readonly="readonly">
                            <input type="hidden" name="financialBill.depotID" id="depotID"/>
                        </td>
                        <td align="right">
                            <span style="color:red;">*</span>验货人：
                        </td>
                        <td align="left" id="dept">
                            <input type="hidden" id="partnerID" name="financialBill.staffsID"
                                   readonly="readonly"
                                   value="${financialBill.staffsID==null?ManStaffId:financialBill.staffsID}"/>
                            <input type="text" id="partnerName"
                                   onclick="importGY('table1','partnerID','partnerName','childPartnerName','ea/cosincumbent/ea_getStaffForCashier.jspa')"
                                   style="width: 160px;" readonly="readonly"
                                   name="financialBill.staffsName"
                                   value="${financialBill.staffsName==null?ManStaffName1:financialBill.staffsName}"
                                   class="notnull inputbottom" readonly="readonly"/>
                        </td>
                        <td align="right">
                            <div id="u1170_rtf">
                                <input type="hidden" class="ACT_btn" name="button" value="关联票据"/>
                                单据状态：
                            </div>
                        </td>
                        <td align="left" colspan=2>
                            <input type="checkbox" id="yyh" name="yyh" disabled/>已验货
                            <input type="checkbox" id="yrk" name="yrk" disabled/>合格已入库
                            <input type="checkbox" id="yth" name="yth" disabled/>不合格已退货
                        </td>
                    </tr>
                </table>
                <table width="800" height="200px" border="0" align="center"
                       cellpadding="0" cellspacing="0" style="margin-left:10px;margin-top: 5px">
                    <tr>
                        <td valign="top">
                            <div id="Layer1"
                                 style="position: absolute; width:800px; height: 200px; overflow: auto;">
                                <table align="center" cellpadding="0" width=800"
                                       cellspacing="0" class="table" id="goodtable">
                                    <tr>
                                        <th align="center" bgcolor="#E4F1FA">
                                            序号
                                        </th>
                                        <th align="center" bgcolor="#E4F1FA">
                                            物品名称
                                        </th>
                                        <th align="center" bgcolor="#E4F1FA">
                                            单价
                                        </th>
                                        <th align="center" bgcolor="#E4F1FA">
                                            收货数量
                                        </th>
                                        <th align="center" bgcolor="#E4F1FA">
                                            合格数量
                                        </th>
                                        <th align="center" bgcolor="#E4F1FA">
                                            不合格数量
                                        </th>
                                        <th align="center" bgcolor="#E4F1FA">
                                            单位
                                        </th>
                                        <th align="center" bgcolor="#E4F1FA">
                                            科目
                                        </th>
                                        <th align="center" bgcolor="#E4F1FA">
                                            收货金额
                                        </th>
                                        <th align="center" bgcolor="#E4F1FA">
                                            合格金额
                                        </th>
                                        <th align="center" bgcolor="#E4F1FA">
                                            供货商
                                        </th>
                                        <th align="center" bgcolor="#E4F1FA">
                                            备注
                                        </th>
                                    </tr>
                                    <c:forEach items="${billgoodsmap}" var="item">
                                        <c:if test="${financialBill.journalNum==item.key}">
                                            <%
                                                int number = 1;
                                            %>
                                            <c:forEach items="${item.value}" var="list" varStatus="ltm">
                                                <tr id="td${ltm.index}" class="checkgoods" height="30">
                                                    <td align="center" bgcolor="#FFFFFF">
                                                        <span><%=number%></span>
                                                        <input type="hidden" id="goodsBillsID"
                                                               name="goodsbillmap[${ltm.index}].goodsBillsID"
                                                               value="${list.goodsBillsID}"/>
                                                    </td>
                                                    <td align="center" bgcolor="#FFFFFF">
                                                        <span>${list.goodsName}</span>
                                                    </td>
                                                    <td align="center" bgcolor="#FFFFFF">
                                                        <span id="price">${list.price}</span>
                                                    </td>
                                                    <td align="center" bgcolor="#FFFFFF">
                                                        <span id="reQuantity">${list.reQuantity}</span>
                                                    </td>
                                                    <td align="center" bgcolor="#FFFFFF">
                                                        <span style="color:red;">*</span>
                                                        <input type="text" id="isQualify"
                                                               style="height:100%;border:0px;" size="5"
                                                               name="goodsbillmap[${ltm.index}].isQualify"
                                                               maxlength="10" class="posnum jisuan put3 input"/>
                                                    </td>
                                                    <td align="center" bgcolor="#FFFFFF">
                                                        <input type="text" id="noisQualify" style="border: 0"
                                                               readonly="readonly" size="5" name="noisQualify"/>
                                                    </td>
                                                    <td align="center" bgcolor="#FFFFFF">
                                                        <span>${list.goodsVariableID}</span>
                                                    </td>
                                                    <td align="center" bgcolor="#FFFFFF">
                                                        <span>${list.subjectsName}</span>
                                                    </td>
                                                    <td align="center" bgcolor="#FFFFFF">
                                                        <span id="money">${list.money}</span>
                                                    </td>
                                                    <td align="center" bgcolor="#FFFFFF">
                                                        <span id="hgamount"></span>
                                                        <input type="hidden" id="bhgamount"/>
                                                    </td>
                                                    <td align="center" bgcolor="#FFFFFF">
                                                        <span id="ccompanyName">${list.ccompanyName}</span>
                                                    </td>
                                                    <td align="center" bgcolor="#FFFFFF">
                                                        <span>${list.remindedContent}</span>
                                                    </td>
                                                </tr>
                                                <%number++;%>
                                            </c:forEach>
                                        </c:if>
                                    </c:forEach>
                                </table>
                            </div>
                        </td>
                    </tr>
                </table>
                <br/>
                <table width="800" border="0" id="table2" align="center"
                       cellpadding="0" cellspacing="0" style="background: #FFFFFF;">
                    <tr>
                        <td width="100" align="right">
                            <span>收货金额合计：</span>
                        </td>
                        <td width="150" align="left">
                            <span id="scountmoney"></span>
                        </td>
                        <td width="125" align="right">
                            <span>合格金额合计：</span>
                        </td>
                        <td width="125" align="left">
                            <span id="hgcountmoney"></span>
                        </td>
                        <td width="150" align="right">
                            <span>不合格金额合计：</span>
                        </td>
                        <td width="150" align="left">
                            <span id="bhgcountmoney"></span>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="6" height="10"></td>
                    </tr>
                    <tr>
                        <td align="right">
                            <span>制&nbsp;&nbsp;&nbsp;&nbsp;单&nbsp;&nbsp;&nbsp;&nbsp;人：</span>
                        </td>
                        <td>
                            <input type="text" style="width: 150px;"
                                   class="inputbottom" readonly="readonly" id="spname"
                                   value="${ManStaffName1}"/>
                        </td>
                        <td align="right">
                            <span>制&nbsp;&nbsp;单&nbsp;&nbsp;日&nbsp;&nbsp;期：</span>
                        </td>
                        <td>
                            <input type="text" style="width: 150px;"
                                   class="inputbottom" readonly="readonly" id="zddate"
                                   value="${financialBill.billsDate==null?param.curDateTime:financialBill.billsDate}"/>
                        </td>
                        <td align="right">

                        </td>
                        <td>

                        </td>
                    </tr>
                </table>
            </div>
            <s:token></s:token>
        </form>
    <%--</c:forEach>--%>
</div>
<%--<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
   <tr><td colspan="2">&nbsp;</td></tr>
   <tr>
   <td align="left" width="50px">备注：</td>
   <td align="left" colspan="9"><input type="text" id="remark" class="inputbottom" style="width:80%;"/></td>
   </tr>
</table>
   <table width="99%" border="0" cellpadding="0" cellspacing="0" id="audittbl">
<tr><td>
<input type="hidden" id="staffauditname"
value="${ManStaffName}">
<input type="hidden" id="staffauditcode"
value="${ManStaffCode}">
<input type="hidden" id="staffauditid"
value="${ManStaffId}">
</td></tr>
<tr>
    <td height="25" align="right">
        公司经理：
    </td>
    <td>
        <input type="text" class="inputbottom gsjl" value="${billcheckmap['gsjl']}" readonly="readonly"/>
        <c:if test='${billcheckmap["gsjl"]==null||billcheckmap["gsjl"]==""}'>
        <input type="button" class="btncon verify" id="gsjl" />
        </c:if>
    </td>
    <td align="right">
        部门主管：
    </td>
    <td>
        <input type="text" class="inputbottom bmzg" value="${billcheckmap['bmzg']}" readonly="readonly"/>
        <c:if test='${billcheckmap["bmzg"]==null||billcheckmap["bmzg"]==""}'>
        <input type="button" class="btncon verify" id="bmzg" />
        </c:if>
    </td>
    <td  align="right">
        人事处：
    </td>
    <td>
        <input type="text" class="inputbottom rsc" value="${billcheckmap['rsc']}" readonly="readonly"/>
        <c:if test='${billcheckmap["rsc"]==null||billcheckmap["rsc"]==""}'>
        <input type="button" class="btncon verify" id="rsc"/>
        </c:if>
    </td>
    <td  align="right">
        财务审核：
    </td>
    <td>
        <input type="text" class="inputbottom cwsh" value="${billcheckmap['cwsh']}" readonly="readonly"/>
        <c:if test='${billcheckmap["cwsh"]==null||billcheckmap["cwsh"]==""}'>
        <input type="button" class="btncon verify" id="cwsh"/>
        </c:if>
    </td>
    <td  align="center">
        收款人确认：
    </td>
    <td>
        <input type="text" class="inputbottom skr" value="${billcheckmap['skr']}"readonly="readonly"/>
        <c:if test='${billcheckmap["skr"]==null||billcheckmap["skr"]==""}'>
        <input type="button" class="btncon verify" id="skr"/>
        </c:if>
    </td>
</tr>
<tr>
    <td  height="25" align="right">
        总部总经理：
    </td>
    <td>
        <input type="text" class="inputbottom zjl" value="${billcheckmap['zjl']}" readonly="readonly"/>
        <c:if test='${billcheckmap["zjl"]==null||billcheckmap["zjl"]==""}'>
        <input type="button" class="btncon verify"  id="zjl"/>
        </c:if>
    </td>
    <td  align="right">
        总部部门主管：
    </td>
    <td>
        <input type="text" class="inputbottom zg" value="${billcheckmap['zg']}" readonly="readonly"/>
        <c:if test='${billcheckmap["zg"]==null||billcheckmap["zg"]==""}'>
        <input type="button" class="btncon verify" id="zg"/>
        </c:if>
    </td>
    <td  align="right">
        总部人事处：
    </td>
    <td>
        <input type="text" class="inputbottom zbrsc" value="${billcheckmap['zbrsc']}" readonly="readonly"/>
        <c:if test='${billcheckmap["zbrsc"]==null||billcheckmap["zbrsc"]==""}'>
        <input type="button" class="btncon verify" id="zbrsc"/>
        </c:if>
    </td>
    <td align="right">
        总财务审核：
    </td>
    <td>
        <input type="text" class="inputbottom zbcw" value="${billcheckmap['zbcw']}" readonly="readonly"/>
        <c:if test='${billcheckmap["zbcw"]==null||billcheckmap["zbcw"]==""}'>
        <input type="button" class="btncon verify" id="zbcw"/>
        </c:if>
    </td>
    <td  align="center">
        交款人确认：
    </td>
    <td>
        <input type="text" class="inputbottom jkr" value="${billcheckmap['jkr']}" readonly="readonly"/>
        <c:if test='${billcheckmap["jkr"]==null||billcheckmap["jkr"]==""}'>
        <input type="button" class="btncon verify" id="jkr"/>
        </c:if>
    </td>
</tr>
</table>
--%><%--*****************选择仓库****************--%>
<form name="ckForms" id="ckForms" method="post"
      enctype="multipart/form-data">
    <input type="submit" name="submit" style="display: none"/>
    <div class="jqmWindow jqmWindowcss1" style="top: 5%;left: 53%;"
         id="ckjqModel">
        <div class="content1" style="width: 100%; height: 400px;">
            <div class="contentbannb" style="text-align:left;">
                <div class="drag">选择仓库</div>
            </div>
            <table width="99%" height="33" id="searchck" border="0"
                   align="center" cellpadding="0" cellspacing="0"
                   style="margin-top: 5px; background: #FFFFFF;">
                <tr>
                    <td height="33" align="center">
                        <input type="button" class="btn02" id="ckok" name="button5" value="确定"/>
                        <input type="button" class="btn02 xzck" name="button" value="新增"/>
                        <input type="button" class="btn02 JQueryreturns" name="button4" value="关闭"/>
                        <input type="hidden" name="parmss" id="parmss"/>
                    </td>
                </tr>
            </table>
            <form name="codeForm" method="post"></form>
            <div class="main_main">
                <table width="99%" border="0" align="center" cellpadding="0"
                       cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
                    <tr>
                        <td id="qh_sw" style="width: 15%;" valign="top">
                            <div id="ckaadTree" class="text_tree"
                                 style="overflow: scroll; z-index: 99; height: 300px;"></div>
                        </td>
                        <td style="width: 84%;" valign="top">
                            <iframe src="" name="ccode" width="100%" height="300" marginwidth="0" marginheight="0"
                                    scrolling="yes" frameborder="0" id="mainframe" border="0" framespacing="0"
                                    noresize="noResize" vspale="0"></iframe>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
    <s:token></s:token>
</form>
<%--******************************************选择接收人****************************************--%>
<div id="bankJqm" class="jqmWindow"
     style="width: 95%; height: 400px; absolute; display: none; left: 1%; top: 1%; background: #eff; overflow-x: hidden; overflow-y: auto;">
    <div style="background: #efg; margin-right: 500px;">
        <input style="display: none;" id="checkopertionID"/>
        <input style="display: none;" id="checkopertionName"/>
        <input style="display: none;" id="childopertionName"/>
        <input style="display: none;" id="checkform"/>
    </div>
    <iframe name="daoRu" id="daoRu" width="100%" height="360px"
            frameborder="0" style="overflow-x: hidden; overflow-y: auto;"></iframe>
    <div align="center">
        <input type="button" class="input-button" id="DaoRuFanqd"
               value=" 确定 " style="cursor: hand; border: 0;"/>
        <input type="button" class="input-button" id="DaoRuFan" value=" 关闭 "
               style="cursor: hand; border: 0;"/>
    </div>
</div>
<script type="text/javascript">
    //库房管理
    setTimeout(function () {
        $(".bDiv").css({"height": $(window).height() - 31 - 30 - 26 - 30 + "px"});
    }, 100);

    $(window).resize(function () {
        setTimeout(function () {
            $(".bDiv").css({"height": $(window).height() - 31 - 30 - 26 - 30 + "px"});
        }, 100);
    });
</script>
</body>
</html>