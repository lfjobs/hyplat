<%@ page import="java.util.Date" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Map" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    Map<String, Object> paramMap = (Map<String, Object>) session.getAttribute("paramMap");

%>
<html>
<head>
    <meta charset="utf-8"/>
    <title>商品信息</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css"
          href="<%=basePath %>css/scMobile/payBudget/inventory/Mdate/needcss/Mdate.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/ea/finance/invoicing/rukuBill_good.css">
    <script type="text/javascript" charset="utf-8"
            src="<%=basePath %>js/pkModule/checkinv/checkInvList/jquery-2.1.1.min.js"></script>
    <!--下拉框插件-->
    <link rel="stylesheet" type="text/css" href="<%=basePath %>js/swiper/css/public.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>js/swiper/css/swiper.min.css"/>
    <script type="text/javascript" charset="utf-8" src="<%=basePath %>js/swiper/js/swiper.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath %>js/swiper/js/dySelect.js"></script>

    <script type="text/javascript" charset="utf-8"
            src="<%=basePath %>js/scMobile/payBudget/inventory/font-size.js"></script>
    <script type="text/javascript" charset="utf-8"
            src="<%=basePath %>js/scMobile/payBudget/inventory/Mdate/iScroll.js"></script>
    <script type="text/javascript" charset="utf-8"
            src="<%=basePath %>js/scMobile/payBudget/inventory/Mdate/Mdate.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath %>js/WFJClient/localforage.min.js"></script>
</head>
<body class="hy">
<header>
    <ul class="clearfix">
        <li onclick="toBack();">
            <img src="<%=basePath %>images/scMobile/payBudget/budgetList/ico-left.png"/>
        </li>
        <li>
            商品信息
        </li>
        <li>

        </li>
    </ul>
</header>

<div class="select_box select_box6"></div>
<div class="div-kc">
    <div class="box">
        <p>请选择库仓<span id="span-close"style="padding-left: 200px;">关闭</span></p>
        <div id="kc-load">
            <iframe marginwidth=0 marginheight=0 width="100%" id="ckiframe" style="height: 92vh" src=""
                    frameborder="no"></iframe>
        </div>
    </div>
</div>
<div class="content">
    <section class="con_co">
        <form id="goodForm" name="goodForm">
            <ul>
                <input id="ppID" type="hidden" name="ppID" value="${p.ppID}"/>
                <input id="goodsID" type="hidden" name="goodsID" value="${p.goodsID}"/>
                <input name="companyname" type="hidden"  value="${p.companyID}"/>
                <%--<li class="clearfix">
                    <p class="left_name"><span>*</span>批次</p>
                    <input type="text" name="" placeholder="自动获取编号" disabled id="" value="" />
                </li>--%>
                <li class="clearfix">
                    <p class="left_name"><span>*</span>生产日期</p>
                    <div id="shengchanriqi_xz">
                        <input id="youxiaoriqi_sj" type="text" placeholder="选择时间" readonly="readonly" name="manufactureDate"/>
                        <%--<img src="<%=basePath %>images/scMobile/payBudget/addBudget/wupinguanli_img_13.png"/>--%>
                        <label for="youxiaoriqi_sj">
                            <img src="<%=basePath %>images/scMobile/payBudget/addBudget/wupinguanli_img_13.png">
                        </label>
                    </div>
                </li>
                <li class="clearfix btn6">
                    <p class="left_name"><span>*</span>有效日期</p>
                    <div id="youxiaoriqi_xz">
                        <input id="codename" type="text" readonly="readonly" placeholder="选择时间" name="codename"
                               autocomplete="off"/>
                        <input id="codeid" type="hidden" name="codeid"/>
                        <img src="<%=basePath %>images/scMobile/payBudget/addBudget/wupinguanli_img_13.png"/>
                    </div>

                </li>
                <li class="clearfix">
                    <p class="left_name"><span>*</span>类别</p>
                    <input type="text" name="type" readonly="readonly" id="type" value="${p.type}" autocomplete="off"/>
                </li>
                <li class="clearfix">
                    <p class="left_name"><span>*</span>条形码</p>
                    <input type="text" name="barcode" readonly="readonly" id="barcode" value="${p.barCode}" autocomplete="off"/>
                </li>
                <li class="clearfix">
                    <p class="left_name"><span>*</span>品名名称</p>
                    <input type="text" name="goodsName" readonly="readonly" id="goodsName" value="${p.goodsName}"
                           autocomplete="off"/>
                </li>
                <li class="clearfix  li-div2">
                    <p class="left_name"><span>*</span>规格</p>
                    <div>
                        <input type="text" name="standard" readonly="readonly" id="standard" value="${p.standard}"
                               autocomplete="off"/>
                        <%--<img src="<%=basePath %>images/scMobile/payBudget/addBudget/wupinguanli_img_13.png"/>--%>
                    </div>
                </li>
                <li class="clearfix li-div2">
                    <p class="left_name"><span>*</span>单位</p>
                    <div>
                        <input type="text" name="variableID" readonly="readonly" id="variableID" value="${p.variableID}"
                               autocomplete="off"/>
                        <%--<img src="<%=basePath %>images/scMobile/payBudget/addBudget/wupinguanli_img_13.png"/>--%>
                    </div>
                </li>
                <li class="clearfix">
                    <p class="left_name"><span>*</span>送货数量</p>
                    <input type="text" name="fhnum" placeholder="请输入" id="fhnum" autocomplete="off"/>
                </li>
                <li class="clearfix">
                    <p class="left_name"><span>*</span>收货数量</p>
                    <input type="text" name="shnum" placeholder="请输入" id="shnum" autocomplete="off"/>
                </li>
                <li class="clearfix">
                    <p class="left_name"><span>*</span>验货数量</p>
                    <input type="text" name="yhnum" placeholder="请输入" id="yhnum" autocomplete="off"/>
                </li>
                <li class="clearfix">
                    <p class="left_name"><span>*</span>入库数量</p>
                    <input type="number" name="rknum" placeholder="请输入" id="rknum" autocomplete="off"/>
                </li>
                <li class="kf clearfix li-div2">
                    <p class="left_name"><span>*</span>库仓</p>
                    <div>
                        <input type="text" name="kfname" id="kfname" value="<c:if test="${depotid!=null}">销售库</c:if><c:if test="${depotid==null}">请选择</c:if>" autocomplete="off"/>
                        <input type="hidden" id="kfid" value="${depotid}" name="kfid"/>
                        <img src="<%=basePath %>images/scMobile/payBudget/addBudget/wupinguanli_img_13.png"/>
                    </div>
                </li>
                <%--<li class="clearfix li_left_l">
                    <p class="left_name"><span>*</span>往来公司（商家）</p>
                    <div>
                        <p>自动获取</p>
                        <img src="<%=basePath %>images/scMobile/payBudget/addBudget/wupinguanli_img_13.png"/>
                    </div>
                </li>
                <li class="clearfix li_left_l">
                    <p class="left_name"><span>*</span>往来个人（商家责任人）</p>
                    <div>
                        <p>自动获取</p>
                        <img src="<%=basePath %>images/scMobile/payBudget/addBudget/wupinguanli_img_13.png"/>
                    </div>
                </li>
                <li class="clearfix">
                    <p class="left_name"><span>*</span>入库人员</p>
                    <div>
                        <p>自动获取</p>
                        <img src="<%=basePath %>images/scMobile/payBudget/addBudget/wupinguanli_img_13.png"/>
                    </div>
                </li>--%>
                <%--<li class="clearfix li_left_l">
                    <p class="left_name"><span>*</span>审核状态：已审 未审 </p>
                    <input type="text" name="" placeholder="请输入" id="" value="" />
                </li>
                <li class="clearfix li_name">
                    <p class="left_name"><span>*</span>审核人</p>
                    <div>
                        <ul class="clearfix">
                            <li>
                                <p><img src="images/wupinguanli_img_20.png"/></p>
                                <p>
                                    张三
                                </p>
                            </li>
                            <li>
                                <p><img src="images/wupinguanli_img_20.png"/></p>
                                <p>
                                    李四
                                </p>
                            </li>
                            <li>
                                <p><img src="images/wupinguanli_img_20.png"/></p>
                                <p>
                                    王五
                                </p>
                            </li>
                        </ul>
                        <img src="images/wupinguanli_img_13.png"/>
                    </div>
                </li>--%>
            </ul>
        </form>
        <section class="btn_footer">
            <p>
                确定
            </p>
        </section>
    </section>

</div>
</body>
<script type="text/javascript">
    var basePath = "<%=basePath%>";
    var comid = "${param.companyid}";//公司id
    var staffid = "${param.staffid}";//登录人id
    var sccid = "${param.sccid}";//登录人id

    //新增物品、物品管理切换
    $(document).on("click", ".gl_tab li", function () {
        $(this).parent().children("li").removeClass("active");
        $(this).addClass("active");
        $("#tab_div>div").hide();
        $("#tab_div>div").eq($(this).index()).show();
    });
    //商品点击选中
    $(document).on("click", "aside", function () {
        if ($(this).is(".aside_yes")) {
            $(this).removeClass().addClass("aside_no");
        } else {
            $(this).removeClass().addClass("aside_yes");
        }
    });

    //日历
    var myDate = new Date();
    var day = myDate.getDate();
    var month = myDate.getMonth() + 1;
    if (month < 10) {
        month = "0" + month;
    }
    if (myDate.getDate() < 10) {
        day = "0" + myDate.getDate();
    }
    var datew = myDate.getFullYear() + "-" + month + "-" + day;
    var datew = datew.toString();
    //data-year="2020" data-month="1" data-day="1"
    $("#youxiaoriqi_sj").val(datew);
    $("#youxiaoriqi_sj").attr("data-year", myDate.getFullYear());
    $("#youxiaoriqi_sj").attr("data-month", month);
    $("#youxiaoriqi_sj").attr("data-day", day);
    //生产日期
    new Mdate("shengchanriqi_xz", {
        //"shengchanriqi"为你点击触发Mdate的id，必填项
        acceptId: "youxiaoriqi_sj",
        //此项为你要显示选择后的日期的input，不填写默认为上一行的"dateShowBtn"
        beginYear: "2010",
        //此项为Mdate的初始年份，不填写默认为2000
        beginMonth: "1",
        //此项为Mdate的初始月份，不填写默认为1
        beginDay: "1",
        //此项为Mdate的初始日期，不填写默认为1
        endYear: "2020",
        //此项为Mdate的结束年份，不填写默认为当年
        //endMonth: "1",
        //此项为Mdate的结束月份，不填写默认为当月
        //endDay: "1",
        //此项为Mdate的结束日期，不填写默认为当天
        format: "-"
        //此项为Mdate需要显示的格式，可填写"/"或"-"或".",不填写默认为年月日
    });
    $("#shengchanriqi_xz").click(function () {
        $("#shengchanriqi_xz").trigger("blur");
    });

    $(function () {

        $('.btn6').on('click', function () {
            $.ajax({
                type: "get",
                url: basePath + "ea/ruku/sajax_ea_getQualityGuaranteePeriod.jspa",
                async: false,
                dataType: "json",
                success: function (data) {
                    var member = eval("(" + data + ")");
                    var scodes = member.scode;
                    var s = new Array();
                    var d = new Array();
                    for (var i = 0; i < scodes.length; i++) {
                        var scode = scodes[i];
                        s[i] = scode.codeDesc;
                        d[i] = scode.codeID;
                    }
                    depSelectSwiper(s, d);
                }
            });
        });

        $(".btn_footer").click(function () {
            var reg = new RegExp(/^(?!0+(?:\.0+)?$)(?:[1-9]\d*|0)(?:\.\d{1,4})?$/);//四位小数
            if ($("#rknum").val().trim() == null || $("#rknum").val().trim() == "" || $("#rknum").val().trim() == 0) {
                alert("入库数量不能为空");
                return false;
            }else {
                if($("#yhnum").val().trim() == null || $("#yhnum").val().trim() == "" || $("#yhnum").val().trim() == 0){
                    $("#yhnum").val($("#rknum").val().trim());
                }
                if($("#shnum").val().trim() == null || $("#shnum").val().trim() == "" || $("#shnum").val().trim() == 0){
                    $("#shnum").val($("#rknum").val().trim());
                }
                if($("#fhnum").val().trim() == null || $("#fhnum").val().trim() == "" || $("#fhnum").val().trim() == 0){
                    $("#fhnum").val($("#shnum").val().trim());
                }
                if (!reg.test($("#rknum").val().trim())) {
                    alert("请确保数据有效！");
                    $("#rknum").val(0);
                    return false;
                }else if (!reg.test($("#shnum").val().trim())) {
                    alert("请确保数据有效！");
                    $("#shnum").val(0);
                    return false;
                }else if (!reg.test($("#yhnum").val().trim())) {
                    alert("请确保数据有效！");
                    $("#yhnum").val(0);
                    return false;
                }else if (!reg.test($("#fhnum").val().trim())) {
                    alert("请确保数据有效！");
                    $("#fhnum").val(0);
                    return false;
                }else{
                    var serializeObj = {};
                    serializeObj = $("#goodForm").parseForm();
                    localforage.getItem('serializeJson').then(function (value) {
                        //当离线仓库中的值被载入时，此处代码运行
                        console.log(value);
                        console.log(serializeObj);
                        var serializeJson = [];
                        if (value != null && value != "") {
                            serializeJson = value;
                        }
                        //serializeJson.push({"id":serializeObj.ppid,"goods":serializeObj});
                        serializeJson.push(serializeObj);
                        localforage.setItem('serializeJson', serializeJson).then(function (value) {
                            console.log(value);
                            //window.history.go(-1);
                            localforage.removeItem('params').then(function() {
                                // 当值被移除后，此处代码运行
                                console.log('Key is cleared!');
                            }).catch(function(err) {
                                // 当出错时，此处代码运行
                                console.log(err);
                            });

                            window.location.href = basePath + "page/ea/main/finance/invoicing/rukuBill_add.jsp?companyid=" + comid + "&staffid=" + staffid + "&sccid=" + sccid;

                        }).catch(function (err) {
                            // 当出错时，此处代码运行
                            console.log(err);
                        });
                    }).catch(function (err) {
                        // 当出错时，此处代码运行
                        console.log(err);
                    });
                }
            }
        });

        $(".kf").click(function () {
            $("#ckiframe").attr("src", "/ea/initialize/ea_getListDepotmanageByPID.jspa?depotID=001&sort=2&compayid=" + comid);
            $(".div-kc").show();
        });
        $("#span-close").click(function () {
            $(".div-kc").hide();
        });

        localforage.getItem('params').then(function (value) {
            //当离线仓库中的值被载入时，此处代码运行
            console.log(value);
            if (value != null && value != "") {
                $('#fhnum').val(value.fhnum);
                $("#rknum").val(value.weight);
                $("#yhnum").val(value.weight);
                $("#shnum").val(value.weight);
            }else{
                localforage.getItem('hiddenform').then(function (value) {
                    //当离线仓库中的值被载入时，此处代码运行
                    console.log(value);
                    if (value != null && value != "") {
                        var goods=value.goods;
                        $.each(goods, function (index, item) {
                            if (item != null && item.pid == $("#ppID").val()) {
                                $('#fhnum').val(item.gqnum);
                                return false;
                            }
                        });
                    }
                }).catch(function (err) {
                    // 当出错时，此处代码运行
                    console.log(err);
                });
            }
        }).catch(function (err) {
            // 当出错时，此处代码运行
            console.log(err);
        });
    });
    function depSelectSwiper(s, d) {
        var hgS6 = new selectSwiper({
            el: '.select_box6',
            data: s,
            init: function (index) {
                if (index !== -1) {
                    $('.btn1').html(this.data[index]);
                }
            },
            okFunUndefind: function () {
                alert('必须选择一项');
                return false;
            },
            okFun: function (index) {
                console.log(index);
                $('#codename').val(this.data[index]);
                $('#codeid').val(d[index]);
                $("body").removeClass("body_yc");
            },
            closeFun: function () {
                console.log('取消');
                $("body").removeClass("body_yc");
            },
        });
        hgS6.openSelectSwiper();
        $("body").addClass("body_yc");
    }

    //扩展jquery的格式化方法
    $.fn.parseForm = function () {
        var serializeObj = {};
        var array = this.serializeArray();
        var str = this.serialize();
        $(array).each(function () {
            if (serializeObj[this.name]) {
                if ($.isArray(serializeObj[this.name])) {
                    serializeObj[this.name].push(this.value);
                } else {
                    serializeObj[this.name] = [serializeObj[this.name], this.value];
                }
            } else {
                serializeObj[this.name] = this.value;
            }
        });
        return serializeObj;
    };

    function toBack() {
        localforage.removeItem('params').then(function() {
            // 当值被移除后，此处代码运行
            console.log('Key is cleared!');
        }).catch(function(err) {
            // 当出错时，此处代码运行
            console.log(err);
        });
        try {
            if (isAndroid == true) {
                console.log("安卓");
                Android.callAndroidjianli();//调用安卓接口
            } else if (isiOS == true) {
                console.log("IOS");
                var url = "func=" + 'callIOSjianli';
                window.webkit.messageHandlers.Native.postMessage(url);
            }
        } catch (error) {
            window.history.go(-1);
            return false;
        }
    }
</script>
</html>

