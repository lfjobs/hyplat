$(document).ready(function() {
	$("input[type='button']").css({ color: "#000", background: "#ccc" });
	$(".jqmWindow").jqm({
                    modal: true,// 限制输入（鼠标点击，按键）的对话  
                    overlay: 20 // 遮罩程度%  
                }).jqmAddClose('.close');// 添加触发关闭的selector  
				//.jqDrag('.drag');// 添加拖拽的selector
	    $("#flex1").flexigrid   
       (   
       {   
      	//url: '<%=basePath%>telrecjson/WebQueryTelByCustomerId.do?customerId=<%=id%>',
		url:encodeURI( basePath+"telrecjson/WebQueryTelByCustomerId.do?t=t&customer_name="+encodeURI(customer_name)+"&user_name="+encodeURI(user_name)+"&startdate="+startdate),   
        dataType: 'json',
		colModel : [
			{ display : '用户', name : 'user_name',width : 100,align : 'center'},
         	{ display : '客户', name : 'customer_name',width : 100,align : 'center'},
         	{ display : '开始时间', name : 'start_time',width : 160,align : 'center'},
         	{ display : '结束时间', name : 'end_time',width : 160,align : 'center'},
         	{ display : '内容', name : 'content',width : 400,align : 'center'},
         	{ display : '电话号码', name : 'telno',width : 120,align : 'center'},
         	{ display : '呼入/呼出', name : 'in_or_out',width : 100,align : 'center'}
         ],
		buttons : [   
             		{name: '播放', bclass: 'playWav', onpress : button},                  
             		{separator: true}   ,
             		//{name: '添加记录', bclass: 'insert', onpress : button},
             		{name: '查询', bclass: 'mysearch', onpress : button}
             	  ],
		        usepager : true,
		        title : '电话记录列表',
		        useRp : true,
		        checkbox : true,// 是否要多选框
		        rowId : 'path',// 多选框绑定行的id
		        rp : 10,
		        showTableToggleBtn : true,
		        width : 'auto',
		        height : 300 
        }   
        );
        function button(com,grid)   
        {   
      
        if(com=="播放"){
                if($(".trSelected").length==1){   
                   var url=basePath+"page/ea/main/telrec/playwav.jsp?wavpath="+$('.trSelected input').val();
					window.showModalDialog(url,"dialogWidth=500px;dialogHeight=500px");
                }else if($(".trSelected").length>1){   
                    alert("请选择一条语音信息,不能同时选择多个");   
                }else if($(".trSelected").length==0){   
                    alert("请选择一条您要播放的语音");   
                }
            }
            //if(com=="添加记录"){
			//	document.location.href=basePath+"page/ea/main/telrec/insertTelrecInfo.jsp";
            //}
            if(com=="查询"){
            	$("#jqModelSearch").jqmShow();
            }   
        }
		//获取文本框值加载telrecStep2.jsp
		$("#queryInfo").click(function(){
			var customer_name = $('#customer').val();
			var user_name = $('#user').val();
			var startdate = $('#startdate').val();
			alert(basePath+"page/ea/main/telrec/telrecStep2.jsp?customer_name="+escape(customer_name)+"&user_name="+escape(user_name)+"&startdate="+startdate);
			document.location=basePath+"page/ea/main/telrec/telrecStep2.jsp?customer_name="+customer_name+"&user_name="+user_name+"&startdate="+startdate;
		
	    });
		//关闭jqmWindowDIV时清空文本框
		$(".close").click(function(){ 
           $('#customer').val("");
		   $('#user').val("");
		   $('#startdate').val("");
       });
	   //用ajax方式查询所有用户名,动态给SearchUserName下拉框赋值,显示showUserNameDIV
	   $("#showUserName").click(function(){
	   		$.ajax({
				type: "POST",
				url: basePath + "telrecjson/WebQueryTelForUser.do",
				async: true,
				dataType: "json",
				success: function(data){
					if ($("#SearchUserName").children().length == 0) {
						for (var i = 0; i < $(data.earTag).length; i++) {
							$("<option>" + $(data.earTag)[i] + "</option>").appendTo("#SearchUserName");
						}
					}
				}
			});	
		  $("#UserName").show();
	   });
	   //用ajax方式查询所有客服名,动态给SearchCustomerName下拉框赋值,显示showCustomerNameDIV	
	   $("#showCustomerName").click(function(){
	   		$.ajax({
			type: "POST",
			url: basePath+"telrecjson/WebQueryTelForCustomer.do",
			async: true,
			dataType: "json",
			success: function(data){
			if ($("#SearchCustomerName").children().length == 0) {
				for (var i = 0; i < $(data.earTag).length; i++) {
					$("<option>" + $(data.earTag)[i] + "</option>").appendTo("#SearchCustomerName");
				}
			}
			}
		});
	   	  $("#CustomerName").show();
	   });
	   //鼠标离开showUserNameDIV隐藏showUserNameDIV
	   $("#UserName").mouseleave(function(){
	   	  $("#UserName").hide();
	   });
	   //鼠标离开showCustomerNameDIV隐藏showCustomerNameDIV
	   $("#CustomerName").mouseleave(function(){
	   	  $("#CustomerName").hide();
	   });
	   //鼠标点击SearchUserName下拉框给user_name文本框赋值
	   $("#SearchUserName").click(function(){
		  if(null!=$("#SearchUserName").val())
		  {
		  	$("#user").val($("#SearchUserName").val());
		  }
	   });
	   //鼠标点击showCustomerName下拉框给customer_name文本框赋值
	   $("#SearchCustomerName").click(function(){
	   	  if (null != $("#SearchCustomerName").val()) 
		  {
		  	$("#customer").val($("#SearchCustomerName").val());
		  }	   
	   });
});
