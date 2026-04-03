<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>微分金</title>
     <meta http-equiv="Access-Control-Allow-Origin" content="*">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link href="<%=basePath%>css/WFJClient/style.css" rel="stylesheet" type="text/css"/>
	<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
  </head>
  
  <body>
  <iframe id="indexTop" align="center" height="46px" width="100%"  src="<%=basePath %>page/WFJClient/Template/Top/Label.jsp" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no"></iframe>
	   <ul id="menuMore" class="menuMore" >
        <li><a href="javascript:;"><img alt="" title="" src="<%=basePath %>images/WFJClient/message.png"/> <span>消息</span> </a> </li>
        <li><a href="javascript:;"><img alt="" title="" src="<%=basePath %>images/WFJClient/homeMore.png"/> <span>首页</span> </a> </li>
       </ul>
	   <div class="con" >
	   	<div class="so fl">
	        <div class="search">
	        	<form action="<%=basePath%>/ea/wfjshop/ea_getShopProducts.jspa" id="submit">
	            	<input type="text" value="" name="productDesign.goodsName" placeholder="搜宝贝"/>
	            	<input type="hidden" value="${beans[0][0]}" name="organizationID"/>
	            	<input type="hidden" name="search" value="search"/>
	            	<input type="hidden" name="comId" class="companyId" value="<%=request.getParameter("comId") %>"/>
	            	<input type="hidden" name="indus" value="${indus }"/>
	            </form>
	            <img alt="" title="" id="search" src="<%=basePath%>images/WFJClient/sos.png"/>
	        </div>
	   	 </div>
    	<div class="clear"></div>
	    <div class="shopAd">
	        <div class="shopLogo fl">
	            <div class="shopLogoDiv">
	            	<c:choose>
				       <c:when test="${beans[0][2]==null}">
				       		<img src="<%=basePath %>/images/WFJClient/zwtp160.png" style="width: 50px;height: 50px;"/>
				       </c:when>
				       <c:otherwise>
				            <img src="<%=basePath%>${beans[0][2]}" style="width: 50px;height: 50px;"/>
				       </c:otherwise>
				    </c:choose>
	            </div>
	        </div>
	                          
	    </div>
	    <p class="shopName"><a href="<%=basePath%>/ea/wfjshop/ea_getShopDetail.jspa?organizationID=${beans[0][0]}">${beans[0][1]}</a></p>
	    <div class="clear"></div>
	    <form id="addCartForm" name="addCartForm" action="<%=basePath %>/ea/buyproducts/ea_putInCart.jspa" method="post">
	    <div class="storeContent storeContentDis fl">
	  
	     <s:iterator value="productList" id="arr">
	        <div class="store fl" >
	            <a href="<%=basePath%>/ea/wfjshop/ea_doodsDetail.jspa?ppid=${arr[0]}&organizationID=${beans[0][0]}&goodsid=${arr[5]}&comId=${arr[7]}">
	                <c:choose>
				       <c:when test="${arr[2]==null}">
				       		<img alt="" src="<%=basePath %>/images/WFJClient/zwtp160.png" />
				       </c:when>
				       <c:otherwise>
				            <img alt="" title="" src="<%=basePath%>${arr[2]}" style="height: 160px;width:160px"/>
				       </c:otherwise>
				    </c:choose>
				</a>
	                <!-- <p>${arr[4]}</p>店铺所属公司名 -->
	                 <c:if test='${beans[0][1]!=null}'>
	                     <p>${beans[0][1]}</p>
    		         </c:if>
	                <p>${arr[1]}</p>
	                <span style="width:85%;">￥：${arr[3]}</span><c:if test='${arr[6]=="00"}'>
	                      <c:if test='${arr[7]=="0"}'>
	                            <input type="checkbox" disabled="disabled" class="checkb" value="${arr[0]}"/>
    		             </c:if>
    		             <c:if test='${arr[7]!="0"}'>
	                            <input type="checkbox" class="checkb" value="${arr[0]}"/>
    		             </c:if>
    		       </c:if>
    		       
    		       <c:if test='${arr[6]=="01"}'>
	                  <input type="checkbox" class="checkb" value="${arr[0]}"/>
    		       </c:if>
    		       <c:if test='${arr[6]==null}'>
	                  <input type="checkbox" class="checkb" value="${arr[0]}"/>
    		       </c:if>
    	         
	            
	        </div>
	     <div id="${arr[0]}">
	      <input id="pid" name="pid" type="hidden" value="${arr[0]}" /> 
	      <input id="pname" name="pname" type="hidden" value="${arr[1]}" /> 
	      <input id="pic" name="pic" type="hidden" value="${arr[2]}" /> 
	      <input id="price" name="price" type="hidden" value="${arr[3]}" /> 
	      <input id="orgnizationId" name="orgnizationId" class="orgnizationId" type="hidden" value="${beans[0][0]}" /> 
	      <input id="orgnizationName" name="orgnizationName" class="orgnizationName" type="hidden" value="${beans[0][1]}" /> 
	      <input id="companyId" name="companyId" class="companyId" type="hidden" value="${beans[0][3]}" /> 
		  <!-- <input id="itemNum" name="itemNum" type="hidden"  value=<c:if test='${arr[7]!=null}'>${arr[7]}</c:if><c:if test='${arr[7]==null}'>1</c:if> > -->
		  <input id="itemNum" name="itemNum" type="hidden"  value="1" />
		  <input id="invenQuantity" name="invenQuantity" type="hidden" value="${arr[7]}"/><!-- 库存数量 -->
		 </div> 
	      
	     </s:iterator>
	    </div>
	    </form>
	    <div class="clear"></div>
	</div>
	
<!-- 	<div class="floorNav fexBottom"> -->
<%-- 	    <div style="width:25%;"><a id="navHover" href="javascript:;" target="_parent"><img alt="" title="" src="<%=basePath %>images/WFJClient/productNavHover.png"/> </a> </div> --%>
<%-- 	    <div style="width:25%;"><a id="buyCommHover" href="javascript:;" target="_parent"><img alt="" title="" src="<%=basePath %>images/WFJClient/buyCommHover.png"/> </a> </div>加入购物车 --%>
<%-- 	    <div style="width:25%;"><a id="shopConHover" href="<%=basePath%>/ea/wfjshop/ea_getShopDetail.jspa?organizationID=${beans[0][0]}" target="_parent"><img alt="" title="" src="<%=basePath %>images/WFJClient/shopConHover.png"/> </a> </div> --%>
<%-- 	    <div style="width:25%;"><a id="woWHover" href="javascript:;" target="_parent"><img alt="" title="" src="<%=basePath %>images/WFJClient/WoWHover.png"/> </a> </div> --%>
<!-- 	</div> -->
	<div class="protype" style="display: none;" id="protype">
	  <table>
	  	<s:iterator value="proType" id="pro">
		  	<tr>
		  		<td><a href="<%=basePath%>/ea/wfjshop/ea_getShopProducts.jspa?productDesign.weiDianType=${pro.codeID}&comId=${pro.companyID}&indus=${indus}">${pro.codeValue }</a></td>
		  	</tr>
	  	</s:iterator>
	  </table>
	</div>
	<div id="jqmWindow2" class="jqmWindow" 
		style=" display: none;  position:fixed;left:5%;top:50px;width: 90%; height:80%;background: #eff">
		<div align="center">
			<iframe name="ifr" id="ifr" width="100%" height="100%" frameborder="0"></iframe>
			<input type="button" class="input-button" id="isSubmit" value=" 确定 "
				style="width: 20%;height:25px;" />
			<input type="button" class="input-button" id="isBack" value=" 关闭 "
				style="width: 20%;height:25px;" />
		</div>
	 </div>
	<%-- <iframe id="indexBottom" align="center" height="46px" width="100%" src="<%=basePath %>page/WFJClient/Template/Bottom/CategoriesShopComm.jsp" 
	frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no"></iframe>   --%> 
  </div>
  </body>
  <script type="text/javascript" src="<%=basePath %>/js/WFJClient/topMore.js"></script>
  <script type="text/javascript">
  $(function() {
	if($("#orgnizationId").val()==null || $("#orgnizationId").val()==""){
		$(".store").height(200);
		$(".shopAd").hide();
		$(".shopName").hide();
		}
	  });
  	$('#indexTop').load(function () {
		var doc=document.getElementById("indexTop").contentWindow.document;
		doc.getElementById("topbar_title").innerHTML = "微分金";
		
	});		
  	$("#search").click (function(){
		$("#submit").submit();
  	  	});
  	
	$("#navHover").click(function(){	
		if(!this.isok){
  	 		this.isok=true;
 	    	$("#protype").show();
  	 	}else{
  	 		this.isok=false;
  	 		$("#protype").hide();
  	 	}
  		});
  	$("#buyCommHover").click(function(){	
  		
  		if($(".checkb:checked").length==0){
			alert("请选择产品！");
  			return;
  		};
  		var num =1;
		$(".checkb:checked").each(function(){
  			var ppid = $(this).val();
  			$("div#"+ppid).find("input").each(function(){
  			  $(this).attr("name","cartItemmap["+ num+ "]."+ this.name);
  			});
  			num++;
  		});
  		//选择微分金店
  		if($("#orgnizationId").val()==null || $("#orgnizationId").val()==""){
			alert("请选择微分金店！");
			$("#ifr").attr("src","<%=basePath%>/ea/wfjshop/ea_getWFJshops.jspa?search=searchShops&companyId=<%=request.getParameter("comId")%>&activity=activity&indus=<%=request.getParameter("indus")%>");
		   	$("#jqmWindow2").show();
  			return;
  		};
  			
		$("#addCartForm").submit();
	});
  	$("#isBack").click(function(){// 返回
	       $("#jqmWindow2").hide();
	    }); 
	   
	$("#isSubmit").click(function(){// 选择确定
		var value1 = window.frames["ifr"].personvalue;
		if(value1 == ""){
			alert("请选择一个店铺！")
			return;
		}
		var value2 = window.frames["ifr"].$('tr#'+value1).find("li#name").text();
		var id = window.frames["ifr"].$('tr#'+value1).find("li#id").text();
		$(".f4").html(value2);
		$(".f4").css('color','#666');
		$("input[class=orgnizationId]").each(function(){
			$(this).val(value1);
			});
		$("input[class=orgnizationName]").each(function(){
			$(this).val(value2);
			});
		$("input[class=companyId]").each(function(){
			$(this).val(id);
			});
		
		$("#ifr").attr("src","");
     $("#jqmWindow2").hide();
     $("#addCartForm").submit();
 });
   <%--	$('#indexBottom').load(function () {
  		var doc=document.getElementById("indexBottom").contentWindow.document;
  		doc.getElementById("shopConHover").href="<%=basePath%>/ea/wfjshop/ea_getShopDetail.jspa?organizationID=${beans[0][0]}";	
	});--%>
  
 </script>
</html>
