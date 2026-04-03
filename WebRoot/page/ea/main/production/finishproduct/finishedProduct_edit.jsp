<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="/struts-tags"  prefix="s"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>修改成品出库单</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/admin_main111.css" /> 
	<link rel="stylesheet" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"  type="text/css"/>
	<link rel="stylesheet" href="<%=basePath%>/js/jqueryplus/treeview/jquery.treeview.css" />
	<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/ea/production/finishproduct/finishedProduct_edit.js"></script>
	<script language="javascript" type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
	<script src="<%=basePath%>/js/jqueryplus/treeview/jquery.treeview.js" type="text/javascript"></script>
	<script src="<%=basePath%>js/tree/codebase/dhtmlxtree.js"></script>
	<link rel="STYLESHEET" type="text/css" href="<%=basePath%>js/tree/codebase/dhtmlxtree.css" />
	
<style type="text/css">
	.ttable input{
	width: 140px;
	}
	.font{
	color: #6F6B70;}
	#examine td{
	font-size: 2;
	color: #6F6B70;
	}
	#logistics{
	width: 100px;}
	#ckaadTree{
	border: 1px solid #a8c7ce;
	height: 300px;
	overflow: auto;}
	a:hover{
	cursor:pointer;}
	input.bor{
	width: 100%;
	border: 0px;}
</style>
<script type="text/javascript">
var basePath="<%=basePath%>";
var type="${type}";
var orgId="${orgId}";
var status=0;
var goodsId="";
var staffId="";
var search2=0;
var select=parseInt("${goods.size()}");
var select2=parseInt("${goods.size()}");
var r=0;
var irr=0;
var treeid;
var treename;
var token = 0;
var depotName="${fin.drdepotName}";
</script>
  </head>
  <body>
  <div style="width: 100%;text-align:left;">
				<table width="100%" border="0" align="center" cellpadding="0"
					cellspacing="0" style="background:#dbe5f1;margin-top:1px;margin-bottom: 1px;border-bottom:1px solid #99bbe8;">
					<tr>
						<td align="left" style="height:24px;">
						<nobr>
						<input type="button" class="menubtn butt" id="preservation" value="保存" /><input type="button" 
						class="menubtn butt" id="addgoodtr" value="增加一行"/><input type="button" 
						class="menubtn butt grey" id="delgoodtr" value="删除新增行" disabled="disabled"/><input type="button" 
						class="menubtn butt grey" id="printPreview" value="打印预览" disabled="disabled" /><input type="button" 
						class="menubtn butt grey" id="close" value="关闭"  disabled="disabled"/>
						</nobr>	
						</td>
					</tr>
				</table>
				</div>
	<form method="post" id="form" name="form">
		<input type="submit" id="submit" name="submit" style="display: none;">
    <div>
    	<div style="position: relative;top: 30px;text-align: center;width: 900px;">
    		<span><font size="5" style="font-weight:bold;color: #15428b">成品移库单</font></span>
    	</div>
    	<div style="position: relative;top: 80px;text-align: center;width: 900px;">
    		<table style="width: 100%;"	class="ttable">
   				<tr height="20">
   					<td width="30px"><input type="hidden" name="utboundOrderVo.cashierbillsid" value="${ca.cashierBillsID}"></td>
   					<td width="210px"><span><font class='font' size="2">公司名称：</font></span>
    						<span><input type="text" class="inputbottom" value="${ca.companyName}" readonly="readonly"></span></td>
   					<td width="350px" align="center"><span><font class='font' size="2">对方科目：</font></span>
    						<span><input type="hidden" value="${fin.subjectID}" id="subjectsid" name="utboundOrderVo.subjectID" />
								 <input type="hidden" id="subjectscode" name="subjectscode">
							     <input type="text" readonly="readonly" value="${fin.subjectName}" 
										class="panduan inputbottom"  id="tosubjects" name="utboundOrderVo.subjectName" /></span></td>
   					<td width="270"><span><font class='font' size="2">单据编号：</font></span>
    						<span><input type="text" class="inputbottom" value="${ca.journalNum}" name="utboundOrderVo.journalnum" readonly="readonly" id="journalnum"></span></td>
   				</tr>
    			<tr height="20px;">
    				<td></td>
    				<td><span><font class='font' size="2">出库日期：</font></span>
    						<span><input type="text" class="inputbottom cashiDate date"  value="${fin.purchaseDate}" 
    						name="utboundOrderVo.purchaseDate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"></span></td>
    				<td align="center"><span><font class='font' size="2">物流方式：</font></span>
							<select name="utboundOrderVo.logisticsType" style="width:100px;">
								<option value="${fin.logisticsType}">${fin.logisticsType}</option>			
							<c:forEach items="${logisticsList}" var="log">
								<option value="${log.codeValue}">${log.codeValue}</option>
							</c:forEach>
							</select>
							<input type="button" value="新添" style="width: 35px;height: 20px;"
										onclick="toCCode('scode20110106hfjes5ucxp0000000021','#logistics','#cashierTallyForm')"/></td>
    				<td><span><font class='font' size="2">出库人员：</font></span>
    						<span><input type="text" id="satffName" class="staffName succ inputbottom" value="${ca.ctUserName}" readonly="readonly">
	  							 <input type="hidden" class="staffName"  value="${ca.ctUserName}"  name="utboundOrderVo.ctUserName">
	  							 <input type="hidden" class="staffId"  value="${ca.contactUserID}" name="utboundOrderVo.contactUserID" ></span></td>
    			</tr>
    			<tr height="20px;">
    				<td></td>
    				<td><span><font class='font' size="2">调出仓库：</font></span>
    						<span><input type="text" class="inputbottom" value="${fin.depotName}" name="utboundOrderVo.depotname" readonly="readonly">
    						<input type="hidden"   value="${fin.depotID}" name="utboundOrderVo.depotid"></span></td>
    				<td align="center"><span><font class='font' size="2">调入仓库：</font></span>
    						<span><input type="text" class="inputbottom" id="choosedepotName"  value="${fin.drdepotName}"
    						name="utboundOrderVo.drdepotName" readonly="readonly">
    						<input type="hidden" name="utboundOrderVo.drdepotID"  value="${fin.drdepotID}" id="choosedepotID"/></span></td>
    				<td><span><font class='font' size="2">联系电话：</font></span>
    						<span><input type="text" class="inputbottom telephone"  value="${ca.reference}" name="utboundOrderVo.reference"></span></td>
    			</tr>
    		</table>
    	</div>
    	<div style="position:relative;top: 100px;left:10px;border: 1px solid #90D5D5;width: 900px;height: 250px;overflow-y:scroll;">
    		<input type="hidden" id="delGoodsBills" name="delGoodsBills">
    		<table class="table">
    		<thead>
    			<tr height="27" id="sale" class="hidetr" >
    				<th width="42">序号</th>
    				<th width="100">产品编号</th>
    				<th width="100">产品名称</th>
    				<th width="80">类型</th>
    				<th width="60">规格</th>
    				<th width="70">数量</th>
    				<th width="90">成本单价</th>
    				<c:if test="${fin.drdepotName=='销售库'}">
	    				<th width="80">利润金额</th>
	    				<th width="90">销售价</th>
    				</c:if>
    				<th width="60">备注</th>
    				<th width="30">操作</th>
    			</tr>
    		</thead>
    		<tbody id="tbody">
    			<c:forEach items="${goods}" var="l" varStatus="a">
    			<tr id="cloningContent${a.index+1}" class="cloningContent">
    				<td>${a.index+1}<input type="hidden" class="goodsBillsid" name="goodsList[${a.index}].goodsBillsID" value="${l.goodsBillsID}"></td>
    				<td><span>${l.goodsNum}</span></td>
    				<td><span>${l.goodsName}</span></td>
    				<td><span>${l.typeID}</span></td>
    				<td><span>${l.standard}</span></td>
    				<td><span>${l.quantity}</span></td>
    				<td><input type="text" value="${l.price}" name="goodsList[${a.index}].price" 
    						class="price bor" readonly="readonly"></td>
    				<c:if test="${fin.drdepotName=='销售库'}">
	    				<td><input type="text" value="${l.profitAmount}" name="goodsList[${a.index}].profitAmount" 
	    						class="profit bor"></td>
	    				<td><input type="text" value="${l.pretium}" name="goodsList[${a.index}].pretium" 
	    						class="sellingPrice bor" readonly="readonly"></td>
    				</c:if>
    				<td><input type="text" value="${l.remark}" name="goodsList[${a.index}].remark" 
    						class="remark bor" ></td>
    				<td align="center"><span><a href="javascript:;" class='dele' name="${a.index+1}">
    					<img src="<%=basePath%>images/admin_images/gtk-del.png" width='16' height='16' border='0'></a></span></td>
    			</tr>
    			</c:forEach>
    			<tr height="20" style="display: none;" id="tr">
    				<td></td>
    				<td><input type="text" class="goodsNum bor" name="goodsNum" readonly="readonly">
    					<input type="hidden" class="inventoryId" name="inventoryID"></td>
    				<td><input type='text' class='goodsName bor' name="goodsName" placeholder='点击选择物品' readonly="readonly">
						<input type='hidden' class='goodsId' name="goodsID"></td>
    				<td><input type="text" class="goodsType bor" name="typeID" readonly="readonly">
    					 <input type="hidden" class="productId" name="ppID"></td>
    				<td><input type="text" class="specifications bor" name="standard" readonly="readonly"></td>
    				<td><input type="text" class="quantity bor" name="quantity" readonly="readonly"></td>
    				<td><input type="text" class="price bor" name="price" readonly="readonly"></td>
    				<td><input type="text" class="bor" readonly="readonly"></td>
    				<td><input type="text" class="bor" readonly="readonly"></td>
    				<td><input type="text" class="bor" readonly="readonly"></td>
    				<td><input type="text" class="bor" readonly="readonly"></td>
    				<td align="center"><a href='javascript:;' class='dele'>
    					<img src="<%=basePath%>images/admin_images/gtk-del.png" width='16' height='16' border='0'></a></td>
    			</tr>
    		</tbody>
    		</table>
    	</div>
    	<div style="position:relative;top: 115px;left:10px;width: 900px;height: 40px;">
    	<span><span><font class='font' size="2">责 任 人：</font></span>
    						<span><input type="text" class="inputbottom" style="width: 150px;" readonly="readonly" name="utboundOrderVo.staffName" value="${ca.staffName} - ${ca.staffCode}">
    						<input type="hidden" name="utboundOrderVo.staffID" value="${ca.staffID}"></span></span>
    	<span style="float: right;"><span><font class='font' size="2">单据日期：</font></span>
    						<span><input type="text" id="date" class="inputbottom date" style="width: 150px;" readonly="readonly" value="${ca.cashierDate}"></span></span>
    	</div>
    </div>
    </form>
    <%----------------支付物流方式---------------------------%>
		<div class="jqmWindow jqmWindowcss3" style="width: 300px; top: 10%;"
			id="newccode">
			<div class=" ">
				添加
			</div>
			<table>
				<tr>
					<td>
						物流方式名称：
					</td>
					<td>
						<input id="ccodevalue" />
						<input id="codePID" type="hidden" />
						<input id="selectID" type="hidden" />
						<input id="formID" type="hidden" />
					</td>
				</tr>
			</table>
			<div align="center">
				<input type="button" class="input-button" onclick="saveCCode()"
					value="确定" />
				<input type="button" class="input-button JQueryreturn1" value="取消" />
			</div>
		</div>
		<%--*****************选择仓库****************--%>
	<form name="ckForms" id="ckForms" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<div class="jqmWindow jqmWindowcss1" style="top: 5%;left: 53%;"
				id="ckjqModel">
				<div class="content1" style="width: 100%; height: 400px;">
					<div class="contentbannb">
						<div class="drag">选择仓库</div>
					</div>
					<table width="99%" height="33" id="searchck" border="0"
						align="center" cellpadding="0" cellspacing="0"
						style="margin-top: 5px; background: #FFFFFF;">
						<tr>
							<td height="33" align="center">
							<input type="button" class="btn02" id="ckok" name="button5" value="确定" />
							<input type="button" class="btn02 xzck" name="button" value="新增" />
							<input type="button" class="btn02 JQueryreturns" name="button4" value="关闭" /> 
							</td>
						</tr>
					</table>
			    <form name="codeForm" method="post"></form>
				<div class="main_main">
				<table width="99%" border="0" align="center" cellpadding="0"
						cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
				  <tr>
				    <td  id="qh_sw" style="width: 15%;" valign="top">
				    <div id="ckaadTree">
				    	<ul  id="xmul"  class="filetree">
				    		<ul>
					    		<li>
					    			<span class='Warehouse folder cycle0 cy001' id="001">实物仓库</span>
					    		</li>
					    		<li>
					    			<span class='Warehouse folder cycle0 cy003' id="003">财务仓库</span>
					    		</li>
				    		</ul>
				    	</ul>
				    </div> 
				    </td>
				    <td style="width: 84%;" valign="top">
				      <iframe src="" name="ccode" width="100%" height="300" marginwidth="0" marginheight="0" scrolling="yes" frameborder="0" 
				      id="mainframe" border="0" framespacing="0" noresize="noResize"  vspale="0"> </iframe>
				    </td>
				  </tr>
				</table>
				</div>
					</div>
			</div>
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
			<!-- ----------------------------------------------选择科目---------------------------------------------------- -->
		<div class="jqmWindow jqmWindowcss2" id="subjectr" style="width: 550px;height:420px;top: 10%;background-color: #DAE7F6;">
	    	<div style="border: 1px #EBEBEB solid; background-color: #B0C4DE;height: 23px">
	    		<font size="3"><b>&nbsp;选择科目</b></font>
	    	</div>
	    	<div style="position:relative;left:250px; height: 50px;top: 2%">
	    		<input type="button" value="首页" class="subrid">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
	    		<input type="button" value="上一页" class="subrid">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    		<input type="button" value="下一页" class="subrid">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    		<input type="button" value="尾页" class="subrid">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    	</div>
	    	<div style="width: 130px;border: 1px #F0F8FF solid;background-color:#F0F8FF;
	    		border-left:3px #F0F8FF inset;border-top:3px #F0F8FF inset;
	    			height:300px;position: relative;top: -10px;">
	    		<div style="height: 270px;overflow:auto;">
	    		<table id="kemuone" cellspacing="0px">  			
	    		</table></div><br>
	    	</div>
	    	<div style="border: 1px #F0F8FF dashed;width:410px; height:300px;
	    		border-left:3px #CFCFCF double;border-top:3px #F0F8FF inset;
	    		position: relative;left:135px;top: -314px;">
	    		<table style="background-color:#F0F8FF" id="kemutoo">
	    			<tr style="background-color: #CAE1FF">
	    				<td width="72px" align="center">科目编号</td>
	    				<td width="110px" align="center">科目名称</td>
	    				<td width="80px" align="center">科目类别</td>
	    				<td width="65px" align="center">借贷方向</td>
	    				<td width="75px" align="center">账号类型</td>
	    			</tr>			
	    		</table>
	    	</div>	
	    	<hr style="border: 1px red solid">
    	<div>
    		<b style="position: absolute;top: 380px;left:380px;">
	    		<input type="button" value="确定" id="determine" style="width: 42px;height: 25px"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    		<input type="button" value="取消" id="cancel" style="width: 42px;height: 25px"/> 
    		</b>
    	</div>
    </div>
    <!-- -----------------------------选择物品-------------------------- -->
			<div class="jqmWindow jqmWindowcss1" style="position: absolute;;top: 20px;" disabled="true"
				id="goodsjqModel">
				<div class="content1" style="width: 100%; height: 400px;">
					<div class="contentbannb">
						<div class="drag">
							选择合格的产品
						</div>
					</div>
					<table width="99%" height="33" id="searchgood" border="0"
						align="center" cellpadding="0" cellspacing="3"
						style="margin-top: 5px; background: #FFFFFF;">
						<tr>
							<td width="100px;" >
								<font>根据条件查询：</font>
							</td>
							<td width="300px;">
								<span>物品类别：<input type="text" id="likeCategory" placeholder="物品类别" style="width: 70px;"></span>
								<span style="position: relative;left: 3px;">物品名称：<input type="text" id="likeGoodsName" placeholder="物品名称" style="width: 70px;"></span>
							</td>
							<td>
								<nobr>
								<input type="button" class="btn02 goodsButton" id="searchGood"
									name="button7" value="查询" />
								<input type="button" class="btn02 goodsButton" id="selectGood"
									name="button5" value="确定" />
								<input type="button" class="btn02 goodsButton" name="button4"
									value="关闭" />
								</nobr>
							</td>
						</tr>
					</table>
					<table width="99%" border="0" align="center" cellpadding="0"
						cellspacing="0" style="margin-top: 2px; margin-bottom: 2px;">
						<tr>
							<td width="100%" valign="top" align="left">
								<div id="body_02"
									style="margin-top: 2px; height: 300px; width: 100%; overflow: auto;">
									<table width="98%" height="26" align='center' cellspacing="0" style="background: url(<%=basePath %>images/admin_images/bg_01.gif) repeat-x;border-color: inherit"
										cellpadding="1" style='font-size: 12px;' class='bannb_01'>
										<tr>
											<td height="24" align="left" valign="top" class="txt01">
												&nbsp;点击选择物品
											</td>
										</tr>
									</table>
									<table width='100%' align='center' id='gotable' cellpadding='0'
										cellspacing='0' class='table' >
										<thead>
										<tr>
											<th height='21' align='center' bgcolor='#E4F1FA'>
												选择
											</th>
											<th align='center' bgcolor='#E4F1FA'>
												产品编号
											</th>
											<th align='center' bgcolor='#E4F1FA'>
												产品名称
											</th>
											<th align='center' bgcolor='#E4F1FA'>
												产品类型
											</th>
											<th align='center' bgcolor='#E4F1FA'>
												产品规格
											</th>
											<th align='center' bgcolor='#E4F1FA'>
												单价
											</th>
											<th align='center' bgcolor='#E4F1FA'>
												数量
											</th>
											<th align='center' bgcolor='#E4F1FA'>
												金额
											</th>
											<th align='center' bgcolor='#E4F1FA'>
												库存位置
											</th>
										</tr>                        
										</thead>
										<tbody id="tbodya">
											<tr id="sampleTr" style="display: none;" name="goods">
												<td></td><td></td><td></td><td></td>
												<td></td><td></td><td></td><td></td><td></td>
											</tr>
										</tbody>
									</table>
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
				<iframe name="hidden"  style="display: none;"></iframe>
  </body>
</html>
