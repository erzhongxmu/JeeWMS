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
    /**
     * 横向排列 true,纵向排列 false
     * **/
    horizontal:{
      type:Boolean,
      default:true,
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
    return {
      legendHorizontal:{
        orient: 'horizontal',
        bottom: 5
      }, // 横向排列 底部 legend
      legendVertical:{
        orient: 'vertical',
        right: '10%',
        top: '30%'
      },  // 纵向排列 右中 legend
    };
  },
  methods: {
    initCharts () {
      let that = this
      let myChart = echarts.init(this.$refs.chart);
      // 绘制图表
      myChart.setOption({
        title: {
          text: 'PIC Loss Fail rate',
          left: 'center'
        },
        tooltip: {
          trigger: 'item'
        },
        legend: this.horizontal ? this.legendHorizontal : this.legendVertical,
        series: [
          {
            name: '访问来源',
            type: 'pie',
            avoidLabelOverlap: true,
            radius: '50%',
            data: this.seriesData,
            itemStyle: {
              emphasis: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              },
              normal:{
                label:{
                  show: true,
                  formatter: '{d}%'
                },
                labelLine :{show:true}
              }
            },
          }
        ]
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