<template>
  <SmtChart :legendList='legendList' :title='title' :seriesData='seriesData' :height="height"/>
</template>

<script>
import SmtChart from '@views/dashboard/components/SmtChart'
export default {
  name: 'AssemblyYR',
  props:{
    height:{
      type:String,
      default:'300px'
    }
  },
  data(){
    return {
      title:'SMT_Fail trent chart',
      legendList:[],
      seriesData:[],
    }
  },
  components:{
    SmtChart
  },
  mounted() {
    this.legendList = ['NGQty', 'Fail rate']
    this.seriesData = [
      {
        name: 'NGQty',
        type: 'bar',
        data: [369, 458, 561, 792, 1536, 1234, 1256, 666, 777, 510, 1562, 789]
      },
      {
        name: 'Fail rate',
        type: 'line',
        data: [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0.9784, 0.9904],
        yAxisIndex:1
      }
    ]
  }
}
</script>

<style scoped>

</style>