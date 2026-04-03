<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page import="hy.ea.bo.Company"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
        + request.getServerName() + ":" + request.getServerPort()
        + path + "/"; 
		Company c = (Company)session.getAttribute("currentcompany"); 
%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="cache-control" content="no-cache"/>
        <title>银行账户管理</title>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>css/admin_main.css"/>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" type="text/css" href="<%=basePath%>css/overlayer.css"/>
		<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>        
        <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
        <script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>        
        <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
        <script language="javascript" type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>        
        <script src="<%=basePath%>js/ea/finance/bankaccountManager.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/common/organizationTree.js"></script>
        <script type="text/javascript">
		 var  basePath='<%=basePath%>';           
         var  pNumber =${pageNumber};  
         var  search='${search}';
         var  accid = '';
         var  token=0;
		 var treeids = "<%=c.getCompanyID()%>";
         var treename = "<%=c.getCompanyName()%>";
		</script>     
		
	</head>
	<body >
        <div class="main_main">
            <table class="JQueryflexme">
                <thead>
                    <tr class="tablewith">
                        <th width="40" align="center">
                            请选择
                        </th>
                        <th width="40" align="center">
                            序号
                        </th>
						<th width="100" align="center">
                            公司		
                        </th>
						<th width="100" align="center">
                            部门		
                        </th>
						<th width="50" align="center">
                            责任人		
                        </th>
						<th width="50" align="center">
                            户主		
                        </th>
                        <th width="100" align="center">
                            银行账号		
                        </th>
                         <th width="100" align="center">
                            所属银行
                        </th>
                         <th width="80" align="center">
                         	省份代码
                        </th>
                         <th width="80" align="center">
                           币种
                        </th>
						<th width="60" align="center">
                            账户性质		
                        </th>
						<th width="80" align="center">
                            往来关系		
                        </th>
                    </tr>
                </thead>
                <tbody> 
                    <s:iterator value="pageForm.list" status="number">
                        <tr id="${accid}"  >
                            <td>
                                <input type="radio" name="a"  class="JQuerypersonvalue" value="${accid}"/>
                            </td>
                            <td>
                            	<s:property value="%{#number.index+1}"/>
                            </td>
							<td>
                                <span id="companyname">${companyname}</span>
                            </td>
							<td>
                                <span id="orgname">${orgname}</span>
                            </td>
							<td>
                                <span id="responser">${responser}</span>
                            </td>
							<td>
                                <span id="accountowner">${accountowner}</span>
                            </td>
                            <td>
                                <span id="account">${account}</span>
                            </td>
                            <td>
                               <span id="banksx">${banksx}</span>
                            </td>
                            <td>
                                <span id="provcode">${provcode}</span>
                            </td>
							<td>
                               <span id="currency">${currency}</span>
                            </td>
							<td>
                               <span id="accounttype">${accounttype}</span>
                            </td>
                            <td>
                                <span id="relation">${relation}</span>
                                <span id="pkey" style="display:none">${pkey}</span>
                                <span id="accid" style="display:none">${accid}</span>
                                <span id="companyid" style="display:none">${companyid}</span>
                                <span id="orgid" style="display:none">${orgid}</span> 
                            </td>  
                        </tr> 
                    </s:iterator>
                </tbody>
            </table>
            <c:import url="../../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/bankaccountManager/ea_getBankAccountList.jspa?pageNumber=${pageNumber}&search=${search}">
                </c:param>
            </c:import>
        </div> 
 	 	<div class="contentbannb jqmWindow " style="top: 10%;width: 700px;right: 30%" id="jqModel">
            <form name="cstaffForm" id="cstaffForm" method="post">
                <input type="submit" name="submit" style="display:none"/>
                <div class="drag">
                    详细信息
                    <div class="close">
                    </div>
                </div>
  				<table cellpadding="0px" cellspacing="0px" name="stafftable" id="stafftable" width="690" style="margin-left: 10px;" height="103">
					<tr>
				    	<td width="100" height="37" align="right">公司：</td>
				    	<td width="200"><select name="bankAccount.companyid" id="deptID">
				    	  <option value="" selected="selected"> 全部公司 </option>
				    	  </select>				    	</td>
				    	<td width="100" height="37" align="right">部门：</td>
				    	<td width="200"><select id="oorgID" name="bankAccount.orgid">
                          <option value=""> 请选择部门 </option>
                        </select></td>
				    </tr> 
				    <tr>
				      <td height="37" align="right">责任人：</td>
				      <td><input name="bankAccount.responser" id="responser" class="put3"/></td>
				      <td height="37" align="right">户主</td>
				      <td><input name="bankAccount.accountowner" id="accountowner" class="put3"/></td>
			      </tr>
				    <tr>
				    	<td width="100" height="37" align="right">银行账号：</td>
				    	<td><input name="bankAccount.account" id="account" class="put3"/></td>
				    	<td width="100" height="37" align="right">所属银行：</td>
				    	<td> 
				    		 <s:select list="%{#request.bankSXList}" id="banksx" listKey="esx" listValue="name" name="bankAccount.banksx" theme="simple"></s:select>				    	</td>
				    </tr>
				    <tr>
				    	<td width="100" height="37" align="right">省份：</td>
				    	<td>
				    		<s:select list="%{#request.provCodeList}" id="provcode" listKey="code" listValue="name" name="bankAccount.provcode" theme="simple"></s:select>				    	</td>
				    	<td width="100" height="37" align="right">币种：</td>
				    	<td>
				    		 <s:select list="%{#request.currencyList}" id="currency" listKey="code" listValue="name" name="bankAccount.currency" theme="simple"></s:select>				    	</td>
				    </tr>
				    <tr>
				      <td height="37" align="right">账户性质：</td>
				      <td><select name="bankAccount.accounttype" id="accounttype">
				    	  	<option value="对公">对公</option>
							<option value="对私">对私</option>
				    	  </select></td>
				      <td height="37" align="right">往来关系：</td>
				      <td><select name="bankAccount.relation" id="relation">
				    	  	<option value="公司账户">公司账户</option>
							<option value="部门账户">部门账户</option>
							<option value="个人账户">个人账户</option>
							<option value="客户账户">客户账户</option>
							<option value="供应商账户">供应商账户</option>
				    	  </select></td>
			      </tr>
				    <tr>
				    	<td colspan="4" align="center" >
				    		<input type="hidden" name="bankAccount.accid" id="accid" />
				            <input type="hidden" name="bankAccount.pkey" id="pkey" />
							<input type="hidden" name="bankAccount.bindto" id="bindto" />
				            <input type="hidden" name="bankAccount.companyname" id="companyname" />
				            <input type="hidden" name="bankAccount.orgname" id="orgname" />
							<input type="hidden" name="bankAccount.cdate" id="cdate" />
							<input type="hidden" name="bankAccount.mdate" id="mdate" />
						    <input type="button"  class="input-button JQuerySubmit" style="cursor:pointer;width:80px;" value="提交" />
						    <input type="button"  class="input-button JQueryreturn" style="cursor:pointer;width:80px;" value="取消" /></td>
				    </tr>
				  </table>
				   <s:token></s:token> 
			</form>
	</div>              
    <iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
    </body>
</html>