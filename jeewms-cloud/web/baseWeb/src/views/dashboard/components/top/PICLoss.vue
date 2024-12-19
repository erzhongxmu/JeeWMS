<template>
  <PICpieChart :title='title' :seriesData='seriesData' :horizontal="orientType" :height="height"/>
</template>

<script>
import PICpieChart from '@views/dashboard/components/PICpieChart'

export default {
  name: 'PICLoss',
  props:{
    orientType:{
      type:Boolean,
      default:true
    },
    height:{
      type:String,
      default:'300px'
    }
  },
  data(){
    return {
      title:'PIC Loss Fail rate',
      seriesData:[],
    }
  },
  components:{
    PICpieChart,
  },
  mounted() {
    this.seriesData = [
      {value: 1048, name: '生产部'},
      {value: 735, name: '技术部'},
      {value: 580, name: '品质部'},
      {value: 484, name: 'ME工程部'},
      {value: 300, name: 'EE工程部'}
    ]
  }
}
</script>

<style scoped>

</style>