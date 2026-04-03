<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>单位图片管理</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<link href="<%=basePath%>/css/ea/validate.css" rel="stylesheet" type="text/css" />
<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script type="text/javascript" src="<%=basePath%>js/photoup/CJL.0.1.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/photoup/ImagePreviewd.js"></script>
<script type="text/javascript" >
   var token = 0;
   var select = 1;
   var contactimgID = '';
   var basePath = '<%=basePath%>';
   var pNumber = ${pageNumber};
   var ccompanyID = '${cimg.ccompanyID}';
   var notoken = 0;

</script>
<script src="<%=basePath%>js/ea/office_ea/contactcompany/contactimg.js"></script>
</head>
<body>
<form  name="cImgForm" id="cImgForm"  method="post">
<s:token></s:token><input type="submit" name="submit" style="display:none"/>

<div id="main_main" class="main_main">
  <table class="contactimg">
	  	<thead>
		 	    <tr>
			 	    <th width="30" align="center">选择</th>
		            <th width="150" align="center">图片名称</th>
		            <th width="300" align="center">图片介绍</th>
		            <th width="150" align="center">图片地址</th>
	      		</tr>
	    </thead>
		<tbody id="tbwid">
          <s:iterator value="cimgList">
	          <tr class="td_bg01 saveAjax trclass" id="${contactimgID}">
	         	 	<td class="td_bg01">
	            	 	<input type="radio" name="a" class="JQuerypersonvalue" value="${contactimgID}"/>
	            	</td>
		            <td class="td_bg01">
		                <span id="imgName" >${imgName}</span>
		            <td class="td_bg01">
		                <span id="imgContent" >${imgContent}</span>
	            	<td class="td_bg01">
	             		 <span id="imgFile" style="display: none;">${imgFile}</span>
	             		 <s:if test="imgFile != ''"><a href="#" onclick="ck('${imgFile}')">查看</a></s:if>
	             		 <s:if test="imgFile == ''">无</s:if>
	          </tr>
          </s:iterator>
    	</tbody>
  </table>
</div>
</form>
<!-- 图片添加 -->
<form name="saveform" id="saveform" method="post" enctype="multipart/form-data">
	<div class="jqmWindow" style="width: 365px; top: 15%;left: 15%; " id="jqmadd">
		<div id="metitle" class="drag">
			图片信息
			<div class="close"></div>
		</div>
		<table id="addTab">
			<tr>
				<td align="right" width="100">
					图片名称：
				</td>
				<td>
					<input name="cimg.imgName" id="imgName"  size="30" />
				</td>
			</tr>
			<tr>
				<td align="right" width="100">
					图片内容：
				</td>
				<td>
					<input name="cimg.imgContent" id="imgContent" size="30"/>
				</td>
			</tr>
			<tr>
				<td align="right" width="100">
					选择图片：
				</td>
				<td>
					<input type="file" name="cimg.photo" id="photo" />
				</td>
			</tr>
		</table>
		<div align="center">
			<input type="button" class="input-button" onclick="saveM()" value="&nbsp;保存 &nbsp;"/>
		</div>
	</div>
</form>
<!-- 预览图片 -->
<div class="jqmWindow" style="width: 200px; top: 15%;left: 25%; " id="jqmshow">
		<div id="metitle" class="drag">
			图片信息
			<div class="close"></div>
		</div>
		<table id="showTab">
			<tr>
				<td>
					<img id="idImg" style="width: 170px; height: 120px; "/>
				</td>
			</tr>
		</table>
		<div align="center">
	</div
<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
<script type="text/javascript">
$(function(){   
	setTimeout(function(){ 			
  	  $("div.bDiv").css({"height":parent.document.getElementById("mainframe22").offsetHeight-57+"px"});
    },100);
	 $(window).resize(function(){ 
		      setTimeout(function(){ 			
		    	  $("div.bDiv").css({"height":parent.document.getElementById("mainframe22").offsetHeight-57+"px"});
		      },100);
	 }); 	
});
</script>
</body>
</html>
