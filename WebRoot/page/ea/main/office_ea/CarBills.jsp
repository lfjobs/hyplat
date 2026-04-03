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
        <title>派车单</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
        <script src="<%=basePath%>js/jquery.js" type="text/javascript">
        </script>
                <link rel="stylesheet" type="text/css" href="<%=basePath%>css/admin_main.css"/>
        <script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
        </script>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
        <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
        <script language="javascript" type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js">
        </script>
          <link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
        
          <link rel="stylesheet" type="text/css" href="<%=basePath%>css/overlayer.css"/>
        <script src="<%=basePath%>js/ea/office_ea/CarBills.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/common/organizationTree.js"></script>
        <script type="text/javascript">
        var  treeID ="<%=session.getAttribute("organizationID")%>";
		 var  basePath='<%=basePath%>';           
         var  pNumber =${pageNumber};  
         var  search='${search}';
         var  token=0;
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
                         <th width="180" align="center">
                            公司
                        </th>
                         <th width="80" align="center">
                            部门
                        </th>
                         <th width="60" align="center">
                            凭证号
                        </th>
                         <th width="110" align="center">
                            派车编号
                        </th>
                        <th width="60" align="center">
                           随车人员
                        </th>
                         <th width="80" align="center">
                           派车责任人
                        </th>
                        <th width="80" align="center">
                          派车日期
                        </th>
                        <th width="100" align="center">
                          日期
                        </th>
                        <th width="100" align="center">
                           返回时间
                        </th>
                        <th width="100" align="center">
                           用车区间
                        </th>
                           <th width="100" align="center">
                           车号
                        </th>
                           <th width="100" align="center">
                           司机
                        </th>
                           <th width="100" align="center">
                           出车公里表数
                        </th>
                        
                        <th width="60" align="center">
                           返回公里表数
                        </th>
                          <th width="60" align="center">
                           备注
                        </th>
                          <th width="150" align="center">
                           出车原因
                        </th>
                        
                        <th width="100" align="center">
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
                        <tr id="${carBillsID}"  >
                            <td>
                                <input type="radio" name="a"  class="JQuerypersonvalue" value="${carBillsID}"/>
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
                                <span id="carBillsNum">${carBillsNum}</span>
                            </td>
                            <td>
                                <span id="applianceCrew">${applianceCrew}</span>
                            </td>
                            <td>
                                <span id="principal">${principal}</span>
                            </td>
                             <td>
                                <span id="carDate"  class="datas">${fn:substring(carDate,0, 10)}</span>
                            </td>
                            <td>
                                <span id="startDate"  class="datas">${fn:substring(startDate,0, 10)}</span>
                            </td>
                            <td>
                                <span id="endDate"  class="datas">${fn:substring(endDate,0, 10)}</span>
                            </td>
                            <td>
                                <span id="carInterval">${carInterval}</span>
                            </td>
                            <td>
                                <span id="carNum">${carNum}</span>
                            </td>
                            <td>
                                <span id="carDriver">${carDriver}</span>
                            </td>
                            <td>
                                <span id="startKilometer">${startKilometer}</span>
                            </td>
                             <td>
                                <span id="endKilometer">${endKilometer}</span>
                            </td>
                             <td>
                                <span id="remark">${remark}</span>
                            </td>
                             <td>
                                <span id="carReason">${carReason}</span>
                            </td>
                            <td>
                                <span id="idea">${idea}</span>
                                <span id="accessory" style="display:none">${accessory}</span>
                                <span id="carBillsID" style="display:none">${carBillsID}</span>
                                <span id="carBillsKey" style="display:none">${carBillsKey}</span>
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
                <c:param name="actionPath" value="ea/carbills/ea_getcarBillsList.jspa?pageNumber=${pageNumber}&search=${search}">
                </c:param>
            </c:import>
        </div> 
 <div class="contentbannb jqmWindow jqmWindowcss1" style="top: 0%" id="jqModel">
            <form name="cstaffForm" id="cstaffForm" method="post" enctype="multipart/form-data">
                <input type="submit" name="submit" style="display:none"/>
                <div class="content">
  <div class="contentbannb">
  	<div class="drag">派车单
    <div class="close"></div>
    </div>
  </div>
   <table width="890 " height="46" border="0" align="center" cellpadding="0" cellspacing="0" style="margin-top:5px;">
    <tr>
      <td align="right">公司：</td>
      <td><input name="company.companyName" value="${company.companyName}" readonly="readonly" class="input" id="companyName" size="20"/></td>
      <td width="138" align="right">凭证号：</td>
      <td width="115"><input name="carBills.voucherNum" class="input put3" id="voucherNum" size="10" style="margin-left:2px;"/></td>
      <td width="85" align="right">附件：</td>
      <td width="197"><input name="carBills.accessory"  class="fileNum"  type="hidden" id="accessory" size="15"/>
       <input name="photo" type="file" class="input" size="15" contentEditable="false" /></td>
      <td width="152" align="right">张　
        <input name="username30" class="input" id="username30" size="3"/>　
      数</td>
    </tr>
    <tr>
      <td width="56" align="right">部门：</td>
      <td width="147"><select id="deptID" name="carBills.deptID" >
      </select></td>
      <td align="right">派车责任人：</td>
      <td colspan="2"><input name="carBills.principal" class="input" id="principal" size="10"/></td>
      <td align="right">派车日期：</td>
      <td align="left"><input name="carBills.carDate"  class="input" id="carDate" onfocus="date(this);" size="10" /></td>
    </tr>
  </table>
  <table width="890" height="300"  border="0" align="center" cellpadding="0" cellspacing="0" class="table" style="background:#FFFFFF;">
    <tr>
      <td width="140" height="36" align="right"><span class="xx">*</span>派车编号：</td>
      <td width="167"><input name="carBills.carBillsNum"  class="input" id="carBillsNum"  size="20" style="margin-left:2px;" /></td>
      <td width="140" align="right"><span class="xx">*</span>随车人员：</td>
      <td width="142"><input name="carBills.applianceCrew"  class="input" id="applianceCrew" size="20" style="margin-left:2px;" /></td>
      <td width="107" align="right">日期：</td>
      <td width="194"><input name="carBills.startDate"  class="input" id="startDate" onfocus="date(this);" size="20" style="margin-left:2px;" /></td>
    </tr>
    <tr>
      <td height="25" align="right"><span class="xx">*</span>预计返回时间：</td>
      <td><input name="carBills.endDate"  class="input" id="endDate" onfocus="date(this);" size="20" style="margin-left:2px;" /></td>
      <td align="right"><span class="xx">*</span>用车区间：</td>
      <td colspan="3"><input name="carBills.carInterval"  class="input" id="carInterval" size="20" style="margin-left:2px;" /></td>
    </tr>
    <tr>
      <td height="25" align="center">车号</td>
      <td align="center">司机</td>
      <td align="center">出车公里表数</td>
      <td colspan="2" align="center">返回公里表数</td>
      <td align="center">备注</td>
    </tr>
    <tr>
      <td height="30" align="center"><input name="carBills.carNum"  class="input" id="carNum" size="15" /></td>
      <td align="center"><input name="carBills.carDriver"  class="input" id="carDriver" size="15" /></td>
      <td align="center"><input name="carBills.startKilometer"  class="input" id="startKilometer" size="15" /></td>
      <td colspan="2" align="center"><input name="carBills.endKilometer"  class="input" id="endKilometer" size="15" /></td>
      <td align="center"><input name="carBills.remark"  class="input" id="remark" size="15" /></td>
    </tr>
     <tr>
      <td height="76" align="right"><span class="xx">*</span>用车原因：</td>
      <td colspan="5"><textarea name="carBills.carReason" cols="80" rows="4" class="input" id="carReason" style="margin-left:2px;"></textarea></td>
    </tr>
     <tr>
      <td align="right">综合意见：</td>
      <td colspan="5"><textarea name="carBills.idea" cols="80" rows="4" class="input" id="idea" style="margin-left:2px;"></textarea></td>
    </tr>
  </table>
 <table width="890" height="86" border="0" align="center" cellpadding="0" cellspacing="0" style="margin-top:5px; margin-bottom:5px;">
    <tr>
      <td width="77" align="right">公司经理：</td>
      <td width="107"><input name="carBills.manager" class="input" id="manager" size="10" style="margin-left:2px;"/></td>
      <td width="93" align="right">部门主管：</td>
      <td width="90"><input name="carBills.deptCharge" class="input" id="deptCharge" size="10" style="margin-left:2px;"/></td>
      <td width="107" align="right">审核：</td>
      <td width="99"><input name="carBills.verify" class="input" id="verify" size="10" style="margin-left:2px;"/></td>
      <td width="76" align="right">会计：</td>
      <td width="87"><input name="carBills.accounting" class="input" id="accounting" size="10" style="margin-left:2px;"/></td>
      <td width="70" align="right">出纳：</td>
      <td width="84"><input name="carBills.cashier" class="input" id="cashier" size="10" style="margin-left:2px;"/></td>
    </tr>
    <tr>
      <td align="right">总部总经理：</td>
      <td><input name="carBills.president" class="input" id="president" size="10" style="margin-left:2px;"/></td>
      <td align="right">总部主管：</td>
      <td><input name="carBills.headCharge" class="input" id="headCharge" size="10" style="margin-left:2px;"/></td>
      <td align="right">财务部审核：</td>
      <td><input name="carBills.financeVerify" class="input" id="financeVerify" size="10" style="margin-left:2px;"/></td>
      <td align="right">总会计：</td>
      <td align="left"><input name="carBills.allAccounting" class="input" id="allAccounting" size="10" style="margin-left:2px;"/></td>
      <td align="right">总出纳：</td>
      <td align="left"><input name="carBills.allCashier" class="input" id="allCashier" size="10" style="margin-left:2px;"/></td>
    </tr>
    <tr>
      <td colspan="10" align="center">
            <input type="hidden" name="carBills.carBillsID" id="carBillsID" />
            <input type="hidden" name="carBills.carBillsKey" id="carBillsKey" />
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
												<input name="carBills.voucherNum" />
                        </td>
              </tr>
                    <tr>
                        <td align="right">
                           派车责任人：                      </td>
                  <td>
                            <input name="carBills.principal" />
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            部门名称：                        </td>
                        <td>
                        <select id="deptID" name="carBills.deptID" ></select>
                        </td>
                    </tr>
                </table>
            <div align="center">
              <input type="button" class="input-button" id="tosearch" value=" 查询 " /><input name="search" type="hidden" value="search" />
            </div>
            </form>
        </div>
         <iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
    </body>
</html>