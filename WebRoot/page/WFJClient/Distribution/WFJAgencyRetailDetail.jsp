<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<base href="<%=basePath%>">
	<title>产品代理零售商会员</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript"></script>
	<link href="<%=basePath%>css/WFJClient/style07.css" rel="stylesheet" />
	<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/WFJClient/PhoneWfj.js"></script>
	<script src="<%=basePath%>js/ea/wechat/wechartMenugrcp.js"></script>
	
	<script type="text/javascript">
		var basePath='<%=basePath%>';
		var token=0;
		var cpid="";
		var notoken=0;
	</script>
	</head>
	<body>
		<div class="wfj07_003 bgcolor0091f1">
        <div class="wfj07_width ">
            <div class="wfj07_top wfj07_t">
                <ul>
                    <li class="colorfff"><</li>
                    <li class="colorfff">微分金店</li>
                    <li class="colorffd800">产品代理零售商会员</li>
                </ul>
            </div>
        </div>
        <div class="wfj07_block">
            <div class="wfj07_width">
                <ul>
                    <li class="color0091f1" style="width:40%;">产品代理零售商会员</li>
                    <li style="width:25%;"><img src="<%=basePath%>/images/WFJClient/Distribution/wfj07_jf.png" /><span class="color000" style="font-weight:bold;">积分1000</span></li>
                </ul>
                <ul>
                    <li style="width:40%;">代理产品的数量</li>
                    <li style="width: 25%;">100</li>
                    <li style="width: 25%;">本月订单的数量</li>
                    <li class="talignright" style="width:10%;">100</li>
                </ul>
                <ul>
                    <li style="width:40%;">佣金</li>
                    <li style="width: 25%;">100</li>
                    <li style="width: 25%;">月销售的数量</li>
                    <li class="talignright" style="width:10%;">100</li>
                </ul>
            </div>
        </div>
        <div class="wfj07_block">
            <div class="wfj07_width">
                <ul>
                    <li class="color0091f1" style="width:40%;">产品代理零售商会员</li>
                    <li style="width:25%;"><img src="<%=basePath%>/images/WFJClient/Distribution/wfj07_jf.png" /><span class="color000" style="font-weight:bold;">积分1000</span></li>
                </ul>
                <ul>
                    <li style="width:40%;">代理产品的数量</li>
                    <li style="width: 25%;">100</li>
                    <li style="width: 25%;">本月订单的数量</li>
                    <li class="talignright" style="width:10%;">100</li>
                </ul>
                <ul>
                    <li style="width:40%;">佣金</li>
                    <li style="width: 25%;">100</li>
                    <li style="width: 25%;">月销售的数量</li>
                    <li class="talignright" style="width:10%;">100</li>
                </ul>
            </div>
        </div>
        <div class="wfj07_block">
            <div class="wfj07_width">
                <ul>
                    <li class="color0091f1" style="width:40%;">产品代理零售商会员</li>
                    <li style="width:25%;"><img src="<%=basePath%>/images/WFJClient/Distribution/wfj07_jf.png" /><span class="color000" style="font-weight:bold;">积分1000</span></li>
                </ul>
                <ul>
                    <li style="width:40%;">代理产品的数量</li>
                    <li style="width: 25%;">100</li>
                    <li style="width: 25%;">本月订单的数量</li>
                    <li class="talignright" style="width:10%;">100</li>
                </ul>
                <ul>
                    <li style="width:40%;">佣金</li>
                    <li style="width: 25%;">100</li>
                    <li style="width: 25%;">月销售的数量</li>
                    <li class="talignright" style="width:10%;">100</li>
                </ul>
            </div>
        </div>
        <div class="wfj07_block">
            <div class="wfj07_width">
                <ul>
                    <li class="color0091f1" style="width:40%;">产品代理零售商会员</li>
                    <li style="width:25%;"><img src="<%=basePath%>/images/WFJClient/Distribution/wfj07_jf.png" /><span class="color000" style="font-weight:bold;">积分1000</span></li>
                </ul>
                <ul>
                    <li style="width:40%;">代理产品的数量</li>
                    <li style="width: 25%;">100</li>
                    <li style="width: 25%;">本月订单的数量</li>
                    <li class="talignright" style="width:10%;">100</li>
                </ul>
                <ul>
                    <li style="width:40%;">佣金</li>
                    <li style="width: 25%;">100</li>
                    <li style="width: 25%;">月销售的数量</li>
                    <li class="talignright" style="width:10%;">100</li>
                </ul>
            </div>
        </div>
        <div class="wfj07_block">
            <div class="wfj07_width">
                <ul>
                    <li class="color0091f1" style="width:40%;">产品代理零售商会员</li>
                    <li style="width:25%;"><img src="<%=basePath%>/images/WFJClient/Distribution/wfj07_jf.png" /><span class="color000" style="font-weight:bold;">积分1000</span></li>
                </ul>
                <ul>
                    <li style="width:40%;">代理产品的数量</li>
                    <li style="width: 25%;">100</li>
                    <li style="width: 25%;">本月订单的数量</li>
                    <li class="talignright" style="width:10%;">100</li>
                </ul>
                <ul>
                    <li style="width:40%;">佣金</li>
                    <li style="width: 25%;">100</li>
                    <li style="width: 25%;">月销售的数量</li>
                    <li class="talignright" style="width:10%;">100</li>
                </ul>
            </div>
        </div>
        <div class="wfj07_block">
            <div class="wfj07_width">
                <ul>
                    <li class="color0091f1" style="width:40%;">产品代理零售商会员</li>
                    <li style="width:25%;"><img src="<%=basePath%>/images/WFJClient/Distribution/wfj07_jf.png" /><span class="color000" style="font-weight:bold;">积分1000</span></li>
                </ul>
                <ul>
                    <li style="width:40%;">代理产品的数量</li>
                    <li style="width: 25%;">100</li>
                    <li style="width: 25%;">本月订单的数量</li>
                    <li class="talignright" style="width:10%;">100</li>
                </ul>
                <ul>
                    <li style="width:40%;">佣金</li>
                    <li style="width: 25%;">100</li>
                    <li style="width: 25%;">月销售的数量</li>
                    <li class="talignright" style="width:10%;">100</li>
                </ul>
            </div>
        </div>
        <div class="wfj07_block">
            <div class="wfj07_width">
                <ul>
                    <li class="color0091f1" style="width:40%;">产品代理零售商会员</li>
                    <li style="width:25%;"><img src="<%=basePath%>/images/WFJClient/Distribution/wfj07_jf.png" /><span class="color000" style="font-weight:bold;">积分1000</span></li>
                </ul>
                <ul>
                    <li style="width:40%;">代理产品的数量</li>
                    <li style="width: 25%;">100</li>
                    <li style="width: 25%;">本月订单的数量</li>
                    <li class="talignright" style="width:10%;">100</li>
                </ul>
                <ul>
                    <li style="width:40%;">佣金</li>
                    <li style="width: 25%;">100</li>
                    <li style="width: 25%;">月销售的数量</li>
                    <li class="talignright" style="width:10%;">100</li>
                </ul>
            </div>
        </div>
        <div class="wfj07_block">
            <div class="wfj07_width">
                <ul>
                    <li class="color0091f1" style="width:40%;">产品代理零售商会员</li>
                    <li style="width:25%;"><img src="<%=basePath%>/images/WFJClient/Distribution/wfj07_jf.png" /><span class="color000" style="font-weight:bold;">积分1000</span></li>
                </ul>
                <ul>
                    <li style="width:40%;">代理产品的数量</li>
                    <li style="width: 25%;">100</li>
                    <li style="width: 25%;">本月订单的数量</li>
                    <li class="talignright" style="width:10%;">100</li>
                </ul>
                <ul>
                    <li style="width:40%;">佣金</li>
                    <li style="width: 25%;">100</li>
                    <li style="width: 25%;">月销售的数量</li>
                    <li class="talignright" style="width:10%;">100</li>
                </ul>
            </div>
        </div>
        <div class="wfj07_block">
            <div class="wfj07_width">
                <ul>
                    <li class="color0091f1" style="width:40%;">产品代理零售商会员</li>
                    <li style="width:25%;"><img src="<%=basePath%>/images/WFJClient/Distribution/wfj07_jf.png" /><span class="color000" style="font-weight:bold;">积分1000</span></li>
                </ul>
                <ul>
                    <li style="width:40%;">代理产品的数量</li>
                    <li style="width: 25%;">100</li>
                    <li style="width: 25%;">本月订单的数量</li>
                    <li class="talignright" style="width:10%;">100</li>
                </ul>
                <ul>
                    <li style="width:40%;">佣金</li>
                    <li style="width: 25%;">100</li>
                    <li style="width: 25%;">月销售的数量</li>
                    <li class="talignright" style="width:10%;">100</li>
                </ul>
            </div>
        </div>
    </div>
		</body>
	</html>