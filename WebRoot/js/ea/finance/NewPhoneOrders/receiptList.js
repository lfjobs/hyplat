var isAndroid;
var isiOS;
$(document).ready(function(){		
 		var u = navigator.userAgent;
 		isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
 		isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
 		
 		if(state=='11'){
 	        $(".successful").addClass("active");
 	        $(".appraise").removeClass("active");
 	        $("#successful").css("display","block").siblings().css("display","none");
 	    }
 		
 		loaded();
	
        $(".header ul li").css("line-height",$(window).height()*0.08+"px");
        $(".header").css("height",$(window).height()*0.08+"px");
        $(".content_hidden").css("height",$(window).height()*0.92-$(".header").height()+"px");
        $(".content").css("height",$(window).height()*0.92-$(".header").height()+"px");

        $(".header .rec_head li").click(function(){
            $(this).addClass("active").siblings().removeClass("active");
        });
      
        $(".appraise").click(function(){
        	$("#appraise").empty();
        	pagenumber=0;
        	state="00";
        	loaded();
            $("#appraise").css("display","block").siblings().css("display","none");
        });
        $(".successful").click(function(){
        	$("#successful").empty();
        	pagenumber=0;
        	state="11";
        	loaded();
            $("#successful").css("display","block").siblings().css("display","none");
        });
        
        /*搜索*/
        $(".header ul li:nth-child(3) img").click(function(){
            $(".alert").show();
            $(".alert_search").show();
        });
        $(".alert_search input:nth-child(1)").keyup(function () {
            var t = $(".alert_search input:nth-child(1)");
            if (t.val() == "") {
                $(".alert_search .top #qx").show();
                $(".alert_search .top #ss").hide();
            }
            else{
                $(".alert_search .top #ss").show();
                $(".alert_search .top #qx").hide();
            }
        });
        $("#qx").click(function(){
        	$(".alert").hide();
            $(".alert_search").hide();
        })
        $("#ss").click(function(){
        	pagenumber=0;
        	search=$(".sousuo").val();
        	$("#appraise").empty();
        	$("#successful").empty();
        	if(search==''){
        		alert("请输入订单号或产品名称");
        	}else{
        		ajax($.trim(search));
        		$(".alert").hide();
                $(".alert_search").hide();
        	}       	
        });
        
 })//加载完毕
 
  function getHeight(){
	 t=setTimeout("getHeight()",200);
	 if($(".last").length>0){
		 if($(".last").offset().top+$(".last").height()-$(".header").height()*4<$(window).height()){
			 if(pagenumber<pagecount){
				 loaded();
			 }
		}		 
	 }
 }
 function loaded(){	 
	 ajax($.trim(search));
}
 function ajax(s){
	 clearTimeout(t);
	 pagenumber++;
	 var url=basePath+"ea/pobuy/sajax_AjaxReceipt.jspa?";
	 $.ajax({
		url : url,
		type: "get",
		async　 :false,
		dataType : "json",
		data:{		
			"state":state,
			"user": user,
			"pageForm.pageNumber":pagenumber,
			"search":s
		},
		success : function cbf(data){
			var member=eval("("+data+")");
			var pageForm=member.pageForm;
			var gmp=member.gmp;
			var ptmp=member.ptmp;
			var goodslist;
			var ptgoodslist;
			var str=new Array();
			if(pageForm!=null&&pageForm.recordCount>0){
				pagenumber=pageForm.pageNumber;
				pagecount=pageForm.pageCount;
				var cashlist=pageForm.list;
				$(".last").removeClass("last");
				for(var i=0;i<cashlist.length;i++){
					var cash=cashlist[i];
					if(i==cashlist.length-1){
						str.push("<div class='grd last' id='"+cash[0]+"'>");
					}else{
						str.push("<div class='grd' id='"+cash[0]+"'>");
					}
					str.push("<input id='jnum' type='hidden' value='"+cash[3]+"'>");
					str.push("<ul class='com_name'>");
	                str.push("<li><div class='head'><img src='"+basePath+cash[9]+"'></div></li>");
	                str.push("<li>"+cash[2]+"<a href='#;'><img src='"+basePath+"images/ea/finance/NewPhoneOrders/rigth.png' alt=''></a></li>");
	                if(cash[4]=='00'){
	                	str.push("<li><span>待评价</span></li></ul>");	
	                }else if(cash[4]=='11'){
	                	str.push("<li><span>交易成功</span></li></ul>");	
	                }
	                goodslist=gmp[cash[0]];
	                ptgoodslist=ptmp[cash[0]];
	                
	                for(var j=0;j<goodslist.length;j++){
	                	var goodbill=goodslist[j];
	                    str.push("<div class='mil'><a href='javascript:;' onclick='xq(\""+cash[0]+"\")'>");
		                str.push("<div class='left'><img src='"+basePath+goodbill[0]+"' alt=''>");
                        if (goodbill[7] != null && goodbill[7] != "" && goodbill[7] == "1") {//批发

                        } else if (goodbill[7] != null && goodbill[7] != "" && goodbill[7] == "2") {//VIP
                            str.push("<span class='sp vip'><i></i></span>")

                        } else if (goodbill[7] != null && goodbill[7] != "" && goodbill[7] == "3") {//普通活动
                            str.push("<span class='sp cx'><i></i></span>")

                        } else if (goodbill[7] != null && goodbill[7] != "" && goodbill[7] == "4") {//特价活动
                            str.push("<span class='sp tj'><i></i></span>")

                        } else {//零售

                        }
                        str.push("</div>");
		                str.push("<input id='ppid' type='hidden' value='"+goodbill[5]+"'/>");
		                str.push("<div class='right'>");
		                str.push("<h3>"+goodbill[1]+"</h3>");
		                str.push("<h3>产品规格："+(goodbill[4]==null?"":goodbill[4])+"</span></h3>");
		                str.push("<h3></h3></div>");
		                str.push("<div class='right right2'>");
		                str.push("<h3>&yen;<span>"+goodbill[2]+"</span></h3>");
		                str.push("<p>x"+goodbill[3]+"</p></div></div></a>")
		            }	                
	                if(ptgoodslist!=null&&ptgoodslist.length>0){
	                	for(var x=0;x<ptgoodslist.length;x++){
	                		var ptgb=ptgoodslist[x];
	                		str.push("<div class='mil'><a href='#'>");
			                str.push("<div class='left'><img src='"+basePath+ptgb[0]+"' alt=''></div>");
			                str.push("<div class='right'>");
			                str.push("<h3>"+ptgb[1]+"</h3>");
			                str.push("<h3>产品规格："+(ptgb[5]==null?"":ptgb[5])+"</span></h3>");
			                str.push("<h3></h3></div>");
			                str.push("<div class='right right2'>");			                
			                str.push("<p>x1</p></div></a><img src='"+basePath+"images/ea/finance/NewPhoneOrders/ico-cu.png' class='cu'></div>")			                
	                	}	                	
	                }
	                str.push("<ul class='com_total'>");
	                if(isAndroid==true){
		                str.push("<li><p>订单号：<span>"+cash[3]+"</span><input class='copy_btn' type='button' value='复制' onclick='Androidcopy(this)'/></p></li>");
	                }else if(isiOS==true){
		                str.push("<li><p>订单号：<span>"+cash[3]+"</span><input class='copy_btn' type='button' value='复制' onclick='IOSCopy(this)'/></p></li>");
	                }
	                str.push("<li><p>共计<span>"+goodslist.length+"</span>件商品 合计：&yen;<span>"+cash[6]+"</span>（含运费&yen;<span></span>）</p></li></ul>")
	                str.push("<div class='com_btn com_btn2' align='right'>");				
					/*str.push("<a href='#;'><input type='button' value='删除订单' onclick='del(\""+cash[0]+"\",\""+cash[4]+"\")'></a>");20180306*/
					if(cash[4]=='00'){
	                	str.push("<a href='#;'><input type='button' value='评价' onclick='assess(\""+cash[0]+"\")'></a>");
	                }
					str.push("</div></div>");
				}
			}else{
				   //<!--无货显示-->
	            str.push("<div class='no'>");
	            str.push("<img src='"+basePath+"images/ea/finance/NewPhoneOrders/wu.png' alt='' class='wu'>");
	            str.push("<p>目前还没有订单哦～</p></div>");                
			}
			if(state=="00"){
				$("#appraise").append(str.join(""));
				$("#appraise").css("display","block").siblings().css("display","none");
				$("#appraise").addClass("active");
			}else if(state=='11'){
				$("#successful").append(str.join(""));
				$("#successful").css("display","block").siblings().css("display","none");
				$("#successful").addClass("active");
			}
			getHeight();
		}
	 });
 }
 function del(id,sta){
 	window.location.href=basePath+"ea/pobuy/ea_delBill.jspa?cbid="+id+"&state="+sta+"&flag=sh&user="+user;
 }
 //订单详情
 function xq(cashid){
		document.location.href=basePath+"/ea/pobuy/ea_getCashBill.jspa?cbid="+cashid+"&user="+user+"&state="+state+"&flag=sh";
	}
 //复制
 function IOSCopy(obj){
 	 var content=$(obj).prev('span').text();
 	 var url= "func=" + 'iosstick';                
     params={'content':content};
     for(var i in params){
      url = url + "&" + i + "=" + params[i];
     }   
     alert("复制成功");
     window.webkit.messageHandlers.Native.postMessage(url); 
 }
 function Androidcopy(obj){
 	var content=$(obj).prev('span').text();
 	Android.callcopy(content);
 }
 function assess(cbid){
	 window.location.href=basePath+"/ea/pobuy/ea_Assess.jspa?cbid="+cbid+"&user="+user;
 }
 