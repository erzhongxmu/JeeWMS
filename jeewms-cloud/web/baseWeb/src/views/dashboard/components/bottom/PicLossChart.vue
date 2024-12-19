<template>
  <div class='ant-row-flex chart-container'>
    <div style='flex: 2'>
      <div class="space">
        <div class="left-chart">
          <PICLoss :orientType="false" v-if="activePart === 0" :height="'500px'"/>
          <PartChart v-if="activePart !== 0" :activePart="activePart"/>
        </div>
        <div class="right-buttons">
          <a-button :type="activePart === 1 ? 'primary' : ''" @click="changePart(1)" style="margin-bottom: 10px">生产部</a-button>
          <a-button :type="activePart === 2 ? 'primary' : ''" @click="changePart(2)" style="margin-bottom: 10px">技术部</a-button>
          <a-button :type="activePart === 3 ? 'primary' : ''" @click="changePart(3)" style="margin-bottom: 10px">品质部</a-button>
          <a-button :type="activePart === 4 ? 'primary' : ''" @click="changePart(4)" style="margin-bottom: 10px">ME工程部</a-button>
          <a-button :type="activePart === 5 ? 'primary' : ''" @click="changePart(5)" style="margin-bottom: 10px">EE工程部</a-button>
        </div>
      </div>
    </div>
    <div style='flex: 1'>
      <StatisticalTable :StatisticalData='StatisticalData'/>
    </div>
  </div>
</template>

<script>
import PICLoss from '@views/dashboard/components/top/PICLoss'
import StatisticalTable from '@views/dashboard/components/StatisticalTable'
import PartChart from '@views/dashboard/components/PartChart'

export default {
  name: 'PicLossChart',
  components:{
    PICLoss,
    StatisticalTable,
    PartChart
  },
  data(){
    return {
      StatisticalData:[],
      activePart:0,
    }
  },
  methods:{
    changePart(index){
      if (this.activePart !== index){
        this.activePart = index
      }else{
        this.activePart = 0
      }
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
  .space {
    height: 100%;
    display: flex;
    .left-chart {
      flex: 1;
    }
    .right-buttons {
      height: 100%;
      padding-right: 10px;
      display: flex;
      flex-direction: column;
      //justify-content: center;
    }
  }
}
</style>