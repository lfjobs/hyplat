<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN""http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="cache-control" content="no-cache"/>
    <title>接口</title>
    <script src="<%=basePath%>js/ea/portmanage/js/jquery.js" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/ea/portmanage/css/base.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/ea/portmanage/css/port.css">
    <script type="text/javascript">
        var basePath = '<%=basePath%>';
        var portId = '${portId}';
        var documentsId = '${documentsId}';
    </script>
</head>
<body>
<form action="" method="post" id="submitForm">
    <div class="clearfix">
        <p>
            接口详情
        </p>
        <input type="button" name="" id="" value="保存接口" onclick="save()"/>
    </div>
    <section class="clearfix">
        <div class="clearfix btn_1">
            <select id="portRequestType">
                <option value="POST">POST</option>
                <option value="GET">GET</option>
                <option value="PUT">PUT</option>
                <option value="DELETE">DELETE</option>
            </select>
        </div>
        <div class="clearfix btn_1">
            <select  id="dataFormat">
                <option value="JSON">JSON</option>
                <option value="XML">XML</option>
                <option value="HTML">HTML</option>
                <option value="text">text</option>
            </select>
            <input type="hidden" id="portId"/>
        </div>
        <input type="text" name="" id="portUrl" placeholder="接口地址"/>
    </section>
    <input type="text" name="" id="portName" placeholder="接口名称"/>
    <textarea name="" rows="" cols="" placeholder="接口描述" id="portDescription"></textarea>
    <p>请求参数</p>
    <section id="sec_fir">
        <menu class="clearfix hed">
            <li>参数名称</li>
            <li>参数类型</li>
            <li>参数说明</li>
            <li>必填</li>
            <li>示例</li>
            <li>删除</li>
        </menu>
        <!--模板-->
        <menu class="clearfix copy_menu none">
            <li>
                <input type="hidden" name="parameterId" value="" class="parameterId"/>
                <input type="text" name="parameterName" value=""/>
            </li>
            <li>
                <div class="clearfix btn_1">
                    <select name="parameterType">
                        <option value="string">string</option>
                        <option value="double">double</option>
                        <option value="int">int</option>
                        <option value="boolean">boolean</option>
                        <option value="byte">byte</option>
                        <option value="short">short</option>
                        <option value="long">long</option>
                        <option value="float">float</option>
                        <option value="date">date</option>
                        <option value="datetime">datetime</option>
                        <option value="object">object</option>
                        <option value="File">File</option>
                    </select>
                </div>
            </li>
            <li>
                <input type="text" name="parameterExplain" value=""/>
            </li>
            <li>
                <input type="checkbox" name="required" value="1"/>
                <input type="hidden" name="required" value="0"/>
            </li>
            <li>
                <input type="text" name="example" value=""/>
            </li>
            <li>
                <input type="text" name="" value="" readonly="readonly"/>
            </li>
            <li class="del none">
                <img src="<%=basePath%>js/ea/portmanage/images/pic_09.png "/>
            </li>
        </menu>

    </section>
    <p>
        <img src="<%=basePath%>/js/ea/portmanage/images/pic_07.png"/>
        <span id="add_menu">添加属性</span>
    </p>
    <section>
        <div class="clearfix" id="tab_a">
            <p class="p_active">
                返回示例
            </p>
            <p>
                异常示例
            </p>
        </div>
        <div id="tab_b">
            <textarea name="" rows="" cols="" id="successParam"></textarea>
            <textarea class="none" name="" rows="" cols="" id="failParam"></textarea>
        </div>
    </section>
</form>
</body>
<script type="text/javascript">

    $(".btn_1").click(function (evevt) {
        event.stopPropagation();
        $(this).children("menu").toggle();
    })

    $("#sec_fir menu").hover(function () {
        $(this).children(".del").toggleClass("none");
    })
    $(".del").click(function () {
        var parameterId=$(this).parents(".copy_menu").find(".parameterId").val();
            if(confirm("您确定要删除该参数吗？")){
                $(this).parents(".copy_menu").remove();
                if(parameterId !=null && parameterId != ''){
                    $.ajax({
                        type: "POST",
                        url: basePath + "ea/ccode1/sajax_pm_deletePortParameterById.jspa",
                        data: {
                            "parameterId": parameterId
                        },
                        success: function (data) {
                            var member = eval("(" + data + ")");
                            var boolean = member.boolean;
                            //删除成功
                            if (boolean == true) {
                                alert(member.code);
                            } else {
                                //删除失败
                                alert(member.code)
                            }

                        },
                        error: function (data) {
                            alert("数据获取失败！");
                        },
                        dateType: "text"
                    });

                }
            }

    })
    $("#add_menu").mouseover(function () {
        $(this).css({
            "color": "blue"
        });
    }).mouseout(function () {
        $(this).css({
            "color": ""
        });
    });
    $("#add_menu").click(function () {
        $("#sec_fir").append($("#sec_fir menu:nth-of-type(2)").clone(true).removeClass("none"))
    })
    $("#tab_a p").click(function () {
        $(this).parent().children("p").removeClass("p_active")
        $(this).addClass("p_active");
        $("#tab_b textarea").addClass("none");
        $("#tab_b textarea").eq($(this).index()).removeClass("none")
    })
</script>
<script>
    function save() {
        //非空校验
        var textcount=$("input[name='parameterName']").length;
        if(textcount==1){
            alert("参数不能为空,请您输入！");
            return;
        }
        /*if ($("#portRequestType").val() == ''||$("#portRequestType").val().replace(/\s*!/g, "") == "") {
         alert("参数不能为空,请您输入！");
         return;
         }
         if ($("#dataFormat").val() == ''||$("#portRequestType").val().replace(/\s*!/g, "") == "") {
         alert("参数不能为空,请您输入！");
         return;
         }*/
        if ($("#portUrl").val() == ''||$("#portUrl").val().replace(/\s*/g, "") == "") {
            alert("接口地址不能为空,请您输入！");
            return;
        }
        if ($("#portName").val() == ''||$("#portName").val().replace(/\s*/g, "") == "") {
            alert("接口名称不能为空,请您输入！");
            return;
        }
        if ($("#portDescription").val() == ''||$("#portDescription").val().replace(/\s*/g, "") == "") {
            alert("接口描述不能为空,请您输入！");
            return;
        }
        if ($("#successParam").val() == ''||$("#successParam").val().replace(/\s*/g, "") == "") {
            alert("返回示例不能为空,请您输入！");
            return;
        }
        if ($("#failParam").val() == ''||$("#failParam").val().replace(/\s*/g, "") == "") {
            alert("异常示例不能为空,请您输入！");
            return;
        }
        /*if ($("#documentsId").val() == ''||$("#documentsId").val().replace(/\s*!/g, "") == "") {
         alert("参数不能为空,请您输入！");
         return;
         }*/
        //数据[=,&...]处理
        $("#sec_fir input[type='text']").each(function(){
            var str = $(this).val();
            //js验证 `~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？
            var pattern = new RegExp("[~'!@#$%^&*()-+_=:]");
            if(str != "" && str != null){
                if(pattern.test(str)){
                    var reg =new RegExp("=","g");
                    var newstart=str.replace(reg,"等于");
                    var reg1 =new RegExp("&","g");
                    var newstart1=newstart.replace(reg1,"并且");
                    $(this).val(newstart1);
                }
            }
        });
        //移除被选元素[#sec_fir menu:nth-of-type(2)]，包括所有文本和子节点。
        $("#sec_fir menu:nth-of-type(2)").remove();
        //表单序列化并解码
        var msg = decodeURIComponent($("#submitForm").serialize());
        var json = "[{";
        var msg2 = msg.split("&");   //先以“&”符号进行分割，得到一个key=value形式的数组
        var t = false;
        for (var i = 0; i < msg2.length; i++) {
            var msg3 = msg2[i].split("=");  //再以“=”进行分割，得到key，value形式的数组
            for (var j = 0; j < msg3.length; j++) {
                json += "\"" + msg3[j] + "\"";
                if (j + 1 != msg3.length) {
                    json += ":";
                }
                if (t) {
                    json += "}";
                    if (i + 1 != msg2.length) {  //表示是否到了当前行的最后一列
                        json += ",{";
                    }
                    t = false;
                }
                if (msg3[j] == "example") {  //这里的“example”是你的表格的最后一列的input标签的name值，表示是否到了当前行的最后一个input
                    t = true;
                }
            }
            if (!msg2[i].match("example")) {  //同上
                json += ",";
            }

        }
        json += "]";

        var portId = $("#portId").val();
        var portRequestType =  $("#portRequestType option:selected").val();
        var dataFormat =  $("#dataFormat option:selected").val();
        var portUrl = $("#portUrl").val();
        var portName = $("#portName").val();
        var portDescription = $("#portDescription").val();
        var successParameter = $("#successParam").val();
        var failParameter = $("#failParam").val();
        var portParms = {
            "portId": portId,
            "portRequestType": portRequestType,
            "dataFormat": dataFormat,
            "portUrl": portUrl,
            "portName": portName,
            "portDescription": portDescription,
            "successParameter": successParameter,
            "failParameter": failParameter,
            "parameters": json
        };
       //alert(JSON.stringify(portParms))

        //接口修改
        $.ajax({
            type: "POST",
            url: basePath + "ea/ccode1/sajax_pm_updatePortAndParameters.jspa",
            data: {"portAndParameter": JSON.stringify(portParms)},
            dateType: "json",
            success: function (data) {
                var member = eval("(" + data + ")");
                var code = member.code;
                var updatePort = member.updatePort;
                //接口修改成功
                if (code == '200') {
                    alert(updatePort);
                    location.href = basePath + 'ea/ccode1/ea_pm_selectPorts.jspa?documentsId=' + documentsId;
                } else if (code == '400') {
                    //未登录
                    alert(updatePort);
                    location.href = basePath + "page/ea/not_login.jsp";
                } else {
                    //接口修改失败
                    alert(updatePort);
                    location.reload();
                }

            },
            error: function (data) {
                alert("接口修改失败！");
                location.reload();
            }
        });
    }
</script>
<script>

    $(function () {
        //接口修改回显
        $.ajax({
            type: "POST",
            url: basePath + "ea/ccode1/sajax_pm_selectPortParticularsParameters.jspa",
            data: {
                "portId": portId
            },
            async:false,
            success: function (data) {
                var member = eval("(" + data + ")");
                var code = member.code;
                //获取接口数据成功，回显数据
                if (code == '200') {
                    var portParms = member.portParticularsParam;
                    $("#successParam").val(member.successfilename);
                    $("#failParam").val(member.failfilename);
                    for (var i = 0; i < portParms.length; i++) {
                        var pp = portParms[i];
                        if (i == 0) {
                            $("#portId").val(pp[1]);
                            $("#portName").val(pp[2]);
                            $("#portUrl").val(pp[3]);
                            $("#portDescription").val(pp[4]);
                            $('#portRequestType').children('option').each(function () {
                                if ($(this).val() == pp[5]) {
                                    $(this).prop('selected', true);
                                }
                            });
                            $('#dataFormat').children('option').each(function () {
                                if ($(this).val() == pp[6]) {
                                    $(this).prop('selected', true);
                                }
                            });
                            $("#sec_fir").append($("#sec_fir menu:nth-of-type(2)").clone(true).removeClass("none"))

                        } else {
                            $("#sec_fir").append($("#sec_fir menu:nth-of-type(2)").clone(true).removeClass("none"))

                        }
                        //接口参数回显
                        var pidlength = $("input[name='parameterId']").length;
                        $("input[name='parameterId']").each(function (i, dom) {
                            //判断是否是最后一个文本框，给其赋值
                            if (i === pidlength - 1) {
                                $(this).val(pp[9]);//通过循环数据回显至文本框
                            }
                        });

                        var pnamelength = $("input[name='parameterName']").length;
                        $("input[name='parameterName']").each(function (i, dom) {
                            //判断是否是最后一个文本框，给其赋值
                            if (i === pnamelength - 1) {
                                //js验证 `~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？
                                var pattern = new RegExp("[~'!@#$%^&*()-+_=:等于并且]");
                                if(pp[10] != "" && pp[10] != null){
                                    if(pattern.test(pp[10])){
                                        var reg =new RegExp("等于","g");
                                        var newstart=pp[10].replace(reg,"=");
                                        var reg1 =new RegExp("并且","g");
                                        var newstart1=newstart.replace(reg1,"&");
                                        $(this).val(newstart1);
                                    }else{
                                        $(this).val(pp[10]);//通过循环数据回显至文本框
                                    }
                                }
                            }
                        });

                        var ptlength = $("select[name='parameterType']").length - 1;
                        $("select[name='parameterType']:eq(" + ptlength + ") option").each(function () {//只会对最后一个select有效
                            if ($(this).val() == pp[11]) {
                                $(this).attr("selected", true);
                            }
                        });

                        var pexplength = $("input[name='parameterExplain']").length;
                        $("input[name='parameterExplain']").each(function (i, dom) {
                            //判断是否是最后一个文本框，给其赋值
                            if (i === pexplength - 1) {
                                //js验证
                                var pattern = new RegExp("[~'!@#$%^&*()-+_=:等于并且]");
                                if(pp[12] != "" && pp[12] != null){
                                    if(pattern.test(pp[12])){
                                        var reg =new RegExp("等于","g");
                                        var newstart=pp[12].replace(reg,"=");
                                        var reg1 =new RegExp("并且","g");
                                        var newstart1=newstart.replace(reg1,"&");
                                        $(this).val(newstart1);
                                    }else{
                                        $(this).val(pp[12]);//通过循环数据回显至文本框
                                    }
                                }
                            }

                        });

                        var reqlength = $("input[name='required'][type='checkbox']").length;
                        $("input[name='required'][type='checkbox']").each(function (i, dom) {
                            //判断是否是最后一个文本框，给其赋值
                            if (i === reqlength - 1) {
                                if ($(this).val() == pp[13]) {
                                    $(this).attr("checked", true);//通过循环数据选中复选框
                                }
                            }
                        });

                        var examlength = $("input[name='example']").length;
                        $("input[name='example']").each(function (i, dom) {
                            //判断是否是最后一个文本框，给其赋值
                            if (i === examlength - 1) {
                                //js验证
                                var pattern = new RegExp("[~'!@#$%^&*()-+_=:等于并且]");
                                if(pp[14] != "" && pp[14] != null){
                                    if(pattern.test(pp[14])){
                                        var reg =new RegExp("等于","g");
                                        var newstart=pp[14].replace(reg,"=");
                                        var reg1 =new RegExp("并且","g");
                                        var newstart1=newstart.replace(reg1,"&");
                                        $(this).val(newstart1);
                                    }else{
                                        $(this).val(pp[14]);//通过循环数据回显至文本框
                                    }
                                }
                            }

                        });

                    }
                }
                else {
                    //获取接口数据失败
                    alert("参数为空无法获取接口数据");
                    location.reload();
                }

            },
            error: function (data) {
                alert("获取数据失败！");
            },
            dateType: "json"
        });
    });

</script>
</html>
