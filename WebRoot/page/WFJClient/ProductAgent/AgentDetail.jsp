<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/3/14 0014
  Time: 17:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";

%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <meta name="format-detection" content="telephone=no, email=no" />
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
    <script type="text/javascript" src="<%=basePath%>js/WFJClient/setHtmlFont.js"></script>
    <link rel="stylesheet" href="<%=basePath%>css/WFJClient/ProductAgent/base.css">
    <link rel="stylesheet" href="<%=basePath%>css/WFJClient/ProductAgent/mer.css">
    <script src="<%=basePath%>js/WFJClient/jquery.min.js"></script>
    <title>招商产品—项目</title>
</head>
<body>
<header class="com_head">
    <a href="<%= basePath%>ea/productAgent/ea_productAgentList.jspa?" class="back"></a>
    <h1>招商产品—项目</h1>
</header>
<div class="wrap_page">
    <div class="p_banner">
        <img src="<%=basePath%><s:property value='#request.map.list[0][4]'/>" alt="">
    </div>
    <div class="p_info">
        <%--<div class="p_info_t">￥<s:property value="#request.map.list[0][5]"/></div>--%>
        <div class="p_info_m"><s:property value="#request.map.list[0][2]"/></div>
        <div class="p_info_b">招商中 · 剩<s:property value="#request.map.count"/>款</div>
    </div>
    <a href="<%= basePath%>ea/productAgent/ea_investmentDemand.jspa?pid=<s:property value='#request.map.list[0][1]'/> " class="mer_require">产品招商要求</a>
    <div class="mer_tit_wrap clearfix">
        <span class="mer_tit_L">各个代理招商每件获得佣金</span>
        <span class="mer_tit_R">签署代理佣金协议</span>
    </div>
    <div class="yj_wrap clearfix">
        <s:iterator value="#request.map.list" var="entity">
        <label class="yj_radio_wrap">
                <input type="radio" class="yj_radio" id="<s:property value='#entity[1]'/>"
                    <s:if test="%{#entity[5]=='00'||#entity[5]=='01'}">
                           <s:if test="%{#entity[8]=='01'}">disabled</s:if>
                           <s:if test="%{#entity[5]=='02'}">checked</s:if>
                    </s:if><s:else> checked </s:else>
                       name="yongjin">
            <div class="yj_box">
                <s:if test="%{#entity[5]=='00'}">
                    <%--<span><s:property value="#entity[6]"/>元</span><br>--%>
                    <span>贴牌佣金</span>
                </s:if>
                <s:if test="%{#entity[5]=='01'}">
                    <%--<span><s:property value="#entity[6]"/>元</span><br>--%>
                    <span>设备安装佣金</span>
                </s:if>
                <s:if test="%{#entity[5]=='02'}">
                    <%--<span><s:property value="#entity[6]"/>元</span><br>--%>
                    <span>省级代理佣金</span>
                </s:if>
                <s:if test="%{#entity[5]=='03'}">
                    <%--<span><s:property value="#entity[6]"/>元</span><br>--%>
                    <span>县级代理佣金</span>
                </s:if>
                <s:if test="%{#entity[5]=='04'}">
                    <%--<span><s:property value="#entity[6]"/>元</span><br>--%>
                    <span>村级代理佣金</span>
                </s:if>
            </div>
        </label>
        </s:iterator>
    </div>
    <a href="<%=basePath%>ea/productAgent/ea_companyAgent.jspa?companyId=<s:property value='#request.map.comlist[0][4]'/>&ppId=<s:property value='#request.map.list[0][0]'/>" class="commany_wrap clearfix">
        <s:if test="%{#request.map.comlist[0][0]==null}">
            <img src="<%=basePath%>images/WFJClient/PersonalJoining/logo@2x.png" alt="" class="commany_logo">
        </s:if>
        <s:else>
            <img src="<%=basePath%><s:property value='#request.map.comlist[0][0]'/>" alt="" class="commany_logo">
        </s:else>

        <div class="commany_text">
            <div class="commany_name"><s:property value="#request.map.comlist[0][1]"/></div>
            <p><s:property value="#request.map.comlist[0][2]"/></p>
        </div>
    </a>
    <!--产品详情（这边用图片代替）-->
    <div class="p_content clearfix">
        <!--<img src="images/p_img.jpg" alt="">-->
        <s:iterator value="#request.map.func" var="func">
           ${func}
        </s:iterator>
    </div>
    <a href="javascript:;" class="mer_btn"><i></i>立即抢购</a>
</div>
<!--弹窗 开始-->
<!--class="overlay active" 增加active 遮罩层显示，然后内层div 加display:block 即可显示弹窗-->
<div class="overlay">
    <div class="mer_popup">
        <a href="javascript:;" class="popup_close"></a>
        <img src="<%=basePath%>images/WFJClient/ProductAgent/popup_img.png" alt="" class="popup_img">
        <div class="popup_text">
            您还不是代理商会员，无法<br>抢购招商商品
        </div>
        <a href="<%=basePath%>ea/wfjplatform/ea_getPlatform.jspa?type=getPlatform" class="popup_btn">立即升级为代理商会员</a>
    </div>
</div>
<!--弹窗 结束-->
</body>
<script type="text/javascript">
    var basePath = '<%=basePath%>';
    $(function () {

        //禁止编辑
        $('.editablesmall').attr("contenteditable",false);

        $('.mer_btn').click(function () {
            var susid = $('input[class="yj_radio"]:checked').attr("id");
            $.ajax({
                url : basePath + "ea/productAgent/sajax_snapUp.jspa?proSetupSub.susid="+susid,
                type : "get",
                async : false,
                success : function (data) {
                    var member = eval("("+data+")");
                    if (member.s=='yes')
                    {
                        alert("抢购成功！");
                        $(this).unbind("click");
                        window.location.href = basePath + "ea/productAgent/ea_productAgentList.jspa?";
                    }
                    else if (member.s=='noeq')
                    {
                        $('.overlay').addClass("active");
                        $('.overlay div:first').css("display","block");
                        $('.popup_text').html("代理商会员，只能抢购<br>同级别的招商商品");
                    }
                    else if (member.s=='gere')
                    {
                        $('.overlay').addClass("active");
                        $('.overlay div:first').css("display","block");
                        $('.popup_text').html("会员级别不够，请升级会员！");
                    }
                    else if (member.s=='re')
                    {
                        $('.overlay').addClass("active");
                        $('.overlay div:first').css("display","block");
                        $('.popup_text').html("不允许重复抢购<br>");
                    }
                    else if (member.s=='are')
                    {
                        $('.overlay').addClass("active");
                        $('.overlay div:first').css("display","block");
                        $('.popup_text').html("该区域代理已经抢购过了<br>");
                    }
                    else if (member.s=='no')
                    {
                        $('.overlay').addClass("active");
                        $('.overlay div:first').css("display","block");
                    }
                }
            });
        });

        // 关闭弹窗
        $('.popup_close').click(function () {
            $('.overlay').removeClass("active");
            $('.overlay div:first').css("display","none");
        });

    });
</script>
</html>
