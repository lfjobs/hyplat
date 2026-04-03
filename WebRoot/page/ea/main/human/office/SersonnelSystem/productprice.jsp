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
<title>产品价格定位</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script type="text/javascript">
    var select = '01';
    var address = '';
	var basePath='<%=basePath%>';
    var ppageNumber=${pageNumber};
    var psearch='${search}';
    var token = 0;
</SCRIPT>
<script type="text/javascript" src="<%=basePath%>js/ea/human/office/SersonnelSystem/productprice.js"></script>

<script src="<%=basePath%>js/ea/human/cstaff.js"></script>
</head>
<body >
<form style="display: none;" name="addressForm" id="addressForm" method="post"><s:token></s:token><input type="submit" name="submit" style="display:none"/>
</form>

<div id="main_main" class="main_main">
  <table   class="address">
  <thead>
	 	    <tr >
	 	    <th width="40" align="center">请选择</th>
            <th width="100" align="center" >品名编号</th>
            <th width="130" align="center" >品名名称</th>
            <th width="130" align="center" >产品基本成本</th>
            <th width="130" align="center" >产品区域分配</th>
			<th width="130" align="center" >产品利润分析</th>
            <th width="130" align="center" >产品利润比例</th>
            <th width="130" align="center" >产品价格定位</th>
            <th width="130" align="center" >产品品种分类</th>
            <th width="130" align="center" >备注</th>
      </tr>
    </thead>
		<tbody>
          <s:iterator value="pageForm.list">
          <tr class="td_bg01 saveAjax" id="${productpriceID}">
          <td class="td_bg01">
             <input type="radio" name="a" class="JQuerypersonvalue" value="${productpriceID}"/>
            </td>
            <td class="td_bg01">
                <span id="goodsCoding" class="datas">${goodsCoding}</span>
            <td class="td_bg01">
               <span id="goodsName" class="datas">${goodsName}</span>
            <td class="td_bg01">
               <span id="productCost" class="datas">${productCost}</span>
				</td>
			<td class="td_bg01">
                <span id="productDistribution" class="datas">${productDistribution}</span>
            <td class="td_bg01">
               <span id="profitSales" class="datas">${profitSales}</span>
            <td class="td_bg01">
               <span id="profitSalesProportion" class="datas">${profitSalesProportion}</span>
				</td>
				<td class="td_bg01">
               <span id="productPositioning" class="datas">${productPositioning}</span>
				</td>
			<td class="td_bg01">
               <span id="productsSort" class="datas">${productsSort}</span>
			</td>
            <td class="td_bg01">
             <span id="remark">${remark}</span>
                             <span style="display:none" id="address">${address}</span>
                             <span style="display:none" id="productpriceID">${productpriceID}</span>
                             <span style="display:none" id="productpriceKey">${productpriceKey}</span>
			</td> 
          </tr>
          </s:iterator>
    </tbody>
  </table>
 <c:import url="../../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/productPrice/ea_getProductPriceList.jspa?pageNumber=${pageNumber}&search=${search}"></c:param>
</c:import>

</div>
    <div class="jqmWindow " style="width: 270px;right: 35%; top:20%" id="jqModelSearch">
            <form name="postSearchForm" id="postSearchForm" method="post">
            	<input type="submit" name="submit" style="display:none"/>
                <div class="drag">
                    查询信息
                    <div class="close">
                    </div>
                </div>
                <table id="cataffSearchTable">
                    <tr>
                        <td>
                            查询条件
                        </td>
                    </tr>
                    <tr>
                        <td>
                           品名编号：
                        </td>
                        <td>   
                          <input   name="productPrice.goodsCoding" />      
                        </td>
                    </tr>
                      <tr>
                        <td>
                           品名名称：
                        </td>
                        <td>   
                          <input   name="productPrice.goodsName" />      
                        </td>
                    </tr>
                </table>
                <div align="center">
                    <input type="button" class="input-button" id="tosearch" value=" 查询 " /><input name="search" type="hidden" value="search" />
                </div>
            </form>
        </div>
		<div class="jqmWindow jqmWindowcss" style="top:10%"  id="jqModel">
			 <form name="cstaffForm" id="cstaffForm" method="post" action="<%=basePath%>/ea/clienttracking/t_ea_save.jspa?pageNumber=${pageNumber}">
			       <div class="drag">产品价格定位
				    <div class="close"></div>
				    </div>
			<input type="submit" name="submit" style="display:none"/>
			 <div class="content">
			  <div class="contentbannb">
			  </div>
			 <table  border="0" id="stafftable" align="center" cellpadding="0" cellspacing="0" style="margin-top:5px;margin-bottom:5px;">
			 <tr>
			   <td>
			     <table height="250" border="0" align="center" cellpadding="0" cellspacing="0" id="stafftable2" style="margin-top:5px;margin-bottom:5px;">
			      <tr >
			        <td width="105"  align="right">品名编号：</td>
			        <td width="195" ><input name="productPrice.goodsCoding" type="text" id="goodsCoding" size="20"/></td>
					<td width="105"  align="right">品名名称：</td>
			        <td width="195" ><input name="productPrice.goodsName" type="text" id="goodsName" size="20"/></td>
		           </tr>
				   <tr>
			        <td width="105"  align="right">产品基本成本：</td>
			        <td width="195" ><input name="productPrice.productCost" type="text" id="productCost" size="20"/></td>
					<td width="105"  align="right">产品区域分配：</td>
			        <td width="195" ><input name="productPrice.productDistribution" type="text" id="productDistribution" size="20"/></td>
		           </tr>
				   <tr>
			        <td width="105"  align="right">产品利润分析：</td>
			        <td width="195" ><input name="productPrice.profitSales" type="text" id="profitSales" size="20"/></td>
					<td width="105"  align="right">产品利润比例：</td>
			        <td width="195" ><input name="productPrice.profitSalesProportion" type="text" id="profitSalesProportion" size="20"/></td>
		           </tr>
				   <tr>
				   <td width="105"  align="right">产品价格定位：</td>
			        <td width="195" ><input name="productPrice.productPositioning" type="text" id="productPositioning" size="20"/></td>
			        <td width="105"  align="right">产品品种分类：</td>
			        <td width="195" ><input name="productPrice.productsSort" type="text" id="productsSort" size="20"/></td>
			        </tr>
			        <tr>
					<td width="105"  align="right">备注：</td>
			        <td colspan="4">
			        <textarea name="productPrice.remark" id="remark" rows="4" cols="53" ></textarea>
									 <input type="hidden" name="productPrice.productpriceKey" id="productpriceKey" />
					          	     <input type="hidden" name="productPrice.productpriceID" id="productpriceID" />
				    </td>
		           </tr>
			    </table>
		       </td>
			  </tr>
			</table>
			</div>
			    <s:token></s:token>
			    <div align="center">
                    <input type="button" class="input-button JQuerySubmit" style="cursor:pointer;width:80px;"  value=" 保存 " />
                    <input type="button" class="input-button JQueryreturn" style="cursor:pointer;width:80px;" value=" 取消 " />
                </div>
			</form>
	</div>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
</body>
</html>
