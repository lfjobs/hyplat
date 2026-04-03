<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
 <%@page import="hy.plat.bo.BaseBean"%>
<%@page import="java.util.List"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	int listSize=((List<BaseBean>)request.getAttribute("list")).size();
	String backurl=request.getParameter("backurl");
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/websitemall/card/style.css"/>
<script src="<%=basePath%>/js/jquery-1.9.1.min.js" type="text/javascript"></script>
<script src="<%=basePath%>js/ea/websitemall/card/android/personalInformation_history_list.js" type="text/javascript"></script>
<script src="<%=basePath%>js/ea/websitemall/card/android/jquery.time.selector.js" type="text/javascript"></script>


<title>个人履历</title>
<script type="text/javascript">
	var basePath="<%=basePath%>";
	var select=<%=listSize%>;
	var editType="${editType}";
	var backurl="<%=backurl%>";
</script>
<style type="text/css">
span{
display:-moz-inline-box;
display:inline-block;
text-align: right;}
#prompt div{
		width: 70%;
		background: rgba(0,0,0, 0.5);
	}
</style>
</head>

<body class="bgcolorFFF" style="background-color: #E8E8E8;">
<form method="post" id="form" name="form">
<input type="submit" name="submit" id="submit" style="display: none;">
<iframe name="hidden"  style="display: none;"></iframe>
	<div class="wfj01_001">
    	<!--中联园区头部-->
    	<div class="wfj_top">
        	<ul>
            	<li><a href="#" target="_self" class="return"><img src="<%=basePath%>images/ea/websitemall/card/wfj_return_02.png" /></a></li>
            	<li>${staffVo.cstaff.staffName} - 个人履历<input type="hidden" name="staffId" value="${staffId}" id="staffId"></li>
            	<li class="display"><a href="javascript:;"><img src="<%=basePath%>images/ea/websitemall/card/dot.png" /></a></li>
            </ul>
        </div>
         <div id="prompt" style="width: 100%;display: none;">
        <center>
        	<div>
        		<span style="position: relative;top: 19.8%;"></span>
        	</div>
        	</center>
        </div>
        <div  class="wfj01_001_body">
        <div class="wfj01_001_body_hidden">
        	<c:forEach items="${list}" var="l" varStatus="a">
        		<div class="wfj01_001_body_layer">
		       		<div class="wfj01_001_body_layer_top">
		       			<span style="margin-left: 10%;float: left;padding-top: 1.5%;color: #125E1A;text-align: left;">履历${a.index+1}</span>
		       			<span><a href="javascript:;"><img src="<%=basePath%>images/ea/websitemall/card/wfj_shrink_01.png" name="<%=basePath%>images/ea/websitemall/card/wfj_shrink_0"></a></span>
		       		</div>
		       		<div class="wfj01_001_body_layer_subject">
		       		<div class="wfj01_001_body_layer_content">
		       			<input type="hidden" name="staffVo.record['${a.index}'].recordID" value="${l.recordID}">
		       			<span style="width: 30%">开始日期：</span>
		       			<span><input type="text" class="time originalTime" name="staffVo.record['${a.index}'].startTime" value="${l.startTime}" readonly="readonly"></span>
		       		</div>
		       		<div class="wfj01_001_body_layer_content">
		       			<span style="width: 30%">截至日期：</span>
		       			<span><input type="text" class="time originalTime" name="staffVo.record['${a.index}'].endTime" value="${l.endTime}" readonly="readonly"></span>
		       		</div>
		       		<div class="wfj01_001_body_layer_content">
		       			<span style="width: 30%">工作单位：</span>
		       			<span><input type="text" name="staffVo.record['${a.index}'].companyName" value="${l.companyName}"></span>
		       		</div>
		       		<div class="wfj01_001_body_layer_content">
		       			<span style="width: 30%">岗位名称：</span>
		       			<span><input type="text" name="staffVo.record['${a.index}'].postName" value="${l.postName}"></span>
		       		</div>
		       		<div class="post wfj01_001_body_layer_content">
		       			<span style="width: 30%">岗位情况：</span>
		       			<span><input type="text" readonly="readonly" name="staffVo.record['${a.index}'].postCase" value="${l.postCase}"></span>
		       		</div>
		       		<div class="wfj01_001_body_layer_content">
		       			<span style="width: 30%">职务：</span>
		       			<span><input type="text" name="staffVo.record['${a.index}'].position" value="${l.position}"></span>
		       		</div>
		       		<div class="wfj01_001_body_layer_content">
		       			<span style="width: 30%">单位地址：</span>
		       			<span><input type="text" name="staffVo.record['${a.index}'].postAddress" value="${l.postAddress}"></span>
		       		</div>
		       		</div>
		       </div>
        	</c:forEach>
	       <div class="wfj01_001_body_layer" id="default">
	       		<div class="wfj01_001_body_layer_top">
	       			<span style="margin-left: 10%;float: left;padding-top: 1.5%;color: #125E1A;text-align: left;">履历<%=listSize+1%></span>
	       			<span><a href="javascript:;"><img src="<%=basePath%>images/ea/websitemall/card/wfj_shrink_01.png" name="<%=basePath%>images/ea/websitemall/card/wfj_shrink_0"></a></span>
	       		</div>
	       		<div class="wfj01_001_body_layer_subject">
	       		<div class="wfj01_001_body_layer_content">
	       			<span style="width: 30%">开始日期：</span>
	       			<span><input type="text" class="time" name="staffVo.record['<%=listSize%>'].startTime"></span>
	       		</div>
	       		<div class="wfj01_001_body_layer_content">
	       			<span style="width: 30%">截至日期：</span>
	       			<span><input type="text" class="time" name="staffVo.record['<%=listSize%>'].endTime"></span>
	       		</div>
	       		<div class="wfj01_001_body_layer_content">
	       			<span style="width: 30%">工作单位：</span>
	       			<span><input type="text" name="staffVo.record['<%=listSize%>'].companyName"></span>
	       		</div>
	       		<div class="wfj01_001_body_layer_content">
	       			<span style="width: 30%">岗位名称：</span>
	       			<span><input type="text" name="staffVo.record['<%=listSize%>'].postName"></span>
	       		</div>
	       		<div class="post wfj01_001_body_layer_content">
	       			<span style="width: 30%">岗位情况：</span>
	       			<span><input type="text" readonly="readonly" name="staffVo.record['<%=listSize%>'].postCase"></span>
	       		</div>
	       		<div class="wfj01_001_body_layer_content">
	       			<span style="width: 30%">职务：</span>
	       			<span><input type="text" name="staffVo.record['<%=listSize%>'].position"></span>
	       		</div>
	       		<div class="wfj01_001_body_layer_content">
	       			<span style="width: 30%">单位地址：</span>
	       			<span><input type="text" name="staffVo.record['<%=listSize%>'].postAddress"></span>
	       		</div>
	       		</div>
	       </div>
	        <div class="wfj01_001_body_layer" style="display: none;" id="clones">
	       		<div class="wfj01_001_body_layer_top">
	       			<span style="margin-left: 10%;float: left;padding-top: 1.5%;color: #125E1A;text-align: left;">履历11</span>
	       			<span><a href="javascript:;"><img src="<%=basePath%>images/ea/websitemall/card/wfj_shrink_01.png" name="<%=basePath%>images/ea/websitemall/card/wfj_shrink_0"></a></span>
	       		</div>
	       		<div class="wfj01_001_body_layer_subject">
	       		<div class="wfj01_001_body_layer_content"><span style="width: 30%">开始日期：</span><span><input type="text" name="startTime"  class="time" ></span></div>
	       		<div class="wfj01_001_body_layer_content"><span style="width: 30%">截至日期：</span><span><input type="text" name="endTime"  class="time" ></span></div>
	       		<div class="wfj01_001_body_layer_content"><span style="width: 30%">工作单位：</span><span><input type="text" name="companyName"></span></div>
	       		<div class="wfj01_001_body_layer_content"><span style="width: 30%">岗位名称：</span><span><input type="text" name="postName"></span></div>
	       		<div class="post wfj01_001_body_layer_content""><span style="width: 30%">岗位情况：</span>
	       			<span><input type="text" readonly="readonly" name="postCase"></span></div>
	       		<div class="wfj01_001_body_layer_content"><span style="width: 30%">职务：</span><span><input type="text" name="position"></span></div>
	       		<div class="wfj01_001_body_layer_content"><span style="width: 30%">单位地址：</span><span><input type="text" name="postAddress"></span></div>
	      	</div>
	       </div>
	       <div style="margin-top: 8%;width: 100%;height: 5%;background-color: #FFFFFF;padding-top: 2%;cursor:pointer;" class="addMore typeface display">
	       		添加更多
	       </div>
	    </div>   
    	</div>
	 	<div class="wfj01_001_bottom display">
	        <div class="wfj01_001_bottom_width">
	            保存信息
	        </div>
	    </div>
    </div>
    <div style="position: absolute;top:0%;width: 100%;height:100%;background: rgba(0,0,0, 0.5);display: none;" class="wfj01_001_popup">
    	<div style="background-color: #FFFFFF;width: 100%;height:18%;position: absolute;top: 82%;text-align: center;" class="wfj01_001_popup_body typeface">
    		<div style="height: 18%;padding-top: 3%;cursor:pointer;" class="positionSelection" name="job">在职</div>
    		<hr/>
    		<div style="height: 18%;cursor:pointer;" class="positionSelection" name="quit">离职</div>
    		<hr/>
    		<div class="positionSelection" name="other" style="cursor:pointer;">其他</div>
    	</div>
    </div>
</form>
</body>
</html>
