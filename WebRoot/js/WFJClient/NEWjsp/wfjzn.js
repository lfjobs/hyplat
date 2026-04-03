	$("#download").click(function(){
		document.location.href = basePath+"page/mobile/zcok.jsp";
	});
	$("#zc2").click(function(e){
		e.stopPropagation(); 
		check();
	});
//判断手机号是否注册
	function isshouji(){
		var count=$("#tel").val();
		if(count!=''){
			$.ajax({
		    	  cache : true, 
		    	  type :"POST",
		    	  url : basePath+"/ea/android/sajax_ea_isacounnt.jspa?pahe="+count,
		    	  async :false,
		    	  dataType : "json",
		    	  success :function(data){
		    	   	var member = eval("(" + data + ")");
		    		if(member.result==0){
		    	   		 d=1;
		    		}
		    		else{
		    		   alert("已被注册,请更换手机号码！");
		    		   $("#tel").val("");
		    		   $("#tel").focus();
		    		   d=2;
		    		   return;
		    		}	
		    	 }	    	   
			}); 
		}else{
			$("#tel").val("");
		}
	}
	//发送验证短信
	function duanxin(){
		//手机
        var x=document.getElementById("tel").value;
		var count=$("#tel").val();
      	if(times<59){
			alert("请稍后");	
			return;
		}
      	var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
      	if(count==""){
      		alert("请输入电话号！");
      		return;
      	}else if(!myreg.test(count)){
            alert("请输入正确格式电话号！");
            return false;
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
    			var member = eval("(" + data + ")");
    			i = member.returna;
    	 	}	    	  
	   	});
	}
	function update(num) {
		if(num>0){
			$("#validate").val("已发送（"+num+"）");
			$("#validate").css("cursor","not-allowed");
			$("#validate").css("backgroundColor","#999");
			$("#validate").css("color","#FFF");
	        times = num;
			setTimeout(function(){
					update(num-1);
			},1000);
		}else{
			$("#validate").css("cursor","pointer");
			$("#validate").css("backgroundColor","#F74C31");
			$("#validate").css("color","#FFF");
			$("#validate").val("获取验证码");
			times=59;
		}
	}
	
	function yanz(){
	   	if(q==0){
	   		alert("请先获取验证码");
	        return;
       	}
       	if($("#valnum").val().length<6){
       		alert("验证码不能小于六位数");
           	return;
       	}
       	if($("#valnum").val()==''){
			alert("请填写验证码");
			c=1;
			return;
		}
		if($("#valnum").val()!=i){
			alert("验证码不正确");
			c=1;  
			return;    			     	
 		}else{
 			c=0;
 		}
	}
	 
    function check() {
    	if(ss==1){
    		ss=0;
    		//姓名
            var name=document.getElementById("name").value;
            if(name == ""){
                alert("姓名不能为空！");
                return false;
            }else{

            }
            if(c==1){
    			alert("请先手机验证");
     			return false;
     		}
     		if(d==1){
     			if(c==1){
         			alert("请先手机验证");
     				return false;
     			}
     		}
            //密码
            var confirmPassword = document.getElementById("password").value;
            var patrn=/^\d+$/;
            var password = document.getElementById("password2").value;
            var flag = false;
            var message = "";
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
            $("#myform").attr("action",basePath+"/ea/wfjlogin/ea_seves.jspa");
    		$("#myform").submit();
    	}
    }