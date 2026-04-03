$(function(){
	var clicker;
$(".t-keybord2").mousedown(function(event) {
clicker = $(this);
var values = clicker.val();
var wh = $(window).height();
var ww = $(window).width();
var kh = $("#t-keybord2").height();
var kw = $("#t-keybord2").width();
var t = clicker.offset().top + clicker.outerHeight() + 5;
var l = clicker.offset().left;
if ((t + kh) > wh) {
	t = clicker.offset().top - kh - 5;
}
if (l + kw > ww) {
	l = clicker.offset().left - kw + clicker.width();
}
//$("#t-keybord").css({ top: t, left: l });
$("#t-value2").val(values);
$("#t-keybord2").fadeIn('fast');
});
//取消
$("#t-qx2").click(function(){
	$(".cz2").fadeOut('fast');
    $("#t-value2").val("");
})

//确认
$("#t-submit2").click(function(event) {
	if($("#t-value2").val()==""){
		return false;
	}
$("#inputnum").val($("#t-value2").val());
$(".cz2").fadeOut('fast');
    var price = $(".chengzhong_js .pr").text();
    if(!$(".chengzhong_js .inputnum").is(':hidden')) {
        $(".totalMoney").text((Number(price) * Number($("#inputnum").val())).toFixed(2));
    }
    $("#t-value2").val("");
});

//关闭
$("#t-close2").click(function(event) {
$("#t-value2").val('');
});

//当点击数字键时
$("#t-keyvalue2 li").bind({
mousedown: function() {
	var val = $("#t-value2").val();
	if(val==""&&$(this).html()=="0"){
        return false;
	}

	val += $(this).html();
    if(val.length>4){
        return false;
    }
	$("#t-value2").val(val);
	$(this).css({
		"background-color": '#ffffff'
	});
},
mouseup: function() {
	$(this).css({
		"background-color": '#ffffff'
	});
},
mouseover: function() {
	$(this).css({
		"background-color": '#ffffff'
	});
},
mouseout: function(){
	$(this).css({
		"background-color": '#ffffff'
	});
}
});

//点击del
$("#t-del2").click(function() {
var val = $("#t-value2").val();
if (val.length > 0) {
	val = val.substring(0, val.length - 1);
}
$("#t-value2").val(val);
});
//$(function(){
//$("#t-keybord").draggable({ handle: "#t-value" });
//})
})
