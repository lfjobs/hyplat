
$(function() {
  //下一步
   $("#next1").click(function () {
       $(".div-p-02").hide();
       var error = 0;

       $(".div-p-02").hide();
       var error = 0;

       var decs = $(".content .maxlength").val();
       if($.trim(decs)!=""){
           if($.trim(decs).length>500){
               $(".div-p-02").show();
               $(".div-p-02 p").text("补充说明不能超过500字");
               error = 1;
               return false;

           }
       }

       if(!$(".num").is(":hidden")) {
           var num = $(".tnum").text();
           if(num=="0"){

               $(".div-p-02").show();
               $(".div-p-02 p").text("请上传特殊资质材料");
               error = 1;
               return false;
           }

       }
       if(!$(".bnum").is(":hidden")) {
           var num = $(".bnum").text();
             if(num=="0"){

                 $(".div-p-02").show();
                 $(".div-p-02 p").text("请上传补充材料");
                 error = 1;
                 return false;
             }

       }





       if(error==0) {
           $("#form1").attr("target", "hidden").attr("action", basePath + "ea/merch/ea_saveMaterial.jspa?message=11");
           token = 13;
           document.form1.submit.click();
       }



       if(error==0) {
           $("#form1").attr("target", "hidden").attr("action", basePath + "ea/merch/ea_saveMaterial.jspa?message=11");
           token = 13;
           document.form1.submit.click();
       }



   })


});

function re_load() {
    if(token) {
        document.location.href = basePath+"ea/merch/ea_submitAudit.jspa?companyID="+companyID+"&staffID="+staffID;


    }
}


function imgChange(obj1, obj2,obj3) {

    var divlength=$(obj3).parents(".sec-01").find(".z_addImg").length;
    if(divlength<5){
        var tnum = Number($(".tnum").text())+1;
        if(divlength<4){
            $(".z_file").prepend($("#file0").clone(true).attr("id","file"+tnum));
        }

        //获取点击的文本框
        var file = document.getElementById("file"+(tnum-1));
        //存放图片的父级元素
        var imgContainer = document.getElementsByClassName(obj1)[0];
        //获取的图片文件
        var fileList = file.files;
        //文本框的父级元素
        var input = document.getElementsByClassName(obj2)[0];
        var imgArr = [];
        //遍历获取到得图片文件
        for (var i = 0; i < fileList.length; i++) {
            var imgUrl = window.URL.createObjectURL(file.files[i]);
            imgArr.push(imgUrl);
            var img = document.createElement("img");
            img.setAttribute("src", imgArr[i]);
            var imgAdd = document.createElement("div");
            imgAdd.setAttribute("class", "z_addImg");
            imgAdd.appendChild(img);
            imgContainer.appendChild(imgAdd);
        }
        //imgRemove();
        var divlengths=$(obj3).parents(".sec-01").find(".z_addImg").length;
        $(obj3).parents(".sec-01").find(".p-zz").children("span").text(divlengths);
    }
}
function imgChange1(obj1, obj2,obj3) {

    var divlength=$(obj3).parents(".sec-01").find(".z_addImg").length;
    if(divlength<5){


        var tnum = Number($(".bnum").text())+1;
        if(divlength<4){
            $(".z_file1").prepend($("#files0").clone(true).attr("id","files"+tnum));
        }




        //获取点击的文本框
        var file = document.getElementById("files"+(tnum-1));
        //存放图片的父级元素
        var imgContainer = document.getElementsByClassName(obj1)[0];
        //获取的图片文件
        var fileList = file.files;
        //文本框的父级元素
        var input = document.getElementsByClassName(obj2)[0];
        var imgArr = [];
        //遍历获取到得图片文件
        for (var i = 0; i < fileList.length; i++) {
            var imgUrl = window.URL.createObjectURL(file.files[i]);
            imgArr.push(imgUrl);
            var img = document.createElement("img");
            img.setAttribute("src", imgArr[i]);
            var imgAdd = document.createElement("div");
            imgAdd.setAttribute("class", "z_addImg");
            imgAdd.appendChild(img);
            imgContainer.appendChild(imgAdd);
        };
        //imgRemove();
        var divlengths=$(obj3).parents(".sec-01").find(".z_addImg").length;
        $(obj3).parents(".sec-01").find(".p-zz").children("span").text(divlengths);
    }
};





