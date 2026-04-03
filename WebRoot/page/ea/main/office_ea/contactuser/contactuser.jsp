<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
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
		<title>往来个人管理</title>
		<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/flexigrid_blue.css" />
		<link rel="stylesheet" type="text/css"
			href="<%=basePath%>js/jqModal/css/jqModal_blue.css" />

		<script language="javascript" type="text/javascript"
			src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/flexigrid.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js"></script>
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<link href="<%=basePath%>css/ea/validate.css" rel="stylesheet"
			type="text/css" />
		<link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
		<script src="<%=basePath%>js/ea/validate.js" type="text/javascript"></script>
		<script src="<%=basePath%>js/ea/office_ea/contactuser/contactuser.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/common/organizationTree.js"></script>
		<script type="text/javascript">
   var relationID = '';
   var basePath='<%=basePath%>';           
   var pNumber =${pageNumber};  
   var token = 0;
   var notoken = 0;
   var search='${search}';
   var roomtpID = "";
   var hotelnameid = "";
   var accommodid = "";//根据酒店类型 名称 的id
   var roomNumid = ""; //房间号id
   var staffID = "";
   var roomtype = "";
   var deitnumid = "";
   var type = "${type}";//用于区分是短信平台查询
   var title="${title}";//显示短信平台列表标题的；
   var typemes = "${typemes}";
   var companyID = "${contactUser.companyID}";
   var opertionID = "";
</script>
	</head>
	<body>
		<div class="main_main" >
			<table class="JQueryflexme">
				<thead>
					<tr class="tablewith">
						<th width="40" align="center">
							请选择
						</th>
						<th width="60" align="center">
							序号
						</th>

						<th width="100" align="center">
							人员编号
						</th>

						<th width="100" align="center">
							档案编号
						</th>
						<th width="100" align="center">
							人员姓名
						</th>
						<th width="80" align="center">
							往来关系
						</th>
						<th width="100" align="center">
							曾用名
						</th>
						<th width="50" align="center">
							性别
						</th>
						<th width="100" align="center">
							出生日期
						</th>
						<th width="100" align="center">
							籍贯
						</th>
						<th width="100" align="center">
							国籍
						</th>
						<th width="100" align="center">
							民族
						</th>
						<th width="200" align="center">
							身份证
						</th>
						<th width="100" align="center">
							电话
						</th>
						<!--  
						<th width="250" align="center">
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
							备注
						</th>
						-->
					</tr>
				</thead>
				<tbody>
					<%
						int number = 1;
					%>
					<s:iterator value="pageForm.list">
						<tr id="${relationID}">
							<td>
								<input type="checkbox" class="chx JQuerypersonvalue"
									value="${relationID}" title="${staffID}" name="chbox" />
							</td>
							<td>
								<span><%=number%></span>
							</td>
							<td>
								<span id="staffCode">${staffCode}</span>
							</td>
							<td>
								<span id="recordCode">${recordCode}</span>
							</td>
							<td>
								<span id="staffName">${staffName}</span>
							</td>
							<td>
								<span id="relation">${relation}</span>
								<span id="companyName" style="display:none;">${companyName}</span>
							</td>
							<td>
								<span id="usedNmae">${usedNmae}</span>
							</td>
							<td>
								<span id="sex">${sex}</span>
							</td>
							<td>
								<span id="birthday" class="datas">${birthday}</span>
							</td>
							<td>
								<span id="nativePlace">${nativePlace}</span>
							</td>
							<td>
								<span id="nationality">${nationality}</span>
							</td>
							<td>
								<span id="nation">${nation}</span>
							</td>
							<td>
								<span id="staffIdentityCard">${staffIdentityCard}</span>
								<span id="staffID" style="display: none">${staffID}</span>
								<span id="relationID" style="display: none">${relationID}</span>
								<span id="relationKey" style="display: none">${relationKey}</span>
								<span style="display: none" id="photo">${photo}</span>
							</td>
						     <td>
								<span id="reference">${reference}</span>
							</td>
							<!--  
							<td>
								<span id="staffAddress">${staffAddress}</span>
							</td>
						
							<td>
								<span id="referenceCode">${referenceCode}</span>
							</td>
							<td>
								<span id="referenceOrganization">${referenceOrganization}</span>
							</td>
							<td>
								<span id="verifyTime" class="datas">${fn:substring(verifyTime,0,
									10)}</span>
							</td>
							<td>
								<span id="staffDesc">${staffDesc}</span>
							</td>
							-->
						</tr>
						<%
							number++;
						%>
					</s:iterator>
				</tbody>
			</table>
			<c:import url="../../../page_navigator.jsp">
				<c:param name="actionPath"
					value="ea/contactuser/ea_getListContactUser.jspa?pageNumber=${pageNumber}&search=${search}&type=${type}&title=${title}&typemes=${typemes}&contactUser.companyID=${contactUser.companyID}">
				</c:param>
			</c:import>
		</div>
		<iframe src="" name="main" width="99%" scrolling="no" style="height:0;"  marginwidth="0" marginheight="0" frameborder="0" id="mainframe" border="0" framespacing="0" noresize="noResize" vspale="0"></iframe>
		<span id="Relation" style="display: none"><s:property
				value="#session.tablesearch.relation" /></span>
		<span id="RecordCount" style="display: none"><s:property
				value="#session.RecordCount" /></span>
		<form name="cstaffForm" id="cstaffForm" method="post">
			<input type="submit" name="submit" style="display: none" />
			<input id="relationID" type="hidden" class="input"  name="contactUser.relationID" />
			<div class="contentbannb jqmWindow jqmWindowcss2" style="top: 10%"
				id="jqModel">
				<div class="content">
					<div class="contentbannb">
						<div class="drag">
							往来个人
							<div class="close"></div>
						</div>
					</div>
					<table width="685" height="220" border="0" id="stafftable"
						align="center" cellpadding="0" cellspacing="0"
						style="margin-top: 5px; margin-bottom: 5px;">
						<tr>
							<td>
								<table width="685" height="200" border="0" id="stafftable2"
									align="center" cellpadding="0" cellspacing="0"
									style="margin-top: 5px; margin-bottom: 5px;">
									<tr>
										<td align="right">
											人员编号：
										</td>
										<td>
											<input type="text" id="staffCode" readonly="readonly" />
										</td>
										<td align="right">
											档案编号：
										</td>
										<td>
											<input type="text" id="recordCode" readonly="readonly" />
										</td>
										<td id="phototd" rowspan="6" align="center">
											<img width="99" height="135" id="photo" />
										</td>
									</tr>
									<tr>
										<td align="right">
											姓名：
										</td>
										<td>
											<input type="text" id="staffName" readonly="readonly" />
										</td>
										<td align="right">
											曾用名：
										</td>
										<td>
											<input readonly="readonly" id="usedNmae" type="text"
												class="input" size="20" />
										</td>
									</tr>
									<tr>
										<td align="right">
											往来关系：
										</td>
										<td>
											<input readonly="readonly" id="relation" type="text"
												class="input" size="20" />
										</td>
										<td align="right">
											性别：
										</td>
										<td>
											<input type="text" id='sex' readonly="readonly" />
										</td>
									</tr>
									<tr>
										<td align="right">
											籍贯：
										</td>
										<td>
											<input type="text" id='nativePlace' readonly="readonly" />
										</td>
										<td align="right">
											国籍：
										</td>
										<td>
											<input type="text" id='nationality' readonly="readonly" />
										</td>
									</tr>
									<tr>
										<td align="right">
											民族：
										</td>
										<td>
											<input type="text" id='nation' readonly="readonly" />
										</td>
										<td align="right">
											身份证：
										</td>
										<td>
											<input id="staffIdentityCard" readonly="readonly" type="text"
												class="input" size="20" />
										</td>
									</tr>
									<!--  
									<tr>
										<td align="right">
											电话：
										</td>
										<td>
											<input id="reference" readonly="readonly" type="text"
												class="input" size="20" />
										</td>
										<td align="right">
											录入时间：
										</td>
										<td>
											<input readonly="readonly" id="verifyTime" type="text"
												class="input" size="20" />
										</td>
									</tr>
									<tr>
										<td align="right">
											qq：
										</td>
										<td>
											<input readonly="readonly" id="referenceCode" type="text"
												class="input" size="20" />
										</td>
										<td align="right">
											邮箱：
										</td>
										<td>
											<input id="referenceOrganization" readonly="readonly"
												type="text" class="input" size="20" />
										</td>
									</tr>
									<tr>
										<td height="40" align="right">
											身份证地址：
										</td>
										<td class="JQueryaddress" colspan="4">
											<input id="staffAddress" type="text" readonly="readonly"
												class="input" style="width: 490px" />
										</td>
									</tr>
									<tr>
										<td align="right">
											备注：
										</td>
										<td colspan="4">
											<input id="staffDesc" readonly="readonly" type="text"
												style="width: 490px; height: 50px" class="input" />
										</td>
									</tr>
									<tr>
										<td colspan="5" align="center">
											<input id="staffID" type="hidden" class="input"
												name="contactUser.staffID" />
											<input id="relationID" type="hidden" class="input"
												name="contactUser.relationID" />
											<input type="button" class="input-button JQueryreturn"
												style="cursor: pointer; width: 80px;" value="关闭" />
										</td>
									</tr>
									-->
								</table>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</form>
		<div class="jqmWindow" style="width: 350px; right: 45%;; top: 10%"
			id="jqModelSearch">
			<form name="SearchForm" id="SearchForm" method="post">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					查询往来个人
					<div class="close">
					</div>
				</div>
				<table width="396" id="cataffSearchTable">
					<tr>
						<td width="123" align="right">
							姓名：
						</td>
						<td width="261">
							<input name="contactUser.staffName" />
						</td>
					</tr>
					<tr>
						<td width="123" align="right">
							编号：
						</td>
						<td width="261">
							<input name="contactUser.staffCode" />
                        </td>
              </tr>
                    <tr>
                        <td align="right">个人身份证号：</td>
                     <td><input name="contactUser.staffIdentityCard" /></td>
                    </tr>
                    <!--  
                    <tr>
                            <td align="right">个人身份证地址：</td>
                     <td><input name="contactUser.staffAddress" /></td>
                    </tr>
                    <tr>
                        <td align="right">个人电话：</td>
                     <td><input name="contactUser.reference" /></td>
                    </tr>
                    -->
                    <tr>
                        <td align="right">往来关系：</td>
                     <td>
                     <select id="relations"></select>
                     </td>
                    </tr>
                </table>
            <div align="center">
              <input type="button" class="input-button" id="tosearch" value=" 查询 " />
              <input name="search" type="hidden" value="search" />
            </div>
            </form>
        </div>

		<div class="jqmWindow" style="width: 400px; right: 25%;; top: 10%"
			id="jqModelprintIn">
			<form name="printInfoForm" id="printInfoForm" method="post">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					打印信息
					<div class="close">
					</div>
				</div>
				<table width="396" id="printstaff">
					<tr>
						<td align="right">
							标题：
						</td>
						<td>
							<input size="40" maxlength="40" name="printInfo.credentialsTitle" />
						</td>
					</tr>
					<tr>
						<td width="100" align="right">
							证件名称：
						</td>
						<td width="296">
							<input size="40" name="printInfo.credentialsName" />
						</td>
					</tr>
					<tr>
						<td align="right">
							职务：
						</td>
						<td>
							<input size="40" name="printInfo.credentialsCode" />
						</td>
					</tr>
					<tr>
						<td align="right">
							组别：
						</td>
						<td>
							<input size="40" name="printInfo.credentialsType" />
						</td>
					</tr>
					<tr>
						<td align="right">
							开始时间：
						</td>
						<td>
							<input size="40" name="printInfo.credentialsDate"
								onfocus="date(this);" />
						</td>
					</tr>
					<tr>
						<td align="right">
							结束时间：
						</td>
						<td>
							<input size="40" name="printInfo.credentialsDate2"
								onfocus="date(this);" />
						</td>
					</tr>
					<tr>
						<td align="right">
							地点：
						</td>
						<td>
							<input size="40" name="printInfo.address" />
						</td>
					</tr>
					<tr>
						<td align="right">
							大小比例：
						</td>
						<td>
							<input type="radio" name="printInfo.size" value="1.3" checked />
							130%
							<input type="radio" name="printInfo.size" value="1.25" checked />
							125%
							<input type="radio" name="printInfo.size" value="1.2" checked />
							120%
							<input type="radio" name="printInfo.size" value="1" />
							100%
							<input type="radio" name="printInfo.size" value="0.9" />
							90%
							<input type="radio" name="printInfo.size" value="0.8" />
							80%
							<input type="radio" name="printInfo.size" value="0.7" />
							70%
							<input type="radio" name="printInfo.size" value="0.6" />
							60%
							<input type="radio" name="printInfo.size" value="0.5" />
							50%
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="queding" value=" 打印 " />
				</div>
			</form>
		</div>

		<!--         房间分配         -->
		<div class="jqmWindow" style="width: 700px; right: 33%; top: 10%;"
			id="jqModelSearchses">
			<form name="cstaffSearchForm" id="cstaffSearchForm" method="post">
				<input type="submit" name="submit" style="display: none" />
				<div class="drag">
					酒店信息
					<div class="close">
					</div>
				</div>
				<table id="hotelSearchTable" align="center" border="0px">
					<tr align="center">
						<td align="center">
							酒店名称：
						</td>
						<td align="left">
							<select id="hotelName" style="width: 200px" readonly="readonly" >
							</select>
						</td>

						<td>
							楼层：
						</td>
						<td >
							<input id="floor" 
								style="text-align: right;width: 60px;"  readonly="readonly"/>层
						</td>
					</tr>
					<tr align="center">
						<td>
							星级：
						</td>
						<td align="left">
							<input id="stars"style="text-align: center;width: 80px;" readonly="readonly" />
							<input id="starsType" type="hidden"  />
						</td>

						<td>
							房间类别：
						</td>
						<td>
							<select id="roomtype" name=""style="width: 100px" readonly="readonly" >
							</select>
						</td>
					</tr>
					
					<tr>
						<td>
							标价(RMB)：
						</td>
						<td align="left">
							<input id="roomPrice"   readonly="readonly"
								style="width: 80px; text-align: right;" />
						</td>
						<td>
							床&nbsp;位&nbsp;&nbsp;总&nbsp;数：
						</td>
						<td align="left">
						<input id ="bedNum"
								style="width: 80px; text-align: right;"  readonly="readonly"/>
						</td>
					</tr>
					<tr>
						<td><font color="red">*</font>
							折扣价(RMB)：
						</td>
						<td align="left">
							<input id= "roomDisPrice" name="accHot.roomDisPrice"
								style="width: 80px; text-align: right;" class ="isNaN put3"/>
						</td>
						<td>
							入&nbsp;住&nbsp;&nbsp;床&nbsp;位：
						</td>
						<td align="left">
						<input id = "bedOccNum" name = "accommod.bedOccNum"
								style="width: 80px; text-align: right;"  readonly="readonly"/>
						</td>
					</tr>
					<tr>
						<td><font color="red">*</font>
							协议价(RMB)：
						</td>
						<td align="left">
							<input  id = "roomAgrPrice" name="accHot.roomAgrPrice"
								style="width: 80px; text-align: right;" class =" isNaN put3"/>
						</td>
						<td>
							未&nbsp;住&nbsp;&nbsp;空&nbsp;位：
						</td>
						<td align="left">
						<input id="wz" readonly="readonly"
								style="width: 80px; text-align: right;"  readonly="readonly"/>
						</td>
					</tr>
					
					<tr align="center">
						<td>
							房间号：
						</td>
						<td colSpan="3" style="padding: 1em 30px 0em 0px;">
							<div id="alldiv"
								style="padding: 2px; border: #000 solid 2px; width =140px; height: 100px;">
								<div
									style="display: none; margin: 2px; float: left; border: #000 solid 2px;"
									id="d000">
									<input type="radio" id="r" class="radio" />
									<input id="t" type="text" disabled="disabled"
										style="width: 30px" value="000" />
								</div>
							</div>
						</td>
					</tr>

					<tr align="center">
						<td>
							备注：
						</td>
						<td colSpan="3" align="left">
							<textarea rows="3" cols="50" id ="remarks" ></textarea>
							<input type="hidden" id="accommodHotKey"/>
							<input type="hidden" id="accommodHotID"/>
							<input type="hidden" id="createName"/>
							<input type="hidden" id="createDate"/>
							<input type="hidden" id="companyID"/>
							<input type="hidden" id="organizationID"/>
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="addh" value=" 保存 " />
					<input name="search" type="hidden" value="search" />
				</div>
			</form>
		</div>
		<div class="menu01">
            <ul>
                <li>
                    <ul class="menu00" style="z-index: 1">
                        <li>
                            <a href="#" id="hotList">住宿管理</a>
                        </li>
                        <li>
                            <a href="#" id="billList">票据管理</a>
                        </li>
                    </ul>
                </li>
            </ul>
       </div>

		<iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
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