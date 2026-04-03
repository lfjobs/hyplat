$(function () {
    //initData();

});
const entryStatistic = function () {
    this.v = "2.4.3"

};
entryStatistic.initData = function(){
    layer.load();
    const param = new Array();
    const url = basePath + "ea/staff/sajax_ea_getEntryStatisticsData.jspa?" + param.join("");
    $.ajax({
        url: encodeURI(url),
        type: "get",
        async: true,
        dataType: "json",
        success: function cbf(data) {
            const codeList = eval("(" + data + ")");
            const entryData = codeList["entryData"];
            $(".entry-person-num").html(entryData["0"]);
            $(".part-person-num").html(entryData["1"]);
            $(".full-person-num").html(entryData["2"]);
            const educationData = codeList["educationData"];
            var length = educationData.length;
            var data = [];
            for (var i = 0; i < length; i++){
                var nameStr = educationData[i][0] + " " + educationData[i][1];
                data.push({value:educationData[i][1],name:nameStr})
            }
            getPieData("educationData",data);
            const contractData = codeList["contractData"];
            length = contractData.length;
            var xData = [],yData = [];
            for (var i = 0; i < length; i++){
                xData.push(contractData[i][0]);
                yData.push(contractData[i][1]);
            }
            getBarData("contractData",xData,yData);
            xData = [],yData = [];
            const entryPersonData = codeList["entryPersonData"];
            length = entryPersonData.length;
            for (var i = 0; i < length; i++){
                xData.push(entryPersonData[i][0]);
                yData.push(entryPersonData[i][1]);
            }
            getLineData("entryPersonData",xData,yData);
            layer.closeAll();
        },
        error: function cbf(data) {
            prompt("数据获取失败！");
        }
    });
}

/**
 * 获取饼图图形
 */
function getPieData(id,data){
    var chart = echarts.init(document.getElementById(id));
    // 指定图表的配置项和数据
    option = {
        tooltip: {
            trigger: 'item'
        },
        legend: {
            orient: 'vertical',
            right: 10,
            top: 'center',
            bottom: 20,
        },
        series: [
            {
                type: 'pie',
                radius: '80%',
                labelLine: {
                    show: false
                },
                center: ['40%','50%'],
                label: {
                    show: false,
                    position: 'center'
                },
                data: data,
                emphasis: {
                    itemStyle: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };

    // 使用刚指定的配置项和数据显示图表。
    chart.setOption(option);
}

/**
 * 获取柱状图数据
 * @param id
 * @param xData
 * @param yData
 */
function getBarData(id,xData,yData){
    var chart = echarts.init(document.getElementById(id));
    option = {
        xAxis: {
            type: 'category',
            data: xData,
            axisLabel: {
                fontSize:12,
                rotate: 45, // 旋转45度
                interval: 0
            },
        },
        yAxis: {
            type: 'value'
        },
        grid:{
            top:'10%'
        },
        series: [
            {
                data: yData,
                type: 'bar',
                showBackground: true,
                label: {
                    show: true,
                    position: 'inside'
                },
                itemStyle: {
                    color: '#f74c32' // 设置颜色
                },
                backgroundStyle: {
                    color: 'rgba(180, 180, 180, 0.2)'
                },
                center: ['50%'],
            }
        ]
    };

    chart.setOption(option);
}

/**
 * 获取折线图形
 * @param id
 * @param xData
 * @param yData
 */
function getLineData(id,xData,yData){
    var chart = echarts.init(document.getElementById('entryPersonData'));
    option = {
        xAxis: {
            type: 'category',
            data: xData,
            axisLabel: {
                fontSize:12,
                rotate: 45, // 旋转45度
                interval: 0
            },
        },
        yAxis: {
            type: 'value'
        },
        grid:{
            top:'10%'
        },
        series: [
            {
                data: yData,
                type: 'line',
                label: {
                    show: true,
                    position: 'inside'
                },
                itemStyle: {
                    color: '#f74c32' // 设置颜色
                },
            }

        ]
    };
    chart.setOption(option);
}