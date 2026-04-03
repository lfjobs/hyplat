<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>考试成绩统计</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script>

<script src="<%=basePath%>js/jquery-1.3.1.js"  type="text/javascript"></script>
<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
<link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
<script  src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
<style type="text/css">
div{
	margin: 0px;
	padding: 0px;
	border: 0px;
}
table.JQueryflexme td,table.JQueryflexme input,table.JQueryflexme select{
	border: 0px;
	margin: 0px;
	width: 100%;
	height: 100%;
}
</style>
<script type="text/javascript" >
   var token = 0;
   var select = 1;
   var drivingtestid = '';
   var basePath = '<%=basePath%>';
   var pNumber = ${pageNumber};
   var notoken = 0;
   var docstatus='${docstatus}';//单据状态  		
   var sdate='${sdate}';
   var edate='${edate}';
   var search='${search}';
   var other='${other}';//区别导航页面 
   
   var extensionStaffCoach='${extensionStaffCoach}';//新版学员报名标识符
   var studentID='${dtDrivingPrincipal.studentid}';//新版报名学员ID
	$(function(){
		if($.browser.version=="6.0"){
			$("html")[0].scrollHeight;
			$("html").height();
		}
		$("html").css("overflow-x",extensionStaffCoach=='extensionStaffStudent'?"scroll":"");
		$("html").css("overflow-y","hidden");
	});
	 function getValueForParm(e){ //打开页面
	 		trID=$(e).parents("tr").attr("id");
	    	if($(e).attr("id")=="firstExaminer"){
	    		id="firstExaminerID";
	    		nameVar="firstExaminer";
	    	}else{
	    		id="secondExaminerID";
	    		nameVar="secondExaminer";
	    	}
	  	$("#ifr").attr("src",basePath+"ea/cstaff/ea_getListCStaffByCompanyID.jspa?cstaff.status=99");
	  	$("#jqmWindow2").jqmShow();
	}
	var trID="";
	var id="";
	var nameVar="";
	$(document).ready(function() {
		$("#isBack").click(function(){// 返回
	       $("#jqmWindow2").jqmHide();
	    }); 
	   
		$("#isSubmit").click(function(){// 选择确定
			var value1 = window.frames["ifr"].personvalue;//弹出框的页面必须声明opertionID这个参数接收id
			if(value1 == ""){
				alert("请选择")
				return;
			}
			var value2 = window.frames["ifr"].$("tr#"+value1).find("span#staffName").text();//弹出框的页面存在于span中才取得到
			var value3 = window.frames["ifr"].$("tr#"+value1).find("span#staffID").text();//弹出框的页面存在于span中才取得到
			
			$("tr#"+trID).find("input#"+id).val(value3);
			$("tr#"+trID).find("input#"+nameVar).val(value2);
			$("#ifr").attr("src","");
	        $("#jqmWindow2").jqmHide();
	    });
	});
</script>
<script type="text/javascript" src="<%=basePath%>js/ea/driving/driving_list_tongji.js"></script></head>
<body>
<form  name="addressForm" id="addressForm" method="post"><s:token></s:token><input type="submit" name="submit" style="display:none"/>

<div id="main_main" class="main_main">
  <table   class="JQueryflexme">
	  	<thead>
		 	    <tr>
			 	    <th width="30" align="center"  >选择</th>
			 	    <th width="30" align="center"  >序号</th>
		            <th width="80" align="center" >报名时间</th>
		            <th width="80" align="center" >科目类型</th>
		            <th width="80" align="center" >车型</th>
		            <th width="60" align="center" >姓名</th>
		            <th width="60" align="center" >性别</th>
		            <th width="145" align="center" >身份证号</th>
		            <th width="80" align="center" >电话</th>
		            <th width="80" align="center" >教练员</th>
		            <th width="80" align="center" >约考日期</th>
		            <th width="80" align="center" >预约方式</th>
		            <th width="80" align="center" >约考责任人</th> 
		            <th width="80" align="center" >考试结果</th> 
		            <th width="145" align="center" >第一次考试时间</th> 
		            <th width="135" align="center" >第一次考试扣分项目</th>
		            <th width="100" align="center" >第一次学员得分</th>
		            <th width="100" align="center" >第一次考试结果</th>
		            <th width="100" align="center" >第一次考试考官</th>
		            <th width="145" align="center" >第二次考试时间</th> 
		            <th width="135" align="center" >第二次考试扣分项目</th>
		            <th width="100" align="center" >第二学员得分</th>
		            <th width="100" align="center" >第二考试结果</th>
		            <th width="100" align="center" >第二次考试考官</th>
	      		</tr>
	    </thead>
		<tbody>
			<% int i=1; %>
          <s:iterator value="pageForm.list" var="ls">
	          <tr  id="${ls[10]}">
	         	 	<td >
						<input type="checkbox" name="drivingtestid" class="chx data" value="${ls[10]}" ${extensionStaffCoach==null?'disabled':''} ${extensionStaffCoach==null?'checked':''} />
					</td>
	            	<td >
		                <span> <%=i%></span>
		            </td>
		            <td >
		                <span  >${ls[0]}</span>
					</td>
					<td >
		                <span  >${ls[12]}</span>
					</td>
					<td >
		                <span  >${ls[1]}</span>
					</td>
					<td >
		                <span  >${ls[2]}</span>
					</td>
					<td >
		                <span  >${ls[3]}</span>
					</td>
					<td >
		                <span  >${ls[4]}</span>
					</td>
					<td >
		                <span  >${ls[5]}</span>
					</td>
					<td >
		                <span  >${ls[6]}</span>
					</td>
					<td >
		                <span  >${fn:substring(ls[7],0,11)}</span>
					</td>
					<td >
		                <span  >${ls[13]}</span>
					</td>
					<td >
		                <span  >${ls[14]}</span>
					</td>
					<td >
		                <span id="examresult" >${ls[9]}</span>
		                <span id="studentid" style="display: none;">${ls[11]}</span>
					</td>
					<td >
						<%-- <span id="firstExamTime" class="data1">${ls[15]}</span> --%>
		                <input  id="firstExamTime" name="firstExamTime"  class="data"  value="${ls[15]}" size="9" ${extensionStaffCoach==null?'disabled':''} onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
					</td>
					<td >
		               <span id="firstExamPoints" class="data1" style="display: none;">${ls[16]}</span>
		               <%--  <input id="firstExamPoints" name="firstExamPoints" class="data"  value="${ls[16]}" size="9" ${extensionStaffCoach==null?'disabled':''} />  --%>
						<select id="firstExamPoints" name="firstExamPoints" class="data" >
						 				<option value="">请选择</option>
						 				<optgroup label="科目一">
						 					<option value="机械常识">机械常识</option>
						 					<option value="法律法规">法律法规</option>
						 					<option value="安全文明">安全文明</option>
						 				</optgroup>
						 				<optgroup label="科目二">
						 					<option value="倒车入库">倒车入库</option>
						 					<option value="侧方移位">侧方移位</option>
						 					<option value="单边桥">单边桥</option>
						 					<option value="直角转弯">直角转弯</option>
						 					<option value="S路">S路</option>
						 					<option value="坡道起步">坡道起步</option>
						 				</optgroup>
						 				<optgroup label="科目三">
						 					<option value="加减挡">加减挡</option>
						 					<option value="靠边停车">靠边停车</option>
						 					<option value="过红绿灯">过红绿灯</option>
						 					<option value="超车">超车</option>
						 					<option value="灯光使用">灯光使用</option>
						 					<option value="窄路掉头">窄路掉头</option>
						 					<option value="超实线">超实线</option>
						 				</optgroup>
						 				<optgroup label="科目四">
						 					<option value="安全驾驶">安全驾驶</option>
						 				</optgroup>
						 			</select> 
					</td>  
					<td >
							<%-- <span id="examScore" class="data1">${ls[8]}</span> --%>
		                	<input id="examScore" name="examScore" class="data"    value="${ls[8]}" size="9" ${extensionStaffCoach==null?'disabled':''} />
					</td>
					<td >
						<span id="firstExamresult" class="data1" style="display: none;">${ls[23]}</span>
		                <select id="firstExamresult" name="firstExamresult" class="data" >
		               		<option value="">请选择</option>
		                	<option value="合格">合格</option>
		                	<option value="不合格">不合格</option>
		                	<option value="缺考">缺考</option>
		                	<option value="误报">误报</option>
		                </select>
					</td>
					<td >
						<%-- <span id="firstExaminer" class="data1">${ls[17]}</span> --%>
						<input type="hidden" id="firstExaminerID" name="firstExaminerID" class="data" value="${ls[24]}"/>
		                 <input id="firstExaminer" name="firstExaminer"  class="data"  value="${ls[17]}"   size="9" ${extensionStaffCoach==null?'disabled':''} style="width: 75%;"/>
						<a  href="#" onclick="getValueForParm(this)" id="firstExaminer" style="width: 25%;" >选择</a>
					</td>
					<td >
						<%-- <span id="secondExamTime" class="data1">${ls[18]}</span> --%>
		                <input id="secondExamTime" name="secondExamTime"  class="data"   value="${ls[18]}" size="9" ${extensionStaffCoach==null?'disabled':''} onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
					</td>
					<td >
		                <span id="secondExamPoints" class="data1" style="display: none;">${ls[19]}</span>
		                <%-- <input id="secondExamPoints" name="secondExamPoints"  class="data"  value="${ls[19]}" size="9" ${extensionStaffCoach==null?'disabled':''} /> --%>
						<select id="secondExamPoints" name="secondExamPoints" class="data" >
						 				<option value="">请选择</option>
						 				<optgroup label="科目一">
						 					<option value="机械常识">机械常识</option>
						 					<option value="法律法规">法律法规</option>
						 					<option value="安全文明">安全文明</option>
						 				</optgroup>
						 				<optgroup label="科目二">
						 					<option value="倒车入库">倒车入库</option>
						 					<option value="侧方移位">侧方移位</option>
						 					<option value="单边桥">单边桥</option>
						 					<option value="直角转弯">直角转弯</option>
						 					<option value="S路">S路</option>
						 					<option value="坡道起步">坡道起步</option>
						 				</optgroup>
						 				<optgroup label="科目三">
						 					<option value="加减挡">加减挡</option>
						 					<option value="靠边停车">靠边停车</option>
						 					<option value="过红绿灯">过红绿灯</option>
						 					<option value="超车">超车</option>
						 					<option value="灯光使用">灯光使用</option>
						 					<option value="窄路掉头">窄路掉头</option>
						 					<option value="超实线">超实线</option>
						 				</optgroup>
						 				<optgroup label="科目四">
						 					<option value="安全驾驶">安全驾驶</option>
						 				</optgroup>
						 			</select> 
					</td>
					<td >
						<%-- <span id="secondExamScore" class="data1">${ls[20]}</span> --%>
		                <input id="secondExamScore" name="secondExamScore"   class="data"  value="${ls[20]}"  size="9" ${extensionStaffCoach==null?'disabled':''} />
					</td>
					<td >
						<span id="secondExamresult" class="data1" style="display: none;">${ls[21]}</span>
		                <select id="secondExamresult" name="secondExamresult" class="data" >
		                	<option value="">请选择</option>
		                	<option value="合格">合格</option>
		                	<option value="不合格">不合格</option>
		                	<option value="缺考">缺考</option>
		                	<option value="误报">误报</option>
		                </select>
					</td>
					<td >
						<%-- <span id="secondExaminer" class="data1">${ls[22]}</span> --%>
						<input type="hidden" id="secondExaminerID" name="secondExaminerID" class="data" value="${ls[25]}"/>
		                <input id="secondExaminer" name="secondExaminer"  class="data"  value="${ls[22]}" size="9"  ${extensionStaffCoach==null?'disabled':''} style="width: 75%;"/>
						<a  href="#" onclick="getValueForParm(this)" id="secondExaminer" style="width: 25%;">选择</a>
					</td>
	          </tr>
	          <%  i++; %>
          </s:iterator>
    	</tbody>
  </table>
<input name="dtDrivingTest.examresult" id="examresult" type="hidden" />
</div>
</form>
 <c:import url="../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/driving/ea_getStatisticsList.jspa?pageNumber=${pageNumber}&docstatus=${docstatus}&sdate=${sdate}&edate=${edate }&search=${search}&other=${other}
					&extensionStaffCoach=${extensionStaffCoach}&dtDrivingPrincipal.studentid=${dtDrivingPrincipal.studentid}"></c:param>
</c:import>

<!-- 社会人力资源 -->
<div id="jqmWindow2" class="jqmWindow"
	style="width: 98%; height: 320px; absolute; display: none; left: 1%; top: 1%; background: #eff">
	<div align="center">
		<iframe name="ifr" id="ifr" width="100%" height="280px"
		frameborder="0"></iframe>
		<input type="button" class="input-button" id="isSubmit" value=" 确定 "
			style="cursor: hand" />
		<input type="button" class="input-button" id="isBack" value=" 关闭 "
			style="cursor: hand" />
	</div>
 </div>
<div id="jqModelSearchss">
				<form name="SearchForm" id="SearchForm" method="post">
					<input type="submit" name="submit" style="display: none" />
					
							学员名称：
							
							<input type="text" id="studentname" name="dtDrivingPrincipal.studentname" size="5"/>
							
							教练名称：
							
							<input type="text" id="coachname" name="dtDrivingPrincipalType.coachname" size="5"/>
							
							约考时间：
							
							<input type="text" id="sdate" name="sdate" size="8" onfocus="date(this)"
									/>至
							<input type="text" id="edate" name="edate" size="8" onfocus="date(this)"
									/>
							
							科目类型：
							
							<select name="dtDrivingTest.examtype" >
								<option value="">全部</option>
								<option value="01">科一</option>
								<option value="02">科二</option>
								<option value="03">科三</option>
								<option value="04">科四</option>
							</select>
							
							考试结果：
							
							<select name="dtDrivingTest.examresult" class="ksjg">
								<option value="全部">全部</option>
								<option value="">未统计</option>
								<option value="00">合格</option>
								<option value="01">不合格</option>
								<option value="02">缺考</option>
								<option value="03">误报</option>
							</select>
	                         <input name="docstatus"  type="hidden" value="${docstatus}"/>
	                            <input type="button" class="input" id="tosearch" value=" 查询 " /><input name="search" type="hidden" value="search" />
	                        
				</form>
			</div>
	
	<form name="cstaffForm" id="cstaffForm" method="post">
			<input type="submit" name="submit" style="display: none" />
			<input type="hidden" name="str" id="strs"/>
			<input type="hidden" name="strsScore" id="strsScore"/>
			 <input name="docstatus"  type="hidden" value="${docstatus}"/>	
			  <input name="dtDrivingTest.examresult" id="examresult" type="hidden" />	
			<div class="jqmWindow jqmWindowcss3"
				style="top: 10%; width: 850px; left: 35%" id="jqModel">
				<div class="content">
					<div class="contentbannb">
						<div class="drag">
							学员信息
							<div class="close"></div>
						</div>
					</div>
				</div>
			</div>
			<s:token></s:token>
		</form>		
<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
</body>
</html>
