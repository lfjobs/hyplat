<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Date" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>
<head>
    <meta charset="utf-8"/>
    <title>入库单添加</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/ea/finance/invoicing/rukuBill_add.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/scMobile/payBudget/addBudget/swiper/public.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/scMobile/payBudget/addBudget/swiper/swiper.min.css"/>
    <script type="text/javascript" charset="utf-8"
            src="<%=basePath %>js/pkModule/checkinv/checkInvList/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" charset="utf-8"
            src="<%=basePath %>js/scMobile/payBudget/addBudget/swiper/swiper.min.js"></script>
    <script type="text/javascript" charset="utf-8"
            src="<%=basePath %>js/scMobile/payBudget/addBudget/swiper/dySelect.js"></script>
    <script type="text/javascript" charset="utf-8"
            src="<%=basePath %>js/scMobile/payBudget/addBudget/font-size.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath %>js/WFJClient/localforage.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath %>js/ea/finance/invoicing/rukuBill_add.js"></script>
    <script ype="text/javascript" charset="utf-8" src="<%=basePath%>js/ea/marketing/supermarket/LodopFuncs.js"></script>
    <script>
        var basePath = "<%=basePath%>";
        var comid = "${param.companyid}";//公司id
        var staffid = "${param.staffid}";//登录人id
        var sccid = "${param.sccid}";//登录人id
    </script>
</head>
<body class="hy">
<header>
    <ul class="clearfix">
        <li onclick="toBack();">
            <img src="<%=basePath %>images/scMobile/payBudget/addBudget/register_return.png"/>
        </li>
        <li>
            入库单<c:if test="${bill==null}">添加</c:if>
        </li>
        <c:if test="${bill!=null}">
            <li style="font-size: .6rem;" id="print" onclick="toPrint();">
                打印
            </li>
        </c:if>
    </ul>
</header>
<div class="select_box select_box1"></div>
<div class="content">
    <section class="sec_con1">
        <form id="billform" name="billform">
            <c:if test="${journalNum!=null}">
                <p>
                    <label for=""><span></span>收货单号：</label>
                    <input type="text" name="jn" id="jn" value="${journalNum}" readonly/>
                </p>
            </c:if>
            <p>
                <label for=""><span></span>入库单号：</label>
                <input type="text" name="jum" id="jum" value="${jum}" readonly/>
                <input type="hidden" name="companyid" id="companyid" value="${param.companyid}" readonly/>
                <input type="hidden" name="cid" id="cid" value="${cid}" readonly/>
                <input type="hidden" name="tid" id="tid" value="${tid}" readonly/>
            </p>
            <p class="gys">
                <label for=""><span></span>供货商：</label>
                <input type="text" name="gcname" id="gcname" value="${ghsname}" readonly/>
                <input type="hidden" name="gcid" id="gcid" value="${ghsid}"/>
            </p>
            <div class="clearfix">
                <p class="cgy">
                    <label for=""><span></span>入库人：</label>
                    <input type="text" name="cgstaffname" id="cgstaffname" value="${bill.cgstaffname}" readonly/>
                    <input type="hidden" name="cgstaffid" id="cgstaffid"/>
                </p>
                <p>
                    <label for=""><span></span>仓库：</label>
                    <input type="text" name="warehousename" id="warehousename" value="${bill.warehousename}" readonly/>
                    <input type="hidden" name="warehouse" id="warehouse"/>
                </p>
            </div>
            <div class="clearfix">
                <p>
                    <label for=""><span></span>制单人：</label>
                    <input type="text" name="inistaffname" id="inistaffname" value="${bill.staffname}" readonly/>
                    <input type="hidden" name="inistaffid" id="inistaffid" readonly/>
                </p>
                <p>
                    <label for=""><span>*</span>制单日期： </label>
                    <input type="text" name="inidate" id="inidate" value="${bill.adddate}" readonly/>
                </p>
            </div>
            <p>
                <label for=""><span></span>单据状态： </label>
                <input type="text" value="入库" readonly/>
            </p>
        </form>
    </section>
    <section class="sec_con2">
        <c:if test="${bill!=null}">
            <table>
                <tr>
                    <td>名称及规格</td>
                    <td>发货数</td>
                    <td>收货数</td>
                    <td>验货数</td>
                    <td>入库数</td>
                </tr>
                <% int number = 1; %>
                <c:forEach var='arr' items="${gb}">
                    <tr class="rgid">
                        <td class="goodname">${arr.goodname}</td>
                        <td class="quantity1">${arr.quantity}</td>
                        <td class="requantity">${arr.quantity}</td>
                        <td class="isqualify">${arr.quantity}</td>
                        <td class="quantity2">${arr.quantity}</td>
                    </tr>
                    <% number++; %>
                </c:forEach>
            </table>
            <%--<div class='clearfix'>
                <div class='left'><p>共<%=number%>种商品</p></div>
                <div class='right'><p class='smli'>扫码</p>
                    <p class='czli'>无码称重</p>
                    <p id='add'>保存</p></div>
            </div>--%>
        </c:if>
    </section>
    <section class="sec-ull">
        <c:if test="${bill==null}">
            <ul class="ul_con3 clearfix">
                    <%--<li>
                        <p class="p_img">
                            <img src="<%=basePath %>images/scMobile/payBudget/addBudget/yusuandan_01_07.png"/>
                        </p>
                        <p class="p_p">
                            扫码枪
                        </p>
                    </li>--%>
                <li class="smli">
                    <p class="p_img">
                        <img src="<%=basePath %>images/scMobile/payBudget/addBudget/yusuandan_01_09.png"/>
                    </p>
                    <p class="p_p">
                        扫码
                    </p>
                </li>
                <li class="czli">
                    <p class="p_img">
                        <img src="<%=basePath %>images/scMobile/payBudget/addBudget/yusuandan_01_09.png"/>
                    </p>
                    <p class="p_p">
                        无码称重
                    </p>
                </li>
                    <%--<li>
                        <p class="p_img">
                            <img src="<%=basePath %>images/scMobile/payBudget/addBudget/yusuandan_01_11.png"/>
                        </p>
                        <p class="p_p">
                            物品管理
                        </p>
                    </li>
                    <li>
                        <p class="p_img">
                            <img src="<%=basePath %>images/scMobile/payBudget/addBudget/yusuandan_01_13.png"/>
                        </p>
                        <p class="p_p">
                            商城
                        </p>
                    </li>
                    <li>
                        <p class="p_img">
                            <img src="<%=basePath %>images/scMobile/payBudget/addBudget/yusuandan_01_15.png"/>
                        </p>
                        <p class="p_p">
                            常用采购单
                        </p>
                    </li>--%>
            </ul>
        </c:if>
    </section>
</div>
<form id="hiddenform" name="hiddenform">
    <input type="hidden" id="jn" name="jn" value="${journalNum}"/>
    <input type="hidden" name="caid" id="caid" value="${cid}" readonly/>
    <input type="hidden" name="trid" id="trid" value="${tid}" readonly/>
    <c:forEach var='arr' items="${ol}">
        <div id="${arr[1]}" class="hiddendiv" style="display: none">
            <input type="hidden" class="pid" name="pid" value="${arr[1]}"/>
            <input type="hidden" class="gid" name="gid" value="${arr[0]}"/>
            <input type="hidden" class="gname" name="gname" value="${arr[2]}"/>
            <input type="hidden" class="gborcode" name="gborcode" value=""/>
            <input type="hidden" class="gqnum" name="gqnum" value="${arr[3]}"/>
                <%--<input type="hidden" id="" name="" value=""/>--%>
        </div>
    </c:forEach>
</form>
</body>
</html>
