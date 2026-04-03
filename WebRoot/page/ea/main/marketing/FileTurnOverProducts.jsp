<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd" >
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>成交产品服务</title>
    <%@ page language="java" pageEncoding="UTF-8" %>
    <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
                + request.getServerName() + ":" + request.getServerPort()
                + path + "/";
    %>
    <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
    <link href="<%=basePath%>css/ea/admin.css" rel="stylesheet"
          type="text/css"/>
    <link href="<%=basePath%>css/css.css" rel="stylesheet" type="text/css"/>

    <script type="text/javascript">
        var basePath = '<%=basePath%>';
    </script>
    <style type="text/css">
        #doctree {
            position: absoute;
            padding-top: 0cm;
            margin-top: 0px;
            width: 100%;
            background-color: #FFFFFF;
            float: left;
        }

        #qh_sw {
            width: 15%;
            border: 1px solid #DAE7F6;
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
<div class="main_main">
    <table width="100%" cellspacing="0" cellpadding="0" border="2">
    <tr>
        <td id="qh_sw" style="width: 15%" valign="top">
            <div class="qh_gg_nav">
                &nbsp;
                <span id="frametitle">成交产品服务</span>
            </div>
            <!--左边的树 -->
            <div style="overflow-y:scroll;height: 100">
                <ul id="navigation" style="width: 180px;"
                    class="filetree">
                    <li>
                        <span class="folder" id="tit">成交产品服务</span>
                        <ul>
                            <li>
                                <span class="folder">客户建档管理</span>
                                <ul>
                                    <li>
                                        <a href="<%=basePath%>/ea/clinch/ea_getListStudentArchive.jspa?module_Identifier=customer_Deal&view_Identifier=educational_train"
                                           target="admin1"><span
                                                class="file" id="unex">订单客户管理</span></a>
                                    </li>
                                    <li>
                                        <a href="<%=basePath%>/ea/clinch/ea_getSArchiveSheetList.jspa"
                                           target="admin1"><span
                                                class="file" id="ex">学员档案出库单据</span> </a>
                                    </li>
                                </ul>
                            </li>
                            <%-- <li>
                                <a href="<%=basePath%>page/ea/ccompany/scheduledproduct/scheduledproduct_main.jsp" target="admin1"><span
                                    class="file">订单管理</span> </a>
                                    <a href="<%=basePath%>ea/costsheet/ea_getCostSheetList.jspa?jumptype=fxlb&billsType=项目收入预算单&zctype=y" target="admin1"><span
                                    class="file">订单管理</span> </a>
                            </li>
                            <li>
                                <a href="<%=basePath%>page/ea/ccompany/scheduledproduct/scheduledproduct_main.jsp" target="admin1"><span
                                    class="file">订单管理</span> </a>
                                    <a href="<%=basePath%>ea/costsheet/ea_getlisorer.jspa?" target="admin1"><span
                                    class="file">微分金订单管理</span> </a>
                            </li>
                            <li>
                                <a href="<%=basePath%>/ea/transactionservice/ea_getListTransactionService.jspa?" target="admin1"><span
                                    class="file">收货单管理</span> </a>
                            </li> --%>
                            <%-- <li>
                                <a href="<%=basePath%>/ea/advisingclients/ea_getListAdvisingClients.jspa?" target="admin1"><span
                                    class="file">指导客户</span> </a>
                            </li> --%>


                            <li>
                                <span class="folder">成交客户网络服务</span>
                                <ul>
                                    <li>
                                        <a href="<%=basePath%>ea/telmessage/ea_goMessageIndex.jspa?type=1&orgDetail=market"
                                           target="admin1"><span
                                                class="file" id="unex">短信推广管理</span></a>
                                    </li>
                                    <li>
												<span
                                                        class="file" id="ex">邮件推广管理</span>
                                    </li>
                                    <li>
												<span
                                                        class="file" id="ex">博客推广管理</span>
                                    </li>
                                    <li>
                                        <!-- <a href="<%=basePath%>/page/ea/main/microletter/microletter_tree.jsp" target="admin1"> 微信导航菜单  已不用-->
                                        <span
                                                class="folder" id="ex">微信推广管理</span> <!-- </a>  -->
                                        <ul>
                                            <li>
                                                <a href="<%=basePath%>ea/activity/ea_activityList.jspa" target="admin1"><span
                                                        class="file" id="unex">微信活动管理</span></a>
                                            </li>
                                        </ul>
                                    </li>
                                    <li>
												<span
                                                        class="file" id="ex">QQ推广管理</span>
                                    </li>
                                    <li>
												<span
                                                        class="file" id="ex">网站推广管理</span>
                                    </li>
                                </ul>
                            </li>
                            <li>
                                <a href="<%=basePath%>/ea/clinch/ea_getListStudentArchive.jspa?module_Identifier=customer_Deal&view_Identifier=educational_train&dangan=dangan"
                                   target="admin1">
										<span
                                                class="file">教务生产跟踪服务</span> </a>
                            </li>
                            <li>
										<span
                                                class="folder">营销合同流转</span>
                            </li>
                            <li>
										<span
                                                class="folder">营销合同查询</span>
                            </li>
                            </li>
                            <li>
                                <span class="folder">企业商城</span>
                                <ul>
                                    <li><span class="folder">运营商会员中心</span>
                                        <ul>
                                            <li><span class="folder">运营商会员</span>
                                                <ul id="hylb">
                                                    <%-- <li><span class="file">公司</span></li>
                                                        <li><a href="#" target="admin1"><span class="file" ><a href="<%=basePath%>ea/activity/ea_findhehuochuangye.jspa" target="admin1">合伙创业</a></span> </li>
                                                        <li><a href="#" target="admin1"><span class="file" ><a href="<%=basePath%>ea/activity/ea_findweifenjin.jspa" target="admin1">微分金店</a></span></li>
                                                        <li><a href="#" target="admin1"><span class="file" ><a href="<%=basePath%>ea/activity/ea_findls.jspa" target="admin1">代理商会员</a></span></li>
                                                        <li><a href="#" target="admin1"><span class="file" ><a href="<%=basePath%>ea/activity/ea_getxfz.jspa" target="admin1">消费者会员</a></span></li>--%>
                                                </ul>
                                            </li>
                                            <%-- <li><span class="folder">商品消费者会员</span>
                                                    <ul>
                                                     <li> <span class="folder" >供应商管理</a></span>

                                                     <ul>
                                                           <li><span class="file" ><a href="<%=basePath%>ea/activity/ea_getcomporder.jspa?weixinCompanyId=1" target="admin1">订单</a></span></li>
                                                           <li><span class="file" ><a href="<%=basePath%>ea/activity/ea_getcomporder.jspa?weixinCompanyId=2" target="admin1">发货单</a></span></li>
                                                           <li><span class="file" ><a href="<%=basePath%>ea/activity/ea_getcomporder.jspa?weixinCompanyId=3" target="admin1">收货单</a></span></li>
                                                           <li><span class="file" ><a href="<%=basePath%>ea/activity/ea_getcomporder.jspa?weixinCompanyId=4" target="admin1">收货客服</a></span></li>
                                                           <li><span class="file" ><a href="#" target="admin1">我的佣金</a></span></li>
                                                           <li><span class="file" ><a href="<%=basePath%>ea/activity/ea_getcomporder.jspa?weixinCompanyId=5" target="admin1">订单收款</a></span></li>
                                                           <li><span class="file" ><a href="<%=basePath%>ea/activity/ea_getcomporder.jspa?weixinCompanyId=6" target="admin1">佣金分配</a></span></li>
                                                           <li><span class="file" ><a href="<%=basePath%>ea/activity/ea_getjifenlist.jspa" target="admin1">积分管理</a></span></li>



                                                        </ul>
                                                   </li>
                                                   <li> <span class="folder" >平台管理</a></span>

                                                     <ul>
                                                           <li><span class="file" ><a href="<%=basePath%>ea/activity/ea_getcomporder.jspa?weixinCompanyId=1" target="admin1">订单</a></span></li>
                                                           <li><span class="file" ><a href="<%=basePath%>ea/activity/ea_getcomporder.jspa?weixinCompanyId=2" target="admin1">发货单</a></span></li>
                                                           <li><span class="file" ><a href="<%=basePath%>ea/activity/ea_getcomporder.jspa?weixinCompanyId=3" target="admin1">收货单</a></span></li>
                                                           <li><span class="file" ><a href="<%=basePath%>ea/activity/ea_getcomporder.jspa?weixinCompanyId=4" target="admin1">收货客服</a></span></li>
                                                           <li><span class="file" ><a href="#" target="admin1">我的佣金</a></span></li>
                                                           <li><span class="file" ><a href="<%=basePath%>ea/activity/ea_getcomporder.jspa?weixinCompanyId=5" target="admin1">订单收款</a></span></li>
                                                           <li><span class="file" ><a href="<%=basePath%>ea/activity/ea_getcomporder.jspa?weixinCompanyId=6" target="admin1">佣金分配</a></span></li>
                                                           <li><span class="file" ><a href="<%=basePath%>ea/activity/ea_getjifenlist.jspa" target="admin1">积分管理</a></span></li>



                                                        </ul>
                                                   </li>
                                                    <li><span class="folder" >公司管理</span>
                                                        <ul>
                                                           <li><span class="file" ><a href="#" target="admin1">订单</a></span></li>
                                                           <li><span class="file" ><a href="#" target="admin1">发货单</a></span></li>
                                                           <li><span class="file" ><a href="#" target="admin1">收货单</a></span></li>
                                                           <li><span class="file" ><a href="#" target="admin1">收货客服</a></span></li>
                                                           <li><span class="file" ><a href="#" target="admin1">我的佣金</a></span></li>
                                                           <li><span class="file" ><a href="#" target="admin1">订单收款</a></span></li>
                                                           <li><span class="file" ><a href="#" target="admin1">佣金分配</a></span></li>
                                                           <li><span class="file" ><a href="#" target="admin1">积分管理</a></span></li>


                                                        </ul>

                                                    </li>
                                                    <li><span class="folder" >合伙创业管理</span>
                                                      <ul>
                                                           <li><span class="file" ><a href="#" target="admin1">订单</a></span></li>
                                                           <li><span class="file" ><a href="#" target="admin1">发货单</a></span></li>
                                                           <li><span class="file" ><a href="#" target="admin1">收货单</a></span></li>
                                                           <li><span class="file" ><a href="#" target="admin1">收货客服</a></span></li>
                                                           <li><span class="file" ><a href="#" target="admin1">我的佣金</a></span></li>
                                                           <li><span class="file" ><a href="#" target="admin1">订单收款</a></span></li>
                                                           <li><span class="file" ><a href="#" target="admin1">佣金分配</a></span></li>
                                                           <li><span class="file" ><a href="#" target="admin1">积分管理</a></span></li>


                                                        </ul>
                                                    </li>
                                                    <li><span class="folder" >代理商会员管理</span>
                                                      <ul>
                                                           <li><span class="file" ><a href="#" target="admin1">订单</a></span></li>
                                                           <li><span class="file" ><a href="#" target="admin1">发货单</a></span></li>
                                                           <li><span class="file" ><a href="#" target="admin1">收货单</a></span></li>
                                                           <li><span class="file" ><a href="#" target="admin1">收货客服</a></span></li>
                                                           <li><span class="file" ><a href="#" target="admin1">我的佣金</a></span></li>
                                                           <li><span class="file" ><a href="#" target="admin1">订单收款</a></span></li>
                                                           <li><span class="file" ><a href="#" target="admin1">佣金分配</a></span></li>
                                                           <li><span class="file" ><a href="#" target="admin1">积分管理</a></span></li>


                                                        </ul>
                                                     </li>
                                                    <li><span class="folder" >消费者会员管理</span>
                                                      <ul>
                                                           <li><span class="file" ><a href="#" target="admin1">订单</a></span></li>
                                                           <li><span class="file" ><a href="#" target="admin1">发货单</a></span></li>
                                                           <li><span class="file" ><a href="#" target="admin1">收货单</a></span></li>
                                                           <li><span class="file" ><a href="#" target="admin1">收货客服</a></span></li>
                                                           <li><span class="file" ><a href="#" target="admin1">我的佣金</a></span></li>
                                                           <li><span class="file" ><a href="#" target="admin1">订单收款</a></span></li>
                                                           <li><span class="file" ><a href="#" target="admin1">佣金分配</a></span></li>
                                                           <li><span class="file" ><a href="#" target="admin1">积分管理</a></span></li>


                                                        </ul>
                                                     </li>
                                                    </ul>

                                            </li>
                                         --%>
                                        </ul>
                                    </li>
                                </ul>
                            </li>


                            <li>
                                <span class="folder">会员中心</span>
                                <ul>
                                    <li>
                                        <span class="file">商城业主会员名片</span>
                                    </li>
                                    <li>
                                        <span class="folder">商城业主人脉财源</span>
                                    </li>
                                    <li>
                                        <span class="folder">商城收支管理</span>
                                        <ul>
                                            <li id="wd">
                                                <span class="folder">我的商城收支管理</span>
                                                <ul>
                                                    <li>
                                                        <span class="folder">订单管理</span>

                                                        <ul>

                                                            <li><span class="file"><a
                                                                    href="<%=basePath%>ea/bdbill/ea_getcomporder.jspa?&hylb=wd&type=pc"
                                                                    target="admin1">我的订单</a></span></li>
                                                        </ul>
                                                    </li>
                                                    <li>
                                                        <span class="folder">现金管理</span>
                                                        <ul>
                                                            <li>
                                                                <a href="<%=basePath%>ea/bdbill/ea_getskd.jspa?hylb=wd&pl=sk&iisnull=1"
                                                                   target='admin1'><span class='file'>现金收款单管理</span></a>
                                                            </li>
                                                            <li>
                                                                <a href="<%=basePath%>ea/bdbill/ea_getskd.jspa?hylb=wd&pl=zk&iisnull=2"
                                                                   target='admin1'><span class='file'>现金支款单管理</span></a>
                                                            </li>
                                                            <li>
                                                                <span class="file">现金流水</span>
                                                            </li>
                                                            <li>
                                                                <span class="file">佣金收支</span>
                                                            </li>
                                                            <li>
                                                                <span class="file">金币兑换现金</span>
                                                            </li>
                                                            <li>
                                                                <span class="file">库存现金</span>
                                                            </li>
                                                        </ul>
                                                    </li>
                                                    <li>
                                                        <span class="folder">收货管理</span>
                                                        <ul>
                                                            <li>
                                                                <span class="file"><a
                                                                        href="<%=basePath%>ea/consignee/ea_getConsigneeSheetList.jspa?stype=01"
                                                                        target="admin1">收货单</a></span>
                                                            </li>
                                                        </ul>
                                                    </li>
                                                    <li>
                                                        <a href="<%=basePath%>/ea/bdbill/ea_getjbfpmx.jspa?hylb=hy&pl=fl"
                                                           target="admin1"><span class="file">金币分配明细管理</span></a>
                                                    </li>
                                                    <li>
                                                        <span class="folder">聚宝盆金币池</span>
                                                        <ul>
                                                            <li>
                                                                <span class="file">分享收金币</span>
                                                            </li>
                                                            <li>
                                                                <span class="file">充值金币</span>
                                                            </li>
                                                            <li>
                                                                <span class="file">消费产品收到馈赠金币</span>
                                                            </li>
                                                            <li>
                                                                <span class="file">充值金币收到馈赠金币</span>
                                                            </li>
                                                            <li>
                                                                <span class="file">好运当头收到馈赠金币</span>
                                                            </li>
                                                            <li>
                                                                <span class="file">金币兑换现金</span>
                                                            </li>
                                                            <li>
                                                                <span class="file">库存金币</span>
                                                            </li>
                                                            <li>
                                                                <span class="file">金币购物</span>
                                                            </li>
                                                            <li>
                                                                <a href="<%=basePath%>/ea/goldwater/ea_getHomePageInformation.jspa"
                                                                   target="_Blank"><span class="file">金币流水</span></a>
                                                            </li>
                                                        </ul>
                                                    </li>
                                                </ul>
                                            </li>
                                            <li>
                                                <span class="folder">供应商商城收支管理</span>
                                                <ul>
                                                    <li>
                                                        <span class="folder">订单管理</span>
                                                        <ul>
                                                            <li><span class="file"><a
                                                                    href="<%=basePath%>ea/bdbill/ea_getcomporder.jspa?hylb=gys&pl=dd"
                                                                    target="admin1">订单</a></span></li>
                                                        </ul>
                                                    </li>
                                                    <li>
                                                        <span class="folder">收款单管理</span>
                                                        <ul>
                                                            <li><span class="file"><a
                                                                    href="<%=basePath%>ea/bdbill/ea_getskd.jspa??hylb=gys&pl=sk"
                                                                    target="admin1">现金收款单管理</a></span></li>
                                                        </ul>
                                                    </li>
                                                    <li>
                                                        <span class="file"><a
                                                                href="<%=basePath%>ea/bdbill/ea_getcomporder.jspa?hylb=gys&pl=pl"
                                                                target="admin1">批量发货管理</a></span>
                                                    </li>
                                                    <li>
                                                        <a href="<%=basePath%>ea/salesman/ea_getHomePageInformationList.jspa?"
                                                           target="admin1"><span class='file'>出库单管理</span></a></li>
                                                    <li>
                                                        <span class="folder">物流管理</span>
                                                        <ul>
                                                            <li>
                                                                <a target="admin1"
                                                                   href="<%=basePath%>/ea/logstor/ea_getHomePageInformationList.jspa"><span
                                                                        class="file">物流入库</span> </a>
                                                            </li>
                                                            <li>
                                                                <a target="admin1"
                                                                   href="<%=basePath%>/ea/logware/ea_getHomePageInformationList.jspa"><span
                                                                        class="file">物流出库</span> </a>
                                                            </li>
                                                        </ul>
                                                    </li>
                                                    <li>
                                                        <span class="folder">收货管理</span>
                                                        <ul>
                                                            <li>
                                                                <span class="file"><a
                                                                        href="<%=basePath%>ea/consignee/ea_getConsigneeSheetList.jspa?stype=02"
                                                                        target="admin1">收货单</a></span>
                                                            </li>
                                                        </ul>
                                                    </li>
                                                    <li>
                                                        <span class="file"><a
                                                                href="<%=basePath%>ea/refund/ea_getRefundSheetList.jspa?stype=01&type=pc"
                                                                target="admin1">退货管理</a></span>
                                                    </li>
                                                    <li>
                                                        <span class="file"><a
                                                                href="<%=basePath%>ea/refund/ea_findRefundCashList.jspa?stype=01"
                                                                target="admin1">退款管理</a></span>
                                                    </li>
                                                </ul>
                                            </li>
                                            <li id="xj">
                                                <span class="folder">我的下级商城收支管理</span>
                                                <ul>
                                                    <li>
                                                        <span class="folder">订单管理</span>
                                                        <ul id="dd">
                                                        </ul>
                                                    </li>
                                                    <li>
                                                        <span class="folder">现金管理</span>
                                                        <ul id="hy"></ul>
                                                    </li>
                                                    <li>
                                                        <span class="folder">收货管理</span>
                                                        <ul id="sh"></ul>
                                                    </li>
                                                </ul>
                                            </li>
                                        </ul>
                                    </li>
                                    <li>
                                        <span class="file">收货单</span>
                                    </li>
                                    <li>
                                        <span class="file">退货单</span>
                                    </li>
                                    <li>
                                        <span class="file">现金管理</span>
                                    </li>
                                    <li>
                                        <span class="file">聚宝盆金币池</span>
                                    </li>
                                </ul>
                            </li>
                        </ul>
            </div>
        </td>
        <td style="width: 85%;" valign="top">
            <iframe src="" id="mainframe1" style="margin:0px;width:100%;height:560px;" name="admin1" scrolling="no"
                    frameBorder="0"></iframe>
            </iframe>
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
        $("#mainframe").css({"height": $(window).height() + "px"});
        //$("#navigation").treeview();

        var url = basePath + "ea/activity/sajax_getCount.jspa";
        $.ajax({
            url: url,
            type: "get",
            async: false,
            dataType: "json",
            success: function (data) {
                var member = eval("(" + data + ")");
                var nologin = member.nologin;
                if (nologin) {
                    document.location.href = basePath
                        + "page/ea/not_login.jsp";
                }
                var list = member.list;
                var list2 = member.list2;
                var list3 = member.list3;
                if (list.length > 0) {
                    var lihtml = "";
                    for (var i = 0; i < list.length; i++) {
                        var count1 = list2[i];
                        var count2 = list3[i];
                        var sumcount = parseInt(count1) + parseInt(count2);
                        lihtml += "<li><span class='folder'>" + list[i].goodsName + "(" + sumcount + ")" + "</span><ul><li><a href='" + basePath + "ea/activity/ea_findCompord.jspa?custype=" + list[i].model + "&status=2' target='admin1'><span class='file'>企业会员(" + count2 + ")</span></a></li><li><a href='" + basePath + "ea/activity/ea_findhehuochuangye.jspa?custype=" + list[i].model + "&status=1' target='admin1'><span class='file'>个人会员(" + count1 + ")</span></a></li></ul></li>";
                    }
                    $("#hylb").append(lihtml);
                }
            }
        });

        //if(comid=="company201009046vxdyzy4wg0000000025"){
        //$("#wd").hide();
        //}
        var url = basePath + "/ea/bdbill/sajax_gethylb.jspa";
        $.ajax({
            url: url,
            type: "get",
            async: false,
            dataType: "json",
            success: function (data) {
                var member = eval("(" + data + ")");
                var nologin = member.nologin;
                if (nologin) {
                    document.location.href = basePath
                        + "page/ea/not_login.jsp";
                }
                var a = member.a;//当前用户权限表
                var b = member.b;//下级代理
                var c = member.c;//会员类别
                var t = "";

                //if(a!=null){
                //t = a.cusType;
                //if(a.companyId==null&&a.companyId==""&&a.companyId==" "){
                //$("#plfh").hide();
                //}
                //}else{
                $("#plfh").hide();
                //}
                var ddhtml = "";
                var skdhtml = "";
                var ckhtml = "";
                var zkthml = "";
                var shhtml = "";
                var e = "";
                var d = "";
                var staffid = "";


                //if(b.length<=0){
                //$("#xj").hide();
                //}
                /*for(var i=0;i<b.length;i++){
                 var f=b[i];
                 if(f[1]==2){
                 d+=f[0]+",";
                 }
                 }
                 if(t<2){
                 lihtml+="<li><span class='file'>公司企业商城会员订单管理</span></li>";
                 } */
                if (t < 3) {
                    d = "";
                    for (var i = 0; i < b.length; i++) {
                        var f = b[i];
                        if (f[1] == 3) {
                            d += f[0] + ",";
                            staffid += f[2] + ",";
                        }
                    }
                    if (d.length > 0) {
                        ddhtml += "<li><a href='" + basePath + "ea/bdbill/ea_getcomporder.jspa?hylb=" + d + "&pl=dd' target='admin1'><span class='file'>" + c[2][1] + "</span></a></li>";
                        shhtml += "<li><a href='" + basePath + "ea/consignee/ea_getConsigneeSheetList.jspa?stype=03&cusType=" + c[2][0] + "' target='admin1'><span class='file'>" + c[2][1] + "收货单</span></a></li>";
                        //skdhtml+="<li><a href='"+basePath+"ea/bdbill/ea_getskd.jspa?hylb="+staffid+"&pl=sk' target='admin1'><span class='file'>"+c[2][1]+"收款单管理</span></a></li>";
                        //ckhtml+="<li><a href='"+basePath+"ea/salesman/ea_getHomePageInformationList.jspa?&hylb="+d+"' target='admin1'><span class='file'>"+c[2][1]+"出库单管理</span></a></li>";
                        //zkthml+="<li><a href='"+basePath+"ea/bdbill/ea_getskd.jspa?iisnull=2&hylb="+staffid+"&pl=zk' target='admin1'><span class='file'>"+c[2][1]+"支款单管理</span></a></li>";
                        e += "<li><span class='folder'>" + c[2][1] + "现金收支单据管理</span><ul>" + xj(staffid) + "</ul></li>";
                    }
                }
                if (t < 4) {
                    d = "";
                    for (var i = 0; i < b.length; i++) {
                        var f = b[i];
                        if (f[1] == 4) {
                            d += f[0] + ",";
                            staffid += f[2] + ",";
                        }
                    }
                    if (d.length > 0) {
                        ddhtml += "<li><a href='" + basePath + "ea/bdbill/ea_getcomporder.jspa?hylb=" + d + "&pl=dd' target='admin1'><span class='file'>" + c[3][1] + "</span></a></li>";
                        shhtml += "<li><a href='" + basePath + "ea/consignee/ea_getConsigneeSheetList.jspa?stype=03&cusType=" + c[3][0] + "' target='admin1'><span class='file'>" + c[3][1] + "收货单</span></a></li>";
                        //skdhtml+="<li><a href='"+basePath+"ea/bdbill/ea_getskd.jspa?hylb="+staffid+"&pl=sk' target='admin1'><span class='file'>"+c[3][1]+"收款单管理</span></a></li>";
                        //ckhtml+="<li><a href='"+basePath+"ea/salesman/ea_getHomePageInformationList.jspa?&hylb="+d+"' target='admin1'><span class='file'>"+c[3][1]+"出库单管理</span></a></li>";
                        //zkthml+="<li><a href='"+basePath+"ea/bdbill/ea_getskd.jspa?iisnull=2&hylb="+staffid+"&pl=zk' target='admin1'><span class='file'>"+c[3][1]+"支款单管理</span></a></li>";
                        e += "<li><span class='folder'>" + c[3][1] + "现金收支单据管理</span><ul>" + xj(staffid) + "</ul></li>";
                    }
                }
                if (t < 5) {
                    d = "";
                    for (var i = 0; i < b.length; i++) {
                        var f = b[i];
                        if (f[1] == 5) {
                            d += f[0] + ",";
                            staffid += f[2] + ",";
                        }
                    }
                    if (d.length > 0) {
                        ddhtml += "<li><a href='" + basePath + "ea/bdbill/ea_getcomporder.jspa?&hylb=" + d + "&pl=dd' target='admin1'><span class='file'>" + c[4][1] + "</span></a></li>";
                        shhtml += "<li><a href='" + basePath + "ea/consignee/ea_getConsigneeSheetList.jspa?stype=03&cusType=" + c[4][0] + "' target='admin1'><span class='file'>" + c[4][1] + "收货单</span></a></li>";
                        //skdhtml+="<li><a href='"+basePath+"ea/bdbill/ea_getskd.jspa?hylb="+staffid+"&pl=sk' target='admin1'><span class='file'>"+c[4][1]+"收款单管理</span></a></li>";
                        //ckhtml+="<li><a href='"+basePath+"ea/salesman/ea_getHomePageInformationList.jspa?hylb="+d+"' target='admin1'><span class='file'>"+c[4][1]+"出库单管理</span></a></li>";
                        //zkthml+="<li><a href='"+basePath+"ea/bdbill/ea_getskd.jspa?iisnull=2&hylb="+staffid+"&pl=zk' target='admin1'><span class='file'>"+c[4][1]+"支款单管理</span></a></li>";
                        e += "<li><span class='folder'>" + c[4][1] + "现金收支单据管理</span><ul>" + xj(staffid) + "</ul></li>";
                    }
                }
                if (t < 6) {
                    d = "";
                    for (var i = 0; i < b.length; i++) {
                        var f = b[i];
                        if (f[1] == 6) {
                            d += f[0] + ",";
                            staffid += f[2] + ",";
                        }
                    }
                    if (d.length > 0) {
                        ddhtml += "<li><a href='" + basePath + "ea/bdbill/ea_getcomporder.jspa?&hylb=" + d + "&pl=dd' target='admin1'><span class='file'>" + c[6][1] + "</span></a></li>";
                        shhtml += "<li><a href='" + basePath + "ea/consignee/ea_getConsigneeSheetList.jspa?stype=03&cusType=" + c[5][0] + "' target='admin1'><span class='file'>" + c[5][1] + "收货单</span></a></li>";
                        //skdhtml+="<li><a href='"+basePath+"ea/bdbill/ea_getskd.jspa?hylb="+staffid+"&pl=sk' target='admin1'><span class='file'>"+c[5][1]+"收款单管理</span></a></li>";
                        //ckhtml+="<li><a href='"+basePath+"ea/salesman/ea_getHomePageInformationList.jspa?&hylb="+d+"' target='admin1'><span class='file'>"+c[5][1]+"出库单管理</span></a></li>";
                        //zkthml+="<li><a href='"+basePath+"ea/bdbill/ea_getskd.jspa?iisnull=2&hylb="+staffid+"&pl=zk' target='admin1'><span class='file'>"+c[5][1]+"支款单管理</span></a></li>";
                        e += "<li><span class='folder'>" + c[5][1] + "现金收支单据管理</span><ul>" + xj(staffid) + "</ul></li>";
                    }
                }
                if (t < 7) {
                    d = "";
                    for (var i = 0; i < b.length; i++) {
                        var f = b[i];
                        if (f[1] == 7) {
                            d += f[0] + ",";
                            staffid += f[2] + ",";
                        }
                    }
                    if (d.length > 0) {
                        ddhtml += "<li><a href='" + basePath + "ea/bdbill/ea_getcomporder.jspa?&hylb=" + d + "&pl=dd' target='admin1'><span class='file'>" + c[7][1] + "</span></a></li>";
                        shhtml += "<li><a href='" + basePath + "ea/consignee/ea_getConsigneeSheetList.jspa?stype=03&cusType=" + c[5][0] + "' target='admin1'><span class='file'>" + c[5][1] + "收货单</span></a></li>";
                        //skdhtml+="<li><a href='"+basePath+"ea/bdbill/ea_getskd.jspa?hylb="+staffid+"&pl=sk' target='admin1'><span class='file'>"+c[5][1]+"收款单管理</span></a></li>";
                        //ckhtml+="<li><a href='"+basePath+"ea/salesman/ea_getHomePageInformationList.jspa?&hylb="+d+"' target='admin1'><span class='file'>"+c[5][1]+"出库单管理</span></a></li>";
                        //zkthml+="<li><a href='"+basePath+"ea/bdbill/ea_getskd.jspa?iisnull=2&hylb="+staffid+"&pl=zk' target='admin1'><span class='file'>"+c[5][1]+"支款单管理</span></a></li>";
                        e += "<li><span class='folder'>" + c[5][1] + "现金收支单据管理</span><ul>" + xj(staffid) + "</ul></li>";
                    }
                }
                if (t < 9) {
                    d = "";
                    for (var i = 0; i < b.length; i++) {
                        var f = b[i];
                        if (f[1] == 9) {
                            d += f[0] + ",";
                            staffid += f[2] + ",";
                        }
                    }
                    if (d.length > 0) {
                        ddhtml += "<li><a href='" + basePath + "ea/bdbill/ea_getcomporder.jspa?&hylb=" + d + "&pl=dd' target='admin1'><span class='file'>" + c[9][1] + "</span></a></li>";
                        shhtml += "<li><a href='" + basePath + "ea/consignee/ea_getConsigneeSheetList.jspa?stype=03&cusType=" + c[5][0] + "' target='admin1'><span class='file'>" + c[5][1] + "收货单</span></a></li>";
                        //skdhtml+="<li><a href='"+basePath+"ea/bdbill/ea_getskd.jspa?hylb="+staffid+"&pl=sk' target='admin1'><span class='file'>"+c[5][1]+"收款单管理</span></a></li>";
                        //ckhtml+="<li><a href='"+basePath+"ea/salesman/ea_getHomePageInformationList.jspa?&hylb="+d+"' target='admin1'><span class='file'>"+c[5][1]+"出库单管理</span></a></li>";
                        //zkthml+="<li><a href='"+basePath+"ea/bdbill/ea_getskd.jspa?iisnull=2&hylb="+staffid+"&pl=zk' target='admin1'><span class='file'>"+c[5][1]+"支款单管理</span></a></li>";
                        e += "<li><span class='folder'>" + c[5][1] + "现金收支单据管理</span><ul>" + xj(staffid) + "</ul></li>";
                    }
                }
                $("#dd").append(ddhtml);
                //$("#sk").append(skdhtml);
                $("#ck").append(ckhtml);
                //$("#zk").append(zkthml);
                $("#hy").append(e);

                $("#sh").append(shhtml);

            },
            error: function (data) {
                alert("操作失败！");
            }

        });
        //$("#dd").prepend("<li><span class='file'>代理商企业商城会员订单管理</span></li>");
        $("#sjdd").click(function () {
            window.open(basePath + "/ea/hypb/ea_getcomporder.jspa?staid=cstaff20151104IF8I49HSDR0000000001");
        });
        $("#mjdd").click(function () {
            window.open(basePath + "/ea/ghspb/ea_getcomporder.jspa?staid=company201009046vxdyzy4wg0000000025");
        });
        $("#navigation").treeview();
    });
    function xj(staffid) {
        return "<li><span class='file'><a href='" + basePath + "ea/bdbill/ea_getskd.jspa?iisnull=1&pl=sk&hylb=" + staffid + "' target='admin1'>现金收款单管理</a></span></li>" +
            "<li><span class='file'><a href='" + basePath + "ea/bdbill/ea_getskd.jspa?iisnull=2&pl=zk&hylb=" + staffid + "' target='admin1'>现金支款单管理</a></span></li>" +
            "<li><span class='file'>现金流水</span></li>" +
            "<li><span class='file'>佣金收支</span></li>" +
            "<li><span class='file'>金币兑换现金</span></li>" +
            "<li><span class='file'>库存现金</span></li>";
    }
</script>
<link rel="stylesheet"
      href="<%=basePath%>/js/jqueryplus/treeview/jquery.treeview.css"/>
<script src="<%=basePath%>/js/jqueryplus/treeview/jquery.treeview.js"
        type="text/javascript"></script>
</body>

</html>
