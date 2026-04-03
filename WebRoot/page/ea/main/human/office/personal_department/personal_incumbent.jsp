<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<!-- 部门正式人员 -->
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>人员列表</title>
		<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
		<script src="<%=basePath%>js/jquery.js" type="text/javascript">
		</script>
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
		</script>
		<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"
			type="text/css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js">
		</script>
		<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/My97DatePicker/WdatePicker.js">
		</script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
		</script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js">
		</script>
		<script src="<%=basePath%>js/ea/human/staff_info.js">
		</script>
		<script src="<%=basePath%>js/ea/human/cstaff.js">
		</script>
		<script type="text/javascript"
			src="<%=basePath%>js/common/organizationTree.js"></script>

		<script type="text/javascript">
            var basePath = "<%=basePath%>";
            var personvalue = "";
            var opertionID =""
             var personurl = "";
             var staffName="";
            var ppageNumber=${pageNumber};
            var treeID = "";
            var treeName = "";
            var treePID = "";
            var treePName = "";
            var search = false;
      		var long = 1;
      		
	        document.onkeydown = function(evt){//捕捉回车   
	   			 evt = (evt) ? evt : ((window.event) ? window.event : "") //兼容IE和Firefox获得keyBoardEvent对象
	   			 var key = evt.keyCode ? evt.keyCode : evt.which; //兼容IE和Firefox获得keyBoardEvent对象的键值
	   			 if (key == 13&&long == 1&&search==true) { //判断是否是回车事件。
	   			  $("input#searchStaff").trigger("click");
	   				  search = false;
	   			 }
			}
            
            if(check(parent.frames["left_tree"]) == true){
            	treeID = parent.frames["leftFrame"].tree.getSelectedItemId(); 
            	treeName = parent.frames["leftFrame"].tree.getItemText(treeID);
			    treePID = parent.frames["leftFrame"].tree.getParentId(treeID);
			    treePName = parent.frames["leftFrame"].tree.getItemText(treePID);
            }
	       
            var psearch='${search}';
            var notoken = 0;
            $(document).ready(function(){
                //图片预览
                $('#staffphoto').change(function(){
                    $t = $("table#stafftable");
                    $t.find('img#photo').attr("src", this.value).show()
                });
                //图片预览END
                 })
                 
            function check(ck){
	           	if(ck != null && ck != "" && ck != "undefined"){
	           		return true;
	           	}else{
	           		return false;
	           	}
            }
        </script>

		<script type="text/javascript"
			src="<%=basePath%>js/ea/human/office/personal_department/personal_incumbent.js"></script>
	</head>
	<body>
		<div class="main_main">
			<table class="JQueryflexme">
				<thead>
					<tr class="tablewith">
						<th width="40" align="center">
							选择
						</th>
						<th width="100" align="center">
							人员编号
						</th>
						<th width="100" align="center">
							档案编号
						</th>
						<th width="100" align="center">
							员工工种
						</th>
						<th width="100" align="center">
							人员姓名
						</th>
						<th width="100" align="center">
							岗位名称
						</th>
						<th width="100" align="center">
							曾用名
						</th>
						<th width="100" align="center">
							性别
						</th>
						<th width="100" align="center">
							出生日期
						</th>
						<th width="100" align="center">
							国籍
						</th>
						<th width="100" align="center">
							籍贯
						</th>
						<th width="100" align="center">
							民族
						</th>
						<th width="200" align="center">
							身份证
						</th>
						<!--  
						<th width="200" align="center">
							身份证地址
						</th>
						<th width="100" align="center">
							电话
						</th>
						<th width="100" align="center">
							qq
						</th>
						<th width="100" align="center">
							邮箱
						</th>
						<th width="100" align="center">
							录入时间
						</th>
						<th width="100" align="center">
							政治面貌
						</th>
						<th width="100" align="center">
							文化程度
						</th>
						<th width="100" align="center">
							婚姻状况
						</th>
						<th width="100" align="center">
							健康状况
						</th>
						<th width="100" align="center">
							银行帐号
						</th>
						<th width="100" align="center">
							备注
						</th>
						-->
					</tr>
				</thead>
				<tbody>
					<%
						int number = 1;
					%>
					<s:iterator value="pageForm.list" var="lists">
						<tr id="${lists[0]}" class="${lists[4]}">
							<td>
								<input type="radio" name="a" class="JQuerypersonvalue"
									value="${lists[0]}" />
							</td>
							<td>
								<span id="staffCode">${lists[1]}</span>
							</td>
							<td>
								<span id="recordCode">${lists[2]}</span>
							</td>
							<td>
								<span id="categoryname">${lists[3]==null?"暂未分配":lists[3]}</span>
							</td>
							<td>
								<span id="staffName">${lists[4]}</span>
							</td>
							<td>
								<span id="postName">${lists[5]}</span>
							</td>
							<td>
								<span id="usedNmae">${lists[6]}</span>
							</td>
							<td>
								<span id="sex">${lists[7]}</span>
							</td>
							<td>
								<span id="birthday" class="datas">${lists[8]}</span>
							</td>
							<td>
								<span id="nationality">${lists[9]}</span>
							</td>
							<td>
								<span id="nativePlace">${lists[10]}</span>
							</td>
							<td>
								<span id="nation">${lists[11]}</span>
							</td>
							<td>
								<span id="staffIdentityCard">${lists[12]}</span>
								<span style="display: none" id="schedulingID">${schedulingID}</span>
								<span style="display: none" id="staffKey">${lists[24]}</span>
								<span style="display: none" id="staffID">${lists[0]}</span>
								<span style="display: none" id="photo">${lists[26]}</span>
							</td>
							<!--  
							<td>
								<span id="staffAddress">${lists[13]}</span>
							</td>
							<td>
								<span id="reference">${lists[14]}</span>
							</td>
							<td>
								<span id="referenceCode">${lists[15]}</span>
							</td>
							<td>
								<span id="referenceOrganization">${lists[16]}</span>
							</td>
							<td>
								<span id="verifyTime" class="datas">${lists[17]}</span>
							</td>
							<td>
								<span id="politicsStatus">${lists[18]}</span>
							</td>
							<td>
								<span id="culturalDegree">${lists[19]}</span>
							</td>
							<td>
								<span id="marriage">${lists[20]}</span>
							</td>
							<td>
								<span id="health">${lists[21]}</span>
							</td>
							<td>
								<span id="bankNum">${lists[22]}</span>
							</td>
							<td>
								<span id="staffDesc">${lists[23]}</span>
								<span style="display: none" id="address">${lists[25]}</span>
							</td>
							-->
						</tr>
						<%
							number++;
						%>
					</s:iterator>
				</tbody>
			</table>
			<c:import url="../../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/cosincumbent/ea_getStaffList.jspa?pageNumber=${pageNumber}&search=${search}&searchValue=${searchValue}">
				</c:param>
			</c:import>

			<div>
				<iframe src="" name="main" scrolling="no" style="width:100%;height:0;"
					marginwidth="0" marginheight="0" frameborder="0"
					id="mainframe" border="0" framespacing="0" noresize="noResize"
					vspale="0"> </iframe>
			</div>
		</div>
		<form name="cstaffForm" id="cstaffForm" method="post"
			enctype="multipart/form-data">
			<div class="jqmWindow jqmWindowcss2" style="width: 700px; top: 10%"
				id="jqModel">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					员工详细信息
					<div class="close">
					</div>
				</div>
				<table width="690" height="211" border="0" align="center"
					cellpadding="0" cellspacing="0" id="stafftable">
					<tr>
						<td width="103" height="27" align="right">
							员工编号：
						</td>
						<td width="140">
							<input name="cstaff.staffCode" class="model3" id="staffCode"
								size="20" />
						</td>
						<td width="147" align="right">
							档案编号：
						</td>
						<td width="148">
							<input name="cstaff.recordCode" class="model3" id="recordCode"
								size="20" />
						</td>
						<td width="144" rowspan="6" align="center">
							<img width="99" height="135" id="photo" />
						</td>
					</tr>
					<tr>
						<td height="27" align="right">
							姓名：
						</td>
						<td>
							<input name="cstaff.staffName" class="notnull model3"
								id="staffName" size="20" />
						</td>
						<td align="right">
							曾用名：
						</td>
						<td>
							<input name="cstaff.usedNmae" class="model3" id="usedNmae"
								size="20" />
						</td>
					</tr>
					<tr>
						<td height="27" align="right">
							性别：
						</td>
						<td>
							<select id='sex' name="cstaff.sex">
							</select>
						</td>
						<td align="right">
							出生日期：
						</td>
						<td>
							<input name="cstaff.birthday" class="model3" id="birthday"
								size="20" onfocus="date(this);" />
						</td>
						<td width="8">
						</td>
					</tr>
					<tr>
						<td height="27" align="right">
							民族：
						</td>
						<td>
							<select id="nation" name="cstaff.nation">
							</select>
						</td>
						<td align="right">
							籍贯：
						</td>
						<td>
							<select id="nativePlace" name="cstaff.nativePlace">
							</select>
						</td>
						<td>
						</td>
					</tr>
					<tr>
						<td height="27" align="right">
							身份证号码：
						</td>
						<td>
							<input name="cstaff.staffIdentityCard" class="notnull model3"
								id="staffIdentityCard" size="20" />
						</td>
						<td align="right">
							国籍：
						</td>
						<td>
							<select id="nationality" name="cstaff.nationality">
							</select>
						</td>
						<td>
						</td>
					</tr>
					<!--  
					<tr>
						<td align="right">
							电话：
						</td>
						<td>
							<input name="cstaff.reference" class="model3" type="text"
								id="reference" size="20" />
						</td>
						<td align="right">
							邮箱：
						</td>
						<td>
							<input name="cstaff.referenceOrganization" class="model3"
								type="text" id="referenceOrganization" size="20" />
						</td>
					</tr>
					<tr>
						<td height="27" align="right">
							QQ：
						</td>
						<td>
							<input name="cstaff.referenceCode" class="model3" type="text"
								id="referenceCode" size="20" />
						</td>
						<td align="right">
							录入时间：
						</td>
						<td>
							<input name="cstaff.verifyTime" type="text" class="model3"
								id="verifyTime" size="20" onfocus="date(this);" />
						</td>
						<td>
							<input name="photo" id="staffphoto" class="input01"
								contentEditable="false" type="file" />
							<input name="cstaff.photo" type="hidden" id="photo" size="14" />
							<input name="cstaff.staffID" id="staffID" type="hidden" />
							<input name="cstaff.staffKey" id="staffKey" type="hidden" />
							<input name="cstaff.schedulingID" id="schedulingID" type="hidden" />
							<input name="sub" value="${session_value}" type="hidden" />
							<!-- 代替token
						</td>
					</tr>
					<tr>
						<td height="27" align="right">
							政治面貌：
						</td>
						<td>
							<select name="cstaff.politicsStatus" id="politicsStatus"
								class="model3">
								<option value="群众" selected="selected">
									群众
								</option>
								<option value="共青团员">
									共青团员
								</option>
								<option value="中共党员">
									中共党员
								</option>
								<option value="民主党">
									民主党
								</option>
								<option value="其他">
									其他
								</option>
							</select>
						</td>
						<td align="right">
							婚姻状况：
						</td>
						<td>
							<select name="cstaff.marriage" id="marriage" class="model3">
								<option value="未婚" selected="selected">
									未婚
								</option>
								<option value="已婚">
									已婚
								</option>
							</select>
						</td>
						<td>
							银行帐号：
							<input name="cstaff.bankNum" type="text" class="model3"
								id="bankNum" size="20" style="width: 80px" />
						</td>
					</tr>
					<tr>
						<td height="27" align="right">
							健康状况：
						</td>
						<td>
							<input name="cstaff.health" class="model3" type="text"
								id="health" size="20" />
						</td>
						<td align="right">
							文化程度：
						</td>
						<td>
							<select name="cstaff.culturalDegree" id="culturalDegree"
								class="model3">
								<option value="硕士">
									硕士
								</option>
								<option value="博士">
									博士
								</option>
								<option value="本科">
									本科
								</option>
								<option selected="selected" value="专科">
									专科
								</option>
								<option value="中专/高中">
									中专/高中
								</option>
								<option value="中专以下">
									中专以下
								</option>
							</select>
						</td>
						<td>
						</td>
					</tr>
					<tr>
						<td height="40" align="right">
							身份证地址：
						</td>
						<td class="JQueryaddress" colspan="7">
							<input name="cstaff.address" id="address" type="hidden" />
							<select name="addressProvince" id="province" number='0'
								style="width: 110px;">
							</select>
							<!-- <option>选择省</option>
							<select name="addressCity" id="city" number='1'
								style="width: 110px;">
							</select>
							<select name="addressCounty" id="county" number='2'
								style="width: 110px;">
							</select>
							<select name="addressTown" id="addressTown" number='3'
								style="width: 110px;">
							</select>
							<select name="addressVillage" id="addressVillage" number='4'
								style="width: 110px;">
							</select>
							<select name="addressCommunity" id="addressCommunity" number='5'
								style="width: 110px;">
							</select>
							<!-- <option>选择省</option>
							<select name="addressFloor" id="addressFloor" number='6'
								style="width: 110px;">
							</select>
							<select name="addressLayer" id="addressLayer" number='7'
								style="width: 110px;">
							</select>
							<select name="addressSize" id="addressSize" number='8'
								style="width: 110px;">
							</select>
						</td>
					</tr>
					<tr>
						<td height="27" align="right">
							备注：
						</td>
						<td colspan="3">
							<textarea name="cstaff.staffDesc" cols="40" rows="5"
								id="staffDesc">
                            </textarea>
						</td>
					</tr>
					-->
				</table>
				<div align="center">
				</div>
			</div>
		</form>
		<div class="menu01">
			<ul>
				<li>
					<ul class="menu00" style="z-index: 1">
						<!--
                        <li>
                        <a href="#" id="pdetails">人员基本信息</a>
                        </li>
                        -->
						<li>
							<a href="#" id="Address">地址管理</a>
						</li>
						<li>
							<a href="#" id="contact">联系方式</a>
						</li>
						<li>
							<a href="#" id="education">学历学位</a>
						</li>
						<li>
							<a href="#" id="precord">个人履历</a>
						</li>
						<li>
							<a href="#" id="showfamily">家庭成员</a>
						</li>
						<li>
							<a href="#" id="showhealth">健康状况</a>
						</li>
						<li>
							<a href="#" id="political">政治面貌</a>
						</li>
						<li>
							<a href="#" id="award">奖励情况</a>
						</li>
						<li>
							<a href="#" id="punishment">处分情况</a>
						</li>
						<li>
							<a href="#" id="showassurance">社会保险</a>
						</li>
						<li>
							<a href="#" id="survey">调查情况</a>
						</li>
						<li>
							<a href="#" id="credentials">证件列表</a>
						</li>
						<li>
							<a href="#" id="documentation">资料列表</a>
						</li>
						<li>
							<a href="#" id="personalRecord">人事档案</a>
						</li>
						<li>
							<a href="#" id="bankAccount">银行帐号</a>
						</li>
					</ul>
				</li>
			</ul>
		</div>
		<!--搜索窗口 -->
		<form name="cstaffSearchForm" id="cstaffSearchForm" method="post">
			<input type="submit" name="submit" style="display: none" />
			<div class="jqmWindow" style="width: 400px; right: 25%; top: 10%"
				id="jqModelSearch">
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table id="cataffSearchTable">
					<tr>
						<td>
							查询条件
						</td>
					</tr>
					<tr>
						<td width="123" align="center">
							人员编号：
						</td>
						<td>
							<input name="searchCStaff.staffCode" />
						</td>
					</tr>
					<tr>
						<td width="123" align="center">
							人员姓名：
						</td>
						<td>
							<input name="searchCStaff.staffName" />
						</td>
					</tr>
					<tr>
						<td width="123" align="center">
							身份证：
						</td>
						<td>
							<input name="searchCStaff.staffIdentityCard" />
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="searchStaff"
						value=" 查询 " />
					<input name="search" type="hidden" value="search" />
				</div>
			</div>
		</form>



		<form name="postForm" id="postForm" method="post"
			enctype="multipart/form-data">
			<div class="contentbannb jqmWindow jqmWindowcss1" style="top: 1%"
				id="postsjqModel">
				<input type="submit" name="submit" style="display: none" />
				<div class="content">
					<div class="contentbannb">
						<div class="drag">
							岗位职责管理
							<div class="close"></div>
						</div>
					</div>
					<table width="885" height="340" border="0" id="posttable"
						align="center" cellpadding="0" cellspacing="0"
						style="margin-top: 5px; margin-bottom: 5px;">
						<tr>
							<td align="right">
								员工编号：
							</td>
							<td>
								<input type="text" id="staffCode" readonly="readonly"
									name="staffCode" />
							</td>
							<td align="right">
								员工姓名：
							</td>
							<td>
								<input type="text" name="responsibilities.staffName"
									id="staffName" readonly="readonly" />
							</td>
							<td width="180" rowspan="6" align="center">
								<img id="pic" width="96" height="128" />
							</td>
						</tr>
						<tr>
							<td width="135" align="right">
								岗位职责编号：
							</td>
							<td width="227">
								<input name="responsibilities.responsibilitiesNum"
									id="responsibilitiesNum" type="text" class="input" size="20" />
							</td>
							<td width="152" align="right">
								岗位职责档案编号：
							</td>
							<td width="191">
								<input name="responsibilities.recordNum" id="recordNum"
									type="text" class="input" size="20" />
							</td>
						</tr>
						<tr>
							<td align="right">
								岗位起始时间：
							</td>
							<td>
								<input name="responsibilities.startDate" id="startDate"
									onfocus="date(this);" type="text" class="input" size="20" />
							</td>
							<td align="right">
								岗位截止时间：
							</td>
							<td>
								<input name="responsibilities.endDate" id="endDate"
									onfocus="date(this);" type="text" class="input" size="20" />
							</td>
						</tr>
						<tr>
							<td align="right">
								岗位名称：
							</td>
							<td>
								<input name="responsibilities.postName" id="postName"
									type="text" class="input" size="20" />
							</td>
							<td align="right">
								职务名称：
							</td>
							<td>
								<input name="responsibilities.dutyName" type="text"
									class="input" id="dutyName" size="20" />
							</td>
						</tr>
						<tr>
							<td height="19" align="right">
								岗位情况管理：
							</td>
							<td>
								<select name="responsibilities.postmanage" id="postmanage"
									class="select">
									<option value="专岗">
										专岗
									</option>
									<option value="兼岗">
										兼岗
									</option>
								</select>
							</td>
							<td align="right">
								文件号：
							</td>
							<td>
								<input name="responsibilities.fileNum" type="hidden"
									class="fileNum" id="fileNum" />
								<input name="photo" type="file" class="input" size="15"
									contentEditable="false" />
							</td>
						</tr>
						<tr>
							<td align="right">
								工作单位名称：
							</td>
							<td>
								<input name="responsibilities.companyName" type="text"
									class="input" id="companyName" size="20" />
							</td>
							<td align="right">
								部门名称：
							</td>
							<td>
								<select id="departmentID" name="responsibilities.departmentID">
								</select>
							</td>
						</tr>
						<tr>
							<td align="right">
								直接行政上级：
							</td>
							<td>
								<select id="organizationPID"
									name="responsibilities.organizationPID">
								</select>
							</td>
							<td align="right">
								直接行政下级：
							</td>
							<td>
								<select id="organizationCID"
									name="responsibilities.organizationCID">
								</select>
							</td>
							<td width="180" align="center">
								员工头像
							</td>
						</tr>
						<tr>
							<td align="right">
								管理范围：
							</td>
							<td colspan="4">
								<textarea name="responsibilities.managesCope"
									style="width: 730px; height: 30px;" class="input"
									id="managesCope"></textarea>
							</td>
						</tr>
						<tr>
							<td align="right">
								工作内容1：
							</td>
							<td colspan="4">
								<textarea name="responsibilities.jobContent1"
									style="width: 730px; height: 30px;" class="input"
									id="jobContent1"></textarea>
							</td>
						</tr>
						<tr>
							<td align="right">
								工作内容2：
							</td>
							<td colspan="4">
								<textarea name="responsibilities.jobContent2"
									style="width: 730px; height: 30px;" class="input"
									id="jobContent2"></textarea>
							</td>
						</tr>
						<tr>
							<td align="right">
								工作内容3：
							</td>
							<td colspan="4">
								<textarea name="responsibilities.jobContent3"
									style="width: 730px; height: 30px;" class="input"
									id="jobContent3"></textarea>
							</td>
						</tr>
						<tr>
							<td align="right">
								工作内容4：
							</td>
							<td colspan="4">
								<textarea name="responsibilities.jobContent4"
									style="width: 730px; height: 30px;" class="input"
									id="jobContent4"></textarea>
							</td>
						</tr>
						<tr>
							<td align="right">
								工作内容5：
							</td>
							<td colspan="4">
								<textarea name="responsibilities.jobContent5"
									style="width: 730px; height: 30px;" class="input"
									id="jobContent5"></textarea>
							</td>
						</tr>
						<tr>
							<td colspan="5" align="center">
								<input name="responsibilities.responsibilitiesKey"
									id="responsibilitiesKey" type="hidden" class="input" size="20" />
								<input name="responsibilities.responsibilitiesID"
									id="responsibilitiesID" type="hidden" class="input" size="20" />
								<input name="responsibilities.staffID" id="staffID"
									type="hidden" class="input" size="20" />
								<input type="button" class="input-button JQueryPrint"
									style="cursor: pointer; width: 80px;" value="打印预览" />
								<input type="button" class="input-button JQuerySubmitpost"
									style="cursor: pointer; width: 80px;" value="提交" />
								<input type="button" class="input-button JQueryreturnpost"
									style="cursor: pointer; width: 80px;" value="取消" />
							</td>
						</tr>
					</table>
				</div>
			</div>
			<s:token></s:token>
		</form>
<script type="text/javascript">
   $(function(){
       setTimeout(function(){ 
		   var _height = $(window).height();		
		   if($("#mainframe").height() > 0){
		       $(".bDiv").css({"height": _height /2 - 30 - 26 - 50 + "px"});
		       $("#mainframe").css({"height": _height / 2 - 20 + "px"});
		   }else{		    
		       $(".bDiv").css({"height": _height - 31 - 30 - 26 - 40 + "px"});
		       $("#mainframe").css({"height": 0 + "px"});
		   }
		},100);
    
	    $(window).resize(function(){ 
		setTimeout(function(){ 
		    var _height = $(window).height();		
		    if($("#mainframe").height() > 0){
		        $(".bDiv").css({"height": _height /2 - 30 - 26 - 50 + "px"});
			    $("#mainframe").css({"height": _height / 2 - 20 + "px"});
		    }else{		    
				$(".bDiv").css({"height": _height - 31 - 30 - 26 - 40 + "px"});
				$("#mainframe").css({"height": 0 + "px"});
		    }
		},100);
	    }); 
   });
</script>   
	</body>
</html>