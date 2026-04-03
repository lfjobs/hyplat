<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
		<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ page import="hy.ea.bo.Company"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
			Company c = (Company)session.getAttribute("currentcompany");
		%><html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="cache-control" content="no-cache" />
		<title>请假申请单</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
		
		<style type="text/css"> 
		.windowJqm{
		    left:55%;
		    width:850px;
		    margin-left:-450px;;	
		}
		.underline {
			text-decoration: underline;
		}
		.sty{
			padding-left:5px;
		}
		</style>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
			type="text/css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/admin_main.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/overlayer.css" />
		<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>

		<script type="text/javascript">
       		var treeID = "${department[0]}";
	      
	        var treePID = '<%=c.getCompanyID()%>';
	        var treePName = '<%=c.getCompanyName()%>';
       		var token = 0;
            var id = ""; 
            var basePath='<%=basePath%>';
            var ppageNumber='${pageNumber}';
            var search='${search}';
            var acceName = '';  //附件查看赋值
            var times = '0';
            var vouch = '';  //凭证号传值
            var type="${type}";
	        

     
		</script>
		<script type="text/javascript"
			src="<%=basePath%>js/ea/mysl/administrative/leaveApply.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
	</head>
	<body>
		<div class="main_main">
			<table class="JQueryflexme">
				<thead>
					<tr class="tablewith">
						<th width="40" align="center">
							请选择
						</th>
						<th width="170" align="center">
							公司
						</th>
						<th width="80" align="center">
							部门
						</th>
						<th width="160" align="center">
							凭证号
						</th>
						<th width="80" align="center">
							责任人
						</th>
						<th width="110" align="center">
							请假类别
						</th>
						<th width="80" align="center">
							申请日期
						</th>
						<th width="120" align="center">
							起日期
						</th>
						<th width="120" align="center">
							止日期
						</th>
						<th width="80" align="center">
							请假天数
						</th>
						<th width="80" align="center">
							请假小时
						</th>
						<th width="80" align="center">
							请假事由
						</th>
						<th width="80" align="center">
							工作接管人
						</th>
						<th width="80" align="center">
							职位
						</th>
						<th width="110" align="center">
						  制单日期
						</th>
						<th width="110" align="center">
							单据状态
						</th>
						<th width="110" align="center">
							附件
						</th>
					</tr>
				</thead>
				<tbody>
					<%
						int number = 1;
					%>
						<s:iterator value="pageForm.list">
						<tr id="${id}">
							<td>
								<input type="radio" name="a" class="JQuerypersonvalue"
									value="${id}" />
							</td>
							<td>
								<span id="companyname">${companyname}</span>
							</td>
							<td>
								<span id="organizationname">${organizationname}</span>
							</td>
							<td>
								<span id="serialnumber">${serialnumber}</span>
							</td>
							<td>
								<span id="staffname">${staffname}</span>
							</td>
							
                             <td>
								<span id="leaveType">${leaveType}</span>
							</td>

							<td>
								<span id="applyDate" >${applyDate}</span>
							</td>
							<td>
								<span id="leaveStartDate">${leaveStartDate}</span>
							</td>
							<td>
							   <span id="leaveEndDate">${leaveEndDate}</span>
								
							</td>
							<td>
								<span id="leaveDays">${leaveDays}</span>
							</td>
							<td>
								<span id="leaveHour">${leaveHour}</span>
							</td>
							<td>
								<span id="leaveReason">${leaveReason}</span>
							</td>
							<td>
								<span id="leaveReceiver">${leaveReceiver}</span>
						        <span id="leaveReceiverid" style="display:none;">${leaveReceiverid}</span>
								
							</td>
							<td>
								<span id="leavePostName">${leavePostName}</span>
							</td>
						
							<td>
								<span id="addtime">${fn:substring(addtime,0,19)}</span>
							</td>
						
						
						
					
							<td><span><s:if test="status=='01'">未审核</s:if> <s:elseif
									test="status=='02'">已审核</s:elseif> <s:elseif
									test="status=='03'">驳回</s:elseif> <s:elseif test="status=='04'">办理中</s:elseif>
								<s:elseif test="status=='05'">已办理</s:elseif> <s:else>草稿</s:else>
						</span>
						<span id="status" style="display:none;" >${status}</span>
						<span id="id" style="display:none;">${id}</span>
						<span id="key" style="display:none;">${key}</span>
						</td>
						<td >
                             	<span id="attachPath" style="display:none">${attachPath}</span>
                            	<s:if test="attachPath==null">无</s:if><s:else>
                            	
                            	<span id="look" onclick="lookImage('${attachPath}');"><a href="#">查看</a></span>
                             	<span id="load" ><a href='<%=basePath%>ea/leaveapply/ea_downFile.jspa?downLoadPath=${attachPath}'>下载</a></span>
                            	</s:else>
                            	
                         </td>
						</tr>
						<%
							number++;
						%>
					</s:iterator>
				</tbody>
			</table>
			<c:import url="../../ea/page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/leaveapply/ea_getListByLeave.jspa?pageNumber=${pageNumber}&search=${search}&type=${type}">
				</c:param>
			</c:import>
		</div>
		<!--搜索窗口 -->
		<div class="jqmWindow  jqmWindow1"
			style="width: 430px; right: 25%;; top: 10%; z-index: 10000"
			id="jqModelSearch">
			<form name="postSearchForm" id="postSearchForm" method="post">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table width="396" id="cataffSearchTable">
					<tr>
	                  	<td align="right">
	                           凭证号：                      
	                  	</td>
	                  	<td>
	                  		<input name="dtMyleave.serialnumber" />
	                  	</td>
	                </tr><%--
					<tr>
						<td align="right">
							请假责任人：
						</td>
						<td>
							<input name="dtMyleave.staffname" />
						</td>
					</tr>
					--%><tr>
					<td align="right">制单时间：</td>
					<td><input name="dtMyleave.addtime"
						onfocus="date()" />
					</td>
				</tr>
				 <tr>
					<td align="right">单据状态：</td>
					<td><select name="dtMyleave.status">
							<option value="">全部</option>
							<option value="01">未审核</option>
							<option value="02">已审核</option>
							<option value="03">驳回</option>
							<option value="04">办理中</option>
							<option value="05">已办理</option>
							<option value="00">草稿</option>
					</select>
					</td>
				</tr>
					
				</table>
				<div align="center">
					<input type="button" class="input-button" id="tosearch"
						value=" 查询 " />
					<input name="search" type="hidden" value="search" />
					<input name="type" type="hidden" value="${type}" />
				</div>
			</form>
		</div>

		<!-- ADD -->
		<div class="contentbannb jqmWindow windowJqm" style="top: 5%"
			id="jqModel">
			<form name="leaveForm" id="leaveForm" method="post"
				enctype="multipart/form-data">
				<input type="submit" name="submit" style="display: none" />
				<div class="content">
					<div class="contentbannb">
						<div class="drag">
							员工请假单
							<div class="close"></div>
						</div>
					</div>
					
				  <table width="850 " height="50" border="0" align="center" cellpadding="0" cellspacing="0" style="margin-top:5px; margin-bottom:5px;">
				   		<tr>
						<td width="80" align="right">公司：</td>
						<td width="220"><input name="dtMyleave.companyname"
							class="input yincang" id="companyName" readonly="readonly"
							value="<%=c.getCompanyName()%>" size="30" /> <span class="xianshi"
							id="companyName1"></span>
							<input type="hidden" name="dtMyleave.companyid"
							class="input yincang" id="companyid" readonly="readonly"
							value="<%=c.getCompanyID()%>" size="30" />
							</td>
						<td align="right"><span class="xx">*</span>部门：</td>
						<td align="left"><input
							name="dtMyleave.organizationname"
							id="organizationname" readonly="readonly"
							value="${department[1]}" /> <input type="hidden"
							name="dtMyleave.organizationid" id="organizationid"
							readonly="readonly" value="${department[0]}" />
						</td>
						<td align="left">附件：<input name="dtMyleave.attachPath"
							class="fileNum" type="hidden" id="attachPath" size="15" value="${attachPath}" /> 
							<input name="photo" type="file" id="accessoryName" class="input yincang"
							size="20" contentEditable="false" /> </td>
						
					</tr>
					<tr>
						<td align="right">凭证号：</td>
						<td><input readonly="readonly"
							name="dtMyleave.serialnumber" id="serialnumber" class="yincang"
							size="25" /> <span id="voucherNum" class="xianshi"></span></td>
						<td align="right"><span class="xx">*</span>责任人：</td>
						<td align="left"><input
							name="dtMyleave.staffname" id="staffname"
							readonly="readonly" value="${staff.staffName}" /> <input
							type="hidden" id="staffid" name="dtMyleave.staffid"
							readonly="readonly" value="${staff.staffID}" />
						</td>
						<td align="left">岗位： <input
							name="dtMyleave.leavePostName" class="yincang"
							id="leavePostName" readonly="readonly" value="${department[2]}" size="12" /> <span
							class="xianshi" id="leavePostName"></span></td>

					</tr>
				   </table>
				   <table width="850" height="210" border="0" align="center"
						cellpadding="0" cellspacing="0" class="table"
						style="background: #FFFFFF;">
						<tr>
							<td width="120" height="34" align="right"><span class="xx">*</span>申请日期：</td>
							<td width="250">
								<input name="dtMyleave.applyDate" onfocus="date(this);" class="input put3 yincang" id="applyDate" size="20" style="margin-left: 2px;" readonly="readonly" />
								<span id="addtime" class="xianshi sty"></span>
							</td>
							<td width="100" align="right">
								接管人：
							</td>
							<td width="375">
								<input name="dtMyleave.leaveReceiver" class="input yincang leaveReceiver put3"  id="leaveReceiver" size="15" style="margin-left: 2px;" readonly="readonly" />
								<input name="dtMyleave.leaveReceiverid"  id="leaveReceiverid" class="leaveReceiverid"  type="hidden" />
								<a href="#" class="yincang" onclick="importGY();">选择</a>
								<span id="leaveReceiver" class="xianshi sty"></span>
							</td>
						<tr>
							<td width="120" height="34" align="right">
								<span class="xx">*</span>请假日期：
							</td>
							<td colspan="3" class="errortime">
								<input name="dtMyleave.leaveStartDate"
									onfocus="daytime(this);" class="input yincang leaveStartDate put3"  id="leaveStartDate"
									size="20" style="margin-left: 2px;" readonly="readonly" />
								<span id="leaveStartDate1" class="underline xianshi"></span>
								至
								<input name="dtMyleave.leaveEndDate"
									onfocus="daytime(this);" class="input yincang leaveEndDate put3"  id="leaveEndDate"
									size="20" style="margin-left: 2px;" readonly="readonly" />
							
								计
								<input name="dtMyleave.leaveDays" class="input yincang"
									id="leaveDays" size="5" style="margin-left: 2px;" readonly="readonly"/>
								
								天
								<input name="dtMyleave.leaveHour" class="input yincang" 
									id="leaveHour" size="5" style="margin-left: 2px;" readonly="readonly"/>
								
								小时
							</td>


						</tr>
						<tr>
							<td width="120" height="34" align="right">
								<span class="xx">*</span>请假类别：
							</td>
							<td colspan="3" id="leaveTypes">
								<input type="radio" name="dtMyleave.leaveType" class="yincang" value="事假" checked/>
								<font class="yincang">事假</font>
								<input type="radio" name="dtMyleave.leaveType" class="yincang" value="病假"/>
								<font class="yincang">病假</font>
								<input type="radio" name="dtMyleave.leaveType" class="yincang" value="婚假"/>
								<font class="yincang">婚假</font>
								<input type="radio" name="dtMyleave.leaveType" class="yincang" value="产假"/>
								<font class="yincang">产假</font>
								<input type="radio" name="dtMyleave.leaveType" class="yincang" value="丧假"/>
								<font class="yincang">丧假</font>
								<input type="radio" name="dtMyleave.leaveType" class="yincang" value="年休假"/>
								<font class="yincang">年休假</font>
								<input type="radio" name="dtMyleave.leaveType" class="yincang" value="探亲假"/>
								<font class="yincang">探亲假</font>
								<input type="radio" name="dtMyleave.leaveType" class="yincang" value="其他"/>
								<font class="yincang">其他</font>
								<span id="leaveType" class="xianshi sty"></span>
							</td>
						</tr>
					
						<tr>
							<td width="120" align="right">
								<span class="xx">*</span>请假原因：
							</td>
							<td colspan="3">
								<textarea name="dtMyleave.leaveReason" cols="88" 
									rows="4" class="input yincang ckTextLength put3" maxlength="250" 
									id="leaveReason" style="margin-left: 2px;"></textarea>
								
							</td>
						</tr>
						
					</table>
				  <table width="870" height="40" border="0" align="center"
					cellpadding="0" cellspacing="0"
					style="margin-top:5px; margin-bottom:5px;">
					<tr>
						<td colspan="10" align="center"><input type="hidden"
							name="dtMyleave" id="id" /> <input type="hidden"
							name="dtMyleave.key" id="key" /> 
							<input type="button" class="input-button JQuerySave"
							style="cursor:pointer;width:80px;" value="保存草稿" title="0"/>
							<input type="button" class="input-button JQuerySubmit"
							style="cursor:pointer;width:80px;" value="提交审核" title="1"/> <input
							type="button" class="input-button JQueryreturn"
							style="cursor:pointer;width:80px;" value="返回" />
							<input type="hidden" id="buttonType" name="buttonType"/>
						</td>
					</tr>
				</table>
				</div>
				<s:token></s:token>
			</form>
			
			
		</div>
	
			
			
			
			<!-- 从当前部门的员工中选择责任人 -->
			<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
			<div id="jqmWindow2" class="jqmWindow"
				style="width: 80%; height: 380px; absolute; display: none; left: 1%; top: 1%; background: #eff">
				<div style="background: #efg; margin-right: 500px;">
					<input style="display: none;" id="myform" />
					<input style="display: none;" id="parm" />
					<input style="display: none;" id="parmNum" />
					<input style="display: none;" id="idNum" />
				</div>
				<iframe name="ifr" id="ifr" width="100%" height="335px"
					frameborder="0"></iframe>
				<div align="center"> 
						<input type="button" class="input-button" onclick="DaoruConfirm()"
						value=" 确定 " style="cursor: hand" />
						<input type="button" class="input-button" id="isBack" value=" 关闭 "
						style="cursor: hand" />
				</div>
			</div>
			
			
			
			
					
			<!--选择审核人员窗口 -->
	<div class="jqmWindow"
		style="display: none; width: 450px; height: 250px; right: 20%; top: 10%;"
		id="jqModelSend">
		<div class="drag">报送审批</div>
		<center>
		<table width="100%" id="SearchTable2" cellspacing="20"
			cellpadding="20">
			<tr>
				<td align="right">审核人公司：</td>
				<td align="left"><select id="receiverCompanyID"
					name="document.receiverCompanyID" onchange="changeCompany(this);"
					style="width:200px;">
						<option value="">请选择审核人公司</option>
				</select>
				</td>
			</tr>
			<tr>
				<td align="right">审核人部门：</td>
				<td align="left"><select id="receiverDeptID"
					name="document.receiverDeptID" onchange="changeDept(this);"
					style="width: 200px;">
						<option value="">请选择审核人部门</option>
				</select>
				</td>
			</tr>
			<tr>
				<td align="right">审核人姓名：</td>
				<td align="left"><select name="document.receiverID"
					id='receiverID' style="width: 200px;">
						<option value="">请选择审核人</option>
				</select>
				</td>
			</tr>
		</table>

		<div align="center" style="margin-top: 25px;">
			<input type="button" class="input-button" id="submitResult"
				value=" 提交 " /> <input type="button" class="input-button"
				id="submitColsed" value=" 关闭 " />
		</div>
		</center>
	</div>
			
			
	</body>
</html>