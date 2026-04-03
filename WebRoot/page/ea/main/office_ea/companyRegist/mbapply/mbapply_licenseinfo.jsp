<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>

<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css" />
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/office_ea/mbapply/applylicenseinfo.css" />
    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/Mdate/needcss/Mdate.css">
    <script src="<%=basePath%>js/font-size.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/Mdate/iScroll.js"></script>
    <script src="<%=basePath%>js/Mdate/Mdate.js"></script>
    <script src="<%=basePath%>js/ea/office_ea/companyRegist/mpapply/mpapply_licenseinfo.js"></script>
    <title>公司认证</title>
    <script type="text/javascript">

        var basePath = "<%=basePath%>";
        var out_request_no = "${applyParam.out_request_no}";
        var companyID = "${companyID}";
        var staffID = "${staffID}";
        var business_time = "${business_license_info.business_time}";
        var organization_time =  "${organization_cert_info.organization_time}";
        var business_license_number = "${business_license_info.business_license_number}";
    </script>
</head>

<body>
<header>
    <ul class="clearfix">
        <li>
            <a href="<%=basePath%>ea/merch/ea_getApplydp.jspa?organization_type=${applyParam.organization_type}&companyID=${companyID}&out_request_no=${applyParam.out_request_no}&staffID=${staffID}">
            <img src="<%=basePath%>images/ea/office/pbapply/img-1.png">
            </a>
        </li>
        <li>
            证件认证
        </li>
        <li>

        </li>
    </ul>
</header>
<style>
    .content .sec-nav ul li {
        /*padding-left: 1rem !important;*/
    }
</style>
<div class="content">
    <section class="sec-nav">
        <ul class="clearfix" style="display: flex !important;overflow-x: scroll">
            <li>
                商家认领
            </li>
            <li class="active">
                证件认证
            </li>
            <li>
                法人认证
            </li>
            <li>
                元素代码
            </li>
            <li>
                银行卡管理
            </li>
            <li>
                集团管理
            </li>
        </ul>
    </section>
    <form name="form1" id="form1" method="post" enctype="multipart/form-data">
    <%--<section class="sec-01">--%>
        <%--<p class="p-01">营业执照类型</p>--%>
        <%--<div class="div-radio">--%>
            <%--<label>--%>
                <%--<input type="radio" checked name="1"  class="a-radio">--%>
                <%--<span class="b-radio"></span>三证合一--%>
            <%--</label>--%>
            <%--<label>--%>
                <%--<input type="radio" name="1"  class="a-radio">--%>
                <%--<span class="b-radio"></span>普通营业执照--%>
            <%--</label>--%>
        <%--</div>--%>
        <%--&lt;%&ndash;<p class="p-02">&ndash;%&gt;--%>
            <%--&lt;%&ndash;将企业依次申请的工商营业执照、组织机构代码证和税务登记证三证合 为一证。&ndash;%&gt;--%>
        <%--&lt;%&ndash;</p>&ndash;%&gt;--%>
    <section class="sec-04 clearfix">
        <label for="">商家编号</label>
        <input type="text" class="notnull"  data="商家编号" name="companyCode" value="${contactCompany.companyCode}"/>
    </section>
    <section class="sec-04 clearfix">
        <label for="">店铺名称</label>
        <input type="text"  data="店铺名称" class="notnull" name="sales_scene_info.store_name"/>
    </section>
    <section class="sec-04 clearfix">
        <label for="">证件编号</label>
        <input type="text" class="notnull"  data="证件编号" name="business_license_info.business_license_number"/>
    </section>
    <section class="sec-04 clearfix">
        <label for="">证件名称</label>
        <input type="text"  class="notnull"  data="证件名称" class="notnull" name="business_license_info.business_license_name"/>
    </section>
    <section class="sec-04 clearfix">
        <label for="">经营范围</label>
        <input type="text" placeholder="经营的城市或地址"  data="经营范围" name="business_license_info.business_scope"/>
    </section>
    <section class="sec-qixian clearfix">
        <p>有效时间</p>
        <input type="hidden" class="final" name="business_license_info.business_time" id="bustime" value="${business_license_info.business_time}" />
        <div class="clearfix">
            <input type="text" id="dateSelectorOne" class="notnull" data="开始营业日期">
            <label for="dateSelectorOne">
                <img src="<%=basePath%>images/ea/office/pbapply/img_016.png" >
            </label>
        </div>
        <div class="clearfix" id="date2">
            <input type="text" id="dateSelectorTwo" class="notnull" data="停止营业日期">
            <label for="dateSelectorTwo">
                <img src="<%=basePath%>images/ea/office/pbapply/img_016.png" >
            </label>
        </div>
        <section class="div-changqi clearfix">
            <div class="check-wrap">
                <input type="checkbox" class="icheck" id="checkbox1" name="check" value="长期" />
                <label for="checkbox1" class="ilabel" id="che1"></label>
                <p>长期</p>
            </div>
        </section>
    </section>
    <section class="sec-04 clearfix">
        <label for="">年审管理</label>
        <input type="text" data="年审管理" placeholder="年审说明" name="business_license_info.companyName"/>
    </section>
    <section class="sec-04 clearfix">
        <label for="">发证单位</label>
        <input type="text" placeholder="发证机构名称" data="发证单位" name="business_license_info.companyName"/>
    </section>
    <section class="sec-02">
        <p>营业执照照片</p>
        <div class="clearfix">
            <div class="left">
                请上传2M内的彩色图片，格式可为bmp、png、jpeg、 jpg或gif。
            </div>
            <div class="right">
                <input type="file" name="licensecopy" id="sdfFile" value="" onchange="f_change(this);" >
                <img alt="图片" class="notnull" data="营业执照照片" src="<%=basePath%>${business_license_info.business_license_copy}" alt="" onerror="this.src='<%=basePath%>images/ea/office/pbapply/img_014.png'" id="imgSdf">
            </div>
        </div>
    </section>
    <section class="sec-04 clearfix">
        <label for="">负责人</label>
        <input type="text"  data="负责人" name="business_license_info.legal_person" value="${contactCompany.cresponsible}"/>
    </section>
    <section class="sec-04 clearfix">
        <label for="">审核人</label>
        <input type="text"  data="审核人" name="business_license_info.companyName"/>
    </section>

    <%--<section class="sec-03 sec-show clearfix">--%>
        <%--<p class="left">--%>
            <%--组织机构代码证--%>
        <%--</p>--%>
        <%--<div class="right">--%>
            <%--<input type="file" name="orgcopy" id="sdfFile1" value="" onchange="f_change1(this);" >--%>
            <%--<img alt="图片" class="notnull" data="组织机构代码证" src="<%=basePath%>${organization_cert_info.organization_copy}"  onerror="this.src='<%=basePath%>images/ea/office/pbapply/img_015.png'"id="imgSdf1">--%>
        <%--</div>--%>
    <%--</section>--%>
    <%--<section class="sec-04 sec-show clearfix">--%>
        <%--<label for="">组织机构代码</label>--%>
        <%--<input type="text" placeholder=""  class="notnull" data="组织机构代码" name="organization_cert_info.organization_number" value="${organization_cert_info.organization_number}"/>--%>
    <%--</section>--%>
    <%--<section class="sec-qixian sec-show clearfix">--%>
        <%--<p>有效期限</p>--%>
        <%--<input type="hidden" class="final" name="organization_cert_info.organization_time" value="${organization_cert_info.organization_time}" id="orgtime"/>--%>
        <%--<div class="clearfix">--%>
            <%--<input type="text" id="dateSelector1" class="notnull" data="组织机构代码开始时间">--%>
            <%--<label for="dateSelector1">--%>
                <%--<img src="<%=basePath%>images/ea/office/pbapply/img_016.png">--%>
            <%--</label>--%>
        <%--</div>--%>
        <%--<div class="clearfix" id="date3">--%>
            <%--<input type="text" id="dateSelector2" class="notnull" data="组织机构代码截止时间">--%>
            <%--<label for="dateSelector2">--%>
                <%--<img src="<%=basePath%>images/ea/office/pbapply/img_016.png">--%>
            <%--</label>--%>
        <%--</div>--%>
        <%--<section class="div-changqi clearfix">--%>
            <%--<div class="check-wrap">--%>
                <%--<input type="checkbox" class="icheck" id="checkbox2" name="check2" value="长期" />--%>
                <%--<label for="checkbox2" class="ilabel" id="che2"></label>--%>
                <%--<p>长期</p>--%>
            <%--</div>--%>
        <%--</section>--%>
    <%--</section>--%>
        <div class="div-p-02" style="display: none;">
            <p>错误提示</p>
        </div>
    <section class="sec-footer clearfix">
        <p class="p-s">
            <a href="<%=basePath%>ea/merch/ea_getApplydp.jspa?organization_type=${applyParam.organization_type}&companyID=${companyID}&out_request_no=${applyParam.out_request_no}&staffID=${staffID}"  style="color:#fff;">上一步</a>
        </p>
        <p id="next1" class="p-x">
            下一步
        </p>
        <input type="submit" name="submit" style="display:none;"/>
        <input type="hidden" name="companyID"   value="${companyID}"/>
        <input type="hidden" name="staffID"   value="${staffID}"/>
        <input type="hidden" name="applyParam.out_request_no" value="${applyParam.out_request_no}"/>
        <input type="hidden" name="mode"   value="business_license_info"/>
        <input type="hidden" name="business_license_info.bliID"  value="${business_license_info.bliID}" />
        <input type="hidden" name="business_license_info.bliKey"  value="${business_license_info.bliKey}" />
        <input type="hidden" name="business_license_info.business_license_copy"  value="${business_license_info.business_license_copy}" />
        <input type="hidden" name="organization_cert_info.organization_copy"  value="${organization_cert_info.organization_copy}" />
        <input type="hidden" name="organization_cert_info.ociID"  value="${organization_cert_info.ociID}" />
        <input type="hidden" name="organization_cert_info.ociKey"  value="${organization_cert_info.ociKey}" />
        <iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
    </section>
    </form>
</div>
</body>
<script type="text/javascript">
    $(".sec-01 .div-radio label").click(function () {
        $(".div-p-02").hide();
        if($(".sec-01 .div-radio label:first-of-type input").is(":checked")){
            $(".sec-show").hide();
        }else{
            $(".sec-show").show();
        }
    })
    //日历
    new Mdate("dateSelectorOne", {
        //"dateShowBtn"为你点击触发Mdate的id，必填项
        acceptId: "dateSelectorOne",
        //此项为你要显示选择后的日期的input，不填写默认为上一行的"dateShowBtn"
        beginYear: "2000",
        //此项为Mdate的初始年份，不填写默认为2000
        beginMonth: "1",
        //此项为Mdate的初始月份，不填写默认为1
        beginDay: "1",
        //此项为Mdate的初始日期，不填写默认为1
        endYear: "2040",
        //此项为Mdate的结束年份，不填写默认为当年
        endMonth: "12",
        //此项为Mdate的结束月份，不填写默认为当月
        endDay: "31",
        //此项为Mdate的结束日期，不填写默认为当天
        format: "-"
        //此项为Mdate需要显示的格式，可填写"/"或"-"或".",不填写默认为年月日
    })
    new Mdate("dateSelectorTwo", {
        //"dateShowBtn"为你点击触发Mdate的id，必填项
        acceptId: "dateSelectorTwo",
        //此项为你要显示选择后的日期的input，不填写默认为上一行的"dateShowBtn"
        beginYear: "2020",
        //此项为Mdate的初始年份，不填写默认为2000
        beginMonth: "1",
        //此项为Mdate的初始月份，不填写默认为1
        beginDay: "1",
        //此项为Mdate的初始日期，不填写默认为1
        endYear: "2040",
        //此项为Mdate的结束年份，不填写默认为当年
        endMonth: "12",
        //此项为Mdate的结束月份，不填写默认为当月
        endDay: "31",
        //此项为Mdate的结束日期，不填写默认为当天
        format: "-"
        //此项为Mdate需要显示的格式，可填写"/"或"-"或".",不填写默认为年月日
    })
    new Mdate("dateSelector1", {
        //"dateShowBtn"为你点击触发Mdate的id，必填项
        acceptId: "dateSelector1",
        //此项为你要显示选择后的日期的input，不填写默认为上一行的"dateShowBtn"
        beginYear: "2000",
        //此项为Mdate的初始年份，不填写默认为2000
        beginMonth: "1",
        //此项为Mdate的初始月份，不填写默认为1
        beginDay: "1",
        //此项为Mdate的初始日期，不填写默认为1
        endYear: "2040",
        //此项为Mdate的结束年份，不填写默认为当年
        endMonth: "12",
        //此项为Mdate的结束月份，不填写默认为当月
        endDay: "31",
        //此项为Mdate的结束日期，不填写默认为当天
        format: "-"
        //此项为Mdate需要显示的格式，可填写"/"或"-"或".",不填写默认为年月日
    })
    new Mdate("dateSelector2", {
        //"dateShowBtn"为你点击触发Mdate的id，必填项
        acceptId: "dateSelector2",
        //此项为你要显示选择后的日期的input，不填写默认为上一行的"dateShowBtn"
        beginYear: "2020",
        //此项为Mdate的初始年份，不填写默认为2000
        beginMonth: "1",
        //此项为Mdate的初始月份，不填写默认为1
        beginDay: "1",
        //此项为Mdate的初始日期，不填写默认为1
        endYear: "2040",
        //此项为Mdate的结束年份，不填写默认为当年
        endMonth: "12",
        //此项为Mdate的结束月份，不填写默认为当月
        endDay: "31",
        //此项为Mdate的结束日期，不填写默认为当天
        format: "-"
        //此项为Mdate需要显示的格式，可填写"/"或"-"或".",不填写默认为年月日
    })
//    var myDate = new Date();
//    var day = myDate.getDate();
//    var month = myDate.getMonth() + 1;
//    if(myDate.getMonth() < 10) {
//        month = "0" + (myDate.getMonth() + 1);
//    }
//    var day = myDate.getDate();
//    if(myDate.getDate() < 10) {
//        day = "0" + myDate.getDate();
//    }
//    var datew = myDate.getFullYear() + "-" + month + "-" + day;
//    var datew = datew.toString();
    //把当前日期放到默认显示
    //data-year="2020" data-month="1" data-day="1"
//    $("#dateSelectorOne").val(datew);
//    $("#dateSelectorOne").attr("data-year", myDate.getFullYear());
//    $("#dateSelectorOne").attr("data-month", month);
//    $("#dateSelectorOne").attr("data-day", day);
//    $("#dateSelectorTwo").val(datew);
//    $("#dateSelectorTwo").attr("data-year", myDate.getFullYear());
//    $("#dateSelectorTwo").attr("data-month", month);
//    $("#dateSelectorTwo").attr("data-day", day);
//    $("#dateSelector1").val(datew);
//    $("#dateSelector1").attr("data-year", myDate.getFullYear());
//    $("#dateSelector1").attr("data-month", month);
//    $("#dateSelector1").attr("data-day", day);
//    $("#dateSelector2").val(datew);
//    $("#dateSelector2").attr("data-year", myDate.getFullYear());
//    $("#dateSelector2").attr("data-month", month);
//    $("#dateSelector2").attr("data-day", day);
    $("#dateSelectorOne").click(function () {
        $("#dateSelectorOne").trigger("blur");
    });
    $("#dateSelectorTwo").click(function () {
        $("#dateSelectorTwo").trigger("blur");
    });
    //.checkbox1
    $("#che1").click(function () {
        if($("#checkbox1").is(":checked")){
            $("#date2").show();
        }else{
            $("#date2").hide();
        }
        $(this).parents(".sec-qixian").find(".final").val($(this).val());
    })
    $("#che2").click(function () {
        if($("#checkbox2").is(":checked")){
            $("#date3").show();
        }else{
            $("#date3").hide();
        }
    })
</script>
</html>