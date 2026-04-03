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
<title>机构负责人</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script type="text/javascript" >
   var token = 0;
   var select = 1;
   var agenciesID = '';
   var basePath = '<%=basePath%>';
   var pNumber = ${pageNumber};
   var organizationPID = '${agencies.organizationPID}';
   var notoken = 0;
    var ogID = '${agencies.organizationPID}';
	var ogName = '<%=request.getParameter("ogName") %>';
//打开页面
function getValueForParm1(attachTable,parm1,parm2,parm3,parm4,url){ 
	//alert(parent.$("#jqmWindow2").html());
   // attachTable = $(attachTable).parent().parent().parent().attr("id");
	$("#myform",$("#jqmWindow2")).attr("value",attachTable);
	$("#parm1",$("#jqmWindow2")).attr("value",parm1);
	$("#parm2",$("#jqmWindow2")).attr("value",parm2);
	$("#parm3",$("jqmWindow2")).attr("value",parm3);
	$("#parm4",$("#jqmWindow2")).attr("value",parm4);
	$("#ifrs").attr("src",basePath+url+"?oid="+organizationPID);
	$("#jqmWindow2").jqmShow();
}

</script>
<script src="<%=basePath%>js/ea/human/agencies.js"></script>
<script src="<%=basePath%>js/ea/human/cstaff.js"></script>

</head>
<body>
<form  name="addressForm" id="addressForm" method="post" enctype="multipart/form-data">
<s:token></s:token>
<input type="submit" name="submit" style="display:none"/>
<div id="main_main" class="main_main">
  <table id="addressTable"  class="address">
	  	<thead>
		 	    <tr>
			 	    <th width="30" align="center"  >选择</th>
			 	    <th width="150" align="center" >负责人编号</th>
		            <th width="70" align="center" >岗位开始时间</th>
		            <th width="70" align="center" >岗位结束时间</th>
		            <th width="80" align="center" >机构负责人</th>
		            <th width="50" align="center" >是否店主</th>
		            <th width="85" align="center" >负责人电话</th>
		            <th width="85" align="center" >公司电话</th>
		            <th width="200" align="center" >负责内容</th>
		            <th width="200" align="center" >图片</th>
		            <th width="300" align="center" >备注</th>
					<th width="290" align="" >
					<th width="150" align="" >		            
	      		</tr>
	    </thead>
		<tbody  id="tbwid">
	           <tr id="sa" style="display: none" class="td_bg01 saveAjax model2">
		            <td><input type="radio" name="a" class="JQuerypersonvalue" value="${agencies}"/></td>
		            <td class="td_bg01"><input name="agenciesCode" readonly="readonly" style="border: 0" id="childPartnerName"  size="15" /></td>
		            <td class="td_bg01"><input name="statDate" id="statDate"  onfocus="date();" size="10" /></td>
		            <td class="td_bg01"><input name="endDate" id="endDate" onfocus="date(this);"  size="10"/></td>
		             <td class="td_bg01">
		             <input name="staffID" id="partnerID" type="hidden"  size="6" />
		             <input name="agenciesName" readonly="readonly" id="partnerName" size="6"/>		             
		             <a href="#" id="chioce" class="onc"
		             onclick="getValueForParm1('addressForm','staffCode','partnerName','reference','childPartnerName1','ea/cosincumbent/ea_getStaffForCashier.jspa')">选择</a>
		             </td>
		             <td class="td_bg01"><select name="isHost" id="isHost"><option value="是">是</option><option value="否">否</option></select></td>
		              <td class="td_bg01"><input name="tep" id="childPartnerName1" size="15"/></td>
		              <td class="td_bg01"><input name="octep" id="octep" size="15"/></td>
		            <td class="td_bg01"><input name="agenciesContent" id="agenciesContent" size="20"/></td>
		             <td class="td_bg01">
         			     <input name="photo"  id="photo" type="file" contentEditable="false"/> 
						</td>
		            <td class="td_bg01"><input name="remarks" id="remarks" size="40"/>
							            <input type="hidden" name="agenciesKey" id="agenciesKey"/>
							            <input type="hidden" name="agenciesID" id="agenciesID" />
							            <input type="hidden" name="organizationID" id="organizationID" value="${agencies.organizationPID}"/>
							            <input type="hidden" name="organizationPID" value="${agencies.organizationPID}" id="organizationPID" />
							          
					</td> 
	          </tr>
          <s:iterator value="pageForm.list">
							<tr class="td_bg01 saveAjax" id="${agenciesID}" class="trclass">
								<td class="td_bg01">
									<input type="radio" name="a" class="JQuerypersonvalue trclass"
										value="${agenciesID}" />
								</td>
								<td class="td_bg01">
									<span id="agenciesCode" class="datas">${agenciesCode}</span>
									<input class="model1" value="${agenciesCode}"
										readonly="readonly" style="border: 0" name="agenciesCode"
										id="childPartnerName" />
								</td>
								<td class="td_bg01">
									<span id="statDate" class="datas">${statDate}</span>
									<input class="model1" value="${statDate}" name="statDate"
										onfocus="date(this);" size="10" />
								</td>
								<td class="td_bg01">
									<span id="endDate" class="datas">${endDate}</span>
									<input class="model1" value="${endDate}" name="endDate"
										onfocus="date(this);" size="10" />
								</td>
								<td class="td_bg01">
										<span id="agenciesName" class="datas">${agenciesName}</span>
										<input type="hidden" name="staffID" id="partnerID"
											value="${staffID }" size="6" />
										<input class="model1" readonly="readonly" name="agenciesName"
											value="${ agenciesName}" id="partnerName" size="6" />
										<a href="#" id="chioce" class="model1 onc"
											onclick="getValueForParm1('addressForm','staffCode','partnerName','reference','childPartnerName1','ea/cosincumbent/ea_getStaffForCashier.jspa')">选择</a>
								</td>
								<td class="td_bg01">
									<select name="isHost" id="isHost">
										<s:if test="isHost==是"><option value="是" selected="selected">是</option></s:if><s:else><option value="是">是</option></s:else>
										<s:if test="isHost==否"><option value="否" selected="selected">否</option></s:if><s:else><option value="否">否</option></s:else>							
									</select>
								</td>
								<td class="td_bg01">
									<span id="tep">${tep}</span>
									<input class="model1" value="${tep}" name="tep" id="childPartnerName1"/>
								</td>
								<td class="td_bg01">
									<span id="octep">${octep}</span>
									<input class="model1" value="${octep}" name="octep" size="40" />
								</td>
								<td class="td_bg01">
									<span id="agenciesContent">${agenciesContent}</span>
									<input class="model1" value="${agenciesContent}"
										name="agenciesContent" size="40" />
								</td>
								<td class="td_bg01">
									<span><s:if test="photoPath==null||photoPath==''">无</s:if>
									</span>
									<s:else>
										<span class="datas" onclick="lookImage('${photoPath}');"><a
											href="#">查看</a>
										</span>
									</s:else>
									<input name="photo" type="file" class="model1"
										contentEditable="false" />
									<input name="photoPath" type="hidden" value="${photoPath}"
										class="model1" />
								</td>
								<td class="td_bg01">
									<span id="remarks">${remarks}</span>
									<input class="model1" name="remarks" value="${remarks}"
										size="40" />
									<input type="hidden" name="agenciesKey" value="${agenciesKey}" />
									<input type="hidden" name="agenciesID" value="${agenciesID}" />
									<input type="hidden" name="organizationPID"
										value="${organizationPID}" />
									<input type="hidden" name="organizationID"
										value="${agencies.organizationPID}" />
								</td>
							</tr>
						</s:iterator>
    	</tbody>
  </table>
 <c:import url="../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/agencies/ea_getListAgencies.jspa?pageNumber=${pageNumber}&agencies.organizationPID=${agencies.organizationPID}&ogName=${param.ogName}"></c:param>
</c:import>
</div>
</form>
<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
<div id="jqmWindow2" class="jqmWindow"
	style="width: 95%; height: 400px; absolute; display: none; left: 1%; top: 1%; background: #eff">
	<div style="background: #efg; margin-right: 500px;">
		<input style="display: none;" id="myform" />
		<input style="display: none;" id="parm1" />
		<input style="display: none;" id="parm2" />
		<input style="display: none;" id="parm3" />
		<input style="display: none;" id="parm4" />
		
	</div>
	<iframe name="ifr" id="ifrs" width="100%" height="350px"
		frameborder="0"></iframe>
	<div align="center">
		<input type="button" class="input-button" id="isSubmit" value=" 确定 "
			style="cursor: hand" />
		<input type="button" class="input-button" id="isBack" value=" 关闭 "
			style="cursor: hand" />
	</div>
</div>
</body>
</html>
