<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>单位规则-修改</title>
    <meta charset="utf-8"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/ea/human/specsConfig.css">
  <script type="text/javascript" charset="utf-8" src="<%=basePath%>js/ea/human/menu/menu.js"></script>
    <script src="<%=basePath %>js/scMobile/payBudget/addBudget/jquery-1.9.1.min.js" type="text/javascript"
            charset="utf-8"></script>

</head>
<body>
<header>
    <ul class="clearfix">
        <li>
            <a onclick="window.history.go(-1);return false;" target="_self">
                <img src="<%=basePath%>images/ea/office/contract/selectp/return.png">
            </a>
        </li>
        <li>
            规格配置
        </li>
        <li>
        </li>
    </ul>
</header>
<div class="content">

    <s:form id="updateSpecsConfig" action="ea_updateSpecsConfig1.jspa" namespace="/ea/specs" method="POST">
        <form>
            <div>
                <div class="div-name">
                    <label for="">规格单位</label>
                    <input type="text" placeholder="请填写规格单位" id="specsType" name="specsConfigAddDTO.specsType"
                           value="${specsConfigAddDTO.specsType}"/>
                    <input type="hidden" id="id" name="specsConfigAddDTO.id"
                           value="${specsConfigAddDTO.id}"/>
                    <input type="hidden" id="specsCode" name="specsConfigAddDTO.specsCode"
                                                                    value="${specsConfigAddDTO.specsCode}"/>
                </div>
                <div class="div-name">
                    <span>规格类型</span>
                    <select name="specs" id="sel-type">
                        <option value="selOne" class="sel-one">A</option>
                        <option value="selTwo" class="sel-two">AxB</option>
                        <option value="selThree" class="sel-three">AxBxC</option>
                        <option value="selFour" selected class="sel-four">AxBxCxD</option>
                    </select>
                </div>
            </div>
            <div class="specs-type">
                <div class="div-name">
                    <label for="">规格单位</label>
                    <div id="div-a">
                        <span>A：</span>
                        <input type="text" placeholder="请填写规格单位" class="sel-a" name="specsConfigAddDTO.specsA"
                               id="specsA" value="${specsConfigAddDTO.specsA}">
                    </div>
                    <div id="div-b">
                        <span>B：</span>
                        <input type="text" placeholder="请填写规格单位" class="sel-b" name="specsConfigAddDTO.specsB"
                               value="${specsConfigAddDTO.specsB}">
                    </div>
                    <div id="div-c">
                        <span>C：</span>
                        <input type="text" placeholder="请填写规格单位" class="sel-c" name="specsConfigAddDTO.specsC"
                               value="${specsConfigAddDTO.specsC}">
                    </div>
                    <div id="div-d">
                        <span>D：</span>
                        <input type="text" placeholder="请填写规格单位" class="sel-d" name="specsConfigAddDTO.specsD"
                               value="${specsConfigAddDTO.specsD}">
                    </div>
                </div>
                <div class="inputBut">
                    <input type=submit value="确认修改">
                    <input type="reset" value="重置"></div>
            </div>
        </form>

    </s:form>

</div>
<script type="text/javascript">
    var basePath = "<%=basePath%>";
    const type = "${param.type}";
    const empowerId = "${param.empowerId}";
    const companyId = "${param.companyId}";
    const one = document.querySelector("#div-a");
    const two = document.querySelector("#div-b");
    const three = document.querySelector('#div-c');
    const four = document.querySelector('#div-d');
    document.getElementById('sel-type').addEventListener('change', selSpace);

    function selSpace() {
        var selectedValue = this.value;
        // 选中A
        if ('selOne' === selectedValue) {
            one.style.display = 'block'
            two.style.display = 'none'
            three.style.display = 'none'
            four.style.display = 'none'
        } else if ('selTwo' === selectedValue) {
            one.style.display = 'block'
            two.style.display = 'block'
            three.style.display = 'none'
            four.style.display = 'none'
        } else if ('selThree' === selectedValue) {
            one.style.display = 'block'
            two.style.display = 'block'
            three.style.display = 'block'
            four.style.display = 'none'
        } else if ('selFour' === selectedValue) {
            one.style.display = 'block'
            two.style.display = 'block'
            three.style.display = 'block'
            four.style.display = 'block'
        }
    }

</script>

</body>
</html>
