/*banner轮播*/
var mySwiper1 = new Swiper('#swiper-ord', {
    autoplay: {
        delay: 3000,
        stopOnLastSlide: false,
        disableOnInteraction: false,
    },
    loop : true,
    pagination: {
        el: '#swiper-ord .swiper-pagination',
        type: 'bullets',
        bulletElement : 'li',
        clickable: false,

    },
    /*如果只有一个slide就销毁swiper
     if(mySwiper.slides.length<=3){
     mySwiper.destroy();
     }*/
    
    
});

if(mySwiper!=""){
    mySwiper.destroy();
}
mySwiper = new Swiper('#swiper-ord2', {
    autoplay: false,
    loop : false,
    observer: true,//修改swiper自己或子元素时，自动初始化swiper
    observeParents: true//修改swiper的父元素时，自动初始化swiper
    ,
    navigation: {
        nextEl: '.swiper-button-next',
        prevEl: '.swiper-button-prev',
    },
    on: {

        slideChangeTransitionEnd() {
            this.active = $('.swiper-slide-active').attr('data-swiper-slide-index')
            var sum = this.realIndex + 1
       //     console.log(sum)
            var num1 = recordCount
        //    console.log(num1)
            if(sum==num1){
                if($(".menu .active").next("li").length>0) {
                    $(".menu .active").next("li").trigger("click");
                }else{
                    if($(".menu .active").next("li").length>0) {
                        $(".menu .active").next("li").trigger("click");
                    }else{
                        $(".menu li").eq("0").trigger("click");
                    }
                }

            }
        },
        touchMove(){
        //    console.log(this.activeIndex)
         //   var num1 = $("#swiper-ord2 .swiper-wrapper>div").size();
            var num1 = recordCount;
            if(num1==1){
                $(".menu .active").next("li").trigger("click");
            }
        },
        setTranslate: function(swiper,translate){
            //自定义事件
            // console.log(swiper);
            if(swiper>20){
            //    console.log("左")
             //   console.log(this.activeIndex)
                if(this.activeIndex==0){
                    if($(".menu .active").prev("li").length>0) {
                        $(".menu .active").prev("li").trigger("click");
                    }
                }

            }
            else{
             //   console.log("右边")

            }
        }
    }
});
$(function(){
    $(".ord-con").height($(window).height()-$(".fooder").height()*2-1+"px");
    $(".ord-nav li").click(function () {
        $(this).addClass("active").siblings().removeClass("active");
    });
    $(document).on("click", ".ord-nav2 .left .con ul li", function () {
        $(this).addClass("active").siblings().removeClass("active");
        maxIndex = 0;
        clearInterval(t);
        $("#swiper-ord2 #showBig").html("");
        categoryId = $(this).attr("id");
        pageNumber=1;
        pagecount=1;
        recordCount = 0;
        getProByCate(categoryId);

        if(mySwiper!=""){
            mySwiper.destroy();
        }
           mySwiper = new Swiper('#swiper-ord2', {
            autoplay: false,
            loop : false,
            navigation: {
                nextEl: '.swiper-button-next',
                prevEl: '.swiper-button-prev',
            },
            observer: true,//修改swiper自己或子元素时，自动初始化swiper
            observeParents: true,//修改swiper的父元素时，自动初始化swiper
               on: {
                   slideChangeTransitionEnd() {
                  //     this.active = $('.swiper-slide-active').attr('data-swiper-slide-index')
                    var sum = this.realIndex + 1
                  //     console.log(sum)
                       var num1 = recordCount
                  //     console.log(num1)
                       if (sum == num1) {
                           if($(".menu .active").next("li").length>0) {
                               $(".menu .active").next("li").trigger("click");
                           }else{
                               $(".menu li").eq("0").trigger("click");
                           }

                       }
                   },
                   touchMove(){
                 //      console.log(this.activeIndex)
                       var num1 = recordCount;
                       if (num1 == 1) {
                           if($(".menu .active").next("li").length>0) {
                               $(".menu .active").next("li").trigger("click");
                           }else{
                               $(".menu li").eq("0").trigger("click");
                           }
                       }
                   }
                   ,
                   setTranslate: function(swiper,translate){
                       //自定义事件
                     //  console.log(swiper);
                       if(swiper>20){
                        //   console.log("左")
                           if(this.activeIndex==0){
                               if($(".menu .active").prev("li").length>0) {
                                   $(".menu .active").prev("li").trigger("click");
                               }
                           }
                       }
                       else{
                       //    console.log("右边")

                       }
                   }
               }


        });
    });
    $(".ord-nav3 ul li").click(function () {
        $(this).addClass("active").siblings().removeClass("active");
    });
   //加数量
    $(document).on("click", ".add", function () {
        var t=$(this).parent().find('input[class*=text_box]');
        t.val(parseInt(t.val())+1);
         var ppid = $(this).parent().find(".ppid").text();
        setTotal(ppid,"add");
    });

    //减少数量
    $(document).on("click", ".min", function () {
        var t = $(this).parent().find('input[class*=text_box]');
        var value = Number(t.val());
        var ppid = $(this).parent().find(".ppid").text();
        if(value!=0){
            t.val(value-1);
            setTotal(ppid,"min");

        }

    });
    function setTotal(ppid,opr){

        var num = Number($(".fooder ul li i").text());
        if(opr=="add") {
            $(".fooder ul li i").text(num + 1);
        }else{
            $(".fooder ul li i").text(num - 1);
        }

        joinCart(ppid,1,opr);
    }

    var sumWidth=0;
    $(".ord-nav2 .left .con ul li").each(function(){
        sumWidth += $(this).outerWidth(true);
    });
    $(".ord-nav2 .left .con ul").width(sumWidth+1);

    var step = $(".ord-nav2 .left .con").scrollLeft();
    $(".ord-nav2 .left #r").click(function () {
        $(".ord-nav2 .left .con").scrollLeft(step+=10);

    });
    $(".ord-nav2 .left #l").click(function () {
        $(".ord-nav2 .left .con").scrollLeft(step-=10);

    })

    //点击结算
    $(".goshopping").click(function() {

        if( $(".fooder ul li i").text()=="0"){
            return;
        }
        var directUrl = basePath
            + "ea/restaurant/ea_queryLoginName.jspa?companyId="+companyId+"&ccompanyId=" + ccompanyId+"&postype="+postype;
        if(posNum!=null&&posNum!=""&&postype=="01") {
            document.location.href = basePath+"ea/sm/ea_qdlist.jspa?posNum="+posNum+"&ccompanyID="+ccompanyId+"&directUrl="+encodeURIComponent(directUrl);

        }else{
            document.location.href  = directUrl;

        }

    });
    //已点菜单
    $(".finiMenu").click(function(){
        if( $(".fooder ul li i").text()=="0"){
            return;
        }
        document.location.href = basePath+"ea/restmn/ea_cateMenu.jspa?companyId="+companyId+"&ccompanyId="+ccompanyId+"&finiMenu=1&posNum="+posNum;

    });


    //getAllCate();

  //购物车数量
 getComCartNum();
    getNextPage();
});
//获取菜单  （暂时停用）
function getAllCate(){

    var url = basePath + "ea/restmn/sajax_ea_getCateByComID.jspa";
    $.ajax({
        url : url,
        type : "get",
        async : true,
        dataType : "json",
        data:{
            companyId:companyId,
            pageNumber:pageNumber,
            pageSize:5,
            posNum:posNum
        },
        success : function (data) {
            if(data==null){
                  return;
            }
            var member = eval("(" + data + ")");

            var list = member.list;
            var html = new Array();

            for(var i = 0;i<list.length;i++) {
                if(i==0) {
                    categoryId = list[i].categoryId;
                    html.push("<li class='active' id='"+list[i].categoryId+"'>"+list[i].categoryName+"</li>");
                }else{

                    html.push("<li  id='"+list[i].categoryId+"'>"+list[i].categoryName+"</li>");
                }

            }


            $(".menu").html(html.join(""));

            var pageForm = member.pageForm;


            if(pageForm!=null){
                var htmlstr=new Array();
                var obj
                pagecount = pageForm.pageCount;

                for ( var i = 0; i <  pageForm.list.length; i++) {
                	maxIndex = Number(maxIndex)+1;
                    obj = pageForm.list[i];
                    htmlstr.push("<div class='swiper-slide' index='"+maxIndex+"'>");
                    if(obj[3]==""){
                        htmlstr.push("<img src='"+basePath+"'images/contacts/restaurant/mainNew/shop1.png' alt=''>");
                    }else{
                        htmlstr.push("<img src='"+basePath+obj[3]+"' onerror='this.src=\"" + basePath + "/images/contacts/restaurant/mainNew/shop1.png\"'  alt=''>");
                    }
                    htmlstr.push("<div class='text'>");
                    htmlstr.push("<h3>"+obj[1]+"</h3>");


                    if(obj[6]!="0") {
                        htmlstr.push("<div class='yj'><p class='price'>原价 &yen;"+obj[4]+"<span></span></p><p class='p2'>活动价 &yen;111"+obj[6]+"<span></span></p></div>");
                    }else if(obj[5]!="0") {
                        htmlstr.push(" <div><p class='price'>原价 &yen;"+obj[4]+"<span></span></p><p class='p2'>VIP价 &yen;"+obj[5]+"<span></span></p></div>");
                    }else{
                        htmlstr.push(" <div><p class='price'>原价 &yen;"+obj[4]+"<span></span></p></div>");

                     }

                    htmlstr.push("<div class='btn'>");
                    htmlstr.push("<span class='ppid'>"+obj[0]+"</span>");
                    htmlstr.push("<input class='min' name='' type='button' value='-'>");
                    htmlstr.push("<input class='text_box' name='' type='text' value='"+obj[7]+"' readonly='readonly'>");
                    htmlstr.push("<input class='add' name='' type='button' value='+'>");
                    htmlstr.push("</div>");
                    htmlstr.push("</div>");
                    htmlstr.push("</div>");

                }
               $("#swiper-ord2 #showBig").append(htmlstr.join(""));

            }

            if(mySwiper!=""){
                mySwiper.destroy();
            }
               mySwiper = new Swiper('#swiper-ord2', {
                autoplay: false,
                loop : false,
                navigation: {
                    nextEl: '.swiper-button-next',
                    prevEl: '.swiper-button-prev',
                },
                observer: true,//修改swiper自己或子元素时，自动初始化swiper
                observeParents: true//修改swiper的父元素时，自动初始化swiper


            });
               
            
            if(pageNumber==1&&pagecount>pageNumber){
                t=setInterval("getNextPage()", 4000);
            }


        }
    });

}

//获取商品
function getProByCate(categoryId){
    var url = basePath + "ea/restmn/sajax_ea_getProductByCate.jspa";
    $.ajax({
        url : url,
        type : "get",
        async : false,
        dataType : "json",
        data:{
            companyId:companyId,
            categoryId:categoryId,
            pageNumber:pageNumber,
            pageSize:10,
            posNum:posNum
        },
        success : function (data) {
            var member = eval("(" + data + ")");
            var pageForm = member.pageForm;


            if(pageForm!=null){
                var htmlstr=new Array();
                var obj = "";
                pagecount = pageForm.pageCount;
                recordCount = pageForm.recordCount;

                for ( var i = 0; i <  pageForm.list.length; i++) {
                	maxIndex = Number(maxIndex)+1;
                    obj = pageForm.list[i];
                    htmlstr.push("<div class='swiper-slide' index='"+maxIndex+"'>");
                    if(obj[3]==""){
                        htmlstr.push("<img src='"+basePath+"'images/contacts/restaurant/mainNew/shop1.png' alt=''>");
                    }else{
                        htmlstr.push("<img src='"+basePath+obj[3]+"' onerror='this.src=\"" + basePath + "/images/contacts/restaurant/mainNew/shop1.png\"'  alt=''>");
                    }
                    htmlstr.push("<div class='text'>");
                    htmlstr.push("<h3>"+obj[1]+"</h3>");


                    if(obj[6]!="0") {
                        htmlstr.push("<div class='yj'><p class='price'>原价 &yen;"+obj[4]+"<span></span></p><p class='p2'>活动价 &yen;111"+obj[6]+"<span></span></p></div>");
                    }else if(obj[5]!="0") {
                        htmlstr.push(" <div><p class='price'>原价 &yen;"+obj[4]+"<span></span></p><p class='p2'>VIP价 &yen;"+obj[5]+"<span></span></p></div>");
                    }else{
                        htmlstr.push(" <div><p class='price'>原价 &yen;"+obj[4]+"<span></span></p></div>");

                     }

                    htmlstr.push("<div class='btn'>");
                    htmlstr.push("<span class='ppid'>"+obj[0]+"</span>");
                    htmlstr.push("<input class='min' name='' type='button' value='-'>");
                    htmlstr.push("<input class='text_box' name='' type='text' value='"+obj[7]+"' readonly='readonly'>");
                    htmlstr.push("<input class='add' name='' type='button' value='+'>");
                    htmlstr.push("</div>");
                    htmlstr.push("</div>");
                    htmlstr.push("</div>");

                }
               $("#swiper-ord2 #showBig").append(htmlstr.join(""));
                if(pagecount>pageNumber){
                    t=setInterval("getNextPage()", 4000);
                }else{
                      clearInterval(5);
                }

            }


        }

    });

}

function getNextPage(){



        if(pageNumber<pagecount){
            pageNumber=pageNumber+1;
           getProByCate(categoryId);
        }else{
            clearInterval(t);
        }


}

//加入购物车
function joinCart(ppid,number,opr){
    var guig = "默认规格,";
    var url = basePath
        + "ea/restaurant/sajax_ea_gateringOrders.jspa";

    if(opr=="min"){

         url = basePath
            + "ea/restaurant/sajax_ea_gateringOrders2.jspa";
    }

    $.ajax({
        url: encodeURI(url),
        type: "post",
        async: true,
        dataType: "json",
        data: {
            "cart.itemNum":number,
             "cart.pid":ppid,
            ccompanyId: ccompanyId,
            companyId: companyId,
            "stardard": guig.substr(0, guig.length - 1),
            posNum:posNum
        },
        success:function(data){
            if(data==null){
                return false;
            }
            var mem = eval("("+data+")");
            var nologin = mem.nologin;
            if(nologin=="nologin"){
                    document.location.href = basePath+"/page/WFJClient/NewLogin.jsp?loginPage=login";
                    return;
            }

        },
        error:function(data){

           console.log("加入购物车失败");
        }

    });

}

//购物车数量
function getComCartNum(){
    var url = basePath + "ea/restmn/sajax_ea_getCompanyCartNum.jspa?da=" + new Date();
    $.ajax({
        url: url,
        type: "get",
        async: true,
        dataType: "json",
        data: {
            posNum: posNum,
            companyId:companyId
        },
        success: function cbf(data) {
            var member = eval("(" + data + ")");
            var cartNum = member.cartNum;
            $(".fooder ul li i").text(cartNum);
        },
        error: function () {
            console("购物车数量获取失败");
        }
    });

}

