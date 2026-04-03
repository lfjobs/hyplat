<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="<%=basePath%>js/jquery-easyui-1.4/themes/black/easyui.css" rel="stylesheet" type="text/css" />
    <link href="<%=basePath%>js/jquery-easyui-1.4/themes/icon.css" rel="stylesheet" type="text/css" />
    <script src="<%=basePath%>js/jquery-easyui-1.4/jquery.min.js" type="text/javascript"></script>
    <script src="<%=basePath%>js/jquery-easyui-1.4/jquery.easyui.min.js" type="text/javascript"></script>
    <script src="<%=basePath%>js/jquery-easyui-1.4/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
  <script type="text/javascript" src="<%=basePath%>js/come4j.js"></script>
<script type="text/javascript">
function init(){

 		var domcontext = document.getElementById("context");
        var kbDom = document.getElementById("kb");
        var remindurl = document.getElementById("url");
       /*  alert(kbDom.innerHTML); */
        JS.Engine.on({
                hello : function(kb){//侦听一个channel
                        kbDom.innerHTML = kb;
                }
        });
        JS.Engine.on({
                context : function(context){//侦听一个channel
                        domcontext.innerHTML = context;
                }
        });
        JS.Engine.on({
        	    rurl : function(rurl){//侦听一个channel
        	    	remindurl.innerHTML = rurl;
            }
    });
        JS.Engine.start("conn"); 
}

</script>
</head>
<body onload="init()">
      <span id="url"  style="display:none" ></br></span>
      <span id="kb"  style="display:none" ></br></span>
      <span id = "neirong" style="display:none">内容：</span> <span id = "context"  style="display:none"></span>
</body>
<script type="text/javascript">
var basePath = "<%=basePath%>";
$(function () {
			    function aa(){
			    	var documentmessage = $("#kb").text();
			    	var neirong =  $("#neirong").text();
			    	var context =  $("#context").text();
			    	var url =  $("#url").text();
			    	var url1=basePath+url;
			    if(documentmessage!=""){ 
			    		var options = {
						        title: "温馨提示/您有新的消息",
						        msg: "标题："+ "<a target='rightFrame' href="+url1+">" + context + "</br>"+"</a>" + neirong + "<a target='rightFrame' href="+url1+">"+ documentmessage +"</a>" ,
						        showType: 'slide',
						        width:'173',
						        height:'120',
						        timeout: 0,
						    };
						$.messager.show(options);
						$("#kb").html("");
			    	}
			   } 
			    setTimeout(aa,5000) ;
			    setInterval(aa,3000); 
});
    </script>
</html>
