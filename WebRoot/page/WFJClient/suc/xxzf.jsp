<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
<base href="<%=basePath%>">
    <title>线下支付提示页面</title>
    <script type="text/javascript" src="<%=basePath%>/js/jquery-1.6.1.min.js"></script>
    <script>
    var basePath="<%=basePath%>";
    var threeNo="${param.threeNo }";
    $.ajax({
    	url:basePath+"ea/wfjshop/sajax_ea_sendMsg.jspa",
				type:"get",
				data:"ddid="+threeNo,
				success:function suc(data){
					if(data=="success"){
					$("#msg").text("短信已发送.");
					}
				}
    });
    </script>
  </head>
  <body>
  <span>务必将[识别码]内容填写到[转账附言]中.转账成功后,在会员中心>>订单管理中操作[确认转账]功能</span>
  <br/>
  <br/>
  <span>银行账户:0200 2107 0902 0118 801(19位)</span>
  <br/>
  <span>开户名称:北京天太世统科技有限公司</span>
  <br/>
  <span>开户银行:中国工商银行</span>
  <br/>
  <span>支行:北京东直门支行</span>
  <br/>
  <span>开户行地址:北京市东城区东直门外新中街甲2号</span>
  <br/>
  <span>识别码:${param.threeNo }</span>
  <br/>
  <br/>
  <span id="msg"></span>
  <br/>
  <br/>
  <button onclick="window.location.href='<%=basePath%>ea/wfjshop/ea_getWFJshops.jspa'">返回首页</button>
  </body>
</html>
