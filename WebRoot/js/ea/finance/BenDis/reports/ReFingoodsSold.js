$(function () {
    load();
    $("#toSch").click(function () {
        load();
    });

    $("#export").click(function () {
        var formData = $("#schForm").serialize();
        formData = decodeURIComponent(formData, true);
        window.location.href = basePath+"/ea/reports/ea_showExcel.jspa?type=all&"+formData;
    })
});

function load() {
    var url = basePath+"/ea/reports/sajax_getGorssListBySch.jspa?pageNumber="+ppageNumber;
    $.ajax({
        url:encodeURI(url),
        type:"get",
        data:$("#schForm").serialize(),
        dataType:"json",
        async: true,
        success:function (data) {
            var member = eval("("+data+")");
            var pageForm = member.pageForm;
            if (pageForm == null) {
                alert("没有数据！");
                return;
            }
            var reportsList = pageForm.list;
            var sum = member.sum;
            var str = new Array();
            $("#reportList tbody").children().not(":first").remove();
            //列表
            for(var i=0;i<reportsList.length;i++){
                str.push("<tr class='"+reportsList[i][12]+"'><td>"+parseInt(i+1)+"</td>");
                str.push("<td>"+(reportsList[i][0]==null?'':reportsList[i][0])+"</td>");
                str.push("<td>"+(reportsList[i][1]==null?'':reportsList[i][1])+"</td>");
                str.push("<td>"+(reportsList[i][2]==null?'':reportsList[i][2])+"</td>");
                str.push("<td>"+(reportsList[i][3]==null?'':reportsList[i][3])+"</td>");
                str.push("<td>"+(reportsList[i][4]==null?'':reportsList[i][4])+"</td>");
                str.push("<td>"+(reportsList[i][5]==null?'':reportsList[i][5])+"</td>");
                str.push("<td>"+(reportsList[i][6]==null?'':reportsList[i][6])+"</td>");
                str.push("<td>"+(reportsList[i][7]==null?'':reportsList[i][7])+"</td>");
                str.push("<td>"+(reportsList[i][8]==null?'':reportsList[i][8])+"</td>");
                str.push("<td>"+(reportsList[i][9]==null?'':reportsList[i][9])+"</td>");
                str.push("<td>"+(reportsList[i][10]==null?'':reportsList[i][10])+"</td>");
                str.push("<td>"+(reportsList[i][11]==null?'':reportsList[i][11])+"</td></tr>");
            }
            $("#reportList").append(str.join(""));
            //汇总信息
            $("#totalCons").text(sum[0]);
            $("#totalOrder").text(sum[1])
            $("#totalNumber").text(sum[2])
            $("#totalAmount").text(sum[3])
            //分页
            var pageNumber = pageForm.pageNumber;// 当前页
            var pageCount = pageForm.pageCount;// 总页数
            $(".limit").empty();
            var limit = [];
            limit.push("<div><a onclick='controlPage(this)' '>上一页</a>");
            for ( var i = 1; i <= pageCount; i++) {
                if(pageNumber==i){
                    limit.push("<a onclick='controlPage(this)' class='active'>"+i+"</a>");
                }else{
                    if(i==1||i==pageCount||(i>=pageNumber-2&&i<=pageNumber+2)){
                        limit.push("<a onclick='controlPage(this)'>"+i+"</a>");
                    }else if(i!=1&&i!=pageCount&&(i==pageNumber-3||i==pageNumber+3)){
                        limit.push("<a style='border:none;background-color:white;' class='point'><span>...</span></a>");
                    }
                }
            }
            limit.push("<a onclick='controlPage(this)'>下一页</a></div>");
            $(".limit").append(limit.join(""));
        }
    })
}
function controlPage(obj){
    if($(obj).text()=="上一页" ){
        if($(".limit .active").text()==1){
            ppageNumber=1;
        }else {
            ppageNumber--;
        }
    }else if($(obj).text()=="下一页" ){
        if(ppageNumber != $(".limit a").eq(-2).text()){
            ppageNumber++;
        }
    }else {
        ppageNumber = $(obj).text();
    }
    load();
}
