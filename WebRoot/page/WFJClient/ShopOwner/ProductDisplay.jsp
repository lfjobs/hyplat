<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>行业分类</title>

<meta http-equiv="Access-Control-Allow-Origin" content="*">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<link rel="stylesheet" type="text/css"
	href="<%=basePath%>css/WFJClient/style2.css" />
<script type="text/javascript" src="<%=basePath%>js/jquery-1.6.1.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/ea/marketing/productDisplay.js"></script>

</head>

<body>
	<div class="wfj10_005">
		<div class="more" style="position: fixed; z-index: 10; background-color: #FFF; width: 100%; margin: 0 auto; min-width: 320px; max-width: 800px; height: 130px;">
			<iframe id="indexTop" align="center" width="100%" height="46px"
				src="<%=basePath%>page/WFJClient/Template/Top/Label.jsp"
				frameborder="no" border="0" marginwidth="0" marginheight="0"
				scrolling="no"></iframe>
			<ul id="menuMore" class="menuMore" >
        		<li><a href="javascript:;"><img alt="" title="" src="<%=basePath %>images/WFJClient/message.png"/> <span>消息</span> </a> </li>
        		<li><a href="javascript:;"><img alt="" title="" src="<%=basePath %>images/WFJClient/homeMore.png"/> <span>首页</span> </a> </li>
       		</ul>
       		
			<div class="wfj10_005_search">
				<div class="wfj10_005_search_width">
					<div class="wfj10_005_search_border">
						<form action="<%=basePath%>/ea/industry/ea_getWFJshopsAndProducts.jspa" id="submit">
							<ul>
								<li style="width: 80%;"><input id="search" type="text" name=""/></li>
								<li style="width: 20%; text-align: right;">
            						<input id="s1" type="hidden" name="search" />
            						<input type="hidden" name="companyId" value="<%=request.getParameter("companyId")%>"/>
	        						<input type="hidden" name="comId" class="companyId" value="<%=request.getParameter("comId") %>"/>
							<img id="search1"
								src="<%=basePath%>images/WFJClient/ProductDisplay/search_03.png" /></li>
						</ul>
						</form>
					</div>
				</div>
			</div>
			<div class="wfj10_005_content_title">
				<div>
					<ul>
						<li class="wfj10_005_title_left"><div
								onclick="change_agencys(this)" name="change">产品展示</div></li>
						<li class="wfj10_005_title_right"><div
								onclick="change_agencys(this)" name="change">店铺展示</div></li>
					</ul>
				</div>
			</div>
			<div class="wfj10_005_title_bg">
				<ul>
					<li style="width: 50%;"><img id="img1"
						src="<%=basePath%>images/WFJClient/ProductDisplay/title_bg_01.png" /></a></li>
					<li style="width: 50%; position: relative; left: 1%;"><img
						id="img2"
						src="<%=basePath%>images/WFJClient/ProductDisplay/title_bg_02.png" /></a></li>
				</ul>
			</div>
		</div>

		        <!--店铺-->
        <div id="shop" style="display:none;" class="wfj10_005_content_shop">
        	<div class="wfj10_005_shop_width">
        	<s:iterator value="beans" id="arr">
            	<div class="wfj10_005_content_shop_left">
                	<ul>
                	
                    	<li><a href="<%=basePath%>/ea/wfjshop/ea_getShopProducts.jspa?organizationID=${arr[0]}&comId=${arr[5]}">
				    	<img class="store" alt="" title="" src="<%=basePath %>${arr[4]==null ? '/images/WFJClient/zwtp160.png' : arr[4]}" /></a></li>
                        <div style="width:94%; margin:0 3%;">
                            <li>${arr[1] }</li>
                            <li><span>${arr[2] }</span></li>
                            <li class="right"><a href="javascript:;">加盟</a></li>
                        </div>
                      
                    </ul>
                </div>
                 </s:iterator>  
            </div>
        </div>
        <!--店铺 end-->
        
        
        <!--产品-->
        <div id="product" class="wfj10_005_content_product">
        	<div class="wfj10_005_content_pro_width">
            	<div class="wfj10_005_hidden" id="pro">
                	<ul id="daohang">
                    </ul>
                </div>
                <div class="wfj10_005_fu">
                	<ul>
                    	<li id="left">></li>
                    </ul>
                </div>
                 
                <div class="wfj10_005_product">
                	<div class="wfj10_005_pro">
                    	<ul>
                    	<s:iterator value="productList" id="arr">
							<li id="products" class="hei">
								<div style="height:60%;"><a
								href="<%=basePath%>/ea/wfjshop/ea_doodsDetail.jspa?ppid=${arr[0]}&organizationID=${beans[0][0]}&goodsid=${arr[5]}&comId=${arr[7]}">
									 <c:choose>
										<c:when test="${arr[2]==null}">
											<img alt="" style="height:100%; width:100%; max-width:160px; max-height:160px;" src="<%=basePath%>/images/WFJClient/zwtp160.png" />
										</c:when>
										<c:otherwise>
											<img alt="" title="" src="<%=basePath%>${arr[2]}"
												style="height:100%; width:100%; max-width:160px; max-height:160px;" />
										</c:otherwise>
									</c:choose> </a>
								</div>
								<div>
									<ul>
										<li id="proname" style="width: 100%;height: 60%;text-overflow: ellipsis;-o-text-overflow: ellipsis;white-space: nowrap;overflow: hidden;">${arr[1]}</li>
									</ul>
									<ul id="price">
										<li class="left" style="width: 50%; text-align: left;"><span
											style="color: #F00;">￥${arr[3]}</span></li>
										<li class="right" style="width: 50%; text-align: right;">
											<c:if test='${arr[6]=="00"}'>
	                      						<c:if test='${arr[7]=="0"}'>
	                            					<input type="checkbox" disabled="disabled" value="${arr[0]}"/>
    		             						</c:if>
    		             						<c:if test='${arr[7]!="0"}'>
	                            					<input type="checkbox" value="${arr[0]}"/>
    		             						</c:if>
    		      							 </c:if>
    		      							 <c:if test='${arr[6]=="01"}'>
	                  								<input type="checkbox" class="checkb" value="${arr[0]}"/>
    		      							 </c:if>
    		       							<c:if test='${arr[6]==null}'>
	                  								<input type="checkbox" class="checkb" value="${arr[0]}"/>
    		       							</c:if>
    		      							 </li>
									</ul>
									<div id="${arr[0]}">
	      								<input id="pid" name="pid" type="hidden" value="${arr[0]}" /> 
							      		<input id="pname" name="pname" type="hidden" value="${arr[1]}" /> 
									    <input id="pic" name="pic" type="hidden" value="${arr[2]}" /> 
									    <input id="price" name="price" type="hidden" value="${arr[3]}" /> 
									    <input id="orgnizationId" name="orgnizationId" class="orgnizationId" type="hidden" value="${beans[0][0]}" /> 
									    <input id="orgnizationName" name="orgnizationName" class="orgnizationName" type="hidden" value="${beans[0][1]}" /> 
									    <input id="companyId" name="companyId" class="companyId" type="hidden" value="${beans[0][3]}" /> 
										<input id="itemNum" name="itemNum" type="hidden"  value="1" />
										<input id="invenQuantity" name="invenQuantity" type="hidden" value="${arr[7]}"/><!-- 库存数量 -->
		 							</div> 
								</div>
							</li>
							</s:iterator>
                        </ul>
                    </div>
                </div>
            </div>
        </div>

        <script type="text/javascript">
    	var change = document.getElementsByName("change");
    	var img1 = document.getElementById("img1");
    	var img2 = document.getElementById("img2");
    	var shop = document.getElementById("shop");
    	var product = document.getElementById("product");
    	var basePath='<%=basePath%>';
    	$(document).ready(function(){
			$(".hei").attr("style","height:"+($(window).height())/4.5+"px;");
			$(".hei").find("img").css("maxHeight",($(window).height())/5+"px");			
    	});
    	
  		$("#search1").click (function(){
  			$("#submit").submit();
  	  	  	});
        </script>
        <!--产品 end-->
    </div>
</body>
<script type="text/javascript" src="<%=basePath%>/js/WFJClient/topMore.js"></script>
</html>