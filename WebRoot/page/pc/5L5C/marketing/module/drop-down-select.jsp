<%@page contentType="text/html;charset=UTF-8" session="false" %>

<div class="drop-down-select-container" id="dropDownSelectContainer" @mounted="onMounted($el)">
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
  <div v-if="selectSwitch" class="door-closer" @click="eventAnimationSwitch()"></div>
</div>

<script type="module">
  import { createApp } from '/js/pc/5L5C/marketing/petite-vue.es.js'
  import { eventPubSub } from '/js/pc/5L5C/marketing/store.es.js'

  let appData = {
    selectSwitch:false,
    optionIndex:-1,
    optionValue:"",
    options: [],
    onMounted :function (el){
      appData = this;
    },
    eventAnimationSwitch:function (){

      if(this.options.length === 0){
        return;
      }

      // 状态取反
      this.selectSwitch = !this.selectSwitch;

      const elSelect = this["$refs"]['elSelect'];
      const elSelectIcon = this["$refs"]['elSelectIcon'];

      if(this.selectSwitch){
        elSelectIcon.style.transform = "rotate(270deg)";
        elSelect.style.transform = "scale(1, 1)";
      }
      else{
        elSelectIcon.style.transform = "";
        elSelect.style.transform = "scale(1, 0)";
      }
    },
    eventSelectOption:function (index){
      this.optionIndex = index;
      this.optionValue = this.options[index].name;
      this.eventAnimationSwitch();

      // 事件中心 - 发布事件
      eventPubSub.publish('dropDownSelectOption',this.optionIndex)
    },
  };

  createApp(appData).mount('#dropDownSelectContainer');

  // 事件中心 - 订阅事件
  eventPubSub.subscribe('dropDownSelectSetData',function (dataList){
    if(Array.isArray(dataList)){
      dataList.forEach((item)=>{
        appData.options.push({
          name:item['templateHeadline'],
          value:item['id']
        })
      })
    }
  })
</script>