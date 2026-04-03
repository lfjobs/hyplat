var siteId = "";
$(function() {
    $(".jqmWindow").jqm({
        modal : true,// 限制输入（鼠标点击，按键）的对话
        overlay : 20
        // 遮罩程度%
    }).jqmAddClose('.close');// 添加触发关闭的selector


    $('.flexme11').flexigrid({
        height : 350,
        width : 'auto',
        minwidth : 30,
        title :(auditStatus=="01"?"待审核印章":"已审核印章")+"&nbsp;&nbsp;&nbsp;&nbsp;印章名称：<input type='text' id='ch' style='width:100px'/> &nbsp;<input type='button' value=' 查询 ' id='toSearch' class='input-button'/> ",
        minheight : 80,
        buttons :  auditStatus=="02"?[


            {
                name : '设置每页显示条数',
                bclass : 'mysearch',
                onpress : action
                // 当点击调用方法
            },{
                separator : true
            }]:[

             {
                name : '驳回',
                bclass : 'delete',
                onpress : action
                // 当点击调用方法
            }	,  {
                separator : true
            },{
                name : '审核通过',
                bclass : 'examine',
                onpress : action
                // 当点击调用方法
            }	,  {
                separator : true
            },

            {
                name : '设置每页显示条数',
                bclass : 'mysearch',
                onpress : action
                // 当点击调用方法
            } , {
                separator : true
            }]
    });

    function action(com, grid){
        switch (com) {

            case '驳回':
                if (enterpriseStampID == "") {
                    alert("请选择！");
                    return;
                }

                $("#jqModeladd").jqmShow();
                break;

            case '审核通过':
                if (enterpriseStampID == "") {
                    alert("请选择！");
                    return;
                }




                    if(confirm("确认通过")) {

                        submit(enterpriseStampID,"02","");

                    }

                break;

            case '设置每页显示条数' :
                var url = basePath
                    + "/ea/enterprisestamp/ea_getAuditStampList.jspa?enterpriseStamp.auditStatus="+auditStatus+"&search="
                    + search+"&pageNumber="+pNumber;
                numback(url);
                break;

                }
    }




    $(".flexme11 tr[id]").click(function() {
        enterpriseStampID = this.id;
        $("input.JQuerypersonvalue", $(this))
            .attr("checked", "checked");
    });

    $(".flexme11 tr[id]").dblclick(function() {
        enterpriseStampID = this.id;
        $("input.JQuerypersonvalue", $(this))
            .attr("checked", "checked");

        action("修改");
    });

    //提交驳回
    $("#save").click(function() {
       var  rejectReason = $("#addTable #rejectReasons").val();

        submit(enterpriseStampID,"03",rejectReason);

    });






    //查询
    $("#toSearch").click(function() {
        $("#searchForm").find(".stampName").val($("#ch").val());

        $("#searchForm").attr(
            "action",
            basePath + "ea/enterprisestamp/ea_toSearchAudit.jspa");
        token = 2;
        document.searchForm.submit.click();
    });






});

function re_load() {
    if(token) {
        document.location.href = basePath
                      + "/ea/enterprisestamp/ea_getAuditStampList.jspa?enterpriseStamp.auditStatus="+auditStatus+"&pageNumber=" + pNumber+"&search="+search;
    }
}
//审核提交
function submit(enterpriseStampID,auditStatus,rejectReason){

    $.ajax({
        url: basePath + "ea/enterprisestamp/sajax_n_ea_auditStamp.jspa",
        type: "POST",
        data: {
            "enterpriseStamp.enterpriseStampID":enterpriseStampID,
            "enterpriseStamp.auditStatus":auditStatus,
            "enterpriseStamp.rejectReason":rejectReason
        },
        dataType: "json",
        async: false,
        success:function (data) {
            window.location.reload();

        },error:function (data) {
        }


    });
}



