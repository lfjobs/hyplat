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
                if(level=='company'){
                        if($("#orgID").val()==companyid){
                            $("#orgID").attr("value","");
                        }
                };
                $("#SearchForm").attr(
                    "action",
                    basePath + "/ea/splitbill/ea_toSprina.jspa?level="+level );
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


         // 查询总公司下的所有子公司
         $(function() {
             var pid=companyid;
             var pname=companyname;
             if(level=="allCompany"){
                     pid=companyid;
                     pname=companyname;
                     var url = basePath
                             + "ea/company/sajax_n_ea_getCompanyList.jspa?date="
                             + new Date().toLocaleString();
                     $.ajax({
                                 url : encodeURI(url),
                                 type : "get",
                                 async : true,
                                 dataType : "json",
                                 success : function cbf(data) {
                                     var member = eval("(" + data + ")");
                                     var companylist = member.companylist;
                                     var data1 = new Array();
                                     data1[0] = {
                                         id : pid,
                                         pid : '-1',
                                         text :pname
                                     };
                                     for (var i = 0; i < companylist.length; i++) {
                                         data1[i + 1] = {
                                             id : companylist[i].companyID,
                                             pid : companylist[i].companyPID,
                                             text : companylist[i].companyName
                                         };
                                     }
                                     var ts3 = new TreeSelector($("#deptID")[0],
                                             data1, -1);
                                     ts3.createTree();
                                 },
                                 error : function cbf(data) {
                                     alert("数据获取失败！");
                                 }
                             });
             }
                     // 公司名称change事件
                     $("#deptID").change(function() {
                                 if ($(this).val() != '') {
                                     bmDept(this.value);
                                 } else {
                                     $("option", $("#orgID")).remove();
                                     $("#orgID")
                                             .html("<option value=''>请选择公司</option>");
                                 }
                             });
                     // 部门名称change事件
                     $("#orgID").change(function() {
                                 var temp = $("#orgID").val();
                                 if (temp.substring(0, 7) != 'company') {
                                         getPerson($("#deptID").val(), this.value);
                                 } else {
                                     $("option", $("select#person")).remove();
                                     $("#person")
                                             .html("<option value=''>请选择部门</option>");
                                 }
                             });
              });

    });

 // 根据公司ID和部门ID查询责任人
function getPerson(company, org) {
    $("option", $("select#person")).remove();
    var url = basePath
            + "ea/cashiersummary/sajax_ea_getStaffList.jspa?currentCompanyID="
            + company + "&currentOrgnizationID=" + org + "&date111="
            + new Date();
    $.ajax({
        url : encodeURI(url),
        type : "get",
        async : true,
        dataType : "json",
        success : function cbf(data) {
            var member = eval("(" + data + ")");
            var persons = member.stafflist;
            var str = "<option value=''>全部</option>";
            for (var i = 0; i < persons.length; i++) {
                var obj = persons[i];
                str += "<option value='" + obj[0] + "'>" + obj[1] + "</option>";
            }
            $("#person").html(str);
        }
    });
}

// 根据公司ID查询对应的部门列表
function bmDept(val) {
    $("option", $("#orgID")).remove();
    var url = basePath
            + "ea/responsibilitiessummary/sajax_n_ea_getoList.jspa?companyID="
            + val + "&date=" + new Date().toLocaleString();
    $.ajax({
                url : encodeURI(url),
                type : "get",
                async : true,
                dataType : "json",
                success : function cbf(data) {
                    /** **添加部门列表** */
                    var member = eval("(" + data + ")");
                    var oList = member.organizationlist;
                    var data2 = new Array();
                    data2[0] = {
                        id : val,
                        pid : '-1',
                        text : '全部'
                    };
                    for (var i = 0; i < oList.length; i++) {
                        data2[i + 1] = {
                            id : oList[i].organizationID,
                            pid : oList[i].organizationPID,
                            text : oList[i].organizationName
                        };
                    }                    
                    ts = new TreeSelector($("#orgID")[0], data2, -1);
                    ts.createTree();
                },
                error : function cbf(data) {
                    alert("数据获取失败！");
                }
            });
    $("option[value=" + this.value + "]", $("#orgID")).val("");
}