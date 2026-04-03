<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>办公室-后勤管理-安全管理科</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
		<link href="<%=basePath %>css/navigation_a.css" rel="stylesheet" type="text/css" />	
		<script src="<%=basePath%>js/jquery.js" type="text/javascript"></script>
		<script type="text/javascript" src="<%=basePath %>/js/dropdown/extendPageMenu.js"></script>
		<style type="text/css">
		.img1 {
			cursor: pointer;
		}
		.redline{
		color:red;
		}
		.colors{
			border-bottom:1px dashed #FF0000;
			padding-top: 10px;
			padding-bottom: 10px;
		}
		</style>

		<script type="text/JavaScript">
$(function(){
	var url = "<%=basePath%>ea/oasafeKind/sajax_ea_getSafeKindsByPID.jspa?parentID=001&data="+new Date();
	var context = "";
	$.ajax({
		url: encodeURI(url),
        type: "get",
        async: true,
        dataType: "json",
        success: function cbf(data){
		    var member = eval("("+data+")");
		    var nologin = member.nologin;
	        if(nologin){
	        	document.location.href =basePath+"page/ea/not_login.jsp";
	        }
		    var safeKindList = member.safeKindList;
		    if(null == safeKindList&& safeKindList.length == 0)
		    	return;
		    var index="";
		    var select = 1;
		    var index2 = 0;
		    $d=$("table#typeList tr#typeId","tr#headTypeId");
		    for(var i=0;i<safeKindList.length;i++){
		    	
		    	context += "<td width='40' align='center' valign='top' style='background-image:url(<%=basePath%>images/he02.gif); padding-top:11px; cursor:pointer' onclick=\"document.location.href='<%=basePath%>/safeinspect/ea_toSearch.jspa?search=search&entityVO.typeName=";
									+ safeKindList[i].name + "'\">";
							context += "<table width='90%' border='0' cellspacing='0' cellpadding='0'>__tag_95$81_<td height='85px' align='center' valign='top'>__tag_95$131_"
									+ safeKindList[i].name
									+ "__tag_95$194_</td>__tag_95$205_";
							if (i < 10)
								index = "0" + (i + 1);
							else
								index = "" + (i + 1);
							context += "__tag_98$19_<td height='24' align='center'>OM"
									+ index
									+ "__tag_98$65_</tr>__tag_98$75_</td>";
							//safeKindList[i].id
							if ((i + 1) % 20 == 0) {
								$d.append(context);
								context = '';
								$("tr#headTypeId").after(
										$("tr#clone").clone(true).attr("id",
												"headTypeId" + select));
								$d = $("table#typeList tr#typeId",
										"tr#headTypeId" + select);
								select++;
							}
							if (parseInt(index) == 21) {
								$("tr#headTypeId" + (select - 1)).show();
							}
						}
						$d.append(context);
					},
					error : function cbf(data) {
						alert("数据获取失败！");
					}
					});
</script>
	</head>
	<body>
		<table >
			<tr>
				<td><div class="na_back_img_ks"></div><div class="center_a"><strong>安全管理</strong></div></td>
				 <td><div class="na_back_img_jt_hx"></div></td>
				<td>
					<div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/keyManage/ea_getKeyManageList.jspa'">
						</div><div class="center_a">
						钥匙管理
					</div>
				</td>
				<td>
					<div class="na_back_img" onclick="document.location.href='<%=basePath%>page/ea/main/office_ea/safe/safekinds_manager.jsp'">
						</div><div class="center_a">
						安全类别
					</div>
				</td>
				<td>
					<div class="na_back_img"
						onclick="document.location.href='<%=basePath%>ea/safeinspect/ea_getSafeInspectList.jspa'">
						</div><div class="center_a">
						安全单据
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="9" class="colors" ></td>
			</tr>
			<tr>
				<td rowspan="3">
					<div class="na_back_img_ks"></div><div class="center_a"><strong>安全防范管理</strong>
					</div>
				</td>
					 <td><div class="na_back_img_jt_xs"></div></td>
				<td>
					<div class="na_back_img"></div><div class="center_a">
						火灾预防
					</div>
				</td>
				<td>
					<div class="na_back_img"></div><div class="center_a">
						防盗管理
					</div>
				</td>
				<td>
					<div class="na_back_img"></div><div class="center_a">
						防霉管理
					</div>
				</td>
				<td>
					<div class="na_back_img"></div><div class="center_a">
						防毒管理
					</div>
				</td>
				<td>
					<div class="na_back_img"></div><div class="center_a">
						污染预防
					</div>
				</td>
				<td>
					<div class="na_back_img"></div><div class="center_a">
						雪灾预防
					</div>
				</td>
				<td>
					<div class="na_back_img"></div><div class="center_a">
						冰雹预防
					</div>
				</td>
			</tr>
			<tr>
				 <td><div class="na_back_img_jt_hx"></div></td>
				<td>
					<div class="na_back_img"></div><div class="center_a">
						冻害预防
					</div>
				</td>
				<td>
					<div class="na_back_img"></div><div class="center_a">
						垮塌预防
					</div>
				</td>
				<td>
					<div class="na_back_img"></div><div class="center_a">
						地震预防
					</div>
				</td>
				<td>
					<div class="na_back_img"></div><div class="center_a">
						洪涝预防
					</div>
				</td>
				<td>
					<div class="na_back_img"></div><div class="center_a">
						防泥石流
					</div>
				</td>
				<td>
					<div class="na_back_img"></div><div class="center_a">
						虫害预防
					</div>
				</td>
				<td>
					<div class="na_back_img"></div><div class="center_a">
						疾病预防
					</div>
				</td>
			</tr>
			<tr>
				<td><div class="na_back_img_jt_xx"></div></td>
				<td>
					<div class="na_back_img"></div><div class="center_a">
						安全用电
					</div>
				</td>
				<td>
					<div class="na_back_img"></div><div class="center_a">
						雷雨预防
					</div>
				</td>
				<td>
					<div class="na_back_img"></div><div class="center_a">
						防龙卷风
					</div>
				</td>
				<td>
					<div class="na_back_img"></div><div class="center_a">
						食品安全
					</div>
				</td>
				<td>
					<div class="na_back_img"></div><div class="center_a">
						车辆设备
					</div>
				</td>
				<td>
					<div class="na_back_img"></div>
				</td>
				<td>
					<div class="na_back_img"></div>
				</td>
			</tr>
		</table>
	</body>
</html>
