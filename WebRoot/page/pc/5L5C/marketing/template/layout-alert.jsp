<%@page contentType="text/html;charset=UTF-8" session="false" %>
<aside class="left-alert-aside sticky-aside" id="leftAlertAside">

</aside>

<template id="leftAlertAsideTemplate">
    <ul class="alert-root">
        <li v-for="item in store.alertList">
            <div class="alert-base" :class="getItemClass(item)">
                <div v-html="getItemSVG(item)" class="icon-alert"></div>
                <div class="main-text">{{item.text}}</div>
                <div class="icon-cancel" @click="eventCancel(item)">
                    <svg viewBox="0 0 1024 1024" width="40" height="40">
                        <path d="M933.43943656 991.9994c-15.35999063 0-29.75998125-5.75999625-41.27997375-17.27998875L49.27998875 131.83993719c-23.03998594-23.03998594-23.03998594-59.5199625 0-82.55994844s59.5199625-23.03998594 82.55994844 0l842.87947406 842.87947406a57.95996344 57.95996344 0 0 1 0 82.55994844 57.38996437 57.38996437 0 0 1-41.27997469 17.27998875zM90.55996344 991.9994c-15.35999063 0-29.75998125-5.75999625-41.27997469-17.27998875a57.95996344 57.95996344 0 0 1 0-82.55994844L892.15946281 49.27998875c23.03998594-23.03998594 59.5199625-23.03998594 82.55994844 0s23.03998594 59.5199625 0 82.55994844L131.83993719 974.71941125A57.38996437 57.38996437 0 0 1 90.55996344 991.9994z"></path>
                    </svg>
                </div>
            </div>
        </li>
    </ul>
</template>

<script type="module">
    import {createApp} from '/js/pc/5L5C/marketing/petite-vue.es.js'
    import store,{cancelAlert} from '/js/pc/5L5C/marketing/store.es.js'

    const elLeftAlertAside = document.getElementById("leftAlertAside");
    const elLeftAlertAsideTemplate = document.getElementById("leftAlertAsideTemplate");
    elLeftAlertAside.appendChild(elLeftAlertAsideTemplate.content.cloneNode(true));

    createApp({
        store,
        getItemClass: function (item) {
            switch (item.type) {
                case "error":
                    return "icon-error";
                case "success":
                    return "icon-success";
                case "warning":
                    return "icon-warning";
                case "info":
                    return "icon-info";
            }
        },
        getItemSVG: function (item) {
            switch (item.type) {
                case "error":
                    return `<svg viewBox="0 0 1024 1024" width="40" height="40"><path d="M512 64a448 448 0 1 1 0 896 448 448 0 0 1 0-896m0 393.664L407.936 353.6a38.4 38.4 0 1 0-54.336 54.336L457.664 512 353.6 616.064a38.4 38.4 0 1 0 54.336 54.336L512 566.336 616.064 670.4a38.4 38.4 0 1 0 54.336-54.336L566.336 512 670.4 407.936a38.4 38.4 0 1 0-54.336-54.336z"></path></svg>`;
                case "success":
                    return `<svg viewBox="0 0 1024 1024" width="40" height="40"><path d="M512 64a448 448 0 1 1 0 896 448 448 0 0 1 0-896m-55.808 536.384-99.52-99.584a38.4 38.4 0 1 0-54.336 54.336l126.72 126.72a38.272 38.272 0 0 0 54.336 0l262.4-262.464a38.4 38.4 0 1 0-54.272-54.336z"></path></svg>`;
                case "warning":
                    return `<svg viewBox="0 0 1024 1024" width="40" height="40"><path d="M512 64a448 448 0 1 1 0 896 448 448 0 0 1 0-896m0 192a58.432 58.432 0 0 0-58.24 63.744l23.36 256.384a35.072 35.072 0 0 0 69.76 0l23.296-256.384A58.432 58.432 0 0 0 512 256m0 512a51.2 51.2 0 1 0 0-102.4 51.2 51.2 0 0 0 0 102.4"></path></svg>`;
                case "info":
                    return `<svg viewBox="0 0 1024 1024" width="40" height="40"><path d="M512 64a448 448 0 1 1 0 896.064A448 448 0 0 1 512 64m67.2 275.072c33.28 0 60.288-23.104 60.288-57.344s-27.072-57.344-60.288-57.344c-33.28 0-60.16 23.104-60.16 57.344s26.88 57.344 60.16 57.344M590.912 699.2c0-6.848 2.368-24.64 1.024-34.752l-52.608 60.544c-10.88 11.456-24.512 19.392-30.912 17.28a12.992 12.992 0 0 1-8.256-14.72l87.68-276.992c7.168-35.136-12.544-67.2-54.336-71.296-44.096 0-108.992 44.736-148.48 101.504 0 6.784-1.28 23.68.064 33.792l52.544-60.608c10.88-11.328 23.552-19.328 29.952-17.152a12.8 12.8 0 0 1 7.808 16.128L388.48 728.576c-10.048 32.256 8.96 63.872 55.04 71.04 67.84 0 107.904-43.648 147.456-100.416z"></path></svg>`;
            }
        },
        eventCancel: function (item) {
            // console.log(item)
            cancelAlert(item.kid)
        }
    }).mount('#leftAlertAside');

</script>
