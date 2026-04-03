<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
        <%@ page language="java" pageEncoding="UTF-8" %>
        <%@ taglib uri="/struts-tags" prefix="s" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%
        	String path = request.getContextPath();
        	String basePath = request.getScheme() + "://"
        			+ request.getServerName() + ":" + request.getServerPort()
        			+ path + "/";
        %>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>招聘渠道</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
        <script src="<%=basePath%>js/jquery.js" type="text/javascript">
        </script>
        <script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
        </script>
        <link href="<%=basePath%>css/ea/validate.css" rel="stylesheet" type="text/css" />
        <link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
        <script type="text/javascript" src="<%=basePath%>js/flexigrid.js">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js">
        </script>
        <script src="<%=basePath%>js/ea/human/office/production/recruitchannel.js"></script>
        
        <script type="text/javascript">
            var basePath = "<%=basePath%>";
            var ppageNumber = '${pageNumber}';
            var search = '${search}';
            var recruitChannelID = "";
            var token = 0;
            var notoken = 0;
            var chname = "" //删除时传参
            
            var retoken = 0;
            
        </script>
        
    </head>
    <body >
        <div class="main_main">
            <table class="JQueryflexme">
                <thead>
					<tr class="tablewith">
						<th width="40" align="center">
							选择
						</th>
						<th width="100" align="center">
							名称
						</th>
						<th width="100" align="center">
							类别
						</th>
						<th width="200" align="center">
							网址
						</th>
						<th width="200" align="center">
							详细地址
						</th>
						<th width="100" align="center">
							固定电话
						</th>
						<th width="200" align="center">
							乘车路线
						</th>
						<th width="100" align="center">
							联系人
						</th>
						<th width="150" align="center">
							联系方式
						</th>
						<th width="100" align="center">
							联系部门
						</th>
						<th width="200" align="center">
							备注
						</th>
					</tr>
				</thead>
                <tbody>
                    <%
                    	int number = 1;
                    %>
                    <s:iterator value="pageForm.list">
						<tr id="${recruitchannelid}" title="${channelname}">
							<td>
								<input type="radio" name="a" class="JQuerypersonvalue"
									value="${recruitchannelid}" />
							</td>
							<td>
								<span id="channelname">${channelname}</span>
							</td>
							<td>
								<span id="sorts">${sorts}</span>
							</td>
							<td>
								<span id="url">${url}</span>
							</td>
							<td>
								<span id="fullAddress">${fullAddress}</span>
							</td>
							<td>
								<span id="tel">${tel}</span>
							</td>
							<td>
								<span id="carroute">${carroute}</span>
							</td>
							<td>
								<span id="contactman">${contactman}</span>
							</td>
							<td>
								<span id="contactway">${contactway}</span>
							</td>
							<td>
								<span id="contactdept">${contactdept}</span>
							</td>
							<td>
								<span id="note">${note}</span>
								<span style="display: none" id="recruitchannelkey">${recruitchannelkey}</span>
								<span style="display: none" id="recruitchannelid">${recruitchannelid}</span>
								<span style="display: none" id="companyID">${companyID}</span>
								<span style="display: none" id="address">${address}</span>
							</td>
						</tr>
						<%
							number++;
						%>
                    </s:iterator>
                </tbody>
            </table>
            <c:import url="../../../../page_navigator.jsp">
                <c:param name="actionPath" value="ea/recruitchannel/ea_getRecruitChannelList.jspa?pageNumber=${pageNumber}&search=${search}">
                </c:param>
            </c:import>
        </div>
        
        <!-- 添加窗口 -->
        <form name="cstaffForm" id="cstaffForm" method="post" enctype="multipart/form-data">
            <div class="jqmWindow jqmWindowcss2" style="width: 700px;top:10%;" id="jqModel">
                <input type="submit" name="submit" style="display:none"/>
                <div class="drag">
                    招聘渠道 
                    <div class="close">
                    </div>
                </div>
        		<table width="680" height="211" border="0" align="center" cellpadding="0" cellspacing="0" id="stafftable">
        			<tr>
        				<td align="right" width="80" height="30">名称：</td>
        				<td width="220">
        					<input name="recruitchannel.channelname" id="channelname" class="put3" style="width:160px;"/>
        				</td>
        			
        				<td align="right" width="60" style="margin-right:0px">类别：</td>
        				<td width="220">
        					<select name="recruitchannel.sorts" id="sorts">
        						<option>请选择</option>
        					</select>
        					<a href="#" onclick="toCCode('scode20121122drr7pykbwr0000000002','#sorts','#cstaffForm')">新添</a>
        				</td>
        			</tr>
        			<tr>
        				<td align="right" height="30">固定电话：</td>
        				<td id="err" width="220">
        					<input name="recruitchannel.tel" id="tel" class="phone" style="width:160px;"/>
        				</td>
        				
        				<td align="right" >联系人：</td>
        				<td>
        					<input name="recruitchannel.contactman" id="contactman"/>
        				</td>
        			</tr>
        			<tr>
        				<td align="right" height="30">联系方式：</td>
        				<td>
        					<input name="recruitchannel.contactway" id="contactway" style="width:160px;"/>
        				</td>
        				
        				<td align="right">联系部门：</td>
        				<td>
        					<input name="recruitchannel.contactdept" id="contactdept"/>
        				</td>
        			</tr>
        			<tr>
        				<td align="right">网址：</td>
        				<td colspan="3" height="45">
        					<textarea name="recruitchannel.url" cols="60" rows="2" class="ckTextLength" maxlength="250" id="url"></textarea>
        				</td>
        			</tr>
        			<tr>
        				<td align="right">详细地址：</td>
        				<td class="JQueryaddress" colspan="3">
                            <input name="recruitchannel.address" id="address" type="hidden"/>
                            <input name="recruitchannel.fullAddress" id="fullAddress" type="hidden"/>
                            <select name="addressProvince" id="province" number='0' style="width:100px;">
                            </select>
                            <!-- <option>选择省</option>-->
                            <select name="addressCity" id="city" number='1' style="width:100px;">
                            </select>
                            <select name="addressCounty" id="county" number='2' style="width:100px;">
                            </select>
                            <select name="addressTown" id="addressTown" number='3' style="width:100px;">
                            </select>
                            <select name="addressVillage" id="addressVillage" number='4' style="width:100px;">
                            </select>
                            <br id="brid"/>
                            <select name="addressCommunity" id="addressCommunity" number='5' style="width:100px;">
                            </select>
                            <!-- <option>选择省</option>-->
                            <select name="addressFloor" id="addressFloor" number='6' style="width:100px;">
                            </select>
                            <select name="addressLayer" id="addressLayer" number='7' style="width:100px;">
                            </select>
                            <select name="addressSize" id="addressSize" number='8' style="width:100px;">
                            </select>
                        </td>
        			</tr>
        			<tr>
        				<td align="right">乘车路线：</td>
        				<td colspan="3" height="45">
        					<textarea name="recruitchannel.carroute" cols="60" rows="2" class="ckTextLength" maxlength="250" id="carroute"></textarea>
        				</td>
        			</tr>
        			<tr>
        				<td align="right">备注：</td>
        				<td colspan="3" height="45">
        					<textarea name="recruitchannel.note" cols="60" rows="2" class="ckTextLength" maxlength="250" id="note"></textarea>
        				</td>
        			</tr>
        		</table>
        		<div align="center">
        			<input type="button" class="input-button" id="save" value="保存" />
        			<input type="button" class="input-button JQueryreturn" value="取消" />
        			<input type="hidden" name="recruitchannel.recruitchannelkey" id="recruitchannelkey"/>
					<input type="hidden" name="recruitchannel.recruitchannelid" id="recruitchannelid"/>
					<input type="hidden" name="recruitchannel.companyID" id="companyID"/>
        		</div>
            </div>
            <iframe name="hidden" frameborder="0" noresize="noresize" border="0" framespacing="0" height="0"></iframe>
        </form>
        
        <!-- 添加地域 -->
        <div class="jqmWindow" style="width: 400px; right: 35%; top: 10%"
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
				<input type="button" class="input-button JQueryreturn2" value="取消" />
			</div>
		</div>
        
        <!--搜索窗口 -->
         <form name="recruitChannelForm" id="recruitChannelForm" method="post">
         <input type="submit" name="submit" style="display:none"/>
        <div class="jqmWindow" style="width: 280px;right: 45%;top: 20%;" id="jqModelSearch">
           
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
                        <td align="right" width="60" height="30">
                            类别：
                        </td>
                        <td>
                            <select name="recruitchannel.sorts" id="sorts">
        						<option>请选择</option>
        					</select>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" height="30">
                            名称：
                        </td>
                        <td>
                            <input name="recruitchannel.channelname" />
                        </td>
                    </tr>
                </table>
                <div align="center">
                    <input type="button" class="input-button" id="searchChannel" value="查询" />
                    <input type="button" class="input-button JQueryreturn" value="取消" />
                    <input name="search" type="hidden" value="search" />
                </div>
        </div> 
        </form>
        
        <!-- 代码新增 -->
        <div class="jqmWindow jqmWindowcss3" style="width: 300px; top: 10%;" id="newccode">
			<div class="drag">
				添加
			</div>
			<table>
				<tr>
					<td>
						代码名字：
					</td>
					<td>
						<input id="ccodevalue" />
						<input id="codePID" type="hidden" />
						<input id="selectID" type="hidden" />
						<input id="formID" type="hidden" />
					</td>
				</tr>
			</table>
			<div align="center">
				<input type="button" class="input-button" onclick="saveCCode()"
					value="确定" />
				<input type="button" class="input-button JQueryreturn1" value="取消" />
			</div>
		</div>
    </body>
</html>