<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>财务初始化</title>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<script language="javascript" type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js">
        </script>
	<script type="text/javascript">
	  var accountID = "${accountID}";
	  var b=true;
	  if(accountID.length){
	  alert("初始化成功！此公司开启财务总帐模块功能");
	  parent.parent.parent.parent.parent.window.location.href= "<%=basePath%>page/ea/index.jsp";
	  }
	  function updatecompany(){
	  if($("#stime").val()==null){
	  	alert("请填写启动日期");
		return;
	  }
	    var url1="<%=basePath%>/ea/initialization/sajax_ea_ajaxFunction.jspa?date="+new Date().toLocaleString();
	               $.ajax({
	                        url: encodeURI(url1),
	                        type: "get",
	                        async: false,
	                        dataType: "json",
	                        success: function cbf(data){
				              var member = eval("(" + data + ")");
							  var c = member.count;
							  if (c != 0) {
									alert("该公司已经财务初始化！不能重复初始化");
									b = false;
							  }
							},
							error : function cbf(data) {
								alert("数据获取失败！");
							}
					});
		if(b){
	    	if(confirm("确定初始化？")){
		  		window.document.location.href ="<%=basePath%>/ea/initialization/ea_getFunction.jspa?stime="+$("#stime").val();
		  	}
		}
	  }
	</script>
	</head>
	<body>
		<form method="post" action="<%=basePath%>plat/login.jspa">
		   <span>财务模块初始化后开启财务总帐模块功能     开启日期</span><input name="stime" id="stime" onfocus="daymonth(this);"/>
			<span><input type="button" onclick="updatecompany()" value="初始化"/></span>
		</form>
	</body>
</html>
