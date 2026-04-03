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
<title>用电管理</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script src="<%=basePath%>js/ea/validate.js"  type="text/javascript"></script>
<script src="<%=basePath%>js/ea/office_ea/electricity.js"></script>
<script type="text/javascript">
		 var select = '01';
         var addressID = '';
		 var  basePath='<%=basePath%>';           
         var  pNumber =${pageNumber};  
         var  search='${search}';
         var  token = 0 ; 
</script>

<script src="<%=basePath%>js/ea/human/cstaff.js"></script>
</head>
<body>
<form style="display: none;" name="addressForm" id="addressForm" method="post"><s:token></s:token><input type="submit" name="submit" style="display:none"/>
</form>

<div id="main_main" class="main_main">
  <table   class="address">
  <thead>
	 	    <tr>
	 	    <th width="40" align="center">选择</th>
            <th width="200" align="center" >用户名</th>
            <th width="200" align="center" >购电量</th>
            <th width="200" align="center" >实际用量</th>
            <th width="200" align="center" >核对人</th>
      </tr>
    </thead>
		<tbody id="tbwid">
           <tr id="sa" style="display: none" class="td_bg01 saveAjax model2">
            <td >
             <input type="radio" name="a" class="JQuerypersonvalue" />
             </td>
            <td class="td_bg01"><input   name="electricity.username" id="username"   /></td>
            <td class="td_bg01"><input   name="electricity.amount" id="amount"  /></td>
            <td class="td_bg01"><input   name="electricity.usedamount" id="usedamount"  /></td>
			<td class="td_bg01"><s:select list="staffList" listKey="staffName" id="xxx" listValue="staffName" name="electricity.examiner" theme="simple"></s:select></td>
          </tr>
          <s:iterator value="pageForm.list">
          <tr class="td_bg01 saveAjax" id="${electricityID}">
          <td class="td_bg01">
             <input type="radio" name="a" class="JQuerypersonvalue"/>
            </td>
            <td class="td_bg01">
                <span id="username" class="datas">${username}</span>
				<input class="model1" value="${username}" name="electricity.username"  /></td>
            <td class="td_bg01">
               <span id="amount" class="datas">${amount}</span>
				<input class="model1" value="${amount}" name="electricity.amount"   /></td>
            <td class="td_bg01">
               <span id="usedamount" class="datas">${usedamount}</span><input class="model1"  name="electricity.usedamount" value="${usedamount}"/>
			  
				</td>
            <td class="td_bg01">
             <span id="examiner">${examiner}</span>
           				 <s:select list="staffList" listKey="staffName" id="examiner" listValue="staffName" name="examiner" theme="simple"></s:select>
					        <span id="electricityKey" style="display:none">${electricityKey}</span>    <input type="hidden" name="electricity.electricityKey" value="${electricityKey}"/>
					        <span id="electricityID" style="display:none">${electricityID}</span>   <input type="hidden" name="electricity.electricityID" value="${electricityID}"/>
			</td> 
          </tr>
          </s:iterator>
    </tbody>
  </table>
 <c:import url="../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/electricity/ea_getListForPage.jspa?pageNumber=${pageNumber}&search=${search}"></c:param>
</c:import>
</div>

 <!--搜索窗口 -->
        <div class="jqmWindow" style="width: 400px;right: 25%;" id="jqModelSearch">
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
                            查询条件
                        </td>
                    </tr>
                    <tr>
                        <td>
                          用户名：
                        </td>
                        <td>
                           <input   name="electricity.username" />
                        </td>
                    </tr>
                </table>
                <div align="center">
                    <input type="button" class="input-button" id="tosearch" value=" 查询 " /><input name="search" type="hidden" value="search" />
                </div>
            </form>
        </div>
		
		
		<!--查看 -->
		<div class="jqmWindow jqmWindowcss" style="top: 10%;width: 400px"  id="jqModel">
			 <form name="cstaffForm" id="cstaffForm" method="post" action="<%=basePath%>ea/electricity/t_ea_save.jspa?pageNumber=${pageNumber}">
			       <div class="drag">用电管理
				    <div class="close"></div>
				    </div>
			<input type="submit" name="submit" style="display:none"/>
			 <div class="content">
			  <div class="contentbannb">
			  </div>
			 <table width="400" border="0" id="stafftable" align="center" cellpadding="0" cellspacing="0" style="margin-top:5px;margin-bottom:5px;">
			 <tr>
			   <td>
			     <table width="400" height="112" border="0" align="center" cellpadding="0" cellspacing="0" id="stafftable2" style="margin-top:5px;margin-bottom:5px;">
			      <tr>
			        <td width="103"  align="right">用户名：</td>
			        <td width="300" ><input name="electricity.username" type="text" id="username" size="20" class="usernames isremove put3"/></td>
		           </tr>
			      <tr>
			        <td width="103"  align="right">购电量：</td>
			        <td ><input name="electricity.amount" type="text" id="amount" size="20" class="amount isremove put3"/></td>
			       </tr>
                   <tr>
			        <td align="right">实际用量：</td>
			        <td ><input id="usedamount"   type="text" name="electricity.usedamount" size="20"/></td>
			       </tr>
                   <tr>
			        <td  align="right">核对人：</td>
			        <td ><input  id="examiner" type="text"  class="examiner isremove put3"  name="electricity.examiner" size="20"/></td>
								<input type="hidden" name="electricity.electricityKey" id="electricityKey" />
					            <input type="hidden" name="electricity.electricityID" id="electricityID" />
			       </tr>
			    </table>
		       </td>
			  </tr>
			</table>
			</div>
			    <s:token></s:token>
			    <div align="center">
                    <input type="button" class="input-button JQuerySubmit" style="cursor:pointer;width:80px;"  value=" 保存 " />
                    <input type="button" class="input-button JQueryreturn" style="cursor:pointer;width:80px;" value=" 取消 " />
                </div>
			</form>
	</div>
	
	 <iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe> 
</body>
</html>
