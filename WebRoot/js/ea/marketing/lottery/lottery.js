/**
 * Created by ljc on 2017/5/10 0010.
 */
$(function () {
    initPopup();
});
var turnplate = {
    restaraunts: [], //大转盘奖品
    awardname: [], //奖品名称
    colors: [], //大转盘奖品区块对应背景颜色
    outsideRadius: 192, //大转盘外圆的半径
    textRadius: 155, //大转盘奖品位置距离圆心的距离
    insideRadius: 68, //大转盘内圆的半径
    startAngle: 0, //开始角度
    bRotate: false //false:停止;ture:旋转
};

$(document).ready(function() {
    turnplate.restaraunts = ["一等奖", "谢谢参与", "二等奖", "谢谢参与", "三等奖", "谢谢参与", "四等奖", "谢谢参与", "五等奖", "谢谢参与", "六等奖", "谢谢参与"];
    //动态添加大转盘的奖品与奖品区域背景颜色
    if(null != prizeLevelList && prizeLevelList.length > 0){
        turnplate.restaraunts = [];
        prizeLevelList = prizeLevelList.substring(1, prizeLevelList.length - 1);
        prizeLevelList = prizeLevelList.split(",");
        for(var i = 0;i < prizeLevelList.length;i++) {
            turnplate.restaraunts.push(prizeLevelList[i].trim());
            turnplate.restaraunts.push("谢谢参与");
        }
    }

    turnplate.awardname = new Array();
    jQuery('#pool ul li').each(function () {
        turnplate.awardname.push($(this).text());
    });
    //turnplate.awardname = ["一等奖的名称","不要灰心，还有机会哦！", "二等奖的名称", "不要灰心，还有机会哦！", "三等奖的名称","不要灰心，还有机会哦！", "四等奖的名称", "不要灰心，还有机会哦！", "五等奖的名称","不要灰心，还有机会哦！", "六等奖的名称","不要灰心，还有机会哦！"]
    turnplate.colors = ["#FFF4D6", "#FFFFFF", "#FFF4D6", "#FFFFFF", "#FFF4D6", "#FFFFFF", "#FFF4D6", "#FFFFFF", "#FFF4D6", "#FFFFFF", "#FFF4D6", "#FFFFFF"];

    var rotateTimeOut = function() {
        $('#wheelcanvas').rotate({
            angle: 0,
            animateTo: 2160,
            duration: 8000,
            callback: function() {
                alert('网络超时，请检查您的网络设置！');
            }
        });
    };

    //旋转转盘 item:奖品位置; txt：提示语;
    var rotateFn = function(item, txt) {
        var angles = item * (360 / turnplate.restaraunts.length) - (360 / (turnplate.restaraunts.length * 2));
        if (angles < 270) {
            angles = 270 - angles;
        } else {
            angles = 360 - angles + 270;
        }
        $('#wheelcanvas').stopRotate();
        $('#wheelcanvas').rotate({
            angle: 0,
            animateTo: angles + 1800,
            duration: 8000,
            callback: function() {
                //alert(txt);
                popupText(item-1,turnplate.awardname[item - 1]);
                turnplate.bRotate = !turnplate.bRotate;
            }
        });
    };

    $('.pointer').click(function() {

        if (turnplate.bRotate) return;
        turnplate.bRotate = !turnplate.bRotate;
        //获取随机数(奖品个数范围内)
        var item = rnd();
        var flag = "free";
        //var item = rnd(1, turnplate.restaraunts.length);
        if (item == '13')
        {
            alert("此次抽奖活动只允许该公司内部员工参加！");
            turnplate.bRotate = !turnplate.bRotate;
            return;
        }else if(item=='15'){
            alert("奖品已抽完，活动已结束！");
            turnplate.bRotate = !turnplate.bRotate;
            return;
        }
        if ($('#free_count').val() >= 1)
        {
            flag = "more";
            if (!confirm("下次为积分抽奖，每次消耗"+bonusPoints+"积分！"))
            {
                turnplate.bRotate = !turnplate.bRotate;
                return;
            }
            if (item == '14')
            {
                alert("您的积分数不够！");
                turnplate.bRotate = !turnplate.bRotate;
                return;
            }
        }

        //保存抽奖记录
        $.ajax({
            url : basePath + "ea/lottery/sajax_ea_saveLotteryRecords.jspa?",
            type : "post",
            async : false,
            dataType : "JSON",
            data : {
                 "model.index" : item,
                 "model.staffId" : $("#staffId").val(),
                 "model.activityId" : activityId,
                 "model.companyId" : companyId,
                 "flag" : flag
            },
            success : function (data) {
                var member = eval("(" + data + ")");
                $("#recordId").val(member.recordId);
                $("#ppId").val(member.ppId);
                $("#free_count").val(member.count);

                rotateFn(item, turnplate.restaraunts[item - 1]);
            },
            error : function () {
            }
        });
    });

    //弹窗返回按钮
    $(".close_btn").click(function() {
        var result = $(".sure_btn").text();
        initPopup();
        if (result == '立即领取') {
            document.location.href = basePath + "ea/lottery/ea_goLottery.jspa?" +
                "model.companyId="+companyId+"&model.activityId="+activityId+"&ccompanyId="+ccompanyId+"&selectType="+selectType;
        }
    });

    //弹窗-立即领取or继续努力
    $(".sure_btn").click(function() {
        var t = $(this).text();
        var ppId = $("#ppId").val();
        var recordId = $("#recordId").val();
        if (t == '立即领取') {
            initPopup();
            document.location.href =
                basePath + "ea/wfjshop/ea_gm.jspa?ppid=" + ppId + "&count=1&morre=0&flag=lottery&recordId=" + recordId;
        } else {
            initPopup();
        }
    });

    //金币公告滚动
    var $box = $(".notice_list");
    var n = $box.find("li").length * 30;
    var a = 0;
    $(".notice_list li").first().clone().appendTo(".notice_list");

    setInterval(function() {
        noticeScroll();
    }, 2000)

    function noticeScroll() {
        if (a == n) {
            $box.css("top", 0);
            a = 30;
        } else {
            a += 30;
        }
        $box.animate({
            top: "-" + a + "px"
        }, 1000);
    }

    $("#activityName").css("background-image", "url('" + basePath + "images/ea/lottery/draw_05.jpg')");

    if("lotteryDraw" == selectType){
        $(".com_head a:first-child").attr("href", basePath + "ea/5l5c/ea_selectCompany.jspa");
    }else {
        $(".com_head a:first-child").attr("href","javascript:history.go(-1)");
    }
});

//初始化弹窗
function initPopup() {
    $(".popup_box").hide().parent().removeClass("active").end().find("div").first().removeClass();
    $(".sure_btn").text("立即领取");
}


//根据返回的判断： 13非公司员工，14积分不够
function rnd() {
    var random = "";
    jQuery.ajax({
        url : basePath + "ea/lottery/sajax_ea_lottery.jspa?",
        type : "get",
        async : false,
        dataType : "JSON",
        data : {
            "model.activityId" : activityId,
            "model.companyId" : companyId
        },
        success : function (data) {
            var member = eval("(" + data + ")");
            var t = member.t;
           // alert(t);
            if (t == "login" )
            {
                alert("请先登录数字地球");
                // document.location.href =
                //     basePath + "ea/lottery/ea_login.jspa?model.activityId=" + activityId
                //         + "&model.companyId=" + companyId;
                document.location.href = basePath + "ea/lottery/ea_loginPc.jspa";
                return;
            }
            else if (t == "no")
            {
                random = "13";
            }
            else if (t == "less")
            {
                random = "14";
            }else if(t=='end'){
                random="15";
            } else {
                random = member.index;
                //alert(random);
            }
            $("#staffId").val(member.staffId);
            $("#free_count").val(member.count);
        }
    });
    return random;
}

//弹窗(index 为奖品位置，text 为奖品名称)
function popupText(index, text) {
    var t = turnplate.awardname[index]?turnplate.awardname[index]:"不要灰心，还有机会哦！";
    var n = turnplate.restaraunts[index]?turnplate.restaraunts[index]:"谢谢参与";
    var $popup_bd = $(".popup_box").find("div").first();
    var $popup_text = $(".popup_text");
    var $btn_t=$(".sure_btn");
    switch (n) {
        case "谢谢参与":
            $popup_bd.addClass("popup_bd award_00");
            $popup_text.text(t);
            $btn_t.text("继续努力");
            break;
        case "一等奖":
            $popup_bd.addClass("popup_bd award_01");
            $popup_text.html("恭喜你获得 <span>" + t + "</span>");
            break;
        case "二等奖":
            $popup_bd.addClass("popup_bd award_02");
            $popup_text.html("恭喜你获得<span>" + t + "</span>");
            break;
        case "三等奖":
            $popup_bd.addClass("popup_bd award_03");
            $popup_text.html("恭喜你获得<span>" + t + "</span>");
            break;
        case "四等奖":
            $popup_bd.addClass("popup_bd award_04");
            $popup_text.html("恭喜你获得<span>" + t + "</span>");
            break;
        case "五等奖":
            $popup_bd.addClass("popup_bd award_05");
            $popup_text.html("恭喜你获得<span>" + t + "</span>");
            break;
        case "六等奖":
            $popup_bd.addClass("popup_bd award_06");
            $popup_text.html("恭喜你获得<span>" + t + "</span>");
            break;
    };
    $(".overlay").addClass("active").find(".popup_box").show();

}

//页面所有元素加载完毕后执行drawRouletteWheel()方法对转盘进行渲染
window.onload = function() {
    drawRouletteWheel();
    if("lotteryDraw" == selectType){
        $(".draw_btn_box:first-child").hide();
    }
};

function drawRouletteWheel() {
    var canvas = document.getElementById("wheelcanvas");
    if (canvas.getContext) {
        //根据奖品个数计算圆周角度
        var arc = Math.PI / (turnplate.restaraunts.length / 2);
        var ctx = canvas.getContext("2d");
        //在给定矩形内清空一个矩形
        ctx.clearRect(0, 0, 422, 422);
        //strokeStyle 属性设置或返回用于笔触的颜色、渐变或模式
        ctx.strokeStyle = "#FFBE04";
        //font 属性设置或返回画布上文本内容的当前字体属性
        ctx.font = '16px Microsoft YaHei';
        for (var i = 0; i < turnplate.restaraunts.length; i++) {
            var angle = turnplate.startAngle + i * arc;
            ctx.fillStyle = turnplate.colors[i];
            ctx.beginPath();
            //arc(x,y,r,起始角,结束角,绘制方向) 方法创建弧/曲线（用于创建圆或部分圆）
            ctx.arc(211, 211, turnplate.outsideRadius, angle, angle + arc, false);
            ctx.arc(211, 211, turnplate.insideRadius, angle + arc, angle, true);
            ctx.stroke();
            ctx.fill();
            //锁画布(为了保存之前的画布状态)
            ctx.save();

            //----绘制奖品开始----
            ctx.fillStyle = "#E5302F";
            var text = turnplate.restaraunts[i];
            var line_height = 17;
            //translate方法重新映射画布上的 (0,0) 位置
            ctx.translate(211 + Math.cos(angle + arc / 2) * turnplate.textRadius, 211 + Math.sin(angle + arc / 2) * turnplate.textRadius);

            //rotate方法旋转当前的绘图
            ctx.rotate(angle + arc / 2 + Math.PI / 2);

            /** 下面代码根据奖品类型、奖品名称长度渲染不同效果，如字体、颜色、图片效果。(具体根据实际情况改变) **/
            if (text.indexOf("M") > 0) {
                var texts = text.split("M");
                for (var j = 0; j < texts.length; j++) {
                    ctx.font = j == 0 ? 'bold 20px Microsoft YaHei' : '16px Microsoft YaHei';
                    if (j == 0) {
                        ctx.fillText(texts[j] + "M", -ctx.measureText(texts[j] + "M").width / 2, j * line_height);
                    } else {
                        ctx.fillText(texts[j], -ctx.measureText(texts[j]).width / 2, j * line_height);
                    }
                }
            } else if (text.indexOf("M") == -1 && text.length > 6) { //奖品名称长度超过一定范围
                text = text.substring(0, 6) + "||" + text.substring(6);
                var texts = text.split("||");
                for (var j = 0; j < texts.length; j++) {
                    ctx.fillText(texts[j], -ctx.measureText(texts[j]).width / 2, j * line_height);
                }
            } else {
                //在画布上绘制填色的文本。文本的默认颜色是黑色
                //measureText()方法返回包含一个对象，该对象包含以像素计的指定字体宽度
                ctx.fillText(text, -ctx.measureText(text).width / 2, 0);
            }

            //添加对应图标
            /*if (text.indexOf("闪币") > 0) {
             var img = document.getElementById("shan-img");
             img.onload = function() {
             ctx.drawImage(img, -15, 10);
             };
             ctx.drawImage(img, -15, 10);
             } else */
            if (text.indexOf("谢谢参与") >= 0) {
                var img = document.getElementById("sorry-img");
                img.onload = function() {
                    ctx.drawImage(img, -15, 10);
                };
                ctx.drawImage(img, -15, 10);
            }
            //把当前画布返回（调整）到上一个save()状态之前
            ctx.restore();
            //----绘制奖品结束----
        }
    }
}