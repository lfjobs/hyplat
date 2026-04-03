<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>部门预算完成率报表</title>
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
    var comp = "${comp}";
    var sztype= "${sztype}";
    $(function() {
  
        
        
        $("tr[id]").each(function(){
            var yeary = 0;
            var yeart = 0;
            var years = 0;
        	var i=4;
        	while(i<14){
        		var num = 0;
        		var numt = 0;
        		var nums = 0;
 
        		for(var j=3;j>0;j--){
        			num+=Number($(this).find(".b"+(i-j)).text());
        			numt+=Number($(this).find(".t"+(i-j)).text());
        			nums+=Number($(this).find(".s"+(i-j)).text());
        		}
        		$(this).find(".sy"+i).text(num);
        		$(this).find(".st"+i).text(numt);
        		$(this).find(".ss"+i).text(nums);
        		yeary+=num;
        		yeart+=numt;
        		years+=nums;
        		
        		i=i+3;
        	}
        	//全年
        	$(this).find(".ny").text(yeary);
        	$(this).find(".nt").text(yeart);
        	$(this).find(".ns").text(years);
        	
        	
        });
        
        //汇总部门
       $(".h").each(function(){
    	  var index = $(this).index(); 
    	  var y=0;
    	  $("tr[id]").each(function(){
    	      y+=Number($(this).find("td:eq("+index+")").text());
    	  });
    	  
    	  $(this).text(y);
    	 
       });
        
        //完成率
        
        $(".r").each(function(){
        	var y = Number($(this).prev().prev().prev().text());
        	var t = Number($(this).prev().prev().text());
        	var s = Number($(this).prev().text());
        	var rate = 0;
        	if(t!=0){
        		rate= round(s/t*100,4);
        	}
        	
        	$(this).text(rate+"%");
        	
        	 	
        });
        
         
    });
    
    function round(v,e){
    	var t=1;
    	for(;e>0;t*=10,e--);
    	for(;e<0;t/=10,e++);
    	return Math.round(v*t)/t;
    	}
    
    
    function getstat(){
    	var year = $("#year").val();
    	$("#year").blur();
    	document.location.href=basePath+"ea/costsheet/ea_budgetComplete.jspa?year="+year;
    	
    }
</script>
	</head>
	<body>
    <div style="margin-bottom:2px;font-size:15px;">计划预算年份：<input style="width:90px;" type="text"  id="year"  value="${year}" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy',onpicked:function(dp){getstat(); return false;}})" class="Wdate"/>年</div>
		<table  style="width:100%;margin-right:15px;"cellspacing="0" cellpadding="10" id="tb" class="table">
			<tr>
				<th colspan="74" align="center" style="font-size: 20px">
				
				${year}年<%=session.getAttribute("organizationName")%>部门目标任务完成汇总表
				
				</th>
			</tr>
			<tr>
				<th class="nobr" rowspan="2"   align="center" width="10%">
					序号
				</th>
				<th  class="nobr"  rowspan="2"  align="center" width="8%">
					目标部门
				</th>
				<th class="nobr"  rowspan="2"  align="center" width="4%">
					目标业务员
				</th>
				<%--<th class="nobr"  rowspan="2" align="center" width="5%">
					生产设备
				</th>
			    --%><th class="nobr"  rowspan="2"   align="center" width="5%">
					产品名称
				</th>
				<th colspan="4" align="center">
					一月任务
				</th>
				<th colspan="4" align="center">
					二月任务
				</th>
				<th colspan="4" align="center">
					三月任务
				</th>
				<th colspan="4" align="center">
					第一季度
				</th>
				<th colspan="4" align="center">
					四月任务
				</th>
				<th colspan="4" align="center">
					五月任务
				</th>
				<th colspan="4" align="center">
					六月任务
				</th>
				<th colspan="4" align="center">
					第二季度
				</th>
				<th colspan="4" align="center">
					七月任务
				</th>
				<th colspan="4" align="center">
					八月任务
				</th>
				<th colspan="4" align="center">
					九月任务
				</th>
				<th colspan="4" align="center">
					第三季度
				</th>
				<th colspan="4" align="center">
					十月任务
				</th>
				<th colspan="4" align="center">
					十一任务
				</th>
				<th colspan="4" align="center">
					十二任务
				</th>
				<th colspan="4" align="center">
					第四季度
				</th>
				<th colspan="4" align="center">
					全年任务
				</th>
				<th class="nobr"  rowspan="2"  align="center">
					备注(单位：元)
				</th>
			</tr>
			<tr style="background:#f2dddc;">
			<c:forEach var="x"  begin="1" end="17" step="1" >
			    <th width="4%">
					计划金额
				</th>
				<th width="4%">
					调整金额
				</th>
				<th width="4%">
					完成金额
				</th>
				<th width="4%">
					完成效率
				</th>
			</c:forEach>
			
				
			</tr>
		<tr>
			<td align="center">(一)</td>
			<td align="center"><%=session.getAttribute("organizationName")%></td>
			<td align="center">&nbsp;</td>
			<%--<td align="center">&nbsp;</td>
			--%><td align="center">&nbsp;</td>
			<c:forEach var="x"  begin="1" end="17" step="1">
			    <td class="h" align="center">&nbsp;</td>
				<td class="h" align="center">&nbsp;</td>
				<td class="h s" align="center">&nbsp;</td>
				<td class="r" align="center">&nbsp;</td>
			</c:forEach>
			<td align="center">&nbsp;</td>
		</tr>

		<c:forEach items="${goodslist}" var="goods" varStatus="status">
			<tr id="${goods.goodsBillsID}">
				<td align="center">${status.count}</td>
				<td align="center">${goods.targetDeptName}</td>
				<td align="center">${goods.targetSalerName}</td>
				<%--<td align="center">生产设备</td>
				--%><td align="center">${goods.goodsName}</td>
			
			   <c:forEach var="x"  begin="1" end="3" step="1">
			
				<td class="b${x}" align="center">${detailmap[goods.goodsBillsID][year][x][1]}</td>
				<td class="t${x}" align="center">${detailmap[goods.goodsBillsID][year][x][2]}</td>
				<td class="s${x}" align="center">${detailmap[goods.goodsBillsID][year][x][3]}</td>
				<td class="r">&nbsp;</td>
				</c:forEach>
				
				<td class="sy4" align="center"></td>
				<td class="st4" align="center"></td>
				<td class="ss4" align="center">&nbsp;</td>
				<td class="r" align="center">&nbsp;</td>
				
				<c:forEach var="x"  begin="4" end="6" step="1">
			
				<td class="b${x}" align="center">${detailmap[goods.goodsBillsID][year][x][1]}</td>
				<td class="t${x}" align="center">${detailmap[goods.goodsBillsID][year][x][2]}</td>
				<td class="s${x}" align="center">${detailmap[goods.goodsBillsID][year][x][3]}</td>
				<td class="r" align="center">&nbsp;</td>
				</c:forEach>
				
				<td class="sy7" align="center"></td>
				<td class="st7" align="center"></td>
				<td class="ss7" align="center">&nbsp;</td>
				<td class="r" align="center">&nbsp;</td>
				
				
				<c:forEach var="x"  begin="7" end="9" step="1">
			
				<td class="b${x}" align="center">${detailmap[goods.goodsBillsID][year][x][1]}</td>
				<td class="t${x}" align="center">${detailmap[goods.goodsBillsID][year][x][2]}</td>
				<td class="s${x}" align="center">${detailmap[goods.goodsBillsID][year][x][3]}</td>
				<td class="r" align="center">&nbsp;</td>
				</c:forEach>
				
				<td class="sy10" align="center"></td>
				<td class="st10" align="center"></td>
				<td class="ss10" align="center">&nbsp;</td>
				<td class="r" align="center">&nbsp;</td>
				
				<c:forEach var="x"  begin="10" end="12" step="1">
			
				<td class="b${x}" align="center">${detailmap[goods.goodsBillsID][year][x][1]}</td>
				<td class="t${x}" align="center">${detailmap[goods.goodsBillsID][year][x][2]}</td>
				<td class="s${x}" align="center">${detailmap[goods.goodsBillsID][year][x][3]}</td>
				<td class="r" align="center">&nbsp;</td>
				</c:forEach>
				<td class="sy13" align="center"></td>
				<td class="st13" align="center"></td>
				<td class="ss13" align="center">&nbsp;</td>
				<td class="r" align="center">&nbsp;</td>
				
				<td class="ny" align="center"></td>
				<td class="nt" align="center"></td>
				<td class="ns" align="center">&nbsp;</td>
				<td class="r" align="center">&nbsp;</td>
				<%-- 备注--%>
				<td align="center">&nbsp;</td>
				
			</tr>
		</c:forEach>
		
				
		</table>
	</body>
</html>
