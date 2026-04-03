$(function() {
	$(".jqmWindow").jqm({
		modal : true,// 限制输入（鼠标点击，按键）的对话
		overlay : 20
			// 遮罩程度%
		}).jqmAddClose('.close');// 添加触发关闭的selector


	$('.flexme11').flexigrid({
				height : 350,
				width : 'auto',
				minwidth : 30,
				title : (deviceType=="00"?"微信商家签约":"支付宝商家签约")+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;商户名称：<input type='text' id='ch' style='width:100px'/>&nbsp;AppID：<input type='text' id='sta' style='width:100px'/> &nbsp;<input type='button' value=' 查询 ' id='toSearch' class='input-button'/> ",
				minheight : 80,
				buttons : [{
							name : '添加',
							bclass : 'add',
							onpress : action
							// 当点击调用方法
					}	, {
							separator : true
						}, {
							name : '修改',
							bclass : 'edit',
							onpress : action
							// 当点击调用方法
					}	, {
							separator : true
						},  {
                    name : '删除',
                    bclass : 'delete',
                    onpress : action
                    // 当点击调用方法
                }	,  {
                    separator : true
                },{
                    name : '绑定设备',
                    bclass : 'add',
                    onpress : action
                    // 当点击调用方法
                }	,  {
                    separator : true
                },{
                    name : '调整费率',
                    bclass : 'add',
                    onpress : action
                    // 当点击调用方法
                }	,  {
                    separator : true
                },{
							name : '设置每页显示条数',
							bclass : 'mysearch',
							onpress : action
							// 当点击调用方法
					  } , {
							separator : true
				}]
	});

	function action(com, grid){
		switch (com) {
			case '添加':
                document.addForm.reset();
                $(".error").remove();
                pfdID  = "";
              $("#jqModeladd").jqmShow();
                break;
			case '修改' :
				if (pfdID == "") {
					alert("请选择！");
					return;
				}
                document.addForm.reset();
                $t = $("table#addTable");
                $p = $("tr#" + pfdID);
                $p.find("span[id]").each(function() {
                    $t.find("#" + this.id).val($(this).text());

                });
                $("#jqModeladd").jqmShow();
                break;
			case '删除':
                if (pfdID == "") {
                    alert("请选择！");
                    return;
                }
                if(confirm("确定删除？")) {
                    var subCompanyID = $("tr#" + pfdID).find("#subCompanyID").text();
                    var url = basePath + "ea/face/sajax_ea_checkStoreHaveDevice.jspa?date="
                        + new Date();
                    $.ajax({
                        url : url,
                        type : "get",
                        async : false,
                        dataType : "json",
                        data : {
                            "bindDevice.subCompanyID" :subCompanyID
                        },
                        success : function(data) {
                            var member = eval("(" + data + ")");
                            var result = member.result;
                            if (result == "1") {// 重复
                               alert("该商户绑定了设备无法删除");
                            } else {
                                var sbdKey = $("tr#" + pfdID).find("#sbdKey").text();
                                $("#addForm #sbdKey").val(sbdKey);
                                $("#addForm").attr("target", "hidden").attr("action", basePath + "ea/face/ea_deleteStore.jspa");
                                document.addForm.submit.click();
                                token = 2;
                            }

                        },
                        error : function(data) {
                            console.log("读取数据失败");
                        }

                    });


                }
                break;
            case '绑定设备':
                if (pfdID == "") {
                    alert("请选择！");
                    return;
                }
                var subCompanyID = $("tr#" + pfdID).find("#subCompanyID").text();
                var storeName = $("tr#" + pfdID).find("#storeName").text();

                document.location.href = basePath+"ea/face/ea_getStoreAllDevice.jspa?bindDevice.subCompanyID="+subCompanyID+"&bindDevice.storeName="+storeName;

                break;
            case '调整费率':
                if (pfdID == "") {
                    alert("请选择！");
                    return;
                }
                var subCompanyID = $("tr#" + pfdID).find("#subCompanyID").text();
                var storeName = $("tr#" + pfdID).find("#storeName").text();

                $("#rateForm #subCompanyID").val(subCompanyID);
                $("#rateForm #storeName").val(storeName);


                $("#jqModelrate").jqmShow();
                break;

		    case '设置每页显示条数' :
			var url = basePath
						+ "ea/face/ea_getStoreBindList.jspa?search="
						+ search+"&deviceType="+deviceType;
				numback(url);
				break;
		}
    }
    
	$(".flexme11 tr[id]").click(function() {
        pfdID = this.id;
		$("input.JQuerypersonvalue", $(this))
				.attr("checked", "checked");
	});

    $(".flexme11 tr[id]").dblclick(function() {
        pfdID = this.id;
        $("input.JQuerypersonvalue", $(this))
            .attr("checked", "checked");

        action("修改");
    });

	//保存
	$("#save").click(function() {
        $(".put3").trigger("blur");
        $(".comNum").trigger("blur");
        if($("#addForm .error").length>0){
        	 return;
		}
		$("#addForm").attr("target","hidden").attr(
				"action",
				basePath + "ea/face/ea_addStoreOrUpdate.jspa");
        token = 2;
		document.addForm.submit.click();
	});


    //调整费率保存
    $("#saveRate").click(function() {
        $(".put3").trigger("blur");
        if($("#rateForm .error").length>0){
            return;
        }
        $("#rateForm").attr("target","hidden").attr(
            "action",
            basePath + "ea/face/ea_changeRate.jspa");
        token = 2;
        document.rateForm.submit.click();
    });


     //验证商户不可重复签约
    $("#addTable .comNum").live("blur", function() {
        $input = $(this);
        $parent = $input.parent();
        var inputValue = $parent.find("#subCompanyID").val();
        if ($input.is(".comNum")) {
            if (inputValue != "") {

                    if (!checkComNum(inputValue)) {
                        $parent
                            .append("<span class=\"error\"><a class=\"tex\">该商户已签约</a></span>");
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

	//查询
    $("#toSearch").click(function() {
        $("#searchForm").find("#storeName").val($("#ch").val());

        $("#searchForm").find("#subAppID").val($("#sta").val());


        $("#searchForm").attr(
            "action",
            basePath + "ea/face/ea_toBindSearch.jspa");
        token = 2;
        document.searchForm.submit.click();
    });

    //

    $(".close").click(function(){
        $("#serdevice").hide();
    });
 




    /** **********************************往来个人**************************************** */
// 选择往来个人
    $(document)
        .ready(
            function() {
                treegr = new dhtmlXTreeObject("grTree", "100%", "100%", 0);
                treegr.enableDragAndDrop(false);
                treegr.enableHighlighting(1);
                treegr.enableCheckBoxes(0);
                treegr.enableThreeStateCheckboxes(false);
                treegr.setSkin(basePath + 'js/tree/dhx_skyblue');
                treegr.setImagePath(basePath + "js/tree/codebase/imgs/");
                treegr.loadXML(basePath + "js/tree/common/tree_b.xml");
                var getcodeurl = basePath
                    + "ea/ccode/sajax_ea_getListCCodeByPID.jspa?codeID=scode20110106hfjes5ucxp0000000017&date="
                    + new Date().toLocaleString();
                treegr.insertNewChild(0,
                    "scode20110106hfjes5ucxp0000000017", "往来个人类别", 0,
                    0, 0, 0);
                $.ajax({
                    url : encodeURI(getcodeurl),
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
                        var codeList = member.codeList;
                        if (null == codeList) {
                            return;
                        }
                        for ( var i = 0; i < codeList.length; i++) {

                            treegr.insertNewChild(
                                "scode20110106hfjes5ucxp0000000017",
                                codeList[i].codeID,
                                codeList[i].codeValue, 0, 0, 0, 0);
                            treegr.setUserData(codeList[i].codeID,
                                "codeNumber", codeList[i].codeNumber);

                        }
                    },
                    error : function cbf(data) {
                        alert("数据获取失败！");
                    }
                });
                treegr.setOnClickHandler(function() {

                    treeid = treegr.getSelectedItemId();
                    treename = treegr.getItemText(treeid);

                    var typeName = $("input#contactUserID",
                        $("table#searchuser")).val();

                    if (treeid == "scode20110106hfjes5ucxp0000000017") {
                        treename = "";
                    }
                    $(":input#grparms").val(treename);

                    var typeCN = "contactUser.staffName=" + typeName
                        + "&contactUser.relation=" + treename;

                    if($("#xmtype").val()=="043111"){
                        typeCN+="&dataIsComplete=00&identifier=baokaixue";
                    }

                    cxwlgr(typeCN);
                    return;

                });

                var cuID = "";
                var userstaffID = "";
                $("table#gouserstable tr[id]").live(
                    "click",
                    function(event) {
                        cuID = this.id;
                        userstaffID = this.title;
                        $("input.rauser", $(this)).attr("checked",
                            "checked");
                    });

                // 选择往来个人
                $(".wlgr")
                    .live("click",
                        function() {

                            $(".jqmWindow", $("#selectuserForm"))
                                .jqmShow();
                            cxwlgr("contactUser.staffName=&contactUser.relation=");
                        });

                // 新增往来个人
                $(".xzgr")
                    .click(
                        function() {
                            window
                                .open(basePath
                                    + "ea/cstaff/ea_getListCStaffByCompanyID.jspa?");
                        });

                // 添加所选中的往来个人
                $("#qduser").click(
                    function() {
                        if (cuID != "") {

                          var  contactUserName =  $("tr#" + cuID).find(
                                "#contactUserName")
                                .text();
                          var contactUserID =   $("tr#" + cuID).find(
                                "#contactUserID")
                                .text();
                            $(".jqmWindow", $("#selectuserForm"))
                                .jqmHide();

                           $("#addForm #staffID").val(contactUserID);
                           $("#addForm #staffName").val(contactUserName);
                            $("#addForm #stname").val(contactUserName);
                            $("#addForm #stname").focus();

                            return;
                        } else {
                            alert("请选择往来个人！");
                        }
                    });

                // 根据输入的往来个人名称查询
                $("input#searchuu").click(
                    function() {
                        cuID = "";
                        userstaffID = "";
                        var typeName = $("input#contactUserID",
                            $("table#searchuser")).val();
                        var relation = $(":input#grparms").val();
                        cxwlgr("contactUser.staffName=" + typeName
                            + "&contactUser.relation=" + relation);
                    });

                // 上一页
                $("#grsy").click(
                    function() {
                        var sy = $("#grsy").attr("title");
                        if (sy != 0) {
                            cuID = "";
                            userstaffID = "";
                            var typeName = $("input#contactUserID",
                                $("table#searchuser")).val();
                            var relation = $(":input#grparms").val();
                            var typeCN = "contactUser.staffName="
                                + typeName
                                + "&contactUser.relation="
                                + relation
                                + "&pageForm.pageNumber=" + sy;
                            cxwlgr(typeCN);
                        } else {
                            alert("已是首页！");
                        }
                    });

                // 下一页
                $("#grxy").click(
                    function() {
                        var xy = $("#grxy").attr("title");
                        if (xy != 0) {
                            cuID = "";
                            userstaffID = "";
                            var typeName = $("input#contactUserID",
                                $("table#searchuser")).val();
                            var relation = $(":input#grparms").val();
                            var typeCN = "contactUser.staffName="
                                + typeName
                                + "&contactUser.relation="
                                + relation
                                + "&pageForm.pageNumber=" + xy;
                            cxwlgr(typeCN);
                        } else {
                            alert("已是尾页！");
                        }
                    });

                // ajax查询往来个人列表
                function cxwlgr(typeCN) {
                    if (notoken==1) {
                        //alert("正在获取数据！请稍等");
                        return;
                    }
                    notoken = 1;
                    $("#grsy").attr("title", 0);
                    $("#grxy").attr("title", 0);
                    $("#grzy").attr("title", 0);
                    var searchurl = basePath
                        + "ea/contactuser/sajax_ea_getListContactUserBycontactUserName.jspa?";

                    $
                        .ajax({
                            url : encodeURI(searchurl + typeCN
                                + "&date="
                                + new Date().toLocaleString()),
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

                                var pageForm = member.pageForm;
                                var codeRelationList = member.codeRelationList;
                                if (pageForm == null) {
                                    alert("没有数据");
                                    notoken = 0;
                                    return;
                                }

                                var dqy = pageForm.pageNumber;// 当前页
                                var zys = pageForm.pageCount;// 总页数
                                if (dqy > 1) {
                                    $("#grsy").attr("title", dqy - 1);
                                }
                                if (dqy < zys) {
                                    $("#grxy").attr("title", dqy + 1);
                                }
                                $("span#grzycount").text(zys);
                                var tabletr = "<table width='98%' height='26' align='center' cellspacing='0' cellpadding='1' style='font-size:12px;' class='bannb_01'><tr><td height='24' align='left' valign='top' class='txt01' >&nbsp;点击选择往来个人</td></tr></table>";
                                tabletr += "<table width='99%' align='center' id='gouserstable' cellpadding='0'  cellspacing='0' class='table'>";
                                tabletr += " <tr><th height='21' align='center' bgcolor='#E4F1FA'>选择</th><th align='center' bgcolor='#E4F1FA'>个人名称</th>"
                                    + "<th align='center' bgcolor='#E4F1FA'>往来关系</th><th align='center' bgcolor='#E4F1FA'>电话</th><th align='center' bgcolor='#E4F1FA'>身份证</th><th align='center' bgcolor='#E4F1FA'>qq</th><th align='center' bgcolor='#E4F1FA'>邮箱 </th></tr>";
                                for ( var i = 0; i < pageForm.list.length; i++) {
                                    tabletr += "<tr style='cursor: hand;' id = "
                                        + pageForm.list[i].relationID
                                        + " title= "
                                        + pageForm.list[i].staffID
                                        + ">";
                                    tabletr += "<td id='checkcc' align='center'><input type ='radio'  class='rauser' value="
                                        + pageForm.list[i].relationID
                                        + " name='checkradio'/></td>";
                                    tabletr += "<td id='contactUserName' align='center'>"
                                        + pageForm.list[i].staffName
                                        + "</td>";
                                    tabletr += "<td id='phone' align='center'>"
                                        + pageForm.list[i].relation
                                        + "</td>";
                                    tabletr += "<td id='tel' align='center'>"
                                        + pageForm.list[i].reference
                                        + "</td>";
                                    tabletr += "<td id='staffIdentityCard' align='center'>"
                                        + pageForm.list[i].staffIdentityCard
                                        + "</td>";
                                    tabletr += "<td id='userQq'  align='center'>"
                                        + pageForm.list[i].referenceCode
                                        + "</td>";
                                    tabletr += "<td id='email'  align='center'>"
                                        + pageForm.list[i].referenceOrganization
                                        + "</td>";
                                    tabletr += "<td id='contactUserID'  style='display:none' align='center'>"
                                        + pageForm.list[i].staffID
                                        + "</td>";
                                    tabletr += " </tr>";
                                }
                                tabletr += " </table>";
                                $("#body_02cu").html(tabletr);
                                $("#body_02cu").show();
                                // alert("数据加载成功")
                                notoken = 0;
                                window.status = "数据加载成功";
                            },
                            error : function cbf(data) {
                                notoken = 0;
                                alert("数据获取失败！");
                            }
                        });
                }


                // 各个弹出框的关闭功能
                $(".closewr").click(function() {
                    notoken = 0;
                    $("#grjqModel").jqmHide();
                });
              // 选择往来个人
                $(".wlgr")
                    .live("click",
                        function() {

                            $(".jqmWindow", $("#selectuserForm"))
                                .jqmShow();
                            cxwlgr("contactUser.staffName=&contactUser.relation=");
                        });
            });

    /** **********************************往来单位**************************************** */
    $(document)
        .ready(
            function() {



                var contactcID = "";// 已经添加到往来单位的ID
                var ccID = "";// ccompanyID
                $("table#gostable tr[id]").live("click", function(event) {
                    contactcID = this.id;
                    ccID = this.title;
                    $("input.ra", $(this)).attr("checked", "checked");
                });

                $(".JQueryreturns").click(function() {
                    notoken = 0;
                    $("#companyjqModel").jqmHide();
                });
                // 选择往来单位
                $(".wanladw")
                    .live("click",
                        function() {
                            $("#clicktr").val(
                                $(this).parent().parent()
                                    .parent().attr("id"));
                            $(".jqmWindow", $("#selectcompanyForm"))
                                .jqmShow();
                            cxwldw("contactCompany.companyName=&cconnection.contactConnections=");
                        });

                // 新增往来单位
                $(".xzdw")
                    .live("click",
                        function() {
                            window
                                .open(basePath
                                    + "ea/contactcompany/ea_getListContactCompany.jspa?");
                        });

                // 添加所选中的往来单位
                $("#qdcompany").click(
                    function() {

                        if (contactcID != "") {
                            var $tbl = $("#"+$("#linet").val());
                            $("#addForm #storeName")
                                .val(
                                    $("tr#" + contactcID).find(
                                        "#ccompanyname")
                                        .text());

                            $("#addForm #subCompanyID").val(
                                contactcID);

                            $("#addForm #storeID").val(
                                contactcID);
                            $(".jqmWindow", $("#selectcompanyForm"))
                                .jqmHide();

                        } else {
                            alert("请选择往来单位！");
                        }
                    });

                // 根据输入的往来单位名称查询
                $("input#searchcc").click(
                    function() {
                        contactcID = "";
                        var typeName = $("input#ccompanyIDs",
                            $("table#searchcompany")).val();
                        var contactConnections = $(":input#dwparms")
                            .val();

                        cxwldw("parameter=" + typeName);
                    });

                // 上一页
                $("#dwsy")
                    .click(
                        function() {
                            var sy = $("#dwsy").attr("title");
                            if (sy != 0) {
                                contactcID = "";
                                var typeName = $(
                                    "input#ccompanyIDs",
                                    $("table#searchcompany"))
                                    .val();
                                var contactConnections = $(
                                    ":input#dwparms").val();
                                var typeCN = "parameter="
                                    + typeName
                                    + "&pageForm.pageNumber="
                                    + sy;
                                cxwldw(typeCN);
                            } else {
                                alert("已是首页！");
                            }
                        });

                // 下一页
                $("#dwxy")
                    .click(
                        function() {
                            var xy = $("#dwxy").attr("title");
                            if (xy != 0) {
                                contactcID = "";
                                var typeName = $(
                                    "input#ccompanyIDs",
                                    $("table#searchcompany"))
                                    .val();
                                var contactConnections = $(
                                    ":input#dwparms").val();
                                var typeCN = "parameter="
                                    + typeName
                                    + "&pageForm.pageNumber="
                                    + xy;
                                cxwldw(typeCN);
                            } else {
                                alert("已是尾页！");
                            }
                        });

                // ajax查询往来单位列表
                function cxwldw(typeCN) {
                    if (notoken) {
                        alert("正在获取数据！请稍等");
                        return;
                    }
                    notoken = 1;
                    $("#dwsy").attr("title", 0);
                    $("#dwxy").attr("title", 0);
                    $("#dwzy").attr("title", 0);
                    var searchurl = basePath
                        + "ea/face/sajax_ea_getCompanyList.jspa?";
                    $
                        .ajax({
                            url : encodeURI(searchurl + typeCN
                                + "&date="
                                + new Date().toLocaleString()),
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
                                var pageForm = member.pageForm;

                                if (pageForm == null) {
                                    alert("没有数据");
                                    notoken = 0;
                                    return;
                                }

                                var dqy = pageForm.pageNumber;// 当前页
                                var zys = pageForm.pageCount;// 总页数
                                if (dqy > 1) {
                                    $("#dwsy").attr("title", dqy - 1);
                                }
                                if (dqy < zys) {
                                    $("#dwxy").attr("title", dqy + 1);
                                }
                                $("span#dwzycount").text(zys);
                                var tabletr = "<table width='98%' height='26' align='center' cellspacing='0' cellpadding='1' style='font-size:12px;' class='bannb_01'><tr><td height='24' align='left' valign='top' class='txt01' >&nbsp;点击选择往来单位</td></tr></table>";
                                tabletr += "<table width='99%' align='center' id='gostable' cellpadding='0'  cellspacing='0' class='table'>";
                                tabletr += " <tr><th height='21' align='center' bgcolor='#E4F1FA'>选择</th><th align='center' bgcolor='#E4F1FA'>商户名称</th>";
                                tabletr += " <th align='center' bgcolor='#E4F1FA'>微分金账号</th>";
                                tabletr += " <th align='center' bgcolor='#E4F1FA'>账号姓名</th><th align='center' bgcolor='#E4F1FA'>行业</th>";

                                for ( var i = 0; i < pageForm.list.length; i++) {
                                    tabletr += "<tr style='cursor: hand;' title="
                                        + pageForm.list[i][1]
                                        + " id = "
                                        + pageForm.list[i][1]
                                        + ">";
                                    tabletr += "<td id='checkcc' align='center'><input type ='radio'  class='ra' value="
                                        + pageForm.list[i][1]
                                        + " name='checkradio'/></td>";
                                    tabletr += "<td id='ccompanyname' align='center'>"
                                        + pageForm.list[i][2]
                                        + "</td>";
                                    tabletr += "<td id='wfjAaccout' align='center'>"
                                        + pageForm.list[i][0]
                                        + "</td>";
                                    tabletr += "<td id='wfjName' align='center'>"
                                        + pageForm.list[i][4]
                                        + "</td>";
                                    tabletr += "<td id='industryType' align='center'>"
                                        + pageForm.list[i][3]
                                        + "</td>";


                                    tabletr += " </tr>";
                                }
                                tabletr += " </table>";
                                $("#body_02cc").html(tabletr);
                                $("#body_02cc").show();
                                // alert("数据加载成功");
                                notoken = 0;
                                window.status = "数据加载成功";
                            },
                            error : function cbf(data) {
                                notoken = 0;
                                alert("数据获取失败！");
                            }
                        });
                }
            });
    /** **********************************往来个人**************************************** */

});

function re_load() {
	if(token) {
        document.location.href = basePath
            + "ea/face/ea_getStoreBindList.jspa?pageNumber=" + pNumber+"&deviceType="+deviceType;
    }
}
// 判断是否商户是否签约过
function checkComNum(subCompanyID) {

    var bool = null;
    var url = basePath + "ea/face/sajax_ea_checkComPosNum.jspa?date="
        + new Date();
    $.ajax({
        url : url,
        type : "get",
        async : false,
        dataType : "json",
        data : {
            "bindDevice.subCompanyID" : subCompanyID,
            "bindDevice.sbdID":pfdID
        },
        success : function(data) {
            var member = eval("(" + data + ")");
            var result = member.result;
            if (result == "1") {// 重复
                bool = false;
            } else {
                bool = true;// 不重复
            }

        },
        error : function(data) {
            console.log("读取数据失败");
        }

    });

    return bool;
}

