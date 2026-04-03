<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" import="com.tiantai.wfj.bo.TEshopCusCom"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<html>
<head>
    <meta charset="UTF-8">
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport" />
    <script type="text/javascript" src="<%=basePath%>js/WFJClient/setHtmlFont.js"></script>
    <link rel="stylesheet" href="<%=basePath%>css/ea/office_ea/makeApp/parkingpay.css">
    <script src="<%=basePath%>js/jquery-2.1.1.min.js"></script>
    <script src="<%=basePath%>js/font-size.js"></script>
    
    <script src="<%=basePath%>js/ea/office_ea/makeApp/parking/parkingPay.js"></script>
      <script type="text/javascript" src="<%=basePath %>/js/WFJClient/ap.js"></script>
    <title>停车收费</title>
    <script type="text/javascript">
        var basePath="<%=basePath%>";
        var carNum = "${carNum}";
        var ppid = "${ppid}";
        var timeUnits = "${timeUnits}";
        var carmID = "${carManage.carmID}";
        var companyName = "${companyName}";
        var newAdd = "${param.newAdd}";
        var carNumber = "${carManage.carNumber}";
        var money = "${carManage.money}";
        var time = "${carManage.time}";
        var equip = "${param.equip}";
        var status = "${param.status}";
        var staffid = '<%=((TEshopCusCom) session.getAttribute("key_shop_cus_com")) != null
					? ((TEshopCusCom) session.getAttribute("key_shop_cus_com"))
							.getStaffid() : ""%>';
        var sccid='<%=((TEshopCusCom) session.getAttribute("key_shop_cus_com")) != null
					? ((TEshopCusCom) session.getAttribute("key_shop_cus_com"))
							.getSccId() : ""%>';

    </script>
</head>
<body>

<div class="lice_con">

    <ul class="top">

        <li>
            <span style="display: none" class="zjcp">${carManage.carNumber eq null?carNum:carManage.carNumber}</span>
            <span>车牌号：${carManage.carNumber eq null?carNum:carManage.carNumber}</span><img class="edit" src="<%=basePath%>/images/parkkingpay/ico-b.png" alt=""></li>
       <c:if test="${carManage.time ne null}">
           <li>用时：${carManage.time}</li>

       </c:if>
        <%--<li>单价：${price}/${timeUnits=="0"?"小时":timeUnits=="1"?"天":timeUnits=="2"?"月":"年"}</li>--%>
        <%--<li>收费：${carManage.money}元</li>--%>
    </ul>
    <c:if test="${carManage.time ne null}">


    <div class="div-p">
        <p class="p-txt">本次停车默认收费方式是${timeUnits=="0"?"按小时计时":timeUnits=="1"?"包天计时":timeUnits=="2"?"包月计时":"包年计时"}</p>
        <p class="p-txt">消费金额为${carManage.money}元您还可以选择其他会员</p>
    </div>

    </c:if>
<c:if test="${carManage.time eq null}">
    <div class="div-p">
        <p class="p-txt">您可以选择购买以下会员套餐</p>
    </div>
</c:if>
    <div class="bg">
        <img src="<%=basePath%>/images/parkkingpay/bg1.png" alt="">
        <input type="text" style="display:none;" id="morre" value="${carManage.money}" />
    </div>
    <div class="div-js">
        <ul class="clearfix">
            <c:forEach items="${feelist}" var="item" varStatus="state">
                <c:if test="${state.index == 0}">

                    <li class="active">
                </c:if>
                <c:if test="${state.index != 0}">

                    <li>
                </c:if>
                    <p>${item[5]== "金币计时"?"小时计时":item[5]}</p>
                    <p>￥${item[1]}/${item[3]== "0"?"小时":item[3]== "1"?"天":item[3]=="2"?"月":"年"}</p>
                   <input type="hidden"  class="price" value="${item[1]}"/>
                   <input type="hidden"  class="ppid" value="${item[0]}"/>
                   <input type="hidden"  class="timeUnits" value="${item[3]}"/>
                </li>
            </c:forEach>



        </ul>
    </div>
</div>

<div class="footer">
    <h4>选择支付方式<span>合计:<span class="hj">${carManage.money}</span></span></h4>
    <ul>
        <li class="active"><img src="<%=basePath%>/images/parkkingpay/ico-w.png" alt="" name="2">微信支付</li>
        <li ><img src="<%=basePath%>/images/parkkingpay/izo-z.png" alt="" name="1">支付宝支付</li>



        <input type="button" value="支付" onclick="pay()">
    </ul>

    <%--<div style="text-align: center;--%>
    <%--color: red;--%>
    <%--width: 50%;--%>
    <%--margin: 1.5rem auto;--%>
    <%--padding: 0.5rem;--%>
    <%--font-size: 0.9rem;--%>
    <%--border: 1.5px solid #18a9e1;--%>
    <%--border-radius: 1rem 1rem;--%>
    <%--font-weight: bold;--%>
<%--">--%>
        <%--交费后动一动车</br>二次识别即抬杆--%>
    <%--</div>--%>
</div>

<div class="alert_w_">
    <div class="alert_w">
        <h4>温馨提示</h4>
        <p class="tipcontent">该车牌没有进入记录，请确定正确的车牌号。</p>
        <input type="button" value="确定">
    </div>
</div>
<div class="alert_c_">
    <div class="alert_c">
     <p class="gbp"><img src="<%=basePath%>images/parkkingpay/close.png"   onclick="back();"></p>
        <h4>请填写车辆信息</h4>
        <form action=""  method="post">
            <h5><span>✲</span>绑定车牌号</h5>
            <input type="text" placeholder="请输入车牌号" id="cp">
            <%--<h5>发动机号</h5>--%>
            <%--<input type="text" placeholder="请输入发动机号(选填)" id="fdj">--%>
            <input type="button" value="确定" id="qd" onclick="check();">
        </form>
    </div>
</div>

<div class="alert_dh">
		<div class="zfz">

			<img src="<%=basePath%>images/supermarket/zfz.png" alt="">
			<p>正在支付中...</p>
		</div>
	</div>
</body>




</html>
