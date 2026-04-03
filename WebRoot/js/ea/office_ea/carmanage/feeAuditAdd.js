
var id = "";
var u = window.navigator.userAgent;
var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
$(function() {
    site();
    $(".close-tingyong").click(function(){
        $(this).parents(".div-tingyong").hide();
        window.history.back();

    })

    $(".close-confirm").click(function(){
        $(this).parents(".div-tingyong").hide();
        window.history.back();

    })





    $(".saveDraft").click(function(){


        if($.trim($("#carNumber").val())==""){
            $(".div-tingyong").show();
            $(".titlep").text("请填写车牌号");
            return false;
        }

          $("#siteName").val($(".site").find("option:selected").text());
        $("#form").attr("target", "hidden").attr("action",
            basePath + "ea/carmanage/ea_addFeeCar.jspa");

        document.form.submit.click();
        token = 13;
    });
    


});

//场地
function site(){




    var url = basePath + "ea/carmanage/sajax_ea_queryNumber.jspa";
    $.ajax({
        url : url,
        type : "post",
        async : true,
        dataType : "json",
        success : function(data) {
            var standard = eval("(" + data + ")");
            var nologin = standard.nologin;
            var number = standard.number;
            if(nologin){
                document.location.href = basePath + "/page/WFJClient/pc/pc_login.jsp";
            }

            if (standard!=null) {
                $(".siteName").attr({ readonly: 'true' });

                var a = [];
                $(number).each(function(i, dom) {
                    a.push("<option date-siteId='"+this[1]+"' date-siteName='"+this[2]+"' value='"+this[1]+"'>"+this[2]+"</option>");

                });
                $(".site").append(a.join(""));



            }
        },
        error : function(data) {
            alert("获取失败");
        }
    });
}

function re_load() {

    $(".div-tingyong").show();
    $(".titlep").text("操作成功");
    return false;
}


