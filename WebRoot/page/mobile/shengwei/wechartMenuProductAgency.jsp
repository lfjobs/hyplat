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
	<title>产品代理</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
	<link href="<%=basePath%>/css/WFJClient/wfjhtmlStyle.css" rel="stylesheet" />
	<link href="<%=basePath%>css/WFJClient/wfjstyle.css" rel="stylesheet" />
	
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/overlayer.css" />
	
	<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
	<script src="<%=basePath%>/js/WFJClient/PhoneWfj.js"></script>
	<script src="<%=basePath%>js/ea/wechat/wechartMenugrcp.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/overlayer.js"></script>
	<script type="text/javascript">
		var basePath='<%=basePath%>';
		var token=0;
		var cpid="";
		var notoken=0;
	</script>
	
	<script>
		bdText= $("#bdText").val();	
		bdUrl= $("#bdUrl").val(); 	
		bdPic= $("#bdPic").val();
		window._bd_share_config = {
				common : {
					bdText : bdText,	
					bdUrl : bdUrl, 	
					bdPic : bdPic
				},
				share : [{
					"bdSize" : 32
				}]
				
				
		}
		with(document)0[(getElementsByTagName('head')[0]||body).appendChild(createElement('script')).src=basePath + '/js/WFJClient/share.js?cdnversion='+~(-new Date()/36e5)];
	</script>
	</head>
	<body>
	<%-- 分享 --%>
	<form name="ckForms" id="ckForms" method="post" enctype="multipart/form-data">
		<div class="jqmWindow jqmWindowcss1" style="top: 20%;left: 73%;width:18%;height:7%;padding:10px 0px 0px 30px;" id="ckjqModel">
				<div class="bdsharebuttonbox" data-tag="share_1">
				<input type="text" name="bdText" id="bdText"/>
					<input type="text" name="bdUrl" id="bdUrl"/>
					<input type="text" name="bdPic" id="bdPic"/>
				<a class="bds_tsina" data-cmd="tsina"></a>
				<a class="bds_qzone" data-cmd="qzone" href="#"></a>
				<a class="bds_tqf" data-cmd="tqf"></a>
				<a class="bds_weixin" data-cmd="weixin"></a>
				<a class="bds_tqq" data-cmd="tqq"></a>
				<input type="button" class="btn02 JQueryreturns" name="button4" style="margin-top:5px;height:35px;width:35px;" value="关闭" /> 
			</div>
		</div>
	</form>
		<div class="wfjall">
        <div class="wfjtop">
            <ul>
                <li><a href="<%=basePath%>/ea/buyproducts/ea_getOrders.jspa" ><img src="<%=basePath%>images/WFJClient/return_fh.png"  style="float:left;width:32px;height:32px;"/></a><span style="margin-left:-25px;">产品</span></li>
            </ul>
        </div>
        <div class="wfj_class">
            <ul>
                <li><div onclick="change_agencys(this)" name="agencysinfo">代理</div></li>
                <li class="change_bg"><div onclick="change_agencys(this)" name="agencysinfo" style="color: #2E63FB">未代理</div></li>
            </ul>
        </div>
        <!--未代理-->
        <div class="unagency" id="unagencys">
        <form method="post" id="dlform" >
            <c:forEach var="n" items="${noBeans}" varStatus="nob">
	            <div class="wfj_block">
	                <div class="all_width">
	                    <div class="wfj_proimg">
	                    	<input type="hidden" id="pID" value="${n[0]}">
	                        <img src="<%=basePath %>/${n[3]}"  width="60px" height="80px;"/>
	                    </div>
	                    <div class="wfj_pro_info">
	                        <ul>
	                            <li class="referral">${n[1]}</li>
	                        </ul>
	                        <ul class="wfj_m">
	                            <li><span style="color:#ff0000;">佣金：￥20</span></li>
	                            <li>现价：￥${n[2]}</li>
	                        </ul>
	                        <ul class="wfj_m center">
	                            <li>
		                            <a href="<%=basePath %>/ea/wfjshop/ea_doodsDetail.jspa?ppid=${n[0]}&organizationID=${n[6]}&goodsid=${n[4]}&comId=${n[5]}">
		                                <div class="wfjdetails">查看详情</div>
		                            </a>
	                            </li>
	                            <li>
	                                <div class="wfjagency" onclick="dl()">我来代理</div>
	                            </li>
	                        </ul>
	                    </div>
	                </div>
	            </div>
            </c:forEach>
            <div style="display: none;">
          <iframe name="hidden" height="0" width="0"></iframe>
         	 <input type="text" id="chenpID"name="chenpID"/>
       		<s:token></s:token></div>
       	</form> 
        </div>

        <!--已代理-->
        <div class="unagency" id="agency" style="display:none;">
 			<form method="post" id="qxform">
 			<c:forEach var="s" items="${beans}" varStatus="idc">
            <div class="wfj_block">
                <div class="all_width">
                    <div class="wfj_proimg">
                    <input type="hidden" id="ppID" value="${s[1]}">
                        <img src="<%=basePath %>/${s[8]}"  width="60px" height="80px;"/>
                    </div>
                    <div class="wfj_pro_info">
                        <ul>
                            <li class="referral">${s[2]}</li>
                        </ul>
                        <ul>
                            <li style="color:#ff0000;">￥${s[3]}</li>
                            <li><span class="wfjyong">佣金：￥20</span></li>
                        </ul>
                        <ul class="wfj_m center">
                            <li>
                                <div class="wfjshare" id="wfjshare" name="wfjshare" onclick="fenxiang('${s[2]}','/ea/wfjshop/ea_doodsDetail.jspa?ppid=${s[1]}&organizationID=${s[12]}&goodsid=${s[10]}&comId=${s[11]}','${s[8]}')">分享</div> 
                            </li>
                            <li>
                                <div class="wfjcancel" onclick="qx()">取消代理</div>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            </c:forEach>
            <div style="display: none;">
       		 <input  type="submit" value="提交"/>
           	 <input type="text" name="chenpID" id="chenID" />
              <iframe name="hidden" height="0" width="0"></iframe>
       		<s:token></s:token>           	
        
        </div>
        </form>
        </div>
    </div>
		</body>
	</html>