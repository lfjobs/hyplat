<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
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
    <link rel="stylesheet" type="text/css"
          href="<%=basePath%>css/WFJClient/pc/5l5C/office/contactcompany/contactComMtAdd.css"/>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" charset="utf-8"
            src="<%=basePath%>js/WFJClient/pc/5l5C/office/contactcompany/contactComMtAdd.js"></script>
    <title>移动端往来单位-添加</title>
    <script type="text/javascript">
        var basePath = "<%=basePath%>";
        var ccomid = "${ccomid}";
        var companyID = "${param.companyID}";
        var ccompanyID = "${param.ccompanyID}";
    </script>
</head>
<body>
<header>
    <ul class="clearfix">
        <li>
            <a onclick="window.history.go(-1);return false;" target="_self">
                <img src="<%=basePath%>images/ea/office/contract/stamp/return.png">
            </a>
        </li>
        <li>往来单位</li>
        <li></li>
    </ul>
</header>
<form name="form" id="form" method="post" enctype="multipart/form-data">
    <input type="submit" name="submit" style="display: none;"/>
    <div class="content">
        <div class="div-name div-only">
            <label for=""><span class="rad">* </span>单位名称</label>
            <input type="hidden" id="ccompanyID" name="contactCompany.ccompanyID"/>
            <input type="text" placeholder="请填写单位名称" class="isNotnull" name="contactCompany.companyName"
                   id="companyName"/>
        </div>
        <div class="div-name div-only">
            <label for=""><span class="rad">* </span>单位电话</label>
            <input type="text" placeholder="请填写单位电话" class="isNotnull" name="contactCompany.companyTel"
                   id="companyTel"/>
        </div>
        <div class="div-name div-only">
            <label for=""><span class="rad">* </span>单位责任人</label>
            <input type="text" placeholder="请填写单位责任人" class="isNotnull" name="contactCompany.cresponsible"
                   id="cresponsible"/>
        </div>
        <div class="div-name div-only">
            <label for=""><span class="rad">* </span>责任人电话</label>
            <input type="text" placeholder="请填写责任人电话" class="isNotnull" name="contactCompany.responsibleTel"
                   id="responsibleTel"/>
            <input type="hidden" name="nfc.companyID" id="companyId"/>
        </div>
        <div class="div-name div-only industryType-div">
            <label for=""><span class="rad">* </span>行业类别</label>
            <input type="text" placeholder="请选择行业类别" class="isNotnull" readonly name="contactCompany.industryType"
                   id="industryType"/>
            <input type="hidden" name="contactCompany.industryId" id="industryId"/>
        </div>
        <div class="div-name div-only comPro-div">
            <label for="">公司性质</label>
            <input type="text" placeholder="请选择公司性质" name="contactCompany.comPro" readonly id="comPro"/>
        </div>
        <div class="div-name div-only comScale-div">
            <label for="">公司规模</label>
            <input type="text" placeholder="请选择公司规模" name="contactCompany.comScale" readonly id="comScale"/>
        </div>
        <div class="div-name oaskName">
            <label for="">备注</label>
            <input type="text" maxlength="256" placeholder="请填写备注（包含标点符号,限128个字）" name="contactCompany.remark"
                   id="remark"/>
        </div>
        <div class="div-name">
            <label for="">公司宗旨</label>
            <input type="text" maxlength="50" placeholder="请输公司宗旨（包含标点符号,限25个字）" name="contactCompany.comPurpose"
                   id="comPurpose"/>
        </div>
        <div class="div-img clearfix">
            <div class="div-left div-qswj">
                <label for=""><span class="rad">* </span>上传公司LOGO</label>
                <input type="text" placeholder="支持png,jpg">
            </div>
            <input type="file" name="photo" onchange="f_change(this)" id="sdfFile" accept="image/png,image/jpeg" class="isNotnull">
            <img alt="图片" src="<%=basePath%>images/ea/lottery/add_btn.png" id="imgSdf">
        </div>
        <div class="div-name">
            <label for="">品牌信息</label>
            <input type="text" maxlength="800" placeholder="请填写品牌信息（包含标点符号，限400个字）" name="contactCompany.brandInfo"
                   id="brandInfo"/>
        </div>
        <div class="div-name">
            <label for="">经营范围</label>
            <input type="text" maxlength="256" placeholder="请填写经营范围（包含标点符号，限128个字）" name="contactCompany.dealIn"
                   id="dealIn"/>
        </div>
        <div class="div-name">
            <label for=""><span class="rad">* </span>公司地址</label>
            <input type="text" placeholder="请选择公司地址" name="contactCompany.companyAddr" id="companyAddr" readonly class="isNotnull"/>
            <input type="hidden" name="contactCompany.address" id="address"/>
        </div>
        <div class="div-name">
            <label for="">公司网址</label>
            <input type="text" placeholder="请选择公司网址" name="contactCompany.companyWeb" id="companyWeb"/>
        </div>
        <div class="div-bottom">
            <p class="submitAudit">
                保存
            </p>
        </div>
    </div>
</form>

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
<!--公司性质-->
<div class="div-comtype">
    <div class="box">
        <ul></ul>
    </div>
</div>
<!--公司规模-->
<div class="div-scalelist">
    <div class="box">
        <ul></ul>
    </div>
</div>
<!-- 行业分类 -->
<div class="div-hangye div-data">
    <div class="div-box">
        <div class="hangye-header">
            <ul class="clearfix">
                <li class="div-close">
                    <img src="http://localhost:8080/images/scMobile/payBudget/budgetList/ico-left.png" alt="">
                </li>
                <li>行业分类</li>
                <li class="keep"></li>
            </ul>
        </div>
        <div class="div-con clearfix hyfl">

        </div>
    </div>
</div>
<!-- 地址 -->
<div class="div-address div-data">
    <div class="div-box">
        <div class="address-header">
            <ul class="clearfix">
                <li class="div-close">
                    <img src="http://localhost:8080/images/scMobile/payBudget/budgetList/ico-left.png" alt="">
                </li>
                <li>地址</li>
                <li class="keep"></li>
            </ul>
        </div>
        <div class="div-con clearfix address">
            <div class="div-isok">
                <p class="isok">
                    确定
                </p>
            </div>
            <div class="line" style="height:7.55px;background-color: #eee"></div>
            <div class="div-name div-only address-div" title="省">
                <label for="">省</label>
                <input type="text" readonly placeholder="选择省级区域" id="s" class="notnull"/>
                <input type="hidden" id="sid" class="c"/>
            </div>
            <div class="div-name div-only address-div" title="县">
                <label for="">县</label>
                <input type="text" readonly placeholder="选择县级区域" id="x" class="notnull"/>
                <input type="hidden" id="xid" class="c"/>
            </div>
            <div class="div-name div-only address-div" title="区">
                <label for="">区</label>
                <input type="text" readonly placeholder="选择区级区域" id="q" class="notnull"/>
                <input type="hidden" id="qid" class="c"/>
            </div>
            <div class="div-name">
                <label for="">详细地址</label>
                <input type="text" placeholder="请填写详细地址" id="xx" class="notnull"/>
            </div>
        </div>
    </div>
</div>
<%--省、县、区数据加载--%>
<div class="div-sxq">
    <input type="hidden" id="type-input"/>
    <div class="box">
        <ul></ul>
    </div>
</div>
</body>
</html>
