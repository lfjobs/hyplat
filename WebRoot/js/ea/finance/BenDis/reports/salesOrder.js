$(function () {
    theDay();
    load();
    // $("#test1").val("2018-01-01 00:00:00 至 2018-12-31 00:00:00");
    $("#toSch").click(function () {
        //theDay();
        if($("input[name='timeInterval']").val()=="" &&
            $("input[name='accountOrName']").val()=="" &&
            $("input[name='cashierBills.journalNum']").val()=="" &&
            $("input[name='cashierBills.inputName']").val()==""){
            alert("请至少输入一种查询方式！")
            return;
        }
        var setime = $("input[name='timeInterval']").val();
        if(setime.substring(setime.length-8)=="00:00:00")
        {
            $("input[name='timeInterval']").val(setime.substring(0,setime.length-8)+"23:59:59");
        }
        load();
    });

    $("#export").click(function () {
        var formData = $("#schForm").serialize();
        formData = decodeURIComponent(formData, true);
        window.location.href = basePath+"/ea/reports/ea_showExcel.jspa?type=all&"+formData+"&reportType="+reportType;
    })
});

function load() {
    var url = basePath+"/ea/reports/sajax_getOrderListBySch.jspa?pageNumber="+ppageNumber;
    $.ajax({
        url:encodeURI(url),
        type:"get",
        data:$("#schForm").serialize(),
        dataType:"json",
        async: true,
        beforeSend : function() {
            $(".load").show();
        },
        success:function (data) {
            $(".load").hide();
            var member = eval("("+data+")");
            var pageForm = member.pageForm;
            if (pageForm == null) {
                alert("没有数据！");
                return;
            }
            var reportsList = pageForm.list;
            var sum = member.sum;
            var count = member.count;
            var str = new Array();
            $("#reportList tbody").children().not(":first").remove();
//在线支付:00微信支付，01，支付宝支付，02银联支付 03:线下转账 04:钱盒子支付  05：积分支付 06：现金支付（收银机公司用积分代付）
                for(var i=0;i<reportsList.length;i++){
                    str.push("<tr class='"+reportsList[i][12]+"'><td>"+parseInt(i+1)+"</td>");
                    str.push("<td>"+(reportsList[i][0]==null?'':reportsList[i][0])+"</td>");
                    str.push("<td>"+(reportsList[i][1]==null?'':reportsList[i][1])+"</td>");
                    str.push("<td>"+(reportsList[i][2]==null?'':reportsList[i][2])+"</td>");
                    str.push("<td>"+(reportsList[i][3]==null?'':reportsList[i][3])+"</td>");
                    str.push("<td>"+(reportsList[i][4]==null?'':reportsList[i][4])+"</td>");
                    str.push("<td>"+(reportsList[i][5]==null?'':reportsList[i][5])+"</td>");
                    str.push("<td>"+(reportsList[i][6]==null?'':reportsList[i][6])+"</td>");
                }

            $("#reportList").append(str.join(""));
            //汇总信息
            $("#totalCons").text(sum[0].toFixed(2));
            $("#totalOrder").text(sum[1].toFixed(2));
            $("#totalAmount").text(sum[2].toFixed(2));
            //分页
            var pageNumber = pageForm.pageNumber;// 当前页
            var pageCount = pageForm.pageCount;// 总页数
            $(".limit").empty();
            var limit = [];
            limit.push("<div><a class='ripple' onclick='controlPage(this)' '>上一页</a>");
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
            limit.push("<a class='ripple' onclick='controlPage(this)'>下一页</a></div>");
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
            if(ppageNumber==0){
                ppageNumber++;
            }
            ppageNumber++;
        }
    }else {
        ppageNumber = $(obj).text();
    }
    load();
}

function theDay() {
    // $("#test1").val("2018-01-01 00:00:00 至 2018-12-31 00:00:00");
    var date = new Date();
    var year = date.getFullYear();
    var month = date.getMonth()+1;
    var day = date.getDate();
    var time = year+"-"+month+"-"+day;
    if($('#test1').val()==null || $('#test1').val()==""){
        $('#test1').val(time+" 00:00:00 至 "+time+" 23:59:59")
    }
}
