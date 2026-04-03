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

    // 设置不要缓存页面
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
    response.setDateHeader("Expires", 0); // 设置过期时间为0.
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/office_ea/contract/mainFrame.css"/>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/ea/office_ea/contract/mainFrame.js?version=20230817" type="text/javascript" charset="utf-8"></script>


    <link rel="stylesheet"
          href="<%=basePath%>/js/jqueryplus/treeview/jquery.treeview.css" />
    <script src="<%=basePath%>/js/jqueryplus/treeview/jquery.treeview.js"
            type="text/javascript"></script>

    <title>&lrm;</title>
    <style type="text/css">
        .filetree span.file {
            line-height: 0.8rem;
            display: inline-block;

        }
        .filetree span.folder {
            color:#343333;
        }
        .treeview li {
            margin: 0;
            padding: 1px 0 1px 16px;
        }

        .numcss {
            color: red;
        }
    </style>
</head>
<body>

<div class="content">
    <div class="main_main">
        <c:if test="${param.ifr ne 'nohead'}">
        <header>
            <ul class="clearfix">
                <li>
                    <a  href = "<%=basePath%>ea/mycenter/ea_myIndex.jspa" target="_self" >
                        <img src="<%=basePath%>images/ea/office/contract/selectp/return.png" >
                    </a>
                </li>
                <li class="title-i">
                    文书合同
                </li>
            </ul>
        </header>
            </c:if>
                <!--左边的树 -->

                <ul id="navigation" style="width: 180px;"
                    class="filetree">
                    <li>
                        <span class="folder" id="tit">文书合同</span>
                        <ul>
                            <li>
                                <span class="folder">拟稿</span>
                                <ul>
                                    <li>
                                        <a href="#" onclick="tonclick('draftlist')"><span
                                                class="file">草稿箱</span><span class="numcss" id="draft">(0)</span>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="#" onclick="tonclick('receivelist')"><span
                                                class="file">收件箱</span><span class="numcss" id="receivebox">(0)</span>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="#" onclick="tonclick('sendedlist')"><span
                                                class="file">已发送</span> </a>
                                    </li>
                                </ul>
                            </li>
                            <li>
                                <span class="folder">审批</span>
                                <ul>
                                    <li>
                                        <a href="#" onclick="tonclick('auditlist')"><span
                                                class="file" id="unex">未审批</span><span class="numcss"
                                                                                         id="approvalno">(0)</span> </a>
                                    </li>
                                    <li>
                                        <a href="#" onclick="tonclick('auditedlist')"><span
                                                class="file" id="ex">已审批</span> </a>
                                    </li>
                                </ul>
                            </li>
                            <c:if test="${param.module != 'news'&&param.module != 'AnnNoti'}">
                                <li>
                                    <span class="folder">盖章签约</span>
                                    <ul>
                                        <li>
                                            <a href="#" onclick="tonclick('seallist')"><span
                                                    class="file" id="unseal">未盖章</span><span class="numcss" id="stampno">(0)</span>
                                            </a>
                                        </li>
                                        <li>
                                            <a href="#" onclick="tonclick('sealedlist')"><span
                                                    class="file" id="seal">已盖章</span> </a>
                                        </li>
                                        <li>
                                            <a href="#" onclick="tonclick('stamp')"><span
                                                    class="file" id="yz">印章管理</span> </a>
                                        </li>
                                    </ul>
                                </li>
                                <li>
                                    <span class="folder" id="gl">发文管理</span>
                                    <ul>
                                        <li>
                                            <a href="#" onclick="tonclick('publishlist')"><span
                                                    class="file" id="unpub">未发公文</span><span class="numcss" id="publish">(0)</span>
                                            </a>
                                        </li>
                                        <li>
                                            <a href="#" onclick="tonclick('publishedlist')"><span
                                                    class="file" id="pub">已发公文</span> </a>
                                        </li>
                                    </ul>
                                </li>
                                <li>
                                    <span class="folder">阅读管理</span>
                                    <ul>
                                        <li>
                                            <a href="#" onclick="tonclick('readlist')"><span
                                                    class="file" id="unread">未阅读</span><span class="numcss" id="read">(0)</span>
                                            </a>
                                        </li>
                                        <li>
                                            <a href="#" onclick="tonclick('readedlist')"><span
                                                    class="file" id="readed">已阅读</span> </a>
                                        </li>
                                    </ul>
                                </li>
                                <li>
                                    <span class="folder" >公文归档</span>
                                    <ul>
                                        <li>
                                            <a href="#" onclick="tonclick('archivelist')"><span
                                                    class="file">已归档</span> </a>
                                        </li>

                                        <li>
                                            <a href="#" onclick="tonclick('archivecate')"><span
                                                    class="file">档案盒管理</span> </a>
                                        </li>
                                    </ul>
                                </li>
                                <li class="mbgl">
                                    <span class="folder" >模板管理</span>
                                    <ul>
                                        <li>
                                            <a href="#" onclick="tonclick('templateType')"><span
                                                    class="file">模板分类</span> </a>
                                        </li>

                                        <li>
                                            <a href="#" onclick="tonclick('template')"><span
                                                    class="file mbname">个人模板</span> </a>
                                        </li>

                                        <li>
                                            <a href="#" onclick="tonclick('sharetemp')"><span
                                                    class="file">共享模板</span> </a>
                                        </li>


                                    </ul>

                                </li>
                                <li class="gr">
                                    <a href="#" onclick="tonclick('sharelist')"><span
                                            class="folder">共享池</span> </a>
                                </li>

                                <li>
                                    <a href="#" onclick="tonclick('recylelist')"><span
                                            class="folder">回收站</span> </a>
                                </li>
                            </c:if>

                            </li>
                        </ul>
                   </li>
                    </ul>


    </div>
    <div class="frame-div">
        <iframe id="mainframe"
                 src="<%=basePath%>ea/contract/ea_getDraftList.jspa?state=draftlist"
                 frameborder="0" style="width: 100%;"></iframe></div>
</div>

<div class="loading">
    <div class="div-box">
        <img class="close-tingyong" src="<%=basePath%>images/ea/office/contract/selectp/loading5.gif" alt="" />
    </div>
</div>
</body>
<script type="text/javascript">
    var module = "<%=session.getAttribute("module")%>"
    var basePath = "<%=basePath%>";
    var companyID = "${param.companyID}";
    var ifr = "${param.ifr}";
    $(document).ready(function() {
        $("#navigation").treeview();


        if(module==""){
             $(".gr").hide();
            $("#yz").text("个人印章");
            $(".mbname").text("个人模板");
        }else{
            $(".gr").show();
            $("#yz").text("印章管理");
            $(".mbname").text("公司模板");

        }

        if(module=="doc"){

            $(".title-i").text("文书流程");

            $("#tit").text("文书流程");
        }else if(module=="contract"){

            $(".title-i").text("合同签约");
            $("#tit").text("合同签约");
        }
    })

</script>
</html>

