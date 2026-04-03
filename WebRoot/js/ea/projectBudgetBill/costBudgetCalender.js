$(document).ready(function() {
    calendarWidget.initCalculate({
        elem: "#dateWidget",
        currentTime: '2025-8-2',
        mark: [{
            time: '2025-8-2',
            text: '<span style="color:red">未排</span>',
            markPoint: true,
            pointText: '<span style="color:red">●</span>'
        }],
        mounted: function(data) { //初始化完成
            console.log(data)
        },
        change: function(selectedDate, markPoint) {

        },
        dateclick: function(widget, curentDate) { //点击顶部时间回调,方便外部设置时间

        },
        formatText: function(text) { //格式化文本

        }
    });

//设置范围
    calendarWidget.setRange('2025-8-2', '2025-8-2');
});


