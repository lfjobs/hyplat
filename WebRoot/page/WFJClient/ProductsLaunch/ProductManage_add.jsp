<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>
<head lang="en">
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/WFJClient/pm_base.css"/>
    <link rel="stylesheet" href="<%=basePath %>css/WFJClient/ProductManage_add.css">
    <script type="text/javascript" charset="utf-8" src="<%=basePath %>js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" charset="utf-8"
            src="<%=basePath %>page/ueditor/third-party/webuploader/webuploader.js"></script>
    <script type="text/javascript" charset="utf-8"
            src="<%=basePath %>page/ueditor/third-party/webuploader/diyUpload.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath %>js/font-size.js"></script>
    <script type="text/javascript" charset="utf-8"
            src="<%=basePath%>js/ea/marketing/wfjeshop/ProductManage_add.js"></script>
    <title>新版产品添加</title>
    <style>
        .jijia {
            display: none;
        }

        #prompt div {
            width: 80%;
            background: rgba(0, 0, 0, 0.5);
        }
    </style>
    <script type="text/javascript">
        var basePath = "<%=basePath%>";
        var companyID = "${param.companyID}";
        var staffID = "${param.staffID}";
        var ppid = "${param.ppId}";
        var colorvalue = '';
    </script>
</head>
<form enctype="multipart/form-data" name="form1" id="launchForm" action="" method="post">
    <!-- 添加物品 -->
    <div class="div-tianjia">
        <div class="tianjia-header">
            <ul class="clearfix">
                <li>
                    <a onclick="window.history.go(-1);return false;" target="_self">
                        <img src="<%=basePath%>images/scMobile/payBudget/budgetList/ico-left.png" alt="">
                    </a>
                </li>
                <li>
                    编辑产品
                </li>
                <li id="isupdate" style="display: none;">
                    修改
                </li>
            </ul>
        </div>

        <div class="tianjia-content">
            <input type="hidden" id="companyID" name="companyID" value="${param.companyID}"/>
            <input type="hidden" id="goodsKey" name="productPackaging.ppID" value="${prolist[0][1] }"/>
            <input type="hidden" id="goodsID" name="goodsManage.goodsID" value="${prolist[0][0] }"/>
            <input type="hidden" id="sizevalue" name="sizevalue" value="${sizevalue }"/>
            <input type="hidden" id="colorvalue" name="colorvalue" value="${colorvalue }"/>
            <input type="hidden" id="attrinames" name="attrinames" value="${attrinames}">
            <input type="hidden" id="attrinamec" name="attrinamec" value="${attrinamec}">
            <input type="hidden" id="attri" name="attri" value="${attri}">
            <input type="hidden" id="ImagesPath" name="ImagesPath" value="${attrinamec}">
            <input type="hidden" id="VideoPath" name="VideoPath" value="${attrinamec}">
            <input type="hidden" id="isScale" name="goodsManage.isScale" value="${prolist[0][7]}">
            <input type="hidden" id="unitOfMeasureCode" name="unitOfMeasureCode" value="${prolist[0][9] }"/>
            <ul class="clearfix">
                <li class="li-01 chengzhong1 clearfix">
                    <p>云计称重</p>
                    <img src="<%=basePath %>images/WFJClient/pc/5l5c/pic_01.png" alt="">
                </li>
                <li class="li-02 chengzhong2 clearfix">
                    <p>云计称重</p>
                    <input type="text" value="" readonly name="" id="jjinput">
                </li>
                <li class="li-01 jijia1 clearfix jijia">
                    <p>计价单位</p>
                    <img src="<%=basePath %>images/WFJClient/pc/5l5c/pic_01.png" alt="">
                </li>
                <li class="li-02 jijia2 clearfix jijia">
                    <p>计价单位</p>
                    <input type="text" value="${prolist[0][11] }" readonly class="">
                </li>
                <li class="li-01 tiaoma1 clearfix">
                    <p>物品条码</p>
                    <div>
                        <p>
                            扫一扫
                        </p>
                        <img src="img/img_02.png" alt="">
                    </div>
                </li>
                <li class="li-02 tiaoma2 clearfix">
                    <p>物品条码</p>
                    <input type="text" value="${prolist[0][12] }" name="goodsManage.barCode" id="barCode">
                </li>
                <li class="li-01 mingcheng1 clearfix">
                    <p>物品名称</p>
                    <img src="<%=basePath %>images/WFJClient/pc/5l5c/pic_01.png" alt="">
                </li>
                <li class="li-02 mingcheng2 clearfix">
                    <p>物品名称</p>
                    <input type="text" value="${prolist[0][2] }" name="goodsManage.goodsName" id="ppname" readonly style="color: #e53e3e;font-weight: bolder;">
                </li>
                <li class="li-01 xiangmu1 clearfix">
                    <p>项目分类</p>
                    <img src="<%=basePath %>images/WFJClient/pc/5l5c/pic_01.png" alt="">
                </li>
                <li class="li-02 xiangmu2 clearfix">
                    <p>项目分类</p>
                    <input type="text" value="${prolist[0][11] }" readonly name="productPackaging.producttype"
                           id="producttype">
                    <input type="hidden" value="${prolist[0][12] }" name="productPackaging.productCode"
                           id="productCode">
                </li>
                <li class="li-01 hangye1 clearfix">
                    <p>行业分类</p>
                    <img src="<%=basePath %>images/WFJClient/pc/5l5c/pic_01.png" alt="">
                </li>
                <li class="li-02 hangye2 clearfix">
                    <p>行业分类</p>
                    <input type="hidden" value="${prolist[0][5] }" name="goodsManage.tradeName" id="tradeName"/>
                    <input type="hidden" value="${prolist[0][3] }" name="goodsManage.tradeID" id="tradeID"/>
                    <input type="text" value="${prolist[0][4] }" readonly id="tradeCode" name="goodsManage.tradeCode"/>
                </li>
                <li class="li-01 wupin1 clearfix">
                    <p>物品类别</p>
                    <img src="<%=basePath %>images/WFJClient/pc/5l5c/pic_01.png" alt="">
                </li>
                <li class="li-02 wupin2 clearfix">
                    <p>物品类别</p>
                    <input type="text" value="${prolist[0][13] }" readonly name="goodsManage.typeID" id="typeID"/>
                </li>
                <li class="li-01 tupian1 clearfix">
                    <p>首页图片</p>
                    <img src="<%=basePath %>images/WFJClient/pc/5l5c/pic_01.png" alt="">
                </li>
                <li class="li-02 tupian2 clearfix">
                    <p>首页图片</p>
                    <div></div>
                </li>
                <li class="li-01 shipin1 clearfix">
                    <p>首页视频</p>
                    <img src="<%=basePath %>images/WFJClient/pc/5l5c/pic_01.png" alt="">
                </li>
                <li class="li-02 shipin2 clearfix">
                    <p>首页视频</p>
                    <div></div>
                </li>
                <li class="li-01 danwei1 clearfix">
                    <p>单位</p>
                    <img src="<%=basePath %>images/WFJClient/pc/5l5c/pic_01.png" alt="">
                </li>
                <li class="li-02 danwei2 clearfix">
                    <p>单位</p>
                    <input type="text" value="${prolist[0][14] }" readonly name="goodsManage.variableID"
                           id="variableID">
                </li>
                <li class="li-01 guige1 clearfix">
                    <p>规格</p>
                    <img src="<%=basePath %>images/WFJClient/pc/5l5c/pic_01.png" alt="">
                </li>
                <li class="li-02 guige2 clearfix">
                    <p>规格</p>
                    <div>
                        <img src="img/img_03.png" alt="">
                        <p>已编辑</p>
                    </div>
                </li>
                <li class="li-01 pinpai1 clearfix">
                    <p>品牌管理</p>
                    <img src="<%=basePath %>images/WFJClient/pc/5l5c/pic_01.png" alt="">
                </li>
                <li class="li-02 pinpai2 clearfix">
                    <p>品牌管理</p>
                    <input type="text" value="${prolist[0][8]}" readonly name="goodsManage.brand" id="brand"/>
                </li>
                <li class="li-01 dengji1 clearfix">
                    <p>等级管理</p>
                    <img src="<%=basePath %>images/WFJClient/pc/5l5c/pic_01.png" alt="">
                </li>
                <li class="li-02 dengji2 clearfix">
                    <p>等级管理</p>
                    <input type="text" readonly value="${prolist[0][17] }" name="productPackaging.gradeName"
                           id="gradeName">
                    <input type="hidden" value="${prolist[0][16] }" name="productPackaging.gradeid" id="gradeid">
                </li>
                <li class="li-01 detailed1 clearfix">
                    <p>宝贝详情</p>
                    <img src="<%=basePath %>images/WFJClient/pc/5l5c/pic_01.png" alt="">
                </li>
                <li class="li-02 detailed2 clearfix">
                    <p>宝贝详情</p>
                    <div>
                        <img src="img/img_03.png" alt="">
                        <p>已编辑</p>
                    </div>
                </li>
            </ul>
        </div>
        <div class="div-tijiao" onclick="toLaunchOrcang()" style="display: none;">
            提交
        </div>
    </div>
    <!-- 云计称重 -->
    <div class="div-chengzhong">
        <div class="div-box">
            <div class="div-top clearfix">
                <p>
                    云计称重
                </p>
                <div class="div-close">
                    <img src="img/img_05.png" alt="">
                </div>
            </div>
            <div class="div-middle clearfix">
                <p class="active">是</p>
                <p>否</p>
            </div>
            <div class="div-bottom">
                <p>
                    确定
                </p>
            </div>
        </div>
    </div>
    <!-- 计价单位 -->
    <div class="div-jijia">
        <div class="div-box">
            <div class="div-top clearfix">
                <p>
                    计价单位
                </p>
                <div class="div-close">
                    <img src="img/img_05.png" alt="">
                </div>
            </div>
            <div class="div-middle clearfix">
                <p class="active">KGM(以重计价)</p>
                <p>PCS(以数计价)</p>
            </div>
            <div class="div-bottom">
                <p>
                    确定
                </p>
            </div>
        </div>
    </div>
    <!-- 物品条码 -->
    <div class="div-tiaoma">
        <div class="div-box">
            <div class="tiaoma-header">
                <ul class="clearfix">
                    <li class="div-close">
                        <img src="img/img_01.png" alt="">
                    </li>
                    <li>
                        <!-- 物品条码 -->
                    </li>
                    <li>
                        <!-- <img src="img/img_02.png" alt=""> -->
                    </li>
                </ul>
            </div>
            <div class="div-con">
                <img src="img/img_06.png" alt="">
            </div>
        </div>
    </div>
    <!-- 物品名称 -->
    <div class="div-mingcheng">
        <div class="div-box">
            <div class="mingcheng-header">
                <ul class="clearfix">
                    <li class="div-close">
                        <img src="<%=basePath%>images/scMobile/payBudget/budgetList/ico-left.png" alt="">
                    </li>
                    <li>
                        物品名称
                    </li>
                    <li class="keep">
                        保存
                    </li>
                </ul>
            </div>
            <div class="div-con">
                <div>
                    <input type="text" value="" placeholder="请输入物品名称" id="input-mingcheng">
                </div>
            </div>
        </div>
    </div>
    <!-- 项目分类 -->
    <div class="div-xiangmu">
        <div class="div-box">
            <div class="xiangmu-header">
                <ul class="clearfix">
                    <li class="div-close">
                        <img src="<%=basePath%>images/scMobile/payBudget/budgetList/ico-left.png" alt="">
                    </li>
                    <li>
                        项目分类
                    </li>
                    <li class="keep">
                        <!-- 保存 -->
                    </li>
                </ul>
            </div>
            <div class="div-con clearfix xmfl">
                <%--<ul class="ul-left">
                    <li>
                        产成品
                    </li>
                    <li class="active">
                        半成品
                    </li>
                    <li>
                        零部件
                    </li>
                    <li>
                        原材料
                    </li>
                    <li>
                        辅料
                    </li>
                    <li>
                        费用类
                    </li>
                    <li>
                        固定资产
                    </li>
                    <li>
                        项目管理
                    </li>
                    <li>
                        其他
                    </li>
                    <li>
                        公司网站
                    </li>
                </ul>
                <div class="div-right">
                    <ul class="clearfix ul-02">
                        <li>科一</li>
                        <li>科一</li>
                        <li>科一</li>
                    </ul>
                    <ul class="clearfix ul-03">
                        <li>科二</li>
                        <li>科二</li>
                        <li>科二</li>
                    </ul>
                    <ul class="clearfix ul-04">
                        <li>科三</li>
                        <li>科三</li>
                        <li>科三</li>
                    </ul>
                </div>--%>
            </div>
        </div>
    </div>
    <!-- 行业分类 -->
    <div class="div-hangye">
        <div class="div-box">
            <div class="hangye-header">
                <ul class="clearfix">
                    <li class="div-close">
                        <img src="<%=basePath%>images/scMobile/payBudget/budgetList/ico-left.png" alt="">
                    </li>
                    <li>
                        行业分类
                    </li>
                    <li class="keep">
                        <!-- 保存 -->
                    </li>
                </ul>
            </div>
            <div class="div-con clearfix hyfl">
                <%--<ul class="ul-left">
                    <li>
                        A101机械工业加工
                    </li>
                    <li class="active">
                        B102汽车交通工具
                    </li>
                    <li>
                        C103软件网络计算机
                    </li>
                    <li>
                        D104军事船舶制造
                    </li>
                    <li>
                        E105航空航天科技
                    </li>
                    <li>
                        B102汽车交通工具
                    </li>
                    <li>
                        C103软件网络计算机
                    </li>
                    <li>
                        D104军事船舶制造
                    </li>
                    <li>
                        E105航空航天科技
                    </li>
                </ul>
                <div class="div-right">
                    <ul class="clearfix">
                        <li>汽车驾校</li>
                        <li>汽车美容</li>
                        <li>汽车加油</li>
                        <li>汽车维修</li>
                        <li>汽车保险</li>
                    </ul>
                </div>--%>
            </div>
        </div>
    </div>
    <!-- 物品类别 -->
    <div class="div-wupin">
        <div class="div-box">
            <div class="wupin-header">
                <ul class="clearfix">
                    <li class="div-close">
                        <img src="<%=basePath%>images/scMobile/payBudget/budgetList/ico-left.png" alt="">
                    </li>
                    <li>
                        物品类别
                    </li>
                    <li class="keep">
                        <!-- 保存 -->
                    </li>
                </ul>
            </div>
            <div class="div-con clearfix wplb">
                <%--<ul class="ul-left">
                    <li>
                        产成品
                    </li>
                    <li class="active">
                        半成品
                    </li>
                    <li>
                        零部件
                    </li>
                    <li>
                        原材料
                    </li>
                    <li>
                        辅料
                    </li>
                    <li>
                        费用类
                    </li>
                    <li>
                        固定资产
                    </li>
                    <li>
                        项目管理
                    </li>
                    <li>
                        其他
                    </li>
                    <li>
                        公司网站
                    </li>
                </ul>
                <div class="div-right">
                    <ul class="clearfix">
                        <li>科一</li>
                        <li>科二</li>
                        <li>科三</li>
                    </ul>
                </div>--%>
            </div>
        </div>
    </div>
    <!-- 首页图片 -->
    <div class="div-tupian2" style="opacity: 0; transform: translate(1000000px);">
        <div class="div-box">
            <ul class="clearfix">
                <li class="div-close">
                    <img src="<%=basePath%>images/scMobile/payBudget/budgetList/ico-left.png" alt="">
                </li>
                <li>
                    首页图片
                </li>
                <li class="p-tijiao">
                    保存
                </li>
            </ul>
        </div>
        <div class="div-con">
            <div class="div-tab">
                <div class="div-01">
                    <div class="demo">
                        <div id="as"></div>
                        <c:if test="${fn:length(arrilist)>0 }">
                            <span style="display:none;" id="arrilist">${fn:length(arrilist) }</span>
                            <div class="parentFileBox" style="width: 360px;">
                                <ul class="fileBoxUl">
                                    <c:forEach items="${arrilist }" var="entity" varStatus="status">
                                        <li id="fileBox_WU_FILE_0_${status.index }" class="">
                                            <div class="viewThumb">
                                                <img src="<%=basePath %>${entity.imgurl}">
                                            </div>
                                            <div class="diyCancel"></div>
                                            <div class="diySuccess" style="display: block;"></div>
                                            <div class="diyFileName">${entity.imgurl.substring(entity.imgurl.lastIndexOf("/")+1, entity.imgurl.length())}</div>
                                            <div class="diyFilePath" style="display: none;">
                                                    ${entity.imgurl}
                                            </div>
                                            <div class="diyBar" style="display: none;">
                                                <div class="diyProgress" style="width: 100%;"></div>
                                                <div class="diyProgressText">上传完成</div>
                                            </div>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </div>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- 首页视频 -->
    <div class="div-shipin2" style="opacity: 0; transform: translate(1000000px);">
        <div class="div-box">
            <ul class="clearfix">
                <li class="div-close">
                    <img src="<%=basePath%>images/scMobile/payBudget/budgetList/ico-left.png" alt="">
                </li>
                <li>
                    首页视频
                </li>
                <li class="p-tijiao">
                    保存
                </li>
            </ul>
        </div>
        <div class="div-con">
            <div class="div-tab">
                <div class="div-01">
                    <div class="demo">
                        <div id="ass"></div>
                        <c:if test="${fn:length(arrvlist)>0 }">
                            <span style="display:none;" id="arrvlist">${fn:length(arrvlist) }</span>
                            <div class="parentFileBox" style="width: 360px;">
                                <ul class="fileBoxUl">
                                    <c:forEach items="${arrvlist }" var="entity" varStatus="status">
                                        <li id="fileBox_WU_FILE_0_${status.index }" class="">
                                            <div class="viewThumb">
                                                <img src="<%=basePath %>${entity.imgurl}">
                                            </div>
                                            <div class="diyCancel"></div>
                                            <div class="diySuccess" style="display: block;"></div>
                                            <div class="diyFileName">${entity.imgurl.substring(entity.imgurl.lastIndexOf("/")+1, entity.imgurl.length())}</div>
                                            <div class="diyFilePath" style="display: none;">
                                                    ${entity.imgurl}
                                            </div>
                                            <div class="diyBar" style="display: none;">
                                                <div class="diyProgress" style="width: 100%;"></div>
                                                <div class="diyProgressText">上传完成</div>
                                            </div>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </div>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- 品牌管理 -->
    <div class="div-pinpai">
        <div class="div-box">
            <div class="pinpai-header">
                <ul class="clearfix">
                    <li class="div-close">
                        <img src="<%=basePath%>images/scMobile/payBudget/budgetList/ico-left.png" alt="">
                    </li>
                    <li>
                        品牌管理
                    </li>
                    <li class="keep">
                        <!-- 保存 -->
                    </li>
                </ul>
            </div>
            <div class="div-con">
                <p>
                    <label for="">品牌名称</label>
                    <input type="text" value="${prolist[0][8] }" class="pinpaival"
                           placeholder="请输入品牌名称">
                </p>
                <p>
                    <label for="">品牌logo</label>
                    <input type="text" placeholder="上传品牌logo">
                </p>
                <div id="div-img3">
                    <input type="file" name="fileLogo" id="sdfFile3" onchange="f_change3(this);"
                           accept="image/png,image/jpeg,image/gif,image/jpg,image/bmp,image/webp">
                    <img alt="" src="<%=basePath%>${prolist[0][15] }" id="imgSdf3">
                </div>
            </div>
            <div class="div-bottom">
                <p>
                    提交
                </p>
            </div>
        </div>
    </div>
    <!-- 单位 -->
    <div class="div-danwei">
        <div class="div-box">
            <div class="div-top clearfix">
                <p>
                    单位
                </p>
                <div class="div-close">
                    <img src="img/img_05.png" alt="">
                </div>
            </div>
            <div class="div-middle clearfix dwgl">
                <%--<p class="active">地槽</p>
                <p>平方</p>
                <p>盒</p>
                <p>米</p>
                <p>张</p>
                <p>方</p>
                <p>圈</p>
                <p>个</p>
                <p>台</p>
                <p>升</p>
                <p>瓶</p>
                <p>袋</p>
                <p>克</p>
                <p>辆</p>
                <p>颗</p>
                <p>斤</p>
                <p>千克</p>
                <p>棵</p>
                <p>件</p>--%>
            </div>
            <div class="div-bottom">
                <p>
                    提交
                </p>
            </div>
        </div>
    </div>
    <!-- 规格 -->
    <div class="div-guige div-guige1">
        <div class="div-box">
            <div class="guige-header">
                <ul class="clearfix">
                    <li class="div-close">
                        <img src="<%=basePath%>images/scMobile/payBudget/budgetList/ico-left.png" alt="">
                    </li>
                    <li>
                        产品规格
                    </li>
                    <li class="keep">
                        保存
                    </li>
                </ul>
            </div>
            <section class="color">
                <input type="text" class="sezi_title" value="颜色">
                <div class="inpAdd clearfix"></div>
                <p class="add-input">自定义</p>
                <ul class="clearfix size_old">
                    <li>
                        白色
                    </li>
                    <li>
                        粉色
                    </li>
                    <li>
                        米黄色
                    </li>
                    <li>
                        蓝色
                    </li>
                    <li>
                        紫色
                    </li>
                    <li>
                        黑色
                    </li>
                </ul>
            </section>
            <section class="size">
                <input type="text" class="sezi_title" value="尺码">
                <div class="inpAdd clearfix"></div>
                <p class="add-input">自定义</p>
                <ul class="clearfix size_old">
                    <li>
                        S
                    </li>
                    <li>
                        M
                    </li>
                    <li>
                        L
                    </li>
                    <li>
                        XL
                    </li>
                    <li>
                        XXL
                    </li>
                    <li>
                        XXXL
                    </li>
                </ul>
            </section>
        </div>
    </div>
    <div class="div-guige div-guige2">
        <div class="div-box">
            <div class="guige-header">
                <ul class="clearfix">
                    <li class="div-close">
                        <img src="img/img_01.png" alt="">
                    </li>
                    <li>
                        规格
                    </li>
                    <li class="keep">
                        <!-- 保存 -->
                    </li>
                </ul>
            </div>
            <ul class="ul-con">
                <li class="clearfix">
                    <p>长</p>
                    <input type="number" class="attri1">
                    <input type="hidden" class="attri2" value="长-">
                    <p>cm</p>
                </li>
                <li class="clearfix">
                    <p>宽</p>
                    <input type="number" class="attri1">
                    <input type="hidden" class="attri2" value="宽-">

                    <p>cm</p>
                </li>
                <li class="clearfix">
                    <p>高</p>
                    <input type="number" class="attri1">
                    <input type="hidden" class="attri2" value="高-">
                    <p>cm</p>
                </li>
                <li class="clearfix">
                    <p>重量</p>
                    <input type="number" class="attri1">
                    <input type="hidden" class="attri2" value="重量-">
                    <p>kg</p>
                </li>
                <li class="clearfix">
                    <p>数量</p>
                    <input type="number" class="attri1">
                    <input type="hidden" class="attri2" value="数量-">
                    <p>pcs</p>
                </li>
            </ul>
            <p class="p-bottom">
                提交
            </p>
        </div>
    </div>
    <!-- 等级管理 -->
    <div class="div-dengji">
        <div class="div-box">
            <div class="div-top clearfix">
                <p>
                    等级名称
                </p>
            </div>
            <div class="div-con djgl">
                <%--<ul>
                    <li>一级</li>
                    <li>二级</li>
                    <li>三级</li>
                    <li>四级</li>
                    <li>五级</li>
                </ul>--%>
            </div>
        </div>
    </div>
    <!-- 产品详情 -->
    <div class="product_describe" style="display: none;">
        <input type="hidden" name="htl" id="editcontent"/>
        <div class="header">
            <ul class="clearfix">
                <li class="div-close">
                    <img src="<%=basePath%>images/scMobile/payBudget/budgetList/ico-left.png" alt="">
                </li>
                <li>
                    产品描述
                </li>
                <li class="keep">
                    <!-- 保存 -->
                </li>
            </ul>
        </div>
        <article>
            <div class="editable" id="edit">
                <span style="display:none;" id="functionList">${fn:length(functionList) }</span>
                <c:if test="${fn:length(functionList)>0 }">
                    <c:forEach items="${functionList }" var="entity" varStatus="status">
                        <div class="content" id="content${status.index }">${entity }</div>
                    </c:forEach>
                </c:if>
                <c:if test="${fn:length(functionList)<=0 }">
                    <div class="content" id="content0">
                        <div contenteditable="true" class="editablesmall">
                            <p class="moren">此处添加文字描述</p>
                        </div>
                    </div>
                </c:if>
            </div>
        </article>
        <ul class="footer">
            <li class="foot_g"><img src="http://www.impf2010.com:80/images/WFJClient/PersonalJoining/foot_add.png">
                添加图片
                <!-- <input name="photo" id="doc" multiple style="width:100%; height:100%; position:absolute; top:0; left:0; opacity:0;" onChange="javascript:setImagePreviews();" accept="image/*" type="file"> -->
            </li>
            <li class="foot_r">
                <button type="button"></button>
                完成
            </li>
        </ul>
    </div>
</form>
<div id="prompt" style="width: 100%; display: none;z-index: 1001">
    <center>
        <div>
            <span style="position: relative; top: 19.8%;"></span>
        </div>
    </center>
</div>
</body>
</html>