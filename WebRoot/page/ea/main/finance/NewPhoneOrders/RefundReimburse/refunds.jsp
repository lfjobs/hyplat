<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>退货</title>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/ea/finance/NewPhoneOrders/phoneOrders.css"/>
    <script type="text/javascript" src="<%=basePath %>js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript">
		var cashid="${cashId}";
		var staffid="${staffid}";
		var number = "${qu}";
		var pri = "${price}";
		var money = "${money}";
		var tp = "${tp}"
	</script>
</head>

<body>

<!-- 弹出层 -->
<div id="prompt" style="width: 100%;display: none;">
	<center>
		<div>
			<span style="position:relative;top:19.8%;z-index:100;"></span>		
		</div>	
	</center>
</div>

<form enctype="multipart/form-data" id="SearchForm" name="SearchForm" method="post" action="">
	<input type="submit" id="submit" style="display:none;"/>
	<div class="header">
	    <ul>
	        <li style="width: 10%;"><a href="javascript:history.go(-1)"><img src="<%=basePath %>images/ea/finance/NewPhoneOrders/left.png"></a></li>
	        <li style="width: 80%;text-align: center;">退货</li>
	        <li style="width: 10%"></li>
	    </ul>
	</div>
	
	<div class="content_hidden">
		
		    <div class="content  app_content">
		        <div class="mil">
		            <span>退款类型</span>           
		            <p id="tklx"><span>退货退款</span><a><img src="<%=basePath %>images/ea/finance/NewPhoneOrders/right.png" alt=""></a></p>
		        </div>
		        <div class="mil">
		            <span>退款原因</span>
		            <p id="tkyy"><span>请选择退款原因</span><a><img src="<%=basePath %>images/ea/finance/NewPhoneOrders/right.png" alt=""></a></p>
		        </div>
		        		        
		        <div id="show">
		          <div class="mil">
		            <span>退货数量</span>
		            <p><span id="numb"><input type="text" id="nu" value="${qu}" ></span></p>		           
		        </div>
		        <div class="mil">
		            <span>单价</span>
		            <p><span id="numb"><input type="text" id="price" value="${price}" readonly="readonly" ></span></p>		           
		        </div>  
		        </div>
		        		             
		        <div class="mil">
		            <span>退款金额(&yen;)</span>
		            <p><span id="money"><input type="text" id="mon" value="${money}" readonly="readonly"></span></p>		           
		        </div>
		        <div class="mil">
		            <textarea rows="4" placeholder="退款(退货)说明" id="account" onkeyup="checkLen(this)"></textarea>		          
		       		<div id="sum" style="float: right;">您还可以输入<span id="count">50</span>个文字     </div>
		       		<div class="up_pic" id="pic">
                		<div class="btn_ btn_3"><img src="<%=basePath %>images/ea/finance/NewPhoneOrders/jia.png" alt="" id="image_box"></div>
            		</div>		       
		        </div>
		    </div>
		    <div class="btn_2">
		         <input type="button" value="提交申请" id="tijiao">
		    </div>		    
	</div>
	
	<div class="alert_sh">	
		<ul class="tklx alert_" id="tu">
			<li id="tu">退货退款<div><h5></h5></div></li>
			<li id="re">退款<div><h5></h5></div></li>	        
   		 </ul>	    	    	    
	     <ul class="tkyy alert_">
	         <li>七天无理由退换<div><h5></h5></div></li>
           	 <li>大小尺寸与商品描述不符<div><h5></h5></div></li>
           	 <li>主商品破损<div><h5></h5></div></li>
           	 <li>效果不好/不喜欢/不想要<div><h5></h5></div></li>
             <li>假冒品牌<div><h5></h5></div></li>
           	 <li>做工瑕疵<div><h5></h5></div></li>
           	 <li>材质不符<div><h5></h5></div></li>
           	 <li>配件破损<div><h5></h5></div></li>
        	 <li>颜色/款式/团与描述不符<div><h5></h5></div></li>
        	 <li>买家发错货<div><h5></h5></div></li>
        	 <li>其他<div><h5></h5></div></li> 	        
	    </ul>	    
	</div>

</form>

<script>
    $(document).ready(function(){
        $(".header ul li").css("line-height",$(window).height()*0.08+"px");
        $(".header").css("height",$(window).height()*0.08+"px");
        $(".content_hidden").css("height",$(window).height()*0.92+"px");
        $(".btn_2").css("height",$(window).height()*0.1+"px");
        $(".content").css("height",$(window).height()*0.82+"px");   
      
        //弹出层
        $("#prompt").css("position","absolute").css("top",$(window).height()*0.10+"px");
		$("#prompt").find("div").css("height",$(window).height()*0.06+"px").css("font-size",$(window).height()*0.0285+"px").css("color","#FFFFFF");
		$("#prompt").find("div").css("-moz-border-radius",$(window).height()*0.015+"px").css("-webkit-border-radius",$(window).height()*0.015+"px");

        
        //退款类型
        $(".alert_sh").click(function(){
            $(".alert_sh").hide();
            $(".alert_sh .tkyy").hide();
            $(".alert_sh .tklx").hide();
        });
        //退款类型
        $("#tklx").click(function(){
            $(".alert_sh").show();
            $(".alert_sh .tklx").show();                    
        });
        $(".alert_sh .tklx li").click(function(){
            var txt=$(this).text();
            $(this).addClass("active").siblings().removeClass("active");           
            $(".alert_sh").hide();
            $("#tklx span").text(txt);                        
            if(txt == "退款"){
            	$("#show").hide();
            	$("#pic").hide();
            }else{           	             
            	$("#show").show();
            	$("#pic").show();
            }
            
            //切换退款（退货退款）是，返回原始状态
            $("#tkyy span").text("请选择退款原因");
            $("#nu").val(number);
            $("#mon").val(pri*number);	
            $("#account").val(null);
                       
           	var flag= $(this).attr("id");          	          
           	var str= new Array();
           	$(".tkyy").empty();
           	if(flag=='re'){           		
           		str.push("<li>不喜欢/不想要<div><h5></h5></div></li>");
           		str.push("<li>为按约定时间发货<div><h5></h5></div></li>");
           		str.push("<li>快递/物流一直没有送到<div><h5></h5></div></li>");
           		str.push("<li>快递/物流无跟踪记录<div><h5></h5></div></li>");
           		str.push("<li>其他<div><h5></h5></div></li>");
           	}else if(flag=='tu'){
           		str.push("<li>七天无理由退换<div><h5></h5></div></li>");
           		str.push("<li>大小尺寸与商品描述不符<div><h5></h5></div></li>");
           		str.push("<li>主商品破损<div><h5></h5></div></li>");
           		str.push("<li>效果不好/不喜欢/不想要<div><h5></h5></div></li>");
           		str.push("<li>假冒品牌<div><h5></h5></div></li>");
           		str.push("<li>做工瑕疵<div><h5></h5></div></li>");
           		str.push("<li>材质不符<div><h5></h5></div></li>");
           		str.push("<li>配件破损<div><h5></h5></div></li>");
        	    str.push("<li>颜色/款式/团与描述不符<div><h5></h5></div></li>");
        	    str.push("<li>买家发错货<div><h5></h5></div></li>");
        	    str.push("<li>其他<div><h5></h5></div></li> ");
           	}
           	$(".tkyy").append(str.join(""));
        });                   
        //退款原因
        $("#tkyy").click(function(){       	  	       	
            $(".alert_sh").show();
            $(".alert_sh .tkyy").show();
        });
        
        $(".alert_sh .tkyy").on("click","li",function(){
        	 var txt=$(this).text();
             $(this).addClass("active").siblings().removeClass("active");
             $(".alert_sh").hide();
             $(".alert_sh .tkyy").hide();
             $("#tkyy span").text(txt);
        });
  
    })
         
</script>
<script>
    //上传照片(提交服务器时需判断input=file 是否有空值，遍历删除；)
    var click_times = 0;
    $(".up_pic .btn_3").click(function() {
        if($(".h_img").length==3){  
        	$("#img").hide();
        	prompt("您最多只能上传3张图片");          
        }else{
            click_times++;
            var _id = "click_" + click_times;
            var t = '<div class="upload_img" id=' + _id + '><div class="img_box"><img src="" alt=""></div><input type="file" name="photo" accept="image/*" style="opacity: 0;width: 3rem;margin-top: 1.5rem;"><i class="del_upimg"></i></div>'
            $(".up_pic .btn_3").before(t);
            var $id = $('#' + _id);
            $id.hide();
            var $id_inp = $('#' + _id + ' ' + 'input');
            $id_inp.click();
            //Input file选择图片上传事件
            $id_inp.one("change", function() {
                var file = this.files[0];
                var reader = new FileReader();
                reader.onload = function() {
                    // 通过 reader.result 来访问生成的 DataURL
                    var url = reader.result;
                    $id.find("img").attr("src", url)
                };
                reader.readAsDataURL(file);
                $id.show();
                $id.addClass("h_img");
            });
        }

    });
    //上传图片删除功能
    $(".up_pic").on("click", ".del_upimg", function(e) {
        e.stopPropagation();
        $(this).parent().detach();
    });

</script>
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

</body>
<script type="text/javascript">
	$(document).ready(function(){
		var basePath = "<%=basePath%>";
				
		$("#nu").click(function(){
			$("#nu").val("");			
		});
		$("#nu").blur(function(){
			var nu = $("#nu").val();
			var sum = nu * pri;
			if(nu==null || nu == ""){
				$("#nu").val(number);
				$("#mon").val(pri*number);				
			}else{
				$("#mon").val(sum)
			}
		});
				
		$("#nu").on('input', function() {			
			var nu = $("#nu").val();
			var reg=/^[1-9]\d*$|^0$/;  //判断输入是否是数字
			if(reg.test(nu)==true){				
				if(nu == 0){
					prompt("请重新输入！");
					$("#nu").val("");
					$("#mon").val("");				
				}else{
					if(parseInt(nu)>parseInt(number)){//判断输入的数字是否大于购买数
					   $("#nu").val(number);
					   var money = number * pri;
					   $("#mon").val(money);
					}
				}							
			}else{
				prompt("输入格式错误，请重新输入！");	
				$("#nu").val("");
				$("#mon").val("");
			}			
        });				
		
		$("#tijiao").click(function(){			
			var type = $("#tklx").text();//退款类型			
			if(type=="退款"){
				var tp = "00";//退款
			}else{
				var tp = "01";//退货
			}			
			var reason = $("#tkyy").text();//退款原因
			var money = $("#mon").val();//退款金额
			var account = $("#account").val();//退款说明			
			var num = $("#nu").val();//退款数量
			
			if(reason=="请选择退款原因"){
				prompt("请选择退款原因");				
			}else if(num.length == 0 || num.match(/^\s+$/g)){
				prompt("请输入退货的数量！");
			}else if(account.length == 0 || account.match(/^\s+$/g)){
				prompt("请填写退货说明！");				
			}else if(confirm("你确定要提交吗?")){
				var url= basePath +"ea/refundMoney/ea_getReturnRefund.jspa?cashId="+cashid+"&type="+type+"&reason="+reason+"&money="+money+"&account="+account+"&num="+num+"&staffid="+staffid+"&tp="+tp;				
				$("#SearchForm").attr("action",url);
				$("#submit").click();
			}								
		});		
	});
	
	//弹出层
	function prompt(obj){
		
		if($("#prompt").css("display")!="none"){
			return
		}
		$("#prompt").find("span").text(obj);
		$("#prompt").fadeIn(500);
		setTimeout(function(){
			$("#prompt").fadeOut(500);
			$("#prompt").find("span").text("");		
		},2000);				
	}
	
	//文本框输入字数限定
	function checkLen(obj){
		var maxChars = 50;//最多的字符数
		if(obj.value.length > maxChars){
			obj.vale = obj.value.substring(0,maxChars);
		}
		var curr = maxChars - obj.value.length;
		document.getElementById("count").innerHTML = curr.toString();			
	}
	
	
</script>
</html>