<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ page language="java" import="com.tiantai.wfj.bo.TEshopCusCom" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>


<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <script src="<%=basePath%>js/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <%--<script src="<%=basePath%>js/font-size.js" type="text/javascript" charset="utf-8"></script>--%>

    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/WFJClient/pc/5l5C/5L5C_index.css">
    <script src="<%=basePath%>js/WFJClient/pc/common.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath%>js/WFJClient/pc/5l5C/5l5c_index.js" type="text/javascript" charset="utf-8"></script>
    <script src="https://res.wx.qq.com/open/js/jweixin-1.6.0.js" type="text/javascript"></script>
    <script src="https://res2.wx.qq.com/open/js/jweixin-1.6.0.js " type="text/javascript"></script>
    <script type="text/javascript">
        window._AMapSecurityConfig = {
            securityJsCode: "92985a9236cbdc3ef50593cba1c23b3f",
        };
    </script>
    <script type="text/javascript"
            src="https://webapi.amap.com/maps?v=2.0&key=72c1339d5b2b01c35970160ccabd0aba"></script>

    <title>5L5C</title>

</head>
<body>
<header>
    <ul class="clearfix">
        <li>
            <!-- <img src="<%=basePath%>images/WFJClient/pc/newimg/return.png" > -->
        </li>
        <li>
            5L5C
        </li>
        <li>
        </li>
    </ul>
</header>
<div class="content">
    <div class="div-search">
        <div class="search-box clearfix">
            <div class="div-left">
                <p><img src="<%=basePath%>images/WFJClient/pc/newimg/img_13.png" alt=""></p>
            </div>
            <input type="text" placeholder="请输入搜索内容">
        </div>
    </div>
    <div class="div-banner clearfix">
        <section>
            <a href="javascript:sys();">
                <div>
                    <img src="<%=basePath%>images/WFJClient/pc/newimg/img_24.png" alt="">
                </div>
                <p>
                    扫一扫
                </p>
            </a>
        </section>
        <section>
            <a href="javascript:skm();">
                <div>
                    <img src="<%=basePath%>images/WFJClient/pc/newimg/img_25.png" alt="">
                </div>
                <p>
                    收款码
                </p>
            </a>
        </section>
    </div>
    <div class="div-ul-content">
        <div class="div-text clearfix">
            <div class="div-img">
                <div>
                    <img src="<%=basePath%>images/WFJClient/pc/newimg/img_26.png" alt="">
                </div>
                <p>
                    5L5C
                </p>
            </div>
            <div class="div-right clearfix">
                <ul class="clearfix">
                    <li><a href="<%=basePath%>page/WFJClient/pc/5l5C/selectCompany.jsp?sccId=${sccId}">5L5CISERP&bull;AIS</a>
                    </li>
                    <li>&nbsp;&nbsp;&nbsp;&nbsp;</li>

                    <li><a href="<%=basePath%>page/WFJClient/pc/5l5C/selectCompany.jsp?sccId=${sccId}&bd=xcx">小程序</a>
                    </li>
                </ul>
                <div class="div-img2">
                    <img src="<%=basePath%>images/WFJClient/pc/newimg/img_39.png" alt="">
                </div>
            </div>
        </div>
        <%--<div class="div-text div-text2 clearfix">--%>
        <%--<div class="div-img">--%>
        <%--<div>--%>
        <%--<img src="<%=basePath%>images/WFJClient/pc/newimg/img_27.png" alt="">--%>
        <%--</div>--%>
        <%--<p>--%>
        <%--物联网--%>
        <%--</p>--%>
        <%--</div>--%>
        <%--<div class="div-right clearfix">--%>
        <%--<ul class="clearfix">--%>
        <%--<li>移动称重</li>--%>
        <%--<li>停车收费</li>--%>
        <%--<li>终端收银</li>--%>
        <%--<li>终端考勤</li>--%>
        <%--</ul>--%>
        <%--<div class="div-img2">--%>
        <%--<img src="<%=basePath%>images/WFJClient/pc/newimg/img_39.png" alt="">--%>
        <%--</div>--%>
        <%--</div>--%>
        <%--</div>--%>
        <div class="div-text div-text3 clearfix">
            <div class="div-img">
                <div>
                    <img src="<%=basePath%>images/WFJClient/pc/newimg/img_28.png" alt="">
                </div>
                <p>
                    商圈
                </p>
            </div>
            <div class="div-right clearfix">
                <ul class="clearfix">
                    <li><a href="javascript:ygauth();" class="ygauth">用工认证</a></li>
                    <li><a href="javascript:sdinfo();">收单抢单</a></li>
                    <%--
                                        <li><a href="javascript:kfbd();">客户帮单</a></li>
                                        <li>帮圈</li>--%>
                    <li><a href="javascript:qdlist();">工程抢单</a></li>
                    <li><a href="javascript:publishpro();">项目发布</a></li>
                    <li><a href="javascript:sdinfo();">发布记录</a></li>
                    <li>开启语音</li>
                </ul>
                <div class="div-img2">
                    <img src="<%=basePath%>images/WFJClient/pc/newimg/img_39.png" alt="">
                </div>
            </div>
        </div>

    </div>
    <div class="content-bottom"></div>
</div>
<!-- 弹框载体 -->

<div id="pop-up"
     style="position: fixed;top: 0;left: 0; width: 100%;height: 100%; background-color: rgba(0,0,0,0.3);display: none;"></div>
<div class="footer div-bottom">
    <ul class="clearfix">
        <li>
            <div>
                <img src="<%=basePath%>images/WFJClient/pc/newimg/img_23.png" alt="">
            </div>
            <p>
                消息
            </p>
        </li>
        <li>
            <div>
                <img src="<%=basePath%>images/WFJClient/pc/newimg/img_38.png" alt="">
            </div>
            <p>
                通讯
            </p>
        </li>
        <li>
            <div>
                <img src="<%=basePath%>images/WFJClient/pc/newimg/img_08.jpg" alt="">
            </div>
            <p>
                数字
            </p>
        </li>
        <li class="active">
            <div>
                <img src="<%=basePath%>images/WFJClient/pc/newimg/img_37.png" alt="">
            </div>
            <p>
                5L5C
            </p>
        </li>
        <li>
            <div>
                <img src="<%=basePath%>images/WFJClient/pc/newimg/img_11.png" alt="">
            </div>
            <p>
                我的
            </p>
        </li>
    </ul>
</div>

<div class="div-tingyong">
    <div class="box">
        <p>扫描结果<img class="close-tingyong" src="<%=basePath%>images/ea/office/contract/stamp/img_031.png" alt=""/></p>
        <div class="div-box">
            <p class="titlep"></p>
            <div class="clearfix">
                <p class="right close-tingyong">确定</p>
            </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
    var basePath = "<%=basePath%>";
    var sccid = '<%=((TEshopCusCom)session.getAttribute("key_shop_cus_com"))!=null ?((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getSccId():"" %>';
    var companyId = '<%=((TEshopCusCom)session.getAttribute("key_shop_cus_com"))!=null ?((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getCompanyId():"" %>';
    var staffId = '<%=((TEshopCusCom)session.getAttribute("key_shop_cus_com"))!=null ?((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getStaffid():"" %>';

    var user = '<%=((TEshopCusCom)session.getAttribute("key_shop_cus_com"))!=null ?((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getAccount():"" %>';
    let pageNumber = 1;
    let recordCount=0;
    let pageCount;
    let list=new Array();
    let hylist=new Array();
    let listindex = 1;
    let dsaddress = "";
    let coordinate = "";

    AMap.plugin(['AMap.Geolocation', 'AMap.PlaceSearch', 'AMap.Geocoder'], function () {
        // 定位
        var geolocation = new AMap.Geolocation({
            enableHighAccuracy: true, // 是否使用高精度定位，默认：true
            timeout: 10000, // 设置定位超时时间，默认：无穷大
            buttonOffset: new AMap.Pixel(10, 20), // 定位按钮的停靠位置的偏移量，默认：Pixel(10, 20)
            // offset: [10, 20], // 定位按钮的停靠位置的偏移量
            position: 'RB' //  定位按钮的排放位置,  RB表示右下
        });
        geolocation.getCurrentPosition(function (status, result) {
            if (status == 'complete') {
                onComplete(result)
            } else {
                onError(result)
            }
        });

        function onComplete(data) {
            // data是具体的定位信息
            // console.log(data.position.lng)
            // console.log(data.position.lat)
            // console.log(data)
            const position = [data.position.lng, data.position.lat]
            //map.setCenter(position);
            // 逆地理编码
            const geocoder = new AMap.Geocoder({
                // city 指定进行编码查询的城市，支持传入城市名、adcode 和 citycode
                city: '全国'
            });
            geocoder.getAddress(position, function (status, result) {
                if (status === 'complete' && result.info === 'OK') {
                    const address = result.regeocode.formattedAddress
                    dsaddress = address;
                    coordinate = position;
                }
            });
        }

        function onError(data) {
            console.log('定位出错');
        }
    });

    // 弹框出现
    $(document).on('click', '#btn', function () {
        $('#pop-up').show()
        speakText()//播放语音
    })

    // 点击事件
    // 小x关闭
    $(document).on('click', '.pop-close', function () {
        $('#pop-up').hide()
        stopSpeech()//终止语音
    });

    // 抢单按钮
    $(document).on('click', '.pop-order-grabbing', function () {
        $('#pop-up').hide();
        const ddid = $(this).parent().parent().find("#ddid").val();
        console.log(dsaddress);
        $.ajax({
            url: basePath + "/ea/dserve/sajax_ea_saveServe.jspa?",
            type: "POST",
            dataType: "json",
            data: {
                "sccid": sccid,
                "ddid": ddid,
                "dsaddress": dsaddress,
                "coordinate": coordinate
            },
            success: function (response) {
                // 请求成功时的处理
                console.log(response);
                let flag = response.flag;
                if (flag == "操作成功") {
                    document.location.href = basePath + "ea/dserve/ea_toPage_demandListBydssccid.jspa?sccid=" + sccid+"&tle=1";
                } else {
                    alert(flag);
                }
            },
            error: function (xhr, status, error) {
                // 请求失败时的处理
                console.log(error)
            }
        });
    });

    // 下一个按钮
    $(document).on('click', '.pop-next', function () {
        $('#pop-up').hide();
        x();
    });

    // 翻页点击
    $(document).on('click','.pop-forward',function(){
        if(listindex>1){
            listindex-=1
            $('.pop-page-number').html(listindex);
            x();
        }
    });
    $(document).on('click','.pop-backward',function(){
        if(listindex<=999){
            listindex++;
            //pageNumber++
            $('.pop-page-number').html(listindex);
            x();
        }
    });


    // 开始播放
    function speakText() {
        const text = '您有一条新的抢单通知，请及时查看';// 朗读内容
        const utterance = new SpeechSynthesisUtterance(text);
        utterance.lang = 'zh-CN'; // 中文语言
        utterance.rate = 0.9; // 语速：正常速度
        utterance.pitch = 1.0; // 音高：默认值
        utterance.volume = 1.0; // 音量：最大音量
        const voices = speechSynthesis.getVoices().filter(v => v.lang.includes('zh'));
        if (voices.length > 0) {
            utterance.voice = voices[0];
        }
        speechSynthesis.speak(utterance);
    }

    // 终止播放
    function stopSpeech() {
        speechSynthesis.cancel();
    }


    function x() {
        if (listindex < list.length) {
            console.log(list[listindex-1]);
            const str = new Array();
            str.push('<div class="pop-box" style="max-width: 80%;margin: 20vh auto 0 auto;background-color: #fff;border-radius: 0.3rem;">');
            str.push('<input type="hidden" value="' + list[listindex-1].ddid + '" id="ddid"/>');
            str.push('<div class="pop-header" style="position: relative;">');
            str.push('<p style="background-color: #f74c31;line-height: 3rem;font-size: 1rem; color: #fff; text-align: center;border-top-left-radius: 0.3rem;border-top-right-radius: 0.3rem;">抢单服务</p>');
            str.push('<span class="pop-close" style="position: absolute;display: block;width: 1.2rem;height:1.2rem;line-height: 1rem;background-color: #fff;border: 0.1rem solid #f74c31;border-radius: 1rem;text-align: center;right: -0.5rem;top: -0.5rem;font-size: 0.7rem;">x</span>');
            str.push('</div>');
            str.push('<div class="pop-content" style="background-color: #fff;padding: 0.5rem 1rem 0 1rem;">');
            str.push('<div class="pop-content-item" style="display: flex;align-items: center;padding: 0.2rem 0;">');
            str.push('<label for="" style="width: 25%;font-size: 0.95rem;">发布项目:</label>');
            str.push('<input type="text" readonly style="outline: none; width: 75%;height: 2.2rem;font-size: 0.95rem;border: 0;border-bottom: 0.05rem solid #ccc;" value="' + list[listindex-1].title + '" />');
            str.push('</div>');
            str.push('<div class="pop-content-item" style="display: flex;align-items: center;padding: 0.2rem 0;">');
            str.push('<label for="" style="width: 25%;font-size: 0.95rem;">注册时间:</label>');
            str.push('<input type="text" readonly style="outline: none;width: 75%;height: 2.2rem;font-size: 0.95rem;border: 0;border-bottom: 0.05rem solid #ccc;" value="' + list[listindex-1].date + '" />');
            str.push('</div>');
            str.push('<div class="pop-content-item" style="display: flex;align-items: center;padding: 0.2rem 0;">');
            str.push('<label for="pop-tel" style="width: 25%;font-size: 0.95rem;">用户账号:</label>');
            str.push('<input type="text" readonly id="pop-tel" style="outline: none;width: 75%;height: 2.2rem;font-size: 0.95rem;border: 0;border-bottom: 0.05rem solid #ccc;" value="' + hidePhone(list[listindex-1].phone) + '" />');
            str.push('</div>');
            str.push('<div class="pop-content-item" style="display: flex;align-items: center;padding: 0.2rem 0;">');
            str.push('<label for="pop-name" style="width: 25%;font-size: 0.95rem;">用户名:</label>');
            str.push('<input type="text" readonly id="pop-name" style="outline: none;width: 75%;height: 2.2rem;font-size: 0.95rem;border: 0;border-bottom: 0.05rem solid #ccc;" value="' + list[listindex-1].name + '" />');
            str.push('</div></div>');
            str.push('<div class="pop-pagination" style="display: flex;align-items: center;justify-content: space-around;font-size: 0.65rem;">');
            str.push('<div class="pop-forward" style="line-height: 2rem;width: 1rem;text-align: center;font-size: 1.5rem;"><</div>');
            str.push('<div><span class="pop-page-number">'+listindex+'</span>/'+recordCount+'</div>');
            str.push('<div class="pop-backward" style="line-height: 2rem;width: 1rem;text-align: center;font-size: 1.5rem;">></div>');
            str.push('</div>');
            str.push('<div class="pop-button" style="background-color: #fff;border-bottom-left-radius: 0.3rem;border-bottom-right-radius: 0.3rem;display: flex;align-items: center;justify-content: space-evenly;padding: 0.15rem 0 1rem 0;">');
            str.push('<input type="button" value="抢单" class="pop-order-grabbing" style="width: 5rem;height: 2rem;background-color: #f74c31;border: 0;border-radius: 0.25rem;font-size: 1rem;color: #fff;">');
            /*str.push('<input type="button" value="下一个" class="pop-next" style="width: 6rem;height: 2.2rem;background-color: #f74c31;border: 0;border-radius: 0.25rem;font-size: 0.95rem;color: #fff;" />');*/
            str.push('</div></div>');
            $('#pop-up').html(str.join(""));
            $("#pop-up").show();
            speakText();
        } else {
            if (pageNumber <= pageCount) {
                pageNumber = pageNumber + 1;
                d();
            }
        }
    }

    function b() {
        // 发送AJAX并存储数据到前端
        $.ajax({
            url: basePath + "/ea/dserve/sajax_ea_detailhyListBydssccid.jspa?",
            type: "POST",
            dataType: "json",
            success: function (data) {
                var member=eval("("+data+")")
                console.log(member)
                hylist=member.hylist;
                console.log(hylist);
                d();
            }
        });
    }

    function d() {
        // 发送AJAX并存储数据到前端
        $.ajax({
            url: basePath + "/ea/dserve/sajax_ea_zhuceqdList.jspa?",
            type: "POST",
            dataType: "json",
            data: {
                "pageNumber": pageNumber
            },
            success: function (data) {
                console.log(data)
                // 1. sessionStorage：会话级，关闭浏览器失效
                //sessionStorage.setItem("qdlist", data.qdlist);
                if (hylist.length>0){
                    for(var i=0;i<data.qdlist.length;i++){
                        for(var j=0;j<hylist.length;j++){
                            if(data.qdlist[i].ddid==hylist[j][0]){
                                continue;
                            }
                            if(j==hylist.length-1){
                                list.push(data.qdlist[i]);
                            }
                        }
                    }
                }else {
                    list=list.concat(data.qdlist);
                }

                //list=list.concat(data.qdlist);
                console.log(list);
                pageNumber = data.pageNumber;
                pageCount = data.pageCount;
                recordCount=data.recordCount;
                x();
                //alert("前端存储成功！");
            }
        });
    }

    // 定义电话号码隐藏函数
    function hidePhone(phone) {
        // 校验是否为11位手机号
        if (!/^1[3-9]\d{9}$/.test(phone)) {
            return phone; // 非11位手机号返回原内容
        }
        // 替换中间4位为星号
        return phone.replace(/(\d{3})\d{4}(\d{4})/, '$1****$2');
    }
</script>
</html>
