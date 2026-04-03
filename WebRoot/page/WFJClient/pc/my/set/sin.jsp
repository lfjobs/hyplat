<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<html>
<head lang="en">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta http-equiv="Access-Control-Allow-Origin" content="*">
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no, width=device-width">
    <title>滴滴</title>
    <script type="text/javascript" src="<%=basePath%>/js/ea/marketing/edmandServe/setHtmlFont.js"></script>
    <script src="<%=basePath%>/js/ea/marketing/edmandServe/jquery.min.js"></script>
    <style type="text/css">
        body,
        html {
            -moz-user-select: none;
            -khtml-user-select: none;
            user-select: none;
            -webkit-overflow-scrolling: touch
        }

        body,
        button,
        dd,
        div,
        dl,
        dt,
        form,
        h1,
        h2,
        h3,
        h4,
        h5,
        h6,
        header,
        html,
        iframe,
        input,
        li,
        menu,
        p,
        section,
        select,
        table,
        td,
        textarea,
        th,
        ul {
            margin: 0 0;
            padding: 0 0;
            font-family: '微软雅黑'
        }

        img {
            cursor: pointer;
            border: none;
            vertical-align: middle
        }

        li,
        ol,
        ul {
            list-style: none
        }

        a,
        a:link {
            text-decoration: none;
            color: #666
        }

        a:active,
        a:hover {
            text-decoration: none;
            -webkit-tap-highlight-color: transparent
        }

        a:focus {
            outline: 0
        }

        input {
            outline: 0;
            -webkit-appearance: none;
            -webkit-tap-highlight-color: transparent
        }

        ::-webkit-scrollbar {
            width: 0
        }

        @keyframes shake {
            0% {
                transform: translateX(0);
            }
            25% {
                transform: translateX(-5px);
            }
            50% {
                transform: translateX(5px);
            }
            75% {
                transform: translateX(-5px);
            }
            100% {
                transform: translateX(0);
            }
        }

        body {
            height: 96vh;
            background: url(<%=basePath%>images/ea/websitemall/card/bg.png) no-repeat bottom right;
            background-size: 7rem;
        }

        .container {
            background: url(<%=basePath%>images/ea/websitemall/card/bg2.jpg) no-repeat top left;
            background-size: 5rem;
        }

        #time {
            text-align: center;
            margin-top: 1rem;
            font-size: 0.7rem;
        }

        .ul-content {
            text-align: center;
        }

        .ul-content li {
            background: url(<%=basePath%>images/ea/websitemall/card/img-01.png) no-repeat;
            margin: 2rem auto;
            background-size: 15rem;
            width: 5rem;
            height: 6.5rem;
            display: flex;
            flex-direction: column;
            justify-content: flex-end;
        }

        .ul-content li div {
            font-size: 0.65rem;
            padding: 0 0.15rem 0.15rem 0;
            border: 0.05rem solid #ccc;
            border-radius: 0.25rem;
        }

        .ul-content .vibrate {
            animation: shake 0.5s 1;
        }

        .ul-content li:nth-of-type(1) {
            background-position: -2rem -13.7rem;
        }

        .ul-content li:nth-of-type(1) div {
            border-color: #FBC18E;
        }

        .ul-content li:nth-of-type(2) {
            background-position: -8rem -0.94rem;
        }

        .ul-content li:nth-of-type(2) div {
            border-color: #FEB3C2;
        }

        .ul-content li:nth-of-type(3) {
            background-position: -2rem -7.28rem;
        }

        .ul-content li:nth-of-type(3) div {
            border-color: #B8F3F7;
        }

        #clockInTimeButton {
        }

        #clockInTimeContent {
            display: none;
            text-align: center;
        }

        #clockInTimeContent > div {
            width: 38%;
            margin: 1rem auto;
            border: 0.1rem solid antiquewhite;
            border-radius: 0.25rem;
            background-color: #fff;
            padding: 0.25rem;
        }

        #clockInTimeContent .div-item {
            display: flex;
        }

        #clockInTimeContent p {
            display: inline-block;
            font-size: 0.7rem;
            line-height: 1.4rem;
        }

        #clockInTimeContent .p-call {
        }

        #clockInTimeContent .p-num {
            text-align: center;
            width: 40%;
        }

        #clockInTimeContent .p-time {
            text-align: center;
            width: 60%;
        }

        #clockInTimeItem01 {
            border-color: #DC9B74 !important;
        }

        #clockInTimeItem01 p {
            color: #DC9B74;
        }

        #clockInTimeItem02 {
            border-color: #FF88A4 !important;
        }

        #clockInTimeItem02 p {
            color: #FF88A4;
        }

        #clockInTimeItem03 {
            border-color: #62D4CB !important;
        }

        #clockInTimeItem03 p {
            color: #62D4CB;
        }

        #clockInTimeButton {
            position: fixed;
            top: 4%;
            left: 3%;
            width: 4rem;
            height: 4rem;
            background-color: transparent;
            z-index: 88;
        }

        .container.active #clockInTimeContent {
            display: block;
        }

        .container.active .ul-content {
            display: none;
        }
    </style>
</head>
<body>
<div class="container">
    <div id="time"></div>
    <div id="clockInTimeButton"></div>
    <div id="clockInTimeContent">
        <div id="clockInTimeItem01"></div>
        <div id="clockInTimeItem02"></div>
        <div id="clockInTimeItem03"></div>
    </div>
    <ul class="ul-content">
        <li id="s01">
            <div id="01">当前签到0次</div>
        </li>
        <li id="s02">
            <div id="02">当前签到0次</div>
        </li>
        <li id="s03">
            <div id="03">当前签到0次</div>
        </li>
    </ul>
</div>


<script>
    var basePath = '<%=basePath%>';
    let sb;
    $(function () {

        /*setInterval(function () {
            /!*const hour = moment().hour();    // 获取小时（0-23）
            const minute = moment().minute(); // 获取分钟（0-59）
            const ymd=now.format('YYYY-MM-DD');*!/
            const now = moment();
            const dateTimeFormat = 'YYYY-MM-DD HH:mm:ss'; // 设置日期和时间的格式
            $('#date-time').text(now.format(dateTimeFormat)); // 更新div元素的内容
            /!*const s=Math.floor(Math.random() * 60);
            switch (hour) {
                case 8:
                    if (minute>50){
                        const ymd=now.format('YYYY-MM-DD');
                        $(".slideIn").each(function (){
                            const m="5"+generateRandomInteger(1);
                            const dateString=ymd+" 08:"+m+":"+s;
                            console.log(dateString);
                            if (now==moment(dateString, dateTimeFormat).toDate()){
                                $(this).click();
                            }
                        });
                    }
                    break;
                case 12:
                    $(".slideIn").each(function (){
                        const m= Math.floor(Math.random() * 60);
                        const dateString=ymd+" 12:"+m+":"+s;
                        console.log(dateString);
                        if (now==moment(dateString, dateTimeFormat).toDate()){
                            $(this).click();
                        }
                    });
                    break;
                case 6:
                    if (minute>30){
                        $(".slideIn").each(function (){
                            const m= Math.floor(Math.random() * 60);
                            const dateString=ymd+" 18:"+m+":"+s;
                            console.log(dateString);
                            if (now==moment(dateString, dateTimeFormat).toDate()){
                                $(this).click();
                            }
                        });
                    }
                    break;
                default:
                    break;
            }*!/
        }, 1000); // 每秒更新一次*/

        // 排序函数
        const numberSort = (data) => {
            data.sort((a, b) => {
                if (a.signDate.hours !== b.signDate.hours) {
                    return a.signDate.hours - b.signDate.hours;
                }
                if (a.signDate.minutes !== b.signDate.minutes) {
                    return a.signDate.minutes - b.signDate.minutes;
                }
                return a.signDate.seconds - b.signDate.seconds;
            })
        }
        // 添加时间dom函数
        const addTimeDom = (clockInTimeItemId, timeData) => {
            numberSort(timeData)//排序
            $(clockInTimeItemId).html(`<p class='p-call'>${'${timeData[0].account}'}</p>`)//添加手机号
            for (let i = 0; i < timeData.length; i++) {//添加时间
                console.log("帐号：" + timeData[i].account + " 时间：" + timeData[i].signDate);
                $(clockInTimeItemId).append(`
							<div class="div-item">
								<p class="p-num">第${'${i+1}'}次</p>
								<p class="p-time">${'${timeData[i].signDate.hours}'}:${'${timeData[i].signDate.minutes}'}:${'${timeData[i].signDate.seconds}'}</p>
							</div>
						`)
            }
        }
        $.ajax({
            url: basePath + "ea/singin/sajax_ea_isPhoneSignCount.jspa",
            type: "POST",
            async: true,
            dataType: "json",
            success: function (data) {
                var member = data;
                var oi = member.oi;
                if (oi.length > 0) {
                    addTimeDom('#clockInTimeItem01', oi)
                }
                var oz = member.oz;
                if (oz.length > 0) {
                    addTimeDom('#clockInTimeItem02', oz)
                }
                var og = member.og;
                if (og.length > 0) {
                    console.log(og.length);
                    addTimeDom('#clockInTimeItem03', og)
                }
                $("#01").text("当前签到" + oi.length + "次");
                $("#02").text("当前签到" + oz.length + "次");
                $("#03").text("当前签到" + og.length + "次");
            }
        });
        $("#s01,#s02,#s03").click(function () {
            var vale = $(this).find("div").attr("id");
            const itemLength = $(`#clockInTimeItem${'${vale}'}`).children('.div-item').length
            console.log(vale);
            if($(`#clockInTimeItem${'${vale}'}`).children('.p-call').length==0){
                let call=1;
                if(vale==01){
                    call=15801151282
                }else if(vale==02){
                    call=13641106263
                }else if(vale==03){
                    call=17610651655
                }
                $(`#clockInTimeItem${'${vale}'}`).append(`
                    <p class="p-call">${'${call}'}</p >
                `)
            }
            $.ajax({
                url: basePath + "ea/singin/sajax_ea_toSignPhone.jspa",
                type: "POST",
                async: true,
                dataType: "json",
                data: {
                    "signSite": vale
                },
                success: function (data) {
                    var member = data;
                    var signlist = member.signlist;
                    const signDate = member.signDate.split(' ')[1];
                    if (signlist.length > 0) {
                        // for (let i=0;i<signlist.length;i++){
                        // console.log("帐号："+signlist[i].account+" 时间："+signlist[i].signDate);
                        $(`#clockInTimeItem${'${vale}'}`).append(`
									<div class="div-item">
										<p class="p-num">第${'${itemLength+1}'}次</p>
										<p class="p-time">${'${signDate}'}</p>
									</div>
								`)
                        // <p class="p-time">:${signlist[itemLength].signDate.minutes}:${signlist[itemLength].signDate.seconds}</p>
                        // }
                    }
                    $("#" + vale).text("当前签到" + signlist.length + "次");
                }
            });
        });

        /*const lou01 = document.getElementById('01');
        const tian02 = document.getElementById('02');
        const gao03 = document.getElementById('03');*/

        // 点击事件
        let isCooldown = false;
        $(document).on('click', '.ul-content li', function () {
            if (isCooldown) return; //0.5秒之内只能点击一次

            isCooldown = true;
            $(this).addClass('vibrate') //设置动画

            // 设置1秒冷却定时器
            setTimeout(() => {
                isCooldown = false;
                $(this).removeClass('vibrate')
            }, 500); //0.5秒后返回初始状态
        })


        // 初始加载时更新
        updateTime();
        // 每秒更新一次
        setInterval(updateTime, 1000);

    });

    /*function sign_in() {
        lou01.click();
    }*/

    function generateRandomInteger(digits) {
        const min = Math.pow(10, digits - 1); // 例如，对于4位数字，最小值为1000
        const max = Math.pow(10, digits) - 1; // 例如，对于4位数字，最大值为9999
        return Math.floor(Math.random() * (max - min + 1)) + min;
    }

    // 时间显示
    function updateTime() {
        const now = new Date();
        const timeStr =
            now.getFullYear() + '-' +
            String(now.getMonth() + 1).padStart(2, '0') + '-' +
            String(now.getDate()).padStart(2, '0') + ' ' +
            String(now.getHours()).padStart(2, '0') + ':' +
            String(now.getMinutes()).padStart(2, '0') + ':' +
            String(now.getSeconds()).padStart(2, '0');
        document.getElementById('time').textContent = timeStr;
    }

    // 切换显示打卡时间
    $(document).on('click', '#clockInTimeButton', function () {
        $(".container").toggleClass('active')
    })
</script>
</body>
</html>
