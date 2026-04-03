<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" import="com.tiantai.wfj.bo.TEshopCusCom"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<!DOCTYPE html>
<html>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <title>驾校预约</title>
	<script type="text/javascript" src="<%=basePath%>js/BuildPlatform/font-size.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/jquery-1.9.1.min.js"></script>
    <link type="text/css" rel="stylesheet" href="<%=basePath%>css/ea/office_ea/makeApp/Make_an_appointment.css">
    <script type="text/javascript" src="<%=basePath%>js/ea/office_ea/makeApp/Make_an_appointment.js"></script>



    <style type="text/css">
        #prompt div{
            width: 75%;
            background: rgba(0,0,0, 0.5);
        }

    </style>
    <script>
        var basePath="<%=basePath%>";
        var  posNum = "";
    </script>
</head>
<body>

<header>
   <ul>
       <li style="width: 10%;">
           <a href="javascript:void(0)" onclick="javascript: window.history.go(-1);return false;"><img src="<%=basePath%>images/BuildPlatform/left_jt.png"></a>
       </li>

       <li style="width: 80%;">预约考场练车</li>
       <li style="width: 10%;"></li>
   </ul>
</header>
<div class="content_hidden div-input">
    <div class="content">
        <div class="con confi">
            <%--<h4 class="title">考场计时收费</h4>--%>
            <div class="name">
                <h5>${object[2]}</h5>
            </div>
            <div class="details" data-ppID="">
                <%--<img src="<%=basePath%>images/BuildPlatform/car.png">
                <div class="text">
                    <h5>${object[4]}</h5>
                    <p class="remark">¥<span></span>/小时</p>

                </div>
                <div class="btm">
                    <p class="number">x1</p>
                </div>--%>
            </div>
            <ul class="row">
                <a href="<%=basePath%>ea/mappointment/ea_jump.jspa?companyId=${param.companyId}&type=">
                <li class="examination">
                    <p class="left">找场地</p><img src="<%=basePath%>images/BuildPlatform/right.png">
                    <span></span>
                </li>
                </a>
                <a  onclick="coach()">
                <li class="coach">
                    <p class="left">找教练员</p><img src="<%=basePath%>images/BuildPlatform/right.png">
                    <span></span>
                </li>
                </a>
                <a  onclick="director()">
                <li class="director">
                    <p class="left">找主管</p><img src="<%=basePath%>images/BuildPlatform/right.png">
                    <span></span>
                </li>
            </a>
                  <li class="li-name">
                    <p class="left">学员手机号</p>
                    <input type="number" placeholder="请输入" id="tel">
                   </li>
                    <li class="li-name">
                        <p class="left">学员姓名</p>
                        <input type="text" placeholder="请输入" id="name">
                    </li>

            </ul>
        </div>
        <div class="btn">
            <button>确认预约</button>
        </div>
    </div>
</div>
<div class="alert_"></div>
<div class="no_order_alert">
    <img src="<%=basePath%>images/ico-delete.png" class="delete">
    <h5>金币不足无法预约</h5>
    <p>注意：</p>
    <p>
        1.金币不足1小时费用，无法预约。<br>
        2.计时费用超出，可计时结束后补交。<br>
        3.计时完成剩余金币，可兑换。
    </p>
    <input type="button" value="马上充值" class="pay">
</div>

<%--提示框--%>
<div class="div-ts">
    预约中
</div>
<!-- 提示窗口 -->
<div id="prompt" style="width: 100%;display: none;">
    <center>
        <div>
            <span style="position: relative;top: 19.8%;"></span>
        </div>
    </center>
</div>
<script language="JavaScript">
    $(function(){
        $(".jia").click(function(){
            var t=$(this).parents(".choose_bid").find('.nub');

            var dan=$(".confi .details .text .money").find('span');
            var zong=$(".btn p .mey");
            t.text(parseInt(t.text())+1);
            zong.text((dan.text()* t.text()).toFixed(2));

        });
        $(".jian").click(function(){
            var t=$(this).parents(".choose_bid").find('.nub');
            var dan=$(".confi .details .text .money").find('span');
            var zong=$(".btn p .mey");

            t.text(parseInt(t.text())-1);
            zong.text((dan.text()* t.text()).toFixed(2));
            if(parseInt(t.text())<0){
                t.text(0);
                zong.text((dan.text()*0).toFixed(2));
            }

        });


    })
</script>
<script>
    $(document).ready(function(){
        $("#prompt").css("position","absolute").css("top",$(window).height()*0.2+"px");
        $("#prompt").find("div").css("height",$(window).height()*0.06+"px").css("font-size",$(window).height()*0.0285+"px").css("color","#FFFFFF");
        $("#prompt").find("div").css("-moz-border-radius",$(window).height()*0.015+"px").css("-webkit-border-radius",$(window).height()*0.015+"px");
        $("header").css("height",$(window).height()*0.08-1+"px");
        $("header").css("line-height",$(window).height()*0.08-1+"px");
        $("header ul li").css("height",$(window).height()*0.08-1+"px");
        $("header ul li").css("line-height",$(window).height()*0.08-1+"px");
        $(".content_hidden").attr("style",";overflow: hidden;"+"height:"+$(window).height()*0.92+"px");
        $(".content").attr("style","overflow: hidden;"+"height:"+$(window).height()*0.92+"px");
        $(".head_top").css("height",$(window).height()*0.08-1+"px");
        $(".head_top ul li").css("line-height",$(window).height()*0.05+"px");
        $(".head_top ul li:nth-child(1) dl").css("margin",$(window).height()*0.015+"px");
        $(".head_top ul li:nth-child(2) input").attr("style","margin:"+$(window).height()*0.015+"px;margin-left:0;line-height:"+$(window).height()*0.05+"px;");
        /*$(".con").css("height",$(window).height()*0.828+"px");*/
        $(".con").css("height",$(window).height()*0.92-$(".btn").height()+"px");
        var erid = getCookie("erid");
        var staffid1 = getCookie("staffid1");
        var staffid2 = getCookie("staffid2");
        var ppid=getCookie("ppid");
        var ername="场地预约"+(getCookie("erName"));
        var erName=(getCookie("erName"));
        var  price=getCookie("price");
        var companyId=getCookie("companyid");
        var staffname1=getCookie("staffname1");
        var staffname2=getCookie("staffname2");
        var id = "${param.type}";
        if(id=="00"){
            delCookie();
        }
        if(erid!=""){
            $(".examination").find("span").attr("data-erid",erid);
            $(".examination").find("span").text(erName+"¥"+price+"/小时");
            $(".remark").find("span").text(price);
        }
        if(staffid1!=""){
            $(".coach").find("span").attr("data-staffid",staffid1);
            $(".coach").find("span").text(staffname1);
        }
        if(staffid2!=""){
            $(".director").find("span").attr("data-staffid",staffid2);
            $(".director").find("span").text(staffname2);
        }
        if(ppid!=""){
            $(".details").attr("data-ppID",ppid);
        }
        $(".alert_").click(function(){
            $(".alert_").hide();
            $(".no_order_alert").hide();
        });
        $(".no_order_alert .delete").click(function(){
            $(".alert_").hide();
            $(".no_order_alert").hide();
        });






        $(".btn").find("button").click(function(){

            if (erid!="")
            {
                if (staffid1!="")
                {
                    if (staffid2!="")
                    {
                        //终端机的验证
                        if(posNum!=null&&posNum!=""){
    var myreg= /^(((13[0-9]{1})|(14[0-9]{1})|(15[0-9]{1})|(16[0-9]{1})|(17[0-9]{1})|(18[0-9]{1})|(19[0-9]{1}))+\d{8})$/;

                            if($.trim($("#tel").val())==""){
                                prompt("请输入学员手机号！");
                                return false;
                            }else if(!myreg.test($.trim($("#tel").val()))){
                                prompt("请输入正确格式电话号！");
                                return false;
                            }

                            if($.trim($("#name").val())==""){
                                prompt("请填写学员姓名");
                                return false;
                            }

                        }

                        ajax(erid,staffid1,staffid2,ppid,ername,price);
                    }else{

                        prompt("请选择主管!");
                    }
                }else{
                    prompt("请选择教练!");
                }
            }else{
                prompt("请选择考场!");
            }
        });
        //根据手机号查学员姓名
        $('#tel').bind('input propertychange', function() {
            var tel = $.trim($("#tel").val());
           if(tel.length==11){
               getStudentName(tel);
           }

        })

        $(".pay").click(function () {
            var user = $(this).attr("data-user");
            var sccid = $(this).attr("data-sccId");
            var staffid = $(this).attr("data-staffid");
            document.location.href = basePath+"/ea/jinbi/ea_getwfjchongzhi.jspa?user="+user+"&sccid="+sccid+"&khd=0&flag=&identifying=&ccompanyId=&staffid="+staffid;
        })

    });
</script>
<script>

    function getCookie(c_name)
    {
        if (document.cookie.length>0)
        {
            c_start=document.cookie.indexOf(c_name + "=")
            if (c_start!=-1)
            {
                c_start=c_start + c_name.length+1
                c_end=document.cookie.indexOf(";",c_start)
                if (c_end==-1) c_end=document.cookie.length
                return decodeURI(document.cookie.substring(c_start,c_end))
            }
        }
        return ""
    }



    function delCookie()
    {
        var myDate=new Date();
        myDate.setTime(-10000);//设置时间
        var data=document.cookie;
        var dataArray=data.split("; ");
        for(var i=0;i<dataArray.length;i++){
            var varName=dataArray[i].split("=");
            document.cookie=varName[0]+"=''; expires="+myDate.toGMTString()+"; path=/";
            document.cookie=varName[0]+"=''; expires="+myDate.toGMTString()+"; path=/ea/mappointment";
        }
    }
    function ajax(erid,staffid1,staffid2,ppid,ername,price){
         var tel = $.trim($("#tel").val());
         var name = $.trim($("#name").val());
        var url = basePath + "/ea/mappointment/sajax_ea_goldCoins.jspa";
        $.ajax({
            url: encodeURI(url),
            type: "post",
            dataType: "json",
            data : {
                "examinationRoom.erId":erid,
                tel :tel,
                name:name,
                tuistaffID:staffid1,
            },
            async: true,
            beforeSend: function(){
                if(posNum!=null&&posNum!="") {
                    $(".div-ts").show();
                }
            },
            success: function (data) {
                var jsonresult = eval("(" + data + ")");
                var state = jsonresult.ss;
                var tcc = jsonresult.tcc;
                var sccId = jsonresult.sccId;
                if (state=="00"||state=="01"){
                    ajax1(erid,staffid1,staffid2,ppid,ername,price,sccId);
                }
        //        else  if(state=="01"){
//                    $(".pay").attr("data-user",tcc.account);
//                    $(".pay").attr("data-sccId",tcc.sccId);
//                    $(".pay").attr("data-staffid",tcc.staffid);
//                    $(".alert_").show();
//                    $(".no_order_alert").show();
          //      }
                else  if(state=="02"){
                    document.location.href = basePath+"page/WFJClient/NewLogin.jsp?loginPage=login";
                }else  if(state=="03"){
                    $(".div-ts").hide();
                    prompt("该产品无法购买,请联系该考场工作人员!");
                }else  if(state=="04"){
                    $(".div-ts").hide();
                    prompt("已预约,请完成预约后再次预约!");
                    setInterval(function(){
                        document.location.href = basePath+"ea/mappointment/ea_theTestTime.jspa?sccId=&posNum="+posNum;

                    }, 4000);

                }
            }
        })
    }

    function ajax1(erid,staffid1,staffid2,ppid,ername,price,sccId) {
        /*  var sort = $(".text").find("h5").text();
       var ppid = $(".details").attr("data-ppID");*/



        var url = basePath + "/ea/wfjshop/sajax_ea_Shopping.jspa?";
        $.ajax({
            url: encodeURI(url),
            type: "post",
            dataType: "json",
            data : {
                "sort":ername,
                "indus":price,
                "ppid":ppid,
                "count":1,
                "morre":price,
                yxsccid:sccId
            },
            async: true,
            beforeSend: function(){
                if(posNum!=null&&posNum!="") {
                    $(".div-ts").show();
                }
            },
            success: function (data) {
                var json = eval('(' + data + ')');
                if (json == null) {
                    alert("数据提交失败。请重试");
                } else {
                    ddid = json.ddid;
                    if(ddid=="fail"){
                        prompt("该推荐人没有注册过微分金，请重新填写，或者不填写！");
                        return false;
                    }
                    ajax2(ddid,ppid,erid,staffid1,staffid2,sccId);
                }
            }
        })
    }

    function ajax2(ddid,ppid,erid,staffid1,staffid2,sccId) {
        var url = basePath+"/ea/mappointment/sajax_ea_buySuccess.jspa?";
        $.ajax({
            url: encodeURI(url),
            type: "post",
            dataType: "json",
            data : {
                "bookingInformation.journalNum":ddid,
                "bookingInformation.ppID":ppid,
                "bookingInformation.erId":erid,
                "bookingInformation.coachStaffId":staffid1,
                "bookingInformation.directorStaffId":staffid2,
                "bookingInformation.sccId":sccId
            },
            async: true,
            beforeSend: function(){
                if(posNum!=null&&posNum!="") {
                    $(".div-ts").show();
                }
            },
            success: function (data) {
                var json = eval('(' + data + ')');
                var sccid = json.sccid;
                var cbid = json.object[0];
                var staffname = json.object[1];
                document.location.href = basePath+"/ea/mappointment/ea_successful.jspa?sccId="+sccid+"&ppk.staffName="+staffname+"&cbId="+cbid+"&ddid="+ddid;
            }
        })
    }

    //根据手机号获取姓名
    function  getStudentName(tel) {
        var url = basePath+"/ea/mappointment/sajax_ea_getStudentName.jspa";
        $.ajax({
            url: encodeURI(url),
            type: "post",
            dataType: "json",
            data : {
                tel:tel
            },
            async: false,
            success: function (data) {
                var json = eval('(' + data + ')');
                var stuname = json.stuname;
                $("#name").val(stuname)
            }
        })
    }

    $(document).on("click",".coach",function () {
       var type= $(".examination").find("span").text();
       if(type!=""){
           document.location.href= "<%=basePath%>ea/mappointment/ea_jump.jspa?companyId=${param.companyId}&type=教练";
       }else {
           prompt("请选择场地")
       }

    });
    $(document).on("click",".director",function()  {
        var type= $(".examination").find("span").text();
        if(type!=""){
            document.location.href= "<%=basePath%>ea/mappointment/ea_jump.jspa?companyId=${param.companyId}&type=主管";
        }else {
            prompt("请选择场地")
        }

    });
</script>

</body>
</html>