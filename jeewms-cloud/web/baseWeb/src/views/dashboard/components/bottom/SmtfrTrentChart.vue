<template>
  <div class='ant-row-flex chart-container'>
    <div class="left">
      <div style="flex: 1">
        <AssemblyYR :height="'500px'"/>
      </div>
      <div class="table-container">
        <ChartInfoTable />
      </div>
    </div>
    <div class="right">
      <StatisticalTable :StatisticalData='StatisticalData'/>
    </div>
  </div>
</template>

<script>
import AssemblyYR from '@views/dashboard/components/top/AssemblyYR'
import StatisticalTable from '@views/dashboard/components/StatisticalTable'
import ChartInfoTable from '@views/dashboard/components/ChartInfoTable'

export default {
  name: 'SmtfrTrentChart',
  components:{
    AssemblyYR,
    StatisticalTable,
    ChartInfoTable
  },
  data(){
    return {
      StatisticalData:[],
    }
  },
  mounted() {
    this.StatisticalData = [
      {
        key: '1',
        name: '线程1号',
        SMT: 121,
        DIP: 333,
        Assembly: 433,
      },
      {
        key: '2',
        name: '线程2号',
        SMT: 121,
        DIP: 333,
        Assembly: 433,
      },
      {
        key: '3',
        name: '线程3号',
        SMT: 121,
        DIP: 333,
        Assembly: 433,
      },
    ]
  }
}
</script>

<style scoped lang="less">
.chart-container {
  //height: 500px;
  .left {
    flex: 2;
    display: flex;
    flex-direction: column;
    padding-right: 10px;

    .table-container {
      min-height: 220px;
    }
  }
  .right {
    flex: 1;
  }
}
</style>