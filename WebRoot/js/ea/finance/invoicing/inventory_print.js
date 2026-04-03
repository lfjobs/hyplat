$(document).ready(function () {
$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector
		
		$("#queding").click(function(){
			$("#tubiao").jqmShow();
		});
		$("#queding2").click(function(){
			$("#tubiao2").jqmShow();
		});
            $("#jqChart").jqChart({
                title: { text: "总物品库存预警折线图" },
                animation: { duration: 1 },
                axes: [
                        {
                            location: "left",
                            minimum: 0,
                            maximum: maxnum
                        }
                      ],
                series: [
                            {
                            	title: "物品库存",
                                type: "line",
                                data: line1
                            },
							 {
                            	title: "物品库存上限",
                                type: "line",
                                data: line2
                            }
                        ]
            }); 
   
    $("#jqChart2").jqChart({
                title: { text: "不同月份物品总价变化趋势折线图" },
                animation: { duration: 1 },
                axes: [
                        {
                            location: "left",
                            minimum: 0,
                            maximum: maxnum2
                        }
                      ],
                series: [
                            {
                            	title: "物品总价变化趋势",
                                type: "line",
                                data: line3
                            }
                        ]
            }); 
        });