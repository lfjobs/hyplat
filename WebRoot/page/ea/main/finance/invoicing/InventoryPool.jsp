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
<title>进销存明细表</title>
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
<script src="<%=basePath%>js/ea/finance/invoicing/InventoryPool.js"></script>
<style type="text/css">
.xx{
	color:#FF0000;
	margin-right:2px;}
.xx1{
	color:##3300CC;
	margin-right:2px;}
</style>
<script  type="text/javascript">
   var cashierBillsID="";
   var select = '01';
   var afforestID = '';
   var  basePath='<%=basePath%>';           
   var  pNumber =${pageNumber};  
   var  search='${search}';
   var  token=0;
   var pageCount='${pageForm.pageCount}';
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
<%--<div class="jqmWindow" style="width: 400px; right: 30%; top: 10%" id="jqModelSearch">
            <form name="postSearchForm" id="postSearchForm" method="post">
            	<input type="submit" name="submit" style="display:none"/>
                <div class="drag"> 查询信息
                    <div class="close">
                    </div>
                </div>
                <table id="cataffSearchTable" width="396px">
                     <tr>
                        <td align="right" width="80px">物品类别：</td>
                        <td>
                           <input name="stockinvParam.goodsType" style="width: 188px"/>
                           </td>
                    </tr>
                    <tr>
						<td align="right" width="80px">
							日期：
						</td>
						<td style="width: 200px">
							<input id="sdate" name="sdate" onfocus="daytime(this);" style="width: 85px" />
							至<input id="edate" name="edate" onfocus="daytime(this);" style="width: 85px" />
						</td>
					</tr>
                    <tr>
                        <td align="right" width="80px">物品名称： </td>
                        <td>
                            <input name="stockinvParam.goodsName" style="width: 188px"/>   </td>
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
			<th width="150" align="center" >日期</th>
			<th width="100" align="center" >业务类型</th>
			<th width="100" align="center" >数量</th>
			<th width="100" align="center" >总金额</th>
      </tr>
    </thead>
		<tbody>
          <c:forEach items="${pageForm.list}" var="stoinv">  
          <tr class="td_bg01 saveAjax" id="${stoinv[0]}" >
            <td class="td_bg01">
             <input type="radio" name="a" id="cashierBillsID"  class="JQuerypersonvalue" value="${stoinv[0]}" style="display:inline" />
            </td>
            <td class="td_bg01">
                <span id="goodsname" class="datas">${stoinv[1]}</span>
				</td>
            <td class="td_bg01">
               <span id="goodstype" class="datas">${stoinv[2]}</span>
				</td>
			 <td class="td_bg01">
             <span id="intime">${stoinv[3]}</span>
           		</td>
           		 <td class="td_bg01">
             <span id="type">${stoinv[4]}</span>
           		</td>
            <td class="td_bg01">
               <span id="invenQuantity" class="datas">${stoinv[5]}</span>
				</td>
            <td class="td_bg01">
             <span id="summoney" class="datas">${stoinv[6]}</span>
			</td>
          </tr>
            </c:forEach>
    </tbody>
  </table>
 <c:import url="../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/warehousing/ea_getInventoryPoolList.jspa?pageNumber=${pageNumber}&search=${search}"></c:param>
</c:import>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
</body>
</html>
