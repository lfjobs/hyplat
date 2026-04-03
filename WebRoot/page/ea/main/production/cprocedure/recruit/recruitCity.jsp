<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>
<head>

<title>工作地点</title>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">

<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/bootstrap.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/ea/production/lanren.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/ea/production/zhaopin.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/ea/production/recruitCity.css" />
<script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="https://webapi.amap.com/maps?v=1.4.15&key=358dde761a483ba6780ee510803f6108"></script><!--高德地图API-->
	<!-- UI组件库 1.0 -->
	<script type="text/javascript" src="//api.map.baidu.com/api?v=2.0&ak=AGLjXtGoLy3G7BBKEnMgDoHpt9G0wcGS"></script>
<script type="text/javascript">
var basePath="<%=basePath%>";
var type = "${param.type}";
var selectcitys = "${param.selectcitys}";
var selectindus = "${param.selectindus}";
var selectpos = "${param.selectpos}";
var jitype="${param.jitype}";
var sccId="${param.sccId}";

var staffid="${param.staffid}"; //共用**
var staffIDs="${param.staffIDs}";//共用**
var staffids="${param.staffids}";//共用**
var work = "${param.work}";//工作性质
var position="${param.position}";
var industrys ="${param.industry}";//行业类别**共用
var salary = "${param.salary}";//期望薪资
var status = "${param.status}";//求职状态
var resumeID = "${param.resumeID}";//求职状态
	var back = "${param.back}";
</script>
</head>
<body data-spy="scroll">

			<header class="fixed">
	<div class="header">
		<ul>
			<li class="arrar" style="width: 10%;">
				<div>
					<img src="<%=basePath%>images/ea/recruit/top_reture.png" alt="" />
				</div>
			</li>
			
			<li class="header_c" style="width:78%;">
				<span class="header_c_tet"><img src="<%=basePath%>images/ea/recruit/search999.png"/></span>
				<div class="search_k">
				<input type="search" name="" id="search" placeholder="搜索城市" value="" />
				</div>
			</li>
			<li class="asd" style="text-align: center;right: 0;position: absolute;">
				<div class="header_c_text">
					<a href="#" class="wancheng"> 完成</a>
					<a href="#" class="quxiao"> 取消</a>
				</div>
			</li>
		</ul>
	</div>
<div class="search_down">

</div>
</header>

<nav class="navbar-example navbar-default">
		<div class="initials" >
			<ul class="nav navbar-nav suoyin">
				<li><img src="<%=basePath%>images/ea/bids/068.png"></li>
			</ul>
		</div>
</nav>
<div class="main1">

</div>
<div class="main res_bot" data-spy="scroll">
	
		<div class="hangye_main">
			<div class="yixuan">
				<div class="kexuan_main">
					<div class="xuan_head">
						已选城市 <span>（<span id="shuzhi" style="color: #ff6600;">0</span>/3）</span>
						<span class="xia"></span>
					</div>
					<div class="xuan_hangye" id="xuanze" style="margin-top:10px;">

						<div class="condition">
							<span>尚未选择，请选择条件</span>

						</div>
					</div>
				</div>
			</div>
		</div>
		

			<div class="ding_city">
				<div class="ding_city_text">定位城市</div>
				<div class="ding_main">
					<div class="ding_main_lis">
						<img
							src="<%=basePath%>images/WFJClient/Newjspim/wfj11_address_01.png"
							width="12px" height="17px">&nbsp;<span id="citys"></span>
					</div>
				</div>
			</div>

			<div class="hot_city">
				<div class="hot_city_text">热门城市</div>
				<div class="hot_city_main">

					<div class="hot_city_lis"><div class="num_name" id="a30">北京市</div></div>
					<div class="hot_city_lis"><div class="num_name" id="a331">重庆市</div></div>
					<div class="hot_city_lis"><div class="num_name" id="a49">成都市</div></div>
					<div class="hot_city_lis"><div class="num_name" id="a112">东莞市</div></div>
					<div class="hot_city_lis"><div class="num_name" id="a115">佛山市</div></div>
					<div class="hot_city_lis"><div class="num_name" id="a116">广州市</div></div>
					<div class="hot_city_lis"><div class="num_name" id="a221">杭州市</div></div>
					<div class="hot_city_lis"><div class="num_name" id="a124">深圳市</div></div>
					<div class="hot_city_lis"><div class="num_name" id="a1">上海市</div></div>
				</div>
			</div>
		
		<div id="letter"></div>
		<div class="sort_box kexuan" data-target="#navbar-example" data-spy="scroll"
			data-offset="0">
			
			<div class="xuan_lis">
				<div class="num_name" id="a1">上海市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a2">临沧市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a3">丽江市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a4">保山市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a5">大理白族自治州</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a6">德宏傣族景颇族自治州</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a7">怒江傈僳族自治州</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a8">文山壮族苗族自治州</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a9">昆明市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a10">昭通市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a11">普洱市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a12">曲靖市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a13">楚雄彝族自治州</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a14">玉溪市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a15">红河哈尼族彝族自治州</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a16">西双版纳傣族自治州</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a17">迪庆藏族自治州</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a18">乌兰察布市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a19">乌海市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a20">兴安盟</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a21">包头市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a22">呼伦贝尔市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a23">呼和浩特市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a24">巴彦淖尔市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a25">赤峰市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a26">通辽市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a27">鄂尔多斯市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a28">锡林郭勒盟</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a29">阿拉善盟</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a30">北京市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a31">吉林市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a32">四平市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a33">延边朝鲜族自治州</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a34">松原市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a35">白城市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a36">白山市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a37">辽源市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a38">通化市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a39">长春市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a40">乐山市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a41">内江市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a42">凉山彝族自治州</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a43">南充市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a44">宜宾市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a45">巴中市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a46">广元市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a47">广安市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a48">德阳市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a49">成都市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a50">攀枝花市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a51">泸州市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a52">甘孜藏族自治州</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a53">眉山市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a54">绵阳市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a55">自贡市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a56">资阳市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a57">达州市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a58">遂宁市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a59">阿坝藏族羌族自治州</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a60">雅安市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a61">天津市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a62">中卫市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a63">吴忠市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a64">固原市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a65">石嘴山市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a66">银川市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a67">亳州市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a68">六安市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a69">合肥市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a70">安庆市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a71">宣城市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a72">宿州市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a73">巢湖市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a74">池州市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a75">淮北市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a76">淮南市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a77">滁州市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a78">芜湖市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a79">蚌埠市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a80">铜陵市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a81">阜阳市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a82">马鞍山市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a83">黄山市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a84">东营市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a85">临沂市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a86">威海市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a87">德州市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a88">日照市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a89">枣庄市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a90">泰安市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a91">济南市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a92">济宁市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a93">淄博市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a94">滨州市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a95">潍坊市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a96">烟台市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a97">聊城市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a98">莱芜市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a99">菏泽市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a100">青岛市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a101">临汾市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a102">吕梁市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a103">大同市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a104">太原市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a105">忻州市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a106">晋中市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a107">晋城市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a108">朔州市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a109">运城市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a110">长治市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a111">阳泉市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a112">东莞市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a113">中山市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a114">云浮市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a115">佛山市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a116">广州市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a117">惠州市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a118">揭阳市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a119">梅州市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a120">汕头市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a121">汕尾市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a122">江门市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a123">河源市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a124">深圳市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a125">清远市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a126">湛江市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a127">潮州市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a128">珠海市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a129">肇庆市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a130">茂名市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a131">阳江市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a132">韶关市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a133">北海市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a134">南宁市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a135">崇左市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a136">来宾市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a137">柳州市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a138">桂林市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a139">梧州市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a140">河池市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a141">玉林市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a142">百色市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a143">贵港市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a144">贺州市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a145">钦州市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a146">防城港市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a147">乌鲁木齐市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a148">伊犁哈萨克自治州</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a149">克孜勒苏柯尔克孜自治州</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a150">克拉玛依市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a151">博尔塔拉蒙古自治州</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a152">吐鲁番地区</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a153">和田地区</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a154">哈密地区</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a155">喀什地区</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a156">图木舒克市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a157">塔城地区</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a158">巴音郭楞蒙古自治州</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a159">昌吉回族自治州</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a160">石河子市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a161">阿克苏地区</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a162">阿勒泰地区</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a163">阿拉尔市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a164">南京市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a165">南通市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a166">宿迁市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a167">常州市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a168">徐州市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a169">扬州市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a170">无锡市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a171">泰州市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a172">淮安市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a173">盐城市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a174">苏州市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a175">连云港市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a176">镇江市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a177">上饶市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a178">九江市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a179">南昌市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a180">吉安市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a181">宜春市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a182">抚州市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a183">新余市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a184">景德镇市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a185">萍乡市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a186">赣州市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a187">鹰潭市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a188">保定市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a189">唐山市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a190">廊坊市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a191">张家口市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a192">承德市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a193">沧州市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a194">石家庄市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a195">秦皇岛市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a196">衡水市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a197">邢台市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a198">邯郸市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a199">三门峡市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a200">信阳市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a201">南阳市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a202">周口市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a203">商丘市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a204">安阳市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a205">平顶山市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a206">开封市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a207">新乡市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a208">洛阳市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a209">济源市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a210">漯河市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a211">濮阳市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a212">焦作市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a213">许昌市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a214">郑州市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a215">驻马店市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a216">鹤壁市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a217">丽水市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a218">台州市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a219">嘉兴市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a220">宁波市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a221">杭州市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a222">温州市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a223">湖州市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a224">绍兴市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a225">舟山市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a226">衢州市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a227">金华市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a228">万宁市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a229">三亚市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a230">东方市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a231">临高县</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a232">乐东黎族自治县</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a233">五指山市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a234">保亭黎族苗族自治县</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a235">儋州市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a236">定安县</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a237">屯昌县</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a238">文昌市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a239">昌江黎族自治县</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a240">海口市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a241">澄迈县</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a242">琼中黎族苗族自治县</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a243">琼海市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a244">白沙黎族自治县</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a245">西沙群岛</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a246">陵水黎族自治县</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a247">仙桃市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a248">十堰市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a249">咸宁市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a250">天门市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a251">孝感市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a252">宜昌市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a253">恩施土家族苗族自治州</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a254">武汉市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a255">潜江市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a256">神农架林区</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a257">荆州市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a258">荆门市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a259">襄樊市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a260">鄂州市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a261">随州市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a262">黄冈市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a263">黄石市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a264">娄底市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a265">岳阳市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a266">常德市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a267">张家界市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a268">怀化市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a269">株洲市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a270">永州市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a271">湘潭市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a272">湘西土家族苗族自治州</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a273">益阳市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a274">衡阳市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a275">邵阳市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a276">郴州市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a277">长沙市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a278">临夏回族自治州</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a279">兰州市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a280">嘉峪关市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a281">天水市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a282">定西市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a283">平凉市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a284">庆阳市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a285">张掖市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a286">武威市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a287">甘南藏族自治州</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a288">白银市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a289">酒泉市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a290">金昌市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a291">陇南市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a292">三明市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a293">南平市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a294">厦门市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a295">宁德市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a296">泉州市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a297">漳州市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a298">福州市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a299">莆田市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a300">龙岩市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a301">山南地区</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a302">拉萨市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a303">日喀则地区</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a304">昌都地区</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a305">林芝地区</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a306">那曲地区</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a307">阿里地区</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a308">六盘水市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a309">安顺市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a310">毕节地区</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a311">贵阳市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a312">遵义市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a313">铜仁地区</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a314">黔东南苗族侗族自治州</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a315">黔南布依族苗族自治州</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a316">黔西南布依族苗族自治州</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a317">丹东市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a318">大连市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a319">抚顺市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a320">朝阳市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a321">本溪市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a322">沈阳市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a323">盘锦市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a324">营口市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a325">葫芦岛市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a326">辽阳市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a327">铁岭市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a328">锦州市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a329">阜新市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a330">鞍山市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a331">重庆市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a332">咸阳市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a333">商洛市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a334">安康市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a335">宝鸡市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a336">延安市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a337">榆林市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a338">汉中市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a339">渭南市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a340">西安市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a341">铜川市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a342">果洛藏族自治州</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a343">海东地区</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a344">海北藏族自治州</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a345">海南藏族自治州</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a346">海西蒙古族藏族自治州</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a347">玉树藏族自治州</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a348">西宁市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a349">黄南藏族自治州</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a350">七台河市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a351">伊春市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a352">佳木斯市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a353">双鸭山市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a354">哈尔滨市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a355">大兴安岭地区</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a356">大庆市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a357">牡丹江市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a358">绥化市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a359">鸡西市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a360">鹤岗市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a361">黑河市</div>
			</div>
			<div class="xuan_lis">
				<div class="num_name" id="a362">齐齐哈尔市</div>
			</div>

		</div>
	</div>
	
	 <div class="alert_div" id="alert_d">
        <div class="alert">
            最多可选3个
        </div>
    </div>
    <div class="alert_div2">
        <div class="alert_txt">
            <p>信息尚未保存，确定要离开吗？</p>
            <div>
                <button id="queding" style="color:#FF6600;" class="queding">确定</button>
                <button id="quxiao">取消</button>
                <div class="clearfix"></div>
            </div>
        </div>
    </div>

	<script
		src="<%=basePath%>js/ea/production/cprocedure/jquery.charfirst.pinyin.js"></script>
	<script src="<%=basePath%>js/bootstrap.js"></script>
	 <script src="<%=basePath%>js/ea/production/cprocedure/recruit/city.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/ea/production/cprocedure/recruit/recruitCity.js"></script>
</body>
<script type="text/javascript">

	$(function() {
	
	  //初始化已选城市(防止頁面未加載完畢，寫在最後)；
		if (selectcitys != "") {
			var arrays = selectcitys.split(",");

			$.each(arrays, function(n, value) {
				$(".kexuan .num_name").each(function() {
					if ($(this).text().indexOf(value) != -1) {
						$(this).parent().click();
						return false;
					}
				});
			});
		}
	});
</script>
</html>