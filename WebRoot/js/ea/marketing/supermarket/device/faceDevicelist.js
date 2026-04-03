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
				title : (deviceType=="00"?"微信刷脸设备":"支付宝刷脸设备")+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;设备号SN：<input type='text' id='ch' style='width:100px'/> &nbsp;激活状态：<select id='acState' ><option value=''>全部</option><option value='01'>已激活</option><option value='00'>未激活</option></select> &nbsp;绑定状态：<select id='sta' ><option value=''>全部</option><option value='01'>已绑定</option><option value='00'>未绑定</option></select><input type='button' value=' 查询 ' id='toSearch' class='input-button'/> ",
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
                    name : '导出',
                    bclass : 'excel',
                    onpress : action
                    // 当点击调用方法
                }	,  {
                    separator : true
                },{
                    name : '分配业务员',
                    bclass : 'excel',
                    onpress : action
                    // 当点击调用方法
                }	,  {
                    separator : true
                },{
                    name : '解绑业务员',
                    bclass : 'delete',
                    onpress : action
                    // 当点击调用方法
                }	,  {
                    separator : true
                },
                //     {
                //     name : '业务员绑定历史记录',
                //     bclass : 'excel',
                //     onpress : action
                //     // 当点击调用方法
                // }	,  {
                //     separator : true
                // },
                    {
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
                    var posKey = $("tr#" + pfdID).find("#pfdkey").text();
                    $("#addForm #pfdkey").val(posKey);
                    $("#addForm").attr("target", "hidden").attr("action", basePath + "ea/face/ea_deletePos.jspa");
                    document.addForm.submit.click();
                    token = 2;
                }
                break;
            case '导出':
                url = basePath
                    + "ea/face/ea_showExcel.jspa?search="
                    + search;
                open(url);
                break;
            case '分配业务员':
                if (pfdID == "") {
                    alert("请选择！");
                    return;
                }
                var clerkID = $("tr#" + pfdID).find("#clerkID").text();
                if(clerkID!=null&&clerkID!=""){
                      alert("此设备已绑定业务员，如需重新绑定，请先解绑");
                      return false;
                }
                var sn = $("tr#" + pfdID).find("#sn").text();

                $("#bindTable #sn").val(sn);

                $("#jqModelbind").jqmShow();
                break;
            case '解绑业务员':
                if (pfdID == "") {
                    alert("请选择！");
                    return;
                }
                var clerkID = $("tr#" + pfdID).find("#clerkID").text();
                var sn = $("tr#" + pfdID).find("#sn").text();
                var clerkName = $("tr#" + pfdID).find("#clerkName").text();
                var clearkAccount = $("tr#" + pfdID).find("#clearkAccount").text();


                if(clerkID==null||clerkID==""){
                    return false;
                }else{
                    if(confirm("确认将设备SN"+sn+"与业务员"+clerkName+"解绑?")) {
                        $("#bindForm #sn").val(sn);
                        $("#bindForm .wfjac").val(clearkAccount);


                        $("#bindForm").attr("target","hidden").attr(
                            "action",
                            basePath + "ea/face/ea_removeClerk.jspa");
                        token = 2;
                        document.bindForm.submit.click();
                    }
                }

                break;
            case '业务员绑定历史记录':
                if (pfdID == "") {
                    alert("请选择！");
                    return;
                }
                var clerkID = $("tr#" + pfdID).find("#clerkID").text();
                var sn = $("tr#" + pfdID).find("#sn").text();
                var clerkName = $("tr#" + pfdID).find("#clerkName").text();
                var clearkAccount = $("tr#" + pfdID).find("#clearkAccount").text();


                break;
		    case '设置每页显示条数' :
			var url = basePath
						+ "ea/face/ea_getFaceDevcieList.jspa?search="
						+ search;
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
        $(".posNum").trigger("blur");
        if($("#addForm .error").length>0){
        	 return;
		}
		$("#addForm").attr("target","hidden").attr(
				"action",
				basePath + "ea/face/ea_addOrUpdate.jspa");
        token = 2;
		document.addForm.submit.click();
	});


    //绑定业务员
    $("#saveBind").click(function() {
        $(".put3").trigger("blur");
        $(".wfjac").trigger("blur");
        if($("#bindForm .error").length>0){
            return;
        }
        $("#bindForm").attr("target","hidden").attr(
            "action",
            basePath + "ea/face/ea_bindClerk.jspa");
        token = 2;
        document.bindForm.submit.click();
    });


    //验证序号不可重复
    $("#bindForm .wfjac").live("blur", function() {
        $input = $(this);
        $parent = $input.parent();
        var inputValue = $input.attr("value");
        // $parent.find(".error").remove();
        // $parent.find(".corect").remove();
        // $parent.find(".tooltip").remove();
        if ($input.is(".wfjac")) {
            if (inputValue != "") {

                if (!checkAccount(inputValue)) {
                    $parent
                        .append("<span class=\"error\"><a class=\"tex\">微分金账号不存在请确认后重填</a></span>");
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



    //验证序号不可重复
    $("#addTable .posNum").live("blur", function() {
        $input = $(this);
        $parent = $input.parent();
        var inputValue = $input.attr("value");
        // $parent.find(".error").remove();
        // $parent.find(".corect").remove();
        // $parent.find(".tooltip").remove();
        if ($input.is(".posNum")) {
            if (inputValue != "") {

                    if (!checkPosNum(inputValue)) {
                        $parent
                            .append("<span class=\"error\"><a class=\"tex\">设备号已存在</a></span>");
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
        $("#searchForm").find("#posNum").val($("#ch").val());

        $("#searchForm").find("#state").val($("#sta").val());

        $("#searchForm").find("#activeState").val($("#acState").val());

        $("#searchForm").attr(
            "action",
            basePath + "ea/face/ea_toSearch.jspa");
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

                treedw = new dhtmlXTreeObject("dwTree", "100%", "100%", 0);
                treedw.enableDragAndDrop(false);
                treedw.enableHighlighting(1);
                treedw.enableCheckBoxes(0);
                treedw.enableThreeStateCheckboxes(false);
                treedw.setSkin(basePath + 'js/tree/dhx_skyblue');
                treedw.setImagePath(basePath + "js/tree/codebase/imgs/");
                treedw.loadXML(basePath + "js/tree/common/tree_b.xml");
                var getcodeurl = basePath
                    + "ea/ccode/sajax_ea_getListCCodeByPID.jspa?codeID=scode20110224xpd2t2jvda0000000002&date="
                    + new Date().toLocaleString();
                treedw.insertNewChild(0,
                    "scode20110224xpd2t2jvda0000000002", "单位类别", 0, 0,
                    0, 0);
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

                            treedw.insertNewChild(
                                "scode20110224xpd2t2jvda0000000002",
                                codeList[i].codeID,
                                codeList[i].codeValue, 0, 0, 0, 0);
                            treedw.setUserData(codeList[i].codeID,
                                "codeNumber", codeList[i].codeNumber);

                        }
                    },
                    error : function cbf(data) {
                        alert("数据获取失败！");
                    }
                });
                treedw
                    .setOnClickHandler(function() {

                        treeid = treedw.getSelectedItemId();
                        treename = treedw.getItemText(treeid);

                        var typeName = $("input#ccompanyIDs",
                            $("table#searchcompany")).val();

                        if (treeid == "scode20110224xpd2t2jvda0000000002") {
                            treename = "";
                        }
                        $(":input#dwparms").val(treename);
                        cxwldw("contactCompany.companyName=" + typeName
                            + "&cconnection.contactConnections="
                            + treename);
                        return;

                    });

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


                            $("#addForm #facName")
                                .val(
                                    $("tr#" + contactcID).find(
                                        "#ccompanyname")
                                        .text());

                            $("#addForm #factoryID").val(
                                contactcID);
                            $("#addForm #factoryName").val(
                                $("tr#" + contactcID).find(
                                    "#ccompanyname")
                                    .text());
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

                        cxwldw("contactCompany.companyName=" + typeName
                            + "&cconnection.contactConnections="
                            + contactConnections);
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
                                var typeCN = "contactCompany.companyName="
                                    + typeName
                                    + "&cconnection.contactConnections="
                                    + contactConnections
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
                                var typeCN = "contactCompany.companyName="
                                    + typeName
                                    + "&cconnection.contactConnections="
                                    + contactConnections
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
                        + "ea/contactcompany/sajax_ea_getListContactCompanyByCompanyName.jspa?";
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
                                tabletr += " <tr><th height='21' align='center' bgcolor='#E4F1FA'>选择</th><th align='center' bgcolor='#E4F1FA'>往来单位名称</th>";
                                tabletr += " <th align='center' bgcolor='#E4F1FA'>往来关系</th>";
                                tabletr += " <th align='center' bgcolor='#E4F1FA'>行业类别</th><th align='center' bgcolor='#E4F1FA'>单位电话</th>";
                                tabletr += " <th align='center' bgcolor='#E4F1FA'>单位负责人</th><th align='center' bgcolor='#E4F1FA'>负责人电话</th>";

                                for ( var i = 0; i < pageForm.list.length; i++) {
                                    tabletr += "<tr style='cursor: hand;' title="
                                        + pageForm.list[i].ccompanyID
                                        + " id = "
                                        + pageForm.list[i].ccompanyID
                                        + ">";
                                    tabletr += "<td id='checkcc' align='center'><input type ='radio'  class='ra' value="
                                        + pageForm.list[i].contactConnectionID
                                        + " name='checkradio'/></td>";
                                    tabletr += "<td id='ccompanyname' align='center'>"
                                        + pageForm.list[i].companyName
                                        + "</td>";
                                    tabletr += "<td id='contactConnections' align='center'>"
                                        + pageForm.list[i].contactConnections
                                        + "</td>";
                                    tabletr += "<td id='industryType' align='center'>"
                                        + pageForm.list[i].industryType
                                        + "</td>";
                                    tabletr += "<td id='companyTel'  align='center'>"
                                        + pageForm.list[i].companyTel
                                        + "</td>";
                                    tabletr += "<td id='cresponsible' align='center'>"
                                        + pageForm.list[i].cresponsible
                                        + "</td>";
                                    tabletr += "<td id='responsibleTel' align='center'>"
                                        + pageForm.list[i].responsibleTel
                                        + "</td>";

                                    tabletr += "<td id='ccompanyID' style='display:none' align='center'>"
                                        + pageForm.list[i].ccompanyID
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
            + "ea/face/ea_getFaceDeviceList.jspa?pageNumber=" + pNumber+"&deviceType="+deviceType;
    }
}
// 判断设备号重复
function checkPosNum(posNum) {

    var bool = null;
    var url = basePath + "ea/face/sajax_ea_checkRepPosNum.jspa?date="
        + new Date();
    $.ajax({
        url : url,
        type : "get",
        async : false,
        dataType : "json",
        data : {
            "payDevice.sn" : $("#addTable #sn").val(),
            "payDevice.pfdID":pfdID
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

// 判断手机号是否是微分金账号
function checkAccount(wfjAccount) {

    var bool = null;
    var url = basePath + "ea/face/sajax_ea_checkUser.jspa?date="
        + new Date();
    $.ajax({
        url : url,
        type : "get",
        async : false,
        dataType : "json",
        data : {
            "payDevice.clearkAccount":wfjAccount
        },
        success : function(data) {
            var member = eval("(" + data + ")");
            var result = member.result;
            if (result == "false") {
                bool = false;
            } else {
                bool = true;
            }

        },
        error : function(data) {
            console.log("读取数据失败");
        }

    });

    return bool;
}


