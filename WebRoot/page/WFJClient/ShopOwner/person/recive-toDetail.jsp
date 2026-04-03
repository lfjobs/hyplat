<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>收货地址管理</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="description" content="收货地址管理">
	<link rel="stylesheet" type="text/css" href="<%=basePath %>css/WFJClient/wfjapp.css"/>
	<script type="text/javascript" src="<%=basePath %>js/jquery.js"></script>
  </head>
  
  <body class="bgcolorFFF">
    <div class="wfj11_018">
        <div class="wfj11_018_content">
        	<div class="wfj11_018_width">
        		<form id="addAddress" name="addAddress" method="post" action="<%=basePath%>ea/wfjshop/ea_delAddr.jspa">
        		<input type="submit" name="submit" style="display:none;"/>
        		<input type="hidden" name="user" value="${param.user}">
        		<input type="hidden" name="staffAddress.addressID" value="${staffAddress.addressID}">
            	<table width="100%" >
                	<tr>                   	
                    	<td align="center"><input type="text" readonly="readonly" value="${staffAddress.consignee}"/></td>                    	
                    </tr>
                	<tr>
                    	<td align="center"><input type="text" readonly="readonly" value="${staffAddress.phone}"/></td>                   	
                    </tr>
                    <tr>
                    	<td align="center"><input type="text" readonly="readonly" value="${staffAddress.area}${staffAddress.addressDetailed}"/></td>
                    </tr>
                	<tr>                 	
                    	<td align="center"><input type="text" readonly="readonly" value="${staffAddress.postCode}"/></td>
                    </tr>
                </table>
                </form>
            </div>
        </div>
        <div class="wfj11_018_bottom">
		        	<div class="wfj11_018_save" id="del">删除</div>
		        	<div class="wfj11_018_save" onclick="setdefault('${staffAddress.addressID}','${staffAddress.staffID }')">设置默认</div>
		        </div>
        
    </div>
    <script type="text/javascript">
    var basePath='<%=basePath%>';
    var sccid="${param.user}";
	var type="${param.type}";
    $(document).ready(function(e) {
         	$("body").css("height",$(window).height()) ;
			//修改字体大小
				$("#tops").find("li").attr("style","float:left;");
				$("#tops").find("li").eq(0).attr("style","width:15%;");
				$("#tops").find("li").eq(0).find("img").attr("style","height:"+$(window).height()*0.03+"px;padding-left:"+$(window).height()*0.02+"px; vertical-align:middle;");
				$("#tops").find("li").eq(1).attr("style","width:70%; text-align:center; font-size:"+$(window).height()*0.025+"px;");
				$("#tops").find("li").eq(2).attr("style","width:15%; text-align:center;");
				$("#tops").find("li").eq(2).find("img").attr("style","height:"+$(window).height()*0.03+"px; width:auto; vertical-align:middle;");
				
			$(".wfj11_018_top").css("height",$(window).height()*0.08+"px");
			$(".wfj11_018_top").css("lineHeight",$(window).height()*0.08+"px");
			$(".wfj11_018_content").find("td").attr("style"," height:"+$(window).height()*0.10+"px; line-height:"+$(window).height()*0.06+"px;font-size:"+$(window).height()*0.02+"px;");
			$(".wfj11_018_content").find("td").find("input").attr("style"," height:"+$(window).height()*0.06+"px;font-size:"+$(window).height()*0.02+"px;text-align:left;width:94%;border:1px solid #DCDCDC;border-radius:5px;padding-left:5px;");
			//alert($(window).width()*0.5+"px;")
			$(".wfj11_018_content").find("td").find("a").attr("style","font-size:"+$(window).height()*0.03+"px;text-align:right;float:right;");
			$(".wfj11_018_save").attr("style","font-size:"+$(window).height()*0.025+"px;height:"+$(window).height()*0.06+"px;line-height:"+$(window).height()*0.06+"px;");		
        	
        });
        $("#del").click(function(){
         $("form#addAddress").find("input[name='submit']").click();
        });
        function setdefault(addrid,staffId){
        	$.ajax({
        		type:"get",
        		url:basePath+"ea/wfjshop/sajax_ea_changeDefault.jspa",
        		data:"staffAddress.addressID="+addrid+"&staffAddress.staffID="+staffId,
        		success:function(data){
        			
        			document.location.href=basePath+"ea/wfjshop/ea_personAddress.jspa?user="+sccid;
        		}
        	});
        }
    </script>
  </body>
</html>
