<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>
<base href="<%=basePath%>">

<title>文书导航</title>
<meta charset="utf-8"/>
<meta name="viewport"
      content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/office_ea/contract/templateManage.css">
<script type="text/javascript" charset="utf-8" src="js/jquery-1.11.3.js"></script>
<script type="text/javascript">
    var basePath = "<%=basePath%>";
    var companyID = "${param.companyID}";
</script>
</head>
<body>
<header>
    <ul class="clearfix">
        <li>
            <a onclick="window.history.go(-1);return false;" target="_self">
                <img src="<%=basePath%>images/ea/office/contract/selectp/return.png">
            </a>
        </li>
        <li>
            行政建设
        </li>
        <li>
        </li>
    </ul>
</header>
<div class="container" id="all_order">
    <div class="pos-top">
        <div class="box-fh">
            <ul class="ul-tab ul-tab-fh clearfix">
                <li>
                    <p id="all">模版文书</p>
                </li>
                <li>
                    <p>共享文书</p>
                </li>
            </ul>
            <ul class="ul-tab ul-tab-fh clearfix">
                <li>
                    <p><img src="<%=basePath%>\images\ea\office\contract/uattach.png"/>公文模版</p>
                </li>
                <li>
                    <p><img src="<%=basePath%>/images/ea/office/contract/uattach.png"/>模版分类</p>
                </li>
            </ul>
        </div>
        <div class="box-fh2">
            <div class="content">
                <section class="sec-nav sec-hide">
                    <!--sec-hide-->
                    <ul class="clearfix">
                        <li class="clearfix ">
                            <p class="draft"><img src="<%=basePath%>/js/jqModal/css/images_blue/add.png"/>新建</p>
                        </li>
                        <li class="clearfix">
                            <p class="view"><img src="<%=basePath%>/images/ea/office/contract/img_06.png"/>修改</p>
                        </li>
                        <li class="clearfix">
                            <p class="print-p"><img src="<%=basePath%>\images\ea\office\contract\selectp/view.png"/>查看</p>
                        </li>
                        <li class="clearfix">
                            <p class="query"><img src="<%=basePath%>/images/ea/office/contract/selectp/del.png"/>删除</p>
                        </li>
                    </ul>
                </section>
                <section class="sec-list" id="pc-sec">
                    <ul class="ul">
                        <li class="clearfix">
                            <div class="title-pc">
                                <div class="sex" style="width: 10%;"> 选择</div>
                                <div title="序号" style="width: 10%">序号</div>
                                <div title="文书编号" style="width: 20%">模版编号</div>
                                <div title="文书名称" style="width: 30%">模版名称</div>
                                <div title="单位名称" style="width: 20%">单位名称</div>
                            </div>
                        </li>
                    </ul>
                </section>
            </div>
            <!--表单提示-->
            <div class="div-tingyong">
                <div class="box">
                    <p>温馨提示<img class="close-tingyong" src="<%=basePath%>images/ea/office/contract/stamp/img_031.png" alt=""/></p>
                    <div class="div-box">
                        <p class="tittle-p"></p>
                        <div class="clearfix">
                            <p class="right close-tingyong">确定</p>
                        </div>
                    </div>
                </div>
            </div>

            <div class="div-chaxun">
                <div class="div-box">
                    <p class="p-top">
                        请输入查询信息
                    </p>
                    <p class="p-inp clearfix">
                        <label>文书编号</label>
                        <input type="number" placeholder="请填写文书编号"/>
                    </p>
                    <p class="p-inp clearfix">
                        <label>文书名称</label>
                        <input type="text" placeholder="请填写文书名称"/>
                    </p>
                    <p class="p-bottom">
                        查询
                    </p>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script>
    $(function () {
        $("li p").click(function () {
            if ($(this).is(".active")) {
                $(this).removeClass("active");
            } else {
                $(".active").removeClass("active");
                $(this).addClass("active");
            }
        });

        $("#all").trigger("click");

        $(".lc").click(function () {
            window.location.href =basePath+"/ea/documentcommon/ea_showDocumentModule.jspa?bb=new&module=";
        });

        $("#NFC").click(function () {
            $("#mainframe").attr("src",basePath+"/page/WFJClient/secure/nfc/NfcList.jsp?companyID="+companyID);
        });
    });
</script>
</html>

