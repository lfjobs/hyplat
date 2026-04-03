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
        <title>整改通知单管理</title>
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
          <script type="text/javascript">
          var  treeID ="<%=session.getAttribute("organizationID")%>";
          	var token = 0;
            var completeAllterID = "";
            var b=true;
            var basePath='<%=basePath%>';
            var ppageNumber=${pageNumber};
            var psearch='${search}';
		</script>
		<script type="text/javascript" src="<%=basePath%>js/ea/office_ea/CompleteAllter.js"></script>
   


	</head>
	<body >
        <div class="main_main">
            <table class="JQueryflexme">
                <thead>
                    <tr class="tablewith">
                        <th width="40" align="center">
                            请选择
                        </th>
                         <th width="80" align="center">
                            任证号
                        </th>
                         <th width="180" align="center">
                            公司
                        </th>
                         <th width="60" align="center">
                            部门
                        </th>
                         <th width="110" align="center">
                            责任人
                        </th>
                        <th width="60" align="center">
                            整改通知时间
                        </th>
                         <th width="80" align="center">
                            整改通知编号
                        </th>
                        <th width="80" align="center">
                            整改编号
                        </th>
                        <th width="200" align="center">
                            整改通知原因
                        </th>
                        <th width="80" align="center">
                            审核人
                        </th>
                        <th width="80" align="center">
                            部门主管
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
                        <tr id="${completeAllterID}"  >
                            <td>
                                <input type="radio" name="a"  class="JQuerypersonvalue" value="${completeAllterID}"/>
                            </td>
                             <td>
                                <span id="voucherNumber">${voucherNumber}</span>
                            </td>
                            <td>
                                <span id="company">${company}</span>
                            </td>
                            <td>
                               <span></span><s:select list="%{#request.organizationlist}" listKey="organizationID" listValue="organizationName" name="deptID" theme="simple" disabled="true"></s:select>
                             
                           </td>
                            <td>
                                <span id="responsible">${responsible}</span>
                            </td>
                            <td>
                                <span id="completeAllterDate">${fn:substring(completeAllterDate,0, 10)}</span>
                            </td>
                            <td>
                                <span id="completeAllterTongZhiCode">${completeAllterTongZhiCode}</span>
                            </td>
                            <td>
                                <span id="completeAllterCode">${completeAllterCode}</span>
                            </td>
                            <td>
                                <span id="completeAllterTongZhiCause">${completeAllterTongZhiCause}</span>
                            </td>
                              <td>
                                <span id="verify">${verify}</span>
                            </td>
                             <td>
                                <span id="deptCharge">${deptCharge}</span>
                            </td>
                             <td >
                              <s:if test="accessories==null||accessories==''">无</s:if>
                             <s:else>
                             <span id="accessories"  onclick="lookImage('${accessories}');"><a href="#">查看</a></span>
                             </s:else>
                                <span id="completeAllterGoods" style="display:none">${completeAllterGoods}</span>
                                <span id="completeAllterMan" style="display:none">${completeAllterMan}</span>
                                <span id="completeAllterBeCauseOne" style="display:none" >${completeAllterBeCauseOne}</span>
                            	<span id="remarkOne" style="display:none">${remarkOne}</span>
                            	<span id="completeAllterBeCauseTwo" style="display:none">${completeAllterBeCauseTwo}</span>
                            	<span id="remarkTwo" style="display:none">${remarkTwo}</span>
                            	<span id="completeAllterBeCauseThree" style="display:none">${completeAllterBeCauseThree}</span>
                            	<span id="remarkThree" style="display:none">${remarkThree}</span>
                                <span id="accessories" style="display:none">${accessories}</span>
                                <span id="completeAllterID" style="display:none">${completeAllterID}</span>
                                <span id="completeAllterKey" style="display:none">${completeAllterKey}</span>
                                <span id="manager" style="display:none">${manager}</span>
                                <span id="president" style="display:none">${president}</span>
                                <span id="deptCharge" style="display:none">${deptCharge}</span>
                                <span id="headCharge" style="display:none">${headCharge}</span>
                                <span id="verify" style="display:none">${verify}</span>
                                <span id="financeVerify" style="display:none">${financeVerify}</span>
                                 <span id="accounting" style="display:none">${accounting}</span>
                                <span id="allAccounting" style="display:none">${allAccounting}</span>
                                <span id="cashier" style="display:none">${cashier}</span>
                                <span id="allCashier" style="display:none">${allCashier}</span>
                                <span id="deptID" style="display:none">${deptID}</span>
                            </td>
                        </tr>
                        <%
                        number++; %>
                    </s:iterator>
                </tbody>
            </table>
            <c:import url="../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/completeAllter/ea_getCmpleteAllterList.jspa?pageNumber=${pageNumber}&search=${search}">
                </c:param>
            </c:import>
        </div>
 <div class="contentbannb jqmWindow jqmWindowcss1" style="top: 10%" id="jqModel">
            <form name="cstaffForm" id="cstaffForm" method="post" enctype="multipart/form-data">
                <input type="submit" name="submit" style="display:none"/>
                <div class="content">
  <div class="contentbannb">
  	<div class="drag">整改通知单
    <div class="close"></div>
    </div>
  </div>
  <table width="890 " height="30" border="0" align="center" cellpadding="0" cellspacing="0" style="margin-top:5px;">
    <tr>
      <td width="80" align="right">凭证号：</td>
      <td width="120"><input name="completeAllter.voucherNumber" class="input put3" id="voucherNumber" size="20" style="margin-left:2px;"/></td>
      <td width="80" align="right">附件：</td>
      <td width="440" colspan="3"><input name="completeAllter.accessories"  class="fileNum"  type="hidden" id="accessories" size="15" />
       <input name="photo" contentEditable="false" type="file" class="input" size="15" /></td>
      <td width="110" align="right">张数</td>
      	<td  width="110">
        <input name="username30" class="input" id="username30" size="20"/>　
      </td>
    </tr>
    <tr>
      <td  align="right">公司：</td>
      <td ><input name="company.companyName" class="input" value="${company.companyName}" readonly="readonly" id="companyName" size="28" />
      <input name="completeAllter.company" class="input" type="hidden" value="${company.companyName}" readonly="readonly" id="companyName" size="20" /></td>
      <td  align="right">部门：</td>
      <td><select id="deptID" name="completeAllter.deptID" ></select></td>
      <td  align="right" width="110">通知责任人：</td>
      <td><input name="completeAllter.responsible"  class="input"  style="margin-left:2px;" id="responsible" size="20"/></td>
      <td  align="right">通知日期：</td>
      <td align="left"><input name="completeAllter.completeAllterDate"  class="input" id="completeAllterDate" onfocus="date(this);" size="20" />
      </td>
    </tr>
    
    <tr>
      <td  align="right"><span class="xx">*</span>通知编号：</td>
      <td ><input name="completeAllter.completeAllterTongZhiCode"  class="input" id="completeAllterTongZhiCode"  size="20"  style="margin-left:2px;"/></td>
      <td align="right"><span class="xx">*</span>整改编号：</td>
      <td  ><input name="completeAllter.completeAllterCode"  class="input" id="completeAllterCode"  size="20"  style="margin-left:2px;"/></td>
      <td   align="right"><span class="xx">*</span>物品名称：</td>
      <td ><input name="completeAllter.completeAllterGoods"  class="input" id="completeAllterGoods"  size="20"  style="margin-left:2px;"/></td>
      <td  align="right"><span class="xx">*</span>批准人：</td>
      <td  ><input name="completeAllter.completeAllterMan"  class="input" id="completeAllterMan"  size="20"  style="margin-left:2px;"/></td>
    </tr>
  </table>
  <table width="890" height="200"  border="0" align="center" cellpadding="0" cellspacing="0" class="table" style="background:#FFFFFF;">
     
    <tr>
      <td align="left" height="10" colspan="3"><span class="xx" >*</span>整改通知原因：</td>
      <td height="10" colspan="17" ><textarea name="completeAllter.completeAllterTongZhiCause" cols="95" rows="2" class="input" id="completeAllterTongZhiCause" style="margin-left:2px;"></textarea></td>
    </tr>
    
     <tr>
      <td align="left" colspan="3"><span class="xx">*</span>整改存在问题一：</td>
      <td colspan="8"><textarea name="completeAllter.completeAllterBeCauseOne" cols="40" rows="2" class="input" id="completeAllterBeCauseOne" style="margin-left:2px;"></textarea></td>
      <td align="left" colspan="2"><span class="xx">*</span>备注一：</td>
      <td colspan="8"><textarea name="completeAllter.remarkOne" cols="40" rows="2" class="input" id="remarkOne" style="margin-left:2px;"></textarea></td>
    </tr>
    <tr>
      <td align="left" colspan="3"><span class="xx">*</span>整改存在问题二：</td>
      <td colspan="8"><textarea name="completeAllter.completeAllterBeCauseTwo" cols="40" rows="2" class="input" id="completeAllterBeCauseTwo" style="margin-left:2px;"></textarea></td>
      <td align="left" colspan="2"><span class="xx">*</span>备注二：</td>
      <td colspan="8"><textarea name="completeAllter.remarkTwo" cols="40" rows="2" class="input" id="remarkTwo" style="margin-left:2px;"></textarea></td>
    </tr>
    <tr>
      <td align="left" colspan="3"><span class="xx">*</span>整改存在问题三：</td>
      <td colspan="8"><textarea name="completeAllter.completeAllterBeCauseThree" cols="40" rows="2" class="input" id="completeAllterBeCauseThree" style="margin-left:2px;"></textarea></td>
      <td align="left" colspan="2"><span class="xx">*</span>备注三：</td>
      <td colspan="8"><textarea name="completeAllter.remarkThree" cols="40" rows="2" class="input" id="remarkThree" style="margin-left:2px;"></textarea></td>
    </tr>
  </table>
 <table width="890" height="86" border="0" align="center" cellpadding="0" cellspacing="0" style="margin-top:5px; margin-bottom:5px;">
    <tr>
      <td width="77" align="right">公司经理：</td>
      <td width="107"><input name="completeAllter.manager" class="input" id="manager" size="10" style="margin-left:2px;"/></td>
      <td width="93" align="right">部门主管：</td>
      <td width="90"><input name="completeAllter.deptCharge" class="input" id="deptCharge" size="10" style="margin-left:2px;"/></td>
      <td width="107" align="right">审核：</td>
      <td width="99"><input name="completeAllter.verify" class="input" id="verify" size="10" style="margin-left:2px;"/></td>
      <td width="76" align="right">会计：</td>
      <td width="87"><input name="completeAllter.accounting" class="input" id="accounting" size="10" style="margin-left:2px;"/></td>
      <td width="70" align="right">出纳：</td>
      <td width="84"><input name="completeAllter.cashier" class="input" id="cashier" size="10" style="margin-left:2px;"/></td>
    </tr>
    <tr>
      <td align="right">总部总经理：</td>
      <td><input name="completeAllter.president" class="input" id="president" size="10" style="margin-left:2px;"/></td>
      <td align="right">总部主管：</td>
      <td><input name="completeAllter.headCharge" class="input" id="headCharge" size="10" style="margin-left:2px;"/></td>
      <td align="right">财务部审核：</td>
      <td><input name="completeAllter.financeVerify" class="input" id="financeVerify" size="10" style="margin-left:2px;"/></td>
      <td align="right">总会计：</td>
      <td align="left"><input name="completeAllter.allAccounting" class="input" id="allAccounting" size="10" style="margin-left:2px;"/></td>
      <td align="right">总出纳：</td>
      <td align="left"><input name="completeAllter.allCashier" class="input" id="allCashier" size="10" style="margin-left:2px;"/></td>
    </tr>
    <tr>
      <td colspan="10" align="center">
                   <input type="hidden" name="completeAllter.completeAllterID" id="completeAllterID" />
                   <input type="hidden" name="completeAllter.completeAllterKey" id="completeAllterKey" />
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
												<input name="completeAllter.voucherNumber" />
                        </td>
              </tr>
                    <tr>
                        <td align="right">
                            部门名称：                        </td>
                        <td>
                        <select id="deptID" name="completeAllter.deptID" ></select>
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
         <iframe name = "hidden" width="100%" scrolling="no"  marginwidth="0" height="0" marginheight="0" frameborder="0" border="0" framespacing="0" noresize="noResize"></iframe>
    </body>
</html>