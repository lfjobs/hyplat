<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
		<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
		%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>个人履历</title>
		<!-- 此页面在2014-12-18被humanResource.jsp替代。三个月后没有用到可以删除 -->
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"
			type="text/css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/common/common.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<SCRIPT type="text/javascript">
			var token = 0;
			var select = 1;
			var recordID='';
			var basePath='<%=basePath%>';
			var pbasePath='<%=basePath%>';
			var recordstaffID='${record.staffID}';
			var notoken = 0;
			var quzhi = '';
			var mainheught = 0; //框架高度
		    var ids = ''; //存放行ID
		   
		    function getValueForParm(id){ //打开页面
				ids = id;
			  	$("#ifr").attr("src",basePath+"ea/cstaff/ea_getListCStaffByCompanyID.jspa?flexbutton=flexbutton");
			  	mainheught = parent.document.getElementById("mainframe5").offsetHeight;
			  	parent.document.getElementById("mainframe5").style.height = 330 + 'px';
			  	$("#jqmWindow2").jqmShow();
		    }
			
			$(document).ready(function() {
				$("#isBack").click(function(){// 返回
			       $("#jqmWindow2").jqmHide();
			       parent.document.getElementById("mainframe5").style.height = mainheught + 'px';
			    }); 
			   
				$("#isSubmit").click(function(){// 选择确定
					var value1 = window.frames["ifr"].personvalue;//弹出框的页面必须声明opertionID这个参数接收id
					if(value1 == ""){
						alert("请选择")
						return;
					}
					var value2 = window.frames["ifr"].$('tr#'+value1).find("span#staffName").text();//弹出框的页面存在于span中才取得到
					var value3 = window.frames["ifr"].$('tr#'+value1).find("span#staffCode").text();//弹出框的页面存在于span中才取得到
					$("#"+ids).find("#reference").val(value2);
					$("#"+ids).find("#referenceCode").val(value3);
					$("#ifr").attr("src","");
					parent.document.getElementById("mainframe5").style.height = mainheught + 'px';
			        $("#jqmWindow2").jqmHide();
			    });
			});
		</SCRIPT>
		<script type="text/javascript"
			src="<%=basePath%>js/ea/human/cstaff/cstaff_precord.js"></script>
		<script src="<%=basePath%>js/ea/human/cstaff.js"></script>
	</head>
	<body>
		<form enctype="multipart/form-data" name="recordForm" method="post"
			id="recordForm">
			<input type="submit" name="submit" style="display: none" />
			<s:token></s:token>
			
			<div id="main_main" class="main_main">
				<table class="precord">
					<thead>
						<tr>
							<th width="30" align="center">
								选择
							</th>
							<th width="70" align="center">
								起始时间
							</th>
							<th width="70" align="center">
								截止时间
							</th>
							<th width="150" align="center">
								工作单位
							</th>
							<th width="100" align="center">
								岗位名称
							</th>
							<th width="70" align="center">
								岗位情况
							</th>
							<th width="70" align="center">
								职务
							</th>
							<th width="150" align="center">
								单位地址
							</th>
							<th width="200" align="center">
								工作内容及职责
							</th>
							<th width="100" align="center">
								文件号
							</th>
							<th width="80" align="center">
								审核人
							</th>
							<th width="110" align="center">
								审核人人员编号
							</th>
							<th width="70" align="center">
								审核时间
							</th>
							<th width="300" align="center">
								备注
							</th>
							<th width="60" align="center">
								附件
							</th>
						</tr>
					</thead>
					<!-- 隐 -->
					<tbody  id="tbwid">
						<tr id="sa" align="center" height="22" id="sa"
							style="display: none" class="td_bg01 saveAjax model2">
							<td>
								<input type="radio" name="a" class="JQuerypersonvalue" />
							</td>
							<td class="td_bg01">
								<input name="startTime" id="startTime" onfocus="date(this);"
									size="10" />
							</td>
							<td class="td_bg01">
								<input name="endTime" id="endTime" onfocus="date(this);"
									size="10" />
							</td>
							<td class="td_bg01">
								<input name="ccompanyID" id="ccompanyID" size="18" style="display: none;"/>
								<input name="companyName" id="companyName" size="18" readonly="readonly"/>
								<a href="#" id = "xzwlaw">选择</a>
							</td>
							<td class="td_bg01">
								<input name="postName" id="postName" size="20" />
								<!--岗位名称 -->
							</td>
							<td class="td_bg01">
								<s:select list="codePostSituationList" listKey="codeID"
									listValue="codeValue" name="postCase" id="xxx" theme="simple"></s:select>

							</td>
							<td class="td_bg01">
								<input name="position" id="position" size="18" />
							</td>
							<td class="td_bg01">
								<input name="postAddress" id="postAddress" size="18"
									maxlength="25" />
								<!--单位地址 -->
							</td>
							<td class="td_bg01">
								<input name="duties" id="duties" size="25" maxlength="25" />
							</td>
							<td class="td_bg01">
								<input name="fileNumber" id="fileNumber" size="5" />
								<!--文件号 -->
							</td>
							<td class="td_bg01">
								<input name="reference" id="reference" size="10" readonly="readonly"/>
								<a href="#" onclick="getValueForParm($(this).parent().parent().parent().attr('id'))">选择</a>
							</td>
							<td class="td_bg01">
								<input name="referenceCode" id="referenceCode" size="10" readonly="readonly"/>
								<!--审核人员编号 -->
							</td>
							<td class="td_bg01">
								<input name="referenceTime" id="referenceTime"
									onfocus="date(this);" size="10" />
								<!--审核时间 -->
							</td>
							<td class="td_bg01">
								<input name="referencePhon" id="referencePhon" size="25" />
								<!--备注 -->
								<input name="recordKey" id="recordKey" type="hidden" />
								<input type="hidden" name="recordID" id="recordID" />
								<input type="hidden" name="staffID" id="staffID"
									value="${record.staffID}" />
							</td>
							<td class="td_bg01">
								<input name="filePhoto" id="filePhoto" contentEditable="false"
									type="file" size="10" />
							</td>
						</tr>
						<!-- 隐 -->
						<s:iterator value="pRecordList">
							<tr align="center" height="22" class="td_bg01 saveAjax trclass"
								id="${recordID}">
								<td>
									<input type="radio" name="a" class="JQuerypersonvalue"
										value="${recordID}"/>
								</td>
								<td class="td_bg01">
									<SPAN id="startTime" class="datas">${ startTime}</SPAN>
									<input class="model1" value="${ startTime}" name="startTime"
										onfocus="date(this);" size="10" />
								</td>
								<td class="td_bg01">
									<SPAN id="endTime" class="datas">${ endTime}</SPAN>
									<input class="model1" value="${ endTime}" name="endTime"
										onfocus="date(this);" size="10" />
								</td>
								<td class="td_bg01">
									<SPAN id="companyName">${ companyName}</SPAN>
								<input class="model1" name="companyName" id="companyName" value="${companyName}" size="18" />
								<input name="ccompanyID" id="ccompanyID" size="18" style="display: none;" value="${ ccompanyID}" readonly="readonly"/>
								<a class="model1" href="#" id = "xzwla">选择</a>
								</td>
								<td class="td_bg01">
									<SPAN id="postName">${ postName}</SPAN>
									<input class="model1" name="postName" value="${ postName}"
										size="20" />
									<!--岗位名称 -->
								</td>
								<td class="td_bg01">
									<SPAN id="postCase"></SPAN>
									<s:select list="codePostSituationList" listKey="codeID"
										listValue="codeValue" name="postCase" id="postCase"
										class="model1" theme="simple"></s:select>
									<!--岗位情况 -->
								</td>
								<td class="td_bg01">
									<SPAN id="position">${ position}</SPAN>
									<input class="model1" name="position" value="${ position}"
										size="18" />
								</td>
								<td class="td_bg01">
									<SPAN id="postAddress">${ postAddress}</SPAN>
									<input class="model1" name="postAddress"
										value="${ postAddress}" size="18" />
									<!--单位地址 -->
								</td>
								<td class="td_bg01">
									<SPAN id="duties">${ duties}</SPAN>
									<input class="model1" name="duties" value="${ duties}"
										size="25" />
								</td>
								<td class="td_bg01">
									<SPAN id="fileNumber">${ fileNumber}</SPAN>
									<input class="model1" name="fileNumber" value="${ fileNumber}"
										size="5" />
									<!--文件号 -->
								</td>
								<td class="td_bg01">
									<SPAN id="reference">${ reference}</SPAN>
									<input class="model1" id="reference" name="reference" value="${ reference}"
										size="10" readonly="readonly"/>
									<a href="#" class="model1" onclick="getValueForParm($(this).parent().parent().parent().attr('id'))">选择</a>
								</td>
								<td class="td_bg01">
									<SPAN id="referenceCode">${ referenceCode}</SPAN>
									<input class="model1" id="referenceCode" name="referenceCode"
										value="${ referenceCode}" size="10" readonly="readonly"/>
									<!--审核人员编号 -->
								</td>
								<td class="td_bg01">
									<SPAN id="referenceTime" class="datas">${ referenceTime}</SPAN>
									<input class="model1" name="referenceTime"
										value="${ referenceTime}" onfocus="date(this);" size="10" />
									<!--审核时间 -->
								</td>
								<td class="td_bg01">
									<SPAN id="referencePhon">${ referencePhon}</SPAN>
									<input class="model1" name="referencePhon"
										value="${ referencePhon}" size="25" />
									<input name="recordKey" type="hidden" value="${ recordKey}" />
									<input name="recordID" type="hidden" value="${ recordID}" />
									<input type="hidden" name="staffID" id="staffID"
										value="${record.staffID}" />
								</td>
								<td class="td_bg01">
									<span><s:if test="photo==null||photo==''">无</s:if>
									</span>
									<s:else>
										<span id="photo" onclick="lookImage('${photo}');"><a
											href="#">查看</a>
										</span>
									</s:else>
									<input name="filePhoto" contentEditable="false" type="file"
										class="model1" size="10" />
									<input name="photo" type="hidden" value="${photo}"
										class="model1" />
								</td>
							</tr>
						</s:iterator>
					</tbody>
				</table>
			</div>
		</form>
		<%------------------------------------选择往来单位------------------------------------%>
		<form name="selectcompanyForm" id="selectcompanyForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<div class="jqmWindow jqmWindowcss1" style="top: 0%;left:53%"
				id="companyjqModel">
				<div class="content1" style="width: 94%; height: 380px;">
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
							<td width="45">
								<a id="dwsy" title="0">上一页</a>
							</td>
							<td width="45">
								<a id="dwxy" title="0">下一页</a>
							</td>
							<td width="50">
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
									style="margin-top: 2px; display: none; width: 100%; overflow: scroll; height: 200px;">
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<s:token></s:token>
		</form>
		<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
		
		<!-- 从当前部门的员工中选择责任人 -->
	    <div id="jqmWindow2" class="jqmWindow"
			style="width: 98%; height: 320px; absolute; display: none; left: 1%; top: 1%; background: #eff">
			<div align="center">
				<iframe name="ifr" id="ifr" width="100%" height="280px"
				frameborder="0"></iframe>
				<input type="button" class="input-button" id="isSubmit" value=" 确定 "
					style="cursor: hand" />
				<input type="button" class="input-button" id="isBack" value=" 关闭 "
					style="cursor: hand" />
			</div>
		 </div>
		
		<script type="text/javascript">
			$(function(){  
				setTimeout(function(){
			        $("div.bDiv").css({"height":parent.document.getElementById("mainframe5").offsetHeight-57+"px"});
			},100);
				 $(window).resize(function(){
					 setTimeout(function(){
					        $("div.bDiv").css({"height":parent.document.getElementById("mainframe5").offsetHeight-57+"px"});
				 },100);
				 }); 	
			});
		</script>
	</body>
</html>