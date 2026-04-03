$(document).ready(function() {
	if (module == "cg" || module == "dg" || module == "pg" || module == "jg") {
		$(".thnum span").text("项目编号");
		$(".thtitle span").text("项目名称");
		$(".intitle span").text("项目名称");

	}
	if (module == "contract") {
		$(".trtip").show();
	} else {
		$(".trtip").hide();

	}


     //集团内部和外部公司
	$(".source").click(function(){
         if($(this).val()=="in"){
             $("#SearchTable2").show();
             $("#SearchTable").hide();
	     }else{
             $("#SearchTable2").hide();
             $("#SearchTable").show();
		 }


	})



    $(document).on("click", "#SearchTable .ul1 li", function () {
    	var companyID = $(this).attr("id");
        var companyName = $(this).text();
        $("#tscomid").val(companyName);
        $("#comid").val(companyID);
         $("#SearchTable .ul1").hide();
    });

    $(document).on("click", "#SearchTable .ul2 li", function () {

        var orgid = $(this).attr("class");
        var staffid = $(this).attr("id");
        var staffname = $(this).text();
        $("#tsreceiverID").val(staffname);
        $("#deptid").val(orgid);
        $("#receiverid").val(staffid);
        $("#SearchTable .ul2").hide();

    });
    // $(".td-ts input").blur(function () {
    //     $(this).parents(".td-ts").find("ul").hide();
    // })
    $('.td-ts input').bind('input propertychange', function() {
    	var $p = $(this);
    	if($p.attr("id")=="tscomid"&&$p.val().length>2) {
            var url = basePath
                + "ea/documentcommon/sajax_ea_getAllCompany.jspa?date="
                + new Date().toLocaleString();
            $.ajax({
                url: encodeURI(url),
                type: "post",
                async: true,
                dataType: "json",
				data:{
                "document.companyName":$p.val()
				},
                success: function cbf(data) {
                    var member = eval("(" + data + ")");
                    var companylist = member.companylist;
                    var str = "";
                    for (var i = 0; i < companylist.length; i++) {
                        var obj = companylist[i];
                        str += "<li id='" + obj.companyID + "'>" + obj.companyName + "</li>";
                    }
                    $("#SearchTable .ul1").html(str);
                    if(str!=""){
                        $p.parents(".td-ts").find("ul").show();
					}else{
                        $p.parents(".td-ts").find("ul").hide();
					}

                },
                error: function cbf(data) {
                    console.log("数据获取失败！")
                }
            });
        }else if($p.attr("id")=="tsreceiverID"&&$p.val().length>=2) {

    		if($("#comid").val()==""){
    			alert("请选填写公司");
    			return false;
			}

            var url = basePath
                + "ea/documentcommon/sajax_ea_getAllPeople.jspa?date="
                + new Date().toLocaleString();
            $.ajax({
                url: encodeURI(url),
                type: "post",
                async: true,
                dataType: "json",
				data:{
                    "document.drafterName":$p.val(),
                    "document.companyID":$("#comid").val()
				},
                success: function cbf(data) {

                    var member = eval("(" + data + ")");
                    var plist = member.plist;
                    var str = "";
                    for (var i = 0; i < plist.length; i++) {
                        var obj = plist[i];
                       str += "<li id='" + obj[1] + "'  class='"+obj[3]+"'>" + obj[4]+"—"+obj[0]+"("+obj[2]+")</li>";
                    }

                    $("#SearchTable .ul2").html(str);
                    if(str!=""){
                        $p.parents(".td-ts").find("ul").show();
                    }else{
                        $p.parents(".td-ts").find("ul").hide();
                    }
                },
                error: function cbf(data) {
                    console.log("数据获取失败！")
                }
            });
		}

    });
	bmdepts();
	// 收件部门
	$("#positionForm #organizationID").change(function() {

				var temp = $(this).find("option:selected").text();
				if (temp != "") {
					getPositionInfo(temp);
				}

			});



});

// 获取当前公司及其子公司
function getCurrentAndSubCompany() {
	var url = basePath
			+ "ea/documentsummary/sajax_ea_getCurrentAndSubCompany.jspa?date="
			+ new Date().toLocaleString();
	$.ajax({
				url : encodeURI(url),
				type : "post",
				async : true,
				dataType : "json",
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var companylist = member.companylist;
					var str = "<option value=''>请选择公司</option>";
					for (var i = 0; i < companylist.length; i++) {
						var obj = companylist[i];
						str += "<option title='" + obj.companyName
								+ "' value='" + obj.companyID + "'>"
								+ obj.companyName + "</option>";
					}
					$("#searchForm #companyID").html(str);

				},
				error : function cbf(data) {
					alert("数据获取失败！")
				}
			});
}

/**
 * 
 * 获得当前公司集团的所有公司
 */
function getAllCompanyOfGroup() {
	var url = basePath
			+ "ea/documentcommon/sajax_ea_getAllCompanyByCurrent.jspa?date="
			+ new Date().toLocaleString();
	$.ajax({
				url : encodeURI(url),
				type : "get",
				async : true,
				dataType : "json",
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var companylist = member.companylist;

					var str = "<option value=''>请选择公司</option>";
					for (var i = 0; i < companylist.length; i++) {
						var obj = companylist[i];
						str += "<option title='" + obj.companyName + "'value='"
								+ obj.companyID + "'>" + obj.companyName
								+ "</option>";
					}
					$("#SendForm select#receiverCompanyID").html(str);
				},
				error : function cbf(data) {
					alert("数据获取失败！")
				}
			});
}

/**
 * 
 * 根据公司获得部门
 * 
 * @param {}
 *            val
 */
function bmdept(val) {

	$("option", $("#receiverDeptID")).remove();

	var url = basePath
			+ "ea/documentcommon/sajax_ea_getAllOrganizations.jspa?date="
			+ new Date().toLocaleString();
	$.ajax({
				url : encodeURI(url),
				type : "post",
				async : true,
				dataType : "json",
				data : {
					"companyID" : val
				},
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var orgaizationlist = member.orgaizationlist;
					var str = "<option value=''>请选择部门</option>";
					for (var i = 0; i < orgaizationlist.length; i++) {
						var obj = orgaizationlist[i];
						str += "<option value='" + obj.organizationID + "'>"
								+ obj.organizationName + "</option>";
					}
					$("#SendForm #receiverDeptID").html(str);

				},
				error : function cbf(data) {
					alert("数据获取失败！")
				}
			});
}

/**
 * 
 * 根据部门获得人员
 * 
 * @param {}
 *            company
 * @param {}
 *            org
 */
function getPerson(company, org) {

	$("option", $("select#receiverID")).remove();

	var url = basePath
			+ "ea/documentcommon/sajax_ea_getPersonByDept.jspa?date="
			+ new Date();
	$.ajax({
				url : encodeURI(url),
				type : "post",
				async : false,
				dataType : "json",
				data : {
					"currentCompanyID" : company,
					"checkOrgID" : org
				},
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var persons = member.stafflist;
					var str = "<option value=''>请选择收件人</option>";
					for (var i = 0; i < persons.length; i++) {
						var obj = persons[i];
						str += "<option value='" + obj.staffID + "'>"
								+ obj.staffName + "(" + obj.staffCode
								+ ")</option>";
					}
					$("#SendForm #receiverID").html(str);
				}
			});
}

/**
 * 
 * 当公司改变时，获取部门
 * 
 * @param {}
 *            obj
 */
function changeCompany(obj) {
	if ($(obj).val() != '') {
		bmdept($(obj).val());
	} else {
		$("#receiverDeptID").html("<option value=''>请选择部门</option>");
	}

}

/**
 * 
 * 当部门改变时，获取员工
 * 
 * @param {}
 *            obj
 */
function changeDept(obj) {
	var dept = $("#SendForm #receiverDeptID").val();
	if (dept != "") {
		getPerson($("#SendForm #receiverCompanyID").val(), dept);
	} else {
		$("#SendForm #receiverID").html("<option value=''>请选择人员</option>");
	}
}

// 转移位置获取部门
function bmdepts() {
	var url = basePath
			+ "ea/documentcommon/sajax_ea_getAllOrganizations.jspa?date="
			+ new Date().toLocaleString();
	$.ajax({
				url : encodeURI(url),
				type : "post",
				async : true,
				dataType : "json",
				success : function cbf(data) {
					var member = eval("(" + data + ")");
					var orgaizationlist = member.orgaizationlist;
					var str = "<option value=''>请选择部门</option>";
					for (var i = 0; i < orgaizationlist.length; i++) {
						var obj = orgaizationlist[i];
						str += "<option value='" + obj.organizationID + "'>"
								+ obj.organizationName + "</option>";
					}
					$("#positionForm #organizationID").html(str);

				},
				error : function cbf(data) {
					alert("数据获取失败！")
				}
			});
}
// 根据部门获取该部门下的所有公文模块;
function getPositionInfo(deptname) {

	if (deptname == "人事处"||deptname=="人事部") {
		$("#positionForm #position").find("option:[value=finace]").remove();
		$("#positionForm #position")
				.append("<option value='person'>人事报表传输</option>");
	} else if (deptname == "财务处"||deptname=="财务部") {
		$("#positionForm #position").find("option:[value=person]").remove();
		$("#positionForm #position")
				.append("<option value='finace'>财务报表</option>");
	} else {
		$("#positionForm #position").find("option:[value=person]").remove();
		$("#positionForm #position").find("option:[value=finace]").remove();

	}

}

// 获得人力资源或者在职员工等的弹出框的操作
function importGY(url) { // 打开页面
	$("#daoRu").attr("src", basePath + url + "?date=" + new Date());
	$("#socialJqm").jqmShow();
}

function DaoruConfirm() {// 选择确定
	var childopertionID = window.frames["daoRu"].opertionID;
	if (childopertionID == "") {
		alert("请选择")
		return;
	}
	var staffName = window.frames["daoRu"].$('tr#' + childopertionID)
			.find("span#staffName").text();
	$("#InfoForm #socialName").val(staffName);
	$("#InfoForm #socials").val(childopertionID);
	$("#searchForm #socialName").val(staffName);
	$("#searchForm #socials").val(childopertionID);
	$("#daoRu").attr("src", "");
	$("#socialJqm").jqmHide();
}
function cancelJqm() {
	$("#socialJqm").jqmHide();
}

// 获得公文跟踪记录
function getDocTrackRecord(obj, docId) {
	var left = $(obj).position().left;
	var top = $(obj).position().top;
	var url = basePath
			+ "ea/documentcommon/sajax_ea_getDocTrackRecord.jspa?date="
			+ Math.random();
	$.ajax({
				url : url,
				type : "get",
				aysnc : true,
				dataType : "json",
				data : {
					docId : docId
				},
				success : function(data) {
					var member = eval("(" + data + ")");
					var tracklist = member.tracklist;
					var stafflist = member.stafflist;
					var str = "<tr><td>操作时间</td><td>操作内容</td><td>操作人</td></tr>";
					for (var i = 0; i < tracklist.length; i++) {
						var obj = tracklist[i];
						var obj2 = stafflist[i];
						str += "<tr><td>" + obj.operaterTime + "</td><td>"
								+ obj.operateContent + "</td><td>"
								+ obj2.staffName + "(" + obj2.staffCode
								+ ")</td></tr>";
					}

					$("#trackdiv").html(str);

					$(".rc_box1").css({
								position : "absolute",
								left : left - 417,
								top : top + 42
							}).show();

				},
				error : function(data) {

				}

			});

}

function hideTrackDiv() {
	$(".rc_box1").hide();
}

// 放入回收站
function putRecycleBin(docIds, stage) {	

	
	$("#recycleForm #docIdg").val(docIds);
	$("#recycleForm #stage").val(stage);
	$("#recycleForm")
			.attr("target", "hidden")
			.attr(
					"action",
					basePath
							+ "ea/documentcommon/ea_putRecycleBin.jspa?date="
							+ new Date());
	document.recycleForm.submit.click();
	token = 2;

}

// 手动输入
function menualInput() {
	$("#titleselect").attr("name", "");
	$("#titleinput").attr("name", "documentSearchInfo.title");
	$(".menual").show();
	$(".selectInput").hide();
}

// 选择输入
function selectInput() {
	$("#titleinput").attr("name", "");
	$("#titleselect").attr("name", "documentSearchInfo.title");
	$(".menual").hide();
	$(".selectInput").show();
}

// 按不同阶段显示查询内容
function showQueryContent(titleType) {
	document.searchForm.reset();
	// 公文状态
	if (titleType == "yessend") {
		$(".statusQuery").show();
	} else {
		$(".statusQuery").hide();
	}
	// 发件人：收件箱，未审批

	if (titleType == "receiver" || titleType == "examine") {
		$(".fromMemberQuery").show();
	} else {
		$(".fromMemberQuery").hide();
	}
	if (titleType == "recycle") {
		$(".deleteQuery").show();
	} else {
		$(".deleteQuery").hide();
	}
	if (titleType == "draft") {
		$(".docTypeQuery").show();
	} else {
		$(".docTypeQuery").hide();
	}
	if (titleType == "receiver" || titleType == "archive"
			|| titleType == "yessend") {
		$(".timeQuery").show();
	} else {
		$(".timeQuery").hide();
	}
	getTitleByStage(titleType);
	$("#jqModelSearch").jqmShow();

}

// 获取不同阶段的title
function getTitleByStage(titleType) {
	var url = basePath
			+ "ea/documentcommon/sajax_ea_getTitleByStage.jspa?date="
			+ new Date().toLocaleString();
	$.ajax({
				url : encodeURI(url),
				type : "get",
				async : false,
				dataType : "json",
				data : {
					titleType : titleType
				},
				success : function(data) {
					var member = eval("(" + data + ")");
					var titlelist = member.titlelist;
					var str = "<option value=''>请选择标题</option>";
					for (var i = 0; i < titlelist.length; i++) {
						str += "<option title='" + titlelist[i] + "'value='"
								+ titlelist[i] + "'>" + titlelist[i]
								+ "</option>";
					}
					$("#titleselect").html(str);
				},
				error : function(data) {
					alert("获取title失败");
				}
			});

}
function changePosition(){


		if ($("#positionForm #organizationID").val() == "") {
			alert("请选择部门");
			return;
		}
		if ($("#positionForm #position").val() == "") {
			alert("请选择位置");
			return;
		}

		if (confirm("确定转移位置？")) {
			$("#positionForm").attr("target","hidden").attr(
					"action",
					basePath + "ea/documentcommon/ea_changePosition.jspa?date="
							+ new Date());
			document.positionForm.submit.click();
			token = 2;
		}
		

}

// 设置打开word或excel
//fileType word:W Excel:E
//isRead 是否只读 ：只读：1 可写：2;
//阶段stage:",,,,,,,";
function OpenOffice(docId,isRead,stage) {

var url = basePath+"ea/documentinfo/sajax_ea_viewAttach.jspa";
				$.ajax({
				  url:url,
				  type:"get",
				  async:false,
				  dataType:"json",
				  data:{
				  	docId:docId
				  },
				  success:function(data){
				  	  var member = eval("("+data+")");
				  	  var attach = member.attach;
				  	 
				  	 window.open(basePath
				  			+ "page/ea/main/office_ea/document/wordcommon.jsp?docPath="
				  			+ attach.filePath+"&fileType=" + attach.fileType+"&isRead="+isRead+"&stage="+stage);
				  },
				  error:function(data){
				  	alert("获取附件失败");
				  }
				
	
    
   });
}
//已经路径直接打开
function OpenOffice2(docPath,fileType,isRead,stage) {

	
	window.open(basePath
					  			+ "page/ea/main/office_ea/document/wordcommon.jsp?docPath="
					  			+docPath+"&fileType=" + fileType+"&isRead="+isRead+"&stage="+stage);

					
		

	}