<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@page language="java" import="hy.ea.bo.CAccount" %>
<%@ page language="java" import="com.tiantai.wfj.bo.TEshopCusCom"%>
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
    <title>添加平台栏目</title>
    <script type="text/javascript" src="<%=basePath%>js/font-size.js"></script>
    <link type="text/css" rel="stylesheet" href="<%=basePath%>css/WFJClient/myapp/new_style.css">
    <script type="text/javascript" src="<%=basePath%>js/WFJClient/myapp/jquery-1.9.1.min.js"></script>
    <script type="text/javascript">
        var basePath = "<%=basePath%>";
        var staffID='<%=((TEshopCusCom)session.getAttribute("key_shop_cus_com"))!=null ?((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getStaffid():"" %>';


        var internationalppid= "p201612089W7PQNDBEM0000000009"; //国际
        var stateppid = "p201612089W7PQNDBEM0000000008";    //国家
        var industryppid = "";  //行业
    </script>
</head>
<body>
<header>
    <ul>
        <li><a href="<%=basePath%>/ea/wfjshop/ea_getWFJshops.jspa"><img src="<%=basePath%>images/WFJClient/DigitalMall/left_jt.png" alt=""></a></li>
        <li>数字地球</li>
        <li></li>
    </ul>
</header>
<section>
    <div class="add_platform-list">
        <div class="tit">
            <p><span>已添加平台栏目</span></p>
        </div>
        <ul class="platform_a">
        </ul>
    </div>
    <div class="add_platform-list">
        <div class="tit">
            <p><span>已建设平台栏目</span></p>
        </div>
        <ul class="platform_b">
        </ul>
    </div>
    <div class="add_platform-list add_platform-list-2" style="border-bottom:none;">
        <div class="tit">
            <p><span>认领平台建设</span></p>
        </div>
        <ul class="head">
            <li class="int active">国际联盟</li>
            <li class="china">中国经济</li>
            <li class="ind">市场入驻</li>
        </ul>
        <div class="claim_con">
            <ul class="list int_"></ul>
            <ul class="list china_"></ul>
            <ul class="list ind_">
                <%--<li class="sol">--%>
                    <%--<h2>体育</h2>--%>
                    <%--<div class="sol_con">--%>
                        <%--<div class="sol_list">--%>
                            <%--<p>篮球</p>--%>
                            <%--<p>橄榄球</p>--%>
                            <%--<p>足球</p>--%>
                            <%--<p>网球</p>--%>
                        <%--</div>--%>
                        <%--<div class="sol_list">--%>
                            <%--<p>排球</p>--%>
                            <%--<p>体操</p>--%>
                            <%--<p>武术</p>--%>
                            <%--<p>打羽毛球</p>--%>
                        <%--</div>--%>
                        <%--<div class="sol_list">--%>
                            <%--<p>跳远</p>--%>
                            <%--<p>打乒乓球</p>--%>
                            <%--<p>棒球</p>--%>
                            <%--<p>铅球</p>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</li>--%>
            </ul>
        </div>
    </div>
</section>
<script>
    $(document).ready(function () {
        $("section").css("height",$(window).height()-$("header").height()+"px");

        $(".add_platform-list-2 .int").click(function () {
            $(".claim_con .int_").show().siblings().hide();
            $(this).addClass("active").siblings().removeClass("active");
            $(".int_").empty();
            platForm(".int_",internationalppid);
        });
        $(".add_platform-list-2 .china").click(function () {
            $(".claim_con .china_").show().siblings().hide();
            $(this).addClass("active").siblings().removeClass("active");
            $(".china_").empty();
            platForm(".china_",stateppid);
        });
        $(".add_platform-list-2 .ind").click(function () {
            $(".claim_con .ind_").show().siblings().hide();
            $(this).addClass("active").siblings().removeClass("active");
            $(".ind_").empty();
            getIndustry();
        });

    })
</script>

<script type="text/javascript">
    $(function () {
        var url =basePath+"/ea/WfjIndustryPlatform/sajax_ea_findPlatForm.jspa?staffID="+staffID;
        $.ajax({
            url:encodeURI(url),
            type:"get",
            async:false,
            dataType:"json",
            success:function (data) {
                var member = eval("("+data+")");
                var platForm = member.platform;
                var str= "";
                for(var i=0;i<platForm.length;i++){
                        str+="<li>"+platForm[i][1]+"("+platForm[i][4]+"经济平台)";
                        str+="<input type='hidden' value='"+platForm[i][2]+"'class='ccompanyID' ></li>"
                }
                $(".platform_b").append(str);
            }
        })

        var purl = basePath+"/ea/WfjIndustryPlatform/sajax_ea_platformByStaff.jspa?staffID="+staffID;
        $.ajax({
            url:encodeURI(purl),
            type:"get",
            async:false,
            dataType:"json",
            success:function (data) {
                var member = eval("("+data+")");
                var platForm = member.platform;
                var str ="";
                for(var i=0;i<platForm.length;i++){
                    str+="<li><img src='<%=basePath%>images/home/ico-delete.png'>"+platForm[i][1]+"("+platForm[i][4]+"经济平台)";
                    str+="<input type='hidden' class='ccompanyid' value='"+platForm[i][2]+"'/>";
                    //str+="<input type='hidden' class='platformid' value='"+platForm[i][5]+"'/>";
                    str+="</li>";
                }
                $(".platform_a").append(str);
            }
        })

        $(".add_platform-list").on('click','.platform_a li',function(){
            var $this = $(this);
            var platformID = $(this).find(".platformid").val();
            var ccompanyID = $(this).find(".ccompanyid").val();
            var url = basePath+"/ea/WfjIndustryPlatform/sajax_ea_addOrDelPlatForm.jspa?ccompanyID="+ccompanyID+"&staffID="+staffID+"&flag=del"
            $.ajax({
                url:encodeURI(url),
                type:"get",
                dataType:"json",
                async:false,
                success:function (data) {
                    $this.remove();
                    $(".platform_b").prepend("<li><input type='hidden' class='ccompanyID' value='"+ccompanyID+"' />"+$this.text()+"</li>");
                    setpbHeight();
                }
            })
        });

        $(".platform_b").on('click','li',function(){
            if($(".platform_a li").length>7){
                alert("添加已满！");
                return;
            }
            var $this = $(this);
            var ccompanyID = $(this).find(".ccompanyID").val()
            var url = basePath+"/ea/WfjIndustryPlatform/sajax_ea_addOrDelPlatForm.jspa?staffID="+staffID+"&ccompanyID="+ccompanyID+"&flag=add";
            $.ajax({
                url:encodeURI(url),
                type:"get",
                dataType:"json",
                async:false,
                success:function (data) {
                        $this.remove();
                        $(".platform_a").append("<li><input type='hidden' class='ccompanyid' value='"+ccompanyID+"'/><img src='<%=basePath%>images/home/ico-delete.png'>"+$this.text()+"</li>");
                        setpbHeight();
                }
            })
        })


        setpbHeight();
        platForm(internationalppid);
    })

    function setpbHeight() {
        var max_height=0;
        $(".platform_b li").each(function(){
            if($(this).index()==5){
                return false;
            }
            if($(this).next().offset().top!= $(this).offset().top){
                max_height+= $(this).outerHeight(true);
            }
        })
        $(".platform_b").height(max_height);
    }

    function platForm(obj,ppid) {
        var iurl = basePath + "ea/WfjIndustryPlatform/sajax_ea_getAjax.jspa?ppid=" + ppid
        $.ajax({
            url:encodeURI(iurl),
            type:"get",
            async:false,
            dataType:"json",
            success:function (data) {
                var member = eval("("+data+")");
                var pageForm = member.pageForm;
                if(pageForm!=null){
                    var list = pageForm.list;
                }else {
                    return;
                }
                var str="";
                if(list.length>0){
                    for (var i=0;i<list.length;i++){
                        var pp = list[i];
                        str +="<li onclick='dj(\""+pp[1]+"\",\""+pp[2]+"\")'><div class='header'>";
                        if(obj==".int_"){
                            str +="<img src='<%=basePath%>images/home/int-1.png'></div>";
                        }else if(obj==".china_"){
                            str +="<i style='background-color: #f583de;box-shadow: 0 0 5px #f583de;'></i></div>"
                        }
                        str +="<div class='text'>";
                        str +="<p> "+ pp[2] + "经济平台</p>";
                        str +=" <img src='<%=basePath%>images/home/ico-right2.png'></div></li>";
                    }
                }
                $(obj).append(str);
            }
        })
    }

     var gurl =basePath+"ea/WfjIndustryPlatform/sajax_ea_getIndustry.jspa?";
    function getIndustry() {
        $.ajax({
            url:encodeURI(gurl),
            type:"get",
            async:false,
            dataType:"json",
            success:function (data) {
                var member = eval("("+data+")");
                var industryList = member.industryList;
                var str="";
                if (industryList != null) {
                    for (var i = 0; i < industryList.length; i++) {
                        var pp = industryList[i];
//                        str+= "<a href='"+basePath+"/ea/WfjIndustryPlatform/ea_getAddPlatform.jspa?goodsname="+pp.codeValue+"'><li class='sol' >" + pp.codeValue + "</li></a>";
                        str +="<a href='"+basePath+"/ea/WfjIndustryPlatform/ea_getAddPlatform.jspa?goodsname="+pp.codeValue+"'><li><div class='header'>";
                        str +="<img src='<%=basePath%>images/home/int-1.png'></div>"
                        str +="<div class='text'>";
                        str +="<p> "+ pp.codeValue + "经济平台</p>";
                        str +=" <img src='<%=basePath%>images/home/ico-right2.png'></div></li></a>";
                    }
                }
                $(".ind_").append(str);
            }
        })
    }

    function dj(this_obj,name){
        var url="";
        var pname;
        pname = name.substring(2,4);
        if(pname=="国际"){
            url = basePath+"/ea/WfjIndustryPlatform/ea_getAddPlatform.jspa?ppid="+this_obj+"&goodsname="+name+"经济平台";
        }else {
            url = basePath+"/ea/WfjIndustryPlatform/ea_getQuery.jspa?type=03&ppid="+this_obj;
        }
        document.location.href = url;
    }

</script>
</body>
</html>
