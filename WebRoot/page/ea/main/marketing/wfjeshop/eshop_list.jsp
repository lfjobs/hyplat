<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()	+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>机构负责人</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/admin_main111.css" />
<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<!-- 最好引用 引用顺序不能错 -->
<script type="text/javascript"src="<%=basePath%>js/accifr/js/accift.js"></script>
<script type="text/javascript" >
   var token = 0;
   var select = 1;
   var eshopkey = '';
   var basePath = '<%=basePath%>';
   var pNumber = ${pageNumber};
   var organizationID = '${eshop.organizationID}';   
   var notoken = 0;    
   var ogName = '<%=request.getParameter("ogName") %>';
//打开页面
function getValueForParm1(attachTable,parm1,parm2,parm3,parm4,url){ 
	//alert(parent.$("#jqmWindow2").html());
   // attachTable = $(attachTable).parent().parent().parent().attr("id");
	$("#myform",$("#jqmWindow2")).attr("value",attachTable);
	$("#parm1",$("#jqmWindow2")).attr("value",parm1);
	$("#parm2",$("#jqmWindow2")).attr("value",parm2);
	$("#parm3",$("jqmWindow2")).attr("value",parm3);
	$("#parm4",$("#jqmWindow2")).attr("value",parm4);
	$("#ifrs").attr("src",basePath+url+"?oid="+organizationID);
	$("#jqmWindow2").jqmShow();
}

</script>
<script src="<%=basePath%>js/ea/marketing/wfjeshop/eshop_list.js"></script>

</head>
<body>
<form  name="addressForm" id="addressForm" method="post" enctype="multipart/form-data">
<s:token></s:token>
<input type="submit" name="submit" style="display:none"/>
<div id="main_main" class="main_main">
  <table id="addressTable"  class="address">
	  	<thead>
		 	    <tr>
			 	    <th width="30" align="center"  >选择</th>
			 	    <th width="100" align="center" >店主</th>
		            <th width="80" align="center" >开店时间</th>
		            <th width="70" align="center" >电话</th>
		            <th width="80" align="center" >QQ</th>
		            <th width="85" align="center" >状态</th>
		            <th width="85" align="center" >是否使用</th>
		            <th width="60" align="center" >所属行业</th>
		            <th width="150" align="center" >店铺地址</th>
		            <th width="100" align="center" >Logo</th>
		            <th width="100" align="center" >主题图</th>
		            <th width="250" align="center" >简介</th>							            
	      		</tr>
	    </thead>
		<tbody  id="tbwid">
	           <tr id="sa" style="display:none;" class="td_bg01 saveAjax model2">
	           <td><input type="radio" name="a" class="JQuerypersonvalue" value=""/></td>
		           <td class="td_bg01">
			             <input name="staffID" id="partnerID" type="hidden"  size="6" />
			             <input name="owner" readonly="readonly" id="partnerName" size="6"/>		             
			             <a href="#" id="chioce" class="onc"
			             onclick="getValueForParm1('addressForm','staffCode','partnerName','reference','childPartnerName1','ea/cosincumbent/ea_getStaffForCashier.jspa')">选择</a>
			        </td>
		            <%--<td class="td_bg01"><input name="owner" id="owner"  size="30" /> </td>--%>
		            
		            <td class="td_bg01"><input name="regdate" id="regdate"  onfocus="date();" size="10" /></td>		            
		            <td class="td_bg01"><input name="telephone" id="telephone" size="20" /></td>
		            <td class="td_bg01"><input name="qq" id="qq" size="15"/></td>
		            <td class="td_bg01"><select name="eshopstatus" id="eshopstatus"><option value="0">正常</option><option value="1">欠费</option><option value="2">暂停</option><option value="3">终止</option></select></td>
		            <td class="td_bg01"><select name="inused" id="inused"><option value="0">否</option><option value="1">是</option></select></td>
		            <td class="td_bg01">
		            	<input name="tradeid" id="tradeid" size="15"/>
		            </td>
		            <td class="td_bg01"><input name="address" id="address" size="100" /></td>
		            <td class="td_bg01">
		            <img id="logo"  alt="" width="20" height="20" style="float: left;margin-left: 40%;"/> 
		            <input id="logo" name="logo" type="hidden" />
		            <div class="btncon"  onclick="javascript:aa()" style="height:18px; width: 22px; margin: 0px; border: 0px;float: right; "/></div>
		            </td>
		            <td class="td_bg01">
		            <img id="titleimage"  alt="" width="20" height="20" style="float: left;margin-left: 40%;"/>
		            <input id="titleimage" name="titleimage" type="hidden" 	/>
		            <div class="btncon"  onclick="javascript:aa()" style="height:18px; width: 22px; margin: 0px; border: 0px;float: right; "></div>
		            </td>
		            
		            <td class="td_bg01"><input name="intro" id="intro" size="200"/>                                                                                                                                                          
							            <input type="hidden" name="eshopkey" id="eshopkey"/>							            
							            <input type="hidden" name="organizationID" id="organizationID" value="${eshop.organizationID}"/>
					</td> 
	          </tr>
          	  <s:iterator value="pageForm.list" id="eshop">
          	  	<tr class="td_bg01 saveAjax" id='<s:property value="#eshop.eshopkey"/>' class="trclass">
          	  		<td class="td_bg01">
						<input type="radio" name="a" class="JQuerypersonvalue trclass" value='<s:property value="#eshop.eshopkey"/>' />
					</td>
					
					
					
					
					
					<td class="td_bg01">
							<span><s:property value="#eshop.owner"/></span>
							<input type="hidden" name="staffID" id="partnerID"
								value='<s:property value="#eshop.owner"/>' size="6" />
							<input class="model1" readonly="readonly" name="owner"
								value='<s:property value="#eshop.owner"/>' id="partnerName" size="6" />
							<a href="#" id="chioce" class="model1 onc"
								onclick="getValueForParm1('addressForm','staffCode','partnerName','reference','childPartnerName1','ea/cosincumbent/ea_getStaffForCashier.jspa')">选择</a>
					</td>
					
					
					
					
					
					
					
					<td class="td_bg01">
						<span id="regdate" class="datas"><s:property value="#eshop.regdate"/></span>
						<input class="model1" value='<s:property value="#eshop.regdate"/>' name="regdate" onfocus="date(this);" />
					</td>
					<td class="td_bg01">
					<span><s:property value="#eshop.telephone"/></span>
						<input class="model1" value='<s:property value="#eshop.telephone"/>' style="border: 0" name="telephone" id="telephone" />
					</td>
					<td class="td_bg01">
					<span><s:property value="#eshop.qq"/></span>
						<input class="model1" value='<s:property value="#eshop.qq"/>' style="border: 0" name="qq" id="qq" />
					</td>
					<td class="td_bg01">
					<span><s:if test="#eshop.eshopstatus==0">正常</s:if>
							<s:if test="#eshop.eshopstatus==1">欠费</s:if>
							<s:if test="#eshop.eshopstatus==2">暂停</s:if>
							<s:if test="#eshop.eshopstatus==3">终止</s:if></span>
						<select name="eshopstatus" id="eshopstatus" class="model1">
							<s:if test="#eshop.eshopstatus==0"><option value="0" selected="selected">正常</option></s:if><s:else><option value="0">正常</option></s:else>
							<s:if test="#eshop.eshopstatus==1"><option value="1" selected="selected">欠费</option></s:if><s:else><option value="1">欠费</option></s:else>
							<s:if test="#eshop.eshopstatus==2"><option value="2" selected="selected">暂停</option></s:if><s:else><option value="2">暂停</option></s:else>
							<s:if test="#eshop.eshopstatus==3"><option value="3" selected="selected">终止</option></s:if><s:else><option value="3">终止</option></s:else>							
						</select>						
					</td>
					<td class="td_bg01">
					<span><s:if test="#eshop.inused==0">否</s:if>
							<s:if test="#eshop.inused==1">是</s:if></span>
						<select name="inused" id="inused" class="model1">
							<s:if test="#eshop.inused==0"><option value="0" selected="selected">否</option></s:if><s:else><option value="0">否</option></s:else>
							<s:if test="#eshop.inused==1"><option value="1" selected="selected">是</option></s:if><s:else><option value="1">是</option></s:else>														
						</select>						
					</td>
					<td class="td_bg01">
						<span><s:property value="#eshop.tradeid"/></span>
						<input class="model1" value='<s:property value="#eshop.tradeid"/>' style="border: 0" name="tradeid" id="tradeid" />
					</td>
					<td class="td_bg01">
						<span><s:property value="#eshop.address"/></span>
						<input class="model1" value='<s:property value="#eshop.address"/>' style="border: 0" name="address" id="address" />
					</td>
					<td class="td_bg01">
		            	 <s:if test="#eshop.logo==null || #eshop.logo==''"><span style="float: left;margin-left: 10%;">无</span>
							<img id="logo"   width="20" height="20" style="float: left;margin-left: 40%;"/>
						</s:if>
						<s:else>
							<span class="datas" onclick="lookImage('<s:property value="#eshop.logo"/>');" style="float: left;margin-left: 10%;"><a
								href="#">查看原图</a>
							</span>
							<img id="logo" src="<%=basePath%><s:property value="#eshop.logo"/>"  width="20" height="20" style="float: left;margin-left: 10%;"/>
						</s:else>
		            	<div class="btncon model1 "   onclick="javascript:aa()" style="height:18px; width: 22px; margin: 0px; border: 0px;float: right; "/></div>
						<input id="logo" name="logo" type="hidden" value='<s:property value="#eshop.logo"/>'	class="model1" />
					</td>
					<td class="td_bg01">
						<s:if test="#eshop.titleimage==null || #eshop.titleimage==''"><span style="float: left;margin-left: 10%;">无</span>
							<img id="titleimage"   width="20" height="20" style="float: left;margin-left: 40%;"/>
						</s:if>
						<s:else>
							<span class="datas" onclick="lookImage('<s:property value="#eshop.titleimage"/>');" style="float: left;margin-left: 10%;"><a
								href="#">查看原图</a>
							</span>
							<img id="titleimage" src="<%=basePath%><s:property value="#eshop.titleimage"/>"  width="20" height="20" style="float: left;margin-left: 10%;"/>
						</s:else>
		            	 
		           		 <div class="btncon model1" onclick="javascript:aa()" style="height:18px; width: 22px; margin: 0px; border: 0px;float: right;  "/></div>
						<input id="titleimage"  name="titleimage" type="hidden" value='<s:property value="#eshop.titleimage"/>'	class="model1" />
					</td>
					<td class="td_bg01">
						<span><s:property value="#eshop.intro"/></span>
						<input class="model1" value='<s:property value="#eshop.intro"/>' style="border: 0" name="intro" id="intro" />
						<input class="model1" value='<s:property value="#eshop.eshopkey"/>' name="eshopkey" id="eshopkey" />
						<input class="model1" value='<s:property value="#eshop.organizationID"/>' name="organizationID" id="organizationID" />
					</td>
					
          	  	</tr>
          	  </s:iterator>
    	</tbody>
  </table>
 <c:import url="../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/marketingWfj/ea_getWfjEshopList.jspa?pageNumber=${pageNumber}&eshop.organizationID=${eshop.organizationID}&ogName=${param.ogName}"></c:param>
</c:import>
</div>
</form>
<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
<div id="jqmWindow2" class="jqmWindow"
	style="width: 95%; height: 400px; absolute; display: none; left: 1%; top: 1%; background: #eff">
	<div style="background: #efg; margin-right: 500px;">
		<input style="display: none;" id="myform" />
		<input style="display: none;" id="parm1" />
		<input style="display: none;" id="parm2" />
		<input style="display: none;" id="parm3" />
		<input style="display: none;" id="parm4" />
		
	</div>
	<iframe name="ifr" id="ifrs" width="100%" height="350px"
		frameborder="0"></iframe>
	<div align="center">
		<input type="button" class="input-button" id="isSubmit" value=" 确定 "
			style="cursor: hand" />
		<input type="button" class="input-button" id="isBack" value=" 关闭 "
			style="cursor: hand" />
	</div>
</div>
<div id="div_allFun" class="rTBotr" style="display:none;z-index:210000; position:absolute;">...</div>  
</body>
</html>
