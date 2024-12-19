<template>
  <div class="main-content">
    <div class="chart-box" id="typeChart2"></div>
  </div>
</template>

<script>
import { getAction } from '@api/manage'

export default {
  name: "Chart2", // 近7日下架数
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
      getAction('/jeewms/bic/dayCount').then(res => {
        if (res.success) {
          res.result[0].data.forEach(item => {
            this.xAxisData.push(item.name)
            this.serierData.push(item.y)
          })
          this.initEchart()
        } else {
          this.$message.error(this.title + ':' + res.message)
        }
      })
    },
    initEchart() {
      this.myChart = this.$echarts.init(document.getElementById('typeChart2'))
      this.setOption()
    },
    setOption() {
      let that = this
      let option = {
        grid: {
          top: '10%',
          left: '3%',
          right: '10%',
          bottom: '0%',
          containLabel: true
        },
        tooltip: {
          trigger: 'axis'
        },
        xAxis: {
          type: 'category',
          data: this.xAxisData
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            data: this.serierData,
            type: 'line'
          }
        ]
      }
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
