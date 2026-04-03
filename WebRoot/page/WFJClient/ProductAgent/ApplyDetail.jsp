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
    <title>申请详情</title>
    <script type="text/javascript">
        var basePath = '<%=basePath%>';
        var divTitle="";
        var areappid="";
    </script>
</head>

<body>
    <header class="com_head">
        <a href="<%=basePath%>ea/productAgent/ea_investmentInfo.jspa?ppId=${ppId}&companyId=${companyId}" class="back"></a>
        <h1>招商发布</h1>
    </header>
    <div class="wrap_page">
        <div class="mer_pro_wrap">
            <div class="mer_pro_box mer_det">
                <div class="mer_pro_top clearfix">
                    <img src="<%=basePath%><s:property value='#request.map.pp[0][0]'/>" class="mer_pro_img" alt="">
                    <div class="mer_pro_text">
                        <div class="mer_pro_tit">
                            <s:property value="#request.map.pp[0][2]"/>
                        </div>
                        <%--<div class="mer_pro_price">￥<s:property value="#request.map.pp[0][3]"/></div>--%>
                    </div>
                </div>
            </div>
        </div>
        <div class="apply_list_wrap">
            <s:iterator value="#request.map.list" var="entity">
                <script type="text/javascript">
                    divTitle="${entity[2]}";
                    areappid="${entity[7]}";
                </script>
                <div class="apply_list">
                    <div class="apply_tit">
                        <s:if test="%{#entity[2]=='00'}">
                            <span>贴牌</span>
                        </s:if>
                        <s:if test="%{#entity[2]=='01'}">
                            <span>设备安装</span>
                        </s:if>
                        <s:if test="%{#entity[2]=='02'}">
                            <span>省级代理</span>
                        </s:if>
                        <s:if test="%{#entity[2]=='03'}">
                            <span>县级代理</span>
                        </s:if>
                        <s:if test="%{#entity[2]=='04'}">
                            <span>村级代理</span>
                        </s:if>
                        <%--<span class="a_tit_R">代理佣金：<s:property value="#entity[3]"/>元</span>--%>
                    </div>
                    <div class="apply_com_wrap">
                        <div class="apply_com clearfix">
                            <img src="<%=basePath%><s:property value="#entity[3]"/>" class="apply_com_logo" alt="">
                            <div class="apply_com_text com_det_text">
                                <div class="apply_com_name"><s:property value="#entity[4]"/><s:if test="%{#en[5]!=null}"> - <s:property value="#en[5]"/></s:if></div>
                                <div class="apply_com_state">已申请</div>
                            </div>
                        </div>
                    </div>
                </div>
            </s:iterator>
            <!--<div class="reason_wrap">
                <div class="reason_tit">申请理由</div>
                <div class="reason_con">您好！我商场于2011年5月30日建工完成，考虑商场初期发展，需要引进一批商户进驻商场。
商场的各项建设许可，以及内部环境绘制图，结构图，区块划分图已经向贵局相关部门递交过了。 
希望区域招商引资办相关领导考虑我商场实际经营困难，批准我商场开展招商引资项目。
在这里，我恳请招商引资办予以批准我们的招商申请！
                </div>
            </div>-->
        </div>
    </div>
    <s:if test="%{#request.map.list[0][5]=='01'}">
        <a href="javascript:;" class="fix_btn agree_btn"><i></i>已同意申请</a>
    </s:if>
    <s:else>
        <a href="javascript:;" class="fix_btn agree_btn ok_btn"><i></i>同意申请</a>
    </s:else>
    <script type="text/javascript">
        $(function () {
            $(".ok_btn").click(function () {
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
                                alert(123);
                                //document.location.href = basePath + "ea/productAgent/ea_agreeApply.jspa?ppId=${ppId}&companyId=${companyId}&investApply.inapId=${request.map.list[0][1]}";
                            }
                            alert(789);
                        }
                    });
                }else{
                    alert(456);
                    //document.location.href = basePath + "ea/productAgent/ea_agreeApply.jspa?ppId=${ppId}&companyId=${companyId}&investApply.inapId=${request.map.list[0][1]}";
                }
            })
        });
    </script>
</body>

</html>
