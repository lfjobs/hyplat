<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
 	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />	
    <title>人才简历库</title>
    	<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
    	<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script language="javascript" type="text/javascript" src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script src="<%=basePath%>js/ea/production/cprocedure/talentResume_list.js"  type="text/javascript" ></script>
		<link rel="stylesheet" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"  type="text/css"/>
		<link rel="stylesheet" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"  type="text/css"/>
		<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css" />
		
		<script type="text/javascript">
		var basePath="<%=basePath%>";
		var tpID=""
		var name="";
		</script>
		<style type="text/css">
		.fieldName{
		text-align:right;
		width: 130px;
		}
		.goodsOutOfTheLibrary tr{
		height:30px;
		}
		#occlusion{
		background: rgba(255,255,255, 0.5);
		width: 100%;
		height: 100%;
		position: absolute;
		top: 0px;
		left: 0px;
		}
		#gotable  th{
		border: 1px solid #a8c7ce;
		}
		#gotable  td{
		border: 1px solid #a8c7ce;
		}
		input{
		border: 1px solid #BDBDBD;
		background-size:100% 100%;}
		span{
		display:-moz-inline-box;
		display:inline-block;
		}
		</style>
  </head>
  
  <body>
  <div  id="main_main" class="main_main">
  	<table class="fexlist">
  	  <thead>
  	  	<tr>
	  	  <th width="80" align="center">选择</th>
	  	  <th width="150" align="center">姓名</th>
	  	  <th width="100" align="center">性别</th>
	  	  <th width="150" align="center">职位名称</th>
	  	  <th width="110" align="center">工作年限</th>
	  	  <th width="110" align="center">学历</th>
	  	  <th width="150" align="center">投递日期</th>
	  	  <th width="100" align="center">状态</th>
		  <th width="100" align="center">来源</th>
  		</tr>
  	  </thead>
  	  <tbody class="tbody">
  	  	<c:forEach items="${pageForm.list}" var="l" >
  	  		<tr class="dps" name="dps">
  	  			<td><input type="checkbox" name="checkbox"  class="box" id="${l[0]}"></td>
  	  			<td><span class="name">${l[1]}</span></td>
  	  			<td><span>${l[2]}</span></td>
  	  			<td><span>${l[3]}</span></td>
  	  			<td><span>${l[4]}年</span></td>
  	  			<td><span>${l[5]}</span></td>
  	  			<td><span>${l[6]}</span></td>
  	  			<td><span>${l[7]=='00'?'待处理':l[7]=='01'?'已邀请':l[7]=='04'?'不合适':l[7]=='03'?'已接受邀请':'拒绝邀请'}</span></td>
				<td><span class="type">${l[8]=='00'?'投递':'抢人才'}</span></td>
  	  		</tr>
  	  	</c:forEach>
  	  </tbody>
  	</table>
  </div>
<form name="form" id="form" method="post">
	<input type="submit" id="submit" name="submit" onclick="TimeoutReload(10)" style="display: none;">
	<iframe name="hidden"  style="display: none;"></iframe>
<div class="interview_title">
<div class="interview_body" >
	<div style="width:100%;height:8%;background-color: #1582C1;text-align: center;">
		<font style="font-size: 25px;color:#fff;position: relative;top:25%;">面试邀请函</font>
	</div>
	<%--
	<div style="position: relative;top: 4%;height: 15%;">

		<div style="position: relative;left: 4%;width: 96%;">
				<font style="font-weight:bold;">***&nbsp;&nbsp;</font>
				<font style="color:#ABABAB">   你好：</font>
		</div>
		<div style="position: relative;left: 4%;width: 96%;color:#ABABAB;height:80%;">
				<textarea type="text" name="talentPool.content" style="width: 99%;height: 100%;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;经过我们公司的初步筛选，认为你与我们的职位要求很匹配，现诚挚邀请你来我公司面谈。请准时出席，如时间上有变化也请尽快与我们联系。
				</textarea>
		</div>
	</div>--%>
	<hr style="position: relative;top: 4%;"/>
	<div style="position: relative;top: 2%;height:50%;">
		<table style="width: 100%;height:100%; border-collapse:3px;">
			<tr class="qrc">
				<td align="right" width="20%;"><font style="color:red;">*</font>面试职位</td><td width="5%;"></td>
				<td>

                    <s:select list="list"  listKey="riId" listValue="jobTitle" name="talentPool.riId"  headerValue="请选择" cssClass="seri"
							  headerKey="" theme="simple"></s:select>
				</td>
			</tr>
			<tr>
				<td align="right" width="20%;"><font style="color:red;">*</font>面试时间</td><td width="5%;"></td>
				<td>
					<input type="text" class="interviewTime inputr" name="talentPool.interviewDate" style="width: 49%;height: 73%;background-image: url('<%=basePath%>images/ea/production/30123.png');" placeholder="请选择开始时间" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})">
					<select style="height: 75%;background-color: #F3F9FD;" class="ws"><option value="上午">上午</option><option value="下午">下午</option></select>
					<select style="height: 75%;width:22%;background-color: #F3F9FD;" class="sj">
						<option value="9:00">9:00</option>
						<option value="10:00">10:00</option>
						<option value="11:00">11:00</option>
						<option value="12:00">12:00</option>
					</select>
				</td>
			</tr>
			<tr>
				<td align="right"><font style="color:red;">*</font>面试地点</td><td></td>
				<td><input type="text"  style="background-color: #F3F9FD;width: 90%;height: 75%;" placeholder="请填写真实地点" name="talentPool.interviewPlace" class="interviewPlace inputr"></td>
			</tr>
			<tr>
				<td align="right">
					<font style="color:red;">*</font>联
					<span style="width:3.5%;"></span>系
					<span style="width:3.5%;"></span>人
				</td><td></td>
				<td><input type="text"  style="background-color: #F3F9FD;width: 90%;height: 75%;" placeholder="请选择联系人" name="talentPool.contactor" class="contactor inputr"></td>
			</tr>
			<tr>
				<td align="right"><font style="color:red;">*</font>联系电话</td><td></td>
				<td><input type="text"  style="background-color: #F3F9FD;width: 90%;height: 75%;" name="talentPool.contactTel" class="contactTel inputr"></td>
			</tr>
			<tr>
				<td align="right"><font>备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注</font></td><td></td>
				<td>
					<input type="checkbox" class="chbox" value="请携带简历" name="talentPool.remark">请携带简历
					<input type="checkbox" class="chbox" value="请带笔纸" name="talentPool.remark">请带笔纸
					<input type="checkbox" class="chbox" value="请带作品" name="talentPool.remark">请带作品
					<input type="checkbox" class="chbox" value="请着正装" name="talentPool.remark">请着正装<br>
					<input type="checkbox" class="chbox">其他
					<input type="text" style="background-color: #F3F9FD;width: 77%;height: 45%;" name="talentPool.remark">
				</td>
			</tr>
		</table>
	</div>
	<hr/>
	<div style="height:9%;">
		&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" name="ccbox" class="chbox ccbox"><font>同时发送短信通知</font><font  color="#858585" style="font-size: 12%;">（需要发送1条，短信剩余0条。余额不足，请联系销售充值。）</font>
	</div>
	<div style="height:8%;">
		<span style="position: relative;left: 25%;width: 20%;background-color: #1582C1;text-align: center;color: #fff;cursor:pointer;" class="buttom">发送</span>
		<span style="position: relative;left: 35%;width: 20%;text-align: center;color: #000;border:1px solid #000;cursor:pointer;" class="buttom">取消</span>
	</div>
</div>
</div>
</form>

	<c:import url="../../../page_navigator.jsp">
		<c:param name="actionPath"
				value="ea/tresume/ea_getListPage.jspa?pageNumber=${pageNumber}&statusbill=${statusbill}">
		</c:param>
	</c:import>
  <iframe name="hidden" frameborder="0" noresize="noresize" border="0"
		  framespacing="0" height="0"></iframe>

  </body>

  <script type="text/javascript">
	$(function () {
		$(".ws").change(function () {
            if($(".ws").val()=="上午"){
                $(".sj").empty();
                $(".sj").append("<option value='09:00'>09:00</option>");
                $(".sj").append("<option value='10:00'>10:00</option>");
                $(".sj").append("<option value='11:00'>11:00</option>");
                $(".sj").append("<option value='12:00'>12:00</option>");
            }else if($(".ws").val()=="下午"){
                $(".sj").empty();
                $(".sj").append("<option value='02:00'>02:00</option>");
                $(".sj").append("<option value='03:00'>03:00</option>");
                $(".sj").append("<option value='04:00'>04:00</option>");
                $(".sj").append("<option value='05:00'>05:00</option>");
            }
        })
    })
  </script>
</html>
