<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ page language="java" import="com.tiantai.wfj.bo.TEshopCusCom" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>&lrm;</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" target="_top" href="<%=basePath %>st/css/e_road_express.css">
    <script src="<%=basePath %>st/js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <%--<script src="<%=basePath %>st/js/font-size.js" type="text/javascript" charset="utf-8"></script>--%>
    <script type="text/javascript" src="<%=basePath%>js/ea/marketing/lottery/awardRotate.js"></script>
    <script type="text/javascript" src="<%=basePath %>js/jquery.qrcode.js"></script>
    <script type="text/javascript" src="<%=basePath %>js/qrcode2.js"></script>
    <script type="text/javascript" src="<%=basePath %>js/utf.js"></script>
</head>
<body>
<header>
    <menu class="clearfix">
        <li>
            <a target="_top" href="javascript:backp()"><img src="<%=basePath%>images/WFJClient/pc/5l5c/img_03.png"/></a>
        </li>
        <li>
            <a target="_top" href="#" class="title-a">${type=="0"? ccom.companyName:ccom[1]}</a>
        </li>
    </menu>
</header>
<div class="content">
    <c:if test="${param.tj eq 'tj'}">
        <div class="div-search">
            <div class="search-box clearfix">
                <div class="div-left">
                    <p><img src="<%=basePath%>images/WFJClient/pc/newimg/img_13.png" alt=""></p>
                </div>
                <input type="text" placeholder="请输入搜索内容">
            </div>
        </div>
        <div class="div-zixun" id="zixun">
            <div class="div-top clearfix">
                <div class="clearfix">
                    <%--// contactCompany20170107NT6PP8C9X40000029147
                    // contactCompany201709112QU7UQY3N70000000036--%>
                    <p><a target="_top" href="<%=basePath%>ea/industry/ea_getAllCompanyList.jspa?industryType=汽车驾校&ccompanyId=contactCompany201709112QU7UQY3N70000000036">e路快车</a></p>
                    <p><a target="_top" href="javascript:signface()">签到考勤</a></p>
                    <p><a target="_top" href="javascript:preCar()">约车训练</a></p>
                    <p><a target="_top" href="<%=basePath%>ea/industry/ea_getAllCompanyList.jspa?industryType=">商家</a></p>
                    <p><a target="_top" href="javascript:phl()">市场</a></p>
                    <p><a target="_top" href="<%=basePath%>ea/productAgent/ea_productAgentList.jspa">招商</a></p>
                    <p><a target="_top" href="<%=basePath%>ea/purchasebids/ea_findGoodbidList.jspa">招标</a></p>
<%--                    <p><a target="_top" href="<%=basePath%>/ea/bidrecruit/ea_getRecruitIndex.jspa?lei=gs">招聘</a></p>--%>
                    <p><a target="_top" href="<%=basePath%>page/ea/main/marketing/unmannedsupermarket/supermarket_home.jsp">超市</a></p>
                    <p><a target="_top" href="<%=basePath%>/ea/wfjshop/ea_getNewsList.jspa?typeNews=&cate=慈善捐赠">慈善</a></p>
                    <p><a target="_top" href="<%=basePath%>/ea/consignee/ea_toVipCenter.jspa?backu=2">会员</a></p>
                </div>
            </div>
        </div>
    </c:if>
    <section>
        <menu class="clearfix">
            <li>
                <a target="_top" href="<%=basePath %>/ea/wfjplatform/ea_platformNews.jspa?ccompanyId=${type=="0"? ccompanyId:ccom[2]}&type=web&miniSystemJudge=00">
                    <img src="<%=basePath %>st/images/e_road_express_07.png"/>
                    <p>平台简介</p>
                </a>
            </li>
            <li>
                <a target="_top" href="#" class="clearfix">
                    <img src="<%=basePath %>st/images/e_road_express_09.png"/>
                    <p>活动</p>
                </a>
            </li>
            <li>
                <c:choose>
                <c:when test="${applied eq '00' }">
            <li>
                <a target="_top" href="<%=basePath %>/ea/coachreserv/ea_registration.jspa?companyId=${companyId}&staffId=${staffid}&flag=1">
                    <img src="<%=basePath %>st/images/ico_6.png" alt="">
                    <p>预约学车</p>
                </a>
            </li>
            </c:when>
            <c:otherwise>
                <a target="_top" href="#apply">
                    <li>
                        <a target="_top" href="<%=basePath %>/ea/industry/ea_getAllCompanyList.jspa?industryType=汽车驾校&enroll=enroll&ccompanyId=${type=="0"? ccompanyId:ccom[2]}">
                            <img src="<%=basePath %>st/images/e_road_express_11.png" alt="">
                            <p>我要报名</p>
                        </a>
                    </li>
                </a>
            </c:otherwise>
            </c:choose>
            </li>
            <li>
                <a target="_top" href="#;" class="jishi">
                    <img src="<%=basePath %>st/images/e_road_express_16.png"/>
                    <p>计时练车</p>
                </a>
            </li>
            <li>
                <a target="_top" href="<%=basePath %>/ea/industry/ea_getAllCompanyList.jspa?industryType=汽车驾校&ccompanyId=${type=="0"? ccompanyId:ccom[2]}">
                    <img src="<%=basePath %>st/images/e_road_express_17.png"/>
                    <p>联营驾校</p>
                </a>
            </li>
            <li>
                <a target="_top" href="<%=basePath %>/st/enroll/ea_News.jspa?ccompanyId=${type=="0"? ccompanyId:ccom[2]}">
                    <img src="<%=basePath %>st/images/e_road_express_18.png"/>
                    <p>平台新闻</p>
                </a>
            </li>
            <li>
                <a target="_top" href="<%=basePath %>ea/wfjshop/ea_getpk.jspa?ccompanyId=${type=="0"? ccompanyId:ccom[2]}">
                    <img src="<%=basePath %>st/images/e_road_express_22.png"/>
                    <p>联营招商</p>
                </a>
            </li>
            <li>
                <a target="_top" href="<%=basePath %>st/enroll/ea_getByTeam.jspa?companyName=${type=="0"? ccom.companyName:ccom[1]}&ccompanyID=${type=="0"? ccompanyId:ccom[2]}">
                    <img src="<%=basePath %>st/images/e_road_express_23.png"/>
                    <p>团队展示</p>
                </a>
            </li>
            <li>
                <a target="_top" href="<%=basePath %>st/enroll/ea_getByEqpt.jspa?companyName=${type=="0"? ccom.companyName:ccom[1]}&ccompanyID=${type=="0"? ccompanyId:ccom[2]}">
                    <img src="<%=basePath %>st/images/e_road_express_24.png"/>
                    <p>设备展示</p>
                </a>
            </li>
            <li>
                <a target="_top" href="<%=basePath%>st/claimManager.jsp" class="clearfix">
                    <img src="<%=basePath %>st/images/e_road_express_29.png"/>
                    <p>驾校认领</p>
                </a>
            </li>
            <li>
                <a target="_top" href="javascript:resource()">
                    <img src="<%=basePath %>st/images/e_road_express_28.png"/>
                    <p>加资源</p>
                </a>
            </li>
        </menu>
    </section>
    <section>
        <menu class="clearfix">
            <li class="clearfix">
                <a onclick="activity('1')"><img src="<%=basePath %>st/images/e_road_express_001.png"/></a>
            </li>
            <li class="clearfix">
                <a onclick="activity('0')"><img src="<%=basePath %>st/images/e_road_express_002.png"/></a>
            </li>
            <%--            <li class="clearfix">
                            <a target="_top" href="javscript:;"><img src="<%=basePath %>st/images/e_road_express_003.png"/></a>
                        </li>
                        <li class="clearfix">
                            <a target="_top" href="javscript:;"><img src="<%=basePath %>st/images/e_road_express_004.png"/></a>
                        </li>
                        <li class="clearfix">
                            <a target="_top" href="javscript:;"><img src="<%=basePath %>st/images/e_road_express_005.png"/></a>
                        </li>--%>
        </menu>
    </section>
    <%--驾校学车产品--%>
    <section class="driverProducts">
        <menu class="clearfix"></menu>
    </section>
    <%--热门驾校--%>
    <section class="stList">
        <h2>
            热门驾校
            <span>
						<a target="_top" href="<%=basePath%>ea/industry/ea_getAllCompanyList.jspa?industryType=汽车驾校&ccompanyId=${type=="0"? ccompanyId:ccom[2]}"
                           class="clearfix">
							更多
							<img src="<%=basePath %>st/images/e_road_express_54.png"/>
						</a>
					</span>
        </h2>
        <menu></menu>
        <footer class="dropdown">
            <a onclick="stmore()" class="stmorelist">
                <p>查看更多驾校</p>
                <img src="<%=basePath %>st/images/e_road_express_71.png"/>
            </a>
        </footer>
    </section>
    <%--招生在线平台新闻--%>
    <section style="padding-top: 0.3rem;" class="news">
        <h2>
            招生在线平台新闻
            <%--<span>--%>
            <%--<a target="_top" href="#">--%>
            <%--发布新闻--%>
            <%--<img src="<%=basePath %>st/images/e_road_express_53.png" />--%>
            <%--</a>--%>
            <%--</span>--%>
        </h2>
        <menu></menu>
        <footer>
            <a target="_top" href="<%=basePath%>st/enroll/ea_News.jspa?ccompanyId=${type=="0"? ccompanyId:ccom[2]}">查看更多新闻>></a>
        </footer>
    </section>
    <%--投诉曝光台--%>
    <section class="complaints">
        <h2>
            投诉曝光台
            <span>
						<%--<a target="_top" href="#">--%>
							<%--申请加入--%>
							<%--<img src="<%=basePath %>st/images/e_road_express_53.png" />--%>
						<%--</a>--%>
					</span>
        </h2>
        <menu></menu>
        <footer>
            <a target="_top" href="<%=basePath%>ea/complaint/ea_findComplaint.jspa?staffid=<%=session.getAttribute("key_shop_cus_com")!=null?((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getStaffid():""%>">查看更多投诉曝光台>></a>
        </footer>
    </section>
    <%--自律管理小组--%>
    <section>
        <%--<h2>--%>
        <%--自律管理小组--%>
        <%--<span>--%>
        <%--<a target="_top" href="#">--%>
        <%--申请加入--%>
        <%--<img src="<%=basePath %>st/images/e_road_express_53.png" />--%>
        <%--</a>--%>
        <%--</span>--%>
        <%--</h2>--%>
        <%--<menu>--%>
        <%--<li class="clearfix">--%>
        <%--<img src="<%=basePath %>st/images/e_road_express_base.jpg" alt="" />--%>
        <%--<div>--%>
        <%--<h3><a target="_top" href="#" class="txt">自律管理小组1组</a></h3>--%>
        <%--<p>北京天太世统科技有限公司</p>--%>
        <%--</div>--%>
        <%--</li>--%>
        <%--</menu>--%>
        <%--<footer>--%>
        <%--<a target="_top" href="#">查看更多自律管理小组>></a>--%>
        <%--</footer>--%>
    </section>
    <%--优秀企业--%>
    <section>
        <%--<h2>--%>
        <%--优秀企业--%>
        <%--<span>--%>
        <%--<a target="_top" href="#">--%>
        <%--申请加入--%>
        <%--<img src="<%=basePath %>st/images/e_road_express_53.png" />--%>
        <%--</a>--%>
        <%--</span>--%>
        <%--</h2>--%>
        <%--<menu>--%>
        <%--<li class="clearfix">--%>
        <%--<img src="<%=basePath %>st/images/e_road_express_base.jpg" alt="" />--%>
        <%--<div>--%>
        <%--<h3><a target="_top" href="#" class="txt">柳江驾校招商平台</a></h3>--%>
        <%--<p>柳江镇修正路45号</p>--%>
        <%--</div>--%>
        <%--</li>--%>
        <%--</menu>--%>
        <%--<footer>--%>
        <%--<a target="_top" href="#">查看更多优秀企业>></a>--%>
        <%--</footer>--%>
    </section>
    <%--优秀员工--%>
    <section class="employees">
        <h2>
            优秀员工
            <%--<span>--%>
            <%--<a target="_top" href="#">--%>
            <%--申请加入--%>
            <%--<img src="<%=basePath %>st/images/e_road_express_53.png" />--%>
            <%--</a>--%>
            <%--</span>--%>
        </h2>
        <menu></menu>
        <footer>
            <a target="_top" href="<%=basePath %>st/enroll/ea_getByTeam.jspa?companyName=${type=="0"? ccom.companyName:ccom[1]}&ccompanyID=${type=="0"? ccompanyId:ccom[2]}">查看更多优秀员工>></a>
        </footer>
    </section>
    <%--共享教练车--%>
    <section class="car">
        <h2>
            共享教练车
            <%--<span>--%>
            <%--<a target="_top" href="#">--%>
            <%--申请加入--%>
            <%--<img src="<%=basePath %>st/images/e_road_express_53.png" />--%>
            <%--</a>--%>
            <%--</span>--%>
        </h2>
        <menu class="clearfix"></menu>
        <footer>
            <a target="_top" href="<%=basePath %>st/enroll/ea_getByEqpt.jspa?companyName=${type=="0"? ccom.companyName:ccom[1]}&ccompanyID=${type=="0"? ccompanyId:ccom[2]}">查看更多共享教练车>></a>
        </footer>
    </section>
    <%--训练场地--%>
    <section>
        <%--<h2>--%>
        <%--训练场地--%>
        <%--<span>--%>
        <%--<a target="_top" href="#">--%>
        <%--申请加入--%>
        <%--<img src="<%=basePath %>st/images/e_road_express_53.png" />--%>
        <%--</a>--%>
        <%--</span>--%>
        <%--</h2>--%>
        <%--<menu class="clearfix">--%>
        <%--<li>--%>
        <%--<a target="_top" href="#">--%>
        <%--<img src="<%=basePath %>st/images/e_road_express_57.png"/>--%>
        <%--<p>--%>
        <%--训练场地--%>
        <%--</p>--%>
        <%--</a>--%>
        <%--</li>--%>
        <%--</menu>--%>
        <%--<footer>--%>
        <%--<a target="_top" href="#">查看更多训练场地>></a>--%>
        <%--</footer>--%>
    </section>
    <%--考试场地--%>
    <section>
        <%--<h2>--%>
        <%--考试场地--%>
        <%--<span>--%>
        <%--<a target="_top" href="#">--%>
        <%--申请加入--%>
        <%--<img src="<%=basePath %>st/images/e_road_express_53.png" />--%>
        <%--</a>--%>
        <%--</span>--%>
        <%--</h2>--%>
        <%--<menu class="clearfix">--%>
        <%--<li>--%>
        <%--<a target="_top" href="#">--%>
        <%--<img src="<%=basePath %>st/images/e_road_express_57.png"/>--%>
        <%--<p>--%>
        <%--考试场地--%>
        <%--</p>--%>
        <%--</a>--%>
        <%--</li>--%>
        <%--</menu>--%>
        <%--<footer>--%>
        <%--<a target="_top" href="#">查看更多考试场地>></a>--%>
        <%--</footer>--%>
    </section>
    <%--业务员团队--%>
    <section>
        <%--<h2>--%>
        <%--业务员团队--%>
        <%--<span>--%>
        <%--<a target="_top" href="#">--%>
        <%--申请加入--%>
        <%--<img src="<%=basePath %>st/images/e_road_express_53.png" />--%>
        <%--</a>--%>
        <%--</span>--%>
        <%--</h2>--%>
        <%--<menu>--%>
        <%--<li class="clearfix">--%>
        <%--<img src="<%=basePath %>st/images/e_road_express_base.jpg" alt="" />--%>
        <%--<div>--%>
        <%--<h3><a target="_top" href="#" class="txt">野狼业务员团队</a></h3>--%>
        <%--<p>四川胜威驾校</p>--%>
        <%--</div>--%>
        <%--</li>--%>
        <%--</menu>--%>
        <%--<footer>--%>
        <%--<a target="_top" href="#">查看更多业务员团队>></a>--%>
        <%--</footer>--%>
    </section>
    <%--联盟商家--%>
    <section>
        <%--<h2>--%>
        <%--联盟商家--%>
        <%--<span>--%>
        <%--<a target="_top" href="#">--%>
        <%--申请加入--%>
        <%--<img src="<%=basePath %>st/images/e_road_express_53.png" />--%>
        <%--</a>--%>
        <%--</span>--%>
        <%--</h2>--%>
        <%--<menu>--%>
        <%--<li class="clearfix">--%>
        <%--<img src="<%=basePath %>st/images/e_road_express_base.jpg" alt="" />--%>
        <%--<div>--%>
        <%--<h3><a target="_top" href="#" class="txt">湘西轮胎制造厂</a></h3>--%>
        <%--<p>柳江镇修正路45号</p>--%>
        <%--</div>--%>
        <%--</li>--%>
        <%--</menu>--%>
        <%--<footer>--%>
        <%--<a target="_top" href="#">查看更多联盟商家>></a>--%>
        <%--</footer>--%>
    </section>
    <%--省市县自律平台代理认领--%>
    <section>
        <%--<h2>--%>
        <%--省市县自律平台代理认领--%>
        <%--<span>--%>
        <%--<a target="_top" href="#">--%>
        <%--申请加入--%>
        <%--<img src="<%=basePath %>st/images/e_road_express_53.png" />--%>
        <%--</a>--%>
        <%--</span>--%>
        <%--</h2>--%>
        <%--<menu>--%>
        <%--<li class="clearfix">--%>
        <%--<img src="<%=basePath %>st/images/e_road_express_base.jpg" alt="" />--%>
        <%--<div>--%>
        <%--<h3><a target="_top" href="#" class="txt">省级招生在线平台</a></h3>--%>
        <%--<p>柳江镇修正路45号</p>--%>
        <%--</div>--%>
        <%--</li>--%>
        <%--</menu>--%>
    </section>
    <%--省市县自律招生(展示)--%>
    <section>
        <%--<h2>--%>
        <%--省市县自律招生(展示)--%>
        <%--<span>--%>
        <%--<a target="_top" href="#">--%>
        <%--申请加入--%>
        <%--<img src="<%=basePath %>st/images/e_road_express_53.png" />--%>
        <%--</a>--%>
        <%--</span>--%>
        <%--</h2>--%>
        <%--<menu>--%>
        <%--<li class="clearfix">--%>
        <%--<img src="<%=basePath %>st/images/e_road_express_base.jpg" alt="" />--%>
        <%--<div>--%>
        <%--<h3><a target="_top" href="#" class="txt">省级招生在线平台</a></h3>--%>
        <%--<p>柳江镇修正路45号</p>--%>
        <%--</div>--%>
        <%--</li>--%>
        <%--</menu>--%>
    </section>
    <%--项目咨询--%>
    <section>
        <img src="<%=basePath %>st/images/e_road_express_74.png"/>
        <p class="txt">我要咨询此项目（请留下联系方式，商家会主动联系您）</p>
        <form class="ipt_con">
            <p>
                <img src="<%=basePath %>st/images/e_road_express_77.png"/>
                <label for="names">姓名：</label>
                <input class="consultantName" type="text" name="" id="names" value="" required="required"/>
            </p>
            <p>
                <img src="<%=basePath %>st/images/e_road_express_77.png"/>
                <label for="call">电话：</label>
                <input class="consultantPhone" type="number" name="" id="call" value="" required="required"/>
            </p>
            <input type="button" value="提交" onclick="consultantSubmit(this)"/>
        </form>
    </section>
    <%--注册下载--%>
    <section>
        <p>注册下载</p>
        <form class="ipt_con" id="myform">
            <p>
                <input type="text" placeholder="请输入姓名" id="name" name="staff.staffName"/>
            </p>
            <p>
                <input type="number" placeholder="请输入注册手机号" id="tel" name="phones"/>
            </p>
            <p class="clearfix">
                <input type="text" id="valnum" placeholder="请输入验证码"/>
                <input type="button" value="获取验证码" onclick="sendCode(this);return false;" id="ver_btn"/>
            </p>
            <p>
                <input type="password" placeholder="设置密码" id="password" name="intf"/>
            </p>
            <p>
                <input type="password" placeholder="请再次输入密码" id="confirmPassword"/>
                <input type="hidden" value="${sccid}" name="sccid">
            </p>
            <p>
                <input type="button" name="" id="submit_btn" value="提交"/>
            </p>
        </form>
    </section>
    <%--关于我们--%>
    <section>
        <h2>
            关于我们
            <span>
						<a target="_top" href="#">
							MORE
							<img src="<%=basePath %>st/images/e_road_express_53.png"/>
						</a>
					</span>
        </h2>
        <div class="clearfix">
            <a target="_top" href="#">
                <img src="<%=basePath %>st/images/e_road_express_81.png"/>
            </a>
            <section>
                <img src="<%=basePath %>st/images/e_road_express_84.png"/>
                <p class="clearfix"><span>地址：</span><span>北京市东直门外大街宇飞大厦801</span></p>
                <p>电话：010-64167113</p>
                <%--<p>传真：010-7879879</p>--%>
                <p>邮箱：ttsw2010@163.com</p>
            </section>
        </div>
    </section>
</div>
<footer>
    <p>
        扫一扫，更多高品质服务
    </p>
    <div id="qrcodeCanvas" style="padding-top: 1rem"></div>
</footer>
</body>
<script type="text/javascript">
    document.addEventListener("DOMContentLoaded", function() {
        if (typeof Android!=='undefined'){
            setShowFromSystem("zixun");
        }
    });
</script>
<script type="text/javascript">
    var basePath = '<%=basePath%>';
    var companyId = '${companyId}';
    var ccompanyId = '${type=="0"? ccompanyId:ccom[2]}';
    var companyname = '${search}';
    var pageNumber = '${pageNumber}';
    var pageSize = '${pageSize}';
    var sccid = '<%=session.getAttribute("key_shop_cus_com")!=null ?((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getStaffid():"" %>';
    var sccID = '<%=session.getAttribute("key_shop_cus_com")!=null ?((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getSccId():"" %>';
    var companyID = '<%=session.getAttribute("key_shop_cus_com")!=null ?((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getCompanyId():"" %>';

    var clock = '';
    var nums = 60;
    var btn = document.getElementById("ver_btn");
    var tel = document.getElementById("tel");
    var _name = document.getElementById("name");
    var valnum = document.getElementById("valnum");
    var submit_btn = document.getElementById("submit_btn");
    var confirmPassword = document.getElementById("confirmPassword");
    var password = document.getElementById("password");

    var tj = "${param.tj}";
    $(function () {
        if (tj == "tj") {
            $(".title-a").text("推荐");
        }

        $("header").css("height", $(window).height() * 0.08 - 1 + "px");
        $("header menu li").css("height", $(window).height() * 0.08 - 1 + "px");
        $("header menu li").css("line-height", $(window).height() * 0.08 - 1 + "px");
        $("div.kong").css("height", $(window).height() * 0.08 - 1 + "px");

        $(".jishi").click(function () {

            var url = basePath + "/ea/elkcRecord/sajax_ea_ajaxTimingToPracticeCar.jspa";
            $.ajax({
                url: url,
                type: "post",
                dataType: "json",
                success: function (data) {
                    var jishi = eval("(" + data + ")");
                    var status = jishi.map.status;
                    var subject = jishi.map.object;
                    if (status == "00") {
                        return alert("未登录");
                    } else if (status == "01") {
                        return alert("未报名");
                    } else if (status == "02") {
                        return alert("未预约");
                    } else if (status == "03") {
                        return alert("时间未到");
                    } else if (status == "04") {

                        var u = window.navigator.userAgent;
                        var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
                        var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
                        var card = "620523198607120855";
                        if (isAndroid == true) {
                            Android.changetoJiShiLianChe("620523198607120855", subject);
                        } else if (isiOS == true) {
                            var url = "func=" + 'jslc';
                            params = {'card': card, 'subject': subject};
                            for (var i in params) {
                                url = url + "&" + i + "=" + params[i];
                            }
                            window.webkit.messageHandlers.Native.postMessage(url);
                        }
                    }
                }
            });
        });
    });

    function backp() {
        var sc = "${param.sc}";
        if (sc == "web") {
            window.history.back();
            return false;
        } else {
            try {
                var u = window.navigator.userAgent;
                var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
                var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
                if (isAndroid == true) {
                    console.log("安卓");
                    Android.callAndroidjianli();//调用安卓接口
                } else if (isiOS == true) {
                    console.log("IOS");
                    var url = "func=" + 'doneClose';
                    window.webkit.messageHandlers.Native.postMessage(url);
                }
            } catch (error) {
                window.history.back();
                return false;
            }
        }
    }

    function back() {
        var u = window.navigator.userAgent;
        var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
        var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
        try {
            if (isAndroid == true) {
                console.log("安卓");
                Android.callAndroidjianli();//调用安卓接口

            } else if (isiOS == true) {
                var str = calliosOrder();
                var url = "func=" + 'iossavephotos';
                params = {'saveImage': str};
                for (var i in params) {
                    url = url + "&" + i + "=" + params[i];
                    console.log(url);
                }
            }
        } catch (e) {
            window.history.go(-1);
            return false;
        }
    }

    function detCom(teacherId, staffid) {
        document.location.href = "<%=basePath%>ea/complaint/ea_teachers.jspa?teacherId=" + teacherId + "&staffid=" + staffid;
    }

</script>
<script type="text/javascript" src="<%=basePath %>st/js/stapplist.js"></script>
</html>
