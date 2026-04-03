<%@ page import="hy.ea.bo.human.Staff" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="hy.ea.bo.human.Staff" %>
<%@ page import="hy.ea.bo.Company" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    Staff staff = (Staff)session.getAttribute("key_staff");
    String staffName = staff.getStaffName();
    String staffId = staff.getStaffID();
    Company company = (Company)session.getAttribute("com");
    String ccomID = (String) session.getAttribute("ccomID");

%>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <title>报名成功</title>
    <script type="text/javascript" src="<%=basePath %>st/js/font-size.js"></script>
    <link type="text/css" rel="stylesheet" href="<%=basePath %>st/css/new_style.css">
    <script type="text/javascript" src="<%=basePath %>st/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>st/js/new-page.js"></script>
    <script type="text/javascript" src="<%=basePath %>js/ea/production/cprocedure/jquery.charfirst.pinyin.js"></script>
</head>
<body>

<header>
    <ul>
        <li style="width: 10%;">
            <a href="javascript:history.go(-1)"><img src="<%=basePath %>st/images/left_jt.png"></a>
        </li>
        <li style="width: 80%;"><%=company.getCompanyName()%></li>
        <a href="<%=basePath %>/ea/wfjshop/ea_getWFJshops.jspa" id="ret"><li style="width: 10%;">完成</li></a>
    </ul>
</header>
<div class="content_hidden">
    <div class="content">
        <div class="con">
            <form name="saveAccount" id="saveAccount" method="post">
                <input type="submit" name="submit" style="display: none" />
            <div class="shop_div">
                <div class="shop_top">
                    <h1>购买成功</h1>
                    <p>恭喜”<%=staffName%>“女士/先生报名成功</p>
                    <p>
                        我们将根据您购买的产品，分配移动办公账号 <br>
                        请记下账号与密码
                    </p>
                </div>
                <%--<div class="shop_end">--%>
                    <%--<div class="shop_grd">--%>
                        <%--<div class="shop_end_left">--%>
                            <%--<p>组织机构名称:</p>--%>
                        <%--</div>--%>
                        <%--<div class="shop_end_right">--%>
                            <%--<p><%=company.getCompanyIdentifier()%></p>--%>
                        <%--</div>--%>
                        <%--<div class="clearfix"></div>--%>
                    <%--</div>--%>
                    <%--<div class="shop_grd">--%>
                        <%--<div class="shop_end_left">--%>
                            <%--<p>用户名:</p>--%>
                        <%--</div>--%>
                        <%--<div class="shop_end_right">--%>
                            <%--<p id="name"></p>--%>
                        <%--</div>--%>
                        <%--<div class="clearfix"></div>--%>
                    <%--</div>--%>
                    <%--<div class="shop_grd">--%>
                        <%--<div class="shop_end_left">--%>
                            <%--<p>初始密码:</p>--%>
                        <%--</div>--%>
                        <%--<div class="shop_end_right">--%>
                            <%--<p>123456</p>--%>
                        <%--</div>--%>
                        <%--<div class="clearfix"></div>--%>
                    <%--</div>--%>
                    <%--<div class="clearfix"></div>--%>
                <%--</div>--%>
            </div>
                <input type="hidden" id="companyIdentifier" name="companyIdentifier" value="<%=company.getCompanyIdentifier()%>"/>
                <input type="hidden" id="accountEmail" name="account.accountEmail" value="" />
                <input type="hidden" id="accountPassword" name="account.accountPassword" value="123456"/>
                <input type="hidden" name="account.staffID" value="<%=staffId%>" />
            </form>
            <%--<div class="shop_div2">--%>
                <%--<h5>用户操作步骤</h5>--%>
                <%--<p>用户可下载登录微分金APP 我办公 移动办公即可管理自己的信息</p>--%>
            <%--</div>--%>
        </div>
    </div>
</div>

<script type="text/javascript">
    var basePath = "<%=basePath %>";
    var staffId = "<%=staffId%>"
    var companyID = "<%=company.getCompanyID() %>";
    $(document).ready(function(e) {
        var account = "<%=staffName%>";
        var AC = makePy(account);
//        if(AC.length>1){
//            AC = AC[0];
//        }
        $("#accountEmail").val(AC[0].toLowerCase());
        $(".shop_end_right #name").text(AC[0].toLowerCase());
        //保存学员信息
        $.ajax({
            url: basePath+"/st/enroll/sajax_ea_saveAccount.jspa",
            type: "post",
            data:$("#saveAccount").serialize(),
            async: false,
            success : function (data) {

            }
        })
        //增加个人往来为学员
        $.ajax({
            url: basePath+"/st/enroll/sajax_ea_addRelation.jspa?staffId="+staffId+"&companyID="+companyID,
            type: "post",
            async: false,
            success : function (data) {

            }
        })
//        $.ajax({
//            url: basePath+"/st/enroll/sajax_ea_getCcom.jspa?companyID="+companyID,
//            type: "post",
//            async: false,
//            success : function (data) {
//                var json = eval('(' + data + ')');
//                ccom = json;
//            }
//        })


    })
</script>

<script>
    $(document).ready(function(){
        $("header").css("height",$(window).height()*0.08-1+"px");
        $("header").css("line-height",$(window).height()*0.08-1+"px");
        $(".content_hidden").attr("style",";overflow: hidden;"+"height:"+$(window).height()*0.92+"px");
        $(".content").attr("style","overflow: hidden;"+"height:"+$(window).height()*0.92+"px");
        $(".head_top").css("height",$(window).height()*0.08-1+"px");
        $(".head_top ul li").css("line-height",$(window).height()*0.05+"px");
        $(".head_top ul li:nth-child(1) dl").css("margin",$(window).height()*0.015+"px");
        $(".head_top ul li:nth-child(2) input").attr("style","margin:"+$(window).height()*0.015+"px;margin-left:0;line-height:"+$(window).height()*0.05+"px;");
        $(".con").css("height",$(window).height()*0.92+"px");

        /*搜索*/
        $(".shop_div .check").click(function(){
            $(".alert_").show();
            $(".no_order_alert").show();
        });
        $(".alert_").click(function(){
            $(".alert_").hide();
            $(".no_order_alert").hide();
        });
        $(".no_order_alert .delete").click(function(){
            $(".alert_").hide();
            $(".no_order_alert").hide();
        });

        //app返回
        var u = window.navigator.userAgent;
        var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
        var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端

        try {
            if (isAndroid == true) {
                var obj = document.getElementById("ret");
                obj.setAttribute("href", "#");
                obj.setAttribute("onclick", "retAndroid()");
                Android.speechOutputForAndroid("购买成功");
            } else if (isiOS == true) {
                var obj = document.getElementById("ret");
                obj.setAttribute("href", "#");
                obj.setAttribute("onclick", "retIOS()");
                console.log("声音提醒开发中");
            }
        } catch (e) {
            console.log("报错啦");
        }



    });
    //安卓，苹果返回
    function retAndroid(){
        try{
            Android.finishWeb();
        }catch(err){
            $("#ret").removeAttr("onclick");
            $("#ret").attr("onclick","javascript: window.history.go(-1);return false;");
            $("#ret").trigger('click');
        }
    }
</script>
<script>

</script>

</body>
</html>