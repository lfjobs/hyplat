/*
    产品分类，点击分类换字体颜色
*/

function change_product(obj) {
    var product = document.getElementsByName("product");
    for (var i = 0; i < product.length; i++) {
    if(obj && obj==product[i]) {
            obj.style.color = "#0094ff";
        } else {
            product[i].style.color = "";
        }
    }
}

/*
    产品代理、未代理转换
*/

function change_agencys(obj) {
    var agencys = document.getElementsByName("agencysinfo");
    var un = document.getElementById("unagencys");
    var ag = document.getElementById("agency");
    for (var i = 0; i < agencys.length; i++) {
    if(obj && obj == agencys[i]) {
            obj.style.color = "#2E63FB";
            obj.style.backgroundColor = "#E0DFDF";
            un.style.display = "";
            ag.style.display = "none";
        } else {
            agencys[i].style.color = "";
            agencys[i].style.backgroundColor = "#F0F0F0";
            un.style.display = "none";
            ag.style.display = "";
        }
    }
}


/*
    订单信息--全部订单、未付款、已付款转换
*/
function orders(obj) {
    var o = document.getElementsByName("order");
    var name = document.getElementById("orders");

    var all = document.getElementById("all_order");
    var no = document.getElementById("no_order");
    var yet = document.getElementById("yet_order");
    for (var i = 0; i < o.length; i++) {
    if(obj && obj == o[i]) {
        if(o[i].innerHTML == "全部订单") {
                obj.style.color = "#0094ff";
                all.style.display = "";
                no.style.display = "none";
                yet.style.display = "none";
            } else if (o[i].innerHTML == "未付款") {
                obj.style.color = "#0094ff";
                all.style.display = "none";
                no.style.display = "";
                yet.style.display = "none";
            } else if (o[i].innerHTML == "已付款") {
                o[i].style.color = "#0094ff";
                all.style.display = "none";
                no.style.display = "none";
                yet.style.display = "";
            }
        } else {
            o[i].style.color = "#666666";
        }
    }
}
/*分销-- vip产品*/
function chanpin() {
	document.location.href = basePath + "/ea/distribution/ea_getlist.jspa";
}

