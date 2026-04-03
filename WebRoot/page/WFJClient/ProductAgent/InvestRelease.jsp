<!DOCTYPE html>
<html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
    <title>添加招商要求</title>
</head>

<body>
    <header class="com_head">
        <s:if test="%{flag=='edit'}">
            <a href="<%=basePath%>ea/productAgent/ea_investmentPro.jspa?companyId=${companyId}" class="back"></a>
        </s:if>
        <s:else>
            <a href="<%=basePath%>ea/productAgent/ea_investmentProducts.jspa?companyId=${companyId}" class="back"></a>
        </s:else>
        <h1>招商发布</h1>
    </header>
    <div class="wrap_page">
        <form id="InvestForm">
            <input type="hidden" name="ppId" value=""/>
        <div class="mer_pro_wrap">
            <s:iterator value="#request.list" var="entity">
            <div class="mer_pro_box" id="<s:property value='#entity.ppID'/>">
                <div class="mer_pro_top clearfix">
                    <img src="<%=basePath%><s:property value="#entity.image"/>" class="mer_pro_img" alt="">
                    <div class="mer_pro_text">
                        <div class="mer_pro_tit">
                            <s:property value="#entity.goodsName"/>
                        </div>
                        <%--<div class="mer_pro_price">￥<s:property value="#entity.rePrice"/></div>--%>
                    </div>
                </div>
                <div class="mer_pro_bottom clearfix">
                    <s:iterator value="%{#entity.pmlist}" var="en">
                        <s:if test="%{#en.ppID=='p20170220ZVZR76B88M0000000016'}">
                            <div class="mer_type"><i></i>贴牌<%--：<span><s:property value="#en.amount"/>元</span>--%></div>
                        </s:if>
                        <s:if test="%{#en.ppID=='p20170220ZVZR76B88M0000000017'}">
                            <div class="mer_type"><i></i>设备安装<%--：<span><s:property value="#en.amount"/>元</span>--%></div>
                        </s:if>
                        <s:if test="%{#en.ppID=='p20170220ZVZR76B88M0000000018'}">
                            <div class="mer_type"><i></i>省级代理<%--：<span><s:property value="#en.amount"/>元</span>--%></div>
                        </s:if>
                        <s:if test="%{#en.ppID=='p20170220ZVZR76B88M0000000019'}">
                            <div class="mer_type"><i></i>县级代理<%--：<span><s:property value="#en.amount"/>元</span>--%></div>
                        </s:if>
                        <s:if test="%{#en.ppID=='p20170220ZVZR76B88M0000000020'}">
                            <div class="mer_type"><i></i>村级代理<%--：<span><s:property value="#en.amount"/>元</span>--%></div>
                        </s:if>
                    </s:iterator>
                </div>
            </div>
            </s:iterator>
        </div>
        <div class="mer_require_wrap">
            <i class="triangle_01"></i>
            <i class="triangle_02"></i>
            <i class="line line_01"></i>
            <i class="line line_02"></i>
            <textarea class="mer_require"  name="html" placeholder="请输入招商要求…"><s:property value="#request.de.func"/></textarea>
        </div>
        </form>
    </div>
    <a href="javascript:;" class="fix_btn next"><i></i>保存发布</a>
    <script type="text/javascript">
        var basePath = '<%= basePath%>';
        var companyId = '${companyId}';
        var ppId = '';

        //处理浏览器输入法遮挡
        var screenH=window.innerHeight;
         window.onresize = function () {
            var t=window.innerHeight;    
             console.log(t);
             console.log(screenH);
            var inp=$("textarea:focus")[0];
            if(t<screenH){
               inp.scrollIntoView(false);
            }
         }

         //报存发布
        $(".next").click(function () {

            if ($(".mer_require").val()=='')
            {
                alert("请编辑招商要求！");
                return;
            }
            $('.mer_pro_box').each(function () {
                ppId += $(this).attr("id") + ",";
                $("input[name='ppId']").val(ppId);
            });
            $.ajax({
                url : basePath + "ea/productAgent/sajax_saveProAgent.jspa?",
                type : "post",
                data : $('#InvestForm').serialize(),
                async : false,
                success : function (data) {
                    var member = eval("("+data+")");
                    if (member.flag == '1')
                    {
                        alert("保存成功！");
                        window.location.href = basePath + "ea/productAgent/ea_investmentPro.jspa?companyId=" + companyId + "&flag=00";
                    }
                },
                error : function (data) {
                     alert("保存失败！");
                }

            });
        });
    </script>
</body>

</html>
