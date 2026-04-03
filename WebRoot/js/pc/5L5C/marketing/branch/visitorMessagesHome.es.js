import {createApp} from '/js/pc/5L5C/marketing/petite-vue.es.js'
import {getAxiosBaseData, sendAxiosPost, setHeaderTitle} from '/js/pc/5L5C/marketing/store.es.js'

setHeaderTitle('未注册粉丝 - 短信模块')


/**
 * 创建 Vue 对象
 * @param {{dataList:{}[]}} props
 */
function createVueApp(props) {
    createApp({
        smsList: props.dataList,
        eventDeleteTemplate(ev,sms) {
            let list = this.smsList;
            for (let i = 0; i < list.length; i++) {
                let item = list[i];
                if(item.id === sms.id) {
                    list.splice(i, 1);
                    deleteSMSTemplate(item.id)
                    break;
                }
            }
        },
        eventRouterPush(ev) {
            document.location.assign('/page/pc/5L5C/marketing/visitorMessagesCreate.jsp')
        }
    }).mount('#main')
}

/**
 * 删除短信模板
 */
function deleteSMSTemplate(id){
    let reqData = getAxiosBaseData();
    reqData['id'] = id;

    sendAxiosPost("/crm/controller/sms/template/delete",reqData).then((res)=>{
        console.log(res)
    })
}

sendAxiosPost("/crm/controller/sms/template/query", getAxiosBaseData())
    .then((res) => {
        let result = res.data;
        if (result.success) {
            let dataList = result.data.map(item => {
                switch (item.templateType) {
                    case 'notification':
                        item['templateTypeName'] = '通知模版';
                        break
                    case 'marketing':
                        item['templateTypeName'] = '营销模版';
                        break
                    default:
                        item['templateTypeName'] = '未知模板类型';
                }
                return item;
            });

            createVueApp({
                dataList: dataList
            })
        } else {
            // 先不处理错误情况，只处理正确的情况
            console.warn(result);
        }
    })
    .catch(err => {})