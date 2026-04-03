
var id = "";
var u = window.navigator.userAgent;
var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
$(function() {

 
    $("#text").click(function(){

        var fileType = $("#fileType").val();
        var ext;
        if (fileType == "W") {
            ext = ".doc";
        } else {
            ext = ".xls";
        }
        $("#text").text("已编辑");



    });

    $("#iframe").attr("height",$(window).height());
    //选择模板分类
    $("#specificTemplate").click(function(){
    //    $("#iframe").attr("src",basePath+"/ea/androiddoc/ea_getDocTempTypeList.jspa?pos=add");
        $(".iframecom").show();

    });


    $(".close-tingyong,.close-confirm").click(function(){
        if($(".titlep").text()=="操作成功"){
            if (isAndroid == true || isiOS == true) {
                if (ifr=="true") {

                    var defaults = "";
                    if (module == "doc") {
                        defaults = "gwmb";
                    } else if (module == "contract") {
                        defaults = "htmb";

                    }
                    document.location.replace(basePath + "/page/WFJClient/pc/5l5C/office/templateManage.jsp?defaults=" + defaults);

                }else{
                    // 当前页面不是通过parent.location.href打开的
                    window.history.back();
                }
            }else{
                window.opener.location.reload(); // 刷新父页面
                window.close(); // 关闭当前窗口
            }




        }else {
            $(this).parents(".div-tingyong").hide();
        }
    })




    //文件格式
    $(".div-doctype li").click(function(){


        $(this).parents(".div-doctype").hide();
        $(this).parents(".div-yinzhang").hide();
        $(this).siblings().removeClass("active");
        $(this).addClass("active");
        $(".p-docType").text($.trim($(this).text()));
        $("#fileType").val($.trim($(this).attr("data-value")));
        var fileType = $.trim($(this).attr("data-value"));
        var sysSet = $("#sysSet").val();
        var templateTypeName = $("#specificTemplate").val();
        var temptId = $("#temptId").val();
        var fileShowName = $("#fileShowName").val();

        var url = basePath + "ea/zoffice/sajax_ea_createBlankOffice.jspa?date="
            + new Date().toLocaleString();
        $.ajax({
            url : encodeURI(url),
            type : "get",
            dataType : "json",
            async : false,
            data : {
                fileType : fileType,
                temp : "temp"
            },
            success : function(data) {
                var jsonresult = eval("(" + data + ")");
                var docPath = jsonresult.result;

                document.location.href = basePath+"page/ea/main/office_ea/contract/tempAdd.jsp?opr=edit&docPath="+docPath+"&fileType="+fileType+"&sysSet="+sysSet+"&templateTypeName="+templateTypeName+"&temptId="+temptId+"&fileShowName="+fileShowName;

            },
            error : function(data) {
                alert("创建模板失败");
            }

        });

    })
    $("#div-docType").click(function(){
        if(opr=="update"){
            return false;
        }
        $(".div-doctype").show();
    })


//提交审核
    $(".saveDraft").click(function(){



       if ($.trim($("#specificTemplate").val()) == "") {
                $(".div-tingyong").show();
                $(".titlep").text("请选择模板分类");
                return false;
       }

        if($.trim($("#fileShowName").val())==""){
            $(".div-tingyong").show();
            $(".titlep").text("请填写模板名称");
            return false;
        }



        if(opr=="bd") {

            if ($("#imgSdf").attr("src").indexOf("images/ea/office/contract/uattach.png") != -1) {
                $(".div-tingyong").show();
                $(".titlep").text("请上传模板文件");
                return false;
            }
        }else  if(opr=="edit") {
            if($("#text").text()=="编辑正文"){

                $(".div-tingyong").show();
                $(".titlep").text("请编辑正文");
                return false;
            }
        }


        $("#form").attr("target", "hidden").attr("action",
            basePath + "ea/androiddoc/ea_addDocTemp.jspa");

        document.form.submit.click();
        token = 13;



    });
    



});

function re_load(){
    $(".div-tingyong").show();
    $(".titlep").text("操作成功");
    return false;

}


