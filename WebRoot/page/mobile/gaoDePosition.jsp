<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<style type="text/css">
    #container {width:100%; height: 200px; }
    p.my-desc {
        margin: 5px 0;
        line-height: 150%;
        font-size:12px;
    }

	.amap-ui-smp-ifwn-info-title {
		font-size:15px;
		font-weight:bold;
	}
	.amap-marker-label {
		border: 1px solid #ccc;
	}

</style>

<div id="container" tabindex="0"></div>

<script type="text/javascript" src="https://webapi.amap.com/maps?v=1.4.15&key=f24dd8c87226673baeba0239b2863a6d&plugin=AMap.Geocoder"></script>
<!-- UI组件库 1.0 -->
<script src="//webapi.amap.com/ui/1.0/main.js?v=1.0.11"></script>

<script type="text/javascript">

    var jd = "${param.jd}";
    var wd = "${param.wd}";
    var name = "${param.name}";
    if(jd!=""&&wd!=""){
        var map = new AMap.Map('container', {
            resizeEnable: true,
            center:[jd,wd],
            zoom:12
        });
        var geocoder = new AMap.Geocoder({
            city: "", //城市设为北京，默认：“全国”
            radius: 500 //范围，默认：500
        });
        regeoCode();
        var marker = new AMap.Marker({
            map:map,
            position:[jd,wd],
            label: {
                offset: new AMap.Pixel(15, 20),//修改label相对于maker的位置
                content: "去这里"
            }
        })
        marker.on('click',function(e){
            marker.markOnAMAP({
                name:address,
                position:marker.getPosition()
            })
        })

        AMap.plugin(['AMap.ToolBar'],function(){//异步同时加载多个插件
            var toolbar = new AMap.ToolBar();
            map.addControl(toolbar);


        });


    }else{
        $("#container").remove();
    }


    function regeoCode() {
        var lnglat = jd+","+wd;
        geocoder.getAddress(lnglat, function(status, result) {
            if (status === 'complete'&&result.regeocode) {
                address = result.regeocode.formattedAddress;
                  bulid  = result.regeocode.addressComponent.building;
                if(bulid==""){
                    bulid = "2";
                }

                AMapUI.loadUI(['overlay/SimpleInfoWindow'], function(SimpleInfoWindow) {

                    var marker = new AMap.Marker({
                        map: map,
                        zIndex: 9999999,
                        position: map.getCenter()
                    });

                    var infoWindow = new SimpleInfoWindow({

                        infoTitle: "<p class='my-title' onclick='Nav()'>"+(name==""?"共享位置":name)+"</p>",
                        infoBody: "<p class='my-desc' onclick='Nav()'><strong>"+address+"</strong> </p>",

                        //基点指向marker的头部位置
                        offset: new AMap.Pixel(0, -31)
                    });

                    function openInfoWin() {
                        infoWindow.open(map, marker.getPosition());
                    }

                    //marker 点击时打开
                    AMap.event.addListener(marker, 'click', function() {
                       openInfoWin();
                    });

                    openInfoWin();
                });
            }else{
                console.log('根据经纬度查询地址失败');
                $("#container").remove();
            }
        });
    }
    function  Nav() {
        marker.markOnAMAP({
            name:address,
            position:marker.getPosition()
        })
    }



</script>

