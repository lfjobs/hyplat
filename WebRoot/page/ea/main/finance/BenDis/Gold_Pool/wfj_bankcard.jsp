<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
    <link rel="stylesheet" href="<%=basePath%>css/ea/finance/Gold_Pool/gold_pool.css">
    <script src="<%=basePath%>js/ea/finance/Gold_Pool/jquery.min.js"></script>
    <script src="<%=basePath%>/js/ea/finance/Gold_Pool/zepto.min.js"></script>
	<script src="<%=basePath%>/js/ea/finance/Gold_Pool/touchwipe.js"></script>
	<%--<script src="<%=basePath%>/js/ea/finance/Gold_Pool/event.min.js"></script>--%>
    <%--<script src="<%=basePath%>/js/ea/finance/Gold_Pool/touch.min.js"></script>    --%>
    <script src="<%=basePath%>js/ea/finance/Gold_Pool/wfj_bankcard.js"></script>    
	<script type="text/javascript">
	 	var basePath="<%=basePath%>";
	 	var staffid="${staffId}";
	 	var sccid="${sccid}";
	 	var mark = "${mark}";
	 	var user = "${user}";
	 	var flag = "${flag}";
	 	var khd = "${khd}";
	 	var bankId = "${bankId}";
	 	var identifying = "${identifying}"; //判断 个人  登录还是公司登录（移动办公）
	 	var object = new Array();
	 	var code;//验证码
	</script>
	<style>
		/* 添加手机号选择框样式 */
		.box_sec_2_select {
			width: 60%;
			height: 1rem;
			border: 1px solid #ddd;
			border-radius: 0.1rem;
			padding: 0 0.2rem;
			font-size: 0.5rem;
			color: #333;
			background-color: #fff;
			margin-left: 0.2rem;
			vertical-align: middle;
		}
		.box_sec_2_select option {
			font-size: 0.3rem;
			padding: 0.1rem;
		}

		.box_sec_2_input {
			float: right;
			width: 29%;
			padding-right: .35rem;
			height: 1rem;
			line-height: 1rem;
			border: 0;
			font-size: .5rem;
			color: #f74c32;
			background-color: #fff;
		}
	</style>

    <title>我的银行卡</title>
</head>

<body>

	<s:if test="khd==0">
	    <header class="com_head">   
	    	<s:if test="mark==00">
	    	     <a href="<%=basePath%>/ea/jinbi/ea_getwfjtixian.jspa?user=${user}&sccid=${sccid}&khd=${khd}&flag=${flag}&staffid=${staffId}&mark=${mark}&bankId=${bankId}&identifying=${identifying}" class="back"></a> 	
	    	</s:if>
	    	<s:elseif test="mark==01">
	    	    <a href="<%=basePath%>/ea/jinbi/ea_gethyjifen.jspa?user=${user}&sccid=${sccid }&khd=${khd}" class="back"></a>    	   	   	
	    	</s:elseif>    	
	    	<s:else>
	    	   <a href="javascript:history.go(-1)" class="back"></a> 	
	    	</s:else>
		
	        <h1>我的银行卡</h1>
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
		<ul class="bangding">
			<li class="bd_li">
				<div class="btn_jc clearfix weChat">
					<div class="left_div">
						<img class="left_div_img" src="<%=basePath%>images/ea/finance/Gold_Pool/weixin.png"/>
					</div>
					<p class="cen_p">
						微信账户
					</p>
					<span  class="state cen_span">(尚未绑定)</span>
				</div>
				<a href="###" class="relieve_btn"><input type="hidden" id="reWeChat" >解除绑定</a>
			</li>
			<li class="bd_li">
				<div class="btn_jc clearfix Alipay">
					<div class="left_div">
						<img class="left_div_img"  src="<%=basePath%>images/ea/finance/Gold_Pool/zfb.png"/>
					</div>
					<p class="cen_p">
						支付宝账户
					</p>
					<span class="state cen_span"></span>
				</div>
				<a href="###" class="relieve_btn"><input type="hidden" id="reAli" >解除绑定</a>
			</li>
		</ul>
        <div class="bank_wrap">
        	<c:forEach items="${list}" var="a">
	            <div class="bank_btn_wrap" id="${a.bankAccountID}">        
	                <div class="bank_box clearfix">	                                    
	                    <!-- 显示对应银行的log -->
	                    <c:if test="${a.bankName == '中国工商银行'}">
	                    	<img src="<%=basePath%>images/ea/finance/Gold_Pool/gs.png" class="bank_ico" alt="">
	                    </c:if>
	                    <c:if test="${a.bankName == '中国建设银行'}">
	                    	<img src="<%=basePath%>images/ea/finance/Gold_Pool/js.png" class="bank_ico" alt="">
	                    </c:if>
	                    <c:if test="${a.bankName == '中国银行'}">
	                    	<img src="<%=basePath%>images/ea/finance/Gold_Pool/zg.png" class="bank_ico" alt="">
	                    </c:if>
	                    <c:if test="${a.bankName == '中国农业银行'}">
	                    	<img src="<%=basePath%>images/ea/finance/Gold_Pool/ny.png" class="bank_ico" alt="">
	                    </c:if>
	                    <c:if test="${a.bankName == '中国交通银行'}">
	                    	<img src="<%=basePath%>images/ea/finance/Gold_Pool/jt.png" class="bank_ico" alt="">
	                    </c:if>
	                    <c:if test="${a.bankName == '招商银行'}">
	                    	<img src="<%=basePath%>images/ea/finance/Gold_Pool/zs.png" class="bank_ico" alt="">
	                    </c:if>
	                    <c:if test="${a.bankName == '浦发银行'}">
	                    	<img src="<%=basePath%>images/ea/finance/Gold_Pool/pf.png" class="bank_ico" alt="">
	                    </c:if>
	                    <c:if test="${a.bankName == '中信银行'}">
	                    	<img src="<%=basePath%>images/ea/finance/Gold_Pool/zx.png" class="bank_ico" alt="">
	                    </c:if>
	                    <c:if test="${a.bankName == '中国光大银行'}">
	                    	<img src="<%=basePath%>images/ea/finance/Gold_Pool/gd.png" class="bank_ico" alt="">
	                    </c:if>
	                    <c:if test="${a.bankName == '华夏银行'}">
	                    	<img src="<%=basePath%>images/ea/finance/Gold_Pool/hx.png" class="bank_ico" alt="">
	                    </c:if>
	                    <c:if test="${a.bankName == '中国民生银行'}">
	                    	<img src="<%=basePath%>images/ea/finance/Gold_Pool/ms.png" class="bank_ico" alt="">
	                    </c:if>
	                    <c:if test="${a.bankName == '兴业银行'}">
	                    	<img src="<%=basePath%>images/ea/finance/Gold_Pool/xy.png" class="bank_ico" alt="">
	                    </c:if>
	                    <c:if test="${a.bankName == '平安银行'}">
	                    	<img src="<%=basePath%>images/ea/finance/Gold_Pool/pa.png" class="bank_ico" alt="">
	                    </c:if>	
	                    <c:if test="${a.bankName == '中国邮政储蓄银行'|| a.bankName == '中国邮储银行'}">
	                    	<img src="<%=basePath%>images/ea/finance/Gold_Pool/youz.png" class="bank_ico" alt="">
	                    </c:if>                                   	                    
	                    <div class="bank_text">
	                        <div class="bank_name">${a.bankName}</div>
	                        <div class="bank_n">
	                        ${fn:substring(a.bankAccount,0,4 )}********${fn:substring(a.bankAccount,12,a.bankAccount.length())}
							</div>
							<div class="bank_s">分行:${a.branchName==null?"无":a.branchName}</div>
	                    </div>
	                </div>          
	                <a href="###" class="relieve_btn"><input type="hidden" id="bankId" value="${a.bankAccountID}">解除绑定</a>
	            </div>
            </c:forEach>                    
        </div> 
        <a href="<%=basePath %>ea/perinfor/ea_getaddBankCardInformation.jspa?staffId=${staffId}&sccid=${sccid}&user=${user}&mark=${mark}&identifying=${identifying}&flag=${flag}&khd=${khd}&bankId=${bankId}&editType=00" class="add_card">
            <i class="addcard_ico"></i>
            <span>添加新的银行卡</span>
        </a>
    </div>
	<div id="Bbox" class="Bbox">
		<div class="content">
			<div class="box">
				<section class="box_sec_1">
					<img class="box_sec_1_img" src="<%=basePath%>images/ea/finance/Gold_Pool/verification_code.png"/>
				</section>
				<section class="box_sec_2">
					<img class="box_sec_2_img" src="<%=basePath%>images/ea/finance/Gold_Pool/phone.png"/>
					<%--<div id="phone" class="box_sec_2_div_1"></div>--%>
					<!-- 修改这里：将原来的div改为select下拉框 -->
					<select id="phoneSelect" class="box_sec_2_select">
						<option value="">请选择手机号</option>
					</select>
					<input type="button" name="" id="getCode"  class="box_sec_2_input" value="获取验证码" />
					<div id="num_js"  class="num_js">
						60
					</div>
				</section>
				<section  class="box_sec_3">
					<p class="box_sec_3_p">输入验证码</p>
					<div id="shoujihao" class="shoujihao">
						<div class="container" id="test">
						<input id="val-code-input" class="" type="text" maxlength="6" onkeyup="checkForNum(this)"  onselectstart="return false;" onblur="checkForNum(this)" />
						<div class="val-box" id="val-box">
						<div class="val-item"></div>
						<div class="val-item"></div>
						<div class="val-item"></div>
						<div class="val-item"></div>
						<div class="val-item"></div>
						<div class="val-item"></div>
						                        </div>
						                    </div>
						                </div>
				</section>
				<footer class="footer">
					<%--<img id="Bbox_close" class="footer_img" src="<%=basePath%>images/ea/finance/Gold_Pool/code_close.png"/>--%>
					<input class="footer_inp_1" type="button" name="" id="confirm" value="确定" />
					<input class="footer_inp_2" type="button" name="" id="Bbox_close" value="取消" />
				</footer>
			</div>
		</div>
	</div>
</body>
    <script>
        /*左划事件  */
//        Zepto(".bank_box").swipeLeft(function(){
//            $(this).addClass("bank_box_L").parent().siblings().find(".bank_box").removeClass("bank_box_L")
//        });
//        Zepto(".bank_box").swipeRight(function(){
//            $(this).removeClass("bank_box_L")
//        })
//        $(".bank_box").click(function(){
//            $(this).addClass("bank_box_L").parent().siblings().find(".bank_box").removeClass("bank_box_L")
//        });

//        Zepto(".btn_jc").swipeLeft(function(){
//            $(this).addClass("bank_box_L").parent().siblings().find(".btn_jc").removeClass("bank_box_L")
//        });
//        Zepto(".btn_jc").swipeRight(function(){
//            $(this).removeClass("bank_box_L")
//        })
        $("#Bbox_close").click(function(){
            code = "";
            $("#Bbox").hide();
            $("#val-code-input").val("");
            $("#getCode").show();
            $("#num_js").hide();
            clearTimeout(set);
        })
        //验证码
        $(function(){
            var valCodeInput = $("#val-code-input");
            var valCodeItems = $("div[class='val-item']");
            var regex = /^[\d]+$/;
            var valCodeLength = 0;
            $('#val-box').on('click',function(){
                valCodeInput.focus();
            })
//              input输入框即时反映
            valCodeInput.on('input propertychange change', function(e){
                valCodeLength = valCodeInput.val().length;
                if(valCodeInput.val() && regex.test(valCodeInput.val())) {
                    $(valCodeItems[valCodeLength - 1]).removeClass('available');
                    $(valCodeItems[valCodeLength - 1]).addClass('available');
                    $(valCodeItems[valCodeLength - 1]).text(valCodeInput.val().substring(valCodeLength - 1, valCodeLength));
                }
            })
//              点击获取验证码或点击第一个数字输入框时获取焦点,添加available类样式
            $(".daojishibtn,div[class='val-item']").on("tap",function(){
                $(valCodeInput).focus();
                $(valCodeItems[0]).addClass('available');
            })
//              删除键
            $(this).on('keyup', function(e){
                if(e.keyCode === 8) {
                    $(valCodeItems[valCodeLength]).text("");
                    if(valCodeLength !== 0){
                        $(valCodeItems[valCodeLength]).removeClass('available');
                    }
                    if($(".val-item:first-of-type").text()==""){
                        $(".val-item:first-of-type").removeClass('available')
                    }
                }
            });
        })
        //          把所有输入的不是数字的字符转换为空值
        function checkForNum(obj) {
            obj.value = obj.value.replace(/[\D]/g, '');
        }
        var time=60;
        $("#getCode").click(function(){
            time = 60;
            $(this).hide();
            $("#num_js").show();
            getRandomCode();
        })
        //倒计时
        function getRandomCode() {
            $("#num_js").text(time);
            time--;
             	set = setTimeout(function() {
                getRandomCode();
            },1000);
            if(time==0){
                $("#getCode").show();
                $("#num_js").hide();
                clearTimeout(set);
            }
        }

    </script>    
</html>
