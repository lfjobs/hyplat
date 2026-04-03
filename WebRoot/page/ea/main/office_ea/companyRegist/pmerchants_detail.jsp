
<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";

%>

<html>
<head>
    <meta charset="UTF-8">
    <title></title>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/office_ea/mbapply/index.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/office_ea/mbapply/style.css"/>

    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>

    <script language="javascript" type="text/javascript"
            src="<%=basePath%>js/ea/office_ea/companyRegist/pmerchants_detail.js"></script>
    <script type="text/javascript">

        var basePath="<%=basePath%>";
        var out_request_no = "${applyParam.out_request_no}";


    </script>
</head>
<body>

<header>
    <div>
        <h2>商家认证信息</h2>
    </div>
</header>
<div class="content">
    <section class="sec-01-01 clearfix sec001">
        <div class="left">
            <h3>
                基本账户信息
            </h3>
            <p class="p-bianhao">业务申请编号：<span>${applyParam.out_request_no}</span></p>
            <p class="p-leixing">主体类型：<span><c:choose>
                <c:when test="${applyParam.organization_type eq '2401'}">
                    个人卖家
                </c:when>
                <c:when test="${applyParam.organization_type eq '2'}">
                    企业
                </c:when>
                <c:when test="${applyParam.organization_type eq '4'}">
                    个体工商户
                </c:when>
                <c:when test="${applyParam.organization_type eq '3'}">
                    党政、机关及事业单位
                </c:when>
            </c:choose></span>
            </p>
        </div>
        <div class="right">
            <p  onclick="edit('organization_type')">修改</p>
        </div>
    </section>
    <section class="sec-04-01 clearfix sec001">
        <div class="left">
            <h3>
                店铺基本信息
            </h3>
            <section class="clearfix">
                <p>店铺logo：</p>
                <div>
                    <img src="<%=basePath%>${contactCompany.logoPath}" onerror="this.src='<%=basePath%>images/ea/office/pbapply/pc_img_05.png'"/>
                </div>
            </section>
            <p>店铺名称：<span>${sales_scene_info.store_name}</span></p>
            <p>商户简称：<span>${applyParam.merchant_shortname}</span></p>
            <p>店铺行业：<span>${contactCompany.industryType}</span></p>
            <p>费率：<span>${wxMainAccount.rate}</span></p>

            <p>地址：<span>${contactCompany.companyAddr}</span></p>
        </div>
        <div class="right">
            <p onclick="edit('sales_scene_info')">修改</p>
        </div>
    </section>

    <section class="sec-03-01 clearfix sec001">
        <div class="left">
            <h3>
                经营者或法人身份证信息
            </h3>
            <section class="clearfix">
                <p>身份证人像面照片：</p>
                <div>
                    <img src="<%=basePath%>${id_card_info.id_card_copy}" onerror="this.src='<%=basePath%>images/ea/office/pbapply/pc_img_04.png'"/>
                </div>
            </section>
            <section class="clearfix">
                <p>身份证国徽面照片：</p>
                <div>
                    <img src="<%=basePath%>${id_card_info.id_card_national}" onerror="this.src='<%=basePath%>images/ea/office/pbapply/pc_img_03.png'"/>
                </div>
            </section>
            <p>身份证姓名：<span>${id_card_info.id_card_name}</span></p>
            <p>身份证号码：<span>${id_card_info.id_card_number}</span></p>
            <p>身份证住址：<span>${id_card_info.id_card_address}</span></p>
            <p>身份证开始日期：<span>${id_card_info.id_card_valid_time_begin}</span></p>
            <p>身份证有效期限：<span>${id_card_info.id_card_valid_time}</span></p>
        </div>
        <div class="right">
            <p onclick="edit('id_card_info')">修改</p>
        </div>
    </section>
    <section class="sec-02-01 clearfix sec001">
        <div class="left">
            <h3>
                营业执照信息
            </h3>

            <section class="clearfix">
                <p>营业执照扫描件：</p>
                <div>
                    <img src="<%=basePath%>${business_license_info.business_license_copy}"  onerror="this.src='<%=basePath%>images/ea/office/pbapply/pc_img_02.png'"/>
                </div>
            </section>
            <p>证件注册号：<span>${business_license_info.business_license_number}</span></p>
            <p>商户名称：<span>${business_license_info.merchant_name}</span></p>
            <p>经营者/法定代表人姓名：<span>${business_license_info.legal_person}</span></p>
            <p>注册地址：<span>${business_license_info.company_address}</span></p>
            <p>营业期限：<span>${business_license_info.business_time}</span></p>

            <section class="clearfix">
                <p>组织机构代码证照片:</p>
                <div>
                    <img src="<%=basePath%>${organization_cert_info.organization_copy}" onerror="this.src='<%=basePath%>images/ea/office/pbapply/pc_img_01.png'"/>
                </div>
            </section>
            <p>组织机构代码：<span>${organization_cert_info.organization_number}</span></p>
            <p>组织机构代码有效期限：<span>${organization_cert_info.organization_time}</span></p>
        </div>
        <div class="right">
            <p onclick="edit('business_license_info')">修改</p>
        </div>
    </section>


    <section class="sec-05-01 clearfix sec001">
        <div class="left">
            <h3>
                结算银行账户
            </h3>
            <p>账户类型：<span> <c:choose>
                <c:when test="${account_info.bank_account_type eq '74'}">
                    对公账户
                </c:when>
                <c:when test="${account_info.bank_account_type eq '75'}">
                    对私账户
                </c:when>

            </c:choose></span></p>
            <p>开户银行：<span>${account_info.account_bank}</span></p>
            <p>开户名称：<span>${account_info.account_name}</span></p>
            <p>开户银行省市：<span>${account_info.bank_address}</span></p>
            <p>开户银行省市编码：<span>${account_info.bank_address_code}</span></p>
            <p>开户银行全称：<span>${account_info.bank_name}</span></p>
            <p>银行卡号：<span>${account_info.account_number}</span></p>
        </div>
        <div class="right">
            <p onclick="edit('account_info')">修改</p>
        </div>
    </section>
    <section class="sec-06-01 clearfix sec001">
        <div class="left">
            <h3>
                超级管理员信息
            </h3>
            <p>超级管理员类型：<span><c:choose>
                <c:when test="${contact_info.contact_type eq '65'}">
                    法人
                </c:when>
                <%--<c:when test="${contact_info.contact_type eq '66'}">--%>
                    <%--负责人--%>
                <%--</c:when>--%>
            </c:choose></span></p>
            <p>超级管理员姓名：<span>${contact_info.contact_name}</span></p>
            <p>超级管理员身份证号码：<span>${contact_info.contact_id_card_number}</span></p>
            <p>超级管理员手机号：<span>${contact_info.mobile_phone}</span></p>
            <p>超级管理员邮箱：<span>${contact_info.contact_email}</span></p>
        </div>
        <div class="right">
            <p onclick="edit('contact_info')">修改</p>
        </div>
    </section>
    <section class="sec-07-01 sec001">
        <div class="box clearfix">
            <div class="left">
                <h3>
                    补充信息
                </h3>
                <section class="clearfix">
                    <p>特殊资质：</p>
                    <div>
                        <img src="<%=basePath%>${applyParam.qualifications}" onerror="this.src='<%=basePath%>images/ea/office/pbapply/pc_img_06.png'"/>
                    </div>
                </section>
                <section class="clearfix">
                    <p>补充材料：</p>
                    <div>
                        <img src="<%=basePath%>${applyParam.business_addition_pics}" onerror="this.src='<%=basePath%>images/ea/office/pbapply/pc_img_07.png'"/>
                    </div>
                </section>
            </div>
            <div class="right">
                <p  onclick="edit('business_addition')">修改</p>
            </div>
        </div>
        <div class="div-buchong">
            补充说明：${applyParam.business_addition_desc}
        </div>
        <%--<div class="footer">--%>
            <%--<div class="clearfix">--%>
                <%--<p>通过</p>--%>
                <%--<p>拒绝</p>--%>
            <%--</div>--%>
        <%--</div>--%>
    </section>



</div>
</body>

</html>
