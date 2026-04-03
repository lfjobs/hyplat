<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>银行帐号</title>
<!-- 此页面在2014-12-18被humanResource.jsp替代。三个月后没有用到可以删除 -->
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script src="<%=basePath%>js/ea/human/cstaff/cstaff_bankaccount.js"></script>

<script type="text/javascript">
   var token = 0;
   var select = 1;
   var bankAccountID = '';
   var basePath = '<%=basePath%>';
   var staffID = '${bankAccount.staffID}'; 
   var notoken = 0;
</script>
</head>

  <body>
  <!-- 银行地址 -->
		 <div class="jqmWindow" style="width: 600px;right: 25%;top:10%" id="jqModelSearch">
                                            <div class="drag">
                                                地址信息
                                                <div class="close">
                                                </div>
                                            </div>
                                            <table id="cataffSearchTable"> 
                                                <tr> 
				                            	<td class="JQueryaddresssearch" >
				                            		 <input name="searchCStaff.address" id="address"  type="hidden"/>
				                            		 <input name="searchCStaff.staffAddress" id="staffAddress"   type="hidden"/>
												     <select name="addressProvince" id="province" number='0' style="width:110px;" ></select><!-- <option>选择省</option>-->
												     <select name="addressCity" id="city" number='1' style="width:110px;"></select>
												     <select name="addressCounty" id="county" number='2'  style="width:110px;"></select>
												     <select name="addressTown" id="addressTown" number='3'  style="width:110px;"></select>
												     <select name="addressVillage" id="addressVillage" number='4'  style="width:110px;"></select>
												     <select name="addressCommunity" id="addressCommunity" number='5' style="width:110px;" ></select><!-- <option>选择省</option>-->
												     <select name="addressFloor" id="addressFloor" number='6' style="width:110px;"></select>
												     <select name="addressLayer" id="addressLayer" number='7'  style="width:110px;"></select>
												     <select name="addressSize" id="addressSize" number='8'  style="width:110px;"></select>
				                                </td>
				                           	</tr>
                                            </table>
                                            <div align="center">
                                               <input class="input-button" type = "button" value="确定"  id = "addrOK"/>
	         								   <input class="input-button" type = "button" value="取消" id = "addrREST"/>
                                            </div> 
                                    </div>
 		<form  name="contactForm" id="contactForm" method="post"><input type="submit" name="submit" style="display:none"/>
		<s:token></s:token>
<div id="main_main" class="main_main"> 
  <table class="contact">
  	<thead>
	    <tr>
	    <th width="30" align="center">选择</th>
	      <th width="60" align="center" >银行编号</th>
	      <th width="60" align="center" >证明号</th>
	      <th width="80" align="center" >银行名称</th>
	      <th width="65" align="center" >发证日期</th>
	      <th width="60" align="center" >账户性质</th>
	      <th width="100" align="center" >银行帐号</th>
	      <th width="65" align="center" >开户时间</th>
	      <th width="80" align="center" >银行联系方式</th>
	      <th width="200" align="center">银行地址</th>
	      <th width="65" align="center">注销时间</th>
	    </tr>
    </thead>
	<tbody id="tbwid">
	    <tr class="td_bg01 saveAjax model2" id="sa" style="display:none;">
		      <td><input type="radio" name="a"  class="JQuerypersonvalue" value="${staffID}" /></td>
		      <td><input name="bankCode" id="bankCode" size="10"/></td>
		      <td class="td_bg01"><input name="approvalNumber" id="approvalNumber" size="10"/></td>
		       <td class="td_bg01"><input name="bankName" id="bankName" size="10"/></td>
		      <td class="td_bg01"><input name="theDate" id="theDate" onfocus="date(this);"  size="10"/></td>
		      <td class="td_bg01"><input name="accountNature" id="accountNature" size="10"/></td>
		      <td class="td_bg01"><input name="bankAccount" id="bankAccount" size="10"/></td>
		      <td class="td_bg01"><input name="openDate" id="openDate" onfocus="date(this);"  size="10"/></td>
		      <td class="td_bg01"><input name="bankContact" id="bankContact" size="10"/></td>
		      <td class="td_bg01"><input name="bankAddress" id="bankAddress" class="bankAddr" size="30"/>
							      <input type="hidden" name="bankAccountKey" id="bankAccountKey"/>
							      <input type="hidden" name="bankAccountID" id="bankAccountID"/>
							      <input type="hidden" name="staffID" value="${bankAccount.staffID}" id="staffID"/>
			  </td>
		      <td class="td_bg01"><input name="cancellationDate" id="cancellationDate" onfocus="date(this);" size="10"/></td>
	    </tr>
    <s:iterator value="bankAccountList">
          <tr class="td_bg01 saveAjax trclass" id="${bankAccountID}">
	         	 	<td class="td_bg01">
	            	 	<input type="radio" name="a" class="JQuerypersonvalue" value="${bankAccountID}"/>
	            	</td>
		            <td class="td_bg01"><span id="bankCode">${bankCode}</span>
      					<input class="model1" name="bankCode"  value="${bankCode}" size="10"/>
      				</td>
      				 <td class="td_bg01"><span id="approvalNumber">${approvalNumber}</span>
      					  <input class="model1" name="approvalNumber" value="${approvalNumber}" size="10"/></td>
      				 <td class="td_bg01"><span id="bankName">${bankName}</span>
      					  <input class="model1" name="bankName" value="${bankName}" size="10"/></td>
      				 <td class="td_bg01"><span id="theDate" class="datas">${theDate}</span>
      				      <input class="model1"  value="${theDate}" name="theDate" onfocus="date(this);" size="10"/></td>
      				 <td class="td_bg01"><span id="accountNature">${accountNature}</span>
      					  <input class="model1" name="accountNature" value="${accountNature}" size="10"/></td>
      				<td class="td_bg01"><span id="bankAccount">${bankAccount}</span>
      					  <input class="model1" name="bankAccount" value="${bankAccount}" size="10"/></td>
      				<td class="td_bg01"><span id="openDate" class="datas">${openDate}</span>
      				      <input class="model1"  value="${openDate}" name="openDate" onfocus="date(this);" size="10"/></td>
     				 <td class="td_bg01"><span id="bankContact">${bankContact}</span>
      					  <input class="model1" name="bankContact" value="${bankContact}" size="10"/></td>
      				 <td class="td_bg01"><span id="bankAddress">${bankAddress}</span>
     					  <input class="model1 bankAddr" name="bankAddress" value="${bankAddress}" size="30"/>
					      <input type="hidden" name="bankAccountKey" value="${bankAccountKey}"/>
					      <input type="hidden" name="bankAccountID" id="bankAccountID" value="${bankAccountID}"/>
					      <input type="hidden" name="staffID" id="staffID" value="${staffID}" />
	 </td>
      <td class="td_bg01">
      <span id="cancellationDate" class="datas">${cancellationDate}</span>
      <input class="model1" name="cancellationDate" value="${cancellationDate}" onfocus="date(this)" size="10"/></td>
    </tr>
    </s:iterator>
    </tbody>
  </table>
</div>
</form>
<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
<script type="text/javascript">
$(function(){   
	setTimeout(function(){ 	
        $("div.bDiv").css({"height":parent.document.getElementById("mainframe16").offsetHeight-57+"px"});
 },100);
	 $(window).resize(function(){ 	
		 setTimeout(function(){ 	
		        $("div.bDiv").css({"height":parent.document.getElementById("mainframe16").offsetHeight-57+"px"});
		 },100);
		 }); 	
});
</script>
</body>
</html>

