/**
 * 个人购买
 */
$(document).ready(function(e) {
            $("body").css("height",$(window).height()) ;
			$(".wfj11_008_top").css("height",$(window).height()*0.05+"px");
			
			$("#ul").find("li").eq(0).attr("style","width:15%; text-align:center; font-size:"+$(window).height()*0.03+"px;");
			$("#ul").find("li").eq(1).attr("style","width:70%; text-align:center; font-size:"+$(window).height()*0.02+"px;");
			$("#ul").find("li").eq(2).attr("style","width:15%; text-align:center;");
			$(".wfj11_008_name").find("table").find("tr").eq(0).find("td").eq(1).attr("style","color:#000; font-size:"+$(window).height()*0.02+"px;");
			$(".wfj11_008_name").find("table").find("tr").eq(1).find("td").eq(0).attr("style","color:#F74C31; font-size:"+$(window).height()*0.02+"px;");
			$(".wfj11_008_content").find("table").find("tr").attr("style","line-height:"+$(window).height()*0.06+"px;height:"+$(window).height()*0.06+"px;");
			$(".wfj11_008_content").find("table").find("tr").find("td").attr("style","font-size:"+$(window).height()*0.02+"px;");
			$(".wfj11_008_content").find("table").find("tr").find("td").find("input").attr("style"," height:"+$(window).height()*0.06+"px; width:100%; border:none; font-size:"+$(window).height()*0.02+"px;");
			$(".wfj11_008_content").find("table").find("tr").find("td").find("select").attr("style"," height:"+$(window).height()*0.06+"px; width:100%; border:none; font-size:"+$(window).height()*0.02+"px;");
			$(".wfj11_008_content").find("table").find("tr").find("td").find("select").find("option").attr("style"," height:"+$(window).height()*0.06+"px; width:75%;font-size:"+$(window).height()*0.02+"px;");
			//alert($(".wfj11_008_content").find("table").find("tr").find("td").find("select").find("option").width())
			
			$(".wfj11_008_buy").find("div").attr("style"," width:50%; height:"+$(window).height()*0.06+"px; line-height:"+$(window).height()*0.06+"px; background-color:#F74C31; border-radius:"+$(window).height()*0.01+"px; color:#FFF; text-align:center; font-size:"+$(window).height()*0.03+"px; font-weight:bold; margin:0 auto; cursor:pointer;");
			$(".wfj11_008_hidden_buy").find("table").find("tr").attr("style"," height:"+$(window).height()*0.06+"px; width:100%; border:none; font-size:"+$(window).height()*0.02+"px;");
			$(".wfj11_008_hidden_buy").find("table").find("tr").find("td").eq(0).find("span").attr("style"," font-size:"+$(window).height()*0.015+"px;");
			
			$(".wfj11_008_bottom_commit").attr("style"," height:"+$(window).height()*0.06+"px; background-color:#FFF; width:100%; padding-top:"+$(window).height()*0.01+"px;");
			$(".wfj11_008_bottom_commit").find("div").attr("style"," width:50%; height:"+$(window).height()*0.08+"px; line-height:"+$(window).height()*0.06+"px; background-color:#F74C31; border-radius:"+$(window).height()*0.01+"px; color:#FFF; text-align:center; font-size:"+$(window).height()*0.03+"px; font-weight:bold; margin:0 auto; cursor:pointer;");
			
			$(".wfj11_008_buy_width").find("td").attr("style"," height:"+$(window).height()*0.06+"px;font-size:"+$(window).height()*0.02+"px;");
			$(".wfj11_008_pay").find("img").attr("style"," height:"+$(window).height()*0.04+"px; width:auto; vertical-align:middle;");
			$(".wfj11_008_choice").find("img").attr("style"," height:"+$(window).height()*0.03+"px; width:auto; vertical-align:middle;");
			$("#money").attr("style","color:#F74C31;");
			$("#money").css("color","#F74C31");
			
			
			$("#pays").find("span").eq(0).attr("style","color:#000; font-size:"+$(window).height()*0.025+"px;");
			$("#pays").find("span").eq(1).attr("style","color:#F74C31; font-size:"+$(window).height()*0.03+"px;");
			
			
			$("#ul").find("li").eq(2).find("img").attr("style","max-height:"+$(window).height()*0.03+"px; width:auto;");
			$(".wfj11_008_top").css("lineHeight",$(window).height()*0.05+"px");
			$(".wfj11_008_name").css("height",$(window).height()*0.155+"px");
			
			
			$(".wfj11_008_choice").find("img").click(function(){
				$(".wfj11_008_choice").find("img").attr("src",basePath+"images/WFJClient/PersonalJoining/choice_02.png");
				$(this).attr("src",basePath+"images/WFJClient/PersonalJoining/choice_01.png");
				
			});
			
			
			
			$(".wfj11_008_buy").find("div").click(function(){
				if(ti()){
					$(".wfj11_008_buy").css("display","none");
					$("#occlusion2").css("z-index",$(".wfj11_008").css("z-index")+1);
					$("#occlusion2").jqmShow();
					$(".wfj11_008_hidden_buy").css("z-index",$("#occlusion2").css("z-index")+1);
					$(".wfj11_008_hidden_buy").fadeIn(1000);
	
				}
			});
			$(".jqmOverlay").live("click",function(){
				$(".wfj11_008_hidden_buy").fadeOut();
				$("#occlusion2").jqmHide();
				$(".wfj11_008_buy").css("display","");
			});
			
			//弹出层初始化
			$(".jqmWindow").jqm({
				modal : true, 
				overlay : 20
			}).jqmAddClose('.close');
			
			
			var   pahe=basePath+"/ea/wfjshop/sajax_ea_getgrlist.jspa?sort="+where;
				 $.ajax({
					 type: "GET",
					 url: pahe,
					 dataType: "json",
					 success: function(data){  
					        var member = eval("(" + data + ")");      		
					        for(var i=0;i<=member.list.length;i++){
					          $("#dl").append("<option  style='width:75%; overflow:hidden;' value='"+member.list[i].ID+"'>"+member.list[i].name+"</option>");
					        	}
					    	}
				});
	});

function ti(){
	  if($("#dl").val()==0)
     {
    	 alert("请选择上级");
       return false;
     }

   if(!CheckMail($("#comppahe1").val()))
     {
     	alert("您的电子邮件格式不正确!");
      return false;
     } 
     if(!isnull())
     {
     alert("信息不完整");
          return false;
     } 
      $.ajax({
    	  cache : true,  		
    	  type :"POST",
    	  url : basePath+"ea/wfjshop/sajax_ea_grgm.jspa",
    	  data : $("#formsutm").serialize(),
    	  async :false,
    	  dataType : "json",
    	  success :function(data){
    		  var member = eval("(" + data + ")");
    		  $("#ddid").val(member.ddid);
    	  }
      });
      return true; 
}
 //电子邮箱验证
function CheckMail(mail) {

	 var filter  = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	 if (filter.test(mail)) 
	 {		
		 return true;
	 }
	 else {
	 
	     return false;
			}
}
function  isnull(){

 var  s=true;
  var allInputs = document.getElementsByTagName('input');
  for (var i = 0; i < allInputs.length; i++) {
	if (allInputs[i].type === 'text') {
		 if(allInputs[i].value==''){
		 		s=false;
				break
		 }			    
	}
  }
  return s;
}
function zfb(){
	var val=$("#code").val();
	var orid=$("#ddid").val();
    if(val==null){
        alert("请选择支付方式");
        return false;
    } 
    else{  
      if(val=='1'){
      
			window.location.href=  encodeURI(basePath+"page/WFJClient/GoodsShow/wfjAlipay.jsp?WIDout_trade_no="+orid+"&WIDtotal_fee="+mort+"&WIDsubject=(北京天太世统科技有限责任公司)"+orname+"&WIDbody=''&WIDit_b_pay=''&WIDextern_token=''");
		  }
		  else if(val=='2'){
			  /*alert(mort);
  		$("#formsutm").attr("action",basePath + "/ea/wfjshop/ea_zfgs.jspa?ddid="+orid+"&baseUrl="+basePath+"&morre="+mort+"&code="+val);
			$("#submit").click();*/
			  alert("银联支付暂时无法使用");
		    	return false;
	 	 }
    }
}
