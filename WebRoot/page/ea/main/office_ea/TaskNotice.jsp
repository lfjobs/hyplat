<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
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
        Company c = (Company)session.getAttribute("currentcompany");
        %>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="cache-control" content="no-cache"/>
        <title>任务通知单管理</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
        <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
        <script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
        <link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
        <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
        <script language="javascript" type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>css/admin_main.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>css/overlayer.css"/>
       <script type="text/javascript" src="<%=basePath%>js/common/organizationTree.js"></script>
       <script type="text/javascript">
       		var  treeID ="<%=session.getAttribute("organizationID")%>";
       		var  treeName ="<%=session.getAttribute("organizationName")%>";
       		var  treePID ="<%=c.getCompanyID()%>";
       		var  treePName ="<%=c.getCompanyName()%>";
       		var token = 0;
            var taskNoticeID = "";
            var b=true;
            var basePath='<%=basePath%>';
            var ppageNumber=${pageNumber};
            var psearch='${search}';
		</script>
		<script type="text/javascript" src="<%=basePath%>js/ea/office_ea/TaskNotice.js"></script>

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
                            责任人
                        </th>
                        <th width="60" align="center">
                            任务通知编号
                        </th>
                        <th width="80" align="center">
                           通知类别
                        </th>
                        <th width="80" align="center">
                           通知日期
                        </th>
                        <th width="200" align="center">
                           通知内容
                        </th>
                        <th width="200" align="center">
                           综合意见
                        </th>
                        <th width="100" align="center">
                           审核人
                        </th>
                        <th width="60" align="center">
                           部门主管
                       </th>
                       <th width="60" align="center">
                           附件
                        </th>
                    </tr>
                </thead>
                <tbody>
                    <%
                    int number = 1; %>
                    <s:iterator value="pageForm.list">
                        <tr id="${taskNoticeID}">
                            <td>
                                <input type="radio" name="a"  class="JQuerypersonvalue" value="${taskNoticeID}"/>
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
                                <span id="enPerson">${enPerson}</span>
                            </td>
                            <td>
                                <span id="enTNumber">${enTNumber}</span>
                            </td>
                            <td>
                                <span style="display:none" id="enType">${enType}</span><s:if test="enType=='00'">一般通知</s:if><s:if test="enType=='01'">紧急通知</s:if>
                            </td>
                            <td>
                                <span id="enDate">${fn:substring(enDate,0,10)}</span>
                            </td>
                            <td>
                                <span id="enContent">${enContent}</span>
                            </td>
                             <td>
                                <span id="enIdea">${enIdea}</span>
                            </td>
                            <td>
                                <span id="enAuditor">${enAuditor}</span>
                            </td>
                            <td>
                                <span id="enLeader">${enLeader}</span>
                            </td>  
                            <td>
                            		 <span id="enFilePath" style="display:none">${enFilePath}</span>
		                              <s:if test="enFilePath==null||enFilePath==''">无</s:if>
		                              <s:else>
		                             		<span id="look"  onclick="lookImage('${enFilePath}');"><a href="#">查看</a></span>
		                              </s:else>
	                            <span id="taskNoticeID" style="display:none">${taskNoticeID}</span>
                                <span id="taskNoticeKey" style="display:none">${taskNoticeKey}</span> 
                                <span id="deptID" style="display:none">${deptID}</span> 
                                <span id="manager" style="display:none">${manager}</span>
                                <span id="president" style="display:none">${president}</span>
                                <span id="headCharge" style="display:none">${headCharge}</span>
                                <span id="financeVerify" style="display:none">${financeVerify}</span>
                                 <span id="accounting" style="display:none">${accounting}</span>
                                <span id="allAccounting" style="display:none">${allAccounting}</span>
                                <span id="cashier" style="display:none">${cashier}</span>
                                <span id="allCashier" style="display:none">${allCashier}</span>
                            </td>
                        </tr>
                        <%
                        number++; %>
                    </s:iterator>
                </tbody>
            </table>
            <c:import url="../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/tasknotice/ea_getTaskNoticeList.jspa?pageNumber=${pageNumber}&search=${search}">
                </c:param>
            </c:import>
        </div>
       <div class="contentbannb jqmWindow jqmWindowcss1" style="top: 5%" id="jqModel">
            <form name="cstaffForm" id="cstaffForm" method="post" enctype="multipart/form-data">
                <input type="submit" name="submit" style="display:none"/>
                <div class="content">
  <div class="contentbannb">
  	<div class="drag">任务通知单
    <div class="close"></div>
    </div>
  </div>
   <table width="890 " height="46" border="0" align="center" cellpadding="0" cellspacing="0" style="margin-top:5px;">
	    <tr>
	      <td align="right">公司：</td>
	      <td width="273"><input name="company.companyName" class="input" readonly="readonly" value="${company.companyName}" id="companyName" size="30"/></td>
	      <td align="right">附件：</td>
	      <td colspan="2"><input name="taskNotice.accessory"  class="fileNum"  type="hidden" id="accessory" size="15"/>
	        <input name="taskNotice.enFile" type="file" contentEditable="false" class="input" size="15" /></td>
	      <td width="177" align="right">张<input name="username30" class="input" id="username30" size="3"/> 数</td>
	    </tr>
	    <tr>
	      <td width="56" align="right">部门：</td>
	      <td><select id="deptID" name="taskNotice.deptID" >
	      </select>      </td>
	      <td width="113" align="right">责任人：</td>
	      <td width="126"><input name="taskNotice.enPerson" class="input" id="enPerson" size="15"/></td>
	      <td width="145" align="right">凭证号：</td>
	      <td align="left"><input name="taskNotice.voucherNum" class="input put3" id="voucherNum" size="20" style="margin-left:2px;"/></td>
	    </tr>
  </table>
  <table width="890" height="271"  border="0" align="center" cellpadding="0" cellspacing="0" class="table" style="background:#FFFFFF;">
	    <tr>
	      <td width="102" height="36" align="right"><span class="xx">*</span>任务编号：</td>
	      <td><input name="taskNotice.enTNumber" class="input" id="enTNumber" size="30" style="margin-left:2px;"/></td>
	      <td align="right"><span class="xx">*</span>通知类别：</td>
	      <td colspan="4">
		      <select name="taskNotice.enType" id="enType">
		      		<option value="00">一般</option>
		      		<option value="01">紧急</option>
		      </select>
	      </td>
	      <td>
	      		<span class="xx">*</span>通知日期：
	      </td>
	      <td>
	      		<input type="text" name="taskNotice.enDate" id="enDate" onfocus="date(this)"/>
	      </td>
	    </tr>
	     <tr>
	      <td align="right"><span class="xx">*</span>通知内容：</td>
	      <td colspan="8"><textarea name="taskNotice.enContent" cols="80" rows="5" class="input" id="enContent" style="margin-left:2px;"></textarea></td>
	    </tr>
	    <tr>
	      <td align="right"><span class="xx">*</span>综合意见：</td>
	      <td colspan="8"><textarea name="taskNotice.enIdea" cols="80" rows="5" class="input" id="enIdea" style="margin-left:2px;"></textarea></td>
	    </tr>
  </table>
  <table width="890" height="86" border="0" align="center" cellpadding="0" cellspacing="0" style="margin-top:5px; margin-bottom:5px;">
    <tr>
      <td width="77" align="right">公司经理：</td>
      <td width="107"><input name="taskNotice.manager" class="input" id="manager" size="10" style="margin-left:2px;"/></td>
      <td width="93" align="right">部门主管：</td>
      <td width="90"><input name="taskNotice.enLeader" class="input" id="enLeader" size="10" style="margin-left:2px;"/></td>
      <td width="107" align="right">审核人：</td>
      <td width="99"><input name="taskNotice.enAuditor" class="input" id="enAuditor" size="10" style="margin-left:2px;"/></td>
      <td width="76" align="right">会计：</td>
      <td width="87"><input name="taskNotice.accounting" class="input" id="accounting" size="10" style="margin-left:2px;"/></td>
      <td width="70" align="right">出纳：</td>
      <td width="84"><input name="taskNotice.cashier" class="input" id="cashier" size="10" style="margin-left:2px;"/></td>
    </tr>
    <tr>
      <td align="right">总部总经理：</td>
      <td><input name="taskNotice.president" class="input" id="president" size="10" style="margin-left:2px;"/></td>
      <td align="right">总部主管：</td>
      <td><input name="taskNotice.headCharge" class="input" id="headCharge" size="10" style="margin-left:2px;"/></td>
      <td align="right">财务部审核：</td>
      <td><input name="taskNotice.financeVerify" class="input" id="financeVerify" size="10" style="margin-left:2px;"/></td>
      <td align="right">总会计：</td>
      <td align="left"><input name="taskNotice.allAccounting" class="input" id="allAccounting" size="10" style="margin-left:2px;"/></td>
      <td align="right">总出纳：</td>
      <td align="left"><input name="taskNotice.allCashier" class="input" id="allCashier" size="10" style="margin-left:2px;"/></td>
    </tr>
    <tr>
      <td colspan="10" align="center">
      			   <input type="hidden" name="taskNotice.enFilePath" id="enFilePath" />
                   <input type="hidden" name="taskNotice.taskNoticeID" id="taskNoticeID" />
                   <input type="hidden" name="taskNotice.taskNoticeKey" id="taskNoticeKey" />
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
	                        <td width="123" align="right"> 凭证号：</td>
							<td width="261"><input name="taskNotice.voucherNum" /></td>
             			 </tr>
	                    <tr>
	                        <td align="right">
	                            部门名称：                        </td>
	                        <td>
	                        <select id="deptID" name="taskNotice.deptID" ></select>
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