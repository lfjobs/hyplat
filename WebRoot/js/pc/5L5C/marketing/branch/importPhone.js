let pageNumber = 1, pageSize = 40, pageCount = 0;
let selectedId = "", selectCosId = "", scrollBool = true;

let item = localStorage.getItem("selectedIdCompany");
let localTemplateText = localStorage.getItem("localTemplateText");
let firstNumber = localStorage.getItem("firstNumber");
//filterValue 筛选条件值
let selectdeData = [], staffIds = [], names = [], filterValue = "";
let selectedDatas = [];
let ImportContacts = [];
let index = 0;

$(function () {
    initCss();
    bindEvents();
    let type = getParameterByName("type");
    if (type === "drfp" || localStorage.getItem("dialingCompleted") === "true") {
        localStorage.removeItem("currentDialIndex");
        localStorage.setItem("dialingCompleted", "false");
    }
    if (type === "fp" || type === "drfp") {
        getSelectImportContacts();
    } else {
        renderSelectedData();
    }
});

function initCss() {
    $(".content").height($(".body").height() - $("header").height());
    $(".sec-list").height($(".content").height() - $(".input-box1").height() - $(".input-box1").height() - $(".but").height() - 25);
}

/**
 * 点击事件
 */
function bindEvents() {
    $(document).on("click", ".data-ul", function (event) {
        let selectedId = event.currentTarget.id;
        let selectCosId = event.currentTarget.attributes["cosid"].value;

        // 获取所有带有 'selected' 类的列表项中的图片
        let imgElements = $(this).find(".selected img");

        imgElements.each(function () {
            let imgSrc = $(this).attr('src'); // 获取当前图片的 src 属性
            // 判断当前图片是否为 'iconUncheckAll.gif'
            if (imgSrc && imgSrc.includes('iconUncheckAll.gif')) {
                // 更换为另一张图片
                $(this).attr('src', basePath + 'js/tree/codebase/imgs/iconCheckAll.gif');
            } else {
                // 如果不是，则还原为原始图片
                $(this).attr('src', basePath + 'js/tree/codebase/imgs/iconUncheckAll.gif');
            }
        });
    });
    // 监听滚动事件
    const divElement = document.querySelector('.data-list');
    divElement.addEventListener('scroll', function () {
        if (divElement.scrollTop + divElement.clientHeight >= divElement.scrollHeight || 1 == 1) {
            if (pageNumber < pageCount && scrollBool) {
                scrollBool = false;
                pageNumber++;
                if (filterValue == "") {
                    getEntryStaffData();
                } else {
                    getStaffListByRelation();
                }

            }

        }
    });

    // 为“取消”按钮添加点击事件监听器
    $(document).on("click", ".data-ul .selected", function (event) {
        let type = getParameterByName("type");
        let storedData = [];
        if (type === "gd") {
            const ul = $(this).closest('ul');
            const number = ul.attr('id');
            removeData1(number);
        } else if (type === "fp" || type === "drfp") {
            const ul = $(this).closest('ul');
            const staffId = ul.attr('id');
            removeData2(staffId);
        } else {
            const ul = $(this).closest('ul');
            const staffId = ul.attr('id');
            removeData(staffId);
        }

    });
}

const searchIn = document.getElementById('searchIn');
// 添加失焦事件监听器
searchIn.addEventListener('blur', function () {
    const query = searchIn.value.trim();
    if (query) {
        performSearch(query);
    } else {
        renderSelectedData()
    }
});

// 模拟搜索函数
function performSearch(query) {
    const param = new Array();
    param.push("pageNumber=" + pageNumber);
    param.push("&&pageSize=" + pageSize);
    param.push("&&companyId=" + item);
    param.push("&&staffName=" + query);
    $.ajax({
        url: basePath + "ea/cRMShortMessagingTemplatePO/sajax_ea_getStaffListByName.jspa?" + param.join(""), // 替换为实际的后端URL
        type: "get",
        async: true,
        dataType: "json",
        success: function cbf(data) {
            const codeList = eval("(" + data + ")");
            if (codeList == null) {
                $(".data-list").html("暂无数据");
                $(".data-list").css({"display": "flex", "align-items": "center", "justify-content": "center"});
            } else {
                const list = codeList.list;
                pageCount = codeList["pageCount"];
                const length = list.length;
                const htmlstr = new Array();
                for (let i = 0; i < length; i++) {
                    htmlstr.push("<ul id='" + list[i][0] + "' cosid='" + list[i][0] + "' class='data-ul data-ul-" + list[i][0] + "'>");
                    htmlstr.push("<li style='text-align: center;'>" + (pageSize * (pageNumber - 1) + i + 1) + "</li>");
                    htmlstr.push("<li name='" + (list[i][2] == null ? " " : list[i][2]) + "' class='push-li1'>" + (list[i][2] == null ? " " : list[i][2]) + "</li>");
                    htmlstr.push("<li phone='" + (list[i][5] == null ? " " : list[i][5]) + "' class='push-li3'>" + (list[i][5] == null ? " " : list[i][5]) + "</li>");
                    htmlstr.push("<li style='display: none' companyId='" + data.companyId + "' class='push-li4'>" + data.companyId + "</li>");
                    htmlstr.push("<li class='selected'>取消</li>");
                    htmlstr.push("</ul>")
                }
                const moreData = document.getElementById('more-data');
                if (moreData != null) {
                    moreData.remove()
                }
                if (pageNumber > 1) {
                    $(".data-list").append(htmlstr.join(""));
                } else {
                    $(".data-list").html(htmlstr.join(""));
                }

            }
            scrollBool = true;
        },
        error: function cbf(data) {
            prompt("数据获取失败！");
        }
    });
}

function renderSelectedData() {
    let type = getParameterByName("type");
    let storedData = [];
    const htmlstr = [];
    if (type === "gd") {
        storedData = localStorage.getItem('contactData');
        const myObject = storedData ? JSON.parse(storedData) : null;
        if (myObject == null) {
            $(".data-list").html("暂无数据");
            $(".data-list").css({"display": "flex", "align-items": "center", "justify-content": "center"});
        } else {
            let counter = 1; // 初始化计数器
            myObject.forEach(data => {
                htmlstr.push("<ul id='" + data.number + "' cosid='" + data.number + "' class='data-ul data-ul-" + data.number + "'>");
                htmlstr.push("<li style='text-align: center;'>" + counter + "</li>"); // 使用计数器
                htmlstr.push("<li name='" + data.name + "' class='push-li1'>" + data.name + "</li>");
                htmlstr.push("<li phone='" + data.number + "' class='push-li3'>" + data.number + "</li>");
                htmlstr.push("<li class='selected'>取消</li>");
                htmlstr.push("</ul>");
                counter++; // 递增计数器
            });
        }
    } else if (type === "fp" || type === "drfp") {
        storedData = localStorage.getItem("ImportContacts");
        const myObject = storedData ? JSON.parse(storedData) : null;
        if (myObject == null) {
            $(".data-list").html("暂无数据");
            $(".data-list").css({"display": "flex", "align-items": "center", "justify-content": "center"});
        } else {
            let counter = 1; // 初始化计数器
            myObject.forEach(data => {
                htmlstr.push("<ul id='" + data.staffId + "' cosid='" + data.staffId + "' class='data-ul data-ul-" + data.staffId + "'>");
                htmlstr.push("<li style='text-align: center;'>" + counter + "</li>"); // 使用计数器
                htmlstr.push("<li name='" + data.name + "' class='push-li1'>" + data.name + "</li>");
                htmlstr.push("<li phone='" + data.phone + "' class='push-li3'>" + data.phone + "</li>");
                htmlstr.push("<li class='selected'>取消</li>");
                htmlstr.push("</ul>");
                counter++; // 递增计数器
            });
        }

    } else {
        storedData = localStorage.getItem('myKey');
        const myObject = storedData ? JSON.parse(storedData) : null;
        if (myObject == null) {
            $(".data-list").html("暂无数据");
            $(".data-list").css({"display": "flex", "align-items": "center", "justify-content": "center"});
        } else {
            let counter = 1; // 初始化计数器
            myObject.forEach(data => {
                htmlstr.push("<ul id='" + data.staffId + "' cosid='" + data.staffId + "' class='data-ul data-ul-" + data.staffId + "'>");
                htmlstr.push("<li style='text-align: center;'>" + counter + "</li>"); // 使用计数器
                htmlstr.push("<li name='" + data.name + "' class='push-li1'>" + data.name + "</li>");
                htmlstr.push("<li phone='" + data.phone + "' class='push-li3'>" + data.phone + "</li>");
                htmlstr.push("<li style='display: none' companyId='" + data.companyId + "' class='push-li4'>" + data.companyId + "</li>");
                htmlstr.push("<li class='selected'>取消</li>");
                htmlstr.push("</ul>");
                counter++; // 递增计数器
            });
        }
    }
    const moreData = document.getElementById('more-data');
    if (moreData != null) {
        moreData.remove()
    }
    if (pageNumber > 1) {
        $(".data-list").append(htmlstr.join(""));
    } else {
        $(".data-list").html(htmlstr.join(""));
    }
    $(".data-list").css("display", "block");

    scrollBool = true;
}

function removeData(staffId) {
    const storedData = localStorage.getItem('myKey');
    let myObject = storedData ? JSON.parse(storedData) : [];
    // 找到并移除对应的数据项
    const updatedObject = myObject.filter(data => data.staffId !== staffId);
    // 更新 localStorage
    localStorage.setItem('myKey', JSON.stringify(updatedObject));
    // 重新渲染页面
    renderSelectedData();
}

function removeData2(staffId) {
    const storedData = localStorage.getItem('ImportContacts');
    let myObject = storedData ? JSON.parse(storedData) : [];
    // 找到并移除对应的数据项
    const updatedObject = myObject.filter(data => data.staffId !== staffId);
    // 更新 localStorage
    localStorage.setItem('ImportContacts', JSON.stringify(updatedObject));
    // 重新渲染页面
    renderSelectedData();
}

function removeData1(number) {
    const storedData = localStorage.getItem('contactData');
    let myObject = storedData ? JSON.parse(storedData) : [];
    // 找到并移除对应的数据项
    const updatedObject = myObject.filter(data => data.number !== number);
    // 更新 localStorage
    localStorage.setItem('contactData', JSON.stringify(updatedObject));
    // 重新渲染页面
    renderSelectedData();
}


// 点击按钮开始拨号
$(document).on("click", ".btn-submit", function (event) {
    event.preventDefault();
    let type = getParameterByName("type");
    if (type === "gd") {
        $(".data-ul").each(function () {
            // 获取姓名
            const name = $(this).find(".push-li1").text();

            // 获取电话号码
            const phone = $(this).find(".push-li3").attr("phone");


            // 创建一个对象，包含姓名和号码
            const data = {
                name: name,
                phone: phone
            };
            // 将对象添加到数组中
            selectedDatas.push(data);
            localStorage.setItem("selectedDatas", JSON.stringify(selectedDatas));
        });
        dialPhones(selectedDatas);
        selectedDatas = [];
        index = 0;
    } else if (type === "drfp") {
        $(".data-ul").each(function () {
            // 获取姓名
            const name = $(this).find(".push-li1").text();
            // 获取 staffId
            const staffId = $(this).attr("id");
            // 获取电话号码
            const phone = $(this).find(".push-li3").attr("phone");

            // // 获取公司ID（如果需要）
            const companyId = $(this).find(".push-li4").attr("companyId");
            // 创建一个对象，包含姓名和号码
            const data = {
                name: name,
                phone: phone,
                companyId: companyId,
                staffId: staffId
            };
            // 将对象添加到数组中
            selectedDatas.push(data);
            localStorage.setItem("selectedDatas", JSON.stringify(selectedDatas));
        });
        dialPhonesFromIndex(selectedDatas);
        selectedDatas = [];
        index = 0;
    } else {
        $(".data-ul").each(function () {
            // 获取姓名
            const name = $(this).find(".push-li1").text();
            // 获取 staffId
            const staffId = $(this).attr("id");
            // 获取电话号码
            const phone = $(this).find(".push-li3").attr("phone");

            // // 获取公司ID（如果需要）
            // const companyId = $(this).find(".push-li4").attr("companyId");
            // 创建一个对象，包含姓名和号码
            const data = {
                name: name,
                phone: phone,
                // companyId: companyId,
                staffId: staffId
            };
            // 将对象添加到数组中
            selectedDatas.push(data);
            localStorage.setItem("selectedDatas", JSON.stringify(selectedDatas));
        });
        dialPhones(selectedDatas);
        selectedDatas = [];
        index = 0;
    }
});

// 判断是否为移动设备
function isMobileDevice() {
    return /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent);
}

//拨号函数
async function dialPhones(selectedDatas) {
    if (!isMobileDevice()) {
        alert("当前设备不支持拨号功能。以下是所有电话号码：\n" + phones.join("\n"));
        return;
    }
    for (let i = 0; i < selectedDatas.length; i++) {
        const phone = selectedDatas[i].phone;
        console.log(`开始拨号: ${phone}`);

        // 触发拨号
        window.location.href = `tel:${phone}`;

        // 等待页面重新获得焦点
        await new Promise((resolve) => {
            const focusHandler = () => {
                console.log(`页面重新获得焦点，拨号可能已完成: ${phone}`);
                window.removeEventListener('focus', focusHandler); // 移除监听器
                resolve();
            };
            window.addEventListener('focus', focusHandler);
        });
        console.log(`拨号完成: ${phone}`);
        if ((getParameterByName("type") === "qd") || (getParameterByName("type") === "fp")) {
            // 弹出第一个弹框：是否打通电话
            const overlay = document.getElementById('overlay');
            const modal = document.getElementById('customModal');
            const modalMessage = document.getElementById('modalMessage');

            modalMessage.textContent = `${phone}是否打通？`;
            overlay.style.display = 'block'; // 显示遮罩层
            modal.style.display = 'block';   // 显示弹框

            const isCallConnected = await new Promise((resolve) => {
                document.getElementById('confirmYes').onclick = () => {
                    overlay.style.display = 'none'; // 隐藏遮罩层
                    modal.style.display = 'none';   // 隐藏弹框
                    resolve(true);
                };
                document.getElementById('confirmNo').onclick = () => {
                    overlay.style.display = 'none'; // 隐藏遮罩层
                    modal.style.display = 'none';   // 隐藏弹框
                    resolve(false);
                };
            });
            if (!isCallConnected) {
                console.log(`${phone} 未接通`);
                saveIntendedCustomers(selectedDatas[i], "未接通");

                // 即使电话未接通，也询问是否发送短信
                modalMessage.textContent = `${phone} 未接通,是否发送短信?`;
                overlay.style.display = 'block';
                modal.style.display = 'block';

                const isSended = await new Promise((resolve) => {
                    document.getElementById('confirmYes').onclick = () => {
                        overlay.style.display = 'none';
                        modal.style.display = 'none';
                        resolve(true);
                    };
                    document.getElementById('confirmNo').onclick = () => {
                        overlay.style.display = 'none';
                        modal.style.display = 'none';
                        resolve(false);
                    };
                });

                if (isSended) {
                    localStorage.setItem("drfpSelectedDatas", JSON.stringify(selectedDatas[i]));
                    saveIsSend();
                    return; // 结束当前函数执行，等待页面返回后继续
                    //   continue;
                }
                continue; // 跳过后续逻辑，继续下一个号码
            } else {
                saveIntendedCustomers(selectedDatas[i], "已接通");
                console.log(`${phone} 已接通`);
            }


            // 弹出第二个弹框：是否是意向客户
            modalMessage.textContent = `${phone} 是意向客户？`;
            overlay.style.display = 'block'; // 显示遮罩层
            modal.style.display = 'block';   // 显示弹框

            const isInterested = await new Promise((resolve) => {
                document.getElementById('confirmYes').onclick = () => {
                    overlay.style.display = 'none'; // 隐藏遮罩层
                    modal.style.display = 'none';   // 隐藏弹框
                    resolve(true);
                };
                document.getElementById('confirmNo').onclick = () => {
                    overlay.style.display = 'none'; // 隐藏遮罩层
                    modal.style.display = 'none';   // 隐藏弹框
                    resolve(false);
                };
            });
            if (isInterested) {
                saveIntendedCustomers(selectedDatas[i], "已接通,是意向客户");
                console.log(`${phone} 被标记为意向客户`);
            } else {
                saveIntendedCustomers(selectedDatas[i], "已接通,不是意向客户");
                console.log(`${phone} 不是意向客户`);
            }
            // 弹出第三个弹框：是否发送短信
            modalMessage.textContent = `${phone} 是否需要发送短信？`;
            overlay.style.display = 'block';
            modal.style.display = 'block';

            const isSended = await new Promise((resolve) => {
                document.getElementById('confirmYes').onclick = () => {
                    overlay.style.display = 'none';
                    modal.style.display = 'none';
                    resolve(true);
                };
                document.getElementById('confirmNo').onclick = () => {
                    overlay.style.display = 'none';
                    modal.style.display = 'none';
                    resolve(false);
                };
            });

            if (isSended) {
                localStorage.setItem("drfpSelectedDatas", JSON.stringify(selectedDatas[i]));
                saveIsSend();
                return; // 结束当前函数执行，等待页面返回后继续
            }
        } else {
            // 弹出第一个弹框：是否打通电话
            const overlay = document.getElementById('overlay');
            const modal = document.getElementById('customModal');
            const modalMessage = document.getElementById('modalMessage');

            modalMessage.textContent = `${phone}是否打通？`;
            overlay.style.display = 'block'; // 显示遮罩层
            modal.style.display = 'block';   // 显示弹框

            const isCallConnected = await new Promise((resolve) => {
                document.getElementById('confirmYes').onclick = () => {
                    overlay.style.display = 'none'; // 隐藏遮罩层
                    modal.style.display = 'none';   // 隐藏弹框
                    resolve(true);
                };
                document.getElementById('confirmNo').onclick = () => {
                    overlay.style.display = 'none'; // 隐藏遮罩层
                    modal.style.display = 'none';   // 隐藏弹框
                    resolve(false);
                };
            });

            // 修改 dialPhonesFromIndex 函数中的相关部分
            if (!isCallConnected) {
                console.log(`${phone} 未接通`);
                // saveIntendedCustomers(selectedDatas[i], "未接通");

                // 即使电话未接通，也询问是否发送短信
                modalMessage.textContent = `${phone} 未接通,是否发送短信？`;
                overlay.style.display = 'block';
                modal.style.display = 'block';

                const isSended = await new Promise((resolve) => {
                    document.getElementById('confirmYes').onclick = () => {
                        overlay.style.display = 'none';
                        modal.style.display = 'none';
                        resolve(true);
                    };
                    document.getElementById('confirmNo').onclick = () => {
                        overlay.style.display = 'none';
                        modal.style.display = 'none';
                        resolve(false);
                    };
                });

                if (isSended) {
                    localStorage.setItem("drfpSelectedDatas", JSON.stringify(selectedDatas[i]));
                    saveIsSend();
                    return; // 结束当前函数执行，等待页面返回后继续
                    // continue;
                }

                continue; // 跳过后续逻辑，继续下一个号码
            } else {
                // saveIntendedCustomers(selectedDatas[i], "已接通");
                console.log(`${phone} 已接通`);
            }


            // 弹出第二个弹框：是否是意向客户
            modalMessage.textContent = `${phone} 是意向客户？`;
            overlay.style.display = 'block'; // 显示遮罩层
            modal.style.display = 'block';   // 显示弹框

            const isInterested = await new Promise((resolve) => {
                document.getElementById('confirmYes').onclick = () => {
                    overlay.style.display = 'none'; // 隐藏遮罩层
                    modal.style.display = 'none';   // 隐藏弹框
                    resolve(true);
                };
                document.getElementById('confirmNo').onclick = () => {
                    overlay.style.display = 'none'; // 隐藏遮罩层
                    modal.style.display = 'none';   // 隐藏弹框
                    resolve(false);
                };
            });

            if (isInterested) {
                // saveIntendedCustomers(selectedDatas[i], "已接通,是意向客户");
                console.log(`${phone} 被标记为意向客户`);
            } else {
                // saveIntendedCustomers(selectedDatas[i], "已接通,不是意向客户");
                console.log(`${phone} 不是意向客户`);
            }
        }
    }
    // 所有号码拨号完成后提示用户拨打完毕
    // alert("所有电话号码已拨打完毕！");
    // 所有号码拨打完毕
    localStorage.setItem("dialingCompleted", "true");
    localStorage.removeItem("currentDialIndex");
    alert("所有电话号码已拨打完毕！");
}

function saveIntendedCustomers(selectedDatas, type1) {
    $.ajax({
        url: basePath + "ea/cRMShortMessagingTemplatePO/sajax_ea_saveIntendedCustomers.jspa?", // 替换为实际的后端URL
        type: "post",
        async: true,
        dataType: "json",
        data: {
            "selectedData": JSON.stringify(selectedDatas),
            "type1": type1,
        },
        success: function cbf(data) {
            layer.msg("保存成功");
            selectedData = [];
        },
        error: function cbf(data) {
            layer.msg("保存失败");
        }
    });
}

//点击批量设置
$(document).on("click", ".screen", function (event) {
    let selectedDatas1 = [];
    $(".data-ul").each(function () {
        // 获取姓名
        const name = $(this).find(".push-li1").text();
        // 获取 staffId
        const staffId = $(this).attr("id");
        // 获取电话号码
        const phone = $(this).find(".push-li3").attr("phone");

        // // 获取公司ID（如果需要）
        // const companyId = $(this).find(".push-li4").attr("companyId");
        // 创建一个对象，包含姓名和号码
        const data = {
            name: name,
            phone: phone,
            // companyId: companyId,
            staffId: staffId
        };
        // 将对象添加到数组中
        selectedDatas1.push(data);
    });
    localStorage.setItem("selectedDatas1", JSON.stringify(selectedDatas1));
    localStorage.removeItem("selectedDatas2");
    localStorage.removeItem("selectedDatas3");
    document.location.href = basePath + "page/WFJClient/pc/5l5C/office/config/setAutomaticDialing.jsp?type=all";
});

function getParameterByName(name) {
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    const regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(window.location.search);
    return results ? (results[2] ? decodeURIComponent(results[2].replace(/\+/g, " ")) : "") : "";
}

function getSelectImportContacts() {
    const param = new Array();
    let codeList;
    param.push("pageNumber=" + pageNumber);
    param.push("&&pageSize=" + pageSize);
    const url = basePath + "ea/importContacts/sajax_ea_selectImportContacts.jspa?" + param.join("");
    $.ajax({
        url: encodeURI(url),
        type: "get",
        async: true,
        dataType: "json",
        success: function cbf(data) {
            codeList = eval("(" + data + ")");
            if (codeList == null) {
                $(".data-list").html("暂无数据");
                $(".data-list").css({"display": "flex", "align-items": "center", "justify-content": "center"});
            } else {
                let counter = 1; // 初始化计数器
                const list = codeList.list;
                pageCount = codeList["pageCount"];
                const length = list.length;
                const htmlstr = new Array();
                // let name= "";
                for (let i = 0; i < length; i++) {
                    const data = {
                        staffId: list[i][8],
                        name: list[i][5],
                        phone: list[i][4]
                    };
                    ImportContacts.push(data);
                    htmlstr.push("<ul id='" + list[i][8] + "' cosid='" + list[i][8] + "' class='data-ul data-ul-" + list[i][8] + "'>");
                    htmlstr.push("<li style='text-align: center;'>" + counter + "</li>"); // 使用计数器
                    htmlstr.push("<li name='" + list[i][5] + "' class='push-li1'>" + list[i][5] + "</li>");
                    htmlstr.push("<li phone='" + list[i][4] + "' class='push-li3'>" + list[i][4] + "</li>");
                    htmlstr.push("<li class='selected'>取消</li>");
                    htmlstr.push("</ul>");
                    counter++; // 递增计数器
                }
                localStorage.setItem("ImportContacts", JSON.stringify(ImportContacts));
                const moreData = document.getElementById('more-data');
                if (moreData != null) {
                    moreData.remove()
                }
                if (pageNumber > 1) {
                    $(".data-list").append(htmlstr.join(""));
                } else {
                    $(".data-list").html(htmlstr.join(""));
                }
                $(".data-list").css("display", "block");
            }
            scrollBool = true;
        },
    });
}

function saveIsSend() {
    // 发送短信逻辑 - 保存当前拨打进度到 localStorage
    localStorage.setItem("dialingCompleted", "false");
    // 跳转到发送短信页面
    document.location.href = basePath + "page/pc/5L5C/marketing/visitorMessagesSendNew.jsp?type=drfp";
}

// 新增函数：从指定索引开始拨打
// 修改 dialPhonesFromIndex 函数
async function dialPhonesFromIndex(selectedDatas) {
    // 不要一开始就设置为 false，而是检查当前状态
    const currentIndex = parseInt(localStorage.getItem("currentDialIndex")) || 0;

    // 检查是否有未完成的拨号任务或者是否是新的拨号任务
    if (currentIndex < selectedDatas.length) {
        if (!isMobileDevice()) {
            alert("当前设备不支持拨号功能。以下是所有电话号码：\n" + selectedDatas.map(d => d.phone).join("\n"));
            return;
        }

        // 从指定索引开始拨打
        for (let i = currentIndex; i < selectedDatas.length; i++) {
            const phone = selectedDatas[i].phone;
            console.log(`开始拨号: ${phone}`);

            try {
                // 触发拨号
                window.location.href = `tel:${phone}`;

                // 等待页面重新获得焦点
                await new Promise((resolve) => {
                    const focusHandler = () => {
                        console.log(`页面重新获得焦点，拨号可能已完成: ${phone}`);
                        window.removeEventListener('focus', focusHandler);
                        resolve();
                    };
                    window.addEventListener('focus', focusHandler);

                    // // 添加超时机制，避免无限等待
                    // setTimeout(() => {
                    //     window.removeEventListener('focus', focusHandler);
                    //     resolve();
                    // }, 10000); // 10秒超时
                });

                console.log(`拨号完成: ${phone}`);
                // 保存当前进度
                localStorage.setItem("currentDialIndex", i + 1);

                if (getParameterByName("type") === "drfp") {
                    // 弹出第一个弹框：是否打通电话
                    const overlay = document.getElementById('overlay');
                    const modal = document.getElementById('customModal');
                    const modalMessage = document.getElementById('modalMessage');

                    modalMessage.textContent = `${phone}是否打通？`;
                    overlay.style.display = 'block';
                    modal.style.display = 'block';

                    const isCallConnected = await new Promise((resolve) => {
                        document.getElementById('confirmYes').onclick = () => {
                            overlay.style.display = 'none';
                            modal.style.display = 'none';
                            resolve(true);
                        };
                        document.getElementById('confirmNo').onclick = () => {
                            overlay.style.display = 'none';
                            modal.style.display = 'none';
                            resolve(false);
                        };
                    });

                    if (!isCallConnected) {
                        console.log(`${phone} 未接通`);
                        saveIntendedCustomers(selectedDatas[i], "未接通");

                        // 即使电话未接通，也询问是否发送短信
                        modalMessage.textContent = `${phone} 未接通,是否发送短信?`;
                        overlay.style.display = 'block';
                        modal.style.display = 'block';

                        const isSended = await new Promise((resolve) => {
                            document.getElementById('confirmYes').onclick = () => {
                                overlay.style.display = 'none';
                                modal.style.display = 'none';
                                resolve(true);
                            };
                            document.getElementById('confirmNo').onclick = () => {
                                overlay.style.display = 'none';
                                modal.style.display = 'none';
                                resolve(false);
                            };
                        });

                        if (isSended) {
                            localStorage.setItem("drfpSelectedDatas", JSON.stringify(selectedDatas[i]));
                            saveIsSend();
                            return;
                        }
                        continue; // 跳过后续逻辑，继续下一个号码
                    } else {
                        saveIntendedCustomers(selectedDatas[i], "已接通");
                        console.log(`${phone} 已接通`);
                    }


                    // 弹出第二个弹框：是否是意向客户
                    modalMessage.textContent = `${phone} 是意向客户？`;
                    overlay.style.display = 'block';
                    modal.style.display = 'block';

                    const isInterested = await new Promise((resolve) => {
                        document.getElementById('confirmYes').onclick = () => {
                            overlay.style.display = 'none';
                            modal.style.display = 'none';
                            resolve(true);
                        };
                        document.getElementById('confirmNo').onclick = () => {
                            overlay.style.display = 'none';
                            modal.style.display = 'none';
                            resolve(false);
                        };
                    });

                    if (isInterested) {
                        saveIntendedCustomers(selectedDatas[i], "已接通,是意向客户");
                        console.log(`${phone} 被标记为意向客户`);
                    } else {
                        saveIntendedCustomers(selectedDatas[i], "已接通,不是意向客户");
                        console.log(`${phone} 不是意向客户`);
                    }

                    // 弹出第三个弹框：是否发送短信
                    modalMessage.textContent = `${phone} 是否需要发送短信？`;
                    overlay.style.display = 'block';
                    modal.style.display = 'block';

                    const isSended = await new Promise((resolve) => {
                        document.getElementById('confirmYes').onclick = () => {
                            overlay.style.display = 'none';
                            modal.style.display = 'none';
                            resolve(true);
                        };
                        document.getElementById('confirmNo').onclick = () => {
                            overlay.style.display = 'none';
                            modal.style.display = 'none';
                            resolve(false);
                        };
                    });

                    if (isSended) {
                        localStorage.setItem("drfpSelectedDatas", JSON.stringify(selectedDatas[i]));
                        saveIsSend();
                        return; // 结束当前函数执行，等待页面返回后继续
                    }
                }
            } catch (error) {
                console.error(`拨号过程中发生错误: ${phone}`, error);
                // 继续下一个号码而不是中断整个过程
                continue;
            }
        }

        // 所有号码拨打完毕
        localStorage.setItem("dialingCompleted", "true");
        localStorage.removeItem("currentDialIndex");
        alert("所有电话号码已拨打完毕！");
    } else {
        // 已经拨打完毕，重置状态
        localStorage.setItem("dialingCompleted", "true");
        localStorage.removeItem("currentDialIndex");
        alert("所有电话号码已拨打完毕！");
    }
}

