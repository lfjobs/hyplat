<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String backurl=request.getParameter("backurl");
%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <meta name="format-detection" content="telephone=no, email=no" />
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
    <script type="text/javascript" src="<%=basePath%>js/ea/finance/Gold_Pool/setHtmlFont.js"></script>
    <link rel="stylesheet" href="<%=basePath%>css/ea/finance/Gold_Pool/base.css">
    <link rel="stylesheet" href="<%=basePath%>css/ea/finance/Gold_Pool/mobiscroll-2.13.2.full.min.css">
    <link rel="stylesheet" href="<%=basePath%>css/ea/finance/Gold_Pool/gold_pool.css">
    <script src="<%=basePath%>js/ea/finance/Gold_Pool/jquery.min.js"></script>
    <script src="<%=basePath%>js/ea/finance/Gold_Pool/mobiscroll-2.13.2.full.min.js"></script>
	<script src="<%=basePath%>js/ea/finance/Gold_Pool/wfj_bankAdd.js" type="text/javascript"></script>
    <title>添加银行卡</title>  
    <script type="text/javascript">
		var basePath="<%=basePath%>";
		var editType="${editType}";
		var backurl="<%=backurl%>";
		var sccid="${sccid}";
		var staffid="${staffId}";
		var user="${user}";
		var mark = "${mark}";
		var bankId= "${bankId}";
		var flag = "${flag}";
		var khd="${khd}";
		var province;
		var city;
		var identifying = "${identifying}"; //判断 个人  登录还是公司登录（移动办公）
		var i;
		var submit = "00";//避免保存银行卡重复提交
		var object = new Array();
	</script>

</head>


<body>

	<s:if test="khd==0">
	    <header class="com_head">
	        <a href="javascript:history.go(-1)" class="back"></a>
	        <h1>添加银行卡</h1>
	    </header>
	 </s:if>
	 <s:else>
    	<style type='text/css'>
			.wrap_page{
				margin-top:0;
				padding-top:0;
			}
		</style>
    </s:else>      
    <div class="wrap_page">
        <div class="top_wran">请绑定银行卡</div>
        
        <form name="form" id="form" method="post">
        
        <div class="bank_info_wrap">
            <div class="ab_box">
                <div class="ab_L">开户银行</div>
                <div class="ab_R radio_text" id="bank">
                    <input type="text" class="ab_inp" id="bank_type" name="staffbank.bankName" readonly placeholder="请选择银行卡">
                    <input type="hidden" class="ab_inp" id="bank_code" name="staffbank.bankCode" >
                    <i class="down_ico"></i>
                </div>
            </div>
            <div class="ab_box">
                <div class="ab_L">开户地点</div>
                <div class="ab_R" id="area">
                    <input type="text" class="ab_inp" id="city" readonly placeholder="请选择省市"">
                    <i class="down_ico"></i>
                </div>
            </div>
            <div class="ab_box">
                <div class="ab_L">分行名称</div>
                <div class="ab_R">
                    <input type="text" class="ab_inp" id="banname" name="staffbank.branchName" placeholder="请填写详细分行名称">
                </div>
            </div>
            <div class="ab_box">
                <div class="ab_L">银行卡号</div>
                <div class="ab_R">
                    <input type="tel" class="ab_inp" id="bankid" name="staffbank.bankAccount" placeholder="请输入银行卡号">
                </div>
            </div>
            <div class="ab_box">
                <div class="ab_L">银行卡类型</div>
                <div class="ab_R radio_text" id="type">
                    <input type="text" class="ab_inp" id="card_type" name="staffbank.accountNature" placeholder="请选择类型">
                    <i class="down_ico"></i>
                </div>
            </div>           
            <div class="ab_box">
                <div class="ab_L">预留手机号</div>
                <div class="ab_R">
                    <input type="tel" id="tel" class="ab_inp" name="staffbank.tel" placeholder="请输入手机号">
                </div>
            </div>
            <div class="ab_box">
                <div class="ab_L">验证码</div>
                <div class="ab_R">
                    <input type="tel" id="code" class="ab_inp verification_inp" placeholder="请输入验证码">
                    <input type="button" class="verification_btn" value="获取验证码" onclick="sendCode(this)">
                </div>
            </div>
            <div class="ab_box">
                <div class="ab_L">持卡人姓名</div>
                <div class="ab_R">
                    <input type="text" class="ab_inp" id="name" name="staffbank.cardholder" placeholder="请输入姓名">
                </div>
            </div>
            <div class="ab_box">
                <div class="ab_L">身份证号</div>
                <div class="ab_R">
                    <input type="text" class="ab_inp" id="IDcard" name = "staffbank.idcard" placeholder="请输入身份证号">
                </div>
            </div>
        </div>
        <input type="submit" name="submit" id="submit" style="display: none;">
        <iframe name="hidden"  style="display: none;"></iframe> 
        </form>
        
        
        <a href="javascript:;" class="ab_submit" id="preservation">提交绑定</a>
    </div>
    
     <!-- 弹窗 -->
     <div id="prompt" style="width: 100%;display: none;">
        <center>
	       	<div>
	       		<span style="position: relative;top: 19.8%;"></span>
	       	</div>
       	</center>
     </div>
    
    <!--遮罩层 开始-->

    <div class="overlay">
        <img class="popup_img" src="<%=basePath%>images/ea/finance/Gold_Pool/popup_img.gif" style="height:100%;width:100%;object-fit:contain;display:none;" alt="">
        <!--选择银行卡 开始-->
        <ul id="bank_list" style="display: none">
            <li>中国工商银行<input type="hidden" value="1002"></li>
            <li>中国建设银行<input type="hidden" value="1003"></li>
            <li>中国银行<input type="hidden" value="1026"></li>
            <li>中国农业银行<input type="hidden" value="1005"></li>
            <li>中国交通银行<input type="hidden" value="1020"></li>
            <li>招商银行<input type="hidden" value="1001"></li>
            <li>浦发银行<input type="hidden" value="1004"></li>
            <li>中信银行<input type="hidden" value="1021"></li>
            <li>中国光大银行<input type="hidden" value="1022"></li>
            <li>华夏银行<input type="hidden" value="1025"></li>
            <li>中国民生银行<input type="hidden" value="1006"></li>
            <li>兴业银行<input type="hidden" value="1009"></li>
            <li>平安银行<input type="hidden" value="1010"></li>
            <li>中国邮储银行<input type="hidden" value="1066"></li>
            <li>广发银行<input type="hidden" value="1027"></li>
            <li>北京银行<input type="hidden" value="1032"></li>
            <li>宁波银行<input type="hidden" value="1056"></li>
        </ul>
 
        <!--选择银行卡 结束-->
        <!--选择省市 开始-->
        <ul id="area_list" style="display: none">

        </ul>
        <!--选择省市 结束-->
        <!--选择银行卡类型 开始-->
        <ul id="type_list" style="display: none">
            <li>借记卡</li>
            <li>信用卡</li>
            <li>储蓄卡</li>
        </ul>
        <!--选择银行卡类型 结束-->
        <!--选择对公对私 开始-->
        <ul id="public_list" style="display: none">
            <li>对公</li>
            <li>对私</li>
        </ul>
        <!--选择对公对私 结束-->
    </div>
    <!--遮罩层 结束-->
    <script>       
        //处理浏览器输入法遮挡
        var screenH=window.innerHeight;
         window.onresize = function () {
            var t=window.innerHeight;    
             console.log(t);
             console.log(screenH);
            var inp=$("input:focus")[0];
            if(t<screenH){
               inp.scrollIntoView(false);
            }
         }       
    </script>
</body>

</html>
