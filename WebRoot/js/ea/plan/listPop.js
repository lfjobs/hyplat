function crateListPop(contentId,data,id,listName,vid,class1,class2) {
    var content = $("#"+contentId);
    var str = [];
    str.push('<div class="div-listPop" id="' + id + '">');
    str.push('<div class="div-box">');
    str.push('<div class="div-top clearfix">');
    str.push('<p>'+listName+'</p>');
    str.push('<div class="div-close">');
    str.push('<img src="'+basePath+'images/BuildPlatform/close_ico_b.png"/>');
    str.push('</div>');
    str.push('</div>');
    str.push('<div class="div-con listPop">');
    str.push("<ul class='ul-left'>");
    for (var i = 0; i < data.length; i++) {
        str.push("<li>");
        str.push("<span class='popValue'>" + data[i] + "</span>");
        str.push("</li>");
    }
    str.push("</ul>");
    str.push('</div>');
    str.push('</div>');
    str.push('</div>');
    content.append(str.join(""));

    $(document).on("click", "#"+id, function (event) {
        if(event.target==this){
            $("#"+id).hide();
        }
    });

    $(document).on("click", "#"+id+" .div-close", function (event) {
        $("#"+id).hide();
    });

    // 	传入选项 更改显示模块
    $(document).on("click", "#"+id+" ul li", function () {
        $("#"+vid).val($(this).find(".popValue").text());
        $("#"+id).hide();
        $("."+class1).hide();
        $("."+class2).show();
    });
}