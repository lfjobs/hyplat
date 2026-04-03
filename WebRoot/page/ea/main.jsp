<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%> 
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="<%=basePath%>css/easyui.css" rel="stylesheet" type="text/css" />
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<script src="<%=basePath%>js/jquery.easyui.min.js"  type="text/javascript"></script>
<title>5L5C中联园区微分金系统平台</title> 
	<script type="text/javascript">
    var url = "<%=basePath%>ea/documentcommon/sajax_ea_noticePlat.jspa";
	$.ajax({
				url : url,
				type : "get",
				async : true,
				dataType : "json",
				success : function(data) {
		         var mem = eval("("+data+")");
		         var titles = mem.result;
		         if(titles!=""){
		           show(titles);
		         }
				},
				error : function(data) {
					alert(data);
				}

			});


         function show(titles){
            $.messager.show({
                title:'温馨提示',
                msg:titles+"这些合同已过期，请及时处理！",
                showType:'show',
                timeout:10000
            });
        }
 

</script>
</head> 

<frameset rows="35,*" frameborder="0" border="0"  framespacing="0">
			<frame src="<%=basePath%>ea/login_main_generateSet.jspa?imgLog=<%=request.getParameter("imgLog")%>" id="topFrame" frameborder="0" border="0" framespacing="0"  scrolling="no" name="topFrame" noresize="noresize" />
	<frameset id="bgMainContent" cols="192,10,*">
	<frame src='<%=basePath%>ea/login_main_generateleft.jspa' name="leftFrame" noresize="noresize" id="leftFrame" style="display:block;" scrolling="yes"/>
		<%--<frameset  id="navadtion" rows="*,125">
			
			 <frame src="<%=basePath%>page/ea/messagepush.jsp"  name="messagepush" id="messagepush"  frameborder="0" border="0"  framespacing="0" /> 
		</frameset>--%>
		<frame id="midFrame"  name="midFrame" src="<%=basePath%>page/ea/main_middle.jsp" frameBorder="0" scrolling="no" noresize="noresize"></frame>
		<frameset id="mainContent" rows="60,*,10">
		    <frame id="daohang" name="daohang" frameborder="0" noresize="noresize" border="0"  framespacing="0" style="display: none;overflow: hidden;"/>
			<frameset id="mainContent">
				 <frame src="<%=basePath%>ea/hdocument/ea_getHDocumentList.jspa" name="rightFrame" id="rightFrame"  frameborder="0" border="0"  framespacing="0" />	
			</frameset>
		</frameset>
    </frameset>
<noframes></noframes>
</frameset>
</html>