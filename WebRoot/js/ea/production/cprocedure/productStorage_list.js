$(function(){
    var title="<form name='form' id='form' method='post'>产品入库单&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;单据编号："
        + "<input type='text' id='likeJournalnum' style='width:100px;'>&nbsp;&nbsp;&nbsp;产品名称："
        + "<input type='text' id='likeGoodsname' style='width:100px;'>&nbsp;&nbsp;&nbsp;类型："
        + "<select style=\"width:70px;\" id='select'><option value='14'>已验收</option><option value='15'>已入库</option></select><input type='submit' "
        + " name='submit' id='submit' style=\"display: none;\">&nbsp;&nbsp;&nbsp;<input type='button' id='tosearch' class='input-button' value='查询' style=\"margin:0px;margin-left:5px;\"></form>";
    $('.fexlist').flexigrid({
        height : 145,
        width : 'auto',
        minwidth : 30,
        title :title,
        minheight : 80,
        buttons : [{
            name : '添加',
            bclass : 'add',
            onpress : action
            // 当点击调用方法
        }, {
            separator : true
        },{
            name : '修改',
            bclass : 'edit',
            onpress : action
            // 当点击调用方法
        }, {
            separator : true
        },{
            name : '打印',
            bclass : 'printer',
            onpress : action
            // 当点击调用方法
        }, {
            separator : true
        },{
            name : '查看审核',
            bclass : 'see',
            onpress : action
            // 当点击调用方法
        }, {
            separator : true
        },{
            name : '确认入库',
            bclass : 'bc',
            onpress : action
            // 当点击调用方法
        }, {
            separator : true
        },{
            name : '设置每页显示条数',
            bclass : 'mysearch',
            onpress : action
            // 当点击调用方法
        }]
    });
    function action(com, grid) {
        switch (com) {
            case '添加' :
                if(status!="00"){
                    alert("正在执行入库方案，请稍候");
                    return;
                }
                open(basePath+"ea/production/ea_getSinglePageData.jspa");
                break;
            case '修改' :
                if(status!="00"){
                    alert("正在执行入库方案，请稍候");
                    return;
                }
                if(id==""){
                    alert("请选择！");
                    break;
                }
                if($("#"+id).parents("tr").find("span").eq(7).text()!="已验货"){
                    alert("只能选择已验货的单据修改");
                    break;
                }
                open(basePath+"ea/production/ea_getSinglePageData.jspa?type=01&utboundOrderVo.ppId="+$("#"+id).parents("tr").find(".ppId").val()+"&utboundOrderVo.cashierbillsid="+id);
                break;
            case '打印' :
                if(status!="00"){
                    alert("正在执行入库方案，请稍候");
                    return;
                }
                if(id==""){
                    alert("请选择！");
                    break;
                }
                open(basePath+"ea/production/ea_storageListReviewAudit.jspa?utboundOrderVo.cashierbillsid="+id+"&type=toPrint");

                break;
            case '查看审核' :
                if(status!="00"){
                    alert("正在执行入库方案，请稍候");
                    return;
                }
                if(id==""){
                    alert("请选择！");
                    break;
                }
                open(basePath+"ea/production/ea_storageListReviewAudit.jspa?utboundOrderVo.cashierbillsid="+id+"&type=examine");
                break;
            case '确认入库' :
                if(status!="00"){
                    alert("正在执行入库方案，请稍候");
                    return;
                }
                if(id==""){
                    alert("请选择！");
                    break;
                }
                if($("#"+id).parents("tr").find("span").eq(7).text()!="已验货"){
                    alert("该单据已入库"); break;
                }
                var br=true;
                $.ajax({
                    url:basePath+"ea/production/sajax_ea_ajaxCheckWhetherAudit.jspa?utboundOrderVo.cashierbillsid="+id,
                    type:"post",
                    async : false,
                    success:function(data){
                        var member = eval("(" + data + ")");
                        var sta=member.sta;
                        if(sta=="0"){
                            br=false;
                        }
                    },
                    error:function(){
                        alert("数据获取失败");
                    }
                });
                if(!br){
                    alert("该单据还未审核！");
                    break;
                }else{
                    if(confirm("确定入库")){
                        status="01";
                        $("#forms #idd").val(id);
                        $.ajax({
                            url:basePath+"ea/production/sajax_ea_confirmationOfStorage.jspa",
                            type:"post",
                            async : true,
                            data:$('#forms').serialize(),
                            success:function(data){
                                alert("操作成功");
                                re_load();
                            },
                            error:function(){
                                alert("数据获取失败");
                            }
                        });
                        // var url=basePath+"ea/production/ea_confirmationOfStorage.jspa?utboundOrderVo.cashierbillsid="+id;
                        // $("#forms").attr("target", "hidden").attr("action",url);
                        // document.forms.submits.click();
                        // token = 2;
                    }
                }
                break;
            case '设置每页显示条数' :
                if(status!="00"){
                    alert("正在执行入库方案，请稍候");
                    return;
                }
                var pr=prompt("请输入0~20的正整数");
                if(pr>0&&pr<=20){
                    window.location.href=basePath+"ea/production/ea_getAccessToProductInformation.jspa?pageNumber="+pr;
                }else if(pr==null){
                    break;
                }else{
                    alert("您输入的有误，请重新输入");
                }
                break;
        }
    }
    //每行的点击事件
    $("tr[id]").live("click",function(){
        $(this).find(".radio").attr("checked","checked");
        if($(this).attr("name")=="goods")
            goodsId=$(this).find(".radio").attr("id");
        if($(this).attr("name")=="dps")
            id=$(this).find(".radio").attr("id");
    });
    $("#tosearch").click(function(){
        if(status!="00"){
            alert("正在执行入库方案，请稍候");
            return;
        }
        url=basePath+"ea/production/ea_getAccessToProductInformation.jspa?utboundOrderVo.journalnum="+$("#likeJournalnum").val()+"&utboundOrderVo.goodsname="+$("#likeGoodsname").val()+"&type="+$("#select option:selected").val();
        window.location.href=url;
    });
});


function re_load(){
    status="00";
    window.location.href=window.location.href;
}