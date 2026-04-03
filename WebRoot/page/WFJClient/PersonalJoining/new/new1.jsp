<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>详细</title>

<script charset="utf-8" type="text/javascript"
	src="<%=basePath %>page/WFJClient/PersonalJoining/new/lxb.js"></script>
<script charset="utf-8" type="text/javascript"
	src="<%=basePath %>page/WFJClient/PersonalJoining/new/v.js"></script>

<script type="text/javascript"
	src="<%=basePath %>page/WFJClient/PersonalJoining/new/mobilego.js"></script>
<link rel="stylesheet"
	href="<%=basePath%>page/WFJClient/PersonalJoining/new/common.css">
<link rel="stylesheet"
	href="<%=basePath%>page/WFJClient/PersonalJoining/new/aboutus.css">
	<link rel="stylesheet"
	href="<%=basePath%>page/WFJClient/PersonalJoining/new/style.css">
<style type="text/css">

.p-aboutus .news-list {
	margin: 50px 0
}

.news-list a {
	font-size: 18px;
	color: #000;
	display: block;
	height: 45px;
	line-height: 45px;
	width: 100%
}

.news-list li:hover {
	background-color: #ccc
}

.news-list a .fRight {
	float: right
}

.inputstyle {
	display: inline-block;
	background-color: #e8e8e8;
	border: 0 none;
	border-radius: 60px;
	font-size: 14px;
	height: 21px;
	line-height: 21px;
	padding: 10px 20px 11px;
	width: 300px;
	color: #000;
	outline: 0
}

#login ul #g_yz .inputstyle {
	width: 155px !important;
	margin-right: 10px
}

#login ul #g_yz #codeImage {
	width: 68px;
	height: 24px
}

#login ul #g_yz a {
	color: #999;
	margin: 14px 5px 0 0
}

#login ul #g_yz a:hover {
	color: #03a9f4
}

h1,h2,h3,h4 {
	padding: 0;
	margin: 0
}

#login {
	width: 340px;
	padding: 0 40px;
	background-color: #fff;
	border-radius: 5px;
	border: 1px solid #dfdfdf;
	position: absolute;
	right: 85px;
	top: 106px
}

#login h2 {
	padding: 30px 0 15px;
	font-weight: 400;
	font-size: 21px;
	color: #666;
	text-align: center;
	letter-spacing: 3px
}

#login ul {
	padding: 0;
	margin: 0
}

.pass-holder {
	position: absolute;
	left: 20px;
	top: 25px;
	color: #999;
	cursor: text
}

#login ul li {
	list-style-type: none;
	padding: 15px 0;
	overflow: hidden;
	vertical-align: middle;
	position: relative
}

#login ul #field_login_options {
	padding-top: 2px;
	margin-top: -10px;
	font-size: 12px;
	color: #999
}

#login ul #field_login_options input {
	margin-top: 1px
}

#login ul #field_login_options a {
	right: 5px !important;
	color: #999
}

#login ul #field_login_options a:hover {
	color: #03a9f4
}

#login ul li span.f-label {
	display: none;
	width: 80px;
	float: left;
	text-align: right;
	padding-right: 4px;
	padding-top: 5px;
	vertical-align: middle
}

#login ul li img,#login ul li input,#login ul li label {
	vertical-align: middle
}

#login ul li div {
	text-align: right;
	padding-right: 5px;
	padding-top: 5px
}

#login ul li a {
	float: right;
	text-align: right;
	padding: 0 0;
	color: #1e5494
}

#login .title-box {
	padding: 16px 0 30px
}

#login .title-box .merchant-name,#login .title-box .separetor {
	float: left
}

#login .title-box .user-action-l {
	float: right;
	color: #999
}

#login .title-box .user-action-l:hover {
	color: #03a9f4
}

#login .title-box .merchant-name {
	width: 231px;
	text-align: right;
	white-space: nowrap;
	overflow: hidden;
	text-overflow: ellipsis;
	height: 19px;
	display: inline-block
}

#login .title-box .separetor {
	line-height: 16px
}

.login_button {
	height: 40px;
	line-height: 40px;
	width: 340px;
	border-radius: 50px;
	text-align: center;
	float: none;
	border: 0 none;
	background-color: #f5a918;
	color: #fff;
	letter-spacing: 8px;
	cursor: pointer;
	font-size: 16px;
	outline: 0
}

.login_button:hover {
	background-color: #fdb326
}

.login_button:active {
	box-shadow: inset 0 3px 5px rgba(0, 0, 0, .125)
}

#error-tip {
	position: absolute;
	background: #ffffe1;
	border: 1px solid #F4E6B1;
	padding: 5px 20px 5px 10px;
	color: #f56a4f;
	width: 200px;
	z-index: 9999
}

#error-tip .error-content {
	line-height: 25px;
	padding-left: 22px
}

#error-tip .icon-tip {
	position: absolute;
	top: 10px;
	left: 10px;
	width: 18px;
	height: 18px;
	background: url(images/error.png) no-repeat;
	filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(src='Content/images/error.png',
		sizingMethod='scale' )
}

#error-tip .triangle-wrapper {
	position: absolute;
	bottom: -12px;
	left: 30px;
	width: 0;
	height: 0;
	border: 6px solid;
	border-color: #F4E6B1 transparent transparent transparent;
	background: 0 0
}

#error-tip .triangle-inner {
	position: absolute;
	top: -8px;
	left: -6px;
	width: 0;
	height: 0;
	border: 6px solid;
	border-color: #ffffe1 transparent transparent transparent;
	background: 0 0
}

#error-tip .close-btn {
	position: absolute;
	top: 3px;
	right: 5px;
	font-style: normal;
	font-size: 10px;
	cursor: pointer
}

#codeImage {
	cursor: pointer
}

#yzlink {
	cursor: pointer;
	position: absolute;
	right: 0;
	top: 12px
}

.input-botfont {
	display: none;
	color: #6076a0;
	padding-left: 85px;
	line-height: 16px
}

.input-advise {
	display: none;
	color: #7c7c7c;
	padding-left: 85px;
	line-height: 20px;
	padding-bottom: 3px
}

.f-label-b {
	display: block;
	padding-left: 5px
}

.s-mask {
	position: fixed;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	z-index: 1999;
	background: #fff;
	filter: alpha(opacity =   50);
	-moz-opacity: .5;
	opacity: .5
}

.s-alert {
	display: block;
	position: fixed;
	background: #fff;
	z-index: 2000;
	width: 466px;
	left: 50%;
	top: 36%;
	margin-left: -233px;
	padding: 0;
	border: 0;
	-moz-box-shadow: 0 0 30px #999;
	-webkit-box-shadow: 0 0 30px #999;
	box-shadow: 0 0 30px #999
}

.s-alert-content {
	padding: 45px 70px 20px 70px;
	font-size: 14px;
	line-height: 22px;
	text-align: center;
	word-wrap: break-word
}

.s-alert-content-msg {
	padding-bottom: 30px;
	color: #444
}

.s-alert-button {
	text-align: center
}

.s-alert-button span {
	display: inline-block;
	color: #bababa;
	border: 1px solid #d9d9d9;
	padding: 0 24px;
	line-height: 28px;
	cursor: pointer;
	background: 0 0;
	background-color: #fff;
	-moz-border-radius: 4px;
	-webkit-border-radius: 4px;
	border-radius: 4px;
	box-shadow: none;
	height: 28px;
	float: none
}
</style>
</head>
<body>

<div class="g-w930 p-aboutus">
	
	<div class="news-list">
		<div class="p-news g-w930">
			<div class="clearfix detail-box g-hide" style="display: block;">


				<div class="news-content">
				<c:if test="${param.types=='平台' }">
					<h3>微分金平台？</h3>
					<span class="website"></span>
					<div class="detail">
					
						<p>在这新的时代，变革的时代，竞争的时代，在企业职业 经营头疼的时刻，</p>
						<p>新的赢利模式体系逐步让人们有所认识， 那就是人们天天找资源，找赢利模式，</p>
						<p>找资源整合的方式。 找互联网平台，都苦于“头疼医头、脚疼医脚”，</p>
						<p>不能根本 清晰解决企业困惑。正当企业职业人渴望寻找资源整合、</p>
						<h4>持 续赢利模式系统的时候，新的资源整合赢利模式系统应运而 生,</h4>
						<p>简称中联园区微分金赢利平台即5L5C微分金赢利系统平 台。</p>
						<p>它包含企业渴望的管理思想，科学化、标准化、大数据、 管理系统、电子商务平台、培训咨询、赢利模式系统、科学 的实施方案。</p>
						<p>基于5L5C管理思想（五层五清孵化管理体系企 业管理新标准）完成企业四化建设、四化融合的运营管理体 系，</p>
						<p>即5L5C大数据（中联园区）+5L5C管理系统（人事系统、 办公系统、财务系统、生产系统、营销系统等）+网络推广即</p>
						<p>电子商务平台（微分金+微信平台+电子商务资源抱团+线上线 下博览）+5L5C管理培训咨询+资源型赢利模式系统+5L5C管理 实施方案。</p>
						<p> 以此开启企业原始资本+互联网时代的新纪元。</p>
					</div>
						</c:if>
						<c:if test="${param.types=='之歌' }">
						<h3>微分金之歌？</h5>
						<h4>旋律动听</h4>
						<p>好机遇好机遇好机遇，快抓住互联网好机遇，
						入驻5L5C中联园区微分金，让企业之树万年长青。
						好机遇好机遇好机遇，五层五清管理科技设计，
						标准化科学化精准定位，让职业人快速发展财源涌进。
						吔！</p>
						
						<p>微分金，微分金，大家来分享千万金，
						线上线下进入亿家心，共享资源共享金。
						好机遇好机遇好机遇，快抓住互联网好机遇，
						入驻5L5C中联园区微分金，国富民强齐欢心。
						好机遇好机遇好机遇，中联园区好似肥沃大地。
						用心服务省市县乡社，成就中国梦我的梦。
						好机遇，好机遇，抓住互联网好机遇，
						入驻5L5C中联园区微分金，成就中国梦我的梦。</p>
						<h5>微分金口号</h5>
						<h4>振奋人心的几句口号，在您的人生道路上有很大的帮助！！！</h4>
						<p>人要有敬畏之心，才有未来有所谓，正确思想诀成败，动机端正成未来。</p>
						<p>	立刻行动拿成果，成绩自我耀祖宗，机会不会等着您，光阴一去不在回，</p>
						<p>	今天就做财源拥，明天再做财不来，后天再做穷一生，共享资源共享金。 </p>
						</div>
						</c:if>
						<c:if test="${param.types=='系统' }">
						<h3>互联网+”驾校未来发展趋势？</h5>
						
						<p>驾校经济联营平台是以5l5c大数据为核心，数字地球联营平台为基础，汽车交通联营平台为行业资源。
						同事随着经济的发展科技的进步，平台经济时代的到来而诞生与该行业相适应的跨界资源平台。并由北京天太世统科技有限公司作为技术开发，技术支持。</p>
						
						<p>中联园微分金股份有限公司发展行业股份有限公司做运营，五层五清管理咨询有限公司做咨询培训落地，形成具有严密的法定运营机构实施，从而确保客户利益。
						驾校经济联营平台也参照驾校行业历史和发展趋势为依据而诞生的产物。驾校行业十于年的“野蛮生长”已经成为社会关注的焦点，</p>
						
						<p>人们茶宇饭后议论的热点，求创收不求质量已经成为整个行业的顽疾，物极必反改革势在必行。门槛低收益高。营收快成为热线投资的热土，
						新兴驾校企业分流大部分生源成为行业“搅局者”。传统老牌驾校利益交织多，职工负载重单位成本远远高于新建驾校，除教学经验外毫无竞争优势可言。</p>
						<p>在转型升级关键时期调头非常困难，互联网汽车驾校联营平台这个时候就是改变状的“灵丹妙药”。改变在一定程度上会触动即得利益，不改也不见得就能保住所谓的即得利益。
						然而在目前驾校传统的经营模式存在的问题是比较多的，在供不应求的时代，
						驾校传统的经营模式是：学员一旦交费，驾校就可以计算出从这个学员身上赚多少钱。</p>
						<p>重是招生收钱，甚至不惜代价先把学员拉进来，轻服务，支出的越少越好，刷学时，吃拿卡要，层层等。才有了我们互联网驾校联营平台的真正的到来，
						驾校与学员互通，学员与学员互通，驾校难以欺骗学员了，学员变量，传统的商业环境变了，驾校的经营模式也必须因而改变，否则很难顺应当今的时代发展趋势。</p>


						<p>驾校联营平台告诉我们驾校的改革时代已经到来。驾校联营平台即将是未来不懈的新产物，因为大家一定明白，今天你赚的钱是今天的钱，明天你赚的是明天的钱，而能看到未来十年，二十年，甚至三十年我们赚的才是未来的经济，赢在起跑线上。
							驾校经济联营平台的诞生，必将形成粉丝客户经济，联营商城生产会员经济供应商城会员经济（招商招标），公司产品经济，代理数字地球联营平台商城商品经济，公司股权经济，汽车交通联营平经济，县域经济。在社会人士的共同努力推动下，做出最大贡献。
						驾校经济联营平台是以五层五清管理思想，管理标准，管理系统大数据平台，微分金电子商务平台，互联网，网络推广：网站，微信，qq,百度等，从而结合粉丝经济平台，客户经济平台，联营商城会员经济平台，公司经济平台，行业经济平台，县域经济平台形成的数字联营平台的业务架构和技术架构。</p>
						</c:if>
			</h3>
				</div>
</body>
</html>