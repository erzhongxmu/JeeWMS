<template>
  <div class="spinBox">
    <a-spin class="spin"/>
  </div>
</template>

<script>
  export default {
    name: 'ExportSpin'
  }
</script>

<style scoped>
  .spinBox{
    position: absolute;
    width: 100%;
    height: 100%;
    background: #e8e8e8;
    z-index: 1;
    top: 0;
    left: 0;
    opacity: .5;
  }
  .spinBox .spin{
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%,-50%);
  }
</style>