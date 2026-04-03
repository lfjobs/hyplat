import {createApp} from '/js/pc/5L5C/marketing/petite-vue.es.js'
import {addAlert, getAxiosBaseData, sendAxiosPost, setHeaderTitle} from '/js/pc/5L5C/marketing/store.es.js'
import {calculateCharLength, getCookie, getSessionStorage} from "../stdlib.js";

setHeaderTitle('未注册粉丝 - 创建模板')

const elDropDownSelectContainer = document.getElementById("dropDownSelectContainer");
const elSelect = elDropDownSelectContainer.querySelector(".display-select")
const elSelectIcon = elDropDownSelectContainer.querySelector(".icon-svg");

let vueAPP = {
    selectSwitch: false,
    optionIndex: -1,
    optionValue: "",
    options: [
        {
            name: "通知模版",
            // key: "notification"
            key: "通知模版"
        },
        {
            name: "营销模版",
            // key: "marketing"
            key: "营销模版"
        }
    ],
    formHeadline: "",
    formMessage: "",
    formMoney: "",
    formMessageCount: 0,
    formMessageMaxLength: 200,
    onMounted: function () {
        vueAPP = this;
    },
    eventAnimationSwitch: function () {
        // 状态取反
        this.selectSwitch = !this.selectSwitch;
        if (this.selectSwitch) {
            elSelectIcon.style.transform = "rotate(270deg)";
            elSelect.style.transform = "scale(1, 1)";
        } else {
            elSelectIcon.style.transform = "";
            elSelect.style.transform = "scale(1, 0)";
        }
    },
    eventSelectOption: function (index) {
        this.optionIndex = index;
        this.optionValue = this.options[index].name;
        this.eventAnimationSwitch();
    },
    eventTextareaChange: function (ev) {
        const value = ev.target.value; // 获取最新输入的值
        this.formMessageCount = calculateCharLength(value);

        // 限制内容长度
        if (this.formMessageCount > this.formMessageMaxLength) {
            this.formMessage = truncateText(this.formMessage, this.formMessageMaxLength);
        }
    },
    eventFormConfirm: function () {
        if (this.optionIndex === -1) {
            addAlert("warning", "您还未选择短信模板类型");
        } else if (this.formHeadline.length === 0) {
            addAlert("warning", "您还未输入短信标题");
        } else if (this.formMessage.length === 0) {
            addAlert("warning", "您还未输入短信内容");
        } else if (this.formMoney.length === 0) {
            addAlert("warning", "您还未输入发送短信所需积分数");
        } else if (this.formMessageCount > this.formMessageMaxLength) {
            addAlert("warning", "请您控制短信长度在" + this.formMessageMaxLength + "字以内");
        } else {
            let requestData = getAxiosBaseData();
            requestData['templateType'] = this.options[this.optionIndex].key;
            requestData['templateHeadline'] = this.formHeadline;
            requestData['templateText'] = this.formMessage;
            requestData['money'] = this.formMoney;
            console.log('路劲' + basePath + "ea/cRMShortMessagingTemplatePO/sajax_ea_saveTemplate.jspa");
            sendAjaxRequest(requestData);
        }


        // sendAxiosPost("/crm/controller/sms/template/save", requestData).then((res) => {
        //     let resData = res.data;
        //     if (resData.success) {
        //         let message = "您的短信模板创建成功，后续您可以在短信列表中查看或编辑您创建好的短信。"
        //         document.location.replace("/page/pc/5L5C/marketing/operationTip.jsp?pattern=succeed&message=" + message)
        //     } else {
        //         addAlert("error", resData.message)
        //     }
        // })
    }
}

function sendAjaxRequest(requestData) {
    $.ajax({
        url: basePath + "ea/cRMShortMessagingTemplatePO/sajax_ea_saveTemplate.jspa",
        type: 'POST',
        // contentType: 'application/json;charset=UTF-8', // 确保服务器知道这是JSON数据
        data: {"data": JSON.stringify(requestData)}, // 直接传递 JSON 字符串
        success: function (response) {
            addAlert("success", "保存成功,您可以在短信列表中查看或编辑您创建好的短信");
            // 延迟3秒后重定向
            setTimeout(function () {
                window.location.href = basePath + "page/pc/5L5C/marketing/visitorMessagesCreate.jsp";
            }, 5000); // 3000毫秒 = 3秒
            // window.location.href = basePath + "page/pc/5L5C/marketing/visitorMessagesCreate.jsp";
        },
        error: function (xhr, status, error) {
            addAlert("error", "短信模板保存失败，请重新保存");
            setTimeout(function () {
                window.location.href = basePath + "page/pc/5L5C/marketing/visitorMessagesCreate.jsp";
            }, 2000); // 3000毫秒 = 3秒
        }
    });
}


createApp(vueAPP).mount('#main');