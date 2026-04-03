$(document).ready(function(){
	var telnotmp="";
		
		$("form :input").blur(function() {
						
			var $parent = $(this).parent();
			$parent.find(".addd").remove();
		
		     if ($(this).is("#telno")) {
				var t = this.value;
			
				if (t == "") {
					var error = "不能为空";
					$parent.append('<span class="addd">' + error + '</span>');
					 return ;
				} else if (!(valid.isTelno(t)||valid.isMobil(t))) {
					 var error = "输入电话号码"
					$parent.append('<span class="addd">' + error + '</span>');
					 return ;
				}
				
				if(telnotmp==t||t==""){
					return ;
				}else{
					telnotmp=t;					
				}
				
				$.ajax({
					url:basePath+"clientQueryCustomer.do?telno="+this.value+"&issave=no",
					type: "get",
					async: true,
					dataType: "json",
					success: function cbf(data){
					
						if(data.type==""){
							alert("没有该客户的信息！请填写完毕再添加通话记录！");
							history.back();
						}
						if(data.type=="company"){
							$("#customer_id").val(data.companyName);
							$("#customer_span").text(data.ccompanyID);
							$("#customer_name").val(data.ccompanyID);		
						}
						if(data.type=="staff")
							$("#customer_id").val(data.staffID);
							$("#customer_span").text(data.staffName);
							$("#customer_name").val(data.staffName);			
					},
					error:function(data){
						alert(data);
					}
				});
		    }
		
			if($(this).is("#in_or_out")){
				var t = this.value;
			 	if (t == "") {
					var error = "不能为空";
					$parent.append('<span class="addd">' + error + '</span>');
				}
			}
			
			if($(this).is("#start_time")){
				var t = this.value;
				if (t == "") {
					var error = "不能为空";
					$parent.append('<span class="addd">' + error + '</span>');
				}
			}
			if($(this).is("#end_time")){
				var t = this.value;
				if (t == "") {
					var error = "不能为空";
					$parent.append('<span class="addd">' + error + '</span>');
				}
			}
			if($(this).is("#content")){
				var t = this.value;
				if (t == "") {
					var error = "不能为空";
					$parent.append('<span class="addd">' + error + '</span>');
				}
			}
		});
		$("#save").click(function(){
			$("form :input").trigger("blur");
			if ($("form .addd").length)return;
			$("form").submit();
		});
})
