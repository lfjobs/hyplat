<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
		<meta http-equiv="cache-control" content="no-cache" />
		<title>选择入库</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
		<script src="<%=basePath%>js/jquery.js" type="text/javascript">
		</script>
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js">
		</script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
		</script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js">
		</script>
		
		<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/ea/finance/invoicing/chooseWarehousing_list.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/common/organizationTree.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jquery.messager.js"></script>
		<script type="text/javascript">
        	var treeID = '<%=session.getAttribute("organizationID")%>';
        	var financialgoodid="";
        	var search="${search}";
        	var basePath="<%=basePath%>";
        	var pNumber=${pageNumber};
        	var token=0;
        	var pageCount='${pageForm.pageCount}';
        	var notoken = 0;
        	var inspectmap="";
        	var select=0;
			document.onkeydown = function(evt){//捕捉回车 
   				evt = (evt) ? evt : ((window.event) ? window.event : "") //兼容IE和Firefox获得keyBoardEvent对象
    			var key = evt.keyCode ? evt.keyCode : evt.which; //兼容IE和Firefox获得keyBoardEvent对象的键值
    			if (key == 13) { //判断是否是回车事件。
    				if($("input#journalNum").val()==''){
						return false;
    				}
        			if($("input#journalNum").val()!=''){
			        	$("#SearchForm").attr("action", basePath+"ea/dutiable/ea_toSearch.jspa?pageNumber="+pNumber+"&taxstatusDu="+taxstatusDu);
                    	document.SearchForm.submit.click();
					}
    			}
			}      
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
						                        async: true,
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
        
	</head>
	<body>
	<!-- 搜索窗口 -->
	<div class="jqmWindow" style="width: 240px;margin-left: 400px;top:15%" id="jqModelSearch2">
            <form name="postSearchForm2" id="postSearchForm2" method="post">
            	<input type="submit" name="submit" style="display:none"/>
                <div class="drag">
                    查询窗口
                    <div class="close">
                    </div>
                </div>
                <table id="cataffSearchTable" style=" margin-left:20px;">
                    <tr align="center">
                        <td >
                           物品名称:</td>
                        <td >
                        <input name="financialBillsGood.goodsName"/>
                        </td>
                    </tr>
					<tr align="center">
                        <td>
                           物品类别:</td>
                        <td>
                        <input name="financialBillsGood.type"/>
                        </td>
                    </tr>
                    <tr align="center">
                        <td>
                           品名编号:</td>
                        <td>
                        <input name="financialBillsGood.goodsNum"/>
                        </td>
                    </tr>
                </table>
                <div align="center"> 
                    <input type="button" class="input-button" id="tosearch" value=" 查询 " /><input name="search" value="search" type="hidden"/>
                </div>
            </form>
        </div>
	
	<div class="jqmWindow" style="width: 240px;margin-left: 400px;top:15%" id="jqModelSearch">
            <form name="postSearchForm" id="postSearchForm" method="post">
            	<input type="submit" name="submit" style="display:none"/>
                <div class="drag">
                    选择库房
                    <div class="close">
                    </div>
                </div>
                <table id="cataffSearchTables" width="240px">
                    <tr align="center"><%--
                        <td align="right">
                           库:</td>
                        <td ><select class="warehouse" id="pware" style="width: 100px" title="1" name="inventoryParam.warehouse">
                          <option value="" selected="selected">无</option>
                          <c:forEach items="${warehouseList}" var="warehouse">
                            <option value="${warehouse.wareID}" >${warehouse.wareName}</option>
                          </c:forEach>
                        </select></td>
                    </tr>
					<tr align="center">
                        <td align="right">
                           区:</td>
                        <td>
                           <select class="warehouse" id="parea" style="width: 100px" title="2" name="inventoryParam.area">
												<option value="" >无</option>
											</select>                        </td>
                    </tr>
                    <tr align="center">
                        <td align="right">
                           架:</td>
                        <td>
                          <select class="warehouse" id="pframe" style="width: 100px" title="3" name="inventoryParam.frame">
												<option value="" >无</option>
											</select>                        </td>
                    </tr>
                    <tr align="center">
                        <td align="right">
                           位:</td>
                        <td>
                           <select class="warehouse" id="place" style="width: 100px" title="4" name="inventoryParam.position">
												<option value="" >无</option>
											</select>     </td>
                    </tr>
                     --%>
                      <tr align="center">
                        <td align="right">入库位置:</td>
                        <td><input  id="weizhi" name="inventoryParam.weizhi" type="text" style="width: 95px"/></td>
                    </tr>
                     <tr align="center">
                        <td align="right">入库数量:</td>
                        <td><input  id="storageQuantity1" name="inventoryParam.invenQuantity" type="text" style="width: 95px"/></td>
                    </tr>
                </table>
                <div align="center"> 
                    <input type="button" class="input-button" id="tosubmit" value=" 提交 " />
                </div>
            </form>
        </div>
        
		<form name="Inspectform" id="Inspectform">
			<input type="submit" name="submit" style="display: none" />
			<input type="hidden" name="inventoryParam.weizhi" id="seat"/>
			<input type="hidden" name="inventoryParam.invenQuantity" id="storageQuantity2"/>
			 <input name="financialBillsGood.storageQuantity" id="storageQuantity" type="hidden"/>
			  <input name="financialBillsGood.financialgoodID" id="financialgoodid" type="hidden"/>
			<s:token />
		<table class="flexme11" id="tt">
			<thead>
				<tr>
						<th width="30" align="center">
						选择
					</th>
					<th align="center" width="90">
						费用或品名名称
					</th>
					<th align="center" width="50">
						类型
					</th>
					<th align="center" width="75">
						品名编号
					</th>
					<th align="center" width="90">
						统一分类条码
					</th>
					<th align="center" width="50">
						采购数量
					</th>					
					<th align="center" width="50">
						采购单价
					</th>
					<th align="center" width="50">
						采购金额
					</th>
					<th align="center" width="70">
						收货数量
					</th>
					<th align="center" width="75">
						合格数量
					</th>
					<th align="center" width="140">
						未入库数量
					</th>
					<th align="center" width="75">
						库存状态
					</th>
					<th align="center" width="75">
						物品状态
					</th>
					<th align="center" width="75">
						责任人
					</th>
					<th align="center" width="75">
						接收人
					</th>
					<th align="center" width="75">
						凭证号
					</th>
					<th align="center" width="50">
						单位
					</th>
					
				</tr>
			</thead>
			<tbody>			
				<c:forEach var='arr' items="${pageForm.list}">
					<tr id="${arr[0] }" class="xggoods">
						<td>
							<input type="radio" name="financialgoodID" class="JQuerypersonvalue"
								value="${arr[0]}" />
						</td>
						<td>
							<span id="goodsName">${arr[3]}</span>
						</td>
						<td>
							<span id="type">${arr[4]}</span>
						</td>
						<td>
							<span id="goodsNum">${arr[1]}</span>
						</td>
						<td>
							<span id="sortCode">${arr[2]}</span>
						</td>
						<td>
							<span id="quantity">${arr[8]}</span>
						</td>
						
						<td>
							<span id="unitPrice">${arr[10]}</span>
						</td>
						<td>
							<span id="amount">${arr[11]}</span>
						</td>
						<td>
							<span id="requantity">${arr[9]}</span>
						</td>
						<td>
							<span id="isQualify">${arr[15]}</span>
						</td>
						<td>
						<span id="storageQuantity">${arr[22]}</span>
						</td>
						<td>
							<span id="status" >${arr[16]}</span>
						</td>
						
						<td>
							<span id="goodstatus" >${arr[17]}</span>
						</td>
						<td>
							<span id="StaffName" >${arr[19]}</span>
						</td>
						<td>
							<span id="StaffsName" >${arr[21]}</span>
						</td>
						<td>
							<span id="journalNum" >${arr[18]}</span>
						</td>
						<td>
							<span id="unit" name="unit">${arr[7]}</span>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		</form>
		<c:import url="../../../page_navigator.jsp">
			<c:param name="actionPath"
				value="/ea/storage/ea_getChooseWarehousingList.jspa?search=${search}&pageNumber=${pageNumber}">
			</c:param>
		</c:import>

		<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
	</body>
</html>
