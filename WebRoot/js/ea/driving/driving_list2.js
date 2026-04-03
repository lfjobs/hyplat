$(document).ready(function(){
    $(".jqmWindow").jqm({
        modal: true,// 限制输入（鼠标点击，按键）的对话
        overlay: 20
        // 遮罩程度%
    }).jqmAddClose('.close');// 添加触发关闭的selector
    // .jqDrag('.drag');// 添加拖拽的selector
    $('.JQueryflexme').flexigrid({
        height: 140,
        width: 'auto',
        minwidth: 30,
        title: '教务 > ' + (docstatus == '01' ? '科一' : docstatus == '02' ? '科二' : docstatus == '03' ? '科三' : docstatus == '04' ? '科四' : '') + ' > 预约培训',
        minheight: 80,
        buttons: [{
            name: '预约管理',
            bclass: 'add',
            onpress: action
            // 当点击调用方法
        }, {
            separator: true
        }, {
            name: '修改教练',
            bclass: 'edit',
            onpress: action
            // 当点击调用方法
        }, {
            separator: true
        },  {
            name: '预约培训',
            bclass: 'edit',
            onpress: action
            // 当点击调用方法
        }, {
            separator: true
        }, {
            name: '查看',
            bclass: 'edit',
            onpress: action
            // �
        }, {
            separator: true
        }, {
            name: '查询',
            bclass: 'mysearch',
            onpress: action
            // �
        }, {
            separator: true
        }, {
            name: '设置每页显示条数',
            bclass: 'mysearch',
            onpress: action
            // 当点击调用方法
        }, {
            separator: true
        }]
    });
    function action(com, grid){
        switch (com) {
            case '预约管理':
                var str = "";
                var ytg = "";
                $("[name='chbox']").each(function(){
                    if ($(this).is(':checked')) {
                        str += $(this).val() + ",";
                        drivingprincipalid = $(this).val();
                        var studentstatus1 = $("tr#" + drivingprincipalid).find("span#studentstatus").text();
                        if ($.trim(studentstatus1) == "已报培训") {
                            ytg = "ytg";
                        }
                    }
                });
                if (ytg == "ytg") {
                    alert("以选中人员中包含已报培训人员!请重新选择");
                    return
                }
                if (str == "" || str.length == 0) {
                    alert('请选择');
                    return
                }
                if (confirm("确定继续？")) {
                    $f = $('#cstaffForm');
                    $f.find(':input#strs').val(str);
                    $("#cstaffForm").attr("target", "hidden").attr("action", basePath +
                    "ea/driving/ea_saveDrivingPeixun.jspa?pageNumber=" +
                    pNumber);
                    document.cstaffForm.submit.click();
                    token = 2;
                }
                break;
            case '修改教练':
                var str = "";
                $("[name='chbox']").each(function(){
                    if ($(this).is(':checked')) {
                        str += $(this).val() + ",";
                    }
                });
                if (str == "" || str.length == 0) {
                    alert('请选择');
                    return;
                }
                $("input#strs", $("#fencheForm")).attr("value", str);
                $("#jqModelSearchss").jqmShow();
                break;
            case '查看':
                var str = 0;
                $("[name='chbox']").each(function(){
                    if ($(this).is(':checked')) {
                        drivingprincipalid = $(this).val();
                        str++;
                    }
                });
                
                if (str != 1) {
                    alert('请选择具体一个往来个人!');
                    return;
                }
                document.cstaffForm.reset();
                $t = $("div#jqModel");
                $p = $("tr#" + drivingprincipalid);
                $p.find("span[id]").each(function(){
                      $t.find("input#" + this.id+","+"select#" + this.id).val($(this).text());
                });
                $("#jqModel").jqmShow();
                break;
            case '查询':
                $("#jqModelSearch").jqmShow();
                break;
            case '设置每页显示条数':
                var url = basePath +
                "ea/driving/ea_getDrivingList.jspa?docstatus=" +
                docstatus +
                "&studentstatus=" +
                studentstatus +
                "&title=" +
                title +
                "&search=" +
                search +
                "&conditions=" +
                conditions+"&extensionStaffCoach="+extensionStaffCoach+"&dtDrivingPrincipal.studentid="+studentID;
                numback(url);
                break;
            case '预约培训':
            	 var str = "";
                 var ytg = "";
                 $("[name='chbox']").each(function(){
                     if ($(this).is(':checked')) {
                         str += $(this).val() + ",";
                         drivingprincipalid = $(this).val();
                         var studentstatus1 = $("tr#" + drivingprincipalid).find("span#studentstatus").text();
                         if ($.trim(studentstatus1) == "已报培训") {
                             ytg = "ytg";
                         }
                     }
                 });
                 if (str == "" || str.length == 0) {
                     alert('请选择');
                     return
                 }
				 $("#daoRu1").attr("src",basePath+'/ea/appointment/ea_getListDtDrivingAppointmentRecord.jspa?dtDrivingAppointmentRecord.drivingprincipalid='+drivingprincipalid+"&dtDrivingAppointmentRecord.docstatus="+docstatus);
			 	 $("div#bankJqm1").show();
            	break;   
        }
    }
    // 复选框选中
    $(".chx").live("click", function(event){
        var b = $(this).attr("checked");
        $(this).attr("checked", !b);
    });
    $(".JQueryflexme tr[id]").click(function(){
        var d = $("input.chx", $(this)).attr("checked");
        $("input.chx", $(this)).attr("checked", !d);
    });
    $(".JQueryflexme tr[id]").dblclick(function(){
    	staffID=$(this).find("span#studentid").text();
        var url =basePath+ "ea/enroll/ea_getHumanResource.jspa?showType=edit"+"&cstaff.staffID="+staffID+"&docstatus="+docstatus+"&studentstatus="+studentstatus+"&istrues="+istrues+"&title="+title;
		window.open(encodeURI(url) , '','scrollbars=yes,resizable=yes,channelmode');
    });		
    $("#tosearch").click(function(){ // 查询
    	$(".ckTextLength", $("table#cataffSearchTable")).trigger("blur");
		if ($("#cataffSearchTable .error").length) {
			alert("请按提示填写查询条件");
			return;
		}
        $("#SearchForm").attr("action", basePath +
        "ea/driving/ea_toSearch.jspa?pageNumber=" +
        pNumber +
        "&docstatus=" +
        docstatus +
        "&studentstatus=" +
        studentstatus +
        "&title=" +
        title+"&extensionStaffCoach="+extensionStaffCoach+"&dtDrivingPrincipal.studentid="+studentID);
        document.SearchForm.submit.click();
    });
    $("input.JQuerySubmit").click(function(){// 保存
        $(".put3").trigger("blur");
        if ($(".error").length) {
            alert("请填完所有必填项");
            return;
        }
        $("#fencheForm").attr("target", "hidden").attr("action", basePath +
        "ea/driving/ea_saveDrivingFenche.jspa?pageNumber=" +
        pNumber);
        document.fencheForm.submit.click();
        token = 2;
        
    });
    $(".close").click(function(){// 取消
        $("#jqModel").jqmHide();
        re_load();
    });
    $("#xuanze").click(function(){
        $("#checkform", $("#bankJqm")).attr("value", "fencheForm");
        $("#daoRu").attr("src", basePath + "ea/driving/ea_getCarInformationList.jspa?");
        $("#bankJqm").jqmShow();
    });
    $("#DaoRuFan").click(function(){// 返回
        $("#bankJqm").jqmHide();
    });
    $("#DaoRuFan1").click(function(){// 返回
        $("#bankJqm1").hide();
    });
    $("#DaoRuFanqd").click(function(){// 选择确定
        var checkform = $("#checkform", $("#bankJqm")).attr("value");
        var carID = window.frames["daoRu"].carID;
        if (carID == "") {
            alert("请选择");
            return;
        }
        var staffName = window.frames["daoRu"].$('tr#' + carID).find("span#staffName").text();
        var carsID = window.frames["daoRu"].$('tr#' + carID).find("span#carID").text();
        var carNum = window.frames["daoRu"].$('tr#' + carID).find("span#carNum").text();
        
        $("#coachname", $("#" + checkform)).attr("value", staffName).trigger("blur");
        $("#coachid", $("#" + checkform)).attr("value", carsID).trigger("blur");
        $("#carNumber", $("#" + checkform)).attr("value", carNum).trigger("blur");
        $("#daoRu").attr("src", "");
        $("#bankJqm").jqmHide();
    });
});
function re_load(){
    if (token) 
        document.location.href = basePath +
        "ea/driving/ea_getDrivingList.jspa?pageNumber=" +
        pNumber +
        "&docstatus=" +
        docstatus +
        "&studentstatus=" +
        studentstatus +
        "&title=" +
        title +
        "&search=" +
        search +
        "&conditions=" +
        conditions+"&extensionStaffCoach="+extensionStaffCoach+"&dtDrivingPrincipal.studentid="+studentID;
}
