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
        <title>离职员工管理</title>
<script type="text/javascript" src="<%=basePath%>js/common/common.js"></script> 
        <script src="<%=basePath%>js/jquery.js" type="text/javascript">
        </script>
        <script src="<%=basePath%>js/ea/validate.js" type="text/javascript">
        </script>
        <link href="<%=basePath%>/css/ea/staff.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/flexigrid_blue.css"/>
        <link rel="stylesheet" type="text/css" href="<%=basePath%>js/jqModal/css/jqModal_blue.css"/>
        <script type="text/javascript" src="<%=basePath%>js/flexigrid.js">
        </script>
            <script src="<%=basePath%>js/ea/human/staff_info.js">
        </script>
        <script src="<%=basePath%>js/ea/human/cstaff.js"></script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqDnR.js">
        </script>
        <script type="text/javascript" src="<%=basePath%>js/jqModal/jqModal.js">
        </script>
        <script src="<%=basePath%>js/ea/human/office/production/cstaff_dimission.js"></script>
        
        <script  src="<%=basePath%>js/tree/codebase/dhtmlxcommon.js"></script>
		<script  src="<%=basePath%>js/tree/codebase/dhtmlxtree.js"></script>
		<link rel="STYLESHEET" type="text/css" href="<%=basePath%>js/tree/codebase/dhtmlxtree.css"/>
		<script type="text/javascript" src="<%=basePath%>js/common/organizationTree.js"></script>
        
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
							人员编号
						</th>
						<th width="100" align="center">
							档案编号
						</th>
						<th width="100" align="center">
							人员姓名
						</th>
						<th width="100" align="center">
							电话
						</th>
						<th width="100" align="center">
							离职时间
						</th>
						<th width="100" align="center">
							离职原因
						</th>
						<th width="100" align="center">
							经手人
						</th>
						<th width="100" align="center">
							离职状态
						</th>
						<th width="100" align="center">
							曾用名
						</th>
						<th width="80" align="center">
							性别
						</th>
						<th width="100" align="center">
							出生日期
						</th>
						<th width="80" align="center">
							国籍
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
					</tr>
				</thead>
                <tbody>
                    <%
                    	int number = 1;
                    %>
                    <s:iterator value="pageForm.list">
						<tr id="${staffID}" class="${staffName}">
							<td>
								<input type="radio" name="a" class="JQuerypersonvalue"
									value="${staffID}" />
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
								<span id="reference">${reference}</span>
							</td>
							<td>
								<span id="dimissionDate" class="datas">${dimissionDate}</span>
							</td>
							<td>
								<span id="dimissionCause">${dimissionCause}</span>
							</td>
							<td>
								<span id="issued">${issued}</span>
							</td>
							<td>
								<span id="dimissionStatus">${dimissionStatus}</span>
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
								<span id="nationality">${nationality}</span>
							</td>
							<td>
								<span id="nativePlace">${nativePlace}</span>
							</td>
							<td>
								<span id="nation">${nation}</span>
							</td>
							<td>
								<span id="staffIdentityCard">${staffIdentityCard}</span>
								<span style="display: none" id="schedulingID">${schedulingID}</span>
								<span style="display: none" id="staffKey">${staffKey}</span>
								<span style="display: none" id="staffID">${staffID}</span>
								<span style="display: none" id="photo">${photo}</span>

								<span style="display: none" id="dimissionStaffID">${dimissionStaffID}</span>
								<span style="display: none" id="dimissionStaffKey">${dimissionStaffKey}</span>
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
								<span id="verifyTime" class="datas">${verifyTime}</span>
							</td>
							<td>
								<span id="politicsStatus">${politicsStatus}</span>
							</td>
							<td>
								<span id="culturalDegree">${culturalDegree}</span>
							</td>
							<td>
								<span id="marriage">${marriage}</span>
							</td>
							<td>
								<span id="health">${health}</span>
							</td>
							<td>
								<span id="bankNum">${bankNum}</span>
							</td>
							<td>
								<span id="staffDesc">${staffDesc}</span>
								<span style="display: none" id="address">${address}</span>
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
                <c:param name="actionPath" value="ea/cosdimission/ea_getListCOSDimission.jspa?pageNumber=${pageNumber}&search=${search}">
                </c:param>
            </c:import>

            <div style="200px;overflow: ">
                <iframe src="" name="main" width="100%" marginwidth="0" scrolling="no"  height="0" marginheight="0"  frameborder="0" id="mainframe" border="0" framespacing="0" noresize="noResize" vspale="0">
                </iframe>
            </div>
        </div>
        <form name="cstaffForm" id="cstaffForm" method="post" enctype="multipart/form-data">
        <div class="jqmWindow jqmWindowcss2" style="width: 700px;top:10%" id="jqModel">
                <input type="submit" name="submit" style="display:none"/>
                <div class="drag">
                    员工详细信息 
                    <div class="close">
                    </div>
                </div>
                <table width="690" height="211" border="0" align="center" cellpadding="0" cellspacing="0" id="stafftable">
                    <tr>
                        <td width="103" height="27" align="right">
                            人员编号：
                        </td>
                        <td width="140">
                            <input name="cstaff.staffCode"  id="staffCode" size="20"/>
                        </td>
                        <td width="147" align="right">
                            档案编号：
                        </td>
                        <td width="148">
                            <input name="cstaff.recordCode" id="recordCode" size="20"/>
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
                            <input name="cstaff.staffName" id="staffName" size="20"/>
                        </td>
                        <td height="27" align="right">
                            离职时间：
                        </td>
                        <td>
                            <input name="cstaff.dimissionDate" id="dimissionDate" size="20"/>
                        </td>
                    </tr>
                       <tr>
                        <td height="27" align="right">
                            离职原因：
                        </td>
                        <td>
                            <input name="cstaff.dimissionCause" id="dimissionCause" size="20"/>
                        </td>
                        <td height="27" align="right">
                            经手人：
                        </td>
                        <td>
                            <input name="cstaff.issued" id="issued" size="20"/>
                        </td>
                       </tr>
                       <tr>
                       <td height="27" align="right">
                            离职状态：
                        </td>
                        <td>
                            <input name="cstaff.dimissionStatus" id="dimissionStatus" size="20"/>
                        </td>
                        <td align="right">
                            曾用名：
                        </td>
                        <td>
                            <input name="cstaff.usedNmae" id="usedNmae" size="20"/>
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
                            <input name="cstaff.birthday" id="birthday" size="20" />
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
                            <input name="cstaff.staffIdentityCard" id="staffIdentityCard" size="20"/>
                        </td>
                        <td align="right">
                            国籍：
                        </td>
                        <td>
                            <select id="nationality" name="cstaff.nationality">
                            </select>
                        </td>
                        <td>
                            <input name="photo" id="staffphoto"  contentEditable="false" type="file" /><input name="cstaff.photo" type="hidden" id="photo" size="14"/><input name="cstaff.staffID" id="staffID" type="hidden"/><input name="cstaff.staffKey" id="staffKey" type="hidden"/><input name="cstaff.schedulingID" id="schedulingID" type="hidden"/><input name="sub" value="${session_value}" type="hidden" /><!-- 代替token-->
                        </td>
                    </tr>
                    <!--  
                     <tr>
                        <td align="right">
                            电话：
                        </td>
                        <td>
                            <input name="cstaff.reference"  type="text" id="reference" size="20" />
                        </td>
                       <td align="right">邮箱：</td>
                                <td><input name="cstaff.referenceOrganization"  type="text" id="referenceOrganization" size="20" /></td>
                     </tr>
                        <tr>
                        <td height="27" align="right">
                            QQ：
                        </td>
                        <td>
                            <input name="cstaff.referenceCode" type="text" id="referenceCode" size="20" />
                        </td>
                        <td align="right">
                            录入时间：
                        </td>
                        <td>
                            <input name="cstaff.verifyTime" type="text"  id="verifyTime" size="20" />
                        </td>
                        <td>
                            <input name="photo" id="staffphoto"  contentEditable="false" type="file" /><input name="cstaff.photo" type="hidden" id="photo" size="14"/><input name="cstaff.staffID" id="staffID" type="hidden"/><input name="cstaff.staffKey" id="staffKey" type="hidden"/><input name="cstaff.schedulingID" id="schedulingID" type="hidden"/><input name="sub" value="${session_value}" type="hidden" /><!-- 代替token
                        </td>
                    </tr>
                    <tr>
						<td height="27" align="right">
							政治面貌：
						</td>
						<td>
							<select name="cstaff.politicsStatus" id="politicsStatus" class="model3">
								<option value="群众" selected="selected">群众</option>
								<option value="共青团员">共青团员</option>
								<option value="中共党员">中共党员</option>
								<option value="民主党">民主党</option>
								<option value="其他">其他</option>
							</select>
						</td>
						<td align="right">
							婚姻状况：
						</td>
						<td>
							<select name="cstaff.marriage" id="marriage" class="model3">
								<option value="未婚" selected="selected">未婚</option>
								<option value="已婚">已婚</option>
							</select>
						</td>
						<td>
							银行帐号：<input name="cstaff.bankNum" type="text" class="model3"
								id="bankNum" style="width: 80px" />
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
							<select name="cstaff.culturalDegree" id="culturalDegree" class="model3">
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
                            <input name="cstaff.address" id="address" type="hidden"/>
                            <select name="addressProvince" id="province" number='0' style="width:110px;">
                            </select>
                            <!-- <option>选择省</option>
                            <select name="addressCity" id="city" number='1' style="width:110px;">
                            </select>
                            <select name="addressCounty" id="county" number='2' style="width:110px;">
                            </select>
                            <select name="addressTown" id="addressTown" number='3' style="width:110px;">
                            </select>
                            <select name="addressVillage" id="addressVillage" number='4' style="width:110px;">
                            </select>
                            <select name="addressCommunity" id="addressCommunity" number='5' style="width:110px;">
                            </select>
                            <!-- <option>选择省</option>
                            <select name="addressFloor" id="addressFloor" number='6' style="width:110px;">
                            </select>
                            <select name="addressLayer" id="addressLayer" number='7' style="width:110px;">
                            </select>
                            <select name="addressSize" id="addressSize" number='8' style="width:110px;">
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td height="27" align="right">
                            备注：
                        </td>
                        <td colspan="3">
                            <textarea name="cstaff.staffDesc" cols="40" rows="5" id="staffDesc">
                            </textarea>
                        </td>
                    </tr>
                    -->
                </table>
                <div align="center">
                </div>
           
        </div> </form>
        <!--搜索窗口 -->
         <form name="cstaffDimForm" id="cstaffDimForm" method="post">
         <input type="submit" name="submit" style="display:none"/>
        <div class="jqmWindow" style="width: 400px;right: 25%;top: 10%;" id="jqModelSearch">
           
                <div class="drag">
                    查询信息
                </div>
                <table id="cataffSearchTable">
                    <tr>
                        <td>
                            查询条件
                        </td>
                    </tr>
                    <tr>
                        <td>
                            人员编号：
                        </td>
                        <td>
                            <input name="codi.staffCode" />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            人员姓名：
                        </td>
                        <td>
                            <input name="codi.staffName" />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            身份证：
                        </td>
                        <td>
                            <input name="codi.staffIdentityCard" />
                        </td>
                    </tr>
                </table>
                <div align="center">
                    <input type="button" class="input-button" id="searchDim" value="查询" /><input type="button" class="input-button JQueryreturn" value="取消" /><input name="search" type="hidden" value="search" />
                </div>
        </div> 
        </form>
        
        <!--分配岗位 -->
        <div class="jqmWindow" style="width: 500px;right: 30%;top: 15%;" id="dimToCosDept">
          <div class="drag"> <span id="stname"></span>——分配专岗
            <div class="close"></div>
          </div>
            <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          	<tr>
          		<td width="20%" height="30" align="right">选择部门：</td>
               <td width="30%" id="upper"><input type="text" id="oraganizationName" style="width:120px;" readonly="readonly" value="请选择部门"/><a href="#" onclick="javascript:getPID()"><img src="<%=basePath %>images/up.jpg" style="border: 0;"/></a>
                		</td>
             	<td width="15%" align="right">选择岗位：</td>
             	<td>
             		<select id="organizationPost">
             			<option value="">请先选择部门</option>
             		</select>
             	</td>
          	</tr>
            </table>
             <div align="center">
                 <input type="button" class="input-button" id="saveDimToCos" value="保存" />
           </div>
           <div style="display:none;width: 200px; height: 240px;position: absolute;top:57%;left:8%;z-index: 4 ; background-color:#e1ecfc; filter : Alpha(opacity=100);"
			id="jqModels"></div> 
        </div>
    </body>
    
    <script type="text/javascript">
        var basePath = "<%=basePath%>";
        var personvalue = "${staffID}";
        var personurl = "";
        var staffName="";
        var ppageNumber = '${pageNumber}';
        var session_val = '${session_value}';
        var psearch = '${search}';
        var codimission = '${dimission}';  //离职员工原因
        var dimissionStaffID="${dimissionStaffID}";
        var retoken = 0;
        var token = 0;
        var notoken = 0;
        
        var comID = '${account.companyID}';
		var companyName='<%=request.getAttribute("companyName")%>';
		var parentID;
		var treeid = null;
		var treename;
		var parentid;
		var parentname;
		var organizationid;
		var tree;
		var me = document.getElementById("oraganizationName").value;
		tree = new dhtmlXTreeObject("jqModels", "100%", "100%", 0);
		tree.enableDragAndDrop(false);
		tree.enableHighlighting(1);
		tree.enableCheckBoxes(0);
		tree.enableThreeStateCheckboxes(false);
		tree.setSkin(basePath + 'js/tree/dhx_skyblue');
		tree.setImagePath(basePath + "js/tree/codebase/imgs/");
		tree.loadXML(basePath + "js/tree/common/tree_b.xml");
		tree.insertNewChild("0", comID, companyName, 0, 0, 0, 0);
		$.ajaxSetup({async:false});
		
		tree.setOnClickHandler(function() {
			getDatas();
		});
		tree.setOnDblClickHandler(function(){
			$("#oraganizationName").attr("value",treename);
			$("#jqModels").hide();
			
			var url = basePath +"ea/departmentpost/sajax_ea_getOrganizationPost.jspa?departmentPost.organizationID="+treeid+"&date="+new Date();
			$.ajax({
					url : encodeURI(url),
					type : "get",
					async : true,
					dataType : "json",
					success : function cbf(data) {
						var member = eval("(" + data + ")");
						var persons = member.departmentPostlist;
						var str = "";
						if(persons.length == 0){
							str = "<option value=''>此部门无岗位</option>";
						}else{
							str = "<option value='' id='firstpost'>请选择岗位</option>";
							for(var i=0;i<persons.length;i++){
								var obj = persons[i].postName;
								objID = persons[i].depPostID;
								str += "<option value='"+objID+"'>"+obj+"</option>";
							}
						}
						$("#organizationPost select").empty();
						$("#organizationPost").html(str);
					}
			});
		});
		
		function getPID() {
			if(document.getElementById("jqModels").style.display=="none"){
	    		$("img","#upper").attr("src",basePath + "images/down.jpg");
				$("#jqModels").show();
			}else{
				$("img","#upper").attr("src",basePath + "images/up.jpg");
				$("#jqModels").hide();
			}
		}
		function getDatas(){
			treeid = tree.getSelectedItemId();
			treename = tree.getItemText(treeid);
			parentid = tree.getParentId(treeid);
			parentname = tree.getItemText(parentid);
			tree.deleteChildItems(treeid);
			var url1 = basePath + "ea/organization/sajax_ea_getOrganizationList.jspa?organizationID="+treeid+"&date="+new Date().toLocaleString();
			$.ajax({
				url: encodeURI(url1),
				type: "get",
				async: true,
				dataType: "json",
				success: function cbf(data){
						var member = eval("(" + data + ")");
						var nologin = member.nologin;
	                  	if(nologin){
	                  		document.location.href =basePath+"page/ea/not_login.jsp";
	                    }
						var organizationList = member.organizationList;
						if (null == organizationList) {
							return;
						}
						for (var i = 0; i < organizationList.length; i++) {
							tree.insertNewChild(treeid,
									organizationList[i].organizationID,
									organizationList[i].organizationName,
									0, 0, 0, 0);
	
						}
				},
				error: function cbf(data){
				alert("数据获取失败！");
				}
			});
		}
    </script>
</html>