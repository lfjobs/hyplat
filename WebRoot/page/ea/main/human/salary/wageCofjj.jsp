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
<title>计件工资设定管理</title>
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

		<script src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js"></script>
		<script src="<%=basePath%>js/tree/codebase/dhtmlxtree.js"></script>
		<link rel="STYLESHEET" type="text/css"
			href="<%=basePath%>js/tree/codebase/dhtmlxtree.css" />
       <link rel="stylesheet"
			href="<%=basePath%>js/tree/common/css/style.css" type="text/css"
			media="screen" />
		
		<link rel="stylesheet"
			href="<%=basePath%>/js/jqueryplus/treeview/jquery.treeview.css" />
		
		<script src="<%=basePath%>/js/jqueryplus/treeview/jquery.treeview.js"
			type="text/javascript"></script>
			
<link rel="stylesheet" type="text/css" href="<%=basePath %>js/163css/css/163css.css"/>
<script type="text/javascript" src="<%=basePath %>js/163css/js/163css.js"></script>
<script type="text/javascript" src="<%=basePath%>js/ea/human/salary/wageCofjj.js"></script>
<script>
var basePath = '<%=basePath%>';
var pageNumber = ${pageNumber};
var cid = '';
var wpid = '';
var select = 0 ;
var notoken = 0;
var search='${search}';
var xmtype = '${xmtype}';
</script>
</head>
<body>
<form  name="cofjjForm" id="cofjjForm" method="post">
	<table id="item" >
	  	<thead>
		 	    <tr>
			 	    <th width="25" align="center">选择</th>
			 	    <th width="25" align="center">序号</th>
		            <th width="100" align="center">设定时间</th>
		            <th width="200" align="center">产品名称</th>
		            <th width="100" align="center">产品价格</th>
		            <th width="100" align="center">职务</th>
		            <th width="100" align="center">阶段</th>
		            <th width="100" align="center">单位</th>
		            <th width="100" align="center">提成类别</th>
		            <th width="100" align="center">提成系数</th>
		            <th width="100" align="center">状态</th>
	      		</tr>
	    </thead>
		<tbody id="tbwid">
	    	<tr id="sa" style="display: none" class="td_bg01">
	    		<td><input type="radio" value="" name="cid" class="cid"/>
	    			<input type="hidden" id="confJjState" value=""/></td>
	    		<td class="td_bg01"><input name="cofSortSnT" id="cofSortSn"/></td>
	    		<td class="td_bg01"><input name="yyyyCofDate" id="yyyyCofDate" onfocus="WdatePicker({dateFmt:'yyyy'})"/></td>
	    		<td class="td_bg01"><input name="goodsName" id="goodsName" size="40" onfocus="getCate(this)" onkeyup="getCate(this)"/></td>
	    		<td class="td_bg01"><input name="cjProPriceT" id="cjProPriceT" readonly="readonly"/></td>
	    		<td class="td_bg01"><s:select list="dpostList" listKey="depPostID" listValue="postName" name="deppostID" id="deppostID" headerKey="" headerValue="请选择" onchange="javascript:postonch(this)" style="width: 100%;height: 100%;"></s:select></td>
	    		<td class="td_bg01">
	    			<input name="jjCodeName" id="jjCodeName" class="shuju"/>
	    		</td>
	    		<td class="td_bg01"><s:select list="codeList" listKey="codeValue" listValue="codeValue" name="unit" id="unit" headerKey="" headerValue="请选择"></s:select></td>
	    		<td class="td_bg01"><s:select list="#{'0':'固定数额(元)','1':'固定比率(%)'}" name="cjStateT" id="cjStateT" headerKey="" headerValue="请选择"></s:select></td>
	    		<td class="td_bg01">
	    			<input name="cjTcxsT" id="cjTcxsT"/>
	    			<input type="hidden" name="postName" id="postName"/>
	    			<input type="hidden" name="jjCodeID" id="jjCodeID"/>
	    		</td>
	    		<td>
	    			&nbsp;
	    		</td>
	    	</tr>
	    <s:iterator value="pageForm.list" var="item">
	    	<tr id="${item.cofJjID }">
	    		<td>
	    			<input type="radio" value="${item.cofJjID }" name="cid" class="cid"/>
		    		<input type="hidden" id="confJjState" value="${item.confJjState }"/>
	    			<input type="hidden" name=cofJjID id="cofJjID" value="${item.cofJjID }"/>
	    		</td>
	    		<td>
					<span>${item.cofSortSn }</span>
	    			<input class="model1 put3" name="cofSortSnT" value="${item.cofSortSn }"/>
				</td> 
	    		<td>
	    			<span>${fn:substring(item.cofJjDate,0,4) }</span>
	    			<input class="model1 put3" name="yyyyCofDate" onfocus="WdatePicker({dateFmt:'yyyy'})" value="${fn:substring(item.cofJjDate,0,4) }"/>
	    		</td> 
	    		<td>
	    			<span>${item.goodsName }</span>
	    			<input class="model1 put3" name="goodsName" id="goodsName" size="40" onfocus="getCate(this)" onkeyup="getCate(this)" value="${item.goodsName }"/>
	    		</td>
	    		<td>
	    			<span>${item.cjProPrice }</span>
	    			<input class="model1 put3" name="cjProPriceT" id="cjProPriceT" readonly="readonly" value="${item.cjProPrice }"/>
	    		</td>
	    		<td>
	    			<span>${item.postName }</span>
	    			<s:select list="dpostList" listKey="depPostID" listValue="postName" name="deppostID" id="deppostID" headerKey="" headerValue="请选择" onchange="javascript:postonch(this)" style="display:none; width: 100%;height: 100%;"></s:select>
	    		</td>
	    		<td>
	    			<span>${item.jjCodeName }</span>
	    			<input class="model1 put3 shuju"  name="jjCodeName" id="jjCodeName" value="${item.jjCodeName }"/>
	    		</td>
	    		<td>
	    			<span>${item.unit }</span>
	    			<s:select list="codeList" listKey="codeValue" listValue="codeValue" name="unit" id="unit" headerKey="" headerValue="请选择" ></s:select>
	    		</td>
	    		<td>
	    			<span>
		    			<s:if test="#item.cjState == 0">固定数额(元)</s:if>
		    			<s:if test="#item.cjState == 1">固定比率(%)</s:if>
		    		</span>
	    			<s:select list="#{'0':'固定数额(元)','1':'固定比率(%)'}" name="cjStateT" id="cjState" headerKey="" headerValue="请选择" ></s:select>
	    		</td>
	    		<td>
	    			<span>${item.cjTcxs }</span>
	    			<input class="model1 put3" name="cjTcxsT" id="cjTcxsT" onkeyup="pnm(this)" value="${item.cjTcxs }"/>
	    			
	    			<input type="hidden" name="cofJjKey" id="cofJjKey" value="${item.cofJjKey }"/>
	    			<input type="hidden" name="companyID" id="companyID" value="${item.companyID }"/>
	    			<input type="hidden" name="groupCompanySn" id="groupCompanySn" value="${item.groupCompanySn }"/>
	    			<input type="hidden" name="cjAdate" id="cjAdate" value="${item.cjAdate }"/>
	    			<input type="hidden" name="cjAname" id="cjAname" value="${item.cjAname }"/>
	    			<input type="hidden" name="postName" id="postName" value="${item.postName }"/>
	    			<input type="hidden" name="jjCodeID" id="jjCodeID" value="${item.jjCodeID }"/>
	    		</td>
	    		<td>
	    			<s:if test="#item.confJjState == 0">正常</s:if>
		    		<s:if test="#item.confJjState == 1">停用</s:if>
	    		</td>
	    	</tr>
	    </s:iterator>
	    </tbody>
  </table>
  </form>
  <c:import url="../../../page_navigator.jsp">
	<c:param name="actionPath"
		value="ea/cofjj/ea_findItem.jspa?pageNumber=${pageNumber}&search=${search }">
	</c:param>
</c:import>
<%------------------------------------项目分类框------------------------------------%>
<div id="jqModel" style="display:none;">
<div style="width:100%;font-weight:bold;margin-bottom:5px;">&nbsp;<a>主项目目录</a><span style="margin-left:100px;cursor:pointer;" id="closeml"><a>关闭</a></span></div>
	<div id="treeBoxs" ></div>
</div>	
<%--******************************************物品选择****************************************--%>
	<form name="goodsForm" id="goodsForm" method="post"
		enctype="multipart/form-data">
		<input type="submit" name="submit" style="display: none" />
		<div class="jqmWindow jqmWindowcss1" style="top: 5%; left: 53%;"
			id="goodsjqModel">
			<div class="content1" style="width: 100%; height: 400px;">
				<div class="contentbannb">
					<div class="drag">
						选择物品
					</div>
				</div>
				<table width="99%" height="33" id="searchgood" border="0"
					align="center" cellpadding="0" cellspacing="0"
					style="margin-top: 5px; background: #FFFFFF;">
					<tr>
						<td width="100" align="right">
							物品编码或名称：
						</td>
						<td width="142">
							<input name="typeID" class="input" id="typeID" size="20"
								style="margin-left: 2px;" />
						</td>
						<td height="33">
							<input type="button" class="btn02" ID="searchGood"
								name="button7" value="查询" />
							<input type="button" class="btn02" id="selectGood"
								name="button5" value="确定" />
							<input type="button" class="btn02 xzwp" name="button" value="新增" />
							<input type="button" class="btn02 JQueryreturns" name="button4"
								value="关闭" />
							<input type="hidden" name="parms" id="parms" />
							<input type="hidden" id="clicktr" />
						</td>
						<td width="80">
							<a id="wpsy" title="0">上一页</a>
						</td>
						<td width="80">
							<a id="wpxy" title="0">下一页</a>
						</td>
						<td width="100">
							<a id="wpzy">共&nbsp;&nbsp; <span style="color: red"
								id="wpzycount"></span>&nbsp;&nbsp;页 </a>
						</td>
					</tr>
				</table>
				<table width="99%" border="0" align="center" cellpadding="0"
					cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
					<tr>
						<td width="16%">
							<table width="100%" cellpadding="0" cellspacing="0">
								<tr id="menuTreeTrid-1" sizcache="1" sizset="0">
									<td>
										<div id="aadTree" class="text_tree"
											style="overflow: scroll; z-index: 99; height: 320px;"></div>
									</td>
								</tr>
							</table>
						</td>
						<td width="83%" valign="top" align="left">
							<div id="body_02"
								style="margin-top: 2px; display: none; height: 310px; width: 100%; overflow: auto;">
							</div>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<s:token></s:token>
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
