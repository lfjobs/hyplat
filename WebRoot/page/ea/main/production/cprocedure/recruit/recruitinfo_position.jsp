<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
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
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0"/>

<title>职位类别</title>


<link rel="stylesheet" href="<%=basePath%>/css/bootstrap.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/ea/production/my_css.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/ea/production/zhaopin.css" />
<script src="<%=basePath%>js/jquery-1.11.3.js" type="text/javascript"></script>
<script src="<%=basePath%>/js/bootstrap.js"></script>
<script type="text/javascript" src="<%=basePath%>js/ea/production/cprocedure/recruit/recruitinfo_position.js"></script>

<script type="text/javascript">

var basePath="<%=basePath%>";	
var type = "${param.type}";
var selectpos = "${param.selectpos}";
var selectindus = "${param.selectindus}";
var selectcitys = "${param.selectcitys}";
var jitype="${param.jitype}";
var sccId="${param.sccId}";
var riId = "${recruitInfo.riId}";

//保存的情况
var staffid="${param.staffid}"; //共用**
var staffIDs="${param.staffIDs}";//共用**
var staffids="${param.staffids}";//共用**
var work = "${param.work}";//工作性质
var region ="${param.region}";//工作地区
var industrys ="${param.industry}";//行业类别**共用
var salary = "${param.salary}";//期望薪资
var status = "${param.staus}";//求职状态
//-----
//添加教育背景的时候
var name="${param.name}";
var graduationTime="${param.graduationTime}";
var admissionTime="${param.admissionTime}";
var professionalName="${param.professionalName}";
var education="${param.education}";
//==========
var position="${param.position}";
var postName="${param.postName}";
var duties="${param.duties}";

//修改的情况下
var jobWantedKeys="${param.jobWantedKeys}";
var resumeIDs="${param.resumeIDs}";
var jobWantedIds="${param.jobWantedIds}";
//-----
//修改教育背景的时候
//多中情况
var keys="${param.keys}";
var educationIDs="${param.educationIDs}";
var resumeIDs="${param.resumeIDs}";
var keya="${param.keya}";
var educationIDa="${param.educationIDa}";
var resumeIDa="${param.resumeIDa}";

var recordKeys ="${param.recordKeys}";
var recordIDs ="${param.recordIDs}";
var resumeIDs ="${param.resumeIDs}";
var recordKeya ="${param.recordKeya}";
var recordIDa ="${param.recordIDa}";
var resumeID = "${param.resumeID}";
var back = "${param.back}";

</script>


</head>
<body>
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
				<input type="search" name="" id="search" placeholder="搜索职位类别" value="" />
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
<div class="main res_bot">
	<div class="hangye_main">
		<div class="yixuan">
				<div class="xuan_head" id="headsx">已选职位
					<span>（<span id="shuzhi" style="color: #ff6600;">0</span>/3）</span>
					<span class="xia"></span>
				</div>
				<div class="xuan_hangye" id="xuanze">
					<div class="condition">
						<span>尚未选择，请选择条件</span>
						
					</div>
			  </div>
		</div>
		<div class="mainlis">
		<c:forEach items="${positionlist}" var="item">
			<div class="kexuan">
				<div class="kexuan_main">
					<span class="xuan_head">${item.codeValue} </span>
					<div class="xuan_zhiwei">
						<ul class="list-group">
						   <c:forEach items="${sublist}" var="itemsub">
						   <c:if test="${itemsub.codePID eq item.codeID}">
							<li class="${itemsub.codePID} list-group-item lisp" id="${itemsub.codeID}">
							<nobr>
							<span class="quan_xuan_d one">
							<img class="quan_xuan xuanz" src="<%=basePath%>images/ea/recruit/ico_zhi_06.png" alt="" />
							<img class="quan_xuan noxuanz dN" src="<%=basePath%>images/ea/recruit/chan_07.png" /> 
							</span> 
							<span class="big_zhiwei"> 
							  <span class="zhiwei_fenlei" style="vertical-align: middle;">${itemsub.codeValue}</span>
									<p class="pull-right">
										<img class="text-right shangi qiehuan"
											src="<%=basePath%>images/ea/recruit/ico_zhi_03.png" alt="" />
										<img class="text-right xiai qiehuan dN"
											src="<%=basePath%>images/ea/recruit/ico_zhi_10.png" alt="" />
									</p> 
							</span>
							</nobr>
							</li>
							</c:if>
							</c:forEach>
							
						</ul>
					</div>
				</div>
			</div>
			</c:forEach>
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
	</body>
</html>
