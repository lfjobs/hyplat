<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/"; 
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>客户成交服务</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script src="<%=basePath%>js/ea/validate.js"  type="text/javascript"></script>
<script src="<%=basePath%>js/ea/human/office/SersonnelSystem/TransactionService.js"></script>

<script  type="text/javascript">
   var 	transactionServiceID  = '';
   var  basePath='<%=basePath%>';           
   var  pNumber =${pageNumber};  
   var  search='${search}';
   var  token=0;
</script>
</head>
<body>
<form style="display: none;" name="addressForm" id="addressForm" method="post"><s:token></s:token><input type="submit" name="submit" style="display:none"/>
<input type="" name="submit" style="display:none"/>
</form>

<div id="main_main" class="main_main">
  <table   class="address">
  <thead>
	 	    <tr>
	 	    <th width="40" align="center">请选择</th>
            <th width="40" align="center" >序号</th>
            <th width="100" align="center" >客户成交序号</th>
            <th width="120" align="center">服务ID</th>
            <th width="120" align="center" >客户编号</th>
            <th width="120" align="center" >客户名称</th>   
            <th width="120" align="center" >客户联系人</th>
            <th width="120" align="center">客户服务类型</th>
            <th width="120" align="center">客户服务方式</th>
            <th width="120" align="center" >公司服务部门</th>
            <th width="120" align="center" >公司服务执行人</th>  
      </tr>
    </thead>
		<tbody>
		<%
              int number = 1; %>
          <s:iterator value="pageForm.list">
          <tr class="td_bg01 saveAjax" id="${transactionServiceID}">
            <td class="td_bg01">
              <input type="radio" name="a"  class="JQuerypersonvalue" value="${transactionServiceID}"/>
            </td>
            <td class="td_bg01">
                <span><%=number%></span>
			</td>
            <td class="td_bg01">
               <span id="transactionServiceCode" class="datas">${transactionServiceCode}</span>
			</td>
			<td class="td_bg01">
              <span id="serviceCode">${serviceCode}</span>
           	</td>
            <td class="td_bg01">
             <span id="customerCode">${customerCode}</span>
            </td>
            <td class="td_bg01">
             <span id="customerName">${customerName}</span>
            </td>
            <td class="td_bg01">
             <span id="customerContact">${customerContact}</span>
            </td>
            <td class="td_bg01">
             <span id="serviceType">${serviceType}</span>
            </td>
            <td class="td_bg01">
             <span id="serviceMode">${serviceMode}</span>
            </td>
             <td class="td_bg01">
             <span id="ServiceDepartment">${ServiceDepartment}</span>
            </td>
            <td class="td_bg01">
               <span id="ServiceExecutor" >${ServiceExecutor}</span>
               <span id="transactionServiceKey" style="display:none">${transactionServiceKey}</span>
			   <span id="transactionServiceID" style="display:none">${transactionServiceID}</span>
			</td>     
          </tr>
          <%
               number++; %>
          </s:iterator>
    </tbody>
  </table>
 <c:import url="../../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/transactionservice/ea_getListTransactionService.jspa?pageNumber=${pageNumber}&search=${search}"></c:param>
</c:import>
</div>

 <!--搜索窗口 -->
        <div class="jqmWindow " style="width: 270px;right: 35%; top:20%" id="jqModelSearch">
            <form name="postSearchForm" id="postSearchForm" method="post">
            	<input type="submit" name="submit" style="display:none"/>
                <div class="drag">
                    查询信息
                    <div class="close">
                    </div>
                </div>
                <table id="cataffSearchTable">
                    <tr>
                        <td>
                           客户成交序列号：
                        </td>
                        <td>   
                          <input name="transactionService.transactionServiceCode" />      
                        </td>
                    </tr>
					<tr>
                        <td>
                           客户名称：
                        </td>
                        <td>
                           <input   name="transactionService.customerName" />
                        </td>
                    </tr>
                </table>
                <div align="center">
                    <input type="button" class="input-button" id="tosearch" value=" 查询 " /><input name="search" type="hidden" value="search" />
                </div>
            </form>
        </div>
		
		
		<!--查看 -->
		<div class="jqmWindow jqmWindowcss" style="top: 10%" id="jqModel">
			 <form name="cstaffForm" id="cstaffForm" method="post" >
			       <div class="drag">客户成交服务
				    <div class="close"></div>
				    </div>
			<input type="submit" name="submit" style="display:none"/>
			 <div class="content">
			  <div class="contentbannb">
			  </div>
			 <table width="550" border="0" id="stafftable" align="center" cellpadding="0" cellspacing="0" style="margin-top:5px;margin-bottom:5px;">
			 <tr>
			   <td>
			     <table width="550" height="117" border="0" align="center" cellpadding="0" cellspacing="0" id="stafftable2" style="margin-top:5px;margin-bottom:5px;">
			      <tr>
			        <td width="100" height="37"  align="right">客户成交序号：</td>
			        <td width="148" ><input name="transactionService.transactionServiceCode" type="text" id="transactionServiceCode" size="20"/></td>
			        <td width="90"  align="right">服务ID：</td>
			        <td width="212" >
			         <input name="transactionService.serviceCode" type="text" id="serviceCode" size="20"/>
			        </td>
			      </tr>
			       <tr>
			        <td width="100" height="37"  align="right">客户编号：</td>
			        <td width="148" ><input name="transactionService.customerCode" type="text" id="customerCode" size="20"/></td>
			        <td width="90"  align="right">客户名称：</td>
			        <td width="212" >
			         <input name="transactionService.customerName" type="text" id="customerName" size="20"/>
			        </td>
			      </tr>
			       <tr>
			        <td width="100" height="37"  align="right">客户联系人：</td>
			        <td width="148" ><input name="transactionService.customerContact" type="text" id="customerContact" size="20"/></td>
			        <td width="90"  align="right">客户服务类型：</td>
			        <td width="212" >
			         <input name="transactionService.serviceType" type="text" id="serviceType" size="20"/>
			        </td>
			      </tr>
			        <tr>
			        <td width="100" height="37"  align="right">客户服务方式：</td>
			        <td width="148" ><input name="transactionService.serviceMode" type="text" id="serviceMode" size="20"/></td>
			        <td width="90"  align="right">公司服务部门：</td>
			        <td width="212" >
			         <input name="transactionService.ServiceDepartment" type="text" id="ServiceDepartment" size="20"/>
			        </td>
			      </tr>
			      <tr>
			        <td height="41"  align="right">公司服务执行人：</td>
			        <td ><input  id="ServiceExecutor" type="text"  class="input"  name="transactionService.ServiceExecutor" size="20"/>
			        <input  type="hidden" name="transactionService.transactionServiceID"  id="transactionServiceID" size="20"/>
			        <input type="hidden" name="transactionService.transactionServiceKey" id="transactionServiceKey" /></td>
			        </tr>
			    </table>
		       </td>
			  </tr>
			</table>
			</div>
			    <s:token></s:token>
			    <div align="center">
                    <input type="button" class="input-button" style="cursor:pointer;width:80px;" id="tosave" value=" 保存 " />
                	 <input type="button"  class="input-button JQueryreturn" style="cursor:pointer;width:80px;"  value="取消" />
                </div>
			</form>
	</div>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
</body>
</html>
