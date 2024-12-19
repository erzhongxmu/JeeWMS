<template>
  <div class="logo">
    <!-- <img v-if="showTitle" src="~@/assets/PLTN_logo.png" alt="logo" class="PLTN_logo" /> -->
    <!-- <router-link :to="{name:'dashboard'}"> -->

    <!-- update-begin- author:sunjianlei --- date:20190814 --- for: logo颜色根据主题颜色变化 -->
    <!-- <img v-if="navTheme === 'dark'" src="~@/assets/logo-white.png" alt="logo"> -->
    <!--      <img v-else src="~@/assets/ruibo.png" alt="logo">-->
    <!-- update-begin- author:sunjianlei --- date:20190814 --- for: logo颜色根据主题颜色变化 -->

    <h1 style="color: #fff;font-size:20px">{{$t('title')}}</h1>
    <!-- <h1 v-if="showTitle">{{$t('title')}}</h1> -->
    <!-- </router-link> -->
  </div>
</template>

<script>
import { mixin } from '@/utils/mixin.js'
export default {
  name: '',
  mixins: [mixin],
  props: {
    title: {
      type: String,
      required: false
    },
    showTitle: {
      type: Boolean,
      default: true,
      required: false
    }
  }
}
</script>
<style lang="less" scoped>
/*缩小首页布 局顶部的高度*/
@height: 59px;

.sider {
  box-shadow: none !important;
  .logo {
    height: @height !important;
    line-height: @height !important;
    box-shadow: none !important;
    transition: background 300ms;

    a {
      color: white;
      &:hover {
        color: rgba(255, 255, 255, 0.8);
      }
    }
    img {
      width: 60px;
    }
    .PLTN_logo {
      width: 160px;
      height: 40px;
      margin-left: 15px;
    }
  }

  &.light .logo {
    background-color: @primary-color;
  }
}
</style>