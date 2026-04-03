$(document).ready(function () {
    if (sort == 2) {
        $("body").removeClass("no-header");
    } else {
        $("body").addClass("no-header");
    }
    $("#fh").click(function () {
        $.ajax({
            url: "ea/seller/sajax_ea_SendLogicalProcessing.jspa",
            type: "post",
            async: false,
            dateType: "json",
            data: {
                "sendid": $("#sendid").val(),
                "staffid":staffid
            },
            success: function (data) {
                var member = eval("(" + data + ")");
                var flag = member.flag;
                if (flag == "00") {
                    alert("操作成功");
                } else if (flag == "01") {
                    alert("生成失败");
                }
            }
        });
    });
});

function toBack() {
    history.go(-1);
}