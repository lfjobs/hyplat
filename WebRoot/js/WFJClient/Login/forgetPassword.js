//判断输入验证码
	function yanz(){
	   	if(q==0){
	   		alert("请先获取验证码");
	    	return;
	    }
	    if($("#validate").val().length<6){
	    	alert("验证码不能小于六位数");
	    	return;
	    }
	    if($("#validate").val()==''){
			alert("请填写验证码");
			c=1;
			return;
		}
		if($("#validate").val()!=i){
			alert("验证码不正确");
			c=1;  
			return;    			     	
		}else{
			c=0;
		}
	}
	function sut(){
		if(c==1){
			alert("请先手机验证");
     			return;
     		}
   		if($("#tel").val()=='请输入手机号'){
   			alert("请输入手机号");
   			$("#tel").focus();
   			return;
   		}
   		if(d==1){
   			if(c==1){
    			alert("请先手机验证");
   				return;
   			}
   		}
   		if($("#tel").val()==''){
			alert("账号为空");
			$("#tel").focus();
			return;
		}
		
     }
      	
     function isshouji(){
   		var count=$("#tel").val();
   		if(count!=''){
			$.ajax({
	   	  		cache : true, 
	   	  		type :"POST",
	   			url : basePath+"/ea/consignee/sajax_ea_isacounnt.jspa?cuscom.account="+count,
	   	  		async :false,
	   	  		dataType : "json",
	   	  		success :function(data){
		   	   		var member = eval("(" + data + ")");
		   			if(member.result==0){
			   			d=1;
		   	   		 	alert("该用户不存在");
		   	   	     	$("#tel").focus();
		   	         	$("#tel").val("");
		   			}else{
		   		   		d=2;
		   			}	
	   	 		}	    	   
	 		}); 
   		}
     }
     function duanxin(){
      	var count=$("#tel").val();
		if(times<59){
			alert("请稍后");	
			return;
		}
		if(count==""){
		    alert("手机号为空");
		    return;
		}
		var reg=/^\d{11}$/;
		if(!reg.test(count)){
		    alert("手机号码输入错误");
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
			setTimeout(function(){
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
        //手机
        var x=document.getElementById("tel").value;
        var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
        if(x == ""){
            alert("请输入电话号！");
            return false;
        }else if(!myreg.test(x)){
            alert("请输入正确格式电话号！");
            return false;
        } 
        //密码
        var confirmPassword = document.getElementById("password").value;
        var password = document.getElementById("password1").value;
        if(confirmPassword.length<6){
        	alert("密码长度不安全");
        	return false;
        }
        if(confirmPassword == ''){
            alert("请输入密码！");
            return false;
        }else if(confirmPassword != password){
            alert("二次密码输入不一致，请重新输入！");
            return false;
        }
        $("#myform").attr("action",basePath+"/ea/consignee/ea_editPassWord.jspa");
		$("#myform").submit();
    }); 