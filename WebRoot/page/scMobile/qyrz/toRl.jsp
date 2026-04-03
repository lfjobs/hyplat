<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <meta name="format-detection" content="telephone=yes"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/scMobile/qyrz/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/scMobile/qyrz/rl.css?version=20210324"/>
    <script src="<%=basePath%>js/font-size.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/jquery-2.1.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/scMobile/qyrz/toRl.js?version=20210328" type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript" src="<%=basePath %>/js/WFJClient/ap.js"></script>
    <script src="<%=basePath%>js/title.js" type="text/javascript" charset="utf-8"></script>
    <title>认领</title>
    <style>
        html,
        body,
        #container {
            width: 100%;
            height: 100%;
        }
    </style>
</head>
<body>
<div id="submitClaim">
<div id="btn_gwc">
    <p>成功加入购物车</p>
</div>
<%--/*微信支付是否完成*/--%>
<div id="weixinqr">
    <div class="box">
        <p>请确认微信支付是否已完成</p>
        <ul>
            <li class="payresult">
                已完成支付
            </li>
            <li class="payresult">
                支付遇到问题，重新支付
            </li>
        </ul>
    </div>
</div>
<%--<header>--%>
    <%--<ul  class="clearfix">--%>
        <%--<li>--%>
            <%--<img src="<%=basePath%>images/scMobile/qyrz/img-1.png" onclick="javascript:window.history.back();return false;" >--%>
        <%--</li>--%>
        <%--<li>--%>
            <%--企业门店个体入驻--%>
        <%--</li>--%>
    <%--</ul>--%>
<%--</header>--%>
<div class="content">
    <section class="sec-001 sec-pimg">
      ${content}
    </section>
    <!--基本信息填写-->
    <section class="sec-002">
     <c:if test="${param.head eq 'show'}">
        <ul class="ul-list clearfix">
            <li class="active">
                开户注册
            </li>
            <li>
                已开户直接下载
            </li>
            <li>
                关注公众号
            </li>
        </ul>
     </c:if>
        <form name="claimForm" id="claimForm" method="post">

        <!--产品信息展示-->
        <div style="padding: .75rem">
            <img src="${param.goodsimg}" style="width: 100%;aspect-ratio: 4/3">
            <div style="font-size: 1rem; color: red; font-weight: bold; margin-top: 8px;">￥${param.money}元</div>
            <div style="font-size: .75rem; margin-top: 8px;">${param.goodsname}</div>
        </div>

        <div class="div-list">
            <div class="div-list-01">
                <h3>填写入驻认领资料</h3>
<%--                <h3 onclick="getCompanyInfoFromDoubao()">自动识别 </h3>--%>
                <h3 id="aiBtn">点击识别</h3>
                <ul>
                    <li>
                        <input type="text" placeholder="请填写准确企业商家名称" name="company.companyName" class="companyName" maxlength="50">
                    </li>
                    <li>
                        <input type="text" placeholder="行业市场" name="company.industryType" class="industryType" >
                        <input type="text"  style="display:none;" name="company.industryId" class="industryId" >
                        <input type="text"   style="display:none;"  class="journalNum" >
                        <input type="text"  style="display:none;"  class="h5_url" >
                    </li>
                    <li>
                        <input type="text" placeholder="品牌信息" name="company.brandInfo" maxlength="50">
                    </li>

                    <li>
                        <input type="text" placeholder="店铺名称" value="${param.name}" name="company.shopname" class="shopname" maxlength="50">

                    </li>
                    <li class="li-img">
                        <input type="text" placeholder="企业商家地域定位" name="company.addr" readonly value="${param.pname == param.cityname?"":param.pname}${param.cityname}${param.adname}${param.address}">
                        <img src="<%=basePath%>images/scMobile/qyrz/img-003.png" >
                    </li>
                    <div>
                        <label style="width: 30%;margin: auto;font-size: 0.65rem;">商家类型：</label>
                        <div style="width: 100%">
                            <div style="width: 100%; display: flex; flex-direction: row">
                                <div style="width: 100%;">
                                    <input style="-webkit-appearance: checkbox;height: 0.7rem;" type="radio" name="comPro"
                                           value="2500"/>
                                    <label style="font-size: 0.65rem;">个人卖家</label>
                                </div>
                                <div style="width: 100%;">
                                    <input style="-webkit-appearance: checkbox;height: 0.7rem;" type="radio" name="comPro"
                                           value="2"/>
                                    <label style="font-size: 0.65rem;">企业</label>
                                </div>
                                <div style="width: 100%;">
                                    <input style="-webkit-appearance: checkbox;height: 0.7rem;" type="radio" name="comPro"
                                           value="4"/>
                                    <label style="font-size: 0.65rem;">个体商户</label>
                                </div>
                            </div>

                            <div style="width: 100%; display: flex; flex-direction: row">
                                <div style="width: 100%;">
                                    <input style="-webkit-appearance: checkbox;height: 0.7rem;" type="radio" name="comPro"
                                           value="3"/>
                                    <label style="font-size: 0.65rem;">党政机关</label>
                                </div>
                                <div style="width: 100%;">
                                    <input style="-webkit-appearance: checkbox;height: 0.7rem;" type="radio" name="comPro"
                                           value="5"/>
                                    <label style="font-size: 0.65rem;">事业单位</label>
                                </div>
                                <div style="width: 100%;">
                                    <input style="-webkit-appearance: checkbox;height: 0.7rem;" type="radio" name="comPro"
                                           value="1708"/>
                                    <label style="font-size: 0.65rem;">其它组织</label>
                                </div>
                            </div>
                        </div>
                    <li>
                        <input type="text" placeholder="企业电话" value="${param.tel}" name="cdl.companyPhone" class="companyPhone" maxlength="20">
                        <input type="text" style="display:none;" value="${param.money}" name="morre" id="morre">
                        <input type="text" style="display:none;" name="sort" value="${param.goodsname}">
                        <input type="text" style="display:none;" value="1" name="count">
                        <input type="text" style="display:none;" value="${param.money}" name="indus">

                        <input type="text" style="display:none;" value="${param.ppid}" name="ppid">
                        <input type="text" style="display:none;" value="${param.id}" name="company.gdID">
                        <input type="text" style="display:none;" value="${param.x}" name="company.accuracy">
                        <input type="text" style="display:none;" value="${param.y}" name="company.dimension">
                        <input type="text" style="display:none;" value="${param.pname}" name="company.pname">
                        <input type="text" style="display:none;" value="${param.cityname}" name="company.cityname">
                        <input type="text" style="display:none;" value="${param.adname}" name="company.adname">
                        <input type="text" style="display:none;" value="${param.address}"  name="company.street">
                        <input type="text" style="display:none;" value="${param.photo}"  name="cdl.logo">
                        <input type="text" style="display:none;" value="${param.busiManagerID}"  name="cdl.busiManagerID">

                        <input type="text" style="display:none;" value="${param.gdcate}"  name="company.gdcate">
                        <input type="text" style="display:none;" value="${param.gdcode}"  name="company.gdcode">
                        <input type="text" style="display:none;" value="${param.gdcate2}"  name="company.gdcate2">
                        <input type="text" style="display:none;" value="${param.gdcode2}"  name="company.gdcode2">
                    </li>

                    <li>
                        <input type="text" placeholder="负责人姓名"  name="cdl.companyManager" class="companyManager" maxlength="10">
                    </li>
                    <li>
                        <input type="text" placeholder="负责人手机号"   name="cdl.managertel" class="managertel"  />

                    </li>
                    <li class="li-p">
                        <input type="text" placeholder="验证码" id="valnum1">
                        <p id="ver_btn1" onclick="sendCode1(this);return false;">
                            获取验证码
                        </p>
                    </li>
                    <%--<li>--%>
                        <%--<input type="text" placeholder="业务员手机号"  value="${staffname}${fn:length(telphone)==11?telphone:""}" ${fn:length(telphone)==11?readonly:""}/>--%>

                    <%--</li>--%>

                </ul>
                <input type="button" name="" id="submitRl" value="提交认领" />
            </div>
            <div class="div-list-02">
                <p class="clearfix">
                    <img src="<%=basePath%>images/scMobile/qyrz/xiazai.png" >
                    <img src="<%=basePath%>images/scMobile/qyrz/xiaziaios.png" >
                </p>
            </div>
            <div class="div-list-03">
                <img src="<%=basePath%>images/scMobile/qyrz/gongzhonghao.jpg" >
                <p>关注微分金数字地球公众号</p>
            </div>
        </div>
        </form>

        <form id="form1" name="form1">
        <section class="sec-003">

            <div>
                <h2>
                    我要咨询
                    <span>     <c:if test="${param.head eq 'show'}">
								<a href="tel:010-64167113"><img
                                     src="<%=basePath%>images/scMobile/qyrz/tel.png"  />电话</a>
                                </c:if>
                                <c:if test="${param.head ne 'show'}">
                                    <a onclick="getPhone('010-64167113')"><img
                                            src="<%=basePath%>images/scMobile/qyrz/tel.png"  />电话</a>
                                </c:if>
							</span>
                </h2>
                <p>
                    我要咨询此项目(请留下联系方式，商家会主动联系您)
                </p>
                <ul>
                    <li>
                        <label><span>*</span>姓名:</label>
                        <input type="text"  placeholder="请输入姓名" id="name"  name="consult.consultantName" />
                        <input type="text"  style="display:none;"  name="consult.ppid" value="${param.ppid}"/>
                        <input type="text"  style="display:none;"  name="consult.staffId" value=""/>
                        <input type="text"  style="display:none;"  name="consult.ccompanyId" value="${param.ccompanyid}"/>
                        <input type="text"  style="display:none;"  name="consult.goodsname" value="${param.goodsname}"/>
                    </li>
                    <p class="li-pad"></p>
                    <li>
                        <label><span>*</span>手机号:</label>
                        <input type="number" maxlength="11" placeholder="请输入手机号" id="tel" name="consult.consultantPhone" />
                    </li>
                    <p class="li-pad"></p>
                    <li>
                        <label><span>*</span>验证码:</label>
                        <input type="number" name="" placeholder="请输入验证码" id="valnum" />
                        <span id="ver_btn"    onclick="sendCode(this);return false;">
									获取验证码
								</span>
                    </li>
                </ul>
                <input type="button" name="" id="submit_btn" value="提交咨询"  />
            </div>

        </section>
        </form>
    </section>

    <div class="wfj11_015_buy_commit" style="display:none;">
        <div class="wfj11_015_need">
            <div class="wfj11_015_width">
                <ul>
                    <li class="left">需支付：</li>
                    <li class="right" style="color:#F74C31;"><span>￥</span><span class="xzf">${param.money}</span></span>
                    </li>
                </ul>
            </div>
        </div>
        <div class="wfj11_015_allbay">
            <div class="wfj11_015_width">
                <table>
                    <tr>
                        <td colspan="2">选择支付方式</td>
                    </tr>
                    <tr class="wfj11_015_choice wechat">
                        <td align="left"><img class="all_pay"
                                              src="<%=basePath%>/images/WFJClient/Newjspim/all_pay_03.png"/>
                        </td>
                        <td class="second" align="right"><img
                                src="<%=basePath%>/images/WFJClient/Newjspim/choice_01.png"
                                width="24" height="24" name="3"/>
                        </td>
                    </tr>
                    <tr class="wfj11_015_choice">
                        <td align="left"><img class="all_pay"
                                              src="<%=basePath%>/images/WFJClient/Newjspim/all_pay_01.png"/>
                        </td>
                        <td class="second" align="right"><img
                                src="<%=basePath%>/images/WFJClient/Newjspim/choice_02.png"
                                width="24" height="24" name="1"/>
                        </td>
                    </tr>



                        <tr class="wfj11_015_choice">
                            <td align="left"><img class="all_pay"
                                                  src="<%=basePath%>/images/WFJClient/Newjspim/xxzz.png"/>
                            </td>
                            <td class="second" align="right"><img
                                    src="<%=basePath%>/images/WFJClient/Newjspim/choice_02.png"
                                    width="24" height="24" name="4"/>
                            </td>
                        </tr>
                    <tr>
                        <td colspan="2" align="center">
                            <div id="paycommit"
                                 onclick="zf()">确认支付
                            </div>
                        </td>
                    </tr>

                </table>
            </div>
        </div>
    </div>

    <div id="occlusion2" class="jqmWindow jqmWindowcss1"></div>
</div>
<%--行业--%>
<div class="hyfl">
    <div class="div-header">
        <ul class="clearfix">
            <li>
                <img src="<%=basePath%>/images/scMobile/qyrz/arrow_left.gif" class="hyback"/>
            </li>
            <li>
                行业分类
            </li>
            <li>
                <!--添加新地址-->
            </li>
        </ul>
    </div>
    <div class="hyfl-content">
        <input type="text" style="display: none" id="selid"/>
        <p class="p-top" id="sel">
            请选择行业
        </p>
        <ul class="hy">
            <%--<li class="clearfix">--%>
                <%--<p>--%>
                    <%--机械工业加工联营平台--%>
                <%--</p>--%>
                <%--<p>--%>
                    <%--<img src="<%=basePath%>/images/scMobile/qyrz/a.png"/>--%>
                <%--</p>--%>
            <%--</li>--%>

        </ul>
    </div>
</div>
</div>
<!--注：style属性可以不用设置margin:0px，否则可能会展示不全-->
<iframe id="claim" src="<%=basePath%>/page/scMobile/qyrz/toRlNext.jsp" style="margin:0px;width:100%;height:100%;" hidden="hidden"></iframe>
</body>

<script type="text/javascript">
    var basePath = "<%=basePath%>";
    var companyname = "${param.companyname}";
    var goodsname = "${param.goodsname}";
    var  head = "${param.head}";
    var user1 = "${user}";
    var ppId = "${param.ppid}";
    var hd = "${param.hd}";
    $(".ul-list li").click(function(){
        $(this).parent(".ul-list").find("li").removeClass("active");
        $(this).addClass("active");
    })
    $("#weixinqr").hide();

    //提交认领
    $("#submitRl").click(function(){
        var industryType = $(".industryType").val();
        if(industryType== ""){
            prompt("请选择行业");
            return false;

        }

        var companyName = $(".companyName").val();
        if(companyName== ""){
            prompt("请填写准确企业名称");
            return false;

        }
        var shopname = $(".shopname").val();
        if(shopname== ""){
            prompt("请填写店铺名称");
            return false;

        }
        var companyPhone = $(".companyPhone").val();
        if(companyPhone== ""){
            prompt("请填写企业电话");
            return false;

        }

        var companyManager = $(".companyManager").val();
        if(companyManager== ""){
            prompt("请填写负责人姓名");
            return false;

        }
        var managertel = $(".managertel").val();
        if(managertel ==""){
            prompt("请填写负责人手机号");

            return false;
        }
        if(!ver_phone(managertel,"rz")){

            return false;
        }
        if(head=="show") {
            var valnum1 = $("#valnum1").val();
            if (!verCode1(valnum1)) {
                return false;
            }
        }
        if(token==1){
            return false;
        }
        //跳转到iframe页面
        showIframe();
    });

    //展示iframe页面内容，并传参
    function showIframe() {
        //隐藏父页面
        $("#submitClaim").hide();
        //展示子页面
        $("#claim").show();
        //给子页面iframe赋值
        //商家类型
        var selectedTypes = [];
        $('input:radio[name=comPro]:checked').each(function () {
            selectedTypes.push(this.value);
        });
        selectedTypes.forEach((value, index, array) => {
            $(window.frames[0].document).find("input:radio[value="+value+"]").attr('checked','true');
        });
        //公司名称
        $(window.frames[0].document).find("#companyName").val($("input[name='company.companyName']").val());
        //行业
        $(window.frames[0].document).find("#industryType").val($("input[name='company.industryType']").val());
        $(window.frames[0].document).find("#industryId").val($("input[name='company.industryId']").val());
        //姓名
        $(window.frames[0].document).find("#cresponsible").val($("input[name='cdl.companyManager']").val());
        //责任人电话
        $(window.frames[0].document).find("#responsibleTel").val($("input[name='cdl.managertel']").val());
        //店铺地址
        $(window.frames[0].document).find("#companyAddr").val($("input[name='company.addr']").val());
    }

    //关闭iframe页面，弹出支付页面
    function hideIframe() {
        //展示父页面
        $("#submitClaim").show();
        //隐藏子页面
        $("#claim").hide();
        //防止重复提交
        if(token==1){
            return false;
        }
        token = 1;
        //支付
        ajaxsut();
    }
</script>
</html>
