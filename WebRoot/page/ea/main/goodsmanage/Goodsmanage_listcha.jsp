<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
	<%@ taglib uri="/struts-tags" prefix="s"%>
	<%@page import="java.util.Date"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>物品管理-查看</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="STYLESHEET" type="text/css"
	href="<%=basePath%>js/tree/codebase/dhtmlxtree.css" />
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
	type="text/css" />
<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
<script src="<%=basePath%>js/ea/goodsmanage/goodsmanage_listcha.js"
	type="text/javascript"></script>
<link rel="stylesheet"	href="<%=basePath%>/js/jqueryplus/treeview/jquery.treeview.css" />
<link rel="stylesheet" href="<%=basePath%>/css/css.css" />
<style type="text/css">
a {
	cursor: pointer;
	text-decoration: none;
}
.input{
 border-left: 0;
   border-right: 0;
   border-top: 0;
   border-bottom: 1px solid #000000;
}
body {
	overflow-y: scroll;
}

A:hover {
	text-decoration: none;
}
</style>
<script type="text/javascript">
var basePath ="<%=basePath%>";
</script>
</head>
<body>
	<div id="top" style="width: 1500; height: 40px;background-color: red;">
		<div
			style="font-size: 18;color:white;padding-top: 10px; padding-left: 5px;">物品管理</div>
	</div>
	<div id="tupiandiv" class="div tupian"
		style="margin-top: 10px;margin: auto auto; background-color: gray; width: 400px; height:170px; border-bottom: 2px;">
		<img 
		<s:if test="#goodsManage.logoPath!=null&& #goodsManage.logoPath!=''">
					      	      src="<%=basePath%>/${goodsManage.logoPath}"
					     			    </s:if>
									<s:else >
					      	 src="<%=basePath%>/images/zanwutup.png"
					      	      </s:else >	 
			style="width: 100%; height: 100%" title="logo" id="dazhuti"
			onclick="chuan('zhuti')" />
	</div>
	<div id="daohang"
		style=" width:  1500px;background-color: red; height: 50px;padding-left: 400px;">
		<div id="jiegou"
			style="float: left;margin-top: 20px;margin-left: 50px;">
			<span style="font-size: 25px;font-weight: 4px;"><a
				onclick="zhuti('jiegou')">产品结构</a> </span>
		</div>
		<div id="jianjie"
			style="float: left;margin-top: 20px;margin-left: 50px;">
			<span style="font-size: 25px;font-weight: 4px;"><a
				onclick="zhuti('jianjie')">简&nbsp&nbsp&nbsp介</a> </span>
		</div>
		<div id="gongneng"
			style="float: left;margin-top: 20px;margin-left: 50px;">
			<span style="font-size: 25px;font-weight: 4px;"><a
				onclick="zhuti('gongneng')">功&nbsp&nbsp&nbsp&nbsp能</a> </span>
		</div>
		<div id="gongtu"
			style="float: left;margin-top: 20px;margin-left: 50px;">
			<span style="font-size: 25px;font-weight: 4px;"><a
				onclick="zhuti('yongtu')">用&nbsp&nbsp&nbsp&nbsp途</a> </span>
		</div>
		<div id="cangjia"
			style="float: left;margin-top: 20px;margin-left: 50px;">
			<span style="font-size: 25px;font-weight: 4px;"><a
				onclick="zhuti('changjia')">厂&nbsp&nbsp&nbsp&nbsp家</a> </span>
		</div>
	</div>
	<!--查看页面主题-->
	<div id="zhuti" style="width: 100%; height: 500px;">
		<!--查看页面-结构-->
		<div class="jiegou" style="width: 100%;height: 500px; display: none;">	
			<div id="Layer1" style="width: 1100px;margin-left: 250px;">
			<table class="" id="goodtable" >
									<tr>
										<th align="left" bgcolor="#E4F1FA"  style="word-break:keep-all;" >
											<span >
												<img
													src="<%=basePath%>/images/header_bg.gif" width="1"
													height="14" />&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;</span> 
										</th>	
										<th align="center" bgcolor="#E4F1FA" >
											<span class="resizeDivClass">
												<img
													src="<%=basePath%>/images/header_bg.gif" width="1"
													height="14" /> </span>  <span class="xx"></span>物品品牌名称
										</th>
										<th align="center" bgcolor="#E4F1FA" >
											<span class="resizeDivClass"
											><img
													src="<%=basePath%>/images/header_bg.gif" width="1"
													height="14" /> </span>  <span class="xx"></span>分类
										</th>	
										<th align="center" bgcolor="#E4F1FA" >
											<span class="resizeDivClass"
											><img
													src="<%=basePath%>/images/header_bg.gif" width="1"
													height="14" /> </span>  <span class="xx"></span>行业分类
										</th>	
										<th align="center" bgcolor="#E4F1FA" >
											<span class="resizeDivClass"
												><img
													src="<%=basePath%>/images/header_bg.gif" width="1"
													height="14" /> </span>  <span class="xx"></span>物品类别
										</th>	
										<th align="center" bgcolor="#E4F1FA" >
											<span class="resizeDivClass"
												><img
													src="<%=basePath%>/images/header_bg.gif" width="1"
													height="14" /> </span>  <span class="xx"></span>物品序号
										</th>
										<th align="center" bgcolor="#E4F1FA" >
											<span class="resizeDivClass"
												><img
													src="<%=basePath%>/images/header_bg.gif" width="1"
													height="14" /> </span>  <span class="xx"></span>物品规格
										</th>					
										<th align="center" bgcolor="#E4F1FA" >
											<span class="resizeDivClass"
												><img
													src="<%=basePath%>/images/header_bg.gif" width="1"
													height="14" /> </span>  <span class="xx">*</span>LOGO
										</th>
										
										<th align="center" bgcolor="#E4F1FA" >
											<span class="resizeDivClass"
												><img
												 src="<%=basePath%>w/images/header_bg.gif"
												  width="1"height="14" /> </span>  <span class="xx">*</span>主题图
										</th>
										
									</tr>																													
									<tr id="kelong1" class="checkgoods">
										<!-- 层级 -->	
										<td  bgcolor="#FFFFFF" id="hierarchy" style="text-align: left;">
											 <img src="<%=basePath%>/js/tree/codebase/imgs/plus5.gif"  class="treeTable"/><img src="<%=basePath%>/js/tree/codebase/imgs/leaf.gif" title="展开下级"  class="showOrHideChildren treeTable" /><span id="nodeName" class="showOrHideChildren treeTable"></span> 
										</td>
										<td align="center" bgcolor="#FFFFFF">
										<div class="tdiv">
											  <input type="text" id="logo" value="${goodsManage.goodsName}" name="name" style="width:90%;border:0px;"  class=" fou "  
												/>
										</div>
										</td>
										<td align="center" bgcolor="#FFFFFF">
										<div class="tdiv">
											  <input type="text" id="logo" value="${goodsManage.bigClass}" name="name" style="width:90%;border:0px;"  class=" fou "  
												/>
										</div>
										</td>
										<td align="center" bgcolor="#FFFFFF">
										<div class="tdiv">
											  <input type="text" id="logo" value="${goodsManage.typeID}" name="name" style="width:90%;border:0px;"  class=" fou "  
												/>
										</div>
										</td>
										<td align="center" bgcolor="#FFFFFF">
										<div class="tdiv">
											  <input type="text" id="logo" value="${goodsManage.tradeCode}" name="name" style="width:90%;border:0px;"  class=" fou "  
												/>
										</div>
										</td>										
										<td align="center" bgcolor="#FFFFFF">
										<div class="tdiv">
											  <input type="text" id="logo" value="${goodsManage.wupxuhao}" name="name" style="width:90%;border:0px;"  class=" fou "  
												/>
										</div>
										</td>	
										<td align="center" bgcolor="#FFFFFF">
										<div class="tdiv">
											  <input type="text" id="logo" value="${goodsManage.standard}" name="name" style="width:90%;border:0px;"  class=" fou "  
												/>
										</div>
										</td>							
										<!-- LOGO -->
										<td align="center" bgcolor="#FFFFFF">
										<div class="tdiv">
											<img id="logo"  class="preview" alt="" width="20" height="20" style="float: left;margin-left: 40%;"/>
											<input type="hidden" id="logo" name="logo" style="width:90%;border:0px;"  class=" fou "  
												/>
										</div>
										</td>
										<!-- 主题图片 -->
										<td align="center" bgcolor="#FFFFFF">
										<div class="tdiv">
											<img id="image"  class="preview" alt="" width="20" height="20" style="float: left;margin-left: 40%;"/>
											<input type="hidden" id="image" name="image" style="width:90%;border:0px;"  class=" fou "  
												/>
										</div>
										</td>										
									
									</tr> 								
								  <tr id="kelong" style="display:none;">									
										<!-- 层级 -->										
										<td  bgcolor="#FFFFFF" id="hierarchy" style="text-align: left;">
											<img src="<%=basePath%>/js/tree/codebase/imgs/plus5.gif"  class="showOrHideChildren treeTable"/><img src="<%=basePath%>/js/tree/codebase/imgs/leaf.gif" title="展开下级"  class="showOrHideChildren treeTable" /><span id="nodeName" class="showOrHideChildren treeTable"></span>
										</td>	
																
										<!-- LOGO -->
										<td align="center" bgcolor="#FFFFFF">
										<div class="tdiv">
											<img id="logo"  class="preview" alt="" width="20" height="20" style="float: left;margin-left: 40%;"/>
											<input type="hidden" id="logo" name="logo" style="width:90%;border:0px;"  class=" fou "  
												/>
										</div>
										</td>
										<!-- 主题图片 -->
										<td align="center" bgcolor="#FFFFFF">
										<div class="tdiv">
											<img id="image"  class="preview" alt="" width="20" height="20" style="float: left;margin-left: 40%;"/>
											<input type="hidden" id="image" name="image" style="width:90%;border:0px;"  class=" fou "  
												/>
										</div>
										</td>
										
									</tr>
									<tr style="display:none;"><td id="line"></td></tr>
								</table>
		</div>
		
				<div style="margin-top:10px;width: 1100px; margin-left: 250px;">
				
				备注：<input type="text" id="remark" name="productDesign.remark" class="input" style="width:96%;" value="${productDesign.remark}">
				<p>责任人：<input type="text" class="input" size="15" value="${staff.staffName}"/>&nbsp;&nbsp;&nbsp;包装日期：<input type="text" name="productDesign.PackagingDate" class="input" id="PackagingDate" value="<fmt:formatDate value="<%=new Date()%>" pattern="yyyy-MM-dd" />" size="20" >
				</p>
				</div>
		</div>
		<!--查看页面-简介-->
		<div class="jianjie" style="width: 100%;height: 500px;display:none;">
			<table style="margin:  auto auto; ">
				<tr>
					<td rowspan="15">
						<div id="tupiandiv" class="div tupian"
							style="margin-top: 50px; margin-left:50px;padding-right:50px;width: 400px; height:300px; border-bottom: 2px;margin-left: 20px">
							  <img 	
							   <s:if test="#goodsManage.logoPath!=null&& #goodsManage.logoPath!=''">
					      	      src="<%=basePath%>/${goodsManage.logoPath}"
					     			    </s:if>
									<s:else >
					      	    src="<%=basePath%>/images/zanwutup.png"
					      	      </s:else >
								style="width: 100%; height: 100%; border-bottom: 2px;"
							 id="dazhuti" onclick="chuan('zhuti')" />
						</div>
					</td>
				<td></td>
				</tr>
				
				<c:forEach items="${list1}" var="name">
				<tr>
					<td><span style="font-size: 17px">${name}</span> </td>
				</tr>
				</c:forEach>
			



			</table>

		</div>
		<!--查看页面-功能-->
		<div class="gongneng" style="width: 100%;height: 500px;display: none">
	  			<div style="width: 500px; height: 800px; margin:auto auto;">
	  			<jsp:include page="../../../../upload_files/wechatmenu/${goodsManage.wupgn}"  flush="true" />
				</div>
		</div>
		<!--查看页面-用途-->
		<div class="yongtu" style="width: 100%;height: 500px;display: none">
		   <div style="width: 500px; height: 800px; margin: auto auto; ">
		   
		   
		   <jsp:include page="../../../../upload_files/wechatmenu/${goodsManage.wupyt}"  flush="true" />
			</div>
		</div>
		<!--查看页面-厂家-->
		<div class="changjia" style="width: 100%;height: 500px;display: none;">
			<table width="1000px;"
				style="border: 0px solid red ;margin: auto auto;">
				<tr>
					<td></td>
					<td rowspan="8"><input type="image"
						  src="<%=basePath%>/images/ditu.jpg" />
					</td>
				</tr>
				<tr>
					<td><span style="text-align: right; font-size: 15px;">厂名:</span>
						<span style="text-align: right; font-size: 15px;">北京天太世统科技有限公司</span>
					</td>
				</tr>
				<tr>
					<td><span style="text-align: right; font-size: 15px;">厂家联系方式:</span>
						<span style="text-align: right; font-size: 15px;">010-123456789</span>
					</td>
				</tr>
				<tr>
					<td><span style="text-align: right; font-size: 15px;">厂家传真:</span>
						<span style="text-align: right; font-size: 15px;">010-64177113</span>
					</td>
				</tr>
				<tr>
					<td><span style="text-align: right; font-size: 15px;">网址:</span>
						<span style="text-align: right; font-size: 15px;"><a
							href="http://www.ttst201.com">www.ttst201.com</a> </span></td>
				</tr>
				<tr>
					<td><span style="text-align: right; font-size: 15px;">厂家地址:</span>
						<span style="text-align: right; font-size: 15px;">北京朝阳门外大街宇飞大厦801室</span>
					</td>
				</tr>
				<tr>
					<td><span style="text-align: right; font-size: 15px;">乘车路线:</span>
						<span style="text-align: right; font-size: 15px;">地铁2号线东直门站下车,C口出。出门直行300米(天恒大厦旁边)即到</span>
					</td>
				</tr>
				<tr>
					<td><span style="text-align: right; font-size: 15px;">附件公交线路:</span>
						<span style="text-align: right; font-size: 15px;">106电车,107电车.123路,等车到</span>
					</td>
				</tr>
			</table>
		</div>


	</div>


	<!-- 隐藏域 -->
	<div style="display:none">
	<!-- 
		<input type="file" name="goodsManage.fileLogo" id="tuplogo" /> <input
			type="file" name="goodsManage.filePhoto" id="tupphoto" /> <input
			type="file" name="goodsManage.fileship" id="tuppship" /> <input
			type="reset" id="qingchu" /> <a href="javascript:window.close();"
			id="closejsp">close</a>
	-->
	</div>
</body>
</html>
