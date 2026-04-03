<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Access-Control-Allow-Origin" content="*">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="Pragma" content="no-cache" />
    <meta http-equiv="Cache-Control" content="no-cache" />
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">

    <base href="<%=basePath%>">
    <title>交易成功</title>
    <script type="text/javascript" src="<%=basePath%>/js/jquery.js"></script>
    <script>
        var basePath="<%=basePath%>";
        function fan(){
            document.location.href=basePath+"/ea/wfjshop/ea_getWFJshops.jspa?";
        }
    </script>
</head>
<body>

<div style="text-align:left;margin-left:5px;margin-right:5px;">
    <img src="<%=basePath %>/images/WFJClient/cgzf.png" alt="支付成功" style="margin-left:20%;"/><br />
    <%-- <span>避免您的权益受到损害，请详细记录订单号。</span><br/> --%>
    <%--   	<s:if test="ddid!=null"> --%>
    <span>订单号:${ddid}</span><br/>
    <%--     </s:if> --%>
    <a href="<%=basePath %>/ea/wfjshop/ea_getWFJshops.jspa?companyId=company201009046vxdyzy4wg0000000025&indus=products">返回首页</a>
    <%-- <span>联系电话:${billsList[1]}</span><br/>
    <span>联系地址:${billsList[1]}</span><br/> --%>
    <span>金额:${morre}</span><br/>
    <span>交易日期:${shijian}</span><br/>
    <input type="button" value="继续购物" id="fan" onclick="fan()" style="margin-left:40%;"/>
</div>
<!-- 注册成公司 -->
<%-- <s:if test="comInfor!=null">
          <span>公司名称&nbsp;:${comInfor[0] }</span><br/>
          <span>公司类型&nbsp;:${comInfor[1] }</span><br/>
          <span>组织机构名:${comInfor[3] }</span><br/>
          <span>用户名&nbsp;&nbsp;:${comInfor[2] }</span><br/>
          <span>登陆密码&nbsp;:123456</span><br/>
          <span>订单号&nbsp;&nbsp;:${comInfor[4] }</span><br/>
  </s:if>

<!-- 注册成店铺 -->
<s:if test="shopInfor!=null">
          <span>店    名 :${shopInfor[0] } </span><br/>
          <span>店    主 :${shopInfor[1] } </span><br/>
          <span>订单号 :${shopInfor[2] } </span><br/>
  </s:if> --%>
  <script>
    $(document).ready(function(){
    	var u = window.navigator.userAgent;
        var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
        var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
        try {
            if (isAndroid == true) {
                Android.speechOutputForAndroid("打赏成功");
            } else if (isiOS == true) {
            	console.log("声音提醒开发中");
            }
        } catch (e) {
            console.log("报错啦");
        }
    });
</script>
</body>
</html>
