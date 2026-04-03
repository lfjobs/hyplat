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
    <title>后勤管理</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/5l5C/office/hqinmanage.css?version=1.1">
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
                    后勤管理
                </li>
                <li>

                </li>
            </ul>
        </header>
        <div class="container">
            <ul class="ul-con">
                <li class="clearfix">
                    <p class="p-title">批发商城</p>
                    <p class="p-height clearfix">
                        指定商家下单  会员商家下单
                    </p>
                    <div class="div-more">
                        <img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
                    </div>
                </li>
                <li class="clearfix">
                    <p class="p-title">接待管理</p>
                    <p class="p-height">
                        来访接待 预约接待
                        接待服务 送客服务
                    </p>
                    <div class="div-more">
                        <img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
                    </div>
                </li>
                <li class="clearfix">
                    <p class="p-title">考场训场</p>
                    <p class="p-height">
                        预约管理 考前培训 考试管理 设备管理 收费设置 收支余
                    </p>
                    <div class="div-more">
                        <img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
                    </div>
                </li>
                <a href="<%=basePath%>/page/WFJClient/pc/5l5C/office/tcmanage.jsp">

                <li class="clearfix">
                    <p class="p-title">停车管理</p>
                    <p class="p-height">
                        普通停车 设备 收支设置 收支余
                        空位停车 设备 收支设置 收支余
                    </p>
                    <div class="div-more">
                        <img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
                    </div>
                </li>
                </a>
<%--                    <a href="<%=basePath%>/page/ea/main/finance/invoicing/rukuBill.jsp?compayid=${param.companyID}&sccId=&staffid=${param.staffID}">--%>
                <%--                    <a href="<%=basePath%>/page/ea/main/finance/invoicing/rukuBill.jsp?compayid=${param.companyID}&sccId=&staffid=${param.staffID}">--%>
                <a href="<%=basePath%>/page/WFJClient/pc/5l5C/office/zchanmanage.jsp?compayid=${param.companyID}&sccId=&staffid=${param.staffID}">
                    <li class="clearfix">
                        <p class="p-title">资产管理</p>
                        <p class="p-height">
                            采购管理 收货验货 库房管理 领用管理 调拨管理
                        </p>
                        <div class="div-more">
                            <img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
                        </div>
                    </li>
                </a>
                <a href="<%=basePath%>page/WFJClient/pc/5l5C/office/securityManage.jsp?companyID=${param.companyID}">
                    <li class="clearfix">
                        <p class="p-title">安全管理</p>
                        <p class="p-height">
                            安全值班 隐患排查 隐患处理 应急管理
                        </p>
                        <div class="div-more">
                            <img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
                        </div>
                    </li>
                </a>
                <li class="clearfix">
                    <p class="p-title">卫生管理</p>
                    <p class="p-height">
                        卫生值班 卫生设备 卫生考评
                    </p>
                    <div class="div-more">
                        <img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
                    </div>
                </li>
                <a href="<%=basePath%>page/WFJClient/pc/5l5C/office/deviceManage.jsp">
                    <li class="clearfix">
                        <p class="p-title">设备管理</p>
                        <p class="p-height">
                            人事设备 办公设备 财务设备 生产设备
                            营销设备 人脸闸机设备<br>

                        </p>
                        <div class="div-more">
                            <img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
                        </div>
                    </li>
                </a>
                <li class="clearfix">
                    <p class="p-title">物流管理</p>
                    <p class="p-height">
                        物流 快递
                    </p>
                    <div class="div-more">
                        <img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
                    </div>
                </li>
                <a href="<%=basePath%>/page/water/index.jsp">
                    <li class="clearfix">
                        <p class="p-title">用水管理</p>
                        <p class="p-height">
                            水价设置 用水 水费  充值 设备绑定 收支余
                        </p>
                        <div class="div-more">
                            <img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
                        </div>
                    </li>
                </a>
                <a href="<%=basePath%>/page/electricity/index.jsp">
                    <li class="clearfix">
                        <p class="p-title">用电管理</p>
                        <p class="p-height">
                            电价设置 用电 电费 充值 设备绑定 收支余
                        </p>
                        <div class="div-more">
                            <img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
                        </div>
                    </li>
                </a>

                <a href="<%=basePath%>/page/houseRent/index.jsp">
                    <li class="clearfix">
                        <p class="p-title">房租管理</p>
                        <p class="p-height">
                            房源发布 房源展示 房东出租 房租收付<br>
                        </p>
                        <div class="div-more">
                            <img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
                        </div>
                    </li>
                </a>

                <li class="clearfix">
                    <p class="p-title">租赁管理</p>
                    <p class="p-height">
                        房源发布 房源展示 出租管理 充值 租费绑定 租金设置 收支余
                    </p>
                    <div class="div-more">
                        <img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
                    </div>
                </li>
                <li class="clearfix">
                    <p class="p-title">值班管理</p>
                    <p class="p-height">
                        排班设置 排班值守 值守考评
                    </p>
                    <div class="div-more">
                        <img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
                    </div>
                </li>
                <li class="clearfix">
                    <p class="p-title">绿化管理</p>
                    <p class="p-height">
                        绿化 维护  收支
                    </p>
                    <div class="div-more">
                        <img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
                    </div>
                </li>
                <li class="clearfix">
                    <p class="p-title">住宿管理</p>
                    <p class="p-height">
                        住宿 收支余
                    </p>
                    <div class="div-more">
                        <img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
                    </div>
                </li>
                <li class="clearfix">
                    <p class="p-title">餐饮管理</p>
                    <p class="p-height">
                        餐饮 收支余
                    </p>
                    <div class="div-more">
                        <img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
                    </div>
                </li>
                <li class="clearfix">
                    <p class="p-title">基建管理</p>
                    <p class="p-height">
                        基建项目 项目分配 项目跟踪 结果 收支余
                    </p>
                    <div class="div-more">
                        <img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
                    </div>
                </li>
                <a href="<%=basePath%>page/WFJClient/pc/5l5C/office/kcunList.jsp">
                    <li class="clearfix">
                    <p class="p-title">库存管理</p>
                    <p class="p-height">
                        库房管理 产品发布 产品管理
                    </p>
                    <div class="div-more">
                        <img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
                    </div>
                </li>
                </a>
                <!--<li class="clearfix">
							<p class="p-title">销售报表</p>
							<p class="p-height">
								毛利润报表  商品销售成本报表   销售 订单报表  销售收入报表毛利润报表  商品销售成本报表
							</p>
							<div class="div-more">
								<img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
							</div>
						</li>-->
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
