<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="hy.ea.bo.CAccount"%>
<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
			CAccount account=(CAccount)session.getAttribute("account");
			String quanxian=account.getCompanyID();
			request.setAttribute("quanxian", quanxian);
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>往来单位管理</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/admin_main111.css" />
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		
		<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
			type="text/css" />
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
		<script type="text/javascript" src="<%=basePath%>js/ea/ccodes_add.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/ea/office_ea/contactcompany/contactcompany.js"></script>
		<script type="text/javascript">
		 var ccompanyID = '';
		 var companyName = '';
		 var basePath='<%=basePath%>';           
		 var pNumber =${pageNumber};  
		 var search='${search}';
		 var token = 0 ;  
		 var personurl='';
		 var notoken = 0;
		 var retoken=0;
		 var select=1;
		 var opaNum = 0; //单位往来关系传值number 
		 var flexbutton = '<%=request.getParameter("flexbutton")%>';
		 var staffID = '<%=request.getParameter("staffID")%>';
		 var hei = 0;
		 var contactcID = "";// 已经添加到往来单位的ID
		 var showType='<%=request.getParameter("showType")%>';
	     var parameter = "${parameter}";
	     var flag="${flag}";//往来单位网站是否启用标志ljc
	     var gtype="${gtype}";
	     var quanxian='${quanxian}';
	     var webstatus='';

	     function a (){
	         $.ajax({
	             url:basePath+"ea/contactcompany/sajax_ea_syncComapny.jspa",
                 type : "get",
                 dataType : "json",
                 success:function suc(data){
                     $("#sync-a").hide();
                     alert("成功");

                 }

             });

         }
         $.ajax({
             url:basePath+"ea/contactcompany/sajax_ea_panduanSync.jspa",
             type : "get",
             dataType : "json",
             success:function suc(data){
                 if(data=="1")
                 {
                     $("#sync-a").hide();
                 }
             }
         });
</script>
	</head>
	<body>
		<div class="main_main">
			<table class="JQueryflexme">
				<thead>
					<tr class="tablewith">
						<th width="30" align="center">
							选择
						</th>
						<th width="30" align="center">
							序号
						</th>
						<th width="200" align="center">
							单位名称
						</th>
						<th width="200" align="center">
							单位地址
						</th>
						<th width="100" align="center">
							单位电话
						</th>
						<th width="100" align="center">
							单位负责人
						</th>
						<th width="100" align="center">
							单位负责人电话
						</th>
						<th width="100" align="center">
							行业类别
						</th>
						<th width="100" align="center">
							单位备注
						</th>
						<th width="100" align="center">
							单位状态
						</th>
						<th width="100" align="center">
							邀请人
						</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<%
						int number = 1;
					%>
					<s:iterator value="pageForm.list">
						<tr id="${ccompanyID}" class="trclass">
							<td>
								<input type="radio" name="a" class="JQuerypersonvalue"
									value="${ccompanyID}" />
							</td>
							<td>
								<span><%=number%></span>
							</td>
							<td>
								<span id="companyName">${companyName}</span>
							</td>

							<td>
								<span id="companyAddr">${companyAddr}</span>
							</td>
							<td>
								<span id="companyTel">${companyTel}</span>
							</td>
							<td>
								<span id="cresponsible">${cresponsible}</span>
							</td>
							<td>
								<span id="responsibleTel">${responsibleTel}</span>
							</td>
							<td>
								<span id="industryType">${industryType}</span>
							</td>
							<td>
								<span id="remark">${remark}</span>
								<span id="address" style="display: none">${address}</span>
								<span id="ccompanyID" style="display: none">${ccompanyID}</span>
								<span id="ccompanyKey" style="display: none">${ccompanyKey}</span>
								<span id="webstatus" style="display: none">${webstatus}</span>
								<span id="dealIn" style="display: none">${dealIn}</span>
							</td>
							<td>
								<span id="custStatus"><c:if test="${custStatus == '01'}">未注册单位</c:if>
								                      <c:if test="${custStatus == '02'}">已注册单位</c:if></span>
							</td>
							
							<td>
								<span id="accountName">${accountName}&nbsp;${accountID}</span>
							</td>
							<td><a id="sync-a" href="javascript:a();">sync</a></td>
						</tr>
						<%
							number++;
						%>
					</s:iterator>
				</tbody>
			</table>
			<c:import url="../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/contactcompany/ea_getListContactCompany.jspa?search=${search}&pageNumber=${pageNumber}&flexbutton=${param.flexbutton}&staffID=${param.staffID}&showType=${showType}&parameter=${parameter}&flag=${flag}&gtype=${gtype}">
				</c:param>
			</c:import>			
		</div>
		<iframe src="" name="main" marginwidth="0" style="height:0;width:100%;" scrolling="no" marginheight="0" frameborder="0" id="mainframe" border="0" framespacing="0" noresize="noResize" ></iframe>
		<!--搜索窗口 -->
		<div class="jqmWindow" style="width: 400px; right: 40%;; top: 10%"
			id="jqModelSearch">
			<form name="SearchForm1" id="SearchForm1" method="post">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					请选择行业类别
					<div class="close">
					</div>
				</div>
				<table width="396" id="cataffSearchTable">										
					
					<tr>
						<td width="123" align="right">
							行业类别：
						</td>
						<td width="261">
							<s:select list="#request.typelist" id="industryType"
								listKey="codeValue" listValue="codeValue"
								name="contactCompany.industryType" theme="simple" headerKey="" 
								headerValue="全部"></s:select>
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="leibie"  onclick="leibi()" value="确定"/>
				</div>
			</form>
		</div>
		<div class="jqmWindow" style="width: 400px; right: 25%; top: 10%"
			id="newccode">
			<%--行业类别--%>
			<div class="drag">
				添加
			</div>
			<table>
				<tr>
					<td>
						代码名字：
					</td>
					<td>
						<input id="ccodevalue" />
						<input id="codePID" type="hidden" />
						<input id="selectID" type="hidden" />
						<input id="formID" type="hidden" />
					</td>
				</tr>
			</table>
			<div align="center">
				<input type="button" class="input-button" onclick="saveCCode()"
					value="确定" />
				<input type="button" class="input-button JQueryreturn1" value="取消" />
			</div>
		</div>
		
		<!-- 添加到单位往来关系 -->
		<div class="jqmWindow jqmWindowcss2" style="width: 600px; top: 10%;"
			id="selectcode">
			<div class="drag">
				添加单位往来关系
			</div>
			<table>
				<tr>
					<td>
						单位往来关系：
					</td>
					<td align="left" class="code">
						<select id="province" number='0' style="width: 110px;"></select>
						<!-- <option>选择省</option>-->
						<select id="city" number='1' style="width: 110px; display: none;"></select>
						<select id="county" number='2'
							style="width: 110px; display: none;"></select>
						<select id="addressTown" number='3'
							style="width: 110px; display: none;"></select>
						<select id="addressVillage" number='4'
							style="width: 110px; display: none;"></select>
						<select id="addressCommunity" number='5'
							style="width: 110px; display: none;"></select>
						<!-- <option>选择省</option>-->
						<select id="addressFloor" number='6'
							style="width: 110px; display: none;"></select>
						<select id="addressLayer" number='7'
							style="width: 110px; display: none;"></select>
						<select id="addressSize" number='8'
							style="width: 110px; display: none;"></select>
					</td>
				</tr>
			</table>
			<div align="center">
				<input type="button" class="input-button" id="savecode" value="确定" />
				<input type="button" class="input-button JQueryreturns" value="取消" />
			</div>
		</div>
	
		<!-- 添加单位往来关系 -->
		<form name="companyCodeForm" id="companyCodeForm" method="post">
			<div class="jqmWindow jqmWindowcss3" style="top: 10%" id="jqModelkf">
				<input type="submit" name="submit" style="display:none"/>
				<div class="content1">
					<div class="contentbannb">
						<div class="drag">
							添加单位往来关系
							<div class="close"></div>
						</div>
					</div>
					<table width="485" border="0" id="stafftable" align="center"
						cellpadding="0" cellspacing="0"
						style="margin-top: 5px; margin-bottom: 5px;">
						<tr>
					        <td width="20%" height="44" align="right" class="td_bg">序号：</td>
						    <td width="80%" class="td_bg"><input readonly="readonly" class="input01" type="text" size="20" id="codeNumber"/>
					    <tr>
					        <td height="45" align="right" class="td_bg"><font color="#ff0000">*</font>代码名称：</td>
					        <td height="45" class="td_bg iscode"><input maxlength="50"  type="text" class="input01  put3 ckTextLength" id="codeValue" size="20"/></td>
					    </tr>
					    <tr>
					        <td height="45" align="right" class="td_bg">代码描述：</td>
					        <td height="45" class="td_bg"><textarea style="height: 80px;" maxlength="250"  class="input01 ckTextLength"  id="desc"></textarea>
					    </tr>
						<tr>
							<td colspan="2" align="center">
								<input type="hidden" id="codePID"/>
								<input type="hidden" id="parmiter"/>
								<input id="treenum" type="hidden" class="input" />
								<input type="button" class="input-button JQuerySubmitkf"
									style="cursor: pointer; width: 80px;" value="提交" />
								<input type="button" class="input-button JQueryreturnkf"
									style="cursor: pointer; width: 80px;" value="取消" />
							</td>
						</tr>
					</table>
				</div>
			</div>
			<s:token></s:token>
		</form>
		<form action="" name='myform' id='myform' method="post">
		<input type="submit" name="submit" style="display: none" />
			<table id='ftable'>
				<tr id="kelong" style="display: none">
					<td align="center" bgcolor="#FFFFFF">
						<input type="text" name="staffID" id="staffID" />
						<input type="text" name="staffName" id="staffName" />
					</td>
				</tr>
			</table>
		</form>
		
		<%------------------------------------选择社会往来单位------------------------------------%>
		<form name="selectcompanyForm" id="selectcompanyForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<div class="jqmWindow jqmWindowcss1" style="top: 0%;left:55%"
				id="companyjqModel">
				<div class="content1" style="width: 91%; height: 330px;">
					<div class="contentbannb">
						<div class="drag">
							选择社会往来单位
						</div>
					</div>
					<table width="99%" height="33" id="searchcompany" border="0"
						align="center" cellpadding="0" cellspacing="0"
						style="margin-top: 5px; background: #FFFFFF;">
						<tr>
							<td width="8%" align="right">
								单位名称：
							</td>
							<td width="25%">
								<input name="ccompanyID" class="input" id="ccompanyID" 
									style="margin-left: 2px;" />
							</td>
							<td width="45%" height="33">
								<input type="button" class="btn02" id="searchcc" name="button7"
									value="查询" />
								<input type="button" class="btn02" id="qdcompany" name="button5"
									value="确定" />
								<input type="button" class="btn02 xzdw" name="button" value="新增" />
								<input type="button" class="btn02 JQueryreturns1" name="button4"
									value="关闭" />
							</td>
							<td width="6%">
								<a id="dwsy" title="0">上一页</a>
							</td>
							<td width="6%">
								<a id="dwxy" title="0">下一页</a>
							</td>
							<td>
								<a id="dwzy">共&nbsp;&nbsp; <span style="color: red"
									id="zycount"></span>&nbsp;&nbsp; 页</a>
							</td>
						</tr>
					</table>
					<table width="99%" border="0" align="center" cellpadding="0"
						cellspacing="0"
						style="margin-top: 5px; margin-bottom: 5px; height: 450px;">
						<tr>
							<td width="99%" valign="top" align="left">
								<div id="body_02cc"
									style="margin-top: 2px; display: none; width: 100%; overflow:scroll; height: 250px;">
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<s:token></s:token>
		</form>
		
		<!--  -->
		<div class="jqmWindow" style="width: 500px; right: 40%;; top: 10%"  id="JQueryaddress">
			<form name="SearchForm1" id="SearchForm1" method="post">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					请选择地区
					<div class="close">
					</div>
				</div>
				<table width="500" id="cataffSearchTable">										
						<tr class="trheight">						
						<td colspan="4" class="JQueryaddress">
							<select name="addressProvince" id="province" number='0'
								style="width: 110px;">
							</select>
							<select name="addressCity" id="city" number='1'
								style="width: 110px;">
							</select>
							<select name="addressCounty" id="county" number='2'
								style="width: 110px;">
							</select>
							<select name="addressTown" id="addressTown" number='3'
								style="width: 110px;">
							</select>
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="tosearch1" onclick="getAddress()"
						value="确定" />
					<input name="search" type="hidden" value="search" />
					
				</div>
			</form>
		</div>
		<!--  -->
		
		
		
		<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>		
	<script type="text/javascript">
<!--
 function testit(){ 	
var dianhua=$("#cstaffForm").find("input#companyTel").attr("value");
var filter= (/(^(\d{3,4}-)?\d{7,8}(-\d{1,3})?$)/.test(dianhua) )|| (/^1[3|4|5|8][0-9]\d{8}$/.test(dianhua) ) ; 
if(("0000000"==(dianhua))||("00000000"==(dianhua))||(filter==false)){
alert("请输入正确的电话号码");
}
 }
 function testits(){ 	
var shouji=$("#cstaffForm").find("input#responsibleTel").attr("value");
var filters= (/(^(\d{4}-)?\d{7,8}(-\d{1,3})?$)/.test(shouji) )|| (/^1[3|4|5|8][0-9]\d{8}$/.test(shouji) ) ; 
if(("0000000"==(shouji))||("00000000"==(shouji))||(filters==false)){
alert("请输入正确的电话号码");
}
 }
//-->
</script>

<script type="text/javascript">

   $(function(){
	   if(flexbutton == 'flexbutton'){
		   setTimeout(function(){
		        $("div.bDiv").css({"height":parent.document.getElementById("mainframe12").offsetHeight-81+"px"});
			},100);
			 $(window).resize(function(){
				 setTimeout(function(){
				        $("div.bDiv").css({"height":parent.document.getElementById("mainframe12").offsetHeight-81+"px"});
			 },100);
			 }); 	
	   }else{
		   setTimeout(function(){ 
			   var _height = $(window).height();		
			   if($("#mainframe").height() > 0){
			       $(".bDiv").css({"height": _height /2 - 30 - 26 - 80 + "px"});
			       $("#mainframe").css({"height": _height / 2 - 20 + "px"});
			   }else{		    
			       $(".bDiv").css({"height": _height - 31 - 30 - 26 - 70 + "px"});
			       $("#mainframe").css({"height": 0 + "px"});
			   }
			},100);
	    
		    $(window).resize(function(){ 
			setTimeout(function(){ 
			    var _height = $(window).height();		
			    if($("#mainframe").height() > 0){
			        $(".bDiv").css({"height": _height /2 - 30 - 26 - 80 + "px"});
				$("#mainframe").css({"height": _height / 2 - 20 + "px"});
			    }else{		    
				$(".bDiv").css({"height": _height - 31 - 30 - 26 - 70 + "px"});
				$("#mainframe").css({"height": 0 + "px"});
			    }
			},100);
		    }); 
	   }
   });
  function getAddress(){
		
		if($("#province ").find("option:selected").text()=="--请选择--"){
			$("#province ").text("");
			}
		 var province=$("#province ").find("option:selected").text();
		 if($("#city ").find("option:selected").text()=="--请选择--"){
			 $("#city ").text("");
			 }
		 var city=$("#city ").find("option:selected").text();
		 if($("#county ").find("option:selected").text()=="--请选择--"){
			 $("#county ").text("");
			 }
		 var county=$("#county ").find("option:selected").text();
		 if($("#addressTown ").find("option:selected").text()=="--请选择--"){
			 $("#addressTown ").text("");
			 }
		 var addressTown=$("#addressTown ").find("option:selected").text();
		 if(addressTown!=""){
			 address=addressTown;
		 }else if(county!="" && addressTown==""){
			 address=county;
	     }else if(city!="" && county=="" && addressTown==""){
			 address=city;
		 }else{
			 address=province;
			 }
		$("#JQueryaddress").jqmHide();
		$("#companyAddr").val(address);
	  }
  function leibi(){
	  var industryType=$("#industryType ").find("option:selected").text();
	  $("#jqModelSearch").jqmHide();
	  $("#type").val(industryType);
	  
	  }
</script>  
	</body>
</html>
