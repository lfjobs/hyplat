<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="<%=basePath%>css/ea/main.css" type="text/css" />
<link href="<%=basePath%>css/ea/admin.css" rel="stylesheet" type="text/css" />
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<script type="text/javascript" src="<%=basePath%>js/curvycorners.js"></script>
<script src="<%=basePath%>js/comm.js" type="text/javascript"></script>
<title>左侧导航栏</title>
<style>
.left_bannb { /*background: url("<%=basePath%>images/left_01.png") no-repeat scroll 0 0 rgba(0, 0, 0, 0);*/ height: 48px;}
.left_bannb { background: none; background-color:#eee; color:#3a5c77;font-size: 14px;height: 48px;line-height: 48px; height: 48px;text-align: left;font-weight:bold;padding-left: 10px;}
.roundedCorners{}
.left_bannb {}
.list_tilte_onclick {background-image: url("<%=basePath%>images/list_title_onclick.png"); color:#3b9fd1; font-size:14px; width:230px; overflow:hidden; height:40px; line-height:40px; margin:0px;}
.list_detail li { height:30px; line-height:30px; font-size:14px; margin-top:0px; padding: 0 0 0 40px;}
</style>
</head>	
<body> 
<div class="roundedCorners" id="roundedCorners" style="border-bottom-color: white; border:none;" >
	<div class="left_bannb">功能菜单<!-- <a style="margin-right:10px;" id="hide" href="javascript:;" onclick="hide()">隐藏头部</a> --></div>
     <div id="right_main_nav">
    </div> 
    <div style="clear:both;"></div>
</div>
 <script type="text/javascript">
     $(function(){ 
         $(window).resize(function(){
             setTimeout(function () {                 
                 $("#roundedCorners").height($(window).height() - 5);
                 var _rows = window.parent.document.getElementById("mainContent").rows; 
                 var _arry_rows = _rows.split(','); 
                 window.parent.document.getElementById("mainContent").rows = _arry_rows[0] + "," + ($("#roundedCorners").height() - $("#daohang").height());
             },100);
         }); 
         $("#roundedCorners").height($(window).height() - 5);
     });
 </script>
 
<script type="text/javascript">  
   var _eaID = "${eaID}";     
   var eaIDS = new Array(10);
    var num = 0 ;
    var eaID = "${seaList[0].eaID}";
    var eaName = "${seaList[0].eaName}";
    if('' != _eaID){
        eaID = _eaID;
        eaName = "${eaName}";
       lists(_eaID);
    }else 
	if('' !=  eaID){
	   lists(eaID);
	}

    function lists(eaID){
        $("#right_main_nav").children("div").hide();
        for(z=0;z < num;z++){
	        if(eaIDS[z] == eaID&&""!==eaIDS[z]){
	           $("#"+eaID).show();
                  return;		      
            }    
	    }
        eaIDS[num] = eaID;
        num++;
        var url1 ="<%=basePath%>ea/ajax_login_ea_generateMenu.jspa?eaID="+eaID+"&date="+new Date().toLocaleString(); 
	    $.ajax({
            url: encodeURI(url1),
            type: "get", 
            dataType: "json",
            success: function cbf(data){
			    var member = eval("(" + data + ")");
			    var nologin = member.nologin;
			    if(nologin){
				    document.location.href ="<%=basePath%>page/ea/not_login.jsp";
			    }
			    var cmiList = member.cmiList;
			
				var add = "<div id='"+eaID+"'>";
				var interface = '';
				for(i=0;i<cmiList.length;i++){
					add += "<div class='list_tilte_onclick' >" + cmiList[i].menuName + "</div>";
					add += "<div class='list_detail' style='display: none;'><ul>";
			   	    for (j=0;j<cmiList[i].sinterfaceList.length;j++){
			   	       var interface = cmiList[i].sinterfaceList[j].interfaceUrl;
			   	       add += "<li class='txt01 sinterface' onclick='changebody("+"\"" +interface+"\","+"\"" +cmiList[i].sinterfaceList[j].interfaceName+"\"" + ")'>";
				       add += cmiList[i].sinterfaceList[j].interfaceName ;
				       add +="</li>";
				    }
				    add += "</ul></div>";
				}
				add += "</div>";
				$("#right_main_nav").append(add);
			//$("#"+eaID).show();
		    },
            error: function cbf(data){
               alert("数据获取失败！");
            }
    });
} 
function changebody(interfaceUrl,menuName){
  switch(menuName){
     case "进入办公系统":
         window.parent.document.getElementById("leftFrame").src="<%=basePath%>"+interfaceUrl+".jspa?result=result";
         window.parent.document.getElementById("rightFrame").src= "";
     break;
     case "进入个人办公室":
         window.parent.document.getElementById("daohang").src="<%=basePath%>"+interfaceUrl+".jspa";
         window.parent.document.getElementById("daohang").style.display = "block";
         window.parent.document.getElementById("rightFrame").src= "";
     break;
     default:
     	 if(interfaceUrl.indexOf(".jsp")>0){
     	 window.parent.document.getElementById("rightFrame").src="<%=basePath%>"+interfaceUrl;
     	 }else{
         window.parent.document.getElementById("rightFrame").src="<%=basePath%>"+interfaceUrl+".jspa";
     	 }
         break;
  }  
}
$(function(){ 
 $(".list_tilte_onclick").live("click", function(event){
     if($(this).next("div").is(":visible")){
     $(this).next("div").hide();
     $(this).attr("style","");
      return;
     }
     $(this).next("div").show();
      $(this).attr("style","background: url(<%=basePath%>images/list_title_onclick_2.png");
 });
});
/* function hide(){
if(parent.document.getElementById("topdiv").style.height == "70px"){
    parent.document.getElementById("topdiv").style.height = "0px";
    $("#hide").text("显示头部");
 }else{
    parent.document.getElementById("topdiv").style.height = "70px";
     $("#hide").text("隐藏头部");
 }
} */
</script>
</body>
</html>
