<!DOCTYPE HTML>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport"
		  content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
	<title>产品设计<c:if test="${productPackaging.ppID!=null}">修改</c:if><c:if test="${productPackaging.ppID==null}">添加</c:if></title>

	<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>

	<link rel="stylesheet" type="text/css"  href="<%=basePath%>css/admin_main111.css" />
	<script src="<%=basePath%>/js/ea/validate.js" type="text/javascript"></script>
	<link href="<%=basePath%>/css/ea/validate.css" rel="stylesheet"type="text/css" />
	<link rel="stylesheet" type="text/css"
		  href="<%=basePath%>css/ea/production/production.css" />

	<script src="<%=basePath%>js/ea/production/adesign/productdesign_add.js"
			type="text/javascript"></script>

	<link rel="stylesheet" type="text/css" href="<%=basePath%>js/popLayer/css/popstyle.css" />
	<script src="<%=basePath%>js/popLayer/js/popLayer.js" type="text/javascript"></script>
	<script src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js"></script>
	<script src="<%=basePath%>js/tree/codebase/dhtmlxtree.js"></script>
	<link rel="STYLESHEET" type="text/css" href="<%=basePath%>js/tree/codebase/dhtmlxtree.css" />
	<link rel="stylesheet" type="text/css"
		  href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
	<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>

	<script type="text/javascript">

        var basePath="<%=basePath%>";
        var seqnum = 2;
        var notoken = 0;
        var fiveClear="${fiveClear}";
        var industryId = "${industryId}";
	</script>

</head>
<body style="overflow-y:auto">
<form id="mainForm" name="mainForm" method="post">
	<input type="submit" name="submit" style="display:none;">
	<div class="main">
		<div class="top">产品信息</div>
		<div class="body">
			<div class="ttab">
				<ul class="tab">
					<li id="showinfo" class="selected left info">产品基本信息</li>
					<li id="showfunc">产品功能介绍</li>
				</ul>
			</div>
			<div class="showinfo show">
				<table id="productbl">

					<tr>
						<td align="right" style="width:30%;">
							产品主图：
						</td>

						<td>

							<img <c:if test="${productPackaging.ppID!=null}">src="<%=basePath%>${productPackaging.image}"</c:if><c:if test="${productPackaging.ppID==null}">src=""</c:if>  id="image" width="250" height="250" />
							<input type="hidden" name="productPackaging.image" class="photoPath" value="${productPackaging.image}"/>

						</td>
					</tr>
					<tr>
						<td align="right" >行业名称：</td>

						<td><input type="text" class="inputtext tradeName"  name="productPackaging.tradeName" readonly value="${productPackaging.tradeName}"/>

							<input type="hidden" class = "tradeCode" name="productPackaging.tradeCode" value="${productPackaging.tradeCode}"/>
							<input type="hidden" class = "tradeID" name="productPackaging.tradeID" value="${productPackaging.tradeID}"/>
							<input type="hidden"  name="productPackaging.ppID" value="${productPackaging.ppID}"/>
							<input type="hidden"  name="productPackaging.ppKey" value="${productPackaging.ppKey}"/>
							<input type="hidden" name="productPackaging.delStatus" value="${productPackaging.delStatus}"/>
							<input type="hidden" name="productPackaging.productstate" value="${productPackaging.productstate}"/>
							<input type="hidden" name="productPackaging.showweixin" value="${productPackaging.showweixin}"/>
							<input type="hidden" name="productPackaging.qualified" value="${productPackaging.qualified}"/>
							<input type="hidden" name="productPackaging.yjstatus" value="${productPackaging.yjstatus}"/>
							<input type="hidden" name="productPackaging.wholesaleStatus" value="${productPackaging.wholesaleStatus}"/>
							<input type="hidden" name="productPackaging.vipStatus" value="${productPackaging.vipStatus}"/>
							<input type="hidden" name="productPackaging.activityStatus" value="${productPackaging.activityStatus}"/>


						</td>
					</tr><%--
					<tr>
						<td align="right">项目产品分类：</td>
						<td><input type="text" class="inputtext projecttype"  readonly/>
						<input type="hidden" id="pro" name="productPackaging.projecttype" />

						</td>
					</tr>


					--%><tr>
					<td align="right">父级项目产品：</td>
					<td><input type="text" class="inputtext" name="productPackaging.parentName" readonly style="width:40%;" id="pp" value="${productPackaging.parentName}"/>
						<input type="hidden"  name="productPackaging.parentId" id="ppid" value="${productPackaging.parentId}"/>
						&nbsp;<input type="button" onclick="pop('products')"  id="selectpr" value="选择" class="btn01"/>
					</td>
				</tr>
					<tr>
						<td align="right">产品条码：</td>
						<td><input type="text" class="inputtext barCode" name="productPackaging.barCode"  value="${productPackaging.barCode}" readonly/>
							<input type="hidden" class="typeID" name="productPackaging.type"  value="${productPackaging.type}" />
						</td>
					</tr>
					<tr>
						<td align="right">产品编号：</td>
						<td><input type="text" class="inputtext" name="productPackaging.productCode" readonly  <c:if test="${productPackaging.ppID!=null}">value="${productPackaging.productCode}"</c:if><c:if test="${productPackaging.ppID==null}">value="自动生成"</c:if>/>
							<input type="hidden" name="productPackaging.goodsID" class="goodsID" id="gdid" value="${productPackaging.goodsID}"/>
							<input type="hidden" name="productPackaging.isScale" class="isScale"  value="${productPackaging.isScale}"/>
						</td>
					</tr>

					<tr>
						<td align="right">产品名称：</td>
						<td><input type="text" class="inputtext goodsName input3" readonly style="width:40%;" id="sp" name="productPackaging.goodsName" value="${productPackaging.goodsName}">
							&nbsp;<input type="button" onclick="pop('goods')" value="选择" id="selgood" class="btn01" />
						</td>
					</tr>


					<tr>
						<td align="right">科目：</td>
						<td><input type="text" class="inputtext input3" style="width:40%;" id="km" readonly name="productPackaging.subjectName" value="${productPackaging.subjectName}"/>
							&nbsp;<input type="button" onclick="pop('subject')" value="选择" class="btn01"/>
							<input type="hidden"  name="productPackaging.subjectID" value="${productPackaging.subjectID}" id="kmid"/>
						</td>
					</tr>


					<tr>
						<td align="right">品牌：</td>

						<td><input type="text" class="inputtext brand " name="productPackaging.brand" readonly value="${productPackaging.brand}"/>



						</td>
					</tr>


					<tr>
						<td align="right">型号管理：</td>
						<td><input type="text" class="inputtext model" name="productPackaging.model" readonly value="${productPackaging.model}"/>
						</td>
					</tr>


					<tr>
						<td align="right">物品规格：</td>
						<td><input type="text" class="inputtext standard" name="productPackaging.standard" readonly value="${productPackaging.standard}"/>
						</td>
					</tr>
					<tr>
						<td align="right">产品类目：</td>
						<td><input type="text" name="goodsManage.tradeName" class="inputtext" style="width:40%;" readonly id="catename" value="${procatename}"/>
							<input type="hidden" id="procate" name="procate" value="${procate}"/>
							&nbsp;<input type="button" onclick="pop('industry')" value="选择" class="btn01 procate"/>
						</td>
					</tr>
					<tr>
						<td align="right">单位：</td>
						<td><input type="text" class="inputtext variableID" name="productPackaging.variableID"  readonly value="${productPackaging.variableID}"/>
						</td>
					</tr>

					<tr>
						<td align="right">单价：</td>
						<td><input type="text" class="inputtext jisuan put3 posnum0"  id="price" name="productPackaging.price" value="${productPackaging.price}"/>
						</td>
					</tr>
					<tr>
						<td align="right">数量：</td>

						<td><input type="text" class="inputtext jisuan  put3 positiveNumZ" id="quantity" name="productPackaging.quantity" value="${productPackaging.quantity}"/>



						</td>
					</tr>

					<tr>
						<td align="right">金额：</td>

						<td><input type="text" class="inputtext  "  id="money" readonly name="productPackaging.money" value="${productPackaging.money}"/>


						</td>
					</tr>
					<tr>
						<td align="right">供应商：</td>
						<td><input type="text" class="inputtext  input3" readonly style="width:40%;" id="spcom"  value="${productPackaging.ccompanyName}">
							<input type="hidden"  id="spcomID"  name="productPackaging.ccompanyID" value="${productPackaging.ccompanyID}">
							&nbsp;<input type="button"  value="选择" id="selccom" class="btn01" />
						</td>
					</tr>
					<tr>
						<td align="right">备注：</td>
						<td><textarea type="text" class="inputtext ckTextLength"  name="productPackaging.remark" maxlength="250" style="height: 50px" value="${productPackaging.remark}" >
						</textarea>
						</td>
					</tr>
				</table>

			</div>
			<div class="showfunc show" style="overflow:auto;">





			</div>

		</div>
		<div class="bottom">
			<input type="button" class="btn save" value="提交保存" />
		</div>
	</div>
</form>
<!-- 科目 -->

<div id="subject" class="popMain">
	<div class="choose-box">
		<div class="choosetitle">
			<span>选择科目</span>
		</div>
		<div class="chooseborder">
			<div id="aadTree" style=" border: 0px solid #000000;"></div>


		</div>
		<div class="choose-box-bottom">
			<input type="botton" onclick="hide('subject')" value="关闭" />
		</div>
	</div>
</div>


<!-- 项目产品分类-->

<div id="project" class="popMain">
	<div class="choose-box">
		<div class="choosetitle">
			<span>选择项目产品分类</span>
		</div>
		<div class="chooseborder">
			<div id="projectTree" style=" border: 0px solid #000000;"></div>


		</div>
		<div class="choose-box-bottom">
			<input type="botton" onclick="hide('project')" value="关闭" />
		</div>
	</div>
</div>


<!-- 行业 -->

<div id="industry" class="popMain">
	<div class="choose-box">
		<div class="choosetitle">
			<span>产品类目</span>
		</div>
		<div class="chooseborder">
			<div id="industryTree" style=" border: 0px solid #000000;"></div>


		</div>
		<div class="choose-box-bottom">
			<input type="botton"  value="保存"  class="savecate"/>
			<input type="botton" onclick="hide('industry')" value="关闭" />
		</div>
	</div>
</div>


<!-- 选择物品 -->

<div id="goods" class="popMain">
	<div class="choose-box">
		<div class="choosetitle">
			<span>选择物品</span>
		</div>
		<div class="chooseborder">
			<div id="goodsTree" style=" border: 0px solid #000000;"></div>
			<table width="99%" height="33" id="searchgood"     border="0"
				   align="center" cellpadding="0" cellspacing="0"
				   style="margin-top: 5px; background: #FFFFFF;">
				<tr>
					<td width="100" align="right">
						物品编码或名称：
					</td>
					<td width="110">
						<input name="typeID" class="input" id="typeID" size="10"
							   style="margin-left: 2px;" />
					</td>
					<td height="33">
						<input type="button" class="btn01" id="searchGood"
							   name="button7" value="查询"/>
						<input type="button" class="btn01" id="selectGood"
							   name="button5" value="确定" />
						<input type="button" class="btn01 xzwp" name="button" value="新增" />

						<input type="hidden" name="parms" id="parms" />

					</td>
					<td width="80">
						<a id="wpsy" title="0" style="cursor:pointer;">上一页</a>
					</td>
					<td width="80">
						<a id="wpxy" title="0" style="cursor:pointer;">下一页</a>
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
									<div id="gtree"
										 style="overflow: scroll; z-index: 99; width:150px;height: 340px;border:1px solid #ccc;"></div>
								</td>
							</tr>
						</table>
					</td>
					<td width="83%" valign="top" align="left">
						<div id="body_02"
							 style="margin-top: 2px; height:340px;width:100%;border:1px solid #ccc;border-left:none; overflow: auto;">
						</div>
					</td>
				</tr>
			</table>

		</div>
		<div class="choose-box-bottom">
			<input type="botton" onclick="hide('goods')" value="关闭" />
		</div>
	</div>
</div>



<!-- 选择父级项目 -->

<div id="products" class="popMain">
	<div class="choose-box">
		<div class="choosetitle">
			<span>选择项目产品</span>
		</div>
		<div class="chooseborder">
			<table width="99%" height="33" id="searchpro"     border="0"
				   align="center" cellpadding="0" cellspacing="0"
				   style="margin-top: 5px; background: #FFFFFF;">
				<tr>
					<td width="100" align="right">
						产品编码或名称：
					</td>
					<td width="110">
						<input name="parameter" class="input" id="parameter" size="10"
							   style="margin-left: 2px;" />
					</td>
					<td height="33">
						<input type="button" class="btn01" id="searchProduct"
							   name="button7" value="查询"/>
						<input type="button" class="btn01" id="selectProduct"
							   name="button5" value="确定" />



					</td>
					<td width="80">
						<a id="wpsyp" title="0" style="cursor:pointer;">上一页</a>
					</td>
					<td width="80">
						<a id="wpxyp" title="0" style="cursor:pointer;">下一页</a>
					</td>
					<td width="100">
						<a id="wpzyp">共&nbsp;&nbsp; <span style="color: red"
														  id="wpzycountp"></span>&nbsp;&nbsp;页 </a>
					</td>
				</tr>
			</table>
			<table width="99%" border="0" align="center" cellpadding="0"
				   cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
				<tr>
					<td width="83%" valign="top" align="left">
						<div id="body_03"
							 style="margin-top: 2px; height:340px;width:100%;border:1px solid #ccc; overflow: auto;">
						</div>
					</td>
				</tr>
			</table>

		</div>
		<div class="choose-box-bottom">
			<input type="botton" onclick="hide('products')" value="关闭" />
		</div>
	</div>
</div>

<%------------------------------------选择往来单位------------------------------------%>



	<div class="jqmWindow jqmWindowcss1" style="top: 5%; left: 53%;"
		 id="companyjqModel">
		<div class="content1" style="width: 100%; height: 400px;">
			<div class="contentbannb">
				<div class="drag">
					往来单位
				</div>
			</div>
			<table width="99%" height="33" id="searchcompany" border="0"
				   align="center" cellpadding="0" cellspacing="0"
				   style="margin-top: 5px; background: #FFFFFF;">
				<tr>
					<td width="100" align="right">
						单位名称：
					</td>
					<td width="142">
						<input name="ccompanyID" class="input" id="ccompanyIDs" size="10"
							   style="margin-left: 2px;" />
					</td>
					<td height="33">
						<input type="button" class="btn02" id="searchcc" name="button7"
							   value="查询" />
						<input type="button" class="btn02" id="qdcompany" name="button5"
							   value="确定" />
						<input type="button" class="btn02 xzdw" name="button" value="新增" />
						<input type="button" class="btn02 JQueryreturns" name="button4"
							   value="关闭" />
						<input type="hidden" name="parms" id="dwparms" />

					</td>
					<td width="80">
						<a id="dwsy" title="0">上一页</a>
					</td>
					<td width="80">
						<a id="dwxy" title="0">下一页</a>
					</td>
					<td width="100">
						<a id="dwzy">共&nbsp;&nbsp; <span style="color: red"
														 id="dwzycount"></span>&nbsp;&nbsp;页 </a>
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
									<div id="dwTree" class="text_tree"
										 style="overflow: scroll; z-index: 99; height: 320px;"></div>
								</td>
							</tr>
						</table>
					</td>
					<td width="83%" valign="top" align="left">
						<div id="body_02cc"
							 style="margin-top: 2px; display: none; height: 310px; width: 100%; overflow: auto;">
						</div>
					</td>
				</tr>
			</table>
		</div>
	</div>


<%------------------------------------选择往来单位------------------------------------%>


<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
</body>
</html>