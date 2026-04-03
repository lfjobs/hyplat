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
    <title>设置生产量</title>
	<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=basePath%>js/ea/production/cprocedure/process/setProduction_data.js"></script>	
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
		var orgId="<%=session.getAttribute("organizationID")%>";
		var staffId="";
		var productID="";
		var amount="${productionAmount.amount}";
		var category="${category}";
		var fiveClear="${fiveClear}";
	</script>
  </head>
  
  <body>
    <form method="post" id="form" name="form" >
  <input type="submit" id="submit" name="submit" style="display: none;">
  <iframe name="hidden"  style="display: none;"></iframe>
    <center>
    	<div style="border: 1px solid #999999;width: 60%;height: 70%;margin-top: 4%;">
    		<div style="background-color: #009DD9;width:100%;height:8%;font-size: 30px;color:#fff;padding-top: 2%;">设置生产量</div>
    		<div style="width: 100%;height:17%;">
				<input type="hidden" id="productionAmountID" name="productionAmount.productionAmountID" value="${productionAmount.productionAmountID}">
				<input type="hidden" id="productionAmountKey" name="productionAmount.productionAmountKey" value="${productionAmount.productionAmountKey}">
    			<input type="hidden" name="productionAmount.category" value="${category}">
    			<input type="hidden" name="productionAmount.fiveClear" value="${fiveClear}">
    			<table style="width: 100%;height: 100%;">
    				<tr style="height: 48%;">
    					<td style="width: 32%;">
    						<span style="width: 80px; text-align: right;">所&nbsp;属&nbsp;公&nbsp;司：</span>
    						<input type="hidden" name="productionAmount.companyID" value="${productionAmount.companyID}">
    						<span><input type="text" class="inputbottom" style="width: 180px;" readonly="readonly" value="${productionAmount.companyName}" name="productionAmount.companyName"></span>
    					</td>
    					<td style="width: 32%;"></td>
    					<td>
    						<span  style="width: 80px; text-align: right;">生产批次号：</span>
    						<input type="hidden" name="productionAmount.status" value="${productionAmount.status}">
    						<span><input type="text" class="inputbottom" style="width: 180px;" readonly="readonly" value="${productionAmount.batchNumber}" id="batchNumber" name="productionAmount.batchNumber"></span>
    					</td>
    				</tr>
    				<tr style="height: 48%;">
    					<td>
    						<span  style="width: 80px; text-align: right;">产&nbsp;品&nbsp;名&nbsp;称：</span>
    						<input type="hidden" id="productID" name="productionAmount.productID" value="${productionAmount.productID}">
    						<span><input type="text" class="inputbottom" style="width: 180px;" value="${productionAmount.goodsName}" readonly="readonly" id="goodsName" name="productionAmount.goodsName"></span>
    					</td>
    					<td>
    						<span  style="width: 80px; text-align: right;">责&nbsp;&nbsp;&nbsp;任&nbsp;&nbsp;&nbsp;人：</span>
    						<input type="hidden" id="staffsID" name="productionAmount.staffID" value="${productionAmount.staffID}">
    						<span><input type="text" class="inputbottom" style="width: 180px;" readonly="readonly" value="${productionAmount.staffName}" id="staffsName" name="productionAmount.staffName"></span>
    					</td>
    					<td>
   							<span  style="width: 80px; text-align: right;">设&nbsp;置&nbsp;日&nbsp;期：</span>
    						<span><input type="text" class="inputbottom" style="width: 180px;" readonly="readonly" value="${productionAmount.setDate}" name="productionAmount.setDate" id="setDate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"></span>
    					</td>
    				</tr>
    			</table>
    		</div>	
    		<div style="height: 45%;width: 100%;" class="tableSample">
    			<table cellpadding="0" cellspacing="0" class="table"  style="width: 80%;float: left;">
	    			<thead>
	    				<tr height="30">
	    					<th><span style="width:320px;overflow: hidden;">物品结构</span></th>
	    					<th><span style="width: 200px;overflow: hidden;">物品编号</span></th>
	    					<th><span style="width: 170px;overflow: hidden;">结构数量</span></th>
	    					<th><span style="width: 169px;overflow: hidden;">生产数量</span></th>
	    				</tr>
	    				</thead>
    				</table>
    				<div style="height: 90%;width: 100%;overflow: hidden;position: relative;top: -5.5px" class="tableOuter">
	    				<div style="height: 95%;overflow-y: auto;" class="tableInner">
		    				<table  cellpadding="0" cellspacing="0" class="table" style="float: left;" id="tableBody">
			    				<tbody>
			    					
			    				</tbody>
		    			</table>
	    			</div>
	    		</div>
    		</div>
    		<hr color="#DADCDB"/>
    		<div style="width: 100%;height:6%;">
    			<span style="float: left;font-size: 14px;">
    				<span>制单人：</span>
    				<input type="hidden" value="${productionAmount.singleID}" id="singleID" name="productionAmount.singleID">
    				<span><input type="text" class="inputbottom" style="width: 150px;" readonly="readonly" value="${productionAmount.singleName}" id="singleName" name="productionAmount.singleName"></span>
    			</span>
    			<span style="float: right;font-size: 14px;">
    				<span>制单时间：</span>
    				<span><input type="text" class="inputbottom" style="width: 150px;" readonly="readonly" value="${productionAmount.singleDate}" id="singleDate" name="productionAmount.singleDate"></span>
    			</span>
    		</div>
    		<div>
    			<span><input type="button" value="提交" style="width: 60px;height: 30px;" class="operation"></span>
    			<span style="width: 100px;"></span>
    			<span><input type="button" value="关闭" style="width: 60px;height: 30px;" class="operation"></span>
    		</div>
    	
    		
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
		<div id="productCoatings" style="position: absolute;top:0px;left:0px;width: 100%;height: 100%;background-color: rgba(0,0,0, 0.5);display: none;">
			<div id="productSubject" style="width: 60%;height:60%;position: relative;left: 20%;top:20%;background-color: #B9D2FE;border: 1px solid #639CFD;">
					<div style="height:5%;width: 100%;background: url(js/jqModal/css/images/headbg.gif) repeat-x top;border-bottom:1px solid #99bbe8;color:#15428b;font-weight:bold;padding-top: 1%;">
						产品列表
					</div>
					<div style="height:6%;width: 95%;font-weight:bold;padding-top: 1%;position: relative;left: 2.5%;top:2%;background-color: #ffffff;">
						<input type="button" value="确定" id="determine"><input type="button" value="关闭" id="close">
					</div>
					<div style="height: 6%;width: 100%;background: url(images/admin_images/bg_01.gif) repeat-x;position: relative;top: 4%;padding-top: 0.5%;">
						点击选择产品
					</div>
					<div style="height: 76%;width: 100%;background-color: #dae7f6;position: relative;top:2%;">
						<table class="table" cellpadding="0" cellspacing="0" height="17%" width="100%;">
							<thead style="width: 100%；">
								<tr height="30px" style="width:100%;" id="ttr">
									<th width='6%'>选择</th>
									<th width='22%'>所属行业</th>
									<th width='22%'>产品名称</th>
									<th width='18%'>产品编号</th>
									<th width='13%'>所属科目</th>
									<th width='10%'>价格</th>
									<th>备注</th>
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
						 <div style="width: 100%;height:91%;overflow: hidden;position: relative;top: -2%;" class="tableOuter" > 
							<div class="tableInner" style="height:100%;overflow: auto;">
								<table class="table" cellpadding="0" cellspacing="0" id="productTable">
									<tbody></tbody>
								</table>
							</div>
						</div> 
					</div>
			</div>
		</div>
  </body>
</html>
