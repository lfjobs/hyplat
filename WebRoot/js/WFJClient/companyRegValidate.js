
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
	
	//组织机构名
	$(".companyIdentifier").focus(function(){
        $("#aa1").text("请输入4到20个字符的组织机构名！");
    }).blur(function(){
        if ($(this).val() != "" && $(this).val().length > 3 && $(this).val().length <19 ) {
				var url = basePath + "ajax_register_isRegister.jspa?parameter="
						+ $(this).val() + "&date=" + new Date();
				$.ajax({
					url : encodeURI(url),
					type : "get",
					async : false,
					dataType : "json",
					success : function cbf(data) {
						var member = eval("(" + data + ")");
						if (member.isR) {
							$("#aa").text("组织机构名已存在！");
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
			}else{
				$("#aa").text('请输入4到20个字符且不带空格的组织机构名！');
		      	return;
			}
    });
	//用户名
	  $(".account").focus(function(){
          $("#aa1").text("请输入英文或者数字组成的2-15个字符用户名！");
      }).blur(function(){
          if($(this).val() ==""|| !$(this).val().match(/^[0-9A-Za-z_]{2,15}$/)||$(this).val().indexOf(' ') != -1){
        	  $("#aa").text('请输入英文或者数字组成的2-15个字符用户名！');
        	  return;
          }else if ($(this).val() != "" && $(this).val().match(/^[0-9A-Za-z_]{2,15}$/)) {
				var url = basePath + "ajax_register_isAccount.jspa?parameter="
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
							ok2=true;
							$("#aa").text("");
						}
					},
					error : function cbf(data) {
						alert("数据获取失败！");
					}
				});
				return;
			}else{
				 $("#aa").text('请输入英文或者数字组成的2-15个字符用户名！');
	        	 return;
			}
      });
	
	  //验证密码
      $(".password").focus(function(){
    	  $("#aa1").text('密码须由6-20个英文字母或数字组成');
      }).blur(function(){
    	  if($(this).val().length < 6 || $(this).val().indexOf(' ') != -1){
    		  $("#aa").text('密码须由6-20个英文字母或数字组成');
          }
          else{
        	  ok3=true;
              $("#aa").text("");
          }
          
      });
	//第二次密码
      $(".password1").focus(function(){
    	  $("#aa1").text('密码须由6-20个英文字母或数字组成');
      }).blur(function(){
			b = $(".password").val();
			if ($(".password1").val() == "" || !($(".password1").val() == b)) {
				$("#aa").text("两次密码不相同！");
			}else{
				 ok4=true;
	             $("#aa").text("");
			}
      });
		
	//公司名称
      $(".companyName").focus(function(){
    	  $("#aa1").text('注册企业填写工商局注册全称');
      }).blur(function(){
          if( $(this).val()!=''){
              ok5=true;
              $("#aa").text("");
          }else{
        	  $("#aa").text('注册企业填写工商局注册全称');
          }
          
      }); 
	//注册号
      $(".registrationNumber").focus(function(){
    	  $("#aa1").text('请输入正确15位数的注册号！');
      }).blur(function(){
    	  reg = /^\d{15}$/;
		  if ( reg.test($(this).val())) {
              $("#aa").text("");
          }else{
        	  $("#aa").text('请输入正确15位数的注册号！');
          }	
      }); 
    //开户行名称
      $(".bankNumber").focus(function(){
    	  $("#aa1").text('请输入正确的开户行名称！');
      }).blur(function(){
              $("#aa").text("");
      }); 
    //开户行账号
      $(".bankAccount").focus(function(){
    	  $("#aa1").text('请输入正确的开户行账号！');
      }).blur(function(){
              $("#aa").text("");
      }); 
    //公司地址
      $(".companyAddress").focus(function(){
    	  $("#aa1").text('请输入公司地址！');
      }).blur(function(){
    	  if ( $(this).val() != ""){
			  ok6=true;
              $("#aa").text("");
    	  }else{
        	  $("#aa").text('请输入公司地址！');
          }
      });
     //负责人
      $(".companyManager").focus(function(){
    	  $("#aa1").text('请输入公司负责人！');
      }).blur(function(){
    	  if ( $(this).val() != ""){
			  ok7=true;
              $("#aa").text("");
    	  }else{
        	  $("#aa").text('请输入负责人！');
          }
      }); 
    //电子邮箱
      $(".companyEmail").focus(function(){
    	  $("#aa1").text('请输入正确公司邮箱！');
      }).blur(function(){
    	  reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
		  if ( $(this).val() != "" && reg.test($(this).val())) {
			  ok8=true;
              $("#aa").text("");
          }else{
        	  $("#aa").text('请输入正确公司邮箱！');
          }	
      }); 
    //电话
      $(".companyPhone").focus(function(){
    	  $("#aa1").text('请输入正确电话！');
      }).blur(function(){
    	  reg = /^[0][0-9]{2,3}-[0-9]{5,8}$/;
    	  reg1 = /^1\d{10}$/;
		  if ( $(this).val() != "" && reg.test($(this).val()) || reg1.test($(this).val())) {
			  ok9=true;
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
						 ok10=true;
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
		  if(ok1 && ok02 && ok3 && ok4 && ok5 && ok6 && ok7 && ok8 && ok9 && ok10){
			  $("#gongsi").attr("action", basePath+"/ea/wfj/ea_companyReg.jspa?");
			  document.gongsi.submit();	
          }else{
        	  $("#aa").text("您输入的信息有误！");
          }
		});
	
});
function re_load() {
	if (token)
		document.location.href = basePath + "ea/wfjshop/ea_getWFJshops.jspa";
}