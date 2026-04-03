<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>数据分配</title>
    <meta charset="utf-8"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <script src="<%=basePath %>js/scMobile/payBudget/addBudget/jquery-1.9.1.min.js" type="text/javascript"
            charset="utf-8"></script>
    <script src="<%=basePath %>js/scMobile/payBudget/addBudget/swiper/swiper.min.js" type="text/javascript"
            charset="utf-8"></script>

    <%--    <script type="text/javascript" src="<%=basePath%>/js/WFJClient/setHtmlFont.js"></script>--%>
    <link rel="stylesheet" href="<%=basePath%>/css/WFJClient/resest.css">
    <link rel="stylesheet" href="<%=basePath%>/css/WFJClient/mem_center.css">
    <link rel="stylesheet" href="<%=basePath%>/css/WFJClient/swiper-3.3.1.min.css">
    <script type="text/javascript" src="<%=basePath%>/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/WFJClient/zepto.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/common/common.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/trilateralData.css">


    <script type="text/javascript">
        var basePath = "<%=basePath%>";
        var companyID = "${param.companyID}";
        var staffID = "${param.staffID}";
    </script>
</head>
<body>
<header>
    <div class="div-ul1">
        <ul class="clearfix">
            <li>
                <a onclick="window.history.go(-1);return false;" target="_self">
                    <img src="<%=basePath%>images/ea/office/contract/selectp/return.png">
                </a>
            </li>
            <li>
                数据分配
            </li>
            <li>
            </li>
        </ul>
    </div>
</header>
<div class="content1">
    <div id="notification" class="notification-container hidden"></div>
    <div class="wrap_page build_plam_wrap">
        <div class="pm_head clearfix" style="display: none">
            <img class="pm_headimg"
                 src="<%=basePath%><c:if test='${cuscom.state=="1"}'><s:if test="staff.headimage!=null">${staff.headimage}</s:if><s:else>images/WFJClient/VipCenter/headimage.png</s:else></c:if><c:if test='${cuscom.state=="2"}'><s:if test="contactCompany.logoPath!=null">${contactCompany.logoPath}</s:if><s:else>images/WFJClient/VipCenter/headimage.png</s:else></c:if>">
            <div class="pm_text">
                <div class="pm_text_top">
                    <c:if test='${cuscom.state=="1"}'>${staff.staffName } </c:if>
                    <c:if test='${cuscom.state=="2"}'>${contactCompany.cresponsible }</c:if>
                </div>
                <div class="pm_text_bottom">${custypename}</div>
                <a href="<%=basePath%>ea/vipcenter/ea_Platform.jspa?staffid=${cuscom.staffid}&sccid=${cuscom.sccId}&flag=${flag }&type=1"
                   class="change_btn">切换平台<i></i></a>
            </div>
        </div>
        <input type="hidden" id="account" value="${cuscom.account}"/>
        <input type="hidden" id="type" value="${cuscom.cusType}"/>
        <div class="build_plam_con flex">
            <dl class="build_conL">
            </dl>
            <div class="build_conR">
                <div class="build_conR_content">
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    var basePath = "<%=basePath%>";
    var type = $("#type").attr("value");//当前用户会员级别
    var account = $("#account").attr("value");
    <%--var sccid = "${sccid}";--%>
    var sccid = "${sccid}";
    var pagenumber = 0;
    var height = 0;
    var t;
    var pagecount;

    $(document).ready(function () {
        vipnum();//初始化左边会员导航
        $(".build_conL").find("dd").eq(0).addClass("build_cur");
        var custype = 0;
        type = Number(type);
        if (type == 0) {
            custype = 2;
        } else if (type == 6 || type == 7) {
            custype = 8;
        } else {
            custype = type + 1;
        }
        loaded(custype);//初始化左边第一种会员级别的右边详细列表

        //点击左边不同会员类别，加载右边详细会员列表
        $(document).on("click", ".build_conL dd", function () {
            $(this).addClass("build_cur").siblings().removeClass();
            $(".build_conR_content").empty();
            pagenumber = 0;
            var custype = $(this).attr("id");
            loaded(custype);
        });


        // 使用事件委托为 .build_wrap 元素绑定点击事件
        $(document).on('click', '.build_wrap', function (event) {
            event.preventDefault(); // 防止默认行为
            // 获取当前被点击的 .build_wrap 元素内的所有 <span> 元素
            var spans = $(this).find("span");
            // 遍历所有的 <span> 元素并打印它们的文本内容
            spans.each(function (index, span) {
                console.log("Span " + index + ": " + $(span).text());
            });
            // 如果你只想获取特定的 <span>，比如第一个或最后一个，你可以这样做：
            var firstSpanText = $(this).find("span:first").text();
            var lastSpanText = $(this).find("span:last").text();
            console.log("First Span Text: " + firstSpanText);
            console.log("Last Span Text: " + lastSpanText);
            // 调用后端逻辑
            callBackendWithItemId(firstSpanText, lastSpanText);
        });
        //
        // 隐藏id等于3的<dd>标签
        $("dd[id='6']").hide();
        // 或者如果您想要更具体的选择器，确保只影响特定部分的<dd>
        $(".build_conL dd[id='6']").hide();
        // 隐藏id等于3的<dd>标签
        $("dd[id='9']").hide();
        // 或者如果您想要更具体的选择器，确保只影响特定部分的<dd>
        $(".build_conL dd[id='9']").hide();
        // 隐藏id等于3的<dd>标签
        $("dd[id='10']").hide();
        // 或者如果您想要更具体的选择器，确保只影响特定部分的<dd>
        $(".build_conL dd[id='10']").hide();
        // 隐藏id等于3的<dd>标签
        $("dd[id='15']").hide();
        // 或者如果您想要更具体的选择器，确保只影响特定部分的<dd>
        $(".build_conL dd[id='15']").hide();
    });
    function showNotification(message, type) {
        var notification = document.getElementById("notification");

        // 清除之前的样式并应用新样式
        notification.className = "notification-container hidden";
        if (type === 'success') {
            notification.classList.add('success');
        } else if (type === 'error') {
            notification.classList.add('error');
        }

        // 更新消息内容
        notification.textContent = message;

        // 显示通知
        notification.classList.remove("hidden");
        notification.style.opacity = "1";
        notification.style.transform = "scale(1)";

        // 自动隐藏通知（例如3秒后）
        setTimeout(function(){
            notification.style.opacity = "0";
            notification.style.transform = "scale(0.9)";
            setTimeout(function(){
                notification.classList.add("hidden");
            }, 600); // 确保动画完成后再隐藏元素
        }, 3000); // 通知显示的时间长度
    }
    // 定义一个函数来处理后端调用
    function callBackendWithItemId(firstSpanText, lastSpanText) {
        // 这里可以进行AJAX请求或其他与后端交互的操作
        // 示例：发送AJAX请求到服务器
        $.ajax({
            url: basePath + "ea/trilateral/ea_assignment4.jspa?", // 替换为实际的后端URL
            type: "POST",
            data: {
                name: firstSpanText,
                phone: lastSpanText,
                id: "${trilateralData.id}",
                // 可能还有其他参数
            },
            success: function (response) {
                // 处理成功的响应
                //  console.log("分配成功");
                // alert("分配成功")
                showNotification('分配成功', 'success');
                // 更新UI或者其他操作
                //短信通知
                var url = "ea/trilateral/ea_sendPhone.jspa?phone="+lastSpanText;
                window.location.href = basePath + url;

            },
            error: function (xhr, status, error) {
                // 错误处理
                // console.error("分配失败");
                showNotification('分配失败，请稍后再试', 'error');
            }
        });
    }

    //加载左边导航会员以及数量
    function vipnum() {
        var url = basePath + "ea/vipcenter/sajax_ea_findVipNum.jspa";
        // var url = basePath+"ea/trilateral/ea_findVipNum.jspa";
        $.ajax({
            url: url,
            type: "get",
            async: false,
            dataType: "json",
            data: {
                cusType: type,
                sccid: sccid
            },
            success: function (data) {
                var member = eval("(" + data + ")");
                var listt = member.listt;
                if (listt == null) {
                    return;
                } else {
                    var htl = new Array();
                    for (var i = 0; i < listt.length; i++) {
                        list = listt[i];
                        htl.push("<dd id='" + list[2] + "' class='check'><span>" + list[0] + "</span><span>" + list[1] + "人</span></dd>");
                    }
                    $(".build_conL").append(htl.join(""));
                }
            },
            error: function (data) {
                alert("获取失败");
            }
        });
    }

    //加载每一种会员级别的详细列表
    function loaded(custype) {
        clearTimeout(t);
        pagenumber += 1;
        var url = basePath + "ea/vipcenter/sajax_ea_findVipAccout.jspa";
        // var url = basePath+"ea/trilateral/ea_findVipAccout.jspa";
        $.ajax({
            url: url,
            type: "get",
            async: false,
            dataType: "json",
            data: {
                "pageForm.pageNumber": pagenumber,
                cusType: custype,
                sccid: sccid
            },
            success: function (data) {
                var member = eval("(" + data + ")");
                var pageForm = member.pageForm;
                if (pageForm == null) {
                    return;
                } else {
                    var htl = new Array();
                    var list = pageForm.list;
                    console.log("数据打印", list);
                    pagenumber = pageForm.pageNumber;
                    pagecount = pageForm.pageCount;
                    for (var i = 0; i < list.length; i++) {
                        var li = list[i];
                        var type = li[7];
                        var invite = "";
                        if (type == "" || type == null) {
                            type = "尚未注册";
                            invite = "<a class='sendmsg' id='" + li[0] + "' href='javascript:;' style='float: right;padding-right: 0.5rem;cursor: pointer;'>短信邀请 </a>";
                        }
                        if (li[3] == "" || li[3] == null) {
                            li[3] = "images/WFJClient/VipCenter/headimage.png";
                        }
                        // 使用计数器生成唯一ID
                        var uniqueId = 'item-' + (++window.uniqueIdCounter);
                        var uniqueId1 = 'item1-' + (++window.uniqueIdCounter);
                        console.log("打印ID", window.uniqueIdCounter);
                        htl.push("<div id='" + uniqueId + "' class='build_wrap' data-index='0'><div class='build_box flex flex_align_center'>");
                        htl.push("<div class='build_boximg'><img src='" + basePath + li[3] + "' alt=''></div><div class='build_boxtxt flex_1'>");
                        htl.push("<span>" + li[2] + "</span><span>" + type + invite + "</span><span style='display: none' id='" + uniqueId1 + "'>" + li[0] + "</span></div></div></div>");
                    }
                    $(".build_conR_content").append(htl.join(""));
                    getHeight(".build_conR_content", ".build_conR", "loaded(" + custype + ")");
                }
            },
            error: function (data) {
                alert("获取失败");
            }
        });
    }

    $(document).on("click", ".sendmsg", function () {
        var num = $(this).attr("id");
        var u = navigator.userAgent;
        var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
        var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
        if (isAndroid == true) {
            if (confirm("确定发送短信邀请?")) {
                Android.callmsg(num);
            }
        } else if (isiOS == true) {
            if (confirm("确定发送短信邀请?")) {
                var url = "func=" + 'message';
                params = {'name': num};
                for (var i in params) {
                    url = url + "&" + i + "=" + params[i];
                }
                window.webkit.messageHandlers.Native.postMessage(url);
            }
        }
    });

</script>
<script>
    window.onload = window.onresize = function () {
        //含义：当窗口加载完成和窗口尺寸变化的时候都执行大括号里面的命令
        //获取窗口的尺寸
        var clientHeight = document.documentElement.clientHeight;
        var plam_H = clientHeight - $('.build_plam_con').offset().top;
        document.getElementsByClassName('build_plam_con')[0].style.height = plam_H + 'px';

    }
</script>
</body>
</html>
