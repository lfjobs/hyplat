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
    <title>北京天太世统科技有限公司</title>
    <script type="text/javascript" src="<%=basePath %>js/BuildPlatform/font-size.js"></script>
    <link type="text/css" rel="stylesheet" href="<%=basePath %>css/BuildPlatform/swiper-3.3.1.min.css">
    <link type="text/css" rel="stylesheet" href="<%=basePath %>css/BuildPlatform/new_style.css">
    <script type="text/javascript" src="<%=basePath %>js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>js/BuildPlatform/swiper-3.3.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>js/BuildPlatform/new-page.js"></script>
<style>

#jj{
	position: fixed;
    border: 0px;
    overflow: auto;
    width: 100%;
    height: 100%;
    top: 0;
    left: 0;
    z-index: 9;
	display: none;
}
#prompt div{
				width: 70%;
				background: rgba(0,0,0, 0.5);
			}
</style>
</head>
<body>
<script type="text/javascript">
var basePath='<%=basePath%>';
</script>
<header>
   <ul>
       <li style="width: 10%;">
           <a href="<%=basePath%>/people/manage/ea_teamAdd.jspa?"><img src="<%=basePath %>images/BuildPlatform/left_jt.png"></a>
       </li>
       <li style="width: 80%;">手动添加</li>
       <li style="width: 10%;"></li>
       <div class="clearfix"></div>
   </ul>
</header>
<div class="content_hidden">
    <div class="content">
    <form method="post" enctype="multipart/form-data">
        <div class="con">
            <ul class="team team_det">
                <li>
                	<s:if test="staff==null||staff.headimage==null||staff.headimage==''">
                		<img src="<%=basePath %>images/BuildPlatform/datu.png" alt="" class="head" id="image_box">
                    	<input type="file" name="photo" accept="image/jpg,image/png" id="head_img">
                	</s:if>
                    <s:else>
                    	<img src="<%=basePath %>${staff.headimage}" alt="" class="head" id="image_box">
                    </s:else>
                    <img src="<%=basePath %>images/BuildPlatform/right.png" alt="" id="right">
                </li>
            </ul>            
                <ul class="personal">
                    <li>
                        <h4>姓名</h4>
                        <div class="left left_">
                            <input type="text" value="${staff.staffName }" name="staff.staffName" placeholder="姓名">
                        </div>
                    </li>
                    <li>
                        <h4>手机号</h4>
                        <div class="left left_">
                            <input type="tel" value="${staff.reference }" name="staff.reference" placeholder="手机号">
                        </div>
                    </li>
                    <li>
                        <h4>员工职位</h4>
                        <div class="left left_">
                            <input type="text" value="${staff.position}" name="staff.position" placeholder="员工职位">
                        </div>                      
                    </li>
                    <li>
                        <h4>员工简介</h4>
                        <div class="left" id="brief">                         
                        </div>
                        <img src="<%=basePath %>images/BuildPlatform/right.png" alt=""/>
                        <iframe id="jj" border="0" width="100%" name="iframe_con" outline="0" src="<%=basePath %>page/ea/main/production/cprocedure/qrshare/qrshare_edit.jsp?flag=team"></iframe>
                    </li>
                </ul>
                <input type="hidden" value="" id="ppId" name="ppId"/>
                <input type="hidden" value="${staff.staffID }" name="staff.staffID"/>
                <input type="hidden" value="${staff.staffKey }" name="staff.staffKey"/>
                <input type="button" value="保存提交" id="sub">
            </form>
        </div>
    </div>
    <div id="prompt" style="width: 100%; display: none;">
			<center>
				<div>
					<span style="position: relative; top: 19.8%;"></span>
				</div>
			</center>
	</div>
</div>
 	
<script type="text/javascript">
    $(document).ready(function(){
        $("header").css("height",$(window).height()*0.08-1+"px");
        $("header").css("line-height",$(window).height()*0.08-1+"px");
        $(".content_hidden").attr("style",";overflow: hidden;"+"height:"+$(window).height()*0.92+"px");
        $(".content").attr("style","overflow: hidden;"+"height:"+$(window).height()*0.92+"px");
        $(".head_top").css("height",$(window).height()*0.08-1+"px");
        $(".head_top ul li").css("line-height",$(window).height()*0.05+"px");
        $(".head_top ul li:nth-child(1) dl").css("margin",$(window).height()*0.015+"px");
        $(".head_top ul li:nth-child(2) input").attr("style","margin:"+$(window).height()*0.015+"px;margin-left:0;line-height:"+$(window).height()*0.05+"px;");

        $("#prompt").css("position","absolute").css("top",$(window).height()*0.09+"px");
		$("#prompt").find("div").css("height",$(window).height()*0.06+"px").css("font-size",$(window).height()*0.0285+"px").css("color","#FFFFFF");
		$("#prompt").find("div").css("-moz-border-radius",$(window).height()*0.015+"px").css("-webkit-border-radius",$(window).height()*0.015+"px");

		$(".con").css("height",$(window).height()*0.92+"px");
		
		//2017.1.7 手机端输入法遮盖输入框
	    $(".personal li .left input").focus(function(){
            $(".content_hidden").attr("style","height:"+$(window).height()*1.3+"px");
        });
        $(".personal li .left input").blur(function(){
            $(".content_hidden").attr("style","height:"+$(window).height()*0.92+"px");
        });

    $("#brief").click(function(){
    	var ppId=$("#ppId").val();
    	if(ppId!=""){
    		$.ajax({
    			url:basePath+"/people/manage/sajax_getPersonalBrief.jspa?",
    			type:"get",
    			async:false,
    			data:{"ppId":ppId},
    			success:function (data){
    				var member=eval("("+data+")");
    				var obj=member.obj;
    				var content=member.content;
    				$("#jj").contents().find(".qrw_tit").text(obj[2]);
    				$("#jj").contents().find("#ppid").text(obj[1]);
    				$("#jj").contents().find("#goodsid").text(obj[0]);
    				$("#jj").contents().find("#image").text(obj[3]);
    				$("#jj").contents().find(".qrw_tit").find("input").val(obj[2])
    				$("#jj").contents().find("#idss").contents().find(".art_con").html(content);
    			}
    		});  		
    	}
    	$("#jj").css("display","block");
    	var _h = $(window).height()-$("#jj").contents().find(".com_head").height()-$("#jj").contents().find(".qrw_tit").outerHeight()-$("#jj").contents().find(".qrw_add").height()-10;
        $("#jj").contents().find("#idss").height(_h);
    });   
    
    function check() {
    	var reg=new RegExp("^1[3|4|5|7|8][0-9]\\d{8}$");
    	var flag=true;
        var name=$(".left_").eq(0).find("input").val();
        if(name == ""){
        	prompt("姓名不能为空！");
            flag= false;
        }       
        var name2=$(".left_").eq(1).find("input").val();
        if(name2 == ""){
        	prompt("手机号不能为空！");
            flag= false;
        }       
		if(!reg.test(name2)){
			prompt("手机号格式不正确！");
            flag= false;
		}
        var name3=$(".left_").eq(2).find("input").val();
        if(name3 == ""){
        	prompt("员工职位不能为空！");
            flag= false;
        }
        if($("#brief").find("h").text()==''){
        	prompt("请编辑员工简介！");
            flag= false;
        }
        if($("input[name='photo']").val()==''){
        	prompt("请上传头像!");
            flag= false;
        }
		return flag;
    }
	$("#sub").click(function(){
		var url=basePath+"/people/manage/sajax_ajaxAdd.jspa?";
		var formData=new FormData(document.forms[0]);
		if(!check()){
			return;
		}else{
		$.ajax({
			url:encodeURI(url),
			type:"post",
			data:formData,
			async:false,
			cache:false,
			contentType:false,
			processData:false,
			success:function(data){
				var member=eval("("+data+")");
				var b=member.b;
				if(b){
					alert("添加成功");
					window.location.href=basePath+"/people/manage/ea_teamShow.jspa?";
				}
			}
		});
		}
	});
});
 function prompt(obj){
		if($("#prompt").css("display")!="none")
			return;
		$("#prompt").find("span").text(obj);
		$("#prompt").fadeIn(500);
		setTimeout(function(){
			$("#prompt").fadeOut(500);
			$("#prompt").find("span").text("");
		}, 2000);
	}
</script>
<script type="text/javascript">
    //头像上传显示
$('#head_img').change(function() {
        var file = this.files[0];
        var reader = new FileReader();
        reader.onload = function() {
            // 通过 reader.result 来访问生成的 DataURL
            var url = reader.result;
            setImageURL(url);
        };
        reader.readAsDataURL(file);
    });

    var image = new Image();

function setImageURL(url) {
        document.getElementById("image_box").src = url;
}


</script>


</body>
</html>