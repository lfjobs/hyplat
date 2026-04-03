<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";

%>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
	<script type="text/javascript" src="<%=basePath %>js/BuildPlatform/font-size.js"></script>
	<script type="text/javascript" src="<%=basePath %>js/jquery-1.9.1.min.js"></script>
	<link type="text/css" rel="stylesheet" href="<%=basePath %>css/ea/production/new_style.css">
    <title>产品分类</title>
</head>
<script>
   var basePath='<%=basePath%>';
   var pagenumber=0;
   var pagecount=0;
   var user='${user}';
   var t;
   var num = 0;
   var delList = "";
   var companyId='${companyId}';
   var backu='<%=session.getAttribute("vipback")%>';
   var ret='${ret}';
   var sys='${sys}';
</script>
<body>
<header>
	<div class="top">
	<ul>
		<li style="width: 10%;">
			<a href="<%=basePath%>/ea/vipcenter/ea_vipDemand.jspa"><img src="<%=basePath %>images/WFJClient/PersonalJoining/back_03.png"></a>
		</li>
		<li style="width: 70%;text-indent: 10%;">产品分类</li>
		<li style="width: 20%;" class="redact">编辑分类</li>
	</ul>
		</div>
</header>
<div class="content_hidden">
	<div class="content">
		<div class="con">
			<form action="<%=basePath%>ea/productslaunch/ea	_ProductTypeCRUD.jspa?flag=isform" name="typeForm" id="typeForm" method="post">
				<input type="submit" name="submit" style="display:none"/>
				<input type="hidden" id="delList" name="delList" style="display:none"/>
				<ul class="product" style="padding-bottom: 4rem;">
				</ul>
			</form>
			<div align="center">
				<input type="button" value="添加" class="add">
			</div>

		</div>
	</div>
</div>

<script>
    $(document).ready(function(){
        loaded();
        $("header").css("height",$(window).height()*0.08-1+"px");
        $("header").css("line-height",$(window).height()*0.08-1+"px");
        $("header ul li").css("line-height",$(window).height()*0.08+"px");
        $(".content_hidden").attr("style",";overflow: hidden;"+"height:"+$(window).height()*0.92+"px");
        $(".content").attr("style","overflow: auto;"+"height:"+$(window).height()*0.92+"px");
        $(".head_top").css("height",$(window).height()*0.08-1+"px");
        $(".content .con").css("height",$(window).height()*0.92+"px");

        //编辑分类
        $(document).on("click",".redact",function(){
            $(this).text("保存");
            $(this).removeClass().attr("id","save");
            $(".typeName").each(function(){
                if($(this).prev().val()!=1){
                    $(this).removeAttr("readonly","readonly");
                    $(this).next().attr("style","display:block");
                    $(this).next().attr("src", "<%=basePath %>images/ea/production/ico-delete.png").addClass("det");
				}
            });
            if($(".product").find(".typeName").size()==0){
                $(".product").html("");
			};
            $(".add").show();
        });
        $(document).on("click",".product li .det",function(){
            if($(this).attr("placeholder")==null||$(this).attr("placeholder")==""){
                if(delList==""){
                    delList=$(this).prev().prev().prev().prev().val();
				}else {
                    delList+=(","+$(this).prev().prev().prev().prev().val());
				}
				$("#delList").val(delList)
			}
            $(this).parents("ul li").remove();
            if($(".product").children().length==0){
                if(delList){
                    var delLists = delList.split(",");
                }else {
                    $(".product").append("<div class='no'><img src='"+basePath+"images/ea/production/no.png' width='100%'><p>您还没有添加分类，赶快添加吧</p></div>");
                    $("header .top ul").children("li").eq(2).hide();
                    return;
                }
                var count =0;
                for(var m in delLists){
                    if(delLists[m] == "undefined"){
                        count++;
                    }
                    if(count == delLists.length){
                        $(".product").append("<div class='no'><img src='"+basePath+"images/ea/production/no.png' width='100%'><p>您还没有添加分类，赶快添加吧</p></div>");
                        $("header .top ul").children("li").eq(2).hide();
                    }
                }
            }
        });
        //保存分类
        $(document).on("click","#save",function(){
            document.typeForm.submit.click();
            $(this).text("编辑分类");
            $(this).removeAttr("id").addClass("redact");
            $(".product li .txt input").attr("readonly","readonly");
            $(".product li .txt img").attr("src", "<%=basePath %>images/ea/production/ico_right.png").removeClass("det");
            $(".add").hide();
            delList = "";
        });
        $(".add").click(function(){
            $("header .top ul").children("li").eq(2).show();
            if($(".product").find(".typeName").size()==0){
                $(".product").html("");
            };
            var li = "<li><div class='txt'><input class='typeName' type='text' value='' placeholder='请添加分类'><img src='<%=basePath %>images/ea/production/ico-delete.png' class='right det'></div></li>";
            $(".product").append(li);
            $(".product li").last().find("input").focus();
        })
        $(document).on("change",".typeName",function () {
            console.info($(this).val());
            $(this).prev().prev().prev().prev().attr("name","typeList["+num+"].categoryKey");
            $(this).prev().prev().prev().attr("name","typeList["+num+"].categoryId");
            $(this).prev().prev().attr("name","typeList["+num+"].companyId");
            $(this).prev().attr("name","typeList["+num+"].status");
			$(this).attr("name","typeList["+num+"].categoryName");
			num++;
        })
    });
    function loaded (){
        clearTimeout(t);
        pagenumber++;
        var url=basePath+"ea/productslaunch/sajax_ea_ajaxProductType.jspa?";
        $.ajax({
            url : url,
            type : "get",
            async : false,
            dataType : "json",
            data:{
                "pageForm.pageNumber":pagenumber,
                "companyId":companyId
            },
            success : function cbf(data) {
                var member = eval("(" + data + ")");
                if(member!=null){
                    var pageForm = member.pageForm;
                    var prostr="";
                    if(pageForm!=null&&pageForm.recordCount>0){
                        var producttypelist=pageForm.list;
                        pagenumber=pageForm.pageNumber;
                        pagecount=pageForm.pageCount;
                        if(producttypelist!=null){
                            $(".last").removeClass("last");
                            for(var i=0;i<producttypelist.length;i++){
                                var pro=producttypelist[i];
                                prostr+="<li class='last'>";
                                prostr+=" <div class='txt'>"
                                prostr+="<input type='hidden' value='"+pro[0]+"'>";
                                prostr+="<input type='hidden' value='"+pro[1]+"'>";
                                prostr+="<input type='hidden' value='"+pro[2]+"'>";
                                prostr+="<input type='hidden' value='"+pro[4]+"'>";
                                prostr+="<input class='typeName' type='text' value='"+pro[3]+"' placeholder='' readonly='readonly'>";
                                prostr+="<img src='<%=basePath %>images/ea/production/ico_right.png' class='right' style='display: none;'>";
                                prostr+="</div>";
                                prostr+="</li>";
                            }
                            $(".product").append(prostr);
                            getHeight();
                        }
                    }else {
                        $(".product").append("<div class='no'><img src='<%=basePath %>images/ea/production/no.png' width='100%'><p>您还每天添加分类，赶快添加吧</p></div>");
                        $(".redact").text("保存");
                        $(".redact").removeClass().attr("id","save");
                        $("header .top ul").children("li").eq(2).hide();
                        $(".add").show();
                    }
                }
            },
            error: function cbf(data){
                alert("产品加载失败");
            }
        });
    }
    function getHeight(){
        t=setTimeout("getHeight()", 200);
        if($(".product").height()-($(window).height()-$(".product").offset().top)<$(window).height()){
            if(pagenumber<pagecount){
                loaded();
            }
        }
    }
</script>
</body>
</html>
<script>
    // var num1=num2=num3=0
    window.onload = window.onresize = function(){
        //含义：当窗口加载完成和窗口尺寸变化的时候都执行大括号里面的命令
        //获取窗口的尺寸
        var clientWidth = document.documentElement.clientWidth;
        //console.log(clientWidth);
        //通过屏幕宽度去设置不同的后台根字体的大小
        //document.getElementsByTagName('html')[0].style.fontSize = clientWidth/16+'px';
        document.getElementsByTagName('html')[0].style.fontSize = clientWidth/640*40+'px'
    }
</script>