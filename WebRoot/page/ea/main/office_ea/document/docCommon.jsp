<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<script src="<%=basePath%>js/jquery-1.8.3.min.js" type="text/javascript"></script>

		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />

		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
		<script src="<%=basePath%>js/ea/office_ea/document/DocCommon.js?version=20200220"></script>
		<title>公共公文</title>


		<script type="text/javascript">
         var basePath='<%=basePath%>';
         var  pNumber =${pageNumber};  
         var  search='${search}';
         var docId = "";
         var module ='<%=session.getAttribute("module")%>'; 
         var searchType = '${searchType}';
  
         </script>
		<!-- 跟踪层 -->
		<style type="text/css">
.rc_box1,.rc_box2,.rc_box3 {
	display: inline-block; *
	display: inline; *
	zoom: 1;
	position: relative;
	border-style: solid;
	border-color: #ddd;
}

.rc_box2,.rc_box3 {
	border-width: 0 1px; *
	left: -2px;
	background-color: #f3f3f3;
}

.rc_box1 {
	border-width: 1px;
	line-height: 1.5;
	display: none;
	z-index: 999999;
}

.rc_box2 {
	margin: 0 -2px;
}

.rc_box3 {
	margin: 1px -2px;
	padding: 0 6px;
	width: 400px;
	height: 250px;
	font-size: 12px;
}

.rc_box4 {
	background-color: #FFFFFF;
	height: 215px;
	width: 380px;
	margin: 10px;
	margin-left: 6px;
	padding-top: 5px;
	overflow: auto;
}

.lov1,.lov2 {
	position: absolute;
	top: 15%;
	overflow: hidden;
	width: 0;
	height: 0;
	border-top: 12px dotted transparent;
	border-bottom: 12px dotted transparent;
	border-left: 11px solid transparent;
}

.lov1 {
	left: 410px;
	border-left-color: #ddd;
	z-index: 99999;
}

.lov2 {
	left: 409px;
	border-left-color: #f3f3f3;
	z-index: 99999;
}

.closer {
	background: url(<%=basePath%>images/ea/office/document/close16.png);
	width:16px;
	height:16px;
	border:none;
	cursor:hand;
}

.td-ts{
	position: relative;
	overflow: visible;
}
.td-ts ul{
	display: none;
 	position: absolute;
	background: #fff;
	left: 1%;
	top: 20px;
	width: 260px;
	list-style:none;
	z-index: 9999;
}
.td-ts ul li{
	list-style:none;
	height: 20px;
	line-height: 20px;
}

</style>

	</head>
	<body>

		<!--转移位置 -->
		<form name="positionForm" id="positionForm" method="post">
			<div class="jqmWindow"
				style="display: none; width: 350px; height: 200px; right: 15%; top: 10%;"
				id="jqModelPosition">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					选择位置
					<div class="close">
					</div>
				</div>
				<center>
					<table width="100%" id="positiontbl" cellspacing="20" cellpadding="20">
						<tr>
							<td align="right">
								所属部门：
							</td>
							<td align="left">
								<select id="organizationID" name="document.organizationID"
									style="width: 160px;">
									<option value="">

										请选择部门
									</option>
								</select>

							</td>
						</tr>
						<tr>
							<td align="right">
								所属位置：
							</td>
							<td align="left">
								<select id="position" name="document.module"
									style="width: 160px;">
									<option value="">
										请选择位置
									</option>
									<option value="doc">
										公文流转单
									</option>
									<option value="contract">
										企业合同管理
									</option>
									<option value="cg">
										公司规划管理
									</option>
									<option value="dg">
										部门规划管理
									</option>
									<option value="pg">
										个人规划管理
									</option>
									<option value="jg">
										职业规划管理
									</option>
									<option value="CountReg">
										国家法定管理
									</option>
									<option value="InduReg">
										行业法规管理
									</option>
									<option value="AnnNoti">
										公告通知管理
									</option>
									<option value="complaint">
										投诉处理
									</option>

									<option value="regime">
										制度管理
									</option>

									<option value="news">
										新闻管理
									</option>
									<option value="InterDis">
										内部纠纷
									</option>
									<option value="ExterDis">
										外部纠纷
									</option>
									<option value="bulletin">
										简报管理
									</option>
									<option value="MeetRecord">
										会议记录管理
									</option>
								</select>

							</td>
						</tr>
					</table>

					<div align="center" style="margin-top: 25px;">
						<input type="button" onclick="changePosition();"
							class="input-button" id="change" value="   转移  " />
						<input type="hidden" value="" id="changeid" name="document.docId" />
						<input type="hidden" value="" name="type1" id="type1" />
					</div>
				</center>
			</div>
		</form>


		<form id="searchForm" name="searchForm">
			<div class="jqmWindow"
				style="display: none; width: 450px; right: 25%; top: 10%"
				id="jqModelSearch">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					查询
					<div class="close">
					</div>
				</div>
				<center>
					<table cellspacing="5">
						<tr>
							<td align="right">
								公文编号：
							</td>
							<td align="left">
								<input type="text" name="documentSearchInfo.docNum"
									style="width: 180px;" />

							</td>
						</tr>
						<tr>
							<td align="right">
								公文标题：
							</td>
							<td align="left">
								<select id="titleselect" style="width: 185px;"
									name="documentSearchInfo.title" class="selectInput">
									<option value="">
										请选择标题
									</option>
								</select>
								<input type="text" id="titleinput"
									style="width: 180px; display: none;" class="menual" />
								<input type="button" onclick="menualInput();"
									class="selectInput input-button" value="  手动输入  " />
								<input type="button" style="display: none;"
									onclick="selectInput();" class="menual input-button"
									value="  选择输入  " />
							</td>
						</tr>
						<tr class="docTypeQuery">
							<td align="right">
								选择公文类别：
							</td>
							<td align="left">

								<select id="docTypess" style="width: 185px;"
									name="documentSearchInfo.docType">
									<option value="">
										请选择公文类型
									</option>
									<option value="aa">
										董事会会议决定文件
									</option>
									<option value="bb">
										董事长办公室文件
									</option>
									<option value="cc">
										总裁办公室文件
									</option>
									<option value="dd">
										总部人事处文件
									</option>
									<option value="ee">
										总部办公室文件
									</option>
									<option value="ff">
										总部财务处文件
									</option>
									<option value="gg">
										总部教务(生产)处文件
									</option>
									<option value="hh">
										总部营销处文件
									</option>
									<option value="ii">
										总部服务(创收)平台
									</option>
									<option value="jj">
										总部教务部文件
									</option>

								</select>
							</td>
						</tr>
						<tr class="statusQuery">
							<td align="right">
								选择公文状态：
							</td>
							<td align="left">

								<select id="docTypess" style="width: 185px;"
									name="documentSearchInfo.status">
									<option value="">
										请选择公文类型
									</option>
									<option value="S">
										审批中
									</option>
									<option value="A">
										盖章中
									</option>
									<option value="U">
										不批准
									</option>
									<option value="R">
										返回修改
									</option>
									<option value="F">
										盖章人存档
									</option>
									<option value="F">
										待群发
									</option>
									<option value="O">
										已群发
									</option>
									<option value="G">
										已归档
									</option>
									<option value="Z">
										传至信息平台
									</option>
								</select>
							</td>
						</tr>
						<tr class="fromMemberQuery">
							<td align="right">
								发件人：
							</td>
							<td align="left">
								<input type="text" name="fromMember" style="width: 180px;"
									value="" id="socialName" readonly />
								<input type="hidden" name="documentSearchInfo.fromMember"
									style="width: 150px;" value="" id="socials" />
								<img src="<%=basePath%>images/r_8_12.gif"
									onclick="importGY('ea/documentcommon/ea_getSocialInfoList.jspa')"
									style="cursor: hand;" />
							</td>
						</tr>
						<tr class="deleteQuery">
							<td align="right">
								删除位置：
							</td>
							<td align="left">

								<select id="docTypess" style="width: 185px;"
									name="documentSearchInfo.delposition">
									<option value="">
										请选择删除位置
									</option>
									<option value="draft">
										个人拟稿箱
									</option>
									<option value="cg">
										草稿箱
									</option>
									<option value="reject">
										驳回列表
									</option>
									<option value="yessend">
										已发送
									</option>
									<option value="examine">
										已审批
									</option>
									<option value="seal">
										已盖章
									</option>
									<option value="publish">
										已分发
									</option>
									<option value="read">
										已阅读
									</option>
									<option value="guidang">
										归档
									</option>
									<option value="share">
										共享池
									</option>
								</select>
							</td>
						</tr>
						<tr class="timeQuery">
							<td align="right">
								起时间：
							</td>
							<td align="left">
								<input type="text" name="documentSearchInfo.sStart"
									id="startDate"
									onFocus="WdatePicker({lang:'zh-cn', dateFmt:'yyyy-MM-dd HH:mm:ss', onpicked:function(){endDate.focus();}})"
									readonly value="${fn:substring(document.startValidity, 0, 10)}"
									style="width: 180px;" />

							</td>
						</tr>
						<tr class="timeQuery">
							<td align="right">
								止时间：
							</td>
							<td align="left">
								<input type="text" name="documentSearchInfo.sEnd" id="endDate"
									onFocus="WdatePicker({lang:'zh-cn', dateFmt:'yyyy-MM-dd HH:mm:ss', minDate:'#F{$dp.$D(\'startDate\')}'})"
									readonly value="${fn:substring(document.endValidity, 0, 10)}"
									style="width: 180px;" />

							</td>
						</tr>
						<tr>

							<td colspan="2" align="center">
								<input type="button" value="   查询   " class="input-button"
									id="tosearch" />
								<input name="search" type="hidden" value="search" />
								<input name="searchType" type="hidden" value="" id="searchType" />
								<input name="finishType" type="hidden" value="" id="finishType" />

							</td>
						</tr>
					</table>
				</center>
			</div>
		</form>

		<form name="SendForm" id="SendForm" method="post">
			<div class="jqmWindow"
				style="display: none; width: 450px; height: 300px; right: 20%; top: 10%;"
				id="jqModelSend">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					<div id="titlem"></div>
					<div class="close"></div>
				</div>
				<center>
					<div style="margin-top:10px;">
						<label><input type="radio" name="to" value="in" checked class="source" style="width:13px" />集团内部</label>
						<label><input type="radio" name="to" value="out"  class="source" style="width:13px"/>外部公司</label>
					</div>

					<table width="100%" id="SearchTable2" cellspacing="20" 
						cellpadding="20">
						<tr>
							<td align="right">
								收件人公司：
							</td>
							<td align="left">
								<select id="receiverCompanyID" name="document.receiverCompanyID"
									onchange="changeCompany(this);" style="width:200px;">
									<option value="">

										请选择收件人公司
									</option>
								</select>

							</td>
						</tr>
						<tr>
							<td align="right">
								收件人部门：
							</td>
							<td align="left">
								<select id="receiverDeptID" name="document.receiverDeptID"
									onchange="changeDept(this);" style="width: 200px;">
									<option value="">

										请选择收件人部门
									</option>
								</select>
							</td>
						</tr>
						<tr>
							<td align="right">
								收件人姓名：
							</td>
							<td align="left">
								<select name="document.receiverID" id='receiverID'
									style="width: 200px;">
									<option value="">

										请选择收件人
									</option>
								</select>
							</td>
						</tr>
					</table>


					<table width="100%" id="SearchTable" cellspacing="20" style="display:none;"
						   cellpadding="20">
						<tr>
							<td align="right">
								收件人公司：
							</td>
							<td align="left" class="td-ts">
								<input type="text" autocomplete="off" value=""   id="tscomid" style="width:200px"/>
								<input type="hidden"  value=""  name="document.receiverDeptID" id="deptid"/>
								<input type="hidden"  value=""  name="document.receiverCompanyID" id="comid" />
								<input type="hidden"  value=""  name="document.receiverID"   id="receiverid" />
								<ul class="ul1">
									<li>01</li>
									<li>02</li>
									<li>03</li>
								</ul>
							</td>
						</tr>
						<tr>
							<td align="right">
								收件人姓名：
							</td>
							<td align="left" class="td-ts">
								<input type="text" autocomplete="off"   id="tsreceiverID" style="width:200px"/>
								<ul class="ul2">
									<li>01</li>
									<li>02</li>
									<li>03</li>
								</ul>
							</td>
						</tr>
					</table>

					<div align="center" style="margin-top: 20px;">
						<input type="button" class="input-button" id="submitResult"
							value=" 提交 " />
						<input type="button" class="input-button close"
							value=" 关闭 " />
						<input type="hidden" id="submitType" value="" name="submitType"/>
						<input type="hidden" id="docId" name="document.docId" value="" />
						<input type="hidden" id="jump" name="jump" value="" />
						<input type="hidden" name="comment" id="comment" value="" />
					</div>
				</center>
			</div>
		</form>

		<form name="recycleForm" id="recycleForm">
			<div class="jqmWindow" id="jqModelrecycle">
				<input type="submit" name="submit" style="display: none" />
				<div>
					<input type="hidden" name="docIds" value="" id="docIdg" />
					<input type="hidden" name="stage" value="" id="stage" />
				</div>

			</div>
		</form>




		<!-- 跟踪层 -->
		<div class="rc_box1">
			<div class="rc_box2">
				<div class="rc_box3">
					<div style="float: right; margin-top: 2px;">
						<input type="button" value="" class="closer" onclick="hideTrackDiv();"/>
					</div>
					<div class="rc_box4">
						<center>
							<table id="trackdiv" cellpadding="0" cellspacing="0" width="100%">

							</table>
						</center>
					</div>
				</div>
			</div>
			<div class="lov1"></div>
			<div class="lov2"></div>
		</div>
	</body>
</html>

