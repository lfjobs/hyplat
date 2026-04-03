<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>比价</title>


<link rel="stylesheet" type="text/css" 
href="<%=basePath%>css/admin_main111.css" />	
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<link rel="stylesheet"
	href="<%=basePath%>/js/jqueryplus/treeview/jquery.treeview.css" />
<script src="<%=basePath%>/js/jqueryplus/treeview/jquery.treeview.js"
	type="text/javascript"></script>
<script src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js"></script>
		<script src="<%=basePath%>js/tree/codebase/dhtmlxtree.js"></script>
		<link rel="STYLESHEET" type="text/css"
			href="<%=basePath%>js/tree/codebase/dhtmlxtree.css" />
<script type="text/javascript"
	src="<%=basePath%>js/ea/finance/invoicing/budgetbprice.js"></script>
<style type="text/css">
.table {
	border: #a8c7ce 1px solid;
	cellpadding: 0;
	cellspacing: 0;
	font-size: 18px;
}

.td {
	border: #cccccc 1px solid;
}

li {
	list-style-type: none;
}

.containerTableStyle{
  overflow:hidden;

}

a{

text-decoration:none;

}

  .querybtn{
  background:url(<%=basePath%>images/search16.png) no-repeat;
  width:16px;
  height:16px;
  border:none;
  cursor:pointer;


  
  }
  
  #recodtbl{
    border-collapse:collapse;
    margin-top:15px;
  }
  
  #recodtbl th,#recodtbl td{
      border:1px solid #a8c7ce;
  }

</style>
<script type="text/javascript">
 var basePath = "<%=basePath%>";
 var results = "${result}";
 var deptpost="${billCheck.deptpost}";
 var bjstatus="${bjstatus}";
 var token = 1;

</script>
</head>
<body style="overflow:auto;">
	<table width="800" border="0" align="center" style="margin-top:20px;margin-bottom:5px;">
		<tr>
			<td align="center" colspan="6">
				<h2 style="padding-top:7px;"><c:if test='${bjstatus=="yqrbj"}'>已确定比价</c:if><c:if test='${bjstatus=="bj"}'>招标比价</c:if><c:if test='${bjstatus=="wqrbj"}'>未确定比价</c:if></h2></td>
		</tr>
	</table>

	<table width="99%" align="center" class="table"  id="tablemain"
		style="font-size:16px;" cellpadding="10">
		<thead>

			<tr>
				<th width="90" align="center"><input type="checkbox"  id="lqx"/><label for="lqx">全选</label></th>
				<th width="70" align="center">序号</th>

				<th width="280" align="right">项目名称<a href="#"
					onclick="getPID('xm',this)" style="margin-left:40px;"><img
						src="<%=basePath%>images/up.jpg"
						style="border: 0;vertical-align:middle" /> </a></th>
				<th width="200" align="right">主项目<a href="#"
					onclick="getPID('xmtype',this)" style="margin-left:40px;"><img
						src="<%=basePath%>images/up.jpg"
						style="border: 0;vertical-align:middle" /> </a></th>
				<th width="160" align="center">凭证号</th>
				<th width="200" align="right">制单时间<a href="#"
					onclick="getPID('zddate',this)" style="margin-left:40px;"><img
						src="<%=basePath%>images/up.jpg"
						style="border: 0;vertical-align:middle" /> </a></th>
				<th width="80" align="center">品名编号</th>
				<th width="200" align="right">品名名称<a href="#"
					onclick="getPID('good',this)" style="margin-left:40px;"><img
						src="<%=basePath%>images/up.jpg"
						style="border: 0;vertical-align:middle" /> </a></th>
				
				<th width="70" align="center">单价</th>
				<th width="70" align="center">数量</th>
				<th width="70" align="center">金额</th>

				<th width="300" align="center">往来单位</th>
				<th width="100" align="center">往来个人</th>
				<th width="200" align="center">比价审核人</th>
			</tr>
		</thead>
		<tbody id="tb">
			<%
				int number = 1;
			%>
			<s:iterator var="bean" value="beans">
				<tr class="docs" id="${bean[0]}" style="cursor:pointer;">
					<td class="td_bg01" align="center"><input type="checkbox"
						name="a" class="JQuerypersonvalue" value="${bean[0]}" /></td>
					<td class="td_bg01" align="center"><span><%=number%></span></td>

					<td class="td_bg01" align="center"><span id="projectName">${bean[1]}</span>
					<span id="cashierBillsID" style="display:none;">${bean[14]}</span>
					</td>

					<td class="td_bg01" align="center"><span id="xmtypename">${bean[2]}</span>
					</td>
					<td class="td_bg01" align="center"><span id="journalNum">${bean[3]}</span>
					</td>
                    <td class="td_bg01" align="center"><span id="cashierDate">${bean[4]}</span>
					</td>
					<td class="td_bg01" align="center"><span id="goodsNum">${bean[5]}</span>
					</td>

					<td class="td_bg01" align="center"><span id="goodsName">${bean[6]}</span>
					</td>
					
					
					<td class="td_bg01" align="center"><span id="price">${bean[8]}</span>
					
					</td>
					<td class="td_bg01" align="center"><span id="quantity">${bean[9]}</span>
					
					</td>
					<td class="td_bg01" align="center"><span id="money">${bean[10]}</span>
					
					</td>
					
					<td class="td_bg01" align="center"><nobr><span id="ccompanyName">${bean[12]}</nobr></span>
					
					</td>
					<td class="td_bg01" align="center"><span id="connectName">${bean[13]}</span>
					
					</td>
					<td class="td_bg01" align="center"><a  onclick="showSee('${bean[0]}',this);">查看</a>
					
					</td>
		
				</tr>
				<%
					number++;
				%>
			</s:iterator>
		</tbody>


	</table>
	<table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
	<tr><td>&nbsp;</td></tr>
	   <tr><td align="left" width="50px">备注：</td><td align="left" colspan="9"><input type="text" id="remark" class="inputbottom" style="width:80%;"/></td></tr>
	</table>
	<table width="80%" border="0" cellpadding="0" cellspacing="0" id="audittbl">
	
	<tr><td><input type="hidden" id="staffaudit" value="${staff.staffName}(${staff.staffCode})">
	
	</td></tr>
				<tr>
					<td height="25" align="right">
						公司经理：
					</td>
					<td>
						<input type="text" class="inputbottom gsjl"/><input type="button" class="btncon" disabled="true" id="gsjl"/>
					</td>
					<td align="right">
						部门主管：
					</td>
					<td>
						<input type="text" class="inputbottom bmzg"/><input type="button" class="btncon" disabled="true" id="bmzg"/>
					</td>
					<td  align="right">
						人事处：
					</td>
					<td>
						<input type="text" class="inputbottom rsc"/><input type="button" class="btncon" disabled="true" id="rsc"/>
					</td>
					<td  align="right">
						财务审核：
					</td>
					<td>
						<input type="text" class="inputbottom cwsh"/><input type="button" class="btncon" disabled="true" id="cwsh"/>
					</td>
					<td  align="center">
						收款人确认：
					</td>
					<td>
						<input type="text" class="inputbottom skr"/><input type="button" class="btncon" disabled="true" id="swr"/>
					</td>
				</tr>
				<tr>
					<td  height="25" align="right">
						总部总经理：
					</td>
					<td>
						<input type="text" class="inputbottom zjl"/><input type="button" class="btncon"  disabled="true" id="zjl"/>
					</td>
					<td  align="right">
						总部部门主管：
					</td>
					<td>
						<input type="text" class="inputbottom zg"/><input type="button" class="btncon" disabled="true" id="zg"/>
					</td>
					<td  align="right">
						总部人事处：
					</td>
					<td>
						<input type="text" class="inputbottom zbrsc"/><input type="button" class="btncon" disabled="true" id="zbrsc"/>
					</td>
					<td align="right">
						总财务审核：
					</td>
					<td>
						<input type="text" class="inputbottom zbcw"/><input type="button" class="btncon" disabled="true" id="zbcw"/>
					</td>
					<td  align="center">
						交款人确认：
					</td>
					<td>
						<input type="text" class="inputbottom jkr"/><input type="button" class="btncon" disabled="true" id="jkr"/>
					</td>
				</tr>
			</table>

	<div
		style="display:none;overflow:auto;border:1px solid #a8c7ce;width: 260px; height: 380px;position: absolute;top: 31%;left: 25%;z-index: 4 ; background-color:#e1ecfc; filter : Alpha(opacity=100);"
		id="jqModel">
		
		
		
		</div>


                <%
						int num=1;
					%>
	<div style="display:none;" id="goodsearch">
	
		<ul id="gdul">	
			<li><input name="good" type="checkbox" id="gqx" value="qx" onclick="qx('gqx','gdul');"/><label for="gqx">全选</label></li>
			
			<s:iterator var="b" value="beanss">
				<li><input name="good" type="checkbox" id="<%=num%>b" value="${b[0]}" /><label for="<%=num%>b">${b[0]}</label></li>
                <%
						 num++;
					%>
			</s:iterator>
			<li><input type="button" value="  确定 "   class="input-button confirm"  disabled ="true" onclick="submit('gdul','goodname')"/><input type="button" value="  取消 "  class="input-button" onclick="cancel();"/></li>
		</ul>
       
	</div>
	
	







<!-- 项目分类树形结构 -->	
<div id="channelsDialog"  style="display:none;">
<div id="treeBoxshow">
<div style='text-align:left;margin-left:5px; margin-top:5px;margin-bottom:4px;border:1px solid #a8c7ce;width:240px;height:22px;'><input type='text' size='28' style='border:none;height:20px;background:#e1ecfc;' id="xmfl"/><input type='button' class='querybtn' /></div>
<div id="treeBox"  style="border-top:1px solid #a8c7ce;border-bottom:1px solid #a8c7ce;overflow:auto;height:300px;">
	

   

   
</div>

<input type='button' value='  确定  ' disabled='true' onclick="submit('treeBox','xmtypes')" class='input-button confirm'/><input class='input-button' type='button' value=" 取消  " onclick="cancel();"/>
</div>
<input type="hidden" value="" id="xmtypeddd"/> 
<input type="hidden" value="" id="ddate"/> 
</div>



<!-- 关于时间树形结构 -->	
<div id="dateDialog" style="overflow:auto;">
<div id="treeDate" >

</div>
</div>


<!-- 筛选提交表单 -->
<div style="display:none;">
   <form id="searchForm" name="searchForm" method="post">
   
   <input type="submit"  style="display:none;" name = "submit"/>
   <input type="hidden"  value="" name="cashierBills.projectName" id="proname"/>
   <input type="hidden"  value="" name="detail.goodsName" id="goodname"/>
   <input type="hidden"  value="" name="cashierBills.xmtype" id="xmtypes"/>
   <input type="hidden"  value="" name="sdate" id="startDates"/>
   <input type="hidden"  value="" name="type" id="types"/>
   <input type="hidden"  value="search" name="search"/>
   <!-- 用于通过 -->
    <input type="hidden"  value=""  name="goodsBillsID" id="csdIDss"/>
    <input type="hidden"  value=""  name="cashierBillsID" id="csbIDss"/>
    <input type="hidden"  value="" name="billCheck.deptpost" id="deptpost"/>
    <input type="hidden" name="billCheck.remark" id="remarks"/>
    <input type="hidden" name="bjstatus" value="${bjstatus}"/>
   
   </form>


</div>



<script type="text/javascript">
 
 $(function() {
		
		
		$("#tree").treeview({	 
			  collapsed: true,
			  unique: true
		});
		
	});
</script>

	<div class="audit" style="display:none;border:1px solid #a8c7ce;width:300px;height:200px; background-color: rgb(225, 236, 252)">

		<table width="100%" style="background:#ffffff;border:1px solid #a8c7ce;" id="recodtbl">
			<caption style="margin-bottom:5px;"><h6>审核记录</h6></caption>
			<thead style="background:#E4F1FA">
				<tr>
					<th align="center">审核人</th>
					<th align="center">审核时间</th>
					<th align="center">审核意见</th>
				</tr>
			</thead>
			<tbody id="record">

			</tbody>

		</table>
       <div style="text-align:center;margin-top:30px;"> <input type="button" value="  关闭    " class="input-button" id="col"/></div>

	</div>

	<iframe name="hidden"  width="0" height="0"></iframe>
</body>
</html>
