<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>生产组装</title>
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<script type="text/javascript"
	src="<%=basePath%>js/ea/production/cprocedure/process/productionAssembly_order_add.js"></script>
<script language="javascript" type="text/javascript"
	src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/css/admin_main111.css">

<script type="text/javascript">
		var basePath="<%=basePath%>";
		var goodsBillsID="${utboundOrderVo.goodsbillsid}";
		var ppId="${utboundOrderVo.ppId}";
		var orgId="${orgId}",staffId="";
		$(function(){
			$(".table_body").css("width",(parseInt($(".table_title").width())+17)+"px");
		})
	</script>
<style type="text/css">
#tableR {
	border-right: 1px solid #a8c7ce;
	border-bottom: 1px solid #a8c7ce;
}

#tableR td {
	border-left: 1px solid #a8c7ce;
	border-top: 1px solid #a8c7ce;
}
.span{
display:-moz-inline-box;
display:inline-block;
}
</style>
</head>
<body>
	<form method="post" id="assForm" name="assForm">
		<input type="submit" id="assSubmit" name="assSubmit" style="display: none;">
		<input type="hidden" class="productPID" name="productionAssembly.productPID" value="${goods.ppID}">
		<input type="hidden" class="productID" name="productionAssembly.productID" value="${pp.ppID}"> 
		<input type="hidden" class="proAssemblyID" name="productionAssembly.proAssemblyID" value="${ass.proAssemblyID}">
		<input type="hidden" class="cashierBillsID" name="productionAssembly.cashierBillsID" value="${goods.cashierBillsID}"> 
		<input type="hidden" class="goodsBillsID" name="utboundOrderVo.goodsbillsid" value="${goods.goodsBillsID}">
		
		<iframe name="hidden" style="display: none;"></iframe>
		<div
			style="width: 35%;height:90%;background-color: #fff;position: absolute;left: 32.5%;border: 1px solid #000;">
			<div
				style="width: 100%;height: 6%;background-color: #009DD9;color: #fff;text-align: center;padding-top: 6%;font-size: 24px;">
				生产组装出库量设置</div>
			<div style="height: 8%;width: 100%;;">
				<div class="dd" style="display: none;">
					<c:forEach items="${list}"   var="l"  varStatus="a">
						<p>${l}</p>
					</c:forEach>
				</div>
				<font
					style="font-weight:bold;position: relative;top: 20px;left: 20px;font-size: 20px;"
					class="productName"> 
				
				</font>
			</div>
			<hr width="100%"
				style="height:1px;border:none;border-top:1px dotted  #000;" />
			<div style="height: 6%;width: 100%;;">
				<table style="width: 100%;height:100%">
					<tr>
						<td style="text-align: right;" width="30%">产品名称：</td>
						<td style="text-align: left;"><input type="text"
							style="width: 100%;height:100%;border: 0px;" class="goodsName"
							readonly="readonly" name="productionAssembly.goodsName"
							value="${pp.goodsName}"></td>
					</tr>
				</table>
			</div>
			<hr width="100%"
				style="height:1px;border:none;border-top:1px dotted  #000;" />
			<div style="height: 6%;width: 100%;;">
				<table style="width: 100%;height:100%"">
					<tr>
						<td style="text-align: right;" width="30%">结构数量：</td>
						<td style="text-align: left;"><input type="text"
							style="width: 100%;height:100%;border: 0px;" class="sQuantity"
							readonly="readonly" name="productionAssembly.structureQuantity"
							value="${pp.quantity}"></td>
					</tr>
				</table>
			</div>
			<hr width="100%"
				style="height:1px;border:none;border-top:1px dotted  #000;" />
			<div style="height: 6%;width: 100%;;">
				<table style="width: 100%;height:100%"">
					<tr>
						<td style="text-align: right;" width="30%">库存数量：</td>
						<td style="text-align: left;"><input type="text"
							style="width: 100%;height:100%;border: 0px;" class="iQuantity"
							readonly="readonly"
							value="${inv.invenQuantity==null?0:inv.invenQuantity}"></td>
					</tr>
				</table>
			</div>
			<hr width="100%"
				style="height:1px;border:none;border-top:1px dotted  #000;" />
			<div style="height: 6%;width: 100%;;">
				<table style="width: 100%;height:100%"">
					<tr class="operation">
						<td style="text-align: right;" width="30%">${ass==null?'生产数量':'原生产数量'}
							：</td>
						<td style="text-align: left;"><c:if test="${ass==null}">
								<div>
									<input type="text"
										style="width: 100%;height:100%;border: 0px;border-bottom: 1px solid #AEADA7;ime-mode:disabled"
										class="outQuantity  number"
										name="productionAssembly.outgoingQuantity">
								</div>
							</c:if> <c:if test="${ass!=null}">
								<div>
									<input type="text" style="width: 30%;height:100%;border: 0px;"
										class="oQuantity" readonly="readonly"
										value="${ass.outgoingQuantity}">&nbsp;&nbsp;&nbsp;新增生产数量：
									<input type="text"
										style="width: 40%;height:100%;border: 0px;border-bottom: 1px solid #AEADA7;ime-mode:disabled"
										class="outQuantity number"
										name="productionAssembly.outgoingQuantity">
								</div>
							</c:if></td>
					</tr>
				</table>
			</div>
			<hr width="100%"
				style="height:1px;border:none;border-top:1px dotted  #000;" />
			<div style="height: 6%;width: 100%;;">
				<table style="width: 100%;height:100%"">
					<tr>
						<td style="text-align: right;" width="30%">起止时间：</td>
						<td style="text-align: left;"><c:if test="${ass==null}">
								<input type="text"
									style="width: 40%;height:100%;border: 0px;border-bottom: 1px solid #AEADA7;"
									class="startTime"
									onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
									name="productionAssembly.startTime" readonly="readonly">
							</c:if> <c:if test="${ass!=null}">
								<input type="text" style="width: 40%;height:100%;border: 0px;"
									class="startTime" name="productionAssembly.startTime"
									readonly="readonly" value="${ass.startTime}">
							</c:if> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;---&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="text"
							style="width: 40%;height:100%;border: 0px;border-bottom: 1px solid #AEADA7;"
							value="${ass.endTime}" class="endTime"
							onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
							name="productionAssembly.endTime" readonly="readonly" /></td>
					</tr>
				</table>
			</div>
			<hr width="100%"
				style="height:1px;border:none;border-top:1px dotted  #000;" />
			<div style="height: 10%;width: 100%;;">
				<table style="width: 100%;height:100%"">
					<tr>
						<td style="text-align: right;" width="30%">生产日志：</td>
						<td style="text-align: left;">
							<textarea style="width: 100%;height: 40%;border: 0px;border-bottom: 1px solid #AEADA7;"
								name="productionAssembly.journal" readonly="readonly"
								class="teaching"></textarea> 
							<input type="text" style="width: 100%;height: 30%;border: 0px;" class="studentLearningLog"
							 readonly="readonly" value="历史日志：${ass.journal}" 
							 			name="${ass.journal}"></td>
					</tr>
				</table>
			</div>
			<hr width="100%"
				style="height:1px;border:none;border-top:1px dotted  #000;" />
			<div style="height: 6%;width: 100%;;">
				<table style="width: 100%;height:100%"">
					<tr>
						<td style="text-align: right;" width="30%">责任人：</td>
						<td style="text-align: left;"><c:if test="${ass==null}">
								<input type="text"
									style="width: 100%;height:100%;border: 0px;border-bottom: 1px solid #AEADA7;"
									class="teachingSupervisor staffName"
									name="productionAssembly.teachingSupervisor">
									<input type="hidden"
									style="width: 100%;height:100%;border: 0px;border-bottom: 1px solid #AEADA7;"
									class="teachingSupervisor staffID"
									name="productionAssembly.teachingSupervisorID">
							</c:if> <c:if test="${ass!=null}">
								<input type="text" style="width: 100%;height:100%;border: 0px;"
									class="teachingSupervisor"
									name="productionAssembly.teachingSupervisor"
									value="${ass.teachingSupervisor}" readonly="readonly">
							</c:if></td>
					</tr>
				</table>
			</div>
			<hr width="100%"
				style="height:1px;border:none;border-top:1px dotted  #000;" />
			<div style="height: 8%;width: 100%;;">
				<table style="width: 100%;height:100%"">
					<tr>
						<td style="text-align: right;" width="30%">备注：</td>
						<td style="text-align: left;"><textarea
								style="width: 100%;height: 100%;border: 0px;border-bottom: 1px solid #AEADA7;"
								class="remarks" name="productionAssembly.remarks"
								value="${ass.remarks}"></textarea></td>
					</tr>
				</table>
			</div>
			<hr width="100%"
				style="height:1px;border:none;border-top:1px dotted  #000;" />
			<div
				style="height: 6%;background-color: #AEADA7;text-align: center;padding-top: 3.5%;font-size: 24px;
				color: #fff;cursor:pointer;position: relative;top: 7px;"
				id="save">保存当前信息</div>
		</div>
	</form> 
	<div style="position: absolute;top:0%;width: 100%;height:100%;background: rgba(0,0,0, 0.5);display: none;">
		<div style="width: 60%;height:55%;background-color: #CAC7AF;position: relative;
				left: 20%;top: 15%;border:3px outset #fff;" class="coachPopup">
			<div style="width: 100%;height: 12%;background-color: #009DD9;text-align: center;">
				<font color="#fff" style="font-size: 30px;position: relative;top: 25%;" class="journal_title"></font>
			</div>
			<hr color="#fff"/>
			<div style="position: relative;left: 10%;">
					<font style="font-size: 15px;">新添日志：</font>	
			</div>
			<div style="width: 80%;height: 28%;background-color: #fff;position: relative;left: 10%;">
				<textarea style="width: 100%;height:100%;" class="journal_content"></textarea>
			</div>
			<div style="position: relative;left: 10%;top: 2%;">
					<font style="font-size: 15px;">历史日志：</font>	
			</div>
			<div style="width: 80%;height:35%;position: relative;left: 10%;top: 2%;overflow: hidden;"
					 class="journal_content_outer">
				<div style="height:100%;overflow: auto;" class="journal_content_inner"></div>
			</div>
			<div style="width: 100%;height:9%;background-color: #7D7F7A;position: relative;top: 3%;
					border-top: 2px ridge #fff;">
				<span style="width: 49.5%;height:100%;color: #000;border-right:2px ridge #fff;
							font-size: 25px;text-align: center;" class="span journal_Determine">
						<font style="position: relative;top: 8px;">
							确&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;定
						</font>
				</span>
				<span style="width: 49%;height:100%;color: #000;font-size: 25px;
						text-align: center;" class="span journal_Close">
					 <font style="position: relative;top: 8px;">
					 	关&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;闭
					 </font>
				</span>
			</div>
		</div>
	</div>
	
	<div class="jqmWindow jqmWindowcss1" style="top: 20%; left: 23%;position: absolute;display: none;"
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
								<input type="button" class="btn02 turns" id="searchdeptbtn" name="button7"
									value="查询" />
								<input type="button" class="btn02 JQueryreturns turns" id="qddept" name="button5"
									value="确定" />
								
								<input type="button" class="btn02 JQueryreturns turns" name="button4"
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
</body>
</html>
