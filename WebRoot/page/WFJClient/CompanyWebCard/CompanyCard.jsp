<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>公司网站名片</title>
<meta http-equiv="Access-Control-Allow-Origin" content="*">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=no, maximum-scale=yes, user-scalable=yes" />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<head>
<link rel="stylesheet" type="text/css" href="<%=basePath %>css/WFJClient/companycard.css"/>
<script type="text/javascript" src="<%=basePath %>js/jquery-1.6.1.min.js"></script>
<script type="text/javascript" src="<%=basePath %>js/ea/marketing/wfjeshop/companyCard.js"></script>
</head>
<script type="text/javascript">
var ccompanyId='${contactCompany.ccompanyID}';
var editType='${editType}';
</script>
<body>
<!--网站商城名片头部-->
<div class="hide">
<section id="wrap">
	<header id="head">
    	<a href="<%=basePath%>/ea/consignee/ea_toVipCenter.jspa" target="_self" class="return"><img src="<%=basePath%>/images/WFJClient/PersonalJoining/wfj_return_01.png" /></a>
        <div class="title">${contactCompany.companyName }</div>
        <a href="javascript:;" class="menu"></a>
    </header>
    <div class="personage">
        <a href="#" class="pre"><img src="<%=basePath%>${contactCompany.logoPath}"/></a>
        <p>${contactCompany.cresponsible }</p>
        <a class="ewm"><img id="erweima" src="<%=basePath%>/images/WFJClient/PersonalJoining/ico_wx.png"/></a>
        <div class="designat"><c:if test="${company.ccomtype eq 0 }">大型企业平台管理商城</c:if>
        					<c:if test="${company.ccomtype eq 1 }">中型企业平台管理商城</c:if>
        					<c:if test="${company.ccomtype eq 2 }">小型企业平台管理商城</c:if>
        					<c:if test="${company.ccomtype eq 3 }">微型企业平台管理商城</c:if>
        					<c:if test="${company.ccomtype eq 4 }">供应商企业平台管理商城</c:if>
        </div>
    </div>
    <div class="datum">
    	<div class="datum_title">
        	<p>基本资料</p>
            <a style="display:none;" href="<%=basePath%>ea/industry/ea_EditCompanyCard.jspa?ccompanyId=${contactCompany.ccompanyID}&user=${user}">编辑基本资料</a>
        </div>
        <ul class="form">
        	<li>
            	<img src="<%=basePath%>/images/WFJClient/PersonalJoining/wfj_all_Profile_17.png"/>
                <input type="text" value="${contactCompany.industryType }" readonly="readonly"/>
            </li>
            <li>
            	<img src="<%=basePath%>/images/WFJClient/PersonalJoining/wfj_all_Profile_03.png"/>
                <input type="text" value="${contactCompany.companyAddr }" readonly="readonly"/>
            </li>
            <li>
            	<img src="<%=basePath%>/images/WFJClient/PersonalJoining/wfj_all_Profile_04.png"/>
                <input type="text" value="${contactCompany.cresponsible }" readonly="readonly"/>
            </li>
            <li>
            	<img src="<%=basePath%>/images/WFJClient/PersonalJoining/wfj_all_Profile_11.png"/>
                <input type="text" value="${contactCompany.companyTel }" readonly="readonly"/>
            </li>
            <li>
            	<img src="<%=basePath%>/images/WFJClient/PersonalJoining/wfj_shop_info_06.png"/>
                <input type="text" value="${contactCompany.companyWeb }" readonly="readonly"/>
            </li>
            <li>
            	<img src="<%=basePath%>/images/WFJClient/PersonalJoining/wfj_shop_info_02.png"/>
                <input type="text" value="${company.companyIdentifier }" readonly="readonly"/>
            </li>
        </ul>
    </div>
    <div class="message">
    	<ul>
        	<li>
            	<a class="edit" href="<%=basePath%>ea/industry/ea_CompanyCertificates.jspa?ccompanyId=${contactCompany.ccompanyID}&user=${user}">
            		<img src="<%=basePath%>/images/WFJClient/PersonalJoining/img_egi.png"/>
                	<p>诚信证件</p>
                </a>
            </li>
            <li>
            	<a class="edit" href="<%=basePath%>ea/industry/ea_getBankCardsList.jspa?ccompanyId=${contactCompany.ccompanyID}&user=${user}">
            		<img src="<%=basePath%>/images/WFJClient/PersonalJoining/img_seve.png"/>
                	<p>银行卡信息</p>
                </a>
            </li>
            <li>
            	<a class="edit" href="<%=basePath%>ea/industry/ea_getCompanyMien.jspa?ccompanyId=${contactCompany.ccompanyID}&user=${user}">
            		<img src="<%=basePath%>/images/WFJClient/PersonalJoining/img_one.png"/>
                	<p>奖励风采</p>
                </a>
            </li>
            <li>
            	<a class="edit" href="<%=basePath%>ea/wfjshop/ea_getNewsList.jspa?ccompanyId=${contactCompany.ccompanyID}&user=${user}&search=${contactCompany.companyName }">
            		<img src="<%=basePath%>/images/WFJClient/PersonalJoining/img_nws.png"/>
                	<p>新闻动态</p>
                </a>
            </li>
            <li>
            	<a class="edit" href="<%=basePath%>ea/industry/ea_getCompanyActivitiesList.jspa?ccompanyId=${contactCompany.ccompanyID}&user=${user}">
            		<img src="<%=basePath%>/images/WFJClient/PersonalJoining/img_thee.png"/>
                	<p>公司活动</p>
                </a>
            </li>
            <li>
            	<a class="edit" href="<%=basePath%>ea/industry/ea_getCompanyHome.jspa?ccompanyId=${contactCompany.ccompanyID}&user=${user}&flag=pro">
            		<img src="<%=basePath%>/images/WFJClient/PersonalJoining/img_tow.png"/>
                	<p>产品展馆</p>
                </a>
            </li>
            <li>
            	<a class="edit" href="<%=basePath%>ea/industry/ea_CompanyForumsList.jspa?ccompanyId=${contactCompany.ccompanyID}&user=${user}">
            		<img src="<%=basePath%>/images/WFJClient/PersonalJoining/img_five.png"/>
                	<p>公司论坛</p>
                </a>
            </li>
            <li>
            	<a class="edit" href="<%=basePath%>ea/industry/ea_EditCompanyStyle.jspa?ccompanyId=${contactCompany.ccompanyID}&user=${user}">
            		<img src="<%=basePath%>/images/WFJClient/PersonalJoining/img_six.png"/>
                	<p>公司风格</p>
                </a>
            </li>
        </ul>
    </div>
    <div class="datum">
    	<div class="datum_title">
        	<p>风采展示</p>
            <%-- <a href="<%=basePath%>ea/industry/ea_getCompanyMien.jspa?ccompanyId=${ contactCompany.ccompanyID}&user=${user}">查看更多</a> --%>
        </div>
        <div style="width:100%">
        <ul class="show">
        	<c:forEach items="${companyMienList }" var="entity">
        	<li>
            	<img src="<%=basePath%>${entity[3]}"/>
            </li></c:forEach>           
        </ul>
        </div>
    </div>
    <div class="skip">
    	<p onclick="jhmp()"><a href="#">交换名片</a></p>
        <p><a href="<%=basePath %>page/WFJClient/NewLogin.jsp?loginPage=login&user=${user}">现在加入</a></p>
    </div>
    <div style="position: absolute;top:0%;left:0%;width: 100%;height:100%;background: rgba(0,0,0, 0.5);display: none;" id="QRCode">
    	<div style="background-color: #FFFFFF;position: relative;top: 15%;left: 20%;" id="QRCodeDiv">
    		<img id="interceptImg" src="<%=basePath%>${contactCompany.qrCodePath }" style="width: 100%;height: 100%;">
    	</div>
    </div>
</section>
</div>
<script type="text/javascript">
var basePath='<%=basePath%>';
var user="${user}";//公司账户,客户账户,要粉的账户
$(function(){
	//加载公司风格
	var url=basePath+"ea/industry/sajax_ea_CompanyStyle.jspa?ccompanyId="+ccompanyId;
	$.ajax({
		url : url,
		type : "get",
		async : false,
		success : function cbf(data){
			var member=eval("("+data+")");
			var activities=member.activities;
			if(activities!=null){
				$("#head").css("background",activities.describe);
				$(".personage").css("background",activities.describe);
			}
		},
		error : function cbf(data){
			alert("公司风格加载失败！");
		}
	});
	if($(window).width()>$(window).height()){
		$("#wrap").attr("style","width:70%;");
	}else{
		$("#wrap").attr("style","width:100%;");
	}
	//可编辑
	if(editType=='0'){
		$(".datum_title").find("a").attr("style","display:block");	
	}else{
		$(".edit").each(function(){
			$(this).attr("href","javascript:;");
		});
	}
})
function jhmp(){
	if("${contactCompany.ccompanyID}"==""){
		return;
	}
	var purl=new Array(basePath);
	purl.push("ea/wfjshop/ea_companyGiveCard.jspa?");
	purl.push("ccompanyId=${contactCompany.ccompanyID}");
	purl.push("&weidiantype=company&user=${user}");
	window.location.href = purl.join("");
}
</script>
</body>
</html>