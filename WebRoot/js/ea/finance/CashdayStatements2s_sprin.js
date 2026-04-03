 $(function() {

        $(".jqmWindow").jqm({
        modal : true,// 限制输入（鼠标点击，按键）的对话
        overlay : 20
            // 遮罩程度%
        }).jqmAddClose('.close');// 添加触发关闭的selector

        var monsum = 0;
        $("span.money1").each(function() {
            var money = $(this).text();
            var id = $(this).parent().parent().attr("id");
            if (money != "" && money.length != 0) {
                monsum = parseFloat(money);
                DX(Math.abs(monsum), id, "a");
            }
        });
        $("#costType").find("option[text='请选择']").attr("selected",true);
        $("span.money2").each(function() {
            var money = $(this).text();
            var id = $(this).parent().parent().attr("id");
            if (money != "" && money.length != 0) {
                monsum = parseFloat(money);
                DX(Math.abs(monsum), id, "b");
            }
        });
        $("span.yue").each(function() {
            var yue = $(this).text();
            var id = $(this).parent().parent().attr("id");
            if (yue != "" && yue.length != 0) {
                monsum = parseFloat(yue);
                DX(Math.abs(monsum), id, "c");
             }
        }); 
        
        //主函数
        function DX(n, id, type) {
            if (!/^(0|[1-9]\d*)(\.\d+)?$/.test(n))
                return "数据非法";
            n = n.toFixed(2);
            var p = n.indexOf('.');
            if (p >= 0)
                n = n.substring(0, p) + n.substr(p + 1, 2);
            var j = n.length - 1;
            for ( var i = 0; i < n.length; i++) {
                var f = 9 - parseInt(i);
                if (type == "a")
                    $("span#" + f, $("#" + id)).text(n.charAt(j));
                else if (type == "b")
                    $("span#1" + f, $("#" + id)).text(n.charAt(j));
                else {
                    f = 11 - parseInt(i);
                    $("span#2" + f, $("#" + id)).text(n.charAt(j));
                }
                j = j - 1;
            }
        }

        $("#searchbutton").click(function() {
            $("#jqModelSearch").jqmShow();
        });

         $("#tosearch").click(function() {
                $("#SearchForm").attr(
                    "action",
                    basePath + "/ea/splitbill/ea_toSprins.jspa?level="+level);
                document.SearchForm.submit.click();
        });

         /** **************************************科目管理******************************************* */
    var subjectsNumber = "";
    var subjectsName = "";
    $(".tosubjects").click(function() {
        $(this).parent().parent().find("td").addClass("receivesubjects");
        $td = $("td.subjects");
        $td.children('select').empty();
        var subRuleurl = basePath
                + "/ea/csubjectsrule/sajax_n_ea_getCSubjectsRule.jspa?date="
                + new Date().toLocaleString();
        var subRule = new Array();
        var endnumber = 0;
        $.ajax({
            url : encodeURI(subRuleurl),
            type : "get",
            async : true,
            dataType : "json",
            success : function cbf(data) {
                var member = eval("(" + data + ")");
                var nologin = member.nologin;
                if (nologin) {
                    document.location.href = basePath + "page/ea/not_login.jsp";
                }
                subRule = (member.subRule.rules).split(",");
            },
            error : function cbf(data) {
                alert("数据获取失败！");
            }
        });
        var subjecturl = basePath
                + "ea/csbjects/sajax_ea_getListCsubejstsByPID.jspa?subjectsID=";
        subjectsNumber = "";
        subjectsName = "";
        $.ajax({
            url : encodeURI(subjecturl + "002&date="
                    + new Date().toLocaleString()),
            type : "get",
            async : true,
            dataType : "json",
            success : function cbf(data) {
                var member = eval("(" + data + ")");
                var nologin = member.nologin;
                if (nologin) {
                    document.location.href = basePath + "page/ea/not_login.jsp";
                }
                var subjectsList = member.subjectsList;
                $td = $("td.subjects");
                $select = "<option selected='selected' value = ''>--请选择--</option>";
                $td.children('select:eq(0)').append($select);
                for (var i = 0; i < subjectsList.length; i++) {
                    $op = $("<option />");
                    $op.attr("value", subjectsList[i].subjectsNumbers).attr(
                            "id", subjectsList[i].subjectsID)
                            .text(subjectsList[i].subjectsName);
                    $td.children('select:eq(0)').append($op);
                }
                $td.children('select:eq(0)').show();
                endnumber += parseInt(subRule[0]);
                if (subjectsNumber.substring(0, subRule[0]) != "") {
                    $td.children('select:eq(0)').attr("value",
                            subjectsNumber.substring(0, subRule[0]));
                    $td.children('select:eq(0)').trigger("change");
                }
                $("#selectsubjects").jqmShow();
            },
            error : function cbf(data) {
                alert("数据获取失败！");
            }
        });

    });
    $("#savesubjects").click(function() {
        if (subjectsName != "--请选择--") {
            $("#subjectsID", $(".receivesubjects")).attr("value",
                    subjectsnumber);
            $("#subjectsName", $(".receivesubjects")).attr("value",
                    subjectsName);
        } else {
            $("#subjectsID", $(".receivesubjects")).attr("value", "");
            $("#subjectsName", $(".receivesubjects")).attr("value", "");
        }
        $("#cashierTallyForm").find("td.receivesubjects")
                .removeClass("receivesubjects");
        notoken = 0;
        $("#selectsubjects").jqmHide();
    });

    // 科目管理
    $('td.subjects select[number]').change(function() {
        if (notoken) {
            alert("正在获取数据！请稍等");
            return;
        }
        notoken = 1;
        var num = $(this).attr("number");
        var number = parseInt(num) + 1;
        $td = $("td.subjects");
        $td.children('select:gt(' + num + ')').empty();
        var subjectsPID = $td.children('select:eq(' + num + ')')
                .children("option:selected").attr("id");
        subjectsnumber = $td.children('select:eq(' + num + ')')
                .children("option:selected").val();
        subjectsName = $td.children('select:eq(' + num + ')')
                .children("option:selected").text();
        if (subjectsnumber == "") {
            var numbers = parseInt(num) - 1;
            subjectsnumber = $td.children('select:eq(' + numbers + ')')
                    .children("option:selected").val();
            subjectsName = $td.children('select:eq(' + numbers + ')')
                    .children("option:selected").text();
            notoken = 0;
            return;
        }
        var subjecturl = basePath
                + "ea/csbjects/sajax_ea_getListCsubejstsByPID.jspa?subjectsID=";
        var subjectsNumber = "";
        $.ajax({
            url : encodeURI(subjecturl + subjectsPID + "&date="
                    + new Date().toLocaleString()),
            type : "get",
            async : true,
            dataType : "json",
            success : function cbf(data) {
                var member = eval("(" + data + ")");
                var nologin = member.nologin;
                if (nologin) {
                    document.location.href = basePath + "page/ea/not_login.jsp";
                }
                var subjectsList = member.subjectsList;
                notoken = 0;
                $td = $("td.subjects");
                $select = "<option selected='selected' value = ''>--请选择--</option>";
                $td.children('select:eq(' + number + ')').append($select);
                for (var i = 0; i < subjectsList.length; i++) {
                    $op = $("<option />");
                    $op.attr("value", subjectsList[i].subjectsNumbers).attr(
                            "id", subjectsList[i].subjectsID)
                            .text(subjectsList[i].subjectsName);
                    $td.children('select:eq(' + number + ')').append($op);
                }
                $td.children('select:eq(' + number + ')').show();
                if (subjectsNumber.length == endnumber
                        || subjectsNumber.length == 0) {
                    return;
                }
                endnumber += parseInt(subRule[number]);
                var startnumber = subRule[number - 1];
                if (subjectsNumber.substring(startnumber, endnumber) != "") {
                    $td.children('select:eq(' + number + ')').attr("value",
                            subjectsNumber.substring(0, endnumber));
                    $td.children('select:eq(' + number + ')').trigger("change");
                }

            },
            error : function cbf(data) {
                notoken = 0;
                alert("数据获取失败！");
            }
        });
    });
    var subRuleurl = basePath
            + "ea/csubjectsrule/sajax_n_ea_getCSubjectsRule.jspa?date="
            + new Date().toLocaleString();
    $.ajax({
                url : encodeURI(subRuleurl),
                type : "get",
                async : true,
                dataType : "json",
                success : function cbf(data) {
                    var member = eval("(" + data + ")");
                    var nologin = member.nologin;
                    if (nologin) {
                        document.location.href = basePath
                                + "page/ea/not_login.jsp";
                    }
                    subRule = (member.subRule.rules).split(",");
                },
                error : function cbf(data) {
                    alert("数据获取失败！");
                }
            });
    });
 