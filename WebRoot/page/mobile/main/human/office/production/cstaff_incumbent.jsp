<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>人事生产-人员列表</title>
		<%@ page language="java" pageEncoding="UTF-8"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
		%>
		<script src="<%=basePath%>js/jquery.js" type="text/javascript">
		</script>
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
		</script>
		<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"
			type="text/css" />
		<script language="javascript"
			src="<%=basePath%>js/jquerypager/cstaff_incumbent.js"></script>
		<script language="javascript"
			src="<%=basePath%>js/jquerypager/jquery.form.js"></script>
		<link href="<%=basePath%>js/jquerypager/pager.css" type="text/css"
			rel="stylesheet"></link>
		<script type="text/javascript">
            var basePath = "<%=basePath%>";
            var ppageNumber = '${pageNumber}';
            var session_val = '${session_value}';
            var psearch = '${search}';
            var codimission = '${dimission}';  //离职员工原因
            var personvalue = "";
            var personIdentityCard;
            var  personurl = "";
            var staffName="";
            var retoken = 0;
            var staffsize ;//后台验证身份证时应该查到的人数
            var token = 0;
            var notoken = 0;
            var  positionPaysum = 0;
	        var  achievementsum =0;
	        var  Allsum =0;
	        var  workday =$("#workDateSaturation").attr("value");
            var perStaffID = '';	//父页面staffID;
            var appDate="";
            var pageNumber = 1;
            $(document).ready(function(){
            
                //图片预览
                $('#staffphoto').change(function(){
                    $t = $("table#stafftable");
                    $t.find('img#photo').attr("src", this.value).show()
                });
                //图片预览END
            })
            var loglist;
        </script>
		<script type="text/javascript">
   			function InitPager(RecordCount, PageIndex) {
    			$("#test").setPager({ RecordCount: RecordCount, PageIndex: PageIndex, buttonClick: PageClick });
   			};
			PageClick = function(RecordCount, PageIndex) {
    			InitPager(RecordCount, PageIndex);
   			};
        </script>
	</head>
	<body>
		<!--搜索窗口 -->
		<form name="cstaffSearchForm" id="cstaffSearchForm" method="post">
			<input type="submit" name="submit" style="display: none" />
			<div id="jqModelSearch">
				<table id="cataffSearchTable">
					<tr>
						<td align="center" colspan="2">
							查询信息
						</td>
					</tr>
					<tr>
						<td align="right">
							人员编号：
						</td>
						<td>
							<input name="searchCStaff.staffCode" />
						</td>
					</tr>
					<tr>
						<td align="right">
							人员姓名：
						</td>
						<td>
							<input name="searchCStaff.staffName" />
						</td>
					</tr>
					<tr>
						<td align="right">
							身份证：
						</td>
						<td>
							<input name="searchCStaff.staffIdentityCard" />
						</td>
					</tr>
					<tr>
						<td align="center" colspan="2">
							<input type="button" class="input-button" id="searchStaff"
								value="查询" />
							<input name="search" type="hidden" value="search" />
						</td>
					</tr>
				</table>
			</div>
		</form>
		<div>
			<div id="result"></div>
			<div id="test" class="pageinfo"></div>
		</div>
	</body>
</html>