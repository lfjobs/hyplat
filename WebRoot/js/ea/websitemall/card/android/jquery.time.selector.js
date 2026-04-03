/*
 * 时间选择器
 */
function timePlugin($this,type){
	var myDate = new Date();
	var year=myDate.getFullYear()-20;    
	var tts="",datePicker=year+'-01-01';
	var div_top='<div style="position: absolute;top:0%;width: 100%;height:100%;background: rgba(0,0,0, 0.5);" class="timeSelector">'+
	'<div style="width: 90%;height:30%;background-color: #333;position: absolute;left: 5%;top: 20%;border: 2px solid #fff;">'+
	'<div style="border-bottom: 1px solid #666;height: 13%;padding-top: 2%;padding-left: 5%;color:#D4D4D4;font-size:'+window.innerHeight*0.02+'px;" class="dateDisplay">'+year+'-1-1'+
	'</div><div style="height: 85%;"><table style="height:75%;width: 100%;">';
	var div_bottom='</table><table style="height:22.5%;width: 100%;text-align: center;background: -webkit-gradient(linear,'+
	'left bottom, left top, from(#000),to(#444) );border: 1px solid #333;box-shadow:inset 0 0 5px #000;">'+
	'<tr><td style="border-right: 1px solid #333;color:#fff;font-size:'+window.innerHeight*0.02+'px;"><div style="cursor:pointer" class="datePickerOperation" name="determine">确定</div></td><td style="color:#fff;font-size:'+window.innerHeight*0.02+'px;"><div style="cursor:pointer" class="datePickerOperation" name="cancel">取消</div></td></tr></table></div></div></div>';
	//判断 需要什么格式 并设计DIV结构 
	switch (type) {
	case 'yyyy-MM-dd' :
		tts="yyyy-MM-dd";
		var div_subject='<tr><td width="40%"><div style="height: 100%;">'+
		'<div style="height: 15%;text-align: center;color: #fff;font-size:'+window.innerHeight*0.02+'px;">年</div><div style="text-align:center;width:'+window.innerWidth*0.34+'px;height:'+window.innerHeight*0.15625+'px;'+
		'background: -webkit-gradient(linear, left bottom, left top, from(#000),color-stop(0.35, #333), color-stop(0.50, #888),'+
		'color-stop(0.65, #333),to(#000) );border-radius:5%;overflow: hidden;"><div class="timePlugin_Year timePlugin_Date" name="year" style="height:'+window.innerHeight*0.15625+'px;overflow: auto;width:'+(window.innerWidth*0.34+17)+'px;text-align: center;">';
		div_subject+='<div style="height:'+window.innerHeight*0.15625/3+'px;font-size:'+window.innerHeight*0.04375+'px;color:#fff;"></div>';
		for(var i=0;i<40;i++){
				div_subject+='<div style="height:'+window.innerHeight*0.15625/3+'px;font-size:'+window.innerHeight*0.04375+'px;color:#fff;">'+(year+i)+'</div>';
		}
		div_subject+='<div style="height:'+window.innerHeight*0.15625/3+'px;font-size:'+window.innerHeight*0.04375+'px;color:#fff;"></div>';
		div_subject+='</div></div></div></td><td width="30%"><div style="height: 100%;"><div style="height: 15%;text-align: center;color: #fff;font-size:'+window.innerHeight*0.02+'px;">月</div>'+
		'<div style="text-align:center;width:'+window.innerWidth*0.253+'px;height:'+window.innerHeight*0.15625+'px;background: -webkit-gradient(linear, left bottom, left top, from(#000),'+
		'color-stop(0.35, #333), color-stop(0.50, #888), color-stop(0.65, #333),to(#000) );border-radius:5%;overflow: hidden;"><div class="timePlugin_Month timePlugin_Date" name="month" style="height:'+window.innerHeight*0.15625+'px;overflow: auto;width:'+(window.innerWidth*0.253+17)+'px;text-align: center;">';
		div_subject+='<div style="height:'+window.innerHeight*0.15625/3+'px;font-size:'+window.innerHeight*0.04375+'px;color:#fff;"></div>';
		for(var i=0;i<12;i++){
			if(i<9)
				div_subject+='<div style="height:'+window.innerHeight*0.15625/3+'px;font-size:'+window.innerHeight*0.04375+'px;color:#fff;">0'+(i+1)+'</div>';
			else
				div_subject+='<div style="height:'+window.innerHeight*0.15625/3+'px;font-size:'+window.innerHeight*0.04375+'px;color:#fff;">'+(i+1)+'</div>';
		}
		div_subject+='<div style="height:'+window.innerHeight*0.15625/3+'px;font-size:'+window.innerHeight*0.04375+'px;color:#fff;"></div>';
		div_subject+='</div></div></div></td><td><div style="height: 100%;"><div style="height: 15%;text-align: center;color: #fff;font-size:'+window.innerHeight*0.02+'px;">日</div>'+
		'<div style="text-align:center;width:'+window.innerWidth*0.253+'px;height:'+window.innerHeight*0.15625+'px;background: -webkit-gradient(linear, left bottom, left top,from(#000),'+
		'color-stop(0.35, #333), color-stop(0.50, #888), color-stop(0.65, #333),to(#000) );border-radius:5%;overflow: hidden;"><div class="timePlugin_Day timePlugin_Date" name="day" style="height:'+window.innerHeight*0.15625+'px;overflow: auto;width:'+(window.innerWidth*0.253+17)+'px;text-align: center;">';
		div_subject+='<div style="height:'+window.innerHeight*0.15625/3+'px;font-size:'+window.innerHeight*0.04375+'px;color:#fff;"></div>';
		for(var i=0;i<31;i++){
			if(i<9)
				div_subject+='<div style="height:'+window.innerHeight*0.15625/3+'px;font-size:'+window.innerHeight*0.04375+'px;color:#fff;">0'+(i+1)+'</div>';
			else
				div_subject+='<div style="height:'+window.innerHeight*0.15625/3+'px;font-size:'+window.innerHeight*0.04375+'px;color:#fff;">'+(i+1)+'</div>';
		}
		div_subject+='<div style="height:'+window.innerHeight*0.15625/3+'px;font-size:'+window.innerHeight*0.04375+'px;color:#fff;"></div>';
		div_subject+='</div></div></div></td></tr>';
		$this.addClass("datePickerGive").attr("readonly","readonly");
		$("body").append(div_top+div_subject+div_bottom);
		break;
	}
	//时间轴滚动事件
	$(".timePlugin_Date").on("scroll",function(){
		$(this).animate({ scrollTop: window.innerHeight*0.15625/3*Math.round($(this).scrollTop()/(window.innerHeight*0.15625/3))},1);
		var year=$(".timePlugin_Year").find("div").eq(Math.round($(".timePlugin_Year").scrollTop()/(window.innerHeight*0.15625/3))+1).text();
		var month=$(".timePlugin_Month").find("div").eq(Math.round($(".timePlugin_Month").scrollTop()/(window.innerHeight*0.15625/3))+1).text();
		
		var days=GetDays(year,month);
		$(".timePlugin_Day").find("div").css("display","block");
		for(var i=days;i<31;i++){
			$(".timePlugin_Day").find("div").eq(i+1).css("display","none");
		}
		var day=$(".timePlugin_Day").find("div").eq(Math.round($(".timePlugin_Day").scrollTop()/(window.innerHeight*0.15625/3))+1).text();

		if(tts!="yyyy-MM-dd"){
			
		}else{
			$(".dateDisplay").text(year+"-"+month+"-"+day);
			datePicker=year+"-"+month+"-"+day;
		}
	});
	//操作
	$(".datePickerOperation").on("click",function(){
		if($(this).attr("name")=="cancel"){
			$(".datePickerGive").removeClass("datePickerGive");
			$(".timeSelector").remove();
		}else{
			$(".datePickerGive").val(datePicker);
			$(".datePickerGive").removeClass("datePickerGive");
			$(".timeSelector").remove();
		}
	});
}

//判断是否是闰年
function IsLeapYear(y) {
	if ((y % 4 == 0 && y % 100 != 0) || y % 400 == 0) {
			return true;
	} else {
			return false;
	}
}
// 根据年月判断当月有几天
function GetDays(y, m) {
	if (m == 2) {
			if (IsLeapYear(y)) {
					return 29;
			} else {
					return 28;
			}
	} else if (m == 1 || m == 3 || m == 5 || m == 7 || m == 8 || m == 10 || m == 12) {
		return 31;
	} else {
		return 30;
	}
}