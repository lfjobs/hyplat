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
<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/admin_main111.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>

<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<script src="<%=basePath%>js/ea/validate.js"  type="text/javascript"></script>
<script src="<%=basePath%>js/ea/finance/invoicing/InventoryManagement.js"></script>
<link rel="stylesheet" href="<%=basePath%>js/tree/common/css/style.css" type="text/css" media="screen" />
<script  src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js"></script>
<script  src="<%=basePath%>js/tree/codebase/dhtmlxtree.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/tree/codebase/dhtmlxtree.css">
<style type="text/css">
.xx{
	color:#FF0000;
	margin-right:2px;}
.xx1{
	color:#3300CC;
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
   var seedID='';
   var tupian='01';
   var nums='';
   $(document).ready(function() {  
                //change事件加载下一级内容
                $("select.warehouse").change(function(){
                		if(notoken){
							alert("正在加载数据")
							return;
						  }
						notoken=1;
                		if($.trim($(this).attr("title"))=='4'){
                		notoken=0;
                		return;
                		}
                		var aryParam=$.trim($(this).attr("title"));
                		var wareID=$.trim($(this).children("option:selected").val());
                		 var url =  basePath+"/ea/warehouse/sajax_ea_getListWareHouseZiAjax.jspa?warehouse.wareID="+wareID+"&warehouse.wareType="+aryParam+"&date="+new Date();
       							 $.ajax({
						                        url: encodeURI(url),
						                        type: "get",
						                        async: false,
						                        dataType: "json",
						                        success: function cbf(data){
						                         var member = eval("(" + data + ")");
						                         var nologin = member.nologin;
								                  if(nologin){
								                  document.location.href =basePath+"page/ea/not_login.jsp";
								                  }
											      var wareHouseList = member.wareHouseList;
											        var se="";
											      if(wareHouseList != null&&wareHouseList.length>0){
												      for(var i=0;i<wareHouseList.length;i++){
												    		var op="<option value='"+wareHouseList[i].wareID+"'>"+wareHouseList[i].wareName+"</option>";
												      		se+=op;
												      }
												      var xe=parseInt(aryParam)+1;
												      $("select[title='"+ xe +"'] option").remove();
												      $("select[title='"+ xe +"']").append("<option value=''>无</option>");
											          $("select[title='"+ xe +"']").append(se);
											      }else{
											      	 var xe=parseInt(aryParam)+1;
											      	for(var i=xe;i<=4;i++){
											      	  $("select[title='"+ i +"'] option").remove();
												      $("select[title='"+ i +"']").append("<option value='' selected='selected'>无</option>");
											      	
											      	}
											      }
											       notoken=0;
						                        },
						                        error: function cbf(data){
						                           notoken=0;
						                           alert("数据获取失败！")
						                        }
						                    });
                			})
                }); 
	
</script>
<script src="<%=basePath%>js/ea/human/cstaff.js"></script>

</head>
<body>
 <!--搜索窗口 -->
<%--<div class="jqmWindow" style="width: 240px;margin-left: 400px;top:15%" id="jqModelSearch">
            <form name="postSearchForm" id="postSearchForm" method="post">
            	<input type="submit" name="submit" style="display:none"/>
                <div class="drag"> 查询信息
                    <div class="close">
                    </div>
                </div>
                <table id="cataffSearchTable" style=" margin-left:30px;">
                     <tr align="center">
                        <td>物品类别：</td>
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
  
--%><form style="display: none;" name="addressForm" id="addressForm" method="post"><s:token></s:token><input type="submit" name="submit" style="display:none"/>
</form>
  <table   class="address" >
  <thead>
	 	    <tr>
	 	    <th width="40" align="center">请选择</th>
            <th width="100" align="center" >物品名称</th>
			<th width="100" align="center" >物品类别</th>
			<th width="100" align="center" >条码</th>
			<th width="100" align="center" >库存数量</th>
			<th width="100" align="center" >单价</th>
			<th width="100" align="center" >总价</th>
			<th width="100" align="center" >物品状态</th>
			<th width="100" align="center" >单位</th>
            <th width="100" align="center" >库存预警值</th>
            <th width="100" align="center" >库房位置</th>
      </tr>
    </thead>
		<tbody>
          <c:forEach items="${pageForm.list}" var="inventory">
          <tr class="td_bg01 saveAjax" name="add" id="${inventory[0]}" >
          <td class="td_bg01">
             <input type="radio"  class="JQuerypersonvalue" value="${inventory[0]}" style="display:none" />
              <a href="#" id="${inventory[0]}" class="add">
              <img src="<%=basePath%>images/u15.png" width="16" height="16"   border="0"/>
              </a>
            </td>
            <td class="td_bg01">
                <span id="classes" class="datas">${inventory[1]}</span>
				</td>
            <td class="td_bg01">
               <span id="area" class="datas">${inventory[2]}</span>
				</td>
			 <td class="td_bg01">
             <span id="amount"></span>
           			</td>
            <td class="td_bg01">
             <span id="place">${inventory[3]}</span>
            </td>
            <td class="td_bg01">
               <span id="principal" class="datas"></span>
				</td>
            <td class="td_bg01">
             <span id="afforestDate" class="datas">${inventory[6]}</span>
			</td> 
			 <td class="td_bg01">
			 
				<span id="goodstatus" class="datas"></span>
			
			</td> 
			 <td class="td_bg01">
             <span id="afforestDate" class="datas"></span>
			</td> 
			<td class="td_bg01">
            	 <c:if test="${inventory[4] =='红色'}">
				<span id="suan" class="xx"><font size="" color="#FF0000">${inventory[3]}</font></span>
			</c:if>
			 <c:if test="${inventory[4] =='蓝色'}">
				<span id="suan" class="xx1"><font size="" color="#0033FF">${inventory[3]}</font></span>
			</c:if>
			 <c:if test="${inventory[4] =='黑色'}">
				<span id="suan" class="datas">${inventory[3]}</span>
			</c:if>
			 <c:if test="${inventory[4] ==''}">
				<span id="suan" class="datas"></span>
			</c:if>
			</td>
			<td class="td_bg01">
			</td>
          </tr>
            </c:forEach>
    </tbody>
  </table>
 <c:import url="../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/warehousing/ea_getInventoryManagementList.jspa?pageNumber=${pageNumber}&search=${search}"></c:param>
</c:import>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
<!-- 申请报损 -->
    <div class="jqmWindow" style="width: 240px;margin-left: 400px;top:15%" id="jqModel">
            <form name="postForm" id="postForm" method="post">
            	<input type="submit" name="submit" style="display:none"/>
                <div class="drag">报损情况<div class="close"></div></div>
                <table id="cataffTables" width="240px">
                    <tr align="center">
                        <td align="right">物品状态:</td>
                        <td>
                          <select name="inventoryParam.goodstatus" id="goodstatus" style="width: 145px">
			     			<option value="">请选择</option>
			    			<option value="01">维修</option>
			    			<option value="02">报废</option>
			   			 </select>
					   	</td>
                    </tr>
                     <tr align="center">
                        <td align="right">报损数量:</td>
                        <td><input  name="inventoryParam.badQuantity" id="badQuantity" style="width: 145px" type="text" /></td>
                    </tr>
                </table>
                <div align="center"> 
                    <input type="button" class="input-button" id="tosubmit" value=" 提交 " />
                </div>
            </form>
        </div>
    <!-- 设置预警值 -->
    <div class="jqmWindow" style="width: 240px;margin-left: 400px;top:15%" id="jqModel2">
            <form name="suanForm" id="suanForm" method="post">
            	<input type="submit" name="submit" style="display:none"/>
                <div class="drag">设置上下限值<div class="close"></div></div>
                <table id="cataffTables" width="240px">
                    <tr align="center">
                        <td align="right">上限值:</td>
                        <td><input  name="inventoryParam.invenOnline" id="invenOnline" type="text" class="notnull"/></td>
                    </tr>
                     <tr align="center">
                        <td align="right">下限值:</td>
                        <td><input  name="inventoryParam.invenUnderline" id="invenUnderline" type="text" class="notnull"/></td>
                    </tr>
                </table>
                <div align="center"> 
                    <input type="button" class="input-button" id="tosubmits" value=" 提交 " />
                </div>
            </form>
        </div>
    <!-- 添加设备 -->
    <div class="jqmWindow" style="width: 240px;left:200px;top:15%" id="jqModeldevice">
            <form name="deviceForm" id="deviceForm" method="post">
            	<input type="submit" name="submit" style="display:none"/>
                 <div class="drag">添加到设备<div class="close"></div></div>
                
                 <div  style="height:300px;"><iframe src="<%=basePath%>page/ea/main/finance/invoicing/oprojecttree.jsp?codeID=scode20141029WKC79JEKJT0000000022&title=设备管理"   scroll="yes" style="width:235px;"height="300"></iframe></div> 
                <div align="center"> 
                    <input type="button" class="input-button" id="confirmAdd" value=" 确定添加 " />
                    <input type="hidden" id="codeID" value="" name="codeID"/>
                </div>
            </form>
    </div>
    <!-- 打印条码 -->
    <div class="jqmWindow" style="width: 500px;margin-left: 200px;top:15%;" id="jqModel3">
            <form name="postForm" id="postForm" method="post">
            	<input type="submit" name="submit" style="display:none"/>
                <div class="drag">打印条码</div>
                <div align="center"> 
                    <input type="button" class="input-button" id="tosubmitgnum" value="打印条码" />
                    <input type="button" class="input-button" id="goodnumclose" value="关闭" />
                </div>
                <table  width="470" class='table'>
                    <tr align="center">
                        <th height='21' width="40" align="center" bgcolor='#E4F1FA'>选择</th>
                        <th align="center" width="130" bgcolor='#E4F1FA'>物品序号</th>
                        <th align="center" width="140" bgcolor='#E4F1FA'>物品名称</th>
                        <th align="center" width="140" bgcolor='#E4F1FA'>品名编号</th>
                    </tr>
                </table>
                <div id="goodsnum" style="width:490px;height:150px;overflow-y:scroll;">
                  <input type="text" id="inventoryid" style="display:none;"/>
                 <table  width="470" class='table'>
                    <tr id="goodsnum" align="center" style="display:none;">
                        <th colspan="4"></th>
                    </tr>
                 </table>
                </div>
            </form>
      </div>
</body>
</html>
