var t = "";
var u = window.navigator.userAgent;
var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; // android终端
var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); // ios终端
$(function() {
     loaded();







 
    //我的订单
    $(".myorder").click(function(){
      if(sccId==""){
          document.location.href = basePath+"/page/WFJClient/NewLogin.jsp?loginPage=login";

      }else{
              document.location.href = basePath+"ea/pobuy/ea_getPhoneOrdersList.jspa?staid="+staid+"&sccId="+sccId;
      }

    });

    //钱包
    $(".bag").click(function(){
        if(sccId==""){
            document.location.href = basePath+"/page/WFJClient/NewLogin.jsp?loginPage=login";

        }else{
            document.location.href = basePath+"/ea/jinbi/ea_gethyjifen.jspa?user="+user+"&sccid="+sccId+"&khd=0&app=00"
        }

    });
    //购物车
    $(".cart").click(function(){
        if(sccId==""){
            document.location.href = basePath+"/page/WFJClient/NewLogin.jsp?loginPage=login";

        }else{
            if(pricetype=="2") {
                document.location.href = basePath + "ea/wholesaleMall/ea_shoppingCartList.jspa"
            }else{
                document.location.href = basePath + "ea/wfjshop/ea_getcity.jspa";

            }
        }


    });

    $("#center").click(function(){
        if(sccId==""){
            document.location.href = basePath+"/page/WFJClient/NewLogin.jsp?loginPage=login";

        }
        $(".div-list").show();
    });
    $("#close-div").click(function(){
        $(this).parents(".div-list").hide();
    })
    if (isAndroid == true) {

    } else if (isiOS == true) {
        $(".div-box img").addClass("div-iosimg");
    }
    
    dealNum();
    dealListNum();
    var myVideo = document.getElementById("video");
    //图片高度判断
    if(($(".div-box img").height()/2)>=($(".div-box img").width())){
        $(".div-box img").addClass("imgtop");
    }
    if(($(".div-box img").width()/2)<=($(".div-box img").height())){
        $(".div-box img").addClass("imgleft");
    }
    //点击播放暂停
    $(".div-play").click(function(){
        $('.player').trigger("click");
    })
    //点击关注
    $(".div-01").click(function(e){
        e.stopPropagation();
        if($(this).find("div").is(".active")){
            $(this).find("div").removeClass("active");  //取消
        }else{
            $(this).find("div").addClass("active");//关注
        }
        var url = basePath+"ea/dsp/sajax_ea_careUser.jspa";
        $.ajax({
            url:url,
            type:"post",
            dataType:"json",
            async:true,
            data:{
                videoStaffID:videoStaffID,
                ajax:"ajax"
            },
            success:function(data){
                var login = data.login;
                if(login=="login"){
                    document.location.href = basePath+"/page/WFJClient/NewLogin.jsp?loginPage=login";
                    return;
                }
            },
            error:function(data){
                console.log("关注失败");
            }

        })
    })
    //点击红心
    var i = 0;
    $(".div-02").click(function(e){
        e.stopPropagation();
        b();
    })
    //点击分享
    $(".div-04").click(function(){
        $(".fenxiang").show();
        $("body").addClass("body-fenxiang");
    })
    $(".qx").click(function(){
        $(".fenxiang").hide();
        $("body").removeClass("body-fenxiang");
    })
    //点击去掉缩略图

    $('.box').on('click', function () {
        $('.video-img').hide();
    });
    //全屏暂停播放
    $('.player').on('click', function () {
        clicking();
    });
    $('.nobox').on('click', function () {
        clicking();
    });
    let clicked = 1;
    let time = null;
    function clicking() {
        if (clicked === 1) {
            clicked++
            time = setTimeout(() => {
                clicked = 1
                // 单击
                a();
            }, 300)
        } else if (clicked === 2) {
            clearInterval(time)
            clicked = 1
            // 双击
            b();
        }
    }
    //滑动
    document.getElementById("acc").addEventListener("touchstart", function(e) {
        //e.preventDefault();
        startX = e.changedTouches[0].pageX,
            startY = e.changedTouches[0].pageY;
    });
    document.getElementById("acc").addEventListener("touchmove", function(e) {
        e.preventDefault();
        moveEndX = e.changedTouches[0].pageX,
            moveEndY = e.changedTouches[0].pageY,
            X = moveEndX - startX,
            Y = moveEndY - startY;
        $('.video-img').hide();
        if ( Math.abs(Y) > Math.abs(X) && Y > 0) {
          //  alert("上往下滑");

            if($(".morevideo").find(".current").length!=0) {
                if($(".morevideo").find(".current").prev().length>0) {
                    $(".morevideo").find(".current").removeClass("current").prev().addClass("current")
                    setVideo($(".morevideo").find(".current"));
                }
            }

            $("#video").attr("autoplay","true");//直接播放
            myVideo.play();
            $("#video").attr("data-play","pause");
            $(".div-play img").hide();
            $('.video-img').hide();
        }
        else if ( Math.abs(Y) > Math.abs(X) && Y < 0 ) {
            //alert("下往上滑");
            if(pagenumber<pagecount){
                loaded();
            }else{
                clearInterval(t);
            }

                if($(".morevideo").find("li").length>2) {

                    if($(".morevideo").find(".current").length==0) {
                        var videoID = $(".box .videoID").val();
                        var vi = $(".morevideo").find("li").eq(0).attr("id");
                        if (vi == videoID) {
                            $(".morevideo").find("li").eq(1).addClass("current");
                            setVideo($(".morevideo").find("li").eq(1));
                        }else{
                            $(".morevideo").find("li").eq(0).addClass("current");
                            setVideo($(".morevideo").find("li").eq(0));
                        }
                    }else{
                        $(".morevideo").find(".current").removeClass("current").next().addClass("current")
                        setVideo($(".morevideo").find(".current"));

                    }
                }else{
                    return false;
                }
            $("#video").attr("autoplay","true");//直接播放
            myVideo.play();
            $("#video").attr("data-play","pause");
            $(".div-play img").hide();
            $('.video-img').hide();
        }
    });
    //点击作品查看
    $(document).on("click",".morevideo li",function(){
        var myVideo = document.getElementById("video");
        $("#video").attr("autoplay","true");//直接播放
        myVideo.play();
        $("#video").attr("data-play","pause");
        $(".div-play img").hide();
        $('.video-img').hide();
        setVideo($(this));
        $("html,body").animate({scrollTop:'0px'},200); //回到顶部

    });



    //下载
    $(".shopping").click(function(e){
        e.stopPropagation();
      //  down();
        document.location.href = basePath+"ea/dsp/ea_getdspProductsList.jspa?videoID="+videoID+"&web=web";

    });
});
function a () {
    //console.log('单击')
    if($("#video").data("play")=="play"){
        $(".player").find('video')[0].play();
        $("#video").data("play","pause");
        $(".div-play img").hide();
        $('.video-img').hide();
    }else{
        $(".player").find('video')[0].pause();
        $("#video").data("play","play");
        $(".div-play img").show();
    }
}

function b () {
    //console.log('双击')
    if($(".div-02").find("div").is(".active")){
        $(".div-02").find("div").removeClass("active");
        $(".div-02 .praisev").text(Number($(".div-02 .praisev").text())-1);
    }else{
        $(".div-02").find("div").addClass("active");
        $(".div-02 .praisev").text(Number($(".div-02 .praisev").text())+1);
    }
    dealNum();
    dz();

}


//作品 
function loaded(){
    pagenumber += 1;

    var url = basePath+"ea/dsp/sajax_ea_getViewAllVideo.jspa";
    $.ajax({
        url : url,
        type : "get",
        async : false,
        data : {
            pageNumber:pagenumber,
            videoStaffID:videoStaffID
        },
        dataType : "json",
        success : function(data) {
            if(data==null){
                return false;
            }
            var pageForm = data.pageForm;
            var htmlstr=new Array();
            var obj;
            if(pageForm!=null){
                pagecount = pageForm.pageCount;
                $(".ttsw_last").removeClass("ttsw_last");
                for ( var i = 0; i <  pageForm.list.length; i++) {
                    obj = pageForm.list[i];
                    if(i==pageForm.list.length-1){
                        htmlstr.push("<li id='"+obj[0]+"'  class='ttsw_last'>");
                    }else{
                        htmlstr.push("<li id='"+obj[0]+"'  >");
                    }
                    htmlstr.push("<img src='"+obj[12]+"' onerror='this.src=\"" + basePath + "/images/ea/collage/dsp/pic_03.png\"' />");
                    htmlstr.push("<input type='hidden' class='videoIDl' value='"+obj[0]+"'>");
                    htmlstr.push("<input type='hidden' class='videourl' value='"+obj[1]+"'>");
                    htmlstr.push("<input type='hidden' class='titlename' value='"+obj[2]+"'>");
                    htmlstr.push("<input type='hidden' class='videoStaffID' value='"+obj[3]+"'>");
                    htmlstr.push("<input type='hidden' class='videoStaffName' value='"+obj[4]+"'>");
                    htmlstr.push("<input type='hidden' class='createdate' value='"+obj[6]+"'>");
                    htmlstr.push("<input type='hidden' class='praisevl' value='"+obj[7]+"'>");
                    htmlstr.push("<input type='hidden' class='plcountvl' value='"+obj[8]+"'>");
                    htmlstr.push("<input type='hidden' class='sharevl' value='"+obj[9]+"'>");
                    htmlstr.push("<input type='hidden' class='ispraisel' value='"+obj[10]+"'>");
                    htmlstr.push("<input type='hidden' class='care' value='"+obj[11]+"'>");
                    htmlstr.push("<input type='hidden' class='coverImgUrl' value='"+obj[12]+"'>");
                    htmlstr.push("<div class='clearfix'>");
                    htmlstr.push("<p>");
                    htmlstr.push("<img src='"+basePath+"images/ea/collage/dsp/img-01.png'/>");
                    htmlstr.push("</p>");
                    htmlstr.push(" <p>");
                    htmlstr.push(jsw(obj[7]));
                    htmlstr.push("</p>");
                    htmlstr.push("</div>");
                    htmlstr.push("</li>");
                }
                $(".morevideo").append(htmlstr.join(""));

                
            }
        },
        error:function(data){
            console.log("加载下一页失败");
        }
    });

}

function dealListNum(){
    $(".morevideo li").each(function(){
          $(this).find(".dz").text(jsw($(this).find(".praisevl").val()));
        })
}
function dealNum(){

     $(".pw").text(jsw($(".praisev").text()));
     $(".cw").text(jsw($(".plcountv").text()));
     $(".sh").text(jsw($(".sharev").text()));
}

//数字转成w单位
function jsw(num){
    var result = num;
    if(Number(num)>10000){
        return formatDecimal(num/10000, 1)+"w";
    }
  return result;
}



//保留小数，不四舍五入
function formatDecimal(num, decimal) {
    num = num.toString()
    let index = num.indexOf('.')
    if (index !== -1) {
        num = num.substring(0, decimal + index + 1)
    } else {
        num = num.substring(0)
    }
    return parseFloat(num).toFixed(decimal);
}

//下载
function down(){
    if (isAndroid == true) {
         document.location.href ="https://a.app.qq.com/o/simple.jsp?pkgname=com.xiaofeng.androidframework&channel=0002160650432d595942&fromcase=60001";
    } else if (isiOS == true) {
        document.location.href = "https://apps.apple.com/cn/app/shu-zi-de-qiu/id1182214522?l=en";
    }

}


function setVideo(obj){
    console.log("----------"+obj);
$(".box .title").text($(obj).find(".titlename").val());
$(document).attr("title",$(obj).find(".titlename").val());//修改title值
$(".box .name").text("@"+$(obj).find(".videoStaffName").val());
console.log($(obj).find(".videoStaffName").val());
$(".box .praisev").text($(obj).find(".praisevl").val());
$(".box .plcountv").text($(obj).find(".plcountvl").val());
$(".box .sharev").text($(obj).find(".sharevl").val());
$(".box #video").attr("src",$(obj).find(".videourl").val())
dealNum();
$(".box .videoID").val($(obj).find(".videoIDl").val());

$(".div-box img").attr("src",basePath+$(obj).find(".headimage").val());
var care = $(obj).find(".care").val();

var  ispraisel = $(obj).find(".ispraisel").val();
if(care=="1"){
    $(".div-img2").addClass("active");
}else{
    $(".div-img2").removeClass("active");
}

    if(ispraisel=="1"){
        $(".div-02 .pr").addClass("active");
    }else{
        $(".div-02 .pr").removeClass("active");
    }
}

function dz(){
    var videoID = $(".div-02 .videoID").val();
    var url = basePath+"ea/dsp/sajax_ea_addZan.jspa";
    $.ajax({
        url:url,
        type:"post",
        dataType:"json",
        async:true,
        data:{
            videoID:videoID,
            ajax:"ajax"
        },
        success:function(data){
            var login = data.login;
            var  result = data.result;
            if(login=="login"){
                document.location.href = basePath+"/page/WFJClient/NewLogin.jsp?loginPage=login";
                return;
            }

        },
        error:function(data){
            console.log("点赞失败");
        }

    })

}
