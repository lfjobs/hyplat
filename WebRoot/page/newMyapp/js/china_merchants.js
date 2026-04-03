$(document).ready(function() {

    //获取城市经济平台
    ajax();

})


function ajax() {
    var url = basePath + "/ea/newpcend/sajax_ea_UrbanEconomy.jspa?";
    $.ajax({
        url : url,
        type : "post",
        async : true,
        dataType : "json",
        success : function(data) {
            var ccode = eval("(" + data + ")");
            var temporary1 = [];
            var temporary2 = [];
            if (ccode != null && ccode.list!=null && ccode.list.length>0) {
                //经济平台
                var a;
                var b;
                $(ccode.list).each(function(i, dom) {
                    temporary1.push("<li>");
                    if(this.codeValue.indexOf("省")>=0){
                        a = "page/newMyapp/images/newPCHomepage/ico-sheng.png";
                        b = "page/newMyapp/images/newPCHomepage/sheng.png";
                    }else if(this.codeValue.indexOf("县")>=0){
                        a = "page/newMyapp/images/newPCHomepage/ico-xian.png";
                        b = "page/newMyapp/images/newPCHomepage/xian.png";
                    }else if(this.codeValue.indexOf("村")>=0){
                        a = "page/newMyapp/images/newPCHomepage/ico-cun.png";
                        b = "page/newMyapp/images/newPCHomepage/cun.png";
                    }
                    temporary1.push("<img src='"+basePath+a+"'>");
                    temporary1.push("<h2>"+this.codeValue+"经济平台</h2>");
                    temporary1.push("<a href='javascript:void(0)' onclick='platformDetails(this)' data-value='"+this.codeValue+"'><input type='button' value='申请加入'></a>");
                    temporary1.push("</li>");

                    //产品代理
                    temporary2.push("<a href='javascript:void(0)' onclick='productAgent(this)' data-value='"+this.codeValue+"'>");
                    temporary2.push("<li>");
                    temporary2.push("<img src='"+basePath+b+"'>");
                    temporary2.push("<p>产品"+this.codeValue.substring(0, 2)+"代理</p>");
                    temporary2.push("</li>");
                    temporary2.push("</a>");

                })
                $(".top").append(temporary1.join(""));
                $(".att_list2").append(temporary2.join(""));
            }
        },
        error : function(data) {
            console.log("获取失败");
        }
    });
}

//经济平台
function platformDetails(obj) {

    var value = $(obj).attr("data-value");

    document.location.href = basePath + "/ea/newpcend/ea_skip.jspa?titleJudge=03&seven=01&hot="+value;

}


//产品代理
function productAgent(obj) {

    var value = $(obj).attr("data-value");

    document.location.href = basePath + "/ea/newpcend/ea_skip.jspa?titleJudge=03&seven=02&hot="+value;
}