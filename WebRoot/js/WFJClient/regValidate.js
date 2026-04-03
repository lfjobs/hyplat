
$(document).ready(function() {
	var ok1=false;
	var ok2=false;
	var ok3=false;
	var ok4=false;
	var ok5=false;
	var ok6=false;
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
		
	//姓名staffName
      $(".staffName").focus(function(){
    	  $("#aa1").text('请输入您的中文姓名！');
      }).blur(function(){
    	  var reg =/^[\u4e00-\u9fa5]{2,64}$/
          if($(this).val().length >0 && $(this).val().length <5 && $(this).val()!='' && reg.test($(this).val())){
              ok4=true;
              $("#aa").text("");
          }else{
        	  $("#aa").text('请输入您的中文姓名！');
          }
          
      }); 
	//省份证验证
      $(".staffIdentityCard").focus(function(){
    	  $("#aa1").text('请输入正确的身份证号码！');
      }).blur(function(){
    	  reg = /^(\d{15}$|^\d{18}$|^\d{17}(\d|X|x))$/;
		  if ($(this).val() != "" && reg.test($(this).val())) {
			  ok5=true;
              $("#aa").text("");
          }else{
        	  $("#aa").text('请输入正确的身份证号码！');
          }	
      }); 
     //手机
      $(".cellphone").focus(function(){
    	  $("#aa1").text('请输入正确的手机号码！');
      }).blur(function(){
    	  reg = /^1\d{10}$/;
		  if ($(this).val() != "" && reg.test($(this).val())) {
			  ok5=true;
              $("#aa").text("");
          }else{
        	  $("#aa").text('请输入正确的手机号码！');
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
						 ok6=true;
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
		  var addr = "";
		  $(".JQueryaddress").find("select").find("option[value]:selected").each(function() {
			if ($(this).text() != '--请选择--' && $(this).text() != '--新增--')
				addr = addr + $(this).text();
				$("#address").val(addr);
		  });
		  if(ok1 && ok2 && ok3 && ok4 && ok5 && ok6 ){
			  if(addr==""){
				  $("#aa").text("请选择地址！");
				  return;
			  }
			$("topdl").val($("#dl").val());
			  if($("#dl").val()=="0")
				
				  {
				  		alert("请选择上级代理商");
				  		return;
				  
				  }
			  $("#submit").submit();
          }else{
        	  $("#aa").text("您输入的信息有误！");
          }
		});

	
});