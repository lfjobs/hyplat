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
<title>进销存汇总表</title>
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
<script src="<%=basePath%>js/ea/finance/invoicing/InventoryDetail.js"></script>

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
   var cashierBillsID="";
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
   
    var kutime="<%=request.getAttribute("kutime")%>";
	var line1 = [];
	var line2 = [];
	var line3 = [];
	var maxnum = 0;
	var maxnum2 = 0;
	var title="${kutime}";//当前的年月
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
<form style="display: none;" name="addressForm" id="addressForm" method="post"><s:token></s:token><input type="submit" name="submit" style="display:none"/>
</form>
      <table class="address">
		<thead>
	 	    <tr>
	 	    <th width="40" align="center">请选择</th>
            <th width="100" align="center" >物品名称</th>
			<th width="100" align="center" >物品类别</th>
			<th width="100" align="center" >上月库存量</th>
			<th width="100" align="center" >本月入库量</th>
			<th width="100" align="center" >本月出库量</th>
			<th width="100" align="center" >当前库存量</th>
			<th width="100" align="center" >标准库存范围</th>
			<th width="100" align="center" >库存预警</th>
            <th width="100" align="center" >库存总金额</th>
      </tr>
    </thead>
		<c:forEach items="${lists}" var="are">
			<script type="text/javascript">
				var a1 = 0;
				if ("${are[1]}" != "" && "${are[2]}" != ""&& "${are[3]}" != "") {
					a1 = parseInt("${are[1]}") - parseInt("${are[2]}") -parseInt("${are[3]}");
				} else if ("${are[1]}" != "" && "${are[2]}" == "") {
					a1 = parseInt("${are[1]}") - parseInt("${are[2]}");
				}else if("${are[1]}" != ""){
					a1 = parseInt("${are[1]}");
				}
				if (maxnum2 < a1) {
					maxnum2 = a1+10;
				}
				var a2 = [ [ "${are[0]}", a1 ] ];
				line3 = $.merge(line3, a2);
			</script>
		</c:forEach>
		<c:forEach items="${pageForm.list}" var="arr">
			<script type="text/javascript">
				var wp = [ [ "${arr[2]}", "${arr[7]}" ] ];
				var jjx = [ [ "${arr[2]}", "${arr[8]}" ] ];
				var dd = [ "${arr[2]}" ];
				if (maxnum < "${arr[7]}") {
					maxnum = parseInt("${arr[7]}")+10;
				}
				line1 = $.merge(line1, wp);
				line2 = $.merge(line2, jjx);
			</script>
			<input id="cashierBillsID" style="display: none" value=""/>
			<tr id="${arr[0]}">
			<td class="td_bg01">
             <input type="radio" name="a" class="JQuerypersonvalue" value="${arr[0]}" disabled="disabled"/>
            </td>
            <td class="td_bg01">
                <span class="datas">${arr[2]}</span>
		    </td>
		    <td class="td_bg01">
                <span class="datas">${arr[3]}</span>
		    </td>
            <td class="td_bg01">
                <span class="datas">${arr[4]==null?'0':arr[4]}</span>
		    </td>
		    <td class="td_bg01">
                <span class="datas">${arr[5]==null?'0':arr[5]}</span>
		    </td>
            <td class="td_bg01">
                <span class="datas">${arr[6]==null?'0':arr[6]}</span>
		    </td>
		    <td class="td_bg01">
                <span class="datas">${arr[7]==null?'0':arr[7]}</span>
		    </td>
		    <td class="td_bg01">
                <span class="datas">(${arr[8]}----${arr[9]})</span>
		    </td>
		    <td class="td_bg01">
                <span class="datas">${arr[10]}</span>
		    </td>
		    <td class="td_bg01">
                <span class="datas">${arr[11]}</span>
		    </td>
			</tr>
		</c:forEach>
	</table>
<c:import url="../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/warehousing/ea_getInventoryDetailList.jspa?pageNumber=${pageNumber}&search=${search}"></c:param>
</c:import>
	<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
    
</body>
</html>
