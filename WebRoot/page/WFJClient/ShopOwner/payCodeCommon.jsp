
<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<script type="text/javascript"  src="<%=basePath%>js/ea/marketing/wfjeshop/payCodeCommon.js"></script>
<link rel="stylesheet" href="<%=basePath%>/css/WFJClient/NEWjspcss/paycodeCommon.css" type="text/css"></link>


<div class="overlay">
    <div class="popup_pay">
        <div class="pay_hd clearfix">
            <span>请输入支付密码</span>
            <a href="javascript:;" class="close_btn"></a>
        </div>
        <div class="pay_bd">
            <div>
                <span class="rmb_ico">￥</span><span class="sum_num"></span>
            </div>
            <div class="pay_text">支付金额</div>
        </div>
        <div class="pay_fd">
            <ul class="pay_label clearfix">
                <li></li>
                <li></li>
                <li></li>
                <li></li>
                <li></li>
                <li></li>
            </ul>
            <input type="tel" maxlength="6" class="pay_inp" oninput="payInput(this)" />
        </div>
    </div>
</div>


<!-- 弹窗 -->
<div id="prompt" style="width: 100%;display: none;">
    <center>
        <div>
            <span style="position: relative;top: 19.8%;"></span>
        </div>
    </center>
</div>
