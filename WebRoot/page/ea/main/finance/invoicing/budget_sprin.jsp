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
		<style type="text/css">
.table {
	border-collapse: collapse;
	border: 1px solid #000000;
	font-size: 9px;
}

.table th {
	border: 1px solid #000000;
	color: #000000;
}

.table td {
	border: 1px solid #000000;
	color: #000000;
}

body,td,th {
	font-size: 9px;
}

body {
	margin-left: 15px;
}

th,TH {
	font-size: 12px;
	border-color: #000000;
	height: 18;
}
</style>
		<script type="text/javascript">
    var basePath = "<%=basePath%>";
    var comp = "${comp}";
    var sztype= "${sztype}";
    $(function() {
        //迭代所有的td如果为空改为0.0
        $("#tb").find("td").each(function(){
           if(!$(this).text()){
             $(this).text("0.0");
             }
             
              if($(this).text()=="Na%"){
                $(this).text("0.0%");
             
           }
            
         });   
          
        
          
          
          

         
    });
</script>
	</head>
	<body>
		<table width="100%" border="1" cellspacing="0" cellpadding="0" id="tb">
			<tr>
				<th colspan="72" align="center" style="font-size: 20px"><%=session.getAttribute("organizationName")%>部门<s:if test='sztype=="s"'>收入</s:if><s:else>支出</s:else>预算完成率
				</th>
			</tr>
			<tr>
				<th rowspan="2" align="center">
					序号
				</th>
				<th width="3%" rowspan="2" align="center">
					部门/项目/产品
				</th>
				<th rowspan="2" align="center">
					责任人
				</th>
				<th colspan="4" align="center">
					一月份
				</th>
				<th colspan="4" align="center">
					二月份
				</th>
				<th colspan="4" align="center">
					三月份
				</th>
				<th colspan="4" align="center">
					第一季度
				</th>
				<th colspan="4" align="center">
					四月份
				</th>
				<th colspan="4" align="center">
					五月份
				</th>
				<th colspan="4" align="center">
					六月份
				</th>
				<th colspan="4" align="center">
					第二季度
				</th>
				<th colspan="4" align="center">
					七月份
				</th>
				<th colspan="4" align="center">
					八月份
				</th>
				<th colspan="4" align="center">
					九月份
				</th>
				<th colspan="4" align="center">
					第三季度
				</th>
				<th colspan="4" align="center">
					十月份
				</th>
				<th colspan="4" align="center">
					十一月份
				</th>
				<th colspan="4" align="center">
					十二月份
				</th>
				<th colspan="4" align="center">
					第四季度
				</th>
				<th colspan="4" align="center">
					全年
				</th>
			</tr>
			<tr>
			<c:forEach var="x"  begin="1" end="17" step="1" >
			    <th width="2%">
					预算金额
				</th>
				<th width="2%">
					调整金额
				</th>
				<th width="2%">
					实际金额
				</th>
				<th width="2%">
					预算完成率
				</th>
			</c:forEach>
				
			</tr>
			<tr>
				<td>
					&nbsp;
				</td>
				<td><%=session.getAttribute("organizationName")%></td>
				<td>
					&nbsp;
				</td>
				<c:forEach var="x"  begin="1" end="12" step="1" >
			
				    <td id="b">${ballmonth[x]}</td>
				    <td id="t">${tallmonth[x]}</td>
				    <td class="fact">${mapfact[x]}</td>
				    <td><font color="#FFCCEE">${fn:substring(mapfact[x]/ballmonth[x]*100,0,fn:indexOf(mapfact[x]/ballmonth[x]*100,'.')+3)}%</font></td>
                    <c:choose>
						<c:when test='${x == 3}'><td>${btallmonth[1]}</td><td>${btallmonth[6]}</td><td>${factseasonm[1]}</td><td>${fn:substring(factseasonm[1]/btallmonth[1]*100,0,fn:indexOf(factseasonm[1]/btallmonth[1]*100,'.')+3)}%</td></c:when>
						<c:when test='${x == 6}'><td>${btallmonth[2]}</td><td>${btallmonth[7]}</td><td>${factseasonm[2]}</td><td>${fn:substring(factseasonm[2]/btallmonth[2]*100,0,fn:indexOf(factseasonm[2]/btallmonth[2]*100,'.')+3)}%</td></c:when>
						<c:when test='${x == 9}'><td>${btallmonth[3]}</td><td>${btallmonth[8]}</td><td>${factseasonm[3]}</td><td>${fn:substring(factseasonm[3]/btallmonth[3]*100,0,fn:indexOf(factseasonm[3]/btallmonth[3]*100,'.')+3)}%</td></c:when>
						<c:when test='${x == 12}'><td>${btallmonth[4]}</td><td>${btallmonth[9]}</td><td>${factseasonm[4]}</td><td>${fn:substring(factseasonm[4]/btallmonth[4]*100,0,fn:indexOf(factseasonm[4]/btallmonth[4]*100,'.')+3)}%</td></c:when>				
					</c:choose>
                 </c:forEach>
                    <td class="bb">${btallmonth[5]}</td>
				    <td class="tt">${btallmonth[10]}</td>
				    <td>${factseasonm[5]}</td>
				    <td>${fn:substring(factseasonm[5]/btallmonth[5]*100,0,fn:indexOf(factseasonm[5]/btallmonth[5]*100,'.')+3)}%</td>
				
			</tr>
				
			<c:forEach var='earnBudgetBills' items="${earnBudgetBillList}" varStatus="list1">
			<tr bgcolor="#CCCCCC" class="x">
				<td>${list1.count}</td>
				<td>${earnBudgetBills.budgetName}</td>
				<td>${earnBudgetBills.staffName}</td>
				
				
				 <c:forEach var="x"  begin="1" end="12" step="1" >
				<td class="b">${budgetofxm[earnBudgetBills.ebbID][x]}</td>
				<td class="t">${tiaotofxm[earnBudgetBills.ebbID][x]}</td>
				<td class="fact">${fxm[earnBudgetBills.ebbID][x]}</td>
                <td>${fn:substring(fxm[earnBudgetBills.ebbID][x]/budgetofxm[earnBudgetBills.ebbID][x]*100,0,fn:indexOf(fxm[earnBudgetBills.ebbID][x]/budgetofxm[earnBudgetBills.ebbID][x]*100,'.')+3)}%</td>
                
                    <c:choose>
						<c:when test='${x == 3}'><td>${seasonxmap[earnBudgetBills.ebbID][1][1]}</td><td>${seasonxmap[earnBudgetBills.ebbID][1][2]}</td><td>${seasonxmap[earnBudgetBills.ebbID][1][3]}</td><td>${fn:substring(seasonxmap[earnBudgetBills.ebbID][1][3]/seasonxmap[earnBudgetBills.ebbID][1][1]*100,0,fn:indexOf(seasonxmap[earnBudgetBills.ebbID][1][3]/seasonxmap[earnBudgetBills.ebbID][1][1]*100,'.')+3)}%</td></c:when>
						<c:when test='${x == 6}'><td>${seasonxmap[earnBudgetBills.ebbID][2][1]}</td><td>${seasonxmap[earnBudgetBills.ebbID][2][2]}</td><td>${seasonxmap[earnBudgetBills.ebbID][2][3]}</td><td>${fn:substring(seasonxmap[earnBudgetBills.ebbID][2][3]/seasonxmap[earnBudgetBills.ebbID][2][1]*100,0,fn:indexOf(seasonxmap[earnBudgetBills.ebbID][2][3]/seasonxmap[earnBudgetBills.ebbID][2][1]*100,'.')+3)}%</td></c:when>
						<c:when test='${x == 9}'><td>${seasonxmap[earnBudgetBills.ebbID][3][1]}</td><td>${seasonxmap[earnBudgetBills.ebbID][3][2]}</td><td>${seasonxmap[earnBudgetBills.ebbID][3][3]}</td><td>${fn:substring(seasonxmap[earnBudgetBills.ebbID][3][3]/seasonxmap[earnBudgetBills.ebbID][3][1]*100,0,fn:indexOf(seasonxmap[earnBudgetBills.ebbID][3][3]/seasonxmap[earnBudgetBills.ebbID][3][1]*100,'.')+3)}%</td></c:when>
						<c:when test='${x == 12}'><td>${seasonxmap[earnBudgetBills.ebbID][4][1]}</td><td>${seasonxmap[earnBudgetBills.ebbID][4][2]}</td><td>${seasonxmap[earnBudgetBills.ebbID][4][3]}</td><td>${fn:substring(seasonxmap[earnBudgetBills.ebbID][4][3]/seasonxmap[earnBudgetBills.ebbID][4][1]*100,0,fn:indexOf(seasonxmap[earnBudgetBills.ebbID][4][3]/seasonxmap[earnBudgetBills.ebbID][4][1]*100,'.')+3)}%</td></c:when>				
					</c:choose>
                </c:forEach>
                <td class="bb">${seasonxmap[earnBudgetBills.ebbID][5][1]}</td>
				<td class="tt">${seasonxmap[earnBudgetBills.ebbID][5][2]}</td>
				<td>${seasonxmap[earnBudgetBills.ebbID][5][3]};</td>
                <td>${fn:substring(seasonxmap[earnBudgetBills.ebbID][5][3]/seasonxmap[earnBudgetBills.ebbID][5][1]*100,0,fn:indexOf(seasonxmap[earnBudgetBills.ebbID][5][3]/seasonxmap[earnBudgetBills.ebbID][5][1]*100,'.')+3)}%</td>
                
			</tr>
			
			<c:forEach var='earnBudgetDetails' items="${productmap[earnBudgetBills.ebbID]}" varStatus="list">
			<c:if test="${earnBudgetBills.ebbID eq earnBudgetDetails[2]}">
			    <tr class="p">
				   <td>(${list.count})</td>
				   <td>${earnBudgetDetails[1]}</td>
				   <td>${earnBudgetBills.staffName}</td>
				   
				   
				   <c:forEach var="x"  begin="1" end="12" step="1" > 
				   
				   <td class="b">${detailmap[earnBudgetBills.ebbID][earnBudgetDetails[0]][x][1]}</td>
				   <td class="t">${detailmap[earnBudgetBills.ebbID][earnBudgetDetails[0]][x][2]}</td>
				   <td><font color="red">${factmap[earnBudgetBills.ebbID][earnBudgetDetails[0]][x]}</font></td>
				   <td><font color="blue">${fn:substring(factmap[earnBudgetBills.ebbID][earnBudgetDetails[0]][x]/detailmap[earnBudgetBills.ebbID][earnBudgetDetails[0]][x][1]*100,0,fn:indexOf(factmap[earnBudgetBills.ebbID][earnBudgetDetails[0]][x]/detailmap[earnBudgetBills.ebbID][earnBudgetDetails[0]][x][1]*100,'.')+3)}%</font></td>
				   <c:choose>
						<c:when test='${x == 3}'><td>${seasonmap[earnBudgetBills.ebbID][earnBudgetDetails[0]][1][1]}</td><td>${seasonmap[earnBudgetBills.ebbID][earnBudgetDetails[0]][1][2]}</td><td><font color="green">${seasonmap[earnBudgetBills.ebbID][earnBudgetDetails[0]][1][3]}</font></td><td>${fn:substring(seasonmap[earnBudgetBills.ebbID][earnBudgetDetails[0]][1][3]/seasonmap[earnBudgetBills.ebbID][earnBudgetDetails[0]][1][1]*100,0,fn:indexOf(seasonmap[earnBudgetBills.ebbID][earnBudgetDetails[0]][1][3]/seasonmap[earnBudgetBills.ebbID][earnBudgetDetails[0]][1][1]*100,'.')+3)}%</td></c:when>
						<c:when test='${x == 6}'><td>${seasonmap[earnBudgetBills.ebbID][earnBudgetDetails[0]][2][1]}</td><td>${seasonmap[earnBudgetBills.ebbID][earnBudgetDetails[0]][2][2]}</td><td><font color="green">${seasonmap[earnBudgetBills.ebbID][earnBudgetDetails[0]][2][3]}</font></td><td>${fn:substring(seasonmap[earnBudgetBills.ebbID][earnBudgetDetails[0]][2][3]/seasonmap[earnBudgetBills.ebbID][earnBudgetDetails[0]][2][1]*100,0,fn:indexOf(seasonmap[earnBudgetBills.ebbID][earnBudgetDetails[0]][2][3]/seasonmap[earnBudgetBills.ebbID][earnBudgetDetails[0]][2][1]*100,'.')+3)}%</td></c:when>
						<c:when test='${x == 9}'><td>${seasonmap[earnBudgetBills.ebbID][earnBudgetDetails[0]][3][1]}</td><td>${seasonmap[earnBudgetBills.ebbID][earnBudgetDetails[0]][3][2]}</td><td><font color="green">${seasonmap[earnBudgetBills.ebbID][earnBudgetDetails[0]][3][3]}</font></td><td>${fn:substring(seasonmap[earnBudgetBills.ebbID][earnBudgetDetails[0]][3][3]/seasonmap[earnBudgetBills.ebbID][earnBudgetDetails[0]][3][1]*100,0,fn:indexOf(seasonmap[earnBudgetBills.ebbID][earnBudgetDetails[0]][3][3]/seasonmap[earnBudgetBills.ebbID][earnBudgetDetails[0]][3][1]*100,'.')+3)}%</td></c:when>
						<c:when test='${x == 12}'><td>${seasonmap[earnBudgetBills.ebbID][earnBudgetDetails[0]][4][1]}</td><td>${seasonmap[earnBudgetBills.ebbID][earnBudgetDetails[0]][4][2]}</td><td><font color="green">${seasonmap[earnBudgetBills.ebbID][earnBudgetDetails[0]][4][3]}</font></td><td>${fn:substring(seasonmap[earnBudgetBills.ebbID][earnBudgetDetails[0]][4][3]/seasonmap[earnBudgetBills.ebbID][earnBudgetDetails[0]][4][1]*100,0,fn:indexOf(seasonmap[earnBudgetBills.ebbID][earnBudgetDetails[0]][4][3]/seasonmap[earnBudgetBills.ebbID][earnBudgetDetails[0]][4][1]*100,'.')+3)}%</td></c:when>				
					</c:choose>
			
				   </c:forEach>
				  <td>${seasonmap[earnBudgetBills.ebbID][earnBudgetDetails[0]][5][1]}</td>
				  <td>${seasonmap[earnBudgetBills.ebbID][earnBudgetDetails[0]][5][2]}</td>
				  <td><font color="green">${seasonmap[earnBudgetBills.ebbID][earnBudgetDetails[0]][5][3]}</font></td>
				  <td>${fn:substring(seasonmap[earnBudgetBills.ebbID][earnBudgetDetails[0]][5][3]/seasonmap[earnBudgetBills.ebbID][earnBudgetDetails[0]][5][1]*100,0,fn:indexOf(seasonmap[earnBudgetBills.ebbID][earnBudgetDetails[0]][5][3]/seasonmap[earnBudgetBills.ebbID][earnBudgetDetails[0]][5][1]*100,'.')+3)}%</td>
				  
			</tr>
			</c:if>
			
			
			</c:forEach>
			
			</c:forEach>
				
		</table>
	</body>
</html>
