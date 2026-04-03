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
    <title>产品批发价设计-添加</title>
    <link type="text/css" rel="stylesheet" href="<%=basePath%>css/ea/finance/brokerage/style.css">
    <script type="application/javascript" src="<%=basePath%>js/jquery-2.1.1.min.js"></script>
    <script type="text/javascript">
        var basePath = '<%=basePath%>';
        var pageNumber = 0;
    </script>
</head>
<body>
<section class="commission des">
    <!--产品批发价设计-->
    <article>
        <!--头部-->
        <header>
            产品批发价设计
        </header>
        <!--头部 end-->
        <!--内容-->
        <figure>
            <div class="head">
                <input type="button" value="关闭" id="close">
                <input type="button" value="保存" id="sure">
            </div>
            <div class="grid-1">

                <div class="right">
                    <div>选择产品</div>
                    <span>
                         <img src="<%=basePath%>images/ea/finance/brokerage/images/search.png" alt="" id="search"><input
                            type="hidden" value="业务佣金分配比例" readonly="readonly">
                    </span>
                </div>
            </div>
            <div class="tab-com">
                <table width="1700" class="tit">
                    <thead>
                    <tr>
                        <th>条码</th>
                        <th>产品名称</th>
                        <th>展示批发价</th>
                        <th>系统批发价</th>
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
                    <form id="addWholesale">
                        <table width="1700">
                            <tbody>
                            <tr>
                                <td class="td-0" style="display: none">
                                    <input type="text" id="ppid" name="wholesale.ppid">
                                </td>
                                <input type="hidden" id="brokerages" name="wholesale.brokerages" class="calculate brokerages">
                                <td class="td-1"></td>
                                <td class="td-2"></td>
                                <td class="td-4"><input type="number" value="0" id="showWholesale" class="calculate showWholesale"></td>
                                <td class="td-3"><input type="number" value="0" id="wholesale" class="calculate wholesale"
                                                        name="wholesale.wholesale"/>
                                </td>
                                <td class="td-5">
                                    <input type="number" placeholder="请输入" id="cost" class="calculate cost"
                                           name="wholesale.factory">
                                </td>
                                <td class="td-6">
                                    <input type="number" value="0" id="sbtz" name="mapPro['sbtz']" class="calculate sbtz">
                                    <input type="hidden" value="${agencyTypeList.get(0).ppID}" name="mapId['sbtzId']">
                                </td>
                                <td class="td-7">
                                    <input type="number" value="0" id="tp" name="mapPro['tp']" class="calculate tp">
                                    <input type="hidden" value="${agencyTypeList.get(1).ppID}" name="mapId['tpId']">
                                </td>
                                <td class="td-8">
                                    <input type="number" value="0" id="sbaz" name="mapPro['sbaz']" class="calculate sbaz">
                                    <input type="hidden" value="${agencyTypeList.get(2).ppID}" name="mapId['sbazId']">
                                </td>
                                <td class="td-9">
                                    <input type="number" value="0" id="sjdl" name="mapPro['sjdl']" class="calculate sjdl">
                                    <input type="hidden" value="${agencyTypeList.get(3).ppID}" name="mapId['sjdlId']">
                                </td>
                                <td class="td-10">
                                    <input type="number" value="0" id="xjdl" name="mapPro['xjdl']" class="calculate xjdl">
                                    <input type="hidden" value="${agencyTypeList.get(4).ppID}" name="mapId['xjdlId']">
                                </td>
                                <td class="td-11">
                                    <input type="number" value="0" id="cjdl" name="mapPro['cjdl']" class="calculate cjdl">
                                    <input type="hidden" value="${agencyTypeList.get(5).ppID}" name="mapId['cjdlId']">
                                </td>
                                <td class="td-12">
                                    <input type="number" value="0" id="khjf" name="mapPro['khjf']" class="calculate khjf">
                                    <input type="hidden" value="${agencyTypeList.get(6).ppID}" name="mapId['khjfId']">
                                </td>
                                <td class="td-13"><input type="number" value="0" id="ywyj" name="wholesale.brokerage"
                                                         class="calculate ywyj">
                                </td>
                                <td class="td-14">
                                    <select name="wholesale.investType" class="selector">
                                        <option value="00">选择投资类别</option>
                                        <option value="01">教练车</option>
                                        <option value="02">创客单车</option>
                                        <option value="03">超市</option>
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
                    设计批发价责任人<input type="text" readonly="readonly" value="${cac.staffName}">
                </li>
                <li>
                    设计批发价日期<input type="text" readonly="readonly" value="${myDate}">
                </li>
            </ul>
        </figure>
        <!--内容 end-->
    </article>
    <div class="alert-com">

        <div class="alert_com">
            <div class="head">
                <h3 style="align-content: center">产品展示</h3>
                <div class="search">
                    <input type="text" placeholder="请输入条码或者名称" id="searchs">
                    <img src="<%=basePath%>images/ea/finance/brokerage/images/search.png" alt="" id="searchProduct">
                </div>
                <input type="button" value="关闭" class="hide">
                <input type="button" value="确定" id="save">
                <div class="page">
                    <a onclick="lastPage()">上一页</a>
                    <a onclick="nextPage()">下一页</a>
                    <a>共<span id="sumPage"></span>页</a>
                    <input type="hidden" id="pageNumber">
                    <input type="hidden" id="pageCount">
                </div>
            </div>
            <div class="tab">
                <table width="958" class="tit">
                    <thead>
                    <tr>
                        <th style="width: 90px;">选择</th>
                        <th style="width: 90px;">序号</th>
                        <th style="width: 200px;">条码</th>
                        <th style="width: 577px;">产品名称</th>
                    </tr>
                    </thead>
                </table>
                <div class="tab-box tab-box2">
                    <table width="958">
                        <tbody id="productList">
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <!--产品佣金设计 end-->
</section>
<script type="application/javascript">
    $(function () {
        //产品弹框显示
        $(".alert-com").show();
        //清空div
        $("#productList").empty();
        //获取没有设置批发价佣金的产品
        productList();
        /*搜索*/
        $("#search").click(function () {
            //产品弹框显示
            $(".alert-com").show();
            //清空div
            $("#productList").empty();
            //获取没有设置批发价佣金的产品
            productList();
        });
        $("#searchProduct").click(function () {
            //清空div
            $("#productList").empty();
            //获取没有设置批发价佣金的产品
            productList();
        });
        //关闭添加窗口
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
                    "productPackaging.ppID":ppid
                },
                success: function (data) {
                    var member = eval("(" + data + ")");
                    //console.log(member)
                    if (member.code == '200') {
                        //系统批发价判断获取
                        if (member.pPercentage == null) {
                            pPercentage_flag++;
                            if (pPercentage_flag == 1) {
                                alert("您还没有设置批发价百分比！");
                                url = basePath + "ea/percentage/ea_toPPercentageAdd.jspa"
                                window.open(url);
                            }
                            return false;
                        } else {
                            var wholesale = member.pPercentage.wholesale;
                            wholesale = val * (wholesale / 100 + 1);
                            //wholesale = wholesale.toFixed(4);
                            parentNode.find(".wholesale").val(wholesale.toFixed(4))//系统批发价
                            //展示批发价判断获取
                            if (member.beanList == null || member.beanList.length == 0) {
                                parentNode.find(".showWholesale").val(wholesale.toFixed(2))//展示批发价[系统批发价]
                            } else {
                                var showWholesale = wholesale * (member.beanList[0][1] / 100 + 1);
                                parentNode.find(".showWholesale").val(showWholesale.toFixed(2))//展示批发价
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
                                var commission = wholesale - val;//佣金（批发价-成本价）
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
            var ywyj = parentNode.find(".wholesale").val() - parentNode.find(".cost").val() - parentNode.find(".sbtz").val() - parentNode.find(".tp").val()
                - parentNode.find(".sbaz").val() - parentNode.find(".sjdl").val() - parentNode.find(".xjdl").val()
                - parentNode.find(".cjdl").val() - parentNode.find(".khjf").val();
            parentNode.find(".ywyj").val(ywyj.toFixed(4))
        })
        /*ajax异步请求防止重复提交*/
        //设置一个对象来控制是否进入AJAX过程
        var post_flag = false;
        //保存数据
        $("#sure").click(function () {
            var cost_flag = false;
            if ($("#ppid").val() == null || $("#ppid").val() == '') {
                alert("您还没有选择产品!");
                return
            }
            var sbtz = $("#sbtz").val()
            if (sbtz == null || sbtz == '0' || sbtz == '') {
                $(".selector").val("00");
            }
            if (sbtz != null && sbtz != '0' && sbtz != '' && $(".selector").val() == '00') {
                alert("请选择设备投资类别!");
                return;
            }
            var facto = $("#wholesale").val() - $("#sbtz").val() - $("#tp").val() - $("#sbaz").val() - $("#sjdl").val() - $("#xjdl").val() - $("#cjdl").val() - $("#khjf").val() - $("#ywyj").val();
            var sj = $("#cost").val();
            if(parseFloat(sj)==0){
                alert("成本价不能为0！");
                return;
            }
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
                    alert("成本价、批发价和佣金不能为空,并且为非负数!");
                    return "";
                }
                //如果正在提交则直接返回，停止执行
                if (post_flag) {
                    console.log("正在提交请稍后...")
                    return;
                }
                //标记当前状态为正在提交状态
                post_flag = true;
                $.ajax({
                    type: "POST",
                    url: basePath + "ea/wholesale/sajax_ea_addBrokeragePercent.jspa",
                    data: $("#addWholesale").serialize(),
                    success: function (data) {
                        var member = eval("(" + data + ")");
                        var code = member.code;
                        post_flag = false; //在提交成功之后将标志标记为可提交状态
                        if (code == "200") {
                            alert("保存成功！");
                            window.opener.location.reload();
                            window.close();
                            location.href = basePath + "ea/wholesale/ea_selectWholesaleList.jspa";
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
                alert("批发价佣金计算有误，请重新计算！")
            }
        })
        /*隐藏弹框*/
        /*关闭*/
        $(".alert_com .head .hide").click(function () {
            $(".alert-com").hide();
        });
        /*确定*/
        $(".alert_com .head #save").click(function () {
            $("input[type='number']").val("");//清空所有数值输入框
            //获取单选框选中的值[产品id]
            var ppidVal = $('input:radio[name="radio"]:checked').val();
            if (ppidVal == null || ppidVal == '') {
                alert("什么也没选中!");
                return false;
            } else {
                var barcode = $('input:radio[name="radio"]:checked').parent(".tr-1").siblings(".tr-3").text();
                var goodsname = $('input:radio[name="radio"]:checked').parent(".tr-1").siblings(".tr-4").text();
                $("#ppid").val(ppidVal);
                $(".td-1").text(barcode);
                $(".td-2").text(goodsname);
            }
            $(".alert-com").hide();
        });
        $("#productList").on("click", "tr", function () {
            $(this).find(".tr-1 input").prop("checked", true);
            /* //产品id
             var ppid = $(this).find(".tr-1 input").val();*/
        });
    })
    //获取没有设置批发价佣金的产品
    function productList() {
        $.ajax({
            type: "POST",
            url: basePath + "ea/wholesale/sajax_ea_getProductByStatus.jspa",
            data: {
                "search": $("#searchs").val(),//通过商品名称或条码搜索
                "pageForm.pageNumber": pageNumber
            },
            success: function (data) {
                var member = eval("(" + data + ")");
                if (member.code == "200") {
                    $("#searchs").val(member.search);
                    var pageForm = member.pageForm;
                    if (pageForm != null && pageForm.list != null) {
                        console.log(pageForm.list)
                        //alert(pageForm.pageNumber+"pageForm.pageNumber")
                        $("#sumPage").text(pageForm.pageCount)
                        $("#pageCount").val(pageForm.pageCount)
                        $("#pageNumber").val(pageForm.pageNumber)
                        var productList = new Array();
                        var size = pageForm.list.length;
                        var number = 1;
                        for (var i = 0; i < size; i++) {
                            var plist = pageForm.list[i];
                            productList.push("<tr>");
                            productList.push("<td class='tr-1'>");
                            productList.push("<input name='radio' type='radio' value='" + plist[0] + "'>");
                            productList.push("<label></label>");
                            productList.push("</td>");
                            productList.push("<td class='tr-2'>" + number + "</td>");
                            if (plist[1] != null) {
                                productList.push("<td class='tr-3'>" + plist[1] + "</td>");
                            } else {
                                productList.push("<td class='tr-3'></td>");
                            }
                            productList.push("<td class='tr-4'>" + plist[2] + "</td>");
                            productList.push("</tr>");
                            number++;
                        }
                        $("#productList").append(productList.join(""))
                    }
                } else {
                    //登录异常，回登录页面重新登录
                    location.href = basePath + "page/ea/not_login.jsp";
                }

            },
            error: function (data) {
                alert("获取数据失败！");
            },
            dateType: "json"
        });
    }
    //上一页
    function lastPage() {
        if ($("#pageNumber").val() > 1) {
            //清空div
            $("#productList").empty();
            //上一页
            pageNumber = parseInt($("#pageNumber").val()) - 1;
            //获取没有设置批发价佣金的产品
            productList();
        }
    }

    //下一页
    function nextPage() {
        if (parseInt($("#pageNumber").val()) < parseInt($("#pageCount").val())) {
            //清空div
            $("#productList").empty();
            //下一页
            pageNumber = parseInt($("#pageNumber").val()) + 1;
            //获取没有设置批发价佣金的产品
            productList();
        }
    }
</script>
</body>
</html>