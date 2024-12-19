<template>
  <SmtChart :legendList='legendList' :title='title' :seriesData='seriesData' :height="height"/>
</template>

<script>
import SmtChart from '@views/dashboard/components/SmtChart'
export default {
  name: 'DIPYR',
  props:{
    height:{
      type:String,
      default:'300px'
    }
  },
  components:{
    SmtChart
  },
  data(){
    return {
      title:'SMT_TOP issue',
      legendList:[],
      seriesData:[],
    }
  },
  mounted() {
    this.legendList = ['NGQty', 'NG Fail addition']
    this.seriesData = [
      {
        name: 'NGQty',
        type: 'bar',
        data: [369, 458, 561, 792, 1536, 1234, 1256, 666, 777, 510, 1562, 789]
      },
      {
        name: 'NG Fail addition',
        type: 'line',
        data: [1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0.9784, 0.9904],
        yAxisIndex:1
      },
    ]
  }
}
</script>

<style scoped>

</style>