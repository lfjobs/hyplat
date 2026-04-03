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
<title>教务生产管理</title>
    <link href="<%=basePath %>css/navigation_a.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=basePath %>/js/jquery.js"></script>
		<script type="text/javascript" src="<%=basePath %>/js/dropdown/extendPageMenu.js"></script>
<style type="text/css">
	td{ 
	padding: 5px;	
	}

</style>
</head>
<body>
    <div>
      <table border="0" >
        <tr>
          <td><table>
          	 <%-- <tr>
                <td>
                  <table>
                    <tr>
                      <td align="center">
                        <div class="na_back_img_ks"></div>
                        <div class="center_a"><strong>公司检测管理</strong></div>
                    </td>
                      <td><div class="na_back_img_jt_hx"></div></td>
                       <td><table border="0" cellspacing="0" cellpadding="0">
                          <tr>
                             <td>
                              <div class="na_back_img" onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/CarGascylindertestOS.jsp'"></div>
                              <div class="center_a">车辆CNG气瓶检测办公系统</div>
                            </td>
                          </tr>
                      </table></td>
                    </tr>
              </table></td>
            </tr>
            <tr>
                <td>
                  <table>
                    <tr>
                      <td align="center">
                        <div class="na_back_img_ks"></div>
                        <div class="center_a"><strong>公司教务管理</strong></div>
                    </td>
                      <td><div class="na_back_img_jt_hx"></div></td>
                       <td><table border="0" cellspacing="0" cellpadding="0">
                          <tr>
                             <td>
                              <div class="na_back_img" onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/teachingAffairsDepartment-a.jsp'"></div>
                              <div class="center_a">教务(生产)一项</div>
                            </td>
                             <td>
                              <div class="na_back_img"></div>
                              <div class="center_a">教务(生产)二项</div>
                            </td>
                             <td>
                              <div class="na_back_img" ></div>
                              <div class="center_a">教务(生产)三项</div>
                              </td>
                             <td>
                              <div class="na_back_img" ></div>
                              <div class="center_a">教务(生产)四项</div>
                            </td>
                             <td>
                              <div class="na_back_img" ></div>
                              <div class="center_a">教务(生产)五项</div>
                           </td>
                          </tr>
                      </table></td>
                    </tr>
              </table></td>
            </tr>
              <tr>
                <td><table>
                    <tr>
                      <td align="center">
                        <div class="na_back_img_ks"></div>
          <div class="center_a"><strong> 集团教务管理</strong></div>
                       
                      </td>
                      <td ><div class="na_back_img_jt_hx"></div></td>
                      <td><table border="0" cellspacing="0" cellpadding="0">
                          <tr>
                             <td>
                              <div class="na_back_img" onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/teachingAffairsDepartment-a.jsp?companyGroupLogo=companyGroupLogo'"></div>
                              <div class="center_a">集团教务(生产)一项</div>
                            </td>
                             <td>
                              <div class="na_back_img" onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/teachingAffairsDepartment-d.jsp'"></div>
                              <div class="center_a">集团教务(生产)二项</div>
                            </td>
                             <td>
                              <div class="na_back_img" ></div>
                              <div class="center_a">集团教务(生产)三项</div>
                            </td>
                             <td> 
                              <div class="na_back_img" ></div>
                              <div class="center_a">集团教务(生产)四项</div>
                            </td>
                             <td>
                              <div class="na_back_img" ></div>
                              <div class="center_a">集团教务(生产)五项</div>
                            </td>
                          </tr>
                      </table></td>
                    </tr>
                </table></td>
              </tr> --%>
              
              <tr>
                <td><table >
                    <tr>
                      <td><div class="na_back_img_ks"></div><div class="center_a"><strong> 绵阳水利项目管理</strong></div></td>
                          <td><div class="na_back_img_jt_hx"></div></td>
                      <td><table>
                          <tr>
                             <td><div class="na_back_img" onclick="document.location.href='<%=basePath%>/ea/projectmanager/ea_getDtMyprojectList.jspa'">
                             </div><div class="center_a">项目管理</div>
                             </td>
                             <td><div class="na_back_img"
					onclick="document.location.href='<%=basePath%>/page/mysl/CheckManage.jsp'">
				</div>
				<div class="center_a">审核管理</div></td>
				<td><div class="na_back_img"
					onclick="document.location.href='<%=basePath%>/ea/checkmanage/ea_getMypassrecordList.jspa'">
				</div>
				<div class="center_a">传阅管理</div></td>
                          </tr>
                      </table></td>
                    </tr>
                </table></td>
              </tr>
              
          </table>
          </td>
        </tr>
      </table>
    </div>
</body>
</html>