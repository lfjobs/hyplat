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
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/office_ea/mbapply/applydp.css" />
    <script src="<%=basePath%>js/font-size.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <!--下拉框插件-->
    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/swiper/css/public.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/swiper/css/swiper.min.css"/>
    <%--<script src="<%=basePath%><%=basePath%>js/swiper/js/swiper.min.js" type="text/javascript" charset="utf-8"></script>--%>
    <%--<script src="<%=basePath%><%=basePath%>js/swiper/js/dySelect.js" type="text/javascript" charset="utf-8"></script>--%>
    <script src="<%=basePath%>js/ea/office_ea/companyRegist/mpapply/mpapply_dp.js"></script>
    <title>公司认证</title>
    <script type="text/javascript">

        var basePath = "<%=basePath%>";
        var organization_type = "${applyParam.organization_type}";
        var out_request_no = "${applyParam.out_request_no}";
        var companyID = "${companyID}";
        var  staffID = "${staffID}";


    </script>
</head>

<body>
<header>
    <ul class="clearfix">
        <li>
            <a onclick="javascript: window.history.go(-1);return false;" target="_self" >
            <img src="<%=basePath%>images/ea/office/pbapply/img-1.png">
            </a>
        </li>
        <li>
            公司认证
        </li>
        <li>

        </li>
    </ul>
</header>
<div class="content">
    <section class="sec-nav">
        <ul class="clearfix">
            <li  class="active">
                店铺信息
            </li>
            <c:if test="${applyParam.organization_type ne '2401'}">
                <li>
                    营业执照
                </li>

            </c:if>

            <li>
                法人信息
            </li>
            <li>
                结算账户
            </li>
            <li>
              超级管理
            </li>
        </ul>
    </section>
    <form name="form1" id="form1" method="post" enctype="multipart/form-data">
    <section class="sec-con">
        <p class="p-01">
            <label class="" for="">认证类型</label>
            <input type="hidden" name="applyParam.organization_type"  value="${applyParam.organization_type}"/>

            <c:choose>
                <c:when test="${applyParam.organization_type eq '2401'}">
                    <input type="text" name=""  value="个人卖家" readonly />

                </c:when>
                <c:when test="${applyParam.organization_type eq '2'}">
                    <input type="text" name=""  value="企业" readonly />
                </c:when>
                <c:when test="${applyParam.organization_type eq '4'}">
                    <input type="text" name=""  value="个体工商户" readonly />
                </c:when>
                <c:when test="${applyParam.organization_type eq '3'}">
                    <input type="text" name=""  value="党政、机关及事业单位" readonly />
                </c:when>
            </c:choose>
        </p>
        <p class="p-01">
            <label class="" for="">店铺编码</label>

            <input type="text" name="contactCompany.companyCode"  value="${contactCompany.companyCode}" class="notnull" data="店铺编码"/>

        </p>
        <p class="p-01">
            <label class="" for="">店铺名称</label>

            <input type="text" name="sales_scene_info.store_name"  value="${contactCompany.shopname}" class="notnull" data="店铺名称"/>

        </p>
        <p class="p-01">
            <label for="">商户简称</label>
            <input type="text" name="applyParam.merchant_shortname"  value="${applyParam.merchant_shortname}"   class="notnull" data="商户简称"/>
        </p>
        <p class="p-01">
            <label for="">所属行业</label>
            <input type="text" name="contactCompany.industryType"  class="industryType notnull"  value="${contactCompany.industryType}" readonly data="所属行业"/>
            <input type="hidden" name="contactCompany.industryId"  class="industryId"  value="${contactCompany.industryId}" />
        </p>
        <p class="p-01">
            <label for="">店铺电话</label>
            <input type="text"   value="${contactCompany.companyTel}" name="contactCompany.companyTel" class="notnull" data="店铺电话" placeholder="手机号/座机010-11111111"/>
        </p>

        <section class="sec-03 clearfix p-01">
            <p class="left">
                店铺logo
            </p>
            <div class="right">
                <input type="file" name="logo" id="sdfFile1" value="" onchange="f_change1(this);">
                <img alt="图片"  src="<%=basePath%>${contactCompany.logoPath}" alt="" onerror="this.src='<%=basePath%>images/ea/office/pbapply/img_015.png'" id="imgSdf1" class="notnull" data="店铺logo">
            </div>
        </section>

        <p class="p-01">
            <label for="">店铺地址</label>
            <span><img src="<%=basePath%>images/scMobile/qyrz/img-003.png"  class="d_r_site"></span>
            <input type="text" data="店铺地址" class="notnull d_r_site"  name="contactCompany.companyAddr"  value="${contactCompany.companyAddr eq 'nullnullnull'?'':contactCompany.companyAddr}" id="ddaddress"/>

            <input type="hidden" name="contactCompany.pname"  value="${contactCompany.pname}"  id="pname"/>
            <input type="hidden" name="contactCompany.cityname"  value="${contactCompany.cityname}"  id="cityname"/>
            <input type="hidden" name="contactCompany.adname"  value="${contactCompany.adname}" id="adname"/>
            <input type="hidden" name="contactCompany.street"  value="${contactCompany.street}" id="street"/>
            <input type="hidden" name="sales_scene_info.ssId"  value="${sales_scene_info.ssId}" />
            <input type="hidden" name="sales_scene_info.ssKey"  value="${sales_scene_info.ssKey}" />
            <input type="hidden" name="mode"   value="sales_scene_info"/>
            <input type="hidden" name="companyID"   value="${companyID}"/>

            <input type="hidden" name="contactCompany.logoPath"   value="${contactCompany.logoPath}"/>
            <input type="hidden" name="contactCompany.accuracy"   id="accuracy" value="${contactCompany.accuracy}"/>
            <input type="hidden" name="contactCompany.dimension"   id="dimension" value="${contactCompany.dimension}" />
        </p>

        <div class="div-bottom">

        </div>
    </section>
        <div class="div-p-02" style="display: none;">
            <p></p>
        </div>
    <section class="sec-footer clearfix">
        <p class="p-s">
            <a href="<%=basePath%>ea/qrshare/ea_queryState.jspa?auditSkip=00"  style="color:#fff;">上一步</a>
        </p>
        <p id="next1" class="p-x">
            下一步
        </p>
    </section>

        <input type="submit" name="submit" style="display:none;"/>
        <input type="hidden" name="applyParam.applyID" value="${applyParam.applyID}"/>
        <input type="hidden" name="applyParam.out_request_no" value="${applyParam.out_request_no}"/>
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
    function f_change1(file){
        var img = document.getElementById('imgSdf1');
        //读取File对象的数据
        reader.onload = function(evt){
            //data:img base64 编码数据显示
            img.width  =  "100";
            img.height =  "100";
            img.src = evt.target.result;
        }
        reader.readAsDataURL(file.files[0]);
    }
</script>




</html>