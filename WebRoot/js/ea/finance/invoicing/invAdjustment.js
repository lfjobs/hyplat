$(function () {
    $(".jqmWindow").jqm({
        modal: true,// 限制输入（鼠标点击，按键）的对话
        overlay: 20
        // 遮罩程度%
    }).jqmAddClose('.close');// 添加触发关闭的selector

    var query = "<form name='searchform' id='searchform' method='post'>"
        + "销售库库存调整&nbsp;<input type='submit' style='display:none;' name='submit'/>"
        + "<input type='hidden' name='search' value='search'/>"
        + "<span style='margin-left:150px;'>条码号：</span><input type='text' style='width: 100px' name='barcode'/>&nbsp;&nbsp;"
        + "添加时间：<input id='sdate' name='sdate' onfocus='date(this);' style='width: 85px' AUTOCOMPLETE='off'/>"
        + "至<input id='edate' name='edate' onfocus='date(this);' style='width: 85px' AUTOCOMPLETE='off'/>"
        + "<input class='input-button' type='button' style='margin:0px;margin-left:5px;' value='查询' id='tosearch'/></form>";

    $('.flexme11').flexigrid({
        height: 350,
        width: 'auto',
        minwidth: 30,
        title: query,
        minheight: 80,
        buttons: [{
            name: '添加',
            bclass: 'add',
            onpress: action
            // 当点击调用方法
        }, {
            separator: true
        }]
    });

    // 这一行的单击事件
    $(".flexme11 tr[id]").click(function () {
        if (event.srcElement.value == undefined) {
            if ($("input.JQuerypersonvalue", $(this)).attr("checked")) {
                $("input.JQuerypersonvalue", $(this)).attr("checked", false);
            } else {
                financialbillID = this.id;
                $("input.JQuerypersonvalue", $(this)).attr("checked", true);
            }
        }
    });

    $("#tosearch").click(function () {// 查询
        $("#postSearchForm2")
            .attr(
                "action",
                basePath
                + "ea/purchase/ea_toinspectSearch.jspa?pageNumber="
                + pNumber);
        document.postSearchForm2.submit.click();
    });

    document.onkeydown = function (evt) {//捕捉回车
        evt = (evt) ? evt : ((window.event) ? window.event : "") //兼容IE和Firefox获得keyBoardEvent对象
        var key = evt.keyCode ? evt.keyCode : evt.which; //兼容IE和Firefox获得keyBoardEvent对象的键值
        if (key == 13) { //判断是否是回车事件。
            if ($("input#barcode").val() == '') {
                alert("输入条码号");
            } else if ($("input#barcode").val() != '') {
                $.ajax({
                    url: 'ea/invadj/sajax_ea_ajxaGetProduct.jspa',
                    type: "get",
                    async: true,
                    dataType: "json",
                    data: {
                        "barcode": $("input#barcode").val()
                    },
                    success: function (data) {
                        var member = eval("(" + data + ")");
                        var list = member.list;
                        if (list == null) {

                        } else {
                            for (var i = 0; i < list.length; i++) {
                                var a = list[i];
                                $("#invid").val(a[0]);
                                $("#ppid").val(a[1]);
                                $("#goodsname").val(a[3]);
                                $("#variableid").val(a[4]);
                                $("#invnum").val(a[5]);
                            }
                        }
                    },
                    error: function (data) {
                        alert("获取单据失败");
                    }
                });
            }
        }
    };

    $("input.JQuerySubmits").click(function () {
        var reg1 = new RegExp(/^(?!0+(?:\.0+)?$)(?:[1-9]\d*|0)(?:\.\d{1,4})?$/);

        if ($("#invid").val() == null || $("#invid").val() == "") {
            alert("数据异常");

            return false;
        } else if ($("#adjnum").val() == null || $("#adjnum").val() == "") {
            alert("调整库存数量不能为空");
            return false;
        } else if (!reg1.test($("#adjnum").val())) {
            alert("调整库存数量违规");
            $("#adjnum").val(0);
            return false;
        } else {
            $("#addForms").append("<input type='submit' name='submit' id='yc' style='display:none'/>");
            if (confirm("确定添加？")) {
                $("#addForms")
                    .attr("target", "hidden")
                    .attr(
                        "action",
                        basePath
                        + "ea/invadj/ea_UpdateInv.jspa?pageNumber="
                        + pNumber);
                document.addForms.submit.click();
                token = 2;
                $("input").remove("#yc");
            }
        }

    });

    $("input.JQueryreturns").click(function () {
        $("#jqModels").jqmHide();
        //re_load();
    });

    $("#cz").click(function () {
        $("#goodsjqModel").jqmShow();
    });
    /** **********************************选择物品**************************************** */
    $(document).ready(function () {
        var divinvid="";
        cx();
        /*$.ajax({
         url: "ea/invadj/sajax_ea_getcode.jspa?"+ new Date().toLocaleString(),
         type: "get",
         async: true,
         dataType: "json",
         success: function cbf(data) {
         var member = eval("(" + data + ")");
         var nologin = member.nologin;
         if (nologin) {
         document.location.href = "page/ea/not_login.jsp";
         }
         var codeList = member.codeList;
         if (null == codeList) {
         return;
         }
         var data1 = new Array();
         for (var i = 0; i < codeList.length; i++) {
         data1[i] = {
         id : codeList[i][0],
         pid : codeList[i][1],
         text : codeList[i][2]
         };
         }
         var ts = new TreeSelector($("ul#xmul"),data1, "scode20190523bj56dxj7r40000000391");
         ts.createTree();
         $("ul#xmul").treeview({
         persist: "location",
         collapsed: false,
         unique: false
         });
         },
         error: function cbf(data) {
         alert("数据获取失败！");
         }
         });*/

        // 双击选中物品
        $("table#gotable tr[id]").live("click", function (event) {
            var b = $("input.ragood", $(this)).attr("checked");
            $("input.ragood", $(this)).attr("checked", !b);
            divinvid=$("input.ragood", $(this)).val();
        });

        // 上一页
        $("#wpsy").click(function () {
            var sy = $("#wpsy").attr("title");
            if (sy != 0) {
                var typeName = $(":input#parms").val();
                var typeCN = typeName
                    + "&pageForm.pageNumber=" + sy;
                cx(typeCN);
            } else {
                alert("已是首页！");
            }
        });

        // 下一页
        $("#wpxy").click(function () {
            var xy = $("#wpxy").attr("title");
            if (xy != 0) {
                var typeName = $(":input#parms").val();
                var typeCN = typeName
                    + "&pageForm.pageNumber=" + xy;
                cx(typeCN);
            } else {
                alert("已是尾页！");
            }
        });

        //关闭
        $("#closes").click(function () {
            $("#goodsjqModel").hide();
        });

        /*******************************************物品以及子物品列表*****************************************************/
        // 添加所选中的物品到物品列表
        $("#selectGood").click(function () {
            if (divinvid != ""&&divinvid!=null) {
                $("#invid").val(divinvid);
                $("#barcode").val($("#"+divinvid).find("#divBarcode").text());
                $("#goodsname").val($("#"+divinvid).find("#divGoodsName").text());
                $("#variableid").val($("#"+divinvid).find("#divVariableid").text());
                $("#invnum").val($("#"+divinvid).find("#divInvnum").text());
                $("#ppid").val($("#"+divinvid).find("#divppid").val());
                $("#goodsjqModel").hide();
            } else {
                alert("请选择物品！");
            }
        });


        // 根据输入的物品编号或物品名称查询
        $("input#searchGood").click(function () {
            var typeName = $("#typeID", $("table#goodtable")).val();
            $(":input#parms").val("parameter=" + typeName);
            cx("parameter=" + typeName);
        });

        // ajax查询物品列表
        function cx(typeCN) {
            if (notoken) {
                alert("正在获取数据！请稍等");
                return;
            }
            notoken = 1;
            $("#wpsy").attr("title", 0);
            $("#wpxy").attr("title", 0);
            $("#wpzy").attr("title", 0);
            var searchurl = "ea/invadj/sajax_ea_GetProduct.jspa?";
            $.ajax({
                url: encodeURI(searchurl + typeCN+ "&date="+ new Date().toLocaleString()),
                type: "get",
                async: true,
                dataType: "json",
                success: function cbf(data) {
                    var member = eval("(" + data + ")");
                    var nologin = member.nologin;
                    if (nologin) {
                        document.location.href = basePath
                            + "page/ea/not_login.jsp";
                    }
                    var pageForm = member.pageForm;
                    if (pageForm == null) {
                        alert("没有数据");
                        notoken = 0;
                        return;
                    }
                    var dqy = pageForm.pageNumber;// 当前页
                    var zys = pageForm.pageCount;// 总页数
                    if (dqy > 1) {
                        $("#wpsy").attr("title", dqy - 1);
                    }
                    if (dqy < zys) {
                        $("#wpxy").attr("title", dqy + 1);
                    }
                    //
                    $("span#wpzycount").text(zys);
                    var tabletr = "<table width='98%' height='26' align='center' cellspacing='0' cellpadding='1'";
                    tabletr += " style='font-size:12px;' class='bannb_01'>";
                    tabletr += "<tr><td height='24' align='left' valign='top' class='txt01' >&nbsp;点击选择物品</td></tr></table>";
                    tabletr += "<table width='99%' align='center' id='gotable' cellpadding='0' cellspacing='0' class='table'>";
                    tabletr += "<tr><th height='21' align='center' bgcolor='#E4F1FA'>选择</th>";
                    tabletr += "<th align='center' bgcolor='#E4F1FA'>条码</th>";
                    tabletr += "<th align='center' bgcolor='#E4F1FA'>商品名称</th>";
                    tabletr += "<th align='center' bgcolor='#E4F1FA'>单位</th>";
                    tabletr += "<th align='center' bgcolor='#E4F1FA'>计价单位</th>";
                    tabletr += "<th align='center' bgcolor='#E4F1FA'>库存量</th>";
                    /*if ($("#selectType").val() == "projects") {

                     types = "radio";
                     }*/
                    for (var i = 0; i < pageForm.list.length; i++) {
                        tabletr += "<tr style='cursor: hand;' id = " + pageForm.list[i][0] + ">";
                        tabletr += "<td id='check' align='center'>";
                        tabletr += "<input type ='radio' class='ragood' id='invid'  value=" + pageForm.list[i][0] + " name='check'/>";
                        tabletr += "<input type ='hidden' id='divppid'  value=" + pageForm.list[i][1] + " name='check'/></td>";
                        tabletr += "<td id='divBarcode' align='center'>" + pageForm.list[i][6] + "</td>";
                        tabletr += "<td id='divGoodsName'  align='center'>" + pageForm.list[i][2] + "</td>";
                        tabletr += "<td id='divVariableid'  align='center'>" + pageForm.list[i][3] + "</td>";
                        tabletr += "<td id='mnemonicCode' align='center'>" + pageForm.list[i][5] + "</td>";
                        tabletr += "<td id='divInvnum' align='center'>" + pageForm.list[i][7] + "</td>";
                        tabletr += " </tr>";
                    }
                    tabletr += " </table>";
                    $("#body_02").html(tabletr);
                    $("#body_02").show();
                    // alert("数据加载成功");
                    notoken = 0;
                    window.status = "数据加载成功";
                },
                error: function cbf(data) {
                    notoken = 0;
                    alert("数据获取失败！");
                }
            });
        }
    });

    /*******************************************文件上传*******************************************/
    var  state = 'pending';
    var uploader = WebUploader.create({
        auto: true, // 选择文件后自动上传,默认不自动上传需要触发
        swf: 'webuploader文件夹/Uploader.swf', // swf文件路径
        server: '/upload/normal', // 上传文件的接口（替换成你们后端给的接口路径）
        // 选择文件的按钮。可选。
        // 内部根据当前运行是创建，可能是input元素，也可能是flash.
        pick: '#picker',
        accept: {
            extensions: 'xls,xlsx', // 允许的文件后缀，不带点，多个用逗号分割，这里支持老版的Excel和新版的
            mimeTypes: 'application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
        },
        resize: false, // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
        duplicate :true //可重复上传
    });
// 当有文件被添加进队列的时候
    uploader.on( 'fileQueued', function( file ) {

    });
// 文件上传过程中创建进度条实时显示。
    uploader.on( 'uploadProgress', function( file, percentage ) {
        //可以自定义进度条
    });

    uploader.on( 'uploadSuccess', function( file ) {
        alert("已上传");
    });

    uploader.on( 'uploadError', function( file ) {
        alert("上传出错");
    });

    uploader.on( 'uploadComplete', function( file ) {

    });

    uploader.on( 'all', function( type ) {
        if ( type === 'startUpload' ){
            state = 'uploading';
        } else if ( type === 'stopUpload' ){
            state = 'paused';
        } else if ( type === 'uploadFinished' ){
            state = 'done';
        }
    });
});

// 读取本地excel文件
function readWorkbookFromLocalFile(file, callback) {
    var reader = new FileReader();
    reader.onload = function(e) {
        var data = e.target.result;
        var workbook = XLSX.read(data, {type: 'binary'});
        if(callback) callback(workbook);
    };
    reader.readAsBinaryString(file);
}

function action(com, grid) {
    switch (com) {
        case '添加':
            $("#jqModels").jqmShow();
            break;
        case '设置每页显示条数' :
            var url = "/ea/invadj/ea_toSearch.jspa"
            numback(url);
            break;
        case '查询' :
            $("#jqModelSearch2").jqmShow();
            break;
    }
}

function re_load() {
    document.location.href = basePath
        + "/ea/invadj/ea_toSearch.jspa?pageNumber=" + pNumber
        + "&pageForm.pageNumber=" + $("#pageNumber").attr("value")
        + "&search=" + search;
}