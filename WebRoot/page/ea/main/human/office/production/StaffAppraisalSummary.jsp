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
		<title>综合考评汇总</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet"
			type="text/css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
		
	
	<script type="text/javascript">
		   var select =1;
		   var appraisalID = '';
		   var payScaleID = '';
		   var basePath = '<%=basePath%>';
           var ppageNumber=${pageNumber};
           var pstaffappraisalstaffID='${staffappraisal.staffID}';
           var pstartdate='${startdate}';
           var penddate='${enddate}';
           var token=0;
           var notoken = 0;
           var search="${search}";
	</script>
	<script src="<%=basePath%>js/ea/human/cstaff.js"></script>
	<script type="text/javascript" src="<%=basePath%>js/ea/human/office/production/StaffAppraisalSummary.js"></script>
	</head>
	<body>
	
	    <form name="lForm" id="lForm" method="post">
			<input type="submit" name="submit" style="display: none" />
			<input type="hidden" value="${sdate}" name="sdate" id="sdate" />
			<input type="hidden" value="${edate}" name="edate" id="edate" />
			<input type="hidden" value="${staffID}" name="staffID" id="staffID" />
		</form>
		<form  name="staffappraisalForm"
			enctype="multipart/form-data" id="staffappraisalForm" method="post">
			<s:token></s:token>
			<input type="submit" name="submit" style="display: none" />
		
		

		<div id="main_main" class="main_main">
			<table class="staffappraisal">
				<thead>
					<tr>
						<th width="35" align="center">
							选择
						</th>
						 <th width="200" align="center">
                            公司名称
                        </th>
                         <th width="100" align="center">
                            员工编号
                        </th>
                        <th width="100" align="center">
                            员工姓名
                        </th>
						<th width="110" align="center">
							考评时间
						</th>
						<th width="110" align="center">
							参会考评人
						</th>
						<th width="110" align="center">
							工作日饱和度
						</th>
						<th width="110" align="center">
							遵守法律
						</th>
						<th width="110" align="center">
							责任心
						</th>
						<th width="110" align="center">
							原则性
						</th>
						<th width="110" align="center">
							工作完成率
						</th>
						<th width="80" align="center">
							工作量是否饱和
						</th>
						<th width="80" align="center">
							工作质量
						</th>
						<th width="110" align="center">
							任务完成率
						</th>  
						<th width="110" align="center">
							目标是否明确
						</th>  
						<th width="110" align="center">
							任务完成主动性
						</th>  
						<th width="110" align="center">
							专业技术能力
						</th>  
						<th width="110" align="center">
							管理能力
						</th>  
						<th width="110" align="center">
							综合素质能力
						</th>  
						<th width="110" align="center">
							出勤率
						</th>  
						<th width="110" align="center">
							工作主动性
						</th>  
						<th width="110" align="center">
							文明礼貌素质
						</th>  
					</tr>
				</thead>
				<tbody> 
					<s:iterator value="pageForm.list">
						<tr class="td_bg01 saveAjax" id="${appraisalID}">
							<td class="td_bg01">
								<input type="radio" name="a" class="JQuerypersonvalue"
									value="${appraisalID}" />
							</td>
							<td>
                                <span id="companyName">${companyName}</span>
                            </td>
                            <td>
                                <span id="staffCode">${staffCode}</span>
                            </td>
                             <td>
                                <span id="staffName">${staffName}</span>
                            </td>
							<td class="td_bg01">
								<span id="appraisalDate" class="datas">${appraisalDate}</span> 
							</td>
							<td class="td_bg01">
								<span id="checkPerson">${checkPerson}</span> 
							</td>
							<td class="td_bg01">
								<span id="workDateSaturation">${workDateSaturation}</span> 
							</td>
							<td class="td_bg01">
								<span id="responsibility1">${responsibility1}</span>  
							</td>
							<td class="td_bg01">
								<span id="responsibility2">${responsibility2}</span> 
							</td>
							<td class="td_bg01">
								<span id="responsibility3">${responsibility3}</span> 
							</td>
							
							<td class="td_bg01">
								<span id="achievements1">${achievements1}</span> 
							</td>
							<td class="td_bg01">
								<span id="achievements2">${achievements2}</span> 
							</td>
							<td class="td_bg01">
								<span id="achievements3">${achievements3}</span> 
							</td>
							
							<td class="td_bg01">
								<span id="task1">${task1}</span> 
							</td>
							<td class="td_bg01">
								<span id="task2">${task2}</span> 
							</td>
							<td class="td_bg01">
								<span id="task3">${task3}</span> 
							</td>
							
							<td class="td_bg01">
								<span id="ability1">${ability1}</span> 
							</td>
							<td class="td_bg01">
								<span id="ability2">${ability2}</span> 
							</td>
							<td class="td_bg01">
								<span id="ability3">${ability3}</span> 
							</td>
							
							<td class="td_bg01">
								<span id="manner1">${manner1}</span> 
							</td>
							<td class="td_bg01">
								<span id="manner2">${manner2}</span> 
							</td>
							<td class="td_bg01">
								<span id="manner3">${manner3}</span>
								<span style="display:none" id="staffID">${staffID}</span>
								<span style="display:none" id="companyID">${companyID}</span>
								<span style="display:none" id="appraisalKey">${appraisalKey}</span>
								<span style="display:none" id="appraisalID">${appraisalID}</span> 
								<span style="display:none" id="payScaleID">${payScaleID}</span> 
							</td> 
						</tr>
					</s:iterator>
				</tbody>
			</table>
			<c:import url="../../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/staffappraisalsummary/ea_getStaffAppraisalList.jspa?startdate=${startdate}&enddate=${enddate}&pageNumber=${pageNumber}&search=${search}"></c:param>
			</c:import>
		</div>
		</form>
	
		 <form name="appraisalForm" id="appraisalForm" method="post" enctype="multipart/form-data">
		
			<div class="jqmWindow jqmWindowcss5" style="width:1000px;  top:0%"
				id="jqModelAppraisal">
              <input type="submit" name="submit" style="display:none"/> 
                 <input type="hidden" name="result" id="result" style="display:none"/>
                <div class="drag">
                    考评详细信息 
                    <div class="close">
                    </div>
                </div>
    <table width="883"  border="0" align="center" cellpadding="0" cellspacing="0" style="background:#FFFFFF;">
        <tr>
          <td height="10" align="right">考评时间</td>
          <td><input name="staffappraisal.appraisalDate"  type="text" id="appraisalDate" style="height: 15px;"   size="12"/></td>
          <td width="635" >
          <table width="562" border="0"  cellpadding="0" cellspacing="0">
            <tr>
              <td width="114" align="right">工作日饱和度</td>
          <td width="180"><input name="staffappraisal.workDateSaturation" type="text" id="workDateSaturation" size="2"  value="1"/> 
              注 1为满分 </td>
          <td align="right">当前级别：</td>
           <td width="74" align="right"><span style="color:red" id="scale"></span></td>
        
            </tr>
          </table>
          
          </td>
        </tr>
</table>
  <table width="883" border="0" align="center" cellpadding="1" cellspacing="1" style="background:#FFFFFF;" class="table" id="staffappr">
    <tr>
 
      <td width="48" height="10" align="center" bgcolor="E4F1FA">序号</td>
      <td width="89" align="center" bgcolor="E4F1FA">月考评内容</td>
      <td width="66" align="center" bgcolor="E4F1FA">得分类别</td>
      <td colspan="4" align="center" bgcolor="E4F1FA">综合得分</td>
      <td width="52" align="center" bgcolor="E4F1FA">总分</td>
      <td width="68" align="center" bgcolor="E4F1FA">实际得分</td>
      <td width="65" align="center" bgcolor="E4F1FA">百分比</td>
      <td width="74" align="center" bgcolor="E4F1FA">考评金额</td>
 
      <td width="67" align="center" bgcolor="E4F1FA">所得金额</td>
      <td width="50" align="center" bgcolor="E4F1FA">得分</td>
      <td width="50" align="center" bgcolor="E4F1FA">备注</td>
    </tr>
    <tr>
      <td height="10">1</td>
      <td>遵纪守法</td>
 
      <td rowspan="3">职责得分</td>
        <td colspan="4" align="left"><label>
          <input name="staffappraisal.responsibility1" type="text" class="positionPay put3" id="responsibility1" size="4" value="0"  style="height:12px"/>
        </label></td>
 
      <td rowspan="3" align="center">15</td>
      <td rowspan="3" align="center"><span style="color:red" id="sumScole1"></span></td>
      <td rowspan="3" align="center"><span style="color:red" id="pc1"></span></td>
      <td rowspan="3" align="center"><span style="color:red" id="positionPay"></span></td>
      <td rowspan="3" align="center"><span style="color:red" id="positionPayMoney"></span></td>
      <td rowspan="3" align="center"><span style="color:red" id="positionPayScore"></span></td>
      <td rowspan="3" align="center"><span style="color:red" ></span></td>
    </tr>
    <tr>
 
      <td height="15">2</td>
      <td>责任心</td>
      <td colspan="4" align="left"><input name="staffappraisal.responsibility2" class="positionPay put3"  type="text" id="responsibility2" style="height:12px" size="4" value="0"/></td>
    </tr>
    <tr>
      <td height="15">3</td>
 
      <td>原则性</td>
      <td colspan="4" align="left"><input name="staffappraisal.responsibility3" class="positionPay put3"  type="text" id="responsibility3" style="height:12px" size="4" value="0"/></td>
    </tr>
    <tr>
      <td height="15">4</td>
 
      <td>工作完成率</td>
      <td rowspan="3">业绩得分</td>
      <td colspan="4" align="left"><input name="staffappraisal.achievements1" class="work put3" type="text" id="achievements1" size="4" style="height:12px" value="0"/></td>
      <td rowspan="3" align="center">15</td>
 
      <td rowspan="3" align="center"><span style="color:red" id="sumScole2"></span></td>
      <td rowspan="3" align="center"><span style="color:red" id="pc2"></span></td>
      <td rowspan="3">&nbsp;</td>
      <td rowspan="3">&nbsp;</td>
      <td rowspan="3">&nbsp;</td>
      <td rowspan="3">&nbsp;</td>
    </tr>
    <tr>
      <td height="15">5</td>
 
      <td>工作量是否饱和</td>
      <td colspan="4" align="left"><input name="staffappraisal.achievements2" class="work put3" type="text" id="achievements2" size="4" style="height:12px" value="0"/></td>
    </tr>
     <tr>
      <td height="15">6</td>
 
      <td>工作质量</td>
      <td colspan="4" align="left"><input name="staffappraisal.achievements3" class="work put3" type="text"  id="achievements3" size="4" value="0" style="height:12px"/></td>
    </tr>
     <tr>
      <td height="15">7</td>
 
      <td>任务完成率</td>
      <td rowspan="3">任务得分</td>
      <td colspan="4" align="left"><input name="staffappraisal.task1" class="achievement put3" type="text" id="task1" size="4" value="0" style="height:12px"/></td>
      <td rowspan="3" align="center">15</td>
 
      <td rowspan="3" align="center"><span style="color:red" id="sumScole3"></span></td>
      <td rowspan="3" align="center"><span style="color:red" id="pc3"></span></td>
      <td rowspan="3" align="center"><span style="color:red" id="timingMoney"></span></td>
      <td rowspan="3" align="center"><span style="color:red" id="timingMoneyMoney"></span></td>
      <td rowspan="3" align="center"><span style="color:red" id="timingMoneyScore"></span></td>
      <td rowspan="3" align="center">&nbsp;</td>
   </tr>
     <tr>
      <td height="15">8</td>
 
      <td>目标是否明确</td>
      <td colspan="4" align="left"><input name="staffappraisal.task2" type="text" class="achievement put3" id="task2" size="4" value="0" style="height:12px"/></td>
    </tr>
     <tr>
      <td height="15">9</td>
 
      <td>任务完成主动性</td>
      <td colspan="4" align="left"><input name="staffappraisal.task3" type="text" class="achievement put3" id="task3" size="4" value="0" style="height:12px"/></td>
    </tr>
     <tr>
      <td height="15">10</td>
 
      <td>专业技术能力</td>
      <td rowspan="3">能力得分</td>
      <td colspan="4" align="left"><input name="staffappraisal.ability1" class="stPay put3" type="text" id="ability1" size="4" value="0" style="height:12px"/></td>
      <td rowspan="3" align="center">15</td>
 
      <td rowspan="3" align="center"><span style="color:red" id="sumScole4"></span></td>
      <td rowspan="3" align="center"><span style="color:red" id="pc4"></span></td>
      <td rowspan="3" align="center"></td>
      <td rowspan="3" align="center"><span style="color:red" id="stPayMoney"></span></td>
      <td rowspan="3" align="center"><span style="color:red" id="stPayScore"></span></td>
      <td rowspan="3" align="center">&nbsp;</td>
   </tr>
     <tr>
      <td height="15">11</td>
 
      <td>管理能力</td>
      <td colspan="4" align="left"><input name="staffappraisal.ability2" class="stPay put3" type="text" id="ability2" size="4" value="0" style="height:12px"/></td>
    </tr>
     <tr>
      <td height="15">12</td>
 
      <td>综合素质能力</td>
      <td colspan="4" align="left"><input name="staffappraisal.ability3" class="stPay put3" type="text" id="ability3" size="4" value="0" style="height:12px"/></td>
    </tr>
     <tr>
      <td height="15">13</td>
 
      <td>出勤率</td>
      <td rowspan="3">态度得分</td>
      <td colspan="4" align="left"><input name="staffappraisal.manner1" type="text" class="chuqin put3" id="manner1" size="4" value="0" style="height:12px"/></td>
      <td rowspan="3" align="center">15</td>
 
      <td rowspan="3" align="center"><span style="color:red" id="sumScole5"></span></td>
      <td rowspan="3" align="center"><span style="color:red" id="pc5"></span></td>
      <td rowspan="3">&nbsp;</td>
      <td rowspan="3">&nbsp;</td>
      <td rowspan="3">&nbsp;</td>
      <td rowspan="3">&nbsp;</td>
   </tr>
     <tr>
      <td height="15">14</td>
 
      <td>工作主动性</td>
      <td colspan="4" align="left"><input name="staffappraisal.manner2" type="text" class="chuqin put3" id="manner2" size="4" value="0" style="height:12px"/></td>
    </tr>
     <tr>
      <td height="15">15</td>
 
      <td>文明礼貌素质</td>
      <td colspan="4" align="left"><input name="staffappraisal.manner3" type="text" class="chuqin put3" id="manner3" size="4" value="0" style="height:12px"/></td>
    </tr>
     <tr>
      <td height="15">得分</td>
 
      <td>总分 75</td>
      <td>实际得分</td>
      <td width="59" align="center"><span style="color:red" id="allMoney"></span></td>
      <td width="53">分数</td>
      <td width="47" align="center">&nbsp;</td>
      <td width="52"> 百分比</td>  
      <td align="center"><span style="color:red" id="allpc"></span></td>
      <td>考评金额</td>
      <td align="center"><span style="color:red" id="pushMoney"></span></td>
      <td>所得金额</td>
      <td align="center"><span style="color:red" id="pushMoneyMoney"></span></td>
 
      <td>得分</td>
      <td align="center"><span style="color:red" id="pushScore"></span></td>
   </tr>
   <tr>
      <td height="16">特殊人才</td>
 
      <td><span style="color:red" id="stPay"></span></td>
      <td>得分</td>
      <td width="59" align="center"><span style="color:red" id="stPayscore"></span></td>
      <td width="53">保密金额</td>
      <td width="47" align="center"><span style="color:red" id="secrecyPay"></span></td>
     <td width="52">保密得分</td>
      <td align="center"><span style="color:red" id="secrecyPayscore"></span></td>
     <td>安全金额</td>
      <td align="center"><span style="color:red" id="safetyAward"></span></td>
      <td>安全得分</td>
      <td align="center"><span style="color:red" id="safetyAwardscore"></span></td>
 
      <td>&nbsp;</td>
      <td align="center">&nbsp;</td>
   </tr>
     <tr>
      <td height="15">会议名称</td>
      <td>&nbsp;</td>
      <td>会议时间</td>
      <td colspan="3">&nbsp;</td>
 
      <td>&nbsp;</td>
      <td colspan="2">参会考评人签字</td>
      <td colspan="5">
       <input name="staffappraisal.checkPerson" class="nocheck" type="text" id="checkPerson"  style="width: 200px"/>
      	<input name="staffappraisal.appraisalKey" class="nocheck" type="hidden" id="appraisalKey"/>
      	<input name="staffappraisal.appraisalID" class="nocheck" type="hidden" id="appraisalID"/>
      	<input name="staffappraisal.payScaleID" class="nocheck" type="hidden" id="payScaleID"/>
      </td>
    </tr>
  </table>
  </div>
		</form>
		<!--搜索窗口 -->	
		  <form name="searchForm" id="searchForm" method="post">
        <div class="jqmWindow jqmWindowcss3" style="width: 400px;top: 10%;" id="jqModelSearch">
               
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table id="cataffSearchTable">
				<tr>
				<td>员工姓名：</td>
				<td><input name="staffappraisalsummary.staffName" size="17"/></td>
				</tr>
				<tr>
                    <td> 起始日期： </td>
                    <td><input name="startdate" onFocus="date(this);" size="17" />
                      至
                      <input name="enddate" onFocus="date(this);" size="17" /></td>
                  </tr>
                  <input name="search" type="hidden" value="search" />
				</table>
               <div align="center">
				  <input type="button" class="input-button" id="tosearch"	value=" 查询 " />
         	   </div> 
         	</div>
        </form>
	</body>
</html>
