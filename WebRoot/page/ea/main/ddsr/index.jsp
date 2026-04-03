<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- saved from url=(0040)http://www.ciorstore.com/jrx/peixun.html -->
<html xmlns="http://www.w3.org/1999/xhtml">
<head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<base href="<%=basePath%>">
<meta name="viewport" content="width=device-width, minimum-scale=1,maximum-scale=1,initial-scale=1,user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="yes" name="apple-touch-fullscreen">
<meta content="telephone=no" name="format-detection">
<meta content="email=no" name="format-detection">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<link rel="Stylesheet" type="text/css" href="http://www.ciorstore.com/jrx/css/css.css">
<link rel="Stylesheet" type="text/css" href="http://www.ciorstore.com/jrx/css/bm.css">
    <script src="http://www.ciorstore.com/jrx/js/jquery.js"></script>
    <script>
        $(function () {
            
            imgAuto();//初始化图片大小
			
			$(".bm_tx_fx").click(function(){Fscreen();});
			$(".bm_tx_pyq").click(function(){Fscreen();});
			$(".bm_2_an").click(function(){$(".bm_2").attr("class","new");});
            window.onresize = function () {
                imgAuto();//屏幕缩放时图片的大小

                //if (wid > 1400) {
                //    $("#aaa").css("width", 1400);
                //} else if (wid < 200) {
                //    $("#aaa").css("width", 1400);
                //} else {
                //    $("#aaa").css("width", wid);
                //}
            }
        });
		
			function Fscreen(){
			$(".new").attr("class","bm_2");
		}

        function imgAuto() {
            var wid = document.documentElement.clientWidth * .8;
            $(".imgAuto").each(function () {
                $(this).css("width", wid);
            });
        }//遍历循环所有class="imgAuto"的标签并改变其大小
		function imgAuto() {
            var wid = document.documentElement.clientWidth * .45;
            $(".imgAuto3").each(function () {
                $(this).css("width", wid);
            });
        }
    </script>
<title>${dssrsubject.subjType==10?"科一":dssrsubject.subjType==20?"科二":dssrsubject.subjType==30?"科三":"科四"}-${dssrstudent.dtHrStaff.staffName }</title>
<style id="style-1-cropbar-clipper">/* Copyright 2014 Evernote Corporation. All rights reserved. */
.en-markup-crop-options {
    top: 18px !important;
    left: 50% !important;
    margin-left: -100px !important;
    width: 200px !important;
    border: 2px rgba(255,255,255,.38) solid !important;
    border-radius: 4px !important;
}

.en-markup-crop-options div div:first-of-type {
    margin-left: 0px !important;
}
</style></head>
<body bgcolor="#E7E7E7">
<div class="bm_tx_bt">预约服务</div>
<div class="geduan"></div>
<div class="peixun_kuang">
	<div class="peixun_heng">
		<div class="peixun_dandu_l" onclick="javascript:window.location.href='<%=basePath%>//page/ea/main/ddsr/list.jsp'" ><img src="<%=basePath%>/images/ea/ddsr/beganreservation.png" class="imgAuto3" style="width: 640.35px;"/></div>
		<div class="peixun_dandu_l" onclick="javascript:window.location.href='<%=basePath%>/ea/appointmentbymicroletterrecord/ea_getListRecordContentPersonal.jspa?'" ><img src="<%=basePath%>/images/ea/ddsr/myreservation.png" class="imgAuto3" style="width: 640.35px;"/></div>
	</div>
	<div style="clear:both;"></div>
</div>
<div class="geduan"></div>


</body></html>
