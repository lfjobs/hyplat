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
<title>创收平台生产管理</title>
	<link href="<%=basePath %>css/navigation_a.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="<%=basePath %>/js/jquery.js"></script>
		<script type="text/javascript" src="<%=basePath %>/js/dropdown/extendPageMenu.js"></script>
		
</head>
<body >
<div>
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><table>
          <tr>
							<td><table>
									<tr>
										<td>
											<div class="na_back_img_ks"></div>
											<div class="center_a">
												<strong>生产合同管理</strong>
											</div>
										</td>
										<td><div class="na_back_img_jt_hx"></div>
										</td>
										<td><table>
												<tr>
													<td width="140">
														<div class="na_back_img"></div>
														<div class="center_a">
															<span>生产合同流转</span>
														</div>
													</td>
													<td width="140">
														<div class="na_back_img"></div>
														<div class="center_a">
															<span>生产合同查询</span>
														</div>
													</td>
												
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td>
						</tr>
            <tr height="80">
                <td><table width="100%" border="0" align="left" cellpadding="0"
							cellspacing="0" class="table03">
                    <tr>
                      <td width="120" align="center" class="colors">
                      	<div class="na_back_img_ks"></div>
                      	<div class="center_a"><strong>公司创收平台管理</strong></div>
                     </td>
                      <td width="55" align="center" class="colors"><div class="na_back_img_jt_hx"></div></td>
                       <td><table border="0" cellspacing="0" cellpadding="0">
                          <tr>
                             <td width="110" align="center" class="colors">
                             	<div class="na_back_img" onclick="document.location.href='<%=basePath%>/page/ea/main/navigation/teachingAffairsDepartment-a.jsp'"></div>
                      			<div class="center_a"><span>教务(生产)一项</span></div>
							 </td>
                             <td width="110" align="center" class="colors">
                             	<div class="na_back_img"></div>
                      			<div class="center_a"><span>教务(生产)二项</span></div>
                      		 </td>
                             <td width="110" align="center" class="colors">
                             	<div class="na_back_img"></div>
                      			<div class="center_a"><span>教务(生产)三项</span></div>
							 </td>
                             <td width="110" align="center" class="colors"> 
                             	<div class="na_back_img"></div>
                      			<div class="center_a"><span>教务(生产)四项</span></div>
							 </td>
                             <td width="110" align="center" class="colors">
                             	<div class="na_back_img"></div>
                      			<div class="center_a"><span>教务(生产)五项</span></div>
							 </td>
                          </tr>
                      </table></td>
                    </tr>
              </table></td>
            </tr>
              <tr>
                <td><table width="100%" border="0" align="left" cellpadding="0"
							cellspacing="0" class="table03">
                    <tr>
                      <td width="120" align="center">
                      	<div class="na_back_img_ks"></div>
                      	<div class="center_a"><strong>集团创收平台管理</strong></div>
                      </td>
                      <td width="55" align="center"><div class="na_back_img_jt_hx"></div></td>
                      <td><table border="0" cellspacing="0" cellpadding="0">
                          <tr>
                             <td width="110" align="center">
                             	<div class="na_back_img"></div>
                      			<div class="center_a"><span>教务(生产)一项</span></div>
							 </td>
                             <td width="110" align="center"> 
                             	<div class="na_back_img"></div>
                      			<div class="center_a"><span>教务(生产)二项</span></div>
							 </td>
                             <td width="110" align="center">
                             	<div class="na_back_img"></div>
                      			<div class="center_a"><span>教务(生产)三项</span></div>
							 </td>
                             <td width="110" align="center"> 
                             	<div class="na_back_img"></div>
                      			<div class="center_a"><span>教务(生产)四项</span></div>
							 </td>
                             <td width="110" align="center">
                             	<div class="na_back_img"></div>
                      			<div class="center_a"><span>教务(生产)五项</span></div>
							 </td>
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