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
<title>用水管理</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>

<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script src="<%=basePath%>js/ea/validate.js"  type="text/javascript"></script>
<SCRIPT type="text/javascript">
  	 var select = '01';
     var addressID = '';
     var basePath='<%=basePath%>';
     var ppageNumber=${pageNumber};
     var psearch='${search}';
     var token = 0 ;
</SCRIPT>
<script type="text/javascript" src="<%=basePath%>js/ea/office_ea/water.js"></script>
<script src="<%=basePath%>js/ea/human/cstaff.js"></script>
</head>
<body >
<form style="display: none;" name="addressForm" id="addressForm" method="post"><s:token></s:token><input type="submit" name="submit" style="display:none"/>
</form>

<div id="main_main" class="main_main">
  <table   class="address">
  <thead>
	 	    <tr >
	 	    <th width="40" align="center">选择</th>
            <th width="200" align="center" >单位</th>
            <th width="100" align="center" >日期</th>
            <th width="200" align="center" >计划用水量</th>
            <th width="200" align="center" >实际用水量</th>
      </tr>
    </thead>
		<tbody id="tbwid">
           <tr id="sa" style="display: none" class="td_bg01 saveAjax model2">
            <td >
             <input type="radio" name="a" class="JQuerypersonvalue" />
             <br /></td>
            <td class="td_bg01"><input   name="coWater.unit" id="unit"   /><br /></td>
            <td class="td_bg01"><input   name="coWater.logDate" id="logDate"  /><br /></td>
            <td class="td_bg01"><input   name="coWater.amount" id="amount"  /><br /></td>
			<td class="td_bg01"><input   name="coWater.usedAmount" id="usedAmount"  /><br /></td>
          </tr>
          <s:iterator value="pageForm.list">
          <tr class="td_bg01 saveAjax" id="${waterID}">
          <td class="td_bg01">
             <input type="radio" name="a" class="JQuerypersonvalue"/>
            </td>
            <td class="td_bg01">
                <span id="unit" class="datas">${unit}</span>
				<input class="model1" value="${unit}" name="coWater.unit"  /></td>
            <td class="td_bg01">
               <span id="logDate" class="datas">${logDate}</span>
				<input class="model1" value="${logDate}" name="coWater.logDate"   /></td>
            <td class="td_bg01">
               <span id="amount" class="datas">${amount}</span><input class="model1"  name="coWater.amount" value="${amount}"/>
			  
				</td>
            <td class="td_bg01">
             <span id="usedAmount">${usedAmount}</span>
           				 <input class="model1"  name="coWater.usedAmount" value="${usedAmount}"/>
					        <span id="waterKey" style="display:none">${waterKey}</span>    <input type="hidden" name="coWater.waterKey" value="${waterKey}"/>
					        <span id="waterID" style="display:none">${waterID}</span>   <input type="hidden" name="coWater.waterID" value="${waterID}"/>
			</td> 
          </tr>
          </s:iterator>
    </tbody>
  </table>
 <c:import url="../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/water/ea_getListForPage.jspa?pageNumber=${pageNumber}&search=${search}"></c:param>
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
                    </tr >
                    <tr>
                        <td>
                          单位：
                        </td>
                        <td>
                           <input   name="coWater.unit" />
                        </td >
                    </tr>
                </table>
                <div align="center">
                    <input type="button" class="input-button" id="tosearch" value=" 查询 " /><input name="search" type="hidden" value="search" />
                </div>
            </form>
        </div>
		
		
		<!--查看 -->
		<div class="jqmWindow jqmWindowcss" style="top: 10%;width: 360px"  id="jqModel">
			 <form name="cstaffForm" id="cstaffForm" method="post" action="<%=basePath%>/ea/water/t_ea_save.jspa?pageNumber=${pageNumber}">
			       <div class="drag">用水管理
				    <div class="close"></div>
				    </div>
			<input type="submit" name="submit" style="display:none"/>
			 <div class="content">
			  <div class="contentbannb">
			  </div>
			 <table width="300" border="0" id="stafftable" align="center" cellpadding="0" cellspacing="0" style="margin-top:5px;margin-bottom:5px;">
			 <tr>
			   <td>
			     <table width="300" height="152" border="0" align="center" cellpadding="0" cellspacing="0" id="stafftable2" style="margin-top:5px;margin-bottom:5px;">
			      <tr>
			        <td width="105"  align="right">单位：</td>
			        <td width="195" ><input name="coWater.unit" type="text" id="unit" size="20"/></td>
		           </tr>
			      <tr>
			        <td width="105"  align="right">日期：</td>
			        <td ><input name="coWater.logDate" type="text" id="logDate" size="20" onfocus="date(this);"/></td>
			       </tr>
                    <tr>
			        <td align="right">计划用水量：</td>
			        <td ><input id="amount"   type="text"  class="input" name="coWater.amount" size="20"/></td>
			       </tr>
                    <tr>
			        <td  align="right">实际用水量：</td>
			        <td ><input  id="usedAmount" type="text"  class="input"  name="coWater.usedAmount" size="20"/></td>
			        <input type="hidden" name="coWater.waterKey" id="waterKey" />
					            <input type="hidden" name="coWater.waterID" id="waterID" />
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
