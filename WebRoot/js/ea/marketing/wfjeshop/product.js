// 产品描述及产品规格

$(document).ready(function (e) {
    var clientWidth = document.documentElement.clientWidth;
    //console.log(clientWidth);
    //通过屏幕宽度去设置不同的后台根字体的大小
    //document.getElementsByTagName('html')[0].style.fontSize = clientWidth/16+'px';
    if(clientWidth>=960){
        $("body").attr("style", "width:60%;margin: 0 20% 0 20%;height:" + $(window).height() + "px;")
        $("footer").attr("style", "width:60%;");

    }else {
        $("body").attr("style", "width:" + $(window).width() + "px;height:" + $(window).height() + "px;")
    }

    //产品规格、仓库头部
    $(".product_size,.depotManage_div,.div-div-iframe").find(".top").attr("style", "height:" + $(window).height() * 0.07 + "px;line-height:" + $(window).height() * 0.07 + "px;");
    $(".product_size,.depotManage_div,.div-div-iframe").find(".top").find("li").attr("style", "width:15%;font-size:" + $(window).height() * 0.02 + "px;");
    $(".product_size,.depotManage_div,.div-div-iframe").find(".top").find("li").eq(0).find("img").attr("style", "height:" + $(window).height() * 0.01 + "px;");
    $(".product_size").find(".top").find("li").eq(1).attr("style", "width:70%;font-size:" + $(window).height() * 0.025 + "px; letter-spacing:4px; ");
    $(".depotManage_div").find(".top").find("li").eq(1).attr("style", "width:70%;font-size:" + $(window).height() * 0.025 + "px; letter-spacing:4px; ");

    //产品描述头部
    $(".product_describe").find(".top").attr("style", "height:" + $(window).height() * 0.07 + "px;line-height:" + $(window).height() * 0.07 + "px;");
    $(".product_describe").find(".top").find("li").attr("style", "width:15%;font-size:" + $(window).height() * 0.03 + "px;");
    $(".product_describe").find(".top").find("li").find("img").attr("style", "height:" + $(window).height() * 0.04 + "px;");
    $(".product_describe").find(".top").find("li").eq(1).attr("style", "width:70%;font-size:" + $(window).height() * 0.035 + "px; letter-spacing:4px; ");

    $(".footer").attr("style", "height:" + $(window).height() * 0.08 + "px;line-height:" + $(window).height() * 0.08 + "px;");
    $(".product_size article").attr("style", "height:" + $(window).height() * 0.93 + "px;");
    //seze
    $(".product_size").find("section").eq(0).css("border", "none")

    $(".editable").eq(0).css({"height": parseInt($(window).height() - $(".top").height() - $(".footer").height() - "10") + "px"})
    $(".editablesmall").css({"height": $(window).height() * 0.1 + "px"})


    //点击
    /*$(".product_size .size_old li").find("input").click(function(){
     $(this).toggleClass("input_xz")
     })*/
    $(document).on("click", ".product_size .size_old li input", function () {
        $(this).toggleClass("input_xz")
    })
    //删除
    var s = function () {
        $(this).parent().remove()
    };

    /*$(".product_size .size_new").find("img").click(s)*/
    $(document).on("click", ".product_size .size_new img", function (s) {
    })
    //添加
    var a = function () {
        var new_name = "new_" + new Date().getTime();
        $(this).parent().after("<li><input class='seze_edit' placeholder='自定义'></li>")
        $(this).parent().html("<input name='" + new_name + "' class='input_xz' id='" + new_name + "' placeholder='自定义'  ><img class='size_del' src='" + basePath + "images/WFJClient/PersonalJoining/size_del.png'>")

        $(".product_size .size_new").find("img").click(s)

        $(".product_size .seze_edit").focus(a)

        //移出
        /*	$(".product_size .size_new li").each(function() {
         $(this).find("input.input_xz").mouseout(function(){
         if($(this).val() == ""){
         $(this).parent().remove()
         }
         });
         });	*/
    }
    $(document).on("focus", ".product_size .seze_edit", function () {
        a()
    })


    //点击完成跳转发布页面，并传参尺码，颜色
    $(".done").find("button").click(function () {
        colorvalue = "";
        sizevalue = "";
        $(".color").find(".input_xz").each(function () {
            colorvalue += $(this).val() + ",";
        });
        $(".size").find(".input_xz").each(function () {
            sizevalue += $(this).val() + ",,";
        });
        var attrinames = $(".color").find(".sezi_title").val();
        var attrinamec = $(".size").find(".sezi_title").val();
        $("#colorvalue").val(colorvalue);
        $("#sizevalue").val(sizevalue);
        $("#attrinames").val(attrinames);
        $("#attrinamec").val(attrinamec);
        if((colorvalue != null && colorvalue != "") || (sizevalue != null && sizevalue != "")){
            $("#ps").text(colorvalue + sizevalue + "...");
        }else if((colorvalue == null || colorvalue == "") && (sizevalue == null || sizevalue == "")){
            $("#ps").text();
        }

        $(".product_size").hide();
    });
    $(document).on("click", ".editablesmall", function () {
        if ($.trim($(this).text()) == '此处添加文字描述') {
            $(this).find(".moren").text("");
        }
    });

    //多图片上传预览
    $(".foot_g").click(function () {
        d = $(".content").length;
        d = d - 1;
        $("#content" + d).append("<div class='p' id='p" + d + "'>" +
            " <input id='file" + d + "' type='file' name='pic' style='display:none' accept='image/png,image/jpeg,image/gif,image/jpg,image/bmp,image/webp' onchange='setImagePreviews(this.id)'/></div>");
        $("#file" + d).click();
    });

    //点击图片
    $(document).on("click", ".pic img", function () {
        $(this).parent().attr("class", "delpic");
        bgstr = '<div id="ImgZoomInBG" style=" background:#000000; filter:Alpha(Opacity=70); opacity:0.7; position:fixed; left:0; top:0; z-index:10000; width:100%; height:100%; display:none;"><iframe src="about:blank" frameborder="5px" scrolling="yes" style="width:100%; height:100%;"></iframe></div>';
        imgstr = '<img id="ImgZoomInImage2" src="' + $(this).attr("src") + '" data-id="' + $(this).attr("data-id") + '" onclick="quxiao()" style="cursor:pointer; display:none; position:absolute; z-index:10000;" /><img id="deletes" style="position: absolute;display:none;" onclick="Delete()" src="' + basePath + 'images/WFJClient/PersonalJoining/cutout.png"/>';
        if ($('#ImgZoomInBG').length < 1) {
            $('body').append(bgstr);
        }
        if ($('#ImgZoomInImage2').length < 1) {
            $('body').append(imgstr);
        }
        else {
            $('#ImgZoomInImage2').attr('src', $(this).attr('src'));
            $('#ImgZoomInImage2').attr('data-id', $(this).attr('data-id'));
        }
        $('#ImgZoomInImage2').css('left', $(window).scrollLeft() + ($(window).width() - $('#ImgZoomInImage2').width()) / 2);
        $('#ImgZoomInImage2').css('top', $(window).scrollTop() + ($(window).height() - $('#ImgZoomInImage2').height()) / 2);
        $('#delete').css({
            "position": "absolute",
            'right': $(window).scrollLeft() + ($(window).width() - $('#ImgZoomInImage2').width() - $(this).width() * 0.1) / 2,
            'top': $(window).scrollTop() + ($(window).height() - $('#ImgZoomInImage2').height() - $(this).height() * 0.2) / 2,
            "width": "30px",
            "z-index": "10001"
        })
        $('#deletes').css({"position": "absolute", 'right': "50%", 'top': "50%", "width": "30px", "z-index": "10001"})
        $('#deletes').show();
        $('#delete').show();
        $('#ImgZoomInBG').show();
        $('#ImgZoomInImage2').show();
    });
    //返回操作
    $(document).on("click", ".sizereturn", function () {
        $(".product_size").hide();
    });
    $(document).on("click", ".foot_r", function () {
        $(".pic img").each(function () {
            $(this).attr("id", "img" + k + "");
            k++;
        });
        $(".editablesmall p").each(function () {
            if ($.trim($(this).text()) == "此处添加文字描述") {
                $(".editable").find(".editablesmall").text("");
            }
        });
        /*if($("#edit").find("input").length==0&&!checkNull()){
         prompt("请编辑产品描述");
         }else{
         $(".product_describe").hide();
         $("#complete").text("已编辑");
         $("#pt").hide();
         }*/
        if ($("#edit").find("input").length == 0) {
            prompt("请添加图片");
        } else {
            $(".product_describe").hide();
            $("#complete").text("已编辑");
            $("#pt").hide();
        }
    });

    $(document).on("click", ".des", function () {
        $(".product_describe").hide();
    });
});//加载完毕

/*function checkNull(){
 var f=true;
 $(".editablesmall p").each(function(){
 if($("#edit").find("input").length==0&&$.trim($(this).text())==""){
 f=false;
 prompt("请添加图片");
 }
 return false;
 });
 return f;
 }*/
function setImagePreviews(id) {
    //获得input=file对象
    var docObj = document.getElementById(id);
    var fileList = docObj.files;
    for (var i = 0; i < fileList.length; i++) {
        $("#p" + d).append("<div class='pic' style='float:left; width:100%;padding-top:1%;' > <img id='img" + (d + i) + "' data-id='" + id + "'/> </div>");
        //获得该input下一个兄弟节点img
        var imgObjPreview = docObj.parentNode.childNodes[i + 2].childNodes[1];
        if (docObj.files && docObj.files[i]) {
            //火狐下，直接设img属性
            imgObjPreview.style.display = 'block';
            imgObjPreview.style.width = '100%';
            imgObjPreview.style.height = 'auto';
            //火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式
            imgObjPreview.src = window.URL.createObjectURL(docObj.files[i]);
        }
        else {
            //IE下，使用滤镜
            docObj.select();
            var imgSrc = document.selection.createRange().text;
            var localImagId = docObj.parentNode.childNodes[i + 2].childNodes[1];
            //必须设置初始大小
            localImagId.style.width = "100%";
            localImagId.style.height = "auto";
            //图片异常的捕捉，防止用户修改后缀来伪造图片
            try {
                localImagId.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
                localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
            }
            catch (e) {
                alert("您上传的图片格式不正确，请重新选择!");
                return false;
            }
            imgObjPreview.style.display = 'none';
            document.selection.empty();
        }
    }
    $("#edit").append("<div class='content' id='content" + (d + 1) + "'><div contenteditable='true' class='editablesmall'><p class='moren'>此处添加文字描述</p></div></div>");
    $(".editablesmall").css({"height": $(window).height() * 0.1 + "px"});
    return true;
}

function Delete() {
    if (window.confirm('确定删除图片？')) {
        var apId = $("#ImgZoomInImage2").attr("src");
        if (apId.indexOf("blob:") == 0) {
            $("#" + $("#ImgZoomInImage2").attr("data-id")).parent().siblings().remove();
            $("#" + $("#ImgZoomInImage2").attr("data-id")).parent().remove();
            /* $("#"+$("#ImgZoomInImage").attr("data-id")).parents("content").remove();*/
            alert("操作成功！");
            $(".delpic").remove();
        } else {
            var url = basePath + "ea/productslaunch/sajax_ea_delPhoto.jspa?apId=" + apId;
            $.ajax({
                url: url,
                type: "get",
                async: false,
                success: function (data) {
                    var member = eval("(" + data + ")");
                    var flag = member.flag;
                    if (flag == "2") {
                        alert("操作成功！");
                    }
                },
                error: function () {
                    alert("操作失败！");
                }
            });
            $(".delpic").remove();
        }
    }
    $('#delete').hide();
    $("#deletes").hide();
    $('#ImgZoomInBG').hide();
    $('#ImgZoomInImage2').hide();
    $(".ImgZoomInImage2").attr("src", "");
    /* $("#edit").append("<div class='content' id='content"+(d)+"'><div contenteditable='true' class='editablesmall'><p class='moren'>此处添加文字描述</p></div></div>");
     $(".editablesmall").css({"height":$(window).height()*0.1+"px"});*/
}
function quxiao() {
    $("#delete").hide();
    $("#deletes").hide();
    $("#ImgZoomInImage2").hide();
    $("#ImgZoomInBG").hide();
    $(".delpic").attr("class", "pic");
}


