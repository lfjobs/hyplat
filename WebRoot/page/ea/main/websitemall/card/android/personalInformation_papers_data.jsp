<!doctype html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags"  prefix="s"%>
<html lang="en">
<head>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String backurl=request.getParameter("backurl");
%>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>证件信息</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/websitemall/card/style.css"/>
<script src="<%=basePath%>js/jquery-1.6.1.min.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<link rel="stylesheet" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"  type="text/css"/>
<script type="text/javascript" src="<%=basePath%>js/ea/websitemall/card/android/personalInformation_papers_data.js"></script>
<style type="text/css">
	#prompt div{
		width: 70%;
		background: rgba(0,0,0, 0.5);
	}
</style>
<script type="text/javascript">
	var basePath="<%=basePath%>";
	var $this=null;
	var editType="${editType}";
	var category="${category}";
	var backurl="<%=backurl%>";
</script>
</head>
<body>
<!--网站商城名片头部-->
 <section id="wrap">
	<header id="head">
    	<a href="javascript:;" target="_self" class="return"><img src="<%=basePath%>images/ea/websitemall/card/wfj_return_02.png" /></a>
        <div class="title">${staff.staffName} - 证件信息</div>
        <a href="javascript:;" class="menu display"><img src="<%=basePath%>images/ea/websitemall/card/dot.png" /></a>
    </header>
    <div class="personage">
        <a href="#" class="pre"><img src="<%=basePath%>${staff.headimage}"/></a>
        <p>${staff.staffName}<input type="hidden" id="staffId" value="${staff.staffID}"></p>
        <a href="#" class="ewm"><img src="<%=basePath%>images/ea/websitemall/card/ico_wx.png" class="wfj01_006_ewm"/></a>
        <div class="designat">
        	${cuscom.cusType=='2'?'公司商城业主会员':
        	cuscom.cusType=='3'?'合伙人商城业主会员':
			cuscom.cusType=='4'?'微分金商城业主会员':
			cuscom.cusType=='5'?'代理商商城业主会员':
			cuscom.cusType=='6'?'VIP客户':'普通客户'}
        </div>
    </div>
    <div id="prompt" style="width: 100%;display: none;">
        <center>
        	<div>
        		<span style="position: relative;top: 19.8%;"></span>
        	</div>
        	</center>
        </div>
    <div class="status">
    	<form method="post" id="form" name="form" enctype="multipart/form-data">
    	<p>证件信息<input type="hidden" name="category" value="${category}" id="category"></p>
    	<input type="hidden" name="staffId" value="${staffId}">
        <div class="status_img" name="positive">
        	<s:if test="#request.paper.positive!=null&&#request.paper.positive!=''">
        		<img src="<%=basePath%>${paper.positive}" class="positive">
        		<input type="hidden" name="originalPositive" id="originalPositive" value="${paper.positive}">
        	</s:if>
        	<s:else>
	        	<img src="<%=basePath%>images/ea/websitemall/card/wfj_addimg_01.png"/>
	            <p>上传证件正面照</p>
        	</s:else>
        </div>
        <div class="status_img" name="back">
        	<s:if test="#request.paper.back!=null&&#request.paper.back!=''">
        		<img src="<%=basePath%>${paper.back}" class="back">
        		<input type="hidden" name="originalBack" id="originalBack" value="${paper.back}">
        	</s:if>
        	<s:else>
	        	<img src="<%=basePath%>images/ea/websitemall/card/wfj_addimg_01.png"/>
         	   <p>上传证件背面照</p>
        	</s:else>
        </div>
      	<input type="file" accept="image/*"  style="display: none;" id="positive" name="positive" class="file"/>
        <input type="file" accept="image/*"  style="display: none;" id="back" name="back" class="file"/>
        <iframe name="hidden"  style="display: none;"></iframe>
        <input type="submit" name="submit" id="submit" style="display: none;">
        </form>
    </div>
    <div class="skip display">
        <p class="bc"><a href="javascript:;">保存信息</a></p>
    </div>
    <div>
    </div>
    <div class="photo">
        <ul>
            <li><a href="javascript:;">拍照</a></li>
            <li><a href="javascript:;">从本地上传</a></li>
            <li><a href="javascript:;">取消</a></li>
        </ul>
    </div>
    <div id="div">
    </div>
    <div style="position: absolute;top:0%;left:0%;width: 100%;height:100%;background: rgba(0,0,0, 0.5);display: none;" id="QRCode">
    	<div style="background-color: #FFFFFF;position: relative;top: 15%;left: 20%;" id="QRCodeDiv">
    		<img id="interceptImg" src="<%=basePath%>${staff.qrCodePath}" style="width: 100%;height: 100%;">
    	</div>
    </div> 
</section>
<div id="occlusion2" class="jqmWindow jqmWindowcss1"></div>
</body>
</html>