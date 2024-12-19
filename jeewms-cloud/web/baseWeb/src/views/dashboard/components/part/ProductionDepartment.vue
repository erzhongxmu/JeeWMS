<template>
  <div class="main-container">
    <div style="width: 50%;">
      <SmtChart :legendList='legendList' :title='title' :seriesData='seriesData' :height="'300px'"/>
    </div>
    <div style="width: 50%;">
      <SmtChart :legendList='legendList' :title='title' :seriesData='seriesData' :height="'300px'"/>
    </div>
    <div style="width: 50%;">
      <SmtChart :legendList='legendList' :title='title' :seriesData='seriesData' :height="'300px'"/>
    </div>
    <div style="width: 50%;">
      <SmtChart :legendList='legendList' :title='title' :seriesData='seriesData' :height="'300px'"/>
    </div>
    <div style="width: 50%;">
      <SmtChart :legendList='legendList' :title='title' :seriesData='seriesData' :height="'300px'"/>
    </div>
    <div style="width: 50%;">
      <SmtChart :legendList='legendList' :title='title' :seriesData='seriesData' :height="'300px'"/>
    </div>
  </div>
</template>

<script>
import SmtChart from '@views/dashboard/components/SmtChart'

export default {
  name: 'ProductionDepartment',
  components:{
    SmtChart
  },
  data(){
    return {
      title:'损件',
      legendList:['NGQty','Fail rate'],
      seriesData:[
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
      ],
    }
  },
  mounted() {

  }
}
</script>

<style scoped lang="less">
  .main-container {
    width: 100%;
    height: 100%;
    display: flex;
    flex-wrap: wrap;
  }

</style>