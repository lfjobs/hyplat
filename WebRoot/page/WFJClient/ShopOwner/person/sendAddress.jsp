<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html>
<head>

    <title>
        个人名片卖家地址管理
    </title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="Access-Control-Allow-Origin" content="*">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes"/>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">

    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/WFJClient/wfjapp.css"/>
    <script type="text/javascript" src="<%=basePath %>js/jquery-1.6.1.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=basePath %>js/WFJClient/localforage.min.js"></script>
</head>
<body>

<div class="wfj11_016">
    <div class="wfj11_016_top" id="tops" style="background-color: #fff;border-bottom:1px solid #dde;">
        <ul>
            <li class="arrow"><img
                    src="<%=basePath%>images/WFJClient/PersonalJoining/1446039721_icon-ios7-arrow-right.png"/></li>
            <li>${type eq '0'?"退货地址":type eq 1?"发货地址":"收货地址"}</li>
        </ul>
    </div>
    <div class="wfj11_016_content">
        <div class="wfj11_016_hidden">
            <c:forEach items="${ entityList}" var="list">
                <div class="wfj11_016_consignee" id="${list.raddressId }">
                    <div class="wfj11_016_width">
                        <table>
                            <tr>
                                <td></td>
                                <td width="40%">
                                    <form id="form${list.raddressId }" name="form${list.raddressId }">
                                        <input type="hidden" id="cashid" name="cashid"/>
                                        <input type="hidden" id="jumber" name="jumber"/>
                                        <input type="hidden" name="raddressId" id="raddressId"
                                               value="${list.raddressId }">
                                        <input type="hidden" name="rname" id="rname" value="${list.rname }">
                                        <input type="hidden" name="rtel" id="rtel" value="${list.rtel }">
                                        <input type="hidden" name="address" id="address"
                                               value="${list.rarea }${list.rstreet}">
                                        <input type="hidden" name="zfzt" id="zfzt">
                                        <input type="hidden" name="zffs" id="zffs">
                                        <input type="hidden" name="money" id="money">
                                    </form>
                                        ${type eq 0 or type eq 2?"收货":"发货"}人:${list.rname }
                                </td>
                                <td width="40%">${list.rtel }</td>
                                <td width="20%">
                                    <c:if test="${list.status eq '00' }"><font color="red">默认</font></c:if>
                                </td>
                            </tr>
                            <tr>
                                <td><img style="width:60%;"
                                         src="<%=basePath %>images/WFJClient/PersonalJoining/wfj11_address_01.png"/>
                                </td>
                                <td colspan="2">
                                        ${type eq '0'?"退货地址":type eq 1?"发货地址":"收货地址"}:
                                        ${list.rarea }${list.rstreet }
                                </td>
                                <td id="bj" onclick="detial('${list.raddressId }')">
                                    <a>编辑</a>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
                </a></c:forEach>
        </div>
        <script>
            $("#bj").click(function (event) {
                event.stopPropagation();
            })
        </script>
    </div>

    <div class="wfj11_016_bottom_address">
        <div>添加${type eq 0?"退货地址":type eq 1?"发货地址":"收货地址"}</div>
    </div>
</div>
<script type="text/javascript">
    var basePath = '<%=basePath%>';
    var user = "${user}";
    var type = "${type}";
    var flag = "${param.flag}";
    var companyId = "${companyId}";
    var staffid = "${param.staffid}";
    var sort = "${param.sort}";

    //扩展jquery的格式化方法
    $.fn.parseForm = function () {
        var serializeObj = {};
        var array = this.serializeArray();
        var str = this.serialize();
        $(array).each(function () {
            if (serializeObj[this.name]) {
                if ($.isArray(serializeObj[this.name])) {
                    serializeObj[this.name].push(this.value);
                } else {
                    serializeObj[this.name] = [serializeObj[this.name], this.value];
                }
            } else {
                serializeObj[this.name] = this.value;
            }
        });
        return serializeObj;
    };

    $(document).ready(function (e) {
        $("body").css("height", $(window).height());
        $(".wfj11_016_content").attr("style", "width:" + $(window).width() + "px;height:" + $(window).height() * 0.9 + "px;overflow:hidden;");
        $(".wfj11_016_hidden").attr("style", "width:" + parseInt($(".wfj11_016_content").width() + 17) + "px;height:" + parseInt($(".wfj11_016_content").height() * 0.94 + 17) + "px;overflow:auto;");
        //修改字体大小
        $("#tops").find("li").attr("style", "float:left;");
        $("#tops").find("li").eq(0).attr("style", "width:15%;float: left;");
        $("#tops").find("li").eq(0).find("img").attr("style", "height:" + $(window).height() * 0.03 + "px;padding-left:" + $(window).height() * 0.03 + "px; vertical-align:middle;");
        $("#tops").find("li").eq(1).attr("style", "width:70%; text-align:center; font-size:" + $(window).height() * 0.025 + "px;float:left;vertical-align:middle;");

        $(".wfj11_016_top").css("height", $(window).height() * 0.07 + "px");
        $(".wfj11_016_top").css("lineHeight", $(window).height() * 0.07 + "px");

        $(".wfj11_016_bottom_address").attr("style", "height:" + $(window).height() * 0.05 + "px;line-height:" + $(window).height() * 0.05 + "px;");
        $(".wfj11_016_bottom_address").find("div").attr("style", "font-size:" + $(window).height() * 0.03 + "px;border-radius:" + $(window).height() * 0.01 + "px;cursor:pointer;");
        +
            $(".wfj11_016_consignee").find("td").attr("style", "font-size:" + $(window).height() * 0.02 + "px;line-height:" + $(window).height() * 0.03 + "px;");
        $(".wfj11_016_consignee").attr("style", "padding:" + $(window).height() * 0.02 + "px 0; margin-bottom:" + $(window).height() * 0.01 + "px;");

        $(".wfj11_016_bottom_address").find("div").click(function () {
            var parm = new Array();
            parm.push(basePath);
            parm.push("ea/refund/ea_toAdd.jspa?");
            parm.push("user=" + user);
            parm.push("&type=" + type);
            parm.push("&flag=" + flag);
            parm.push("&companyId=" + companyId);
            open(parm.join(""), "_self")
        });

        $(".change").click(function () {
            $(".change").find(".changeimg").attr("src", basePath + "images/WFJClient/PersonalJoining/choice_02.png");
            $(this).find(".changeimg").attr("src", basePath + "images/WFJClient/PersonalJoining/choice_01.png");
        });

        if (flag == 2) {
            $("#tops").show();
        } else {
            $("#tops").hide();
        }

        //返回
        $(".arrow").click(function () {
            if (type == "0" || type == "1") {
                document.location.href = basePath + "mobile/office/mobileoffice_fastApplication.jspa";
            }else if (type == "2") {
                document.location.href = basePath + "ea/seller/ea_getBuyerOrder.jspa?companyid=" + companyId + "&staffid=" + staffid + "&sort=" + sort;
            }
        });

        $(".wfj11_016_consignee").click(function () {
            var id = $(this).attr("id");
            if (type == 2) {
                localforage.getItem('formxz1').then(function (value) {
                    //当离线仓库中的值被载入时，此处代码运行
                    if (value != null && value != "") {

                        $("#form" + id).find("#cashid").val(value.cashid);
                        $("#form" + id).find("#jumber").val(value.jumber);
                        $("#form" + id).find("#zfzt").val(value.zfzt);
                        $("#form" + id).find("#zffs").val(value.zffs);
                        $("#form" + id).find("#money").val(value.money);
                        var params = $("#form" + id).parseForm();
                        console.log(params);
                        localforage.setItem('formxz1', params).then(function (value) {

                            // 当值被存储后，可执行其他操作
                            window.history.go(-1);
                            /*window.close();*/
                        }).catch(function (err) {    // 当出错时，此处代码运行
                            console.log(err);
                        });
                    }

                });

            }
        });
    });

    //详情
    function detial(adid) {
        var param = new Array();
        param.push("user=" + user);
        param.push("&type=" + type);
        param.push("&flag=" + flag);
        param.push("&refundAddress.raddressId=" + adid);
        document.location.href = basePath + "ea/refund/ea_toDetail.jspa?" + param.join("");
    }


</script>
</body>
</html>