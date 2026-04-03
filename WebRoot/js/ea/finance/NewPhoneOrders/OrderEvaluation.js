/**
 * 
 */
 $(document).ready(function(){
        $(".header ul li").css("line-height",$(window).height()*0.08+"px");
        $(".header").css("height",$(window).height()*0.08+"px");
        $(".content_hidden").css("height",$(window).height()*0.92+"px");
        $(".content").css("height",$(window).height()*0.92-$(".evaluate_btn").height()+"px");

        //好评差评
        $(".options_ ul li").click(function(){
            $(this).addClass("active").siblings().removeClass("active");
            $("#evaluation").val($(this).find("p").text());
        });

        //店铺评分
        $(".evaluate .grade ul li .star img").click(function(){
            var image = $(this).parents(".star").find($(".evaluate .grade ul li .star img"));
            var index = image.index(this)+1;    //当前谁第几个元素
            $(this).parents(".star").find("input").val(index);
            if(index==1){
                image.eq(0).attr("src",basePath+"images/ea/finance/NewPhoneOrders/star_.png");
                image.eq(1).attr("src",basePath+"images/ea/finance/NewPhoneOrders/star.png");
                image.eq(2).attr("src",basePath+"images/ea/finance/NewPhoneOrders/star.png");
                image.eq(3).attr("src",basePath+"images/ea/finance/NewPhoneOrders/star.png");
                image.eq(4).attr("src",basePath+"images/ea/finance/NewPhoneOrders/star.png");
            }else if(index==2){
                image.eq(0).attr("src",basePath+"images/ea/finance/NewPhoneOrders/star_.png");
                image.eq(1).attr("src",basePath+"images/ea/finance/NewPhoneOrders/star_.png");
                image.eq(2).attr("src",basePath+"images/ea/finance/NewPhoneOrders/star.png");
                image.eq(3).attr("src",basePath+"images/ea/finance/NewPhoneOrders/star.png");
                image.eq(4).attr("src",basePath+"images/ea/finance/NewPhoneOrders/star.png");
            }else if(index==3){
                image.eq(0).attr("src",basePath+"images/ea/finance/NewPhoneOrders/star_.png");
                image.eq(1).attr("src",basePath+"images/ea/finance/NewPhoneOrders/star_.png");
                image.eq(2).attr("src",basePath+"images/ea/finance/NewPhoneOrders/star_.png");
                image.eq(3).attr("src",basePath+"images/ea/finance/NewPhoneOrders/star.png");
                image.eq(4).attr("src",basePath+"images/ea/finance/NewPhoneOrders/star.png");
            }else if(index==4){
                image.eq(0).attr("src",basePath+"images/ea/finance/NewPhoneOrders/star_.png");
                image.eq(1).attr("src",basePath+"images/ea/finance/NewPhoneOrders/star_.png");
                image.eq(2).attr("src",basePath+"images/ea/finance/NewPhoneOrders/star_.png");
                image.eq(3).attr("src",basePath+"images/ea/finance/NewPhoneOrders/star_.png");
                image.eq(4).attr("src",basePath+"images/ea/finance/NewPhoneOrders/star.png");
            }else if(index==5){
                image.eq(0).attr("src",basePath+"images/ea/finance/NewPhoneOrders/star_.png");
                image.eq(1).attr("src",basePath+"images/ea/finance/NewPhoneOrders/star_.png");
                image.eq(2).attr("src",basePath+"images/ea/finance/NewPhoneOrders/star_.png");
                image.eq(3).attr("src",basePath+"images/ea/finance/NewPhoneOrders/star_.png");
                image.eq(4).attr("src",basePath+"images/ea/finance/NewPhoneOrders/star_.png");
            }
        });

        //上传照片(提交服务器时需判断input=file 是否有空值，遍历删除；)
        var click_times = 0;
        $(".up_pic .btn_3").click(function() {
            if($(".h_img").length==3){
                alert("您最多只能上传3张图片")
            }else{
                click_times++;
                var _id = "click_" + click_times;
                var t = '<div class="upload_img" id=' + _id + '><div class="img_box"><img src="" alt=""></div><input type="file" name="pictureList" accept="image/*" style="opacity: 0;width: 3rem;margin-top: 1.5rem;"><i class="del_upimg"></i></div>'
                $(".up_pic .btn_3").before(t);
                var $id = $('#' + _id);
                $id.hide();
                var $id_inp = $('#' + _id + ' ' + 'input');
                $id_inp.click();
                //Input file选择图片上传事件
                $id_inp.one("change", function() {
                    var file = this.files[0];
                    var reader = new FileReader();
                    reader.onload = function() {
                        // 通过 reader.result 来访问生成的 DataURL
                        var url = reader.result;
                        $id.find("img").attr("src", url)
                    };
                    reader.readAsDataURL(file);
                    $id.show();
                    $id.addClass("h_img");
                });
            }

        });
        //上传图片删除功能
        $(".up_pic").on("click", ".del_upimg", function(e) {
            e.stopPropagation();
            $(this).parent().detach();
        });
        $(".evaluate_btn").click(function(){
        	if($("textarea").val()==""){
        		prompt("请补充评论！");
        		return;
    		}if($("#evaluation").val()==''){
    			prompt("请完善评价！");
    			return;
    		}if(!checkNull($(".score"))){
    			prompt("请完善店铺评分！");
    			return;
    		}
        	 var key = $("#key").val();
        	 var companyId=$("#companyID").val();
        	 var user=$("#user").val();
    		 var url=basePath+"/ea/consignee/sajax_ea_saveComments.jspa?tupn=goods&cskey="+key+"&companyID="+companyID;
    		 var formData=new FormData($("#from1")[0]);
    		 $.ajax({
    		     url: encodeURI(url) ,  
    		        type: 'POST',  
    		        data: formData,  
    		        async: false,  
    		        cache: false,  
    		        contentType: false,  
    		        processData: false,  
    		        success: function (data) {  
    					var member = eval("(" + data + ")");
    					var boo=member.s;
    					if(boo=="1"){
    						alert("保存成功");
    						window.location.href=basePath+"/ea/pobuy/ea_getReceiptList.jspa?user="+user+"&state=11";
    					}else{
    						alert("保存失败");
    					}
    		        },  
    		        error: function (data) {  
    		            alert("保存失败！");  
    		        } 
    		 });
        });
        

    })
    function checkNull(items){
	 var flag=true;
	 for(var i=0;i<items.length;i++){
		 if(items[i].value==""){
			 flag=false;
			 return;
		 }
	 }
	 return flag;
 }
