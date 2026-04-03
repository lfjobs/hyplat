﻿<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8" %>
<%@page import="hy.ea.bo.Company"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    String filepath = request.getSession().getServletContext().getRealPath("/");
    Company company = (Company)request.getAttribute("company");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <!-- 社会人力页面调整方案2 -->
    <title>添加修改社会人力列表</title>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/css/ea/validate.css" />
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/admin_mains.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/overlayer.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

    <script type="text/javascript" src="<%=basePath%>js/jquery.js" ></script>
    <script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/ea/validate.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/overlayer.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/photoup/CJL.0.1.min.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/photoup/ImagePreviewd.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/ea/human/cstaff/humanResource.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/ea/ccodes_add.js"></script>

    <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>

<%--    <link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>--%>

    <script type="text/javascript">
        var basePath = '<%=basePath%>';
        var retoken = 0;
        var staffID = '${cstaff.staffID}';
        var personIdentityCard;
        var aa = '<%=request.getParameter("aa")%>';
        var showType = '${showType}';
        var roleID = '${account.roleID}';
        var select = 1;
        var photosizes = 0;
        var status = '${cstaff.status}';
        var ids = ''; //存放行ID
        var isvals = 0; //赋值判断
        var str = "";
        var temp = "";
        var relation = "${param.relation}";
        var companyID = "<%=company.getCompanyID()%>";
        var companyName = "<%=company.getCompanyName()%>";
        function autoRead() {
            if ($.browser.msie) {
                initCardRead();
            }
            alert("该只支持IE!请切换为IE");
            return;
        }
        /**
         初始化信息 步驟
         使用的时候要先设置端口 串口：1~16 USB：1001~1016
         然后初始化，在读取信息
         */
        function initCardRead() {
            var i = 1000;
            while (str != "0") {
                SynIDCard1.Port = i++;
                //初始化成功是返回0
                str = SynIDCard1.Init();
            }
            if (str == "-1") {
                alert("未找到读卡设备，请先连接读卡设备。");
                return;
            }

            ReadCard_onclick();
        }
        function ReadCard_onclick() {
            var str1 = SynIDCard1.ReadCard();
            var $form = $("#box1Form");
            //根据获得值判断是否成功读取，成功设置字段
            if (str1.indexOf("读卡成功") > -1) {
                //检查该人员数据是否已经存在
                var urlCard = basePath + "ea/cstaff/sajax_n_ea_IsLawfulIdentityCard.jspa?result=" + SynIDCard1.CardNo + "&date=" + new Date().toLocaleString();
                $.ajax({
                    url: encodeURI(urlCard), type: "get", async: true, dataType: "json",
                    success: function cbf(data) {
                        var member = eval("(" + data + ")");
                        var SynIDCarder = member.SynIDCarder;
                        document.box1Form.reset();
                        //数据库中不存在记录
                        if (SynIDCarder == "0") {
                            $("#invite").hide();
                            $("#staffName", $form).val(SynIDCard1.NameA);

                            var birthday = SynIDCard1.Born;
                            birthday = birthday.substring(0, 4) + "-" + birthday.substring(4, 6) + "-" + birthday.substring(6, 8);
                            $("#birthday", $form).val(birthday);
                            $("#staffIdentityCard", $form).val(SynIDCard1.CardNo);
                            $("input#sex").val(SynIDCard1.SexL + "性");
                            $("img#photo", $form).attr("src", "");

                            $("#PhotoName").html(SynIDCard1.PhotoName);

                            //民族
                            $("#nation option").each(function () {
                                if ($(this).text().indexOf(SynIDCard1.NationL) > -1) {
                                    $(this).attr("selected", true);
                                    return;
                                }
                            });

                            address = SynIDCard1.Address;
                            //籍贯
                            $("#nativePlace option").each(function () {
                                if ($(this).text().indexOf(address.substring(0, 2)) > -1) {
                                    $(this).attr("selected", true);
                                    return;
                                }
                            });
                            personvalue = SynIDCard1.CardNo;
                        } else {
                            // 数据库中存在记录(根据记录的状态做不同的处理)
                            // ###此时添加改成编辑
                            staffsize = 1;
                            personIdentityCard = SynIDCard1.CardNo;
                            $("#staffCode", $form).val(SynIDCarder.staffCode);
                            $("#recordCode", $form).val(SynIDCarder.recordCode);
                            $("#staffName", $form).val(SynIDCarder.staffName);
                            $("#usedNmae", $form).val(SynIDCarder.usedNmae);
                            $("#sex", $form).val(SynIDCarder.sex);
                            $("#birthday", $form).val(SynIDCarder.birthday);
                            $("#nation", $form).val(SynIDCarder.nation);
                            $("#nativePlace", $form).val(SynIDCarder.nativePlace);
                            $("#staffIdentityCard", $form).val(SynIDCarder.staffIdentityCard);
                            $("#nationality", $form).val(SynIDCarder.nationality);
                            $("#reference", $form).val(SynIDCarder.reference);
                            $("#referenceCode", $form).val(SynIDCarder.referenceCode);
                            $("#PhotoName").html(SynIDCard1.PhotoName);

                            if (SynIDCarder.verifyTime) {
                                var verifyTime = SynIDCarder.verifyTime.time;
                                verifyTime = new Date(verifyTime);
                                verifyTime = verifyTime.getFullYear() + "-" + (verifyTime.getMonth() + 1) + "-" + verifyTime.getDate();
                                $("#verifyTime", $form).val(verifyTime);
                            }

                            $("#staffDesc", $form).val(SynIDCarder.staffDesc);
                            $("img#photo", $form).val(SynIDCarder.photo);
                            $("img#photo", $form).attr("src", basePath + SynIDCarder.photo);
                            $("table#stafftable").find('img#photo').show();
                            $("table#stafftable").find('#singleShuter').hide();
                            $("#schedulingID", $form).val(SynIDCarder.schedulingID);
                            $("#staffID", $form).val(SynIDCarder.staffID);
                            $("#staffKey", $form).val(SynIDCarder.staffKey);
                            personvalue = SynIDCarder.staffID;

                            if (SynIDCarder.address) {
                                var urldistrict = basePath + "ea/cstaff/sajax_n_ea_getPDetailsDistricts.jspa?districtPID=" + SynIDCarder.address + "&date=" + new Date().toLocaleString();
                                $.ajax({
                                    url: encodeURI(urldistrict), type: "get", async: true, dataType: "json",
                                    success: function cbf(data) {
                                        var member = eval("(" + data + ")");
                                        var distinctlistSaved = member.distinctlistSaved;
                                        var list = member.list;
                                        $select = "<option selected='selected'>--请选择--</option>";
                                        for (var i = 0; i < distinctlistSaved.length; i++) {
                                            if (i == 9) {
                                                return;
                                            }
                                            $td.children('select:eq(' + i + ')').empty();
                                            $td.children('select:eq(' + i + ')').append($select);
                                            for (var j = 0; j < list[i].length; j++) {
                                                $op = $("<option />");
                                                $op.attr("value", list[i][j].districtID).attr("id", list[i][j].districtID).text(list[i][j].districtName);
                                                $td.children('select:eq(' + i + ')').append($op);
                                            }

                                            $opp = $("<option  selected='selected'/>");
                                            $opp.attr("value", distinctlistSaved[i].districtID).attr("id", distinctlistSaved[i].districtID).text(distinctlistSaved[i].districtName);
                                            $td.children('select:eq(' + i + ')').append($opp);
                                            $add = "<option class='add'  value = '" + distinctlistSaved[i].districtPID + "' >--新增--</option>";
                                            $td.children('select:eq(' + i + ')').append($add);
                                        }
                                        $td.children('select:eq(' + distinctlistSaved.length + ')').append($select);

                                        for (var z = 0; z < list[distinctlistSaved.length].length; z++) {
                                            $op = $("<option />");
                                            $op.attr("value", list[distinctlistSaved.length][z].districtID).attr("id", list[distinctlistSaved.length][z].districtID).text(list[distinctlistSaved.length][z].districtName);
                                            $td.children('select:eq(' + distinctlistSaved.length + ')').append($op);
                                        }
                                        $addd = "<option class='add'  value = '" + distinctlistSaved[distinctlistSaved.length - 1].districtID + "' >--新增--</option>";
                                        $td.children('select:eq(' + distinctlistSaved.length + ')').append($addd);
                                    },
                                    error: function cbf(data) {
                                        // alert("数据获取失败！")
                                    }
                                });
                            }
                            $("#invite").show();
                        }
                    },
                    error: function cbf(data) {
                        alert("数据获取失败！");
                    }
                });
            } else {
                alert("身份证信息读取失败!请确认二代身份证已放到读卡器读卡区域,或与管理员联系。");
            }
        }

        //点击读取芯片号
        function chipRead() {
            $("#box1Form #loadcab").attr(
                "src",
                basePath + "page/ea/common/loadActiveX.html?code="
                + Math.random());


        }
        //正在读取芯片号
        function readChiping(values) {

            $("input#chipid").val(values);
        }
    </script>
</head>

<body style="overflow: auto;">
<div class="content" style="width:850px;height: auto;">
    <div class="contentbannb">
        <div class="divtx">&nbsp;社会人力管理</div>
        <table class="JQueryflexme" border="0">
            <tr>
                <td>
                </td>
            </tr>
        </table>
    </div>
    <table width="99%" border="0" align="center" cellpadding="0"
           cellspacing="0" class="biaoti box1">
        <tr>
            <td height="27" class="txt03">人员基本信息</td>
            <td align="right"><a href="#" onclick="changemenu('box1',1,'edit')" id="mord1"
                                 class="mord" style="color:#0066FF;">修改</a><a href="#"
                                                                              onclick="changemenu('box1',1,'close')"
                                                                              id="mord1_close"
                                                                              class="mord isHide"
                                                                              style="color:#0066FF;">取消</a>&nbsp;&nbsp;
            </td>
        </tr>
    </table>
    <p style="display: none;">
        <object classid="clsid:E6E0A751-541A-4855-9A8D-35EB7122C950"
                id="SynIDCard1" name="SynIDCard1"
                codeBase="<%=basePath%>WEB-INF/plug-in/SynIDCard.Cab#version=1,0,0,1"
                width="0" height="0">
            <param name="_Version" value="65536"/>
            <param name="_ExtentX" value="635"/>
            <param name="_ExtentY" value="582"/>
            <param name="_StockProps" value="0"/>
        </object>
        <textarea rows="17" name="S1" cols="82"></textarea>
    </p>
    <div id="box1">
        <form name="box1Form" id="box1Form" method="post"
              enctype="multipart/form-data">
            <s:token/>
            <table id="stafftable" width="99%" align="center" cellpadding="0"
                   cellspacing="0" class="table">
                <tr class="trheight">
                    <td style="height:30" align="right"><font color="red">*</font>姓名：</td>
                    <td done0="10" done1="10"><input name="cstaff.staffName" style="width:100%;height:100%;border: 0;"
                                                     class="put4 isHide" id="staffName" size="10"
                                                     value="${cstaff.staffName }"/>
                        <span class="isShow">${cstaff.staffName }</span>
                        <span id="staffID" style="display:none">${cstaff.staffID }</span>
                    </td>
                    <td width="12%" align="right">员工编号：</td>
                    <td width="23%"><input name="cstaff.staffCode" value="${cstaff.staffCode }"
                                           class="input isHide" id="staffCode" readonly="readonly" size="10"
                                           style="width:100%;height:100%;border: 0;"/>
                        <span class="isShow">${cstaff.staffCode }</span>
                    </td>
                    <td width="30%"  rowspan="7" align="center" id="phototd">
                        <object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000"
                                codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=9,0,0,0"
                                width="250" height="180" id="singleShuter" align="middle">
                            <param name="allowScriptAccess" value="sameDomain"/>
                            <param name="allowFullScreen" value="false"/>
                            <param name="FlashVars"
                                   value="servicesUrl=<%=basePath%>js/photo/save2.jsp"/>
                            <param name="movie"
                                   value="<%=basePath%>js/photo/singleShuter.swf"/>
                            <param name="quality" value="high"/>
                            <param name="bgcolor" value="#ffffff"/>
                            <param name="wmode" value="transparent"/> <!--是否透明-->
                            <embed src="<%=basePath%>js/photo/singleShuter.swf"
                                   FlashVars="servicesUrl=<%=basePath%>js/photo/save2.jsp"
                                   quality="high" bgcolor="#ffffff" width="250" height="180"
                                   name="singleShuter" align="middle"
                                   allowScriptAccess="sameDomain" allowFullScreen="false"
                                   type="application/x-shockwave-flash" wmode="transparent"
                                   pluginspage="http://www.macromedia.com/go/getflashplayer"/>
                        </object>
                        <img name="photos" id="photo" style="display: none;" src="xxx"
                             onload="setImg(this,   102,   126)"/>
                        <img id="idImg" style="display: none; width: 126px; height: 102px; " src="xxx"/>
                        <br/>
                        图片大小：102 x 126
                        <a id="PhotoName"></a>
                        <input id="singleShuterphoto" type="button" style="width: 50px;" class="isHide input-button"
                               value="摄像头"/><input name="photo" id="staffphoto" class="input01 isHide" type="file"
                                                   style="width: 150px;"/>
                        <input name="cstaff.photo" type="hidden" id="photo" value="${cstaff.photo}"/>
                        <input name="cstaff.staffID" id="staffID" type="hidden" value="${cstaff.staffID}"/>
                        <input name="cstaff.staffKey" id="staffKey" type="hidden" value="${cstaff.staffKey}"/>
                    </td>
                </tr>
                <tr>
                    <td style="height:30" align="right"><font color="red">*</font>身份证号码：</td>
                    <td><span class="isShow staffIdentityCard"
                              id="staffIdentityCard">${cstaff.staffIdentityCard }</span><input
                            name="cstaff.staffIdentityCard"
                            class="input isHide IdentityCard card put4" id="staffIdentityCard"
                            style="height:100%;border: 0;"
                            size="18" value="${cstaff.staffIdentityCard }"/>
                        <input type="button" name="ReadCardBtn" id="ReadCardBtn" style="cursor: pointer;" value="读取 "
                               onclick="autoRead();"/>
                    </td>
                    <td style="height:30" align="right">性别：</td>
                    <td done0="12" done1="12"><input name="cstaff.sex" style="width:100%;height:100%;border: 0;"
                                                     title='根据身份证自动生成' class="input isHide" id="sex" size="10"
                                                     value="${cstaff.sex }"
                                                     readonly="readonly"/><span class="isShow">${cstaff.sex }</span>
                    </td>
                </tr>
                <tr>
                    <td align="right">出生日期：</td>
                    <td done0="13" done1="13"><input name="cstaff.birthday" style="width:100%;height:100%;border: 0;"
                                                     title='根据身份证自动生成' class="input isHide" id="birthday" size="10"
                                                     value="${cstaff.birthday }"
                                                     readonly="readonly"/><span
                            class="isShow">${cstaff.birthday }</span></td>
                    <td style="height:30" align="right">民族：</td>
                    <td><s:select list="nations" listKey="codeValue" id="nation"
                                  listValue="codeValue" name="cstaff.nation" headerKey="汉族"
                                  headerValue="汉族" theme="simple"></s:select>
                        <span class="isShow">${cstaff.nation }</span>
                    </td>
                </tr>
                <tr>
                    <td style="height:30" align="right">联系方式：</td>
                    <td>
                        <input name="cstaff.reference" class="input isHide cellphone"
                               style="width:95px;height:100%;border: 0;"
                               id="reference" size="14" value="${cstaff.reference}"/><span
                            class="isShow">${cstaff.reference }</span>
                    </td>
                    <td style="height:30" align="right">QQ号：</td>
                    <td>
                        <input name="cstaff.referenceCode" class="input isHide"
                               style="width:100%;height:100%;border: 0;"
                               id="referenceCode" size="14" value="${cstaff.referenceCode}"/><span
                            class="isShow">${cstaff.referenceCode }</span>
                    </td>
                </tr>
                <tr>
                    <td align="right"><font color="red">*</font>人员多级分类：</td>
                    <td>
                        <s:select list="#{'00':'一般人物','01':'政界人物','02':'商界人物','03':'学术界人物','04':'艺术界人物','05':'科学界人物'}"
                                  listKey="key" id="status" listValue="value" name="cstaff.status"
                                  theme="simple"></s:select>
                        <span class="statusinfo" id="status">
					        	<c:if test="${cstaff.status eq '00' }">一般人物</c:if>
								<c:if test="${cstaff.status eq '01' }">政界人物</c:if></span>
                    </td>
                    <td align="right"><font color="red">*</font>人员往来关系：</td>
                    <td><select id="relationship"></select>
                        <input type="hidden" id="relationshipValue" name="relationshipValue"/>
                        <span id="relationshipValueAll">已有关系(<font
                                color="red">${relationshipValueAll==null||relationshipValueAll==''?'无':relationshipValueAll}</font>)</span>
                    </td>
                </tr>
                <tr>

                    <td style="height:30" align="right">信息类别：</td>
                    <td><select id="staus" class="tm isHide">
                        <option value="00" selected="selected">确定人员信息</option>
                        <option value="01">非确定人员信息</option>
                    </select><span class="statusinfo"><c:if
                            test="${cstaff.staffIdentityCard ne '' }">确定人员信息</c:if> <c:if
                            test="${cstaff.staffIdentityCard eq '' }">非确定人员信息</c:if></span></td>
                    <td style="height:30" align="right"><font color="red">*</font>芯片号：</td>
                    <td><input name="cstaff.chipid"
                               class="input isHide chip" id="chipid" style="width:100px;height:100%;border: 0;"
                               size="18" value="${cstaff.chipid}"/>
                        <input type="hidden" id="oldchipid" value="${cstaff.chipid}"/>
                        <span class="isShow chipid">${cstaff.chipid }</span>
                        <input type="button" class="isHide" style="cursor: pointer;" value=" 芯片号读取 "
                               onclick="chipRead();"/>
                        <iframe width="0" height="0" name="loadcab" id="loadcab"></iframe>
                    </td>
                </tr>
                <tr class="addtr isHide">
                    <td style="height:30" align="right">是否本地：</td>
                    <td><select id="isLocal" class="tm isHide" name="student.isLocal" value="${student.isLocal}">
                        <option value="1" >是</option>
                        <option value="0" selected="selected">否</option>
                    </select>
                        <span class="isShow"><c:if
                                test="${student.isLocal == '1' }">是</c:if> <c:if
                                test="${student.isLocal == '0' }">否</c:if></span>
                    </td>
                    <td style="height:30" align="right">地址：</td>
                    <td>
                        <input type="text" id="address" name = "student.address" class="isHide" value="${student.address}" />
                        <span class="isShow">${student.address}</span>
                    </td>
                </tr>
                <tr class="addtr isHide zadd">
                    <td style="height:30" align="right">暂住证号：</td>
                    <td>
                        <input type="text" id="tempCardNo" class="isHide" name = "student.tempCardNo" value="${student.tempCardNo}"/>
                        <span class="isShow">${student.tempCardNo}</span>
                    </td>
                    <td style="height:30" align="right">暂住地址：</td>
                    <td>
                       <input class="isHide" id="stayAddress" name = "student.stayAddress" value="${student.stayAddress}" />
                        <span class="isShow">${student.stayAddress}</span>
                    </td>
                </tr>

                <tr class="addtr isHide">
                    <td style="height:30" align="right">国籍：</td>
                    <td><select id="nationality" class="tm isHide" name = "student.nationality">
                        <option value="中国" selected="selected">中国</option>
                    </select>
                        <span class="isShow">${student.nationality}</span>
                    </td>
                </tr>

                <tr>
                    <td style="height:30" align="right">微分金账号：</td>
                    <td colspan="4"><span id="spanzh" >${cstaff.sccid != null&&cstaff.sccid ne ''?tcc.account:null}</span>
                        <input type="hidden" name="cstaff.sccid" value="${cstaff.sccid}" id="inputsccid"/>
                        <a id="wfjzh" href="javascript:" onclick="getValueForParm($(this).parent().parent().parent().attr('id'),1)"
                           class="isHide" style="color:#0066FF">选择</a>
                    </td>
                </tr>

                <tr class="coach isHide">
                <td style="height: 30;" align="right" >
                    驾驶证号：
                </td>
                <td>
                    <input type="text" class="input isHide" name="tbJpTeacher.drilicence" value="${tbJpTeacher.drilicence}">
                    <input type="hidden" name="tbJpTeacher.teacherKey" value="${tbJpTeacher.teacherKey}">
                    <span class="isShow">${tbJpTeacher.drilicence}</span>
                </td>
                <td style="height: 30;" align="right" >
                    日期：
                </td>
                <td>
                    <input type="text" onfocus="date(this);" class="input isHide chip" name="tbJpTeacher.fstdrilicdate" value="${tbJpTeacher.fstdrilicdate}">
                    <span class="isShow">${tbJpTeacher.fstdrilicdate}</span>
                </td>
                    <td>
                        准教车型：<span class="isShow">${tbJpTeacher.teachpermitted}</span>

                        <s:select list="#{'A1':'A1','A2':'A2','A3':'A3','B1':'B1','B2':'B2','C1':'C1','C2':'C2','C3':'C3','C4':'C4','C5':'C5','D':'D','E':'E','F':'F','M':'M','N':'N','P':'P'}"
                                  class="isHide" listKey="key" id="trainType" listValue="value" headerKey="" headerValue="请选择" name="tbJpTeacher.teachpermitted"
                                  theme="simple"></s:select>
                    </td>
                </tr>
                <tr class="coach isHide">
                    <td style="height: 30;" align="right">
                        职业资格等级：
                    </td>
                    <td>
                        <input type="text" class="input isHide" name="tbJpTeacher.occupationlevel" value="${tbJpTeacher.occupationlevel}">
                        <span class="isShow">${tbJpTeacher.occupationlevel}</span>
                    </td>
                    <td style="height:30" align="right" >
                        准驾车型：
                    </td>
                    <td>
                        <s:select list="#{'A1':'A1','A2':'A2','A3':'A3','B1':'B1','B2':'B2','C1':'C1','C2':'C2','C3':'C3','C4':'C4','C5':'C5','D':'D','E':'E','F':'F','M':'M','N':'N','P':'P'}"
                                  class="isHide" listKey="key" id="dripermitted" listValue="value" headerKey="" headerValue="请选择" name="tbJpTeacher.dripermitted"
                                  theme="simple"></s:select>
                        <span class="isShow">${tbJpTeacher.dripermitted}</span>
                    </td>
                    <td>
                        地址：
                        <input type="text" class="input isHide"  name="tbJpTeacher.address" value="${tbJpTeacher.address}">
                        <span class="isShow">${tbJpTeacher.address}</span>
                    </td>
                </tr>

                <tr class="coach isHide">
                    <td style="height:30" align="right" >
                        准教科目：
                    </td>
                    <td>
                        <s:select list="#{'1':'科目一','2':'科目二','3':'科目三','4':'科目四'}"
                                  class="isHide" listKey="key" id="subject" listValue="value" headerKey="" headerValue="请选择" name="tbJpTeacher.subject"
                                  theme="simple"></s:select>
                        <span class="isShow">
                            <c:if test="${tbJpTeacher.subject eq '1' }">科目一</c:if>
                            <c:if test="${tbJpTeacher.subject eq '2' }">科目二</c:if>
                            <c:if test="${tbJpTeacher.subject eq '3' }">科目三</c:if>
                            <c:if test="${tbJpTeacher.subject eq '4' }">科目四</c:if>
                        </span>
                    </td>
                </tr>
                    <%--<td style="height: 30;" align="right" >--%>
                        <%--入职时间：--%>
                    <%--</td>--%>
                    <%--<td>--%>
                        <%--<input type="text" class="input isHide" name="tbJpTeacher.hiredate" onfocus="date(this);">--%>
                        <%--<span class="isShow">${tbJpTeacher.hiredate}</span>--%>
                    <%--</td>--%>
                    <%--<td style="height:30" align="right">--%>
                        <%--离职时间：--%>
                    <%--</td>--%>
                    <%--<td>--%>
                        <%--<input type="text" class="input isHide"  name="tbJpTeacher.leavedate" onfocus="date(this);">--%>
                        <%--<span class="isShow">${tbJpTeacher.leavedate}</span>--%>
                    <%--</td>--%>
                    <%--<td>--%>
                        <%--供职状态：<input type="text" class="input isHide" name="tbJpTeacher.employstatus">--%>
                        <%--<span class="isShow">${tbJpTeacher.employstatus}</span>--%>
                    <%--</td>--%>
                <%--</tr>--%>
                <tr class="biaoti studentinfo isHide">
                    <td colspan="5" height="27" style="color: #ebf0f7;padding-left: 25px;">学员报名信息</td>
                </tr>
                <tr class="trheight studentinfo isHide">
                    <td style="height:30" align="right"><font color="red"></font>报名驾校：</td>
                    <td done0="10" done1="10">
                        <input value="${enroll.companyName}" class="isHide" id="company"  readonly="readonly">
                        <input value="${student.studentId}" type="hidden" name="student.studentId">
                        <input value="${student.studentKey}" type="hidden" name="student.studentKey">
                        <input value="${student.companyId}" type="hidden" id="companyID" name="student.companyId">
                        <span class="isShow">${enroll.companyName}</span>
                    </td>
                    <td width="12%" align="right">报名日期：</td>
                    <td width="23%">
                        <input value="${student.applyDate}" class="isHide" id="applydate" onfocus="date(this);"
                               name="student.applyDate"
                        >
                        <span class="isShow">${student.applyDate}</span>
                    </td>
                    <td>培训车型：<span class="isShow"
                              id="licenceType">${student.trainType}</span>
                        <s:select list="#{'A1':'A1','A2':'A2','A3':'A3','B1':'B1','B2':'B2','C1':'C1','C2':'C2','C3':'C3','C4':'C4','C5':'C5','D':'D','E':'E','F':'F','M':'M','N':'N','P':'P'}"
                                  class="isHide" listKey="key" id="trainTypes" listValue="value" headerKey="" headerValue="请选择" name="student.trainType"
                                  theme="simple"></s:select>
                    </td>
                </tr>
                <tr class="studentinfo isHide">

                    <td style="height:30" align="right">付款方式：</td>
                    <td done0="12" done1="12"><input name="student.charId" style="width:100%;height:100%;border: 0;"
                                                     title='' class="input isHide" id="" size="10"
                                                     value=""
                                                     readonly="readonly"/><span class="isShow"></span>
                    </td>
                    <td align="right">申请类型：</td>
                    <td done0="13" done1="13">
                        <s:select list="#{'1':'初领','2':'增领'}"
                                  class="isHide" listKey="key" id="busiType" listValue="value" headerKey="" headerValue="请选择" name="student.busiType"
                                  theme="simple"></s:select>
                        <span class="isShow">
					        	<c:if test="${student.busiType eq '1' }">初领</c:if>
								<c:if test="${student.busiType eq '2' }">增领</c:if></span>
                    </td>
                    <td id="perdriType">原准驾车型：<span class="isShow"
                                   id="licenceType">${student.perdriType}</span>
                        <s:select list="#{'A1':'A1','A2':'A2','A3':'A3','B1':'B1','B2':'B2','C1':'C1','C2':'C2','C3':'C3','C4':'C4','C5':'C5','D':'D','E':'E','F':'F','M':'M','N':'N','P':'P'}"
                                  class="isHide" listKey="key" id="perdriType" listValue="value" headerKey="" headerValue="请选择" name="student.perdriType"
                                  theme="simple"></s:select>
                    </td>
                </tr>
                <tr id="tools" style="display:table-row;border:0;">
                    <td style="height:30" align="right" colspan="5">
                        <input type="button" onclick="toSave('box1Form','/ea/humanResource/ea_saveCStaff.jspa?')"
                               class="input-button JQuerySubmit isHide" style="cursor: pointer; width: 80px;"
                               value="提交"/>
                    </td>
                </tr>
            </table>
        </form>
    </div>


    <%--<table width="99%" border="0" align="center" cellpadding="0"--%>
           <%--cellspacing="0" class="biaoti box18" style="display: none" id ="studenttable">--%>
        <%--<tr>--%>
            <%--<td height="27" class="txt03">学员报名信息</td>--%>
        <%--</tr>--%>
    <%--</table>--%>
    <%--<div id="box18" style="display: none">--%>
        <%--<form name="box18Form" id="box18Form" method="post"--%>
              <%--enctype="multipart/form-data">--%>
            <%--<s:token/>--%>
            <%--<table id="stafftable" width="99%" align="center" cellpadding="0"--%>
                   <%--cellspacing="0" class="table">--%>
                <%--<tr class="trheight">--%>
                    <%--<td style="height:30" align="right"><font color="red"></font>报名驾校：</td>--%>
                    <%--<td done0="10" done1="10">--%>
                        <%--<input value="${enroll.companyName}" class="isHide" id="company"  readonly="readonly">--%>
                        <%--<input value="${student.studentId}" type="hidden" name="student.studentId">--%>
                        <%--<input value="${student.studentKey}" type="hidden" name="student.studentKey">--%>
                        <%--<input value="${student.companyId}" type="hidden" id="companyID" name="student.companyId">--%>
                        <%--<span class="isShow">${enroll.companyName}</span>--%>
                    <%--</td>--%>
                    <%--<td width="12%" align="right">报名日期：</td>--%>
                    <%--<td width="23%">--%>
                        <%--<input value="${student.applyDate}" class="isHide" id="applydate" onfocus="date(this);"--%>
                                           <%--name="student.applyDate"--%>
                                           <%-->--%>
                        <%--<span class="isShow">${student.applyDate}</span>--%>
                    <%--</td>--%>
                    <%--<td style="height:30" align="right"><font color="red">*</font>培训车型：</td>--%>
                    <%--<td><span class="isShow"--%>
                              <%--id="licenceType">${student.trainType}</span>--%>
                        <%--<s:select list="#{'A1':'A1','A2':'A2','A3':'A3','B1':'B1','B2':'B2','C1':'C1','C2':'C2','C3':'C3','C4':'C4','C5':'C5','D':'D','E':'E','F':'F','M':'M','N':'N','P':'P'}"--%>
                                  <%--class="isHide" listKey="key" id="trainType" listValue="value" name="student.trainType"--%>
                                  <%--theme="simple"></s:select>--%>
                    <%--</td>--%>
                <%--</tr>--%>
                <%--<tr>--%>

                    <%--<td style="height:30" align="right">付款方式：</td>--%>
                    <%--<td done0="12" done1="12"><input name="student.charId" style="width:100%;height:100%;border: 0;"--%>
                                                     <%--title='' class="input isHide" id="" size="10"--%>
                                                     <%--value=""--%>
                                                     <%--readonly="readonly"/><span class="isShow"></span>--%>
                    <%--</td>--%>
                    <%--<td align="right">申请类型：</td>--%>
                    <%--<td done0="13" done1="13"><input name="" style="width:100%;height:100%;border: 0;"--%>
                                                     <%--title='' class="input isHide" size="10"--%>
                                                     <%--value=""--%>
                                                     <%--readonly="readonly"/><span--%>
                            <%--class="isShow"></span>--%>
                    <%--</td>--%>
                    <%--<td></td>--%>
                    <%--<td></td>--%>
                <%--</tr>--%>
                <%--&lt;%&ndash;<tr id="tools" style="display:table-row;border:0;">&ndash;%&gt;--%>
                    <%--&lt;%&ndash;<td style="height:30" align="right" colspan="6">&ndash;%&gt;--%>
                        <%--&lt;%&ndash;<input type="button" onclick="toSave('box1Form','box18Form','/ea/humanResource/ea_saveCStaff.jspa')"&ndash;%&gt;--%>
                               <%--&lt;%&ndash;class="input-button JQuerySubmit isHide" style="cursor: pointer; width: 80px;"&ndash;%&gt;--%>
                               <%--&lt;%&ndash;value="提交"/>&ndash;%&gt;--%>
                    <%--&lt;%&ndash;</td>&ndash;%&gt;--%>
                <%--</tr>--%>
            <%--</table>--%>
        <%--</form>--%>
    <%--</div>--%>


    <div class="menu01">
        <form name="humanForm" id="humanForm" method="post">
            <ul>
                <li>
                    <input type="submit" name="submit" style="display: none"/>
                    <input name="humanresource.humanresourcekey" value="${humanresource.humanresourcekey}"
                           style="display: none"/>
                    <input name="humanresource.humanresourceid" value="${humanresource.humanresourceid}"
                           style="display: none"/>
                    <input name="humanresource.companyid" value="${humanresource.companyid}" style="display: none"/>
                    <ul class="menu00" style="z-index:100;">
                        <li>
                            <input type="checkbox" class="oroupboxAll"/>全选
                        </li>
                        <li>
                            <input type="checkbox" name="humanresource.staffaddress" id="staffaddress" value="1"/>地址管理
                        </li>
                        <li>
                            <input type="checkbox" name="humanresource.staffcontact" id="staffcontact" value="1"/>联系方式
                        </li>
                        <li>
                            <input type="checkbox" name="humanresource.staffeducation" id="staffeducation" value="1"/>学历学位
                        </li>
                        <li>
                            <input type="checkbox" name="humanresource.staffresume" id="staffresume" value="1"/>个人履历
                        </li>
                        <li>
                            <input type="checkbox" name="humanresource.stafffamilymember" id="stafffamilymember"
                                   value="1"/>家庭成员
                        </li>
                        <li>
                            <input type="checkbox" name="humanresource.staffphysicalcondition"
                                   id="staffphysicalcondition" value="1"/>健康状况
                        </li>
                        <li>
                            <input type="checkbox" name="humanresource.staffpoliticalstatus" id="staffpoliticalstatus"
                                   value="1"/>政治面貌
                        </li>
                        <li>
                            <input type="checkbox" name="humanresource.staffencourage" id="staffencourage" value="1"/>奖励情况
                        </li>
                        <li>
                            <input type="checkbox" name="humanresource.staffpunishment" id="staffpunishment" value="1"/>处分情况
                        </li>
                        <li>
                            <input type="checkbox" name="humanresource.staffinsurance" id="staffinsurance" value="1"/>社会保险
                        </li>
                        <li>
                            <input type="checkbox" name="humanresource.staffinvestigation" id="staffinvestigation"
                                   value="1"/>调查情况
                        </li>
                        <li>
                            <input type="checkbox" name="humanresource.staffcertificate" id="staffcertificate"
                                   value="1"/>证件列表
                        </li>
                        <li>
                            <input type="checkbox" name="humanresource.staffdocumentation" id="staffdocumentation"
                                   value="1"/>资料列表
                        </li>
                        <li>
                            <input type="checkbox" name="humanresource.staffpersonalfile" id="staffpersonalfile"
                                   value="1"/>人事档案
                        </li>
                        <li>
                            <input type="checkbox" name="humanresource.staffbankaccount" id="staffbankaccount"
                                   value="1"/>银行账号
                        </li>
                        <li>
                            <input type="checkbox" name="humanresource.staffagreement" id="staffagreement" value="1"/>合同管理
                        </li>
                        <li>
                            <input type="button" class="input-button JQuerySubmits"
                                   style="cursor: pointer; margin-top:0px; width: 60px;" value="保存"/>
                        </li>
                    </ul>
                </li>
            </ul>
        </form>
    </div>

    <div style="overflow-y:scroll;" class="gdkd">

        <div name="staffaddress" id="${humanresource.staffaddress}" class="showorhide" style="display: none;">
            <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box2">
                <tr>
                    <td height="27" class="txt03">地址管理</td>
                    <td align="right"><a href="javascript:" onclick="changemenu('box2',2,'edit')" id="mord2"
                                         class="mord" style="color:#0066FF">编辑</a><a href="#"
                                                                                     onclick="changemenu('box2',2,'close')"
                                                                                     id="mord2_close"
                                                                                     class="mord isHide"
                                                                                     style="color:#0066FF;">取消</a>&nbsp;&nbsp;
                    </td>
                </tr>
            </table>
            <div id="box2" style="display:none;">
                <form name="box2Form" id="box2Form" method="post">

                    <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table contact">
                        <tr>
                            <td>
                                <input type="submit" name="submit" style="display: none"/>
                                <div style="width: 100%;">
                                    <iframe url="ea/csaddress/ea_getListAddress.jspa?address.staffID="
                                            src="" name="main" height="80px" width="100%" marginwidth="0"
                                            marginheight="0" scrolling="no" frameborder="0"
                                            id="mainframe2" border="0" framespacing="0" noresize="noResize"
                                            vspale="0">
                                    </iframe>
                                </div>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>

        <div name="staffcontact" dir="ltr" id="${humanresource.staffcontact}" class="showorhide" style="display: none;">
            <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box3">
                <tr>
                    <td height="27" class="txt03">联系方式</td>
                    <td align="right"><a href="javascript:" onclick="changemenu('box3',3,'edit')" id="mord3"
                                         class="mord" style="color:#0066FF">编辑</a><a href="#"
                                                                                     onclick="changemenu('box3',3,'close')"
                                                                                     id="mord3_close"
                                                                                     class="mord isHide"
                                                                                     style="color:#0066FF;">取消</a>&nbsp;&nbsp;
                    </td>
                </tr>
            </table>
            <div id="box3" style="display:none;">
                <form name="box3Form" id="box3Form" method="post">

                    <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table contact">
                        <tr>
                            <td>
                                <input type="submit" name="submit" style="display: none"/>
                                <div style="width: 100%;">
                                    <iframe url="ea/contact/ea_getListContact.jspa?contact.staffID="
                                            src="" name="main" width="100%" marginwidth="0"
                                            height="80px" marginheight="0" scrolling="no" frameborder="0"
                                            id="mainframe3" border="0" framespacing="0" noresize="noResize"
                                            vspale="0">
                                    </iframe>
                                </div>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>

        <div name="staffeducation" id="${humanresource.staffeducation}" class="showorhide" style="display: none;">
            <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box4">
                <tr>
                    <td height="27" class="txt03">学历学位</td>
                    <td align="right"><a href="javascript:" onclick="changemenu('box4',4,'edit')" id="mord4"
                                         class="mord" style="color:#0066FF">编辑</a><a href="#"
                                                                                     onclick="changemenu('box4',4,'close')"
                                                                                     id="mord4_close"
                                                                                     class="mord isHide"
                                                                                     style="color:#0066FF;">取消</a>&nbsp;&nbsp;
                    </td>
                </tr>
            </table>
            <div id="box4" style="display:none;">
                <form name="box4Form" id="box4Form" method="post">

                    <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table contact">
                        <tr>
                            <td>
                                <input type="submit" name="submit" style="display: none"/>
                                <div style="width: 100%;">
                                    <iframe url="ea/education/ea_getListEducation.jspa?education.staffID="
                                            src="" name="main" width="100%" marginwidth="0"
                                            height="80px" marginheight="0" scrolling="no" frameborder="0"
                                            id="mainframe4" border="0" framespacing="0" noresize="noResize"
                                            vspale="0">
                                    </iframe>
                                </div>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>

        <div name="staffresume" id="${humanresource.staffresume}" class="showorhide" style="display: none;">
            <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box5">
                <tr>
                    <td height="27" class="txt03">个人履历</td>
                    <td align="right"><a href="javascript:" onclick="changemenu('box5',5,'edit')" id="mord5"
                                         class="mord" style="color:#0066FF">编辑</a><a href="#"
                                                                                     onclick="changemenu('box5',5,'close')"
                                                                                     id="mord5_close"
                                                                                     class="mord isHide"
                                                                                     style="color:#0066FF;">取消</a>&nbsp;&nbsp;
                    </td>
                </tr>
            </table>
            <div id="box5" style="display:none;">
                <form name="box5Form" id="box5Form" method="post">

                    <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table contact">
                        <tr>
                            <td>
                                <input type="submit" name="submit" style="display: none"/>
                                <div style="width: 100%;">
                                    <iframe url="ea/precord/ea_getListPRecord.jspa?record.staffID="
                                            src="" name="main" width="100%" marginwidth="0"
                                            height="80px" marginheight="0" scrolling="no" frameborder="0"
                                            id="mainframe5" border="0" framespacing="0" noresize="noResize"
                                            vspale="0">
                                    </iframe>
                                </div>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>

        <div name="stafffamilymember" id="${humanresource.stafffamilymember}" class="showorhide" style="display: none;">
            <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box6">
                <tr>
                    <td height="27" class="txt03">家庭成员</td>
                    <td align="right"><a href="javascript:" onclick="changemenu('box6',6,'edit')" id="mord6"
                                         class="mord" style="color:#0066FF">编辑</a><a href="#"
                                                                                     onclick="changemenu('box6',6,'close')"
                                                                                     id="mord6_close"
                                                                                     class="mord isHide"
                                                                                     style="color:#0066FF;">取消</a>&nbsp;&nbsp;
                    </td>
                </tr>
            </table>
            <div id="box6" style="display:none;">
                <form name="box6Form" id="box6Form" method="post">

                    <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table contact">
                        <tr>
                            <td>
                                <input type="submit" name="submit" style="display: none"/>
                                <div style="width: 100%;">
                                    <iframe url="ea/fmember/ea_getListFMember.jspa?member.staffID="
                                            src="" name="main" width="100%" marginwidth="0"
                                            height="80px" marginheight="0" scrolling="no" frameborder="0"
                                            id="mainframe6" border="0" framespacing="0" noresize="noResize"
                                            vspale="0">
                                    </iframe>
                                </div>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>

        <div name="staffphysicalcondition" id="${humanresource.staffphysicalcondition}" class="showorhide"
             style="display: none;">
            <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box7">
                <tr>
                    <td height="27" class="txt03">健康状况</td>
                    <td align="right"><a href="javascript:" onclick="changemenu('box7',7,'edit')" id="mord7"
                                         class="mord" style="color:#0066FF">编辑</a><a href="#"
                                                                                     onclick="changemenu('box7',7,'close')"
                                                                                     id="mord7_close"
                                                                                     class="mord isHide"
                                                                                     style="color:#0066FF;">取消</a>&nbsp;&nbsp;
                    </td>
                </tr>
            </table>
            <div id="box7" style="display:none;">
                <form name="box7Form" id="box7Form" method="post">

                    <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table contact">
                        <tr>
                            <td>
                                <input type="submit" name="submit" style="display: none"/>
                                <div style="width: 100%;">
                                    <iframe url="ea/pcondition/ea_getListPCondition.jspa?condition.staffID="
                                            src="" name="main" width="100%" marginwidth="0"
                                            height="80px" marginheight="0" scrolling="no" frameborder="0"
                                            id="mainframe7" border="0" framespacing="0" noresize="noResize"
                                            vspale="0">
                                    </iframe>
                                </div>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>

        <div name="staffpoliticalstatus" id="${humanresource.staffpoliticalstatus}" class="showorhide"
             style="display: none;">
            <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box8">
                <tr>
                    <td height="27" class="txt03">政治面貌</td>
                    <td align="right"><a href="javascript:" onclick="changemenu('box8',8,'edit')" id="mord8"
                                         class="mord" style="color:#0066FF">编辑</a><a href="#"
                                                                                     onclick="changemenu('box8',8,'close')"
                                                                                     id="mord8_close"
                                                                                     class="mord isHide"
                                                                                     style="color:#0066FF;">取消</a>&nbsp;&nbsp;
                    </td>
                </tr>
            </table>
            <div id="box8" style="display:none;">
                <form name="box8Form" id="box8Form" method="post">

                    <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table contact">
                        <tr>
                            <td>
                                <input type="submit" name="submit" style="display: none"/>
                                <div style="width: 100%;">
                                    <iframe url="ea/political/ea_getListPolitical.jspa?political.staffID="
                                            src="" name="main" width="100%" marginwidth="0"
                                            height="80px" marginheight="0" scrolling="no" frameborder="0"
                                            id="mainframe8" border="0" framespacing="0" noresize="noResize"
                                            vspale="0">
                                    </iframe>
                                </div>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>

        <div name="staffencourage" id="${humanresource.staffencourage}" class="showorhide" style="display: none;">
            <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box9">
                <tr>
                    <td height="27" class="txt03">奖励情况</td>
                    <td align="right"><a href="javascript:" onclick="changemenu('box9',9,'edit')" id="mord9"
                                         class="mord" style="color:#0066FF">编辑</a><a href="#"
                                                                                     onclick="changemenu('box9',9,'close')"
                                                                                     id="mord9_close"
                                                                                     class="mord isHide"
                                                                                     style="color:#0066FF;">取消</a>&nbsp;&nbsp;
                    </td>
                </tr>
            </table>
            <div id="box9" style="display:none;">
                <form name="box9Form" id="box9Form" method="post">

                    <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table contact">
                        <tr>
                            <td>
                                <input type="submit" name="submit" style="display: none"/>
                                <div style="width: 100%;">
                                    <iframe url="ea/encourage/ea_getListEncourage.jspa?encourage.staffID="
                                            src="" name="main" width="100%" marginwidth="0"
                                            height="80px" marginheight="0" scrolling="no" frameborder="0"
                                            id="mainframe9" border="0" framespacing="0" noresize="noResize"
                                            vspale="0">
                                    </iframe>
                                </div>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>

        <div name="staffpunishment" id="${humanresource.staffpunishment}" class="showorhide" style="display: none;">
            <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box10">
                <tr>
                    <td height="27" class="txt03">处分情况</td>
                    <td align="right"><a href="javascript:" onclick="changemenu('box10',10,'edit')" id="mord10"
                                         class="mord" style="color:#0066FF">编辑</a><a href="#"
                                                                                     onclick="changemenu('box10',10,'close')"
                                                                                     id="mord10_close"
                                                                                     class="mord isHide"
                                                                                     style="color:#0066FF;">取消</a>&nbsp;&nbsp;
                    </td>
                </tr>
            </table>
            <div id="box10" style="display:none;">
                <form name="box10Form" id="box10Form" method="post">

                    <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table contact">
                        <tr>
                            <td>
                                <input type="submit" name="submit" style="display: none"/>
                                <div style="width: 100%;">
                                    <iframe url="ea/punishment/ea_getListPunishment.jspa?punishment.staffID="
                                            src="" name="main" width="100%" marginwidth="0"
                                            height="80px" marginheight="0" scrolling="no" frameborder="0"
                                            id="mainframe10" border="0" framespacing="0" noresize="noResize"
                                            vspale="0">
                                    </iframe>
                                </div>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>

        <div name="staffinsurance" id="${humanresource.staffinsurance}" class="showorhide" style="display: none;">
            <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box11">
                <tr>
                    <td height="27" class="txt03">社会保险</td>
                    <td align="right"><a href="javascript:" onclick="changemenu('box11',11,'edit')" id="mord11"
                                         class="mord" style="color:#0066FF">编辑</a><a href="#"
                                                                                     onclick="changemenu('box11',11,'close')"
                                                                                     id="mord11_close"
                                                                                     class="mord isHide"
                                                                                     style="color:#0066FF;">取消</a>&nbsp;&nbsp;
                    </td>
                </tr>
            </table>
            <div id="box11" style="display:none;">
                <form name="box11Form" id="box11Form" method="post">

                    <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table contact">
                        <tr>
                            <td>
                                <input type="submit" name="submit" style="display: none"/>
                                <div style="width: 100%;">
                                    <iframe url="ea/insurance/ea_getListInsurance.jspa?insurance.staffID="
                                            src="" name="main" width="100%" marginwidth="0"
                                            height="80px" marginheight="0" scrolling="no" frameborder="0"
                                            id="mainframe11" border="0" framespacing="0" noresize="noResize"
                                            vspale="0">
                                    </iframe>
                                </div>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>

        <div name="staffinvestigation" id="${humanresource.staffinvestigation}" class="showorhide"
             style="display: none;">
            <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box12">
                <tr>
                    <td height="27" class="txt03">调查情况</td>
                    <td align="right"><a href="javascript:" onclick="changemenu('box12',12,'edit')" id="mord12"
                                         class="mord" style="color:#0066FF">编辑</a><a href="#"
                                                                                     onclick="changemenu('box12',12,'close')"
                                                                                     id="mord12_close"
                                                                                     class="mord isHide"
                                                                                     style="color:#0066FF;">取消</a>&nbsp;&nbsp;
                    </td>
                </tr>
            </table>
            <div id="box12" style="display:none;">
                <form name="box12Form" id="box12Form" method="post">

                    <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table contact">
                        <tr>
                            <td>
                                <input type="submit" name="submit" style="display: none"/>
                                <div style="width: 100%;">
                                    <iframe url="ea/investigation/ea_getListInvestigation.jspa?investigation.staffID="
                                            src="" name="main" width="100%" marginwidth="0"
                                            height="80px" marginheight="0" scrolling="no" frameborder="0"
                                            id="mainframe12" border="0" framespacing="0" noresize="noResize"
                                            vspale="0">
                                    </iframe>
                                </div>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>

        <div name="staffcertificate" id="${humanresource.staffcertificate}" class="showorhide" style="display: none;">
            <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box13">
                <tr>
                    <td height="27" class="txt03">证件列表</td>
                    <td align="right"><a href="javascript:" onclick="changemenu('box13',13,'edit')" id="mord13"
                                         class="mord" style="color:#0066FF">编辑</a><a href="#"
                                                                                     onclick="changemenu('box13',13,'close')"
                                                                                     id="mord13_close"
                                                                                     class="mord isHide"
                                                                                     style="color:#0066FF;">取消</a>&nbsp;&nbsp;
                    </td>
                </tr>
            </table>
            <div id="box13" style="display:none;">
                <form name="box13Form" id="box13Form" method="post">

                    <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table contact">
                        <tr>
                            <td>
                                <input type="submit" name="submit" style="display: none"/>
                                <div style="width: 100%;">
                                    <iframe url="ea/credentials/ea_getListCredentials.jspa?credentials.staffID="
                                            src="" name="main" width="100%" marginwidth="0"
                                            height="80px" marginheight="0" scrolling="no" frameborder="0"
                                            id="mainframe13" border="0" framespacing="0" noresize="noResize"
                                            vspale="0">
                                    </iframe>
                                </div>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>

        <div name="staffdocumentation" id="${humanresource.staffdocumentation}" class="showorhide"
             style="display: none;">
            <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box14">
                <tr>
                    <td height="27" class="txt03">资料列表</td>
                    <td align="right"><a href="javascript:" onclick="changemenu('box14',14,'edit')" id="mord14"
                                         class="mord" style="color:#0066FF">编辑</a><a href="#"
                                                                                     onclick="changemenu('box14',14,'close')"
                                                                                     id="mord14_close"
                                                                                     class="mord isHide"
                                                                                     style="color:#0066FF;">取消</a>&nbsp;&nbsp;
                    </td>
                </tr>
            </table>
            <div id="box14" style="display:none;">
                <form name="box14Form" id="box14Form" method="post">

                    <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table contact">
                        <tr>
                            <td>
                                <input type="submit" name="submit" style="display: none"/>
                                <div style="width: 100%;">
                                    <iframe url="ea/documentation/ea_getListDocumentation.jspa?documentation.staffID="
                                            src="" name="main" width="100%" marginwidth="0"
                                            height="80px" marginheight="0" scrolling="no" frameborder="0"
                                            id="mainframe14" border="0" framespacing="0" noresize="noResize"
                                            vspale="0">
                                    </iframe>
                                </div>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>

        <div name="staffpersonalfile" id="${humanresource.staffpersonalfile}" class="showorhide" style="display: none;">
            <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box15">
                <tr>
                    <td height="27" class="txt03">人事档案</td>
                    <td align="right"><a href="javascript:" onclick="changemenu('box15',15,'edit')" id="mord15"
                                         class="mord" style="color:#0066FF">编辑</a><a href="#"
                                                                                     onclick="changemenu('box15',15,'close')"
                                                                                     id="mord15_close"
                                                                                     class="mord isHide"
                                                                                     style="color:#0066FF;">取消</a>&nbsp;&nbsp;
                    </td>
                </tr>
            </table>
            <div id="box15" style="display:none;">
                <form name="box15Form" id="box15Form" method="post">

                    <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table contact">
                        <tr>
                            <td>
                                <input type="submit" name="submit" style="display: none"/>
                                <div style="width: 100%;">
                                    <iframe url="ea/personalrecord/ea_getListPersonalRecord.jspa?personalRecord.staffID="
                                            src="" name="main" width="100%" marginwidth="0"
                                            height="80px" marginheight="0" scrolling="no" frameborder="0"
                                            id="mainframe15" border="0" framespacing="0" noresize="noResize"
                                            vspale="0">
                                    </iframe>
                                </div>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>

        <div name="staffbankaccount" id="${humanresource.staffbankaccount}" class="showorhide" style="display: none;">
            <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box16">
                <tr>
                    <td height="27" class="txt03">银行账号</td>
                    <td align="right"><a href="javascript:" onclick="changemenu('box16',16,'edit')" id="mord16"
                                         class="mord" style="color:#0066FF">编辑</a><a href="#"
                                                                                     onclick="changemenu('box16',16,'close')"
                                                                                     id="mord16_close"
                                                                                     class="mord isHide"
                                                                                     style="color:#0066FF;">取消</a>&nbsp;&nbsp;
                    </td>
                </tr>
            </table>
            <div id="box16" style="display:none;">
                <form name="box16Form" id="box16Form" method="post">

                    <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table contact">
                        <tr>
                            <td>
                                <input type="submit" name="submit" style="display: none"/>
                                <div style="width: 100%;">
                                    <iframe url="ea/bankaccount/ea_getListBankAccount.jspa?bankAccount.staffID="
                                            src="" name="main" width="100%" marginwidth="0"
                                            height="80px" marginheight="0" scrolling="no" frameborder="0"
                                            id="mainframe16" border="0" framespacing="0" noresize="noResize"
                                            vspale="0">
                                    </iframe>
                                </div>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>
        <div name="staffagreement" id="${humanresource.staffagreement}" class="showorhide" style="display: none;">
            <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="biaoti box17">
                <tr>
                    <td height="27" class="txt03">合同管理</td>
                    <td align="right"><a href="javascript:" onclick="changemenu('box17',17,'edit')" id="mord17"
                                         class="mord" style="color:#0066FF">编辑</a><a href="#"
                                                                                     onclick="changemenu('box17',17,'close')"
                                                                                     id="mord17_close"
                                                                                     class="mord isHide"
                                                                                     style="color:#0066FF;">取消</a>&nbsp;&nbsp;
                    </td>
                </tr>
            </table>
            <div id="box17" style="display:none;">
                <form name="box17Form" id="box17Form" method="post">

                    <table width="99%" border="0" align="center" cellpadding="0" cellspacing="0" class="table contact">
                        <tr>
                            <td>
                                <input type="submit" name="submit" style="display: none"/>
                                <div style="width: 100%;">
                                    <iframe url="ea/agreement/ea_getListAgreement.jspa?agreement.staffID="
                                            src="" name="main" width="100%" marginwidth="0"
                                            height="80px" marginheight="0" scrolling="no" frameborder="0"
                                            id="mainframe17" border="0" framespacing="0" noresize="noResize"
                                            vspale="0">
                                    </iframe>
                                </div>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>


    </div>
</div>

<div id="jqmWindow2" class="jqmWindow"
     style="width: 700px; height: 320px;  display: none; left: 30%; top: 15%; background: #eff">
    <div align="center">
        <iframe name="ifr" id="ifr" width="100%" height="280px"
                frameborder="0"></iframe>
        <input type="button" class="input-button" id="isSubmit" value=" 确定 "
               style="cursor: hand" />
        <input type="button" class="input-button" id="isBack" value=" 关闭 "
               style="cursor: hand" />
    </div>
</div>
<script type="text/javascript">
    var ip = new ImagePreview($$("staffphoto"), $$("idImg"), {
        maxWidth: 200, maxHeight: 200, action: ""
    });
    ip.img.src = ImagePreview.TRANSPARENT;
    ip.file.onchange = function () {
        ip.preview();
        window.setTimeout('setwh(document.getElementById("idImg"))', 200);
    };

    function setwh(img) {
        //if(img.width != 102 || img.height !=126||img.fileSize > 100*1024){
        //   alert("上传图片规格必须为102X126！ 大小必须小于100k");
        //    photosizes = 1;
        //    $("table#stafftable").find('img#idImg').attr("src", "xxx");
        //    $("table#stafftable").find('img#idImg').css("filter","progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='scale',src='')");
        //}else{
        //   photosizes = 0;
        //}
        photosizes = 0;
    }

    function setImageUrl(str) {
        $("#hideImagePath").value = str;
    }

    function gotoLogin() {
        document.location = basePath + "page/ea/not_login.jsp";
    }

    function setImg(img, width, height) {
        var s1 = width / height;
        var s2 = img.offsetWidth / img.offsetHeight;
        if (s1 > s2) img.height = img.offsetHeight > height ? height : img.offsetHeight;
        else     img.width = img.offsetWidth > width ? width : img.offsetWidth;
    }

    $(function () {
        if($("select[name='student.busiType'] option:selected").text() == "初领"){
            $("#perdriType").hide();
        }

        $("select[name='student.busiType']").change(function () {
            if($("select[name='student.busiType']  option:selected").text() == "初领"){
                $("select[name='student.perdriType'] option[value='C1']").attr("selected",true)
                $("#perdriType").hide();
            }else if($("select[name='student.busiType']  option:selected").text() == "增领"){
                $("#perdriType").show();
            }
        })


    })

    $(function () {
        setTimeout(function () {
            $("div.gdkd").css({"height": GetPageSize()[3] - 350 + "px"});
        }, 100);
        $(window).resize(function () {
            setTimeout(function () {
                $("div.gdkd").css({"height": GetPageSize()[3] - 350 + "px"});
            }, 100);
        });
    });

    $(function () {
        /***根据身份证获取  性别，生日 start***/
        if (showType == "add") {
            $("input#staffIdentityCard", "form#box1Form").trigger("blur");
        }
        $("input#staffIdentityCard", "form#box1Form").bind(
            "blur",
            function () {
                var cardID = $(this).attr("value");
                if (cardID.length == 18) {
                    // $("select#sex","form#box1Form")[0].selectedIndex =
                    // cardID.slice(14,17)%2;
                    if (cardID.slice(14, 17) % 2) {// 偶数女 奇数男
                        $("#sex", "form#box1Form").attr("value",
                            "男");
                    } else {
                        $("#sex", "form#box1Form").attr("value",
                            "女");
                    }
                    var birthday2 = cardID.slice(6, 10) + "-"
                        + cardID.slice(10, 12) + "-"
                        + cardID.slice(12, 14);
                    $("#birthday", "form#box1Form").attr("value",
                        birthday2);
                } else if (cardID.length == 15) {
                    if (cardID.substr(14, 1) % 2) {
                        $("#sex", "form#box1Form").attr("value",
                            "男");
                    } else {
                        $("#sex", "form#box1Form").attr("value",
                            "女");
                    }
                    // $("select#sex","form#box1Form")[0].selectedIndex =
                    // cardID.substr(14,1)%2;
                    var birthday2 = "19" + cardID.substr(6, 2)
                        + "-" + cardID.substr(8, 2) + "-"
                        + cardID.substr(10, 2);
                    $("#birthday", "form#box1Form").attr("value",
                        birthday2);

                }
            });
        /***根据身份证获取  性别，生日 end***/


        //图片预览
        $('#staffphoto').change(function () {
            $t = $("table#stafftable");
            $("table#stafftable").find('#singleShuter').hide();
            $t.find('img#idImg').show();
            $t.find('img#photo').hide();
        });
        //图片预览END
    });
</script>
</body>
</html>
