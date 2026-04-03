$(function() {

    //选择品类
    $(document).on("click",".content>p:first-of-type a:nth-of-type(2)",function(){
        if($("#category").is(":hidden")){
            leftProCate();
            $(".content>section:first-of-type").hide();
            $(".content>p:first-of-type a:first-of-type img").removeClass("active");
            $(".content>p:first-of-type a:nth-of-type(2) img").addClass("active");
            $("#position").hide();
            $("#category").show();
        }else{
            $(".content>section:first-of-type").show();
            $(".content>p:first-of-type a:nth-of-type(2) img").removeClass("active");
            $("#category").hide();
        }
    })

    //选好品货关闭弹框
    $(document).on("click","#category>menu:last-of-type>li ul li,#category>menu:last-of-type>li h3",function(){
        cateCrit = $(this).attr("class");
        $(".content>p:first-of-type>a:nth-of-type(2) span").text($(this).text());
        $(".content>p:first-of-type>a:nth-of-type(2)").addClass("active");
        $(".content>section:first-of-type").show();
        $("#category").hide();
        $(".content>p:first-of-type a:nth-of-type(2) img").removeClass("active");

        search();
    })
    //货品全部
    $(document).on("click","#category>menu:last-of-type>li p",function(){
        cateCrit = "pcf"+$(this).parents("#category").find("li.active").attr("id");
        var txt=$(this).parents("#category").find("li.active").text();
        $(".content>p:first-of-type>a:nth-of-type(2) span").text(txt);
        $(".content>p:first-of-type>a:nth-of-type(2)").addClass("active");
        $(".content>section:first-of-type").show();
        $("#category").hide();
        $(".content>p:first-of-type a:nth-of-type(2) img").removeClass("active");
        search();
    })
    //选择品货选中状态
    $(document).on("click","#category menu:first-of-type li",function(){
        $(this).parent().find("li").removeClass("active");
        $(this).addClass("active");
        if($(this).text()=="全部"){
        	 $(".content>p:first-of-type>a:nth-of-type(2) span").text("销售品类");
             $(".content>p:first-of-type>a:nth-of-type(2)").addClass("active");
             $(".content>section:first-of-type").show();
             $("#category").hide();
             $(".content>p:first-of-type a:nth-of-type(2) img").removeClass("active");
             cateCrit = "";
      }
        rightProCate($(this).attr("id"));

    })


    //搜索按钮还原进入页面状态
    $("input[type='search']").click(function(){
        $(".content>section:first-of-type").show();
        $(".content>p:first-of-type a:first-of-type img").removeClass("active");
        $(".content>p:first-of-type a:nth-of-type(2) img").removeClass("active");
        $("#position").hide();
        $("#category").hide();
    })

    $("input[type='search']").on("input", function () {
        search();
    })

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //选择排序关闭位置以及省份
    $(document).on("click",".content>p:first-of-type a:not(:nth-of-type(2),:first-of-type)",function(){
        $(".content>p:first-of-type a:nth-of-type(2) img").removeClass("active");
        $(".content>section:first-of-type").show();
        $("#category").hide();
        $("#position").hide();
        $(this).toggleClass("active");
        var tab = $(this).attr("id");
        if($(this).attr("class")=="active"){
             if(tab=="disCrit"){
                  disCrit = "active";
             }
             if(tab=="saleCrit"){
                 saleCrit = "active";
             }
        }else{
            if(tab=="disCrit"){
                disCrit = "";
            }
            if(tab=="saleCrit"){
                saleCrit = "";
            }
        }
        search();

    })
    //选择地区
    $(document).on("click",".content>p:first-of-type a:first-of-type",function(){
    	
        if($("#position").is(":hidden")){
            provice();
            position();
            $(".content>section:first-of-type").hide();
            $("#category").hide();
            $("#position").show();
            $(".content>p:first-of-type a:first-of-type img").addClass("active");
            $(".content>p:first-of-type a:nth-of-type(2) img").removeClass("active");
        }else{
            $(".content>section:first-of-type").show();
            $(".content>p:first-of-type a:first-of-type img").removeClass("active");
            $("#position").hide();
        }
    })

    //选择地区省份选中状态
    $(document).on("click","#position>menu:first-of-type li",function(){
        $(this).parent().find("li").removeClass("active");
        $(this).addClass("active");
       
        if($(this).attr("id")=="qg"){
        	  $(".content>p:first-of-type>a:first-of-type span").text($(this).text());
              $(".content>p:first-of-type>a:first-of-type").addClass("active");
              $(".content>p:first-of-type a:first-of-type img").removeClass("active");
              $(".content>section:first-of-type").show();
            $("#position").hide();
            placeCrit = "";
            search();
        }
        city($(this).attr("id"));

    });

    //选好地区关闭弹框
    $(document).on("click","#position>p>span,#position>menu:last-of-type li",function(){
        placeCrit = $(".provicem").find(".active").text()+$(this).text();
        $(".content>p:first-of-type>a:first-of-type span").text($(this).text());
        $(".content>p:first-of-type>a:first-of-type").addClass("active");
        $(".content>p:first-of-type a:first-of-type img").removeClass("active");
        $(".content>section:first-of-type").show();
        $("#position").hide();
        search();
    });

    loaded();
    
    
    //进入店铺
    $(document).on("click",".busiul li",function(){
    	var companyid = $(this).attr("data-comid");
    	var ccompanyID = $(this).attr("id");
    	var companyName = $(this).find(".comname").text();
    	document.location.href = basePath+"ea/wholesaleMall/ea_toWholesaleMall.jspa?companyid="+companyid+"&ccompanyID="+ccompanyID+"&companyName="+companyName+"&phl=phl";
    	
    });


});





//左边分类
function leftProCate(){
    if($.trim($(".leftmenus").html()).length!=0){
        return false;
    }
    var url = basePath+"ea/phl/sajax_ea_getProCate.jspa";
    $.ajax({
        url : url,
        type : "get",
        async : true,
        dataType : "json",
        data:{
            codePID:"scode20190415raqvqk3uvs0000000762"
        },
        success : function(data) {
            var member = eval("(" + data + ")");
            var catelist   = member.catelist;

            var htmlstr=new Array();
            htmlstr.push("<li>全部</li>");
            var obj;
            if(catelist!=null){
                for ( var i = 0; i <  catelist.length; i++) {

                    obj = catelist[i];
                    if(i==0){
                        htmlstr.push("<li id='"+obj[1]+"' class='last active'>");

                    }else{
                        htmlstr.push("<li id='"+obj[1]+"'>");
                    }
                    htmlstr.push(obj[0]);
                    htmlstr.push("</li>")


                }
                $(".leftmenus").html(htmlstr.join(""));
                var codePID = $(".leftmenus").find(".active").attr("id");
                rightProCate(codePID);

            }

        },
        error:function(data){
            console.log("获取左边分类失败");
        }
    });

}

//右边分类
function rightProCate(codePID){
    var url = basePath+"ea/phl/sajax_ea_getProCate.jspa";
    $.ajax({
        url : url,
        type : "get",
        async : true,
        dataType : "json",
        data:{
            codePID:codePID
        },
        success : function(data) {
            var member = eval("(" + data + ")");
            var catelist   = member.catelist;
            if(catelist!=null){
                var htmlstr=new Array();
                htmlstr.push("<li>");
                htmlstr.push("<p class='"+codePID+"'>全部</p>");
                htmlstr.push("</li>");
                htmlstr.push("<li>");
                htmlstr.push("<ul class='clearfix'>");
                var obj;
                for ( var i = 0; i <  catelist.length; i++) {

                    obj = catelist[i];

                    htmlstr.push("<li class='"+obj[1]+"'>"+obj[0]+"</li>");


                }
                htmlstr.push("</ul></li>");
                $(".rightmenus").html(htmlstr.join(""));

            }

        },
        error:function(data){
            console.log("获取右边分类失败");
        }
    });

}

//省份
function provice(){
    if( $.trim($(".provicem").html()).length!=0){
        return false;
    }
    var url = basePath+"ea/phlbusi/sajax_ea_getCDistricts.jspa";
    $.ajax({
        url : url,
        type : "get",
        async : true,
        dataType : "json",
        success : function(data) {
            var member = eval("(" + data + ")");
            var distinctlist   = member.distinctlist;
            if(distinctlist!=null){
                var htmlstr=new Array();
                var obj;
                htmlstr.push("<li id='qg'>");
                htmlstr.push("全国");
                htmlstr.push("</li>");
                for ( var i = 0; i <  distinctlist.length; i++) {

                    obj = distinctlist[i];
                    if(i==0){
                        htmlstr.push("<li id='"+obj.districtID+"' class='active'>");

                    }else{
                        htmlstr.push("<li id='"+obj.districtID+"'>");
                    }
                    htmlstr.push(obj.districtName);
                    htmlstr.push("</li>")


                }
                $(".provicem").html(htmlstr.join(""));
                var districtPID = $(".provicem").find(".active").attr("id");
                city(districtPID);

            }

        },
        error:function(data){
            console.log("获取省份失败");
        }
    });

}


//城市
function city(districtPID){
    var url = basePath+"ea/phlbusi/sajax_ea_getCityDistricts.jspa";
    $.ajax({
        url : url,
        type : "get",
        async : true,
        dataType : "json",
        data:{
            districtPID:districtPID
        },
        success : function(data) {
            var member = eval("(" + data + ")");
            var distinctlist   = member.distinctlist;
            if(distinctlist!=null){
                var htmlstr=new Array();
                var obj;
                for ( var i = 0; i <  distinctlist.length; i++) {

                    obj = distinctlist[i];
                    htmlstr.push("<li id='"+obj.districtID+"'>");
                    htmlstr.push(obj.districtName);
                    htmlstr.push("</li>")

                }
                $(".citym").html(htmlstr.join(""));

            }


        },
        error:function(data){
            console.log("获取城市失败");
        }
    });

}
function getHeight(){

    if($(".last").length>0){
        if($(".last").offset().top-$(document).scrollTop()<=$(window).height()){

            if(pagenumber<pagecount){
                loaded();
            }else{
                clearInterval(t);
            }
        }
    }

}
//商家
function loaded(){
    pagenumber += 1;
    var url = basePath+"ea/phlbusi/sajax_ea_findBusiCompany.jspa";
    $.ajax({
        url : url,
        type : "get",
        async : false,
        data : {
            "pageForm.pageNumber":pagenumber,
            companyMID:companyMID,
            companyName:$("#search").val(),
            placeCrit:placeCrit,
            cateCrit:cateCrit,
            disCrit: disCrit,
            saleCrit:saleCrit
        },
        dataType : "json",
        success : function(data) {
            var member = eval("(" + data + ")");
            var pageForm = member.pageForm;
            var pmap = member.pmap;


            if(pageForm!=null){
                var htmlstr=new Array();
                var obj;
                pagecount = pageForm.pageCount;
                $(".last").removeClass("last");
                for ( var i = 0; i <  pageForm.list.length; i++) {
                    obj = pageForm.list[i];
                    if(i==pageForm.list.length-1){
                        htmlstr.push("<li class='last clearfix' data-comID='"+obj[5]+"' id='"+obj[0]+"'>");
                    }else{
                        htmlstr.push("<li class='clearfix' data-comID='"+obj[5]+"'  id='"+obj[0]+"'>");

                    }
                    htmlstr.push("<div class='clearfix'>");
                  
                     htmlstr.push("<img src='" + basePath + obj[1]+"' onerror='this.src=\""+ basePath +"/images/ea/production/forum/reportAnError.png\"'/>");

                   
                     htmlstr.push("<p>"+obj[2]+"<span>"+(obj[6]==null?"":obj[6]+"km")+"</span>"+"</p>");
                    htmlstr.push("<p class='comname'>"+obj[3]+"</p>");
                   
                    htmlstr.push("<p><img src='"+basePath+"images/ea/collage/phl/location.png'>"+obj[4]+"</p>");
                    htmlstr.push("</div>");
                    htmlstr.push("<div class='clearfix divvs'>");
                    $.each(pmap,function(key,values){
                    	if(key==obj[5]){
                    		  for ( var i = 0; i <  values.length; i++) {
                    			  
                    	       htmlstr.push("<img src='"+basePath+values[i][0]+"'  onerror='this.src=\""+ basePath +"/images/ea/production/forum/reportAnError.png\"'/>");
                    		  }
                    	}
                    });
                    htmlstr.push("</div>");
                    htmlstr.push("</li>");

                }
                $(".busiul").append(htmlstr.join(""));

                if(pagenumber==1&&pagecount>pagenumber){
                    t=setInterval("getHeight()", 200);
                }
            }


        },
        error:function(data){
            console.log("加载下一页失败");
        }
    });

}
//条件查询加载
function search(){
    pagecount = 0;
    pagenumber = 0;
    if(t!="") {
        clearInterval(t);
    }
    $(".busiul").html("");
     loaded();
}

function calliosMapInfo(name){
    if(name!="-1"){
        var a = name.split(",");
      var  city = a[0];//所在城市
      var  eastLongitude = a[1];//东经
      var  northLatitude = a[2];//北纬
        $("#city").text(city);
    }else{
    	 $("#city").text("未知");
    }
}

//定位
function position(){

    try {
    	   var u = window.navigator.userAgent;
    	    var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
    	    var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
    	    if (isAndroid == true) {
             
                var collection = Android.callgetLocal();//调用安卓接口
                if(collection!="-1"){
                    var a = collection.split(",");
                  var  city = a[0];//所在城市
                 var   eastLongitude = a[1];//东经
                 var   northLatitude = a[2];//北纬
               
                    $("#city").text(city);
                }else{
                    $("#city").text("未知");
                }
            } else if (isiOS == true) {
                console.log("IOS");
                var url= "func=" + 'calliosMapInfo';
                window.webkit.messageHandlers.Native.postMessage(url);
            }
    } catch (e) {
       
    }
}