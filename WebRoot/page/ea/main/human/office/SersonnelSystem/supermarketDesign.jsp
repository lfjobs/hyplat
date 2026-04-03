<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmx" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>营销产品发布>>产品发布</title>
	<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
	<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
	<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"
		  type="text/css" />
	<link rel="stylesheet" type="text/css"
		  href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
	<link rel="stylesheet" type="text/css"
		  href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
	<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
	<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
	<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
	<script src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js"></script>
	<script src="<%=basePath%>js/tree/codebase/dhtmlxtree.js"></script>
	<link rel="STYLESHEET" type="text/css"
		  href="<%=basePath%>js/tree/codebase/dhtmlxtree.css" />
	<script type="text/javascript"
			src="<%=basePath%>js/common/organizationTree.js"></script>
	<script
			src="<%=basePath%>js/ea/human/office/SersonnelSystem/supermarketDesign.js"></script>
	<script type="text/javascript"src="<%=basePath%>js/accifr/js/category.js"></script>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/admin_main111.css" />
	<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
	<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/overlayer.js"></script>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/overlayer.css" />
	<link rel="stylesheet" href="<%=basePath%>js/tree/common/css/style.css" type="text/css" media="screen" />
	<script src="<%=basePath%>js/ea/human/cstaff.js"></script>
	<script language="javascript" type="text/javascript" src="<%=basePath%>js/common/common_word.js"></script>
	<script src="<%=basePath%>swfupload/swfupload.js"></script>
	<script src="<%=basePath%>swfupload/files_uploadauto.js"></script>
	<script type="text/javascript">
        var ghua = "${param.ghua}";
        var fiveClear="${fiveClear}";
        var  devide="${devide}";
        var 	productdesignID = '';
        var opertionID = '';
        var  basePath='<%=basePath%>';
        var  pNumber ="${pageNumber}";
        var  search='${search}';
        var companyId="${companyID}";
        var  token=0;
        var  goodsID='';
        var notoken = 0;
        var treeid;
        var no="${no}";
        var treename;
        var typeString="${typeString}";
        var tree;
        var sdate="${sdate}";
        var edate="${edate}";
        var identifier='${param.identifier}';
        var selects =1;
        var selectpeijian =1;
        //判断flexigrid中的button
        var flexbutton = '<%=request.getParameter("flexbutton") %>';
        function check(){
            var r=$("#weiDianType").val();
            if(r=="培训店"){
                $("#banzheng").show();
            }else{
                $("#banzheng").hide();
            }
        }

        $(function(){
            //$("html").css("overflow-x",extensionStaffCoach=="extensionStaffStudent"?"scroll":"");
            $("html").css("overflow-y","hidden");
        });
        //父页面必须用此方法，以供子页(弹出层返回数据)调用**********************
        function paret(e){
            alert("I'm parent");
            //隐藏弹出层
            $("#category").hide();
        }

        $(function(){
            $(".divCTL").hover(function(){
                $(".divFL").show();
            },function(){
                $(".divFL").hide();
            });
        });

	</script>
</head>
<body>

<form style="display: none;" name="addressForm" id="addressForm"
	  method="post">
	<s:token></s:token>
	<input type="submit" name="submit" style="display: none" />
</form>

<div id="main_main" class="main_main">
	<table class="address">
		<thead>
		<tr>
			<th width="30" align="center">
				选择
			</th>
			<th width="30" align="center">
				序号
			</th>
			<c:if test="${param.identifier!='identifier'}">
				<th width="150" align="center">
					公司名称
				</th>
				<th width="70" align="center">
					部门
				</th>
				<th width="70" align="center">
					责任人
				</th>
				<th width="70" align="center">
					包装日期
				</th>
			</c:if>
			<th width="70" align="center">
				产品编号
			</th>
			<th width="70" align="center">
				产品名称
			</th>
			<th width="70" align="center">
				产品单位
			</th>
			<th width="70" align="center">
				产品类型
			</th>
			<th width="70" align="center" style="display: none;">
				产品规格
			</th>
			<th width="50" align="center">
				产品数量
			</th>
			<!--
            <th width="50" align="center">
                价格类型
            </th>
            <th width="50" align="center">
                产品单价
            </th>
            <th width="50" align="center">
                产品金额
            </th>
            -->
			<th width="50" align="center">
				单价
			</th>
			<th width="50" align="center">
				金额
			</th>
			<th width="50" align="center">
				产品重量
			</th>
			<th width="80" align="center">
				发布状态
			</th>
			<th width="80" align="center">
				佣金状态
			</th>


		</tr>
		</thead>
		<tbody>
		<% int number = 1; %>
		<c:forEach var='arr' items="${pageForm.list}">
			<tr id="${arr[0] }">
				<td>
					<input type="radio" name="a" class="JQuerypersonvalue"
						   value="${arr[0]}" />
				</td>
				<td>
					<span id="number"><%=number%></span>
				</td>
				<c:if test="${param.identifier!='identifier'}">
					<td>
						<span id="companyname">${arr[1]}</span>
						<span id="ppID" style="display: none">${arr[0]}</span>
					</td>
					<td>
						<span id="journalNum">${arr[2]}</span>
					</td>
					<td>
						<span id="staffID">${arr[3]}</span>
					</td>
					<td>
						<span id="PackagingDate">${fn:substring(arr[4], 0, 10)}</span>
					</td>
				</c:if>
				<td>
					<span id="goodsCoding">${arr[5]}</span>
				</td>
				<td>
					<span id="goodsName">${arr[6]}</span>
					<span id="goodsID" style="display: none">${arr[16]}</span>
				</td>
				<td>
					<span id="variableID">${arr[7]}</span>
				</td>
				<td>
					<span id="acquiesceStandard">${arr[15]}</span>
				</td>
				<td style="display: none;">
					<span id="acquiesceStandard">${arr[8]}</span>
				</td>
				<td>
					<span id="quantity">${arr[9]}</span>
				</td>

				<td>
					<span>${arr[16]}</span>
				</td>
				<td>
					<span>${arr[17]}</span>
				</td>
				<td>
					<span id="weight">${arr[10]}</span>
				</td>

				<td>
					<span id="weight">${arr[18]=="01"?"已发布":"未发布"}</span>
				</td>

				<td>
					<span id="weight">${arr[21]=="00"?"未设计佣金":"已设计佣金"}</span>

				</td>




			</tr>
			<% number++; %>
		</c:forEach>
		</tbody>
	</table>
	<c:import url="../../../../page_navigator.jsp">
		<c:param name="actionPath"
				 value="ea/productdesign/ea_getListSupermarketDesign.jspa?pageNumber=${pageNumber}&search=${search}&sdate=${sdate}&edate=${edate}&identifier=${param.identifier}&flexbutton=${param.flexbutton}&ghua=${param.ghua}&fiveClear=${fiveClear}&no=${no}&typeString=${typeString}&devide=${devide}"></c:param>
	</c:import>
</div>

<!--搜索窗口 -->
<div class="jqmWindow " style="width: 270px; right: 35%; top: 20%"
	 id="jqModelSearch">
	<form name="postSearchForm" id="postSearchForm" method="post">
		<input type="submit" name="submit" style="display: none" />
		商品名称${param.identifier}：
		<input name="productDesign.goodsName" />
		包装日期：
		<input id="sdate" name="sdate" onfocus="date(this);"
			   style="width: 85px" />
		至
		<input id="edate" name="edate" onfocus="date(this);"
			   style="width: 85px" />
		<input type="button" id="tosearch"
			   value=" 查询 " />
		<input name="search" type="hidden" value="search" />
		<input name="fiveClear" type="hidden" id="fiveClear" value="${fiveClear}" />
	</form>
</div>


<!--添加产品 -->
<div class="jqmWindow jqmWindowcss" style="top: 5%;height: 500px; width: 680px; overflow: scroll;"  id="jqModel">
	<form name="cstaffForm" id="cstaffForm" method="post">
		<input type="submit" name="submit" style="display: none" />
		<div class="drag">
			产品包装设计
			<div class="close"></div>
		</div>
		<table  id="stafftable" cellpadding="0" cellspacing="0"
				style="margin-0.0.top: 5px; margin-bottom: 5px;width: 650px;">
			<tr>
				<td colspan="6">
					<table width="100%" id="ms">
						<tr>
							<td height="30" align="left" colspan="2" >
								<input type="button" id="shuju" value="选择产品"  style="width: 100px;"/>
								<div class="divCTL"><input type="button" id="xzcpfl" value="选择产品分类"  style="width: 100px;"/></div>
								<div class="divFL" style="display:none;">
									<select name="prdCategory" id="prdCategory" size="5" multiple="multiple" style="width:150px;">
									</select>
								</div>
							</td>
							<td align="right" width="90">所属店铺：</td>
							<td align="left" width="100" colspan="2">
								<select name="productDesign.weiDianType" onchange="check()" id="weiDianType" style="width: 60px;">
									<option value="" selected="selected"/>请选择</option>
									<option value="培训店"/>培训店</option>
									<option value="招商店"/>招商店</option>
									<option value="餐饮店"/>餐饮店</option>
									<option value="茶园店"/>茶园店</option>
									<option value="住宿店"/>住宿店</option>
								</select>
							</td>
							<td  rowspan="4" colspan="2">
								<div contenteditable="" id="post_article_content" style="border:1px solid #000; width: 252px;height: 120px;overflow: hidden;"></div>
								<table id="upload_content" class="swfupload_main"   style="display:none;" width="100%">
									<tbody id="divFileProgressContainer">
									</tbody>
									<tfoot>
									<tr>
										<td colspan="4">
											<input type="hidden" id="hidIdList"/>
										</td>
									</tr>
									</tfoot>
								</table>
							</td>
						</tr>
						<tr>
							<td height="30" align="right" width="90">
								产品编号：
							</td>
							<td width="100">
								<span id="goodsCoding"></span>
							</td>
							<td width="90" align="right">
								产品名称：
							</td>
							<td width="100">
								<span id="goodsName"></span>
							</td>
						</tr>
						<tr>
							<td height="30" align="right">
								单&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;位：
							</td>
							<td>
								<span id="variableID"></span>
							</td>
							<td align="right">
								规&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;格：
							</td>
							<td>
								<span id="acquiesceStandard"></span>
							</td>
						</tr>
						<tr>
							<td height="30" align="right" >
								成&nbsp;&nbsp;本&nbsp;价：
							</td>
							<td width="100">
								<input name="productDesign.money"  id="moneyi" class="input" style="margin-left: 2px;width: 50px" />
							</td>
							<td align="right">
								库存数量：
							</td>
							<td width="100">
								<input name="productDesign.stockSize"  id="stockSize" class="input" style="margin-left: 2px;width: 50px" />
							</td>

							<input name="productDesign.quantity" class="jisuanq put3 isNaN" type="hidden"
								   id="quantity"  style="margin-left: 2px;width: 50px" value="1"/>
						</tr>

						<tr style="display: none;" id="banzheng">
							<td  height="30" align="right">
								考核办证费：
							</td>
							<td  colspan="3" align="left">
								<input name="productDesign.certificateCost" class="isNaN" style="margin-left: 2px;width: 50px" />
							</td>
						</tr>
						<tr>
							<td colspan="4" align="left" height="25" ><input type="button" id="shuju2" value="选择配件"  style="width: 100px;"/></td>
							<td colspan="2" align="center">
								<span id="uploads"></span>
							</td>
						</tr>
					</table>
				</td>

			</tr>
			<tr>
				<td colspan="9">
					<table width="100%" id="pj">
						<thead>
						<th align="center" bgcolor="#FFFFFF" height="25">产品编号</th>
						<th align="center" bgcolor="#FFFFFF">产品名称</th>
						<th align="center" bgcolor="#FFFFFF">规格</th>
						<th align="center" bgcolor="#FFFFFF">类型</th>
						<th align="center" bgcolor="#FFFFFF">重量</th>
						<th align="center" bgcolor="#FFFFFF">数量</th>
						<th align="center" bgcolor="#FFFFFF">单价</th>
						<th align="center" bgcolor="#FFFFFF">金额</th>
						<th align="center" bgcolor="#FFFFFF">操作</th>
						</thead>
						<tbody>
						<tr id="kelongpeijian" style="display: none;">
							<td align="center" bgcolor="#FFFFFF" height="26">
								<input type="hidden" name="goodsID" id="goodsID"/>
								<span id="goodsCoding"></span>
							</td>
							<td align="center" bgcolor="#FFFFFF">
								<span id="goodsName"></span>
								<input name="goodsName" type="hidden" id="goodsName" />
							</td>

							<td align="center" bgcolor="#FFFFFF">
								<span id="standard"></span><!-- 规格 -->
							</td>
							<td align="center" bgcolor="#FFFFFF">
								<span id="typeID"></span>
							</td>
							<td align="center" bgcolor="#FFFFFF">
								<input name="weight" class="input" id="weight" style="width: 40px" />
							</td>
							<td align="center" bgcolor="#FFFFFF">
								<input id="quantpj" name="quantity" class="input jisuanpj" style="width: 40px"/>
							</td>
							<td align="center" bgcolor="#FFFFFF">
								<input id="pricepj" name="price" class="input jisuanpj" style="width: 40px"/>
							</td>
							<td align="center" bgcolor="#FFFFFF">
								<input id="moneypj" name="money" class="input jisuanpj" style="width: 40px"/>
							</td>
							<td align="center" bgcolor="#FFFFFF">
								<a href="#" class="removeline"><img
										src="<%=basePath%>images/admin_images/gtk-del.png"
										width="16" height="16" title="删除" border="0" /> </a>
							</td>
						</tr>
						</tbody>
					</table>
				</td>
			</tr>
			<tr style="height: 20px">
				<td height="20" align="left" colspan="4" ><input type="button"  id="newline"  value=" 添加产品价格类别" style="width: 120px;"/></td>
			</tr>

			<tr>
				<td colspan="4">
					<table width="100%" id="ms">
						<thead>
						<th align="center" bgcolor="#FFFFFF" height="25">价格类别</th>
						<th align="center" bgcolor="#FFFFFF" >单价</th>
						<th align="center" bgcolor="#FFFFFF">金额</th>
						<th align="center" bgcolor="#FFFFFF">操作</th>
						</thead>
						<tbody>
						<tr class="checkgoods" id="kelong1">
							<td align="center" bgcolor="#FFFFFF" height="26">
								<input type="text" name="productPriceCategory.category" value="零售价" style="margin-left: 2px;width: 60px;" readonly="readonly"/>
							</td>
							<td align="center" bgcolor="#FFFFFF">
								<input name="productPriceCategory.price" class="jisuan put3 input" id="price1" style="margin-left: 2px;width: 50px" />
							</td>
							<td align="center" bgcolor="#FFFFFF">
								<input name="productPriceCategory.money" class="input" id="money1" style="margin-left: 2px;width: 50px"  />
							</td>
							<td align="center" bgcolor="#FFFFFF"></td>

						</tr>
						<tr id="kelong" style="display:none;">
							<td align="center" bgcolor="#FFFFFF" height="26">
								<s:select list="%{#request.priceManageList}"
										  listKey="codeValue" listValue="codeValue" id="category"
										  name="category" theme="simple"></s:select>
							</td>
							<td align="center" bgcolor="#FFFFFF">
								<input name="price" class="jisuan isNaN price" id="price" style="margin-left: 2px;width: 50px"/>
							</td>
							<td align="center" bgcolor="#FFFFFF">
								<input name="money"  id="money" class="money" style="margin-left: 2px;width: 50px"  />
							</td>
							<td align="center" bgcolor="#FFFFFF">
								<img src="<%=basePath%>images/gtk-del.png"  class="removeline" style="cursor:pointer;" />
							</td>
						</tr>
						</tbody>
					</table>
				</td>
			</tr>
			<tr>
				<td height="37" align="right">
					附产品说明书：
				</td>
				<td>
					<input class=" accessoriesUrl1" type="button"
						   value="文档编辑" />
					<input name="productDesign.manualUrl" id="manualUrl"
						   type="hidden" />
				</td>
				<td align="right">
					附产品宣传：
				</td>
				<td>
					<input class=" accessoriesUrl2" type="button"
						   value="文档编辑" />
					<input name="productDesign.propagandaUrl" id="propagandaUrl"
						   type="hidden" />
				</td>
			</tr>
			<tr>
				<td height="37" align="right">
					附公司文件：
				</td>
				<td>
					<input class=" accessoriesUrl3" type="button"
						   value="文档编辑" />
					<input type="hidden" name="productDesign.ppID" id="ppID" />
					<input name="productDesign.goodsID" type="hidden" id="goodsID"
					/>
					<input name="productDesign.goodsName" type="hidden"
						   id="goodsName" />
				</td>

				<td  align="right">
					是否在微信显示：
				</td>
				<td>
					<select name="productDesign.showweixin"  style="width: 60px;">
						<option value="01"/>是</option>
						<option value="02" selected="selected"/>否</option>
					</select>
				</td>
			</tr>
		</table>
		<s:token></s:token>
		<div align="center">
			<input type="button" class="input-button"
				   style="cursor: pointer; width: 80px;" id="tosave" value="保存 " />
			<input type="button" class="input-button JQueryreturn"
				   style="cursor: pointer; width: 80px;" value="取消" />
		</div>
	</form>
</div>
<div>
	<form id="shangjia" name="shangjia" mehod="post">
		<input type="hidden" value="" name="chenpID" id="ID"/>
		<input type="hidden" name="type" id="type" />
	</form>
</div>

<%------------------------------------物品选择------------------------------------%>
<form name="SubjectsForm" id="SubjectsForm" method="post"
	  enctype="multipart/form-data">
	<input type="submit" name="submit" style="display: none" />
	<div class="jqmWindow jqmWindowcss1" style="top: 5%;"
		 id="goodsjqModel">
		<div class="content1" style="width: 100%; height: 400px;">
			<div class="contentbannb">
				<div class="drag">
					选择产品
					<div class="close">
					</div>
				</div>
			</div>
			<table width="99%" height="33" id="searchgood" border="0"
				   align="center" cellpadding="0" cellspacing="0"
				   style="margin-top: 5px; background: #FFFFFF;">
				<tr>
					<td width="100" align="right">
						物品编码或名称：
					</td>
					<td width="70">
						<input name="parameter" class="input" id="parameter" size="10"
							   style="margin-left: 2px;" />
					</td>
					<td height="33">
						<input type="button" class="btn02" ID="searchGood"
							   name="button7" value="查询" />
						<input type="button" class="btn02" id="selectGood"
							   name="button5" value="确定" />
						<input type="button" class="btn02" id="selectGood2"
							   name="button5" value="确定" />
						<input type="button" class="btn02 xzwp" name="button" value="新增" />
						<input type="hidden" name="parms" id="parms" />
					</td>
					<td width="40">
						<a id="wpsy" title="0">上一页</a>
					</td>
					<td width="40">
						<a id="wpxy" title="0">下一页</a>
					</td>
					<td width="80">
						<a id="wpzy">共&nbsp;&nbsp; <span style="color: red"
														 id="wpzycount"></span>&nbsp;&nbsp;页</a>
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
									<div id="SubjectsAadTree" class="text_tree"
										 style="overflow: scroll; z-index: 99; height: 320px;"></div>
								</td>
							</tr>
						</table>
					</td>
					<td width="83%" valign="top" align="left">
						<div id="body_02"
							 style="margin-top: 2px; display: none; height: 320px; width: 100%; overflow: scroll;">
						</div>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<s:token></s:token>
</form>


<!-- 临时添加 -->
<form name="TransferForm" id="TransferForm" method="post" action="">
	<div class="jqmWindow" style="width: 400px; right: 25%; top: 5%"
		 id="jqModelTransfer">
		<input type="submit" name="submit" style="display: none" />
		<div class="drag">
			转移
			<div class="close">
			</div>
		</div>
		<div style="padding-left:100px;">
			<p>
				选择转移至：
			</p>
			<p>
				<input type="radio" name="fiveClear"  id="fiveClear1" value="1" />
				<label for="fiveClear1">人事产品设计</label>
			</p>
			<p>
				<input type="radio" name="fiveClear"  id="fiveClear2" value="2" />
				<label for="fiveClear2">办公室产品设计</label>
			</p>
			<p>
				<input type="radio" name="fiveClear" id="fiveClear3" value="3" />
				<label for="fiveClear3">财务产品设计</label>
			</p>
			<p>
				<input type="radio" name="fiveClear" id="fiveClear4" value="4" />
				<label for="fiveClear4">生产产品设计</label>
			</p>
			<p>
				<input type="radio" name="fiveClear" id="fiveClear5" value="5" />
				<label for="fiveClear5">营销产品设计</label>
			</p>
			<p>
				<input type="button" class="input-button" id="trans" value="   转移     "/>
			</p>
		</div>



	</div>
</form>

<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
<script type="text/javascript">
    var basePath = "<%=basePath%>";
    var SWFUpload1_id={SWFObj:new Object()}
    function SWFUpload1_load() {
        String.prototype.trim = function() {
            return this.replace(/(^\s*)|(\s*$)/g, "");
        }
        var LoadSettings = {
            post_params:{
                path: "/upload_files/office/filemanage/",
                fn:"",
                small:"false",
                sw:"30",
                sh:"80",
                wm:"True",
                data:""
            },
            file_size_limit: "2 MB",
            file_types: "*.jpg;*.gif;*.png;*.bmp",
            file_types_description: "只能上传.jpg;.gif;.png;.bmp 格式的图片文件",
            file_upload_limit: 1,
            button_action:SWFUpload.BUTTON_ACTION.SELECT_FILE,//点击按钮将会打开单文件上传的对话框
            button_window_mode : true,//SWFUpload.WINDOW_MODE.Opaque,//在页面上显示swf可被覆盖
            button_disabled : false,//是否禁用按钮
            upload_success_handler:SWFUpload1_uploadSuccess,
            button_image:"<%=basePath%>images/pic.jpg",
            button_width:150,
            button_height:25,
            button_placeholder_id:"uploads", //swf替换页面中的位置
            custom_settings: {
                upload_target: "divFileProgressContainer",//上传图片在页面中的显示位置
                submitBtnId: "BtnSave",//save按钮
                serverDataId: "hidIdList",//隐藏域
                uploadMode: "LIST"//?
            }
        }
        SWFLoad(SWFUpload1_id,LoadSettings);
    }
    addLoadEvent(SWFUpload1_load);
    function SWFUpload1_uploadSuccess(file, serverData) {
        uploadSuccess(file, serverData,this);
        $("#upload_content").hide();

        var hidIdList=$("#hidIdList").val();
        var result=hidIdList.split(",");
        var str="";
        for(var i=0;i<result.length-1;i++){
            str+="<center><image src='<%=basePath%>"+result[i]+"' style='width: 250px;height:120px;'/></center>";
        }
        $("#post_article_content").append(str);
        // $("#hidIdList").val("");
    }
    function uploads(){
        var swf = SWFUpload1_id.SWFObj;
        if (swf != null && swf.getStats().files_queued > 0) {
            swf.startUpload();
        }else{

            alert("请添加文件");
            return;
        }
    }
    function dialogOpen() {
        //$("#upload_content").show();
    }
</script>
</body>
</html>
