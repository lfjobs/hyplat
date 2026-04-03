<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd" >
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>办公室后勤-树形</title>
    <%@ page language="java" pageEncoding="UTF-8" %>
    <%@ taglib uri="/struts-tags" prefix="s" %>
    <%@ page import="hy.ea.bo.CAccount" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
                + request.getServerName() + ":" + request.getServerPort()
                + path + "/";
        CAccount ca = (CAccount) session.getAttribute("account");
    %>
    <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
    <link href="<%=basePath%>css/ea/admin.css" rel="stylesheet"
          type="text/css"/>
    <link href="<%=basePath%>css/css.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet"
          href="<%=basePath%>/js/jqueryplus/treeview/jquery.treeview.css"/>
    <script src="<%=basePath%>/js/jquery.js" type="text/javascript"></script>
    <script src="<%=basePath%>/js/jqueryplus/treeview/jquery.treeview.js"
            type="text/javascript"></script>
    <script type="text/javascript" src="<%=basePath %>/js/dropdown/extendPageMenu.js"></script>

    <script type="text/javascript">

        var basePath = '<%=basePath%>';
        var aemail = '<%=ca.getAccountEmail()%>';
        var companyid = '<%=ca.getCompanyID()%>';


        $(document).ready(function () {

            if ((companyid == "company201009046vxdyzy4wg0000000130" && aemail == "yj")||companyid == "company201009046vxdyzy4wg0000000015" && aemail == "sa"||(companyid == "company201009046vxdyzy4wg0000000140" && (aemail == "lqh"||aemail == "zm"))||(companyid == "company20251212JI3XK6C26S0000000477" && aemail == "sa")) {

                $(".sfbu").show();
            }

            $("#navigation").treeview({
                persist: "location",
                collapsed: true,
                unique: true
            });


        });

        function mymeeting(tt) {
            var url = basePath + "ea/videoroom/sajax_ea_addUserinfo.jspa";
            $.ajax({
                url: url,
                type: "get",
                async: false,
                dataType: "json",
                success: function (data) {
                    var me = eval("(" + data + ")");
                    var not_login = me.not_login;
                    if (not_login == "not_login") {
                        document.location.href = basePath + "page/ea/not_login.jsp";
                        return false;
                    }
                    var user = me.user;
                    var sccid = me.sccid;
                    var staffID = me.staffID;
                    if (staffID != null && staffID != "") {
                        alert("系统账号没有绑定数字地球APP账号请先绑定");
                        var url = basePath + "ea/humanResource/ea_getHumanResource.jspa?showType=edit&cstaff.staffID=" + staffID + "&cstaff.status=99";
                        window
                            .open(url, '',
                                'scrollbars=yes,resizable=yes,channelmode');
                    } else {

                        if (tt == 0) {
                            mainframe.location.href = basePath + "ea/videoroom/ea_getRoominfoByUserName.jspa?user=" + user + "&sccid=" + sccid + "&r=" + Math.random();
                        } else {
                            mainframe.location.href = basePath + "ea/videoroom/ea_myRoomMeeting.jspa?user=" + user + "&sccid=" + sccid + "&r=" + Math.random();

                        }
                    }

                },
                error: function (data) {
                    console.log("添加用户失败");
                }

            });


        }
    </script>
    <style type="text/css">
        #qh_sw {
            width: 15%;
            border: 1px solid #DAE7F6;
        }

        .treeview li {
            margin: 0;
            padding: 1px 0 1px 16px;
        }

        #navigation a:hover {

            color: red;

        }
    </style>

</head>
<body>
<div class="main_main">
    <table width="100%" cellspacing="0" cellpadding="0" border="2">
        <tr>
            <td id="qh_sw" style="width: 20%;" valign="top">
                <div class="qh_gg_nav">
                    &nbsp; <span id="frametitle">办公室后勤管理</span>

                </div> <!--左边的树 -->
                <div style="overflow:auto;">
                    <ul id="navigation" style="width: 240px;margin-left:15px;" class="filetree">
                        <%--<li ><span class="folder">后勤管理</span>

                            --%>
                        <ul>
                            <li><span class="folder">会议管理</span>
                                <ul>
                                    <li><span class="folder">现场会议</span>

                                        <ul>
                                            <li><a target="mainframe"
                                                   href="<%=basePath%>ea/dtconferenceorg/ea_getAllDtconOrg.jspa"><span
                                                    class="file">会务组织机构人员配备</span> </a></li>
                                            <li><a target="mainframe"
                                                   href="<%=basePath%>ea/dtconference/ea_getDtconferenceList.jspa?newState=00"><span
                                                    class="file">会议准备阶段模块管理</span> </a></li>
                                            <li><a target="mainframe"
                                                   href="<%=basePath%>ea/dtconference/ea_getDtconferenceList.jspa?newState=01"><span
                                                    class="file">正式会议阶段模块管理</span> </a></li>
                                            <li><a target="mainframe"
                                                   href="<%=basePath%>/ea/dtconference/ea_getDtconferenceList.jspa?newState=02"><span
                                                    class="file">会议闭幕阶段模块管理</span> </a></li>

                                        </ul>
                                    </li>
                                    <li><span class="folder">会议室会议</span>

                                        <ul>
                                            <li><a target="mainframe"
                                                   href="<%=basePath%>ea/meetingroom/ea_getMyRoomOrder.jspa"><span
                                                    class="file">会议室预约管理</span> </a></li>
                                            <li><a target="mainframe"
                                                   href="<%=basePath%>ea/smeeting/ea_getStaffMeeingList.jspa"><span
                                                    class="file">员工会议管理</span> </a></li>


                                        </ul>
                                    </li>
                                    <li><span class="folder">视频会议</span>

                                        <ul>
                                            <li>
                                            <li><a target="mainframe" onclick="mymeeting(0)"><span
                                                    class="file">可参与的会议</span> </a></li>
                                            <li><a target="mainframe" onclick="mymeeting(1)"
                                            ><span
                                                    class="file">我创建的会议</span> </a></li>


                                        </ul>
                                    </li>
                                </ul>
                            </li>
                            <li><span class="folder">接待管理</span>
                                <ul>
                                    <li><span class="folder">往来个人管理</span>

                                        <ul>
                                            <li><a target="mainframe"
                                                   href="<%=basePath%>ea/contactuser/ea_getListContactUser.jspa"><span
                                                    class="file">个人接待信息管理</span> </a></li>
                                            <li><a target="mainframe"
                                                   href="<%=basePath%>/ea/publicreceipts/ea_getListPublicreceipts.jspa?labelTag=00"><span
                                                    class="file">个人接待信息报表</span> </a></li>

                                        </ul>
                                    </li>
                                    <li><a target="mainframe"
                                           href="<%=basePath%>/ea/contactConnection/ea_getListContactConnection.jspa"><span
                                            class="file">往来单位管理</span> </a></li>

                                </ul>
                            </li>
                            <li><span class="folder">考场管理</span>
                                <ul>
                                    <li><a target="mainframe"
                                           href="<%=basePath%>/ea/carmanage/ea_examinationRoomList.jspa">
                                        <span class="file">考场管理</span></a></li>
                                    <li><a target="mainframe" href="<%=basePath%>/ea/carmanage/ea_chargeList.jspa">
                                        <span class="file">收费标准</span></a></li>
                                    <li><a target="mainframe"
                                           href="<%=basePath%>/ea/carmanage/ea_testRecordList.jspa?tcc.account=&bfmation.signInState=">
                                        <span class="file">考场记录</span></a></li>
                                    <li><a target="mainframe"
                                           href="<%=basePath%>ea/contract/ea_getAllFileList.jspa">
                                        <span class="file">培训合同</span></a></li>
                                    <%--<li><a target="mainframe" href="<%=basePath%>/ea/facerec/ea_getListFaceRec.jspa">--%>
                                        <%--<span class="file">人脸闸机设备管理</span></a></li>--%>
                                </ul>
                            </li>
                            <li><span class="folder">停车场管理</span>
                                <ul>
                                    <li><a target="mainframe" href="<%=basePath%>/ea/carmanage/ea_collectList.jspa?">
                                        <span class="file">汇总</span></a></li>
                                    <li><a target="mainframe"
                                           href="<%=basePath%>/ea/carmanage/ea_getList.jspa?cm.status=1">
                                        <span class="file">进入车辆管理</span></a></li>
                                    <li><a target="mainframe"
                                           href="<%=basePath%>/ea/carmanage/ea_getList.jspa?cm.status=0">
                                        <span class="file">历史记录(出)</span></a></li>
                                    <li><a target="mainframe"
                                           href="<%=basePath%>/ea/carmanage/ea_getList.jspa?cm.status=2">
                                        <span class="file">异常数据</span></a></li>
                                    <li><a target="mainframe" href="<%=basePath%>/ea/carmanage/ea_feescale.jspa">
                                        <span class="file">收费标准</span></a></li>
                                    <li><a target="mainframe" href="<%=basePath%>/ea/carmanage/ea_siteList.jspa">
                                        <span class="file">场地管理</span></a></li>
                                    <li><a target="mainframe"
                                           href="<%=basePath%>/ea/carmanage/ea_parkingSpaceList.jspa">
                                        <span class="file">停车位管理</span></a></li>
                                    <li><a target="mainframe" href="<%=basePath%>/ea/carmanage/ea_facilityList.jspa">
                                        <span class="file">设备管理</span></a></li>
                                    <li class="sfbu" style="display: none;"><a target="mainframe"
                                                                               href="<%=basePath%>/ea/carmanage/ea_getVipFeeList.jspa">
                                        <span class="file">收费补录</span></a></li>
                                </ul>
                            </li>

                            <li><span class="file">资产库管</span></li>
                            <li><span class="folder">安全管理</span>
                                <ul>
                                    <li><span class="folder">安全管理</span>
                                        <ul>
                                            <li><a target="mainframe"
                                                   href="<%=basePath%>/ea/keyManage/ea_getKeyManageList.jspa"><span
                                                    class="file">钥匙管理</span> </a>
                                            </li>
                                            <li><a target="mainframe"
                                                   href="<%=basePath%>page/ea/main/office_ea/safe/safekinds_manager.jsp"><span
                                                    class="file">安全类别</span> </a>
                                            </li>
                                            <li><a target="mainframe"
                                                   href="<%=basePath%>ea/safeinspect/ea_getSafeInspectList.jspa"><span
                                                    class="file">安全单据</span> </a>
                                            </li>

                                        </ul>
                                    </li>
                                    <li><span class="folder">安全防范管理</span>
                                        <ul>
                                            <li><a target="mainframe"
                                                   href="javascript:"><span
                                                    class="file">火灾预防</span> </a>
                                            </li>
                                            <li><a target="mainframe"
                                                   href="javascript:"><span
                                                    class="file">防盗管理</span> </a>
                                            </li>
                                            <li><a target="mainframe"
                                                   href="javascript:"><span
                                                    class="file">防霉管理</span> </a>
                                            </li>
                                            <li><a target="mainframe"
                                                   href="javascript:"><span
                                                    class="file">防毒管理</span> </a>
                                            </li>
                                            <li><a target="mainframe"
                                                   href="javascript:"><span
                                                    class="file">污染预防</span> </a>
                                            </li>
                                            <li><a target="mainframe"
                                                   href="javascript:"><span
                                                    class="file">雪灾预防</span> </a>
                                            </li>
                                            <li><a target="mainframe"
                                                   href="javascript:"><span
                                                    class="file">冰雹预防</span> </a>
                                            </li>
                                            <li><a target="mainframe"
                                                   href="javascript:"><span
                                                    class="file">冻害预防</span> </a>
                                            </li>
                                            <li><a target="mainframe"
                                                   href="javascript:"><span
                                                    class="file">垮塌预防</span> </a>
                                            </li>
                                            <li><a target="mainframe"
                                                   href="javascript:"><span
                                                    class="file">地震预防</span> </a>
                                            </li>
                                            <li><a target="mainframe"
                                                   href="javascript:"><span
                                                    class="file">洪涝预防</span> </a>
                                            </li>
                                            <li><a target="mainframe"
                                                   href="javascript:"><span
                                                    class="file">防泥石流</span> </a>
                                            </li>
                                            <li><a target="mainframe"
                                                   href="javascript:"><span
                                                    class="file">虫害预防</span> </a>
                                            </li>
                                            <li><a target="mainframe"
                                                   href="javascript:"><span
                                                    class="file">疾病预防</span> </a>
                                            </li>
                                            <li><a target="mainframe"
                                                   href="javascript:"><span
                                                    class="file">安全用电</span> </a>
                                            </li>
                                            <li><a target="mainframe"
                                                   href="javascript:"><span
                                                    class="file">雷雨预防</span> </a>
                                            </li>
                                            <li><a target="mainframe"
                                                   href="javascript:"><span
                                                    class="file">防龙卷风</span> </a>
                                            </li>
                                            <li><a target="mainframe"
                                                   href="javascript:"><span
                                                    class="file">食品安全</span> </a>
                                            </li>
                                            <li><a target="mainframe"
                                                   href="javascript:"><span
                                                    class="file">车辆设备</span> </a>
                                            </li>
                                        </ul>
                                    </li>

                                </ul>
                            </li>
                            <li><span class="folder">设备管理</span>
                                <ul>
                                    <li><span class="folder">车管设备</span>
                                        <ul>
                                            <li><span class="folder">汽车</span>
                                                <ul>
                                                    <li><span class="folder">车辆管理办</span>
                                                        <ul>
                                                            <li><a target="mainframe"
                                                                   href="<%=basePath%>/ea/car/ea_getCarInformationList.jspa"><span
                                                                    class="file">完善车辆信息</span> </a></li>
                                                            <li><a target="mainframe"
                                                                   href="<%=basePath%>/ea/carbaseinfo/ea_getCarBaseInfoList.jspa"><span
                                                                    class="file">车辆基本信息</span> </a>
                                                            </li>
                                                            <li><a target="mainframe"
                                                                   href="<%=basePath%>/ea/cardmanage/ea_getCarGooutRecord.jspa"><span
                                                                    class="file">车辆门禁</span> </a>
                                                            </li>
                                                            <li><span class="folder">安全巡检</span>
                                                                <ul>
                                                                    <li><a target="mainframe"
                                                                           href="<%=basePath%>ea/safetycheck/ea_point.jspa"><span
                                                                            class="file">检查点</span> </a>
                                                                    </li>
                                                                    <li><a target="mainframe"
                                                                           href="<%=basePath%>ea/safetycheck/ea_pointitem.jspa"><span
                                                                            class="file">检查项</span> </a>
                                                                    </li>
                                                                    <li><a target="mainframe"
                                                                           href="<%=basePath%>ea/safetycheck/ea_plan.jspa"><span
                                                                            class="file">巡检计划</span> </a>
                                                                    </li>
                                                                    <li><a target="mainframe"
                                                                           href="<%=basePath%>ea/safetycheck/ea_task.jspa"><span
                                                                            class="file">巡检任务</span> </a>
                                                                    </li>
                                                                    <li><a target="mainframe"
                                                                           href="<%=basePath%>ea/safetycheck/ea_results.jspa"><span
                                                                            class="file">巡检结果</span> </a>
                                                                    </li>
                                                                </ul>
                                                            </li>
                                                            <li><a target="mainframe"
                                                                   href="<%=basePath%>ea/carnum/ea_getListCarByCompanyID.jspa?type=1"><span
                                                                    class="file">车牌号维护</span> </a>
                                                            </li>
                                                            <li><a target="mainframe"
                                                                   href="<%=basePath%>/ea/carinvoice/ea_getCarInvoiceList.jspa?type=1"><span
                                                                    class="file">购车发票</span> </a>
                                                            </li>
                                                            <li><a target="mainframe"
                                                                   href="<%=basePath%>/ea/carpurchasetax/ea_getCarPurchaseTaxList.jspa?type=1"><span
                                                                    class="file">购置税发票</span> </a>
                                                            </li>
                                                            <li><a target="mainframe"
                                                                   href="<%=basePath%>/ea/carinsurance/ea_getCarInsuranceList.jspa?type=1"><span
                                                                    class="file">购置保险信息</span> </a>
                                                            </li>
                                                            <li><a target="mainframe"
                                                                   href="<%=basePath%>/ea/carareview/ea_getCarAReviewList.jspa?type=1"><span
                                                                    class="file">购置年检信息</span> </a>
                                                            </li>
                                                            <li><a target="mainframe"
                                                                   href="<%=basePath%>/ea/carcng/ea_getCarCNGList.jspa?type=1"><span
                                                                    class="file">车辆CNG信息</span> </a>
                                                            </li>
                                                            <li><a target="mainframe"
                                                                   href="<%=basePath%>/ea/carsafeinformation/ea_getCarsafeinformationList.jspa?type=1"><span
                                                                    class="file">车辆安全信息</span> </a>
                                                            </li>
                                                            <li><a target="mainframe"
                                                                   href="<%=basePath%>/ea/carassetcinformation/ea_getCaraffetcinformationList.jspa?type=1"><span
                                                                    class="file">车辆资产信息</span> </a>
                                                            </li>
                                                            <li><a target="mainframe"
                                                                   href="<%=basePath%>/ea/employcondition/ea_getemployconditionList.jspa?type=1"><span
                                                                    class="file">车辆使用信息</span> </a>
                                                            </li>
                                                            <li><a target="mainframe"
                                                                   href="<%=basePath%>/ea/carmaintain/ea_getListCarMaintain.jspa?type=1"><span
                                                                    class="file">车辆维护信息</span> </a>
                                                            </li>
                                                            <li><a target="mainframe"
                                                                   href="<%=basePath%>/ea/certificateatable/ea_getCertificateaTableList.jspa?type=1"><span
                                                                    class="file">相关证件子集</span> </a>
                                                            </li>
                                                            <li><a target="mainframe"
                                                                   href="<%=basePath%>/ea/motorcar/ea_getMotorcarList.jspa?type=1"><span
                                                                    class="file">机动车行驶证</span> </a>
                                                            </li>
                                                            <li><a target="mainframe"
                                                                   href="<%=basePath%>/ea/carroad/ea_getCarRoadList.jspa?type=1"><span
                                                                    class="file">道路运输证</span> </a>
                                                            </li>
                                                            <li><a target="mainframe"
                                                                   href="<%=basePath%>/ea/bottle/ea_getBottleList.jspa?type=1"><span
                                                                    class="file">车用瓶使用证</span> </a>
                                                            </li>
                                                            <li><a target="mainframe"
                                                                   href="<%=basePath%>/ea/carpurchase/ea_getPurchaseList.jspa?type=1"><span
                                                                    class="file">车辆购置税证</span> </a>
                                                            </li>
                                                            <li><a target="mainframe"
                                                                   href="<%=basePath%>/ea/carviolate/ea_getCarViolateList.jspa?type=1"><span
                                                                    class="file">车辆违章信息</span> </a>
                                                            </li>
                                                            <li><a target="mainframe"
                                                                   href="<%=basePath%>/ea/carquasi/ea_getCarseatList.jspa?type=1"><span
                                                                    class="file">车辆准载座位</span> </a>
                                                            </li>

                                                            <li><a target="mainframe"
                                                                   href="<%=basePath%>/ea/carassectinformation/ea_getSafetyHealthList.jspa?type=1"><span
                                                                    class="file">安全卫生检查</span> </a>
                                                            </li>


                                                        </ul>
                                                    </li>

                                                    <li><span class="folder">接送预约接送到达管理</span>
                                                        <ul>
                                                            <li><a target="mainframe"
                                                                   href="<%=basePath%>/ea/shuttle/ea_getCarQuasiList.jspa"><span
                                                                    class="file">接送预约信息管理 </span> </a></li>
                                                            <li><a target="mainframe"
                                                                   href="<%=basePath%>/ea/shuttle/ea_getCarQuasiList.jspa"><span
                                                                    class="file">接送预约信息报表管理 </span> </a></li>
                                                            <li><a target="mainframe"
                                                                   href="<%=basePath%>/ea/shuttle/ea_getCarQuasiList.jspa"><span
                                                                    class="file">接送基本信息管理 </span> </a></li>
                                                            <li><a target="mainframe"
                                                                   href="<%=basePath%>/ea/shuttle/ea_getCarQuasiList.jspa"><span
                                                                    class="file">接送信息报表管理 </span> </a></li>
                                                            <li><a target="mainframe"
                                                                   href="<%=basePath%>/ea/shuttle/ea_getCarQuasiList.jspa"><span
                                                                    class="file">接送到达基本信息管理</span> </a></li>
                                                            <li><a target="mainframe"
                                                                   href="<%=basePath%>/ea/shuttle/ea_getCarQuasiList.jspa"><span
                                                                    class="file">接送到达信息报表管理</span> </a></li>

                                                        </ul>
                                                    </li>
                                                </ul>
                                            </li>
                                        </ul>
                                    </li>
                                    <li><a target="mainframe"
                                           href="<%=basePath%>/ea/marketingExamine/ea_getMarketingList.jspa"><span
                                            class="folder">工程机械</span> </a>
                                        <ul>
                                            <li><a target="mainframe"
                                                   href="javascript:"><span
                                                    class="file">挖掘机</span> </a>
                                            </li>
                                            <li><a target="mainframe"
                                                   href="javascript:"><span
                                                    class="file">装载机</span> </a>
                                            </li>

                                        </ul>
                                    </li>
                                    <li><a target="mainframe"><span
                                            class="folder">办公室设备</span> </a>
                                        <ul>
                                            <li><a target="mainframe"
                                                   href="javascript:"><span
                                                    class="file">电脑</span> </a>
                                            </li>
                                            <li><a target="mainframe"
                                                   href="javascript:"><span
                                                    class="file">打印机</span> </a>
                                            </li>
                                            <li><a target="mainframe"
                                                   href="javascript:"><span
                                                    class="file">通讯设备</span> </a>
                                            </li>
                                            <li><a target="mainframe"
                                                   href="<%=basePath%>/ea/scale/ea_getScaleList.jspa"><span
                                                    class="file">电子称</span> </a>
                                            </li>
                                            <li><a target="mainframe"
                                                   href="<%=basePath%>/ea/pos/ea_getPosList.jspa"><span
                                                    class="file">POS销售终端</span> </a>
                                            </li>

                                        </ul>


                                    </li>
                                    <li><a target="mainframe"><span
                                            class="folder">微信刷脸设备</span> </a>
                                        <ul>
                                            <li><a target="mainframe"
                                                   href="<%=basePath%>/ea/face/ea_getFaceDeviceList.jspa?deviceType=00"><span
                                                    class="file">终端信息</span> </a>
                                            </li>
                                            <%--<li><a target="mainframe"--%>
                                            <%--href="javascript:"><span--%>
                                            <%--class="file">终端划拨</span> </a>--%>
                                            <%--</li>--%>
                                            <li><a target="mainframe"
                                                   href="<%=basePath%>/ea/face/ea_getStoreBindList.jspa?deviceType=00"><span
                                                    class="file">商家签约</span> </a>
                                            </li>
                                            <li><a target="mainframe"
                                                   href="<%=basePath%>/ea/face/ea_getPosterList.jspa?deviceType=00"><span
                                                    class="file">设置海报</span> </a>
                                            </li>

                                        </ul>


                                    </li>
                                    <%--<li><a target="mainframe"><span--%>
                                    <%--class="folder">支付宝刷脸设备</span> </a>--%>
                                    <%--<ul>--%>
                                    <%--<li><a target="mainframe"--%>
                                    <%--href="<%=basePath%>/ea/face/ea_getFaceDeviceList.jspa?deviceType=01"><span--%>
                                    <%--class="file">终端信息</span> </a>--%>
                                    <%--</li>--%>
                                    <%--<li><a target="mainframe"--%>
                                    <%--href="javascript:"><span--%>
                                    <%--class="file">终端划拨</span> </a>--%>
                                    <%--</li>--%>
                                    <%--<li><a target="mainframe"--%>
                                    <%--href="javascript:"><span--%>
                                    <%--class="file">终端分配</span> </a>--%>
                                    <%--</li>--%>

                                    <%--</ul>--%>


                                    <%--</li>--%>
                                    <li><a target="mainframe"
                                           href="javascript:"><span
                                            class="file">弱电设备</span> </a></li>

                                </ul>
                            </li>
                            <li><a target="mainframe"
                                   href="<%=basePath%>/ea/logistics/ea_getLogisticsList.jspa"><span
                                    class="file">物流管理</span></a></li>
                            <li><a target="mainframe"
                                   href="<%=basePath%>/ea/water/ea_getListForPage.jspa"><span
                                    class="file">用水管理</span></a></li>

                            <li><a target="mainframe"
                                   href="<%=basePath%>/ea/electricity/ea_getListForPage.jspa"><span
                                    class="file">用电管理</span></a></li>

                            <li><a target="mainframe"
                                   href="<%=basePath%>/ea/onduty/ea_getListForPage.jspa"><span class="file">值班管理</span></a>
                            </li>

                            <li><a target="mainframe"
                                   href="<%=basePath%>/ea/afforest/ea_getListForPage.jspa"><span
                                    class="file">绿化管理</span></a></li>
                            <li><a target="mainframe"
                                   href="javascript:"><span class="file">基建管理</span></a></li>

                            <li><a target="mainframe"
                                   href="javascript:"><span class="file">食堂管理</span></a></li>

                            <li><span class="folder">住宿管理</span>

                                <ul>
                                    <li><a target="mainframe"
                                           href="<%=basePath%>/ea/accommod/ea_getAllList.jspa"><span
                                            class="file">单位住宿</span> </a></li>
                                    <li><a target="mainframe"
                                           href="javascript:"><span
                                            class="file">单位住宿报表</span> </a></li>
                                    <li><a target="mainframe"
                                           href="javascript:"><span
                                            class="file">住宿分配</span> </a></li>
                                    <li><a target="mainframe"
                                           href="javascript:"><span
                                            class="file">住宿分配报表</span> </a></li>

                                </ul>
                            </li>
                            <li><span class="folder">餐饮管理</span>

                                <ul>
                                    <li><a target="mainframe"
                                           href="<%=basePath%>ea/cater/ea_getCaterList.jspa"><span
                                            class="file">餐桌管理</span> </a></li>
                                </ul>
                            </li>


                            <li><span class="folder">票务管理</span>
                                <ul>
                                    <li><a target="mainframe"
                                           href="<%=basePath%>/ea/piaowuManager/ea_getListpiaowu.jspa?aa=zz"><span
                                            class="file">票务管理</span> </a></li>
                                    <li><a target="mainframe"
                                           href="<%=basePath%>/ea/piaowuManager/ea_getListpiaowu.jspa?aa=bb"><span
                                            class="file">票务报表管理</span> </a></li>

                                </ul>
                            </li>

                            <li><span class="folder">资产库存管理</span>
                                <ul>
                                    <li><a target="mainframe"
                                           href="<%=basePath%>/ea/purchase1/ea_getPurchaseList.jspa?type=00"><span
                                            class="file">采购管理</span> </a>
                                    </li>
                                    <li><a target="mainframe"
                                           href="<%=basePath%>/ea/purchase1/ea_getPurchaseList.jspa?type=01"><span
                                            class="file">收货管理</span> </a>
                                    </li>

                                    <li><a target="mainframe"
                                           href="<%=basePath%>/ea/purchase1/ea_getinspectList.jspa"><span
                                            class="file">验货管理</span> </a>
                                    </li>
                                    <li><span class="folder">入库管理</span>
                                        <ul>

                                            <li><a target="mainframe"
                                                   href="<%=basePath%>/ea/newstorage/ea_toExamineGoodsBillList.jspa"><span
                                                    class="file">采购入库</span> </a></li>
                                            <li><a target="mainframe"
                                                   href="<%=basePath%>/ea/allocationsto/ea_toAllocationStorageList.jspa"><span
                                                    class="file">调拨入库</span> </a></li>
                                            <li><a target="mainframe"
                                                   href="<%=basePath%>/ea/receivesto/ea_toAllocationStorageList.jspa"><span
                                                    class="file">归还入库</span></a></li>
                                            <li><a target="mainframe"
                                                   href="<%=basePath%>/ea/goldStock/ea_getGoldOfTheHomePage.jspa?type=00"><span
                                                    class="file">金币入库</span></a></li>
                                            <li><a target="mainframe"
                                                   href="<%=basePath%>/ea/cashStock/ea_getCashOfTheHomePage.jspa?type=00"><span
                                                    class="file">现金入库</span></a></li>
                                        </ul>
                                    </li>
                                    <li><%--<a target="mainframe"
												href="<%=basePath%>/ea/marketingExamine/ea_getMarketingList.jspa"><span
													class="file">出库管理</span> </a>
													
												--%>
                                        <span class="folder">出库管理</span>
                                        <ul>
                                            <li><a target="mainframe"
                                                   href="<%=basePath%>/ea/newsales/ea_getWareManagementList.jspa?billStatus=07"><span
                                                    class="file">销售出库</span> </a></li>
                                            <li><a target="mainframe"
                                                   href="<%=basePath%>/ea/allocation/ea_getWareManagementList.jspa?billStatus=07"><span
                                                    class="file">调拨出库</span> </a></li>
                                            <li><a target="mainframe"
                                                   href="<%=basePath%>/ea/receive/ea_getWareManagementList.jspa?billStatus=07">
                                                <span class="file">领用出库</span></a></li>
                                            <li><a target="mainframe"
                                                   href="<%=basePath%>/ea/goldStock/ea_getGoldOfTheHomePage.jspa?type=01"><span
                                                    class="file">金币出库</span></a></li>
                                            <li><a target="mainframe"
                                                   href="<%=basePath%>/ea/cashStock/ea_getCashOfTheHomePage.jspa?type=01"><span
                                                    class="file">现金出库</span></a></li>
                                        </ul>
                                    </li>
                                    <li>
                                        <span class="folder">库存管理</span>
                                        <ul>
                                            <li><a target="mainframe"
                                                   href="<%=basePath%>/ea/invadj/ea_toSearch.jspa"><span
                                                    class="file">销售库存调整</span> </a></li>
                                            <li><a target="mainframe"
                                                   href="<%=basePath%>/ea/warehousing/ea_getInventoryManagementList.jspa"><span
                                                    class="file">库存管理</span> </a></li>
                                            <li><a target="mainframe"
                                                   href="<%=basePath%>/ea/warehousing/ea_getInventoryPoolList.jspa"><span
                                                    class="file">进销存明细</span> </a></li>
                                            <li><a target="mainframe"
                                                   href="<%=basePath%>/ea/warehousing/ea_getInventoryDetailList.jspa"><span
                                                    class="file">进销存汇总</span> </a></li>
                                        </ul>
                                    </li>
                                    <li><a target="mainframe"
                                           href="<%=basePath%>/ea/break/ea_getbreakList.jspa"><span
                                            class="file">报损管理</span> </a>
                                    </li>
                                    <li><a target="mainframe"
                                           href="<%=basePath%>/ea/marketingExamine/ea_getMarketingList.jspa"><span
                                            class="file">存货核算 </span> </a>
                                    </li>

                                </ul>
                            <li><span class="file">物品物料管理</span></li>


                        </ul>
                        <%--</li>
                --%></ul>
                </div>
            </td>
            <td style="width: 80%;" valign="top">
                <iframe id="mainframe"
                        name="mainframe" src="" frameborder="0" style="width: 100%;"></iframe>
            </td>

        </tr>
    </table>
</div>
<script type="text/javascript">
    $(function () {
        $(window).resize(function () {
            setTimeout(function () {
                $("#navigation").height($(window).height() - 30);
                // $("#mainframe").css({"height" : $(window).height() - 5 + "px"});
            }, 100);
        });
        $("#navigation").height($(window).height() - 30);
        $("#mainframe").css({
            "height": $(window).height() + "px"
        });
    });
</script>
</body>


</html>
