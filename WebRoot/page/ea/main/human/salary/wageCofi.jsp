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
<title>工资设定管理</title>
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
<script type="text/javascript" src="<%=basePath%>js/ea/human/salary/wageCofi.js"></script>
<script>
var basePath = '<%=basePath%>';
var pageNumber = ${pageNumber};
var wstate = '${wstate}'
var cid = '';
var select = 0 ;
var notoken = 0;
var search='${search}';
var xmtype = '${xmtype}';
</script>
</head>
<body>
<form  name="cofiForm" id="cofiForm" method="post">
	<table id="item" >
	  	<thead>
		 	    <tr>
			 	    <th width="25" align="center">选择</th>
			 	    <th width="25" align="center">序号</th>
		            <th width="50" align="center">设定时间</th>
		            <th width="100" align="center">工资构成分类</th>
		            <th width="200" align="center">项目名称</th>
		            <th width="50" align="center">规格</th>
		            <th width="50" align="center">单位</th>
		            <th width="60" align="center">级差显示</th>
		            <th width="100" align="center">参考单价</th>
		            <th width="50" align="center">参考数量</th>
		            <th width="135" align="center">系数(%)</th>
		            <th width="100" align="center">参考金额</th>
		            <th width="60" align="center">加减分项</th>
		            <th width="50" align="center">状态</th>
	      		</tr>
	    </thead>
		<tbody id="tbwid">
	    	<tr id="sa" style="display: none" class="td_bg01">
	    		<td><input type="radio" value="" name="cid" class="cid"/></td>
	    		<td class="td_bg01"><input name="wageCofSortSn" id="wageCofSortSn"/></td>
	    		<td class="td_bg01"><input name="cofDate" class="yz" id="cofDate" onfocus="WdatePicker({dateFmt:'yyyy'})"/></td>
	    		<td class="td_bg01"><s:select list="wageitemlist" listKey="key" listValue="value" name="wageState" class="model1" ></s:select></td>
	    		<td class="td_bg01"><input name="codeValue" class="yz" id="codeValue" size="40" onfocus="getCate(this)" onkeyup="getCate(this)"/></td>
	    		<td class="td_bg01"><input name=norms id="norms" size="40"/></td>
	    		<td class="td_bg01"><s:select list="codeList" class="yz" listKey="codeValue" listValue="codeValue" name="unit" id="unit" ></s:select></td>
	    		<td><select name="jicha"><option value="0">不显示</option><option value="1">显示</option></select></td>    	
	    	   <td class="td_bg01"><input name="price" class="yz" id="price" onkeyup="pnm(this)"/></td>
	    		<td class="td_bg01"><input name="nums" class="yz" id="nums" onkeyup="pnm(this)"/></td>
	    		<td class="td_bg01">
	    		<select name="wcXslb" id="wcXslb"><option value="0">固定数额</option><option value="1">百分比</option></select>
	    		<input name="wcTcxs" class="fgnsize" id="wcTcxs" readonly="readonly" size="50" onkeyup="pnm(this)"/></td>
	    		<td class="td_bg01"><input name="moneys" class="yz" id="moneys" onkeyup="pnm(this)"/>
	    		<input type="hidden" name="codeID" id="codeID"/></td>
	    		<td>
	    			<s:select list="#{'0':'加分项','1':'减分项'}"   name="addsubState"></s:select>
	    		</td>
	    		<td>
	    			&nbsp;
	    		</td>
	    	</tr>
	    <s:iterator value="pageForm.list" var="item">
	    	<tr id="${item.wageCofId }">
	    		<td>
	    			<input type="radio" value="${item.wageCofId }" name="cid" class="cid"/>
	    		</td>
	    		<td>
					<span>${item.wageCofSortSn }</span>
	    			<input class="model1 yz" name="wageCofSortSn" value="${item.wageCofSortSn }"/>
				</td>
	    		<td>
	    			<span>${fn:substring(item.wageCofDate,0,4) }</span>
	    			<input class="model1 yz" name="cofDate" onfocus="WdatePicker({dateFmt:'yyyy'})" value="${fn:substring(item.wageCofDate,0,4) }"/>
	    		</td> 
	    			<td><span id="gong"><s:if test="#item.wageState==1">基本工资设定</s:if>	
			    			<s:if test="#item.wageState==2">职务工资设定</s:if>	
			    			<s:if test="#item.wageState==3">考评工资设定</s:if>	
			    			<s:if test="#item.wageState==4">考勤工资设定</s:if>	
			    			<s:if test="#item.wageState==5">奖惩工资设定</s:if>
			    			<s:if test="#item.wageState==6">级差工资设定</s:if>
			    			<s:if test="#item.wageState==7">计件工资设定</s:if></span>
					<s:select list="wageitemlist" listKey="key" listValue="value" name="wageState" class="model1" ></s:select>
	    			</td>	    	
	    		<td>
	    			<span>${item.codeValue }</span>
	    			<input class="model1 yz" name="codeValue" value="${item.codeValue }" onfocus="getCate(this)" onkeyup="getCate(this)"/>
	    			<input type="hidden" name="codeID" id="codeID" value="${item.codeID }"/>
	    		</td>
	    		<td>
	    			<span>${item.norms }</span>
	    			<input class="model1" name="norms" value="${item.norms }"/>
	    		</td>
	    		<td>
	    			<span >${item.unit}</span>
	    			<s:select list="codeList" listKey="codeValue" listValue="codeValue" name="unit"></s:select>
	    		</td>	
	    			<td>
	    			<span>
	    				<s:if test="#item.jicha == 0">不显示</s:if>
		    			<s:if test="#item.jicha == 1">显示</s:if>
	    			</span>
				<s:select list="#{'0':'不显示','1':'显示'}"  name="jicha"></s:select>
	  			</td>

	    		<td>
	    			<span>${item.price }</span>
	    			
	    			<input class="model1 yz" name="price" id="price" onkeyup="pnm(this)" value="${item.price }"/>
	    		</td>
	    		<td>
	    			<span>${item.nums }</span>
	    			<input class="model1 yz" name="nums" id="nums" onkeyup="pnm(this)" value="${item.nums }"/>
	    		</td>
	    		<td>
	    			<span>${item.wcXslb=="1"?item.wcTcxs:"固定数额" }</span>
	    			<select name="wcXslb" id="wcXslb">
	    			<option value="0" <c:if test="${item.wcXslb==0 }">selected="selected"</c:if>>固定数额</option>
	    			<option value="1" <c:if test="${item.wcXslb==1 }">selected="selected"</c:if>>百分比</option></select>
	    		<input name="wcTcxs" <c:if test="${item.wcXslb==1 }">value="${item.wcTcxs }"</c:if> class="model1" class="fgnsize" id="wcTcxs" 
	    		<c:if test="${item.wcXslb==0 }">readonly="readonly"</c:if> size="50" onkeyup="pnm(this)" />
	    		</td>
	    		<td>
	    			<span>${item.moneys }</span>
	    			<input class="model1 yz" name="moneys" id="moneys" onkeyup="pnm(this)" value="${item.moneys }"/>
	    			<input type="hidden" name="wageCofKey" id="wageCofKey" value="${item.wageCofKey }"/>
	    			<input type="hidden" name="companyId" id="companyId" value="${item.companyId }"/>
	    			<input type="hidden" name="groupCompanySn" id="groupCompanySn" value="${item.groupCompanySn }"/>
	    			<input type="hidden" name="wcAdate" value="${item.wcAdate }"/>
	    			<input type="hidden" name="wcAname" value="${item.wcAname }"/>
	    			<input type="hidden" id="wageCofState" value="${item.wageCofState }"/>
	    			<input type="hidden" name="wageCofId" id="wageCofId" value="${item.wageCofId }"/>
	    		</td>
	    		<td>
	    			<span>
	    				<s:if test="#item.addsubState == 0">加分项</s:if>
		    			<s:if test="#item.addsubState == 1">减分项</s:if>
	    			</span>
	    			<s:select list="#{'0':'加分项','1':'减分项'}"  name="addsubState"></s:select>
	    		</td>
	    		<td>
	    			<s:if test="#item.wageCofState == 0">正常</s:if>
		    		<s:if test="#item.wageCofState == 1">停用</s:if>
	    		</td>
	    	</tr>
	    </s:iterator>
	    </tbody>
  </table>
  </form>
  <c:import url="../../../page_navigator.jsp">
	<c:param name="actionPath"
		value="ea/cofi/ea_findItem.jspa?pageNumber=${pageNumber}&search=${search}&wstate=${wstate }">
	</c:param>
</c:import>
<%------------------------------------项目分类框------------------------------------%>
<div id="jqModel" style="display:none;">
<div style="width:100%;font-weight:bold;margin-bottom:5px;">&nbsp;<a>主项目目录</a><span style="margin-left:100px;cursor:pointer;" id="closeml"><a>关闭</a></span></div>
	<div id="treeBoxs" ></div>
</div>	
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
