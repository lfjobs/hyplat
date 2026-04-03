<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script type="text/javascript" src="<%=basePath%>js/jquery-1.6.1.min.js"></script>
	<link href="<%=basePath%>css/contacts/Network/style12.css" rel="stylesheet"
		type="text/css" />
	<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<title>商城业主人脉财源</title>
</head>
<body>
	<div class="wfj12_020">
 
    	<!--中联园区头部-->
    	<div class="wfj_top">
        	<ul>
            	<li><a href="<%=basePath%>/ea/consignee/ea_toVipCenter.jspa" target="_self"><img src="<%=basePath%>images/contacts/Network/wfj_return_01.png" /></a></li>
            	<li>商城业主人脉财源</li>
            	<li><a href="javascript:;"><img src="<%=basePath%>images/contacts/Network/top_more.png" /></a></li>
            </ul>
        </div>
    	<!--中联园区头部 end-->
    	<input type="hidden" id="account" value="${session.account.account}"/>
    	<input type="hidden" id="type" value="${session.type.cusType}"/>
    	<input type="hidden" id="custid" value="${session.account.custid}"/>
        <div class="wfj12_020_content">
        	<div class="wfj12_020_hidden">
            	<div class="wfj12_020_con">
                	<div class="wfj12_020_width">
                        <table>
                            <tr class="wfj12_020_click04 che01">
                                <td><img src="<%=basePath%>images/contacts/Network/wfj_connections_00.png" /></td>
                                <td>公司会员商城业主会员</td>
                                <td  align="right">
                                  <img src="<%=basePath%>images/contacts/Network/wfj_return_06.png" />
                                  <span style="display:none;">2</span>
                                </td>
                            </tr>
                        
                            <tr class="wfj12_020_click04 che01">
                                <td ><img src="<%=basePath%>images/contacts/Network/wfj_connections_01.png" /></td>
                                <td >合伙人商城业主会员</td>
                                <td  align="right">
                                  <img src="<%=basePath%>images/contacts/Network/wfj_return_06.png" />
                                  <span style="display:none;">3</span>
                                </td>
                            </tr>
                         
                            <tr class="wfj12_020_click04  che01">
                                <td><img src="<%=basePath%>images/contacts/Network/wfj_connections_02.png" /></td>
                                <td>微分金商城业主会员</td>
                                <td align="right"><img src="<%=basePath%>images/contacts/Network/wfj_return_06.png" />
                                   <span style="display:none;">4</span>
                                </td>
                            </tr>
                        
                            <tr class="wfj12_020_click04  che01">
                                <td><img src="<%=basePath%>images/contacts/Network/wfj_connections_03.png" /></td>
                                <td>代理商商城业主会员</td>
                                <td align="right"><img src="<%=basePath%>images/contacts/Network/wfj_return_06.png" />
                                   <span style="display:none;">5</span>
                                </td>
                            </tr>
                            <tr class="wfj12_020_click04  che01">
                                <td><img src="<%=basePath%>images/contacts/Network/wfj_connections_04.png" /></td>
                                <td>客户</td>
                                <td align="right"><img src="<%=basePath%>images/contacts/Network/wfj_return_06.png" />
                                   <span style="display:none;">6</span>
                                </td>
                            </tr>
                            <tr  class="wfj12_020_click04 che01">
                                <td><img src="<%=basePath%>images/contacts/Network/wfj_connections_05.png" /></td>
                                <td>粉丝名片</td>
                                <td align="right"><img src="<%=basePath%>images/contacts/Network/wfj_return_06.png" />
                                
                                   <span style="display:none;">8</span>
                                </td>
                            </tr>
                            <tr class="wfj12_020_click06 che01">
                                <td><img src="<%=basePath%>images/contacts/Network/wfj_connections_06.png" /></td>
                                <td>添加粉丝名片</td>
                                <td align="right"><img src="<%=basePath%>images/contacts/Network/wfj_return_06.png" />
                                </td>
                            </tr>
                           
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <script type="text/javascript">
      var basePath='<%=basePath%>';
    	$(document).ready(function(e) {
			//中联园区头部
            $(".wfj_top").attr("style","height:"+$(window).height()*0.06+"px;line-height:"+$(window).height()*0.06+"px;");
            $(".wfj_top").find("li").attr("style","width:15%;");
            $(".wfj_top").find("li").find("img").attr("style","height:"+$(window).height()*0.03+"px;");
            $(".wfj_top").find("li").eq(1).attr("style","width:70%;font-size:"+$(window).height()*0.025+"px;");
			
			if($(window).width()>$(window).height()){
				$(".wfj12_020").attr("style","width:"+$(window).width()*0.7+"px;height:"+$(window).height()+"px;");
			}else{
				$(".wfj12_020").attr("style","width:"+$(window).width()+"px;height:"+$(window).height()+"px;");
			}
			//$(".wfj12_020_con").find("tr").eq(0).attr("style","display:none;width:100%;");
			$(".wfj12_020_con").find("td").attr("style","height:"+$(window).height()*0.08+"px;line-height:"+$(window).height()*0.08+"px;font-size:"+$(window).height()*0.02+"px; border-bottom:"+$(window).height()*0.002+"px solid #F0F0F0;");
			$(".wfj12_020_con").find("td").find("img").parent().attr("style"," text-align:center; border-bottom:"+$(window).height()*0.002+"px solid #F0F0F0;");
			$(".wfj12_020_con").find("td").find("img").attr("style","height:"+$(window).height()*0.04+"px;width:auto;");
			
		
			
			//点击会员各级别默认获取个人的会员 type：1 个人;2:公司
			$(".wfj12_020_click04").click(function(){
			    var chooseCusType = $(this).find("span").text();
				open(basePath+"ea/resourse/ea_show.jspa?type=1&chooseCusType="+chooseCusType,"_self");
			});
			
			var custid=$("#custid").attr("value");
			var account = $("#account").attr("value");
			$(".wfj12_020_click06").click(function(){
				open(basePath+"page/Network/resources_1.jsp?custid="+custid+"&account="+account,"_self");
			});
			
			$(".wfj12_020_con").each(function(){
				$(this).find("tr").find("td").css("width","10%");
				$(this).find("tr").find("td").eq(1).css("width","80%");
			});
			var type=$("#type").attr("value");
	        if(type==0){
		
			  $(".che01").show();
			}
			if(type==2){
		
			  $(".che01").show();
			  $(".wfj12_020_con").find("tr").eq(0).hide();
			  $(".wfj12_020_con").find("tr").eq(1).find("td").eq(0).css("width","2%");
		      $(".wfj12_020_con").find("tr").eq(1).find("td").eq(1).css("width","98%");
			}
			
			if(type==3){
			
			$(".che01").show();
			$(".wfj12_020_con").find("tr").eq(0).hide();	
			$(".wfj12_020_con").find("tr").eq(1).hide();	
			$(".wfj12_020_con").find("tr").eq(2).find("td").eq(0).css("width","2%");
		    $(".wfj12_020_con").find("tr").eq(2).find("td").eq(1).css("width","98%");
			}
			 if(type==4){
			$(".che01").show();
			$(".wfj12_020_con").find("tr").eq(0).hide();
			$(".wfj12_020_con").find("tr").eq(1).hide();
			$(".wfj12_020_con").find("tr").eq(2).hide();
		    $(".wfj12_020_con").find("tr").eq(3).find("td").eq(0).css("width","2%");
		    $(".wfj12_020_con").find("tr").eq(3).find("td").eq(1).css("width","98%");			
			}else if(type==5){
			   $(".che01").show();
			   $(".wfj12_020_con").find("tr").eq(0).hide();
			   $(".wfj12_020_con").find("tr").eq(1).hide();
			   $(".wfj12_020_con").find("tr").eq(2).hide();
			   $(".wfj12_020_con").find("tr").eq(3).hide();
			   $(".wfj12_020_con").find("tr").eq(4).find("td").eq(0).css("width","2%");
		       $(".wfj12_020_con").find("tr").eq(4).find("td").eq(1).css("width","98%");	
			}else if(type==6||type==7){
			
			$(".che01").show();
			$(".wfj12_020_con").find("tr").eq(0).hide();
			$(".wfj12_020_con").find("tr").eq(1).hide();
			$(".wfj12_020_con").find("tr").eq(2).hide();
			$(".wfj12_020_con").find("tr").eq(3).hide();
			$(".wfj12_020_con").find("tr").eq(4).hide();
			$(".wfj12_020_con").find("tr").eq(5).find("td").eq(0).css("width","2%");
		    $(".wfj12_020_con").find("tr").eq(5).find("td").eq(1).css("width","98%");
			}
			
		
			
			
        });
    </script>
</body>
</html>
