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
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/office_ea/mbapply/applybc.css" />
    <script src="<%=basePath%>js/font-size.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/ea/office_ea/companyRegist/mpapply/mpapply_supinfo.js"></script>

    <title>公司认证</title>

    <script type="text/javascript">

        var basePath = "<%=basePath%>";
        var out_request_no = "${applyParam.out_request_no}";
        var companyID = "${companyID}";
        var staffID = "${staffID}";


    </script>
</head>

<body>
<header>
    <ul class="clearfix">
        <li>
            <a onclick="javascript: window.history.go(-1);return false;"  >
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
<form name="form1" id="form1" method="post" enctype="multipart/form-data">

<div class="content">

    <c:if test="${param.auditComment.indexOf('特殊资质')!=-1}">
        <section class="sec-01">
            <div class="div-01 clearfix">
                <p class="p-zz">
                    特殊资质（<span class="tnum">0</span>/5）
                </p>
                <p class="p-img">
                    <img src="<%=basePath%>images/ea/office/pbapply/img_015.png"/>
                </p>
                <!--    照片添加    -->
            </div>
            <p class="p-01">
                <span></span>支持格式：bmp,png,jpeg,jpg请上传2M内的
            </p>
            <div class="z_photo clearfix">
                <div class="z_file">
                    <input type="file" name="qualifications" id="file0" value="" accept="image/*"  onchange="imgChange('z_photo','z_file',this);" />


                </div>
            </div>
        </section>
    </c:if>
    <c:if test="${param.auditComment.indexOf('补充材料')!=-1}">
        <section class="sec-01">
            <div class="div-01 clearfix">
                <p class="p-zz">
                    补充材料（<span class="bnum">0</span>/5）
                </p>
                <p class="p-img">
                    <img src="<%=basePath%>images/ea/office/pbapply/img_015.png"/>
                </p>
            </div>
            <p class="p-01">
                支持格式：bmp、png、jpeg、jpg请上传2M内的彩色图片。
            </p>
            <div class="z_photo1 clearfix">
                <div class="z_file1">
                    <input type="file" name="businessadpics" id="files0" value="" accept="image/*"  onchange="imgChange1('z_photo1','z_file1',this);" />
                </div>
            </div>
        </section>

    </c:if>
    <section class="sec-buchong">
        <p>补充说明</p>
        <textarea name="applyParam.business_addition_desc" rows="20" placeholder="选填，不超过500个字。" cols="40" class="maxlength"></textarea>
    </section>

    <div class="div-p-02" style="display: none;">
        <p>错误提示</p>
    </div>
    <section class="sec-footer clearfix">

        <p id="next1" class="p-x">
            提交
        </p>
        <input type="submit" name="submit" style="display:none;"/>
        <input type="hidden" name="companyID"   value="${companyID}"/>
        <input type="hidden" name="staffID"   value="${staffID}"/>
        <input type="hidden" name="applyParam.out_request_no" value="${applyParam.out_request_no}"/>
        <input type="hidden" name="mode"   value="business_addition"/>

        <iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
    </section>
</div>
</form>
</body>

</html>