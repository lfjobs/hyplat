<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    
    <title>申请加入联营经济平台会员</title>
    
	 <link type="text/css" rel="stylesheet" href="<%=basePath%>/css/WFJClient/platform/style.css">
    <link type="text/css" rel="stylesheet" href="<%=basePath%>/css/WFJClient/platform/style2016.9.18.css">
    <script type="text/javascript" src="<%=basePath%>js/jquery-1.9.1.min.js"></script>

  </head>
  
 <header>
    <ul>
        <li style="width: 10%; position: relative;">
            <a href="javascript:history.go(-1)"><img src="<%=basePath%>/images/WFJClient/Platform/left_jt.png" style="position: absolute;top: 50%;margin-top: -0.35rem;"></a>
        </li>
        <li style="width: 80%;">申请加入联营经济平台会员</li>
        <li style="width: 10%;"></li>
        <div class="clearfix"></div>
    </ul>
</header>
<div class="content content2">
    <ul class="terrace">
   <!--  <a href="#;">
            <li>
                <p>行</p>
                <span>中联园行业经济平台</span>
            </li>
        </a> -->
    	<c:forEach items="${list}" var="a">
         <a onclick="ON(this)">
         <input type="hidden" id="codeID" value="${a.codeID }">
         <input type="hidden" id="codePID" value="${a.codePID }">
          <input type="hidden" id="codeValue" value="${a.codeValue }">
          
            <li>
                <p>${fn:substring(a.codeValue,0,1)}</p>
                <span>${fn:substring(a.codeValue,0,2)}平台</span>
            </li>
        </a>
        </c:forEach>
        
         <a href="<%=basePath%>ea/wfjplatform/ea_getpk.jspa?ccompanyId=contactCompany20101230UB4U5884S30000000176&typeNews=MyjiaruCompany">
            <li>
                <p>公</p>
                <span>公司认领</span>
            </li>
        </a>
        <c:if test="${param.nogr ne 'nogr'}">
        <%--<a href="<%=basePath%>ea/wfjplatform/ea_getpk.jspa?ccompanyId=contactCompany20101230UB4U5884S30000000176&typeNews=">--%>
            <%--<li>--%>
                <%--<p>个</p>--%>
                <%--<span>个人认领</span>--%>
            <%--</li>--%>
        <%--</a>--%>

            <a href="<%=basePath%>page/WFJClient/pc/my/vip/perVip.jsp">
                <li>
                    <p>个</p>
                    <span>个人认领</span>
                </li>
            </a>
        </c:if>
       <input type="hidden" id="address" value="${address.addressID}">
       
       <c:forEach items="${list}" var="a">
         <a onclick="ON1(this)">
         <input type="hidden" id="codeID" value="${a.codeID }">
         <input type="hidden" id="codePID" value="${a.codePID }">
          <input type="hidden" id="codeValue" value="${a.codeValue }">
            <li>
                <p>${fn:substring(a.codeValue,0,1)}</p>
                <span>${fn:substring(a.codeValue,0,1)}品代理</span>
            </li>
        </a>
        </c:forEach>
       <%-- <a href="<%=basePath%>ea/wfjshop/ea_doodsDetail.jspa?goodsname=设备安装&ppid=p20170220ZVZR76B88M0000000017&standard=a&money=500&addre=北京天太世统科技有限公司&companyId=company201009046vxdyzy4wg0000000025">
            <li>
                <p>安</p>
                <span>设备安装</span>
            </li>
        </a>
        <a href="<%=basePath%>ea/wfjshop/ea_doodsDetail.jspa?goodsname=贴牌&ppid=p20170220ZVZR76B88M0000000016&standard=a&money=500&addre=北京天太世统科技有限公司&companyId=company201009046vxdyzy4wg0000000025">
            <li>
                <p>贴</p>
                <span>贴牌</span>
            </li>
        </a>--%>
    </ul>
</div>

<script>
var basePath='<%=basePath%>';

function ON(obj){
	var codeValue = $(obj).find("#codeValue").val();
	var address = $("#address").val();
	console.log($("#address").val())
	document.location.href= basePath+"ea/wfjplatform/ea_getPlatform.jspa?type=qurey&content="+codeValue+"&addre="+address;
}

function ON1(obj){
	var codeValue = $(obj).find("#codeValue").val();
	var address = $("#address").val();
	console.log($("#address").val())
	document.location.href= basePath+"ea/wfjplatform/ea_getPlatform.jspa?type=qureyy&content="+codeValue+"&addre="+address;
}
    $(document).ready(function(){
        $("header").attr("style","height:"+$(window).height()*0.08+"px;line-height:"+$(window).height()*0.08+"px;");
        $("header ul li").css("height",$(window).height()*0.08+"px");
        $(".content").attr("style","margin-top:"+$(window).height()*0.08+"px;height:"+$(window).height()*0.92+"px;overflow: auto;");

    });
</script>
<script>
    window.onload = window.onresize = function(){
        var clientWidth = document.documentElement.clientWidth;
        if(clientWidth>=960){
            document.getElementsByTagName('html')[0].style.fontSize = clientWidth / 640 * 15 + 'px';

        }else {
            //通过屏幕宽度去设置不同的后台根字体的大小
            document.getElementsByTagName('html')[0].style.fontSize = clientWidth / 640 * 40 + 'px';
        }
    }
</script>
</body>
</html>
</html>
