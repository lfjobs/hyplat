<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>


		<title>移动平台菜单</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<link href="<%=basePath%>css/ea/admin.css" rel="stylesheet"
			type="text/css" />
	</head>

	<body>
		<div id="right_main_nav" style="overflow: auto; height: 510px;">
		</div>
	</body>
	<script type="text/javascript">

					var eaid="<%=request.getParameter("eaid")%>";
					var add = "<div id='"+eaid+"'  >";
					var interface = '';
					var mo=eval('('+self.parent.frames["topFrame"].menuObj[eaid]+')');

					for(i=0;i<mo.cmiList.length;i++){
						add += "<div class='list_tilte_onclick' >" + mo.cmiList[i].menuName + "</div>";
						add += "<div class='list_detail' style='display: none;'>"
				   	    for (j=0;j<mo.cmiList[i].sinterfaceList.length;j++){
				   	    
				   	       var interface = mo.cmiList[i].sinterfaceList[j].interfaceUrl;
				   	       add += "<li class='txt01 sinterface' onclick='changebody("+"\"" +interface+"\","+"\"" +mo.cmiList[i].sinterfaceList[j].interfaceName+"\"" + ")'>";
					       add += mo.cmiList[i].sinterfaceList[j].interfaceName ;
					       add +="</li>";
					    }
					    add += "</div>";
					}
					add += "</div>";
				
						$("#right_main_nav").append(add);
					function changebody(interfaceUrl,menuName){
						self.location.href="<%=basePath%>"+interfaceUrl+".jspa";
					}
$(".list_tilte_onclick").live("click", function(event){
     if($(this).next("div").is(":visible")){
     $(this).next("div").hide();
      return;
     }
     $(this).next("div").show();
 })
		</script>
</html>
