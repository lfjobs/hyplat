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
		<meta http-equiv="cache-control" content="no-cache" />
		<title>酒店管理</title>
		<style type="text/css">
html {
	overflow: hidden;
}
</style>

		<script type="text/javascript" src="<%=basePath%>/js/common/common.js"></script>
		
		<script src="<%=basePath%>/js/jquery-1.3.1.js" type="text/javascript"></script>
		<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"
			type="text/css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>/js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>/js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript" src="<%=basePath%>/js/flexigrid.js"></script>
		<script src="<%=basePath%>/js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/jqModal/jqDnR.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>/js/jqModal/jqModal.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>/js/common/organizationTree.js"></script>
		<link href="<%=basePath%>/css/ea/validate.css" rel="stylesheet" type="text/css" />
		<script src="<%=basePath%>/js/ea/validate.js" type="text/javascript"></script>
		<script type="text/javascript">
   var token = 0;
   var select = 1;
   var basePath = '<%=basePath%>/';
   var pNumber = ${pageNumber};
   var notoken = 0;
   var quzhi = "";
   var edit = '${edit}';
 	</script>
		<script src="<%=basePath%>/js/ea/finance/company/accommod_edit.js"></script>
	</head>
	
	<body style="background-image:url(<%=basePath%>/images/bg01.jpg)">
		<div align="center">
			<form name="accommodForm" id="accommodForm" method="post">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag" align="left">
					酒店管理
				</div>
				<table id="cataffSearchTable" width="700px" align="center" border="0" cellpadding="3px" cellspacing="3px">
					<tr align="center">
						<td align="center">
						<font color="red">*</font>
							酒店名称：
						</td>
						<td align="left">
						
							<input name="accommod.hotelName" id="contactid" size="10" type="hidden"/>
							<input id="hotelName" style="width: 120px" readonly ="readonly"  class=" put3"/>
							<a href="#" id ="xzwlaw">选择</a>
						</td>
					
						<td>
						<font color="red">*</font>
							楼层：
						</td>
						<td id="tdu" align="left">
							
							<input id ="floor" name="accommod.floor" class="isNaN put3" style="text-align:right;width: 80px;"/>
						</td>
					</tr>
					<tr align="center">
						<td>
							星级：
						</td>
						<td  align="left">
							<s:select list="starsList" listKey="codeID"
								id="addstart" listValue="codeValue" 
								 name="accommod.stars" theme="simple" style="width: 155px"></s:select>
						</td>
					
						<td>
							房间类别：
						</td>
						<td  align="left"> 
							<s:select list="roomTypeList" listKey="codeID"
								id="addRoomtype" listValue="codeValue"
								name="accommod.roomType" theme="simple" style="width: 155px"></s:select>
						</td>
					</tr>
					
					<tr>
						<td><font color="red">*</font>
							标价(RMB)：
						</td>
						<td align="left">
							
							<input id ="roomPrice" name="accommod.roomPrice" class="isNaN put3" style="width: 80px;text-align:right;"/>
						</td>
						<td><font color="red">*</font>
							床&nbsp;位&nbsp;&nbsp;总&nbsp;数：
						</td>
						<td align="left">
						
							<input name="accommod.bedNum" id="bedNum" class="positiveNum  put3" style="width: 80px;text-align:right;"/>
						</td>
					</tr>
					<tr>
						<td>
							折扣价(RMB)：
						</td>
						<td align="left">
							<input name="accommod.roomDisPrice" id ="roomDisPrice" class="isNaN"  style="width: 80px;text-align:right;"/>
						</td>
						<td>
							入&nbsp;住&nbsp;&nbsp;床&nbsp;位：
						</td>
						<td align="left">
						<input name="accommod.bedOccNum" id="bedOccNum" class="positiveNum" style="width: 80px;text-align:right;" />
						</td>
					</tr>
					<tr>
						<td>
							协议价(RMB)：
						</td>
						<td align="left">
							<input name="accommod.roomAgrPrice" id ="roomAgrPrice" style="width: 80px;text-align:right;"  readonly ="readonly"/>
						</td>
						<td>
							未&nbsp;住&nbsp;&nbsp;空&nbsp;位：
						</td>
						<td align="left">
						<input name="" id="wz" readonly ="readonly" style="width: 80px;text-align:right;" />
						</td>
					</tr>
					<tr align="center">
						<td>
							房间号：
						</td>
						<td  align="left">
							<input id="createNum" style="width: 45px" class="isNaN"/>
							<input type="button" value="增加" id="addNum"/>
							<input type="button" value="删除" id="delNum"/>
						</td>
					</tr>
					<tr>
						<td colSpan="4" style="padding: 1em 30px 0em 40px;">
							<div id ="alldiv" style="padding:2px;border: #000 solid 2px; width =140px; height: 100px;">
								<div style="display:none;margin:2px ;float: left; border: #000 solid 2px;" id="d000">
									<input type="radio" id="r" class="radio"/>
									<input id="t"type="text" disabled="disabled" style="width: 30px" value="000"/>
								</div>
							</div>
						</td>
					</tr>
					
					<tr align="center">
						<td>
							备注：
						</td>
						<td  colSpan="3" align="left">
							<textarea rows="3" cols="50" id = "remarks" name = "accommod.remarks"></textarea>
							<input name="accommod.accommodKey" id="accommodKey" type="hidden"/>
							<input name="accommod.accommodID" id="accommodID" type="hidden"/>
							<input name="accommod.companyID" id="companyID" type="hidden"/>
							<input name="accommod.organizationID" id="organizationID" type="hidden"/>
							<input name="accommod.createName" id="createName" type="hidden"/>
							<input name="accommod.createDate" id="createDate" type="hidden"/>
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="addh" value=" 保存 " />
					<input type="button" class="input-button" id="reth" value=" 返回 " />
					<input name="search" type="hidden" value="search" />
				</div>
			</form>
		</div>


		<iframe name="hidden" width="100%" height="0"></iframe>
		
		
		<%------------------------------------选择往来单位------------------------------------%>
		<form name="selectcompanyForm" id="selectcompanyForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<div class="jqmWindow jqmWindowcss1" style="top: 5%;"
				id="companyjqModel">
				<div class="content1" style="width: 100%; height: 400px;">
					<div class="contentbannb">
						<div class="drag">
							选择往来单位
						</div>
					</div>
					<table width="99%" height="33" id="searchcompany" border="0"
						align="center" cellpadding="0" cellspacing="0"
						style="margin-top: 5px; background: #FFFFFF;">
						<tr>
							<td width="70" align="right">
								单位名称：
							</td>
							<td width="60">
								<input name="ccompanyID" class="input" id="ccompanyID" size="10"
									style="margin-left: 2px;" />
							</td>
							<td width="70" align="right">
								往来关系：
							</td>
							<td width="85">
								<s:select list="connectionlist" listKey="codeValue"
									id="contactConnections" listValue="codeValue" headerKey=""
									headerValue="--全部--" name="contactConnections" theme="simple"></s:select>
							</td>
							<td height="33">
								<input type="button" class="btn02" id="searchcc" name="button7"
									value="查询" />
								<input type="button" class="btn02" id="qdcompany" name="button5"
									value="确定" />
								<input type="button" class="btn02 xzdw" name="button" value="新增" />
								<input type="button" class="btn02 JQueryreturns" name="button4"
									value="关闭" />
							</td>
							<td width="50">
								<a id="dwsy" title="0">上一页</a>
							</td>
							<td width="50">
								<a id="dwxy" title="0">下一页</a>
							</td>
							<td width="70">
								<a id="dwzy">共&nbsp;&nbsp; <span style="color: red"
									id="zycount"></span>&nbsp;&nbsp; 页</a>
							</td>
						</tr>
					</table>
					<table width="99%" border="0" align="center" cellpadding="0"
						cellspacing="0"
						style="margin-top: 5px; margin-bottom: 5px; height: 450px;">
						<tr>
							<td width="99%" valign="top" align="left">
								<div id="body_02cc"
									style="margin-top: 2px; display: none; width: 100%; overflow: scroll; height: 330px;">
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<s:token></s:token>
		</form>
		
	</body>
</html>
