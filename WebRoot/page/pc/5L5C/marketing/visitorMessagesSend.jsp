<%@page contentType="text/html;charset=UTF-8" session="false" %>
<%
    String contextPath = request.getServletContext().getContextPath();
    String urlPrefix = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + contextPath;
    request.setAttribute("urlPrefix", urlPrefix);
%>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1,shrink-to-fit=no">
    <link rel="icon" href="${urlPrefix}/images/icon/png/favicon-32x32.png" type="image/png">
    <link rel="apple-touch-icon" sizes="48x48" href="${urlPrefix}/images/icon/png/icon-48x48.png">
    <link rel="apple-touch-icon" sizes="72x72" href="${urlPrefix}/images/icon/png/icon-72x72.png">

    <link href="${urlPrefix}/css/pc/5L5C/marketing/base.css" rel="stylesheet" type="text/css">
    <link href="${urlPrefix}/css/pc/5L5C/marketing/common.css" rel="stylesheet" type="text/css">
    <link href="${urlPrefix}/css/pc/5L5C/marketing/branchPage.css" rel="stylesheet" type="text/css">

    <script src="${urlPrefix}/js/pc/5L5C/marketing/axios.min.js" type="module"></script>
    <script src="${urlPrefix}/js/pc/5L5C/marketing/petite-vue.es.js" type="module"></script>
    <script src="${urlPrefix}/js/pc/5L5C/marketing/store.es.js" type="module"></script>
    <script src="${urlPrefix}/js/pc/5L5C/marketing/branch/visitorMessagesSend.es.js" type="module"></script>
    <title>标准模板</title>
</head>

<body>

<article class="layout-flex-column mobile-screen">
    <%@ include file="./template/layout-header.jsp" %>

    <main class="page-messages-send layout-flex-content">
        <div class="nav-list">
            <div class="nav-item">
                <div class="icon-svg">
                    <svg viewBox="0 0 1175 1024" width="40" height="40">
                        <path d="M1015.45512969 32H288.07924344a118.95981094 118.95981094 0 0 0-118.95981094 118.95981094v94.5626475h-56.73758813A81.13475156 81.13475156 0 0 0 32.00359344 327.41371156v428.74704469a80.94562687 80.94562687 0 0 0 81.13475156 81.13475156h148.27423219v154.70449219l232.81323844-143.54609906a40.09456219 40.09456219 0 0 1 27.80141812-11.34751782h318.29787281A80.94562687 80.94562687 0 0 0 920.89248219 756.16075625v-56.73758813h94.5626475A118.95981094 118.95981094 0 0 0 1134.03669031 580.46335719V150.77068531A118.95981094 118.95981094 0 0 0 1015.45512969 32zM650.44331 593.13475156A51.44208 51.44208 0 1 1 701.88539 542.63829781a51.44208 51.44208 0 0 1-51.44208 50.49645375z m-173.80614656 0A51.44208 51.44208 0 1 1 528.07924344 542.63829781a51.44208 51.44208 0 0 1-51.44208 50.49645375z m-173.80614657 0A51.44208 51.44208 0 1 1 353.51659531 542.63829781a51.44208 51.44208 0 0 1-50.68557844 50.49645375z m756.50118188-13.61702062A43.30969313 43.30969313 0 0 1 1015.45512969 622.82742312h-94.5626475V327.22458594a80.94562687 80.94562687 0 0 0-80.94562594-80.94562594H244.76955125v-94.5626475A43.49881781 43.49881781 0 0 1 288.07924344 107.65011781H1015.45512969a43.1205675 43.1205675 0 0 1 43.1205675 43.1205675z"></path>
                    </svg>
                </div>
                <div class="text">群发记录</div>
            </div>
            <div class="nav-item at-item">
                <div class="icon-svg">
                    <svg viewBox="0 0 1024 1024" width="40" height="40">
                        <path d="M463.859375 402.700625L789.2290625 119.1021875c-12.3628125-9.928125-28.096875-15.9215625-45.1434375-15.9215625H105.8965625c-19.10625 0-36.526875 7.4925-49.45125 19.85625l322.185 279.85125c23.6015625 20.2303125 61.6275 20.2303125 85.2290625-0.1875z"></path>
                        <path d="M509.0028125 505.5378125c29.596875-29.596875 64.2496875-52.8234375 102.6496875-69.12 39.8990625-16.8590625 82.0453125-25.475625 125.690625-25.475625 27.1603125 0 53.9465625 3.3721875 79.79625 9.928125v-230.775l-2.060625 1.6865625c-1.498125 1.310625-3.55875 2.0596875-5.244375 2.99625L506.380625 457.95875c-47.2040625 41.023125-123.6290625 41.023125-170.833125 0L32.8428125 195.153125v450.496875c0 1.8740625 0.1875 5.4328125 0.1875 5.4328125-0.1875 33.3421875 33.7171875 69.12 72.67875 69.12h309.073125c1.6865625-38.775 10.115625-76.425 25.100625-112.2028125 16.2965625-38.2125 39.5240625-72.86625 69.12-102.4621875z m361.7090625-211.85625h0.1875-0.1875z m71.743125 71.743125c0-39.525-32.0315625-71.555625-71.555625-71.743125v146.295c25.6621875 11.80125 49.63875 26.59875 71.555625 44.581875V365.4246875zM167.15 774.1503125v2.060625c0 39.710625 32.218125 71.7421875 71.74125 71.7421875H435.2c-8.990625-23.9765625-15.1725-48.515625-18.35625-73.8028125H167.15z"></path>
                        <path d="M734.1584375 477.2525c-142.17375 0-257.37375 115.2-257.37375 257.37375S591.9846875 992 734.1584375 992c142.1728125 0 257.3728125-115.2 257.3728125-257.37375S876.33125 477.2525 734.159375 477.2525zM865.0925 761.975L723.85625 903.21125c-15.1725 15.1734375-39.710625 15.1734375-54.6965625 0-15.1725-15.1725-15.1725-39.710625 0-54.6965625l113.889375-113.8875-113.889375-113.889375c-15.1725-15.1725-15.1725-39.7115625 0-54.6965625 15.1725-15.1725 39.7115625-15.1725 54.6965625 0l141.2371875 141.2371875a38.5528125 38.5528125 0 0 1 0 54.6965625z"></path>
                    </svg>
                </div>
                <div class="text">群发短信</div>
            </div>
        </div>

        <section class="sms-template" id="shortMessage" @mounted="onMounted($el)">
            <div class="drop-down-select-container">
                <div class="display-header layout-flex-row-lr" @click="eventAnimationSwitch">
                    <div class="add-context">
                        <span v-if="optionIndex === -1" class="placeholder">请选择模板分类</span>
                        <span v-else class="text-select">{{optionValue}}</span>
                    </div>
                    <div ref="elSelectIcon" class="icon-svg">
                        <svg width="40" height="40" viewBox="0 0 1024 1024"><path d="M317.44 972.8c-20.48 0-30.72-10.24-40.96-20.48-20.48-20.48-20.48-61.44 0-81.92L624.64 512l-358.4-358.4c-20.48-20.48-20.48-61.44 0-81.92s61.44-20.48 81.92 0l399.36 399.36c20.48 20.48 20.48 61.44 0 81.92L358.4 952.32c-10.24 10.24-30.72 20.48-40.96 20.48"></path></svg>
                    </div>
                </div>
                <div ref="elSelect" class="display-select">
                    <div class="select-options">
                        <div v-for="(item,index) in options" class="option" @click="eventSelectOption(index)">{{item.name}}</div>
                    </div>
                </div>
                <div v-if="selectSwitch" class="door-closer" @click="eventAnimationSwitch"></div>
            </div>
            <div class="textarea-box">
                <label>
                    <textarea disabled="disabled" placeholder="选择玩短信模板后，这里会显示短信模板内容。" v-model="templateText"></textarea>
                </label>
                <aside>
                    <div class="text-time">{{templateUpdatedAt}}</div>
                    <div class="text-count">{{templateCharLength}}</div>
                </aside>
            </div>
            <div class="tips-box">
                <p>超过70字短信每次算长短信，长短信的收费是普通短信的2倍。</p>
                <p>发送成功的短信才会扣除相应的费用，费用不足时未发送的用户则会被标记未发送失败。</p>
            </div>
        </section>

        <section class="customer-select" id="customerSelect" @mounted="onMounted($el)">
            <div class="filtrate-menu">
                <ul class="menu-button" ref="menuButton">
                    <li v-for="(item,index) of menuSelect" @click="eventMenuSelectSwitch($el,item,index)">
                        <div class="name">{{item.name}}</div>
                        <div class="icon-svg">
                            <svg viewBox="0 0 1024 1024" width="20" height="20">
                                <path d="M440.9664 789.29386666L65.44 370.91946666A93.1776 93.1776 0 0 1 41.6 308.67946666C41.6 257.19786666 83.36 215.46666666 134.8736 215.46666666h751.05173333a93.30773333 93.30773333 0 0 1 62.28266667 23.82506667c38.34666667 34.3744 41.54773333 93.3056 7.15093333 131.62666666L579.83253333 789.29493333a93.24266667 93.24266667 0 0 1-7.152 7.14666666c-38.34666667 34.37546667-97.31733333 31.17546667-131.71413333-7.14666666z"></path>
                            </svg>
                        </div>
                    </li>
                </ul>
                <ul class="menu-list" ref="menuList">
                    <li v-for="(item,index) of menuSelectOptions" :class="getMenuSelectOptionClass(item,index)" @click="eventMenuSelectOption($el,index)">
                        <span>{{item.name}}</span>
                    </li>
                </ul>
            </div>
            <div class="customer-view">
                <div class="row" v-for="(item,index) of customerList">
                    <div class="name">{{item.name}}</div>
                    <div class="contact">{{item.contact}}</div>
                    <div class="checkbox">
                        <label>
                            <input type="checkbox" checked="checked">
                        </label>
                    </div>
                </div>
                <div class="load-wait-container" ref="loadWait">
                    <div class="spinner">
                        <svg class="svg-spin" viewBox="0 0 1024 1024" width="40" height="40">
                            <path d="M876.864 782.592c3.264 0 6.272-3.2 6.272-6.656 0-3.456-3.008-6.592-6.272-6.592-3.264 0-6.272 3.2-6.272 6.592 0 3.456 3.008 6.656 6.272 6.656z m-140.544 153.344c2.304 2.432 5.568 3.84 8.768 3.84a12.16 12.16 0 0 0 8.832-3.84 13.76 13.76 0 0 0 0-18.56 12.224 12.224 0 0 0-8.832-3.84 12.16 12.16 0 0 0-8.768 3.84 13.696 13.696 0 0 0 0 18.56zM552.32 1018.24c3.456 3.648 8.32 5.76 13.184 5.76a18.368 18.368 0 0 0 13.184-5.76 20.608 20.608 0 0 0 0-27.968 18.368 18.368 0 0 0-13.184-5.824 18.368 18.368 0 0 0-13.184 5.76 20.608 20.608 0 0 0 0 28.032z m-198.336-5.76c4.608 4.8 11.072 7.68 17.6 7.68a24.448 24.448 0 0 0 17.536-7.68 27.456 27.456 0 0 0 0-37.248 24.448 24.448 0 0 0-17.536-7.68 24.448 24.448 0 0 0-17.6 7.68 27.52 27.52 0 0 0 0 37.184z m-175.68-91.84c5.76 6.08 13.824 9.6 21.952 9.6a30.592 30.592 0 0 0 22.016-9.6 34.368 34.368 0 0 0 0-46.592 30.592 30.592 0 0 0-22.016-9.6 30.592 30.592 0 0 0-21.952 9.6 34.368 34.368 0 0 0 0 46.592z m-121.152-159.36c6.912 7.36 16.64 11.648 26.368 11.648a36.736 36.736 0 0 0 26.432-11.584 41.28 41.28 0 0 0 0-55.936 36.736 36.736 0 0 0-26.432-11.584 36.8 36.8 0 0 0-26.368 11.52 41.28 41.28 0 0 0 0 56zM12.736 564.672a42.88 42.88 0 0 0 30.784 13.44 42.88 42.88 0 0 0 30.784-13.44 48.128 48.128 0 0 0 0-65.216 42.88 42.88 0 0 0-30.72-13.44 42.88 42.88 0 0 0-30.848 13.44 48.128 48.128 0 0 0 0 65.216z m39.808-195.392a48.96 48.96 0 0 0 35.2 15.36 48.96 48.96 0 0 0 35.2-15.36 54.976 54.976 0 0 0 0-74.56 48.96 48.96 0 0 0-35.2-15.424 48.96 48.96 0 0 0-35.2 15.424 54.976 54.976 0 0 0 0 74.56zM168.32 212.48c10.368 11.008 24.96 17.408 39.68 17.408 14.592 0 29.184-6.4 39.552-17.408a61.888 61.888 0 0 0 0-83.84 55.104 55.104 0 0 0-39.616-17.408c-14.656 0-29.248 6.4-39.616 17.408a61.888 61.888 0 0 0 0 83.84zM337.344 124.8c11.52 12.16 27.712 19.264 43.968 19.264 16.256 0 32.448-7.04 43.968-19.264a68.672 68.672 0 0 0 0-93.184 61.248 61.248 0 0 0-43.968-19.264 61.248 61.248 0 0 0-43.968 19.264 68.736 68.736 0 0 0 0 93.184z m189.632-1.088c12.672 13.44 30.528 21.248 48.448 21.248s35.712-7.808 48.384-21.248a75.584 75.584 0 0 0 0-102.464A67.392 67.392 0 0 0 575.36 0c-17.92 0-35.776 7.808-48.448 21.248a75.584 75.584 0 0 0 0 102.464z m173.824 86.592c13.824 14.592 33.28 23.104 52.736 23.104 19.584 0 39.04-8.512 52.8-23.104a82.432 82.432 0 0 0 0-111.744 73.472 73.472 0 0 0-52.8-23.168c-19.52 0-38.912 8.512-52.736 23.168a82.432 82.432 0 0 0 0 111.744z m124.032 158.528c14.976 15.872 36.032 25.088 57.216 25.088 21.12 0 42.24-9.216 57.152-25.088a89.344 89.344 0 0 0 0-121.088 79.616 79.616 0 0 0-57.152-25.088c-21.184 0-42.24 9.216-57.216 25.088a89.344 89.344 0 0 0 0 121.088z m50.432 204.032c16.128 17.088 38.784 27.008 61.632 27.008 22.784 0 45.44-9.92 61.568-27.008a96.256 96.256 0 0 0 0-130.432 85.76 85.76 0 0 0-61.568-27.072c-22.848 0-45.44 9.984-61.632 27.072a96.192 96.192 0 0 0 0 130.432z"></path>
                        </svg>
                        <span>数据加载中，请稍后...</span>
                    </div>
                </div>
            </div>
        </section>

        <div id="buttonConfirm" class="button-base button-border-round button-confirm" @click="eventConfirm">发送短信</div>
    </main>

    <aside class="aside">
        <dialog class="component-dialog" id="dialog" @mounted="onMounted($el)">
            <header class="header">
                <h2>再次确认</h2>
            </header>
            <section class="body">
                <p>本次群发短信客户数量总计{{customerCount}}个，短信标题为《{{headline}}》、短信文本包含{{templateCharLength}}字。预估消耗{{calcIntegral}}积分或{{calcMoney}}元。</p>
            </section>
            <footer class="footer">
                <div class="button-row">
                    <div class="button button-gray" @click="eventSubmit('cancel')">取消</div>
                    <div class="button button-red" @click="eventSubmit('confirm')">确定</div>
                </div>
            </footer>
        </dialog>
    </aside>
    <%@ include file="./template/layout-footer.jsp" %>
</article>

</body>
</html>