
$(document).ready(function() {
	var ok1=false;
	var ok2=false;
	var ok3=false;
	var ok4=false;
	var ok5=false;
	var ok6=false;
	var ok7=false;
	var ok8=false;
	var ok9=false;
	var ok10=false;
	
	//帐号
	$(".account").focus(function(){
        $("#aa1").text("请输入6位以上的帐号！");
    }).blur(function(){
        if($(this).val()=="" ||$(this).val().indexOf(' ') != -1 || $(this).val().length<6){
      	  $("#aa").text('请输入6位以上不带空格的帐号！');
      	  return;
        }else if ($(this).val() != "" && $(this).val().length > 5) {
				var url = basePath + "ea/wfj/sajax_ea_isRegister.jspa?parameter="
						+ $(this).val() + "&date=" + new Date();
				$.ajax({
					url : encodeURI(url),
					type : "get",
					async : false,
					dataType : "json",
					success : function cbf(data) {
						var member = eval("(" + data + ")");
						if (member.isR) {
							$("#aa").text("帐号已存在！");
							return;
						}else{
							ok1=true;
							$("#aa").text("");
						}
					},
					error : function cbf(data) {
						alert("数据获取失败！");
					}
				});
				return;
			}
    });
	
	//验证密码
    $(".password").focus(function(){
  	  $("#aa1").text('密码应该为6-20位之间 ,不能有特殊符号');
    }).blur(function(){
  	   var reg =/(\w){6,20}$/;  
	  	if(!reg.test($(this).val()))
	  	{  
	  		$("#aa").text('不能有特殊符号');
	  	}
	  	if($(this).val().length >= 6 && $(this).val().length <=20 && $(this).val()!=''){
	        ok2=true;
            $("#aa").text("");
        }
        else{
      	  $("#aa").text('密码应该为6-20位之间');
        }
        
    });
	//第二次密码
    $(".password1").focus(function(){
  	  $("#aa1").text('请输入6-10位之间的确认密码！');
    }).blur(function(){
			b = $(".password").val();
			if ($(".password1").val() == "" || !($(".password1").val() == b)) {
				$("#aa").text("两次密码不相同！");
			}else{
				 ok3=true;
	             $("#aa").text("");
			}
    });
		
	//公司名称
      $(".organizationName").focus(function(){
    	  $("#aa1").text('请填写店铺名称');
      }).blur(function(){
          if( $(this).val()!=''){
              ok4=true;
              $("#aa").text("");
          }else{
        	  $("#aa").text('请填写店铺名称');
          }
          
      }); 
    //支付账号
      $(".payaccount").focus(function(){
    	  $("#aa1").text('请输入正确的支付账号！');
      }).blur(function(){
              $("#aa").text("");
      }); 
     //负责人
      $(".owner").focus(function(){
    	  $("#aa1").text('请输入店铺负责人！');
      }).blur(function(){
    	  if ( $(this).val() != ""){
			  ok5=true;
              $("#aa").text("");
    	  }else{
        	  $("#aa").text('请输入店铺负责人！');
          }
      }); 
    //电子邮箱
      $(".qq").focus(function(){
    	  $("#aa1").text('请输入正确qq！');
      }).blur(function(){
    	  reg =/^\d{5,12}$/;
		  if ( $(this).val() != "" && reg.test($(this).val())) {
			  ok6=true;
              $("#aa").text("");
          }else{
        	  $("#aa").text('请输入正确qq！');
          }	
      }); 
    //电话
      $(".telephone").focus(function(){
    	  $("#aa1").text('请输入正确电话！');
      }).blur(function(){
    	  reg = /^[0][0-9]{2,3}-[0-9]{5,8}$/;
    	  reg1 = /^1\d{10}$/;
		  if ( $(this).val() != "" && reg.test($(this).val()) || reg1.test($(this).val())) {
			  ok7=true;
              $("#aa").text("");
          }else{
        	  $("#aa").text('请输入正确电话！');
          }	
      }); 
		// 判断验证码
      $(".validate").focus(function(){
    	  $("#aa1").text("");
      }).blur(function(){
			if ($(this).val() == "") {
				$("#aa").text("验证码不能为空！");
				return;
			}
			var url1 = basePath + "ajax_register_validate.jspa?parameter="
					+ encodeURI($(this).val()) + "&date3=" + new Date();
			$.ajax({
				url : url1,
				type : "get",
				async : false,
				dataType : "json",
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					if (member.success) {
						$("#aa").text("验证码错误！");
						return;
					}else{
						ok8=true;
			            $("#aa").text("");
					}
				},
				error : function cbf(data) {
					alert("数据获取失败！");
				}
			});

			return;
		});	
	$("#reg").click(function () {
		  if(ok1 && ok3 && ok4 && ok5 && ok6 && ok7 && ok8){
			  $("#dianpu").attr("action", basePath+"/ea/wfj/ea_shopReg.jspa?");
			  document.dianpu.submit();	
			  /*$("#dianpu").attr("action",basePath+"/ea/wfj/ea_shopReg.jspa?");
			  $("#dianpu").submit();*/
          }else{
        	  $("#aa").text("您输入的信息有误！");
          }
		});
	
});
function re_load() {
	if (token)
		document.location.href = basePath + "ea/wfjshop/ea_getWFJshops.jspa";
}