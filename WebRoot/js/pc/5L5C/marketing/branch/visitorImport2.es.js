import {createApp, nextTick} from '/js/pc/5L5C/marketing/petite-vue.es.js'
import {getCookie, readerFile} from "/js/pc/5L5C/marketing/stdlib.js";
import {read, utils} from "/js/pc/5L5C/marketing/xlsx.es.js";
import {sendAxiosPost, setHeaderTitle} from '/js/pc/5L5C/marketing/store.es.js'

setHeaderTitle('导入粉丝');
// setHeaderTitle('导入数据');
//   var path = request.getContextPath();
//  var basePath1 = request.getScheme() + "://"
//     + request.getServerName() + ":" + request.getServerPort()
//     + path + "/";
// var basePath = "<%=basePath1%>";
// var companyID = "${param.companyID}";
// var staffID = "${param.staffID}";

const elMain = document.getElementById('main');
const elTemplate = document.getElementById('template');
const path = basePath + "ea/crmCustomerPO/ea_saveImportDataPo.jspa";
const path1 = basePath + "ea/crmCustomerPO/sajax_ea_saveImportDataPo.jspa";
console.log('路劲' + path1);
const urlPath = {
    // 修改地方   全路径   后端解析字符串  前端穿入json字符串
    batch: path1
    // batch:"/crm/controller/customer/batch"

    // var url = "ea/trilateral/ea_importData.jspa";
    // window.location.href = basePath + url;
}

let mainApp = {
    showUploadExcel: true,
    showExcelTable: false,
    showExcelHint: false,
    table: {
        fileName: '',
        sheetList: [],
        sheetIndex: 0,
        sheetAt: []
    },
    elMainHeight: (elMain.clientHeight - 1),
    uploadTimer: 0,
    requestMessage: "",
    onMounted: function () {
        mainApp = this;
    },
    eventReaderFile: function (event) {
        // 获取事件被分派到的对象
        const eventTarget = event.target;

        // 判断是否包含文件
        if (eventTarget.files !== null && eventTarget.files.length === 1) {
            // 获取到需要 input 选中的文件
            let fileMata = getFileMata(eventTarget.files[0]);
            if (fileMata.extension === 'xlsx' || fileMata.extension === 'xls') {
                // 获取文件内的数据
                readerFile(eventTarget.files[0]).then((fileData) => {
                    fileMata.data = fileData;
                }).then(() => {
                    let excelTableMate = toExcelTable(fileMata);
                    mainApp.table.fileName = excelTableMate.fileName;
                    mainApp.table.sheetList = excelTableMate.sheetList;
                    mainApp.table.sheetIndex = excelTableMate.sheetIndex;
                    mainApp.table.sheetAt = mainApp.table.sheetList[mainApp.table.sheetIndex];

                    // 数据初始化完毕后再显示元素
                    mainApp.showUploadExcel = false;
                    mainApp.showExcelTable = true;
                    nextTick(() => {
                        mainApp.$refs['tableBox'].style.height = mainApp.elMainHeight + 'px';
                    })
                })

            }
        }
    },
    eventButtonTable: function (type) {
        if (type === 'confirm') {
            // 确定
            mainApp.showExcelTable = false;
            mainApp.showExcelHint = true;

            // 获取发送请求时附带的数据
            let requestData = toExcelSheetDTO(mainApp.table.sheetAt);

            // 启动计时器，显示上传用时
            let interval = setInterval(() => {
                mainApp.uploadTimer++;
            }, 1000)

            sendAxiosPost(urlPath.batch, requestData).then(response => {
                // let records = response.data.data;
                // let failedRecords = records['failedRecords'];
                // let successfulRecords = records['successfulRecords'];
                //  mainApp.requestMessage = `数据上传成功 ${successfulRecords.length} 条;数据上传失败 ${ failedRecords.length} 条;`;
                mainApp.requestMessage = `数据上传成功`;

            }).catch((error) => {
                mainApp.requestMessage = error.message + ", 更详细的错误信息请查看控制台。";
                console.log(error)
            }).finally(() => {
                clearTimeout(interval);
            })
        } else if (type === 'cancel') {
            // 取消
            mainApp.showExcelTable = false;
            mainApp.showUploadExcel = true;
        }
    }
}

/**
 * 获取 web 文件详细信息
 * @param   file
 * @returns {{name, extension: string, mimeType, lastModified: number, data: [number]}}
 */
function getFileMata(file) {
    let index = file.name.lastIndexOf(".");
    return {
        name: file.name,
        extension: file.name.substring(index + 1).toLowerCase(),
        mimeType: file.type,
        lastModified: 0,
        data: []
    };
}

/**
 * 转换成 ExcelTable 表单对象
 * @param excelFileMata     Excel 文件信息
 * @returns {{fileName, sheetList: {sheetName: string, header: string[][], body: string[][], check: boolean[]}[], sheetIndex: number}}
 */
function toExcelTable(excelFileMata) {
    if (excelFileMata.data instanceof ArrayBuffer) {
        return {
            fileName: excelFileMata.name,
            sheetList: xlsxReaderHandler(excelFileMata.data),
            sheetIndex: 0
        }
    }
    throw new Error("excelFileMata 的数据值类型必须是 ArrayBuffer");
}

/**
 * 将 Excel 二进制数据转换成数据对象
 * @param arrayBuffer
 * @returns {[{sheetName:string,header:[string[]],body:[string[]],check:boolean[]}]}
 */
function xlsxReaderHandler(arrayBuffer) {
    const workbook = read(arrayBuffer, {type: 'array'});

    let jsonArray = [];

    for (let sheetName of workbook.SheetNames) {
        let sheetToJson = utils.sheet_to_json(workbook.Sheets[sheetName], {header: 1});
        let sheetHeader;
        if (sheetToJson.length > 0) {
            sheetHeader = [sheetToJson.shift()];
        } else {
            sheetHeader = [];
        }

        let checkArray = new Array(sheetToJson.length);
        for (let i = 0; i < sheetToJson.length; i++) {
            checkArray[i] = true;
        }

        jsonArray.push({
            "sheetName": sheetName,
            "header": sheetHeader,
            "body": sheetToJson,
            "check": checkArray
        })
    }

    return jsonArray;
}

/**
 * 将 ExcelTable 对象转换成 DTO 对象
 * @param sheet
 * @returns {[{}]}
 */
function toExcelSheetDTO(sheet) {
    let key = getCookie("key_customer");
    var list = [];
    sheet.body.filter((item, index) => {
        return sheet.check[index];
    }).forEach((item) => {
        // 创建一个符合DataDTO结构的对象
        list.push({
            "name": item[0],
            "number": item[1],
            "contact": item[2],
            "address": item[3],
            "extend": item[4],
            "social": item[5]
        });
        // 将创建的DataDTO对象添加到列表中
    });

    // 发送数据到后端的示例代码
    sendToBackend(list);

    return list;

}

function sendToBackend(dataDTOList) {
    // console.log(JSON.stringify(dataDTOList));

    $.ajax({
        url: basePath + "ea/crmCustomerPO/sajax_ea_saveImportDataPo.jspa",
        type: "POST",
        // contentType: 'application/json; charset=utf-8',
        // data: JSON.stringify(dataDTOList),
        data: {"data": JSON.stringify(dataDTOList)},
        async: false,
        success: function (data) {
            // var refreshUrl = "ea/crmCustomerPO/ea_crmCustomerPOList.jspa";
            // window.location.href = basePath + refreshUrl;
            window.location.href = basePath + "page/WFJClient/pc/5l5C/office/config/crmCustomer1.jsp";
        },
        error: function () {
        }
    });


    // fetch(basePath +"ea/trilateral/ea_saveImportData1.jspa", {
    //     method: 'POST',
    //     headers: {
    //         'Content-Type': 'application/json'
    //     },
    //     body: {dataDTOList: dataDTOList} // 假设后端期望接收名为"dataDTOs"的数组
    // })
    //     .then(response => response.json())
    //     .then(data => console.log('Success:', data))
    //     .catch((error) => console.error('Error:', error));
}


// function toExcelSheetDTO(sheet) {
//     let key = getCookie("key_customer");
//     var  list = [];
//     return sheet.body.filter((item, index) => {
//         return sheet.check[index];
//     }).map((item) => {
//         return {
//             // 修改地方
//             "name": item[0],
//             "number": item[1],
//             "contact": item[2],
//             "address": item[3],
//             "extend": item[4],
//             "customerKey": key,
//             "social": '学员',
//
//         };
//     })
// }

elMain.appendChild(elTemplate.content.cloneNode(true));
createApp(mainApp).mount('#main')

