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
    <title>业务佣金百分比设置</title>
    <link type="text/css" rel="stylesheet" href="<%=basePath%>css/ea/finance/percentage/style.css">
    <script type="application/javascript" src="<%=basePath%>js/jquery-2.1.1.min.js"></script>
    <script type="text/javascript">
        var basePath = '<%=basePath%>';
    </script>
</head>
<body>

<section class="commission">
    <!--业务佣金设置-->
    <article>
        <!--头部-->
        <header>
            业务佣金百分比设置
        </header>
        <!--头部 end-->
        <!--内容-->
        <figure>
            <form id="bPercentageAdd">
                <div class="head">
                    <input type="button" value="关闭" onclick="qx()">
                    <input type="button" value="保存" id="save">
                </div>
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
</section>
<!--弹框-->
<div class="alert_">
    <div class="alert">
        <h3>温馨提示</h3>
        <p>佣金不能大于零售价，请重新设置哦~</p>
        <div class="btn">
            <input type="button" value="取消" id="">
            <input type="button" value="确定">
        </div>
    </div>
</div>
<!--弹框 end-->
<script type="application/javascript">
    $(function () {
        $(".alert .btn input").click(function () {
            $(".alert_").hide();
        });
        var _isInvalid = false;
        var _isInvalids = false;
        /*判断输入框不能超过100*/
        $(".commission figure .tab-box .price").blur(function () {
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
                    $(".alert p").text("不能为空");
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
                    $(".alert p").text("百分比不能超过100%");
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
                $(".alert p").text("总百分比不能超过100%");
                _isInvalid = true;
            } else if (pre < 100) {
                $(".alert_").show();
                $(".alert p").text("总百分比不能小于100%");
                _isInvalid = true;
            } else {

            }
        }

        //设置一个对象来控制是否进入AJAX过程
        var post_flag = false;
        /*保存*/
        $("#save").click(function () {
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
            //添加佣金百分比
            $.ajax({
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
            });
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
</script>
</body>
</html>