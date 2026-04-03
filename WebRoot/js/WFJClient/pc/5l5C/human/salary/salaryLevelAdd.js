$(document).ready(function () {
    getUnitInit();
});

//获取单元
function getUnitInit() {


    var unitslist = getUnits("");
    var html = new Array();
    for(var i=0;i<unitslist.length;i++){
        var obj = unitslist[i];
        var suID = obj[0];
        var unitName = obj[1];
        var unitslist1 = getUnits(suID);
        var show = 1;
        if(unitslist1.length==0){
            var itemlist = getItems(suID);

            if(itemlist.length==1){
                var obj = itemlist[0];

                var itemName = obj[1];
                if(itemName==unitName){

                    show = 0;
                }

            }
            if(show==1&&itemlist.length>0){
                html.push("<li id='"+suID+"'><div class='div-1'><label>"+unitName+"</label></div></li>");

            }
            for(var u=0;u<itemlist.length;u++){
                var obj = itemlist[u];
                var stID = obj[0];
                var itemName = obj[1];
                html.push("<li id='"+stID+"'><div><label>"+itemName+"</label><input type='text' /></div></li>");


            }



        }else {

            html.push("<li id='"+suID+"'><div class='div-0'><label>"+unitName+"</label></div></li>");

            for (var j = 0; j < unitslist1.length; j++) {
                var obj = unitslist1[j];
                var suID = obj[0];
                var unitName = obj[1];
                var itemlist = getItems(suID);
                if(itemlist.length>0) {
                    html.push("<li id='" + suID + "'><div class='div-1'><label>" + unitName + "</label></div></li>");
                }

                for (var k = 0; k < itemlist.length; k++) {
                    var obj = itemlist[k];
                    var stID = obj[0];
                    var itemName = obj[1];
                    html.push("<li id='" + stID + "'><div><label>" + itemName + "</label><input type='text' /></div></li>");


                }
            }
        }
    }
    $(".main-ul").append(html.join(""));


}


//获取单元
function getUnits(parentID) {

    var unitslist=new Array();
    $.ajax({
        type: "post",
        url: basePath + "ea/salarylevel/sajax_ea_getSalaryUnitsList.jspa",
        data: {

            suID:parentID
        },
        async:false,
        dataType: "json",
        success: function (data) {
            var member = eval("("+data+")");
            unitslist = member.unitslist;


        },
        error: function (data) {

        }


    });
    return  unitslist;

}

//获取项目
function getItems(suID) {
var itemlist = new Array();

    $.ajax({
        type: "post",
        url: basePath + "ea/salarylevel/sajax_ea_getSalaryItemList.jspa",
        data: {

            suID:suID
        },
        dataType: "json",
        async:false,
        success: function (data) {
            var member = eval("("+data+")");
            itemlist = member.itemlist;
        },
        error: function (data) {

        }


    });
  return itemlist;
}