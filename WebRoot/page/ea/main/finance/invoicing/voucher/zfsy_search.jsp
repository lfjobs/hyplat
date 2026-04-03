<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd" >
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>资产负债损益表查询</title>
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
	<link rel="stylesheet" type="text/css"
			href="<%=basePath%>css/admin_main111.css" />
	<style type="text/css">
		/* input{
		width: 150px;} */
	</style>
<script type="text/javascript">
	var basePath="<%=basePath%>";
	var dc=${dc};
	var cc=${cc};
</script>
  </head>
  
  <body style="background-color: #DAE7F6">
    <div style="position: absolute;left:200px;top:150px;">
    <form method="post" id="zsform" name="zsform" target="_blank">
    <input type="submit" name="submit" id="submit" style="display: none;"/>
    	<table cellspacing="20px">
    		<tr>
    			<td>报表类型：</td>
    			<td><input type="radio" name="bt" id="sy" value="B" checked="checked"/>损益表</td>
    			<td><input type="checkbox" name="mx" checked="checked" value="Y" id="mx"/>是否列印明细</td>
    		</tr>
    		
    		<tr>
    			<td></td>
    			<td><input type="radio" name="bt" id="zf" value="A"/>资产负债表</td>
    			<td><input type="checkbox" name="dw" checked="checked" value="Y" id="dw"/>千元</td>
    		</tr>
    		
    		<!-- <tr>
    			<td></td>
    			<td><input type="radio" name="bt" id="zfsy" value="C"/>资产负债表损益表</td>
    			<td></td>
    		</tr> -->
    		<tr>
    			<td colspan="3"></td>
    		</tr>
    		
    		<tr id="time">
    			<td><span class="xx">*</span>资料日期：</td>
    			<td colspan="2"><input type="text" size="10" readonly="readonly" onfocus="cher_ydm(this)" name="st" id="st"/>&nbsp;&nbsp;-&nbsp;&nbsp;<input type="text" size="10" readonly="readonly" onfocus="cher_ydm(this)" name="et" id="et"/></td>
    		</tr>
    		<tr id="ym" style="display: none;">
    			<td><span class="xx">*</span>资料年月：</td>
    			<td><input type="text" readonly="readonly" onfocus="cher_dm(this)" name="ym" id="ny"/></td>
    		</tr>
    	</table>
    	<input type="button" id="but" value="预览" style="width:50px;position: absolute;left:170px;">
    	<!-- <input type="button" value="取消" style="width:50px;position: absolute;left:320px;"> -->
  	</form>
    </div>
    <script type="text/javascript">
    $(function(){
    	$("#but").click(function(){
    		console.info($("#zsform.error").length);
			var raval=$("input[name='bt']:checked").val();
			if (raval!="A"&&(dc<=0||cc<=0)) {
				alert("请先设定损益表内容！");
				return;
			}
			if(raval=="B"&&$("#st").val()!=null&&$("#st").val()!=""&&$("#et").val()!=null&&$("#et").val()!=""){
				$("#zsform").attr("action",basePath+"ea/ccpbsgl/ea_gettosy.jspa");
				document.zsform.submit.click();
			}else if(raval=="A"&&$("#ny").val()!=null&&$("#ny").val()!=""){
				$("#zsform").attr("action",basePath+"ea/ccpbsgl/ea_gettozf.jspa");
				document.zsform.submit.click();
			}else if($("#ny").val()!=null&&$("#ny").val()!=""){
				$("#zsform").attr("action",basePath+"ea/ccpbsgl/ea_gettosy.jspa");
				document.zsform.submit.click();
			}else{
				alert("请填完所有必填项");
				return;
			}
		});
		
		$("input[name='bt']").click(function(){
			var raid=$("input[name='bt']:checked").attr("id");
			if(raid=="sy"){
				/* $("#time").find("input").removeClass("notnull");
				$("#ym").find("input").removeClass("notnull");
				$("#time").find("input").addClass("notnull"); */
				$("#time").show();
				$("#ym").hide();
			}else{
				/* $("#time").find("input").removeClass("notnull");
				$("#ym").find("input").removeClass("notnull");
				$("#ym").find("input").addClass("notnull"); */
				$("#time").hide();
				$("#ym").show();
			}
		});
    });
    </script>
  </body>
</html>
