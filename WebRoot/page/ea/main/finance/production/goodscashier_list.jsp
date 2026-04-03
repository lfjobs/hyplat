<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page language="java" pageEncoding="UTF-8" %>
<%@page import="hy.ea.bo.Company"%>
        <%@ taglib uri="/struts-tags" prefix="s" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
         <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
        <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
        + request.getServerName() + ":" + request.getServerPort()
        + path + "/"; 
        Company c = (Company)session.getAttribute("currentcompany");%><html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="cache-control" content="no-cache"/>
        <title>出纳记账明细汇总管理</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
        <script src="<%=basePath%>js/jquery.js" type="text/javascript">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/flexigrid.js">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js">
        </script>
        
        <script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
        <script language="javascript" type="text/javascript" src="<%=basePath%>js/ea/finance/production/goodscashier_list.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/common/organizationTree.js"></script>
        <script type="text/javascript">
        	var treeID ="<%=c.getCompanyID()%>";
    var treeNames="<%=c.getCompanyName()%>";
        	var goodsBillsID="";
        	var search="${search}";
        	var basePath="<%=basePath%>";
        	var pNumber=${pageNumber};
        	var sdate="${sdate}";
        	var edate="${edate}";
         	var notoken = 0;
         	var period="${period}";
          
			document.onkeydown = function(evt){//捕捉回车 
   				evt = (evt) ? evt : ((window.event) ? window.event : "") //兼容IE和Firefox获得keyBoardEvent对象
    			var key = evt.keyCode ? evt.keyCode : evt.which; //兼容IE和Firefox获得keyBoardEvent对象的键值
    			if (key == 13) { //判断是否是回车事件。
    				if($("input#journalNum").val()==''){
						return false;
    				}
        			if($("input#journalNum").val()!=''){
			        	$("#SearchForm").attr("action", basePath+"ea/goodscashier/ea_toProSearch.jspa?pageNumber="+pNumber);
                    	document.SearchForm.submit.click();
					}
    			}
			}
        </script>
    </head>
    <body>
         <table class="flexme11">
            <thead>
                <tr>
                    <th width="30" align="center">
                        选择
                    </th>
                     <th width="200" align="center">
                        公司名称
                    </th>
                   <th width="150" align="center">
                       黏贴单编号
                    </th>
                      <th width="70" align="center">
                        单据类别
                    </th>
                     <th width="70" align="center">
                       部门
                    </th>
                    <th width="70" align="center">
                        责任人
                    </th>
                      <th width="70" align="center">
                       制单日期
                    </th>
                    <th width="70" align="center">
                        物品名称
                    </th>
                    <th width="60" align="center">
                      物品编号
                    </th>
                    <th width="60" align="center">
                        类型
                    </th>
                    <th width="70" align="center">
						科目
					</th>
                     <th width="60" align="center">
                      单位
                    </th>
                     <th width="60" align="center">
                      单价
                    </th>
                       <th width="60" align="center">
                       数量
                    </th>
                     <th width="70" align="center">
                     金额
                    </th>
                   <th width="70" align="center">
						库房管理
					</th>
					<th width="70" align="center">
						借方金额
					</th>
					<th width="70" align="center">
						贷方金额
					</th>
					<th width="70" align="center">
						方向
					</th>
					<th width="70" align="center">
						余额
					</th>
                    <th width="90" align="center">
                    税务状态
                    </th>
                    <th width="90" align="center">
                     单据状态
                    </th>
                     <th width="70" align="center">
                       附件管理
                    </th>
                </tr>
            </thead>
            <tbody>
            <s:iterator value="pageForm.list" >
					<tr id="${cashierBillsID}">
						<td>
							<input type="radio" name="a" class="JQuerypersonvalue"
								value="${cashierBillsID}" />
						</td>
						<td>
							<span id="companyname">${companyname}</span>
						</td>
						<td>
							<span id="journalNum">${journalNum}</span>
						</td>
						<td>
							<span id="billsType">${billsType}</span>
						</td>
						<td>
							<span id="departmentname">${departmentname}</span>
						</td>
						<td>
							<span id="staffname">${staffname}</span>
						</td>
						<td>
							<span id="cashierDate">${fn:substring(cashierDate, 0, 10)}</span>
						</td>
						<td>
							<span id="goodsName">${goodsName}</span>
						</td>
						<td>
							<span id="goodsCoding">${goodsCoding}</span>
						</td>
						<td>
							<span id="typeID">${typeID}</span>
						</td>
						<td>
							<span id="subjectsName">${subjectsName}</span>
						</td>
						<td>
							<span id="goodsVariableID">${goodsVariableID}</span>
						</td>
						<td>
							<span id="price">${price}</span>
						</td>
						<td>
							<span id="quantity">${quantity}</span>
						</td>
						<td>
							<span id="money">${money}</span>
						</td>
						<td>
							<span id="depotType">${depotType}</span>
						</td>
						<td>
							<span id="loan">${loan}</span>
						</td>
						<td>
							<span id="forLoan">${forLoan}</span>
						</td>
						<td>
							<span id="direction">${direction}</span>
						</td>
						<td>
							<span id="balance">${balance}</span>
						</td>
						<td>
							<span id="taxstatus">
							<s:if test="taxstatus01==01">待主管审核</s:if>
								<s:elseif test="taxstatus==02">待经理审核</s:elseif>
								<s:elseif test="taxstatus==03">待财务审计审核</s:elseif>
								<s:elseif test="taxstatus==04">可报税</s:elseif>
								<s:elseif test="taxstatus==10">驳回作废</s:elseif>
								<s:else></s:else></span>
						</td>
						<td>
							<span id="status">
								<s:if test="status==04">待主管审核</s:if>
								<s:elseif test="status==05">待会计审核</s:elseif>
								<s:elseif test="status==06">待出纳审核</s:elseif>
								<s:elseif test="status==07">已审核</s:elseif>
								<s:elseif test="status==20">税务单据</s:elseif>
								<s:else>驳回作废</s:else>
							</span>
						</td>
						<td>
							<a href="#" onclick="fj('${val[0]}')" class="fj">附件</a>
						</td>
						</tr>
                    </s:iterator>
            </tbody>
        </table>
        <c:import url="../../../page_navigator.jsp">
            <c:param name="actionPath" value="ea/goodscashier/ea_getProCashierList.jspa?search=${search}&pageNumber=${pageNumber}&sdate=${sdate}&edate=${edate}&period=${period}">
            </c:param>
        </c:import>
        
          <!--搜索窗口 -->
          <form name="SearchForm" id="SearchForm" method="post">
        <div class="jqmWindow" style="width: 400px;right: 25%;top:10%;" id="jqModelSearch">
            	<input type="submit" name="submit" style="display:none"/>
	            <div class="drag">
	                    查询信息
	                    <div class="close">
	                    </div>
	            </div>
                <table width="396" id="SearchTable">
					<tr>
                        <td width="123" align="right">单据类别：</td>
						<td >
						<select name="cashierBills.billsType" id="btype"></select>
						</td>
                   </tr>
                    <tr>
                        <td align="right">黏贴单编号：</td>
                        <td><input id="journalNum"  style="width:195px"  name="cashierBills.journalNum" /></td>
                    </tr>
                    <tr><td align="right">责任人：</td>
                        <td>
                         <s:select list="%{#request.stafflist}"  style="width:200px"  headerKey="" headerValue="请选择"  listKey="staffID" name="cashierBills.staffID"  listValue="staffName"   theme="simple" >
                         </s:select>
                       </td>
                    </tr>
                     <tr><td align="right">物品名称：</td>
                        <td><input id="goodsName"  style="width:195px"  name="cashierBills.goodsName" /></td>
                    </tr>
                     <tr><td align="right">物品编码：</td>
                         <td><input id="goodsCoding"  style="width:195px"  name="cashierBills.goodsCoding" /></td>
                    </tr>
                  <tr>
                   <td align="right">
                            部门名称：                        </td>
                      <td >
                        <select id="departmentID" name="cashierBills.departmentID" style="width:200px"><option value="">请选择</option></select>
                    </td>
                  </tr>
                   <tr>
                        <td align="right" >制单日期：</td>
                        <td style="width: 200px"><input id="sdate"   name="sdate" onfocus="date(this);" style="width: 85px"/>至<input id="edate"    name="edate" onfocus="date(this);" style="width: 85px"/></td>
                    </tr>
                  <tr>
                   <td align="right">
                           往来单位：                        </td>
                        <td><input id="ccID"  style="width:195px"  name="cashierBills.ccompanyname" /><a id="wldw" href="#">选择</a></td>
                  </tr>
                  <tr>
                   <td align="right">
                            往来个人：                        </td>
                        <td><input id="cuID"  style="width:195px"  name="cashierBills.contactUserName" /><a id="wlgr" href="#">选择</a></td>
                  </tr>
                </table>
            <div align="center">
              <input type="button" class="input-button" id="tosearch" value=" 查询 " /><input name="search" type="hidden" value="search" />
            </div>
            </div>
            </form>
            
          <%-- ajax往来单位列表 --%>       
     <form name="selectcompanyForm" id="selectcompanyForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none;width: 1300px" />
			<div class="jqmWindow jqmWindowcss1" style="top:4%;" id="companyjqModel">
				<div class="content1" style="width:100%">
                     <div class="contentbannb">
						<div class="drag">
							选择往来单位
						</div>
					</div>
  <table width="99%" height="33" id="searchcompany"  border="0" align="center" cellpadding="0" cellspacing="0" style=" margin-top:5px;background:#FFFFFF;">
    <tr>
    <td width="70" align="right">单位名称：</td>
      <td width="60"><input name="ccompanyID" class="input" id="ccompanyID" size="10" style="margin-left:2px;"/></td>
        <td width="70" align="right">往来关系：</td>
        <td  width="85"><s:select list="connectionlist" listKey="codeValue" id="contactConnections" listValue="codeValue" headerKey="" headerValue="--全部--" name="contactConnections" theme="simple"></s:select> </td>
      <td height="33"><input type="button" class="btn02" id="searchcc" name="button7" value="查询" />
      <input type="button" class="btn02" id="qdcompany" name="button5" value="确定" />
      <input type="button" class="btn02 xzdw" name="button" value="新增" />
       <input type="button" class="btn02 JQueryreturnsCcompany" name="button4" value="关闭" /></td>
     <td width="50"><a id="dwsy" title="0" href="#">上一页</a></td>
     <td width="50"><a id="dwxy"  title="0" href="#">下一页</a></td>
     <td width="70"><a id="dwzy">共&nbsp;&nbsp; <span style="color:red"  id="zycount"></span>&nbsp;&nbsp; 页</a></td>
    </tr>
  </table>
  <table width="99%" border="0" align="center"  cellpadding="0"  cellspacing="0" style="margin-top:5px;margin-bottom:5px;height:400px;">
    <tr>
      <td width="99%" valign="top" align="left"> 
       <div id="body_02cc" style="margin-top:2px;display:none;height:450px;width:100%;overflow: scroll;height:450px;">
       </div>
    </td>
    </tr>
  </table>
</div>
			</div>
			<s:token></s:token>
		</form>
		 <%-- ajax往来个人 --%>  
		 
		 <form name="selectuserForm" id="selectuserForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<div class="jqmWindow jqmWindowcss1" style="top:5%;" id="userjqModel">
				<div class="content1" style="width:100%">
                     <div class="contentbannb">
						<div class="drag">
							选择往来个人
						</div>
					</div>
  <table width="99%" height="33" id="searchuser"  border="0" align="center" cellpadding="0" cellspacing="0" style=" margin-top:5px;background:#FFFFFF;">
    <tr>
    <td width="40" align="right">姓名：</td>
      <td width="50"><input name="contactUserID" class="input" id="contactUserID" size="10" style="margin-left:2px;"/></td>
      <td width="70" align="right">往来关系：</td>
        <td  width="100"><s:select list="codeRelationList" listKey="codeValue" listValue="codeValue" headerKey="" headerValue="--全部--" id="relation" name="relation" theme="simple"></s:select> </td>
      <td height="33"><input type="button" class="btn02" id="searchuu" name="button7" value="查询" />
      <input type="button" class="btn02" id="qduser" name="button5" value="确定" />
       <input type="button" class="btn02 xzgr"  name="button5" value="新增" />
       <input type="button" class="btn02 JQueryreturnsUser" name="button4" value="关闭" /></td>
         <td width="50"><a id="grsy" title="0" href="#">上一页</a></td>
     <td width="50"><a id="grxy"  title="0" href="#">下一页</a></td>
     <td width="70"><a id="grzy">共&nbsp;&nbsp; <span style="color:red" id="grzycount"></span>&nbsp;&nbsp; 页</a></td>
    </tr>
  </table>
  <table width="99%" border="0" align="center"  cellpadding="0"  cellspacing="0" style="margin-top:5px;margin-bottom:5px;height:450px;">
    <tr>
      <td width="99%" valign="top" align="left"> 
       <div id="body_02cu" style="margin-top:2px;display:none;height:450px;width:100%;overflow: scroll;height:450px;">
       </div>
    </td>
    </tr>
  </table>
</div>
			</div>
			<s:token></s:token>
		</form>       
            
            
 <form name="cstaffForm" id="cstaffForm" method="post" >
 <div class="jqmWindow jqmWindowcss6" style="width: 1200px;top:1%" id="jqModels">
       <div class="drag">出纳明细单
	    <div class="close"></div></div>
 			<div class="content">
     <div class="contentbannb"></div>
  <table width="99%" border="0" id="table3" align="center" cellpadding="0" cellspacing="0" style="background:#FFFFFF;" class="table">
  <tr>
      <td height="20" align="right">粘贴单编号：</td>
      <td><span id="journalNum"></span></td>
      <td align="right">单据类别：</td>
      <td><span id="billsType"></span>
        </td>
       <td align="right">制单日期：</td>
      <td><span id="cashierDate"></span></td>
    </tr>
    <tr>
       <td height="20" width="7%" align="right"><span class="STYLE1">公司：</span></td>
       <td ><span id="companyname"></span></td>
       <td align="right">部门：</td>
       <td align="left" ><span id="departmentname"></span>
	       </td>
      <td align="right"><div id="u1170_rtf">责任人：</div></td>
       <td width="15%" >
      <span id="staffname"></span>
	       </td>
    </tr>
    <tr>
       <td height="20" width="7%" align="right"><span class="STYLE1">银行账号：</span></td>
       <td >${cashierBillsVO.companyBankNum}</td>
        <td align="right">票据支票管理:</td>
       <td align="left" >${cashierBillsVO.billCheck}</td>
      <td align="right"></td>
       <td width="15%" align="left" ></td>
    </tr>
  </table>
<table width="99%" height="200px" border="0" align="center" cellpadding="0" cellspacing="0" style="margin-top:5px">
  <tr>
    <td valign="top">
<div id= "Layer1" style= "position:absolute; width:100%; height:195px; overflow: scroll;">
<table width="2200" align="center" cellpadding="0" cellspacing="0" class="table" id="goodtable">
  <tr>
    <th height="30" align="center" bgcolor="#E4F1FA">款源日期</th>
    <th align="center"  bgcolor="#E4F1FA">入账日期</th>
    <th width="75" align="center"  bgcolor="#E4F1FA">有效日期</th>
    <th width="110" align="center"  bgcolor="#E4F1FA">批号/期号</th>
    <th align="center" bgcolor="#E4F1FA">品名编号</th>
    <th align="center" bgcolor="#E4F1FA">统一分类条码</th>
     <th align="center" bgcolor="#E4F1FA">费用或品名名称</th>
         <th width="60" align="center" bgcolor="#E4F1FA">类型</th>
    <th width="60" align="center" bgcolor="#E4F1FA">品牌</th>
    <th width="60" align="center" bgcolor="#E4F1FA">型号</th>
    <th width="60" align="center" bgcolor="#E4F1FA">品牌规格</th>
    <th align="center" width="140" bgcolor="#E4F1FA">标识条码</th>
    <th width="60" align="center" bgcolor="#E4F1FA">单位</th>
    <th align="center" bgcolor="#E4F1FA">数量</th>
    <th align="center" bgcolor="#E4F1FA">重量</th>
    <th align="center" bgcolor="#E4F1FA">箱规格</th>
    <th align="center" width="140" bgcolor="#E4F1FA">单价管理</th>
    <th align="center" bgcolor="#E4F1FA">单价</th>
    <th align="center" bgcolor="#E4F1FA">金额</th>
    <th align="center"  width="120" bgcolor="#E4F1FA">费用类别</th>
    <th align="center" width="140" bgcolor="#E4F1FA">科目</th>
     <th align="center" width="140" bgcolor="#E4F1FA">摘要</th>
    <th align="center"  width="120" bgcolor="#E4F1FA">库房管理</th>
    <th align="center" bgcolor="#E4F1FA">借方金额</th>
    <th align="center" bgcolor="#E4F1FA">贷方金额</th>
    <th align="center" bgcolor="#E4F1FA">方向</th>
    <th align="center" bgcolor="#E4F1FA">余额</th>
  </tr>
  <tr  >
    <td height="30" align="center" bgcolor="#FFFFFF">
      <span id="startDate"></span></td>
    <td align="center" bgcolor="#FFFFFF">
     <span id="endDate"></span></td>
     
         <td align="center" bgcolor="#FFFFFF">
     <span id="periodDate"></span>${periodDate}</td>
         <td align="center" bgcolor="#FFFFFF">
     <span id="batchNumber"></span>${batchNumber}</td>
     
    <td align="center" bgcolor="#FFFFFF">
    <span id="goodsCoding"></span></td>
     <td align="center" bgcolor="#FFFFFF"> <span id="defaultStorage"></span></td>
     <td align="center" bgcolor="#FFFFFF"><span id="goodsName"></span></td>
    <td align="center" bgcolor="#FFFFFF"><span id="typeID"></span>${typeID}</td>
    <td align="center" bgcolor="#FFFFFF"><span id="mnemonicCode"></span>${mnemonicCode}</td>
    <td align="center" bgcolor="#FFFFFF"><span id="model"></span>${model}</td>
    <td align="center" bgcolor="#FFFFFF"><span id="standard"></span>${standard}</td>
     <td align="center" bgcolor="#FFFFFF">
   <span id="identifyingCode"></span></td>
    <td align="center" bgcolor="#FFFFFF"><span id="goodsVariableID"></span></td>
    <td align="center" bgcolor="#FFFFFF">
     <span id="quantity"></span>
    </td>
    <td align="center" bgcolor="#FFFFFF">
     <span id="weight"></span>
    </td>
    <td align="center" bgcolor="#FFFFFF">
    <span id="boxStandard"></span>
    </td>
    <td align="center" bgcolor="#FFFFFF">
   <span id="priceManage"></span>
       </td>
    <td align="center" bgcolor="#FFFFFF">
   <span id="price"></span></td>
    <td align="center" bgcolor="#FFFFFF">
     <span id="money"></span></td>
    <td align="center" bgcolor="#FFFFFF">
    <span id="costType"></span></td>
    <td align="center" bgcolor="#FFFFFF">  
    <span id="subjectsName"></span>
    </td>
    <td align="center" bgcolor="#FFFFFF">
    <span id="reasonThing"></span>
    </td>
    <td align="center" bgcolor="#FFFFFF">
   <span id="depotType"></span></td>
    <td align="center" bgcolor="#FFFFFF">
    <span id="loan"></span></td>
    <td align="center" bgcolor="#FFFFFF">
   <span id="forLoan"></span></td>
    <td align="center" bgcolor="#FFFFFF">
    <span id="direction"></span></td>
    <td align="center" bgcolor="#FFFFFF">
  <span id="balance"></span></td>
  </tr>
</table>
</div>
    </td>
  </tr>
</table>
  <table width="99%" height="30" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF" style="margin-bottom:5px;">
    <tr>
      <td width="1%" bgcolor="#FFFFFF">&nbsp;</td>
     <td >
		财务仓库：<span id="bankDepotName"></span>
	</td>
    <td >
		实物仓库：<span id="afterDiscount"></span>
	</td>
	 <td >
		资料仓库：<span id="dataDepotName"></span>
	</td>
    </tr>
  </table>
  <table width="99%" border="0" id="table4" align="center" cellpadding="0" cellspacing="0" style="background:#FFFFFF;" class="table">
    <tr>
      <td width="10%" height="30" align="right"><span class="STYLE1">往来单位：</span></td>
      <td width="15%">
       <span id="ccompanyname"></span>
     </td>
      <td width="10%" align="right"><span class="STYLE1">单位电话：</span></td>
      <td width="15%"><span id="companyTel"></span></td>
     <td width="10%" align="right"><span class="STYLE1">单位负责人：</span></td>
      <td width="15%"><span id="cresponsible"></span></td>
      <td width="10%" align="right"><span class="STYLE1">银行账户：</span></td>
      <td width="15%"><span id="accountNum"></span></td>
    </tr>
    <tr>
      <td height="30" align="right"><span class="STYLE1">单位负责人电话：</span></td>
      <td><span id="responsibleTel"></span></td>
      <td align="right"><span class="STYLE1">公司地址：</span></td>
      <td ><span id="companyAddr"></span></td>
      <td align="right"><span class="STYLE1">行业类别：</span></td>
      <td><span id="industryType"></span></td>
       <td height="30" align="right"><span class="STYLE1">单位往来关系：</span></td>
      <td><span id="contactConnections"></span></td>
    </tr>
  </table>
  <table width="99%" border="0" align="center" id="table5" cellpadding="0" cellspacing="0" style="background:#FFFFFF;" class="table">
    <tr>
      <td width="10%" height="30" align="right"><span class="STYLE1">往来个人：</span></td>
      <td width="15%">
      <span id="contactUserName"></span>
     </td>
      <td width="10%" align="right"><span class="STYLE1">电话：</span></td>
      <td width="15%"><span id="tel"></span></td>
      <td width="10%" align="right"><span class="STYLE1">个人身份证号：</span></td>
      <td width="15%"><span id="staffIdentityCard"></span></td>
     <td align="right"><span class="STYLE1">银行账号：</span></td>
      <td><span id="userAccountNum"></span></td>
       </tr>
    <tr>
      <td height="30" align="right"><span class="STYLE1">QQ：</span></td>
      <td ><span id="userQq"></span></td>
      <td align="right"><span class="STYLE1">邮箱：</span></td>
      <td><span id="email"></span></td>
      <td align="right"><span class="STYLE1">地址：</span></td>
      <td><span id="userAddr"></span></td>
       <td width="10%" align="right"><span class="STYLE1">往来关系：</span></td>
      <td width="15%"><span id="phone"></span></td>
    </tr>
  </table>
  <table width="99%"  border="0" align="center" cellpadding="0" cellspacing="0" style="margin-top:5px; margin-bottom:5px;">
    <tr>
    <!-- 
    <td height="30" align="center">
      <input type="button" class="btn001 JQueryprint" name="button" value="打印预览" /></td> -->
      <td height="30" align="center">
      <input type="button" class="btn001 JQueryClose" name="button2" value="返回" /></td>
    </tr>
  </table>
</div>
</form>
    </body>
</html>
