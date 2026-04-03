<%@ page language="java" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no"/>
    <title>产品活动价设计-修改</title>
    <link type="text/css" rel="stylesheet" href="<%=basePath%>css/ea/finance/brokerage/style.css">
    <script type="application/javascript" src="<%=basePath%>js/jquery-2.1.1.min.js"></script>
    <script type="text/javascript">
    var basePath = '<%=basePath%>';
    var pageNumber = 0;
    var activityType = '${activityType}';//活动类型
    var activityId = '${activityId}';//活动id
    </script>
    <script type="application/javascript" src="<%=basePath%>js/ea/finance/brokerage/activity/activity_brokerage_update.js"></script>
</head>
<body>
<section class="commission des">
    <!--产品活动价设计-->
    <article>
        <!--头部-->
        <header>
            产品活动价设计
        </header>
        <!--头部 end-->
        <!--内容-->
        <figure>
            <div class="head">
                <input type="button" value="关闭" id="close">
                <input type="button" value="保存" id="sure">
            </div>
            <div class="grid-1">
                <div class="right" style="display: none">
                    <div>选择产品</div>
                    <img src="<%=basePath%>images/ea/finance/brokerage/images/search.png" alt="" id="search"><input
                        type="hidden" value="业务佣金分配比例" readonly="readonly">
                </div>
            </div>
            <div class="tab-com">
                <table style="display: none" id="first_td">
                    <tr><%--表格克隆模板--%>
                        <td class="td" style="display: none">
                            <input type="hidden" class="ppid" data-id="ppid" name="">
                            <input type="hidden" class="actPriceId" data-id="actPriceId" name="">
                        </td>
                        <td class="td-0" style="display: none">
                            <input type="number" class="calculate brokerages" data-id="brokerages" name="">
                        </td>
                        <td class="td-1"></td>
                        <td class="td-2"></td>
                        <td class="td-4"><input type="number" value="0" class="calculate showActPrice"></td>
                        <td class="td-3"><input type="number" value="0" class="calculate actPrice" name=""
                                                data-id="actPrice">
                        </td>
                        <td class="td-5"><input type="number" placeholder="请输入" class="calculate cost"
                                                data-id="factory"></td>
                        <td class="td-6">
                            <input type="number" value="0" class="calculate sbtz" name="" data-id="sbtz">
                            <input type="hidden" value="0" class="sbtzId" name="" data-id="sbtzId">
                        </td>
                        <td class="td-7">
                            <input type="number" value="0" class="calculate tp" name="" data-id="tp">
                            <input type="hidden" value="0" class="tpId" name="" data-id="tpId">
                        </td>
                        <td class="td-8">
                            <input type="number" value="0" class="calculate sbaz" name="" data-id="sbaz">
                            <input type="hidden" value="0" class="sbazId" name="" data-id="sbazId">
                        </td>
                        <td class="td-9">
                            <input type="number" value="0" class="calculate sjdl" name="" data-id="sjdl">
                            <input type="hidden" value="0" class="sjdlId" name="" data-id="sjdlId">
                        </td>
                        <td class="td-10">
                            <input type="number" value="0" class="calculate xjdl" name="" data-id="xjdl">
                            <input type="hidden" value="0" class="xjdlId" name="" data-id="xjdlId">
                        </td>
                        <td class="td-11">
                            <input type="number" value="0" class="calculate cjdl" name="" data-id="cjdl">
                            <input type="hidden" value="0" class="cjdlId" name="" data-id="cjdlId">
                        </td>
                        <td class="td-12">
                            <input type="number" value="0" class="calculate khjf" name="" data-id="khjf">
                            <input type="hidden" value="0" class="khjfId" name="" data-id="khjfId">
                        </td>
                        <td class="td-13"><input type="number" value="0" class="calculate ywyj" name=""
                                                 data-id="brokerage">
                        </td>
                        <td class="td-14">
                            <select name="" class="selector" data-id="investType">
                                <option value="00">选择投资类别</option>
                                <option value="01">教练车</option>
                                <option value="02">创客单车</option>
                                <option value="03">超市</option>
                            </select>
                        </td>
                        <td class="del"><img src="<%=basePath%>images/ea/finance/brokerage/images/del.png"></td>
                    </tr>
                </table>

                <table width="1700" class="tit">
                    <thead>
                    <tr>
                        <th>条码</th>
                        <th>产品名称</th>
                        <th>展示活动价</th>
                        <th>系统活动价</th>
                        <th>成本价</th>
                        <c:forEach items="${agencyTypeList}" var="alist">
                            <th>${alist.goodsName}</th>
                        </c:forEach>
                        <th>业务佣金</th>
                        <th>设备投资类别</th>
                    </tr>
                    </thead>
                </table>
                <div class="tab-com-box">
                    <form id="updateActivity">
                        <input type="hidden" value="${agencyTypeList.get(0).ppID}" name="actPrice.sbtzId" id="sbtzId">
                        <input type="hidden" value="${agencyTypeList.get(1).ppID}" name="actPrice.tpId" id="tpId">
                        <input type="hidden" value="${agencyTypeList.get(2).ppID}" name="actPrice.sbazId" id="sbazId">
                        <input type="hidden" value="${agencyTypeList.get(3).ppID}" name="actPrice.sjdlId" id="sjdlId">
                        <input type="hidden" value="${agencyTypeList.get(4).ppID}" name="actPrice.xjdlId" id="xjdlId">
                        <input type="hidden" value="${agencyTypeList.get(5).ppID}" name="actPrice.cjdlId" id="cjdlId">
                        <input type="hidden" value="${agencyTypeList.get(6).ppID}" name="actPrice.khjfId" id="khjfId">
                        <input type="hidden" value="" name="actPrice.activityId" id="activityId">
                        <table width="1756" id="product">

                        </table>
                    </form>
                </div>
            </div>
            <ul class="footer">
                <li>
                    设计活动价责任人<input type="text" readonly="readonly" value="${cac.staffName}">
                </li>
                <li>
                    设计活动价日期<input type="text" readonly="readonly" value="${myDate}">
                </li>
            </ul>
        </figure>
        <!--内容 end-->
    </article>
</section>
<!--删除弹框-->
<div class="alert_">
    <div class="alert">
        <h3>温馨提示</h3>
        <p>是否确定删除</p>
        <div class="btn">
            <input type="button" value="确定" id="qd">
            <input type="button" value="取消" id="qx">
        </div>
    </div>
</div>
</body>
</html>