<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>科目管理--期初余额管理</title>
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link href="<%=basePath%>/css/ea/organization.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" href="<%=basePath%>js/tree/common/css/style.css" type="text/css" media="screen" />
<script  src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js"></script>
<script  src="<%=basePath%>js/tree/codebase/dhtmlxtree.js"></script>
<link rel="STYLESHEET" type="text/css" href="<%=basePath%>js/tree/codebase/dhtmlxtree.css"/>
<script  src="<%=basePath%>/js/ea/finance/invoicing/voucher/csubejsts_manger.js"></script>
<style type="text/css">
<!--
.STYLE1 {color: #27B3FE;
}

-->
</style>
<script type="text/javascript">
  var basePath="<%=basePath%>";
   var treeid;
   var treename;
   var tree;
   var subRule;
   $(function(){
   
    $(window).resize(function(){
             setTimeout(function () {                 
                 $("#qh_sw").height($(window).height()- 30);
                 $("#mainframe").css({"height" : $(window).height() - 5 + "px"});
             },100);
         });
         $("#qh_sw").height($(window).height()- 30);
         $("#mainframe").css({"height" : $(window).height() - 5 + "px"}); 
         
		var url = basePath + "ea/csubjectsrule/sajax_n_ea_getCSubjectsRule.jspa?date="+new Date().toLocaleString();
        $.ajax({
                        url: encodeURI(url),
                        type: "get",
                        async: true,
                        dataType: "json",
                        success: function cbf(data){
			var member = eval("(" + data + ")");
			 var nologin = member.nologin;
			                  if(nologin){
			                  document.location.href =basePath + "page/ea/not_login.jsp";
			                  }
			
			subRule = member.subRule;
        }, 
                        error: function cbf(data){
                           alert("数据获取失败！");
                        }
                    });
		
	});
	 function Submit_onclick(){
	if(window.parent.document.getElementById("leftFrame").style.display == "block") {
	     window.parent.document.getElementById("bgMainContent").cols="0,5,*";
		 window.parent.document.getElementById("leftFrame").style.display = "none";
	} 
}
</script>
</head>
<body>
<form name="csbjectsForm" method="post">
<input name="sub" value="${session_value}" type="hidden"/><!-- 代替token--></form>
<table style="width:100%;overflow:hidden;" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td  id="qh_sw" style="width: 15%;margin-left: 5px;" valign="top"> 
    <div class="qh_gg_nav">&nbsp;会计科目</div>
   <div id="aadTree" style=" border: 0px solid #000000;height:540px"></div> 
    </td>
    <td style="width: 85%;" valign="top">
       <iframe src="" style="margin:0px;width:100%;height:auto;"  name="ccode" marginwidth="0"  marginheight="0" scrolling="no" frameborder="0" id="mainframe"> </iframe>
    </td>
  </tr>
</table>
</body>
</html>
