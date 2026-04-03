<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
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
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>科一收集</title>
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

		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
			type="text/css" />
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/ea/driving/driving_list.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/common/common.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/ea/ccodes_add.js"></script>
		<script type="text/javascript">
    var basePath = '<%=basePath%>';
	var pNumber=${pageNumber};
	var token=0;
	var drivingprincipalid="";
	var treeID = '<%=session.getAttribute("organizationID")%>';	
	var dateYuJing='${dateYuJing}';	
	var docstatus='${docstatus}';//单据状态
	var studentstatus='${studentstatus}';//学员状态
	var title='${title}';
	var search='${search}';
	var conditions='${conditions}';
	var staffID='';
	var istrues='';//是否合格
	var reason='';
	
	var extensionStaffCoach='${extensionStaffCoach}';//新版学员报名标识符
	var studentID='${dtDrivingPrincipal.studentid}';//新版报名学员ID
</script>
	</head>
	<body>
		<div class="main_main">
			<table class="JQueryflexme">
				<thead>
					<tr class="tablewith">
						<th width="40" align="center">
							请选择
						</th>
						<th width="50" align="center">
							序号
						</th>
						<th width="170" align="center">
							公司
						</th>
						<th width="70" align="center">
							部门
						</th>
						<th width="50" align="center">
							责任人
						</th>
						<th width="60" align="center">
							报名时间
						</th>
						<th width="50" align="center">
							车型
						</th>
						<th width="50" align="center">
							学员姓名
						</th>
						<th width="50" align="center">
							性别
						</th>
						<th width="70" align="center">
							电话
						</th>
						<th width="120" align="center">
							身份证号
						</th>

						<th width="60" align="center">
							体检时间
						</th>
						<th width="60" align="center">
							操作时间
						</th>
						<th width="50" align="center">
							是否合格
						</th>
						<th width="100" align="center">
							不合格原因
						</th>
						<th width="100" align="center">
							学员状态
						</th>
					</tr>
				</thead>
				<tbody>
					<%
						int number = 1;
					%>
					<s:iterator value="pageForm.list" var="driving">
						<tr id="${driving[1]}">
							<td>
								<input type="radio" name="a" class="JQuerypersonvalue"
									value="${driving[1]}" />
							</td>
							<td>
								<span><%=number%></span>
							</td>
							<td>
								<span id="companyName">${driving[3]}</span>
								<span id="companyid" style="display: none">${driving[2]}</span>
							</td>

							<td>
								<span id="organizationname">${driving[5]}</span>
								<span id="organizationID" style="display: none">${driving[4]}</span>
							</td>
							<td>
								<span id="responsiblePersonsID">${driving[26]}</span>
							</td>
							<td>
								<span id="registrationdate">${driving[7]}</span>
							</td>
							<td>
								<span id="registrationcarid" style="display: none">${driving[8]}</span>
								<span id="registrationcarname">${driving[9]}</span>
							</td>
							<td>
								<span id="studentname">${driving[10]}</span>
								<span id="studentid" style="display: none">${driving[18]}</span>
							</td>
							<td>
								<span id="studentsex">${driving[11]}</span>
							</td>
							<td>
								<span id="studentphone">${driving[12]}</span>
							</td>
							<td>
								<span id="studentcard">${driving[13]}</span>
							</td>
							<td
								style="dateYuJing =='dateYuJing'?'background-color: red ':' '">
								<span id="resgistrationmedicaldate">${driving[14]}</span>
							</td>
							<td>
								<span id="operationdate">${driving[16]}</span>
							</td>
							<td>
								<span id="istrues">${driving[15]}</span>
							</td>
							<td>
								<span id="reason">${driving[17]}</span>
								<span id="drivingprincipalkey" style="display: none">${driving[0]}</span>
								<span id="drivingprincipalid" style="display: none">${driving[1]}</span>
							</td>
							<td>
								<span id="studentstatus">${driving[21]=='03'&&driving[15]=='合格'?'已预约培训':'未预约培训'}</span>
							</td>
						</tr>
						<%
							number++;
						%>
					</s:iterator>
				</tbody>
			</table>

			<c:import url="../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/driving/ea_getDrivingList.jspa?pageNumber=${pageNumber}&dateYuJing=${dateYuJing}&docstatus=${docstatus}&studentstatus=${studentstatus}&title=${title}&search=${search}&conditions=${conditions}
					&extensionStaffCoach=${extensionStaffCoach}&dtDrivingPrincipal.studentid=${dtDrivingPrincipal.studentid}">
				</c:param>
			</c:import>

		</div>

		

		<form name="cstaffForm" id="cstaffForm" method="post">
			<input type="submit" name="submit" style="display: none" />
			<div class="jqmWindow jqmWindowcss3"
				style="top: 10%; width: 840px; left: 35%" id="jqModel">
				<div class="content">
					<div class="contentbannb">
						<div class="drag">
							学员信息
							<div class="close"></div>
						</div>
					</div>
					<table border="0" id="stafftable2" align="center" cellpadding="0"
						cellspacing="0"
						style="margin-top: 5px; margin-bottom: 5px; table-layout: fixed; width: 840px">
						<tr>

							<td width="100" height="30" align="right">
								公司：
							</td>
							<td width="180">
								<input type="text" id="companyName" readonly="readonly"
									value="${companyName}"
									style="border: 0; background-color: #DAE7F6; width: 180px" />
							</td>
							<td width="90" align="right">
								部门：
							</td>
							<td width="190">
								<input type="text" id="organizationname" readonly="readonly"
									style="border: 0; background-color: #DAE7F6;" />
							</td>
							<td width="100" align="right">
								责任人：
							</td>
							<td width="180">
								<input type="text" id="responsiblePersonsID" readonly="readonly"
									class="put3"
									style="border: 0; background-color: #DAE7F6; width: 40px" />

							</td>

						</tr>
						<tr>

							<td height="30" align="right">
								报名时间：
							</td>
							<td>
								<input type="text" id="registrationdate"
									onfocus="WdatePicker({skin:'whyGreen',dateFmt: 'yyyy-MM-dd'})"
									class="put3" name="dtDrivingPrincipal.registrationdate"
									style="width: 80px" />
							</td>
							<td align="right">
								车型：
							</td>
							<td>
							<input type="text" id="registrationcarname" readonly="readonly"
									style="border: 0; background-color: #DAE7F6;" />
								<%--<s:select id="registrationcarid" cssClass="put3"
									cssStyle="width:80px;"
									name="dtDrivingPrincipal.registrationcarid"
									list="examinationList" listKey="codeID" listValue="codeValue"
									headerKey="" headerValue="选择"></s:select>
								<a href="#"
									onclick="toCCode('scode201211095p8932ixtz0000000014','#registrationcarid','#cstaffForm')">新添</a>
							--%>
							
							</td>
							<td align="right">
								姓名：
							</td>
							<td>
								<input type="text" id="studentname"
									style="border: 0; background-color: #DAE7F6;" />
								<input type="hidden" id="studentid"
									name="dtDrivingPrincipal.studentid" value="" />
							</td>

						</tr>
						<tr>

							<td height="30" align="right">
								性别：
							</td>
							<td>
								<input type="text" id="studentsex"
									style="border: 0; background-color: #DAE7F6;" />
							</td>
							<td align="right">
								电话：
							</td>
							<td>
								<input type="text" id="studentphone"
									style="border: 0; background-color: #DAE7F6;" />
							</td>
							<td align="right">
								身份证号：
							</td>
							<td>
								<input type="text" id="studentcard"
									style="border: 0; background-color: #DAE7F6;" />
							</td>

						</tr>
						<tr>

							<td height="30" align="right">
								体检时间：
							</td>
							<td>
								<input type="text" id="resgistrationmedicaldate" class="put3"
									onfocus="WdatePicker({skin:'whyGreen',dateFmt: 'yyyy-MM-dd'})"
									name="dtDrivingPrincipal.resgistrationmedicaldate"
									style="width: 80px" />
							</td>
							<td align="right">
								资料是否合格：
							</td>
							<td>
								<select id="istrues" name="dtDrivingPrincipal.istrues">
									<option value="合格">
										合格
									</option>
									<option value="不合格">
										不合格
									</option>
								</select>
							</td>
							<td align="right">
								操作时间：
							</td>
							<td>
								<input type="text" id="operationdate"
									style="width: 80px; border: 0; background-color: #DAE7F6;"
									readonly="readonly" />
							</td>

						</tr>
						<tr id="isHege" style="display: none">

							<td height="30" align="right">
								不合格原因：
							</td>
							<td colspan="6">
								<input type="text" id="reason" name="dtDrivingPrincipal.reason"
									class="" style="width: 160px" />
								<input type="hidden" id="drivingprincipalid"
									name="dtDrivingPrincipal.drivingprincipalid" />
								<input type="hidden" id="drivingprincipalkey"
									name="dtDrivingPrincipal.drivingprincipalkey" />
								<input type="hidden" id="docstatus" name="docstatus"
									value="${docstatus}" />
								<input type="hidden" id="studentstatus" name="studentstatus"
									value="${studentstatus}" />
								<input type="hidden" id="title" name="title" value="${title}" />
							</td>
						</tr>
						<tr class="islib " style="display: none" id="istrues2">
							<td align="right">
								是否档案编辑：
							</td>
							<td colspan="6">
								<select class="isShowDangAn" name="isShowDangAn">
									<option value="是">是</option>
									<option value="否">否</option>
								</select>
							</td>
						</tr>
						<tr class="islib" style="display: none">
							<td align="right">
								芯片号：
							</td>
							<td colspan="6">
								<input type="text" name="archiveTemp.chipid" id="chipidss"
									readonly class="put3 chip" />
								<input type="button" class="input-button readchipid" value="读取" />
							</td>
						</tr>
						<tr class="islib" style="display: none" >
							<td align="right">
								条码：
							</td>
							<td colspan="6">
								<input type="text" name="archiveTemp.barcode" id="barcodes" />
							</td>
						</tr>
						
		
				     <tr class="islib"   style="display: none" >
						<td align="right"  height="30">
							档案生效日期：
						</td>
						<td colspan="6" align="left">
                           <input type="text" name="archiveTemp.startDate" id="startDate" onfocus="WdatePicker({lang:'zh-cn', dateFmt:'yyyy-MM-dd', onpicked:function(){endDate.focus();}})"
								readonly value="${fn:substring(archiveTemp.startDate, 0, 10)}" class="put3"/>
							
						</td>
					  </tr>
					<tr class="islib"  style="display: none"  >
					    <td align="right" height="30">
							档案失效日期：
						</td>
						<td colspan="6" align="left">
							<input type="text" name="archiveTemp.endDate" id="endDate"
								onfocus="WdatePicker({lang:'zh-cn', dateFmt:'yyyy-MM-dd', minDate:'#F{$dp.$D(\'startDate\')}'})"
								readonly value="${fn:substring(archiveTemp.endDate, 0, 10)}" class="put3" />
						</td>
					</tr>
					<tr>
							<td height="30" colspan="6" align="center">
								<input type="button" class="input-button JQuerySubmit"
									value="保存" />
							</td>
						</tr>
					</table>
				</div>
				<iframe width="1" height="1" name="loadcab" id="loadcab"></iframe>
			</div>
			<s:token></s:token>
		</form>
		<div class="jqmWindow" style="width: 400px; right: 35%;; top: 10%"
			id="jqModelSearch">
			<form name="SearchForm" id="SearchForm" method="post">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					查询
					<div class="close">
					</div>
				</div>
				<table width="396" id="cataffSearchTable">
					<tr>
						<td width="123" align="right">
							姓名：
						</td>
						<td width="261">
							<input name="dtDrivingPrincipal.studentname" />
						</td>
					</tr>

					<tr>
						<td align="right">
							个人身份证号：
						</td>
						<td>
							<input name="dtDrivingPrincipal.studentcard" />
						</td>
					</tr>
					<tr>
						<td align="right">
							报考车型：
						</td>
						<td>
							<s:select id="registrationcarid"
								name="dtDrivingPrincipal.registrationcarid"
								list="examinationList" listKey="codeID" listValue="codeValue"
								headerKey="" headerValue="请选择" theme="simple"></s:select>
						</td>
					</tr>
					<tr>
						<td align="right">
							是否合格：
						</td>
						<td>
							<select name="conditions">
								<option value="">
									全部
								</option>
								<option value="conditions">
									未合格
								</option>
								<option value="conditionsFalse">
									已合格
								</option>
							</select>
						</td>
					</tr>
					<tr>
						<td align="right">
							不合格原因：
						</td>
						<td>
							<input name="dtDrivingPrincipal.reason" />
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="tosearch"
						value=" 查询 " />
					<input name="search" type="hidden" value="search" />
				</div>
			</form>
		</div>

		<div class="jqmWindow" style="width: 400px; right: 25%; top: 10%"
			id="newccode">
			<div class="drag">
				添加
			</div>
			<table>
				<tr>
					<td>
						代码名字：
					</td>
					<td>
						<input id="ccodevalue" />
						<input id="codePID" type="hidden" />
						<input id="selectID" type="hidden" />
						<input id="formID" type="hidden" />
					</td>
				</tr>
			</table>
			<div align="center">
				<input type="button" class="input-button" onclick="saveCCode()"
					value="确定" />
				<input type="button" class="input-button JQueryreturn1" value="取消" />
			</div>
		</div>
		
		<!-- 预约记录展示层 -->
		<div id="bankJqm" class="jqmWindow"
			style="width: 95%; height: 320px; absolute; display: none; left: 2.5%; top: 10%; background: #eff; overflow-x: hidden; overflow-y: auto;">
			<iframe name="daoRu" id="daoRu" width="100%" height="270px"
				frameborder="0" style="overflow-x: hidden; overflow-y: auto;"></iframe>
			<div align="center">
				<%--<input type="button" class="input-button" id="DaoRuFanqd"
					value=" 确定 " style="cursor: hand; border: 0;" />
				--%><input type="button" class="input-button" id="DaoRuFan" value=" 关闭 "
					style="cursor: hand; border: 0;" />
			</div>
			
		</div>
		
		
		<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
	</body>
</html>