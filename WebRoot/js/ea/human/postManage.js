let pageNumber = 1, pageSize = 30,pageCount = 0;
let selectedId= "",scrollBool = true,staffTypeList=null,dataList = {};
let type = "",radioBool = false;
$(function () {
    initCss();
    bindEvents();
    getData();
    //getStaffType();
});

/**
 * 初始化样式
 */
function initCss(){
    $(".content").height($(window).height() - $("header").height());
    $(".sec-list").height($(".content").height() - $(".sec-nav").height());
    $(".data-list").height($(".sec-list").height() - $(".data-title").height() - $(".spd-content").height());

    $(".sec-people").height($(".content").height() - $(".sec-nav").height());
    $(".div-people").height($(".sec-people").height() - $(".div-bottom").height() - 20);
    $(".dtd-oa-search-bar-input").width($(".dtd-oa-search-bar").width() - 55 - 70);
    $(".close-data").css("right","85px");
}



/**
 * 点击事件
 */
function bindEvents() {
    // 添加
    $(".add").click(function () {
        document.location.href = basePath+ "/ea/post/ea_toSaveDepartmentPost.jspa?";
    })
    // 修改
    $(".edit").click(function () {
        if (selectedId == ""){
            layer.msg("请选择将要修改的数据");
            return false;
        }
        document.location.href = basePath + "ea/post/ea_getDepartmentPostById.jspa?type=edit" + "&&postId=" + selectedId ;
    });
    // 岗位人员
    $(".postPerson").click(function () {
        document.location.href = basePath + "page/WFJClient/pc/5l5C/human/postPerson.jsp?depPostId=" + selectedId;
    });
    // 删除
    $(".del").click(function () {
        if (selectedId == '') {
            layer.msg("请选择将要删除的数据");
            return;
        }

        const data = dataList[selectedId];
        var adminNum = data["adminNum"];
        if(adminNum > 0){
            layer.msg("职务下已经有编员，不允许删除！");
            return
        }
        layer.confirm('确定删除', {
            title:'温馨提示',
            skin: 'delete-class',
            btn: ['取消','确定']
        }, function(){
            layer.close(layer.index);
        }, function() {
            var url = basePath
                + "ea/post/sajax_ea_delPostDataById.jspa?postId=" + selectedId ;
            $.ajax({
                type: "GET",
                url: url,
                async: false,
                dataType: "json",
                success: function (data) {
                    getData();
                    layer.close(layer.index);
                    selectedId = "";
                },
                error: function (data) {
                    layer.msg("保存失败");
                }
            })

        });

    });
    $(document).on("click","#staffName",function(){
        const param = {
            "titleName": "选择人员",
            "id": "staffID",
            "name": "staffName",
            "queryBool": true,
            "callBackBool":false,
            "titleList":["员工编号","姓名","性别","手机号","身份证"],
            "fieldList":["staffCode","staffName","sex","reference","staffIdentityCard"],
            "titleWidth":[80,80,80,120,200]};
        const url = basePath + "ea/post/sajax_ea_getNeedJoinStaffData.jspa?1=1";
        sdfp.getDataByPage(url, param);
    })

    $(document).on("click", ".data-ul", function (event){
        selectedId = event.currentTarget.id;
        if (!radioBool){
            radioBool = false;
            document.location.href = basePath + "page/WFJClient/pc/5l5C/human/postPerson.jsp?depPostId=" + selectedId;
        } else {
            $(".data-ul").removeClass("active");
            $(this).addClass("active");
            document.querySelector("input[name='post'][value='" + selectedId + "']").checked = true;
            form.render('radio');
            radioBool = false;
        }

    })
    $(document).on("click", ".radio-li", function (event){
        selectedId = event.currentTarget.id;
        radioBool = true;

    })
    $(document).on("click", ".page-last", function (event) {
        if (scrollBool && pageNumber > 1){
            scrollBool = false;
            pageNumber--;
            getData();
        }

    })

    $(document).on("click", ".page-next", function (event) {
        if (scrollBool && pageNumber < pageCount){
            scrollBool = false;
            pageNumber++;
            getData();

        }

    })



    layui.use('form', function() {
        form = layui.form;
        form.render('select');
        form.render('radio');
        form.on('select(pageSelect)', function(data){
            pageNumber = 1;
            scrollBool = false;
            pageSize = data.value;
            getData();
        });
    })
}

function getStaffType(){
    var url = basePath +"ea/saudition/sajax_n_ea_getBillID.jspa?date="+new Date();
    $.ajax({
        url: url,
        type: "get",
        async: false,
        dataType: "json",
        success: function cbf(data){
            var member = eval("(" + data + ")");
            var nologin = member.nologin;
            if(nologin){
                document.location.href ="<%=basePath%>page/ea/not_login.jsp";
            }
            staffTypeList = member.staffTypeList;

        },error: function cbf(data){
            alert("数据获取失败！");
        }
    });
}
function getData(){
    layer.load();
    const param = new Array();
    param.push("pageNumber=" + pageNumber);
    param.push("&&pageSize=" + pageSize);
    param.push("&&search=" + $("#search").val());
    param.push("&&departmentPost.organizationID=" + companyId);
    const url = basePath + "ea/post/sajax_ea_getDeployList.jspa?" + param.join("");
    $.ajax({
        url: encodeURI(url),
        type: "get",
        async: true,
        dataType: "json",
        success: function cbf(data) {
            const codeList = eval("(" + data + ")");
            const post = codeList.post;
            const organization = codeList.organization;
            if (codeList === null || post === undefined){
                $(".data-list").html("暂无数据");
                $(".data-list").css({"display":"flex","align-items":"center","justify-content":"center"});
            } else {
                const list = post.list;
                pageCount = post["pageCount"];
                $(".pageNumber").html(pageNumber);
                $(".recordCount").html(post["recordCount"]);
                $(".pageCount").html(pageCount);
                const length = list.length;
                const htmlstr = new Array();
                for (let i = 0; i < length; i++) {
                    let depPostId = list[i]["depPostID"];
                    dataList[depPostId] = list[i];
                    var index = pageSize*(pageNumber - 1) + i + 1;
                    htmlstr.push("<ul id='" + list[i]["depPostID"] + "' class='data-ul data-ul-" + depPostId +"'>");
                    htmlstr.push("<li class='radio-li radio-li-" + depPostId+"'><input type=\"radio\" name=\"post\" value='" + depPostId + "' >");
                    htmlstr.push("</li>");
                    htmlstr.push("<li>" + index + "</li>");
                    htmlstr.push("<li>" + (organization[list[i]["organizationID"]] === undefined ? "":  organization[list[i]["organizationID"]]) + "</li>");
                    htmlstr.push("<li>" + list[i]["postName"] + "</li>");
                    htmlstr.push("<li>" + list[i]["postNum"] + "</li>");
                    htmlstr.push("<li>" + (list[i]["adminNum"] === null? "0" : list[i]["adminNum"]) + "</li>");
                    htmlstr.push("<li>" + (list[i]["specialpostNum"] === null? "0" : list[i]["specialpostNum"]) + "</li>");
                    htmlstr.push("<li>" + (list[i]["omppostNum"] === null? "0" : list[i]["omppostNum"]) + "</li>");
                    htmlstr.push("<li>" + list[i]["responsibilityRequire"] + "</li>");
                    htmlstr.push("<li>" + list[i]["remark"] + "</li>");
                    htmlstr.push("</ul>")
                }
                $(".data-list").html(htmlstr.join(""));
                form.render('radio');
            }
            scrollBool = true;
            layer.close(layer.index)
        },
        error: function cbf(data) {
            prompt("数据获取失败！");
            layer.close(layer.index)
        }
    });
}

function checkData(){
    let staffName = $("#staffName").val().trim();
    if ("" === staffName){
        layer.msg("请选择姓名");
        return false;
    }
    let postType = $('input[name="cos.status"]:checked').val();
    if (postType === "01") {
        let fieldArray = ["staffTypeName","salaryLevelSerial"];
        let fieldName = ["员工类型","职务级别"];
        let length = fieldArray.length;
        for (let i = 0; i < length; i++){
            const field = fieldArray[i];
            value = $("#" + field).val().trim();
            if (value == ""){
                layer.msg("请选择"+ fieldName[i]);
                return false;
            }
        }
    }
    return true;
}

function saveData(){
    if (!checkData()){
        return false;
    }
    let url = "ea/post/sajax_ea_savePersonPostAllocation.jspa";
    const formData = new FormData($("#form")[0]);
    formData.append("type",type);
    saveBool = true;
    if (!saveBool){
        layer.msg("请勿重复提交");
        return false;
    }
    $.ajax({
        url: basePath + url,
        type: 'POST',
        data: formData,
        dataType:"json",
        async : true,
        processData : false,
        contentType : false,
        success: function (data) {
            var member = eval("(" + data + ")");
            var vals = member.vals;
            var status = member.status;
            layer.msg(vals);
            if ("0" === status){
                location.reload();
                type = "";
            }
        },
        error: function (data) {
            layer.msg("保存失败");
        }
    });
}
function returnPage(){
    window.history.back();
    return false
}

function clearQueryName(){
    $("#search").val("");
    pageNumber = 1;
    getData();
}
function getDataByName(){
    pageNumber = 1;
    getData();
}
