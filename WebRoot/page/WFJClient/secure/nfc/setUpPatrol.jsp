<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/secure/nfc/setUpPatrol.css"/>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" charset="utf-8"
            src="<%=basePath %>page/ueditor/third-party/webuploader/webuploader.js"></script>
    <script type="text/javascript" charset="utf-8"
            src="<%=basePath %>page/ueditor/third-party/webuploader/diyUpload.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/WFJClient/secure/nfc/setUpPatrol.js"></script>
    <title>巡查</title>

    <script type="text/javascript">
        var basePath = "<%=basePath%>";
        var sn = "${param.sn}";//序列号
        var model = "${param.model}";//芯片型号
        var staffid = "${staffid}";//芯片型号
        var staffname = "${staffname}";//芯片型号
    </script>
</head>
<body>
<header>
    <ul class="clearfix">
        <li>巡查</li>
        <li></li>
        <li></li>
    </ul>
</header>
<form name="form" id="form" method="post" enctype="multipart/form-data">
    <input type="submit" name="submit" style="display: none;"/>
    <input type="hidden" name="si.companyID" id="companyID" value="${nfc.companyID}"/>
    <input type="hidden" name="si.companyName" id="companyName" value="${nfc.companyName}"/>
    <input type="hidden" name="si.nfcID" id="nfcID" value="${nfc.nfcID}"/>
    <div class="content">
        <div class="div-name">
            <label for="">安全点编号</label>
            <input type="text" placeholder="请填写安全点编号" id="ln" readonly value="${nfc.ln}" name="ln"/>
        </div>
        <div class="div-name">
            <label for="">芯片序列号</label>
            <input type="hidden" value="${nfc.model}" name="model"/>
            <input type="text" placeholder="请填写芯片序列号" id="sn" value="${nfc.sn}" readonly name="sn"/>
        </div>
        <div class="div-name">
            <label for="">安全类型</label>
            <input type="text" placeholder="请选择安全类型" class="isNotnull oaskName" readonly id="oaskName" style="text-overflow: ellipsis;"/>
            <input type="hidden" name="oask" id="oask"/>
        </div>
        <div class="div-name">
            <label for="">安全巡查地点</label>
            <input type="text" placeholder="安全巡查地点" id="bindLocation" readonly
                   value="${nfc.bindLocation}"/>
        </div>
        <%--<div class="div-radio">
            <fieldset>
                <legend>紧急程度</legend>--%>
        <div class="div-name radio">
            <label for="radio-1">绿</label>
            <input type="radio" name="si.siType" value="02" class="my_radio" id="radio-1" checked/>
        </div>
        <div class="div-name radio">
            <label for="radio-2">黄</label>
            <input type="radio" name="si.siType" value="01" class="my_radio" id="radio-2"/>
        </div>
        <div class="div-name radio">
            <label for="radio-3">红</label>
            <input type="radio" name="si.siType" value="00" class="my_radio" id="radio-3"/>
        </div>
        <%--</fieldset>
    </div>--%>
        <div class="div-name">
            <label for="">巡查结果</label>
            <input type="text" maxlength="50" placeholder="请描述巡查结果" name="si.illustrate" value=""/>
        </div>
        <div class="div-name img">
            <label for="">巡查图片</label>
            <input type="hidden" id="ImagesPath" name="ImagesPath"/>
            <%--<input type="text" placeholder="请选择责任人" readonly name="nfc.img" id="img"/>--%>
            <div class="div-img">请上传巡查图片</div>
        </div>
        <div class="div-name video">
            <label for="">巡查视频</label>
            <input type="hidden" id="VideoPath" name="VideoPath"/>
            <div class="div-video">请上传巡查视频</div>
        </div>
        <div class="div-name">
            <label for="">责任人</label>
            <input type="text" placeholder="请选择巡查人" readonly value="${staffname}" id="staffName" name="si.staffName"/><%-- name="si.staffName"--%>
            <input type="hidden" name="si.staffID" id="StaffId" value="${staffid}"/>
        </div>
        <%--<div class="div-name">
            <label for="">巡查时间</label>
            <input type="text" readonly name="nfc" id="" value=""/>
        </div>--%>
        <div class="div-bottom">
            <p class="submitAudit">
                保存
            </p>
        </div>
    </div>
</form>

<!-- 图片添加 -->
<div class="div-tupian2" style="opacity: 0; transform: translate(1000000px);">
    <div class="div-box">
        <ul class="clearfix">
            <li class="div-close">
                <img src="<%=basePath%>images/ea/office/contract/stamp/return.png" alt="">
            </li>
            <li>
                巡查图片上传
            </li>
            <li class="p-tijiao">
                保存
            </li>
        </ul>
    </div>
    <div class="div-con content">
        <div class="div-tab">
            <div class="div-01">
                <div class="demo">
                    <div id="as"></div>
                    <c:if test="${fn:length(arrilist)>0 }">
                        <span style="display:none;" id="arrilist">${fn:length(arrilist) }</span>
                        <div class="parentFileBox" style="width: 360px;">
                            <ul class="fileBoxUl">
                                <c:forEach items="${arrilist }" var="entity" varStatus="status">
                                    <li id="fileBox_WU_FILE_0_${status.index }" class="">
                                        <div class="viewThumb">
                                            <img src="<%=basePath %>${entity.imgurl}">
                                        </div>
                                        <div class="diyCancel"></div>
                                        <div class="diySuccess" style="display: block;"></div>
                                        <div class="diyFileName">${entity.imgurl.substring(entity.imgurl.lastIndexOf("/")+1, entity.imgurl.length())}</div>
                                        <div class="diyFilePath" style="display: none;">
                                                ${entity.imgurl}
                                        </div>
                                        <div class="diyBar" style="display: none;">
                                            <div class="diyProgress" style="width: 100%;"></div>
                                            <div class="diyProgressText">上传完成</div>
                                        </div>
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- 视频添加 -->
<div class="div-shipin2" style="opacity: 0; transform: translate(1000000px);">
    <div class="div-box">
        <ul class="clearfix">
            <li class="div-close">
                <img src="<%=basePath%>images/ea/office/contract/stamp/return.png" alt="">
            </li>
            <li>
                巡查视频上传
            </li>
            <li class="p-tijiao">
                保存
            </li>
        </ul>
    </div>
    <div class="div-con content">
        <div class="div-tab">
            <div class="div-01">
                <div class="demo">
                    <div id="ass"></div>
                    <c:if test="${fn:length(arrvlist)>0 }">
                        <span style="display:none;" id="arrvlist">${fn:length(arrvlist) }</span>
                        <div class="parentFileBox" style="width: 360px;">
                            <ul class="fileBoxUl">
                                <c:forEach items="${arrvlist }" var="entity" varStatus="status">
                                    <li id="fileBox_WU_FILE_0_${status.index }" class="">
                                        <div class="viewThumb">
                                            <img src="<%=basePath %>${entity.imgurl}">
                                        </div>
                                        <div class="diyCancel"></div>
                                        <div class="diySuccess" style="display: block;"></div>
                                        <div class="diyFileName">${entity.imgurl.substring(entity.imgurl.lastIndexOf("/")+1, entity.imgurl.length())}</div>
                                        <div class="diyFilePath" style="display: none;">
                                                ${entity.imgurl}
                                        </div>
                                        <div class="diyBar" style="display: none;">
                                            <div class="diyProgress" style="width: 100%;"></div>
                                            <div class="diyProgressText">上传完成</div>
                                        </div>
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</div>

<!--温馨提示-->
<div class="div-tingyong div-dqd">
    <div class="box">
        <p>温馨提示</p>
        <div class="div-box">
            <p class="titlep"></p>
            <div class="clearfix">
                <%--<p class="left close-tingyong">取消</p>--%>
                <p class="right close-confirm">确定</p>
            </div>
        </div>
    </div>
</div>
<div class="loading">
    <div class="div-box">
        <img class="close-tingyong" src="<%=basePath%>images/ea/office/contract/selectp/loading2.gif" alt=""/>
        <p>保存中...</p>
    </div>
</div>

<%--安全类型--%>
<div id="divList" style="display: none;position: absolute; top: 0%; width: 100%; height: 100%; background: rgb(255, 255, 255); z-index: 1001;">
    <header>
        <ul class="clearfix">
            <li>
                <a target="_self" class="close-a">
                    <img src="<%=basePath%>images/ea/office/contract/selectp/return.png">
                </a>
            </li>
            <li class="li-text"></li>
            <li></li>
        </ul>
    </header>
    <div class="divList-content">
        <div class="div-section">
            <section class="sec-search">
                <label for="">
                    <img src="<%=basePath%>images/ea/office/contract/selectp/img_01.png"/>
                </label>
                <input type="text" id="search"/>
            </section>
        </div>
        <section class="sec-ul">
            <ul class="ul-list"></ul>
        </section>
        <section class="sec-bottom">
            <p>
                确定
            </p>
        </section>
    </div>
</div>
</body>
</html>
