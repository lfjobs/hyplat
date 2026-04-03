var pageNumber = 0;
const pageSize = 25;
const clientWidth = document.documentElement.clientWidth;
var posNum;
let isopen = false; //自动贩卖机门是否打开，默认关闭  false:关闭 true:打开

/**
 * 自动贩卖机操作员信息
 * @type {{posNum: (string|string), loginGuid: (string|string), companyid: (string|string), companyIdentifier: (string|string), staffName: (string|string), source: (string|string), hgcode: (string|string)}}
 */
const config = {
    posNum: getcookie("posNum"), //货柜终端机编号
    hgcode: getcookie("hgcode"), //货柜库房编号
    loginGuid: getcookie("loginGuid"), //登录id
    companyid: getcookie("comID"), //公司id
    staffName: getcookie("staffName"), //人员姓名
    user: getcookie("user"), //人员帐号
    companyIdentifier: getcookie("companyIdentifier"), //公司标识
    source: getcookie("source") //来源 scan:扫码 login:登录
}

window.onload = function () {

    //获取窗口的尺寸
    // var clientWidth = document.documentElement.clientWidth;
    // document.getElementsByTagName('html')[0].style.fontSize = clientWidth / 640 * 40 + 'px';
    // window.onload = window.onresize = setFont;

    if (config != null && config.posNum != null && config.posNum != "") {
        $(".title").html(config.hgcode + "-库存管理");
        posNum = config.posNum;
        //MQTTconnect();
    }

    /*$.ajax({
        url: basePath + "/ea/sm/sajax_ea_pageJump.jspa",
        type: "get",
        data: {
            name:config.staffName,
            posNum:config.posNum,
            hgcode: config.hgcode
        },
        dataType: "json",
        success: function cbf(data) {
            var m = eval("(" + data + ")");
        }, error: function cbf(data) {
        }
    });*/

    getList();

    //分页滚动
    $(window).scroll(function () {
        var Height = $(window).height();
        var scroll = $(document).scrollTop(); //滚动高度
        var Top = $(".last1").offset().top; //元素距离顶部距离
        if (Top - Height - scroll <= 20) {
            if (pageNumber < pageCount) {
                getList();
            }
        }
    });

    //选中
    $(document).on("click", ".ul li", function (event) {
        event.stopPropagation();
        if ($(this).is(".active")) {
            $(this).removeClass("active");
        } else {
            $(".ul .active").removeClass("active");
            $(this).addClass("active");
        }
    });


    // 提示框显示
    $(".close-tingyong").click(function () {
        $(".div-tingyong").hide();
    });

    // 查询框显示
    $(".query-p").click(function () {
        $(".div-chaxun").show();
    });

    // 查询框隐藏
    $(".div-chaxun").click(function () {
        $(".div-chaxun").hide();
    });

    //查询
    $(".p-bottom").click(function () {
        let ln = $("#ln").val();
        let pname = $("#pname").val();
        let depotid = $("#depotID").val();
        if ((pname == null || pname == "") && (depotid == null || depotid == "")) {
            $(".title-p").text("请填写查询条件");
            $(".div-tingyong").show();
            $(".div-chaxun").hide();
        } else {
            pageNumber = 0;
            $(".ul").empty();
            $(".ul").append("<li class='clearfix'><div class='title-pc'></div></li>");
            getList();
        }
    });

    $(".div-chaxun .div-box").click(function (e) {
        e.stopPropagation();
    });

    //补货
    $(".supplement").click(function () {
        // 获取当前选中的元素，并确保其 ID 存在且有效
        let $activeElement = $(".active");
        let id = $activeElement.attr("id");
        getDoorStatus();
        if (!isopen || config == null || config.posNum == null || config.posNum == "" || config.user == null || config.user == "") {
            $(".title-p").text("请扫码开门后补货"); // 修正拼写错误
            $(".div-tingyong").show();
            return false;
        }

        if (!id) {
            $(".title-p").text("请选择补货产品"); // 修正拼写错误
            $(".div-tingyong").show();
            return false;
        }

        // 缓存 DOM 元素以提高性能
        let $targetElement = $("#" + id);

        // 构建参数对象
        let params = {
            'depotName': $targetElement.find(".depot-p").text(),
            'depotCoding': $targetElement.find(".codeing-p").text(),
            'stanpro': $targetElement.find(".stanpro-p").text(),
            'variableID': $targetElement.find(".mu-p").text(),
            'ppId': id,
            'goodsName': $targetElement.find(".name-p").text(),
            'barCode': $targetElement.find(".barCode-p").text(),
            'quantity': $targetElement.find(".quantity-p").text(),
            'singleWeight': $targetElement.find(".singleWeight-p").text(),
        };

        // 存储参数到 localforage，并处理成功和失败的情况
        localforage.setItem('params', params)
            .then(function (value) {
                console.log(value);
                $(".li-title").text("补货");
                // 继续执行其他操作
                $("#iframe-").attr("src", basePath + "page/WFJClient/ProductsLaunch/ProductCalculationNum.jsp?originPage=win-bh");
                $(".div-iframe").show();
                $("#overfover").css("overflow", "hidden");

            }).catch(function (err) {
            console.error("存储参数时出错:", err);
            // 提示用户或采取其他措施
            $(".title-p").text("数据传输失败，请稍后再试。");
            $(".div-tingyong").show();
            return false;
        });
    });

    //调价
    $(".price").click(function () {
        let $activeElement = $(".active");
        let id = $activeElement.attr("id");
        let url;

        if (!id) {
            $(".title-p").text("请选择调价产品"); // 修正拼写错误
            $(".div-tingyong").show();
            return false;
        }
        let suid = $("#" + id).find(".suid").val();
        $(".li-title").text("调价");
        if (!suid || suid == "null") {
            // 构建参数对象
            let params = {
                'ppId': id,
                'ppname': $activeElement.find(".name-p").text(),
                'codeing': $activeElement.find(".codeing-p").text()
            };
            // 存储参数到 localforage，并处理成功和失败的情况
            localforage.setItem('params', params)
                .then(function (value) {
                    console.log(value);
                    // 继续执行其他操作
                    $("#iframe-").attr("src", basePath + "/ea/mobile/ea_getPage.jspa?yjtype=retail-li&priceType=02&originPage=win-tj");
                    $(".div-iframe").show();
                    $("#overfover").css("overflow", "hidden");
                }).catch(function (err) {
                console.error("存储参数时出错:", err);
                // 提示用户或采取其他措施
                $(".title-p").text("数据传输失败，请稍后再试。");
                $(".div-tingyong").show();
                return false;
            });
        } else {
            $("#iframe-").attr("src", basePath + "/ea/mobile/ea_getPage.jspa?priceid=" + suid + "&yjtype=retail-li&priceType=02&originPage=win-tj");
            $(".div-iframe").show();
            $("#overfover").css("overflow", "hidden");
        }
    });

    //仓库管理
    $("#depotname").click(function () {
        $(".li-title").text("仓库管理");
        $("#iframe-").attr("src", basePath + "/page/WFJClient/pc/5l5C/office/inventory/depotManageTransfer.jsp?originPage=win-rk");
        //$(".div-chaxun").hide();
        $(".div-iframe").show();
        $("#overfover").css("overflow", "hidden");
    });

    //开关门
    $(".door-p").click(function () {
        //adminLogin(basePath + "/ea/sm/ea_getScanhg.jspa?hgcode=hg001&door=1&user=15801151282");
        //判断是否管理员扫码登录
        if (config.source == "" || config.source == null || config.hgcode == "") {
            if (typeof Android !== 'undefined') {
                Android.callscan();
            } else {
                $(".title-p").text("管理员请在app内扫码开门"); // 修正拼写错误
                $(".div-tingyong").show();
                return false;
            }
        } else {
            if (config.source == "login") {
                window.location.href = basePath + "page/ea/main/marketing/supermarket/container/selectDoor2.jsp?hgcode=" + config.hgcode + "&loginMode=admin&staffName=" + config.staffName + "&posNum=" + config.posNum;
                return false;
            }
            if (config.source == "scan") {
                if (typeof Android !== 'undefined') {
                    Android.callscan();
                } else {
                    $(".title-p").text("管理员请在app内扫码开门"); // 修正拼写错误
                    $(".div-tingyong").show();
                    return false;
                }
            }
        }
    });

    //关闭iframe
    $(".close-iframe").click(function () {
        $(".div-iframe").hide();
        $("#iframe-").attr("src", "");
        $("#overfover").css("overflow", "auto");
    });

    //发布
    $(".add").click(function () {
        getDoorStatus();
        /*if (!isopen || config == null || config.posNum == null || config.posNum == "" || config.user == null || config.user == "") {
            $(".title-p").text("请扫码开门后发布商品"); // 修正拼写错误
            $(".div-tingyong").show();
            return false;
        }*/
        //var id = $(".active").attr("id");
        window.parent.location.href = basePath + "page/WFJClient/ProductsLaunch/selectProList.jsp?";
        //window.parent.location.href = basePath + "/ea/productslaunch/ea_toProductsLaunch.jspa?";
    });

    //上架
    $(".up-p").click(function () {
        // 获取当前选中的元素，并确保其 ID 存在且有效
        let $activeElement = $(".active");
        let id = $activeElement.attr("id");

        if (!id) {
            $(".title-p").text("请选择要上架的产品"); // 修正拼写错误
            $(".div-tingyong").show();
            return false;
        }
        if (window.confirm('你确定要上架吗？')) {
            ajaxUpOrdown("oncang", id);
        }
    });

    //下架
    $(".down-p").click(function () {
        // 获取当前选中的元素，并确保其 ID 存在且有效
        let $activeElement = $(".active");
        let id = $activeElement.attr("id");

        if (!id) {
            $(".title-p").text("请选择要下架的产品"); // 修正拼写错误
            $(".div-tingyong").show();
            return false;
        }
        if (window.confirm('你确定要下架吗？')) {
            ajaxUpOrdown("onsale", id);
        }
    });

    //查看
    $(".browse").click(function () {
        // 获取当前选中的元素，并确保其 ID 存在且有效
        let $activeElement = $(".active");
        let id = $activeElement.attr("id");

        if (!id) {
            $(".title-p").text("请选择产品"); // 修正拼写错误
            $(".div-tingyong").show();
            return false;
        }
        getBrowse(id);
    });

}

//贩卖机管理员登录
function adminLogin(data) {
    try {
        // 使用 JSON.parse 解析 data，避免使用 eval
        let url = new URL(data);
        // 初始化 params 数组
        let params = [];
        // 获取参数值
        let posNum = url.searchParams.get('posNum');
        let user = url.searchParams.get('user');
        let hgcode = url.searchParams.get('hgcode');
        let door = url.searchParams.get('door');
        params.push("posNum=" + posNum);
        params.push("user=" + user);
        params.push("hgcode=" + hgcode);
        params.push("door=" + door);
        params.push("source='scan'");
        // 确保 basePath 已定义，并正确拼接 URL
        window.location.href = (basePath || "") + "page/ea/main/marketing/supermarket/container/adminOpen.jsp?" + params.join("&");
    } catch (e) {
        alert(e);
    }
}

//上下架
function ajaxUpOrdown(flag, ppId) {
    var f = false;
    var url = basePath + "ea/productslaunch/sajax_ea_upOrdown.jspa?flag=" + flag + "&ppId=" + ppId;
    $.ajax({
        url: url,
        type: "get",
        async: false,
        success: function cbf(data) {
            var member = eval("(" + data + ")");
            var flag = member.flag;
            if (flag == "1") {
                f = true;
                window.location.reload();
            }
        },
        error: function () {
            alert("操作失败！");
            f = false;
        }
    });
    return f;
}

//查看
function getBrowse(id) {
    var goodsId = $("#" + id).find(".goodid").val();
    var params = new Array();
    params.push("user=" + config.user);
    params.push("&companyId=" + config.companyid);
    params.push("&ppId=" + id);
    params.push("&goodsId=" + goodsId);
    window.location.href = basePath + "ea/productslaunch/ea_toProductsLaunch.jspa?" + params.join("");
}

//仓库选中回调
function FeedbackDepot(data) {
    $("#depotID").val(data.depotID);
    $("#depotname").val(data.text);
}


//开关门状态
function onDoorStateChange(data) {
    var jsonArray = JSON.parse(data);
    for (var i = 0; i < jsonArray.length; i++) {
        const {doorNumber, status} = jsonArray[i];
        if (status == "OPENED") {
            isopen = true;
            break;
        }
    }
}


function getList() {
    pageNumber++;
    $(".div-chaxun").hide();
    let pname = $("#pname").val();
    let depotid = $("#depotID").val();
    var url = "/ea/productslaunch/sajax_ea_ProductManageMobileList.jspa";
    $.ajax({
        url: basePath + url,
        type: "get",
        dataType: "json",
        aysnc: false,
        data: {
            "pageForm.pageNumber": pageNumber,
            "pageForm.pageSize": pageSize,
            "pname": pname,
            "depotid": depotid
        },
        success: function (data) {
            if (data != null) {
                var member = eval("(" + data + ")");
                if (member != null) {
                    var flag = member.flag;
                    if (flag == "1") {
                        var error = member.error;
                        if (error == "0") {
                            if (config.loginGuid != null && config.loginGuid != "") {
                                document.location.href = basePath + "page/ea/main/marketing/supermarket/container/adminOpen.jsp";
                            } else {
                                document.location.href = basePath + "page/WFJClient/pc/pc_login.jsp";
                            }
                        } else {
                            $(".title-p").text(member.msg);
                            $(".div-tingyong").show();
                            return false;
                        }
                    } else {
                        var pageForm = member.pageForm;
                        var dlList = member.dlList;
                        /*var listnum=3+dlList.length;*/
                        var listnum = 3 + 0;
                        var htmlstr = new Array();
                        var divlstr = new Array();
                        if (pageForm != null && pageForm.recordCount > 0) {
                            //pageNumber = pageForm.pageNumber;
                            pageCount = pageForm.pageCount;
                            console.log(pageNumber);
                            $(".last1").remove();
                            if (pageNumber == 1) {
                                divlstr.push("<div class='sex'> 选择</div>");
                                divlstr.push("<div class='img-p pc'> 图片</div>");
                                divlstr.push("<div class='codeing-p'> 货架号</div>");
                                divlstr.push("<div class='depot-p'> 仓库名</div>");
                                divlstr.push("<div class='barCode-p pc'> 产品条码</div>");
                                divlstr.push("<div class='name-p'> 产品名称</div>");
                                divlstr.push("<div class='stanpro-p pc'> 标品</div>");
                                divlstr.push("<div class='singleWeight-p pc'> 单重</div>");
                                divlstr.push("<div class='price-p'> 价格</div>");
                                /*if (clientWidth >= 960) {
                                    for (var i = 0; i < dlList.length; i++){
                                        divlstr.push("<div class='dl-p pc'>"+dlList[i].goodsName+"</div>");
                                    }
                                }*/
                                divlstr.push("<div class='ef-p pc'> 出厂价</div>");
                                divlstr.push("<div class='proxy-p pc'> 代理金</div>");
                                divlstr.push("<div class='brokerage-p pc'> 业务金</div>");
                                divlstr.push("<div class='sub-p pc'> 红包金</div>");
                                divlstr.push("<div class='paytype-p pc'> 交易类型</div>");
                                divlstr.push("<div class='mu-p pc'> 计量单位</div>");
                                divlstr.push("<div class='quantity-p'> 库存量</div>");
                                divlstr.push("<div class='show-p'> 是否上架</div>");
                                $(".title-pc").append(divlstr.join(""));
                            }
                            for (var i = 0; i < pageForm.list.length; i++) {
                                if (i == pageForm.list.length - 1) {
                                    htmlstr.push("<li class= 'clearfix last1' id='" + pageForm.list[i][2] + "' style='background-color:"+ (pageForm.list[i][21] == "01" ? "#f1b1b1" : pageForm.list[i][21] == "00" ? "#e3f5c2" : "-") +"'>");
                                } else {
                                    htmlstr.push("<li class='clearfix' id='" + pageForm.list[i][2] + "' style='background-color:"+ (pageForm.list[i][21] == "01" ? "#f1b1b1" : pageForm.list[i][21] == "00" ? "#e3f5c2" : "-") +"'>");
                                }
                                htmlstr.push("<input class='suid' type='hidden' value='" + pageForm.list[i][20] + "'/>");
                                htmlstr.push("<input class='goodid' type='hidden' value='" + pageForm.list[i][10] + "'/>");
                                htmlstr.push("<div class='title-pc'><div class='sex'>");
                                htmlstr.push("<img class='img-01' src='" + basePath + "images/ea/office/contract/selectp/img_02.png'/>");
                                htmlstr.push("<img class='img-02' src='" + basePath + "images/ea/office/contract/selectp/img_03.png'/>");
                                htmlstr.push("</div>");
                                var img = pageForm.list[i][9];
                                htmlstr.push("<div class='img-p pc' title='" + pageForm.list[i][9] + "'><img src='" + basePath + img + "' style='width: 5rem'/></div>");
                                htmlstr.push("<div class='codeing-p' title='" + pageForm.list[i][13] + "'>" + pageForm.list[i][13] + "</div>");
                                htmlstr.push("<div class='depot-p' title='" + pageForm.list[i][14] + "'>" + pageForm.list[i][14] + "</div>");
                                htmlstr.push("<div class='barCode-p pc' title='" + pageForm.list[i][1] + "'>" + (pageForm.list[i][1] == null ? "-" : pageForm.list[i][1]) + "</div>");
                                htmlstr.push("<div class='name-p' title='" + pageForm.list[i][0] + "'>" + pageForm.list[i][0] + "</div>");
                                htmlstr.push("<div class='stanpro-p pc' title='" + pageForm.list[i][17] + "'>" + (pageForm.list[i][17] == "0" ? "是" : pageForm.list[i][17] == "1" ? "否" : "-") + "</div>");
                                htmlstr.push("<div class='singleWeight-p pc' title='" + pageForm.list[i][18] + "'>" + ((pageForm.list[i][18] == null || pageForm.list[i][18] == "") ? 0 : pageForm.list[i][18]) + "</div>");
                                htmlstr.push("<div class='price-p' title='" + pageForm.list[i][3] + "'>￥" + (pageForm.list[i][3] == null ? 0 : pageForm.list[i][3]) + "</div>");
                                /*if (clientWidth >= 960) {
                                    for (var j = 0; j < dlList.length; j++){
                                        htmlstr.push("<div class='dl-p pc'>￥" + (pageForm.list[i][4+j]==null?0:pageForm.list[i][4+j]) + "</div>");
                                    }
                                }*/
                                htmlstr.push("<div class='ef-p pc'>￥" + (pageForm.list[i][5] == null ? 0 : pageForm.list[i][5]) + "</div>");
                                htmlstr.push("<div class='proxy-p pc'>￥" + (pageForm.list[i][7] == null ? 0 : pageForm.list[i][7]) + "</div>");
                                htmlstr.push("<div class='brokerage-p pc'>￥" + (pageForm.list[i][6] == null ? 0 : pageForm.list[i][6]) + "</div>");
                                htmlstr.push("<div class='sub-p pc'>￥" + (pageForm.list[i][8] == null ? 0 : pageForm.list[i][8]) + "</div>");
                                htmlstr.push("<div class='paytype-p pc'> " + (pageForm.list[i][19] == null ? "-" : pageForm.list[i][19]) + "</div>");
                                htmlstr.push("<div class='mu-p pc' title='" + pageForm.list[i][16] + "'>" + (pageForm.list[i][16] == null ? "-" : pageForm.list[i][16]) + "</div>");
                                htmlstr.push("<div class='quantity-p'>" + pageForm.list[i][15] + "</div>");
                                htmlstr.push("<div class='show-p'>" + (pageForm.list[i][21] == "01" ? "是" : pageForm.list[i][21] == "00" ? "否" : "-") + "</div>");
                                htmlstr.push("</div></li>");
                            }
                            $(".ul").append(htmlstr.join(""));
                        }
                    }
                }
            }
        },
        error: function (data) {
            console.log(data);
        }
    });

}
