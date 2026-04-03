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
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <title>数字地球简介</title>
    <link type="text/css" rel="stylesheet" href="<%=basePath %>css/WFJClient/myapp/briefIntrodution.css">
    <script type="text/javascript" src="<%=basePath %>js/jquery-1.9.1.min.js"></script>


</head>
<body>
<header>
   <ul>
       <li style="width: 10%;">
        <a onclick="javascript: window.history.go(-1);return false;" target="_self" >
          <img src="<%=basePath %>images/WFJClient/wfjh/left_jt.png"></a>
       </li>
       <li style="width: 80%;">中联园区简介</li>
       <li style="width: 10%;"></li>
       <div class="clearfix"></div>
   </ul>
</header>
<div class="content_hidden">
    <div class="content intro">
        <img src="<%=basePath %>images/WFJClient/wfjh/bg.png" alt="" width="100%" id="header">
        <div class="txt_con">
            <div class="txt">
                <h3 class="title">什么是数字地球</h3>
                <i></i>

                <img src="<%=basePath %>images/WFJClient/wfjh/txt_bg1.png" alt="" class="bg">
                <div class="txt_">
                    <p>
                        大国崛起，工业革命、公司化运作的诞生、互联网经济，总是颠覆性发展，生死挑战、浪潮般前进。物质地球供给人类自然物质生存，新地数字地球联营平台思想建立在5L5C中联园区微分金系统平台即五层五清管理思想、管理标准、管理系统，数字地球商城，招商招标系统等循环生态系统， 同时通过物联网技术形成一个庞大的大数据具有相当于物质地球基因的定律平台， 从而诞生出新的微分金数字地球联营平台，必将带来新的颠覆性发展，铸造新的倍增财富空间机遇。
                    </p>
                    <p>
                        新财富空间机遇还要从物质地球形成说起， 150亿年前诞生宇宙时奠定了地球产生的物质基础，46亿年前形成了地球 ，250万年前诞生了有人类生存的地球。经过由简单到复杂的地球运动、 变化和相互联系、相互影响，构成了具有形貌变迁、人类活动的物质地球。1968年诞生并在此后发展形成的全球互联网，以及伴随互联网运用的大数据，开启了一次重大的时代转型。互联网大数据不仅带来全人类生活、工作、管理、思维大变革，带来全社会组织机构、商业市场、政府与公民关系的新融合，而且直接影响到全人类赖以生存的地球及其环境。物质地球给人类赋予了生存发展的基本物质基础，数字地球则颠覆了千百年来人类的思维惯例，创造了全新的价值源泉。物质地球具有自然形成的万有引力，而数字地球则具有无限循环的无形转换力和吸引力。数字地球带来数据的资源化与云计算的深度结合，带来科学理论的突破数据及科学数据联盟的成立， 带来数据管理核心竞争力， 带来数据质量成为BI(商业智能)成功的关键数据，带来生态系统复合化程度加强，促进了社会的发展。
                    </p>
                    <p>
                        新的数字地球思想发出新的彩虹，我们中华儿传承五千年中华民族文化之精华，历经战争，自然灾害，文化，经济的洗礼，也尽经历了兴衰败的历程教训，超越互联网时代的步伐，数字地球联营平台。
                    </p>
                    <p>
                        特别传承是具有举世非凡的汉高祖刘邦缔造的汉文化，汉魂，塑造一个字”生”生生不息，永争第一的民族精神。
                    </p>
                    <p>
                        再此我们的党经历几十年的艰苦努力，适应社会发展趋势方针政策，使中国逐步发展成为世界强国，特别是习主席倡导忠于人民终于党，塑造一个‘忠’字，形成党魂，党的灵魂。
                    </p>
                    <p>
                        在新的变革时代，经济成为当今的主旋律，发展经济，不得忘记我们邓小平改革开放，腾飞我们的经济，但我们更要借于公元前2世纪与1世纪至于16世纪及保留下使用丝绸之路，丝绸经济，今天习主席提出一带一路，建设新的丝绸之路经济带。同时社会的发展进步，始终都有相连的新思想展现。刘太平先生6年前就开始投资数千万元，互联网科技领域研究， 结合人生丰厚的阅历、经历， 企业经营实践，方知人类社会需求，企业管理职业人和各界人士需求，同时依据物质地球，数字地球思想， 2010年提出并实现五层五清管理思想，五层五清管理标准，5L5C管理系统大数据。2016年建立招商招标联营平台，大国崛起，工业革命、公司化运作的诞生、互联网经济，总是颠覆性发展，生死挑战、浪潮般前进。物质地球供给人类自然物质生存，新地数字地球联营平台思想建立在5L5C中联园区微分金系统平台即五层五清管理思想、管理标准、管理系统，数字地球商城，招商招标系统等循环生态系统， 同时通过物联网技术形成一个庞大的大数据具有相当于物质地球基因的定律平台， 从而诞生出新的微分金数字地球联营平台，必将带来新的颠覆性发展，铸造新的倍增财富空间机遇。
                    </p>
                    <!--<p>
                        新财富空间机遇还要从物质地球形成说起， 150亿年前诞生宇宙时奠定了地球产生的物质基础，46亿年前形成了地球 ，250万年前诞生了有人类生存的地球。经过由简单到复杂的地球运动、 变化和相互联系、相互影响，构成了具有形貌变迁、人类活动的物质地球。1968年诞生并在此后发展形成的全球互联网，以及伴随互联网运用的大数据，开启了一次重大的时代转型。互联网大数据不仅带来全人类生活、工作、管理、思维大变革，带来全社会组织机构、商业市场、政府与公民关系的新融合，而且直接影响到全人类赖以生存的地球及其环境。物质地球给人类赋予了生存发展的基本物质基础，数字地球则颠覆了千百年来人类的思维惯例，创造了全新的价值源泉。物质地球具有自然形成的万有引力，而数字地球则具有无限循环的无形转换力和吸引力。数字地球带来数据的资源化与云计算的深度结合，带来科学理论的突破数据及科学数据联盟的成立， 带来数据管理核心竞争力， 带来数据质量成为BI(商业智能)成功的关键数据，带来生态系统复合化程度加强，促进了社会的发展。
                    </p>
                    <p>
                        新的数字地球思想发出新的彩虹，我们中华儿传承五千年中华民族文化之精华，历经战争，自然灾害，文化，经济的洗礼，也尽经历了兴衰败的历程教训，超越互联网时代的步伐，数字地球联营平台。
                    </p>-->
                    <p>
                        特别传承是具有举世非凡的汉高祖刘邦缔造的汉文化，汉魂，塑造一个字”生”生生不息，永争第一的民族精神。
                    </p>
                    <p>
                        再此我们的党经历几十年的艰苦努力，适应社会发展趋势方针政策，使中国逐步发展成为世界强国，特别是习主席倡导忠于人民终于党，塑造一个‘忠’字，形成党魂，党的灵魂。
                    </p>
                    <p>
                        在新的变革时代，经济成为当今的主旋律，发展经济，不得忘记我们邓小平改革开放，腾飞我们的经济，但我们更要借于公元前2世纪与1世纪至于16世纪及保留下使用丝绸之路，丝绸经济，今天习主席提出一带一路，建设新的丝绸之路经济带。同时社会的发展进步，始终都有相连的新思想展现。刘太平先生6年前就开始投资数千万元，互联网科技领域研究， 结合人生丰厚的阅历、经历， 企业经营实践，方知人类社会需求，企业管理职业人和各界人士需求，同时依据物质地球，数字地球思想， 2010年提出并实现五层五清管理思想，五层五清管理标准，5L5C管理系统大数据。2016年建立招商招标联营平台，
                    </p>
                </div>
            </div>
        </div>
        <div class="txt_con">
            <div class="txt txt2">
                <h3 class="title">什么是5L5C</h3>
                <i></i>

                <img src="<%=basePath %>images/WFJClient/wfjh/txt_bg2.png" alt="" class="bg">
                <div class="txt_">
                    <ul>
                    <h4>5L5C就是五层五清管理思想的意思，也是管理新标准、纵向分五层：</h4>
                        <li>策划层包括<span>（股东会、董事会、监事会、工会、顾问会）；</span></li>
                        <li>决策层包括<span>（董事长室）；</span></li>
                        <li>执行层包括<span>（总裁室、总经理室）；</span></li>
                        <li>功能层包括<span>（人事部、办公室、财务室、生产部、营销部）；</span></li>
                        <li>创收层包括<span>（创收事业部）</span></li>
                    <h4>横向分五清包括：<span>人事清、办公室清、财务清、生产清、营销清</span></h4>
                    </ul>
                </div>
            </div>
        </div>
        <div class="txt_con">
            <div class="txt txt3">
                <h3 class="title">5L5C管理平台功能特点</h3>
                <i></i>

                <img src="<%=basePath %>images/WFJClient/wfjh/txt_bg3.png" alt="" class="bg">
                <div class="txt_">
                    <p>
                        阴阳平衡，满足职业、企业、政府、社会管理功能，具有全面性，标准化程度高，易操作性，能操作性强，根据行业特点，既有标准，又有专业的特点，满足多方需求，提现了简单实用，易操作的特点，同时也具有试用范围广，经济实惠，投资少，收益大，全能、快捷拯救个人资源、职业资源、企业资源、园区资源及省市县镇资源，加上互联网，实现从互联网经济，向更高的平台经济过度，真正让你的价值倍增。
                    </p>
                </div>
            </div>
        </div>


    </div>
</div>

<a href="#;" class="return2"><img src="<%=basePath %>images/WFJClient/wfjh/return.png" alt=""></a>

<script>
    $(document).ready(function(){
        $("header").attr("style","height:"+$(window).height()*0.08+"px;line-height:"+$(window).height()*0.08+"px;position:fixed;");
        $(".content_hidden").attr("style",";overflow: auto;position: relative;"+"top:"+$(window).height()*0.08+"px");
        $(".content").attr("style","overflow: auto;height:"+$(window).height()*0.92+"px;");
    });
    
    $(".return2").click(function(){
        $(".content").scrollTop(0);
    })
</script>
<script>
    // var num1=num2=num3=0
    window.onload = window.onresize = function(){
        //含义：当窗口加载完成和窗口尺寸变化的时候都执行大括号里面的命令
        //获取窗口的尺寸
        var clientWidth = document.documentElement.clientWidth;
        //console.log(clientWidth);
        //通过屏幕宽度去设置不同的后台根字体的大小
        //document.getElementsByTagName('html')[0].style.fontSize = clientWidth/16+'px';
        document.getElementsByTagName('html')[0].style.fontSize = clientWidth/640*40+'px'
    }
</script>
<script type="text/javascript">
    //绑定滚动条事件
    $('.content').scroll(function () {
        var sTop = $(".content").scrollTop();
        var sTop = parseInt(sTop);
        var height = $(window).height() * 1;
        if (sTop >= height) {
            $(".return2").slideDown();
            $(".return2").show();
        }
        else {
            $(".return2").hide();
            $(this).unbind("bind");
        }
    });

</script>

</body>
</html>