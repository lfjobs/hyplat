<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
<title>买家会员中心</title>
<link href="<%=basePath%>css/contacts/recepit/style01.css" rel="stylesheet"
		type="text/css" />
<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
<script type="text/javascript">
var basePath = "<%=basePath%>";
var ff = document.referrer;


var state = "${cuscom.state}";
var user = "${cuscom.account}";

function tabRole(){
var role = $("#role").text();
if(role=="buyer"){
  //切换到卖家
  $("#roletitle").text("买家");
  $("#role").text("seller");
  $(".buyer1").removeClass("buyer1").addClass("seller1");
  $(".buyer2").removeClass("buyer2").addClass("seller2");
}else{
 //切换到买家
 $("#roletitle").text("卖家");
  $("#role").text("buyer");
  $(".seller1").removeClass("seller1").addClass("buyer1");
  $(".seller2").removeClass("seller2").addClass("buyer2");
 }
}


//操作

	function oprateManage(type) {
	    //判断是买家还是卖家操作
		var role = $("#role").text();
		//订单管理
		if (type == "order") {
			if (role == "buyer") {
				open(
						basePath
								+ "/ea/hypb/ea_getcomporder.jspa?staid=${cuscom.staffid}",
						"_self");
			} else {
				open(
						basePath
								+ "/ea/ghspb/ea_getcomporder.jspa?staid=${cuscom.companyId}",
						"_self");
			}
		}
         //收货单
		if (type == "consignee") {
			var stype = "";
			if (role == "seller") {
				stype = "seller";
				
			}
			open(
						basePath
								+ "/ea/consignee/ea_receipt.jspa?stype="+stype+"&sta=00&tupn=goods&stype3=&user",
						"_self");
	    	}
		
		  //人脉财源
		if (type == "wealth") {

			open(
						basePath
								+ "/ea/resourse/ea_findconWealth.jspa",
						"_self");
		
     	}
     	
     	
     	if (type == "card") {
     	    var backurl = "ea/consignee/ea_toVipCenter.jspa";
			if (state == "1") {//个人名片
				open(basePath
								+"ea/perinfor/ea_getPageHomeData.jspa?user="+user+"&editType=00&backurl="+backurl,
						"_self");
			} else { //公司名片
				open(basePath
								+ "/ea/industry/ea_CompanyCard.jspa?user="+user+"&editType=0&backurl="+backurl,
						"_self");
			}
		}
		
		   if (type == "refund") {
		
				open(basePath
								+ "ea/refund/ea_getRefundSheetList.jspa?user="+user+"&type=mobile&role="+role,
						"_self");
	
		}
		
		if (type == "jinbi") {
			open(basePath+ "/ea/jinbi/ea_gethyjifen.jspa?user="+user+"&khd=1","_self");
			/* var url=basePath+"/ea/jinbi/sajax_getjinbiScore.jspa?user="+user;
			$.ajax({
				url : url,
				type : "get",
				async : false,
				dataType : "json",
				success : function (data) {
					var member = eval("(" + data + ")");
					var jifen=member.jifen;
					if(jifen!=null&&jifen.wfjJifenScore!=0){
						
					}else{
						alert("您还没有金币哦！");
					}
				},
				error:function(data){
					alert("获取数据失败");
				}
			});	 */
		}
		//产品管理
		if(type=="productmanage"){
			open(basePath+"ea/productslaunch/ea_productsManage.jspa?user="+user,"_self");
		}
		//产品发布
		if(type=="productlaunch"){
			open(basePath+"ea/productslaunch/ea_toProductsLaunch.jspa?user="+user+"&companyId=${cuscom.companyId}","_self");
		}
	}
	//返回功能
	function backpage(){
	var backu = "<%=session.getAttribute("vipback")%>";
	if(backu==2){
	     document.location.href=basePath+"/ea/wfjshop/ea_getWFJshops.jspa";
	}else{
	    document.location.href = basePath+backu;
	}	 
	}	
</script>
</head>
<body class="bgcolorFFF">
	<div class="wfj01_002">
    	<div class="wfj01_002_top buyer1">
        	<ul id="tops">
            	<li><a onclick="backpage();" target="_self"><img src="<%=basePath%>images/contacts/recepit/wfj_return_01.png" /></a></li>
            	<li>会员中心</li>
            </ul>
        </div>
        
        <div class="wfj01_002_title buyer2">
        	<div class="wfj01_002_title_top">
            	<table class="buyer2">
                	<tr>
                    	<td width="35%">
                    	<div class="wfj01_002_changehtml tpc2">
                    	<span style="display:none;" id="role">buyer</span>
                    	<a onclick="tabRole()" class="tpc">切换为<span id="roletitle">卖家</span></a></div>
                    	</td>
                    	<td width="25%" align="center">
                    	<img class="wfj01_002_hyimg" src="<%=basePath%><c:if test='${cuscom.state=="1"}'><s:if test="staff.photo!=null">${staff.photo}</s:if><s:else>images/contacts/Network/defaultbig.png</s:else></c:if><c:if test='${cuscom.state=="2"}'><s:if test="contactCompany.logoPath!=null">${contactCompany.logoPath}</s:if><s:else>images/contacts/recepit/wfj_huiyuan_01.png</s:else></c:if>"/>
                    	</td>
                    	<td width="40%" align="right"><div class="wfj01_002_introduce">
                    	<c:if test='${cuscom.cusType=="0"}'>中联园区平台</c:if>
                    	<c:if test='${cuscom.cusType=="0.5"}'>国税</c:if>
                    	<c:if test='${cuscom.cusType=="1"}'>地税</c:if>
                    	<c:if test='${cuscom.cusType=="2"}'>公司商城业主会员</c:if>
                    	<c:if test='${cuscom.cusType=="3"}'>合伙创业商城业主会员</c:if>
                    	<c:if test='${cuscom.cusType=="4"}'>微分金商城业主会员</c:if>
                    	<c:if test='${cuscom.cusType=="5"}'>代理商商城业主会员</c:if>
                    	<c:if test='${cuscom.cusType=="6"}'>Vip客户</c:if>
                    	<c:if test='${cuscom.cusType=="7"}'>普通客户</c:if>
                    	
                    	</td>
                    </tr>
                </table>
            </div>
        </div>
        <!--会员中心-->
        <div class="wfj01_002_content">
        	<div class="wfj01_002_hidden">
            	<div class="wfj01_002_vip">
                    <div class="wfj01_002_vip_data">
                        <table width="92%" cellspacing="0">
                            <tr onclick="oprateManage('card');">
                                <td width="20%" align="left"><img src="<%=basePath%>images/contacts/recepit/wfj_vip_list_01.png" /></td>
                                <td width="65%"><a href="javascript:"><c:if test='${cuscom.state=="1"}'>个人会员名片</c:if><c:if test='${cuscom.state=="2"}'>公司会员名片</c:if></a></td>
                                <td width="15%" align="right"><img style="width:50%;" src="<%=basePath%>images/contacts/recepit/wfj_return_06.png" /></td>
                            </tr>
                            <tr onclick="oprateManage('wealth');">
                                <td align="left"><img src="<%=basePath%>images/contacts/recepit/wfj_vip_list_02.png" /></td>
                                <td><a href="javascript:"><c:if test='${cuscom.state=="1"}'>人脉财源</c:if><c:if test='${cuscom.state=="2"}'>公司人脉财源</c:if></a></td>
                                <td align="right"><img style="width:50%;" src="<%=basePath%>images/contacts/recepit/wfj_return_06.png" /></td>
                            </tr>
                            <c:if test="${cuscom.state=='2' }">
                            <tr onclick="oprateManage('productmanage');">
                                <td align="left"><img src="<%=basePath%>images/WFJClient/PersonalJoining/wfj_vip_list_10.png" /></td>
                                <td><a href="javascript:">产品管理</a></td>
                                <td align="right"><img style="width:50%;" src="<%=basePath%>images/contacts/recepit/wfj_return_06.png" /></td>
                            </tr>                           
                            <tr onclick="oprateManage('productlaunch');">
                                <td align="left"><img src="<%=basePath%>images/WFJClient/PersonalJoining/wfj_vip_list_09.png" /></td>
                                <td><a href="javascript:">产品发布</a></td>
                                <td align="right"><img style="width:50%;" src="<%=basePath%>images/contacts/recepit/wfj_return_06.png" /></td>
                            </tr>
                            </c:if>
                            <tr onclick="oprateManage('order');">
                                <td align="left"><img src="<%=basePath%>images/contacts/recepit/wfj_vip_list_03.png" /></td>
                                <td><a href="javascript:">订单管理</a></td>
                                <td align="right"><img style="width:50%;" src="<%=basePath%>images/contacts/recepit/wfj_return_06.png" /></td>
                            </tr>
                            <tr onclick="oprateManage('consignee')";>
                                <td align="left"><img src="<%=basePath%>images/contacts/recepit/wfj_vip_list_04.png" /></td>
                                <td><a href="javascript:">收货单</a></td>
                                <td align="right"><img style="width:50%;" src="<%=basePath%>images/contacts/recepit/wfj_return_06.png" /></td>
                            </tr>
                            <tr onclick="oprateManage('refund');">
                                <td align="left"><img src="<%=basePath%>images/contacts/recepit/wfj_vip_list_05.png" /></td>
                                <td><a href="javascript:">退货单</a></td>
                                <td align="right"><img style="width:50%;" src="<%=basePath%>images/contacts/recepit/wfj_return_06.png" /></td>
                            </tr>
                            <tr>
                                <td align="left"><img src="<%=basePath%>images/contacts/recepit/wfj_vip_list_06.png" /></td>
                                <td>现金管理</td>
                                <td align="right"><img style="width:50%;" src="<%=basePath%>images/contacts/recepit/wfj_return_06.png" /></td>
                            </tr>
                            <tr onclick="oprateManage('jinbi');">
                                <td align="left"><img src="<%=basePath%>images/contacts/recepit/wfj_vip_list_07.png" /></td>
                                <td><a href="javascript:">聚宝盆金币池</a></td>
                                <td align="right"><img style="width:50%;" src="<%=basePath%>images/contacts/recepit/wfj_return_06.png" /></td>
                            </tr>
                            <tr>
                                <td align="left"><img src="<%=basePath%>images/contacts/recepit/wfj_vip_list_08.png" /></td>
                                <td><a href="<%=basePath%>page/conacts/receipt/loadsite.jsp">下载微分金App</a></td>
                                <td align="right"><img style="width:50%;" src="<%=basePath%>images/contacts/recepit/wfj_return_06.png" /></td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        
            
        <!--会员中心 end-->
    </div>
    
    <script type="text/javascript">
    	$(document).ready(function(e) {
         	$("body").css("height",$(window).height()) ;
			//修改字体大小
			$(".wfj01_002_top").attr("style","height:"+$(window).height()*0.08+"px;line-height:"+$(window).height()*0.08+"px;");
		 
			$("#tops").find("li").attr("style","float:left;");
			$("#tops").find("li").eq(0).attr("style","width:15%;");
			$("#tops").find("li").eq(0).find("img").attr("style","height:"+$(window).height()*0.03+"px;padding-left:"+$(window).height()*0.02+"px; vertical-align:middle;");
			$("#tops").find("li").eq(1).attr("style","width:70%; text-align:center; font-size:"+$(window).height()*0.035+"px;");
			$("#tops").find("li").eq(2).attr("style","width:15%; text-align:center;");
			$("#tops").find("li").eq(2).find("img").attr("style","height:"+$(window).height()*0.03+"px; width:auto; vertical-align:middle;");
			
			
			
			$(".wfj01_002_title").attr("style","height:"+$(window).height()*0.15+"px;line-height:"+$(window).height()*0.15+"px;");
			$(".wfj01_002_title").find("td").attr("style","font-size:"+$(window).height()*0.02+"px;");
			$(".wfj01_002_title").find(".wfj01_002_changehtml").attr("style","width:80%;font-size:"+$(window).height()*0.025+"px;height:"+$(window).height()*0.045+"px; line-height:"+$(window).height()*0.045+"px; border-top-right-radius:"+$(window).height()*0.022+"px; border-bottom-right-radius:"+$(window).height()*0.022+"px;");
			$(".wfj01_002_title").find(".wfj01_002_introduce").attr("style","font-size:"+$(window).height()*0.021+"px;height:"+$(window).height()*0.045+"px; line-height:"+$(window).height()*0.045+"px; border-top-left-radius:"+$(window).height()*0.022+"px; border-bottom-left-radius:"+$(window).height()*0.022+"px;");
			$(".wfj01_002_title").find(".wfj01_002_title_top").attr("style","font-size:"+$(window).height()*0.02+"px;height:"+$(window).height()*0.15+"px; line-height:"+$(window).height()*0.15+"px; border-top-left-radius:"+$(window).height()*0.02+"px; border-bottom-left-radius:"+$(window).height()*0.02+"px;");

			$(".wfj01_002_hyimg").attr("style","height:"+$(window).height()*0.08+"px; width:auto;margin:"+$(window).height()*0.01+"px auto;");
			$(".wfj01_002_ewm").attr("style","height:"+$(window).height()*0.02+"px; width:auto;");			
			
			$(".wfj01_002_vip_data").find("td").attr("style","font-size:"+$(window).height()*0.032+"px;height:"+$(window).height()*0.1+"px;");
			$(".wfj01_002_vip_data").find("td").find("a").attr("style","font-size:"+$(window).height()*0.032+"px; color:#000;");
			
			$(".wfj01_002_vip_data").attr("style","height:"+$(window).height()*0.78+"px; overflow:auto;");
			if($(window).width()>$(window).height()){
				$(".wfj01_002").attr("style","width:70%;height:"+$(window).height()+"px;");
				$(".wfj01_002_content").attr("style","width:"+$(".wfj01_002").width()+"px;height:"+parseInt($(window).height()-$(".wfj01_002_top").height()-$(".wfj01_002_title").height())+"px;overflow:hidden;");
				$(".wfj01_002_hidden").attr("style","width:"+parseInt($(".wfj01_002_content").width()+17)+"px;height:"+$(".wfj01_002_content").height()+"px;overflow:auto;");
			}
			
			
			if(state=="1"){
	    		  $(".tpc").hide();
	    		  $(".tpc2").css("display","none");
	    		  
	    		}else{
	    		 $(".tpc").show();
	    		 $(".tpc2").show();
	    		}
		});
    	
    		


    	
	</script>
</body>
</html>