<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="com.tiantai.wfj.bo.TEshopCusCom" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<!DOCTYPE html>
<html>
<meta charset="UTF-8">
<head lang="en">
    <base href="<%=basePath%>">
    <title>${product[0]}</title>
    <meta http-equiv="Access-Control-Allow-Origin" content="*">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" href="<%=basePath%>css/bootstrap.css"/>
    <link rel="stylesheet"
          href="<%=basePath%>/css/WFJClient/goodsDetail.css" type="text/css"></link>
    <script src="<%=basePath%>js/jquery-1.11.3.js" type="text/javascript"></script>
    <script src="<%=basePath%>js/ea/marketing/wfjeshop/GoodsDetail.js"
            type="text/javascript"></script>
    <script src="<%=basePath%>js/bootstrap.js" type="text/javascript"></script>
    <script src="<%=basePath%>js/toucher.js" type="text/javascript"></script>
    <script src="<%=basePath%>js/ea/marketing/supermarket/font-size.js" type="text/javascript"></script>



    <script type="text/javascript">
        var path = "<%=basePath%>";
        var pricetype="${param.pricetype}";
        var shuliang = '${inventory.invenQuantity}';
        var ppid = '${objbeanns[0][1]}';
        var ppids = '${ppids}';
        var type = '${objbeanns[0][6]}';
        var categoryId ='${objbeanns[0][8]}';
        var  categoryName ='${objbeanns[0][9]}';
        var ccompanyId = "${ccompanyId}";
        var companyId = "${comId}";
        var indus = '${indus}';
        var size = "${size}";
        var color = "${color}";
        var result = "${result}";
        var goodsid = "${goodsid}";
        var goodsldname = "${goodsldname}";
        var carType = "${param.carType}";

        //判断预算模块ljc
        var budget = '${budget}';
        var goodsBillsID = '${goodsBillsID}';
        var user = '${user}';
        var cashierBillsID = '${cashierBillsID}';
        var length = "${fn:length(ptlist)}";
        var pprice = "${product[2]}";
        var shoptype = "";
        var sccid = '<%=((TEshopCusCom)session.getAttribute("key_shop_cus_com"))!=null ?((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getSccId():"" %>';
        var supername = '<%=((TEshopCusCom)session.getAttribute("key_shop_cus_com"))!=null ?((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getSuperioragent():"" %>';
        var pos = "${param.pos}";
        var price = 0;
        var totalPct = 0;
        <%--if(!${ssIsnull}){--%>
        <%--totalPct='${setsubsidize.totalPct}';--%>
        <%--}--%>
        <%--var Pct=parseFloat(totalPct)/100+1;--%>
    </script>
    <script type="text/javascript">
        var priceType = "${priceType}";
        $(function () {
            //商品价格类型判断
            // 价格类别[cx:促销价,tj:特价,vip:VIP价,yj:原价(零售价)]

            if (priceType == 'yj') {
                $(".wfj11_013_costprice").hide();
                $("#flagPrice1").remove();
                $("#flagPrice2").remove();
            }
            if (priceType == 'tj') {
                $("#flagPrice1").attr("class", "tj");
                $("#flagPrice2").attr("class", "tj");
            }
            if (priceType == 'cx') {
                $("#flagPrice1").attr("class", "cx");
                $("#flagPrice2").attr("class", "cx");
            }
            if (priceType == 'vip') {
                $("#flagPrice1").attr("class", "vip");
                $("#flagPrice2").attr("class", "vip");
            }
            if (priceType=='pf') {
                $("#flagPrice1").remove();
                $("#flagPrice2").remove();
            }
        })
    </script>
</head>
<body>
<div id="btn_gwc">
    <p>成功加入购物车</p>
</div>
<div class="wfj11_013">
    <form method="post" id="formsutin"
          action="<%=basePath%>ea/wfjshop/ea_gm.jspa?pricetype=${param.pricetype}&carType=${param.carType}">
        <div class="wfj11_013_top">
            <ul id="tops">
                <li class="backt">
                    <a href="javascript:history.go(-1)" target="_self"><img
                            src="<%=basePath%>images/WFJClient/DigitalMall/left_jt.png"/>
                    </a>
                </li>
                <li>${product[0]}</li>
                <li class="cartList">
                
                 <c:if test="${param.pricetype eq '1' }">
                <a href="<%=basePath %>ea/wholesaleMall/ea_shoppingCartList.jspa">
                    <img src="<%=basePath%>/images/WFJClient/Newjspim/shopcar.png"/>
                </a>
                </c:if>
                <c:if test="${param.pricetype ne '1' }">
                <a href="<%=basePath %>/ea/wfjshop/ea_getcity.jspa?pos=${pos}&companyId=${comId}">
                    <img src="<%=basePath%>/images/WFJClient/Newjspim/shopcar.png"/>
                </a>
                 </c:if>
                </li>
            </ul>
        </div>
        <div class="wfj11_013_hidden">
            <div class="wfj11_013_auto" id="wfj11_013_auto">

                <div class="wfj11_013_top_img"><span id="flagPrice1"><i></i></span>
                    <div id="carousel-example-generic" class="carousel slide both"
                         data-ride="carousel">
                        <!-- Indicators -->
                        <ol class="carousel-indicators">

                            <c:if test='${fn:length(tplist)!=0}'>
                                <li data-target="#carousel-example-generic" data-slide-to="0"
                                    class="active"></li>
                            </c:if>
                            <c:forEach items="${tplist}" varStatus="status">
                                <li data-target="#carousel-example-generic"
                                    data-slide-to="${status.index+1}"></li>
                            </c:forEach>
                        </ol>

                        <!-- Wrapper for slides -->
                        <div class="carousel-inner" role="listbox">
                            <div class="item active">
                                <c:if test="${param.carType eq 'c'}">
                                <img src="<%=basePath%>images/ea/office/jlctc.jpg" alt="...">
                                </c:if>
                                <c:if test="${param.carType eq 'p'}">
                                    <img src="<%=basePath%>images/ea/office/tcsfs.jpg" alt="...">
                                </c:if>
                                <c:if test="${param.carType ne 'c'&&param.carType ne 'p'}">
                                    <img src="<%=basePath%>${product[3]}" alt="...">
                                </c:if>


                            </div>
                            <c:forEach items="${tplist}" var="tp">
                                <div class="item">
                                    <img src="<%=basePath%>${tp.imgurl}" alt="...">
                                    <div class="carousel-caption">...</div>

                                </div>
                            </c:forEach>
                        </div>
                        <!-- Controls -->
                        <a id="carleft" class="left carousel-control"
                           href="#carousel-example-generic" role="button" data-slide="prev">

                        </a> <a id="carright" class="right carousel-control"
                                href="#carousel-example-generic" role="button" data-slide="next">

                    </a>
                    </div>

                </div>
                <div class="wfj11_013_details">
                    <div class="wfj11_013_width">

                        <ul class="biaojia">
                            <li class="wfj11_013_peice">￥${product[2]}</li>
                            <li class="wfj11_013_costprice">￥${productRetail[2]}</li>
                        </ul>
                        <span style="display: none;" id="pm"></span>
                        <script type="text/javascript">
                          if($(window).width()==1080&&$(window).height()>1000&&priceType=="vip"){
                              price = ${productRetail[2]};
                          }if(pos=="1"&&priceType=="vip"){
                              price = ${productRetail[2]};
                          } else{
                            price =${product[2]};
                          }
                        </script>
                        <ul>
                            <li class="wfj11_013_details_name">${goodsldname}${product[0]}</li>
                        </ul>
                        <ul class="wfj11_013_infor">
                            <li style="text-align:left;"><img
                                    src="<%=basePath%>/images/WFJClient/PersonalJoining/013_kd.png"/>快递：0.00元
                            </li>
                            <li style="text-align:right"><img
                                    src="<%=basePath%>/images/WFJClient/PersonalJoining/013_yx.png"/>月销量${product[7]}笔
                            </li>
                            <li>
                            <li class="li-last" style="text-align:right"><img
                                    src="<%=basePath%>/images/WFJClient/PersonalJoining/013_dz.png"/>${city}</li>
                        </ul>
                        <%-- 							<ul>
                                                        <li class="wfj11_013_link"><a
                                                            href="<%=basePath%>/ea/industry/ea_getCompanyHome.jspa?companyId=${comId}&ccompanyId=${ccompanyId}&search=${indus}"
                                                            target="_self">${indus}</a>
                                                        </li>
                                                    </ul> --%>
                        <!-- 如果该产品为预约产品则隐藏添加数量按钮xgb -->
                        <c:if test="${!fn:contains(objbeanns[0][6], '驾校预约')}">
                            <ul id="num">
                                <li>数量：</li>
                                <li class="wfj11_013_del" onclick="jian()">-</li>
                                <li class="wfj11_013_input"><input type="text" value="1"
                                                                   id="shuliang" name="count"/>
                                </li>
                                <li class="wfj11_013_add"
                                    style="height:15px; line-height:15px; margin-top:1px;"
                                    onclick="jia()">+
                                </li>
                            </ul>
                        </c:if>
                    </div>
                </div>
                <!-- 规格 -->
                <c:if test="${size!=null||color!=null}">
                    <div class="wfj11_013_choose">
                        <div class="wfj11_013_width">
                            <ul>
                                <li>规格参数</li>
                                <li><p>
                                    <img src="<%=basePath%>/images/013_choose.png"/>
                                </p></li>
                                <div class="clear"></div>
                            </ul>
                        </div>
                    </div>
                </c:if>

                <!-- 促销产品 -->
                <c:if test="${ptlist ne null&&fn:length(ptlist)>0 }">
                    <!-- <a href="<%=basePath %>/ea/productslaunch/ea_PromotionsDetail.jspa?companyId=${comId}&ppId=${objbeanns[0][1]}"> -->
                    <div class="wfj11_013_choose2">
                        <div class="wfj11_013_width">
                            <p id="cx">赠品搭配</p>
                            <img src="<%=basePath%>/images/013_choose.png"/>
                        </div>
                        <div class="sales" style="font-size: 16px;line-height: 30px;">
                                <%-- 	<div class="sales_img">
                                        <img src="<%=basePath%>${product[3]}">
                                    </div>
                                    <img class="jia" src="<%=basePath%>/images/WFJClient/PersonalJoining/jia.png">
                                    <c:forEach items="${ptlist }" var="entity" varStatus="status">
                                    <div class="sales_img">
                                    <input type="hidden" value="${entity[1] }" id="ptId"/>
                                    <img src="<%=basePath %>${entity[3]}">
                                    </div>
                                    <c:if test="${status.index<fn:length(ptlist)-1}">
                                    <img class="jia" src="<%=basePath%>/images/WFJClient/PersonalJoining/jia.png"></c:if>
                                    </c:forEach>
                                    <div class="clearfix"></div> --%>
                        </div>
                        <p id="cu">
                            <span>该产品含有促销赠品,赠完即止！</span>
                        </p>
                    </div>
                    <!-- </a> -->
                </c:if>
                <!-- 评论 -->
                <div class="wfj11_013_comment">
                    <div class="wfj11_013_width">
                        <h3 class="com_head">
                            全部评论（<span>${qp}</span>）

                        </h3>

                        <a
                                href="<%=basePath%>ea/wfjshop/ea_findComments.jspa?ppid=${ppid}">
                            <div class="com_con">
                                <div class="con_title">
                                    <p class="title_icon_p">
                                        <c:if test='${comobj[1] eq "null" ||comobj[1] eq ""||comobj[1] eq null}'>
                                            <img class="title_icon"
                                                 src="<%=basePath%>images/contacts/Network/defaultsmall.png"/>

                                        </c:if>
                                        <c:if test='${comobj[1] ne "null" &&comobj[1] ne ""&&comobj[1] ne null}'>
                                            <img class="title_icon" src="<%=basePath%>${comobj[1]}"/>
                                        </c:if>
                                    </p>
                                    <p class="title_name"><c:if test='${comobj[5]=="匿名评价"}'>匿名</c:if><c:if
                                            test='${comobj[5]!="匿名评价"}'>${comobj[2]}</c:if></p>
                                </div>
                                <div class="con_con">
                                    <p class="con_text">${comobj[3]}</p>
                                </div>
                            </div>
                        </a>
                        <div class="com_foot">
                            <a
                                    href="<%=basePath%>ea/wfjshop/ea_findComments.jspa?ppid=${ppid}"><input
                                    type="button" value="查看更多评论">
                            </a>
                        </div>
                    </div>
                </div>
                <!-- 公司信息 -->
                <div class="shop_name">
                    <div class="shop_name_txt1">
                        <div class="border1">
                            <img src="<%=basePath%>${companyInfo[0]!=null?companyInfo[0]:'images/WFJClient/PersonalJoining/logo@2x.png'}">
                        </div>
                        <h3>${indus}</h3>
                    </div>
                    <div class="shop_name_txt2">
                        <div class="txt2_">
                            <p>${companyInfo[1] }</p>
                            <h4>全部产品</h4>
                        </div>
                        <div class="txt2_2">
                            <p>${companyInfo[2] }</p>
                            <h4>粉丝关注</h4>
                        </div>
                    </div>
                    <div class="shop_name_txt3">
                        <div class="shop_btn">
                        <c:if test="${param.pricetype eq '1' }">

                         <a href="<%=basePath%>ea/wholesaleMall/ea_toWholesaleMall.jspa?companyid?companyid=${comId}s&ccompanyID=${ccompanyId}&companyName=${indus}&phl=phl">
                                <input type="button" value="查看产品"/>
                            </a> <a
                                href="<%=basePath %>ea/wholesaleMall/ea_toWholesaleMall.jspa?companyid=${comId}&ccompanyID=${ccompanyId}&companyName=${indus}&phl=phl">
                            <input type="button" value="进入网站"/>
                        </a>
                        </c:if>
                        <c:if test="${param.pricetype ne '1' }">
                            <a href="<%=basePath%>ea/industry/ea_CompanyProducts.jspa?companyId=${comId}&ccompanyId=${ccompanyId}">
                                <input type="button" value="查看产品"/>
                            </a> <a
                                href="<%=basePath %>/ea/industry/ea_getCompanyHome.jspa?ccompanyId=${ccompanyId}&pos=${pos}">
                            <input type="button" value="进入网站"/>
                        </a>
                          </c:if>
                        </div>
                    </div>
                </div>

                <div class="wfj11_013_referral">
                    <div class="wfj11_013_width">
                        <div class="013_ccgd">
                            <p style="text-align:center; color:#eb4f38; padding:20px 0;">继续拖动查看更多商品信息</p>
                        </div>

                        <div class="wfj11_013_referral_con" style="display:none;">
                            <div class="wfj_013_cpcs">
                                <img
                                        src="<%=basePath%>images/WFJClient/PersonalJoining/013_cpcs.png">产品参数
                            </div>

                        </div>

                    </div>
                </div>
                <div class="sctop" style="height:60px; background:#fff;">
                    <img style="height:60px;width:60px;display:block;margin:0 auto;"
                         src="<%=basePath%>images/WFJClient/PersonalJoining/013_load.gif">
                </div>
            </div>
        </div>
        <!--中联园区底部-->
        <c:choose>

            <c:when test="${judge=='Promotion'}">
                <div class="wfj11_013_bottoms">
                    <ul id="kleft" class="kleft">
                        <li id="contact">
                       	<img src="<%=basePath%>images/WFJClient/PersonalJoining/xkf.png">
                        <a href="javascript:;">客服</a>
                        </li>
                        <li>
                         <c:if test="${param.pricetype eq '1' }">
                             <a href="<%=basePath %>ea/wholesaleMall/ea_toWholesaleMall.jspa?companyid=${comId}s&ccompanyID=${ccompanyId}&companyName=${indus}&phl=phl">
                           <img src="<%=basePath%>images/WFJClient/PersonalJoining/xdp.png">
                           店铺</a>
                         </c:if>
                          <c:if test="${param.pricetype ne '1' }">
                              <a href="<%=basePath %>/ea/industry/ea_getCompanyHome.jspa?ccompanyId=${ccompanyId}">
                          <img src="<%=basePath%>images/WFJClient/PersonalJoining/xdp.png">
                         店铺</a>
                       </c:if>  
                        
                        </li>
                        <li onclick="collectGoods('${objbeanns[0][1]}','${param.pricetype}')";>
                        <c:if test="${iscollect eq '1' }">
                        <img src="<%=basePath%>images/WFJClient/PersonalJoining/xsc.png" class="scsp">
                        </c:if>
                         <c:if test="${iscollect ne '1' }">
                        <img src="<%=basePath%>images/WFJClient/PersonalJoining/xwsc.png" class="scsp">
                        </c:if>
                       收藏
                        </li>
                    </ul>
                    <ul id="buy">
                        <li>
                            <div id="shopcart" class="jr">加入购物车</div>
                        </li>
                        <li>
                            <div id="buynow">
                                <c:choose>
                                    <c:when test="${fn:contains(objbeanns[0][6], '驾校预约')}">
                                        立即预约
                                    </c:when>
                                    <c:otherwise>
                                        立即购买
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </li>
                    </ul>
                </div>
            </c:when>
            <c:when test="${judge=='selectSalesPromotion'}">
                <div class="wfj11_013_bottoms" onclick="promotionProduct(this)">
                    <div style="text-align:center;line-height:59px;">
                        <p style="color: #FFF;height: 45px;font-size: 20px;background-color: #F74C31;line-height: 45px;">
                            点击确定</p>
                        <input type="hidden" id="user" name="user" value="${user }"/>
                        <input type="hidden" id="promotionID" value="${promotionID }"/>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <div class="wfj11_013_bottoms">
                    <ul id="kleft" class="kleft">
                        <li id="contact">
                            <input type="hidden" value="<s:property value='#request.contactInfo[0][0]'/>">
                            <input type="hidden" value="<s:property value='#request.contactInfo[0][1]'/>">
                            <input type="hidden" value="<s:property value='#request.contactInfo[0][2]'/>">
                             <img src="<%=basePath%>images/WFJClient/PersonalJoining/xkf.png">
                            
                            <a href="javascript:;">客服</a>
                        </li>
                        
                        <li>
                         <c:if test="${param.pricetype eq '1' }">
                             <a href="<%=basePath %>ea/wholesaleMall/ea_toWholesaleMall.jspa?companyid=${comId}s&ccompanyID=${ccompanyId}&companyName=${indus}&phl=phl">
                              <img src="<%=basePath%>images/WFJClient/PersonalJoining/xdp.png">店铺
                             </a>
                         </c:if>       
                          <c:if test="${param.pricetype ne '1' }">
                              <a href="<%=basePath %>/ea/industry/ea_getCompanyHome.jspa?ccompanyId=${ccompanyId}">
                                  <img src="<%=basePath%>images/WFJClient/PersonalJoining/xdp.png">店铺
                              </a>
                        </c:if>
                        </li>


                        <li onclick="collectGoods('${objbeanns[0][1]}','${param.pricetype}');">
                          <c:if test="${iscollect eq '1' }">
                               <img src="<%=basePath%>images/WFJClient/PersonalJoining/xsc.png" class="scsp">
                         </c:if>
                         <c:if test="${iscollect ne '1' }">
                              <img src="<%=basePath%>images/WFJClient/PersonalJoining/xwsc.png" class="scsp">
                        </c:if>
                            收藏
                        </li>
                    </ul>
                    <ul id="buy">
                        <li>
                            <div id="shopcart" class="jr">加入购物车</div>
                        </li>
                        <li>
                            <div id="buynow">
                                <c:choose>
                                    <c:when test="${fn:contains(objbeanns[0][6], '驾校预约')}">
                                        立即预约
                                    </c:when>
                                    <c:otherwise>
                                        立即购买
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </li>
                    </ul>
                </div>
            </c:otherwise>
        </c:choose>
        <input type="hidden" name="ppid" value="${objbeanns[0][1] }"/>
        <input type="hidden" name="inventory.inventoryID" value="${inventory.inventoryID }"/>
        <input type="hidden" name="companyId" value="${comId }"/>
        <input type="hidden" name="standard" id="standards"/>
        <input type="hidden" name="ptppid" id="ptppid"/>
        <input type="hidden" name="ptstandard" id="ptstandard"/>
        <input type="hidden" name="ppids" value="${ppids}"/>
        <input type="hidden" name="ccompanyId" value="${ccompanyId}"/>
        <input type="submit" style="display: none;" id="tj"/>
    </form>

    <!--中联园区底部 end-->
</div>
<div class="box">
    <div class="cover"></div>
    <div class="choose_size">
        <div class="summary">
            <div class="img">
                <span id="flagPrice2"><i></i></span>
                <img src="<%=basePath%>${objbeanns[0][3]}"/>
            </div>
            <div class="main">
                <div class="pricer">￥${objbeanns[0][2]}</div>
                <div class="stock">
                    <label>库存</label>${inventory.invenQuantity}件
                </div>
            </div>
            <button class="sback">
                <img src="<%=basePath%>images/WFJClient/013-forks.png"/>
            </button>
        </div>

        <div class="choose_con">
            <div class="con_control">
                <ul>
                    <c:if test="${size!=null}">
                        <li id="daxiao">
                            <h2>${size}</h2>
                            <div class="items">
                                <s:iterator value="listsize">
                                    <label>${attrivalue}</label>
                                </s:iterator>

                            </div>
                        </li>
                    </c:if>
                    <c:if test="${color!=null}">
                        <li id="yanse">
                            <h2>${color}</h2>
                            <div class="items">
                                <s:iterator value="listcolor">
                                    <label>${attrivalue}</label>
                                    <input type="hidden" value="${imgurl}"/>

                                </s:iterator>
                            </div>
                        </li>
                    </c:if>
                    <li id="shuliang2">
                        <h2>购买数量</h2>
                        <div class="number">
                            <button type="button" class="decrease">-</button>
                            <h5>1</h5>

                            <button type="button" class="increase">+</button>
                            <div class="clear"></div>
                        </div>
                    </li>
                </ul>
            </div>
        </div>

        <div class="bottom_button1">
            <button class="jrl">加入购物车</button>
            <button class="buy" onclick="gm()">
                <c:choose>
                    <c:when test="${fn:contains(objbeanns[0][6], '驾校预约')}">
                        立即预约
                    </c:when>
                    <c:otherwise>
                        立即购买
                    </c:otherwise>
                </c:choose>
            </button>
            <div class="clear"></div>
        </div>
        <div class="bottom_button2">
            <button class="confirmop">确定</button>
            <input type="hidden" id="optype"/>

        </div>
    </div>
</div>

<div class="alert_new_"></div>
<div class="alert_new">
    <div class="top">
        <h5>赠品<img src="<%=basePath %>images/WFJClient/Newjspim/x2.png" id="close"></h5>
    </div>
    <div class="shop_new" style="overflow: auto;">
        <c:forEach items="${ptlist }" var="entity" varStatus="status">
            <div class="mil" id="${entity[1] }">
                <div class="left">
                    <i><input type="hidden" value="${entity[1] }" class="ppId"/>
                        <input type="hidden" value="${entity[4] }" class="goodsId"/>
                        <input type="hidden" value="${entity[2] }" class="price"/>
                        <input type="hidden" value="" class="ptstandard"/></i>
                </div>
                <div class="right">
                    <div class="tu">
                        <img src="<%=basePath %>${entity[3]}" alt="">
                    </div>
                    <div class="txt">
                        <p>${entity[0] }</p>
                        <p>&yen;${entity[2] } <span></span></p>
                    </div>
                        <%-- <div class="right">
                            <img src="<%=basePath %>images/WFJClient/Newjspim/013_choose.png" alt="">
                        </div> --%>
                </div>
            </div>
        </c:forEach>
        <div class="bottom_button2 cxqr" style="display: block;">
            <button id="btn_btn">确定</button>
        </div>
    </div>
</div>
<div class="box2">
    <div class="choose_size choose_size2">
        <div class="summary summary2">
            <div class="img">
                <img id="image" src="">
            </div>
            <div class="main">
                <div class="pricer">
                </div>
                <div class="stock">
                    <p><span style="color: #AEAEAE;">还未选</span><span id="color">颜色分类</span><span
                            id="set-meal">尺寸规格</span></p>
                </div>
            </div>
            <button class="sback">
                <img src="<%=basePath %>images/WFJClient/Newjspim/x2.png">
            </button>
        </div>

        <div class="choose_con">
            <div class="con_control con_control2">
                <ul>
                    <li id="dx">
                        <h2></h2>
                        <div class="items color">
                        </div>
                    </li>
                    <li id="ys">
                        <h2></h2>
                        <div class="items set-meal">
                        </div>
                    </li>
                </ul>
            </div>
        </div>
        <div class="bottom_button2 ptbutton" style="display: block;">
            <button>确定</button>
        </div>
    </div>
</div>


<script type="text/javascript">
    var basePath = '<%=basePath%>';
    var judge = $("#judge").val();


    $(document).ready(function (e) {
//           $(".wfj11_013_peice").text("￥"+(price*Pct).toFixed(2));
//           $(".box").find(".pricer").text("￥"+(price*Pct).toFixed(2));
        //修改文字及高度自适应--底部通用
        $(".wfj11_013_bottoms").attr("style", "height:" + $(window).height() * 0.08 + "px;");
        $(".wfj11_013_bottoms").find("li").attr("style", " line-height:" + $(window).height() * 0.08 + "px;");
        $(".wfj11_013_bottoms").find("li").find("a").attr("style", "font-size:" + $(window).height() * 0.02 + "px;color:#000;");
        $(".wfj11_013_bottoms").find("li").find("div").attr("style", "font-size:" + $(window).height() * 0.025 + "px;");
        //图片
        $("#kleft li").find("img").attr("style", "height:" + $(window).height() * 0.035 + "px;margin-top:" + $(window).height() * 0.005 + "px;");
        $("#kleft li").find("p").attr("style", "height:" + $(window).height() * 0.04 + "px;line-height:" + $(window).height() * 0.04 + "px;");

        if (budget == 'budget') {
            $(".wfj11_013_bottoms").html("<ul><li><div id='sel' onclick='sel()'>选择产品</div></li></ul>");
            $(".wfj11_013_bottoms").find("ul").attr("style", "float:right;background-color:#F74C31;height:" + $(".wfj11_013_bottoms").height() + "px;width:" + $(".wfj11_013_bottoms").width() * 0.3 + "px;");
            $(".wfj11_013_bottoms").find("li").attr("style", " width: 100%;line-height:" + $(window).height() * 0.08 + "px;text-align: center;");
            $(".wfj11_013_bottoms").find("div").attr("style", "width:100%;color:#FFF;font-family:微软雅黑;font-size:16px;text-align: center;cursor: pointer;");

        }
        $("body").css("height", $(window).height());
        $("body").css("width", $(window).width());

        //修改字体大小
        $("#tops").find("li").attr("style", "float:left;");
        $("#tops").find("li").eq(0).attr("style", "width:15%;");
        $("#tops").find("li").eq(0).find("img").attr("style", "height:" + $(window).height() * 0.04 + "px;padding-left:" + $(window).height() * 0.02 + "px; vertical-align:middle;");
        $("#tops").find("li").eq(1).attr("style", "width:70%; text-align:center; font-size:" + $(window).height() * 0.03 + "px;color:#000;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;");
        $("#tops").find("li").eq(2).attr("style", "width:15%; text-align:center;");
        $("#tops").find("li").eq(2).find("img").attr("style", "height:" + $(window).height() * 0.04 + "px; width:auto; vertical-align:middle;");


        $(".wfj11_013_top").css("height", $(window).height() * 0.08 + "px");
        $(".wfj11_013_top").css({"lineHeight": $(window).height() * 0.08 + "px", "border-bottom": "1px solid #e3e3e3"});

        $(".wfj11_013_hidden").attr("style", "height:" + $(window).height() * 0.84 + "px;");
        $(".wfj11_013_hidden").css("overflow", "hidden");
        $(".wfj11_013_hidden").css("width", "100%");
        $(".wfj11_013_auto").css("overflow", "auto");
        $(".wfj11_013_auto").attr("style", "height:" + $(window).height() * 0.84 + "px; overflow:auto");
        $(".wfj11_013_auto").attr("style", "width:" + parseInt($(".wfj11_013_hidden").width() + 17) + "px;height:" + $(".wfj11_013_hidden").height() + "px;overflow:auto;");

        $(".wfj11_013_link").find("a").attr("style", "padding:0 " + $(window).height() * 0.01 + "px;border-radius:" + $(window).height() * 0.005 + "px;");
        $("#num").attr("style", "height:" + $(window).height() * 0.065 + "px;");
        $("#num").find("li").attr("style", " margin-top:" + $(window).height() * 0.01 + "px;font-size:" + $(window).height() * 0.02 + "px;");

        $(".wfj11_013_del").attr("style", "font-size:" + $(window).height() * 0.03 + "px;height:" + $(window).height() * 0.03 + "px; line-height:" + $(window).height() * 0.03 + "px; margin-top:" + $(window).height() * 0.01 + "px;");
        $(".wfj11_013_add").attr("style", "font-size:" + $(window).height() * 0.03 + "px;height:" + $(window).height() * 0.03 + "px; line-height:" + $(window).height() * 0.03 + "px; margin-top:" + $(window).height() * 0.01 + "px;");

        $(".wfj11_013_input").attr("style", "font-size:" + $(window).height() * 0.025 + "px;height:" + $(window).height() * 0.03 + "px; line-height:" + $(window).height() * 0.02 + "px; margin-top:" + $(window).height() * 0.01 + "px;");


        $(".wfj11_013_input").find("input").attr("style", "font-size:" + $(window).height() * 0.025 + "px;height:" + $(window).height() * 0.03 + "px; border:1px solid #A9A9A9;");


        $(".wfj11_013_bak").attr("style", "font-size:" + $(window).height() * 0.02 + "px;height:" + $(window).height() * 0.03 + "px; border:1px solid #A9A9A9;");

        $(".wfj11_013_referral").find("li").attr("style", "font-size:" + $(window).height() * 0.02 + "px; line-height:" + $(window).height() * 0.03 + "px;");
        $(".wfj11_013_referral").find("h2").attr("style", "font-size:" + $(window).height() * 0.03 + "px; padding-top:" + $(window).height() * 0.01 + "px;");

        $(".wfj11_013_bottom").css("padding-bottom", $(window).height() * 0.1 + "px");
        $(".wfj11_013_hidden").css("overflow", "hidden");

        $(".wfj11_013_auto").css("overflow", "auto");


        //规格

        $(".wfj11_013_choose").find("li").eq(0).attr("style", "width:99%; height:" + $(window).height() * 0.07 + "px; line-height:" + $(window).height() * 0.07 + "px; font-size:" + $(window).height() * 0.027 + "px;");
        $(".wfj11_013_choose").find("li").eq(1).attr("style", "width:1%; height:" + $(window).height() * 0.07 + "px; line-height:" + $(window).height() * 0.07 + "px;");
        $(".wfj11_013_choose").find("li").find("p").attr("style", "line-height:" + $(window).height() * 0.04 + "px;display:inline-block;margin-top:20%;");
        $(".wfj11_013_choose").find("li").find("img").attr("style", "height:" + $(window).height() * 0.04 + "px; ");
        $(".title_name").css({"line-height": $(".title_icon").height() + "px"});
        $(".carousel-inner > .item").css({"height": $(window).width() + "px"});


        $(".wfj11_013_choose2").find("img").eq(0).attr("style", "height:" + $(window).height() * 0.04 + "px; ");
    });


    //size 点击选择
    $(document).on("click", ".items label", function () {

        $(this).parent().find("label").css({"background": "#fff", "color": "#666"});
        $(this).parent().find("label").removeClass("xz");

        $(this).css({"background": "#f74c31", "color": "#fff"});
        $(this).addClass("xz");

    });

</script>

<script>
    var myTouch = util.toucher(document
        .getElementById('carousel-example-generic'));
    $("#carright").css("background", "transparent")
    $("#carleft").css("background", "transparent")
    myTouch.on('swipeLeft', function (e) {
        $('#carright').click();
    }).on('swipeRight', function (e) {
        $('#carleft').click();
    });




</script>
</body>
</html>