<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
        <%@ page language="java" pageEncoding="UTF-8" %>
        <%@ taglib uri="/struts-tags" prefix="s" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
         <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
        <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
        + request.getServerName() + ":" + request.getServerPort()
        + path + "/"; %>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="cache-control" content="no-cache"/>
        <title>待营销审核列表</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
        <script src="<%=basePath%>js/jquery.js" type="text/javascript">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/flexigrid.js">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js">
        </script>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
        <script language="javascript" type="text/javascript" src="<%=basePath%>js/ea/marketing/examine/marketingExamine_list.js"></script>
        <script language="javascript" type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
        <script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/common/organizationTree.js"></script>
        <script type="text/javascript">
        	var  treeID ="<%=session.getAttribute("organizationID")%>";
        	var  treeName ="<%=session.getAttribute("organizationName")%>";
        	var cashierBillsID="";
        	var search="${search}";
        	var basePath="<%=basePath%>";
        	var pNumber=${pageNumber};
        	var token=0;
        	var sdate="${sdate}";
        	var edate="${edate}";
        	var hostStatus="${cashierBillsVO.consultStatus}";
			
			document.onkeydown = function(evt){//捕捉回车 
				evt = (evt) ? evt : ((window.event) ? window.event : "") //兼容IE和Firefox获得keyBoardEvent对象
				var key = evt.keyCode ? evt.keyCode : evt.which; //兼容IE和Firefox获得keyBoardEvent对象的键值
				if (key == 13) { //判断是否是回车事件。
					if($("input#journalNum").val()==''){
						return false;
					}
		   			if($("input#journalNum").val()!=''){
		       			$("#SearchForm").attr("action", basePath+"ea/marketingExamine/ea_toSearch.jspa?pageNumber="+pNumber);
                    	document.SearchForm.submit.click();
					}
				}
			}
        </script>
    </head>
    <body>
        <form  name="CashierBillsform" id="CashierBillsform">
            <input type="submit" name="submit" style="display:none"/>
			<input  name="cashierBills.cashierBillsID" id="cashierBillsID" style="display:none"/>
			<input  name="cashierBills.consultStatus" id="status" style="display:none"/>
			<s:token/>
        </form>
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
                    <th width="80" align="center">
                        制单人
                    </th>
                     <th width="90" align="center">
                       制单日期
                    </th>
                     <th width="180" align="center">
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
                        营销审核人
                    </th>
                    <th width="90" align="center">
                        人事审核人
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
             <c:forEach var='arr' items="${pageForm.list}">
					<tr id="${arr[0] }">
						<td>
							<input type="radio" name="a" class="JQuerypersonvalue"
								value="${arr[0]}" />
						</td>
						<td>
							<span id="companyname">${arr[1]}</span>
						</td>
						<td>
							<span id="journalNum">${arr[2]}</span>
						</td>
						<td>
							<span id="billsType">${arr[3]}</span>
						</td>
						<td>
							<span id="departmentname">${arr[4]}</span>
						</td>
						<td>
							<span id="staffname">${arr[5]}</span>
						</td>
						<td>
							<span id="input">${arr[6]}</span>
						</td>
						<td>
							<span id="cashierDate">${fn:substring(arr[7], 0, 10)}</span>
						</td>
						<td>
							<span id="ccompanyname">${arr[8]}</span>
						</td>
						<td>
							<span id="accountNum">${arr[9]}</span>
						</td>
						<td>
							<span id="contactConnections">${arr[10]}</span>
						</td>
						<td>
							<span id="contactUserName">${arr[11]}</span>
						</td>
						<td>
							<span id="userAccountNum">${arr[12]}</span>
						</td>
						<td>
							<span id="phone">${arr[13]}</span>
						</td>
						<td>
							<span id="marketer">${arr[14]}</span>
						</td>
						<td>
							<span id="personnel">${arr[15]}</span>
						</td>
						<td>
							<span id="status">${arr[16]}</span>
						</td>
						<td>
							<a href="#" onclick="fj('${arr[0]}')" class="fj">附件</a>
						</td>
					</tr>
				</c:forEach>
            </tbody>
        </table>
        <c:import url="../../../page_navigator.jsp">
            <c:param name="actionPath" value="ea/marketingExamine/ea_getMarketingList.jspa?search=${search}&pageNumber=${pageNumber}&sdate=${sdate}&edate=${edate}">
            </c:param>
        </c:import>
          <!--搜索窗口 -->
          <form name="SearchForm" id="SearchForm" method="post">
        <div class="jqmWindow" style="width: 380px;right: 30%;top:10%" id="jqModelSearch">
            	<input type="submit" name="submit" style="display:none"/>
	            <div class="drag">
	                    查询信息
	                    <div class="close">
	                    </div>
	            </div>
                <table width="326" id="SearchTable">
					<tr>
                        <td align="right">单据类别：</td>
						<td>咨询跟踪单</td>
                   </tr>
                    <tr>
                        <td align="right">黏贴单编号：</td>
                        <td><input id="journalNum"  style="width:195px"  name="cashierBillsVO.journalNum" /></td>
                    </tr>
					<tr><td align="right">部门：</td>
                        <td> <select name="cashierBillsVO.departmentID"  style="width:200px" id="departmentID" >
                        <option value="">请选择</option>
                        </select></td>
                    </tr>
                    <tr><td align="right">责任人：</td>
                        <td>
                         <s:select list="%{#request.stafflist}"  style="width:200px"  headerKey="" headerValue="请选择"  listKey="staffID" name="cashierBillsVO.staffID"  listValue="staffName"   theme="simple" >
                         </s:select>
                       </td>
                    </tr>
                    <tr><td align="right">单位往来关系：</td>
                        <td> <s:select list="%{#request.connectionlist}"  style="width:200px"  headerKey="" headerValue="请选择"  listKey="codeValue" name="cashierBillsVO.contactConnections"  listValue="codeValue"   theme="simple" >
                         </s:select></td>
                    </tr>
                    <tr><td align="right">个人往来关系：</td>
                        <td><s:select list="%{#request.codeRelationList}"  style="width:200px"  headerKey="" headerValue="请选择"  listKey="codeValue" name="cashierBillsVO.phone"  listValue="codeValue"   theme="simple" >
                         </s:select></td>
                    </tr>
                    <tr>
                        <td width="123" align="right">查询单据：</td>
						<td >
					    	<select id="status" style="width:200px"  name="cashierBillsVO.consultStatus">
					    	<option value="1" selected="selected" >待营销审核</option>
					    	<option value="hostStatus">历史单据</option>
					    	</select>
						</td>
                    </tr>
                      <tr>
                        <td align="right" >制单日期：</td>
                        <td style="width: 200px"><input id="sdate"   name="sdate" onfocus="date(this);" style="width: 85px"/>至<input id="edate"    name="edate" onfocus="date(this);" style="width: 85px"/></td>
                    </tr>
                    <tr>
                      <td align="right" >往来单位：</td>
                        <td ><input id="sdate"   name="cashierBillsVO.ccompanyname" style="width: 188px"/></td>
                    </tr>
                    <tr>
                      <td align="right" >往来个人：</td>
                        <td ><input id="sdate"   name="cashierBillsVO.contactUserName" style="width: 188px"/></td>
                    </tr>
                </table>
            <div align="center">
              <input type="button" class="input-button" id="tosearch" value=" 查询 " /><input name="search" type="hidden" value="search" />
            </div>
            </div>
            </form>
             <iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
    </body>
</html>
