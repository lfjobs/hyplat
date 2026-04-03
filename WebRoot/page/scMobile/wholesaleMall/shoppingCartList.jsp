<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
    <title>购物车详细页面</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" href="<%=basePath %>css/scMobile/wholesaleMall/shoppingCart/city.css" type="text/css"></link>
    <script type="text/javascript" src="<%=basePath%>js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript">
        var basePath = "<%=basePath%>";
        //暂时无用
        var companyId = "";
        if ("${comps}" != null) {
            companyId = "${comps[0][0]}";
        }
    </script>
    <script type="text/javascript" src="<%=basePath %>js/scMobile/wholesaleMall/shoppingCart/ap.js"></script>
</head>
<body>
<div class="wfj12_004">
    <!--中联园区头部-->
    <div class="wfj_top">
        <ul>
            <li class="bk">
                <a onclick="javascript: window.history.go(-1);return false;" target="_self" class="bhj"><img
                        src="<%=basePath %>images/WFJClient/DigitalMall/left_jt.png"/>
                </a>
            </li>
            <li>购物车</li>
            <li>
                <img width="100" height="80" src="<%=basePath %>images/scMobile/wholesaleMall/shoppingCart/shopcar.png"/>
            </li>
        </ul>
    </div>

    <div class="wfj12_004_content">
        <div class="wfj12_004_hidden" id="allprod">
            <%--<c:if test="${mapObj.shoppingCartList.size() <= 0}">--%>
                <%--<div class="kgwc">--%>
                    <%--<img src="<%=basePath %>images/scMobile/wholesaleMall/shoppingCart/emptycar.png">--%>
                <%--</div>--%>
            <%--</c:if>--%>
            <%--<c:forEach items="${mapObj.comps}" var="com">--%>
                <%--<div class="changes bj01">--%>
                    <%--<div class="wfj12_004_con">--%>
                        <%--<!--一个公司-->--%>
                        <%--<div class="wfj12_004_title">--%>
                            <%--<div class="wfj12_004_height">--%>
                                <%--<div class="wfj12_004_width">--%>
                                    <%--<ul>--%>
                                        <%--<li id="choice">--%>
                                            <%--<a href="javascript:;">--%>
                                                <%--<img class="change_imgs" src="<%=basePath%>/images/choice_02.png"/>--%>
                                            <%--</a>--%>
                                        <%--</li>--%>
                                        <%--<li class="title">--%>
                                            <%--<a href="<%=basePath%>/ea/industry/ea_getCompanyHome.jspa?ccompanyId=${com[2]}">--%>
                                                <%--<img class="logopic" src="<%=basePath%>${com[3]!=null?com[3]:'images/WFJClient/PersonalJoining/logo@2x.png'}">&nbsp;${com[1]}--%>
                                            <%--</a>--%>
                                            <%--<input type="hidden" value="${com[0]}" class="fcomid"/>--%>
                                        <%--</li>--%>
                                        <%--<li class="changefont change"><a href="javascript:;">编辑</a>--%>
                                        <%--</li>--%>
                                    <%--</ul>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                        <%--<!--一个公司 end-->--%>
                        <%--<c:forEach items="${mapObj.shoppingCartList}" var="entity">--%>
                            <%--<c:if test="${com[0] eq entity.companyid}">--%>
                                <%--<div class="wfj12_004_product zcp ${entity.pscId}zcp">--%>
                                    <%--<div class="wfj12_004_width">--%>
                                        <%--<div class="wfj12_004_choice">--%>
                                            <%--<ul>--%>
                                                <%--<li>--%>
                                                    <%--<img class="change_img ${entity.companyid}" src="<%=basePath%>/images/choice_02.png"/>--%>
                                                <%--</li>--%>
                                            <%--</ul>--%>
                                            <%--<input type="hidden" class="dx" name="xuanz" value="2"/>--%>
                                            <%--<input type="hidden" class="ppid" name="ppid" value="${entity.pscId}"/>--%>
                                            <%--<input type="hidden" value="${entity.companyid}" class="companyId ${entity.companyid}"/>--%>
                                        <%--</div>--%>
                                        <%--<div class="wfj12_004_proimg">--%>
                                            <%--<img src="<%=basePath%>${entity.image}"/>--%>
                                        <%--</div>--%>
                                        <%--<div class="wfj12_004_proInfo proinfo02 oneProduct">--%>
                                            <%--<ul>--%>
                                                <%--<li>${entity.goodsName}</li>--%>
                                            <%--</ul>--%>
                                            <%--<ul>--%>
                                                <%--<li class="stard">--%>
                                                    <%--<c:if test="${!empty entity.cmStr}">尺码：${entity.cmStr}</c:if>--%>
                                                    <%--<c:if test="${!empty entity.ysStr}">颜色：${entity.ysStr}</c:if>--%>
                                                    <%--<c:if test="${!empty entity.ftStr}">副图：${entity.ftStr}</c:if>--%>
                                                    <%--<c:if test="${!empty entity.spStr}">视频：${entity.spStr}</c:if>--%>
                                                    <%--<c:if test="${empty entity.cmStr && empty entity.ysStr && empty entity.ftStr && empty entity.spStr}">${entity.standard}</c:if>--%>
                                                <%--</li>--%>
                                            <%--</ul>--%>
                                            <%--<ul>--%>
                                                <%--<li class="left">￥<span id="price">${entity.allPrice}</span></li>--%>
                                                <%--<li class="right">x<span id="num">${entity.tjNum}</span></li>--%>
                                            <%--</ul>--%>
                                        <%--</div>--%>
                                        <%--&lt;%&ndash;编辑时用&ndash;%&gt;--%>
                                        <%--<div class="wfj12_004_changedelete deletes01" style="display:none;">--%>
                                            <%--<div class="wfj12_004_nums">--%>
                                                <%--<ul class="borders">--%>
                                                    <%--<li class="li1">-</li>--%>
                                                    <%--<li id="nums">--%>
                                                        <%--<input type="text" readonly="readonly" value="${entity.tjNum}" class="ls"/>--%>
                                                    <%--</li>--%>
                                                    <%--<li class="li2">+</li>--%>
                                                <%--</ul>--%>
                                                <%--<ul>--%>
                                                    <%--<li style="font-size: 15px;">--%>
                                                        <%--单价:￥<span id="oprPrice">${entity.allPrice}</span>&lt;%&ndash;零售价&ndash;%&gt;--%>
                                                    <%--</li>--%>
                                                    <%--<li style="font-size: 15px;">--%>
                                                        <%--小计:￥<span id="oprSum"><fmt:formatNumber value="${entity.allPrice * entity.tjNum}" type="NUMBER" maxFractionDigits="2"/></span>--%>
                                                    <%--</li>--%>
                                                    <%--<input type="hidden" value="${entity.allPrice}" id="moue"/>--%>
                                                    <%--<input type="hidden" id="editppid" value="${entity.pscId}"/>--%>
                                                <%--</ul>--%>
                                            <%--</div>--%>
                                            <%--<div class="wfj12_004_delete">--%>
                                                <%--<ul>--%>
                                                    <%--<li class="dele">删除</li>--%>
                                                <%--</ul>--%>
                                            <%--</div>--%>
                                        <%--</div>--%>
                                    <%--</div>--%>
                                <%--</div>--%>
                            <%--</c:if>--%>
                        <%--</c:forEach>--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</c:forEach>--%>
        </div>
    </div>
    <!--所有订单 end-->
    <div class="wfj12_004_bottom">
        <div class="wfj12_004_width">
            <div class="wfj12_004_allchecked">
                <ul>
                    <li>
                        <img class="allchangeimg" style="text-align: center;position:relative;left: 0px;" src="<%=basePath%>/images/choice_02.png"/>
                    </li>
                    <li class="qx">全选</li>
                </ul>
            </div>
            <div class="wfj12_004_allmoney">
                <ul>
                    <li>合计：</li>
                    <li>￥<span id="zh">0</span>
                    </li>
                </ul>
            </div>
            <div class="wfj12_004_commit">结算</div>
        </div>
    </div>
</div>
<%--提交表单--%>
<s:form id="f1" action="" namespace="/ea/scBudget" method="POST">
    <input type="hidden" name="shoppingCartParmStr" id="ppic"/>
    <input type="hidden" name="checkCompanyIds" id="companyIds"/>
</s:form>
<input type="hidden" id="ttsw_hid_del_pscid" value=""/><%--购物车删除id拼接字符串--%>
<script>
    //后退
    function toBack() {
        var bs = ${lxType};//跳转标识用于区分是搜索页进入还是列表页进入
        var ccompanyID = '${ccompanyID}';
        var companyId = "${companyId}";
        var search = '${search}';
        var companyName = '${companyName}';
        /* var url = "ea/wholesaleMall/ea_toWholesaleMall.jspa";
        if(bs == 2){
              url =  "ea/wholesaleMall/ea_toGoodsSearch.jspa?search="+search+"&companyName="+companyName+"&ccompanyID=" + ccompanyID+"&lxType=0";
        }
        window.location.href = basePath + url; */
      window.history.go(-1);
        return false;
    }

    //监听点击浏览器后退
    $(function(){
        pushHistory();
        window.addEventListener("popstate", function(e) {
            toBack();
        }, false);
        function pushHistory() {
            var state = {
                title: "title",
                url: ""
            };
            window.history.pushState(state, "title", "");
        }
    });
</script>
</body>
</html>
