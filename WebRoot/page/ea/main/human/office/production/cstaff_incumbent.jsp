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
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>人事生产-人员列表</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
			type="text/css" />
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
		<script src="<%=basePath%>js/ea/human/cstaff.js"></script>
		<script
			src="<%=basePath%>js/ea/human/office/production/cstaff_incumbent.js">
		</script>
		
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
            var pnum = '${pageForm.pageNumber}'; //页数传值
            var companyName = '${companyName}';
            var aa="${aa}";
            
            $(document).ready(function(){
                //图片预览
                $('#staffphoto').change(function(){
                    $t = $("table#stafftable");
                    $t.find('img#photo').attr("src", this.value).show();
                });
                //图片预览END
            })
            var loglist;
        </script>
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
							员工工种
						</th>
						<th width="100" align="center">
							人员姓名
						</th>
						<th width="100" align="center">
							部门名称
						</th>
						<th width="100" align="center">
							岗位名称
						</th>
							<th width="80" align="center">
							性别
						</th>
						<th width="100" align="center">
							出生日期
						</th>
						<th width="80" align="center">
							籍贯
						</th>
						<th width="80" align="center">
							民族
						</th>
						<th width="200" align="center">
							身份证
						</th>
						<th width="100" align="center">
							电话
						</th>
						<!--  
						<th width="200" align="center">
							身份证地址
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
						<th width="100" align="center">
							入职时间
						</th>
						<th width="100" align="center">
							转正时间
						</th>
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
								<span id="categoryname">${lists[3]==null?"暂未分配":lists[3]}</span>
							</td>
							<td>
								<span id="staffName">${lists[4]}</span>
							</td>
							<td>
								<span id="organizationName">${lists[5]}</span>
							</td>
							<td>
								<span id="postName">${lists[6]}</span>
							</td>
							<td>
								<span id="sex">${lists[8]}</span>
							</td>
							<td>
								<span id="birthday" class="datas">${lists[9]}</span>
							</td>
							<td>
								<span id="nativePlace">${lists[11]}</span>
							</td>
							<td>
								<span id="nation">${lists[12]}</span>
							</td>
							<td>
								<span id="staffIdentityCard">${lists[13]}</span>
								<span style="display: none" id="schedulingID">${schedulingID}</span>
								<span style="display: none" id="staffKey">${lists[25]}</span>
								<span style="display: none" id="address">${lists[26]}</span>
								<span style="display: none" id="staffID">${lists[0]}</span>
								<span style="display: none" id="photo">${lists[27]}</span>
							</td>
							<td>
								<span id="reference">${lists[15]}</span>
							</td>
							<!--  
							<td>
								<span id="staffAddress">${lists[14]}</span>
							</td>
							
							<td>
								<span id="referenceCode">${lists[16]}</span>
							</td>
							<td>
								<span id="referenceOrganization">${lists[17]}</span>
							</td>
							<td>
								<span id="verifyTime" class="datas">${lists[18]}</span>
							</td>
							<td>
								<span id="politicsStatus">${lists[19]}</span>
							</td>
							<td>
								<span id="culturalDegree">${lists[20]}</span>
							</td>
							<td>
								<span id="marriage">${lists[21]}</span>
							</td>
							<td>
								<span id="health">${lists[22]}</span>
							</td>
							<td>
								<span id="bankNum">${lists[23]}</span>
							</td>
							<td>
								<span id="staffDesc">${lists[24]}</span>
							</td>
							-->
							<td>
								<span id="registerDate">${lists[28]}</span>
							</td>
							<td>
								<span id="becomesDate">${lists[29]}</span>
							</td>
						</tr>
						<%
							number++;
						%>
					</s:iterator>
				</tbody>
			</table>
			<c:import url="../../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/soincumbent/ea_getStaffListForIncumbent.jspa?pageNumber=${pageNumber}&search=${search}&searchValue=${searchValue}&aa=${aa }">
				</c:param>
			</c:import> 
		</div>
		<iframe src="" name="main" scrolling="no" style="width:100%;height:0;"
					marginwidth="0" marginheight="0" frameborder="0"
					id="mainframe" border="0" framespacing="0" noresize="noResize"
					vspale="0"> </iframe>
		<form name="cstaffForm" id="cstaffForm" method="post"
			enctype="multipart/form-data">
			<div class="jqmWindow jqmWindowcss1" style="width: 820px; top: 2%"
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
							人员编号：
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
							<object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000"
								codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=9,0,0,0"
								width="250" height="180" id="singleShuter" align="middle">
								<param name="allowScriptAccess" value="sameDomain" />
								<param name="allowFullScreen" value="false" />
								<param name="FlashVars"
									value="servicesUrl=<%=basePath%>js/photo/save2.jsp" />
								<param name="movie"
									value="<%=basePath%>js/photo/singleShuter.swf" />
								<param name="quality" value="high" />
								<param name="bgcolor" value="#ffffff" />
								<embed src="<%=basePath%>js/photo/singleShuter.swf"
									FlashVars="servicesUrl=<%=basePath%>js/photo/save2.jsp"
									quality="high" bgcolor="#ffffff" width="250" height="180"
									name="singleShuter" align="middle"
									allowScriptAccess="sameDomain" allowFullScreen="false"
									type="application/x-shockwave-flash"
									pluginspage="http://www.macromedia.com/go/getflashplayer" />
							</object>
							<img width="99" height="135" id="photo" style="display: none;" />
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
							<input name="cstaff.staffIdentityCard"
								class="notnull IdentityCard model3" id="staffIdentityCard"
								size="20" />
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
							qq：
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
							<input id="singleShuterphoto" type="button" style="width: 50px;"
								value="摄像头" />
							<input name="photo" id="staffphoto" class="input01" type="file" />
							<input name="cstaff.photo" type="hidden" id="photo" size="14" />
							<input name="cstaff.staffID" id="staffID" type="hidden" />
							<input name="cstaff.staffKey" id="staffKey" type="hidden" />
							<input name="cstaff.schedulingID" id="schedulingID" type="hidden" />
							<input name="sub" value="${session_value}" type="hidden" />
							<!-- 代替token-->
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
							<input name="cstaff.staffAddress" id="staffAddress" type="hidden" />
							<select name="addressProvince" id="province" number='0'
								style="width: 110px;">
							</select>
							<!-- <option>选择省</option>-->
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
							<!-- <option>选择省</option>-->
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
							<textarea name="cstaff.staffDesc" class="model3" cols="40"
								rows="4" id="staffDesc">
                            
                            </textarea>
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button JQuerySubmit"
						style="cursor: pointer; width: 80px;" value="提交" />
					<input type="button" class="input-button JQueryreturn"
						style="cursor: pointer; width: 80px;" value="返回" />
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
		<div class="jqmWindow" style="width: 400px; right: 25%; top: 10%"
			id="newdistrict">
			<div class="drag">
				添加地域
			</div>
			<table>
				<tr>
					<td>
						城市名字：
					</td>
					<td>
						<input id="districtNames" />
						&nbsp;&nbsp;
						<span style="color: red">*按地域区分组</span>
					</td>
				</tr>
			</table>
			<div align="center">
				<input type="button" class="input-button" id="savedistrict"
					value="确定" />
				<input type="button" class="input-button JQueryreturn1" value="取消" />
			</div>
		</div>
		<!--搜索窗口 -->
		<form name="cstaffSearchForm" id="cstaffSearchForm" method="post">
			<input type="submit" name="submit" style="display: none" />

			<div class="jqmWindow jqmWindowcss3" style="width: 500px;; top: 10%"
				id="jqModelSearch">
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table width="395" id="cataffSearchTable">
					<tr>
						<td width="122" align="right">
							人员编号：
						</td>
						<td width="261">
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
				</table>
				<div align="center">
					<input type="button" class="input-button" id="searchStaff"
						value="查询" />
					<input name="search" type="hidden" value="search" />
				</div>
			</div>
		</form>
		<form name="appraisalForm" id="appraisalForm" method="post"
			enctype="multipart/form-data">
			<div class="jqmWindow jqmWindowcss1" style="width: 920px; top: 0%;"
				id="jqModelAppraisal">
				<input type="submit" name="submit" style="display: none" />
				<input type="hidden" name="result" id="result" style="display: none" />
				<div class="drag">
					考评详细信息
					<div class="close">
					</div>
				</div>
				<table width="883" border="0" align="center" cellpadding="0"
					cellspacing="0" style="background: #FFFFFF;">
					<tr>
						<td height="10" align="right">
							考评时间
						</td>
						<td>
							<input name="staffappraisal.appraisalDate" type="text"
								id="appraisalDate" style="height: 15px;" onfocus="date(this)"
								size="12" />
						</td>
						<td width="635">
							<table width="562" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td width="114" align="right">
										工作日饱和度
									</td>
									<td width="180">
										<input name="staffappraisal.workDateSaturation" type="text"
											id="workDateSaturation" size="2" value="1" />
										注 1为满分
									</td>
									<td align="right">
										当前级别：
									</td>
									<td width="74" align="right">
										<span style="color: red" id="scale"></span>
									</td>
									<td width="74" align="right">
										<input type="button"
											class="input-button JQuerySubmitAppraisal"
											style="cursor: pointer; width: 80px;" value="提交" />
									</td>
								</tr>
							</table>

						</td>
					</tr>
				</table>
				<table width="883" border="0" align="center" cellpadding="1"
					cellspacing="1" style="background: #FFFFFF;" class="table"
					id="staffappr">
					<tr>

						<td width="59" height="10" align="center" bgcolor="E4F1FA"
							class="kptd">
							序号
						</td>
						<td width="112" align="center" bgcolor="E4F1FA">
							月考评内容
						</td>
						<td width="67" align="center" bgcolor="E4F1FA">
							得分类别
						</td>
						<td colspan="4" align="center" bgcolor="E4F1FA">
							综合得分
						</td>
						<td width="41" align="center" bgcolor="E4F1FA">
							总分						</td>
				  <td width="68" align="center" bgcolor="E4F1FA">
							实际得分
						</td>
						<td width="65" align="center" bgcolor="E4F1FA">
							百分比
						</td>
						<td width="74" align="center" bgcolor="E4F1FA">
							考评金额
						</td>

						<td width="67" align="center" bgcolor="E4F1FA">
							所得金额
						</td>
						<td width="50" align="center" bgcolor="E4F1FA">
							得分
						</td>
						<td width="50" align="center" bgcolor="E4F1FA">
							备注
						</td>
					</tr>
					<tr>
						<td height="10" class="kptd">
							1
						</td>
						<td>
							遵纪守法
						</td>

						<td rowspan="3">
							职责得分
						</td>
						<td colspan="4" align="left">
							<label>
								<input name="staffappraisal.responsibility1" type="text"
									class="positionPay put3" id="responsibility1" size="4"
									value="0" style="height: 12px" />
							</label>
						</td>

						<td rowspan="3" align="center">
							15
						</td>
						<td rowspan="3" align="center">
							<span style="color: red" id="sumScole1"></span>
						</td>
						<td rowspan="3" align="center">
							<span style="color: red" id="pc1"></span>
						</td>
						<td rowspan="3" align="center">
							<span style="color: red" id="positionPay"></span>
						</td>
						<td rowspan="3" align="center">
							<span style="color: red" id="positionPayMoney"></span>
						</td>
						<td rowspan="3" align="center">
							<span style="color: red" id="positionPayScore"></span>
						</td>
						<td rowspan="3" align="center">
							<span style="color: red"></span>
						</td>
					</tr>
					<tr>

						<td height="15" class="kptd">
							2
						</td>
						<td>
							责任心
						</td>
						<td colspan="4" align="left">
							<input name="staffappraisal.responsibility2"
								class="positionPay put3" type="text" id="responsibility2"
								style="height: 12px" size="4" value="0" />
						</td>
					</tr>
					<tr>
						<td height="15" class="kptd">
							3
						</td>

						<td>
							原则性
						</td>
						<td colspan="4" align="left">
							<input name="staffappraisal.responsibility3"
								class="positionPay put3" type="text" id="responsibility3"
								style="height: 12px" size="4" value="0" />
						</td>
					</tr>
					<tr>
						<td height="15" class="kptd">
							4
						</td>

						<td>
							工作完成率
						</td>
						<td rowspan="3">
							业绩得分
						</td>
						<td colspan="4" align="left">
							<input name="staffappraisal.achievements1" class="work put3"
								type="text" id="achievements1" size="4" style="height: 12px"
								value="0" />
						</td>
						<td rowspan="3" align="center">
							15
						</td>

						<td rowspan="3" align="center">
							<span style="color: red" id="sumScole2"></span>
						</td>
						<td rowspan="3" align="center">
							<span style="color: red" id="pc2"></span>
						</td>
						<td rowspan="3">&nbsp;
							

						</td>
						<td rowspan="3">&nbsp;
							

						</td>
						<td rowspan="3">&nbsp;
							

						</td>
						<td rowspan="3">&nbsp;
							

						</td>
					</tr>
					<tr>
						<td height="15" class="kptd">
							5
						</td>

						<td>
							工作量是否饱和
						</td>
						<td colspan="4" align="left">
							<input name="staffappraisal.achievements2" class="work put3"
								type="text" id="achievements2" size="4" style="height: 12px"
								value="0" />
						</td>
					</tr>
					<tr>
						<td height="15" class="kptd">
							6
						</td>

						<td>
							工作质量
						</td>
						<td colspan="4" align="left">
							<input name="staffappraisal.achievements3" class="work put3"
								type="text" id="achievements3" size="4" value="0"
								style="height: 12px" />
						</td>
					</tr>
					<tr>
						<td height="15" class="kptd">
							7
						</td>

						<td>
							任务完成率
						</td>
						<td rowspan="3">
							任务得分
						</td>
						<td colspan="4" align="left">
							<input name="staffappraisal.task1" class="achievement put3"
								type="text" id="task1" size="4" value="0" style="height: 12px" />
						</td>
						<td rowspan="3" align="center">
							15
						</td>

						<td rowspan="3" align="center">
							<span style="color: red" id="sumScole3"></span>
						</td>
						<td rowspan="3" align="center">
							<span style="color: red" id="pc3"></span>
						</td>
						<td rowspan="3" align="center">
							<span style="color: red" id="timingMoney"></span>
						</td>
						<td rowspan="3" align="center">
							<span style="color: red" id="timingMoneyMoney"></span>
						</td>
						<td rowspan="3" align="center">
							<%--任务得分--%>
							<span style="color: red" id="timingMoneyScore"></span>
						</td>
						<td rowspan="3" align="center">&nbsp;
							

						</td>
					</tr>
					<tr>
						<td height="15" class="kptd">
							8
						</td>

						<td>
							目标是否明确
						</td>
						<td colspan="4" align="left">
							<input name="staffappraisal.task2" type="text"
								class="achievement put3" id="task2" size="4" value="0"
								style="height: 12px" />
						</td>
					</tr>
					<tr>
						<td height="15" class="kptd">
							9
						</td>

						<td>
							任务完成主动性
						</td>
						<td colspan="4" align="left">
							<input name="staffappraisal.task3" type="text"
								class="achievement put3" id="task3" size="4" value="0"
								style="height: 12px" />
						</td>
					</tr>
					<tr>
						<td height="15" class="kptd">
							10
						</td>

						<td>
							专业技术能力
						</td>
						<td rowspan="3">
							能力得分
						</td>
						<td colspan="4" align="left">
							<input name="staffappraisal.ability1" class="stPay put3"
								type="text" id="ability1" size="4" value="0"
								style="height: 12px" />
						</td>
						<td rowspan="3" align="center">
							15
						</td>

						<td rowspan="3" align="center">
							<span style="color: red" id="sumScole4"></span>
						</td>
						<td rowspan="3" align="center">
							<span style="color: red" id="pc4"></span>
						</td>
						<td rowspan="3" align="center"></td>
						<td rowspan="3" align="center">
							<span style="color: red" id="stPayMoney"></span>
						</td>
						<td rowspan="3" align="center">
							<span style="color: red" id="stPayScore"></span>
						</td>
						<td rowspan="3" align="center">&nbsp;
							

						</td>
					</tr>
					<tr>
						<td height="15" class="kptd">
							11
						</td>

						<td>
							管理能力
						</td>
						<td colspan="4" align="left">
							<input name="staffappraisal.ability2" class="stPay put3"
								type="text" id="ability2" size="4" value="0"
								style="height: 12px" />
						</td>
					</tr>
					<tr>
						<td height="15" class="kptd">
							12
						</td>

						<td>
							综合素质能力
						</td>
						<td colspan="4" align="left">
							<input name="staffappraisal.ability3" class="stPay put3"
								type="text" id="ability3" size="4" value="0"
								style="height: 12px" />
						</td>
					</tr>
					<tr>
						<td height="15" class="kptd">
							13
						</td>

						<td>
							出勤率
						</td>
						<td rowspan="3">
							态度得分
						</td>
						<td colspan="4" align="left">
							<input name="staffappraisal.manner1" type="text"
								class="chuqin put3" id="manner1" size="4" value="0"
								style="height: 12px" />
						</td>
						<td rowspan="3" align="center">
							15
						</td>

						<td rowspan="3" align="center">
							<span style="color: red" id="sumScole5"></span>
						</td>
						<td rowspan="3" align="center">
							<span style="color: red" id="pc5"></span>
						</td>
						<td rowspan="3">&nbsp;
							

						</td>
						<td rowspan="3">&nbsp;
							

						</td>
						<td rowspan="3">&nbsp;
							

						</td>
						<td rowspan="3">&nbsp;
							

						</td>
					</tr>
					<tr>
						<td height="15" class="kptd">
							14
						</td>

						<td>
							工作主动性
						</td>
						<td colspan="4" align="left">
							<input name="staffappraisal.manner2" type="text"
								class="chuqin put3" id="manner2" size="4" value="0"
								style="height: 12px" />
						</td>
					</tr>
					<tr>
						<td height="15" class="kptd">
							15
						</td>

						<td>
							文明礼貌素质
						</td>
						<td colspan="4" align="left">
							<input name="staffappraisal.manner3" type="text"
								class="chuqin put3" id="manner3" size="4" value="0"
								style="height: 12px" />
						</td>
					</tr>
					<tr>
						<td height="15" class="kptd">
							得分
						</td>

						<td class="kptd">
							总分 75
						</td>
						<td class="kptd">
							实际得分
						</td>
						<td width="33" align="center" class="kptd">
							<span style="color: red" id="allMoney"></span>
						</td>
						<td width="51" class="kptd">
							分数
						</td>
						<td width="40" align="center" class="kptd">&nbsp;
							
						</td>
						<td width="63" class="kptd">
							百分比						</td>
				  <td align="center" class="kptd">
							<span style="color: red" id="allpc"></span>
						</td>
						<td class="kptd">
							考评金额
						</td>
						<td align="center" class="kptd">
							<span style="color: red" id="pushMoney"></span>
						</td>
						<td class="kptd">
							所得金额
						</td>
						<td align="center" class="kptd">
							<span style="color: red" id="pushMoneyMoney"></span>
						</td>

						<td class="kptd">
							得分
						</td>
						<td align="center" class="kptd">
							<span style="color: red" id="pushScore"></span>
						</td>
					</tr>
					<tr>
						<td height="16" class="kptd">
							特殊
							<br />
							人才
						</td>

						<td class="kptd">
							<span style="color: red" id="stPay"></span>
						</td>
						<td class="kptd">
							得分
						</td>
						<td width="33" align="center" class="kptd">
							<span style="color: red" id="stPayscore"></span>
						</td>
						<td width="51" class="kptd">
							保密
							<br />
							金额
						</td>
						<td width="40" align="center" class="kptd">
							<span style="color: red" id="secrecyPay"></span>
						</td>
						<td width="63" class="kptd">
							保密
						  <br />
							得分
						</td>
				  <td align="center" class="kptd">
							<span style="color: red" id="secrecyPayscore"></span>
						</td>
						<td class="kptd">
							安全金额
					  </td>
						<td align="center" class="kptd">
							<span style="color: red" id="safetyAward"></span>
						</td>
						<td class="kptd">
							安全得分
					  </td>
						<td align="center" class="kptd">
							<span style="color: red" id="safetyAwardscore"></span>
						</td>

						<td class="kptd">&nbsp;
							
						</td>
						<td align="center" class="kptd">&nbsp;
							
						</td>
					</tr>
					<tr>
						<td class="kptd">
							奖励
							<br />
							工资
						</td>
						<td class="kptd"><span style="color: red" id="awardPay"></span></td>
						<td class="kptd">
							得分
						</td>
						<td class="kptd"><span style="color: red" id="awardPayScore"></span></td>
						<td class="kptd">
							孝道金
						</td>
						<td class="kptd"><span style="color: red" id="pietypay"></span></td>
						<td class="kptd">
							竞职金
						</td>
						<td class="kptd"><span style="color: red" id="campaignpay"></span></td>
						<td class="kptd">
							通讯补助
						</td>
						<td class="kptd"><span style="color: red" id="telecompay"></span></td>
						<!-- <td class="kptd">
							PK金
						</td>
						<td class="kptd"><span style="color: red" id="pkpay"></span></td> -->
						<td class="kptd">
							生活补助
						</td>
						<td class="kptd"><span style="color: red" id="living"></span></td>
					</tr>
					<tr>
						<td height="15" class="kptd">
							会议
							<br />
							名称
						</td>
						<td>&nbsp;
							

						</td>
						<td>
							会议时间
						</td>
						<td colspan="3">&nbsp;
							

						</td>

						<td>&nbsp;
							

						</td>
						<td colspan="2">
							参会考评人签字
						</td>
						<td colspan="5">
							<input name="staffappraisal.checkPerson" class="nocheck"
								type="text" id="checkPerson" style="width: 200px" />
							<input name="staffappraisal.appraisalKey" class="nocheck"
								type="hidden" id="appraisalKey" />
							<input name="staffappraisal.appraisalID" class="nocheck"
								type="hidden" id="appraisalID" />
							<input name="staffappraisal.payScaleID" class="nocheck"
								type="hidden" id="payScaleID" />
						</td>
					</tr>
				</table>

			</div>
		</form>



		<!--员工离职原因 -->
		<form name="cstaffDForm" id="cstaffDForm" method="post">
			<input type="submit" name="submit" id="submit" style="display: none" />
			<div class="jqmWindow" style="width: 350px; right: 45%; top: 30%;"
				id="jqModelDimission">

				<div class="drag">
					员工离职
					<div class="close">
					</div>
				</div>
				<table id="cataffSearchTable">
					<tr>
						<td>
							离职时间：
						</td>
						<td>
							<input name="codimission.dimissionDate" id="dimissionDate" onfocus="date(this);" class="goodsCoding put3" type="text"/>
						</td>
					</tr>
					<tr>
						<td>
							离职原因：
						</td>
						<td>
							<input name="codimission.dimissionCause" id="dimissionCause" align="left" class="goodsCoding put3" type="text"/>
						</td>
					</tr>
					<tr>
						<td>
							经手人：
						</td>
						<td>
							<input name="codimission.issued" id="issued" class="goodsCoding put3" type="text"/>
						</td>
					</tr>
					<tr>
						<td>
							离职状态：
						</td>
						<td>
							<input type="radio" id="01" name="codimission.dimissionStatus"
								value="辞职" />
							辞职
							<input type="radio" id="02" name="codimission.dimissionStatus"
								value="辞退" />
							辞退
							<input type="radio" id="03" name="codimission.dimissionStatus"
								value="开除" />
							开除
							<input type="radio" id="04" name="codimission.dimissionStatus"
								value="终止" />
							终止
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button JQ" id="searchCod"
						value="确定" />
					<input type="button" class="input-button JQueryreturn" value="取消" />
					<input name="dimission" type="hidden" value="dimission" />
				</div>
			</div>
		</form>
		
		
		<!--员工工种变更-->
		<form name="cstaffWForm" id="cstaffWForm" method="post">
			<input type="submit" name="submit" id="submit" style="display: none" />
			<div class="jqmWindow" style="width: 450px; right: 45%; top: 30%;"
				id="jqModelWork">

				<div class="drag">
					员工工种
					<div class="close">
					</div>
				</div>
				<table id="cataffWorkTable">
					<tr>
						<td style="width: 110px;" align="right">
							员工姓名：
						</td>
						<td>
							<span id="staffName"></span>
						</td>
					</tr>
					<tr>
						<td style="width: 110px;" align="right">
							原工种：
						</td>
						<td>
							<span id="oldcategoryname"></span>
						</td>
					</tr>
					<tr>
						<td style="width: 110px;" align="right">
							新工种：
						</td>
						<td>
							<input type="radio" id="01" name="audition.categoryName"
								value="参观补助期" />
							参观补助期
							<input type="radio" id="02" name="audition.categoryName"
								value="试用期员工" />
							试用期员工
							<input type="radio" id="03" name="audition.categoryName"
								value="培训补助期" />
							培训补助期
							<br />
							<input type="radio" id="04" name="audition.categoryName"
								value="正式员工" />
							正式员工
							<input type="radio" id="05" name="audition.categoryName"
								value="临时员工" />
							临时员工
							<input type="radio" id="06" name="audition.categoryName"
								value="中介员工" />
							中介员工
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button JW" id="JW"
						value="确定" />
					<input type="button" class="input-button JQueryreturn" value="取消" />
					<input id="staffID" name="audition.staffID" type="hidden"  />
					<input id="staffCategoryID" name="audition.staffCategoryID" type="hidden"  />
				</div>
			</div>
		</form>
		<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
		
<script type="text/javascript">
   $(function(){
       setTimeout(function(){ 
		   var _height = $(window).height();		
		   if($("#mainframe").height() > 0){
		       $(".bDiv").css({"height": _height /2 - 30 - 26 - 50 + "px"});
		       $("#mainframe").css({"height": _height / 2 - 30 + "px"});
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
			$("#mainframe").css({"height": _height / 2 - 30 + "px"});
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