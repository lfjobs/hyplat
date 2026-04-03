<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<html>
<%@ include file="/page/ea/BuildPlatform/wfjIndustryPlatfrom/coom.jsp"%>
<body>

<header>
   <ul>
       <li style="width: 10%;">
           <a href="javascript:history.go(-1)"><img src="<%=basePath%>images/BuildPlatform/left_jt.png"></a>
       </li>
       <li style="width: 80%;">添加村级经济平台</li>
       <li style="width: 10%;"></li>
       <div class="clearfix"></div>
   </ul>
</header>
<div class="content_hidden content_hidden_">
    <div class="content content_">
        <div class="gjpt_search">
        </div>
        <div class="con_">
        <%-- action="<%=basePath %>ea/WfjIndustryPlatform/ea_getAdd.jspa" --%>
            <form action="<%=basePath %>ea/WfjIndustryPlatform/ea_getAdd.jspa" id="act"  onsubmit="return check(this)" method="post" enctype="multipart/form-data">
                <ul class="gjpt_cjpt">
                    <li>
                        <h3>经济平台logo</h3>
                        <div class="head">
                            <input type="file" accept="<%=basePath%>images/BuildPlatform/jpg,image/png" id="head_img" class="head_img" name="pictureList">
                            <div class="head_"><img src="<%=basePath%>images/BuildPlatform/moren.png" alt="" id="image_box"></div>
                        </div>
                        <img src="<%=basePath%>images/BuildPlatform/right.png" alt="" class="gjpt_right">
                    </li>
                    <li class="text">
                        <h3>平台名称</h3>
                        <input type="text" readonly="readonly"  id="PTname" value="${param.goodsname}"   placeholder="请重新选择" >
                       
                   		<input type="hidden"  value="1"  name="cdl.companyAddress">
                   		
                   		<input type="hidden"  value="${param.ppid}"  name="ppidUser">
                   		<input type="hidden"  value="0"  id="checkid">
                   		
                   		
                    </li>
                     <li class="text">
                        <h3>公司名称</h3>
                        <input type="text" id="company"  name="company.companyName"  placeholder="请输入" onblur="checkCompany()">
                  		<input type="hidden" id="companyIden"  name="company.companyIdentifier">
                  		<input type="hidden"  id="ccmename" name="ccmomtype">
                    </li>
                    <li class="text">
                        <h3>姓名</h3>
                        <input placeholder="请输入" id="name" name="cdl.companyManager">
                    </li>
                    <li class="text">
                        <h3>联系电话</h3>
                        <input type="text" placeholder="请输入" id="tel" name="cdl.companyPhone">
                    </li>
                </ul>
                <input type="submit" value="提交" class="gjpt_tj">
            </form>
        </div>
    </div>
</div>


<script>
var ppid = "${param.ppids}";
var ppids = "${param.ppid}";
    $(document).ready(function(){
        $("header").css("height",$(window).height()*0.08-1+"px");
        $("header").css("line-height",$(window).height()*0.08-1+"px");
        $(".content_hidden").attr("style",";overflow: hidden;"+"height:"+$(window).height()*0.92+"px");
        $(".content").attr("style","overflow: hidden;"+"height:"+$(window).height()*0.92+"px");
        $(".content_").attr("style","overflow: hidden;"+"height:"+$(window).height()*0.92+"px");
        $(".head_top").css("height",$(window).height()*0.08-1+"px");
        $(".head_top ul li").css("line-height",$(window).height()*0.05+"px");
        $(".head_top ul li:nth-child(1) dl").css("margin",$(window).height()*0.015+"px");
        $(".head_top ul li:nth-child(2) input").attr("style","margin:"+$(window).height()*0.015+"px;margin-left:0;line-height:"+$(window).height()*0.05+"px;");

        $(".content_hidden_").css("height",$(window).height()*0.92+"px");
        $(".gjpt_search").css("height","0");
        $(".con_").css("height",$(".content_").height()-$(".gjpt_search").height()-6+"px");
        var PTname = $("#PTname").val();
        if(PTname!=null){
         check2(); 
        }
       $("#PTname").click(function(){
       
       url = basePath+"/ea/WfjIndustryPlatform/ea_getQuery.jspa?type=04&ppid="+ppid;
       document.location.href=url;
       
       });
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
            $(".gjpt_cjpt li .head input").removeClass("head_img")
        });

        var image = new Image();

        function setImageURL(url) {
            document.getElementById("image_box").src = url;
        }


		$(".gjpt_tj").click(function(){
			
			$(".gjpt_tj").addClass("yes")
			$(".gjpt_tj").unbind("click");
		})

    });
</script>
<script>
function checkCompany(){
var company=$("#company").val();
url = basePath+"ea/WfjIndustryPlatform/sajax_ea_getVerificationCompany.jspa?company="+company;
	$.ajax({
		url: url,
		type: "post",
		async: false,
		dateType: "json",
		success: function(data) {
			var member = eval("(" + data + ")");
			var name = member.voi;
			if(name == "01"){
				alert("验证不通过,该公司已存在请重新输入！");
				$("#company").val("");
				return
			}
			$("#companyIden").val(company);
			$("#ccmename").val(company);
				
		},
		error: function(data) {
			
		}
	})


}


function check2(){
	//平台名称
   
	url = basePath+"ea/WfjIndustryPlatform/sajax_ea_getVerification.jspa?ppid="+ppids;
	$.ajax({
		url: url,
		type: "post",
		async: false,
		dateType: "json",
		success: function(data) {
			var member = eval("(" + data + ")");
			var name = member.voi;
			if(name == "01"){
				alert("验证不通过,该平台已被抢注，请重新选择！");
				$("#PTname").val("");
				return
			}
			
			
		},
		error: function(data) {
			
		}
	})
	
}
 var checkid=0;
    function check(form) {
    
    	if(checkid==1){
			return false;
		}
			
          if($("#head_img").hasClass("head_img")){
            alert("请上传Logo");
            return false;
	        }else{
	
	        }
	        //平台名称
	        var TPname=document.getElementById("PTname").value;
	        if(TPname == ""){
	            alert("平台名称不能为空！");
	            return false;
	        }else{
	
	        }
	        //姓名
	        var name=document.getElementById("name").value;
	        if(name == ""){
	            alert("姓名不能为空！");
	            return false;
	        }else{
	
	        }
	        
	      //公司名称
	        var comname=document.getElementById("company").value;
	        if(comname == ""){
	            alert("公司名称不能为空！");
	            return false;
	        }else{
	
	        }
	        
	        
	       
	        //联系电话
	       /*  var tel=document.getElementById("tel").value;
	        var reg=/^([0-9]|[\-])+$/g ;
	        if(tel == ""){
	            alert("电话不能为空！");
	            return false;
	        }else if(tel.length<7 || tel.length>18){
	            alert("请填写正确格式");
	            return false;
	        }else{
	
	        } */
	        
	        var tel=document.getElementById("tel").value;
	        reg = /^([0\+]\d{1,4}-)?(13[0-9]|14[0-9]|15[0-9]|18[0-9]|17[0-9])\d{8}$/;
	        if(tel == ""){
	            alert("电话不能为空！");
	            return false;
	        }else if(!reg.test(tel)){
	            alert("请填写正确手机格式");
	            return false;
	        }else{
	
	        }
	        
			checkid=1;
			
			return true;
			
			

    }
</script>
</body>
</html>