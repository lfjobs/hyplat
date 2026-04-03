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
<!DOCTYPE html>
<html>
<head lang="zh">
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/office_ea/contract/sharelist.css"/>
    <%--<script src="<%=basePath%>js/font-size.js" type="text/javascript" charset="utf-8"></script>--%>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/ea/office_ea/contract/sharelist.js" type="text/javascript" charset="utf-8"></script>
    <title>&lrm;</title>
    <script type="text/javascript">
        var module = "<%=session.getAttribute("module")%>"

        var basePath = "<%=basePath%>";
        var pageNumber  = ${pageForm==null?0:pageForm.pageNumber};
        var pageCount  = ${pageForm==null?0:pageForm.pageCount};

        var pageNumber1  = 0;
        var pageCount1  = 0;
        var recordCount1 = 0;

        var sccId = "${sccId}";

        var  deal = "${param.deal}";

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
        <li>
            共享池
        </li>
        <li class="stamp">
            <%--<a href = "<%=basePath%>/ea/enterprisestamp/ea_getStampList.jspa?sccId=${sccId}">--%>
            <%--<img src="<%=basePath%>images/ea/office/contract/img_05.png"/>个人印章--%>
            <%--</a>--%>
        </li>
    </ul>
</header>
<div class="content">
    <section class="sec-search">
        <div class="box clearfix">
            <label for="">
                <img src="<%=basePath%>images/ea/office/contract/img_02.png"/>
            </label>
            <input type="text" name="" id="search" placeholder="搜索文件" />
        </div>
        <div><input type="button" name="" id="qsearch" value="搜索" /></div>
    </section>
    <section class="sec-nav sec-hide">
        <!--sec-hide-->
        <ul class="clearfix" >


            <li class="clearfix">
                <p class="view"><img src="<%=basePath%>images/ea/office/contract/selectp/view.png"/>查看</p>
            </li>

            <li class="clearfix">
                <p class="del"><img src="<%=basePath%>images/ea/office/contract/selectp/del.png"/>删除</p>
            </li>
        </ul>
    </section>
    <section class="sec-list" id="pc-sec">
        <ul class="ul">


            <li class="clearfix" id="${item.docId}">
                <div class="title-pc">

                    <div class="sex">
                        选择
                    </div>

                    <div class="docNum-p" title="公文编号">公文编号</div>
                    <div class="docNum-p" title="正式编号">正式编号</div>
                    <div class="title-p" title="标题">标题</div>

                    <%--<div class="theme-p" title="主题"> 主题</div>--%>


                    <div class="docType-p" title="公文类型">公文类型</div>


                    <div class="emergencyType-p" title="文件缓急">缓急</div>

                    <div class="com-p" title="拟稿公司">拟稿公司</div>
                    <div class="dept-p" title="拟稿部门">拟稿部门</div>
                    <div class="draft-p" title="拟稿人">拟稿人</div>
                    <div class="date-p" title="拟稿日期">拟稿日期</div>


                </div>


            </li>
            <c:forEach  items="${pageForm.list}" var="item" varStatus="v">


                <c:if test="${v.index+1 eq fn:length(pageForm.list)}">
                    <li class="clearfix last1" id="${item.docId}">
                </c:if>
                <c:if test="${v.index+1 ne fn:length(pageForm.list)}">
                    <li class="clearfix" id="${item.docId}">

                </c:if>
                <div class="title-pc">
                    <div class="sex">
                        <img class="img-01" src="<%=basePath%>/images/ea/office/contract/selectp/img_02.png">
                        <img class="img-02" src="<%=basePath%>/images/ea/office/contract/selectp/img_03.png"></div>

                    <div class="docNum-p" title="${item.docNum}">${item.docNum}</div>
                    <div class="docNum-p" title="${item.formalNum}">${item.formalNum}</div>
                    <div class="title-p" title="${item.title}">${item.title eq null?'无':item.title}</div>

                    <%--<div class="theme-p" title="${item.theme}"> ${item.theme eq null?'无':item.theme}</div>--%>


                    <c:choose>
                        <c:when test='${item.docType=="aa"}'><div class="docType-p" title="董事会会议决定文件">董事会会议决定文件</div></c:when>
                        <c:when test='${item.docType=="bb"}'><div class="docType-p" title="董事长办公室文件">董事长办公室文件</div></c:when>
                        <c:when test='${item.docType=="cc"}'><div class="docType-p" title="总裁办公室文件">总裁办公室文件</div></c:when>
                        <c:when test='${item.docType=="dd"}'><div class="docType-p" title="总部人事处文件">总部人事处文件</div></c:when>
                        <c:when test='${item.docType=="ee"}'><div class="docType-p" title="总部办公室文件">总部办公室文件</div></c:when>
                        <c:when test='${item.docType=="ff"}'><div class="docType-p" title="总部财务处文件">总部财务处文件</div></c:when>
                        <c:when test='${item.docType=="gg"}'><div class="docType-p" title="总部教务(生产)处文件">总部教务(生产)处文件</div></c:when>
                        <c:when test='${item.docType=="hh"}'><div class="docType-p" title="总部营销处文件">总部营销处文件</div></c:when>
                        <c:when test='${item.docType=="jj"}'><div class="docType-p" title="总部教务部文件">总部教务部文件</div></c:when>
                        <c:otherwise><div class="docType-p" title="总部服务(创收)平台">总部服务(创收)平台</div></c:otherwise>
                    </c:choose>



                    <c:choose>
                        <c:when test='${item.emergencyType=="p"}'><div class="emergencyType-p" title="普通">普通</div></c:when>
                        <c:when test='${item.emergencyType=="j"}'><div class="emergencyType-p" title="急件">急件</div></c:when>
                        <c:when test='${item.emergencyType=="t"}'><div class="emergencyType-p" title="特急">特急</div></c:when>
                        <c:otherwise><div class="emergencyType-p" title="普通">普通</div></c:otherwise>
                    </c:choose>


                    <div class="com-p" title="${item.companyName eq null?'无':item.companyName}">${item.companyName eq null?'无':item.companyName}</div>
                    <div class="dept-p" title="${item.deptNameOfDraft eq null?'无':item.deptNameOfDraft}">${item.deptNameOfDraft eq null?'无':item.deptNameOfDraft}</div>
                    <div class="draft-p" title="${item.drafterName eq null?'无':item.drafterName}">${item.drafterName eq null?'无':item.drafterName}</div>
                    <div class="p-wq date-p" title="${item.guidangTime eq null?'无':fn:substring(item.guidangTime,0,10)}">${item.guidangTime eq null?'无':fn:substring(item.guidangTime,0,10)}</div>

                </div>


                </li>
            </c:forEach>



        </ul>
    </section>
    <section class="sec-list" id="phone-sec">
        <ul class="ul">
            <c:forEach  items="${pageForm.list}" var="item" varStatus="v">


                <c:if test="${v.index+1 eq fn:length(pageForm.list)}">
                    <li class="clearfix last1" id="${item.docId}">
                </c:if>
                <c:if test="${v.index+1 ne fn:length(pageForm.list)}">
                    <li class="clearfix" id="${item.docId}">

                </c:if>
                <div class="sex">
                    <img class="img-01" src="<%=basePath%>/images/ea/office/contract/selectp/img_02.png">
                    <img class="img-02" src="<%=basePath%>/images/ea/office/contract/selectp/img_03.png"></div>
                <p class="title">${item.title}</p>
                <span style="display: none;" class="status">${item.status}</span>
                <span style="display: none;" class="companyName">${item.companyName}</span>
                <span style="display: none;" class="companyID">${item.companyID}</span>
                <span style="display: none;" class="scene">${item.scene}</span>
                <span style="display: none;" class="specificTemplate">${item.specificTemplate}</span>
                <p class="p-wq">${fn:substring(item.draftTime,0,10)}</p>
                </li>
            </c:forEach>


        </ul>
    </section>
</div>

<div class="con">
    <div class="div-sel">
        <button class="btn" id="bd"><span>本地上传</span></button>
        <button class="btn" id="lineedit"><span>在线编辑</span></button>

    </div>
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

<div class="loading">
    <div class="div-box">
        <img class="close-tingyong" src="<%=basePath%>images/ea/office/contract/selectp/loading2.gif" alt="" />
        <p>正在准备文档...</p>
    </div>
</div>
</body>
<script>
    window.onload =  function () {



        var clientWidth = document.documentElement.clientWidth;
        if(clientWidth>=960){
            $("#phone-sec").remove();


        }else {
            $("#pc-sec").remove();
        }
    }



    window.onresize = function () {

        var u = navigator.userAgent;
        var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
        var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端


        if(isAndroid==false&&isiOS==false) {
            var clientWidth = document.documentElement.clientWidth;
            if(clientWidth>=960){
                $("#phone-sec").remove();


            }else {
                $("#pc-sec").remove();
            }
            document.location.reload();
        }



    }
</script>
</html>
