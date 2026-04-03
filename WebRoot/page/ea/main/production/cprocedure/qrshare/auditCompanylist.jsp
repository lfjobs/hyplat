<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
  <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <meta name="format-detection" content="telephone=no, email=no" />
    <meta name="screen-orientation" content="portrait">
    <meta name="x5-orientation" content="portrait">
    <script type="text/javascript" src="<%=basePath%>js/ea/finance/setHtmlFont.js"></script>
    <link rel="stylesheet" href="<%=basePath%>css/WFJClient/base.css">
    <link rel="stylesheet" href="<%=basePath%>css/WFJClient/zy_sys.css">
    <script type="text/javascript"  src="<%=basePath%>js/ea/finance/jquery.min.js"></script>
        <script type="text/javascript"  src="<%=basePath%>js/ea/production/cprocedure/qrshare/auditCom.js"></script>
	  <title>审批人</title>
</head>
<body>
<script >
	var auid='${auid}';
	var comment  = "${param.comment}";
	var basePath = "<%=basePath%>";
</script>
	<header class="com_head"> 
        <a href="javascript:history.back(-1);" class="back"></a>
        <h1>审批人</h1>
        <a href="javascript:;" class="head_R">完成</a>
    </header>
   <div class="wrap_page">
        <div class="add_search">
            <div class="tinfo_wrap">
                <input type="text" class="taskinfo_search">
                <div class="tinfo_over">
                    <i class="tinfo_ico"></i> 搜索
                </div>
            </div>
        </div>
        <div class="company_list">
        <c:forEach items="${companylist}" var="item" varStatus="state">
            <div class="c_box" id="${item.companyID}">
                <div class="c_nav clearfix">
                    <img src="<%=basePath%>images/WFJClient/Mimage/head_img.png" class="c_img" alt="">
                    <div class="c_name">${item.companyName}</div>
                    <i class="c_arr"></i>
                </div>
                <c:if test="${state.index==0}">
                <ul class="c_section_wrap" style="display:show;">
                <c:forEach items="${orglist}" var="subitem">
                    <li><a href="javascript:findStaff('${item.companyName}','${subitem.organizationName}','${item.companyID}','${subitem.organizationID}');" class="c_section">${subitem.organizationName }</a></li>
                  </c:forEach>
                </ul>
                
                </c:if>
            </div>
            </c:forEach>
          
           
        </div>
        <div class="person_list">
           <%--  <div class="p_search clearfix">
                <img src="<%=basePath%>images/WFJClient/Mimage/head_img.png" class="p_img" alt="">
                <div class="p_text">
                    <div class="p_name">孙海舰</div>
                    <div class="p_info">北京天太世统科技有限公司-人事部</div>
                </div>
                <i></i>
            </div> --%>

           
        </div>
    </div>
    <script>
        //搜索框点击
        $(".tinfo_over").click(function() {
            $(this).hide();
            $(".taskinfo_search").focus();
        });
        //搜索失去焦点事件
        $(".taskinfo_search").blur(function() {
            if ($(this).val() == '') {
                $(".tinfo_over").show();
                $(".person_list").hide();
                $(".company_list").show();
                $(".head_R").removeClass("add_btn");
            }
        })
        //搜索获取焦点事件
        $(".taskinfo_search").focus(function() {
                $(".person_list").show();
                $(".company_list").hide();
                $(".head_R").show();
            })
            //点击公司展开-折叠部门
        $(document).on("click", ".c_nav", function() {
                $(this).next().slideToggle(200)
                    .end().find("i").toggleClass("c_arr_b")
                    .parents(".c_box").siblings(".c_box").find("i").removeClass("c_arr_b")
                    .end().find(".c_section_wrap").slideUp(200);
            })
            //搜索出列表点击样式改变
        $(document).on('click', '.p_search', function() {
            $(this).find("i").addClass("select_ico").end().siblings(".p_search").find("i").removeClass();
            $(".head_R").addClass("add_btn");
        })
        //搜索
        $(".taskinfo_search").on("input", function() {
            if ($(this).val() == '') {
                $(".person_list").hide();
                $(".company_list").show();
                $(".head_R").hide().removeClass("add_btn");
            }
        })

    </script>

</body>
</html>