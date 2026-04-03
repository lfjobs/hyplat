<%@page contentType="text/html;charset=UTF-8" session="false" %>

<header class="top-title-header" id="layoutHeader">
    <div class="header-left" @click="event.routerBack">
        <div class="icon-svg">
            <svg viewBox="0 0 1024 1024" xmlns="http://www.w3.org/2000/svg" width="200" height="200">
                <path d="M706.56 51.2c20.48 0 30.72 10.24 40.96 20.48 20.48 20.48 20.47999999 61.44 0 81.92L399.36 512 757.76 870.4C778.24 890.88 778.24 931.84 757.76 952.32c-20.48 20.48-61.44 20.47999999-81.92 0l-399.36-399.36c-20.48-20.48-20.47999999-61.44 0-81.92L665.6 71.68c10.24-10.24 30.72-20.48 40.96-20.48z"></path>
            </svg>
        </div>
    </div>
    <div class="header-title" :title="store.header.title">
        <span class="text" v-html="store.header.title"></span>
    </div>
    <div class="header-right"></div>
</header>

<script type="module">
    import {createApp} from '/js/pc/5L5C/marketing/petite-vue.es.js'
    import store from '/js/pc/5L5C/marketing/store.es.js'

    createApp({
        store,
        event: {
            routerBack() {
                window.history.back()
            },
        },
        compute: {
            atNavIndex(index) {
                if (store.navIndex === index) {
                    return 'opt'
                }
                return '';
            }
        }
    }).mount('#layoutHeader');


</script>