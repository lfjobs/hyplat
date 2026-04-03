<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.tiantai.wfj.bo.TEshopCusCom" %>
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
    <title>抢单</title>
    <link rel="stylesheet" href="https://cache.amap.com/lbs/static/main1119.css"/>
    <link rel="stylesheet" href="<%=basePath%>css/ea/edmandServe/mapList.css"/>
    <script type="text/javascript">
        window._AMapSecurityConfig = {
            securityJsCode: "92985a9236cbdc3ef50593cba1c23b3f",
        };
    </script>
    <script type="text/javascript" src="https://webapi.amap.com/maps?v=2.0&key=72c1339d5b2b01c35970160ccabd0aba">
    </script>
    <script src="<%=basePath%>/js/ea/marketing/edmandServe/jquery.min.js"></script>
    <script src="<%=basePath%>js/font-size.js"></script>

</head>
<body>
<header>
    <ul>
        <li onclick="javascript:history.back(-1);">
            <img src="<%=basePath%>/images/WFJClient/pc/5l5c/img_03.png" alt=""/>
        </li>
        <li>
            位置信息
        </li>
        <li>
            <!-- 确定 -->
        </li>
    </ul>
    <div class="btn-top">
        <div id="btn1" onclick="ygauth()">用工认证</div>
        <div onclick="qdlist()">商圈</div>
    </div>
    <div>
        <div id="map-zoom"></div>
    </div>
</header>
<!-- 地图主体 -->
<div id="container"></div>
<div class="btn-bottom">
    <div onclick="qdlist()">收单抢单</div>
    <div onclick="publishpro()">项目发布</div>
</div>
<!-- 提示框 -->
<div class="div-prompt">
    <div class="box">
        <div class="close-btn">×</div>
        <div class="div-txt">提示文字</div>
    </div>
</div>
<!-- 抢单弹框 -->
<div class="order-grabbing">
    <div class="box">
        <div class="content">
            <div class="div-top">
                <input type="hidden" id="ddid"/>
                <p class="p-distance">距离：<span class="order-grabbing-distance"></span>米</p>
                <p>地址：<span class="order-grabbing-address"></span></p>
                <p>项目工种：<span class="order-grabbing-workType"></span></p>

                <p>标题：<span class="order-grabbing-title"></span></p>
                <%--<p>备注：<span class="order-grabbing-remarks"></span></p>
                <p>期望时间：<span class="order-grabbing-exDate"></span></p>--%>
                <p>期望价格：<span class="order-grabbing-expectPrice"></span></p>

                <p>联系人：<span class="order-grabbing-name"></span></p>
                <p>联系电话：<span class="order-grabbing-tel"></span></p>
            </div>
            <div class="div-bottom">
                <p class="cancel">取消</p>
                <p class="confirm" id="confirm">确认抢单</p>
            </div>
        </div>
    </div>
</div>
<!-- /ea/dserve/sajax_ea_saveServe.jspa -->
<script type="text/javascript">
    var basePath = "<%=basePath%>";
    var wts;
    var sccid = '<%=((TEshopCusCom)session.getAttribute("key_shop_cus_com"))!=null ?((TEshopCusCom)session.getAttribute("key_shop_cus_com")).getSccId():"" %>';
    let rawData = {"detailList": ""};

    // ddworktype
    const prompt = (divText) => {
        $('.div-prompt .div-txt').text(divText)
        $('.div-prompt').show()
    }

    // ----------------高德相关
    //地图加载
    const map = new AMap.Map("container", {
        viewMode: "2D", //默认使用 2D 模式
        zoom: 12, //地图级别
        // center: [116.397428, 39.90923], //地图中心点
        resizeEnable: true,
        animateEnable: true,
    })

    $(document).on('click', ".div-txt", function () {
        $("#panel").hide()
    });
    AMap.plugin(['AMap.PlaceSearch', 'AMap.AutoComplete', 'AMap.Geolocation', 'AMap.PlaceSearch', 'AMap.Geocoder',
            'AMap.MarkerCluster', 'AMap.IndexCluster', 'AMap.ToolBar'
        ],
        async function () {

            let tool = new AMap.ToolBar({
                position: 'LB'
            });
            // tool.position('LB')
            map.addControl(tool);

            // 批量地址逆解码
            const geocoderAsync = (lnglats) => {
                return new Promise((resolve, reject) => {
                    var geocoder = new AMap.Geocoder({
                        city: "全国", // city 指定进行编码查询的城市，支持传入城市名、adcode 和 citycode
                    })
                    /* for (var i = 0; i < Things.length; i++) {
                        Things[i]
                    } */

                    // console.log(lnglats)
                    function chunkArray(arr, size = 20) {
                        // 输入验证
                        if (!Array.isArray(arr)) {
                            throw new TypeError('输入必须为数组');
                        }
                        if (size <= 0) throw new RangeError('分块大小必须大于0');

                        const chunks = [];
                        for (let i = 0; i < arr.length; i += size) {
                            // 使用slice保证不修改原数组
                            chunks.push(arr.slice(i, i + size));
                        }
                        return chunks;
                    }

                    const chunkedArray = chunkArray(lnglats)
                    // console.log(chunkedArray)
                    let mergedDatas = [];
                    let ddddd = [];
                    chunkedArray.forEach((data) => {
                        //console.log('发送请求:', data.length, '条数据');
                        geocoder.getAddress(data, function (status, result) {
                            if (status === "complete" && result.info === "OK") {
                                let addresses = result.regeocodes.map((item) => {
                                    return item.formattedAddress
                                })
                                ddddd.unshift(...addresses)
                                const mergedData = addresses.map((address, index) => ({
                                    lnglat: lnglats[index], // 将经纬度数组转为字符串
                                    building: address
                                }))
                                mergedDatas.unshift(...mergedData)

                            } else {
                                reject(new Error("地理编码失败"))
                            }
                        })
                    })
                    // console.log(ddddd)
                    // console.log(mergedDatas)
                    resolve(mergedDatas)

                })
            }

            // 批量计算距离
            function calculateDistances(fixedPoint, dataSet) {
                // 转换固定点为高德坐标对象
                const fixedLngLat = new AMap.LngLat(...fixedPoint);
                return dataSet.map(item => {
                    const pointLngLat = new AMap.LngLat(
                        parseFloat(item.lnglat[0]),
                        parseFloat(item.lnglat[1])
                    );
                    const distance = AMap.GeometryUtil.distance(fixedLngLat, pointLngLat);
                    return {
                        ...item,
                        distance: Math.round(distance)
                    };
                });
            }

            // 获取定位
            const currentLocation = () => {
                return new Promise((resolve, reject) => {
                    // 定位
                    var geolocation = new AMap.Geolocation({
                        enableHighAccuracy: true, // 是否使用高精度定位，默认：true
                        timeout: 10000, // 设置定位超时时间，默认：无穷大
                        buttonOffset: new AMap.Pixel(10, 20), // 定位按钮的停靠位置的偏移量，默认：Pixel(10, 20)
                        offset: [10, 20], // 定位按钮的停靠位置的偏移量
                        zoomToAccuracy: true, //  定位成功后调整地图视野范围使定位位置及精度范围视野内可见，默认：false
                        position: 'RB',//  定位按钮的排放位置,  RB表示右下
                        showCircle: false,
                        showMarker: false
                    })
                    map.addControl(geolocation);
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
                        const position = [data.position.lng, data.position.lat]
                        map.setCenter(position);
                        // 逆地理编码
                        const geocoder = new AMap.Geocoder({
                            // city 指定进行编码查询的城市，支持传入城市名、adcode 和 citycode
                            city: '全国'
                        })
                        //const lnglat = [116.396574, 39.992706]
                        geocoder.getAddress(position, function (status, result) {
                            if (status === 'complete' && result.info === 'OK') {
                                // result为对应的地理位置详细信息
                                const address = result.regeocode.formattedAddress
                                // 点标记
                                const marker = new AMap.Marker({
                                    position: new AMap.LngLat(data.position.lng, data.position.lat), // 经纬度对象，也可以是经纬度构成的一维数组[116.39, 39.9]
                                    title: address
                                })
                                // address是地址信息position是经纬度
                                map.add(marker);
                                // marker.on("click", function (e) {
                                //   console.log(address)
                                // })
                                marker.setLabel({
                                    offset: new AMap.Pixel(0, 0), //设置文本标注偏移量
                                    content: `<div class='current-location'>${address}</div>`, //设置文本标注内容
                                    direction: 'top' //设置文本标注方位
                                })
                                let isCooldown = false;
                                marker.on('click', function (e) {
                                    if (isCooldown) return;
                                    isCooldown = true;
                                    console.log(address)
                                    console.log(position)
                                    setTimeout(() => {
                                        isCooldown = false;
                                    }, 300);
                                })
                                resolve(position)
                            }
                        })
                    }

                    function onError(data) {
                        // 定位出错
                        reject(new Error("定位出错"))

                    }
                })
            }

            // 获取地图zoom

            const getRawDataAsync = () => {
                return new Promise((resolve, reject) => {
                    $.ajax({
                        url: basePath + '/ea/dserve/sajax_ea_detailListByDate.jspa',
                        type: 'GET',//POST
                        data: {
                            "wts": wts,
                            "pageSize": 15,
                            "type":"1"
                        },
                        dataType: 'json',
                        success: function (response) {
                            if (response != null) {
                                if(response.flag=="1"){
                                    if (response.detailList.length>0){
                                        resolve({"detailList": response.detailList});
                                    }else {
                                        prompt("还没有可以抢的单子");
                                    }
                                }else {
                                    reject(response.error);
                                }
                            }
                        },
                        error: function (xhr, status, error) {
                            // 请求失败时的处理
                            console.log(error);
                            reject(error);
                        }
                    });
                })
            }

            function filterCoordinates(arr) {
                // 正则表达式说明：
                // ^-?       : 允许负号（经纬度可能为负）
                // \d+       : 至少1位整数
                // (\.\d+)?  : 可选的小数部分
                // ,         : 必须包含逗号分隔符
                // -?        : 第二个数值同样允许负号
                const coordRegex = /^-?\d+(\.\d+)?,-?\d+(\.\d+)?$/;

                return arr.filter(item => {
                    const coord = item.coordinate;
                    // 双重验证：1.存在性 2.格式正确性
                    if (!coord) return false;
                    const trimmedCoord = coord.toString().trim();
                    // 验证格式
                    if (!coordRegex.test(trimmedCoord)) return false;
                    const [longitude, latitude] = trimmedCoord.split(',').map(Number);
                    return longitude !== latitude;
                });
            }

            rawData = await getRawDataAsync();
            // 处理后的原始数据
            //console.log(rawData)
            const rawPoints = filterCoordinates(rawData.detailList);
            //console.log(rawPoints)

            // 验证数组是否是经纬度并排序
            function standardizeCoordinate(coord) {
                // 验证输入格式
                if (!Array.isArray(coord) || coord.length !== 2) {
                    throw new Error('输入必须是包含两个元素的数组');
                }

                // 转换为数字类型
                const [first, second] = coord.map(Number);

                // 验证数值有效性
                if (isNaN(first) || isNaN(second)) {
                    throw new Error('坐标包含非数值数据');
                }

                // 经纬度范围判断（核心逻辑）
                const isFirstLongitude = Math.abs(first) <= 180 && Math.abs(first) > 90;
                const isSecondLongitude = Math.abs(second) <= 180 && Math.abs(second) > 90;
                const isFirstLatitude = Math.abs(first) <= 90;
                const isSecondLatitude = Math.abs(second) <= 90;

                // 决策逻辑
                if (isFirstLongitude && isSecondLatitude) {
                    return [first, second]; // 已经是经度在前
                } else if (isSecondLongitude && isFirstLatitude) {
                    return [second, first]; // 需要交换顺序
                } else {
                    throw new Error(`无法确定坐标顺序: ${coord}`);
                }
            }

            // console.log(rawPoints)
            let points = rawPoints.map((item, index) => {
                const lnglat = standardizeCoordinate(item.coordinate.split(','))
                //console.log(lnglat)
                return {
                    //...item,
                    id:item.ddid,
                    name: item.ddcontactname,
                    tel: item.ddcontactphone,
                    workType: item.ddworktype,
                    lnglat: lnglat,
                    weight: 1,
                    title: item.ddtitle,
                    remarks: item.ddremark,
                    exDate: item.ddexdate,
                    expectPrice: item.ddexpectprice
                }
            })
            //console.log(points)


            let lnglatsss = points.map((item) => {
                return item.lnglat
            })
            //console.log(lnglatsss)

            // 编码转换
            const mergedDatas = await geocoderAsync(lnglatsss)

            // 获取定位
            const currentLocationMsg = await currentLocation()

            // 定位后计算距离
            const calculateDistancesMsg = calculateDistances(currentLocationMsg, points);


            // 显示数据整合
            let mapDate = points.map((item, index) => {
                return {
                    ...item,
                    building: mergedDatas[index].building,
                    distance: calculateDistancesMsg[index].distance
                }
            })

            //聚合点样式
            var _renderClusterMarker = function (context) {
                let mapZoom = $("#map-zoom").html()
                //context 为回调参数，
                //包含如下属性 marker:当前聚合点，count:当前聚合点内的点数量
                const clusterCount = context.count; //聚合点内点数量
                context.marker.setContent(
                    `<div class='contextNum'>${clusterCount}</div>`
                )
                //if(mapZoom>=12){
                let isCooldown = false
                context.marker.on('click', function (e) {
                    if (isCooldown) return;
                    isCooldown = true;

                    // ._amapMarker.originData
                    const clusterArr = []
                    context.clusterData.map((item) => {
                        item._amapMarker.originData.map((item) => {
                            clusterArr.push(...item)
                        })
                    })
                    console.log("聚合点点击")
                    console.log(clusterArr)//集合数据
                    //console.log(context.clusterData[0].lnglat)
                    setTimeout(() => {
                        isCooldown = false;
                    }, 300)
                })

            };
            //非聚合点样式
            var _renderMarker = function (context) {
                let mapZoom = $("#map-zoom").html()
                context.marker.setContent(
                    <%--<p>地址:${${context.data[0].building}}</p>--%>
                    `<div class='building-content'>
							<p>姓名:${'${context.data[0].name}'}</p>
							<p>手机号:${'${context.data[0].tel}'}</p>
							<p>项目分类:${'${context.data[0].workType}'}</p>
							<p class="distance">距离${'${context.data[0].distance}'}米</p>
						</div>
						`
                )
                //if(mapZoom>=12){
                let isCooldown = false
                context.marker.on('click', function (e) {
                    if (isCooldown) return;
                    isCooldown = true;
                    //console.log(context.data[0])
                    $("#ddid").val(`${'${context.data[0].id}'}`)
                    $(".order-grabbing-name").html(`${'${context.data[0].name}'}`)
                    $(".order-grabbing-tel").html(`${'${context.data[0].tel}'}`)
                    $(".order-grabbing-address").html(`${'${context.data[0].building}'}`)
                    $(".order-grabbing-distance").html(`${'${context.data[0].distance}'}`)
                    $(".order-grabbing-workType").html(`${'${context.data[0].workType}'}`)
                    $(".order-grabbing-title").html(`${'${context.data[0].title}'}`)
                    $(".order-grabbing-remarks").html(`${'${context.data[0].remarks}'}`)
                    $(".order-grabbing-exDate").html(`${'${context.data[0].exDate}'}`)
                    $(".order-grabbing-expectPrice").html(`${'${context.data[0].expectPrice}'}`)
                    $(".order-grabbing").show()
                    setTimeout(() => {
                        isCooldown = false;
                    }, 500)
                })

                let isCooldown2 = false
                // 确认抢单发送请求
                $("#confirm").on('click', function () {
                    if (isCooldown2) return;
                    isCooldown2 = true;
                    $.ajax({
                        url: basePath+'/ea/dserve/sajax_ea_saveServe.jspa',
                        type: 'GET',//POST
                        data: {
                            ddid: $("#ddid").val(),
                            sccid: sccid,
                            dsaddress: context.data[0].building,
                            coordinate: [context.data[0].lnglat.lng, context.data[0].lnglat.lat].toString()
                        },
                        dataType: 'json',
                        success: function (response) {
                            // 请求成功时的处理
                            console.log(response);
                            let flag=response.flag;
                            if(flag=="操作成功"){
                                document.location.href=basePath+"ea/dserve/ea_toPage_demandListBydssccid.jspa?sccid="+sccid+"&tle=1";
                            }else{
                                alert(flag);
                            }
                        },
                        error: function (xhr, status, error) {
                            // 请求失败时的处理
                            console.log(error)
                        }
                    })
                    setTimeout(() => {
                        isCooldown2 = false;
                    }, 500)
                })
            }
            let styles = [{
                //聚合量在1-10时，聚合点的样式
                url: "//a.amap.com/jsapi_demos/static/images/blue.png", //图标显示图片的地址
                size: new AMap.Size(32, 32), //图标显示图片的大小
                offset: new AMap.Pixel(-16, -16), //图标定位在地图上的位置相对于图标左上角的偏移值
                textColor: "#fff", //文字的颜色
            },
                {
                    //聚合量在11-100时，聚合点的样式
                    url: "//a.amap.com/jsapi_demos/static/images/green.png",
                    size: new AMap.Size(32, 32),
                    offset: new AMap.Pixel(-16, -16),
                    textColor: "#fff",
                },
                {
                    //聚合量在101-1000时，聚合点的样式
                    url: "//a.amap.com/jsapi_demos/static/images/orange.png",
                    size: new AMap.Size(36, 36),
                    offset: new AMap.Pixel(-18, -18),
                },
            ]
            map.plugin(["AMap.MarkerCluster"], function () {
                cluster = new AMap.MarkerCluster(
                    map, //地图实例
                    mapDate, //海量点数据，数据中需包含经纬度信息字段 lnglat
                    {
                        gridSize: 150, //数据聚合计算时网格的像素大小
                        maxZoom: 20,
                        renderClusterMarker: _renderClusterMarker, //上述步骤的自定义聚合点样式
                        renderMarker: _renderMarker, //上述步骤的自定义非聚合点样式
                        styles: styles,
                    }
                );
            });

        });

    //显示地图层级与中心点信息
    function logMapinfo() {
        var zoom = map.getZoom(); //获取当前地图级别
        //var center = map.getCenter(); //获取当前地图中心位置
        document.querySelector("#map-zoom").innerText = zoom
        //document.querySelector("#map-center").innerText = center.toString();
    }

    //绑定地图移动与缩放事件
    // map.on('moveend', logMapinfo)
    // map.on('zoomend', logMapinfo)


    // 关闭弹框
    $('.close-btn').click(function () {
        $(this).parents('.div-prompt').hide()
    })
    $('.cancel,.confirm').click(function () {
        $(this).parents('.order-grabbing').hide()
    })

    //发布项目
    function publishpro() {
        if (sccid == null || sccid == "") {
            document.location.href = basePath + "page/WFJClient/NewLogin.jsp?loginPage=login";
        } else {
            //document.location.href = basePath + "/ea/dserve/ea_toaddpage.jspa?tle=1";
            document.location.href = basePath + "/page/ea/main/marketing/edmandServe/workType_save.jsp";
        }

    }


    //收单信息和发布记录
    function sdinfo() {
        if (sccid == null || sccid == "") {
            document.location.href = basePath + "page/WFJClient/NewLogin.jsp?loginPage=login";
        } else {
            document.location.href = basePath + "ea/dserve/ea_detailListBySccid.jspa?staffid=" + staffId + "&sccId=" + sccid + "&tle=1&type=0";
        }

    }

    //抢单列表
    function qdlist() {

        if (sccid == null || sccid == "") {

            document.location.href = basePath + "page/WFJClient/NewLogin.jsp?loginPage=login";

        } else {
            document.location.href = basePath + "ea/dserve/ea_toPage_demandList.jspa?wts=" + wts + "&sccid=" + sccid + "&tle=1";
        }

    }

    //用工认证
    function ygauth() {
        if (sccid == null || sccid == "") {
            document.location.href = basePath + "page/WFJClient/NewLogin.jsp?loginPage=login";
        } else {
            document.location.href = basePath + "page/ea/main/marketing/edmandServe/workType_list.jsp?";
        }
    }
</script>
</body>
</html>
