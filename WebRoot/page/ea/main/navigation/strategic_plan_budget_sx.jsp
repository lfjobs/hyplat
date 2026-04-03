<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd" >
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>项目预算计划流程</title>
    <%@ page language="java" pageEncoding="UTF-8" %>
    <%@ taglib uri="/struts-tags" prefix="s" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ page import="hy.ea.bo.Company" %>
    <%@ page import="hy.ea.bo.CAccount" %>
    <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
                + request.getServerName() + ":" + request.getServerPort()
                + path + "/";
        Company c = (Company) session.getAttribute("currentcompany");
        CAccount ca = (CAccount) session.getAttribute("account");
    %>
    <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
    <link href="<%=basePath%>css/ea/admin.css" rel="stylesheet"
          type="text/css"/>
    <link href="<%=basePath%>css/css.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet"
          href="<%=basePath%>/js/jqueryplus/treeview/jquery.treeview.css"/>
    <script src="<%=basePath%>/js/jquery.js" type="text/javascript"></script>
    <script src="<%=basePath%>/js/jqueryplus/treeview/jquery.treeview.js"
            type="text/javascript"></script>
    <script type="text/javascript" src="<%=basePath %>/js/dropdown/extendPageMenu.js"></script>

    <script type="text/javascript">

        var basePath = '<%=basePath%>';
        var comid = '<%=c.getCompanyID()%>';
        $(document).ready(function () {

            $("#navigation").treeview();

        });

    </script>
    <style type="text/css">


        #qh_sw {
            width: 15%;
            border: 1px solid #DAE7F6;
        }

        .treeview li {
            margin: 0;
            padding: 1px 0 1px 16px;
        }


    </style>


</head>
<body>
<div class="main_main">
    <table width="100%" cellspacing="0" cellpadding="0" border="2">
        <tr>
            <td id="qh_sw" style="width: 15%" valign="top">
                <div class="qh_gg_nav">
                    &nbsp; <span id="frametitle">项目预算计划</span>

                </div> <!--左边的树 -->

                <ul id="navigation" style="width: 240px;margin-left:15px;" class="filetree">
                    <%--<li ><span class="folder">后勤管理</span>

                        <ul>
                            <li><a target="mainframe"
                                        href="<%=basePath%>ea/productdesign/ea_getListProductdesign.jspa?ghua=ghua"><span class="folder">办公项目规划设计</span></a>

                                <ul>


                                    <li><a target="mainframe"
                                        href="<%=basePath%>ea/promanage/ea_getProjectList.jspa?type=xm"><span class="file" >拟定项目</span></a></li>
                                    <li><a target="mainframe"
                                        href="<%=basePath%>ea/promanage/ea_getProjectList.jspa?type=wxm"><span
                                            class="file">未审核项目</span> </a></li>
                                    <li><a target="mainframe"
                                        href="<%=basePath%>ea/promanage/ea_getProjectList.jspa?type=yxm"><span
                                            class="file">已审核项目</span> </a></li>
                                </ul></li>--%>

                    <li><span class="folder">收入项目预算管理</span>
                        <ul>
                            <li><a target="mainframe"
                                   href="<%=basePath%>ea/costsheet/ea_getCostSheetList.jspa?jumptype=fxlb&billsType=项目收入预算单&zctype=y"><span
                                    class="file">收入项目预算</span> </a>
                            </li>
                            <li><a target="mainframe"
                                   href="<%=basePath%>ea/costsheet/ea_getCostSheetList.jspa?jumptype=fxlb&billsType=项目收入预算单&zctype=t"><span
                                    class="file">收入项目预算调整</span> </a>
                            </li>
                            <li><a target="_blank"
                                   href="<%=basePath%>ea/costsheet/ea_budgetComplete.jspa"><span
                                    class="file">收入项目预算日月季度年预算</span> </a>
                            </li>
                            <li><a target="mainframe"
                                   href="<%=basePath%>ea/costsheet/ea_getProjectDetailList.jspa?type=srmx"><span
                                    class="file">收入项目预算明细</span> </a>
                            </li>
                            <li><a target="mainframe"
                                   href="<%=basePath%>ea/productdesign/ea_getListProductdesign.jspa?ghua=ghua&flexbutton=yongjin"><span
                                    class="file">项目产品佣金设计</span> </a>
                            </li>
                          <%--  <li><a target="mainframe"
                                   href="<%=basePath%>ea/productdesign/ea_getListSupermarketDesign.jspa?ghua=ghua&flexbutton=yongjin&chaoshi=platform"><span
                                    class="file">超市产品佣金设计</span> </a>
                            </li>--%>
                            <li id="xfbz" style="display: none;"><a target="mainframe"
                                                                    href="<%=basePath%>/ea/setsubsidize/ea_listSub.jspa?"><span
                                    class="file">消费补助设置</span> </a>
                            </li>
                           <%-- <li><a target="mainframe"
                                   href="<%=basePath%>ea/retail/ea_selectRetailList.jspa"><span
                                    class="file">零售产品佣金设计</span> </a>
                            </li>--%>
                            <%--<li><a target="mainframe"
                                   href="<%=basePath%>ea/wholesale/ea_selectWholesaleList.jspa"><span
                                    class="file">批发产品佣金设计</span> </a>
                            </li>
                            <li><a target="mainframe"
                                   href="<%=basePath%>ea/vip/ea_selectVipList.jspa"><span
                                    class="file">VIP产品佣金设计</span> </a>
                            </li>
                            <li><a target="mainframe"
                                   href="<%=basePath%>ea/activity/ea_selectActivityList.jspa?activityType=00"><span
                                    class="file">普通活动产品佣金设计</span> </a>
                            </li>
                            <li><a target="mainframe"
                                   href="<%=basePath%>ea/activity/ea_selectActivityList.jspa?activityType=01"><span
                                    class="file">特价活动产品佣金设计</span> </a>
                            </li>--%>
                        </ul>
                    </li>

                    <%--</ul>
                </li>--%>
                    <li><span class="folder">支出项目预算管理</span>
                        <ul>
                            <li><a target="mainframe"
                                   href="<%=basePath%>ea/costsheet/ea_getCostSheetList.jspa?jumptype=fxlb&billsType=项目支出预算单&zctype=cg"><span
                                    class="file">常规支出项目预算</span></a>

                            </li>
                            <li><a target="mainframe"
                                   href="<%=basePath%>ea/costsheet/ea_getCostSheetList.jspa?jumptype=fxlb&billsType=项目支出预算单&zctype=fcg"><span
                                    class="file">非常规支出项目预算</span></a>

                            </li>
                            <li><a target="mainframe"
                                   href="<%=basePath%>ea/costsheet/ea_getProjectDetailList.jspa?type=zcmx"><span
                                    class="file">支出项目预算明细</span></a>

                            </li>


                        </ul>
                    </li>

                    <li><span class="folder">招标比价审核管理</span>

                        <ul>
                            <li><a target="_blank"
                                   href="<%=basePath%>ea/product/ea_getBudgetGoodList.jspa?result=first&bjstatus=bj"><span
                                    class="file">比价管理</span> </a></li>
                            <li><a target="_blank"
                                   href="<%=basePath%>ea/product/ea_getBudgetGoodList.jspa?bjstatus=wqrbj"><span
                                    class="file">未确定比价</span> </a></li>
                            <li><a target="_blank"
                                   href="<%=basePath%>ea/product/ea_getBudgetGoodList.jspa?bjstatus=yqrbj"><span
                                    class="file">已确定比价</span> </a></li>

                        </ul>
                    </li>

                    <li><span class="folder">项目收支余额管理</span>
                        <ul>
                            <li><a target="mainframe"
                                   href="<%=basePath%>ea/costsheet/ea_getCostSheetList.jspa?jumptype=fxlb&billsType=项目收入预算单&zctype=y&yg=1"><span
                                    class="file">项目收入预算</span> </a></li>
                            <li><a target="mainframe"
                                   href="<%=basePath%>ea/costsheet/ea_getCostSheetList.jspa?jumptype=fxlb&billsType=项目支出预算单&zctype=&yg=1"><span
                                    class="file">项目支出预算</span> </a></li>
                            <li><a target="_blank"
                                   href="<%=basePath%>/ea/costsheet/ea_getProjectSzy.jspa?yk=y"><span
                                    class="file">项目收支余预算</span> </a></li>
                            <li><a target="_blank"
                                   href="<%=basePath%>/ea/costsheet/ea_getProjectSzy.jspa?yk=k"><span
                                    class="file">项目盈亏预算管理</span> </a></li>
                        </ul>
                    </li>
                </ul>
            </td>
            <td style="width: 85%;" valign="top">
                <iframe id="mainframe" name="mainframe"
                        src=""
                        frameborder="0" style="width: 100%;"></iframe>
            </td>

        </tr>
    </table>
</div>
<script type="text/javascript">
    $(function () {
        $(window).resize(function () {
            setTimeout(function () {
                $("#navigation").height($(window).height() - 30);
                // $("#mainframe").css({"height" : $(window).height() - 5 + "px"});
            }, 100);
        });
        $("#navigation").height($(window).height() - 30);
        $("#mainframe").css({
            "height": $(window).height() + "px"
        });
        if (comid != null && comid == 'company201009046vxdyzy4wg0000000065') {
            $("#xfbz").show();
        }
    });
</script>
</body>


</html>
