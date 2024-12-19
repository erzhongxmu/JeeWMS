<template>
  <a-config-provider :locale="locale">
    <div id="app">
      <router-view  v-if="isRouterAlive"/>
    </div>
  </a-config-provider>
</template>
<script>
  import enquireScreen from '@/utils/device'
  export default {
    data () {
      return {
        locale: {},
        isRouterAlive:true
      }
    },
    provide () {
      return {
        reload: this.reload
      }
    },
    watch: {
    "$store.state.app.lang": {
        handler: function (newVal, oldVal) {
          this.setLanguage(newVal);
        },
      },
    },
    created () {
      let that = this
      enquireScreen(deviceType => {
        // tablet
        if (deviceType === 0) {
          that.$store.commit('TOGGLE_DEVICE', 'mobile')
          that.$store.dispatch('setSidebar', false)
        }
        // mobile
        else if (deviceType === 1) {
          that.$store.commit('TOGGLE_DEVICE', 'mobile')
          that.$store.dispatch('setSidebar', false)
        }
        else {
          that.$store.commit('TOGGLE_DEVICE', 'desktop')
          that.$store.dispatch('setSidebar', true)
        }
      })
      let Language = window.localStorage.getItem('Language')
      if(Language){
        that.$store.dispatch('setLanguage', Language)
      }else{
        that.$store.dispatch('setLanguage', 'zh')
      }

      let SystemPattern = window.localStorage.getItem('SystemPattern')
      if (SystemPattern) {
        that.$store.dispatch('setSystemPattern', SystemPattern)
      }
    },
    methods: {
       reload () {
        this.isRouterAlive = false
        this.$nextTick(function () {
          this.isRouterAlive = true
        })
      },
      //设置多语言
      setLanguage(lang) {
        console.log(lang)
        switch (lang) {
          case "zh":
            this.locale = require("ant-design-vue/es/locale-provider/zh_CN").default;
            break;
          case "en":
          default:
            this.locale = require("ant-design-vue/es/locale-provider/en_US").default;
            break;
        }
      },
    },
  }
</script>
<style>
  #app {
    height: 100%;
  }
</style>