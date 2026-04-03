<%@ page import="com.alipay.util.UtilDate" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    //生成当前时间，精确到毫秒
    Date date = new Date();
    DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    String companyCode = String.format("SJ%s", df.format(date));
%>
<html>

<head>
    <meta charset="utf-8"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/office_ea/mbapply/applydp.css"/>
    <script src="<%=basePath%>js/font-size.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <!--下拉框插件-->
    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/swiper/css/public.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/swiper/css/swiper.min.css"/>
    <script src="<%=basePath%>js/ea/office_ea/companyRegist/mpapply/mpapply_dp.js"></script>
    <title>商家认领</title>
    <script type="text/javascript">
        var basePath = "<%=basePath%>";
    </script>
</head>

<body>
<header>
    <ul class="clearfix">
        <li>
            <a onclick="javascript: window.history.go(-1);return false;" target="_self">
                <img src="<%=basePath%>images/ea/office/pbapply/img-1.png">
            </a>
        </li>
        <li>
            商家认领
        </li>
        <li>

        </li>
    </ul>
</header>
<div class="content">
    <form name="form1" id="form1" method="post" enctype="multipart/form-data">
        <section class="sec-con">
            <p class="p-01">
                <label class="" for="">商家编号</label>
                <input type="text" name="contactCompany.companyCode" value="<%=companyCode%>" class="notnull"
                       data="商家编号"/>
            </p>
            <p class="p-01">
                <label class="" for="">店铺名称</label>
                <input id="shopname" type="text" name="contactCompany.shopname" class="notnull" data="店铺名称"/>
                <input type=hidden name="contactCompany.companyName" id ="companyName">
            </p>
            <p class="p-01">
                <label for="">商户简称</label>
                <input type="text" name="merchant_shortname" data="商户简称"/>
            </p>
            <p class="p-01">
                <label for="">所属行业</label>
                <input type="text" id="industryType" name="contactCompany.industryType" class="industryType notnull"
                       data="所属行业"/>
                <input type="hidden" id="industryId" name="contactCompany.industryId" class="industryId"/>
            </p>
            <div class="p-011" style="padding: .625rem .375rem;
    border-bottom: .025rem solid #e9e9e9; display: flex; flex-direction: row; height: 1.2rem;
    font-size: .6rem;
    color: #222;">
                <label style="width: 30%;margin: auto">商家类型：</label>
                <div style="width: 100%">
                    <div style="width: 100%; display: flex; flex-direction: row">
                        <div style="width: 100%;">
                            <input style="-webkit-appearance: checkbox;" type="radio" name="contactCompany.comPro"
                                   value="2500"/>
                            <label style="">个人卖家</label>
                        </div>
                        <div style="width: 100%;">
                            <input style="-webkit-appearance: checkbox;" type="radio" name="contactCompany.comPro"
                                   value="2"/>
                            <label style="position: relative;left: 5.6px;">企业</label>
                        </div>
                        <div style="width: 100%;">
                            <input style="-webkit-appearance: checkbox;" type="radio" name="contactCompany.comPro"
                                   value="4"/>
                            <label style="">个体商户</label>
                        </div>
                    </div>

                    <div style="width: 100%; display: flex; flex-direction: row">
                        <div style="width: 100%;">
                            <input style="-webkit-appearance: checkbox;" type="radio" name="contactCompany.comPro"
                                   value="3"/>
                            <label style="">党政机关</label>
                        </div>
                        <div style="width: 100%;">
                            <input style="-webkit-appearance: checkbox;" type="radio" name="contactCompany.comPro"
                                   value="5"/>
                            <label style="">事业单位</label>
                        </div>
                        <div style="width: 100%;">
                            <input style="-webkit-appearance: checkbox;" type="radio" name="contactCompany.comPro"
                                   value="1708"/>
                            <label style="">其它组织</label>
                        </div>
                    </div>
                </div>
                <%--<section class="headNav">
                    <ul>
                        <li><p><a  href="javascript:nav('1','${param.docId}','${param.typee}')"><img src="<%=basePath%>images/ea/office/contract/selectp/dxseleted.png">集团内部</a></p></li>
                        <li><p><a href="javascript:nav('2','${param.docId}','${param.typee}')"><img src="<%=basePath%>images/ea/office/contract/selectp/dxwselet.png">往来公司</a></p></li>
                        <li><p><a href="javascript:nav('3','${param.docId}','${param.typee}')"><img src="<%=basePath%>images/ea/office/contract/selectp/dxwselet.png">往来个人</a></p></li>
                        <li><p><a href="javascript:nav('4','${param.docId}','${param.typee}')"><img src="<%=basePath%>images/ea/office/contract/selectp/dxwselet.png">最近联系</a></p></li>

                    </ul>
                </section>--%>
            </div>
            <section class="sec-03 clearfix p-01">
                <p class="left">
                    店铺logo
                </p>
                <div class="right">
                    <input type="file" name="logo" id="sdfFile1" value="" onchange="f_change1(this);">
                    <img alt="图片" src="<%=basePath%>${contactCompany.logoPath}" alt=""
                         onerror="this.src='<%=basePath%>images/ea/office/pbapply/img_015.png'" id="imgSdf1"
                         class="notnull" data="店铺logo">
                </div>
            </section>
            <p class="p-01">
                <label for="">责任人姓名</label>
                <input type="text" id="cresponsible" name="contactCompany.cresponsible" class="notnull"
                       data="店铺电话"/>
            </p>
            <p class="p-01">
                <label for="">责任人电话</label>
                <input type="text" id="responsibleTel" value="${contactCompany.companyTel}"
                       name="contactCompany.responsibleTel" class="notnull" data="店铺电话"
                       placeholder="手机号/座机010-11111111"/>
            </p>
            <p class="p-01">
                <label for="">店铺地址</label>
                <span><img src="<%=basePath%>images/scMobile/qyrz/img-003.png" class="d_r_site"></span>
                <input type="text" data="店铺地址" class="notnull d_r_site" name="contactCompany.companyAddr"
                       id="companyAddr"/>
            </p>
            <div class="div-bottom">
            </div>
        </section>
        <div class="div-p-02" style="display: none;">
            <p></p>
        </div>
        <section class="sec-footer clearfix">
            <p id="confirm">
                确认
            </p>
        </section>
        <input type="submit" name="submit" style="display:none;"/>
        <iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
    </form>
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

            </li>
        </ul>
    </div>
    <div class="hyfl-content">
        <input type="text" style="display: none" id="selid"/>
        <p class="p-top" id="sel">
            请选择行业
        </p>
        <ul class="hy">
        </ul>
    </div>
</div>
</body>
<script>
    //JS file 图片 即选即得 显示
    //创建一个FileReader对象
    var reader = new FileReader();

    function f_change1(file) {
        var img = document.getElementById('imgSdf1');
        //读取File对象的数据
        reader.onload = function (evt) {
            //data:img base64 编码数据显示
            img.width = "100";
            img.height = "100";
            img.src = evt.target.result;
        }
        reader.readAsDataURL(file.files[0]);
    }

    //确认
    $("#confirm").click(function () {
        var error = 0;
        $(".p-01 .notnull").each(function () {
            var tip = $(this).attr("data");
            if ($.trim($(this).val()) == "") {
                if (tip == "店铺logo") {
                    if ($(this).attr("src").indexOf("pbapply/img_015.png") != -1) {
                        $(".div-p-02").show();
                        $(".div-p-02 p").text(tip + "不能为空");
                        error = 1;
                        return false;
                    }
                } else {
                    $(".div-p-02").show();
                    $(".div-p-02 p").text(tip + "不能为空");
                    error = 1;
                    return false;
                }
            } else {
                if (tip == "责任人电话") {
                    if (!checkTel($.trim($(this).val()))) {
                        $(".div-p-02").show();
                        $(".div-p-02 p").text("请填写正确的电话号");
                        error = 1;
                        return false;
                    }
                }
            }
        });
        if (error == 0) {
            //保存公司
            $("#form1").attr("target", "hidden").attr("action", basePath + "/ea/company/sajax_n_ea_saveClaimCompanyInfo.jspa");
            token = 13;
            document.form1.submit.click();
            //调用父类支付购买方法
            parent.hideIframe();
        }
    })
</script>
</html>