<template>
  <div class="main-content">
    <div class="chart-box" id="typeChart3"></div>
  </div>
</template>

<script>
import { getAction } from '@api/manage'

export default {
  name: "Chart3", // 下架数量前6
  data() {
    return {
      id: '1442744546150064130',
      legendData: [],
      serierData: [],
      xAxisData: [],
      myChart: null,
      color: ['#19EF3C', '#F49E01', '#BF2D62', '#34F2FF', '#9700ED', '#1F7EC2', '#16A3AD', '#5470c6', '#91cc75', '#fac858', '#ee6666', '#73c0de', '#3ba272', '#fc8452', '#9a60b4', '#ea7ccc'],
    }
  },
  mounted() {
    this.getList()
    // this.initEchart()
  },
  methods: {
    getList() {
      getAction('/jeewms/bic/cpNamedownCount').then(res => {
        if (res.success) {
          let arr = [['数量', this.$t('入库数量'), this.$t('出库数量')]]
          res.result[0].data.forEach(item => {
            let arr1 = [item.name,item.y,item.y1]
            arr.push(arr1)
          })
          this.serierData = arr
          this.initEchart()
        } else {
          this.$message.error(this.title + ':' + res.message)
        }
      })
    },
    initEchart() {
      this.myChart = this.$echarts.init(document.getElementById('typeChart3'))
      this.setOption()
    },
    setOption() {
      let that = this
      // let option = {
      //   grid: {
      //     top: '10%',
      //     left: '3%',
      //     right: '10%',
      //     bottom: '0%',
      //     containLabel: true
      //   },
      //   tooltip: {
      //     trigger: 'axis'
      //   },
      //   xAxis: {
      //     type: 'category',
      //     data: this.xAxisData
      //   },
      //   yAxis: {
      //     type: 'value'
      //   },
      //   series: [
      //     {
      //       data: this.serierData,
      //       type: 'bar',
      //       barWidth: 15,
      //     }
      //   ]
      // }



      let option = {
          legend: {},
          tooltip: {},
          dataset: {
            source: this.serierData
          },
          grid: {
            left: '5%', // 左边距
            right: '5%', // 右边距
            bottom: '5%', // 下边距
            top: '15%', // 上边距
            containLabel: true, // 自动调整宽度以包含所有标签（可选）
            width: '90%' // 设置图表区域宽度为父容器的80%
          },
          xAxis: { type: 'category'},
          yAxis: {},
          series: [{
      type: 'bar',
      label: { // 添加标签配置
        show: true,
        position: 'top', // 设置标签在柱子顶部显示
        formatter: (e)=>{console.log(e); return e.value[1]} // 显示原始数值
      },
      name: this.$t('入库数量')
    },
    {
      type: 'bar',
      label: { // 对第二个系列同样进行标签配置
        show: true,
        position: 'top',
        formatter: (e)=>{console.log(e); return e.value[2]} 
      },
      name: this.$t('出库数量')
    }]
        };
      this.myChart.setOption(option);
    },
  }
}
</script>

<style scoped lang="less">
.main-content {
  flex: 1;
  box-sizing: border-box;

  .chart-box {
    width: 100%;
    height: 100%;
  }
}

</style>
