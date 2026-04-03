<!doctype html>
<%@ page language="java" pageEncoding="UTF-8"%>
<html lang="en">
<head>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String backurl=request.getParameter("backurl");
%>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<script src="<%=basePath%>js/jquery-1.6.1.min.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath%>js/ea/websitemall/card/android/personalInformation_papers_list.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/websitemall/card/hyzx.css"/>
    
 <script type="text/javascript">
	var basePath="<%=basePath%>";
	var $this=null;
	var editType="${editType}";
	var backurl="<%=backurl%>";
</script>
<title>会员中心</title>
</head>
<body>
<div class="main">
    <div class="top">
        	<ul>
            	<li class="wfj_return"><a href="javascript:;"><img src="<%=basePath%>images/ea/websitemall/card/wfj_return_02.png" /></a></li>
            	<li>证件信息<input type="hidden" name="staffId" id="staffId" value="${staffVo.cstaff.staffID}"></li>
            	<li><a href="javascript:;" style="display: none;"><img class="add" src="<%=basePath%>images/ea/websitemall/card/addbank.png" /></a></li>
            </ul>
        </div>
    <div class="papers_body">
    	<div class="papers_body_hidden">
		    	<ul>
		        	<li>
		                <div class="pic"><img src="<%=basePath%>images/ea/websitemall/card/sfz1.png" /></div>
		                身份证
		                <div class="more" id="001"><img src="<%=basePath%>images/ea/websitemall/card/more1.png" /></div>
		            </li>
		            <li>
		            	<div class="pic"><img src="<%=basePath%>images/ea/websitemall/card/jkz1.png" /></div>
		                健康证
		                <div class="more" id="002"><img src="<%=basePath%>images/ea/websitemall/card/more1.png" />
		            </li>
		            <li>
		            	<div class="pic"><img src="<%=basePath%>images/ea/websitemall/card/jhz1.png" /></div>
		                结婚证
		                <div class="more" id="003"><img src="<%=basePath%>images/ea/websitemall/card/more1.png" /></div>
		            </li>
		            <li>
		            	<div class="pic"><img src="<%=basePath%>images/ea/websitemall/card/jsz1.png" /></div>
		                驾驶证
		                <div class="more" id="004"><img src="<%=basePath%>images/ea/websitemall/card/more1.png" />
		            </li>
		            <li>
		            	<div class="pic"><img src="<%=basePath%>images/ea/websitemall/card/gzz1.png" /></div>
		                工作证
		                <div class="more" id="005"><img src="<%=basePath%>images/ea/websitemall/card/more1.png" />
		            </li>
		            <li>
		            	<div class="pic"><img src="<%=basePath%>images/ea/websitemall/card/byz1.png" /></div>
		                毕业证
		                <div class="more" id="006"><img src="<%=basePath%>images/ea/websitemall/card/more1.png" /></div>
		            </li>
		            <li>
		            	<div class="pic"><img src="<%=basePath%>images/ea/websitemall/card/xsz1.png" /></div>
		                学生证
		                <div class="more" id="007"><img src="<%=basePath%>images/ea/websitemall/card/more1.png" /></div>
		            </li>
		            <div class="clear"></div>
		        </ul>
    	</div>
    </div>
    <div class="bottom"></div>
</div>
<div class="add_1">
    <div class="mu"></div>
    <div class="tan">
        <h3>添加证书</h3>
        <div class="hide">
            <ul>
               
            </ul>
        </div>
    </div>
</div>
</body>
</html>
