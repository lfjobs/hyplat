<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>订单详情</title>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/ea/finance/NewPhoneOrders/sellerOrder/style.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/ea/finance/NewPhoneOrders/sellerOrder/selle_style.css"/>
    <script type="text/javascript" src="<%=basePath %>js/jquery-2.0.0.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/common/common.js"></script>
    

</head>
<script>
var basePath = "<%=basePath%>";
var companyid = "${param.companyid}";
var journalNum ="${param.oaBillId}";
var cashierBillsIDs ="${param.cashierBillsID}";
var s1="${param.typel}";
var s2="${param.trade_no}";
var s3="${param.out_trade_no}";
var s4="${param.refund_amount}";
var fkStatus="${pb.fkStatus}";
</script>
<body>
<div class="header">
    <ul>
        <li style="width: 10%;"><a  href="javascript:history.go(-1);"><img src="<%=basePath %>images/ea/finance/NewPhoneOrders/sellerOrder/left.png"></a></li>
        <li style="width: 80%;text-align: center;">订单详情</li>
        <li style="width: 10%"></li>
    </ul>
</div>
<div class="content_hidden">
    <div class="content rec_content">
       <jsp:include page="/page/ea/main/finance/NewPhoneOrders/sellerOrder/Share/titleaddress.jsp"/>
        
        <!-- 引入外部jsp -->
       <jsp:include page="/page/ea/main/finance/NewPhoneOrders/sellerOrder/Share/titleCentral.jsp"/>
 		<!-- 引入外部jsp -->
       <jsp:include page="/page/ea/main/finance/NewPhoneOrders/sellerOrder/Share/titleOrderDetails.jsp"/>
        <div class="appraise2">
            <ul>
                <li class="blue">
                    <h4>买家提出了申请</h4>
                    <div class="txt">
                        <p>发起了退货退款申请，货物状态：
                        
                        <c:if test="${refundSheet.refundType=='00'}">
                        仅退款<span></span></p>
                        </c:if>
                        <c:if test="${refundSheet.refundType=='01'}">
                        退款退货<span></span></p>
                        </c:if>
                        <c:if test="${refundSheet.refundType=='02'}">
                        换货<span></span></p>
                        </c:if>
                        <c:if test="${refundSheet.refundType=='03'}">
                        维修<span></span></p>
                        </c:if>
                        <p>退款金额：&yen;<span>${refundSheet.refundMoney}</span></p>
                        <div style="overflow: hidden;">
                            <p><span style="float: left;">原因：</span>
                            <p style="float: left;width: 80%;margin: 0;">${refundSheet.refundCause}</p>
                        </div>
                    </div>
                    <div class="img">
                    <c:if test="${refundSheet.voucherfile1!=null}">
                        <i><img src="<%=basePath %>${refundSheet.voucherfile1}" alt=""></i>
                        </c:if>
                        <c:if test="${refundSheet.voucherfile2!=null}">
                        
                        <i><img src="<%=basePath %>${refundSheet.voucherfile2}" alt=""></i>
                        </c:if>
                        <c:if test="${refundSheet.voucherfile3!=null}">
                        <i><img src="<%=basePath %>${refundSheet.voucherfile3}" alt=""></i>
                         </c:if>
                    </div>
                </li>
                <li class="orange">
                    <h4 style="border-bottom: 1px solid #fff;">请处理退款<p class="time4"><img src="<%=basePath %>images/ea/finance/NewPhoneOrders/sellerOrder/ico-time2.png" alt=""><span>剩01天23小时26分</span></p></h4>
                    <div class="txt">
                        <p><span style="padding-right: 0.2rem;float: left;padding-bottom: 0.1rem;">•</span>
                        </p><p style="float: left;width: 90%;margin: 0;margin-bottom: 0.1rem;"><span>如未发货，请点击退款给买家</span></p>
                        <p><span style="padding-right: 0.2rem;float: left;padding-bottom: 0.1rem;">•</span>
                        </p><p style="float: left;width: 90%;margin: 0;margin-bottom: 0.1rem;"><span>如实际已发货，请主动与买家联系确认后再去订单详情页面上操作发货</span></p>
                        <p><span style="padding-right: 0.2rem;float: left;padding-bottom: 0.1rem;">•</span>
                        </p><p style="float: left;width: 90%;margin: 0;margin-bottom: 0.5rem;"><span>逾期未处理，系统将自动退款给买家</span></p>

                        <div style="clear: both;"></div>
                        <p style="border-top: 1px solid #666;padding-top: 0.5rem;color: #666;">编号：<span>${refundSheet.refundCode}</span></p>
                    </div>
                </li>
               
                <!-- 同意退款 -->
                <li class="gray" id="ty02" style="display: none;">
                    <h4>商家已同意申请</h4>
                    <div class="txt">
                        <p><span style="padding-right: 0.2rem;">•</span>商家同意了这次售后申请服务</p>
                        <p><span style="padding-right: 0.2rem;">•</span>退款金额：￥${refundSheet.refundMoney}</p>
                    </div>
                </li>
                 <!-- 同意退款 -->
                 <%--<li class="gray" id="ty022" style="display: none;">
                    <div class="txt">
                        <p><span>${refundSheet.refundMoney}</span></p>
                    </div>
                </li>--%>
                <!-- 同意退款 -->
                <li class="orange" id="ty021" style="display: none;">
                    <h4 style="border-bottom: 1px solid #fff;">退款成功</h4>
                    <div class="txt">
                        <p><span style="padding-right: 0.2rem;float: left;padding-bottom: 0.5rem;">•</span></p><p style="float: left;width: 90%;margin: 0;margin-bottom: 0.5rem;"><span>可与买家协商退款具体事项</span></p>
                        <div style="clear: both;"></div>
                        <p style="border-top: 1px solid #666;padding-top: 0.5rem;color: #666;">编号：<span>${refundSheet.refundCode}</span></p>
                    </div>
                </li>
               <!--  //拒绝的 -->
                <li class="gray" id="ju01" style="display: none;">
                    <h4>商家已拒绝申请</h4>
                    <div class="txt">
                        <p><span style="padding-right: 0.2rem;">•</span>商家拒绝了这次售后服务申请</p>
                    </div>
                </li> 
                <!--  //拒绝的 -->
                <li class="orange" id="ju011" style="display: none;">
                    <h4 style="border-bottom: 1px solid #fff;">退款失败</h4>
                    <div class="txt">
                        <p><span style="padding-right: 0.2rem;float: left;padding-bottom: 0.5rem;">•</span></p><p style="float: left;width: 90%;margin: 0;margin-bottom: 0.5rem;"><span>可与买家协商退款具体事项</span></p>
                        <div style="clear: both;"></div>
                        <p style="border-top: 1px solid #666;padding-top: 0.5rem;color: #666;">编号：<span>${refundSheet.refundCode}</span></p>
                    </div>
                </li>
            </ul>
        </div>

    </div>
    <div class="btn">
        <%--<a href="#;"><input type="button" value="联系买家"></a>--%>
        <s:if test="%{#request.refundSheet.refundType == '00'}">
            <s:if test="%{#request.refundSheet.refundstate == '00'}">
                <a onclick="refusetoReturn(this)"><input type="button" value="拒绝退款" id="jujue"></a>
                <a onclick="refusetoReturn(this)"><input type="button" value="同意退款" id="tongyi"></a>
            </s:if>
        </s:if>
        <s:elseif test="%{#request.refundSheet.refundType == '01'}">
            <s:if test="%{#request.refundSheet.refundstate == '00'}">
                <a onclick="agreetoReturn(this)"><input type="button" value="拒绝退货" id="jujue02"></a>
                <a onclick="agreetoReturn(this)"><input type="button" value="同意退货" id="tongyi02"></a>
            </s:if>
        </s:elseif>
    </div>
    <!-- 引入外部jsp -->
       <%-- <jsp:include page="/page/ea/main/finance/NewPhoneOrders/sellerOrder/Share/titlebottom.jsp"/>
         --%>
</div>

<script>

	var wfStatus4  = "${param.typel}";
    var trade_no  = "${param.trade_no}";
    var orderBill = "${param.out_trade_no}";
    var money = "${request.pb.priceSub}";
    var type = "${param.type}";
    var refundState = '${request.refundSheet.refundstate}';
    var refundCode ='${request.refundSheet.refundCode}';
    var wfStatus1 ='${param.wfStatus1}';
    $(document).ready(function()
    {
        $(".header ul li").css("line-height",$(window).height()*0.08+"px");
        $(".header").css("height",$(window).height()*0.08+"px");
        $(".content_hidden").css("height",$(window).height()*0.92+"px");
        $(".content").css("height",$(window).height()*0.92-40+"px");
        $(".so_shop ul li img").css("height",$(".so_shop ul li img").width()+"px");


        //卖家同意
        if (refundState =='05')
        {
            $('.orange').hide();
            $('#ty02').show();
            $('#ty022').show();
            $('#ty021').show();
        }
        //卖家拒绝
        else if (refundState == '02')
        {
            $('.orange').hide();
            $('#ju01').show();
            $('#ju011').show();
        }

		$(".up_btn #up").click(function()
        {
            $(this).hide().siblings().show();
            $(".mon .txt h5").hide();
            $(".code h4").hide();
            $(".code .code_").hide();
        });
        $(".up_btn #up").click(function()
        {
            $(this).hide().siblings().show();
            $(".mon .txt h5").hide();
            $(".code h4").hide();
            $(".code .code_").hide();
        });
        $(".up_btn #down").click(function()
        {
            $(this).hide().siblings().show();
            $(".mon .txt h5").show();
            $(".code h4").show();
            $(".code .code_").show();
        });
        
    })
</script>
<script type="text/javascript" src="<%=basePath%>js/ea/finance/NewPhoneOrders/sellerOrder/order.js"></script>

<script>
    window.onload = window.onresize = function(){
        var clientWidth = document.documentElement.clientWidth;
        document.getElementsByTagName('html')[0].style.fontSize = clientWidth/640*40+'px'
    }
</script>
</body>
</html>
