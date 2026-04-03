<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <title>设备展示</title>
    <script type="text/javascript" src="<%=basePath %>st/js/font-size.js"></script>
    <link type="text/css" rel="stylesheet" href="<%=basePath %>st/css/new_style.css">
    <script type="text/javascript" src="<%=basePath %>st/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>st/js/new-page.js"></script>
    <script type="text/javascript" src="<%=basePath %>st/js/teamAndeqment.js"></script>
    <script type="text/javascript">
        var basePath = "<%=basePath %>";
        var ccompanyID = "${ccompanyID}";
        $(document).ready(function(){
            var url = basePath+"st/enroll/sajax_ea_getComPhoto.jspa?ccompanyID="+ccompanyID;
            $.ajax({
                url : encodeURI(url),
                type : "post",
                dataType:"json",
                success : function (data) {
                    var member = eval("(" + data + ")");
                    var photo = member.photo;
                    if(photo!=null) {
                        photo = photo.replace(/\\/g, "/")
                    }
                    $(".friend img").attr("src",basePath+(photo==null?"st/images/shequ2.png":photo))
                }
            })
        });
    </script>
</head>
<body>

<header>
    <ul>
        <li style="width: 10%;">
            <a href="javascript:history.go(-1)"><img src="<%=basePath %>st/images/left_jt.png"></a>
        </li>
        <li style="width: 80%;">设备展示</li>
        <li style="width: 10%;"></li>
        <div class="clearfix"></div>
    </ul>
</header>
<div class="content_hidden">
    <div class="content">
        <div class="con">
            <div class="friend">
                <img src="" alt="" id="logo">
                <div class="txt">
                    <h4>${companyName==null?"":companyName}</h4>
                </div>
            </div>
            <div class="search_coach2">
                <div class="search_frd">
                    <input type="text" class="search">
                    <div class="search_">
                        <img src="<%=basePath %>st/images/ico-search2.png" alt="">
                        <p>搜索车辆型号</p>
                    </div>
                </div>
            </div>
            <ul class="coach_list2" id="car_list"></ul>
        </div>
    </div>
</div>
<script type="text/javascript">
    var basePath='<%=basePath%>';
    var companyID='${companyID}';
</script>

<script>
    $(document).ready(function(){
        $("header").css("height",$(window).height()*0.08-1+"px");
        $("header").css("line-height",$(window).height()*0.08-1+"px");
        $("header ul li").css("height",$(window).height()*0.08-1+"px");
        $("header ul li").css("line-height",$(window).height()*0.08-1+"px");
        $(".content_hidden").attr("style",";overflow: hidden;"+"height:"+$(window).height()*0.92+"px");
        $(".content").attr("style","overflow: hidden;"+"height:"+$(window).height()*0.92+"px");
        $(".con").css("height",$(window).height()*0.92+"px");


        /*搜索*/
        $(".search_frd input").focus(function(){
            $(".search_frd .search_").hide();
        });
        $(".search_frd input").blur(function(){
            if( $(".search_frd input").val()==""){
                $(".search_frd .search_").show();
            }else{
                $(".search_frd .search_").hide();
            }
        });
        $(".search_frd .search_").click(function(){
            $(".search_frd .search_").hide();
            $(".search_frd input").focus();
        });
        $(".search").on("input",function(){
            $("#car_list").empty();
            var carNum = $(this).val();
            eqment(carNum);
            var carNum;
        });

    });
</script>

</body>
</html>