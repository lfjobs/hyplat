<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>微信招聘管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<link href="<%=basePath%>/css/ea/validate.css" rel="stylesheet" type="text/css" />
<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath %>js/163css/css/163css.css"/>
<script type="text/javascript" src="<%=basePath %>js/163css/js/163css.js"></script>
<script type="text/javascript" src="<%=basePath%>js/ea/human/wx/wxrecruit.js"></script>
<script>
var basePath = '<%=basePath%>';
var pageNumber = ${pageNumber};
var cid = '';
var notoken = 0;
var search='${search}';
var select = 0;
</script>
</head>
<body>
<form  name="recruitForm" id="recruitForm" method="post">
	<table id="item" >
	  	<thead>
		 	    <tr>
			 	    <th width="25" align="center">选择</th>
			 	    <th width="100" align="center">职位名称</th>
		            <th width="200" align="center">职位性质</th>
		            <th width="100" align="center">职位类型</th>
		            <th width="100" align="center">人数</th>
		            <th width="100" align="center">学历</th>
		            <th width="100" align="center">薪水</th>
		            <th width="100" align="center">工作经验</th>
		            <th width="100" align="center">工作地址</th>
		            <th width="100" align="center">发布时间</th>
		            <th width="100" align="center">截至时间</th>
		            <th width="100" align="center">状态</th>
	      		</tr>
	    </thead>
		<tbody id="tbwid">
	    <s:iterator value="pageForm.list" var="item">
	    <tr id="${item.wxRecID }">
	    		<td>
	    			<input type="radio" value="${item.wxRecID }" name="cid" class="cid"/>
	    			<input type="hidden" id="recState" value="${item.recState }"/>
	    		</td>
	    		<td>
					<span>${item.recName }</span>
				</td>
				<td>
					<span>${item.recNature } ${item.recNaturei } ${item.recNatureii }</span>
				</td>
				<td>
					<span>${item.recInd }</span>
				</td>
				<td>
					<span>${item.recNum }</span>
				</td>
				<td>
					<span>${item.recEdu }</span>
				</td>
				<td>
					<span>${item.recPay }</span>
				</td>
				<td>
					<span>${item.recExp }</span>
				</td>
				<td>
					<span>${item.recAdd }</span>
				</td>
	    		<td>
	    			<span>${fn:substring(item.recIssue,0,10) }</span>
	    		</td>
	    		<td>
	    			<span>${fn:substring(item.recUp,0,10) }</span>
	    		</td>
	    		<td>
	    			<s:if test="#item.recState == 0 ">正常</s:if>
		    		<s:if test="#item.recState == 1">停用</s:if>
	    		</td>
	    	</tr>
	    </s:iterator>
	    </tbody>
  </table>
  </form>
  <c:import url="../../../page_navigator.jsp">
	<c:param name="actionPath"
		value="ea/wxrecruit/ea_findItem.jspa?pageNumber=${pageNumber}&search=${search }">
	</c:param>
</c:import>
<%------------------------------------项目分类框------------------------------------%>
<div id="jqModel" style="display:none;">
<div style="width:100%;font-weight:bold;margin-bottom:5px;">&nbsp;<a>主项目目录</a><span style="margin-left:100px;cursor:pointer;" id="closeml"><a>关闭</a></span></div>
	<div id="treeBoxs" ></div>
</div>	
<form name="addform" id="addform" method="post">
	<div class="jqmWindow" style="width: 365px; top: 15%;left: 15%; " id="jqmadd">
		<div id="metitle" class="drag">
			分类信息
			<div class="close"></div>
		</div>
		<table id="addTab">
			<tr>
				<td align="right" width="100">
					序号：
				</td>
				<td>
					<input type="text" class="put3" name="wcofra.cofSortSnT" id="cofSortSn" maxlength="10" size="25" onkeyup='this.value=this.value.replace(/[^\d]/g,"")'/>
				</td>
			</tr>
			<tr>
				<td align="right" width="100">
					设定时间：
				</td>
				<td>
					<input type="text" class="put3" name="wcofra.yyyyCofDate" id="cofRaDate" maxlength="10" size="25"/>
				</td>
			</tr>
			<tr>
				<td align="right" width="100">
					级差编号：
				</td>
				<td>
					<input type="text" class="put3" name="wcofra.raNum" id="raNum" maxlength="25" size="25"/>
				</td>
			</tr>
			<tr>
				<td align="right" width="100">
					级差名称：
				</td>
				<td>
					<input type="text" class="put3" name="wcofra.crName" id="crName" maxlength="10" size="25"/>
				</td>
			</tr>
			
			<s:iterator value="confi" var="itd">
			<tr>
				<td align="right" width="100">
					${itd.codeValue }
				</td>
				<td>
					<input type="hidden" name="cofClKey" id="cofClKey" class="com" value=""/>
					<input type="hidden" name="cofClID" id="cofClID" class="com" value=""/>
					<input type="hidden" name="cofRaID" id="cofRaID" class="com" value=""/>
					<input type="hidden" name="typePID" id="typePID" class="com" value="${itd.wageCofId }"/>
					<input type="text" class="put3 comt com" name="typeMoneyT" id="typeMoney" onfocus="pnm(this)" onkeyup="pnm(this)" onchange="pnm(this)" maxlength="10" size="25" />
				</td>
			</tr>
			</s:iterator>
			
			<s:iterator value="confii" var="iitd">
			<tr>
				<td align="right" width="100">
					${iitd.codeValue }
				</td>
				<td>
					<input type="hidden" name="cofClKey" id="cofClKey" class="com" value=""/>
					<input type="hidden" name="cofClID" id="cofClID" class="com" value=""/>
					<input type="hidden" name="cofRaID" id="cofRaID" class="com" value=""/>
					<input type="hidden" name="typePID" id="typePID" class="com" value="${iitd.wageCofId }"/>
					<input type="text" class="put3 comt com" name="typeMoneyT" id="typeMoney" onfocus="pnm(this)" onkeyup="pnm(this)" onchange="pnm(this)" maxlength="10" size="25"/>
				</td>
			</tr>
			</s:iterator>
			<tr>
				<td align="right" width="100">
					总计：
				</td>
				<td>
					<input type="text" name="wcofra.sumMoneyT" id="sumMoney" maxlength="25" size="25"/>
					<input type="hidden" name="wcofra.cofRaKey" id="cofRaKey"/>
					<input type="hidden" name="wcofra.cofRaID" id="cofRaID"/>
					<input type="hidden" name="wcofra.groupCompanySn" id="groupCompanySn"/>
					<input type="hidden" name="wcofra.companyID" id="companyID"/>
					<input type="hidden" name="wcofra.cjAname" id="cjAname"/>
				</td>
			</tr>
		</table>
		<div align="center">
			<input type="button" class="input-button" onclick="saveM()" value="&nbsp;保存 &nbsp;"/>
			<input type="button" class="input-button" onclick="closeM()" value="&nbsp;取消&nbsp;" />
		</div>
	</div>
</form>



<script type="text/javascript">
   $(function(){
       setTimeout(function(){ 
		   var _height = $(window).height();		
		       $(".bDiv").css({"height": _height - 150 + "px"});
		},100);
    
	    $(window).resize(function(){ 
		setTimeout(function(){ 
		    var _height = $(window).height();		
		        $(".bDiv").css({"height": _height - 150 + "px"});
		},100);
	    }); 
   });
</script> 	
</body>
</html>
