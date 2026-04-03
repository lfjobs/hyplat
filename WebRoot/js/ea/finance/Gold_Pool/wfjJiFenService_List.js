$(function() {
    $(".jqmWindow").jqm({
        modal : true,// 限制输入（鼠标点击，按键）的对话
        overlay : 20
        // 遮罩程度%
    }).jqmAddClose('.close');// 添加触发关闭的selector

    var query ="<form method='post' name='SearchForm' id='SearchForm'><input type='submit' style='display:none;' name='submit' /><input type='hidden' name='search' value='search'/>兑换审核"
        + "<table id='SearchTable'><tr>"
        + "<td align='right'>"//&nbsp;&nbsp;兑现人帐号/姓名：<input type='text' style=\"width: 100px\" id='zhxm' name='zhxm'/>
        + "&nbsp;&nbsp;顺序号：<input id='journalNum' style=\"width: 100px\" name='num' />"+
        /*+ "&nbsp;&nbsp;审核人：<input id='shr' style=\"width: 50px\" name='shr' />" +*/
        "&nbsp;&nbsp;状态：<select name='payState' style=\"width: 85px;hight: 50px\" id='payState'>" +
      "<option value='' >全部</option>" +
      "<option value='00' >初始状态</option>"+
      "<option value='01' >通过</option>"+
      "<option value='02' >不通过</option>"+
      "<option value='03' >已打款</option>"+
      "</select>"
        + "&nbsp;&nbsp;申请日期：<input id='sdate' name='sdate' onfocus='\daytime(this);\' style='width: 85px' />至<input id='edate' name='edate' onfocus='\daytime(this);\' style='width: 85px' />"
        + "&nbsp;&nbsp;<input class=\"input-button\"  style=\"margin:0px;margin-left:5px;\"  id='tosearch' value=' 查询 ' type='button'/></td>"
        + "<tr></table>"
        + "</form>";


    $('.flexme11').flexigrid({
        height : 300,
        width : 'auto',
        minwidth : 30,
        title : query,
        minheight : 80,
        buttons : [{
            // 设置分割线
            separator : true
        },{
            name : '查看',
            bclass : 'find',
            onpress : action
            // 当点击调用方法
        },{
            separator : true
        },{
            name : '审核',
            bclass : 'edit',
            onpress :action // 当点击调用方法
        },{
            separator : true
        }, {
                name : '回执',
                bclass : 'receipt',
                onpress :action // 当点击调用方法
            },{
                separator : true
            },{
            name : '导出',
            bclass : 'excel',
            onpress : action
            // 当点击调用方法
        },{
            separator : true
        },{
            name : '设置每页显示条数',
            bclass : 'mysearch',
            onpress : action
            // 当点击调用方法
        },{
            separator : true
        }]
    });


    function action(com, grid) {
        switch (com) {
            case '查看' :
                if(erId!=""){
                    url = basePath + "ea/jinbi/ea_findCashingAudit.jspa?wdaID="+erId;
                    open(url);
                }else {
                    alert("请选择");
                    return
                }
                break;
            case '审核' :
                if(erId!=""){
                url = basePath + "ea/jinbi/ea_CustomerCashingAudit.jspa?wdaID="+erId+"&flag=02";
                 open(url);
                }else {
                    alert("请选择");
                    return
                }
                break;
            case '回执' :
                if(erId!=""){
                    if(state=="通过"){
                    url = basePath + "ea/jinbi/ea_CustomerCashingAudit.jspa?wdaID="+erId+"&flag=01";
                    open(url);
                    }else {
                        alert("还没有审核");
                        return
                    }
                }else {
                    alert("请选择");
                    return
                }
                break;
            case '导出' :
                $("#shyj").jqmShow();
                /*url = basePath + "ea/jinbi/ea_showExcel.jspa?sdate="+sdate+"&edate="+edate+"&exportNum=1";
                open(url);*/
                break;
            case '设置每页显示条数' :
                var url = basePath
                    + "ea/jinbi/ea_getWdsList.jspa?search="
                    + search +"&sdate="+sdate+"&edate="+edate;
                numback(url);
                break;
        }
    }


    $("#tosearch").click(function() {
        $("#SearchForm").attr(
            "action",
            basePath + "/ea/jinbi/ea_getWdsList.jspa?pageNumber="
            + ppageNumber);
        document.SearchForm.submit.click();
    });
    $(".flexme11 tr[id]").dblclick(function() {
        $("input.JQuerypersonvalue", $(this))
            .attr("checked", "checked");
        cashierBillsID = this.id;
        action("查看");
    });

    $(".flexme11 tr[id]").click(function() {
        var d = $("input.JQuerypersonvalue", $(this)).attr("checked");
        $("input.JQuerypersonvalue", $(this)).attr("checked", !d);
        erId = this.id;
        state=$(this).find("#state").text().replace(/^\s+/, '').replace(/\s+$/, '');
    });

    $("#submitResult2").click(function (){
        $("#shyj").jqmHide();
        $("#SendForm2").attr(
            "action",
            basePath + "ea/jinbi/ea_showExcel.jspa");
        document.SendForm2.submit.click();

       /* var a=$("#pageNum").val();
        var url = basePath+"ea/jinbi/sajax_showExcel.jspa?sdate="+sdate+"&edate="+edate+"&zhxm="+zhxm+"&num="+num+"&shr="+shr+"&payState="+payState+"&pageNum="+a;
        $.ajax({
            url : url,
            type : "post",
            async : false,
            dataType : "json",
            /!*data : {
             "pageNumber":pagenumber,
             "sccid":sccid
             },*!/
            success : function(data){
                alert(234);
            }

        });*/
    });
});
function re_load() {
    document.location.href = basePath
        + "ea/accountant/ea_getAccountantList.jspa?pageNumber=" + pNumber
        + "&pageForm.pageNumber=" + $("#pageNumber").attr("value")+"&sdate="+sdate+"&edate="+edate;
}

