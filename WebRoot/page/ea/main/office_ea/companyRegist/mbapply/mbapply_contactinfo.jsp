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

    <script src="<%=basePath%>js/ea/office_ea/companyRegist/mpapply/mpapply_contactinfo.js"></script>
    <title>公司认证</title>
    <script type="text/javascript">

        var basePath = "<%=basePath%>";
        var out_request_no = "${applyParam.out_request_no}";
        var companyID = "${companyID}";
        var  staffID = "${staffID}";
    </script>
</head>

<body>
<header>
    <ul class="clearfix">
        <li>
        <a href="<%=basePath%>ea/merch/ea_getApplyAccount.jspa?applyParam.out_request_no=${applyParam.out_request_no}&companyID=${companyID}&staffID=${staffID}">

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
            <li >
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
            <li   class="active">
                超级管理
            </li>
        </ul>
    </section>
    <form name="form1" id="form1" class="form-4" method="post" enctype="multipart/form-data">
    <section class="sec-con">
        <p class="p-01">
            <label for="">超级管理员类型</label>
            <c:if test="${applyParam.organization_type eq '2401'}">
                <input type="hidden" name="contact_info.contact_type" placeholder=""  value="65" />
                <input type="text"  placeholder=""  value="经营者/法人" readonly />
            </c:if>
            <c:if test="${applyParam.organization_type ne '2401'}">

                <input type="hidden" name="contact_info.contact_type" placeholder=""  value="66" />
                <input type="text"  placeholder=""  value="负责人"  readonly/>
          </c:if>
        </p>

        <p class="p-01">
            <label for="">法人姓名</label>
            <input type="text" class="notnull" data="超级管理员姓名" name="contact_info.contact_name"  value="${contact_info.contact_name}" />
        </p>
        <p class="p-01">
            <label class="" for="">法人身份证件号码</label>
            <input type="text"  class="notnull" data="身份证件号码" name="contact_info.contact_id_card_number" placeholder=""  value="${contact_info.contact_id_card_number}" />
        </p>
        <p class="p-01">
            <label for="">管理员手机号</label>
            <input type="text"  class="notnull" data="手机号" name="contact_info.mobile_phone"  value="${contact_info.mobile_phone}" />
        </p>
        <c:if test="${applyParam.organization_type ne '2401'}">

            <p class="p-01">
                <label class="" for="">管理员邮箱</label>
                <input type="text"  class="notnull" data="超级管理员邮箱" name="contact_info.contact_email" placeholder=""  value="${contact_info.contact_email}" />


            </p>
        </c:if>



    </section>
        <div class="div-p-02" style="display: none;">
            <p>错误提示</p>
        </div>
    <section class="sec-footer clearfix">
        <%--<p class="p-s">
            <a href="<%=basePath%>ea/merch/ea_getApplyAccount.jspa?companyID=${companyID}&applyParam.out_request_no=${applyParam.out_request_no}&staffID=${staffID}"  style="color:#fff;">上一步</a>
        </p>--%>
        <p id="next1">
            确认
        </p>
        <input type="submit" name="submit" style="display:none;"/>
        <input type="hidden" name="companyID"   value="${companyID}"/>
        <input type="hidden" name="staffID"   value="${staffID}"/>
        <input type="hidden" name="applyParam.out_request_no" value="${applyParam.out_request_no}"/>
        <input type="hidden" name="mode"   value="contact_info"/>

        <input type="hidden" name="contact_info.coId"  value="${contact_info.coId}" />
        <input type="hidden" name="contact_info.coKey"  value="${contact_info.coKey}" />

        <iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
    </section>
    </form>
</div>
</body>


</html>