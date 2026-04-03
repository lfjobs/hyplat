<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>收款单打印</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
 <%@ page language="java" pageEncoding="UTF-8" %>
        <%@ taglib uri="/struts-tags" prefix="s" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
         <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
        <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
        + request.getServerName() + ":" + request.getServerPort()
        + path + "/"; %>
        <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
        <script language="javascript" type="text/javascript" src="<%=basePath%>js/ea/finance/company/cashierbillsprint4cashaccept.js"></script>
<style type="text/css">
.table {
	border-collapse:collapse;
	border:1px solid #000000;
}

.table th {
	border:1px solid #000000;
	color:#000000;
}
.table td {
	border:1px solid #000000;
	color:#000000;
}
body,td,th {
	font-size: 12px;
	text-align: left;
}
body {
	margin-left: 15px;
}
#apDiv1 {
	position:absolute;
	left:507px;
	top:287px;
	width:63px;
	height:32px;
	z-index:1;
}
</style>
 <script type="text/javascript">
    var  st = "${cashierBillsVO.status}";
    var basePath = "<%=basePath%>";
    var jk="${strList[0]}";
    var sk="${strList[1]}";
    var carNum = "${carNum}";
    $(function(){

       if(carNum!=null&&carNum!=""){
           $(".hcp").text("车牌号");
           $(".hpp").text(carNum);
	   }

	});
</script>
 </head>
<body>
<div id="apDiv1"></div>
<div id="tableprint" align="center">
<table width="620"  border="0" cellpadding="0" cellspacing="0"  style="background:#FFFFFF;">
  <tr>
    <td height="25" align="center" style="font-weight:bold;text-align: center; ">&nbsp;<span style="font-size: 15px">收款单</span></td>
  </tr>
</table>
				<table width="620" border="0" cellpadding="0" cellspacing="0" style="background:#FFFFFF;">
					<tr align="left">
						<td align="left"><img src="<%=basePath%>/images/ea/finance/ttswlogo3.png" width="100px" height="25px"/></td>
					</tr>
				</table>
		<table width="620" border="0" cellpadding="0" cellspacing="0"
			style="background:#FFFFFF;">
			<tr>
				<td width="70%" colspan="5"><c:if
						test="${cashierBillsVO.jNumOrder!=null}">订单号：
						<font color="red">${cashierBillsVO.jNumOrder}</font>
					</c:if></td>
				<td width="10%" colspan="1" align="left">凭证号</td>
				<td width="20%" colspan="2"><c:if
						test="${cashierBillsVO.journalNum!=null}">
						<font color="red">${cashierBillsVO.journalNum}</font>
					</c:if>
				</td>
			</tr>
			<tr>
				<td width="6%" height="25" align="left">公司：</td>
				<td width="25%" align="left">
					<s:if test="#request.companyname !=null && #request.cashierBillsVO.statusbill=='04' ">
						${companyname}	
					</s:if>
					<s:elseif test="#request.cashApplyBills.receivablesName != null ">
						${cashApplyBills.receivablesName}
					</s:elseif>
					<s:else>
						${cashierBillsVO.companyname}	
					</s:else>
				</td>
				<td width="6%" align="left">
				<c:if test="${cashierBillsVO.statusbill != '04'}">				
					部门：</c:if></td>
					<td width="8%" align="left"><c:if test="${cashierBillsVO.statusbill != '04'}">	${cashierBillsVO.departmentname}</c:if></td>
					<td width="8%" align="left"><c:if test="${cashierBillsVO.statusbill != '04'}">	责任人：</c:if></td>
					<td width="15%"><c:if test="${cashierBillsVO.statusbill != '04'}">	<c:if test="${cashierBillsVO.staffname!=null}">${cashierBillsVO.staffname}</c:if></c:if>			
				
				</td>				
				
				<td width="10%" align="left">入账时间：</td>
				<td width="10%" align="left">${fn:substring(cashierBillsVO.cashierDate,0, 10)}</td>
				
			</tr>
			<c:if test="${cashierBillsVO.statusbill != '04'}">	
			<tr>
				<td width="100%" height="25" colspan="8"><table width="100%">
						<tr>
							<td width="15%" align="left">目标部门：</td>
							<td width="15%" align="left">${cashierBillsVO.targetDeptName}</td>
							<td width="15%" align="left">目标业务员：</td>
							<td width="15%" align="left">${cashierBillsVO.targetSalerName}</td>
							<td  align="left"></td>
							<td  align="left"></td>
							<td  align="left"></td>
							<td  align="left"></td>
						</tr>						
					</table>
				</td>
			</tr>
			</c:if>
			<c:if test="${cashierBillsVO.statusbill == '04'}">
				<c:if test="${(companyname !=null && cashierBillsVO.statusbill=='04') || cashApplyBills.receivablesName != null}">
					<tr>
						<td width="10%" align="left">监管平台：</td>
						<td width="10%" align="left">${cashierBillsVO.companyname}</td>	
					</tr>
				</c:if>
			</c:if>
		</table>
		<table width="620" cellpadding="0" cellspacing="0" class="table">
				  <tr>
				    <th style="line-height: 30px" rowspan="1" align="center">款源时间</th>
				    <th rowspan="1" align="center">物品编号</th>
				    <th rowspan="1" align="center">物品名称</th>
				    <th rowspan="1" align="center" class="hcp">品牌</th>
				    <th rowspan="1" align="center">等级</th>
				    <th rowspan="1" align="center">型号</th>
				    <th rowspan="1" align="center">规格</th>
				    <th align="center">单位</th>
				    <th align="center">数量</th>
				    <th align="center">重量</th>
				    <th rowspan="1" align="center">单价管理</th>
				    <th rowspan="1" align="center">单价</th>
				    <th rowspan="1" align="center">金额</th>
				    <th width="40px" rowspan="1" align="center">是否是赠品</th>
				  </tr>
				  <s:iterator value="goodList">
				  <tr>
				    <td>${fn:substring(startDate, 0, 10)}</td>
				    <td>${goodsNomber}</td>
				    <td>${goodsName}</td>
				    <td class="hpp">&nbsp;</td>
				    <td>${goodsCoding}</td>
				    <td>${model}</td>
				    <td>${standard}</td>
				    <td>${variableID}</td>
				    <td>${quantity}</td>
				    <td>${weight}</td>
				    <td><span id="priceManage">${priceManage}</span></td>
				    <td>${price}<span id="balance" style="display:none" class="moneysum">${money}</span></td>
				    <td><span id="money" >${money}</span></td>
				    <td align="center"><span id="premiums" >${premiums=='1'?'是':'否'}</span></td>
				  </tr>
				  </s:iterator>
				</table>
				<table width="620" cellpadding="0" cellspacing="0" class="table">
				  <tr>
				    <td width="186" height="15" align="left" style="border-right:1px solid #000000;">缴款单位或个人所缴金额</td>
				    <td id="daxie">金额（大写）：&nbsp;&nbsp;<span id="6"></span>&nbsp;&nbsp;<span style="color:red">万</span>&nbsp;&nbsp;<span id="5"></span>&nbsp;&nbsp;<span style="color:red">仟</span>&nbsp;&nbsp;<span id="4"></span>&nbsp;&nbsp;<span style="color:red">佰</span>&nbsp;&nbsp;<span id="3"></span>&nbsp;&nbsp;<span style="color:red">拾</span>&nbsp;&nbsp;<span id="2"></span>&nbsp;&nbsp;<span style="color:red">元</span>&nbsp;&nbsp;<span id="1"></span>&nbsp;&nbsp;<span style="color:red">角</span>&nbsp;&nbsp;<span id="0"></span>&nbsp;&nbsp;<span style="color:red">分</span>
				    	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;￥<span id="monsum"></span>&nbsp;&nbsp;
				    </td>
				  </tr>
				</table>
				
				<!-- 判断是否驾校学员 -->				
				<c:choose>				
					<c:when test="${tradeCode =='z30001汽车驾校' && cashierBillsVO.statusbill=='04'}">
						<table width="620" cellpadding="0" cellspacing="0" class="table">
							<tr>
								<td width="110" height="15" align="left" style="border-right:1px solid #000000;">缴款单位或个人</td>
						    	<td width="60">${contactUserName }</td>						
								<td width="200" height="15" align="left" style="border-right:1px solid #000000;">缴款单位或个人身份证号码</td>
						    	<td width="250">${staffIdentityCard }</td>																		
							</tr>
							<tr>
								<td width="156" height="15" align="left" style="border-right:1px solid #000000;">缴款单位或个人电话</td>
						    	<td width="160" >${tel }</td>
						    	<td width="186" height="15" align="left" style="border-right:1px solid #000000;">缴款单位或个人地址</td>
						    	<td width="160">${ReferrerAddress }</td>																									  
							</tr>
						</table>
					</c:when>
					<c:otherwise>
						<table width="620" cellpadding="0" cellspacing="0" class="table">
							  <tr>
							    <td width="186" height="15" align="left" style="border-right:1px solid #000000;">缴款单位或个人</td>
							    <td width="160" id="jk"><c:if test="${cashierBillsVO.statusbill=='04'}">${cashierBillsVO.contactUserName}</c:if></td>
							    <c:if test="${strList[0]!=null}"><td width="150" align="center">缴款单位或个人身份证号码</td>
							  	<td width="120">${strList[0]}</td></c:if>
							  </tr>
							  <tr>
							    <td width="186" height="15" align="left" style="border-right:1px solid #000000;">缴款单位或个人帐号</td>
							    <td colspan="3"><c:if test="${cashApplyBills.appropriationNum!='null'}">${cashApplyBills.appropriationNum}</c:if></td>
							  </tr>
							</table>																								
					</c:otherwise>																		
				</c:choose>
																											
				<table width="620" cellpadding="0" cellspacing="0" class="table">
				  <tr>
				    <td width="70" height="15" align="left" style="border-right:1px solid #000000;">费用类别</td>
				    <td width="115" style="border-right:1px solid #000000;"></td>
				    <td width="69" align="left">一级科目</td>
				    <td width="80" ></td>
				    <td width="68" align="left">二级科目</td>
				    <td width="134" colspan="5"></td>
				  </tr>
				</table>
	
				<table width="620" cellpadding="0" cellspacing="0" class="table">				 				 
					  <tr>
					    <td width="186" height="15" align="left" style="border-right:1px solid #000000;">最终收款单位或个人名称</td>
					    <td width="160" id="sk">					    	
					    		<c:choose>
									<c:when test="${companyname !=null && cashierBillsVO.statusbill=='04'}">								
										${companyname}		
									</c:when>
									<c:otherwise>
										<c:if test="${cashApplyBills.receivablesName!= null}">				    	
					    					${cashApplyBills.receivablesName}											    						    						    	
					    				</c:if>																												
									</c:otherwise>			
								</c:choose>				    				    				 
					    	
					    </td>
					  	<c:if test="${strList[1]!=null}"><td width="150" align="center">收款单位或个人身份证号码</td>
					  	<td width="120">${strList[1]}</td> </c:if>
					  </tr>
					  				  		  
					  <tr>
					    <td width="186" height="15" align="left" style="border-right:1px solid #000000;">最终收款单位责任人电话</td>
					    <td colspan="3"><c:if test="${cashApplyBills.recropriationNum!='null'}">${responsibletel }</c:if></td>
					  </tr>					  
				</table>
			
		<table width="620" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td height="25" colspan="10" width="80">备注:${cashierBillsVO.remark}</td>
				</tr>
					
					<c:if test="${cashierBills.statusbill=='04' }">
						<tr><td>1.客户确定购物收货项目或培训学习项目.</td></tr>
						<tr><td>2.平台自动将该成交项目金币(金额)分配给该项目业务所有人、请客户看清后确定确定后不予退款.</td></tr>
						<tr><td>3.平台方，供应商订，金币兑现、三方对帐.</td></tr>	
					</c:if>	
		</table>
		<table width="620" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td height="25" align="right" width="80">公司经理：</td>
						<td width="40"><span>${bcheckMap["gsjl"] }</span></td>
						<td align="right" width="90">部门主管：</td>
						<td width="40"><span>${bcheckMap["bmzg"] }</span></td>
						<td align="right" width="70">人事处：</td>
						<td width="40"><span>${bcheckMap['rsc'] }</span></td>
						<td align="right" width="80">财务审核：</td>
						<td width="40"><span>${bcheckMap['cwsh'] }</span></td>
						<td align="center">交款人确认：</td>
							<td><span>${bcheckMap['jkr'] }<c:if test="${cashApplyBills.appropriationcompanyName != null}">${cashApplyBills.appropriationcompanyName}</c:if></span>
							</td>
					</tr>
					<c:if test="${cashierBills.statusbill!='04' }">
						<tr>
							<td height="25" align="right">总部总经理：</td>
							<td><span>${bcheckMap["zjl"] }</span>
							</td>
							<td align="right">总部部门主管：</td>
							<td><span>${bcheckMap['zg'] }</span>
							</td>
							<td align="right">总部人事处：</td>
							<td><span>${bcheckMap['zbrsc'] }</span>
							</td>
							<td align="right">总财务审核：</td>
							<td><span>${bcheckMap['zbcw'] }</span>
							</td>
							<td align="center" width="80">收款人确认：</td>
							<td width="40"><span>${bcheckMap['skr'] }</span></td>
					   </tr>
					</c:if>									
				</table>
				<table width="620" border="0" cellpadding="0" cellspacing="0" style="background:#FFFFFF;">
					<tr>
						<td align="left">一联（白）制单&nbsp;&nbsp;存根</td>
						<td>二联（红）制单&nbsp;&nbsp;存根</td>
						<td>三联（黄）制单&nbsp;&nbsp;存根</td>
						<td align="right">四联（蓝）制单&nbsp;&nbsp;存根</td>
					</tr>
				</table>
				<c:if test="${tradeCode =='z30001汽车驾校' && cashierBillsVO.statusbill=='04'}">
					<table width="620" border="0" cellpadding="0" cellspacing="0" style="background:#FFFFFF;">
						<tr>
							<td align="left" style="word-break:break-all;">约定：必须按时到校认真训练，严格遵守约考时间及纪律，违者后果自负。交款确认后，除车运管不能受理的学员外，不来训练或考试者属自动放弃，一概不退费，学员签字： __________</td>
						</tr>
					</table>					
				</c:if>

</body>
</html>