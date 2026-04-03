<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
    <title>场地分配${type}页面</title>
    	<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
    	<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script src="<%=basePath%>js/ea/production/cprocedure/fieldDistribution_edit.js"  type="text/javascript" ></script>
		<script language="javascript" type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<link rel="stylesheet" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"  type="text/css"/>
		<link rel="stylesheet" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"  type="text/css"/>
		<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css" />
		<script src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js"></script>
		<script src="<%=basePath%>js/tree/codebase/dhtmlxtree.js"></script>
		<link rel="STYLESHEET" type="text/css" href="<%=basePath%>js/tree/codebase/dhtmlxtree.css" />
		
		<script type="text/javascript">
			var basePath="<%=basePath%>";
			var orgId="${orgId}";
			var staffId="";
			var productId="";
			var treegr;
		</script>
		<style type="text/css">
			.ta .tdName{
				text-align: right;
			}
			.ta .tdContent{
				text-align: left;
			}
			.ta tr{
				height: 40px;
			}
		</style>
  </head>
  
  <body>
  <center>
  <form method="post" id="form" name="form" onsubmit="window.opener=null;window.close();">
  		<input type="submit" id="submit" name="submit" style="display: none;">
  		<div style="width: 500px;border: 1px solid #63B8FF;height: 600px;">
  			<div  style="background-color: #63B8FF;height:45px;text-align: center;">
  				<font size="5" color="#FFFFFF" style="position: relative;top: 10px;">场地分配交接单</font>
  			</div>
  			<div>
  				<table class="ta">
  					<tr>
  			
  						<td class="tdName" width="100px;">项目名称：</td>
  						<td width="30px;"></td>
  						<td class="tdContent" width="250px;"><input type="text" id="projectName" class="projectName succ" 
  								 value="${fieldDistribution.ppName}" disabled="disabled">
  							  <input type="hidden" name="fieldDistribution.fieldDistributionId" value="${fieldDistribution.fieldDistributionId}">
  							  <input type="hidden" name="fieldDistribution.ppId" value="${fieldDistribution.ppId}">
  							  <input type="hidden" name="fieldDistribution.ppName" value="${fieldDistribution.ppName}">
  							  <input type="hidden" name="fieldDistribution.productCode" value="${fieldDistribution.productCode}">
  							  <input type="hidden" name="fieldDistribution.status" value="${fieldDistribution.status}">
  							  <input type="hidden" name="fieldDistribution.fieldDistributionKey" value="${fieldDistribution.fieldDistributionKey}">
  							  <input type="hidden" class="fiveClear" name="fieldDistribution.fiveClear" value="${fiveClear}">
  						</td>
  					</tr>
  					<tr>
  						<td class="tdName">开始时间：</td>
  						<td></td>
  						<td class="tdContent"><input type="text" id="edate"  class="startDate succ" name="fieldDistribution.startTime"
  								onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${fieldDistribution.startTime}"></td>
  					</tr>
  					<tr>
					<td class="tdName">结束时间：</td>
  						<td></td>
  						<td class="tdContent"><input type="text"  id="sdate" class="endDate succ"  name="fieldDistribution.endTime"
  								onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${fieldDistribution.endTime}"></td>
  					</tr>
  					<tr>
  						<td class="tdName">职责：</td>
  						<td></td>
  						<td class="tdContent"><input type="text" id="duty" class="duty succ" disabled="disabled" value="${fieldDistribution.duty}">
  							 <input type="hidden" class="duty"  name="fieldDistribution.duty" value="${fieldDistribution.duty}"></td>
  					</tr>
  					<tr>
  						<td class="tdName">场地分配：</td>
  						<td></td>
  						<td class="tdContent"><input type="text" id="site" class="site succ"  name="fieldDistribution.siteAddress"
  									 placeholder="请填写场地地址" value="${fieldDistribution.siteAddress}"></td>
  					</tr>
  					<tr>
  						<td class="tdName">分配责任人：</td>
  						<td></td>
  						<td class="tdContent"><input type="text" id="satffName" class="staffName succ" placeholder="请选择责任人" value="${fieldDistribution.staffName}">
  							 <input type="hidden" class="staffName" name="fieldDistribution.staffName" value="${fieldDistribution.staffName}">
  							 <input type="hidden" class="staffId" name="fieldDistribution.staffId" value="${fieldDistribution.staffId}"></td>
  					</tr>
  					<tr>
  						<td class="tdName">分配时间：</td>
  						<td></td>
  						<td class="tdContent"><input type="text" id="distributionDate" class="distributionDate succ" disabled="disabled" value="${date}">
  							 <input type="hidden" class="distributionDate" value="${date}" name="fieldDistribution.distributionTime"></td>
  					</tr>
  					<tr style="height: 90px;">
  						<td class="tdName">备注：</td>
  						<td></td>
  						<td class="tdContent"><textarea style="width: 162px;height: 70px;overflow: auto;" id="remarks" class="remarks" name="fieldDistribution.remarks" value="${fieldDistribution.remarks}"></textarea></td>
  					</tr>
  				</table>
  			</div>
  			<div style="position: relative;top: 30px;">
  				<span><input type="button" id="button" value="提交保存" style="width: 150px;height: 30px;background-color: #63B8FF;"></span>
  			</div>
  		</div>
</form>
  	</center>
		<%------------------------------------部门树和人 ------------------------------------%>
		<form name="selectdeptForm" id="selectdeptForm" method="post"
			enctype="multipart/form-data">
			
			<input type="submit" name="submit" style="display: none" />
			<div class="jqmWindow jqmWindowcss1" style="top: 5%; left: 53%;"
				id="deptjqModel">
				<div class="content1" style="width: 100%; height: 400px;">
					<div class="contentbannb">
						<div class="drag">
							组织机构
						</div>
					</div>
					<table width="99%" height="33" id="searchdept"  border="0"
						align="center" cellpadding="0" cellspacing="0"
						style="margin-top: 5px; background: #FFFFFF;">
						<tr>
							<td width="100" align="right">
								员工姓名：
							</td>
							<td width="142">
								<input class="input" id="parameterrm"
									size="10" style="margin-left: 2px;" />
								<input type="hidden" id="selectdept"
									/>
								<input type="hidden" id="selectdeptname" />
								<input type="hidden" id="deptpos" />
							</td>
							<td height="33">
								<input type="button" class="btn02 JQueryreturns" id="searchdeptbtn" name="button7"
									value="查询" />
								<input type="button" class="btn02 JQueryreturns" id="qddept" name="button5"
									value="确定" />
								
								<input type="button" class="btn02 JQueryreturns" name="button4"
									value="关闭" />
									
			
							</td>
							<td width="80">
								<a id="dpsy" title="0">上一页</a>
							</td>
							<td width="80">
								<a id="dpxy" title="0">下一页</a>
							</td>
							<td width="100">
								<a id="dpzy">共&nbsp;&nbsp; <span style="color: red"
									id="dpzycount"></span>&nbsp;&nbsp;页 </a>
							</td>
						</tr>
					</table>
					<table width="99%" border="0" align="center" cellpadding="0"
						cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
						<tr>
							<td width="20%">
								<table width="100%" cellpadding="0" cellspacing="0">
								
									<tr id="menuTreeTrid-1" sizcache="1" sizset="0">
										<td>
										
											<div class="text_tree"
												style="overflow: auto; z-index: 99;width:170px; height: 280px;"><iframe src="<%=basePath%>page/ea/main/finance/invoicing/organizationtree.jsp?yanzheng=${zhuangtai}" width="250" height="270"></iframe></div>
											
										</td>
									</tr>
								</table>
							</td>
							<td width="80%" valign="top" align="left">
								<div 
									style="margin-top: 2px; height: 310px; width: 100%; overflow: auto;">
								<table width='98%' height='26' align='center' cellspacing='0'
									cellpadding='1' style='font-size:12px;' class='bannb_01'>
									<tr>
										<td height='24' align='left' valign='top' class='txt01'>&nbsp;点击选择员工</td>
									</tr>
								</table>
								<table width='99%' align='center' id='dptable' cellpadding='0'
									cellspacing='0' class='table'>
									<thead>
										<tr>
											<th height='21' align='center' width='30' bgcolor='#E4F1FA'>选择</th>
											<th align='center' bgcolor='#E4F1FA' width='30'>序号</th>
											<th align='center' bgcolor='#E4F1FA' width='70'>人员编号</th>
											<th align='center' bgcolor='#E4F1FA' width='70'>人员姓名</th>
											<th align='center' bgcolor='#E4F1FA' width='30'>性别</th>
											<th align='center' bgcolor='#E4F1FA' width='100'>出生日期</th>
											<th align='center' bgcolor='#E4F1FA' width='30'>籍贯</th>
											<th align='center' bgcolor='#E4F1FA' width='70'>手机号</th>
											<th align='center' bgcolor='#E4F1FA'>身份证</th>
										</tr>
									</thead>
									<tbody id="body_02dept"></tbody>
								</table>

							</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<s:token></s:token>
		</form>
  </body>
</html>
