<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd" >
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>银行流水账</title>
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
	<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
	<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
<style type="text/css">
	input{
	width: 120px;height:20px;}
</style>
<script type="text/javascript">
	var basePath="<%=basePath%>";
	$(function(){
	
		$.ajax({
		url : basePath+"ea/office/sajax_ea_sajaxGetThenFiveDepartmentsList.jspa",
		type : "post",
		async : false,
		dataType : "json",
		success:function(data){
			var member = eval("(" + data + ")");
			var list=member.list;
			for(var i=0;i<list.length;i++){
				var option="<option value='"+list[i].organizationID+"'>"+list[i].organizationName+"</option>";
				$("#department").append(option);
			}
		},
		error:function(){
			alert("数据获取失败");
		}
		});
		
		$("#button").click(function(){
		    $(".notnull").trigger("blur");
		    if ($("form#forms .error").length) {
						alert("请填完所有必填项");
						notoken = 0;
						return;
					}
			if($("#sdate").val()<$("#edate").val()){
				alert("终止日期必须大于起始日期");
				return;
			}
			$("#forms").attr("action",basePath+"/ea/vaccount/ea_getAccountList.jspa?zz=01").submit();
		})	
	})
</script>
  </head>
  <body style="background-color: #DAE7F6">
    <div style="position: absolute;left:200px;top:100px;">
    <form action="" method="post" id="forms" name="forms" target="_blank">
    	<table cellspacing="10px" border="0">
    	    <tr>
    			<td align="center" colspan="2" width="500"><font size="6">银行流水账</font></td>
    		</tr>
    		<tr>
    			<td align="right" width="100"><font color="red">*</font>起止日期：</td>
    			<td width="400"><input type="text" class="notnull" name="sdate" id="edate"  
    			onfocus="WdatePicker({dateFmt:'yyyyMMdd'})"> - <input type="text" class="notnull" name="edate" id="sdate"  
    			onfocus="WdatePicker({dateFmt:'yyyyMMdd'})"></td>
    		</tr>
    		<tr>
    			<td align="right">起止科目：</td>
    			<td><input type="text" name="scodesub" id="scodesub">
    			- <input type="text" name="ecodesub" id="ecodesub"></td>
    		</tr>
    		<tr>
    			<td align="right">银行账号：</td>
    			<td><input type="text" name="yhzh" id="yhzh" style="width:270px;"></td>
    		</tr>
    		<tr>
    			<td align="right">部门：</td>
    			<td>
    			<select name="organizationname" id="department" style="height:25px;width:120px;">
    			<option value=""></option>
    			</select>
    			</td>
    		</tr>
    	</table><br/>
    	<input type="button" id="button" value="预览" style="height:30px;width:60px;position: absolute;left:150px;">
    	<input type="button" value="取消" style="height:30px;width:60px;position: absolute;left:300px;">
  	</form>
    </div>
  </body>
</html>
