<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/"; %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>在职员工</title>
    <script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
    <script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
    <script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>

    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
    <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
    <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
    <script type="text/javascript">
        var basePath = "<%=basePath%>";
        var pageSize = '${pageSize}';
        var custype = '${custype}';
        var status = '${status}';
        var token = 0;
        var personvalue = "";
        var s_account = "";
        var orgID = '${orgID}';
    </script>
</head>
<body>
<div style="margin-top:10px;margin-left:10px;"
		class="query">

		<form id="SearchForm" name="SearchForm" method="post">
			<input type="submit" name="submit" style="display:none;" />
			<table>
				<tr>
					<td><strong>在职员工</strong></td>
				</tr>
			</table>
		</form>
	</div>
<div class="main_main">
    <form name="formtest" id="formtest" method="post">
        <input type="submit" name="submit" style="display:none">
        <input type="hidden" name=orgID id="orgID" value="${orgID}">
        <s:token></s:token>
        <table class="JQueryflexme">
            <thead>
            <tr class="tablewith">
                <th width="40" align="center">请选择</th>
                <th width="40" align="center">序号</th>
                <th width="100" align="center">微分金帐号</th>
                <th width="100" align="center">员工姓名</th>
                <th width="150" align="center">会员级别</th>
                <th width="100" align="center">帐号类型</th>
            </thead>
            <%    int number = 1; %>
            <c:forEach items="${pageForm.list}" var="a">
                <tr id="${a[2]}">
                    <td>
                    		<input type="checkbox" name="a" class="JQuerypersonvalue"  id="sccid"
                               value="${a[2]}"/>
                    </td>
                    <td ><span><%=number%></span></td>
                    <td><span id="s_account">${a[4]}</span></td>

                    <td><span id="author">${a[0]}</span>
                    	<input type="hidden" name="staffid" id="staffid" value="${a[1]}">
                    </td>
                    <td><span id="logoNote">${a[3]}</span></td>
                    <td><span>${a[5]=='1'?'个人会员':a[5]=='2'?'企业会员':null}</span></td>
                </tr>
                <%  number++; %>
            </c:forEach>

        </table>
    </form>

    <c:import url="../../ea/page_navigator.jsp">
        <c:param name="actionPath"
                 value="ea/wfjzh/ea_getPersonByDept.jspa?mz=${mz}&zh=${zh}&type=${type}&orgID=${orgID}">
        </c:param>
    </c:import>
    <s:token></s:token>
</div>
</body>
<script type="text/javascript">
    $(function () {
        $(".JQueryflexme tr[id]").click(function () {
        	if($("input.JQuerypersonvalue:checkbox", $(this)).is(":checked")){
    			$("input.JQuerypersonvalue:checkbox", $(this)).attr("checked",false);
        	}else{
        		personvalue = this.id;
            	s_account = $(this).find("span#s_account").text();
        		$("input.JQuerypersonvalue:checkbox", $(this)).attr("checked", true);
        	}
            /* personvalue = this.id;
            s_account = $(this).find("span#s_account").text();
            $("input.JQuerypersonvalue", $(this)).attr("checked", "checked"); */
        });
    });

    $(document).ready(
        function () {
            $(".jqmWindow").jqm({
                modal: true,// 限制输入（鼠标点击，按键）的对话
                overlay: 20
                // 遮罩程度%
            }).jqmAddClose('.close');// 添加触发关闭的selector

            $('.JQueryflexme').flexigrid({
                height: 445,
                width: 'auto',
                minwidth: 30,
                //title : name,
                minheight: 80,
                buttons: [
                    {
                        name: '名称:<input type="test" id="mz" name="mz" style="width:100px;height:100%;border: 0;"/>帐号:<input type="test" id="zh" name="zh" style="width:100px;height:100%;border: 0;"/>会员级别:<select name="type" style="width:100px;height:100%;border: 0;"><option value="">请选择</option><c:forEach items="${p_list}" var="b"><option value="${b[1]}">${b[0]}</option></c:forEach></select>'

                    },
                    {
                        separator: true
                    }, {

                        name: '查询',
                        bclass: 'mysearch',
                        onpress: action
                        // 当点击调用方法
                    }, {
                        separator: true
                    }]
            });
            function action(com, grid) {
                switch (com) {
                    case '查询':
                        $('#formtest')
                            .attr(
                                "action",
                                basePath
                                + "/ea/wfjzh/ea_getPersonByDept.jspa?");
                        document.formtest.submit.click();
                        break;
                }
            }
        });
</script>
</html>
