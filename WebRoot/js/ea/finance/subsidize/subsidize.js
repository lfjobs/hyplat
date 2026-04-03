
$(document).ready(function() {
    $(".jqmWindow").jqm({
        modal : true,// 限制输入（鼠标点击，按键）的对话
        overlay : 20
        // 遮罩程度%
    }).jqmAddClose('.close');// 添加触发关闭的selector

    var name;

    $('.address').flexigrid({
        height : 445,
        width : 'auto',
        minwidth : 30,
        title : '消费补助设置',
        minheight : 80,
        buttons : [{name : '发红包',bclass : 'add',onpress : action},{separator : true},
            {name : '添加',bclass : 'add',onpress : action},{separator : true},
            {name : '修改',bclass : 'edit',onpress : action},{separator : true},
            {name : '删除',bclass : 'delete',onpress : action},{separator : true},
            {name : '打印预览',bclass : 'printer',onpress : action},{separator : true},
            {name : '设置每页显示条数',bclass : 'mysearch',onpress : action},{separator : true}]
    });

    function action(com, grid) {
        switch (com) {
            case '发红包':
                if (ssid == "") {
                    alert("请选择！");
                    return;
                }
                $("#ssid2").val(ssid);
                $("#gtid2").val($("#"+ssid).find(".gtid").text());
                $("#jqModelfhb").jqmShow();
                break;
            case '添加':
                $.ajax({url:basePath+"/ea/setsubsidize/sajax_ea_ajaxTypeSubsidize.jspa?",
                    type:"get",
                    dataType:"json",
                    async:false,
                    success:function(data){
                        var mem = eval("("+data+")");
                        var typeList = mem.typeList;
                        if(typeList!=null){
                            var selecthtml="<select id='typeSubsidize' name='setSubsidize.stid'>";
                            selecthtml+="<option value=''>请选择</option>";
                            for (var i=0;i<typeList.length;i++){
                                selecthtml+="<option value='"+typeList[i].stid+"'>"+typeList[i].stname+"</option>";
                            }
                            selecthtml+="</select>";
                            $("#stype").prepend(selecthtml);
                        }
                    }
                });
                $("#jqModeladd").jqmShow();
                break;
            case '修改':
                if (ssid == "") {
                    alert("请选择！");
                    return;
                }
                $("#sskey").val($("#"+ssid).find(".sskey").val());
                $("#ssid").val(ssid);
                $("#gtid").val($("#"+ssid).find(".gtid").text());
                $("#totalPct").val($("#"+ssid).find(".totalPct").text());
                $("#flPct").val($("#"+ssid).find(".flPct").text());
                $("#slPct").val($("#"+ssid).find(".slPct").text());
                $("#xfPct").val($("#"+ssid).find(".xfPct").text());
                $("#xbPct").val($("#"+ssid).find(".xbPct").text());
                $("#fsPct").val($("#"+ssid).find(".fsPct").text());
                //$("#totalPct").val($("#"+ssid).find(".totalPct").text());

                $.ajax({url:basePath+"/ea/setsubsidize/sajax_ea_ajaxTypeSubsidize.jspa?",
                    type:"get",
                    dataType:"json",
                    async:false,
                    success:function(data){
                        var mem = eval("("+data+")");
                        var typeList = mem.typeList;
                        if(typeList!=null){
                            var selecthtml="<select id='typeSubsidize' name='setSubsidize.stid'>";
                            selecthtml+="<option value=''>请选择</option>";
                            for (var i=0;i<typeList.length;i++){
                                if($("#"+ssid).find(".stName").text()==typeList[i].stname){
                                    selecthtml+="<option value='"+typeList[i].stid+"' selected>"+typeList[i].stname+"</option>";
                                }else{
                                    selecthtml+="<option value='"+typeList[i].stid+"'>"+typeList[i].stname+"</option>";
                                }
                            }
                            selecthtml+="</select>";
                            $("#stype").html(selecthtml);
                        }
                    }
                });
                $("#jqModeladd").jqmShow();
                break;
            case '删除':
                if (ssid == "") {
                    alert("请选择！");
                    return;
                }
                $.ajax({url:basePath+"/ea/setsubsidize/sajax_ea_delSetSubsidize.jspa?",
                    type:"get",
                    dataType:"json",
                    async:false,
                    data:{"ssid":ssid},
                    success:function(data){
                        var mem = eval("("+data+")");
                        var flag = mem.flag;
                        if(flag){
                            $("#"+ssid).remove();
                            alert("删除成功");
                        }
                    }
                });
                break;
            case '设置每页显示条数':
                numback(s);
                break;
        }
    }
    $("div.bDiv",$("div.flexigrid")).css("height","350px");
    $("#tosave").click(function(){
            var flag=true;
            $(".notNull").each(function () {
                if($(this).val().trim()==null||$(this).val().trim()==""){
                    alert("请详细填写消费补助信息各项数据");
                    flag=false;
                    return false;
                }
            });
            if(flag){
                if($("#typeSubsidize").val()==null||$("#typeSubsidize").val()==""||$("#typeSubsidize").val()=="请选择"){
                    alert("请详细填写消费补助信息各项数据");
                    flag=false;
                }
            }
            if(flag){
                $(".bl").each(function(){
                    var tval=$(this).val();
                    var reg=/^[1-9]\d*$/;
                    if(reg.test(tval)&&tval<100){
                    }else{
                        alert("请输入1-100的正整数");
                        flag=false;
                        return false;
                    }
                });
            }

            if(flag){
                var a=Number($("#flPct").val())+Number($("#slPct").val());
                if(a>100){
                    alert("第一次赠送比例和第二次赠送比例的和不可以超过100%");
                    flag=false;
                    return false;
                }
            }
            if(flag){
                var a=Number($("#flPct").val())+Number($("#slPct").val());
                if(a>100){
                    alert("第一次赠送比例和第二次赠送比例的和不可以超过100%");
                    flag=false;
                    return false;
                }
            }
            if(flag){
                if($("#sskey").val()!=null&&$("#sskey").val()!=""){
                    $("#saveForm").attr("target", "hidden").attr("action",basePath +"/ea/setsubsidize/ea_UpdateSub.jspa?");
                    token=2;
                    document.saveForm.submit.click();
                }else{
                    var gtid=$("#gtid").val();
                    $.ajax({
                        url:basePath+"/ea/setsubsidize/sajax_ea_getsstype.jspa?",
                        type:"get",
                        dataType:"json",
                        async:false,
                        data:{"gtid":gtid},
                        success:function(data){
                            var mem = eval("("+data+")");
                            var count = mem.count;
                            if(count>0){
                                alert("这个行业已经添加过了，不可以重复添加");
                            }else {
                                $("#saveForm").attr("target", "hidden").attr("action",basePath +"/ea/setsubsidize/ea_UpdateSub.jspa?");
                                token=2;
                                document.saveForm.submit.click();
                            }
                        }
                    });
                }
            }
    });

    $("#tosave2").click(function(){
        var zh=$("#zh").val();
        var mm=$("#mm").val();
        $.ajax({
            url:basePath+"/ea/setsubsidize/sajax_ea_ajaxsdf.jspa?",
            type:"get",
            dataType:"json",
            async:false,
            data:{"zh":zh,
                "mm":mm
            },
            success:function(data){
                var mem = eval("("+data+")");
                var falg = mem.falg;
                var sccid=mem.sccid;
                if(falg=="核对正确"){
                    $("#sccid").val(sccid);
                    $("#fhbForm").attr("target", "hidden").attr("action",basePath +"/ea/setsubsidize/ea_laskdf.jspa?");
                    token=2;
                    document.fhbForm.submit.click();
                }else {
                    alert(falg);
                }
            }
        });
    });

    $("#tosearch").click(function() {
        var a="";
        $("#SearchForm").attr("action",s +"&vuvtype=edit");
        document.SearchForm.submit.click();
    });

    //获取id
    $(".address tr[id]").click(
        function() {
            ssid = this.id;
            $("input.JQuerypersonvalue", $(this)).attr("checked","checked");
    });

    /*$(".bl").blur(function(){
        var tval=$(this).val();
        var reg=/^[1-9]\d*$/;
        if(reg.test(tval)&&tval<100){
            return;
        }else{
            alert("请输入1-100的正整数");
        }
    });*/

    //查询消费补助类型
    /*$.ajax({url:basePath+"/ea/setsubsidize/sajax_ea_ajaxTypeSubsidize.jspa?",
        type:"get",
        dataType:"json",
        async:false,
        success:function(data){
            var mem = eval("("+data+")");
            var typeList = mem.typeList;
            if(typeList!=null){
                var selecthtml="<select id='typeSubsidize' name='setSubsidize.stid'>";
                selecthtml+="<option value=''>请选择</option>";
                for (var i=0;i<typeList.length;i++){
                    selecthtml+="<option value='"+typeList[i].stid+"'>"+typeList[i].stname+"</option>";
                }
                selecthtml+="</select>";
                $("#stype").prepend(selecthtml);
            }
        }
    });*/

    // 行业
    $(document).ready(function() {
        tree1 = new dhtmlXTreeObject("industryTree", "100%", "100%", 0);
        tree1.enableDragAndDrop(false);
        tree1.enableHighlighting(1);
        tree1.enableCheckBoxes(0);
        tree1.enableThreeStateCheckboxes(false);
        tree1.setSkin(basePath + 'js/tree/dhx_skyblue');
        tree1.setImagePath(basePath + "js/tree/codebase/imgs/");
        tree1.loadXML(basePath + "js/tree/common/tree_b.xml");
        tree1.insertNewChild("0", "scode20170714cnjcrn5jm20000000067", "行业分类",
            0, 0, 0, 0);

        getIndustryItem(3);
        tree1.setOnClickHandler(function() {

            getIndustryItem(1);

        });

        tree1.setOnDblClickHandler(function() {
            getIndustryItem(2);

        });

    });

});

function xz(type){
    $(".jqmWindow").find('.close').click();
    pop(type);
}

    // 获取行业
    function getIndustryItem(type) {
        if (type == 3) {
            treeid1 = "scode20170714cnjcrn5jm20000000067";
        } else {
            treeid1 = tree1.getSelectedItemId();
        }
        treename1 = tree1.getItemText(treeid1);
        tree1.deleteChildItems(treeid1);
        var getListCCodeurl = basePath
            + "/ea/setsubsidize/sajax_ea_ajaxScode.jspa?codePid=" + treeid1
            + "&date=" + new Date().toLocaleString();
        $.ajax({
            url: encodeURI(getListCCodeurl),
            type: "get",
            async: true,
            dataType: "json",
            success: function cbf(data) {
                var member = eval("(" + data + ")");
                var scodeList = member.scodeList;

                if (scodeList.length == 0) {
                    if (type == 2) {
                        $("#indID").val(treeid1);
                        $("#ind").val(treename1);
                        $("#industry").hide();

                        var a = new Array();
                        a.unshift(">");
                        a.unshift(treename1);
                        var parentid = treeid1;
                        var parentname = "";

                        while (true) {

                            parentid = tree1.getParentId(parentid);

                            if (parentid != "scode20170714cnjcrn5jm20000000067") {

                                parentname = tree1.getItemText(parentid);
                                a.unshift("/");
                                a.unshift(parentname);
                            } else {
                                var str = a.join("").substring(0,
                                    a.join("").length - 1);
                                $("#gtid").val(str);
                                $("#jqModeladd").jqmShow();
                                break;
                            }
                        }
                    }
                    return;
                }
                for (var i = 0; i < scodeList.length; i++) {

                    tree1.insertNewChild(treeid1, scodeList[i].codeID,
                        /*scodeList[i].codeSn +*/ scodeList[i].codeValue, 0, 0, 0, 0);

                }
            },
            error: function cbf(data) {
                alert("数据获取失败！");
            }
        });
    }
function re_load() {
        window.location.href =  basePath+"/ea/setsubsidize/ea_listSub.jspa?";
}
