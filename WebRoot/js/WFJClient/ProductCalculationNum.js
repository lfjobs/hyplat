const $parentWin = $(window.parent.document);
const u = window.navigator.userAgent;
const isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
const isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
const config = {
    posNum: getcookie("posNum"), //货柜终端机编号
    hgcode: getcookie("hgcode"), //货柜库房编号
    loginGuid: getcookie("loginGuid"), //登录id
    companyid: getcookie("comID"), //公司id
    staffName: getcookie("staffName"), //人员姓名
    companyIdentifier: getcookie("companyIdentifier") //公司标识
}
let ppId;
let depotCoding;
let depotid;
let qpqlWeight = 0;//记录去皮、清零前的重量
let requestPage;//返回页面
let swStruts;//0:单重已确定，1：单重未确认
var index = 0;//获取称重次数从0开始
var stanpros;//记录秤原有产品是标重还是非标重 是：标重，否：非标重
var singleWeightArray = [];//记录秤原有标重产品的标准重量
var Error_Num;//标重下预设的误差值

window.onload = window.onresize = function () {
    //含义：当窗口加载完成和窗口尺寸变化的时候都执行大括号里面的命令
    //获取窗口的尺寸
    var clientWidth = document.documentElement.clientWidth;
    //console.log(clientWidth);
    //通过屏幕宽度去设置不同的后台根字体的大小
    //document.getElementsByTagName('html')[0].style.fontSize = clientWidth/16+'px';
    if (clientWidth >= 960) {
        document.getElementsByTagName('html')[0].style.fontSize = clientWidth / 640 * 8 + 'px';

    } else {
        //通过屏幕宽度去设置不同的后台根字体的大小
        document.getElementsByTagName('html')[0].style.fontSize = clientWidth / 640 * 40 + 'px';
    }
}

window.onload = function () {

    $("#prompt").css("position", "absolute").css("top", $(window).height() * 0.09 + "px");
    $("#prompt").find("div").css("height", $(window).height() * 0.06 + "px").css("font-size", $(window).height() * 0.0285 + "px").css("color", "#FFFFFF");
    $("#prompt").find("div").css("-moz-border-radius", $(window).height() * 0.015 + "px").css("-webkit-border-radius", $(window).height() * 0.015 + "px");

    if (originPage != "" && originPage != null) {
        requestPage = originPage.split("-");
        if (requestPage[0] == "win") {
            $("header").hide();
        }
        if (requestPage[1] == "bh") {
            $("#bh").show();
            $("#isok").hide();
            swStruts=0;
        } else if (requestPage[1] == "rk") {
            $("#bh").hide();
            $("#isok").show();
            $(".singleWeight").addClass("sw");
            swStruts=1;
        }
    }

    /**
     * 页面赋值
     */
    localforage.getItem('params').then(function (value) {
        // 检查 value 是否为有效对象且不为空字符串
        if (value && typeof value == 'object' && Object.keys(value).length > 0) {
            // 确保所有属性存在并安全设置文本内容
            const safeSetText = (selector, property) => {
                if (property in value) {
                    $("." + selector).text(value[property]);
                    $("#" + selector).val(value[property]);
                }
            };

            safeSetText("depotManage", "depotName");
            $(".depotManage").append(" (" + (value.depotCoding || '') + ")");
            depotCoding = value.depotCoding;
            getsingleWeights(depotCoding);
            safeSetText("stanpro", "stanpro");
            safeSetText("variable", "variableID");
            safeSetText("ppId", "ppId");
            ppId = value.ppId;
            depotid=value.depotID;
            safeSetText("goodsName", "goodsName");
            safeSetText("barCode", "barCode");
            safeSetText("invNum", "quantity");

            if (value.stanpro == '是') {
                safeSetText("singleWeight", "singleWeight");
               /* $(".singleWeight").text(value.singleWeight || 0);*/
                $(".upn").show();
                if (requestPage[1] == "rk") {
                    $(".main_inp_right_unit").show();
                } else if (requestPage[1] == "bh") {
                    $(".main_inp_right_unit").hide();
                }
                $(".sw-div").show();
            } else {
                $(".upn").hide();
                $(".variable").text("KG");
                swStruts = 0;
            }

            if(ppId!=null&&ppId!=""&&requestPage[1] == "rk"){
                getDepotByProid();
                if (value.stanpro == '是'&&value.singleWeight!=null&&value.singleWeight!="") {
                    $(".main_inp_right_unit").hide();
                }
            }
        }
        // 移除存储项并处理异常
        return localforage.removeItem('params');
    }).catch(function (err) {
        console.error("Error occurred:", err);
        // 可以在此处添加更多处理逻辑，例如显示错误信息给用户
    });

    /**
     * 归零、去皮处理
     */
    $(".footer_half").click(function () {
        qpqlWeight = truncateToCustomDecimals($(".changeWeight").text(),3,'floor') + qpqlWeight;
        alert(qpqlWeight);
        $(".initialWeight").text("0");
        //incrementWeightUntilRandom();
    });

    /**
     * 补货
     */
    $("#bh").click(function () {
        if(window.confirm('确认入库量和标重是否正确！')){
            //getSingleWeight();
            let launchForm = $("#launchForm").serializeArray();
            let uri = basePath + "ea/productslaunch/sajax_ea_calculationNum.jspa?";
            console.log(uri);
            console.log(encodeURI(uri));
            $.ajax({
                url: encodeURI(uri),
                type: "post",
                dataType: "json",
                data: launchForm, // 假设 serializeArray 是 launchForm 的别名
                success: function (data) {
                    console.log($.type(data));
                    if (data !== null && (typeof data == 'object' || typeof data == 'string')) {
                        var result = eval("(" + data + ")");
                        if (result.flag == "1") {
                            if (result.code == "0") {
                                window.location.href = basePath + "page/WFJClient/pc/pc_login.jsp";
                            } else {
                                prompt(result.msg);
                                return false;
                            }
                        } else {
                            $parentWin.ready(function () {
                                /*$('.div-iframe', $parentWin).hide();
                                $('.div-iframe', $parentWin).find("iframe").attr("src", "");*/
                                window.parent.location.reload();
                            });
                        }
                    } else {
                        console.error("Invalid response format:", data);
                    }
                },
                error: function (xhr, status, error) {
                    console.error("AJAX request failed:", error);
                    prompt("Error occurred: " + error);
                }
            });
        }
    });

    /**
     * 确定
     */
    $("#isok").click(function () {

        //getSingleWeight();

        let inv = {
            "singleWeight": $(".singleWeight").text(),
            "invNum": $(".quantity").text()
        }
        window.parent.FeedbackInv(inv);
        // 隐藏 iframe 并重置其 src 属性
        $('.div-iframe', $parentWin).hide();
        $('.div-iframe', $parentWin).find("iframe").attr("src", "");
    });

    /**
     * 定时获取时时重量
     */
    const intervalId = setInterval(function () {
        getWeight();
        //generateRandomFloat();
    }, 100);
    $(".initialWeight").text(generateRandomFloat());


    /**
     * 增加入库量
     */
    $(".add-number").click(function () {
        clearInterval(intervalId);
        let num = $(".span-number").html();
        if (num < 999) {
            num++;
        }
        $(".span-number").html(num);
        $("#quantity").val(num);
    });

    /**
     * 减少入库量
     */
    $(".reduce-number").click(function () {
        clearInterval(intervalId);
        let num = $(".span-number").html();
        if (num > 0) {
            num -= 1;
        }
        $(".span-number").html(num);
        $("#quantity").val(num);
    });

    /**
     * 确认标重
     */
    $(document).on("click", ".isoksw", function () {
        let falg = false;
        if (stanpros == 0) {
            for (var i = 0; i < singleWeightArray.length; i++) {
                let singleWeight = singleWeightArray[i];
                falg = isMultiple(singleWeight, $(".singleWeight").text());
                if (falg) {
                    break;
                }
            }
        }
        if (!falg) {
            $(this).text("修改标重");
            $(this).addClass("updatesw");
            $(this).removeClass("isoksw");
            $(".sw").removeClass("sw");
            swStruts = 0;
        }
    });

    /**
     * 修改标重
     */
    $(document).on("click", ".updatesw", function () {

        $(this).text("确认标重");
        $(this).addClass("isoksw");
        $(this).removeClass("updatesw");
        $(".singleWeight").addClass("sw");
        swStruts = 1;
    });
}

/**
 * 根据产品id获取库存
 */
function getDepotByProid() {
    $.ajax({
        url: basePath + "ea/productslaunch/sajax_ea_getDepotByProid.jspa?",
        type: "get",
        dataType: "json",
        data: {
            "ppId": ppId,
            "depotid":depotid
        },
        success: function (data) {
            let member = eval("(" + data + ")");
            if (member.flag == "0") {
                for (let i = 0; i < member.invl.length; i++) {
                    let inv = member.invl[i];
                    if (inv.warehouse==depotid){
                        $(".invNum").text(inv.invenQuantity);
                        $(".main_inp_right_unit").hide();
                        break;
                    }
                }
            }else {
                prompt(member.msg);
            }
        }
    });
}

/**
 * 弹框处理
 * @param obj
 */
function prompt(obj) {
    const $prompt = $("#prompt");
    const $promptSpan = $prompt.find("span");

    // 检查提示框是否已经可见
    if ($prompt.is(":visible")) {
        return;
    }

    // 确保传入的内容是安全的
    const safeText = String(obj).replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;");
    $promptSpan.text(safeText);

    // 显示提示框
    $prompt.fadeIn(500);

    // 清除之前的定时器，确保只有一个定时器在运行
    clearTimeout($prompt.data('timeoutId'));

    // 设置新的定时器，在2秒后隐藏提示框并清空文本
    const timeoutId = setTimeout(function () {
        $prompt.fadeOut(500, function () {
            $promptSpan.text("");
        });
    }, 2000);

    // 存储定时器ID以便后续清除
    $prompt.data('timeoutId', timeoutId);
}

/**
 * 判断两个浮点型数值是否互为倍数
 * @param {number} num1 - 第一个数值
 * @param {number} num2 - 第二个数值
 * @returns {boolean} - 如果互为倍数则返回 true，否则返回 false
 */
function isMultiple(num1, num2) {
    if (num1 == 0 || num2 == 0) return false; // 0 不能作为倍数
    const ratio1 = num1 / num2;
    const ratio2 = num2 / num1;
    // 判断比值是否接近整数，允许一定的误差
    const epsilon = 0.0001;
    return Math.abs(ratio1 - Math.round(ratio1)) < epsilon || Math.abs(ratio2 - Math.round(ratio2)) < epsilon;
}

/**
 * 自定义保留小数位数并指定进位方式
 * @param {number} num - 原始数字
 * @param {number} precision - 保留的小数位数
 * @param {string} roundingMethod - 进位方式，可选值为 'round'（四舍五入）、'ceil'（向上取整）、'floor'（向下取整）
 * @returns {number} - 处理后的数字
 * 1.5867579908675797
 */
function truncateToCustomDecimals(num, precision, roundingMethod = 'round') {
    // 将数字转换为字符串
    let numStr = num.toString();
    console.log("将数字转换为字符串:"+numStr);
    // 查找小数点的位置
    let dotIndex = numStr.indexOf('.');
    console.log("查找小数点的位置:"+dotIndex);

    if (dotIndex == -1&&precision>0) {
        // 如果没有小数点，直接添加 '.000'
        return numStr + '.' + '0'.repeat(precision);
    }

    console.log("如果没有小数点，直接添加 '.000':"+numStr);

    // 截取到小数点后 precision 位
    let truncatedStr = numStr.slice(0, dotIndex + precision + 2);
    console.log("截取到小数点后 precision 位:"+truncatedStr);

    let truncatedNum = parseFloat(truncatedStr);
    console.log("数字:"+truncatedNum);
    console.log("数字:"+Math.pow(10, precision));

    let returnNum;
    // 根据进位方式处理数字
    switch (roundingMethod) {
        case 'ceil':
            returnNum= Math.ceil(truncatedNum * Math.pow(10, precision)) / Math.pow(10, precision);
        case 'floor':
            returnNum= Math.floor(truncatedNum * Math.pow(10, precision)) / Math.pow(10, precision);
        case 'round':
        default:
            returnNum= Math.round(truncatedNum * Math.pow(10, precision)) / Math.pow(10, precision);
    }
    console.log("终:"+returnNum);
    return returnNum
}

/**
 * 获取实时重量
 */
function getWeight() {
    if (depotCoding != "undefined" && depotCoding != null && depotCoding != "") {
        $.ajax({
            url: basePath + "ea/productslaunch/sajax_ea_getWeitht.jspa",
            type: "get",
            dataType: "json",
            data: {
                "scaleCoding": depotCoding,
                "containerCoding": config.posNum
            },
            success: function (data) {
                let member = eval("(" + data + ")");
                if (member.flag == "0") {
                    var weight = truncateToCustomDecimals(member.weight - qpqlWeight, 3,'floor');
                    if (index == 0) {
                        $(".initialWeight").text(weight); //初始重量
                        $(".changeWeight").text(weight); //时时重量
                        $(".sw").text(weight); //单重
                    } else {
                        $(".changeWeight").text(weight); //时时重量
                        let initialWeight = $(".initialWeight").text();//初始重量
                        let subtract = (weight - initialWeight);
                        subtract = subtract < 0 ? 0 : subtract;
                        subtract = truncateToCustomDecimals(subtract, 3);
                        $(".sw").text(subtract); //标重
                    }
                    if (swStruts == 0) {
                        quantityManage(weight);
                    }
                    index++;
                } else {
                    prompt(member.msg);
                }
            }
        });
    }
}

/**
 * 生成三位小数的随机数
 * @returns {number} - 三位小数的随机数
 */
function generateRandomFloat() {
    return parseFloat((Math.random()).toFixed(3)); // 生成0到10之间的三位小数随机数
}

/**
 * 实时重量递增，直到达到随机数
 * @param {number} initialWeight - 初始重量
 */
function incrementWeightUntilRandom() {
    const randomWeight = truncateToCustomDecimals(generateRandomFloat(),3,'floor'); // 生成随机数
    let currentWeight = truncateToCustomDecimals($(".initialWeight").text(),3,'floor');
    let r=randomWeight+currentWeight;
    // 显示随机数
    console.log("目标随机数: " + randomWeight);
    console.log("目标随机数: " + r);


    const intervalId = setInterval(() => {
        if (currentWeight >= r) {
            clearInterval(intervalId); // 停止递增
            console.log("达到随机数，停止递增。");
        } else {
            currentWeight = truncateToCustomDecimals(currentWeight + 0.005,3,'floor'); // 以0.005递增
            console.log("实时重量："+currentWeight);
            $(".changeWeight").text(currentWeight);
            quantityManage(currentWeight);
        }
    }, 100); // 每100毫秒递增一次
}

/**
 * 计算入库数量
 * @param data 实时重量
 */
function quantityManage(data) {
    let weight = data;//实时重量
    let initialWeight = truncateToCustomDecimals($(".initialWeight").text(), 3,'floor');//初始重量
    const stanpro = $(".stanpro").text();
    if (initialWeight >= weight) {
        prompt("没有入库重量");
        return;
    }
    let subtract = (weight - initialWeight);
    subtract = subtract < 0 ? 0 : subtract;
    subtract = truncateToCustomDecimals(subtract, 3,'floor');

    if (stanpro == "是") {
        let singleWeight = truncateToCustomDecimals($(".singleWeight").text(), 3,'floor');//标重
        if (singleWeight == null || singleWeight == "") {
            prompt("单重有误！");
            return;
        }
        if (singleWeight == 0 || swStruts == 1) {
            prompt("请先获取单重重量！");
            return;
        }
        //let upLimitDivide = subtract / (parseFloat(singleWeight)+Error_Num);
        //let loLimitDivide = subtract / (parseFloat(singleWeight)-Error_Num);
        let divide = truncateToCustomDecimals(subtract / singleWeight, 0,'round');
        //divide = divide < 0 ? 0 : divide;
        console.log("数量："+divide);
        prompt("数量："+divide);
        //divide = truncateToCustomDecimals(divide, 3,'round');
        //console.log("四舍五入:"+truncateToCustomDecimals(divide, 0,'round'));
        //if (loLimitDivide<divide<upLimitDivide){
        $(".quantity").text(divide);
        $("#quantity").val(divide);
        //}else {
        //prompt("入库重量超出范围");
        //}
    } else {
        $(".quantity").text(subtract);
        $("#quantity").val(subtract);
    }
}

/**
 *处理标重（根据数量取平均值）
 */
function getSingleWeight() {
    let weight = parseFloat($(".changeWeight").text());//实时重量
    let initialWeight = parseFloat($(".initialWeight").text());//初始重量
    const stanpro = $(".stanpro").text();
    var q=parseFloat($(".quantity").text());
    let subtract = (weight - initialWeight);
    subtract = subtract < 0 ? 0 : subtract;
    subtract = truncateToCustomDecimals(subtract, 3,'floor');

    if (stanpro == "是") {
        $(".singleWeight").text(truncateToCustomDecimals(subtract/q, 3));
    }
}

/**
 * 获取秤盘原有产品信息进行验证
 * @param data 秤盘编号
 */
function getsingleWeights(data) {
    $.ajax({
        url: basePath + "ea/productslaunch/sajax_ea_getProductTochBalance.jspa",
        type: "get",
        dataType: "json",
        data: {
            "code": data
        },
        success: function (data) {
            let member = eval("(" + data + ")");
            if (member.flag == "0") {
                Error_Num=truncateToCustomDecimals(member.Error_Num,3,'floor');
                for (let i = 0; i < member.productList.length; i++) {
                    let pro = member.productList[i];
                    if (pro[2] == "0") {
                        stanpros = "是";
                        singleWeightArray.push(pro[3]);
                    } else {
                        stanpros = "否";
                        if (requestPage[1] == "rk") {
                            singleWeightArray = null;
                            prompt(data + "秤盘已存在非标品商品，不可再发布其它商品！");
                            $parentWin.history.go(-1);
                            return false;
                            break;
                        }
                    }
                }
            }else {
                prompt(member.msg);
            }
        }
    });
}