$(document).ready(function() {
    // .jqDrag('.drag');//
    var html =  $(".query").html();
    $(".query").remove();
    $('.JQueryflexme').flexigrid({
        height : 350,
        width : 'auto',
        minwidth : 30,
        title : html,
        minheight : 80,
        buttons : [{
            name : '添加',
            bclass : 'add',
            onpress : action
            //
        }, {
            separator : true
        }, {
            name : '修改',
            bclass : 'edit',
            onpress : action
            //
        }, {
            separator : true
        }, {
            name : '删除',
            bclass : 'delete',
            onpress : action
            //
        }, {
            separator : true
        },{
            name : '设置每页显示条数',
            bclass : 'mysearch',
            onpress : action
            // 当点击调用方法
        }, {
            separator : true
        }]
    });
    function action(com, grid) {
        switch (com) {
            case '添加' :
                standard();
                break;
            case '修改' :
                if (ercId == "") {
                    alert('请选择!');
                    return;
                }
                updateStandard(ercId);
                break;
            case '删除' :
                if (ercId == "") {
                    alert('请选择');
                    return
                }
                if (confirm("确定删除？")) {
                    delstandard(ercId);
                }
                break;
            case '设置每页显示条数' :
                var url = basePath
                    + "ea/carmanage/ea_chargeList.jspa?search="+"&exRoom.erNumber="+$("#erNumber").val();
                numback(url);
                break;
        }
        //隐藏添加表单
        $(".tit").find("img").click(function(){
            $(".con")[0].reset();
            $(".exRoom").empty();
            $(".staff").empty();
            $("#fees_").hide();
            $(".erName").val("");
            $(".erId").val("");
            $(".staffID").val("");
            $(".staffName").val("");
            $(".CompanyID").val("");
            $(".ercID").val();

        })
        $(".exRoom").change(function(){
            var $this = $(this).find("option:selected");
            $(".erId").val($this.attr("date-erId"));
            $(".erName").val($this.attr("date-erName"));
        })
        $(".staff").change(function(){
            var $this = $(this).find("option:selected");
            $(".staffID").val($this.attr("date-staffID"));
            $(".staffName").val($this.attr("date-staffName"));
            $(".CompanyID").val($this.attr("date-CompanyID"));
        })
    }
    //删除收费标准
    function delstandard(obj){
        var url = basePath + "ea/carmanage/sajax_ea_delErCharge.jspa";
        $.ajax({
            url : url,
            type : "post",
            async : false,
            dataType : "json",
            data :{
                "erCharge.ercID":obj
            },
            success : function(data) {
                var standard = eval("(" + data + ")");
                if(standard.boolean){
                    window.location.reload();
                }else{
                    alert("删除失败");
                }
            },
            error : function(data) {
                alert("删除失败");
            }
        });
    }

    //添加公司员工以及场地编号
    function standard(){
        $("#imgPoint").hide();
        var url = basePath + "ea/carmanage/sajax_ea_erNumberPersonnel.jspa";
        $.ajax({
            url : url,
            type : "post",
            async : true,
            dataType : "json",
            success : function(data) {
                var standard = eval("(" + data + ")");
                var nologin = standard.nologin;
                if(nologin){
                    document.location.href = basePath + "/page/ea/not_login.jsp";
                }
                var number = standard.number;//场地编号
                var list = standard.list;//公司员工
                var dlList=standard.dlList;//代理类别查询
                $("#imgSdf").attr("src",basePath+"images/WFJClient/PersonalJoining/shangchuan_07.png");
                if (standard!=null) {
                    $(".chargeNumber").attr({ readonly: 'true' });
                    $(".erName").attr({ readonly: 'true' });

                    $(".chargeNumber").val("自动生成");
                    var a = [];
                    $(".exRoom").html("");
                    $(number).each(function(i, dom) {
                        a.push("<option date-erId='"+this[1]+"' date-erName='"+this[2]+"'>"+this[0]+"</option>");
                        if(i==0){
                            $(".erId").val(this[1]);
                            $(".erName").val(this[2]);
                        }
                    });
                    $(".exRoom").append(a.join(""));

                    var b = [];
                    $(".staff").html("");
                    $(list).each(function(i, dom) {
                        b.push("<option date-staffID='"+this[0]+"' date-staffName='"+this[1]+"' date-companyID='"+this[2]+"'>"+this[1]+"</option>");
                        if(i==0){
                            $(".staffID").val(this[0]);
                            $(".staffName").val(this[1]);
                            $(".CompanyID").val(this[2]);
                        }
                    });
                    $(".staff").append(b.join(""));

                    var yj = [];
                    $(".yj").html("");
                    $(dlList).each(function(i, dom) {
                        yj.push("<div class='mil'>");
                        yj.push("<p >"+this.goodsName+"佣金(元)："+"</p>");
                        yj.push("<div>");
                        yj.push("<input  id='"+this.ppID+"' class='ver dlyj' type='text' name='pssMap["+i+"].amount'/>");
                        yj.push("<input type='hidden' value='"+this.ppID+"' name='pssMap["+i+"].typePpid'/>");
                        yj.push("</div>");
                        yj.push("</div>");
                    });
                    $(".yj").append(yj.join(""));
                    $("#fees_").show();
                }
            },
            error : function(data) {
                alert("获取失败");
            }
        });
    }
    //修改收费标准,公司员工以及场地编号
    function updateStandard(obj){
        var url = basePath + "ea/carmanage/sajax_ea_testStandard.jspa";
        $.ajax({
            url : url,
            type : "post",
            async : true,
            dataType : "json",
            data :{
                "erCharge.ercID":obj,
                "exRoom.erNumber":erNumber
            },
            success : function(data) {
                var standard = eval("(" + data + ")");
                var nologin = standard.nologin;
                if(nologin){
                    document.location.href = basePath + "/page/ea/not_login.jsp";
                }
                if (standard!=null) {
                    var erCharge = standard.erCharge;//收费详情
                    var number = standard.number;//场地编号
                    var ppk = standard.ppk;//产品
                    var psp = standard.psp;//佣金
                    var list = standard.list;//公司员工
                    var prtset=standard.prtset;//佣金
                    var dlList=standard.dlList;//佣金类别
                    var brokerage=standard.brokerage;
                    var arrlist=standard.arrlist;
                    $(".chargeNumber").attr({ readonly: 'true' });
                    $(".erName").attr({ readonly: 'true' });
                    $(".ercID").val(erCharge.ercID);
                    $(".price").val(psp.rePrice);
                    $(".chargeNumber").val(erCharge.chargeNumber);
                    $(".goodsID").val(erCharge.goodsID);
                    $(".dlyj").val(brokerage[0]);
                    $(arrlist).each(function (i,dom) {
                        $("#imgSdf").attr("src",basePath+this.imgurl);
                    })
                    var a = [];
                    $(".exRoom").html("");
                    $(number).each(function(i, dom) {
                        a.push("<option onclick='assignment1(this)' date-erId='"+this[1]+"' date-erName='"+this[2]+"'>"+this[0]+"</option>");
                        if(i==0){
                            $(".erId").val(this[1]);
                            $(".erName").val(this[2]);
                        }
                    });
                    $(".exRoom").append(a.join(""));
                    var b = [];
                    $(".staff").html("");
                    $(list).each(function(i, dom) {
                        b.push("<option onclick='assignment2(this)' date-staffID='"+this[0]+"' date-staffName='"+this[1]+"' date-companyID='"+this[2]+"'>"+this[1]+"</option>");
                        if(i==0){

                            $(".CompanyID").val(this[2]);
                        }
                    });

                    $(".staff").append(b.join(""));
                    $(".staff").val(erCharge.staffName);
                    $(".staffID").val(erCharge.staffID);
                    $(".staffName").val(erCharge.staffName);

                    var yj = [];
                    $(".yj").html("");
                    $(dlList).each(function(i, dom) {
                        yj.push("<div class='mil'>");
                        yj.push("<p >"+this[2]+"佣金(元):"+"</p>");
                        yj.push("<div>");
                        if(prtset!=null){
                            $(prtset).each(function (j,prt) {
                                if(dom[0]==prt[3]){
                                    yj.push("<input  id='"+prt[5]+"' class='ver dlyj' type='text' name='pssMap["+j+"].amount' value='"+prt[4]+"'/>");
                                    yj.push("<input type='hidden' value='"+prt[3]+"' name='pssMap["+j+"].typePpid'/>");
                                    yj.push("<input type='hidden' value='"+prt[7]+"' name='pssMap["+j+"].suskey'/>");
                                    yj.push("<input type='hidden' value='"+prt[6]+"' name='pssMap["+j+"].susid'/>");
                                }
                            });}
                        else {
                            yj.push("<input  id='"+this[0]+"' class='ver dlyj' type='text' name='pssMap["+i+"].amount'/>");
                            yj.push("<input type='hidden' value='"+this[0]+"' name='pssMap["+i+"].typePpid'/>");
                        }
                        yj.push("</div>");
                        yj.push("</div>");
                    });
                    $(".yj").append(yj.join(""));
                    $("#fees_").show();
                }
            },
            error : function(data) {
                alert("获取失败");
            }
        });
    }
    //添加修改收费标准
    $(".sub").click(function(){
        var fileInput = $('#sdfFile').get(0).files[0];
        if(!fileInput){
            if($("#imgSdf").attr("src").indexOf("shangchuan_07.png")!=-1) {
                $("#imgPoint").show();
                return;
            }

        }else{
            $("#imgPoint").hide();
        }

        $(".posnum").trigger("blur");
        if($(".error").length>0){
            return;
        }
        var exRoom = $(".exRoom").val();
        if(exRoom==""){
            return "您未设置场地,请设置";
        }
        var url = basePath + "ea/carmanage/sajax_ea_addOrUpdateExRoomCharge.jspa";
        var formData=new FormData($("#launchForm")[0]);
        $.ajax({
            url : url,
            type : "post",
            data:formData,// 你的formid
            cache: false,
            contentType: false,
            processData: false,
            success : function(data) {
                var standard = eval("(" + data + ")");
                if(standard.boolean){
                    window.location.reload();
                }else{
                    alert("添加失败");
                }
            },
            error : function(data) {
                alert("添加失败");
            }
        });
    })



    $(".JQueryflexme tr[id]").click(function() {
        $("input.JQuerypersonvalue", $(this))
            .attr("checked", "checked");
        ercId = this.id;
        erNumber = $(this).find("#RerNumber").text();
    });
    $(".JQueryflexme tr[id]").dblclick(function() {
        action('修改');
    });

    //like查询场地编号
    $(".input-button").click(function(){
        ppageNumber = 0;
        var url = basePath
            + "ea/carmanage/ea_chargeList.jspa?exRoom.erNumber="+$("#erNumber").val();
        document.location.href = url;
    })
    //启用标准
    $(".start").click(function(){
        var obj = $(this).parents("tr").attr("id")
        var url = basePath + "ea/carmanage/sajax_ea_examIsEnabled.jspa";
        $.ajax({
            url : url,
            type : "post",
            async : true,
            dataType : "json",
            data :{
                "erCharge.ercID":obj
            },
            success : function(data) {
                var standard = eval("(" + data + ")");
                if(standard.boolean){
                    window.location.reload();
                }else{
                    alert("启用失败");
                }
            },
            error : function(data) {
                alert("启用失败");
            }
        });
    })

});
