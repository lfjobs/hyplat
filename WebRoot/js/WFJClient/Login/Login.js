	//登陆
	$("#dl").click(function(){
		$("#myform").attr("action",basePath+"ea/wfj/ea_customerLogin.jspa?ccompanyId="+ccompanyId);
		$("#myform").submit();
	});
	
	//注册
	$("#zc").click(function(){
		/*if(sccid!=""){
	   	    document.location.href = basePath+"ea/wfjshop/ea_getjspzc.jspa?sccid="+sccid;
	   	}else{
	   	    if(ccompanyId!=""){
	   	    	document.location.href = basePath+"ea/wfjshop/ea_findWebUser.jspa?ccompanyId="+ccompanyId;
	   	    }else{
	   	    	document.location.href = basePath+"zhuceError.jsp";
	   	    }
	   	} */
        if(ccompanyId!=""){
            document.location.href = basePath+"ea/wfjshop/ea_findWebUser.jspa?ccompanyId="+ccompanyId;
        }else{
            document.location.href = basePath+"ea/wfjshop/ea_getjspzc.jspa?sccid="+sccid;
        }
	});
    function check(form) {
        //手机
        var x=document.getElementById("tel").value;
        var myreg = /^(((13[0-9]{1})|(14[0-9]{1})|(17[0-9]{1})|(15[0-3]{1})|(15[5-9]{1})|(18[0-3]{1})|(18[5-9]{1}))+\d{8})$/;
        if(x == ""){
            alert("请输入手机号！");
            return false;
        }else if(!myreg.test(x)){
            alert("请输入正确格式手机号！");
            return false;
        }
        //密码
        var y=document.getElementById("password").value;
        if(y == ""){
            alert("请输入密码！");
            return false;
        }
    }