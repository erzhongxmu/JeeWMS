<template>
  <SmtChart :legendList='legendList' :title='title' :seriesData='seriesData' :height="height"/>
</template>

<script>
import SmtChart from '@views/dashboard/components/SmtChart'
export default {
  name: 'SMTYR',
  props:{
    height:{
      type:String,
      default:'300px'
    }
  },
  data(){
    return {
      title:'SMT_FPYR trent chart',
      legendList:[],
      seriesData:[],
    }
  },
  components:{
    SmtChart
  },
  mounted() {
    console.log('SMTYR')
    this.legendList = ['SMT_input', 'Goal','SMT_FPYR']
    this.seriesData = [
      {
        name: 'SMT_input',
        type: 'bar',
        data: [369, 458, 561, 792, 1536, 1234, 1256, 666, 777, 510, 1562, 789]
      },
      {
        name: 'Goal',
        type: 'line',
        data: [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0.9784, 0.9904],
        yAxisIndex:1
      },
      {
        name: 'SMT_FPYR',
        type: 'line',
        data: [0.97, 0.97, 0.97, 0.97, 0.97, 0.97, 0.97, 0.97, 0.97, 0.97, 0.97, 0.97],
        yAxisIndex:1
      }
    ]
  }
}
</script>

<style scoped>

</style>