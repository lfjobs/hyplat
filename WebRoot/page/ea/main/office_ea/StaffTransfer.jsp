<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
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
        <title>人事调令单管理</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
        <script src="<%=basePath%>js/jquery.js" type="text/javascript">
        </script>
         <script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
        </script>
          <link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
        <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
        <script language="javascript" type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js">
        </script>
           <link rel="stylesheet" type="text/css" href="<%=basePath%>css/admin_main.css"/>
          <link rel="stylesheet" type="text/css" href="<%=basePath%>css/overlayer.css"/>
        <script type="text/javascript" src="<%=basePath%>js/common/organizationTree.js"></script>
       <script type="text/javascript" src="<%=basePath%>js/common/organizationTree.js"></script>
        <script>
        var  treeID ="<%=session.getAttribute("organizationID")%>";
        		var token = 0;
                var transferID = "";
                var b=true;
                var basePath='<%=basePath%>';
                var ppageNumber=${pageNumber};
                var psearch='${search}';
        </script>
        <script type="text/javascript" src="<%=basePath%>js/ea/office_ea/StaffTransfer.js"></script>

	</head>
	<body >
        <div class="main_main">
            <table class="JQueryflexme">
                <thead>
                    <tr class="tablewith">
                        <th width="40" align="center">
                            请选择
                        </th>
                         <th width="170" align="center">
                            公司
                        </th>
                         <th width="80" align="center">
                            部门
                        </th>
                         <th width="60" align="center">
                            凭证号
                        </th>
                         <th width="110" align="center">
                             人事调动编号
                        </th>
                        <th width="60" align="center">
                            调动部门
                        </th>
                         <th width="80" align="center">
                           人事调动责任人
                        </th>
                        <th width="80" align="center">
                           人事调动时间
                        </th>
                        <th width="100" align="center">
                           职称
                        </th>
                        <th width="100" align="center">
                            职级
                        </th>
                        <th width="100" align="center">
                            工资级别
                        </th>
                        <th width="60" align="center">
                            原单位
                        </th>
                        <th width="60" align="center">
                           起时间
                        </th>
                        <th width="80" align="center">
                            调动日期
                        </th>
                         <th width="60" align="center">
                            审核人
                        </th>
                        <th width="200" align="center">
                           调动原因
                        </th>
                         <th width="200" align="center">
                           综合意见
                        </th>
                        <th width="80" align="center">
                           附件
                        </th>
                    </tr>
                </thead>
                <tbody>
                    <%
                    int number = 1; %>
                    <s:iterator value="pageForm.list">
                        <tr id="${transferID}"  >
                            <td>
                                <input type="radio" name="a"  class="JQuerypersonvalue" value="${transferID}"/>
                            </td>
                            <td>
                                <span id="companyName">${company.companyName}</span>
                            </td>
                            <td>
                               <span></span><s:select list="%{#request.organizationlist}" listKey="organizationID" listValue="organizationName" name="deptID" theme="simple" disabled="true"></s:select>
                            </td>
                             <td>
                                <span id="voucherNum">${voucherNum}</span>
                            </td>
                            <td>
                                <span id="transferNum">${transferNum}</span>
                            </td>
                            <td>
                                <span id="transferDept">${transferDept}</span>
                            </td>
                            <td>
                                <span id="principal">${principal}</span>
                            </td>
                            <td>
                                <span id="confirmDate"  class="datas">${fn:substring(confirmDate,0, 10)}</span>
                            </td>
                            <td>
                                <span id="postName">${postName}</span>
                            </td>
                             <td>
                                <span id="postRank" >${postRank}</span>
                            </td>
                             <td>
                                <span id="wagesRank">${wagesRank}</span>
                            </td>
                             <td>
                                <span id="originalCompany" >${originalCompany}</span>
                            </td>
                            <td>
                                <span id="startDate"  class="datas">${fn:substring(startDate,0, 10)}</span>
                            </td>
                            <td>
                                <span id="endDate"  class="datas">${fn:substring(endDate,0, 10)}</span>
                            </td>
                            <td>
                                <span id="auditor">${auditor}</span>
                            </td>
                            <td>
                                <span id="transferReason">${transferReason}</span>
                            </td>
                            <td>
                                <span id="idea">${idea}</span>
                                <span id="accessory" style="display:none">${accessory}</span>
                                <span id="transferID" style="display:none">${transferID}</span>
                                <span id="transferKey" style="display:none">${transferKey}</span>
                                <span id="manager" style="display:none">${manager}</span>
                                <span id="president" style="display:none">${president}</span>
                                <span id="deptCharge" style="display:none">${deptCharge}</span>
                                <span id="headCharge" style="display:none">${headCharge}</span>
                                <span id="verify" style="display:none">${verify}</span>
                                <span id="financeVerify" style="display:none">${financeVerify}</span>
                                 <span id="accounting" style="display:none">${accounting}</span>
                                <span id="allAccounting" style="display:none">${allAccounting}</span>
                                <span id="cashier" style="display:none">${cashier}</span>
                                <span id="headCharge" style="display:none">${headCharge}</span>
                                <span id="allCashier" style="display:none">${allCashier}</span>
                                <span id="deptID" style="display:none">${deptID}</span>
                            </td>
                             <td >
                              <s:if test="accessory==null||accessory==''">无</s:if>
                             <s:else>
                             <span id="look"  onclick="lookImage('${accessory}');"><a href="#">查看</a></span>
                             </s:else>
                            </td>
                        </tr>
                        <%
                        number++; %>
                    </s:iterator>
                </tbody>
            </table>
            <c:import url="../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/stafftransfer/ea_getstaffTransferList.jspa?pageNumber=${pageNumber}&search=${search}">
                </c:param>
            </c:import>
        </div>
<div class="contentbannb jqmWindow jqmWindowcss5" style="top: 10%" id="jqModel">
            <form name="cstaffForm" id="cstaffForm" method="post" enctype="multipart/form-data">
                <input type="submit" name="submit" style="display:none"/>
                <div class="content">
  <div class="contentbannb">
  	<div class="drag">人事调令单确定单
    <div class="close"></div>
    </div>
  </div>
 <table width="960" height="46" border="0" align="center" cellpadding="0" cellspacing="0" style="margin-top:5px;">
 
 
  <tr>
      <td align="right">公司：</td>
      <td width="170"><input name="company.companyName" class="input" id="companyName" readonly="readonly" value="${company.companyName}" size="20" style="margin-left:2px;"/></td>
      <td width="149" align="right">凭证号：</td>
      <td width="108"><input name="staffTransfer.voucherNum" class="input put3" id="voucherNum" size="10" style="margin-left:2px;"/></td>
      <td width="127" align="right">附件：</td>
      <td width="193"><input name="staffTransfer.accessory"  class="fileNum"  type="hidden" id="accessory" size="15"/>
          <input name="photo" type="file" contentEditable="false" class="input" size="15" /></td>
      <td width="148" align="right">张　
        <input name="username30" class="input" id="username30" size="3"/>　
      数</td>
    </tr>
    <tr>
      <td width="55" align="right">部门：</td>
      <td><select id="deptID" name="staffTransfer.deptID" >
      </select>      </td>
      <td align="right">人事调动责任人：</td>
      <td><input name="staffTransfer.principal" class="input" id="principal" size="10" style="margin-left:2px;"/></td>
      <td align="right">人事调动日期：</td>
      <td colspan="2"><input name="staffTransfer.confirmDate"  class="input" id="confirmDate" onfocus="date(this);" size="15" style="margin-left:2px;"/></td>
      </tr>
  </table>
  <table width="960" height="253"  border="0" align="center" cellpadding="0" cellspacing="0" class="table" style="background:#FFFFFF;">
    <tr>
      <td width="124" height="36" align="right"><span class="xx">*</span>人事调动编号：</td>
      <td width="88"><input name="staffTransfer.transferNum" class="input" id="transferNum" size="10" style="margin-left:2px;"/></td>
      <td width="91" align="right"><span class="xx">*</span>调动部门：</td>
      <td width="100"><input name="staffTransfer.transferDept" class="input" id="transferDept" size="10" style="margin-left:2px;"/></td>
      <td width="102" align="right"><span class="xx">*</span>职称：</td>
      <td width="107"><input name="staffTransfer.postName" class="input" id="postName" size="10" style="margin-left:2px;"/></td>
      <td width="51" align="right">职级：</td>
      <td width="101"><input name="staffTransfer.postRank" class="input" id="postRank" size="10" style="margin-left:2px;"/></td>
      <td width="91" align="right">工资级别：</td>
      <td width="95"><input name="staffTransfer.wagesRank" class="input" id="wagesRank" size="10" style="margin-left:2px;"/></td>
    </tr>
    <tr>
      <td height="34" align="right"><span class="xx">*</span>原单位：</td>
      <td><input name="staffTransfer.originalCompany" class="input" id="originalCompany" size="10" style="margin-left:2px;"/></td>
      <td align="right">起日期：</td>
      <td><input name="staffTransfer.startDate" class="input" onfocus="date(this);"  id="startDate" size="10" style="margin-left:2px;"/></td>
      <td align="right">调动日期：</td>
      <td colspan="2"><input name="staffTransfer.endDate" class="input" onfocus="date(this);"  id="endDate" size="15" style="margin-left:2px;"/></td>
      <td align="right">审核人：</td>
      <td colspan="2"><input name="staffTransfer.auditor" class="input" id="auditor" size="15" style="margin-left:2px;"/></td>
    </tr>
    <tr>
      <td height="83" align="right"><span class="xx">*</span>人事调动原因：</td>
      <td colspan="9"><textarea name="staffTransfer.transferReason" cols="80" rows="5" class="input" id="transferReason" style="margin-left:2px;"></textarea></td>
      </tr>
    <tr>
      <td align="right"><span class="xx">*</span>综合意见：</td>
      <td colspan="9"><textarea name="staffTransfer.idea" cols="80" rows="5" class="input" id="idea" style="margin-left:2px;"></textarea></td>
      </tr>
  </table>
  <table width="960" height="86" border="0" align="center" cellpadding="0" cellspacing="0" style="margin-top:5px; margin-bottom:5px;">
    <tr>
      <td width="77" align="right">公司经理：</td>
      <td width="107"><input name="staffTransfer.manager" class="input" id="manager" size="10" style="margin-left:2px;"/></td>
      <td width="93" align="right">部门主管：</td>
      <td width="90"><input name="staffTransfer.deptCharge" class="input" id="deptCharge" size="10" style="margin-left:2px;"/></td>
      <td width="107" align="right">审核：</td>
      <td width="99"><input name="staffTransfer.verify" class="input" id="verify" size="10" style="margin-left:2px;"/></td>
      <td width="76" align="right">会计：</td>
      <td width="87"><input name="staffTransfer.accounting" class="input" id="accounting" size="10" style="margin-left:2px;"/></td>
      <td width="70" align="right">出纳：</td>
      <td width="84"><input name="staffTransfer.cashier" class="input" id="cashier" size="10" style="margin-left:2px;"/></td>
    </tr>
    <tr>
      <td align="right">总部总经理：</td>
      <td><input name="staffTransfer.president" class="input" id="president" size="10" style="margin-left:2px;"/></td>
      <td align="right">总部主管：</td>
      <td><input name="staffTransfer.headCharge" class="input" id="headCharge" size="10" style="margin-left:2px;"/></td>
      <td align="right">财务部审核：</td>
      <td><input name="staffTransfer.financeVerify" class="input" id="financeVerify" size="10" style="margin-left:2px;"/></td>
      <td align="right">总会计：</td>
      <td align="left"><input name="staffTransfer.allAccounting" class="input" id="allAccounting" size="10" style="margin-left:2px;"/></td>
      <td align="right">总出纳：</td>
      <td align="left"><input name="staffTransfer.allCashier" class="input" id="allCashier" size="10" style="margin-left:2px;"/></td>
    </tr>
    <tr>
      <td colspan="10" align="center">
                   <input type="hidden" name="staffTransfer.transferID" id="transferID" />
            <input type="hidden" name="staffTransfer.transferKey" id="transferKey" />
      <input type="button"   class="input-button JQuerySubmit" style="cursor:pointer;width:80px;" value="提交" />
        <input type="button"  class="input-button JQueryreturn" style="cursor:pointer;width:80px;"  value="取消" /></td>
    </tr>
  </table>
</div>
    <s:token></s:token>
            </form>
        </div>
       <!--搜索窗口 -->
        <div class="jqmWindow" style="width: 400px;right: 25%;;top: 10%" id="jqModelSearch">
          <form name="postSearchForm" id="postSearchForm" method="post">
            	<input type="submit" name="submit" style="display:none"/>
            <div class="drag">
                    查询信息
                    <div class="close">
                    </div>
            </div>
                <table width="396" id="cataffSearchTable">
<tr>
                        <td width="123" align="right">
                           凭证号：        </td>
												<td width="261">
												<input name="staffTransfer.voucherNum" />
                        </td>
              </tr>
                    <tr>
                        <td align="right">
                          人事调动责任人：                      </td>
                  <td>
                            <input name="staffTransfer.principal" />
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            部门名称：                        </td>
                        <td>
                        <select id="deptID" name="staffTransfer.deptID" ></select>
                        </td>
                    </tr>
                </table>
            <div align="center">
              <input type="button" class="input-button" id="tosearch" value=" 查询 " /><input name="search" type="hidden" value="search" />
            </div>
            </form>
        </div>
        
          <div class="jqmWindow" style="width: 400px;right: 25%;" id="newamount">
                                            <div class="drag">
                                                文件：
                                            </div>
                                            
                                           <img id="wenjian" width="350" height="400"/>
                                            
         </div>
         <iframe name = "hidden" width="100%" height = "0"></iframe>
    </body>
</html>