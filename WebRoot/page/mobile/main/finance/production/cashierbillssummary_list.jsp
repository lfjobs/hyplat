<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page language="java" pageEncoding="UTF-8" %>
        <%@ taglib uri="/struts-tags" prefix="s" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
         <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
        <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
        + request.getServerName() + ":" + request.getServerPort()  
        + path + "/"; %> <html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="cache-control" content="no-cache"/>
        <title>出纳单据汇总统计</title>
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
        <script language="javascript" type="text/javascript" src="<%=basePath%>js/ea/finance/production/cashierbillssummary_list.js"></script>
        <script type="text/javascript">
        	var cashierBillsID=""; 
        	var search="${search}";
        	var basePath="<%=basePath%>";
        	var pNumber=${pageNumber};
        	var sdate="${sdate}";
        	var edate="${edate}";
        	var notoken = 0;
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
                    <th width="100" align="center">
                        单据类别
                    </th>
                    <th width="100" align="center">
                       部门
                    </th>
                    <th width="80" align="center">
                        责任人
                    </th>
                      <th width="90" align="center">
                       制单日期
                    </th>
                     <th width="80" align="center">
                       实物仓库
                    </th>
                     <th width="80" align="center">
                      财务仓库
                    </th>
                     <th width="80" align="center">
                        资料仓库
                    </th>
                     <th width="90" align="center">
                        往来单位
                    </th>
                     <th width="90" align="center">
                          单位银行账号
                    </th>
                      <th width="90" align="center">
                        单位往来关系
                    </th>
                     <th width="90" align="center">
                       往来个人
                    </th> 
                     <th width="90" align="center">
                      个人银行账号
                    </th>
                      <th width="90" align="center">
                        个人往来关系
                    </th>
                       <th width="90" align="center">
                      主管审核人
                    </th>
                     <th width="90" align="center">
                       会计审核人
                    </th>
                     <th width="90" align="center">
                       出纳审核人
                    </th>
                    <th width="90" align="center">
                       单据状态
                    </th>
                     <th width="90" align="center">
                       附件管理
                    </th>
                </tr>
            </thead>
            <tbody>
             <s:iterator value="pageForm.list">
                        <tr id="${cashierBillsID}">
                            <td>
                                <input type="radio" name="a"  class="JQuerypersonvalue" value="${cashierBillsID}"/>
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
                                <span id="afterDiscount">${afterDiscount}</span>
                            </td>
                              <td>
                                <span id="bankDepotName">${bankDepotName}</span>
                            </td>
                             <td>
                                <span id="dataDepotName">${dataDepotName}</span>
                            </td>
                            <td>
                                <span id="ccompanyname">${ccompanyname}</span>
                            </td>
                            <td>
                                <span id="accountNum">${accountNum}</span>
                            </td>
                            <td>
                                <span id="contactConnections">${contactConnections}</span>
                            </td>
                            <td>
                                <span id="contactUserName">${contactUserName}</span>
                            </td> 
                             <td>
                                <span id="userAccountNum">${userAccountNum}</span>
                            </td>
                             <td>
                                <span id="phone">${phone}</span>
                            </td>
                            <td>
                                <span id="responsible">${responsible}</span>
                            </td>
                            <td>
                                <span id="accountant">${accountant}</span>
                            </td>
                             <td>
                                <span id="cashier">${cashier}</span>
                            </td>
                            <td>
                                <span id="xsstatus">
                                <s:if test='status=="00"'>
                                         作废
                                </s:if>
                                 <s:if test='status=="01"'>
                                         待主管审核
                                </s:if>
                                 <s:if test='status=="02"'>
                                         待会计审核
                                </s:if>
                                 <s:if test='status=="03"'>
                                         待出纳审核
                                </s:if>
                                 <s:if test='status=="04"'>
                                         已审核
                                </s:if>
                                <s:if test='status=="10"'>
                                         驳回作废
                                </s:if>
                                </span>
                                <span id="status" style="display:none">${status}</span>
                            </td>
                            <td>
                               <a href="#" onclick="fj('${cashierBillsID}')" class="fj">附件</a>
                            </td>
                        </tr>
                    </s:iterator>
            </tbody>
        </table>
        <c:import url="../../../page_navigator.jsp">
            <c:param name="actionPath" value="ea/cashiersummarycompany/ea_getCashierList.jspa?search=${search}&pageNumber=${pageNumber}&sdate=${sdate}&edate=${edate}">
            </c:param>
        </c:import>
          <!--搜索窗口 -->
          <form name="SearchForm" id="SearchForm" method="post">
        <div class="jqmWindow" style="width: 400px;right: 25%;top:10%" id="jqModelSearch">
            	<input type="submit" name="submit" style="display:none"/>
	            <div class="drag">
	                    查询信息
	                    <div class="close">
	                    </div>
	            </div>
                <table width="396" id="SearchTable">
					<tr>
                        <td width="123" align="right">单据类别：</td>
						 <td>
						 <select id="billsType" style="width:200px"  name="cashierBillsVO.billsType">
					    	<option value="">全部</option></select>
						 </td>
                   </tr>
                    <tr>
                        <td align="right">黏贴单编号：</td>
                        <td><input id="journalNum"  style="width:195px"  name="cashierBillsVO.journalNum" /></td>
                    </tr>
                    <tr><td align="right">责任人：</td>
                        <td>
                         <s:select list="%{#request.stafflist}"  style="width:200px"  headerKey="" headerValue="请选择"  listKey="staffID" name="cashierBillsVO.staffID"  listValue="staffName"   theme="simple" >
                         </s:select>
                       </td>
                    </tr>
                  <tr>
                   <td align="right">
                            部门名称：                        </td>
                      <td width="314">
                        <select id="departmentID" name="cashierBillsVO.departmentID" ><option value="">请选择公司</option></select>
                    </td>
                  </tr>
                   <tr>
                        <td align="right" >制单日期：</td>
                        <td style="width: 200px"><input id="sdate"   name="sdate" onfocus="date(this);" style="width: 85px"/>至<input id="edate"    name="edate" onfocus="date(this);" style="width: 85px"/></td>
                    </tr>
                  <tr>
                   <td align="right">
                           往来单位：                        </td>
                        <td><input id="ccID"  style="width:195px"  name="cashierBillsVO.ccompanyname"  /><a id="wldw" href="#">选择</a></td>
                  </tr>
                  <tr>
                   <td align="right">
                            往来个人：                        </td>
                        <td><input id="cuID"  style="width:195px"  name="cashierBillsVO.contactUserName" /><a id="wlgr" href="#">选择</a></td>
                  </tr>
                   <tr><td align="right">单位往来关系：</td>
                        <td> <s:select list="%{#request.connectionlist}"  style="width:200px"  headerKey="" headerValue="请选择"  listKey="codeValue" name="cashierBillsVO.contactConnections"  listValue="codeValue"   theme="simple" >
                         </s:select></td>
                    </tr>
                    <tr><td align="right">个人往来关系：</td>
                        <td><s:select list="%{#request.codeRelationList}"  style="width:200px"  headerKey="" headerValue="请选择"  listKey="codeValue" name="cashierBillsVO.phone"  listValue="codeValue"   theme="simple" >
                         </s:select></td>
                    </tr>
                    <tr><td align="right">单据状态：</td>
                        <td>
                          <input type="radio" name="cashierBillsVO.status" checked="checked" value=""/>全部&nbsp;&nbsp;
                          <input type="radio" name="cashierBillsVO.status" value="10"/>作废&nbsp;&nbsp;
                          <input type="radio" name="cashierBillsVO.status" value="01"/>待主管审核&nbsp;&nbsp;
                       </td>
                    </tr>
                     <tr><td align="right"></td>
                        <td>
                          <input type="radio" name="cashierBillsVO.status" value="02"/>待会计审核
                          <input type="radio" name="cashierBillsVO.status" value="03"/>待出纳审核
                           <input type="radio" name="cashierBillsVO.status" value="04"/>已审核
                       </td>
                    </tr>
                </table>
            <div align="center">
              <input type="button" class="input-button" id="tosearch" value=" 查询 " /><input name="search" type="hidden" value="search" />
            </div>
            </form>
     <%-- ajax往来单位列表 --%>       
     <form name="selectcompanyForm" id="selectcompanyForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
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
		
             <iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
    </body>
</html>
