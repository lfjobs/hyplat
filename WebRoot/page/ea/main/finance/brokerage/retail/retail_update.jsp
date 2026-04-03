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
    <title>产品零售价设计-修改</title>
    <link type="text/css" rel="stylesheet" href="<%=basePath%>css/ea/finance/brokerage/style.css">
    <script type="application/javascript" src="<%=basePath%>js/jquery-2.1.1.min.js"></script>
    <script type="text/javascript">
        var basePath = '<%=basePath%>';
    </script>
</head>
<body>
<section class="commission des">
    <!--产品零售价设计-->
    <article>
        <!--头部-->
        <header>
            产品零售价设计
        </header>
        <!--头部 end-->
        <!--内容-->
        <figure>
            <div class="head">
                <input type="button" value="关闭" id="close">
                <input type="button" value="保存" id="sure">
                <input type="hidden" value="${ppid}" id="ppid"/>
            </div>
            <div class="grid-1">

                <div class="right" style="display: none">
                    <div>选择产品</div>
                    <img src="<%=basePath%>images/ea/finance/brokerage/images/search.png" alt="" id="search"><input
                        type="hidden" value="业务佣金分配比例" readonly="readonly">
                </div>
            </div>
            <div class="tab-com">
                <table width="1700" class="tit">
                    <thead>
                    <tr>
                        <th>条码</th>
                        <th>产品名称</th>
                        <th>展示零售价</th>
                        <th>系统零售价</th>
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
                    <form id="updateRetail">
                        <table width="1700">
                            <tbody>
                            <tr>
                                <td class="td-0" style="display: none">
                                    <input type="text" name="proSetup.suid" value="${pRetails[0]}"
                                           class="">
                                </td>
                                <input type="hidden" id="brokerages" name="proSetup.proxyprice"
                                       class="calculate brokerages"
                                       value="${pRetails[4]}"><%--代理佣金和--%>
                                <td class="td-1"><input type="text" value="${product.barCode}" readonly="readonly"
                                                        class=""></td>
                                <td class="td-2"><input type="text" value="${product.goodsName}" readonly="readonly"
                                                        name="ppname"
                                                        class="">
                                </td>
                                <td class="td-4"><input type="number" value="${product.price}" id="showRetail"
                                                        class="calculate showRetail"></td>
                                <td class="td-3"><input type="number" value="${pRetails[1]}" class="calculate rePrice"
                                                        data-value="${pRetails[1]}" id="rePrice"
                                                        name="proSetup.rePrice">
                                </td>

                                <td class="td-5"><input type="number" value="${pRetails[2]}" placeholder="请输入"
                                                        id="cost" class="calculate cost"
                                                        name="proSetup.efPrice" data-value="${pRetails[2]}"></td>
                                <td class="td-6">
                                    <!--[if supportFields]>
                                    <c:forEach items="${brokerageList}" var="blist" varStatus="status">
                                        <c:if test="${blist[2]==agencyTypeList.get(0).ppID}">
                                    <![endif]-->
                                    <input type="number" value="${blist[1]}" id="sbtz" class="calculate sbtz"
                                           name="mapPro['sbtz']" data-value="${blist[1]}">
                                    <input type="hidden" value="${blist[0]}" name="mapId['sbtzId']">
                                    <!--[if supportFields]>
                                    </c:if>
                                    </c:forEach>
                                    <![endif]-->
                                </td>
                                <td class="td-7">
                                    <!--[if supportFields]>
                                    <c:forEach items="${brokerageList}" var="blist" varStatus="status">
                                        <c:if test="${blist[2]==agencyTypeList.get(1).ppID}">
                                    <![endif]-->
                                    <input type="number" value="${blist[1]}" id="tp" name="mapPro['tp']"
                                           data-value="${blist[1]}" class="calculate tp">
                                    <input type="hidden" value="${blist[0]}" name="mapId['tpId']">
                                    <!--[if supportFields]>
                                    　   </c:if>
                                    </c:forEach>
                                    <![endif]-->
                                </td>
                                <td class="td-8">
                                    <!--[if supportFields]>
                                    <c:forEach items="${brokerageList}" var="blist" varStatus="status">
                                        　
                                        <c:if test="${blist[2]==agencyTypeList.get(2).ppID}">
                                    <![endif]-->
                                    <input type="number" value="${blist[1]}" id="sbaz" class="calculate sbaz"
                                           name="mapPro['sbaz']" data-value="${blist[1]}">
                                    <input type="hidden" value="${blist[0]}" name="mapId['sbazId']">
                                    <!--[if supportFields]>
                                    　</c:if>
                                    </c:forEach>
                                    <![endif]-->
                                </td>
                                <td class="td-9">
                                    <!--[if supportFields]>
                                    <c:forEach items="${brokerageList}" var="blist" varStatus="status">
                                        　
                                        <c:if test="${blist[2]==agencyTypeList.get(3).ppID}">
                                    <![endif]-->
                                    <input type="number" value="${blist[1]}" id="sjdl" class="calculate sjdl"
                                           name="mapPro['sjdl']" data-value="${blist[1]}">
                                    <input type="hidden" value="${blist[0]}" name="mapId['sjdlId']">
                                    <!--[if supportFields]>
                                    　</c:if>
                                    </c:forEach>
                                    <![endif]-->
                                </td>
                                <td class="td-10">
                                    <!--[if supportFields]>
                                    <c:forEach items="${brokerageList}" var="blist" varStatus="status">
                                        　
                                        <c:if test="${blist[2]==agencyTypeList.get(4).ppID}">
                                    <![endif]-->
                                    <input type="number" value="${blist[1]}" id="xjdl" class="calculate xjdl"
                                           name="mapPro['xjdl']" data-value="${blist[1]}">
                                    <input type="hidden" value="${blist[0]}" name="mapId['xjdlId']">
                                    <!--[if supportFields]>
                                    　</c:if>
                                    </c:forEach>
                                    <![endif]-->
                                </td>
                                <td class="td-11">
                                    <!--[if supportFields]>
                                    <c:forEach items="${brokerageList}" var="blist" varStatus="status">
                                        　
                                        <c:if test="${blist[2]==agencyTypeList.get(5).ppID}">
                                    <![endif]-->
                                    <input type="number" value="${blist[1]}" id="cjdl" class="calculate cjdl"
                                           name="mapPro['cjdl']" data-value="${blist[1]}">
                                    <input type="hidden" value="${blist[0]}" name="mapId['cjdlId']">
                                    <!--[if supportFields]>
                                    　</c:if>
                                    </c:forEach>
                                    <![endif]-->
                                </td>
                                <td class="td-12">
                                    <!--[if supportFields]>
                                    <c:forEach items="${brokerageList}" var="blist" varStatus="status">
                                        　
                                        <c:if test="${blist[2]==agencyTypeList.get(6).ppID}">
                                    <![endif]-->
                                    <input type="number" value="${blist[1]}" id="khjf" class="calculate khjf"
                                           name="mapPro['khjf']" data-value="${blist[1]}">
                                    <input type="hidden" value="${blist[0]}" name="mapId['khjfId']">
                                    <!--[if supportFields]>
                                    　</c:if>
                                    </c:forEach>
                                    <![endif]-->
                                </td>
                                <td class="td-13"><input type="number" value="${pRetails[3]}" id="ywyj"
                                                         class="calculate ywyj"
                                                         name="proSetup.brokerage" data-value="${pRetails[3]}">
                                </td>
                                <td class="td-14">
                                    <select name="proSetup.tzType" class="selector">
                                        <option value="00" <c:if test="${pRetails[5]=='00'}">selected</c:if>>选择投资类别
                                        </option>
                                        <option value="01" <c:if test="${pRetails[5]=='01'}">selected</c:if>>教练车
                                        </option>
                                        <option value="02" <c:if test="${pRetails[5]=='02'}">selected</c:if>>创客单车
                                        </option>
                                        <option value="03" <c:if test="${pRetails[5]=='03'}">selected</c:if>>超市
                                        </option>
                                    </select>
                                </td>
                            </tr>

                            </tbody>
                        </table>
                    </form>
                </div>
            </div>
            <ul class="footer">
                <li>
                    设计零售价责任人<input type="text" readonly="readonly" value="${cac.staffName}">
                </li>
                <li>
                    设计零售价日期<input type="text" readonly="readonly" value="${myDate}">
                </li>
            </ul>
        </figure>
        <!--内容 end-->
    </article>
    <input type="hidden" value="${flag}" id="flag">
    <!--产品佣金设计 end-->
</section>
<script type="application/javascript">
    $(function () {
        //标识判断
        var flag = $("#flag").val()
        if (flag == '01') {//设置数据为只读
            $("form input").each(function () {
                $(this).attr('readonly', 'readonly')
            });
            $("#sure").attr("disabled", "disabled");
        }
        //关闭修改窗口
        $("#close").click(function () {
            if (confirm("您确定要关闭本页吗？")) {
                window.opener = null;
                window.open('', '_self');
                window.close();
            } else {
            }
        })
        var pPercentage_flag = 0;//价格百分比标识
        var bPercentage_flag = 0;//业务佣金百分比标识
        //数值输入框[number]绑定事件[keyup keydown focus blur]
        $(".cost").on("keyup keydown focus blur", function (event) {
            var ppid = $("#ppid").val();
            var parentNode = $(this).parent().parent();
            var val = parentNode.find(".cost").val();//成本价获取
            //获取各种佣金百分比[批发商品]
            $.ajax({
                type: "POST",
                url: basePath + "ea/wholesale/sajax_ea_getBrokeragePercent.jspa",
                data: {
                    "productPackaging.ppID": ppid
                },
                success: function (data) {
                    var member = eval("(" + data + ")");
                    //console.log(member)
                    if (member.code == '200') {
                        //系统零售价判断获取
                        if (member.pPercentage == null) {
                            pPercentage_flag++;
                            if (pPercentage_flag == 1) {
                                alert("您还没有设置零售价百分比！");
                                url = basePath + "ea/percentage/ea_toPPercentageAdd.jspa"
                                window.open(url);
                            }
                            return false;
                        } else {
                            var retail = member.pPercentage.retail;
                            retail = val * (retail / 100 + 1);
                            //retail = retail.toFixed(2);
                            parentNode.find(".rePrice").val(retail.toFixed(4))//系统零售价
                            //展示零售价判断获取
                            if (member.beanList == null || member.beanList.length == 0) {
                                parentNode.find(".showRetail").val(retail.toFixed(2))//展示零售价[系统零售价]
                            } else {
                                var showRetail = retail * (member.beanList[0][1] / 100 + 1);
                                parentNode.find(".showRetail").val(showRetail.toFixed(2))//展示零售价
                            }
                            //佣金判断获取
                            if (member.bPercentage == null) {
                                bPercentage_flag++;
                                if (bPercentage_flag == 1) {
                                    alert("您还没有设置业务佣金百分比！");
                                    url = basePath + "ea/percentage/ea_toBPercentageAdd.jspa"
                                    window.open(url);
                                }
                                return false;
                            } else {
                                var commission = retail - val;//佣金（零售价-成本价）
                                //计算设备投资佣金
                                var bsbtz = member.bPercentage.sbtz;
                                var sbtz = commission * (bsbtz / 100);
                                sbtz = sbtz.toFixed(4);
                                parentNode.find(".sbtz").val(sbtz);
                                //计算贴牌佣金
                                var btp = member.bPercentage.tp;
                                var tp = commission * (btp / 100);
                                tp = tp.toFixed(4);
                                parentNode.find(".tp").val(tp);
                                //计算设备安装佣金
                                var bsbaz = member.bPercentage.sbaz;
                                var sbaz = commission * (bsbaz / 100);
                                sbaz = sbaz.toFixed(4);
                                parentNode.find(".sbaz").val(sbaz);
                                //计算省级代理佣金
                                var bsjdl = member.bPercentage.sjdl;
                                var sjdl = commission * (bsjdl / 100);
                                sjdl = sjdl.toFixed(4);
                                parentNode.find(".sjdl").val(sjdl);
                                //计算县级代理佣金
                                var bxjdl = member.bPercentage.xjdl;
                                var xjdl = commission * (bxjdl / 100);
                                xjdl = xjdl.toFixed(4);
                                parentNode.find(".xjdl").val(xjdl);
                                //计算村级代理佣金
                                var bcjdl = member.bPercentage.cjdl;
                                var cjdl = commission * (bcjdl / 100);
                                cjdl = cjdl.toFixed(4);
                                parentNode.find(".cjdl").val(cjdl);
                                //计算客户积分佣金
                                var bkhjf = member.bPercentage.khjf;
                                var khjf = commission * (bkhjf / 100);
                                khjf = khjf.toFixed(4);
                                parentNode.find(".khjf").val(khjf);
                                //计算业务佣金
                                /*  var bywyj = member.bPercentage.ywyj;
                                 var ywyj = commission * (bywyj / 100);*/
                                var ywyj = commission - sbtz - tp - sbaz - sjdl - xjdl - cjdl - khjf;
                                ywyj = ywyj.toFixed(4);
                                parentNode.find(".ywyj").val(ywyj);
                                //计算代理佣金和
                                var brokerages = commission - ywyj;
                                brokerages = brokerages.toFixed(4);
                                parentNode.find(".brokerages").val(brokerages);
                            }
                        }

                    } else if (member.code == '400') {
                        //未登录
                        location.href = basePath + "page/ea/not_login.jsp";
                    } else {
                        alert("获取数据异常！");
                    }

                },
                error: function (data) {
                    alert("获取数据异常！");
                },
                dateType: "json"
            });

        });
        $("input[type='number']").not(".cost").not(".ywyj").on("keyup keydown focus blur", function (event) {
            var parentNode = $(this).parent().parent();
            var sbtz1 = Number(isNaN(parentNode.find(".sbtz").val()) ? "0" : parentNode.find(".sbtz").val())
            var tp1 = Number(isNaN(parentNode.find(".tp").val()) ? "0" : parentNode.find(".tp").val())
            var sbaz1 = Number(isNaN(parentNode.find(".sbaz").val()) ? "0" : parentNode.find(".sbaz").val())
            var sjdl1 = Number(isNaN(parentNode.find(".sjdl").val()) ? "0" : parentNode.find(".sjdl").val())
            var xjdl1 = Number(isNaN(parentNode.find(".xjdl").val()) ? "0" : parentNode.find(".xjdl").val())
            var cjdl1 = Number(isNaN(parentNode.find(".cjdl").val()) ? "0" : parentNode.find(".cjdl").val())
            var khjf1 = Number(isNaN(parentNode.find(".khjf").val()) ? "0" : parentNode.find(".khjf").val())
            var ywyj = parentNode.find(".rePrice").val() - parentNode.find(".cost").val() - sbtz1 - tp1 - sbaz1 - sjdl1 - xjdl1 - cjdl1 - khjf1;
            parentNode.find(".ywyj").val(ywyj.toFixed(4))
        })

        /*ajax异步请求防止重复提交*/
        //设置一个对象来控制是否进入AJAX过程
        var post_flag = false;
        //保存数据
        $("#sure").click(function () {
            var cost_flag = false;
            var num = 1;
            $("form input[type='number']").not(".showRetail").each(function () {
                if (parseFloat($(this).val()) == parseFloat($(this).attr('data-value'))) {
                } else {
                    num = 2;
                }
            });
            if (num == 1) {
                alert("数据没有修改，您不能保存！")
                return
            }
            //系统零售价
            var sbtz = $("#sbtz").val()
            if (sbtz == null || sbtz == '0' || sbtz == '') {
                $(".selector").val("00");
            }
            if (sbtz != null && sbtz != '0' && sbtz != '' && $(".selector").val() == '00') {
                alert("请选择设备投资类别!");
                return;
            }
            var sj = $("#cost").val();
            if(parseFloat(sj)==0){
                alert("成本价不能为0！");
                return;
            }
            var sbtz1 = Number(isNaN($("#sbtz").val()) ? "0" : $("#sbtz").val())
            var tp1 = Number(isNaN($("#tp").val()) ? "0" : $("#tp").val())
            var sbaz1 = Number(isNaN($("#sbaz").val()) ? "0" : $("#sbaz").val())
            var sjdl1 = Number(isNaN($("#sjdl").val()) ? "0" : $("#sjdl").val())
            var xjdl1 = Number(isNaN($("#xjdl").val()) ? "0" : $("#xjdl").val())
            var cjdl1 = Number(isNaN($("#cjdl").val()) ? "0" : $("#cjdl").val())
            var khjf1 = Number(isNaN($("#khjf").val()) ? "0" : $("#khjf").val())
            var facto = $("#rePrice").val() - sbtz1 - tp1 - sbaz1 - sjdl1 - xjdl1 - cjdl1 - khjf1 - $("#ywyj").val();
            if (Number(sj).toFixed(2) == Number(facto).toFixed(2)) {
                //获取所有佣金
                $.each($('.calculate'), function () {
                    var calculate = $(this).val();
                    if (calculate == null || calculate == '' || parseFloat(calculate) < 0) {//判断
                        cost_flag = true;
                        return;
                    }
                })
                if (cost_flag == true) {
                    alert("成本价、零售价和佣金不能为空,并且为非负数!");
                    return "";
                }
                //如果正在提交则直接返回，停止执行
                if (post_flag) {
                    alert("正在提交请稍后...")
                    return;
                }
                //标记当前状态为正在提交状态
                post_flag = true;
                $.ajax({
                    type: "POST",
                    url: basePath + "ea/retail/sajax_ea_updateRetailBrokerage.jspa",
                    data: $("#updateRetail").serialize(),
                    success: function (data) {
                        var member = eval("(" + data + ")");
                        var code = member.code;
                        post_flag = false; //在提交成功之后将标志标记为可提交状态
                        if (code == "200") {
                            alert("保存成功！");
                            window.opener.location.reload();
                            window.close();
                            location.href = basePath + "ea/retail/ea_selectRetailList.jspa";
                        } else {
                            //未登录
                            location.href = basePath + "page/ea/not_login.jsp";
                        }
                    },
                    error: function (data) {
                        alert("保存异常！");
                    },
                    dateType: "json"
                });
            } else {
                alert("零售价佣金计算有误，请重新计算！")
            }
        })

    })
</script>
</body>
</html>