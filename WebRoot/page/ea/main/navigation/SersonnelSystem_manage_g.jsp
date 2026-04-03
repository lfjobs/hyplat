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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title> 售前、售中、售后服务管理</title>
		<link href="<%=basePath %>css/navegate.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div>
  <table border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td width="98"><table>
        <tr>
          <td width="98" rowspan="2" align="center"><img src="<%=basePath%>images/04.gif" width="50"
											height="50" border="0" /> <br />
            售前、售中、售后服务管理 </td>
          <td width="80" height="62" align="center"><table>
            <tr>
              <td><img src="<%=basePath%>images/jiatou_01.gif"
											width="56" height="51" border="0" /> </td>
            </tr>
            <tr>
              <td><img src="<%=basePath%>images/jiatou_02.gif" width="56"
										height="51" border="0" /></td>
            </tr>
          </table></td>
        </tr>
      </table></td>
      <td><table>
        <tr>
          <td><table height="90" border="0" align="left" cellpadding="0"
							cellspacing="0" class="table03" style="margin-top: 5px">
            <tr>
              <td rowspan="2" align="center"><table border="0" cellspacing="0" cellpadding="0"
										height="132px" >
                <tr>
                  <td width="100px" align="center" onClick="document.location.href='<%=basePath%>/ea/marketsurvey/ea_getListMarketSurvey.jspa'"><img src="<%=basePath%>images/04.gif" width="50"
											height="50" border="0" /> <br />
                            <span style="font-weight: normal; font-size:12px;">市场调查</span></td>
                  <td width="100px"  align="center"><img src="<%=basePath%>images/04.gif" width="50"
											height="50" border="0" /> <br />
                            <span style="font-weight: normal; font-size:12px;">地域调查</span></td>
                  <td  width="100px" align="center"><img src="<%=basePath%>images/04.gif" width="50"
											height="50" border="0" /> <br />
                            <span style="font-weight: normal; font-size:12px;">价格调查</span></td>
                  <td width="100px" align="center" onClick="document.location.href='<%=basePath%>/ea/cstaff/ea_getListCStaffByCompanyID.jspa'"><img src="<%=basePath%>images/04.gif" width="50"
											height="50" border="0" /> <br />
                            <span style="font-weight: normal; font-size:12px;">社会人力资源</span></td>
                  <td width="100px" align="center"onClick="document.location.href='<%=basePath%>/ea/contactcompany/ea_getListContactCompany.jspa'"><img src="<%=basePath%>images/04.gif" width="50"
											height="50" border="0" /> <br />
                            <span style="font-weight: normal; font-size:12px;">社会单位管理</span></td>
                  <td width="100px"  align="center" onClick="document.location.href='<%=basePath%>/ea/productdesign/ea_getListProductdesign.jspa?'"><img src="<%=basePath%>images/04.gif" width="50"
											height="50" border="0" /> <br />
                            <span style="font-weight: normal; font-size:12px;">产品设计 </span></td>
                  <td width="100px"  align="center"><img src="<%=basePath%>images/04.gif" width="50"
											height="50" border="0" /> <br />
                            <span style="font-weight: normal; font-size:12px;">产品定位</span></td>
                  <td  width="100px" align="center"><img src="<%=basePath%>images/04.gif" width="50"
											height="50" border="0" /> <br />
                            <span style="font-weight: normal; font-size:12px;">产品价格</span></td>
                  <td  width="100px" align="center" onClick="document.location.href='<%=basePath%>/ea/stafftrack/ea_getStaffList.jspa'"><img src="<%=basePath%>images/04.gif" width="50"
											height="50" border="0" /> <br />
                            <span style="font-weight: normal; font-size:12px;">个人服务</span></td>
                  <td width="100px"  align="center" onClick="document.location.href='<%=basePath%>/ea/companytrack/ea_getCompanyList.jspa'"><img src="<%=basePath%>images/04.gif" width="50"
											height="50" border="0" /> <br />
                            <span style="font-weight: normal; font-size:12px;">单位服务</span></td>
                  <td  width="100px" align="center" onClick="document.location.href='<%=basePath%>/ea/custdata/ea_getCustomerDataList.jspa'"><img src="<%=basePath%>images/04.gif" width="50"
											height="50" border="0" /> <br />
                            <span style="font-weight: normal; font-size:12px;">服务单位客户</span></td>
                  <td width="100px"  align="center" onClick="document.location.href='<%=basePath%>/ea/comregist/ea_getCompanyRegistList.jspa'"><img src="<%=basePath%>images/04.gif" width="50"
											height="50" border="0" /> <br />
                            <span style="font-weight: normal; font-size:12px;">企业注册</span></td>
                  <td width="100px"  align="center" onClick="document.location.href='<%=basePath%>/page/ea/main/navigation/SersonnelSystem_a_network.jsp'"><img src="<%=basePath%>images/04.gif" width="50"
											height="50" border="0" /> <br />
                            <span style="font-weight: normal; font-size:12px;">网络推广</span></td>
                  <td width="100px"  align="center"><img src="<%=basePath%>images/04.gif" width="50"
											height="50" border="0" /> <br />
                            <span style="font-weight: normal; font-size:12px;">户外广告</span></td>
                  <td width="100px"  align="center" onClick="document.location.href='<%=basePath%>/page/ea/main/navigation/SersonnelSystem_a_yuanchuan.jsp'"><img src="<%=basePath%>images/04.gif" width="50"
											height="50" border="0" /> <br />
                            <span style="font-weight: normal; font-size:12px;">媒体宣传</span></td>
                  <td width="100px"  align="center"><img src="<%=basePath%>images/04.gif" width="50"
											height="50" border="0" /> <br />
                            <span style="font-weight: normal; font-size:12px;">会议宣传</span></td>
                  <td width="100px"  align="center"><img src="<%=basePath%>images/04.gif" width="50"
											height="50" border="0" /> <br />
                            <span style="font-weight: normal; font-size:12px;">成交客户</span></td>
                  <td width="100px"  align="center" onClick="document.location.href='http://192.168.100.188:8080/hyplat/page/ea/ccompany/scheduledproduct/scheduledproduct_main.jsp'"><img src="<%=basePath%>images/04.gif" width="50"
											height="50" border="0" /> <br />
                            <span style="font-weight: normal; font-size:12px;">预定产品</span></td>
                  <td width="100px"  align="center" onClick="document.location.href='http://192.168.100.188:8080/hyplat//ea/transactionservice/ea_getListTransactionService.jspa?'"><img src="<%=basePath%>images/04.gif" width="50"
											height="50" border="0" /> <br />
                            <span style="font-weight: normal; font-size:12px;">产品成交</span></td>
                  <td width="100px"  align="center" onClick="document.location.href='http://192.168.100.188:8080/hyplat//ea/advisingclients/ea_getListAdvisingClients.jspa?'"><img src="<%=basePath%>images/04.gif" width="50"
											height="50" border="0" /> <br />
                            <span style="font-weight: normal; font-size:12px;">指导客户</span></td>
                  <td width="100px"  align="center" onClick="document.location.href='http://192.168.100.188:8080/hyplat/ea/clientTracking/ea_getClientTrackingList.jspa'"><img src="<%=basePath%>images/04.gif" width="50"
											height="50" border="0" /> <br />
                            <span style="font-weight: normal; font-size:12px;">跟踪服务</span></td>
                  <td width="100px"  align="center" onClick="document.location.href='http://192.168.100.188:8080/hyplat/ea/clientPblm/ea_getClientPblmList.jspa'"><img src="<%=basePath%>images/04.gif" width="50"
											height="50" border="0" /> <br />
                            <span style="font-weight: normal; font-size:12px;">问题解决</span></td>
                  <td width="100px"  align="center" onClick="document.location.href='http://192.168.100.188:8080/hyplat/ea/clientIncrement/ea_getClientIncrementList.jspa'"><img src="<%=basePath%>images/04.gif" width="50"
											height="50" border="0" /> <br />
                            <span style="font-weight: normal; font-size:12px;">增值服务</span></td>
                  <td width="100px"  align="center"><img src="<%=basePath%>images/04.gif" width="50"
											height="50" border="0" /> <br />
                            <span style="font-weight: normal; font-size:12px;">成交增值</span></td>
                  <td width="100px"  align="center" onclick="document.location.href='<%=basePath%>ea/documentcommon/ea_showDocumentModule.jspa?module=complaint'"><img src="<%=basePath%>images/04.gif" width="50"
											height="50" border="0" /> <br />
                            <span style="font-weight: normal; font-size:12px;">投拆处理</span></td>
                  <td width="100px"  align="center" onclick="document.location.href='<%=basePath%>ea/documentcommon/ea_showDocumentModule.jspa?module=InterDis'"><img src="<%=basePath%>images/04.gif" width="50"
											height="50" border="0" /> <br />
                            <span style="font-weight: normal; font-size:12px;">内部纠纷</span></td>
                  <td width="100px"  align="center" onclick="document.location.href='<%=basePath%>ea/documentcommon/ea_showDocumentModule.jspa?module=ExterDis'"><img src="<%=basePath%>images/04.gif" width="50"
											height="50" border="0" /> <br />
                            <span style="font-weight: normal; font-size:12px;">外部纠纷</span></td>
                  <td width="100px"  align="center" onclick="document.location.href='<%=basePath%>ea/extralflow/ea_showExtralDocModule.jspa'"><img src="<%=basePath%>images/04.gif" width="50"
											height="50" border="0" /> <br />
                            <span style="font-weight: normal; font-size:12px;">网站投诉</span></td>
                </tr>
              </table></td>
            </tr>
          </table></td>
        </tr>
      </table></td>
    </tr>
  </table>
</div>
</body>
</html>
