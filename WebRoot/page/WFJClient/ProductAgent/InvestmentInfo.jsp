<!DOCTYPE html>
<html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";

%>

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
    <link rel="stylesheet" href="<%=basePath%>css/WFJClient/ProductAgent/mer_manger.css">
    <script src="<%=basePath%>js/WFJClient/jquery.min.js"></script>
    <title>查看项目</title>
</head>
<script type="text/javascript">
    var basePath = '<%=basePath%>';
</script>
<body>
    <header class="com_head">
        <a href="<%=basePath%>ea/productAgent/ea_investmentPro.jspa?companyId=${companyId}" class="back"></a>
        <h1>招商发布</h1>
    </header>
    <div class="wrap_page">
        <div class="mer_pro_wrap">
            <div class="mer_pro_box mer_det">
                <div class="mer_pro_top clearfix">
                    <img src="<%=basePath%><s:property value='#request.map.plist[0][2]'/>" class="mer_pro_img" alt="">
                    <div class="mer_pro_text">
                        <div class="mer_pro_tit">
                           <s:property value="#request.map.plist[0][0]"/>
                        </div>
                        <%--<div class="mer_pro_price">￥<s:property value="#request.map.plist[0][5]"/></div>--%>
                    </div>
                </div>
            </div>
        </div>
        <a href="###" class="set_require">设置代理规则要求</a>
        <div class="apply_list_wrap">
            <s:iterator value="#request.map.psslist" var="entity">
            <div class="apply_list" title="${entity.agenttype}">
                <div class="apply_tit">
                    <s:if test="%{#entity.agenttype=='00'}">
                        <span id="entity<s:property value='#entity.agenttype'/>">贴牌</span>
                        <%--<span class="a_tit_R">代理佣金：<s:property value="#entity.amount"/>元</span>--%>
                    </s:if>
                    <s:if test="%{#entity.agenttype=='01'}">
                        <span id="entity<s:property value='#entity.agenttype'/>">设备安装</span>
                        <%--<span class="a_tit_R">代理佣金：<s:property value="#entity.amount"/>元</span>--%>
                    </s:if>
                    <s:if test="%{#entity.agenttype=='02'}">
                        <span id="entity<s:property value='#entity.agenttype'/>">省级代理</span>
                        <%--<span class="a_tit_R">代理佣金：<s:property value="#entity.amount"/>元</span>--%>
                    </s:if>
                    <s:if test="%{#entity.agenttype=='03'}">
                        <span id="entity<s:property value='#entity.agenttype'/>">县级代理</span>
                        <%--<span class="a_tit_R">代理佣金：<s:property value="#entity.amount"/>元</span>--%>
                    </s:if>
                    <s:if test="%{#entity.agenttype=='04'}">
                        <span id="entity<s:property value='#entity.agenttype'/>">村级代理</span>
                        <%--<span class="a_tit_R">代理佣金：<s:property value="#entity.amount"/>元</span>--%>
                    </s:if>
                </div>
                <s:if test="%{#request.map.aplist.size() > 0}">
                    <s:iterator value="#request.map.aplist" var="en">
                        <s:if test="%{#entity.pid==#en[0]}">
                            <div class="apply_com_wrap">
                                <a href="<%=basePath%>ea/productAgent/ea_applyDetail.jspa?ppId=<s:property value='#request.map.plist[0][1]'/>&investApply.inapId=<s:property value='#en[1]'/>&companyId=${companyId}"
                                   class="apply_com clearfix">
                                    <img src="<%=basePath%><s:property value='#en[3]'/>" class="apply_com_logo" alt="">
                                    <div class="apply_com_text">
                                        <div class="apply_com_name"><s:property value="#en[2]"/><s:if test="%{#en[5]!=null}"> - <s:property value="#en[5]"/></s:if></div>
                                        <div class="apply_com_state">已申请</div>
                                    </div>
                                </a>
                                <s:if test="%{#en[4]=='01'}">
                                	<div class="2subEntity<s:property value='#entity.agenttype'/>">
                                    <a href="javascript:;" class="apply_btn agreed" style="width:18.5%">审核通过</a>
                                     </div>
                                </s:if>
                                <s:else>
                                	<div class="subEntity<s:property value='#entity.agenttype'/>">
                                    <a href="javascript:;" class="apply_btn" title="<s:property value='#en[6]'/>"  id="<s:property value='#en[1]'/>">通过</a>
                                    <font></font>
                                    </div>
                                </s:else>
                            </div>
                        </s:if>
                        <s:else>
                            <div class="no_com_wrap clearfix">
                                <img src="<%=basePath%>images/WFJClient/ProductAgent/no_apply.png" class="no_com_img" alt="">
                                <div class="no_com_text">暂时还没有人申请代理！</div>
                            </div>
                        </s:else>
                    </s:iterator>
                </s:if>
                <s:else>
                    <div class="no_com_wrap clearfix">
                        <img src="<%=basePath%>images/WFJClient/ProductAgent/no_apply.png" class="no_com_img" alt="">
                        <div class="no_com_text">暂时还没有人申请代理！</div>
                    </div>
                </s:else> 
            </div>
            </s:iterator>
        </div>
    </div>
</body>

<script type="text/javascript">

$(function () {
    //去重复
   $(".apply_list").each(function()
   {
       $(this).find(".no_com_wrap").first().nextAll('.no_com_wrap').remove();
   })
   $('.apply_com_wrap').each(function () {
       $(this).parent().find(".no_com_wrap").remove();
   }) ;
   
   $(".agreed").each(function(){
	   var divClass=$(this).parent().attr("class").replace("2","");
	   var divTitle=$(this).parent().parent().parent().attr("title");
	   if(divTitle!="02"&&divTitle!="03"&&divTitle!="04"){
           $(".apply_btn").each(function(){
               if(divClass==$(this).parent().attr("class")){
                   $(this).next("font").text("未通过").addClass("apply_btn agreed").css("color","#ff4a00");
                   $(this).remove();
               }
           });
       }
   });
})

//同意申请

$(".apply_btn").click(function(){
     var inapId = $(this).attr('id');
     var areappid=$(this).attr("title");
     if ($(this).hasClass("agreed"))
     {
         $(this).unbind("click");
     }
     else
     {
         var divTitle=$(this).parent().parent().parent().attr("title");
             if(divTitle=="02"||divTitle=="03"||divTitle=="04"){
                 $.ajax({
                 url: basePath + "ea/productAgent/sajax_getSnapCount.jspa?",
                 type: "get",
                 async: false,
                 data:{
                     'ppId':'${ppId}',
                     'areappid':areappid
                 },
                 success: function (data) {
                     var member = eval("(" + data + ")");
                     var count=member.count;
                     if(count>0){
                         alert("这个区域已经有人代理，不可以重复代理！");
                     }else{
                         document.location.href = basePath + "ea/productAgent/ea_agreeApply.jspa?ppId=${ppId}&companyId=${companyId}&investApply.inapId=" + inapId;
                     }
                 }
             });
         }else{
             document.location.href = basePath + "ea/productAgent/ea_agreeApply.jspa?ppId=${ppId}&companyId=${companyId}&investApply.inapId=" + inapId;
         }
     }
 });
</script>
</html>
