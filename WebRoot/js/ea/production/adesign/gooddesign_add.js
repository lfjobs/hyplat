$(document).ready(function() {

    //对于车的处理
    if(typeIDs.indexOf("车")!=-1){
        $("#showcar").show();
    }
    //是
    if(isScale=="0"){
        $("#isScale option[value='1']").remove();
        $(".jjdw").show();
        if(goodsID=="") {
            $("#bcd").val("");
        }
        $("#bcd").attr("readonly",true);
        $(".moretr").hide();

        $("#unitOfMeasureCode").val(unitOfMeasureCode);
    }else{
       var size =  $("#isScale option").size();
       if(size==1) {
           $("#isScale").prepend("<option value='1'>否</option>");
       }
        $(".moretr").show();
    }

    if(variableID!=""){
        $(".dwspan").text(variableID);

    }
   //单位切换
    $("#dw").change(function(){
          $(".dwspan").text($(this).val());

    });


    $("#isScale").change(function(){
   if($(this).val()=="0"){
       $(".jjdw").show();

       $("#bcd").val("");
       $("#bcd").attr("readonly",true);
       $("#bcd").removeClass("put3");
       $("#bcd").parent().find("span").remove()
       $("#bcd").attr("placeHolder","自动生成无需录入");
       $(".moretr").hide();
       $(".spec").remove();
       $(".more").hide();
   }else{
       $(".jjdw").hide();
       $("#bcd").attr("readonly",false);
       $("#bcd").attr("placeHolder","请录入商品条码");
       $("#bcd").addClass("put3");
       $("#bcd").trigger("blur");
       $(".moretr").show();
   }


    });


    $("#bcd").blur(function(){
        if($("#isScale").val()=="0" || $(this).val()=="" || $("#barCodeText").val()==$(this).val()){
            return;
        }
        var url = basePath+"ea/gooddesign/sajax_ea_isExistsBarCode.jspa?barCode="+$(this).val();
        $.ajax({
            url:encodeURI(url),
            type:"get",
            async:false,
            dataType:"json",
            success:function (data) {
                var member = eval("("+data+")");
                var count = member.count;
                if(count!=0){
                    alert("物品条码已存在，请重新填写！");
                    $("#bcd").val("");
                    $("#bcd").trigger("blur");
                    $("#bcd").focus();
                }
            }
        })
    });
    // 切换
    $(".tab li").click(function() {
        $("." + $(".selected").attr("id")).addClass("hidcontent");
        $(".selected").removeClass("selected");

        $(this).addClass("selected");
        $("." + $(this).attr("id")).removeClass("hidcontent");

    });

    // 新增功能

    $(".add").live("click", function() {
        seqnum++;
        $(".showfunc").append($("#init").clone(true).attr("id",
            "init" + seqnum).show());

        $("#init" + seqnum).find(".del").show();
        $("#init" + seqnum).find(".input").attr("name","goodFunction.name");
        $("#init" + seqnum).find(".url").attr("name","goodFunction.url");
        $("#init" + seqnum).find(".defaults").val("").css("color","#000").removeClass("defaults");
        getd("editor"+seqnum,"init"+seqnum);


    });

    // 移除

    $(".del").live("click", function() {
        $(this).parent().parent().remove();

    });

    //文本域默认值效果
    $(".defaults").live("focus",function(){
        if ($(this).val() =='结构'){
            $(this).val("");
            $(this).css("color","#000");
        }

    }).live("blur",function(){
        if ($(this).val() ==""){
            $(this).val("结构");
            $(this).css("color","#999");
        }

    });

    $(".staffBtn").click(function(){
        staffFun("",0);
    });

    $("#searchGood").click(function(){
        staffFun($("#typeID").val(),0);
    });
    $("#wpsy").click(function(){
        staffFun("",pageNumber-1);
    });
    $("#wpxy").click(function(){
        staffFun("",pageNumber+1);
    });

    $("#selectGood").click(function(){
        if(staffID==""){
            alert("请选择");
            return;
        }
        $(".staffID").val(staffID);
        $(".staffName").val($("#body_02").find("tr#"+staffID).find("td").eq(3).find("span").text());
        $("#staffTable").hide();
    });

    $("tr.staffCon").live("click",function(){
        staffID=$(this).find(".radio").val();
        $(this).find(".radio").attr("checked",true);
    });

    $(".goodsName").live("blur",function(){
        $(this).val($(this).val().replace(/\s+/g,''));
    });


    //提交保存
    $(".save").click(function(){
        if($("#main_img").length==1){
             alert("请上传主图");
             return;
         }

        $("form :input:.ckTextLength").trigger("blur");
        $("form :input:.input3").trigger("blur");
        $("form :input:.posnum").trigger("blur");
        $("form :input:#bcd").trigger("blur");
        if($("form .error").length>1){
            return;
        }


        //关于尺码
        var str="";
        $(".size_con").find(".xuanzhong").each(function(){
            str+=$(this).text()+"##";

        });

        $("#sizevalue").val(str);
        //关于颜色以及对应图片
        var idphoto = "";//选中的且修改过图片的
        var colorphoto = "";////选中的且修改过图片的对应的颜色

        $(".style_list").find(".xuanzhong").each(function(){

            $(this).attr("name","colorvalue");
            $(this).parent().find(".btn_file").attr("name","photo");

            if(goodsID!=""){
                $(this).parent().find(".apid").attr("name","attrproduct.apid");
                $(this).parent().find(".imgurl").attr("name","attrproduct.imgurl");

                var src=  $(this).parent().find("img").attr("src");
                if(src.indexOf("data")!=-1){
                    idphoto+=$(this).parent().find(".apid").val()+",";
                    colorphoto+=$(this).val()+",";

                }
                $("#idphoto").val(idphoto);
                $("#colorphoto").val(colorphoto);

            }


        });


        //获取编辑器
        var url = "";
        $(".group").each(function(){

            if($(this).find(".content").html()!=""){
                var id = $(this).find(".content").find("div:first-child").attr("id");
                var e = id.substring(6);
                var content = UE.getEditor(id).getContent();
                url+=content+"#z";


            }
        });
        $(".url").val(url);

        $("#mainForm").attr("target","hidden").attr("action",basePath+"ea/gooddesign/ea_saveGoods.jspa");

        document.mainForm.submit.click();
        token = 2;



    });



  $(".uploadpic").click(function(){

           $(".btn_filem").click();
  });

    //主图上传弃用
   /* $("#J_MultimageField").uploadify({
        "swf"      : basePath+"js/uploadify/uploadify.swf",    //指定上传控件的主体文件
        "fileObjName"  : "file",        //文件对象
        "uploader" : basePath+"ea/gooddesign/sajax_ea_uploadFile.jspa?companyID="+companyID,   //指定服务器端上传处理文件
        "fileSizeLimit":3072,
        "queueSizeLimit":1,
        'buttonText' : '图片上传',
        'width': 72,
        'fileTypeDesc':'请选择图片格式',
        'fileTypeExts':'*.jpeg;*.jpg;*.gif;*.png;*.JPEG',
        "onUploadSuccess" : function(file, data, response) {
            var member = eval("(" + data + ")");
            var obj = jQuery.parseJSON(member);
            var filePath = obj.filePath;
            var img = "<img width=\"250\" height=\"250\" src=\""+basePath+filePath+"\">";
            $(".uploadpic").html(img);
            $("#photoPath").val(filePath);
        }
        //其他配置项
    });
*/


    // 行业
    $(document).ready(function() {
        tree1 = new dhtmlXTreeObject("industryTree", "100%", "100%", 0);
        tree1.enableDragAndDrop(false);
        tree1.enableHighlighting(1);
        tree1.enableCheckBoxes(0);
        tree1.enableThreeStateCheckboxes(false);
        tree1.setSkin(basePath + 'js/tree/dhx_skyblue');
        tree1.setImagePath(basePath + "js/tree/codebase/imgs/");
        tree1.loadXML(basePath + "js/tree/common/tree_b.xml");
        tree1.insertNewChild("0", "scode20150815wygb79q82p0000000005", "行业分类",
            0, 0, 0, 0);

        getIndustryItem(3);
        tree1.setOnClickHandler(function() {

            getIndustryItem(1);

        });

        tree1.setOnDblClickHandler(function() {
            getIndustryItem(2);

        });

    });


    // 项目产品分类
    $(document).ready(function() {
        tree2 = new dhtmlXTreeObject("projectTree", "100%", "100%", 0);
        tree2.enableDragAndDrop(false);
        tree2.enableHighlighting(1);
        tree2.enableCheckBoxes(0);
        tree2.enableThreeStateCheckboxes(false);
        tree2.setSkin(basePath + 'js/tree/dhx_skyblue');
        tree2.setImagePath(basePath + "js/tree/codebase/imgs/");
        tree2.loadXML(basePath + "js/tree/common/tree_b.xml");
        tree2.insertNewChild("0", "scode201410284shpd9x4fa0000000005", "项目产品分类",
            0, 0, 0, 0);
        getProjectItem(3)
        tree2.setOnClickHandler(function() {

            getProjectItem(1);

        });

        tree2.setOnDblClickHandler(function() {
            getProjectItem(2);

        });

    });


    //物品类别
    $(document).ready(function() {
        tree4 = new dhtmlXTreeObject("goodcateTree", "100%", "100%", 0);
        tree4.enableDragAndDrop(false);
        tree4.enableHighlighting(1);
        tree4.enableCheckBoxes(0);
        tree4.enableThreeStateCheckboxes(false);
        tree4.setSkin(basePath + 'js/tree/dhx_skyblue');
        tree4.setImagePath(basePath + "js/tree/codebase/imgs/");
        tree4.loadXML(basePath + "js/tree/common/tree_b.xml");
        tree4.insertNewChild("0", "scode20101014v5zed7cukk0000000002", "物品类别",
            0, 0, 0, 0);
        getGoodCateItem(3)
        tree4.setOnClickHandler(function() {

            getGoodCateItem(1);

        });

        tree4.setOnDblClickHandler(function() {
            getGoodCateItem(2);

        });

    });




    //xgb场地
    $(".site").click(function(){
        site("",0);
    });

    $("#sitequery").click(function(){
        site($("#numberOrName").val(),0);
    });
    $("#sitewpsy").click(function(){
        site("",pageNumber-1);
    });
    $("#sitewpxy").click(function(){
        site("",pageNumber+1);
    });
    $("#sitedetermine").click(function(){
        if(siteId==""){
            alert("请选择");
            return;
        }
        $("#siteId").val(siteId);
        $("#siteName").val($("#site_body").find("tr#"+siteId).find("td").eq(3).find("span").text());
        $("#site").hide();
    });

    $("tr.siteId").live("click",function(){
        siteId=$(this).find(".radio").val();
        $(this).find(".radio").attr("checked",true);
    });

});



// 获取行业
function getIndustryItem(type) {
    if(type==3){
        treeid1 ="scode20150815wygb79q82p0000000005";
    }else{
        treeid1 = tree1.getSelectedItemId();
    }
    treename1 = tree1.getItemText(treeid1);
    tree1.deleteChildItems(treeid1);
    var getListCCodeurl = basePath
        + "ea/ccode/sajax_ea_getListCCodeByPID.jspa?codeID=" + treeid1
        + "&date=" + new Date().toLocaleString();
    $.ajax({
        url : encodeURI(getListCCodeurl),
        type : "get",
        async : true,
        dataType : "json",
        success : function cbf(data) {
            var member = eval("(" + data + ")");
            var codeList = member.codeList;

            if (codeList.length == 0) {
                if (type == 2) {
                    $("#indID").val(treeid1);
                    $("#ind").val(treename1);
                    $("#industry").hide();

                    var a = new Array();
                    a.unshift(">");
                    a.unshift(treename1);
                    var parentid = treeid1;
                    var parentname = "";

                    while (true) {

                        parentid = tree1.getParentId(parentid);

                        if (parentid != "scode20150815wygb79q82p0000000005") {

                            parentname = tree1.getItemText(parentid);
                            a.unshift(">");
                            a.unshift(parentname);
                        } else {
                            var str = a.join("").substring(0,
                                a.join("").length - 1);
                            $("#mulind").val(str);
                            break;
                        }

                    }
                }
                return;
            }
            for (var i = 0; i < codeList.length; i++) {

                tree1.insertNewChild(treeid1, codeList[i].codeID,
                    codeList[i].codeSn + codeList[i].codeValue, 0, 0, 0, 0);

            }
        },
        error : function cbf(data) {
            alert("数据获取失败！");
        }
    });

}



// 项目产品分类
function getProjectItem(type) {
    if(type==3){
        treeid2 ="scode201410284shpd9x4fa0000000005";
    }else{
        treeid2 = tree2.getSelectedItemId();
    }
    treename2 = tree2.getItemText(treeid2);
    tree2.deleteChildItems(treeid2);
    var getListCCodeurl = basePath
        + "ea/ccode/sajax_ea_getListCCodeByPID.jspa?codeID=" + treeid2
        + "&date=" + new Date().toLocaleString();
    $.ajax({
        url : encodeURI(getListCCodeurl),
        type : "get",
        async : true,
        dataType : "json",
        success : function cbf(data) {
            var member = eval("(" + data + ")");
            var codeList = member.codeList;

            if (codeList.length == 0) {
                if (type == 2) {
                    $("#pro").val(treename2);
                    $("#project").hide();

                    var b = new Array();
                    b.unshift(">");
                    b.unshift(treename2);
                    var parentid = treeid2;
                    var parentname = "";

                    while (true) {

                        parentid = tree2.getParentId(parentid);

                        if (parentid != "scode201410284shpd9x4fa0000000005") {

                            parentname = tree2.getItemText(parentid);
                            b.unshift(">");
                            b.unshift(parentname);
                        } else {
                            var str = b.join("").substring(0,
                                b.join("").length - 1);

                            $("#mulpro").val(str);
                            break;
                        }

                    }
                }
                return;
            }
            for (var i = 0; i < codeList.length; i++) {

                tree2.insertNewChild(treeid2, codeList[i].codeID,
                    codeList[i].codeSn + codeList[i].codeValue, 0, 0, 0, 0);

            }
        },
        error : function cbf(data) {
            alert("数据获取失败！");
        }
    });

}




// 获取物品类别
function getGoodCateItem(type) {
    if (type == 3) {
        treeid4 = "scode20101014v5zed7cukk0000000002";
    } else {
        treeid4 = tree4.getSelectedItemId();
    }
    treename4 = tree4.getItemText(treeid4);

    tree4.deleteChildItems(treeid4);
    var getListCCodeurl = basePath
        + "ea/ccode/sajax_ea_getListCCodeByPID.jspa?codeID=" + treeid4
        + "&date=" + new Date().toLocaleString();
    $.ajax({
        url : encodeURI(getListCCodeurl),
        type : "get",
        async : true,
        dataType : "json",
        success : function cbf(data) {
            var member = eval("(" + data + ")");
            var codeList = member.codeList;
            if (codeList.length == 0) {
                if (type == 2) {
                    $("#td").val(treename4);
                    $("#goodcate").hide();
                    if(treename4.indexOf("车")!=-1){
                        $("#showcar").show();
                    }else{
                        $("#showcar").hide();
                    }

                    /**xgb显示场地选择**/
                    if(treename4.indexOf("金币计时")!=-1 || treename4.indexOf("包月计时")!=-1 || treename4.indexOf("包年计时")!=-1||treename4.indexOf("包天计时")!=-1){
                        $("#siteName").parent().parent("tr").show();
                    }else{
                        $("#siteName").parent().parent("tr").hide();
                    }

                }
                return;
            }
            for (var i = 0; i < codeList.length; i++) {

                tree4.insertNewChild(treeid4, codeList[i].codeID,
                    "("+codeList[i].codeSn+")"+codeList[i].codeValue, 0, 0, 0, 0);

            }
        },
        error : function cbf(data) {
            alert("数据获取失败！");
        }
    });

}











function getd(id,initid){

    var str = "<script type='text/plain' id='"+id+"'"+
        "style='width:100%;height:200px;'></script>";


    var str1="<script type='text/javascript'>"+

        "UE.getEditor('"+id+"', {autoFloatEnabled:"+false+"});</script>";


    $("#"+initid).find(".content").html(str+str1);


}


function staffFun(parameter,pageNum){
    if(pageNum<1)
        pageNum=1;
    if(pageNum>pageCount)
        pageNum=pageCount;
    $(".staff").remove();
    var url=basePath+"ea/gooddesign/sajax_ea_ajaxGetStaffData.jspa?parameter="+parameter;
    $.ajax({
        url : encodeURI(url),
        data:{"pageForm.pageNumber":pageNum},
        type : "get",
        async : false,
        dataType : "json",
        success : function(data) {
            var member = eval("(" + data + ")");
            var list=member.pageForm.list;
            pageCount=member.pageForm.pageCount;
            pageNumber=member.pageForm.pageNumber;
            $("#wpzycount").text(pageCount);
            var th="<tr class='staff'>" +
                "<th width='50px;'>选择</th>" +"<th width='50px;'>序号</th>" +"<th width='120px;'>编号</th>" +
                "<th width='120px;'>姓名</th>" +"<th width='50px;'>性别</th>" +"<th width='121px;'>出生日期</th>" +
                "<th width='50px;'>籍贯</th>" +"<th width='100px;'>手机号</th>" +"<th width='200px;'>身份证</th>" +
                "</tr>";
            $("div#body_02").find("table").find("thead").append(th);
            for(var i=0;i<list.length;i++){
                var td="<tr class='staff staffCon' id="+list[i].staffID+">" +
                    "<td><input type='radio' class='radio' name='staffRadio' value="+list[i].staffID+"></td>" +
                    "<td>"+(i+1)+"</td>" +
                    "<td><span>"+list[i].staffCode+"</span></td>" +
                    "<td><span>"+list[i].staffName+"</span></td>" +
                    "<td><span>"+list[i].sex+"</span></td>" +
                    "<td><span>"+list[i].birthday+"</span></td>" +
                    "<td><span>"+list[i].nativePlace+"</span></td>" +
                    "<td><span>"+list[i].reference+"</span></td>" +
                    "<td><span>"+list[i].staffIdentityCard+"</span></td>" +
                    "</tr>";
                $("div#body_02").find("table").find("tbody").append(td);
            }
        },error:function(data){
            alert("数据获取错误");
        }
    });
}



function re_load() {
    window.opener.location.href=window.opener.location.href
    window.close();

}






function site(numberOrName,pageNum){
    if(pageNum<1)
        pageNum=1;
    if(pageNum>pageCount)
        pageNum=pageCount;
    $(".site_type").remove();
    var url=basePath+"ea/carmanage/sajax_ea_ajaxSiteList.jspa?numberOrName="+numberOrName;
    $.ajax({
        url : encodeURI(url),
        data:{"pageForm.pageNumber":pageNum},
        type : "get",
        async : false,
        dataType : "json",
        success : function(data) {
            var member = eval("(" + data + ")");
            var nologin = member.nologin;
            if(nologin){
                document.location.href = basePath + "/page/ea/not_login.jsp";
            }
            var list=member.pageForm.list;
            pageCount=member.pageForm.pageCount;
            pageNumber=member.pageForm.pageNumber;
            $("#sitecount").text(pageCount);
            var th="<tr class='site_type'>" +
                "<th width='50px;'>选择</th>" +"<th width='50px;'>序号</th>" +"<th width='120px;'>编号</th>" +
                "<th width='120px;'>名称</th>" +"<th width='50px;'>所属地点</th>" +"<th width='121px;'>面积</th>" +
                "<th width='50px;'>容纳</th>" +"<th width='100px;'>创建时间</th>" +"<th width='200px;'>所属公司</th>"+
                "</tr>";
            $("div#site_body").find("table").find("thead").append(th);

            $(list).each(function(i, dom) {
                var td="<tr class='site_type siteId' id="+this[0]+">" +
                    "<td><input type='radio' class='radio' name='siteId' value="+this[0]+"></td>" +
                    "<td>"+(i+1)+"</td>" +
                    "<td><span>"+this[2]+"</span></td>" +
                    "<td><span>"+this[3]+"</span></td>" +
                    "<td><span>"+this[4]+"</span></td>" +
                    "<td><span>"+this[5]+"</span></td>" +
                    "<td><span>"+this[6]+"</span></td>" +
                    "<td><span>"+this[7]+"</span></td>" +
                    "<td><span>"+this[1]+"</span></td>" +
                    "</tr>";
                $("div#site_body").find("table").find("tbody").append(td);
            })

        },error:function(data){
            alert("数据获取错误");
        }
    });
}

//添加更多规格
function addMoreSpec(){

    var dw = $("#dw").val();
    if(dw==""){
        alert("请先选择单位");
        return;
    }
    if($(".more").is(":hidden")){
        $(".more").show();
    }
    var option = $("#dw").html();
    specnum++;
    var html = new Array();

    html.push("<tr class='spec'>");
    html.push("<td><input type='text'   name='goodsbarmap["+specnum+"].spcation'/></td>");
    html.push("<td><div style='display: inline-block'>1<select  name='goodsbarmap["+specnum+"].variable1Id'>"+option+"</select>=</div></td>");
    html.push("<td align='left'><input type='text'  name='goodsbarmap["+specnum+"].quantity' style='width:88%;' /><span class='dwspan'>"+dw+"</span></td>");
    html.push("<td><input type='text'  name='goodsbarmap["+specnum+"].barcode'/></td>");
    html.push("<td class='del' style='cursor:pointer;' onclick='deleteX(this)'>X</td>");
    html.push("</tr>");
  $(".spectbl").append(html.join(""));

}
//规格删除
function deleteX(obj){
  $(obj).parent("tr").remove();
  if($(".spectbl").find(".del").length==0){
      $(".more").hide();
  }

}


