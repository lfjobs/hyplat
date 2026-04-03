var  xselect = 0;
var id = "";
var idr = "";
var u = window.navigator.userAgent;
var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端

$(function() {
    getDate();
    if(poe=="edit") {
        getEdit();
    }

    $("#title").click(function() {
        if(opr=="edit"){
            if($("#specificTemplate").val()=="") {

                $(".div-tingyong").show();
                $(".titlep").text("请先选择文书模板");
                return false;
            }
        }
    });
    $("#title").bind('input propertychange', function() {
       if($(".qczwed").css('display')!="none"){
           if($.trim($("#title").val())==""){
               return false;
           }
           var fileType = $("#fileType").val();
           var fileShowName = $("#title").val();
           var ext;
           if (fileType == "W") {
               ext = ".doc";
           }else if (fileType == "E") {
               ext = ".xls"
           } else if(fileType == "P"){
               ext = ".pdf";
           }
           if(fileShowName.length>12){
               fileShowName = fileShowName.substring(0,12);
           }
           $("#filename").text(fileShowName + ext);
           $(".qczwed").show();
           $(".qczw").hide();

       }
    });
    $("#text").click(function(){

        if($.trim($("#title").val())==""){
            $(".div-tingyong").show();
            $(".titlep").text("请填写文件标题");
            return false;
        }
        var fileType = $("#fileType").val();
        var fileShowName = $("#title").val();
        var ext;
        if (fileType == "W") {
            ext = ".doc";
        } else {
            ext = ".xls";
        }
        if(fileShowName.length>12){
            fileShowName = fileShowName.substring(0,12);
        }
        $("#filename").text(fileShowName + ext);
        $(".qczwed").show();
        $(".qczw").hide();


    });


    //选择模板
    $("#specificTemplate").click(function(){
        var module = $("#module").val();
        if(poe==""||poe==null) {

            if ($.trim($(this).val()) != "") {
              
                  $(".titlep").text("更换模板会清空内容确定更换么？");
                $(".div-dqd").show();
                xselect = 1;

            } else {
                document.location.href = basePath + "/ea/androiddoc/ea_getSelectTemp.jspa?module="+module;
            }
        }

    });


    $(".close-tingyong").click(function(){
        if($(".titlep").text()=="操作成功"){
        //    window.location.replace(basePath+"ea/contract/ea_getDraftList.jspa?state=draftlist");
            if (isAndroid == true || isiOS == true) {
                window.history.back();
            } else {
                if(rz=="1"){
                    window.history.back();
                }else {
                    window.opener.location.reload(); // 刷新父页面
                    window.close(); // 关闭当前窗口
                }
            }
        }else {
            $(this).parents(".div-tingyong").hide();
        }
    })
    $(".close-confirm").click(function(){


        if($(".titlep").text()=="操作成功"){
      //      window.location.replace(basePath+"ea/contract/ea_getDraftList.jspa?state=draftlist");


            if (isAndroid == true || isiOS == true) {
                window.history.back();
            } else {
                if(rz=="1"){
                    window.history.back();
                }else {
                    window.opener.location.reload();
                    window.close();
                }

            }

        }else {
            $(this).parents(".div-tingyong").hide();
            if(xselect==1){

                document.location.href = basePath + "page/ea/main/office_ea/contract/selectTemp.jsp";

            }
        }



    })



    $(".ddc,.ddcl").click(function(){

        if(poe=="edit"){
            $(".titlep").text("不能修改文书类别");
            $(".div-tingyong").show();

            return false;
        }
        $(".ddc").hide();
       $(".docyxz").show();

        $(".ccc").show();
        $(".ccyxz").hide();
        $(".contract").hide();

        $("#module").val("doc");

    });

    $(".ccc,.cccl").click(function(){

        if(poe=="edit"){
            $(".titlep").text("不能修改文书类别");
            $(".div-tingyong").show();
      
            return false;
        }
        $(".ccc").hide();
        $(".ccyxz").show();


        $(".ddc").show();
        $(".docyxz").hide();

        $(".contract").show();
        $("#module").val("contract");

    });

    //文件缓急
    $(".div-yinzhang li").click(function(){
        $(this).parents(".div-yinzhang").hide();
        $(this).siblings().removeClass("active");
        $(this).addClass("active");
        $(".p-leixing").text($.trim($(this).text()));
        $("#emergencyType").val($.trim($(this).attr("data-value")));
    })

    $("#div-leixing").click(function(){
        $(".div-yinzhang").show();
    })
    $(".hj").click(function(){
        $(".div-yinzhang").show();
    })
    //公文类型
    $(".div-doctype li").click(function(){
        $(this).parents(".div-doctype").hide();
        $(this).parents(".div-yinzhang").hide();
        $(this).siblings().removeClass("active");
        $(this).addClass("active");
        $(".p-docType").text($.trim($(this).text()));
        $("#docType").val($.trim($(this).attr("data-vaue")));

    })
    $("#div-docType,.lx").click(function(){
        $(".div-doctype").show();
    })
    //JS file 图片 即选即得 显示
    //创建一个FileReader对象
    // var reader = new FileReader();
    // function f_change(file){
    //
    //
    //     var img = document.getElementById('imgSdf');
    //
    //     var name  = file.files[0].name;
    //
    //     //读取File对象的数据
    //     reader.onload = function(evt) {
    //         //data:img base64 编码数据显示
    //         img.width = "100";
    //         img.height = "100";
    //         var ext = evt.target.result;
    //         var fileType = "M";
    //         if (name.indexOf("xls") != -1 || name.indexOf("xls") != -1) {
    //             ext = basePath + "images/ea/office/contract/excel-ext.png";
    //             fileType = "E";
    //         } else if (name.indexOf("doc") != -1 || name.indexOf("docx") != -1){
    //
    //             ext = basePath + "images/ea/office/contract/word.png";
    //             fileType = "W";
    //         }
    //         else if (name.indexOf("pdf") != -1 || name.indexOf("PDF") != -1){
    //             ext = basePath + "images/ea/office/contract/PDF.png";
    //             fileType = "P";
    //         }
    //         img.src = ext;
    //
    //         $("#fileType").val(fileType);
    //     }
    //     reader.readAsDataURL(file.files[0]);
    // }


//提交审核
    $(".saveDraft").click(function(){

        saveDoc("s");
    });
    
    $(".passDraft").click(function(){
        saveDoc("p");

    });
    $(".submitAudit").click(function(){
        saveDoc("A");

    });

    //公司输入
    $('#partyAName,#partyBName').bind('input propertychange', function() {


        var top = $(this).position().top;
        $(".ul-list").html("");
        $(".sec-ul").hide();

        var $p = $(this);

        if($.trim($p.val())==""||$.trim($p.val()).length<4){
            return false;

        }
        id  = $p.attr("id");
        var url = basePath
            + "ea/documentcommon/sajax_ea_getAllCompany.jspa?date="
            + new Date().toLocaleString();
        $.ajax({
            url: encodeURI(url),
            type: "post",
            async: true,
            dataType: "json",
            data:{
                "document.companyName":$p.val()
            },
            success: function cbf(data) {
                var member = eval("(" + data + ")");
                var companylist = member.companylist;
                var str = "";
                for (var i = 0; i < companylist.length; i++) {
                    var obj = companylist[i];
                    str += "<li class='company' id='" + obj.companyID + "'><p>" + obj.companyName + "</p></li>";
                }
                $(".ul-list").html(str);
                if(str!="") {
                    $(".sec-ul").css({
                        position: "absolute",
                        top: top + 45
                    }).show();
                }

            },
            error: function cbf(data) {
                console.log("数据获取失败！")
            }
        });

    });
    //责任人身份证号输入
    $('#partyAstaffnames,#partyBstaffnames,#staffIdentityCardA,#staffIdentityCardB').bind('input propertychange', function() {
        var companyID = "";


        var top = $(this).position().top;
        $(".ul-list").html("");
        $(".sec-ul").hide();

        var $p = $(this);

        if($.trim($p.val())==""||$.trim($p.val()).length<2){
            return false;

        }
        idr  = $p.attr("id");
        if($p.attr("id").indexOf("A")!=-1){
           //说明是甲方
            companyID = $("#partyA").val();
        }else{
            //说明是乙方
            companyID = $("#partyB").val();
        }

        var url = basePath
            + "ea/documentcommon/sajax_ea_getAllPeople.jspa?date="
            + new Date().toLocaleString();
        $.ajax({
            url: encodeURI(url),
            type: "post",
            async: true,
            dataType: "json",
            data:{
                "document.drafterName":$p.val(),
                "document.companyID":companyID
            },
            success: function cbf(data) {

                var member = eval("(" + data + ")");
                var plist = member.plist;

                var htmlstr = new Array();

                for(var i=0;i<plist.length;i++){
                    var obj = plist[i];
                    htmlstr.push("<li class='clearfix staff' id='"+obj[1]+"'  staffname='"+obj[0]+"'>");

                    htmlstr.push("<div class='div-img'>");
                    htmlstr.push("<img src='"+basePath+obj[8]+"' onerror='this.src=\"" + basePath + "/images/ea/production/head2x.png\"'>");
                    htmlstr.push("</div>");
                    htmlstr.push("<p data-card='"+(obj[9]==null?'':obj[9])+"'>");



                    if(obj[9]==null||obj[9]==""){
                        htmlstr.push(obj[0]+"(未完善身份证信息)");
                    }else {
                        htmlstr.push(obj[0] + "(" + obj[9] + ")");
                    }
                    htmlstr.push("</p>")




                    htmlstr.push("</li>")


                }

                $(".ul-list").html(htmlstr.join(""));
                if(plist.length!=0) {
                    $(".sec-ul").css({
                        position: "absolute",
                        top: top + 45
                    }).show();
                }
            },
            error: function cbf(data) {
                console.log("数据获取失败！")
            }
        });
    });



    //选中公司
    $(document).on("click",".ul-list li.company",function(){
        $("#"+id.substring(0,id.length-4)).val($(this).attr("id"));
        $("#"+id).val($(this).attr("id"));
        $("#"+id).val($(this).find("p").text());
        $(".sec-ul").hide();

    })
     //选中人员
    $(document).on("click",".ul-list li.staff",function(){

        if(idr.indexOf("A")!=-1){
            if($(this).find("p").attr("data-card")==null||$(this).find("p").attr("data-card")==""){
                $(".div-tingyong").show();
                $(".titlep").text("此人尚未完善身份证信息");

            }
            $("#partyAstaff").val($(this).attr("id"));//人id
            $("#partyAstaffnames").val($(this).attr("staffname"));//姓名

            $("#staffIdentityCardA").val($(this).find("p").attr("data-card"));//身份证

        }else{
            if($(this).find("p").attr("data-card")==null||$(this).find("p").attr("data-card")==""){
                $(".div-tingyong").show();
                $(".titlep").text("此人尚未完善身份证信息");

            }
            $("#partyBstaff").val($(this).attr("id"));//人id

            $("#partyBstaffnames").val($(this).attr("staffname"));//姓名
            $("#staffIdentityCardB").val($(this).find("p").attr("data-card"));//身份证
        }

        $(".sec-ul").hide();
    })
});
//保存文档
function saveDoc(type){
typee = type;
    if(opr=="edit") {
        if ($.trim($("#specificTemplate").val()) == "") {
            $(".div-tingyong").show();
            $(".titlep").text("请选择文件模板");
            return false;
        }
    }
    if($.trim($("#title").val())==""){
        $(".div-tingyong").show();
        $(".titlep").text("请填写文件标题");
        return false;
    }


    // if($.trim($("#theme").val())==""){
    //     $(".div-tingyong").show();
    //     $(".titlep").text("请填写文件主题");
    //     return false;
    // }

    if($.trim($("#numWord").val())==""){
        $(".div-tingyong").show();
        $(".titlep").text("请填写正式编号");
        return false;
    }
    if(opr=="bd"&&poe!="edit") {

        if ($("#imgSdf").attr("src").indexOf("images/ea/office/contract/uattach.png") != -1) {
            $(".div-tingyong").show();
            $(".titlep").text("请上传签署文件");
            return false;
        }
    }else{
        if($(".qczw").css('display')!="none"){

            $(".div-tingyong").show();
            $(".titlep").text("请起草正文");
            return false;
        }
    }

   $(".loading").show();
    $("#form").attr("target", "hidden").attr("action",
        basePath + "ea/androiddoc/ea_storeDocAPP.jspa");

    document.form.submit.click();
    token = 13;


    // var ulp = basePath
    //     + "ea/androiddoc/sajax_ea_storeDocAPP.jspa";
    // $.ajax({
    //     type: "GET",
    //     url: ulp,
    //     async: false,
    //     dataType: "json",
    //     data: $("#form").serialize(),
    //     success: function (data) {
    //         var m = data.result;
    //         var docId = m.docId;
    //         if(m=="suc"){
    //                 if(type=="s"){
    //                     document.location.href = basePath+"ea/contract/ea_getMyFileList.jspa?module="+module;
    //
    //           }else if(type=="p"||type=="A"){
    //                     document.location.href = basePath+"page/ea/main/office_ea/contract/selectCompany.jsp?docId="+docId+"&type="+type;
    //
    //                 }
    //
    //         }
    //
    //
    //
    //     },
    //     error: function (data) {
    //
    //         console.log("获取链接失败");
    //     }
    //
    //
    // });


}
function re_load(){
    var docId = window.frames["hidden"].financialbillID;//用于接收docID
    if(typee=="s"){
        $(".loading").hide();
        $(".div-tingyong").show();
        $(".titlep").text("操作成功");

        return false;

    }else if(typee=="p"||typee=="A"){

        if(module==""){
            document.location.href = basePath+"page/ea/main/office_ea/contract/selectWldw.jsp?docId="+docId+"&typee="+typee;


        }else{
            document.location.href = basePath+"page/ea/main/office_ea/contract/selectCompany.jsp?docId="+docId+"&typee="+typee;


        }
    }

}


function getDate() {
    var date = new Date();
    var year = date.getFullYear();


    var month = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1);

    var D = (date.getDate() < 10 ? '0'+(date.getDate()) : date.getDate());
    if($("#numTime").val()==""){
        $(".year").text(year);
        $("#numTime").val(year);
        $("#numWord").val(year+""+month+""+D);
    }

}
function  getEdit(){

  var emergencyType = $("#emergencyType").val();
    if(emergencyType!=null&&emergencyType!=""){
       if(emergencyType=="p"){
         $(".p-leixing").text("普通");
       }else if(emergencyType=="j"){
           $(".p-leixing").text("急件");
       }else if(emergencyType=="t"){
           $(".p-leixing").text("特急");
       }else{

           $(".p-leixing").text("普通");
       }
    }


    var docType = $("#docType").val();
    if(docType!=null&&docType!=""){
        if(docType=="aa"){
            $(".p-docType").text("董事会会议决定文件");
        }else if(docType=="bb"){
            $(".p-docType").text("董事长办公室文件");
        }else if(docType=="cc"){
            $(".p-docType").text("总裁办公室文件");
        }else if(docType=="dd"){
            $(".p-docType").text("总部人事处文件");
        }else if(docType=="ee"){
            $(".p-docType").text("总部办公室文件");
        }else if(docType=="ff"){
            $(".p-docType").text("总部财务处文件");
        }else if(docType=="gg"){
            $(".p-docType").text("总部教务(生产)处文件");
        }else if(docType=="hh"){
            $(".p-docType").text("总部营销处文件");
        }else if(docType=="ii"){
            $(".p-docType").text("总部服务(创收)平台");
        }else if(docType=="jj"){
            $(".p-docType").text("总部教务部文件");
        }else{

            $(".p-docType").text("董事会会议决定文件");
        }
    }

  if(poe=="edit"){

    var ext;
    if (fileType == "W") {
        ext = ".doc";
    } else if (fileType == "E") {
          ext = ".xls";
      }else if (fileType == "P") {
        ext = ".pdf";
        $("#filename").attr("href","javascript:showtip('"+fileType+"');");
    }else {

    }


      var u = navigator.userAgent;
      var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
      var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端


          if (isAndroid||isiOS) {
             //手机端不能在线编辑
              $("#filename").attr("href","javascript:showtip('"+fileType+"');");
          }
    if(fileShowName.length>12){
        fileShowName = fileShowName.substring(0,12);
    }
    $("#filename").text(fileShowName + ext);
    $(".qczwed").show();
    $(".qczw").hide();


  }else{

      var img = document.getElementById('imgSdf');

          if (fileType=="E") {
              ext = basePath + "images/ea/office/contract/excel-ext.png";

          } else if ( fileType == "W"){

              ext = basePath + "images/ea/office/contract/word.png";

          }
          else if (fileType = "P"){
              ext = basePath + "images/ea/office/contract/PDF.png";

          }
          img.src = ext;

          $("#fileType").val(fileType);
          $("#sdfFile").hide();
  }

}
/**
 *
 * PDF无法编辑
 */
function showtip(fileType){
    if (fileType == "W") {
        $(".titlep").text("只能在PC端编辑Word文档");
    } else if (fileType == "E") {
        $(".titlep").text("只能在PC端编辑Excel文档");
    }else if (fileType == "P") {
        $(".titlep").text("不可编辑PDF格式文件");
    }
    $(".div-tingyong").show();


}