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
		<title>物品管理</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"
			type="text/css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/ea/ccodes_add.js"></script>
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
		<script src="<%=basePath%>js/ea/validate.js"  type="text/javascript"></script>
		<script type="text/javascript"> 
		$(document).ready(function() {
		 $('.JQueryflexme').flexigrid({
        height: 145,
        width: 'auto',
        minwidth: 30,
        title: '选择物品',
        minheight: 80
      });
	  $(".JQueryflexme").find("select").each(function(){
                    $s = $(this).hide()
                    $o = $("<span/>").text($s.find("option:selected").text());
                    $o.insertAfter($s)
                }); 
      $(".JQueryflexme tr[id]").click(function(){
                    $("input.JQuerypersonvalue", $(this)).attr("checked", "checked");
                    var goodsID =this.id;
					 $("#"+goodsID).find("span[id]").each(function(i,tmp){
					     parent.checksgoodsManage(this.id,$(this).text());
					 })
                })
      }) 
        </script>
	</head>
	<body>
		<div class="main_main">
			<table class="JQueryflexme">
				<thead>
					<tr class="tablewith">
						<th width="40">
							请选择
						</th>
						<th width="60">
							品名编号
						</th>
						<th width="60">
							品名名称
						</th>
						<th width="80">
							品牌
						</th>
						<th width="120">
							类型
						</th>
						<th width="80">
							单位
						</th>
						<th width="80">
							默认规格
						</th>
						<th width="60">
							品牌规格
						</th>
						<th width="60">
							型号
						</th>
						<th width="80">
							缺省仓库
						</th>
						<th width="70">
							厂家
						</th>
					</tr>
				</thead>
				<tbody>
					<%
						int number = 1;
					%>
					<s:iterator value="pageForm.list">
						<tr id="${goodsID}">
							<td>
								<input type="radio" name="a" class="JQuerypersonvalue"
									value="${goodsID}" />
							</td>
							<td>
								<span id="goodsCoding">${goodsCoding}</span>
							</td>
							<td>
								<span id="goodsName">${goodsName}</span>
							</td>

							<td>
								<span id="mnemonicCode">${mnemonicCode}</span>
							</td>
							<td>
								<span></span>
								<s:select list="%{#request.typelist}" id="typeID"
									listKey="codeValue" listValue="codeValue" name="typeID"
									theme="simple"></s:select>
							</td>
							<td>
								<span></span>
								<s:select list="%{#request.variablelist}" id="typeID"
									listKey="codeValue" listValue="codeValue" name="variableID"
									theme="simple"></s:select>
							</td>
							<td>
								<span id="acquiesceStandard">${acquiesceStandard}</span>
							</td>
							<td>
								<span id="standard">${standard}</span>
							</td>
							<td>
								<span id="model">${model}</span>
							</td>
							<td>
								<span id="defaultStorage">${defaultStorage}</span>
							</td>
							<td>
								<span id="manufacturers">${manufacturers}</span>
								<span id="typeID" style="display: none">${typeID}</span>
								<span id="variableID" style="display: none">${variableID}</span>
								<span id="goodsID" style="display: none">${goodsID}</span>
								<span id="goodsKey" style="display: none">${goodsKey}</span>
								<span id="photoPath" style="display: none">${photoPath}</span>
							</td>
						</tr>
						<%
							number++;
						%>
					</s:iterator>
				</tbody>
			</table>
			<c:import url="../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/goodsmanage/ea_getListGoodsManage.jspa?pageNumber5&parameter=${parameter}}">
				</c:param>
			</c:import>
		</div>
	</body>
</html>