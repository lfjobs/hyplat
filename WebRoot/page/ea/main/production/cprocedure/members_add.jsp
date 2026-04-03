<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="java.util.Date"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />

<title>人员分配交接单</title>

<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<script src="<%=basePath%>/js/ea/validate.js" type="text/javascript"></script>
			<link href="<%=basePath%>/css/ea/validate.css" rel="stylesheet"type="text/css" />

<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/ea/production/production.css" />
<link rel="stylesheet" type="text/css"  href="<%=basePath%>css/admin_main111.css" />

<script src="<%=basePath%>js/ea/production/cprocedure/member_add.js"
	type="text/javascript"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/popLayer/css/popstyle.css" />
<script src="<%=basePath%>js/popLayer/js/popLayer.js"
	type="text/javascript"></script>
<script src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js"></script>
<script src="<%=basePath%>js/tree/codebase/dhtmlxtree.js"></script>
<link rel="STYLESHEET" type="text/css"
	href="<%=basePath%>js/tree/codebase/dhtmlxtree.css" />
<script language="javascript" type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>

<script type="text/javascript">

var basePath="<%=basePath%>";
var seqnum = 2;
var notoken = 0;
var token=0;
var fiveClear="${fiveClear}";
var type="${type}",category="${category}";
</script>

</head>
<body>
	<form id="mainForm" name="mainForm" method="post"  onsubmit="return validate_form(this)">
		<s:hidden name="memberAllot.makey"></s:hidden>
	<s:hidden name="memberAllot.maid"></s:hidden>
		<input type="hidden" name="memberAllot.fiveClear" value="${fiveClear}">
	<s:token></s:token>
		<input type="submit" name="submit" style="display:none;">
		<div class="main">
			<div class="top">人员分配交接单</div>
			<div class="body">
				<input type="hidden" name="memberAllot.category" value="${category}">
				<input type="hidden" name="memberAllot.type" value="${type}">
				<div class="showinfo show">
					<table id="productbl">
						<tr>
							<td align="right" style="width:38%;" class="t">项目产品名称：</td>
							<td>
								<s:textfield cssClass="inputtext goodsName input3" name="memberAllot.productName" readOnly="readOnly" cssStyle="width:40%;" id="idd"></s:textfield>
							<input
								type="button" onclick="pop('products')"  id="selectpr"  value="选择" class="btn01" />
							</td>
						</tr>
						<tr>
							<td align="right">产品编号：</td>
							<td>
							<s:hidden name="memberAllot.productID" cssClass="inputtext1 ppID"></s:hidden>
								<s:textfield cssClass="inputtext productCode" name="memberAllot.productCode"  readOnly="readOnly" ></s:textfield>
								</td>
						</tr>
						<tr>
							<td align="right">职责：</td>
							<td>
								<s:textfield cssClass="inputtext1 put3" name="memberAllot.duty" id="auditoroption" >
				 				
								</s:textfield>
								
								</td>
						</tr>
						<tr>
							<td align="right">开始时间：</td>
							<td>
								<s:textfield cssClass="inputtext1 put3" name="memberAllot.startDate" 
								onFocus="WdatePicker({startDate:'%y-%M-01 ',dateFmt:'yyyy-MM-dd ',alwaysUseStartDate:true})"
								></s:textfield>
								</td>
						</tr>


						<tr>
							<td align="right">结束时间：</td>
							<td>
								<s:textfield cssClass="inputtext1 put3" name="memberAllot.endDate" 
								onFocus="WdatePicker({startDate:'%y-%M-01 ',dateFmt:'yyyy-MM-dd ',alwaysUseStartDate:true})"
								></s:textfield>
								</td>
						</tr>

						<tr>
							<td align="right">分配责任人：</td>
							<td>
								<s:textfield cssClass="inputtext names input3" cssStyle="width:40%;" name="memberAllot.allotorName" readonly="true"></s:textfield>
								<s:hidden cssClass="inputtext ids" name="memberAllot.allotorID" ></s:hidden>
								
								
								<input
								type="button" onclick="pop('members')"  value="选择" class="btn01 fpr" readonly="readonly"/>
								
								</td>
						</tr>
						<tr>
							<td align="right">交接人<span class="xx">*</span>：</td>
							<td>
								<s:textfield cssClass="inputtext names input3" cssStyle="width:40%;" name="memberAllot.transferName" readonly="true"  id="idd"></s:textfield>
								<s:hidden cssClass="inputtext ids" name="memberAllot.transferID" ></s:hidden>
								
								
								<input
								type="button" onclick="pop('members')"     value="选择" class="btn01 fpr" readonly="readonly"/>
								
								</td>
						</tr>
						<tr>
							<td align="right">接班人<span class="xx input3">*</span>：</td>
							<td>
								<s:textfield cssClass="inputtext names input3" cssStyle="width:40%;" name="memberAllot.receiverName" readonly="true" id="idd" ></s:textfield>
								<s:hidden cssClass="inputtext ids" name="memberAllot.receiverID" ></s:hidden>
								
								
								<input
								type="button" onclick="pop('members')"  value="选择" class="btn01 fpr" readonly="readonly"/>
								
								</td>
						</tr>
						
						<tr>
							<td align="right">分配时间：</td>
							<td>
								<s:textfield cssClass="inputtext1 put3" name="memberAllot.allotDate" 
								onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})"
								></s:textfield>
								</td>
						</tr>


						<tr>
							<td align="right">分配备注：</td>
							<td>
							<s:textarea name="memberAllot.remark" cssClass="inputtext1 ckTextLength" cssStyle="height: 50px;border: 1px solid #ccc;" id="auditoroption"  maxlength="250">
							
							</s:textarea>
							
							
							</td>
						</tr>
					</table>

				</div>
			</div>

		</div>
		<div class="bottom">
			<input type="button" class="btn save" value="提交保存" />
		</div>
		</div>
	</form>


	<!-- 行业 -->

	<div id="industry" class="popMain">
		<div class="choose-box">
			<div class="choosetitle">
				<span>选择行业</span>
			</div>
			<div class="chooseborder">
				<div id="industryTree" style=" border: 0px solid #000000;"></div>


			</div>
			<div class="choose-box-bottom">
				<input type="botton" onclick="hide('industry')" value="关闭" />
			</div>
		</div>
	</div>


	<!-- 项目产品分类-->

	<div id="project" class="popMain">
		<div class="choose-box">
			<div class="choosetitle">
				<span>选择项目产品分类</span>
			</div>
			<div class="chooseborder">
				<div id="projectTree" style=" border: 0px solid #000000;"></div>


			</div>
			<div class="choose-box-bottom">
				<input type="botton" onclick="hide('project')" value="关闭" />
			</div>
		</div>
	</div>
	
		
	<!-- 选择产品 -->

	<div id="products" class="popMain">
		<div class="choose-box">
			<div class="choosetitle">
				<span>选择项目产品</span>
			</div>
			<div class="chooseborder">
		        <table width="99%" height="33" id="searchpro"     border="0"
						align="center" cellpadding="0" cellspacing="0"
						style="margin-top: 5px; background: #FFFFFF;">
						<tr>
							<td width="100" align="right">
								产品编码或名称：
							</td>
							<td width="110">
								<input name="parameter" class="input" id="parameter" size="10"
									style="margin-left: 2px;" />
							</td>
							<td height="33">
								<input type="button" class="btn01" id="searchProduct"
									name="button7" value="查询"/>
								<input type="button" class="btn01" id="selectProduct"
									name="button5" value="确定" />
			
								
								
							</td>
							<td width="80">
								<a id="wpsyp" title="0" style="cursor:pointer;">上一页</a>
							</td>
							<td width="80">
								<a id="wpxyp" title="0" style="cursor:pointer;">下一页</a>
							</td>
							<td width="100">
								<a id="wpzy">共&nbsp;&nbsp; <span style="color: red"
									id="wpzycountp"></span>&nbsp;&nbsp;页 </a>
							</td>
						</tr>
					</table>
					<table width="99%" border="0" align="center" cellpadding="0"
						cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
						<tr>
							<td width="83%" valign="top" align="left">
								<div id="body_03"
									style="margin-top: 2px; height:340px;width:100%;border:1px solid #ccc; overflow: auto;">
								</div>
							</td>
						</tr>
					</table>

			</div>
			<div class="choose-box-bottom">
				<input type="botton" onclick="hide('products')" value="关闭" />
			</div>
		</div>
	</div>
	
	
		
	<!-- 选择人员 -->
	<div id="members" class="popMain">
		<div class="choose-box">
			<div class="choosetitle">
				<span>选择人员</span>
			</div>
			<div class="chooseborder">
				<div id="goodsTree" style=" border: 0px solid #000000;"></div>
		        <table width="99%" height="33" id="searchuser"  border="0"
						align="center" cellpadding="0" cellspacing="0"
						style="margin-top: 5px; background: #FFFFFF;">
						<tr>
							<td width="100" align="right">
								姓名：
							</td>
							<td width="110">
								<input name="contactUserID" class="input" id="contactUserID"
									size="10" style="margin-left: 2px;" />
							</td>
							<td height="33">
								<input type="button" class="btn01" id="searchuu"
									name="button7" value="查询"/>
								<input type="button" class="btn01" id="qduser"
									name="button5" value="确定" />
								<input type="button" class="btn01 xzgr" name="button" value="新增" />
			
							   <input type="hidden" name="parms" id="grparms" />
								
							</td>
							<td width="80">
								<a id="grsy" title="0" style="cursor:pointer;">上一页</a>
							</td>
							<td width="80">
								<a id="grxy" title="0" style="cursor:pointer;">下一页</a>
							</td>
							<td width="100">
								<a id="grzy">共&nbsp;&nbsp; <span style="color: red"
									id="grzycount"></span>&nbsp;&nbsp;页 </a>
							</td>
						</tr>
					</table>
					<table width="99%" border="0" align="center" cellpadding="0"
						cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
						<tr>
							<td width="16%">
								<table width="100%" cellpadding="0" cellspacing="0">
									<tr id="menuTreeTrid-1" sizcache="1" sizset="0">
										<td>
											<div id="grTree" 
												style="overflow: scroll; z-index: 99; width:180px;height: 340px;border:1px solid #ccc;"></div>
										</td>
									</tr>
								</table>
							</td>
							<td width="83%" valign="top" align="left">
								<div id="body_02cu"
									style="margin-top: 2px; height:340px;width:100%;border:1px solid #ccc;border-left:none; overflow: auto;">
								</div>
							</td>
						</tr>
					</table>

			</div>
			<div class="choose-box-bottom">
				<input type="botton" onclick="hide('members')" value="关闭" />
			</div>
		</div>
	</div>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
</body>
</html>