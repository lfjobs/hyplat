var videoFrames = new Array()
var selectedFrame
var selectedFile

$(function () {

    /**
     * 创建一个上传对象
     * 使用 UploadAuth 上传方式
     */
    function createUploader() {
        var uploader = new AliyunUpload.Vod({
            timeout: $('#timeout').val() || 60000,
            partSize: $('#partSize').val() || 1048576,
            parallel: $('#parallel').val() || 5,
            retryCount: $('#retryCount').val() || 3,
            retryDuration: $('#retryDuration').val() || 2,
            region: $('#region').val(),
            userId: $('#userId').val(),
            localCheckpoint: true, //此参数是禁用服务端缓存
            // 添加文件成功
            addFileSuccess: function (uploadInfo) {
                console.log('addFileSuccess')
                $('#authUpload').attr('disabled', false)
                $('#resumeUpload').attr('disabled', false)
                $('#status').text('添加文件成功, 等待上传...')
                console.log("addFileSuccess: " + uploadInfo.file.name)
            },
            // 开始上传
            onUploadstarted: function (uploadInfo) {
                // 如果是 UploadAuth 上传方式, 需要调用 uploader.setUploadAuthAndAddress 方法
                // 如果是 UploadAuth 上传方式, 需要根据 uploadInfo.videoId是否有值，调用点播的不同接口获取uploadauth和uploadAddress
                // 如果 uploadInfo.videoId 有值，调用刷新视频上传凭证接口，否则调用创建视频上传凭证接口
                // 注意: 这里是测试 demo 所以直接调用了获取 UploadAuth 的测试接口, 用户在使用时需要判断 uploadInfo.videoId 存在与否从而调用 openApi
                // 如果 uploadInfo.videoId 存在, 调用 刷新视频上传凭证接口(https://help.aliyun.com/document_detail/55408.html)
                // 如果 uploadInfo.videoId 不存在,调用 获取视频上传地址和凭证接口(https://help.aliyun.com/document_detail/55407.html)
                if (!uploadInfo.videoId) {
                    var title = $("#titleName").val()
                    var fileName = selectedFile.name;
                    var fileSize = selectedFile.size;
                    var createUrl = `${basePath}/ea/dsp/sajax_ea_getVideoUploadInfo.jspa`;
                    $.ajax({
                        url: createUrl,
                        type: "post",
                        dataType: "json",
                        async: false,
                        data: {
                            title: title,
                            fileName: fileName,
                            fileSize: fileSize
                        },
                        success: function (data) {
                            var uploadAuth = data.uploadAuth
                            var uploadAddress = data.uploadAddress
                            var videoId = data.videoId
                            uploader.setUploadAuthAndAddress(uploadInfo, uploadAuth, uploadAddress, videoId)
                        }
                    })
                    $('#status').text('文件开始上传...')
                    console.log("onUploadStarted:" + uploadInfo.file.name + ", endpoint:" + uploadInfo.endpoint + ", bucket:" + uploadInfo.bucket + ", object:" + uploadInfo.object)
                } else {
                    // 如果videoId有值，根据videoId刷新上传凭证
                    // https://help.aliyun.com/document_detail/55408.html?spm=a2c4g.11186623.6.630.BoYYcY
                    var refreshUrl = 'https://demo-vod.cn-shanghai.aliyuncs.com/voddemo/RefreshUploadVideo?BusinessType=vodai&TerminalType=pc&DeviceModel=iPhone9,2&UUID=59ECA-4193-4695-94DD-7E1247288&AppVersion=1.0.0&Title=haha1&FileName=xxx.mp4&VideoId=' + uploadInfo.videoId
                    $.get(refreshUrl, function (data) {
                        var uploadAuth = data.UploadAuth
                        var uploadAddress = data.UploadAddress
                        var videoId = data.VideoId
                        uploader.setUploadAuthAndAddress(uploadInfo, uploadAuth, uploadAddress, videoId)
                    }, 'json')
                }
            },
            // 文件上传成功
            onUploadSucceed: function (uploadInfo) {
                console.log("onUploadSucceed: " + uploadInfo.file.name + ", endpoint:" + uploadInfo.endpoint + ", bucket:" + uploadInfo.bucket + ", object:" + uploadInfo.object)
                publishVideo(uploadInfo.videoId)
                $('#status').text('文件上传成功!')
            },
            // 文件上传失败
            onUploadFailed: function (uploadInfo, code, message) {
                console.log("onUploadFailed: file:" + uploadInfo.file.name + ",code:" + code + ", message:" + message)
                $('#status').text('文件上传失败!')
                $("#upload-dialog").hide();
            },
            // 取消文件上传
            onUploadCanceled: function (uploadInfo, code, message) {
                console.log("Canceled file: " + uploadInfo.file.name + ", code: " + code + ", message:" + message)
                $('#status').text('文件上传已暂停!')
            },
            // 文件上传进度，单位：字节, 可以在这个函数中拿到上传进度并显示在页面上
            onUploadProgress: function (uploadInfo, totalSize, progress) {
                console.log("onUploadProgress:file:" + uploadInfo.file.name + ", fileSize:" + totalSize + ", percent:" + Math.ceil(progress * 100) + "%")
                var progressPercent = Math.ceil(progress * 100)
                $('#auth-progress').text(`${progressPercent}%`);
                $('#status').text('文件上传中...')
            },
            // 上传凭证超时
            onUploadTokenExpired: function (uploadInfo) {
                // 上传大文件超时, 如果是上传方式一即根据 UploadAuth 上传时
                // 需要根据 uploadInfo.videoId 调用刷新视频上传凭证接口(https://help.aliyun.com/document_detail/55408.html)重新获取 UploadAuth
                // 然后调用 resumeUploadWithAuth 方法, 这里是测试接口, 所以我直接获取了 UploadAuth
                $('#status').text('文件上传超时!')

                let refreshUrl = 'https://demo-vod.cn-shanghai.aliyuncs.com/voddemo/RefreshUploadVideo?BusinessType=vodai&TerminalType=pc&DeviceModel=iPhone9,2&UUID=59ECA-4193-4695-94DD-7E1247288&AppVersion=1.0.0&Title=haha1&FileName=xxx.mp4&VideoId=' + uploadInfo.videoId
                $.get(refreshUrl, function (data) {
                    var uploadAuth = data.UploadAuth
                    uploader.resumeUploadWithAuth(uploadAuth)
                    console.log('upload expired and resume upload with uploadauth ' + uploadAuth)
                }, 'json')
            },
            // 全部文件上传结束
            onUploadEnd: function (uploadInfo) {
                $('#status').text('文件上传完毕!')
                console.log("onUploadEnd: uploaded all the files")
            }
        })
        return uploader
    }

    var uploader = null

    $("#select-file").on("click", function (e) {
        $("#video-input").val("")
        $("#video-input").click();
    });

    $("#video-input").on('change', async function (e) {
        if (e.target.files.length > 0) {
            try {
                selectedFile = e.target.files[0];

                // var video = document.createElement("video");
                // video.currentTime = 1;
                // video.muted = true;
                // video.autoplay = true;
                // video.playsInline = true;
                // video['webkit-playsinline'] = true;
                // video.addEventListener('loadeddata', function () {
                //     const cvs = document.createElement("canvas");
                //     const ctx = cvs.getContext("2d");
                //
                //     cvs.width = video.videoWidth;
                //     cvs.height = video.videoHeight;
                //     ctx.drawImage(video, 0, 0, cvs.width, cvs.height);
                //     cvs.toBlob((blob) => {
                //         document.getElementById("cover-preview").src = URL.createObjectURL(blob);
                //     });
                // });
                //
                // video.src = URL.createObjectURL(selectedFile);
                // video.play() // .then(() => video.pause());

                const coverData = await getVideoCover(selectedFile);
                document.getElementById("cover-preview").src = coverData.url;

                var userData = '{"Vod":{}}';
                if (uploader) {
                    uploader.stopUpload();
                }
                uploader = createUploader();
                uploader.addFile(selectedFile, null, null, null, userData);
            } catch (e) {
                alert(JSON.stringify(e))
            }
        }

        // if (e.target.files.length > 0) {
        //     try {
        //         $("#cover-picker-dialog").show()
        //         selectedFile = e.target.files[0]
        //
        //         let successed = 0;
        //         const frameCount = 10;
        //
        //         captureFrames(frameCount, () => {
        //             successed = successed + 1;
        //             console.log(`${100 * (successed / frameCount)}%`)
        //             $(".progress-bar").css("width", `${100 * (successed / frameCount)}%`);
        //         }).then((frames) => {
        //             createPreview(frames)
        //         });
        //     } catch (e) {
        //         alert(JSON.stringify(e))
        //     }
        // }
    });

    $(".cover-picker").on('click', 'img', function (e) {
        $(".cover-picker").children().each(function () {
            $(this).css("border", "none");
        })

        $(this).css("border", "2px red solid");

        const index = $(this).attr("data");
        selectedFrame = videoFrames[index]
        const image = document.createElement('img');
        image.style.width = "100%";
        image.style.objectFit = "fill";
        image.src = selectedFrame.url;

        $("#selected-cover").empty();
        $("#selected-cover").append(image);
    })

    $("#cover-confirm").on('click', function () {
        var userData = '{"Vod":{}}';
        if (uploader) {
            uploader.stopUpload();
        }
        uploader = createUploader();
        uploader.addFile(selectedFile, null, null, null, userData);
        document.getElementById("cover-preview").src = selectedFrame.url;

        $("#cover-picker-dialog").hide();
    })

    $("#cover-cancel").on('click', function () {
        $("#cover-picker-dialog").hide();
    })

    $('#publish-button').on('click', function () {
        $("#publish-button").attr('disabled', "disabled");
        var titleName = $("#titleName").val();

        if (titleName === undefined || titleName === "") {
            alert("请填写小视频标题");
            return;
        }

        if (selectedFile === null) {
            alert("请选择要发布的短视频");
            return;
        }

        $("#upload-dialog").show();

        // 然后调用 startUpload 方法, 开始上传
        uploader.startUpload();
    })

    $("#pick-products").on('click', function () {
        $('#products-picker').show();
        $("#products-picker-contianer").empty();
        pageNumber = 1;
        getDspProducts();
    });
});

function createPreview(frames) {
    videoFrames = frames;

    $("#selected-cover").empty();
    $(".cover-picker").empty();
    for (let i = 0; i < frames.length; i++) {
        const {blob, url} = frames[i];

        const image = document.createElement('img');
        image.style.width = "100%";
        image.style.objectFit = "fill";
        if (i === 0) {
            image.style.border = "2px red solid";
        }
        image.setAttribute("data", i);
        image.src = url;

        $(".cover-picker").append(image);

        if (i === 0) {
            const image1 = document.createElement('img');
            image.style.width = "100%";
            image1.style.objectFit = "fill";
            image.style.border = "2px red solid";
            image1.setAttribute("data", i);
            image1.src = url;
            $("#selected-cover").append(image1);
        }
    }
}

async function captureFrames(count, onProgress) {
    let total = count || 10;
    return new Promise((resolve, reject) => {
        console.log("captureFrame")
        var video = document.createElement("video");
        video.currentTime = 0;
        video.muted = true;
        video.autoplay = true;
        video.playsInline = true;
        video['webkit-playsinline'] = true;
        video.addEventListener('loadeddata', async function () {
            console.log("loadeddata")
            const frameDataList = []
            for (let i = 1; i <= total; i++) {
                const frameData = await captureFrame(video, i, onProgress);
                frameDataList.push(frameData);
            }
            resolve(frameDataList);
        });

        video.src = URL.createObjectURL(selectedFile);
        video.play() // .then(() => video.pause());
    });
}

function captureFrame(video, time, callback) {
    return new Promise(resolve => {
        video.width = video.videoWidth;
        video.height = video.videoHeight;

        video.currentTime = time;
        video.addEventListener('seeked', async function () {
            console.log("seeked:" + time)
            console.log("duration:" + video.duration)
            const frameData = await drawVideoFrame(video);
            callback();
            resolve(frameData);
        }, {once: true});
    });
}

// async function captureFrames(count, onProgress) {
//     let total = count || 10;
//     let captureTasks = [];
//     for (let i = 1; i <= total; i++) {
//         captureTasks.push(captureFrame(selectedFile, i * 2, onProgress));
//     }
//     return await Promise.all(captureTasks);
// }
//
// function captureFrame(videoFile, time, callback) {
//     return new Promise((resolve, reject) => {
//         console.log("captureFrame")
//         var video = document.createElement("video");
//         video.muted = true;
//         video.autoplay = true;
//         video.playsInline = true;
//         video['webkit-playsinline'] = true;
//
//         video.currentTime = 0;
//         video.addEventListener('loadeddata', function () {
//             console.log("loadeddata")
//             setTimeout(() => {
//                 video.width = video.videoWidth;
//                 video.height = video.videoHeight;
//
//                 video.currentTime = time;
//                 video.addEventListener('seeked', async function () {
//                     console.log("seeked")
//                     const frameData = await drawVideoFrame(video);
//                     callback();
//                     resolve(frameData);
//                 }, {once: true});
//             });
//         }, 100);
//
//         video.src = URL.createObjectURL(videoFile);
//         video.play(); // .then(() => video.pause());
//     });
// }

function drawVideoFrame(video) {
    return new Promise(resolve => {
        const cvs = document.createElement("canvas");
        const ctx = cvs.getContext("2d");

        cvs.width = video.videoWidth;
        cvs.height = video.videoHeight;

        ctx.drawImage(video, 0, 0, cvs.width, cvs.height);

        cvs.toBlob((blob) => {
            resolve({
                blob: blob,
                url: URL.createObjectURL(blob)
            });
        });
    });
}

function selectFrame() {
    const image = document.createElement('img');
    image.src = frame.url;
    $("#selected-cover").html(image);
}

function publishVideo(videoId) {
    var titleName = $("#titleName").val()
    var staffId = staffId
    var priceType = $('input[name="priceType"]:checked').val()
    var state = $('input[name="state"]:checked').val()
    var location = $("#location").val()
    var ppids = selectedProducts.map(pro => pro.goodsId).join(",");

    if (titleName === undefined || titleName === "") {
        alert("请填写小视频标题！")
        $(this).prop('disabled', false);
        return
    }

    var data = {
        "staffId": staffId,
        "video.vodID": videoId,
        "video.vodProvider": "aliyun",
        "video.titleName": titleName,
        "video.state": state,
        "pvedio.ppid": ppids,
        "priceType": priceType,
    }

    $.ajax({
        url: basePath + "/ea/dsp/sajax_ea_publishVideo.jspa",
        type: "post",
        dataType: "json",
        data: data,
        success: function (data) {
            console.log(data);
            window.location.replace(document.referrer)
        },
        error: function (error) {
            console.log(error);
            $("#vod-upload").hide()
        }
    });
}

// 视频带货相关

var selectedProducts = []

function getDspProducts() {

    if (pageNumber > totalPages) {
        return;
    }

    var priceType = $('input[name="priceType"]:checked').val();
    $.ajax({
        type: "get",
        url: basePath + `/ea/dsp/sajax_ea_getDspProducts.jspa`,
        dataType: "json",
        data: {
            pageNumber: pageNumber,
            pageSize: pageSize,
            priceType: priceType
        },
        success: function (data) {
            if (data.content.length === 0) {
                return false;
            }
            $("#loadmore-button").remove()


            var htmls = new Array();

            var goodsList = data.content;
            for (var i = 0; i < goodsList.length; i++) {
                var goods = goodsList[i];
                htmls.push(`
                        <div class="flex flex-row space-x-2 pl-2 py-2 goods-item">
                            <img class="w-20 h-20" src="${basePath + goods.image}"/>
                            <div class="flex flex-col m-auto space-y-2 grow">
                                <div>${goods.goodsName}</div>
                                <div class="text-red-700 font-bold">￥${goods.price}</div>
                            </div>
                            <input id="product-checkbox-${goods.goodsId}" class="product-check" type="checkbox" value="${$.base64Encode(JSON.stringify(goods))}"/>
                        </div>
                    `)
            }

            $("#products-picker-contianer").append(htmls.join(""));

            $('.goods-item').on('click', function () {
                $(this).find(".product-check").click()
            });

            totalPages = data.totalPages;
            pageNumber++

            if (pageNumber < totalPages) {
                $("#products-picker-contianer").append(`
                    <div id="loadmore-button" className="p-2 text-center m-auto" onClick="getDspProducts()">加载更多
                    </div>
                `);
            }
        }
    });
}

function updateCheckData() {
    $('.product-check').each(function () {
        var isChecked = $(this).is(':checked');
        if (isChecked) {
            let value = $(this).val()
            let pro = JSON.parse($.base64Decode(value));

            selectedProducts.push(pro)
        }
    });

    $('#products-picker').hide()

    refreshContainer()
}

function removeProduct(goodsId) {
    selectedProducts = selectedProducts.filter(pro => pro.goodsId !== goodsId)
    // console.log(goodsid)
    // console.log(`#product-checkbox-\${goodsid}`)
    $(`#product-checkbox-${goodsId}`).attr("checked", false)
    refreshContainer()
}

function refreshContainer() {
    $("#products-container").empty()
    selectedProducts.forEach(goods => {
        $("#products-container").append(`
            <div class="flex flex-row space-x-2 pl-2 py-2">
                <img class="w-8 h-8" src="${basePath + goods.image}"/>
                <div class="flex flex-row m-auto grow space-x-2">
                    <div class="text-small">${goods.goodsName}</div>
                    <div class="text-red-700 text-small font-bold">￥${goods.price}</div>
                </div>
                <input class="product-checked" type="checkbox" style="display: none" value="${goods.goodsId}"/>
                <button class="text-red-700 text-underline" type="button" onclick="removeProduct('${goods.goodsId}')">删除</button>
            </div>
            `);
    })
}

// 解决微信上传视频封面问题
function getVideoCover(videoFile) {
    if (!videoFile) {
        return Promise.reject('Please upload a video file.');
    }

    return new Promise((resolve, reject) => {
        const video = document.createElement('video');
        const videoUrl = URL.createObjectURL(videoFile);
        video.src = videoUrl;

        function addVideoLoadListener() {
            video.addEventListener('loadedmetadata', function () {
                setTimeout(() => {
                    try {
                        // 设置video的尺寸，确保能够获取整个视频帧
                        video.width = video.videoWidth;
                        video.height = video.videoHeight;

                        // 在视频准备好后，将currentTime设置为0尝试获取第一帧
                        video.currentTime = 0;
                        video.addEventListener('seeked', function () {
                            // 使用canvas绘制当前帧
                            const canvas = document.createElement('canvas');
                            canvas.width = video.videoWidth;
                            canvas.height = video.videoHeight;

                            const ctx = canvas.getContext('2d');
                            ctx.drawImage(video, 0, 0, canvas.width, canvas.height);

                            canvas.toBlob((blob) => {
                                resolve({
                                    blob: blob,
                                    url: URL.createObjectURL(blob)
                                });
                            });

                            // 释放视频URL对象
                            URL.revokeObjectURL(videoUrl);
                        }, {once: true});
                    } catch (err) {
                        reject(err);
                    }
                }, 100);
            });
        }

        // 解决微信浏览器下，ios上传视频，出现的问题
        if (window.WeixinJSBridge && /(iPhone|iPad|iPod|iOS)/i.test(navigator.userAgent)) {
            window.WeixinJSBridge.invoke('getNetworkType', {}, () => {
                video.muted = true;
                video.playsInline = true;
                video['webkit-playsinline'] = true;
                addVideoLoadListener()
                video.play().then(() => video.pause());
            });
        } else {
            addVideoLoadListener();
        }
    });
}

function base64ToBlob(base64, mimeType) {
    const byteString = atob(base64);
    const ab = new ArrayBuffer(byteString.length);
    const ia = new Uint8Array(ab);

    for (let i = 0; i < byteString.length; i++) {
        ia[i] = byteString.charCodeAt(i);
    }

    const blob = new Blob([ab], {type: mimeType});
    return blob;
}