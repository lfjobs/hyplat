$(function () {
    $("#activityId").val(activityId);//赋值
    //删除当前活动产品及佣金[更改状态为:01]
    $(document).on('click', '.tab-com-box .del img', function () {
        $(".alert_").show();//显示弹框
        $(this).parent().parent().attr("id", "del");
        var actPriceId = $(this).parents("tr").find(".actPriceId").val();//活动价id
        var ppid = $(this).parents("tr").find(".ppid").val();//产品id
        /*确认删除*/
        $(".alert .btn #qd").click(function () {
            $.ajax({
                type: "POST",
                async: false,
                url: basePath + "ea/activityPrice/sajax_ea_delActivityBrokerageById.jspa",
                data: {
                    "actPrice.actPriceId": actPriceId,
                    "actPrice.ppid": ppid
                },
                success: function (data) {
                    var member = eval("(" + data + ")");
                    var code = member.code;
                    if (code == "200") {
                        $("#del").remove();//删除当前tr
                        $(".alert_").hide();
                    } else {
                        console.log("删除异常！");
                    }
                },
                error: function (data) {
                    console.log("操作异常！");
                },
                dateType: "json"
            });

        });
    });
    /*删除--取消*/
    $(".alert .btn #qx").click(function () {
        $("#del").removeAttr("id");
        $(".alert_").hide();
    });
    //获取当前活动所有产品的活动价及相关佣金并回显
    $.ajax({
        type: "POST",
        url: basePath + "ea/activityPrice/sajax_ea_getActivityPrice.jspa",
        data: {
            "actPrice.activityId": activityId,
            "productPackaging.ppID": "00"//默认给00防止为空异常
        },
        async: false,
        success: function (data) {
            var member = eval("(" + data + ")");
            var actPriceList = member.actPriceList;
            if (actPriceList != null && actPriceList.length > 0) {
                //获取各种佣金百分比[活动商品]
                if (member.code == '200') {
                    var actPriceLength = actPriceList.length;
                    for (var i = 0; i < actPriceLength; i++) {
                        $("#product").append($("#first_td tr").clone(true));//克隆表格每一行数据
                        tr = $("#product").find("tr:eq(" + i + ")");
                        //对应赋值
                        tr.find(".ppid").val(actPriceList[i][6]);
                        tr.find(".actPriceId").val(actPriceList[i][0]);
                        //展示活动价判断获取并赋值
                        if (member.beanList == null || member.beanList.length == 0) {
                            tr.find(".showActPrice").val(actPriceList[i][1]);//展示活动价[系统活动价]
                        } else {
                            var showActPrice = actPriceList[i][1] * (member.beanList[0][1] / 100 + 1);
                            tr.find(".showActPrice").val(showActPrice.toFixed(2));//展示活动价
                        }
                        tr.find(".actPrice").val(actPriceList[i][1]);
                        tr.find(".cost").val(actPriceList[i][2]);
                        tr.find(".ywyj").val(actPriceList[i][3]);
                        tr.find(".brokerages").val(actPriceList[i][4]);
                        tr.find(".selector").val(actPriceList[i][5]);
                        tr.find(".td-1").html(actPriceList[i][8]);
                        tr.find(".td-2").html(actPriceList[i][9]);
                        // alert("获取数据成功！");
                        $.ajax({
                            type: "POST",
                            url: basePath + "ea/activityPrice/sajax_ea_getActivityBrokerage.jspa",
                            data: {
                                "actPrice.actPriceId": actPriceList[i][0]
                            },
                            async: false,
                            success: function (data) {
                                var member = eval("(" + data + ")");
                                var actBroList = member.actBrokerageList;
                                //console.log(actBroList);
                                if (actBroList != null && actBroList.length > 0) {
                                    var actBroLength = actBroList.length;
                                    for (var j = 0; j < actBroLength; j++) {
                                        if (actBroList[j][2] == $("#sbtzId").val()) {
                                            tr.find(".sbtzId").val(actBroList[j][0]);
                                            tr.find(".sbtz").val(actBroList[j][1]);
                                        }
                                        if (actBroList[j][2] == $("#tpId").val()) {
                                            tr.find(".tpId").val(actBroList[j][0]);
                                            tr.find(".tp").val(actBroList[j][1]);
                                        }
                                        if (actBroList[j][2] == $("#sbazId").val()) {
                                            tr.find(".sbazId").val(actBroList[j][0]);
                                            tr.find(".sbaz").val(actBroList[j][1]);
                                        }
                                        if (actBroList[j][2] == $("#sjdlId").val()) {
                                            tr.find(".sjdlId").val(actBroList[j][0]);
                                            tr.find(".sjdl").val(actBroList[j][1]);
                                        }
                                        if (actBroList[j][2] == $("#xjdlId").val()) {
                                            tr.find(".xjdlId").val(actBroList[j][0]);
                                            tr.find(".xjdl").val(actBroList[j][1]);
                                        }
                                        if (actBroList[j][2] == $("#cjdlId").val()) {
                                            tr.find(".cjdlId").val(actBroList[j][0]);
                                            tr.find(".cjdl").val(actBroList[j][1]);
                                        }
                                        if (actBroList[j][2] == $("#khjfId").val()) {
                                            tr.find(".khjfId").val(actBroList[j][0]);
                                            tr.find(".khjf").val(actBroList[j][1]);
                                        }

                                    }
                                }
                            }
                            ,
                            error: function (data) {
                                alert("数据获取异常！");
                            }
                            ,
                            dateType: "json"
                        });
                    }

                } else if (member.code == '400') {
                    //未登录
                    location.href = basePath + "page/ea/not_login.jsp";
                } else {
                    alert("获取数据异常！1111");
                    return;
                }

            } else {
                $("#sure").attr('disabled', "true");
                alert("您还没有添加当前活动所有产品的活动价及相关佣金！");
            }
        },
        error: function (data) {
            alert("数据获取异常！");
        },
        dateType: "json"
    });
    $("#product tr td input").click(function () {
        if ($(this).hasClass("showActPrice") == false) {//文本框class为showActPrice的不予赋值
            var index_tr = $(this).parents("tr").index();
            var input_id = $(this).attr("data-id");
            $(this).attr("name", "actPriceList[" + index_tr + "]." + input_id);
        }
    });
    $("#product tr td input").trigger("click");//触发文本框点击事件

    //给表单下拉框属性统一赋值
    $("#product tr td .selector").click(function () {
        var index_tr = $(this).parents("tr").index();
        var input_id = $(this).attr("data-id");
        $(this).attr("name", "actPriceList[" + index_tr + "]." + input_id);
    });
    $("#product tr td .selector").trigger("click");//触发下拉框点击事件
    var pPercentage_flag = 0;//价格百分比标识
    var bPercentage_flag = 0;//业务佣金百分比标识
    //数值输入框[number]绑定事件[keyup keydown focus blur]
    $(".cost").on("keyup keydown focus blur", function (event) {
        var parentNode = $(this).parent().parent();
        var val = parentNode.find(".cost").val();//成本价获取
        var ppid = parentNode.find(".ppid").val();//产品ppid获取
        //获取各种佣金百分比[活动商品]
        $.ajax({
            type: "POST",
            url: basePath + "ea/wholesale/sajax_ea_getBrokeragePercent.jspa",
            data: {
                "productPackaging.ppID":ppid
            },
            success: function (data) {
                var member = eval("(" + data + ")");
                //console.log(member)
                if (member.code == '200') {
                    //系统活动价判断获取
                    if (member.pPercentage == null) {
                        pPercentage_flag++;
                        if (pPercentage_flag == 1) {
                            alert("您还没有设置活动价百分比！");
                            url = basePath + "ea/percentage/ea_toPPercentageAdd.jspa";
                            window.open(url);
                        }
                        return false;
                    } else {
                        var actPrice = 0;
                        if (activityType == "00") {//普通活动
                            actPrice = member.pPercentage.activity;
                        }
                        if (activityType == "01") {//特价活动
                            actPrice = member.pPercentage.special;
                        }
                        // var actPrice = member.pPercentage.activity;
                        actPrice = val * (actPrice / 100 + 1);
                        //actPrice = actPrice.toFixed(2);
                        parentNode.find(".actPrice").val(actPrice.toFixed(4));//系统活动价
                        //展示活动价判断获取
                        if (member.beanList == null || member.beanList.length == 0) {
                            parentNode.find(".showActPrice").val(actPrice.toFixed(2));//展示活动价[系统活动价]
                        } else {
                            var showActPrice = actPrice * (member.beanList[0][1] / 100 + 1);
                            parentNode.find(".showActPrice").val(showActPrice.toFixed(2));//展示活动价
                        }
                        //佣金判断获取
                        if (member.bPercentage == null) {
                            alert("您还没有设置业务佣金百分比！");
                            return;
                        } else {
                            var commission = actPrice - val;//佣金（活动价-成本价）
                            //计算设备投资佣金
                            var bsbtz = member.bPercentage.sbtz;
                            var sbtz = commission * (bsbtz / 100);
                            sbtz = sbtz.toFixed(4);
                            parentNode.find(".sbtz").val(sbtz);
                            //计算贴牌佣金
                            var btp = member.bPercentage.tp;
                            var tp = commission * (btp / 100);
                            tp = tp.toFixed(4);
                            parentNode.find(".tp").val(tp);
                            //计算设备安装佣金
                            var bsbaz = member.bPercentage.sbaz;
                            var sbaz = commission * (bsbaz / 100);
                            sbaz = sbaz.toFixed(4);
                            parentNode.find(".sbaz").val(sbaz);
                            //计算省级代理佣金
                            var bsjdl = member.bPercentage.sjdl;
                            var sjdl = commission * (bsjdl / 100);
                            sjdl = sjdl.toFixed(4);
                            parentNode.find(".sjdl").val(sjdl);
                            //计算县级代理佣金
                            var bxjdl = member.bPercentage.xjdl;
                            var xjdl = commission * (bxjdl / 100);
                            xjdl = xjdl.toFixed(4);
                            parentNode.find(".xjdl").val(xjdl);
                            //计算村级代理佣金
                            var bcjdl = member.bPercentage.cjdl;
                            var cjdl = commission * (bcjdl / 100);
                            cjdl = cjdl.toFixed(4);
                            parentNode.find(".cjdl").val(cjdl);
                            //计算客户积分佣金
                            var bkhjf = member.bPercentage.khjf;
                            var khjf = commission * (bkhjf / 100);
                            khjf = khjf.toFixed(4);
                            parentNode.find(".khjf").val(khjf);
                            //计算业务佣金
                            /*  var bywyj = member.bPercentage.ywyj;
                             var ywyj = commission * (bywyj / 100);*/
                            var ywyj = commission - sbtz - tp - sbaz - sjdl - xjdl - cjdl - khjf;
                            ywyj = ywyj.toFixed(4);
                            parentNode.find(".ywyj").val(ywyj);
                            //计算代理佣金和
                            var brokerages = commission - ywyj;
                            brokerages = brokerages.toFixed(4);
                            parentNode.find(".brokerages").val(brokerages);
                        }
                    }

                } else if (member.code == '400') {
                    //未登录
                    location.href = basePath + "page/ea/not_login.jsp";
                } else {
                    alert("获取数据异常！");
                }

            },
            error: function (data) {
                alert("获取数据异常！");
            },
            dateType: "json"
        });


    });
    $("input[type='number']").not(".cost").not(".ywyj").on("keyup keydown focus blur", function (event) {
        var parentNode = $(this).parent().parent();
        var ywyj = parentNode.find(".actPrice").val() - parentNode.find(".cost").val() - parentNode.find(".sbtz").val() - parentNode.find(".tp").val()
            - parentNode.find(".sbaz").val() - parentNode.find(".sjdl").val() - parentNode.find(".xjdl").val()
            - parentNode.find(".cjdl").val() - parentNode.find(".khjf").val();
        parentNode.find(".ywyj").val(ywyj.toFixed(4));
    });

    //关闭添加窗口
    $("#close").click(function () {
        if (confirm("您确定要关闭本页吗？")) {
            window.opener = null;
            window.open('', '_self');
            window.close();
        } else {
        }
    });
    /*ajax异步请求防止重复提交*/
    //设置一个对象来控制是否进入AJAX过程
    var post_flag = false;
    //保存数据
    $("#sure").click(function () {
        var price_flag = false;
        var cost_flag = false;
        var sbtz_flag = false;
        var zero_flag = false;
        $.each($('#product tr'), function () {
            var sbtz = $(this).find(".sbtz").val();
            if (sbtz == null || sbtz == '0' || sbtz == '') {
                $(this).find(".selector").val("00");
            }
            if (sbtz != null && sbtz != '0' && sbtz != '' && $(this).find(".selector").val() == '00') {
                sbtz_flag = true;
                return;
            }

            var facto = $(this).find(".actPrice").val() - $(this).find(".sbtz").val() - $(this).find(".tp").val()
                - $(this).find(".sbaz").val() - $(this).find(".sjdl").val() - $(this).find(".xjdl").val()
                - $(this).find(".cjdl").val() - $(this).find(".khjf").val() - $(this).find(".ywyj").val();
            var sj = $(this).find(".cost").val();
            if(parseFloat(sj)==0){
                zero_flag = true;
                return;
            }
            if (Number(sj).toFixed(2) != Number(facto).toFixed(2)) {//判断价格计算结果是否有误
                price_flag = true;
                return
            }
        });
        if (sbtz_flag == true) {
            alert("请选择设备投资类别!");
            return "";
        }
        if (zero_flag == true) {
            alert("成本价不能为0！");
            return "";
        }
        if (price_flag == false) {
            //获取所有佣金
            $.each($('#product .calculate'), function () {
                var calculate = $(this).val();
                if (calculate == null || calculate == '' || parseFloat(calculate) < 0) {//判断
                    cost_flag = true;
                    return;
                }
            });
            if (cost_flag == true) {
                alert("成本价、活动价和佣金不能为空,并且为非负数!");
                return "";
            }
            //如果正在提交则直接返回，停止执行
            if (post_flag) {
                alert("正在提交请稍后...");
                return;
            }
            //标记当前状态为正在提交状态
            post_flag = true;
            $.ajax({
                type: "POST",
                url: basePath + "ea/activityPrice/sajax_ea_updateActivityBrokerage.jspa",
                data: $("#updateActivity").serialize(),
                success: function (data) {
                    var member = eval("(" + data + ")");
                    var code = member.code;
                    post_flag = false; //在提交成功之后将标志标记为可提交状态
                    if (code == "200") {
                        alert("保存成功！");
                        window.opener.location.reload();
                        window.close();
                        location.href = basePath + "ea/activityPrice/ea_selectActivityList.jspa?activityType =" + activityType;
                    } else if (code == "400") {
                        //未登录
                        location.href = basePath + "page/ea/not_login.jsp";
                    } else {
                        alert("参数异常！");
                    }
                },
                error: function (data) {
                    alert("保存异常！");
                },
                dateType: "json"
            });
        } else {
            alert("活动价佣金计算有误，请重新计算！");
            return "";
        }

    });

});

