$(function() {


    load();
    $(window).scroll(function () {

        var Height = $(window).height();
        var scroll = $(document).scrollTop(); //滚动高度


        if(scroll>115){
            $(".sec-nav").addClass("nav");
        }else{
            $(".sec-nav").removeClass("nav");
        }

        var Top = $(".last").offset().top; //元素距离顶部距离


        if (Top - Height - scroll <= 50) {
            if (pageNumber < pageCount) {
                load();
            }
        }

    })
    $(document).on("click",".pro-div",function(event){
        var ppid = $(this).attr("id");
        var goodsid = $(this).find(".goodsid").text();
        var companyId = $(this).find(".companyId").text();
        var ccompanyId = $(this).find(".ccompanyId").text();
        document.location.href=basePath+"/ea/wfjshop/ea_doodsDetail.jspa?ppid="+ppid+"&goodsid="+goodsid+"&companyId="+companyId+"&ccompanyId="+ccompanyId;
    });
});
var array = new Array();
var arraypro = new Array();

function load() {
    pageNumber = pageNumber + 1;


    var ulp = basePath
        + "ea/productslaunch/sajax_ea_getVipList.jspa";
    $.ajax({
        type: "GET",
        url: ulp,
        async: true,
        dataType: "json",
        data: {
            pageNumber: pageNumber
        },
        success: function (data) {
            if (data == null) {
                return false;

            }
            var member = eval('(' + data + ')');
            var pageForm = member.pageForm;
            var cxlist = member.cxlist;
            var html = new Array();


            pageNumber = pageForm.pageNumber;
            pageCount = pageForm.pageCount;
            $(".last").removeClass("last");
            var obj = "";
            var arrayList = new Array();
            if (pageForm.list != null && pageForm.list.length > 0) {


                for (var j = 0; j < pageForm.list.length; j++) {
                    var obj = pageForm.list[j];
                    var companyId = obj[6];


                    if (array.indexOf(companyId) == -1) {//不包含
                        array.push(companyId);

                        html.push("<div class='mm' id='"+companyId+"'>");

                        html.push("<div class='left'><div><img src = '" + basePath + obj[9]+"'  onerror='this.src=\"" + basePath + "/images/ea/production/forum/reportAnError.png\"'></div></div>");
                        html.push("<div class='right'>");
                        html.push("<div class='com'>" + obj[7] + "</div>");

                        $(pageForm.list)
                            .each(
                                function (i, dom) {

                                    if (companyId == this[6]) {
                                        var ppid = this[1];
                                        if (arraypro.indexOf(ppid) == -1) {
                                            arraypro.push(ppid);
                                            if ($(pageForm.list).length - 1 == i) {
                                                html.push("<div id='" + this[1] + "' class='last pro-div'>");
                                            } else {
                                                html.push("<div  id='" + this[1] + "' class='pro-div'>");
                                            }

                                            html.push("<span style='display:none' class='goodsid'>"+this[5]+"</span>");
                                            html.push("<span style='display:none'  class='companyId'>"+this[6]+"</span>");
                                            html.push("<span style='display:none' class='ccompanyId'>"+this[8]+"</span>");


                                            html.push("<ul class='pro'>");

                                            html.push("<li><span class='zpro'>" + this[0] + "</span><span class='zprice'>￥" + this[2] + "</span></li>");
                                            html.push("<ul class='zp'>");

                                            for(var x = 0;x<cxlist.length;x++){
                                                var  cx = cxlist[x];
                                                if (ppid == cx[3]) {
                                                    html.push("<li><span class='zpspan'>赠" + cx[0] + "</span><span>￥" + cx[4] + "</span></li>")
                                                }
                                            }

                                            html.push("</ul>");
                                            html.push("</ul>");
                                            html.push("</div>");

                                        }

                                    }
                                })

                        html.push("</div>");
                        html.push("</div>");
                    }else{
                        //把包含的还筛选出来
                        arrayList.push(obj);

                    }



                }
                $(".content").append(html.join(""));
            }
            if(pageNumber>1) {
                var html1 = new Array();
                for (var k = 0; k < arrayList.length; k++) {
                    var obj1 = arrayList[k];
                    var companyId = obj1[6];
                    var ppid = obj1[1];

                    if (arraypro.indexOf(ppid) == -1) {
                        $(".last").removeClass("last");
                        html1.push("<div  id='" + ppid + "' class='last pro-div'>");

                        html1.push("<span style='display:none' class='goodsid'>" + obj1[5] + "</span>");
                        html1.push("<span style='display:none'  class='companyId'>" + obj1[6] + "</span>");
                        html1.push("<span style='display:none' class='ccompanyId'>" + obj1[8] + "</span>");


                        html1.push("<ul class='pro'>");

                        html1.push("<li><span class='zpro'>" + obj1[0] + "</span><span class='zprice'>￥" + obj1[2] + "</span></li>");
                        html1.push("<ul class='zp'>");

                        for (var x = 0; x < cxlist.length; x++) {
                            var cx = cxlist[x];
                            if (ppid == cx[3]) {
                                html1.push("<li><span class='zpspan'>赠" + cx[0] + "</span><span>￥" + cx[4] + "</span></li>")
                            }
                        }

                        html1.push("</ul>");
                        html1.push("</ul>");
                        html1.push("</div>");

                    }


                    $("#" + companyId).find(".right").append(html1.join(""));
                }
            }

        },
        error: function (data) {
            console.log("失败");
        }
    });


}


