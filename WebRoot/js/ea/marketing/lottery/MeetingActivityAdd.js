/**
 * Created by jcy on 2017/7/20 0024.
 */
$(function() {
    //活动主图
    $(document).on("change", "#act_upinp", function() {
        var file = this.files[0];
        var reader = new FileReader();
        reader.onload = function() {
            // 通过 reader.result 来访问生成的 DataURL
            var url = reader.result;
            $("#act_img").attr("src", url);
            cutImg();
            $("#image").cropper("replace", url);
        };
        reader.readAsDataURL(file);
        $("#confrim").one("click", function() {
            confirm($("#act_img"));
        });
    });

    //添加图片
    var fileID = 0;
    var fd = new FormData();
    $(document).on("change", "#upimg_inp", function() {
        fileID++; //添加图片ID
        var filename = 'file' + '_' + fileID;
        var file = this.files[0];
        var reader = new FileReader();
        reader.onload = function() {
            // 通过 reader.result 来访问生成的 DataURL
            var url = reader.result;
            var html = '<a class="upimg_box img_wrap" href="javascript:;" data-filename="'
                + filename + '"><i class="upimg_del" id=""></i><img src="' + url + '" alt=""></a>';
            $(".add_upimg_btn").before(html);
            fd.append(filename, file);
        };
        reader.readAsDataURL(file);

    });
    //删除已添加图片
    $(document).on('click', '.upimg_del', function() {
        if ($(this).attr("id")!= "")
        {
            $that = $(this);
            $.ajax({
                url : basePath + "ea/lottery/sajax_ea_delPrizeDecImage.jspa?",
                type : "post",
                async : false,
                dataType : "json",
                data : {"model.poolId" : $(this).attr("id"),
                    "model.companyId" : companyId
                },
                success : function cbf(data) {
                    if (data == 'true')
                    {
                        prompt("删除成功");
                        $that.parent().remove();
                    }
                    else
                    {
                        prompt("删除失败");
                    }
                }
            });
        }
        else
        {
            var name = $(this).parent().attr("data-filename");
            $(this).parent().remove();
            fd.delete(name);
        }
    });

    //裁剪
    //弹出裁剪遮罩层
    function cutImg() {
        $(".overlay").first().addClass("active");
        $(".crop_wrap").show();
        var $image = $('#image');
        $image.cropper({
            checkImageOrigin: true,
            aspectRatio: 1 / 1, //比例
            autoCropArea: 0.8
        });
    }
    //裁剪确定按钮
    function confirm(img) {
        $(".overlay").removeClass("active");
        $(".crop_wrap").hide();
        var $image = $('#image');
        var dataURL = $image.cropper("getCroppedCanvas");
        var imgurl = dataURL.toDataURL("image/png", 1.0);
        img.attr("src", imgurl);
    }
    //选择时间插件
    var currYear = (new Date()).getFullYear();
    var opt = {};
    opt.date = {
        preset: 'date'
    };
    opt.datetime = {
        preset: 'datetime'
    };
    opt.time = {
        preset: 'time'
    };
    opt.default = {
        theme: 'android-ics light', //皮肤样式
        display: 'modal', //显示方式
        mode: 'scroller', //日期选择模式
        dateFormat: 'yyyy-mm-dd',
        lang: 'zh',
        showNow: true,
        nowText: "今天",
        startYear: currYear - 10, //开始年份
        endYear: currYear + 10 //结束年份
    };

    var optDateTime = $.extend(opt['datetime'], opt['default']);
    var optTime = $.extend(opt['time'], opt['default']);
    $("#begin_time").mobiscroll(optDateTime).datetime(optDateTime);
    $("#end_time").mobiscroll(optDateTime).datetime(optDateTime);

    //时间判断
    $("#begin_time").change(function() {
        var cur_time = getNowFormatDate();

        console.log(cur_time);
        var t = $(this).val();
        var d = new Date(t).getTime(); //开始时间
        //console.log(d);
        if (d < cur_time) {
            console.log("请选择大于当前时间");
            $(this).val('');
        }
    })
    $("#end_time").change(function() {
        var begin = $("#begin_time").val(); //开始时间
        var end = $(this).val(); //结束时间
        if (begin) {
            if (end < begin) {
                console.log("请选择大于开始时间的时间");
                $(this).val('');
            }
        } else {
            console.log("请先选择开始时间");
            $(this).val('');
        }

    })

    function getNowFormatDate() {
        var date = new Date();
        var seperator1 = "-";
        var seperator2 = ":";
        var month = date.getMonth() + 1;
        var strDate = date.getDate();
        if (month >= 1 && month <= 9) {
            month = "0" + month;
        }
        if (strDate >= 0 && strDate <= 9) {
            strDate = "0" + strDate;
        }
        var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate +
            " " + date.getHours() + seperator2 + date.getMinutes();

        var n = new Date(currentdate).getTime();
        //console.log(n);
        return n;
    }
    //处理浏览器输入法遮挡
    var screenH = window.innerHeight;
    window.onresize = function() {
        var t = window.innerHeight;
        console.log(t);
        console.log(screenH);
        var inp = $("input:focus")[0];
        if (t < screenH) {
            inp.scrollIntoView(false);
            $(".save_next").hide();
        }else{
            $(".save_next").show();
        }
    }
    //点击导航切换
    var cur_index = 0;
    /*$(".step_box").click(function() {
        var nav_index = $(".step_wrap .step_box").index($(this));
        //console.log(nav_index);
        $(".hotact_con").eq(nav_index).show().siblings(".hotact_con").hide();
        $(this).addClass("step_act").siblings().removeClass("step_act");
        if (nav_index == 2) {
            $(".save_next").text("保存完成");
            $("#idss").height($(".qrw_add").offset().top-$(".com_head").outerHeight()-$(".step_wrap").outerHeight());
        } else {
            $(".save_next").text("保存进行下一步");
        }
        cur_index = nav_index;
    })*/
    //点击保存进行下一步
    $(".save_next").click(function() {
        var isflag = 1;
        if (cur_index != 2) {
            if(cur_index == 0){
                if ($('.act_src').attr("src")=='')
                {
                    prompt("请上传活动主图");
                    isflag = 0;
                    return;
                }
                else if ($('.act_name').val()=='')
                {
                    prompt("请输入活动名称");
                    isflag = 0;
                    return;
                }
                else if ($('#begin_time').val()=='')
                {
                    prompt("请选择开始时间");
                    isflag = 0;
                    return;
                }
                else if ($('#end_time').val()=='')
                {
                    prompt("请选择结束时间");
                    isflag = 0;
                    return;
                }
                else if ($('#address').val()=='')
                {
                    prompt("请选择活动地址");
                    isflag = 0;
                    return;
                }
            }
            if(cur_index == 1){
                var t = $("#ActivityForm").contents().find('div.ticket_info.clearfix');
                $(t).each(function(){
                    var a = "";
                    var ticketId = $(this).find("input.ticketId").val();
                    var ticketKey = $(this).find("input.ticketKey").val();
                    var ticketName = $(this).find("input.ticketName").val();
                    var ticketPrice = $(this).find("input.ticketPrice").val();
                    var ticketCount = $(this).find("input.ticketCount").val();
                    if(ticketName == "" || ticketPrice == "" || ticketCount == ""){
                        alert("请先完善不完整的票券信息");
                        isflag = 0;
                        return;
                    }
                    a = ticketId+","+ticketKey+","+ticketName+","+ticketPrice+","+ticketCount;
                    tickets = tickets+a+"@";
                    $("#tickets").val(tickets);
                })
            }
            if(isflag){
                $(".hotact_con").eq(cur_index).hide();
                $(".hotact_con").eq(cur_index + 1).show().siblings(".hotact_con").hide();
                $(".step_box").eq(cur_index + 1).addClass("step_act").siblings().removeClass("step_act");
                cur_index++;
            }
        } else {
            var flag = saveImage();
            if(flag){
                save();
            }else{
                console.log("保存失败");
            }
        }
        if (cur_index == 2) {
            $(".save_next").text("保存完成");
            $("#idss").height($(".qrw_add").offset().top-$(".com_head").outerHeight()-$(".step_wrap").outerHeight());
        } else {
            $(".save_next").text("保存进行下一步");
        }
    })
    //点击删除票券
    $(".ticket_wrap").on("click",".minus_btn",function(){
        $(this).parents(".ticket_box").remove();
    })
    //点击添加票券类型
    $(".ticket_add").click(function(){
        var t_html='<div class="ticket_box">'
            +'<a href="javascript:;" class="minus_btn"></a>'
            +'<div class="ticket_info clearfix">'
            +'<div class="ticket_info_tr"><span>票券名称</span>'
            +'<span><input type="text" class="ticket_inp ticketName" placeholder="请输入"></span></div>'
            +'<div class="ticket_info_tr"><span>票券价格</span>'
            +'<span><input type="text" class="ticket_inp ticketPrice" value="0" readonly></span></div>'
            +'<div class="ticket_info_tr"><span>票券数量</span>'
            +'<span><input type="text" class="ticket_inp ticketCount" placeholder="0张"></span></div></div></div>';
        $(".ticket_add").before(t_html);
    })
})

//保存活动
function save() {
    //判断整数
    if ($('.act_src').attr("src")=='')
    {
        prompt("请上传活动主图");
        return;
    }
    else if ($('.act_name').val()=='')
    {
        prompt("请输入活动名称");
        return;
    }
    else if ($('#begin_time').val()=='')
    {
        prompt("请选择开始时间");
        return;
    }
    else if ($('#end_time').val()=='')
    {
        prompt("请选择结束时间");
        return;
    }
    else if ($('#address').val()=='')
    {
        prompt("请选择活动地址");
        return;
    }
    else if($('#submit_val').val() == ""){
        prompt("请上传相关图片");
        return;
    }
    else {
        var logo = $("#act_img").attr("src");
        var form = document.forms[0];
        var formData = new FormData(form);
        $("#submit_Img").val(logo);
        document.getElementById('ActivityForm').action = basePath + "ea/lottery/ea_saveMeetingActivity.jspa?";
        document.getElementById("ActivityForm").submit();
    }
}
//js 把字符串转化为日期,在转换Timestamp
function StringToTimestamp(date) {
    //var s ='2017-04-18 09:16:15';
    var s = date.replace(/-/g,"/");
    var time = new Date(s);
    return Math.round(time.getTime());
}

function saveImage() {
    var flag = 0;
    var $html = $(".submit_html");
    $("#idss").contents().find(".art_box")
        .each(
            function () {
                var t = $(this).find("textarea").val()
                if (t != null) {
                    t = t.replace(new RegExp(/\n/g), "<br/>");
                    t = t.replace(new RegExp(/\s/g), "&nbsp;");
                }
                var src = $(this).find("img").attr("src");
                var vedio_src = $(this).find("img")
                    .attr("data-vediosrc");
                var audio_src = $(this)
                    .attr("data-url");
                var audio_tit = $(this).text();
                var _html = '';

                if (this.classList
                        .contains("art_music")) {
                    if (audio_src.length > 0) {
                        _html = '<audio  class="article_p article_audio" src="" data-hash="'
                            + audio_src
                            + '" data-name="'
                            + audio_tit
                            + '" loop="loop"></audio>';
                    }
                } else if (this.classList
                        .contains("art_img")) {
                    _html = '<div class="article_p article_img" style="margin:10px auto 20px"><p>'
                        + t
                        + '</p><img style="text-align:center;max-width:100%;display:block;margin:10px auto 0" src='
                        + src + ' alt=""/></div>';
                } else if (this.classList
                        .contains("art_vedio")) {
                    _html = '<div class="article_p article_vedio"  style="margin:10px auto 20px"><p>'
                        + t
                        + '</p><video style="width:100%;display:block;margin:10px auto 0; max-height:320px;background:#000" src='
                        + vedio_src
                        + ' controls="controls" poster='
                        + src
                        + ' preload="meta"></video>';
                } else if (this.classList
                        .contains("art_pro")) {
                    var pro = '';
                    $(this)
                        .find(".pro")
                        .each(
                            function () {
                                var pro_html = $(
                                    this)
                                    .html();
                                var href = $(
                                    this)
                                    .attr(
                                        "data-href");
                                pro += '<a href="'
                                    + href
                                    + '" class="pro">'
                                    + pro_html
                                    + '</a>'
                            })
                    _html = '<div class="article_p article_pro clearfix">' + pro + '</div>';

                }
                $html.append(_html);
            })
    var more_pro = '<a href="'
        + basePath
        + 'ea/digitalmall/ea_DigitalMall.jspa?" class="more_pro">点击查看更多产品</a>';
    $html.find(".pro").last().parent().after(more_pro)
    $("#submit_val").val($html.html());
    $html.empty();
    flag = 1;
    return flag;
}

/*function getHeight()
 {
 t = setTimeout("getHeight()",200);
 if($(".last").length > 0)
 {
 if($(".last").offset().top + $(".last").height() - $("header").height() * 4 < $(window).height())
 {
 if(pagenumber < pagecount)
 {
 loaded();
 }
 }
 }
 }
 function loaded ()
 {
 clearTimeout(t);
 pagenumber++;
 $.ajax({
 url : basePath + "ea/lottery/sajax_ea_ajaxProPageForm.jspa?",
 type : "post",
 async : false,
 dataType : "JSON",
 data : {
 "pageForm.pageNumber" : pagenumber,
 "pageForm.pageSize" : 10,
 "model.companyId" : companyId,
 "search" : search
 },
 success : function (data) {
 var member = eval("("+data+")");
 var pageForm = member.pageForm;
 var str = new Array();
 if (pageForm != null && pageForm.recordCount> 0)
 {
 pagenumber = pageForm.pageNumber;
 pagecount = pageForm.pageCount;
 var list = pageForm.list;
 $(".last").removeClass("last");
 for (var i = 0;i < list.length;i++)
 {
 var entity = list[i];
 if(i==list.length - 1){
 str.push('<label class="pro_box last">');
 }else{
 str.push('<label class="pro_box">');
 };
 str.push('<input type="radio" class="pro_inp" name="pro_select" id="' + entity[2] + '"><i></i>');
 str.push('<img src="' + basePath + entity[0] + '" class="pro_img" alt="">');
 str.push('<div class="pro_info">');
 str.push('<div class="pro_tit">'+ entity[3] + '</div>');
 str.push('<div class="pro_price">￥' +entity[1] + '</div>');
 str.push('<div class="pro_state">新增产品</div></div></label>');
 }
 $(".pro_wrap").append(str.join(""));
 t = setTimeout("getHeight()",200);
 }
 }
 });
 }*/
