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
		%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>


<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<script src="<%=basePath%>/js/ea/validate.js" type="text/javascript"></script>
			<link href="<%=basePath%>/css/ea/validate.css" rel="stylesheet"type="text/css" />

<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/ea/production/production.css" />
<link rel="stylesheet" type="text/css"  href="<%=basePath%>css/admin_main111.css" />

<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/popLayer/css/popstyle.css" />
<script src="<%=basePath%>js/popLayer/js/popLayer.js"
	type="text/javascript"></script>
<script src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js"></script>
<script src="<%=basePath%>js/tree/codebase/dhtmlxtree.js"></script>
<link rel="STYLESHEET" type="text/css"
	href="<%=basePath%>js/tree/codebase/dhtmlxtree.css" />
<script language="javascript" type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" >

</script>

</head>
<body>
<form id="mainForm" name="mainForm" method="post"  enctype="multipart/form-data">
		<s:hidden name="memberAllot.makey"></s:hidden>
	<s:hidden name="memberAllot.maid"></s:hidden>
	<s:token></s:token>
		<input type="submit" name="submit" style="display:none;">
		<div class="main">
			<div class="top">模拟测试</div>
			<div class="body">

				<div class="showinfo show">
					<table id="productbl">
						<tr>
							<td align="right" style="width:38%;" class="t">产品行业：</td>
							<td>
							<s:textfield  name="bsimtest.industryClassification" readOnly="readOnly" cssStyle="width:40%;" id="idd"></s:textfield>
							<s:hidden  name="bsimtest.bsimTestkey" id="bsimTestkey"></s:hidden>
							<s:hidden  name="bsimtest.id" id="id"></s:hidden>
							
							</td>
						</tr>
						<tr>
							<td align="right">项目产品编号：</td>
							<td>
							<s:hidden name="memberAllot.productID" cssClass="inputtext1 ppID"></s:hidden>
								<s:textfield name="bsimtest.itemNumber"  readOnly="readOnly" ></s:textfield>
								</td>
						</tr>
						<tr>
							<td align="right">产品条码：</td>
							<td>
								<s:textfield  name="bsimtest.goodBar" id="auditoroption" readOnly="readOnly">
								</s:textfield>
								</td>
						</tr>
						<tr>
							<td align="right">产品名称：</td>
							<td>
								<s:textfield  name="bsimtest.goodName" readOnly="readOnly" 
								></s:textfield>
								</td>
						</tr>
						<tr>
							<td align="right">单价：</td>
							<td>
								<s:textfield  name="bsimtest.price" readOnly="readOnly"></s:textfield>
								</td>
						</tr>
                         <tr>
							<td align="right">考核数量：</td>
							<td>
								<s:textfield  cssStyle="width:40%;" name="bsimtest.btnumber" readOnly="readOnly"></s:textfield>
								<%-- <s:hidden cssClass="inputtext ids" name="memberAllot.allotorID" ></s:hidden> --%>
								</td>
						</tr>
						<tr>
							<td align="right">金额<span class="xx">*</span>：</td>
							<td>
								<s:textfield  cssStyle="width:40%;" name="bsimtest.money" readOnly="readOnly"  id="idd"></s:textfield>
								<%-- <s:hidden cssClass="inputtext ids" name="memberAllot.transferID" ></s:hidden> --%>
							
								</td>
						</tr>
						<tr>
							<td align="right">审核人<span class="xx input3">*</span>：</td>
							<td>
								<s:textfield  cssStyle="width:40%;" name="bsimtest.auditor" readOnly="readOnly" id="idd"  value="%{account.staffName}"></s:textfield>
								<%-- <s:hidden cssClass="inputtext ids" name="memberAllot.receiverID" ></s:hidden> --%>
						
								</td>
						</tr>
						<tr>
							<td align="right">审核时间：</td>
							<td>
								<s:textfield name="bsimtest.auditTime" readOnly="readOnly"
								 value="%{auditTime}"></s:textfield>
								</td>
						</tr>
						<tr>
					   <td align="right">审核人部门：</td>
					   <td>
						<s:textfield  name="bsimtest.organizationName" readOnly="readOnly" 
								></s:textfield>
								<td>
						
				     </tr>
						
						<tr>

					<td  align="right" >审核意见：</td>
					<td><textarea style="width:200px;height: 100px;"
							name="bsimtest.auditoroption" id="auditoroption" class="put3"></textarea><span class="xx" id="auditoroptionSpan" style="color:red;">*</span></td>
				</tr>
					</table>
				</div>
			</div>
		</div>
		<div class="bottom">
		 <c:if test='${pass=="合格"}'>
			 <input type="button" class="btn save" value="合格"/>
	             </c:if>
	             <c:if test='${pass=="不合格"}'>
	             <input type="button" class="btn save" value="不合格"/>   
			</c:if>
			
		</div>
		</div>
	</form>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
	  <script type="text/javascript">
	  var basePath='<%=basePath%>';
    	$(document).ready(function(e) {
	$(".btn")
			.click(
					function() {
					var pass=$(this).val();
					var key = $("#bsimTestkey").val();
					var id= $("#id").val;
						$("form :input:.put3").trigger("blur");
						if($("form .error").length)
						{ 
							return;
						} 
						if (confirm("确认要执行吗？")) {

							var url = basePath
									+ "ea/bsimtest/ea_getBsimtestListByStatus.jspa?bsimTestkey="
									+ key;
							//判断pass取值
							if (pass == "合格") {
								$("form#mainForm").attr("target", "hidden")
										.attr("action", url + "&type=02");
							} else {
								$("form#mainForm").attr("target", "hidden")
										.attr("action", url + "&type=03");
							}
							document.mainForm.submit.click();
							document.mainForm.reset();
							token = 2;
						}
					});

});
function re_load() {
	window.opener.location.href=window.opener.location.href;
	window.close();

}
	  </script>

</body>

</html>