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
<script src="<%=basePath%>js/jquery.js"  type="text/javascript"></script>
<link rel="stylesheet" href="<%=basePath%>css/ea/main.css"
			type="text/css" />
<script type="text/javascript" src="<%=basePath%>js/curvycorners.js"></script>
<link href="<%=basePath%>css/ea/admin.css" rel="stylesheet" type="text/css" />
<title>左侧导航栏</title>
<style type="text/css">
<!--
.roundedCorners{
width:196px;
height:613px;
background-color: #FFF;
border:1px solid #2b90de;
margin-left:5px;
 
/* Do rounding (native in Safari, Firefox and Chrome) */
-webkit-border-radius: 6px;
-moz-border-radius: 6px;
 
}
body {
	background-image: url(../../images/ea/admin/bg_03.gif);
	background-repeat: repeat-x;
}
</style>
<script type="text/javascript">
var eaIDS = new Array(10);
var num = 0 ;
var eaID = "${seaList[0].eaID}";
var eaName = "${seaList[0].eaName}";
if('' !=  eaID){
   lists(eaID)
}

function lists(eaID)
		{
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
                  async: true,
                  dataType: "json",
                  success: function cbf(data){
					var member = eval("(" + data + ")");
					var nologin = member.nologin;
					if(nologin){
						document.location.href ="<%=basePath%>page/ea/not_login.jsp";
					}
					var cmiList = member.cmiList;
					
					var add = "<div id='"+eaID+"'  style='display: none;'>";
					var interface = '';
					for(i=0;i<cmiList.length;i++){
						add += "<div class='list_tilte_onclick' >" + cmiList[i].menuName + "</div>";
						add += "<div class='list_detail' style='display: none;'><ul>"
				   	    for (j=0;j<cmiList[i].sinterfaceList.length;j++){
				   	       var interface = cmiList[i].sinterfaceList[j].interfaceUrl;
				   	       add += "<li class='txt01 sinterface' onclick='changebody("+"\"" +interface+"\","+"\"" +cmiList[i].sinterfaceList[j].interfaceName+"\"" + ")'>";
					       add += cmiList[i].sinterfaceList[j].interfaceName ;
					       add +="</li></ul>";
					    }
					    add += "</div>";
					}
					add += "</div>";
					$("#right_main_nav").append(add);
					$("#"+eaID).show();
				},
                     error: function cbf(data){
                        alert("数据获取失败！")
                     }
                 });
		}

function changebody(interfaceUrl,menuName){
  parent.manFrame.document.location.href="<%=basePath%>"+interfaceUrl+".jspa";
}
$(function(){ 
 $(".list_tilte_onclick").live("click", function(event){
     if($(this).next("div").is(":visible")){
     $(this).next("div").hide();
      return;
     }
     $(this).next("div").show();
 })
})
function hide(){
if(parent.document.getElementById("topdiv").style.height == "70px"){
    parent.document.getElementById("topdiv").style.height = "0px";
    $("#hide").text("显示头部");
 }else{
    parent.document.getElementById("topdiv").style.height = "70px";
     $("#hide").text("隐藏头部");
 }
}

</script>
</head>
	
<body>
        <div class="roundedCorners">
			<div class="left_bannb" align="right"><a style="margin-right:10px;" id="hide" href="#" onclick="hide()">隐藏头部</a></div>
		     <div id="right_main_nav" style="overflow:auto;height:510px;">
		    </div>
		</div>
	</body>
</html>
