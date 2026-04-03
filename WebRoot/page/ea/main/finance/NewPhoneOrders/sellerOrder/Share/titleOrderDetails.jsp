<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>

<body>
<table>
    <tr>
        <th>
            名称及规格
        </th>
        <th>
            单位
        </th>
        <th>
            数量
        </th>
        <th>
            单价
        </th>
        <th>
            金额
        </th>
    </tr>
    <c:forEach items="${pb.goodsList}" var="gl">
        <%-- <div class="shop_mil sel_shop_mil" onclick="mallClick(this)">

                 <input type="hidden" id="ppid" value="${gl[7]}">
                 <input type="hidden" id="goodsid" value="${gl[10]}">
                 <input type="hidden" id="companyId" value="${gl[11]}">
                <div class="left">
                    <img src="<%=basePath %>${gl[6]}" alt="">
                </div>
                <div class="txt">
                    <h3>${gl[5]}</h3>
                    <h4>产品规格：<span>${gl[12]}</span></h4>
                    <h4><span></span></h4>
                </div>
                <div class="txt2">
                    <h3>&yen;<span>${gl[3]}</span></h3>
                    <h4>x<span>${gl[2]}</span></h4>
                   <c:if test="${gl[16] ne gl[2]}" >
                       <h4>待配送x<span>${gl[16]}</span></h4>
                    </c:if>
                </div>

                <input type="hidden" id="beizhu" value="${gl[14]}">
                 <input type="hidden" id="money" value="${gl[13]}">

            </div> --%>
        <tr class="tr-zl" onclick="mallClick(this)">
            <td>
                    ${gl[5]}-${gl[12]}
                <input type="hidden" id="ppid" value="${gl[7]}">
                <input type="hidden" id="goodsid" value="${gl[10]}">
                <input type="hidden" id="companyId" value="${gl[11]}">
            </td>
            <td>

            </td>
            <td class="num">
                    ${gl[2]}
            </td>
            <td class="dw">
                    ${gl[3]}
            </td>
            <td class="je">
                    ${gl[2]*gl[3]}
            </td>
        </tr>
    </c:forEach>
    <c:if test="${pt.ptgoodsList!=null }">
        <c:forEach items="${pt.ptgoodsList}" var="gr">
            <%-- <div class="shop_mil sel_shop_mil" onclick="mallClick(this)">
            <input type="hidden" id="ppid" value="${gr[7]}">
            <input type="hidden" id="goodsid" value="${gr[8]}">
            <input type="hidden" id="companyId" value="${gr[9]}">

               <div class="left">
                   <img src="<%=basePath %>${gr[6]}" alt="">
               </div>
               <div class="txt">
                   <h3>${gr[5]}</h3>
                   <h4>产品规格：<span>${gr[11]}</span></h4>
                   <h4><span></span></h4>
               </div>
               <div class="txt2">
                   <h3>&yen;<span>${gr[3]}</span></h3>
                   <h4>x<span>${gr[2]}</span></h4>
               </div>
               <img src="<%=basePath %>images/ea/finance/NewPhoneOrders/ico-cu.png" class="cu">
           </div> --%>

            <tr class="tr-zl" onclick="mallClick(this)">
                <td>
                        ${gl[5]}-${gl[11]}
                    <input type="hidden" id="ppid" value="${gr[7]}">
                    <input type="hidden" id="goodsid" value="${gr[8]}">
                    <input type="hidden" id="companyId" value="${gr[9]}">
                </td>
                <td>

                </td>
                <td class="num">
                        ${gl[2]}
                </td>
                <td class="dw">
                        ${gl[3]}
                </td>
                <td class="je">
                        ${gl[2]}*${gl[3]}
                </td>
            </tr>
        </c:forEach>
    </c:if>
</table>
<div class="mon sel_mon">
    <div class="txt">
        <!--  <h4>买家实付<p>&yen;<span id="money2"></span></p></h4> -->
    </div>
</div>
<div class="code">
    <div class="code_">
        <h4>
            <span>订单编号：</span>
            <input id="bianhao" value="${param.oaBillId}" readonly="readonly">
        </h4>
        <input type="button" value="复制" id="fuzhi">
    </div>

    <c:if test="${orderBill.xdDate!=null}">
        <h4>下单时间：<span>${orderBill.xdDate}</span></h4>
    </c:if>
    <c:if test="${orderBill.fkDate!=null}">
        <h4>付款时间：<span>${orderBill.fkDate}</span></h4>
    </c:if>
    <c:if test="${orderBill.shDate!=null}">
        <h4>收货时间：<span>${orderBill.shDate}</span></h4>
    </c:if>
    <s:if test="#request.pb.wfStatus4=='00'">
        <h4>
            支付方式：<span>微信支付</span>
        </h4>
    </s:if>
    <s:if test="#request.pb.wfStatus4=='01'">
        <h4>
            支付方式：<span>支付宝支付</span>
        </h4>
    </s:if>
    <s:if test="#request.pb.wfStatus4=='02'">
        <h4>
            支付方式：<span>银联支付</span>
        </h4>
    </s:if>
    <s:if test="#request.pb.wfStatus4=='03'">
        <h4>
            支付方式：<span>线下转账</span>
        </h4>
    </s:if>
    <s:if test="#request.pb.wfStatus4=='04'">
        <h4>
            支付方式：<span>钱盒子支付</span>
        </h4>
    </s:if>
    <h4>备　　注：<span id="beizhu2">&nbsp;${pb.remark}</span></h4>
    <c:if test="${pb.privateRoom!=null}">
        <h4>桌号:<span>&nbsp;${pb.privateRoom}</span></h4>

    </c:if>
    <div class="up_btn">
        <ul>
            <li id="up">
                <img src="<%=basePath %>images/ea/finance/NewPhoneOrders/sellerOrder/up.png" alt="">
                <p>收缩</p>
            </li>
            <li id="down">
                <img src="<%=basePath %>images/ea/finance/NewPhoneOrders/sellerOrder/down.png" alt="">
                <p>展示</p>
            </li>
        </ul>
    </div>
</div>
</body>
</html>