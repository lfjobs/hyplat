<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <meta name="format-detection" content="telephone=no, email=no" />
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
    <script type="text/javascript" src="<%=basePath%>js/BuildPlatform/setHtmlFont.js"></script>
    <link rel="stylesheet" href="<%=basePath%>css/ea/finance/Gold_Pool/base.css">
    <link rel="stylesheet" href="<%=basePath%>css/ea/finance/Gold_Pool/gold_pool.css">
    <script src="<%=basePath%>js/ea/finance/Gold_Pool/jquery.min.js"></script>

    <title>我的钱包</title>
    	
   	<script type="text/javascript">
		var khd="${khd}";//去除表头
		var basePath="<%=basePath%>";
    	var user="${user}";
    	var sccid="${sccid}";
    	var staffid="${staffid}";
    	var flag = "${flag}"; //判断   移动办公    个人中心进入
    	var identifying = "${identifying}"; //判断 个人  登录还是公司登录（移动办公）       	
    	var staffName = "${staff.staffName}";
    	var ccompanyId="${ccompanyId}"
    	var state="";
    	/*var pp = "${pp}";   */
    	var companyId="company201009046vxdyzy4wg0000000025"; //测试签到公司
    	var object = new Array();
    	$(function(){
            var ua = navigator.userAgent.toLowerCase();
        var isWeixin = ua.indexOf('micromessenger') != -1;
        if (isWeixin) {
               $(".nav_list_08,.nav_list_04,.nav_list_03").parents("a").hide();


         }
        });
	</script>

	<script src="<%=basePath%>js/ea/finance/Gold_Pool/goldPool.js"></script>
</head>
<body>
<input type="hidden" id="wfjJifenId" value="${jifen.wfjJifenId}"/>
<input type="hidden" id="bonusPointsId" value="${bp.bonusPointsId}">
	<c:choose>
		<c:when test="${khd == 0}">
			<header class="com_head">
				<c:choose>
	       			<c:when test="${flag == 'sys'}">
	       				<a href="<%=basePath%>/mobile/office/mobileoffice_fastApplication.jspa" class="back"></a>      				       			       				        			     			
	       			</c:when>
	       			<%--<c:otherwise>--%>
	       				<%--<a href="<%=basePath%>/ea/consignee/ea_toVipCenter.jspa?backu=2" class="back"></a>     					        				        			--%>
	       			<%--</c:otherwise>--%>
	       			<c:otherwise>
						<a href="javascript:history.go(-1)" target="_self" class="back"></a>
					</c:otherwise>
	       		</c:choose> 		
	        <h1>我的钱包</h1>
	   	    </header>	
		</c:when>
		<c:otherwise>
			<style type='text/css'>
				.wrap_page{
					margin-top:0;
					padding-top:0;
				}
			</style>				
		</c:otherwise>
	</c:choose>
    <div class="wrap_page gold_wrap">
       <a href="<%=basePath%>/ea/jinbi/ea_Platform.jspa?staffid=${staffid}&sccid=${sccid}&user=${user }&flag=${flag }&khd=${khd}&identifying=${identifying}&ccompanyId=${ccompanyId}&type=1" class="per_info clearfix">
           <c:choose>
				<c:when test="${staff.headimage != null }">
					<img src="<%=basePath %>${staff.headimage }" class="per_headimg" alt=""/>																		
				</c:when>
				<c:otherwise>
					<img src="<%=basePath %>images/WFJClient/VipCenter/headimage.png" class="per_headimg" alt=""/>																											
				</c:otherwise>								
			</c:choose>	
                                 
           <div class="info_text">
               <div class="text_top">               
                   <span id='cusname'></span>
               </div>
               <div class="text_bottom"></div>
           </div>
       </a>
	   <div class="nav_list_wrap">
			<div class="list_part">
                <a href="###" class="nav_list_box bank_card bank_num">
                    <i class="nav_list_01"></i>
                    <span>我的银行卡</span>
                    <small class="part_banknum"></small>
                </a>
            </div>
			<c:choose>
       			<c:when test="${flag == 'sys'}">
       				<c:choose>
       					<c:when test="${identifying != 'company'}"> 
							<div class="list_part">
							   <a href="<%=basePath%>/ea/jinbi/ea_gethyjifenBill.jspa?user=${user}&sccid=${sccid}&khd=${khd}&flag=${flag}&identifying=${identifying}&ccompanyId=${ccompanyId}&staffid=${staffid}" class="nav_list_box  gold_num">
									<i class="nav_list_02"></i>
									<span>现金数（分）</span>
									<small class="part_goldnum"><fmt:formatNumber value="${jifen.wfjJifenScore==null?0:jifen.wfjJifenScore }"></fmt:formatNumber></small>
								</a>
								<a href="javascript:;" class="nav_list_box "><i class="nav_list_03"></i><span>现金兑换</span></a>
								<a href="javascript:;" class="nav_list_box "><i class="nav_list_04"></i><span>金币充值</span></a>
								<a href="<%=basePath%>/ea/jinbi/ea_gethyjifenBill.jspa?user=${user}&sccid=${sccid}&khd=${khd}&flag=${flag}&identifying=${identifying}&ccompanyId=${ccompanyId}&staffid=${staffid}"  class="nav_list_box "><i class="nav_list_05"></i><span>兑换账单</span></a>
								<a href="<%=basePath%>/ea/jinbi/ea_getFriends.jspa?user=${user}&sccid=${sccid}&khd=${khd}&flag=${flag}&identifying=${identifying}&ccompanyId=${ccompanyId}&staffid=${staffid}"  class="nav_list_box "><i class="nav_list_06"></i><span>好友排名</span></a>
							</div>
							<div class="list_part">
								<a href="###" class="nav_list_box  gold_num"><i class="nav_list_07"></i>									
									<span>积分数</span>
									<small class="part_goldnum"><fmt:formatNumber value="${bp.bonusPointScore==null?0:bp.bonusPointScore }"></fmt:formatNumber></small>
								</a>
								<a href="<%=basePath%>/ea/bonuspoints/ea_getWfjJifen.jspa?user=${user}&sccid=${sccid }&khd=${khd}&flag=${flag}"  class="nav_list_box "><i class="nav_list_08"></i><span>积分充值</span></a>
								<a href="<%=basePath%>/ea/bonuspoints/ea_getbpDetail.jspa?account=${user}&sccid=${sccid}&khd=${khd}&flag=${flag}&staffid=${staffid}&identifying=${identifying}&ccompanyId=${ccompanyId}"  class="nav_list_box ">
									<i class="nav_list_09"></i>
									<span>积分账单</span>
								</a>								
							</div>
						</c:when>
       					<c:otherwise>
							<div class="list_part">
							   <a href="<%=basePath%>/ea/jinbi/ea_gethyjifenBill.jspa?user=${user}&sccid=${sccid}&khd=${khd}&flag=${flag}&identifying=${identifying}&ccompanyId=${ccompanyId}&staffid=${staffid}" class="nav_list_box  gold_num">
									<i class="nav_list_02"></i>
									<span>现金数（分）</span>
									<small class="part_goldnum"><fmt:formatNumber value="${jifen.wfjJifenScore==null?0:jifen.wfjJifenScore }"></fmt:formatNumber></small>
								</a>
								<a href="javascript:;" class="nav_list_box "><i class="nav_list_03"></i><span>现金兑换</span></a>
								<a href="javascript:;" class="nav_list_box "><i class="nav_list_04" ></i><span>金币充值</span></a>
								<a href="<%=basePath%>/ea/jinbi/ea_gethyjifenBill.jspa?user=${user}&sccid=${sccid}&khd=${khd}&flag=${flag}&identifying=${identifying}&ccompanyId=${ccompanyId}&staffid=${staffid}"  class="nav_list_box "><i class="nav_list_05"></i><span>兑换账单</span></a>
								<a href="<%=basePath%>/ea/jinbi/ea_getFriends.jspa?user=${user}&sccid=${sccid}&khd=${khd}&flag=${flag}&identifying=${identifying}&ccompanyId=${ccompanyId}&staffid=${staffid}"  class="nav_list_box "><i class="nav_list_06"></i><span>好友排名</span></a>
							</div>
							<div class="list_part">
								<a href="###" class="nav_list_box  gold_num"><i class="nav_list_07"></i>									
									<span>积分数</span>
									<small class="part_goldnum"><fmt:formatNumber value="${bp.bonusPointScore==null?0:bp.bonusPointScore }"></fmt:formatNumber></small>
								</a>
								<a href="<%=basePath%>/ea/bonuspoints/ea_getWfjJifen.jspa?user=${user}&sccid=${sccid }&khd=${khd}" class="nav_list_box "><i class="nav_list_08" ></i><span>积分充值</span></a>
								<a href="<%=basePath%>/ea/bonuspoints/ea_getbpDetail.jspa?account=${user}&sccid=${sccid}&khd=${khd}&flag=${flag}&staffid=${staffid}&identifying=${identifying}&ccompanyId=${ccompanyId}"  class="nav_list_box ">
									<i class="nav_list_09"></i>
									<span>积分账单</span>
								</a>								
							</div>
						</c:otherwise>       				
       				</c:choose>    								      			      			      				
       			</c:when>
       			<c:otherwise>
							<div class="list_part">
							   <a href="<%=basePath%>/ea/jinbi/ea_gethyjifenBill.jspa?user=${user}&sccid=${sccid}&staffid=${staffid}&khd=${khd}&flag=${flag}&identifying=${identifying}&ccompanyId=${ccompanyId}" class="nav_list_box  gold_num">
									<i class="nav_list_02"></i>
									<span>现金数（分）</span>
									<small class="part_goldnum"><fmt:formatNumber value="${jifen.wfjJifenScore==null?0:jifen.wfjJifenScore }"></fmt:formatNumber></small>
								</a>
								<a href="<%=basePath%>/ea/jinbi/ea_getwfjtixian.jspa?user=${user}&sccid=${sccid}&khd=${khd}&flag=${flag}&staffid=${staffid}&identifying=${identifying}&ccompanyId=${ccompanyId}&purview=1" class="nav_list_box "><i class="nav_list_03"></i><span>现金兑换</span></a>
								<a href="<%=basePath%>/ea/jinbi/ea_getwfjchongzhi.jspa?user=${user}&sccid=${sccid}&khd=${khd}&flag=${flag}&identifying=${identifying}&ccompanyId=${ccompanyId}&staffid=${staffid}" class="nav_list_box "><i class="nav_list_04" ></i><span>金币充值</span></a>
								<a href="<%=basePath%>/ea/jinbi/ea_gethyjifenBill.jspa?user=${user}&sccid=${sccid}&staffid=${staffid}&khd=${khd}&flag=${flag}&identifying=${identifying}&ccompanyId=${ccompanyId}"  class="nav_list_box "><i class="nav_list_05"></i><span>兑换账单</span></a>
								<a href="<%=basePath%>/ea/jinbi/ea_getFriends.jspa?user=${user}&sccid=${sccid}&staffid=${staffid}&khd=${khd}&flag=${flag}&identifying=${identifying}&ccompanyId=${ccompanyId}"  class="nav_list_box "><i class="nav_list_06"></i><span>好友排名</span></a>
							</div>
							<div class="list_part">
								<a href="###" class="nav_list_box  gold_num"><i class="nav_list_07"></i>									
									<span>积分数</span>
									<small class="part_goldnum"><fmt:formatNumber value="${bp.bonusPointScore==null?0:bp.bonusPointScore }"></fmt:formatNumber></small>
								</a>
								<a href="<%=basePath%>/ea/bonuspoints/ea_getWfjJifen.jspa?user=${user}&sccid=${sccid }&khd=${khd}"  class="nav_list_box "><i class="nav_list_08" ></i><span>积分充值</span></a>
								<a href="<%=basePath%>/ea/bonuspoints/ea_getbpDetail.jspa?account=${user}&sccid=${sccid}&khd=${khd}&flag=${flag}&staffid=${staffid}&identifying=${identifying}&ccompanyId=${ccompanyId}"  class="nav_list_box ">
									<i class="nav_list_09"></i>
									<span>积分账单</span>
								</a>								
							</div>
						</c:otherwise>       		
       			</c:choose> 	
	   </div>
    </div>    
</body>


</html>