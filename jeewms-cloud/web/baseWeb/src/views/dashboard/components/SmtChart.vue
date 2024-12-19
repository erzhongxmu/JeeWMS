<template>
  <div :style="{ height: height }" ref="chart"></div>
</template>

<script>
const echarts = require('echarts');
export default{
  props:{
    title:{
      type:String,
      default:'标题'
    },
    legendList:{
      type:Array,
      default:()=>[]
    },
    xAxisData:{
      type:Array,
      default:()=>["Jan","Feb","Mar","Apr ","May","Jun","Jul","Aug","Sept","Oct","Nov","Dec"]
    },
    seriesData:{
      type:Array,
      default:()=>[]
    },
    height:{
      type:String,
      default:'300px'
    }
  },
  data () {
    return {};
  },
  methods: {
    initCharts () {
      let that = this
      let myChart = echarts.init(this.$refs.chart);
      // 绘制图表
      myChart.setOption({
        title: { text: that.title,left: 'center' },
        tooltip: {
          trigger: 'axis',
        },
        legend: {
          data:that.legendList,
          bottom:5,
        },
        xAxis: {
          data: that.xAxisData,
          // axisLabel:{
          //   interval:0
          // }
        },
        yAxis: [
          {
            type: 'value',
            // name: '数量',
            splitLine:{
              show:false
            },
            axisLine: {
              show: true,
            },
          },
          {
            type: 'value',
            // min: 0.9,
            // interval:0.02,
            // name: '良品率%',
            splitLine:{
              show:false
            },
            axisLine: {
              show: true,
            },
          }
        ],
        series: that.seriesData
      });
    }
  },
  mounted () {
    setTimeout(()=>{
      this.initCharts();
    },500)
  }
}
</script>

<style scoped>

</style>