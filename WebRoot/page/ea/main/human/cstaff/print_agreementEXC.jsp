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
<title>打印合同管理报表</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="te/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
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
<style type="text/css">
table,td,th{
	border:solid 1px grey;
	margin: 0px;
}	
</style>
</head>

  <body>
<div id="main_main" class="main_main" align="center"> 
	<div align="center" width="100%"height="100px" ><span style="font-size: 30px;">在职员工合同报表</span></div>
  <table class="agreement " align="center">
	    <tr>
	      <th width="70" align="center" rowspan="2">工号</th>
	      <th width="70" align="center" rowspan="2">姓名</th>
	      <th align="center" colspan="3">岗位信息</th>
	      <th align="center" colspan="3">基本信息</th>
	      <th align="center" colspan="6">劳动信息</th>
	      <th width="80" align="center" rowspan="2">薪级级别</th>
	    </tr>
	     <tr>
	      <th width="100" align="center" >部门</th>
	      <th width="100" align="center" >岗位</th>
	      <th width="70" align="center" >工种</th>
	      <th width="100" align="center" >出生日期</th>
	      <th width="180" align="center" >身份证号</th>
	      <th width="100" align="center" >联系电话</th>
	      <th width="50" align="center" >参观期协议</th>
	      <th width="50" align="center" >培训期协议</th>
	      <th width="50" align="center" >劳动合同</th>
	      <th width="50" align="center" >竞职协议</th>
	      <th width="50" align="center" >保密协议</th>
	      <th width="50" align="center" >安全责任协议</th>
	    </tr>
  <c:forEach var='lists' items="${beans}" varStatus="index">
		<tr id="${lists[0]}" ">
			<td align="center" >
				<span id="">${lists[1]}</span>
			</td>
			<td align="center" >
				<span id="">${lists[2]}</span>
			</td>
			<td align="center" >
				<span id="">${lists[3]}</span>
			</td>
			<td align="center" >
				<span id="">${lists[4]}</span>
			</td>
			<td align="center" >
				<span id="">${lists[5]}</span>
			</td>
			<td align="center" >
				<span id="">${lists[6]}</span>
			</td>
			<td align="center" >
				<span id="">${lists[7]}</span>
			</td>
			<td align="center" >
				<span id="">${lists[8]}</span>
			</td>
			<td align="center" >
				<span id="">${lists[9]}</span>
			</td>
			<td align="center" >
				<span id="">${lists[10]}</span>
			</td>
			<td align="center" >
				<span id="">${lists[11]}</span>
			</td>
			<td align="center" >
				<span id="">${lists[12]}</span>
			</td>
			<td align="center" >
				<span id="">${lists[13]}</span>
			</td>
			<td align="center" >
				<span id="">${lists[14]}</span>
			</td>
			<td align="center" >
				<span id="">${lists[15]}</span>
			</td>
			
		</tr>
	</c:forEach>
  </table>
</div>
</form>
<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
</body>
</html>

