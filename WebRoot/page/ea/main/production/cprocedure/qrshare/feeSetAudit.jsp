<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=no, minimal-ui" />


<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta name="format-detection" content="telephone=no, email=no" />
<meta name="screen-orientation" content="portrait">
<meta name="x5-orientation" content="portrait">
<script
	src="<%=basePath%>js/ea/production/cprocedure/qrshare/setHtmlFont.js"
	type="text/javascript"></script>
<link rel="stylesheet" href="<%=basePath%>/css/BuildPlatform/base.css">
<link rel="stylesheet"
	href="<%=basePath%>/css/ea/production/qrshare/site_manger.css">

<script src="<%=basePath%>/js/jquery.min.js"></script>
	<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>

<title>免费设置</title>
</head>

<script>
     var basePath = '<%=basePath%>';

    $(function(){
        //提交审核
        $("#t1").click(function(){

            var carNumber = "${carManageAudit.carNumber}";
            var  carmID = "${carManageAudit.carmID}";
            var status = "${carManageAudit.status}";
            var starttime = "${carManageAudit.indate}";
            var endtime = $("#end").val();
            var  siteName = "${carManageAudit.siteName}";
            var  siteId = "${carManageAudit.siteId}"

            $.ajax({
                  type:"post",
				  url:basePath+"/ea/qrshare/sajax_ea_saveTimeInfo.jspa",
				   async:false,
				  dataType:"json",
                  data:  {
            "carManageAudit.carNumber":carNumber,
	        "carManageAudit.carmID":carmID,
	    	"carManageAudit.status":status,
               starttime:starttime,
            endtime:endtime,
           "carManageAudit.siteName":siteName,
           "carManageAudit.siteId":siteId

				  },
                success:function(data){
					var  m = eval("("+data+")");
					var cmaID = m.cmaID;
                    localStorage.setItem("title", siteName+"车牌"+carNumber+"免费时长设置");
                    localStorage.setItem("source","fs");
                    localStorage.setItem("htmlUrl","ea/qrshare/ea_getCarTimeInfo.jspa?cmaID="+cmaID);
                    localStorage.setItem("tableName","CarManageAudit");
                    localStorage.setItem("idName","cmaID");
                    localStorage.setItem("idValue",cmaID);
                    localStorage.setItem("stateName","auditStatus");
                    localStorage.setItem("stateValue","02");
                    localStorage.setItem("refundValue","03");
                    window.location.replace(basePath+"page/ea/main/office_ea/contract/selectCompany.jsp?typee=L");
				},
				error:function(data){
                    console.log("出错");
				}

			})


		})

	})
     
</script>

<body>
	<header class="com_head">
		<a href="javascript:void(0);" class="back"
			onclick="javascript: window.history.go(-1);return false;"></a>
		<h1>

		免费时长设置
		</h1>
	</header>

	<div class="wrap_page" style="margin-top: 1.16rem">
		<dl class="site_det_box">



			<dd>
				<span class="dd_L"> 车牌号： </span> <span class="dd_R">
				${carManageAudit.carNumber} </span>
			</dd>
			<dd>
				<span class="dd_L"> 进/出： </span> <span class="dd_R">
				<c:if test="${carManageAudit.status eq '0'}">出</c:if> 	<c:if test="${carManageAudit.status eq '1'}">进</c:if> </span>
			</dd>

			<fieldset>
				<legend>&nbsp;免费时间段&nbsp;</legend>
				<dd class="startdd">
					<span class="dd_L"> &nbsp; &nbsp;开始时间： </span> <span class="dd_R">
		          	<input type="text"  value="${fn:substring(carManageAudit.indate,0,19)}" readonly name="starttime" id="startDate"/> </span> </span>
				</dd>
                  <c:if test="${carManageAudit.status eq '1'}">
				  <dd class="enddd">
					  <span class="dd_L"> &nbsp; &nbsp; 截止时间： </span> <span class="dd_R">
				<input type="text" onFocus="WdatePicker({lang:'zh-cn',

dateFmt:'yyyy-MM-dd HH:mm:ss', minDate:'#F{$dp.$D(\'startDate\')}'})"  value="${fn:substring(carManageAudit.outdate,0,19)} " id="end" name="endtime" />


				</span>
				  </dd>


			  </c:if>
				<c:if test="${carManageAudit.status eq '0'}">
					<dd class="enddd">
						<span class="dd_L"> &nbsp; &nbsp; 截止时间： </span> <span class="dd_R">
				<input type="text" onFocus="WdatePicker({lang:'zh-cn',

dateFmt:'yyyy-MM-dd HH:mm:ss', minDate:'#F{$dp.$D(\'fout\')}'})"  value="${fn:substring(outtime,0,19)} " id="end" name="endtime" />

	              <input type="hidden"  value="${fn:substring(outtime,0,19)}" id="fout" /> </span> </span>
				</span>
					</dd>


				</c:if>

			</fieldset>



		</dl>
		<div  class="fee">
			<input type="button" value="提交审核" id="t1" />
		</div>
	</div>


</body>

</html>
