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
<title>库房管理</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>

<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script src="<%=basePath%>js/ea/validate.js"  type="text/javascript"></script>
<script src="<%=basePath%>js/ea/finance/invoicing/personalwarehouse_list.js"></script>
<style type="text/css">
.xx{
	color:#FF0000;
	margin-right:2px;}
.xx1{
	color:##3300CC;
	margin-right:2px;}
</style>
<script  type="text/javascript">
   var select = '01';
   var afforestID = '';
   var  basePath='<%=basePath%>';           
   var  pNumber =${pageNumber};  
   var  search='${search}';
   var  token=0;
   var  notoken=0;
   var sdate="${sdate}";
   var edate="${edate}";
   var billStatus='07';
   var inventoryID='';
   var kutime='';//用于库存盘点
 
</script>
<script src="<%=basePath%>js/ea/human/cstaff.js"></script>
</head>
<body>
 <!--搜索窗口 -->
        
<div class="jqmWindow" style="width: 240px;margin-left: 400px;top:15%" id="jqModelSearch">
            <form name="postSearchForm" id="postSearchForm" method="post">
            	<input type="submit" name="submit" style="display:none"/>
                <div class="drag"> 查询信息
                    <div class="close">
                    </div>
                </div>
                <table id="cataffSearchTable">
                     <tr align="center">
                        <td>物品类别</td>
                        <td>
                           <input name="inventoryParam.goodsType" style="width: 100px"/>
                           </td>
                    </tr>
                     <tr align="center">
                        <td>物品名称： </td>
                        <td>
                            <input name="inventoryParam.goodsName" style="width: 100px"/>   </td>
                    </tr>
                </table>
                <div align="center"> 
                    <input type="button" class="input-button" id="tosearch" value=" 查询 " /><input name="search" type="hidden" value="search" />
                </div>
            </form>
        </div>
  
<form style="display: none;" name="addressForm" id="addressForm" method="post"><s:token></s:token><input type="submit" name="submit" style="display:none"/>
</form>
  <table   class="address">
  <thead>
	 	    <tr>
	 	    <th width="40" align="center">请选择</th>
            <th width="100" align="center" >物品名称</th>
			<th width="100" align="center" >物品类别</th>
			<th width="100" align="center" >条码</th>
			<th width="100" align="center" >库存数量</th>
			<th width="100" align="center" >单价</th>
			<th width="100" align="center" >总价</th>
			<th width="100" align="center" >单位</th>
			<th width="100" align="center" >物品状态</th>
      </tr>
    </thead>
		<tbody>
          <c:forEach items="${pageForm.list}" var="inventory">
          <tr class="td_bg01 saveAjax" id="${inventory[0]}">
          <td class="td_bg01">
              <input type="radio" name="a"  class="JQuerypersonvalue" value="${inventory[0]}"/>
            </td>
            <td class="td_bg01">
            	<span id="inventoryID" style="display: none;">${inventory[0]}</span>
            	<span id="goodsID" style="display: none;">${inventory[9]}</span>
                <span id="goodsName" class="datas" >${inventory[1]}</span>
				</td>
            <td class="td_bg01">
               <span id="area" class="datas">${inventory[2]}</span>
				</td>
			 <td class="td_bg01">
             <span id="amount">${inventory[3]}</span>
           			</td>
            <td class="td_bg01">
             <span id="storageQuantity">${inventory[4]}</span>
            </td>
            <td class="td_bg01">
               <span id="principal" class="datas">${inventory[5]}</span>
				</td>
            <td class="td_bg01">
             <span id="afforestDate" class="datas">${inventory[6]}</span>
			</td> 
			 <td class="td_bg01">
             <span id="afforestDate" class="datas">${inventory[7]}</span>
			</td> 
			 <td class="td_bg01">
			  <c:if test="${inventory[8] =='00'}">
				<span id="goodstatus" class="datas">正常</span>
			</c:if>
              <c:if test="${inventory[8] =='01'}">
				<span id="goodstatus" class="datas">维修</span>
			</c:if>
			 <c:if test="${inventory[8] =='02'}">
				<span id="goodstatus" class="datas">报废</span>
			</c:if>
			</td>
          </tr>
          </c:forEach>
    </tbody>
  </table>
 <c:import url="../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="/ea/personal/ea_getInventoryManagementList.jspa?pageNumber=${pageNumber}&search=${search}"></c:param>
</c:import>
 <!--借出窗口 -->
        
<div class="jqmWindow" style="width: 200px;margin-left: 400px;top:15%" id="jqModeljiechu">
            <form name="tojiechuForm" id="tojiechuForm" method="post">
            	<input type="submit" name="submit" style="display:none"/>
                <div class="drag"> 借出信息
                    <div class="close">
                    </div>
                </div>
                <table id="table4">
                     <tr align="center">
                        <td align="right">物品名称：</td>
                        <td>
                        <input id="goodsID" name="inventoryParam.goodsID" style="display: none;"/>
                            <input id="goodsName" name="inventoryParam.goodsName" style="width: 100px" readonly="readonly"/>
                            <input id="inventoryID" name="inventoryParam.inventoryID" style="display: none;"/></td>
                            
                    </tr>
                     <tr align="center">
                        <td align="right">借出数量：</td>
                        <td><input  id="storageQuantity1" name="inventoryParam.invenQuantity" type="text" style="width: 100px"/></td>
                    </tr>
                     <tr align="center">
                        <td align="right">物品使用人： </td>
                        <td><s:select list="%{#request.stafflist}" style="width:100px"
								headerKey="" headerValue="请选择" listKey="staffID"
								name="inventoryParam.staffID" listValue="staffName" id="staffname"
								theme="simple">
							</s:select></td>
                    </tr>
                </table>
                <div align="center">
                    <input type="button" class="input-button" id="tojiechu" value=" 确定" />
                </div>
                <s:token></s:token>
            </form>
        </div>
        	<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
</body>
</html>
