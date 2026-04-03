<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
        <meta name="apple-mobile-web-app-capable" content="yes" />
        <meta name="apple-mobile-web-app-status-bar-style" content="black" />
        <meta name="format-detection" content="telephone=no, email=no" />
        <meta name="screen-orientation" content="portrait">
        <meta name="x5-orientation" content="portrait">
        <script type="text/javascript" src="<%=basePath%>js/WFJClient/setHtmlFont.js"></script>
        <script src="<%=basePath%>js/jquery.min.js"></script>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/CityList.css">
        <title>城市列表</title>
</head>
<script type="text/javascript">
    var basePath='<%=basePath%>';
</script>
<body><header>
    <menu class="clearfix">
        <li>
            <a href="javascript:history.go(-1)"><img src="<%=basePath%>/images/BuildPlatform/back_ico.png"></a>
        </li>
        <li>城市列表</li>
    </menu>
</header>
<div class="kong"></div>
<section class="search">
    <input type="text" id="search" value="" placeholder="输入城市名称或拼音缩写搜寻" />
</section>
<div style="padding-left: 20px;" class="main1"></div>
<div class="content">
    <section></section>
    <section>
        <div class="sort_letter">
            <p>
                <a href="#">定位城市</a>
            </p>
        </div>
        <menu class="clearfix">
            <ul><li id="city" class="click_li"></li></ul>
        </menu>
    </section>
    <section>
        <div class="sort_letter">
            <p>
                <a name="common">常用</a>
            </p>
        </div>
        <menu class="clearfix">
            <ul>
                <li class="click_li">北京市</li>
                <li class="click_li">成都市</li>
            </ul>
        </menu>
    </section>
    <div id="letter"></div><div class="clear"></div>
    <section>
        <div class="sort_letter">
            <p>
                <a name="hot">热门城市</a>
            </p>
        </div>
        <menu class="clearfix">
            <ul>
                <li class="click_li">北京市</li>
                <li class="click_li">上海市</li>
                <li class="click_li">广州市</li>
                <li class="click_li">深圳市</li>
                <li class="click_li">成都市</li>
                <li class="click_li">重庆市</li>
                <li class="click_li">天津市</li>
                <li class="click_li">南京市</li>
                <li class="click_li">杭州市</li>
                <li class="click_li">苏州市</li>
                <li class="click_li">武汉市</li>
                <li class="click_li">西安市</li>
            </ul>
        </menu>
    </section>
    <section class="sort_section">
        <menu class="clearfix">
            <div class="sort_list">
                <div class="num_name">上海市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">临沧市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">丽江市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">保山市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">大理白族自治州</div>
            </div>
            <div class="sort_list">
                <div class="num_name">德宏傣族景颇族自治州</div>
            </div>
            <div class="sort_list">
                <div class="num_name">怒江傈僳族自治州</div>
            </div>
            <div class="sort_list">
                <div class="num_name">文山壮族苗族自治州</div>
            </div>
            <div class="sort_list">
                <div class="num_name">昆明市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">昭通市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">普洱市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">曲靖市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">楚雄彝族自治州</div>
            </div>
            <div class="sort_list">
                <div class="num_name">玉溪市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">红河哈尼族彝族自治州</div>
            </div>
            <div class="sort_list">
                <div class="num_name">西双版纳傣族自治州</div>
            </div>
            <div class="sort_list">
                <div class="num_name">迪庆藏族自治州</div>
            </div>
            <div class="sort_list">
                <div class="num_name">乌兰察布市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">乌海市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">兴安盟</div>
            </div>
            <div class="sort_list">
                <div class="num_name">包头市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">呼伦贝尔市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">呼和浩特市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">巴彦淖尔市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">赤峰市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">通辽市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">鄂尔多斯市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">锡林郭勒盟</div>
            </div>
            <div class="sort_list">
                <div class="num_name">阿拉善盟</div>
            </div>
            <div class="sort_list">
                <div class="num_name">北京市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">吉林市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">四平市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">延边朝鲜族自治州</div>
            </div>
            <div class="sort_list">
                <div class="num_name">松原市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">白城市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">白山市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">辽源市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">通化市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">长春市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">乐山市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">内江市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">凉山彝族自治州</div>
            </div>
            <div class="sort_list">
                <div class="num_name">南充市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">宜宾市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">巴中市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">广元市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">广安市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">德阳市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">成都市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">攀枝花市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">泸州市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">甘孜藏族自治州</div>
            </div>
            <div class="sort_list">
                <div class="num_name">眉山市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">绵阳市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">自贡市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">资阳市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">达州市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">遂宁市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">阿坝藏族羌族自治州</div>
            </div>
            <div class="sort_list">
                <div class="num_name">雅安市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">天津市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">中卫市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">吴忠市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">固原市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">石嘴山市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">银川市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">亳州市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">六安市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">合肥市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">安庆市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">宣城市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">宿州市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">巢湖市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">池州市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">淮北市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">淮南市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">滁州市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">芜湖市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">蚌埠市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">铜陵市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">阜阳市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">马鞍山市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">黄山市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">东营市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">临沂市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">威海市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">德州市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">日照市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">枣庄市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">泰安市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">济南市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">济宁市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">淄博市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">滨州市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">潍坊市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">烟台市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">聊城市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">莱芜市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">菏泽市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">青岛市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">临汾市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">吕梁市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">大同市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">太原市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">忻州市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">晋中市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">晋城市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">朔州市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">运城市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">长治市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">阳泉市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">东莞市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">中山市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">云浮市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">佛山市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">广州市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">惠州市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">揭阳市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">梅州市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">汕头市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">汕尾市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">江门市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">河源市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">深圳市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">清远市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">湛江市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">潮州市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">珠海市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">肇庆市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">茂名市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">阳江市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">韶关市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">北海市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">南宁市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">崇左市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">来宾市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">柳州市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">桂林市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">梧州市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">河池市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">玉林市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">百色市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">贵港市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">贺州市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">钦州市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">防城港市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">乌鲁木齐市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">伊犁哈萨克自治州</div>
            </div>
            <div class="sort_list">
                <div class="num_name">克孜勒苏柯尔克孜自治州</div>
            </div>
            <div class="sort_list">
                <div class="num_name">克拉玛依市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">博尔塔拉蒙古自治州</div>
            </div>
            <div class="sort_list">
                <div class="num_name">吐鲁番地区</div>
            </div>
            <div class="sort_list">
                <div class="num_name">和田地区</div>
            </div>
            <div class="sort_list">
                <div class="num_name">哈密地区</div>
            </div>
            <div class="sort_list">
                <div class="num_name">喀什地区</div>
            </div>
            <div class="sort_list">
                <div class="num_name">图木舒克市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">塔城地区</div>
            </div>
            <div class="sort_list">
                <div class="num_name">巴音郭楞蒙古自治州</div>
            </div>
            <div class="sort_list">
                <div class="num_name">昌吉回族自治州</div>
            </div>
            <div class="sort_list">
                <div class="num_name">石河子市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">阿克苏地区</div>
            </div>
            <div class="sort_list">
                <div class="num_name">阿勒泰地区</div>
            </div>
            <div class="sort_list">
                <div class="num_name">阿拉尔市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">南京市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">南通市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">宿迁市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">常州市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">徐州市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">扬州市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">无锡市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">泰州市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">淮安市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">盐城市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">苏州市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">连云港市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">镇江市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">上饶市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">九江市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">南昌市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">吉安市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">宜春市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">抚州市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">新余市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">景德镇市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">萍乡市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">赣州市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">鹰潭市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">保定市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">唐山市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">廊坊市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">张家口市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">承德市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">沧州市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">石家庄市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">秦皇岛市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">衡水市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">邢台市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">邯郸市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">三门峡市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">信阳市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">南阳市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">周口市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">商丘市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">安阳市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">平顶山市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">开封市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">新乡市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">洛阳市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">济源市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">漯河市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">濮阳市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">焦作市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">许昌市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">郑州市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">驻马店市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">鹤壁市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">丽水市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">台州市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">嘉兴市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">宁波市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">杭州市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">温州市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">湖州市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">绍兴市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">舟山市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">衢州市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">金华市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">万宁市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">三亚市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">东方市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">临高县</div>
            </div>
            <div class="sort_list">
                <div class="num_name">乐东黎族自治县</div>
            </div>
            <div class="sort_list">
                <div class="num_name">五指山市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">保亭黎族苗族自治县</div>
            </div>
            <div class="sort_list">
                <div class="num_name">儋州市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">定安县</div>
            </div>
            <div class="sort_list">
                <div class="num_name">屯昌县</div>
            </div>
            <div class="sort_list">
                <div class="num_name">文昌市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">昌江黎族自治县</div>
            </div>
            <div class="sort_list">
                <div class="num_name">海口市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">澄迈县</div>
            </div>
            <div class="sort_list">
                <div class="num_name">琼中黎族苗族自治县</div>
            </div>
            <div class="sort_list">
                <div class="num_name">琼海市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">白沙黎族自治县</div>
            </div>
            <div class="sort_list">
                <div class="num_name">西沙群岛</div>
            </div>
            <div class="sort_list">
                <div class="num_name">陵水黎族自治县</div>
            </div>
            <div class="sort_list">
                <div class="num_name">仙桃市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">十堰市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">咸宁市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">天门市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">孝感市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">宜昌市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">恩施土家族苗族自治州</div>
            </div>
            <div class="sort_list">
                <div class="num_name">武汉市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">潜江市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">神农架林区</div>
            </div>
            <div class="sort_list">
                <div class="num_name">荆州市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">荆门市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">襄樊市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">鄂州市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">随州市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">黄冈市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">黄石市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">娄底市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">岳阳市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">常德市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">张家界市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">怀化市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">株洲市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">永州市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">湘潭市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">湘西土家族苗族自治州</div>
            </div>
            <div class="sort_list">
                <div class="num_name">益阳市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">衡阳市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">邵阳市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">郴州市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">长沙市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">临夏回族自治州</div>
            </div>
            <div class="sort_list">
                <div class="num_name">兰州市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">嘉峪关市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">天水市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">定西市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">平凉市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">庆阳市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">张掖市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">武威市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">甘南藏族自治州</div>
            </div>
            <div class="sort_list">
                <div class="num_name">白银市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">酒泉市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">金昌市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">陇南市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">三明市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">南平市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">厦门市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">宁德市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">泉州市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">漳州市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">福州市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">莆田市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">龙岩市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">山南地区</div>
            </div>
            <div class="sort_list">
                <div class="num_name">拉萨市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">日喀则地区</div>
            </div>
            <div class="sort_list">
                <div class="num_name">昌都地区</div>
            </div>
            <div class="sort_list">
                <div class="num_name">林芝地区</div>
            </div>
            <div class="sort_list">
                <div class="num_name">那曲地区</div>
            </div>
            <div class="sort_list">
                <div class="num_name">阿里地区</div>
            </div>
            <div class="sort_list">
                <div class="num_name">六盘水市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">安顺市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">毕节地区</div>
            </div>
            <div class="sort_list">
                <div class="num_name">贵阳市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">遵义市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">铜仁地区</div>
            </div>
            <div class="sort_list">
                <div class="num_name">黔东南苗族侗族自治州</div>
            </div>
            <div class="sort_list">
                <div class="num_name">黔南布依族苗族自治州</div>
            </div>
            <div class="sort_list">
                <div class="num_name">黔西南布依族苗族自治州</div>
            </div>
            <div class="sort_list">
                <div class="num_name">丹东市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">大连市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">抚顺市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">朝阳市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">本溪市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">沈阳市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">盘锦市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">营口市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">葫芦岛市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">辽阳市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">铁岭市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">锦州市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">阜新市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">鞍山市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">重庆市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">咸阳市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">商洛市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">安康市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">宝鸡市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">延安市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">榆林市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">汉中市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">渭南市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">西安市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">铜川市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">果洛藏族自治州</div>
            </div>
            <div class="sort_list">
                <div class="num_name">海东地区</div>
            </div>
            <div class="sort_list">
                <div class="num_name">海北藏族自治州</div>
            </div>
            <div class="sort_list">
                <div class="num_name">海南藏族自治州</div>
            </div>
            <div class="sort_list">
                <div class="num_name">海西蒙古族藏族自治州</div>
            </div>
            <div class="sort_list">
                <div class="num_name">玉树藏族自治州</div>
            </div>
            <div class="sort_list">
                <div class="num_name">西宁市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">黄南藏族自治州</div>
            </div>
            <div class="sort_list">
                <div class="num_name">七台河市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">伊春市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">佳木斯市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">双鸭山市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">哈尔滨市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">大兴安岭地区</div>
            </div>
            <div class="sort_list">
                <div class="num_name">大庆市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">牡丹江市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">绥化市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">鸡西市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">鹤岗市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">黑河市</div>
            </div>
            <div class="sort_list">
                <div class="num_name">齐齐哈尔市</div>
            </div>
        </menu>
    </section>
    <section class="section_initials" id="initials_list">
        <menu class="clearfix">
            <ul>
                <li><a href="#">定位</a></li>
                <li><a href="#common">常用</a></li>
                <li><a href="#hot">热门</a></li>
            </ul>
        </menu>
    </section>
</div>
</body>
<script type="text/javascript">
    $(document).ready(function(){
        $("header").css("height",$(window).height()*0.08-1+"px");
        $("header menu li").css("height",$(window).height()*0.08-1+"px");
        $("header menu li").css("line-height",$(window).height()*0.08-1+"px");
        $(".content section:last-of-type menu").css("top",$(window).height()*0.16-1+"px");
        $("div.kong").css("height",$(window).height()*0.08-1+"px");
        //$("div.kong").css("padding-left",".825rem");
    });
</script>
<script src="<%=basePath%>js/ea/production/cprocedure/jquery.charfirst.pinyin.js"></script>
<script src="<%=basePath%>js/bootstrap.js"></script>
<script type="text/javascript" src="<%=basePath %>js/ea/marketing/wfjeshop/citylist.js"></script>
</html>
