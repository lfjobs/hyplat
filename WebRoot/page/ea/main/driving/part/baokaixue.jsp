<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="cs" uri="http://java.sun.com/jstl/core_rt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>学员是否报开学</title>
		<script src="<%=basePath%>js/jquery-1.3.1.js" type="text/javascript"></script>
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
			src="<%=basePath%>/js/ea/driving/part/baokaixue.js"></script>
		<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/common/common.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/common/organizationTree.js"></script>
		<script type="text/javascript">
	
   var depotID = '';
   var basePath = '<%=basePath%>';
   var personurl='';
   var  depotName='';
	var pNumber=${pageNumber};
	var token=0;
	var depotPID = '${depotID}';
	var drivingprincipalid="";
	var treeID = '<%=session.getAttribute("organizationID")%>';	
	var dateYuJing='${dateYuJing}';	
	var docstatus='${docstatus}';//单据状态
	var studentstatus='${studentstatus}';//学员状态
	var title='${title}';
	var studentName="";
	var search='${search}';
	var conditions='${conditions}';
	var other='${other}';//区别导航页面 
	
	var extensionStaffCoach='${extensionStaffCoach}';//新版学员报名标识符
	var studentID='${dtDrivingPrincipal.studentid}';//新版报名学员ID
	var identifier='${identifier}';
	
	
	$(function(){
		$("html").css("overflow-x",extensionStaffCoach=='extensionStaffStudent'?"scroll":"");
		$("html").css("overflow-y","hidden");
	});
	function getValueForParm(e){ //打开页面
	  	$("#ifr").attr("src",basePath+"ea/cstaff/ea_getListCStaffByCompanyID.jspa?cstaff.status=99");
	  	$("#jqmWindow2").jqmShow();
	}
	$(document).ready(function() {
		$("#isBack").click(function(){// 返回
	       $("#jqmWindow2").jqmHide();
	    }); 
	   
		$("#isSubmit").click(function(){// 选择确定
			var value1 = window.frames["ifr"].personvalue;//弹出框的页面必须声明opertionID这个参数接收id
			if(value1 == ""){
				alert("请选择")
				return;
			}
			var value2 = window.frames["ifr"].$("tr#"+value1).find("span#staffName").text();//弹出框的页面存在于span中才取得到
			var value3 = window.frames["ifr"].$("tr#"+value1).find("span#staffID").text();//弹出框的页面存在于span中才取得到
			
			$("form#yuekaoForm").find("input#appointmenPeopleID").val(value3);
			$("form#yuekaoForm").find("input#appointmenPeople").val(value2);
			$("#ifr").attr("src","");
	        $("#jqmWindow2").jqmHide();
	    });
	});
</script>
		<script type="text/javascript" src="<%=basePath%>js/ea/ccodes_add.js"></script>
	</head>
	<body>
		<form method="post" name="sortchildren" id="sortchildren">
			<input type="submit" name="submit" style="display: none" />
			<input id="oID" name="depotID" type="hidden" />
		</form>
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
						<th width="80" align="center">
							报名时间
						</th>
						<th width="80" align="center">
							报开学时间
						</th>
						<th width="80" align="center">
							报开学号
						</th>
						<th width="80" align="center">
							学员期数
						</th>
						<!-- 
						<th width="170" align="center">
							公司
						</th>
						<th width="80" align="center">
							部门
						</th>
						<th width="50" align="center">
							责任人
						</th>
						<th width="60" align="center">
							教练员
						</th>
						<th width="60" align="center">
							车牌号
						</th> -->
						<th width="50" align="center">
							学员姓名
						</th>
						<th width="50" align="center">
							性别
						</th>
						<th width="100" align="center">
							电话
						</th>
						<th width="150" align="center">
							身份证号
						</th>
						<th width="50" align="center">
							车型
						</th>
						<!-- <th width="60" align="center">
							体检时间
						</th>
						<th width="50" align="center">
							体检是否合格
						</th>
						<th width="60" align="center">
							操作时间
						</th> -->
						<!-- <th width="60" align="center">
							测试状态
						</th>
						<th width="60" align="center">
							测试结果
						</th>
						<th width="60" align="center">
							考试状态
						</th>
						<th width="60" align="center">
							考试结果
						</th> --><%--
						<th width="100" align="center">
							是否进入下一科目
						</th>
						--%>
						<th  width="80" align="center">
							是否报开学
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
								<input type="checkbox" name="chbox" class="chx"
									value="${driving[1]}" />
							</td>
							<td>
								<span><%=number%></span>
							</td>
							<td>
								<span id="registrationdate">${driving[7]}</span>
							</td>
							<td>
								<span id="schoolopendate">${driving[22]}</span>
							</td>
							<td>
								<span id="studentCode">${driving[31]}</span>
							</td>
							<td>
								<span id="studentPeriods">${driving[32]}</span>
							</td>
							<%-- 
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
								<span id="coachname">${driving[20]==null?'暂未分配':driving[20]}</span>
								<span id="coachid" style="display: none">${driving[19]}</span>
							</td>
							<td>
								<span id="carNumber">${driving[27]==null?'暂未分配':driving[27]}</span>
							</td> --%>
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
							<td>
								<span id="registrationcarid" style="display: none">${driving[8]}</span>
								<span id="registrationcarname">${driving[9]}</span>
							</td>
							<%-- <td style="dateYuJing =='dateYuJing'?'background-color: red ':' '">
								<span id="resgistrationmedicaldate">${driving[14]}</span>
							</td>
							<td>
								<span id="istrues">${driving[15]}</span>
							</td>
							<td>
								<span id="operationdate">${driving[16]}</span>
							</td> --%>
							<%-- <td>
								<span id="toTestOther">${driving[33]=='01'?'已预约':'未预约'}</span>
								<span id="docstatus" style="display: none">${driving[28]}</span>
							</td>
							<td>
								<span id="testOtherResult">${driving[34]=='02'?'合格':driving[34]=='01'?'不合格':driving[34]=='00'?'未考':'未统计'}</span>
							</td>
							<td>
								<span id="studentstatus">${driving[21]=='06'?'已约考':driving[21]=='07'?'已合格':'未约考'}</span>
								<span id="drivingprincipalkey" style="display: none">${driving[0]}</span>
								<span id="drivingprincipalid" style="display: none">${driving[1]}</span>
							</td>
							<td>
								<span id="testresult">${driving[23]=='02'?'合格':driving[23]=='01'?'不合格':driving[23]=='00'?'未考':'未统计'}</span>
							</td> --%><%--
							<td>
								<span id="subjectsstatus">${driving[25]=='01'?'是':'否'}</span>
							</td>
							--%>
							<td>
								<span id="studentstatus">${driving[21]=='04'?'未报开学':'已报开学'}</span>
							</td>		
						</tr>
						<%
							number++;
						%>
					</s:iterator>
				</tbody>
			</table>
			<c:import url="../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/driving/ea_getDrivingList.jspa?pageNumber=${pageNumber}&dateYuJing=${dateYuJing}&docstatus=${docstatus}&studentstatus=${studentstatus}&title=${title}&search=${search}&conditions=${conditions}&other=${other}
					&extensionStaffCoach=${extensionStaffCoach}&dtDrivingPrincipal.studentid=${dtDrivingPrincipal.studentid}&identifier=${identifier }">
				</c:param>
			</c:import><%--
			<div style="width: 100%;">
				<iframe src="" name="main" width="100%" marginwidth="0"
					height="300px" marginheight="0" scrolling="no" frameborder="0"
					id="mainframe" border="0" framespacing="0" noresize="noResize"
					vspale="0">
				</iframe>
			</div>

		--%></div>

		<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
		
		<form name="cstaffForm" id="cstaffForm" method="post">
			<input type="submit" name="submit" style="display: none" />
			<input type="hidden" name="str" id="strs"/>	
			<div class="jqmWindow jqmWindowcss3"
				style="top: 10%; width: 850px; left: 35%" id="jqModel">
				<div class="content">
					<div class="contentbannb">
						<div class="drag">
							学员信息
							<div class="close"></div>
						</div>
					</div>
					<table width="850" border="0" id="stafftable2" align="center"
						cellpadding="0" cellspacing="0"
						style="margin-top: 5px; margin-bottom: 5px;">
						<tr>

							<td width="80" height="35" align="right">
								公司：
							</td>
							<td width="80">
								<input type="text" id="companyName" readonly="readonly"
									style="border: 0; background-color: #DAE7F6; width: 180px"
									value="${companyName}" />
							</td>
							<td width="80" height="35" align="right">
								部门：
							</td>
							<td width="80">
								<input type="text" id="organizationname" readonly="readonly"
									style="border: 0; background-color: #DAE7F6" />
							</td>
							<td width="80" height="35" align="right">
								责任人：
							</td>
							<td width="80">
								<input type="text" id="responsiblePersonsID" readonly="readonly"
									style="border: 0; background-color: #DAE7F6" />
							</td>

						</tr>
						<tr>

							<td width="80" height="35" align="right">
								报名时间：
							</td>
							<td width="80">
								<input type="text" id="registrationdate" readonly="readonly"
									style="border: 0; background-color: #DAE7F6" />
							</td>
							<td width="80" height="35" align="right">
								车型：
							</td>
							<td width="80">
								<input type="text" id="registrationcarname" readonly="readonly"
									style="border: 0; background-color: #DAE7F6" />
							</td>
							<td width="80" height="35" align="right">
								姓名：
							</td>
							<td width="80">
								<input type="text" id="studentname" readonly="readonly"
									style="border: 0; background-color: #DAE7F6" />
							</td>

						</tr>
						<tr>

							<td width="80" height="35" align="right">
								性别：
							</td>
							<td width="80">
								<input type="text" id="studentsex" readonly="readonly"
									style="border: 0; background-color: #DAE7F6" />
							</td>
							<td width="80" height="35" align="right">
								电话：
							</td>
							<td>
								<input type="text" id="studentphone" readonly="readonly"
									style="border: 0; background-color: #DAE7F6" />
							</td>
							<td width="80" height="35" align="right">
								身份证号：
							</td>
							<td width="80">
								<input type="text" id="studentcard" readonly="readonly"
									style="border: 0; background-color: #DAE7F6" />
							</td>

						</tr>
						<tr>

							<td width="80" height="35" align="right">
								体检时间：
							</td>
							<td>
								<input type="text" id="resgistrationmedicaldate"
									readonly="readonly"
									style="border: 0; background-color: #DAE7F6" />
							</td>
							<td width="80" height="35" align="right">
								是否合格：
							</td>
							<td width="80">
								<input type="text" id="istrues" readonly="readonly"
									style="border: 0; background-color: #DAE7F6" />
							</td>
							<td width="80" height="35" align="right">
								操作时间：
							</td>
							<td width="80">
								<input type="text" id="operationdate" readonly="readonly"
									style="border: 0; background-color: #DAE7F6" />
							</td>

						</tr>
						<tr>

							<td width="80" height="35" align="right">
								不合格原因：
							</td>
							<td width="80">
								<input type="text" id="reason" readonly="readonly"
									style="border: 0; background-color: #DAE7F6; width: 180px" />
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

							<td width="80" height="35" align="right">
								教练：
							</td>
							<td width="80">
								<input type="text" id="coachname" readonly="readonly"
									style="border: 0; background-color: #DAE7F6; width: 180px" />
								<input type="hidden" id="coachid"
									name="dtDrivingPrincipal.coachid" />
							</td>
							<td width="80" height="35" align="right">
								报开学时间：
							</td>
							<td width="80">
								<input type="text" id="schoolopendate" readonly="readonly"
									style="border: 0; background-color: #DAE7F6" />
							</td>
						</tr>
					</table>
				</div>
			</div>
			<s:token></s:token>
		</form>
        
		<div class="jqmWindow" style="width: 450px; left: 30%; top: 15%;border: 0px;text-align: center;"
				id="jqModelSearchss">
				<form name="yuekaoForm" id="yuekaoForm" method="post">
					<input type="submit" name="submit" style="display: none" />
					<input type="hidden" name="dtDrivingPrincipal.studentid" value="${dtDrivingPrincipal.studentid}"  style="display: none" />
					<div class="drag">
						约考
						<div class="close">
						</div>
					</div>
					<table  cellspacing="5px" style="margin-left: 50px;width: 350px;">
						<tr>
							<td align="right">
							约考时间：
							</td>
							<td align="left">
								<input type="text"
									name="dtDrivingTest.testdate" onfocus="WdatePicker({skin:'whyGreen',dateFmt: 'yyyy-MM-dd'})"  class="put3"/>
								<input type="hidden" name="str" id="strs"/>	
								<input type="hidden" id="docstatusStr"
									name="docstatusStr" />
							</td>
						</tr>
						<tr>
							<td align="right">
							是否缴费：
							</td>
							<td align="left">
								<s:select list="#{'00':'否','01':'是'}" id="ifPay" name="dtDrivingTest.ifPay"></s:select>
							</td>
						</tr>
						<tr class="ifPayNote">
						<td align="right">
							缴费名称：
							</td>
							<td align="left">
							<input name="dtDrivingTest.payName" id="payName" class="put3"/>
							</td>
						</tr>
						<tr class="ifPayNote" >
						<td align="right">
							缴费金额：
							</td>
							<td align="left">
							<input name="dtDrivingTest.payMoney" id="payMoney" class="put3 isNaN"/>
							</td>
						</tr>
						</tr>
						<tr>
							<td align="right">
							预约方式：
							</td>
							<td align="left">
							<s:select id="appointmenType"
									name="dtDrivingTest.appointmenType"
									list="appointmentList" listKey="codeValue" listValue="codeValue" headerKey="" headerValue="请选择"
									theme="simple"></s:select>
							</td>	
						</tr>
						<tr>
							<td align="right">约考责任人：</td>
							<td align="left">
								<input type="text" name="dtDrivingTest.appointmenPeople" id="appointmenPeople" />
								<input type="hidden" name="dtDrivingTest.appointmenPeopleID" id="appointmenPeopleID" />
								<a  href="#" onclick="getValueForParm(this)"  style="width: 25%;">选择</a>
							</td>
						</tr>
					</table>
					<div align="center" >
						<input type="button" class="JQuerySubmit" 
							value=" 确定 " />
					</div>
				</form>
			</div>
			
		<div  id="jqModelSearch">
          <form name="SearchForm" id="SearchForm" method="post">
            	<input type="submit" name="submit" style="display:none"/>
            			报开学时间：
							
							<input type="text" id="sdate" name="dtDrivingPrincipal.searchStaDate" size="9" onfocus="date(this)"
									/>&nbsp;至&nbsp;
							<input type="text" id="edate" name="dtDrivingPrincipal.searchEndDate" size="9" onfocus="date(this)"
									/>
                           姓名： 
							<input name="dtDrivingPrincipal.studentname" size="5"/>
                 个人身份证号：<input name="dtDrivingPrincipal.studentcard" size="11"/></td>
                    教练员：
                     <input name="dtDrivingPrincipalType.coachname" size="5"/>
                     车牌号：<input name="dtDrivingPrincipalType.carNumber" size="8"/><br/> &nbsp;&nbsp;&nbsp;&nbsp;报考车型：
                     <s:select id="registrationcarname"
									name="dtDrivingPrincipal.registrationcarname"
									list="examinationList" listKey="codeValue" listValue="codeValue" headerKey="" headerValue="请选择"
									theme="simple"></s:select>是否报开学：
                   			<select name="conditions">
                   				<option value="">全部</option>
                   				<option value="conditions">已报开学</option>
                   				<option value="conditionsFalse">未报开学</option>
                   			</select>
                   		<input type="hidden" name="dtDrivingPrincipalType.testOtherResult" value="${dtDrivingPrincipalType.testOtherResult}"/>
           
              <input type="button" id="tosearch" value=" 查询 " /><input name="search" type="hidden" value="search" />
          
            </form>
        </div>
        <!-- 社会人力资源 -->
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
	</body>
</html>