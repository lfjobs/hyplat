<!DOCTYPE html>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" import="hy.ea.bo.human.Staff" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="renderer" content="webkit">
    <title>立即购买</title>
    <link href="<%=basePath%>page/newMyapp/css/myStyle.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="<%=basePath%>page/newMyapp/js/jquery-1.9.1.min.js" ></script>
    <script type="text/javascript" src="<%=basePath%>js/ea/pcwfj/PC_goodsPayNow.js"></script>
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="js/html5shiv.min.js"></script>
    <script src="js/respond.min.js"></script>
    <![endif]-->
    <script type="text/javascript">
    	var basePath="<%=basePath%>";
    	var type="${map.obj[6]}";
    </script>
</head>
<body>
    <div class="content">
        <!--搜索-->
        <div class="mil search_mil sc_search">
            <a href="#;" class="logo"><img src="<%=basePath%>page/newMyapp/images/logo.png"></a>
            <h1>数字地球</h1>
            <img src="<%=basePath%>page/newMyapp/images/top1.png" class="top">
            <img src="<%=basePath%>page/newMyapp/images/top2.png" class="top" style="margin-right: 25px;">
        </div>
        <h5 class="gradient"></h5>
        <div class="mil address_mil">
            <div class="title">
                <h4><i></i>已保存的收货地址</h4>
                <h5>您已创建<span class="addressCount"></span>个收货地址，最多添加<span>10</span>个收货地址</h5>
            </div>
            <ul class="add_con">
            	<!-- js拼接 -->
            </ul>
            <a href="javascript:void(0);" ><button type="button" id="add_address">管理收货地址</button></a>
        </div>
        <div class="mil address_mil add_address select_bid address_bid">
        	<form id="mainForm" type="post">
        		<c:if test="${param.showParam==null}">
            		<div class="title">
                		<h4><i></i>${map.company.companyName}</h4>
            		</div>
            		<ul class="con_bid">
                		<li>
                    		<img src="<%=basePath%>${map.obj[3]}" class="shop" id="goodsShop">
                			<input type="hidden" class="ppID" value="${map.obj[1]}">
                    		<div class="txt">
                        		<p>${map.obj[0]}</p>
                        		<h5>规格：${param.standard}</h5>
                    		</div>
                    		<div class="unit-price">
                    			<h5>单价</h5>
                    	 		<p class="money">&yen;<span>${map.obj[2]}</span></p>
                    		</div>
	                    	<div class="amount">
	                     		<h5>数量</h5>
	                    		<div class="choose_bid_">
	                        		<div class="choose_bid">
	                        			<input type="button" value="-" class="jian" />
	                            			<p class="nub">${param.count}</p>
	                           			<input type="button" value="+" class="jia" />
	                        		</div>
	                    		</div>
	                     	</div>
	                    	<div class="total">
	                    		<h5>合计</h5>
	                    		<p class="all-money">&yen;<span>${map.totalPrice}</span></p>
							</div>
	                	</li>
	                	<c:if test="${map.ppt!=null&&map.ppt.size()>0}">
	                		<!--赠品-->
	                		<c:forEach items="${map.ppt}" var="ppt">
	                			<li class="gift">
	                    			<div class="shop_img">
	                        			<img src="<%=basePath%>${ppt[3]}" class="shop">
	                        			<input type="hidden" class="pptID" value="${ppt[1]}">
	                        			<p>赠</p>
	                    			</div>
	                    			<div class="txt">
	                    				<input type="hidden" name="ptppid" class="ptppids" value="${ppt[1]}" />
	                        			<p>${ppt[0]}</p>
	                        			<c:forEach items="${map.ptStandardMap}" var="ptStandardEntry">
	                        				<c:if test="${ptStandardEntry.key==ppt[1]}">
	                        					<h5>规格：${ptStandardEntry.value}</h5>
	                    						<input type="hidden" name="ptStandard" class="ptStandards" value="${ptStandardEntry.value}" />
	                        				</c:if>
	                        			</c:forEach>
	                    			</div>
	                    			<div class="giftPrice">
	                    				<p class="money">&yen;<span>${ppt[2]}</span></p>
	                    			</div>
	                    			<div class="giftAmount">
	                    				<div class="choose_bid_">
	                    					<div class="giftNub">
	                    						<p>1</p>
	                    					</div>
	                    				</div>
	                    		 	</div>
	               	 			</li>
	                    	</c:forEach>
	                	</c:if>
	            	</ul>
	            	<c:if test="${map.drivingSchoolGoods=='drivingSchoolGoods'}">
						<div class="information">				
					    	<h4 class="title">学员信息：</h4>
					        <ul>
					        	<li>
					            	<p>姓名：</p>
					             	<div class="right">
					                	<input type="text" name="staff.accountName" id="accountName" placeholder="请输入姓名" onblur="this.value=this.value.replace(/^\s+|\s+$/g,'')">
					                </div>
					             </li>
					             <li>
					                 <p>电话：</p>
					                 <div class="right">
					                 	<input type="text" name="staff.reference" id="reference" placeholder="请输入联系方式" onblur="this.value=this.value.replace(/^\s+|\s+$/g,'')">
					                 </div>
					             </li>
					             <li>
					             	 <p>身份证号：</p>
					                 <div class="right">
					                 	<input type="text" name="staff.staffIdentityCard" placeholder="请输入身份证号" onblur="this.value=this.value.replace(/^\s+|\s+$/g,'')">
					                  </div>
					              </li>
					              <li>
					              	  <p>地址：</p>
					                  <div class="right">
					                            <input type="text" name="staff.staffAddress" id="staffAddress" placeholder="请输入常用地址" onblur="this.value=this.value.replace(/^\s+|\s+$/g,'')">
					                  </div>
					              </li>
					         </ul>
					     </div>
				    </c:if>
	            	<div class="btn">
	                	<button type="button" class="freight">运费</button>
	                	<p>本产品免运费</p>
	            	</div>
	            	<input type="hidden" name="ppk.ppID" id="ppID" value="${map.obj[1]}" /> 
	        		<input type="hidden" name="standard" id="standard" value="${param.standard}" />
	        		<input type="hidden" name="ptppMoney" id="ptppMoney" value="${param.giftMoney}">
	        		<input type="hidden" name="totalMoney" id="totalMoney" />
	        		<input type="hidden" name="count" id="count" />
	        	</c:if>
            	<c:if test="${param.showParam eq 'payShoppingCart'}">
            		<c:forEach items="${map.goodsMap}" var="goodsEntry" varStatus="status">
           				<div class="title">
                			<h4><i></i>${goodsEntry.key}</h4>
                			<input type="hidden" name="companyIds" class="company">
            			</div>
            			<ul class="con_bid">
            				<c:forEach items="${goodsEntry.value}" var="goods" varStatus="status">
                				<li>
                    				<img src="<%=basePath%>${goods[3]}" id="goodsShop" class="shop">
                    				<input type="hidden" name="cartIds" class="cartIds" value="${goods[0]}">
                    				<input type="hidden" class="ppID" value="${goods[1]}">
                    				<input type="hidden" class="companyID" value="${goods[5]}">
                    				<div class="txt">
	                        			<p>${goods[2]}</p>
	                        			<h5>规格：${goods[7]}</h5>
	                    			</div>
	                    			<div class="unit-price">
	                    				<h5>单价</h5>
	                    	 			<p class="money">&yen;<span>${goods[4]}</span></p>
	                    			</div>
	                    			<div class="amount">
	                     				<h5>数量</h5>
	                    				<div class="choose_bid_">
	                        				<div class="choose_bid">
	                        					<input type="button" value="-" class="jian" />
	                            					<p class="nub">${goods[6]}</p>
	                            					<input type="hidden" name="counts" class="counts" value="${goods[6]}"> 
	                           					<input type="button" value="+" class="jia" />
	                        				</div>
	                    				</div>
	                     			</div>
	                    			<div class="total">
	                    				<h5>合计</h5>
	                    				<p class="all-money">&yen;<span></span></p>
									</div>
	                			</li>
	                			<!--赠品-->
	                			<c:forEach items="${map.giftList}" var="gift">
	                				<c:if test="${gift[0]==goods[0]}">
	                					<li class="gift">
	                    					<div class="shop_img">
	                        					<img src="<%=basePath%>${gift[8]}" class="shop">
	                        					<input type="hidden" class="pptID" value="${gift[2]}">
	                        					<p>赠</p>
	                    					</div>
	                    					<div class="txt">
	                        					<p>${gift[6]}</p>
	                        					<h5>规格：${gift[3]}</h5>
	                    					</div>
	                    					<div class="giftPrice">
	                    						<p class="money">&yen;<span>${gift[7]}</span></p>
	                    					</div>
	                    					<div class="giftAmount">
	                    						<div class="choose_bid_">
	                    							<div class="giftNub">
	                    								<p>${gift[4]}</p>
	                    							</div>
	                    						</div>
	                    		 			</div>
	               	 					</li>
	               	 				</c:if>
	                    		</c:forEach>
	                		</c:forEach>
	            		</ul>
	            		<c:forEach items="${map}" var="comEntry">
				            <c:if test="${comEntry.key==goodsEntry.key && comEntry.value=='drivingSchoolGoods'}">
					            <div class="information">				
					                <h4 class="title">学员信息：</h4>
					                <ul>
					                    <li>
					                        <p>姓名：</p>
					                        <div class="right">
					                            <input type="text" name="accountNames" id="accountName" placeholder="请输入姓名" onblur="this.value=this.value.replace(/^\s+|\s+$/g,'')">
					                        </div>
					                    </li>
					                    <li>
					                        <p>电话：</p>
					                        <div class="right">
					                            <input type="text" name="references" id="reference" placeholder="请输入联系方式" onblur="this.value=this.value.replace(/^\s+|\s+$/g,'')">
					                        </div>
					                    </li>
					                    <li>
					                        <p>身份证号：</p>
					                        <div class="right">
					                            <input type="text" name="staffIdentityCards" placeholder="请输入身份证号" onblur="this.value=this.value.replace(/^\s+|\s+$/g,'')">
					                        </div>
					                    </li>
					                    <li>
					                        <p>地址：</p>
					                        <div class="right">
					                            <input type="text" name="staffAddresses" id="staffAddress" placeholder="请输入常用地址" onblur="this.value=this.value.replace(/^\s+|\s+$/g,'')">
					                        </div>
					                    </li>
					                </ul>
					                <input type="hidden" name="companyNames" value="${comEntry.key}">
					            </div>
				            </c:if>
			            </c:forEach>
	            		<div class="btn">
	                		<button type="button" class="freight">运费</button>
	                		<p>本产品免运费</p>
	            		</div>
	        		</c:forEach>
	        		<input type="hidden" name="showParam" id="showParam" value="${param.showParam}">
	            </c:if>
	            <input type="hidden" name="staffAddress.addressID" id="addressID" />
	            <input type="hidden" id="formLimit" value="false" />
	            <div class="close_btn">
	                <p>合计费用:<span id="total">${map.totalPrice}</span></p>
	                <button type="button" id="makeBills">确认订单</button>
	            </div>
            </form>
        </div>
    </div>
    <!--热卖商品-->
    <div class="mil_shop_con hot_shop">
        <ul>
            <!-- js拼接 -->
        </ul>
    </div>
    <div class="det-add_alert_">
        <div class="det-add_alert">
            <h2>确认是否删除此收货地址？</h2>
            <div class="btn">
                <button id="abolish">取消</button>
                <button id="sure" style="background-color: #ff6600;">确认</button>
            </div>
        </div>
    </div>
    <!--尾部-->
    <div id="footer">
        <div class="text footer_txt">
           <!--  <p><a href="#;">天使云服务</a><a href="#;">联系我们</a><a href="#;">商务合作</a><a href="#;">网站地图</a><a href="#;">免责声明</a></p> -->
            <p>Copyright &copy; 2010-2015北京天太世统科技有限公司<span style="padding-left: 50px;">京ICP备10034132号 </span></p>
            <p>公司地址：北京市东城区东直门外大街42号宇飞大厦8层801室<span>热线电话:010-64167113</span></p>
            <p>版权所有：北京天太世统科技有限公司</p>
        </div>
    </div>
</body>
</html>