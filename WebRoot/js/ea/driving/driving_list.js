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
        title: '教务 > 科一 > 预约培训',
        minheight: 80,
        buttons: [/*{
            name: '返回上级',
            bclass: 'prev',
            onpress: action
            // 当点击调用方法
        }, {
            separator: true
        },*/ {
            name: '删除',
            bclass: 'delete',
            onpress: action
            // 当点击调用方法
        }, {
            separator: true
        }, {
            name: '合格管理',
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
        },{
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
        }, {
            name: '切换体检表过期预警',
            bclass: 'mysearch',
            onpress: action
            // 当点击调用方法
        }, {
            separator: true
        }]
    });
    function action(com, grid){
        switch (com) {
            /*case '返回上级':
                document.location.href = basePath + "page/ea/main/navigation/driving_management.jsp";
                break;*/
            case '合格管理':
                if (drivingprincipalid == "") {
                    alert('请选择!');
                    return;
                }
                document.cstaffForm.reset();
                $t = $("div#jqModel");
                $p = $("tr#" + drivingprincipalid);
                $p.find("span[id]").each(function(){
                    $t.find("input#" + this.id+","+"select#" + this.id).val($(this).text());
                });
                $("select#istrues").trigger("change");
                $("#jqModel").jqmShow();
                break;
            case '删除':
                if (drivingprincipalid == "") {
                    alert('请选择！');
                    return
                }
                $f = $('#cstaffForm');
                $f.find('input#drivingprincipalid').val(drivingprincipalid);
                if (confirm("确定继续？")) {
                    $("#cstaffForm").attr("target", "hidden").attr("action", basePath +
                    "ea/driving/ea_delDriving.jspa?pageNumber=" +
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
                    conditions+"&extensionStaffCoach="+extensionStaffCoach+"&dtDrivingPrincipal.studentid="+studentID);
                    document.cstaffForm.submit.click();
                    $("tr#" + drivingprincipalid).remove();
                    drivingprincipalid = "";
                    token = 11;
                }
                break;
            case '查询':
                $("#jqModelSearch").jqmShow();
                break;
            case '设置每页显示条数':
                var url = basePath +
                "ea/driving/ea_getDrivingList.jspa?dateYuJing=" +
                dateYuJing +
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
                numback(url);
                break;
            case '切换体检表过期预警':
                document.location.href = basePath +
                "ea/driving/ea_getDrivingList.jspa?pageNumber=" +
                pNumber +
                "&dateYuJing=" +
                (dateYuJing == "dateYuJing" ? "" : "dateYuJing") +
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
                break;
             case '预约培训':
            	 if (drivingprincipalid == "") {
                    alert('请选择!');
                    return;
             	   }
				 $("#daoRu").attr("src",basePath+'/ea/appointment/ea_getListDtDrivingAppointmentRecord.jspa?dtDrivingAppointmentRecord.drivingprincipalid='+drivingprincipalid+"&dtDrivingAppointmentRecord.docstatus="+docstatus);
			 	 $("div#bankJqm").show();
             	break;
        }
    }
    //动态显示
//    $("select#istrues").change(function(){
//        if ($(this).val() == "不合格") {
//            $("tr#isHege").fadeIn("slow");
//            $("tr.islib").fadeOut("slow");
//            $("tr.islib").fadeOut("slow");
//            $("input#reason").addClass("put3");
//        }
//        else{
//			$("tr#isHege").fadeOut("slow");
//			$("tr.islib").fadeIn("slow");
//            $("tr.islib").fadeIn("slow");
//            $("tr#isHege").find("span[class]").remove();
//            $("input#reason").removeAttr("class");
//            $("input#reason").val("");
//        }
//    })
    
    
    $("select#istrues").change(function(){
        if ($(this).val() == "不合格") {
            $("tr#isHege").show();
            $("tr.islib").hide();
            $("input#reason").addClass("put3");
			$("input#chipidss").removeClass("put3");
			$("input#startDate").removeClass("put3");
			$("input#endDate").removeClass("put3");
			$("tr.islib").find("span[class]").remove();
			$("input#chipidss").val("");
			$("input#barcodes").val("");
        }else{
			$("tr#isHege").hide();
			$("tr.islib").show();
            $("tr#isHege").find("span[class]").remove();
            $("input#reason").removeClass("put3");
            $("input#reason").val("");
			$("input#chipidss").addClass("put3");
          	$("input#startDate").addClass("put3");
			$("input#endDate").addClass("put3");
        }
    });
    
    $("select.isShowDangAn").change(function(){
		 if ($(this).val() == "否") {
            $("tr.islib[id!='istrues2']").hide();
			$("input#chipidss").removeClass("put3");
			$("input#startDate").removeClass("put3");
			$("input#endDate").removeClass("put3");
			$("tr.islib").find("span[class]").remove();
			$("input#chipidss").val("");
			$("input#barcodes").val("");
        }else{
			$("tr.islib[id!='istrues2']").show();
            $("tr#isHege").find("span[class]").remove();
			$("input#chipidss").addClass("put3");
          	$("input#startDate").addClass("put3");
			$("input#endDate").addClass("put3");
        }
    });
    
    //----------------选择在职人员操作------------//
	$("#DaoRuFan").click(function(){// 返回
       $("#bankJqm").hide();
	}); 
    
    // 点击选中
    $(".JQueryflexme tr[id]").click(function(){
        $("input.JQuerypersonvalue", $(this)).attr("checked", "checked");
        drivingprincipalid = this.id;
    });
    // 点击选中
    /**$(".JQueryflexme tr[id]").dblclick(function(){
        $("input.JQuerypersonvalue", $(this)).attr("checked", "checked");
        drivingprincipalid = this.id;
        staffID=$(this).find("span#studentid").text();
        istrues=$(this).find("span#istrues").text();
        reason=$(this).find("span#reason").text();
        var url =basePath+ "ea/enroll/ea_getHumanResource.jspa?showType=edit"+"&cstaff.staffID="+staffID+"&apply=apply&docstatus="+docstatus+"&studentstatus="+studentstatus+"&istrues="+istrues+"&reason="+reason;
		window.open(encodeURI(url) , '','scrollbars=yes,resizable=yes,channelmode');
        
    });*/
    $("input.JQuerySubmit").click(function(){// 保存
        $(".put3").trigger("blur");
        if ($(".error").length) {
            alert("请填完所有必填项");
            return;
        }
        $("#cstaffForm").attr("target", "hidden").attr("action", basePath +
        "ea/driving/ea_saveDriving.jspa?pageNumber=" +
        pNumber);
        document.cstaffForm.submit.click();
        token = 2;
        
        
    });
    $("#tosearch").click(function(){ // 查询
        $("#SearchForm").attr("action", basePath +
        "ea/driving/ea_toSearch.jspa?pageNumber=" +
        pNumber +
        "&dateYuJing=" +
        dateYuJing +
        "&docstatus=" +
        docstatus +
        "&studentstatus=" +
        studentstatus +
        "&title=" +
        title+"&extensionStaffCoach="+extensionStaffCoach+"&dtDrivingPrincipal.studentid="+studentID);
        document.SearchForm.submit.click();
    });
    $(".close").click(function(){// 取消
        $("#jqModel").jqmHide();
        re_load();
    });
    /**	$("#xuanze").click(function(){
     $("#checkform",$("#bankJqm")).attr("value","stafftable2");
     $("#daoRu").attr("src",basePath+"ea/cosincumbent/ea_getStaffForCashier.jspa?jiaowu=jiaowu");
     $("#bankJqm").jqmShow();
     })
     $("#DaoRuFan").click(function(){// 返回
     $("#bankJqm").jqmHide();
     });
     $("#DaoRuFanqd").click(function(){// 选择确定
     var checkform =$("#checkform",$("#bankJqm")).attr("value");
     var childopertionID = window.frames["daoRu"].opertionID;
     if(childopertionID == ""){
     alert("请选择")
     return;
     }
     var staffName = window.frames["daoRu"].$('tr#'+childopertionID).find("span#staffName").text();
     var sex =window.frames["daoRu"].$('tr#'+childopertionID).find("span#sex").text();
     var reference =window.frames["daoRu"].$('tr#'+childopertionID).find("span#reference").text();
     var staffIdentityCard =window.frames["daoRu"].$('tr#'+childopertionID).find("span#staffIdentityCard").text();
     var staffID =window.frames["daoRu"].$('tr#'+childopertionID).find("span#staffID").text();
     
     $("#studentname",$("#"+checkform)).attr("value",staffName).trigger("blur");
     $("#studentsex",$("#"+checkform)).attr("value",sex).trigger("blur");
     $("#studentphone",$("#"+checkform)).attr("value",reference).trigger("blur");
     $("#studentcard",$("#"+checkform)).attr("value",staffIdentityCard).trigger("blur");
     $("#studentid",$("#"+checkform)).attr("value",staffID).trigger("blur");
     
     $("#daoRu").attr("src","");
     $("#bankJqm").jqmHide();
     });   	**/

    //读取芯片号
    	$(".readchipid").click(function() {

		$("#cstaffForm #loadcab")
				.attr(
						"src",
						basePath
								+ "page/ea/main/office_ea/archives/loadActiveX.html?code="
								+ Math.random());

	});
	
$("form .chip").bind("blur", function() {
		$input = $(this);
		$parent = $input.parent();
		var inputValue = $input.attr("value");
		$parent.find(".error").remove();
		$parent.find(".corect").remove();
		$parent.find(".tooltip").remove();
		if ($input.is(".chip")) {
			if (inputValue != "") {
					if (!checkChip(inputValue)) {
						$parent
								.append("<span class=\"error\"><a class=\"tex\">芯片号已使用</a></span>");
						return;
					} else {
						$parent
								.append("<span class=\"corect\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>");
						return;
					}
			} else {
				$parent
						.append("<span class=\"error\"><a class=\"tex\">\u4e0d\u80fd\u4e3a\u7a7a</a></span>");
			}
		}
	}); 
});
// 判断芯片号是否重复
function checkChip(chipid) {
	if ($("#postEnterForm #oldchipids").val() == chipid) {
		return true;
	}
	var bool = '';
	var url = basePath + "ea/archive/sajax_ea_IsChipRepeat.jspa?date="
			+ new Date();
	$.ajax({
				url : url,
				type : "get",
				async : false,
				dataType : "json",
				data : {
					chipid : chipid
				},
				success : function(data) {
					var member = eval("(" + data + ")");
					var result = member.result;
					if (result == "fail") {// 重复
						bool = false;
					} else {
						bool = true;// 不重复
					}

				},
				error : function(data) {
					alert("读取数据失败");
				} 
			}); 
	return bool;
}
function re_load(){
    if (token) 
        document.location.href = basePath +
        "ea/driving/ea_getDrivingList.jspa?pageNumber=" +
        pNumber +
        "&dateYuJing=" +
        dateYuJing +
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

/**$(function() {

 // /////////////////////////////////////

 var treeName = parent.frames["leftFrame"].tree.getItemText(treeID);

 var treePID = parent.frames["leftFrame"].tree.getParentId(treeID);

 var treePName = parent.frames["leftFrame"].tree

 .getItemText(treePID);

 var url = basePath

 + "ea/responsibilities/sajax_n_ea_getoList.jspa?date="

 + new Date().toLocaleString();

 $.ajax({

 url : encodeURI(url),

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

 var oList = member.organizationlist;

 var data1 = new Array();

 data1[0] = {

 id : treeID,

 pid : '-1',

 text : treeName

 };

 for (var i = 0; i < oList.length; i++) {

 data1[i + 1] = {

 id : oList[i].organizationID,

 pid : oList[i].organizationPID,

 text : oList[i].organizationName

 };

 }

 $t = $("table#stafftable2");

 var ts3 = new TreeSelector($t

 .find("select#organizationID")[0],

 data1, -1);

 ts3.createTree();

 },

 error : function cbf(data) {

 alert("数据获取失败！")

 }

 });

 });**/

