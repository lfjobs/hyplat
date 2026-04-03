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
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/5l5C/office/hqinmanage.css">
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
                    <p class="p-title">会议管理</p>
                    <p class="p-height clearfix">
                        会务组织机构人员配备 会议准备阶段模块管理 正式会议阶段模块管理 会议闭幕阶段模块管理 会议室预约管理 员工会议管理 可参与的会议 我创建的会议
                    </p>
                    <div class="div-more">
                        <img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
                    </div>
                </li>
                <li class="clearfix">
                    <p class="p-title">接待管理</p>
                    <p class="p-height">
                        个人接待信息管理 个人接待信息报表 往来单位管理
                    </p>
                    <div class="div-more">
                        <img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
                    </div>
                </li>
                <li class="clearfix">
                    <p class="p-title">考场管理</p>
                    <p class="p-height">
                        考场管理 收费标准 考场记录
                        <a style="font-size: 16px" onclick="deviceInfo()">人脸闸机设备管理</a>
                    </p>
                    <div class="div-more">
                        <img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
                    </div>
                </li>
                <li class="clearfix">
                    <p class="p-title">停车场管理</p>
                    <p class="p-height">
                        汇总 进入车辆管理 历史记录(出) 异常数据 收费标准 场地管理 停车位管理 设备管理
                    </p>
                    <div class="div-more">
                        <img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
                    </div>
                </li>
                <a href="<%=basePath%>page/WFJClient/pc/5l5C/office/securityManage.jsp?companyID=${param.companyID}">
                    <li class="clearfix">
                        <p class="p-title">安全管理</p>
                        <p class="p-height">
                            钥匙管理 安全类别 安全单据
                        </p>
                        <div class="div-more">
                            <img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
                        </div>
                    </li>
                </a>
                <li class="clearfix">
                    <p class="p-title">安全防范管理</p>
                    <p class="p-height">
                        火灾预防 防盗管理 防霉管理 防毒管理 污染预防<br/>
                        雪灾预防 冰雹预防 冻害预防 垮塌预防 地震预防<br/>
                        洪涝预防 防泥石流 虫害预防 疾病预防 安全用电<br/>
                        雷雨预防 防龙卷风 食品安全 车辆设备
                    </p>
                    <div class="div-more">
                        <img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
                    </div>
                </li>
                <li class="clearfix">
                    <p class="p-title">车管设备</p>
                    <p class="p-height">
                        完善车辆信息 车辆基本信息 车辆门禁 检查点 检查项<br/>
                        巡检计划 巡检任务 巡检结果 车牌号维护 购车发票<br/>
                        购置税发票 购置保险信息 购置年检信息 车辆CNG信息<br/>
                        车辆安全信息 车辆资产信息 车辆使用信息 车辆维护信息<br/>
                        相关证件子集 机动车行驶证 道路运输证 车用瓶使用证<br/>
                        车辆购置税证 车辆违章信息 车辆准载座位 安全卫生检查<br/>
                        接送预约信息管理 接送预约信息报表管理 <br/>
                        接送基本信息管理 接送信息报表管理 <br/>
                        接送到达基本信息管理 接送到达信息报表管理
                    </p>
                    <div class="div-more">
                        <img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
                    </div>
                </li>
                <li class="clearfix">
                    <p class="p-title">设备管理</p>
                    <p class="p-height">
                        工程机械 办公室设备 微信刷脸设备 弱电设备
                    </p>
                    <div class="div-more">
                        <img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
                    </div>
                </li>
                <li class="clearfix">
                    <p class="p-title">物流管理</p>
                    <p class="p-height">
                        资产库管 物流管理 用水管理 用电管理 值班管理 绿化管理 基建管理 食堂管理
                    </p>
                    <div class="div-more">
                        <img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
                    </div>
                </li>
                <li class="clearfix">
                    <p class="p-title">餐饮管理</p>
                    <p class="p-height">
                        餐桌管理
                    </p>
                    <div class="div-more">
                        <img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
                    </div>
                </li>
                <li class="clearfix">
                    <p class="p-title">票务管理</p>
                    <p class="p-height">
                        票务管理 票务报表管理
                    </p>
                    <div class="div-more">
                        <img src="<%=basePath%>images/WFJClient/pc/5l5c/pic_01.png"/>
                    </div>
                </li>
                <a href="<%=basePath%>/page/ea/main/finance/invoicing/rukuBill.jsp?compayid=${param.companyID}&sccId=&staffid=${param.staffID}">
                    <li class="clearfix">
                        <p class="p-title">资产库存管理</p>
                        <p class="p-height">
                            采购管理 收货管理 验货管理 采购入库 调拨入库<br/>
                            归还入库 金币入库 现金入库 销售出库 调拨出库<br/>
                            领用出库 金币出库 现金出库 销售库存调整<br/>
                            库存管理 进销存明细 进销存汇总 报损管理 <br/>
                            存货核算 物品物料管理
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
    function deviceInfo(){
        document.location.href = basePath+"ea/faceDevice/ea_findFaceDeviceInfo.jspa";
    }
</script>
</body>
</html>
