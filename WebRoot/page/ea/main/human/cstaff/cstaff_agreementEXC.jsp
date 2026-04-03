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
%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>公司合同管理</title>
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
<script type="te/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script src="<%=basePath%>js/ea/human/cstaff/cstaff_agreementexc.js"></script>
<script type="text/javascript">
   var token = 0;
   var select = 1;
   var agreementID = '';
   var basePath = '<%=basePath%>';
   var staffID = '${agreement.staffID}'; 
   var notoken = 0; 
   var ppageNumber = ${pageNumber};
   var search = '${search}';
   var staff = '${staff}';
</script>
</head>

  <body>
 		<form  name="agreementForm" id="agreementForm" method="post">
 		<input type="submit" name="submit" style="display:none"/>
		<s:token></s:token>
<div id="main_main" class="main_main"> 
  <table class="agreement">
  	<thead>
	    <tr>
	      <th width="30" align="center" >选择</th>
	      <th width="100" align="center" >工号</th>
	      <th width="100" align="center" >姓名</th>
	      <th width="100" align="center" >部门</th>
	      <th width="100" align="center" >岗位</th>
	      <th width="100" align="center" >工种</th>
	      <th width="100" align="center" >出生日期</th>
	      <th width="180" align="center" >身份证号</th>
	      <th width="100" align="center" >联系电话</th>
	      <th width="70" align="center" >参观期协议</th>
	      <th width="70" align="center" >培训期协议</th>
	      <th width="70" align="center" >劳动合同</th>
	      <th width="70" align="center" >竞职协议</th>
	      <th width="70" align="center" >保密协议</th>
	      <th width="70" align="center" >安全责任协议</th>
	      <th width="80" align="center" >薪级级别</th>
	    </tr>
    </thead>
   <s:iterator value="pageForm.list" var="lists">
		<tr id="${lists[0]}" ">
			<td>
				<input type="radio" name="a" class="JQuerypersonvalue"
					value="${lists[0]}" />
			</td>
			<td>
				<span id="">${lists[1]}</span>
			</td>
			<td>
				<span id="">${lists[2]}</span>
			</td>
			<td>
				<span id="">${lists[3]}</span>
			</td>
			<td>
				<span id="">${lists[4]}</span>
			</td>
			<td>
				<span id="">${lists[5]}</span>
			</td>
			<td>
				<span id="">${lists[6]}</span>
			</td>
			<td>
				<span id="">${lists[7]}</span>
			</td>
			<td>
				<span id="">${lists[8]}</span>
			</td>
			<td>
				<span id="">${lists[9]}</span>
			</td>
			<td>
				<span id="">${lists[10]}</span>
			</td>
			<td>
				<span id="">${lists[11]}</span>
			</td>
			<td>
				<span id="">${lists[12]}</span>
			</td>
			<td>
				<span id="">${lists[13]}</span>
			</td>
			<td>
				<span id="">${lists[14]}</span>
			</td>
			<td>
				<span id="">${lists[15]}</span>
			</td>
			
		</tr>
	</s:iterator>
    </tbody>
  </table>
  <c:import url="../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/agreement/ea_getListEXC.jspa?pageNumber=${pageNumber}&search=${search}&staff=${staff}">
				</c:param>
			</c:import>
</div>
</form>
<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
	
	<!--搜索窗口 -->
	<div class="jqmWindow" style="width: 300px; right: 45%; top: 30%"
		id="jqModelSerch">
		<form name="jqModelSerchForm" id="jqModelSerchForm" method="post">
			
			<div class="drag">
				查询信息
				<div class="close">
				</div>
			</div>
			<table width="260px" id="SearchTable">
				<tr>
					<td align="right" width="100px">
						工号：
					</td>
					<td>
						<input name="staff.staffCode" id="staffCode" />
					</td>
				</tr>
				<tr>
					<td align="right">
						姓名：
					</td>
					<td>
						<input name="staff.staffName" id="staffName" />
					</td>
				</tr>
				<tr>
					<td align="right">
						身份证号：
					</td>
					<td>
						<input name="staff.staffIdentityCard" id="staffIdentityCard" />
					</td>
				</tr>
			</table>
			<div align="center">
				<input type="submit" name="submit" style="display: none" />
				<input type="button" class="input-button" id="searchAda"
					value=" 查询 " />
				<input name="search" type="hidden" value="search" />
			</div>
		</form>
</div>
</body>
</html>

