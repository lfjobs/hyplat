$(function(){
	//弹出层
	$("#prompt").css("position","absolute").css("top",$(window).height()*0.50+"px").css("z-index","999999");
	$("#prompt").find("div").css("line-height",$(window).height()*0.1+"px").css("height",$(window).height()*0.1+"px").css("font-size",$(window).height()*0.0285+"px").css("color","#FFFFFF");
	$("#prompt").find("div").css("-moz-border-radius",$(window).height()*0.015+"px").css("-webkit-border-radius",$(window).height()*0.015+"px");

	document.myform.reset();
	$("#tel").val(account);});
var t = "";

//判断输入验证码
	function yanz(){
	   	if(q==0){
			prompt("请先获取验证码");
	    	return;
        }

	}

      	

     function duanxin(){
      	var count=$("#tel").val();
		if(times<59){
			prompt("请稍后");
			return;
		}
		if(count==""){
			prompt("手机号为空");
		    return;
		}
		var reg=/^\d{11}$/;
		if(!reg.test(count)){
			prompt("手机号码输入错误");
		    return;
		}
		update(times);
		q=1;

		 $.ajax({
			 cache : true,
			 type :"POST",
			 url : basePath+"/ea/android/sajax_ea_getduanxin.jspa?pahe="+count,
			 async :false,
			 dataType : "json",
			 success :function(data){
				 var member = data;
				 i = member.returna;
			 }
		 });

     }
   	function update(num) {
		if(num>0){
			$(".huoquyzm").val("已发送（"+num+"）");
			$(".huoquyzm").css("cursor","not-allowed");
			$(".huoquyzm").css("backgroundColor","#999");
			$(".huoquyzm").css("color","#FFF");
	        times = num;
			t = setTimeout(function(){
					update(num-1);
			},1000);
		}else{
			$(".huoquyzm").css("cursor","pointer");
			$(".huoquyzm").css("backgroundColor","#F74C31");
			$(".huoquyzm").css("color","#FFF");
			$(".huoquyzm").val("获取验证码");
			times=59;
		}
	}
    $("#zc2").click(function(){
		if($("#validate").val().length<6){
			prompt("验证码不能小于六位数");
			return false;
		}
		if($("#validate").val()==''){
			prompt("请填写验证码");
			c=1;
			return false;
		}
		if($("#validate").val()!=i){
			prompt("验证码不正确");
			c=1;
			return false;
		}else{
			c=0;
		}
        //密码
        var confirmPassword = document.getElementById("password").value;
        var password = document.getElementById("password1").value;
        if(confirmPassword.length<6){
			prompt("密码长度不安全");
        	return false;
        }
        if(confirmPassword == ''){
			prompt("请输入密码！");
            return false;
        }else if(confirmPassword != password){
			prompt("二次密码输入不一致！");
            return false;
        }
       var url =  basePath+"ea/mycenter/sajax_ea_updateLoginPsw.jspa";

		if(pswtype=="交易"){
			url =  basePath+"ea/mycenter/sajax_ea_updatePayPsw.jspa";
		}
		$.ajax({
			type: "POST",
			url:url,
			dataType: "json",
			data: {
				newpsw:$.trim(password)

			},
			success: function (data) {

				var m = eval("("+data+")");
				var result = m.re;
				 if (result=="0") {

					prompt("密码设置成功");
					document.myform.reset();
					 $(".huoquyzm").css("cursor","pointer");
					 $(".huoquyzm").css("backgroundColor","#F74C31");
					 $(".huoquyzm").css("color","#FFF");
					 $(".huoquyzm").val("获取验证码");
					 times=59;
					 clearTimeout(t);
					 $("#tel").val(account);
				}
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
	}, 3000);
}