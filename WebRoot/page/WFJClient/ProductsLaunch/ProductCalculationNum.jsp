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
    <meta charset="UTF-8"/>
    <meta name="viewport"
          content="width=device-width, user-scalable=0, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0"/>
    <title>补货</title>

    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/WFJClient/ProductCalculationNum.css">

    <script type="text/javascript" charset="utf-8" src="<%=basePath %>js/jquery-2.0.0.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/BuildPlatform/setHtmlFont.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/comm.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath %>js/WFJClient/localforage.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath %>js/WFJClient/ProductCalculationNum.js"></script>

    <script type="text/javascript">
        var user = '${user}';
        var companyId = '${companyId}';
        var basePath = '<%=basePath%>';
        var originPage = '${param.originPage}';
    </script>
</head>
<body>
<form enctype="multipart/form-data" name="form1" id="launchForm" action="" method="post">

    <header>
        <p><a id="returnClick" onclick="goBack()">
            <img src="<%=basePath%>images/WFJClient/PersonalJoining/back_03.png" alt=""/>
        </a></p>
        <span>补货</span>
    </header>

    <!--------------------------------------------- 主内容开始 ------------------------------------------------->
    <div class="main">
        <div class="main_hidden">
            <input type="submit" id="submit" style="display:none;"/>
            <input type="hidden" id="companyId" name="companyId"/>
            <input type="hidden" id="user" name="user"/>
            <input type="hidden" id="ppId" name="productPackaging.ppID"/>
            <input type="hidden" id="stanpro" name="productPackaging.stanpro">
            <input type="hidden" id="goodsName" name="productPackaging.goodsName">
            <input type="hidden" id="singleWeight" name="productPackaging.singleWeight">
            <input type="hidden" id="quantity" name="productPackaging.invNum"/>
            <div class="main_hide" style="overflow: auto;">
                <%--入库仓库--%>
                <div class="main_inp2">
                    <div class="main_inp_left">入库仓库</div>
                    <div class="main_inp_right">
                        <p class="depotManage">depotManage</p>
                    </div>
                </div>
                <%--初始重量--%>
                <div class="main_inp2">
                    <div class="main_inp_left">初始重量</div>
                    <div class="main_inp_right">
                        <p class="initialWeight">0</p>
                        <p>KG</p>
                    </div>
                </div>
                <%--时时重量--%>
                <div class="main_inp2">
                    <div class="main_inp_left">实时重量</div>
                    <div class="main_inp_right">
                        <p class="changeWeight weight">0</p>
                        <p>KG</p>
                    </div>
                </div>
                <%--名称--%>
                <div class="main_inp2">
                    <div class="main_inp_left">产品名称</div>
                    <div class="main_inp_right">
                        <p class="goodsName"></p>
                    </div>
                </div>
                <%--产品条码--%>
                <div class="main_inp2">
                    <div class="main_inp_left">产品条码</div>
                    <div class="main_inp_right">
                        <p class="barCode"></p>
                    </div>
                </div>
                <%--是否标品--%>
                <div class="main_inp2">
                    <div class="main_inp_left">是否标品</div>
                    <div class="main_inp_right">
                        <p class="stanpro"></p>
                    </div>
                </div>
                <%--计量单位--%>
                <%--<div class="main_inp2">
                    <div class="main_inp_left">计量单位</div>
                    <div class="main_inp_right">
                        <p class="variable">variable</p>
                    </div>
                </div>--%>
                <%--单重--%>
                <div class="main_inp2 sw-div" style="display: none;">
                    <div class="main_inp_left">标重</div>
                    <div class="main_inp_right">
                        <p class="singleWeight">12</p>
                        <p>KG</p>
                        <div class="main_inp_right_unit isoksw">
                            确认标重
                        </div>
                    </div>
                </div>
                <%--现有库存--%>
                <div class="main_inp2">
                    <div class="main_inp_left">现有库存</div>
                    <div class="main_inp_right">
                        <p class="invNum">0</p>
                        <p class="variable"></p>
                    </div>
                </div>
                <%--入库量--%>
                <div class="main_inp2">
                    <div class="main_inp_left">入库量</div>
                    <%--<div class="main_inp_right">
&lt;%&ndash;                        <p class="quantity">0</p>&ndash;%&gt;
                        <input type="number" class="quantity" value="0" max="10" min="0" >
                        <p class="variable"></p>
                    </div>--%>
                    <div class="main_inp_right">
                        <div class="num">
                            <img src="<%=basePath%>images/WFJClient/add-number.png" class="add-number upn" alt=""/>
                            <%--<img src="<%=basePath%>images/WFJClient/pc/login/land_07.png" class="reduce-number"/>--%>
                            <span class="span-number quantity">0</span>
                            <%--<img src="<%=basePath%>images/WFJClient/pc/login/land_07.png" class="add-number"/>--%>
                            <img src="<%=basePath%>images/WFJClient/reduce-number.png" class="reduce-number upn" alt=""/>
                        </div>
                        <p class="variable"></p>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="annotation">
        <div>1、名词解释</div>
        <div>（1）标品：标品即相同的产品（固定重量、规格相同，包装相同等）</div>
        <div>（2）非标品：非标品即同一种类不同规格的产品（水果、蔬菜，按斤计价的产品）</div>
        <div>（3）初始重量：初始重量即货柜开启时，显示的重量</div>
        <div>（4）实时重量：实时重量即上货完毕后，显示的重量</div>
        <div>2、入库</div>
        <div>（1）标品入库数量计算方法：</div>
        <div>数量=（实时重量-初始重量）/单重，显示屏返回“件”数，查看是否与上货数量一致，不一致时，手动修改。</div>
        <div>（计量单位：个）</div>
        <div>（2）非标品入库：</div>
        <div>重量 = 实时重量 - 初始重量</div>
        <div> （计量单位：KG）</div>
        <div>3、秤盘产品</div>
        <div>（1）同一个秤盘只能放置一个种类的非标品（水果蔬菜等只能放置一种，比如只能放苹果）</div>
        <div>（2）同一个秤盘可以放置多个种类标品（农夫山泉、果粒橙、红酒等）</div>
        <div>（3）同一个秤盘上放置的标品，重量不能互为倍数</div>
        <div>4、秤盘量程</div>
        <div>单个秤盘量程为40KG，单个秤盘放置产品的重量不能超过20KG</div>
    </div>

    <footer>
        <%--<div class="footer_half">归零</div>
        <div class="footer_half">去皮</div>--%>
        <div class="footer_entire bh" id="bh">确认补货</div>
        <div class="footer_entire isok" id="isok">确定</div>
    </footer>
    <!--------------------------------------------- 主内容结束 ------------------------------------------------->
    <div id="prompt" style="width: 100%; display: none;z-index: 1001">
        <center>
            <div>
                <span style="position: relative; top: 19.8%;"></span>
            </div>
        </center>
    </div>
</form>
</body>
</html>