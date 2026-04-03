<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";


    // 设置不要缓存页面
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
    response.setDateHeader("Expires", 0); // 设置过期时间为0.

%>
<!DOCTYPE html>
<html>
<head lang="zh">
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/5l5C/human/entry/e_myexam.css"/>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/WFJClient/pc/5l5C/human/entry/e_myexam.js" type="text/javascript" charset="utf-8"></script>
    <title>&lrm;</title>
    <script type="text/javascript">

        var basePath = "<%=basePath%>";
        var pageNumber  = ${pageForm==null?0:pageForm.pageNumber};
        var pageCount  = ${pageForm==null?0:pageForm.pageCount};

         var type="${type}";

        var tqeID = "${totalQuestion.tqeID}";






    </script>
</head>
<body>
<header>
    <ul class="clearfix">
        <li class="back-li">
            <a onclick="javascript: window.history.go(-1);return false;"  target="_self">

                <img src="<%=basePath%>images/ea/office/contract/selectp/return.png" >
            </a>
        </li>
        <li class="title-li">
           我的考试
        </li>
        <li>

        </li>
    </ul>
</header>
<div class="content">
    <section class="sec-search">
        <div class="box clearfix">
            <label for="">
                <img src="<%=basePath%>images/ea/office/contract/img_02.png"/>
            </label>
            <input type="text" name="" id="search" placeholder="题库名称" />
        </div>
        <div><input type="button" name="" id="qsearch" value="搜索" /></div>
    </section>
    <section class="sec-nav sec-hide">
        <ul class="clearfix" >

            <li class="clearfix">
                <p class="exam" onclick="opr('m')"><img src="<%=basePath%>images/ea/office/contract/selectp/exam.png"/>开始考试</p>
            </li>
            <li class="clearfix">
                <p class="view"><img src="<%=basePath%>images/ea/office/contract/selectp/view.png"/>查看</p>
            </li>
        </ul>
    </section>
    <section class="sec-list" id="pc-sec" >
        <ul class="ul">


            <li class="clearfix">
                <div class="title-pc">

                    <div class="sex">
                        选择
                    </div>
                    <div class="date-p" title="题库">题库</div>
                    <div class="date-p" title="时长">时长</div>
                    <div class="date-p pc" title="总分数">总分</div>
                    <div class="date-p pc" title="及格分数">及格分</div>
                    <div class="date-p pc" title="题数">题数</div>
                    <div class="date-p pc" title="是否合格">是否合格</div>
                    <div class="date-p" title="考生得分">考生得分</div>
                    <div class="date-p" title="状态">状态</div>
                </div>


            </li>
            <c:forEach  items="${pageForm.list}" var="f" varStatus="v">


                <c:if test="${v.index+1 eq fn:length(pageForm.list)}">
                    <li class="clearfix last1" id="${f[9]}">
                </c:if>
                <c:if test="${v.index+1 ne fn:length(pageForm.list)}">
                    <li class="clearfix" id="${f[9]}">

                </c:if>
                <div class="title-pc">
                    <div class="sex">

                        <img class="img-01" src="<%=basePath%>/images/ea/office/contract/selectp/img_02.png">
                        <img class="img-02" src="<%=basePath%>/images/ea/office/contract/selectp/img_03.png">
                    </div>
                    <span class="status" style="display: none;">${f[8]}</span>
                    <div class="date-p" title="${f[5]}">${f[5]}</div>
                    <div class="date-p" title="${f[1]}">${f[1]}</div>
                    <div class="date-p pc" title="${f[2]}">${f[2]}</div>
                    <div class="date-p pc" title="${f[3]}">${f[3]}</div>
                    <div class="date-p pc" title="${f[4]}">${f[4]}</div>
                    <div class="date-p pc" title="${f[6]}">${f[6] eq '00'?'合格':f[6] eq '01'?'不合格':'未考试'}</div>
                    <div class="date-p" title="${f[7]}">${f[7] eq null ?'0':f[7]}</div>
                    <div class="date-p" title="${f[8]}">${f[8] eq '00'?'未考试':f[8] eq '01'?'考试中':f[8] eq '02'?'已完成':'已超时'}</div>

                </div>
                </li>
            </c:forEach>



        </ul>
    </section>



</div>


<!--表单提示-->
<div class="div-tingyong">
    <div class="box">
        <p>温馨提示<img class="close-tingyong" src="<%=basePath%>images/ea/office/contract/stamp/img_031.png" alt="" /></p>
        <div class="div-box">
            <p class="titlep"></p>
            <div class="clearfix">
                <p class="left close-tingyong">取消</p>
                <p class="right close-confirm">确定</p>
            </div>
        </div>
    </div>
</div>

</body>
<script>
    window.onload =  function () {



        var clientWidth = document.documentElement.clientWidth;
        if(clientWidth>=960){

            $(".pc").show();
        }else {
            $(".pc").hide();
        }
    }



</script>
</html>
