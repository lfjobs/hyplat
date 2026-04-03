<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
    <title>评价订单</title>
    <script type="text/javascript" src="<%=basePath %>js/BuildPlatform/font-size.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/ea/finance/NewPhoneOrders/evaluate_style.css"/>
    <script type="text/javascript" src="<%=basePath %>js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>js/ea/finance/NewPhoneOrders/OrderEvaluation.js"></script>
    
</head>
<script type="text/javascript">
var basePath='<%=basePath%>';
</script>
<body>
<div class="header">
    <ul>
        <li style="width: 10%;"><a href="javascript:history.back()"><img src="<%=basePath %>images/ea/finance/NewPhoneOrders/left.png"></a></li>
        <li style="width: 80%;text-align: center;">评价订单</li>
        <li style="width: 10%"></li>
    </ul>
</div>

<div class="content_hidden">
    <div class="content evaluate">
    <form action="" id="from1" method="post" enctype="multipart/form-data">
    	<input type="hidden" name="comments.cashierBillsID" value="${list[0][0] }">
    	<input type="hidden" name="comments.ppID" value="${list[0][5]}">
    	<input type="hidden" name="comments.csid" value="${list[0][1] }">
    	<input type="hidden" name="key" id="key" value="${list[0][3] }">
     	<input type="hidden" id="companyID" name="companyID" value="${list[0][4]}">
     	<input type="hidden" name="comments.evaluation" id="evaluation">
        <input type="hidden" id="user" value="${list[0][10] }"/>
        <div class="shop_mil sel_shop_mil">
            <div class="left">
                <img src="<%=basePath %>${list[0][6]}" alt="">
            </div>
            <div class="txt">
                <h3>${list[0][7] }</h3>
                <h4>产品规格：<span>${list[0][8] }</span></h4>
                <h4>备注：<span></span></h4>
            </div>
            <div class="txt2">
                <h3>&yen;<span>${list[0][2] }</span></h3>
                <h4>x<span>${list[0][9] }</span></h4>
            </div>
        </div>
        <!--评论-->
        <div class="txt_con_">
            <textarea rows="3" onfocus="document.getElementById('note').style.display='none'" onblur="if(value=='')document.getElementById('note').style.display='block'" class="txt_con" name="comments.comments"></textarea>
            <div id="note" class="note">请输入评价内容...</div>
        </div>
        <!--添加图片-->
        <div class="up_pic eva">
            <div class="btn_ btn_3">
                <img src="<%=basePath %>images/ea/finance/NewPhoneOrders/camera.png" alt="" id="image_box">
            </div>
        </div>
        <!--好评差评-->
        <div class="options_">
            <ul>
                <li>
                     <div class="imge">
                         <img src="<%=basePath %>images/ea/finance/NewPhoneOrders/good.png">
                         <img src="<%=basePath %>images/ea/finance/NewPhoneOrders/good_.png">
                     </div>
                    <p>好评</p>
                </li>
                <li>
                    <div class="imge">
                        <img src="<%=basePath %>images/ea/finance/NewPhoneOrders/mean.png">
                        <img src="<%=basePath %>images/ea/finance/NewPhoneOrders/mean_.png">
                    </div>
                    <p>中评</p>
                </li>
                <li>
                    <div class="imge">
                        <img src="<%=basePath %>images/ea/finance/NewPhoneOrders/bad.png">
                        <img src="<%=basePath %>images/ea/finance/NewPhoneOrders/bad_.png">
                    </div>
                    <p>差评</p>
                </li>
            </ul>
        </div>

        <!--店铺评分-->
        <div class="grade">
            <h3>店铺评分</h3>
            <ul>
                <li>
                    <h5>描述相符</h5>
                    <div class="star">
                        <input type="hidden" name="comments.score1" class="score">                   
                        <img src="<%=basePath %>images/ea/finance/NewPhoneOrders/star.png" alt="">
                        <img src="<%=basePath %>images/ea/finance/NewPhoneOrders/star.png" alt="">
                        <img src="<%=basePath %>images/ea/finance/NewPhoneOrders/star.png" alt="">
                        <img src="<%=basePath %>images/ea/finance/NewPhoneOrders/star.png" alt="">
                        <img src="<%=basePath %>images/ea/finance/NewPhoneOrders/star.png" alt="">
                    </div>
                </li>
                <li>
                    <h5>物流服务</h5>
                    <div class="star">
                        <input type="hidden" name="comments.score2" class="score">                    
                        <img src="<%=basePath %>images/ea/finance/NewPhoneOrders/star.png" alt="">
                        <img src="<%=basePath %>images/ea/finance/NewPhoneOrders/star.png" alt="">
                        <img src="<%=basePath %>images/ea/finance/NewPhoneOrders/star.png" alt="">
                        <img src="<%=basePath %>images/ea/finance/NewPhoneOrders/star.png" alt="">
                        <img src="<%=basePath %>images/ea/finance/NewPhoneOrders/star.png" alt="">
                    </div>
                </li>
                <li>
                    <h5>服务态度</h5>
                    <div class="star">
                        <input type="hidden" name="comments.score3" class="score">                 
                        <img src="<%=basePath %>images/ea/finance/NewPhoneOrders/star.png" alt="">
                        <img src="<%=basePath %>images/ea/finance/NewPhoneOrders/star.png" alt="">
                        <img src="<%=basePath %>images/ea/finance/NewPhoneOrders/star.png" alt="">
                        <img src="<%=basePath %>images/ea/finance/NewPhoneOrders/star.png" alt="">
                        <img src="<%=basePath %>images/ea/finance/NewPhoneOrders/star.png" alt="">
                    </div>
                </li>
            </ul>
        </div>
  	<jsp:include page="/page/prompt.jsp"/>
	</form>
    </div>
    <!--提交评价-->
    <div class="evaluate_btn">
        <button>确认提交评价</button>
    </div>
</div>
</body>
</html>