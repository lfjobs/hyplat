<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>采购验货添加页面</title>
    <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=basePath%>js/ea/production/cprocedure/sourcing/sourcingInspection_ins.js"></script>	
	<script language="javascript" type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
	<link rel="stylesheet" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"  type="text/css"/>
	<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/admin_main111.css">
	<style type="text/css">
		span{
			display:-moz-inline-box;
			display:inline-block;
		}
	</style>
	<script type="text/javascript">
		var basePath="<%=basePath%>";
		var orgId="${organizationID}";
		var staffId="";
		var fiveClear="${fiveClear}"
	</script>
  </head>
  
  <body>
  <form method="post" id="form" name="form" >
  <input type="submit" id="submit" name="submit" style="display: none;">
  <iframe name="hidden"  style="display: none;"></iframe>
    <center>
    	<div style="border: 1px solid #999999;width: 60%;height: 70%;margin-top: 4%;">
    		<div style="background-color: #009DD9;width:100%;height:8%;font-size: 30px;color:#fff;padding-top: 2%;">采购验货</div>
    		<div style="width: 100%;height:20%;">
    			<table style="width: 100%;height: 100%;">
    				<tr style="height: 32%;">
    					<td style="width: 32%;">
    						<span style="width: 80px; text-align: right;">所&nbsp;属&nbsp;公&nbsp;司：</span>
    						<span><input type="text" class="inputbottom" style="width: 180px;" readonly="readonly" value="${c.companyName}"></span>
    					</td>
    					<td style="width: 32%;">
    						<input type="hidden" id="cashierBillsID" value="${c.cashierBillsID}" name="cashierBillsID">
    						<span  style="width: 80px; text-align: right;">单&nbsp;据&nbsp;编&nbsp;号：</span>
    						<span><input type="text" class="inputbottom" style="width: 180px;" readonly="readonly" value="${c.journalNum}"></span>
    					</td>
    					<td>
    						<span  style="width: 80px; text-align: right;">部&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;门：</span>
    						<span><input type="text" class="inputbottom" style="width: 180px;" readonly="readonly" value="${c.departmentName}"></span>
    					</td>
    				</tr>
    				<tr style="height: 32%;">
    					<td>
    						<span  style="width: 80px; text-align: right;">采&nbsp;&nbsp;&nbsp;购&nbsp;&nbsp;&nbsp;员：</span>
    						<span><input type="text" class="inputbottom" style="width: 180px;" readonly="readonly" value="${f.staffName}"></span>
    					</td>
    					<td>
    						<span  style="width: 80px; text-align: right;">采购员电话：</span>
    						<span><input type="text" class="inputbottom" style="width: 180px;" readonly="readonly" value="${f.staffPhone}"></span>
    					</td>
    					<td>
    						<span  style="width: 80px; text-align: right;">采&nbsp;购&nbsp; 日&nbsp;期：</span>
    						<span><input type="text" class="inputbottom" style="width: 180px;" readonly="readonly" value="${f.purchaseDate}"></span>
    					</td>
    				</tr>
    				<tr style="height: 32%;">
    					<td>
    						<span  style="width: 80px; text-align: right;">验&nbsp;&nbsp;&nbsp;货&nbsp;&nbsp;&nbsp;人：</span>
    						<span>
    							<input type="text" class="inputbottom" style="width: 180px;" id="staffsName" name="financialBill.staffsName"  readonly="readonly" value="${f.staffsName}">
    							<input type="hidden" name="financialBill.staffsID" id="staffsID">	
    						</span>
    					</td>
    					<td>
    						<span  style="width: 80px; text-align: right;">付&nbsp;款&nbsp;方&nbsp;式：</span>
    						<span><input type="text" class="inputbottom" style="width: 180px;" readonly="readonly" value="${f.paymentType}"></span>
    					</td>
    					<td>
    						<span  style="width: 80px; text-align: right;">验&nbsp;货&nbsp; 日&nbsp;期：</span>
    						<span><input type="text" class="inputbottom" style="width: 180px;" name="financialBill.examinegoodsDate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"></span>
    					</td>
    				</tr>
    			</table>
    		</div>	
    		<div style="height: 45%;width: 100%;" class="tableSample">
    			<table cellpadding="0" cellspacing="0" class="table"  style="width: 80%;float: left;">
	    			<thead>
	    				<tr height="30">
	    					<th><span style="width: 30px;overflow: hidden;">序号</span></th>
	    					<th><span style="width:80px;overflow: hidden;">物品编号</span></th>
	    					<th><span style="width: 73px;overflow: hidden;">物品名称</span></th>
	    					<th><span style="width: 40px;overflow: hidden;">数量</span></th>
	    					<th><span style="width: 50px;overflow: hidden;">合格数量</span></th>
	    					<th><span style="width: 40px;overflow: hidden;">单价</span></th>
	    					<th><span style="width: 50px;overflow: hidden;">总金额</span></th>
	    					<th><span style="width: 40px;overflow: hidden;">类别</span></th>
	    					<th><span style="width: 30px;overflow: hidden;">单位</span></th>
	    					<th><span style="width: 45px;overflow: hidden;">规格</span></th>
	    					<th><span style="width: 50px;overflow: hidden;">物流方式</span></th>
	    					<th><span style="width: 100px;overflow: hidden;">供应商</span></th>
	    					<th><span style="width: 91px;overflow: hidden;">供应商负责人</span></th>
	    					<th><span style="width: 78px;overflow: hidden;">负责人电话</span></th>
	    					<th><span style="width: 51px;overflow: hidden;">备注</span></th>
	    				</tr>
	    				</thead>
    				</table>
    				<div style="height: 90%;width: 100%;overflow: hidden;position: relative;top: -5.5px" class="tableOuter">
	    				<div style="height: 95%;overflow-y: auto;" class="tableInner">
		    				<table  cellpadding="0" cellspacing="0" class="table" style="float: left;">
			    				<tbody>
			    					<c:forEach items="${goodsList}" var="list" varStatus="a">
			    						<tr>
			    							<td>
			    								<input type="hidden" name="goodsBillsList[${a.index}].goodsBillsID" value="${list.goodsBillsID}">
			    								<span style="width: 30px;overflow: hidden;">${a.index+1}</span>
			    							</td>
			    							<td><span style="width: 80px;overflow: hidden;">${list.goodsNum}</span></td>
			    							<td><span style="width: 73px;overflow: hidden;">${list.goodsName}</span></td>
			    							<td><span style="width: 40px;overflow: hidden;">${list.quantity}</span></td>
			    							<td><input type="text" style="border: 0px;width: 50px;" name="goodsBillsList[${a.index}].isQualify"></td>
			    							<td><span style="width: 40px;overflow: hidden;">${list.price}</span></td>
			    							<td><span style="width: 50px;overflow: hidden;">${list.money}</span></td>
			    							<td><span style="width: 40px;overflow: hidden;">${list.typeID}</span></td>
			    							<td><span style="width: 30px;overflow: hidden;">${list.goodsVariableID}</span></td>
			    							<td><span style="width: 45px;overflow: hidden;">${list.standard}</span></td>
			    							<td><span style="width: 50px;overflow: hidden;">${list.logistics}</span></td>
			    							<td><span style="width: 100px;overflow: hidden;">${list.supplier}</span></td>
			    							<td><span style="width: 91px;overflow: hidden;">${list.supplierStaffName}</span></td>
			    							<td><span style="width: 78px;overflow: hidden;">${list.supplierStaffTelephone}</span></td>
			    							<td><span style="width: 51px;overflow: hidden;">${list.remark}</span></td>
			    						</tr>
			    					</c:forEach>
							</tbody>
		    			</table>
	    			</div>
	    		</div>
    		</div>
    		<hr color="#DADCDB"/>
    		<div style="width: 100%;height:6%;">
    			<span style="float: left;font-size: 14px;">
    				<span>制单人：</span>
    				<span><input type="text" class="inputbottom" style="width: 150px;" readonly="readonly" value="${staffName}"></span>
    			</span>
    			<span style="float: right;font-size: 14px;">
    				<span>制单时间：</span>
    				<span><input type="text" class="inputbottom" style="width: 150px;" readonly="readonly" value="${fn:substring(date,0,10)}"></span>
    			</span>
    		</div>
    		<div>
    			<span><input type="button" value="提交" style="width: 60px;height: 30px;" class="operation"></span>
    			<span style="width: 100px;"></span>
    			<span><input type="button" value="关闭" style="width: 60px;height: 30px;" class="operation"></span>
    		</div><br/>
    		<div style="color:red">警告：检验物品时，未合格的物品将自动转移到退货库！！！</div>
    	</div>
    </center>
  <s:token></s:token>
  </form>
  
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
			</form>
  </body>
</html>
