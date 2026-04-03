<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no"/>
    <title>修改价格百分比</title>
    <link type="text/css" rel="stylesheet" href="<%=basePath%>css/ea/finance/percentage/style.css">
    <%--<script type="application/javascript" src="<%=basePath%>js/jquery-2.1.1.min.js"></script>--%>
    <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/admin_main111.css"/>
    <link href="<%=basePath%>css/css.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="<%=basePath%>js/ea/finance/percentage/Percentage.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
    <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
    <script type="text/javascript">
        var basePath = '<%=basePath%>';
        var percentageId = '${percentageId}';
    </script>
</head>
<body>

<section class="commission percentum">
    <!-- 展示价格百分比设置-->
    <article>
        <!--头部-->
        <header>
            系统价格百分比设置
        </header>
        <!--头部 end-->
        <!--内容-->
        <figure>
            <form name="" id="pPercentageUpdate">
                <div class="head">
                    <input type="button" value="关闭" onclick="qx()">
                    <input type="button" value="保存" id="save">
                    <input type="hidden" name="ppercentage.percentageId" id="percentageId">
                    <input type="hidden" name="bpercentage.brokerageId" id="brokerageId">
                </div>
                <div class="tab">
                    <div class="tab_div"><p>请选择分类:</p>
                        <div><input type="button" class="btncon projectbtn"/></div>
                    </div>
                    <br>
                    <div class="tab_">
                        <table width="1280">
                            <tbody>
                            <tr>
                                <td class="td-1">系统零售价</td>
                                <td class="td-2">=</td>
                                <td class="td-3"><input type="number" name="ppercentage.retail" id="retail"></td>
                                <td class="td-4">%</td>
                                <td class="td-5">×</td>
                                <td class="td-6">成本价</td>
                                <td class="td-7">+</td>
                                <td class="td-8">成本价</td>
                            </tr>
                            <tr>
                                <td class="td-1">系统活动价</td>
                                <td class="td-2">=</td>
                                <td class="td-3"><input type="number" name="ppercentage.activity" id="activity"></td>
                                <td class="td-4">%</td>
                                <td class="td-5">×</td>
                                <td class="td-6">成本价</td>
                                <td class="td-7">+</td>
                                <td class="td-8">成本价</td>
                            </tr>
                            <tr>
                                <td class="td-1">系统VIP价</td>
                                <td class="td-2">=</td>
                                <td class="td-3"><input type="number" name="ppercentage.vip" id="vip"></td>
                                <td class="td-4">%</td>
                                <td class="td-5">×</td>
                                <td class="td-6">成本价</td>
                                <td class="td-7">+</td>
                                <td class="td-8">成本价</td>
                            </tr>
                            <tr>
                                <td class="td-1">系统批发价</td>
                                <td class="td-2">=</td>
                                <td class="td-3"><input type="number" name="ppercentage.wholesale" id="wholesale"></td>
                                <td class="td-4">%</td>
                                <td class="td-5">×</td>
                                <td class="td-6">成本价</td>
                                <td class="td-7">+</td>
                                <td class="td-8">成本价</td>
                            </tr>
                            <tr>
                                <td class="td-1">系统特价</td>
                                <td class="td-2">=</td>
                                <td class="td-3"><input type="number" name="ppercentage.special" id="special"></td>
                                <td class="td-4">%</td>
                                <td class="td-5">×</td>
                                <td class="td-6">成本价</td>
                                <td class="td-7">+</td>
                                <td class="td-8">成本价</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <header>
                    业务佣金百分比设置
                </header>
                <div class="tab">
                    <table width="1286" class="tit">
                        <thead>
                        <tr>
                            <th style="width: 140px;">贴牌佣金</th>
                            <th style="width: 120px;">设备安装佣金</th>
                            <th style="width: 126px;">设备投资佣金</th>
                            <th style="width: 126px;">省级代理佣金</th>
                            <th style="width: 126px;">县级代理佣金</th>
                            <th style="width: 126px;">村级代理佣金</th>
                            <th style="width: 126px;">客户积分佣金</th>
                            <th style="width: 125px;text-indent: -20px;">业务佣金</th>
                            <th style="width: 140px;text-indent: -45px;">总百分比</th>
                        </tr>
                        </thead>
                    </table>
                    <div class="tab-box">
                        <table width="1286">
                            <tbody>
                            <tr>
                                <td class="tr-1"><input type="number" class="price" id="tp" name="bpercentage.tp"></td>
                                <td class="sign">%+</td>
                                <td class="tr-2"><input type="number" class="price" id="sbaz" name="bpercentage.sbaz">
                                </td>
                                <td class="sign">%+</td>
                                <td class="tr-3"><input type="number" class="price" id="sbtz" name="bpercentage.sbtz">
                                </td>
                                <td class="sign">%+</td>
                                <td class="tr-4"><input type="number" class="price" id="sjdl" name="bpercentage.sjdl">
                                </td>
                                <td class="sign">%+</td>
                                <td class="tr-5"><input type="number" class="price" id="xjdl" name="bpercentage.xjdl">
                                </td>
                                <td class="sign">%+</td>
                                <td class="tr-6"><input type="number" class="price" id="cjdl" name="bpercentage.cjdl">
                                </td>
                                <td class="sign">%+</td>
                                <td class="tr-7"><input type="number" class="price" id="khjf" name="bpercentage.khjf">
                                </td>
                                <td class="sign">%+</td>
                                <td class="tr-8"><input type="number" class="price" id="ywyj" name="bpercentage.ywyj">
                                </td>
                                <td class="sign">%=</td>
                                <td class="tr-9"><input type="number" readonly="readonly" id="sum"></td>
                                <td class="sign">%</td>
                            </tr>

                            </tbody>
                        </table>
                    </div>
                </div>
                <ul class="footer">
                    <li>
                        设计百分比责任人
                        <input type="text" readonly="readonly" id="principal">
                    </li>
                    <li>
                        设计日期<input type="text" readonly="readonly" id="times"/>
                    </li>
                </ul>
            </form>
        </figure>
        <!--内容 end-->
    </article>
    <!--展示价格百分比设置 end-->
</section>
<!--弹框-->
<div class="alert_">
    <div class="alert">
        <h3>温馨提示</h3>
        <p>佣金不能大于零售价，请重新设置哦~</p>
        <div class="btn">
            <input type="button" value="取消">
            <input type="button" value="确定">
        </div>
    </div>
</div>
<!--弹框 end-->


<%--<section class="commission">
    <!--业务佣金设置-->
    <article>
        <!--头部-->
      &lt;%&ndash;  <header>
            业务佣金百分比设置
        </header>&ndash;%&gt;
        <!--头部 end-->
        <!--内容-->
        <figure>
            <form id="bPercentageAdd">
               &lt;%&ndash; <div class="head">
                    <input type="button" value="关闭" onclick="qx()">
                    <input type="button" value="保存" id="save">
                </div>&ndash;%&gt;
                <div class="tab">
                    <table width="1286" class="tit">
                        <thead>
                        <tr>
                            <th style="width: 140px;">贴牌佣金</th>
                            <th style="width: 120px;">设备安装佣金</th>
                            <th style="width: 126px;">设备投资佣金</th>
                            <th style="width: 126px;">省级代理佣金</th>
                            <th style="width: 126px;">县级代理佣金</th>
                            <th style="width: 126px;">村级代理佣金</th>
                            <th style="width: 126px;">客户积分佣金</th>
                            <th style="width: 125px;text-indent: -20px;">业务佣金</th>
                            <th style="width: 140px;text-indent: -45px;">总百分比</th>
                        </tr>
                        </thead>
                    </table>
                    <div class="tab-box">
                        <table width="1286">
                            <tbody>
                            <tr>
                                <td class="tr-1"><input type="number" class="price" name="bpercentage.tp"></td>
                                <td class="sign">%+</td>
                                <td class="tr-2"><input type="number" class="price" name="bpercentage.sbaz"></td>
                                <td class="sign">%+</td>
                                <td class="tr-3"><input type="number" class="price" name="bpercentage.sbtz"></td>
                                <td class="sign">%+</td>
                                <td class="tr-4"><input type="number" class="price" name="bpercentage.sjdl"></td>
                                <td class="sign">%+</td>
                                <td class="tr-5"><input type="number" class="price" name="bpercentage.xjdl"></td>
                                <td class="sign">%+</td>
                                <td class="tr-6"><input type="number" class="price" name="bpercentage.cjdl"></td>
                                <td class="sign">%+</td>
                                <td class="tr-7"><input type="number" class="price" name="bpercentage.khjf"></td>
                                <td class="sign">%+</td>
                                <td class="tr-8"><input type="number" class="price" name="bpercentage.ywyj"></td>
                                <td class="sign">%=</td>
                                <td class="tr-9"><input type="number" readonly="readonly" id="sum"></td>
                                <td class="sign">%</td>
                            </tr>

                            </tbody>
                        </table>
                    </div>
                </div>
                <ul class="footer">
                    <li>
                        设计佣金百分比责任人<input type="text" readonly="readonly" value="${cac.staffName}"
                                         name="bpercentage.principal">
                        <input type="hidden" value="${cac.companyID}" name="bpercentage.companyId"/>
                    </li>
                    <li>
                        设计日期<input type="text" readonly="readonly" value="${myDate}"/>
                    </li>
                </ul>
            </form>
        </figure>
        <!--内容 end-->
    </article>
    <!--业务佣金设置 end-->
</section>--%>

<%--起始--%>
<%--******************************************物品选择****************************************--%>
<form name="goodsForm" id="goodsForm" method="post"
      enctype="multipart/form-data">
    <input type="submit" name="submit" style="display: none"/>
    <div class="jqmWindow jqmWindowcss1" style="top: 5%; left: 53%;"
         id="goodsjqModel">
        <div class="content1" style="width: 100%; height: 400px;">
            <div class="contentbannb">
                <div class="drag">
                    选择物品
                </div>
            </div>
            <table width="99%" height="33" id="searchgood" border="0"
                   align="center" cellpadding="0" cellspacing="0"
                   style="margin-top: 5px; background: #FFFFFF;">
                <tr>
                    <td width="100" align="right">
                        物品编码或名称：
                    </td>
                    <td width="142">
                        <input name="typeID" class="input" id="typeID" size="10"
                               style="margin-left: 2px;"/>
                    </td>
                    <td height="33">
                        <input type="button" class="btn02" ID="searchGood"
                               name="button7" value="查询"/>
                        <input type="button" class="btn02" id="selectGood"
                               name="button5" value="确定"/>
                        <input type="button" class="btn02 xzwp" name="button" value="新增"/>
                        <input type="button" class="btn02 JQueryreturns" name="button4"
                               value="关闭"/>
                        <input type="hidden" name="parms" id="parms"/>
                        <input type="hidden" id="clicktr"/>
                    </td>
                    <td width="80">
                        <a id="wpsy" title="0">上一页</a>
                    </td>
                    <td width="80">
                        <a id="wpxy" title="0">下一页</a>
                    </td>
                    <td width="100">
                        <a id="wpzy">共&nbsp;&nbsp; <span style="color: red"
                                                         id="wpzycount"></span>&nbsp;&nbsp;页 </a>
                    </td>
                </tr>
            </table>
            <table width="99%" border="0" align="center" cellpadding="0"
                   cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
                <tr>
                    <td width="16%">
                        <table width="100%" cellpadding="0" cellspacing="0">
                            <tr id="menuTreeTrid-1" sizcache="1" sizset="0">
                                <td>
                                    <div id="aadTree" class="text_tree"
                                         style="overflow: scroll; z-index: 99; height: 320px;"></div>
                                </td>
                            </tr>
                        </table>
                    </td>
                    <td width="83%" valign="top" align="left">
                        <div id="body_02"
                             style="margin-top: 2px; display: none; height: 310px; width: 100%; overflow: auto;">
                        </div>
                    </td>
                </tr>
            </table>
        </div>
    </div>
    <%--  <s:token></s:token>--%>
</form>

<form name="selectxmForm" id="selectxmForm" method="post"
      enctype="multipart/form-data">
    <input type="submit" name="submit" style="display: none"/>
    <div class="jqmWindow jqmWindowcss1" style="top: 5%; left: 53%;"
         id="xmjqModel">
        <div class="content1" style="width: 100%; height: 400px;">
            <div class="contentbannb">
                <div class="drag">
                    项目信息
                </div>
            </div>
            <table width="99%" height="33" id="searchxm" border="0"
                   align="center" cellpadding="0" cellspacing="0"
                   style="margin-top: 5px; background: #FFFFFF;">
                <tr>
                    <td width="100" align="right">
                        项目名称：
                    </td>
                    <td width="142">
                        <input name="parameter" class="input" id="parameterxm"
                               size="10" style="margin-left: 2px;"/>
                        <input type="hidden" id="selectxm"/>
                        <input type="hidden" id="selectxms"/>
                    </td>
                    <td height="33">
                        <input type="button" class="btn02" id="searchxmbtn" name="button7"
                               value="查询"/>
                        <input type="button" class="btn02" id="qdxm" name="button5"
                               value="确定"/>
                        <input type="button" class="btn02 xzxm" name="button5"
                               value="新增"/>
                        <input type="button" class="btn02 JQueryreturns" name="button4"
                               value="关闭"/>


                    </td>
                    <td width="80">
                        <a id="xmsy" title="0">上一页</a>
                    </td>
                    <td width="80">
                        <a id="xmxy" title="0">下一页</a>
                    </td>
                    <td width="100">
                        <a id="xmzy">共&nbsp;&nbsp; <span style="color: red"
                                                         id="xmzycount"></span>&nbsp;&nbsp;页 </a>
                    </td>
                </tr>
            </table>
            <table width="99%" border="0" align="center" cellpadding="0"
                   cellspacing="0" style="margin-top: 5px; margin-bottom: 5px;">
                <tr>
                    <td width="20%">
                        <table width="100%" cellpadding="0" cellspacing="0">

                            <tr id="menuTreeTrid-1" sizcache="1" sizset="0">
                                <td>

                                    <div class="text_tree" id="treeg"
                                         style="overflow: auto; z-index: 99; height: 280px;">
                                        <iframe src="<%=basePath%>page/ea/main/finance/invoicing/oprojecttreenew.jsp?codeID=scode20190415raqvqk3uvs0000000762&title=项目分类"
                                                width="600" height="300"></iframe>
                                        <%-- <iframe src="<%=basePath%>page/ea/main/finance/invoicing/oprojecttreenew.jsp?codeID=scode201410284shpd9x4fa0000000005&title=项目分类"
                                                 width="600" height="300"></iframe>--%>
                                    </div>
                                    <div class="mohuc text_tree"
                                         style="overflow: auto; z-index: 99; height: 280px;display:none;"></div>
                                    <div class="input_top" style="margin-top:5px;">模糊查询<input type="text" size="10"
                                                                                              id="inputmoc"/><input
                                            type="button" class="btncon" id="moc"></div>
                                </td>
                            </tr>
                        </table>
                    </td>
                    <td width="80%" valign="top" align="left">
                        <div
                                style="margin-top: 2px;  height: 310px; width: 100%; overflow: auto;">
                            <table width='98%' height='26' align='center' cellspacing='0'
                                   cellpadding='1' style='font-size:12px;' class='bannb_01'>
                                <tr>
                                    <td height='24' align='left' valign='top' class='txt01'>&nbsp;点击选择项目</td>
                                </tr>
                            </table>
                            <table width='99%' align='center' id='xmtable' cellpadding='0'
                                   cellspacing='0' class='table'>
                                <thead>
                                <th align='center' bgcolor='#E4F1FA' width='3%'>选择</th>
                                <th align='center' bgcolor='#E4F1FA' width='3%'>序号</th>
                                <th align='center' bgcolor='#E4F1FA' width='15%' align="left">项目编号</th>
                                <th align='center' bgcolor='#E4F1FA' width='20%'>项目名称</th>
                                <th align='center' bgcolor='#E4F1FA' width='8%'>项目分类</th>
                                <%--<th align='center' bgcolor='#E4F1FA' width='6%'>负责人</th>
                                <th align='center' bgcolor='#E4F1FA' width='6%'>创建人</th>
                        --%></thead>
                                <tbody id="body_02xm"></tbody>
                            </table>
                        </div>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</form>
<%--结束--%>
<script type="application/javascript">
    $(function () {
        //价格百分比修改回显
        $.ajax({
            type: "POST",
            url: basePath + "ea/percentage/sajax_ea_getPPercentagebyId.jspa",
            data: {
                "ppercentage.percentageId": percentageId
            },
            success: function (data) {
                var member = eval("(" + data + ")");
                var pPercentage = member.pPercentage;
                var bPercentage = member.bPercentage;
                //console.log(pPercentage)
                //获取价格百分比回显数据，回显数据
                $("#percentageId").val(pPercentage.percentageId);
                $("#retail").val(pPercentage.retail);
                $("#activity").val(pPercentage.activity);
                $("#vip").val(pPercentage.vip);
                $("#wholesale").val(pPercentage.wholesale);
                $("#special").val(pPercentage.special);
                $("#principal").val(pPercentage.principal);
                var JsonDateValue = new Date(pPercentage.times.time);
                var text = JsonDateValue.toLocaleString();
                $("#times").val(text);
                console.log(bPercentage);
                //获取佣金百分比回显数据，回显数据
                $("#brokerageId").val(bPercentage.brokerageId);
                $("#tp").val(bPercentage.tp);
                $("#sbaz").val(bPercentage.sbaz);
                $("#sbtz").val(bPercentage.sbtz);
                $("#sjdl").val(bPercentage.sjdl);
                $("#xjdl").val(bPercentage.xjdl);
                $("#cjdl").val(bPercentage.cjdl);
                $("#khjf").val(bPercentage.khjf);
                $("#ywyj").val(bPercentage.ywyj);
                // $("#principal").val(bPercentage.principal);
                $("#sum").val(100);

            },
            error: function (data) {
                alert("获取数据失败！");
            },
            dateType: "json"
        });
        $(".alert .btn input").click(function () {
            $(".alert_").hide();
        });
        /*判断不能为空或者大于100*/
        var _isInvalid = false;

        function empty1() {
            _isInvalid = false;
            $(".percentum .tab tr .td-3 input").each(function () {
                var val = parseFloat($(this).val());
                if (val > 100) {
                    $(".alert_").show();
                    $(".alert p").text("百分比不能超过100%");
                    _isInvalid = true;
                    return false;
                } else if ($(this).val() == '') {
                    $(".alert_").show();
                    $(".alert p").text("不能为空");
                    _isInvalid = true;
                    return false;
                } else {
                }
            });
        }

        /*佣金百分比*/
        var _isInvalids = false;
        /*判断输入框不能超过100*/
        $(".commission figure .tab-box .price").keyup(function () {
            sum()
        });
        /*为空*/
        function empty() {
            _isInvalids = false;
            var sum = 0;
            $(".commission figure .tab-box .price").each(function () {
                var val = parseInt($(this).val());
                sum += parseInt($(this).val());
                if ($(this).val() == '') {
                    $(".alert_").show();
                    $(".alert p").text("佣金百分比不能为空！");
                    _isInvalids = true;
                    return false;
                }
            });
        }

        /*求和*/
        function sum() {
            var sum = 0;
            $(".commission figure .tab-box .price").each(function () {
                var val = $(this).val() * 1;
                sum += $(this).val() / 1;
                if (val > 100) {
                    $(".alert_").show();
                    $(".alert p").text("佣金百分比不能超过100%");
                } else if ($(this).val() == '') {
                    return false;
                } else {
                    $(".commission figure .tab-box .tr-9 input").val(sum);
                }
            });

        }

        /*判断总百分比是否是100*/
        function price() {
            _isInvalid = false;
            var pre = $(".commission figure .tab-box .tr-9 input").val();
            if (pre > 100) {
                $(".alert_").show();
                $(".alert p").text("佣金总百分比不能超过100%！");
                _isInvalid = true;
            } else if (pre < 100) {
                $(".alert_").show();
                $(".alert p").text("佣金总百分比不能小于100%！");
                _isInvalid = true;
            } else {

            }
        }


        //设置一个对象来控制是否进入AJAX过程
        var post_flag = false;
        /*保存*/
        $("#save").click(function () {
            //表单校验
            empty1();
            if (_isInvalid) {
                return false;
            }

            /*佣金百分比始*/
            price();
            empty();
            if (_isInvalid) {
                return false;
            }
            if (_isInvalids) {
                return false;
            }
            /*佣金百分比终*/

            //如果正在提交则直接返回，停止执行
            if (post_flag) {
                return;
            }
            //标记当前状态为正在提交状态
            post_flag = true;
            //修改价格百分比
            $.ajax({
                type: "POST",
                url: basePath + "ea/percentage/sajax_ea_PPercentageUpdate.jspa",
                data: $('#pPercentageUpdate').serialize(),
                success: function (data) {
                    var member = eval("(" + data + ")");
                    var code = member.code;
                    post_flag = false; //在提交成功之后将标志标记为可提交状态
                    if (code == '200') {
                        alert("修改成功！");
                        window.opener.location.reload();
                        window.close();
                        var url = basePath + "ea/percentage/ea_selectPPercentage.jspa";
                        location.href = url;

                    } else if (code == '400') {
                        document.location.href = basePath
                            + "page/ea/not_login.jsp";
                    } else {
                        alert("修改失败！");

                    }

                },
                error: function (data) {
                    post_flag = false; //AJAX失败也需要将标志标记为可提交状态
                    alert("获取数据失败！");
                    //location.reload();
                },
                dateType: "json"
            });

        });


        /*input只能输入数字或者点*/
        $('input[type=number]').keypress(function (e) {
            if (!String.fromCharCode(e.keyCode).match(/[0-9\.]/)) {
                return false;
            }
        });

    })
    function qx() {
        window.opener.location.reload();
        window.close();
        var url = basePath + "ea/percentage/ea_selectPPercentage.jspa";
        location.href = url;
    }
</script>
</body>
</html>