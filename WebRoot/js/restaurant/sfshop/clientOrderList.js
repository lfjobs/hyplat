$(function(){
    getHeight();
      //查看
    $(document).on("click",".pay_wrap",function(){
      var coId = $(".coId").val();
      document.location.href = basePath+"ea/assicode/ea_getClientOrderDetail.jspa?coID="+coId;
    });
    //结算
    $(document).on("click",".pay_btn",function(event){
        event.stopPropagation();
        var coId = $(this).parents(".pay_wrap").find(".coId").val();
        var companyId = $(this).parents(".pay_wrap").find(".companyId").val();
        document.location.href = basePath+"ea/assicode/ea_genJieSunCode.jspa?companyId="+companyId+"&coID="+coId;
    });
    //加单
    $(document).on("click",".menu_add_btn",function(event){
        event.stopPropagation();
        var coId = $(this).parents(".pay_wrap").find(".coId").val();
        var ccompanyId = $(this).parents(".pay_wrap").find(".ccompanyId").val();
       document.location.href = basePath+"ea/assicode/ea_getCompanyProList.jspa?ccompanyId="+ccompanyId+"&coID="+coId;
    });


});
function getHeight(){
    if($(".pay_wrap").length>0) {
        height = parseInt(Math.abs($(".non_payment").height() - ($(window).height() - $(".non_payment").offset().top)));
        t = setTimeout("getHeight()", 200);
        if (height < $(window).height()) {
            if (pagenumber < pagecount) {
                loaded();
            }
        }
    }
}

function loaded () {

    pagenumber += 1;
    var industryType="";
    var companyAddr="";
    var url= basePath+"ea/assicode/sajax_ea_getClientOrderList.jspa";

     $.ajax({
        url : url,
        type : "get",
        async : true,
        dataType : "json",
        data:{
            "pageForm.pageNumber":pagenumber,
             type:"ajax",
             state:state
        },
        success : function (data) {
            var member = eval("(" + data + ")");
            var pageForm = member.pageForm;

            if(pageForm!=null&&pageForm.recordCount>0){
                var clientlist =pageForm.list;
                pagecount=pageForm.pageCount;
                pagenumber=pageForm.pageNumber;
                for(var i = 0; i < clientlist.length; i++){
                    var str = new Array();
                    var item = clientlist[i];
                    str.push("<a href='###' class='pay_wrap'><input class='coId' type='hidden' value='"+item[0]+"'>");
                    str.push("<input class='companyId' type='hidden' value='"+item[3]+"'>");
                    str.push("<input class='ccompanyId' type='hidden' value='"+item[7]+"'>");
                    str.push("<img src='"+basePath+(item[4]!=null?item[4]:'images/WFJClient/PersonalJoining/logo@2x.png')+"'class='s_logo' alt=''>");
                    str.push("<div class='pay_box'>");
                    str.push("<div class='pay_line'>");
                    str.push("<span class='pay_name'>"+item[5]+"</span>");
                    str.push("<span class='pay_state "+(state=='00'?"paid":"no_pay")+"'>"+(state=="01"?"待结算":"已结算")+"</span>");
                    str.push("</div>");
                    str.push("<div class='pay_line'>");
                    str.push("<span class='pay_num'><b>"+item[2]+"</b></span>");
                    str.push("<span class='pay_ordernum'>订单号："+item[1]+"</span>");
                    str.push("</div>");
                    str.push("<div class='pay_line'>");
                    str.push("<span class='pay_pirce'>￥"+item[6]+"</span>");
                   if(state=="01") {
                       str.push("<span class='pay_operation clearfix'>");
                       str.push("<span class='menu_add_btn'>加单</span>");
                       str.push("<span class='pay_btn'>结算</span>");
                       str.push("</span></div></div></a>");
                  }
                    $(".non_payment").append(str.join(""));
                }

            }

            getHeight();
        },
        error:function(data){
            //alert("获取项目失败");
        }
    });
}