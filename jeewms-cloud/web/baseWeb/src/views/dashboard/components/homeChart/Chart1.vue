<template>
  <div class="main-content">
    <div class="chart-box" id="typeChart1"></div>
  </div>
</template>

<script>
import { getAction } from '@api/manage'

export default {
  name: "Chart1", // 上架数量前6
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
      getAction('/jeewms/bic/cpNameupCount').then(res => {
        if (res.success) {
          res.result[0].data.forEach(item => {
            this.serierData.push({
              name:item.name,
              value:item.y
            })
          })
          this.initEchart()
        } else {
          this.$message.error(this.title + ':' + res.message)
        }
      })
    },
    initEchart() {
      this.myChart = this.$echarts.init(document.getElementById('typeChart1'))
      this.setOption()
    },
    setOption() {
      let option = {
        grid: {
          top: '10%',
          left: '3%',
          right: '3%',
          bottom: '0%',
          containLabel: true
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          orient: 'horizontal',
          left: 'center',
          bottom: '10'
        },
        series: [
          {
            type: 'pie',
            radius: '50%',
            data: this.serierData,
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            },
            label: {
              show: true,
              formatter: '{d}%', // {b} 为数据项名称，{d} 为百分比
              position: 'inside' // 标签位置，可选 'inside' 或 'outside'
            }
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
