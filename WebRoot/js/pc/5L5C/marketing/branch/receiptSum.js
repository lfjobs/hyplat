// let pageNumber = 1, pageSize = 40, pageCount = 0;
// let selectedId = "", selectCosId = "", moneys = "", scrollBool = true;
// $(function () {
//     bindEvents();
//     getEntryStaffData();
//     getNum();
// });
//
// /**
//  * 点击事件
//  */
// function bindEvents() {
//     // 添加
//     $(".item1").click(function () {
//         window.location.href = basePath + "page/pc/5L5C/marketing/visitorMessagesHome1.jsp";
//     })
//     $(".item2").click(function () {
//         window.location.href = basePath + "page/pc/5L5C/marketing/visitorMessagesSendNew.jsp";
//     })
//     // 修改
//     $(".edit").click(function () {
//         if (selectedId == "") {
//             layer.msg("请选择将要修改的数据");
//             return false;
//         }
//         document.location.href = basePath + "ea/cRMShortMessagingTemplatePO/ea_updateTemplate1.jspa?id=" + selectedId;
//     });
//     // 查询
//     $(".query").click(function () {
//         if (selectedId == "") {
//             layer.msg("请选择将要查看的数据");
//             return false;
//         }
//         document.location.href = basePath + "ea/crmCustomerPO/ea_queryCustomer.jspa?id=" + selectedId;
//     });
//     //跳到公司
//     $("#modal-btn-confirm1").click(function () {
//         window.location.href = basePath + "page/pc/5L5C/marketing/companys.jsp";
//     })
//     // 导入
//     $(".importData").click(function () {
//         var url = "ea/crmCustomerPO/ea_importData.jspa";
//         window.location.href = basePath + url;
//     });
//     // 删除
//     $(".del").click(function () {
//         if (selectedId == "") {
//             layer.msg("请选择将要删除的数据");
//             return false;
//         }
//         layer.confirm('确定删除', {
//             title: '温馨提示',
//             skin: 'delete-class',
//             btn: ['取消', '确定']
//         }, function () {
//             layer.close(layer.index);
//         }, function () {
//             var url = basePath
//                 + "ea/cRMShortMessagingTemplatePO/sajax_ea_deleteTemplate.jspa?id=" + selectedId;
//             $.ajax({
//                 type: "GET",
//                 url: url,
//                 async: false,
//                 dataType: "json",
//                 success: function (data) {
//                     pageNumber = 1, pageSize = 40, pageCount = 0;
//                     getEntryStaffData();
//                     layer.close(layer.index);
//                     selectedId = "";
//                 },
//                 error: function (data) {
//                     layer.msg("保存失败");
//                 }
//             })
//
//         });
//     });
//     $(document).on("click", ".data-div", function (event) {
//         selectedId = event.currentTarget.id;
//         selectCosId = event.currentTarget.attributes["cosid"].value;
//         $(".data-div").removeClass("active");
//         $(this).addClass("active");
//         $("#myModal").css("display", "block");
//     })
//     // 监听滚动事件
//     const divElement = document.querySelector('.data-list');
//     divElement.addEventListener('scroll', function () {
//         if (divElement.scrollTop + divElement.clientHeight >= divElement.scrollHeight || 1 == 1) {
//             if (pageNumber < pageCount && scrollBool) {
//                 scrollBool = false;
//                 pageNumber++;
//                 getEntryStaffData();
//             }
//
//         }
//     })
// }
//
// function getEntryStaffData() {
//     const param = new Array();
//     param.push("pageNumber=" + pageNumber);
//     param.push("&&pageSize=" + pageSize);
//     const url = basePath + "ea/cRMShortMessagingTemplatePO/sajax_ea_getTemplate.jspa?" + param.join("");
//     $.ajax({
//         url: encodeURI(url),
//         type: "get",
//         async: true,
//         dataType: "json",
//         success: function cbf(data) {
//             const codeList = eval("(" + data + ")");
//             if (codeList == null) {
//                 $(".data-list").html("暂无数据");
//                 $(".data-list").css({"display": "flex", "align-items": "center", "justify-content": "center"});
//             } else {
//                 const list = codeList.list;
//                 pageCount = codeList["pageCount"];
//                 const length = list.length;
//                 const htmlstr = new Array();
//                 // let name= "";
//                 for (let i = 0; i < length; i++) {
//                     htmlstr.push("<div id='" + list[i][0] + "' cosid='" + list[i][0] + "' class='data-div data-div-" + list[i][0] + "'>");
//                     htmlstr.push("<div class='push-li1'>" + (list[i][3] == null ? " " : list[i][3]) + "</div>");
//                     htmlstr.push("<div class='push-li2'>" + (list[i][4] == null ? " " : list[i][4]) + "</div>");
//                     htmlstr.push("<div class='push-li3'>" + (list[i][2] == null ? " " : list[i][2]) + "</div>");
//                     // htmlstr.push("<div class='push-li3'>" + (list[i][5] == null ? " " : list[i][5]) + "</div>");
//                     htmlstr.push("<div id='moneyss' class='push-li3'>" + "发送约需" + (list[i][15] == null ? " " : list[i][15]) + "积分/人/条" + "</div>");
//                     htmlstr.push("</div>");
//                 }
//                 const moreData = document.getElementById('more-data');
//                 if (moreData != null) {
//                     moreData.remove()
//                 }
//                 if (pageNumber > 1) {
//                     $(".data-list").append(htmlstr.join(""));
//                 } else {
//                     $(".data-list").html(htmlstr.join(""));
//                 }
//
//             }
//             scrollBool = true;
//         },
//         error: function cbf(data) {
//             prompt("数据获取失败！");
//         }
//     });
// }
//
// const searchIn = document.getElementById('searchIn');
// // 添加失焦事件监听器
// searchIn.addEventListener('blur', function () {
//     const query = searchIn.value.trim();
//     if (query) {
//         performSearch(query);
//     } else {
//         getEntryStaffData();
//     }
// });
//
// // 模拟搜索函数
// function performSearch(query) {
//     const param = new Array();
//     param.push("pageNumber=" + pageNumber);
//     param.push("&&pageSize=" + pageSize);
//     param.push("&&templateHeadline=" + query);
//     $.ajax({
//         url: basePath + "ea/cRMShortMessagingTemplatePO/sajax_ea_selectTemplate.jspa?" + param.join(""), // 替换为实际的后端URL
//         type: "get",
//         async: true,
//         dataType: "json",
//         success: function cbf(data) {
//             const codeList = eval("(" + data + ")");
//             if (codeList == null) {
//                 $(".data-list").html("暂无数据");
//                 $(".data-list").css({"display": "flex", "align-items": "center", "justify-content": "center"});
//             } else {
//                 const list = codeList.list;
//                 pageCount = codeList["pageCount"];
//                 const length = list.length;
//                 const htmlstr = new Array();
//                 // let name= "";
//                 for (let i = 0; i < length; i++) {
//                     htmlstr.push("<div id='" + list[i][0] + "' cosid='" + list[i][0] + "' class='data-div data-div-" + list[i][0] + "'>");
//                     htmlstr.push("<div class='push-li1'>" + (list[i][3] == null ? " " : list[i][3]) + "</div>");
//                     htmlstr.push("<div class='push-li2'>" + (list[i][4] == null ? " " : list[i][4]) + "</div>");
//                     htmlstr.push("<div class='push-li3'>" + (list[i][2] == null ? " " : list[i][2]) + "</div>");
//                     // htmlstr.push("<div class='push-li3'>" + (list[i][5] == null ? " " : list[i][5]) + "</div>");
//                     htmlstr.push("<div  class='push-li3'>" + "发送约需" + (list[i][15] == null ? " " : list[i][15]) + "积分/人/条" + "</div>");
//                     htmlstr.push("</div>");
//                 }
//                 const moreData = document.getElementById('more-data');
//                 if (moreData != null) {
//                     moreData.remove()
//                 }
//                 if (pageNumber > 1) {
//                     $(".data-list").append(htmlstr.join(""));
//                 } else {
//                     $(".data-list").html(htmlstr.join(""));
//                 }
//
//             }
//             scrollBool = true;
//         },
//         error: function cbf(data) {
//             prompt("数据获取失败！");
//         }
//     });
// }
//
// document.addEventListener('DOMContentLoaded', function () {
//     // 获取所有的<div class="item">元素
//     const items = document.querySelectorAll('.item');
//     const icons = document.querySelectorAll('.item i');
//     const paragraphs = document.querySelectorAll('.item p');
//
//     // 默认选中第二个<div class="item">元素及其对应的<i>和<p>元素
//     if (items[1]) {
//         items[1].querySelector('i').classList.add('green-color');
//         items[1].querySelector('p').classList.add('green-color');
//     }
//
//     items.forEach(item => {
//         item.addEventListener('click', function (event) {
//             // 找到当前<div class="item">元素内的<i>和<p>元素
//             const currentIElement = event.target.closest('.item').querySelector('i');
//             const currentPElement = event.target.closest('.item').querySelector('p');
//
//             // 移除所有<i>和<p>元素的green-color类
//             icons.forEach(i => {
//                 i.classList.remove('green-color');
//             });
//             paragraphs.forEach(p => {
//                 p.classList.remove('green-color');
//             });
//
//             // 给当前点击的<i>和<p>元素添加green-color类
//             currentIElement.classList.add('green-color');
//             currentPElement.classList.add('green-color');
//         });
//     });
// });
// // 获取关闭按钮
// const closeBtn = document.querySelector(".btn-cancel");
// // 当用户点击关闭按钮时，关闭模态框
// closeBtn.onclick = function () {
//     $("#myModal").css("display", "none");
// }
//
// // 当用户点击模态框外部时，关闭模态框
// window.onclick = function (event) {
//     if (event.target == document.getElementById("myModal")) {
//         $("#myModal").css("display", "none");
//     }
// }
// // 当用户点击模态框外部时，关闭模态框
// window.onclick = function (event) {
//     if (event.target == document.getElementById("btnModal")) {
//         $("#btnModal").css("display", "none");
//     }
// }
// $(document).on("click", ".data-div", function (event) {
//     selectedId = event.currentTarget.id;
//     selectCosId = event.currentTarget.attributes["cosid"].value;
//     if (selectedId != null) {
//         $(".substance").html($(this).find('.push-li2').text());   //短信内容
//         localStorage.setItem("localTemplateText", $(this).find('.push-li2').text());
//         let text = $(this).find('#moneyss').text();
//         let firstNumber = text.match(/\d+/)[0];
//         localStorage.setItem("firstNumber", firstNumber);
//         $(".money").html("(每条" + firstNumber + "积分)");
//     }
//     $("#myModal").css("display", "none");
// })
//
// function getNum() {
//     const url = basePath + "ea/cRMShortMessagingTemplatePO/sajax_ea_getNum.jspa";
//     $.ajax({
//         url: encodeURI(url),
//         type: "get",
//         async: true,
//         dataType: "json",
//         success: function cbf(data) {
//             const codeList = JSON.parse(data);
//             $(".num1").html("免费短信：" + codeList.num);
//             $(".num2").html("积分余额：" + codeList.money);
//         },
//         error: function cbf(data) {
//             prompt("数据获取失败！");
//         }
//     });
// }
//
// /*提示弹框*/
// $(document).on("click", ".btn-confirm", function (event) {
//     $("#myModal").css("display", "none");
//     $("#btnModal").css("display", "block");
// })
// document.getElementById('modal-btn-cancel1').addEventListener('click', function () {
//     $("#modal-btn-confirm1").removeClass("active1");
//     $(this).addClass("active1");
// });
// document.getElementById('modal-btn-confirm1').addEventListener('click', function () {
//     $("#modal-btn-cancel1").removeClass("active1");
//     $(this).addClass("active1");
// });