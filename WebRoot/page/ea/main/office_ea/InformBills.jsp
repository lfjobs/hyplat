<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8" %>
        <%@ taglib uri="/struts-tags" prefix="s" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
       <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
        <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
        + request.getServerName() + ":" + request.getServerPort()
        + path + "/"; %><html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="cache-control" content="no-cache"/>
        <title>通知单</title>
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
            var informID = "";
            var b=true;
            var basePath='<%=basePath%>';
            var ppageNumber=${pageNumber};
            var psearch='${search}';
		</script>
		<script type="text/javascript" src="<%=basePath%>js/ea/office_ea/InformBills.js"></script>
   


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
                             通知编号
                        </th>
                        <th width="60" align="center">
                          通知责任人
                        </th>
                         <th width="80" align="center">
                            被通知责任人
                        </th>
                        <th width="200" align="center">
                           通知内容
                        </th>
                        <th width="70" align="center">
                           通知时间	
                        </th>
                        <th width="200" align="center">
                          通知原因
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
                        <tr id="${informID}"  >
                            <td>
                                <input type="radio" name="a"  class="JQuerypersonvalue" value="${informID}"/>
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
                                <span id="informNum">${informNum}</span>
                            </td>
                            <td>
                                <span id="principal">${principal}</span>
                            </td>
                            <td>
                                <span id="informed">${informed}</span>
                            </td>
                            <td>
                                <span id="content">${content}</span>
                            </td>
                             <td>
                                <span id="informDate"  class="datas">${fn:substring(informDate,0, 10)}</span>
                            </td>
                            <td>
                                <span id="informReason">${informReason}</span>
                            </td>
                            <td>
                                <span id="idea">${idea}</span>
                                <span id="accessory" style="display:none">${accessory}</span>
                                <span id="informID" style="display:none">${informID}</span>
                                <span id="informKey" style="display:none">${informKey}</span>
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
                <c:param name="actionPath" value="ea/informbills/ea_getInformBillsList.jspa?pageNumber=${pageNumber}&search=${search}">
                </c:param>
            </c:import>
        </div>
 <div class="contentbannb jqmWindow jqmWindowcss1" style="top: 0%" id="jqModel">
            <form name="cstaffForm" id="cstaffForm" method="post" enctype="multipart/form-data">
                <input type="submit" name="submit" style="display:none"/>
                <div class="content">
  <div class="contentbannb">
  	<div class="drag">通知单
    <div class="close"></div>
    </div>
  </div>
  <table width="890 " height="46" border="0" align="center" cellpadding="0" cellspacing="0" style="margin-top:5px;">
    <tr>
      <td >&nbsp;</td>
      <td width="73" align="right">凭证号：</td>
      <td colspan="2" width="132"><input name="informBills.voucherNum" class="input put3" id="voucherNum" size="10" style="margin-left:2px;"/></td>
      <td width="76" align="right">附件：</td>
      <td colspan="2" width="156"><input name="informBills.accessory"  class="fileNum"  type="hidden" id="accessory" size="15" />
       <input name="photo" contentEditable="false" type="file" class="input" size="15" /></td>
      <td width="164" align="right">张　
        <input name="username30" class="input" id="username30" size="3"/>　
      数</td>
    </tr>
    <tr>
      <td width="53" align="right">公司：</td>
      <td width="139"><input name="company.companyName" class="input" value="${company.companyName}" readonly="readonly" id="companyName" size="30" /></td>
      <td width="47" align="right">部门：</td>
      <td><select id="deptID" name="informBills.deptID" ></select></td>
      <td align="right">通知责任人：</td>
      <td><input name="informBills.principal"  class="input"  size="10" style="margin-left:2px;" id="principal" size="15"/></td>
      <td align="right">通知日期：</td>
      <td align="left"><input name="informBills.informDate"  class="input" id="informDate" onfocus="date(this);" size="20" />
      </td>
    </tr>
  </table>
  <table width="890" height="295"  border="0" align="center" cellpadding="0" cellspacing="0" class="table" style="background:#FFFFFF;">
    <tr>
      <td width="102" height="36" align="right"><span class="xx">*</span>通知编号：</td>
      <td colspan="3"><input name="informBills.informNum"  class="input" id="informNum"  size="20"  style="margin-left:2px;"/></td>
      <td width="114" align="right"><span class="xx">*</span>被通知责任人：</td>
      <td width="390" colspan="4"><input name="informBills.informed"  class="input" id="informed"  size="20"  style="margin-left:2px;"/></td>
    </tr>
    <tr>
      <td height="83" align="right"><span class="xx">*</span>通知内容：</td>
      <td colspan="8"><textarea name="informBills.content" cols="80" rows="5" class="input" id="content" style="margin-left:2px;"></textarea></td>
    </tr>
    <tr>
      <td align="right"><span class="xx">*</span>通知原因：</td>
      <td colspan="8"><textarea name="informBills.informReason" cols="80" rows="5" class="input" id="informReason" style="margin-left:2px;"></textarea></td>
    </tr>
     <tr>
      <td align="right"><span class="xx">*</span>综合意见：</td>
      <td colspan="8"><textarea name="informBills.idea" cols="80" rows="5" class="input" id="idea" style="margin-left:2px;"></textarea></td>
    </tr>
  </table>
 <table width="890" height="86" border="0" align="center" cellpadding="0" cellspacing="0" style="margin-top:5px; margin-bottom:5px;">
    <tr>
      <td width="77" align="right">公司经理：</td>
      <td width="107"><input name="informBills.manager" class="input" id="manager" size="10" style="margin-left:2px;"/></td>
      <td width="93" align="right">部门主管：</td>
      <td width="90"><input name="informBills.deptCharge" class="input" id="deptCharge" size="10" style="margin-left:2px;"/></td>
      <td width="107" align="right">审核：</td>
      <td width="99"><input name="informBills.verify" class="input" id="verify" size="10" style="margin-left:2px;"/></td>
      <td width="76" align="right">会计：</td>
      <td width="87"><input name="informBills.accounting" class="input" id="accounting" size="10" style="margin-left:2px;"/></td>
      <td width="70" align="right">出纳：</td>
      <td width="84"><input name="informBills.cashier" class="input" id="cashier" size="10" style="margin-left:2px;"/></td>
    </tr>
    <tr>
      <td align="right">总部总经理：</td>
      <td><input name="informBills.president" class="input" id="president" size="10" style="margin-left:2px;"/></td>
      <td align="right">总部主管：</td>
      <td><input name="informBills.headCharge" class="input" id="headCharge" size="10" style="margin-left:2px;"/></td>
      <td align="right">财务部审核：</td>
      <td><input name="informBills.financeVerify" class="input" id="financeVerify" size="10" style="margin-left:2px;"/></td>
      <td align="right">总会计：</td>
      <td align="left"><input name="informBills.allAccounting" class="input" id="allAccounting" size="10" style="margin-left:2px;"/></td>
      <td align="right">总出纳：</td>
      <td align="left"><input name="informBills.allCashier" class="input" id="allCashier" size="10" style="margin-left:2px;"/></td>
    </tr>
    <tr>
      <td colspan="10" align="center">
                   <input type="hidden" name="informBills.informID" id="informID" />
                   <input type="hidden" name="informBills.informKey" id="informKey" />
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
												<input name="informBills.voucherNum" />
                        </td>
              </tr>
                    <tr>
                        <td align="right">
                          通知责任人：                      </td>
                  <td>
                            <input name="informBills.principal" />
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            部门名称：                        </td>
                        <td>
                        <select id="deptID" name="informBills.deptID" ></select>
                        </td>
                    </tr>
                </table>
            <div align="center">
              <input type="button" class="input-button" id="tosearch" value=" 查询 " /><input name="search" type="hidden" value="search" />
            </div>
            </form>
        </div>
         <iframe name = "hidden" width="100%" scrolling="no"  marginwidth="0" height="0" marginheight="0" frameborder="0" border="0" framespacing="0" noresize="noResize"></iframe>
    </body>
</html>