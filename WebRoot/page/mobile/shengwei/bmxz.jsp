<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>报名须知</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" /> 
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel=Stylesheet type=text/css href="<%=basePath%>/css/shengwei/bm.css">
	<link rel=Stylesheet type=text/css href="<%=basePath%>/css/shengwei/css.css">
    <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
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
    </script>
        <script>
		var imgUrl = 'http://www.impf2010.com/images/wechat.jpg';
		var lineLink = document.location.href;
		var descContent = document.title;
		var shareTitle = document.title;
		var appid = 'wxc9937e3a66af6dc8';
		function shareFriend() {
		    WeixinJSBridge.invoke('sendAppMessage',{
		                            "appid": appid,
		                            "img_url": imgUrl,
		                            "img_width": "640",
		                            "img_height": "640",
		                            "link": lineLink,
		                            "desc": '点击发布产品...',
		                            "title": shareTitle
		                            }, function(res) {
		                            _report('send_msg', res.err_msg);
		                            })
		}
		function shareTimeline() {
		    WeixinJSBridge.invoke('shareTimeline',{
		                            "img_url": imgUrl,
		                            "img_width": "640",
		                            "img_height": "640",
		                            "link": lineLink,
		                            "desc": descContent,
		                            "title": shareTitle
		                            }, function(res) {
		                            _report('timeline', res.err_msg);
		                            });
		}
		function shareWeibo() {
		    WeixinJSBridge.invoke('shareWeibo',{
		                            "content": descContent,
		                            "url": lineLink,
		                            }, function(res) {
		                            _report('weibo', res.err_msg);
		                            });
		}
		// 当微信内置浏览器完成内部初始化后会触发WeixinJSBridgeReady事件。
		document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {

	        // 发送给好友
	        WeixinJSBridge.on('menu:share:appmessage', function(argv){
	            shareFriend();
	            });
	
	        // 分享到朋友圈
	        WeixinJSBridge.on('menu:share:timeline', function(argv){
	            shareTimeline();
	            });
	
	        // 分享到微博
	        WeixinJSBridge.on('menu:share:weibo', function(argv){
	            shareWeibo();
	            });
        }, false);
	</script>
<title>四川胜威驾校</title>
</head>
<body bgcolor="#E7E7E7">
		<div class="new">
			<div class="bm_2_con">
				<div class="bm_2_div">
					<img class="imgAuto2" src="<%=basePath %>/images/shengwei/yd.png" />
				</div>
				<div class="bm_2_div">
					<input name="" type="button" class="bm_2_an" value="关闭提示 " />
				</div>
			</div>
		</div>
		<div class="bm_tx_bt">
			报名须知
		</div>
		<div class="geduan"></div>
		<div class="bm_kuang">
			<div class="bm_wz">
				<p>
					一、满18周岁至70周岁、身高150cm(小货155cm)以上，视力不低于0.7(可矫正)辩色力正常，身体运动能力正常。
				</p>
				<p>
					二、缴费报名，完善相关培训资料
					<p>
						① 白底一寸彩照六张
					</p>
					<p>
						② 机动车驾驶证申请表签字
					</p>
					<p>
						③
						身份证明复印件：外地户口提供身份证和暂住证原件及复印件；本地户口需提供二代身份证复印件，如没有请交旧身份证或临时身份证原件及复曾甲印件；学员一并提供驾驶复印件，驾驶证区划具体到"省市县区"。
					</p>
					<p>
						④ 体检证明：请在车管备案的县市级以上进行体检，体检后请认真核对自己的基本信息和体检信息。
					</p>
					<p>
						⑤ 提示单：车管要求，学员需仔细阅读并签名。⑥ 合同：学员报名后认真阅读，按照合同约定的权利义务，签字生效。
					</p>
					<p>
						三、 资料移交到综合办报开学处，在车管网录入基本信息之后，可制卡。
					</p>
					<p>
						3、 制卡处查询录入信息，制卡。
					</p>
					<p>
						学员信息录入，本人凭报名票据，领取学时卡，扫描录入并制卡。
					</p>
					<p>
						① 科一理论26小时，模拟4小时，合计30小时
					</p>
					<p>
						② 科二理论1小时，实操27小时，合计28小时
					</p>
					<p>
						③ 科三理论及后续，合计28小时
					</p>
					<p>
						备注：每科目考试以完成学时完成情况而定，进行约考。科一学时每天不能潮六个学时，科二，科三不能超过四学时。
					</p>
					<p>
						四、原有E照、F照的加考学员须具备初领驾驶证一年以上的条件，另须携带原驾驶证复印件、原驾驶证副表(到户籍所在区、县交警支队宣传科抽取底卡)。
					</p>
			</div>
		</div>
		<div class="geduan"></div>
		<div class="bm_tx_kuang">
			<div class="bm_tx_anbg">
				<div class="bm_tx_fx">
					<input name="" type="button" class="bm_tx_fxan" value="分享到群/朋友" />
				</div>
				<div class="bm_tx_pyq">
					<input name="" type="button" class="bm_tx_fxan" value="分享到朋友圈" />
				</div>
			</div>
		</div>
		<div class="geduan"></div>
	</body>
</html>
