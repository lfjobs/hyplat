<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta http-equiv="Access-Control-Allow-Origin" content="*">
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no, width=device-width">
    <title>搜索</title>
    <link rel="stylesheet" href="https://cache.amap.com/lbs/static/main1119.css"/>
    <link rel="stylesheet" href="<%=basePath%>css/ea/production/qrshare/siteMap.css"/>
    <style type="text/css">
        #panel {
            position: fixed;
            top: 100px;
            left: 0;
            width: 100%;
        }
    </style>
    <script type="text/javascript">
        window._AMapSecurityConfig = {
            securityJsCode: "aee832f98d13d816205012d808a20b35",
        };
    </script>
    <script type="text/javascript" src="https://webapi.amap.com/maps?v=2.0&key=4bb7b1f5a14c79e2b2b0d06100b8b7e9"></script>
    <script src="<%=basePath%>/js/ea/marketing/edmandServe/jquery.min.js"></script>
    <script src="<%=basePath%>js/font-size.js"></script>
</head>
<body>
<header>
    <ul>
        <li onclick="closepage()">
            <img src="<%=basePath%>images/ea/production/qrshare/back.png" alt=""/>
        </li>
        <li>
            位置信息
        </li>
        <li>
            <!-- 确定 -->
        </li>
    </ul>
    <div class="div-search">
        <div class="changeCity"><span class="selectCity">全国</span>&nbsp;<img style="width: 0.6rem" src="<%=basePath%>images/ea/production/qrshare/downarrow.png">&nbsp;&nbsp;<span style="color:#d5d5d5">|</span></div>
        <img style="width: 0.6rem" src="<%=basePath%>images/WFJClient/pc/newimg/img_13.png"><input type="" id="myInput" placeholder="请输入搜索地址" class="">
        <div class="div-txt">&nbsp;</div>
    </div>
</header>
<!-- 地图主体 -->
<div id="container"></div>
<!-- 搜索结果 -->
<div id="panel"></div>
<!-- 切换城市 -->
<div class="city-picker">
    <div class="province-list" id="provinceList"></div>
    <div class="letter-index" id="letterIndex"></div>
</div>
<!-- 提示框 -->
<div class="div-prompt">
    <div class="box">
        <div class="close-btn">×</div>
        <div class="div-txt">提示文字</div>
    </div>
</div>
<script type="text/javascript">
    var address = "";
    var position = "";
    var originPage = '${"${param.originPage}"}';

    $("header>li:eq(0)").click(function () {
        if (originPage != "" && originPage != null) {
            requestPage = originPage.split("-");
            if (requestPage[0] == "win") {
                closepage();
            }
        }
    })

    const prompt = (divText) => {
        $('.div-prompt .div-txt').text(divText)
        $('.div-prompt').show()
    }
    // ----------------高德相关
    //地图加载
    const map = new AMap.Map("container", {
        viewMode: "2D", //默认使用 2D 模式
        zoom: 15, //地图级别
        // center: [116.397428, 39.90923], //地图中心点
        resizeEnable: true,
    })

    $(document).on('click', ".div-txt", function () {
        $("#panel").hide();
    });
    AMap.plugin(['AMap.PlaceSearch', 'AMap.AutoComplete', 'AMap.Geolocation', 'AMap.PlaceSearch', 'AMap.Geocoder'],
        function () {
            //构造地点查询类
            const placeSearch = new AMap.PlaceSearch({
                pageSize: 5, // 单页显示结果条数·
                pageIndex: 1, // 页码
                city: "全国", // 兴趣点城市
                citylimit: false, //是否强制限制在设置的城市内搜索
                map: map, // 展现结果的地图实例
                panel: "panel", // 结果列表将在此容器中进行展示。
                autoFitView:true, // 是否自动调整地图视野使绘制的 Marker点都处于视口的可见范围
            });

            // 搜索
            function search(city, keyword) {
                $("#panel").show()
                placeSearch.city = city;
                placeSearch.search(keyword, (status, result) => {
                    if (status === 'complete' && result.poiList) {
                        searchdom = result.poiList.pois;
                    }
                })
                if ($('#myInput').val() === "") {
                    $("#panel").hide()
                }
            }

            // 点击事件
            $(document).on('click', ".poibox", function () {
                const index = $(this).index();
                console.log(searchdom[index].name); //名称
                if(searchdom[index].pname==searchdom[index].cityname){
                    address = searchdom[index].cityname+searchdom[index].adname+searchdom[index].address; //地址

                }else{
                    address = searchdom[index].pname+searchdom[index].cityname+searchdom[index].adname+searchdom[index].address; //地址

                }
                console.log("地址"+address); //地址
                position = `${'${searchdom[index].location.lat}'},${'${searchdom[index].location.lng}'}`; //经纬度
                console.log("经纬度"+position); //名称
                closeIframe(address,position);
            });
            let searchdom;
            document.getElementById('myInput').addEventListener('input', (event) => {
                const keyword = event.target.value.trim();
                const changeCity = $('.changeCity').html()
                if (keyword) {
                    search(changeCity, keyword)
                }
            });
            // 定位
            var geolocation = new AMap.Geolocation({
                enableHighAccuracy: true, // 是否使用高精度定位，默认：true
                timeout: 10000, // 设置定位超时时间，默认：无穷大
                buttonOffset: new AMap.Pixel(10, 20), // 定位按钮的停靠位置的偏移量，默认：Pixel(10, 20)
                // offset: [10, 20], // 定位按钮的停靠位置的偏移量
                zoomToAccuracy: true, //  定位成功后调整地图视野范围使定位位置及精度范围视野内可见，默认：false
                position: 'RB' //  定位按钮的排放位置,  RB表示右下
            })
            geolocation.getCurrentPosition(function (status, result) {
                if (status == 'complete') {
                    // const province = result.addressComponent.province;
                    // console.log(province)
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
                position = [data.position.lng, data.position.lat]
                map.setCenter(position);

                // 逆地理编码
                const geocoder = new AMap.Geocoder({
                    // city 指定进行编码查询的城市，支持传入城市名、adcode 和 citycode
                    city: '全国'
                });

                //const lnglat = [116.396574, 39.992706]
                geocoder.getAddress(position, function (status, result) {
                    if (status === 'complete' && result.info === 'OK') {
                        // result为对应的地理位置详细信息
                        address = result.regeocode.formattedAddress
                        // 点标记
                        const marker = new AMap.Marker({
                            position: new AMap.LngLat(data.position.lng, data.position
                                .lat), // 经纬度对象，也可以是经纬度构成的一维数组[116.39, 39.9]
                            title: address
                        })
                        // address是地址信息position是经纬度
                        map.add(marker);
                        // marker.on("click", function (e) {
                        //   console.log(address)
                        // })
                        marker.setLabel({
                            offset: new AMap.Pixel(0, 0), //设置文本标注偏移量
                            content: `<div class='current-location'>${'${address}'}</div>`, //设置文本标注内容
                            direction: 'top' //设置文本标注方位
                        })




                        $(document).on('click', ".current-location", function () {
                            // 点击标签
                            consolesear.log(address)
                            console.log(position)

                            closeIframe(address,position);
                        })
                    }
                });
            }

            function onError(data) {



                // 定位出错
              //  console.log(prompt('定位出错')
            }
        });
    // 地图点击
    map.on('click', function (e) {
        const lnglat = e.lnglat;
        const placeSearch = new AMap.PlaceSearch({
            pageSize: 1,
            pageIndex: 1
        });
        // 第三个参数300是搜索范围(米) 如果过小会导致经常搜不到POI
        placeSearch.searchNearBy('', lnglat, 300, function (status, result) {
            if (status === 'complete' && result.info === 'OK') {
                // console.log(result.poiList.pois[0]);
                const nearestPOI = result.poiList.pois[0];
                position = nearestPOI.location.lng+','+nearestPOI.location.lat;//经纬度
                console.log("经纬度"+nearestPOI.location.lng,nearestPOI.location.lat);
                address = nearestPOI.address; //地址
                console.log("地址:"+address);

                closeIframe(address,position);
            } else {
                prompt('未找到附近的POI');
            }
        });
    });

    // -----------------选择城市相关
    const provinces = [
        {
            name: '北京',
            letter: 'B'
        },
        {
            name: '天津',
            letter: 'T'
        },
        {
            name: '河北',
            letter: 'H'
        },
        {
            name: '山西',
            letter: 'S'
        },
        {
            name: '内蒙古',
            letter: 'N'
        },
        {
            name: '辽宁',
            letter: 'L'
        },
        {
            name: '吉林',
            letter: 'J'
        },
        {
            name: '黑龙江',
            letter: 'H'
        },
        {
            name: '上海',
            letter: 'S'
        },
        {
            name: '江苏',
            letter: 'J'
        },
        {
            name: '浙江',
            letter: 'Z'
        },
        {
            name: '安徽',
            letter: 'A'
        },
        {
            name: '福建',
            letter: 'F'
        },
        {
            name: '江西',
            letter: 'J'
        },
        {
            name: '山东',
            letter: 'S'
        },
        {
            name: '河南',
            letter: 'H'
        },
        {
            name: '湖北',
            letter: 'H'
        },
        {
            name: '湖南',
            letter: 'H'
        },
        {
            name: '广东',
            letter: 'G'
        },
        {
            name: '广西',
            letter: 'G'
        },
        {
            name: '海南',
            letter: 'H'
        },
        {
            name: '重庆',
            letter: 'C'
        },
        {
            name: '四川',
            letter: 'S'
        },
        {
            name: '贵州',
            letter: 'G'
        },
        {
            name: '云南',
            letter: 'Y'
        },
        {
            name: '西藏',
            letter: 'X'
        },
        {
            name: '陕西',
            letter: 'S'
        },
        {
            name: '甘肃',
            letter: 'G'
        },
        {
            name: '青海',
            letter: 'Q'
        },
        {
            name: '宁夏',
            letter: 'N'
        },
        {
            name: '新疆',
            letter: 'X'
        },
        {
            name: '香港',
            letter: 'X'
        },
        {
            name: '澳门',
            letter: 'A'
        },
        {
            name: '台湾',
            letter: 'T'
        }
    ].sort((a, b) => a.letter.localeCompare(b.letter));

    // 初始化省份列表
    function initProvinceList() {
        let html = '';
        let currentLetter = '';

        provinces.forEach(province => {
            if (province.letter !== currentLetter) {
                html += `<div class="letter-title">${"${province.letter}"}</div>`;
                currentLetter = province.letter;
            }
            html += `<div class="province-item" data-name="${'${province.name}'}">${'${province.name}'}</div>`;
        });

        $('#provinceList').html(html);
    }

    // 初始化字母索引
    function initLetterIndex() {
        const letters = [...new Set(provinces.map(p => p.letter))];
        let html = letters.map(l => `<div>${'${l}'}</div>`).join('');
        $('#letterIndex').html(html);
    }

    // 字母点击事件
    $('#letterIndex').on('click', 'div', function () {
        const letter = $(this).text();
        const target = $(`.letter-title:contains(${'${letter}'})`);
        if (target.length) {
            const offset = target.offset().top - $('.province-list').offset().top;
            $('.province-list').animate({
                scrollTop: offset
            }, 300);
        }
    });

    // 选择省份事件
    $('#provinceList').on('click', '.province-item', function () {
        const province = $(this).data('name');
        $('.city-picker').hide();
        $('.selectCity').text(province);
    });
    $('.div-search').on('click', '.changeCity', function () {
        $('.city-picker').show()
    });
    $(document).ready(() => {
        initProvinceList();
        initLetterIndex();
    });
    // 关闭弹框
    $('.close-btn').click(function () {
        $(this).parents('.div-prompt').hide()
    });
    
    function closeIframe(address,position) {
        let param={
            "address":address,
            "coordinate":position
        }
        //console.log(param);
        window.parent.page_address(param);
    }

    function closepage() {
        window.parent.$('.iframecom').hide();
        window.parent.$('#iframepub').attr("src", "");
    }
</script>
</body>
</html>
