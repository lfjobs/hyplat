$(function() {

    var menuHei=$(window).height()-$("header").outerHeight();
    $(".content menu").height(menuHei);
    $(document).on("click",".content menu:first-of-type li",function(){
        $(this).parent().find("li").removeClass("active");
        $(this).addClass("active");
        rightProCate($(this).attr("id"));

    })
    $(".li_bor").parent().addClass("liBor");

    leftProCate();
});





//左边分类
function leftProCate(){
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
            var obj;
            if(catelist!=null){
                for ( var i = 0; i <  catelist.length; i++) {

                    obj = catelist[i];
                    if(i==0){
                        htmlstr.push("<li id='"+obj[1]+"' class='active'>");

                    }else{
                        htmlstr.push("<li id='"+obj[1]+"'>");
                    }
                    htmlstr.push(obj[0]);
                    htmlstr.push("</li>")


                }
                $(".leftmenu").append(htmlstr.join(""));
                var codePID = $(".leftmenu").find(".active").attr("id");
                rightProCate(codePID);

            }

        },
        error:function(data){
            console.log("获取左边分类失败");
        }
    });

}

//左边分类
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
                $(".rightmenu").html(htmlstr.join(""));

            }

        },
        error:function(data){
            console.log("获取右边分类失败");
        }
    });

}
