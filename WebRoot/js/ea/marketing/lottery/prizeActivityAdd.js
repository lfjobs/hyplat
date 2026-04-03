/**
 * Created by ljc on 2017/4/24 0024.
 */
$(function() {
    //活动主图
    $(document).on("change", "#act_upinp", function() {
        var file = this.files[0];
        // if (file.size > 30 * 1024)
        // {
        //     prompt("图片不得大于30KB！");
        //     return;
        // }
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
        $(".overlay").addClass("active");
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

    //$("#appDate").mobiscroll($.extend(opt['date'], opt['default']));
    var optDateTime = $.extend(opt['datetime'], opt['default']);
    var optTime = $.extend(opt['time'], opt['default']);
    $("#prize_begin_time").mobiscroll(optDateTime).datetime(optDateTime);
    $("#prize_end_time").mobiscroll(optDateTime).datetime(optDateTime);

    //优惠券时间
    $("#coupon_begin_time").mobiscroll(optDateTime).datetime(optDateTime);
    $("#coupon_end_time").mobiscroll(optDateTime).datetime(optDateTime);
    //$("#appTime").mobiscroll(optTime).time(optTime);

    //时间判断
    $("#prize_begin_time").change(function() {
        var cur_time= getNowFormatDate();
        var t = $(this).val();
        var d = new Date(t).getTime(); //开始时间
        if(d < cur_time){
            prompt("请选择大于当前时间");
            $(this).val('');
        }
    });

    $("#prize_end_time").change(function() {
        var begin = $("#prize_begin_time").val(); //开始时间
        var end = $(this).val(); //结束时间
        if(begin){
            if(end <= begin){
                prompt("请选择大于开始时间的时间");
                $(this).val('');
            }
        }else{
            prompt("请先选择开始时间");
            $(this).val('');
        }
    });

    $("#coupon_begin_time").change(function() {
        var cur_time = getNowFormatDate();
        var t = $(this).val();
        var d = new Date(t).getTime(); //开始时间
        if (d < cur_time) {
            prompt("请选择大于当前时间");
            $(this).val('');
        }
    });

    $("#coupon_end_time").change(function() {
        var begin = $("#coupon_begin_time").val(); //开始时间
        var end = $(this).val(); //结束时间
        if (begin) {
            if (end < begin) {
                prompt("请选择大于开始时间的时间");
                $(this).val('');
            }
        } else {
            prompt("请先选择开始时间");
            $(this).val('');
        }
    });

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
        return n;
    }

    //内嵌页面返回按钮
    $(document).on("click", "#back_prize", function() {
        clearTimeout(t);
        $(this).parents(".nest_page").hide();
    });

    //内嵌页面产品返回按钮
    $(document).on("click", "#back_pro", function() {
        clearTimeout(t);
        $(this).parents(".nest_page").hide();
        $('.award_add_wrap').show();
    });

    //内嵌页面优惠券返回按钮
    $(document).on("click", "#back_coupons", function() {
        clearTimeout(t);
        $(this).parents(".nest_page").hide();
        $('.award_add_wrap').show();
    });

    //内嵌页面优惠券添加返回按钮
    $(document).on("click", "#back_couponAdd", function() {
        clearTimeout(t);
        $(this).parents(".nest_page").hide();
        $('.award_add_wrap_coupons').show();
    });

    //内嵌页面设置权限返回按钮
    $(document).on("click", "#back_limit", function() {
        clearTimeout(t);
        $(this).parents(".nest_page").hide();
    });

    //设置限制页面点击切换样式、返回、返回值
    $(".limit_list_box").click(function() {
        var t = $(this).text();
        $(this).addClass("limit_cur").siblings().removeClass("limit_cur");
        $(this).parents(".nest_page").hide();
        $(".set_limit_btn").text(t);
        $("#limit").val(t=='所有人都可参加' ? '0': '1');
        $("#activityRange").val(t=='所有人都可参加' ? '0': '1');
    });

    //添加页面触发嵌套页面按钮
    $(document).on("click", ".nest_trigge_btn", function() {
        var which_nest = '.' + $(this).attr("data-name");
        $(which_nest).show();
    });

    //增加优惠券
    $(document).on("click",".add_btn",function () {
        $('.award_add_wrap_coupons').hide();
        $('.award_add_wrap_couponAdd').show();
    });
    //单项文本选择
    var i = "1";
    $(".radio_text").on("click", function(e) {
        e.stopPropagation();
        var id = $(this).attr("id") + "_list";
        var which = $('#' + id);
        var that = $(this);
        which.mobiscroll_select().treelist({
            theme: "android-ics",
            lang: "zh",
            display: 'bottom',
            inputClass: 'tmp',
            headerText: '请您选择',
            onSelect: function(valueText) {
                var m = $(this).find("li").eq(valueText).html();
                that.val(m);
                if (m =='会员产品'){
                    $('#prizeType').val("0");
                    $("#pro").attr("onclick","");
                    $("#pro").attr("placeholder","选择会员产品");
                }
                else if (m =='商家优惠券')
                {
                    $('#prizeType').val("1");
                    $("#pro").attr("onclick","couponsList()");
                    $("#pro").attr("placeholder","选择优惠券");
                }
                else if (m=='实物产品')
                {
                    $('#prizeType').val("2");
                    $("#pro").attr("onclick","proList()");
                    $("#pro").attr("placeholder","选择产品");
                }
                else if(m=='会员积分')
                {
                    $('#prizeType').val("3");
                    $("#pro").attr("onclick","");
                    $("#pro").attr("placeholder","选择会员积分");
                }
            }
        });
        $("input[id^=" + id + "]").focus();
    });


    //搜索
    $(".pro_search").on("input",function () {
        pagenumber = 0;
        search = $(this).val();
        if (search != '')
        {
            $(".pro_wrap").empty();
            loaded();
        }
    });


    //处理浏览器输入法遮挡
    var screenH = window.innerHeight;
    window.onresize = function() {
        var t = window.innerHeight;
        console.log(t);
        console.log(screenH);
        var inp = $("input:focus,textarea:focus")[0];
        if (t < screenH) {
            inp.scrollIntoView(false);
        }
    }
});

function getHeight()
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
}
//保存
function save() {

    //判断整数
    var pattern = /^([1-9]+\d*)$/;

    if ($('.cj_inp_both').val()=='')
    {
        prompt("请输入活动名称");
        return;
    }
    else if ($('.act_src').attr("src")=='')
    {
        prompt("请上传活动主图");
        return;
    }
    else if ($('#prize_begin_time').val()=='')
    {
        prompt("请选择开始时间");
        return;
    }
    else if ($('#prize_end_time').val()=='')
    {
        prompt("请选择结束时间");
        return;
    }
    else if ($('#limit').val()=='')
    {
        prompt("请选择参选人");
        return;
    }
    else if ($('#activityType').val()== '1' && !pattern.test($('#score').val()))
    {
        prompt("请输入有效积分数");
    }
    else if ($('#activityType').val()== '1' &&!pattern.test($('#count').val()))
    {
        prompt("请输入有效签到次数");
    }
    else if ($('#activityType').val()== '0' && $('.award_tb').length == 0)
    {
        prompt("请设置奖品");
        return;
    }
    else if ($('#activityType').val()== '0' && $('.award_tb').length < 3)
    {
        prompt("设置奖品不能少于三个奖品等级");
        return;
    }
    else if ($('#activityType').val()== '0' && ($('.bonusPoints').val()=='' || $('.bonusPoints').val().trim() == ''))
    {
        prompt("请输入抽奖所需积分数");
        return;
    }else if($('#activityType').val()== '0' && isNaN($(".bonusPoints").val().trim())){
        alert("抽奖所需积分数必须是数字");
        return;
    }else if($('#activityType').val()== '0' && (parseFloat($(".bonusPoints").val().trim()) > 99999 || parseFloat($(".bonusPoints").val().trim()) < 100)){
        alert("抽奖所需积分数取值范围：100 - 99999");
        return;
    }
    else if ($('.matters_inp').val()=='')
    {
        prompt("请输入注意事项");
        return;
    }
    else if ($('.img_wrap').length == 0)
    {
        prompt("请添加描述图片");
        return;
    }
    else {
        if($('#activityType').val() == '0'){
            $('.bonusPoints').val($('.bonusPoints').val().trim());
        }
        var logo = $("#act_img").attr("src");
        var form = document.forms[0];
        var formData = new FormData(form);
        var imageDesc = '';
        var poolIds = '';

        //活动描述图片
        $('.img_wrap').each(function () {
            if ($(this).find("i").attr("id")== '')
            {
                imageDesc += $(this).find('img').attr('src')+ "***";
            }
        });

        //奖品池id
        $('.award_tb').each(function () {
            poolIds += $(this).attr("id") + ",";
        });

        //修改时判断活动主图片src是否含有base64，若有则重新保存，否则不保存
        if (logo.indexOf("base64")!= -1) {
            formData.append("model.activityImg",logo);
        }
        else
        {
            formData.append("model.activityImg",$('#activityImg').val());
        }

        formData.append("model.imageDesc",imageDesc);
        formData.append("model.poolId",poolIds);

        //ajax 提交form
        $.ajax({
            url : basePath + "ea/lottery/sajax_ea_saveActivity.jspa?",
            type : "POST",
            data : formData,
            dataType:"json",
            beforeSend : function () {
                $(".overlay").addClass("active");
                $(".loading").show();
            },
            async : true,
            processData : false,         // 告诉jQuery不要去处理发送的数据
            contentType : false,        // 告诉jQuery不要去设置Content-Type请求头
            success:function(data){
                var member = eval("(" + data + ")");
                if (member.flag == '1'){
                    document.location.href = basePath + "ea/lottery/ea_prizeActivityList.jspa?flag="
                        + $('#activityType').val() + "&model.companyId=" + companyId;
                }else if (member.flag == '2')
                {
                    prompt("该公司没有积分");
                }
                else{
                    prompt("活动保存失败");
                }
            },
        });
    }
}
//js 把字符串转化为日期,在转换Timestamp
function StringToTimestamp(date) {
    //var s ='2017-04-18 09:16:15';
    var s = date.replace(/-/g,"/");
    var time = new Date(s);
    return Math.round(time.getTime());
}
function savePrizePool() {
    var flag = true;
    if ($('.a_R').eq(0).val()==''){
        prompt("选择奖品类型");
        flag = false;
    }else if ($('.a_R').eq(1).val()=='')
    {
        prompt("选择奖品");
        flag = false;
    }else if ($('.a_R').eq(3).val()=='')
    {
        prompt("输入奖品数量");
        flag = false;
    }else if ($('.a_R').eq(4).val()=='')
    {
        prompt("选择几等奖")
        flag = false;
    }
    else if ($('.award_tb').length >6)
    {
        prompt("设置奖品不能大于六个奖品等级");
        flag = false;
    }
    $(".award_tb dd:last").each(function(){
        if($('.a_R').eq(4).val()==$(this).text()){
            prompt("请设置不同级别的奖品");
            flag = false;
        }
    });

    if(flag){
        //ajax 提交form
        $.ajax({
            url: basePath + "ea/lottery/sajax_ea_savePrizePool.jspa?",
            type: "POST",
            data: $("#prizePoolForm").serialize(),
            async: false,
            success : function (data) {
                    var member = eval("("+data+")");
                    var str = new Array();
                    if (member.flag == '1')
                    {
                        prompt("奖品保存成功");
                        str.push('<dl class="award_tb" id="' + member.ppb.poolId + '">');
                        if (member.ppb.prizeType =='0')
                        {
                            str.push('<dd>会员产品</dd>');
                        }
                        else if (member.ppb.prizeType =='1')
                        {
                            str.push('<dd>商家优惠券</dd>');
                        }
                        else if (member.ppb.prizeType =='2')
                        {
                            str.push('<dd>实物产品</dd>');
                        }else if (member.ppb.prizeType =='3')
                        {
                            str.push('<dd>会员积分</dd>');
                        }
                        var ppname=member.ppb.ppName;
                        if (ppname.length>6){
                            ppname=ppname.substr(0,6)+'...';
                        }
                        str.push('<dd id="ppname1">' + ppname + '</dd>');
                        str.push('<dd>' + member.ppb.prizeNum +'</dd>');
                        str.push('<dd>' +member.ppb.prizeLvl + '</dd></dl>');
                        $('.award_wrap').append(str.join(""));
                        $('.award_add_wrap').hide();
                        $(".nest_page").find(".a_R").val("");
                    }
                    else {
                        prompt("奖品保存失败");
                    }
            	}
        	});
    	}
	}


function proList() {
    loaded();
    $('.award_add_wrap').hide();
    $('.award_add_wrap_pro').show();
}
function couponsList() {
    $('.award_add_wrap').hide();
    $('.award_add_wrap_coupons').show();
}
function chance() {
    var ppId = $("input[type='radio']:checked").attr("id");
    var ppName = $("input[type='radio']:checked").parent().find(".pro_tit").text();
    var price = $("input[type='radio']:checked").parent().find(".pro_price").text();
    clearTimeout(t);
    $('#productId').val(ppId);
    $('#pro').val(ppName);
    $('#price').val(price);
    $('.award_add_wrap').show();
    $('.award_add_wrap_pro').hide()
}
