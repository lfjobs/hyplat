<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd" >
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>凭证列印</title>
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
		width: 150px;}
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
				$("#orgValue").append(option);
			}
		},
		error:function(){
			alert("数据获取失败");
		}
		});
	
		$("#but").click(function(){
			if($("#edate").val()==""||$("#sdate").val()==""||$("#sdate").val()<$("#edate").val()){
				alert("起止日期必须输入,且终止日期必须大于起始日期");
				return;
			}
			var br=true;
			$.ajax({
				url:basePath+"ea/voucher/sajax_ea_ajaxVoucherPrint.jspa",
				data:{"edate":$("#sdate").val(),"sdate":$("#edate").val()},
				type:"post",
				async : false,
				success:function(data){
					var member = eval("(" + data + ")");
					if(member.ot=="nul"){
						br=false;
						alert("该日期内无数据");
					}
				},
				error:function(data){
					
				}
			})
			if(br)
			$("#form").attr("action",basePath+"ea/voucher/ea_voucherPrint.jspa").submit();			
		})	
	})
</script>
  </head>
  
  <body style="background-color: #DAE7F6">
    <div style="position: absolute;left:200px;top:100px;">
    <form action="" method="post" id="form" name="form" target="_blank">
    	<center><font size="6">凭证列印</font></center>
    	<table cellspacing="20px">
    	
    		<tr>
    			<td><font color="red">*</font>起止日期：</td>
    			<td><input type="text" name="sdate" id="edate"  onfocus="WdatePicker({dateFmt:'yyyyMMdd'})">&nbsp;-</td>
    			<td><input type="text" name="edate" id="sdate"  onfocus="WdatePicker({dateFmt:'yyyyMMdd'})"></td>
    		</tr>
    		
    		<tr>
    			<td>起止凭证号码：</td>
    			<td><input type="text" name="snumber" id="enumber">&nbsp;-</td>
    			<td><input type="text" name="enumber" id="snumber"></td>
    		</tr>
    		
    		<tr>
    			<td>部门：</td>
    			<td colSpan=2><select name="organizationname" style="width: 150px" id="orgValue">
    				<option value=""></option>
    			</select></td>

    		</tr>
			
    	</table>
    	<input type="button" id="but" value="预览" style="width:50px;position: absolute;left:170px;">
    	<input type="button" value="取消" style="width:50px;position: absolute;left:320px;">
  	</form>
    </div>
  </body>
</html>
