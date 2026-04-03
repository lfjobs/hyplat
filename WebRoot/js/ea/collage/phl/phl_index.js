$(function() {
    
   // nav();
   //获取最近看过
    recentView();
    //最新资讯
    recentNews();
    //优质商户
    busiComList();
    //市场
    marketList();
    //有货拉
    recentCar();
    //特色区
    getExhiList();
    
    
    getLocation();

    //点击最新资讯查看详情
    $(document).on("click","#rotation li",function(event){
        var ppId = $(this).attr("id");
        var ccompanyId = $(this).find(".newsccomID").val();
        document.location.href = basePath+"ea/industry/ea_informationDetails.jspa?ppId="+ppId+"&ccompanyId="+ccompanyId+"&type=time&miniSystemJudge=03";

    });
    //点击查看市场
    $(document).on("click",".smul li",function(event){
        var ccompanyID = $(this).attr("id");
        document.location.href = basePath+"ea/phl/ea_getPhlIndex.jspa?ccompanyID="+ccompanyID;

    });
    
    //搜索按钮
    $("input[type='search']").click(function(){
    	var term = $(this).attr("placeholder");
        document.location.href = basePath+"ea/phlpro/ea_jumpSearch.jspa?ccompanyID="+ccomIDPlatform+"&term="+term;
    })
    
    //点击查看热门分类
    $(document).on("click",".hotcate",function(){
    	var codePID = $(this).attr("id");
    	var codeValue = $(this).find(".fenlei").text();
    	addRecentView(codePID,codeValue);
        document.location.href = basePath+"page/ea/collage/phl/phl_product.jsp?ccompanyID="+ccomIDPlatform+"&hotID="+codePID+"&hotValue="+codeValue;

    });
  
   //跳转地址添加
    $(".divdz").click(function(){
    	//定位地址跳转到添加地址
    	
    	document.location.href = basePath+"ea/perinfor/ea_getReceiptAddressList.jspa?staffId="+staffid+"&editType=00&flag=1";
    	
    	
    });
    
    //进入店铺
    $(document).on("click",".buul li",function(){
    	var companyid = $(this).attr("data-comid");
    	var ccompanyID = $(this).attr("id");
    	var companyName = $(this).find(".comname").text();
    	document.location.href = basePath+"ea/wholesaleMall/ea_toWholesaleMall.jspa?companyid="+companyid+"&ccompanyID="+ccompanyID+"&companyName="+companyName+"&phl=phl";
    	
    });

});

//获取最近看过
function recentView(){

    var url = basePath+"ea/phl/sajax_ea_getRecentViewList.jspa";
    $.ajax({
        url : url,
        type : "get",
        async : true,
        dataType : "json",
        success : function(data) {
            var member = eval("(" + data + ")");
            var viewlist   = member.viewlist;;

            var htmlstr=new Array();
            var obj;
            if(viewlist!=null){
                htmlstr.push("<li>");
                htmlstr.push("<a href='#'>");
                htmlstr.push("<img src='"+basePath+"images/ea/collage/phl/img_05_01.png'/>");
                htmlstr.push("</a>");
                htmlstr.push("</li>");
                for ( var i = 0; i <  viewlist.length; i++) {
                    obj = viewlist[i];
                    htmlstr.push("<li>");
                    htmlstr.push("<a href='javascript:viewProduct(\""+obj.codeID+"\",\""+obj.codeValue+"\")'>"+obj.codeValue+"</a>");
                    htmlstr.push("</li>");

                }
                $(".view").append(htmlstr.join(""));

            }

        },
        error:function(data){
            console.log("获取最近看过失败");
        }
    });

}


//有货拉
function recentCar(){

    var url = basePath+"ea/phl/sajax_ea_getIndexLogisList.jspa";
    $.ajax({
        url : url,
        type : "get",
        async : true,
        dataType : "json",
        success : function(data) {
            var member = eval("(" + data + ")");
            var carlist   = member.carlist;;

            var htmlstr=new Array();
            var obj;
            if(carlist!=null){
                for ( var i = 0; i <carlist.length; i++) {
                    obj = carlist[i];
                    htmlstr.push("<li class='clearfix'>");
                    htmlstr.push("<img src='"+basePath+obj[0]+"' onerror=\"this.src=\'' + basePath + 'images/ea/collage/phl/img_06.png\'\"/>");
                    htmlstr.push("<menu>");
                    htmlstr.push("<li>车主："+obj[1]+"</li>");
                    htmlstr.push("<li>车牌号："+obj[2]+"</li>");
                    htmlstr.push("<li>载重："+obj[3]+"</li>");
                    htmlstr.push("<li>载重体积："+obj[4]+"</li>");
                    htmlstr.push("</menu>");
                    htmlstr.push("<menu>");
                    htmlstr.push("<li>车型："+obj[5]+"</li>");
                    htmlstr.push("<li>长宽高："+obj[6]+"</li>");
                    htmlstr.push("<li>市场："+obj[7]+"</li>");
                    htmlstr.push("</menu>");
                    htmlstr.push("</li>");

                }
                $(".yhl").append(htmlstr.join(""));

            }

        },
        error:function(data){
            console.log("获取最近看过失败");
        }
    });

}

//最新资讯
function recentNews(){

    var url = basePath+"ea/phl/sajax_ea_getRecentNews.jspa";
    $.ajax({
        url : url,
        type : "get",
        async : true,
        dataType : "json",
        success : function(data) {
            var member = eval("(" + data + ")");
            var newslist   = member.newslist;


            var obj;
            if(newslist!=null){
                for ( var i = 0; i <  newslist.length; i++) {
                    var htmlstr=new Array();
                    obj = newslist[i];
                    htmlstr.push("<li id='"+obj[2]+"' class='row"+i+"'>");
                    htmlstr.push("<input type='hidden' class='newsccomID' value='"+obj[3]+"'/>");

                    htmlstr.push("<p class='txt_2'>");
                    htmlstr.push(obj[0]);
                    htmlstr.push("</p>")
                    htmlstr.push("<img src='"+basePath+obj[1]+"' onerror=\"this.src=\'' + basePath + '/images/ea/production/forum/default.jpg\'\"/>");
                    htmlstr.push("</li>");
                    $("#rotation").append(htmlstr.join(""));
                    if($("#rotation").find(".row"+i).find(".txt_2").css("height")<="30px"){
                        $("#rotation").find(".row"+i).find(".txt_2").css("line-height","2.3rem")
                    }
                }

            }

        },
        error:function(data){
            console.log("获取最近看过失败");
        }
    });

}
//优质商户
function busiComList(){

    var url = basePath+"ea/phl/sajax_ea_getIndexBusiComList.jspa";
    $.ajax({
        url : url,
        type : "get",
        async : true,
        dataType : "json",
        data:{
        	ccompanyID:ccomIDPlatform
        },
        success : function(data) {
            var member = eval("(" + data + ")");
            var comlist   = member.comlist;


            if(comlist!=null){
                var htmlstr=new Array();
                var obj;
                for ( var i = 0; i <  comlist.length; i++) {
                    obj = comlist[i];
                    htmlstr.push("<li data-comid='"+obj[5]+"'class='clearfix' id='"+obj[0]+"'>");
               
                    htmlstr.push("<img src='" + basePath + obj[1]+"' onerror=\"this.src=\'' + basePath + '/images/ea/production/forum/default.jpg\'\"/>");

                    
                    htmlstr.push("<p>"+obj[2]+"</p>");
                    htmlstr.push("<p class='comname'>"+obj[3]+"</p>");
                    htmlstr.push("<p><span class='txt'>"+obj[4]+"</span></p>");
                    htmlstr.push("</li>");


                }
                $(".buul").append(htmlstr.join(""));

            }

        },
        error:function(data){
            console.log("获取优质商家失败");
        }
    });

}


//批发农贸市场
function marketList(){

    var url = basePath+"ea/phl/sajax_ea_getIndexMarketList.jspa";
    $.ajax({
        url : url,
        type : "get",
        async : true,
        dataType : "json",
        success : function(data) {
            var member = eval("(" + data + ")");
            var marketlist   = member.marketlist;

            var htmlstr=new Array();
            var obj;
            if(marketlist!=null){
                for ( var i = 0; i <  marketlist.length; i++) {
                    obj = marketlist[i];
                    htmlstr.push("<li class='clearfix' id='"+obj[0]+"'>");
                   
                        htmlstr.push("<img src='" + basePath + obj[2]+"' onerror=\"this.src=\'' + basePath + '/images/ea/production/forum/default.jpg\'\"/>");

                    
                    htmlstr.push("<p>"+obj[1]+"</p>");
                    htmlstr.push("<p>");
                    htmlstr.push("<img src='"+basePath+"images/ea/collage/phl/location.png'/>");
                    htmlstr.push(obj[3]);
                    htmlstr.push("</p>");
                    htmlstr.push("</li>");

                }
                $(".smul").append(htmlstr.join(""));

            }

        },
        error:function(data){
            console.log("获取市场失败");
        }
    });

}


//特色区
function getExhiList(){

    var url = basePath+"ea/phl/sajax_ea_getExhiProduct.jspa";
    $.ajax({
        url : url,
        type : "get",
        async : true,
        dataType : "json",
        data:{
        	companyID:companyID
        },
        success : function(data) {
            var member = eval("(" + data + ")");
            var tsmp = member.ts;

            var htmlstr=new Array();
            var obj;
            if(tsmp!=null){
            	
            	 $.each(tsmp,function(key,values){
            		 htmlstr.push("<li>");
                	 htmlstr.push("<a href='"+basePath+"/ea/industry/ea_informationDetails.jspa?ppId="+values[3]+"&ccompanyId=&type=time&miniSystemJudge=03&pricetype=1'>");
                	 htmlstr.push("<p>"+key+"</p>");
                	 htmlstr.push("<p>"+values[2]+"</p>");
                	 htmlstr.push("<p>");
                	 htmlstr.push("<img src='"+values[0]+"'/>");
                	 htmlstr.push("<img src='"+values[1]+"'/>");
                	 htmlstr.push("</p>");
                	 htmlstr.push("</a>");
                	 htmlstr.push("</li>");
                 });
            	 $("#ts").append(htmlstr.join(""));
               

            }

        },
        error:function(data){
            console.log("获取特色");
        }
    });

}
//添加最近看过
function addRecentView(codeID,codeValue){
	
    var url = basePath+"ea/phl/sajax_ea_addRecentView.jspa";
    $.ajax({
        url : url,
        type : "get",
        async : true,
        dataType : "json",
        data:{
        	ccompanyID:ccomIDPlatform,
        	codeID:codeID,
        	codeValue:codeValue
        },
        success : function(data) {
        	console.log("添加最新看过成功");

        },
        error:function(data){
            console.log("添加最新看过失败");
        }
    });
	
}

//最近浏览分类查看
function viewProduct(codeID,codeValue){
	
    document.location.href = basePath+"page/ea/collage/phl/phl_product.jsp?ccompanyID="+ccomIDPlatform+"&hotID="+codeID+"&hotValue="+codeValue;
	
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
            longitude = ar[1];//经度
            latitude = ar[2];//纬度
            console.log("当前位置,经纬度"+collection);
            $(".adr").text(address);
            updatePos(longitude,latitude);
           
           // $("#getLocation").text(address.substring(address.indexOf("市")+1));
        } else {
            console.log("经纬度获取失败!")
        }
    } else if (isiOS == true) {
        console.log("IOS");
        var url = "func=" + 'iosMapPoint';
        window.webkit.messageHandlers.Native.postMessage(url);
    }
    }catch(error){
    	
    	gdWeblocation();
    }
}

function iosMapPoint(name) {
    if (name != "-1") {
        var ar = name.split(",");
         address = ar[0];//当前位置
        longitude = ar[1];//经度
        latitude = ar[2];//纬度
        $(".adr").text(address);
        updatePos(longitude,latitude);
    } else {
        console.log("经纬度获取失败!");
    }
}
/**
 * 
 * 更新地址
 */
function updatePos(longitude,latitude){


    var url = basePath+"ea/android/sajax_ea_updatePosInfo.jspa";
    $.ajax({
        url : url,
        type : "get",
        async : true,
        data:{
        	    staffid:staffid,
        	    longitude:longitude,
        		latitude:latitude,
        },
        dataType : "json",
        success : function(data) {
           
      

        },
        error:function(data){
            
        }
    });
	
	
}
//高德地图，安卓不好用
function gdWeblocation(){
	
	mapObj = new AMap.Map('iCenter');
	mapObj.plugin('AMap.Geolocation', function () {
	    geolocation = new AMap.Geolocation({
	        enableHighAccuracy: true,//是否使用高精度定位，默认:true
	        timeout: 10000,          //超过10秒后停止定位，默认：无穷大
	        maximumAge: 0,           //定位结果缓存0毫秒，默认：0
	        convert: true,           //自动偏移坐标，偏移后的坐标为高德坐标，默认：true
	        showButton: true,        //显示定位按钮，默认：true
	        buttonPosition: 'LB',    //定位按钮停靠位置，默认：'LB'，左下角
	        buttonOffset: new AMap.Pixel(10, 20),//定位按钮与设置的停靠位置的偏移量，默认：Pixel(10, 20)
	        showMarker: true,        //定位成功后在定位到的位置显示点标记，默认：true
	        showCircle: true,        //定位成功后用圆圈表示定位精度范围，默认：true
	        panToLocation: true,     //定位成功后将定位到的位置作为地图中心点，默认：true
	        zoomToAccuracy:true      //定位成功后调整地图视野范围使定位位置及精度范围视野内可见，默认：false
	    });
	    
	    function onComplete(obj){
	    	var pos = obj.position;

	        var  longitude = obj.position.getLng();//精度
	        var  latitude = obj.position.getLat();//纬度

	        $(".adr").text(obj.formattedAddress);
	        updatePos(longitude,latitude);

	        
	     }

	     function  onError(obj){
	       console.log(obj.message);
	     }  
	    mapObj.addControl(geolocation);
	    geolocation.getCurrentPosition();
	    AMap.event.addListener(geolocation, 'complete', onComplete);//返回定位信息
	    AMap.event.addListener(geolocation, 'error', onError);      //返回定位出错信息
	});
	
	
}

//百度地图
function gdWeblocation(){

    // 百度地图API功能
    var map = new BMap.Map("allmap");
    var point = new BMap.Point(116.331398,39.897445);
    map.centerAndZoom(point,12);

    var geolocation = new BMap.Geolocation();
    geolocation.getCurrentPosition(function(r){
        if(this.getStatus() == BMAP_STATUS_SUCCESS){
            var mk = new BMap.Marker(r.point);
            map.addOverlay(mk);
            map.panTo(r.point);
            updatePos(r.point.lng,r.point.lat);
            var pt = new BMap.Point(r.point.lng,r.point.lat);

            var geoc = new BMap.Geocoder();

            geoc.getLocation(pt, function(rs){
                var addComp = rs.addressComponents;
                var province = addComp.province;
                if(addComp.province==addComp.city){
                    province = "";
                }
                $(".adr").text(province + "" + addComp.city + "" + addComp.district + "" + addComp.street + "" + addComp.streetNumber);
                $(".divdz").show();
            });

        }
        else {
            alert('failed'+this.getStatus());
        }
    },{enableHighAccuracy: true})


}
//短视频
function fenshi(){
	
	     var u = navigator.userAgent;
         var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
         var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端

    	  if(isAndroid==true){
        	  Android.showVideoListForAndroid();
          }else if(isiOS==true){
              var url= "func=" + 'showVideoListForIOS';
              window.webkit.messageHandlers.Native.postMessage(url);
          }
    	  
 }

function nav(){
	
	
	  var u = navigator.userAgent;
      var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
      var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端

      var ua = navigator.userAgent.toLowerCase();
      var isWeixin = ua.indexOf('micromessenger') != -1;
      if (!isWeixin) {
    	  if(isAndroid==true){
    		  $(".jmsj").hide();
     		  $(".fenshi").show();
    		
          }else if(isiOS==true){
        	  $(".jmsj").show();
     		  $(".fenshi").hide();
          }
    	  
      }else{
    	  //微信端
    	  $(".jmsj").show();
 		  $(".fenshi").hide();
      }
    
}
