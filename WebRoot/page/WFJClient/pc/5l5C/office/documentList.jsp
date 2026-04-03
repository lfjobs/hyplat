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
<head lang="zh">
    <meta charset="utf-8"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/5l5C/office/documentList.css"/>
    <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/jquery-1.9.1.min.js"></script>

    <title>移动版全部文书列表</title>
    <script type="text/javascript">
        var basePath = "<%=basePath%>";
    </script>
</head>
<body>
<div class="content">
    <section class="sec-nav sec-hide">
        <!--sec-hide-->
        <ul class="clearfix">

            <li class="clearfix">
                <p class="view"><img src="<%=basePath%>images/ea/office/contract/selectp/view.png"/>查看</p>
            </li>

            <li class="clearfix">
                <p class="print-p"><img src="<%=basePath%>images/ea/production/drive/att_search.png"/>查询</p>
            </li>
        </ul>
    </section>
    <section class="sec-list" id="pc-sec">
        <ul class="ul">
            <li class="clearfix">
                <div class="title-pc">
                    <div class="sex" style="width: 10%;"> 选择</div>
                    <div title="文书编号" style="width: 20%">文书编号</div>
                    <div title="文书名称" style="width: 30%">文书名称</div>
                    <div title="单位名称" style="width: 20%">流程状态</div>
                </div>
            </li>
            <li class="clearfix">
                <div class="title-pc">
                    <div class="sex" style="width: 10%;">
                        <img class="img-01" src="<%=basePath%>images/ea/office/contract/selectp/img_02.png">
                        <img class="img-02" src="<%=basePath%>images/ea/office/contract/selectp/img_03.png">
                    </div>

                    <div class="sn-p" title="ffff字【2023】第0001号" style="width:20%!important;">
                        ffff字【2023】第0001号
                    </div>
                    <div class="oask-p" title="总部服务(创收)平台" style="width:30%!important;">总部服务(创收)平台</div>
                    <div class="state-p" title="无" style="width:20%!important;">无</div>
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
</body>
</html>
