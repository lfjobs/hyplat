<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>


<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>办公管理</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/5l5C/office/officeindex.css">
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>

</head>
<body id="">
<div class="pc-box">
    <div class="div-box">
        <header>
            <ul class="clearfix">
                <li>
                    <a onclick="window.history.go(-1);return false;" target="_self">
                        <img src="<%=basePath%>images/WFJClient/pc/5l5c/img_03.png"/>
                    </a>
                </li>
                <li>
                    办公管理
                </li>
                <li>

                </li>
            </ul>
        </header>
        <div class="container">

            <ul class="ul-con">
                <a href="<%=basePath%>page/WFJClient/pc/5l5C/office/zlplanmanage.jsp">
                    <li class="clearfix">
                        <p class="p-title">规划管理</p>
                        <p class="p-height clearfix">

                            企业战略规划
                            项目管理


                        </p>
                        <div class="div-more">
                            <img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
                        </div>
                    </li>
                </a>
                <a href="<%=basePath%>page/WFJClient/pc/5l5C/office/documentManage.jsp?companyID=${param.companyID}">
                    <li class="clearfix">

                        <p class="p-title">行政管理</p>
                        <p class="p-height">

                            文书管理
                            分类流程
                            文书模板

                        </p>
                        <div class="div-more">
                            <img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
                        </div>

                    </li>
                </a>
                <a href="<%=basePath%>page/WFJClient/pc/5l5C/office/infomanage.jsp">
                    <li class="clearfix">
                        <p class="p-title">信息管理</p>
                        <p class="p-height">

                            网络管理
                            公共信息管理
                            企业信息管理<br/>
                            网络安全管理
                            消息管理

                        </p>
                        <div class="div-more">
                            <img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
                        </div>
                    </li>
                </a>
                <a href="<%=basePath%>page/WFJClient/pc/5l5C/office/hqinmanage.jsp?companyID=${param.companyID}&staffID=${param.staffID}">
                    <li class="clearfix">
                        <p class="p-title">后勤管理</p>
                        <p class="p-height">
                            会议管理
                            接待管理
                            考场管理
                            停车场管理<br/>
                            安全管理
                            设备管理
                            住宿管理
                            餐饮管理<br/>
                            票务管理
                            资产库存管理
                            物品物料管理
                        </p>
                        <div class="div-more">
                            <img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
                        </div>
                    </li>
                </a>
                <a href="<%=basePath%>page/WFJClient/pc/5l5C/office/auditmanage.jsp">
                    <li class="clearfix">
                        <p class="p-title">督察管理</p>
                        <p class="p-height">
                            人事督查审核管理
                            办公室督查审核管理<br/>
                            财务督查审核管理
                            生产督查审核管理<br/>
                            营销督查审核管理
                        </p>
                        <div class="div-more">
                            <img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
                        </div>
                    </li>
                </a>

            </ul>
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
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/img_38.png" alt="">
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
                <li class="active">
                    <div>
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/img_37.png" alt="">
                    </div>
                    <p>
                        5L5C
                    </p>
                </li>
                <li>
                    <div>
                        <img src="<%=basePath%>images/WFJClient/pc/newimg/img_11.png" alt="">
                    </div>
                    <p>
                        我的
                    </p>
                </li>
            </ul>
        </div>
    </div>
</div>
<script type="text/javascript">
    var basePath = "<%=basePath%>";
    //计算中间区域宽度
    $(".p-height").each(function () {
        var pWth = $(".pc-box").width() - $(this).prev().width() - 100;
        $(this).width(pWth + "px")
    })
    //计算列表高度
    $(".p-height").each(function () {
//				console.log($(this).outerHeight())
//				console.log($(this).parent().outerHeight())
        var pHei = $(this).parent().outerHeight() - 51;
        $(this).parent().find(".p-title").css('line-height', pHei + "px");
        $(this).parent().find(".div-more").css('line-height', pHei + 50 + "px");
    })
    //判断页面是否有底部导航
    if ($("*").is(".div-bottom")) {
        $(".container").addClass("pc-bottom");
    }
</script>
</body>
</html>
