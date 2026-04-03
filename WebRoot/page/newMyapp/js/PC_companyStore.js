$(document).ready(function () {

    loaded();
    bestSeller();

    $(document).on("click",".product li",function(){
        ppid = $(this).find(".ppID").val();
        var url=basePath+"/ea/newpcend/ea_companyWebsite.jspa?titleJudge=01&seven=01&ccompanyId="+ccompanyId+"&ppk.ppID="+ppid;
        window.open(url);
    });



    $(document).on("click",".hot_shop li",function(){
        ppid = $(this).find(".ppID").val();
        var url=basePath+"/ea/newpcend/ea_companyWebsite.jspa?titleJudge=01&seven=01&ccompanyId="+ccompanyId+"&ppk.ppID="+ppid;
        window.open(url);
    });

})

function loaded () {
    var search = $(".shop-sort .active").attr("data-name");
    var url=basePath+"ea/industry/sajax_ea_getAjaxPorductsList.jspa?";
    $.ajax({
        url : url,
        type : "post",
        async : true,
        dataType : "json",
        data:{
            "pageForm.pageNumber":pageNumber,
            "ccompanyId":ccompanyId,
            "search":search,
            "ppId":ppIds
        },
        success : function cbf(data) {
            var member = eval("(" + data + ")");
            var pageForm = member.pageForm;
            var product=[];
            $(".mil_shop_con").find(".product").empty();
            $(".shop_page").empty();
            if (pageForm != null && pageForm.list != null
                && pageForm.list.length > 0) {
                $(pageForm.list).each(function(i, dom) {
                    product.push("<li>");
                    product.push("<input type='hidden' class='ppID' value='"+this[1]+"'/>");
                    product.push("<input type='hidden' class='companyID' value='"+this[5]+"'/>");
                    product.push("<input type='hidden' class='type' value='"+this[6]+"'/>");
                    product.push("<img src='"+basePath+this[3]+"' alt='' class='top'>");
                    product.push("<div class='bottom'>");
                    product.push("<h3>&yen;<span>"+this[2]+"</span></h3>");
                    product.push("<p class='text'>"+this[0]+"</p>");
                    product.push("</div></li>");
                })
                $(".mil_shop_con").find(".product").append(product.join(""));
                pageCount = pageForm.pageCount;
                pageNumber = pageForm.pageNumber;
                var paging = [];
                paging.push("<button style='float: left;' onclick='lastStep()'><img src='"+basePath+"page/newMyapp/images/newPCHomepage/page-left.png'></button><ul>");
                for ( var i = 1; i <= pageCount; i++) {
                    if(pageNumber==i){
                        paging.push("<li onclick='choose(this)' class='active'>"+i+"</li>");
                    }else{
                        if(i==1||i==pageCount||(i>=pageNumber-2&&i<=pageNumber+2)){
                            paging.push("<li onclick='choose(this)'>"+i+"</li>");
                        }else if(i!=1&&i!=pageCount&&(i==pageNumber-3||i==pageNumber+3)){
                            paging.push("<li style='border:none;background-color:white;' class='point'><span>...</span></li>");
                        }
                    }
                }
                paging.push("</ul><button style='float: right;' onclick='nextStep()'><img src='"+basePath+"page/newMyapp/images/newPCHomepage/page-right.png'></button>");
                $(".shop_page").append(paging.join(""));
                var nub = $(".mil_shop_con .shop_page ul li").length;
                $(".mil_shop_con .shop_page").css("width",80+40*nub+"px");
            }else{
                product.push("<div class='goodsEmpty' align='center'>");
                product.push("<img src='"+basePath+"page/newMyapp/images/wu.png'>");
                product.push("<p>该行业暂无商品</p>");
                product.push("</div>");
                $(".mil_shop_con").find(".product").append(product.join(""));
            }
        },
        error: function cbf(data){
            alert("产品加载失败");
        }
    });
}
//上一步
function lastStep(){
    if(pageNumber>1){
        pageNumber = parseInt(pageNumber)-1;
        loaded();
    }
}
//下一步
function nextStep(){
    if(pageNumber<pageCount){
        pageNumber = parseInt(pageNumber)+1;
        loaded();
    }
}
//选取
function choose(obj){
    pageNumber = $(obj).text();
    loaded();
}

//热卖
function bestSeller(){
    var url=basePath+"ea/industry/sajax_ea_getAjaxPorductsList.jspa?";
    $.ajax({
        url : url,
        type : "post",
        async : true,
        dataType : "json",
        data:{
            "pageForm.pageNumber":1,
            "search":"pop",
            "ccompanyId":ccompanyId,
            "ppId":ppIds
        },
        success : function(data) {
            var member = eval("(" + data + ")");
            var pageForm = member.pageForm;
            var product = [];
            $(".hot_shop").find("ul").empty();
            if (pageForm != null && pageForm.list != null
                && pageForm.list.length > 0) {
                product.push("<h3 class='title'>热卖商品</h3>");
                $(pageForm.list).each(function(i, dom) {
                    if(i<5){
                        product.push("<li>");
                        product.push("<input type='hidden' class='ppID' value='"+this[1]+"'/>");
                        product.push("<input type='hidden' class='companyID' value='"+this[5]+"'/>");
                        product.push("<input type='hidden' class='type' value='"+this[6]+"'/>");
                        product.push("<img src='"+basePath+this[3]+"' alt='' class='top'>");
                        product.push("<div class='bottom'>");
                        product.push("<h3>&yen;<span>"+this[2]+"</span></h3>");
                        product.push("<p class='text'>"+this[0]+"</p>");
                        product.push("</div></li>");
                    }
                })
                $(".hot_shop").find("ul").append(product.join(""));
            }
        },
        error: function(){
            /*alert("产品加载失败");*/
        }
    });
}
