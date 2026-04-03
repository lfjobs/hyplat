<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%><html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="cache-control" content="no-cache" />
		<title>车辆准载座位管理</title>
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
		<script type="text/javascript"
			src="<%=basePath%>js/jqModal/jqModal.js"></script>
		<script src="<%=basePath%>js/ea/office_ea/Carseat.js"></script>  
<style type="text/css">
.xx{
	color:#FF0000;
	margin-right:2px;} 
</style>
	<script type="text/javascript">
   var basePath = '<%=basePath%>';
   var quasiID = '';
   var  search='${search}';
   var personurl = "";
   var notoken = 0;
   var pNumber = ${pageNumber};
   var carID=parent.carID;
   var token=0;
   var quzhi="";
   var carzuo='';
   var type='${type}';
	</script>	


	</head>
	<body >
		<form name="carquasiForm" id="carquasiForm" method="post">
		<input type="submit" name="submit" style="display: none" />
        <div class="main_main">
            <table class="JQueryflexme">
                <thead>
                    <tr class="tablewith">
                        <th width="40" align="center">
                            	请选择
                        </th>
                         <th width="40" align="center">
                            	自编号
                        </th>
                           <th width="150" align="center">
                            	车牌号
                        </th>
                         <th width="100" align="center">
                            	车型
                        </th>
                        <th width="100" align="center">
                            	车架号
                        </th>
                        <th width="150" align="center">
                            	排号
                        </th>
                        <th width="100" align="center">
                            	列数
                        </th>
                        <th width="100" align="center">
                           		座位状态
                        </th>   
                        <th width="100" align="center">
                           		准载人数
                        </th>
                        <th width="100" align="center">
                           		实载人数
                        </th>
                        <th width="100" align="center">
                            	超载人数
                        </th>
                        <th width="100" align="center">
                            	车辆所属单位
                        </th>
                        <th width="100" align="center">
                            	单位责任人
                        </th>
                        <th width="100" align="center">
                            	单位联系方式
                        </th>
     
                        
                    </tr>
                </thead>
                <tbody>
                    <%
                    int number = 1; %>
                    <s:iterator value="pageForm.list">
                        <tr id="${quasiID}"  >
                            <td>
                                <input type="radio" name="a" class="JQuerypersonvalue"
									value="${carID}" />
								<input type="hidden" name="quasiKey" id="quasiKey" />
								<input type="hidden" name="quasiID" id="quasiID" />
								<input type="hidden" name="companyID" id="companyID" />
								<input type="hidden" name="organizationID" id="organizationID" />
								<input type="hidden" name="carID" value="${carID}"
									id="carID" />
                            </td>
                            <td>
                                <span ><%=number%></span>
                            </td>
                             <td>
                               <span id="carNum">${carNum}</span>
                            </td>
                            <td>
                                <span id="carType">${carType}</span>
                            </td>
                             <td>
                                <span id="carFrameNum">${carFrameNum}</span>
                            </td>
                            <td>
                                <span id="numeral">${numeral}</span>
                            </td>
                             <td>
                                <span id="seatNum">${seatNum}</span>
                            </td>                                
                            <td>
                               <span id="seatState">${seatState=='00'?'未坐':'已坐'}</span>
                            </td>
                            <td>
                                <span id="QuasiLoad">${quasiLoad}</span>
                            </td>
                            <td>
                                <span id="realLoad">${realLoad}</span>
                            </td>
                            <td>
                                <span id="overLoad">${overLoad}</span>
                            </td>
                            <td>
                                <span id="carUtil">${carUtil}</span>
                            </td>
                            <td>
                                <span id="utilPeople">${utilPeople}</span>
                            </td>
                            <td>
                                <span id="utilTel">${utilTel}</span>
                                <span id="quasiID" style="display:none">${quasiID}</span>
                                 <span id="quasiKey"  style="display:none">${quasiKey}</span>
                            </td>
      
                        </tr>
                        <%number++;%>
                    </s:iterator>
                </tbody>
                </table>
            <c:import url="../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/carquasi/ea_getCarseatList.jspa?carInformation.carID=${carInformation.carID}&pageNumber=${pageNumber}&search=${search}&type=${type}">
                </c:param>
            </c:import>
             <iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
                <iframe src="" name="main" width="99%" scrolling="no"  marginwidth="0" height="0" marginheight="0" frameborder="0" id="mainframe" border="0" framespacing="0" noresize="noResize" vspale="0">
                </iframe>
        </div>
        </form>
		<!--车辆准载座位管理添加 -->
		<div class="contentbannb jqmWindow jqmWindowcss2" style="width: 680px; left:55%; top: 15%"
			id="jqModel">
			<form name="addseatForm" id="addseatForm" method="post"
				enctype="multipart/form-data">
				<input type="submit" name="submit" style="display: none" />
				<div class="content">
					<div class="contentbannb">
						<div class="drag">
							添加
							<div class="close"></div>
						</div>
					</div>
					<table width="100%" border="0" id="stafftable" cellpadding="5" cellspacing="5">
						<tr>
						    <td align="right">
								实载人数：
							</td>
							<td>
								<span id="rPeoples">0</span>
							</td>
							<td  align="right">
								超载人数：
							</td>
							<td>
								<span id="oPeople">0</span>
							</td>
							<td align="right">
								选择单位：
							</td>
							<td>
								<input type="button" name="button5" value="选择单位" id="xzwlaw" />
							</td>

						</tr>
						<tr>
							<td align="right">
								车辆所属单位：
							</td>
							<td>
								<span id="ccompanyname"></span>
							</td>
							<td align="right">
								单位责任人：
							</td>
							<td>
								<span id="cresponsible"></span>
							</td>
							<td align="right">
								单位联系方式：
							</td>
							<td>
								<span id="responsibleTel"></span>
							</td>
						</tr>
						<tr>
							<td align="right">
								座位状态：
							</td>
							<td>
								<select name="carquasi.seatState" id="seatState">
									<option value="00">
										未坐
									</option>
								</select>
							</td>
							<td align="right">
								排号：
							</td>
							<td>
								<select name="carquasi.numeral" id="numerals">
									<option value="" selected>
										请选择
									</option>
									<option value="1排">
										一排
									</option>
									<option value="2排">
										二排
									</option>
									<option value="3排">
										三排
									</option>
									<option value="4排">
										四排
									</option>
								</select>
							</td>
							<td align="right">
								列数：
							</td>
							<td>
								<select name="carquasi.seatNum" id="seatNums">
									<option value="" selected>
										请选择
									</option>
									<option value="1号">
										1号
									</option>
									<option value="2号">
										2号
									</option>
									<option value="3号">
										3号
									</option>
									<option value="4号">
										4号
									</option>
								</select>
							</td>
						</tr>
						<tr id="isShow" style="display: none">
							<td colspan="6" align="center">
								<div id="body_03"
									style="width: 600px; height: 130px; border: 1px solid #000000">
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="6" align="center">
								<input name="carInformation.carID" type="hidden"  id="numCarID"/>
								<input name="contactCompany.ccompanyID" id="ccompanyID"
									type="hidden" class="input" size="20" />
								<input type="button" class="input-button JQuerySubmit"
									style="cursor: pointer; width: 80px;" value="提交" />
								<input name="sub" value="${session_value}" type="hidden" />
								<!-- 代替token-->
								<input type="button" class="input-button JQueryreturn"
									style="cursor: pointer; width: 80px;" value="取消" />
							</td>
						</tr>
					</table>
				</div>
				<s:token></s:token>
			</form>
		</div>
		<!--车辆准载座位管理修改 -->
		<div class="contentbannb jqmWindow jqmWindowcss2"
			style="width: 680px; left: 50%; top: 15%" id="jqModelup">
			<form name="updateseatForm" id="updateseatForm" method="post"
				enctype="multipart/form-data">
				<input type="submit" name="submit" style="display: none" />
				<div class="content">
					<div class="contentbannb">
						<div class="drag">
							修改
							<div class="close"></div>
						</div>
					</div>
					<table width="100%" border="0" id="stafftables" cellpadding="5"
						cellspacing="5">
						<tr>
							<td align="right">
								排号:
							</td>
							<td>
								<select name="carquasi.numeral" id="numeral2">
									<option value="" selected>
										请选择
									</option>
									<option value="1排">
										一排
									</option>
									<option value="2排">
										二排
									</option>
									<option value="3排">
										三排
									</option>
									<option value="4排">
										四排
									</option>
								</select>
							</td>
							<td align="right">
								列数:
							</td>
							<td>
								<select name="carquasi.seatNum" id="seatNum2">
									<option value="" selected>
										请选择
									</option>
									<option value="1号">
										1号
									</option>
									<option value="2号">
										2号
									</option>
									<option value="3号">
										3号
									</option>
									<option value="4号">
										4号
									</option>
								</select>
							</td>
						</tr>
						<tr id="isShows" style="display: none">
							<td colspan="4" align="center">
								<div id="body_04"
									style="width: 600px; height: 130px; border: 1px solid #000000">
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="4" align="center">
								<input name="carInformation.carKey" id="carKey" type="hidden"
									class="input" size="20" />
								<input name="carInformation.carID" id="contactUserID"
									type="hidden" class="input" size="20" />
								<input type="button" class="input-button JQuerySubmits"
									style="cursor: pointer; width: 80px;" value="提交" />
								<input name="sub" value="${session_value}" type="hidden" />
								<!-- 代替token-->
								<input type="button" class="input-button JQueryreturns"
									style="cursor: pointer; width: 80px;" value="取消" />
							</td>
						</tr>
					</table>
				</div>
				<s:token></s:token>
			</form>
		</div>
		
		<%------------------------------------选择往来单位------------------------------------%>
		<form name="selectcompanyForm" id="selectcompanyForm" method="post"
			enctype="multipart/form-data">
			<input type="submit" name="submit" style="display: none" />
			<div class="jqmWindow jqmWindowcss1" style="width: 800px;left: 55%; top: 5%"
				id="companyjqModel">
				<div class="content1" style="width: 100%;">
					<div class="contentbannb">
						<div class="drag">
							选择往来单位
						</div>
					</div>
					<table width="100%" id="searchcompany" border="0"
						align="center" cellpadding="0" cellspacing="0"
						style="margin-top: 5px; background: #FFFFFF;">
						<tr>
							<td  style="height:33;" align="right">
								单位名称：
							</td>
							<td >
								<input name="ccompanyID" class="input" id="ccompanyID" size="10"
									style="margin-left: 2px;" />
							</td>
							<td align="right">
								往来关系：
							</td>
							<td >
								<s:select list="connectionlist" listKey="codeValue"
									id="contactConnections" listValue="codeValue" headerKey=""
									headerValue="--全部--" name="contactConnections" theme="simple"></s:select>
							</td>
							<td>
								<input type="button" class="btn02" id="searchcc" name="button7"
									value="查询" />
								<input type="button" class="btn02" id="qdcompany" name="button5"
									value="确定" />
								<input type="button" class="btn02 xzdw" name="button" value="新增" />
								<input type="button" class="btn02 JQueryreturnutil" name="button4"
									value="关闭" />
							</td>
							<td>
								<a id="dwsy" title="0">上一页</a>
							</td>
							<td>
								<a id="dwxy" title="0">下一页</a>
							</td>
							<td>
								<a id="dwzy">共&nbsp;&nbsp; <span style="color: red"
									id="zycount"></span>&nbsp;&nbsp; 页</a>
							</td>
						</tr>
					</table>
					<table width="100%"  border="0" align="center" cellpadding="0"
						cellspacing="0"
						style="margin-top: 5px; margin-bottom: 5px;">
						<tr>
							<td valign="top" align="left" >
								<div id="body_02cc"
									style="margin-top: 2px; display: none;  overflow: auto; height: 200px;">
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<s:token></s:token>
		</form>
		<!-- 查询信息 -->
			<form name="carSearchForm" id="carSearchForm" method="post" >
			<div class="jqmWindow" style="width:300px; right: 35%; top: 10%"
				id="jqModelcarSearch">
				<div class="drag">
					查询信息
					<div class="close">
					</div>
				</div>
				<table id="carSearchTable">
					
					<tr>
						<td align="right">
							车牌号：
						</td>
						<td>
							<input name="carquasi.carNum" />
						</td>
					</tr>
					<tr>
						<td align="right">
							车辆类型：
						</td>
						<td>
							<input name="carquasi.carType" />
						</td>
					</tr>
					<tr>
						<td align="right">
							车架号：
						</td>
						<td>
							<input name="carquasi.carFrameNum" />
						</td>
					</tr>
					<tr>
						<td align="right">
							车辆所属单位：
						</td>
						<td>
							<input name="carquasi.carUtil" />
						</td>
					</tr>
					<tr>
						<td align="right">
							单位责任人：
						</td>
						<td >
							<input name="carquasi.utilPeople" />
						</td>
					</tr>
				</table>
				<div align="center">
					<input type="button" class="input-button" id="searchCar"
						value=" 查询 " />
					<input name="search" type="hidden" value="search" />
					<input name="carInformation.carID" type="hidden" id="carIDs" />
					<input name="type" type="hidden" value="${type}" />
					
					
				</div>
			</div>
		</form>
		<!-- 车辆准载座位查看 -->
		<div class="contentbannb jqmWindow jqmWindowcss2" style="width: 680px; left: 50%; top: 15%" id="jqModelsee">
            <form name="seeseatForm" id="seeseatForm" method="post" enctype="multipart/form-data">
                <input type="submit" name="submit" style="display:none"/>
                <div class="content">
				  <div class="contentbannb">
				  	<div class="drag">查看
				    <div class="close"></div>
				  </div>
				  </div>
				 <table width="100%" border="0" id="stafftablesee">
                      <tr>
                        <td style="width:13%;" align="right">车牌号:</td>
                        <td style="width:33%;"><input name="carquasi.carNum"  value="${carquasi.carNum }"  id="carNum" size="10"/></td>
                     	<td style="width:19%;" align="right">车型:</td>
                        <td ><input name="carquasi.carType"  value="${carquasi.carType}"  id="carType" size="10"/></td>
                        
                      </tr>
                      <tr>
                        <td align="right">车架号:</td>
                        <td ><input name="carquasi.carFrameNum"  value="${carquasi.carFrameNum}"  id="carFrameNum" size="10"/></td>
                        <td align="right">准载人数:</td>
                        <td><input name="carquasi.quasiLoad"  value="${carquasi.quasiLoad }"  id="QuasiLoad" size="10"/></td>
                       </tr>
                       <tr>
                        
                        <td align="right">实载人数:</td>
                        <td><input name="carquasi.realLoad"  value="${carquasi.realLoad }"  id="realLoad" size="10"/></td>
                        <td align="right">超载人数:</td>
                        <td><input name="carquasi.overLoad"  value="${carquasi.overLoad }"  id="overLoad" size="10"/></td>
                      </tr>
                      <tr>
                        <td align="right">车辆所属单位:</td>
                        <td><input name="carquasi.carUtil"  value="${carquasi.carUtil }"  id="carUtil" size="10"/></td>
                        <td align="right">单位责任人:</td>
                        <td><input name="carquasi.utilPeople"  value="${carquasi.utilPeople }"  id="utilPeople" size="10"/></td>
                        
                      </tr>
                      <tr>
                        <td  align="right">单位联系方式:</td>
                        <td><input name="carquasi.utilTel"  value="${carquasi.utilTel }"  id="utilTel" size="10"/><span id="utilTel">${carquasi.utilTel}</span></td>
                        <td align="right">排号:</td>
                        <td><input name="carquasi.numeral"  value="${carquasi.numeral }"  id="numeral" size="10"/></td>
                        <td align="right">列数:</td>
                        <td colspan="3"><input name="carquasi.seatNum"  value="${carquasi.seatNum }"  id="seatNum" size="10"/></td>
                      </tr>
                      <tr >
                      	<td colspan="6" align="center">
                      		<div id="body_05" style="width:600px;height:130px;border:1px solid #000000"></div>
                      	</td>
                      </tr>
				</table>
				</div>
				<s:token></s:token>
            </form>
        </div>
		<!--JS遮罩层-->
		<div id="fullbg"></div>
       
    </body>
  
</html>