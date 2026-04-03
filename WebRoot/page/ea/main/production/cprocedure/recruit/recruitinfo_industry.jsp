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

<title>行业类别</title>


<link rel="stylesheet" href="<%=basePath%>/css/bootstrap.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/ea/production/my_css.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/ea/production/zhaopin.css" />
<script src="<%=basePath%>js/jquery-1.11.3.js" type="text/javascript"></script>
<script src="<%=basePath%>/js/bootstrap.js"></script>
<script type="text/javascript" src="<%=basePath%>js/ea/production/cprocedure/recruit/recruitinfo_industry.js"></script>

<script type="text/javascript">

var basePath="<%=basePath%>";
var pagecount = ${pageForm.pageCount==null?0:pageForm.pageCount};
var count = ${pageForm.recordCount==null?0:pageForm.recordCount};
var pageSize = ${pageForm.pageSize==null?0:pageForm.pageSize};
var pagenumber = ${pageForm.pageNumber==null?0:pageForm.pageNumber};
var type = "${param.type}";
var selectindus = "${param.selectindus}";
var selectpos = "${param.selectpos}";
var selectcitys = "${param.selectcitys}";
var jitype="${param.jitype}";
var sccId="${param.sccId}";
//保存的情况
var staffid="${param.staffid}"; //共用**
var staffIDs="${param.staffIDs}";//共用**
var staffids="${param.staffids}";//共用**
var work = "${param.work}";//工作性质
var region ="${param.region}";//工作地区
var industry ="${param.industry}";//行业类别**共用
var salary = "${param.salary}";//期望薪资
var status = "${param.status}";//求职状态
var position="${param.position}";
var typedata="${param.typedata}";
var name="${param.name}";
var graduationTime="${param.graduationTime}";
var admissionTime="${param.admissionTime}";


var postName="${param.postName}";
var duties="${param.duties}";
//修改的情况下
var jobWantedKeys="${param.jobWantedKeys}";
var resumeIDs="${param.resumeIDs}";
var jobWantedIds="${param.jobWantedIds}";

var recordKey ="${param.recordKey}";
var recordID ="${param.recordID}";
var resumeID ="${param.resumeID}";
var recordKeya ="${param.recordKeya}";
var recordIDa ="${param.recordIDa}";
var resumeIDa ="${param.resumeIDa}";

var resumeID = "${param.resumeID}";//每个人的id
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
				<input type="search" name="" id="search" placeholder="搜索行业类别" value="" />
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
			<div class="kexuan_main">
				<div class="xuan_head">已选行业
					<span>（<span id="shuzhi" style="color: #ff6600;">0</span>/3）</span>
					<span class="xia"></span>
				</div>
				<div class="xuan_hangye" id="xuanze">
					
					<div class="condition">
						<span>尚未选择，请选择条件</span>
						
					</div>
				</div>
			</div>
		</div>
		</div>
		<div class="mainlis">
			<c:forEach items="${pageForm.list}" var="item" varStatus="status">
			 <c:if test="${fn:length(pageForm.list)-1 eq status.index}">
                <div class="kexuan last">
            </c:if> 
             <c:if test="${fn:length(pageForm.list)-1 ne status.index}"> 
            <div class="kexuan">
            </c:if> 
				
					<div class="kexuan_main">
						<span class="xuan_head">${item.codeValue}</span>
						<div class="xuan_hangye xhy">
						   <c:forEach items="${sublist}" var="itemsub">
						   <c:if test="${item.codeID eq itemsub.codePID}">
							<div class="xuan_lis" id="${itemsub.codeID}">
								<span>${itemsub.codeValue}</span> <span style="display:none;">${itemsub.codeID}</span>
							</div>
							
							</c:if>
							</c:forEach>
						</div>
					</div>
				</div>
			</c:forEach>
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
</div>
</body>

</html>
