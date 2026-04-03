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
<title>查看考场记录</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/office_ea/carmanage/viewExaminationRecords.css" />
</head>
<body>
	<div class="collect">
		<div class="stair">
            <div class="three-level-one">
                <div class="pyatyi-one">
                    练车记录
                </div>
            </div>
			<div class="second">
                <div class="three-level-three" style="height: 280px;border: none;">
                    <table>
                        <tr>
                            <td>编号 :</td>
                            <td>${object[1]}</td>
                        </tr>
                        <tr>
                            <td>用户名称 :</td>
                            <td>${object[2]}</td>
                        </tr>
                        <tr>
                            <td>微分金账号 :</td>
                            <td>${object[3]}</td>
                        </tr>
                        <tr>
                            <td>签到时间 :</td>
                            <td>
                                <c:choose>
                                    <c:when test="${empty object[4]}">
                                        - - - -
                                    </c:when>
                                    <c:otherwise>
                                        ${object[4]}
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                        <tr>
                            <td>签退时间 :</td>
                            <td>
                                <c:choose>
                                    <c:when test="${empty object[5]}">
                                        - - - -
                                    </c:when>
                                    <c:otherwise>
                                        ${object[5]}
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                        <tr>
                            <td>时长 :</td>
                            <td>
                                <c:choose>
                                    <c:when test="${empty object[6]}">
                                        - - - -
                                    </c:when>
                                    <c:otherwise>
                                        ${object[6]}
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                        <tr>
                            <td>金额(元) :</td>
                            <td>
                                <c:choose>
                                    <c:when test="${empty object[7]}">
                                        - - - -
                                    </c:when>
                                    <c:otherwise>
                                        ${object[7]}
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                        <tr>
                            <td>考场编号 :</td>
                            <td>${object[8]}</td>
                        </tr>
                        <tr>
                            <td>考场名称 :</td>
                            <td>${object[9]}</td>
                        </tr>
                        <tr>
                            <td>主管名称 :</td>
                            <td>${object[10]}</td>
                        </tr>
                        <tr>
                            <td>教练名称 :</td>
                            <td>${object[11]}</td>
                        </tr>
                        <tr>
                            <td>监管人微分金账号:</td>
                            <td>${object[12]}</td>
                        </tr>
                        <tr>
                            <td>监管人:</td>
                            <td>${object[13]}</td>
                        </tr>
                        <tr>
                            <td>状态 :</td>
                            <td>
                                <c:choose>
                                    <c:when test="${object[14] eq '00'}">
                                        未签到
                                    </c:when>
                                    <c:when test="${object[14] eq '01'}">
                                        已签到
                                    </c:when>
                                    <c:when test="${object[14] eq '02'}">
                                        已签退
                                    </c:when>
                                    <c:when test="${object[14] eq '03'}">
                                        签退失败
                                    </c:when>
                                </c:choose>
                            </td>
                        </tr>
                    </table>
                </div>
			</div>
		</div>
	</div>
</body>
</html>