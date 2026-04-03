<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/scMobile/qyrz/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/scMobile/qyrz/cate.css"/>

    <script src="<%=basePath%>js/font-size.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/jquery-1.11.3.js" type="text/javascript"></script>
    <script src="<%=basePath%>js/title.js" type="text/javascript" charset="utf-8"></script>


    <title>分类</title>
    <style>
        html,
        body,
        #container {
            width: 100%;
            height: 100%;
        }
    </style>

    <script type="text/javascript">
        var basePath = "<%=basePath%>";
        var dwLnglatX = "${param.dwLnglatX}";
        var dwLnglatY = "${param.dwLnglatY}";
        var tj = "${param.tj}";
   $(function(){
       $("#catepage li,#catepage section p").click(function () {
           if(tj=="1"){
              if($(this).parent().prop("tagName")=="UL"){
                  document.location.href = basePath + "ea/qyrz/ea_getCompleteBus.jspa?gdcate=" + $.trim($(this).text())+"&gdcate2="+$.trim($(this).text());
              }else{
                  document.location.href = basePath + "ea/qyrz/ea_getCompleteBus.jspa?gdcate=" + $.trim($(this).text());

              }



           }else {
               document.location.href = basePath + "/ea/qyrz/ea_toPeriphery.jspa?cate=" + $.trim($(this).text()) + "&dwLnglatX=" + dwLnglatX + "&dwLnglatY=" + dwLnglatY;
           }
       })


   });
    </script>
</head>
<body>
<%--<c:if test="${param.head eq 'show'}">--%>
<%--<header class="head">--%>
    <%--<ul  class="clearfix">--%>
        <%--<li onclick="javascript: window.history.go(-1);return false;">--%>
            <%--<img src="<%=basePath%>images/scMobile/qyrz/img-1.png" >--%>
        <%--</li>--%>
        <%--<li>--%>
            <%--周边分类--%>
        <%--</li>--%>
    <%--</ul>--%>
<%--</header>--%>
<%--</c:if>--%>
<div class="content" id="catepage">
    <section>
        <div class="clearfix">
            <p>
                <img src="<%=basePath%>images/scMobile/qyrz/cate03.png"/>
            </p>
            <p>
                餐饮服务
            </p>
        </div>
        <ul class="clearfix">


            <li>	中餐厅	</li>
            <li>	外国餐厅	</li>
            <li>	快餐厅	</li>
            <li>	综合酒楼	</li>

            <li>	四川菜(川菜)	</li>
            <li>	广东菜(粤菜)	</li>
            <li>	山东菜(鲁菜)	</li>
            <li>	江苏菜	</li>
            <li>	浙江菜	</li>
            <li>	上海菜	</li>
            <li>	湖南菜(湘菜)	</li>
            <li>	安徽菜(徽菜)	</li>
            <li>	福建菜	</li>
            <li>	北京菜	</li>
            <li>	湖北菜(鄂菜)	</li>
            <li>	东北菜	</li>
            <li>	云贵菜	</li>
            <li>	西北菜	</li>
            <li>	老字号	</li>
            <li>	火锅店	</li>
            <li>	海鲜酒楼	</li>
            <li>	中式素菜馆	</li>
            <li>	清真菜馆	</li>
            <li>	台湾菜	</li>
            <li>	潮州菜	</li>
            <li>	外国餐厅	</li>
            <li>	日本料理	</li>
            <li>	韩国料理	</li>
            <li>	法式菜品餐厅	</li>
            <li>	意式菜品餐厅	</li>
            <li>	美式风味	</li>
            <li>	印度风味	</li>
            <li>	牛扒店(扒房)	</li>
            <li>	俄国菜	</li>
            <li>	葡国菜	</li>
            <li>	德国菜	</li>
            <li>	巴西菜	</li>
            <li>	墨西哥菜	</li>
            <li>	肯德基	</li>
            <li>	麦当劳	</li>
            <li>	必胜客	</li>
            <li>	永和豆浆	</li>
            <li>	茶餐厅	</li>
            <li>	大家乐	</li>
            <li>	大快活	</li>
            <li>	美心	</li>
            <li>	吉野家	</li>
            <li>	仙跡岩	</li>
            <li>	呷哺呷哺	</li>
            <li>	休闲餐饮场所	</li>
            <li>	咖啡厅	</li>
            <li>	星巴克咖啡	</li>
            <li>	上岛咖啡	</li>
            <li>	巴黎咖啡店	</li>
            <li>	茶艺馆	</li>
            <li>	冷饮店	</li>
            <li>	糕饼店	</li>
            <li>	甜品店	</li>

        </ul>
    </section>
    <section>
        <div class="clearfix">
            <p>
                <img src="<%=basePath%>images/scMobile/qyrz/cate06.png"/>
            </p>
            <p>
                体育休闲服务
            </p>
        </div>
        <ul class="clearfix">
            <li>	运动场所	</li>
            <li>	综合体育馆	</li>
            <li>	保龄球馆	</li>
            <li>	网球场	</li>
            <li>	篮球场馆	</li>
            <li>	足球场	</li>
            <li>	滑雪场	</li>
            <li>	溜冰场	</li>
            <li>	户外健身场所	</li>
            <li>	海滨浴场	</li>
            <li>	游泳馆	</li>
            <li>	健身中心	</li>
            <li>	乒乓球馆	</li>
            <li>	台球厅	</li>
            <li>	壁球场	</li>
            <li>	马术俱乐部	</li>
            <li>	赛马场	</li>
            <li>	橄榄球场	</li>
            <li>	羽毛球场	</li>
            <li>	跆拳道场馆	</li>
            <li>	高尔夫相关	</li>
            <li>	高尔夫球场	</li>
            <li>	高尔夫练习场	</li>
            <li>	娱乐场所	</li>
            <li>	夜总会	</li>
            <li>	KTV	</li>
            <li>	迪厅	</li>
            <li>	酒吧	</li>
            <li>	游戏厅	</li>
            <li>	棋牌室	</li>
            <li>	博彩中心	</li>
            <li>	网吧	</li>
            <li>	度假疗养场所	</li>
            <li>	度假村	</li>
            <li>	疗养院	</li>
            <li>	休闲场所	</li>
            <li>	游乐场	</li>
            <li>	垂钓园	</li>
            <li>	采摘园	</li>
            <li>	露营地	</li>
            <li>	水上活动中心	</li>
            <li>	影剧院相关	</li>
            <li>	电影院	</li>
            <li>	音乐厅	</li>
            <li>	剧场	</li>

        </ul>
    </section>
    <section>

        <div class="clearfix">
            <p>
                <img src="<%=basePath%>images/scMobile/qyrz/cate01.png"/>
            </p>
            <p>
                汽车服务
            </p>
        </div>


        <ul class="clearfix">
            <li>
                加油站



            </li>
            <li>

                其它能源站


            </li>
            <li>
                加气站


            </li>
            <li>
                汽车养护/装饰


            </li>
            <li>
                汽车养护/装饰


            </li>
            <li>
                洗车场

            </li>
            <li>
                汽车俱乐部
            </li>
            <li>
                汽车救援
            </li>
            <li>
                汽车配件销售
            </li>
            <li>
                汽车租赁

            </li>
            <li>
                汽车租赁

            </li>
            <li>
                二手车交易
            </li>
            <li>
                充电站
            </li>
            <li>
                汽车销售


            </li>

        </ul>
    </section>
    <section>
        <div class="clearfix">
            <p>
                <img src="<%=basePath%>images/scMobile/qyrz/cate22.png"/>
            </p>
            <p>
                汽车销售
            </p>
        </div>
        <ul class="clearfix">
            <li>
                大众特约销售



            </li>
            <li>
                本田特约销售


            </li>
            <li>
                奥迪特约销售


            </li>
            <li>
                通用特约销售


            </li>
            <li>
                宝马特约销售


            </li>
            <li>
                日产特约销售


            </li>


            <li>

                丰田特约销售


            </li>


            <li>

                三菱特约销售



            </li>
            <li>
                菲亚特约销售




            </li>

            <li>
                现代特约销售



            </li>
            <li>


                起亚特约销售


            </li>
            <li>
                福特特约销售




            </li>
            <li>
                捷豹特约销售


            </li>
            <li>
                路虎特约销售



            </li>

            <li>
                东风特约销售



            </li>
            <li>
                吉利特约销售



            </li>
            <li>

                奇瑞特约销售


            </li>


            <li>
                名爵销售



            </li>

            <li>
                红旗销售



            </li>
            <li>
                长安汽车销售



            </li>
            <li>
                海马汽车销售



            </li>
            <li>
                北京汽车销售



            </li>
            <li>
                长城汽车销售



            </li>

            <li>


                纳智捷销售

            </li>

            <li>
                货车销售


            </li>
            <li>
                东风货车销售



            </li>
            <li>
                中国重汽销售

            </li>
            <li>
                一汽解放销售

            </li>
            <li>
                福田卡车销售


            </li>
            <li>
                陕西重汽销售



            </li>
            <li>
                北奔重汽销售


            </li>
            <li>
                江淮货车销售



            </li>
            <li>

                华菱星马销售


            </li>



        </ul>
    </section>
    <section>
        <div class="clearfix">
            <p>
                <img src="<%=basePath%>images/scMobile/qyrz/cate21.png"/>
            </p>
            <p>
                汽车维修
            </p>
        </div>
        <ul class="clearfix">
            <li>
                汽车维修

            </li>
            <li>
                汽车综合维修


            </li>
            <li>
                大众特约维修

            </li>
            <li>

                本田特约维修

            </li>
            <li>

                奥迪特约维修

            </li>
            <li>

                通用特约维修


            </li>
            <li>
                宝马特约维修

            </li>
            <li>


                日产特约维修

            </li>
            <li>

                奥迪特约维修

            </li>
            <li>

                日产特约维修

            </li>
            <li>


                雷诺特约维修



            </li>

            <li>

                丰田特约维修


            </li>
            <li>


                斯巴鲁特约维修



            </li>

            <li>


                三菱特约维修



            </li>


            <li>

                现代特约维修
            </li>
            <li>

                起亚特约维修
            </li>
            <li>

                福特特约维修




            </li>
            <li>

                捷豹特约维修

            </li>
            <li>

                路虎特约维修


            </li>

            <li>

                东风特约维修




            </li>
            <li>

                吉利特约维修






            </li>
            <li>


                奇瑞特约维修





            </li>

            <li>
                荣威维修

            </li>
            <li>


                名爵维修


            </li>
            <li>

                江淮维修




            </li>
            <li>


                红旗维修







            </li>


            <li>


                北京汽车维修

            </li>
            <li>


                长城汽车维修

            </li>
            <li>

                纳智捷维修

            </li>
            <li>


                广汽传祺维修

            </li>
            <li>

                货车维修

            </li>


            <li>

            东风货车维修

        </li>
            <li>


                中国重汽维修


            </li>
            <li>


                一汽解放维修


            </li>
            <li>


                福田卡车维修


            </li>
            <li>


                陕西重汽维修


            </li>
            <li>


                北奔重汽维修


            </li>
            <li>


                江淮货车维修


            </li>
            <li>


                华菱星马维修

            </li>


            <li>


                德国曼恩维修


            </li>


            <li>



                观致维修

            </li>
        </ul>
    </section>
    <section>
        <div class="clearfix">
            <p>
                <img src="<%=basePath%>images/scMobile/qyrz/cate02.png"/>
            </p>
            <p>
                摩托车服务
            </p>
        </div>
        <ul class="clearfix">

            <li style="width:30%;">
                摩托车服务相关


            </li>
            <li>
                摩托车销售

            </li>
            <li>
                摩托车维修

            </li>

        </ul>
    </section>

    <section>
        <div class="clearfix">
            <p>
                <img src="<%=basePath%>images/scMobile/qyrz/gouwu.png"/>
            </p>
            <p>
                购物服务
            </p>
        </div>
        <ul class="clearfix">
            <li>	购物相关场所	</li>
            <li>	商场	</li>
            <li>	购物中心	</li>
            <li>	普通商场	</li>
            <li>	免税品店	</li>
            <li style="width:28%;">	便民商店/便利店	</li>
            <li style="width:28%;">	7-ELEVEn便利店	</li>
            <li>	家电电子卖场	</li>
            <li>	综合家电商场	</li>
            <li>	国美	</li>
            <li>	大中	</li>
            <li>	苏宁	</li>
            <li>	手机销售	</li>
            <li>	数码电子	</li>
            <li>	丰泽	</li>
            <li>	苏宁镭射	</li>
            <li>	超市	</li>
            <li>	家乐福	</li>
            <li>	沃尔玛	</li>
            <li>	华润	</li>
            <li>	北京华联	</li>
            <li>	上海华联	</li>
            <li>	麦德龙	</li>
            <li>	乐天玛特	</li>
            <li>	华堂	</li>
            <li>	卜蜂莲花	</li>
            <li>	屈臣氏	</li>
            <li>	惠康超市	</li>
            <li>	百佳超市	</li>
            <li>	万宁超市	</li>
            <li>	花鸟鱼虫市场	</li>
            <li>	花卉市场	</li>
            <li>	宠物市场	</li>
            <li>	家居建材市场	</li>
            <li style="width:30%;">	家具建材综合市场	</li>
            <li>	家具城	</li>
            <li>	建材五金市场	</li>
            <li>	厨卫市场	</li>
            <li>	布艺市场	</li>
            <li>	灯具瓷器市场	</li>
            <li>	综合市场	</li>
            <li>	小商品市场	</li>
            <li>	旧货市场	</li>
            <li>	农副产品市场	</li>
            <li>	果品市场	</li>
            <li>	蔬菜市场	</li>
            <li>	水产海鲜市场	</li>
            <li>	文化用品店	</li>
            <li>	体育用品店	</li>
            <li>	李宁专卖店	</li>
            <li>	耐克专卖店	</li>
            <li>	彪马专卖店	</li>
            <li>	高尔夫用品店	</li>
            <li>	户外用品	</li>
            <li>	特色商业街	</li>
            <li>	步行街	</li>
            <li style="width:28%;">	服装鞋帽皮具店	</li>
            <li>	品牌服装店	</li>
            <li>	品牌鞋店	</li>
            <li>	品牌皮具店	</li>
            <li>	品牌箱包店	</li>
            <li>	专营店	</li>
            <li>	古玩字画店	</li>
            <li style="width:28%;">	珠宝首饰工艺品	</li>
            <li>	钟表店	</li>
            <li>	眼镜店	</li>
            <li>	书店	</li>
            <li>	音像店	</li>
            <li>	儿童用品店	</li>
            <li>	自行车专卖店	</li>
            <li>	礼品饰品店	</li>
            <li>	烟酒专卖店	</li>
            <li>	宠物用品店	</li>
            <li>	摄影器材店	</li>
            <li>	宝马生活方式	</li>
            <li>	土特产专卖店	</li>
            <li>	特殊买卖场所	</li>
            <li>	拍卖行	</li>
            <li>	典当行	</li>


        </ul>
    </section>
    <section>
        <div class="clearfix">
            <p>
                <img src="<%=basePath%>images/scMobile/qyrz/cate05.png"/>
            </p>
            <p>
                生活服务
            </p>
        </div>
        <ul class="clearfix">
            <li>	生活服务场所	</li>
            <li>	旅行社	</li>
            <li>	信息咨询中心	</li>
            <li>	服务中心	</li>
            <li>	旅馆问讯	</li>
            <li>	售票处	</li>
            <li>	飞机票代售点	</li>
            <li>	火车票代售点	</li>
            <li>	船票代售点	</li>
            <li>	邮局	</li>
            <li>	邮政速递	</li>
            <li>	物流速递	</li>
            <li>	电讯营业厅	</li>
            <li style="width:28%;">	中国电信营业厅	</li>
            <li style="width:28%;">	中国移动营业厅	</li>
            <li style="width:28%;">	中国联通营业厅	</li>
            <li style="width:28%;">	中国铁通营业厅	</li>
            <li>	和记电讯	</li>
            <li>	数码通电讯	</li>
            <li>	电讯盈科	</li>
            <li>	中国移动香港	</li>
            <li>	事务所	</li>
            <li>	律师事务所	</li>
            <li>	会计师事务所	</li>
            <li>	评估事务所	</li>
            <li>	审计事务所	</li>
            <li>	认证事务所	</li>
            <li>	专利事务所	</li>
            <li>	人才市场	</li>
            <li>	自来水营业厅	</li>
            <li>	电力营业厅	</li>
            <li>	美容美发店	</li>
            <li>	维修站点	</li>
            <li>	摄影冲印	</li>
            <li>	洗浴推拿场所	</li>
            <li>	洗衣店	</li>
            <li>	中介机构	</li>
            <li>	搬家公司	</li>
            <li>	马会投注站	</li>
            <li>	丧葬设施	</li>
            <li>	陵园	</li>
            <li>	公墓	</li>
            <li>	殡仪馆	</li>
            <li>	婴儿服务场所	</li>
            <li>	婴儿游泳馆	</li>

        </ul>
    </section>

    <section>
        <div class="clearfix">
            <p>
                <img src="<%=basePath%>images/scMobile/qyrz/cate04.png"/>
            </p>
            <p>
              医疗保健服务
            </p>
        </div>
        <ul class="clearfix">
            <li>	综合医院	</li>
            <li>	三级甲等医院	</li>
            <li>	卫生院	</li>
            <li>	专科医院	</li>
            <li>	整形美容	</li>
            <li>	口腔医院	</li>
            <li>	眼科医院	</li>
            <li>	耳鼻喉医院	</li>
            <li>	胸科医院	</li>
            <li>	骨科医院	</li>
            <li>	肿瘤医院	</li>
            <li>	脑科医院	</li>
            <li>	妇科医院	</li>
            <li>	精神病医院	</li>
            <li>	传染病医院	</li>
            <li>	诊所	</li>
            <li>	急救中心	</li>
            <li>	疾病预防	</li>
            <li>	医药保健相关	</li>
            <li>	药房	</li>
            <li>	医疗保健用品	</li>
            <li>	动物医疗场所	</li>
            <li>	宠物诊所	</li>
            <li>	兽医站	</li>

        </ul>
    </section>
    <section>
        <div class="clearfix">
            <p>
                <img src="<%=basePath%>images/scMobile/qyrz/cate07.png"/>
            </p>
            <p>
                住宿服务
            </p>
        </div>
        <ul class="clearfix">
            <li>	住宿服务相关	</li>
            <li>	宾馆酒店	</li>
            <li>	奢华酒店	</li>
            <li>	五星级宾馆	</li>
            <li>	四星级宾馆	</li>
            <li>	三星级宾馆	</li>
            <li>	经济型连锁酒店	</li>
            <li>	旅馆招待所	</li>
            <li>	青年旅舍	</li>

        </ul>
    </section>
    <section>
        <div class="clearfix">
            <p>
                <img src="<%=basePath%>images/scMobile/qyrz/cate08.png"/>
            </p>
            <p>
               风景名胜

            </p>
        </div>
        <ul class="clearfix">
            <li>	旅游景点	</li>
            <li>	公园广场	</li>
            <li>	公园	</li>
            <li>	动物园	</li>
            <li>	植物园	</li>
            <li>	水族馆	</li>
            <li>	城市广场	</li>
            <li>	公园内部设施	</li>
            <li>	风景名胜	</li>
            <li>	世界遗产	</li>
            <li>	国家级景点	</li>
            <li>	省级景点	</li>
            <li>	纪念馆	</li>
            <li>	寺庙道观	</li>
            <li>	教堂	</li>
            <li>	回教寺	</li>
            <li>	海滩	</li>
            <li>	观景点	</li>

        </ul>
    </section>
    <section>
        <div class="clearfix">
            <p>
                <img src="<%=basePath%>images/scMobile/qyrz/cate09.png"/>
            </p>
            <p>
                商务住宅
            </p>
        </div>
        <ul class="clearfix">
            <li>	商务住宅相关	</li>
            <li>	产业园区	</li>
            <li>	楼宇相关	</li>
            <li>	商务写字楼	</li>
            <li style="width:30%">	工业大厦建筑物	</li>
            <li>	商住两用楼宇	</li>
            <li>	住宅区	</li>
            <li>	别墅	</li>
            <li>	住宅小区	</li>
            <li>	宿舍	</li>
            <li>	社区中心	</li>

        </ul>
    </section>
    <section>
        <div class="clearfix">
            <p>
                <img src="<%=basePath%>images/scMobile/qyrz/cate12.png"/>
            </p>
            <p>
                政府机构及社会团体
            </p>
        </div>
        <ul class="clearfix">
            <li>	政府机关相关	</li>
            <li>	外地政府办	</li>
            <li>	外国机构相关	</li>
            <li>	外国使领馆	</li>
            <li  style="width:30%">	国际组织办事处	</li>
            <li>	民主党派	</li>
            <li>	社会团体相关	</li>
            <li>	共青团	</li>
            <li>	少先队	</li>
            <li>	妇联	</li>
            <li>	残联	</li>
            <li>	红十字会	</li>
            <li>	消费者协会	</li>
            <li>	行业协会	</li>
            <li>	慈善机构	</li>
            <li>	教会	</li>
            <li>	公检法机关	</li>
            <li>	公安警察	</li>
            <li>	检察院	</li>
            <li>	法院	</li>
            <li>	消防机关	</li>
            <li>	公证鉴定机构	</li>
            <li>	社会治安机构	</li>
            <li  style="width:40%">	交通车辆管理相关	</li>
            <li>	交通管理机构	</li>
            <li>	车辆管理机构	</li>
            <li>	验车场	</li>
            <li>	交通执法站	</li>
            <li style="width:40%" >	车辆通行证办理处	</li>
            <li style="width:30%">	货车相关检查站	</li>
            <li>	工商税务机构	</li>
            <li>	工商部门	</li>
            <li>	国税机关	</li>
            <li>	地税机关	</li>

            <li style="width:40%">	国家级机关及事业单位	</li>
            <li  style="width:50%">	省直辖市级政府及事业单位	</li>
            <li  style="width:40%">	地市级政府及事业单位	</li>
            <li  style="width:40%">	区县级政府及事业单位	</li>
            <li  style="width:40%">	乡镇级政府及事业单位	</li>
            <li  style="width:50%">	乡镇以下级政府及事业单位	</li>

        </ul>
    </section>
    <section>
        <div class="clearfix">
            <p>
                <img src="<%=basePath%>images/scMobile/qyrz/cate10.png"/>
            </p>
            <p>
                科教文化服务
            </p>
        </div>
        <ul class="clearfix">
            <li>	科教文化场所	</li>
            <li>	博物馆	</li>
            <li>	奥迪博物馆	</li>
            <li  style="width:50%">	梅赛德斯-奔驰博物馆	</li>
            <li>	展览馆	</li>
            <li>	室内展位	</li>
            <li>	会展中心	</li>
            <li>	美术馆	</li>
            <li>	图书馆	</li>
            <li>	科技馆	</li>
            <li>	天文馆	</li>
            <li>	文化宫	</li>
            <li>	档案馆	</li>
            <li>	文艺团体	</li>
            <li>	传媒机构	</li>
            <li>	电视台	</li>
            <li>	电台	</li>
            <li>	报社	</li>
            <li>	杂志社	</li>
            <li>	出版社	</li>
            <li>	学校	</li>
            <li>	高等院校	</li>
            <li>	中学	</li>
            <li>	小学	</li>
            <li>	幼儿园	</li>
            <li>	成人教育	</li>
            <li>	职业技术学校	</li>
            <li>	学校内部设施	</li>
            <li>	科研机构	</li>
            <li>	培训机构	</li>
            <li>	驾校	</li>

        </ul>
    </section>
    <section>
        <div class="clearfix">
            <p>
                <img src="<%=basePath%>images/scMobile/qyrz/cate11.png"/>
            </p>
            <p>
                交通设施服务
            </p>
        </div>
        <ul class="clearfix">
            <li>	交通服务相关	</li>
            <li>	机场相关	</li>
            <li>	候机室	</li>
            <li>	摆渡车站	</li>
            <li>	飞机场	</li>
            <li>	机场出发/到达	</li>
            <li>	直升机场	</li>
            <li>	机场货运处	</li>
            <li>	火车站	</li>
            <li>	候车室	</li>
            <li>	进站口/检票口	</li>
            <li>	出站口	</li>
            <li>	站台	</li>
            <li>	售票	</li>
            <li>	退票	</li>
            <li>	改签	</li>
            <li>	公安制证	</li>
            <li>	票务相关	</li>
            <li>	货运火车站	</li>
            <li>	港口码头	</li>
            <li>	客运港	</li>
            <li>	车渡口	</li>
            <li>	人渡口	</li>
            <li>	货运港口码头	</li>
            <li>	长途汽车站	</li>
            <li>	地铁站	</li>
            <li>	出入口	</li>
            <li>	轻轨站	</li>
            <li>	公交车站相关	</li>
            <li>	旅游专线车站	</li>
            <li>	普通公交站	</li>
            <li>	机场巴士	</li>
            <li>	班车站	</li>
            <li>	停车场相关	</li>
            <li>	换乘停车场	</li>
            <li>	公共停车场	</li>
            <li>	专用停车场	</li>
            <li>	路边停车场	</li>
            <li>	停车场入口	</li>
            <li>	停车场出口	</li>
            <li>	停车场出入口	</li>
            <li>	过境口岸	</li>
            <li>	出租车	</li>
            <li>	轮渡站	</li>
            <li>	索道站	</li>

        </ul>
    </section>
    <section>
        <div class="clearfix">
            <p>
                <img src="<%=basePath%>images/scMobile/qyrz/cate14.png"/>
            </p>
            <p>
                金融保险服务
            </p>
        </div>
        <ul class="clearfix">
            <li>	金融保险机构	</li>
            <li>	银行	</li>
            <li>	中国人民银行	</li>
            <li>	国家开发银行	</li>
            <li style="width:30%;">	中国进出口银行	</li>
            <li>	中国银行	</li>
            <li>	中国工商银行	</li>
            <li style="width:30%;">	中国建设银行	</li>
            <li>	中国农业银行	</li>
            <li>	交通银行	</li>
            <li>	招商银行	</li>
            <li>	华夏银行	</li>
            <li>	中信银行	</li>
            <li>	中国民生银行	</li>
            <li>	中国光大银行	</li>
            <li>	上海银行	</li>
            <li style="width:30%;">	上海浦东发展银行	</li>
            <li>	平安银行	</li>
            <li>	兴业银行	</li>
            <li>	北京银行	</li>
            <li>	广发银行	</li>
            <li>	农村商业银行	</li>
            <li>	香港恒生银行	</li>
            <li>	东亚银行	</li>
            <li>	花旗银行	</li>
            <li>	渣打银行	</li>
            <li>	汇丰银行	</li>


            <li style="width:30%">	中国邮政储蓄银行	</li>
            <li>	香港星展银行	</li>
            <li>	南洋商业银行	</li>
            <li>	上海商业银行	</li>
            <li>	永亨银行	</li>
            <li>	香港永隆银行	</li>
            <li>	创兴银行	</li>
            <li>	大新银行	</li>
            <li style="width:30%">	中信银行(国际)	</li>
            <li>	北京农商银行	</li>
            <li>	上海农商银行	</li>
            <li>	广州农商银行	</li>
            <li style="width:30%">	深圳农村商业银行	</li>
            <li>	银行相关	</li>
            <li>	自动提款机	</li>
            <li>	中国银行ATM	</li>
            <li style="width:40%">	中国工商银行ATM	</li>
            <li style="width:35%">	中国建设银行ATM	</li>
            <li style="width:35%">	中国农业银行ATM	</li>
            <li style="width:35%">	交通银行ATM	</li>
            <li style="width:35%" >招商银行ATM	</li>
            <li style="width:35%">	华夏银行ATM	</li>
            <li style="width:35%">	中信银行ATM	</li>
            <li style="width:35%">	中国民生银行ATM	</li>
            <li style="width:35%">	中国光大银行ATM	</li>
            <li>	上海银行ATM	</li>
            <li style="width:40%">	上海浦东发展银行ATM	</li>
            <li>	平安银行ATM	</li>
            <li>	兴业银行ATM	</li>
            <li>	北京银行ATM	</li>
            <li>	广发银行ATM	</li>
            <li style="width:35%">	农村商业银行ATM	</li>


            <li>	花旗银行ATM	</li>
            <li>	渣打银行ATM	</li>
            <li>	汇丰银行ATM	</li>



            <li style="width:40%">	中国邮政储蓄银行ATM	</li>
            <li style="width:35%">	上海商业银行ATM	</li>
            <li style="width:35%">	北京农商银行ATM	</li>
            <li style="width:35%">	上海农商银行ATM	</li>
            <li style="width:35%">	广州农商银行ATM	</li>
            <li>	保险公司	</li>
            <li style="width:35%">	中国人民保险公司	</li>
            <li style="width:35%">	中国人寿保险公司	</li>
            <li style="width:35%">	中国平安保险公司	</li>
            <li style="width:35%">	中国再保险公司	</li>
            <li style="width:35%">	中国太平洋保险	</li>
            <li style="width:35%">	新华人寿保险公司	</li>
            <li style="width:35%">	泰康人寿保险公司	</li>
            <li>	证券公司	</li>
            <li>	证券营业厅	</li>
            <li>	财务公司	</li>

        </ul>
    </section>
    <section>
        <div class="clearfix">
            <p>
                <img src="<%=basePath%>images/scMobile/qyrz/cate13.png"/>
            </p>
            <p>
                公司企业
            </p>
        </div>
        <ul class="clearfix">
            <li>	公司企业	</li>
            <li>	知名企业	</li>
            <li>	公司	</li>
            <li>	广告装饰	</li>
            <li>	建筑公司	</li>
            <li>	医药公司	</li>
            <li>	机械电子	</li>
            <li>	冶金化工	</li>
            <li>	网络科技	</li>
            <li>	商业贸易	</li>
            <li>	电信公司	</li>
            <li>	矿产公司	</li>
            <li>	工厂	</li>
            <li style="width:30%;">	其它农林牧渔基地	</li>
            <li>	渔场	</li>
            <li>	农场	</li>
            <li>	林场	</li>
            <li>	牧场	</li>
            <li>	家禽养殖基地	</li>
            <li>	蔬菜基地	</li>
            <li>	水果基地	</li>
            <li>	花卉苗圃基地	</li>

        </ul>
    </section>
    <section>
        <div class="clearfix">
            <p>
                <img src="<%=basePath%>images/scMobile/qyrz/cate15.png"/>
            </p>
            <p>
                道路附属设施
            </p>
        </div>
        <ul class="clearfix">
            <li>	道路附属设施	</li>
            <li>	警示信息	</li>
            <li>	摄像头	</li>
            <li>	测速设施	</li>
            <li>	铁路道口	</li>
            <li>	违章停车	</li>
            <li>	收费站	</li>
            <li>	高速收费站	</li>
            <li>	国省道收费站	</li>
            <li>	桥洞收费站	</li>
            <li>	高速服务区	</li>
            <li>	高速加油站服务区	</li>
            <li>	高速停车区	</li>
            <li>	红绿灯	</li>
            <li>	路牌信息	</li>

        </ul>
    </section>
    <section>
        <div class="clearfix">
            <p>
                <img src="<%=basePath%>images/scMobile/qyrz/cate17.png"/>
            </p>
            <p>
                地名地址信息
            </p>
        </div>
        <ul class="clearfix">
            <li>	地名地址信息	</li>
            <li>	普通地名	</li>
            <li>	国家名	</li>
            <li>	省级地名	</li>
            <li>	直辖市级地名	</li>
            <li>	地市级地名	</li>
            <li>	区县级地名	</li>
            <li>	乡镇级地名	</li>
            <li>	街道级地名	</li>
            <li>	村庄级地名	</li>
            <li>	村组级地名	</li>
            <li>	自然地名	</li>
            <li>	海湾海峡	</li>
            <li>	岛屿	</li>
            <li>	山	</li>
            <li>	河流	</li>
            <li>	湖泊	</li>
            <li>	交通地名	</li>
            <li>	道路名	</li>
            <li>	路口名	</li>
            <li>	环岛名	</li>
            <li>	高速路出口	</li>
            <li>	高速路入口	</li>
            <li>	立交桥	</li>
            <li>	桥	</li>
            <li style="width:30%;">	城市快速路出口	</li>
            <li style="width:30%;">	城市快速路入口	</li>
            <li>	隧道	</li>
            <li>	铁路	</li>
            <li>	门牌信息	</li>
            <li>	地名门牌	</li>
            <li>	道路门牌	</li>
            <li>	楼栋号	</li>
            <li>	城市中心	</li>
            <li>	标志性建筑物	</li>
            <li>	热点地名	</li>

        </ul>
    </section>
    <section>
        <div class="clearfix">
            <p>
                <img src="<%=basePath%>images/scMobile/qyrz/cate16.png"/>
            </p>
            <p>
                公共设施
            </p>
        </div>
        <ul class="clearfix">
            <li>	公共设施	</li>
            <li>	报刊亭	</li>
            <li>	公用电话	</li>
            <li>	公共厕所	</li>
            <li>	男洗手间	</li>
            <li>	女洗手间	</li>
            <li>	残障洗手间/无障碍洗手间	</li>
            <li>	婴儿换洗间/哺乳室/母婴室	</li>
            <li>	紧急避难场所	</li>

        </ul>
    </section>
    <section>
        <div class="clearfix">
            <p>
                <img src="<%=basePath%>images/scMobile/qyrz/cate18.png"/>
            </p>
            <p>
                事件活动

            </p>
        </div>
        <ul class="clearfix">
            <li>	公众活动	</li>
            <li>	节日庆典	</li>
            <li>	展会展览	</li>
            <li>	体育赛事	</li>
            <li>	文艺演出	</li>
            <li>	大型会议	</li>
            <li>	运营活动	</li>
            <li>	商场活动	</li>
            <li>	突发事件	</li>
            <li>	自然灾害	</li>
            <li>	事故灾难	</li>
            <li>	城市新闻	</li>
            <li>	公共卫生事件	</li>
            <li>	公共社会事件	</li>


        </ul>
    </section>
    <section>
        <div class="clearfix">
            <p>
                <img src="<%=basePath%>images/scMobile/qyrz/cate19.png"/>
            </p>
            <p>
                室内设施

            </p>
        </div>
        <ul class="clearfix">
            <li>	室内设施
            </li>


        </ul>
    </section>
    <section>
        <div class="clearfix">
            <p>
                <img src="<%=basePath%>images/scMobile/qyrz/cate20.png"/>
            </p>
            <p>
                通行设施

            </p>
        </div>
        <ul class="clearfix">
            <li>	通行设施	</li>
            <li>	建筑物门	</li>
            <li>	建筑物正门	</li>
            <li>	临街院门	</li>
            <li>	临街院正门	</li>
            <li>	虚拟门	</li>


        </ul>
    </section>
</div>

</body>
</html>
