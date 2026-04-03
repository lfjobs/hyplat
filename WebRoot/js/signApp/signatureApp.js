//本插件用于天太世统软件管理系统电子印章
//需要设置参数
$(document).ready(function(){
	var signmanagerkey = signparam.signmanagerkey;
	var position = signparam.position;
	$.ajax({
		url:signparam.stampManagerUrl+"?signmanagerkey="+signmanagerkey,
		dataType:"json",  
       	asnyc:false,
		success:function(data){
			var path = signparam.basePath+data.path;
			var top = data.top;
			var left = data.left;
			var str = "<div id='stampDiv'><img id='stampImg' src='"+path+"'></div>";
			$("body").append(str);
			$("#stampDiv").css({
				'position':'absolute',
				'top':top,
				'left':left,
				'z-index':'90'
			});
		},
		error:function(){
		
		}
	});		
});
var signList;

function initSignature(){
	
    loadInitInfo();
  
}
function loadInitInfo(){
	if(signList!=null)return;
		$.ajax({
			url:signparam.SignListUrl,
			dataType:"json",  
			asnyc:false,
			success:function(data){
				signList=data;
				signDrawDiv();
				signDivBindEvent();
				signSelectBindEvent();
				signDoBindEvent();
				$("#signDiv").css({
					'border':'2px solid #ccc',
		 			'width':'50px',
					'min-width':'50px',
					'width':'auto',
					'float':'left',
					'height':'50px',
					'min-height':'50px',	
					'height':'auto !important',
					'z-index':'120'
				});
        	},
        	error:function(){
				alert("无法取得信息");        
        	}
		});	
}
function signDrawDiv(){
    var str="<div id='signDiv' >";
	str+='<p id="handler"><select id="signSelect"><option value="noimage">--请选择--</option>';
	for (i=0; i<signList.length;i++){
		str+="<option value='"+signList[i].enterpriseStampID+"'>"+signList[i].stampName+"</option>";
	}
    str+="</select></p><img id='signImg' src='"+signparam.defaultImage+"' > <input type='button' value='盖章' id='signDo'/>  </div>";
    $("body").append(str);
}
function signDivBindEvent(){
    //单击时间
    
    $("#signDiv").bind("dblclick",function(){
        $("#handler").show();
    });
  	$("#signDiv").draggable();

}
function  signSelectBindEvent(){
         $("#signSelect").bind("change",function(){
		 	var sid=$(this).val();
			if(sid=="noimage"){
				$("#signImg").attr("src",signparam.defaultImage);
				return;
			}
      	for(i=0; i<signList.length;i++){
			if(signList[i].enterpriseStampID==sid){
				var imagepath = signList[i].scanningAccessories;
				$("#signImg").attr("src",signparam.basePath+imagepath);
				break;	
			}			
		}
             $("#handler").hide();

         });
}
function signDoBindEvent(){
    $("#signDo").bind("click",function(){
		 $(this).hide();
		 $("#signDiv").unbind("dblclick");
		 /*
		    临时性禁用拖动功能
		  	$('#signDiv').draggable("disable");
		  	启用拖动功能
		  	$('#signDiv').draggable("enable")
		  */
		 //彻底移除对象上的拖动功能
		 $('#signDiv').draggable("destroy"); 
		 $("#signDiv").css({border:'0px solid #ccc' });
		 var obj = $("#signDiv").offset();
		 var top=obj.top;
		 var left=obj.left;
		 alert(top+" "+left);
		 var obj = $("#signDiv").offset();
		 var top=obj.top;
		 var left=obj.left;
		 var position = top+","+left;
		 var signid=$('#signSelect').val();
		 var relationid = signparam.relationid;
		 var relationtable = signparam.relationtable;
		 var signstat = signparam.signstat;
		 var sign = $("#signDiv");
		 //var url = "?&relationtable='"+signparam.relationtable+"'&relationid='"+signparam.relationid+"'&position="+position;
		 //url:signparam.sendSignUrl+"?relationtable="+relationtable+"&relationid="+relationid+"&signid="+signid+"&signstat="+signstat+"&position="+position,
		$.ajax({
			url:signparam.sendSignUrl+"?relationtable="+relationtable+"&relationid="+relationid+"&signid="+signid+"&signstat="+signstat+"&position="+position,
            async:true,
            success:function(data){
            	alert(data);
            },
            error:function(){
               
            }
        });
	
    });

}
