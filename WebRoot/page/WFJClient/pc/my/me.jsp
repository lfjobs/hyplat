
<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ page language="java" import="com.tiantai.wfj.bo.TEshopCusCom" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>


<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>

    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/my/me.css">
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>

    <%--<script src="<%=basePath%>js/font-size.js" type="text/javascript" charset="utf-8"></script>--%>

    <script src="<%=basePath%>js/comm.js" type="text/javascript" charset="utf-8"></script>

    <script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/WFJClient/pc/my/me.js" type="text/javascript" charset="utf-8"></script>
    <title>&lrm;</title>

</head>
<body>
<header>
    <ul class="clearfix">
        <li>
            <!-- <img src="<%=basePath%>images/WFJClient/pc/newimg/return.png" > -->
        </li>
        <li>
            我的
        </li>
        <li>
        </li>
    </ul>
</header>
<div class="content">
    <div class="div-header clearfix">
        <div class="div-img">
            <img src="<%=basePath%>${userinfo[3]}" onerror='this.src="<%=basePath%>images/WFJClient/pc/my/pcimg_07.png"'

                 onclick="baseInfo()" alt="">

        </div>
        <div class="div-text">
            <p><span onclick="baseInfo()">${userinfo[1]}</span>
                <%--<c:if test="${userinfo[4] eq null}">
                    <img src="<%=basePath%>images/ea/office/contract/selectp/wrz.png" class="rz"></c:if>
                <c:if test="${userinfo[4] ne null}">
                    <img src="<%=basePath%>images/ea/office/contract/selectp/yrz.png" class="rz finish"></c:if>--%>
                <img style="width: 60px;height: 45px" src="<%=basePath%>images/WFJClient/pc/newimg/wdrz.png" class="rz">
            </p>
            <p>${userinfo[2]}</p>
        </div>
        <div class="div-right">
            <img src="<%=basePath%>images/WFJClient/pc/newimg/img_41.png" alt="" onclick="mycode()">
        </div>
    </div>
    <div class="div-news">
        <div class="interest">
            <div style="margin-left: 10px"><b>关注</b></div>
            <div class="interestInfo">
                <c:forEach var="item" items="${recommendList}">
                    <div style="margin-left: 10px" onclick="goTo('${item[1]}','${item[2]}')">${item[0]}</div>
                </c:forEach>
            </div>
            <div style="margin-left: 10px" onclick="showDG()">更多...</div>
        </div>
    </div>
    <div class="div-news">
<%--        <ul>--%>
<%--            <li class="clearfix">--%>
<%--                <div class="div-top clearfix" id="zx">--%>
<%--                    <p>资讯</p>--%>
<%--                    <div class="div-img">--%>
<%--                        <a href="javascript:zxselect()"><img src="<%=basePath%>images/WFJClient/pc/newimg/img_42.png"--%>
<%--                                                             alt=""></a>--%>
<%--                    </div>--%>
<%--                </div>--%>

<%--            </li>--%>
<%--            <li class="clearfix">--%>
<%--                <div class="div-top clearfix" id="wmfs">--%>
<%--                    <a href="<%=basePath%>/ea/dsp/ea_getMyHomePage.jspa">--%>
<%--                        <p style="width: 80% !important;">微美粉视</p>--%>


<%--                        <div class="div-img">--%>
<%--                            <img src="<%=basePath%>images/WFJClient/pc/newimg/img_42.png" alt="">--%>
<%--                        </div>--%>
<%--                    </a>--%>
<%--                </div>--%>
<%--            </li>--%>
<%--        </ul>--%>

    </div>
    <div class="div-my">
        <h3>
            我的资料
        </h3>
        <ul>
            <li class="clearfix">
                <a href="<%=basePath%>/ea/documentcommon/ea_showDocumentModule.jspa?bb=new&module=">
                    <div class="img-left">
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/img_43.png" alt="">
                    </div>

                    <p>办公通</p>
                    <div class="img-right">
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/img_39.png" alt="">
                    </div>
                </a>
            </li>
            <li class="clearfix">
                <a href="<%=basePath%>ea/consult/ea_getConsultslist.jspa">
                    <div class="img-left">
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/img_44.png" alt="">
                    </div>
                    <p>客户咨询<span class="tip-span">0</span></p>
                    <div class="img-right">
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/img_39.png" alt="">
                    </div>
                </a>
            </li>
        </ul>
    </div>
    <div class="div-my">
        <h3>
            我的商圈
        </h3>
        <ul>
            <li class="clearfix">

                <a href="<%=basePath%>/ea/vipcenter/ea_findconWealth.jspa?sccid=<%=session.getAttribute("key_shop_cus_com")!=null ?((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getSccId():"" %>">
                    <div class="img-left">
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/img_45.png" alt="">
                    </div>
                    <p>我的团队</p>
                    <div class="img-right">
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/img_39.png" alt="">
                    </div>
                </a>
            </li>
            <li class="clearfix">
                <a href="javascript:publishproduct();">


                    <div class="img-left">
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/img_46.png" alt="">
                    </div>
                    <p>一键财富</p>
                    <div class="img-right">
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/img_39.png" alt="">
                    </div>
                </a>
            </li>
            <li class="clearfix">
                <a href="javascript:mycode()">

                    <div class="img-left">
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/img_47.png" alt="">
                    </div>
                    <p>推广分享</p>
                    <div class="img-right">
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/img_39.png" alt="">
                    </div>
                </a>
            </li>
            <li class="clearfix">
                <a href="<%=basePath%>ea/jinbi/ea_gethyjifen.jspa?user=<%=session.getAttribute("key_shop_cus_com")!=null ?((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getAccount():"" %>&sccid=<%=session.getAttribute("key_shop_cus_com")!=null ?((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getSccId():"" %>&khd=0&app=00">

                    <div class="img-left">
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/img_48.png" alt="">
                    </div>
                    <p>我的钱包</p>
                    <div class="img-right">
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/img_39.png" alt="">
                    </div>
                </a>
            </li>
<%--            <li class="clearfix">--%>
<%--                <a href="<%=basePath%>ea/vipcenter/ea_orderManage.jspa?sccid=<%=session.getAttribute("key_shop_cus_com")!=null ?((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getSccId():"" %>&ret=ret">--%>

<%--                    <div class="img-left">--%>
<%--                        <img src="<%=basePath%>images/WFJClient/pc/newimg/img_49.png" alt="">--%>
<%--                    </div>--%>
<%--                    <p>项目订单</p>--%>
<%--                    <div class="img-right">--%>
<%--                        <img src="<%=basePath%>images/WFJClient/pc/newimg/img_39.png" alt="">--%>
<%--                    </div>--%>
<%--                </a>--%>
<%--            </li>--%>
            <li class="clearfix">
                <a id="buyProject" href="">
                    <div class="img-left">
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/img_49.png" alt="">
                    </div>
                    <p>采购销售订单</p>
                    <div class="img-right">
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/img_39.png" alt="">
                    </div>
                </a>
            </li>
            <li class="clearfix">
                <a id="" href="<%=basePath%>/page/ea/main/marketing/edmandServe/mapList.jsp">
                    <div class="img-left">
                        <img src="<%=basePath%>/images/WFJClient/pc/newimg/img_28.png" alt="">
                    </div>
                    <p>商圈</p>
                    <div class="img-right">
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/img_39.png" alt="">
                    </div>
                </a>
            </li>
            <li class="clearfix">
                <a href="<%=basePath%>ea/vipcenter/ea_vipDemand.jspa?sccid=<%=session.getAttribute("key_shop_cus_com")!=null ?((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getSccId():"" %>">
                <%--<a href="<%=basePath%>page/ea/main/marketing/edmandServe/certificate_Specifics.jsp?staffid=&sccId=<%=session.getAttribute("key_shop_cus_com")!=null ?((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getSccId():"" %>">--%>
                    <div class="img-left">
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/img_50.png" alt="">
                    </div>
                    <p>家应急</p>
                    <div class="img-right">
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/img_39.png" alt="">
                    </div>
                </a>
            </li>
            <li class="clearfix">
                <a href="<%=basePath%>ea/productAgent/ea_investmentPro.jspa?companyId=<%=session.getAttribute("key_shop_cus_com")!=null ?((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getCompanyId():"" %>">
                    <%--<a href="<%=basePath%>page/ea/main/marketing/edmandServe/certificate_Specifics.jsp?staffid=&sccId=<%=session.getAttribute("key_shop_cus_com")!=null ?((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getSccId():"" %>">--%>
                    <div class="img-left">
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/img_50.png" alt="">
                    </div>
                    <p>公司招商</p>
                    <div class="img-right">
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/img_39.png" alt="">
                    </div>
                </a>
            </li>
            <li class="clearfix">
                <a href="<%=basePath%>ea/wfjplatform/ea_getPlatform.jspa?type=getPlatform">
                    <div class="img-left">
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/img_51.png" alt="">
                    </div>
                    <p>业务升级</p>
                    <div class="img-right">
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/img_39.png" alt="">
                    </div>
                </a>
            </li>
        </ul>
    </div>
    <div class="div-set">
        <a href="<%=basePath%>ea/mycenter/ea_getSetlist.jspa">

            <div>
                <img src="<%=basePath%>images/WFJClient/pc/my/set.png">
                <h3>
                    设置
                </h3>
            </div>
        </a>
    </div>

    <div style="height: 134px"></div>

    <div class="content-bottom"></div>
</div>
<div class="footer div-bottom">
    <ul class="clearfix">
        <li>
            <div>
                <img src="<%=basePath%>images/WFJClient/pc/newimg/img_23.png" alt="">
            </div>
            <p>
                消息
            </p>
        </li>
        <li>
            <div>
                <img src="<%=basePath%>images/WFJClient/pc/newimg/img_07.jpg" alt="">
            </div>
            <p>
                通讯
            </p>
        </li>
        <li>
            <div>
                <img src="<%=basePath%>images/WFJClient/pc/newimg/img_08.jpg" alt="">
            </div>
            <p>
                数字
            </p>
        </li>
        <li>
            <div>
                <img src="<%=basePath%>images/WFJClient/pc/newimg/img_10.jpg" alt="">
            </div>
            <p>
                5L5C
            </p>
        </li>
        <li class="active">
            <div>
                <img src="<%=basePath%>images/WFJClient/pc/newimg/img_40.png" alt="">
            </div>
            <p>
                我的
            </p>
        </li>
    </ul>
</div>

<div class="zx-div">
    <div class="select-div">
        <img class="close-zx" src="<%=basePath%>images/ea/office/contract/del.png" alt=""/>
        <p class="grfb">个人发布</p>
        <p class="gsfb">公司发布</p>
    </div>
</div>

<%--<div id="download-app" style="position: fixed; bottom: 72px; width: 100%; display: none; background: white;">--%>
<%--    <p style="position: absolute; right: 16px;text-align: center; color: rgb(128,128,128); font-size: 0.8rem" onclick="closeHelp()">关闭</p>--%>
<%--    <div style="padding-bottom: 24px">--%>
<%--        <p style="text-align: center; color: rgb(128,128,128); font-size: 1.0rem">下载APP使用更方便</p>--%>
<%--        <div style="display: flex; justify-content: center">--%>
<%--            &lt;%&ndash;<a href="http://www.impf2010.com:80/upload_files/company201009046vxdyzy4wg0000000025/upload_files/ea/Android/wfj.apk">--%>
<%--                <img style="width: 100%" src="<%=basePath%>images/WFJClient/zhuce/6895_07.png"/>--%>
<%--            </a>&ndash;%&gt;--%>
<%--            <a href="https://sj.qq.com/search?q=%E6%95%B0%E5%AD%97%E5%9C%B0%E7%90%83" target="_blank">--%>
<%--                <img style="width: 100%" src="<%=basePath%>images/WFJClient/zhuce/6895_07.png"/>--%>
<%--            </a>--%>
<%--            <a href="https://apps.apple.com/cn/app/shu-zi-de-qiu/id1182214522?l=en">--%>
<%--                <img style="width: 100%" src="<%=basePath%>images/WFJClient/zhuce/6895_09.png"/>--%>
<%--            </a>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--</div>--%>
<script type="text/javascript">
    document.addEventListener("DOMContentLoaded", function() {
        // var str=Android.setAndroidFlag();
        // setAndroidFlag(str)
        // setShowFromSystem("zx");
        // setShowFromSystem("wmfs");
    });

</script>
<script>
    initTagCache(); // 页面加载时自动开始监听和恢复标签（可选）

    function isApp() {
        let deviceId = "";
        try {
            if(isAndroid) {
                deviceId = Android.forAndroidDeviceId()
            }
            if(isiOS) {
                deviceId = "-"
            }
        } catch (e) {
            deviceId = ""
        }
        return deviceId !== ""
    }

    function closeHelp() {
        document.getElementById("download-app").style.display = "none"
    }

    document.getElementById("download-app").style.display = isApp() ? "none" : "block"
</script>

</body>
<!-- <script src="js/less.js" type="text/javascript" charset="utf-8"></script> -->
<script type="text/javascript">
    var basePath = "<%=basePath%>";
    var sccid = '<%=session.getAttribute("key_shop_cus_com")!=null ?((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getSccId():"" %>';
    var companyID = '<%=session.getAttribute("key_shop_cus_com")!=null ?((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getCompanyId():"" %>';
    var user = '<%=session.getAttribute("key_shop_cus_com")!=null ?((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getAccount():"" %>';
    var staffID = '<%=session.getAttribute("key_shop_cus_com")!=null ?((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getStaffid():"" %>';

    function goTo(url,menuId){
        //请求数据库添加数据并跳转地址
        $.ajax({
            url: basePath + "ea/mycenter/sajax_ea_saveMenuRecommend.jspa",
            type: "POST",
            dataType: "json",
            aysnc: false,
            data: {
                "pmr.menuId": menuId
            },
            success: function (data) {
                if(url!=null){
                    url= tihuan(url);
                    document.location.href =  basePath+url;

                }
            },
            error: function (data) {
                document.location.href = basePath+"page/WFJClient/pc/pc_login.jsp";
            }
        })
    }
    function showDG(){
        document.location.href = "<%=basePath%>page/WFJClient/pc/my/myRelated.jsp?number=1";
    }
    function tihuan(url){
        const regex = /\((.*?)\)/;
        let match = url.match(regex);
        while (match) {
            const content = match[1]; // 括号内的内容
            var replacement = "";
            if ("sccid=" == content) {
                replacement = "sccid=" + sccid;
            }
            if ("user=" == content) {
                replacement = "user=" + user;
            }
            if ("companyId=" == content) {
                replacement = "companyId=" + companyID;
            }
            url = url.replace("(" + content + ")", replacement);
            match = url.match(regex);
        }
        return url;
    }

    $(document).ready(function () {
        $("#buyProject").attr("href","<%=basePath%>ea/scBudget/ea_toProjectBillList.jspa?tenantFlag=personal&billsType=初始项目单&companyid="+companyID+"&staffId="+staffID+"&sccId="+sccid);
    });

</script>
</html>

