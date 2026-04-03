<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>产品出库预览</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/admin_main111.css" /> 
	<link rel="stylesheet" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"  type="text/css"/>
	<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/ea/production/finishproduct/finishedProduct_print.js"></script>
	
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
</style>
<script type="text/javascript">
var basePath="<%=basePath%>";
var type="${type}";
var str="";
var depotName="${ut[6]}";
</script>
  </head>
  <body>
    <div>
    	<div>
    	<div style="position: relative;top: 30px;text-align: center;width: 900px;">
    		<span><font size="5" style="font-weight:bold;color: #15428b">成品移库单</font></span>
    	</div>
    	<div style="position: relative;top: 80px;text-align: center;width: 900px;">
    		<table style="width: 100%;"	class="ttable">
   				<tr height="20">
   					<td width="30px"></td>
   					<td width="210px"><span><font class='font' size="2">公司名称：</font></span>
    						<span><input type="text" class="inputbottom" value="${comName}" readonly="readonly"></span></td>
   					<td width="350px" align="center"><span><font class='font' size="2">对方科目：</font></span>
    						<span><input type="text" class="inputbottom" value="${ut[0]}" readonly="readonly"></span></td>
   					<td width="270"><span><font class='font' size="2">单据编号：</font></span>
    						<span><input type="text" class="inputbottom" value="${ut[1]}" readonly="readonly">
    							<input type="hidden" id="cashiId" value="${ut[8]}"></span></td>
   				</tr>
    			<tr height="20px;">
    				<td></td>
    				<td><span><font class='font' size="2">出库日期：</font></span>
    						<span><input type="text" class="inputbottom" value="${ut[10]}" readonly="readonly"></span></td>
    				<td align="center"><span><font class='font' size="2">物流方式：</font></span>
    						<span><input type="text" class="inputbottom" value="${ut[3]}" readonly="readonly"></span></td>
    				<td><span><font class='font' size="2">出库人员：</font></span>
    						<span><input type="text" class="inputbottom" value="${ut[4]}" readonly="readonly"></span></td>
    			</tr>
    			<tr height="20px;">
    				<td></td>
    				<td><span><font class='font' size="2">调出仓库：</font></span>
    						<span><input type="text" class="inputbottom" value="${ut[5]}" readonly="readonly"></span></td>
    				<td align="center"><span><font class='font' size="2">调入仓库：</font></span>
    						<span><input type="text" id="depot" class="inputbottom" value="${ut[6]}" readonly="readonly"></span></td>
    				<td><span><font class='font' size="2">联系电话：</font></span>
    						<span><input type="text" class="inputbottom" value="${ut[7]}" readonly="readonly"></span></td>
    			</tr>
    		</table>
    	</div>
    	<div style="position:relative;top: 100px;left:10px;border: 1px solid #90D5D5;width: 900px;height: 250px;overflow-y:scroll;">
    		<table class="table">
    		<thead>
    			<tr height="27" id="sale" class="hidetr" >
    				<th width="42">序号</th>
    				<th width="150">产品编号</th>
    				<th width="139">产品名称</th>
    				<th width="80">类型</th>
    				<th width="70">规格</th>
    				<th width="70">数量</th>
    				<th width="90">成本单价</th>
				   <c:if test="${ut[6]=='销售库'}">
	    				<th width="80">利润金额</th>
	    				<th width="90">销售价</th>
    				</c:if>
    				<th width="60">备注</th>
    			</tr>
    		</thead>
    		<tbody id="tbody">
    			<c:forEach items="${list}" var="l" varStatus="a">
    				<tr>
    					<td>${a.index+1}</td>
    					<td>${l.goodsNum}</td>
    					<td>${l.goodsName}</td>
    					<td>${l.typeID}</td>
    					<td>${l.standard}</td>
    					<td>${l.quantity}</td>
    					<td>${l.price}</td>
    					<c:if test="${ut[6]=='销售库'}">
	    					<td>${l.profitAmount}</td>
	    					<td>${l.pretium}</td>
    					</c:if>
    					<td>${l.remark}</td>
    				</tr>
    			</c:forEach>
    		</tbody>
    		</table>
    	</div>
    	<div style="position:relative;top: 115px;left:10px;width: 900px;height: 40px;">
    	<span><span><font class='font' size="2">责 任 人：</font></span>
    						<span><input type="text" class="inputbottom" value="${ut[9]} - ${ut[11]}" readonly="readonly"></span></span>
    	<span style="float: right;"><span><font class='font' size="2">单据日期：</font></span>
    						<span><input type="text" class="inputbottom" value="${ut[2]}" readonly="readonly"></span></span>
    	</div>
    </div>
   	<div style="position:relative;top:90px;" id="examine">
   		 <table width="99%" border="0" style="margin-left: 15px;" align="center" cellpadding="0" cellspacing="0">
			   <tr><td colspan="2">&nbsp;</td></tr>
			   <tr>
			   <td align="left" width="50px">备注：</td>
			   <td align="left" colspan="9">
			   <input type="text" id="remark" class="inputbottom" style="width:80%;" readonly="readonly" value="${str}"/>
			   </td>
			   </tr>
			</table>
			<table width="99%" border="0" cellpadding="0" cellspacing="0" id="audittbl">
			<tr><td>
			<input type="hidden" id="staffauditname" 
			value="${ManStaffName}">
			<input type="hidden" id="staffauditcode" 
			value="${ManStaffCode}">
			<input type="hidden" id="staffauditid" 
			value="${ManStaffId}">
			</td></tr>
			<tr class="aduittr">
				<td height="25" align="right">公司经理：</td>
				<td><input type="text" readonly="readonly" class="inputbottom gsjl" value='${billcheckmap["gsjl"]}    '/>
				<c:if test='${billcheckmap["gsjl"]==null||billcheckmap["gsjl"]==""}'>
					<input type="button" class="btncon verify" id="gsjl" />
				</c:if >
				<c:if test='${billcheckmap["gsjl"]!=null&&billcheckmap["gsjl"]!=""}'>
					${billcheckmap["gsjlzt"]==02?"通过":"驳回"}
				</c:if>
				<td align="right">部门主管：</td>
				<td><input type="text" readonly="readonly" class="inputbottom bmzg" value='${billcheckmap["bmzg"] }    '/>
				
				<c:if test='${billcheckmap["bmzg"]==null||billcheckmap["bmzg"]==""}'>		
					<input type="button" class="btncon verify" id="bmzg"/>
				</c:if>
				<c:if test='${billcheckmap["bmzg"]!=null&&billcheckmap["bmzg"]!=""}'>		
					${billcheckmap["bmzgzt"]==02?"通过":"驳回"}
				</c:if>
				</td>
				<td align="right">人事处：</td>
				<td><input type="text" readonly="readonly" class="inputbottom rsc" value="${billcheckmap['rsc'] }"/>
				<c:if test='${billcheckmap["rsc"]==null||billcheckmap["rsc"]==""}'>
					<input type="button" class="btncon verify" id="rsc" />
				</c:if>
				<c:if test='${billcheckmap["rsc"]!=null&&billcheckmap["rsc"]!=""}'>
					${billcheckmap['rsczt']==02?'通过':'驳回'}
				</c:if>
				</td>
				<td align="right">财务审核：</td>
				<td><input type="text" readonly="readonly" class="inputbottom cwsh" value="${billcheckmap['cwsh'] }"/>
				<c:if test='${billcheckmap["cwsh"]==null||billcheckmap["cwsh"]==""}'>
					<input type="button" class="btncon verify" id="cwsh" />
				</c:if>
				<c:if test='${billcheckmap["cwsh"]!=null&&billcheckmap["cwsh"]!=""}'>
					${billcheckmap['cwshzt']==02?'通过':'驳回'}
				</c:if>
				</td>
				<td align="center">收款人确认：</td>
				<td><input type="text" readonly="readonly" class="inputbottom skr" value="${billcheckmap['skr'] }"/>
				<c:if test='${billcheckmap["skr"]==null||billcheckmap["skr"]==""}'>
					<input type="button" class="btncon verify " id="skr" />
				</c:if>
				<c:if test='${billcheckmap["skr"]!=null&&billcheckmap["skr"]!=""}'>
					${billcheckmap['skrzt']==02?'通过':'驳回'}
				</c:if>
				</td>
			</tr>
			<tr class="aduittr">
				<td height="25" align="right">总部总经理：</td>
				<td><input type="text" readonly="readonly" class="inputbottom zjl" value='${billcheckmap["zjl"] }'/>
				<c:if test='${billcheckmap["zjl"]==null||billcheckmap["zjl"]==""}'>
					<input type="button" class="btncon verify" id="zjl" />
				</c:if>
				<c:if test='${billcheckmap["zjl"]!=null&&billcheckmap["zjl"]!=""}'>
					 ${billcheckmap["zjlzt"]==02?"通过":"驳回"}
				</c:if>
				</td>
				<td align="right">总部部门主管：</td>
				<td><input type="text" readonly="readonly" class="inputbottom zg" value="${billcheckmap['zg'] }"/>
				<c:if test='${billcheckmap["zg"]==null||billcheckmap["zg"]==""}'>
					<input type="button" class="btncon verify" id="zg" />
				</c:if>
				<c:if test='${billcheckmap["zg"]!=null&&billcheckmap["zg"]!=""}'>
					${billcheckmap['zgzt']==02?'通过':'驳回'}
				</c:if>
				</td>
				<td align="right">总部人事处：</td>
				<td><input type="text" readonly="readonly" class="inputbottom zbrsc" value="${billcheckmap['zbrsc'] }"/>
				<c:if test='${billcheckmap["zbrsc"]==null||billcheckmap["zbrsc"]==""}'>
					<input type="button" class="btncon verify" id="zbrsc" />
				</c:if>
				<c:if test='${billcheckmap["zbrsc"]!=null&&billcheckmap["zbrsc"]!=""}'>
					${billcheckmap['zbrsczt']==02?'通过':'驳回'}
				</c:if>
				</td>
				<td align="right">总财务审核：</td>
				<td><input type="text" readonly="readonly" class="inputbottom zbcw" value="${billcheckmap['zbcw'] }"/>
				<c:if test='${billcheckmap["zbcw"]==null||billcheckmap["zbcw"]==""}'>
					<input type="button" class="btncon verify" id="zbcw" />
				</c:if>
				<c:if test='${billcheckmap["zbcw"]!=null&&billcheckmap["zbcw"]!=""}'>
					${billcheckmap['zbcwzt']==02?'通过':'驳回'}
				</c:if>
				</td>
				<td align="center">交款人确认：</td>
				<td><input type="text" readonly="readonly" class="inputbottom jkr" value="${billcheckmap['jkr'] }"/>
				<c:if test='${billcheckmap["jkr"]==null||billcheckmap["jkr"]==""}'>
					<input type="button" class="btncon verify" id="jkr" />
				</c:if>
				<c:if test='${billcheckmap["jkr"]!=null&&billcheckmap["jkr"]!=""}'>
					 ${billcheckmap['jkrzt']==02?'通过':'驳回'}
				</c:if>
				</td>
			</tr>
		</table>
   	
   	</div>
    </div>
    <div style="width: 350px;height:220px;border: 1px solid #ffffff;background-color: #DBFAF8;
			position: absolute;top:209px;left: 860px;" class="jqmWindow jqmWindowcss2" id="single">
    	<div style="background-color: #C5C1AA;height: 28px;text-align: center;">
    		<font size="3" color="#008B00" style="position: relative;top: 4px;">物品审核</font>
    	</div>
    	<div style="position: relative;top: 10px;height: 120px;">
    		<div style="position: relative;left: 20px;"><span>审核意见：</span>
    			<span style="position: relative;left: 80px;"><input type="radio" name="radio" class="radio" value="yes" checked="checked">通过</span>
    			<span style="position: relative;left: 110px;"><input type="radio" name="radio" class="radio" value="no">驳回</span></div>
    		<div><textarea style="width: 250px;height: 100px;position: relative;left: 20px;top: 2px;"></textarea></div>
    	</div>
    	<div style="position:relative;top:10px;">
    		<input type="button" class="but" value="提交" style="width: 45px;height: 25px;position: relative;top: 18px;left: 100px;">
    		<input type="button" class="but" value="关闭" style="width: 45px;height: 25px;position: relative;top: 18px;left: 150px;">
    	</div>
    </div>
  </body>
</html>
