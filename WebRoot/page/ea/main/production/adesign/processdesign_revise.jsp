<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>产品修改</title>
    <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		
	<script src="<%=basePath%>/js/ea/validate.js" type="text/javascript"></script>
	<link href="<%=basePath%>/css/ea/validate.css" rel="stylesheet"type="text/css" />
	<link rel="stylesheet" type="text/css"
		href="<%=basePath%>css/ea/production/production_revise.css" />
	
	<script src="<%=basePath%>js/ea/production/adesign/productdesign_revise.js"
		type="text/javascript"></script>
	<link rel="stylesheet" type="text/css"  href="<%=basePath%>css/admin_main111.css" />
	
	<link rel="stylesheet" type="text/css" href="<%=basePath%>js/popLayer/css/popstyle.css" />
	<script src="<%=basePath%>js/popLayer/js/popLayer.js" type="text/javascript"></script>
	<script src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js"></script>
	<script src="<%=basePath%>js/tree/codebase/dhtmlxtree.js"></script>
	<link rel="STYLESHEET" type="text/css" href="<%=basePath%>js/tree/codebase/dhtmlxtree.css" />
	
	<script type="text/javascript">
	var basePath="<%=basePath%>";
	var seqnum = 2;
	var notoken = 0;
	var fiveClear="${fiveClear}";
	var category="${category}";
	var staffID="";
	var pageNumber=0,pageCount=0;
	</script>
	<style type="text/css">
	.second select{
		display: none;
	}
	</style>
	
  </head>
  
<body>
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
						<td align="right" style="width:30%;"> 产品主图：</td>
                        <td>
							<img <c:if test="${productPackaging.ppID!=null}">src="<%=basePath%>${productPackaging.image}"</c:if><c:if test="${productPackaging.ppID==null}">src=""</c:if>  id="image" width="250" height="250" />
							<input type="hidden" name="productPackaging.image" class="photoPath" value="${productPackaging.image}"/>
						</td>
					</tr>
					<tr>
						<td align="right" >行业名称：</td>
						<td>
							<input type="text" class="inputtext tradeName"  name="productPackaging.tradeName" readonly value="${productPackaging.tradeName}"/>
							<input type="hidden" class = "tradeCode" name="productPackaging.tradeCode" value="${productPackaging.tradeCode}"/>
							<input type="hidden" class = "tradeID" name="productPackaging.tradeID" value="${productPackaging.tradeID}"/>
							<input type="hidden"  name="productPackaging.ppID" value="${productPackaging.ppID}"/>
							<input type="hidden"  name="productPackaging.ppKey" value="${productPackaging.ppKey}"/>
							<input type="hidden" name="productPackaging.delStatus" value="${productPackaging.delStatus}"/>
							<input type="hidden" name="productPackaging.productstate" value="${productPackaging.productstate}"/>
							<input type="hidden" name="productPackaging.sorting" value="${productPackaging.sorting}"/>
							<input type="hidden" name="productPackaging.hierarchical" value="${productPackaging.hierarchical}" id="hierarchical">
						</td>
					</tr>
					<tr>
						<td align="right">父级项目产品：</td>
						<td>
							<input type="text" class="inputtext" name="productPackaging.parentName" readonly id="pp" value="${productPackaging.parentName}"/>
							<input type="hidden"  name="productPackaging.parentId" id="ppid" value="${productPackaging.parentId}"/>
						</td>
					</tr>
					<tr>
						<td align="right">产品条码：</td>
						<td>
							<input type="text" class="inputtext barCode" name="productPackaging.barCode"  value="${productPackaging.barCode}" readonly/>
							<input type="hidden" class="typeID" name="productPackaging.type"  value="${productPackaging.type}" />
						</td>
					</tr>
					<tr>
						<td align="right">产品编号：</td>
						<td>
							<input type="text" class="inputtext" name="productPackaging.productCode" readonly  <c:if test="${productPackaging.ppID!=null}">value="${productPackaging.productCode}"</c:if><c:if test="${productPackaging.ppID==null}">value="自动生成"</c:if>/>
							<input type="hidden" name="productPackaging.goodsID" class="goodsID" id="gdid" value="${productPackaging.goodsID}"/>
						</td>
					</tr>
					<tr>
						<td align="right">产品名称：</td>
						<td>
							<input type="text" class="inputtext goodsName input3" readonly id="sp" name="productPackaging.goodsName" value="${productPackaging.goodsName}">
						</td>
					</tr>
					<tr>
						<td align="right">科目：</td>
						<td>
							<input type="text" class="inputtext input3" id="km" readonly name="productPackaging.subjectName" value="${productPackaging.subjectName}"/>
							<input type="hidden"  name="productPackaging.subjectID" value="${productPackaging.subjectID}" id="kmid"/>
						</td>
					</tr>
					<tr>
						<td align="right">责任人：</td>
						<td>
							<input type="text" class="inputtext put3 staffName" name="productPackaging.staffName" readonly value="${productPackaging.staffName}"/>
							<input type="hidden" class="inputtext put3 staffID" name="productPackaging.staffID"  value="${productPackaging.staffID}"/>
						</td>
					</tr>
				   <tr>
						<td align="right">品牌：</td>
						<td>
							<input type="text" class="inputtext brand " name="productPackaging.brand" readonly value="${productPackaging.brand}"/>
						</td>
					</tr>
					<tr>
						<td align="right">型号管理：</td>
						<td>
							<input type="text" class="inputtext model" name="productPackaging.model" readonly value="${productPackaging.model}"/>
						</td>
					</tr>
					<tr>
						<td align="right">物品规格：</td>
						<td>
							<input type="text" class="inputtext standard" name="productPackaging.standard" readonly value="${productPackaging.standard}"/>
						</td>
					</tr>
					<tr>
						<td align="right">单位：</td>
						<td>
							<input type="text" class="inputtext variableID" name="productPackaging.variableID"  readonly value="${productPackaging.variableID}"/>
						</td>
					</tr>
                    <tr>
						<td align="right">单价：</td>
						<td>
							<input type="text" class="inputtext jisuan put3 posnum0"  id="price" name="productPackaging.price" value="${productPackaging.price}"/>
						</td>
					</tr>
					   <tr>
						<td align="right">数量：</td>
						<td>
							<input type="text" class="inputtext jisuan  put3 positiveNumZ" id="quantity" name="productPackaging.quantity" value="${productPackaging.quantity}"/>
						</td>
					</tr>
					<tr>
						<td align="right">金额：</td>
						<td>
							<input type="text" class="inputtext  "  id="money" readonly name="productPackaging.money" value="${productPackaging.money}"/>
						</td>
					</tr>
					<tr>
						<td align="right">产品类别：</td>
						<td>
							<select class="inputtext field" id="" readonly="readonly" 
										style="width:249px;height:22px;"  name="productPackaging.field">
								<option sign="产品" value="01">产品</option>
								<option sign="字段" value="00">信息字段</option>
								<option sign="组织机构" value="02">组织机构</option>
							</select>
						</td>
					</tr>
					<tr class="information" style="display: none;">
						<td align="right">信息字段详情：</td>
						<td>
							<div>
								<span>一级信息：</span>
								<span>
									<select class="inputtext racing"  style="width:185px;">
										<option sign="time" value="时间">时间格式</option>
										<option sign="text" value="文本">文本格式</option>
										<option sign="option" value="选项">选项格式</option>
										<option sign="details" value="信息">信息格式</option>
										<option sign="file" value="文件">文件格式</option>
										<option sign="product" value="产品">所属产品</option>
									</select>
								</span>
							</div>
						</td>
					</tr>
					<tr class="information second"  style="display: none;">
						<td></td>
						<td>
							<div>
								<span>二级信息：</span>
								<span>
									<select  style="width:185px;display:inline;" class="inputtext nothing time">
										<option sign="singleTime" value="单一时间格式">单一时间格式</option>
										<option sign="combinationTime" value="开始~结束时间格式">开始~结束时间格式</option>
									</select>	
									<select  style="width:185px;" class="inputtext  text">
										<option sign="aloneText" value="不可添加多次">不可添加多次</option>
										<option sign="repeatText" value="可重复添加，并显示历史记录">可重复添加，并显示历史记录</option>
									</select>
									<select  style="width:185px;" class="inputtext  option">
										<option sign="radioOption" value="单选模式">单选模式</option>
										<option sign="multiselectOption" value="多选模式">多选模式</option>
									</select>
									<select  style="width:185px;" class="inputtext  details">
										<option sign="staffDetails" value="人员列表">人员列表</option>
										<option sign="storageDetails" value="库房列表">库房列表</option>
										<option sign="companyDetails" value="公司列表">公司列表</option>
										<option sign="subjectDetails" value="库房列表">库房列表</option>
									</select>
									<select  style="width:185px;" class="inputtext  file">
										<option sign="textFile" value="文本文档格式">文本文档格式</option>
<!-- 										<option sign="pictureFile" value="图片格式">图片格式</option>
										<option sign="companyDetails" value="公司列表">公司列表</option>
										<option sign="subjectDetails" value="库房列表">库房列表</option> -->
									</select>
									<select  style="width:185px;" class="inputtext  product">
										<option sign="theProduct" value="所属产品">所属产品</option>
<!-- 										<option sign="pictureFile" value="图片格式">图片格式</option>
										<option sign="companyDetails" value="公司列表">公司列表</option>
										<option sign="subjectDetails" value="库房列表">库房列表</option> -->
									</select>
								</span>
							</div>
						</td>
					</tr>
					<tr>
						<td align="right">备注：</td>
						<td>
							<textarea type="text" class="inputtext ckTextLength"  name="productPackaging.remark" maxlength="250" style="height: 50px">${productPackaging.remark}</textarea>
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
	
	
	
	
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		framespacing="0" height="0"></iframe>
</body>
</html>
