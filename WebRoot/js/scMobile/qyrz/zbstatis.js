var key = "60f2afc9a34ec851b0baf7fd83eb75ba";
var  page = 0;
var type = "";
var t = "";
var tt="";
var head = "";
var date = "";
var yw = "";
var date = "";
var parameter = "";
var u = navigator.userAgent;
var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端

$(function() {
    //切换地址
  $(".p-dz").click(function(){

      if (isAndroid) {
          Android.callgetRoundLocal();
      } else if (isiOS) {
          var url = "func=" + 'iosMapaddress';
          window.webkit.messageHandlers.Native.postMessage(url);
      }

  });



    dateShow();
    getLocation();
    $(document).on("click","#dateSure",function() {
         date = $("#dateSelectorOne").val();

         getComplete(gdcate);

    });
    //点击认领
    $(document).on("click",".ul-list3 .rl ,.ul-list2 .rl",function(event) {
         event.stopPropagation();

         var id = $(this).parents("li").attr("id");

         var photo = $(this).parents("li").find(".photo").val();

         var name = $(this).parents("li").find(".name").val();
         var tel = $(this).parents("li").find(".tel").val();
         var location = $(this).parents("li").find(".location").val();

         var pname =  $(this).parents("li").find(".pname").val();
         var cityname =  $(this).parents("li").find(".cityname").val();
         var adname =  $(this).parents("li").find(".adname").val();
         var address =  $(this).parents("li").find(".address").val();
         var location =  $(this).parents("li").find(".location").val();
         var loc = location.split(",");
         var x = loc[0];
         var y = loc[1];
           tel =  tel.split(";")[0];
        var type =   $(this).parents("li").find(".type").val();
        var typecode =  $(this).parents("li").find(".typecode").val();
         document.location.href = basePath+"ea/qyrz/ea_getpk.jspa?id="+id+"&name="+name+"&photo="+photo+"&tel="+tel+"&pname="+pname+"&cityname="+cityname+"&adname="+adname+"&address="+address+"&x="+x+"&y="+y+"&head="+head+"&busiManagerID="+busiManagerID+"&type="+type+"&typecode="+typecode;



     });


    //未认领详情
    $(document).on("click",".ul-list3 li ,.ul-list2 li",function() {

        var gdID = $(this).attr("id");
        var allphoto = $(this).find(".allphoto").val();
        var photoys = $(this).find(".photo").val();
        var name = $(this).find(".name").val();
        var distance = $(this).find(".distance").val();
        var tel = $(this).find(".tel").val();
        var location = $(this).find(".location").val();
        var jd = location.split(",")[0];
        var wd = location.split(",")[1];
        var pname =  $(this).find(".pname").val();
        var cityname =  $(this).find(".cityname").val();
        var adname =  $(this).find(".adname").val();
        var address =  $(this).find(".address").val();
        var site = adname+address;
        var type =  $(this).find(".type").val();
        var typecode = $(this).find(".typecode").val();

        document.location.href = basePath+"page/scMobile/qyrz/detail.jsp?gdID="+gdID+"&allphoto="+allphoto+"&name="+name+"&site="+site+"&distance="+distance+"&tel="+tel+"&dwLnglatX="+dwLnglatX+"&dwLnglatY="+dwLnglatY+"&head="+head+"&jd="+jd+"&wd="+wd+"&photoys="+photoys+"&cityname="+cityname+"&pname="+pname+"&adname="+adname+"&address="+address+"&location="+location+"&busiManagerID="+busiManagerID+"&type="+type+"&typecode="+typecode;



    });


     //已认领详情
    $(document).on("click","#complete li",function() {
            var ccompanyID = $(this).attr("id");
            document.location.href = basePath+"ea/industry/ea_getCompanyHome.jspa?ccompanyId="+ccompanyID+"&industryType=&etype=&sc=web";
    });


    //分类点击
    $(".ul-nav li").click(function(){
        $(this).parents(".ul-nav").find("li").removeClass("active");
        $(this).addClass("active");
        gdcate = $.trim($(this).find("p").text());
        if(gdcate=="全部"){
            gdcate = "";
        }else{
            gdcate = gdcate+"服务";
        }
        clearInterval(t);
            pageNumber = 0;
            pageCount = 0;
            getComplete(gdcate);
            page = 0;
        if($(".sec-nav .active").index()==1) {
            if($(".ul-tab .active").index()==0) {
                //已发送
                yw = "y";

            }else{
                //未发送
                yw = "w";

            }

            getSendcompany(gdcate,yw);
            t = setInterval("getSendcompany(gdcate,yw)", 2000);
        }


    })


    //信息点击.
    $(".ul-tab li:not(.bh)").click(function(){
        $(this).parents(".ul-tab").find("li").removeClass("active");
        $(this).addClass("active");
          $(".ul-list2").html("");
        $(".ul-list3").html("");
        $(".ul-list").hide();
        clearInterval(t);
        page = 0;
        if( $(this).index()==0){
            //已发送
                    yw = "y";
            $(".ul-list2").show();
            $(".bh").hide();
        }else{
            //未发送
            yw = "w";
            $(".ul-list3").show();
            $(".bh").show();
        }

        getSendcompany(gdcate,yw);
        t = setInterval("getSendcompany(gdcate,yw)", 2000);
    })

    //自动拨号
    $(".bh").click(function(){

        if(isAndroid){
            Android.androidToAutoCallPhone(gdcate, parameter,dwLnglatX, dwLnglatY);
        }else if(isiOS){
            var url= "func=" + 'iosToAutoCallPhone';
            window.webkit.messageHandlers.Native.postMessage(url);
        }

    })
    //切换未认领已认领
    $(".sec-nav li").click(function(){
        $(this).parents(".sec-nav").find("li").removeClass("active");
        $(this).addClass("active");
        $(".ul-list").hide();
        clearInterval(t);
        if($(this).index()==1){
            page = 0;
            $(".ul-tab").show();

            if($(".ul-tab li.active").index()==0){
                yw = "y";
                $(".ul-list2").show();
            }else{
                  //未发送
                yw = "w";
                $(".ul-list3").show();
            }


            getSendcompany(gdcate,yw);
            t = setInterval("getSendcompany(gdcate,yw)", 2000);
        }else{
            $(".ul-tab").hide();
            $(".ul-list").hide();
            //已认领
            $("#complete").show();
        }
        dateShow();
    })

   //已认领滚动分页
    $(window).scroll(function(){


            if($(".sec-nav li.active").index()==0) {

                if ($(window).scrollTop() + $(window).height() > $("#complete").height()) {
                    if (pageNumber < pageCount) {
                        getComplete(gdcate);
                    }
                }
            }



    });


    // if($(".sec-nav li.active").index()==1){
    //     //未认领
    //     if(yw=="y") {
    //         if ($(window).scrollTop()+$(window).height()> $(".ul-list2").height()) {
    //             getSendcompany(gdcate,yw);
    //         }
    //     }else{
    //         if ($(window).scrollTop()+$(window).height()> $(".ul-list3").height()) {
    //             getSendcompany(gdcate,yw);
    //         }
    //     }
    //
    //
    // }else{
    //     if ($(window).scrollTop()+$(window).height()> $("#complete").height()) {
    //         if (pageNumber < pageCount) {
    //             getComplete(gdcate);
    //         }
    //     }
    // }


    //搜索
    $("#search").on("input", function () {
        parameter = $("#search").val();
        clearInterval(t);
        if($(".sec-nav li.active").index()==1){

            //未认领
            page = 0;
            getSendcompany(gdcate,yw);
            t = setInterval("getSendcompany(gdcate,yw)", 2000);

        }else{
            pageNumber = 0;
            pageCount =  0;
                    getComplete(gdcate);

        }
    });


});

//需要先引用高德api
function getCurrentPosition() {//调用浏览器定位服务
    if(dwLnglatX==""){
        // 百度地图API功能
        var map = new BMap.Map("allmap");
        var point = new BMap.Point(116.331398,39.897445);

        var geolocation = new BMap.Geolocation();
        geolocation.getCurrentPosition(function(r){
            if(this.getStatus() == BMAP_STATUS_SUCCESS){
                var mk = new BMap.Marker(r.point);
                map.addOverlay(mk);
                map.panTo(r.point);

                dwLnglatX = r.point.lng;
                dwLnglatY = r.point.lat;
                var pt = new BMap.Point(r.point.lng,r.point.lat);

                var geoc = new BMap.Geocoder();

                geoc.getLocation(pt, function(rs){
                    var addComp = rs.addressComponents;
                    var province = addComp.province;
                    if(addComp.province==addComp.city){
                        province = "";
                    }
                    $(".pos-dz").text(province + "" + addComp.city + "" + addComp.district + "" + addComp.street + "" + addComp.streetNumber);
                });

            } else {
                alert('failed'+this.getStatus());
            }
        },{enableHighAccuracy: true})
    }else{


    }



}


//获取定位
function getLocation() {
    var u = window.navigator.userAgent;
    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
    var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
    try{
        if (isAndroid == true) {
            console.log("安卓");
            var collection = Android.callgetAddrstr();//调用安卓接口
            if (collection != "-1") {
                var ar = collection.split(",");
                address = ar[0];//当前位置
                dwLnglatX = ar[1];//经度
                dwLnglatY = ar[2];//纬度
                $(".pos-dz").text(address);
            } else {
                console.log("经纬度获取失败!")
            }
        } else if (isiOS == true) {
            console.log("IOS");
            var url = "func=" + 'iosMapPoint';
            window.webkit.messageHandlers.Native.postMessage(url);
        }
    }catch(error){

        getCurrentPosition();
    }
}

function iosMapPoint(name) {
    if (name != "-1") {
        var ar = name.split(",");
        address = ar[0];//当前位置
        dwLnglatX = ar[1];//经度
        dwLnglatY = ar[2];//纬度
        $(".pos-dz").text(address);
        getPOI("");
    } else {
        console.log("经纬度获取失败!");
    }
}


function getComplete(gdcate){

    if(pageNumber==0){
        $("#complete").html("");
    }
    pageNumber += 1;
    var ulp = basePath
        + "/ea/qyrz/sajax_ea_getCompleteBus.jspa";
    $.ajax({
        type : "POST",
        url : ulp,
        async : true,
        dataType : "json",
        data:{
            gdcate:gdcate,
            parameter:parameter,
            date:date,
            "pageForm.pageNumber" : pageNumber,
            ajax:"ajax"
        },
        success : function(data) {
            var member = eval('(' + data + ')');
            var pageForm = member.pageForm;
            var htmlstr = new Array();
            $(".last").removeClass("last");
            $(".sec-nav .rlnum").text(0);
            if (pageForm != null && pageForm.list != null && pageForm.list.length > 0) {
                var list = pageForm.list;
                pageNumber = pageForm.pageNumber;
                pageCount = pageForm.pageCount;
                recordCount = pageForm.recordCount;
                $(".sec-nav .rlnum").text(recordCount);
                for(var i = 0;i<list.length;i++) {
                    var id= list[i][0];
                    if($("#complete li#"+id).length>0){
                        break;
                    }
                    if(i==list.length-1){
                        htmlstr.push("<li id='"+list[i][0]+"' class='clearfix last'>");
                    }else{
                        htmlstr.push("<li  class='clearfix' id='"+list[i][0]+"'>");
                    }
                    htmlstr.push("<div class='div-img'>");
                    htmlstr.push("<img src='"+basePath+list[i][2]+"'  onerror='this.src=\""+ basePath +"/images/ea/production/forum/reportAnError.png\"'>");
                    htmlstr.push(" </div>");
                    htmlstr.push("<div class='div-txt'>");
                    htmlstr.push(" <p>"+list[i][1]+"</p>");
                    htmlstr.push(" <p>"+(list[i][3]==null?'':list[i][3])+"</p>");
                    htmlstr.push(" </div>");

                    htmlstr.push("</li>");

                }
            }
            $("#complete").append(htmlstr.join(""));



        },
        error : function(data) {
            console.log("加载已认领");
        }
    });

}




//已发消息，未发消息
function getSendcompany(gdcate,yw){
  console.log(page);
    if(page==0){
      //  clearInterval(t);
        if(yw=="y") {
            $(".ul-list2").html("")
        }else{
            $(".ul-list3").html("")

        }

    }
    page += 1;
    var ulp = basePath
        + "/ea/qyrz/sajax_ea_getSendcompany.jspa?d="+new Date();
    $.ajax({
        type : "POST",
        url : ulp,
        async : true,
        dataType : "json",
        data:{
            gdcate:gdcate,
            parameter:parameter,
            "pageForm.pageNumber" : page,
            jd:dwLnglatX,
            wd:dwLnglatY,
            yw:yw
        },
        success : function(data) {
            var member = eval('(' + data + ')');
            var pageForm = member.pageForm;
            var htmlstr = new Array();
            $(".last").removeClass("last");
            if (pageForm != null && pageForm.list != null && pageForm.list.length > 0) {
                var pois = pageForm.list;
                for(var i = 0;i<pois.length;i++) {
                    var id= pois[i].id;
                    var pname = pois[i].pname;
                    var cityname =  pois[i].cityname;
                    var adname = pois[i].adname;
                    var address = pois[i].address;
                    var tel = pois[i].tel;
                    var location = pois[i].location;
                    var type = pois[i].type;
                    var typecode = pois[i].typecode;
                    if(pname==cityname){
                        pname = "";
                    }
                    var site = adname+address;
                    if(yw=="y") {
                        if ($(".ul-list2 li#" + id).length > 0||pageForm.pageNumber==-1) {
                            clearInterval(t);
                            break;
                        }
                    }else{
                        if ($(".ul-list3 li#" + id).length > 0||pageForm.pageNumber==-1) {
                            clearInterval(t);
                            break;
                        }

                    }
                     var photo = pois[i].photos;
                    var ph = "";
                    var allphoto = "";
                    if(photo.length>0){
                        ph = photo[0];

                        for(var j=0;j<photo.length;j++){

                            if(j==photo.length-1){
                                allphoto += photo[j];
                            }else{
                                allphoto += photo[j]+",";
                            }
                        }
                    }


                    htmlstr.push("<li  class='clearfix' id='"+pois[i].id+"'>");
                    htmlstr.push("<input type='hidden' value='"+pois[i].name+"' class='name'>");
                    htmlstr.push("<input type='hidden' value='"+pois[i].distance+"' class='distance'>");
                    htmlstr.push("<input type='hidden' value='"+pois[i].location+"' class='location'>");
                    htmlstr.push("<input type='hidden' value='"+pois[i].pname+"' class='pname'>");
                    htmlstr.push("<input type='hidden' value='"+pois[i].cityname+"' class='cityname'>");
                    htmlstr.push("<input type='hidden' value='"+pois[i].adname+"' class='adname'>");
                    htmlstr.push("<input type='hidden' value='"+pois[i].address+"' class='address'>");
                    htmlstr.push("<input type='hidden' value='"+pois[i].tel+"' class='tel'>");
                    htmlstr.push("<input type='hidden' value='"+ph+"' class='photo'>");
                    htmlstr.push("<input type='hidden' value='"+allphoto+"' class='allphoto'>");
                    htmlstr.push("<input type='hidden' value='"+type+"' class='type'>");
                    htmlstr.push("<input type='hidden' value='"+typecode+"' class='typecode'>");
                    htmlstr.push("<div class='div-img'>");
                    htmlstr.push("<img src='"+ph+"'  onerror='this.src=\""+ basePath +"/images/ea/production/forum/reportAnError.png\"'>");
                    htmlstr.push(" </div>");
                    htmlstr.push("<div class='div-txt'>");
                    htmlstr.push(" <p>"+pois[i].name+"</p>");
                    htmlstr.push(" <p>"+adname+address+"</p>");
                    htmlstr.push(" </div>");
                    htmlstr.push("<div class='div-right rl'>");
                    htmlstr.push(" <p>请认领</p>");
                    htmlstr.push(" <p>"+pois[i].distance+"</p>");
                    htmlstr.push(" </div>");
                    htmlstr.push("</li>");

                }
            }

            if(yw=="y") {
                $(".ul-list2").append(htmlstr.join(""));
            }else{
                $(".ul-list3").append(htmlstr.join(""));
            }

            if(pageForm.pageNumber==-1) {

                    clearInterval(t);

            }



        },
        error : function(data) {
            console.log("加载已认领");
        }
    });

}


//已认领的时候显示
function dateShow(){

    if($(".sec-nav li.active").index()==1){
        //未认领
        $(".div-img-rl").hide();
    }else{
       //已认领
        $(".div-img-rl").show();
    }
}
/**************************定位获取地址开始************************/
function ios_address(param) {
    var p = param.substring(0, param.indexOf(">"));
    var address = p.substring(0, p.indexOf("<"));
    var jv = p.substring(p.indexOf("<") + 1);
    dwLnglatX = jv.substring(1, jv.indexOf(","));
    dwLnglatY  = jv.substring(jv.indexOf(",") + 2);

    $(".pos-dz").text(address);
    getcx();
}

function a_address(param) {
    var address = param.substring(0, param.indexOf(","));
    var coordinate = param.substring(param.indexOf(",") + 1);
    dwLnglatX = jv.substring(1, jv.indexOf(","));
    dwLnglatY  = jv.substring(jv.indexOf(",") + 2);
    $(".pos-dz").text(address);
    getcx();

}

/**************************定位获取地址结束************************/
function getcx(){

    if($(".sec-nav li.active").index()==1){
        getSendcompany(gdcate,yw);

    }

}

function retAndroid(){
    if(isAndroid==true){
        Android.finishWeb();
    }else if(isiOS==true){
        window.webkit.messageHandlers.Native.postMessage(url);

    }
}
