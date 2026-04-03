<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>项目收支余预算表</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
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
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<style type="text/css">
.table {
	border-collapse:collapse;
	border:1px solid #000000;
	font-size:12px;
}

.table th {
	border:1px solid #000000;
	color:#333;
	
}
.table td {
	border:1px solid #000000;
	color:#333;
}




th,TH {
	font-size: 12px;
	border-color: #000000;
	height: 18;
}

.nobr{

      white-space: nowrap;
   
}
</style>
		<script type="text/javascript">
    var basePath = "<%=basePath%>";
    var yk = "${param.yk}";
    
    $(function() {
  
    	if(yk=="y"){
        	$(".ykhid").hide();
        	$(document).attr("title","项目收支余预算表");//修改title值
        }else{
        	$(".ykhid").show();
        	$(".yktitle").text("盈亏");
        	$(document).attr("title","项目收支余预算盈亏表");//修改title值
        }

        
        $(".hj").each(function(){
        	  
        	  var s=0;
        	  var z=0;
        	  var y=0;
        	  $(this).find(".hs").parent().parent().find("td.s").each(function(){
        		 
        	      s+=Number($(this).text());
        	  });
        	  
        	 
        	  
        	  $(this).find(".hz").parent().parent().find("td.z").each(function(){
         		 
        	      z+=Number($(this).text());
        	  });
        	  
        	  $(this).find(".hy").parent().parent().find("td.y").each(function(){
         		 
        	      y+=Number($(this).text());
        	  });
        	 
        	  $(this).find(".hs").text(s);
        	  $(this).find(".hz").text(z);
        	  $(this).find(".hy").text(y);
        	 var ye = Number($(this).find(".hy").text());
        	  if(ye>0){
        		  
        		  $(this).find(".yk").text("盈");
        	  }else if(ye==0){
        		  $(this).find(".yk").text("平");
        	  }else{
            		  $(this).find(".yk").text("亏");
            	 
        	  }
        	
        });
        
        
    
        
     });
    
    function getstat(){
    	var year = $("#year").val();
    	$("#year").blur();
    	document.location.href=basePath+"ea/costsheet/ea_budgetComplete.jspa?year="+year;
    	
    }
</script>
	</head><%--
	<body>
    <div style="margin-bottom:2px;font-size:15px;">计划预算年份：<input style="width:90px;" type="text"  id="year"  value="${year}" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy',onpicked:function(dp){getstat(); return false;}})" class="Wdate"/>年
    
    
    </div>
    --%><div style="margin-left:30%;margin-bottom:10px;font-weight: bold"><%--
    ${year}年
    --%><%=session.getAttribute("organizationName")%>项目收支余<span class="yktitle"></span>预算表
    
    </div>
		
		<c:forEach items="${list}" var="project">
		</br>
		<table  style="width:70%;margin-right:15px;"cellspacing="0" cellpadding="10" id="tb" class="table">

			<tr><td align="left" colspan="6">(${project.goodsNum})${project.goodsName}</td></tr>
			<tr>
			    
				<th class="nobr"  align="center" width="50px">
					序号
				</th>
				<th  class="nobr" align="center" width="130px">
					单据凭证号
				</th>
				<th class="nobr"  align="center" >
					收入金额
				</th>
				<th class="nobr"  align="center" >
					支出金额
				</th>
			    <th class="nobr"    align="center" >
				   余额
				</th>
				<th class="ykhid" align="center">
					盈亏
				</th>
				
				
			</tr>
			<c:forEach items="${mapszy[project.ppID]}" var="cashier" varStatus="status">
			
		   <tr>
		       <td align="center" >${status.count}</td>
		       <td align="center" >${cashier.journalNum}</td>
		       <td align="center"  class="s"><c:if test='${cashier.billsType=="项目收入预算单"}'>${cashier.accountNum}</c:if></td>
		       <td align="center"  class="z"><c:if test='${cashier.billsType=="项目支出预算单"}'>${cashier.accountNum}</c:if></td>
		       <td align="center"  class="y"><c:if test='${cashier.billsType=="项目支出预算单"}'>-</c:if>${cashier.accountNum}</td>
		       <td align="center"  class="ykhid">&nbsp;</td>
			</tr>
			</c:forEach>
			<tr class="hj">
				<td align="center" >&nbsp;</td>
				<td align="center" >合计</td>
				<td align="center"  class="hs"></td>
				<td align="center"  class="hz"></td>
				<td align="center"  class="hy"></td>
				<td align="center"  class="yk ykhid">盈</td>
				
			</tr>

		</table>
		</c:forEach>
	</body>
</html>
