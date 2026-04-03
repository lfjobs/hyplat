<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>科目余额表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
 <%@ page language="java" pageEncoding="UTF-8" %>
        <%@ taglib uri="/struts-tags" prefix="s" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
         <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
        <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
        + request.getServerName() + ":" + request.getServerPort()
        + path + "/"; %>
        <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
        <script language="javascript" type="text/javascript" src="<%=basePath%>js/ea/finance/company/cashierbillsprint.js"></script>
<style type="text/css">
.table {
	border-collapse:collapse;
	border:1px solid #000000;
	font-size:9px;
	 
}

.table th {
	border:1px solid #000000;
	color:#000000;
}
.table td {
	border:1px solid #000000;
	color:#000000;
}
body,td,th {
	font-size: 9px;
}
body {
	margin-left: 15px;
}
#apDiv1 {
	position:absolute;
	left:507px;
	top:287px;
	width:63px;
	height:32px;
	z-index:1;
}
</style>
 <script type="text/javascript">
    var  st = "${cashierBillsVO.status}";
    var basePath = "<%=basePath%>";
    var bjz=0.0;
    var bdz=0.0;
    var jlz=0.0;
    var dlz=0.0;
    var qcz=0.0;
    var qmz=0.0;
    var cfx="";
</script>
 </head>
<body>
<div align="center">
<table style='width:90%; text-align:center' align="center" >
<tr>
<td colspan="2" style="height:50px; font-size: 20px;font-weight:bold;">科目余额表</td>
</tr>
<tr  >
<td style="height:20px" align="left">期间：${sdate}</td>
<td></td>
</tr>
<tr>
<td style="height:20px" align="left">币种：本币</td>
<td></td>
</tr>
</table>
<table style='width:90%; text-align:center' align="center" class="table">
  <tr>
    <th width="93" rowspan="2">科目编码</th>
    <th width="200" rowspan="2">科目名称</th>
    <th width="15" rowspan="2">方<br />向</th>
    <th width="112">期初余额</th>
    <th width="112">本期借方</th>
    <th width="112">本期贷方</th>
    <th width="112">借方累计</th>
    <th width="112">贷方累计</th>
    <th width="15" rowspan="2">方<br />向</th>
    <th width="112">期末余额</th>
  </tr>
  <tr>
    <th>本币</th>
    <th>本币</th>
    <th>本币</th>
    <th>本币</th>
    <th>本币</th>
    <th>本币</th>
  </tr>
  <s:iterator value="report">
  <tr id='${subjectsID }'>
    <td align="left">${subjectsNumbers }</td>
    <td align="left">${subjectsName }</td>
    <td>${sdirection }</td>
    <td>${startCash }</td>
    <td>${cloan }</td>
    <td>${cforLoan}</td>
    <td><span id="jie"></span></td>
    <td><span id="dai"></span></td>
    <td>${edirection }</td>
    <td>${endCash }</td>
  </tr>
  <script>
  var id="${subjectsID }";
  var num="${subjectsNumbers}";
  var fx="${sdirection }";
  var qc=parseFloat("${startCash }");
  var bj="${cloan }";
  var bd="${cforLoan }";
  var jl="";
  var dl="";
  if(bd==""){
  	bd=parseFloat("0.0");
  }
  if(bd==""){
  	bd=parseFloat("0.0");
  }
  if(fx=="借"){
  	jl=parseFloat(qc) +parseFloat(bj);
  	dl=parseFloat(bd);
  }
  if(fx=="贷"){
  	jl=parseFloat(bj);
  	dl=parseFloat(qc) +parseFloat(bd);
  	qc=parseFloat("-"+qc);
  }
  if(fx=="平"){
  	jl=parseFloat(bj);
  	dl=parseFloat(bd);
  }
  $("span#jie",$("tr#"+id)).text(parseInt(Math.round(jl*100))/100);
  $("span#dai",$("tr#"+id)).text(parseInt(Math.round(dl*100))/100);
  if(num.toString().length==4){
  bjz=bjz+parseFloat(bj);
  bdz=bdz+parseFloat(bd);
  jlz=jlz+parseFloat(jl);
  dlz=dlz+parseFloat(dl);
  qcz=Math.round((qcz+parseFloat(qc))*100)/100;
  }
  </script>
 </s:iterator>
  <tr>
    <td align="left">总计</td>
    <td align="left"></td>
    <td><span id="cfx"></span></td>
    <td><span id="qcz"></span></td>
    <td><span id="bjz"></span></td>
    <td><span id="bdz"></span></td>
    <td><span id="jlz"></span></td>
    <td><span id="dlz"></span></td>
    <td><span id="mfx"></span></td>
    <td><span id="qmz"></span></td>
    
  </tr>
  <script>
  qmz=parseInt(Math.round((parseFloat(qcz)+parseFloat(bjz)-parseFloat(bdz))*100))/100;
  
  if((String)(qcz).substring(0, 1)=="-"){
  	$("span#cfx").text("贷");
  	$("span#qcz").text((String)(qcz).substring(1));
  }else if(qcz==0){
  	$("span#cfx").text("平");
  	$("span#qcz").text(qcz);
  }else{
  	$("span#cfx").text("借");
  	$("span#qcz").text(qcz);
  }
  if((String)(qmz).substring(0, 1)=="-"){
  	$("span#mfx").text("贷");
  	$("span#qmz").text((String)(qmz).substring(1));
  }else if(qmz==0){
  	$("span#mfx").text("平");
  	$("span#qmz").text(qmz);
  }else{
  	$("span#mfx").text("借");
  	$("span#qmz").text(qmz);
  }
  $("span#bjz").text(parseInt(Math.round(bjz*100))/100);
  $("span#bdz").text(parseInt(Math.round(bdz*100))/100);
  $("span#jlz").text(parseInt(Math.round(jlz*100))/100);
  $("span#dlz").text(parseInt(Math.round(dlz*100))/100);
  </script>
</table>
</div>
</body>
</html>
