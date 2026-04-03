<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
        <%@ page language="java" pageEncoding="UTF-8" %>
        <%@ taglib uri="/struts-tags" prefix="s" %>
        <%@page import="hy.ea.bo.Company"%>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
          <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
        <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
        + request.getServerName() + ":" + request.getServerPort()
        + path + "/"; 
        Company c = (Company)session.getAttribute("currentcompany"); %>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="cache-control" content="no-cache"/>
        <title>车辆信息管理</title>
        <script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
        <style type="text/css"> 
        .wenben{
        	border-color: #FFFFFF;
        	border-width: 0px;
        	background: url('../../../images/ea/office/brue.jpg')
        }
        </style>
        <script src="<%=basePath%>js/jquery.js" type="text/javascript">
        </script>
        <script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
        </script>
        <link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
			type="text/css" />
        <link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"
			type="text/css" />
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
        <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/common/organizationTree.js">
        </script>
        <script src="<%=basePath%>js/ea/office_ea/CarInformation.js"></script>
        <script src="<%=basePath%>js/ea/office_ea/carMenu.js"></script>
         <script type="text/javascript">
         var treeID = '<%=session.getAttribute("organizationID")%>';
         var treePName='<%=session.getAttribute("organizationName")%>';
         var treeNames='<%=c.getCompanyName()%>';
		 var  basePath='<%=basePath%>';           
         var pNumber =${pageNumber};  
         var  search='${search}';  
         var carNums="";
         var personurl = "";
         var carID = "";
         var token=0;
         var treeid;
  		 var treename;
		 var goodsBillsID="";
		 var notoken = 0;
		 var tokens = 0;
		 var token = 0;
		 var companyID='${account.companyID}';
   		</script>  
   		<script>
   		function importGY(attachSearchTable,checkopertionID,checkopertionName,childopertionName,url){ //打开页面
			  if(checkopertionName=="bankNum"){
		 	var departmentID =  $("#departmentID").attr("value");
		 	url = url + "?departmentID="+departmentID+"&title1=title1";
		 }
		 url = url + "?title1=title1";
		 $("#checkopertionID",$("#bankJqm")).attr("value",checkopertionID);
		 $("#checkform",$("#bankJqm")).attr("value",attachSearchTable);
		 $("#checkopertionName",$("#bankJqm")).attr("value",checkopertionName);
		 $("#childopertionName",$("#bankJqm")).attr("value",childopertionName);
	  	 $("#daoRu").attr("src",basePath+url);
	  	 $("#bankJqm").jqmShow();
			}
			$(document).ready(function() {//销售单FORM
			$("#DaoRuFan").click(function(){// 返回
		       $("#bankJqm").jqmHide();
		}); 
	$("#DaoRuFanqd").click(function(){// 选择确定
		var checkopertionID =$("#checkopertionID",$("#bankJqm")).attr("value");
		var checkform =$("#checkform",$("#bankJqm")).attr("value");
		var checkopertionName = $("#checkopertionName",$("#bankJqm")).attr("value");
		var childopertionName = $("#childopertionName",$("#bankJqm")).attr("value");
		var childopertionID = window.frames["daoRu"].opertionID;
		if(childopertionID == ""){
			alert("请选择")
			return;
		}
		var no = window.frames["daoRu"].$('tr#'+childopertionID).find("span#"+checkopertionName).text();
		var childopertionName =window.frames["daoRu"].$('tr#'+childopertionID).find("span#"+childopertionName).text();
		if(checkopertionID != "")
			$("#"+checkopertionID,$("#"+checkform)).attr("value",childopertionID).trigger("blur");
		if(checkopertionName != ""){
			$("#"+checkopertionName,$("#"+checkform)).attr("value",childopertionName).trigger("blur");
		}
		if(checkopertionName =="staffName"){
			var final = no + "---" + childopertionName;
			$("#"+checkopertionName,$("#"+checkform)).attr("value",final).trigger("blur");
		}
		 $("#daoRu").attr("src","");
         $("#bankJqm").jqmHide();
   });
});
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
                         <th width="40" align="center">
                            	序号
                        </th>
                           <th width="150" align="center">
                            	公司
                        </th>
                         <th width="100" align="center">
                            	部门
                        </th>
                        <th width="100" align="center">
                            责任人
                        </th>
                        <th width="150" align="center">
                            	车型
                        </th>
                        <th width="100" align="center">
                            	车牌号
                        </th>
                        <th width="100" align="center">
                           		 发动机号
                        </th>
                        
                        <th width="100" align="center">
                           		车架号
                        </th>
                        <th width="100" align="center">
                           		注册登记日期
                        </th>
                        <th width="100" align="center">
                            当前状态
                        </th>
                        <th width="100" align="center">
                            加盟状态
                        </th>
     
                        
                    </tr>
                </thead>
                <tbody>
                    <%
                    int number = 1; %>
                    <s:iterator value="pageForm.list">
                        <tr id="${carID}"  >
                            <td>
                                <input type="radio" name="a"  class="JQuerypersonvalue" value="${carID}"/>
                            </td>
                            <td>
                                <span ><%=number%></span>
                            </td>
                             <td>
                                <span >${companyname}</span>
                            </td>
                            <td>
                                <span >${organizationname}</span>
                            </td>
                             <td>
                                <span id="staffName">${staffName}</span>
                            </td>
                            <td>
                                <span id="carType">${carType}</span>
                            </td>
                             <td>
                                <span id="carNum">${carNum}</span>
                            </td>     
                            <td>
                                <span id="engineNum">${engineNum}</span>
                            </td>
                            
                            <td>
                                <span id="carFrameNum">${carFrameNum}</span>
                            </td>
                            <td>
                                <span id="registrationDate">${registrationDate}</span>
                            </td>
                            <td>
                            	<span id="state3">${state3=='00'?'未使用':state3=='01'?'已使用':'下线'}</span>
                            </td>
                            <td>
                            	<span id="state1">${state1=='00'?'加盟车':'本校车'}</span>
                            	<span id="carID" style="display:none">${carID}</span>
                                 <span id="carKey"  style="display:none">${carKey}</span>
                            </td>
    
                            
                        </tr>
                        <%number++;%>
                    </s:iterator>
                </tbody>
                </table>
            <c:import url="../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/car/ea_getCarInformationList.jspa?pageNumber=${pageNumber}&search=${search}">
                </c:param>
            </c:import>
             <iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
            <div style="width: 100%">
                <iframe src="" name="main" width="99%" scrolling="no"  marginwidth="0" height="268px" marginheight="0" frameborder="0" id="mainframe" border="0" framespacing="0" noresize="noResize" vspale="0">
                </iframe>
            </div>
        </div>
        <!-- 车辆添加 -->
        <div class="contentbannb jqmWindow jqmWindowcss2" style="top: 10%" id="jqModel">
            <form name="cstaffForm" id="cstaffForm" method="post" enctype="multipart/form-data">
                <input type="submit" name="submit" style="display:none"/>
                <div class="content">
				  <div class="contentbannb">
				  	<div class="drag">车辆管理
				    <div class="close"></div>
				  </div>
				  </div>
				   <table width="99%" id="table3">	
				     <tr>
				     <td>
				     <table width="699" height="263" border="0" id="stafftable" align="center" cellpadding="0" cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
				     <tr>
						<td  height="40" align="right"><span class="STYLE1">公司名称：</span></td>
						<td ><span id="companyNames"></span></td>
						<td height="40" align="right">责 任 人：</td>
						<td><input type="hidden" id="partnerID" name="carInformation.staffID" readonly="readonly" value="${carInformation.staffID}" />
							<input type="text" id="staffName" name="carInformation.staffName" class="put3" readonly="readonly" value="${carInformation.staffName}" size="15"/><a href="#" onclick="importGY('table3','partnerID','staffName','childPartnerName','ea/cosincumbent/ea_getStaffForCashier.jspa')">选择</a></td>
					</tr>
					<tr>
						<td height="40" align="right">部门名称：</td>
						<td ><span id="dept"></span></td>
						<td height= "46" align="right">选择车辆：</td>
                       	<td><input type="button" id="newG" name="button7"value="选择车辆" /></td>
					</tr>
                    <tr>
                      	<td height="46" align="right">车 牌 号：</td>
                      	<td ><span class="carNum" id="carNum">${carInformation.carNum}</span></td>
                      	<td height="46" align="right">发动机号：</td>
                      	<td ><span id="engineNum"></span></td>
                    </tr>
                    <tr>
                        <td height="46"  align="right">车辆类型：</td>
                        <td ><span id="carType"></span></td>
                        <td  align="right">车 架 号：</td>
                        <td ><span id="carFrameNum"></span></td>
                    </tr>
                    <tr>
	                    <td height="46" align="right">注册日期：</td>
	                     <td><span id="registrationDate"></span></td>
	                     <td height = "46" align="right">使用状态：</td>
                     	 <td><select name="carInformation.state3" id="state3">
						     <option value="00">未使用</option>
						    <option value="01">已使用</option>
						   </select></td>
                    </tr>
                     <tr>
		                  <td align="right">加盟状态：</td>
		                  <td>
		                  <select name="carInformation.state1" id="j_state">
					      <option value="00">加盟车</option>
					      <option value="01">本校车</option>
                              <option value="02">教练车</option>
					   </select></td>
						 <td height="40" align="right">行驶证发证日期：</td>
						 <td>
                    </tr>
						 <tbody id="j_body" style="display: none;">
                         <tr>
                             <td height = "46" align="right">
                                 培训车型：</td>
                             <td>
                                 <select  name="tcar.perdritype" style="width:140px;">
                                     <option value="A1">A1</option>
                                     <option value="A2">A2</option>
                                     <option value="A3">A3</option>
                                     <option value="B1">B1</option>
                                     <option value="B2">C2</option>
                                     <option value="C1">C1</option>
                                     <option value="C2">C2</option>
                                     <option value="C3">C3</option>
                                     <option value="C4">C4</option>
                                     <option value="C5">C5</option>
                                     <option value="D">D</option>
                                     <option value="E">E</option>
                                     <option value="F">F</option>
                                     <option value="M">M</option>
                                     <option value="N">N</option>
                                     <option value="P">P</option>
                                 </select>
                             </td>
                             <td height = "46" align="right">
                                 技术等级：</td>

                             <td><select  name="tcar.skillLevel" style="width:140px;">
									 <option value="1">无</option>
									 <option value="2">一级</option>
									 <option value="3">二级</option>
									 <option value="4">三级</option>
								 </select>
                             </td>
                         </tr>
                         <tr>
                             <td height = "46" align="right">
                                 燃油：</td>
                             <td> <select  name="tcar.fuel" style="width:140px;">
                                     <option value="1">无</option>
                                     <option value="2">汽油</option>
                                     <option value="3">3柴油</option>
                                     <option value="4">4其他</option>
                                 <option value="5">双燃油</option>
                                 </select>
                             </td>
                             <td height = "46" align="right">
                                 车辆颜色：</td>
                             <td><select  name="tcar.plateColor" style="width:140px;">
                                     <option value="1">蓝色</option>
                                     <option value="2">黄色</option>
                                     <option value="3">黑色</option>
                                     <option value="4">白色</option>
                                     <option value="5">绿色</option>
                                     <option value="9">9其他</option>
                             </select>
                             </td>
                         </tr>
                         <tr>
                             <td height = "46" align="right">
                                 生产厂家：</td>
                             <td><input type="text" style="width:140px;" name="tcar.manufacture">
                             </td>
                             <td height = "46" align="right">
                                 厂牌型号：</td>
                             <td><input type="text" style="width:140px;" name="tcar.model">
                             </td>
                         </tr>
                         <tr>
                             <td height = "46" align="right">
                                 购买时间：</td>
                             <td><input type="text" style="width:140px;" name="tcar.buydate" onfocus="daytime(this);">
                             </td>
                             <td height = "46" align="right">
                                 载客量：</td>
                             <td><input type="text" style="width:140px;" name="tcar.zkl">
                             </td>
                         </tr>
                         <tr>
                             <td height = "46" align="right">
                                 检测情况：</td>
                             <td><input type="text" style="width:140px;" name="tcar.equipStatus">
                             </td>
                             <td height = "46" align="right">
                                 备注：</td>
                             <td><input type="text" style="width:140px;" name="tcar.remark">
                             </td>
                         </tr>
						 </tbody>
                    <tr>
                      <td height="30" colspan="5" align="center"><input name="carInformation.carKey" id="carKey" type="hidden" class="input"  size="20"/>
                          <input name="carInformation.carID" id="contactUserID" type="hidden" class="input"  size="20"/>
                          <input type="button"   class="input-button JQuerySubmit" style="cursor:pointer;width:80px;" value="提交" />
                         <input name="sub" value="${session_value}" type="hidden" /><!-- 代替token-->
                          <input type="button"  class="input-button JQueryreturn" style="cursor:pointer;width:80px;"  value="取消" />                         </td>
                    </tr>

                     </table>
				     </td>
				     </tr>
				</table>
				</div>
				<s:token></s:token>
            </form>
        </div> 
         <!-- 车辆修改 --> 
         <div class="jqmWindow " style="width: 400px; right: 25%; top: 10%" id="jqModels">
            <form name="cstaffForms" id="cstaffForms" method="post" enctype="multipart/form-data">
                <input type="submit" name="submit" style="display:none"/>
                	<div class="drag">
				修改责任人
			</div>
				   <table width="99%" id="table4">	
				     <tr>
				     <td>
				     	<table  border="0" id="stafftables" align="center" cellpadding="0" cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
				     		<tr>
								<td height="40" align="right">责任人：</td>
								<td>
							      <input type="hidden" id="partnerID" name="carInformation.staffID" readonly="readonly" value="${carInformation.staffID}" />
							      <input type="text" id="staffName" name="carInformation.staffName" readonly="readonly" value="${carInformation.staffName}" size="15" />
								  <a href="#" onclick="importGY('table4','partnerID','staffName','childPartnerName','ea/cosincumbent/ea_getStaffForCashier.jspa')">选择</a>
							     </td>
							</tr>
                       		<tr>
		                         <td height="30" colspan="5" align="center"><input name="carInformation.carKey" id="carKey" type="hidden" class="input"  size="20"/>
		                            <input name="carInformation.carID" id="carID" type="hidden" class="input"  size="20"/>
		                            <input type="button"   class="input-button JQuerySubmits" style="cursor:pointer;width:80px;" value="提交" />
		                           <input name="sub" value="${session_value}" type="hidden" /><!-- 代替token-->
		                            <input type="button"  class="input-button JQueryreturns" style="cursor:pointer;width:80px;"  value="取消" /></td>
		                    </tr>
                     </table>
				     </td>
				     </tr>
				</table>  
        	<s:token></s:token> 
        	 	  </form>
        </div>
        <!-- 下线以及调离功能 -->
         <div class="jqmWindow " style="width: 400px; right: 25%; top: 10%" id="jqModeles">
            <form name="cstaffFormes" id="cstaffFormes" method="post" enctype="multipart/form-data">
                <input type="submit" name="submit" style="display:none"/>
                	<div class="drag">
				下线
			</div>
				   <table width="99%" id="table4">	
				     <tr>
				     <td>
				     <table  border="0" id="stafftablees" align="center" cellpadding="0" cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
				     <tr>
								<td height="40" align="right">目前状态：</td>
								<td>
								  <td>
							      <select name="carInformation.state3" id="state3">
					     			<option value="00">未使用</option>
					    			<option value="01">已使用</option>
					    			<option value="10">下线</option>
					   			</select>
							     </td>
								
							</tr>
                       <tr>
                         <td height="30" colspan="5" align="center"><input name="carInformation.carKey" id="carKey" type="hidden" class="input"  size="20"/>
                             <input name="carInformation.carID" id="carID" type="hidden" class="input"  size="20"/>
                             <input type="button"   class="input-button JQuerySubmites" style="cursor:pointer;width:80px;" value="提交" />
                            <input name="sub" value="${session_value}" type="hidden" /><!-- 代替token-->
                             <input type="button"  class="input-button JQueryreturnes" style="cursor:pointer;width:80px;"  value="取消" /></td>
                       </tr>
                     </table>
				     </td>
				     </tr>
				</table>  
        	<s:token></s:token> 
        	 	  </form>
        </div>
       
       <div class="jqmWindow " style="width: 400px; right: 25%; top: 10%" id="jqModeldept">
            <form name="cstaffFormdept" id="cstaffFormdept" method="post" enctype="multipart/form-data">
                <input type="submit" name="submit" style="display:none"/>
                	<div class="drag">
				调离
			</div>
				   <table width="99%" id="table4">	
				     <tr>
				     <td>
				     <table  border="0" id="stafftabledept" align="center" cellpadding="0" cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
				     <tr>
						<td height="40" align="right">
							部门：
						</td>
						<td align="left" id="dept">
							<select name="carInformation.organizationID" id="organizationID"
								style="width: 180px;">
								<option value="0">
									请选择
								</option>
								</select>
						</td>
					</tr>
					<tr>
						<td height="40" align="right">责任人：</td>
						<td>
							<select name="carInformation.staffID" id='person' style="width: 180px;">
								<option value="">
									请选择部门
								</option>
							</select>
					     </td>
					</tr>
                       <tr>
                         <td height="30" colspan="5" align="center"><input name="carInformation.carKey" id="carKey" type="hidden" class="input"  size="20"/>
                             <input name="carInformation.carID" id="carID" type="hidden" class="input"  size="20"/>
                             <input type="button"   class="input-button diaoli" style="cursor:pointer;width:80px;" value="提交" />
                            <input name="sub" value="${session_value}" type="hidden" /><!-- 代替token-->
                             <input type="button"  class="input-button JQueryreturndept" style="cursor:pointer;width:80px;"  value="取消" /></td>
                       </tr>
                     </table>
				     </td>
				     </tr>
				</table>  
        	<s:token></s:token> 
        	 	  </form>
        </div>
        <!--搜索窗口 -->
		<form name="carSearchForm" id="carSearchForm" method="post">
			<div class="jqmWindow" style="width:300px; right: 35%; top: 10%"
				id="jqModelcarSearch">
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table id="carSearchTable">
					<tr>
						<td height="40" >
							查询条件 
						</td>
					</tr>
					<tr>
						<td height="40">
							车牌号：
						</td>
						<td>
							<input name="carInformation.carNum" />
						</td>
					</tr>
					<tr>
						<td height="40">
							车辆类型：
						</td>
						<td>
							<input name="carInformation.carType" />
						</td>
					</tr>
					<tr>
						<td height="40">
							责任人：
						</td>
						<td >
							<input name="carInformation.staffName" />
						</td>
					</tr>
					<tr>
						<td height="40">
							当前状态：
						</td>
						<td>
							<select name="carInformation.state3" id="state3">
							<option value="" selected="selected">
									全部
								</option>
								<option value="00">未使用</option>
					    			<option value="01">已使用</option>
					    			<option value="10">下线</option>
					   			</select>
						</td>
					</tr>
					<tr>
						<td height="40">
							加盟状态：
						</td>
						<td>
						<select name="carInformation.state1" id="state1">
						<option value="" selected="selected">
									全部
								</option>
					     <option value="00">加盟车</option>
					    <option value="01">本校车</option>
					   </select>
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="searchCar"
						value=" 查询 " />
					<input name="search" type="hidden" value="search" />
				</div>
			</div>
		</form>
        <!-- 车辆详细信息 -->
           <form name="allForm" id="allForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<div class="jqmWindow jqmWindowcss1" style="width: 820px; top: 0%""
				id="allModel">
				<div class="content1" style="width: 100%;height: 400px;">
					<div class="contentbannb">
						<div class="drag">
							查看车辆详细信息
						</div>
					</div>
					<table width="99%" border="0" align="center" cellpadding="0"
						cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
						<tr>
							<td width="99%" valign="top" align="left">
								<div id="carAllList"
									style="margin-top: 2px; display: none; height: 330px; width: 100%; overflow: scroll;">
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<s:token></s:token>
		</form>
        <!-- 查询菜单 --> 
        <!-- 
        <div >
			<ul>
				<li>
					<ul class="menu00" style="z-index: 1">
						<li>
							<a href="#" id="carNumweihu">车牌号维护</a>
						</li>
						<li>
							<a href="#" id="carinvoice">购车发票</a>
						</li>
						<li>
							<a href="#" id="carPurchaseTax">购置税发票</a>
						</li>
						<li>
							<a href="#" id="carInsurance">车辆保险信息</a>
						</li>
						<li>
							<a href="#" id="carAReview">车辆年审信息</a>
						</li>
						<li>
							<a href="#" id="carCNG">车辆CNG信息</a>
						</li>
						<li>
							<a href="#" id="CarSafeInformation">车辆安全信息</a>
						</li>
						<li>
							<a href="#" id="CarwagwInformation"> 车辆资产信息</a>
						</li>
						<li>
							<a href="#" id="CarUserInformation">车辆使用信息</a>
						</li>
						<li>
							<a href="#" id="MaintainManagerInformation">车辆维护信息</a>
						</li>
						<li>
							<a href="#" id="Certificatea">相关证件子集</a>
						</li>
						<li>
							<a href="#" id="motorcar">机动车行驶证</a>
							</li>
						<li>
							<a href="#" id="carroad">道路运输证</a>
							</li>
						<li>
							<a href="#" id="bottle">车用瓶使用证</a>
								</li>
						<li>
							<a href="#" id="purchase">车辆购置税政</a>
						</li>
						<li>
							<a href="#" id="carviolate">车辆违章信息</a>
						</li>
						<li>
							<a href="#" id="carquasi">车辆准载座位</a>
						</li>
					</ul>
				</li>
			</ul>
		</div>
		
			<s:token></s:token>
		 -->
		<%------------------------------------物品选择------------------------------------%>
		<form name="goodsForm" id="goodsForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<div class="jqmWindow jqmWindowcss1" style="top: 5%;"
				id="goodsjqModel">
				<div class="content1" style="width: 100%;height: 400px;">
					<div class="contentbannb">
						<div class="drag">
							选择车辆
						</div>
					</div>
					<table width="99%" height="33" id="searchgood" border="0"
						align="center" cellpadding="0" cellspacing="0"
						style="margin-top: 5px; background: #FFFFFF;">
						<tr>
							<td  align="right">
								车牌号：
							</td>
							<td >
								<input name="carNum" class="input" id="carNum" 
									style="margin-left: 2px;" size="5"/>
							</td>
							<td  align="right">
								车架号：
							</td>
							<td >
								<input name="carFrameNum" class="input" id="carFrameNum" 
									style="margin-left: 2px;" size="5"/>
							</td>
							<td align="right">
								车型：
							</td>
							<td >
								<input name="carType" class="input" id="carType" 
									style="margin-left: 2px;" size="5"/>
							</td>
							<td height="33">
								<input type="button" class="btn02" id="chaxun"
									name="button7" value="查询" />
								<input type="button" class="btn02" id="qdcar"
									name="button5" value="确定" />
								<input type="button" class="btn02 JQueryreturngoods" name="button4"
									value="关闭" />
								<input type="hidden" name="parms" id="parms" />
							</td>
							<td width="80">
								<a id="wpsy" title="0">上一页</a>
							</td>
							<td width="80">
								<a id="wpxy" title="0">下一页</a>
							</td>
							<td width="100">
								<a id="wpzy">共&nbsp;&nbsp; <span style="color: red"
									id="wpzycount"></span>&nbsp;&nbsp;页</a>
							</td>
						</tr>
					</table>
					<table width="99%" border="0" align="center" cellpadding="0"
						cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
						<tr>
							<td width="99%" valign="top" align="left">
								<div id="body_02"
									style="margin-top: 2px; display: none; height: 330px; width: 100%; overflow: scroll;">
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<s:token></s:token>
		</form>
		
       <!--搜索窗口 -->
        <div class="jqmWindow" style="width: 400px;right: 25%;" id="jqModelSearch">
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
                            员工编号：        </td>
							<td width="261">
							<input name="staffcarInformation.staffCode" />
                        </td>
              </tr>
                    <tr>
                        <td align="right">
                            员工姓名：                      </td>
                  <td>
                            <input name="staffcarInformation.staffName" />
                        </td>
                    </tr>
					<tr>
                        <td align="right">
                            岗位名称：                        </td>
                  <td>
                            <input name="staffcarInformation.postName" />
                        </td>
                    </tr>
                    <tr>
        <td align="right">部门名称: </td>            
        <td>
        <select id="deptID" name="staffcarInformation.departmentID" ></select>
        </td>
        </tr>
        </table>
        <div align="center">
        <input type="button" class="input-button" id="tosearch" value=" 查询 " /><input name="search" type="hidden" value="search" />
        </div>
       	 </form>
        </div>	
       <!----------------------------------------选择责任人---------------------------------------- -->
        <div id="bankJqm" class="jqmWindow"
			style="width: 95%; height: 400px; absolute; display: none; left: 1%; top: 1%; background: #eff; overflow-x: hidden; overflow-y: auto;">
			<div style="background: #efg; margin-right: 500px;">
				<input style="display: none;" id="checkopertionID" />
				<input style="display: none;" id="checkopertionName" />
				<input style="display: none;" id="childopertionName" />
				<input style="display: none;" id="checkform" />
			</div>
			<iframe name="daoRu" id="daoRu" width="100%" height="360px"
				frameborder="0" style="overflow-x: hidden; overflow-y: auto;"></iframe>
			<div>
				<input type="button" class="input-button" id="DaoRuFanqd"
					value=" 确定 "
					style="cursor: hand; border: 0; margin-left: 400px; height: 25px; width: 60px" />
				<input type="button" class="input-button" id="DaoRuFan" value=" 关闭 "
					style="cursor: hand; border: 0; margin-left: 40px; height: 25px; width: 60px" " />
			</div>
		</div>
		<!--JS遮罩层-->
		<div id="fullbg"></div>
		<script type="text/javascript">
			$("#j_state").change(function () {
				if($(this).val()=="02"){
				    $("#j_body").show();
				    $("#mainframe").attr("height",632+"px");
				}else {
                    $("#j_body").hide();
				}
            })
		</script>
    </body>
  
</html>