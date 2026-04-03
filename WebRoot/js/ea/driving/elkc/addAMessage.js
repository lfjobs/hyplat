$(document).ready(function () {

    $(".sele_search").on('input propertychange',function(){
        pageNumber = 0;
        $(".sele_list").empty();
        ajax();
    });



    //提交保存
    $(".submit_btn").click(function () {
        var theme_inp = $(".theme_inp").val();//主题
        var mes_main_inp = $(".mes_main_inp").val();//内容
        var receive_box = [];
        $(".receive_box").each(function(i,dom){
            receive_box[i] = $(this).attr("data-staffid");
        });
        if ($.trim(theme_inp)==""){
            return alert("主题不能为空!!!");
        }
        if ($.trim(mes_main_inp)==""){
            return alert("内容不能为空!!!");
        }
        if (receive_box.length==0){
            return alert("请添加收信人!!!");
        }
        var oMyForm = new FormData();
        oMyForm.append("edsn.companyId", companyid);
        oMyForm.append("edsn.createperson", staffID);
        oMyForm.append("edsn.theme", theme_inp);
        oMyForm.append("edsn.content", mes_main_inp);
        for(var k in submitDateArray){
            oMyForm.append("file",submitDateArray[k]);//图片数组
            oMyForm.append("fileName",submitDateArray[k].name);//图片名称数组
        }
        for(var y in receive_box){ //人员id
            oMyForm.append("staffId",receive_box[y]);
        }

        var url = basePath
            + "ea/elkcInform/sajax_ea_ajaxAddInform.jspa";
        $.ajax({
            url : url,
            type : "post",
            dataType : "json",
            data : oMyForm,
            async : true,
            processData : false,         // 告诉jQuery不要去处理发送的数据
            contentType : false,        // 告诉jQuery不要去设置Content-Type请求头
            success : function(data) {
                document.location.href = basePath + "/page/ea/main/driving/elkc/sendMesSuccess.jsp";
            },error:function(data){
                alert("保存失败!!!");
            }
        });
    })








});

function getHeight(){
    t=setTimeout("getHeight()", 200);
    if ($(".last").length > 0){
        if($(".last").offset().top<$(window).height()){
            if(pageNumber<pageCount){
                ajax();
            }
        }
    }
}

function ajax() {
    var staffName = $(".sele_search").val();
    var url = basePath
        + "ea/elkcInform/sajax_ea_ajaxCoachStudents.jspa";
    $.ajax({
        url : url,
        type : "post",
        async : false,
        dataType : "json",
        data : {
            "staffName":$.trim(staffName),
            "companyid":companyid,
            "pageForm.pageNumber":pageNumber+1,
            "pageForm.pageSize":5
        },
        success : function(data) {
            var personnel = eval("(" + data + ")");
            var pageForm = personnel.pageForm;
            if (pageForm != null && pageForm.list != null && pageForm.list.length > 0) {
                var inform = [];
                $(".last").removeClass("last");
                $(pageForm.list).each(function(i,dom){
                    if($(pageForm.list).length-1==i){
                        inform.push("<label class='sele_box last' data-staffid='"+this[0]+"'>");
                    }else{
                        inform.push("<label class='sele_box' data-staffid='"+this[0]+"'>");
                    }
                    inform.push("<img src='"+basePath+this[1]+"' class='sele_head_img' alt='' onerror='aaa(this)'>");
                    var aaa;
                    if (this[3]=="0"){
                        aaa="教练";
                    }else if (this[3]=="1"){
                        aaa="学员";
                    }
                    inform.push("<span>"+this[2]+"("+aaa+")"+"</span>");
                    inform.push("<input type='checkbox' class='sele_check'>");
                    inform.push("<i></i>");
                    inform.push("</label>");
                })
                $(".sele_list").append(inform.join(" "));

                pageNumber = pageForm.pageNumber;
                pageCount = pageForm.pageCount;
                if(pageNumber<pageCount){
                    getHeight();
                }
            }
        }
    });
}

function aaa(obj) {
    $(obj).attr("src",basePath+"images/ea/driving/elkc/head.png");
}


