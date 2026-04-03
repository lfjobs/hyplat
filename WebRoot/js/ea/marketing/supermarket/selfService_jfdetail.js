$(function(){

    $(".jfem").text(round(bpoint));
    $(".mem").text(round(Number(bpoint)/100));
    loaded();
  if(pagecount!=1) {
      getHeight();
  }


    //返回首页
    $("#backhome").click(function(){
        // if(posNum!=null&&posNum!=""){
        //     ajaxGetAccess(posNum);
        // }else {
        //     document.location.href = basePath + "ea/sm/ea_index.jspa";
        // }
        window.history.go(-1);
    });
});

function getHeight(){

    t = setTimeout("getHeight()",100);
    if($(".last").length>0){
        if($(".last").offset().top<$(window).height()){
            console.log($(".last").offset().top);
            if(pagenumber<pagecount){
                loaded();
            }
        }
    }
}

function loaded(){

    pagenumber++;


    var url = basePath+"/ea/bonuspoints/sajax_DetailList.jspa?";
    $.ajax({
        url : url,
        type : "post",
        async : false,
        dataType : "json",
        data : {
            "pageNumber":pagenumber,
            "sccid":sccid
        },
        success : function(data){
            var map = eval("("+data+")").map;
            var pageForm = map.pageForm;
            var signList = map.signList;
            var str = new Array();

            if(pageForm != null&&pageForm.recordCount>0)
            {
                $(".last").removeClass("last");
                var lists = pageForm.list;
                pagenumber = pageForm.pageNumber;
                pagecount = pageForm.pageCount;

                for(var i = 0; i<lists.length;i++)
                {
                    var list = lists[i];
                    if(i==lists.length-1)
                    {
                        str.push('<tr class="last">');
                    }else
                    {
                        str.push('<tr>');
                    }
                    if(list[0]=='签到送积分')
                    {
                        if(signList.length <= 0 || signList == null)
                        {
                            str.push('<td class="jf">'+list[0]+'</td>');
                        }else
                        {
                            var bflag = false;
                            for(var s = 0;s<signList.length;s++)
                            {
                                var sign = signList[s];
                                if(sign[0]==list[3])
                                {
                                    str.push('<td class="jf">'+list[0]+'-'+sign[1]+'</td>');
                                    bflag = true;
                                }
                            }
                            if(!bflag)
                            {
                                str.push('<td class="jf">'+list[0]+'</td>');
                            }
                        }
                    }
                    else
                    {
                        str.push('<td class="jf">'+list[0]+'</td>');

                    }
                    if(list[4] == 'A'){
                        str.push('<td>+'+list[1]+'</td>');
                    }else{
                        str.push('<td>-'+list[1]+'</td>');
                    }
                    str.push('<td>'+list[2]+'</td>');
                    str.push('</tr>');
                }
                $("#jifen").append(str.join(""));
                if(pagenumber==pagecount&&pagecount!=1){
                    clearTimeout(t);
                }

            }

        }
    });
}
//保留2位小数
function round(value){

    var aStr = value.toString();
    var aArr = aStr.split('.');
    if(aArr.length > 1) {
        if(aArr[1].length>2) {
            value = aArr[0] + "." + aArr[1].substr(0, 2);
        }
    }

    return Number(value);
}
/**
 *
 * 获取返回的首页参数
 * @param posNum
 */
function ajaxGetAccess(posNum){

    var ulp = basePath
        + "ea/smg/sajax_sm_ajaxGetAccess.jspa";
    $.ajax({
        type : "GET",
        url : ulp,
        dataType : "json",
        data:{
            posNum:posNum
        },
        success : function(data) {
            var me = eval("("+data+")");

            document.location.href = basePath+"ea/smg/sm_toSupermarketGoods.jspa?ccompanyID="+me.ccompanyID+"&industryType="+me.industryType+"&companyName="+me.companyName+"&posNum="+posNum;

        },
        error : function(data) {
            console.log("获取失败入口");
        }
    });
}