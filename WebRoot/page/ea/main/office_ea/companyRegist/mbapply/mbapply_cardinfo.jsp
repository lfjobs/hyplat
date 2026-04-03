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
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/css/ea/office_ea/mbapply/applycardinfo.css" />
    <script src="<%=basePath%>js/font-size.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>

    <!--日历插件-->
    <script src="<%=basePath%>js/Mdate/iScroll.js"></script>
    <script src="<%=basePath%>js/Mdate/Mdate.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/Mdate/needcss/Mdate.css">

    <script src="<%=basePath%>js/ea/office_ea/companyRegist/mpapply/mpapply_cardinfo.js"></script>
    <title>公司认证</title>
    <script type="text/javascript">

        var basePath = "<%=basePath%>";

        var out_request_no = "${applyParam.out_request_no}";
        var companyID = "${companyID}";
        var  staffID = "${staffID}";
        var id_card_valid_time = "${id_card_info.id_card_valid_time}";
    </script>
</head>

<body>
<header>
    <ul class="clearfix">
        <li>
            <a href="<%=basePath%>ea/merch/ea_getApplyLicense.jspa?applyParam.out_request_no=${applyParam.out_request_no}&companyID=${companyID}&staffID=${staffID}">

            <img src="<%=basePath%>images/ea/office/pbapply/img-1.png">
        </a>
        </li>
        <li>
            法人认证
        </li>
        <li>

        </li>
    </ul>
</header>
<div class="content">
    <div class="div-01">
        <section class="sec-nav">
            <ul class="clearfix" style="display: flex !important;overflow-x: scroll">
                <li >
                    商家认领
                </li>
                <c:if test="${applyParam.organization_type ne '2401'}">
                <li>
                    证件认证
                </li>
                </c:if>
                <li  class="active">
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
        <section class="sec-box clearfix">
            <div class="clearfix div-sf">
                <div class="clearfix div-sf">
                    <p>
                        身份证人像面:
                    </p>
                    <section class="sec-sfzsc2">
                        <input type="file" name="cardcopy" id="sdfFile1" value="" onchange="f_change1(this);" >
                        <img alt="图片" class="notnull" data="身份证人像面" src="<%=basePath%>${id_card_info.id_card_copy}"  onerror="this.src='<%=basePath%>images/ea/office/pbapply/img_007.png'"  id="imgSdf1">
                    </section>
                </div>
                <p>
                    身份证国徽面:
                </p>
                <section class="sec-sfzsc1">
                    <input type="file" name="cardnational" id="sdfFile" value="" onchange="f_change(this);" >
                    <img alt="图片" class="notnull" data="身份证国徽面" src="<%=basePath%>${id_card_info.id_card_national}"  onerror="this.src='<%=basePath%>images/ea/office/pbapply/img_006.png'" id="imgSdf">
                </section>
            </div>

        </section>

        <p class="p-01">
            <label class="lab-xm" for="">姓    名</label>
            <input type="text"  class="notnull" data="姓名"  name="id_card_info.id_card_name"  value="${id_card_info.id_card_name}" id="id_card_name"/>
        </p>
        <p class="p-01">
            <label for="">身份证号码</label>
            <input type="text" class="notnull" data="身份证号码" name="id_card_info.id_card_number"  value="${id_card_info.id_card_number}" id="id_card_number" />
        </p>
            <p class="p-01">
                <label for="">身份证住址</label>
                <input type="text" class="notnull" data="身份证住址" name="id_card_info.id_card_address"  value="${id_card_info.id_card_address}" id="id_card_address" />
            </p>
            <section class="sec-qixian clearfix">
                <p>有效期开始时间</p>
                <div class="clearfix" id="date1">
                    <input type="text"  name="id_card_info.id_card_valid_time_begin"  value="${id_card_info.id_card_valid_time_begin}" id="dateSelectorOne" data-year="2021" data-month="01" data-day="12" readonly class="notnull" data="有效期开始时间"  >
                    <label for="dateSelectorOne">
                        <img src="<%=basePath%>images/ea/office/pbapply/img_016.png">
                    </label>
                </div>

            </section>
        <section class="sec-qixian clearfix">
            <p>有效期截止时间</p>
            <div class="clearfix" id="date2">
                <input type="text"  name="id_card_info.id_card_valid_time"  id="dateSelectorTwo" data-year="2021" data-month="01" data-day="12" readonly class="notnull" data="有效期截止时间">
                <label for="dateSelectorTwo">
                    <img src="<%=basePath%>images/ea/office/pbapply/img_016.png">
                </label>
            </div>
            <section class="div-changqi">
                <div class="check-wrap clearfix">
                    <input type="checkbox" class="icheck" id="checkbox1" name="check" value="1" />
                    <label for="checkbox1" class="ilabel" id="che1"></label>
                    <p>长期</p>
                </div>
            </section>
        </section>

        <div class="div-p-02" style="display:none">
            <p>提示</p>
        </div>

    <section class="sec-footer clearfix">

        <p class="p-s">
            <a href="<%=basePath%>ea/merch/ea_getApplyLicense.jspa?applyParam.out_request_no=${applyParam.out_request_no}&companyID=${companyID}&staffID=${staffID}"  style="color:#fff;">上一步</a>

        </p>
        <p id="next1" class="p-x">
            确认
        </p>
        <input type="submit" name="submit" style="display:none;"/>
        <input type="hidden" name="companyID"   value="${companyID}"/>
        <input type="hidden" name="staffID"   value="${staffID}"/>
        <input type="hidden" name="applyParam.out_request_no" value="${applyParam.out_request_no}"/>
        <input type="hidden" name="mode"   value="id_card_info"/>
        <input type="hidden" name="id_card_info.id_card_copy"  value="${id_card_info.id_card_copy}" />
        <input type="hidden" name="id_card_info.id_card_national"  value="${id_card_info.id_card_national}" />
        <input type="hidden" name="id_card_info.cardId"  value="${id_card_info.cardId}" />
        <input type="hidden" name="id_card_info.cardKey"  value="${id_card_info.cardKey}" />

        <iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
    </section>
    </form>
    </div>


</body>
<script src="<%=basePath%>js/less.js" type="text/javascript" charset="utf-8"></script>

<script type="text/javascript">
//    //日历
new Mdate("dateSelectorOne", {
    //"dateShowBtn"为你点击触发Mdate的id，必填项
    acceptId: "dateSelectorOne",
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
    var myDate = new Date();
    var day = myDate.getDate();
    var month = myDate.getMonth() + 1;
    if(myDate.getMonth() < 10) {
        month = "0" + (myDate.getMonth() + 1);
    }
    var day = myDate.getDate();
    if(myDate.getDate() < 10) {
        day = "0" + myDate.getDate();
    }
    var datew = myDate.getFullYear() + "-" + month + "-" + day;
    var datew = datew.toString();


    $(".div-datatime").click(function() {
        $(this).hide();
    });



</script>
<script>


    //.checkbox1
    $("#che1").click(function () {
        if($("#checkbox1").is(":checked")){
            $("#date2").show();
        }else{
            $("#date2").hide();
        }
    })
</script>
</html>