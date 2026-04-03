<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<html>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <meta name="format-detection" content="telephone=no, email=no" />
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
    <script type="text/javascript" src="<%=basePath%>js/WFJClient/setHtmlFont.js"></script>
    <link rel="stylesheet" href="<%=basePath%>css/BuildPlatform/base.css">
    <link rel="stylesheet" href="<%=basePath%>css/ea/office_ea/makeApp/AppParkong.css">
    <script src="<%=basePath%>js/jquery.min.js"></script>
    <script src="<%=basePath%>js/ea/office_ea/makeApp/BindingStateHave.js"></script>
    <title>停车记录</title>
    <script type="text/javascript">
        var basePath="<%=basePath%>";
        var pageNumber = 0;
        var pageSize = 5;
        var pageCount;
        var eastLongitude = "0";//东经
        var northLatitude = "0";//北纬
        var city;//所在城市
        var token = 13;
    </script>
</head>

<body style="background:#f6f6f6;">
<header class="com_head">
    <a href="javascript:void(0)" class="back" onclick="javascript: window.history.go(-1);return false;"></a>
    <h1>停车收费</h1>
    <a href="###" class="head_R park_area"></a>
</header>
<div class="wrap_page park_record_wrap">
    <div class="fixed_wrap">
        <div class="park_tab_wrap clearfix">
            <div class="park_tab_box"><a href="<%=basePath%>/ea/mappointment/ea_parkingIsIntroduced.jspa?" class="park_tab">停车场</a></div>
            <div class="park_tab_box"><a href="<%=basePath%>/ea/mappointment/ea_countdown.jspa?" class="park_tab">停车记录</a></div>
            <div class="park_tab_box"><a href="<%=basePath%>/ea/mappointment/ea_viewVehicle.jspa?" class="park_tab  park_tab_cur">绑定车牌</a></div>
            <div class="park_tab_box"><a href="<%=basePath%>/ea/jinbi/ea_getwfjchongzhi.jspa?user=${tcc.account}&sccid=${tcc.sccId}&khd=0&flag=&identifying=&ccompanyId=&staffid=${tcc.staffid}" class="park_tab">充值金币</a></div>
        </div>
    </div>

    <div class="bind_wrap">
            <c:forEach items="${list}" var="item">
               <div class="bind_car park_list_box">
                车牌号：${item.carNum}
                  <a href="javascript:(0)" class="unbind_btn" onclick="unbind('${item.carID}')">解除绑定</a>
               </div>
            </c:forEach>

            <div class="bind_rec_wrap park_list_box">
            <div class="div-box clearfix">
                <div class="bind_rec_tit">
                    绑定记录
                </div>
                <div class="div-xz" onclick="newBind()">
                   新增车辆
                </div>
            </div>
            <%--js拼接--%>
        </div>
    </div>
</div>
<div class="overlay" id="overlay">
    <div class="popup_wrap">
        <div class="popup_text">
            是否解除绑定
        </div>
        <div class="popup_btn_wrap clearfix">
            <a href="javascript:void(0);" class="popup_no">否</a>
            <a class="popup_yes">是</a>
        </div>
    </div>
</div>
<form name="ubindForm" id="ubindForm" method="post">
 <input type="submit" style="display: none" name="submit"/>
 <input type="hidden" id="carID" name="carInformation.carID"/>
</form>
<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>

<script>
    $(function(){

        //查询选择
        $(document).on("click", ".unbind_btn", function () {
            $("#overlay").attr("class","overlay active");
        });

    });
    var overlay = document.getElementById("overlay");
    document.querySelector(".popup_no").addEventListener("click",function(){
        overlay.className="overlay";
    })

</script>
</body>
</html>
