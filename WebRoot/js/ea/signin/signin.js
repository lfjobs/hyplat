// ======================================================================================================================
//                                                  人脸信息预览
// ======================================================================================================================
function drawPreview(url) {
    document.querySelector(".detected-face-preview-overlay").style.display = "inline";
    document.querySelector(".face-preview").src = url;
}

function clearPreview() {
    document.querySelector(".detected-face-preview-overlay").style.display = "none";
    document.querySelector(".face-preview").src = null;
}

// ======================================================================================================================
//                                                  处理检测到的人脸数据
// ======================================================================================================================
let faceEntityId;
let ossFileUrl;

function processFaceData(url) {
    return uploadFace(url)
        .then((uploadResult) => {
            if (uploadResult.score < 80) {
                log("==人脸分数小于80==");
                return Promise.reject({
                    message: "人脸识别失败，请重试。"
                });
            } else {
                ossFileUrl = uploadResult.ossFileUrl;
                return searchFace(ossFileUrl);
            }
        })
        .then(async (searchResult) => {
            // 根据照片搜索人脸信息
            log("==根据照片搜索人脸信息==");
            if (searchResult) {
                log(`searchResult -> ${searchResult}`);
                log(`entityId -> ${searchResult.entityId}`);
                log(`faceId -> ${searchResult.faceId}`);
                log(`score -> ${searchResult.score}`);
                log(`extraData -> ${searchResult.extraData}`);
                log(`entityId -> ${searchResult.entityId}`);
            }

            closeFaceRecognitionDialog();
            // 如果没有搜索到人脸信息
            if (searchResult && searchResult.score >= 0.8) {
                log("==搜索到人脸信息==");
                // 如果搜索到人脸信息，
                faceEntityId = searchResult.entityId;
                // 使用人脸信息获取加入的公司
                getJoinedCompaniesAndSign();
            } else {
                log("==未搜索到人脸信息==");
                // 无人脸信息，保存一张人脸到样本库 并且 在系统中和手机号绑定
                swal({
                    title: "提示",
                    text: "没有在系统中搜索到你的人脸信息，是否现在添加到系統人脸数据库？",
                    icon: "warning",
                    buttons: {
                        cancel: "关闭",
                        default: "添加",
                    },
                    dangerMode: true,
                })
                    .then((willDelete) => {
                        if (willDelete) {
                            // 将当前人脸ID和微分金账号绑定
                            openBindDialog();
                        }
                    });
            }
            return searchResult;
        });
    // return new Promise(async (resolve, reject) => {
    //     // 上传人脸识别到的图片到阿里云检测质量
    //     log("Upload detected face to aliyun and recognition face.");
    //     let uploadResult = await uploadFace(url)
    //         .catch((reason) => {
    //             reject(reason)
    //         });
    //
    //     if (uploadResult.score < 80) {
    //         log("==人脸分数小于80==");
    //         reject({
    //             message: "人脸识别失败，请重试。"
    //         });
    //     }
    //
    //     ossFileUrl = uploadResult.ossFileUrl;
    //
    //     // 根据照片搜索人脸信息
    //     log("==根据照片搜索人脸信息==");
    //     const searchResult = await searchFace(ossFileUrl)
    //         .catch((reason) => {
    //             reject(reason)
    //         });
    //
    //     if (searchResult) {
    //         log(`searchResult -> ${searchResult}`);
    //         log(`entityId -> ${searchResult.entityId}`);
    //         log(`faceId -> ${searchResult.faceId}`);
    //         log(`score -> ${searchResult.score}`);
    //         log(`extraData -> ${searchResult.extraData}`);
    //         log(`entityId -> ${searchResult.entityId}`);
    //     }
    //
    //     closeFaceRecognitionDialog();
    //     // 如果没有搜索到人脸信息
    //     if (searchResult && searchResult.score >= 0.8) {
    //         log("==搜索到人脸信息==");
    //         // 如果搜索到人脸信息，
    //         faceEntityId = searchResult.entityId;
    //         // 使用人脸信息获取加入的公司
    //         await getJoinedCompaniesAndSign();
    //     } else {
    //         log("==未搜索到人脸信息==");
    //         // 无人脸信息，保存一张人脸到样本库 并且 在系统中和手机号绑定
    //         swal({
    //             title: "提示",
    //             text: "没有在系统中搜索到你的人脸信息，是否现在添加到系統人脸数据库？",
    //             icon: "warning",
    //             buttons: {
    //                 cancel: "关闭",
    //                 default: "添加",
    //             },
    //             dangerMode: true,
    //         })
    //             .then((willDelete) => {
    //                 if (willDelete) {
    //                     // 将当前人脸ID和微分金账号绑定
    //                     openBindDialog();
    //                 }
    //             });
    //     }
    //     resolve(false);
    // });
}

async function uploadFace(url) {
    return new Promise(async (resolve, reject) => {
        const recognizeFaceResult = await recognizeFace(url)
            .catch((reason) => {
                log(reason)
                reject(reason);
            });
        resolve(recognizeFaceResult);
    });
}

/**
 * 验证上传的照片质量
 * @param blob
 * @returns {Promise<void>}
 */
function recognizeFace(localFileUrl) {
    return new Promise((resolve, reject) => {
        $.ajax({
            type: "POST",
            url: `${basePath}/ea/signin/sajax_recognizeFace.jspa?localFileUrl=${btoa(encodeURI(localFileUrl))}`,
            async: false,
            dataType: "json",
            success: function (response) {
                resolve(JSON.parse(response));
            },
            error: function (xhr, status, error) {
                reject(JSON.parse(xhr.responseText));
            }
        });
    });
}

function addFace(ossFileUrl) {
    return new Promise((resolve, reject) => {
        $.ajax({
            type: "POST",
            url: `${basePath}ea/signin/sajax_addFace.jspa?ossFileUrl=${btoa(ossFileUrl)}`,
            async: false,
            dataType: "json",
            success: function (response) {
                resolve(JSON.parse(response));
            },
            error: function (xhr, status, error) {
                reject(JSON.parse(xhr.responseText));
            }
        });
    });
}

function searchFace(ossFileUrl) {
    return new Promise((resolve, reject) => {
        $.ajax({
            type: "POST",
            url: `${basePath}ea/signin/sajax_searchFace.jspa?ossFileUrl=${btoa(ossFileUrl)}`,
            async: false,
            dataType: "json",
            success: function (response) {
                resolve(JSON.parse(response));
            },
            error: function (xhr, status, error) {
                reject(JSON.parse(xhr.responseText));
            }
        });
    });
}

/**
 * ================= 获取验证码 ===================
 *
 */
let captcha;
let resendInterval;
let count = 0;

function sendCaptcha() {
    var mobile = $("#mobile").val();
    if (mobile == "") {
        swal({
            title: "提示",
            text: "手机号不能为空!",
            icon: "error",
            button: "关闭",
        });
        return false;
    }
    var reg = /^1[3|4|5|8][0-9]\d{4,8}$/;
    if (!reg.test(mobile)) {
        swal({
            title: "提示",
            text: "请输入正确11位手机号码!",
            icon: "error",
            button: "关闭",
        });
        return false;
    }
    startCountdown();
    // 发送短信
    $.ajax({
        type: "POST",
        url: basePath + "/ea/android/sajax_ea_getduanxin.jspa?pahe=" + mobile,
        async: false,
        dataType: "json",
        success: function (data) {
            log(data)
            captcha = data.returna;
        },
        error: function (xhr, status, error) {
            swal({
                title: "提示",
                text: "验证码发送失败，请稍后再试！",
                icon: "error",
                button: "关闭",
            });
        }
    });
}

function startCountdown() {
    if (resendInterval) {
        clearInterval(resendInterval);
    }

    count = 60
    resendInterval = setInterval(resendCountdown, 1000);
}

function resendCountdown() {
    const sendButton = document.getElementById("sendCaptchaButton");

    if (count < 1) {
        clearInterval(resendInterval);
        sendButton.innerText = "获取验证码"
        return;
    }

    sendButton.innerText = `${count}`;

    count = count - 1;
}

/**
 * 绑定人脸Id和微分金手机号
 * 1、上传样本
 * 2、绑定样本ID和账号
 */
async function submitBind() {
    var mobile = $("#mobile").val();
    if (mobile == "") {
        swal({
            title: "提示",
            text: "手机号不能为空！",
            icon: "error",
            button: "关闭",
        });
        return false;
    }

    var code = $("#captcha").val();
    log(code + "-" + captcha)
    if (code == "") {
        swal({
            title: "提示",
            text: "请填写验证码！",
            icon: "error",
            button: "关闭",
        });
        return false;
    } else if (code !== captcha) {
        swal({
            title: "提示",
            text: "验证码不正确！",
            icon: "error",
            button: "关闭",
        });
        return false;
    }

    // 添加样本库
    addFace(ossFileUrl)
        .then((addResult)=>{
            log("==创建样本库，上传人脸数据==");
            log(addResult.entityId);
            log(addResult.faceId);
            log(addResult.qualitieScore);
            // 设置当前人脸识别后的faceId
            faceEntityId = addResult.entityId;

            log("==绑定样本ID到手机号==");
            return bindFaceEntityId(faceEntityId, mobile);
        })
        .then((bindResult) => {
            console.log(bindResult);
            log("==关闭绑定窗口==");
            closeBindDialog();

            // 选择加入的公司并签到
            log("==选择加入的公司并签到==");
            return getJoinedCompaniesAndSign();
        })
        .catch((reason) => {
            openFailedDialog(reason);
        });
}

function bindFaceEntityId(faceEntityId, mobile) {
    return new Promise((resolve, reject) => {
        $.ajax({
            url: `${basePath}ea/signin/sajax_bindFaceEntityId.jspa`,
            type: "post",
            data: {
                faceEntityId: faceEntityId,
                mobile: mobile
            },
            dataType: "json",
            async: false,
            success: function (response) {
                log("人脸信息绑定数字地球账号成功!")
                speechOutputForAndroid("人脸信息绑定数字地球账号成功！");
                resolve(response);
            },
            error: function (xhr, status, error) {
                const errorData = JSON.parse(xhr.responseText);
                reject(errorData);
            }
        });
    });
}

// ======================================================================================================================
//                                                  选择签到公司
// ======================================================================================================================
let companies = []

function getJoinedCompaniesAndSign() {
    getCompanies()
        .then((data) => {
            companies = data;
            if (companies.length === 1) {
                log("只有一个公司不用选择公司签到");
                selectedCompany = companies[0];
                signIn(faceEntityId);
            } else {
                log("选择一个公司签到");
                openCompaniesPickerDialog();
            }
        });
}

function getCompanies() {
    const url = `${basePath}ea/signin/sajax_findCompanies.jspa?faceEntityId=${faceEntityId}`;
    return fetch(url, {
        method: "get"
    })
        .then((response) => response.json())
        .catch((error) => {
            swal({
                title: "提示",
                text: "获取签到公司信息失败，请重试!",
                icon: "error",
                button: "关闭",
            });
            closeAll();
        });
}


// ======================================================================================================================
//                                                  签到
// ======================================================================================================================

/**
 * ================= 完成签到 ===================
 * @param account
 * @param companyId
 */
function signIn(faceEntityId) {
    const {companyId, companyName} = selectedCompany;
    $.ajax({
        url: `${basePath}ea/signin/sajax_signIn.jspa?faceEntityId=${faceEntityId}&companyId=${companyId}`,
        type: "get",
        dataType: "json",
        async: false,
        success: function (data) {
            var signDate = data.signDate;
            var signCount = data.signCount;
            var staffName = data.staffName;
            try {
                // 打开成功页面
                const signResult = {staffName, signCount, companyName: companyName, signDate};
                openSuccessDialog(signResult);
                speechOutputForAndroid("签到成功");
            } catch (error) {
                log("语音播放失败");
            }
        }
    });
}

// ============================================================================================
//                                      窗口操作
// ============================================================================================

// 开大人脸识别窗口
function openFaceRecognitionDialog(url) {
    var dialog = document.getElementById('faceRecognitionDialog');
    drawPreview(url);
    dialog.showModal();
}

// 开启人脸识别窗口
function closeFaceRecognitionDialog() {
    var dialog = document.getElementById('faceRecognitionDialog');
    // 清除Dialog中的控件
    clearPreview();
    // 清除定时任务
    dialog.close();
}

// 打开绑定手机号窗口
function openBindDialog() {
    var dialog = document.getElementById('bindDialog');
    dialog.showModal();
}

function closeBindDialog() {
    var dialog = document.getElementById('bindDialog');
    dialog.close();
}

let selectedCompany;

// 打开公司选择窗口
function openCompaniesPickerDialog() {
    var dialog = document.getElementById('companiesPickerDialog');
    dialog.showModal();

    renderCompanyList();
}

function closeCompaniesPickerDialog() {
    var dialog = document.getElementById('companiesPickerDialog');
    dialog.close();
}

function renderCompanyList() {
    var dialog = document.getElementById('companiesPickerDialog');

    selectCompany(0)

    const list = [];
    companies.map((company, index, array) => {
        const item = `
            <div class="company-item ${company.isActive ? 'active' : ''}" onclick="onCompanySelect('${index}')">
                ${company.companyName}
            </div>
        `;
        list.push(item);
    });

    dialog.querySelector(".content").innerHTML = `<div class="company-wrapper">
                <div class="company-list">${list.join("")}</div>
                <button type="button" onclick="submitAttendanceSignIn()">确认签到</button>
            </div>`;
}

function onCompanySelect(index) {
    selectCompany(index)
    renderCompanyList();
}

function submitAttendanceSignIn() {
    if (!selectedCompany) {
        swal({
            title: "提示",
            text: "请选择要签到的公司!",
            icon: "error",
            button: "关闭",
        });
        return;
    }
    closeCompaniesPickerDialog();
    const {account, companyId} = selectedCompany;
    signIn(faceEntityId, companyId);
}

function selectCompany(index) {
    for (let i = 0; i < companies.length; i++) {
        companies[i].isActive = false
    }
    companies[index].isActive = true
    selectedCompany = companies[index]
}

var closeTimerId

// 打开签到成功窗口
function openSuccessDialog(signResult) {
    console.log("打开签到成功窗口")
    var dialog = document.getElementById('successDialog');
    dialog.showModal();

    const {staffName, signCount, companyName, signDate} = signResult;

    dialog.querySelector(".content").innerHTML = `
        <img class="big-icon" src="${basePath}images/home/img-2.png"/>
        <div class="list">
            <div class="item"><div class="title">签到人：&nbsp;&nbsp;&nbsp;&nbsp;</div><div class="subtitle">${staffName}</div></div>
            <div class="item"><div class="title">今日签到：</div><div class="subtitle">${signCount + 1}次</div></div>
            <div class="item"><div class="title">签到公司：</div><div class="subtitle"> ${companyName} </div></div>
            <div class="item"><div class="title">签到时间：</div><div class="subtitle"> ${signDate}</div></div>
        </div>
    `
    closeTimerId = setTimeout(() => {
        closeAll();
    }, 5000)
}

function closeSuccessDialog() {
    var dialog = document.getElementById('successDialog');
    dialog.close();
}

// 打开签到失败窗口
function openFailedDialog(reason) {
    var dialog = document.getElementById('failedDialog');
    dialog.querySelector(".subtitle").innerText = reason.message;
    dialog.showModal();

    closeTimerId = setTimeout(() => {
        closeAll();
    }, 5000);
}

function closeFailedDialog() {
    var dialog = document.getElementById('failedDialog');
    dialog.querySelector(".subtitle").innerText = "";
    dialog.close();
}

function closeAll() {
    closeFaceRecognitionDialog();
    closeBindDialog();
    closeCompaniesPickerDialog();
    closeSuccessDialog();
    closeFailedDialog();
}

function clearTimer() {
    if (closeTimerId) {
        clearTimeout(closeTimerId)
    }
}

//
function speechOutputForAndroid(text) {
    try {
        Android.speechOutputForAndroid(text);
    } catch (e) {
        log(e)
    }
}

// 展示当前时间
function showRealTime() {
    const formatedDate = dayjs().format('YYYY年MM月DD日 dddd H:mm:ss') // '25/01/2019'
    document.querySelector(".date").innerText = formatedDate;
    setInterval(showRealTime, 1000);
}

// 添加加载完成后事件
document.addEventListener("DOMContentLoaded", async (event) => {
    showRealTime();
    const signinButton = document.querySelector('#signface');
    signinButton.addEventListener('click', (event) => {
        try {
            Android.showFaceDetectionDialog();
            // onFaceDetected(JSON.stringify({
            //     url: "https://impf2010.com/upload_files/android/signface/c0de6ad3-043f-4866-bc85-7ec52a9be11d.jpg"
            // }));
        } catch (e) {
            swal({
                title: "提示",
                text: "人脸识别启动失败!",
                icon: "error",
                button: "关闭",
            });
        }
    });
});

function onFaceDetected(data) {
    const {url} = JSON.parse(data);
    // 处理检测到的人脸数据
    log("Processing face data ->");
    openFaceRecognitionDialog(url);
    clearTimer();
    setTimeout(async () => {
        await processFaceData(url).catch((reason) => {
            openFailedDialog(reason);
        });
    }, 100);

}

// helper function to print strings to html document as a log
function log(...txt) {
    console.log(...txt); // eslint-disable-line no-console
    const div = document.getElementById('log');
    if (div) div.innerHTML = `${txt}`;
}