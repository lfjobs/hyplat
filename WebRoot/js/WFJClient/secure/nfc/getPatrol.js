var num;
var options = {
    autoplay: false,// 自动播放：true/false
    controls: true, // 是否显示底部控制栏：true/false
    aspectRatio: "4:3", // 将播放器置于流体模式下（如“16:9”或“4:3”）
    loop: false, // 是否循环播放:true/false
    muted: false, // 设置默认播放音频：true/false
    preload: "auto",
    fluid: true, // 是否自适应布局
    inactivityTimeout: 0, // 闲置超时
    nativeControlsForTouch: false, // 是否使用浏览器原生的控件
    language: 'zh-CN',
    controlBar: {
        children: [
            {name: 'playToggle'}, // 播放按钮
            {name: 'currentTimeDisplay'}, // 当前已播放时间
            {name: 'progressControl'}, // 播放进度条
            {name: 'durationDisplay'}, // 总时间
            {
                name: 'volumePanel', // 音量控制
                inline: false, // 不使用水平方式
            },
            {name: 'FullscreenToggle'}, // 全屏
        ]
    }
};
$(function () {

    // 查询视频播放框隐藏
    $(".div-tingyong").click(function () {
        $(".div-tingyong").hide();
    });

    var myPlayer = videojs('my-player', options, function () {
        // 准备好播放
        // 在回调函数中，this代表当前播放器，
        var myPlayer = this;
    });

    //视频播放
    $(document).on("click", ".vod-click", function () {
        var path = $(this).attr("title");
        num=$(this).index();
        myPlayer.src(basePath + path);
        myPlayer.play();
        $(".div-tingyong").show();
    });

    //视频切换（上一个）
    $(".previous-p").click(function (){
        var len=$(".div-video").children().length;
        if(len>1){
            if(num=0){
                num=len-1
            }else{
                num=num-1;
            }
        }
        var path = $(".div-video").find("img:eq("+num+")") .attr("title");
        myPlayer.src(basePath + path);
        myPlayer.play();
    });

    //视频切换（下一个）
    $(".latter-p").click(function (){
        var len=$(".div-video").children().length;
        if(len>1){
            if(num=len-1){
                num=0
            }else{
                num=num+1;
            }
        }
        var path = $(".div-video").find("img:eq("+num+")") .attr("title");
        myPlayer.src(basePath + path);
        myPlayer.play();
    });

    //图片放大
    $(document).on("click", ".div-img img", function () {
        $(this).parent().attr("class", "deldiv-img");
        bgstr = '<div id="ImgZoomInBG" style=" background:#000000; filter:Alpha(Opacity=70); opacity:0.7; position:fixed; left:0; top:0; z-index:10000; width:100%; height:100%; display:none;" onclick="quxiao()"></div>';
        imgstr = '<img id="ImgZoomInImage2" src="' + $(this).attr("src") + '" data-id="' + $(this).attr("data-id") + '" style="cursor:pointer; display:none; position:absolute; z-index:10000;" /><img id="deletes" style="position: absolute;display:none;" onclick="Delete()" src="' + basePath + 'images/WFJClient/PersonalJoining/cutout.png"/>';
        if ($('#ImgZoomInBG').length < 1) {
            $('body').append(bgstr);
        }
        if ($('#ImgZoomInImage2').length < 1) {
            $('body').append(imgstr);
        } else {
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
});

//图片删除
function Delete() {
    /*if (window.confirm('确定删除图片？')) {
        var apId = $("#ImgZoomInImage2").attr("src");
        if (apId.indexOf("blob:") == 0) {
            $("#" + $("#ImgZoomInImage2").attr("data-id")).parent().siblings().remove();
            $("#" + $("#ImgZoomInImage2").attr("data-id")).parent().remove();
            /!* $("#"+$("#ImgZoomInImage").attr("data-id")).parents("content").remove();*!/
            prompt("操作成功！");
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
                        prompt("操作成功！");
                    }
                },
                error: function () {
                    prompt("操作失败！");
                }
            });
            $(".delpic").remove();
        }
    }*/
    $('#delete').hide();
    $("#deletes").hide();
    $('#ImgZoomInBG').hide();
    $('#ImgZoomInImage2').hide();
    $(".ImgZoomInImage2").attr("src", "");
    $(".deldiv-img").attr("class", "div-img");
    /* $("#edit").append("<div class='content' id='content"+(d)+"'><div contenteditable='true' class='editablesmall'><p class='moren'>此处添加文字描述</p></div></div>");
     $(".editablesmall").css({"height":$(window).height()*0.1+"px"});*/
}

//图片取消
function quxiao() {
    $("#delete").hide();
    $("#deletes").hide();
    $("#ImgZoomInImage2").hide();
    $("#ImgZoomInBG").hide();
    $(".deldiv-img").attr("class", "div-img");
}