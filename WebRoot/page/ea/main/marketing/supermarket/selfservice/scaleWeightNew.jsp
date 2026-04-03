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
    <meta charset="utf-8"/>
    <title>无码称重</title>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/supermarket/style-weight.css">
    <script src="<%=basePath%>js/jquery-1.11.3.js" type="text/javascript"></script>
    <script src="<%=basePath%>js/font-size.js" type="text/javascript" charset="utf-8"></script>
    <script language="javascript" type="text/javascript"
            src="<%=basePath%>js/ea/marketing/supermarket/scaleWeightNew.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath %>js/WFJClient/localforage.min.js"></script>

    <script type="text/javascript">

        var staffID = "";
        var basePath = "<%=basePath%>";
        var companyID = "${param.companyID}";
        var totalNum = "${param.totalNum}";
        var posNum = "${param.posNum}";
        var ccompanyID = "${param.ccompanyID}";
        var pageNumber = 0;
        var pageSize = 35;
        var pageCount = 0;
        var number = 0;
        var relateID = "${param.relateID}";
        var lxType = "${param.lxType}";//查询类型，0：其他入口进入1：批发商城进入
        var token = 0;
        var pcid = "";
    </script>

</head>
<body class="hy">
<header>
    <ul class="clearfix">
        <li>
            <%--<img src="<%=basePath%>images/supermarket/scale/register_return.png"/>--%>
        </li>
        <li>
            无码称重
        </li>
        <li>
            <p class="tojiesuan">
                去结算
            </p>
        </li>
    </ul>
</header>
<div class="content">
    <section class="header clearfix">

        <form target="frameFile" id="search_from">
            <input type="search" placeholder="plu/商品名称" name="" id="search" value=""/>
            <iframe name='frameFile' style="display: none;"></iframe>
        </form>
        <div class="tojiesuan">
            <img src="<%=basePath%>images/supermarket/scale/cart.png"/>
            <span class="tonum">${param.totalNum}</span>
        </div>
    </section>
    <section class="nav-1">
        <div>
            <ul class="clearfix fcate">
                <li class="active">
                    全部
                </li>
                <%--<li>--%>
                <%--蔬菜--%>
                <%--</li>--%>
                <%--<li>--%>
                <%--水果--%>
                <%--</li>--%>
                <%--<li>--%>
                <%--干货--%>
                <%--</li>--%>
                <%--<li>--%>
                <%--膨化食品--%>
                <%--</li>--%>
            </ul>
        </div>
    </section>
    <section class="nav-2" style="display: none;">
        <div>
            <ul class="clearfix scate">
                <%--<li class="active">--%>
                <%--芒果--%>
                <%--</li>--%>
                <%--<li>--%>
                <%--香蕉--%>
                <%--</li>--%>
                <%--<li>--%>
                <%--火龙果--%>
                <%--</li>--%>
                <%--<li>--%>
                <%--橘子--%>
                <%--</li>--%>
            </ul>
        </div>
    </section>
    <section class="sec-con">
        <ul class="clearfix ul_list_sp">
            <%--<li class="active">--%>
            <%--<p>--%>
            <%--<img src="<%=basePath%>images/supermarket/scale/img_img.png"/>--%>
            <%--</p>--%>
            <%--<p>--%>
            <%--芒果--%>
            <%--</p>--%>
            <%--</li>--%>
            <%--<li>--%>
            <%--<p>--%>
            <%--<img src="<%=basePath%>images/supermarket/scale/img_img.png"/>--%>
            <%--</p>--%>
            <%--<p>--%>
            <%--芒果最多两行最多两行最多两行--%>
            <%--</p>--%>
            <%--</li>--%>
            <%--<li>--%>
            <%--<p>--%>
            <%--<img src="<%=basePath%>images/supermarket/scale/img_img.png"/>--%>
            <%--</p>--%>
            <%--<p>--%>
            <%--芒果最多两行最多两行最多两行最多两行--%>
            <%--</p>--%>
            <%--</li>--%>
            <%--<li>--%>
            <%--<p>--%>
            <%--<img src="<%=basePath%>images/supermarket/scale/img_img.png"/>--%>
            <%--</p>--%>
            <%--<p>--%>
            <%--芒果最多两行--%>
            <%--</p>--%>
            <%--</li>--%>
            <%--<li>--%>
            <%--<p>--%>
            <%--<img src="<%=basePath%>images/supermarket/scale/img_img.png"/>--%>
            <%--</p>--%>
            <%--<p>--%>
            <%--芒果最多两行--%>
            <%--</p>--%>
            <%--</li>--%>
            <%--<li>--%>
            <%--<p>--%>
            <%--<img src="<%=basePath%>images/supermarket/scale/img_img.png"/>--%>
            <%--</p>--%>
            <%--<p>--%>
            <%--芒果最多两行--%>
            <%--</p>--%>
            <%--</li>--%>
            <%--<li>--%>
            <%--<p>--%>
            <%--<img src="<%=basePath%>images/supermarket/scale/img_img.png"/>--%>
            <%--</p>--%>
            <%--<p>--%>
            <%--芒果最多两行--%>
            <%--</p>--%>
            <%--</li>--%>
            <%--<li>--%>
            <%--<p>--%>
            <%--<img src="<%=basePath%>images/supermarket/scale/img_img.png"/>--%>
            <%--</p>--%>
            <%--<p>--%>
            <%--芒果最多两行--%>
            <%--</p>--%>
            <%--</li>--%>
            <%--<li>--%>
            <%--<p>--%>
            <%--<img src="<%=basePath%>images/supermarket/scale/img_img.png"/>--%>
            <%--</p>--%>
            <%--<p>--%>
            <%--芒果最多两行--%>
            <%--</p>--%>
            <%--</li>--%>
            <%--<li>--%>
            <%--<p>--%>
            <%--<img src="<%=basePath%>images/supermarket/scale/img_img.png"/>--%>
            <%--</p>--%>
            <%--<p>--%>
            <%--芒果最多两行--%>
            <%--</p>--%>
            <%--</li>--%>
            <%--<li>--%>
            <%--<p>--%>
            <%--<img src="<%=basePath%>images/supermarket/scale/img_img.png"/>--%>
            <%--</p>--%>
            <%--<p>--%>
            <%--芒果最多两行--%>
            <%--</p>--%>
            <%--</li>--%>
            <%--<li>--%>
            <%--<p>--%>
            <%--<img src="<%=basePath%>images/supermarket/scale/img_img.png"/>--%>
            <%--</p>--%>
            <%--<p>--%>
            <%--芒果最多两行--%>
            <%--</p>--%>
            <%--</li>--%>
            <%--<li>--%>
            <%--<p>--%>
            <%--<img src="<%=basePath%>images/supermarket/scale/img_img.png"/>--%>
            <%--</p>--%>
            <%--<p>--%>
            <%--芒果最多两行--%>
            <%--</p>--%>
            <%--</li>--%>
            <%--<li>--%>
            <%--<p>--%>
            <%--<img src="<%=basePath%>images/supermarket/scale/img_img.png"/>--%>
            <%--</p>--%>
            <%--<p>--%>
            <%--芒果最多两行--%>
            <%--</p>--%>
            <%--</li>--%>
            <%--<li>--%>
            <%--<p>--%>
            <%--<img src="<%=basePath%>images/supermarket/scale/img_img.png"/>--%>
            <%--</p>--%>
            <%--<p>--%>
            <%--芒果最多两行--%>
            <%--</p>--%>
            <%--</li>--%>
            <%--<li>--%>
            <%--<p>--%>
            <%--<img src="<%=basePath%>images/supermarket/scale/img_img.png"/>--%>
            <%--</p>--%>
            <%--<p>--%>
            <%--芒果最多两行--%>
            <%--</p>--%>
            <%--</li>--%>

        </ul>
    </section>
    <%--<p class="p-button">--%>
    <%--确定--%>
    <%--</p>--%>
</div>

<!--------------------------------------------------称重开始--------------------------------------------->
<div class="weigh chengzhong_js">
    <div class="box">
        <h2>
            无码称重
        </h2>
        <ul>
            <li>
                <label for="">商品名称：</label>
                <input type="text" class="goodsname" readonly/>
            </li>
            <li>
                <label for="">商品plu：</label>
                <input type="text" class="plu" readonly/>
            </li>
            <li>
                <label for="">商品单价：</label>
                <input type="text" name="price" class="pr" readonly/>元
                <input type="button" class="in-tj" value="调价">
                <span class="prcc" style="display: none;"></span>
                <span class="hhh" style="display: none;"></span>
                <span class="pd" style="display: none;"></span>
                <span class="ppprit" style="display: none;"></span>
                <span class="cccostm" style="display: none;"></span>
                <span class="aaactivityID" style="display: none;"></span>
            </li>
            <li class="weight">
                <label for="">商品重量：</label>
                <input type="number" class="wvalue" readonly/>KG
            </li>
            <li class="inputnum" style="display: none">
                <label for="">商品个数：</label>
                <input type="number" id="inputnum"/>PCS
            </li>
            <li>
                <label for="">合计：</label><span class="totalMoney">0.00</span>

            </li>
        </ul>
        <section class="sec-btn">
            <div class="clearfix">
                <input type="button" name="" class="peeled" value="去皮"/>
                <input type="button" name="" class="zero" value="清零"/>
            </div>
            <div class="clearfix">
                <input type="button" name="" id="" class="cancel" value="取消"/>
                <input type="button" name="" class="isok" value="加入购物车"/>
            </div>
        </section>
    </div>
</div>

<!--输入密码弹框-->
<%--<div class="div-tiaojia">
    <figure class="figure-tiaojia">
        <table class="jp tioajia-num">
            <tr>
                <td><input id="Button1" type="button" value="1" onclick="return btnNum_onclick(1)"/></td>
                <td><input id="Button2" type="button" value="2" onclick="return btnNum_onclick(2)"/></td>
                <td><input id="Button3" type="button" value="3" onclick="return btnNum_onclick(3)"/></td>
            </tr>
            <tr>
                <td><input id="Button4" type="button" value="4" onclick="return btnNum_onclick(4)"/></td>
                <td><input id="Button5" type="button" value="5" onclick="return btnNum_onclick(5)"/></td>
                <td><input id="Button6" type="button" value="6" onclick="return btnNum_onclick(6)"/></td>
            </tr>
            <tr>
                <td><input id="Button7" type="button" value="7" onclick="return btnNum_onclick(7)"/></td>
                <td><input id="Button8" type="button" value="8" onclick="return btnNum_onclick(8)"/></td>
                <td><input id="Button9" type="button" value="9" onclick="return btnNum_onclick(9)"/></td>
            </tr>
            <tr>
                <td><input id="ButtonDel" type="button" value="删除" onclick="return delText()"/></td>
                <td><input id="Button0" type="button" value="0" onclick="return btnNum_onclick(0)"/></td>
                <td><input id="Buttond" type="button" value="."/></td>
            </tr>
            <tr>
                <td><input id="ButtonClear" type="button" value="清空" onclick="return clearText()"/></td>
                <td colspan="2"><input id="btnEnter" type="button" value="确定" onclick="return confirm()"/></td>
            </tr>
        </table>
    </figure>
</div>--%>

<div class="mm-alert">
    <div class="mm-div-tiaojia">
        <h1>提示</h1>
        <h5 class="ct"></h5>
        <input type="button" value="确定">
    </div>
</div>
<!--调价弹框-->
<div class="container div-con-tiaojia" style="display: none;">
    <section>
        <ul><a href='#' class='close-2'></a>
            <li class='clearfix'>
                <div class='div-img'><img src=''/></div>
                <p><input type='text' class='input-bold' disabled/></p></li>
            <%--<li class='clearfix'><p>产品分类</p>
                <div id="type"><p></p></div>
            </li>
            <li class='clearfix'><p>产品规格</p>
                <div id="standard"><p></p></div>
            </li>
            <li class='clearfix'><p>产品品牌</p>
                <div id="brand"><p></p></div>
            </li>--%>
            <li class='clearfix'><p>单价</p>
                <p><span class="dj"></span><input type='hidden' id='dj'/></p>
            <li class='clearfix'><p>数量/重量</p>
                <p><span id='sl'></span></p></li>
            <li class='clearfix'><p>总金额</p>
                <p><span class="je"></span><input type='hidden' id='je'/></p></li>
            <li class='clearfix'><p>调整单价为现价</p>
                <p><span id="dj2"></span></li>
            <%--</li>
            <li class='clearfix'><p>成本价</p>--%>
                <input type='hidden' id='cb' class='upinput'/>
                <input type='hidden' id='priceid' name='priceid'/>
                <input type='hidden' id='jlid' name='jlid'/>
                <input type='hidden' id='ppid' name='ppid'/>
                <input type='hidden' id='sj'/><%--售价--%>
                <input type='hidden' id='yj'/><%--//业务佣金--%>
                <input type='hidden' id='dl'/><%--//代理佣金--%>
                <input type='hidden' id='pri'/><%--//消费红包--%>
                <input type='hidden' id='xtdj'/><input type='hidden' id='sgrid'/><%--//系统单价--%>
            </li>
        </ul>
        <%--<div class='footer'><input type='button' name='' id='save' value='确定'/></div>--%>

            <figure class="figure-tiaojia">
                <table class="jp tioajia-num">
                    <tr>
                        <td><input id="Button1" type="button" value="1" onclick="return btnNum_onclick(1)"/></td>
                        <td><input id="Button2" type="button" value="2" onclick="return btnNum_onclick(2)"/></td>
                        <td><input id="Button3" type="button" value="3" onclick="return btnNum_onclick(3)"/></td>
                    </tr>
                    <tr>
                        <td><input id="Button4" type="button" value="4" onclick="return btnNum_onclick(4)"/></td>
                        <td><input id="Button5" type="button" value="5" onclick="return btnNum_onclick(5)"/></td>
                        <td><input id="Button6" type="button" value="6" onclick="return btnNum_onclick(6)"/></td>
                    </tr>
                    <tr>
                        <td><input id="Button7" type="button" value="7" onclick="return btnNum_onclick(7)"/></td>
                        <td><input id="Button8" type="button" value="8" onclick="return btnNum_onclick(8)"/></td>
                        <td><input id="Button9" type="button" value="9" onclick="return btnNum_onclick(9)"/></td>
                    </tr>
                    <tr>
                        <td><input id="ButtonDel" type="button" value="删除" onclick="return delText()"/></td>
                        <td><input id="Button0" type="button" value="0" onclick="return btnNum_onclick(0)"/></td>
                        <td><input id="Buttond" type="button" value="." onclick="return dian()"/></td>
                    </tr>
                    <tr>
                        <td><input id="ButtonClear" type="button" value="清空" onclick="return clearText()"/></td>
                        <td colspan="2"><input id="btnEnter" type="button" value="确定"/></td>
                    </tr>
                </table>
            </figure>
    </section>
</div>

</body>
<script type="text/javascript">

    //    //关闭弹框展示
    //    $(".inspection-sheet .sec-btn div:last-of-type input,.weigh .sec-btn div:last-of-type input").click(function(){
    //        $(".inspection-sheet").hide();
    //        $(".weigh").hide();
    //        $("body").removeClass("body_yc");
    //    })

</script>
</html>
