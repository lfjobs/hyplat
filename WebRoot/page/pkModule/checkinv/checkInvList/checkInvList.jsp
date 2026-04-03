<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="java.util.Map" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
     Map<String,Object> paramMap = (Map<String,Object>)session.getAttribute("paramMap");
%>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <title>盘库单</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <%--<link rel="stylesheet" type="text/css" href="css/checkStyle.css">--%>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <script src="<%=basePath %>js/pkModule/checkinv/checkInvList/jquery-1.9.1.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath %>js/pkModule/checkinv/checkInvList/font-size.js" type="text/javascript" charset="utf-8"></script>
    <script src="<%=basePath %>js/pkModule/checkinv/checkInvList/iScroll.js" type="text/javascript" charset="utf-8"></script>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/pkModule/chekinv/mdate/needcss/Mdate.css">
    <script src="<%=basePath %>js/pkModule/checkinv/checkInvList/Mdate.js" type="text/javascript" charset="utf-8"></script>
    <link href="<%=basePath %>css/pkModule/chekinv/checkInvList/checkStyle.css" rel="stylesheet">
</head>
<script type="text/javascript">
    var basePath="<%=basePath%>";
//    var treeid = "company201009046vxdyzy4wg0000000025";//公司id
    <%--var treeid = "<%=paramMap.get("companyId")%>";//公司id--%>
    var departmentName = "${departmentName}";//所选部门名称
    var search = "${search}";//模糊查询条件
    var showFlag="${showFlag}";
    var departmentId = "${departmentID}";//部门id
    var showStyle = 1;
</script>
<body class="hy">
<header>
    <ul class="clearfix">
        <li onclick="toBack()">
            <img src="<%=basePath %>images/pkModule/checkinv/checkInvList/register_return.png"  alt="">
        </li>
        <li>
            盘库
        </li>
        <li onclick="toQuery()">
            <img src="<%=basePath %>images/pkModule/checkinv/checkInvList/fdj.png"  alt="" >
        </li>
    </ul>
</header>
<div class="content">
<!--一级菜单-->
    <nav class="bug-nav-box" id="ttsw_one_menu_id">
        <ul>
            <li onclick="editStyle(1)" class="active">盘库</li>
            <li onclick="editStyle(2)">库存数</li>
            <li onclick="editStyle(3)">盘库数</li>
            <li onclick="editStyle(4)">误差数</li>
        </ul>
    </nav>
       <section class="sec_head">
            <ul class="clearfix">
                 <li onclick="toAdd()">添加</li>
                 <li onclick="toUpdate()">修改</li>
                 <li onclick="delCheck()">删除</li>
                 <li>导入</li>
                 </ul>
       </section>
    <section class="sec_con clearfix">
        <ul class="ul_con">
            <c:forEach items="${pageForm.list}" var = "entity" varStatus="v">
                <li class="clearfix <c:if test="${v.index+1 ne fn:length(pageForm.list)}"> ttsw_last</c:if>">
                    <aside class="aside_no" checkCasId="${entity.fbillid}">
                        <img class="img_no" src="<%=basePath %>images/pkModule/checkinv/checkInvList/wupinguanli_07.png" alt="">
                        <img class="img_yes" src="<%=basePath %>images/pkModule/checkinv/checkInvList/wupinguanli_10.png" alt="">
                    </aside>

                    <div class="div_ul_c" >
                        <p>
                            单据编号：${entity.journalnum}
                            <img src="<%=basePath %>images/pkModule/checkinv/checkInvList/wupinguanli_img_13.png"  alt="">
                        </p>
                        <ul  onclick="toDetail('${entity.fbillid}')">
                            <li class="txt">
                                公司名称：${entity.companyName}
                            </li>
                            <li>
                                单据类型：${entity.billstype}
                            </li>
                            <li>
                                盘库人： ${entity.staffname}
                            </li>
                        </ul>
                        <ul>
                            <li class="txt">
                                部门：${entity.orgName}
                            </li>
                            <li>
                                单据状态：${entity.billstatus}
                            </li>
                            <li  class="txt">
                                制单日期：${fn:substring(entity.billsdate,0,19)}
                            </li>
                        </ul>
                    </div>
                </li>
            </c:forEach>
            <c:if test="${empty pageForm}">
            <li style="text-align:center;">暂无数据</li>
            </c:if>
        </ul>
    </section>
</div>
</body>
<script type="text/javascript" src="<%=basePath%>js/pkModule/checkinv/checkInvList/checkinv_phoneList.js"></script>
<script src="<%=basePath%>st/js/less.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
    //计算总列表宽度
    var listWidth_1=$(".ul_nav li").length;
    var listWidth=0;
    for(var i=0;i<listWidth_1;i++){
        listWidth+=$(".ul_nav").children("li").eq(i).outerWidth(true);
    }
    $(".ul_nav").width(listWidth+25);
</script>
<script>
    //放回
    //后退
    function toBack() {
        window.history.back();
        var userAgent = navigator.userAgent;
        if (userAgent.indexOf("Firefox") != -1 || userAgent.indexOf("Presto") != -1) {
            window.location.replace("about:blank");
        } else {
            window.opener = null;
            window.open("", "_self");
            window.close();
        }
    }
    //添加方法
    function toAdd() {
        var url = "ea/cashinv/ea_toAddCheckInv.jspa";
        var parameter = "?showFlag="+showFlag+"&departmentID="+departmentId;
        if(showFlag){//单独部门传部门名称过去
            parameter += "&departmentName="+departmentName;
        }
        window.location.href = basePath + url + parameter;
    }

    //跳转详情
    function toDetail(abcdefgid){
        var url = "ea/cashinv/ea_toDetail.jspa";
        var parameter = "?showFlag="+showFlag+"&departmentID="+departmentId+"&fbillid="+abcdefgid+"&showStyle="+showStyle;
        if(showFlag){//单独部门传部门名称过去
            parameter += "&departmentName="+departmentName;
        }
        window.location.href = basePath + url + parameter;
    }
   //删除项目
    function delCheck(){
        //循环获取选中的值
        var id = "";
        $(".aside_yes").each(function (){
            id=$(this).attr("checkCasId");
        });
        if(id != "" && id != null){
            var r = confirm("确定删除该数据吗？");
            if (r == true) {
                var url = "ea/cashinv/ea_delCostSheet.jspa?fbillid="+id;
                window.location.href = basePath + url;
            }
        }else{
            alert("请选择要删除的数据");
            return false;
        }
    }
    //跳转查询页面
    function toQuery(){
        var url = "ea/queryUtil/ea_toQuery.jspa?jumpType=YSD_LB";
        window.location.href = basePath + url;
    }


    //修改
    function toUpdate(){
        //循环获取选中的值
        var id = "";
        $(".aside_yes").each(function (){
            id=$(this).attr("checkCasId");
        });
        if(id != "" && id != null){
            var url = "ea/cashinv/ea_toUpCheckInv.jspa";
            var parameter = "?showFlag="+showFlag+"&departmentID="+departmentId+"&fbillid="+id+"&showStyle="+showStyle;
            if(showFlag){//单独部门传部门名称过去
                parameter += "&departmentName="+departmentName;
            }
            window.location.href = basePath + url + parameter;
        }else{
            alert("请选择要修改的数据");
            return false;
        }
    }

    function editStyle(num){
        showStyle=num;
    }

    //nav点击选中
    $(document).on("click", ".bug-nav-box ul li", function () {
        $(".bug-nav-box ul li").siblings().removeClass("active");
        $(this).addClass("active");
    });
</script>

</html>

