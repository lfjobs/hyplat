<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd" >
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>金币流水报表选择人员页面</title>
        <%@ page language="java" pageEncoding="UTF-8"%>
        <%
            String path = request.getContextPath();
            String basePath = request.getScheme() + "://"
                    + request.getServerName() + ":" + request.getServerPort()
                    + path + "/";
        %>
   <script language="javascript" type="text/javascript"
			src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
	<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
	<style type="text/css">
		input{
		width: 100px;}
	</style>
<script type="text/javascript">
	var basePath="<%=basePath%>";
	$(function(){
		$("#cusType").change(function(){
			var url=basePath+"ea/goldwater/sajax_ea_getLowerMemberType.jspa?type=ajax&cusType="+$("#cusType option:selected").val();
			$.ajax({
				url : url,
				type : "post",
				async : false,
				success:function(data){
					var member = eval("(" + data + ")");
					var list=member.list;
						$(".option").remove();
					for(var i=0;i<list.length;i++){
						var option="<option value="+list[i][0]+" class='option'>"+list[i][1]+"</option>";
						$("#userName").append(option);
					}
				},
				error:function(data){
					alert("数据获取失败！");
				}
			});
		});
		$("#but").click(function(){
			if(!$("#userName option:selected").val()){
				alert("请选择会员");
				return;
			}
			if($("#edate").val()<$("#sdate").val()&&$("#edate").val()){
				alert("结束日期不可小于开始日期");
				return;
			}
			var url=basePath+"ea/goldwater/ea_getHomePageInformation.jspa?type=like&userId="+$("#userName option:selected").val();
			if($("#sdate").val())
				url+="&sdate="+$("#sdate").val();
			if($("#edate").val())
				url+="&edate="+$("#edate").val();
			open(url);
		})
	});
</script>
  </head>
  
  <body style="background-color: #DAE7F6">
    <div style="position: absolute;left:200px;top:100px;">
    <form action="" method="post" id="form" name="form" target="_blank">
    	<center><font size="6">金币流水报表</font></center><br>
    	<table cellspacing="15px" >
    		<tr>
    			<td>会员类型：</td>
    			<td colspan="2">
    				<select id="cusType" style="width: 250px;">
    					<option></option>
    					<c:forEach items="${list}" var="l">
    						<option value="${l.codeNumber}">${l.codeValue}</option>
    					</c:forEach>
    				</select>
    			</td>
    		</tr>
    		<tr>
    			<td>会员名称：</td>
    			<td colspan="2">
    				<select id="userName" style="width: 250px;"></select>
    			</td>
    		</tr>
    		<tr>
    			<td><font color="red">*</font>起止日期：</td>
    			<td><input type="text" name="sdate" id="sdate"  onfocus="WdatePicker({dateFmt:'yyyyMMdd'})">&nbsp;-</td>
    			<td><input type="text" name="edate" id="edate"  onfocus="WdatePicker({dateFmt:'yyyyMMdd'})"></td>
    		</tr>
		
    	</table>
    	<input type="button" id="but" value="查询" style="width:150px;height:25px;position:relative;left:130px;top: 15px;">
  	</form>
    </div>
  </body>
</html>
