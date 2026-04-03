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
		<title>验货管理</title>
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
		<link href="<%=basePath%>/css/ea/validate.css" rel="stylesheet" type="text/css" />
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
		<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/ea/finance/invoicing/inspect_list.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/common/organizationTree.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jquery.messager.js"></script>
		<script type="text/javascript">
        	var treeID = '<%=session.getAttribute("organizationID")%>';
        	var financialbillID="";
        	var search="${search}";
        	var basePath="<%=basePath%>";
        	var pNumber=${pageNumber};
        	var token=0;
        	var pageCount='${pageForm.pageCount}';
        	var notoken = 0;
        	var inspectmap="";
        	var select=0;
        	var str='';
        	function importGY(attachSearchTable,checkopertionID,checkopertionName,childopertionName,url){ //打开页面
       		 if(checkopertionName=="bankNum"){
       		 	var departmentID =  $("#departmentID").attr("value");
       		 	url = url + "?departmentID="+departmentID;
       		 }
       		 $("#checkopertionID",$("#bankJqm")).attr("value",checkopertionID);
       		 $("#checkform",$("#bankJqm")).attr("value",attachSearchTable);
       		 $("#checkopertionName",$("#bankJqm")).attr("value",checkopertionName);
       		 $("#childopertionName",$("#bankJqm")).attr("value",childopertionName);
       	  	 $("#daoRu").attr("src",basePath+url);
       	  	 $("#bankJqm").jqmShow();
       	}
       	
       	
       		
       $(document).ready(function() {//销售单FORM
       	$("#DaoRuFan").click(function(){// 返回
              $("#bankJqm").jqmHide();
       	}); 
       	$("#DaoRuFanqd").click(function(){// 选择确定
       		var checkopertionID =$("#checkopertionID",$("#bankJqm")).attr("value");
       		var checkform =$("#checkform",$("#bankJqm")).attr("value");
       		var checkopertionName = $("#checkopertionName",$("#bankJqm")).attr("value");
       		var childopertionName = $("#childopertionName",$("#bankJqm")).attr("value");
       		var childopertionID = window.frames["daoRu"].opertionID;
       		if(childopertionID == ""){
       			alert("请选择")
       			return;
       		}
       		var no = window.frames["daoRu"].$('tr#'+childopertionID).find("span#"+checkopertionName).text();
       		var childopertionName =window.frames["daoRu"].$('tr#'+childopertionID).find("span#"+childopertionName).text();
       		if(checkopertionID != "")
       			$("#"+checkopertionID,$("#"+checkform)).attr("value",childopertionID).trigger("blur");
       		if(checkopertionName != ""){
       			$("#"+checkopertionName,$("#"+checkform)).attr("value",childopertionName).trigger("blur");
       		}
       		if(checkopertionName =="partnerName"){
       			var final = no;
       			$("#"+checkopertionName,$("#"+checkform)).attr("value",final).trigger("blur");
       		}
       		 $("#daoRu").attr("src","");
                $("#bankJqm").jqmHide();
          });
		
       });
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
        </script>
	</head>
	<body>
		<form name="Inspectform" id="Inspectform">
			<input type="submit" name="submit" style="display: none" />
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
						往来个人
					</th >
					<th align="center" width="75">
						往来单位
					</th>
					<th align="center" width="75">
						凭证号
					</th>
					<th align="center" width="50">
						单位
					</th>
					<!--  
					<th align="center" width="50">
						品牌
					</th>
					<th align="center" width="50">
						型号
					</th>
					<th align="center" width="75">
						付款方式
					</th>
					<th align="center" width="75">
						物流方式
					</th>
					<th align="center" width="140">
						提醒内容
					</th>
					-->
					
					
				</tr>
			</thead>
			<tbody>			
				<c:forEach var='arr' items="${pageForm.list}">
					<tr id="${arr[0] }" class="xggoods">
						<td>
							<input type="checkbox" name="financialgoodID" class="JQuerypersonvalue"
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
							<span id="isQualify">
							<input id="startu" value="${arr[16]}" style="display: none;"/>
							<c:if test="${arr[16]=='已收货'}">
								<input id="isQualify" name="isQualify" class="input put3" size="5" style="margin-left: 2px;" value="${arr[9]}"/>
							</c:if>
							<c:if test="${arr[16]=='入库失败'}">
								<input id="isQualify" name="isQualify" class="input put3" size="5" style="margin-left: 2px;" value="${arr[9]}"/>
							</c:if>
							<c:if test="${arr[16]=='已验货'}">
							<span>${arr[15]}</span>
							</c:if>
							</span>
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
							<span id="staffsName" >${arr[21]}</span>
						</td>
						<td>
							<span id="cstaffName" >${arr[22]}</span>
						</td>
						<td>
							<span id="ccompanyName" >${arr[23]}</span>
						</td>
						<td>
							<span id="journalNum" >${arr[18]}</span>
						</td>
						<td>
							<span id="unit" name="unit">${arr[7]}</span>
						</td>
						<!-- 
						<td>
							<span id="brand">${arr[5]}</span>
						</td>
						<td>
							<span id="modelNumber">${arr[6]}</span>
						</td>
						<td>
							<span name="paymentType">${arr[12]}</span>
						</td>
						<td>
							<span name="logisticsType">${arr[13]}</span>
						</td>
						<td>
							<span name="remindContent">${arr[14]}</span>
						</td>
						 -->
					</tr>
				</c:forEach>
			</tbody>
		</table>
		</form>
		<c:import url="../../../page_navigator.jsp">
			<c:param name="actionPath"
				value="/ea/purchase/ea_getinspectList.jspa?search=${search}&pageNumber=${pageNumber}">
			</c:param>
		</c:import>
			<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
		  <!----------------------------------------选择责任人---------------------------------------- -->
        
		 <div class="jqmWindow " style="width: 400px; right: 25%; top: 10%" id="jqModels">
            <form name="cstaffForms" id="cstaffForms" method="post" enctype="multipart/form-data">
                <input type="submit" name="submit" style="display:none"/>
                	<div class="drag">
					添加责任人
			</div>
				   <table width="99%" id="table4">	
				     <tr>
				     <td>
				     	<table  border="0" id="stafftables" align="center" cellpadding="0" cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
				     		<tr>
								<td height="40" align="right">责任人：</td>
								<td>
							     <input type="hidden" id="partnerID" name="financialBill.staffsID"
								readonly="readonly" />
							<input  class="notnull" type="text" id="partnerName"
								name="financialBill.staffsName" size="15" />
							<a href="#"
								onclick="importGY('table4','partnerID','partnerName','childPartnerName','ea/cosincumbent/ea_getStaffForCashier.jspa')">选择</a>
							     </td>
							</tr>
                       		<tr>
		                         <td height="30" colspan="5" align="center"><input name="carInformation.carKey" id="carKey" type="hidden" class="input"  size="20"/>
		                            <input name="carInformation.carID" id="carID" type="hidden" class="input"  size="20"/>
		                            <input type="button"   class="input-button JQuerySubmits" style="cursor:pointer;width:80px;" value="提交" />
		                             <input name="sub" value="${session_value}" type="hidden" /><!-- 代替token-->
		                            <input type="button"  class="input-button JQueryreturns" style="cursor:pointer;width:80px;"  value="取消" /></td>
		                    </tr>
                     </table>
				     </td>
				     </tr>
				</table>  
        	<s:token></s:token> 
        	 	  </form>
        </div>
        <!-- 搜索窗口 -->
		<div class="jqmWindow" style="width: 240px;margin-left: 400px;top:15%" id="jqModelSearch2">
            <form name="postSearchForm2" id="postSearchForm2" method="post">
            	<input type="submit" name="submit" style="display:none"/>
                <div class="drag">
                    查询窗口
                    <div class="close">
                    </div>
                </div>
                <table id="cataffSearchTable">
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
                    <tr>
						<td align="center" width="80px">
		  &nbsp;单据状态：
						</td>
						<td>
							<select id="taxstatus" style="width:140px"  name="financialBillsGood.status">
					    		<option value="01" selected="selected" >已收货</option>
					    		<option value="02">已验货</option>
					    	</select>
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
                  <input name="sub" value="${session_value}" type="hidden" /><!-- 代替token-->
                    <input type="button" class="input-button" id="tosearch" value=" 查询 " /><input name="search" value="search" type="hidden"/>
                </div>
            </form>
        </div>
        
        <div id="bankJqm" class="jqmWindow"
			style="width: 95%; height: 400px; absolute; display: none; left: 1%; top: 1%; background: #eff; overflow-x: hidden; overflow-y: auto;">
			<div style="background: #efg; margin-right: 500px;">
				<input style="display: none;" id="checkopertionID" />
				<input style="display: none;" id="checkopertionName" />
				<input style="display: none;" id="childopertionName" />
				<input style="display: none;" id="checkform" />
			</div>
			<iframe name="daoRu" id="daoRu" width="100%" height="360px"
				frameborder="0" style="overflow-x: hidden; overflow-y: auto;"></iframe>
			<div>
				<input type="button" class="input-button" id="DaoRuFanqd"
					value=" 确定 "
					style="cursor: hand; border: 0; margin-left: 400px; height: 25px; width: 60px" />
				<input type="button" class="input-button" id="DaoRuFan" value=" 关闭 "
					style="cursor: hand; border: 0; margin-left: 40px; height: 25px; width: 60px " />
			</div>
		</div>
		
	
	 
	</body>
	
</html>
