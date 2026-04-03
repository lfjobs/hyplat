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
<title> 办公室生产管理</title>
<link href="<%=basePath %>css/navigation_a.css" rel="stylesheet" type="text/css" />	
<script type="text/javascript" src="<%=basePath %>/js/jquery.js"></script>
		<script type="text/javascript" src="<%=basePath %>/js/dropdown/extendPageMenu.js"></script>
</head>
<body>
    <div>
      <table >
        <tr>
          <td><table>
          
                    <tr>
                      <td><div class="na_back_img_ks"></div><div class="center_a"><strong> 公司办公室管理</strong></div></td>
                      <td><div class="na_back_img_jt_hx"></div></td>
                      <td><table>
                          <tr>
                             <td><div class="na_back_img"  onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/strategic_planning_s.jsp'"></div><div class="center_a">企业战略规划管理</div>
                              </td>
                              <td><div class="na_back_img"  onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/work_bills_s.jsp'"></div><div class="center_a">企业行政管理</div>
                             </td>
                             <td><div class="na_back_img"  onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/danwei_manager.jsp'"></div><div class="center_a">企业单位管理</div>
                             </td>
                             <td ><div class="na_back_img"  onclick="window.parent.document.getElementById('rightFrame').src='<%=basePath%>/page/ea/main/navigation/information_management_o.jsp'"></div><div class="center_a">信息管理</div>
                             </td>
                              <td><div class="na_back_img"  onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/logistics_management_sxcom.jsp'"></div><div class="center_a">后勤管理</div>
                             </td>
                             <td><div class="na_back_img"  onclick="window.parent.document.getElementById('rightFrame').src='<%=basePath%>/page/ea/main/navigation/office_dccom.jsp'"></div><div class="center_a">督查(审核)管理</div>
                             </td>
                          </tr>
                      </table></td>
                    </tr>
                </table></td>
              </tr>
            <tr>
                <td><table >
                    <tr>
                      <td><div class="na_back_img_ks"></div><div class="center_a"><strong> 集团办公室管理</strong></div></td>
                          <td><div class="na_back_img_jt_hx"></div></td>
                      <td><table>
                          <tr>
                             
                             <td><div class="na_back_img" onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/strategic_planning_ss.jsp'">
                             </div><div class="center_a">集团规划管理</div>
                             </td>
                             <td><div class="na_back_img" onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/work_bills_ss.jsp'"></div><div class="center_a">集团行政管理</div>
                             </td>
                             <td><div class="na_back_img"  onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/danwei_manager.jsp'"></div><div class="center_a">集团单位管理</div>
                             </td>
                             <td><div class="na_back_img" onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/information_management_og.jsp'"></div><div class="center_a">集团信息管理</div>
                             </td>
                             <td><div class="na_back_img" onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/logistics_management_sxg.jsp'"></div><div class="center_a">集团后勤管理</div>
                             </td>
                             <td><div class="na_back_img"  onclick="window.parent.document.getElementById('rightFrame').src='<%=basePath%>/page/ea/main/navigation/office_dccom.jsp'"></div><div class="center_a">集团督查(审核)管理</div>
                             </td>
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