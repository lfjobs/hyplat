<!DOCTYPE html>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" import="hy.ea.bo.human.Staff" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="renderer" content="webkit">
    <title>招标招聘-招聘信息介绍</title>
    <link rel="stylesheet" href="<%=basePath %>page/newMyapp/css/newStyle.css">
    <script type="text/javascript" src="<%=basePath %>page/newMyapp/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>page/newMyapp/js/toShareCode.js"></script>
    <script type="text/javascript" src="<%=basePath %>js/ea/pcwfj/liandong.js"></script>
    <script type="text/javascript" src="<%=basePath %>js/ea/pcwfj/PC_recruitmentDetails.js"></script>
    <!--ie8及以下浏览器不兼容-->
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="js/html5shiv.min.js"></script>
    <script src="js/respond.min.js"></script>
    <![endif]-->
     <script>
		var basePath = '<%=basePath%>';
		var pageNumber = 0;
		var pageCount;
		var tpid='${param.tpID}';
		var titleJudge = '${titleJudge}';
		var hot='${map.resultObj[7]}';
		var a = '<%=((Staff) session.getAttribute("key_staff"))==null?"":((Staff) session.getAttribute("key_staff")).getStaffName()%>';
	</script>
</head>
<body>
<div id="header">
    <ul>
        <li class="logo">
            <a href="<%=basePath%>ea/wfjshop/ea_getWFJshops.jspa"><img src="<%=basePath%>page/newMyapp/images/wfj.png" alt="" class="log"></a>
        </li>
        <li class="scroll">
            <img src="<%=basePath%>page/newMyapp/images/scroll.png" alt="">
        </li>
    </ul>
</div>
    <div class="content" style="background-color: #f5f5f5;">
        <div class="ind_con_head">
            <div class="bid_head">
                <h2>招标招聘</h2>
                <ul>
                    <a href="<%=basePath%>ea/newpcend/ea_skip.jspa?titleJudge=07&seven=00"><li>招标商品</li></a>
                    <a href="<%=basePath%>ea/newpcend/ea_skip.jspa?titleJudge=07&seven=01"><li class="active">招聘人才</li></a>
                    <a href="<%=basePath%>/ea/newpcend/ea_skip.jspa?titleJudge=07&seven=02"><li>招商产品</li></a>
                </ul>
            </div>
        </div>
        <div class="nav"><a href="#;">招标招聘>招聘人才>职位搜索></a><a href="#;" class="present">HY-高级秘书（北京）</a></div>
        <div class="Certificate_con Cert_det">
            <div class="left">
                <h3 class="title">招聘职位</h3>
                <div class="search">
                    <div class="sea_left">
                        <input type="text" placeholder="请输入您要查找的职位名称" onkeypress="if (event.keyCode == 13)selPosition();" value="" />
                    </div>
                    <button onclick="selPosition();">职位搜索</button>
                </div>
                <div class="select">
                    <div class="mil_se">
                        <ul class="mil_se_left local">
                            <li>全部</li>
                            <li>北京</li>
                            <li>深圳</li>
                            <li>上海</li>
                            <li>成都</li>
                            <li>武汉</li>
                            <li>香港</li>
                            <li>汉口</li>
                            <li>天津</li>
                        </ul>
                        <a href="#;" class="more" id="more_dy">更多<img src="<%=basePath%>page/newMyapp/images/ico-more2.png" alt="" ></a>
                    </div>
                    <div class="mil_se">
                        <ul class="mil_se_left jobs">
                        <!-- js拼接 -->
                        </ul>
                        <a href="#;" class="more" id="more_js">更多<img src="<%=basePath%>page/newMyapp/images/ico-more2.png" alt=""></a>
                    </div>
                    <div class="Selected">
                        <ul>
                            <li id="local">
                            <p>北京</p>
                            <input type="hidden" id="province" /> 
                            <input type="hidden" id="city" />
                            <img src="<%=basePath%>page/newMyapp/images/ico-delete.png">
                            </li>
                            <li id="jobs">
                            <p>销售经理</p>
                           	<input id="codePID" type="hidden" />
                            <input id="codeID" type="hidden" />
                            <img src="<%=basePath%>page/newMyapp/images/ico-delete.png">
                            </li>
                        </ul>
                    </div>
                    <div class="details_con">
                        <h3 class="tit">${map.resultObj[0]}（${map.resultObj[1]}）</h3>
                        <div class="text_con">
                            <ul class="name">
                                <li>工作地区：${map.resultObj[2]}</li>
                                <li>职位类别：${map.resultObj[3]}</li>
                                <%--<li>招聘人数：${map.resultObj[4]}人</li>--%>
                            </ul>
                            <div class="text1">
                                <h5>工作内容：</h5>
                                <p>${map.resultObj[5]}</p>
                            </div>
                            <%-- <div class="text1">
                                <h5>工作要求：</h5>
                                <p>${map.resultObj[5]}</p>
                            </div> --%>
                            <span style="display: none;">${map.resultObj[6]}</span>
                            <%--<c:if test='${(param.tpID != "0" && param.tpID!=null && param.tpID!="")||(map.resultObj[7] != "0" && map.resultObj[7]!=null && map.resultObj[7]!="") }'>--%>
                                <%--<button style="background-color: #d6cdc9">已申请</button>--%>
                            <%--</c:if>--%>
                            <%--<c:if test='${(param.tpID == "0" || param.tpID==null || param.tpID=="") || (map.resultObj[7] == "0") }'>--%>
                                <%--<button class="applyJob">申请岗位</button>--%>
                            <%--</c:if>--%>
                            ${(param.tpID==""||param.tpID==null || param.tpID=="0")?
                              ((map.resultObj[7]=="0"||map.resultObj[7]==""||map.resultObj[7]==null)?
                                "<button class='applyJob'>申请岗位</button>":"<button style='background-color: #d6cdc9'>已申请</button>"):"<button style='background-color: #d6cdc9'>已申请</button>"}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    </div>
    <div id="footer" style="margin: 0;">
        <div class="text footer_txt">
            <!-- <p><a href="#;">天使云服务</a><a href="#;">联系我们</a><a href="#;">商务合作</a><a href="#;">网站地图</a><a href="#;">免责声明</a></p> -->
            <p>Copyright &copy; 2010-2015北京天太世统科技有限公司<span style="padding-left: 50px;">京ICP备10034132号 </span></p>
            <p>公司地址：北京市东城区东直门外大街42号宇飞大厦8层801室<span>热线电话:010-64167113</span></p>
            <p>版权所有：北京天太世统科技有限公司</p>
        </div>
    </div>
    <!--返回顶部-->
<a href="#header" class="return"><img src="<%=basePath%>page/newMyapp/images/return.png" alt=""></a>
<div class="liandong_alert_">
    <div class="liandong_alert">
        <img src="<%=basePath%>page/newMyapp/images/ico_x.png" id="delete2">
        <h4>选择省市</h4>
        <dl>
            <dd style="text-align: right;width: 45%;">
                省 份：<select id="selProvince" onchange="provinceChange();"></select>
            </dd>
            <dd style="text-align: left;text-indent: 10px;width: 55%;">
                市(区)：<select id="selCity"></select>
            </dd>
        </dl>
        <button>确定选择</button>
    </div>
</div>
<div class="Certificate_alert_">
    <div class="Certificate_alert">
        <div class="left">
            <ul>
            <!-- js拼接 -->
           </ul>
        </div>
        <div class="right">
            <div id="delete">
                <img src="<%=basePath%>page/newMyapp/images/ico_x.png">
            </div>
            <!-- js拼接 -->
       </div>
    </div>
</div>
    
<script>
    $(document).ready(function(){
        /*头部*/
        $(".bid_head ul li").click(function(){
            $(this).addClass("active").parents(".bid_head ul a").siblings().find("li").removeClass("active");
        });

        /*招标信息和规格参数*/
        $(".bid_det_con .bottom .bid_details_tit li").click(function(){
            $(this).addClass("active").siblings().removeClass("active");
        });
        $(".bid_det_con .bottom .bid_details_tit .TenderInfo").click(function(){
            $("#TenderInfo").show().siblings().hide();
        });
        $(".bid_det_con .bottom .bid_details_tit .Specifications").click(function(){
            $("#Specifications").show().siblings().hide();
        });

        $(".Certificate_con .select .mil_se .more").click(function(){
            $(this).parents(".Certificate_con .select .mil_se").find(".hide_").toggleClass("hide");
        });

        /*地域更多*/
        $(".Certificate_con .select .mil_se #more_dy").click(function(){
            $(".liandong_alert_").show();
        });
        /*关闭职业弹框*/
        $("#delete2").click(function(){
            $(".liandong_alert_").hide();
        });

        $(".liandong_alert button").click(function(){
        	var optionPro=$("#selProvince option:selected").text();
            var options=$("#selCity option:selected").text();
            $(".Certificate_con .select .Selected ul #local p").text(options);
            $(".Certificate_con .select .Selected ul #local #province").val(optionPro);
            $(".Certificate_con .select .Selected ul #local #city").val(options);
            $(".Certificate_con .select .Selected ul #local").show();
            $(".liandong_alert_").hide();
        });
        
        /*关闭职业弹框*/
        $("#delete img").click(function(){
            $(".Certificate_alert_").hide();
        });
        /*添加/删除*/
        $(".Certificate_con .select .local li").on('click',function(){
            var txt = $(this).text();
            $(".Certificate_con .select .Selected ul #local p").text(txt);
            $(".Certificate_con .select .Selected ul #local #province").val(txt);
            $(".Certificate_con .select .Selected ul #local #city").val("");
            $(".Certificate_con .select .Selected ul #local").show();
        });
        
        $(".Certificate_con .select .Selected ul li img").click(function(){
            $(this).parents(".Certificate_con .select .Selected ul li").hide();
            $(this).prevAll(".Certificate_con .select .Selected ul li input").val("");
        });
    })
</script>
</body>
</html>