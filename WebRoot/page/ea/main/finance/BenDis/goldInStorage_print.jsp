<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>${cashi[8]}预览页面</title>
    
    <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/admin_main111.css" />
    <script type="text/javascript">
    	var basePath="<%=basePath%>";
    	$(function(){
    		$("#date").val($("#date").val().split(" "));
    		
    		$(".JQueryprint").click(function(){
    			open(basePath+"ea/goldStock/ea_getGoldPreviewPage.jspa?type=00&status=01&utboundOrderVo.cashierbillsid="+$("#cashiId").val());
    		});
    		$(".JQueryClose").click(function(){
    			window.close();
    		});
    		$.ajax({
			url:basePath+"ea/goldStock/sajax_ea_ajaxGetUserOwnedCompany.jspa?utboundOrderVo.cashierbillsid="+$("#cashiId").val(),
		    type:"get",
		    success:function(data){
		    	var member = eval("(" + data + ")");
				var com = member.com;
				$("#company").val(com.companyName);
		    },
		});
    	});
    </script>
  </head>
  
  <body>
    <div style="width: 100%;text-align:left;border:0px solid red;">
		<table id="titleClone" width="100%" border="0" align="center" cellpadding="0"
					cellspacing="0" style="background:#dbe5f1;margin-top:1px;
					margin-bottom: 1px;border-bottom:1px solid #99bbe8;">
			<tr><td align="left" style="height:24px;">
			    <nobr>
				<input type="button" 
								class="menubtn JQueryprint" name="button" value="打印"/><input type="button" 
								class="menubtn JQueryClose" name="button2"value="关闭" /><input type="button" 
								class="menubtn grey" name="button" value="帮助" disabled="disabled"/>
				</nobr>
			</td></tr>
		</table>
	</div>
	<div>
		<div style="position: relative;top: 30px;left: 400px;width: 120px;"><font color="#15428b" style="font-weight:bold;">${cashi[8]}</font></div>
		<div style="position: relative;top: 60px;left:10px;width:900px;">
			<table>
				<tr height="30px;">
					<td><font size="2" color="#4F4F4F">单据编号：</font></td>
					<td width="247px;"><input type="text" class="inputbottom" value="${cashi[5]}" readonly="readonly" 
							style="width: 140px;"><input type="hidden" id="cashiId" value="${cashi[0]}"></td>
					<td><font size="2" color="#4F4F4F">会员类别：</font></td>
					<td width="247px;"><input type="text" class="inputbottom" value="${cashi[4]}" readonly="readonly" style="width: 140px;"></td>
					<td><font size="2" color="#4F4F4F">所属公司：</font></td>
					<td><input type="text" class="inputbottom" value="${cashi[3]}" readonly="readonly" style="width: 140px;"></td>
				</tr>
				<tr height="30px;">
					<td><font size="2" color="#4F4F4F">用户所属公司：</font></td>
					<td width="247px;"><input type="text" class="inputbottom" id="company" value="" readonly="readonly"  style="width: 140px;"></td>
					<td><font size="2" color="#4F4F4F">用户名称：</font></td>
					<td width="247px;"><input type="text" class="inputbottom" value="${cashi[1]}" readonly="readonly" style="width: 140px;"></td>
					<td><font size="2" color="#4F4F4F">用户编号：</font></td>
					<td><input type="text" class="inputbottom" value="${cashi[2]}" readonly="readonly" style="width: 140px;"></td>
				</tr>
			</table>
		</div>
		<div style="position: relative;top: 80px;left:10px;border: 1px solid #a8c7ce;width: 880px;height: 300px;">
			<table class="table">
				<thead>
					<tr height="22px;">
						<th width="40">序号</th>
						<th width="197">产品所属公司</th>
						<th width="120">产品名称</th>
						<th width="80">产品编号</th>
						<th width="70">数量</th>
						<th width="60">单价</th>
						<th width="70">金额</th>
						<th width="60">金币（佣金）</th>
						<th width="80">仓库地址</th>
						<th width="55">备注</th>
					</tr>
				</thead>
				<tbody>
						<c:forEach items="${goodsList}" var="l" varStatus="a">
							<tr>
								<td>${a.index+1}</td>
								<td>${cashi[4]}</td>
								<td>${l[0]}</td>
								<td>${l[1]}</td>
								<td>${l[2]}</td>
								<td>${l[3]}</td>
								<td>${l[4]}</td>
								<td>${l[5]}</td>
								<td>${l[5]}</td>
								<td>${l[6]}</td>
							</tr>
						</c:forEach>
				</tbody>
			</table>
		</div>
		<div style="position: relative;top: 95px;left:10px;">
			<span>
				<span><font size="2" color="#4F4F4F">责任人：</font></span>
				<span><input type="text" class="inputbottom" readonly="readonly" value="${cashi[7]}" style="width: 140px;"></span>
			</span>
			<span style="position: relative;left: 430px;">
				<span><font size="2" color="#4F4F4F">单据时间：</font></span>
				<span><input type="text" class="inputbottom" readonly="readonly" value="${cashi[6]}" id="date"  style="width: 140px;"></span>
			</span>
		</div>
	</div>
  </body>
</html>
