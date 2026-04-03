<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>佣金分配比例设计</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
	<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
	<link rel="stylesheet" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" type="text/css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
	<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
	<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css" />
	<script src="<%=basePath%>js/ea/finance/commission_list.js" type="text/javascript"></script>
	<script type="text/javascript">
		var basePath="<%=basePath%>";
		var select=0;
		var selber=0;
		var status="";
		var number=0;
		var saech=0;
		var length="${length}";
	</script>
	<style type="text/css">
		.tths{
		height: 30px;
		}
	</style>
  </head>
  
  <body>
  <div  id="main_main" class="main_main">
 	<div id="fexdiv">
 	<form id="form" name="form" action="" method="post">
	  	<table class="fexlist" id="table">
	  		<thead id="thead">
		  		<tr class="tths ttrs" id="tthps">
		  			<th width='20'>选择</th>
		  			<th width='80' align='center'>方案</th>
		  			<c:forEach items="${levelList}" var="l" varStatus="a">
		  				<th width='80' align='center' id="th${a.index}" class='levelpps' name="${l[2]}">${l[0]}</th>
		  			</c:forEach>
		  			<th width='80' class='deltes' style="display: none;" id="thsprs"><span style="color:red;">*</span><input type='text' class='putrs putbs' style="width:90%;height:100%; border:0px;background-color: #E0FFFF;" disabled='disabled'></th>
		  		</tr>
	  		</thead>
	  		<tbody class="tbody" id="tbwid"> 
	  			<c:forEach items="${dataList}" var="l">
	  				<tr class='ttrs'>
	  					<td width='20'><input type='radio' name='red' class='rad'></td>
	  					<td width='80'><span class="none spans datapps" style="float: left;">${l[0]}</span>
	  						<input type="text" class='bsName model1' id="${l[1]}" value="${l[0]}" style="width: 100%;border:0" disabled='disabled'></td>
	  					<c:forEach var="s" begin="2" end="${length-1<0?1:length-1}"  varStatus="a">
	  						<td width='80'><span class="none spans" style="float: right;">${l[s]}</span>
	  							<input type="text" class='putds model1' id='${a.index-2}' value="${l[s]}" style="width: 100%;border:0;background-color: #E0FFFF;"></td>
	  					</c:forEach>
	  					<td width='80' class='deltes tdsprs' style="display: none;"> </td>
	  				</tr>
	  			</c:forEach>
	  			<tr id="trprs" style="display: none;">
	  				<td><input type='radio' name='red'></td>
	  				<td><input type='text' class='putrs putbs' style="width:100%;height:100%;border:0px;background-color: #E0FFFF;" disabled='disabled'></td>
	  				<td><input type='text' class='putrs putds' style="width:100%;height:100%;border:0px;background-color: #E0FFFF;"></td>
	  				<c:forEach var="s" begin="1" end="${length-2<=0?2:length-2}" varStatus="a">
	  					<c:if test="${a.index==length-2}">
	  						<td class="deltes" width="80" style="background-color: rgb(213, 239, 252)"><input type='text' class='putrs putds' style="width:100%;height:100%;border:0px;background-color: rgb(213, 239, 252);margin:0px 0px;"></td>
	  					</c:if>
	  					<c:if test="${a.index!=length-2}">
	  						<td><input type='text' class='putrs putds' style="width:100%;height:100%;border:0px;background-color: #E0FFFF;"></td>
	  					</c:if>
	  				</c:forEach>
	  			</tr>
	  		</tbody>
	  	</table>
	  	<input type="submit" name="submit" id="submit" style="display: none;">
	  	<s:token></s:token>
	</form>
  	</div>
  </div> 
  			
			<!--           --------选择会员名称---------             -->
  <div id="divrs" class="jqmWindow jqmWindowcss1"  style="position: absolute; top: 20%;left:650;width: 300px;height: 380px">
		<div style="border: 2px #FCFCFC inset; height: 25px;background-color: #E8E8E8">
			<font style="position: relative;top: 3px;left: 5px;font-weight:bold;">选择会员名称</font></div>
		<div style="height: 30px;">
			<span><input type="button" id="determine" class="operation" value="确定" style="width: 45px;height: 22px;position: relative; left: 160px;top: 10px"></span>
			<span><input type="button" id="close" class="operation" value="关闭" style="width: 45px;height: 22px;position: relative; left: 180px;top: 10px"></span>
		</div>
		<hr color="#FFFFFF"/>
		<div>
			<table cellspacing="0px" border="1" bordercolor="#FFFFFF">
				<tr bgcolor="#F0F8FF" height="25px">
					<th width="40px"> </th><th width="80">编号</th><th width="280px">名称</th>
				</tr>
				<tr style="display: none;" id="trus" height="22" bgcolor="#FFF8DC">
					<td></td><td></td><td></td>
				</tr>
			</table>
		</div>
		<hr color="#FFFFFF"/>
		<div style="left:0; bottom:26; POSITION:absolute; z-index:100;"><span>
			<input type="button" id="home" class="page" value="首页" style="width: 40;height: 20;position: relative;top: 2;left: 10;">
			<input type="button" id="previous" class="page" value="上一页" style="width: 60;height: 20;position: relative;top: 2;left: 29;">
			<input type="button" id="next" class="page" value="下一页" style="width: 60;height: 20;position: relative;top: 2;left: 55;">
			<input type="button" id="shadowe" class="page" value="尾页" style="width: 40;height: 20;position: relative;top: 2;left: 74;">
		</span></div>
  </div> 
  		<iframe name="hidden" frameborder="0" noresize="noresize" border="0"
			framespacing="0" height="0"></iframe>
  </body>
</html>
