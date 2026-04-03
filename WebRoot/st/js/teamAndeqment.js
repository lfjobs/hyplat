$(document).ready(function(e) {
    $("#car_list a").on("click",function(){
        alert("成功");
    });
    teamList();
    eqment();
})

function eqment(carNum) {
    var url= basePath+"st/enroll/sajax_ea_AjaxEqment.jspa?companyID="+companyID;
    if(carNum){
        url = basePath+"st/enroll/sajax_ea_AjaxEqment.jspa?carNum="+carNum+"&companyID="+companyID;
    }
    $.ajax({
        url:encodeURI(url),
        type:"get",
        dataType:"json",
        async:false,
        success:function (data) {
            var member = eval("(" + data + ")");
            var carList = member.carList;
            var str = new Array();
            if (carList != null && carList.length > 0) {
                for (i=0;i<carList.length;i++){
                    var entity = carList[i];
                    str.push('<a onclick="remove()" href="'+basePath+'st/enroll/ea_getEqptDetails.jspa?&carNum='+entity[0]+'&cartype='+entity[1]+'&name='+entity[2]+'&reference='+entity[3]+'&address='+entity[4]+ '&photo='+entity[5]+'"> ');
                    str.push('<li id='+basePath+entity[7]+'>');
                    str.push('<img src='+basePath+(entity[5]==null?"st/images/ico-car1.png":entity[5])+' class="left">');
                    str.push('<div class="text">');
                    str.push('<h4>'+(entity[1]==null?"":entity[1])+'  '+entity[0]+'</h4>');
                    str.push('<p></p>');
                    str.push('<p class="tel"><img src='+basePath +'st/images/ico-job.png>教练:'+entity[2]+'教练</p>');
                    str.push('</div>');
                    str.push('<img class="right" src='+basePath +'st/images/ico-right2.png>');
                    str.push('</li>');

                }
                $("#car_list").append(str.join(""));
            }
        }
    });
}

function remove() {
    $(".search_frd .search").val("")
}
function teamList(staffName) {
    var url= basePath+"st/enroll/sajax_ea_AjaxTeam.jspa?companyID="+companyID;
    if(staffName){
        url = basePath+"st/enroll/sajax_ea_AjaxTeam.jspa?staffName="+staffName+"&companyID="+companyID;
    }
    $.ajax({
        url:encodeURI(url),
        type:"get",
        dataType:"json",
        async:false,
        success:function (data) {
            var member = eval("(" + data + ")");
            var personnelList = member.personnelList;
            var str = new Array();
            if (personnelList != null && personnelList.length > 0) {
                for (i=0;i<personnelList.length;i++){
                    var entity = personnelList[i];
                    str.push('<a onclick="remove()" href="'+basePath+'st/enroll/ea_getTeamDetails.jspa?staffId='+entity[0]+'&postName='+entity[1]+'"> ')
                    str.push('<li id='+entity[0]+'>');
                    str.push('<img src='+basePath+(entity[2]==null?"images/ea/driving/elkc/head.png":entity[2])+' class="left">');
                    str.push('<div class="text">');
                    str.push('<h4>'+entity[1]+'</h4>');
                    str.push('<p></p>');
                    str.push('<p class="tel"><img src='+basePath +'st/images/ico-job.png>教练员:'+entity[1]+'教练员</p>')
                    str.push('</div>');
                    str.push('<img class="right" src='+basePath +'st/images/ico-right2.png>');
                    str.push('</li>');

                }
                $("#coach_list2").append(str.join(""));
            }
        }
    });

};