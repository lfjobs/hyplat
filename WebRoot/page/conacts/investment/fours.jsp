<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/contacts/investment/bootstrap.css"
	type="text/css">
	<link href="<%=basePath%>css/contacts/investment/style11.css" rel="stylesheet"
		type="text/css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/contacts/investment/fadongji.css"
	type="text/css">


<link href="<%=basePath%>css/contacts/Restaurant/jqModal_blue.css"
	rel="stylesheet" type="text/css" />
	 <script type="text/javascript" src="<%=basePath%>/js/jquery.js"></script>
<script type="text/javascript" src="<%=basePath%>js/fontscroll.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>

	

<title>天太世统科技有限公司</title>

</head>

<body style="background: #f8f8f8;">
<div class="top">
    <ul>
        <li class="arrow"><img src="<%=basePath%>images/contacts/comments/wfj_return_02.png"/></li>
        <li>${param.xitong}</li>
        <li></li>
    </ul>
</div>
<!--11  -->
<div class="main_hide">
		
		<div class="main col-xs-12">
			<div class="banner">
				<img src="${param.image}"/>
			<div class="col-xs-12">
				<div class="zhanshi">优势展示</div>
			</div>
			<div class="zhan_lis">
				<div class="col-xs-1 pull-left zhan_lis_img">
					<img src="<%=basePath%>images/contacts/attractment/uldian.png" />
				</div>
				<div class="col-xs-11 pull-left zhan_lis_tet">
					加入创业商城业主会员，我们将为您 组织管理人事系统、办公系统、财务 系统、营销系统等</div>
			</div>
			<div class="zhan_lis">
				<div class="col-xs-1 pull-left zhan_lis_img">
					<img src="<%=basePath%>images/contacts/attractment/uldian.png" />
				</div>
				<div class="col-xs-11 pull-left zhan_lis_tet">
					给您自动生成印钞系统,APP,网络商城，移动办公</div>
			</div>
			<div class="zhan_lis">
				<div class="col-xs-1 pull-left zhan_lis_img">
					<img src="<%=basePath%>images/contacts/attractment/uldian.png" />
				</div>
				<div class="col-xs-11 pull-left zhan_lis_tet">
					采用最先进的5L5C管理培训咨询+资源型赢利模式系统+5L5C管理实施方案</div>
			</div>
			<div class="zhan_lis">
				<div class="col-xs-1 pull-left zhan_lis_img">
					<img src="<%=basePath%>images/contacts/attractment/uldian.png" />
				</div>
				<div class="col-xs-11 pull-left zhan_lis_tet">享受产品分销权直接佣金70%</div>
			</div>
			<div class="zhan_lis">
				<div class="col-xs-1 pull-left zhan_lis_img">
					<img src="<%=basePath%>images/contacts/attractment/uldian.png" />
				</div>
				<div class="col-xs-11 pull-left zhan_lis_tet">
					产品代理享受直接或间接佣金收益(诺发展100个消费者，一天消费1元，一年消费365*100=365000)</div>
			</div>
			<div class="zhan_lis">
				<div class="col-xs-1 pull-left zhan_lis_img">
					<img src="<%=basePath%>images/contacts/attractment/uldian.png"/>
				</div>
				<div class="col-xs-11 pull-left zhan_lis_tet">分享会员卡权</div>
			</div>
			<div class="zhan_lis">
				<div class="col-xs-1 pull-left zhan_lis_img">
					<img src="<%=basePath%>images/contacts/attractment/uldian.png" />
				</div>
				<div class="col-xs-11 pull-left zhan_lis_tet">分享免费打电话系统</div>
			</div>
		</div>
		<!-- 123 -->
		<form  id="formsutm" name="formsutm" method="post">
		<input type="submit" name="submit" style="display:none;">
		<input type="hidden" id="ppid" name="ppid" value="${param.ppid}">
		<input type="hidden" id="staffAddress" name="staffAddress.addressID">
		<!-- <input type="hidden" id="ricep" name="ricep" value="">
		<input type="hidden" id="xitong" name="xitong" value="">
		<input type="hidden" id="companyId" name="companyId" value="">  -->
		<div class="join">轻松快速一步加入</div>
		<!--暂时隐藏后期会用到的  -->
		<ul class="text_kuang" >
			 <li><span>姓名</span> <input type="text" name="consignee" id="consignee" />
			</li> 
			<li><span>电话</span> <input type="text" name="phone"  id="phone"/>
			</li>
			 <li><span>所在区</span> <input type="text" name="area" id="area" />
			</li>
			<li><span>邮政编码</span> <input type="text" name="postCode" id="postCode"/>
			</li>
			<li><span>详细地址</span> <input type="text" class="gongsi" id="addressDetailed" name="addressDetailed"/>
			</li>
		</ul>
		<div id="commit" style="width:50%;margin:2rem auto;text-align:center;">
			<img src="<%=basePath%>images/contacts/attractment/jb.png"/>
			<span style="color:#f85d1e;">支付方式</span>
		</div>
		
	
		 <div class="wfj11_015_buy_commit" style="display:block;">
        	<div class="wfj11_015_need" style="padding:0 15px;">
            	<div class="wfj11_015_width">
                	<ul>
                    	<li class="left">需支付：</li>
                    	<li class="right"><span>￥${param.ricep}</span><input type="hidden" id="morre" name="morre" value="${param.ricep}"/></li>
                    </ul>
                </div>
            </div>
           <div class="wfj11_015_allbay">
				<div class="wfj11_015_width">
					<table>
						<tr>
							<td colspan="2">选择支付方式</td>
						</tr>
						<tr class="wfj11_015_choice">
							<td align="left"><img class="all_pay"
								src="<%=basePath%>/images/WFJClient/Newjspim/all_pay_01.png" />
							</td>
							<td  class="second" align="right"><img
								src="<%=basePath%>/images/WFJClient/Newjspim/choice_01.png"
								width="24" height="24" name="1" />
							</td>
						</tr>
						<tr class="wfj11_015_choice">
							<td align="left"><img class="all_pay"
								src="<%=basePath%>/images/WFJClient/Newjspim/all_pay_03.png" />
							</td>
							<td class="second" align="right"><img
								src="<%=basePath%>/images/WFJClient/Newjspim/choice_02.png"
								width="24" height="24" name="3" />
							</td>
						</tr>
						<tr class="wfj11_015_choice">
							<td align="left"><img class="all_pay"
								src="<%=basePath%>/images/WFJClient/Newjspim/xxzz.png" />
							</td>
							<td class="second" align="right"><img
								src="<%=basePath%>/images/WFJClient/Newjspim/choice_02.png"
								width="24" height="24" name="4" />
							</td>
						</tr>
						<tr>
							<td colspan="2" align="center"><div id="paycommit"
									onclick="zf()">确认支付</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
        </div>
        </form>
		</div>
		<!--12  -->
	

   <div id="occlusion2" class="jqmWindow jqmWindowcss1"></div>
   <iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe> 

<script>
var basePath="<%=basePath%>";
var zffs = 1;//选择的支付方式   默认为支付宝
var ddid;
var token=0;
</script>
<script type="text/javascript" src="<%=basePath%>js/restaurant/investment/four.js"></script>
	

</body>
</html>