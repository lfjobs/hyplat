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
    <title>系统价格百分比设置</title>
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
    </script>
</head>

<body>

<section class="commission percentum">
    <!-- 价格百分比设置-->
    <article>
        <!--头部-->
        <header>
            系统价格百分比设置
        </header>
        <!--头部 end-->
        <!--内容-->
        <figure>
            <form name="" id="pPercentageAdd">
                <div class="head">
                    <input type="button" value="关闭" onclick="qx()">
                    <input type="button" value="保存" id="save">
                </div>
                <div class="tab">
                    <div class="tab_div"><p>请选择分类:</p>
                        <div><input type="button" class="btncon projectbtn"/></div>
                    </div>
                    <br>
                    类别名称:<input type="text" id="codeName">
                    产品名称:<input type="text" id="pName">
                    <div class="tab_">
                        <table width="1280" id="priceTable">
                            <tbody>
                            <tr>
                                <td class="td-1">系统零售价</td>
                                <td class="td-2">=</td>
                                <td class="td-3"><input type="number" name="ppercentage.retail"></td>
                                <td class="td-4">%</td>
                                <td class="td-5">×</td>
                                <td class="td-6">成本价</td>
                                <td class="td-7">+</td>
                                <td class="td-8">成本价</td>
                            </tr>
                            <tr>
                                <td class="td-1">系统活动价</td>
                                <td class="td-2">=</td>
                                <td class="td-3"><input type="number" name="ppercentage.activity"></td>
                                <td class="td-4">%</td>
                                <td class="td-5">×</td>
                                <td class="td-6">成本价</td>
                                <td class="td-7">+</td>
                                <td class="td-8">成本价</td>
                            </tr>
                            <tr>
                                <td class="td-1">系统VIP价</td>
                                <td class="td-2">=</td>
                                <td class="td-3"><input type="number" name="ppercentage.vip"></td>
                                <td class="td-4">%</td>
                                <td class="td-5">×</td>
                                <td class="td-6">成本价</td>
                                <td class="td-7">+</td>
                                <td class="td-8">成本价</td>
                            </tr>
                            <tr>
                                <td class="td-1">系统批发价</td>
                                <td class="td-2">=</td>
                                <td class="td-3"><input type="number" name="ppercentage.wholesale"></td>
                                <td class="td-4">%</td>
                                <td class="td-5">×</td>
                                <td class="td-6">成本价</td>
                                <td class="td-7">+</td>
                                <td class="td-8">成本价</td>
                            </tr>
                            <tr>
                                <td class="td-1">系统特价</td>
                                <td class="td-2">=</td>
                                <td class="td-3"><input type="number" name="ppercentage.special"></td>
                                <td class="td-4">%</td>
                                <td class="td-5">×</td>
                                <td class="td-6">成本价</td>
                                <td class="td-7">+</td>
                                <td class="td-8">成本价</td>
                            </tr>
                            </tbody>
                        </table>
                        <input type="hidden" name="ppercentage.codeId" id="codeId">
                        <input type="hidden" name="ppercentage.ppId" id="ppId">
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
                        设计百分比责任人
                        <input type="text" readonly="readonly" value="${cac.staffName}" name="bpercentage.principal">
                        <input type="hidden" readonly="readonly" value="${cac.staffName}" name="ppercentage.principal">
                        <input type="hidden" value="${cac.companyID}" name="bpercentage.companyId"/>
                        <input type="hidden" value="${cac.companyID}" name="ppercentage.companyId"/>
                    </li>
                    <li>
                        设计日期<input type="text" readonly="readonly" value="${myDate}"/>
                    </li>
                </ul>
                <%-- <ul class="footer">
                     <li>
                         责任人<input type="text" readonly="readonly" value="${cac.staffName}"
                                   name="ppercentage.principal">
                         <input type="hidden" value="${cac.companyID}" name="ppercentage.companyId"/>
                     </li>
                     <li>
                         设计日期<input type="text" readonly="readonly" value="${myDate}"/>
                     </li>
                 </ul>--%>
            </form>
        </figure>
        <!--内容 end-->
    </article>
    <!--系统价格百分比设置 end-->
</section>
<!--弹框-->
<div class="alert_">
    <div class="alert">
        <h3>温馨提示</h3>
        <p></p>
        <div class="btn">
            <input type="button" value="取消" id="">
            <input type="button" value="确定">
        </div>
    </div>
</div>
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
                    产品信息
                </div>
            </div>
            <table width="99%" height="33" id="searchxm" border="0"
                   align="center" cellpadding="0" cellspacing="0"
                   style="margin-top: 5px; background: #FFFFFF;">
                <tr>
                    <td width="100" align="right">
                        产品名称：
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
                                        <iframe src="<%=basePath%>page/ea/main/finance/invoicing/oprojecttreenew.jsp?codeID=scode20190415raqvqk3uvs0000000762&title=产品分类"
                                                width="600" height="300" id="myiframe"></iframe>
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
                                    <td height='24' align='left' valign='top' class='txt01'>&nbsp;点击选择产品</td>
                                </tr>
                            </table>
                            <table width='99%' align='center' id='xmtable' cellpadding='0'
                                   cellspacing='0' class='table'>
                                <thead>
                                <th align='center' bgcolor='#E4F1FA' width='3%'>选择</th>
                                <th align='center' bgcolor='#E4F1FA' width='3%'>序号</th>
                                <th align='center' bgcolor='#E4F1FA' width='15%' align="left">产品编号</th>
                                <th align='center' bgcolor='#E4F1FA' width='20%'>产品名称</th>
                                <th align='center' bgcolor='#E4F1FA' width='8%'>产品分类</th>
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
<!--弹框 end-->
<script type="application/javascript">
    $(function () {
        $(".alert .btn input").click(function () {
            $(".alert_").hide();
        });
        /*判断价格百分比是否为0~100*/
        var _isInvalid = false;

        function empty1() {
            _isInvalid = false;
            $("#priceTable input").each(function () {
                var val = parseFloat($(this).val());
                if (val > 100) {
                    $(".alert_").show();
                    $(".alert p").text("价格百分比不能超过100%！");
                    _isInvalid = true;
                    return false;
                } else if ($(this).val() == '' || $(this).val() == undefined || $(this).val() == null) {
                    $(".alert_").show();
                    $(".alert p").text("价格百分比不能为空！");
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

        /*佣金百分比*/
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
            //添加价格、佣金百分比
            $.ajax({
                type: "POST",
                url: basePath + "ea/percentage/sajax_ea_PPercentageAdd.jspa",
                data: $('#pPercentageAdd').serialize(),
                success: function (data) {
                    var member = eval("(" + data + ")");
                    var code = member.code;
                    post_flag = false; //在提交成功之后将标志标记为可提交状态
                    if (code == '200') {
                        alert("添加成功！");
                        window.opener.location.reload();
                        window.close();
                        var url = basePath + "ea/percentage/ea_selectPPercentage.jspa";
                        location.href = url;
                    } else if (code == '400') {
                        alert("添加失败！");
                    } else {//code == '400'
                        alert("该分类或产品之前已选择，请选择其他分类或产品添加百分比！")
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

    /*$(function () {
     $(".alert .btn input").click(function () {
     $(".alert_").hide();
     });
     var _isInvalid = false;
     var _isInvalids = false;
     /!*判断输入框不能超过100*!/
     $(".commission figure .tab-box .price").keyup(function () {
     sum()
     });
     /!*为空*!/
     function empty() {
     alert(322)
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
     /!*求和*!/
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
     /!*判断总百分比是否是100*!/
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
     /!*保存*!/
     alert(4)
     $("#save").click(function () {
     alert(6)
     price();
     empty();
     if (_isInvalid) {
     return false;
     }
     if (_isInvalids) {
     return false;
     }
     //如果正在提交则直接返回，停止执行
     if (post_flag) {
     return;
     }
     //标记当前状态为正在提交状态
     post_flag = true;
     alert("添加佣金百分比")
     //添加佣金百分比
     /!*  $.ajax({
     type: "POST",
     url: basePath + "ea/percentage/sajax_ea_BPercentageAdd.jspa",
     data: $('#bPercentageAdd').serialize(),
     success: function (data) {
     var member = eval("(" + data + ")");
     var code = member.code;
     post_flag = false; //在提交成功之后将标志标记为可提交状态
     if (code == '200') {
     alert("添加成功！");
     window.opener.location.reload();
     window.close();
     var url = basePath + "ea/percentage/ea_selectBPercentage.jspa";
     location.href = url;
     } else {
     alert("添加失败！");

     }

     },
     error: function (data) {
     post_flag = false; //AJAX失败也需要将标志标记为可提交状态
     alert("获取数据失败！");
     //location.reload();
     },
     dateType: "json"
     });*!/
     })


     /!*input只能输入数字或者点*!/
     $('input[type=number]').keypress(function (e) {
     if (!String.fromCharCode(e.keyCode).match(/[0-9\.]/)) {
     return false;
     }
     });
     })
     function qx() {
     window.opener.location.reload();
     window.close();
     var url = basePath + "ea/percentage/ea_selectBPercentage.jspa";
     location.href = url;
     }*/
</script>

<%--<script type="application/javascript">
    alert(2)
    $(function () {
        $(".alert .btn input").click(function () {
            $(".alert_").hide();
        });
        var _isInvalid = false;
        var _isInvalids = false;
        /*判断输入框不能超过100*/
        $(".commission figure .tab-box .price").keyup(function () {
            sum()
        });
        /*为空*/
        function empty() {
            alert(322)
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
        alert(4)
        $("#save").click(function () {
            alert(6)
            price();
            empty();
            if (_isInvalid) {
                return false;
            }
            if (_isInvalids) {
                return false;
            }
            //如果正在提交则直接返回，停止执行
            if (post_flag) {
                return;
            }
            //标记当前状态为正在提交状态
            post_flag = true;
            alert("添加佣金百分比")
            //添加佣金百分比
            /*  $.ajax({
             type: "POST",
             url: basePath + "ea/percentage/sajax_ea_BPercentageAdd.jspa",
             data: $('#bPercentageAdd').serialize(),
             success: function (data) {
             var member = eval("(" + data + ")");
             var code = member.code;
             post_flag = false; //在提交成功之后将标志标记为可提交状态
             if (code == '200') {
             alert("添加成功！");
             window.opener.location.reload();
             window.close();
             var url = basePath + "ea/percentage/ea_selectBPercentage.jspa";
             location.href = url;
             } else {
             alert("添加失败！");

             }

             },
             error: function (data) {
             post_flag = false; //AJAX失败也需要将标志标记为可提交状态
             alert("获取数据失败！");
             //location.reload();
             },
             dateType: "json"
             });*/
        })


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
        var url = basePath + "ea/percentage/ea_selectBPercentage.jspa";
        location.href = url;
    }
</script>--%>
</body>
</html>