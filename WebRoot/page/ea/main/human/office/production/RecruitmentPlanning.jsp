<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
        <%@ page language="java" pageEncoding="UTF-8" %>
        <%@ taglib uri="/struts-tags" prefix="s" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
          <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
        <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://"
        + request.getServerName() + ":" + request.getServerPort()
        + path + "/"; %>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="cache-control" content="no-cache"/>
        <title>招聘规划</title>
        <script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>
        <script src="<%=basePath%>js/jquery.js" type="text/javascript">
        </script>
        <script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/ea/human/office/production/recruitmentplan.js"></script>
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
			type="text/css" />
        <link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"
			type="text/css" />
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
        <script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/common/organizationTree.js">
        </script>
        <script type="text/javascript">
        	var notoken = 0;
	    	var token = 0;
	   		var select = 1;
	   		var recruitruleid = '';
	   		var basePath = '<%=basePath%>';
	   		var pNumber = ${pageNumber};
	   		var organizations = new Array();  //获取部门数组
	   		var search='${search}';
	   		var comID = '${account.companyID}';
	   		var postname = ""; //岗位显示赋值
        
        
		function importGY(attachSearchTable,checkopertionID,checkopertionName,childopertionName,url){ //打开页面
			$("#checkopertionID",$("#bankJqm")).attr("value",checkopertionID);
			$("#checkform",$("#bankJqm")).attr("value",attachSearchTable);
			$("#checkopertionName",$("#bankJqm")).attr("value",checkopertionName);
			$("#childopertionName",$("#bankJqm")).attr("value",childopertionName);
			
			var checkOrgID = $("#organizationid","#cstaffForm").val();
			if(checkOrgID != ''){
				$("#daoRu").attr("src",basePath+url+"?checkOrgID=" + checkOrgID);
			}else{
				alert('请先选择部门！');
				return;
			}
		  	$("#bankJqm").jqmShow();
		}
		$(document).ready(function() {//销售单FORM
			$("#DaoRuFan").click(function(){// 返回
		        $("#bankJqm").jqmHide();
			}); 
		    $("#DaoRuFanqd").click(function(){// 选择确定
			    var checkopertionID = window.frames["daoRu"].$('tr#'+childopertionID).find("span#"+checkopertionID).text();
				var checkopertionID =$("#checkopertionID",$("#bankJqm")).attr("value");
				var checkform =$("#checkform",$("#bankJqm")).attr("value");
				var checkopertionName = $("#checkopertionName",$("#bankJqm")).attr("value");
				var childopertionName = $("#childopertionName",$("#bankJqm")).attr("value");
				var childopertionID = window.frames["daoRu"].opertionID;
				if(childopertionID == ""){
					alert("请选择")
					return;
				}
				var no = window.frames["daoRu"].$('tr#'+childopertionID).find("span#"+checkopertionName).text();
				var childopertionName =window.frames["daoRu"].$('tr#'+childopertionID).find("span#"+childopertionName).text();
				if(checkopertionID != "")
					$("#"+checkopertionID,$("#"+checkform)).attr("value",childopertionID).trigger("blur");
				if(checkopertionName != ""){
					$("#"+checkopertionName,$("#"+checkform)).attr("value",childopertionName).trigger("blur");
				}
				if(checkopertionName =="staffName"){
					var starffName = no + "---" + childopertionName;
					$("#"+checkopertionName,$("#"+checkform)).attr("value",starffName).trigger("blur");
				}
				 $("#daoRu").attr("src","");
		         $("#bankJqm").jqmHide();
		    });
		});
		</script>
	</head>
	<body>
		 <form name="resruitmentplanForm" id="resruitmentplanForm" method="post">
			<input type="submit" name="submit" style="display: none ;" />
		    <div class="main_main">
			<table class="JQueryflexme">
                 <thead>
                     <tr>
						<th width="30" align="center">
							选择
						</th>
						<th width="50" align="center">
							岗位
						</th>
						<th width="50" align="center">
							部门
						</th>
						<th width="50" align="center">
							责任人
						</th>
						<th width="70" align="center">
							起时间
						</th>
						<th width="70" align="center">
							止时间
						</th>
						<th width="50" align="center">
							现有人数
						</th>
						<th width="50" align="center">
							拟增人数
						</th>
						<th width="150" align="center">
							增加原因
						</th>
						<th width="50" align="center">
							拟减人数
						</th>
						<th width="150" align="center">
							减员原因
						</th>
						<th width="60" align="center">
							拟录用人数
						</th>
						<th width="150" align="center">
							拟招聘渠道
						</th>
						<th width="70" align="center">
							制表日期
						</th>
					</tr>
                    </thead>
                    <tbody>
                     <c:forEach var="arr" items="${pageForm.list}">
						<tr id="${arr[14]}">
							<td>
								<input type="radio" name="a" class="JQuerypersonvalue"
									value="${arr[14] }"/>
							</td>
							<td>
								<span id="postname">${arr[0]}</span>
							</td>
							<td>
								<span id="organizationName">${arr[1]}</span>
							</td>
							<td>
								<span id="staffName">${arr[2]}</span>
							</td>
							<td>
								<span id="starttime">${arr[3]}</span>
							</td>
							<td>
								<span id="endtime">${arr[4]}</span>
							</td>
							<td>
								<span id="nownumbers">${arr[5]}</span>
							</td>
							<td>
								<span id="addnumbers">${arr[6]}</span>
							</td>
								<td>
								<span id="addreason">${arr[7]}</span>
							</td>
							<td>
								<span id="cutnumbers">${arr[8]}</span>
							</td>
								<td>
								<span id="cutreason">${arr[9]}</span>
							</td>
							<td>
								<span id="inputnumbers">${arr[10]}</span>
							</td>
							<td>
								<span id="channels">${arr[11]}</span>
							</td>
							<td>
								<span id="tabdate">${arr[12]}</span>
								<span style="display:none" id="recruitrulekey">${arr[13]}</span>
								<span style="display:none" id="recruitruleid">${arr[14]}</span>
								<span style="display:none" id="organizationid">${arr[15]}</span>
								<span style="display:none" id="organizationPost">${arr[16]}</span>
								<span style="display:none" id="staffid">${arr[17]}</span>
							</td>
						</tr>
					</c:forEach>
                    </tbody>	
                </table>
          		<c:import url="../../../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/safetyHealth/ea_getSafetyHealthList.jspa?pageNumber=${pageNumber}&search=${search}">
                </c:param>
            	</c:import>
        </div>
       </form>
		<div class="contentbannb jqmWindow jqmWindowcss1"
			style="top: 10%; width: 850px; " id="jqModel">
			<form name="cstaffForm" id="cstaffForm" method="post" enctype="multipart/form-data">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag" id="title">
					招聘规划信息
					<div class="close">
					</div>
				</div>
				<table width="99%" cellpadding="0" cellspacing="0" border="0" align="center"
					id="stafftable">
					<tr>
						<td align="right" width="14%" height="40">
							部门：
						</td>
						<td width="12%">
							<select name="dtrecruitrule.organizationid" id="organizationid"></select>
						</td>
						<td width="12%" align="right">
							岗位：
						</td>
						<td width="17%">
							<select name="dtrecruitrule.deptpostid" id="organizationPost">
				            <option value="">请先选择部门</option></select>
						</td>
						<td align="right" width="15%">
							责任人：
						</td>
						<td width="30%">
						<input type="hidden" id="staffid" name="dtrecruitrule.staffid"
								readonly="readonly" />
							<input id="staffName" name="meeting.staffName"
								class="put3" readonly="readonly" style="width:100px;"/>
								<a href="#" onclick="importGY('stafftable','staffid', 'staffName', 'childPartnerName','ea/cosincumbent/ea_getStaffForCashier.jspa');">选择</a>
						</td>
					</tr>
					<tr>
						<td align="right" height="40">
							起时间：
						</td>
						<td>
							<input name="dtrecruitrule.starttime" style="width:100px;" onfocus="date(this)" id="starttime"/>
						</td>
						<td align="right">
							止时间：
						</td>
						<td>
							<input name="dtrecruitrule.endtime" style="width:100px;" onfocus="date(this)" id="endtime"/>
						</td>
						<td align="right">
							现有人数：
						</td>
						<td>
							<input name="dtrecruitrule.nownumbers" style="width:100px;" id="nownumbers" class="positiveNum"/>
						</td>
					</tr>
					<tr>
						<td align="right" height="40">
							拟增人数：
						</td>
						<td>
						<input name="dtrecruitrule.addnumbers" id="addnumbers" style="width:100px;"/>
						</td>
						<td align="right">
							拟减人数：
						</td>
						<td>
							<input type="text"  name="dtrecruitrule.cutnumbers" id="cutnumbers" style="width:100px;"/>
						</td>
						<td align="right">
							拟录用人数：
						</td>
						<td>
							<input type="text"  name="dtrecruitrule.inputnumbers" id="addnumbers" style="width:100px;"/>
						</td>
					</tr>
					<tr>
						<td align="right" height="50">
							增加原因：
						</td>
						<td colspan="5">
							<textarea name="dtrecruitrule.addreason" id="addreason" rows="2" cols="80"></textarea>
						</td>
					</tr>
					<tr>
						<td align="right" height="50">
							减员原因：
						</td>
						<td colspan="5">
							<textarea name="dtrecruitrule.cutreason" id="cutreason" rows="2" cols="80"></textarea>
						</td>
					</tr>
					<tr>
						<td align="right" height="50">
							拟招聘渠道：
						</td>
						<td colspan="5">
							<textarea name="dtrecruitrule.channels" id="channels" rows="2" cols="80"></textarea>
						</td>
					</tr>
					<tr>
						<td colspan="6" align="center">
							<input type="hidden" name="dtrecruitrule.recruitruleid" id="recruitruleid" />
							<input type="hidden" name="dtrecruitrule.recruitrulekey" id="recruitrulekey" />
							<input type="hidden" name="dtrecruitrule.tabdate" id="tabdate" />
							<input type="button" class="input-button JQuerySubmit"
								style="cursor: pointer; width: 80px;" value="提交"/>
							<input type="button" class="input-button JQueryreturn"
								style="cursor: pointer; width: 80px;" value="取消"/>
						</td>
					</tr>
				</table>
				<s:token></s:token>
			</form>
		</div>

		<!--搜索窗口 -->
		<div class="jqmWindow" style="width: 420px; right: 35%; top: 10%"
			id="jqModelSearch">
			<form name="postSearchForm" id="postSearchForm" method="post">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table width="460px" id="cataffSearchTable">
					<tr>
						<td align="right" height="30">
							部门：
						</td>
						<td>
							<select name="dtrecruitrule.organizationid" id="organizationid"></select>
						</td>
					</tr> 
					<tr>
						<td align="right" height="30">
							岗位：
						</td>
						<td>
							<select name="dtrecruitrule.deptpostid" id="organizationPost">
				            <option value="">请先选择部门</option></select>
						</td>
					</tr>
					<tr>
						<td align="right" height="30">
							制表日期：
						</td>
						<td>
							<input name="dtrecruitrule.tabdate" id="tabdate" onfocus="date(this)" size="12"/>
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="searchStaff"
						value=" 查询 " />
					<input name="search" type="hidden" value="search" />
				</div>
			</form>
		</div>
		
		<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
			<!----------------------------------------选择责任人---------------------------------------- -->
		<div id="bankJqm" class="jqmWindow"
			style="width: 95%; height: 400px; absolute; display: none; left: 1%; top: 1%; background: #eff">
			<div style="background: #efg; margin-right: 500px;">
				<input style="display: none;" id="checkopertionID" />
				<input style="display: none;" id="checkopertionName" />
				<input style="display: none;" id="childopertionName" />
				<input style="display: none;" id="checkform" />
			</div>
			<iframe name="daoRu" id="daoRu" width="100%" height="360px"
				frameborder="0" ></iframe>
			<div align="center">
				<input type="button" class="input-button" id="DaoRuFanqd" value=" 确定 "
					style="cursor: hand;" />
				<input type="button" class="input-button" id="DaoRuFan" value="关闭 "
					style="cursor: hand;" />
			</div>
		</div>
</body> 
</html>