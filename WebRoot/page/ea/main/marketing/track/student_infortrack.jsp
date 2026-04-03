<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8" %> 
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/"; 
	String filepath = request.getSession().getServletContext().getRealPath("/");
	response.setHeader("Pragma","No-cache");
	response.setHeader("Cache-Control","no-cache");
	response.setDateHeader("Expires", -10);
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<!-- 社会人力页面报名调整方案31-->   
<title>人员报名列表</title>
<link href="<%=basePath%>/css/ea/validate.css" rel="stylesheet" type="text/css" />
<link href="<%=basePath%>css/admin_mains.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/overlayer.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/overlayer.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script type="text/javascript" src="<%=basePath%>js/photoup/CJL.0.1.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/photoup/ImagePreviewd.js"></script>

<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script type="text/javascript">
		var basePath = '<%=basePath%>';
		var retoken = 0;
		var staffID = "${cstaff.staffID}";
		var staffName="${cstaff.staffName }";
		var personIdentityCard;
		var aa = '<%=request.getParameter("aa")%>';
		var showType ='${showType}'; 
		var select = 1;
    	var str="";
    	var temp = "";
    	var notoken = 0;
    	var pNumber = ${pageNumber};
    	var peopleid="";
    	var relationID="${contactrelation.relationID}";
    	var  search='${search}';
    	var module_title='${param.module_title}';  
    	var educationalCategories='${param.educationalCategories}';
</script>
</head>

<body style="overflow: auto;">
	<div class="content" style="width:900px;">
		<div class="contentbannb">
			<div class="divtx">&nbsp;学员进度跟踪表</div>
		</div>
		<table width="99%" border="0" align="center" cellpadding="0"
			cellspacing="0" class="biaoti box1">
			<tr>
				<td height="27" class="txt03">基本信息</td>
			</tr>
		</table>
		
		<div id="box1">
			<table id="stafftable" width="99%" align="center" cellpadding="0"
				cellspacing="0" class="table" >
				<tr class="trheight">
					<td width="12%" align="right">员工编号：</td>
					<td width="23%"><span id="staffCode">${cstaff.staffCode }</span></td>
					<td width="12%" align="right">档案编号：</td>
					<td width="23%" done0="9" done1="9"><span id="recordCode">${cstaff.recordCode }</span></td>
					<td width="30%" rowspan="5" align="center" id="phototd">
					
						<img name="photos" id="photo"  src="<%=basePath%>${cstaff.photo}" />
						
					</td>
				</tr>
				<tr>
					<td  align="right">姓名：</td>
					<td done0="10" done1="10"><span id="staffName">${cstaff.staffName }</span></td>
					<td align="right">曾用名：</td>
					<td done0="11" done1="11"><span id="usedNmae">${cstaff.usedNmae }</span></td>
				</tr>
				<tr>
					<td  align="right">性别：</td>
					<td done0="12" done1="12"><span id="sex">${cstaff.sex }</span></td>
					<td align="right">出生日期：</td>
					<td done0="13" done1="13"><span id="birthday">${cstaff.birthday }</span></td>
				</tr>
				<tr>
					<td  align="right">民族：</td>
					<td><span id="nation">${cstaff.nation }</span></td>
					<td align="right">籍贯：</td>
					<td><span id="nativePlace">${cstaff.nativePlace }</span></td>
				</tr>
				<tr>
					<td align="right">国籍：</td>
					<td><span id="nationality">${cstaff.nationality }</span></td>
					<td  align="right">身份证号码：</td>
					<td><span id="staffIdentityCard">${cstaff.staffIdentityCard }</span></td>
					<td style="display:block;border:0;">
						<input id="singleShuterphoto" type="button" style="width: 50px;" class="isHide input-button" 
							value="摄像头" /><input name="photo" id="staffphoto" class="input01 isHide"  type="file"
							style="width: 150px;" />
						<input name="cstaff.photo" type="hidden" id="photo"/>
						<input name="cstaff.staffKey" id="staffKey" type="hidden" />
					</td>
				</tr>			
			</table>
		</div>
		<table width="99%" border="0" align="center" cellpadding="0"
			cellspacing="0" class="biaoti box1">
			<tr>
				<td height="27" class="txt03">联系方式</td>
			</tr>
		</table>
		<div id="box2">
				<table id="stafftable" width="99%" align="center" cellpadding="0"
					cellspacing="0" class="table" >
					<tr>
						<td width="12%" align="right">移动电话：</td>
						<td width="23%"><span id="staffCode">${cstaff.reference }</span></td>
						<td width="12%" align="right">E-mail：</td>
						<td width="23%" done0="9" done1="9"><span id="recordCode">${cstaff.referenceOrganization }</span></td>
						<td width="12%" align="right">QQ：</td>
						<td width="23%" done0="9" done1="9"><span id="recordCode">${cstaff.referenceCode }</span></td>
					</tr>				
				</table>
		</div>
		<table width="99%" border="0" align="center" cellpadding="0"
			cellspacing="0" class="biaoti box1">
			<tr>
				<td height="27" class="txt03">公司信息</td>
			</tr>
		</table>
		<div id="box1">
			<table id="stafftable" width="99%" align="center" cellpadding="0"
				cellspacing="0" class="table" >
				<tr>
					<td width="12%" align="right">分校/报名点：</td>
					<td width="23%"><span id="staffCode">${RegistrationCompany.organizationName }</span></td>
					<td width="12%" align="right">报名电话：</td>
					<td width="23%" done0="9" done1="9"><span id="recordCode">${RegistrationCompany.registrationPhone}</span></td>
					
				</tr>
				<tr>
					<td width="12%" align="right">推荐人：</td>
					<td width="23%" done0="9" done1="9"><span id="recordCode">${RegistrationCompany.referrer}</span></td>
					<td width="12%" align="right">推荐人联系方式：</td>
					<td width="23%" done0="9" done1="9"><span id="recordCode">${RegistrationCompany.referrerPhone }</span></td>
				
				</tr>	
				<tr>
					<td width="12%" align="right">推荐人身份证号码：</td>
					<td width="23%" done0="9" done1="9"><span id="recordCode">${RegistrationCompany.referrerIdentityCard }</span></td>
					<td width="12%" align="right">受理人：</td>
					<td width="23%" done0="9" done1="9"><span id="recordCode">${RegistrationCompany.acceptPeople }</span></td>
				</tr>			
			</table>
		</div>	
		<table width="99%" border="0" align="center" cellpadding="0"
			cellspacing="0" class="biaoti box1">
			<tr>
				<td height="27" class="txt03">收费信息</td>
			</tr>
		</table>
		<div id="box1">
			<table id="stafftable" width="99%" align="center" cellpadding="0"
				cellspacing="0" class="table" >
				<thead>
				    <tr>
				      <th width="23%" done0="9" done1="9" align="center" >收费名称</th>
				      <th width="23%" done0="9" done1="9" align="center" >应收金额</th>
				      <th width="23%" done0="9" done1="9" align="center" >实收金额</th>
				      <th width="23%" done0="9" done1="9" align="center" >欠款金额</th>
				    </tr>
	   		 	</thead>
				<s:iterator value="beanList">
				<s:if test="dataTitle=='05'">
					 <tr class="td_bg01 saveAjax trclass">
						<td align="center"><span id="chargeName">${chargeName}</span></td>
						<td align="center"><span id="codeValue">${codeValue}</span></td>
						<td align="center"><span id="chargeMoney">${chargeMoney}</span></td>
						<td align="center"><span id="arrearsMoney">${arrearsMoney}</span></td>
		    	 	</tr>
				</s:if>
		       </s:iterator>
			</table>
		</div>	
		<table width="99%" border="0" align="center" cellpadding="0"
			cellspacing="0" class="biaoti box1">
			<tr>
				<td height="27" class="txt03">证件信息</td>
			</tr>
		</table>
		<div id="box1">
			<table id="stafftable1" width="99%" align="center" cellpadding="0"
				cellspacing="0" class="table" >
				<thead>
				    <tr>
				      <th width="32%" done0="9" done1="9" align="center" >申领</th>
				      <th width="32%" done0="9" done1="9" align="center" >准驾车代号</th>
				      <th width="32%" done0="9" done1="9" align="center" >申请时间</th>
				    </tr>
	   		 	</thead>
				<s:iterator value="beanList">
					<s:if test="dataTitle=='06'">
			          <tr class="td_bg01 saveAjax trclass">
						<td align="center"><span id="credentialsType">${applyCertificate}</span></td>
						<td align="center"><span id="drivingCode">${drivingCode}</span></td>
						<td align="center"><fmt:formatDate value="${applyDate}"  pattern="yyyy-MM-dd HH:mm" /></td>
			    	 </tr>
		    	  </s:if>
		       </s:iterator>
			</table>
		</div>	
		<table width="99%" border="0" align="center" cellpadding="0"
			cellspacing="0" class="biaoti box1">
			<tr>
				<td height="27" class="txt03">接送信息</td>
			</tr>
		</table>
		<div id="box1">
			<table id="stafftable2" width="99%" align="center" cellpadding="0"
				cellspacing="0" class="table" >
				<thead>
				    <tr>
				      <th width="16%" done0="9" done1="9" align="center" >接送地点</th>
				      <th width="16%" done0="9" done1="9" align="center" >接时间</th>
				      <th width="16%" done0="9" done1="9" align="center" >送时间</th>
				      <th width="16%" done0="9" done1="9" align="center" >接送责任人</th>
				      <th width="16%" done0="9" done1="9" align="center" >责任人电话</th>
				      <th width="16%" done0="9" done1="9" align="center" >接送车牌号</th>
				    </tr>
	   		 	</thead>
				<s:iterator value="beanList">
				  <s:if test="dataTitle=='07'">
		          	<tr class="td_bg01 saveAjax trclass">
						<td align="center"><span id="shuttleAddress">${shuttleAddress}</span></td>
						<td align="center"><fmt:formatDate value="${shuttleDate}"  pattern="yyyy-MM-dd HH:mm" /></td>
						<td align="center"><fmt:formatDate value="${endShuttleDate}"  pattern="yyyy-MM-dd HH:mm" /></td>
						<td align="center"><span id="shuttleStaff">${shuttleStaff}</span></td>
						<td align="center"><span id="shuttleStaffPhone">${shuttleStaffPhone}</span></td>
						<td align="center"><span id="shuttleCarNumber">${shuttleCarNumber}</span></td>
		    	 	</tr>
		    	  </s:if>
		       </s:iterator>
			</table>
		</div>	
		<table width="99%" border="0" align="center" cellpadding="0"
			cellspacing="0" class="biaoti box1">
			<tr>
				<td height="27" class="txt03">教练信息</td>
			</tr>
		</table>
		<div id="box1">
			<table id="stafftable3" width="99%" align="center" cellpadding="0"
				cellspacing="0" class="table" >
				<thead>
				    <tr>
				      <th width="19%" done0="9" done1="9" align="center" >科目</th>
				      <th width="19%" done0="9" done1="9" align="center" >教练姓名</th>
				      <th width="19%" done0="9" done1="9" align="center" >联系方式</th>
				      <th width="19%" done0="9" done1="9" align="center" >身份证号码</th>
				      <th width="19%" done0="9" done1="9" align="center" >车牌号</th>
				    </tr>
	   		 	</thead>
				<s:iterator value="coachList" var="arr">
		          <tr class="td_bg01 saveAjax trclass">
					<td align="center"><span id="chargeName1">${arr[5]}</span></td>
					<td align="center"><span id="arrearsMoney1">${arr[1]}</span></td>
					<td align="center"><span id="arrearsMoney1">${arr[4]}</span></td>
					<td align="center"><span id="arrearsMoney1">${arr[3]}</span></td>
					<td align="center"><span id="arrearsMoney1">${arr[2]}</span></td>
		    	 </tr>
		       </s:iterator>
			</table>
		</div>	
		<table width="99%" border="0" align="center" cellpadding="0"
			cellspacing="0" class="biaoti box1">
			<tr>
				<td height="27" class="txt03">培训信息</td>
			</tr>
		</table>
		<div id="box1">
			<table id="stafftable2" width="99%" align="center" cellpadding="0"
				cellspacing="0" class="table" >
				<thead>
				    <tr>
				      <th width="19%" done0="9" done1="9" align="center" >科目</th>
				      <th width="19%" done0="9" done1="9" align="center" >培训开始时间</th>
				      <th width="19%" done0="9" done1="9" align="center" >培训结束数据</th>
				      <th width="19%" done0="9" done1="9" align="center" >培训时间</th>
				      <th width="19%" done0="9" done1="9" align="center" >培训备注</th>
				    </tr>
	   		 	</thead>
				<s:iterator value="beanList">
				  <s:if test="dataTitle=='08'">
		          	<tr class="td_bg01 saveAjax trclass">
						<td align="center"><span id="shuttleAddress">${subjectStatus}</span></td>
						<td align="center"><fmt:formatDate value="${timingStartTime}"  pattern="yyyy-MM-dd HH:mm" /></td>
						<td align="center"><fmt:formatDate value="${timingEndTime}"  pattern="yyyy-MM-dd HH:mm" /></td>
						<td align="center"><span id="shuttleStaff">${timingTime}${timingTime==null?'':'天'}</span></td>
						<td align="center"><span id="shuttleStaffPhone">${timingNote}</span></td>
		    	 	</tr>
		    	  </s:if>
		       </s:iterator>
			</table>
		</div>
		<table width="99%" border="0" align="center" cellpadding="0"
			cellspacing="0" class="biaoti box1">
			<tr>
				<td height="27" class="txt03">考试信息</td>
			</tr>
		</table>
		<div id="box1">
			<table id="stafftable3" width="99%" align="center" cellpadding="0"
				cellspacing="0" class="table" >
				<thead>
				    <tr>
				      <th width="33%" done0="9" done1="9" align="center" >考试科目</th>
				      <th width="33%" done0="9" done1="9" align="center" >考试时间</th>
				      <th width="33%" done0="9" done1="9" align="center" >考试结果</th>
				    </tr>
	   		 	</thead>
				<s:iterator value="testList" var="arr">
		          <tr class="td_bg01 saveAjax trclass">
					<td align="center"><span id="chargeName1">${arr[0]}</span></td>
					<td align="center"><span id="arrearsMoney1">${arr[1]}</span></td>
					<td align="center"><span id="arrearsMoney1">${arr[2]=='00'?'合格':'不合格'}</span></td>
		    	 </tr>
		       </s:iterator>
			</table>
		</div>						
    </div>
	
		<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
</body>
</html>